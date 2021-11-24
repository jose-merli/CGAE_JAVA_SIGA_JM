package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPagosporcajaSqlProvider;

import java.util.List;
import java.util.stream.Collectors;

public class FacPagosporcajaSqlExtendsProvider extends FacPagosporcajaSqlProvider {

    public String getNuevoID(String idInstitucion, String idFactura) {

        SQL sql = new SQL();
        sql.SELECT("NVL(MAX(IDPAGOPORCAJA), 0) + 1 AS IDPAGOPORCAJA");
        sql.FROM("FAC_PAGOSPORCAJA");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDFACTURA = '" + idFactura + "'");

        return sql.toString();
    }

    public String deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG IN (" + idPagos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("FAC_PAGOSPORCAJA");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO IN ( " + subQuery.toString() + " )");

        return query.toString();
    }

}
