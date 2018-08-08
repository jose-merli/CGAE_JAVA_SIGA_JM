package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenEstadocivilSqlProvider;

public class CenEstadocivilSqlExtendsProvider extends CenEstadocivilSqlProvider{

	public String distinctCivilStatus(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("CIV.IDESTADOCIVIL");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_ESTADOCIVIL CIV");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CIV.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("CIV.FECHA_BAJA IS NULL");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
}
