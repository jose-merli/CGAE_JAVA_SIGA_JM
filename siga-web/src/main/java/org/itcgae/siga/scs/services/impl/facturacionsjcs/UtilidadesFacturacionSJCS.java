package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class UtilidadesFacturacionSJCS 
{
	private Logger LOGGER = Logger.getLogger(UtilidadesFacturacionSJCS.class);
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;
	
	@Autowired
	private FcsFacturacionjgMapper fcsFacturacionjgMapper;
	
	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;

	/**
	 * Funcion que devuelve el nombre de los ficheros de exportacion
	 * @param idInstitucion
	 * @param idFacturacion
	 * @param usuario
	 * @return
	 * @throws SIGAException
	 */
	@SuppressWarnings("rawtypes")
	public Hashtable getNombreFicherosFacturacion (FcsFacturacionjg itemFact, Integer usuario) throws Exception {
		return getNombreFicherosFac(itemFact, null, null, usuario);
	}

	/**
	 * Funcion que devuelve el nombre de los ficheros de exportacion
	 * @param idInstitucion
	 * @param idFacturacion
	 * @param idPago
	 * @param idPersona
	 * @param usuario
	 * @return
	 * @throws SIGAException
	 */
	@SuppressWarnings("rawtypes")
	public Hashtable getNombreFicherosPago (FcsFacturacionjg itemFact, Integer idPago, Long idPersona, Integer usuario) throws Exception {
		return getNombreFicherosFac(itemFact, idPago, idPersona, usuario);
	}


	
	/**
 	 * Funcion que devuelve el nombre de los ficheros de exportacion
	 * @param idInstitucion
	 * @param idFacturacion
	 * @param idPago
	 * @param idPersona
	 * @param usuario
	 * @return
	 * @throws SIGAException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static private Hashtable getNombreFicherosFac (FcsFacturacionjg itemFact, Integer idPago, Long idPersona, Integer usuario) throws Exception  
	{
		try {
			Hashtable nombreFicheros = new Hashtable();
			
			boolean bPrevision      = (itemFact.getPrevision().equalsIgnoreCase(SigaConstants.DB_TRUE)?true:false);
			boolean bRegularizacion = (itemFact.getRegularizacion().equalsIgnoreCase(SigaConstants.DB_TRUE)?true:false);
			
			String sNombreFichero = "";
			if (bPrevision && bRegularizacion) 
				return nombreFicheros;

			if (idPago != null) {
				sNombreFichero = "PAGOS_";				// Pagos
			}
			else {
				if (!bPrevision && !bRegularizacion)  	// Facturacion
					sNombreFichero = "FACTURACION_";
				
				if (bPrevision)							// Prevision 
					sNombreFichero = "PREVISION_";
			
				if (bRegularizacion) 					// Regularizacion
					sNombreFichero = "REGULARIZACION_";
			}
			
			String extensionFichero = "_" + itemFact.getIdinstitucion() + "_" + itemFact.getIdfacturacion();
			if (idPago != null) {
				extensionFichero += "_" + idPago;
			}
			if (idPersona != null) {
				extensionFichero += "_" + idPersona;
			}
			extensionFichero += ".XLS";
			
			nombreFicheros.put (SigaConstants.HITO_GENERAL_TURNO, sNombreFichero + "TURNOSOFICIO" + extensionFichero);
			nombreFicheros.put (SigaConstants.HITO_GENERAL_GUARDIA, sNombreFichero + "GUARDIAS" + extensionFichero);
			nombreFicheros.put (SigaConstants.HITO_GENERAL_EJG, sNombreFichero + "EJG" + extensionFichero);
			nombreFicheros.put (SigaConstants.HITO_GENERAL_SOJ, sNombreFichero + "SOJ" + extensionFichero);

			return nombreFicheros;
		}
		catch (Exception e) {
			throw new Exception (e.getMessage());
		}
	}
	
	public void exportarDatosFacturacion (FcsFacturacionjg item,
												 Integer usuario, CenInstitucion institucion)
			throws Exception
	{
		exportarDatosFac(item, null, null, usuario, institucion);
	}
	public void exportarDatosPagos (FcsFacturacionjg item,
										   Integer idPago,
										   Long idPersona,
										   Integer usuario, CenInstitucion institucion)
			throws Exception
	{
		exportarDatosFac(item, idPago, idPersona, usuario, institucion);
	}

	
	@SuppressWarnings("rawtypes")
	private void exportarDatosFac (FcsFacturacionjg itemFac, Integer idPago, Long idPersona, Integer usuario, CenInstitucion institucion) throws Exception
	{
		try {
			
			//String tipoP=SigaConstants.FACTURACION_SJCS;
			
			//GenParametrosExample example = new GenParametrosExample();
			//example.createCriteria().andModuloEqualTo("FCS").andParametroEqualTo("PATH_PREVISIONES_BD").andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_0);
			
			StringDTO config = genParametrosMapper.selectParametroPorInstitucion("PATH_PREVISIONES_BD", SigaConstants.IDINSTITUCION_0);//.selectByExample(example);
			 
			String pathFicheros = config.getValor();
			
			Hashtable nombreFicheros = null;
			if (idPago == null) { 
				nombreFicheros = getNombreFicherosFacturacion(itemFac, usuario);
			}
			//TODO Realizar cuando se haga el proceso de pagos
			/* else {
				nombreFicheros = UtilidadesFacturacionSJCS.getNombreFicherosPago(item, idPago, idPersona, usuario);
				tipoP=ClsConstants.PAGOS_SJCS;
			}*/ 
			
			//El lenguaje de los informes es el de la institucion a la que pertenecen las
			//facturaciones, no tiene que ver con el usuario que la genera. 
			//Por lo tanto accedemos al idioma de cada institucion
			
			// TURNOS DE OFICIO
	    	ejecutarPLExportar(
	    			"PROC_FCS_EXPORTAR_TURNOS_OFI",
	    			itemFac.getIdinstitucion().toString(),
	    			itemFac.getIdfacturacion().toString(), 
	    			null, 
	    			(idPersona == null ? "" : idPersona.toString()), 
	    			pathFicheros, 
	    			(String) nombreFicheros.get(SigaConstants.HITO_GENERAL_TURNO), 
	    			institucion.getIdlenguaje());
	    	
	    	// Guardias
	    	ejecutarPLExportar(
	    			"PROC_FCS_EXPORTAR_GUARDIAS",
	    			itemFac.getIdinstitucion().toString(),
	    			itemFac.getIdfacturacion().toString(), 
	    			null, 
	    			(idPersona == null ? "" : idPersona.toString()), 
	    			pathFicheros, 
	    			(String) nombreFicheros.get(SigaConstants.HITO_GENERAL_GUARDIA), 
	    			institucion.getIdlenguaje());
	    	
	    	// SOJ
	    	ejecutarPLExportar(
	    			"PROC_FCS_EXPORTAR_SOJ",
	    			itemFac.getIdinstitucion().toString(),
	    			itemFac.getIdfacturacion().toString(), 
	    			null, 
	    			(idPersona == null ? "" : idPersona.toString()), 
	    			pathFicheros, 
	    			(String) nombreFicheros.get(SigaConstants.HITO_GENERAL_SOJ), 
	    			institucion.getIdlenguaje());
	    	
	    	// EJG
	    	ejecutarPLExportar(
	    			"PROC_FCS_EXPORTAR_EJG",
	    			itemFac.getIdinstitucion().toString(),
	    			itemFac.getIdfacturacion().toString(), 
	    			null, 
	    			(idPersona == null ? "" : idPersona.toString()), 
	    			pathFicheros, 
	    			(String) nombreFicheros.get(SigaConstants.HITO_GENERAL_EJG), 
	    			institucion.getIdlenguaje());

		}
		catch (Exception e) {
    		throw new Exception ("Error al exportar datos: " + e.getMessage());			
		}
	}
	
	public String ejecutarPLExportar(String nomPL, String idInstitucion,
			String idFacturacionDesde, String idFacturacionHasta, String idPersona,
			String pathFichero, String fichero, String idioma)
			throws Exception
	{
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
	    		LOGGER.error("Error en PL = "+(String)resultado[1]);
	    	}

		} catch (Exception e) {
    		throw new Exception ("Error al exportar datos: " + e.getMessage());			
		}

		// Resultado del PL
		return resultado[0];
	} 
	
	@Transactional
	void ejecutarRegularizacionJG(FcsFacturacionjg item, CenInstitucion institucion) throws Exception {
		try
		{
			// proceso de facturacion
			
			double  importeTotal = 0;
			Double  importeOficio = null, 
			importeGuardia = null, 
			importeSOJ = null,  
			importeEJG = null; 
			
			// parametros de entrada
			Object[] param_in_facturacion = new Object[4];
			param_in_facturacion[0] = item.getIdinstitucion().toString(); // IDINSTITUCION
			param_in_facturacion[1] = item.getIdfacturacion().toString(); // IDFACTURACION
			param_in_facturacion[2] = item.getUsumodificacion().toString(); 		 // USUMODIFICACION
			param_in_facturacion[3] = institucion.getIdlenguaje();
			
			String resultado[] = null;
			
			
			//////////////////////////////////
			// TURNOS DE OFICIO rgg 29-03-2005
			resultado = new String[3];
			try {
				resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_TURNOS_OFI(?,?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equals("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio: " +(String)resultado[2] );
				}
			} catch (IOException | NamingException | SQLException e) {
				LOGGER.error("Error en PL al ejecutar la regularización de Turnos de Oficio = "+(String)resultado[2]);
				throw new Exception ("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio", e);
			}
			
			importeOficio = new Double(resultado[0].replaceAll(",","."));
			importeTotal += importeOficio.doubleValue();
			
			//////////////////////////////////
			// GUARDIAS PRESENCIALES rgg 29-03-2005
			resultado = new String[3];
			try {
				resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_GUARDIAS(?,?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equals("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la regularización de Guardias: " +(String)resultado[2]);	
				} 
			} catch (IOException | NamingException | SQLException e) {
				LOGGER.error("Error en PL al ejecutar la regularización de Guardias = "+(String)resultado[2]);
				throw new Exception ("Ha ocurrido un error al ejecutar la regularización de Guardias", e);
			}
			
			importeGuardia = new Double((String)resultado[0].replaceAll(",","."));
			importeTotal += importeGuardia.doubleValue();
			
			
			//////////////////////////////////
			// SOJ rgg 29-03-2005
			resultado = new String[3];
			try {
				resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_SOJ(?,?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equals("0")) {
					LOGGER.error("Error en PL al ejecutar la regularización de SOJ= "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la regularización de SOJ: " +(String)resultado[2]);	
				} 
			} catch (IOException | NamingException | SQLException e) {
				LOGGER.error("Error en PL al ejecutar la regularización de SOJ = "+(String)resultado[2]);
				throw new Exception ("Ha ocurrido un error al ejecutar la regularización de SOJ", e);
			}
			
			importeSOJ = new Double((String)resultado[0].replaceAll(",","."));
			importeTotal += importeSOJ.doubleValue();
			
			
			//////////////////////////////////
			// EJG rgg 29-03-2005
			resultado = new String[3];
			try {
				resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_EJG(?,?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equals("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la regularización de Expedientes de Justicia Gratuita: " +(String)resultado[2]);	
				} 
			} catch (IOException | NamingException | SQLException e) {
				LOGGER.error("Error en PL al ejecutar la regularización de EJG= "+(String)resultado[2]);
				throw new Exception ("Ha ocurrido un error al ejecutar la regularización de EJG", e);
			}
			
			importeEJG = new Double(((String)resultado[0]).replaceAll(",","."));
			importeTotal += importeEJG.doubleValue();
			
			//////////////////////////////////
			// ACTUALIZO LOS TOTALES
			item.setImporteejg(new BigDecimal(importeEJG));
			item.setImporteguardia(new BigDecimal(importeGuardia));
			item.setImporteoficio(new BigDecimal(importeOficio));
			item.setImportesoj(new BigDecimal(importeSOJ));
			item.setImportetotal(new BigDecimal(importeTotal));
			try{
				fcsFacturacionjgMapper.updateByPrimaryKey(item);
			}catch(Exception ex) {
				LOGGER.error("Ha ocurrido un error al almacenar los importes resultado de la facturacion");
				throw new Exception("Ha ocurrido un error al almacenar los importes resultado de la facturacion",ex);
			}
			
			// Exportacion de datos a EXCEL
			UtilidadesFacturacionSJCS utils = new UtilidadesFacturacionSJCS();
			utils.exportarDatosFacturacion(item, SigaConstants.USUMODIFICACION_0, institucion);
		}catch(Exception ex) {
			throw new Exception("Error en la ejecución de la Facturación SJCS. idinstitucion="+ item.getIdinstitucion()+" idfacturacion="+ item.getIdfacturacion(), ex);
		}
	
				
	}

	@Transactional 
	void ejecutarFacturacionJG(FcsFacturacionjg itemFac, CenInstitucion institucion) throws Exception {
		
		// Fichero de log
		/*GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andModuloEqualTo("FCS").andParametroEqualTo("PATH_PREVISIONES_BD").andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_0);
		
		List<GenParametros> config = genParametrosMapper.selectByExample(example);
		 
		String pathFicheros = config.get(0).getValor();
		*/
		//StringDTO config = genParametrosMapper.selectParametroPorInstitucion("PATH_PREVISIONES_BD", SigaConstants.IDINSTITUCION_0);//.selectByExample(example);
		StringDTO config = fcsFacturacionJGExtendsMapper.getParametroInstitucion(SigaConstants.IDINSTITUCION_0, "PATH_PREVISIONES_BD");
		 
		String pathFicheros = config.getValor();
		
		String sNombreFichero = pathFicheros + File.separator + "LOG_ERROR_" + itemFac.getIdinstitucion() + "_" + itemFac.getIdfacturacion() + ".log";
		File ficheroLog = new File(sNombreFichero);
		if (ficheroLog!=null && ficheroLog.exists()) {
		    ficheroLog.delete();
		}
	    
		try {
		    
			boolean prevision = false;
			
				// proceso de facturacion
				double  importeTotal = 0;
				Double  importeOficio = null, 
				importeGuardia = null, 
				importeSOJ = null,  
				importeEJG = null;
				
				//////////////////////////////////
				// TURNOS DE OFICIO rgg 16-03-2005

				Object[] param_in_facturacion = new Object[3];
				param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
				param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION 
				param_in_facturacion[2] = itemFac.getUsumodificacion().toString();        // USUMODIFICACION

				String resultado[] = new String[3];
				resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_TURNOS_OFI(?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equalsIgnoreCase("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la facturación de Turnos de Oficio: "+(String)resultado[2]);
				}
				importeOficio = new Double(resultado[0].replaceAll(",","."));
				importeTotal += importeOficio.doubleValue();


				//////////////////////////////////
				// GUARDIAS rgg 22-03-2005

				param_in_facturacion = new Object[3];
				param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
				param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
				param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

				resultado = new String[3];
				resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_GUARDIAS(?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equalsIgnoreCase("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la facturación de Guardias: "+(String)resultado[2]);
				} 
				importeGuardia = new Double(resultado[0].replaceAll(",","."));
				importeTotal += importeGuardia.doubleValue();

				//////////////////////////////////
				// EXPEDIENTES SOJ rgg 22-03-2005

				param_in_facturacion = new Object[3];
				param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
				param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
				param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); 		 // USUMODIFICACION

				resultado = new String[3];
				resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_SOJ(?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equalsIgnoreCase("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la facturación de Expedientes de Orientación Jurídica: "+(String)resultado[2]);
				} 
				importeSOJ = new Double(resultado[0].replaceAll(",","."));
				importeTotal += importeSOJ.doubleValue();


				//////////////////////////////////
				// EXPEDIENTES EJG rgg 22-03-2005

				param_in_facturacion = new Object[3];
				param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
				param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
				param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); 		 // USUMODIFICACION

				resultado = new String[3];
				resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_EJG (?,?,?,?,?,?)}", 3, param_in_facturacion);
				if (!resultado[1].equalsIgnoreCase("0")) {
					LOGGER.error("Error en PL = "+(String)resultado[2]);
					throw new Exception ("Ha ocurrido un error al ejecutar la facturación de Expedientes de Justicia Gratuita: "+(String)resultado[2]);
				} 

				importeEJG = new Double(resultado[0].replaceAll(",","."));
				importeTotal += importeEJG.doubleValue();
				
				if(prevision){
					//////////////////////////////////////
					/// CREAMOS EL INFORME
					//ArrayList filtrosInforme = getFiltrosInforme(itemFac, institucion);
					//File fichero = getFicheroGenerado(institucion,  SigaConstants.I_INFORMEFACTSJCS,null, filtrosInforme);
					//itemFac.setNombrefisico(fichero.getPath());
					
					//TODO Esta funcionalidad llamará al módulo de comunicaciones cuando esté desarrollado
					
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}


				//////////////////////////////////
				// ACTUALIZO LOS TOTALES
				itemFac.setImporteejg(new BigDecimal(importeEJG));
				itemFac.setImporteguardia(new BigDecimal(importeGuardia));
				itemFac.setImporteoficio(new BigDecimal(importeOficio));
				itemFac.setImportesoj(new BigDecimal(importeSOJ));
				itemFac.setImportetotal(new BigDecimal(importeTotal));
				try{
					fcsFacturacionjgMapper.updateByPrimaryKey(itemFac);
				}catch(Exception ex) {
					LOGGER.error("Ha ocurrido un error al almacenar los importes resultado de la facturacion");
					throw new Exception("Ha ocurrido un error al almacenar los importes resultado de la facturacion",ex);
				}	

			// Exportacion de datos a EXCEL: Se ha comentado este metodo por que no se quiere utilizar
			//UtilidadesFacturacionSJCS.exportarDatosFacturacion(new Integer(idInstitucion), new Integer(idFacturacion), this.usrbean);			
	
		} catch (Exception e) {
			try {

			    LOGGER.error("Error al ejecutar facturacion SJCS.",e);
			    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			} catch (Exception ee) {
			    LOGGER.error("Error al cambiar de estado facturacion SJCS por error.",ee);
			}
		    throw e;
		} 		
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
	 * @author CSD
	 * @param functionDefinition
	 *            string that defines the function
	 * @param inParameters
	 *            input parameters
	 * @param outParameters
	 *            number of output parameters
	 * @return error code, '0' if ok
	 * @throws NamingException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClsExceptions
	 *             type Exception
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

		} catch (SQLTimeoutException ex) {
			return null;
		} catch (SQLException ex) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			con.close();
			con = null;
		}
	}
}
