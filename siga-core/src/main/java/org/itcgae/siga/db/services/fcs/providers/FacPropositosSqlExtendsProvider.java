package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPropositosSqlProvider;

public class FacPropositosSqlExtendsProvider extends FacPropositosSqlProvider {

    public String comboPropTranferencia(String idLenguaje, boolean isOtrasTrans) {

        SQL sql = new SQL();

        sql.SELECT("IDPROPOSITO");
        sql.SELECT("F_SIGA_GETRECURSO (NOMBRE, " + idLenguaje + ") AS NOMBRE");

        sql.FROM("FAC_PROPOSITOS");

        if (isOtrasTrans) {
            sql.WHERE("TIPOSEPA = 0");
        } else {
            sql.WHERE("TIPOSEPA = 1");
        }

        sql.ORDER_BY("CODIGO");
        sql.ORDER_BY("NOMBRE");

        return sql.toString();
    }

}
