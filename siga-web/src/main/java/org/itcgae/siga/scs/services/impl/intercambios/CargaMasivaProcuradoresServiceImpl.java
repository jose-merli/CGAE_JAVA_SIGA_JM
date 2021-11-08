package org.itcgae.siga.scs.services.impl.intercambios;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosPDItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.entities.ScsDatosprocuradores;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsDesignaprocurador;
import org.itcgae.siga.db.entities.ScsDesignaprocuradorExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsEjgdesignaExample;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionturnoKey;
import org.itcgae.siga.db.entities.ScsProcurador;
import org.itcgae.siga.db.entities.ScsProcuradorKey;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.mappers.ScsDatosprocuradoresMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaprocuradorMapper;
import org.itcgae.siga.db.mappers.ScsProcuradorMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GenFicheroExtendsMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgdesignaMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsIntercambiosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.impl.ejg.BusquedaRemesasServiceImpl;
import org.itcgae.siga.scs.services.impl.oficio.GestionCargasMasivasOficioServiceImpl;
import org.itcgae.siga.scs.services.intercambios.ICargaMasivaProcuradores;
import org.itcgae.siga.scs.services.oficio.IGestionCargasMasivasOficio;
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
public class CargaMasivaProcuradoresServiceImpl implements ICargaMasivaProcuradores {

	private Logger LOGGER = Logger.getLogger(CargaMasivaProcuradoresServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsIntercambiosExtendsMapper scsIntercambiosExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private ScsProcuradorMapper scsProcuradorMapper;
	
	@Autowired
	private ScsDesignaMapper scsDesignaMapper;
	
	@Autowired
	private ScsEjgMapper scsEjgMapper;
	
	@Autowired
	private ScsEstadoejgMapper scsEstadoEjgMapper;
	
	@Autowired
	private ScsEjgdesignaMapper scsEjgdesignaMapper;
	
	@Autowired
	private ScsDesignaprocuradorMapper scsDesignaprocuradorMapper;
	
	@Autowired
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;
	
	@Autowired
	private ScsDatosprocuradoresMapper scsDatosprocuradoresMapper;
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	
	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	
	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
	@Override
	public InputStreamResource descargarModelo(HttpServletRequest request)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		ByteArrayInputStream byteInput = crearExcel();

		return new InputStreamResource(byteInput);

	}

	private ByteArrayInputStream crearExcel() {
		List<String> cabeceraExcel = Arrays.asList(SigaConstants.PD_CODIGODESIGNAABOGADO, SigaConstants.PD_NUMEJG, SigaConstants.PD_NUMCOLPROCURADOR,
				SigaConstants.PD_NUMDESIGNAPROCURADOR, SigaConstants.PD_FECHADESIGPROCURADOR, SigaConstants.PD_OBSERVACIONES);
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
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
			LOGGER.debug(
					"listado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"listado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"listado() / ScsRemesasExtendsMapper.buscarRemesas() -> Entrada a ScsIntercambiosExtendsMapper para obtener las Cargas Maivas de Procuradores");

			List<CargaMasivaProcuradorItem> cargaMasivaProcurador = scsIntercambiosExtendsMapper
					.listadoCargaMasivaProcuradores(cargaMasivaItem, idInstitucion);

			LOGGER.debug(
					"listado() / ScsRemesasExtendsMapper.buscarRemesas() -> Salida a ScsRemesasExtendsMapper para obtener las Cargas Maivas de Procuradores");

			if (cargaMasivaProcurador != null) {
				cargaMasivaProcuradorDTO.setCargaMasivaProcuradorItem(cargaMasivaProcurador);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener las Cargas Maivas de Procuradores");
		return cargaMasivaProcuradorDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO cargarFichero(MultipartHttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("cargarFichero() -> Entrada al servicio para subir un archivo");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();
		Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
		LOGGER.debug("cargarFichero() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String nombreFichero = file.getOriginalFilename();

		// Extraer la debugrmación del excel
		LOGGER.debug("cargarFichero() -> Extraer los datos del archivo");
		try {
			datos = ExcelHelper.parseExcelFile(file.getBytes());
		} catch (BusinessException | IOException e1) {
			// TODO Auto-generated catch block
			LOGGER.error(e1.getStackTrace());
			throw e1;
		}

		CenCargamasiva cenCargamasivacv = new CenCargamasiva();
		ScsDesignaprocurador scsDesignaprocurador = new ScsDesignaprocurador();

		// Conseguimos debugrmación del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"cargarFichero() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.debug(
					"cargarFichero() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.debug("cargarFichero() -> Extraer los datos del archivo y los convertimos en clases");
				List<CargaMasivaDatosPDItem> cargaMasivaDatosPDItems = parseExcelFilePD(datos, usuario);

				LOGGER.debug("cargarFichero() / Se obtienen los ficheros cargados en una lista de clases");
				
				if(cargaMasivaDatosPDItems.size() != 0 && !cargaMasivaDatosPDItems.isEmpty()) {
					
					for (CargaMasivaDatosPDItem cargaMasivaDatosPDItem : cargaMasivaDatosPDItems) {

						LOGGER.debug("cargarFichero() / Se van leyendo los ficheros cargados en la lista");
						
						LOGGER.debug("cargarFichero() / Si no se ha detectado errores leyendo el excel introducido");
						if (cargaMasivaDatosPDItem.getErrores() == null) {
							
							LOGGER.debug("cargarFichero() / Insertamos linea en scs_procuradorDesigna, si no existe la designacion,"
									+ " si no continuamos para asignar los procuradores a los EJG");
							if(!cargaMasivaDatosPDItem.isExisteDesigna()){
								scsDesignaprocurador = new ScsDesignaprocurador();
								scsDesignaprocurador.setIdinstitucion(cargaMasivaDatosPDItem.getIdInstitucion());
								scsDesignaprocurador.setIdturno(cargaMasivaDatosPDItem.getDesigaAbogadoIdTurno());
								scsDesignaprocurador.setAnio(cargaMasivaDatosPDItem.getDesigaAbogadoAnio());
								scsDesignaprocurador.setNumero(cargaMasivaDatosPDItem.getDesigaAbogadoNumero());

								scsDesignaprocurador.setIdprocurador(cargaMasivaDatosPDItem.getIdProcurador());
								scsDesignaprocurador.setIdinstitucionProc(cargaMasivaDatosPDItem.getIdInstitucion());
								scsDesignaprocurador.setNumerodesignacion(String.valueOf(cargaMasivaDatosPDItem.getNumDesignaProcurador()));
								scsDesignaprocurador.setFechadesigna(cargaMasivaDatosPDItem.getFechaDesignaProcurador());
								scsDesignaprocurador.setObservaciones(cargaMasivaDatosPDItem.getObservaciones());
								scsDesignaprocurador.setFechamodificacion(new Date());
								scsDesignaprocurador.setUsumodificacion(usuario.getUsumodificacion());

								scsDesignaprocuradorMapper.insert(scsDesignaprocurador);
							}

							LOGGER.debug("cargarFichero() / Insertamos el procurador en el ejg ");
							if(cargaMasivaDatosPDItem.getNumEJG()!=null){
								ScsEjgWithBLOBs ejg = new ScsEjgWithBLOBs();
								ejg.setIdinstitucion(cargaMasivaDatosPDItem.getIdInstitucion());
								ejg.setIdtipoejg((short) cargaMasivaDatosPDItem.getEjgIdTipo());
								ejg.setAnio((short) cargaMasivaDatosPDItem.getEjgAnio());
								ejg.setNumero(cargaMasivaDatosPDItem.getEjgNumero());

								ejg.setIdprocurador(cargaMasivaDatosPDItem.getIdProcurador());
								ejg.setIdinstitucionProc(cargaMasivaDatosPDItem.getIdInstitucion());
								ejg.setNumerodesignaproc(String.valueOf(cargaMasivaDatosPDItem.getNumDesignaProcurador()));
								ejg.setFechaDesProc(cargaMasivaDatosPDItem.getFechaDesignaProcurador());


								scsEjgMapper.updateByPrimaryKeySelective(ejg);
								
								ScsEstadoejg aux = scsIntercambiosExtendsMapper.getMaxIdEstadoPorEJG(ejg.getAnio().toString(), idInstitucion, ejg.getNumero().toString(), ejg.getIdtipoejg().toString());

								ScsEstadoejg scsEstadoejg = new ScsEstadoejg();
								scsEstadoejg.setIdinstitucion(ejg.getIdinstitucion());
								scsEstadoejg.setIdtipoejg(ejg.getIdtipoejg());
								scsEstadoejg.setAnio(ejg.getAnio());
								scsEstadoejg.setNumero(ejg.getNumero());
								scsEstadoejg.setAutomatico("1");
								scsEstadoejg.setIdestadoejg(new Short("9")); //REMITIDO A COMISIÓN
								scsEstadoejg.setFechainicio(new Date());
								scsEstadoejg.setFechamodificacion(new Date());
								scsEstadoejg.setUsumodificacion(usuario.getUsumodificacion());
								scsEstadoejg.setIdestadoporejg(aux.getIdestadoporejg()+1);

								scsEstadoEjgMapper.insert(scsEstadoejg);
							}
							
							LOGGER.debug("cargarFichero() / Insertamos linea en scs_Datosprocuradores");
							ScsDatosprocuradores cenDatoscv = new ScsDatosprocuradores();
							cenDatoscv.setIdinstitucion(cargaMasivaDatosPDItem.getIdInstitucion());
							cenDatoscv.setCodigodesignaabogado(cargaMasivaDatosPDItem.getCodigoDesignaAbogado());
							cenDatoscv.setFechadesigprocurador(cargaMasivaDatosPDItem.getFechaDesignaProcurador());
							cenDatoscv.setNumcolprocurador(String.valueOf(cargaMasivaDatosPDItem.getNumColProcurador()));
							cenDatoscv.setNumdesignaprocurador(String.valueOf(cargaMasivaDatosPDItem.getNumDesignaProcurador()));
							cenDatoscv.setNumejg(cargaMasivaDatosPDItem.getNumEJG());
							cenDatoscv.setObservaciones(cargaMasivaDatosPDItem.getObservaciones());
							cenDatoscv.setFechamodificacion(new Date());
							cenDatoscv.setUsumodificacion(SigaConstants.USUMODIFICACION_0);
							scsDatosprocuradoresMapper.insert(cenDatoscv);
							
							// Se introduce el documento en el historico
							LOGGER.debug("cargarFichero() -> Se introduce el documento en el historico");
							insertaCenHistoricoIT(cargaMasivaDatosPDItem, usuario);

						} else {
							errores += cargaMasivaDatosPDItem.getErrores();
							error.setDescription(errores);
							deleteResponseDTO.setError(error);

							registrosErroneos++;
						}

						LOGGER.debug("cargarFichero() / Se llama al método convertItemtoHastIT()");
						Hashtable<String, Object> e = new Hashtable<String, Object>();
						e = convertItemtoHashIT(cargaMasivaDatosPDItem);
						// Guardar log
						datosLog.add(e);
					}
					
					LOGGER.debug("cargarFichero() / Subimos el fichero");
					
					int result = 0;
					try {
						byte[] bytesLog = ExcelHelper.createExcelBytes(SigaConstants.CAMPOSLOG_PD, datosLog);

						cenCargamasivacv.setTipocarga("PD");
						cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
						cenCargamasivacv.setNombrefichero(nombreFichero);
						cenCargamasivacv.setNumregistros(cargaMasivaDatosPDItems.size());
						cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
						cenCargamasivacv.setFechamodificacion(new Date());
						cenCargamasivacv.setFechacarga(new Date());
						cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());

						Long idFile = uploadFileLog(file.getBytes(), cenCargamasivacv, false);
						Long idLogFile = uploadFileLog(bytesLog, cenCargamasivacv, true);

						cenCargamasivacv.setIdfichero(idFile);
						cenCargamasivacv.setIdficherolog(idLogFile);
						
						try {
							guardarFicheroEnDisco(file.getBytes(), cenCargamasivacv, false);
							guardarFicheroEnDisco(bytesLog, cenCargamasivacv, true);
						} catch (IOException e) {
							LOGGER.debug("Error al crear y escribir el fichero: " + e.getStackTrace());
							throw e;
						}

						result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
					}catch(IOException e) {
						LOGGER.error(e.getStackTrace());
						throw e;
					}
					
					if (result == 0) {
						throw new Exception("Error subiendo el fichero al repositorio");
					}

					deleteResponseDTO.setStatus(SigaConstants.OK);
					error.setDescription(errores);
					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
					error.setCode(SigaConstants.CODE_200);
					
				}else {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}

		deleteResponseDTO.setError(error);

		LOGGER.debug("cargarFichero() -> Salida del servicio para cargarFichero");
		
		return deleteResponseDTO;
	}
	
	@Transactional
	private void insertaCenHistoricoIT(CargaMasivaDatosPDItem cargaMasivaDatosPDItem, AdmUsuarios usuario) 
			throws BusinessException{
		
		LOGGER.debug("Insertando en CEN_HISTORICO para el colegio " + usuario.getIdinstitucion() + ", idPersona "
				+ cargaMasivaDatosPDItem.getIdPersona());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		CenHistorico cenHistorico = new CenHistorico();
		cenHistorico.setIdinstitucion(usuario.getIdinstitucion());
		cenHistorico.setIdpersona(cargaMasivaDatosPDItem.getIdPersona());
		cenHistorico.setFechaentrada(Calendar.getInstance().getTime());
		cenHistorico.setFechaefectiva(Calendar.getInstance().getTime());
		cenHistorico.setMotivo(null);
		cenHistorico.setIdtipocambio(SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		cenHistorico.setFechamodificacion(Calendar.getInstance().getTime());
		cenHistorico.setUsumodificacion(usuario.getIdusuario());

		NewIdDTO newIdDTO = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(
				String.valueOf(cargaMasivaDatosPDItem.getIdPersona()), String.valueOf(usuario.getIdinstitucion()));
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
		descripcion.append("- CodigoDesignaAbogado: ");
		descripcion.append(cargaMasivaDatosPDItem.getCodigoDesignaAbogado() != null
				? cargaMasivaDatosPDItem.getCodigoDesignaAbogado()
				: "");

		descripcion.append("\n");
		descripcion.append("- NumEJG: ");
		descripcion.append(cargaMasivaDatosPDItem.getNumEJG() != null ? cargaMasivaDatosPDItem.getNumEJG() : "");
		
		descripcion.append("\n");
		descripcion.append("- NumColProcurador: ");
		descripcion.append(
				cargaMasivaDatosPDItem.getNumColProcurador() != null ? cargaMasivaDatosPDItem.getNumColProcurador()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- NumDesignaProcurador: ");
		descripcion.append(
				cargaMasivaDatosPDItem.getNumDesignaProcurador() != null ? cargaMasivaDatosPDItem.getNumDesignaProcurador()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- FechaDesignaProcurador: ");
		descripcion.append(
				cargaMasivaDatosPDItem.getFechaDesignaProcurador() != null ? simpleDateFormat.format(cargaMasivaDatosPDItem.getFechaDesignaProcurador())
						: "");
		
		descripcion.append("\n");
		descripcion.append("- Observaciones: ");
		descripcion.append(
				cargaMasivaDatosPDItem.getObservaciones() != null ? cargaMasivaDatosPDItem.getObservaciones()
						: "");

		cenHistorico.setDescripcion(descripcion.toString());

		if (cenHistoricoExtendsMapper.insert(cenHistorico) != 1) {
			throw new BusinessException("No se ha insertado correctamente el registro en cenHistorico para el colegio "
					+ usuario.getIdinstitucion() + ", idPersona " + cargaMasivaDatosPDItem.getIdPersona()
					+ " e idTipoCambio " + SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		}

	}

	private Hashtable<String, Object> convertItemtoHashIT(CargaMasivaDatosPDItem cargaMasivaDatosPDItem) {
		// TODO Auto-generated method stub
		Date dateLog = new Date();
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

		LOGGER.debug(dateLog + " --> Inicio CargaMasivaProcuradoresServiceImpl convertItemtoHashIT");
		Hashtable<String, Object> e = new Hashtable<String, Object>();
				
		e.put(SigaConstants.PD_CODIGODESIGNAABOGADO, cargaMasivaDatosPDItem.getCodigoDesignaAbogado() != null ? cargaMasivaDatosPDItem.getCodigoDesignaAbogado() : "");

		String fechaDesignaProcurador = "";
		
		if (cargaMasivaDatosPDItem.getFechaDesignaProcurador() != null) {
			fechaDesignaProcurador = df2.format(cargaMasivaDatosPDItem.getFechaDesignaProcurador());
		}
		
		e.put(SigaConstants.PD_FECHADESIGPROCURADOR, cargaMasivaDatosPDItem.getFechaDesignaProcurador() != null ? fechaDesignaProcurador : "");

		e.put(SigaConstants.PD_NUMCOLPROCURADOR, cargaMasivaDatosPDItem.getNumColProcurador() != null ? cargaMasivaDatosPDItem.getNumColProcurador() : "");
		
		e.put(SigaConstants.PD_NUMDESIGNAPROCURADOR, cargaMasivaDatosPDItem.getNumDesignaProcurador() != null ? cargaMasivaDatosPDItem.getNumDesignaProcurador() : "");
		
		e.put(SigaConstants.PD_NUMEJG, cargaMasivaDatosPDItem.getNumEJG() != null ? cargaMasivaDatosPDItem.getNumEJG() : "");
		
		e.put(SigaConstants.PD_OBSERVACIONES, cargaMasivaDatosPDItem.getObservaciones() != null ? cargaMasivaDatosPDItem.getObservaciones() : "");
		
		e.put(SigaConstants.ERRORES, cargaMasivaDatosPDItem.getErrores() != null ? cargaMasivaDatosPDItem.getErrores() : "");
		
		
		LOGGER.debug(dateLog + " --> Fin CargaMasivaProcuradoresServiceImpl convertItemtoHashIT");
		return e;
	}

	@Transactional
	private Long uploadFileLog(byte[] bytes, CenCargamasiva cenCargamasiva, boolean isLog) throws IOException {
		// TODO Auto-generated method stub
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaProcuradoresServiceImpl uploadFileLog");
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
		
		LOGGER.debug(dateLog + " --> Fin CargaMasivaProcuradoresServiceImpl uploadFileLog");
		return ficheroVo.getIdfichero();
	}
	
	private String getDirectorioFichero(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaProcuradoresServiceImpl getDirectorioFichero");

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

		LOGGER.debug(dateLog + " --> Fin CargaMasivaProcuradoresServiceImpl getDirectorioFichero");
		return directorioFichero.toString();
	}

	private List<CargaMasivaDatosPDItem> parseExcelFilePD(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario) {
		// TODO Auto-generated method stub
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaProcuradoresServiceImpl parseExcelFilePD");

		List<CargaMasivaDatosPDItem> masivaDatosPDVos = new ArrayList<CargaMasivaDatosPDItem>();
		CargaMasivaDatosPDItem cargaMasivaDatosPDItem = null;
		
		HashMap<String, ScsProcurador> procuradorHashtable = new HashMap<String, ScsProcurador>();
		ScsProcurador scsProcurador = null;

		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);

		StringBuffer errorLinea = null;
		int numLinea = 1;
		for (Hashtable<String, Object> hashtable : datos) {
			cargaMasivaDatosPDItem = new CargaMasivaDatosPDItem();

			cargaMasivaDatosPDItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
		
			LOGGER.debug("parseExcelFilePD() / Obtenemos los datos de la columna OBSERVACIONES");
			if (hashtable.get(SigaConstants.PD_OBSERVACIONES) != null && !hashtable.get(SigaConstants.PD_OBSERVACIONES).toString().equals("") &&
				!hashtable.get(SigaConstants.PD_OBSERVACIONES).toString().equals("aaaaaaaaaaaaa") && !hashtable.get(SigaConstants.PD_OBSERVACIONES).toString().equals("Opcional")) {
				cargaMasivaDatosPDItem.setObservaciones(hashtable.get(SigaConstants.PD_OBSERVACIONES).toString());
			}
			
			LOGGER.debug("parseExcelFilePD() / Obtenemos los datos de la columna NUMDESIGNAPROCURADOR");
			if(hashtable.get(SigaConstants.PD_NUMDESIGNAPROCURADOR)!=null && !hashtable.get(SigaConstants.PD_NUMDESIGNAPROCURADOR).toString().equals("") &&
				!hashtable.get(SigaConstants.PD_NUMDESIGNAPROCURADOR).toString().equals("nnnnn") &&	!hashtable.get(SigaConstants.PD_NUMDESIGNAPROCURADOR).toString().equals("Opcional")){
				cargaMasivaDatosPDItem.setNumDesignaProcurador(hashtable.get(SigaConstants.PD_NUMDESIGNAPROCURADOR).toString());
				
				if(cargaMasivaDatosPDItem.getNumDesignaProcurador().length() != 5) {
					errorLinea.append("Numero de designa del procurador mal introducida. Debe ser nnnnn. ");
				}
			}
			
			LOGGER.debug("parseExcelFilePD() / Obtenemos los datos de la columna FECHADESIGPROCURADOR");
			if(hashtable.get(SigaConstants.PD_FECHADESIGPROCURADOR)!=null && !hashtable.get(SigaConstants.PD_FECHADESIGPROCURADOR).toString().equals("") &&
				!hashtable.get(SigaConstants.PD_FECHADESIGPROCURADOR).toString().equals("dd/mm/yyyy") && !hashtable.get(SigaConstants.PD_FECHADESIGPROCURADOR).toString().equals("Requerido")){
				try {
					cargaMasivaDatosPDItem.setFechaDesignaProcurador(new SimpleDateFormat("dd-MM-yyyy").parse(hashtable.get(SigaConstants.PD_FECHADESIGPROCURADOR).toString()));
				} catch (ParseException e1) {
					errorLinea.append("Fecha de designacion de procurador mal introducida. Debe ser dd/mm/yyyy. ");
				}
			}else{
				errorLinea.append("Es obligatorio introducir la fecha de designacion del procurador. ");
			}
			
			LOGGER.debug("parseExcelFilePD() / Obtenemos los datos de la columna NUMCOLPROCURADOR");
			if(hashtable.get(SigaConstants.PD_NUMCOLPROCURADOR)!=null && !hashtable.get(SigaConstants.PD_NUMCOLPROCURADOR).toString().equals("") &&
			   !hashtable.get(SigaConstants.PD_NUMCOLPROCURADOR).toString().equals("nnnnn") && !hashtable.get(SigaConstants.PD_NUMCOLPROCURADOR).toString().equals("Requerido")){
				cargaMasivaDatosPDItem.setNumColProcurador(hashtable.get(SigaConstants.PD_NUMCOLPROCURADOR).toString());

				
				// PREGUNTAR ESTO!!!!!!
				
				if(!procuradorHashtable.containsKey(cargaMasivaDatosPDItem.getNumColProcurador())){
					try {
						ScsProcuradorKey procuradorKey = new ScsProcuradorKey();
						
						procuradorKey.setIdinstitucion(cargaMasivaDatosPDItem.getIdInstitucion());
						procuradorKey.setIdprocurador(Long.valueOf(cargaMasivaDatosPDItem.getNumColProcurador()));
						scsProcurador  = scsProcuradorMapper.selectByPrimaryKey(procuradorKey);
					} catch (BusinessException e) {
						scsProcurador = null;
					}
				}else{
					scsProcurador = procuradorHashtable.get(cargaMasivaDatosPDItem.getNumColProcurador());	
				}
				
				// PREGUNTAR LO ANTERIOR!!!!!
				
				
				StringBuffer nombre = null;
				if(scsProcurador!=null){
					nombre = new StringBuffer();
					nombre.append(scsProcurador.getNombre());
					nombre.append(" ");
					nombre.append(scsProcurador.getApellidos1());
					if(scsProcurador.getApellidos2()!=null){
						nombre.append(" ");
						nombre.append(scsProcurador.getApellidos2());
					}
					cargaMasivaDatosPDItem.setNombreProcurador(nombre.toString());
					cargaMasivaDatosPDItem.setIdProcurador(scsProcurador.getIdprocurador());
					
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andNcolegiadoEqualTo(scsProcurador.getNcolegiado()).andIdinstitucionEqualTo(scsProcurador.getIdinstitucion());
					List<CenColegiado> cenColegiado = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					cargaMasivaDatosPDItem.setIdPersona(Long.valueOf(cenColegiado.get(0).getIdpersona().toString()));
				}else{
					cargaMasivaDatosPDItem.setNombreProcurador("Procurador no encontrado");
					errorLinea.append("Procurador no encontrado. ");

				}
				procuradorHashtable.put(String.valueOf(cargaMasivaDatosPDItem.getNumColProcurador()), scsProcurador);
			}else{
				errorLinea.append("Es obligatorio introducir número de procurador. ");

			}
			
			
			// PREGUNTAR ESTO!!!!!!
			
			LOGGER.debug("parseExcelFilePD() / Obtenemos los datos de la columna CODIGODESIGNAABOGADO");
			if(hashtable.get(SigaConstants.PD_CODIGODESIGNAABOGADO)!=null && !hashtable.get(SigaConstants.PD_CODIGODESIGNAABOGADO).toString().equals("") &&
			   !hashtable.get(SigaConstants.PD_CODIGODESIGNAABOGADO).toString().equals("nnnn/nnnnn") && !hashtable.get(SigaConstants.PD_CODIGODESIGNAABOGADO).toString().equals("Requerido")){

				String codigoDesignaAbogado = hashtable.get(SigaConstants.PD_CODIGODESIGNAABOGADO).toString();
				cargaMasivaDatosPDItem.setCodigoDesignaAbogado(codigoDesignaAbogado);
				try {
					String [] designa =  codigoDesignaAbogado.split("/");
					Short anio = Short.valueOf(designa[0]);	
					String codigo = designa[1];

					ScsDesignaExample designaExample = new ScsDesignaExample();
					designaExample.createCriteria().andAnioEqualTo(anio).andIdinstitucionEqualTo(idInstitucion).andCodigoEqualTo(codigo);
					
					List<ScsDesigna> designaciones =  scsDesignaMapper.selectByExample(designaExample);
					
					if(designaciones==null){
						throw new BusinessException("No existe la designa seleccionada");
					}else if (designaciones.size()>1){
						throw new BusinessException("El codigo de designa no es unico para ese año");
					}
					cargaMasivaDatosPDItem.setDesigaAbogadoAnio(designaciones.get(0).getAnio());
					cargaMasivaDatosPDItem.setDesigaAbogadoIdTurno(designaciones.get(0).getIdturno());
					cargaMasivaDatosPDItem.setDesigaAbogadoNumero(designaciones.get(0).getNumero());

				}  catch (BusinessException e) {
					errorLinea.append("No se ha encontrado la Designacion. ");
				}catch (Exception e) {
					errorLinea.append("Codigo de designacion mal introducido. Debe ser año/codigo(nnnn/aaaaaa). ");
				}  

			}else{
				errorLinea.append("Es obligatorio introducir número de designacion. ");
			}
			
			// PREGUNTAR LO ANTERIOR!!!!!
			
			LOGGER.debug("parseExcelFilePD() / Obtenemos los datos de la columna NUMEJG");
			if(hashtable.get(SigaConstants.PD_NUMEJG)!=null && !hashtable.get(SigaConstants.PD_NUMEJG).toString().equals("") &&
				!hashtable.get(SigaConstants.PD_NUMEJG).toString().equals("nnnn/nnnnn") && !hashtable.get(SigaConstants.PD_NUMEJG).toString().equals("Opcional")){


				String numEJg = hashtable.get(SigaConstants.PD_NUMEJG).toString();
				cargaMasivaDatosPDItem.setNumEJG(numEJg);

				try {
					String [] ejgString =  numEJg.split("/");
					Short anio = Short.valueOf(ejgString[0]);	
					String numEJG = ejgString[1];
					
					ScsEjgExample ejgExample = new ScsEjgExample();
					
					ejgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(anio).andNumejgEqualTo(numEJG);

					List<ScsEjg> ejgs =  scsEjgMapper.selectByExample(ejgExample);
					
					ScsEjg ejg = ejgs.get(0);

					if(cargaMasivaDatosPDItem.getDesigaAbogadoAnio()!=null){
						ScsEjgdesignaExample scsEjgdesignaExample = new ScsEjgdesignaExample();
						scsEjgdesignaExample.createCriteria().andAnioejgEqualTo(ejg.getAnio()).andNumeroejgEqualTo(Long.valueOf(ejg.getNumero()))
						.andIdinstitucionEqualTo(Short.valueOf(ejg.getIdinstitucion())).andIdtipoejgEqualTo(ejg.getIdtipoejg())
						.andAniodesignaEqualTo(cargaMasivaDatosPDItem.getDesigaAbogadoAnio()).andIdturnoEqualTo(cargaMasivaDatosPDItem.getDesigaAbogadoIdTurno())
						.andNumerodesignaEqualTo(cargaMasivaDatosPDItem.getDesigaAbogadoNumero());
						List<ScsEjgdesigna> scsEjgdesignas = scsEjgdesignaMapper.selectByExample(scsEjgdesignaExample);
						//Si esta relacionado con nuestra designa
						if(scsEjgdesignas!=null && scsEjgdesignas.size()>0){
							cargaMasivaDatosPDItem.setEjgAnio(ejg.getAnio());
							cargaMasivaDatosPDItem.setEjgIdTipo(ejg.getIdtipoejg());
							cargaMasivaDatosPDItem.setEjgNumero(ejg.getNumero());
						}else{
							errorLinea.append("Expediente no relacionado con la designación.");
						}
					}
				}catch (BusinessException e) {
					errorLinea.append("No se ha encontrado el EJG. ");
				}catch (Exception e) {
					errorLinea.append("Codigo de EJG mal introducido. Debe ser año/codigo(nnnn/nnnnn). ");
				}
			}	

			if (!errorLinea.toString().isEmpty()) {
				cargaMasivaDatosPDItem.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaDatosPDVos.add(cargaMasivaDatosPDItem);
			numLinea++;
		}
		
		// MCL: Se comprueba si la designacion ya existe en base de datos
		for (CargaMasivaDatosPDItem cargaMasivaDatos : masivaDatosPDVos) {
			if(cargaMasivaDatos.getIdInstitucion()!=null && !cargaMasivaDatos.getIdInstitucion().toString().equals("")
					&& cargaMasivaDatos.getDesigaAbogadoIdTurno()!=0
					&& cargaMasivaDatos.getDesigaAbogadoAnio()!=null && !cargaMasivaDatos.getDesigaAbogadoAnio().toString().equals("")
					&& cargaMasivaDatos.getDesigaAbogadoNumero()!=null && !cargaMasivaDatos.getDesigaAbogadoNumero().toString().equals("")
					&& cargaMasivaDatos.getIdProcurador()!=null && !cargaMasivaDatos.getIdProcurador().toString().equals("")
					&& cargaMasivaDatos.getFechaDesignaProcurador()!=null && !cargaMasivaDatos.getFechaDesignaProcurador().toString().equals("")){

				ScsDesignaprocuradorExample scsDesignaprocuradorExample = new ScsDesignaprocuradorExample();
				scsDesignaprocuradorExample.createCriteria().andIdinstitucionEqualTo(cargaMasivaDatos.getIdInstitucion())
				.andIdturnoEqualTo(cargaMasivaDatos.getDesigaAbogadoIdTurno())
				.andAnioEqualTo(cargaMasivaDatos.getDesigaAbogadoAnio()).andNumeroEqualTo(cargaMasivaDatos.getDesigaAbogadoNumero())
				.andIdprocuradorEqualTo(cargaMasivaDatos.getIdProcurador()).andIdinstitucionProcEqualTo(cargaMasivaDatos.getIdInstitucion())
				.andFechadesignaEqualTo(cargaMasivaDatos.getFechaDesignaProcurador());

				List<ScsDesignaprocurador> listaDesigna = scsDesignaprocuradorMapper.selectByExample(scsDesignaprocuradorExample);

				// MCL: En caso de existir se marca para continuar con el proceso y asignar el procurador al expediente EJG
				if(listaDesigna!=null && listaDesigna.size()>0){
					cargaMasivaDatos.setExisteDesigna(true);
				}

				// MCL: Se comprueba si el expediente ya esta asignado al procurador y a la designacion
				if(cargaMasivaDatos.getEjgNumero() != null && cargaMasivaDatos.getEjgIdTipo() != 0 && cargaMasivaDatos.getNumDesignaProcurador() != null){
					ScsEjgExample scsEjgExample = new ScsEjgExample();
					scsEjgExample.createCriteria().andIdinstitucionEqualTo(cargaMasivaDatos.getIdInstitucion()).andIdtipoejgEqualTo((short) cargaMasivaDatos.getEjgIdTipo())
					.andAnioEqualTo((short) cargaMasivaDatos.getEjgAnio()).andNumeroEqualTo(cargaMasivaDatos.getEjgNumero())
					.andIdprocuradorEqualTo(cargaMasivaDatos.getIdProcurador()).andIdinstitucionProcEqualTo(cargaMasivaDatos.getIdInstitucion())
					.andFechaDesProcEqualTo(cargaMasivaDatos.getFechaDesignaProcurador()).andNumerodesignaprocEqualTo(String.valueOf(cargaMasivaDatosPDItem.getNumDesignaProcurador()));

					List<ScsEjg> listaEjg = scsEjgMapper.selectByExample(scsEjgExample);

					// MCL: En caso de estar designado se muestra el mensaje de error
					if(listaEjg!=null && listaEjg.size()>0){
						String error = "El expediente ya se encuentra asignado a dicho procurador y a la designación";
						// MCL: Si tiene errores previos, se le concatena
						if(cargaMasivaDatos.getErrores()!=null && !cargaMasivaDatos.getErrores().equals("")){
							cargaMasivaDatos.setErrores(cargaMasivaDatos.getErrores() + error);
						}else{
							cargaMasivaDatos.setErrores(error);
						}
					} 
				}
			}
		}

		LOGGER.debug(dateLog + " --> Fin CargaMasivaProcuradoresServiceImpl parseExcelFilePD");
		return masivaDatosPDVos;
	}
	
	public void guardarFicheroEnDisco(byte[] bytes, CenCargamasiva cenCargamasiva, boolean isLog) throws IOException {
		
		File directorio = new File(getDirectorioFicheroSigaClassique(cenCargamasiva.getIdinstitucion()) + File.separator + cenCargamasiva.getIdcargamasiva());
		
		if(!directorio.exists()) 
			directorio.mkdirs();
		
		StringBuffer pathDirectorio = new StringBuffer(getDirectorioFicheroSigaClassique(cenCargamasiva.getIdinstitucion()));
		
		pathDirectorio.append(File.separator);
		pathDirectorio.append(isLog ? "log_" + cenCargamasiva.getIdficherolog() + "_" + cenCargamasiva.getNombrefichero() : cenCargamasiva.getIdfichero() + "_" +  cenCargamasiva.getNombrefichero());
		File file = new File(pathDirectorio.toString());
		
		OutputStream output = null;
		
		try {
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			output = new FileOutputStream(file);
			output.write(bytes);
			output.flush();
		} catch (IOException e) {
			LOGGER.debug("Error al crear y escribir el fichero: " + e.getStackTrace());
			throw e;
		}finally {
			if(output!=null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				
			output = null;
		}
	}
		
	
	private String getDirectorioFicheroSigaClassique(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaProcuradoresServiceImpl getDirectorioFicheroSigaClassique");

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

		LOGGER.debug(dateLog + " --> Fin CargaMasivaProcuradoresServiceImpl getDirectorioFicheroSigaClassique");
		return directorioFichero.toString();
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarFicheros(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("descargarFicheros() -> Entrada al servicio para generar la plantilla de errores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.debug(
					"descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");
			
			if(cargaMasivaProcuradorItem.size() == 1) {
				res = soloUnArchivo(cargaMasivaProcuradorItem, idInstitucion);
			}else {
				res = getZipFile(cargaMasivaProcuradorItem, idInstitucion);
			}
			
			LOGGER.info("descargarFicheros() -> Salida del servicio para generar la plantilla de errores");

		} catch (Exception e) {
			LOGGER.error(
					"descargarFicheros() -> Se ha producido un error al descargar archivos asociados a la carga masiva de procuradores",
					e);
		}
		
		return res;
	}

	private ResponseEntity<InputStreamResource> soloUnArchivo(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem,
			Short idInstitucion) {
		
		// Extraer el path
		//String path = "C:\\Users\\DTUser\\Documents\\CV" + idInstitucion + "\\cargas\\";
		String path = getDirectorioFichero(idInstitucion);
		path += File.separator + "log_" + cargaMasivaProcuradorItem.get(0).getIdFicheroLog() + "_" + cargaMasivaProcuradorItem.get(0).getNombreFichero();
		File file = new File(path);
		
		InputStream fileStream;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + cargaMasivaProcuradorItem.get(0).getNombreFichero());
			headers.setContentLength(file.length());
			headers.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			LOGGER.warn("descargarFicheros() -> No encuentra el fichero original en la ruta SigaNovo");

			//Si no encuentra el fichero buscamos en la ruta de siga classique
			String pathClassique = getDirectorioFicheroSigaClassique(idInstitucion);
			pathClassique += File.separator +"log_" + cargaMasivaProcuradorItem.get(0).getIdFicheroLog() + "_" + cargaMasivaProcuradorItem.get(0).getNombreFichero();
			
			File fileClassique = new File(pathClassique);

			try {
				fileStream = new FileInputStream(fileClassique);
				HttpHeaders headersClassique = new HttpHeaders();
				headersClassique.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

				headersClassique.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + cargaMasivaProcuradorItem.get(0).getNombreFichero());
				headersClassique.setContentLength(fileClassique.length());
				headersClassique.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headersClassique, HttpStatus.OK);
			
			} catch (FileNotFoundException eClassique) {
				LOGGER.warn("descargarFicheros() -> No encuentra el fichero original en la ruta SigaClassique");
			}
		}
		return res;
	}

	private ResponseEntity<InputStreamResource> getZipFile(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem,
			Short idInstitucion) {
		// TODO Auto-generated method stub
		
		HttpHeaders headersClassique = new HttpHeaders();
		headersClassique.setContentType(MediaType.parseMediaType("application/zip"));

		headersClassique.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CargaMasivaProcuradores.zip");
		headersClassique.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
		
		ByteArrayOutputStream byteArrayOutputStream = null;
		ResponseEntity<InputStreamResource> res = null;

		byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
				
		try {
			for (CargaMasivaProcuradorItem doc : cargaMasivaProcuradorItem) {
				try {
					String path = getDirectorioFichero(idInstitucion);
					path += File.separator + "log_" + doc.getIdFicheroLog() + "_" + doc.getNombreFichero();
					File file = new File(path);
					FileInputStream fileInputStream = new FileInputStream(file);
					zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
					IOUtils.copy(fileInputStream, zipOutputStream);
					fileInputStream.close();
				}catch(FileNotFoundException e) {
					//Si no encuentra el fichero buscamos en la ruta de siga classique
					String pathClassique = getDirectorioFicheroSigaClassique(idInstitucion);
					pathClassique += File.separator +"log_" + doc.getIdFicheroLog() + "_" + doc.getNombreFichero();
					
					try {
						File fileClassique = new File(pathClassique);
						zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
						FileInputStream fileInputStream = new FileInputStream(fileClassique);
						IOUtils.copy(fileInputStream, zipOutputStream);
						fileInputStream.close();
					}catch(FileNotFoundException eClassique) {
						LOGGER.warn("descargarFicheros() -> No encuentra el fichero original en la ruta SigaClassique");
						res = new ResponseEntity<InputStreamResource>(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), headersClassique, HttpStatus.NO_CONTENT);
					}
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
		
		res = new ResponseEntity<InputStreamResource>(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), headersClassique, HttpStatus.OK);

		return res;
		
		
	}
}