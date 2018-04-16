package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.providers.AdmUsuariosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AdmUsuariosExtendsMapper extends AdmUsuariosMapper{

	/***
	 * Map column of data base to java object (UsuarioItem). This is possible due to this function is associate with the result of a SQL query(AdmUsuariosSqlExtendsProvider.class).
	 * @param numPagina The number of the page that will be displayed in web page. Each page contains a portion of users with their information.
	 * @param usuarioRequestDTO The filter that web user uses on web page to obtain users information.
	 * @return List of objects mapped from data base to java object.
	 */
	@SelectProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "getUsersByFilter")
	@Results({
		@Result(column = "DESCRIPCION", property = "nombreApellidos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.DATE),
		@Result(column = "ACTIVO", property = "activo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ROLES", property = "roles", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExterno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PERFIL", property = "perfil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDUSUARIO", property = "idUsuario", jdbcType = JdbcType.VARCHAR)
	})
	List<UsuarioItem> getUsersByFilter(int numPagina, UsuarioRequestDTO usuarioRequestDTO);
	
	
	@UpdateProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "updateUsersAdmUserTable")
	int updateUsersAdmUserTable(UsuarioUpdateDTO usuarioUpdateDTO);
	
	
	@UpdateProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "updateUsersAdmPerfilTable")
	int updateUsersAdmPerfilTable(UsuarioUpdateDTO usuarioUpdateDTO);
	
	
	@InsertProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "createUserAdmUsuariosTable")
	int createUserAdmUsuariosTable(UsuarioCreateDTO usuarioCreateDTO, Integer usuarioModificacion);
	
	
	@InsertProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "createUserAdmUsuarioEfectivoTable")
	int createUserAdmUsuarioEfectivoTable(UsuarioCreateDTO usuarioCreateDTO, Integer usuarioModificacion);
	
	
	@InsertProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "createUserAdmUsuariosEfectivoPerfilTable")
	int createUserAdmUsuariosEfectivoPerfilTable(UsuarioCreateDTO usuarioCreateDTO, Integer usuarioModificacion);
	
	
	@UpdateProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "enableUser")
	int enableUser(UsuarioDeleteDTO usuarioDeleteDTO);
	
	
	@UpdateProvider(type = AdmUsuariosSqlExtendsProvider.class, method = "disableUser")
	int disableUser(UsuarioDeleteDTO usuarioDeleteDTO);
}
