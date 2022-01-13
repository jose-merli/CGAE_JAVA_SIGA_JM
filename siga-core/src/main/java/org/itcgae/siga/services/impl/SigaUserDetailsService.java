package org.itcgae.siga.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoEntity;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmPerfilRol;
import org.itcgae.siga.db.entities.AdmPerfilRolExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmUsuarioEfectivo;
import org.itcgae.siga.db.entities.AdmUsuarioEfectivoKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.mappers.AdmPerfilRolMapper;
import org.itcgae.siga.db.mappers.AdmTiposaccesoMapper;
import org.itcgae.siga.db.mappers.AdmUsuarioEfectivoMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmRolExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenProcesosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.security.UserCgae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	AdmPerfilRolMapper admPerfilRolMapper;
	
	@Autowired
	AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	AdmUsuarioEfectivoMapper admUsuarioEfectivoMapper;
	
	@Autowired
	AdmUsuariosEfectivosPerfilMapper admUsuariosEfectivoPerfilMapper;
	
	@Autowired
	AdmRolExtendsMapper admRol;
	
	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

		return new UserCgae(dni, null, null, null,null,null, null, null);
	}

	public UserDetails loadUserByUsername(UserCgae user) throws UsernameNotFoundException, Exception {

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
				
				if(null != perfilesPuertaAtras && perfilesPuertaAtras.size()>0 && letrado != null) {
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
						List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoPerfilMapper.selectByExample(exampleUsuarioPerfil);
						letrado = roles.get(0).getLetrado().toString();
						if (letrado.equals("0")) {
							letrado = "N";
						}else{
							letrado = "S";
						}
						if(null == perfiles || perfiles.size()==0) {
							insertaUsuEfectivoBd(usuarios.get(0),roles.get(0));
							perfiles = admUsuariosEfectivoPerfilMapper.selectByExample(exampleUsuarioPerfil);
						}
						/*
						 * Tratamos todos los grupos del Rol
						 */
						for (AdmUsuariosEfectivosPerfil perfil : perfiles) {
							idperfiles.add("'" + perfil.getIdperfil() + "'");
						}
						
					}else {
						if(rol != null) {
							AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();
						
							exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion))
										.andIdusuarioEqualTo(usuarios.get(0).getIdusuario()).andIdrolEqualTo(rol.getIdrol()).andFechaBajaIsNull();
							List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoPerfilMapper.selectByExample(exampleUsuarioPerfil);
							letrado = rol.getLetrado().toString();
							if (letrado.equals("0")) {
								letrado = "N";
							}else{
								letrado = "S";
							}
							if(null == perfiles || perfiles.size()==0) {
								insertaUsuEfectivoBd(usuarios.get(0),rol);
								perfiles = admUsuariosEfectivoPerfilMapper.selectByExample(exampleUsuarioPerfil);
							}
							/*
							 * Tratamos todos los grupos del Rol
							 */
							for (AdmUsuariosEfectivosPerfil perfil : perfiles) {
								idperfiles.add("'" + perfil.getIdperfil() + "'");
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
							LOGGER.error("El perfil "+str + " de la institución " + institucion + " no tiene permisos asociados");
							throw new BadCredentialsException("El perfil seleccionado no tiene permisos");
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
			}else {
				throw new BadCredentialsException("Perfil del usuario erróneo");
			}
		}else {
			throw new BadCredentialsException("Ha ocurrido un problema al crear el usuario en base de datos");
		}
		
	}
	
	public String getGrupoCAS(HttpServletRequest request) {
		String defaultRole = null;
		String [] roleAttributes;
		String codigoRol = "";
		String tipoUsuario = "";
		int primero, ultimo =0;
		defaultRole = (String) request.getHeader("CAS-defaultRole");
		if(defaultRole != null) {
			roleAttributes = defaultRole.split(" ");
		}else {
			String roles = (String) request.getHeader("CAS-roles");
			String [] rolesList = roles.split("::");
			if(rolesList.length > 1) {
				defaultRole = rolesList[0];
				roleAttributes = defaultRole.split(" ");
			}else {
				roleAttributes = roles.split(" ");
			}
		}
			
		if(isNumeric(roleAttributes[1])) { //Si es númerico el segundo atributo
			primero = 2; //el rol empieza en el tercero
			if(roleAttributes[roleAttributes.length-1].equalsIgnoreCase("Residente")) { //Si el último atributo es Residente
				ultimo = roleAttributes.length -2; //la ultima palabra del rol es la penultima
			}else {
				ultimo = roleAttributes.length -1; //Si no, la ultima palabra del rol es la ultima
			}
		}else {
			primero = 1; //Si no es numerico, el rol empieza en el segundo atributo
			ultimo = roleAttributes.length -1; //Acaba en el ultimo atributo
		}
		
		for(int i=primero;i<=ultimo ; i++) {
			String constructRol = "";
			if (i != ultimo) {
				constructRol += roleAttributes[i];

				constructRol += " ";

			} else {
				constructRol += roleAttributes[i];

			}
			tipoUsuario += constructRol;
		}

		codigoRol = SigaConstants.getTipoUsuario(tipoUsuario);
		
		return codigoRol;
	}
	
	public String getInstitucionCAS(HttpServletRequest request) {
		String defaultRole = null;
		String [] roleAttributes;
		defaultRole = (String) request.getHeader("CAS-defaultRole");
		if(defaultRole != null) {
			roleAttributes = defaultRole.split(" ");
		}else {
			String roles = (String) request.getHeader("CAS-roles");
			String [] rolesList = roles.split("::");
			if(rolesList.length > 1) {
				defaultRole = rolesList[0];
				roleAttributes = defaultRole.split(" ");
			}else {
				roleAttributes = roles.split(" ");
			}
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
	
	public AdmRol getRolLogin(HttpServletRequest request) {
		String defaultRole = null;
		String [] roleAttributes;
		String tipoUsuario = "";
		int primero, ultimo=0;
		defaultRole = (String) request.getHeader("CAS-defaultRole");
		if(defaultRole != null) {
			roleAttributes = defaultRole.split(" ");
		}else {
			String roles = (String) request.getHeader("CAS-roles");
			String [] rolesList = roles.split("::");
			if(rolesList.length > 1) {
				defaultRole = rolesList[0];
				roleAttributes = defaultRole.split(" ");
			}else {
				roleAttributes = roles.split(" ");
			}
		}
		
		if(isNumeric(roleAttributes[1])) { //Si es númerico el segundo atributo
			primero = 2; //el rol empieza en el tercero
			if(roleAttributes[roleAttributes.length-1].equalsIgnoreCase("Residente")) { //Si el último atributo es Residente
				ultimo = roleAttributes.length -2; //la ultima palabra del rol es la penultima
			}else {
				ultimo = roleAttributes.length -1; //Si no, la ultima palabra del rol es la ultima
			}
		}else {
			primero = 1; //Si no es numerico, el rol empieza en el segundo atributo
			ultimo = roleAttributes.length -1; //Acaba en el ultimo atributo
		}
		
		for(int i=primero;i<=ultimo ; i++) {
			String constructRol = "";
			if (i != ultimo) {
				constructRol += roleAttributes[i];

				constructRol += " ";

			} else {
				constructRol += roleAttributes[i];

			}
			tipoUsuario += constructRol;
		}
		
		List<AdmRol> roles = admRol.selectRolAccesoByExample(tipoUsuario);
		
		if(roles != null && !roles.isEmpty())
			return roles.get(0);
		else
			return null;
	}

	private void insertaUsuBd(UserCgae user) throws Exception {
		
		try {
			AdmUsuarios usuario = new AdmUsuarios();
			CenInstitucion institucion = institucionMapper.selectByPrimaryKey(Short.valueOf(user.getInstitucion()));
			usuario.setActivo("S");
			usuario.setDescripcion(user.getNombre());
			usuario.setFechaalta(new Date());
			usuario.setFechamodificacion(new Date());
			usuario.setUsumodificacion(new Integer("-1"));
			usuario.setIdinstitucion(institucion.getIdinstitucion());
			usuario.setIdlenguaje(institucion.getIdlenguaje());
			usuario.setNif(user.getDni());
			
			admUsuariosExtendsMapper.insert(usuario);
			
			LOGGER.debug("Insertamos en bd el usuario efectivo y sus perfiles");
			insertaUsuEfectivoBd(usuario, user.getRol());
			
		}catch(Exception e) {
			LOGGER.error("Se ha producido un error al crear el usuario efectivo en base de datos", e);
			throw new Exception("Se ha producido un error al crear el usuario efectivo en base de datos", e);
		}
		
	}
	
	private void insertaUsuEfectivoBd(AdmUsuarios usuario, AdmRol rol) {
		
		try {
			LOGGER.debug("Buscamos el usuario efectivo " + usuario.getNif() + " en base de datos.");
			AdmUsuarioEfectivoKey key = new AdmUsuarioEfectivoKey();
			key.setIdinstitucion(usuario.getIdinstitucion());
			key.setIdusuario(usuario.getIdusuario());
			key.setIdrol(rol.getIdrol());
			AdmUsuarioEfectivo admUsuarioEfectivo = admUsuarioEfectivoMapper.selectByPrimaryKey(key);
			
			if(admUsuarioEfectivo == null) {
				LOGGER.debug("No se ha encontrado usuario efectivo: "+usuario.getNif());
				admUsuarioEfectivo = new AdmUsuarioEfectivo();
				admUsuarioEfectivo.setIdinstitucion(usuario.getIdinstitucion());
				admUsuarioEfectivo.setIdrol(rol.getIdrol());
				admUsuarioEfectivo.setIdusuario(usuario.getIdusuario());
				admUsuarioEfectivo.setUsumodificacion(new Integer("-1"));
				admUsuarioEfectivo.setFechamodificacion(new Date());
				LOGGER.debug("Se intenta crear el usuario efectivo " + usuario.getNif() + " en base de datos.");
				admUsuarioEfectivoMapper.insert(admUsuarioEfectivo);
			}
			
			LOGGER.debug("Buscamos el perfil "+rol.getDescripcion()+ " del usuario efectivo " + usuario.getNif() + " en base de datos.");
			AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();
			exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(usuario.getIdinstitucion()))
						.andIdusuarioEqualTo(usuario.getIdusuario()).andIdrolEqualTo(rol.getIdrol()).andFechaBajaIsNull();
			List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoPerfilMapper.selectByExample(exampleUsuarioPerfil);
			
			if(perfiles == null || perfiles.size() == 0) {
				AdmPerfilRolExample example = new AdmPerfilRolExample();
				example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion()).andIdrolEqualTo(rol.getIdrol());		
				List<AdmPerfilRol> admPerfilRol = admPerfilRolMapper.selectByExample(example);
				
				AdmUsuariosEfectivosPerfil admUsuariosEfectivosPerfil = new AdmUsuariosEfectivosPerfil();
				admUsuariosEfectivosPerfil.setIdinstitucion(usuario.getIdinstitucion());
				admUsuariosEfectivosPerfil.setIdperfil(admPerfilRol.get(0).getIdperfil());
				admUsuariosEfectivosPerfil.setIdrol(rol.getIdrol());
				admUsuariosEfectivosPerfil.setIdusuario(usuario.getIdusuario());
				admUsuariosEfectivosPerfil.setUsumodificacion(new Integer("-1"));
				admUsuariosEfectivosPerfil.setFechamodificacion(new Date());
				
				admUsuariosEfectivoPerfilMapper.insert(admUsuariosEfectivosPerfil);
			}
		}catch(Exception e) {
			LOGGER.error("Se ha producido un error al crear el usuario efectivo en base de datos", e);
		}
	}
	
	public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }
		
}
