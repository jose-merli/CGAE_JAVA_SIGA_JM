package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTiposcvSqlProvider;

public class CenTiposcvSqlExtendsProvider extends CenTiposcvSqlProvider{

	public String selectCategoriaCV(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT("CV.IDTIPOCV");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_TIPOSCV CV");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CV.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("CV.FECHA_BAJA IS NULL");
		sql.WHERE("CV.BLOQUEADO = 'N'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
}
