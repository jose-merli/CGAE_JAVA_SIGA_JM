package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AdmConsultainformeSqlProvider;

public class AdmConsultainformeSqlExtendsProvider extends AdmConsultainformeSqlProvider {

    public String getConsultasInforme(String idInstitucion, String idPlantilla) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("ADM_CONSULTAINFORME INF, CON_CONSULTA CON");
        sql.WHERE("INF.IDINSTITUCION_CONSULTA = CON.IDINSTITUCION");
        sql.WHERE("INF.IDCONSULTA = CON.IDCONSULTA");
        sql.WHERE("INF.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("INF.IDPLANTILLA = " + idPlantilla);

        return sql.toString();
    }

    public String selectCamposOrdenados(String select) {
        return select;
    }

    public String getFechaCompletaBD() {
        SQL sql = new SQL();
        sql.SELECT("TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') AS FECHA");
        sql.FROM("DUAL");

        return sql.toString();
    }

}
