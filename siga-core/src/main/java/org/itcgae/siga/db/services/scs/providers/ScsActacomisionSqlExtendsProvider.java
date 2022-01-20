package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsActacomisionSqlProvider;

public class ScsActacomisionSqlExtendsProvider extends ScsActacomisionSqlProvider{
	public String getActaAnnio(String idInstitucion, String anioacta, String idacta) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		//La clave primaria de SCS_ACTACOMISION son la institucion, el año y su id.
		//Como en esta consulta solo se devuelven filas de la misma institucion que el EJG, 
		//se considera reduntante que el id tenga esa informacion.
		//sql.SELECT("idinstitucion||','||anioacta||','||idacta as IDACTANNIO");
		
		sql.SELECT("idacta||','||anioacta as IDACTANNIO");
		sql.SELECT("anioacta||'/'||numeroacta || ' - ' || to_char(fecharesolucion, 'dd/mm/yyyy') as DESCRIPCION");
		
		sql.FROM("scs_actacomision");
		
		sql.WHERE("idinstitucion = '" + idInstitucion + "'");

		if(!UtilidadesString.esCadenaVacia(idacta) && !UtilidadesString.esCadenaVacia(anioacta)) {
			sql.WHERE("((fechaResolucion is null) OR (idacta = '" +idacta + "' AND anioacta = '" + anioacta + "'))");
		}else {
			sql.WHERE("fechaResolucion is null");
		}
		
		
		return sql.toString();
	}
}
