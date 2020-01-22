package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsActacomisionSqlProvider;

public class ScsActacomisionSqlExtendsProvider extends ScsActacomisionSqlProvider{
	public String getActaAnnio(String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("idinstitucion||','||anioacta||','||idacta as IDACTANNIO," + 
				   " anioacta||'/'||numeroacta || ' - ' || to_char(fecharesolucion, 'dd/mm/yyyy') as DESCRIPCION");
		
		sql.FROM("scs_actacomision");
		
		if(idInstitucion != null && idInstitucion != "")
			sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		sql.ORDER_BY("anioacta desc, to_number(regexp_replace(numeroacta, '\\D', '')) desc, numeroacta desc");
		return sql.toString();
	}
}
