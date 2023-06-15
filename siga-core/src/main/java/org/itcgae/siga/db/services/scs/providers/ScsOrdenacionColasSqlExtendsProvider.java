package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasSqlProvider;

public class ScsOrdenacionColasSqlExtendsProvider extends ScsOrdenacioncolasSqlProvider {

	public String ordenColas(String idordenacioncolas) {

		SQL sql = new SQL();

		sql.SELECT("ABS(valor)as numero, POR_FILAS,  DECODE(SIGN(valor), '-1', 'desc', DECODE(SIGN(valor),0,'','asc')) AS orden FROM SCS_ORDENACIONCOLAS unpivot (valor for POR_FILAS in (alfabeticoapellidos, fechanacimiento, numerocolegiado, ANTIGUEDADCOLA))" );
		sql.WHERE("idordenacioncolas = '"+idordenacioncolas +"'");
		sql.ORDER_BY("numero desc");
		return sql.toString();
	}
	
	public String ordenColasFinal(String idordenacioncolas) {

		SQL sql = new SQL();

		sql.SELECT("ABS(valor)as numero, POR_FILAS,  DECODE(SIGN(valor), '-1', 'desc', DECODE(SIGN(valor),0,'','asc')) AS orden FROM SCS_ORDENACIONCOLAS unpivot (valor for POR_FILAS in (alfabeticoapellidos, fechanacimiento, numerocolegiado, ANTIGUEDADCOLA,ordenacionmanual))" );
		sql.WHERE("idordenacioncolas = '"+idordenacioncolas +"'");
		sql.ORDER_BY("numero desc");
		return sql.toString();
	}
	
	public String getIdOrdenacion() {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDORDENACIONCOLAS) AS IDORDENACIONCOLAS");
		sql.FROM("SCS_ORDENACIONCOLAS");

		return sql.toString();
	}
	
}
