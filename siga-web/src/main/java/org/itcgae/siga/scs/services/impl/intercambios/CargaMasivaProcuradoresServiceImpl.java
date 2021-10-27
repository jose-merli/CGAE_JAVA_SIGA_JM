package org.itcgae.siga.scs.services.impl.intercambios;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosPDItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionturnoKey;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsIntercambiosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.impl.ejg.BusquedaRemesasServiceImpl;
import org.itcgae.siga.scs.services.intercambios.ICargaMasivaProcuradores;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class CargaMasivaProcuradoresServiceImpl implements ICargaMasivaProcuradores {

	private Logger LOGGER = Logger.getLogger(BusquedaRemesasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsIntercambiosExtendsMapper scsIntercambiosExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Override
	public InputStreamResource descargarModelo(HttpServletRequest request)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		ByteArrayInputStream byteInput = crearExcel();

		return new InputStreamResource(byteInput);

	}

	private ByteArrayInputStream crearExcel() {
		List<String> cabeceraExcel = Arrays.asList("CODIGODESIGNAABOGADO", "NUMEJG", "NUMCOLPROCURADOR",
				"NUMDESIGNAPROCURADOR", "FECHADESIGPROCURADOR", "OBSERVACIONES");
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Modelo Carga Masiva Procuradores");

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < cabeceraExcel.size(); col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cabeceraExcel.get(col));
			}

			Row row = sheet.createRow(1);

			row.createCell(0).setCellValue("nnnn/nnnnn");
			row.createCell(1).setCellValue("nnnn/nnnnn");
			row.createCell(2).setCellValue("nnnnn");
			row.createCell(3).setCellValue("nnnnn");
			row.createCell(4).setCellValue("dd/mm/yyyy");
			row.createCell(5).setCellValue("aaaaaaaaaaaaa");

			Row row2 = sheet.createRow(2);

			row2.createCell(0).setCellValue("Requerido");
			row2.createCell(1).setCellValue("Opcional");
			row2.createCell(2).setCellValue("Requerido");
			row2.createCell(3).setCellValue("Opcional");
			row2.createCell(4).setCellValue("Requerido");
			row2.createCell(5).setCellValue("Opcional");

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Error al crear el archivo Excel: " + e.getMessage());
		}
	}

	@Override
	public CargaMasivaProcuradorDTO listado(CargaMasivaProcuradorBusquedaItem cargaMasivaItem,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CargaMasivaProcuradorDTO cargaMasivaProcuradorDTO = new CargaMasivaProcuradorDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"listado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"listado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"listado() / ScsRemesasExtendsMapper.buscarRemesas() -> Entrada a ScsIntercambiosExtendsMapper para obtener las Cargas Maivas de Procuradores");

			List<CargaMasivaProcuradorItem> cargaMasivaProcurador = scsIntercambiosExtendsMapper
					.listadoCargaMasivaProcuradores(cargaMasivaItem, idInstitucion);

			LOGGER.info(
					"listado() / ScsRemesasExtendsMapper.buscarRemesas() -> Salida a ScsRemesasExtendsMapper para obtener las Cargas Maivas de Procuradores");

			if (cargaMasivaProcurador != null) {
				cargaMasivaProcuradorDTO.setCargaMasivaProcuradorItem(cargaMasivaProcurador);
			}

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener las Cargas Maivas de Procuradores");
		return cargaMasivaProcuradorDTO;
	}

	@Override
	public DeleteResponseDTO uploadFilePD(MultipartHttpServletRequest request) {
//		// TODO Auto-generated method stub
//		LOGGER.info("uploadFilePD() -> Entrada al servicio para subir un archivo");
//		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
//		Error error = new Error();
//		String errores = "";
//		int registrosErroneos = 0;
//		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();
//
//		// Coger archivo del request
//		LOGGER.debug("uploadFilePD() -> Coger archivo del request");
//		Iterator<String> itr = request.getFileNames();
//		MultipartFile file = request.getFile(itr.next());
//		String nombreFichero = file.getOriginalFilename();
//
//		// Extraer la información del excel
//		LOGGER.debug("uploadFilePD() -> Extraer los datos del archivo");
//		Vector<Hashtable<String, Object>> datos = ExcelHelper.parseExcelFile(file.getBytes());
//
//		CenCargamasiva cenCargamasivacv = new CenCargamasiva();
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		if (null != idInstitucion) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//			LOGGER.info(
//					"uploadFilePD() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//			LOGGER.info(
//					"uploadFilePD() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//
//				AdmUsuarios usuario = usuarios.get(0);
//
//				List<CargaMasivaDatosPDItem> cargaMasivaDatosPDItems = parseExcelFilePD(datos, usuario);
//
//				for (CargaMasivaDatosPDItem cargaMasivaDatosPDItem : cargaMasivaDatosPDItems) {
//
//					int result = -1;
//
//					// Si no se ha detectado errores leyendo el excel introducido
//					if (cargaMasivaDatosPDItem.getErrores() == null) {
//						int z = 1;						
//
//						TurnosItem t = new TurnosItem();
//						t.setIdturno(cargaMasivaDatosPDItem.getIdTurno());
//						List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t,	idInstitucion.toString(), usuario.getIdlenguaje());
//
//						//Se comprueba si el turno tiene turnos o no
//						if (!listGu.isEmpty()) {
//							
//							//Se compruaba si hay inscripciones guardias ya insertadas con los valores introducidos
//							ScsInscripcionguardia guardia = new ScsInscripcionguardia();
//
//							guardia.setFechasuscripcion(cargaMasivaDatosPDItem.getFechaEfectiva());
//							guardia.setFechavalidacion(cargaMasivaDatosPDItem.getFechaEfectiva());
//							guardia.setIdturno(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()));
//							guardia.setIdpersona(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()));
//							guardia.setIdinstitucion(idInstitucion);
//							guardia.setFechamodificacion(new Date());
//							guardia.setUsumodificacion(usuario.getIdusuario());
//
//							// Se comprueba si se ha introducido una guardia especifica. Si no es asi, se
//							// realiza con todos las guardias del turno.
//							if (cargaMasivaDatosPDItem.getIdGuardia() == null
//									|| cargaMasivaDatosPDItem.getIdGuardia().equals("")) {
//								int i = 0;
//								while (i < listGu.size() && result != 0) {
//									GuardiasItem gu = listGu.get(i);
//									guardia.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
//
//									result = scsInscripcionguardiaMapper.insert(guardia);
//									i++;
//								}
//								if (result == 0) {
//									errores += "Error insertando la peticion de la linea " + z + ". <br/>";
//									error.setDescription(errores);
//									deleteResponseDTO.setError(error);
//
//									throw new Exception("Error insertando la peticion de la linea " + z);
//								}
//							} else {
//
//								guardia.setIdguardia(Integer.parseInt(cargaMasivaDatosPDItem.getIdGuardia()));
//
//								result = scsInscripcionguardiaMapper.insert(guardia);
//
//								if (result == 0) {
//									errores += "Error insertando la peticion de la linea " + z + ". <br/>";
//									error.setDescription(errores);
//									deleteResponseDTO.setError(error);
//
//									registrosErroneos++;
//
////									throw new Exception("Error insertando la peticion de la linea "+z);
//
//								}
//							}
//
//						}
//
//						// Se busca si ya existe una incripcion a dicho turno para esa persona.
//						ScsInscripcionturnoExample exampleInscripcion = new ScsInscripcionturnoExample();
//
//						exampleInscripcion.setOrderByClause("FECHASOLICITUD DESC");
//						exampleInscripcion.createCriteria()
//								.andIdturnoEqualTo(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()))
//								.andIdinstitucionEqualTo(idInstitucion)
//								.andIdpersonaEqualTo(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()));
//
//						List<ScsInscripcionturno> inscripcionesTurno = scsInscripcionturnoMapper.selectByExample(exampleInscripcion);
//
//						if (!inscripcionesTurno.isEmpty()) {
//
//							ScsInscripcionturno inscripcionturno = inscripcionesTurno.get(0);
//
//							inscripcionturno.setFechavalidacion(cargaMasivaDatosPDItem.getFechaEfectiva());
//							inscripcionturno.setFechabaja(null);
//							inscripcionturno.setFechasolicitudbaja(null);
//							inscripcionturno.setFechamodificacion(new Date());
//							inscripcionturno.setUsumodificacion(usuario.getIdusuario());
//
//							scsInscripcionturnoMapper.updateByPrimaryKey(inscripcionturno);
//						} else {
//
//							ScsInscripcionturno inscripcionturno = new ScsInscripcionturno();
//
//							inscripcionturno.setFechasolicitud(cargaMasivaDatosPDItem.getFechaEfectiva());
//							inscripcionturno.setFechavalidacion(cargaMasivaDatosPDItem.getFechaEfectiva());
//							inscripcionturno.setFechabaja(null);
//							inscripcionturno.setFechasolicitudbaja(null);
//							inscripcionturno.setIdturno(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()));
//							inscripcionturno.setIdpersona(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()));
//							inscripcionturno.setIdinstitucion(idInstitucion);
//							inscripcionturno.setFechamodificacion(new Date());
//							inscripcionturno.setUsumodificacion(usuario.getIdusuario());
//
//							scsInscripcionturnoMapper.insert(inscripcionturno);
//						}
//
//						// Se introduce el documento en el hisotrico tanto para bajas temporales como
//						// para inscripciones.
//						insertaCenHistoricoIT(cargaMasivaDatosPDItem, usuario);
//
//					} else {
//						errores += cargaMasivaDatosPDItem.getErrores();
//						error.setDescription(errores);
//						deleteResponseDTO.setError(error);
//
//						registrosErroneos++;
//					}
//
//					Hashtable<String, Object> e = new Hashtable<String, Object>();
//					e = convertItemtoHashIT(cargaMasivaDatosPDItem);
//					// Guardar log
//					datosLog.add(e);
//				}
//
//				if (cargaMasivaDatosPDItems.isEmpty()) {
//					error.setMessage("No existen registros en el fichero.");
//					deleteResponseDTO.setStatus(SigaConstants.OK);
//				} else {
//					byte[] bytesLog = ExcelHelper.createExcelBytes(SigaConstants.CAMPOSLOGIT, datosLog);
//
//					cenCargamasivacv.setTipocarga("IT");
//					cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
//					cenCargamasivacv.setNombrefichero(nombreFichero);
//					cenCargamasivacv.setNumregistros(cargaMasivaDatosPDItems.size());
//					cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
//					cenCargamasivacv.setFechamodificacion(new Date());
//					cenCargamasivacv.setFechacarga(new Date());
//					cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());
//
//					Long idFile = uploadFileLog(file.getBytes(), cenCargamasivacv, false);
//					Long idLogFile = uploadFileLog(bytesLog, cenCargamasivacv, true);
//
//					cenCargamasivacv.setIdfichero(idFile);
//					cenCargamasivacv.setIdficherolog(idLogFile);
//
//					int result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
//
//					if (result == 0) {
//						throw new Exception("Error subiendo el fichero al repositorio");
//					}
//
//					LOGGER.info("uploadFilePD() -> Salida al servicio para subir un archivo");
//					deleteResponseDTO.setStatus(SigaConstants.OK);
//					error.setDescription(errores);
//					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
//					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
//							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
//					error.setCode(SigaConstants.CODE_200);
//				}
//			}
//		}
//
//		deleteResponseDTO.setError(error);
//
//		return deleteResponseDTO;
		return null;
	}

//	private void insertaCenHistoricoIT(CargaMasivaDatosPDItem cargaMasivaDatosPDItem, AdmUsuarios usuario) {
//		// TODO Auto-generated method stub
//		LOGGER.debug("Insertando en CEN_HISTORICO para el colegio " + usuario.getIdinstitucion() + ", idPersona "
//				+ cargaMasivaDatosPDItem.getIdPersona());
//
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//		CenHistorico cenHistorico = new CenHistorico();
//		cenHistorico.setIdinstitucion(usuario.getIdinstitucion());
//		cenHistorico.setIdpersona(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()));
//		cenHistorico.setFechaentrada(Calendar.getInstance().getTime());
//		cenHistorico.setFechaefectiva(Calendar.getInstance().getTime());
//		cenHistorico.setMotivo(null);
//		cenHistorico.setIdtipocambio(SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
//		cenHistorico.setFechamodificacion(Calendar.getInstance().getTime());
//		cenHistorico.setUsumodificacion(usuario.getIdusuario());
//
//		NewIdDTO newIdDTO = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(
//				String.valueOf(cargaMasivaDatosPDItem.getIdPersona()), String.valueOf(usuario.getIdinstitucion()));
//		if (newIdDTO == null) {
//			cenHistorico.setIdhistorico((short) 1);
//		} else {
//			int newIdCv = Integer.parseInt(newIdDTO.getNewId()) + 1;
//			cenHistorico.setIdhistorico((short) newIdCv);
//		}
//
//		StringBuffer descripcion = new StringBuffer();
//
//		GenRecursosExample genRecursosExample = new GenRecursosExample();
//		genRecursosExample.createCriteria().andIdrecursoEqualTo("historico.literal.registroNuevo")
//				.andIdlenguajeEqualTo(usuario.getIdlenguaje());
//		List<GenRecursos> genRecursos = genRecursosMapper.selectByExample(genRecursosExample);
//
//		descripcion = descripcion.append(genRecursos.get(0).getDescripcion());
//
//		descripcion.append("\n");
//		descripcion.append("- FechaEfectiva: ");
//		descripcion.append(cargaMasivaDatosPDItem.getFechaEfectiva() != null
//				? simpleDateFormat.format(cargaMasivaDatosPDItem.getFechaEfectiva())
//				: "");
//
//		descripcion.append("\n");
//		descripcion.append("- Turno: ");
//		descripcion.append(cargaMasivaDatosPDItem.getNombreTurno() != null ? cargaMasivaDatosPDItem.getNombreTurno() : "");
//		
//		descripcion.append("\n");
//		descripcion.append("- Idinstitucion: ");
//		descripcion.append(
//				cargaMasivaDatosPDItem.getIdInstitucion() != null ? cargaMasivaDatosPDItem.getIdInstitucion()
//						: "");
//		
//		descripcion.append("\n");
//		descripcion.append("- Guardia: ");
//		descripcion.append(
//				cargaMasivaDatosPDItem.getNombreGuardia() != null ? cargaMasivaDatosPDItem.getNombreGuardia()
//						: "");
//		
//		descripcion.append("\n");
//		descripcion.append("- Tipo: ");
//		descripcion.append(
//				cargaMasivaDatosPDItem.getTipo() != null ? cargaMasivaDatosPDItem.getTipo()
//						: "");
//
//		cenHistorico.setDescripcion(descripcion.toString());
//
//		if (cenHistoricoMapper.insert(cenHistorico) != 1) {
//			throw new BusinessException("No se ha insertado correctamente el registro en cenHistorico para el colegio "
//					+ usuario.getIdinstitucion() + ", idPersona " + cargaMasivaDatosPDItem.getIdPersona()
//					+ " e idTipoCambio " + SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
//		}
//	}
//
//	private Hashtable<String, Object> convertItemtoHashIT(CargaMasivaDatosPDItem cargaMasivaDatosPDItem) {
//		// TODO Auto-generated method stub
//		Date dateLog = new Date();
//		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
//
//		LOGGER.info(dateLog + ":inicio.CargaMasivaProcuradoresServiceImpl.convertItemtoHashIT");
//		Hashtable<String, Object> e = new Hashtable<String, Object>();
//				
//		if (cargaMasivaDatosPDItem.getnColegiado() != null) {
//			e.put(SigaConstants.IT_NCOLEGIADO, cargaMasivaDatosPDItem.getnColegiado());
//		}
//		if (cargaMasivaDatosPDItem.getIdTurno() != null) {
//			e.put(SigaConstants.IT_TURNO, cargaMasivaDatosPDItem.getIdTurno());
//		}
//		if (cargaMasivaDatosPDItem.getIdGuardia() != null) {
//			e.put(SigaConstants.IT_GUARDIA, cargaMasivaDatosPDItem.getIdGuardia());
//		}
//		if (cargaMasivaDatosPDItem.getTipo() != null) {
//			e.put(SigaConstants.IT_TIPO, cargaMasivaDatosPDItem.getTipo());
//		}
//		if (cargaMasivaDatosPDItem.getFechaEfectiva() != null) {
//			String fechaEfectiva = df2.format(cargaMasivaDatosPDItem.getFechaEfectiva());
//			e.put(SigaConstants.IT_FECHAEFECTIVA, fechaEfectiva);
//		}
//
//		if (cargaMasivaDatosPDItem.getErrores() != null) {
//			e.put(SigaConstants.ERRORES, cargaMasivaDatosPDItem.getErrores());
//		}
//		
//		LOGGER.info(dateLog + ":fin.CargaMasivaProcuradoresServiceImpl.convertItemtoHashIT");
//		return e;
//	}
//
//	private Long uploadFileLog(byte[] bytes, CenCargamasiva cenCargamasiva, boolean isLog) {
//		// TODO Auto-generated method stub
//		Date dateLog = new Date();
//		LOGGER.info(dateLog + ":inicio.CargaMasivaProcuradoresServiceImpl.uploadFileLog");
//		FicheroVo ficheroVo = new FicheroVo();
//
//		String directorioFichero = getDirectorioFichero(cenCargamasiva.getIdinstitucion());
//		ficheroVo.setDirectorio(directorioFichero);
//		String nombreFicheroString = cenCargamasiva.getNombrefichero();
//		ficheroVo.setNombre(nombreFicheroString); 
//		ficheroVo.setDescripcion("Carga Masiva " + ficheroVo.getNombre());
//
//		ficheroVo.setIdinstitucion(cenCargamasiva.getIdinstitucion());
//		ficheroVo.setFichero(bytes);
//		ficheroVo.setExtension("xls");
//
//		ficheroVo.setUsumodificacion(Integer.valueOf(cenCargamasiva.getUsumodificacion()));
//		ficheroVo.setFechamodificacion(new Date());
//		ficherosServiceImpl.insert(ficheroVo);
//
//		if (isLog) {
//			ficheroVo.setDescripcion("log_" + ficheroVo.getDescripcion());
//			ficheroVo.setNombre("log_" + ficheroVo.getNombre());
//		}
//
//		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
//		LOGGER.info(dateLog + ":fin.CargaMasivaProcuradoresServiceImpl.uploadFileLog");
//		return ficheroVo.getIdfichero();;
//	}
//	
//	private String getDirectorioFichero(Short idInstitucion) {
//		Date dateLog = new Date();
//		LOGGER.info(dateLog + ":inicio.CargaMasivaProcuradoresServiceImpl.getDirectorioFichero");
//
//		// Extraer propiedad
//		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
//		genPropertiesExampleP.createCriteria().andParametroEqualTo("cen.cargaExcel.ficheros.path");
//		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
//		String pathCV = genPropertiesPath.get(0).getValor(); 
//		
//		StringBuffer directorioFichero = new StringBuffer(pathCV);
//		directorioFichero.append(idInstitucion);
//		directorioFichero.append(File.separator);
//
//		// Extraer propiedad
//		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
//		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaCV");
//		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
//		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());
//
//		LOGGER.info(dateLog + ":fin.CargaMasivaProcuradoresServiceImpl.getDirectorioFichero");
//		return directorioFichero.toString();
//	}

//	private List<CargaMasivaDatosPDItem> parseExcelFilePD(Vector<Hashtable<String, Object>> datos,
//			AdmUsuarios usuario) {
//		// TODO Auto-generated method stub
//		Date dateLog = new Date();
//		LOGGER.info(dateLog + ":inicio.CargaMasivaProcuradoresServiceImpl.parseExcelFilePD");
//
//		List<CargaMasivaDatosPDItem> masivaDatosPDVos = new ArrayList<CargaMasivaDatosPDItem>();
//		CargaMasivaDatosPDItem cargaMasivaDatosPDItem = null;
//
////		Hashtable<String, SubtiposCVItem> tipoCvHashtable = new Hashtable<String, SubtiposCVItem>();
////		Hashtable<String, SubtiposCVItem> subtipo1CvHashtable = new Hashtable<String, SubtiposCVItem>();
////		Hashtable<String, SubtiposCVItem> subtipo2CvHashtable = new Hashtable<String, SubtiposCVItem>();
////		Hashtable<Long, String> personaHashtable = new Hashtable<Long, String>();
//
////		SubtiposCVItem tipoCVVo = null;
////		SubtiposCVItem subtipoCV1Vo = null;
////		SubtiposCVItem subtipoCV2Vo = null;
//
//
//		Short idInstitucion = usuario.getIdinstitucion();
//		String idLenguaje = usuario.getIdlenguaje();
//
//		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
//		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);
//
//		List<Short> idInstituciones = new ArrayList<Short>();
//		idInstituciones.add(idInstitucion);
//
//		// Comprueba si la institucion que esta logeada es la 2000 si es diferente la
//		// añade a la lista de instituciones
//		if (idInstitucion != SigaConstants.IDINSTITUCION_2000) {
//			idInstituciones.add(SigaConstants.IDINSTITUCION_2000);
//		}
//
//		StringBuffer errorLinea = null;
//		int numLinea = 1;
//		for (Hashtable<String, Object> hashtable : datos) {
//			cargaMasivaDatosPDItem = new CargaMasivaDatosPDItem();
//
//			cargaMasivaDatosPDItem.setIdInstitucion(idInstitucion);
//			errorLinea = new StringBuffer();
////			SigaConstants.IT_TURNO, "Campo de texto. Obligatorio");
////			SigaConstants.IT_GUARDIA, "Campo de texto");
////			SigaConstants.IT_NCOLEGIADO, "Campo numérico. Obligatorio");
//		
//			// Turno
//			if (hashtable.get(SigaConstants.IT_TURNO) != null
//					&& !hashtable.get(SigaConstants.IT_TURNO).toString().equals("")) {
//				cargaMasivaDatosPDItem
//						.setNombreTurno((String) hashtable.get(SigaConstants.IT_TURNO));
//				try {
//					TurnosItem turnosItem = new TurnosItem();
//					turnosItem.setAbreviatura(cargaMasivaDatosPDItem.getNombreTurno());
//					List<TurnosItem> listaTur = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion);
//		
//					cargaMasivaDatosPDItem.setIdTurno(listaTur.get(0).getIdturno().toString());
//				}
//				catch (Exception e) {
//					errorLinea.append("No se ha encontrado un turno con la introducida");
//					cargaMasivaDatosPDItem.setNombreTurno("Error");
//				}
//			}
//			else{
//				errorLinea.append("Es obligatorio introducir el turno.");
//				cargaMasivaDatosPDItem.setNombreTurno("Error");
//			} 
//			
//			// Obtener guardia
//			if (hashtable.get(SigaConstants.IT_GUARDIA) != null
//					&& !hashtable.get(SigaConstants.IT_GUARDIA).toString().equals("")) {
//				cargaMasivaDatosPDItem.setNombreGuardia((String) hashtable.get(SigaConstants.IT_GUARDIA));
//				try {
//					ScsGuardiasturnoExample guardiaExample = new ScsGuardiasturnoExample();
//					guardiaExample.createCriteria().andNombreEqualTo(cargaMasivaDatosPDItem.getNombreGuardia()).
//						andIdinstitucionEqualTo(idInstitucion);
//					List<ScsGuardiasturno> listaGuar = scsGuardiasTurnoExtendsMapper.selectByExample(guardiaExample);
//		
//					if(listaGuar.get(0).getIdturno().toString().equals(cargaMasivaDatosPDItem.getIdTurno())) {
//						errorLinea.append("No se ha encontrado una guardia con el nombre introducido en el turno asociado");
//						cargaMasivaDatosPDItem.setNombreGuardia("Error");
//					}
//					else cargaMasivaDatosPDItem.setIdGuardia(listaGuar.get(0).getIdguardia().toString());
//				}
//				catch (Exception e) {
//					errorLinea.append("No se ha encontrado una guardia con el nombre introducido");
//					cargaMasivaDatosPDItem.setNombreGuardia("Error");
//				}
//			}
//			
//			// Obtener ncolegiado y extraemos id persona
//			if (hashtable.get(SigaConstants.IT_NCOLEGIADO) != null
//					&& !hashtable.get(SigaConstants.IT_NCOLEGIADO).toString().equals("")) {
//				cargaMasivaDatosPDItem.setnColegiado((String) hashtable.get(SigaConstants.IT_NCOLEGIADO));
//				
//				try {
//					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
//					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosPDItem.getnColegiado()).andIdinstitucionEqualTo(idInstitucion);
//					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);
//		
//					cargaMasivaDatosPDItem.setIdPersona(cenColegiado.get(0).getIdpersona().toString());
//		
//				} catch (Exception e) {
//					errorLinea.append("No se ha encontrado una persona con el número de colegiado introducido en su institucion. ");
//					cargaMasivaDatosPDItem.setnColegiado("Error");
//				}
//			}
//			else {
//				errorLinea.append("Es obligatorio introducir número de colegiado.");
//				cargaMasivaDatosPDItem.setnColegiado("Error");
//			}
////			SigaConstants.IT_FECHAEFECTIVA, "Obligatorio");
////			SigaConstants.IT_TIPO, "puede tener los valores ‘ALTA’ o ‘BAJA’. Obligatorio");
////			SigaConstants.IT_GRUPO, "Número de grupo para las guardias por grupo");
////			SigaConstants.IT_ORDEN, "Dentro del grupo para las guardias por grupo. Obligatorio si se rellena el Grupo");
//	
//			
//			// Comprobamos tipo 
//			if (hashtable.get(SigaConstants.IT_TIPO) != null
//					&& !hashtable.get(SigaConstants.IT_TIPO).toString().equals("")) {
//				
//				if(hashtable.get(SigaConstants.IT_TIPO).toString().equals("ALTA") ||
//						hashtable.get(SigaConstants.IT_TIPO).toString().equals("BAJA")) {
//					cargaMasivaDatosPDItem.setTipo((String) hashtable.get(SigaConstants.IT_TIPO));
//				}
//				else{
//					errorLinea.append("El valor de tipo solo puede ser \"ALTA\" o \"BAJA\".");
//					cargaMasivaDatosPDItem.setTipo("Error");
//				}
//				
//			}
//			else {
//				errorLinea.append("Es obligatorio introducir el tipo de petición a realizar.");
//				cargaMasivaDatosPDItem.setTipo("Error");
//			}
//			
//			//Comprobamos fecha efectiva
//			if (hashtable.get(SigaConstants.IT_FECHAEFECTIVA) != null
//					&& !hashtable.get(SigaConstants.IT_FECHAEFECTIVA).toString().equals("")) {
//				try {
//					cargaMasivaDatosPDItem.setFechaEfectiva(new SimpleDateFormat("dd-MM-yyyy").parse((String) hashtable.get(SigaConstants.IT_FECHAEFECTIVA)));
//				} catch (Exception e) {
//					errorLinea.append("La fecha introducida no tiene un formato valido.");
//					cargaMasivaDatosPDItem.setFechaEfectiva(null);
//				}
//				
//			}
//			else {
//				errorLinea.append("Es obligatorio introducir la fecha efectiva. ");
//				cargaMasivaDatosPDItem.setFechaEfectiva(null);
//			}
//			
////			El grupo y el orden sólo aplicarán para guardias por grupos, en otro caso, no se tendrán en cuenta.
////			• Orden no se puede repetir dentro del mismo grupo, tanto los existentes como los del fichero.
//
//
//			//Comporbamos grupo y orden juntos al ser dependientes
////			if (hashtable.get(SigaConstants.IT_GRUPO) != null
////					&& !hashtable.get(SigaConstants.IT_GRUPO).toString().equals("")) {
////
////				cargaMasivaDatosPDItem.setGrupo((String) hashtable.get(SigaConstants.IT_GRUPO));
////				//Comprobamos longitud de entrada
////				if(cargaMasivaDatosPDItem.getGrupo().length()<=5) {
////					errorLinea.append("En valor en la columna \"GRUPO\" debe tener menos de seis cifras.");
////					cargaMasivaDatosPDItem.setGrupo("Error");
////				}
////				else {
////					if(cargaMasivaDatosPDItem.getIdGuardia().equals("") || cargaMasivaDatosPDItem.getIdGuardia().equals(null)) {
////						TurnosItem t = new TurnosItem();
////						t.setIdturno(cargaMasivaDatosPDItem.getIdTurno());
////						List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
////						for(GuardiasItem gu : listGu) {
////						//Comprobamos que todas las guardias de un turno estan en un grupo. Se cambian los valores pero no se añade un error para que no impida su posible insercion
////							List<cargaMasivaDatosPDItem> group = scsInscripcionguardiaExtendsMapper.searchGrupoGuardia(idInstitucion, gu.getIdGuardia());
////							if(group.isEmpty()) {
////								cargaMasivaDatosPDItem.setOrden("Error");
////								cargaMasivaDatosPDItem.setGrupo("Error");
////							}
////						}
////					}
////					else {
////						CheckGrupoOrden(cargaMasivaDatosPDItem, errorLinea, idInstitucion, hashtable, datos);
////					}
////				}
////			}
//			
//			//Comprobamos según su tipo (ALTA o BAJA) y unicamente si no tiene otros errrores
//			if(errorLinea.toString().isEmpty() && hashtable.get(SigaConstants.IT_TIPO).toString().equals("ALTA")) {
//				TurnosItem t = new TurnosItem();
//				t.setIdturno(cargaMasivaDatosPDItem.getIdTurno());
//				List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
//				
//				//Comprobamos el tipo de guardias asociadas al turno. En caso de ser obligatorias (valor 0), no se realizarian las inscripciones.  2- A elegir; 1-Todas o ninguna;
//				if(!listGu.isEmpty()) {
//					if(!listGu.get(0).getObligatoriedad().equals("0")) {
//					
//						ScsInscripcionturnoKey key2 = new ScsInscripcionturnoKey();
//						key2.setIdinstitucion(idInstitucion);
//						key2.setIdpersona(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()));
//						key2.setFechasolicitud(cargaMasivaDatosPDItem.getFechaEfectiva());
//						key2.setIdturno(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()));
//						
//						ScsInscripcionturno instur = new ScsInscripcionturno();
//						
//						instur = scsInscripcionturnoMapper.selectByPrimaryKey(key2);
//						
//						//Comprobamos si ya exite inscripcion a dicho turno. Si no existe, no se inscriben las guardias.
////						if(instur != null) {
//							
//							ScsInscripcionguardiaKey key1= new ScsInscripcionguardiaKey();
//							
//							key1.setFechasuscripcion(cargaMasivaDatosPDItem.getFechaEfectiva());
//							key1.setIdturno(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()));
//							key1.setIdpersona(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()));
//							key1.setIdinstitucion(idInstitucion);
//							
//							//Si la tabla especifica alguna guardia, se comprueba solo una guardia 
//							if(cargaMasivaDatosPDItem.getIdGuardia()!=null && cargaMasivaDatosPDItem.getIdGuardia()!="") {
//								
//								key1.setIdguardia(Integer.parseInt(cargaMasivaDatosPDItem.getIdGuardia()));
//								
//								//Se comprueba si la inscripción en la guardia ya existe de alta para la fecha efectiva indicada
//								if(scsInscripcionguardiaMapper.selectByPrimaryKey(key1)==null) {
//								
//									//Se comprueba que la fecha efectiva es mayor o igual a la fecha efectiva de la inscripcion al turno correspondiente .
//									if(instur != null && cargaMasivaDatosPDItem.getFechaEfectiva().compareTo(instur.getFechavalidacion())<0) errorLinea.append("La fecha efectiva introducida es anterior a la fecha efectiva de la inscripcion al turno asociado.");
//								}
//								else errorLinea.append("Ya existe una inscripcion a dicha guardia con las mismas caracteristicas.");
//							}
//							//Si no se introduce una guardia en especifico, se comprueban todas las guardias de dicho turno
//							else {
//								for(GuardiasItem gu : listGu) {
//									
//									key1.setIdguardia(Integer.parseInt(gu.getIdGuardia()));
//										
//									//Se comprueba si la inscripción en la guardia ya existe de alta para la fecha efectiva indicada
//									if(scsInscripcionguardiaMapper.selectByPrimaryKey(key1)!=null) errorLinea.append("Ya existe una inscripcion a la guardia "+gu.getNombre()+" con las mismas caracteristicas.");
//								}
//							}
//							//Se comprueba que la fecha efectiva es mayor o igual a la fecha efectiva del turno correspondiente.
//							if(instur != null && cargaMasivaDatosPDItem.getFechaEfectiva().compareTo(instur.getFechavalidacion())<0) errorLinea.append("La fecha efectiva introducida es anterior a la fecha efectiva de la inscripcion al turno asociado.");
//						
////						}
////						else errorLinea.append("El colegiado no esta inscrito en el turno indicado.");
//					}
//					else errorLinea.append("Las guardias en el turno son obligatorias por lo que no se puede realizar la fila.");
//				}
////				else errorLinea.append("No se ha encontrado guardias asociadas al turno indicado.");
//			}
//			else if(errorLinea.toString().isEmpty() && hashtable.get(SigaConstants.IT_TIPO).toString().equals("BAJA")) {
//				
//				TurnosItem t = new TurnosItem();
//				t.setIdturno(cargaMasivaDatosPDItem.getIdTurno());
//				List<GuardiasItem> listGu = scsGuardiasTurnoExtendsMapper.searchGuardias(t, idInstitucion.toString(), usuario.getIdlenguaje());
//				
//				//Comprobamos el tipo de guardias asociadas al turno. En caso de ser obligatorias (0) o Todas o ninguna (1), no se realizarian las inscripciones.  2- A elegir; 1-Todas o ninguna;
//				if(!listGu.isEmpty()) {
//					if(!listGu.get(0).getObligatoriedad().equals("0")) {
//						if(!listGu.get(0).getObligatoriedad().equals("1")) {
//					
//							
//							
//							//Comprobamos si se introdujo una guardia o no.
//							if(cargaMasivaDatosPDItem.getIdGuardia() == null) {
//								
//									ScsInscripcionguardiaExample example1= new ScsInscripcionguardiaExample();
//									
//									example1.setOrderByClause("FECHASOLICITUD DESC");
//									example1.createCriteria().andIdturnoEqualTo(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()))
//									.andIdpersonaEqualTo(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()))
//									.andIdinstitucionEqualTo(idInstitucion);
//									
//									List<ScsInscripcionguardia> insGur = scsInscripcionguardiaMapper.selectByExample(example1);
//									
//									if(listGu.get(0).getIdTurno().toString().equals(cargaMasivaDatosPDItem.getIdTurno())) {
//										errorLinea.append("No se ha encontrado una guardia con el nombre introducido en el turno asociado");
//										cargaMasivaDatosPDItem.setNombreGuardia("Error");
//									}
//									
//									//Se comprueba si la inscripción en la guardia no existe
//									if(insGur.size()!=listGu.size()) errorLinea.append("El colegiado no esta inscrito en todas las guardias del turno.");
//									//Se comprueba si ya esta de baja
//									else {
//										for(ScsInscripcionguardia guardia: insGur) {
//											if(guardia.getFechabaja() != null)errorLinea.append("El colegiado ya dió de baja la inscripcion en alguna guardia del turno.");
//										}
//									}
//								
//							}
//							else {
//								
//								ScsInscripcionguardiaExample example1= new ScsInscripcionguardiaExample();
//								
//								example1.setOrderByClause("FECHASOLICITUD DESC");
//								example1.createCriteria().andIdturnoEqualTo(Integer.parseInt(cargaMasivaDatosPDItem.getIdTurno()))
//								.andIdpersonaEqualTo(Long.parseLong(cargaMasivaDatosPDItem.getIdPersona()))
//								.andIdinstitucionEqualTo(idInstitucion).andIdguardiaEqualTo(Integer.valueOf(cargaMasivaDatosPDItem.getIdGuardia()));
//								
//								List<ScsInscripcionguardia> insGur = scsInscripcionguardiaMapper.selectByExample(example1);
//								
//								if(listGu.get(0).getIdTurno().toString().equals(cargaMasivaDatosPDItem.getIdTurno())) {
//									errorLinea.append("No se ha encontrado una guardia con el nombre introducido en el turno asociado");
//									cargaMasivaDatosPDItem.setNombreGuardia("Error");
//								}
//								
//								//Se comprueba si la inscripción en la guardia no existe
//								if(insGur.size()==0) errorLinea.append("El colegiado no esta inscrito en la guardia introducida.");
//								//Se comprueba si ya esta de baja
//								else {
//									for(ScsInscripcionguardia guardia: insGur) {
//										if(guardia.getFechabaja() != null)errorLinea.append("El colegiado ya dió de baja la inscripcion en esa guardia del turno.");
//									}
//								}
//							}
//						}
//						else errorLinea.append("Las guardias en el turno son \"Todas o ninguna\" por lo que no se puede realizar la fila.");
//					}
//					else errorLinea.append("Las guardias en el turno son \"Obligatorias\" por lo que no se puede realizar la fila.");
//				}
////				else errorLinea.append("No se ha encontrado guardias asociadas al turno indicado.");
//			}
//
//			if (!errorLinea.toString().isEmpty()) {
//				cargaMasivaDatosPDItem
//						.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
//			}
//
//			masivaDatosPDVos.add(cargaMasivaDatosPDItem);
//			numLinea++;
//		}
//
//		LOGGER.info(dateLog + ":fin.CargaMasivaProcuradoresServiceImpl.parseExcelFilePD");
//		return masivaDatosPDVos;
//	}

	@Override
	public InputStreamResource descargarFicheros(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InputStream fileStream = null;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !cargaMasivaProcuradorItem.isEmpty()) {
				fileStream = getZipFileRemesasResultados(cargaMasivaProcuradorItem, idInstitucion);				
			}

		} catch (Exception e) {
			LOGGER.error(
					"descargarFicheros() -> Se ha producido un error al descargar archivos asociados a la carga masiva de procuradores",
					e);
		}

		return new InputStreamResource(fileStream);
	}

	private InputStream getZipFileRemesasResultados(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem,
			Short idInstitucion) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (CargaMasivaProcuradorItem doc : cargaMasivaProcuradorItem) {
				if (doc.getNombreFichero() != null) {
					String nextEntry;
					if (doc.getNombreFichero().contains(".")) {
						nextEntry = doc.getNombreFichero().substring(0, doc.getNombreFichero().lastIndexOf("."));
						nextEntry += "-" + doc.getIdCargaMasiva();
						String extension = doc.getNombreFichero()
								.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
								.toLowerCase();
						nextEntry += extension;
					} else {
						nextEntry = doc.getNombreFichero();
					}

					String path = getDirectorioFicheroRemesa(idInstitucion, doc.getIdCargaMasiva());
					path += File.separator + doc.getNombreFichero();
					File file = new File(path);
					if (file.exists() && !file.isDirectory()) {
						zipOutputStream.putNextEntry(new ZipEntry(nextEntry));
						FileInputStream fileInputStream = new FileInputStream(file);
						IOUtils.copy(fileInputStream, zipOutputStream);
						fileInputStream.close();
					}
					zipOutputStream.closeEntry();
				}
			}

			zipOutputStream.closeEntry();

			if (zipOutputStream != null) {
				zipOutputStream.finish();
				zipOutputStream.flush();
				IOUtils.closeQuietly(zipOutputStream);
			}

			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	private String getDirectorioFicheroRemesa(Short idInstitucion, int idCargaMasiva) {
		// TODO Auto-generated method stub
		// Extraemos el path para los ficheros
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("cajg.directorioFisicoCAJG");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String path = genPropertiesPath.get(0).getValor();

		GenPropertiesExample genPropertiesExamplePG = new GenPropertiesExample();
		genPropertiesExamplePG.createCriteria().andParametroEqualTo("cajg.directorioCAJGJava");
		List<GenProperties> genPropertiesPathP = genPropertiesMapper.selectByExample(genPropertiesExamplePG);
		path += genPropertiesPathP.get(0).getValor();
		path += File.separator + idInstitucion + File.separator + "cargaMasivaProcuradores";
		path += File.separator + idCargaMasiva;

		LOGGER.info("getDirectorioFicheroRemesa() -> Path del directorio de ficheros CargaMasivaProcuradores  = " + path);

		return path;
	}

}
