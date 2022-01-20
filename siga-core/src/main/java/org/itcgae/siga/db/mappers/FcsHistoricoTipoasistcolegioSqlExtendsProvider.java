package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

public class FcsHistoricoTipoasistcolegioSqlExtendsProvider {

    public String delete(String idInstitucion, String idFacturacion) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_HISTORICO_TIPOASISTCOLEGIO");

        SQL sql2 = new SQL();
        sql2.SELECT("IDTIPOASISTENCIACOLEGIO");
        sql2.FROM("SCS_TIPOASISTENCIACOLEGIO");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDTIPOASISTENCIACOLEGIO IN (" + sql2.toString() + ") ");

        sql.WHERE("IDFACTURACION = " + idFacturacion );
        sql.WHERE("IDINSTITUCION = " + idInstitucion );
        return sql.toString();
    }

}
