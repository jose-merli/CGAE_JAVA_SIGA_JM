package org.itcgae.siga.scs.services.impl.justiciables;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTO.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsTelefonospersona;
import org.itcgae.siga.db.entities.ScsTelefonospersonaExample;
import org.itcgae.siga.db.mappers.ScsTelefonospersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRolesJusticiablesExtendsMapper;
import org.itcgae.siga.scs.services.justiciables.IBusquedaJusticiablesService;
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
	private ScsTelefonospersonaMapper scsTelefonospersonaMapper;
	
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
	public JusticiableTelefonoDTO getTelefonos(HttpServletRequest request) {

		LOGGER.info("getTelefonos() -> Entrada al servicio para obtener telefonos personas");

		JusticiableTelefonoDTO justiciableTelefonoDTO = new JusticiableTelefonoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getTelefonos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getTelefonos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				ScsTelefonospersonaExample scsTelefonospersonaExample = new ScsTelefonospersonaExample();
				scsTelefonospersonaExample.createCriteria()
				.andIdinstitucionEqualTo(idInstitucion);
				
				scsTelefonospersonaExample.setOrderByClause("NOMBRETELEFONO asc");
				
				List<ScsTelefonospersona> telefonospersonaList = scsTelefonospersonaMapper.selectByExample(scsTelefonospersonaExample);
				
				if (telefonospersonaList != null && telefonospersonaList.size() > 0) {
					justiciableTelefonoDTO.setTelefonospersona(telefonospersonaList);
				}
			}
		}
		
		LOGGER.info("getTelefonos() -> Salida del servicio para obtener telefonos personas");

		return justiciableTelefonoDTO;
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
		List<JusticiableBusquedaItem> justiciableBusquedaItems = new ArrayList<JusticiableBusquedaItem>();

		if (idInstitucion != null) {
		
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchJusticiables() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchJusticiables() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchJusticiables() / scsPretensionExtendsMapper.searchJusticiables() -> Entrada a scsPretensionExtendsMapper para obtener justiciables");

			//??

				LOGGER.info(
						"searchJusticiables() / scsPretensionExtendsMapper.searchJusticiables() -> Salida a scsPretensionExtendsMapper para obtener justiciables");

				if (justiciableBusquedaItems != null) {
					justiciableBusquedaDTO.setJusticiableBusquedaItem(justiciableBusquedaItems);
				}
			}

		}
		LOGGER.info("searchJusticiables() -> Salida del servicio para obtener los justiciables");
		return justiciableBusquedaDTO;
	}

}