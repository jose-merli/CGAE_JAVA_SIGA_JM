package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDatosGFItem;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.IntegerWrapper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.StringWrapper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteKey;
import org.itcgae.siga.db.entities.CenGruposclienteExample;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.CenCargamasivaMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteMapper;
import org.itcgae.siga.db.mappers.CenHistoricoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.SIGAServicesHelperMapper;
import org.itcgae.siga.exception.BusinessException;
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
public class CargasMasivasGFServiceImpl implements ICargasMasivasGFService {

	//@Autowired
	//private ICargasMasivasGFService cargasMasivasGFService;

	@Autowired
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;

	@Autowired
	private CenGruposclienteMapper cenGruposclienteMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Autowired
	private CenGruposclienteClienteMapper cenGruposclienteClienteMapper;

	@Autowired
	private CenHistoricoMapper cenHistoricoMapper;

	@Autowired
	private CenCargamasivaMapper cenCargamasivaMapper;

	@Autowired
	private IFicherosService ficherosService;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private GenRecursosMapper genRecursosMapper;

	@Autowired
	private SIGAServicesHelperMapper sigaServicesHelperMapper;

	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	
	@Autowired
	private ExcelHelper excelHelper;

	private Logger LOGGER = Logger.getLogger(CargasMasivasGFServiceImpl.class);

	@Override
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector)
			throws BusinessException {

		LOGGER.info("createExcelFile() -> Entrada al servicio que crea la plantilla Excel");

		if (orderList == null && datosVector == null)
			throw new BusinessException("No hay datos para crear el fichero");
		if (orderList == null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = this.excelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroEjemplo);

		LOGGER.info("createExcelFile() -> Salida al servicio que crea la plantilla Excel");

		return XLSFile;

	}

	@Override
	public ResponseEntity<InputStreamResource> generateExcelEtiquetas() {

		LOGGER.info("generateExcelEtiquetas() -> Entrada al servicio para generar la plantilla Excel Etiquetas");

		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();

		// 1. Se defonen las columnas que conforman la plantilla

		// 1.1 Se rellena la primera fila
		datosHashtable.put(SigaConstants.COLEGIADONUMERO, "nnnnnn");
		datosHashtable.put(SigaConstants.PERSONANIF, "nnnnnnnna");
		datosHashtable.put(SigaConstants.C_IDGRUPO, "nnnn");
		datosHashtable.put(SigaConstants.GENERAL, "1/0");
		datosHashtable.put(SigaConstants.ACCION, "A/B");
		datosHashtable.put(SigaConstants.C_FECHAINICIO, "dd/MM/yyyy");
//		datosHashtable.put(SigaConstants.C_FECHAFIN, "dd/MM/yyyy");
		datosVector.add(datosHashtable);

		// 1.1 Se rellena la segunda fila
		datosHashtable = new Hashtable<String, Object>();
		datosHashtable.put(SigaConstants.COLEGIADONUMERO, "Opcional. Si nulo nif/cif requerido");
		datosHashtable.put(SigaConstants.PERSONANIF, "Opcional. Si nulo colegiadonumero requerido");
		datosHashtable.put(SigaConstants.C_IDGRUPO, "Requerido");
		datosHashtable.put(SigaConstants.GENERAL, "Requerido. 1 si es general, 0 si es propio del ICA");
		datosHashtable.put(SigaConstants.ACCION, "Requerido");
		datosHashtable.put(SigaConstants.C_FECHAINICIO, "Requerido si accion es A");
//		datosHashtable.put(SigaConstants.C_FECHAFIN, "Opcional");
		datosVector.add(datosHashtable);

		// 2. Crea el fichero excel
		File file = createExcelFile(SigaConstants.CAMPOSEJEMPLOGF, datosVector);

		// 3. Se convierte el fichero en array de bytes para enviarlo al front
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOGGER.info("generateExcelEtiquetas() -> Salida del servicio para generar la plantilla Excel Etiquetas");

		return res;

	}

	@Override
	public CargaMasivaDTO searchEtiquetas(CargaMasivaItem cargaMasivaItem, HttpServletRequest request) {

		LOGGER.info("searchEtiquetas() -> Entrada al servicio para obtener etiquetas");
		Error error = new Error();		
		CargaMasivaDTO cargaMasivaDTO = new CargaMasivaDTO();
		List<CargaMasivaItem> cargaMasivaItemList = new ArrayList<CargaMasivaItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {

			cargaMasivaItemList = cenCargaMasivaExtendsMapper.selectEtiquetas(idInstitucion, cargaMasivaItem);
			cargaMasivaDTO.setCargaMasivaItem(cargaMasivaItemList);
			
			if((cargaMasivaItemList != null) && (cargaMasivaItemList.size()) >= 200) {
				error.setCode(200);
				error.setDescription("La consulta devuelve más de 200 resultados, pero se muestran sólo los 200 más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
				cargaMasivaDTO.setError(error);
			}

			if (cargaMasivaItemList == null || cargaMasivaItemList.size() == 0) {

				LOGGER.warn(
						"searchEtiquetas() / cenCargaMasivaExtendsMapper.searchEtiquetas() -> No existen etiquetas con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchEtiquetas() -> idInstitucion del token nula");
		}

		return cargaMasivaDTO;
	}

	@Override
	public UpdateResponseDTO uploadFileExcel(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {

		LOGGER.info("uploadFileExcel() -> Entrada al servicio para guardar un archivo");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Controlar errores
		Error error = new Error();
		StringWrapper errores = new StringWrapper("");

		// Coger archivo del request
		LOGGER.debug("uploadFileExcel() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String nombreFichero = file.getOriginalFilename();

		// Extraer la información del excel
		LOGGER.debug("uploadFile() -> Extraer los datos del archivo");
		Vector<Hashtable<String, Object>> datos = this.excelHelper.parseExcelFile(file.getBytes());
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = null;
		Map<String, String> revisionLetradoMap = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		IntegerWrapper registrosErroneos = new IntegerWrapper(0);
		CenCargamasiva cenCargamasivaGF = new CenCargamasiva();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
		
				List<CargaMasivaDatosGFItem> cargaMasivaDatosGFItems = uploadFileExcelDev(request, updateResponseDTO, usuarios, datos, datosHashtable, errores, error, revisionLetradoMap, registrosErroneos, datosLog);
				
				uploadFileExcelCreateSaveFile(request, updateResponseDTO, cargaMasivaDatosGFItems, error, datosLog, cenCargamasivaGF, idInstitucion, nombreFichero, registrosErroneos, file, usuarios, errores);
				
			}

		}

		LOGGER.info("uploadFileExcel() -> Salida al servicio para subir un archivo");

		return updateResponseDTO;
	}
	
	@Transactional
	public List<CargaMasivaDatosGFItem> uploadFileExcelDev(MultipartHttpServletRequest request, UpdateResponseDTO updateResponseDTO, List<AdmUsuarios> usuarios, Vector<Hashtable<String, Object>> datos, Hashtable<String, Object> datosHashtable, StringWrapper errores, Error error, Map<String, String> revisionLetradoMap, IntegerWrapper registrosErroneos, Vector<Hashtable<String, Object>> datosLog)
			throws IllegalStateException, IOException {

		LOGGER.info("uploadFileExcelDev() -> Entrada al servicio para guardar un archivo");
		
		AdmUsuarios usuario = usuarios.get(0);

		List<CargaMasivaDatosGFItem> cargaMasivaDatosGFItems = parseExcelFile(datos, usuario);

		for (CargaMasivaDatosGFItem cargaMasivaDatosGFVo : cargaMasivaDatosGFItems) {
			if (cargaMasivaDatosGFVo.getErrores().isEmpty()) {
				cargaMasivaDatosGFVo.setCodIdioma(usuario.getIdlenguaje());
				cargaMasivaDatosGFVo.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
				datosHashtable = getLineaCargaMasiva(cargaMasivaDatosGFVo);
				datos.add(datosHashtable);

				if (cargaMasivaDatosGFVo.getAccion().equalsIgnoreCase(SigaConstants.ALTA)) {
					int result = 0;
					
					CenGruposclienteClienteKey key = new CenGruposclienteClienteKey();
					key.setIdgrupo(cargaMasivaDatosGFVo.getIdGrupo());
					key.setIdpersona(cargaMasivaDatosGFVo.getIdPersona());
					key.setIdinstitucion(cargaMasivaDatosGFVo.getIdInstitucion());
					key.setIdinstitucionGrupo(cargaMasivaDatosGFVo.getIdInstitucionGrupo());

					
					CenGruposclienteCliente cgcc = cenGruposclienteClienteMapper.selectByPrimaryKey(key);

					if(cgcc == null) {
					
						CenGruposclienteCliente cenGruposclienteCliente = new CenGruposclienteCliente();
						cenGruposclienteCliente.setIdpersona(cargaMasivaDatosGFVo.getIdPersona());
						cenGruposclienteCliente.setIdinstitucion(cargaMasivaDatosGFVo.getIdInstitucion());
						cenGruposclienteCliente.setIdgrupo(cargaMasivaDatosGFVo.getIdGrupo());
						cenGruposclienteCliente.setIdinstitucionGrupo(cargaMasivaDatosGFVo.getIdInstitucionGrupo());
						cenGruposclienteCliente.setFechamodificacion(new Date());
						cenGruposclienteCliente.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
						cenGruposclienteCliente.setFechaInicio(cargaMasivaDatosGFVo.getFechaInicio());
						cenGruposclienteCliente.setFechaBaja(cargaMasivaDatosGFVo.getFechaFin());

						result = cenGruposclienteClienteMapper.insert(cenGruposclienteCliente);
				
					}else {
						
						cgcc.setFechamodificacion(new Date());
						cgcc.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
						cgcc.setFechaInicio(cargaMasivaDatosGFVo.getFechaInicio());
						cgcc.setFechaBaja(cargaMasivaDatosGFVo.getFechaFin());

						result = cenGruposclienteClienteMapper.updateByPrimaryKey(cgcc);
					}
					

					if (result == 0) {
						errores.concat("Error al insertar una fila");
						error.setDescription(errores.getValue());
						updateResponseDTO.setError(error);
					}

				} else {

					CenGruposclienteCliente cenGruposclienteClienteBaja = new CenGruposclienteCliente();
					cenGruposclienteClienteBaja.setIdpersona(cargaMasivaDatosGFVo.getIdPersona());
					cenGruposclienteClienteBaja.setIdinstitucion(cargaMasivaDatosGFVo.getIdInstitucion());
					cenGruposclienteClienteBaja.setIdgrupo(cargaMasivaDatosGFVo.getIdGrupo());
					cenGruposclienteClienteBaja
							.setIdinstitucionGrupo(cargaMasivaDatosGFVo.getIdInstitucionGrupo());
//							cenGruposclienteClienteBaja.setFechamodificacion(new Date());
//							cenGruposclienteClienteBaja.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
//							cenGruposclienteClienteBaja.setFechaInicio(cargaMasivaDatosGFVo.getFechaInicio());
//							cenGruposclienteClienteBaja.setFechaBaja(new Date());

					int result = cenGruposclienteClienteMapper.deleteByPrimaryKey(cenGruposclienteClienteBaja);

					if (result == 0) {
						errores.concat("Error al insertar una fila");
						error.setDescription(errores.getValue());
						updateResponseDTO.setError(error);
					}
				}

				insertaCenHistorico(cargaMasivaDatosGFVo, usuario);

				revisionLetradoMap = new Hashtable<String, String>();
				revisionLetradoMap.put("idinstitucion", cargaMasivaDatosGFVo.getIdInstitucion().toString());
				revisionLetradoMap.put("idpersona", cargaMasivaDatosGFVo.getIdPersona().toString());
				revisionLetradoMap.put("fecha", "");
				revisionLetradoMap.put("usuario", usuario.getUsumodificacion().toString());
				List<String> outRevisionLetradoList = sigaServicesHelperMapper
						.getProcesoRevisionLetrado(revisionLetradoMap);
				for (String out : outRevisionLetradoList) {
					LOGGER.debug("RevisionLetrado:" + out);
				}

			} else {
				registrosErroneos.setValue(registrosErroneos.getValue() + 1);
			}

					byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGGF, datosLog);

		}

		LOGGER.info("uploadFileExcelDev() -> Salida al servicio para subir un archivo");
		
		return cargaMasivaDatosGFItems;
	}
	
	public void uploadFileExcelCreateSaveFile(MultipartHttpServletRequest request, UpdateResponseDTO updateResponseDTO, List<CargaMasivaDatosGFItem> cargaMasivaDatosGFItems, Error error, Vector<Hashtable<String, Object>> datosLog, CenCargamasiva cenCargamasivaGF, Short idInstitucion, String nombreFichero, IntegerWrapper registrosErroneos, MultipartFile file, List<AdmUsuarios> usuarios, StringWrapper errores) throws BusinessException, IOException {
		
		LOGGER.info("uploadFileExcelCreateSaveFile() -> Entrada al servicio para crear y guardar un fichero excel");
		
		AdmUsuarios usuario = usuarios.get(0);
		
		if (cargaMasivaDatosGFItems.isEmpty()) {
			error.setMessage("No existen registros en el fichero.");
			updateResponseDTO.setStatus(SigaConstants.OK);
			
		} else {

			byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGGF, datosLog);

			cenCargamasivaGF.setTipocarga(SigaConstants.TIPO_CARGA);
			cenCargamasivaGF.setIdinstitucion(idInstitucion);
			cenCargamasivaGF.setNombrefichero(nombreFichero);
			cenCargamasivaGF.setNumregistros((Integer) cargaMasivaDatosGFItems.size());
			cenCargamasivaGF.setNumregistroserroneos(registrosErroneos.getValue());
			cenCargamasivaGF.setFechamodificacion(new Date());
			cenCargamasivaGF.setFechacarga(new Date());
			cenCargamasivaGF.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));

			Long idFile = uploadFile(file.getBytes(), cenCargamasivaGF, false, usuario);
			Long idLogFile = uploadFile(bytesLog, cenCargamasivaGF, true, usuario);
			cenCargamasivaGF.setIdfichero(idFile);
			cenCargamasivaGF.setIdficherolog(idLogFile);

			int result = cenCargamasivaMapper.insert(cenCargamasivaGF);

			if (result == 0) {
				error.setCode(SigaConstants.CODE_400);
				errores.concat("Error al insertar en cargas masivas");
			}
			
			updateResponseDTO.setStatus(SigaConstants.OK);
			error.setDescription(errores.getValue());
			int correctos = cenCargamasivaGF.getNumregistros() - registrosErroneos.getValue();
			error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
					+ "<br/> Registros Erroneos: " + cenCargamasivaGF.getNumregistroserroneos());
			error.setCode(SigaConstants.CODE_200);
			updateResponseDTO.setError(error);
		}
		
		error.setDescription(errores.getValue());
		updateResponseDTO.setError(error);
		
		LOGGER.info("uploadFileExcelCreateSaveFile() -> Salida al servicio para crear y guardar un fichero excel");
	}

	public List<CargaMasivaDatosGFItem> parseExcelFile(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario)
			throws BusinessException {

		List<CargaMasivaDatosGFItem> masivaDatosCVVos = new ArrayList<CargaMasivaDatosGFItem>();
		CargaMasivaDatosGFItem cargaMasivaDatosGFVo = null;
		//Hashtable<String, CenGruposcliente> idGruposHashTable = new Hashtable<String, CenGruposcliente>();

		Hashtable<Long, String> personaHashtable = new Hashtable<Long, String>();
		List<String> clavesStrings = new ArrayList<String>();
		String cenGruposCliente = null;
		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);
		GenRecursosCatalogos recursosCatalogos = null;

		boolean noEncontradoGrupoFijo = false;

		List<Short> idInstituciones = new ArrayList<Short>();
		idInstituciones.add(idInstitucion);

		// Comprueba si la institucion que esta logeada es la 2000 si es diferente la
		// añade a la lista de instituciones
		if (idInstitucion != SigaConstants.IDINSTITUCION_2000) {
			idInstituciones.add(SigaConstants.IDINSTITUCION_2000);
		}

		StringBuffer errorLinea = null;
		StringBuffer clave = null;

		for (Hashtable<String, Object> hashtable : datos) {

			cargaMasivaDatosGFVo = new CargaMasivaDatosGFItem();
			cargaMasivaDatosGFVo.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();

			// Llamada a metodo para obtener idpersona
			// si la institucion es el CGAE no miramos en Colegiado porque no tiene sentido
			// ya que no va a encontrar nada

			if (hashtable.get(SigaConstants.COLEGIADONUMERO) != null
					&& !hashtable.get(SigaConstants.COLEGIADONUMERO).toString().equals(""))
				cargaMasivaDatosGFVo.setColegiadoNumero((String) hashtable.get(SigaConstants.COLEGIADONUMERO));
			if (hashtable.get(SigaConstants.PERSONANIF) != null
					&& !hashtable.get(SigaConstants.PERSONANIF).toString().equals(""))
				cargaMasivaDatosGFVo.setPersonaNif((String) hashtable.get(SigaConstants.PERSONANIF));
			if (cargaMasivaDatosGFVo.getColegiadoNumero() != null || cargaMasivaDatosGFVo.getPersonaNif() != null) {

				try {
					Long idPersona = getIdPersonaVerify(cargaMasivaDatosGFVo.getColegiadoNumero(),
							cargaMasivaDatosGFVo.getPersonaNif(), cargaMasivaDatosGFVo.getIdInstitucion());
					cargaMasivaDatosGFVo.setIdPersona(idPersona);
				} catch (Exception e) {
					errorLinea.append(e.getMessage() + ". ");
					cargaMasivaDatosGFVo.setPersonaNombre("Error");
				}

				if (cargaMasivaDatosGFVo.getIdPersona() != null) {
					String nombreString = null;
					if (!personaHashtable.containsKey(cargaMasivaDatosGFVo.getIdPersona())) {
						CenPersona cenPersona = cenPersonaMapper
								.selectByPrimaryKey(cargaMasivaDatosGFVo.getIdPersona());
						StringBuffer nombre = new StringBuffer();
						nombre.append(cenPersona.getNombre());
						nombre.append(" ");
						nombre.append(cenPersona.getApellidos1());
						if (cenPersona.getApellidos2() != null) {
							nombre.append(" ");
							nombre.append(cenPersona.getApellidos2());
						}

						cargaMasivaDatosGFVo.setPersonaNombre(nombre.toString());
						nombreString = cargaMasivaDatosGFVo.getPersonaNombre();

					} else {
						nombreString = personaHashtable.get(cargaMasivaDatosGFVo.getIdPersona());
						cargaMasivaDatosGFVo.setPersonaNombre(nombreString);
					}

					personaHashtable.put(cargaMasivaDatosGFVo.getIdPersona(), nombreString);

				}else {
					errorLinea.append("No existe la persona seleccionada");
					cargaMasivaDatosGFVo.setPersonaNombre("Error");
				}


			} else {
				errorLinea.append("Es obligatorio introducir número de colegiado o nif/cif. ");
				cargaMasivaDatosGFVo.setPersonaNombre("Error");
			}

			// Comprobacion si el colegiado pertenece al colegio general o a otro diferente
			// Y si le ha asignado una etiqueta
			short idInst = cargaMasivaDatosGFVo.getIdInstitucion();
			if ((hashtable.get(SigaConstants.GENERAL) != null
					&& !hashtable.get(SigaConstants.GENERAL).toString().equals(""))
					&& (hashtable.get(SigaConstants.GENERAL).toString().equals("1")
							|| hashtable.get(SigaConstants.GENERAL).toString().equals("0"))) {

				// Seteamos general
				cargaMasivaDatosGFVo.setGeneral(
						hashtable.get(SigaConstants.GENERAL).toString().equals(SigaConstants.DB_TRUE) ? true : false);

				if (hashtable.get(SigaConstants.GENERAL).toString().equals(SigaConstants.DB_TRUE))
					idInst = new Short(SigaConstants.IDINSTITUCION_2000);

				if (hashtable.get(SigaConstants.C_IDGRUPO) != null
						&& !hashtable.get(SigaConstants.C_IDGRUPO).toString().equals("")) {
					try {
						Short idGrupo = Short.valueOf((String) hashtable.get(SigaConstants.C_IDGRUPO));
						if (idGrupo > 9999)
							throw new NumberFormatException();
						//String key = idInst + "||" + idGrupo;
//						if (!idGruposHashTable.containsKey(key)) {
							CenGruposclienteExample cen = new CenGruposclienteExample();
							List<Short> instituciones = new ArrayList<>();
							instituciones.add(idInstitucion);
							instituciones.add(SigaConstants.IDINSTITUCION_2000);
							// Llamada a metodo para obtener idgrupo
							cen.createCriteria().andIdgrupoEqualTo(idGrupo)
							.andIdinstitucionIn(instituciones);
							
							cen.setOrderByClause("IDINSTITUCION DESC");
							
							List<CenGruposcliente> gruposCliente = cenGruposclienteMapper
									.selectByExample(cen);
							
							if (gruposCliente != null && gruposCliente.size() > 0) {
								//cenGruposCliente = gruposCliente.get(0);
								
								genRecursosCatalogosKey.setIdrecurso(gruposCliente.get(0).getNombre());
								recursosCatalogos = genRecursosCatalogosMapper
										.selectByPrimaryKey(genRecursosCatalogosKey);
								cenGruposCliente = recursosCatalogos.getDescripcion();

								if (cenGruposCliente != null) {
									cargaMasivaDatosGFVo.setIdGrupo(gruposCliente.get(0).getIdgrupo());
									cargaMasivaDatosGFVo.setIdInstitucionGrupo(gruposCliente.get(0).getIdinstitucion());
									cargaMasivaDatosGFVo.setNombreGrupo(cenGruposCliente);

								} else {
									cargaMasivaDatosGFVo.setNombreGrupo("Error");
									errorLinea.append("No se ha encontrado el grupo fijo. ");
									noEncontradoGrupoFijo = true;
								}

							} else {
								cargaMasivaDatosGFVo.setNombreGrupo("Error");
								errorLinea.append("No se ha encontrado el grupo fijo. ");
								noEncontradoGrupoFijo = true;

							}

//						} else {
//							cenGruposCliente = idGruposHashTable.get(key);
//						}
						//idGruposHashTable.put(key, cenGruposCliente);

//						cargaMasivaDatosGFVo.setIdGrupo(cenGruposCliente.getIdgrupo());
//						cargaMasivaDatosGFVo.setIdInstitucionGrupo(cenGruposCliente.getIdinstitucion());
					} catch (NumberFormatException e) {
						cargaMasivaDatosGFVo.setNombreGrupo("Error");
						errorLinea.append("El identificador de Grupo Fijo debe ser numerico de valor menor que 9999.");
					}
				} else {
					cargaMasivaDatosGFVo.setNombreGrupo("Error");
					errorLinea.append("El Grupo Fijo es campo obligatorio. ");

				}
			} else {
				errorLinea.append("Es obligatorio decir si el grupo es general(1) o propia del colegio(0). ");
				cargaMasivaDatosGFVo.setNombreGrupo("Error");
			}

			// Se comprueba si se quiere dar de alta o de baja a la etiqueta para el
			// colegiado indicado
			if (hashtable.get(SigaConstants.ACCION) != null) {
				String accion = (String) hashtable.get(SigaConstants.ACCION);
				if (!accion.equalsIgnoreCase(SigaConstants.ALTA) && !accion.equalsIgnoreCase(SigaConstants.BAJA)) {
					LOGGER.debug("La accion debe ser A o B");
					errorLinea.append("La accion debe ser ");
					errorLinea.append(SigaConstants.ALTA);
					errorLinea.append(" o ");
					errorLinea.append(SigaConstants.BAJA);
					errorLinea.append(". ");
					cargaMasivaDatosGFVo.setAccion("Error");
				} else
					cargaMasivaDatosGFVo.setAccion(accion);
			} else {
				LOGGER.debug("Accion es campo obligatorio");
				errorLinea.append("Accion es campo obligatorio. ");
			}

			// Se comprueba que la fecha inicio y fecha fin sean campos obligatorios excepto
			// cuando sea una baja
			if (hashtable.get(SigaConstants.C_FECHAINICIO) != null
					&& hashtable.get(SigaConstants.C_FECHAINICIO) != "") {

				try {
					DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy"); // for parsing input
					LOGGER.debug("DateFormat:" +df1 );
					LOGGER.debug("ValorParseado:" + (String) hashtable.get(SigaConstants.C_FECHAINICIO) );
					cargaMasivaDatosGFVo.setFechaInicio(df1.parse((String) hashtable.get(SigaConstants.C_FECHAINICIO)));

				} catch (ParseException e1) {
					LOGGER.debug("Error al parsear fecha: " + e1 );
					LOGGER.debug("Error al parsear fecha: " + SigaConstants.C_FECHAINICIO + " : " );
				}

			} else {

				if (!hashtable.get(SigaConstants.ACCION).equals("B")) {
					LOGGER.debug("Fecha inicio es campo obligatorio");
					errorLinea.append("Fecha inicio es campo obligatorio. ");
				}
			}
			// Se indica que este campo desaparece
//			if (hashtable.get(SigaConstants.C_FECHAFIN) != null && hashtable.get(SigaConstants.C_FECHAFIN) != "") {
//
//				try {
//					DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy"); // for parsing input
//					LOGGER.debug("ValorParseado:" + (String) hashtable.get(SigaConstants.C_FECHAFIN) );
//					cargaMasivaDatosGFVo.setFechaFin(df1.parse((String) hashtable.get(SigaConstants.C_FECHAFIN)));
//
//				} catch (ParseException e) {
//					LOGGER.debug("Error al parsear fecha: " + e );
//					LOGGER.debug("Error al parsear fecha: " + SigaConstants.C_FECHAFIN + " : " );
//				}
//
//			} else {
// 
//				if (!hashtable.get(SigaConstants.ACCION).equals("B")) {
//					LOGGER.debug("Fecha fin es campo obligatorio");
//					errorLinea.append("Fecha fin es campo obligatorio. ");
//				}
//			}

			if (!noEncontradoGrupoFijo) {
				// Se comprueba si el colegiado ya tiene asignada la etiqueta
				if (cargaMasivaDatosGFVo.getIdInstitucion() != null && cargaMasivaDatosGFVo.getIdPersona() != null
						&& cargaMasivaDatosGFVo.getIdInstitucionGrupo() != null
						&& cargaMasivaDatosGFVo.getIdGrupo() != null) {
					CenGruposclienteClienteKey cenGruposclienteClienteKey = new CenGruposclienteClienteKey();
					cenGruposclienteClienteKey.setIdinstitucion(cargaMasivaDatosGFVo.getIdInstitucion());
					cenGruposclienteClienteKey.setIdpersona(cargaMasivaDatosGFVo.getIdPersona());
					cenGruposclienteClienteKey.setIdinstitucionGrupo(cargaMasivaDatosGFVo.getIdInstitucionGrupo());
					cenGruposclienteClienteKey.setIdgrupo(cargaMasivaDatosGFVo.getIdGrupo());
					/** 4. Comprobacion que la persona no esta asignada ya al grupo **/
					if (cargaMasivaDatosGFVo.getAccion().equalsIgnoreCase(SigaConstants.ALTA)) {
						CenGruposclienteCliente cenGruposclienteCliente = cenGruposclienteClienteMapper
								.selectByPrimaryKey(cenGruposclienteClienteKey);
						if (cenGruposclienteCliente != null) {
							Date fechaActual = new Date();
							
							if(cenGruposclienteCliente.getFechaBaja() == null) {
								
								LOGGER.debug("process.usuario.ya.asignado");
								errorLinea.append("Usuario ya asignado a ese grupo fijo. ");
								
							}else if((cenGruposclienteCliente.getFechaInicio().before(fechaActual) && cenGruposclienteCliente.getFechaBaja().after(fechaActual)) 
									|| (cenGruposclienteCliente.getFechaInicio().before(fechaActual) && cenGruposclienteCliente.getFechaBaja().after(fechaActual))
									|| (fechaActual.compareTo(cenGruposclienteCliente.getFechaBaja()) == 0 && fechaActual.compareTo(cenGruposclienteCliente.getFechaInicio()) == 0)) {
							
								LOGGER.debug("process.usuario.ya.asignado");
								errorLinea.append("Usuario ya asignado a ese grupo fijo. ");
								
							}else {
								cargaMasivaDatosGFVo.setIdGrupo(cenGruposclienteCliente.getIdgrupo());
								cargaMasivaDatosGFVo.setIdInstitucionGrupo(cenGruposclienteCliente.getIdinstitucion());
								cargaMasivaDatosGFVo.setNombreGrupo(cenGruposCliente);
							}
						
						}
					} else if (cargaMasivaDatosGFVo.getAccion().equalsIgnoreCase(SigaConstants.BAJA)) {
						/**
						 * 5. Comprobacion que la persona existe en el grupo donde se quiere dar de baja
						 **/
						CenGruposclienteCliente cenGruposclienteCliente = cenGruposclienteClienteMapper
								.selectByPrimaryKey(cenGruposclienteClienteKey);
						if (cenGruposclienteCliente == null) {
							LOGGER.debug("process.usuario.noexiste");
							errorLinea.append("Usuario no tiene asignado ese grupo Fijo. ");
						} else {
							/**
							 * 6. Comprobacion que la persona ya esta dada de baja
							 **/
							if (cenGruposclienteCliente.getFechaBaja() != null && cenGruposclienteCliente.getFechaBaja().before(new Date())) {
								LOGGER.debug("process.usuario.noexiste");
								errorLinea.append("La etiqueta ya está dada de baja. ");
							}
						}
					}
				}
			}

			clave = new StringBuffer();

			if (cargaMasivaDatosGFVo.getIdPersona() != null && cargaMasivaDatosGFVo.getIdInstitucionGrupo() != null
					&& cargaMasivaDatosGFVo.getIdGrupo() != null && cargaMasivaDatosGFVo.getAccion() != null) {
				clave.append(cargaMasivaDatosGFVo.getIdPersona());
				clave.append("_");
				clave.append(cargaMasivaDatosGFVo.getIdInstitucionGrupo());
				clave.append("_");
				clave.append(cargaMasivaDatosGFVo.getIdGrupo());
				clave.append("_");
				clave.append(cargaMasivaDatosGFVo.getAccion().toUpperCase());
				if (clavesStrings.contains(clave.toString())) {
					LOGGER.debug(
							"La linea esta duplicada. Esta intentando asignar o desasignar el mismo grupo al mismo usuario mas de una vez");
					errorLinea.append(
							"La linea esta duplicada. Esta intentando asignar o desasignar el mismo grupo al mismo usuario mas de una vez. ");
				} else
					clavesStrings.add(clave.toString());
			}

			cargaMasivaDatosGFVo.setErrores(errorLinea.toString());
			masivaDatosCVVos.add(cargaMasivaDatosGFVo);

		}
		return masivaDatosCVVos;
	}

	public Long getIdPersonaVerify(String colegiadoNumero, String personaNif, Short idInstitucion) {

		Long idPersonaSearch = null;

		if ((personaNif == null && personaNif == "") || (colegiadoNumero == null && colegiadoNumero == "")) {
			throw new BusinessException("Ambos campos son obligatorios");
		} else if ((personaNif != null && personaNif != "") && (colegiadoNumero == null || colegiadoNumero == "")) {
			idPersonaSearch = cenClienteExtendsMapper.getIdPersonaWithNif(personaNif, idInstitucion);
		} else {
			idPersonaSearch = cenClienteExtendsMapper.getIdPersona(colegiadoNumero, personaNif, idInstitucion);
		}

		return idPersonaSearch;
	}

	private Hashtable<String, Object> getLineaCargaMasiva(CargaMasivaDatosGFItem cargaMasivaDatosCVVo) {
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
		datosHashtable.put(SigaConstants.COLEGIADONUMERO,
				cargaMasivaDatosCVVo.getColegiadoNumero() == null ? "" : cargaMasivaDatosCVVo.getColegiadoNumero());
		datosHashtable.put(SigaConstants.PERSONANIF,
				cargaMasivaDatosCVVo.getPersonaNif() == null ? "" : cargaMasivaDatosCVVo.getPersonaNif());
		datosHashtable.put(SigaConstants.PERSONANOMBRE,
				cargaMasivaDatosCVVo.getPersonaNombre() == null ? "" : cargaMasivaDatosCVVo.getPersonaNombre());
		datosHashtable.put(SigaConstants.C_IDPERSONA,
				cargaMasivaDatosCVVo.getIdPersona() == null ? "" : cargaMasivaDatosCVVo.getIdPersona());
		datosHashtable.put(SigaConstants.C_IDGRUPO,
				cargaMasivaDatosCVVo.getIdGrupo() == null ? "" : cargaMasivaDatosCVVo.getIdGrupo());
		if (cargaMasivaDatosCVVo.getIdInstitucionGrupo() == null)
			datosHashtable.put(SigaConstants.GENERAL, "");
		else
			datosHashtable.put(SigaConstants.GENERAL,
					cargaMasivaDatosCVVo.getIdInstitucionGrupo() == SigaConstants.IDINSTITUCION_2000 ? "1" : "0");
		datosHashtable.put(SigaConstants.NOMBREGRUPO,
				cargaMasivaDatosCVVo.getNombreGrupo() == null ? "" : cargaMasivaDatosCVVo.getNombreGrupo());
		datosHashtable.put(SigaConstants.ACCION,
				cargaMasivaDatosCVVo.getAccion() == null ? "" : cargaMasivaDatosCVVo.getAccion());
		return datosHashtable;
	}

	private void insertaCenHistorico(CargaMasivaDatosGFItem cargaMasivaObject, AdmUsuarios usuario) {
		LOGGER.debug("Insertando en CEN_HISTORICO para el colegio " + cargaMasivaObject.getIdInstitucion()
				+ ", idPersona " + cargaMasivaObject.getIdPersona());
		CenHistorico cenHistorico = new CenHistorico();
		cenHistorico.setIdinstitucion(cargaMasivaObject.getIdInstitucion());
		cenHistorico.setIdpersona(cargaMasivaObject.getIdPersona());
		cenHistorico.setFechaentrada(Calendar.getInstance().getTime());
		cenHistorico.setFechaefectiva(Calendar.getInstance().getTime());
		cenHistorico.setMotivo(null);

		cenHistorico.setIdtipocambio(Short.valueOf(SigaConstants.HISTORICOCAMBIOGF.DATOSCARGAMASIVA.getId()));
		// //Cambio Historico de direcciones
		cenHistorico.setFechamodificacion(Calendar.getInstance().getTime());
		cenHistorico.setUsumodificacion(usuario.getUsumodificacion());

		NewIdDTO newIdDTO = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(
				String.valueOf(cargaMasivaObject.getIdPersona()), String.valueOf(usuario.getIdinstitucion()));
		if (newIdDTO == null) {
			cenHistorico.setIdhistorico((short) 1);
		} else {
			int newIdCv = Integer.parseInt(newIdDTO.getNewId()) + 1;
			cenHistorico.setIdhistorico((short) newIdCv);
		}

		StringBuffer descripcion = new StringBuffer();
		if (cargaMasivaObject.getAccion().equalsIgnoreCase(SigaConstants.ALTA)) {
			// descripcion =
			GenRecursosExample genRecursosExample = new GenRecursosExample();
			genRecursosExample.createCriteria().andIdrecursoEqualTo("historico.literal.registroNuevo")
					.andIdlenguajeEqualTo(usuario.getIdlenguaje());
			List<GenRecursos> genRecursos = genRecursosMapper.selectByExample(genRecursosExample);

			descripcion = descripcion.append(genRecursos.get(0).getDescripcion());
			descripcion.append("\n");
		} else {

			GenRecursosExample genRecursosExample = new GenRecursosExample();
			genRecursosExample.createCriteria().andIdrecursoEqualTo("historico.literal.registroEliminado")
					.andIdlenguajeEqualTo(usuario.getIdlenguaje());
			List<GenRecursos> genRecursos = genRecursosMapper.selectByExample(genRecursosExample);
			descripcion = descripcion.append(genRecursos.get(0).getDescripcion());

			descripcion.append("\n");
		}

		descripcion.append(" - idGrupoFijo: ");
		descripcion.append(cargaMasivaObject.getIdGrupo());
		descripcion.append(" (");
		descripcion.append(cargaMasivaObject.getNombreGrupo());
		descripcion.append(")");
		cenHistorico.setDescripcion(descripcion.toString());

		if (cenHistoricoMapper.insert(cenHistorico) != 1) {
			throw new BusinessException("No se ha insertado correctamente el registro en cenHistorico para el colegio "
					+ usuario.getIdinstitucion() + ", idPersona " + cargaMasivaObject.getIdPersona()
					+ " e idTipoCambio " + SigaConstants.HISTORICOCAMBIOGF.DATOSCARGAMASIVA.getId());
		}

	}

	@Transactional
	private Long uploadFile(byte[] excelBytes, CenCargamasiva cargaMasivaObject, boolean isLog, AdmUsuarios usuario)
			throws BusinessException {
		Date dateLog = new Date(0);
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosGFImpl.uploadFile");
		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(cargaMasivaObject.getIdinstitucion());
		ficheroVo.setDirectorio(directorioFichero);
		String nombreFicheroString = cargaMasivaObject.getNombrefichero();
		ficheroVo.setNombre(nombreFicheroString);
		ficheroVo.setDescripcion("Carga Masiva " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(cargaMasivaObject.getIdinstitucion());
		ficheroVo.setFichero(excelBytes);
		ficheroVo.setExtension("xls");

		ficheroVo.setUsumodificacion(Integer.valueOf(usuario.getUsumodificacion()));
		ficheroVo.setFechamodificacion(new Date());
		ficherosService.insert(ficheroVo);

		if (isLog) {
			String descripcion = "log_" + ficheroVo.getDescripcion();
			ficheroVo.setDescripcion(descripcion);
			String nombreFichero = "log_" + ficheroVo.getNombre();
			ficheroVo.setNombre(nombreFichero);
		}

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		LOGGER.info(dateLog + ":fin.CargaMasivaDatosGFImpl.uploadFile");
		return ficheroVo.getIdfichero();

	}

	private Hashtable<String, Object> convertItemtoHash(CargaMasivaDatosGFItem cargaMasivaDatosGFItem) {
		Hashtable<String, Object> e = new Hashtable<String, Object>();

		if (cargaMasivaDatosGFItem.getColegiadoNumero() != null) {
			e.put(SigaConstants.COLEGIADONUMERO, cargaMasivaDatosGFItem.getColegiadoNumero());
		}
		if (cargaMasivaDatosGFItem.getPersonaNif() != null) {
			e.put(SigaConstants.PERSONANIF, cargaMasivaDatosGFItem.getPersonaNif());
		}
		if (cargaMasivaDatosGFItem.getPersonaNombre() != null) {
			e.put(SigaConstants.PERSONANOMBRE, cargaMasivaDatosGFItem.getPersonaNombre());
		}
		if (cargaMasivaDatosGFItem.getIdPersona() != null) {
			e.put(SigaConstants.C_IDPERSONA, cargaMasivaDatosGFItem.getIdPersona());
		}
		if (cargaMasivaDatosGFItem.getIdGrupo() != null) {
			e.put(SigaConstants.C_IDGRUPO, cargaMasivaDatosGFItem.getIdGrupo());
		}
		if (cargaMasivaDatosGFItem.getNombreGrupo() != null) {
			e.put(SigaConstants.NOMBREGRUPO, cargaMasivaDatosGFItem.getNombreGrupo());
		}
		if (cargaMasivaDatosGFItem.getAccion() != null) {
			e.put(SigaConstants.ACCION, cargaMasivaDatosGFItem.getAccion());
		}
		if (cargaMasivaDatosGFItem.getFechaInicio() != null) {
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			String fechaInicio = df2.format(cargaMasivaDatosGFItem.getFechaInicio());
			e.put(SigaConstants.C_FECHAINICIO, fechaInicio);
		}
		if (cargaMasivaDatosGFItem.getFechaFin() != null) {
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			String fechaFin = df2.format(cargaMasivaDatosGFItem.getFechaFin());
			e.put(SigaConstants.C_FECHAFIN, fechaFin);
		}
		if (cargaMasivaDatosGFItem.getErrores() != null) {
			e.put(SigaConstants.ERRORES, cargaMasivaDatosGFItem.getErrores());
		}

		if (cargaMasivaDatosGFItem.getGeneral()) {
			e.put(SigaConstants.GENERAL, "1");
		} else {
			e.put(SigaConstants.GENERAL, "0");
		}

		return e;
	}

	public String getDirectorioFichero(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosGFImpl.getDirectorioFichero");
		
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("cen.cargaExcel.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathGF = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(pathGF);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaGF");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosGFImpl.getDirectorioFichero");
		return directorioFichero.toString();
	}
	
	private String getDirectorioFicheroSigaClassique(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosGFImpl.getDirectorioFicheroSigaClassique");
		
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathGF = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(pathGF);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaGF");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosGFImpl.getDirectorioFicheroSigaClassique");
		return directorioFichero.toString();
	}

	@Override
	public ResponseEntity<InputStreamResource> downloadOriginalFile(CargaMasivaItem cargaMasivaItem,
			HttpServletRequest request) {

		LOGGER.info("downloadCurrentFile() -> Entrada al servicio para generar la plantilla original Excel Etiquetas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		//String path = "C:\\Users\\DTUser\\Documents\\gf" + idInstitucion + "\\gruposfijos\\";
		String path = getDirectorioFichero(idInstitucion);
		path += File.separator + idInstitucion + "_" + cargaMasivaItem.getIdFichero() + "." + SigaConstants.tipoExcelXls;
		// Se coge la imagen de la persona juridica
		File file = new File(path);

		// Se convierte el fichero en array de bytes para enviarlo al front
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			LOGGER.warn("downloadOriginalFile() -> No encuentra el fichero original en la ruta SigaNovo");

			//Si no encuentra el fichero buscamos en la ruta de siga classique
			String pathClassique = getDirectorioFicheroSigaClassique(idInstitucion);
			pathClassique += File.separator + idInstitucion + "_" + cargaMasivaItem.getIdFichero() + "." + SigaConstants.tipoExcelXls;

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
				LOGGER.warn("downloadOriginalFile() -> No encuentra el fichero original en la ruta SigaClassique");

			}
		}

		LOGGER.info(
				"downloadCurrentFile() -> Salida del servicio para descargar la plantilla original Excel Etiquetas");

		return res;

	}

	@Override
	public ResponseEntity<InputStreamResource> downloadLogFile(CargaMasivaItem cargaMasivaItem,
			HttpServletRequest request) {

		LOGGER.info("downloadLogFile() -> Entrada al servicio para generar la plantilla errores Excel Etiquetas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		//String path = "C:\\Users\\DTUser\\Documents\\gf" + idInstitucion + "\\gruposfijos\\";
		String path = getDirectorioFichero(idInstitucion);
		path += File.separator + "log_" + idInstitucion + "_" + cargaMasivaItem.getIdFicheroLog() + "." + SigaConstants.tipoExcelXls;
		// Se coge la imagen de la persona juridica
		File file = new File(path);

		// Se convierte el fichero en array de bytes para enviarlo al front
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
			pathClassique += File.separator + idInstitucion + "_" + cargaMasivaItem.getIdFicheroLog() + "."
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

		LOGGER.info("downloadLogFile() -> Salida del servicio para descargar la plantilla errores Excel Etiquetas");

		return res;
	}

}
