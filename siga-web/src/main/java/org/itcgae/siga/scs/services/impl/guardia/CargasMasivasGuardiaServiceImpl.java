package org.itcgae.siga.scs.services.impl.guardia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.FileDataDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosGuardiatem;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiaCalendarioItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.cen.services.impl.BusquedaColegiadosServiceImpl;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiado;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoKey;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenHistoricoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoSqlProvider;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBajasTemporalesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.guardia.CargasMasivasGuardiaService;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
import org.itcgae.siga.scs.services.oficio.IGestionInscripcionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Service
public class CargasMasivasGuardiaServiceImpl implements CargasMasivasGuardiaService{

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsInscripcionturnoMapper scsInscripcionturnoMapper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
	@Autowired
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;
	
	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasTurnoExtendsMapper;
	
	@Autowired
	private ScsBajasTemporalesExtendsMapper scsBajasTemporalesExtendsMapper;
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
	
	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	
	@Autowired
	private ScsInscripcionguardiaMapper scsInscripcionguardiaMapper;
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	
	@Autowired
	private CenHistoricoMapper cenHistoricoMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;

	
	@Autowired
	private ScsInscripcionguardiaExtendsMapper scsInscripcionguardiaExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;
	
	@Autowired
	private ScsGrupoguardiacolegiadoSqlProvider scsGrupoguardiacolegiadoSqlMapper;
	
	private static final String ESTADO_PENDIENTE = "5"; 
	@Override
//	public ResponseEntity<FileOutputStream> descargarModelo(HttpServletRequest request, String turnos, String guardias, String tipo) 
	public ResponseEntity<InputStreamResource> descargarModelo(HttpServletRequest request, String turnos, String guardias, String tipo) 
	throws IOException, EncryptedDocumentException, InvalidFormatException{
		

		
		//BUSCAMOS LAS INSCRPICIONES A PRESENTAR CON LOS PARAMETROS OBTENIDOS
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		File file;
//		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String, Object>>();

//		if(tipo.equals("IT")) {
//
//		    
//			
//			Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
////			CellRangeAddressList addressList = new CellRangeAddressList(1, 5, 0, 0);
////	        DVConstraint dvConstraint = DVConstraint
////	                .createExplicitListConstraint(new String[] { "ALTA", "BAJA" });
////	        HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
////	                dvConstraint);
////	        dataValidation.setSuppressDropDownArrow(false);
////	        sheet.addValidationData(dataValidation);
//			
//			
//			if(!turnos.equals("") && turnos!=null) {
////				   List<InscripcionesItem> listaInsTur = scsInscripcionesTurnoExtendsMapper.busquedaInscripcionesCMO(turnos.toString(), idInstitucion);
////				   TurnosItem turnosItem = new TurnosItem();
////				   turnosItem.setIdturno(turnos.toString());
////				   List<TurnosItem> listaInsTur = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion);
//			// 1. Se rellenan las filas con las inscripciones de turnos
////			for(InscripcionesItem ins : listaInsTur) {
//				String[] partes = turnos.split(",");
//			for(String parte : partes) {
//				//No se tienen en cuenta inscripciones de turno pendientes de alta
////				if(ins.getFechavalidacion()!=null) {
//					datosHashtable = new Hashtable<String, Object>();
//					datosHashtable.put(SigaConstants.IT_TURNO, parte);
//					datosHashtable.put(SigaConstants.IT_GUARDIA, "");
////					if(ins.getNcolegiado()!=null)datosHashtable.put(SigaConstants.IT_NCOLEGIADO, ins.getNcolegiado());
////					else  datosHashtable.put(SigaConstants.IT_NCOLEGIADO, "");
//					datosHashtable.put(SigaConstants.IT_NCOLEGIADO, "");
////					if(ins.getFechabaja()==null) {
////						datosHashtable.put(SigaConstants.IT_FECHAEFECTIVA, dateFormat.format(ins.getFechavalidacion()));
////						datosHashtable.put(SigaConstants.IT_TIPO, "ALTA");
////					}
////					else {
////						datosHashtable.put(SigaConstants.IT_FECHAEFECTIVA, dateFormat.format(ins.getFechabaja()));
////						datosHashtable.put(SigaConstants.IT_TIPO, "BAJA");
////					}
//					datosHashtable.put(SigaConstants.IT_FECHAEFECTIVA, "");
//					datosHashtable.put(SigaConstants.IT_TIPO, "");
//					datosHashtable.put(SigaConstants.IT_GRUPO, "");
//					datosHashtable.put(SigaConstants.IT_ORDEN, "");
//					datosVector.add(datosHashtable);
////				}
//			}
//			}
//			// 2. Se rellenan las filas con las inscripciones de guardias
//			if(guardias!=null && !guardias.equals("") ) {
//				List<CargaMasivaDatosITItem> listaInsG = scsGuardiasTurnoExtendsMapper.busquedaGuardiasCMO(turnos.toString(), guardias.toString(), idInstitucion);
//				
////				for(InscripcionesItem ins : listaInsG) {
//				for(CargaMasivaDatosITItem ins : listaInsG) {
//					//No se tienen en cuenta inscripciones de guardia pendientes de alta
////					if(ins.getFechavalidacion()!=null) {
//						datosHashtable = new Hashtable<String, Object>();
//						datosHashtable.put(SigaConstants.IT_TURNO, ins.getNombreTurno());
//						datosHashtable.put(SigaConstants.IT_GUARDIA, ins.getNombreGuardia());
////						if(ins.getNcolegiado()!=null)datosHashtable.put(SigaConstants.IT_NCOLEGIADO, ins.getNcolegiado());
////						else  datosHashtable.put(SigaConstants.IT_NCOLEGIADO, "");
//						datosHashtable.put(SigaConstants.IT_NCOLEGIADO, "");
////						if(ins.getFechabaja()==null) {
////							datosHashtable.put(SigaConstants.IT_FECHAEFECTIVA, dateFormat.format(ins.getFechavalidacion()));
////							datosHashtable.put(SigaConstants.IT_TIPO, "ALTA");
////						}
////						else {
////							datosHashtable.put(SigaConstants.IT_FECHAEFECTIVA, dateFormat.format(ins.getFechabaja()));
////							datosHashtable.put(SigaConstants.IT_TIPO, "BAJA");
////						}
//						datosHashtable.put(SigaConstants.IT_FECHAEFECTIVA, "");
//						datosHashtable.put(SigaConstants.IT_TIPO, "");
//						datosHashtable.put(SigaConstants.IT_GRUPO, ins.getGrupo());
//						datosHashtable.put(SigaConstants.IT_ORDEN, ins.getOrden());
//						datosVector.add(datosHashtable);
////					}
//				}
//			}
//			// 3. Crea el fichero excel
//			file = createExcelFile(SigaConstants.CAMPOSMODEL_IT, datosVector, tipo);
//		}
//		else {
//			//List<InscripcionesItem> listaBajas = scsInscripcionesTurnoExtendsMapper.busquedaInscripcionesCMO(turnos.toString(), idInstitucion);
//		    
//			
//			Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
//	
//			
//			// 1. Se rellenan las filas con las inscripciones de turnos
//			//for(InscripcionesItem baja : listaBajas) {
//			datosHashtable = new Hashtable<String, Object>();
//			datosHashtable.put(SigaConstants.BT_NCOLEGIADO, "");
//			datosHashtable.put(SigaConstants.BT_NIF, "");
//			datosHashtable.put(SigaConstants.BT_TIPO, "");
//			datosHashtable.put(SigaConstants.BT_MOTIVO, "");
//			datosHashtable.put(SigaConstants.BT_FECHAI, "");
//			datosHashtable.put(SigaConstants.BT_FECHAF, "");
//			datosVector.add(datosHashtable);
//				
//			//}
//			// 3. Crea el fichero excel
//			file = createExcelFile(SigaConstants.CAMPOSMODEL_BT, datosVector, tipo);
//		}
		
			
		// 4. Se convierte el fichero en array de bytes para enviarlo al front
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			if(tipo.equals("I")) {
				file = newModelI(turnos, guardias, idInstitucion);
			}else if (tipo.equals("GC")) {
				file = newModelGC(turnos, guardias, idInstitucion);
			}
			else {
				file = newModelC(turnos, guardias, idInstitucion);
			}
				
			fileStream = new FileInputStream(file);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		ResponseEntity<FileOutputStream> res = null;
//		try {
//			
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//			
//			if(tipo.equals("IT")) res = new ResponseEntity<FileOutputStream>(newModelIT(turnos, guardias, idInstitucion), headers, HttpStatus.OK);
//			else res = new ResponseEntity<FileOutputStream>(newModelBT(), headers, HttpStatus.OK);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		LOGGER.info("descargarModelo() -> Salida del servicio para generar el modelo de inscripciones o bajas temporales");
	
		return res;
	}
	
	public File  newModelI(String turnos, String guardias, Short idInstitucion) throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb =  new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Modelo Inscripciones");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		//CABECERA
		for(String p :SigaConstants.CAMPOSMODEL_I) {
			if(row.getLastCellNum()==-1)row.createCell(row.getLastCellNum()+1).setCellValue(p);
			else row.createCell(row.getLastCellNum()).setCellValue(p);
			sheet.autoSizeColumn(row.getLastCellNum()-1);
		}
		
		String[] gparts = guardias.split(",");
		String[] tparts = turnos.split(",");
		String[] listTurnos = new String[gparts.length];
		
		if(!guardias.equals("") ) {
//			try {
			for (int i=0; i<gparts.length; i++) {
				
				row = sheet.createRow(i+1);
				//Busqueda para obtener turno asociado a la guardia y su nombre
				List<CargaMasivaDatosITItem> listGu = scsGuardiasTurnoExtendsMapper.searchNombreTurnoGuardia(idInstitucion.toString(), gparts[i]);
				if (listGu.size() != 0) {
				listTurnos[i] = listGu.get(0).getNombreTurno();
				row.createCell(0).setCellValue(listGu.get(0).getNombreTurno());
				row.createCell(1).setCellValue(gparts[i]);
				row.createCell(4);
				}
			}
//			}
//			catch (Exception e) {
//				throw new BusinessException("Error al buscar asociacion de guardia" + e.toString());
//			}
			//Eliminamos de los turnos seleccionados los turnos que tienen guardia seleccionada
			
			List<String> union = new ArrayList<String>(Arrays.asList(tparts));
			union.addAll(Arrays.asList(listTurnos));
			// Prepare an intersection
			List<String> intersection = new ArrayList<String>(Arrays.asList(tparts));
			intersection.retainAll(Arrays.asList(listTurnos));
			// Subtract the intersection from the union
			union.removeAll(intersection);
			tparts= union.toArray(new String[0]);
		}
		if(tparts.length>0) {
			if(!tparts[0].equals("")) {
				for(String t: tparts) {
	//				ScsTurnoKey key = new ScsTurnoKey();
	//				key.setIdinstitucion(idInstitucion);
	//				key.setIdturno(Integer.parseInt(t));
	//				scsTurnoMapper.selectByPrimaryKey(key);
					row = sheet.createRow(sheet.getLastRowNum()+1);
					row.createCell(0).setCellValue(t);
					row.createCell(4);
				}
			}
		}
		
		//Creamos desplegable
		if(sheet.getLastRowNum()>0) {
			CellRangeAddressList addressList = new CellRangeAddressList(
		  1, sheet.getLastRowNum()+1, 4, 4);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(
			  new String[]{"ALTA", "BAJA"});
			DataValidation dataValidation = new HSSFDataValidation
			  (addressList, dvConstraint);
			dataValidation.setSuppressDropDownArrow(false);
			sheet.addValidationData(dataValidation);
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
//		InputStream is = null;
//		try {
//	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	        wb.write(bos);
//	        byte[] barray = bos.toByteArray();
//	        is = new ByteArrayInputStream(barray);
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
		File returnFile = null;
		try {
			File tmpFile = File.createTempFile("xls","");
			returnFile = new File(tmpFile.getParent()+File.separator+"ModeloCargaI"+".xls");
			returnFile.createNewFile();
			tmpFile.delete();
			FileOutputStream archivo = new FileOutputStream(returnFile);
			wb.write(archivo);
			archivo.flush();
			archivo.close();
		} catch (Exception e) {
			throw new BusinessException("Error al crear el fichero Excel" + e.toString());
		}
//		FileOutputStream fileOut = null;
//		try {
//			fileOut = new FileOutputStream("ModeloDatosIT.xls");
//		    wb.write(fileOut);
//		    fileOut.close();
//		    wb.close();
//		}
//		 catch (IOException e) {
//	        e.printStackTrace();
//	    }
		return returnFile;
		
	}
	
	
	public File  newModelGC(String turnos, String guardias, Short idInstitucion) throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb =  new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Modelo Grupos en cola");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		//CABECERA
		for(String p :SigaConstants.CAMPOSMODEL_GC) {
			if(row.getLastCellNum()==-1)row.createCell(row.getLastCellNum()+1).setCellValue(p);
			else row.createCell(row.getLastCellNum()).setCellValue(p);
			sheet.autoSizeColumn(row.getLastCellNum()-1);
		}
		
		String[] gparts = guardias.split(",");
		String[] tparts = turnos.split(",");
		String[] listTurnos = new String[gparts.length];
		
		if(!guardias.equals("") ) {
//			try {
			for (int i=0; i<gparts.length; i++) {
				
				row = sheet.createRow(i+1);
				//Busqueda para obtener turno asociado a la guardia y su nombre
				List<CargaMasivaDatosITItem> listGu = scsGuardiasTurnoExtendsMapper.searchNombreTurnoGuardia(idInstitucion.toString(), gparts[i]);
				listTurnos[i] = listGu.get(0).getNombreTurno();
				row.createCell(0).setCellValue(listGu.get(0).getNombreTurno());
				row.createCell(1).setCellValue(gparts[i]);
				row.createCell(4);
			}
//			}
//			catch (Exception e) {
//				throw new BusinessException("Error al buscar asociacion de guardia" + e.toString());
//			}
			//Eliminamos de los turnos seleccionados los turnos que tienen guardia seleccionada
			
			List<String> union = new ArrayList<String>(Arrays.asList(tparts));
			union.addAll(Arrays.asList(listTurnos));
			// Prepare an intersection
			List<String> intersection = new ArrayList<String>(Arrays.asList(tparts));
			intersection.retainAll(Arrays.asList(listTurnos));
			// Subtract the intersection from the union
			union.removeAll(intersection);
			tparts= union.toArray(new String[0]);
		}
		if(tparts.length>0) {
			if(!tparts[0].equals("")) {
				for(String t: tparts) {
	//				ScsTurnoKey key = new ScsTurnoKey();
	//				key.setIdinstitucion(idInstitucion);
	//				key.setIdturno(Integer.parseInt(t));
	//				scsTurnoMapper.selectByPrimaryKey(key);
					row = sheet.createRow(sheet.getLastRowNum()+1);
					row.createCell(0).setCellValue(t);
					row.createCell(4);
				}
			}
		}
		
		//Creamos desplegable
		if(sheet.getLastRowNum()>0) {
			CellRangeAddressList addressList = new CellRangeAddressList(
		  1, sheet.getLastRowNum()+1, 4, 4);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(
			  new String[]{"ALTA", "BAJA"});
			DataValidation dataValidation = new HSSFDataValidation
			  (addressList, dvConstraint);
			dataValidation.setSuppressDropDownArrow(false);
			sheet.addValidationData(dataValidation);
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
//		InputStream is = null;
//		try {
//	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	        wb.write(bos);
//	        byte[] barray = bos.toByteArray();
//	        is = new ByteArrayInputStream(barray);
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
		File returnFile = null;
		try {
			File tmpFile = File.createTempFile("xls","");
			returnFile = new File(tmpFile.getParent()+File.separator+"ModeloCargaIT"+".xls");
			returnFile.createNewFile();
			tmpFile.delete();
			FileOutputStream archivo = new FileOutputStream(returnFile);
			wb.write(archivo);
			archivo.flush();
			archivo.close();
		} catch (Exception e) {
			throw new BusinessException("Error al crear el fichero Excel" + e.toString());
		}
//		FileOutputStream fileOut = null;
//		try {
//			fileOut = new FileOutputStream("ModeloDatosIT.xls");
//		    wb.write(fileOut);
//		    fileOut.close();
//		    wb.close();
//		}
//		 catch (IOException e) {
//	        e.printStackTrace();
//	    }
		return returnFile;
		
	}
	
	public File  newModelC(String turnos, String guardias, Short idInstitucion) throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb =  new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Modelo Calendarios");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		//CABECERA
		for(String p :SigaConstants.CAMPOSMODEL_C) {
			if(row.getLastCellNum()==-1)row.createCell(row.getLastCellNum()+1).setCellValue(p);
			else row.createCell(row.getLastCellNum()).setCellValue(p);
			sheet.autoSizeColumn(row.getLastCellNum()-1);
		}
		
		String[] gparts = guardias.split(",");
		String[] tparts = turnos.split(",");
		String[] listTurnos = new String[gparts.length];
		
		
		
		//El turno, la guardia y el número de colegiado deben existir en el colegio.
		
		//Si la guardia de colegiado ya existe en el sistema, dará error.
		//Si las fechas indicadas no se corresponden con la forma de generación del calendario, dará error.
		//Si el colegiado no está inscrito en la guardia para la fecha indicada, dará error. Tener en cuenta las fechas de inscripción efectiva de alta a futuro.
		//Al terminar las comprobaciones, se realizará la comprobación de las fechas dentro del calendario y se creará el calendario programado.
		//Entonces para cada línea correcta, se creará una guardia de colegiado con sus datos.

		
		if(!guardias.equals("") ) {
//			try {
			for (int i=0; i<gparts.length; i++) {
				
				row = sheet.createRow(i+1);
				//Busqueda para obtener turno asociado a la guardia y su nombre
				List<CargaMasivaDatosITItem> listGu = scsGuardiasTurnoExtendsMapper.searchNombreTurnoGuardiaNoAbrev(idInstitucion.toString(), gparts[i]);
				listTurnos[i] = listGu.get(0).getNombreTurno();
				row.createCell(0).setCellValue(listGu.get(0).getNombreTurno());
				row.createCell(1).setCellValue(gparts[i]);
				row.createCell(4);
			}

			//Eliminamos de los turnos seleccionados los turnos que tienen guardia seleccionada
			
			List<String> union = new ArrayList<String>(Arrays.asList(tparts));
			union.addAll(Arrays.asList(listTurnos));
			// Prepare an intersection
			List<String> intersection = new ArrayList<String>(Arrays.asList(tparts));
			intersection.retainAll(Arrays.asList(listTurnos));
			// Subtract the intersection from the union
			union.removeAll(intersection);
			tparts= union.toArray(new String[0]);
		}
		if(tparts.length>0) {
			if(!tparts[0].equals("")) {
				for(String t: tparts) {
					row = sheet.createRow(sheet.getLastRowNum()+1);
					row.createCell(0).setCellValue(t);
					row.createCell(4);
				}
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		

		File returnFile = null;
		try {
			File tmpFile = File.createTempFile("xls","");
			returnFile = new File(tmpFile.getParent()+File.separator+"ModeloCargaC"+".xls");
			returnFile.createNewFile();
			tmpFile.delete();
			FileOutputStream archivo = new FileOutputStream(returnFile);
			wb.write(archivo);
			archivo.flush();
			archivo.close();
		} catch (Exception e) {
			throw new BusinessException("Error al crear el fichero Excel" + e.toString());
		}

		return returnFile;
		
	}

	
	
	@Override
	@Transactional
	public DeleteResponseDTO uploadFileI(HttpServletRequest request, FileDataDTO body) throws IllegalStateException, IOException {
		LOGGER.info("uploadFileIT() -> Entrada al servicio para subir un archivo");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
		LOGGER.debug("uploadFileI() -> Coger archivo del body");
//		Iterator<String> itr = request.getFileNames();
//		MultipartFile file = request.getFile(itr.next());
		MultipartFile file = body.getFile();
		String nombreFichero = file.getName();
		//Esta fecha será la que se pondrá en todas las inscripciones cargadas desde fichero
		String fechaSolicitud = body.getDatos()[0];
		
		
		
		
		// Extraer la información del excel
		LOGGER.debug("uploadFileI() -> Extraer los datos del archivo");
		Vector<Hashtable<String, Object>> datos = ExcelHelper.parseExcelFile(file.getBytes());

		CenCargamasiva cenCargamasivacv = new CenCargamasiva();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"uploadFileI() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"uploadFileI() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);
				//•	El turno, la guardia y el número de colegiado deben existir en el colegio.
				
				
				List<CargaMasivaDatosITItem> cargaMasivaDatosITItems = parseExcelFileI(datos, usuario);
				
				
				for (CargaMasivaDatosITItem cargaMasivaDatosITItem : cargaMasivaDatosITItems) {
					
					int result = -1;
					
					//Si no se ha detectado errores leyendo el excel introducido
					if (cargaMasivaDatosITItem.getErrores() == null) {
						int z=1;
						//En el caso que se haya introducido un tipo alta
						if(cargaMasivaDatosITItem.getTipo()=="ALTA") {
							
							TurnosItem t = new TurnosItem();
							t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
							List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
								
							ScsInscripcionguardia guardia = new ScsInscripcionguardia();

							guardia.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
							guardia.setFechavalidacion(cargaMasivaDatosITItem.getFechaEfectiva());
							guardia.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							guardia.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							guardia.setIdinstitucion(idInstitucion);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());
								
							//Se comprueba si se ha introducido una guardia especifica. Si no es asi, se realiza con todos las guardias del turno.
							if(cargaMasivaDatosITItem.getIdGuardia().equals(null) || cargaMasivaDatosITItem.getIdGuardia().equals("")) {
								int i=0;
								while(i < listGu.size() && result!=0) {
									GuardiasItem gu = listGu.get(i);
									guardia.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
												
									result = scsInscripcionguardiaMapper.insert(guardia);
									i++;
								}
								if(result==0) {
									errores += "Error insertando la peticion de la linea "+z+". <br/>";
									error.setDescription(errores);
									deleteResponseDTO.setError(error);

									registrosErroneos++;
								}
								//Se no se ha completado la insercion de todas las inscripciones, se eliminan las realizadas.
								if(i<listGu.size() && i!=0) {
									
									ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
									key.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
									key.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
									key.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
									
									key.setIdinstitucion(idInstitucion);
									for(int y = 0 ; y<(i-1) ; i++) {
										GuardiasItem gu = listGu.get(i);
										key.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
										scsInscripcionguardiaMapper.deleteByPrimaryKey(key);
									}
								}
							}
							else {

								guardia.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));
									
								result = scsInscripcionguardiaMapper.insert(guardia);
								
								if(result==0) {
									errores += "Error insertando la baja temporal de la linea "+z+". <br/>";
									error.setDescription(errores);
									deleteResponseDTO.setError(error);

									registrosErroneos++;
								}
							}
						}
						//En el caso que se haya introducido una inscripcion de baja
						else {
							TurnosItem t = new TurnosItem();
							t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
							List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
									
								ScsInscripcionguardia guardia = new ScsInscripcionguardia();
									
								guardia.setFechabaja(cargaMasivaDatosITItem.getFechaEfectiva());
								guardia.setFechasolicitudbaja(cargaMasivaDatosITItem.getFechaEfectiva());
								guardia.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
								guardia.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
								guardia.setIdinstitucion(idInstitucion);
								guardia.setFechamodificacion(new Date());
								guardia.setUsumodificacion(usuario.getIdusuario());
									
								//Se comprueba si se ha introducido una guardia especifica. Si no es asi, se realiza con todos las guardias del turno.
								if(cargaMasivaDatosITItem.getIdGuardia().equals(null) || cargaMasivaDatosITItem.getIdGuardia().equals("")) {
									int i=0;
									while(i < listGu.size() && result!=0) {
										
										GuardiasItem gu = listGu.get(i);
											
										guardia.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
													
										result = scsInscripcionguardiaMapper.updateByPrimaryKeySelective(guardia);
										i++;
									}
									if(result==0) {
										errores += "Error insertando la baja temporal de la linea "+z+". <br/>";
										error.setDescription(errores);
										deleteResponseDTO.setError(error);

										registrosErroneos++;
									}
									//Se no se ha completado la insercion de todas las inscripciones, se eliminan las realizadas.
									if(i<listGu.size() && i!=0) {
										
										ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
										key.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
										key.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
										key.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
										
										key.setIdinstitucion(idInstitucion);
										for(int y = 0 ; y<(i-1) ; i++) {
											GuardiasItem gu = listGu.get(i);
											key.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
											scsInscripcionguardiaMapper.deleteByPrimaryKey(key);
										}
									}
								}
								else {
	
									guardia.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));
										
									result = scsInscripcionguardiaMapper.updateByPrimaryKeySelective(guardia);
									if(result==0) {
										errores += "Error insertando la baja temporal de la linea "+z+". <br/>";
										error.setDescription(errores);
										deleteResponseDTO.setError(error);

										registrosErroneos++;
									}
								}
							}
							if (result == 1) {
								insertaCenHistoricoIT(cargaMasivaDatosITItem, usuario);
							}
	
						
					} else {
						errores += cargaMasivaDatosITItem.getErrores();
						error.setDescription(errores);
						deleteResponseDTO.setError(error);

						registrosErroneos++;
					}

					Hashtable<String, Object> e = new Hashtable<String, Object>();
					e = convertItemtoHashIT(cargaMasivaDatosITItem);
					// Guardar log
					datosLog.add(e);
				}
				
				
				if(cargaMasivaDatosITItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK); 
				}else {
					byte[] bytesLog = ExcelHelper.createExcelBytes(SigaConstants.CAMPOSLOGI, datosLog);

					cenCargamasivacv.setTipocarga("I");
					cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
					cenCargamasivacv.setNombrefichero(nombreFichero);
					cenCargamasivacv.setNumregistros(cargaMasivaDatosITItems.size());
					cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
					cenCargamasivacv.setFechamodificacion(new Date());
					cenCargamasivacv.setFechacarga(new Date());
					cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());

					Long idFile = uploadFileLog(file.getBytes(), cenCargamasivacv, false);
					Long idLogFile = uploadFileLog(bytesLog, cenCargamasivacv, true);

					cenCargamasivacv.setIdfichero(idFile);
					cenCargamasivacv.setIdficherolog(idLogFile);

					int result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
					
					if (result == 0) {
						error.setCode(SigaConstants.CODE_400);
						errores += "Error al insertar en cargas masivas";
					} 
					
					LOGGER.info("uploadFileIT() -> Salida al servicio para subir un archivo");
					deleteResponseDTO.setStatus(SigaConstants.OK);
					error.setDescription(errores);
					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
					error.setCode(SigaConstants.CODE_200); 
				}
			}
		}

		deleteResponseDTO.setError(error);

		return deleteResponseDTO;
	}


	private Long uploadFileLog(byte[] bytes, CenCargamasiva cenCargamasiva, boolean isLog){
		
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.GestionCargasMasivasOficioServiceImpl.uploadFile");
		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(cenCargamasiva.getIdinstitucion());
		ficheroVo.setDirectorio(directorioFichero);
		String nombreFicheroString = cenCargamasiva.getNombrefichero();
		ficheroVo.setNombre(nombreFicheroString); 
		ficheroVo.setDescripcion("Carga Masiva " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(cenCargamasiva.getIdinstitucion());
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension("xls");

		ficheroVo.setUsumodificacion(Integer.valueOf(cenCargamasiva.getUsumodificacion()));
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		if (isLog) {
			ficheroVo.setDescripcion("log_" + ficheroVo.getDescripcion());
			ficheroVo.setNombre("log_" + ficheroVo.getNombre());
		}

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		LOGGER.info(dateLog + ":fin.GestionCargasMasivasOficioServiceImpl.uploadFile");
		return ficheroVo.getIdfichero();
	}
	
	private String getDirectorioFichero(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.GestionCargasMasivasOficioServiceImpl.getDirectorioFichero");

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("cen.cargaExcel.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathCV = genPropertiesPath.get(0).getValor(); 
		
		StringBuffer directorioFichero = new StringBuffer(pathCV);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaCV");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.info(dateLog + ":fin.GestionCargasMasivasOficioServiceImpl.getDirectorioFichero");
		return directorioFichero.toString();
	}
	
	private List<CargaMasivaDatosITItem> parseExcelFileI(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario)
			throws BusinessException {

		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.GestionCargasMasivasOficioServiceImpl.parseExcelFileIT");

		List<CargaMasivaDatosITItem> masivaDatosITVos = new ArrayList<CargaMasivaDatosITItem>();
		CargaMasivaDatosITItem cargaMasivaDatosITItem = null;

//		Hashtable<String, SubtiposCVItem> tipoCvHashtable = new Hashtable<String, SubtiposCVItem>();
//		Hashtable<String, SubtiposCVItem> subtipo1CvHashtable = new Hashtable<String, SubtiposCVItem>();
//		Hashtable<String, SubtiposCVItem> subtipo2CvHashtable = new Hashtable<String, SubtiposCVItem>();
//		Hashtable<Long, String> personaHashtable = new Hashtable<Long, String>();

//		SubtiposCVItem tipoCVVo = null;
//		SubtiposCVItem subtipoCV1Vo = null;
//		SubtiposCVItem subtipoCV2Vo = null;


		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);

		List<Short> idInstituciones = new ArrayList<Short>();
		idInstituciones.add(idInstitucion);

		// Comprueba si la institucion que esta logeada es la 2000 si es diferente la
		// añade a la lista de instituciones
		if (idInstitucion != SigaConstants.IDINSTITUCION_2000) {
			idInstituciones.add(SigaConstants.IDINSTITUCION_2000);
		}

		StringBuffer errorLinea = null;
		int numLinea = 1;
		for (Hashtable<String, Object> hashtable : datos) {
			cargaMasivaDatosITItem = new CargaMasivaDatosITItem();

			cargaMasivaDatosITItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
//			SigaConstants.IT_TURNO, "Campo de texto. Obligatorio");
//			SigaConstants.IT_GUARDIA, "Campo de texto");
//			SigaConstants.IT_NCOLEGIADO, "Campo numérico. Obligatorio");
		
			// Turno
			if (hashtable.get(SigaConstants.TURNO) != null
					&& !hashtable.get(SigaConstants.TURNO).toString().equals("")) {
				cargaMasivaDatosITItem
						.setNombreTurno((String) hashtable.get(SigaConstants.TURNO));
				try {
					TurnosItem turnosItem = new TurnosItem();
					turnosItem.setAbreviatura(cargaMasivaDatosITItem.getNombreTurno());
					List<TurnosItem> listaTur = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion);
		
					cargaMasivaDatosITItem.setIdTurno(listaTur.get(0).getIdturno().toString());
				}
				catch (Exception e) {
					errorLinea.append("No se ha encontrado un turno con la introducida");
					cargaMasivaDatosITItem.setNombreTurno("Error");
				}
			}
			else{
				errorLinea.append("Es obligatorio introducir el turno.");
				cargaMasivaDatosITItem.setNombreTurno("Error");
			} 
			
			// Obtener guardia
			if (hashtable.get(SigaConstants.GUARDIA) != null
					&& !hashtable.get(SigaConstants.GUARDIA).toString().equals("")) {
				cargaMasivaDatosITItem.setNombreGuardia((String) hashtable.get(SigaConstants.GUARDIA));
				try {
					ScsGuardiasturnoExample guardiaExample = new ScsGuardiasturnoExample();
					guardiaExample.createCriteria().andNombreEqualTo(cargaMasivaDatosITItem.getNombreGuardia()).
						andIdinstitucionEqualTo(idInstitucion);
					List<ScsGuardiasturno> listaGuar = scsGuardiasTurnoExtendsMapper.selectByExample(guardiaExample);
		
					cargaMasivaDatosITItem.setIdTurno(listaGuar.get(0).getIdturno().toString());
				}
				catch (Exception e) {
					errorLinea.append("No se ha encontrado una guardia con el nombre introducido");
					cargaMasivaDatosITItem.setNombreGuardia("Error");
				}
			}
			
			// Obtener ncolegiado y extraemos id persona
			if (hashtable.get(SigaConstants.NCOLEGIADO) != null
					&& !hashtable.get(SigaConstants.NCOLEGIADO).toString().equals("")) {
				cargaMasivaDatosITItem.setnColegiado((String) hashtable.get(SigaConstants.NCOLEGIADO));
				
				try {
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosITItem.getnColegiado()).andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);
		
					cargaMasivaDatosITItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());
		
				} catch (Exception e) {
					errorLinea.append("No se ha encontrado una persona con el número de colegiado introducido en su institucion. ");
					cargaMasivaDatosITItem.setnColegiado("Error");
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir número de colegiado.");
				cargaMasivaDatosITItem.setnColegiado("Error");
			}
//			SigaConstants.IT_FECHAEFECTIVA, "Obligatorio");
//			SigaConstants.IT_TIPO, "puede tener los valores ‘ALTA’ o ‘BAJA’. Obligatorio");
//			SigaConstants.IT_GRUPO, "Número de grupo para las guardias por grupo");
//			SigaConstants.IT_ORDEN, "Dentro del grupo para las guardias por grupo. Obligatorio si se rellena el Grupo");
	
			
			// Comprobamos tipo 
			if (hashtable.get(SigaConstants.TIPO) != null
					&& !hashtable.get(SigaConstants.TIPO).toString().equals("")) {
				
				if(hashtable.get(SigaConstants.TIPO).toString().equals("ALTA") ||
						hashtable.get(SigaConstants.TIPO).toString().equals("BAJA")) {
					cargaMasivaDatosITItem.setTipo((String) hashtable.get(SigaConstants.TIPO));
				}
				else{
					errorLinea.append("El valor de tipo solo puede ser \"ALTA\" o \"BAJA\".");
					cargaMasivaDatosITItem.setTipo("Error");
				}
				
			}
			else {
				errorLinea.append("Es obligatorio introducir el tipo de petición a realizar.");
				cargaMasivaDatosITItem.setTipo("Error");
			}
			
			//Comprobamos fecha efectiva
			if (hashtable.get(SigaConstants.FECHAEFECTIVA) != null
					&& !hashtable.get(SigaConstants.FECHAEFECTIVA).toString().equals("")) {
				try {
					cargaMasivaDatosITItem.setFechaEfectiva(new SimpleDateFormat("dd-MM-yyyy").parse((String) hashtable.get(SigaConstants.FECHAEFECTIVA)));
				} catch (Exception e) {
					errorLinea.append("La fecha introducida no tiene un formato valido.");
					cargaMasivaDatosITItem.setFechaEfectiva(null);
				}
				
			}
			else {
				errorLinea.append("Es obligatorio introducir la fecha efectiva. ");
				cargaMasivaDatosITItem.setFechaEfectiva(null);
			}
			
//			El grupo y el orden sólo aplicarán para guardias por grupos, en otro caso, no se tendrán en cuenta.
//			• Orden no se puede repetir dentro del mismo grupo, tanto los existentes como los del fichero.


			//Comporbamos grupo y orden juntos al ser dependientes
//			if (hashtable.get(SigaConstants.IT_GRUPO) != null
//					&& !hashtable.get(SigaConstants.IT_GRUPO).toString().equals("")) {
//
//				cargaMasivaDatosITItem.setGrupo((String) hashtable.get(SigaConstants.IT_GRUPO));
//				//Comprobamos longitud de entrada
//				if(cargaMasivaDatosITItem.getGrupo().length()<=5) {
//					errorLinea.append("En valor en la columna \"GRUPO\" debe tener menos de seis cifras.");
//					cargaMasivaDatosITItem.setGrupo("Error");
//				}
//				else {
//					if(cargaMasivaDatosITItem.getIdGuardia().equals("") || cargaMasivaDatosITItem.getIdGuardia().equals(null)) {
//						TurnosItem t = new TurnosItem();
//						t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
//						List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
//						for(GuardiasItem gu : listGu) {
//						//Comprobamos que todas las guardias de un turno estan en un grupo. Se cambian los valores pero no se añade un error para que no impida su posible insercion
//							List<CargaMasivaDatosITItem> group = scsInscripcionguardiaExtendsMapper.searchGrupoGuardia(idInstitucion, gu.getIdGuardia());
//							if(group.isEmpty()) {
//								cargaMasivaDatosITItem.setOrden("Error");
//								cargaMasivaDatosITItem.setGrupo("Error");
//							}
//						}
//					}
//					else {
//						CheckGrupoOrden(cargaMasivaDatosITItem, errorLinea, idInstitucion, hashtable, datos);
//					}
//				}
//			}
			
			//Comprobamos según su tipo (ALTA o BAJA) y unicamente si no tiene otros errrores
			if(errorLinea.toString().isEmpty() && hashtable.get(SigaConstants.TIPO).toString().equals("ALTA")) {
				TurnosItem t = new TurnosItem();
				t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
				List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
				
				//Comprobamos el tipo de guardias asociadas al turno. En caso de ser obligatorias (valor 0), no se realizarian las inscripciones.  2- A elegir; 1-Todas o ninguna;
				if(!listGu.isEmpty()) {
					if(!listGu.get(0).getObligatoriedad().equals("0")) {
					
						ScsInscripcionturnoKey key2 = new ScsInscripcionturnoKey();
						key2.setIdinstitucion(idInstitucion);
						key2.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
						key2.setFechasolicitud(cargaMasivaDatosITItem.getFechaEfectiva());
						key2.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
						
						ScsInscripcionturno instur = new ScsInscripcionturno();
						
						instur = scsInscripcionturnoMapper.selectByPrimaryKey(key2);
						
						//Comprobamos si ya exite inscripcion a dicho turno. Si no existe, no se inscriben las guardias.
						if(instur != null) {
							
							ScsInscripcionguardiaKey key1= new ScsInscripcionguardiaKey();
							
							key1.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
							key1.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							key1.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							key1.setIdinstitucion(idInstitucion);
							
							//Si la tabla especifica alguna guardia, se comprueba solo una guardia 
							if(cargaMasivaDatosITItem.getIdGuardia()!=null && cargaMasivaDatosITItem.getIdGuardia()!="") {
								
								key1.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));
								
								//Se comprueba si la inscripción en la guardia ya existe de alta para la fecha efectiva indicada
								if(scsInscripcionguardiaMapper.selectByPrimaryKey(key1)==null) {
								
									//Se comprueba que la fecha efectiva es mayor o igual a la fecha efectiva del turno correspondiente.
									if(cargaMasivaDatosITItem.getFechaEfectiva().compareTo(instur.getFechavalidacion())<0) errorLinea.append("La fecha efectiva introducida es anterior a la fecha efectiva del turno asociado.");
								}
								else errorLinea.append("Ya existe una inscripcion a dicha guardia con las mismas caracteristicas.");
							}
							//Si no se introduce una guardia en especifico, se comprueban todas las guardias de dicho turno
							else {
								for(GuardiasItem gu : listGu) {
									
									key1.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
										
									//Se comprueba si la inscripción en la guardia ya existe de alta para la fecha efectiva indicada
									if(scsInscripcionguardiaMapper.selectByPrimaryKey(key1)!=null) errorLinea.append("Ya existe una inscripcion a la guardia "+gu.getNombre()+" con las mismas caracteristicas.");
								}
							}
							//Se comprueba que la fecha efectiva es mayor o igual a la fecha efectiva del turno correspondiente.
							if(cargaMasivaDatosITItem.getFechaEfectiva().compareTo(instur.getFechavalidacion())<0) errorLinea.append("La fecha efectiva introducida es anterior a la fecha efectiva del turno asociado.");
						
						}
						else errorLinea.append("El colegiado no esta inscrito en el turno indicado.");
					}
					else errorLinea.append("Las guardias en el turno son obligatorias por lo que no se pueden realizar la fila.");
				}
				else errorLinea.append("No se ha encontrado guardias asociadas al turno indicado.");
			}
			else if(errorLinea.toString().isEmpty() && hashtable.get(SigaConstants.TIPO).toString().equals("BAJA")) {
				
				TurnosItem t = new TurnosItem();
				t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
				List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
				
				//Comprobamos el tipo de guardias asociadas al turno. En caso de ser obligatorias (0) o Todas o ninguna (1), no se realizarian las inscripciones.  2- A elegir; 1-Todas o ninguna;
				if(!listGu.isEmpty()) {
					if(!listGu.get(0).getObligatoriedad().equals("0")) {
						if(!listGu.get(0).getObligatoriedad().equals("1")) {
					
							ScsInscripcionguardiaKey key1= new ScsInscripcionguardiaKey();
							
							key1.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							key1.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							key1.setIdinstitucion(idInstitucion);
							
							//Comprobamos si se introdujo una guardia o no.
							if(cargaMasivaDatosITItem.getIdGuardia().equals(null)) {
							
								for(GuardiasItem gu : listGu) {
		
									key1.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
									
									ScsInscripcionguardia ins = null;
									ins = scsInscripcionguardiaMapper.selectByPrimaryKey(key1);
									
									//Se comprueba si la inscripción en la guardia no existe
									if(ins==null) errorLinea.append("El colegiado no esta inscrito en la guardia "+gu.getNombre()+" del turno.");
									//Se comprueba si ya esta de baja
									else if(ins.getFechabaja()!=null) errorLinea.append("El colegiado ya dió de baja la inscripcion en la guardia "+gu.getNombre()+" del turno.");
								}
							}
							else {
								
								key1.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));
								
								ScsInscripcionguardia ins = null;
								ins = scsInscripcionguardiaMapper.selectByPrimaryKey(key1);
								
								//Se comprueba si la inscripción en la guardia no existe
								if(ins==null) errorLinea.append("El colegiado ya esta inscrito en la guardia "+cargaMasivaDatosITItem.getNombreGuardia()+" del turno.");
								//Se comprueba si ya esta de baja
								else if(ins.getFechabaja()==null) errorLinea.append("El colegiado ya dió de baja la inscripcion en la guardia "+cargaMasivaDatosITItem.getNombreGuardia()+" del turno.");
							
							}
						}
						else errorLinea.append("Las guardias en el turno son \"Todas o ninguna\" por lo que no se pueden realizar la fila.");
					}
					else errorLinea.append("Las guardias en el turno son \"Obligatorias\" por lo que no se pueden realizar la fila.");
				}
				else errorLinea.append("No se ha encontrado guardias asociadas al turno indicado.");
			}

			if (!errorLinea.toString().isEmpty()) {
				cargaMasivaDatosITItem
						.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaDatosITVos.add(cargaMasivaDatosITItem);
			numLinea++;
		}

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.parseExcelFileIT");
		return masivaDatosITVos;
	}
	
	private void CheckGrupoOrden (CargaMasivaDatosITItem cargaMasivaDatosITItem, StringBuffer errorLinea, Short idInstitucion, Hashtable<String, Object> hashtable, Vector<Hashtable<String, Object>> datos) {
		//Comprobamos que la guardia esta en un grupo. Se cambian los valores pero no se añade un error para que no impida su posible insercion
		List<CargaMasivaDatosITItem> group = scsInscripcionguardiaExtendsMapper.searchGrupoGuardia(idInstitucion, cargaMasivaDatosITItem.getIdGuardia());
		if(group.isEmpty()) {
			cargaMasivaDatosITItem.setOrden("Error");
			cargaMasivaDatosITItem.setGrupo("Error");
		}
		else {
			int colegr=0;
			//Comprobamos si el colegiado ya esta en el grupo.
			List<CargaMasivaDatosITItem> gr = scsInscripcionguardiaExtendsMapper.searchGrupo((String) hashtable.get(SigaConstants.GRUPO),idInstitucion);
			for(CargaMasivaDatosITItem g : gr) {
				if(g.getIdPersona().equals(cargaMasivaDatosITItem.getIdPersona())) colegr++;
			}
			if(colegr>0) {
				errorLinea.append("El colegiado ya esta en ese grupo.");
				cargaMasivaDatosITItem.setGrupo("Error");
			}
			else {
				if (hashtable.get(SigaConstants.ORDEN) != null
						&& !hashtable.get(SigaConstants.ORDEN).toString().equals("")) {
					//Comprobamos longitud de entrada
					if(cargaMasivaDatosITItem.getOrden().length()<=5) {
						errorLinea.append("El valor en la columna \"ORDEN\" debe tener menos de seis cifras.");
						cargaMasivaDatosITItem.setOrden("Error");
					}
					else {
						//Comprobamos que en el grupo no existe una entrada con el mismo orden
						int j=0;
						int i=0;
						while(i<gr.size() && j==0) {
							gr.get(i).getOrden().equals((String) hashtable.get(SigaConstants.ORDEN));
							i++;
						}
						if(j==0) {
							//Comprobamos que en el fichero no existe una entrada con el mismo orden y grupo.
							int z=0;
							while (z<datos.size() && j<=1) {
								Hashtable<String, Object> hashtable2 = datos.get(z);
								if(hashtable2.get(SigaConstants.ORDEN).equals((String) hashtable.get(SigaConstants.ORDEN))
										&& hashtable2.get(SigaConstants.GRUPO).equals((String) hashtable.get(SigaConstants.GRUPO)))j++;
								z++;
							}
							//Se tiene en cuenta que se encontra con linea que estamos revisando al leer el fichero entero
							if(j>1) {
								errorLinea.append("El fichero tiene repetida una linea con grupo "+hashtable.get(SigaConstants.GRUPO)+" con orden "+hashtable.get(SigaConstants.ORDEN)+".");
								cargaMasivaDatosITItem.setOrden("Error");
							}
							//Si pasa todas las comprobaciones se asignan los valores.
							else {
								cargaMasivaDatosITItem.setOrden((String) hashtable.get(SigaConstants.ORDEN));
								cargaMasivaDatosITItem.setGrupo((String) hashtable.get(SigaConstants.GRUPO));
							}
						}
						else {
							errorLinea.append("El grupo "+hashtable.get(SigaConstants.GRUPO)+" ya tiene una entrada con orden "+hashtable.get(SigaConstants.ORDEN)+".");
							cargaMasivaDatosITItem.setOrden("Error");
						}

					}
				}
				else {
					errorLinea.append("Es obligatorio introducir un valor en la columna \"ORDEN\" una vez se rellena la columna \"GRUPO\".");
					cargaMasivaDatosITItem.setOrden("Error");
				}
			}
		}
	}
	
	private Hashtable<String, Object> convertItemtoHashIT(CargaMasivaDatosITItem cargaMasivaDatosITItem) {
		Date dateLog = new Date();
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

		LOGGER.info(dateLog + ":inicio.GestionCargasMasivasOficioServiceImpl.convertItemtoHashIT");
		Hashtable<String, Object> e = new Hashtable<String, Object>();
				
		if (cargaMasivaDatosITItem.getnColegiado() != null) {
			e.put(SigaConstants.NCOLEGIADO, cargaMasivaDatosITItem.getnColegiado());
		}
		if (cargaMasivaDatosITItem.getIdTurno() != null) {
			e.put(SigaConstants.TURNO, cargaMasivaDatosITItem.getIdTurno());
		}
		if (cargaMasivaDatosITItem.getIdGuardia() != null) {
			e.put(SigaConstants.GUARDIA, cargaMasivaDatosITItem.getIdGuardia());
		}
		if (cargaMasivaDatosITItem.getTipo() != null) {
			e.put(SigaConstants.TIPO, cargaMasivaDatosITItem.getTipo());
		}
		if (cargaMasivaDatosITItem.getFechaEfectiva() != null) {
			String fechaEfectiva = df2.format(cargaMasivaDatosITItem.getFechaEfectiva());
			e.put(SigaConstants.FECHAEFECTIVA, fechaEfectiva);
		}

		if (cargaMasivaDatosITItem.getErrores() != null) {
			e.put(SigaConstants.ERRORES, cargaMasivaDatosITItem.getErrores());
		}
		
		LOGGER.info(dateLog + ":fin.GestionCargasMasivasOficioServiceImpl.convertItemtoHashIT");
		return e;
	}
	
	@Override
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector, String tipo)
			throws BusinessException {
		if (orderList == null && datosVector == null)
			throw new BusinessException("No hay datos para crear el fichero");
		if (orderList == null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile;
		if(tipo.equals("I")) {
			XLSFile = ExcelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroModeloI);
		}
		else if(tipo.equals("GC")){
			XLSFile = ExcelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroModeloGC);
		}else {
			XLSFile = ExcelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroModeloC);
		}
		return XLSFile;
	}
	
	private void insertaCenHistoricoIT(CargaMasivaDatosITItem cargaMasivaDatosITItem, AdmUsuarios usuario) 
			throws BusinessException{
		
		LOGGER.debug("Insertando en CEN_HISTORICO para el colegio " + usuario.getIdinstitucion() + ", idPersona "
				+ cargaMasivaDatosITItem.getIdPersona());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		CenHistorico cenHistorico = new CenHistorico();
		cenHistorico.setIdinstitucion(usuario.getIdinstitucion());
		cenHistorico.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
		cenHistorico.setFechaentrada(Calendar.getInstance().getTime());
		cenHistorico.setFechaefectiva(Calendar.getInstance().getTime());
		cenHistorico.setMotivo(null);
		cenHistorico.setIdtipocambio(SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		cenHistorico.setFechamodificacion(Calendar.getInstance().getTime());
		cenHistorico.setUsumodificacion(usuario.getIdusuario());

		NewIdDTO newIdDTO = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(
				String.valueOf(cargaMasivaDatosITItem.getIdPersona()), String.valueOf(usuario.getIdinstitucion()));
		if (newIdDTO == null) {
			cenHistorico.setIdhistorico((short) 1);
		} else {
			int newIdCv = Integer.parseInt(newIdDTO.getNewId()) + 1;
			cenHistorico.setIdhistorico((short) newIdCv);
		}

		StringBuffer descripcion = new StringBuffer();

		GenRecursosExample genRecursosExample = new GenRecursosExample();
		genRecursosExample.createCriteria().andIdrecursoEqualTo("historico.literal.registroNuevo")
				.andIdlenguajeEqualTo(usuario.getIdlenguaje());
		List<GenRecursos> genRecursos = genRecursosMapper.selectByExample(genRecursosExample);

		descripcion = descripcion.append(genRecursos.get(0).getDescripcion());

		descripcion.append("\n");
		descripcion.append("- FechaEfectiva: ");
		descripcion.append(cargaMasivaDatosITItem.getFechaEfectiva() != null
				? simpleDateFormat.format(cargaMasivaDatosITItem.getFechaEfectiva())
				: "");

		descripcion.append("\n");
		descripcion.append("- Turno: ");
		descripcion.append(cargaMasivaDatosITItem.getIdTurno() != null ? cargaMasivaDatosITItem.getIdTurno() : "");
		
		descripcion.append("\n");
		descripcion.append("- Idinstitucion: ");
		descripcion.append(
				cargaMasivaDatosITItem.getIdInstitucion() != null ? cargaMasivaDatosITItem.getIdInstitucion()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- IdGuardia: ");
		descripcion.append(
				cargaMasivaDatosITItem.getIdGuardia() != null ? cargaMasivaDatosITItem.getIdGuardia()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- Tipo: ");
		descripcion.append(
				cargaMasivaDatosITItem.getTipo() != null ? cargaMasivaDatosITItem.getTipo()
						: "");

		cenHistorico.setDescripcion(descripcion.toString());

		if (cenHistoricoMapper.insert(cenHistorico) != 1) {
			throw new BusinessException("No se ha insertado correctamente el registro en cenHistorico para el colegio "
					+ usuario.getIdinstitucion() + ", idPersona " + cargaMasivaDatosITItem.getIdPersona()
					+ " e idTipoCambio " + SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		}

	}


	@Override
	public DeleteResponseDTO uploadFileGC(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {
		LOGGER.info("uploadFileBT() -> Entrada al servicio para subir un archivo");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
		LOGGER.debug("uploadFileGC() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String nombreFichero = file.getOriginalFilename(); 

		// Extraer la información del excel
		LOGGER.debug("uploadFileGC() -> Extraer los datos del archivo");
		Vector<Hashtable<String, Object>> datos = ExcelHelper.parseExcelFile(file.getBytes());

		CenCargamasiva cenCargamasivacv = new CenCargamasiva();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"uploadFileGC() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"uploadFileGC() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);
				
				
				List<CargaMasivaDatosGuardiatem> cargaMasivaDatosBTItems = parseExcelFileGC(datos, usuario);
				
				
				for (CargaMasivaDatosGuardiatem cargaMasivaDatosGCItem : cargaMasivaDatosBTItems) {
					int i=1;
					int result = -1;
					
					//Si no se ha detectado errores leyendo el excel introducido
					if (cargaMasivaDatosGCItem.getErrores() == null) {
						
//						CenBajastemporales cenBajasTemporales = new CenBajastemporales();
//								
//						cenBajasTemporales.setIdpersona(Long.parseLong(cargaMasivaDatosBTItem.getIdPersona()));
//						cenBajasTemporales.setIdinstitucion(idInstitucion);
//						cenBajasTemporales.setFechadesde(cargaMasivaDatosBTItem.getFechaInicio());
//						cenBajasTemporales.setFechahasta(cargaMasivaDatosBTItem.getFechaFinal());
//						cenBajasTemporales.setFechamodificacion(new Date());
//						cenBajasTemporales.setTipo(cargaMasivaDatosBTItem.getTipo());
//						cenBajasTemporales.setUsumodificacion(usuario.getIdusuario());
//						cenBajasTemporales.setFechaestado(new Date());
//						cenBajasTemporales.setFechabt(new Date());
//						cenBajasTemporales.setDescripcion(cargaMasivaDatosBTItem.getMotivo());
//						cenBajasTemporales.setFechaalta(new Date());
//						cenBajasTemporales.setEliminado(0);
//						cenBajasTemporales.setValidado("1");
						
						// Carga de grupos en cola = asignar los grupos a las inscripciones de guardia. 
						String idGuardia = scsGuardiasturnoExtendsMapper.getIdGuardiaByName(cargaMasivaDatosGCItem.getIdGuardia());
						String idTurno = scsGuardiasturnoExtendsMapper.getIdTurnoByName(cargaMasivaDatosGCItem.getIdTurno());
						
						//Si no existe inscripción, la asignación de grupo, no puede crearse. 
						List<ScsInscripcionguardia> listInscrip = scsInscripcionguardiaExtendsMapper.getInscripcionByTurnoGuardiaNcolegiado(usuarios.get(0).getIdusuario().toString(), idTurno, idGuardia, cargaMasivaDatosGCItem.getnColegiado());
						listInscrip.forEach(inscripcion -> {
							ScsGrupoguardiacolegiado grupo = new ScsGrupoguardiacolegiado();
							grupo.setFechacreacion(new Date());
							grupo.setIdguardia(inscripcion.getIdguardia());
							grupo.setIdturno(inscripcion.getIdturno());
							grupo.setIdpersona(inscripcion.getIdpersona());
							grupo.setIdinstitucion(inscripcion.getIdinstitucion());
							grupo.setFechamodificacion(new Date());
							grupo.setFechasuscripcion(new Date());
							//grupo.setIdgrupoguardiacolegiado();
							grupo.setOrden(Integer.parseInt(cargaMasivaDatosGCItem.getOrden()));
							grupo.setIdgrupoguardia(Long.parseLong(cargaMasivaDatosGCItem.getGrupo()));
//							grupo.setUsucreacion(usucreacion);
//							grupo.setUsumodificacion(usumodificacion);
							scsGrupoguardiacolegiadoSqlMapper.insertSelective(grupo);
						});
						
						//Si se intenta añadir un grupo sin indicar una guardia, hay que buscar todas las inscripciones existentes de guardias 
						//pertenecientes al turno indicado para asignar el grupo (ya que no es obligatorio estar apuntado a todas las guardias de un turno). 
						
							if (listInscrip.size() == 0) {
							errores += "Error insertando los grupos en cola de la linea "+i+" debido a que no existen inscripciones . <br/>";
							error.setDescription(errores);
							deleteResponseDTO.setError(error);

							registrosErroneos++;
						}
						
					} else {
						errores += cargaMasivaDatosGCItem.getErrores();
						error.setDescription(errores);
						deleteResponseDTO.setError(error);

						registrosErroneos++;
					}

					Hashtable<String, Object> e = new Hashtable<String, Object>();
					e = convertItemtoHashC(cargaMasivaDatosGCItem);
					// Guardar log
					datosLog.add(e);
					i++;
				}
				
				
				if(cargaMasivaDatosBTItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK); 
				}else {
					byte[] bytesLog = ExcelHelper.createExcelBytes(SigaConstants.CAMPOSLOGGC, datosLog);

					cenCargamasivacv.setTipocarga("GC");
					cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
					cenCargamasivacv.setNombrefichero(nombreFichero);
					cenCargamasivacv.setNumregistros(cargaMasivaDatosBTItems.size());
					cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
					cenCargamasivacv.setFechamodificacion(new Date());
					cenCargamasivacv.setFechacarga(new Date());
					cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());

					Long idFile = uploadFileLog(file.getBytes(), cenCargamasivacv, false);
					Long idLogFile = uploadFileLog(bytesLog, cenCargamasivacv, true);

					cenCargamasivacv.setIdfichero(idFile);
					cenCargamasivacv.setIdficherolog(idLogFile);

					int result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
					
					if (result == 0) {
						error.setCode(SigaConstants.CODE_400);
						errores += "Error al insertar en cargas masivas";
					} 
					
					LOGGER.info("uploadFileGC() -> Salida al servicio para subir un archivo");
					deleteResponseDTO.setStatus(SigaConstants.OK);
					error.setDescription(errores);
					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
					error.setCode(SigaConstants.CODE_200); 
				}
			}
		}

		deleteResponseDTO.setError(error);

		return deleteResponseDTO;
	}
	
	
	@Override
	public DeleteResponseDTO uploadFileC(MultipartHttpServletRequest request, String fechaDesde, String fechaHasta, String observaciones)
			throws IllegalStateException, IOException {
		LOGGER.info("uploadFileBT() -> Entrada al servicio para subir un archivo");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
				LOGGER.debug("uploadFileC() -> Coger archivo del request");
				Iterator<String> itr = request.getFileNames();
				MultipartFile file = request.getFile(itr.next());
				String nombreFichero = file.getOriginalFilename(); 

				// Extraer la información del excel
				LOGGER.debug("uploadFileC() -> Extraer los datos del archivo");
				Vector<Hashtable<String, Object>> datos = ExcelHelper.parseExcelFile(file.getBytes());


		
		
		

		// Extraer la información del excel

		CenCargamasiva cenCargamasivacv = new CenCargamasiva();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"uploadFileC() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"uploadFileC() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);
				
				
				List<CargaMasivaDatosGuardiatem> cargaMasivaDatosBTItems = parseExcelFileC(datos, usuario);
				
				
				for (CargaMasivaDatosGuardiatem cargaMasivaDatosBTItem : cargaMasivaDatosBTItems) {
					int i=1;
					int result = -1;
					
					//Si no se ha detectado errores leyendo el excel introducido
					if (cargaMasivaDatosBTItem.getErrores() == null) {
						
						CenBajastemporales cenBajasTemporales = new CenBajastemporales();
								
						cenBajasTemporales.setIdpersona(Long.parseLong(cargaMasivaDatosBTItem.getIdPersona()));
						cenBajasTemporales.setIdinstitucion(idInstitucion);
						cenBajasTemporales.setFechadesde(cargaMasivaDatosBTItem.getFechaInicio());
						cenBajasTemporales.setFechahasta(cargaMasivaDatosBTItem.getFechaFinal());
						cenBajasTemporales.setFechamodificacion(new Date());
						cenBajasTemporales.setTipo(cargaMasivaDatosBTItem.getTipo());
						cenBajasTemporales.setUsumodificacion(usuario.getIdusuario());
						cenBajasTemporales.setFechaestado(new Date());
						cenBajasTemporales.setFechabt(new Date());
						cenBajasTemporales.setDescripcion(cargaMasivaDatosBTItem.getMotivo());
						cenBajasTemporales.setFechaalta(new Date());
						cenBajasTemporales.setEliminado(0);
						cenBajasTemporales.setValidado("1");
						
						
						
						//Comprobar que las fechas introducidas en el fichero están dentro del periodo 
						
						try {
							if (cenBajasTemporales.getFechadesde().before(new SimpleDateFormat("dd/MM/yyyy").parse(fechaDesde)) || cenBajasTemporales.getFechahasta().after(new SimpleDateFormat("dd/MM/yyyy").parse(fechaHasta))) {
								error.setCode(SigaConstants.CODE_400);
								errores += "Error debido a que las fechas no cumplen el rango establecido";
							}
							
							
							//Cumplimentar el calendario (programación de calendario) que se creará con todas las guardias del fichero subido.
							//Creamos un calendario con fechaDesde, fechaHasta y observaciones
							
							
//						CenBajastemporalesMapper cenBajasTemporalesMapper;

							if (!fechaDesde.isEmpty() && fechaDesde != null && !fechaHasta.isEmpty() && fechaHasta != null) {
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								String today = formatter.format(new Date());
								int numGuards = scsGuardiasturnoExtendsMapper.getGuardiasToProgByDates(fechaDesde, fechaHasta, idInstitucion.toString());
								String idGuardia = scsGuardiasturnoExtendsMapper.getIdGuardiaByName(cargaMasivaDatosBTItem.getIdGuardia());
								String idTurno = scsGuardiasturnoExtendsMapper.getIdTurnoByName(cargaMasivaDatosBTItem.getIdTurno());
								if (numGuards <= 0) {
									error.setCode(204);
									error.setDescription("No existen guardias asociadas a esta programación");
									error.setMessage("No existen guardias asociadas a esta programación");
									deleteResponseDTO.setError(error);
								}else {
									DatosCalendarioProgramadoItem calendarioItem = new DatosCalendarioProgramadoItem();
									calendarioItem.setEstado(ESTADO_PENDIENTE);
									calendarioItem.setNumGuardias(Integer.toString(numGuards));
									calendarioItem.setIdGuardia(idGuardia);
									calendarioItem.setIdTurno(idTurno);
									calendarioItem.setFechaDesde(new SimpleDateFormat("dd/MM/yyyy").format(cargaMasivaDatosBTItem.getFechaInicio()));
									calendarioItem.setFechaHasta(new SimpleDateFormat("dd/MM/yyyy").format(cargaMasivaDatosBTItem.getFechaFinal()));
									calendarioItem.setObservaciones(observaciones);
									//generamos programacion por cada guardia
									//ScsProgCalendariosSqlProvider.insertSelective(ScsProgCalendarios record)
									String  res = scsGuardiasturnoExtendsMapper.generateCalendarioProgramado(calendarioItem,  idInstitucion.toString(), today, usuario.getIdusuario().toString());

										String idProgramacion = scsGuardiasturnoExtendsMapper.getLastProgramacion(idInstitucion.toString());
										//generamos un calendario por cada guardia asociada a esa programacion	
										GuardiaCalendarioItem item = new GuardiaCalendarioItem();
										item.setIdGuardia(idGuardia);
										item.setIdTurno(idTurno);
										item.setOrden("1");
										String  res2 = scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias( null,  null,  null,  observaciones,  idTurno,  
												idGuardia,  fechaHasta,  fechaDesde,  idProgramacion,  idInstitucion.toString(),  null,  today,  
												null,  null,  usuarios.get(0).getIdusuario().toString());
										
										String  idCalendario = scsGuardiasturnoExtendsMapper.getLastCalendar(idInstitucion.toString());
										//insertamos historico de calendario	
										scsGuardiasturnoExtendsMapper.insertHistoricoCalendario( idCalendario,  null,  idInstitucion.toString(),  today,  item,  usuarios.get(0).getIdusuario().toString());

										}
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(result==0) {
							errores += "Error insertando calendario (a tarvés de cargas masivas) de la linea "+i+". <br/>";
							error.setDescription(errores);
							deleteResponseDTO.setError(error);

							registrosErroneos++;
						}
						
					} else {
						errores += cargaMasivaDatosBTItem.getErrores();
						error.setDescription(errores);
						deleteResponseDTO.setError(error);

						registrosErroneos++;
					}

					Hashtable<String, Object> e = new Hashtable<String, Object>();
					e = convertItemtoHashC(cargaMasivaDatosBTItem);
					// Guardar log
					datosLog.add(e);
					i++;
				}
				
				
				if(cargaMasivaDatosBTItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK); 
				}else {
					byte[] bytesLog = ExcelHelper.createExcelBytes(SigaConstants.CAMPOSLOGGC, datosLog);

					cenCargamasivacv.setTipocarga("BT");
					cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
					cenCargamasivacv.setNombrefichero(nombreFichero);
					cenCargamasivacv.setNumregistros(cargaMasivaDatosBTItems.size());
					cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
					cenCargamasivacv.setFechamodificacion(new Date());
					cenCargamasivacv.setFechacarga(new Date());
					cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());

					Long idFile = uploadFileLog(file.getBytes(), cenCargamasivacv, false);
					Long idLogFile = uploadFileLog(bytesLog, cenCargamasivacv, true);

					cenCargamasivacv.setIdfichero(idFile);
					cenCargamasivacv.setIdficherolog(idLogFile);

					int result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
					
					if (result == 0) {
						error.setCode(SigaConstants.CODE_400);
						errores += "Error al insertar en cargas masivas";
					} 
					
					LOGGER.info("uploadFileBT() -> Salida al servicio para subir un archivo");
					deleteResponseDTO.setStatus(SigaConstants.OK);
					error.setDescription(errores);
					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
					error.setCode(SigaConstants.CODE_200); 
				}
			}
		}

		deleteResponseDTO.setError(error);

		return deleteResponseDTO;
	}
	
	private Hashtable<String, Object> convertItemtoHashC(CargaMasivaDatosGuardiatem cargaMasivaDatosBTItem) {
		Date dateLog = new Date();
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

		LOGGER.info(dateLog + ":inicio.GestionCargasMasivasOficioServiceImpl.CargaMasivaDatosCItem");
		Hashtable<String, Object> e = new Hashtable<String, Object>();
				
		if (cargaMasivaDatosBTItem.getnColegiado() != null) {
			e.put(SigaConstants.NCOLEGIADO, cargaMasivaDatosBTItem.getnColegiado());
		}
		if (cargaMasivaDatosBTItem.getNif() != null) {
			e.put(SigaConstants.NIF, cargaMasivaDatosBTItem.getNif());
		}
		if (cargaMasivaDatosBTItem.getTipo() != null) {
			e.put(SigaConstants.TIPO, cargaMasivaDatosBTItem.getTipo());
		}
		if (cargaMasivaDatosBTItem.getFechaInicio() != null) {
			String fechaInicio = df2.format(cargaMasivaDatosBTItem.getFechaInicio());
			e.put(SigaConstants.FECHAI, fechaInicio);
		}
		if (cargaMasivaDatosBTItem.getIdGuardia() != null) {
			e.put(SigaConstants.GUARDIA, cargaMasivaDatosBTItem.getIdGuardia());
		}
		if (cargaMasivaDatosBTItem.getIdTurno() != null) {
			e.put(SigaConstants.TURNO, cargaMasivaDatosBTItem.getIdTurno());
		}
		if (cargaMasivaDatosBTItem.getFechaFinal() != null) {
			String fechaFinal = df2.format(cargaMasivaDatosBTItem.getFechaFinal());
			e.put(SigaConstants.FECHAF, fechaFinal);
		}
		if (cargaMasivaDatosBTItem.getErrores() != null) {
			e.put(SigaConstants.ERRORES, cargaMasivaDatosBTItem.getErrores());
		}
		
		LOGGER.info(dateLog + ":fin.CargasMasivasGuardiaServiceImpl.CargaMasivaDatosCItem");
		return e;
	}


	private List<CargaMasivaDatosGuardiatem> parseExcelFileC(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario)
			throws BusinessException {

		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargasMasivasGuardiaServiceImpl.parseExcelFileC");

		List<CargaMasivaDatosGuardiatem> masivaDatosBTVos = new ArrayList<CargaMasivaDatosGuardiatem>();
		CargaMasivaDatosGuardiatem cargaMasivaDatosBTItem = null;

		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);

		List<Short> idInstituciones = new ArrayList<Short>();
		idInstituciones.add(idInstitucion);

		// Comprueba si la institucion que esta logeada es la 2000. Si es diferente la
		// añade a la lista de instituciones
		if (idInstitucion != SigaConstants.IDINSTITUCION_2000) {
			idInstituciones.add(SigaConstants.IDINSTITUCION_2000);
		}

		StringBuffer errorLinea = null;
		int numLinea = 1;
		for (Hashtable<String, Object> hashtable : datos) {
			cargaMasivaDatosBTItem = new CargaMasivaDatosGuardiatem();

			cargaMasivaDatosBTItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
//			datosHashtable.put(SigaConstants.BT_NCOLEGIADO, "Campo de texto. Opcional, si es nulo el campo nifcif es requerido");
//			datosHashtable.put(SigaConstants.BT_NIF, "Campo de texto. Opcional, si es nulo el campo colegiado numero es requerido");

		
			// Comprobamos el numero de colegiado y el nif
			if (hashtable.get(SigaConstants.NCOLEGIADO) != null
					&& !hashtable.get(SigaConstants.NCOLEGIADO).toString().equals("")) {
				cargaMasivaDatosBTItem
				.setnColegiado((String) hashtable.get(SigaConstants.NCOLEGIADO));
				try {
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosBTItem.getnColegiado()).andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					cargaMasivaDatosBTItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());
				}
				catch (Exception e) {
					errorLinea.append("No se ha encontrado una persona con el número de colegiado introducido");
					cargaMasivaDatosBTItem.setnColegiado("Error");
				}
			}
			else {
					errorLinea.append("Es obligatorio el numero del colegiado.");
					cargaMasivaDatosBTItem.setNif("Error");
			}
		
				
//			datosHashtable.put(SigaConstants.BT_TIPO, "V-Vacaciones B-Baja M-Maternidad S-Suspension. Obligatorio.");


			//Comprobamos el la fecha de inicio
			if (hashtable.get(SigaConstants.FECHAI) != null
					&& !hashtable.get(SigaConstants.FECHAI).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setFechaInicio(new SimpleDateFormat("dd-MM-yyyy").parse((String) hashtable.get(SigaConstants.FECHAI)));				
				} catch (Exception e) {
					errorLinea.append("La fecha de inicio introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setFechaInicio(null);
				}
				
				
			}
			else {
				errorLinea.append("Es obligatorio introducir la fecha de inicio. ");
				cargaMasivaDatosBTItem.setFechaInicio(null);
			}
			
			//Comprobamos el la fecha de finalizacion
			if (hashtable.get(SigaConstants.FECHAF) != null
					&& !hashtable.get(SigaConstants.FECHAF).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setFechaFinal(new SimpleDateFormat("dd-MM-yyyy").parse((String) hashtable.get(SigaConstants.BT_FECHAF)));
				} catch (Exception e) {
					errorLinea.append("La fecha de finalizacion introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setFechaFinal(null);
				}
				//Comprobamos que la fincha de incio es anterior a la final
				if(cargaMasivaDatosBTItem.getFechaFinal()!=null && cargaMasivaDatosBTItem.getFechaInicio()!=null) {
					if(cargaMasivaDatosBTItem.getFechaInicio().after(cargaMasivaDatosBTItem.getFechaFinal())){
						errorLinea.append("La fecha de finalizacion no puede ser anterior a la de inicio.");
						cargaMasivaDatosBTItem.setFechaFinal(null);
					}
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir la fecha de finalizacion. ");
				cargaMasivaDatosBTItem.setFechaFinal(null);
			}
			
			
			
			
			
			//Comprobamos GUARDIA
			if (hashtable.get(SigaConstants.GUARDIA) != null
					&& !hashtable.get(SigaConstants.GUARDIA).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setIdGuardia(hashtable.get(SigaConstants.GUARDIA).toString());
				} catch (Exception e) {
					errorLinea.append("La guardia introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setIdGuardia(null);
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir la/s guardia/s. ");
				cargaMasivaDatosBTItem.setIdGuardia(null);
			}
			
			
			//Comprobamos TURNO
			
			if (hashtable.get(SigaConstants.TURNO) != null
					&& !hashtable.get(SigaConstants.TURNO).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setIdTurno(hashtable.get(SigaConstants.TURNO).toString());
				} catch (Exception e) {
					errorLinea.append("El turno introducido no tiene un formato valido.");
					cargaMasivaDatosBTItem.setIdTurno(null);
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir el turno. ");
				cargaMasivaDatosBTItem.setIdTurno(null);
			}
			
			

//			datosHashtable.put(SigaConstants.BT_MOTIVO, "Campo de texto. Obligatorio");
			


			if (!errorLinea.toString().isEmpty()) {
				cargaMasivaDatosBTItem
						.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaDatosBTVos.add(cargaMasivaDatosBTItem);
			numLinea++;
		}

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.parseExcelFileBT");
		return masivaDatosBTVos;
	}
	
	
	
	private List<CargaMasivaDatosGuardiatem> parseExcelFileGC(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario)
			throws BusinessException {

		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargasMasivasGuardiaServiceImpl.parseExcelFileC");

		List<CargaMasivaDatosGuardiatem> masivaDatosBTVos = new ArrayList<CargaMasivaDatosGuardiatem>();
		CargaMasivaDatosGuardiatem cargaMasivaDatosBTItem = null;

		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);

		List<Short> idInstituciones = new ArrayList<Short>();
		idInstituciones.add(idInstitucion);

		// Comprueba si la institucion que esta logeada es la 2000. Si es diferente la
		// añade a la lista de instituciones
		if (idInstitucion != SigaConstants.IDINSTITUCION_2000) {
			idInstituciones.add(SigaConstants.IDINSTITUCION_2000);
		}

		StringBuffer errorLinea = null;
		int numLinea = 1;
		for (Hashtable<String, Object> hashtable : datos) {
			cargaMasivaDatosBTItem = new CargaMasivaDatosGuardiatem();

			cargaMasivaDatosBTItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
//			datosHashtable.put(SigaConstants.BT_NCOLEGIADO, "Campo de texto. Opcional, si es nulo el campo nifcif es requerido");
//			datosHashtable.put(SigaConstants.BT_NIF, "Campo de texto. Opcional, si es nulo el campo colegiado numero es requerido");

		
			// Comprobamos el numero de colegiado y el nif
			if (hashtable.get(SigaConstants.NCOLEGIADO) != null
					&& !hashtable.get(SigaConstants.NCOLEGIADO).toString().equals("")) {
				cargaMasivaDatosBTItem
				.setnColegiado((String) hashtable.get(SigaConstants.NCOLEGIADO));
				try {
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosBTItem.getnColegiado()).andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					cargaMasivaDatosBTItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());
				}
				catch (Exception e) {
					errorLinea.append("No se ha encontrado una persona con el número de colegiado introducido");
					cargaMasivaDatosBTItem.setnColegiado("Error");
				}
			}
			else {
					errorLinea.append("Es obligatorio el numero del colegiado.");
					cargaMasivaDatosBTItem.setNif("Error");
			}
		
			//Comprobamos GUARDIA
			if (hashtable.get(SigaConstants.GUARDIA) != null
					&& !hashtable.get(SigaConstants.GUARDIA).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setIdGuardia(hashtable.get(SigaConstants.GUARDIA).toString());
				} catch (Exception e) {
					errorLinea.append("La guardia introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setIdGuardia(null);
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir la/s guardia/s. ");
				cargaMasivaDatosBTItem.setIdGuardia(null);
			}
			
			
			//Comprobamos TURNO
			
			if (hashtable.get(SigaConstants.TURNO) != null
					&& !hashtable.get(SigaConstants.TURNO).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setIdTurno(hashtable.get(SigaConstants.TURNO).toString());
				} catch (Exception e) {
					errorLinea.append("El turno introducido no tiene un formato valido.");
					cargaMasivaDatosBTItem.setIdTurno(null);
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir el turno. ");
				cargaMasivaDatosBTItem.setIdTurno(null);
			}
			
			
			if (hashtable.get(SigaConstants.GRUPO) != null
					&& !hashtable.get(SigaConstants.GRUPO).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setGrupo(hashtable.get(SigaConstants.GRUPO).toString());
				} catch (Exception e) {
					errorLinea.append("El grupo introducido no tiene un formato valido.");
					cargaMasivaDatosBTItem.setGrupo(null);
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir el grupo. ");
				cargaMasivaDatosBTItem.setGrupo(null);
			}
			
			if (hashtable.get(SigaConstants.ORDEN) != null
					&& !hashtable.get(SigaConstants.ORDEN).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setOrden(hashtable.get(SigaConstants.ORDEN).toString());
				} catch (Exception e) {
					errorLinea.append("El orden introducido no tiene un formato valido.");
					cargaMasivaDatosBTItem.setOrden(null);
				}
			}
			else {
				errorLinea.append("Es obligatorio introducir el orden. ");
				cargaMasivaDatosBTItem.setOrden(null);
			}
			

//			datosHashtable.put(SigaConstants.BT_MOTIVO, "Campo de texto. Obligatorio");
			


			if (!errorLinea.toString().isEmpty()) {
				cargaMasivaDatosBTItem
						.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaDatosBTVos.add(cargaMasivaDatosBTItem);
			numLinea++;
		}

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.parseExcelFileBT");
		return masivaDatosBTVos;
	}
	
	@Override
	public ResponseEntity<InputStreamResource> downloadLogFile(CargaMasivaItem cargaMasivaItem,
			HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("downloadLogFile() -> Entrada al servicio para generar la plantilla de errores");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Extraer el path
		//String path = "C:\\Users\\DTUser\\Documents\\CV" + idInstitucion + "\\cargas\\";
		String path = getDirectorioFichero(idInstitucion);
		path += File.separator +"log_" + idInstitucion + "_" + cargaMasivaItem.getIdFicheroLog() + "."
				+ SigaConstants.tipoExcelXls;
		File file = new File(path);

		// Preparar la descarga
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			LOGGER.warn("downloadLogFile() -> No encuentra el fichero original en la ruta SigaNovo");

			//Si no encuentra el fichero buscamos en la ruta de siga classique
			String pathClassique = getDirectorioFicheroSigaClassique(idInstitucion);
			pathClassique += File.separator +"log_" + idInstitucion + "_" + cargaMasivaItem.getIdFicheroLog() + "."
					+ SigaConstants.tipoExcelXls;
			
			File fileClassique = new File(pathClassique);
			
			// Preparar la descarga
			InputStream fileStreamClassique = null;

			try {
				fileStreamClassique = new FileInputStream(fileClassique);
				HttpHeaders headersClassique = new HttpHeaders();
				headersClassique.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

				headersClassique.setContentLength(fileClassique.length());
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStreamClassique), headersClassique, HttpStatus.OK);
			
			} catch (FileNotFoundException eClassique) {
				LOGGER.warn("downloadLogFile() -> No encuentra el fichero original en la ruta SigaClassique");
			}
		}

		LOGGER.info("downloadLogFile() -> Salida del servicio para generar la plantilla de errores");

		return res;
	}
	
	private String getDirectorioFicheroSigaClassique(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosCVImpl.getDirectorioFicheroSigaClassique");

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathCV = genPropertiesPath.get(0).getValor(); 
		
		StringBuffer directorioFichero = new StringBuffer(pathCV);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaCV");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.getDirectorioFicheroSigaClassique");
		return directorioFichero.toString();
	}
}
