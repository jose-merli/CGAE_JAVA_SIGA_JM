package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgePermisoscalendarioSqlProvider;

public class AgePermisosCalendarioSqlExtendsProvider extends  AgePermisoscalendarioSqlProvider{
		
	public String getProfilesPermissions(String idCalendario, String idInstitucion){
		SQL sql = new SQL();
		
		sql.SELECT("adm.idperfil");
		sql.SELECT("adm.descripcion");
		sql.SELECT("NVL(per.tipoacceso,'0') as tipoacceso");
		sql.FROM("ADM_PERFIL adm");
		sql.LEFT_OUTER_JOIN("AGE_PERMISOSCALENDARIO per on (adm.idperfil = per.idperfil and"
				+ " adm.idinstitucion = per.idinstitucion AND per.idinstitucion = '"+ idInstitucion +"' AND per.idcalendario = '"+ idCalendario +"' )");
		sql.WHERE("adm.idinstitucion = '" + idInstitucion + "'");
		sql.ORDER_BY("adm.descripcion");
		
		return sql.toString();
	}	
	
}
