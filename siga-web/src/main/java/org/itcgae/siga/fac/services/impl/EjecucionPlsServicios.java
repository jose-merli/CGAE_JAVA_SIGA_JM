package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
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
import java.sql.Timestamp;


import org.itcgae.siga.scs.services.impl.facturacionsjcs.UtilidadesFacturacionSJCS;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;

@Service
public class EjecucionPlsServicios {

    private final Logger LOGGER = Logger.getLogger(EjecucionPlsServicios.class);

    @Autowired
    private AdmConfigMapper admConfigMapper;

    /**
     * PL para el proceso de suscripcion automatica de servicios
     */
    public String[] ejecutarPL_SuscripcionAutomaticaServicio(short idInstitucion, int idTipoServicios, int idServicio, String idServiciosInstitucion, AdmUsuarios usuario) throws Exception {
    	Date date = new Date();
    	
        Object[] paramIn = new Object[6];
    
        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        paramIn[0] = idInstitucion; // IDINSTITUCION
        paramIn[1] = idTipoServicios; // IDTIPOSERVICIOS
        paramIn[2] = idServicio; // IDSERVICIO
        paramIn[3] = Integer.parseInt(idServiciosInstitucion); // IDSERVICIOSINSTITUCION
        paramIn[4] = sqlDate; // FECHAPROCESO
        paramIn[5] = usuario.getIdusuario(); // IDUSUARIO

        String resultado[] = new String[2];

        //El primer parametro ?????? son el numero de parametros entradas/salidas del PL
        //El segundo parametro el numero de parametros de salida del PL
        resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_SUSCRIPCION_AUTO(?,?,?,?,?,?,?,?)}", 2, paramIn);

        if (!resultado[0].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[1]);
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. Error en PL = " + (String) resultado[1]);
        }

        return resultado;
    }
    
    /**
     * PL para el proceso de baja de servicios
     */
    public String[] ejecutarPL_ServiciosAutomaticosProcesoBaja(short idInstitucion, int idTipoServicios, int idServicio, int idServiciosInstitucion, AdmUsuarios usuario) throws Exception {
    	Date date = new Date();
    	
        Object[] paramIn = new Object[6];
    
        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        paramIn[0] = idInstitucion; // IDINSTITUCION
        paramIn[1] = idTipoServicios; // IDTIPOSERVICIOS
        paramIn[2] = idServicio; // IDSERVICIO
        paramIn[3] = idServiciosInstitucion; // IDSERVICIOSINSTITUCION
        paramIn[4] = sqlDate; // FECHAPROCESO
        paramIn[5] = usuario.getIdusuario(); // IDUSUARIO

        String resultado[] = new String[2];

        //El primer parametro ?????? son el numero de parametros entradas/salidas del PL
        //El segundo parametro el numero de parametros de salida del PL
        resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_BAJA_SERVICIO(?,?,?,?,?,?,?,?)}", 2, paramIn);

        if (!resultado[0].equalsIgnoreCase("0")) {
            LOGGER.error("Error en PL = " + (String) resultado[1]);
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de baja del servicio. Error en PL = " + (String) resultado[1]);
        }

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

            	if(i == 0) {
            		cs.setShort(i + 1,  (short) inParameters[i]);
            	}
            	
            	if(i == 1 || i == 2  || i == 3 || i == 5) {
            		cs.setInt(i + 1, (int) inParameters[i]);
            	}
            	
            	if (i == 4) {
            		cs.setDate(i + 1, (java.sql.Date) inParameters[i]);
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