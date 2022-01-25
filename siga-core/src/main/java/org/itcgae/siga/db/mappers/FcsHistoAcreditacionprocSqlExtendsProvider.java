package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

public class FcsHistoAcreditacionprocSqlExtendsProvider {

    public String deleteByAcreditacionProc(String idInstitucion, String idFacturacion) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_HISTO_ACREDITACIONPROC");

        SQL sql2 = new SQL();
        sql2.SELECT("IDPROCEDIMIENTO");
        sql2.FROM("SCS_ACREDITACIONPROCEDIMIENTO");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURACION = " + idFacturacion);

        SQL sql3 = new SQL();
        sql3.SELECT("IDACREDITACION");
        sql3.FROM("SCS_ACREDITACIONPROCEDIMIENTO");
        sql3.WHERE("IDINSTITUCION = " + idInstitucion);
        sql3.WHERE("IDFACTURACION = " + idFacturacion);

        sql.WHERE(" IDPROCEDIMIENTO IN (" + sql2.toString() + ") ");
        sql.WHERE(" IDACREDITACION IN (" + sql3.toString() + ") ");

        sql.WHERE("IDFACTURACION = " + idFacturacion );
        sql.WHERE("IDINSTITUCION = " + idInstitucion );
        return sql.toString();
    }
}
