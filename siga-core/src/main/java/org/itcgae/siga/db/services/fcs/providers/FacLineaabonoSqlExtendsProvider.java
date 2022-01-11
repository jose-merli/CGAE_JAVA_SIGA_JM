package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacLineaabonoSqlProvider;

import java.util.List;
import java.util.stream.Collectors;

public class FacLineaabonoSqlExtendsProvider extends FacLineaabonoSqlProvider {

    public String getNuevoID(String idInstitucion, String idAbono) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(NUMEROLINEA) + 1 , 1)) AS NUMEROLINEA");
        sql.FROM("FAC_LINEAABONO");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDABONO = '" + idAbono + "'");

        return sql.toString();
    }

    public String deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG IN (" + idPagos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("FAC_LINEAABONO");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO IN ( " + subQuery.toString() + " )");

        return query.toString();
    }
}
