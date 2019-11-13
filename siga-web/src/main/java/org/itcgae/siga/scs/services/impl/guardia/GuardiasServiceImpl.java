package org.itcgae.siga.scs.services.impl.guardia;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.guardia.GuardiasDTO;
import org.itcgae.siga.DTO.scs.guardia.GuardiasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.guardia.ScsGuardiasturnoExtendsMapper;
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
				
				guardias = guardias.stream().map( it ->{
					it.setTipoDia(("Selección: Labor. "+it.getDiasLab()+", Fest. "+it.getDiasFes()).replace("null", ""));
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
								"deleteGuardias() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"deleteGuardias() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(new Date());
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteGuardias() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"deleteGuardias() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
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
								"activateGuardias() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"activateGuardias() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(null);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateGuardias() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"activateGuardias() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
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

		LOGGER.info("activateGuardias() -> Salida del servicio para dar de alta a las prisiones");

		return updateResponseDTO;

	}

	@Transactional
	@Override
	public GuardiasItem detalleGuardia(String idGuardia, HttpServletRequest request) {

		LOGGER.info("getGuardia() ->  Entrada al servicio para obtener una guardia");

		GuardiasItem guardiaItem = null;
		
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
							"getGuardia() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la guardia");

						List<GuardiasItem> guardiasList = scsGuardiasturnoExtendsMapper.detalleGuardia(idGuardia, idInstitucion.toString());
						if(guardiasList != null && guardiasList.size() > 0)
							guardiaItem = guardiasList.get(0);
						LOGGER.info(
								"getGuardia() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		


		LOGGER.info("getGuardia() -> Salida del servicio para dar de alta a las prisiones");

		return guardiaItem;

	}

}
