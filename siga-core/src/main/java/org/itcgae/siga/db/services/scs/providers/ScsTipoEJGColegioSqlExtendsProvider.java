package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioSqlProvider;

public class ScsTipoEJGColegioSqlExtendsProvider extends ScsTipoejgcolegioSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsTipoEJGColegioSqlExtendsProvider.class);

	public String comboTipoEjgColegio(Short idLenguaje, String idInstitucion) {
 
		SQL sql = new SQL();

		sql.SELECT("tipoejg.IDTIPOEJGCOLEGIO");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOEJGCOLEGIO tipoejg");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipoejg.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipoejg.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("tipoejg.fecha_baja is null");
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}

    
    

	
}
