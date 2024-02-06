package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ComboConsultasDTO;
import org.itcgae.siga.DTOs.com.ComboSufijoDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.DTOs.com.DocumentosPlantillaDTO;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoBorrarDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDataListDTO;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.com.SufijoItem;
import org.itcgae.siga.DTOs.com.TarjetaPlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IPlantillasDocumentoService;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumento;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumentoKey;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModPlantilladocumentoKey;
import org.itcgae.siga.db.entities.ModRelPlantillaSufijo;
import org.itcgae.siga.db.entities.ModRelPlantillaSufijoExample;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocConsultaMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModRelPlantillaSufijoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocFormatoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocSufijoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.itcgae.siga.DTOs.com.SufijoItem.SufijosAgrupados;

@Service
@Transactional(timeout=2400)
public class PlantillasDocumentoServiceImpl implements IPlantillasDocumentoService {
	
	private static final Collection<String> validFormats = Collections.unmodifiableList(Arrays.asList("doc","docx","xls","xlsx","fo"));
 
	private Logger LOGGER = Logger.getLogger(PlantillasDocumentoServiceImpl.class);

	@Autowired
	IPlantillasEnvioService plantillasEnvioService;

	@Autowired
	AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	ModPlantillaDocumentoConsultaExtendsMapper modPlantillaDocumentoConsultaExtendsMapper;

	@Autowired
	ConConsultasExtendsMapper conConsultasExtendsMapper;

	@Autowired
	ModModeloPlantilladocumentoMapper modModeloPlantilladocumentoMapper;

	@Autowired
	ModPlantilladocConsultaMapper modPlantilladocConsultaMapper;

	@Autowired
	ModPlantilladocumentoMapper modPlantilladocumentoMapper;

	@Autowired
	ModPlantillaDocumentoExtendsMapper modPlantillaDocumentoExtendsMapper;

	@Autowired
	ModPlantillaDocSufijoExtendsMapper modPlantillaDocSufijoExtendsMapper;

	@Autowired
	ModPlantillaDocFormatoExtendsMapper modPlantillaDocFormatoExtendsMapper;

	@Autowired
	ModRelPlantillaSufijoMapper modRelPlantillaSufijoMapper;

	@Autowired
	ModModeloPlantillaDocumentoExtendsMapper modModeloPlantillaDocumentoExtendsMapper;

	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;

	@Autowired
	private ModClasecomunicacionesMapper _modClasecomunicacionesMapper;

	@Autowired
	private ConConsultaMapper _conConsultaMapper;

	@Override
	public ConsultasDTO obtenerConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc,
			boolean historico) {
		LOGGER.info(
				"obtenerConsultasPlantilla() -> Entrada al servicio para obtener las consultas de una plantilla de documento");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		ConsultasDTO respuesta = new ConsultasDTO();
		List<ConsultaItem> listaConsultaItem = new ArrayList<ConsultaItem>();

		if (null != idInstitucionUser) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {

					if (plantillaDoc.getIdInstitucion() != null
							&& plantillaDoc.getIdInstitucion().equals(SigaConstants.IDINSTITUCION_0)) {
						plantillaDoc.setIdInstitucion(String.valueOf(SigaConstants.IDINSTITUCION_2000));
					}

					listaConsultaItem = modPlantillaDocumentoConsultaExtendsMapper.selectConsultasByInforme(
							Short.parseShort(plantillaDoc.getIdInstitucion()),
							Long.parseLong(plantillaDoc.getIdModeloComunicacion()),
							Long.parseLong(plantillaDoc.getIdInforme()), usuario.getIdlenguaje(), historico);
					if (listaConsultaItem != null && listaConsultaItem.size() > 0) {
						for (ConsultaItem consulta : listaConsultaItem) {

							// Obtenemos la sentencia
							ConConsultaKey key = new ConConsultaKey();
							key.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
							key.setIdinstitucion(Short.valueOf(consulta.getIdInstitucion()));
							ConConsulta consultaEntity = _conConsultaMapper.selectByPrimaryKey(key);

							if (consultaEntity != null) {
								consulta.setSentencia(consultaEntity.getSentencia());
							}
						}
						respuesta.setConsultaItem(listaConsultaItem);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		LOGGER.info(
				"obtenerConsultasPlantilla() -> Salida del servicio para obtener las consultas de una plantilla de documento");
		return respuesta;
	}

	@Override
	public ComboConsultasDTO obtenerConsultasDisponibles(HttpServletRequest request,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info(
				"obtenerConsultasDisponibles() -> Entrada al servicio para obtener las disponibles para la clase y la institucion");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboConsultasDTO comboConsultasDTO = new ComboConsultasDTO();
		List<ComboItemConsulta> comboItems = new ArrayList<ComboItemConsulta>();

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				try {
					Long idClaseComunicacion = null;
					if (plantillaDoc.getIdClaseComunicacion() != null) {
						idClaseComunicacion = Long.parseLong(plantillaDoc.getIdClaseComunicacion());
					}

					if (plantillaDoc.getIdInstitucion() != null
							&& plantillaDoc.getIdInstitucion().equals(SigaConstants.IDINSTITUCION_0)) {
						plantillaDoc.setIdInstitucion(String.valueOf(SigaConstants.IDINSTITUCION_2000));
					}

					LOGGER.debug("Obtenemos las consultas CONDICIONAL");
					comboItems = conConsultasExtendsMapper.selectConsultasDisponibles(
							Short.parseShort(plantillaDoc.getIdInstitucion()), idClaseComunicacion,
							SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());

					comboConsultasDTO.setConsultasCondicional(comboItems);

					LOGGER.debug("Obtenemos las consultas DATOS");

					comboItems = conConsultasExtendsMapper.selectConsultasDisponibles(
							Short.parseShort(plantillaDoc.getIdInstitucion()), idClaseComunicacion,
							SigaConstants.OBJETIVO.DATOS.getCodigo());

					comboConsultasDTO.setConsultasDatos(comboItems);

					LOGGER.debug("Obtenemos las consultas MULTIDOCUMENTO");

					comboItems = conConsultasExtendsMapper.selectConsultasDisponibles(
							Short.parseShort(plantillaDoc.getIdInstitucion()), idClaseComunicacion,
							SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());

					comboConsultasDTO.setConsultasMultidoc(comboItems);

					LOGGER.debug("Obtenemos las consultas DESTINATARIOS");

					comboItems = conConsultasExtendsMapper.selectConsultasDisponibles(
							Short.parseShort(plantillaDoc.getIdInstitucion()), idClaseComunicacion,
							SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());

					comboConsultasDTO.setConsultasDestinatarios(comboItems);

				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		LOGGER.info(
				"obtenerConsultasDisponibles() -> Salida del servicio para obtener las disponibles para la clase y la institucion");
		return comboConsultasDTO;
	}

	@Override
	public Error guardarConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info("guardarConsultasPlantilla() -> Entrada al servicio para guardar las consultas de la plantilla");

		if (plantillaDoc != null && plantillaDoc.getIdInstitucion() != null
				&& plantillaDoc.getIdInstitucion().equals(SigaConstants.IDINSTITUCION_0)) {
			plantillaDoc.setIdInstitucion(String.valueOf(SigaConstants.IDINSTITUCION_2000));
		}

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Error respuesta = new Error();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				boolean consultasValidas = false;
				List<Long> listaConsultasIdAAsociar = new ArrayList<Long>();
				try {

					// Obtenemos las consultas asignadas al informe
					// List<ConsultaItem> listaConsultasAsociadas =
					// modPlantillaDocumentoConsultaExtendsMapper.selectConsultasByInforme(Short.parseShort(plantillaDoc.getIdInstitucion()),
					// Long.parseLong(plantillaDoc.getIdModeloComunicacion()),
					// Long.parseLong(plantillaDoc.getIdInforme()), usuario.getIdlenguaje(), false);

					// Comprobamos las consultas a guardar
					List<ConsultaItem> listaItems = plantillaDoc.getConsultas();
					if (listaItems != null) {

						int consultaDatos = 0;
						int consultaDestinatario = 0;
						int consultaMultidocumento = 0;
						int consultaCondicion = 0;

						for (ConsultaItem consultaItem : listaItems) {
							if (Short.parseShort(consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.DESTINATARIOS
									.getCodigo()) {
								consultaDestinatario++;
							} else if (Short.parseShort(consultaItem
									.getIdObjetivo()) == SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo()) {
								consultaMultidocumento++;
							} else if (Short.parseShort(
									consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.CONDICIONAL.getCodigo()) {
								consultaCondicion++;
							} else if (Short.parseShort(consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.DATOS
									.getCodigo()) {
								consultaDatos++;
							}
						}

						consultasValidas = true;

						if (consultaDestinatario != 1 && !SigaConstants.ID_CLASE_CONSULTA_GENERICA
								.equals(plantillaDoc.getIdClaseComunicacion())) {
							consultasValidas = false;
						}
						if (consultaMultidocumento > 1) {
							consultasValidas = false;
						}
						if (consultaCondicion > 1) {
							consultasValidas = false;
						}
					}

					if (listaItems != null && consultasValidas && plantillaDoc.getIdInforme() != null) {
						// Por cada plantilla asociada hay que guardar sus consultas
						ModModeloPlantilladocumentoExample modModeloPlantillaExample = new ModModeloPlantilladocumentoExample();
						modModeloPlantillaExample.createCriteria()
								.andIdinformeEqualTo(Long.parseLong(plantillaDoc.getIdInforme()))
								.andIdmodelocomunicacionEqualTo(Long.parseLong(plantillaDoc.getIdModeloComunicacion()))
								.andFechabajaIsNull();
						List<ModModeloPlantilladocumento> listaPlantillaDoc = modModeloPlantilladocumentoMapper
								.selectByExample(modModeloPlantillaExample);

						for (ModModeloPlantilladocumento modPlantilla : listaPlantillaDoc) {
							for (ConsultaItem consultaItem : listaItems) {
								// Comprobamos si la consulta ya está asignada a la plantilla
								ModPlantilladocConsultaExample consultaPlantillaExample = new ModPlantilladocConsultaExample();
								ModPlantilladocConsulta consultaPlantillaModificar = null;

								if (consultaItem.getIdConsulta() != null && !"".equals(consultaItem.getIdConsulta())) {
									consultaPlantillaExample.createCriteria()
											.andIdconsultaEqualTo(Long.parseLong(consultaItem.getIdConsulta()))
											.andIdmodelocomunicacionEqualTo(
													Long.parseLong(plantillaDoc.getIdModeloComunicacion()))
											.andIdinstitucionEqualTo(Short.parseShort(plantillaDoc.getIdInstitucion()))
											.andIdplantilladocumentoEqualTo(modPlantilla.getIdplantilladocumento())
											.andFechabajaIsNull();

									List<ModPlantilladocConsulta> listaPlantillaModificar = modPlantilladocConsultaMapper
											.selectByExample(consultaPlantillaExample);

									if (listaPlantillaModificar != null && listaPlantillaModificar.size() > 0) {
										if (listaPlantillaModificar != null && listaPlantillaModificar.size() == 1) {
											consultaPlantillaModificar = listaPlantillaModificar.get(0);
										}

										if (consultaPlantillaModificar != null) {
											consultaPlantillaModificar.setFechamodificacion(new Date());
											consultaPlantillaModificar.setIdinstitucionConsulta(
													Short.parseShort(consultaItem.getIdInstitucion()));
											consultaPlantillaModificar.setRegion(consultaItem.getRegion());
											modPlantilladocConsultaMapper
													.updateByPrimaryKey(consultaPlantillaModificar);
											listaConsultasIdAAsociar.add(consultaPlantillaModificar.getIdconsulta());
										}
									} else {
										consultaPlantillaModificar = new ModPlantilladocConsulta();
										consultaPlantillaModificar
												.setIdinstitucion(Short.parseShort(plantillaDoc.getIdInstitucion()));
										consultaPlantillaModificar.setIdmodelocomunicacion(
												Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
										consultaPlantillaModificar
												.setIdplantilladocumento(modPlantilla.getIdplantilladocumento());
										consultaPlantillaModificar
												.setIdconsulta(Long.parseLong(consultaItem.getIdConsulta()));
										consultaPlantillaModificar.setFechabaja(null);
										consultaPlantillaModificar.setUsumodificacion(usuario.getIdusuario());
										consultaPlantillaModificar.setFechamodificacion(new Date());
										consultaPlantillaModificar.setIdinstitucionConsulta(
												Short.parseShort(consultaItem.getIdInstitucion()));
										consultaPlantillaModificar.setRegion(consultaItem.getRegion());

										modPlantilladocConsultaMapper.insert(consultaPlantillaModificar);
										listaConsultasIdAAsociar.add(consultaPlantillaModificar.getIdconsulta());
									}
								}
							}
						}

						for (ConsultaItem consultaItem : listaItems) {
							// Si la consulta se ha editado, actualizamos la fecha de baja de la consulta
							// anterior
							if (consultaItem.getIdConsultaAnterior() != null
									&& !consultaItem.getIdConsultaAnterior().equalsIgnoreCase("") && !consultaItem
											.getIdConsultaAnterior().equalsIgnoreCase(consultaItem.getIdConsulta())) {
								// Obtenemos la consulta a borrar
								List<ConsultaItem> listaConsultasBorrar = modPlantillaDocumentoConsultaExtendsMapper
										.selectConsultaByIdConsulta(Short.parseShort(plantillaDoc.getIdInstitucion()),
												Long.parseLong(plantillaDoc.getIdModeloComunicacion()),
												Long.parseLong(plantillaDoc.getIdInforme()),
												Long.parseLong(consultaItem.getIdConsultaAnterior()), null);
								for (ConsultaItem consultaBorrar : listaConsultasBorrar) {
									ModPlantilladocConsulta consultaEntity = new ModPlantilladocConsulta();
									consultaEntity.setFechabaja(new Date());
									consultaEntity.setFechamodificacion(new Date());
									consultaEntity.setUsumodificacion(usuario.getIdusuario());
									consultaEntity.setIdplantillaconsulta(
											Long.parseLong(consultaBorrar.getIdPlantillaConsulta()));
									modPlantilladocConsultaMapper.updateByPrimaryKeySelective(consultaEntity);
								}
							}
						}

						/*
						 * Borrar las consultas eliminadas if(listaConsultasAsociadas != null &&
						 * listaConsultasAsociadas.size() > 0){ for(ConsultaItem consultaAsociada :
						 * listaConsultasAsociadas){
						 * if(listaConsultasIdAAsociar.indexOf(Long.parseLong(consultaAsociada.
						 * getIdConsulta())) == -1){ // Añadimos la fecha de baja a la consulta para
						 * cada una de las plantillas asociadas String idPlantillas =
						 * consultaAsociada.getIdPlantillaConsulta(); if(idPlantillas != null){ String[]
						 * arrayPlantillas = idPlantillas.split(","); for(String id : arrayPlantillas){
						 * ModPlantilladocConsulta consulta =
						 * modPlantilladocConsultaMapper.selectByPrimaryKey(Long.parseLong(id));
						 * if(consulta != null){ consulta.setFechabaja(new Date());
						 * modPlantilladocConsultaMapper.updateByPrimaryKey(consulta); } } } } } }
						 */

						respuesta.setCode(200);
						respuesta.setDescription("Consultas guardadas");

					} else {
						respuesta.setCode(500);
						respuesta.setDescription("Consultas no válidas");
						respuesta.setMessage("Error al guardar las consultas");
					}
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al guardar la consulta");
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("guardarConsultasPlantilla() -> Entrada al servicio para guardar las consultas de la plantilla");
		return respuesta;
	}

	@Override
	public ResponseDocumentoDTO uploadFile(MultipartHttpServletRequest request, String idClaseComunicacion) {
		LOGGER.info("uploadFile() -> Entrada al servicio para subir una plantilla de documento");

		ResponseDocumentoDTO response = new ResponseDocumentoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ModClasecomunicaciones modClase = null;
		String rutaPlantillaClase = "";

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {

				// OBtenemos la ruta de las plantillas
				if (idClaseComunicacion != null) {
					modClase = _modClasecomunicacionesMapper.selectByPrimaryKey(Short.parseShort(idClaseComunicacion));
					if (modClase != null) {
						rutaPlantillaClase = modClase.getRutaplantilla();
					}
				}

				if (rutaPlantillaClase == null || "".equals(rutaPlantillaClase)) {
					rutaPlantillaClase = SigaConstants.rutaPlantillaSinClase;
				} else {
					rutaPlantillaClase = rutaPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO
							+ SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO,
							String.valueOf(idInstitucion));
				}

				// crear path para almacenar el fichero
				GenPropertiesKey key = new GenPropertiesKey();
				key.setFichero(SigaConstants.FICHERO_SIGA);
				key.setParametro(SigaConstants.parametroRutaPlantillas);

				GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);

				String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator
						+ rutaPlantillaClase + SigaConstants.pathSeparator;

				String pathFichero = rutaPlantilla;

				// 1. Coger archivo del request
				LOGGER.debug("uploadFile() -> Coger documento de cuenta bancaria del request");
				Iterator<String> itr = request.getFileNames();
				MultipartFile file = request.getFile(itr.next());
				String fileName = file.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				String nombreFichero = fileName.substring(0, fileName.lastIndexOf("."));

				if (extension.contains("doc") || extension.contains("docx") || extension.contains("xls")
						|| extension.contains("xlsx") || extension.contains("fo")) {
					try {

						// Obtenemos el idplantilladocumento para concatenarlo con el nombre del fichero
						NewIdDTO newId = modPlantillaDocumentoExtendsMapper.selectMaxIdPlantillaDocumento();
						int idNuevaPlantilla = Integer.parseInt(newId.getNewId()) + 1;
						String newNombreFichero = nombreFichero + "_" + idNuevaPlantilla + extension;

						File aux = new File(pathFichero);
						// creo directorio si no existe
						aux.mkdirs();
						File serverFile = new File(pathFichero, newNombreFichero);
						if (serverFile.exists()) {
							LOGGER.error("Ya existe el fichero: " + pathFichero + fileName);
							throw new FileAlreadyExistsException("El fichero ya existe");
						}
						FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
						response.setNombreDocumento(newNombreFichero);
						response.setRutaDocumento(pathFichero + newNombreFichero);
					} catch (FileNotFoundException e) {
						Error error = new Error();
						error.setCode(500);
						error.setDescription(e.getMessage());
						response.setError(error);
						e.printStackTrace();
						LOGGER.error(
								"uploadFile() -> Error al buscar la plantilla de documento en el directorio indicado",
								e);
					} catch (FileAlreadyExistsException ex) {
						Error error = new Error();
						error.setCode(400);
						error.setDescription(ex.getMessage());
						response.setError(error);
						ex.printStackTrace();
						LOGGER.error("uploadFile() -> El fichero ya existe en el filesystem", ex);
					} catch (IOException ioe) {
						Error error = new Error();
						error.setCode(500);
						error.setDescription(ioe.getMessage());
						response.setError(error);
						ioe.printStackTrace();
						LOGGER.error(
								"uploadFile() -> Error al guardar la plantilla de documento en el directorio indicado",
								ioe);
					} finally {
						// close the stream
						LOGGER.debug("uploadFile() -> Cierre del stream del documento");
						// stream.close();
					}
				} else {
					Error error = new Error();
					error.setCode(400);
					error.setDescription("Formato no permitido o tamaño maximo superado");
					response.setError(error);
				}
			}
		}

		LOGGER.info("uploadFile() -> Salida del servicio para subir una plantilla de documento");
		return response;
	}

	@Override
	public ResponseDocumentoDTO guardarPlantillaDocumento(HttpServletRequest request,
			DocumentoPlantillaItem documento) {
		LOGGER.info("guardarPlantillaDocumento() -> Entrada al servicio para guardar la plantilla de documento");

		Error error = new Error();
		ResponseDocumentoDTO response = new ResponseDocumentoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					ModPlantilladocumento modPlantillaDoc = new ModPlantilladocumento();

					modPlantillaDoc.setFechamodificacion(new Date());
					modPlantillaDoc.setIdioma(documento.getIdIdioma());
					modPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
					modPlantillaDoc.setPlantilla(documento.getNombreDocumento());
					modPlantillaDocumentoExtendsMapper.insert(modPlantillaDoc);

					response.setIdioma(documento.getIdioma());
					response.setNombreDocumento(documento.getNombreDocumento());
					response.setRutaDocumento(documento.getNombreDocumento());
					response.setIdPlantillaDocumento(String.valueOf(modPlantillaDoc.getIdplantilladocumento()));
				} catch (Exception e) {
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al insertar documento");
					documento.setError(error);
					e.printStackTrace();
				}

			}
		}
		LOGGER.info("guardarPlantillaDocumento() -> Salida del servicio para guardar la plantilla de documento");
		return response;
	}

	@Override
	public DocumentosPlantillaDTO obtenerPlantillasInforme(HttpServletRequest request,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info("obtenerPlantilla() -> Entrada al servicio para obtener las plantillas asociadas a un informe");

		Error error = new Error();
		List<DocumentoPlantillaItem> items = new ArrayList<DocumentoPlantillaItem>();
		DocumentosPlantillaDTO response = new DocumentosPlantillaDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					items = modPlantillaDocumentoExtendsMapper.selectPlantillasByInforme(
							Long.parseLong(plantillaDoc.getIdInforme()),
							Long.parseLong(plantillaDoc.getIdModeloComunicacion()), usuario.getIdlenguaje());

					response.setDocumentoPlantillaItem(items);
				} catch (Exception e) {
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al obtener las plantillas");
					e.printStackTrace();
				}

			}
		}
		LOGGER.info("obtenerPlantillasInforme() -> Salida del servicio las plantillas asociadas a un informe");
		return response;
	}
	

	@Override
	public ComboSufijoDTO obtenerSufijosAgrupados(HttpServletRequest request) {
		LOGGER.info("obtenerSufijosAgrupados() -> Entrada al servicio para obtener combo sufijos agrupados");

		ComboSufijoDTO comboDTO = new ComboSufijoDTO();
		List<SufijoItem> comboItems = new ArrayList<SufijoItem>();
		
		for(SufijosAgrupados s: SufijosAgrupados.values()) {
			comboItems.add(new SufijoItem(s.getId(),s.getOrden(),s.getNombre()));
		}		
		
		comboDTO.setSufijos(comboItems);

		LOGGER.info("obtenerSufijosAgrupados() -> Salida del servicio para obtener combo sufijos");

		return comboDTO;
	}
	
	@Override
	public ComboSufijoDTO obtenerSufijos(HttpServletRequest request) {
		LOGGER.info("obtenerSufijos() -> Entrada al servicio para obtener combo sufijos");

		ComboSufijoDTO comboDTO = new ComboSufijoDTO();
		List<SufijoItem> comboItems = new ArrayList<SufijoItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				if (null != usuarios && usuarios.size() > 0) {

					AdmUsuarios usuario = usuarios.get(0);
					comboItems = modPlantillaDocSufijoExtendsMapper.selectSufijos(usuario.getIdlenguaje());
					comboDTO.setSufijos(comboItems);

				}
			}
		} catch (Exception e) {
			Error error = new Error();
			error.setCode(500);
			error.setDescription("Error al guardar datos generales");
			error.setMessage(e.getMessage());
			comboDTO.setError(error);
			e.printStackTrace();
		}

		LOGGER.info("obtenerSufijos() -> Salida del servicio para obtener combo sufijos");

		return comboDTO;
	}

	@Override
	public ComboDTO obtenerFormatoSalida(HttpServletRequest request) {
		LOGGER.info("obtenerFormatoSalida() -> Entrada al servicio para obtener combo formatos de salida");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			try {

				if (null != usuarios && usuarios.size() > 0) {

					AdmUsuarios usuario = usuarios.get(0);
					comboItems = modPlantillaDocFormatoExtendsMapper.selectFormatos(usuario.getIdlenguaje());
					comboDTO.setCombooItems(comboItems);

				}

			} catch (Exception e) {
				Error error = new Error();
				error.setCode(500);
				error.setDescription("Error al guardar datos generales");
				error.setMessage(e.getMessage());
				comboDTO.setError(error);
				e.printStackTrace();
			}
		}

		LOGGER.info("obtenerFormatoSalida() -> Salida del servicio para obtener combo formatos de salida");

		return comboDTO;
	}

	@Override
	public ResponseDataDTO guardarModPlantillaDocumento(HttpServletRequest request,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info(
				"guardarModPlantillaDocumento() -> Entrada al servicio para guardar los datos de la plantilla de documento");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ResponseDataDTO respuesta = new ResponseDataDTO();
		Error error = new Error();
		Long idInforme = (long) 0;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			List<Long> listaPlantillasIdAAsociar = new ArrayList<Long>();
			try {
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);

					if (plantillaDoc.getIdModeloComunicacion() != null) {

						if (plantillaDoc.getPlantillas() != null && plantillaDoc.getPlantillas().size() > 0) {

							if (plantillaDoc.getIdInforme() != null && !"".equals(plantillaDoc.getIdInforme())) {
								LOGGER.debug("El informe ya está asociado");
								idInforme = Long.parseLong(plantillaDoc.getIdInforme());

							} else {
								idInforme = modModeloPlantillaDocumentoExtendsMapper.selectMaxInforme(
										Short.parseShort(plantillaDoc.getIdInstitucion()),
										Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
								if (idInforme == null) {
									idInforme = (long) 0;
								}
								idInforme = idInforme + (long) 1;
							}

//							// Comprobamos los sufijos guardadados al informe
//							ModRelPlantillaSufijoExample relSufijoPlantillaExample = new ModRelPlantillaSufijoExample();
//							relSufijoPlantillaExample.createCriteria()
//									.andIdmodelocomunicacionEqualTo(
//											Long.parseLong(plantillaDoc.getIdModeloComunicacion()))
//									.andIdinformeEqualTo(idInforme);
//
//							List<ModRelPlantillaSufijo> sufijosGuardados = modRelPlantillaSufijoMapper
//									.selectByExample(relSufijoPlantillaExample);
//
//							// Borramos los sufijos guardados
//							for (ModRelPlantillaSufijo modSufijo : sufijosGuardados) {
//								modRelPlantillaSufijoMapper.deleteByPrimaryKey(modSufijo.getIdplantillasufijo());
//							}
							List<DocumentoPlantillaItem> listaPlantillas = new ArrayList<>(plantillaDoc.getPlantillas().stream()
									.collect(Collectors.toMap(
											DocumentoPlantillaItem::getIdIdioma,
											x -> x,
											(plantiA,plantiB) -> plantiA
											)).values()
									);
							for (DocumentoPlantillaItem idPlantillaDoc : listaPlantillas) {

								ModPlantilladocumentoKey modeloPlantillaKey = new ModPlantilladocumentoKey();

								modeloPlantillaKey.setIdioma(idPlantillaDoc.getIdIdioma());
								modeloPlantillaKey.setIdplantilladocumento(
										Long.parseLong(idPlantillaDoc.getIdPlantillaDocumento()));
								ModPlantilladocumento modPlantillaDoc = modPlantillaDocumentoExtendsMapper
										.selectByPrimaryKey(modeloPlantillaKey);

								if (modPlantillaDoc != null) {
									ModModeloPlantilladocumentoKey modModeloPlantillaDocKey = new ModModeloPlantilladocumentoKey();
									modModeloPlantillaDocKey.setIdmodelocomunicacion(
											Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
									modModeloPlantillaDocKey
											.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());

									ModModeloPlantilladocumento modModeloPlantillaDoc = modModeloPlantilladocumentoMapper
											.selectByPrimaryKey(modModeloPlantillaDocKey);

									if (modModeloPlantillaDoc == null) {
										modModeloPlantillaDoc = new ModModeloPlantilladocumento();
										modModeloPlantillaDoc.setFechamodificacion(new Date());
										modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getIdFormatoSalida());
										modModeloPlantillaDoc
												.setNombreficherosalida(plantillaDoc.getNombreFicheroSalida());
										modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
										modModeloPlantillaDoc
												.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
										modModeloPlantillaDoc.setIdmodelocomunicacion(
												Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
										modModeloPlantillaDoc.setFechaasociacion(new Date());
										modModeloPlantillaDoc.setIdinforme(idInforme);

										modModeloPlantilladocumentoMapper.insert(modModeloPlantillaDoc);
										listaPlantillasIdAAsociar.add(modModeloPlantillaDoc.getIdplantilladocumento());

										// Si el informe ya tiene asociadas consultas se las asociamos para esta
										// plantilla
										List<ConsultaItem> listaConsultas = modPlantillaDocumentoConsultaExtendsMapper
												.selectConsultasByInforme(
														Short.parseShort(plantillaDoc.getIdInstitucion()),
														Long.parseLong(plantillaDoc.getIdModeloComunicacion()),
														idInforme, usuario.getIdlenguaje(), false);
										if (listaConsultas != null && listaConsultas.size() > 0) {
											for (ConsultaItem consulta : listaConsultas) {
												// Obtenemos la sentencia
												ConConsultaKey key = new ConConsultaKey();
												key.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
												key.setIdinstitucion(Short.valueOf(consulta.getIdInstitucion()));
												ConConsulta consultaEntity = _conConsultaMapper.selectByPrimaryKey(key);

												if (consultaEntity != null) {
													consulta.setSentencia(consultaEntity.getSentencia());
												}

												ModPlantilladocConsulta plantillaConsulta = new ModPlantilladocConsulta();
												plantillaConsulta.setIdplantilladocumento(
														modModeloPlantillaDoc.getIdplantilladocumento());
												plantillaConsulta.setFechabaja(null);
												plantillaConsulta.setFechamodificacion(new Date());
												plantillaConsulta.setUsumodificacion(usuario.getIdusuario());
												plantillaConsulta.setIdinstitucion(
														Short.parseShort(plantillaDoc.getIdInstitucion()));
												plantillaConsulta
														.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
												plantillaConsulta.setIdmodelocomunicacion(
														Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
												plantillaConsulta.setIdinstitucionConsulta(
														Short.parseShort(consulta.getIdInstitucion()));
												modPlantilladocConsultaMapper.insert(plantillaConsulta);
											}
										}
									}
								}

//								// Guardamos los sufijos recibidos
//								if (plantillaDoc.getSufijos() != null) {
//									for (SufijoItem sufijo : plantillaDoc.getSufijos()) {
//										ModRelPlantillaSufijo relSufijoPlantilla = new ModRelPlantillaSufijo();
//										relSufijoPlantilla.setIdmodelocomunicacion(
//												Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
//										relSufijoPlantilla
//												.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
//										relSufijoPlantilla.setIdsufijo(Short.parseShort(sufijo.getIdSufijo()));
//										relSufijoPlantilla.setOrden(Short.parseShort(sufijo.getOrden()));
//										relSufijoPlantilla.setIdinforme(idInforme);
//										relSufijoPlantilla.setFechamodificacion(new Date());
//										relSufijoPlantilla.setUsumodificacion(usuario.getIdusuario());
//										modRelPlantillaSufijoMapper.insertSelective(relSufijoPlantilla);
//									}
//								}
							}

							/*
							 * if(listaPlantillasAsociadas != null && listaPlantillasAsociadas.size() > 0){
							 * for(ModModeloPlantilladocumento plantillaAsociada :
							 * listaPlantillasAsociadas){
							 * if(listaPlantillasIdAAsociar.indexOf(plantillaAsociada.
							 * getIdplantilladocumento()) == -1){ // Borramos la plantilla
							 * ModModeloPlantilladocumentoKey key = new ModModeloPlantilladocumentoKey();
							 * key.setIdmodelocomunicacion(plantillaAsociada.getIdmodelocomunicacion());
							 * key.setIdplantilladocumento(plantillaAsociada.getIdplantilladocumento());
							 * modModeloPlantilladocumentoMapper.deleteByPrimaryKey(key); } } }
							 */
						}
						respuesta.setData(String.valueOf(idInforme));
					} else {
						error.setCode(500);
						error.setDescription("Error al guardar la plantilla de documento");
						error.setMessage("Error al guardar la plantilla de documento");
						respuesta.setError(error);
					}

				}
			} catch (Exception e) {
				error.setCode(500);
				error.setDescription("Error al guardar datos generales");
				error.setMessage(e.getMessage());
				e.printStackTrace();
				respuesta.setError(error);
			}

		}
		LOGGER.info(
				"guardarModPlantillaDocumento() -> Salida del servicio para guardar los datos de la plantilla de documento");
		return respuesta;
	}
	
	
	@Override
	public ResponseDataListDTO guardarModPlantillasDocumento(MultipartHttpServletRequest request,
			TarjetaPlantillaDocumentoDTO[] plantillasDoc) {
		LOGGER.info(
				"guardarModPlantillaDocumento() -> Entrada al servicio para guardar los datos de la plantilla de documento");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ResponseDataListDTO respuesta = new ResponseDataListDTO();
		Error error = new Error();		

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			try {
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					for (TarjetaPlantillaDocumentoDTO plantillaDoc : plantillasDoc) {
						procesaPlantilla(request, usuario, plantillaDoc, respuesta);
						if(respuesta.getError()!=null) {
							break;
						}
					}
				}
			} catch (Exception e) {
				error.setCode(500);
				error.setDescription("Error al guardar datos generales");
				error.setMessage(e.getMessage());
				respuesta.setError(error);
			}

		}
		LOGGER.info(
				"guardarModPlantillasDocumento() -> Salida del servicio para guardar los datos de la plantilla de documento");
		return respuesta;
	}
	

	private void tratarAdjunto(AdmUsuarios usuario, MultipartFile file, ResponseDataListDTO response, TarjetaPlantillaDocumentoDTO plantillaDoc, ModModeloPlantilladocumento modPlantillaDoc, ModPlantilladocumento mPlantillaDoc) {
		// crear path para almacenar el fichero
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.parametroRutaPlantillas);

		GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
		
		
		
		String rutaPlantillaClase = generarRutaPlantillaClase( plantillaDoc);

		String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator
				+ rutaPlantillaClase + SigaConstants.pathSeparator;

		String pathFichero = rutaPlantilla;

		// 1. Coger archivo del request
		LOGGER.debug("uploadFile() -> Coger documento del request");

		String fileName = file.getOriginalFilename();
		String extension = getExtension(fileName);
		String nombreFichero = getName(fileName);
		
		if (validFormats.contains(extension)) {
			try {
				String idPlantilla = plantillaDoc.getIdPlantillaDocumento();
				String newNombreFichero = nombreFichero + "_" + idPlantilla + "." + extension;

				File aux = new File(pathFichero);
				// creo directorio si no existe
				aux.mkdirs();
				File serverFile = new File(pathFichero, newNombreFichero);
				FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
				
				mPlantillaDoc.setFechamodificacion(new Date());
				mPlantillaDoc.setPlantilla(nombreFichero);
				mPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
				
				modPlantilladocumentoMapper.updateByPrimaryKey(mPlantillaDoc);
				
			} catch (IOException ioe) {
				Error error = new Error();
				error.setCode(500);
				error.setDescription(ioe.getMessage());
				response.setError(error);
				LOGGER.error(
						"uploadFile() -> Error al guardar la plantilla de documento en el directorio indicado",
						ioe);
			} 
		} else {
			Error error = new Error();
			error.setCode(400);
			error.setDescription("El fichero " +fileName + " no es válido. Extensiones permitidas: " + validFormats.stream().collect(Collectors.joining(",")));
			response.setError(error);
		}
	}
	
	private String getName(String fileName) {
		String name=fileName;
		if(fileName.lastIndexOf(".")>-1)
			name = fileName.substring(0, fileName.lastIndexOf("."));
		return name;
	}

	private String getExtension(String fileName) {
		String extension="";
		if(fileName.lastIndexOf(".")>-1) {
			extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).toLowerCase();
		}
		return extension;
	}

	private String generarRutaPlantillaClase(TarjetaPlantillaDocumentoDTO plantillaDoc ) {
		ModClasecomunicaciones modClase = null;
		// OBtenemos la ruta de las plantillas
				String rutaPlantillaClase = "";
				
				if (plantillaDoc.getIdClaseComunicacion() != null) {
					modClase = _modClasecomunicacionesMapper.selectByPrimaryKey(Short.parseShort(plantillaDoc.getIdClaseComunicacion()));
					if (modClase != null) {
						rutaPlantillaClase = modClase.getRutaplantilla();
					}
				}

				if (rutaPlantillaClase == null || "".equals(rutaPlantillaClase)) {
					rutaPlantillaClase = SigaConstants.rutaPlantillaSinClase;
				} else {
					rutaPlantillaClase = rutaPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO
							+ SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO,
							String.valueOf(plantillaDoc.getIdInstitucion()));
				}
				return rutaPlantillaClase;
	}

	private void procesaPlantilla(MultipartHttpServletRequest request, AdmUsuarios usuario,
			TarjetaPlantillaDocumentoDTO plantillaDoc, ResponseDataListDTO respuesta) {
		Error error = new Error();
		Long idInforme = (long) 0;
		List<Long> listaPlantillasIdAAsociar = new ArrayList<Long>();
		ModPlantilladocumento modPlantillaDoc=null;
		ModModeloPlantilladocumento modModeloPlantillaDoc=null;
		

		if (plantillaDoc.getIdModeloComunicacion() != null) {
			idInforme = obtenerIdInforme(plantillaDoc);
			if (plantillaDoc.getIdPlantillaDocumento() != null) {
				ModPlantilladocumentoKey modeloPlantillaKey = new ModPlantilladocumentoKey();
				modeloPlantillaKey.setIdioma(plantillaDoc.getIdIdioma());
				modeloPlantillaKey.setIdplantilladocumento(Long.parseLong(plantillaDoc.getIdPlantillaDocumento()));
				modPlantillaDoc = modPlantillaDocumentoExtendsMapper.selectByPrimaryKey(modeloPlantillaKey);
				ModModeloPlantilladocumentoKey modModeloPlantillaDocKey = new ModModeloPlantilladocumentoKey();
				modModeloPlantillaDocKey
						.setIdmodelocomunicacion(Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
				modModeloPlantillaDocKey.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
				modModeloPlantillaDoc = modModeloPlantilladocumentoMapper.selectByPrimaryKey(modModeloPlantillaDocKey);
			}

			if (modModeloPlantillaDoc == null) { // hay que crear un nuevo modeloplantilla
				modModeloPlantillaDoc = crearModeloPlantillaDoc(usuario, listaPlantillasIdAAsociar, plantillaDoc, idInforme);
				modPlantillaDoc = crearPlantillaDoc(usuario, modModeloPlantillaDoc, plantillaDoc);
			} else {
				actualizarModeloPlantillaDoc(usuario,modModeloPlantillaDoc,plantillaDoc);
			}
			
			tratarSufijos(usuario, modModeloPlantillaDoc, plantillaDoc);
		
			MultipartFile ficheroAdjunto = request.getFile("uploadFile_"+plantillaDoc.getIdIdioma());
			if(ficheroAdjunto!=null)
			{
			tratarAdjunto(usuario, ficheroAdjunto, respuesta,plantillaDoc, modModeloPlantillaDoc, modPlantillaDoc);
			}
			respuesta.getDataList().add(String.valueOf(idInforme));
		} else {
			error.setCode(500);
			error.setDescription("Error al guardar la plantilla de documento");
			error.setMessage("Error al guardar la plantilla de documento");
			respuesta.setError(error);
		}
	}

	private void tratarSufijos(AdmUsuarios usuario, ModModeloPlantilladocumento modModeloPlantillaDoc,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		ModRelPlantillaSufijoExample relSufijoPlantillaExample = new ModRelPlantillaSufijoExample();
		relSufijoPlantillaExample.createCriteria()
				.andIdmodelocomunicacionEqualTo(Long.parseLong(plantillaDoc.getIdModeloComunicacion()))
				.andIdinformeEqualTo(modModeloPlantillaDoc.getIdinforme())
				.andIdplantilladocumentoEqualTo(modModeloPlantillaDoc.getIdplantilladocumento());
		relSufijoPlantillaExample.setOrderByClause("ORDEN ASC");
		
		List<ModRelPlantillaSufijo> sufijosAsignados = modRelPlantillaSufijoMapper.selectByExample(relSufijoPlantillaExample);
		
		if(!mismaConfiguracionSufijos(sufijosAsignados, plantillaDoc)) {
			actualizarSufijos(usuario,relSufijoPlantillaExample,plantillaDoc);
		}
		
	}

	private void actualizarSufijos(AdmUsuarios usuario, ModRelPlantillaSufijoExample relSufijoPlantillaExample,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		SufijosAgrupados configuracion = SufijosAgrupados.valueOf(plantillaDoc.getIdSufijo());
		modRelPlantillaSufijoMapper.deleteByExample(relSufijoPlantillaExample);
		Short orden=1;
		for(Short idSufijo:configuracion.getIdSufijos()) {
			insertarSufijo(usuario, idSufijo, orden++, plantillaDoc);
		}
		
	}

	private void insertarSufijo(AdmUsuarios usuario, Short idSufijo, Short orden, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		ModRelPlantillaSufijo sufijo = new ModRelPlantillaSufijo();
		sufijo.setIdsufijo(idSufijo);
		sufijo.setOrden(orden);
		sufijo.setIdinforme(Long.valueOf(plantillaDoc.getIdInforme()));
		sufijo.setIdmodelocomunicacion(Long.valueOf(plantillaDoc.getIdModeloComunicacion()));
		sufijo.setIdplantilladocumento(Long.valueOf(plantillaDoc.getIdPlantillaDocumento()));
		sufijo.setFechamodificacion(new Date());
		sufijo.setUsumodificacion(usuario.getIdusuario());
		
		modRelPlantillaSufijoMapper.insertSelective(sufijo);
	}

	private boolean mismaConfiguracionSufijos(List<ModRelPlantillaSufijo> sufijosAsignados, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		if (plantillaDoc.getIdSufijo().equals("")) return true; // si viene vacío mantenemos configuración
		SufijosAgrupados configuracion = SufijosAgrupados.valueOf(plantillaDoc.getIdSufijo());
		List<Short> listIdSufijosAsignados = sufijosAsignados.stream().map(s->s.getIdsufijo()).collect(Collectors.toList());
		
		if(configuracion.getIdSufijos().size() != listIdSufijosAsignados.size()) {
			return false;
		}
		
		Iterator<Short> itSufijos = listIdSufijosAsignados.iterator();
		for(Short idSufijo:configuracion.getIdSufijos()) {
			Short nextSufijo = itSufijos.next(); 
			if(!nextSufijo.equals(idSufijo)) return false;
		}
		return true;
	}

	private ModPlantilladocumento crearPlantillaDoc(AdmUsuarios usuario, ModModeloPlantilladocumento modModeloPlantillaDoc, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		ModPlantilladocumento modPlantillaDoc = new ModPlantilladocumento();
		modPlantillaDoc.setFechamodificacion(new Date());
		modPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
		modPlantillaDoc.setIdioma(plantillaDoc.getIdIdioma());
		modPlantillaDoc.setIdplantilladocumento(modModeloPlantillaDoc.getIdplantilladocumento());
		modPlantilladocumentoMapper.insert(modPlantillaDoc);
		return modPlantillaDoc;
	}

	private void actualizarModeloPlantillaDoc(AdmUsuarios usuario, ModModeloPlantilladocumento modModeloPlantillaDoc,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		modModeloPlantillaDoc.setFechamodificacion(new Date());
		modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getIdFormatoSalida());
		modModeloPlantillaDoc
				.setNombreficherosalida(plantillaDoc.getNombreFicheroSalida());
		modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
		modModeloPlantilladocumentoMapper.updateByPrimaryKey(modModeloPlantillaDoc);
	}

	private Long obtenerIdInforme(TarjetaPlantillaDocumentoDTO plantillaDoc) {
		Long idInforme;
		if (plantillaDoc.getIdInforme() != null && !"".equals(plantillaDoc.getIdInforme())) {
			LOGGER.debug("El informe ya está asociado");
			idInforme = Long.parseLong(plantillaDoc.getIdInforme());

		} else {
			idInforme = modModeloPlantillaDocumentoExtendsMapper.selectMaxInforme(
					Short.parseShort(plantillaDoc.getIdInstitucion()),
					Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
			if (idInforme == null) {
				idInforme = (long) 0;
			}
			idInforme = idInforme + (long) 1;
		}
		return idInforme;
	}

	private ModModeloPlantilladocumento crearModeloPlantillaDoc(AdmUsuarios usuario, List<Long> listaPlantillasIdAAsociar, TarjetaPlantillaDocumentoDTO plantillaDoc, Long idInforme) {
		ModModeloPlantilladocumento modModeloPlantillaDoc = new ModModeloPlantilladocumento();
		
		Long idPlantillaDoc = Long.getLong(modPlantillaDocumentoExtendsMapper.selectMaxIdPlantillaDocumento().getNewId())+1l;
		
		modModeloPlantillaDoc.setFechamodificacion(new Date());
		modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getIdFormatoSalida());
		modModeloPlantillaDoc
				.setNombreficherosalida(plantillaDoc.getNombreFicheroSalida());
		modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
		modModeloPlantillaDoc
				.setIdplantilladocumento(idPlantillaDoc);
		modModeloPlantillaDoc.setIdmodelocomunicacion(
				Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
		modModeloPlantillaDoc.setFechaasociacion(new Date());
		modModeloPlantillaDoc.setIdinforme(idInforme);

		modModeloPlantilladocumentoMapper.insert(modModeloPlantillaDoc);

		
		listaPlantillasIdAAsociar.add(modModeloPlantillaDoc.getIdplantilladocumento());

		// Si el informe ya tiene asociadas consultas se las asociamos para esta
		// plantilla
		List<ConsultaItem> listaConsultas = modPlantillaDocumentoConsultaExtendsMapper
				.selectConsultasByInforme(
						Short.parseShort(plantillaDoc.getIdInstitucion()),
						Long.parseLong(plantillaDoc.getIdModeloComunicacion()),
						idInforme, usuario.getIdlenguaje(), false);
		if (listaConsultas != null && listaConsultas.size() > 0) {
			trataListaConsultas(usuario,plantillaDoc, modModeloPlantillaDoc, listaConsultas);
		}
		return modModeloPlantillaDoc;	
	}

	private void trataListaConsultas(AdmUsuarios usuario, TarjetaPlantillaDocumentoDTO plantillaDoc, ModModeloPlantilladocumento modModeloPlantillaDoc, List<ConsultaItem> listaConsultas) {
		for (ConsultaItem consulta : listaConsultas) {
			// Obtenemos la sentencia
			ConConsultaKey key = new ConConsultaKey();
			key.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
			key.setIdinstitucion(Short.valueOf(consulta.getIdInstitucion()));
			ConConsulta consultaEntity = _conConsultaMapper.selectByPrimaryKey(key);

			if (consultaEntity != null) {
				consulta.setSentencia(consultaEntity.getSentencia());
			}

			ModPlantilladocConsulta plantillaConsulta = new ModPlantilladocConsulta();
			plantillaConsulta.setIdplantilladocumento(
					modModeloPlantillaDoc.getIdplantilladocumento());
			plantillaConsulta.setFechabaja(null);
			plantillaConsulta.setFechamodificacion(new Date());
			plantillaConsulta.setUsumodificacion(usuario.getIdusuario());
			plantillaConsulta.setIdinstitucion(
					Short.parseShort(plantillaDoc.getIdInstitucion()));
			plantillaConsulta
					.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
			plantillaConsulta.setIdmodelocomunicacion(
					Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
			plantillaConsulta.setIdinstitucionConsulta(
					Short.parseShort(consulta.getIdInstitucion()));
			modPlantilladocConsultaMapper.insert(plantillaConsulta);
		}
		
	}

	@Override
	public Error borrarConsultasPlantilla(HttpServletRequest request, PlantillaDocumentoBorrarDTO[] plantillaDoc) {
		LOGGER.info(
				"borrarConsultasPlantilla() -> Entrada al servicio para borrar las consultas asociadas a un informe");

		Error respuesta = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					for (PlantillaDocumentoBorrarDTO consulta : plantillaDoc) {
						List<ConsultaItem> listaConsultasBorrar = modPlantillaDocumentoConsultaExtendsMapper
								.selectConsultaByIdConsulta(Short.parseShort(consulta.getIdInstitucion()),
										Long.parseLong(consulta.getIdModeloComunicacion()),
										Long.parseLong(consulta.getIdInforme()),
										Long.parseLong(consulta.getIdConsulta()), null);
						for (ConsultaItem consultaBorrar : listaConsultasBorrar) {
							ModPlantilladocConsulta consultaEntity = new ModPlantilladocConsulta();
							consultaEntity.setFechabaja(new Date());
							consultaEntity.setFechamodificacion(new Date());
							consultaEntity.setUsumodificacion(usuario.getIdusuario());
							consultaEntity
									.setIdplantillaconsulta(Long.parseLong(consultaBorrar.getIdPlantillaConsulta()));
							modPlantilladocConsultaMapper.updateByPrimaryKeySelective(consultaEntity);
						}
					}

					respuesta.setCode(200);
					respuesta.setMessage("Consultas eliminadas correctamente");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al obtener las plantillas");
					e.printStackTrace();
				}

			}
		}
		LOGGER.info(
				"borrarConsultasPlantilla() -> Salida del servicio para borrar las consultas asociadas a un informe");
		return respuesta;
	}

	@Override
	public Error borrarPlantillas(HttpServletRequest request, PlantillaDocumentoBorrarDTO[] plantillaDoc) {
		LOGGER.info("borrarPlantillas() -> Entrada al servicio para borrar las plantillas asociadas a un informe");

		Error respuesta = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {

					for (PlantillaDocumentoBorrarDTO plantilla : plantillaDoc) {
						if (plantilla.getIdPlantillaDocumento() != null) {
							ModModeloPlantilladocumentoKey key = new ModModeloPlantilladocumentoKey();
							key.setIdmodelocomunicacion(Long.parseLong(plantilla.getIdModeloComunicacion()));
							key.setIdplantilladocumento(Long.parseLong(plantilla.getIdPlantillaDocumento()));
							ModModeloPlantilladocumento plantillaBorrar = modModeloPlantilladocumentoMapper
									.selectByPrimaryKey(key);
							if (plantillaBorrar != null) {
								plantillaBorrar.setFechabaja(new Date());
								plantillaBorrar.setFechamodificacion(new Date());
								plantillaBorrar.setUsumodificacion(usuario.getIdusuario());
								modModeloPlantilladocumentoMapper.updateByPrimaryKey(plantillaBorrar);

								// También ponemos fecha de baja a las consultas asociadas a esa plantilla
								ModPlantilladocConsultaExample example = new ModPlantilladocConsultaExample();
								example.createCriteria()
										.andIdmodelocomunicacionEqualTo(
												Long.parseLong(plantilla.getIdModeloComunicacion()))
										.andIdplantilladocumentoEqualTo(
												Long.parseLong(plantilla.getIdPlantillaDocumento()))
										.andIdinstitucionEqualTo(Short.parseShort(plantilla.getIdInstitucion()));
								List<ModPlantilladocConsulta> listaConsultas = modPlantilladocConsultaMapper
										.selectByExample(example);
								for (ModPlantilladocConsulta consultaBorrar : listaConsultas) {
									consultaBorrar.setFechabaja(new Date());
									modPlantilladocConsultaMapper.updateByPrimaryKey(consultaBorrar);
								}
							}
						}
					}

					respuesta.setCode(200);
					respuesta.setMessage("Consultas eliminadas correctamente");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al obtener las plantillas");
					e.printStackTrace();
				}

			}
		}
		LOGGER.info("borrarPlantillas() -> Salida del servicio para borrar las plantillas asociadas a un informe");
		return respuesta;
	}

	@Override
	public ResponseFileDTO descargarPlantilla(HttpServletRequest request, DocumentoPlantillaItem plantillaDoc) {

		LOGGER.info("descargarPlantilla() -> Entrada al servicio para descargar la plantilla de documento");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseFileDTO response = new ResponseFileDTO();
		Error error = new Error();
		ModClasecomunicaciones modClase = null;
		String rutaPlantillaClase = "";

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				try {

					// Obtenemos el idPlantillaDocumento
					String idPlantillaDoc = plantillaDoc.getIdPlantillaDocumento();
					ModPlantilladocumentoExample modeloPlantillaKey = new ModPlantilladocumentoExample();
					modeloPlantillaKey.createCriteria().andIdplantilladocumentoEqualTo(Long.parseLong(idPlantillaDoc))
							.andIdiomaEqualTo(plantillaDoc.getIdIdioma());
//					modeloPlantillaKey.setIdioma(plantillaDoc.getIdIdioma());
//					modeloPlantillaKey.setIdplantilladocumento(Long.parseLong(idPlantillaDoc));
//					ModPlantilladocumento modPlantillaDoc = modPlantillaDocumentoExtendsMapper
//							.selectByPrimaryKey(modeloPlantillaKey);

					List<ModPlantilladocumento> plantillaDocumentoList = modPlantilladocumentoMapper
							.selectByExample(modeloPlantillaKey);

					if (plantillaDocumentoList != null && plantillaDocumentoList.size() > 0) {

						ModPlantilladocumento plantillaDocumento = plantillaDocumentoList.get(0);

						// OBtenemos la ruta de las plantillas
						if (plantillaDoc.getIdClaseComunicacion() != null) {
							modClase = _modClasecomunicacionesMapper
									.selectByPrimaryKey(Short.parseShort(plantillaDoc.getIdClaseComunicacion()));
							if (modClase != null) {
								rutaPlantillaClase = modClase.getRutaplantilla();
							}
						}

						if ("".equals(rutaPlantillaClase)) {
							rutaPlantillaClase = SigaConstants.rutaPlantillaSinClase;
						} else {
							rutaPlantillaClase = rutaPlantillaClase
									.replaceAll(
											SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION
													+ SigaConstants.REPLACECHAR_PREFIJO_SUFIJO,
											String.valueOf(idInstitucion));
						}

						// Obtenemos el nombre del fichero
						String nombrePlantilla = plantillaDocumento.getPlantilla();

						GenPropertiesKey key = new GenPropertiesKey();
						key.setFichero(SigaConstants.FICHERO_SIGA);
						key.setParametro(SigaConstants.parametroRutaPlantillas);

						GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);

						String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator
								+ rutaPlantillaClase + SigaConstants.pathSeparator;

						String pathFichero = rutaPlantilla;

						File file = new File(pathFichero + nombrePlantilla);

						if (!file.exists()) {
							error.setCode(404);
							error.setDescription("Fichero no encontrado");
							error.setMessage("Fichero no encontrado");
							response.setError(error);
							LOGGER.debug("Plantilla no encontrada: " + idPlantillaDoc + " para la institucion "
									+ idInstitucion);
						}
						response.setFile(file);
					}
				} catch (Exception e) {
					LOGGER.error("ejecutarConsulta() -> Error al ejecutar la consulta: " + e.getMessage());
					e.printStackTrace();
					error.setCode(500);
					error.setDescription(e.getCause().toString());
					error.setMessage("Error al ejecutar la consulta");
				}

			}
		}
		LOGGER.info("descargarPlantilla() -> Salida del servicio para descargar la plantilla de documento");
		return response;
	}

	@Override
	public ConsultasDTO obtenerConsultasById(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info(
				"obtenerConsultasById() -> Entrada al servicio para obtener las consultas de una plantilla de documento");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		ConsultasDTO respuesta = new ConsultasDTO();
		List<ConsultaItem> listaConsultaItem = new ArrayList<ConsultaItem>();

		if (null != idInstitucionUser) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					listaConsultaItem = conConsultasExtendsMapper.selectConsultasById(
							Short.parseShort(plantillaDoc.getIdInstitucion()), usuario.getIdlenguaje(),
							plantillaDoc.getIdConsulta());
					if (listaConsultaItem != null && listaConsultaItem.size() > 0) {
						for (ConsultaItem consulta : listaConsultaItem) {
							String finalidad = plantillasEnvioService.obtenerFinalidadByIdConsulta(
									Short.parseShort(plantillaDoc.getIdInstitucion()),
									Long.parseLong(consulta.getIdConsulta()));
							consulta.setFinalidad(finalidad);
						}
						respuesta.setConsultaItem(listaConsultaItem);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		LOGGER.info(
				"obtenerConsultasById() -> Salida del servicio para obtener las consultas de una plantilla de documento");
		return respuesta;
	}

	@Override
	public ComboDTO obtenerSizeFichero(HttpServletRequest request) {

		LOGGER.info(
				"obtenerSizeFichero() -> Entrada al servicio para obtener el tamaño máximo de los ficheros de entrada");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			try {

				if (null != usuarios && usuarios.size() > 0) {

					// crear path para almacenar el fichero
					GenPropertiesKey key = new GenPropertiesKey();
					key.setFichero(SigaConstants.FICHERO_SIGA);
					key.setParametro(SigaConstants.parametroSizePlantillas);

					GenProperties sizeFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
					ComboItem size = new ComboItem();
					size.setLabel("size");
					size.setValue(sizeFicherosPlantilla.getValor());
					comboItems.add(size);

					comboDTO.setCombooItems(comboItems);

				}

			} catch (Exception e) {
				Error error = new Error();
				error.setCode(500);
				error.setDescription("Error al obtener tamaño máximo de los ficheros de entrada");
				error.setMessage(e.getMessage());
				comboDTO.setError(error);
				e.printStackTrace();
			}
		}

		LOGGER.info("obtenerSizeFichero() -> Salida del servicio para obtener el tamaño de los ficheros de entrada");

		return comboDTO;
	}

	@Override
	@Transactional(timeout=2400)
	public ResponseDataDTO guardarDatosSalida(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info(
				"guardarDatosSalida() -> Entrada al servicio para guardar los datos de salida de la plantilla de documento");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ResponseDataDTO respuesta = new ResponseDataDTO();
		List<DocumentoPlantillaItem> plantillas = new ArrayList<DocumentoPlantillaItem>();
		Error error = new Error();
		Long idInforme = (long) 0;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarDatosSalida() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener usuario logueado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarDatosSalida() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener usuario logueado");

			try {
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);

					if (plantillaDoc.getIdModeloComunicacion() != null) {

						// Si no hay hay plantillas se generan las plantillas por defecto
						if (!(plantillaDoc.getPlantillas() != null && plantillaDoc.getPlantillas().size() > 0)) {
							plantillas = addModPlantillasDocumentos(plantillaDoc, usuario, idInstitucion);

							if (plantillas != null && plantillas.size() > 0) {
								plantillaDoc.setPlantillas(plantillas);
							}
						}

						if (plantillaDoc.getIdInforme() != null && !"".equals(plantillaDoc.getIdInforme())) {
							LOGGER.debug("El informe ya está asociado");
							idInforme = Long.parseLong(plantillaDoc.getIdInforme());

						} else {

							LOGGER.info(
									"guardarDatosSalida() / modModeloPlantillaDocumentoExtendsMapper.selectByExample() -> Entrada a modModeloPlantillaDocumentoExtendsMapper para obtener idInforme modelo de la plantilla");

							idInforme = modModeloPlantillaDocumentoExtendsMapper.selectMaxInforme(
									Short.parseShort(plantillaDoc.getIdInstitucion()),
									Long.parseLong(plantillaDoc.getIdModeloComunicacion()));

							LOGGER.info(
									"guardarDatosSalida() / modModeloPlantillaDocumentoExtendsMapper.selectByExample() -> Salida de modModeloPlantillaDocumentoExtendsMapper para obtener idInforme modelo de la plantilla");

							if (idInforme == null) {
								idInforme = (long) 0;
							}
							idInforme = idInforme + (long) 1;
						}

						// Comprobamos los sufijos guardadados al informe
						ModRelPlantillaSufijoExample relSufijoPlantillaExample = new ModRelPlantillaSufijoExample();
						relSufijoPlantillaExample.createCriteria()
								.andIdmodelocomunicacionEqualTo(Long.parseLong(plantillaDoc.getIdModeloComunicacion()))
								.andIdinformeEqualTo(idInforme);

						LOGGER.info(
								"guardarDatosSalida() / modRelPlantillaSufijoMapper.selectByExample() -> Entrada a modRelPlantillaSufijoMapper para obtener los sufijos relacionados con la plantilla");

						List<ModRelPlantillaSufijo> sufijosGuardados = modRelPlantillaSufijoMapper
								.selectByExample(relSufijoPlantillaExample);

						LOGGER.info(
								"guardarDatosSalida() / modRelPlantillaSufijoMapper.selectByExample() -> Salida de modRelPlantillaSufijoMapper para obtener los sufijos relacionados con la plantilla");

						// Borramos los sufijos guardados
						for (ModRelPlantillaSufijo modSufijo : sufijosGuardados) {

							LOGGER.info(
									"guardarDatosSalida() / modRelPlantillaSufijoMapper.selectByExample() -> Entrada a modRelPlantillaSufijoMapper para eliminar los sufijos relacionados con la plantilla");

							modRelPlantillaSufijoMapper.deleteByPrimaryKey(modSufijo.getIdplantillasufijo());

							LOGGER.info(
									"guardarDatosSalida() / modRelPlantillaSufijoMapper.selectByExample() -> Salida de modRelPlantillaSufijoMapper para eliminar los sufijos relacionados con la plantilla");
						}

						for (DocumentoPlantillaItem idPlantillaDoc : plantillaDoc.getPlantillas()) {

							ModPlantilladocumentoKey modeloPlantillaKey = new ModPlantilladocumentoKey();

							modeloPlantillaKey.setIdioma(idPlantillaDoc.getIdIdioma());
							modeloPlantillaKey
									.setIdplantilladocumento(Long.parseLong(idPlantillaDoc.getIdPlantillaDocumento()));

							LOGGER.info(
									"guardarDatosSalida() / modPlantillaDocumentoExtendsMapper.selectByExample() -> Entrada a modPlantillaDocumentoExtendsMapper para obtener la plantilla documento");

							ModPlantilladocumento modPlantillaDoc = modPlantillaDocumentoExtendsMapper
									.selectByPrimaryKey(modeloPlantillaKey);

							LOGGER.info(
									"guardarDatosSalida() / modPlantillaDocumentoExtendsMapper.selectByExample() -> Salida de modPlantillaDocumentoExtendsMapper para obtener la plantilla documento");

							if (modPlantillaDoc != null) {

								ModModeloPlantilladocumentoKey modModeloPlantillaDocKey = new ModModeloPlantilladocumentoKey();
								modModeloPlantillaDocKey.setIdmodelocomunicacion(
										Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
								modModeloPlantillaDocKey
										.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());

								LOGGER.info(
										"guardarDatosSalida() / modModeloPlantilladocumentoMapper.selectByExample() -> Entrada a modPlantillaDocumentoExtendsMapper para obtener el modelo de la plantilla documento");

								ModModeloPlantilladocumento modModeloPlantillaDoc = modModeloPlantilladocumentoMapper
										.selectByPrimaryKey(modModeloPlantillaDocKey);

								LOGGER.info(
										"guardarDatosSalida() / modModeloPlantilladocumentoMapper.selectByExample() -> Salida de modPlantillaDocumentoExtendsMapper para obtener el modelo de la plantilla documento");

								if (modModeloPlantillaDoc != null) {
									modModeloPlantillaDoc.setFechamodificacion(new Date());
									modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getIdFormatoSalida());
									modModeloPlantillaDoc.setNombreficherosalida(plantillaDoc.getNombreFicheroSalida());
									modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());

									LOGGER.info(
											"guardarDatosSalida() / modModeloPlantilladocumentoMapper.selectByExample() -> Entrada a modPlantillaDocumentoExtendsMapper para modificar el modelo de la plantilla documento");

									modModeloPlantilladocumentoMapper.updateByPrimaryKey(modModeloPlantillaDoc);

									LOGGER.info(
											"guardarDatosSalida() / modModeloPlantilladocumentoMapper.selectByExample() -> Salida de modPlantillaDocumentoExtendsMapper para modificar el modelo de la plantilla documento");

								}else {

									modModeloPlantillaDoc = new ModModeloPlantilladocumento();
									modModeloPlantillaDoc.setFechamodificacion(new Date());
									modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getIdFormatoSalida());
									modModeloPlantillaDoc
											.setNombreficherosalida(plantillaDoc.getNombreFicheroSalida());
									modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
									modModeloPlantillaDoc
											.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
									modModeloPlantillaDoc.setIdmodelocomunicacion(
											Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
									modModeloPlantillaDoc.setFechaasociacion(new Date());
									modModeloPlantillaDoc.setIdinforme(idInforme);

									modModeloPlantilladocumentoMapper.insert(modModeloPlantillaDoc);

									// Si el informe ya tiene asociadas consultas se las asociamos para esta
									// plantilla
									List<ConsultaItem> listaConsultas = modPlantillaDocumentoConsultaExtendsMapper
											.selectConsultasByInforme(
													Short.parseShort(plantillaDoc.getIdInstitucion()),
													Long.parseLong(plantillaDoc.getIdModeloComunicacion()),
													idInforme, usuario.getIdlenguaje(), false);
									if (listaConsultas != null && listaConsultas.size() > 0) {
										for (ConsultaItem consulta : listaConsultas) {
											// Obtenemos la sentencia
											ConConsultaKey key = new ConConsultaKey();
											key.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
											key.setIdinstitucion(Short.valueOf(consulta.getIdInstitucion()));
											ConConsulta consultaEntity = _conConsultaMapper.selectByPrimaryKey(key);

											if (consultaEntity != null) {
												consulta.setSentencia(consultaEntity.getSentencia());
											}

											ModPlantilladocConsulta plantillaConsulta = new ModPlantilladocConsulta();
											plantillaConsulta.setIdplantilladocumento(
													modModeloPlantillaDoc.getIdplantilladocumento());
											plantillaConsulta.setFechabaja(null);
											plantillaConsulta.setFechamodificacion(new Date());
											plantillaConsulta.setUsumodificacion(usuario.getIdusuario());
											plantillaConsulta.setIdinstitucion(
													Short.parseShort(plantillaDoc.getIdInstitucion()));
											plantillaConsulta
													.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
											plantillaConsulta.setIdmodelocomunicacion(
													Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
											plantillaConsulta.setIdinstitucionConsulta(
													Short.parseShort(consulta.getIdInstitucion()));
											modPlantilladocConsultaMapper.insert(plantillaConsulta);
										}
									}
								
								}

								// Guardamos los sufijos recibidos
								if (plantillaDoc.getSufijos() != null) {
									for (SufijoItem sufijo : plantillaDoc.getSufijos()) {
										ModRelPlantillaSufijo relSufijoPlantilla = new ModRelPlantillaSufijo();
										relSufijoPlantilla.setIdmodelocomunicacion(
												Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
										relSufijoPlantilla
												.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
										relSufijoPlantilla.setIdsufijo(Short.parseShort(sufijo.getIdSufijo()));
										relSufijoPlantilla.setOrden(Short.parseShort(sufijo.getOrden()));
										relSufijoPlantilla.setIdinforme(idInforme);
										relSufijoPlantilla.setFechamodificacion(new Date());
										relSufijoPlantilla.setUsumodificacion(usuario.getIdusuario());

										LOGGER.info(
												"guardarDatosSalida() / modRelPlantillaSufijoMapper.selectByExample() -> Entrada a modRelPlantillaSufijoMapper para guardar los sufijos del modelo de la plantilla documento");

										modRelPlantillaSufijoMapper.insertSelective(relSufijoPlantilla);

										LOGGER.info(
												"guardarDatosSalida() / modRelPlantillaSufijoMapper.selectByExample() -> Salida de modRelPlantillaSufijoMapper para guardar los sufijos del modelo de la plantilla documento");

									}
								}
							}

						}
						
						respuesta.setData(String.valueOf(idInforme));

					} else {
						error.setCode(500);
						error.setDescription("Error al guardar los datos de salida de la plantilla de documento");
						error.setMessage("Error al guardar los datos de salida de la plantilla de documento");
						respuesta.setError(error);
					}

				}
			} catch (Exception e) {
				error.setCode(500);
				error.setDescription("Error al guardar datos generales");
				error.setMessage(e.getMessage());
				e.printStackTrace();
				respuesta.setError(error);
			}

		}
		LOGGER.info(
				"guardarDatosSalida() -> Salida del servicio para guardar los datos de salida de la plantilla de documento");
		return respuesta;
	}

	private List<DocumentoPlantillaItem> addModPlantillasDocumentos(TarjetaPlantillaDocumentoDTO modeloCom,
			AdmUsuarios usuario, Short idInstitucion) {
		LOGGER.info("addModPlantillasDocumentos() -> Entrada al servicio para generar las plantillas por defecto");

		List<DocumentoPlantillaItem> plantillas = new ArrayList<DocumentoPlantillaItem>();
		List<ModPlantilladocumento> modPlantillas = new ArrayList<ModPlantilladocumento>();

		Integer i = 0;

		do {

			try {

				ModPlantilladocumento plantilla = new ModPlantilladocumento();
				plantilla.setFechamodificacion(new Date());
				plantilla.setPlantilla(SigaConstants.nombreExcelConsulta);
				plantilla.setUsumodificacion(usuario.getIdusuario());
				Integer idioma = i + 1;
				plantilla.setIdioma(idioma.toString());

				LOGGER.info(
						"addModPlantillasDocumentos() / modPlantilladocumentoMapper.insert() -> Entrada a modPlantilladocumentoMapper para insertar plantilla por defecto");

				modPlantilladocumentoMapper.insert(plantilla);

				LOGGER.info(
						"addModPlantillasDocumentos() / modPlantilladocumentoMapper.insert() -> Salida de modPlantilladocumentoMapper para insertar plantilla por defecto");

				modPlantillas.add(plantilla);
				i++;

			} catch (Exception e) {
				LOGGER.info("Error" + e.getMessage());
			}

		} while (i < 4);

		for (ModPlantilladocumento modPlantilladocumento : modPlantillas) {

			DocumentoPlantillaItem documentoPlantillaItem = new DocumentoPlantillaItem();
			documentoPlantillaItem.setIdIdioma(modPlantilladocumento.getIdioma());
			documentoPlantillaItem.setIdPlantillaDocumento(modPlantilladocumento.getIdplantilladocumento().toString());

			plantillas.add(documentoPlantillaItem);

		}

		LOGGER.info("addModPlantillasDocumentos() -> Salida del servicio para generar las plantillas por defecto");
		return plantillas;
	}

}
