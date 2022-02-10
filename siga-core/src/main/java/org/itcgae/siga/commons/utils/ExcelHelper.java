package org.itcgae.siga.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @author MJM 
 * @date 06/04/2015
 *
 */
@Service
public class ExcelHelper {
	
	private static Logger log = Logger.getLogger(ExcelHelper.class);
	public static final String C_INFO = "INFO"; 
	
	
	private Vector<Hashtable<String, Object>> parseWorkbook(HSSFWorkbook wb) throws BusinessException {
		Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String, Object>>();
		
		try {
			HSSFSheet ws = wb.getSheetAt(0);
			// Cogemos los datos de columnas y filas
			int rowNum = ws.getLastRowNum();
			int colNum = ws.getRow(0).getLastCellNum();
			int nuevaFila = 1;
			Vector<Hashtable<String, Object>> datosHoja = new Vector<Hashtable<String, Object>>();
			for (int fila = nuevaFila; fila <= rowNum; fila++) {
				Hashtable<String, Object> dFilaH = new Hashtable<String, Object>();
				HSSFRow row = ws.getRow(fila);
				boolean tieneAlgunaCeldaValue = false;
				if(row!=null){
					for (int columna = 0; columna < colNum; columna++) {
						String clave = "";
						// Si tiene cabecera y NO es la cabecera reservada (INFO) para ayudar a usario a rellenar el excel, devolvemos para cada fila un hashmap con Key igual a los nombres de cada columna
						if(ws.getRow(0).getCell(columna)!=null && !ws.getRow(0).getCell(columna).toString().equals("") && !ws.getRow(0).getCell(columna).toString().equals(C_INFO))
							clave = ws.getRow(0).getCell(columna).toString();
						else{
							continue;
						}
						HSSFCell cell = row.getCell(columna);
						if (cell != null && !cell.toString().trim().equals("")) {
							String celdaTrim = cell.toString().trim();
							 if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								dFilaH.put(clave, "");
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								String valorS = celdaTrim;
								String valorCelda = null;
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									Date date = cell.getDateCellValue();
									DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
								    valorCelda = dateFormat.format(date);
								}
								// Tratamiento de las fechas que las reconoce como tipo de celda num�rico
//								if (valorS.indexOf("-") != -1 || valorS.indexOf("/") != -1) {
//									valorCelda = celdaTrim;
//									HSSFDateUtil.getExcelDate(cell.getDateCellValue());
								 else {
									if(!clave.equals(SigaConstants.NIF)) {
										Integer parteEntera = Integer.parseInt(valorS.substring(0, valorS.indexOf(".")));
										Integer parteDecimal = Integer.parseInt(valorS.substring(valorS.indexOf(".") + 1, valorS.length()));
										if (parteDecimal == 0)
											valorCelda = parteEntera.toString();
										else
											valorCelda = celdaTrim;
									}else {
										valorCelda = celdaTrim;
									}
								
								}
								dFilaH.put(clave, valorCelda == null ? "" : valorCelda);
								tieneAlgunaCeldaValue = true;
							} else {
								dFilaH.put(clave, celdaTrim);
								tieneAlgunaCeldaValue = true;
							}
	
						} else {
							dFilaH.put(clave, "");
						}
					}
					if (tieneAlgunaCeldaValue)
						datosHoja.add(dFilaH);
				}
			}
			datos.addAll(datosHoja);

		} catch (Exception e) {
			log.error("Se ha producido un error en parseWorkbook ", e);
			throw new BusinessException("Se ha producido un error al parsear los datos del fichero excel", e);
		}

		return datos;
	}

	public File createExcelFile(List<String> cabecera, Vector<Hashtable<String, Object>> datos) throws BusinessException {
		return createExcelFile(cabecera, datos,"fichero");
		
	}
	
	public File createExcelFile(List<String> cabecera, Vector<Hashtable<String, Object>> datos,String nombreFichero) throws BusinessException {
		HSSFWorkbook generarLibroExcelUnaHoja = createExcel(cabecera, datos);
		File returnFile = null;
		try {
			File tmpFile = File.createTempFile("xls","");
			if(nombreFichero == null)
				nombreFichero = "fichero";
			returnFile = new File(tmpFile.getParent()+File.separator+nombreFichero+".xls");
			returnFile.createNewFile();
			tmpFile.delete();
			FileOutputStream archivo = new FileOutputStream(returnFile);
			generarLibroExcelUnaHoja.write(archivo);
			archivo.flush();
			archivo.close();
		} catch (Exception e) {
			throw new BusinessException("Error al crear el fichero Excel" + e.toString());
		}

		return returnFile;
	}
	

	public byte[] createExcelBytes(List<String> cabecera, Vector<Hashtable<String, Object>> datos) throws BusinessException {
		HSSFWorkbook generarLibroExcelUnaHoja = createExcel(cabecera, datos);
		ByteArrayOutputStream archivo = new ByteArrayOutputStream();
		try {
			generarLibroExcelUnaHoja.write(archivo);
			archivo.flush();
			archivo.close();
		} catch (IOException e) {
			throw new BusinessException("Error al crear el fichero Excel" + e.toString());

		}

		return archivo.toByteArray();
	}
	
	public Vector<Hashtable<String, Object>> parseExcelFile(byte[] fich) throws BusinessException {
		Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String, Object>>();
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(fich);
			HSSFWorkbook wb = new HSSFWorkbook(input);
			datos = parseWorkbook(wb);
		} catch (Exception e) {
			log.error("Se ha producido un error al obtener los datos del fichero excel obtenerDatosFichExcel tipo fich. File+", e);
			throw new BusinessException("Se ha producido un error al obtener los datos del fichero excel", e);
		}

		return datos;
	}
	/**
	 * Si viene lista de cabecera ordenaremos por esa lista.
	 * @param orderList orden
	 * @param datos
	 * @return
	 * @throws BusinessException
	 */
	private HSSFWorkbook createExcel(List<String> orderList, Vector<Hashtable<String, Object>> datos) throws BusinessException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		try {
			log.info("Generando plantilla excel generarLibroExcelUnaHoja");
			HSSFSheet hssfSheet = hssfWorkbook.createSheet("Hoja 1");
			int numeroFila = 0;
			int numeroColumna = 0;
			if (orderList != null && orderList.size() > 0) {
				log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque estilo de la cabecera");
				// Estilo de la cabecera
				HSSFCellStyle hssfCellStyleCabecera = hssfWorkbook.createCellStyle();
				hssfCellStyleCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
				hssfCellStyleCabecera.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
				hssfCellStyleCabecera.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				hssfCellStyleCabecera.setBottomBorderColor((short) 8);
				hssfCellStyleCabecera.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				hssfCellStyleCabecera.setLeftBorderColor((short) 8);
				hssfCellStyleCabecera.setBorderRight(HSSFCellStyle.BORDER_THIN);
				hssfCellStyleCabecera.setRightBorderColor((short) 8);
				hssfCellStyleCabecera.setBorderTop(HSSFCellStyle.BORDER_THIN);
				hssfCellStyleCabecera.setTopBorderColor((short) 8);
				
				log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque estilo de la cabecera");
	
				log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque crear fuente de la cabecera");
				// Crear la fuente de la cabecera
				HSSFFont hssfFont = hssfWorkbook.createFont();
				hssfFont.setFontName(HSSFFont.FONT_ARIAL);
				hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				hssfFont.setColor(HSSFColor.BLACK.index);
				log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque crear fuente de la cabecera");
	
				log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque aplicar fuente al estilo de la cabecera");
				// Aplicarle la fuente al estilo de la cabecera
				hssfCellStyleCabecera.setFont(hssfFont);
				HSSFRow hssfRow = hssfSheet.createRow(numeroFila);
				HSSFCell hssfCell = null;
				log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque aplicar fuente al estilo de la cabecera");
				
				log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque agregar los nombres de las cabeceras al excel");
				// Agregar los nombres de las cabeceras a el excel (tenemos una lista con las columnas de la cabecera)
				for (int i = 0; i < orderList.size(); i++) {
					hssfCell = hssfRow.createCell(numeroColumna);
					hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					HSSFRichTextString textCell = new HSSFRichTextString(orderList.get(i));
					hssfCell.setCellValue(textCell);
					log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque agregar los nombres de las cabeceras al excel");
	
					log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque agregar el estilo");
					// Agregar el estilo
					hssfCell.setCellStyle(hssfCellStyleCabecera);
					hssfSheet.autoSizeColumn((short) i);
					numeroColumna++;
					log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque agregar el estilo");
				}
				numeroFila++;
			}
	
			if (datos != null) {
				if (datos.size() > 0) {
					// Si creamos la cabecera del fichero con las keys del
					// vector de datos
					if (orderList == null || orderList.size() == 0) {
						
						log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque estilo de la cabecera");
						// Estilo de la cabecera
						HSSFCellStyle hssfCellStyleCabecera = hssfWorkbook.createCellStyle();
						hssfCellStyleCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
						hssfCellStyleCabecera.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
						hssfCellStyleCabecera.setBorderBottom(HSSFCellStyle.BORDER_THIN);
						hssfCellStyleCabecera.setBottomBorderColor((short) 8);
						hssfCellStyleCabecera.setBorderLeft(HSSFCellStyle.BORDER_THIN);
						hssfCellStyleCabecera.setLeftBorderColor((short) 8);
						hssfCellStyleCabecera.setBorderRight(HSSFCellStyle.BORDER_THIN);
						hssfCellStyleCabecera.setRightBorderColor((short) 8);
						hssfCellStyleCabecera.setBorderTop(HSSFCellStyle.BORDER_THIN);
						hssfCellStyleCabecera.setTopBorderColor((short) 8);
						log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque estilo de la cabecera");
	
						log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque crear fuente de la cabecera");
						// Crear la fuente de la cabecera
						HSSFFont hssfFont = hssfWorkbook.createFont();
						hssfFont.setFontName(HSSFFont.FONT_ARIAL);
						hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						hssfFont.setColor(HSSFColor.BLACK.index);
						log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque crear fuente de la cabecera");
	
						log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque aplicar fuente al estilo de la cabecera");
						// Aplicarle la fuente al estilo de la cabecera
						hssfCellStyleCabecera.setFont(hssfFont);
						HSSFRow hssfRow = hssfSheet.createRow(numeroFila); 
						HSSFCell hssfCell = null; // Creamos la celda
						log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque aplicar fuente al estilo de la cabecera");
	
	
						Hashtable<String, Object> datosCabH = datos.get(0);
						Enumeration<String> ecab = datosCabH.keys();
	
						while (ecab.hasMoreElements()) {
							hssfCell = hssfRow.createCell(numeroColumna);
							hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
							HSSFRichTextString textCell = new HSSFRichTextString(ecab.nextElement());
							hssfCell.setCellValue(textCell);
	
							// Agregar el estilo
							hssfCell.setCellStyle(hssfCellStyleCabecera);
							hssfSheet.autoSizeColumn((short) numeroColumna); 
							numeroColumna++;
						}
						numeroFila++;
					}
	
					log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque aplicar estilo del contenido");
					// Estilo del contenido
					HSSFCellStyle hssfCellStyleContenido = hssfWorkbook.createCellStyle();
					hssfCellStyleContenido.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
					hssfCellStyleContenido.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
					hssfCellStyleContenido.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					hssfCellStyleContenido.setBottomBorderColor((short) 8);
					hssfCellStyleContenido.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					hssfCellStyleContenido.setLeftBorderColor((short) 8);
					hssfCellStyleContenido.setBorderRight(HSSFCellStyle.BORDER_THIN);
					hssfCellStyleContenido.setRightBorderColor((short) 8);
					hssfCellStyleContenido.setBorderTop(HSSFCellStyle.BORDER_THIN);
					hssfCellStyleContenido.setTopBorderColor((short) 8);
					log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque aplicar estilo del contenido");
	
					log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque crear fuente contenido");
					// Crear la fuente del contenido
					HSSFFont hssfFontCont = hssfWorkbook.createFont();
					hssfFontCont.setFontName(HSSFFont.FONT_ARIAL);
					hssfFontCont.setColor(HSSFColor.BLACK.index);
					hssfCellStyleContenido.setFont(hssfFontCont);
					int numeroColumnaDatos = 0;
					log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque crear fuente contenido");
	
					log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque obtener elementos del vector");
					log.info("Tamaño datos: " + datos.size());
					// Obtenemos los elementos del vector. Cada elemento del vector es una fila y cada valor del Hastable una columna
					for (int f = 0; f < datos.size(); f++) {
						HSSFRow hssfRow = hssfSheet.createRow(numeroFila);
						log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque creacion columnas y celdas");
						// Creamos las columnas
						numeroColumnaDatos = 0;
						Hashtable<String, Object> datosCol = datos.get(f);
						int firstRow = 0;
						int firstCol = 0;
						int lastRow = 0;
						int lastCol = 0;
						
						for (int i = 0; i < orderList.size(); i++) {
							String key = orderList.get(i);
							Object valor = datosCol.get(key);
	
							HSSFCell hssfCell = hssfRow.createCell(numeroColumnaDatos);
	
							if(key == SigaConstants.FORMA_PAGO) {
								firstRow = 1;
								firstCol = 1;
								lastRow = 1;
								lastCol = 1;
							}else if(key == SigaConstants.ASISTENCIA) {
								firstRow = 1;
								firstCol = 2;
								lastRow = datos.size();
								lastCol = 2;
							}
							
							if ((valor != null) && (!valor.toString().isEmpty())) {
								// Si es un n�mero lo alineamos a la derecha y definimos la celda como num�rica
								if ((valor instanceof Double) || (valor instanceof Integer)) {
									hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									hssfCell.setCellValue(Double.parseDouble(valor.toString()));
									hssfCellStyleContenido.setAlignment(HSSFCellStyle.ALIGN_RIGHT); 
								}else if(valor instanceof String []) {
									DataValidationHelper dvHelper = hssfSheet.getDataValidationHelper();
									 DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint((String []) valor);
									  CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol,  lastCol);            
									  DataValidation validation = dvHelper.createValidation(
									    dvConstraint, addressList);
									  validation.setSuppressDropDownArrow(false);
									  hssfSheet.addValidationData(validation);
								}else {
									hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
									HSSFRichTextString textCell = new HSSFRichTextString(valor.toString());
									hssfCell.setCellValue(textCell);
									hssfCellStyleContenido.setAlignment(HSSFCellStyle.ALIGN_LEFT);
								}
							} else {
								hssfCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
							}
							//log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque creacion columnas y celdas");
	
							//log.info("GeneracionFicheroContabilidad --> createExcel(): Entrada bloque agregar el estilo");
							// Agregar el estilo
							hssfCell.setCellStyle(hssfCellStyleContenido);
							hssfSheet.autoSizeColumn((short) numeroColumnaDatos);
							numeroColumnaDatos++;
							//LOGGER.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque agregar el estilo");
						}
						numeroFila++;
					}
					log.info("GeneracionFicheroContabilidad --> createExcel(): Salida bloque obtener elementos del vector");
				}
			}
	
		} catch (Exception e) {
			log.error("Se ha producido un error al generar el fichero excel generarLibroExcelUnaHoja", e);
			throw new BusinessException("Se ha producido un error al generar el fichero excel generarLibroExcelUnaHoja", e);
		}
		return hssfWorkbook;
	}
	
	
}
