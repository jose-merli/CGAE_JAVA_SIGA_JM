package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.DTOs.com.PlantillaModeloItem;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDTO;
import org.itcgae.siga.DTOs.com.PlantillasModeloDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.SufijoItem;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDTO;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EnvPlantillasenvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModModeloPerfiles;
import org.itcgae.siga.db.entities.ModModeloPerfilesExample;
import org.itcgae.siga.db.entities.ModModeloPerfilesKey;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumento;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvio;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioExample;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioKey;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModRelPlantillaSufijo;
import org.itcgae.siga.db.entities.ModRelPlantillaSufijoExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.ModModeloPerfilesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantillaenvioMapper;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocConsultaMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModRelPlantillaSufijoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPerfilesExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModRelPlantillaSufijoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=2400)
public class ModelosYcomunicacionesServiceImpl implements IModelosYcomunicacionesService {

	private Logger LOGGER = Logger.getLogger(ModelosYcomunicacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;

	@Autowired
	private ModModeloComunicacionExtendsMapper modModeloComunicacionExtendsMapper;

	@Autowired
	private ModModelocomunicacionMapper modModelocomunicacionMapper;

	@Autowired
	private ModModeloPerfilesMapper modModeloPerfilesMapper;

	@Autowired
	private ModModeloPlantilladocumentoMapper modModeloPlantilladocumentoMapper;

	@Autowired
	private ModModeloPlantillaenvioMapper modModeloPlantillaenvioMapper;

	@Autowired
	private ModPlantilladocConsultaMapper modPlantilladocConsultaMapper;

	@Autowired
	private ModPlantillaDocumentoConsultaExtendsMapper modPlantillaDocumentoConsultaExtendsMapper;

	@Autowired
	private ModModeloPerfilesExtendsMapper modModeloPerfilesExtendsMapper;

	@Autowired
	private ModModeloPlantillaDocumentoExtendsMapper modModeloPlantillaDocumentoExtendsMapper;

	@Autowired
	private ModModeloPlantillaEnvioExtendsMapper _modModeloPlantillaEnvioExtendsMapper;

	@Autowired
	private ModRelPlantillaSufijoExtendsMapper modRelPlantillaSufijoExtendsMapper;

	@Autowired
	private EnvPlantillaEnviosExtendsMapper envPlantillaEnviosExtendsMapper;

	@Autowired
	private ModPlantilladocumentoMapper modPlantilladocumentoMapper;

	@Autowired
	private CenInstitucionExtendsMapper _cenInstitucionExtendsMapper;

	@Autowired
	private ModClasecomunicacionesMapper _modClasecomunicacionesMapper;

	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;

	@Autowired
	private ModRelPlantillaSufijoMapper modRelPlantillaSufijoMapper;

	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request,
			DatosModelosComunicacionesSearch filtros, boolean historico) {

		LOGGER.info("modeloYComunicacionesSearch() -> Entrada al servicio para busqueda de modelos de comunicación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		DatosModelosComunicacionesDTO respuesta = new DatosModelosComunicacionesDTO();
		List<ModelosComunicacionItem> modelosItem = new ArrayList<ModelosComunicacionItem>();

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					modelosItem = modModeloComunicacionExtendsMapper.selectModelosComunicacion(
							String.valueOf(idInstitucion), filtros, usuario.getIdlenguaje(), historico);
					if (modelosItem != null && modelosItem.size() > 0) {
						respuesta.setModelosComunicacionItem(modelosItem);
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

		LOGGER.info("modeloYComunicacionesSearch() -> Salida del servicio para busqueda de modelos de comunicación");
		return respuesta;
	}

	@Override
	public String duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion) {

		LOGGER.info("duplicarModeloComunicaciones() -> Entrada al servicio para duplicar un modelos de comunicación");

		Error respuesta = new Error();
		String idDuplicado = "";

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			ModClasecomunicaciones modClase = null;
			String rutaPlantillaClase = "";

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					// Tabla mod_modelocomunicacion
					ModModelocomunicacion modelo = modModelocomunicacionMapper
							.selectByPrimaryKey(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));

					modelo.setIdmodelocomunicacion(null);

					// Comprobamos el nombre del modelo duplicado
					// En caso de que no exista ningun duplicado anterior (*_COPIA), ese será su
					// nombre
					// En caso contrario, se añadira un contador (*_COPIA1)

					String nuevoNombre = comprobarNombreDuplicado(modelo.getNombre());
					Short idInstitucionModelo = Short.parseShort(modeloComunicacion.getIdInstitucion());

					if (idInstitucionModelo != null
							&& String.valueOf(idInstitucionModelo).equals(SigaConstants.IDINSTITUCION_0)) {
						idInstitucionModelo = SigaConstants.IDINSTITUCION_2000;
					}

					// OBtenemos la ruta de las plantillas
					if (modelo.getIdclasecomunicacion() != null) {
						modClase = _modClasecomunicacionesMapper.selectByPrimaryKey(modelo.getIdclasecomunicacion());
						if (modClase != null) {
							rutaPlantillaClase = modClase.getRutaplantilla();
						}
					}

					if (rutaPlantillaClase == null || "".equals(rutaPlantillaClase)) {
						rutaPlantillaClase = SigaConstants.rutaPlantillaSinClase;
					} else {
						rutaPlantillaClase = rutaPlantillaClase
								.replaceAll(
										SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION
												+ SigaConstants.REPLACECHAR_PREFIJO_SUFIJO,
										String.valueOf(idInstitucion));
					}

					// String nuevoNombre = modelo.getNombre() +
					// SigaConstants.SUFIJO_MODULO_COM_DUPLICADO;
					// if(nuevoNombre.length() < SigaConstants.NOMBRE_MAXLENGTH){
					// modelo.setNombre(nuevoNombre);
					// }else{
					// nuevoNombre = nuevoNombre.substring(0,SigaConstants.NOMBRE_MAXLENGTH);
					// modelo.setNombre(nuevoNombre);
					// }

					// Si el modelo de comunicacion tiene marcado el campo "PORDEFECTO" a SI,
					// tendremos que utilizar la institucion actual
					// Si no está marcado "PORDEFECTO" a SI, al modelo duplicado le pondremos la
					// institucion del modelo original.
					if (modelo.getPordefecto() == null || modelo.getPordefecto().equalsIgnoreCase("NO"))
						modelo.setIdinstitucion(idInstitucionModelo);
					else {
						modelo.setIdinstitucion(idInstitucion);
						if (idInstitucion != SigaConstants.IDINSTITUCION_2000) {
							modelo.setPordefecto("NO");
						}
					}

					modelo.setNombre(nuevoNombre);
					modelo.setFechabaja(null);
					modelo.setIdmodelocomunicacion(null);
					modelo.setFechamodificacion(new Date());

					modModelocomunicacionMapper.insert(modelo);
					idDuplicado = String
							.valueOf(modelo.getIdmodelocomunicacion() == null ? "" : modelo.getIdmodelocomunicacion());

					// Tabla mod_modelo_perfiles
					ModModeloPerfilesExample example = new ModModeloPerfilesExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucionModelo)
							.andIdmodelocomunicacionEqualTo(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));

					List<ModModeloPerfiles> listaModPerfiles = modModeloPerfilesMapper.selectByExample(example);

					if (listaModPerfiles != null) {
						for (ModModeloPerfiles modPerfil : listaModPerfiles) {
							modPerfil.setFechamodificacion(new Date());
							modPerfil.setIdinstitucion(modelo.getIdinstitucion());
							modPerfil.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
							modModeloPerfilesMapper.insert(modPerfil);
						}
					}

					// Tabla mod_modelo_plantilladocumento
					ModModeloPlantilladocumentoExample examplePlantillaDoc = new ModModeloPlantilladocumentoExample();
					examplePlantillaDoc.createCriteria()
							.andIdmodelocomunicacionEqualTo(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()))
							.andFechabajaIsNull();

					List<ModModeloPlantilladocumento> listaModPlantilla = modModeloPlantilladocumentoMapper
							.selectByExample(examplePlantillaDoc);

					if (listaModPlantilla != null) {
						for (ModModeloPlantilladocumento modPlantilla : listaModPlantilla) {
							Long idPlantillaDuplicar = modPlantilla.getIdplantilladocumento();
							modPlantilla.setFechamodificacion(new Date());
							modPlantilla.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
							modPlantilla.setFechaasociacion(new Date());
							modModeloPlantilladocumentoMapper.insert(modPlantilla);

							// Duplicamos los sufijos
							ModRelPlantillaSufijoExample relSufijoExample = new ModRelPlantillaSufijoExample();
							relSufijoExample.createCriteria().andIdplantilladocumentoEqualTo(idPlantillaDuplicar)
									.andIdinformeEqualTo(modPlantilla.getIdinforme());
							List<ModRelPlantillaSufijo> listaSufijos = modRelPlantillaSufijoMapper
									.selectByExample(relSufijoExample);
							if (listaSufijos != null && listaSufijos.size() > 0) {
								for (ModRelPlantillaSufijo sufijo : listaSufijos) {
									sufijo.setIdplantillasufijo(null);
									sufijo.setIdplantilladocumento(modPlantilla.getIdplantilladocumento());
									sufijo.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
									sufijo.setFechamodificacion(new Date());
									sufijo.setUsumodificacion(usuario.getIdusuario());
									modRelPlantillaSufijoMapper.insert(sufijo);
								}
							}

							// Tabla mod_plantilladoc_consulta
							ModPlantilladocConsultaExample examplePlantillaConsulta = new ModPlantilladocConsultaExample();
							examplePlantillaConsulta.createCriteria().andIdinstitucionEqualTo(idInstitucionModelo)
									.andIdmodelocomunicacionEqualTo(
											Long.valueOf(modeloComunicacion.getIdModeloComunicacion()))
									.andIdplantilladocumentoEqualTo(idPlantillaDuplicar);

							List<ModPlantilladocConsulta> listaModPlantillaConsulta = modPlantilladocConsultaMapper
									.selectByExample(examplePlantillaConsulta);

							if (listaModPlantillaConsulta != null) {
								for (ModPlantilladocConsulta modPlantillaConsulta : listaModPlantillaConsulta) {
									modPlantillaConsulta.setFechamodificacion(new Date());
									modPlantillaConsulta
											.setIdmodelocomunicacion(modPlantilla.getIdmodelocomunicacion());
									modPlantillaConsulta.setIdinstitucion(modelo.getIdinstitucion());
									modPlantillaConsulta.setIdplantillaconsulta(null);
									modPlantillaConsulta
											.setIdplantilladocumento(modPlantilla.getIdplantilladocumento());
									modPlantilladocConsultaMapper.insert(modPlantillaConsulta);
								}
							}

							// Duplicamos la plantilla fisicamente si la institucion es del general
							if (idInstitucionModelo != modelo.getIdinstitucion()) {

								GenPropertiesKey key = new GenPropertiesKey();
								key.setFichero(SigaConstants.FICHERO_SIGA);
								key.setParametro(SigaConstants.parametroRutaPlantillas);

								GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);

								String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator
										+ rutaPlantillaClase + SigaConstants.pathSeparator;
								String rutaPlantillaDestino = rutaFicherosPlantilla.getValor()
										+ SigaConstants.pathSeparator + rutaPlantillaClase + SigaConstants.pathSeparator
										+ String.valueOf(modelo.getIdinstitucion()) + SigaConstants.pathSeparator;

								ModPlantilladocumentoExample examplePlantilla = new ModPlantilladocumentoExample();
								examplePlantilla.createCriteria()
										.andIdplantilladocumentoEqualTo(modPlantilla.getIdplantilladocumento());
								List<ModPlantilladocumento> listaPlantillasDoc = modPlantilladocumentoMapper
										.selectByExample(examplePlantilla);
								if (listaPlantillasDoc != null && listaPlantillasDoc.size() > 0) {
									for (ModPlantilladocumento plantillaDoc : listaPlantillasDoc) {
										File ficheroCopiar = new File(rutaPlantilla + plantillaDoc.getPlantilla());
										File ficheroDestino = new File(
												rutaPlantillaDestino + plantillaDoc.getPlantilla());
										if (ficheroCopiar.exists()) {
											Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()),
													Paths.get(ficheroDestino.getAbsolutePath()),
													StandardCopyOption.REPLACE_EXISTING);
										}
									}
								}

							}

						}
					}

					// Tabla mod_modelo_plantillaenvio
					/*
					 * ModModeloPlantillaenvioExample examplePlantillaEnvio = new
					 * ModModeloPlantillaenvioExample();
					 * examplePlantillaEnvio.createCriteria().andIdmodelocomunicacionEqualTo(Long.
					 * valueOf(modeloComunicacion.getIdModeloComunicacion())).
					 * andIdinstitucionEqualTo(Short.parseShort(modeloComunicacion.getIdInstitucion(
					 * ))).andFechabajaIsNull();
					 * 
					 * List<ModModeloPlantillaenvio> listaModPlantillaEnvio =
					 * modModeloPlantillaenvioMapper.selectByExample(examplePlantillaEnvio);
					 * 
					 * if(listaModPlantillaEnvio != null){ for(ModModeloPlantillaenvio
					 * modPlantillaEnvio : listaModPlantillaEnvio){ int idPlantillaEnvioDuplicar =
					 * modPlantillaEnvio.getIdplantillaenvios();
					 * 
					 * //Tabla EnvPlantillasenvios EnvPlantillasenviosExample envioExample = new
					 * EnvPlantillasenviosExample();
					 * envioExample.createCriteria().andIdinstitucionEqualTo(modPlantillaEnvio.
					 * getIdinstitucion()).andIdplantillaenviosEqualTo(idPlantillaEnvioDuplicar).
					 * andIdtipoenviosEqualTo(modPlantillaEnvio.getIdtipoenvios()).
					 * andFechabajaIsNull(); List<EnvPlantillasenviosWithBLOBs>
					 * listaPlantillasEnvios =
					 * envPlantillasenviosMapper.selectByExampleWithBLOBs(envioExample);
					 * if(listaPlantillasEnvios != null & listaPlantillasEnvios.size() > 0){
					 * for(EnvPlantillasenviosWithBLOBs plantillaEnvio : listaPlantillasEnvios){
					 * plantillaEnvio.setIdplantillaenvios(Integer.parseInt(
					 * envPlantillaEnviosExtendsMapper.selectMaxIDPlantillas().getNewId()));
					 * plantillaEnvio.setUsumodificacion(usuario.getIdusuario());
					 * plantillaEnvio.setFechamodificacion(new Date());
					 * envPlantillasenviosMapper.insert(plantillaEnvio);
					 * 
					 * modPlantillaEnvio.setIdplantillaenvios(plantillaEnvio.getIdplantillaenvios())
					 * ; modPlantillaEnvio.setFechamodificacion(new Date());
					 * modPlantillaEnvio.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
					 * modModeloPlantillaenvioMapper.insert(modPlantillaEnvio);
					 * 
					 * //Tabla mod_plantillaenvio_consulta ModPlantillaenvioConsultaExample
					 * exampleEnvioConsulta = new ModPlantillaenvioConsultaExample();
					 * exampleEnvioConsulta.createCriteria().andIdinstitucionEqualTo(
					 * modPlantillaEnvio.getIdinstitucion()).andIdplantillaenviosEqualTo(
					 * idPlantillaEnvioDuplicar).andIdtipoenviosEqualTo(modPlantillaEnvio.
					 * getIdtipoenvios()).andFechabajaIsNull();
					 * 
					 * List<ModPlantillaenvioConsulta> listaModPlantillaEnvioConsulta =
					 * modPlantillaenvioConsultaMapper.selectByExample(exampleEnvioConsulta);
					 * 
					 * if(listaModPlantillaEnvioConsulta != null){ for(ModPlantillaenvioConsulta
					 * modPlantillaEnvioConsulta : listaModPlantillaEnvioConsulta){
					 * modPlantillaEnvioConsulta.setFechamodificacion(new Date());
					 * modPlantillaEnvioConsulta.setIdinstitucion(Short.parseShort(
					 * modeloComunicacion.getIdInstitucion()));
					 * modPlantillaEnvioConsulta.setIdplantillaenvios(plantillaEnvio.
					 * getIdplantillaenvios());
					 * modPlantillaenvioConsultaMapper.insert(modPlantillaEnvioConsulta); } } } } }
					 * }
					 */

					respuesta.setCode(200);
					respuesta.setDescription("Datos duplicados correctamente");
					respuesta.setMessage("Updates correcto");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("duplicarModeloComunicaciones() -> Salida del servicio para duplicar el modelo de comunicación");
		return idDuplicado;
	}

	@Override
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem[] modeloComunicacion) {
		LOGGER.info("borrarModeloComunicaciones() -> Entrada al servicio para borrar un módulo de comunicación");

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

				try {
					boolean para = true;

					ModPlantilladocConsultaExample modeloExample = null;
					List<Short> instituciones = new ArrayList<Short>();
					instituciones.add(idInstitucion);
					instituciones.add((short) 2000);

					List<ModPlantilladocConsulta> consultas = null;
					List<ModPlantilladocConsulta> plantillas = null;

					for (ModelosComunicacionItem it : modeloComunicacion) {

						modeloExample = new ModPlantilladocConsultaExample();
						modeloExample.createCriteria()
								.andIdmodelocomunicacionEqualTo(Long.valueOf(it.getIdModeloComunicacion()))
								.andIdinstitucionIn(instituciones);

						consultas = modPlantilladocConsultaMapper.selectByExample(modeloExample);

						for (ModPlantilladocConsulta consulta : consultas) {
							para = true;
							modeloExample = new ModPlantilladocConsultaExample();
							modeloExample.createCriteria().andIdconsultaEqualTo(Long.valueOf(consulta.getIdconsulta()))
									.andIdinstitucionIn(instituciones);
							plantillas = modPlantilladocConsultaMapper.selectByExample(modeloExample);
							for (ModPlantilladocConsulta x : plantillas) {
								if (!x.getIdmodelocomunicacion().toString().equals(it.getIdModeloComunicacion()))
									para = false;
							}

							if (para)
								break;
						}
						if (para)
							break;
					}
					if (!para) {
						for (ModelosComunicacionItem modeloCom : modeloComunicacion) {
							// Tabla mod_modelocomunicacion
							ModModelocomunicacion modelo = new ModModelocomunicacion();
							modelo.setIdmodelocomunicacion(Long.parseLong(modeloCom.getIdModeloComunicacion()));
							modelo.setFechabaja(new Date());
							modelo.setFechamodificacion(new Date());

							modModelocomunicacionMapper.updateByPrimaryKeySelective(modelo);
						}

						respuesta.setCode(200);
						respuesta.setDescription("Datos borrados correctamente");
						respuesta.setMessage("Updates correcto");
					} else {
						respuesta.setCode(500);
						respuesta.setDescription("ultimo");
					}

				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
			}
		}
		LOGGER.info("borrarModeloComunicaciones() -> Salida del servicio para borrar un modelo de comunicación");
		return respuesta;
	}

	@Override
	public Error rehabilitarModeloComunicaciones(HttpServletRequest request,
			ModelosComunicacionItem[] modeloComunicacion) {
		LOGGER.info(
				"rehabilitarModeloComunicaciones() -> Entrada al servicio para rehabilitar un módulo de comunicación");

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

					for (ModelosComunicacionItem modeloCom : modeloComunicacion) {
						if (modeloCom.getFechaBaja() != null) {
							// Tabla mod_modelocomunicacion
							ModModelocomunicacion modelo = modModelocomunicacionMapper
									.selectByPrimaryKey(Long.parseLong(modeloCom.getIdModeloComunicacion()));
							modelo.setIdmodelocomunicacion(Long.parseLong(modeloCom.getIdModeloComunicacion()));
							modelo.setFechabaja(null);
							modelo.setFechamodificacion(new Date());
							modelo.setUsumodificacion(usuario.getIdusuario());
							modModelocomunicacionMapper.updateByPrimaryKey(modelo);
						}
					}

					respuesta.setCode(200);
					respuesta.setDescription("Registro rehabilitado correctamente");
					respuesta.setMessage("Update correcto");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
			}
		}
		LOGGER.info(
				"rehabilitarModeloComunicaciones() -> Salida del servicio para rehabilitar un modelo de comunicación");
		return respuesta;
	}

	@Override
	public ComboDTO obtenerPerfilesModelo(HttpServletRequest request, String idInstitucion,
			String idModeloComunicacion) {
		LOGGER.info("obtenerPerfilesModelo() -> Entrada al servicio para obtener los perfiles asignados al modelo");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				try {

					if (idInstitucion != null && idInstitucion.equals(SigaConstants.IDINSTITUCION_0)) {
						idInstitucion = String.valueOf(SigaConstants.IDINSTITUCION_2000);
					}

					comboItems = modModeloPerfilesExtendsMapper.selectPerfilesModelo(Short.parseShort(idInstitucion),
							Long.parseLong(idModeloComunicacion));
					comboDTO.setCombooItems(comboItems);
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("obtenerPerfilesModelo() -> Salida del servicio para obtener los perfiles asignados al modelo");
		return comboDTO;
	}

	@Override
	public PlantillasDocumentosDTO obtenerInformes(HttpServletRequest request, String idInstitucion,
			String idModeloComunicacion) {
		LOGGER.info("obtenerInformes() -> Entrada al servicio para obtener los informes de un modelo de comunicación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		PlantillasDocumentosDTO respuesta = new PlantillasDocumentosDTO();
		List<PlantillaModeloDocumentoDTO> informesItem = new ArrayList<PlantillaModeloDocumentoDTO>();

		if (null != idInstitucionUser) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				try {
					AdmUsuarios usuario = usuarios.get(0);

					if (idInstitucion != null && idInstitucion.equals(SigaConstants.IDINSTITUCION_0)) {
						idInstitucion = String.valueOf(SigaConstants.IDINSTITUCION_2000);
					}

					informesItem = modModeloPlantillaDocumentoExtendsMapper.selectInformes(
							Short.parseShort(idInstitucion), Long.parseLong(idModeloComunicacion),
							usuario.getIdlenguaje());
					if (informesItem != null && informesItem.size() > 0) {
						for (PlantillaModeloDocumentoDTO informeItem : informesItem) {
							String idPlantillaDocumento = "0";
							if (informeItem.getIdPlantillas() != null) {
								String[] idsPlantillas = informeItem.getIdPlantillas().split(",");
								if (idsPlantillas[0] != null) {
									// Todas las plantillas tienen asociadas las mismas consultas, con coger una nos
									// vale
									idPlantillaDocumento = idsPlantillas[0];
								}
							}

							// Recuperamos Sufijo
							List<SufijoItem> sufijos = modRelPlantillaSufijoExtendsMapper.selectSufijosPlantilla(
									Long.parseLong(idModeloComunicacion), Long.parseLong(informeItem.getIdInforme()),
									usuario.getIdlenguaje());
							informeItem.setSufijos(sufijos);

							// Recuepramos nombre consultas
							List<ConsultaItem> consultasItem = modPlantillaDocumentoConsultaExtendsMapper
									.selectConsultaPorObjetivo(Short.parseShort(idInstitucion),
											Long.parseLong(idModeloComunicacion), idPlantillaDocumento,
											SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());

							String destinatarios = "";
							if (consultasItem != null && consultasItem.size() > 0 && consultasItem.get(0) != null) {
								destinatarios = consultasItem.get(0).getDescripcion();
							}

							consultasItem = modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(
									Short.parseShort(idInstitucion), Long.parseLong(idModeloComunicacion),
									idPlantillaDocumento, SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());

							String multi = "";
							if (consultasItem != null && consultasItem.size() > 0 && consultasItem.get(0) != null) {
								multi = consultasItem.get(0).getDescripcion();
							}

							consultasItem = modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(
									Short.parseShort(idInstitucion), Long.parseLong(idModeloComunicacion),
									idPlantillaDocumento, SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());

							String condicional = "";
							if (consultasItem != null && consultasItem.size() > 0 && consultasItem.get(0) != null) {
								condicional = consultasItem.get(0).getDescripcion();
							}

							int numDatos = modPlantillaDocumentoConsultaExtendsMapper.selectCountConsultaPorObjetivo(
									Short.parseShort(idInstitucion), Long.parseLong(idModeloComunicacion),
									Long.parseLong(idPlantillaDocumento), SigaConstants.OBJETIVO.DATOS.getCodigo());

							informeItem.setDestinatarios(destinatarios);
							informeItem.setMultiDocumento(multi);
							informeItem.setCondicion(condicional);
							informeItem.setDatos(numDatos);

						}

						respuesta.setPlantillasModeloDocumentos(informesItem);
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

		LOGGER.info("obtenerInformes() -> Salida del servicio para obtener los informes de un modelo de comunicación");
		return respuesta;
	}

	@Override
	public ResponseDataDTO guardarDatosGenerales(HttpServletRequest request,
			TarjetaModeloConfiguracionDTO datosTarjeta) {
		LOGGER.info(
				"guardarDatosGenerales() -> Entrada al servicio para guardar los datos generales del modelo de comunicación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Error error = new Error();
		ResponseDataDTO respuesta = new ResponseDataDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			ModModelocomunicacion modeloCom = new ModModelocomunicacion();

			try {
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					if (datosTarjeta.getIdModeloComunicacion() != null) {
						modeloCom = modModelocomunicacionMapper
								.selectByPrimaryKey(Long.parseLong(datosTarjeta.getIdModeloComunicacion()));
						modeloCom.setDescripcion(datosTarjeta.getDescripcion());
						modeloCom.setFechabaja(null);
						modeloCom.setFechamodificacion(new Date());
						modeloCom.setIdclasecomunicacion(Short.parseShort(datosTarjeta.getIdClaseComunicacion()));
						if (datosTarjeta.getIdInstitucion().equals(SigaConstants.IDINSTITUCION_0)) {
							modeloCom.setIdinstitucion(SigaConstants.IDINSTITUCION_2000);
							modeloCom.setPordefecto(SigaConstants.SI);
						} else {
							modeloCom.setIdinstitucion(Short.parseShort(datosTarjeta.getIdInstitucion()));
							modeloCom.setPordefecto(SigaConstants.NO);
						}
						modeloCom.setNombre(datosTarjeta.getNombre());
						if (datosTarjeta.getOrden() != null)
							modeloCom.setOrden(Short.parseShort(datosTarjeta.getOrden()));
						modeloCom.setPreseleccionar(datosTarjeta.getPreseleccionar());
						modeloCom.setUsumodificacion(usuario.getIdusuario());
						modeloCom.setVisible(Short.parseShort(datosTarjeta.getVisible()));

						if (datosTarjeta.getIdPlantillaEnvio() != null) {
							modeloCom.setIdplantillaenvios(Long.parseLong(datosTarjeta.getIdPlantillaEnvio()));
						}
						if (datosTarjeta.getIdTipoEnvio() != null) {
							modeloCom.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvio()));
						}
						modeloCom.setInformeunico(Short.parseShort(datosTarjeta.getInformeUnico()));

						modModelocomunicacionMapper.updateByPrimaryKey(modeloCom);
					} else {
						modeloCom = new ModModelocomunicacion();
						modeloCom.setDescripcion(datosTarjeta.getDescripcion());
						modeloCom.setFechabaja(null);
						modeloCom.setFechamodificacion(new Date());
						modeloCom.setIdclasecomunicacion(Short.parseShort(datosTarjeta.getIdClaseComunicacion()));

						if (datosTarjeta.getIdInstitucion().equals(SigaConstants.IDINSTITUCION_0)) {
							modeloCom.setIdinstitucion(SigaConstants.IDINSTITUCION_2000);
							modeloCom.setPordefecto(SigaConstants.SI);
						} else {
							modeloCom.setIdinstitucion(Short.parseShort(datosTarjeta.getIdInstitucion()));
							modeloCom.setPordefecto(SigaConstants.NO);
						}

						modeloCom.setNombre(datosTarjeta.getNombre());
						modeloCom.setOrden(Short.parseShort(datosTarjeta.getOrden()));
						modeloCom.setPreseleccionar(datosTarjeta.getPreseleccionar());
						modeloCom.setUsumodificacion(usuario.getIdusuario());
						modeloCom.setVisible(Short.parseShort(datosTarjeta.getVisible()));

						if (datosTarjeta.getIdPlantillaEnvio() != null) {
							modeloCom.setIdplantillaenvios(Long.parseLong(datosTarjeta.getIdPlantillaEnvio()));
						}
						if (datosTarjeta.getIdTipoEnvio() != null) {
							modeloCom.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvio()));
						}
						
						Short informeUnico = datosTarjeta.getInformeUnico() != null ? Short.parseShort(datosTarjeta.getInformeUnico()) : 0;
						modeloCom.setInformeunico(informeUnico);
						modModelocomunicacionMapper.insert(modeloCom);

						// Si es nuevo por defecto a de tener todos los perfiles
						List<ComboItem> comboItems = admPerfilExtendsMapper.selectListadoPerfiles(idInstitucion);
						if (comboItems != null && comboItems.size() > 0) {
							for (ComboItem item : comboItems) {
								ModModeloPerfiles perfil = new ModModeloPerfiles();
								perfil.setIdmodelocomunicacion(Long.valueOf(modeloCom.getIdmodelocomunicacion()));
								perfil.setFechamodificacion(new Date());
								perfil.setIdperfil(item.getValue());
								perfil.setUsumodificacion(usuario.getIdusuario());
								perfil.setIdinstitucion(idInstitucion);
								modModeloPerfilesMapper.insert(perfil);
							}
						}

					}

					// A la plantilla de envio guardada en el modelo se le asigna la clase de
					// comunicacion del modelo asignado
					if (modeloCom.getIdplantillaenvios() != null) {

						EnvPlantillasenviosExample envPlantillasenviosExample = new EnvPlantillasenviosExample();
						envPlantillasenviosExample.createCriteria()
								.andIdplantillaenviosEqualTo(modeloCom.getIdplantillaenvios().intValue());

						LOGGER.info(
								"guardarDatosGenerales() / envPlantillaEnviosExtendsMapper.selectByExample() -> Entrada a envPlantillaEnviosExtendsMapper para obtener la plantilla de envio asociada al modelo de comunicacion");

						List<EnvPlantillasenvios> plantillas = envPlantillaEnviosExtendsMapper
								.selectByExample(envPlantillasenviosExample);

						LOGGER.info(
								"guardarDatosGenerales() / envPlantillaEnviosExtendsMapper.selectByExample() -> Salida de envPlantillaEnviosExtendsMapper para obtener la plantilla de envio asociada al modelo de comunicacion");

						if (null != plantillas && plantillas.size() > 0) {
							EnvPlantillasenvios plantilla = plantillas.get(0);

							plantilla.setFechamodificacion(new Date());
							plantilla.setUsumodificacion(usuario.getIdusuario());
							plantilla.setIdclasecomunicacion((long) modeloCom.getIdclasecomunicacion());

							LOGGER.info(
									"guardarDatosGenerales() / envPlantillaEnviosExtendsMapper.updateByPrimaryKey() -> Entrada a envPlantillaEnviosExtendsMapper para modificar la plantilla de envio asociada al modelo de comunicacion");

							envPlantillaEnviosExtendsMapper.updateByPrimaryKey(plantilla);

							LOGGER.info(
									"guardarDatosGenerales() / envPlantillaEnviosExtendsMapper.updateByPrimaryKey() -> Salida de envPlantillaEnviosExtendsMapper para modificar la plantilla de envio asociada al modelo de comunicacion");

						}

					}

					respuesta.setData(String.valueOf(modeloCom.getIdmodelocomunicacion()));

				}
			} catch (Exception e) {
				error.setCode(500);
				error.setDescription("Error al guardar datos generales");
				error.setMessage(e.getMessage());
				respuesta.setError(error);
			}

		}
		LOGGER.info(
				"guardarDatosGenerales() -> Salida del servicio para guardar los datos generales del modelo de comunicación");
		return respuesta;
	}

	@Override
	public Error guardarPerfilesModelo(HttpServletRequest request, TarjetaPerfilesDTO perfilesDTO) {

		LOGGER.info("guardarPerfilesModelo() -> Entrada al servicio para guardar datos perfiles");

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

					// borramos las etiquetas no seleccionadas
					for (int i = 0; i < perfilesDTO.getPerfilesNoSeleccionados().length; i++) {
						ModModeloPerfilesExample example = new ModModeloPerfilesExample();
						example.createCriteria()
								.andIdmodelocomunicacionEqualTo(Long.valueOf(perfilesDTO.getIdModeloComunicacion()))
								.andIdperfilEqualTo(perfilesDTO.getPerfilesNoSeleccionados()[i]);
						modModeloPerfilesMapper.deleteByExample(example);
					}

					// añadimos las etiquetas seleccionadas
					for (int i = 0; i < perfilesDTO.getPerfilesSeleccionados().length; i++) {
						ModModeloPerfilesKey key = new ModModeloPerfilesKey();
						key.setIdinstitucion(idInstitucion);
						key.setIdmodelocomunicacion(Long.valueOf(perfilesDTO.getIdModeloComunicacion()));
						key.setIdperfil(perfilesDTO.getPerfilesSeleccionados()[i]);
						ModModeloPerfiles perfil = modModeloPerfilesMapper.selectByPrimaryKey(key);
						if (perfil == null) {
							perfil = new ModModeloPerfiles();
							perfil.setIdmodelocomunicacion(Long.valueOf(perfilesDTO.getIdModeloComunicacion()));
							perfil.setFechamodificacion(new Date());
							perfil.setIdperfil(perfilesDTO.getPerfilesSeleccionados()[i]);
							perfil.setUsumodificacion(usuario.getIdusuario());
							perfil.setIdinstitucion(idInstitucion);
							modModeloPerfilesMapper.insert(perfil);
						}
					}
					respuesta.setCode(200);
					respuesta.setDescription("Datos perfiles del modelo guardados correctamente");
					respuesta.setMessage("Updates correcto");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}

			}
		}
		LOGGER.info("guardarPerfilesModelo() -> Salida del servicio para guardar datos perfiles");
		return respuesta;
	}

	@Override
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearch(HttpServletRequest request, String idModelo) {
		LOGGER.info("obtenerPlantillasModelo() -> Entrada al servicio para obtener plantillas del modelo");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		PlantillasModeloDTO respuesta = new PlantillasModeloDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					List<PlantillaModeloItem> plantillas = _modModeloPlantillaEnvioExtendsMapper
							.getPlantillasModelo(idModelo, idInstitucion, usuario.getIdlenguaje());
					if (plantillas != null && plantillas.size() > 0) {
						respuesta.setPlantillas(plantillas);
					}

				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					respuesta.setError(error);
				}
			}
		}

		LOGGER.info("obtenerPlantillasModelo() -> Salida del servicio para obtener plantillas del modelo");
		return respuesta;
	}

	@Override
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearchHist(HttpServletRequest request, String idModelo) {
		LOGGER.info("obtenerPlantillasModeloHist() -> Entrada al servicio para obtener plantillas del modelo HIST");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		PlantillasModeloDTO respuesta = new PlantillasModeloDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					List<PlantillaModeloItem> plantillas = _modModeloPlantillaEnvioExtendsMapper
							.getPlantillasModeloHist(idModelo, idInstitucion, usuario.getIdlenguaje());
					if (plantillas != null && plantillas.size() > 0) {
						respuesta.setPlantillas(plantillas);
					}

				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					respuesta.setError(error);
				}
			}
		}

		LOGGER.info("obtenerPlantillasModeloHist() -> Salida del servicio para obtener plantillas del modelo HIST");
		return respuesta;
	}

	@Override
	public Error borrarPlantillaEnviosModelo(HttpServletRequest request, PlantillaModeloBorrarDTO[] plantillas) {
		LOGGER.info("borrarPlantillaModelo() -> Entrada al servicio para obtener plantillas del modelo");

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
				try {
					for (int i = 0; i < plantillas.length; i++) {
						ModModeloPlantillaenvioKey key = new ModModeloPlantillaenvioKey();
						key.setIdmodelocomunicacion(Long.valueOf(plantillas[i].getIdModeloComunicacion()));
						key.setIdplantillaenvios(Integer.parseInt(plantillas[i].getIdPlantillaEnvios()));
						key.setIdinstitucion(Short.valueOf(plantillas[i].getIdInstitucion()));
						key.setIdtipoenvios(Short.valueOf(plantillas[i].getIdTipoEnvios()));
						ModModeloPlantillaenvio plantilla = modModeloPlantillaenvioMapper.selectByPrimaryKey(key);
						plantilla.setFechabaja(new Date());
						plantilla.setFechamodificacion(new Date());
						plantilla.setUsumodificacion(usuario.getIdusuario());
						modModeloPlantillaenvioMapper.updateByPrimaryKey(plantilla);
					}
					respuesta.setCode(200);
					respuesta.setMessage("Plantillas borradas");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al borrar plantillas");
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("borrarPlantillaModelo() -> Salida del servicio para obtener plantillas del modelo");
		return respuesta;
	}

	@Override
	public Error guardarPlantillaEnviosModelo(HttpServletRequest request,
			List<PlantillaModeloBorrarDTO> listDatosPlantilla) {
		LOGGER.info("guardarPlantillaModelo() -> Entrada al servicio para guardar la plantilla del modelo");

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
				try {

					for (PlantillaModeloBorrarDTO datosPlantilla : listDatosPlantilla) {

						if (datosPlantilla.getPorDefecto().equals("Si")
								|| datosPlantilla.getPorDefecto().equals("SI")) {
							ModModeloPlantillaenvioExample example = new ModModeloPlantillaenvioExample();
							example.createCriteria().andIdmodelocomunicacionEqualTo(
									Long.valueOf(datosPlantilla.getIdModeloComunicacion()));
							List<ModModeloPlantillaenvio> plantillas = modModeloPlantillaenvioMapper
									.selectByExample(example);
							for (int i = 0; i < plantillas.size(); i++) {
								plantillas.get(i).setPordefecto("No");
								plantillas.get(i).setFechamodificacion(new Date());
								plantillas.get(i).setUsumodificacion(usuario.getIdusuario());
								modModeloPlantillaenvioMapper.updateByPrimaryKey(plantillas.get(i));
							}
						}
						ModModeloPlantillaenvioKey key = new ModModeloPlantillaenvioKey();
						if (datosPlantilla.getIdInstitucion() == null) {
							key.setIdinstitucion(idInstitucion);
						} else {
							key.setIdinstitucion(Short.valueOf(datosPlantilla.getIdInstitucion()));
						}
						key.setIdmodelocomunicacion(Long.valueOf(datosPlantilla.getIdModeloComunicacion()));
						key.setIdplantillaenvios(Integer.parseInt(datosPlantilla.getIdPlantillaEnvios()));
						key.setIdtipoenvios(Short.valueOf(datosPlantilla.getIdTipoEnvios()));
						ModModeloPlantillaenvio plantilla = modModeloPlantillaenvioMapper.selectByPrimaryKey(key);
						if (plantilla != null) {
							plantilla.setFechabaja(null);
							plantilla.setUsumodificacion(usuario.getIdusuario());
							plantilla.setFechamodificacion(new Date());
							plantilla.setPordefecto(datosPlantilla.getPorDefecto());
							modModeloPlantillaenvioMapper.updateByPrimaryKey(plantilla);
						} else {
							plantilla = new ModModeloPlantillaenvio();
							plantilla.setIdmodelocomunicacion(Long.valueOf(datosPlantilla.getIdModeloComunicacion()));
							plantilla.setIdplantillaenvios(Integer.parseInt(datosPlantilla.getIdPlantillaEnvios()));
							plantilla.setIdinstitucion(idInstitucion);
							plantilla.setIdtipoenvios(Short.valueOf(datosPlantilla.getIdTipoEnvios()));
							plantilla.setPordefecto(datosPlantilla.getPorDefecto());
							plantilla.setUsumodificacion(usuario.getIdusuario());
							plantilla.setFechamodificacion(new Date());
							modModeloPlantillaenvioMapper.insert(plantilla);
						}

						// Si llega los ids antiguos es una edición del registro de la tabla por lo
						// tanto borramos la antigua.
						if (datosPlantilla.getIdAntiguaPlantillaEnvios() != null && !datosPlantilla
								.getIdAntiguaPlantillaEnvios().equals(datosPlantilla.getIdPlantillaEnvios())) {
							key = new ModModeloPlantillaenvioKey();
							key.setIdinstitucion(Short.valueOf(datosPlantilla.getIdInstitucion()));
							key.setIdmodelocomunicacion(Long.valueOf(datosPlantilla.getIdModeloComunicacion()));
							key.setIdplantillaenvios(Integer.parseInt(datosPlantilla.getIdAntiguaPlantillaEnvios()));
							key.setIdtipoenvios(Short.valueOf(datosPlantilla.getIdAntiguaTipoEnvios()));
							plantilla = modModeloPlantillaenvioMapper.selectByPrimaryKey(key);
							plantilla.setFechabaja(new Date());
							plantilla.setUsumodificacion(usuario.getIdusuario());
							plantilla.setFechamodificacion(new Date());
							modModeloPlantillaenvioMapper.updateByPrimaryKey(plantilla);
						}

						respuesta.setCode(200);
						respuesta.setDescription("Plantilla guardada");
					}
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al guardar plantilla");
				}
			}
		}

		LOGGER.info("guardarPlantillaModelo() -> Salida del servicio para guardar la plantilla del modelo");
		return respuesta;
	}

	@Override
	public Error borrarInformes(HttpServletRequest request, PlantillaDocumentoBorrarDTO[] plantillasDoc) {
		LOGGER.info("borrarInformes() -> Entrada al servicio para guardar la plantilla del modelo");

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
				try {
					if (plantillasDoc != null & plantillasDoc.length > 0) {
						for (PlantillaDocumentoBorrarDTO plantilla : plantillasDoc) {
							ModModeloPlantilladocumentoExample example = new ModModeloPlantilladocumentoExample();
							example.createCriteria().andIdinformeEqualTo(Long.parseLong(plantilla.getIdInforme()))
									.andIdmodelocomunicacionEqualTo(Long.valueOf(plantilla.getIdModeloComunicacion()));
							List<ModModeloPlantilladocumento> listaPlantillas = modModeloPlantilladocumentoMapper
									.selectByExample(example);

							boolean para = false;
							ModPlantilladocConsultaExample modeloExample = null;

							List<Short> instituciones = new ArrayList<Short>();
							instituciones.add(idInstitucion);
							instituciones.add((short) 2000);

							List<ModPlantilladocConsulta> consultas = null;
							List<ModPlantilladocConsulta> plantillas = null;

							for (ModModeloPlantilladocumento plantillaBorrar : listaPlantillas) {

								modeloExample = new ModPlantilladocConsultaExample();
								modeloExample.createCriteria()
										.andIdmodelocomunicacionEqualTo(
												Long.valueOf(plantilla.getIdModeloComunicacion()))
										.andIdinstitucionIn(instituciones);

								consultas = modPlantilladocConsultaMapper.selectByExample(modeloExample);

								for (ModPlantilladocConsulta consulta : consultas) {
									para = false;
									modeloExample = new ModPlantilladocConsultaExample();
									modeloExample.createCriteria()
											.andIdconsultaEqualTo(Long.valueOf(consulta.getIdconsulta()))
											.andIdinstitucionIn(instituciones);
									plantillas = modPlantilladocConsultaMapper.selectByExample(modeloExample);
									for (ModPlantilladocConsulta x : plantillas) {
										if (!x.getIdplantilladocumento()
												.equals(plantillaBorrar.getIdplantilladocumento()))
											para = true;
									}
									if (!para)
										break;
								}
								if (para) {

									plantillaBorrar.setFechabaja(new Date());
									plantillaBorrar.setFechamodificacion(new Date());
									plantillaBorrar.setUsumodificacion(usuario.getIdusuario());
									modModeloPlantilladocumentoMapper.updateByPrimaryKey(plantillaBorrar);

									// También ponemos fecha de baja a las consultas asociadas a esa plantilla
									ModPlantilladocConsultaExample examplePlantilla = new ModPlantilladocConsultaExample();
									examplePlantilla.createCriteria()
											.andIdmodelocomunicacionEqualTo(
													Long.parseLong(plantilla.getIdModeloComunicacion()))
											.andIdplantilladocumentoEqualTo(plantillaBorrar.getIdplantilladocumento())
											.andIdinstitucionEqualTo(Short.parseShort(plantilla.getIdInstitucion()));
									List<ModPlantilladocConsulta> listaConsultas = modPlantilladocConsultaMapper
											.selectByExample(examplePlantilla);
									for (ModPlantilladocConsulta consultaBorrar : listaConsultas) {
										consultaBorrar.setFechabaja(new Date());
										modPlantilladocConsultaMapper.updateByPrimaryKey(consultaBorrar);
									}

									respuesta.setCode(200);
									respuesta.setDescription("Informe borrado correctamente");
								} else {

									respuesta.setCode(500);
									respuesta.setDescription("ultimo");
								}

							}
						}
					}
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al borrar informe");
				}
			}
		}

		return respuesta;

	}

	@Override
	public ComboDTO colegiosModelo(HttpServletRequest request) {

		LOGGER.info("colegiosModelo() -> Entrada al servicio para obtener los colegios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				try {
					comboItems = _cenInstitucionExtendsMapper.comboColegiosModelo(idInstitucionUser);
					comboDTO.setCombooItems(comboItems);
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("colegiosModelo() -> Salida del servicio para obtener los colegios");
		return comboDTO;
	}

	@Override
	public ComboDTO obtenerPlantillasComunicacion(String idClaseComunicacion, HttpServletRequest request) {

		LOGGER.info(
				"obtenerPlantillasComunicacion() -> Entrada al servicio para obtener las plantillas para añadir a la comunicación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				try {
					comboItems = envPlantillaEnviosExtendsMapper.getPlantillasComunicacion(idInstitucionUser,
							idClaseComunicacion);
					comboDTO.setCombooItems(comboItems);
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
				"obtenerPlantillasComunicacion() -> Salida del servicio para obtener las plantillas para añadir a la comunicación");
		return comboDTO;
	}

	@Override
	public PlantillaEnvioItem obtenerTipoEnvioPlantilla(HttpServletRequest request, String idPlantilla) {
		LOGGER.info(
				"obtenerPlantillasComunicacion() -> Entrada al servicio para obtener el tipo de envio de la plantilla");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);
		PlantillaEnvioItem plantilla = new PlantillaEnvioItem();

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					List<PlantillaEnvioItem> plantillas = envPlantillaEnviosExtendsMapper
							.getTipoEnvioPlantilla(idInstitucionUser, idPlantilla, usuario.getIdlenguaje());
					if (plantillas != null && plantillas.size() > 0) {
						plantilla = plantillas.get(0);
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
				"obtenerPlantillasComunicacion() -> Salida del servicio para obtener las plantillas para añadir a la comunicación");
		return plantilla;
	}

	@Override
	public ModelosComunicacionItem modeloYComunicacionesSearchModelo(HttpServletRequest request, String idModelo) {

		LOGGER.info(
				"modeloYComunicacionesSearchModelo() -> Entrada al servicio para busqueda de modelos de comunicación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);
		ModelosComunicacionItem modeloComunicacionItem = null;

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				modeloComunicacionItem = new ModelosComunicacionItem();
				try {
					modeloComunicacionItem = modModeloComunicacionExtendsMapper.selectModelo(idModelo,
							usuario.getIdlenguaje());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		LOGGER.info(
				"modeloYComunicacionesSearchModelo() -> Salida del servicio para busqueda de modelos de comunicación");
		return modeloComunicacionItem;

	}

	@Override
	public Boolean comprobarNombreModeloComunicacion(HttpServletRequest request,
			TarjetaModeloConfiguracionDTO datosTarjeta) {
		LOGGER.info(
				"comprobarNombreModeloComunicacion() -> Entrada al servicio para comprobar si existe un modelo con el nombre introducido");

		Boolean respuesta = Boolean.FALSE;
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni)
					.andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				ModModelocomunicacionExample example = new ModModelocomunicacionExample();
				example.createCriteria().andNombreEqualTo(datosTarjeta.getNombre());
				List<ModModelocomunicacion> listModelos = modModelocomunicacionMapper.selectByExample(example);

				if (listModelos != null && listModelos.size() > 0)
					respuesta = Boolean.TRUE;

			}
		}

		LOGGER.info(
				"comprobarNombreModeloComunicacion() -> Salida al servicio para comprobar si existe un modelo con el nombre introducido");
		return respuesta;
	}

	private String comprobarNombreDuplicado(String nombreModelo) {
		StringDTO nombreFinal = new StringDTO();

		if (nombreModelo.contains(SigaConstants.SUFIJO_MODULO_COM_DUPLICADO)) {
			nombreModelo = nombreModelo.split(SigaConstants.SUFIJO_MODULO_COM_DUPLICADO)[0];
		}

		// Si no devuelve nada, se llamara *_COPIA
		// En caso de que devuelva algo, devolverá el numero que deberá de estar despues
		// de copia --> *_COPIA1
		StringDTO nombre = modModeloComunicacionExtendsMapper
				.comprobarNombreDuplicado(nombreModelo + SigaConstants.SUFIJO_MODULO_COM_DUPLICADO);

		if (nombre == null || nombre.getValor().equals(""))
			nombreFinal.setValor(nombreModelo + SigaConstants.SUFIJO_MODULO_COM_DUPLICADO);
		else
			nombreFinal.setValor(nombreModelo + SigaConstants.SUFIJO_MODULO_COM_DUPLICADO + nombre.getValor());

		return nombreFinal.getValor();

	}

	@Override
	public PlantillasModeloDTO obtenerPlantillaPorDefecto(HttpServletRequest request, String idModelo) {
		LOGGER.info(
				"obtenerPlantillaPorDefecto() -> Entrada al servicio para obtener la plantilla por defecto del modelo");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		PlantillasModeloDTO respuesta = new PlantillasModeloDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					List<PlantillaModeloItem> plantillas = modModeloComunicacionExtendsMapper
							.selectPlantillaPorDefecto(idModelo, idInstitucion, usuario.getIdlenguaje());
					if (plantillas != null && plantillas.size() > 0) {
						respuesta.setPlantillas(plantillas);
					}

				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					respuesta.setError(error);
				}
			}
		}

		LOGGER.info(
				"obtenerPlantillaPorDefecto() -> Salida del servicio para obtener la plantilla por defecto del modelo");
		return respuesta;
	}
}
