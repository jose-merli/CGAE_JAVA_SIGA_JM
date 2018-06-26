package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenCargoSqlProvider;

public class CenCargoSqlExtendsProvider extends CenCargoSqlProvider{

	public String getCargos(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("CAR.IDCARGO");
		sql.SELECT_DISTINCT("REC.DESCRIPCION");
		sql.FROM("CEN_CARGO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON CAR.DESCRIPCION = REC.IDRECURSO");
		sql.WHERE("CAR.FECHA_BAJA IS NULL");
		sql.WHERE("REC.IDLENGUAJE = '");
		sql.WHERE("REC.IDINSTITUCION = '");
		sql.ORDER_BY("REC.DESCRIPCION");
		
		return sql.toString();
	}
}
