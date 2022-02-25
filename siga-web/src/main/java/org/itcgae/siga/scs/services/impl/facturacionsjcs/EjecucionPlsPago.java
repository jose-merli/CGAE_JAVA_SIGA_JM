package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;

@Service
public class EjecucionPlsPago {

    private final Logger LOGGER = Logger.getLogger(EjecucionPlsPago.class);

    @Autowired
    private AdmConfigMapper admConfigMapper;

    @Autowired
    private UtilidadesFacturacionSJCS utilidadesFacturacionSJCS;

    /**
     * PL para el pago de Turnos de Oficio con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoTurnosOficio(Integer idInstitucion, Integer idPago, AdmUsuarios usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idPago.toString(); // IDPAGO
        paramIn[2] = usuario.getIdusuario().toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_TURNOS_OFI(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("ejecutarPL_PagoTurnosOficio -> Error en PL = " + (String) resultado[3]);
            throw new FacturacionSJCSException("Ha ocurrido un error al ejecutar el Pago de Turnos de Justicia Gratuita. Error en PL = " + (String) resultado[3],
                    "messages.factSJCS.error.pagoTurnosSJCS");
        }

        return resultado;
    }

    /**
     * PL para el pago de Guardias con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoGuardias(Integer idInstitucion, Integer idFacturacion, AdmUsuarios usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idFacturacion.toString(); // IDFACTURACION
        paramIn[2] = usuario.getIdusuario().toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_GUARDIAS(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("ejecutarPL_PagoGuardias -> Error en PL = " + (String) resultado[3]);
            throw new FacturacionSJCSException("Ha ocurrido un error al ejecutar el Pago de Guardias de Justicia Gratuita. Error en PL = " + (String) resultado[3],
                    "messages.factSJCS.error.pagoGuardiasSJCS");
        }

        return resultado;
    }

    /**
     * PL para el pago de SOJ con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoSOJ(Integer idInstitucion, Integer idFacturacion, AdmUsuarios usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idFacturacion.toString(); // IDFACTURACION
        paramIn[2] = usuario.getIdusuario().toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_SOJ(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("ejecutarPL_PagoSOJ -> Error en PL = " + (String) resultado[3]);
            throw new FacturacionSJCSException("Ha ocurrido un error al ejecutar el Pago de Expedientes SOJ de Justicia Gratuita. Error en PL = " + (String) resultado[3],
                    "messages.factSJCS.error.pagoExpedientesSogSJCS");
        }

        return resultado;
    }

    /**
     * PL para el pago de EJG con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoEJG(Integer idInstitucion, Integer idFacturacion, AdmUsuarios usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idFacturacion.toString(); // IDFACTURACION
        paramIn[2] = usuario.getIdusuario().toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_EJG(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("ejecutarPL_PagoEJG -> Error en PL = " + (String) resultado[3]);
            throw new FacturacionSJCSException("Ha ocurrido un error al ejecutar el Pago de Expedientes EJG de Justicia Gratuita. Error en PL = " + (String) resultado[3],
                    "messages.factSJCS.error.pagoExpedientesEjgSJCS");
        }

        return resultado;
    }

    /**
     * @param idinstitucion
     * @param idpersona
     * @param esSociedad
     * @return
     * @throws Exception
     */
    public String[] ejecutarPLCalcularIRPF_Pagos(String idinstitucion, String idpersona, boolean esSociedad) throws Exception {

        Object[] param_in = new Object[3];
        param_in[0] = idpersona;
        param_in[1] = (esSociedad ? "1" : "0");
        param_in[2] = idinstitucion;

        String resultadoPl[] = new String[3];
        resultadoPl = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_CALCULAR_IRPF(?,?,?,?,?,?,?)}", 4, param_in);
        return resultadoPl;
    }

    /**
     * PL para aplicar las retenciones judiciales.
     *
     * @param idInstitucion
     * @param idPago
     * @param idPersona
     * @return
     * @throws Exception
     */
    public String[] ejecutarPLAplicarRetencionesJudiciales(
            String idInstitucion, String idPago,
            String idPersona, String importeNeto, String usuMod, String idioma) throws Exception {

        Object[] param_in = new Object[6]; //Parametros de entrada del PL
        String resultado[] = new String[2]; //Parametros de salida del PL

        try {
            //Parametros de entrada del PL
            param_in[0] = idInstitucion;
            param_in[1] = idPago;
            param_in[2] = idPersona;
            param_in[3] = importeNeto;
            param_in[4] = usuMod;
            param_in[5] = idioma;

            //Ejecucion del PL
            resultado = callPLProcedure(
                    "{call PKG_SIGA_RETENCIONES_SJCS.PROC_FCS_APLICAR_RETENC_JUDIC (?,?,?,?,?,?,?,?)}",
                    2, param_in);
        } catch (Exception e) {
            resultado[0] = "1"; //ERROR P_CODRETORNO
            resultado[1] = "ERROR"; //ERROR P_DATOSERROR
        }
        //Resultado del PL
        return resultado;
    }

    public String ejecutarPLExportarTurno(String idInstitucion, String idFacturacionDesde, String idFacturacionHasta, String idPersona,
                                          String pathFichero, String fichero, String idioma, AdmUsuarios usuario) throws Exception {
        Object[] param_in; // Parametros de entrada del PL
        String resultado[] = null; // Parametros de salida del PL

        try {
            param_in = new Object[7];
            param_in[0] = idInstitucion;
            param_in[1] = idFacturacionDesde;
            param_in[2] = (idFacturacionHasta == null ? "" : idFacturacionHasta);
            param_in[3] = (idPersona == null ? "" : idPersona);
            param_in[4] = pathFichero;
            param_in[5] = fichero;
            param_in[6] = idioma;

            // Ejecucion del PL
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_EXPORTAR_TURNOS_OFI (?,?,?,?,?,?,?,?,?)}", 2, param_in);
            if (!resultado[0].equalsIgnoreCase("0")) {
                //ClsLogging.writeFileLog("Error en PL = " + (String) resultado[1], 3);
                LOGGER.error("ejecutarPLExportarTurno -> Error en PL = " + (String) resultado[1]);
            }

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al exportar datos", e, "messages.factSJCS.error.exportDatos");
        }

        // Resultado del PL
        return resultado[0];
    }

    public String ejecutarPLExportarGuardias(String idInstitucion, String idFacturacionDesde, String idFacturacionHasta, String idPersona,
                                             String pathFichero, String fichero, String idioma, AdmUsuarios usuario) throws Exception {

        Object[] param_in; // Parametros de entrada del PL
        String resultado[] = null; // Parametros de salida del PL

        try {
            param_in = new Object[7];
            param_in[0] = idInstitucion;
            param_in[1] = idFacturacionDesde;
            param_in[2] = (idFacturacionHasta == null ? "" : idFacturacionHasta);
            param_in[3] = (idPersona == null ? "" : idPersona);
            param_in[4] = pathFichero;
            param_in[5] = fichero;
            param_in[6] = idioma;

            // Ejecucion del PL
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_EXPORTAR_GUARDIAS (?,?,?,?,?,?,?,?,?)}", 2, param_in);
            if (!resultado[0].equalsIgnoreCase("0")) {
                //ClsLogging.writeFileLog("Error en PL = " + (String) resultado[1], 3);
                LOGGER.error("ejecutarPLExportarGuardias -> Error en PL = " + (String) resultado[1]);
            }

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al exportar datos", e, "messages.factSJCS.error.exportDatos");
        }

        // Resultado del PL
        return resultado[0];
    }

    public String ejecutarPLDeshacerCierre(Short idInstitucion, Date fechaPago) throws Exception {

        String[] resultado;

        try {

            Object[] param_in = new Object[2];
            param_in[0] = idInstitucion;
            param_in[1] = fechaPago;
            LOGGER.info("Entrada al procedimiento PROC_FACSJCS_DESCIERREPAGO: " + param_in);
            for(int i = 0; i < param_in.length; i++){
                LOGGER.info("Resultado["+i+"]: " + param_in[i]);
            }
            resultado = callPLProcedure("{call PROC_FACSJCS_DESCIERREPAGO (?,?,?,?)}", 2, param_in);
            LOGGER.info("Resultado del procedimiento PROC_FACSJCS_DESCIERREPAGO: " + resultado);
            if(resultado == null){
                return "";
            }
            for(int i = 0; i < resultado.length; i++){
                LOGGER.info("Resultado["+i+"]: " + resultado[i]);
            }
            if (!resultado[0].equalsIgnoreCase("0")) {
                LOGGER.error("ejecutarPLDeshacerCierre -> Error en PL = " + (String) resultado[1]);
            }

        } catch (Exception e) {
            LOGGER.error(e.getCause());
            e.printStackTrace();
            throw new Exception("Error al ejecutar el PL de deshacer cierre", e);
        }

        return resultado[0];
    }

    /**
     * Recupera el datasource con los datos de conexión sacados del fichero de
     * configuracion
     *
     * @return
     * @throws IOException
     * @throws NamingException
     */
    private DataSource getOracleDataSource() throws IOException, NamingException {
        try {

            LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");

            AdmConfigExample example = new AdmConfigExample();
            example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
            List<AdmConfig> config = admConfigMapper.selectByExample(example);
            Context ctx = new InitialContext();
            return (DataSource) ctx.lookup(config.get(0).getValor());
        } catch (NamingException e) {
            throw e;
        }
    }

    /**
     * Calls a PL Funtion
     *
     * @param functionDefinition string that defines the function
     * @param inParameters       input parameters
     * @param outParameters      number of output parameters
     * @return error code, '0' if ok
     * @throws NamingException
     * @throws IOException
     * @throws SQLException
     * @throws Exception       type Exception
     * @author CSD
     */
    public String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
            throws IOException, NamingException, SQLException {

        String result[] = null;

        if (outParameters > 0)
            result = new String[outParameters];
        DataSource ds = getOracleDataSource();
        Connection con = ds.getConnection();
        try {
            CallableStatement cs = con.prepareCall(functionDefinition);
            int size = inParameters.length;

            // input Parameters
            for (int i = 0; i < size; i++) {
                if (inParameters[i] instanceof Date) {
                    cs.setDate(i + 1, new java.sql.Date(((Date) inParameters[i]).getTime()));
                } else if (inParameters[i] instanceof Short) {
                    cs.setShort(i + 1, (Short) inParameters[i]);
                } else {
                    cs.setString(i + 1, (String) inParameters[i]);
                }
            }
            // output Parameters
            for (int i = 0; i < outParameters; i++) {
                cs.registerOutParameter(i + size + 1, Types.VARCHAR);
            }

            for (int intento = 1; intento <= 2; intento++) {
                try {
                    cs.execute();
                    break;

                } catch (SQLTimeoutException tex) {
                    throw tex;

                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
                        // segunda vez deberia funcionar)
                        throw ex;
                    }
                }

            }

            for (int i = 0; i < outParameters; i++) {
                result[i] = cs.getString(i + size + 1);
            }
            cs.close();
            return result;

        } catch (Exception ex) {
            return null;
        } finally {
            con.close();
            con = null;
        }
    }
}
