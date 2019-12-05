package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTipoviaSqlProvider;

public class CenTipoviaSqlExtendsProvider extends CenTipoviaSqlProvider {

	
	public String getTipoVias(Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TIPOVIA.IDTIPOVIA");
		sql.SELECT_DISTINCT("GEN.DESCRIPCION");
		sql.FROM("CEN_TIPOVIA TIPOVIA");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GEN on GEN.IDRECURSO = TIPOVIA.DESCRIPCION");
		
		sql.WHERE("GEN.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("TIPOVIA.IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("GEN.DESCRIPCION");
		
		return sql.toString();
	}
}
