package org.itcgae.siga.db.services.adm.providers;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.db.mappers.AdmUsuariosSqlProvider;
import org.itcgae.siga.security.UserTokenUtils;

public class AdmUsuariosSqlExtendsProvider extends AdmUsuariosSqlProvider{

	

	
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
		sql.SELECT("f_siga_perfiles_usuario(USUARIOS.idinstitucion, USUARIOS.idusuario) as PERFIL");    
		sql.FROM("ADM_USUARIOS USUARIOS");
		
		// comprobacion campo rol del body para aplicar filtro
		if(null != usuarioRequestDTO.getRol() && !usuarioRequestDTO.getRol().equalsIgnoreCase("")){
			sql.WHERE("exists (select 1 from ADM_PERFIL_ROL ROL,"
					+ " ADM_USUARIO_EFECTIVO UEF where ROL.IDINSTITUCION = USUARIOS.IDINSTITUCION"
					+ " and ROL.IDROL = UEF.IDROL"
					+ " and UEF.IDINSTITUCION = ROL.IDINSTITUCION"
					+ " and UEF.IDUSUARIO = USUARIOS.IDUSUARIO and ROL.IDROL = '" + usuarioRequestDTO.getRol() + "')");		
		}
		//comprobacion campo grupo del body para aplicar filtro
		if(null != usuarioRequestDTO.getGrupo() && !usuarioRequestDTO.getGrupo().equalsIgnoreCase("")){
			sql.WHERE("exists (select 1 from ADM_USUARIOS_EFECTIVOS_PERFIL UEP where UEP.IDINSTITUCION = USUARIOS.IDINSTITUCION ");
			sql.WHERE("UEP.IDUSUARIO = USUARIOS.IDUSUARIO and UEP.IDPERFIL = '" + usuarioRequestDTO.getGrupo() + "')");
		}

		// comprobacion campo nombreApellidos del body para aplicar filtro
		if(null != usuarioRequestDTO.getNombreApellidos() &&  !usuarioRequestDTO.getNombreApellidos().equalsIgnoreCase("")){
			sql.WHERE("(UPPER(USUARIOS.DESCRIPCION) LIKE UPPER ('%" + usuarioRequestDTO.getNombreApellidos() + "%'))");
		}
		
		// comprobacion campo nif del body para aplicar filtro
		if(null != usuarioRequestDTO.getNif() && !usuarioRequestDTO.getNif().equalsIgnoreCase("")){
			sql.WHERE("(UPPER(USUARIOS.NIF) LIKE UPPER  ('%" + usuarioRequestDTO.getNif() + "%'))");
		}
		
		// El campo idinstitucion es obligatorio en el body. El filtro se aplica siempre
		sql.WHERE("USUARIOS.IDINSTITUCION = '" + usuarioRequestDTO.getIdInstitucion() + "'");
		
		// comprobacion campo activo del body para aplicar filtro
		if(null != usuarioRequestDTO.getActivo() && !usuarioRequestDTO.getActivo().equalsIgnoreCase("")){
			sql.WHERE("USUARIOS.ACTIVO = '" + usuarioRequestDTO.getActivo() + "'");
		}
		
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
	
	public String deleteUserGroup(UsuarioGrupoDeleteDTO usuarioDeleteDTO){
		SQL sql = new SQL();
		
		sql.UPDATE("ADM_PERFIL");
		sql.SET("FECHA_BAJA = SYSDATE");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuarioDeleteDTO.getIdUsuario().get(0) + "'");

		
		sql.WHERE("IDPERFIL = '" +  usuarioDeleteDTO.getidGrupo() +  "'");
		sql.WHERE("IDINSTITUCION = '" + usuarioDeleteDTO.getIdInstitucion() + "'");
		
		return sql.toString();
	}
	
	
	/***
	 * Build the SQL query to get users information depending on a filter which in this case is the UsuarioRequestDTO object. 
	 * @param numPagina The number of the page that will be displayed in web page. Each page contains a portion of users with their information.
	 * @param usuarioRequestDTO The filter that some web user uses on web page to obtain users information.
	 * @return The SQL query for data base.
	 */
	public String getUsersLog(HttpServletRequest usuarioRequestDTO){
		SQL sql = new SQL();
		String token = usuarioRequestDTO.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short institucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		sql.SELECT("USUARIOS.IDUSUARIO AS IDUSUARIO");
		sql.SELECT("USUARIOS.DESCRIPCION AS NOMBRE");
		sql.SELECT("USUARIOS.NIF AS DNI");
		sql.SELECT("TO_CHAR(ULTIMA_CONEXION, ' HH24:MI:SS || DD/MM/YYYY')  AS ULTIMACONEX");
		sql.SELECT("REC.DESCRIPCION AS IDIOMA");
		sql.SELECT("INST.NOMBRE AS INSTITUCION");
		sql.FROM(" ADM_USUARIOS USUARIOS");
		sql.INNER_JOIN(" CEN_INSTITUCION INST ON USUARIOS.IDINSTITUCION = INST.IDINSTITUCION");
		sql.INNER_JOIN(" ADM_LENGUAJES LENG ON USUARIOS.IDLENGUAJE = LENG.IDLENGUAJE");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = LENG.DESCRIPCION AND USUARIOS.IDLENGUAJE = REC.IDLENGUAJE");
		
		sql.WHERE("USUARIOS.NIF = '" + dni + "'");
		// El campo idinstitucion es obligatorio en el body. El filtro se aplica siempre
		sql.WHERE("USUARIOS.IDINSTITUCION = '" + institucion + "'");
		sql.WHERE("USUARIOS.ACTIVO = 'S' ");
		sql.ORDER_BY("USUARIOS.DESCRIPCION ASC");
		
		return sql.toString();
	}
	
	
	public String getAccessControls(ControlRequestItem request){
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("PROC.IDPROCESO AS ID");
		sql.SELECT("NVL(PROC.IDPARENT,'ARBOL') AS PARENT");
		sql.SELECT("PROC.DESCRIPCION AS TEXT");
		sql.SELECT("DECODE(MIN(DECODE(ACCESO.DERECHOACCESO,0,5,ACCESO.DERECHOACCESO)),5,0,MIN(DECODE(ACCESO.DERECHOACCESO,0,5,ACCESO.DERECHOACCESO))) AS DERECHOACCESO");

		sql.FROM("GEN_PROCESOS PROC");
		
		sql.INNER_JOIN("ADM_TIPOSACCESO ACCESO ON PROC.IDPROCESO = ACCESO.IDPROCESO ");
		sql.WHERE("IDINSTITUCION = ('" + request.getInstitucion() + "')");
		sql.WHERE("IDPERFIL IN (" + request.getIdGrupo() + ")");
		sql.WHERE("PROC.IDPROCESO = ('" + request.getIdProceso() + "')");
		sql.GROUP_BY("PROC.IDPROCESO,PROC.IDPARENT,PROC.DESCRIPCION");
		sql.ORDER_BY("PARENT DESC, TEXT ASC");
		return sql.toString();
	}
	
	public String getAccessControlsWithOutProcess(ControlRequestItem request){
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("PROC.IDPROCESO AS ID");
		sql.SELECT("NVL(PROC.IDPARENT,'ARBOL') AS PARENT");
		sql.SELECT("PROC.DESCRIPCION AS TEXT");
		sql.SELECT("DECODE(MIN(DECODE(ACCESO.DERECHOACCESO,0,5,ACCESO.DERECHOACCESO)),5,0,MIN(DECODE(ACCESO.DERECHOACCESO,0,5,ACCESO.DERECHOACCESO))) AS DERECHOACCESO");

		sql.FROM("GEN_PROCESOS PROC");
		
		sql.INNER_JOIN("ADM_TIPOSACCESO ACCESO ON PROC.IDPROCESO = ACCESO.IDPROCESO ");
		sql.WHERE("IDINSTITUCION = ('" + request.getInstitucion() + "')");
		sql.WHERE("IDPERFIL IN (" + request.getIdGrupo() + ")");
		sql.GROUP_BY("PROC.IDPROCESO,PROC.IDPARENT,PROC.DESCRIPCION");
		sql.ORDER_BY("PARENT DESC, TEXT ASC");
		return sql.toString();
	}
	
}
