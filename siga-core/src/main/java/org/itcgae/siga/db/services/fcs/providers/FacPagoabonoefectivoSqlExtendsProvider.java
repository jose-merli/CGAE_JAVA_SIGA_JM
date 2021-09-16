package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoSqlProvider;

public class FacPagoabonoefectivoSqlExtendsProvider extends FacPagoabonoefectivoSqlProvider {

    public String getNuevoID(String institucion, String abono) {

        SQL sql = new SQL();
        sql.SELECT("(NVL((MAX(IDPAGOABONO) + 1), 1 )) AS IDPAGOABONO");
        sql.FROM("FAC_PAGOABONOEFECTIVO");
        sql.WHERE("IDINSTITUCION = '" + institucion + "'");
        sql.WHERE("IDABONO = '" + abono + "'");

        return sql.toString();
    }

}
