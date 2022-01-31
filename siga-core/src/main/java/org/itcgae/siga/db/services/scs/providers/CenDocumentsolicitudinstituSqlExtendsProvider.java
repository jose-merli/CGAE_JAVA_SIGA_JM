package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenDocumentsolicitudinstituSqlProvider;

public class CenDocumentsolicitudinstituSqlExtendsProvider extends CenDocumentsolicitudinstituSqlProvider {

    public String getDocRequerida(Short idInstitucion, String tipoColegiacion, String tipoSolicitud, String modalidadDocumentacion, String idLenguaje, String idSolicitud, String codDocAnexo){
        SQL SQL = new SQL();

        SQL.SELECT("DS.IDDOCUMENTACION",
                UtilidadesString.getCampoMultidioma("DS.DESCRIPCION", idLenguaje),
                "DS.OBSERVACIONES",
                "CASE WHEN DS.OBLIGATORIO = '1' THEN 'SÍ' ELSE 'NO' END AS OBLIGATORIO",
                "DSI.IDMODALIDAD",
                "DSI.IDTIPOSOLICITUD",
                "DSI.IDTIPOCOLEGIACION",
                "CODIGOEXT");
        if(!UtilidadesString.esCadenaVacia(idSolicitud)){
            SQL.SELECT("DP.NOMBREFICHERO",
                    "DP.IDFICHERO");
        }else{
            SQL.SELECT("NULL AS NOMBREFICHERO",
                    "NULL AS IDFICHERO");
        }
        SQL.FROM("CEN_DOCUMENTSOLICITUDINSTITU DSI");
        SQL.INNER_JOIN("CEN_DOCUMENTACIONSOLICITUD DS ON DSI.IDDOCUMSOLIINSTITU = DS.IDDOCUMENTACION");
        if(!UtilidadesString.esCadenaVacia(idSolicitud)) {
            SQL.LEFT_OUTER_JOIN("CEN_DOCUMENTACIONPRESENTADA DP ON DSI.IDDOCUMSOLIINSTITU = DP.IDDOCUMENTACION AND DP.IDSOLICITUD='"+idSolicitud+"'");
        }
        SQL.WHERE("IDINSTITUCION = '"+idInstitucion+"'",
                "IDTIPOCOLEGIACION = '"+tipoColegiacion+"'",
                "IDTIPOSOLICITUD = '"+tipoSolicitud+"'",
                "IDMODALIDAD = '"+modalidadDocumentacion+"'",
                "FECHA_BAJA IS NULL",
                "CODIGOEXT != '"+codDocAnexo+"'");
        if(!UtilidadesString.esCadenaVacia(idSolicitud)) {
            SQL.OR();
            SQL.WHERE("(CODIGOEXT = '"+codDocAnexo+"' AND IDFICHERO IS NOT NULL)");
        }
        SQL.ORDER_BY("OBLIGATORIO DESC");
        return SQL.toString();
    }
}
