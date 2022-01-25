package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

public class FcsHistoTipoactcostefijoSqlExtendsProvider {

    public String delete(String idInstitucion, String idFacturacion) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_HISTO_TIPOACTCOSTEFIJO");

        SQL sql2 = new SQL();
        sql2.SELECT("IDTIPOASISTENCIA");
        sql2.FROM("SCS_TIPOACTUACIONCOSTEFIJO");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTIPOASISTENCIA IN (" + sql2.toString() + ") ");

        SQL sql3 = new SQL();
        sql3.SELECT("IDCOSTEFIJO");
        sql3.FROM("SCS_TIPOACTUACIONCOSTEFIJO");
        sql3.WHERE("IDINSTITUCION = " + idInstitucion);
        sql3.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTIPOACTUACION IN (" + sql3.toString() + ") ");

        SQL sql4 = new SQL();
        sql4.SELECT("IDTIPOACTUACION");
        sql4.FROM("SCS_TIPOACTUACIONCOSTEFIJO");
        sql4.WHERE("IDINSTITUCION = " + idInstitucion);
        sql4.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTIPOACTUACION IN (" + sql4.toString() + ") ");

        sql.WHERE("IDFACTURACION = " + idFacturacion );
        sql.WHERE("IDINSTITUCION = " + idInstitucion );
        return sql.toString();
    }
}
