package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenDocumentacionsolicitudSqlProvider;

public class CenDocumentacionsolicitudSqlExtendsProvider extends CenDocumentacionsolicitudSqlProvider {

    public String getNextId(){
        SQL SQL = new SQL();

        SQL.SELECT("MAX(IDDOCUMENTACION) + 1 AS IDDOCUMENTACION");
        SQL.FROM("CEN_DOCUMENTACIONSOLICITUD");

        return SQL.toString();
    }

}
