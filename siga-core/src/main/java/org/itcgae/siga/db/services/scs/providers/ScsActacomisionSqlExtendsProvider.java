package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsActacomisionSqlProvider;

public class ScsActacomisionSqlExtendsProvider extends ScsActacomisionSqlProvider{
	public String getActaAnnio(String idInstitucion, String idActa, String anioActa) {
		SQL sql = new SQL();
		//La clave primaria de SCS_ACTACOMISION son la institucion, el año y su id.
		//Como en esta consulta solo se devuelven filas de la misma institucion que el EJG, 
		//se considera reduntante que el id tenga esa informacion.
		//sql.SELECT("idinstitucion||','||anioacta||','||idacta as IDACTANNIO");
		sql.SELECT("idacta||','||anioacta as IDACTANNIO");
		sql.SELECT("anioacta||'/'||numeroacta || ' - ' || to_char(fecharesolucion, 'dd/mm/yyyy') as DESCRIPCION");
		
		sql.FROM("scs_actacomision");
		
		sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		//Esta comprobacion se realiza para que la consulta solo seleccione actas abiertas de la institucion
		//además de la ultima acta al EJG que se selecciona siempre.
		if(!idActa.equals("null") && !idActa.equals("undefined")) {
			sql.WHERE("(fechaResolucion is null OR (anioacta = '"+anioActa+"' and idacta = '" +idActa+ "'))");
		}
		else {
			sql.WHERE("fechaResolucion is null");
		}
		
		return sql.toString();
	}
}
