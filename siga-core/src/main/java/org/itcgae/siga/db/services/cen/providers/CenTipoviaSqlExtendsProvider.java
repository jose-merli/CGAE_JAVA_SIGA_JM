package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTipoviaSqlProvider;

public class CenTipoviaSqlExtendsProvider extends CenTipoviaSqlProvider {

	
	public String getTipoVias(Short idInstitucion, String idLenguaje, String idTipoViaJusticiable) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TIPOVIA.IDTIPOVIA");
		sql.SELECT_DISTINCT("f_siga_getrecurso(TIPOVIA.DESCRIPCION, " + idLenguaje + ") AS DESCRIPCION");
		sql.FROM("CEN_TIPOVIA TIPOVIA");
		sql.WHERE("TIPOVIA.IDINSTITUCION = '" + idInstitucion + "'");
		if(idTipoViaJusticiable != null && !idTipoViaJusticiable.isEmpty() && !"undefined".equals(idTipoViaJusticiable)) {
			sql.WHERE("TIPOVIA.FECHA_BAJA IS NULL OR TIPOVIA.IDTIPOVIA = " + idTipoViaJusticiable);
		} else {
			sql.WHERE("TIPOVIA.FECHA_BAJA IS NULL");
		}
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	
	}
}
