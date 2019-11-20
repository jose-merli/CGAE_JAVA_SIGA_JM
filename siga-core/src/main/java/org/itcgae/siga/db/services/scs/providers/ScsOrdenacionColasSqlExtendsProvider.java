package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsOrdenacionColasSqlExtendsProvider extends ScsOrdenacioncolasSqlProvider {

	public String ordenColas(String idordenacioncolas) {

		SQL sql = new SQL();

		sql.SELECT("ABS(valor)as numero, POR_FILAS,  DECODE(SIGN(valor), '-1', 'desc', DECODE(SIGN(valor),0,'','asc')) AS orden FROM SCS_ORDENACIONCOLAS unpivot (valor for POR_FILAS in (alfabeticoapellidos, fechanacimiento, numerocolegiado, ANTIGUEDADCOLA))" );
		sql.WHERE("idordenacioncolas = '"+idordenacioncolas +"'");
		sql.ORDER_BY("numero desc");
		return sql.toString();
	}
	
	public String ordenColasFinal(TurnosItem turnosItem) {

		SQL sql = new SQL();

		sql.SELECT("ABS(valor)as numero, POR_FILAS,  DECODE(SIGN(valor), '-1', 'desc', DECODE(SIGN(valor),0,'','asc')) AS orden FROM SCS_ORDENACIONCOLAS unpivot (valor for POR_FILAS in (alfabeticoapellidos, fechanacimiento, numerocolegiado, ANTIGUEDADCOLA))" );
		sql.WHERE("idordenacioncolas = '"+turnosItem.getIdordenacioncolas() +"'");
		sql.ORDER_BY("numero desc");
		return sql.toString();
	}
	
}
