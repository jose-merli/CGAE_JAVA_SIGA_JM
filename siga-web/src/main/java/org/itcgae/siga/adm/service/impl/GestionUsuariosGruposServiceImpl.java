package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.RolPerfilDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoEditDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoItem;
import org.itcgae.siga.DTOs.adm.UsuarioGruposDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboCatalogoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmPerfilKey;
import org.itcgae.siga.db.entities.AdmPerfilRol;
import org.itcgae.siga.db.entities.AdmPerfilRolExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmRolExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilKey;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmPerfilRolMapper;
import org.itcgae.siga.db.mappers.AdmRolMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionUsuariosGruposServiceImpl implements IGestionUsuariosGruposService {

	@Autowired
	private AdmRolMapper admRolMapper;

	
	@Autowired
	private AdmPerfilRolMapper admPerfilRolMapper;
	
	
	
	@Autowired
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private AdmUsuariosEfectivosPerfilMapper admUsuariosEfectivoPerfilMapper;

	/***
	 * Get the different users roles Return ComboDTO (id, value) where id is the
	 * identificator rol and value is the description of these rol.
	 */
	@Override
	public ComboDTO getUsersRole() {
		AdmRolExample example = new AdmRolExample();
		List<AdmRol> roles = new ArrayList<AdmRol>();
		ComboDTO comboDto = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String unblocked = "N";

		example.createCriteria().andBloqueadoEqualTo(String.valueOf(unblocked));
		example.setOrderByClause("DESCRIPCION ASC");
		roles = admRolMapper.selectByExample(example);

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
		}

		comboDto.setCombooItems(comboItems);
		return comboDto;
	}

	/***
	 * Get the different users profiles. Return object ComboDto (id,
	 * description) sorted by description(natural String order) and where each
	 * element id is unique for a description
	 */
	@Override
	public ComboDTO getUsersProfile( HttpServletRequest request) {
		AdmPerfilExample example = new AdmPerfilExample();
		HashMap<String, String> hashProfiles = new HashMap<String, String>();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();
		ComboDTO comboDto = new ComboDTO();
		
		
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		example.createCriteria().andFechaBajaIsNull().andIdinstitucionEqualTo(Short.valueOf(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length())));
		example.setOrderByClause("DESCRIPCION ASC");
		example.setDistinct(true);
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);

		if (profiles != null && profiles.size() > 0) {
			for (AdmPerfil admPerfil : profiles) {
				hashProfiles.put(admPerfil.getIdperfil(), admPerfil.getDescripcion());

			}

			ComboItem comboItem = new ComboItem();
			comboItem.setValue("");
			comboItem.setLabel("");
			comboItems.add(comboItem);
			hashProfiles.forEach((id, description) -> {
				ComboItem comboItem2 = new ComboItem();
				comboItem2.setValue(id);
				comboItem2.setLabel(description);
				comboItems.add(comboItem2);
			});

			// ordenar por descripcion
			Comparator<ComboItem> orderBydDescription = new Comparator<ComboItem>() {
				public int compare(ComboItem combo1, ComboItem combo2) {
					return String.valueOf(combo1.getValue()).compareTo(String.valueOf(combo2.getValue()));
				}
			};

			comboItems.sort(orderBydDescription);

		}

		comboDto.setCombooItems(comboItems);
		return comboDto;
	}

	/***
	 * Get users information, showing them on different pages on web page. This
	 * information can be searched by a web user using different parameters.
	 * Call the mapper to get them. Return the users information.
	 */
	@Override
	public UsuarioDTO getUsersSearch(int numPagina, UsuarioRequestDTO usuarioRequestDTO, HttpServletRequest request) {
		List<UsuarioItem> usuarioItems = new ArrayList<UsuarioItem>();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		usuarioRequestDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		usuarioItems = admUsuariosExtendsMapper.getUsersByFilter(numPagina, usuarioRequestDTO);

		
		
		if (usuarioItems != null && usuarioItems.size() > 0) {
			
		for (UsuarioItem usuarioItem : usuarioItems) {
			if (null != usuarioItem.getPerfil() && !usuarioItem.getPerfil().equalsIgnoreCase("") ) {
				usuarioItem.setPerfiles(usuarioItem.getPerfil().split("; "));
			}
		}
			usuarioDTO.setUsuarioItem(usuarioItems);
		}

		return usuarioDTO;
	}

	@Override
	public UpdateResponseDTO updateUsers(UsuarioUpdateDTO usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int response1 = 1;
		int response2 = 1;
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0,9);
		usuarioUpdateDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		if (!usuarioUpdateDTO.getActivo().equalsIgnoreCase("")
				|| !usuarioUpdateDTO.getCodigoExterno().equalsIgnoreCase("")) {
			response1 = admUsuariosExtendsMapper.updateUsersAdmUserTable(usuarioUpdateDTO);
		}
		if (null != usuarioUpdateDTO.getIdGrupo() && usuarioUpdateDTO.getIdGrupo().length > 0) {
				for (int i = 0; i < usuarioUpdateDTO.getIdGrupo().length; i++) {
					
					usuarioUpdateDTO.setGrupo(usuarioUpdateDTO.getIdGrupo()[i]);
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
					
		
					//Buscamos el perfil para ver si ya existe. En caso de que no exista
					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
					AdmUsuarios usuario = usuarios.get(0);
					AdmPerfilKey key = new AdmPerfilKey();
					key.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
					key.setIdperfil(usuarioUpdateDTO.getGrupo());
					AdmPerfil perfil = admPerfilExtendsMapper.selectByPrimaryKey(key);
					//Buscamos el perfil para ver si ya existe. En caso de que no exista creamos la relación entre el usuario y el perfil
					if (null == perfil) {
						AdmPerfilExample keyPerfil = new AdmPerfilExample();
						keyPerfil.setDistinct(Boolean.TRUE);
						keyPerfil.createCriteria().andIdperfilEqualTo(usuarioUpdateDTO.getGrupo());
						List<AdmPerfil> perfilExistente = admPerfilExtendsMapper.selectByExample(keyPerfil);
						AdmPerfil record = new AdmPerfil();
						record.setUsumodificacion(usuario.getIdusuario());
						record.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
						record.setIdperfil(usuarioUpdateDTO.getGrupo());
						record.setFechamodificacion(new Date());
						record.setNivelperfil(new Long(0));
						record.setDescripcion(perfilExistente.get(0).getDescripcion());
						//Se guarda el perfil para la institucion
						admPerfilExtendsMapper.insert(record );
					}
					AdmUsuariosEfectivosPerfilKey keyUsuarioPerfil = new AdmUsuariosEfectivosPerfilKey();
					keyUsuarioPerfil.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
					keyUsuarioPerfil.setIdusuario(Integer.valueOf(usuarioUpdateDTO.getIdUsuario()));
					keyUsuarioPerfil.setIdrol(usuarioUpdateDTO.getRol());
					keyUsuarioPerfil.setIdperfil(usuarioUpdateDTO.getGrupo());
					AdmUsuariosEfectivosPerfil usuarioPerfil = admUsuariosEfectivoPerfilMapper.selectByPrimaryKey(keyUsuarioPerfil);
					if (null == usuarioPerfil) {
						AdmUsuariosEfectivosPerfil recordUsuarioEfectivo = new AdmUsuariosEfectivosPerfil();
						recordUsuarioEfectivo.setUsumodificacion(usuario.getIdusuario());
						recordUsuarioEfectivo.setIdinstitucion(Short.valueOf(usuarioUpdateDTO.getIdInstitucion()));
						recordUsuarioEfectivo.setIdperfil(usuarioUpdateDTO.getGrupo());
						recordUsuarioEfectivo.setFechamodificacion(new Date());
						recordUsuarioEfectivo.setIdrol(usuarioUpdateDTO.getRol());
						recordUsuarioEfectivo.setIdusuario(Integer.valueOf(usuarioUpdateDTO.getIdUsuario()));
						//Se guarda el usuario efectivoPerfil
						admUsuariosEfectivoPerfilMapper.insert(recordUsuarioEfectivo );
					}
				
				}
			
		}

		if (response1 == 1 && response2 == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");

		return updateResponseDTO;
	}

	@Override
	public CreateResponseDTO createUsers(UsuarioCreateDTO usuarioCreateDTO, HttpServletRequest request) {
		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		int response1 = 1;
		int response2 = 1;
		int response3 = 1;

		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0,9);
		usuarioCreateDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
		//admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		//Obtenemos el idioma
		usuarioCreateDTO.setIdLenguaje(usuario.getIdlenguaje());
		
		if(!usuarioCreateDTO.getNif().equalsIgnoreCase(""))
		{
			AdmUsuariosExample exampleNifInstitucion = new AdmUsuariosExample();
			exampleNifInstitucion.createCriteria().andNifEqualTo(usuarioCreateDTO.getNif()).andIdinstitucionEqualTo(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
			List<AdmUsuarios> userNifInstitucion = admUsuariosExtendsMapper.selectByExample(exampleNifInstitucion);
			
			// si no existe un usuario ya con ese dni y ese idInstitucion, se crea
			if(userNifInstitucion.size() == 0)
			{
				if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioCreateDTO.getIdInstitucion().equals(null)) {
					response1 = admUsuariosExtendsMapper.createUserAdmUsuariosTable(usuarioCreateDTO, usuario.getIdusuario());
				}
				if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioCreateDTO.getIdInstitucion().equals(null) &&
					!usuarioCreateDTO.getRol().equalsIgnoreCase("") && !usuarioCreateDTO.getRol().equals(null)) {
					response2 = admUsuariosExtendsMapper.createUserAdmUsuarioEfectivoTable(usuarioCreateDTO,
							usuario.getIdusuario());
				}

				if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioCreateDTO.getIdInstitucion().equals(null)
						&& !usuarioCreateDTO.getRol().equalsIgnoreCase("") && !usuarioCreateDTO.getRol().equals(null)
						&& !usuarioCreateDTO.getGrupo().equalsIgnoreCase("") && !usuarioCreateDTO.getGrupo().equals(null)) {
					AdmPerfilKey key = new AdmPerfilKey();
					key.setIdinstitucion(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
					key.setIdperfil(usuarioCreateDTO.getGrupo());
					AdmPerfil perfil = admPerfilExtendsMapper.selectByPrimaryKey(key);
					if (null == perfil) {
						AdmPerfil record = new AdmPerfil();
						record.setUsumodificacion(usuario.getIdusuario());
						record.setIdinstitucion(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
						record.setIdperfil(usuarioCreateDTO.getGrupo());
						record.setFechamodificacion(new Date());
						record.setNivelperfil(new Long(0));
						record.setDescripcion(usuarioCreateDTO.getGrupo());
						//Se guarda el perfil para la institucion
						admPerfilExtendsMapper.insert(record );
					}
					
					response3 = admUsuariosExtendsMapper.createUserAdmUsuariosEfectivoPerfilTable(usuarioCreateDTO,
							usuario.getIdusuario());
				}
			}
			else
			{
				Error err = new Error();
				err.setMessage("administracion.usuario.ya.asignado.institucion");
				createResponseDTO.setError(err);
				
			}
		}
		
		if (response1 == 0 || response2 == 0 || response3 == 0)
			createResponseDTO.setStatus("ERROR");
		else
			createResponseDTO.setStatus("OK");

		return createResponseDTO;
	}

	@Override
	public DeleteResponseDTO deleteUsers(UsuarioDeleteDTO usuarioDeleteDTO, HttpServletRequest request) {
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		usuarioDeleteDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;

		if (!usuarioDeleteDTO.getIdUsuario().equals(null) && usuarioDeleteDTO.getIdUsuario().size() > 0
				&& !usuarioDeleteDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioDeleteDTO.getIdInstitucion().equals(null)) {

			// si activo = S, dar de baja => fecha de baja a fecha actual y
			// activo = N
			if (usuarioDeleteDTO.getActivo().equals("S")) {
				response = admUsuariosExtendsMapper.disableUser(usuarioDeleteDTO);
			}
			// si activo = N, dar de alta => fecha de baja a null y activo = S
			else if (usuarioDeleteDTO.getActivo().equals("N")) {
				response = admUsuariosExtendsMapper.enableUser(usuarioDeleteDTO);
			}
		} else
			response = 0;

		if (response > 0)
			deleteResponseDTO.setStatus("OK");
		else
			deleteResponseDTO.setStatus("ERROR");

		return deleteResponseDTO;
	}

	@Override
	public UsuarioGruposDTO getUsersGroupsSearch(int numPagina, HttpServletRequest request) {
		UsuarioGruposDTO response = new UsuarioGruposDTO();
		AdmPerfilExample example = new AdmPerfilExample();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		example.setOrderByClause("DESCRIPCION ASC");
		example.setDistinct(true);
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andFechaBajaIsNull();
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);

		
		
		if (profiles != null && profiles.size() > 0) {
			List<UsuarioGrupoItem> usuarioGrupoItems = new ArrayList<>();
			
			for (AdmPerfil admPerfil : profiles) {
				UsuarioGrupoItem usuarioGrupoItem = new UsuarioGrupoItem();
				usuarioGrupoItem.setDescripcionGrupo(admPerfil.getDescripcion());
				usuarioGrupoItem.setIdGrupo(admPerfil.getIdperfil());
				usuarioGrupoItem.setDescripcionRol(" ");
				List<String> rolesasignados = new ArrayList<String>(); 
				
				List<RolPerfilDTO> perfilesRol = admPerfilExtendsMapper.selectRolPerfilDistinctByExample(institucion,admPerfil.getIdperfil());
				
				if (null != perfilesRol && perfilesRol.size()>0) {
					ComboItem[] rolesAsignadosItem = new ComboItem[perfilesRol.size()];
					List<ComboItem> rolesPorAsignar = new ArrayList<ComboItem>();
					int i = 0;
					ComboItem rolPorAsignar = new ComboItem();
					rolPorAsignar.setValue("");
					rolPorAsignar.setLabel("");
					rolesPorAsignar.add(rolPorAsignar);
					for (RolPerfilDTO admRol : perfilesRol) {
						if (admRol.getGrupopordefecto()!= null && admRol.getGrupopordefecto().equals("S")) {
							usuarioGrupoItem.setDescripcionRol(usuarioGrupoItem.getDescripcionRol().concat(admRol.getDescripcion().concat(", ")));
							
						}else {
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

					if (null != rolesPorAsignar && rolesPorAsignar.size()>0) {
						ComboItem[] añadirRolesNoAsignados = new ComboItem[rolesPorAsignar.size()];
						int j=0;
						for (ComboItem comboItem : rolesPorAsignar) {
							añadirRolesNoAsignados[j] = new ComboItem();
							añadirRolesNoAsignados[j] = comboItem;
							j++;
						}
						usuarioGrupoItem.setAsignarRolDefecto(añadirRolesNoAsignados);
					}
					usuarioGrupoItem.setRolesAsignados(rolesAsignadosItem);
				}
				

				
				AdmRolExample exampleRol = new AdmRolExample();

				if (null != rolesasignados && rolesasignados.size()>0) {
					exampleRol.createCriteria().andIdrolNotIn(rolesasignados);
				}

				List<AdmRol> rolNoAsignados = this.admRolMapper.selectByExample(exampleRol );

				if (null != rolNoAsignados && rolNoAsignados.size()>0) {
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
			response.setUsuarioGrupoItem(usuarioGrupoItems);	
		}

	
		
		return response;
	}

	@Override
	public DeleteResponseDTO deleteUsersGroup(UsuarioDeleteDTO usuarioDeleteDTO, HttpServletRequest request) {

		
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0,9);
		usuarioDeleteDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(usuarioDeleteDTO.getIdInstitucion()));
		

		//Buscamos el perfil para ver si ya existe. En caso de que no exista
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		List<String> idUsuario = new ArrayList<String>();
		idUsuario.add(String.valueOf(usuario.getIdusuario()));
		usuarioDeleteDTO.setIdUsuario(idUsuario);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;

		if (!usuarioDeleteDTO.getIdUsuario().equals(null) && usuarioDeleteDTO.getIdUsuario().size() > 0
				&& !usuarioDeleteDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioDeleteDTO.getIdInstitucion().equals(null)) {

				response = admUsuariosExtendsMapper.deleteUserGroup(usuarioDeleteDTO);

		} else
			response = 0;

		if (response > 0)
			deleteResponseDTO.setStatus("OK");
		else
			deleteResponseDTO.setStatus("ERROR");

		return deleteResponseDTO;
	
	}

	@Override
	public UsuarioGruposDTO getUsersGroupsHistoric(int numPagina, HttpServletRequest request) {
		UsuarioGruposDTO response = new UsuarioGruposDTO();
		AdmPerfilExample example = new AdmPerfilExample();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		example.setOrderByClause("DESCRIPCION ASC");
		example.setDistinct(true);
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion));
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);

		
		
		if (profiles != null && profiles.size() > 0) {
			List<UsuarioGrupoItem> usuarioGrupoItems = new ArrayList<>();
			
			for (AdmPerfil admPerfil : profiles) {
				UsuarioGrupoItem usuarioGrupoItem = new UsuarioGrupoItem();
				usuarioGrupoItem.setDescripcionGrupo(admPerfil.getDescripcion());
				usuarioGrupoItem.setIdGrupo(admPerfil.getIdperfil());
				usuarioGrupoItem.setDescripcionRol(" ");
				usuarioGrupoItem.setFechaBaja(admPerfil.getFechaBaja());
				List<String> rolesasignados = new ArrayList<String>(); 
				
				List<RolPerfilDTO> perfilesRol = admPerfilExtendsMapper.selectRolPerfilDistinctByExample(institucion,admPerfil.getIdperfil());
				
				if (null != perfilesRol && perfilesRol.size()>0) {
					ComboItem[] rolesAsignadosItem = new ComboItem[perfilesRol.size()];
					int i = 0;
					for (RolPerfilDTO admRol : perfilesRol) {
						if (admRol.getGrupopordefecto()!= null && admRol.getGrupopordefecto().equals("S")) {
							usuarioGrupoItem.setDescripcionRol(usuarioGrupoItem.getDescripcionRol().concat(admRol.getDescripcion().concat(", ")));
							
						}
						rolesasignados.add(admRol.getIdRol());
						rolesAsignadosItem[i] = new ComboItem();
						rolesAsignadosItem[i].setLabel(admRol.getDescripcion());
						rolesAsignadosItem[i].setValue(admRol.getIdRol());
						i++;
					}

					usuarioGrupoItem.setRolesAsignados(rolesAsignadosItem);
				}
				

				
				AdmRolExample exampleRol = new AdmRolExample();

				if (null != rolesasignados && rolesasignados.size()>0) {
					exampleRol.createCriteria().andIdrolNotIn(rolesasignados);
				}

				List<AdmRol> rolNoAsignados = this.admRolMapper.selectByExample(exampleRol );

				if (null != rolNoAsignados && rolNoAsignados.size()>0) {
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
			response.setUsuarioGrupoItem(usuarioGrupoItems);	
		}

	
		
		return response;
	}

	@Override
	public UpdateResponseDTO updateGroupUsers(UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		//Buscamos el perfil para ver si ya existe. En caso de que no exista
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmPerfilKey key = new AdmPerfilKey();
		key.setIdinstitucion(Short.valueOf(institucion));
		key.setIdperfil(usuarioUpdateDTO.getIdGrupo());
		AdmPerfil record = this.admPerfilExtendsMapper.selectByPrimaryKey(key );
		record.setDescripcion(usuarioUpdateDTO.getDescripcionGrupo());
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuarios.get(0).getIdusuario());
		//Actualizamos el registro de perfil
		this.admPerfilExtendsMapper.updateByPrimaryKeySelective(record);
		List<String> rolesComprobar = new ArrayList<String>();		
		if (null != usuarioUpdateDTO.getRolesAsignados() && usuarioUpdateDTO.getRolesAsignados().length>0) {
			AdmPerfilRolExample examplePerfilRol = new AdmPerfilRolExample();
			examplePerfilRol.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(usuarioUpdateDTO.getIdGrupo());
			List<AdmPerfilRol> perfilesRolABorrar = this.admPerfilRolMapper.selectByExample(examplePerfilRol );
			
			
			if (null != perfilesRolABorrar && perfilesRolABorrar.size()>0) {
			

				for (AdmPerfilRol string : perfilesRolABorrar) {
					rolesComprobar.add(string.getIdrol());
				}

			}
			for (ComboCatalogoItem rolesAsignados : usuarioUpdateDTO.getRolesAsignados()) {
				
				if (rolesComprobar.contains(rolesAsignados.getValue())) {
					rolesComprobar.remove(rolesAsignados.getValue());
				}else {
					AdmPerfilRol recordPerfilRol = new AdmPerfilRol();
					recordPerfilRol.setFechamodificacion(new Date());
					recordPerfilRol.setGrupopordefecto("N");
					recordPerfilRol.setIdinstitucion(Short.valueOf(institucion));
					recordPerfilRol.setIdperfil(usuarioUpdateDTO.getIdGrupo());
					recordPerfilRol.setIdrol(rolesAsignados.getValue());
					recordPerfilRol.setUsumodificacion(usuarios.get(0).getIdusuario());
					this.admPerfilRolMapper.insert(recordPerfilRol );
				}
			}
			
			if (null != rolesComprobar && rolesComprobar.size()>0) {
				for (String idRol : rolesComprobar) {
					
					AdmPerfilRolExample keydelete = new AdmPerfilRolExample();
					keydelete.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(usuarioUpdateDTO.getIdGrupo()).andIdrolEqualTo(idRol);
					
					this.admPerfilRolMapper.deleteByExample(keydelete );
				}
			}
			
			
			
		}else {
			
			AdmPerfilRolExample examplePerfilRol = new AdmPerfilRolExample();
			examplePerfilRol.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(usuarioUpdateDTO.getIdGrupo());
			List<AdmPerfilRol> perfilesRolABorrar = this.admPerfilRolMapper.selectByExample(examplePerfilRol );
			if (null != perfilesRolABorrar && perfilesRolABorrar.size()>0) {
				
				AdmPerfilRolExample keydelete = new AdmPerfilRolExample();
				keydelete.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(usuarioUpdateDTO.getIdGrupo());
				
				this.admPerfilRolMapper.deleteByExample(keydelete );
			}
			
			
		}


		

			updateResponseDTO.setStatus("OK");

		return updateResponseDTO;
		
		
		
		
	}

	@Override
	public UpdateResponseDTO createGroupUsers(UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		//Buscamos el perfil para ver si ya existe. En caso de que no exista
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmPerfilKey key = new AdmPerfilKey();
		key.setIdinstitucion(Short.valueOf(institucion));
		key.setIdperfil(usuarioUpdateDTO.getIdGrupo());
		AdmPerfil record = this.admPerfilExtendsMapper.selectByPrimaryKey(key );
		if (!(null != record)) {
					
				record =  new  AdmPerfil();
				record.setIdperfil(usuarioUpdateDTO.getIdGrupo());
				record.setIdinstitucion(Short.valueOf(institucion));
				record.setDescripcion(usuarioUpdateDTO.getDescripcionGrupo());
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuarios.get(0).getIdusuario());
				record.setNivelperfil(new Long(0));
				//Actualizamos el registro de perfil
				int status = this.admPerfilExtendsMapper.insert(record);
				
				
				if (null != usuarioUpdateDTO.getRolesAsignados() && usuarioUpdateDTO.getRolesAsignados().length>0) {
					
					for (ComboCatalogoItem rolesAsignados : usuarioUpdateDTO.getRolesAsignados()) {
						/*if (null != rolesAsignados.getLocal() && rolesAsignados.getLocal().equals("S")) {
							examplePerfilRol = new AdmPerfilRolExample();
							examplePerfilRol.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdrolEqualTo(rolesAsignados.getValue()).andGrupopordefectoEqualTo(rolesAsignados.getLocal());
							List<AdmPerfilRol> perfilesRol = this.admPerfilRolMapper.selectByExample(examplePerfilRol );
							if (null != perfilesRol && perfilesRol.size()>0) {
								AdmPerfilRol perfilRol = perfilesRol.get(0);
								perfilRol.setGrupopordefecto("N");
								this.admPerfilRolMapper.updateByPrimaryKey(perfilRol);
							}
							AdmPerfilRol recordPerfilRol = new AdmPerfilRol();
							recordPerfilRol.setFechamodificacion(new Date());
							recordPerfilRol.setGrupopordefecto(rolesAsignados.getLocal());
							recordPerfilRol.setIdinstitucion(Short.valueOf(institucion));
							recordPerfilRol.setIdperfil(usuarioUpdateDTO.getIdGrupo());
							recordPerfilRol.setIdrol(rolesAsignados.getValue());
							recordPerfilRol.setUsumodificacion(usuarios.get(0).getIdusuario());
							this.admPerfilRolMapper.insert(recordPerfilRol );
							
						}else {*/
							AdmPerfilRol recordPerfilRol = new AdmPerfilRol();
							recordPerfilRol.setFechamodificacion(new Date());
							recordPerfilRol.setGrupopordefecto("N");
							recordPerfilRol.setIdinstitucion(Short.valueOf(institucion));
							recordPerfilRol.setIdperfil(usuarioUpdateDTO.getIdGrupo());
							recordPerfilRol.setIdrol(rolesAsignados.getValue());
							recordPerfilRol.setUsumodificacion(usuarios.get(0).getIdusuario());
							this.admPerfilRolMapper.insert(recordPerfilRol );
						//}
					}
					
					
					
				}

		}else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			
			Error error = new Error();
			
			error.setMessage("administracion.grupo.duplicado");
			updateResponseDTO.setError(error );
		}
		

			

		return updateResponseDTO;
		
		
		
		
	}

	@Override
	public UpdateResponseDTO updateGrupoDefecto(UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		String dni = nifInstitucion.substring(0,9);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		

		//Buscamos el perfil para ver si ya existe. En caso de que no exista
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		AdmPerfilRolExample example = new AdmPerfilRolExample();
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).
		andIdrolEqualTo(usuarioUpdateDTO.getRolesAsignados()[0].getValue()).andGrupopordefectoEqualTo("S");

		List<AdmPerfilRol> perfilesRol = this.admPerfilRolMapper.selectByExample(example );
		if (null != perfilesRol && perfilesRol.size()>0) {
			for (AdmPerfilRol admPerfilRol : perfilesRol) {
				admPerfilRol.setGrupopordefecto("N");
				this.admPerfilRolMapper.updateByPrimaryKeySelective(admPerfilRol);
			}
		}
		AdmPerfilRol record = new AdmPerfilRol();
		record.setFechamodificacion(new Date());
		record.setGrupopordefecto("S");
		record.setIdperfil(usuarioUpdateDTO.getIdGrupo());
		record.setIdrol(usuarioUpdateDTO.getRolesAsignados()[0].getValue());
		record.setIdinstitucion(Short.valueOf(institucion));
		record.setUsumodificacion(usuario.getIdusuario());
		this.admPerfilRolMapper.updateByPrimaryKeySelective(record );
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		updateResponseDTO.setStatus(SigaConstants.KO);
		
		return updateResponseDTO;
	}

}
