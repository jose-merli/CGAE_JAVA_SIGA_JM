package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTiposseguroSqlProvider;

public class CenTiposseguroSqlExtendsProvider extends CenTiposseguroSqlProvider{

	public String getTypeInsurances(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT("T.IDTIPOSSEGURO AS LABEL");
		sql.SELECT("CAT.DESCRIPCION AS VALUE");
		sql.FROM("CEN_TIPOSSEGURO T");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (T.NOMBRE = CAT.IDRECURSO)");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
}
