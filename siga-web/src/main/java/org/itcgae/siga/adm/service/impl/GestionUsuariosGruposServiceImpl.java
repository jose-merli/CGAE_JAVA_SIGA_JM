package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.RolPerfilDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoEditDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoItem;
import org.itcgae.siga.DTOs.adm.UsuarioGruposDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmPerfilKey;
import org.itcgae.siga.db.entities.AdmPerfilRol;
import org.itcgae.siga.db.entities.AdmPerfilRolExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmRolExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmPerfilMapper;
import org.itcgae.siga.db.mappers.AdmPerfilRolMapper;
import org.itcgae.siga.db.mappers.AdmRolMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosEfectivoPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionUsuariosGruposServiceImpl implements IGestionUsuariosGruposService {

	private Logger LOGGER = Logger.getLogger(GestionUsuariosGruposServiceImpl.class);

	@Autowired
	private AdmRolMapper admRolMapper;

	@Autowired
	private AdmPerfilRolMapper admPerfilRolMapper;

	@Autowired
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private AdmPerfilMapper admPerfilMapper;

	@Autowired
	private AdmUsuariosEfectivoPerfilExtendsMapper admUsuariosEfectivoPerfilExtendsMapper;

	/***
	 * Get the different users roles Return ComboDTO (id, value) where id is the
	 * identificator rol and value is the description of these rol.
	 */
	@Override
	public ComboDTO getUsersRole() {
		LOGGER.info("getUsersRole() -> Entrada al servicio para los diferentes roles de usuario");
		AdmRolExample example = new AdmRolExample();
		List<AdmRol> roles = new ArrayList<AdmRol>();
		ComboDTO comboDto = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		String unblocked = "N";

		example.createCriteria().andBloqueadoEqualTo(String.valueOf(unblocked)).andFechaBajaIsNull();
		example.setOrderByClause("DESCRIPCION ASC");
		LOGGER.info(
				"getUsersRole() / admRolMapper.selectByExample() -> Entrada a admRolMapper para obtener listado de roles de usuario");
		roles = admRolMapper.selectByExample(example);
		LOGGER.info(
				"getUsersRole() / admRolMapper.selectByExample() -> Salida de admRolMapper para obtener listado de roles de usuario");

		if (roles != null && roles.size() > 0) {
			ComboItem comboItem = new ComboItem();
			comboItem.setValue("");
			comboItem.setLabel("");
			comboItems.add(comboItem);
			for (AdmRol admRol : roles) {
				comboItem = new ComboItem();
				comboItem.setValue(admRol.getIdrol());
				comboItem.setLabel(admRol.getDescripcion());
				comboItems.add(comboItem);
			}
		} else {
			LOGGER.warn("getUsersRole() / admRolMapper.selectByExample() -> No existen roles de usuario");
		}

		comboDto.setCombooItems(comboItems);
		LOGGER.info("getUsersRole() -> Salida del servicio para los diferentes roles de usuario");
		return comboDto;
	}

	/***
	 * Get the different users profiles. Return object ComboDto (id, description)
	 * sorted by description(natural String order) and where each element id is
	 * unique for a description
	 */
	@Override
	public ComboDTO getUsersProfile(HttpServletRequest request) {
		LOGGER.info("getUsersProfile() -> Entrada al servicio para los diferentes perfiles de usuario");
		AdmPerfilExample example = new AdmPerfilExample();
		HashMap<String, String> hashProfiles = new HashMap<String, String>();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();
		ComboDTO comboDto = new ComboDTO();

		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		example.createCriteria().andFechaBajaIsNull().andIdinstitucionEqualTo(idInstitucion);
		example.setOrderByClause("DESCRIPCION ASC");
		example.setDistinct(true);
		LOGGER.info(
				"getUsersProfile() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> Entrada a admPerfilExtendsMapper para obtener listado de perfiles de usuario");
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);
		LOGGER.info(
				"getUsersProfile() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> Salida de admPerfilExtendsMapper para obtener listado de perfiles de usuario");

		if (profiles != null && profiles.size() > 0) {
			for (AdmPerfil admPerfil : profiles) {
				hashProfiles.put(admPerfil.getIdperfil(), admPerfil.getDescripcion());

			}

			hashProfiles.forEach((id, description) -> {
				ComboItem comboItem2 = new ComboItem();
				comboItem2.setValue(id);
				comboItem2.setLabel(description);
				comboItems.add(comboItem2);
			});

			// ordenar por descripcion
			Comparator<ComboItem> orderBydDescription = new Comparator<ComboItem>() {
				public int compare(ComboItem combo1, ComboItem combo2) {
					return String.valueOf(combo1.getLabel()).compareTo(String.valueOf(combo2.getLabel()));
				}
			};

			comboItems.sort(orderBydDescription);

		} else {
			LOGGER.warn(
					"getUsersProfile() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> No existen perfiles de usuario");
		}

		comboDto.setCombooItems(comboItems);
		LOGGER.info("getUsersProfile() -> Entrada al servicio para los diferentes perfiles de usuario");
		return comboDto;
	}

	/***
	 * Get users information, showing them on different pages on web page. This
	 * information can be searched by a web user using different parameters. Call
	 * the mapper to get them. Return the users information.
	 */
	@Override
	public UsuarioDTO getUsersSearch(int numPagina, UsuarioRequestDTO usuarioRequestDTO, HttpServletRequest request) {
		LOGGER.info("getUsersSearch() -> Entrada al servicio para búsqueda de usuarios con filtros");
		List<UsuarioItem> usuarioItems = new ArrayList<UsuarioItem>();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		usuarioRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
		LOGGER.info(
				"getUsersSearch() / admUsuariosExtendsMapper.getUsersByFilter() -> Entrada a admUsuariosExtendsMapper para obtener lista de usuarios por el filtro usado");
		usuarioItems = admUsuariosExtendsMapper.getUsersByFilter(numPagina, usuarioRequestDTO);
		LOGGER.info(
				"getUsersSearch() / admUsuariosExtendsMapper.getUsersByFilter() -> Salida de admUsuariosExtendsMapper para obtener lista de usuarios por el filtro usado");

		if (usuarioItems != null && usuarioItems.size() > 0) {
			for (UsuarioItem usuarioItem : usuarioItems) {
				if (null != usuarioItem.getPerfil() && !usuarioItem.getPerfil().equalsIgnoreCase("")) {
					usuarioItem.setPerfiles(usuarioItem.getPerfil().split("; "));
				}
			}
			usuarioDTO.setUsuarioItem(usuarioItems);
		} else {
			LOGGER.warn(
					"getUsersSearch() / admUsuariosExtendsMapper.getUsersByFilter() -> No se devuelven usuarios para el filtro usado");
		}

		LOGGER.info("getUsersSearch() -> Salida del servicio para búsqueda de usuarios con filtros");
		return usuarioDTO;
	}

	@Override
	public UpdateResponseDTO updateUsers(UsuarioUpdateDTO usuarioUpdateDTO, HttpServletRequest request) {
		LOGGER.info("updateUsers() -> Entrada al servicio para actualizar datos de usuario");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int response1 = 1;
		int response2 = 0;
		int response3 = 0;
		int responseborrado = 1;
		boolean serviceOK = true;

		List<String> gruposAborrar = new ArrayList<String>();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		usuarioUpdateDTO.setIdInstitucion(String.valueOf(idInstitucion));

		AdmUsuariosExample exampleUsuarios1 = new AdmUsuariosExample();

		exampleUsuarios1.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info(
				"createUsers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios1 = admUsuariosExtendsMapper.selectByExample(exampleUsuarios1);
		LOGGER.info(
				"createUsers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		// pone a activo el usuario si no lo estaba
		LOGGER.debug("updateUsers() -> Pone activo al usuario si no lo estaba");
		if (!usuarioUpdateDTO.getActivo().equalsIgnoreCase("")
				|| !usuarioUpdateDTO.getCodigoExterno().equalsIgnoreCase("")) {
			LOGGER.info(
					"updateUsers() / admUsuariosExtendsMapper.updateUsersAdmUserTable() -> Entrada a admUsuariosExtendsMapper para poner un usuario a activo");
			response1 = admUsuariosExtendsMapper.updateUsersAdmUserTable(usuarioUpdateDTO);
			LOGGER.info(
					"updateUsers() / admUsuariosExtendsMapper.updateUsersAdmUserTable() -> Salida de admUsuariosExtendsMapper para poner un usuario a activo");
			if (response1 == 0) {
				LOGGER.warn(
						"updateUsers() / admUsuariosExtendsMapper.updateUsersAdmUserTable() -> No se ha podido poner un usuario a activo");
				serviceOK = false;
			}
		}

		// comprobar perfiles a los que pertenece el usuario antes de modificarlos
		LOGGER.info(
				"updateUsers() / admUsuariosEfectivoPerfilExtendsMapper.getPerfilesUsuario() -> Entrada a admUsuariosEfectivoPerfilExtendsMapper para comprobar perfiles a los que pertenece un usuario");
		comboItems = admUsuariosEfectivoPerfilExtendsMapper.getPerfilesUsuario(usuarioUpdateDTO,
				String.valueOf(idInstitucion));
		LOGGER.info(
				"updateUsers() / admUsuariosEfectivoPerfilExtendsMapper.getPerfilesUsuario() -> Salida de admUsuariosEfectivoPerfilExtendsMapper para comprobar perfiles a los que pertenece un usuario");

		for (int i = 0; i < comboItems.size(); i++) {
			gruposAborrar.add(comboItems.get(i).getLabel());
		}
		// obtener los perfiles a borrar que ahora ya no pertenecen al usuario
		for (int i = 0; i < usuarioUpdateDTO.getIdGrupo().length; i++) {
			if (gruposAborrar.contains(usuarioUpdateDTO.getIdGrupo()[i]))
				gruposAborrar.remove(usuarioUpdateDTO.getIdGrupo()[i]);
		}

		// borrar los perfiles que ya no pertenecen al usuario
		for (int i = 0; i < gruposAborrar.size(); i++) {

			AdmUsuariosEfectivosPerfil record = new AdmUsuariosEfectivosPerfil();
			record.setFechaBaja(new Date()); // fecha_baja será la la actual al dar de baja un perfil de un usuario
			record.setUsumodificacion(usuarios1.get(0).getIdusuario());

			AdmUsuariosEfectivosPerfilExample example = new AdmUsuariosEfectivosPerfilExample();
			example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdperfilEqualTo(gruposAborrar.get(i))
					.andIdrolEqualTo(usuarioUpdateDTO.getRol())
					.andIdusuarioEqualTo(Integer.valueOf(usuarioUpdateDTO.getIdUsuario()));
			LOGGER.info(
					"updateUsers() / admUsuariosEfectivoPerfilExtendsMapper.updateByExampleSelective() -> Entrada a admUsuariosEfectivoPerfilExtendsMapper para borrar perfiles que ya no pertenecen al usuario");
			responseborrado = admUsuariosEfectivoPerfilExtendsMapper.updateByExampleSelective(record, example);
			LOGGER.info(
					"updateUsers() / admUsuariosEfectivoPerfilExtendsMapper.updateByExampleSelective() -> Salida de admUsuariosEfectivoPerfilExtendsMapper para borrar perfiles que ya no pertenecen al usuario");

			if (responseborrado == 0) {
				serviceOK = false;
			}

		}

		// actualizar los perfiles nuevos
		if (null != usuarioUpdateDTO.getIdGrupo() && usuarioUpdateDTO.getIdGrupo().length > 0) {
			for (int i = 0; i < usuarioUpdateDTO.getIdGrupo().length; i++) {

				// mete el grupo (perfil) actual en su variable grupo, que no se usa para nada
				usuarioUpdateDTO.setGrupo(usuarioUpdateDTO.getIdGrupo()[i]);

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));

				// Buscamos el perfil de un usuario para ver si ya existe
				LOGGER.debug("updateUsers()  -> Buscamos el perfil de un usuario para ver si ya existe");
				LOGGER.info(
						"updateUsers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para búsqueda de usuario por dni e institucion");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"updateUsers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapperBúsqueda para búsqueda de usuario por dni e institucion");
				AdmUsuarios usuario = usuarios.get(0);
				AdmPerfilKey key = new AdmPerfilKey();
				key.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
				key.setIdperfil(usuarioUpdateDTO.getGrupo());
				LOGGER.info(
						"updateUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Entrada a admPerfilExtendsMapper para buscar si existe el perfil de un usuario");
				AdmPerfil perfil = admPerfilExtendsMapper.selectByPrimaryKey(key);
				LOGGER.info(
						"updateUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Salida de admPerfilExtendsMapper para buscar si existe el perfil de un usuario");

				// En caso de que no exista un perfil creamos la relación entre el usuario y el
				// perfil => tabla admPerfil
				LOGGER.debug(
						"updateUsers()  -> En caso de que no exista un perfil creamos la relación entre el usuario y el perfil");
				if (null == perfil) {
					AdmPerfilExample keyPerfil = new AdmPerfilExample();
					keyPerfil.setDistinct(Boolean.TRUE);
					keyPerfil.createCriteria().andIdperfilEqualTo(usuarioUpdateDTO.getGrupo());
					LOGGER.info(
							"updateUsers() / admPerfilExtendsMapper.selectByExample() -> Entrada a admPerfilExtendsMapper para buscar la descripción de un perfil de un usuario");
					List<AdmPerfil> perfilExistente = admPerfilExtendsMapper.selectByExample(keyPerfil);
					LOGGER.info(
							"updateUsers() / admPerfilExtendsMapper.selectByExample() -> Salida de admPerfilExtendsMapper para buscar la descripción de un perfil de un usuario");
					AdmPerfil record = new AdmPerfil();
					record.setUsumodificacion(usuario.getIdusuario());
					record.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
					record.setIdperfil(usuarioUpdateDTO.getGrupo());
					record.setFechamodificacion(new Date());
					record.setNivelperfil(new Long(0));
					record.setDescripcion(perfilExistente.get(0).getDescripcion());
					// Se guarda el perfil para la institucion
					LOGGER.debug("updateUsers()  -> Se guarda el perfil para la institución");
					LOGGER.info(
							"updateUsers() / admPerfilExtendsMapper.insert() -> Entrada a admPerfilExtendsMapper para añadir una relación entre usuario y perfil");
					response2 = admPerfilExtendsMapper.insert(record);
					LOGGER.info(
							"updateUsers() / admPerfilExtendsMapper.insert() -> Salida de admPerfilExtendsMapper para añadir una relación entre usuario y perfil");
					// Si una inserción de un registro da fallo, la respuesta del servicio deberá
					// ser KO
					if (response2 == 0) {
						LOGGER.warn(
								"updateUsers() / admPerfilExtendsMapper.insert() -> No se ha podido añadir una relación entre usuario y perfil");
						serviceOK = false;
					}
				}

				AdmUsuariosEfectivosPerfilExample keyUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();
				keyUsuarioPerfil.createCriteria()
						.andIdinstitucionEqualTo(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()))
						.andIdusuarioEqualTo(Integer.valueOf(usuarioUpdateDTO.getIdUsuario()))
						.andIdrolEqualTo(usuarioUpdateDTO.getRol()).andIdperfilEqualTo(usuarioUpdateDTO.getGrupo());

				LOGGER.info(
						"updateUsers() / admUsuariosEfectivoPerfilMapper.selectByExample() -> Entrada a admUsuariosEfectivoPerfilExtendsMapper para comprobar si existe relación entre usuario y perfil en usuariosEfectivos");
				List<AdmUsuariosEfectivosPerfil> usuarioPerfil = admUsuariosEfectivoPerfilExtendsMapper
						.selectByExample(keyUsuarioPerfil);
				LOGGER.info(
						"updateUsers() / admUsuariosEfectivoPerfilMapper.selectByExample() -> Salida de admUsuariosEfectivoPerfilExtendsMapper para comprobar si existe relación entre usuario y perfil en usuariosEfectivos");

				// si no hay relacion entre usuario y perfil => actualizar tabla
				// adm_Usuarios_Efectivos_Perfil
				if (usuarioPerfil.isEmpty()) { // con null == usuarioPerfil no servia. Nunca era nulo
					AdmUsuariosEfectivosPerfil recordUsuarioEfectivo = new AdmUsuariosEfectivosPerfil();
					recordUsuarioEfectivo.setUsumodificacion(usuario.getIdusuario());
					recordUsuarioEfectivo.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
					recordUsuarioEfectivo.setIdperfil(usuarioUpdateDTO.getGrupo());
					recordUsuarioEfectivo.setFechamodificacion(new Date());
					recordUsuarioEfectivo.setIdrol(usuarioUpdateDTO.getRol());
					recordUsuarioEfectivo.setIdusuario(Integer.valueOf(usuarioUpdateDTO.getIdUsuario()));
					recordUsuarioEfectivo.setFechaBaja(null);
					// Se guarda el usuario efectivoPerfil
					LOGGER.info(
							"updateUsers() / admUsuariosEfectivoPerfilMapper.insert() -> Entrada a admUsuariosEfectivoPerfilMapper para añadir una relación de perfil-usuario en tabla admUsuariosEfectivoPerfil");
					response3 = admUsuariosEfectivoPerfilExtendsMapper.insert(recordUsuarioEfectivo);
					LOGGER.info(
							"updateUsers() / admUsuariosEfectivoPerfilMapper.insert() -> Salida de admUsuariosEfectivoPerfilMapper para añadir una relación de perfil-usuario en tabla admUsuariosEfectivoPerfil");
					// Si una inserción de un registro da fallo, la respuesta del servicio deberá
					// ser KO
					if (response3 == 0) {
						LOGGER.warn(
								"updateUsers() / admUsuariosEfectivoPerfilMapper.insert() -> No se ha podido añadir una relación de perfil-usuario en tabla admUsuariosEfectivoPerfil");
						serviceOK = false;
					}
				}else{
					if (null != usuarioPerfil.get(0).getFechaBaja()) {
						// Se guarda el usuario efectivoPerfil
						LOGGER.info(
								"updateUsers() / admUsuariosEfectivoPerfilMapper.insert() -> Entrada a admUsuariosEfectivoPerfilMapper para añadir una relación de perfil-usuario en tabla admUsuariosEfectivoPerfil");
						AdmUsuariosEfectivosPerfil recordUsuarioEfectivo = usuarioPerfil.get(0);
						recordUsuarioEfectivo.setFechaBaja(null);
						response3 = admUsuariosEfectivoPerfilExtendsMapper.updateByExample(recordUsuarioEfectivo, keyUsuarioPerfil);
					}
				}

			}
		}

		// comprobar si ha actualizado en bd correctamente
		if (response1 == 1 && response2 == 1 && response3 == 1 && serviceOK) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("updateUsers() -> OK. Se han actualizado correctamente los datos de usuario");
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateUsers() -> KO. NO se han actualizado correctamente los datos de usuario ");
		}

		LOGGER.info("updateUsers() -> Salida del servicio para actualizar datos de usuario");
		return updateResponseDTO;
	}

	@Override
	public CreateResponseDTO createUsers(UsuarioCreateDTO usuarioCreateDTO, HttpServletRequest request) {
		LOGGER.info("createUsers() -> Entrada al servicio para creación de usuarios");
		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		int response1 = 1;
		int response2 = 1;
		int response3 = 1;
		int response4 = 1;
		boolean responseOK = true;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		// Asignacion de la institucion del token al usuario que queremos crear
		usuarioCreateDTO.setIdInstitucion(institucion);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni)
				.andIdinstitucionEqualTo(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
		LOGGER.info(
				"createUsers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"createUsers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			// Asignacion del idioma del usuario logeado al usuario que queremos crear
			usuarioCreateDTO.setIdLenguaje(usuario.getIdlenguaje());

			if (!usuarioCreateDTO.getNif().equalsIgnoreCase("")) {
				AdmUsuariosExample exampleNifInstitucion = new AdmUsuariosExample();
				exampleNifInstitucion.createCriteria().andNifEqualTo(usuarioCreateDTO.getNif())
						.andIdinstitucionEqualTo(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
				LOGGER.info(
						"createUsers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener usuarios por nif e institucion");
				List<AdmUsuarios> userNifInstitucion = admUsuariosExtendsMapper.selectByExample(exampleNifInstitucion);
				LOGGER.info(
						"createUsers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener usuarios por nif e institucion");

				// si no existe un usuario ya con ese dni y ese idInstitucion, se crea
				if (userNifInstitucion.size() == 0) {
					if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("")
							&& !usuarioCreateDTO.getIdInstitucion().equals(null)) {
						LOGGER.info(
								"createUsers() / admUsuariosExtendsMapper.createUserAdmUsuariosTable() -> Entrada a admUsuariosExtendsMapper añadir registro del usuario en tabla AdmUsuarios");
						response1 = admUsuariosExtendsMapper.createUserAdmUsuariosTable(usuarioCreateDTO,
								usuario.getIdusuario());
						LOGGER.info(
								"createUsers() / admUsuariosExtendsMapper.createUserAdmUsuariosTable() -> Salida de admUsuariosExtendsMapper añadir registro del usuario en tabla AdmUsuarios");
						if (response1 == 0) {
							responseOK = false;
						}
					}
					if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("")
							&& !usuarioCreateDTO.getIdInstitucion().equals(null)
							&& !usuarioCreateDTO.getRol().equalsIgnoreCase("")
							&& !usuarioCreateDTO.getRol().equals(null)) {
						LOGGER.info(
								"createUsers() / admUsuariosExtendsMapper.createUserAdmUsuarioEfectivoTable() -> Entrada a admUsuariosExtendsMapper añadir registro del usuario en tabla AdmUsuarioEfectivo");
						response2 = admUsuariosExtendsMapper.createUserAdmUsuarioEfectivoTable(usuarioCreateDTO,
								usuario.getIdusuario());
						LOGGER.info(
								"createUsers() / admUsuariosExtendsMapper.createUserAdmUsuarioEfectivoTable() -> Salida de admUsuariosExtendsMapper añadir registro del usuario en tabla AdmUsuarioEfectivo");
						if (response2 == 0) {
							responseOK = false;
						}
					}

					if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("")
							&& !usuarioCreateDTO.getIdInstitucion().equals(null)
							&& !usuarioCreateDTO.getRol().equalsIgnoreCase("")
							&& !usuarioCreateDTO.getRol().equals(null)
							&& !usuarioCreateDTO.getGrupo().equalsIgnoreCase("")
							&& !usuarioCreateDTO.getGrupo().equals(null)) {
						AdmPerfilKey key = new AdmPerfilKey();
						key.setIdinstitucion(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
						key.setIdperfil(usuarioCreateDTO.getGrupo());
						LOGGER.info(
								"createUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Entrada a admPerfilExtendsMapper para comprobar si existe un perfil con una institución determinada");
						AdmPerfil perfil = admPerfilExtendsMapper.selectByPrimaryKey(key);
						LOGGER.info(
								"createUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Salida de admPerfilExtendsMapper para comprobar si existe un perfil con una institución determinada");
						if (null == perfil) {
							AdmPerfil record = new AdmPerfil();
							record.setUsumodificacion(usuario.getIdusuario());
							record.setIdinstitucion(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
							record.setIdperfil(usuarioCreateDTO.getGrupo());
							record.setFechamodificacion(new Date());
							record.setNivelperfil(new Long(0));
							record.setDescripcion(usuarioCreateDTO.getGrupo());
							// Se guarda el perfil para la institucion
							LOGGER.info(
									"createUsers() / admPerfilExtendsMapper.insert() -> Entrada a admPerfilExtendsMapper para añadir un perfil con una institución determinada");
							response3 = admPerfilExtendsMapper.insert(record);
							LOGGER.info(
									"createUsers() / admPerfilExtendsMapper.insert() -> Salida de admPerfilExtendsMapper para añadir un perfil con una institución determinada");
							if (response3 == 0) {
								responseOK = false;
							}
						}

						LOGGER.info(
								"createUsers() / admUsuariosExtendsMapper.createUserAdmUsuariosEfectivoPerfilTable() -> Entrada a admUsuariosExtendsMapper para una relación entre: usuario-perfil con institución determinada");
						response4 = admUsuariosExtendsMapper.createUserAdmUsuariosEfectivoPerfilTable(usuarioCreateDTO,
								usuario.getIdusuario());
						LOGGER.info(
								"createUsers() / admUsuariosExtendsMapper.createUserAdmUsuariosEfectivoPerfilTable() -> Salida de admUsuariosExtendsMapper para una relación entre: usuario-perfil con institución determinada");
						if (response1 == 4) {
							responseOK = false;
						}
					}

				} else {
					responseOK = false;// para que se sepa que ha fallado
					LOGGER.error(
							"createUsers() / admUsuariosExtendsMapper.selectByExample() -> El usuario a crear ya tiene asignada una institución");
					Error err = new Error();
					err.setMessage("administracion.usuario.ya.asignado.institucion");
					createResponseDTO.setError(err);
				}
			} else {
				LOGGER.error("createUsers() -> El usuario a crear no tiene nif");
				responseOK = false;
			}
		} else {
			LOGGER.info(
					"createUsers() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
			responseOK = false;
		}

		// comprobar si ha actualizado en bd correctamente
		if (response1 == 1 && response2 == 1 && response3 == 1 && response4 == 1 && responseOK) {
			createResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("createUsers() -> OK. Usuario creado correctemente");
		} else {
			createResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("createUsers() -> KO. Usuario NO creado correctemente");
		}

		LOGGER.info("createUsers() -> Salida del servicio para creación de usuarios");
		return createResponseDTO;
	}

	@Override
	public DeleteResponseDTO deleteUsers(UsuarioDeleteDTO usuarioDeleteDTO, HttpServletRequest request) {
		LOGGER.info("deleteUsers() -> Entrada al servicio para deshabilitar usuarios");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		// asignar al usuario a eliminar la institucion del token
		usuarioDeleteDTO.setIdInstitucion(institucion);

		if (!usuarioDeleteDTO.getIdUsuario().equals(null) && usuarioDeleteDTO.getIdUsuario().size() > 0
				&& !usuarioDeleteDTO.getIdInstitucion().equalsIgnoreCase("")
				&& !usuarioDeleteDTO.getIdInstitucion().equals(null)) {
			// si activo = S, dar de baja => fecha de baja a fecha actual y activo = N
			if (usuarioDeleteDTO.getActivo().equals("S")) {
				LOGGER.info(
						"createUsers() / admUsuariosExtendsMapper.disableUser() -> Entrada a admUsuariosExtendsMapper para deshabilitar a un usuario");
				response = admUsuariosExtendsMapper.disableUser(usuarioDeleteDTO);
				LOGGER.info(
						"createUsers() / admUsuariosExtendsMapper.disableUser() -> Salida de admUsuariosExtendsMapper para deshabilitar a un usuario");
			}
			// si activo = N, dar de alta => fecha de baja a null y activo = S
			else if (usuarioDeleteDTO.getActivo().equals("N")) {
				LOGGER.info(
						"createUsers() / admUsuariosExtendsMapper.enableUser() -> Entrada a admUsuariosExtendsMapper para habilitar a un usuario");
				response = admUsuariosExtendsMapper.enableUser(usuarioDeleteDTO);
				LOGGER.info(
						"createUsers() / admUsuariosExtendsMapper.enableUser() -> Salida de admUsuariosExtendsMapper para habilitar a un usuario");
			}
		} else {
			response = 0;
			LOGGER.warn(
					"deleteUsers() -> No se puede eliminar ningún usuario (atributos para borrarlo no suficientes)");
		}

		// comprobar si ha actualizado en bd correctamente
		if (response == 1) {
			deleteResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("deleteUsers() -> OK. Usuario eliminado correctamente");
		} else {
			deleteResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("deleteUsers() -> KO. Usuario NO eliminado correctamente");
		}

		LOGGER.info("deleteUsers() -> Salida del servicio para deshabilitar usuarios");
		return deleteResponseDTO;
	}

	@Override
	public UsuarioGruposDTO getUsersGroupsSearch(int numPagina, HttpServletRequest request) {
		LOGGER.info("getUsersGroupsSearch() -> Entrada al servicio para búsqueda de perfiles de usuario");
		UsuarioGruposDTO response = new UsuarioGruposDTO();
		AdmPerfilExample example = new AdmPerfilExample();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();

		String token = request.getHeader("Authorization");
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);

		// obtener los perfiles disponibles para una institucion
		example.setOrderByClause("IDPERFIL ASC");
		example.setDistinct(true);
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andFechaBajaIsNull();
		LOGGER.info(
				"getUsersGroupsSearch() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> Entrada a admPerfilExtendsMapper para buscar los diferentes perfiles");
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);
		LOGGER.info(
				"getUsersGroupsSearch() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> Salida de admPerfilExtendsMapper para buscar los diferentes perfiles");

		if (profiles != null && profiles.size() > 0) {
			List<UsuarioGrupoItem> usuarioGrupoItems = new ArrayList<>();

			for (AdmPerfil admPerfil : profiles) {
				UsuarioGrupoItem usuarioGrupoItem = new UsuarioGrupoItem();
				usuarioGrupoItem.setDescripcionGrupo(admPerfil.getDescripcion());
				usuarioGrupoItem.setIdGrupo(admPerfil.getIdperfil());
				usuarioGrupoItem.setDescripcionRol(" ");
				List<String> rolesasignados = new ArrayList<String>();
				LOGGER.info(
						"getUsersGroupsSearch() / admPerfilExtendsMapper.selectRolPerfilDistinctByExample() -> Entrada a admPerfilExtendsMapper para buscar los diferentes roles");
				List<RolPerfilDTO> perfilesRol = admPerfilExtendsMapper.selectRolPerfilDistinctByExample(institucion,
						admPerfil.getIdperfil());
				LOGGER.info(
						"getUsersGroupsSearch() / admPerfilExtendsMapper.selectRolPerfilDistinctByExample() -> Salida de admPerfilExtendsMapper para buscar los diferentes roles");

				if (null != perfilesRol && perfilesRol.size() > 0) {
					ComboItem[] rolesAsignadosItem = new ComboItem[perfilesRol.size()];
					List<ComboItem> rolesPorAsignar = new ArrayList<ComboItem>();
					int i = 0;
					ComboItem rolPorAsignar = new ComboItem();
					rolPorAsignar.setValue("");
					rolPorAsignar.setLabel("");
					rolesPorAsignar.add(rolPorAsignar);
					for (RolPerfilDTO admRol : perfilesRol) {
						if (admRol.getGrupopordefecto() != null && admRol.getGrupopordefecto().equals("S")) {
							usuarioGrupoItem.setDescripcionRol(
									usuarioGrupoItem.getDescripcionRol().concat(admRol.getDescripcion().concat(", ")));

						} else {
							rolPorAsignar = new ComboItem();
							rolPorAsignar.setValue(admRol.getIdRol());
							rolPorAsignar.setLabel(admRol.getDescripcion());
							rolesPorAsignar.add(rolPorAsignar);
						}
						rolesasignados.add(admRol.getIdRol());
						rolesAsignadosItem[i] = new ComboItem();
						rolesAsignadosItem[i].setLabel(admRol.getDescripcion());
						rolesAsignadosItem[i].setValue(admRol.getIdRol());
						i++;
					}
					
					if (null != rolesPorAsignar && rolesPorAsignar.size() > 0) {
						ComboItem[] añadirRolesNoAsignados = new ComboItem[rolesPorAsignar.size()];
						int j = 0;
						for (ComboItem comboItem : rolesPorAsignar) {
							añadirRolesNoAsignados[j] = new ComboItem();
							añadirRolesNoAsignados[j] = comboItem;
							j++;
						}
						usuarioGrupoItem.setAsignarRolDefecto(añadirRolesNoAsignados);
					}
					usuarioGrupoItem.setRolesAsignados(rolesAsignadosItem);
					
				} else {
					LOGGER.info(
							"getUsersGroupsSearch() / admPerfilExtendsMapper.selectRolPerfilDistinctByExample() -> No existen roles en la búsqueda");
				}

				AdmRolExample exampleRol = new AdmRolExample();

				if (null != rolesasignados && rolesasignados.size() > 0) {
					exampleRol.createCriteria().andIdrolNotIn(rolesasignados);
				}

				LOGGER.info(
						"getUsersGroupsSearch() / admRolMapper.selectByExample() -> Entrada a admRolMapper para buscar los roles no asignados a perfiles");
				List<AdmRol> rolNoAsignados = this.admRolMapper.selectByExample(exampleRol);
				LOGGER.info(
						"getUsersGroupsSearch() / admRolMapper.selectByExample() -> Salida de admRolMapper para buscar los roles no asignados a perfiles");

				if (null != rolNoAsignados && rolNoAsignados.size() > 0) {
					ComboItem[] rolesNoAsignadosItem = new ComboItem[rolNoAsignados.size()];
					int i = 0;
					for (AdmRol admRolNoAsignado : rolNoAsignados) {
						rolesasignados.add(admRolNoAsignado.getIdrol());
						rolesNoAsignadosItem[i] = new ComboItem();
						rolesNoAsignadosItem[i].setLabel(admRolNoAsignado.getDescripcion());
						rolesNoAsignadosItem[i].setValue(admRolNoAsignado.getIdrol());
						i++;
					}
					usuarioGrupoItem.setRolesNoAsignados(rolesNoAsignadosItem);
				}
				
				usuarioGrupoItems.add(usuarioGrupoItem);
			}
			for(UsuarioGrupoItem usu:usuarioGrupoItems) {
				if (usu.getDescripcionRol().length()>2) {
					if(usu.getDescripcionRol().substring(usu.getDescripcionRol().length()- 2).equals(", ") ) {
						usu.setDescripcionRol(usu.getDescripcionRol().substring(0, usu.getDescripcionRol().length()-2));
					}else {
						usu.setDescripcionRol(usu.getDescripcionRol().substring(0, usu.getDescripcionRol().length()));
					}
					
				} else {
					usu.setDescripcionRol("No tiene asignado ningun Rol por defecto");
				}
			}
			response.setUsuarioGrupoItem(usuarioGrupoItems);
		} else {
			LOGGER.warn(
					"getUsersGroupsSearch() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> No existen perfiles en la búsqueda");
		}

		LOGGER.info("getUsersGroupsSearch() -> Salida del servicio para búsqueda de perfiles de usuario");
		return response;
	}

	@Override
	public DeleteResponseDTO deleteUsersGroup(UsuarioGrupoDeleteDTO[] usuarioDeleteDTO, HttpServletRequest request) {
		LOGGER.info("deleteUsersGroup() -> Entrada al servicio para eliminar perfiles de usuario");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;

		if (null != usuarioDeleteDTO && usuarioDeleteDTO.length > 0) {

			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);

			for (int i = 0; i < usuarioDeleteDTO.length; i++) {

				usuarioDeleteDTO[i].setIdInstitucion(institucion);

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(usuarioDeleteDTO[i].getIdInstitucion()));

				LOGGER.info(
						"deleteUsersGroup() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para buscar usuarios por dni e institución");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"deleteUsersGroup() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para buscar usuarios por dni e institución");
				AdmUsuarios usuario = usuarios.get(0);
				List<String> idUsuario = new ArrayList<String>();
				idUsuario.add(String.valueOf(usuario.getIdusuario()));
				usuarioDeleteDTO[i].setIdUsuario(idUsuario);

				if (!usuarioDeleteDTO[i].getIdUsuario().equals(null) && usuarioDeleteDTO[i].getIdUsuario().size() > 0
						&& !usuarioDeleteDTO[i].getIdInstitucion().equalsIgnoreCase("")
						&& !usuarioDeleteDTO[i].getIdInstitucion().equals(null)) {

					LOGGER.info(
							"deleteUsersGroup() / admUsuariosExtendsMapper.deleteUserGroup() -> Entrada a admUsuariosExtendsMapper para eliminar perfiles por dni e institución");
					response = admUsuariosExtendsMapper.deleteUserGroup(usuarioDeleteDTO[i]);
					LOGGER.info(
							"deleteUsersGroup() / admUsuariosExtendsMapper.deleteUserGroup() -> Salida de admUsuariosExtendsMapper para eliminar perfiles por dni e institución");

				} else {
					response = 0;
					LOGGER.warn(
							"deleteUsersGroup() / admUsuariosExtendsMapper.deleteUserGroup() -> No se puede eliminar el perfil porque falta el idUsuario y/o la institución");
				}

				// error no gestionado del todo correcto
				if (response > 0)
					deleteResponseDTO.setStatus(SigaConstants.OK);
				else
					deleteResponseDTO.setStatus(SigaConstants.KO);
			}
		} else {
			LOGGER.warn("deleteUsersGroup() -> No hay grupos de usuario para eliminar");
			deleteResponseDTO.setStatus(SigaConstants.KO);

		}

		LOGGER.info("deleteUsersGroup() -> Salida del servicio para eliminar perfiles de usuario");
		return deleteResponseDTO;

	}

	@Override
	public UsuarioGruposDTO getUsersGroupsHistoric(int numPagina, HttpServletRequest request) {
		LOGGER.info(
				"getUsersGroupsHistoric() -> Entrada al servicio para buscar el histórico de todos los perfiles de usuario");
		UsuarioGruposDTO response = new UsuarioGruposDTO();
		AdmPerfilExample example = new AdmPerfilExample();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();

		String token = request.getHeader("Authorization");
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);

		example.setOrderByClause("DESCRIPCION ASC");
		example.setDistinct(true);
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion));
		LOGGER.info(
				"getUsersGroupsHistoric() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> Entrada a admPerfilExtendsMapper para buscar los distintos perfiles de una institución");
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);
		LOGGER.info(
				"getUsersGroupsHistoric() / admUsuariosExtendsMapper.selectComboPerfilDistinctByExample() -> Salida de admPerfilExtendsMapper para buscar los distintos perfiles de una institución");

		if (profiles != null && profiles.size() > 0) {
			List<UsuarioGrupoItem> usuarioGrupoItems = new ArrayList<>();

			for (AdmPerfil admPerfil : profiles) {
				UsuarioGrupoItem usuarioGrupoItem = new UsuarioGrupoItem();
				usuarioGrupoItem.setDescripcionGrupo(admPerfil.getDescripcion());
				usuarioGrupoItem.setIdGrupo(admPerfil.getIdperfil());
				usuarioGrupoItem.setDescripcionRol(" ");
				usuarioGrupoItem.setFechaBaja(admPerfil.getFechaBaja());
				List<String> rolesasignados = new ArrayList<String>();

				LOGGER.info(
						"getUsersGroupsHistoric() / admPerfilExtendsMapper.selectRolPerfilDistinctByExample() -> Entrada a admPerfilExtendsMapper para buscar la relación entre los distintos roles-perfil(i) para una institución concreta");
				List<RolPerfilDTO> perfilesRol = admPerfilExtendsMapper.selectRolPerfilDistinctByExample(institucion,
						admPerfil.getIdperfil());
				LOGGER.info(
						"getUsersGroupsHistoric() / admPerfilExtendsMapper.selectRolPerfilDistinctByExample() -> Salida de admPerfilExtendsMapper para buscar la relación entre los distintos roles-perfil(i) para una institución concreta");

				if (null != perfilesRol && perfilesRol.size() > 0) {
					ComboItem[] rolesAsignadosItem = new ComboItem[perfilesRol.size()];
					int i = 0;
					for (RolPerfilDTO admRol : perfilesRol) {
						if (admRol.getGrupopordefecto() != null && admRol.getGrupopordefecto().equals("S")) {
							usuarioGrupoItem.setDescripcionRol(
									usuarioGrupoItem.getDescripcionRol().concat(admRol.getDescripcion().concat(", ")));

						}
						rolesasignados.add(admRol.getIdRol());
						rolesAsignadosItem[i] = new ComboItem();
						rolesAsignadosItem[i].setLabel(admRol.getDescripcion());
						rolesAsignadosItem[i].setValue(admRol.getIdRol());
						i++;
					}

					usuarioGrupoItem.setRolesAsignados(rolesAsignadosItem);
				} else {
					LOGGER.warn(
							"getUsersGroupsHistoric() / admPerfilExtendsMapper.selectRolPerfilDistinctByExample() -> No existe relación entre los distintos roles-perfil(i) para una institución concreta");
				}

				AdmRolExample exampleRol = new AdmRolExample();

				if (null != rolesasignados && rolesasignados.size() > 0) {
					exampleRol.createCriteria().andIdrolNotIn(rolesasignados);
				}

				LOGGER.info(
						"getUsersGroupsHistoric() / admRolMapper.selectByExample() -> Entrada a admRolMapper para buscar los roles no asignados");
				List<AdmRol> rolNoAsignados = this.admRolMapper.selectByExample(exampleRol);
				LOGGER.info(
						"getUsersGroupsHistoric() / admRolMapper.selectByExample() -> Salida de admRolMapper para buscar los roles no asignados");

				if (null != rolNoAsignados && rolNoAsignados.size() > 0) {
					ComboItem[] rolesNoAsignadosItem = new ComboItem[rolNoAsignados.size()];
					int i = 0;
					for (AdmRol admRolNoAsignado : rolNoAsignados) {
						rolesasignados.add(admRolNoAsignado.getIdrol());
						rolesNoAsignadosItem[i] = new ComboItem();
						rolesNoAsignadosItem[i].setLabel(admRolNoAsignado.getDescripcion());
						rolesNoAsignadosItem[i].setValue(admRolNoAsignado.getIdrol());
						i++;
					}

					usuarioGrupoItem.setRolesNoAsignados(rolesNoAsignadosItem);
				} else {
					LOGGER.warn(
							"getUsersGroupsHistoric() / admRolMapper.selectByExample() -> No hay roles no asignados");
				}

				usuarioGrupoItems.add(usuarioGrupoItem);
			}
			response.setUsuarioGrupoItem(usuarioGrupoItems);
		} else {
			LOGGER.warn(
					"getUsersGroupsHistoric() / admPerfilExtendsMapper.selectComboPerfilDistinctByExample() -> No existen los perfiles de la institución del token actual");
		}

		LOGGER.info(
				"getUsersGroupsHistoric() -> Salida del servicio para buscar el histórico de todos los perfiles de usuario");
		return response;
	}

	@Override
	public UpdateResponseDTO updateGroupUsers(UsuarioGrupoItem[] usuarioUpdateDTO, HttpServletRequest request) {
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int response1 = 1;
		int response2 = 1;
		int responsePerfil = 1;
		boolean responseOK = true;
		List<AdmPerfilRol> rolesAntiguos = new ArrayList<AdmPerfilRol>();

		// informacion del usuario logeado
		LOGGER.info("updateGroupUsers() -> Entrada al servicio para eliminar perfiles de usuarios");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		LOGGER.info(
				"updateGroupUsers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateGroupUsers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			
			AdmUsuarios usuario = usuarios.get(0);
			
			// Recorremos lista de perfiles a modificar
			for (int i = 0; i < usuarioUpdateDTO.length; i++) {
				
				// 1. Comprobar roles antiguos asignados a un grupo (perfil)
				AdmPerfilRolExample admPerfilRolExample = new AdmPerfilRolExample();
				admPerfilRolExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(usuarioUpdateDTO[i].getIdGrupo());
				
				LOGGER.info(
						"updateGroupUsers() / admPerfilRolMapper.selectByExample() -> Entrada a admPerfilRolMapper para obtener roles antiguos asignados a un perfil");
				rolesAntiguos = admPerfilRolMapper.selectByExample(admPerfilRolExample);
				LOGGER.info(
						"updateGroupUsers() / admPerfilRolMapper.selectByExample() -> Salida de admPerfilRolMapper para obtener roles antiguos asignados a un perfil");
				
				List<String> rolesFinalesABorrar = rolesAntiguos.stream().map(result -> result.getIdrol()).collect(Collectors.toList());
				
				// 2. Actualizar los nuevos roles-perfiles respecto a los antiguos
				for (ComboItem rolesNuevos : usuarioUpdateDTO[i].getRolesAsignados()) 
				{	
					if(rolesFinalesABorrar.contains(rolesNuevos.getValue())) {
						rolesFinalesABorrar.remove(rolesNuevos.getValue());
					}
					else {
						AdmPerfilRol nuevoPerfilRol = new AdmPerfilRol();
						nuevoPerfilRol.setFechamodificacion(new Date());
						nuevoPerfilRol.setGrupopordefecto("S");
						nuevoPerfilRol.setIdinstitucion(Short.valueOf(institucion));
						nuevoPerfilRol.setIdperfil(usuarioUpdateDTO[i].getIdGrupo());
						nuevoPerfilRol.setIdrol(rolesNuevos.getValue());
						nuevoPerfilRol.setUsumodificacion(usuario.getIdusuario());
						LOGGER.info(
								"updateGroupUsers() / admPerfilRolMapper.insertSelective() -> Entrada a admPerfilRolMapper para actualizar las nuevas relaciones perfil-roles");
						
						response1 = admPerfilRolMapper.insertSelective(nuevoPerfilRol);
						LOGGER.info(
								"updateGroupUsers() / admPerfilRolMapper.insertSelective() -> Salida de admPerfilRolMapper para actualizar las nuevas relaciones perfil-roles");
						
						if (response1 == 0) {
							LOGGER.warn(
									"updateGroupUsers() / admPerfilRolMapper.insertSelective() -> No se ha podido crear una relación perfil-rol");
							responseOK = false;
						}
						
					}		
				}
				
				//Actualizar la descripcion del perfil
				if(usuarioUpdateDTO[i].getDescripcionGrupo() != null){
					
					AdmPerfilKey key = new AdmPerfilKey();
					key.setIdinstitucion(Short.valueOf(institucion));
					key.setIdperfil(usuarioUpdateDTO[i].getIdGrupo());
					AdmPerfil perfil = admPerfilMapper.selectByPrimaryKey(key);
					
					if(perfil != null){
						perfil.setDescripcion(usuarioUpdateDTO[i].getDescripcionGrupo());
						perfil.setFechamodificacion(new Date());
						perfil.setUsumodificacion(usuario.getIdusuario());
						LOGGER.info("updateGroupUsers() / admPerfilMapper.updateByPrimaryKey() -> Entrada admPerfilMapper para actualizar la descripcion del perfil");
						admPerfilMapper.updateByPrimaryKey(perfil);
						LOGGER.info("updateGroupUsers() / admPerfilMapper.updateByPrimaryKey() -> Salida de admPerfilMapper para actualizar la descripcion del perfil");
					}
					
					
					if(responsePerfil == 0){
						LOGGER.info("updateGroupUsers() / admPerfilMapper.insertSelective() -> No se ha actualizado la descripción del perfil");
					}
					
					
				}
				
				// 3. Borrar roles que quedan
				if(!rolesFinalesABorrar.isEmpty())
				{
					AdmPerfilRolExample rolesPerfilAntiguosExample = new AdmPerfilRolExample();
					rolesPerfilAntiguosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(usuarioUpdateDTO[i].getIdGrupo()).andIdrolIn(rolesFinalesABorrar);
					LOGGER.info(
							"updateGroupUsers() / admPerfilRolMapper.deleteByExample() -> Entrada a admPerfilRolMapper para eliminar las antiguas relaciones perfil-roles");
					response2 = admPerfilRolMapper.deleteByExample(rolesPerfilAntiguosExample);
					LOGGER.info(
							"updateGroupUsers() / admPerfilRolMapper.deleteByExample() -> Entrada a admPerfilRolMapper para eliminar las antiguas relaciones perfil-roles");
					if (response2 == 0) {
						LOGGER.warn(
								"updateGroupUsers() / admPerfilRolMapper.deleteByExample() -> No se ha podido borrar una antigua relación perfil-rol");
						responseOK = false;
					}
					
				}
				
			}	
		} else {
			LOGGER.warn(
					"updateGroupUsers() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios para el dni e institucion indicados");
			responseOK = false;
		}

		// comprobar si ha actualizado en bd correctamente
		if (responseOK) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("updateGroupUsers() -> OK. Perfiles de usuario eliminados correctamente");
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.info("updateGroupUsers() -> KO. Perfiles de usuario NO eliminados correctamente");
		}

		LOGGER.info("updateGroupUsers() -> Salida del servicio para eliminar perfiles de usuarios");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO createGroupUsers(UsuarioGrupoItem usuarioUpdateDTO, HttpServletRequest request) {
		LOGGER.info("createGroupUsers() -> Entrada al servicio de creación de perfiles de usuario");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short institucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		// Buscamos el perfil para ver si ya existe. En caso de que no exista
		LOGGER.info(
				"createGroupUsers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"createGroupUsers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		AdmPerfilKey key = new AdmPerfilKey();
		key.setIdinstitucion(Short.valueOf(institucion));
		key.setIdperfil(usuarioUpdateDTO.getIdGrupo());
		LOGGER.info(
				"createGroupUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Entrada a admPerfilExtendsMapper para obtener un perfil");
		AdmPerfil record = this.admPerfilExtendsMapper.selectByPrimaryKey(key);
		LOGGER.info(
				"createGroupUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Salida de admPerfilExtendsMapper para obtener un perfil");

		if (null == record && null != usuarios && usuarios.size() > 0) {
			record = new AdmPerfil();
			record.setIdperfil(usuarioUpdateDTO.getIdGrupo());
			record.setIdinstitucion(Short.valueOf(institucion));
			record.setDescripcion(usuarioUpdateDTO.getDescripcionGrupo());
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuarios.get(0).getIdusuario());
			record.setNivelperfil(new Long(0));
			// Actualizamos el registro de perfil
			LOGGER.info(
					"createGroupUsers() / admPerfilExtendsMapper.insert() -> Entrada a admPerfilExtendsMapper para crear un nuevo perfil");
			this.admPerfilExtendsMapper.insert(record);
			LOGGER.info(
					"createGroupUsers() / admPerfilExtendsMapper.insert() -> Salida de admPerfilExtendsMapper para crear un nuevo perfil");

			if (null != usuarioUpdateDTO.getRolesAsignados() && usuarioUpdateDTO.getRolesAsignados().length > 0) {
				for (ComboItem rolesAsignados : usuarioUpdateDTO.getRolesAsignados()) {
					if (!UtilidadesString.esCadenaVacia(rolesAsignados.getValue())) {
					AdmPerfilRol recordPerfilRol = new AdmPerfilRol();
					recordPerfilRol.setFechamodificacion(new Date());
					recordPerfilRol.setGrupopordefecto("S");
					recordPerfilRol.setIdinstitucion(Short.valueOf(institucion));
					recordPerfilRol.setIdperfil(usuarioUpdateDTO.getIdGrupo());
					recordPerfilRol.setIdrol(rolesAsignados.getValue());
					recordPerfilRol.setUsumodificacion(usuarios.get(0).getIdusuario());
					LOGGER.info(
							"createGroupUsers() / admPerfilRolMapper.insert() -> Entrada a admPerfilRolMapper para crear una relación perfil-rol");
					this.admPerfilRolMapper.insert(recordPerfilRol);
					LOGGER.info(
							"createGroupUsers() / admPerfilRolMapper.insert() -> Salida de admPerfilRolMapper para crear una relación perfil-rol");
					}
				}
			}
			updateResponseDTO.setStatus(SigaConstants.OK);

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			Error error = new Error();
			error.setMessage("administracion.grupo.usuario.duplicado");
			LOGGER.error(
					"createGroupUsers() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
			LOGGER.error(
					"createGroupUsers() / admPerfilExtendsMapper.selectByPrimaryKey() -> Perfil de usuario duplicado");
			updateResponseDTO.setError(error);
		}

		LOGGER.info("createGroupUsers() -> Salida del servicio de creación de perfiles de usuario");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateGrupoDefecto(UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		LOGGER.info("updateGrupoDefecto() -> Entrada al servicio de actualización de perfil por defecto");
		int response = 1;
		int response1 = 0;
		boolean responseOK = true;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));

		// Buscamos el perfil para ver si ya existe. En caso de que no exista
		LOGGER.info(
				"updateGrupoDefecto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateGrupoDefecto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);

			AdmPerfilRolExample example = new AdmPerfilRolExample();
			example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion))
					.andIdrolEqualTo(usuarioUpdateDTO.getAsignarRolDefecto()[0].getValue())
					.andGrupopordefectoEqualTo("S");

			List<AdmPerfilRol> perfilesRol = this.admPerfilRolMapper.selectByExample(example);
			if (null != perfilesRol && perfilesRol.size() > 0) {
				for (AdmPerfilRol admPerfilRol : perfilesRol) {
					admPerfilRol.setGrupopordefecto("N");
					LOGGER.info(
							"updateGrupoDefecto() / admPerfilRolMapper.updateByPrimaryKeySelective() -> Entrada a admPerfilRolMapper para actualizar el perfil a  de un rol");
					response = this.admPerfilRolMapper.updateByPrimaryKeySelective(admPerfilRol);
					LOGGER.info(
							"updateGrupoDefecto() / admPerfilRolMapper.updateByPrimaryKeySelective() -> Salida de admPerfilRolMapper para actualizar el perfil de un rol");
					if (response == 0) {
						responseOK = false;
					}
				}
			}

			AdmPerfilRol record = new AdmPerfilRol();
			record.setFechamodificacion(new Date());
			record.setGrupopordefecto("S");
			record.setIdperfil(usuarioUpdateDTO.getIdGrupo());
			record.setIdrol(usuarioUpdateDTO.getAsignarRolDefecto()[0].getValue());
			record.setIdinstitucion(Short.valueOf(institucion));
			record.setUsumodificacion(usuario.getIdusuario());
			LOGGER.info(
					"updateGrupoDefecto() / admPerfilRolMapper.updateByPrimaryKeySelective() -> Entrada a admPerfilRolMapper para actualizar el perfil de un rol");
			response1 = this.admPerfilRolMapper.updateByPrimaryKeySelective(record);
			LOGGER.info(
					"updateGrupoDefecto() / admPerfilRolMapper.updateByPrimaryKeySelective() -> Salida a admPerfilRolMapper para actualizar el perfil de un rol");

		} else {
			LOGGER.warn(
					"updateGrupoDefecto() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
		}

		if (response == 1 && response1 == 1 && responseOK) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("updateGrupoDefecto() -> OK. Se ha actualizado el perfil por defecto");
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateGrupoDefecto() -> KO. No se ha podido actualizar el perfil por defecto");
		}

		LOGGER.info("updateGrupoDefecto() -> Salida del servicio de actualización de perfil por defecto");
		return updateResponseDTO;
	}

}
