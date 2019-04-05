package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.com.ComboConsultaInstitucionDTO;
import org.itcgae.siga.DTOs.com.ConsultaDestinatarioItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DestinatarioIndvEnvioMasivoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.DestinatariosDTO;
import org.itcgae.siga.DTOs.com.DocumentoEnvioItem;
import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaEtiquetasDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IColaEnvios;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDestConsultaEnvio;
import org.itcgae.siga.db.entities.EnvDestConsultaEnvioKey;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDestinatariosExample;
import org.itcgae.siga.db.entities.EnvDestinatariosKey;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvDocumentosExample;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoKey;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.EnvEnviosgrupocliente;
import org.itcgae.siga.db.entities.EnvEnviosgrupoclienteExample;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvioExample;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDestConsultaEnvioMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosgrupoclienteMapper;
import org.itcgae.siga.db.mappers.EnvHistoricoestadoenvioMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestConsultaEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestinatariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDocumentosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosGrupoClienteExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEstadoEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvTipoEnvioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Transactional
public class EnviosMasivosServiceImpl implements IEnviosMasivosService {

	private Logger LOGGER = Logger.getLogger(EnviosMasivosServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private EnvTipoEnvioExtendsMapper _envTipoEnvioExtendsMapper;

	@Autowired
	private EnvEstadoEnvioExtendsMapper _envEstadoEnvioExtendsMapper;

	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;

	@Autowired
	private EnvEnviosMapper _envEnviosMapper;

	@Autowired
	private EnvEnvioprogramadoMapper _envEnvioprogramadoMapper;

	@Autowired
	private EnvHistoricoestadoenvioMapper _envHistoricoestadoenvioMapper;

	@Autowired
	private EnvPlantillaEnviosExtendsMapper _envPlantillaEnviosExtendsMapper;

	@Autowired
	private EnvPlantillasenviosMapper _envPlantillasenviosMapper;

	@Autowired
	private EnvDocumentosExtendsMapper _envDocumentosExtendsMapper;

	@Autowired
	private EnvDocumentosMapper _envDocumentosMapper;

	@Autowired
	private EnvEnviosgrupoclienteMapper _envEnviosgrupoclienteMapper;

	@Autowired
	private CenGruposclienteClienteExtendsMapper _cenGruposclienteClienteExtendsMapper;

	@Autowired
	private EnvEnviosGrupoClienteExtendsMapper _envEnviosGrupoClienteExtendsMapper;

	@Autowired
	private EnvConsultasenvioMapper _envConsultasenvioMapper;

	@Autowired
	private IColaEnvios _colaEnvios;

	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;

	@Autowired
	private EnvDestConsultaEnvioExtendsMapper _envDestConsultaEnvioExtendsMapper;

	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;

	@Autowired
	private EnvDestConsultaEnvioMapper _envDestConsultaEnvioMapper;

	@Autowired
	private EnvDestinatariosExtendsMapper _envDestinatariosExtendsMapper;

	@Autowired
	private EnvDestinatariosMapper _envDestinatariosMapper;

	@Autowired
	private CenPersonaMapper _cenPersonaMapper;

	@Autowired
	private CenDireccionesMapper _cenDireccionesMapper;

	@Override
	public ComboDTO estadoEnvios(HttpServletRequest request) {

		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");

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

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _envEstadoEnvioExtendsMapper.selectEstadoEnvios(usuario.getIdlenguaje());

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");

		return comboDTO;
	}

	@Override
	public ComboDTO tipoEnvio(HttpServletRequest request) {

		LOGGER.info("tipoEnvio() -> Entrada al servicio para obtener combo tipos de envio");

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

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _envTipoEnvioExtendsMapper.selectTipoEnvios(usuario.getIdlenguaje());

				// Se añade el placeholder "Seleccionar" al combo en el front
				// if(null != comboItems && comboItems.size() > 0) {
				// ComboItem element = new ComboItem();
				// element.setLabel("");
				// element.setValue("");
				// comboItems.add(0, element);
				// }

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo tipos de envio");

		return comboDTO;
	}

	@Override
	public EnviosMasivosDTO enviosMasivosSearch(HttpServletRequest request, EnviosMasivosSearch filtros) {
		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");

		EnviosMasivosDTO enviosMasivos = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosItemList = new ArrayList<EnviosMasivosItem>();

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
					enviosItemList = _envEnviosExtendsMapper.selectEnviosMasivosSearch(usuario.getIdinstitucion(),
							usuario.getIdlenguaje(), filtros);
					if (enviosItemList.size() > 0) {
						enviosMasivos.setEnviosMasivosItem(enviosItemList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					enviosMasivos.setError(error);
				}

			}
		}

		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");
		return enviosMasivos;
	}

	@Override
	public Error programarEnvio(HttpServletRequest request, EnviosMasivosItem[] enviosProgramadosDto) {

		LOGGER.info("programarEnvio() -> Entrada al servicio para programar los envios");

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

					for (int i = 0; i < enviosProgramadosDto.length; i++) {
						// Solo programamos los envios si tiene estado 1(nuevo) o 4(programado)
						if (enviosProgramadosDto[i].getIdEstado() == 1 || enviosProgramadosDto[i].getIdEstado() == 4) {
							int updateInsert = 0;
							EnvEnvioprogramadoKey key = new EnvEnvioprogramadoKey();
							key.setIdenvio(enviosProgramadosDto[i].getIdEnvio());
							key.setIdinstitucion(idInstitucion);
							EnvEnvioprogramado envioProgramado = _envEnvioprogramadoMapper.selectByPrimaryKey(key);
							if (envioProgramado != null) {
								envioProgramado.setFechaprogramada(enviosProgramadosDto[i].getFechaProgramada());
								envioProgramado.setFechamodificacion(new Date());
								envioProgramado.setUsumodificacion(usuario.getIdusuario());
								updateInsert = _envEnvioprogramadoMapper.updateByPrimaryKey(envioProgramado);
							} else {
								envioProgramado = new EnvEnvioprogramado();
								envioProgramado.setIdenvio(enviosProgramadosDto[i].getIdEnvio());
								envioProgramado.setIdinstitucion(idInstitucion);
								envioProgramado.setEstado("0");
								if (enviosProgramadosDto[i].getIdPlantilla() != null) {
									envioProgramado
											.setIdplantilla(Short.valueOf(enviosProgramadosDto[i].getIdPlantilla()));
								}
								envioProgramado.setIdplantillaenvios(
										Integer.parseInt(enviosProgramadosDto[i].getIdPlantillaEnvios()));
								envioProgramado
										.setIdtipoenvios(Short.valueOf(enviosProgramadosDto[i].getIdTipoEnvios()));
								envioProgramado.setNombre(enviosProgramadosDto[i].getDescripcion());
								envioProgramado.setFechaprogramada(enviosProgramadosDto[i].getFechaProgramada());
								envioProgramado.setFechamodificacion(new Date());
								envioProgramado.setUsumodificacion(usuario.getIdusuario());
								updateInsert = _envEnvioprogramadoMapper.insert(envioProgramado);

							}
							if (updateInsert > 0) {
								EnvEnviosKey keyEnvio = new EnvEnviosKey();
								keyEnvio.setIdenvio(enviosProgramadosDto[i].getIdEnvio());
								keyEnvio.setIdinstitucion(usuario.getIdinstitucion());
								EnvEnvios envio = _envEnviosMapper.selectByPrimaryKey(keyEnvio);
								envio.setFechaprogramada(enviosProgramadosDto[i].getFechaProgramada());
								// estado 4 programado (pendiente automatico)
								Short idEstado = 4;
								envio.setIdestado(idEstado);
								envio.setFechamodificacion(new Date());
								envio.setUsumodificacion(usuario.getIdusuario());
								_envEnviosMapper.updateByPrimaryKey(envio);
							}
						}

					}
					respuesta.setCode(200);
					respuesta.setDescription("Envios Masivos programados correctamente");
					respuesta.setMessage("Updates correcto");

				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}

			}

		}
		LOGGER.info("programarEnvio() -> Salida del servicio para programar los envios");
		return respuesta;
	}

	@Override
	public Error cancelarEnvios(HttpServletRequest request, EnvioProgramadoDto[] envios) {
		LOGGER.info("cancelarEnvios() -> Entrada al servicio para cancelar envios");

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
					for (int i = 0; i < envios.length; i++) {
						// Solo cancelamos los envios si tiene estado 1(nuevo) o 4(programado)
						if (envios[i].getIdEstado().equals("1") || envios[i].getIdEstado().equals("4")) {
							int update = 0;
							EnvEnviosKey keyEnvio = new EnvEnviosKey();
							keyEnvio.setIdenvio(Long.parseLong(envios[i].getIdEnvio()));
							keyEnvio.setIdinstitucion(usuario.getIdinstitucion());
							EnvEnvios envio = _envEnviosMapper.selectByPrimaryKey(keyEnvio);
							envio.setFechabaja(new Date());
							// Le asignamos el estado 6(archivado)
							Short idEstado = 6;
							envio.setIdestado(idEstado);
							update = _envEnviosMapper.updateByPrimaryKey(envio);
							if (update > 0) {
								EnvHistoricoestadoenvioExample example = new EnvHistoricoestadoenvioExample();
								example.createCriteria().andIdenvioEqualTo(Long.parseLong(envios[i].getIdEnvio()))
										.andIdinstitucionEqualTo(usuario.getIdinstitucion());
								List<EnvHistoricoestadoenvio> historico = _envHistoricoestadoenvioMapper
										.selectByExample(example);
								historico.get(0).setFechamodificacion(new Date());
								historico.get(0).setFechaestado(new Date());
								historico.get(0).setUsumodificacion(usuario.getIdusuario());
								historico.get(0).setIdestado(idEstado);
								_envHistoricoestadoenvioMapper.updateByPrimaryKey(historico.get(0));
							}
						}
					}
					respuesta.setCode(200);
					respuesta.setDescription("Envios cancelados correctamente");
					respuesta.setMessage("Updates correcto");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}

			}
		}
		LOGGER.info("programarEnvio() -> Salida del servicio para cancelar envios");
		return respuesta;
	}

	@Override
	public Error enviar(HttpServletRequest request, EnvioProgramadoDto[] enviosDTO) {

		LOGGER.info("enviar() -> Entrada al servicio para enviar");

		Error respuesta = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EnvEnvios envio = null;
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					for (int i = 0; i < enviosDTO.length; i++) {
						EnvEnviosKey key = new EnvEnviosKey();
						key.setIdenvio(Long.valueOf(enviosDTO[i].getIdEnvio()));
						key.setIdinstitucion(usuario.getIdinstitucion());
						envio = _envEnviosMapper.selectByPrimaryKey(key);

						switch (envio.getIdtipoenvios().toString()) {

						case SigaConstants.ID_ENVIO_MAIL:
							_colaEnvios.preparaCorreo(envio);
							LOGGER.info("Correo electrónico enviado con éxito");
							break;
						case SigaConstants.ID_ENVIO_CORREO_ORDINARIO:
							_colaEnvios.preparaCorreo(envio);
							LOGGER.info("Correo ordinario generado con éxito");
							break;
						case SigaConstants.ID_ENVIO_SMS:
							_colaEnvios.preparaEnvioSMS(envio, false);
							LOGGER.info("SMS enviado con éxito");
							break;
						case SigaConstants.ID_ENVIO_BURO_SMS:
							_colaEnvios.preparaEnvioSMS(envio, true);
							LOGGER.info("BURO SMS enviado con éxito");
							break;
						default:
							break;
						}
					}
					respuesta.setCode(200);
					respuesta.setDescription("El envio se ha lanzado correctamente");
					respuesta.setMessage("Envio lanzado");
				} catch (Exception e) {
					envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
					envio.setFechamodificacion(new Date());
					_envEnviosMapper.updateByPrimaryKey(envio);
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
					e.printStackTrace();
				}

			}
		}
		LOGGER.info("enviar() -> Salida del servicio para enviar");
		return respuesta;
	}

	@Override
	public Error guardarConfiguracion(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta) {

		LOGGER.info("guardarConfiguracion() -> Entrada al servicio para guardar datos tarjeta configuración");

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

					if (datosTarjeta.getIdEnvio() == null) {
						EnvEnvios envio = new EnvEnvios();
						envio.setIdinstitucion(idInstitucion);
						envio.setDescripcion(datosTarjeta.getDescripcion());
						envio.setFecha(new Date());
						envio.setGenerardocumento("N");
						envio.setImprimiretiquetas("N");
						envio.setIdplantillaenvios(Integer.parseInt(datosTarjeta.getIdPlantillaEnvios()));
						Short estadoNuevo = 1;
						envio.setIdestado(estadoNuevo);
						envio.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvios()));
						envio.setFechamodificacion(new Date());
						envio.setUsumodificacion(usuario.getIdusuario());
						envio.setEnvio("M");
						int insert = _envEnviosMapper.insert(envio);
						if (insert > 0) {
							EnvHistoricoestadoenvio historico = new EnvHistoricoestadoenvio();
							historico.setIdenvio(envio.getIdenvio());
							historico.setIdinstitucion(usuario.getIdinstitucion());
							historico.setFechamodificacion(new Date());
							historico.setFechaestado(new Date());
							historico.setUsumodificacion(usuario.getIdusuario());
							Short idEstado = 1;
							historico.setIdestado(idEstado);
							_envHistoricoestadoenvioMapper.insert(historico);
						}
						respuesta.setCode(200);
						respuesta.setDescription(envio.getIdenvio().toString());
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
						respuesta.setMessage(dateFormat.format(envio.getFecha()));
					} else {
						EnvEnviosKey key = new EnvEnviosKey();
						key.setIdenvio(Long.valueOf(datosTarjeta.getIdEnvio()));
						key.setIdinstitucion(idInstitucion);
						EnvEnvios envio = _envEnviosMapper.selectByPrimaryKey(key);
						envio.setIdenvio(Long.parseLong(datosTarjeta.getIdEnvio()));
						envio.setDescripcion(datosTarjeta.getDescripcion());
						envio.setFechamodificacion(new Date());
						envio.setUsumodificacion(usuario.getIdusuario());
						_envEnviosMapper.updateByPrimaryKey(envio);
						respuesta.setCode(200);
						respuesta.setDescription(envio.getIdenvio().toString());
					}
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}

			}
		}
		LOGGER.info("guardarConfiguracion() -> Salida del servicio para guardar datos tarjeta configuración");
		return respuesta;
	}

	@Override
	public ComboDTO nombrePlantillas(HttpServletRequest request, String idtipoEnvio) {

		LOGGER.info("nombrePlantillas() -> Entrada al servicio para obtener plantillas");

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

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _envPlantillaEnviosExtendsMapper.getPlantillas(idInstitucion, idtipoEnvio);
				if (null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("nombrePlantillas() -> Salida del servicio para obtener plantillas");

		return comboDTO;
	}

	@Override
	@Transactional
	public EnviosMasivosDTO duplicarEnvio(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta) {

		LOGGER.info("duplicarEnvio() -> Entrada al servicio para duplicar envío");

		Error error = new Error();
		EnviosMasivosDTO respuesta = new EnviosMasivosDTO();
		EnviosMasivosItem envioMasivoItem = new EnviosMasivosItem();

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
					AdmUsuarios usuario = usuarios.get(0);

					// tabla env_enviosplantillas
					EnvPlantillasenviosKey PlantillaKey = new EnvPlantillasenviosKey();
					PlantillaKey.setIdinstitucion(idInstitucion);
					PlantillaKey.setIdplantillaenvios(Integer.parseInt(datosTarjeta.getIdPlantillaEnvios()));
					PlantillaKey.setIdtipoenvios(Short.valueOf(datosTarjeta.getIdTipoEnvios()));
					EnvPlantillasenviosWithBLOBs plantillaEnvio = _envPlantillasenviosMapper
							.selectByPrimaryKey(PlantillaKey);
					NewIdDTO idPlantilla = _envPlantillaEnviosExtendsMapper.selectMaxIDPlantillas();
					int idPlantillaEnvio = plantillaEnvio.getIdplantillaenvios();
					int idPlantillaNuevo = Short.valueOf(idPlantilla.getNewId());
					plantillaEnvio.setIdplantillaenvios(idPlantillaNuevo);
					plantillaEnvio.setFechamodificacion(new Date());
					plantillaEnvio.setUsumodificacion(usuario.getIdusuario());
					_envPlantillasenviosMapper.insert(plantillaEnvio);

					// tabla env_envios
					EnvEnviosKey keyEnvio = new EnvEnviosKey();
					keyEnvio.setIdenvio(Long.parseLong(datosTarjeta.getIdEnvio()));
					keyEnvio.setIdinstitucion(idInstitucion);
					EnvEnvios envio = _envEnviosMapper.selectByPrimaryKey(keyEnvio);
					Long idEnvio = envio.getIdenvio();
					envio.setIdplantillaenvios(plantillaEnvio.getIdplantillaenvios());
					envio.setFechamodificacion(new Date());
					envio.setUsumodificacion(usuario.getIdusuario());
					envio.setFecha(new Date());
					envio.setEnvio("M");
					Short idEstadoPendiente = 1;
					envio.setIdestado(idEstadoPendiente);
					_envEnviosMapper.insert(envio);
					Long idEnvioNuevo = envio.getIdenvio();

					// tabla env_envioProgramado
					EnvEnvioprogramadoKey progKey = new EnvEnvioprogramadoKey();
					progKey.setIdenvio(Long.parseLong(datosTarjeta.getIdEnvio()));
					progKey.setIdinstitucion(idInstitucion);
					EnvEnvioprogramado programado = _envEnvioprogramadoMapper.selectByPrimaryKey(progKey);
					if (programado != null) {
						programado.setIdenvio(idEnvioNuevo);
						programado.setIdplantillaenvios(plantillaEnvio.getIdplantillaenvios());
						_envEnvioprogramadoMapper.insert(programado);

						// Si es programada comprobamos las consultas asociadas al envio y las
						// duplicamos
						EnvConsultasenvioExample example = new EnvConsultasenvioExample();
						example.createCriteria().andIdenvioEqualTo(Long.parseLong(datosTarjeta.getIdEnvio()))
								.andIdinstitucionEqualTo(idInstitucion);

						List<EnvConsultasenvio> listaConsultasEnvio = _envConsultasenvioMapper
								.selectByExampleWithBLOBs(example);

						if (listaConsultasEnvio != null && listaConsultasEnvio.size() > 0) {
							for (EnvConsultasenvio consultaEnvio : listaConsultasEnvio) {
								// Duplicamos las consultas del envio
								consultaEnvio.setIdenvio(envio.getIdenvio());
								_envConsultasenvioMapper.insert(consultaEnvio);
							}
						}

					}

					// tabla env_documentos
					EnvDocumentosExample docExample = new EnvDocumentosExample();
					docExample.createCriteria().andIdenvioEqualTo(idEnvio).andIdinstitucionEqualTo(idInstitucion);
					List<EnvDocumentos> documentos = _envDocumentosMapper.selectByExample(docExample);
					for (EnvDocumentos documento : documentos) {
						// el id documento se debería de calcular con secuencia en bdd
						documento.setIdenvio(idEnvioNuevo);
						documento.setFechamodificacion(new Date());
						documento.setUsumodificacion(usuario.getIdusuario());
						_envDocumentosMapper.insert(documento);
					}

					// tabla env_historicoestadoenvio
					EnvHistoricoestadoenvioExample histExample = new EnvHistoricoestadoenvioExample();
					histExample.createCriteria().andIdenvioEqualTo(idEnvio).andIdinstitucionEqualTo(idInstitucion);
					List<EnvHistoricoestadoenvio> historicosEstados = _envHistoricoestadoenvioMapper
							.selectByExample(histExample);
					for (EnvHistoricoestadoenvio envHistorico : historicosEstados) {
						// el id historico se debería de calcular con secuencia en bdd
						envHistorico.setIdenvio(idEnvioNuevo);
						envHistorico.setFechamodificacion(new Date());
						envHistorico.setUsumodificacion(usuario.getIdusuario());
						envHistorico.setIdestado(idEstadoPendiente);
						_envHistoricoestadoenvioMapper.insert(envHistorico);
					}

					// env_plantilla remitentes
					// EnvPlantillaremitentesExample keyRemitentesExample = new
					// EnvPlantillaremitentesExample();
					// keyRemitentesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdplantillaenviosEqualTo(idPlantillaEnvio).andIdtipoenviosEqualTo(envio.getIdtipoenvios());
					// List<EnvPlantillaremitentes> plantillaRemitentes =
					// _envPlantillaremitentesMapper.selectByExample(keyRemitentesExample);
					// for (EnvPlantillaremitentes envPlantillaremitentes : plantillaRemitentes) {
					// envPlantillaremitentes.setIdplantillaenvios(idPlantillaNuevo);
					// envPlantillaremitentes.setFechamodificacion(new Date());
					// envPlantillaremitentes.setUsumodificacion(usuario.getIdusuario());
					// _envPlantillaremitentesMapper.insert(envPlantillaremitentes);
					// }

					// env_grupocliente
					EnvEnviosgrupoclienteExample gruposExample = new EnvEnviosgrupoclienteExample();
					gruposExample.createCriteria().andIdenvioEqualTo(idEnvio);
					List<EnvEnviosgrupocliente> grupos = _envEnviosgrupoclienteMapper.selectByExample(gruposExample);
					for (EnvEnviosgrupocliente envEnviosgrupocliente : grupos) {
						envEnviosgrupocliente.setIdenvio(idEnvioNuevo);
						envEnviosgrupocliente.setFechamodificacion(new Date());
						envEnviosgrupocliente.setUsumodificacion(usuario.getIdusuario());
						_envEnviosgrupoclienteMapper.insert(envEnviosgrupocliente);
					}

					List<EnviosMasivosItem> listaEnvioDuplicado = _envEnviosExtendsMapper.selectEnviosMasivosById(
							idInstitucion, usuario.getIdlenguaje(), String.valueOf(idEnvioNuevo));

					if (listaEnvioDuplicado != null && listaEnvioDuplicado.size() == 1) {
						respuesta.setEnviosMasivosItem(listaEnvioDuplicado);

						LOGGER.debug("Duplicado correctamente");
					} else {
						LOGGER.debug("No se ha encontrado el envio duplicado");
					}

				} catch (Exception e) {
					LOGGER.error("Error al duplicar el envío", e);
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					respuesta.setError(error);
				}

			}
		}
		LOGGER.info("duplicarEnvio() -> Salida del servicio para duplicar el envío");
		return respuesta;
	}

	@Override
	public DocumentosEnvioDTO obtenerDocumentosEnvio(HttpServletRequest request, String idEnvio) {

		LOGGER.info("obtenerDocumentosEnvio() -> Entrada al servicio para obtener documento de envio");
		Error error = null;
		DocumentosEnvioDTO response = new DocumentosEnvioDTO();

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

					List<DocumentoEnvioItem> documentos = _envDocumentosExtendsMapper
							.selectDocumentosEnvio(idInstitucion, idEnvio);

					if (documentos.size() > 0) {
						response.setDocumentoEnvioItem(documentos);
					}

				} catch (Exception e) {
					error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					response.setError(error);
				}

			}
		}

		LOGGER.info("obtenerDocumentosEnvio() -> Salida del servicio para obtener documento de envio");
		return response;
	}

	@Override
	public ComboDTO obtenerEtiquetas(HttpServletRequest request) {

		LOGGER.info("obtenerEtiquetas() -> Entrada al servicio para obtener combo etiquetas");

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

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _cenGruposclienteClienteExtendsMapper.selectGruposEtiquetas(usuario.getIdlenguaje(),
						idInstitucion.toString());
				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("obtenerEtiquetas() -> Salida del servicio para obtener combo etiquetas");

		return comboDTO;
	}

	@Override
	public ComboDTO obtenerEtiquetasEnvio(HttpServletRequest request, String idEnvio) {

		LOGGER.info("obtenerEtiquetasEnvio() -> Entrada al servicio para obtener las etiquetas de un envio");

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

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _envEnviosGrupoClienteExtendsMapper.getGruposEnvio(idEnvio, usuario.getIdlenguaje(),
						idInstitucion);
				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("obtenerEtiquetasEnvio() -> Salida del servicio para obtener las etiquetas de un envio");

		return comboDTO;
	}

	@Override
	public Error guardarEtiquetasEnvio(HttpServletRequest request, TarjetaEtiquetasDTO etiquetasDTO) {

		LOGGER.info("guardarEtiquetasEnvio() -> Entrada al servicio para guardar datos etiquetas");

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
					for (int i = 0; i < etiquetasDTO.getEtiquetasNoSeleccionadas().length; i++) {
						EnvEnviosgrupoclienteExample example = new EnvEnviosgrupoclienteExample();
						example.createCriteria().andIdenvioEqualTo(Long.valueOf(etiquetasDTO.getIdEnvio()))
								.andIdgrupoEqualTo(Short.valueOf(etiquetasDTO.getEtiquetasNoSeleccionadas()[i]));
						_envEnviosgrupoclienteMapper.deleteByExample(example);
					}

					// añadimos las etiquetas seleccionadas
					for (int i = 0; i < etiquetasDTO.getEtiquetasSeleccionadas().length; i++) {
						EnvEnviosgrupocliente etiqueta = new EnvEnviosgrupocliente();
						etiqueta.setIdenvio(Long.valueOf(etiquetasDTO.getIdEnvio()));
						etiqueta.setFechamodificacion(new Date());
						etiqueta.setIdgrupo(Short.valueOf(etiquetasDTO.getEtiquetasSeleccionadas()[i]));
						etiqueta.setUsumodificacion(usuario.getIdusuario());
						_envEnviosgrupoclienteMapper.insert(etiqueta);
					}

					respuesta.setCode(200);
					respuesta.setDescription("Datos etiquetas de envio guardados correctamente");
					respuesta.setMessage("Updates correcto");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}

			}
		}
		LOGGER.info("guardarEtiquetasEnvio() -> Salida del servicio para guardar datos etiquetas");
		return respuesta;
	}

	@Override
	public ResponseDocumentoDTO uploadFile(MultipartHttpServletRequest request) throws IOException {
		LOGGER.info("uploadFile() -> Entrada al servicio para subir un documento de envio");

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

				// crear path para almacenar el fichero

				GenPropertiesKey key = new GenPropertiesKey();
				key.setFichero(SigaConstants.FICHERO_SIGA);
				key.setParametro(SigaConstants.parametroRutaPlantillas);

				GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);

				String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator
						+ SigaConstants.carpetaDocumentosEnvio + SigaConstants.pathSeparator
						+ String.valueOf(idInstitucion) + SigaConstants.pathSeparator;

				String pathFichero = rutaPlantilla;

				// 1. Coger archivo del request
				LOGGER.debug("uploadFile() -> Coger documento de cuenta bancaria del request");
				Iterator<String> itr = request.getFileNames();
				MultipartFile file = request.getFile(itr.next());
				String fileName = file.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				// BufferedOutputStream stream = null;
				try {
					File aux = new File(rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator
							+ SigaConstants.carpetaDocumentosEnvio + SigaConstants.pathSeparator);
					// creo directorio si no existe
					aux.mkdirs();

					aux = new File(pathFichero);
					// creo directorio si no existe
					aux.mkdirs();
					File serverFile = new File(pathFichero, fileName);
					if (serverFile.exists()) {
						LOGGER.error("Ya existe el fichero: " + pathFichero + fileName);
						throw new FileAlreadyExistsException("El fichero ya existe");
					}
					// stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					// stream.write(file.getBytes());
					FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
					response.setNombreDocumento(fileName);
					response.setRutaDocumento(pathFichero + fileName);
				} catch (FileNotFoundException e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					response.setError(error);
					e.printStackTrace();
					LOGGER.error("uploadFile() -> Error al buscar el documento de envio en el directorio indicado", e);
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
					LOGGER.error("uploadFile() -> Error al guardar el documento de envio en el directorio indicado",
							ioe);
				} finally {
					// close the stream
					LOGGER.debug("uploadFile() -> Cierre del stream del documento");
					// stream.close();
				}
			}
		}

		LOGGER.info("uploadFile() -> Salida del servicio para subir un documento de envio");
		return response;
	}

	@Override
	public Error guardarDocumentoEnvio(HttpServletRequest request, ResponseDocumentoDTO documentoDTO) {

		LOGGER.info("guardarDocumentoEnvio() -> Entrada al servicio para guardar datos tarjeta docuentos");

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
					EnvDocumentos documento = new EnvDocumentos();
					documento.setIdinstitucion(idInstitucion);
					documento.setIdenvio(Long.valueOf(documentoDTO.getIdEnvio()));
					documento.setPathdocumento(documentoDTO.getRutaDocumento());
					documento.setDescripcion(documentoDTO.getNombreDocumento());
					documento.setFechamodificacion(new Date());
					documento.setUsumodificacion(usuario.getIdusuario());
					_envDocumentosMapper.insert(documento);
					respuesta.setCode(200);
					respuesta.setDescription("Datos configuracion de envio guardados correctamente");
					respuesta.setMessage("Updates correcto");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al insertar documento");
				}

			}
		}
		LOGGER.info("guardarDocumentoEnvio() -> Salida del servicio para guardar datos tarjeta documentos");
		return respuesta;
	}

	@Override
	public Error borrarDocumento(HttpServletRequest request, ResponseDocumentoDTO[] documentoDTO) {

		LOGGER.info("borrarDocumento() -> Entrada al servicio para borrar un documento");

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
					boolean noBorrado = false;
					for (int i = 0; i < documentoDTO.length; i++) {
						File fichero = new File(documentoDTO[i].getRutaDocumento());
						if (fichero.exists()) {
							fichero.delete();
							EnvDocumentosExample example = new EnvDocumentosExample();
							example.createCriteria().andIdenvioEqualTo(Long.valueOf(documentoDTO[i].getIdEnvio()))
									.andPathdocumentoEqualTo(documentoDTO[i].getRutaDocumento());
							_envDocumentosMapper.deleteByExample(example);
						} else {
							noBorrado = true;
						}
					}
					if (noBorrado) {
						respuesta.setCode(400);
						respuesta.setDescription("Algunos Documento/s no se han borrado");
					} else {
						respuesta.setCode(200);
						respuesta.setDescription("Documento/s borrado/s correctamente");
						respuesta.setMessage("Delete correcto");
					}

				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al borrar el documento");
					LOGGER.error("borrarDocumento() -> Error al borrar el documento " + e.getMessage());
				}

			}
		}
		LOGGER.info("borrarDocumento() -> Salida del servicio para borrar un documento");
		return respuesta;
	}

	@Override
	public PlantillaEnvioItem obtenerAsuntoYcuerpo(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta) {

		LOGGER.info("ObtenerAsuntoYcuerpo() -> Entrada al servicio para obtener el asunto y el cuerpo de la plantilla");

		PlantillaEnvioItem plantilla = new PlantillaEnvioItem();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				EnvPlantillasenviosKey key = new EnvPlantillasenviosKey();
				key.setIdinstitucion(idInstitucion);
				key.setIdplantillaenvios(Integer.parseInt(datosTarjeta.getIdPlantillaEnvios()));
				key.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvios()));
				EnvPlantillasenviosWithBLOBs plant = _envPlantillasenviosMapper.selectByPrimaryKey(key);
				plantilla.setCuerpo(plant.getCuerpo());
				plantilla.setAsunto(plant.getAsunto());

			}
		}

		LOGGER.info(
				"ObtenerAsuntoYcuerpo() -> Salida del servicio para obtener el asunto y el cuerpo de la plantillas");

		return plantilla;
	}

	@Override
	public ComboConsultaInstitucionDTO obtenerconsultasDestinatarios(HttpServletRequest request) {
		LOGGER.info(
				"obtenerconsultasDestinatarios() -> Entrada al servicio para obtener consultas de desinatario del envio");

		ComboConsultaInstitucionDTO response = new ComboConsultaInstitucionDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				long idObjetivo = 1;
				List<ComboItemConsulta> consultas = _conConsultasExtendsMapper.selectConsultasDisponibles(idInstitucion,
						null, idObjetivo);
				response.setConsultas(consultas);
			}
		}

		LOGGER.info(
				"obtenerconsultasDestinatarios() -> Salida del servicio para obtener consultas de desinatario del envio");
		return response;
	}

	@Override
	public ConsultasDTO consultasDestAsociadas(HttpServletRequest request, String idEnvio) {
		LOGGER.info(
				"consultasDestEnvio() -> Entrada al servicio para obtener consultas de asociadas desinatario del envio");

		ConsultasDTO response = new ConsultasDTO();

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
					List<ConsultaItem> consultaItem = _envDestConsultaEnvioExtendsMapper
							.selectConsultasDestEnvio(idInstitucion, idEnvio);
					response.setConsultaItem(consultaItem);
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener consultas asociadas al envio");
					response.setError(error);
				}

			}
		}

		LOGGER.info(
				"consultasDestEnvio() -> Salida del servicio para obtener consultas de asociadas desinatario del envio");
		return response;
	}

	@Override
	public Error asociarConsulta(HttpServletRequest request, ConsultaDestinatarioItem consulta) {
		LOGGER.info("asociarConsulta() -> Entrada al servicio para asociar consulta de destinatario de envio");

		Error response = new Error();
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
					EnvDestConsultaEnvioKey key = new EnvDestConsultaEnvioKey();
					key.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
					key.setIdenvio(Long.parseLong(consulta.getIdEnvio()));
					EnvDestConsultaEnvio consultaEntity = _envDestConsultaEnvioMapper.selectByPrimaryKey(key);
					if (consultaEntity == null) {
						consultaEntity = new EnvDestConsultaEnvio();
						consultaEntity.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
						consultaEntity.setIdenvio(Long.parseLong(consulta.getIdEnvio()));
						consultaEntity.setIdinstitucion(idInstitucion);
						consultaEntity.setIdinstitucionConsulta(Short.parseShort(consulta.getIdInstitucion()));
						consultaEntity.setUsumodificacion(usuarios.get(0).getIdusuario());
						consultaEntity.setFechamodificacion(new Date());
						_envDestConsultaEnvioMapper.insert(consultaEntity);
						response.setCode(200);
						response.setDescription("Consulta asociada");
					} else {
						if (consultaEntity.getFechabaja() != null) {
							consultaEntity.setFechabaja(null);
							consultaEntity.setUsumodificacion(usuarios.get(0).getIdusuario());
							consultaEntity.setFechamodificacion(new Date());
							_envDestConsultaEnvioMapper.updateByPrimaryKey(consultaEntity);
						} else {
							response.setCode(400);
							response.setDescription("La consulta ya ha sido asociada");
						}
					}

				} catch (Exception e) {
					response.setCode(500);
					response.setDescription("Error al asociar la consulta");
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("asociarConsulta() -> Salida del servicio para asociar consulta de destinatario de envio");
		return response;
	}

	@Override
	public Error desAsociarConsulta(HttpServletRequest request, ConsultaDestinatarioItem[] consultas) {
		LOGGER.info("desAsociarConsulta() -> Entrada al servicio para asociar consulta de destinatario de envio");

		Error response = new Error();
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
					for (ConsultaDestinatarioItem consulta : consultas) {
						EnvDestConsultaEnvioKey key = new EnvDestConsultaEnvioKey();
						key.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
						key.setIdenvio(Long.parseLong(consulta.getIdEnvio()));
						EnvDestConsultaEnvio consultaEntity = _envDestConsultaEnvioMapper.selectByPrimaryKey(key);
						consultaEntity.setFechabaja(new Date());
						consultaEntity.setUsumodificacion(usuarios.get(0).getIdusuario());
						consultaEntity.setFechamodificacion(new Date());
						_envDestConsultaEnvioMapper.updateByPrimaryKey(consultaEntity);
						response.setCode(200);
						response.setDescription("Consulta asociada");
					}
				} catch (Exception e) {
					response.setCode(500);
					response.setDescription("Error al des asociar la consulta");
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("desAsociarConsulta() -> Salida del servicio para desasociar consulta de destinatario de envio");
		return response;
	}

	@Override
	public ComboConsultaInstitucionDTO getComboConsultas(HttpServletRequest request, String filtro) {
		LOGGER.info("getComboConsultas() -> Entrada al servicio para obtener las consultas disponibles");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboConsultaInstitucionDTO comboDTO = new ComboConsultaInstitucionDTO();
		List<ComboItemConsulta> comboItems = new ArrayList<ComboItemConsulta>();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				long idObjetivoDest = 1;
				comboItems = _conConsultasExtendsMapper.selectConsultasDisponiblesFiltro(idInstitucion, null,
						idObjetivoDest, filtro);

				if (null != comboItems && comboItems.size() > 0) {
					ComboItemConsulta element = new ComboItemConsulta();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}

				comboDTO.setConsultas(comboItems);

			}
		}

		LOGGER.info("getComboConsultas() -> Salida del servicio para obtener las consultas disponibles");
		return comboDTO;
	}

	@Override
	public DestinatariosDTO obtenerDestinatariosIndv(HttpServletRequest request, String idEnvio) {
		LOGGER.info(
				"obtenerDestinatariosIndv() -> Entrada al servicio para obtener los destinatarios individuales de envio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DestinatariosDTO response = new DestinatariosDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				try {
					List<DestinatarioItem> destinatarios = _envDestinatariosExtendsMapper
							.selectDestinatarios(idInstitucion, idEnvio);
					if (destinatarios != null && destinatarios.size() > 0) {
						response.setDestinatarios(destinatarios);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription("Error al obtener destinatarios indivuales");
					response.setError(error);
					e.printStackTrace();
				}

			}
		}

		LOGGER.info(
				"obtenerDestinatariosIndv() -> Salida del servicio para obtener los destinatarios individuales de envio");
		return response;
	}

	@Override
	public Error asociarDestinatario(HttpServletRequest request, DestinatarioIndvEnvioMasivoItem destinatario) {
		LOGGER.info("asociarDestinatario() -> Entrada al servicio para asociar un destinatario individual");

		Error response = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				EnvDestinatariosExample ejemplo = new EnvDestinatariosExample();
				ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdenvioEqualTo(Long.parseLong(destinatario.getIdEnvio()))
						.andIdpersonaEqualTo(Long.parseLong(destinatario.getIdPersona()));
				List<EnvDestinatarios> destinatariosExistentes = _envDestinatariosMapper.selectByExample(ejemplo);
				if (destinatariosExistentes.size() > 0) {
					try {
						CenPersona persona = _cenPersonaMapper
								.selectByPrimaryKey(Long.parseLong(destinatario.getIdPersona()));
						CenDireccionesKey key = new CenDireccionesKey();
						key.setIddireccion(Long.parseLong(destinatario.getIdDireccion()));
						key.setIdpersona(Long.parseLong(destinatario.getIdPersona()));
						key.setIdinstitucion(idInstitucion);
						CenDirecciones dir = _cenDireccionesMapper.selectByPrimaryKey(key);
						EnvDestinatarios dest = new EnvDestinatarios();
						dest.setApellidos1(persona.getApellidos1());
						dest.setApellidos2(persona.getApellidos2());
						dest.setCodigopostal(dir.getCodigopostal());
						dest.setCorreoelectronico(dir.getCorreoelectronico());
						dest.setDomicilio(dir.getDomicilio());
						dest.setFax1(dir.getFax1());
						dest.setFax2(dir.getFax2());
						dest.setFechamodificacion(new Date());
						dest.setIdenvio(Long.parseLong(destinatario.getIdEnvio()));
						dest.setIdinstitucion(idInstitucion);
						dest.setIdpais(dir.getIdpais());
						dest.setIdpersona(persona.getIdpersona());
						dest.setIdpoblacion(dir.getIdpoblacion());
						dest.setIdprovincia(dir.getIdprovincia());
						dest.setMovil(dir.getMovil());
						dest.setNifcif(persona.getNifcif());
						dest.setNombre(persona.getNombre());
						dest.setPoblacionextranjera(dir.getPoblacionextranjera());
						dest.setUsumodificacion(usuarios.get(0).getIdusuario());
						dest.setTipodestinatario("CEN_PERSONA");
						_envDestinatariosMapper.updateByExample(dest, ejemplo);
						response.setCode(200);
						response.setDescription("Destinatario asociado con éxito");
					} catch (Exception e) {
						response.code(500);
						response.setDescription("Error al asociar destinatario");
						e.printStackTrace();
					}
				} else {
					try {
						CenPersona persona = _cenPersonaMapper
								.selectByPrimaryKey(Long.parseLong(destinatario.getIdPersona()));
						CenDireccionesKey key = new CenDireccionesKey();
						key.setIddireccion(Long.parseLong(destinatario.getIdDireccion()));
						key.setIdpersona(Long.parseLong(destinatario.getIdPersona()));
						key.setIdinstitucion(idInstitucion);
						CenDirecciones dir = _cenDireccionesMapper.selectByPrimaryKey(key);
						EnvDestinatarios dest = new EnvDestinatarios();
						dest.setApellidos1(persona.getApellidos1());
						dest.setApellidos2(persona.getApellidos2());
						dest.setCodigopostal(dir.getCodigopostal());
						dest.setCorreoelectronico(dir.getCorreoelectronico());
						dest.setDomicilio(dir.getDomicilio());
						dest.setFax1(dir.getFax1());
						dest.setFax2(dir.getFax2());
						dest.setFechamodificacion(new Date());
						dest.setIdenvio(Long.parseLong(destinatario.getIdEnvio()));
						dest.setIdinstitucion(idInstitucion);
						dest.setIdpais(dir.getIdpais());
						dest.setIdpersona(persona.getIdpersona());
						dest.setIdpoblacion(dir.getIdpoblacion());
						dest.setIdprovincia(dir.getIdprovincia());
						dest.setMovil(dir.getMovil());
						dest.setNifcif(persona.getNifcif());
						dest.setNombre(persona.getNombre());
						dest.setPoblacionextranjera(dir.getPoblacionextranjera());
						dest.setUsumodificacion(usuarios.get(0).getIdusuario());
						dest.setTipodestinatario("CEN_PERSONA");
						_envDestinatariosMapper.insert(dest);
						response.setCode(200);
						response.setDescription("Destinatario asociado con éxito");
					} catch (Exception e) {
						response.code(500);
						response.setDescription("Error al asociar destinatario");
						e.printStackTrace();
					}
				}
			}
		}

		LOGGER.info("asociarDestinatario() -> Salida del servicio para asociar un destinatario individual");
		return response;
	}

	@Override
	public Error desAsociarDestinatarios(HttpServletRequest request, DestinatarioIndvEnvioMasivoItem[] destinatarios) {
		LOGGER.info("desAsociarDestinatarios() -> Entrada al servicio para des asociar un destinatario individual");

		Error response = new Error();
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
					for (DestinatarioIndvEnvioMasivoItem destinatario : destinatarios) {
						EnvDestinatariosKey key = new EnvDestinatariosKey();
						key.setIdenvio(Long.parseLong(destinatario.getIdEnvio()));
						key.setIdinstitucion(idInstitucion);
						key.setIdpersona(Long.parseLong(destinatario.getIdPersona()));
						_envDestinatariosMapper.deleteByPrimaryKey(key);
					}
					response.setCode(200);
					response.setDescription("Destinatarios eliminados con éxito");
				} catch (Exception e) {
					response.setCode(500);
					response.setDescription("Error al eliminar los destinatarios");
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("desAsociarDestinatarios() -> Salida del servicio para des asociar un destinatario individual");
		return response;
	}

	@Override
	public DatosDireccionesDTO obtenerDireccionesDisp(HttpServletRequest request, String idPersona) {
		LOGGER.info("obtenerDireccionesDisp() -> Entrada al servicio para obtener direcciones");

		DatosDireccionesDTO response = new DatosDireccionesDTO();

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
					CenDireccionesExample example = new CenDireccionesExample();
					example.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona))
							.andIdinstitucionEqualTo(idInstitucion);
					List<CenDirecciones> direcciones = _cenDireccionesMapper.selectByExample(example);
					if (direcciones != null && direcciones.size() > 0) {
						List<DatosDireccionesItem> direccionesList = new ArrayList<DatosDireccionesItem>();
						for (CenDirecciones item : direcciones) {
							DatosDireccionesItem direccion = new DatosDireccionesItem();
							direccion.setIdPersona(item.getIdpersona().toString());
							if (item.getIdinstitucion() != null) {
								direccion.setIdInstitucion(item.getIdinstitucion().toString());
							}
							if (item.getIddireccion() != null) {
								direccion.setIdDireccion(item.getIddireccion().toString());
							}
							direccion.setDomicilio(item.getDomicilio());
							direccion.setIdPoblacion(item.getIdpoblacion());
							direccion.setIdProvincia(item.getIdprovincia());
							direccion.setIdPais(item.getIdpais());
							direccion.setCodigoPostal(item.getCodigopostal());
							direccion.setTelefono(item.getTelefono1());
							direccion.setTelefono2(item.getTelefono2());
							direccion.setMovil(item.getMovil());
							direccion.setFax(item.getFax1());
							direccion.setFax2(item.getFax2());
							direccion.setCorreoElectronico(item.getCorreoelectronico());
							direccion.setPaginaWeb(item.getPaginaweb());
							direccionesList.add(direccion);
						}
						response.setDatosDireccionesItem(direccionesList);
					} else {
						Error error = new Error();
						error.setCode(400);
						error.setDescription("No existen direcciones disponibles");
						response.setError(error);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription("Error al obtener direcciones de destinatario");
					response.setError(error);
					e.printStackTrace();
				}

			}
		}

		LOGGER.info("obtenerDireccionesDisp() -> Salida al servicio para obtener direcciones");
		return response;
	}

}
