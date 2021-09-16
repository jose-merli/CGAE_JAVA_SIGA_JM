package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacAbonoSqlProvider;

public class FacAbonoSqlExtendsProvider extends FacAbonoSqlProvider {

    public String getNuevoID(String idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(IDABONO) + 1, 1)) AS IDABONO");
        sql.FROM("FAC_ABONO");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }
}
