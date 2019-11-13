package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipodictamenejgSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoejgSqlProvider;

public class ScsTipoejgSqlExtendsProvider extends ScsTipoejgSqlProvider{

	public String comboTipoejg(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("tipoejg.IDTIPOEJG");
		sql.SELECT("catalogoTipoejg.DESCRIPCION");

		sql.FROM("SCS_TIPOEJG tipoejg");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoTipoejg on catalogoTipoejg.idrecurso = tipoejg.DESCRIPCION and catalogoTipoejg.idlenguaje ="+idLenguaje);
		sql.WHERE("tipoejg.fecha_baja is  null");
		
		return sql.toString();
	}
}

