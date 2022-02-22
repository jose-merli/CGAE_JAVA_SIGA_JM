package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.JuzgadoDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsJuzgado;
import org.itcgae.siga.db.entities.ScsJuzgadoExample;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimiento;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimientoExample;
import org.itcgae.siga.db.mappers.ScsJuzgadoprocedimientoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IBusquedaJuzgadosService;
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
	public JuzgadoDTO searchCourt(JuzgadoItem juzgadoItem, HttpServletRequest request) {
		LOGGER.info("searchCourt() -> Entrada al servicio para obtener los juzgados");

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
					"searchCourt() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCourt() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchCourt() / scsJuzgadoExtendsMapper.selectTipoSolicitud() -> Entrada a scsJuzgadoExtendsMapper para obtener los juzgados");

				//Tratamiento apostrofes
				juzgadoItem.setNombre(UtilidadesString.tratamientoApostrofes(juzgadoItem.getNombre()));
				
				juzgadoItems = scsJuzgadoExtendsMapper.searchCourt(juzgadoItem, idInstitucion);

				LOGGER.info(
						"searchCourt() / scsJuzgadoExtendsMapper.selectTipoSolicitud() -> Salida a scsJuzgadoExtendsMapper para obtener los juzgados");

				if (juzgadoItems != null) {
					juzgadoDTO.setJuzgadoItems(juzgadoItems);
				}
			}

		}
		LOGGER.info("searchCourt() -> Salida del servicio para obtener los juzgados");
		return juzgadoDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteCourt(JuzgadoDTO juzgadoDTO, HttpServletRequest request) {
		LOGGER.info("deleteCourt() ->  Entrada al servicio para eliminar juzgados");

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
					"deleteCourt() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteCourt() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (JuzgadoItem juzgadoItem : juzgadoDTO.getJuzgadoItems()) {

						// Eliminamos asociaciones procedimientos

						ScsJuzgadoprocedimientoExample scsJuzgadoprocedimientoExample = new ScsJuzgadoprocedimientoExample();
						scsJuzgadoprocedimientoExample.createCriteria().andIdinstitucionJuzgEqualTo(idInstitucion)
								.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()));

						LOGGER.info(
								"deleteCourt() / scsJuzgadoprocedimientoMapper.selectByExample() -> Entrada a scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

						List<ScsJuzgadoprocedimiento> procedimientosList = scsJuzgadoprocedimientoMapper
								.selectByExample(scsJuzgadoprocedimientoExample);

						LOGGER.info(
								"deleteCourt() / scsJuzgadoprocedimientoMapper.selectByExample() -> Salida de scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

						if (null != procedimientosList && procedimientosList.size() > 0) {

							for (ScsJuzgadoprocedimiento scsJuzgadoprocedimiento : procedimientosList) {

								scsJuzgadoprocedimiento.setFechabaja(new Date());
								scsJuzgadoprocedimiento.setFechamodificacion(new Date());
								scsJuzgadoprocedimiento.setUsumodificacion(usuario.getIdusuario());

								LOGGER.info(
										"deleteCourt() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoprocedimientoMapper para dar de baja a los procedimientos asociados al juzgado");

								response = scsJuzgadoprocedimientoMapper.updateByPrimaryKey(scsJuzgadoprocedimiento);

								LOGGER.info(
										"deleteCourt() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Salida de scsJuzgadoprocedimientoMapper para dar de baja a los procedimientos asociados al juzgado");

							}
						}

						// Damos baja juzgado

						ScsJuzgadoExample scsJuzgadoExample = new ScsJuzgadoExample();
						scsJuzgadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()));

						LOGGER.info(
								"deleteCourt() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar el juzgado");

						List<ScsJuzgado> juzgadosList = scsJuzgadoExtendsMapper.selectByExample(scsJuzgadoExample);

						LOGGER.info(
								"deleteCourt() / scsJuzgadoExtendsMapper.selectByExample() -> Salida de scsJuzgadoExtendsMapper para buscar el juzgado");

						if (null != juzgadosList && juzgadosList.size() > 0) {

							ScsJuzgado juzgado = juzgadosList.get(0);

							juzgado.setFechabaja(new Date());
							juzgado.setFechamodificacion(new Date());
							juzgado.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteCourt() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoExtendsMapper para dar de baja a un juzgado");

							response = scsJuzgadoExtendsMapper.updateByPrimaryKey(juzgado);

							LOGGER.info(
									"deleteCourt() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Salida de scsJuzgadoExtendsMapper para dar de baja a un juzgado");
						}
					}

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
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteCourt() -> Salida del servicio para eliminar juzgados");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO activateCourt(JuzgadoDTO juzgadoDTO, HttpServletRequest request) {

		LOGGER.info("activateCourt() ->  Entrada al servicio para dar de alta a grupos zona");

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
					"activateCourt() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateCourt() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
								"activateCourt() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar el juzgado");

						List<ScsJuzgado> juzgadosList = scsJuzgadoExtendsMapper.selectByExample(scsJuzgadoExample);

						LOGGER.info(
								"activateCourt() / scsJuzgadoExtendsMapper.selectByExample() -> Salida de scsJuzgadoExtendsMapper para buscar el juzgado");

						if (null != juzgadosList && juzgadosList.size() > 0) {

							ScsJuzgado juzgado = juzgadosList.get(0);

							juzgado.setFechabaja(null);
							juzgado.setFechamodificacion(new Date());
							juzgado.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateCourt() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoExtendsMapper para dar de alta a un juzgado");

							response = scsJuzgadoExtendsMapper.updateByPrimaryKey(juzgado);

							LOGGER.info(
									"activateCourt() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Salida de scsJuzgadoExtendsMapper para dar de alta a un juzgado");

							// Eliminamos asociaciones procedimientos

							ScsJuzgadoprocedimientoExample scsJuzgadoprocedimientoExample = new ScsJuzgadoprocedimientoExample();
							scsJuzgadoprocedimientoExample.createCriteria().andIdinstitucionJuzgEqualTo(idInstitucion)
									.andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()))
									.andFechabajaIsNotNull();

							LOGGER.info(
									"activateCourt() / scsJuzgadoprocedimientoMapper.selectByExample() -> Entrada a scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

							List<ScsJuzgadoprocedimiento> procedimientosList = scsJuzgadoprocedimientoMapper
									.selectByExample(scsJuzgadoprocedimientoExample);

							LOGGER.info(
									"activateCourt() / scsJuzgadoprocedimientoMapper.selectByExample() -> Salida de scsJuzgadoprocedimientoMapper para buscar los procedimientos asociados al juzgado");

							if (null != procedimientosList && procedimientosList.size() > 0) {

								for (ScsJuzgadoprocedimiento scsJuzgadoprocedimiento : procedimientosList) {

									scsJuzgadoprocedimiento.setFechabaja(null);
									scsJuzgadoprocedimiento.setFechamodificacion(new Date());
									scsJuzgadoprocedimiento.setUsumodificacion(usuario.getIdusuario());

									LOGGER.info(
											"activateCourt() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoprocedimientoMapper para dar de alta a los procedimientos asociados al juzgado");

									response = scsJuzgadoprocedimientoMapper
											.updateByPrimaryKey(scsJuzgadoprocedimiento);

									LOGGER.info(
											"activateCourt() / scsJuzgadoprocedimientoMapper.updateByPrimaryKey() -> Salida de scsJuzgadoprocedimientoMapper para dar de alta a los procedimientos asociados al juzgado");

								}
							}

						}

					}

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
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateCourt() -> Salida del servicio para dar de alta a los juzgados");

		return updateResponseDTO;

	}

}
