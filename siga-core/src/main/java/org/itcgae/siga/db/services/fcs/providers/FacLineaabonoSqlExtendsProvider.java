package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacLineaabonoSqlProvider;

public class FacLineaabonoSqlExtendsProvider extends FacLineaabonoSqlProvider {

    public String getNuevoID(String idInstitucion, String idAbono) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(NUMEROLINEA) + 1 , 1)) AS NUMEROLINEA");
        sql.FROM("FAC_LINEAABONO");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDABONO = '" + idAbono + "'");

        return sql.toString();
    }
}
