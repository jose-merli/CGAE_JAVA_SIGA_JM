package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsActacomisionSqlProvider;

public class ScsActacomisionSqlExtendsProvider extends ScsActacomisionSqlProvider{
	public String getActaAnnio(String idInstitucion, String idActa) {
		SQL sql = new SQL();
		sql.SELECT("idinstitucion||','||anioacta||','||idacta as IDACTANNIO," + 
				   " anioacta||'/'||numeroacta || ' - ' || to_char(fecharesolucion, 'dd/mm/yyyy') as DESCRIPCION");
		
		sql.FROM("scs_actacomision");
		
		sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		//Esta comprobacion se realiza para que la consulta solo seleccione actas abiertas de la institucion
		//excepto si el acta es la ultima asociada al EJG que se selecciona siempre.
		if(idActa!=null)sql.WHERE("(fechaResolucion is null OR idinstitucion||','||anioacta||','||idacta like '" +idActa+ "')");
		else sql.WHERE("fechaResolucion is null");
		
		return sql.toString();
	}
}
