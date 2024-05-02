package org.itcgae.siga.scs.services.impl.justiciables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.scs.PersonaTelefonoDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsTelefonospersona;
import org.itcgae.siga.db.entities.ScsTelefonospersonaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRolesJusticiablesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTelefonosPersonaExtendsMapper;
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
	private ScsTelefonosPersonaExtendsMapper scsTelefonospersonaMapper;
	

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

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
	public PersonaTelefonoDTO getTelefonos(HttpServletRequest request) {

		LOGGER.info("getTelefonos() -> Entrada al servicio para obtener telefonos personas");

		PersonaTelefonoDTO justiciableTelefonoDTO = new PersonaTelefonoDTO();

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
		List<StringDTO> idPersonaJusticiables = new ArrayList<StringDTO>();
		List<JusticiableBusquedaItem> justiciablesItems = new ArrayList<JusticiableBusquedaItem>();
		Error error = new Error();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

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
					// Solamente busca el valor de un justiciable
					if (justiciableBusquedaItem.getIdPersona() != null
							&& justiciableBusquedaItem.getIdPersona() != "") {

						StringDTO idPersona = new StringDTO();
						idPersona.setValor(justiciableBusquedaItem.getIdPersona());
						idPersonaJusticiables.add(idPersona);
						// Busca los justiciables segun los filtros introducidos en la busqueda general
						// de justiciables
					} else {

						GenParametrosExample genParametrosExample = new GenParametrosExample();

						genParametrosExample.createCriteria().andModuloEqualTo("SCS")
								.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
								.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

						genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

						LOGGER.info(
								"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

						tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

						LOGGER.info(
								"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

						
						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");

						if (tamMax != null) {
							tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
						} else {
							tamMaximo = null;
						}
						
						idPersonaJusticiables = scsPersonajgExtendsMapper.searchIdPersonaJusticiables(justiciableBusquedaItem, idInstitucion, tamMaximo);

						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Salida a scsPersonajgExtendsMapper para obtener las personas justiciables");

					}

					if (idPersonaJusticiables != null && idPersonaJusticiables.size() > 0) {

						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

						justiciablesItems = scsPersonajgExtendsMapper.searchJusticiables(idPersonaJusticiables,idInstitucion);

						LOGGER.info(
								"searchJusticiables() / scsPersonajgExtendsMapper.searchJusticiables() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");

						if(justiciablesItems != null && tamMaximo != null  && justiciablesItems.size() > tamMaximo) {
							error.setCode(200);
							error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
							justiciableBusquedaDTO.setError(error);
							justiciablesItems.remove(justiciablesItems.size()-1);
						}
						
						justiciableBusquedaDTO.setJusticiableBusquedaItem(justiciablesItems);
					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				}

			}

		}
		LOGGER.info("searchJusticiables() -> Salida del servicio para obtener los justiciables");
		return justiciableBusquedaDTO;
	}

}