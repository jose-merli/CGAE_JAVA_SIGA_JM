package org.itcgae.siga.scs.services.impl.oficio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaKey;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrisionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignacionesServiceImpl implements IDesignacionesService {

	private Logger LOGGER = Logger.getLogger(DesignacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosDesignaMapper;

	@Autowired
	private ScsDefendidosdesignaMapper scsDefendidosdesignaMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosdesignaMapper;

	@Autowired
	private ScsPrisionExtendsMapper scsPrisionExtendsMapper;

	@Override
	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item,
			HttpServletRequest request) {
		List<JustificacionExpressItem> result = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO parametros = new StringDTO();
		String idPersona = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaJustificacionExpres -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo longitud_codeejg...");
					// cargamos los parámetros necesarios
					String longitudCodEJG;

					// LONGITUD_CODEJG
					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG",
							idInstitucion.toString());

					// si el ncolegiado, viene relleno, debemos obtener la idpersona
					if (item.getnColegiado() != null && !item.getnColegiado().trim().isEmpty()) {
						LOGGER.info(
								"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo la idpersona...");

						// obtenemos la idpersona
						ColegiadosSJCSItem colegiadosSJCSItem = new ColegiadosSJCSItem();
						colegiadosSJCSItem.setnColegiado(item.getnColegiado());

						List<ColegiadosSJCSItem> colegiadosSJCSItems = cenColegiadoExtendsMapper
								.busquedaColegiadosSJCS(idInstitucion.toString(), colegiadosSJCSItem);

						if (colegiadosSJCSItems.size() > 0) {
							idPersona = colegiadosSJCSItems.get(0).getIdPersona();
						}
					}

					// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
					// la institucion 0
					if (parametros != null && parametros.getValor() != null) {
						longitudCodEJG = parametros.getValor();
					} else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", "0");
						longitudCodEJG = parametros.getValor();
					}

					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo justificacion pendientes...");
					// busqueda de designaciones segun los filtros (max 200)
					result = scsDesignacionesExtendsMapper.busquedaJustificacionExpresPendientes(item,
							idInstitucion.toString(), longitudCodEJG, idPersona);

					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo las actuaciones...");
					// obtenemos las actuaciones

					for (JustificacionExpressItem record : result) {
						record.setActuaciones(scsDesignacionesExtendsMapper.busquedaActuacionesJustificacionExpres(
								record.getIdInstitucion(), record.getIdTurno(), record.getAnioDesignacion(),
								record.getNumDesignacion()));
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> tratando expedientes...");
					// cogemos los expedientes devueltos de la consulta y los tratamos para el front
					for (int i = 0; i < result.size(); i++) {
						List<String> expedientes = new ArrayList<String>();

						if (result.get(i).getEjgs() != null && !result.get(i).getEjgs().isEmpty()) {
							String[] parts = result.get(i).getEjgs().split(",");

							for (String str : parts) {
								if (str.indexOf("##") != -1) {
									expedientes.add(str.substring(0, str.indexOf("##")).trim());
								}
							}
						}

						if (expedientes.size() > 0) {
							result.get(i).setExpedientes(expedientes);
						}
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida del servicio");
				} catch (Exception e) {
					LOGGER.error(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> ERROR: al consultar datos de la bd. ",
							e);
				}
			}
		}

		return result;
	}

	@Override
	public List<DesignaItem> busquedaDesignas(DesignaItem designaItem, HttpServletRequest request) {
		DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaDesignas -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaDesignaciones(designaItem, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem item, HttpServletRequest request) {

		// [designaItem.idTurno, designaItem.nombreTurno, designaItem.numero,
		// designaItem.anio]

		LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada al servicio servicio");
		List<ListaInteresadoJusticiableItem> interesados = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaListaContrarios -> Entrada a servicio para la busqueda de contrarios");
				try {
					interesados = scsDesignacionesExtendsMapper.busquedaListaInteresados(item, idInstitucion);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
			}
		}

		return interesados;
	}

	@Override
	public DeleteResponseDTO deleteInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteInteresado() ->  Entrada al servicio para eliminar contrarios");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsDefendidosdesignaKey key = new ScsDefendidosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsDefendidosdesignaMapper.deleteByPrimaryKey(key);

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("deleteInteresado() -> Salida del servicio para eliminar contrarios");

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO insertInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteInteresado() ->  Entrada al servicio para eliminar contrarios");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsDefendidosdesigna designa = new ScsDefendidosdesigna();
					designa.setAnio(item.getAnio());
					designa.setNumero(item.getNumero());
					designa.setIdturno(item.getIdturno());
					designa.setIdinstitucion(item.getIdinstitucion());
					designa.setIdpersona(item.getIdpersona());
					designa.setFechamodificacion(new Date());
					designa.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsDefendidosdesignaMapper.insert(designa);

					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertInteresado() -> Salida del servicio para eliminar contrarios");

		return insertResponseDTO;
	}

	@Override
	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request,
			Boolean historico) {
		LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada al servicio servicio");
		List<ListaContrarioJusticiableItem> contrarios = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaListaContrarios -> Entrada a servicio para la busqueda de contrarios");

				try {
					contrarios = scsDesignacionesExtendsMapper.busquedaListaContrarios(item, idInstitucion, historico);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
			}
		}

		return contrarios;
	}

	@Override
	public UpdateResponseDTO deleteContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteContrarios() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

					if (contrario.getFechabaja() == null) {
						contrario.setFechabaja(new Date());
					} else {
						contrario.setFechabaja(null);
					}

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"deleteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"deleteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("insertContrario() ->  Entrada al servicio para eliminar contrarios");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsContrariosdesigna designa = new ScsContrariosdesigna();
					designa.setAnio(item.getAnio());
					designa.setNumero(item.getNumero());
					designa.setIdturno(item.getIdturno());
					designa.setIdinstitucion(item.getIdinstitucion());
					designa.setIdpersona(item.getIdpersona());
					designa.setFechamodificacion(new Date());
					designa.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"insertContrario() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosdesignaMapper.insert(designa);

					LOGGER.info(
							"insertContrario() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertContrario() -> Salida del servicio para eliminar contrarios");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDetalleDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDetalleDesigna() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDetalleDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDetalleDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updateDetalleDesigna()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample ejemplo = new ScsDesignaExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdprocedimientoEqualTo(designaItem.getNumProcedimiento())
							.andIdjuzgadoEqualTo(new Long(designaItem.getIdJuzgado()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(ejemplo);

					if ((designaExistentes != null && designaExistentes.size() > 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} else {

						ScsDesignaExample example = new ScsDesignaExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(designaItem.getIdTurno())
								.andAnioEqualTo((short) designaItem.getAno())
								.andNumeroEqualTo(new Long(designaItem.getNumero()));

						ScsDesigna scsDesigna = new ScsDesigna();
						List<ScsDesigna> scsDesignaLista = scsDesignacionesExtendsMapper.selectByExample(example);
						scsDesigna = scsDesignaLista.get(0);

						scsDesigna.setEstado(designaItem.getEstado());
						scsDesigna.setFechaestado(designaItem.getFechaEstado());
						scsDesigna.setFechafin(designaItem.getFechaFin()); // Fecha cierre
						scsDesigna.setNig(designaItem.getNig());
						scsDesigna.setNumprocedimiento(designaItem.getNumProcedimiento());
						scsDesigna.setIdjuzgado(new Long(designaItem.getIdJuzgado()));
						scsDesigna.setIdprocedimiento(designaItem.getIdProcedimiento());
						// TODO modulo
						scsDesigna.setDelitos(designaItem.getDelitos());

						LOGGER.info("updateDetalleDesigna() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDetalleDesigna() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

						LOGGER.info(
								"updateDetalleDesigna() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					}
				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateDetalleDesigna() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDatosAdicionales(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDatosAdicionales() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDatosAdicionales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDatosAdicionales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updateDatosAdicionales()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample ejemplo = new ScsDesignaExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdprocedimientoEqualTo(designaItem.getNumProcedimiento())
							.andIdjuzgadoEqualTo(new Long(designaItem.getIdJuzgado()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(ejemplo);

					if ((designaExistentes != null && designaExistentes.size() > 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} else {

						ScsDesignaExample example = new ScsDesignaExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(designaItem.getIdTurno())
								.andAnioEqualTo((short) designaItem.getAno())
								.andNumeroEqualTo(new Long(designaItem.getNumero()));

						ScsDesigna scsDesigna = new ScsDesigna();
						List<ScsDesigna> scsDesignaLista = scsDesignacionesExtendsMapper.selectByExample(example);
						scsDesigna = scsDesignaLista.get(0);

						scsDesigna.setFechaoficiojuzgado(designaItem.getFechaOficioJuzgado());
						// TODO faltan dos campos observaciones
						scsDesigna.setObservaciones(designaItem.getObservaciones());
						scsDesigna.setFecharecepcioncolegio(designaItem.getFechaRecepcionColegio());
						// TODO hora juicio?
						scsDesigna.setFechajuicio(designaItem.getFechaJuicio());

						LOGGER.info("updateDatosAdicionales() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					}
				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info(
						"updateDatosAdicionales() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public ActuacionDesignaDTO busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		ActuacionDesignaDTO actuacionDedignaDTO = new ActuacionDesignaDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				LOGGER.info(
						"DesignacionesServiceImpl.busquedaActDesigna() -> Se inicia la búsqueda de actuaciones asociadas a una designación");

				List<ActuacionDesignaItem> listaActuacionDesignaItem = scsDesignacionesExtendsMapper
						.busquedaActDesigna(actuacionDesignaRequestDTO, Short.toString(idInstitucion));

				actuacionDedignaDTO.setActuacionesDesignaItems(listaActuacionDesignaItem);

				LOGGER.info(
						"DesignacionesServiceImpl.busquedaActDesigna() -> Se finaliza la búsqueda de actuaciones asociadas a una designación");
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.busquedaActDesigna() -> Se ha producido un error al consultar las actuaciones asociadas a una designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			actuacionDedignaDTO.setError(error);
		}

		return actuacionDedignaDTO;
	}

	@Override
	public UpdateResponseDTO anularReactivarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			boolean anular, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.anularReactivarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.anularReactivarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for (ActuacionDesignaItem actuacionDesignaItem : listaActuacionDesignaItem) {

					if ((anular && !actuacionDesignaItem.isFacturado()) || !anular) {
						int response = scsDesignacionesExtendsMapper.anularReactivarActDesigna(actuacionDesignaItem,
								Short.toString(idInstitucion), usuarios.get(0), anular);
						responses.add(response);
					}

				}

				updateResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar alguna de las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al anular/reactivar alguna de las actuaciones asociadas a la designación");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for (ActuacionDesignaItem actuacionDesignaItem : listaActuacionDesignaItem) {

					int response = scsDesignacionesExtendsMapper.eliminarActDesigna(actuacionDesignaItem,
							Short.toString(idInstitucion), usuarios.get(0));
					responses.add(response);

				}

				deleteResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al eliminar alguna de las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al eliminar alguna las actuaciones asociadas a la designación");
					deleteResponseDTO.setError(error);
				} else {
					error.setCode(200);
					error.setDescription("messages.deleted.selected.success");
					deleteResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al anular las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO createDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("createDesigna() ->  Entrada al servicio para insertar designacion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		Integer idTurnoNuevo = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);

			LOGGER.info(
					"createDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() >= 0) {
				try {

					ScsDesigna designa = new ScsDesigna();
					designa.setIdinstitucion(idInstitucion);
					designa.setIdturno(designaItem.getIdTurno());
					designa.setAnio((short) designaItem.getAno());
					designa.setNumero(new Long(designaItem.getNumero()));
					designa.setIdtipodesignacolegio((short) designaItem.getIdTipoDesignaColegio());
					designa.setArt27(designaItem.getArt27());

					ScsDesignasletrado designaLetrado = new ScsDesignasletrado();
					designaLetrado.setIdinstitucion(idInstitucion);
					designaLetrado.setIdturno(designaItem.getIdTurno());
					designaLetrado.setAnio((short) designaItem.getAno());
					designaLetrado.setNumero(new Long(designaItem.getNumero()));
					// designaLetrado.set

					LOGGER.info(
							"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

					// response = scsTurnosExtendsMapper.insert(turno);

					LOGGER.info(
							"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if (error.getDescription() == null) {
				error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(String.valueOf(idTurnoNuevo));
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("updateModules() -> Salida del servicio para insertar modulos");

		return insertResponseDTO;

	}

	@Override
	public ProcuradorDTO busquedaProcurador(List<String> procurador, HttpServletRequest request) {
		LOGGER.info("searchPrisiones() -> Entrada al servicio para obtener prisiones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				String num = procurador.get(1);
				String idinstitucion = procurador.get(0);
				procuradorItemList = scsDesignacionesExtendsMapper.busquedaProcurador(num, idinstitucion);

				LOGGER.info(
						"searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("searchComisarias() -> Salida del servicio para obtener prisiones");
		return procuradorDTO;
	}

	@Override
	public ComboDTO comboTipoMotivo(HttpServletRequest request) {

		LOGGER.info("comboTipoMotivo() -> Entrada al servicio para combo comboTipoMotivo");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoMotivo() / scsDesignacionesExtendsMapper.comboTipoMotivo() -> Entrada a scsDesignacionesExtendsMapper para obtener combo TipoMotivo");

				List<ComboItem> comboItems = scsDesignacionesExtendsMapper.comboTipoMotivo(idInstitucion);

				LOGGER.info(
						"comboTipoMotivo() / scsDesignacionesExtendsMapper.comboTipoMotivo() -> Salida e scsDesignacionesExtendsMapper para obtener combo TipoMotivo");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoMotivo() -> Salida del servicio para obtener combo TipoMotivo");
		}
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO guardarProcurador(List<ProcuradorItem> procuradorItem, HttpServletRequest request) {
		LOGGER.info("deleteBaja() ->  Entrada al servicio para eliminar bajas temporales");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (ProcuradorItem bti : procuradorItem) {
//						response = scsDesignacionesExtendsMapper.guardarProcurador(bti);
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha eliminado la baja temporal");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han elimiando las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha eliminado la baja temporal correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("deleteBaja() -> Salida del servicio para eliminar bajas temporales");

			}

		}

		return updateResponseDTO;
	}

	@Override
	public List<DesignaItem> getDatosAdicionales(DesignaItem designa, HttpServletRequest request) {
		DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.getDatosAdicionales -> Entrada a servicio para la busqueda de datos adicionales de una designa");

				try {
					designas = scsDesignacionesExtendsMapper.getDatosAdicionales(idInstitucion, tamMaximo, designa);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
			}
		}

		return designas;
	}

	 @Override
	public ComboDTO comboPrisiones(HttpServletRequest request) {

		LOGGER.info("comboPrisiones() -> Entrada al servicio para combo comboPrisiones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPrisiones() / scsPrisionExtendsMapper.getComboPrisiones() -> Entrada a scsPrisionExtendsMapper para obtener combo de prisiones");

				List<ComboItem> comboItems = scsPrisionExtendsMapper.getComboPrisiones(idInstitucion);

				LOGGER.info(
						"comboPrisiones() / scsPrisionExtendsMapper.getComboPrisiones() -> Salida e scsPrisionExtendsMapper para obtener combo de prisiones");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboPrisiones() -> Salida del servicio para obtener combo de prisiones");
		}
		return comboDTO;
	}
}