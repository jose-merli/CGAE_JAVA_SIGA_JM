package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

public class FcsHistoricoTipoactuacionSqlExtendsProvider {

    public String deleteByAcreditacionProc(String idInstitucion, String idFacturacion) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_HISTORICO_TIPOACTUACION");

        SQL sql2 = new SQL();
        sql2.SELECT("IDTIPOACTUACION");
        sql2.FROM("SCS_TIPOACTUACION");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTIPOACTUACION IN (" + sql2.toString() + ") ");

        SQL sql3 = new SQL();
        sql3.SELECT("IDTIPOASISTENCIA");
        sql3.FROM("SCS_TIPOACTUACION");
        sql3.WHERE("IDINSTITUCION = " + idInstitucion);
        sql3.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTIPOASISTENCIA IN (" + sql3.toString() + ") ");

        sql.WHERE("IDFACTURACION = " + idFacturacion );
        sql.WHERE("IDINSTITUCION = " + idInstitucion );
        return sql.toString();
    }
}
