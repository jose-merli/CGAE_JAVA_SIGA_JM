package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsSubzonapartidoSqlProvider;

public class ScsSubzonapartidoSqlExtendsProvider extends ScsSubzonapartidoSqlProvider{

	public String getPartidoJudicialTurno(String idZona, String idSubzona, String idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("CEN_PARTIDOJUDICIAL.NOMBRE");
	
		sql.FROM("SCS_SUBZONAPARTIDO");
		sql.JOIN("CEN_PARTIDOJUDICIAL  ON SCS_SUBZONAPARTIDO.IDPARTIDO = CEN_PARTIDOJUDICIAL.IDPARTIDO");

		sql.WHERE("SCS_SUBZONAPARTIDO.IDINSTITUCION = "+idInstitucion);
		sql.WHERE("SCS_SUBZONAPARTIDO.IDSUBZONA = "+idSubzona);
		sql.WHERE("SCS_SUBZONAPARTIDO.IDZONA = "+idZona);
	
		return sql.toString();
	}
	
	
}
