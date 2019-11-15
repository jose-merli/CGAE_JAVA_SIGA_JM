package org.itcgae.siga.scs.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRolesJusticiablesExtendsMapper;
import org.itcgae.siga.scs.service.IBusquedaJusticiablesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaJusticiableServiceImpl implements IBusquedaJusticiablesService {

	private Logger LOGGER = Logger.getLogger(BusquedaJusticiableServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsRolesJusticiablesExtendsMapper scsRolesJusticiablesExtendsMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Override
	public ComboDTO getComboRoles(HttpServletRequest request) {
		LOGGER.info("getComboRoles() -> Entrada al servicio para obtener combo roles justiciables");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboRoles() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboRoles() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getComboRoles() / scsRolesJusticiablesExtendsMapper.getComboRoles() -> Entrada a scsRolesJusticiablesExtendsMapper para obtener los roles justiciables");

				List<ComboItem> comboItems = scsRolesJusticiablesExtendsMapper.getComboRoles(usuario.getIdlenguaje());

				LOGGER.info(
						"getComboRoles() / scsRolesJusticiablesExtendsMapper.getComboRoles() -> Salida a scsRolesJusticiablesExtendsMapper para obtener los roles justiciables");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboRoles() -> Salida del servicio para obtener combo roles justiciables");
		return combo;
	}

	@Override
	public JusticiableBusquedaDTO searchJusticiables(JusticiableBusquedaItem justiciableBusquedaItem,
			HttpServletRequest request) {

		LOGGER.info("searchJusticiables() -> Entrada al servicio para obtener los justiciables");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		JusticiableBusquedaDTO justiciableBusquedaDTO = new JusticiableBusquedaDTO();
		List<StringDTO> idPersonaJusticiables = new ArrayList<StringDTO>();
		List<JusticiableBusquedaItem> justiciablesItems = new ArrayList<JusticiableBusquedaItem>();
		Error error = new Error();

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchJusticiables() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchJusticiables() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				try {

					if(justiciableBusquedaItem.getIdPersona() != null && justiciableBusquedaItem.getIdPersona() != "") {
						
						StringDTO idPersona = new StringDTO();
						idPersona.setValor(justiciableBusquedaItem.getIdPersona());
						idPersonaJusticiables.add(idPersona);
						
					}else {
						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");

						idPersonaJusticiables = scsPersonajgExtendsMapper.searchIdPersonaJusticiables(justiciableBusquedaItem, idInstitucion);

						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Salida a scsPersonajgExtendsMapper para obtener las personas justiciables");
					}
					
					if (idPersonaJusticiables != null && idPersonaJusticiables.size() > 0) {

						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

						justiciablesItems = scsPersonajgExtendsMapper.searchJusticiables(idPersonaJusticiables, idInstitucion);

						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchJusticiables() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");



						justiciableBusquedaDTO.setJusticiableBusquedaItem(justiciablesItems);
					}

				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
				}

			}

		}
		LOGGER.info("searchJusticiables() -> Salida del servicio para obtener los justiciables");
		return justiciableBusquedaDTO;
	}

}