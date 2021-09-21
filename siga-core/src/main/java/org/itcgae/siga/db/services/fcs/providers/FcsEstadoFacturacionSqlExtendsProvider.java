package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsEstadosfacturacionSqlProvider;

public class FcsEstadoFacturacionSqlExtendsProvider extends FcsEstadosfacturacionSqlProvider{

	public String estadosFacturacion(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("IDESTADOFACTURACION");
		sql.SELECT("F_SIGA_GETRECURSO (descripcion,"+idLenguaje+") AS DESCRIPCION");
		sql.FROM("FCS_ESTADOSFACTURACION");
		
		return sql.toString();
	}
}

