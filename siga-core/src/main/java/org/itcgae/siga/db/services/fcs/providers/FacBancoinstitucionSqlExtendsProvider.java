package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacBancoinstitucionSqlProvider;

public class FacBancoinstitucionSqlExtendsProvider extends FacBancoinstitucionSqlProvider {

    public String comboPropTranferencia(Short idInstitucion) {

        SQL sql = new SQL();

        sql.SELECT("BANCOS_CODIGO");
        sql.SELECT("IBAN");

        sql.FROM("FAC_BANCOINSTITUCION");

        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }

}
