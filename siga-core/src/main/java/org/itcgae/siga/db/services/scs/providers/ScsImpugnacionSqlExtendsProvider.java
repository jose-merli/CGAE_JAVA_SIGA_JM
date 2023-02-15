package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiporesolautoSqlProvider;

public class ScsImpugnacionSqlExtendsProvider extends ScsTiporesolautoSqlProvider {
	public String comboImpugnacion(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("impugnacion.IDTIPORESOLAUTO AS IDIMPUGNACION");
		sql.SELECT("impugnacion.DESCRIPCION");
//		sql.SELECT("catalogoImpugnacion.DESCRIPCION");

		sql.FROM("SCS_TIPORESOLAUTO impugnacion");
//		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoImpugnacion on catalogoImpugnacion.idrecurso = impugnacion.DESCRIPCION and catalogoImpugnacion.idlenguaje ="+idLenguaje);
		sql.WHERE("impugnacion.fecha_baja is  null");
		
		return sql.toString();
	}
	
	public String getDescImpugnacion(Short idImpugnacion) {
		SQL sql = new SQL();
		
		sql.SELECT("impugnacion.DESCRIPCION");

		sql.FROM("SCS_TIPORESOLAUTO impugnacion");
		sql.WHERE("impugnacion.IDTIPORESOLAUTO = " + idImpugnacion);
		
		return sql.toString();
	}

}
