package org.itcgae.siga.com.services.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.com.documentos.DataMailMergeDataSource;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.MailMergeCleanupOptions;

@Service
public class GeneracionDocumentosServiceImpl implements IGeneracionDocumentosService{

	private Logger LOGGER = Logger.getLogger(GeneracionDocumentosServiceImpl.class);
	
	@Autowired
	private IPFDService pfdService;
	
	@Override
	public Document sustituyeDocumento(Document doc, HashMap<String, Object> dato) throws Exception{

		try {
			
			Set<String> claves=dato.keySet();
			
			for(String clave : claves){
				Object o = dato.get(clave);
                if (o instanceof List) {
                	List aux = (List)o;
                    doc = sustituyeRegionDocumento(doc,clave,aux);                   
                }
			}
			
			for(String clave : claves){
				Object datosMap = (Object) dato.get(clave);
				if (datosMap instanceof HashMap) {
					HashMap htRowDatosInforme = (HashMap) datosMap;
					doc = sustituyeDatos(doc, htRowDatosInforme);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("GeneracionDocumentosServiceImpl.sustituyeDocumento :: Error al sustituir los datos del documento", e);
			throw e;
		}
		return doc;
	}
	
	@Override
	public Document sustituyeRegionDocumento(Document doc, String region, List dato) throws Exception{
		DataMailMergeDataSource dataMerge = new DataMailMergeDataSource(region, dato);

		try {
			doc.getMailMerge().executeWithRegions(dataMerge);
		} catch (Exception e) {
			LOGGER.error("GeneracionDocumentosServiceImpl.sustituyeRegionDocumento :: Error al sustituir región", e);
			throw e;
		}
		return doc;
	}
	
	@Override
	public Document sustituyeDatos(Document doc, HashMap<String, Object> dato){

		try {
			
			Set<String> claves=dato.keySet();
			
			doc.getMailMerge().setCleanupOptions(MailMergeCleanupOptions.REMOVE_CONTAINING_FIELDS | MailMergeCleanupOptions.REMOVE_EMPTY_PARAGRAPHS  | MailMergeCleanupOptions.REMOVE_UNUSED_REGIONS | MailMergeCleanupOptions.REMOVE_UNUSED_FIELDS);
			
			DocumentBuilder builder=new DocumentBuilder(doc);
			
			if(claves.size() != 0) {
				
			for(String clave : claves){
				while(builder.moveToMergeField(clave))
				{
					Object o = dato.get(clave);
					try {
						if(o != null){
							builder.write(o.toString().trim());	
						}else{
							builder.write("");
						}					
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
			}
			}else {
				doc = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	@Override
	public DatosDocumentoItem grabaDocumento(Document doc, String pathfinal, String nombrefichero, boolean firmado) throws Exception{
		// nombrefichero incluye la extension .doc
		File archivo = null;
		DatosDocumentoItem documento = new DatosDocumentoItem();
		try {
			if(doc != null) {
				
			doc.save(pathfinal + nombrefichero);
			archivo = new File(pathfinal + nombrefichero);
			if (!archivo.exists())return null;

			byte[] byteArray = null;
			
			if(firmado){
				String docFirmado = pfdService.firmarPDF(archivo);
				byteArray = Base64.decodeBase64(docFirmado.getBytes());
			}else {
				byteArray = Files.readAllBytes(archivo.toPath());
			}			
			
			documento.setFileName(nombrefichero);
			documento.setDatos(byteArray);
			documento.setPathDocumento(pathfinal + nombrefichero);
			}else {
				documento = null;
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		
		return documento;
	}

	@Override
	public DatosDocumentoItem generarExcel(String pathPlantilla, String pathFicheroSalida, String nombreFicheroSalida, List<List<Map<String, Object>>> listaDatosExcel, ArrayList<String> nombresConsultasDatos) throws Exception {
		DatosDocumentoItem documento = new DatosDocumentoItem();
		boolean hayPlantilla = false;
		
		if(listaDatosExcel != null && listaDatosExcel.size() >= 1) {
			if(listaDatosExcel.get(0).size() >= 1) {
			Workbook workbook = null;
			
			//Creamos el libro de excel			
			// si existe la plantilla la cogemos, si no, generamos el excel desde cero
			
			File plantilla = new File(pathPlantilla);
			
			if(plantilla.exists()) {
				FileInputStream inputStream;
				try {
					inputStream = new FileInputStream(new File(pathPlantilla));
					workbook = new XSSFWorkbook(inputStream);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}
				
				hayPlantilla = true;
			}else {
				workbook = new XSSFWorkbook();
			}
			
			for(int i = 0; i< listaDatosExcel.size(); i++){
				
				// Cada lista de listaDatosExcel se crea en una hoja
				List<Map<String, Object>> registrosHoja = listaDatosExcel.get(i);
				Sheet sheet = null;
				
				if(!hayPlantilla) {
					//Creamos la hoja
					String nombreHoja = "Consulta " + i;
					try {
						if(nombresConsultasDatos != null && nombresConsultasDatos.size() > 0 && nombresConsultasDatos.get(i) != null) {
							nombreHoja = nombresConsultasDatos.get(i);
							if(nombreHoja.length() > 30)nombreHoja = nombreHoja.substring(0, 27) + "...";
						}
					}catch(Exception e) {
						LOGGER.error("Error al obtener el nombre de la consulta");
					}
					sheet = workbook.createSheet(nombreHoja);
				}else {
					sheet = workbook.getSheetAt(i);
				}
				
				int rowNum = 1;
		        int index = 0;
		        CellStyle headerCellStyle = null;
		        
		        List<String> columnsKey = new ArrayList<String>();				        
	        	
		        if(registrosHoja != null && registrosHoja.size() > 0) {
		        	for (String value : registrosHoja.get(0).keySet()) {
		    			columnsKey.add(value);
		    		}
		        }		        
	        	
				if(!hayPlantilla) {
					//Le aplicamos estilos a las cabeceras
					Font headerFont = workbook.createFont();
					headerFont.setBold(true);
					//headerFont.setItalic(true);
					headerFont.setFontHeightInPoints((short) 14);
					headerFont.setColor(IndexedColors.BLUE.getIndex());
					headerCellStyle = workbook.createCellStyle();
			        headerCellStyle.setFont(headerFont);	
			        
			        Row headerRow = sheet.createRow(0);
			        
			        for(String value : columnsKey) {
			        	Cell cell = headerRow.createCell(index);
		    			cell.setCellValue(value);
		    			cell.setCellStyle(headerCellStyle);
		    			index++;
			        }
				}
				
				for (Map<String, Object> map : registrosHoja) {
	            	
	            	Row row = sheet.createRow(rowNum++);
	            	int cell = 0;
	            	
	            	CellStyle cellStyle = workbook.createCellStyle();
	            	for(int j = 0; j < columnsKey.size(); j++){
	            		Object campo = map.get(columnsKey.get(j).trim());
	            		if(campo == null || campo.toString().trim() == ""){
	            			row.createCell(cell).setCellValue("");
	            		}else{
	            			Cell celda = row.createCell(cell);
	            			if (campo instanceof Number) {
								celda.setCellType(Cell.CELL_TYPE_NUMERIC);
								celda.setCellValue(Double.parseDouble(campo.toString()));
								cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);	    		    			
	    		    			celda.setCellStyle(cellStyle);
	            			} else if (campo instanceof Date) {
	            				celda.setCellType(Cell.CELL_TYPE_STRING);
	            				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
								XSSFRichTextString textCell = new XSSFRichTextString(SigaConstants.DATE_FORMAT_MIN_SEC.format(campo));
								celda.setCellValue(textCell);
								celda.setCellStyle(cellStyle);
							} else {
								celda.setCellType(Cell.CELL_TYPE_STRING);
								cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
								XSSFRichTextString textCell = new XSSFRichTextString(campo.toString());
								celda.setCellValue(textCell);
								celda.setCellStyle(cellStyle);
							}            			
	            		}
	            		cell++;
	            	}
	    		}
	            
	            for(int j = 0; j < index; j++) {
	                sheet.autoSizeColumn(j);
	            }
			}
			
			String nombreFicheroSalidaAux = nombreFicheroSalida;
			String ficheroSalida = pathFicheroSalida + nombreFicheroSalidaAux;
			FileOutputStream fileOut = new FileOutputStream(ficheroSalida);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			workbook.write(bos);
			workbook.write(fileOut);

	        fileOut.close();
	        workbook.close();		

	        documento.setDatos(bos.toByteArray());
	        documento.setFileName(nombreFicheroSalidaAux);
	        documento.setPathDocumento(pathFicheroSalida);
	        bos.close();
		}else {
			documento = null;
		}
		}else {
			documento = null;
		}
		
		return documento;
	}

}
