package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacSeriefacturacionBancoSqlProvider;

public class FacSeriefacturacionBancoExtendsSqlProvider extends FacSeriefacturacionBancoSqlProvider {

	public String getBancosSufijos(Short idInstitucion) {
		SQL sql = new SQL();

		// Consulta de los select
		sql.SELECT_DISTINCT("sfb.bancos_codigo, sfb.idsufijo");

		sql.FROM("fac_seriefacturacion_banco sfb");
		sql.INNER_JOIN("fac_seriefacturacion sf ON (sf.idinstitucion = sfb.idinstitucion AND sf.idseriefacturacion = sfb.idseriefacturacion)");
		sql.WHERE("sfb.idinstitucion = " + idInstitucion);
		sql.WHERE("sf.fechabaja is null");

		return sql.toString();

	}

}
