package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class FcsHistoricoProcedimientosSqlExtendsProvider {

    public String deleteByProcedimiento(String idInstitucion, String idFacturacion) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_HISTORICO_PROCEDIMIENTOS");

        SQL sql2 = new SQL();
        sql2.SELECT("IDPROCEDIMIENTO");
        sql2.FROM("SCS_PROCEDIMIENTOS");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDPROCEDIMIENTO IN (" + sql2.toString() + ") ");

        sql.WHERE("IDFACTURACION = " + idFacturacion );
        sql.WHERE("IDINSTITUCION = " + idInstitucion );
        return sql.toString();
    }
}
