package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsEstadosfacturacionSqlProvider;

public class FcsEstadoFacturacionSqlExtendsProvider extends FcsEstadosfacturacionSqlProvider{

	public String estadosFacturacion(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("estados.IDESTADOFACTURACION");
		sql.SELECT("catalogos.DESCRIPCION");
		sql.FROM(" FCS_ESTADOSFACTURACION estados");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos ON catalogos.IDRECURSO=estados.DESCRIPCION");
		sql.WHERE("catalogos.IDLENGUAJE="+idLenguaje);
		
		return sql.toString();
	}
	
	public String estadosPagos(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("estados.IDESTADOPAGOSJG");
		sql.SELECT("catalogos.DESCRIPCION");
		sql.FROM("FCS_ESTADOSPAGOS estados");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos ON catalogos.IDRECURSO=estados.DESCRIPCION");
		sql.WHERE("catalogos.IDLENGUAJE="+idLenguaje);
		
		return sql.toString();
	}
}