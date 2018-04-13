package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;

public class AdmUsuariosSqlExtendsProvider {

	

	
	/***
	 * Build the SQL query to get users information depending on a filter which in this case is the UsuarioRequestDTO object. 
	 * @param numPagina The number of the page that will be displayed in web page. Each page contains a portion of users with their information.
	 * @param usuarioRequestDTO The filter that some web user uses on web page to obtain users information.
	 * @return The SQL query for data base.
	 */
	public String getUsersByFilter(int numPagina, UsuarioRequestDTO usuarioRequestDTO){
		SQL sql = new SQL();
		
		sql.SELECT("USUARIOS.DESCRIPCION");
		sql.SELECT("USUARIOS.NIF");
		sql.SELECT("USUARIOS.FECHAALTA");
		sql.SELECT("USUARIOS.ACTIVO");
		sql.SELECT("USUARIOS.CODIGOEXT");
		sql.SELECT("USUARIOS.IDINSTITUCION");
		sql.SELECT("USUARIOS.IDUSUARIO");
		sql.SELECT("f_siga_roles_usuario(USUARIOS.idinstitucion, USUARIOS.idusuario) as ROLES");
		sql.FROM("ADM_USUARIOS USUARIOS");
		
		// comprobacion campo rol del body para aplicar filtro
		if(!usuarioRequestDTO.getRol().equalsIgnoreCase("")){
			sql.WHERE("exists (select 1 from ADM_PERFIL_ROL ROL,"
					+ " ADM_USUARIO_EFECTIVO UEF where ROL.IDINSTITUCION = USUARIOS.IDINSTITUCION"
					+ " and ROL.IDROL = UEF.IDROL"
					+ " and UEF.IDINSTITUCION = ROL.IDINSTITUCION"
					+ " and UEF.IDUSUARIO = USUARIOS.IDUSUARIO and ROL.IDROL = '" + usuarioRequestDTO.getRol() + "')");		
		}
		//comprobacion campo grupo del body para aplicar filtro
		if(!usuarioRequestDTO.getGrupo().equalsIgnoreCase("")){
			sql.WHERE("exists (select 1 from ADM_USUARIOS_EFECTIVOS_PERFIL UEP where UEP.IDINSTITUCION = USUARIOS.IDINSTITUCION ");
			sql.WHERE("UEP.IDUSUARIO = USUARIOS.IDUSUARIO and UEP.IDPERFIL = '" + usuarioRequestDTO.getGrupo() + "')");
		}
		// comprobacion campo nombreApellidos del body para aplicar filtro
		if(!usuarioRequestDTO.getNombreApellidos().equalsIgnoreCase("")){
			sql.WHERE("USUARIOS.DESCRIPCION = '" + usuarioRequestDTO.getNombreApellidos() + "'");
		}
		
		// comprobacion campo nif del body para aplicar filtro
		if(!usuarioRequestDTO.getNif().equalsIgnoreCase("")){
			sql.WHERE("USUARIOS.NIF = '" + usuarioRequestDTO.getNif() + "'");
		}
		
		// El campo idinstitucion es obligatorio en el body. El filtro se aplica siempre
		sql.WHERE("USUARIOS.IDINSTITUCION = '" + usuarioRequestDTO.getIdInstitucion() + "'");
		sql.WHERE("USUARIOS.ACTIVO = '" + usuarioRequestDTO.getActivo() + "'");
		sql.ORDER_BY("USUARIOS.DESCRIPCION ASC");
		
		return sql.toString();
	}
	
	/***
	 * Build the SQL query to actualize some characteristics, such as ACTIVO or CODIGOEXT from ADM_USUARIOS table.
	 * @param usuarioUpdateDTO Object that contains the information for actualize the table.
	 * @return the SQL query
	 */
	public String updateUsersAdmUserTable(UsuarioUpdateDTO usuarioUpdateDTO){
		SQL sql = new SQL();
		sql.UPDATE("ADM_USUARIOS");
		sql.SET("ACTIVO = '" + usuarioUpdateDTO.getActivo() + "'");
		sql.SET("CODIGOEXT = '" + usuarioUpdateDTO.getCodigoExterno() + "'");
		// campos obligatorios. filtro para actualizar solo determinados campos
		sql.WHERE("IDUSUARIO = '" + usuarioUpdateDTO.getIdUsuario() + "'");
		sql.WHERE("IDINSTITUCION = '" + usuarioUpdateDTO.getIdInstitucion() + "'");
		
		return sql.toString();
	}
	
	/**
	 * Build the SQL query to actualize some characteristics, such as DESCRIPCION from ADM_PERFIL
	 * @param usuarioUpdateDTO Object that contains the information for actualize the table.
	 * @return the SQL query
	 */
	public String updateUsersAdmPerfilTable(UsuarioUpdateDTO usuarioUpdateDTO){
		SQL sql = new SQL();
		sql.UPDATE("ADM_USUARIOS_EFECTIVOS_PERFIL");
		
		// comprobacion para actualizar campo Perfil
		if(!usuarioUpdateDTO.getGrupo().equalsIgnoreCase(""))
		{
			sql.SET("IDPERFIL = '" + usuarioUpdateDTO.getGrupo() + "'");
			sql.SET("FECHAMODIFICACION = SYSDATE");
		}
		
		// campos obligatorios. filtro para actualizar solo determinados campos
		sql.WHERE("IDUSUARIO = '" + usuarioUpdateDTO.getIdUsuario() + "'");
		sql.WHERE("IDINSTITUCION = '" + usuarioUpdateDTO.getIdInstitucion() + "'");
		sql.WHERE("IDROL = '" + usuarioUpdateDTO.getRol() + "'");
		
		return sql.toString();
	}
	
	
	
	/**
	 * Build the SQL query to create a new user row for adm_usuario table
	 * @param usuarioCreateDTO Object that contains the information for actualize the table.
	 * @return the SQL query
	 */
	public String createUserAdmUsuariosTable(UsuarioCreateDTO usuarioCreateDTO, Integer usuarioModificacion){
		SQL sql = new SQL();
	        
		sql.INSERT_INTO("ADM_USUARIOS");
		sql.VALUES("IDUSUARIO", ("(select MAX(IDUSUARIO)  + 1 from ADM_USUARIOS  where IDINSTITUCION = " + "'"
				+ usuarioCreateDTO.getIdInstitucion() + "' )")); // Realmente este idInstitucion se coge del variable session
		sql.VALUES("DESCRIPCION", "'" + usuarioCreateDTO.getNombreApellidos() + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuarioModificacion)+"'");
		sql.VALUES("IDINSTITUCION", "'" + usuarioCreateDTO.getIdInstitucion() + "'");
		sql.VALUES("NIF", "'" + usuarioCreateDTO.getNif() + "'");
		sql.VALUES("ACTIVO", "'" + usuarioCreateDTO.getActivo() + "'");
		sql.VALUES("FECHAALTA", "SYSDATE");
		sql.VALUES("IDLENGUAJE", "'" + usuarioCreateDTO.getIdLenguaje() + "'");
		//sql.VALUES("CODIGOEXT", "' '");

		return sql.toString();
	}
	
	
	/**
	 * Build the SQL query to create a new user row for adm_usuario_efectivo table
	 * @param usuarioCreateDTO Object that contains the information for actualize the table.
	 * @return the SQL query
	 */
	public String createUserAdmUsuarioEfectivoTable(UsuarioCreateDTO usuarioCreateDTO, Integer usuarioModificacion){
		SQL sql = new SQL();

		sql.INSERT_INTO("ADM_USUARIO_EFECTIVO");
		sql.VALUES("IDUSUARIO", ("(select MAX(IDUSUARIO) from ADM_USUARIOS  where IDINSTITUCION = " + "'"
				+ usuarioCreateDTO.getIdInstitucion() + "' )"));
		sql.VALUES("IDINSTITUCION", "'" + usuarioCreateDTO.getIdInstitucion() + "'");
		sql.VALUES("IDROL", "'" + usuarioCreateDTO.getRol() + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuarioModificacion)+"'");
		//sql.VALUES("NUMSERIE",  "");
		
		return sql.toString();
	}
	
	/**
	 * Build the SQL query to create a new user row for adm_usuarios_efectivo_perfil table
	 * @param usuarioCreateDTO Object that contains the information for actualize the table.
	 * @return the SQL query
	 */
	public String createUserAdmUsuariosEfectivoPerfilTable(UsuarioCreateDTO usuarioCreateDTO, Integer usuarioModificacion){
		SQL sql = new SQL();
		sql.INSERT_INTO("ADM_USUARIOS_EFECTIVOS_PERFIL");

		
		sql.VALUES("IDUSUARIO", ("(select MAX(IDUSUARIO) from ADM_USUARIOS  where IDINSTITUCION = " + "'"
				+ usuarioCreateDTO.getIdInstitucion() + "' )"));
		sql.VALUES("IDINSTITUCION", "'" + usuarioCreateDTO.getIdInstitucion() + "'");
		sql.VALUES("IDROL", "'" + usuarioCreateDTO.getRol() + "'");
		sql.VALUES("IDPERFIL", "'" + usuarioCreateDTO.getGrupo() + "'");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuarioModificacion)+"'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE"); 
		
		return sql.toString();
	}
	
	
	public String enableUser(UsuarioDeleteDTO usuarioDeleteDTO){
		SQL sql = new SQL();
		String whereIdUsuarios = "";
		int size = usuarioDeleteDTO.getIdUsuario().size();
		
		sql.UPDATE("ADM_USUARIOS");
		sql.SET("FECHA_BAJA = NULL");
		sql.SET("ACTIVO = 'S'");
		
		for (int i = 0; i < size - 1; i++) {
			whereIdUsuarios = whereIdUsuarios.concat(usuarioDeleteDTO.getIdUsuario().get(i) + ",");
		}
		whereIdUsuarios = whereIdUsuarios.concat(usuarioDeleteDTO.getIdUsuario().get(size-1));
		sql.WHERE("IDUSUARIO" + " IN (" + whereIdUsuarios + " )");
		sql.WHERE("IDINSTITUCION = '" + usuarioDeleteDTO.getIdInstitucion() + "'");
		
		return sql.toString();
	}
	
	
	public String disableUser(UsuarioDeleteDTO usuarioDeleteDTO){
		SQL sql = new SQL();
		String whereIdUsuarios = "";
		int size = usuarioDeleteDTO.getIdUsuario().size();
		
		sql.UPDATE("ADM_USUARIOS");
		sql.SET("FECHA_BAJA = SYSDATE");
		sql.SET("ACTIVO = 'N'");
		
		for (int i = 0; i < size - 1; i++) {
			whereIdUsuarios = whereIdUsuarios.concat(usuarioDeleteDTO.getIdUsuario().get(i) + ",");
		}
		whereIdUsuarios = whereIdUsuarios.concat(usuarioDeleteDTO.getIdUsuario().get(size-1));
		sql.WHERE("IDUSUARIO" + " IN (" + whereIdUsuarios + " )");
		sql.WHERE("IDINSTITUCION = '" + usuarioDeleteDTO.getIdInstitucion() + "'");
		
		return sql.toString();
	}
	
}
