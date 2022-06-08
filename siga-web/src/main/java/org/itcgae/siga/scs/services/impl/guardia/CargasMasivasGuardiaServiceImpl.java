package org.itcgae.siga.scs.services.impl.guardia;

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
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.*;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.guardia.CargasMasivasGuardiaService;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
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

import net.bytebuddy.asm.Advice.This;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CargasMasivasGuardiaServiceImpl implements CargasMasivasGuardiaService {

	private final Logger LOGGER = Logger.getLogger(CargasMasivasGuardiaServiceImpl.class);

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
	private ScsGrupoguardiaMapper scsGrupoguardiaMapper;

	@Autowired
	private ScsGuardiasturnoMapper scsGuardiasturnoMapper;

	@Autowired
	private ScsGrupoguardiacolegiadoMapper scsGrupoguardiacolegiadoMapper;

	@Autowired
	private ScsGrupoguardiacolegiadoExtendsMapper scsGrupoguardiacolegiadoExtendMapper;

	@Autowired
	private ScsGuardiascolegiadoMapper scsGuardiascolegiadoMapper;

	@Autowired
	private ScsCalendarioguardiasMapper scsCalendarioguardiasMapper;
	
	@Autowired
	private ExcelHelper excelHelper;

	private static final String ESTADO_PENDIENTE = "5";

	@Override
//	public ResponseEntity<FileOutputStream> descargarModelo(HttpServletRequest request, String turnos, String guardias, String tipo) 
	public ResponseEntity<InputStreamResource> descargarModelo(HttpServletRequest request, String turnos,
			String guardias, String tipo) throws IOException, EncryptedDocumentException, InvalidFormatException {

		// BUSCAMOS LAS INSCRPICIONES A PRESENTAR CON LOS PARAMETROS OBTENIDOS

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
			if (tipo.equals("I")) {
				file = newModelI(turnos, guardias, idInstitucion);
			} else if (tipo.equals("GC")) {
				file = newModelGC(turnos, guardias, idInstitucion);
			} else {
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
		LOGGER.info(
				"descargarModelo() -> Salida del servicio para generar el modelo de inscripciones o bajas temporales");

		return res;
	}

	public File newModelI(String turnos, String guardias, Short idInstitucion)
			throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Modelo Inscripciones");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		// CABECERA
		for (String p : SigaConstants.CAMPOSMODEL_I) {
			if (row.getLastCellNum() == -1)
				row.createCell(row.getLastCellNum() + 1).setCellValue(p);
			else
				row.createCell(row.getLastCellNum()).setCellValue(p);
			sheet.autoSizeColumn(row.getLastCellNum() - 1);
		}

		String[] gparts = guardias.split(",");
		String[] tparts = turnos.split(",");
		String[] listTurnos = new String[gparts.length];

		if (!guardias.equals("")) {
//			try {
			for (int i = 0; i < gparts.length; i++) {

				row = sheet.createRow(i + 1);
				// Busqueda para obtener turno asociado a la guardia y su nombre
				List<CargaMasivaDatosITItem> listGu = scsGuardiasTurnoExtendsMapper
						.searchNombreTurnoGuardiaNoAbrev(idInstitucion.toString(), gparts[i]);
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
			// Eliminamos de los turnos seleccionados los turnos que tienen guardia
			// seleccionada

			List<String> union = new ArrayList<String>(Arrays.asList(tparts));
			union.addAll(Arrays.asList(listTurnos));
			// Prepare an intersection
			List<String> intersection = new ArrayList<String>(Arrays.asList(tparts));
			intersection.retainAll(Arrays.asList(listTurnos));
			// Subtract the intersection from the union
			union.removeAll(intersection);
			tparts = union.toArray(new String[0]);
		}
		if (tparts.length > 0) {
			if (!tparts[0].equals("")) {
				for (String t : tparts) {
					// ScsTurnoKey key = new ScsTurnoKey();
					// key.setIdinstitucion(idInstitucion);
					// key.setIdturno(Integer.parseInt(t));
					// scsTurnoMapper.selectByPrimaryKey(key);
					row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(t);
					row.createCell(4);
				}
			}
		}

		// Creamos desplegable
		if (sheet.getLastRowNum() > 0) {
			CellRangeAddressList addressList = new CellRangeAddressList(1, sheet.getLastRowNum() + 1, 4, 4);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(new String[] { "ALTA", "BAJA" });
			DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
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
			File tmpFile = File.createTempFile("xls", "");
			returnFile = new File(tmpFile.getParent() + File.separator + "ModeloCargaI" + ".xls");
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

	public File newModelGC(String turnos, String guardias, Short idInstitucion)
			throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Modelo Grupos en cola");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		// CABECERA
		for (String p : SigaConstants.CAMPOSMODEL_GC) {
			if (row.getLastCellNum() == -1)
				row.createCell(row.getLastCellNum() + 1).setCellValue(p);
			else
				row.createCell(row.getLastCellNum()).setCellValue(p);
			sheet.autoSizeColumn(row.getLastCellNum() - 1);
		}

		String[] gparts = guardias.split(",");
		String[] tparts = turnos.split(",");
		String[] listTurnos = new String[gparts.length];

		if (!guardias.equals("")) {
//			try {
			for (int i = 0; i < gparts.length; i++) {

				// Busqueda para obtener turno asociado a la guardia y su nombre
				List<CargaMasivaDatosITItem> listGu = scsGuardiasTurnoExtendsMapper
						.searchNombreTurnoGuardiaNoAbrev(idInstitucion.toString(), gparts[i]);
				List<String> idGuardiaList = scsGuardiasturnoExtendsMapper.getIdGuardiaByName(gparts[i], idInstitucion.toString());
				String idGuardia = idGuardiaList.get(0);
				List<String> idTurnoList = scsGuardiasturnoExtendsMapper.getIdTurnoByName(tparts[i], idInstitucion.toString());
				String idTurno = idTurnoList.get(0);
				ScsInscripcionguardiaKey key1 = new ScsInscripcionguardiaKey();

				key1.setIdturno(Integer.parseInt(idTurno));
				key1.setIdinstitucion(idInstitucion);
				key1.setIdguardia(Integer.parseInt(idGuardia));
				List<String> colegiadosInscritos = scsInscripcionguardiaExtendsMapper
						.getColegiadosInscritosGuardia(key1);
				// Creamos tantas filas como colegiados haya inscritos en la Guardia
				// Seleccionada
				if (!colegiadosInscritos.isEmpty()) {
					for (int j = 0; j < colegiadosInscritos.size(); j++) {
						listTurnos[i] = listGu.get(0).getNombreTurno();
						row = sheet.createRow(j + 1);
						row.createCell(0).setCellValue(listGu.get(0).getNombreTurno());
						row.createCell(1).setCellValue(gparts[i]);
						row.createCell(2).setCellValue(colegiadosInscritos.get(j));
						row.createCell(4);
					}
				} else {
					listTurnos[i] = listGu.get(0).getNombreTurno();
					row = sheet.createRow(i + 1);
					row.createCell(0).setCellValue(listGu.get(0).getNombreTurno());
					row.createCell(1).setCellValue(gparts[i]);
					row.createCell(4);
				}

			}

//			}
//			catch (Exception e) {
//				throw new BusinessException("Error al buscar asociacion de guardia" + e.toString());
//			}
			// Eliminamos de los turnos seleccionados los turnos que tienen guardia
			// seleccionada

			List<String> union = new ArrayList<String>(Arrays.asList(tparts));
			union.addAll(Arrays.asList(listTurnos));
			// Prepare an intersection
			List<String> intersection = new ArrayList<String>(Arrays.asList(tparts));
			intersection.retainAll(Arrays.asList(listTurnos));
			// Subtract the intersection from the union
			union.removeAll(intersection);
			tparts = union.toArray(new String[0]);
		}
		if (tparts.length > 0) {
			if (!tparts[0].equals("")) {
				for (String t : tparts) {
					// ScsTurnoKey key = new ScsTurnoKey();
					// key.setIdinstitucion(idInstitucion);
					// key.setIdturno(Integer.parseInt(t));
					// scsTurnoMapper.selectByPrimaryKey(key);
					row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(t);
					row.createCell(4);
				}
			}
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
			File tmpFile = File.createTempFile("xls", "");
			returnFile = new File(tmpFile.getParent() + File.separator + "ModeloCargaIT" + ".xls");
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

	public File newModelC(String turnos, String guardias, Short idInstitucion)
			throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Modelo Calendarios");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		// CABECERA
		for (String p : SigaConstants.CAMPOSMODEL_C) {
			if (row.getLastCellNum() == -1)
				row.createCell(row.getLastCellNum() + 1).setCellValue(p);
			else
				row.createCell(row.getLastCellNum()).setCellValue(p);
			sheet.autoSizeColumn(row.getLastCellNum() - 1);
		}

		String[] gparts = guardias.split(",");
		String[] tparts = turnos.split(",");
		String[] listTurnos = new String[gparts.length];

		// El turno, la guardia y el número de colegiado deben existir en el colegio.

		// Si la guardia de colegiado ya existe en el sistema, dará error.
		// Si las fechas indicadas no se corresponden con la forma de generación del
		// calendario, dará error.
		// Si el colegiado no está inscrito en la guardia para la fecha indicada, dará
		// error. Tener en cuenta las fechas de inscripción efectiva de alta a futuro.
		// Al terminar las comprobaciones, se realizará la comprobación de las fechas
		// dentro del calendario y se creará el calendario programado.
		// Entonces para cada línea correcta, se creará una guardia de colegiado con sus
		// datos.

		if (!guardias.equals("")) {
//			try {
			for (int i = 0; i < gparts.length; i++) {

				row = sheet.createRow(i + 1);
				// Busqueda para obtener turno asociado a la guardia y su nombre
				List<CargaMasivaDatosITItem> listGu = scsGuardiasTurnoExtendsMapper
						.searchNombreTurnoGuardiaNoAbrev(idInstitucion.toString(), gparts[i]);
				listTurnos[i] = listGu.get(0).getNombreTurno();
				row.createCell(0).setCellValue(listGu.get(0).getNombreTurno());
				row.createCell(1).setCellValue(gparts[i]);
				row.createCell(4);
			}

			// Eliminamos de los turnos seleccionados los turnos que tienen guardia
			// seleccionada

			List<String> union = new ArrayList<String>(Arrays.asList(tparts));
			union.addAll(Arrays.asList(listTurnos));
			// Prepare an intersection
			List<String> intersection = new ArrayList<String>(Arrays.asList(tparts));
			intersection.retainAll(Arrays.asList(listTurnos));
			// Subtract the intersection from the union
			union.removeAll(intersection);
			tparts = union.toArray(new String[0]);
		}
		if (tparts.length > 0) {
			if (!tparts[0].equals("")) {
				for (String t : tparts) {
					row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(t);
					row.createCell(4);
				}
			}
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

		File returnFile = null;
		try {
			File tmpFile = File.createTempFile("xls", "");
			returnFile = new File(tmpFile.getParent() + File.separator + "ModeloCargaC" + ".xls");
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
	public DeleteResponseDTO uploadFileI(String fechaSolicitud, MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {
		LOGGER.info("uploadFileI() -> Entrada al servicio para subir un archivo");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
		LOGGER.debug("uploadFileI() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String nombreFichero = file.getOriginalFilename();

		// Extraer la información del excel
		LOGGER.debug("uploadFileI() -> Extraer los datos del archivo");
		Vector<Hashtable<String, Object>> datos = this.excelHelper.parseExcelFile(file.getBytes());

		CenCargamasiva cenCargamasivacv = new CenCargamasiva();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Boolean erroresExcel = false;
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
				// • El turno, la guardia y el número de colegiado deben existir en el colegio.

				List<CargaMasivaDatosITItem> cargaMasivaDatosITItems = parseExcelFileI(datos, usuario, fechaSolicitud);

				for (CargaMasivaDatosITItem cargaMasivaDatosITItem : cargaMasivaDatosITItems) {

					int result = -1;
					cenCargamasivacv.setNumregistros(cargaMasivaDatosITItems.size());
					// Si no se ha detectado errores leyendo el excel introducido
					if (cargaMasivaDatosITItem.getErrores() == null) {
						int z = 1;
						// En el caso que se haya introducido un tipo alta
						if (cargaMasivaDatosITItem.getTipo().equals("ALTA")) {

							TurnosItem t = new TurnosItem();
							t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
							List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t,
									idInstitucion.toString(), usuario.getIdlenguaje());

							ScsInscripcionguardia guardia = new ScsInscripcionguardia();
							// En SCS_INSCRIPCIONTURNO tenemos el campo FECHASOLICITUD, mientras en
							// SCS_INSCRIPCIONGUARDIA, el campo es FECHASUSCRIPCION.
							try {
								guardia.setFechasuscripcion(new SimpleDateFormat("dd/MM/yyyy").parse(fechaSolicitud));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							guardia.setFechavalidacion(cargaMasivaDatosITItem.getFechaEfectiva());
							guardia.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							guardia.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							guardia.setIdinstitucion(idInstitucion);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							// Se comprueba si se ha introducido una guardia especifica. Si no es asi, se
							// realiza con todos las guardias del turno.
							if (cargaMasivaDatosITItem.getIdGuardia().equals(null)
									|| cargaMasivaDatosITItem.getIdGuardia().equals("")) {
								int i = 0;
								while (i < listGu.size() && result != 0) {
									GuardiasItem gu = listGu.get(i);
									guardia.setIdguardia(Integer.parseInt(gu.getIdGuardia()));

									result = scsInscripcionguardiaMapper.insert(guardia);
									i++;
								}
								if (result == 0) {
									errores += "Error insertando la peticion de la linea " + z + ". <br/>";
									error.setDescription(errores);
									deleteResponseDTO.setError(error);

									registrosErroneos++;
								}
								// Se no se ha completado la insercion de todas las inscripciones, se eliminan
								// las realizadas.
								if (i < listGu.size() && i != 0) {

									ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
									key.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
									key.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
									key.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));

									key.setIdinstitucion(idInstitucion);
									for (int y = 0; y < (i - 1); i++) {
										GuardiasItem gu = listGu.get(i);
										key.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
										scsInscripcionguardiaMapper.deleteByPrimaryKey(key);
									}
								}
							} else {

								guardia.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));

								result = scsInscripcionguardiaMapper.insert(guardia);

								if (result == 0) {
									errores += "Error insertando la inscripcion de la linea " + z + ". <br/>";
									error.setDescription(errores);
									deleteResponseDTO.setError(error);

									registrosErroneos++;
								}
							}
						}
						// En el caso que se haya introducido una inscripcion de baja
						else {
							TurnosItem t = new TurnosItem();
							t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
							List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t,
									idInstitucion.toString(), usuario.getIdlenguaje());

							ScsInscripcionguardia guardia = new ScsInscripcionguardia();

							guardia.setFechabaja(cargaMasivaDatosITItem.getFechaEfectiva());
							guardia.setFechasolicitudbaja(cargaMasivaDatosITItem.getFechaEfectiva());
							guardia.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							guardia.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							guardia.setIdinstitucion(idInstitucion);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							// Se comprueba si se ha introducido una guardia especifica. Si no es asi, se
							// realiza con todos las guardias del turno.
							if (cargaMasivaDatosITItem.getIdGuardia().equals(null)
									|| cargaMasivaDatosITItem.getIdGuardia().equals("")) {
								int i = 0;
								while (i < listGu.size() && result != 0) {

									GuardiasItem gu = listGu.get(i);

									guardia.setIdguardia(Integer.parseInt(gu.getIdGuardia()));

									result = scsInscripcionguardiaMapper.updateByPrimaryKeySelective(guardia);
									i++;
								}
								if (result == 0) {
									errores += "Error insertando la inscripcion de la linea " + z + ". <br/>";
									error.setDescription(errores);
									deleteResponseDTO.setError(error);

									registrosErroneos++;
								}
								// Se no se ha completado la insercion de todas las inscripciones, se eliminan
								// las realizadas.
								if (i < listGu.size() && i != 0) {

									ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
									key.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
									key.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
									key.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));

									key.setIdinstitucion(idInstitucion);
									for (int y = 0; y < (i - 1); i++) {
										GuardiasItem gu = listGu.get(i);
										key.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
										scsInscripcionguardiaMapper.deleteByPrimaryKey(key);
									}
								}
							} else {

								guardia.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));

								result = scsInscripcionguardiaMapper.updateByPrimaryKeySelective(guardia);
								if (result != 0) {
									errores += "Error insertando la inscripcion de la linea " + z + ". <br/>";
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
						erroresExcel = true;
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

				if (cargaMasivaDatosITItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK);
				} else {
						byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGI, datosLog);
	
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
					
				}
				LOGGER.info("uploadFileIT() -> Salida al servicio para subir un archivo");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				error.setDescription(errores);
				int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
				error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
						+ "<br/> Registros Erroneos: " + registrosErroneos);
				error.setCode(SigaConstants.CODE_200);
			}
		}

		deleteResponseDTO.setError(error);

		return deleteResponseDTO;
	}

	private Long uploadFileLog(byte[] bytes, CenCargamasiva cenCargamasiva, boolean isLog) {

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

	private List<CargaMasivaDatosITItem> parseExcelFileI(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario,
			String fechaSolicitud) throws BusinessException {

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
				cargaMasivaDatosITItem.setNombreTurno((String) hashtable.get(SigaConstants.TURNO));
				try {
					TurnosItem turnosItem = new TurnosItem();
					turnosItem.setAbreviatura(null);
					turnosItem.setNombre(cargaMasivaDatosITItem.getNombreTurno());
					List<TurnosItem> listaTur = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion, usuario.getIdlenguaje());

					cargaMasivaDatosITItem.setIdTurno(listaTur.get(0).getIdturno().toString());
				} catch (Exception e) {
					errorLinea.append("No se ha encontrado un turno con la introducida");
					cargaMasivaDatosITItem.setNombreTurno("Error");
				}
			} else {
				errorLinea.append("Es obligatorio introducir el turno.");
				cargaMasivaDatosITItem.setNombreTurno("Error");
			}

			// Obtener guardia
			if (hashtable.get(SigaConstants.GUARDIA) != null
					&& !hashtable.get(SigaConstants.GUARDIA).toString().equals("")) {
				cargaMasivaDatosITItem.setNombreGuardia((String) hashtable.get(SigaConstants.GUARDIA));
				List<String> idGuardiaList = scsGuardiasturnoExtendsMapper
						.getIdGuardiaByName(cargaMasivaDatosITItem.getNombreGuardia(), idInstitucion.toString());
				String idGuardia = idGuardiaList.get(0);
				cargaMasivaDatosITItem.setIdGuardia(idGuardia);
				try {
					ScsGuardiasturnoExample guardiaExample = new ScsGuardiasturnoExample();
					guardiaExample.createCriteria().andNombreEqualTo(cargaMasivaDatosITItem.getNombreGuardia())
							.andIdinstitucionEqualTo(idInstitucion);
					List<ScsGuardiasturno> listaGuar = scsGuardiasTurnoExtendsMapper.selectByExample(guardiaExample);

					cargaMasivaDatosITItem.setIdTurno(listaGuar.get(0).getIdturno().toString());
				} catch (Exception e) {
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
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosITItem.getnColegiado())
							.andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					cargaMasivaDatosITItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());

				} catch (Exception e) {
					errorLinea.append(
							"No se ha encontrado una persona con el número de colegiado introducido en su institucion. ");
					cargaMasivaDatosITItem.setnColegiado("Error");
				}
			} else {
				errorLinea.append("Es obligatorio introducir número de colegiado.");
				cargaMasivaDatosITItem.setnColegiado("Error");
			}
//			SigaConstants.IT_FECHAEFECTIVA, "Obligatorio");
//			SigaConstants.IT_TIPO, "puede tener los valores ‘ALTA’ o ‘BAJA’. Obligatorio");
//			SigaConstants.IT_GRUPO, "Número de grupo para las guardias por grupo");
//			SigaConstants.IT_ORDEN, "Dentro del grupo para las guardias por grupo. Obligatorio si se rellena el Grupo");

			// Comprobamos tipo
			if (hashtable.get(SigaConstants.TIPO) != null && !hashtable.get(SigaConstants.TIPO).toString().equals("")) {

				if (hashtable.get(SigaConstants.TIPO).toString().equals("ALTA")
						|| hashtable.get(SigaConstants.TIPO).toString().equals("BAJA")) {
					cargaMasivaDatosITItem.setTipo((String) hashtable.get(SigaConstants.TIPO));
				} else {
					errorLinea.append("El valor de tipo solo puede ser \"ALTA\" o \"BAJA\".");
					cargaMasivaDatosITItem.setTipo("Error");
				}

			} else {
				errorLinea.append("Es obligatorio introducir el tipo de petición a realizar.");
				cargaMasivaDatosITItem.setTipo("Error");
			}

			// Comprobamos fecha efectiva
			if (hashtable.get(SigaConstants.FECHAEFECTIVA) != null
					&& !hashtable.get(SigaConstants.FECHAEFECTIVA).toString().equals("")) {
				try {
					cargaMasivaDatosITItem.setFechaEfectiva(new SimpleDateFormat("dd-MM-yyyy")
							.parse((String) hashtable.get(SigaConstants.FECHAEFECTIVA)));
				} catch (Exception e) {
					errorLinea.append("La fecha introducida no tiene un formato valido.");
					cargaMasivaDatosITItem.setFechaEfectiva(null);
				}

			} else {
				errorLinea.append("Es obligatorio introducir la fecha efectiva. ");
				cargaMasivaDatosITItem.setFechaEfectiva(null);
			}

//			El grupo y el orden sólo aplicarán para guardias por grupos, en otro caso, no se tendrán en cuenta.
//			• Orden no se puede repetir dentro del mismo grupo, tanto los existentes como los del fichero.

			// Comporbamos grupo y orden juntos al ser dependientes
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

			// Comprobamos según su tipo (ALTA o BAJA) y unicamente si no tiene otros
			// errrores
			if (errorLinea.toString().isEmpty() && hashtable.get(SigaConstants.TIPO).toString().equals("ALTA")) {
				TurnosItem t = new TurnosItem();
				t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
				List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(),
						usuario.getIdlenguaje());

				if (!listGu.isEmpty()) {

					// 1. Si no se ha indicado la guardia, se realizará la inscripción en todas las
					// guardias del turno (habrá que realizar las comprobaciones para todas las
					// guardias).
					if (cargaMasivaDatosITItem.getIdGuardia() != null && cargaMasivaDatosITItem.getIdGuardia() != "") {

						// 2. Si las guardias para el turno son obligatorias, no se podrá realizar.
						if (listGu.get(0).getObligatoriedad().equals("0")) {

							// 3. Si la inscripción en la guardia ya existe de alta para la fecha efectiva
							// indicada, no se podrá realizar.
							ScsInscripcionguardiaKey key1 = new ScsInscripcionguardiaKey();

							key1.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
							key1.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							key1.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							key1.setIdinstitucion(idInstitucion);
							key1.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));
							if (scsInscripcionguardiaMapper.selectByPrimaryKey(key1) == null) {

								// 4. Si no existe una inscripción en el turno para dicha inscripción en la
								// guardia, no se podrá realizar
								ScsInscripcionturnoKey key2 = new ScsInscripcionturnoKey();
								key2.setIdinstitucion(idInstitucion);
								key2.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
								key2.setFechasolicitud(cargaMasivaDatosITItem.getFechaEfectiva());
								key2.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));

								List<ScsInscripcionturno> insturList = new ArrayList<ScsInscripcionturno>();
								insturList = scsInscripcionturnoMapper.selectByPrimaryKeyDate(key2,
										new SimpleDateFormat("dd/MM/yyyy")
												.format(cargaMasivaDatosITItem.getFechaEfectiva()));

								// Comprobamos si ya exite inscripcion a dicho turno. Si no existe, no se
								// inscriben las guardias.
								if (insturList.size() != 0 && insturList.get(0) != null) {
									// 5. La fecha efectiva tiene que ser mayor o igual a la fecha efectiva del
									// turno correspondiente.
									if (cargaMasivaDatosITItem.getFechaEfectiva()
											.compareTo(insturList.get(0).getFechavalidacion()) < 0)
										errorLinea.append(
												"La fecha efectiva introducida es anterior a la fecha efectiva del turno asociado.");

									// 6. El grupo y el orden sólo aplicarán para guardias por grupos, en otro caso,
									// no se tendrán en cuenta
									// 6.1 Grupo y orden deben tener la longitud máxima permitida para estos campos
									// en base de datos.
									// 6.2 Orden no se puede repetir dentro del mismo grupo, tanto los existentes
									// como los del fichero.
									// 6.3 Un letrado no se encuentra dos veces en el mismo grupo
									StringBuffer errorLineaOrdenGrupo = CheckGrupoOrden(cargaMasivaDatosITItem,
											errorLinea, idInstitucion, hashtable, datos);

									// 7. Si la línea es correcta, se creará una inscripción de guardia ya validada
									// para el turno, guardia y colegiado indicados, usando el método utilizado en
									// la creación con validación de inscripciones de guardia.
									ScsInscripcionguardia ig = new ScsInscripcionguardia();
									ig.setFechamodificacion(new Date());
									ig.setFechavalidacion(new Date());
									ig.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));
									ig.setIdinstitucion(idInstitucion);
									ig.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
									ig.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
									ig.setUsumodificacion(usuario.getIdusuario());
									try {
										ig.setFechasuscripcion(
												new SimpleDateFormat("dd/MM/yyyy").parse(fechaSolicitud));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									scsInscripcionguardiaMapper.insertSelective(ig);

								} else
									errorLinea.append("El colegiado no esta inscrito en el turno indicado.");
							} else
								errorLinea.append(
										"Ya existe una inscripcion a dicha guardia con las mismas caracteristicas.");

						} else
							errorLinea.append(
									"Las guardias en el turno son obligatorias por lo que no se pueden realizar la fila.");

					}
					// Si no se introduce una guardia en especifico, se comprueban todas las
					// guardias de dicho turno
					else {
						for (GuardiasItem gu : listGu) {

							// 2. Si las guardias para el turno son obligatorias, no se podrá realizar.
							if (!gu.getObligatoriedad().equals("0")) {

								// 3. Si la inscripción en la guardia ya existe de alta para la fecha efectiva
								// indicada, no se podrá realizar.
								ScsInscripcionguardiaKey key1 = new ScsInscripcionguardiaKey();

								key1.setFechasuscripcion(cargaMasivaDatosITItem.getFechaEfectiva());
								key1.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
								key1.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
								key1.setIdinstitucion(idInstitucion);
								key1.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
								if (scsInscripcionguardiaMapper.selectByPrimaryKey(key1) == null) {

									// 4. Si no existe una inscripción en el turno para dicha inscripción en la
									// guardia, no se podrá realizar
									ScsInscripcionturnoKey key2 = new ScsInscripcionturnoKey();
									key2.setIdinstitucion(idInstitucion);
									key2.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
									key2.setFechasolicitud(cargaMasivaDatosITItem.getFechaEfectiva());
									key2.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));

									List<ScsInscripcionturno> insturList = new ArrayList<ScsInscripcionturno>();
									insturList = scsInscripcionturnoMapper.selectByPrimaryKeyDate(key2,
											new SimpleDateFormat("dd/MM/yyyy")
													.format(cargaMasivaDatosITItem.getFechaEfectiva()));
									if (insturList.get(0) != null) {
										// 5. La fecha efectiva tiene que ser mayor o igual a la fecha efectiva del
										// turno correspondiente.
										if (cargaMasivaDatosITItem.getFechaEfectiva()
												.compareTo(insturList.get(0).getFechavalidacion()) < 0)
											errorLinea.append(
													"La fecha efectiva introducida es anterior a la fecha efectiva del turno asociado.");

										// 6. El grupo y el orden sólo aplicarán para guardias por grupos, en otro caso,
										// no se tendrán en cuenta
										// 6.1 Grupo y orden deben tener la longitud máxima permitida para estos campos
										// en base de datos.
										// 6.2 Orden no se puede repetir dentro del mismo grupo, tanto los existentes
										// como los del fichero.
										// 6.3 Un letrado no se encuentra dos veces en el mismo grupo
										StringBuffer errorLineaOrdenGrupo = CheckGrupoOrden(cargaMasivaDatosITItem,
												errorLinea, idInstitucion, hashtable, datos);
//											if (errorLineaOrdenGrupo == null) {
//												//7. Si la línea es correcta, se creará una inscripción de guardia ya validada para el turno, guardia y colegiado indicados, usando el método utilizado en la creación con validación de inscripciones de guardia.
//												ScsInscripcionguardia ig = new ScsInscripcionguardia();
//												ig.setFechamodificacion(new Date());
//												ig.setFechavalidacion(new Date());
//												ig.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
//												ig.setIdinstitucion(idInstitucion);
//												ig.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
//												ig.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
//												ig.setUsumodificacion(usuario.getIdusuario());
//												try {
//													ig.setFechasuscripcion(new SimpleDateFormat("dd/MM/yyyy").parse(fechaSolicitud));
//												} catch (ParseException e) {
//													// TODO Auto-generated catch block
//													e.printStackTrace();
//												}
//												scsInscripcionguardiaMapper.insertSelective(ig);
//											
//											}
									} else
										errorLinea.append("El colegiado no esta inscrito en el turno indicado.");
								} else
									errorLinea.append(
											"Ya existe una inscripcion a dicha guardia con las mismas caracteristicas.");

							} else
								errorLinea.append(
										"Las guardias en el turno son obligatorias por lo que no se pueden realizar la fila.");
						}
					}

				} else
					errorLinea.append("No se ha encontrado guardias asociadas al turno indicado.");
			} else if (errorLinea.toString().isEmpty() && hashtable.get(SigaConstants.TIPO).toString().equals("BAJA")) {

				TurnosItem t = new TurnosItem();
				t.setIdturno(cargaMasivaDatosITItem.getIdTurno());
				List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(),
						usuario.getIdlenguaje());

				// Comprobamos el tipo de guardias asociadas al turno. En caso de ser
				// obligatorias (0) o Todas o ninguna (1), no se realizarian las inscripciones.
				// 2- A elegir; 1-Todas o ninguna;
				if (!listGu.isEmpty()) {
					// 2. Si las guardias para el turno son obligatorias o todas o ninguna, no se
					// podrá realizar
					if (!listGu.get(0).getObligatoriedad().equals("0")) {
						if (!listGu.get(0).getObligatoriedad().equals("1")) {

							ScsInscripcionguardiaKey key1 = new ScsInscripcionguardiaKey();

							key1.setIdturno(Integer.parseInt(cargaMasivaDatosITItem.getIdTurno()));
							key1.setIdpersona(Long.parseLong(cargaMasivaDatosITItem.getIdPersona()));
							key1.setIdinstitucion(idInstitucion);

							// Comprobamos si se introdujo una guardia o no.
							if (cargaMasivaDatosITItem.getIdGuardia().equals(null)) {
								// 1. Si no se ha indicado la guardia, se actualizará la inscripción en todas
								// las guardias del turno (habrá que realizar las comprobaciones para todas las
								// guardias).
								for (GuardiasItem gu : listGu) {

									key1.setIdguardia(Integer.parseInt(gu.getIdGuardia()));

									ScsInscripcionguardia ins = null;
									ins = scsInscripcionguardiaMapper.selectByPrimaryKey(key1);

									// 3. Si la inscripción en la guardia no existe o ya está de baja, no se podrá
									// realizar.
									// Se comprueba si la inscripción en la guardia no existe
									if (ins == null)
										errorLinea.append("El colegiado no esta inscrito en la guardia "
												+ gu.getNombre() + " del turno.");
									// Se comprueba si ya esta de baja
									else if (ins.getFechabaja() != null)
										errorLinea.append("El colegiado ya dió de baja la inscripcion en la guardia "
												+ gu.getNombre() + " del turno.");
								}
							} else {

								key1.setIdguardia(Integer.parseInt(cargaMasivaDatosITItem.getIdGuardia()));

								ScsInscripcionguardia ins = null;
								ins = scsInscripcionguardiaMapper.selectByPrimaryKey(key1);
								// 3. Si la inscripción en la guardia no existe o ya está de baja, no se podrá
								// realizar.
								// Se comprueba si la inscripción en la guardia no existe
								if (ins == null)
									errorLinea.append("El colegiado ya esta inscrito en la guardia "
											+ cargaMasivaDatosITItem.getNombreGuardia() + " del turno.");
								// Se comprueba si ya esta de baja
								else if (ins.getFechabaja() == null)
									errorLinea.append("El colegiado ya dió de baja la inscripcion en la guardia "
											+ cargaMasivaDatosITItem.getNombreGuardia() + " del turno.");

							}
						} else
							errorLinea.append(
									"Las guardias en el turno son \"Todas o ninguna\" por lo que no se pueden realizar la fila.");
					} else
						errorLinea.append(
								"Las guardias en el turno son \"Obligatorias\" por lo que no se pueden realizar la fila.");
				} else
					errorLinea.append("No se ha encontrado guardias asociadas al turno indicado.");
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

	private StringBuffer CheckGrupoOrden(CargaMasivaDatosITItem cargaMasivaDatosITItem, StringBuffer errorLinea,
			Short idInstitucion, Hashtable<String, Object> hashtable, Vector<Hashtable<String, Object>> datos) {
		// Comprobamos que la guardia esta en un grupo. Se cambian los valores pero no
		// se añade un error para que no impida su posible insercion
		List<CargaMasivaDatosITItem> group = scsInscripcionguardiaExtendsMapper.searchGrupoGuardia(idInstitucion,
				cargaMasivaDatosITItem.getIdGuardia(), cargaMasivaDatosITItem.getIdPersona());
		if (group.isEmpty()) {
			cargaMasivaDatosITItem.setOrden("Error");
			cargaMasivaDatosITItem.setGrupo("Error");
		} else {
			int colegr = 0;
			// Comprobamos si el colegiado ya esta en el grupo.
			List<CargaMasivaDatosITItem> gr = scsInscripcionguardiaExtendsMapper
					.searchGrupo((String) hashtable.get(SigaConstants.GRUPO), idInstitucion);
			for (CargaMasivaDatosITItem g : gr) {
				if (g.getIdPersona().equals(cargaMasivaDatosITItem.getIdPersona()))
					colegr++;
			}
			if (colegr > 0) {
				errorLinea.append("El colegiado ya esta en ese grupo.");
				cargaMasivaDatosITItem.setGrupo("Error");
			} else {
				if (hashtable.get(SigaConstants.ORDEN) != null
						&& !hashtable.get(SigaConstants.ORDEN).toString().equals("")) {
					// Comprobamos longitud de entrada
					if (cargaMasivaDatosITItem.getOrden().length() <= 5) {
						errorLinea.append("El valor en la columna \"ORDEN\" debe tener menos de seis cifras.");
						cargaMasivaDatosITItem.setOrden("Error");
					} else {
						// Comprobamos que en el grupo no existe una entrada con el mismo orden
						int j = 0;
						int i = 0;
						while (i < gr.size() && j == 0) {
							gr.get(i).getOrden().equals((String) hashtable.get(SigaConstants.ORDEN));
							i++;
						}
						if (j == 0) {
							// Comprobamos que en el fichero no existe una entrada con el mismo orden y
							// grupo.
							int z = 0;
							while (z < datos.size() && j <= 1) {
								Hashtable<String, Object> hashtable2 = datos.get(z);
								if (hashtable2.get(SigaConstants.ORDEN)
										.equals((String) hashtable.get(SigaConstants.ORDEN))
										&& hashtable2.get(SigaConstants.GRUPO)
												.equals((String) hashtable.get(SigaConstants.GRUPO)))
									j++;
								z++;
							}
							// Se tiene en cuenta que se encontra con linea que estamos revisando al leer el
							// fichero entero
							if (j > 1) {
								errorLinea.append("El fichero tiene repetida una linea con grupo "
										+ hashtable.get(SigaConstants.GRUPO) + " con orden "
										+ hashtable.get(SigaConstants.ORDEN) + ".");
								cargaMasivaDatosITItem.setOrden("Error");
							}
							// Si pasa todas las comprobaciones se asignan los valores.
							else {
								cargaMasivaDatosITItem.setOrden((String) hashtable.get(SigaConstants.ORDEN));
								cargaMasivaDatosITItem.setGrupo((String) hashtable.get(SigaConstants.GRUPO));
							}
						} else {
							errorLinea.append("El grupo " + hashtable.get(SigaConstants.GRUPO)
									+ " ya tiene una entrada con orden " + hashtable.get(SigaConstants.ORDEN) + ".");
							cargaMasivaDatosITItem.setOrden("Error");
						}

					}
				} else {
					errorLinea.append(
							"Es obligatorio introducir un valor en la columna \"ORDEN\" una vez se rellena la columna \"GRUPO\".");
					cargaMasivaDatosITItem.setOrden("Error");
				}
			}
		}
		return errorLinea;
	}

	private StringBuffer CheckGrupoOrden2(CargaMasivaDatosGuardiatem cargaMasivaDatosITItem, StringBuffer errorLinea,
			Short idInstitucion, Hashtable<String, Object> hashtable, Vector<Hashtable<String, Object>> datos) {
		// Comprobamos que la guardia esta en un grupo. Se cambian los valores pero no
		// se añade un error para que no impida su posible insercion
		List<CargaMasivaDatosITItem> group = scsInscripcionguardiaExtendsMapper.searchGrupoGuardia(idInstitucion,
				cargaMasivaDatosITItem.getIdGuardia(), null);
		if (group.isEmpty()) {
			cargaMasivaDatosITItem.setOrden("Error");
			cargaMasivaDatosITItem.setGrupo("Error");
		} else {
			int colegr = 0;
			// Comprobamos si el colegiado ya esta en el grupo.
			List<CargaMasivaDatosITItem> gr = scsInscripcionguardiaExtendsMapper
					.searchGrupo((String) hashtable.get(SigaConstants.GRUPO), idInstitucion);
			for (CargaMasivaDatosITItem g : gr) {
				if (g.getIdPersona().equals(cargaMasivaDatosITItem.getIdPersona()))
					colegr++;
			}
			if (colegr > 0) {
				errorLinea.append("El colegiado ya esta en ese grupo.");
				cargaMasivaDatosITItem.setGrupo("Error");
			} else {
				if (hashtable.get(SigaConstants.ORDEN) != null
						&& !hashtable.get(SigaConstants.ORDEN).toString().equals("")) {
					// Comprobamos longitud de entrada
					if (cargaMasivaDatosITItem.getOrden().length() > 5) {
						errorLinea.append("El valor en la columna \"ORDEN\" debe tener menos de seis cifras.");
						cargaMasivaDatosITItem.setOrden("Error");
					} else {
						// Comprobamos que en el grupo no existe una entrada con el mismo orden
						int j = 0;
						int i = 0;
						while (i < gr.size() && j == 0) {
							gr.get(i).getOrden().equals((String) hashtable.get(SigaConstants.ORDEN));
							i++;
						}
						if (j == 0) {
							// Comprobamos que en el fichero no existe una entrada con el mismo orden y
							// grupo.
							int z = 0;
							while (z < datos.size() && j <= 1) {
								Hashtable<String, Object> hashtable2 = datos.get(z);
								if (hashtable2.get(SigaConstants.ORDEN)
										.equals((String) hashtable.get(SigaConstants.ORDEN))
										&& hashtable2.get(SigaConstants.GRUPO)
												.equals((String) hashtable.get(SigaConstants.GRUPO)))
									j++;
								z++;
							}
							// Se tiene en cuenta que se encontra con linea que estamos revisando al leer el
							// fichero entero
							if (j > 1) {
								errorLinea.append("El fichero tiene repetida una linea con grupo "
										+ hashtable.get(SigaConstants.GRUPO) + " con orden "
										+ hashtable.get(SigaConstants.ORDEN) + ".");
								cargaMasivaDatosITItem.setOrden("Error");
							}
							// Si pasa todas las comprobaciones se asignan los valores.
							else {
								cargaMasivaDatosITItem.setOrden((String) hashtable.get(SigaConstants.ORDEN));
								cargaMasivaDatosITItem.setGrupo((String) hashtable.get(SigaConstants.GRUPO));
							}
						} else {
							errorLinea.append("El grupo " + hashtable.get(SigaConstants.GRUPO)
									+ " ya tiene una entrada con orden " + hashtable.get(SigaConstants.ORDEN) + ".");
							cargaMasivaDatosITItem.setOrden("Error");
						}

					}
				} else {
					errorLinea.append(
							"Es obligatorio introducir un valor en la columna \"ORDEN\" una vez se rellena la columna \"GRUPO\".");
					cargaMasivaDatosITItem.setOrden("Error");
				}
			}
		}
		return errorLinea;
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
		if (tipo.equals("I")) {
			XLSFile = this.excelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroModeloI);
		} else if (tipo.equals("GC")) {
			XLSFile = this.excelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroModeloGC);
		} else {
			XLSFile = this.excelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroModeloC);
		}
		return XLSFile;
	}

	private void insertaCenHistoricoIT(CargaMasivaDatosITItem cargaMasivaDatosITItem, AdmUsuarios usuario)
			throws BusinessException {

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
				cargaMasivaDatosITItem.getIdInstitucion() != null ? cargaMasivaDatosITItem.getIdInstitucion() : "");

		descripcion.append("\n");
		descripcion.append("- IdGuardia: ");
		descripcion.append(cargaMasivaDatosITItem.getIdGuardia() != null ? cargaMasivaDatosITItem.getIdGuardia() : "");

		descripcion.append("\n");
		descripcion.append("- Tipo: ");
		descripcion.append(cargaMasivaDatosITItem.getTipo() != null ? cargaMasivaDatosITItem.getTipo() : "");

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
		LOGGER.info("uploadFileGC() -> Entrada al servicio para subir un archivo");
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
		Vector<Hashtable<String, Object>> datos = this.excelHelper.parseExcelFile(file.getBytes());

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
					int i = 1;
					int result = -1;

					// Si no se ha detectado errores leyendo el excel introducido
					if (cargaMasivaDatosGCItem.getErrores() == null) {
						TurnosItem t = new TurnosItem();
						t.setIdturno(cargaMasivaDatosGCItem.getIdTurno());
						List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t,
								idInstitucion.toString(), usuario.getIdlenguaje());
						if (!listGu.isEmpty()) {

							// 1. Si no se ha indicado la guardia, se realizará la inscripción en todas las
							// guardias del turno (habrá que realizar las comprobaciones para todas las
							// guardias).
							if (cargaMasivaDatosGCItem.getIdGuardia() != null
									&& cargaMasivaDatosGCItem.getIdGuardia() != "") {

								// 2.Si la inscripción en la guardia no existe de alta para la fecha actual, no
								// se podrá realizar.
								ScsInscripcionguardiaKey key1 = new ScsInscripcionguardiaKey();

								key1.setFechasuscripcion(new Date());
								key1.setIdturno(Integer.parseInt(cargaMasivaDatosGCItem.getIdTurno()));
								key1.setIdpersona(Long.parseLong(cargaMasivaDatosGCItem.getIdPersona()));
								key1.setIdinstitucion(idInstitucion);
								key1.setIdguardia(Integer.parseInt(cargaMasivaDatosGCItem.getIdGuardia()));
								if (scsInscripcionguardiaMapper.selectByPrimaryKeyNoDate(key1) != null) {

									// Si la guardia no es por grupo, dará error.
									// Grupo y orden deben tener la longitud máxima permitida para estos campos en
									// base de datos
									// Orden no se puede repetir dentro del mismo grupo, tanto los existentes como
									// los del fichero
									// Un letrado no se encuentra dos veces en el mismo grupo.

									// YA PROBADO EN EL PARSEO

									// Si se intenta añadir un grupo sin indicar una guardia, hay que buscar todas
									// las inscripciones existentes de guardias
									// pertenecientes al turno indicado para asignar el grupo (ya que no es
									// obligatorio estar apuntado a todas las guardias de un turno).
									List<ScsInscripcionguardia> listInscrip = scsInscripcionguardiaExtendsMapper
											.getInscripcionByTurnoGuardiaNcolegiado(
													usuarios.get(0).getIdusuario().toString(),
													cargaMasivaDatosGCItem.getIdTurno(),
													cargaMasivaDatosGCItem.getIdGuardia(),
													cargaMasivaDatosGCItem.getnColegiado());

									// INSERTAMOS GRUPOGUARDIA
									// NewIdDTO lastIdGG = scsGrupoguardiaMapper.getLastId();
									listInscrip.forEach(inscripcion -> {
										ScsGrupoguardia grupo = new ScsGrupoguardia();
										grupo.setFechacreacion(new Date());
										grupo.setIdguardia(inscripcion.getIdguardia());
										grupo.setIdturno(inscripcion.getIdturno());
										// grupo.setIdpersona(inscripcion.getIdpersona());
										grupo.setIdinstitucion(inscripcion.getIdinstitucion());
										grupo.setFechamodificacion(new Date());
										// grupo.setFechasuscripcion(new Date());
										// grupo.setIdgrupoguardiacolegiado(Long.parseLong(lastIdGGC.getNewId()) + 1);
										// grupo.setOrden(Integer.parseInt(cargaMasivaDatosGCItem.getOrden()));
										grupo.setNumerogrupo(Integer.parseInt(cargaMasivaDatosGCItem.getGrupo()));
										// grupo.setIdgrupoguardia(Long.parseLong(lastIdGG.getNewId()) + 1);
										grupo.setUsucreacion(
												Integer.parseInt(usuarios.get(0).getIdusuario().toString()));
										grupo.setUsumodificacion(
												Integer.parseInt(usuarios.get(0).getIdusuario().toString()));

										int existeGrupo = scsGrupoguardiacolegiadoMapper
												.getGrupoGuardiaByUniqueKey(grupo);
										// Carga de grupos en cola = asignar los grupos a las inscripciones de guardia.
										if (existeGrupo == 0) {
											scsGrupoguardiaMapper.insertSelective(grupo);
										}
										NewIdDTO lastIdGGnew = scsGrupoguardiaMapper.getLastId();
										// INSERTAMOS GRUPOGUARDIACOLEGIADO
										// NewIdDTO lastIdGGC = scsGrupoguardiacolegiadoExtendMapper.getLastId();
										ScsGrupoguardiacolegiado grupoGC = new ScsGrupoguardiacolegiado();
										grupoGC.setFechacreacion(new Date());
										grupoGC.setIdguardia(inscripcion.getIdguardia());
										grupoGC.setIdturno(inscripcion.getIdturno());
										grupoGC.setIdpersona(inscripcion.getIdpersona());
										grupoGC.setIdinstitucion(inscripcion.getIdinstitucion());
										grupoGC.setFechamodificacion(new Date());
										// grupoGC.setIdgrupoguardiacolegiado(Long.parseLong(lastIdGGC.getNewId()) + 1);
										grupoGC.setOrden(Integer.parseInt(cargaMasivaDatosGCItem.getOrden()));
										grupoGC.setIdgrupoguardia(Long.valueOf(lastIdGGnew.getNewId()));
										grupoGC.setUsucreacion(
												Integer.parseInt(usuarios.get(0).getIdusuario().toString()));
										grupoGC.setUsumodificacion(
												Integer.parseInt(usuarios.get(0).getIdusuario().toString()));
										grupoGC.setFechasuscripcion(inscripcion.getFechasuscripcion());
										scsGrupoguardiacolegiadoMapper.insertSelective(grupoGC);

										// ACTUALIZAMOS PORGRUPOS = 1
										ScsGuardiasturno gt = new ScsGuardiasturno();
										gt.setFechamodificacion(new Date());
										gt.setIdguardia(Integer.parseInt(cargaMasivaDatosGCItem.getIdGuardia()));
										gt.setIdinstitucion(idInstitucion);
										gt.setIdturno(Integer.parseInt(cargaMasivaDatosGCItem.getIdTurno()));
										gt.setPorgrupos("1");
										scsGuardiasturnoMapper.updateByPrimaryKeySelective(gt);

									});
									// Si no existe inscripción, la asignación de grupo, no puede crearse.
									if (listInscrip.size() == 0) {
										errores += "Error insertando los grupos en cola de la linea " + i
												+ " debido a que no existen inscripciones . <br/>";
										error.setDescription(errores);
										deleteResponseDTO.setError(error);

										registrosErroneos++;
									}

								} else {
									errores += "Error insertando los grupos en cola de la linea " + i
											+ " debido a que no existen inscripcion de alta para la fecha actual . <br/>";
									error.setDescription(errores);
									deleteResponseDTO.setError(error);

									registrosErroneos++;
								}

							} else {
								for (GuardiasItem gu : listGu) {

								}
							}
						} else {
							error.setMessage("No existen la guardia y el turno de alta en el sistema.");
							deleteResponseDTO.setStatus(SigaConstants.KO);
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

				if (cargaMasivaDatosBTItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				} else {
					byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGGC, datosLog);

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
	public DeleteResponseDTO uploadFileC(MultipartHttpServletRequest request, String fechaDesde, String fechaHasta,
			String observaciones) throws Exception {
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
		Vector<Hashtable<String, Object>> datos = this.excelHelper.parseExcelFile(file.getBytes());

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
					int i = 1;
					int result = -1;

					// Si no se ha detectado errores leyendo el excel introducido
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

						// Comprobar que las fechas introducidas en el fichero están dentro del periodo

						try {
							if (cenBajasTemporales.getFechadesde()
									.before(new SimpleDateFormat("dd/MM/yyyy").parse(fechaDesde))
									|| cenBajasTemporales.getFechahasta()
											.after(new SimpleDateFormat("dd/MM/yyyy").parse(fechaHasta))) {
								error.setCode(SigaConstants.CODE_400);
								errores += "Error debido a que las fechas no cumplen el rango establecido";
								LOGGER.error(
										"uploadFileC() -> Error debido a que las fechas no cumplen el rango establecido");
							}

							// Cumplimentar el calendario (programación de calendario) que se creará con
							// todas las guardias del fichero subido.
							// Creamos un calendario con fechaDesde, fechaHasta y observaciones

//						CenBajastemporalesMapper cenBajasTemporalesMapper;

							if (!fechaDesde.isEmpty() && cargaMasivaDatosBTItem.getFechaInicio() != null
									&& !fechaHasta.isEmpty() && cargaMasivaDatosBTItem.getFechaFinal() != null) {
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								String today = formatter.format(new Date());
								int numGuards = scsGuardiasturnoExtendsMapper.getGuardiasToProgByDates(
										new SimpleDateFormat("dd/MM/yyyy")
												.format(cargaMasivaDatosBTItem.getFechaInicio()),
										new SimpleDateFormat("dd/MM/yyyy").format(
												cargaMasivaDatosBTItem.getFechaFinal()),
										idInstitucion.toString());
								List<String> idGuardiaList = scsGuardiasturnoExtendsMapper
										.getIdGuardiaByName(cargaMasivaDatosBTItem.getIdGuardia(), idInstitucion.toString());
								String idGuardia = idGuardiaList.get(0);
								List<String> idTurnoList = scsGuardiasturnoExtendsMapper
										.getIdTurnoByName(cargaMasivaDatosBTItem.getIdTurno(), idInstitucion.toString());
								String idTurno = idTurnoList.get(0);
								if (numGuards <= 0) {
									error.setCode(204);
									error.setDescription("No existen guardias asociadas a esta programación");
									error.setMessage("No existen guardias asociadas a esta programación");
									deleteResponseDTO.setError(error);
								} else {
									DatosCalendarioProgramadoItem calendarioItem = new DatosCalendarioProgramadoItem();
									calendarioItem.setEstado(ESTADO_PENDIENTE);
									calendarioItem.setNumGuardias(Integer.toString(numGuards));
									calendarioItem.setIdGuardia(idGuardia);
									calendarioItem.setIdTurno(idTurno);
									calendarioItem.setFechaDesde(new SimpleDateFormat("dd/MM/yyyy")
											.format(cargaMasivaDatosBTItem.getFechaInicio()));
									calendarioItem.setFechaHasta(new SimpleDateFormat("dd/MM/yyyy")
											.format(cargaMasivaDatosBTItem.getFechaFinal()));
									calendarioItem.setObservaciones(observaciones);
									// generamos programacion por cada guardia
									// ScsProgCalendariosSqlProvider.insertSelective(ScsProgCalendarios record)
									String nextIdCalendarioProgramado = getNuevoIdCalProg();
									calendarioItem.setIdCalendarioProgramado(nextIdCalendarioProgramado);
									int res = scsGuardiasturnoExtendsMapper.generateCalendarioProgramado(
											nextIdCalendarioProgramado, calendarioItem, idInstitucion.toString(), today,
											usuario.getIdusuario().toString());

									String idProgramacion = scsGuardiasturnoExtendsMapper
											.getLastProgramacion(idInstitucion.toString());
									// generamos un calendario por cada guardia asociada a esa programacion
									GuardiaCalendarioItem item = new GuardiaCalendarioItem();
									item.setIdGuardia(idGuardia);
									item.setIdTurno(idTurno);
									item.setOrden("1");
									String res2 = scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias(null,
											null, null, observaciones, idTurno, idGuardia,
											new SimpleDateFormat("dd/MM/yyyy")
													.format(cargaMasivaDatosBTItem.getFechaFinal()),
											new SimpleDateFormat("dd/MM/yyyy")
													.format(cargaMasivaDatosBTItem.getFechaInicio()),
											idProgramacion, idInstitucion.toString(), null, today, null, null,
											usuarios.get(0).getIdusuario().toString());

									String idCalendario = scsGuardiasturnoExtendsMapper
											.getLastCalendar(idInstitucion.toString());
									// insertamos historico de calendario
									scsGuardiasturnoExtendsMapper.insertHistoricoCalendario(idCalendario, null,
											idInstitucion.toString(), today, item,
											usuarios.get(0).getIdusuario().toString());

									// Para cada línea correcta, se creará una guardia de colegiado con sus datos.
									ScsGuardiasturnoKey key = new ScsGuardiasturnoKey();
									key.setIdguardia(Integer.parseInt(idGuardia));
									key.setIdinstitucion(idInstitucion);
									key.setIdturno(Integer.parseInt(idTurno));
									ScsGuardiasturno configGuardia = scsGuardiasturnoMapper.selectByPrimaryKey(key);
									ScsGuardiascolegiado gc = new ScsGuardiascolegiado();
									gc.setIdguardia(Integer.parseInt(idGuardia));
									gc.setFechafin(cargaMasivaDatosBTItem.getFechaFinal());
									gc.setFechainicio(cargaMasivaDatosBTItem.getFechaInicio());
									gc.setFechamodificacion(new Date());
									gc.setIdpersona(Long.parseLong(cargaMasivaDatosBTItem.getIdPersona()));
									gc.setIdturno(Integer.parseInt(idTurno));
									gc.setIdinstitucion(idInstitucion);
									gc.setDiasguardia(new Long(configGuardia.getDiasguardia()));
									gc.setDiasacobrar(new Long(configGuardia.getDiasguardia()));
									gc.setUsumodificacion(usuario.getIdusuario());
									gc.setReserva("N");
									scsGuardiascolegiadoMapper.insert(gc);
								}
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (result == 0) {
							errores += "Error insertando calendario (a tarvés de cargas masivas) de la linea " + i
									+ ". <br/>";
							error.setDescription(errores);
							deleteResponseDTO.setError(error);

							registrosErroneos++;
						}

					} else {
						LOGGER.error("uploadFileC() -> " + cargaMasivaDatosBTItem.getErrores());
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

				if (cargaMasivaDatosBTItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK);
				} else {
					byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGGC, datosLog);

					cenCargamasivacv.setTipocarga("C");
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

					LOGGER.info("uploadFileC() -> Salida al servicio para subir un archivo");
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

	private List<CargaMasivaDatosGuardiatem> parseExcelFileC(Vector<Hashtable<String, Object>> datos,
			AdmUsuarios usuario) throws Exception {

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
				cargaMasivaDatosBTItem.setnColegiado((String) hashtable.get(SigaConstants.NCOLEGIADO));
				try {
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosBTItem.getnColegiado())
							.andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					cargaMasivaDatosBTItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());
				} catch (Exception e) {
					errorLinea.append("No se ha encontrado una persona con el número de colegiado introducido");
					cargaMasivaDatosBTItem.setnColegiado("Error");
				}
			} else {
				errorLinea.append("Es obligatorio el numero del colegiado.");
				cargaMasivaDatosBTItem.setNif("Error");
			}

			// Comprobamos el la fecha de inicio
			if (hashtable.get(SigaConstants.FECHAI) != null
					&& !hashtable.get(SigaConstants.FECHAI).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setFechaInicio(
							new SimpleDateFormat("dd-MM-yyyy").parse((String) hashtable.get(SigaConstants.FECHAI)));
				} catch (Exception e) {
					errorLinea.append("La fecha de inicio introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setFechaInicio(null);
				}

			} else {
				errorLinea.append("Es obligatorio introducir la fecha de inicio. ");
				cargaMasivaDatosBTItem.setFechaInicio(null);
			}

			// Comprobamos el la fecha de finalizacion
			if (hashtable.get(SigaConstants.FECHAF) != null
					&& !hashtable.get(SigaConstants.FECHAF).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setFechaFinal(
							new SimpleDateFormat("dd-MM-yyyy").parse((String) hashtable.get(SigaConstants.BT_FECHAF)));
				} catch (Exception e) {
					errorLinea.append("La fecha de finalizacion introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setFechaFinal(null);
				}
				// Comprobamos que la fincha de incio es anterior a la final
				if (cargaMasivaDatosBTItem.getFechaFinal() != null && cargaMasivaDatosBTItem.getFechaInicio() != null) {
					if (cargaMasivaDatosBTItem.getFechaInicio().after(cargaMasivaDatosBTItem.getFechaFinal())) {
						errorLinea.append("La fecha de finalizacion no puede ser anterior a la de inicio.");
						cargaMasivaDatosBTItem.setFechaFinal(null);
					}
				}
			} else {
				errorLinea.append("Es obligatorio introducir la fecha de finalizacion. ");
				cargaMasivaDatosBTItem.setFechaFinal(null);
			}

			// Comprobamos GUARDIA
			if (hashtable.get(SigaConstants.GUARDIA) != null
					&& !hashtable.get(SigaConstants.GUARDIA).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setIdGuardia(hashtable.get(SigaConstants.GUARDIA).toString());
				} catch (Exception e) {
					errorLinea.append("La guardia introducida no tiene un formato valido.");
					cargaMasivaDatosBTItem.setIdGuardia(null);
				}
			} else {
				errorLinea.append("Es obligatorio introducir la/s guardia/s. ");
				cargaMasivaDatosBTItem.setIdGuardia(null);
			}

			// Comprobamos TURNO

			if (hashtable.get(SigaConstants.TURNO) != null
					&& !hashtable.get(SigaConstants.TURNO).toString().equals("")) {
				try {
					cargaMasivaDatosBTItem.setIdTurno(hashtable.get(SigaConstants.TURNO).toString());
				} catch (Exception e) {
					errorLinea.append("El turno introducido no tiene un formato valido.");
					cargaMasivaDatosBTItem.setIdTurno(null);
				}
			} else {
				errorLinea.append("Es obligatorio introducir el turno. ");
				cargaMasivaDatosBTItem.setIdTurno(null);
			}
			if (errorLinea.toString().isEmpty()) {
			// 1. Si la guardia de colegiado ya existe en el sistema, dará error.
			List<String> idGuardiaList = scsGuardiasturnoExtendsMapper
					.getIdGuardiaByName(cargaMasivaDatosBTItem.getIdGuardia(), idInstitucion.toString());
			String idGuardia = idGuardiaList.get(0);
			List<String> idTurnoList = scsGuardiasturnoExtendsMapper
					.getIdTurnoByName(cargaMasivaDatosBTItem.getIdTurno(), idInstitucion.toString());
			String idTurno = idTurnoList.get(0);
			ScsGuardiascolegiadoKey keyGC = new ScsGuardiascolegiadoKey();
			keyGC.setFechafin(cargaMasivaDatosBTItem.getFechaFinal());
			keyGC.setFechainicio(cargaMasivaDatosBTItem.getFechaInicio());
			keyGC.setIdguardia(Integer.parseInt(idGuardia));
			keyGC.setIdinstitucion(cargaMasivaDatosBTItem.getIdInstitucion());
			keyGC.setIdpersona(Long.parseLong(cargaMasivaDatosBTItem.getIdPersona()));
			keyGC.setIdturno(Integer.parseInt(idTurno));
			ScsGuardiascolegiado guardiasColegiado = scsGuardiascolegiadoMapper.selectByPrimaryKey(keyGC);

			if (guardiasColegiado == null) {

				// 2. Si las fechas indicadas no se corresponden con la forma de generación del
				// calendario, dará error.
				ScsGuardiasturnoKey key = new ScsGuardiasturnoKey();
				key.setIdguardia(Integer.parseInt(idGuardia));
				key.setIdinstitucion(idInstitucion);
				key.setIdturno(Integer.parseInt(idTurno));
				ScsGuardiasturno configGuardia = scsGuardiasturnoMapper.selectByPrimaryKey(key);
				if (configGuardia != null) {
				Short diasSeparacion = configGuardia.getDiasseparacionguardias();
				int periodo = 0;
				int unidadesPeriodo = 0;
				String laborables = configGuardia.getSeleccionlaborables();
				String festivos = configGuardia.getSeleccionfestivos();
				// DURACION:
				int duracion = configGuardia.getDiasguardia().intValue();
				int unidadesDuracion = this.convertirUnidadesDuracion(configGuardia.getTipodiasguardia());

				// PERIODO:
				if (configGuardia.getDiasperiodo() != null) {
					periodo = configGuardia.getDiasperiodo().intValue();
					unidadesPeriodo = this.convertirUnidadesDuracion(configGuardia.getDiasperiodo().toString());
				} else {
					periodo = 0;
					unidadesPeriodo = 0;
				}

				// Seleccion de laborables:
				Vector seleccionLaborables = new Vector();
				if (laborables != null) {
					for (int i = 0; i < laborables.length(); i++)
						seleccionLaborables.add(
								new Integer(CalendarioAutomatico.convertirUnidadesDiasSemana(laborables.charAt(i))));
				}

				// Seleccion de festivos:
				Vector seleccionFestivos = new Vector();
				if (festivos != null) {
					for (int i = 0; i < festivos.length(); i++)
						seleccionFestivos
								.add(new Integer(CalendarioAutomatico.convertirUnidadesDiasSemana(festivos.charAt(i))));
				}

				// FESTIVOS:
				List<String> listaFestivos = null;
				try {
					listaFestivos = obtenerFestivosTurno(Integer.parseInt(idInstitucion.toString()),
							Integer.parseInt(idTurno),
							new SimpleDateFormat("dd/MM/yyyy").format(cargaMasivaDatosBTItem.getFechaInicio()),
							cargaMasivaDatosBTItem.getFechaFinal());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// CalendarioAutomatico ()

				CalendarioEfectivo calendarioEfectivo = new CalendarioEfectivo(
						new SimpleDateFormat("dd/MM/yyyy").format(cargaMasivaDatosBTItem.getFechaInicio()),
						new SimpleDateFormat("dd/MM/yyyy").format(cargaMasivaDatosBTItem.getFechaFinal()), duracion,
						unidadesDuracion, periodo, unidadesPeriodo, seleccionLaborables, seleccionFestivos,
						listaFestivos);
				ArrayList<PeriodoEfectivoItem> periodoOk;
				ArrayList<PeriodoEfectivoItem> periodoNoOk;
				if (periodo != 0) {
					periodoOk = generarCalendarioEfectivo2(true, unidadesPeriodo,
							cargaMasivaDatosBTItem.getFechaInicio(), cargaMasivaDatosBTItem.getFechaFinal(), duracion,
							unidadesDuracion, periodo, calendarioEfectivo);
					periodoNoOk = generarCalendarioEfectivo2(false, unidadesPeriodo,
							cargaMasivaDatosBTItem.getFechaInicio(), cargaMasivaDatosBTItem.getFechaFinal(), duracion,
							unidadesDuracion, periodo, calendarioEfectivo);

				} else {
					periodoOk = generarCalendarioEfectivoSinPeriodo(true, unidadesPeriodo,
							cargaMasivaDatosBTItem.getFechaInicio(), cargaMasivaDatosBTItem.getFechaFinal(), duracion,
							unidadesDuracion, periodo, calendarioEfectivo);
					periodoNoOk = generarCalendarioEfectivoSinPeriodo(false, unidadesPeriodo,
							cargaMasivaDatosBTItem.getFechaInicio(), cargaMasivaDatosBTItem.getFechaFinal(), duracion,
							unidadesDuracion, periodo, calendarioEfectivo);

				}

				// Boolean cumpleFestivoOlaboral =
				// calendarioEfectivo.cumple(cargaMasivaDatosBTItem.getFechaEfectiva());

//				en una guardia de duración 1 semana con separación de los días de guardia, dado un calendario con fechas 01/09/2021 al 30/09/2021, 
				// las guardias de colegiado generadas comenzarán el 06/09 y terminarán el 03/10
				// (4 colegiados que tendrán 06/09-12/09, 13/09-19/09, 20/09-26/09,
				// 27/09-03/10 respectivamente).
//				Esto afecta también a la carga porque si te suben el día 03/09, ese día habría que rechazarlo porque realmente pertenece a agosto. 
				// Y si te suben el 03/10, todavía es válido para dentro del calendario de
				// septiembre.

				boolean cumpleSeparacion = true; // TODO
				if (periodoOk.size() != 0) {
					PeriodoEfectivoItem pefirst = periodoOk.get(0);
					TreeSet tsf = pefirst.ConjuntoDias;

					PeriodoEfectivoItem pelast = periodoOk.get(periodoOk.size() - 1);
					TreeSet tsl = pelast.ConjuntoDias;
					try {
						Hashtable miHash = new Hashtable();
						if (idInstitucion != null) {
							miHash.put("IDINSTITUCION", idInstitucion);
						}
						if (idGuardia != null) {
							miHash.put("IDGUARDIA", idGuardia);
						}
						if (idTurno != null) {
							miHash.put("IDTURNO", idTurno);
						}
						if (tsf.first() != null) {
							miHash.put("FECHAINICIO", new SimpleDateFormat("dd/MM/yyyy").format(tsf.first()));
						}
						if (tsl.last() != null) {
							miHash.put("FECHAFIN", new SimpleDateFormat("dd/MM/yyyy").format(tsl.last()));
						}
						if (cargaMasivaDatosBTItem.getIdPersona() != null) {
							miHash.put("IDPERSONA", cargaMasivaDatosBTItem.getIdPersona().toString());
						}

						cumpleSeparacion = validarSeparacionGuardias(miHash);

					} catch (Exception e) {
						throw new Exception(e + ": Excepcion en haySeparacionDias.");
					}
				}

				// if (periodoOk.size() != 0 && cumpleSeparacion && periodoNoOk.size() == 0) {
				if (periodoOk.size() != 0 && cumpleSeparacion) {

					// 3. Si el colegiado no está inscrito en la guardia para la fecha indicada,
					// dará error.
					BusquedaInscripcionMod inscripciones = new BusquedaInscripcionMod();
					inscripciones.setIdpersona(cargaMasivaDatosBTItem.getIdPersona());
					inscripciones.setIdinstitucion(cargaMasivaDatosBTItem.getIdInstitucion());
					inscripciones.setIdturno(idTurno);
					inscripciones.setIdguardia(idGuardia);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					List<ScsInscripcionguardia> ig = scsInscripcionguardiaExtendsMapper.checkInscripcionesRangoFecha(
							inscripciones, idInstitucion.toString(),
							simpleDateFormat.format(cargaMasivaDatosBTItem.getFechaInicio()),
							simpleDateFormat.format(cargaMasivaDatosBTItem.getFechaFinal()));
					// EN LA QUERY ANTERIOR: Tener en cuenta las fechas de inscripción efectiva de
					// alta a futuro:
					// solo se puede aceptar una guardia si el colegiado indicado está inscrito con
					// fecha de alta efectiva (fechavalidacion) anterior o igual a la fechainicio
					// de la guardia y con fecha de baja efectiva (fechabaja) posterior o igual a la
					// fechafin de la guardia (o bien sin fecha de baja efectiva).

					if (ig != null) {

					} else {
						errorLinea.append("el colegiado no está inscrito en la guardia para la fecha indicada");
					}
				} else {
					if (periodoNoOk.size() != 0) {
						String dateNoCumpleList = "";
						periodoNoOk.forEach(dateNoCumple -> {
							dateNoCumpleList.concat(dateNoCumple.toString() + ", ");
						});
						errorLinea.append("Las fechas " + dateNoCumpleList
								+ " no se corresponden con la forma de generación del calendario");
					} else {
						errorLinea.append(
								"Las fechas indicadas no se corresponden con la forma de generación del calendario");
					}
				}
			
			}else {
				errorLinea.append("No existen guardiasTurno para el idGuardia, idTurno e idInstitucion indicados");
			}

			} else {
				errorLinea.append("La guardia de colegiado ya existe en el sistema");
			}
			}

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

	private ArrayList<PeriodoEfectivoItem> generarCalendarioEfectivo2(Boolean cumple, int unidadesPeriodo,
			Date fechaInicio, Date fechaFin, int duracion, int unidadesDuracion, int periodo,
			CalendarioEfectivo calendarioEfectivo) {
		Date inicioPeriodo;
		PeriodoEfectivoItem periodoActual;
		PeriodoEfectivoItem periodoNoCumple = new PeriodoEfectivoItem();
		Date punteroFecha;
		int tiempoPendiente;
		ArrayList<PeriodoEfectivoItem> listaPeriodos = null;
		ArrayList<PeriodoEfectivoItem> listaPeriodosNo = new ArrayList<PeriodoEfectivoItem>();

		// INICIO

		// obteniendo primer dia del periodo desde el inicio
		inicioPeriodo = calendarioEfectivo.inicializarUnidadDeTiempo(unidadesPeriodo, fechaInicio);

		// recorriendo los periodos
		while (!inicioPeriodo.after(fechaFin) && duracion >= 0) // fechaFin incluido
		{
			periodoActual = new PeriodoEfectivoItem();

			// obteniendo primer dia de la duracion desde el inicio del periodo
			punteroFecha = calendarioEfectivo.inicializarUnidadDeTiempo(unidadesDuracion, inicioPeriodo);
			tiempoPendiente = duracion;

			// calculando siguiente inicio de periodo
			inicioPeriodo = calendarioEfectivo.calcularInicioSiguiente(unidadesPeriodo, inicioPeriodo, periodo,
					fechaFin);
			duracion--; // aniadido
			// recorriendo los dias
			while (tiempoPendiente > 0 && punteroFecha.before(inicioPeriodo)) {
				if (calendarioEfectivo.cumple(punteroFecha)) {
					// anyadiendo fecha efectiva al periodo actual
					periodoActual.add((Date) punteroFecha.clone());

					// si termina unidad de tiempo, restando duracion pendiente
					if (calendarioEfectivo.manyanaTerminaLaUnidadDeTiempo(unidadesDuracion, punteroFecha))
						tiempoPendiente--;
				} else {
					periodoNoCumple.add((Date) punteroFecha.clone());
				}

				// avanzando puntero hasta la siguiente fecha
				punteroFecha = calendarioEfectivo.incrementar(punteroFecha, Calendar.DATE, 1);
			} // while

			// anyadiendo periodo a la lista
			if (listaPeriodos == null)
				listaPeriodos = new ArrayList();
			if (periodoActual.iterator().hasNext())
				listaPeriodos.add(periodoActual);

			if (periodoNoCumple.iterator().hasNext())
				listaPeriodosNo.add(periodoNoCumple);

		} // while
		if (cumple) {
			return listaPeriodos;
		} else {
			return listaPeriodosNo;
		}

	} // generarCalendarioEfectivo ()

	/** Genera el Calendario Efectivo si no aplica periodo */
	private ArrayList generarCalendarioEfectivoSinPeriodo(Boolean cumple, int unidadesPeriodo, Date fechaInicio,
			Date fechaFin, int duracion, int unidadesDuracion, int periodo, CalendarioEfectivo calendarioEfectivo) {

		Date inicioSiguiente;
		PeriodoEfectivoItem periodoActual;
		Date punteroFecha;
		int tiempoPendiente;
		ArrayList<PeriodoEfectivoItem> listaPeriodos = null;
		ArrayList<PeriodoEfectivoItem> listaPeriodosNo = new ArrayList<PeriodoEfectivoItem>();
		PeriodoEfectivoItem periodoNoCumple = new PeriodoEfectivoItem();
		// INICIO

		// obteniendo primer dia del periodo desde el inicio
		punteroFecha = calendarioEfectivo.inicializarUnidadDeTiempo(unidadesDuracion, fechaInicio);
		inicioSiguiente = calendarioEfectivo.calcularInicioSiguiente(unidadesDuracion, fechaFin, duracion, fechaFin);

		// recorriendo los periodos
		while (punteroFecha.before(inicioSiguiente)) {
			periodoActual = new PeriodoEfectivoItem();
			tiempoPendiente = duracion;

			// calculando siguiente inicio de periodo
			inicioSiguiente = calendarioEfectivo.calcularInicioSiguiente(unidadesDuracion, inicioSiguiente, duracion,
					fechaFin);

			// recorriendo los dias
			while (tiempoPendiente > 0 && punteroFecha.before(inicioSiguiente)) {
				if (calendarioEfectivo.cumple(punteroFecha)) {
					// anyadiendo fecha efectiva al periodo actual
					periodoActual.add((Date) punteroFecha.clone());

					// si termina unidad de tiempo, restando duracion pendiente
					if (calendarioEfectivo.manyanaTerminaLaUnidadDeTiempo(unidadesDuracion, punteroFecha))
						tiempoPendiente--;
				}
				// si termina unidad de tiempo y las unidades no son dias, restando duracion
				// pendiente
				else if (calendarioEfectivo.manyanaTerminaLaUnidadDeTiempo(unidadesDuracion, punteroFecha)
						&& unidadesDuracion != Calendar.DAY_OF_YEAR) {
					tiempoPendiente--;
				} else {
					periodoNoCumple.add((Date) punteroFecha.clone());
					tiempoPendiente--;
				}
				// avanzando puntero hasta la siguiente fecha
				punteroFecha = calendarioEfectivo.incrementar(punteroFecha, Calendar.DATE, 1);
			} // while

			// anyadiendo periodo a la lista
			if (listaPeriodos == null)
				listaPeriodos = new ArrayList();
			if (periodoActual.iterator().hasNext())
				listaPeriodos.add(periodoActual);

			if (periodoNoCumple.iterator().hasNext())
				listaPeriodosNo.add(periodoNoCumple);

		} // while

		if (cumple) {
			return listaPeriodos;
		} else {
			return listaPeriodosNo;
		}
	} // generarCalendarioEfectivoSinPeriodo ()

	public boolean validarSeparacionGuardias(Hashtable miHash) throws Exception {
		int numeroSepacionesIncorrectas = 0;
		boolean salida = false;

		try {
			if (validacionSeparacionGuardias(miHash) != null) {
				numeroSepacionesIncorrectas = Integer.parseInt(validacionSeparacionGuardias(miHash));
				if (numeroSepacionesIncorrectas == 0)
					salida = true;
				else
					salida = false;
			} else {
				salida = false;
			}

		} catch (Exception e) {
			throw e;
		}
		return salida;
	}

	private String validacionSeparacionGuardias(Hashtable miHash) throws Exception {
		String consulta = "";
		String idinstitucion = "", idguardia = "", idturno = "", idpersona = "";
		String fechaPeriodoUltimoDia = "", fechaPeriodoPrimerDia = "", fechaPeriodoPrimerDiaOriginal = "";
		StringBuffer sBuffer;
		String fechaMIN = "", fechaMAX = "";
		String total = "";

		try {
			idinstitucion = (String) miHash.get("IDINSTITUCION").toString();
			idguardia = (String) miHash.get("IDGUARDIA").toString();
			idturno = (String) miHash.get("IDTURNO").toString();
			idpersona = (String) miHash.get("IDPERSONA").toString();
			fechaPeriodoPrimerDia = (String) miHash.get("FECHAINICIO").toString(); // Fecha del periodo de la primera
																					// guardia
			fechaPeriodoUltimoDia = (String) miHash.get("FECHAFIN").toString(); // Fecha del periodo de la ultima
																				// guardia
			fechaPeriodoPrimerDiaOriginal = (String) miHash.get("FECHAINICIO_ORIGINAL"); // Fecha del periodo de la
																							// primera guardia - solo se
																							// rellena en las permutas

			// Si tenemos fechas originales (venimos de permutas) es true.
			boolean esPermuta = miHash.containsKey("FECHAFIN_ORIGINAL") && miHash.containsKey("FECHAINICIO_ORIGINAL");

			// Consulto la maxima fecha inicio para el periodo en la cabecera de guardias:

			/*
			 * fechaMAX = scsGuardiasturnoExtendsMapper.maxFechaInicioPeriodoCabGuardia(
			 * idpersona, idinstitucion, idturno, idguardia, esPermuta,
			 * fechaPeriodoPrimerDiaOriginal, fechaPeriodoPrimerDia); String OLD_FORMAT =
			 * "yyyy-MM-dd HH:mm:ss.S"; String NEW_FORMAT = "dd/MM/yyyy"; if (fechaMAX!=
			 * null) { fechaMAX = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaMAX); } else
			 * { fechaMAX = fechaPeriodoUltimoDia; }
			 */
			// Consulto la minima fecha inicio para el periodo en la cabecera de guardias:

			/*
			 * fechaMIN =
			 * scsGuardiasturnoExtendsMapper.minFechaInicioPeriodoCabGuardia(idpersona,
			 * idinstitucion, idturno, idguardia, esPermuta, fechaPeriodoPrimerDiaOriginal,
			 * fechaPeriodoUltimoDia); if (fechaMIN!= null) { fechaMIN =
			 * changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaMIN); }else { fechaMIN =
			 * fechaPeriodoPrimerDia; }
			 */
			total = scsGuardiasturnoExtendsMapper.diasSeparacionEntreGuardias(idpersona, idinstitucion, idturno,
					idguardia, esPermuta, fechaPeriodoPrimerDiaOriginal, fechaPeriodoPrimerDia, fechaPeriodoUltimoDia);
		} catch (Exception e) {
			throw new Exception(e + ": Excepcion en ScsGuardiasColegiadoAdm.validacionIncompatibilidad(). Consulta SQL:"
					+ consulta);
		}
		return total;
	}

	List<String> obtenerFestivosTurno(Integer idInstitucion, Integer idTurno, String fechaInicio, Date fechaFin)
			throws Exception {
		LocalDate date4 = ZonedDateTime
				.parse(fechaFin.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
				.toLocalDate();
		String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd/MM/yyyy";
		String fechaFinOk = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		return scsGuardiasturnoExtendsMapper.getFestivosTurno(fechaInicio, fechaFinOk.toString(),
				idInstitucion.toString(), null, idTurno.toString());
	}

	private String changeDateFormat(String OLD_FORMAT, String NEW_FORMAT, String oldDateString) {
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = null;
		try {
			d = sdf.parse(oldDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);
		return newDateString;
	}

	private int convertirUnidadesDuracion(String tipoDiasGuardia) {
		int unidades = 0;

		try {
			switch (tipoDiasGuardia.charAt(0)) {
			case 'D':
				unidades = Calendar.DAY_OF_YEAR;
				break;// 6
			case 'S':
				unidades = Calendar.WEEK_OF_YEAR;
				break;// 3
			case 'Q':
				unidades = CalendarioEfectivo.QUINCENA;
				break;// 1524
			case 'M':
				unidades = Calendar.MONTH;
				break;// 2
			}
		} catch (Exception e) {
			unidades = 0;
		}
		return unidades;
	}

	private List<CargaMasivaDatosGuardiatem> parseExcelFileGC(Vector<Hashtable<String, Object>> datos,
			AdmUsuarios usuario) throws BusinessException {

		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargasMasivasGuardiaServiceImpl.parseExcelFileC");

		List<CargaMasivaDatosGuardiatem> masivaDatosBTVos = new ArrayList<CargaMasivaDatosGuardiatem>();
		CargaMasivaDatosGuardiatem cargaMasivaDatosGCItem = null;

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
			cargaMasivaDatosGCItem = new CargaMasivaDatosGuardiatem();

			cargaMasivaDatosGCItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
//			datosHashtable.put(SigaConstants.BT_NCOLEGIADO, "Campo de texto. Opcional, si es nulo el campo nifcif es requerido");
//			datosHashtable.put(SigaConstants.BT_NIF, "Campo de texto. Opcional, si es nulo el campo colegiado numero es requerido");

			// Comprobamos el numero de colegiado y el nif
			if (hashtable.get(SigaConstants.NCOLEGIADO) != null
					&& !hashtable.get(SigaConstants.NCOLEGIADO).toString().equals("")) {
				cargaMasivaDatosGCItem.setnColegiado((String) hashtable.get(SigaConstants.NCOLEGIADO));
				try {
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosGCItem.getnColegiado())
							.andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					cargaMasivaDatosGCItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());
				} catch (Exception e) {
					errorLinea.append("No se ha encontrado una persona con el número de colegiado introducido");
					cargaMasivaDatosGCItem.setnColegiado("Error");
				}
			} else {
				errorLinea.append("Es obligatorio el numero del colegiado.");
				cargaMasivaDatosGCItem.setNif("Error");
			}

			// Comprobamos GUARDIA
			if (hashtable.get(SigaConstants.GUARDIA) != null
					&& !hashtable.get(SigaConstants.GUARDIA).toString().equals("")) {
				try {
					List<String> idGuardiaList = scsGuardiasturnoExtendsMapper
							.getIdGuardiaByName(hashtable.get(SigaConstants.GUARDIA).toString(), idInstitucion.toString());
					String idGuardia = idGuardiaList.get(0);
					cargaMasivaDatosGCItem.setIdGuardia(idGuardia);
				} catch (Exception e) {
					errorLinea.append("La guardia introducida no tiene un formato valido.");
					cargaMasivaDatosGCItem.setIdGuardia(null);
				}
			} else {
				errorLinea.append("Es obligatorio introducir la/s guardia/s. ");
				cargaMasivaDatosGCItem.setIdGuardia(null);
			}

			// Comprobamos TURNO

			if (hashtable.get(SigaConstants.TURNO) != null
					&& !hashtable.get(SigaConstants.TURNO).toString().equals("")) {
				try {
					List<String> idTurnoList = scsGuardiasturnoExtendsMapper
							.getIdTurnoByName(hashtable.get(SigaConstants.TURNO).toString(), idInstitucion.toString());
					String idTurno = idTurnoList.get(0);
					cargaMasivaDatosGCItem.setIdTurno(idTurno);
				} catch (Exception e) {
					errorLinea.append("El turno introducido no tiene un formato valido.");
					cargaMasivaDatosGCItem.setIdTurno(null);
				}
			} else {
				errorLinea.append("Es obligatorio introducir el turno. ");
				cargaMasivaDatosGCItem.setIdTurno(null);
			}

			if (hashtable.get(SigaConstants.GRUPO) != null
					&& !hashtable.get(SigaConstants.GRUPO).toString().equals("")) {
				try {
					cargaMasivaDatosGCItem.setGrupo(hashtable.get(SigaConstants.GRUPO).toString());
				} catch (Exception e) {
					errorLinea.append("El grupo introducido no tiene un formato valido.");
					cargaMasivaDatosGCItem.setGrupo(null);
				}
			} else {
				errorLinea.append("Es obligatorio introducir el grupo. ");
				cargaMasivaDatosGCItem.setGrupo(null);
			}

			if (hashtable.get(SigaConstants.ORDEN) != null
					&& !hashtable.get(SigaConstants.ORDEN).toString().equals("")) {
				try {
					cargaMasivaDatosGCItem.setOrden(hashtable.get(SigaConstants.ORDEN).toString());
				} catch (Exception e) {
					errorLinea.append("El orden introducido no tiene un formato valido.");
					cargaMasivaDatosGCItem.setOrden(null);
				}
			} else {
				errorLinea.append("Es obligatorio introducir el orden. ");
				cargaMasivaDatosGCItem.setOrden(null);
			}

//			datosHashtable.put(SigaConstants.BT_MOTIVO, "Campo de texto. Obligatorio");
			// Si la guardia no es por grupo, dará error.
			// Grupo y orden deben tener la longitud máxima permitida para estos campos en
			// base de datos
			// Orden no se puede repetir dentro del mismo grupo, tanto los existentes como
			// los del fichero
			// Un letrado no se encuentra dos veces en el mismo grupo.
			StringBuffer errorLineaOrdenGrupo = CheckGrupoOrden2(cargaMasivaDatosGCItem, errorLinea, idInstitucion,
					hashtable, datos);
			errorLinea.append(errorLineaOrdenGrupo);

			if (!errorLinea.toString().isEmpty()) {
				cargaMasivaDatosGCItem
						.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaDatosBTVos.add(cargaMasivaDatosGCItem);
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
		// String path = "C:\\Users\\DTUser\\Documents\\CV" + idInstitucion +
		// "\\cargas\\";
		String path = getDirectorioFichero(idInstitucion);
		path += File.separator + "log_" + idInstitucion + "_" + cargaMasivaItem.getIdFicheroLog() + "."
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

			// Si no encuentra el fichero buscamos en la ruta de siga classique
			String pathClassique = getDirectorioFicheroSigaClassique(idInstitucion);
			pathClassique += File.separator + "log_" + idInstitucion + "_" + cargaMasivaItem.getIdFicheroLog() + "."
					+ SigaConstants.tipoExcelXls;

			File fileClassique = new File(pathClassique);

			// Preparar la descarga
			InputStream fileStreamClassique = null;

			try {
				fileStreamClassique = new FileInputStream(fileClassique);
				HttpHeaders headersClassique = new HttpHeaders();
				headersClassique.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

				headersClassique.setContentLength(fileClassique.length());
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStreamClassique),
						headersClassique, HttpStatus.OK);

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

	public String getNuevoIdCalProg() throws Exception {
		String nuevoId = "";

		try {
			nuevoId = scsGuardiasturnoExtendsMapper.nextIdCalprog();
		} catch (Exception e) {
			throw new Exception(e + ": Error al obtener nuevo id calendarios programados");
		}

		return nuevoId;
	} // getNuevoIdSaltoCompensacionGrupo()
	
	
 
}
