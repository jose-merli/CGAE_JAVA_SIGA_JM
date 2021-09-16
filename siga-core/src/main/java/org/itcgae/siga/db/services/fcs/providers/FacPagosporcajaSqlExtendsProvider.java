package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPagosporcajaSqlProvider;

public class FacPagosporcajaSqlExtendsProvider extends FacPagosporcajaSqlProvider {

    public String getNuevoID(String idInstitucion, String idFactura) {

        SQL sql = new SQL();
        sql.SELECT("NVL(MAX(IDPAGOPORCAJA), 0) + 1 AS IDPAGOPORCAJA");
        sql.FROM("FAC_PAGOSPORCAJA");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDFACTURA = '" + idFactura + "'");

        return sql.toString();
    }

}
