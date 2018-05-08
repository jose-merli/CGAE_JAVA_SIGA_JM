package org.itcgae.siga.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoEntity;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmTiposaccesoMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenProcesosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserPrincipalCgae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("AdmUsuariosMapper") 
public class SigaUserDetailsService implements UserDetailsService  {

	
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
	
	@Override
	//TODO: Añadir llamada a bbdd para recuperar el usuario
	public UserDetails loadUserByUsername(String nifInstitucion) throws UsernameNotFoundException {
		
		ControlRequestItem controlItem = new ControlRequestItem();
		HashMap<String,String> response= new HashMap<String,String>();
		//Cargamos el Dni del Token
				
		//String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(authorization);
		
		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));

		String dni = nifInstitucion.substring(0,9);
		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		//Obtenemos el Usuario para comprobar todas sus instituciones

		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);
		
		/*if (usuarios == null || usuarios.isEmpty()) {
			Error error = new Error();
			error.setCode(400);
			error.setDescription("400");
			response.setError(error);
			return response;
		}*/

			List<String> idperfiles = new ArrayList<String>();

			//Obtenemos todos los perfiles del Usuario para cargar sus puntos de Menú
			AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();
			
			//TODO: Esta fallando aqui porque no encuentra mi usuario en la anterior busqueda usuarioMapper.selectByExample(usuarioExample) 
			//DNI 51120235 e id institucion 2000
			exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdusuarioEqualTo(usuarios.get(0).getIdusuario());
			List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoMapper.selectByExample(exampleUsuarioPerfil);
			
			/*if (perfiles == null) {
				Error error = new Error();
				error.setCode(400);
				error.setDescription("400");
				response.setError(error);
				return response;
			}*/
			for(AdmUsuariosEfectivosPerfil perfil:perfiles){
				idperfiles.add("'" + perfil.getIdperfil() +"'");
			}
			//Nos quedamos con todos los perfiles para realizar la búsqueda.
			String[] listParameters = nifInstitucion.split("-");
			controlItem.setInstitucion(listParameters[2]);
			String str = idperfiles.toString().replace("[", "").replace("]", "");
			controlItem.setIdGrupo(str);
	
			//Añadimos los permisos a la lista
			List<PermisoEntity> permisos =  this.admUsuariosExtendsMapper.getAccessControlsWithOutProcess(controlItem);
			if (null != permisos && permisos.size()>0) {
				for (PermisoEntity permisoEntity : permisos) {
					response.put(permisoEntity.getData(), permisoEntity.getDerechoacceso());
				}
				
			}
			//return response;
	
			
		UserCgae user = new UserCgae();
		user.setUsername(nifInstitucion);
		user.setPermisos(response);
		return new UserPrincipalCgae(user);
	}
	
	

}
