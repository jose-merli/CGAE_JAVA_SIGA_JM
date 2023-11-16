package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioSqlProvider;

public class ScsTipoEJGColegioSqlExtendsProvider extends ScsTipoejgcolegioSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsTipoEJGColegioSqlExtendsProvider.class);

	public String comboTipoEjgColegio(Short idLenguaje, String idInstitucion) {
 
		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipoejg.IDTIPOEJGCOLEGIO");
		sql.SELECT("f_siga_getrecurso(tipoejg.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOEJGCOLEGIO tipoejg");
		sql.WHERE("tipoejg.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("tipoejg.fecha_baja is null");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}

    
    

	
}
