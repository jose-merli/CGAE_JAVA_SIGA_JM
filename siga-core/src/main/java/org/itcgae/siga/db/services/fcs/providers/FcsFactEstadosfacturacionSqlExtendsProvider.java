package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsFactEstadosfacturacionSqlProvider;

public class FcsFactEstadosfacturacionSqlExtendsProvider extends FcsFactEstadosfacturacionSqlProvider {

    public String getIdEstadoFacturacion(Short idInstitucion, String idFacturacion) {

        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(IDORDENESTADO)");
        subQuery.FROM("FCS_FACT_ESTADOSFACTURACION");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDFACTURACION = " + idFacturacion);

        SQL sql = new SQL();
        sql.SELECT("IDESTADOFACTURACION");
        sql.FROM("FCS_FACT_ESTADOSFACTURACION");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDFACTURACION = " + idFacturacion);
        sql.WHERE("IDORDENESTADO = (" + subQuery.toString() + ")");

        return sql.toString();
    }

    public String getIdordenestadoMaximo(Short idInstitucion, String idFacturacion) {

        SQL sql = new SQL();
        sql.SELECT("MAX(IDORDENESTADO) + 1 AS IDORDENESTADO");
        sql.FROM("FCS_FACT_ESTADOSFACTURACION");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDFACTURACION = " + idFacturacion);

        return sql.toString();
    }

}
