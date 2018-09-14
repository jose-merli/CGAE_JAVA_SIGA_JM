package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTratamientoSqlProvider;

public class CenTratamientoSqlExtendsProvider extends CenTratamientoSqlProvider{

	
	public String selectTratamiento(String idLenguage) {
		
		SQL sql = new SQL();
		
		sql.SELECT("T.IDTRATAMIENTO AS VALUE");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION," + idLenguage + ") AS LABEL");
		sql.FROM("CEN_TRATAMIENTO T");
		sql.ORDER_BY("LABEL");
		return sql.toString();
	}
	
}
