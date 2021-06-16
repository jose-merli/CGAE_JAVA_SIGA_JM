package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioSqlProvider;

public class ScsTipoEJGColegioComisionSqlExtendsProvider extends ScsTipoejgcolegioSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsTipoEJGColegioComisionSqlExtendsProvider.class);

    
    public String comboColegioEjgComision(Short idLenguaje, String idInstitucion) {
        SQL sql = new SQL();
        LOGGER.info(
				"************************************************comboColegioEjgComision**************************************************");
        sql.SELECT("INT.NOMBRE");
        sql.SELECT("INT.IDINSTITUCION");
        sql.FROM("CEN_INSTITUCION INT");
        sql.WHERE("INT.IDCOMISION IN (SELECT INTCOM.IDCOMISION FROM CEN_INSTITUCION INTCOM WHERE INTCOM.IDINSTITUCION = IDINSTITUCION AND INTCOM.IDINSTITUCION = '"+idInstitucion+"') AND INT.IDTIPOINSTITUCION =3");
        
        
        LOGGER.info(
				sql.toString());

        return sql.toString();
    }

	
}
