package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacDisquetecargosSqlProvider;

public class FacDisquetecargosSqlExtendsProvider extends FacDisquetecargosSqlProvider {

    public String getRenegociacionFactura(String institucion, String factura) {

        SQL sql = new SQL();
        sql.SELECT("FAC_FACTURAINCLUIDAENDISQUETE.IDRENEGOCIACION");
        sql.FROM("FAC_DISQUETECARGOS");
        sql.JOIN("FAC_FACTURAINCLUIDAENDISQUETE ON FAC_FACTURAINCLUIDAENDISQUETE.IDINSTITUCION = FAC_DISQUETECARGOS.IDINSTITUCION" +
                " AND FAC_FACTURAINCLUIDAENDISQUETE.IDDISQUETECARGOS = FAC_DISQUETECARGOS.IDDISQUETECARGOS");
        sql.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDINSTITUCION  = " + institucion);
        sql.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDFACTURA = " + factura);
        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(DISQ.FECHACREACION)");
        subQuery.FROM("FAC_DISQUETECARGOS DISQ");
        subQuery.WHERE("FID.IDINSTITUCION = DISQ.IDINSTITUCION");
        subQuery.WHERE("FID.IDDISQUETECARGOS = DISQ.IDDISQUETECARGOS");
        subQuery.WHERE("FID.IDINSTITUCION = " + institucion);
        subQuery.WHERE("FID.IDFACTURA = " + factura);
        sql.WHERE("FAC_DISQUETECARGOS.FECHACREACION IN (" + subQuery.toString() + ")");

        return sql.toString();
    }
}
