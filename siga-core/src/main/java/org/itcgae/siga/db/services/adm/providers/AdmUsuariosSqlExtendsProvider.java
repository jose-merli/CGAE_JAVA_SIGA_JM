package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;

public class AdmUsuariosSqlExtendsProvider {

	

	
	/***
	 * Build the SQL query to get users information depending on a filter which in this case is the UsuarioRequestDTO object. 
	 * @param numPagina The number of the page that will be displayed in web page. Each page contains a portion of users with their information.
	 * @param usuarioRequestDTO The filter that some web user uses on web page to obtain users information.
	 * @return The SQL query for data base.
	 */
	public String getUsersByFilter(int numPagina, UsuarioRequestDTO usuarioRequestDTO)
	{
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
	public String updateUsersAdmUserTable(UsuarioUpdateDTO usuarioUpdateDTO)
	{
		SQL sql = new SQL();
		sql.UPDATE("ADM_USUARIOS");
		
		// comprobacion para actualizar campo ACTIVO
		if(!usuarioUpdateDTO.getActivo().equalsIgnoreCase(""))
		{
			sql.SET("ACTIVO = '" + usuarioUpdateDTO.getActivo() + "'");
		}
		// comprobacion para actualizar campo CODIGOEXT
		if(!usuarioUpdateDTO.getCodigoExterno().equalsIgnoreCase(""))
		{
			sql.SET("CODIGOEXT = '" + usuarioUpdateDTO.getCodigoExterno() + "'");
		}
		
		// campos obligatorios. filtro para actualizar solo determinados campos
		sql.WHERE("IDUSUARIO = '" + usuarioUpdateDTO.getIdUsuario() + "'");
		sql.WHERE("IDINSTITUCION = '" + usuarioUpdateDTO.getIdInstitucion() + "'");
		
		return sql.toString();
	}
	
	public String updateUsersAdmPerfilTable(UsuarioUpdateDTO usuarioUpdateDTO)
	{
		SQL sql = new SQL();
		sql.UPDATE("ADM_PERFIL");
		
		// comprobacion para actualizar campo DESCRIPCION
		if(!usuarioUpdateDTO.getGrupo().equalsIgnoreCase(""))
		{
			sql.SET("DESCRIPCION = '" + usuarioUpdateDTO.getGrupo() + "'");
		}
		
		// campos obligatorios. filtro para actualizar solo determinados campos
		sql.WHERE("IDPERFIL = '" + usuarioUpdateDTO.getIdGrupo() + "'");
		
		return sql.toString();
	}
	
}
