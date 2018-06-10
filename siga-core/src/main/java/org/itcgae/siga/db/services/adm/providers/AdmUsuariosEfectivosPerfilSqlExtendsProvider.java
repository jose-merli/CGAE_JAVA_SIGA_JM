package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilSqlProvider;

public class AdmUsuariosEfectivosPerfilSqlExtendsProvider extends AdmUsuariosEfectivosPerfilSqlProvider{

	
	public String getPerfilesUsuario(UsuarioUpdateDTO usuarioUpdateDTO, String idInstitucion) {
		
		SQL sql = new SQL();
		
		
		sql.SELECT_DISTINCT("PER.IDPERFIL as PERFIL1");
		sql.SELECT_DISTINCT("PER.IDPERFIL as PERFIL2");
		sql.FROM("adm_usuarios_efectivos_perfil USUEFEC");
		sql.INNER_JOIN("adm_perfil PER on USUEFEC.IDPERFIL = PER.IDPERFIL");
		sql.WHERE("PER.fecha_baja is null");
		sql.WHERE("USUEFEC.idusuario = '"+usuarioUpdateDTO.getIdUsuario()+"'");
		sql.WHERE("PER.idinstitucion = '"+idInstitucion+"'");
		sql.WHERE(" USUEFEC.idrol = '"+usuarioUpdateDTO.getRol()+"'");
		sql.WHERE("USUEFEC.fecha_baja is null");
		
		return sql.toString();
	}
}
