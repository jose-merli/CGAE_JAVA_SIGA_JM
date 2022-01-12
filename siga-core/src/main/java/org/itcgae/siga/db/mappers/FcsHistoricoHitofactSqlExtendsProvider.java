package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

public class FcsHistoricoHitofactSqlExtendsProvider {

    public String delete(String idInstitucion, String idFacturacion) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_HISTORICO_HITOFACT");

        SQL sql2 = new SQL();
        sql2.SELECT("IDTURNO");
        sql2.FROM("SCS_HITOFACTURABLEGUARDIA");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTURNO IN (" + sql2.toString() + ") ");

        SQL sql3 = new SQL();
        sql3.SELECT("IDGUARDIA");
        sql3.FROM("SCS_HITOFACTURABLEGUARDIA");
        sql3.WHERE("IDINSTITUCION = " + idInstitucion);
        sql3.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDGUARDIA IN (" + sql3.toString() + ") ");

        return sql.toString();
    }
}
