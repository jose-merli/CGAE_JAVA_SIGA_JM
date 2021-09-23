package org.itcgae.siga.fac.services.impl;


import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.scs.services.impl.facturacionsjcs.UtilidadesFacturacionSJCS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EjecucionPlsServicios {

    private final Logger LOGGER = Logger.getLogger(EjecucionPlsServicios.class);

    @Autowired
    private AdmConfigMapper admConfigMapper;

    @Autowired
    private UtilidadesFacturacionSJCS utilidadesFacturacionSJCS;

    /**
     * PL para el proceso de suscripcion automatica de servicios
     */
    public String[] ejecutarPL_SuscripcionAutomaticaServicio(Integer idInstitucion, Integer idTipoServicios, Integer idServicio, Integer idServiciosInsitucion, AdmUsuarios usuario) throws Exception {

        Object[] paramIn = new Object[3];
        paramIn[0] = idInstitucion.toString(); // IDINSTITUCION
        paramIn[1] = idTipoServicios.toString(); // IDTIPOSERVICIOS
        paramIn[2] = idServicio.toString(); // IDSERVICIO
        paramIn[3] = idServiciosInsitucion.toString(); // IDSERVICIOSINSTITUCION
        paramIn[4] = new Date(); // FECHAPROCESO
        paramIn[5] = usuario.getIdusuario().toString(); // USUARIO

        String resultado[] = new String[3];

        //resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_SUSCRIPCION_AUTO(?,?,?,?,?,?)}", 3, paramIn);

        if (!resultado[1].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[3]);
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. Error en PL = " + (String) resultado[3]);
                    //utilidadesFacturacionSJCS.getMensajeIdioma(usuario.getIdlenguaje(), "messages.factSJCS.error.pagoTurnosSJCS"));//??????
        }

        return resultado;
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
//    public String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
//            throws IOException, NamingException, SQLException {
//
//        String result[] = null;
//
//        if (outParameters > 0)
//            result = new String[outParameters];
//        DataSource ds = getOracleDataSource();
//        Connection con = ds.getConnection();
//        try {
//            CallableStatement cs = con.prepareCall(functionDefinition);
//            int size = inParameters.length;
//
//            // input Parameters
//            for (int i = 0; i < size; i++) {
//
//                cs.setString(i + 1, (String) inParameters[i]);
//            }
//            // output Parameters
//            for (int i = 0; i < outParameters; i++) {
//                cs.registerOutParameter(i + size + 1, Types.VARCHAR);
//            }
//
//            for (int intento = 1; intento <= 2; intento++) {
//                try {
//                    cs.execute();
//                    break;
//
//                } catch (SQLTimeoutException tex) {
//                    throw tex;
//
//                } catch (SQLException ex) {
//                    if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
//                        // segunda vez deberia funcionar)
//                        throw ex;
//                    }
//                }
//
//            }
//
//            for (int i = 0; i < outParameters; i++) {
//                result[i] = cs.getString(i + size + 1);
//            }
//            cs.close();
//            return result;
//
//        } catch (Exception ex) {
//            return null;
//        } finally {
//            con.close();
//            con = null;
//        }
//    }
}