package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsEstadosfacturacionSqlProvider;

public class FcsHitoGeneralSqlExtendsProvider extends FcsEstadosfacturacionSqlProvider{

	public String factConceptos(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("IDHITOGENERAL");
		sql.SELECT("F_SIGA_GETRECURSO (descripcion,"+idLenguaje+") AS DESCRIPCION");
		sql.FROM("FCS_HITOGENERAL");
		
		return sql.toString();
	}
}

