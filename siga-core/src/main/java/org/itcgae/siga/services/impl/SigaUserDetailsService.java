package org.itcgae.siga.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoEntity;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmTiposaccesoMapper;
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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("AdmUsuariosMapper")
public class SigaUserDetailsService implements UserDetailsService {

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

		return new UserCgae(dni, null, null, null,null,null);
	}

	public UserDetails loadUserByUsername(UserCgae user) throws UsernameNotFoundException {

		String dni = user.getDni();
		String grupo = user.getGrupo();
		String institucion = user.getInstitucion();
		String letrado = user.getLetrado();
		ControlRequestItem controlItem = new ControlRequestItem();
		HashMap<String, String> response = new HashMap<String, String>();

		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		// Obtenemos el Usuario para comprobar todas sus instituciones

		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);

		
		if (null != usuarios && usuarios.size() >0) {
			
			List<String> idperfiles = new ArrayList<String>();
			
			
			
			
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
					if (null!= roles && roles.size()>0) {
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
					return new UserCgae(dni, grupo, institucion, response,idperfiles,letrado);
				}else {
					 throw new BadCredentialsException("El usuario no tiene permisos");
				}	
		}
			
		}else {
			 throw new AuthenticationCredentialsNotFoundException("Usuario no encontrado en la aplicación");
		}
		return null;
			
		
	}

}
