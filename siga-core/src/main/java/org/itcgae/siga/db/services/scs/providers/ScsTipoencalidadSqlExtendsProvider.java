package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoencalidadSqlProvider;

public class ScsTipoencalidadSqlExtendsProvider extends ScsTipoencalidadSqlProvider {
	
	public String comboTipoencalidad(String idLenguaje, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("CALIDAD.IDTIPOENCALIDAD");
		sql.SELECT("catalogoCALIDAD.DESCRIPCION");

		sql.FROM("scs_TIPOENCALIDAD CALIDAD");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoCALIDAD on catalogoCALIDAD.idrecurso = CALIDAD.DESCRIPCION and catalogoCALIDAD.idlenguaje ="+idLenguaje);
		sql.WHERE("CALIDAD.fecha_baja is null");
		sql.WHERE("CALIDAD.idInstitucion = '"+idInstitucion+"'");
		sql.ORDER_BY("descripcion");
		
		return sql.toString();
	}

}
