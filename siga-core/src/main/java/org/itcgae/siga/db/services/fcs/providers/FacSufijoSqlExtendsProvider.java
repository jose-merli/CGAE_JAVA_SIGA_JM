package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacSufijoSqlProvider;

public class FacSufijoSqlExtendsProvider extends FacSufijoSqlProvider {

    public String comboSufijos(Short idInstitucion) {

        SQL sql = new SQL();

        sql.SELECT("IDSUFIJO");
        sql.SELECT("CONCEPTO");

        sql.FROM("FAC_SUFIJO");

        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        sql.ORDER_BY("IDINSTITUCION");
        sql.ORDER_BY("SUFIJO");

        return sql.toString();

    }
}
