package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsMinusvaliaSqlProvider;

public class ScsMinusvaliaSqlExtendsProvider extends ScsMinusvaliaSqlProvider {

	public String getMinusvalias(Short idInstitucion, String lenguaje) {

		SQL sql = new SQL();
		
		sql.SELECT("IDMINUSVALIA");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION,"+lenguaje+")");
		sql.FROM("scs_minusvalia");
		sql.WHERE("fecha_baja is null");
		sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		sql.ORDER_BY("descripcion");

		
		return sql.toString();
	}

}
