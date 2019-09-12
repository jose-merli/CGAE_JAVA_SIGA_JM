package org.itcgae.siga.scs.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.JuzgadoDTO;
import org.itcgae.siga.DTO.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsJuzgado;
import org.itcgae.siga.db.entities.ScsJuzgadoExample;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimiento;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimientoExample;
import org.itcgae.siga.db.mappers.ScsJuzgadoprocedimientoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.scs.service.IBusquedaJuzgadosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusquedaJuzgadosServiceImpl implements IBusquedaJuzgadosService {

	private Logger LOGGER = Logger.getLogger(BusquedaJuzgadosServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsJuzgadoExtendsMapper scsJuzgadoExtendsMapper;

	@Autowired
	private ScsJuzgadoprocedimientoMapper scsJuzgadoprocedimientoMapper;

	@Override
	public JuzgadoDTO searchJudged(JuzgadoItem juzgadoItem, HttpServletRequest request) {
		LOGGER.info("searchJudged() -> Entrada al servicio para obtener los juzgados");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		JuzgadoDTO juzgadoDTO = new JuzgadoDTO();
		List<JuzgadoItem> juzgadoItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchJudged() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchJudged() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchJudged() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				juzgadoItems = scsJuzgadoExtendsMapper.searchJudged(juzgadoItem, idInstitucion);

				LOGGER.info(
						"searchJudged() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				if (juzgadoItems != null) {
					juzgadoDTO.setJuzgadoItems(juzgadoItems);
				}
			}

		}
		LOGGER.info("searchJudged() -> Salida del servicio para obtener los juzgados");
		return juzgadoDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteJudged(JuzgadoDTO juzgadoDTO, HttpServletRequest request) {
		LOGGER.info("deleteJudged() ->  Entrada al servicio para eliminar juzgados");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteJudged() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteJudged() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (JuzgadoItem juzgadoItem : juzgadoDTO.getJuzgadoItems()) {

						// Eliminamos asociaciones procedimientos

						ScsJuzgadoprocedimientoExample scsJuzgadoprocedimientoExample = new ScsJuzgadoprocedimientoExample();
						scsJuzgadoprocedimientoExample.createCriteria().andIdinstitucionJuzgEqualTo(idInstitucion)
								.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()));

						LOGGER.info(
								"deleteJudged() / scsJuzgadoprocedimientoMapper.selectByExample() -> Entrada a scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

						List<ScsJuzgadoprocedimiento> procedimientosList = scsJuzgadoprocedimientoMapper
								.selectByExample(scsJuzgadoprocedimientoExample);

						LOGGER.info(
								"deleteJudged() / scsJuzgadoprocedimientoMapper.selectByExample() -> Salida de scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

						if (null != procedimientosList && procedimientosList.size() > 0) {

							for (ScsJuzgadoprocedimiento scsJuzgadoprocedimiento : procedimientosList) {

								scsJuzgadoprocedimiento.setFechabaja(new Date());
								scsJuzgadoprocedimiento.setFechamodificacion(new Date());
								scsJuzgadoprocedimiento.setUsumodificacion(usuario.getIdusuario());

								LOGGER.info(
										"deleteJudged() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoprocedimientoMapper para dar de baja a los procedimientos asociados al juzgado");

								response = scsJuzgadoprocedimientoMapper.updateByPrimaryKey(scsJuzgadoprocedimiento);

								LOGGER.info(
										"deleteJudged() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Salida de scsJuzgadoprocedimientoMapper para dar de baja a los procedimientos asociados al juzgado");

							}
						}

						// Damos baja juzgado

						ScsJuzgadoExample scsJuzgadoExample = new ScsJuzgadoExample();
						scsJuzgadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()));

						LOGGER.info(
								"deleteJudged() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar el juzgado");

						List<ScsJuzgado> juzgadosList = scsJuzgadoExtendsMapper.selectByExample(scsJuzgadoExample);

						LOGGER.info(
								"deleteJudged() / scsJuzgadoExtendsMapper.selectByExample() -> Salida de scsJuzgadoExtendsMapper para buscar el juzgado");

						if (null != juzgadosList && juzgadosList.size() > 0) {

							ScsJuzgado juzgado = juzgadosList.get(0);

							juzgado.setFechabaja(new Date());
							juzgado.setFechamodificacion(new Date());
							juzgado.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteJudged() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoExtendsMapper para dar de baja a un juzgado");

							response = scsJuzgadoExtendsMapper.updateByPrimaryKey(juzgado);

							LOGGER.info(
									"deleteJudged() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Salida de scsJuzgadoExtendsMapper para dar de baja a un juzgado");
						}
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("No se han eliminado los juzgados");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Se han eliminado los juzgados correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteJudged() -> Salida del servicio para eliminar juzgados");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO activateJudged(JuzgadoDTO juzgadoDTO, HttpServletRequest request) {

		LOGGER.info("activateJudged() ->  Entrada al servicio para dar de alta a grupos zona");

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
					"activateJudged() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateJudged() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (JuzgadoItem juzgadoItem : juzgadoDTO.getJuzgadoItems()) {

						// Damos alta juzgado

						ScsJuzgadoExample scsJuzgadoExample = new ScsJuzgadoExample();
						scsJuzgadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()))
								.andFechabajaIsNotNull();

						LOGGER.info(
								"activateJudged() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar el juzgado");

						List<ScsJuzgado> juzgadosList = scsJuzgadoExtendsMapper.selectByExample(scsJuzgadoExample);

						LOGGER.info(
								"activateJudged() / scsJuzgadoExtendsMapper.selectByExample() -> Salida de scsJuzgadoExtendsMapper para buscar el juzgado");

						if (null != juzgadosList && juzgadosList.size() > 0) {

							ScsJuzgado juzgado = juzgadosList.get(0);

							juzgado.setFechabaja(null);
							juzgado.setFechamodificacion(new Date());
							juzgado.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateJudged() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoExtendsMapper para dar de alta a un juzgado");

							response = scsJuzgadoExtendsMapper.updateByPrimaryKey(juzgado);

							LOGGER.info(
									"activateJudged() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Salida de scsJuzgadoExtendsMapper para dar de alta a un juzgado");

							// Eliminamos asociaciones procedimientos

							ScsJuzgadoprocedimientoExample scsJuzgadoprocedimientoExample = new ScsJuzgadoprocedimientoExample();
							scsJuzgadoprocedimientoExample.createCriteria().andIdinstitucionJuzgEqualTo(idInstitucion)
									.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()))
									.andFechabajaIsNotNull();

							LOGGER.info(
									"activateJudged() / scsJuzgadoprocedimientoMapper.selectByExample() -> Entrada a scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

							List<ScsJuzgadoprocedimiento> procedimientosList = scsJuzgadoprocedimientoMapper
									.selectByExample(scsJuzgadoprocedimientoExample);

							LOGGER.info(
									"activateJudged() / scsJuzgadoprocedimientoMapper.selectByExample() -> Salida de scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

							if (null != procedimientosList && procedimientosList.size() > 0) {

								for (ScsJuzgadoprocedimiento scsJuzgadoprocedimiento : procedimientosList) {

									scsJuzgadoprocedimiento.setFechabaja(null);
									scsJuzgadoprocedimiento.setFechamodificacion(new Date());
									scsJuzgadoprocedimiento.setUsumodificacion(usuario.getIdusuario());

									LOGGER.info(
											"activateJudged() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoprocedimientoMapper para dar de alta a los procedimientos asociados al juzgado");

									response = scsJuzgadoprocedimientoMapper
											.updateByPrimaryKey(scsJuzgadoprocedimiento);

									LOGGER.info(
											"activateJudged() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Salida de scsJuzgadoprocedimientoMapper para dar de alta a los procedimientos asociados al juzgado");

								}
							}

						}

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("No se han activado los juzgados");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Se han actualizado los juzgados correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateJudged() -> Salida del servicio para dar de alta a los juzgados");

		return updateResponseDTO;

	}

}
