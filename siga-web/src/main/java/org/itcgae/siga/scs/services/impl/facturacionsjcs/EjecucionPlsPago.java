package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@Service
public class EjecucionPlsPago {

    private final Logger LOGGER = Logger.getLogger(EjecucionPlsPago.class);

    @Autowired
    private AdmConfigMapper admConfigMapper;

    /**
     * PL para el pago de Turnos de Oficio con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoTurnosOficio(Integer idInstitucion, Integer idPago, Integer usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idPago.toString(); // IDPAGO
        paramIn[2] = usuario.toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_TURNOS_OFI(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[3]);
            throw new Exception("Ha ocurrido un error al ejecutar el Pago de Turnos de Justicia Gratuita.\nError en PL = " + (String) resultado[3]);
        }

        return resultado;
    }

    /**
     * PL para el pago de Guardias con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoGuardias(Integer idInstitucion, Integer idFacturacion, Integer usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idFacturacion.toString(); // IDFACTURACION
        paramIn[2] = usuario.toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_GUARDIAS(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[3]);
            throw new Exception("Ha ocurrido un error al ejecutar el Pago de Guardias de Justicia Gratuita.\nError en PL = " + (String) resultado[3]);
        }

        return resultado;
    }

    /**
     * PL para el pago de SOJ con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoSOJ(Integer idInstitucion, Integer idFacturacion, Integer usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idFacturacion.toString(); // IDFACTURACION
        paramIn[2] = usuario.toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_SOJ(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[3]);
            throw new Exception("Ha ocurrido un error al ejecutar el Pago de Expedientes SOJ de Justicia Gratuita.\nError en PL = " + (String) resultado[3]);
        }

        return resultado;
    }

    /**
     * PL para el pago de EJG con Criterio por Facturacion
     */
    public String[] ejecutarPL_PagoEJG(Integer idInstitucion, Integer idFacturacion, Integer usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idFacturacion.toString(); // IDFACTURACION
        paramIn[2] = usuario.toString(); // USUARIO

        String resultado[] = new String[3];

        resultado = callPLProcedure("{call PKG_SIGA_PAGOS_SJCS.PROC_FCS_PAGO_EJG(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[3]);
            throw new Exception("Ha ocurrido un error al ejecutar el Pago de Expedientes EJG de Justicia Gratuita.\nError en PL = " + (String) resultado[3]);
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
    private String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
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

                cs.setString(i + 1, (String) inParameters[i]);
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
