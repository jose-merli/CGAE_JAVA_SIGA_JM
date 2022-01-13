package org.itcgae.siga.fac.services.impl;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmInforme;
import org.itcgae.siga.db.entities.AdmInformeExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmInformeMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionProgramadaPySService;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class FacturacionProgramadaPySServiceImpl implements IFacturacionProgramadaPySService {

	private static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR_ENVIO = "messages.facturacion.confirmacion.errorEnvio";
	private static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF = "messages.facturacion.confirmacion.errorPdf";
	private static final String TRASPASO_FACTURAS_WS_ACTIVO = "TRASPASO_FACTURAS_WS_ACTIVO";
	private static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR = "messages.facturacion.confirmacion.error";
	private static final String PROC_PAGOS_BANCO = "{call PKG_SIGA_CARGOS.PRESENTACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String PROC_GENERACION_FACTURACION = "{call PKG_SIGA_FACTURACION.GENERACIONFACTURACION(?,?,?,?,?,?,?,?)}";
	private static final String PROC_CONFIRMACION_FACTURACION = "{call PKG_SIGA_FACTURACION.CONFIRMACIONFACTURACION(?,?,?,?,?,?,?,?)}";
	private static final String COD_OK = "0";
	private static final String COD_FACTURACION_CONFIRMACION_ERROR_PDF = "-208";
	private static final String COD_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO = "-205";
	private static final String MSG_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO = "messages.facturacion.confirmar.contadorRepetido";
	private static final String MSG_FACTURACION_CONFIRMACION_ERROR_PDF = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
	private static final String MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR = "facturacion.confirmarFacturacion.mensaje.generacionDisquetesERROR";
	private static final String FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION = "facturacion.directorioFisicoLogProgramacion";
	private static final String LOG_XLS = ".log.xls";
	private static final String LOG_FAC_CONFIRMACION_PREFIX = "LOG_FAC_CONFIRMACION_";
	private static final String TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA = "No se ha podido facturar nada. Compruebe la configuracion y el periodo indicado";
	private static final String TIPO_ADM_INFORME_PREV = "PREV";
	private static final Short DEFAULT_INSTITUCION = 0;
	private static final String PROP_FACTURACION_DIRECTORIO_FISICO_PREVISIONES_JAVA = "facturacion.directorioFisicoPrevisionesJava";
	private static final String PROP_FACTURACION_DIRECTORIO_PREVISIONES_JAVA = "facturacion.directorioPrevisionesJava";
	private static final String LOG_FAC_SUFFIX = LOG_XLS;
	private static final String LOG_FAC_PREFIX = "LOG_FAC";
	private static final String FACTURACION_NUEVA_PREVISION_FACTURACION_MENSAJE_GENERACION_FICHERO_ERROR = "facturacion.nuevaPrevisionFacturacion.mensaje.generacionFicheroERROR";
	private static final String PROP_SIGA_JTA_TIMEOUT_PESADA = "siga.jta.timeout.pesada";
	private static final Integer DEFAULT_SIGA_JTA_TIMEOUT_PESADA = 2400;
	private static final int CHUNK_SIZE = 10;
	private static final String PROP_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION = "facturacion.programacionAutomatica.maxMinutosEnEjecucion";
	private static final Double DEFAULT_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION = 120.0 / (24.0 * 60.0);
	private static final Integer USUARIO_AUTO = 0;
	private static final String DEFAULT_LENGUAJE = "1";
	private static final String[] CODIGOS_ERROR_FORMATO = {"-201", "-202", "-203", "-204"};

	@Autowired
	private FacFacturacionprogramadaExtendsMapper facProgMapper;

	@Autowired
	private CenInstitucionMapper instMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private GenParametrosMapper genParametrosMapper;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private WSCommons wsCommons;
	
	@Autowired
	private AdmInformeMapper adInformeMapper;
	
	@Autowired
	private EcomColaMapper ecomColaMapper;
	
	@Autowired
	private EcomColaParametrosMapper ecomColaParametrosMapper;
	

	private Map<Short, CenInstitucion> mInstituciones;
	
	private Logger LOGGER = Logger.getLogger(FacturacionProgramadaPySServiceImpl.class);

	private Function<Boolean,String> boolTo10 = v->v?"1":"0";
	private Function<String,Boolean> s10ToBool = v->v.equals("1")?true:false;
	private Function<Boolean,String> boolToSN = v->v?"S":"N";

	@Override
	public void ejecutaProcesoFacturacionPyS() {
		/* la clase de referencia en SIGA Clásico es  com.siga.servlets.SIGASvlProcesoFacturacion */
		Double tiempoMaximoEjecucion = getMaxMinutosEnEjecucion();
		List<FacFacturacionprogramada> lFac = facProgMapper.getListaNFacturacionesProgramadasProcesar(CHUNK_SIZE,
				tiempoMaximoEjecucion);
		
		List<FacFacturacionprogramada> lFacConfirmar = facProgMapper.getListaNConfirmarFacturacionesProgramadas(CHUNK_SIZE);

		// estos "for" son temporales, hay que "mezclar" la ejecución de distintos estados de la facturación
		// para que el funcionamiento sea similar al descrito en el DF.
		for (FacFacturacionprogramada fac : lFac) {
			tratarFacturacion(fac);
		}
		for(FacFacturacionprogramada fac:lFacConfirmar) {
			tratarConfirmacion(fac);
		}
		
		/* lo mismo con 
		 * 	generarPDFsYenviarFacturasProgramacion (reutiliza generarPdfEnvioProgramacionFactura)
		 *  generarEnviosFacturasPendientes
		 *  comprobacionTraspasoFacturas
		 * */
		
	}

	private void tratarConfirmacion(FacFacturacionprogramada fac) {
		LOGGER.info("Confirmar facturación programada: " + fac.getIdinstitucion() + " " + fac.getIdseriefacturacion()  + " " + fac.getIdprogramacion());
		
		Path pLog = getPathLogConfirmacion(fac); 
		
		boolean archivar=false;
		boolean generarPagosBanco=true;
		boolean soloGenerarFactura=false;
		boolean esFacturacionRapida=false;
		TransactionStatus transactionStatus = getNewLongTransaction();
		try {
			confirmarProgramacionFactura(transactionStatus,fac,pLog,archivar,generarPagosBanco, soloGenerarFactura, esFacturacionRapida,true);
		} catch(Exception e) {
			rollBack(transactionStatus);
		}
		
	}

	private void rollBack(TransactionStatus transactionStatus) {
		if(!transactionStatus.isCompleted()) {
			transactionManager.rollback(transactionStatus);
		}
	}
	

	private void commit(TransactionStatus transactionStatus) {
		if(transactionStatus!=null&&!transactionStatus.isCompleted()) {
			transactionManager.commit(transactionStatus);
		}
	}

	private Path getPathLogConfirmacion(FacFacturacionprogramada fac) {
		String nombreFichero = getNombreFicheroLogConfirmacion(fac);
		String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
		return 	Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(nombreFichero);
	}
	
	private String getNombreFicheroLogConfirmacion(FacFacturacionprogramada fac) {
		return LOG_FAC_CONFIRMACION_PREFIX + fac.getIdseriefacturacion() +"_"+ fac.getIdprogramacion() +LOG_XLS;
	}

	
	private void confirmarProgramacionFactura(TransactionStatus transactionStatus, FacFacturacionprogramada fac, Path pLog, boolean archivar,
			boolean generarPagosBanco, boolean soloGenerarFactura, boolean esFacturacionRapida, boolean realizarEnvio) {
		TransactionStatus tx=null;
		
		if(transactionStatus!=null) {
			tx = transactionStatus;
		}
		
		if(pLog==null) {
			pLog = getPathLogConfirmacion(fac);
		}
		
		fac.setArchivarfact(boolTo10.apply(archivar));
		fac.setFechaconfirmacion(new Date());
		
		if(soloGenerarFactura) {
			fac.setIdestadoconfirmacion(FacEstadosFacturacion.CONFIRM_FINALIZADA.getId());
		} else {
			try {
				llamadaProcConfirmacionFacturacion(fac, transactionStatus);
				if(generarPagosBanco) {
					tratarPagosBanco(fac);
				}
				if(!esFacturacionRapida) {
					String nameFile = generarInformesConfirmacion(fac);
				}
			} catch(Exception e) {
				tratarExcepcionesConfirmarFacturacion(fac, transactionStatus, e);
			}
    		encolarTraspasoFacturas(fac);
		}
		
		facProgMapper.updateByPrimaryKey(fac);
		
		commit(tx);
		//////////// FIN TRANSACCION ////////////////
		
		LOGGER.info("CONFIRMAR Y PRESENTAR OK ");
			
		LOGGER.info("Entra a generar y enviar");
		boolean isGenerarPdf = s10ToBool.apply(fac.getGenerapdf())  && !esFacturacionRapida;
		boolean isGenerarEnvio = s10ToBool.apply(fac.getEnvio()) && realizarEnvio;
		
		if(isGenerarPdf){
			generarPdfEnvioProgramacionFactura(fac, isGenerarEnvio, tx);
		}
			
		
	}
	
	private void generarPdfEnvioProgramacionFactura(FacFacturacionprogramada fac, boolean isGenerarEnvio,
			TransactionStatus tx) {
		fac.setIdestadopdf(FacEstadosFacturacion.PDF_PROCESANDO.getId());
		facProgMapper.updateByPrimaryKey(fac);
		int errorAlmacenar = generaryEnviarProgramacionFactura(fac);
		if(errorAlmacenar==0) {
			fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADA.getId());
			if(isGenerarEnvio) {
				fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADA.getId());
			}
			facProgMapper.updateByPrimaryKey(fac);
			LOGGER.info("OK TODO, CAMBIO DE ESTADO PDF");
		} else {
			tratarErrorAlmacenarFactura(errorAlmacenar, fac,isGenerarEnvio );
		}
		
	}

	private String tratarErrorAlmacenarFactura(int errorAlmacenar, FacFacturacionprogramada fac, boolean isGenerarEnvio) {
		String logError = getNombreFicheroLogConfirmacion(fac);
		String msjAviso;
		LOGGER.error("ERROR AL ALMACENAR FACTURA, RETORNO="+errorAlmacenar);
		switch (errorAlmacenar) {
		case 1:// ERROR EN GENERAR PDF
			msjAviso = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
			fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADAERRORES.getId());
			fac.setLogerror(logError);
			if(isGenerarEnvio) {
				fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
			}
			break;
		case 2:
			msjAviso = MESSAGES_FACTURACION_CONFIRMACION_ERROR_ENVIO;
			fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADA.getId());
			if(isGenerarEnvio) {
				fac.setLogerror(logError);
				fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
			}
			break;
		default:
			msjAviso = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
			fac.setLogerror(logError);
			fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADAERRORES.getId());
			if(isGenerarEnvio) {
				fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
			}
			break;
		}
		facProgMapper.updateByPrimaryKey(fac);
		return msjAviso;
	}

	private int generaryEnviarProgramacionFactura(FacFacturacionprogramada fac) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void encolarTraspasoFacturas(FacFacturacionprogramada fac) throws BusinessException
    {
		FacEstadosFacturacion estado;
		if(isServicioTraspasoFacturasActivo(fac.getIdinstitucion())&&
				isSerieFacturacionActiva(fac.getIdinstitucion(), fac.getIdseriefacturacion(), fac.getIdprogramacion())) {
			informarTraspasoEcomCola( fac);
			estado = FacEstadosFacturacion.TRASPASO_PROGRAMADA;
		} else {
			estado = FacEstadosFacturacion.TRASPASO_NOAPLICA;
		}
		
		fac.setIdestadotraspaso(estado.getId());
		fac.setFechamodificacion(new Date());
		facProgMapper.updateByPrimaryKey(fac);
    }
	
	
	private void informarTraspasoEcomCola(FacFacturacionprogramada fac) {
		EcomCola ecomCola = new EcomCola();
		ecomCola.setIdinstitucion(fac.getIdinstitucion());
		ecomCola.setIdoperacion(SigaConstants.ECOM_OPERACION.TRASPASAR_FACTURAS_CREARCLIENTE_NAVISION.getId());
		
		ecomColaMapper.insert(ecomCola);
				
		HashMap<String,String> map = new HashMap<>();
		
		map.put("IDINSTITUCION", fac.getIdinstitucion().toString());
		map.put("IDSERIEFACTURACION", fac.getIdseriefacturacion().toString());
		map.put("IDPROGRAMACION", fac.getIdprogramacion().toString());
		
		insertarColaParametros(ecomCola, map);		
	}
	
	
	private void insertarColaParametros(EcomCola ecomCola, HashMap<String,String> map) {
		try {
			map.forEach((k,v)->{
				EcomColaParametros param = new EcomColaParametros();
				param.setIdecomcola(ecomCola.getIdecomcola());
				param.setClave(k);
				param.setValor(v);
				int insertado = ecomColaParametrosMapper.insert(param);
				if(insertado!=1) { 
					throw new RuntimeException();
				}
			});
		} catch (Exception e) {
			throw new BusinessException("Error al insertar los parámetros de la cola.");
		}
		
	}

	private boolean  isServicioTraspasoFacturasActivo(Short idInstitucion) {
		Boolean activo = getParametro(TRASPASO_FACTURAS_WS_ACTIVO,s10ToBool,false);
		return activo;
	}
	
    private boolean isSerieFacturacionActiva(Short idInstitucion, Long idSerieFacturacion, Long idProgramacion)
    {
    	boolean bResultado = false;
    	
    	try
    	{
    		bResultado = facProgMapper.isSerieFacturacionActiva(idInstitucion, idSerieFacturacion, idProgramacion);
    	}
    	catch (Exception e)
    	{
    		LOGGER.error("@@@ Error al tratar de recuperar si la Serie de Facturación " + idSerieFacturacion + " está activa." + e);
    	}
    	
    	return bResultado;
    }

	private void tratarExcepcionesConfirmarFacturacion(FacFacturacionprogramada fac, TransactionStatus transactionStatus, Exception e) {
		rollBack(transactionStatus);// TODO Auto-generated method stub
		LOGGER.error("ERROR AL CONFIRMAR Y PRESENTAR: " + e);
//		(SIGALogging) log.error("CONFIRMACION","N/A","N/A","Error en proceso de confirmaci�n: " + e.getMessage());
		String nombreFichero = getNombreFicheroLogConfirmacion(fac);
		fac.setIdestadoconfirmacion(FacEstadosFacturacion.ERROR_CONFIRMACION.getId());
		fac.setFechaconfirmacion(null);
		fac.setLogerror(nombreFichero);
		facProgMapper.updateByPrimaryKey(fac);
		LOGGER.error("CAMBIA ESTADO A FINALIZADA ERRORES.");
		String msgErr = getProperty(MESSAGES_FACTURACION_CONFIRMACION_ERROR);
		throw new BusinessException(msgErr);
	}

	private String generarInformesConfirmacion(FacFacturacionprogramada fac) {
		// TODO hay que ver si procede generar estos informes y en su caso migrarlos 
		return null;
	}

	private void tratarPagosBanco(FacFacturacionprogramada fac) {
		String[] resultado = null;
		Object[] param_in = generaParametrosPagoBanco(fac);

		try {
			resultado = wsCommons.callPLProcedureFacturacionPyS(PROC_PAGOS_BANCO, 3, param_in);
		} catch (Exception e) {
			throw new BusinessException("Error en la llamada a " + PROC_PAGOS_BANCO ,e);
		}
		
		String codRetorno = resultado[1];
		if(!codRetorno.equals(COD_OK)) {
			String strError = getMensaje("", fac.getIdinstitucion())+ resultado[2];
			throw new BusinessException(strError);
		}

	}

	private String[] llamadaProcConfirmacionFacturacion(FacFacturacionprogramada fac, TransactionStatus transactionPrincipal) {
		String[] resultado = null;
		Object[] param_in = generaParametrosConfirmacion(fac);
				
		try {
			resultado = wsCommons.callPLProcedureFacturacionPyS(
					PROC_CONFIRMACION_FACTURACION, 2, param_in);
		} catch (Exception e) {
			tratarExcepcionEnLlamadaConfirmacion(fac,e);
		}

		tratarResultadoProcConfirmacionFacturacion(fac,resultado, transactionPrincipal);

		return resultado;
	}

	private Object[] generaParametrosConfirmacion(FacFacturacionprogramada fac) {
		Object[] params = new Object[6];
		params[0] = fac.getIdinstitucion();
		params[1] = fac.getIdseriefacturacion();
		params[2] = fac.getIdprogramacion();
		params[3] = USUARIO_AUTO;
		return params;
	}
	
	private Object[] generaParametrosPagoBanco(FacFacturacionprogramada fac) {
		Object[] params = new Object[11];
		params[0] = fac.getIdinstitucion();
		params[1] = fac.getIdseriefacturacion();
		params[2] = fac.getIdprogramacion();
		params[3] = "";
		params[4] = "";
		params[5] = "";
		params[6] = "";
		params[7] = "";
		params[8] = generaRutaFicheroPago(fac);
		params[9] = USUARIO_AUTO;
		params[10] = getLenguajeInstitucion(fac.getIdinstitucion());
		return params;
	}

	private String generaRutaFicheroPago(FacFacturacionprogramada fac) {
		String ruta = getProperty("facturacion.directorioBancosOracle");
		Path pRuta = Paths.get(ruta).resolve(fac.getIdinstitucion().toString());
		return pRuta.toString();
	}

	private void tratarResultadoProcConfirmacionFacturacion(FacFacturacionprogramada fac, String[] resultado,
			TransactionStatus transactionPrincipal) {
		String codretorno = resultado[0];
		
		if (codretorno.equals(COD_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO)){
			throw new BusinessException (MSG_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO);
		}
		
		if (codretorno.equals(COD_FACTURACION_CONFIRMACION_ERROR_PDF)){
			throw new BusinessException(MSG_FACTURACION_CONFIRMACION_ERROR_PDF);	
		}	
		
		if (!codretorno.equals(COD_OK)){
			String mensaje = getMensaje(MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR, fac.getIdinstitucion());
			throw new BusinessException ( mensaje + resultado[1]);
		}
		
	}

	private void tratarExcepcionEnLlamadaConfirmacion(FacFacturacionprogramada fac, Exception e) {
		// TODO Auto-generated method stub
		String msgErr = getMensaje(MESSAGES_FACTURACION_CONFIRMACION_ERROR,fac.getIdinstitucion());
		throw new BusinessException(msgErr);
	}

	private void initInstituciones() {
		CenInstitucionExample example = new CenInstitucionExample();
		example.createCriteria().andFechaenproduccionIsNotNull();
		List<CenInstitucion> lInst = instMapper.selectByExample(example);
		mInstituciones = lInst.stream().collect(Collectors.toMap(i -> i.getIdinstitucion(), i -> i));
	}
	
	public void tratarFacturacionRapida(FacFacturacionprogramada fac /* habría que parametrizar el usuario que realiza la fact. rápida*/) {
		
	}

	private void tratarFacturacion(FacFacturacionprogramada fac) {
		marcaEjecutandoGeneracion(fac);
		generarFacturacion(fac);

	}

	private void generarFacturacion(FacFacturacionprogramada fac) {
		String resultado[] = null;
		TransactionStatus transactionStatus = getNewLongTransaction();
		try {
			resultado = llamadaProcGenerarFacturacion(fac, transactionStatus);
		} catch (Exception e) {
			rollBack(transactionStatus);
		}
		
		finalizaTransaccion(transactionStatus);
	}

	private void finalizaTransaccion(TransactionStatus transactionStatus) {
		if(!transactionStatus.isCompleted()) {
			if (transactionStatus.isRollbackOnly()) {
				rollBack(transactionStatus);
			} else {
				commit(transactionStatus);
			}
		}
	}


	private TransactionStatus getNewLongTransaction() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setTimeout(getTimeoutLargo());
		def.setName("transGenFac");
		return transactionManager.getTransaction(def);
	}

	private String getLenguajeInstitucion(Short idinstitucion) {
		String idLenguaje = DEFAULT_LENGUAJE;
		if (mInstituciones == null) {
			initInstituciones();
		}
		if (mInstituciones.containsKey(idinstitucion)) {
			idLenguaje = mInstituciones.get(idinstitucion).getIdlenguaje();
		}
		return idLenguaje;
	}

	private String[] llamadaProcGenerarFacturacion(FacFacturacionprogramada fac, TransactionStatus transactionPrincipal)
			throws Exception {
		String[] resultado = null;
		Object[] param_in = generaParametrosGeneracion(fac);
				
		try {
			resultado = wsCommons.callPLProcedureFacturacionPyS(
					PROC_GENERACION_FACTURACION, 2, param_in);

		} catch (Exception e) {
			tratarExcepcionEnLlamadaGeneracion(fac,e);
		}

		tratarResultadoProcGenerarFacturacion(fac,resultado, transactionPrincipal);

		return resultado;
	}
	
	
	private void logResultadoError(FacFacturacionprogramada fac) {
		LOGGER.error("### Fin GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), finalizada con errores");		
	}
	
	private String getMensaje(String clave, Short idInstitucion) {
		String mensaje=null;
		return mensaje;
	}

	private void tratarResultadoProcGenerarFacturacion(FacFacturacionprogramada fac, String[] resultado, TransactionStatus transactionPrincipal) {
		String codretorno = resultado[0];
		if (Arrays.asList(CODIGOS_ERROR_FORMATO).contains(codretorno)) {
			throw new BusinessException(resultado[1]);
		
    	} else if (!codretorno.equals(COD_OK)) {				
			logResultadoError(fac);
			String msgExc = msgErr(fac,codretorno);
			throw new BusinessException(msgExc);
			
		} else {
			LOGGER.info("### Fin GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), finalizada correctamente");
			
			/** ACTUALIZAMOS ESTADO A GENERADA **/
			actualizarAGenerada(fac);	
			commit(transactionPrincipal);

			
			/****** INICIAMOS LA GENERACION DEL INFORME *******/
			try {
				LOGGER.info("### Inicio datosInforme GENERACION");
				String nombreFichero = "GENERACION_" + fac.getIdseriefacturacion() + "_" + fac.getIdprogramacion();    
				String nameFile = generarInformesGeneracion(fac);
				// Si la previsón está vacía
				if (nameFile == null || nameFile.length() == 0) {
					LOGGER.info("### Inicio creación fichero log GENERACION sin datos");
					controlarEstadoErrorGeneracion(transactionPrincipal, fac, nombreFichero, FacEstadosFacturacion.GENERADA, null);
					LOGGER.info("### Fin creación fichero log GENERACION sin datos");

				} else {
					LOGGER.info("### GENERACION finalizada correctamente con datos ");
					actualizarNombreFichero(fac,nameFile);
				}
        	} catch (Exception e) {
        		LOGGER.error("### Excepcion " + e.getMessage());	
        		String msgExc = msgErr(fac, e.getMessage());
        		throw new BusinessException(msgExc);
			}					
		}
		
	}

	private void controlarEstadoErrorGeneracion(TransactionStatus transactionPrincipal, FacFacturacionprogramada fac,
			String nombreFichero, FacEstadosFacturacion estadoFin, String mensaje) {
		fac.setIdestadoconfirmacion(estadoFin.getId());
		fac.setLogerror(LOG_FAC_PREFIX+ nombreFichero + LOG_FAC_SUFFIX);
		LOGGER.info("### GESTION ERROR GENERACION  ####");
		LOGGER.info("### CAMBIANDO A ESTADO: "+estadoFin);
		try {
			facProgMapper.updateByPrimaryKey(fac);
			logErrorFacturacion(fac);
		} catch(Exception e) {
			throw new BusinessException("### Error al actualizar el nombre del fichero de la GENERACION.",e);
		}
		
	}

	private void logErrorFacturacion(FacFacturacionprogramada fac) throws Exception {
		String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
		Path pLog = Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(fac.getLogerror());
		Files.deleteIfExists(pLog);
		try (PrintWriter log = new PrintWriter(pLog.toFile())){
			log.println(TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA);
		} catch(Exception e) {
			throw new BusinessException("Error al crear el fichero de log:"+pLog,e);
		}			
	}

	private void actualizarNombreFichero(FacFacturacionprogramada fac, String namefile) {
		LOGGER.info("### GENERACION finalizada correctamente con datos ");
		fac.setNombrefichero(namefile);
		fac.setLogerror(null);
		try {
			facProgMapper.updateByPrimaryKey(fac);
		} catch(Exception e) {
			throw new BusinessException("### Error al actualizar el nombre del fichero de la GENERACION.");
		}

	}

	private String generarInformesGeneracion(FacFacturacionprogramada fac) throws Exception {
		String nameFile=null;
		List<String> nameFiles = new ArrayList<String>();
		Short idInstitucion = fac.getIdinstitucion();
		Long idSerieFacturacion = fac.getIdseriefacturacion();
		Long idprogramacion = fac.getIdprogramacion();
		
		String nombreFichero = "GENERACION_" + idSerieFacturacion + "_" + idprogramacion;
		String dirPrevisiones = getProperty(PROP_FACTURACION_DIRECTORIO_PREVISIONES_JAVA);
		String sRutaFisicaJava = getProperty(PROP_FACTURACION_DIRECTORIO_FISICO_PREVISIONES_JAVA);
		String sRutaJava = Paths.get(sRutaFisicaJava, dirPrevisiones, idInstitucion.toString()).toString();
		
		List<AdmInforme> lInformes = getListaInformes();
		for (AdmInforme admInforme : lInformes) {
			nameFiles.add(generarInformeGeneracion(sRutaJava, nombreFichero, admInforme, fac));
		}
		
		if(nameFiles.size()>0) {
			nameFile = nameFiles.get(0);
		} else {
			nameFile = generaFicheroError(sRutaJava,nombreFichero);
		}
		
		return nameFile; 
	}

	private String generaFicheroError(String sRutaJava, String nombreFichero) throws Exception {
		Path pLog = Paths.get(sRutaJava).resolve(nombreFichero+".XLS");
		Files.deleteIfExists(pLog);
		try (PrintWriter log = new PrintWriter(pLog.toFile())){
			log.println(TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA);
		} catch(Exception e) {
			throw new BusinessException("Error al crear el fichero de log:"+pLog,e);
		}
		return pLog.toFile().getName();
	}

	private String generarInformeGeneracion(String sRutaJava, String nombreFichero, AdmInforme admInforme,
			FacFacturacionprogramada fac) {
		// TODO: hay una llamada genérica a InformePersonalizable.generarInformeXLS. ¿procede?
		
//			AdmInformeBean beanInforme = (AdmInformeBean) vInforme.get(dv);
//
//			ArrayList<HashMap<String, String>> filtrosInforme = new ArrayList<HashMap<String, String>>();
//
//			HashMap<String, String> filtro = new HashMap<String, String>();
//			filtro.put(AdmTipoFiltroInformeBean.C_NOMBRECAMPO, "IDIOMA");
//			filtro.put("VALOR", this.usrbean.getLanguageInstitucion().toString());
//			filtrosInforme.add(filtro);
//
//			filtro = new HashMap<String, String>();
//			filtro.put(AdmTipoFiltroInformeBean.C_NOMBRECAMPO, "IDSERIEFACTURACION");
//			filtro.put("VALOR", idSerieFacturacion);
//			filtrosInforme.add(filtro);
//
//			filtro = new HashMap<String, String>();
//			filtro.put(AdmTipoFiltroInformeBean.C_NOMBRECAMPO, "IDPROGRAMACION");
//			filtro.put("VALOR", idProgramacion);
//			filtrosInforme.add(filtro);
//
//			filtro = new HashMap<String, String>();
//			filtro.put(AdmTipoFiltroInformeBean.C_NOMBRECAMPO, "IDINSTITUCION");
//			filtro.put("VALOR", idInstitucion);
//			filtrosInforme.add(filtro);
//
//			beanInforme.setNombreSalida(nombreFichero);
//
//			ClsLogging.writeFileLog("### Inicio generaci�n fichero excel GENERACION", 7);
//
//			ArrayList<File> fichPrev = InformePersonalizable.generarInformeXLS(beanInforme, filtrosInforme, sRutaJava, this.usrbean);
//
//			ClsLogging.writeFileLog("### Fin generaci�n fichero excel GENERACION", 7);
//
//			if (fichPrev != null && fichPrev.size() > 0) {
//				nameFile = fichPrev.get(0).getName();
//			
//			} else{
//				//Generamos un fichero de Error
//				File ficheroGenerado = null;
//				BufferedWriter out;
//				ficheroGenerado = new File (sRutaJava + File.separator + nombreFichero + ".xls");
//				if (ficheroGenerado.exists())
//					ficheroGenerado.delete();
//				ficheroGenerado.createNewFile();
//				out = new BufferedWriter(new FileWriter(ficheroGenerado));
//				out.write("No se ha podido facturar nada. Compruebe la configuracion y el periodo indicado\t");
//				out.close();	
//				
//				nameFile = ficheroGenerado.getName();
//			}
//		}
//	}
//
//} catch (Exception e) {
//	throw e;
//}
//
		return null;
		
	}

	private List<AdmInforme> getListaInformes() {
		AdmInformeExample ex = new AdmInformeExample();
		ex.createCriteria().andIdtipoinformeLike(TIPO_ADM_INFORME_PREV).andIdinstitucionEqualTo(DEFAULT_INSTITUCION);
		return adInformeMapper.selectByExample(ex);
	}

	private void actualizarAGenerada(FacFacturacionprogramada fac) {
		LOGGER.info("### CAMBIANDO A ESTADO GENERADA ");
		TransactionStatus newTransactionStatus = transactionManager.getTransaction(null);
		try {
			fac.setIdestadoconfirmacion(FacEstadosFacturacion.GENERADA.getId());
			fac.setLogerror(null);
			facProgMapper.updateByPrimaryKey(fac);
			commit(newTransactionStatus);
		} catch (Exception e) {
			transactionManager.rollback(newTransactionStatus);
			throw new BusinessException("### Error al actualizar el estado de la GENERACION.");
		}
	}

	private String msgErr(FacFacturacionprogramada fac, String coderror) {
		return getMensaje(FACTURACION_NUEVA_PREVISION_FACTURACION_MENSAJE_GENERACION_FICHERO_ERROR,fac.getIdinstitucion()) +  "(Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:" + coderror + ")" ;
	}

	private void tratarExcepcionEnLlamadaGeneracion(FacFacturacionprogramada fac, Exception e) {
		String txtLog = "### Fin GENERACION - ERROR TIMEOUT (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:"
				+ fac.getIdprogramacion() + "), excepcion " + e.getMessage();
		LOGGER.info(txtLog);
		if (esTimeout(e)) {
			throw new BusinessException(
					"TimedOutException al generar una Facturacion (Serie:" + fac.getIdseriefacturacion()
							+ "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:" + e.getMessage() + ")");

		} else {
			throw new BusinessException("facturacion.nuevaPrevisionFacturacion.mensaje.procesoPlSQLERROR" + "(Serie:"
					+ fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:"
					+ e.getMessage() + ")");
		}

	}

	private boolean esTimeout(Exception e) {
		return e.getMessage().indexOf("TimedOutException") != -1 || e.getMessage().indexOf("timed out") != -1;
	}

	private Object[] generaParametrosGeneracion(FacFacturacionprogramada fac) {
		Object[] params = new Object[6];
		params[0] = fac.getIdinstitucion();
		params[1] = fac.getIdseriefacturacion();
		params[2] = fac.getIdprogramacion();
		params[3] = getLenguajeInstitucion(fac.getIdinstitucion());
		params[4] = ""; // IdPeticion
		params[5] = USUARIO_AUTO;
		return params;
	}

	private int getTimeoutLargo() {
		Integer time = getProperty(PROP_SIGA_JTA_TIMEOUT_PESADA, Integer::valueOf, DEFAULT_SIGA_JTA_TIMEOUT_PESADA);
		return time;
	}

	private void marcaEjecutandoGeneracion(FacFacturacionprogramada fac) {
		fac.setIdestadoconfirmacion(FacEstadosFacturacion.EJECUTANDO_GENERACION.getId());
		fac.setUsumodificacion(USUARIO_AUTO);
		fac.setFechamodificacion(new Date());
		facProgMapper.updateByPrimaryKey(fac);
	}

	private Double getMaxMinutosEnEjecucion() {
		Double minutos = getProperty(PROP_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION, Double::valueOf, DEFAULT_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION);
		minutos = minutos / (24.0 * 60.0);
		return minutos;
	}
	
	private String getProperty(final String key) {
		return getProperty(key, null,null);
	}
	
	private String getProperty(final String key, final String defaultValue) {
		return getProperty(key, null,defaultValue);
	}
	
	private <T> T getProperty(final String key, Function<String, T> convert, T defValue) {
		T value=null;
		String sValue=null;
		GenPropertiesExample pEx = new GenPropertiesExample();
		pEx.createCriteria().andParametroEqualTo(key);

		List<GenProperties> lParam = genPropertiesMapper.selectByExample(pEx);
		if (lParam.size() > 0) {
			sValue = lParam.get(0).getValor();
		}
		
		if(convert!=null) {
			value = convert.apply(sValue); 
		}
		
		return value;
	}
	
	
	private String getParametro(final String key) {
		return getParametro(key, null,null);
	}
	
	private String getParametro(final String key, final String defaultValue) {
		return getParametro(key, null,defaultValue);
	}
	
	private <T> T getParametro(final String key, Function<String, T> convert, T defValue) {
		T value=null;
		String sValue=null;
		GenParametrosExample pEx = new GenParametrosExample();
		pEx.createCriteria().andParametroEqualTo(key);

		List<GenParametros> lParam = genParametrosMapper.selectByExample(pEx);
		if (lParam.size() > 0) {
			sValue = lParam.get(0).getValor();
		}
		
		if(convert!=null) {
			value = convert.apply(sValue); 
		}
		
		return value;
	}
	
	
	



}
