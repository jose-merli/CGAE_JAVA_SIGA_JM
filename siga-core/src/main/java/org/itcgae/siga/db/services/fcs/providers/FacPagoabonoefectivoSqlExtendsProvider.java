package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoSqlProvider;

import java.util.List;
import java.util.stream.Collectors;

public class FacPagoabonoefectivoSqlExtendsProvider extends FacPagoabonoefectivoSqlProvider {

    public String getNuevoID(String institucion, String abono) {

        SQL sql = new SQL();
        sql.SELECT("(NVL((MAX(IDPAGOABONO) + 1), 1 )) AS IDPAGOABONO");
        sql.FROM("FAC_PAGOABONOEFECTIVO");
        sql.WHERE("IDINSTITUCION = '" + institucion + "'");
        sql.WHERE("IDABONO = '" + abono + "'");

        return sql.toString();
    }

    public String deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG IN (" + idPagos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("FAC_PAGOABONOEFECTIVO");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO IN ( " + subQuery.toString() + " )");

        return query.toString();
    }

}
