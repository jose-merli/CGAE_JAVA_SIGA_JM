package org.itcgae.siga.db.services.ecom.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.EcomIntercambioSqlProvider;

import java.util.Arrays;

public class EcomIntercambioExtendsSqlProvider extends EcomIntercambioSqlProvider {

    private static String CASE_ESTADO_RESPUESTA = "CASE " +
            "WHEN IC.IDESTADORESPUESTA = 4 THEN 'Error' " +
            "WHEN IC.IDESTADORESPUESTA = 6 THEN 'Error de validación' " +
            "WHEN IC.IDESTADORESPUESTA = 5 THEN'Enviado' " +
            "ELSE 'Envío en proceso' END";
    
    private static String ASIGNA_ENVIO_DOCUMENTO = "2";
    private static String ASIGNA_CONSULTA_NUMERO = "5";
    private static String XUNTA_ENVIA_PRESENTACION = "23"; 
    private static String XUNTA_VERIFICA_PRESENTACION = "24"; 
    private static String GV_ENVIO_DOCUMENTO = "68";
    private static String ARAGON_ENVIO_DOCUMENTO = "72";
    private static String ALCALA_ENVIO_DOCUMENTO = "77";
    private static String PERICLES_ENVIA_ALTA_EXPEDIENTE = "78";
    private static String PERICLES_ENVIA_DOCUMENTO = "79";
    private static String PERICLES_CONSULTAR_RESOLUCION = "83";
    private static String PERICLES_CONSULTAR_ESTADO = "84";
    private static String PERICLES_ENVIA_COMUNICACION = "88";
    private static String PERICLES_ENVIA_EXPEDIENTE_PENDIENTE = "89";
    

    public String getNewId() {
        SQL sql = new SQL();

        sql.SELECT("SEQ_ECOM_INTERCAMBIO.NEXTVAL");
        sql.FROM("DUAL");

        return sql.toString();
    }

    public String getListadoIntercambiosAltaEJG(String idInstitucion, String anio, String idTipoEJG, String numero) {
        SQL sql = new SQL();
        sql.SELECT("IC.*");
        sql.SELECT(String.format("(%s) estadoRespuesta", CASE_ESTADO_RESPUESTA));

        sql.FROM("ECOM_INTERCAMBIO IC");

        String idEcomCola = getEcomColaListadoIntercambios(
        		String.join(", ", Arrays.asList(PERICLES_ENVIA_ALTA_EXPEDIENTE, PERICLES_CONSULTAR_ESTADO, PERICLES_CONSULTAR_RESOLUCION, ASIGNA_CONSULTA_NUMERO, PERICLES_ENVIA_EXPEDIENTE_PENDIENTE,
        				XUNTA_ENVIA_PRESENTACION, XUNTA_VERIFICA_PRESENTACION)), idInstitucion, anio, idTipoEJG, numero);
        sql.WHERE(String.format("IC.IDECOMCOLA IN (%s)", idEcomCola));
        sql.ORDER_BY("FECHAENVIO DESC");
        return sql.toString();
    }

    public String getListadoIntercambiosDocumentacionEJG(String idInstitucion, String anio, String idTipoEJG, String numero) {
        SQL sql = new SQL();
        sql.SELECT("IC.*");
        sql.SELECT(String.format("(%s) estadoRespuesta", CASE_ESTADO_RESPUESTA));

        sql.FROM("ECOM_INTERCAMBIO IC");

        String idEcomCola = getEcomColaListadoIntercambios(
                String.join(", ", Arrays.asList(PERICLES_ENVIA_DOCUMENTO, PERICLES_ENVIA_COMUNICACION,  ASIGNA_ENVIO_DOCUMENTO, GV_ENVIO_DOCUMENTO, ARAGON_ENVIO_DOCUMENTO,
                		ALCALA_ENVIO_DOCUMENTO)),
                idInstitucion, anio, idTipoEJG, numero);
        sql.WHERE(String.format("IC.IDECOMCOLA IN (%s)", idEcomCola));
        sql.ORDER_BY("FECHAENVIO DESC");
        return sql.toString();
    }

    private String getEcomColaListadoIntercambios(String idOperacion, String idInstitucion, String anio, String idTipoEJG, String numero) {
        SQL sql = new SQL();
        sql.SELECT("C.IDECOMCOLA");
        sql.FROM("ECOM_COLA C");
        sql.WHERE(String.format("C.IDOPERACION IN (%s)", idOperacion));
        sql.WHERE(String.format("C.IDECOMCOLA IN (%S)", getParamEcomCola("IDINSTITUCION", idInstitucion)));
        sql.WHERE(String.format("C.IDECOMCOLA IN (%S)", getParamEcomCola("ANIO", anio)));
        sql.WHERE(String.format("C.IDECOMCOLA IN (%S)", getParamEcomCola("IDTIPOEJG", idTipoEJG)));
        sql.WHERE(String.format("C.IDECOMCOLA IN (%S)", getParamEcomCola("NUMERO", numero)));
        return sql.toString();
    }

    private String getParamEcomCola(String clave, String valor) {
        SQL sql = new SQL();
        sql.SELECT("CP.IDECOMCOLA");
        sql.FROM("ECOM_COLA_PARAMETROS CP");
        sql.WHERE(String.format("CP.CLAVE = '%s' AND CP.VALOR = '%s'", clave, valor));
        return sql.toString();
    }

}
