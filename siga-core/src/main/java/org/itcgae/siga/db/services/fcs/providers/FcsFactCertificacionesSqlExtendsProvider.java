package org.itcgae.siga.db.services.fcs.providers;


import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsFactCertificacionesSqlProvider;

public class FcsFactCertificacionesSqlExtendsProvider extends FcsFactCertificacionesSqlProvider {
    public String getFechaMaxMinFact(Short idInstitucion, String idFactList) {

        SQL sql = new SQL();
        sql.SELECT("MAX(FECHAHASTA) AS FECHAHASTA");
        sql.SELECT("MIN(FECHADESDE) AS FECHADESDE");
        sql.FROM("FCS_FACTURACIONJG");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDFACTURACION IN (" + idFactList + ")");

        return sql.toString();
    }

}
