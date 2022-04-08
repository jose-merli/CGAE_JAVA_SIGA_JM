package org.itcgae.siga.com.services.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.com.documentos.DataMailMergeDataSource;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.FoUtils;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.services.scs.mappers.ScsSaltoscompensacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.MailMergeCleanupOptions;

@Service
public class GeneracionDocumentosServiceImpl implements IGeneracionDocumentosService {

	private static final int EXCEL_ROW_FLUSH = 1000;

	private Logger LOGGER = Logger.getLogger(GeneracionDocumentosServiceImpl.class);

	@Autowired
	private IPFDService pfdService;
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
	
	@Autowired
	private ScsSaltoscompensacionesExtendsMapper scsSaltoscompensacionesExtendsMapper;

	@Override
	public Document sustituyeDocumento(Document doc, HashMap<String, Object> dato) throws Exception {

		try {

			Set<String> claves = dato.keySet();

			for (String clave : claves) {
				Object o = dato.get(clave);
				if (o instanceof List) {
					List aux = (List) o;
					doc = sustituyeRegionDocumento(doc, clave, aux);
				}
			}

			for (String clave : claves) {
				Object datosMap = (Object) dato.get(clave);
				if (datosMap instanceof HashMap) {
					HashMap htRowDatosInforme = (HashMap) datosMap;
					doc = sustituyeDatos(doc, htRowDatosInforme);
				}
			}

			if (doc != null && doc.getMailMerge() != null) {
				doc.getMailMerge().deleteFields();
			}

		} catch (Exception e) {
			LOGGER.error(
					"GeneracionDocumentosServiceImpl.sustituyeDocumento :: Error al sustituir los datos del documento",
					e);
			throw new BusinessException("Error al reemplazar los datos en el documento", e);
		}
		return doc;
	}

	private Document sustituyeRegionDocumento(Document doc, String region, List dato) throws Exception {
		DataMailMergeDataSource dataMerge = new DataMailMergeDataSource(region, dato);

		try {
			if (doc != null && doc.getMailMerge() != null) {
				doc.getMailMerge().executeWithRegions(dataMerge);
			}
		} catch (Exception e) {
			LOGGER.error("GeneracionDocumentosServiceImpl.sustituyeRegionDocumento :: Error al sustituir región", e);
			throw e;
		}
		return doc;
	}

	private Document sustituyeDatos(Document doc, HashMap<String, Object> dato) {

		try {

			Set<String> claves = dato.keySet();

			DocumentBuilder builder = new DocumentBuilder(doc);

			if (claves.size() != 0) {

				for (String clave : claves) {
					while (builder.moveToMergeField(clave)) {
						Object o = dato.get(clave);
						try {
							if (o != null) {
								builder.write(o.toString().trim());
							} else {
								builder.write(" ");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

			} else {
				doc = null;
			}

			doc.getMailMerge().setCleanupOptions(MailMergeCleanupOptions.REMOVE_CONTAINING_FIELDS
					| MailMergeCleanupOptions.REMOVE_EMPTY_PARAGRAPHS | MailMergeCleanupOptions.REMOVE_UNUSED_REGIONS
					| MailMergeCleanupOptions.REMOVE_UNUSED_FIELDS);

		} catch (Exception e) {
			LOGGER.error(e);
		}

		return doc;
	}

	@Override
	public DatosDocumentoItem grabaDocumento(Document doc, String pathfinal, String nombrefichero, boolean firmado)
			throws Exception {
		// nombrefichero incluye la extension .doc
		File archivo = null;
		DatosDocumentoItem documento = new DatosDocumentoItem();
		try {
			if (doc != null) {
				doc.removeMacros();
				doc.save(pathfinal + nombrefichero);
				archivo = new File(pathfinal + nombrefichero);
				if (!archivo.exists())
					return null;

				byte[] byteArray = null;

				if (firmado) {
					String docFirmado = pfdService.firmarPDF(archivo);
					byteArray = Base64.decodeBase64(docFirmado.getBytes());
				} else {
					byteArray = Files.readAllBytes(archivo.toPath());
				}

				WSCommons.fileBytes(byteArray, pathfinal + nombrefichero);
				
				documento.setFileName(nombrefichero);
				documento.setDatos(byteArray);
				documento.setPathDocumento(pathfinal);
				documento.setDocumentoFile(new File(pathfinal + nombrefichero));
			} else {
				documento = null;
			}

		} catch (Exception e) {
			String mensaje = "Error al guardar el documento: " + pathfinal + nombrefichero;
			LOGGER.error(mensaje);
			throw new BusinessException(mensaje, e);
		}

		return documento;
	}

	@Override
	public DatosDocumentoItem generarExcel(String pathPlantilla, String pathFicheroSalida, String nombreFicheroSalida,
			List<List<Map<String, Object>>> listaDatosExcel, ArrayList<String> nombresConsultasDatos) throws Exception {
		DatosDocumentoItem documento = new DatosDocumentoItem();
		boolean hayPlantilla = false;

		if (listaDatosExcel != null && listaDatosExcel.size() >= 1) {
			if (listaDatosExcel.get(0).size() >= 1) {
				Workbook workbook = null;

				// Creamos el libro de excel
				// si existe la plantilla la cogemos, si no, generamos el excel desde cero

				if (pathPlantilla != null) {
					File plantilla = new File(pathPlantilla);
					
	
					if (plantilla.exists()) {
						FileInputStream inputStream;
						try {
							inputStream = new FileInputStream(new File(pathPlantilla));
							XSSFWorkbook wb = new XSSFWorkbook(inputStream);
							workbook = new SXSSFWorkbook(wb, EXCEL_ROW_FLUSH);
						} catch (Exception e) {
							LOGGER.error(e);
							throw e;
						}
	
						hayPlantilla = true;
					} else {
						workbook = new SXSSFWorkbook(EXCEL_ROW_FLUSH);
					}
				} else {
					workbook = new SXSSFWorkbook(EXCEL_ROW_FLUSH);
				}
				LOGGER.debug("Rellenamos los valores en el excel " + nombreFicheroSalida);

				for (int i = 0; i < listaDatosExcel.size(); i++) {

					// Cada lista de listaDatosExcel se crea en una hoja
					List<Map<String, Object>> registrosHoja = listaDatosExcel.get(i);
					Sheet sheet = null;

					if (!hayPlantilla) {
						// Creamos la hoja
						String nombreHoja = "Consulta " + i;
						if (nombresConsultasDatos != null && nombresConsultasDatos.size() > i
								&& nombresConsultasDatos.get(i) != null) {
							nombreHoja = nombresConsultasDatos.get(i);
						}
						nombreHoja = getNombreConsulta(workbook, nombreHoja);

						sheet = workbook.createSheet(nombreHoja);
					} else {
						sheet = workbook.getSheetAt(i);
					}

					int rowNum = 1;
					int index = 0;
					CellStyle headerCellStyle = null;

					List<String> columnsKey = new ArrayList<String>();

					if (registrosHoja != null && registrosHoja.size() > 0) {
						for (String value : registrosHoja.get(0).keySet()) {
							columnsKey.add(value);
						}
					}

					if (!hayPlantilla) {
						// Le aplicamos estilos a las cabeceras
						Font headerFont = workbook.createFont();
						headerFont.setBold(true);
						// headerFont.setItalic(true);
						headerFont.setFontHeightInPoints((short) 14);
						headerFont.setColor(IndexedColors.BLUE.getIndex());
						headerCellStyle = workbook.createCellStyle();
						headerCellStyle.setFont(headerFont);

						Row headerRow = sheet.createRow(0);

						for (String value : columnsKey) {
							Cell cell = headerRow.createCell(index);
							cell.setCellValue(value);
							cell.setCellStyle(headerCellStyle);
							index++;
						}
					}

				
					Map<Integer, CellStyle> mapaEstilos = new HashMap<Integer, CellStyle>();

					CellStyle cellStyleNum = workbook.createCellStyle();
					cellStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
					
					CellStyle cellStyleString = workbook.createCellStyle();
					cellStyleString.setAlignment(CellStyle.ALIGN_LEFT);
					Row row = null;
					Object campo = null;
					XSSFRichTextString textCell = null;
					
					for (Map<String, Object> map : registrosHoja) {
			
						if (map != null) {
			
							row = sheet.createRow(rowNum++);
							int cell = 0;
			
							
							for (int j = 0; j < columnsKey.size(); j++) {
								campo = map.get(columnsKey.get(j).trim());
								
								if (campo == null || campo.toString().trim() == "") {
									row.createCell(cell).setCellValue("");
								} else {
									Cell celda = row.createCell(cell);
									if (campo instanceof Number) {
										if (!mapaEstilos.containsKey(cell)) {
											mapaEstilos.put(cell, cellStyleNum);
										}
										celda.setCellType(Cell.CELL_TYPE_NUMERIC);
										celda.setCellValue(Double.parseDouble(campo.toString()));
										
									} else if (campo instanceof Date) {
										if (!mapaEstilos.containsKey(cell)) {
											mapaEstilos.put(cell, cellStyleString);
										}
										

										CreationHelper creationHelper = workbook.getCreationHelper();
										
										celda.setCellValue((Date) campo);
										
										CellStyle style1 = workbook.createCellStyle();
										style1.setDataFormat(creationHelper.createDataFormat().getFormat(
												"dd/mm/yyyy hh:mm"));
										celda.setCellStyle(style1);
									} else {
										if (!mapaEstilos.containsKey(cell)) {
											mapaEstilos.put(cell, cellStyleString);
										}
										
										celda.setCellType(Cell.CELL_TYPE_STRING);
										textCell = new XSSFRichTextString(campo.toString());
										celda.setCellValue(textCell);
									}
								}
								cell++;
								
							}
						}
					}
					
					LOGGER.debug("Ponemos los estilos al excel " + nombreFicheroSalida);
					
					for (int j = 0; j < index; j++) {
						//sheet.autoSizeColumn(j);
						if (mapaEstilos.containsKey(j)) {
							sheet.setDefaultColumnStyle(j, mapaEstilos.get(j));
						}
					}
				}

                

				File theDir = new File(pathFicheroSalida);
				if (!theDir.exists()){
				    theDir.mkdirs();
				}

				File file = new File(pathFicheroSalida);
				SIGAHelper.addPerm777(file);
				
				nombreFicheroSalida = reeemplazaCaracteres(nombreFicheroSalida);
				
				file = new File(file, nombreFicheroSalida);
				
				FileOutputStream fileOut = new FileOutputStream(file);
				
				LOGGER.debug("Guardamos el excel en " + file.getAbsolutePath());
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				workbook.close();

				documento.setDatos(Files.readAllBytes(file.toPath()));
				documento.setFileName(nombreFicheroSalida);
				documento.setPathDocumento(pathFicheroSalida);
				documento.setDocumentoFile(new File(pathFicheroSalida + nombreFicheroSalida));
			} else {
				documento = null;
			}
		} else {
			documento = null;
		}
		
		LOGGER.debug("Fin de proceso de generación del excel.");

		return documento;
	}

	
	
	@Override
	public DatosDocumentoItem generarExcelGeneracionCalendario(String pathFicheroSalida, String nombreFicheroSalida,
			List<List<Map<String, Object>>> listaDatosExcel) throws Exception {
		DatosDocumentoItem documento = new DatosDocumentoItem();

		if (listaDatosExcel != null && listaDatosExcel.size() >= 1) {
			if (listaDatosExcel.get(0).size() >= 1) {
				XSSFWorkbook workbook = null;

				// Creamos el libro de excel
				// si existe la plantilla la cogemos, si no, generamos el excel desde cero

				workbook = new XSSFWorkbook();

				LOGGER.debug("Rellenamos los valores en el excel " + nombreFicheroSalida);

				for (int i = 0; i < listaDatosExcel.size(); i++) {

					// Cada lista de listaDatosExcel se crea en una hoja
					List<Map<String, Object>> registrosHoja = listaDatosExcel.get(i);
					XSSFSheet sheet = null;

					// Creamos la hoja
					String nombreHoja = "Consulta " + i;
					nombreHoja = getNombreConsulta(workbook, nombreHoja);
					sheet = workbook.createSheet(nombreHoja);


					int rowNum = 1;
					int index = 0;
					CellStyle headerCellStyle = null;
				
					Map<Integer, CellStyle> mapaEstilos = new HashMap<Integer, CellStyle>();

					CellStyle cellStyleNum = workbook.createCellStyle();
					cellStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
					
					CellStyle cellStyleString = workbook.createCellStyle();
					cellStyleString.setAlignment(CellStyle.ALIGN_LEFT);
					Row row = null;
					Object campo = null;
					XSSFRichTextString textCell = null;
					
					for (Map<String, Object> map : registrosHoja) {
			
						if (map != null) {
			
							row = sheet.createRow(rowNum++);
							for(Map.Entry<String, Object> entry: map.entrySet()) {
								Cell celda1 = row.createCell(0);
								Cell celda2 = row.createCell(1);
								celda1.setCellValue(entry.getKey());
								celda2.setCellValue(entry.getValue().toString());				
							}

						}
					}
					
					LOGGER.debug("Ponemos los estilos al excel " + nombreFicheroSalida);
					
					for (int j = 0; j < index; j++) {
						//sheet.autoSizeColumn(j);
						if (mapaEstilos.containsKey(j)) {
							sheet.setDefaultColumnStyle(j, mapaEstilos.get(j));
						}
					}
				}

                

				File theDir = new File(pathFicheroSalida);
				if (!theDir.exists()){
				    theDir.mkdirs();
				}

				File file = new File(pathFicheroSalida);
				SIGAHelper.addPerm777(file);
				
				nombreFicheroSalida = reeemplazaCaracteres(nombreFicheroSalida);
				
				file = new File(file, nombreFicheroSalida);
				
				FileOutputStream fileOut = new FileOutputStream(file);
				
				LOGGER.debug("Guardamos el excel en " + file.getAbsolutePath());
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				workbook.close();

				documento.setDatos(Files.readAllBytes(file.toPath()));
				documento.setFileName(nombreFicheroSalida);
				documento.setPathDocumento(pathFicheroSalida);
			} else {
				documento = null;
			}
		} else {
			documento = null;
		}
		
		LOGGER.debug("Fin de proceso de generación del excel.");

		return documento;
	}
	public static void main2(String[] args) {
		String nombreHoja = "adfadf :a asd//fad? : ,;;";
		String[] invalidCharsRegex = new String[] { "/", "\\", "*", "[", "]", ":", "?" };
		for (String s : invalidCharsRegex) {
			nombreHoja = nombreHoja.replace(s, "");
		}
		System.out.println(nombreHoja);
	}

	private String getNombreConsulta(Workbook workbook, String nombreHoja) {

		if (nombreHoja != null) {
			nombreHoja = reeemplazaCaracteres(nombreHoja);

			if (nombreHoja.length() > 30) {
				nombreHoja = nombreHoja.substring(0, 27) + "...";
			}
			if (workbook != null) {
				int i = 2;
				while (workbook.getSheet(nombreHoja) != null) {
					nombreHoja = nombreHoja.substring(0, 26) + " " + (i++);
				}
			}
		}

		return nombreHoja;

	}

	private String reeemplazaCaracteres(String nombreHoja) {
		
		String[] invalidCharsRegex = new String[] { "/", "\\", "*", "[", "]", ":", "?" };
		
		if (nombreHoja != null) {
			for (String s : invalidCharsRegex) {
				nombreHoja = nombreHoja.replace(s, "");
			}
		}
		return nombreHoja;
	}

//	public boolean isNumeric(String cadena) {
//
//		boolean resultado;
//
//		try {
//			Integer.parseInt(cadena);
//			resultado = true;
//		} catch (NumberFormatException excepcionInteger) {
//
//			try {
//				Double.parseDouble(cadena);
//				resultado = true;
//			} catch (NumberFormatException excepcionDouble) {
//
//				try {
//					Long.parseLong(cadena);
//					resultado = true;
//				} catch (NumberFormatException excepcionLong) {
//
//					resultado = false;
//				}
//
//			}
//		}
//
//		return resultado;
//	}
	
	
	public static void main(String[] args) throws Exception {
        System.out.println("inicio");
        GeneracionDocumentosServiceImpl generacionDocumentosServiceImpl = new GeneracionDocumentosServiceImpl();
        generacionDocumentosServiceImpl.creaExcel(2);
        generacionDocumentosServiceImpl.creaExcel(1);
//        generacionDocumentosServiceImpl.creaExcel(1);
        System.out.println("fin");
    }
    
    public void creaExcel(int metodo) throws Exception {
        
        int columnasX3 = 6;
        int filas = 10000;
        
        
        int index = 0;
        CellStyle headerCellStyle = null;
        
        
        
        List<String> columnsKey = new ArrayList<String>();
        
        for (int i = 0; i < columnasX3; i++) {
            columnsKey.add("Texto");
            columnsKey.add("Numero");
            columnsKey.add("Fecha");
        }
        
        Workbook workbook = null;
                
        if (metodo == 1) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook(100);
        }
        
        Sheet sheet = workbook.createSheet("sheet1");

        // Le aplicamos estilos a las cabeceras
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        // headerFont.setItalic(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (String value : columnsKey) {
            Cell cell = headerRow.createCell(index);
            cell.setCellValue(value);
            cell.setCellStyle(headerCellStyle);
            index++;
        }
        
        List<Map<String, Object>> registrosHoja = new ArrayList<Map<String,Object>>();
        
        for (int i = 0; i < filas; i++) {
            Map<String, Object> mapa = new HashMap<String, Object>();
            for (int j = 0; j < columnasX3; j++) {
                mapa.put("Texto", "1234" + i + j);
                mapa.put("Numero", 5678 + i + j);
                mapa.put("Fecha", new Date());
            }
            
            registrosHoja.add(mapa);
        }
        
        Calendar iniCalendar = Calendar.getInstance();
        
        
        if (metodo == 1) {
            int rowNum = 1;
            
            for (Map<String, Object> map : registrosHoja) {
    
                if (map != null) {
    
                    Row row = sheet.createRow(rowNum++);
                    int cell = 0;
    
                    CellStyle cellStyle = workbook.createCellStyle();
                    
                    for (int j = 0; j < columnsKey.size(); j++) {
                        Object campo = map.get(columnsKey.get(j).trim());
                        
                        if (campo == null || campo.toString().trim() == "") {
                            row.createCell(cell).setCellValue("");
                        } else {
                            Cell celda = row.createCell(cell);
                            if (campo instanceof Number) {
                                celda.setCellType(Cell.CELL_TYPE_NUMERIC);
                                celda.setCellValue(Double.parseDouble(campo.toString()));
                                cellStyle = workbook.createCellStyle();
                                cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
                                celda.setCellStyle(cellStyle);
                            } else if (campo instanceof Date) {
                                celda.setCellType(Cell.CELL_TYPE_STRING);
                                cellStyle = workbook.createCellStyle();
                                cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
                                XSSFRichTextString textCell = new XSSFRichTextString(
                                        SigaConstants.DATE_FORMAT_MIN.format(campo));
                                celda.setCellValue(textCell);
                                celda.setCellStyle(cellStyle);
                            } else {
                                celda.setCellType(Cell.CELL_TYPE_STRING);
                                cellStyle = workbook.createCellStyle();
                                cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
                                XSSFRichTextString textCell = new XSSFRichTextString(campo.toString());
                                celda.setCellValue(textCell);
                                celda.setCellStyle(cellStyle);
                            }
                        }
                        cell++;
                        
                    }
                }
            }
            
            for (int j = 0; j < index; j++) {
                sheet.autoSizeColumn(j);
            }
        }
        
        
        if (metodo == 2) {
            
            rellenaSheet(sheet, columnsKey, registrosHoja);
        }
        
        Calendar finCalendar = Calendar.getInstance();        
        long tiempoGenerando = (finCalendar.getTimeInMillis() - iniCalendar.getTimeInMillis());
        
        
        System.out.println("Tiempo generando Excel método " + metodo + ": " + tiempoGenerando);
        
        File file = new File("c:/tmp/");
        file.mkdirs();
        file = new File(file, "metodo_" + metodo + ".xlsx");
        
        OutputStream fileOut = new FileOutputStream(file);
        
        
        iniCalendar = Calendar.getInstance();
        workbook.write(fileOut);  
        fileOut.flush();
        fileOut.close();
        workbook.close();
        
        
        		
//        workbook.write(bos);
//        workbook.close();
//        fileOut.flush();
        
        
        finCalendar = Calendar.getInstance();
        long tiempoGuardado = (finCalendar.getTimeInMillis() - iniCalendar.getTimeInMillis());
        System.out.println("Guardado: " + tiempoGuardado);
        System.out.println("Suma total método " + metodo + ": " + (tiempoGenerando + tiempoGuardado));

        
        byte[] arrayBytes = Files.readAllBytes(file.toPath());
        
        iniCalendar = Calendar.getInstance();
        ficheroPlano(registrosHoja, columnsKey, metodo);
        finCalendar = Calendar.getInstance();
        System.out.println("En texto plano: " + (finCalendar.getTimeInMillis() - iniCalendar.getTimeInMillis()));
        
    }


	private void rellenaSheet(Sheet sheet, List<String> columnsKey, List<Map<String, Object>> registrosHoja) {
        
        int rowNum = 1;
        Map<Integer, CellStyle> mapaEstilos = new HashMap<Integer, CellStyle>();

        CellStyle cellStyleNum = sheet.getWorkbook().createCellStyle();
        cellStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
        
        CellStyle cellStyleString = sheet.getWorkbook().createCellStyle();
        cellStyleString.setAlignment(CellStyle.ALIGN_LEFT);
        
        Row row = null;
        Cell celda = null;
        XSSFRichTextString textCell = null;
        
        for (Map<String, Object> map : registrosHoja) {

            if (map != null) {    
                row = sheet.createRow(rowNum++);
                int cell = 0;

                for (int j = 0; j < columnsKey.size(); j++) {
                    Object campo = map.get(columnsKey.get(j).trim());
                    
                    if (campo == null || campo.toString().trim() == "") {
                        row.createCell(cell).setCellValue("");
                    } else {
                        celda = row.createCell(cell);
                        if (campo instanceof Number) {
                            if (!mapaEstilos.containsKey(cell)) {
                                mapaEstilos.put(cell, cellStyleNum);
                            }
                            celda.setCellType(Cell.CELL_TYPE_NUMERIC);
                            celda.setCellValue(Double.parseDouble(campo.toString()));
                            
                        } else if (campo instanceof Date) {
                            if (!mapaEstilos.containsKey(cell)) {
                                mapaEstilos.put(cell, cellStyleString);
                            }
                            celda.setCellType(Cell.CELL_TYPE_STRING);
                            textCell = new XSSFRichTextString(
                                    SigaConstants.DATE_FORMAT_MIN.format(campo));
                            celda.setCellValue(textCell);
                        } else {
                            if (!mapaEstilos.containsKey(cell)) {
                                mapaEstilos.put(cell, cellStyleString);
                            }
                            
                            celda.setCellType(Cell.CELL_TYPE_STRING);
                            textCell = new XSSFRichTextString(campo.toString());
                            celda.setCellValue(textCell);
                        }
                    }
                    cell++;
                    
                }
            }
        }
        
        for (int j = 0; j < columnsKey.size(); j++) {
//            sheet.autoSizeColumn(j);
            if (mapaEstilos.containsKey(j)) {
                sheet.setDefaultColumnStyle(j, mapaEstilos.get(j));
            }
        }
        
    }

    private static void ficheroPlano(List<Map<String, Object>> registrosHoja, List<String> columnsKey, int metodo) throws IOException {
        
        final String TABULADOR = "\t";
        
        File file = new File("c:/tmp/");
        file.mkdirs();
        file = new File(file, "plano_" + metodo + ".xls");

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        
        for (int j = 0; j < columnsKey.size(); j++) {
            writer.write(columnsKey.get(j));
            writer.write(TABULADOR);
        }
        writer.newLine();
        
        for (Map<String, Object> map : registrosHoja) {

            if (map != null) {

                for (int j = 0; j < columnsKey.size(); j++) {
                    
                    Object campo = map.get(columnsKey.get(j).trim());
                    
                    if (campo != null && !campo.toString().trim().equals("")) {
                        
                        if (campo instanceof Number) {
                            writer.write(campo.toString());                            
                        } else if (campo instanceof Date) {
                            
                            String textCell = SigaConstants.DATE_FORMAT_MIN.format(campo);
                            writer.write(textCell);
                        } else {
                            writer.write(campo.toString());
                        }
                    }
                    writer.write(TABULADOR);
                }
                writer.newLine();
            }
        }
        writer.flush();
        writer.close();
        
    }

	@Override
	public DatosDocumentoItem generarFO(String plantilla, String rutaTmp, String nombreFicheroSalida,
			List<Map<String, Object>> hDatosFinal) throws IOException, Exception {
		DatosDocumentoItem documento = new DatosDocumentoItem();
		if(hDatosFinal != null && hDatosFinal.size() > 0) {
			// 1. obteniendo plantilla FO
			
			File plantillaFO = new File(plantilla);
			LOGGER.debug("*********** rutaFicheroFO: " + plantilla);
	
			// 2. generando intermedio FOP a partir de plantilla y datos
			// 2.1. obteniendo ruta para fichero intermedio FOP
			String rutaFicheroFOP = rutaTmp 
					+ nombreFicheroSalida + ".fo";
			File ficheroFOP = new File(rutaFicheroFOP);
	
			// 2.2. obteniendo texto de plantilla FO
			String sPlantillaFO = UtilidadesString.getFileContent(plantillaFO);
			
			// 2.3. generando intermedio FOP, reemplazando los datos en la plantilla
			String content = reemplazarDatos(hDatosFinal, sPlantillaFO);
			UtilidadesString.setFileContent(ficheroFOP, content);
	
			// 3. generando PDF final
			// 3.1. obteniendo ruta para fichero PDF final
			
			File ficheroPDF = new File(rutaTmp+nombreFicheroSalida);
			LOGGER.debug("*********** rutaFicheroPDF: " + nombreFicheroSalida);
	
			// 3.2. convirtiendo FOP a PDF
	//					Plantilla plantilla = new Plantilla(this.usuario);
			FoUtils.convertFO2PDF(ficheroFOP, ficheroPDF,	rutaTmp);
	
			// 3.3. borrando fichero intermedio FOP generado
			ficheroFOP.delete();
	
			// devolviendo el fichero PDF generado
			try {
				documento.setDatos(Files.readAllBytes(ficheroPDF.toPath()));
				documento.setFileName(nombreFicheroSalida);
				documento.setPathDocumento(rutaTmp);
			} catch (IOException e) {
				LOGGER.error("Error al devolver el fichero generado");
				e.printStackTrace();
			}
		} else {
			documento = null;
		}
		return documento;
	}
	
	protected String reemplazarDatos(List<Map<String, Object>> hDatosFinal, String plantillaFO) throws Exception{
		
		Hashtable htDatos = new Hashtable<>();
		String institucion = hDatosFinal.get(0).get("IDINSTITUCION").toString();
		String turno = hDatosFinal.get(0).get("IDTURNO").toString();
		
		//Cargar último letrado
		List<TurnosItem> turnosItems = scsTurnosExtendsMapper.busquedaUltimoLetrado(turno, Short.valueOf(institucion));
		if(turnosItems!= null && !turnosItems.isEmpty()) {
			String nombreTurno =turnosItems.get(0).getNombre();
			htDatos.put("NCOLEGIADO", turnosItems.get(0).getNumerocolegiado());
			htDatos.put("APELLIDO1", turnosItems.get(0).getApellido1());
			htDatos.put("APELLIDO2", turnosItems.get(0).getApellido2());
			htDatos.put("NOMBRE", turnosItems.get(0).getNombrepersona());
			htDatos.put("NOMBRE_TURNO", nombreTurno);
		}
		htDatos.put("FECHA_GENERACION",  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		htDatos.put("FECHA_CONSULTA", "Muestra todas las inscripciones");//(fecha!=null&&!fecha.trim().equals(""))?fecha:"Muestra todas las inscripciones");
		
		//Cargar listado de letrados en cola
		Vector vLetradosEnCola = new Vector();
		for(Map<String, Object> letradoTurno:hDatosFinal){
			Hashtable row = new Hashtable();
			Hashtable htRow = new Hashtable();
			
			htRow.put("IDPERSONA", letradoTurno.get("IDPERSONA").toString());
			htRow.put("APELLIDOS1", letradoTurno.get("APELLIDOS1").toString());
			if(letradoTurno.get("APELLIDOS2") != null)
				htRow.put("APELLIDOS2", letradoTurno.get("APELLIDOS2").toString());
			else
				htRow.put("APELLIDOS2", " ");
			htRow.put("NOMBRE", letradoTurno.get("NOMBRE").toString());
			htRow.put("NCOLEGIADO", letradoTurno.get("NCOLEGIADO").toString());
			if(letradoTurno.get("FECHAVALIDACION")!=null)
				htRow.put("FECHAVALIDACION", letradoTurno.get("FECHAVALIDACION").toString());
			else
				htRow.put("FECHAVALIDACION", "");
			if(letradoTurno.get("FECHABAJA")!=null)
				htRow.put("FECHABAJA", letradoTurno.get("FECHABAJA").toString());
			else
				htRow.put("FECHABAJA", "");

			vLetradosEnCola.add(htRow);
			
		}
				
		plantillaFO = this.reemplazaRegistros(plantillaFO, vLetradosEnCola, htDatos, "LETRADOS");
		
		
		//Cargar listado de compensaciones
		List<LetradoGuardiaItem> lCompensaciones= scsSaltoscompensacionesExtendsMapper.searchSaltosOCompensacionesOficio(institucion, turno,null, "C");
		
		
		plantillaFO = this.reemplazaRegistrosSyC(plantillaFO, lCompensaciones, htDatos, "COMPENSACIONES");
		
		//Cargar listado de saltos
		List<LetradoGuardiaItem> lSaltos = scsSaltoscompensacionesExtendsMapper.searchSaltosOCompensacionesOficio(institucion, turno,null, "S");
		plantillaFO = this.reemplazaRegistrosSyC(plantillaFO, lSaltos, htDatos, "SALTOS");
				
		
		plantillaFO = this.reemplazaVariables(htDatos, plantillaFO);
		
		return plantillaFO;
	}
	
	public String reemplazaRegistrosSyC(String plantillaFO, List<LetradoGuardiaItem> lCompensaciones, Hashtable htDatos, String delim){
		String CTR="%%"; 
		String plantilla=plantillaFO;
		String delimIni=CTR+"INI_"+delim+CTR;
		String delimFin=CTR+"FIN_"+delim+CTR;
		String sAux="";
		 
		 // RGG 09/07/2008
//		 if (lCompensaciones==null || lCompensaciones.isEmpty()) {
//		 	LOGGER.debug("No hay registros");
//			 // cuando no existen datos se busca la marca INI_TODO_xxx para sustituirla por NADA.
//		     // Estas marcas deben estas fuera de las que pretendemos cambiar con datos.
//			 delimIni=CTR+"INI_TODO_"+delim+CTR;
//			 delimFin=CTR+"FIN_TODO_"+delim+CTR;
//			 sAux="";
//		 } else {
		 	
		     String delimTodoIni=CTR+"INI_TODO_"+delim+CTR;
		     String delimTodoFin=CTR+"FIN_TODO_"+delim+CTR;
		    
		     String auxAux=UtilidadesString.encuentraEntreMarcas(plantilla, delimTodoIni, delimTodoFin);
		    
		     plantilla=UtilidadesString.reemplazaEntreMarcasCon(plantilla, delimTodoIni, delimTodoFin,auxAux);
		    
		     //if(lCompensaciones!=null && !lCompensaciones.isEmpty()){
		     	
				 String plantillaRegistro=UtilidadesString.encuentraEntreMarcas(plantilla, delimIni, delimFin);
				 int size=lCompensaciones.size();
				 LOGGER.debug("TAMAÑO DEL VECTOR PARA REEMPLAZAR DATOS: "+size);
				 if(lCompensaciones == null || lCompensaciones.size() == 0){
					 Hashtable row= new Hashtable();
					 row.put("NCOLEGIADO", " ");
					 row.put("APELLIDOS1", " ");
					 row.put("APELLIDOS2", " ");
					 row.put("NOMBRE", " ");
					 row.put("NUMERO", " ");
					 
					 sAux+=UtilidadesString.reemplazaParametros(plantillaRegistro,CTR, row);
				 }
					 
				 for(LetradoGuardiaItem elemento: lCompensaciones){
					 Hashtable row= new Hashtable();
					 row.put("NCOLEGIADO", elemento.getNumeroColegiado());
					 row.put("APELLIDOS1", elemento.getApellidos1());
					 row.put("APELLIDOS2", elemento.getApellidos2());
					 row.put("NOMBRE", elemento.getNombre());
					 row.put("NUMERO", elemento.getNumeroGrupo());
			
					 sAux+=UtilidadesString.reemplazaParametros(plantillaRegistro,CTR, row);
			
				 }
			 //}
//		 }	 
		 
	     plantilla=UtilidadesString.reemplazaEntreMarcasCon(plantilla, delimIni, delimFin,sAux);
	     
		 return plantilla;
	 }
	
	public String reemplazaRegistros(String plantillaFO, Vector registros, Hashtable htDatos, String delim){
		String CTR="%%"; 
		String plantilla=plantillaFO;
		String delimIni=CTR+"INI_"+delim+CTR;
		String delimFin=CTR+"FIN_"+delim+CTR;
		String sAux="";
		 
		 // RGG 09/07/2008
		 if (registros==null || registros.isEmpty()) {
		 	LOGGER.debug("No hay registros");
			 // cuando no existen datos se busca la marca INI_TODO_xxx para sustituirla por NADA.
		     // Estas marcas deben estas fuera de las que pretendemos cambiar con datos.
			 delimIni=CTR+"INI_TODO_"+delim+CTR;
			 delimFin=CTR+"FIN_TODO_"+delim+CTR;
			 sAux="";
		 } else {
		 	
		     String delimTodoIni=CTR+"INI_TODO_"+delim+CTR;
		     String delimTodoFin=CTR+"FIN_TODO_"+delim+CTR;
		    
		     String auxAux=UtilidadesString.encuentraEntreMarcas(plantilla, delimTodoIni, delimTodoFin);
		    
		     plantilla=UtilidadesString.reemplazaEntreMarcasCon(plantilla, delimTodoIni, delimTodoFin,auxAux);
		    
		     if(registros!=null && !registros.isEmpty()){
		     	
				 String plantillaRegistro=UtilidadesString.encuentraEntreMarcas(plantilla, delimIni, delimFin);
				 int size=registros.size();
				 LOGGER.debug("TAMAÑO DEL VECTOR PARA REEMPLAZAR DATOS: "+size);
				 for(int i=0;i<size;i++){
					 Object rObj=registros.elementAt(i);
					 Hashtable row=null;
					 if(rObj instanceof Row){
						 row=(Hashtable)registros.elementAt(i);
					 }else{
						 row=(Hashtable)rObj;
					 }
					 //ClsLogging.writeFileLog("MASTERREPORT: REGISTRO: "+i,10);
			
					 sAux+=UtilidadesString.reemplazaParametros(plantillaRegistro,CTR, row);
			
				 }
			 }
		 }	 
		 
	     plantilla=UtilidadesString.reemplazaEntreMarcasCon(plantilla, delimIni, delimFin,sAux);
	     
		 return plantilla;
	 }
	
	public String reemplazaVariables(Hashtable htDatos, String plantillaFO){
		String CTR="%%"; 
		return UtilidadesString.reemplazaParametros(plantillaFO,CTR, htDatos);
	 }

}
