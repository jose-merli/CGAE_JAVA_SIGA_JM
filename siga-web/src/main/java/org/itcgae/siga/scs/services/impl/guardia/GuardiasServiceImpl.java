package org.itcgae.siga.scs.services.impl.guardia;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuardiasServiceImpl implements GuardiasService {

	private Logger LOGGER = Logger.getLogger(GuardiasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Override
	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GuardiasDTO guardiaDTO = new GuardiasDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				List<GuardiasItem> guardias = scsGuardiasturnoExtendsMapper.searchGuardias(guardiasItem,
						idInstitucion.toString(), usuarios.get(0).getIdlenguaje());

				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLab() + ", Fest. " + it.getSeleccionFes())
							.replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				guardiaDTO.setGuardiaItems(guardias);

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			}
		}
		return guardiaDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request) {

		LOGGER.info("deleteGuardias() ->  Entrada al servicio para eliminar prisiones");

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
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample scsGuardiasExample = new ScsGuardiasturnoExample();
						scsGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdguardiaEqualTo(Integer.valueOf(guardiaItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiaItem.getIdTurno()));

						LOGGER.info(
								"deleteGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a scsGuardiasturnoExtendsMapper para buscar la guardia");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"deleteGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de scsGuardiasturnoExtendsMapper para buscar la guardia");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(new Date());
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Entrada a scsGuardiasturnoExtendsMapper para dar de baja a una guardia");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"deleteGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Salida de scsGuardiasturnoExtendsMapper para dar de baja a una guardia");
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("deleteGuardias() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;

	}

	@Override
	@Transactional
	public UpdateResponseDTO activateGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request) {

		LOGGER.info("activateGuardias() ->  Entrada al servicio para dar de alta a prisiones");

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
					"activateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample scsGuardiasExample = new ScsGuardiasturnoExample();
						scsGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdguardiaEqualTo(Integer.valueOf(guardiaItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiaItem.getIdTurno()));

						LOGGER.info(
								"activateGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a scsGuardiasturnoExtendsMapper para buscar la guardia");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"activateGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de scsGuardiasturnoExtendsMapper para buscar la guardia");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(null);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Entrada a scsGuardiasturnoExtendsMapper para dar de baja a una prision");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"activateGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Salida de scsGuardiasturnoExtendsMapper para dar de baja a una prision");
						}

					}

				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("activateGuardias() -> Salida del servicio para dar de alta las guardias");

		return updateResponseDTO;

	}

	@Transactional
	@Override
	public GuardiasItem detalleGuardia(String idGuardia, HttpServletRequest request) {

		LOGGER.info("getGuardia() ->  Entrada al servicio para obtener una guardia");

		GuardiasItem guardiaItem = new GuardiasItem();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activagetGuardiateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"getGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a la busqueda de la guardia");

					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(idGuardia))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper.selectByExample(example);
					if (guardiasList != null && guardiasList.size() > 0) {

						LOGGER.info(
								"getGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Mapeamos lo recogido");
						// Mapeo para Datos Generales
						ScsGuardiasturno guardia = guardiasList.get(0);
						guardiaItem.setNombre(guardia.getNombre());
						guardiaItem.setDescripcion(guardia.getDescripcion());
						if (guardia.getIdtipoguardia() != null)
							guardiaItem.setTipoGuardia(guardia.getIdtipoguardia() + "");
						if (guardia.getEnviocentralita() != null)
							guardiaItem.setEnvioCentralita(guardia.getEnviocentralita() + "");
						guardiaItem.setDescripcionFacturacion(guardia.getDescripcionfacturacion());
						guardiaItem.setDescripcionPago(guardia.getDescripcionpago());
						guardiaItem.setIdTurno(guardia.getIdturno() + "");
						guardiaItem.setIdGuardia(guardia.getIdguardia() + "");

						// Configuracion de cola
						guardiaItem.setPorGrupos(guardia.getPorgrupos());
						if (guardia.getIdordenacioncolas() != null)
							guardiaItem.setIdOrdenacionColas(guardia.getIdordenacioncolas() + "");
						if (guardia.getDiasperiodo() != null)
							guardiaItem.setDiasPeriodo(guardia.getDiasperiodo() + "");
						guardiaItem.setNombre(guardia.getNombre());

						// Configuracion calendarios
						if (guardia.getDiasguardia() != null)
							guardiaItem.setDiasGuardia(guardia.getDiasguardia() + "");
						if (guardia.getDiasperiodo() != null)
							guardiaItem.setDiasPeriodo(guardia.getDiasperiodo() + "");
						guardiaItem.setTipoDiasGuardia(guardia.getTipodiasguardia());
						guardiaItem.setTipoDiasPeriodo(guardia.getTipodiasperiodo());
						if (guardia.getDiasseparacionguardias() != null)
							guardiaItem.setDiasSeparacionGuardias(guardia.getDiasseparacionguardias() + "");
						guardiaItem.setSeleccionLab(guardia.getSeleccionlaborables());
						guardiaItem.setSeleccionFes(guardia.getSeleccionfestivos());

						// Cola de guardia
						if (guardia.getIdpersonaUltimo() != null)
							guardiaItem.setIdPersonaUltimo(guardia.getIdpersonaUltimo() + "");
						if (guardia.getIdordenacioncolas() != null)
							guardiaItem.setIdOrdenacionColas(guardia.getIdordenacioncolas() + "");

					}
					LOGGER.info(
							"getGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de la busqueda de guardia");

				} catch (Exception e) {
					LOGGER.error(e);
				}
			}

		}

		LOGGER.info("getGuardia() -> Salida del servicio para recoger una guardia");

		return guardiaItem;

	}

}
