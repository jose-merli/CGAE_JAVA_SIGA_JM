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
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDatosCVItem;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.SubtiposCVItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ICargasMasivasCVService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.CenHistoricoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.CenTiposcvMapper;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo1Mapper;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo2Mapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
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
public class CargasMasivasCVServiceImpl implements ICargasMasivasCVService {
	private Logger LOGGER = Logger.getLogger(CargasMasivasCVServiceImpl.class);

	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	@Autowired
	private CenTiposcvMapper cenTiposcvMapper;
	@Autowired
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;
	@Autowired
	private CenTiposcvsubtipo1Mapper cenTiposcvsubtipo1Mapper;
	@Autowired
	private CenTiposcvsubtipo2Mapper cenTiposcvsubtipo2Mapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private CenDatoscvExtendsMapper cenDatoscvMapperExtends;
	@Autowired
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;
	@Autowired
	private CenHistoricoMapper cenHistoricoMapper;
	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	@Autowired 
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	@Autowired
	private ExcelHelper excelHelper;
	
	@Override
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector)
			throws BusinessException {
		if (orderList == null && datosVector == null)
			throw new BusinessException("No hay datos para crear el fichero");
		if (orderList == null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = this.excelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroEjemploCV);
		return XLSFile;
	}

	@Override
	public ResponseEntity<InputStreamResource> generateExcelCV() {

		LOGGER.info("generateExcelCV() -> Entrada al servicio para generar la plantilla Excel CV");

		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();

		// 1. Se defonen las columnas que conforman la plantilla

		// 1.1 Se rellena la primera fila
		datosHashtable.put(SigaConstants.COLEGIADONUMERO, "nnnnnn");
		datosHashtable.put(SigaConstants.PERSONANIF, "nnnnnnnna");
		datosHashtable.put(SigaConstants.C_FECHAINICIO, "dd/MM/yyyy");
		datosHashtable.put(SigaConstants.C_FECHAFIN, "dd/MM/yyyy");
		datosHashtable.put(SigaConstants.C_CREDITOS, "nnn");
		datosHashtable.put(SigaConstants.FECHAVERIFICACION, "dd/MM/yyyy");
		datosHashtable.put(SigaConstants.C_DESCRIPCION, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		datosHashtable.put(SigaConstants.TIPOCVCOD, "aaa");
		datosHashtable.put(SigaConstants.SUBTIPOCV1COD, "aaa");
		datosHashtable.put(SigaConstants.SUBTIPOCV2COD, "aaa");
		datosVector.add(datosHashtable);

		// 1.1 Se rellena la segunda fila
		datosHashtable = new Hashtable<String, Object>();
		datosHashtable.put(SigaConstants.COLEGIADONUMERO, "Opcional. Si nulo nif/cif requerido");
		datosHashtable.put(SigaConstants.PERSONANIF, "Opcional. Si nulo colegiadonumero requerido");
		datosHashtable.put(SigaConstants.C_FECHAINICIO, "Opcional");
		datosHashtable.put(SigaConstants.C_FECHAFIN, "Opcional");
		datosHashtable.put(SigaConstants.C_CREDITOS, "Opcional");
		datosHashtable.put(SigaConstants.FECHAVERIFICACION, "Opcional");
		datosHashtable.put(SigaConstants.C_DESCRIPCION, "Opcional");
		datosHashtable.put(SigaConstants.TIPOCVCOD, "Requerido");
		datosHashtable.put(SigaConstants.SUBTIPOCV1COD, "Opcional");
		datosHashtable.put(SigaConstants.SUBTIPOCV2COD, "Opcional");
		datosVector.add(datosHashtable);

		// 2. Crea el fichero excel
		File file = createExcelFile(SigaConstants.CAMPOSEJEMPLOCV, datosVector);

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
			e.printStackTrace();
		}

		LOGGER.info("generateExcelCV() -> Salida del servicio para generar la plantilla Excel CV");

		return res;

	}

	@Override
	@Transactional
	public UpdateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		LOGGER.info("uploadFile() -> Entrada al servicio para subir un archivo");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
		LOGGER.debug("uploadFile() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String nombreFichero = file.getOriginalFilename(); 

		// Extraer la información del excel
		LOGGER.debug("uploadFile() -> Extraer los datos del archivo");
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
					"uploadFile() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"uploadFile() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				List<CargaMasivaDatosCVItem> cargaMasivaDatosCVItems = parseExcelFile(datos, usuario);

				for (CargaMasivaDatosCVItem cargaMasivaDatosCVItem : cargaMasivaDatosCVItems) {
					if (cargaMasivaDatosCVItem.getErrores() == null) {
						
							CenDatoscv cenDatosCV = new CenDatoscv();
							cenDatosCV.setCertificado("1");
							cenDatosCV.setCreditos(cargaMasivaDatosCVItem.getCreditos());
							cenDatosCV.setDescripcion(cargaMasivaDatosCVItem.getDescripcion());
							cenDatosCV.setFechafin(cargaMasivaDatosCVItem.getFechaFin());
							cenDatosCV.setFechainicio(cargaMasivaDatosCVItem.getFechaInicio());
							cenDatosCV.setFechamodificacion(new Date());
							cenDatosCV.setFechamovimiento(cargaMasivaDatosCVItem.getFechaVerificacion());
							cenDatosCV.setIdinstitucion(usuario.getIdinstitucion());
							cenDatosCV.setIdinstitucioncargo(usuario.getIdinstitucion());
							cenDatosCV.setIdinstitucionSubt1(cargaMasivaDatosCVItem.getIdinstitucionSubt1());
							cenDatosCV.setIdinstitucionSubt2(cargaMasivaDatosCVItem.getIdinstitucionSubt2());
							cenDatosCV.setIdpersona(cargaMasivaDatosCVItem.getIdPersona());

							NewIdDTO idCv = cenDatoscvMapperExtends.getMaxIdCv(
									String.valueOf(usuario.getIdinstitucion()),
									String.valueOf(cargaMasivaDatosCVItem.getIdPersona()));

							if (idCv == null) {
								cenDatosCV.setIdcv(new Short("1"));
							} else {
								int newIdCv = Integer.parseInt(idCv.getNewId()) + 1;
								cenDatosCV.setIdcv((short) newIdCv);
							}

							cenDatosCV.setIdtipocv(cargaMasivaDatosCVItem.getIdTipoCV());
							cenDatosCV.setIdtipocvsubtipo1(cargaMasivaDatosCVItem.getIdTipoCVSubtipo1());
							cenDatosCV.setIdtipocvsubtipo2(cargaMasivaDatosCVItem.getIdTipoCVSubtipo2());
							cenDatosCV.setUsumodificacion(usuario.getIdusuario());

							int result = cenDatoscvMapperExtends.insertSelective(cenDatosCV);

							if (result == 1) {
								insertaCenHistorico(cargaMasivaDatosCVItem, usuario);
							}
							// }else {
							// errores += "Error al insertar una fila. <br/>";
							// error.setDescription(errores);
							// updateResponseDTO.setError(error);
							// }
						
					} else {
						errores += cargaMasivaDatosCVItem.getErrores();
						error.setDescription(errores);
						updateResponseDTO.setError(error);

						registrosErroneos++;
					}

					Hashtable<String, Object> e = new Hashtable<String, Object>();
					e = convertItemtoHash(cargaMasivaDatosCVItem);
					// Guardar log
					datosLog.add(e);
				}
				
				if(cargaMasivaDatosCVItems.isEmpty()) {
					error.setMessage("No existen registros en el fichero.");
					updateResponseDTO.setStatus(SigaConstants.OK); 
				}else {
					byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGCV, datosLog);

					cenCargamasivacv.setTipocarga("CV");
					cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
					cenCargamasivacv.setNombrefichero(nombreFichero);
					cenCargamasivacv.setNumregistros(cargaMasivaDatosCVItems.size());
					cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
					cenCargamasivacv.setFechamodificacion(new Date());
					cenCargamasivacv.setFechacarga(new Date());
					cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());

					Long idFile = uploadFile(file.getBytes(), cenCargamasivacv, false);
					Long idLogFile = uploadFile(bytesLog, cenCargamasivacv, true);

					cenCargamasivacv.setIdfichero(idFile);
					cenCargamasivacv.setIdficherolog(idLogFile);

					int result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
					
					if (result == 0) {
						error.setCode(SigaConstants.CODE_400);
						errores += "Error al insertar en cargas masivas";
					} 
					
					LOGGER.info("uploadFile() -> Salida al servicio para subir un archivo");
					updateResponseDTO.setStatus(SigaConstants.OK);
					error.setDescription(errores);
					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
					error.setCode(SigaConstants.CODE_200); 
				}

				
			}
		}

		updateResponseDTO.setError(error);

		return updateResponseDTO;
	}

	private Hashtable<String, Object> convertItemtoHash(CargaMasivaDatosCVItem cargaMasivaDatosCVItem) {
		Date dateLog = new Date();
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosCVImpl.convertItemtoHash");
		Hashtable<String, Object> e = new Hashtable<String, Object>();

		if (cargaMasivaDatosCVItem.getColegiadoNumero() != null) {
			e.put(SigaConstants.COLEGIADONUMERO, cargaMasivaDatosCVItem.getColegiadoNumero());
		}
		if (cargaMasivaDatosCVItem.getPersonaNIF() != null) {
			e.put(SigaConstants.PERSONANIF, cargaMasivaDatosCVItem.getPersonaNIF());
		}
		if (cargaMasivaDatosCVItem.getPersonaNombre() != null) {
			e.put(SigaConstants.PERSONANOMBRE, cargaMasivaDatosCVItem.getPersonaNombre());
		}
		if (cargaMasivaDatosCVItem.getIdPersona() != null) {
			e.put(SigaConstants.C_IDPERSONA, cargaMasivaDatosCVItem.getIdPersona());
		}
		if (cargaMasivaDatosCVItem.getFechaInicio() != null) {
			String fechaInicio = df2.format(cargaMasivaDatosCVItem.getFechaInicio());
			e.put(SigaConstants.C_FECHAINICIO, fechaInicio);
		}
		if (cargaMasivaDatosCVItem.getFechaFin() != null) {
			String fechaFin = df2.format(cargaMasivaDatosCVItem.getFechaFin());
			e.put(SigaConstants.C_FECHAFIN, fechaFin);
		}
		if (cargaMasivaDatosCVItem.getCreditos() != null) {
			e.put(SigaConstants.C_CREDITOS, cargaMasivaDatosCVItem.getCreditos());
		}
		if (cargaMasivaDatosCVItem.getFechaVerificacion() != null) {
			String fechaVerificacion = df2.format(cargaMasivaDatosCVItem.getFechaVerificacion());
			e.put(SigaConstants.FECHAVERIFICACION, fechaVerificacion);
		}
		if (cargaMasivaDatosCVItem.getDescripcion() != null) {
			e.put(SigaConstants.C_DESCRIPCION, cargaMasivaDatosCVItem.getDescripcion());
		}
		if (cargaMasivaDatosCVItem.getTipoCVCOD() != null) {
			e.put(SigaConstants.TIPOCVCOD, cargaMasivaDatosCVItem.getTipoCVCOD());
		}
		if (cargaMasivaDatosCVItem.getTipoCVNombre() != null) {
			e.put(SigaConstants.TIPOCVNOMBRE, cargaMasivaDatosCVItem.getTipoCVNombre());
		}
		if (cargaMasivaDatosCVItem.getIdTipoCV() != null) {
			e.put(SigaConstants.C_IDTIPOCV, cargaMasivaDatosCVItem.getIdTipoCV());
		}
		if (cargaMasivaDatosCVItem.getSubtipoCV1COD() != null) {
			e.put(SigaConstants.SUBTIPOCV1COD, cargaMasivaDatosCVItem.getSubtipoCV1COD());
		}
		if (cargaMasivaDatosCVItem.getSubTipoCV1Nombre() != null) {
			e.put(SigaConstants.SUBTIPOCV1NOMBRE, cargaMasivaDatosCVItem.getSubTipoCV1Nombre());
		}
		if (cargaMasivaDatosCVItem.getIdTipoCVSubtipo1() != null) {
			e.put(SigaConstants.C_IDTIPOCVSUBTIPO1, cargaMasivaDatosCVItem.getIdTipoCVSubtipo1());
		}
		if (cargaMasivaDatosCVItem.getSubtipoCV2COD() != null) {
			e.put(SigaConstants.SUBTIPOCV2COD, cargaMasivaDatosCVItem.getSubtipoCV2COD());
		}
		if (cargaMasivaDatosCVItem.getSubtipoCV2Nombre() != null) {
			e.put(SigaConstants.SUBTIPOCV2NOMBRE, cargaMasivaDatosCVItem.getSubtipoCV2Nombre());
		}
		if (cargaMasivaDatosCVItem.getIdTipoCVSubtipo2() != null) {
			e.put(SigaConstants.C_IDTIPOCVSUBTIPO2, cargaMasivaDatosCVItem.getIdTipoCVSubtipo2());
		}
		if (cargaMasivaDatosCVItem.getErrores() != null) {
			e.put(SigaConstants.ERRORES, cargaMasivaDatosCVItem.getErrores());
		}

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.convertItemtoHash");
		return e;
	}

	private List<CargaMasivaDatosCVItem> parseExcelFile(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario)
			throws BusinessException {

		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosCVImpl.parseExcelFile");

		List<CargaMasivaDatosCVItem> masivaDatosCVVos = new ArrayList<CargaMasivaDatosCVItem>();
		CargaMasivaDatosCVItem cargaMasivaDatosCVItem = null;

		Hashtable<String, SubtiposCVItem> tipoCvHashtable = new Hashtable<String, SubtiposCVItem>();
		Hashtable<String, SubtiposCVItem> subtipo1CvHashtable = new Hashtable<String, SubtiposCVItem>();
		Hashtable<String, SubtiposCVItem> subtipo2CvHashtable = new Hashtable<String, SubtiposCVItem>();
		Hashtable<Long, String> personaHashtable = new Hashtable<Long, String>();

		SubtiposCVItem tipoCVVo = null;
		SubtiposCVItem subtipoCV1Vo = null;
		SubtiposCVItem subtipoCV2Vo = null;


		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);
		GenRecursosCatalogos recursosCatalogos = null;

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
			cargaMasivaDatosCVItem = new CargaMasivaDatosCVItem();

			cargaMasivaDatosCVItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
			if (hashtable.get(SigaConstants.C_CREDITOS) != null
					&& !hashtable.get(SigaConstants.C_CREDITOS).toString().equals("")) {
				try {
					cargaMasivaDatosCVItem
							.setCreditos(Long.valueOf((String) hashtable.get(SigaConstants.C_CREDITOS)));
				} catch (NumberFormatException e) {
					LOGGER.debug("Los creditos debe ser numericos:" + hashtable.get(SigaConstants.C_CREDITOS));
					errorLinea.append("Creditos debe ser numérico. ");
				}
			}

			// Llamada a método para obtener idpersona
			if (hashtable.get(SigaConstants.COLEGIADONUMERO) != null
					&& !hashtable.get(SigaConstants.COLEGIADONUMERO).toString().equals(""))
				cargaMasivaDatosCVItem
						.setColegiadoNumero((String) hashtable.get(SigaConstants.COLEGIADONUMERO));
			if (hashtable.get(SigaConstants.PERSONANIF) != null
					&& !hashtable.get(SigaConstants.PERSONANIF).toString().equals(""))
				cargaMasivaDatosCVItem.setPersonaNIF((String) hashtable.get(SigaConstants.PERSONANIF));
			if (cargaMasivaDatosCVItem.getColegiadoNumero() != null || cargaMasivaDatosCVItem.getPersonaNIF() != null) {
				if (cargaMasivaDatosCVItem.getPersonaNIF() != null) {
						try {
		
							CenPersonaExample cenPersonaExample = new CenPersonaExample();
							cenPersonaExample.createCriteria().andNifcifEqualTo(cargaMasivaDatosCVItem.getPersonaNIF());
							List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(cenPersonaExample);
		
							cargaMasivaDatosCVItem.setIdPersona(cenPersona.get(0).getIdpersona());
						    if(cargaMasivaDatosCVItem.getColegiadoNumero() != null) {						
								// Comprobamos si el num de colegiado pertenece a esa persona
								CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
								cenColegiadoExample.createCriteria().andIdpersonaEqualTo(cargaMasivaDatosCVItem.getIdPersona()).andIdinstitucionEqualTo(idInstitucion).andNcolegiadoEqualTo(cargaMasivaDatosCVItem.getColegiadoNumero());
								
								List<CenColegiado> cenColegiado = cenColegiadoExtendsMapper.selectByExample(cenColegiadoExample);
								
								if(cenColegiado.isEmpty()) {							
									
									errorLinea.append("El número de colegiado no coincide con la persona introducida. ");
								}
							 }
		
						} catch (Exception e) {
							errorLinea.append("No se ha encontrado una persona con el Nif introducido");
							cargaMasivaDatosCVItem.setPersonaNombre("Error");
						}
				}if(null == cargaMasivaDatosCVItem.getIdPersona() && cargaMasivaDatosCVItem.getColegiadoNumero() != null){
					CenColegiadoExample exampleColegiado = new CenColegiadoExample();
					exampleColegiado.createCriteria().andNcolegiadoEqualTo(cargaMasivaDatosCVItem.getColegiadoNumero()).andIdinstitucionEqualTo(idInstitucion);
					List<CenColegiado> colegiado = cenColegiadoExtendsMapper.selectByExample(exampleColegiado );
					
					if (null != colegiado && colegiado.size()>0) {
						cargaMasivaDatosCVItem.setIdPersona(colegiado.get(0).getIdpersona());
						
					//Si no es un colegiado comprobamos que sea un colegiado inscrito
					}else {
						CenColegiadoExample exampleColegiadoInscrito = new CenColegiadoExample();
						exampleColegiadoInscrito.createCriteria().
						andNcomunitarioEqualTo(cargaMasivaDatosCVItem.getColegiadoNumero()).andIdinstitucionEqualTo(idInstitucion);
						List<CenColegiado> colegiadoInscrito = cenColegiadoExtendsMapper.selectByExample(exampleColegiadoInscrito);
						
						if (null != colegiadoInscrito && colegiadoInscrito.size()>0) {
							cargaMasivaDatosCVItem.setIdPersona(colegiadoInscrito.get(0).getIdpersona());
						}
						
					}
				}
				if (cargaMasivaDatosCVItem.getIdPersona() != null) {

					String nombreString = null;
					if (!personaHashtable.containsKey(cargaMasivaDatosCVItem.getIdPersona())) {

						CenPersona cenPersona = cenPersonaMapper
								.selectByPrimaryKey(cargaMasivaDatosCVItem.getIdPersona());
						StringBuffer nombre = new StringBuffer();
						nombre.append(cenPersona.getNombre());
						nombre.append(" ");
						nombre.append(cenPersona.getApellidos1());
						if (cenPersona.getApellidos2() != null) {
							nombre.append(" ");
							nombre.append(cenPersona.getApellidos2());
						}

						cargaMasivaDatosCVItem.setPersonaNombre(nombre.toString());
						nombreString = cargaMasivaDatosCVItem.getPersonaNombre();

					} else {
						nombreString = personaHashtable.get(cargaMasivaDatosCVItem.getIdPersona());
						cargaMasivaDatosCVItem.setPersonaNombre(nombreString);
					}

					personaHashtable.put(cargaMasivaDatosCVItem.getIdPersona(), nombreString);

				}	
				else {
					errorLinea.append("No existe la persona seleccionada");
					cargaMasivaDatosCVItem.setPersonaNombre("Error");
				}		

			} else {
				errorLinea.append("Es obligatorio introducir número de colegiado o nif/cif. ");
				cargaMasivaDatosCVItem.setPersonaNombre("Error");
			}

			if (hashtable.get(SigaConstants.C_DESCRIPCION) != null
					&& !hashtable.get(SigaConstants.C_DESCRIPCION).toString().equals(""))
				cargaMasivaDatosCVItem.setDescripcion((String) hashtable.get(SigaConstants.C_DESCRIPCION));

			if (hashtable.get(SigaConstants.C_FECHAFIN) != null
					&& !hashtable.get(SigaConstants.C_FECHAFIN).toString().equals(""))
				try {
					DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

					cargaMasivaDatosCVItem
							.setFechaFin(sdf.parse((String) hashtable.get(SigaConstants.C_FECHAFIN)));
				} catch (ParseException e1) {
					errorLinea.append("Fecha Fin mal introducida. ");

				}
			if (hashtable.get(SigaConstants.C_FECHAINICIO) != null
					&& !hashtable.get(SigaConstants.C_FECHAINICIO).toString().equals(""))
				try {
					DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

					cargaMasivaDatosCVItem
							.setFechaInicio(sdf.parse((String) hashtable.get(SigaConstants.C_FECHAINICIO)));
				} catch (ParseException e1) {
					errorLinea.append("Fecha Inicio mal introducida. ");
				}

			if (cargaMasivaDatosCVItem.getFechaInicio() != null && cargaMasivaDatosCVItem.getFechaFin() != null
					&& cargaMasivaDatosCVItem.getFechaInicio().compareTo(cargaMasivaDatosCVItem.getFechaFin()) > 0) {
				errorLinea.append("La fecha de inicio no puede ser posterior a la fecha fin. ");
			}
			if (hashtable.get(SigaConstants.FECHAVERIFICACION) != null
					&& !hashtable.get(SigaConstants.FECHAVERIFICACION).toString().equals(""))
				try {
					DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

					cargaMasivaDatosCVItem.setFechaVerificacion(
							sdf.parse((String) hashtable.get(SigaConstants.FECHAVERIFICACION)));
				} catch (ParseException e1) {
					errorLinea.append("Fecha Verificacion mal introducida. ");
				}

			if (hashtable.get(SigaConstants.TIPOCVCOD) != null
					&& !hashtable.get(SigaConstants.TIPOCVCOD).toString().equals("")) {
 
				try{
					String tipocvCod = String.valueOf(hashtable.get(SigaConstants.TIPOCVCOD));

					if (!tipoCvHashtable.containsKey(tipocvCod)) {
						tipoCVVo = new SubtiposCVItem();
	
						// Llamada a método para obtener idtipocv
						CenTiposcvExample cenTiposCVExample = new CenTiposcvExample();
						cenTiposCVExample.createCriteria().andCodigoextEqualTo(tipocvCod).andFechaBajaIsNull();
						List<CenTiposcv> tiposCV = cenTiposcvMapper.selectByExample(cenTiposCVExample);
	
						if (tiposCV != null && tiposCV.size() > 0) {
							tipoCVVo.setIdtipocv(tiposCV.get(0).getIdtipocv());
							
							genRecursosCatalogosKey.setIdrecurso(tiposCV.get(0).getDescripcion());
	
							GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosMapper
									.selectByPrimaryKey(genRecursosCatalogosKey);
	
							tipoCVVo.setTipocvDescripcion(genRecursosCatalogos.getDescripcion());
							tipoCVVo.setTipocvCodigoExt(tipocvCod);
						}
					} else {
						tipoCVVo = tipoCvHashtable.get(tipocvCod);
					}
					tipoCvHashtable.put(tipocvCod, tipoCVVo);
					if (tipoCVVo.getTipocvDescripcion() != null) {
						cargaMasivaDatosCVItem.setTipoCVNombre(tipoCVVo.getTipocvDescripcion());
						cargaMasivaDatosCVItem.setIdTipoCV(tipoCVVo.getIdTipocv());
						cargaMasivaDatosCVItem.setTipoCVCOD(tipocvCod);
					} else {
						cargaMasivaDatosCVItem.setTipoCVNombre("Error");
						errorLinea.append("No se ha encontrado el tipo CV. ");
					}
					cargaMasivaDatosCVItem.setTipoCVCOD(tipocvCod);
					
				} catch (Exception e1) {
					errorLinea.append("TIPOCVCOD mal introducido. ");
				}
				
			} else {
				errorLinea.append("Es obligatorio introducir tipocvcod. ");
				cargaMasivaDatosCVItem.setPersonaNombre("Error");
			}

			// SI Tiene subtipos1 es obligatorio meter alguno
			if (cargaMasivaDatosCVItem.getIdTipoCV() != null) {

				CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
				cenTiposcvsubtipo1Example.createCriteria().andIdinstitucionIn(idInstituciones)
						.andIdtipocvEqualTo(cargaMasivaDatosCVItem.getIdTipoCV()).andFechaBajaIsNull();
				List<CenTiposcvsubtipo1> tiposcvsubtipo1s = cenTiposcvsubtipo1Mapper
						.selectByExample(cenTiposcvsubtipo1Example);

				if (tiposcvsubtipo1s != null && tiposcvsubtipo1s.size() > 0
						&& hashtable.get(SigaConstants.SUBTIPOCV1COD) == null) {
					errorLinea.append(
							"Al existir subtipos 1 para este tipo de cv es obligatorio introducir el subtipo 1 ");
					cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");
				} else if (hashtable.get(SigaConstants.SUBTIPOCV1COD) != null
						&& !hashtable.get(SigaConstants.SUBTIPOCV1COD).toString().equals("")) {
					try{
					String subtipocv1Cod = (String) hashtable.get(SigaConstants.SUBTIPOCV1COD);
					if (!subtipo1CvHashtable.containsKey(subtipocv1Cod)) {
						subtipoCV1Vo = new SubtiposCVItem();
						// Llamada a método para obtener idtipocv

						CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example1 = new CenTiposcvsubtipo1Example();
						cenTiposcvsubtipo1Example1.createCriteria()
								.andCodigoextEqualTo(subtipocv1Cod)
								.andIdinstitucionIn(idInstituciones)
								.andFechaBajaIsNull()
								.andIdtipocvEqualTo(cargaMasivaDatosCVItem.getIdTipoCV());
						
						//ORDERNAR
						cenTiposcvsubtipo1Example1.setOrderByClause("IDINSTITUCION DESC");
						
						tiposcvsubtipo1s = cenTiposcvsubtipo1Mapper.selectByExample(cenTiposcvsubtipo1Example1);

						if (tiposcvsubtipo1s != null && tiposcvsubtipo1s.size() > 0) {
							CenTiposcvsubtipo1 subCV1Select = new CenTiposcvsubtipo1();
							//Si existen dos subtipos con el mismo codigo externo en la institucion logeada como en la general 
							//nos quedamos con el subtipo propio
							if(tiposcvsubtipo1s.size() > 1) {
								for (CenTiposcvsubtipo1 subCV1 : tiposcvsubtipo1s) {
									if(subCV1.getIdinstitucion().equals(idInstitucion)) {
										subCV1Select = subCV1;
									}
								}
							}else {
								subCV1Select = tiposcvsubtipo1s.get(0);
							}
							
							subtipoCV1Vo.setSubTipo1IdTipo(subCV1Select.getIdtipocvsubtipo1());
							subtipoCV1Vo.setSubTipo1IdInstitucion(subCV1Select.getIdinstitucion());
							subtipoCV1Vo.setSubTipo1CodigoExt(subtipocv1Cod);

							genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
							genRecursosCatalogosKey.setIdrecurso(subCV1Select.getDescripcion());

							GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosMapper
									.selectByPrimaryKey(genRecursosCatalogosKey);

							subtipoCV1Vo.setSubTipo1Descripcion(genRecursosCatalogos.getDescripcion());
						}

					} else {
						subtipoCV1Vo = subtipo1CvHashtable.get(subtipocv1Cod);
					}
					subtipo1CvHashtable.put(subtipocv1Cod, subtipoCV1Vo);
					if (subtipoCV1Vo.getSubTipo1Descripcion() != null) {
						cargaMasivaDatosCVItem.setSubTipoCV1Nombre(subtipoCV1Vo.getSubTipo1Descripcion());
						cargaMasivaDatosCVItem.setIdTipoCVSubtipo1(subtipoCV1Vo.getSubTipo1IdTipo());
						cargaMasivaDatosCVItem.setSubtipoCV1COD(subtipocv1Cod);
						cargaMasivaDatosCVItem.setIdinstitucionSubt1(subtipoCV1Vo.getSubTipo1IdInstitucion());

					} else {
						cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");
						errorLinea.append("No se ha encontrado el subtipo 1 CV. ");
					}
					cargaMasivaDatosCVItem.setSubtipoCV1COD(subtipocv1Cod);
					} catch (Exception e1) {
						errorLinea.append("SUBTIPOCV1COD mal introducido. ");
					}
					if (cargaMasivaDatosCVItem.getIdTipoCVSubtipo1() != null
							&& hashtable.get(SigaConstants.SUBTIPOCV2COD) != null
							&& !hashtable.get(SigaConstants.SUBTIPOCV2COD).toString().equals("")) {
						try{
						String subtipocv2Cod = (String) hashtable.get(SigaConstants.SUBTIPOCV2COD);
						if (!subtipo2CvHashtable.containsKey(subtipocv2Cod)) {
							subtipoCV2Vo = new SubtiposCVItem();
							// Llamada a método para obtener idtipocv

							CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
							cenTiposcvsubtipo2Example.createCriteria()
									.andCodigoextEqualTo(subtipocv2Cod)
									.andIdinstitucionIn(idInstituciones)
									.andFechaBajaIsNull()
									.andIdtipocvEqualTo(cargaMasivaDatosCVItem.getIdTipoCV());
							
							//ORDERNAR
							cenTiposcvsubtipo2Example.setOrderByClause("IDINSTITUCION DESC");

							List<CenTiposcvsubtipo2> tiposcvsubtipo2s = cenTiposcvsubtipo2Mapper
									.selectByExample(cenTiposcvsubtipo2Example);

							if (tiposcvsubtipo2s != null && tiposcvsubtipo2s.size() > 0) {
								
								CenTiposcvsubtipo2 subCV2Select = new CenTiposcvsubtipo2();
								//Si existen dos subtipos con el mismo codigo externo en la institucion logeada como en la general 
								//nos quedamos con el subtipo propio
								if(tiposcvsubtipo1s.size() > 1) {
									for (CenTiposcvsubtipo2 subCV2 : tiposcvsubtipo2s) {
										if(subCV2.getIdinstitucion().equals(idInstitucion)) {
											subCV2Select = subCV2;
										}
									}
								}else {
									subCV2Select = tiposcvsubtipo2s.get(0);
								}
								
								subtipoCV2Vo.setSubTipo2IdTipo(subCV2Select.getIdtipocvsubtipo2());
								subtipoCV2Vo.setSubTipo2IdInstitucion(subCV2Select.getIdinstitucion());
								subtipoCV2Vo.setSubTipo2CodigoExt(subtipocv2Cod);

								genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
								genRecursosCatalogosKey.setIdrecurso(subCV2Select.getDescripcion());

								GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosMapper
										.selectByPrimaryKey(genRecursosCatalogosKey);

								subtipoCV2Vo.setSubTipo2Descripcion(genRecursosCatalogos.getDescripcion());
							}
						} else {
							subtipoCV2Vo = subtipo2CvHashtable.get(subtipocv2Cod);
						}
						subtipo2CvHashtable.put(subtipocv2Cod, subtipoCV2Vo);
						
						if (subtipoCV2Vo.getSubTipo2Descripcion() != null) {
							cargaMasivaDatosCVItem.setSubtipoCV2Nombre(subtipoCV2Vo.getSubTipo2Descripcion());
							cargaMasivaDatosCVItem.setIdTipoCVSubtipo2(subtipoCV2Vo.getSubTipo2IdTipo());
							cargaMasivaDatosCVItem.setIdinstitucionSubt2(subtipoCV2Vo.getSubTipo2IdInstitucion());
							cargaMasivaDatosCVItem.setSubtipoCV2COD(subtipocv2Cod);

						} else {
							cargaMasivaDatosCVItem.setSubtipoCV2Nombre("Error");
							errorLinea.append("No se ha encontrado el subtipo 2 CV. ");
						}
						cargaMasivaDatosCVItem.setSubtipoCV2COD(subtipocv2Cod);
					} catch (Exception e1) {
						errorLinea.append("SUBTIPOCV2COD mal introducido. ");
					}
					}
				} else {
					errorLinea.append(
							"Al existir subtipos 1 para este tipo de cv es obligatorio introducir el subtipo 1. ");
					cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");

				}
			} else {
				cargaMasivaDatosCVItem.setSubtipoCV2Nombre("Error");
				cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");
			}

			if (!errorLinea.toString().isEmpty()) {
				cargaMasivaDatosCVItem
						.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaDatosCVVos.add(cargaMasivaDatosCVItem);
			numLinea = numLinea + 1;
		}

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.parseExcelFile");
		return masivaDatosCVVos;
	}

	private void insertaCenHistorico(CargaMasivaDatosCVItem cargaMasivaDatosCVItem, AdmUsuarios usuario) {
		LOGGER.debug("Insertando en CEN_HISTORICO para el colegio " + usuario.getIdinstitucion() + ", idPersona "
				+ cargaMasivaDatosCVItem.getIdPersona());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		CenHistorico cenHistorico = new CenHistorico();
		cenHistorico.setIdinstitucion(usuario.getIdinstitucion());
		cenHistorico.setIdpersona(cargaMasivaDatosCVItem.getIdPersona());
		cenHistorico.setFechaentrada(Calendar.getInstance().getTime());
		cenHistorico.setFechaefectiva(Calendar.getInstance().getTime());
		cenHistorico.setMotivo(null);
		cenHistorico.setIdtipocambio(SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		cenHistorico.setFechamodificacion(Calendar.getInstance().getTime());
		cenHistorico.setUsumodificacion(usuario.getIdusuario());

		NewIdDTO newIdDTO = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(
				String.valueOf(cargaMasivaDatosCVItem.getIdPersona()), String.valueOf(usuario.getIdinstitucion()));
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
		descripcion.append("- Certificado: ");
		descripcion.append(cargaMasivaDatosCVItem.getFechaVerificacion() != null ? "Sí" : "No");
		descripcion.append("\n");
		descripcion.append("- FechaCertificacion: ");
		descripcion.append(cargaMasivaDatosCVItem.getFechaVerificacion() != null
				? simpleDateFormat.format(cargaMasivaDatosCVItem.getFechaVerificacion())
				: "");

		descripcion.append("\n");
		descripcion.append("- Creditos: ");
		descripcion.append(cargaMasivaDatosCVItem.getCreditos() != null ? cargaMasivaDatosCVItem.getCreditos() : "");
		descripcion.append("\n");
		descripcion.append("- Descripcion: ");
		descripcion
				.append(cargaMasivaDatosCVItem.getDescripcion() != null ? cargaMasivaDatosCVItem.getDescripcion() : "");
		descripcion.append("\n");
		descripcion.append("- Fechainicio: ");
		descripcion.append(cargaMasivaDatosCVItem.getFechaInicio() != null
				? simpleDateFormat.format(cargaMasivaDatosCVItem.getFechaInicio())
				: "");
		descripcion.append("\n");
		descripcion.append("- Fechafin: ");
		descripcion.append(cargaMasivaDatosCVItem.getFechaFin() != null
				? simpleDateFormat.format(cargaMasivaDatosCVItem.getFechaFin())
				: "");

		descripcion.append("\n");
		descripcion.append("- Tipocv:  ");
		descripcion.append(
				cargaMasivaDatosCVItem.getTipoCVNombre() != null ? cargaMasivaDatosCVItem.getTipoCVNombre() : "");
		descripcion.append("\n");
		descripcion.append("- Subtipocv1: ");
		descripcion.append(
				cargaMasivaDatosCVItem.getSubTipoCV1Nombre() != null ? cargaMasivaDatosCVItem.getSubTipoCV1Nombre()
						: "");
		descripcion.append("\n");
		descripcion.append("- Idinstitucion_Subtipocv1: ");
		descripcion.append(
				cargaMasivaDatosCVItem.getIdinstitucionSubt1() != null ? cargaMasivaDatosCVItem.getIdinstitucionSubt1()
						: "");
		descripcion.append("\n");
		descripcion.append("- Subtipocv2: ");
		descripcion.append(
				cargaMasivaDatosCVItem.getSubtipoCV2Nombre() != null ? cargaMasivaDatosCVItem.getSubtipoCV2Nombre()
						: "");
		descripcion.append("\n");
		descripcion.append("- Idinstitucion_Subtipocv2: ");
		descripcion.append(
				cargaMasivaDatosCVItem.getIdinstitucionSubt2() != null ? cargaMasivaDatosCVItem.getIdinstitucionSubt2()
						: "");

		cenHistorico.setDescripcion(descripcion.toString());

		if (cenHistoricoMapper.insert(cenHistorico) != 1) {
			throw new BusinessException("No se ha insertado correctamente el registro en cenHistorico para el colegio "
					+ usuario.getIdinstitucion() + ", idPersona " + cargaMasivaDatosCVItem.getIdPersona()
					+ " e idTipoCambio " + SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		}

	}

	private Long uploadFile(byte[] excelBytes, CenCargamasiva cenCargamasivacv, boolean isLog)
			throws BusinessException {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosCVImpl.uploadFile");
		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(cenCargamasivacv.getIdinstitucion());
		ficheroVo.setDirectorio(directorioFichero);
		String nombreFicheroString = cenCargamasivacv.getNombrefichero();
		ficheroVo.setNombre(nombreFicheroString); 
		ficheroVo.setDescripcion("Carga Masiva " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(cenCargamasivacv.getIdinstitucion());
		ficheroVo.setFichero(excelBytes);
		ficheroVo.setExtension("xls");

		ficheroVo.setUsumodificacion(Integer.valueOf(cenCargamasivacv.getUsumodificacion()));
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		if (isLog) {
			ficheroVo.setDescripcion("log_" + ficheroVo.getDescripcion());
			ficheroVo.setNombre("log_" + ficheroVo.getNombre());
		}

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.uploadFile");
		return ficheroVo.getIdfichero();
	}

	private String getDirectorioFichero(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaMasivaDatosCVImpl.getDirectorioFichero");

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

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosCVImpl.getDirectorioFichero");
		return directorioFichero.toString();
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

	@Override
	public CargaMasivaDTO searchCV(CargaMasivaItem cargaMasivaItem, HttpServletRequest request) {

		LOGGER.info("searchCV() -> Entrada al servicio para obtener datos curriculares");

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
						"searchCV() / cenCargaMasivaExtendsMapper.searchCV() -> No existen etiquetas con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchCV() -> idInstitucion del token nula");
		}

		return cargaMasivaDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> downloadOriginalFile(CargaMasivaItem cargaMasivaItem,
			HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("downloadOriginalFile() -> Entrada al servicio para generar la plantilla original");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Extraer el path
		//String path = "C:\\Users\\DTUser\\Documents\\CV" + idInstitucion + "\\cargas\\";
		String path = getDirectorioFichero(idInstitucion);
		path += File.separator + idInstitucion + "_" + cargaMasivaItem.getIdFichero() + "." + SigaConstants.tipoExcelXls;
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

		LOGGER.info("downloadOriginalFile() -> Salida del servicio para generar la plantilla original");

		return res;
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
}
