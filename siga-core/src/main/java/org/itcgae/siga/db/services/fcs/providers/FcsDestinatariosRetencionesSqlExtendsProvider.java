package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsDestinatariosRetencionesSqlProvider;

public class FcsDestinatariosRetencionesSqlExtendsProvider extends FcsDestinatariosRetencionesSqlProvider {

    public String getComboDestinatarios(Short idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("IDDESTINATARIO AS ID");
        sql.SELECT("NOMBRE AS DESCRIPCION");
        sql.FROM("FCS_DESTINATARIOS_RETENCIONES");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.ORDER_BY("NOMBRE");

        return sql.toString();
    }

}
