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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

		return new UserCgae(dni, null, null, null);
	}

	public UserDetails loadUserByUsername(UserCgae user) throws UsernameNotFoundException {

		String dni = user.getDni();
//		String grupo = user.getGrupo();
		String institucion = user.getInstitucion();
		ControlRequestItem controlItem = new ControlRequestItem();
		HashMap<String, String> response = new HashMap<String, String>();

		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		// Obtenemos el Usuario para comprobar todas sus instituciones

		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);

		/*
		 * if (usuarios == null || usuarios.isEmpty()) { Error error = new
		 * Error(); error.setCode(400); error.setDescription("400");
		 * response.setError(error); return response; }
		 */

		List<String> idperfiles = new ArrayList<String>();

		// Obtenemos todos los perfiles del Usuario para cargar sus puntos de
		// Menú
		AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();

		exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion))
				.andIdusuarioEqualTo(usuarios.get(0).getIdusuario());
		List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoMapper.selectByExample(exampleUsuarioPerfil);

		for (AdmUsuariosEfectivosPerfil perfil : perfiles) {
			idperfiles.add("'" + perfil.getIdperfil() + "'");
		}

		controlItem.setInstitucion(institucion);
		String str = idperfiles.toString().replace("[", "").replace("]", "");
		controlItem.setIdGrupo(str);

		// Añadimos los permisos a la lista
		List<PermisoEntity> permisos = this.admUsuariosExtendsMapper.getAccessControlsWithOutProcess(controlItem);
		if (null != permisos && permisos.size() > 0) {
			for (PermisoEntity permisoEntity : permisos) {
				response.put(permisoEntity.getData(), permisoEntity.getDerechoacceso());
			}

		}

		// TODO: Añadir grupo
		return new UserCgae(dni, null, institucion, response);
	}

}
