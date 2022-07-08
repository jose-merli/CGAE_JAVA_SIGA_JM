package org.itcgae.siga.fac.services.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.BorrarSuscripcionBajaItem;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjecucionPlsServicios {

    private final Logger LOGGER = Logger.getLogger(EjecucionPlsServicios.class);

    @Autowired
    private AdmConfigMapper admConfigMapper;

    /**
     * PL para el proceso de suscripcion automatica de servicios
     */
    public String[] ejecutarPL_SuscripcionAutomaticaServicio(short idInstitucion, int idTipoServicios, int idServicio, String idServiciosInstitucion, AdmUsuarios usuario) throws Exception {

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
        
//        LOGGER.info("SUSCRIPCION_AUTO: resultado[0] = " + resultado[0] + " y resultado[1] = " + resultado[1]);

        if (resultado != null) {
        	if (!resultado[0].equalsIgnoreCase("0")) {
                LOGGER.error("Error en PL = " + (String) resultado[1]);
                throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. Error en PL = " + (String) resultado[1]);
            }
        } else {
        	LOGGER.error("Error en PL");
        	throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. El resultado del PL es null");
        }

        return resultado;
    }
    
    /**
     * PL para el proceso de revision automatica de servicios de unainstitucion
     */
    public String[] ejecutarPL_RevisionAutomaticaServicios(short idInstitucion, AdmUsuarios usuario) throws Exception {

        Object[] paramIn = new Object[3];
    
        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        paramIn[0] = idInstitucion; // IDINSTITUCION
        paramIn[1] = sqlDate; // FECHAPROCESO
        paramIn[2] = usuario.getIdusuario(); // IDUSUARIO

        String resultado[] = new String[2];

        //El primer parametro ?????? son el numero de parametros entradas/salidas del PL
        //El segundo parametro el numero de parametros de salida del PL
        resultado = callPLProcedure3("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_AUTO(?,?,?,?,?)}", 2, paramIn);
        
//        LOGGER.info("REVISION_AUTO: resultado[0] = " + resultado[0] + " y resultado[1] = " + resultado[1]);

        if (resultado != null) {
        	if (!resultado[0].equalsIgnoreCase("0")) {
                LOGGER.error("Error en PL = " + (String) resultado[1]);
                throw new Exception("Ha ocurrido un error al ejecutar el proceso de revisón automática de servicios de una institución. Error en PL = " + (String) resultado[1]);
            }
        } else {
        	LOGGER.error("Error en PL");
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de revisón automática de servicios de una institución. El resultado del PL es null");
        }

        return resultado;
    }
    
    
    /**
     * PL para el proceso de baja de servicios
     */
    public String[] ejecutarPL_ServiciosAutomaticosProcesoBaja(short idInstitucion, int idTipoServicios, int idServicio, int idServiciosInstitucion, AdmUsuarios usuario) throws Exception {
    	
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

//        LOGGER.info("BAJA_SERVICIO: resultado[0] = " + resultado[0] + " y resultado[1] = " + resultado[1]);
        if (resultado != null) {
        	if (!resultado[0].equalsIgnoreCase("0")) {
                LOGGER.error("Error en PL = " + (String) resultado[1]);
                throw new Exception("Ha ocurrido un error al ejecutar el proceso de baja del servicio. Error en PL = " + (String) resultado[1]);
            }
        } else {
        	LOGGER.error("Error en PL");
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de baja del servicio. El resultado del PL es null");
        }

        return resultado;
    }

    /**
     * PL para el proceso de baja de servicios
     */
    public String[] ejecutarPL_ServiciosAutomaticosProcesoEliminarSuscripcion(short idInstitucion, BorrarSuscripcionBajaItem borrarSuscripcionBajaItem, AdmUsuarios usuario) throws Exception {
    	
        Object[] paramIn = new Object[8];
    
        paramIn[0] = idInstitucion; // IDINSTITUCION
        paramIn[1] = borrarSuscripcionBajaItem.getIdtiposervicios(); // IDTIPOSERVICIOS
        paramIn[2] = borrarSuscripcionBajaItem.getIdservicio(); // IDSERVICIO
        paramIn[3] = borrarSuscripcionBajaItem.getIdserviciosinstitucion(); //IDSERVICIOSINSTITUCION
        paramIn[4] = "0"; // P_ALTA - IN - Indica los tipos de solicitudes a borrar - VARCHAR2(1) (RADIOBUTTON) //0 para borrar. 
        paramIn[5] = null; //P_FECHAALTA - IN - Fecha de alta en formato DD/MM/YYYY - VARCHAR2(10) (DATEPICKER)
        paramIn[6] = borrarSuscripcionBajaItem.getIncluirbajasmanuales(); //- P_INCLUIRMANUALES - IN - Incluir servicios manuales - VARCHAR2(1)    (CHECKBOX)
        paramIn[7] = usuario.getIdusuario(); // - P_USUMODIFICACION - IN - Usuario que realiza la modificacion - NUMBER(5)
        
        
        String resultado[] = new String[3]; 

        //El primer parametro ?????? son el numero de parametros entradas/salidas del PL
        //El segundo parametro el numero de parametros de salida del PL
        resultado = callPLProcedure2("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_ELIMINAR_SUSCRIPCION(?,?,?,?,?,?,?,?,?,?,?)}", 3, paramIn);
        

//        LOGGER.info("ELIMINAR_SUSCRIPCION: resultado[0] = " + resultado[0] + ", resultado[1] = " + resultado[1] + " y resultado[2] = "+ resultado[2]);
        
        if (resultado != null) {
        	if (!resultado[0].equalsIgnoreCase("0")) {
                LOGGER.error("Error en PL = " + (String) resultado[1]);
                throw new Exception("Ha ocurrido un error al ejecutar el proceso de baja del servicio. Error en PL = " + (String) resultado[1]);
            }else if(resultado[0].equalsIgnoreCase("0")) {
            	LOGGER.info("El PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_ELIMINAR_SUSCRIPCION se ha ejecutado correctamente, numero de suscripciones borradas: " + resultado[2]);
            }
        } else {
        	LOGGER.error("Error en PL");
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de baja del servicio. El resultado del PL es null");
        }

        return resultado;
    }
    
    /**
     * PL para el revisar todos los servicios de una persona y darse baja o alta automaticamente
     */
    public String[] ejecutarPL_ProcesoRevisionLetrado(short idInstitucion, Long idPersona, Date fechaProcesamiento, AdmUsuarios usuario) throws Exception {
    	
        Object[] paramIn = new Object[4];
        
        java.sql.Date fechaProcesamientoSql;
        if(fechaProcesamiento != null) {
        	fechaProcesamientoSql = new java.sql.Date(fechaProcesamiento.getTime());
        }
        else {
        	java.util.Date utilDate = new Date();
        	fechaProcesamientoSql = new java.sql.Date(utilDate.getTime());
        }
        paramIn[0] = idInstitucion; // Identificador de la institucion - NUMBER(4)
        paramIn[1] = idPersona; // Identificador de la persona suscrita al servicio - NUMBER(10) 
        paramIn[2] = fechaProcesamientoSql; //Fecha para la revision de las suscripciones - DATE
        paramIn[3] = usuario.getIdusuario(); // IDUSUARIO
        
        String resultado[] = new String[2]; 

        //El primer parametro ?????? son el numero de parametros entradas/salidas del PL
        //El segundo parametro el numero de parametros de salida del PL
        resultado = callPLProcedureRevLetr("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO(?,?,?,?,?,?)}", 2, paramIn);
        
//        LOGGER.info("BAJA_SERVICIO: resultado[0] = " + resultado[0] + " y resultado[1] = " + resultado[1]);

        if (resultado != null) {
	        if (!resultado[0].equalsIgnoreCase("0")) {
	            LOGGER.error("Error en PL = " + (String) resultado[1]);
	            throw new Exception("Ha ocurrido un error al ejecutar el proceso de revisar las condiciones de los servicios para una persona. Error en PL = " + (String) resultado[1]);
	        }
        } else {
        	LOGGER.error("Error en PL");
            throw new Exception("Ha ocurrido un error al ejecutar el proceso de revisar las condiciones de los servicios para una persona. El resultado del PL es null");
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
    public String[] callPLProcedure2(String functionDefinition, int outParameters, Object[] inParameters)
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
            	
            	if(i == 1 || i == 2  || i == 3 || i == 7) {
            		cs.setInt(i + 1, (int) inParameters[i]);
            	}
            	
            	if (i == 4 || i == 5 || i == 6) {
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
    
    
    
    public String[] callPLProcedure3(String functionDefinition, int outParameters, Object[] inParameters)
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
            	
            	if(i == 1 ) {
            		cs.setDate(i + 1, (java.sql.Date) inParameters[i]);
            	}
            	
            	if (i == 2) {
            		cs.setLong(i + 1, (int) inParameters[i]);
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
    
    public String[] callPLProcedureRevLetr(String functionDefinition, int outParameters, Object[] inParameters)
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
            	
            	if(i == 1 ) {
            		cs.setLong(i + 1, (long) inParameters[i]);
            	}
            	
            	if (i == 2) {
            		cs.setDate(i + 1, (java.sql.Date) inParameters[i]);
            	}
            	if(i == 3 ) {
            		cs.setInt(i + 1, (int) inParameters[i]);
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