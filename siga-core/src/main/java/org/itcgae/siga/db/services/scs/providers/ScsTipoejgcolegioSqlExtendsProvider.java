package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoejgSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioSqlProvider;

public class ScsTipoEJGColegioSqlExtendsProvider extends ScsTipoejgcolegioSqlProvider {

	public String comboTipoEjgColegio(Short idLenguaje) {
 
		SQL sql = new SQL();

		sql.SELECT("tipoejg.IDTIPOEJGCOLEGIO");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOEJGCOLEGIO tipoejg");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipoejg.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipoejg.fecha_baja is null");
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
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
