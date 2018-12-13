package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DocumentoEnvioItem;
import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IComunicacionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoKey;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvHistoricoestadoenvioMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDocumentosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEstadoEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvHistoricoEstadoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvTipoClaseComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvTipoEnvioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComunicacionesServiceImpl implements IComunicacionesService {

	private Logger LOGGER = Logger.getLogger(ComunicacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private EnvTipoEnvioExtendsMapper _envTipoEnvioExtendsMapper;

	@Autowired
	private EnvTipoClaseComunicacionExtendsMapper _envTipoClaseComunicacionExtendsMapper;
	
	@Autowired
	private EnvEstadoEnvioExtendsMapper _envEstadoEnvioExtendsMapper;
	
	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;
	
	@Autowired
	private EnvEnviosMapper _envEnviosMapper;
	
	@Autowired
	private EnvHistoricoestadoenvioMapper _envHistoricoestadoenvioMapper;
	
	@Autowired
	private EnvHistoricoEstadoExtendsMapper _envHistoricoEstadoExtendsMapper;
	
	@Autowired
	private EnvEnvioprogramadoMapper _envEnvioprogramadoMapper;
	
	@Autowired
	private EnvDocumentosExtendsMapper _envDocumentosExtendsMapper;
	
	@Autowired
	private EnvDocumentosMapper _envDocumentosMapper;
	
	/**Combo estado de envio**/
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
				if (null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");

		return comboDTO;
	}

	/**Combo tipo de envio**/
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
				if (null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("tipoEnvio() -> Salida del servicio para obtener combo tipos de envio");

		return comboDTO;
	}

	/**Combo de clase de comunicaciones**/
	@Override
	public ComboDTO claseComunicacion(HttpServletRequest request) {
		LOGGER.info("tipoClaseComunicacion() -> Entrada al servicio para obtener tipos de clase de comunicacion");

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
				comboItems = _envTipoClaseComunicacionExtendsMapper.selectTipoClaseComunicacion(usuario.getIdlenguaje());
				if (null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("tipoClaseComunicacion() -> Salida del servicio para obtener combo tipos de clase de comunicacion");

		return comboDTO;
	}

	/**Realiza la busqueda de comunicaciones **/
	@Override
	public EnviosMasivosDTO comunicacionesSearch(HttpServletRequest request, EnviosMasivosSearch filtros) {
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

	/**Programa el envío modificando la hora y la fecha**/
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
						// Solo programamos los envios si tiene estado 1(nuevo)
						// o 4(programado)
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
									envioProgramado.setIdplantilla(enviosProgramadosDto[i].getIdPlantilla());
								}
								envioProgramado.setIdplantillaenvios(enviosProgramadosDto[i].getIdPlantillasEnvio());
								envioProgramado.setIdtipoenvios(enviosProgramadosDto[0].getIdTipoEnvio());
								envioProgramado.setNombre(enviosProgramadosDto[0].getDescripcion());
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

	/**Cancela el envío - cambia el estado a 6 ARCHIVADO**/
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
						// Solo cancelamos los envios si tiene estado 1(nuevo) o
						// 4(programado)
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

								EnvHistoricoestadoenvio historico = new EnvHistoricoestadoenvio();
								NewIdDTO idDTO = _envHistoricoEstadoExtendsMapper.selectMaxIDHistorico();
								historico.setIdhistorico(Short.parseShort(idDTO.getNewId()));
								historico.setIdenvio(Long.parseLong(envios[i].getIdEnvio()));
								historico.setIdinstitucion(usuario.getIdinstitucion());
								historico.setFechamodificacion(new Date());
								historico.setFechaestado(new Date());
								historico.setUsumodificacion(usuario.getIdusuario());
								historico.setIdestado(idEstado);
								_envHistoricoestadoenvioMapper.insert(historico);
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

	/**Obtiene los documentos del envío, para cargarlo en la ficha**/
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

	/**Duplica el envío pero mandandolo a Envios masivos**/
	@Override
	public Error reenviar(HttpServletRequest request, EnviosMasivosItem[] envio) {
		LOGGER.info("reenviar() -> Entrada al servicio para reenviar");
		
		// TODO Auto-generated method stub
		LOGGER.info("reenviar() -> Salida del servicio para reenviar");
		return null;
	}

	/**Obtiene los datos de la configuración del envío, para cargarlo en la ficha**/
	@Override
	public EnviosMasivosDTO detalleConfiguracion(HttpServletRequest request, String idEnvio) {
		LOGGER.info("detalleConfiguracion() -> Entrada al servicio para obtener el detalle de la configuracion del envio");
		
		// TODO Auto-generated method stub
		LOGGER.info("detalleConfiguracion() -> Salida del servicio para obtener el detalle de la configuracion del envio");
		return null;
	}

	/**Obtiene los destinatarios del envío, para cargar los destinatarios en la ficha**/
	@Override
	public EnviosMasivosDTO detalleDestinatarios(HttpServletRequest request, String idEnvio) {
		LOGGER.info("detalleDestinatarios() -> Entrada al servicio para obtener el detalle de los destinatarios del envio");
		
		// TODO Auto-generated method stub
		LOGGER.info("detalleDestinatarios() -> Salida del servicio para obtener el detalle de los destinatarios de envio");
		return null;
	}

}
