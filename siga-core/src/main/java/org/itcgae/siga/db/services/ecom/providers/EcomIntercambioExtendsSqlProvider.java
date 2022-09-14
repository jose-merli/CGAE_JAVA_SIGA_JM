package org.itcgae.siga.db.services.ecom.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.EcomIntercambioSqlProvider;

import java.util.Arrays;

public class EcomIntercambioExtendsSqlProvider extends EcomIntercambioSqlProvider {

    private static String CASE_ESTADO_RESPUESTA = "CASE " +
            "WHEN IC.IDESTADORESPUESTA = 4 THEN 'Error indeterminado' " +
            "WHEN IC.IDESTADORESPUESTA = 6 THEN 'Error de validación' " +
            "WHEN IC.IDESTADORESPUESTA = 5 THEN'Enviado' " +
            "ELSE 'Envío en proceso' END";

    private static String ID_OPERACION_ALTA_EJG = "78";
    private static String ID_OPERACION_DOCUMENTACION_EJG = "79";
    private static String ID_OPERACION_DOCUMENTACION_ADICIONAL_EJG = "88";

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

        String idEcomCola = getEcomColaListadoIntercambios(ID_OPERACION_ALTA_EJG, idInstitucion, anio, idTipoEJG, numero);
        sql.WHERE(String.format("IC.IDECOMCOLA IN (%s)", idEcomCola));
        return sql.toString();
    }

    public String getListadoIntercambiosDocumentacionEJG(String idInstitucion, String anio, String idTipoEJG, String numero) {
        SQL sql = new SQL();
        sql.SELECT("IC.*");
        sql.SELECT(String.format("(%s) estadoRespuesta", CASE_ESTADO_RESPUESTA));

        sql.FROM("ECOM_INTERCAMBIO IC");

        String idEcomCola = getEcomColaListadoIntercambios(
                String.join(", ", Arrays.asList(ID_OPERACION_DOCUMENTACION_EJG, ID_OPERACION_DOCUMENTACION_ADICIONAL_EJG)),
                idInstitucion, anio, idTipoEJG, numero);
        sql.WHERE(String.format("IC.IDECOMCOLA IN (%s)", idEcomCola));
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
