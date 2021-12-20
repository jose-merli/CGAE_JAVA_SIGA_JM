package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenDocumentsolicitudinstituSqlProvider;

public class CenDocumentsolicitudinstituSqlExtendsProvider extends CenDocumentsolicitudinstituSqlProvider {

    public String getDocRequerida(Short idInstitucion, String tipoColegiacion, String tipoSolicitud, String modalidadDocumentacion, String idLenguaje){
        SQL SQL = new SQL();

        SQL.SELECT("DS.IDDOCUMENTACION",
                UtilidadesString.getCampoMultidioma("DS.DESCRIPCION", idLenguaje));
        SQL.FROM("CEN_DOCUMENTSOLICITUDINSTITU DSI");
        SQL.INNER_JOIN("CEN_DOCUMENTACIONSOLICITUD DS ON DSI.IDDOCUMSOLIINSTITU = DS.IDDOCUMENTACION");
        SQL.WHERE("IDINSTITUCION = '"+idInstitucion+"'",
                "IDTIPOCOLEGIACION = '"+tipoColegiacion+"'",
                "IDTIPOSOLICITUD = '"+tipoSolicitud+"'",
                "IDMODALIDAD = '"+modalidadDocumentacion+"'");

        return SQL.toString();
    }
}
