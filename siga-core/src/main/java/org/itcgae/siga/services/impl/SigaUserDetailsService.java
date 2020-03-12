package org.itcgae.siga.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoEntity;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmUsuarioEfectivo;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.mappers.AdmTiposaccesoMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmRolExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenProcesosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.gen.services.impl.MenuServiceImpl;
import org.itcgae.siga.security.UserCgae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("AdmUsuariosMapper")
public class SigaUserDetailsService implements UserDetailsService {
	
	Logger LOGGER = Logger.getLogger(SigaUserDetailsService.class);

	@Autowired
	GenMenuExtendsMapper menuExtend;

	@Autowired
	CenInstitucionExtendsMapper institucionMapper;

	@Autowired
	AdmPerfilExtendsMapper perfilMapper;

	@Autowired
	AdmUsuariosMapper usuarioMapper;

	@Autowired
	GenProcesosExtendsMapper permisosMapper;

	@Autowired
	AdmTiposaccesoMapper tiposAccesoMapper;

	@Autowired
	AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	AdmUsuariosEfectivosPerfilMapper admUsuariosEfectivoMapper;
	
	@Autowired
	AdmRolExtendsMapper admRol;

	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

		return new UserCgae(dni, null, null, null,null,null, null, null);
	}

	public UserDetails loadUserByUsername(UserCgae user) throws UsernameNotFoundException {

		String dni = user.getDni();
		String nombre = user.getNombre();
		String grupo = user.getGrupo();
		AdmRol rol = user.getRol();
		String institucion = user.getInstitucion();
		String letrado = user.getLetrado();
		ControlRequestItem controlItem = new ControlRequestItem();
		HashMap<String, String> response = new HashMap<String, String>();

		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		if (null != user.getDni()) {
			usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		}else{
			usuarioExample.createCriteria().andIdusuarioEqualTo(SigaConstants.IdUsuarioPuertaAtras).andIdinstitucionEqualTo(Short.valueOf(institucion));
		}
		// Obtenemos el Usuario para comprobar todas sus instituciones

		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);

		
		if (null == usuarios || usuarios.size() ==0) {
			insertaUsuBd(user);
			usuarios = usuarioMapper.selectByExample(usuarioExample);
		}
		if (null != usuarios && usuarios.size() > 0) {
			List<String> idperfiles = new ArrayList<String>();
			if (!(null != user.getDni())) {
				user.setDni(usuarios.get(0).getNif());
				dni = usuarios.get(0).getNif();
			}
			
			String[] grupos = grupo.split(",");
			if (null != grupos && grupos.length>0) {
				List<PermisoEntity> permisos = new ArrayList<PermisoEntity>();
				for (int j = 0; j < grupos.length; j++) {
					
				AdmPerfilExample examplePerfil = new AdmPerfilExample();
				examplePerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion)).andIdperfilEqualTo(grupos[j]);
				List<AdmPerfil> perfilesPuertaAtras = perfilMapper.selectByExample(examplePerfil );
				
				if(null != perfilesPuertaAtras && perfilesPuertaAtras.size()>0) {
					/*
					 * Tratamos solo el grupo seleccionado en el combo
					 */
					for (AdmPerfil perfil : perfilesPuertaAtras) {
						idperfiles.add("'" + perfil.getIdperfil() + "'");
					}
	
				}else {
					List<AdmRol> roles = admRol.selectRolAccesoByExample(grupo );
					if (null != roles && roles.size()>0) {
						AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();
						exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion))
									.andIdusuarioEqualTo(usuarios.get(0).getIdusuario()).andIdrolEqualTo(roles.get(0).getIdrol()).andFechaBajaIsNull();
						List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoMapper.selectByExample(exampleUsuarioPerfil);
						letrado = roles.get(0).getLetrado().toString();
						if (letrado.equals("0")) {
							letrado = "N";
						}else{
							letrado = "S";
						}
						if(null != perfiles && perfiles.size()>0) {
							/*
							 * Tratamos todos los grupos del Rol
							 */
							for (AdmUsuariosEfectivosPerfil perfil : perfiles) {
								idperfiles.add("'" + perfil.getIdperfil() + "'");
							}
						}
					}else {
						if(rol != null) {
							AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();
						
							exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion))
										.andIdusuarioEqualTo(usuarios.get(0).getIdusuario()).andIdrolEqualTo(rol.getIdrol()).andFechaBajaIsNull();
							List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoMapper.selectByExample(exampleUsuarioPerfil);
							letrado = rol.getLetrado().toString();
							if (letrado.equals("0")) {
								letrado = "N";
							}else{
								letrado = "S";
							}
							if(null != perfiles && perfiles.size()>0) {
								/*
								 * Tratamos todos los grupos del Rol
								 */
								for (AdmUsuariosEfectivosPerfil perfil : perfiles) {
									idperfiles.add("'" + perfil.getIdperfil() + "'");
								}
							}
						}
					}
				}
				
						controlItem.setInstitucion(institucion);
						String str = idperfiles.toString().replace("[", "").replace("]", "");
						controlItem.setIdGrupo(str);
				
						// Añadimos los permisos a la lista
						List<PermisoEntity> permisosPorGrupo = this.admUsuariosExtendsMapper.getAccessControlsWithOutProcess(controlItem);
						permisos.addAll(permisosPorGrupo);
						if (null != permisosPorGrupo && permisosPorGrupo.size() > 0) {
						}else {
							 throw new BadCredentialsException("El usuario no tiene permisos");
						}
		
				}
				if (null != permisos && permisos.size() > 0) {
					for (PermisoEntity permisoEntity : permisos) {
						response.put(permisoEntity.getData(), permisoEntity.getDerechoacceso());
					}
					return new UserCgae(dni, grupo, institucion, response,idperfiles,letrado, rol, nombre);
				}else {
					 throw new BadCredentialsException("El usuario no tiene permisos");
				}	
			}
		}else {
			throw new BadCredentialsException("Ha ocurrido un problema al crear el usuario en base de datos");
		}

		return null;
			
		
	}
	
	public String getGrupoCAS(HttpServletRequest request) {
		String roles = (String) request.getHeader("CAS-roles");
		String defaultRole = null;
		String [] roleAttributes;
		String [] rolesList = roles.split("::");
		if(rolesList.length > 1) {
			defaultRole = (String) request.getHeader("CAS-defaultRole");
			roleAttributes = defaultRole.split(" ");
		}else {
			roleAttributes = roles.split(" ");
		}
			
		if(roleAttributes.length == 2) {
			return SigaConstants.getTipoUsuario(roleAttributes[1]);
		}else {
			return SigaConstants.getTipoUsuario(roleAttributes[2]);
		}
	}
	
	public String getInstitucionCAS(HttpServletRequest request) {
		String roles = (String) request.getHeader("CAS-roles");
		String defaultRole = null;
		String [] roleAttributes;
		String [] rolesList = roles.split("::");
		if(rolesList.length > 1) {
			defaultRole = (String) request.getHeader("CAS-defaultRole");
			roleAttributes = defaultRole.split(" ");
		}else {
			roleAttributes = roles.split(" ");
		}
			
		return getidInstitucionByCodExterno(roleAttributes[0]).get(0).getIdinstitucion().toString();
	}
	
	public List<CenInstitucion> getidInstitucionByCodExterno(String codExterno) {
		if(codExterno != null && !codExterno.isEmpty()) {
			CenInstitucionExample example = new CenInstitucionExample();
			example.createCriteria().andCodigoextEqualTo(codExterno);
			
			return institucionMapper.selectByExample(example);
		}else {
			return null;
		}
	}

	public AdmRol getRolLoginMultiple(String grupo) {
		AdmRol rol = admRol.selectByPrimaryKey(grupo);
		return rol;
	}

	private void insertaUsuBd(UserCgae user) {
		UsuarioCreateDTO usuDTO = new UsuarioCreateDTO();
		CenInstitucion institucion = institucionMapper.selectByPrimaryKey(Short.valueOf(user.getInstitucion()));
		usuDTO.setActivo("S");
		usuDTO.setFechaAlta(new Date());
		usuDTO.setIdInstitucion(user.getInstitucion());
		usuDTO.setIdLenguaje(institucion.getIdlenguaje());
		usuDTO.setGrupo(user.getGrupo());
		usuDTO.setRol(user.getRol().getIdrol());
		usuDTO.setNombreApellidos(user.getNombre());
		usuDTO.setNif(user.getDni());
		// Obtenemos el nuevo idusuario
		try {
			LOGGER.debug("Se intenta crear el usuario " + user.getDni() + " en base de datos.");
			admUsuariosExtendsMapper.createUserAdmUsuariosTable(usuDTO,new Integer("-1"));	
			admUsuariosExtendsMapper.createUserAdmUsuarioEfectivoTable(usuDTO, new Integer("-1"));
			admUsuariosExtendsMapper.createUserAdmUsuariosEfectivoPerfilTable(usuDTO, new Integer("-1"));
		}catch(Exception e) {
			LOGGER.error("Se ha producido un error al crear el usuario en base de datos", e);
		}
	}
}
