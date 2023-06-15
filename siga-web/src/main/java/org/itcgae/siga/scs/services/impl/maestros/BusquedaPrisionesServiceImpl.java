package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.PrisionDTO;
import org.itcgae.siga.DTOs.scs.PrisionItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPrision;
import org.itcgae.siga.db.entities.ScsPrisionExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrisionExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IBusquedaPrisionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusquedaPrisionesServiceImpl implements IBusquedaPrisionesService {

	private Logger LOGGER = Logger.getLogger(BusquedaPrisionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsPrisionExtendsMapper scsPrisionExtendsMapper;


	@Override
	public PrisionDTO searchPrisiones(PrisionItem prisionItem, HttpServletRequest request) {
		LOGGER.info("searchPrisiones() -> Entrada al servicio para obtener prisiones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PrisionDTO prisionDTO = new PrisionDTO();
		List<PrisionItem> prisionItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchPrisiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchPrisiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchPrisiones() / scsPrisionExtendsMapper.searchPrisiones() -> Entrada a scsPrisionExtendsMapper para obtener las prisiones");

				prisionItemList = scsPrisionExtendsMapper.searchPrisiones(prisionItem, idInstitucion);

				LOGGER.info(
						"searchPrisiones() / scsPrisionExtendsMapper.searchPrisiones() -> Salida a scsPrisionExtendsMapper para obtener las prisiones");

				if (prisionItemList != null) {
					prisionDTO.setPrisionItems(prisionItemList);
				}
			}

		}
		LOGGER.info("searchPrisiones() -> Salida del servicio para obtener prisiones");
		return prisionDTO;
	}


	@Override
	@Transactional
	public UpdateResponseDTO deletePrisiones(PrisionDTO prisionDTO, HttpServletRequest request) {

		LOGGER.info("deletePrisiones() ->  Entrada al servicio para eliminar prisiones");

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
					"deletePrisiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deletePrisiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (PrisionItem prisionItem : prisionDTO.getPrisionItems()) {

						ScsPrisionExample scsPrisionExample = new ScsPrisionExample();
						scsPrisionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdprisionEqualTo(Long.valueOf(prisionItem.getIdPrision()));

						LOGGER.info(
								"deletePrisiones() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsPrision> prisionesList = scsPrisionExtendsMapper.selectByExample(scsPrisionExample);

						LOGGER.info(
								"deletePrisiones() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != prisionesList && prisionesList.size() > 0) {

							ScsPrision prision = prisionesList.get(0);

							prision.setFechabaja(new Date());
							prision.setFechamodificacion(new Date());
							prision.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deletePrisiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

							response = scsPrisionExtendsMapper.updateByPrimaryKey(prision);

							LOGGER.info(
									"deletePrisiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
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

		LOGGER.info("deletePrisiones() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;
	
	}


	@Override
	@Transactional
	public UpdateResponseDTO activatePrisiones(PrisionDTO prisionDTO, HttpServletRequest request) {


		LOGGER.info("activatePrisiones() ->  Entrada al servicio para dar de alta a prisiones");

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
					"activatePrisiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activatePrisiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (PrisionItem prisionItem : prisionDTO.getPrisionItems()) {

						ScsPrisionExample scsPrisionExample = new ScsPrisionExample();
						scsPrisionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdprisionEqualTo(Long.valueOf(prisionItem.getIdPrision()));

						LOGGER.info(
								"activatePrisiones() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsPrision> prisionesList = scsPrisionExtendsMapper.selectByExample(scsPrisionExample);

						LOGGER.info(
								"activatePrisiones() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != prisionesList && prisionesList.size() > 0) {

							ScsPrision prision = prisionesList.get(0);

							prision.setFechabaja(null);
							prision.setFechamodificacion(new Date());
							prision.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activatePrisiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de alta a una prision");

							response = scsPrisionExtendsMapper.updateByPrimaryKey(prision);

							LOGGER.info(
									"activatePrisiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de alta a una prision");

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

		LOGGER.info("activateCourt() -> Salida del servicio para dar de alta a las prisiones");

		return updateResponseDTO;

	
	}

}
