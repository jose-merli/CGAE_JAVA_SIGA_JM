package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioSqlProvider;

public class ScsTipoejgcolegioSqlExtendsProvider extends ScsTipoejgcolegioSqlProvider {
	
	public String comboTipoColegioEjg(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("tipocolegioejg.IDTIPOEJGCOLEGIO");
		sql.SELECT("catalogoTipocolegioejg.DESCRIPCION");

		sql.FROM("SCS_TIPOEJGCOLEGIO tipocolegioejg");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoTipocolegioejg on catalogoTipocolegioejg.idrecurso = tipocolegioejg.DESCRIPCION and catalogoTipocolegioejg.idlenguaje ="+idLenguaje);
		sql.WHERE("tipocolegioejg.fecha_baja is null and tipocolegioejg.idinstitucion =" + idInstitucion);
		sql.ORDER_BY("descripcion");

		return sql.toString();
	}

}
