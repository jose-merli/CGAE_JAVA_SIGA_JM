package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaExtendsDTO;
import org.itcgae.siga.DTO.fac.FacFicherosDescargaBean;
import org.itcgae.siga.DTO.fac.FacturaFacturacionProgramadaDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SIGALogging;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmInforme;
import org.itcgae.siga.db.entities.AdmInformeExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.FacFacturacionprogramadaExample;
import org.itcgae.siga.db.entities.FacPlantillafacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.AdmInformeMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacPlantillafacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionProgramadaPySService;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
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
    private static final String FACTURACION_DIRECTORIO_FISICO_TEMPORAL_FACTURAS_JAVA = "facturacion.directorioFisicoTemporalFacturasJava";
    private static final String FACTURACION__DIRECTORIO_TEMPORAL_FACTURAS_JAVA = "facturacion.directorioTemporalFacturasJava";
    private static final String FACTURACION_DIRECTORIO_FISICO_PLANTILLA_FACTURA_JAVA = "facturacion.directorioFisicoPlantillaFacturaJava";
    private static final String FACTURACION_DIRECTORIO_PLANTILLA_FACTURA_JAVA = "facturacion.directorioPlantillaFacturaJava";

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

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private FacPlantillafacturacionExtendsMapper facPlantillafacturacionExtendsMapper;

    @Autowired
    private FacturacionHelper facturacionHelper;

    @Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;

    @Autowired
    private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;


    private Map<Short, CenInstitucion> mInstituciones;

    private Logger LOGGER = Logger.getLogger(FacturacionProgramadaPySServiceImpl.class);

    private Function<Boolean, String> boolTo10 = v -> v ? "1" : "0";
    private Function<String, Boolean> s10ToBool = v -> v.equals("1") ? true : false;
    private Function<Boolean, String> boolToSN = v -> v ? "S" : "N";

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
        for (FacFacturacionprogramada fac : lFacConfirmar) {
            tratarConfirmacion(fac);
        }

        /* lo mismo con
         * 	generarPDFsYenviarFacturasProgramacion (reutiliza generarPdfEnvioProgramacionFactura)
         *  generarEnviosFacturasPendientes
         *  comprobacionTraspasoFacturas
         * */

        //TODO HAY QUE HACER TODO LO ANTERIOR POR CADA INSTITUCION

        List<CenInstitucion> listaInstituciones = obtenerInstitucionesAlta();

        for (CenInstitucion institucion : listaInstituciones) {

            generarPDFsYenviarFacturasProgramacion(institucion.getIdinstitucion(), tiempoMaximoEjecucion);

        }

    }

    private void generarPDFsYenviarFacturasProgramacion(Short idInstitucion, Double tiempoMaximoEjecucion) {
        LOGGER.info("GENERAR PDF DE FACTURAS POR INSTITUCION:: " + idInstitucion);

        try {
            // Obtencion de las facturaciones programadas y pendientes con fecha de prevista confirmacion pasada a ahora
            List<FacFacturacionprogramadaExtendsDTO> facFacturacionprogramadaExtendsDTOList = facProgMapper.getFacturacionesProgramadasYPendientes(idInstitucion, tiempoMaximoEjecucion);

            if (null != facFacturacionprogramadaExtendsDTOList && !facFacturacionprogramadaExtendsDTOList.isEmpty()) {

                Integer logLevel = null;

                GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
                genPropertiesKey.setFichero(SigaConstants.FICHERO_SIGA);
                genPropertiesKey.setParametro(SigaConstants.PARAMETRO_LOG_COLALETRADOS_LEVEL);

                GenProperties genProperties = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey);

                if (genProperties != null && !UtilidadesString.esCadenaVacia(genProperties.getValor())) {
                    logLevel = Integer.valueOf(genProperties.getValor());
                }

                // PROCESO PARA CADA FACTURACION PROGRAMADA
                for (FacFacturacionprogramadaExtendsDTO fac : facFacturacionprogramadaExtendsDTOList) {

                    LOGGER.info("GENERAR PDFs Y ENVIAR FACTURACION PROGRAMADA: " + idInstitucion + " " + fac.getIdseriefacturacion() + " " + fac.getIdprogramacion());

                    // ficheros de log
                    SIGALogging log = new SIGALogging(getPathLogConfirmacion(fac).toString(), logLevel);

                    TransactionStatus transactionStatus = getNewLongTransaction();
                    fac.setFechamodificacion(new Date());
                    fac.setIdestadoconfirmacion(FacEstadosFacturacion.CONFIRM_FINALIZADA.getId());

                    generarPdfEnvioProgramacionFactura(fac, true, transactionStatus, log);

                }

            }

        } catch (Exception e) {
            // Error general (No hacemos nada, para que continue con la siguiente institucion
            LOGGER.error("Error general al confirmar facturas (Proceso automatico) INSTITUCION: " + idInstitucion, e);
        }

    }

    private List<CenInstitucion> obtenerInstitucionesAlta() {

        CenInstitucionExample cenInstitucionExample = new CenInstitucionExample();
        cenInstitucionExample.setDistinct(true);
        cenInstitucionExample.createCriteria().andFechaenproduccionIsNotNull();

        return instMapper.selectByExample(cenInstitucionExample);
    }

    private void tratarConfirmacion(FacFacturacionprogramada fac) {
        LOGGER.info("Confirmar facturación programada: " + fac.getIdinstitucion() + " " + fac.getIdseriefacturacion() + " " + fac.getIdprogramacion());

        Path pLog = getPathLogConfirmacion(fac);

        boolean archivar = false;
        boolean generarPagosBanco = true;
        boolean soloGenerarFactura = false;
        boolean esFacturacionRapida = false;
        TransactionStatus transactionStatus = getNewLongTransaction();
        try {
            confirmarProgramacionFactura(transactionStatus, fac, pLog, archivar, generarPagosBanco, soloGenerarFactura, esFacturacionRapida, true);
        } catch (Exception e) {
            rollBack(transactionStatus);
        }

    }

    private void rollBack(TransactionStatus transactionStatus) {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
        }
    }

    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.commit(transactionStatus);
        }
    }

    private Path getPathLogConfirmacion(FacFacturacionprogramada fac) {
        String nombreFichero = getNombreFicheroLogConfirmacion(fac);
        String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
        return Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(nombreFichero);
    }

    private String getNombreFicheroLogConfirmacion(FacFacturacionprogramada fac) {
        return LOG_FAC_CONFIRMACION_PREFIX + fac.getIdseriefacturacion() + "_" + fac.getIdprogramacion() + LOG_XLS;
    }

    private void confirmarProgramacionFactura(TransactionStatus transactionStatus, FacFacturacionprogramada fac, Path pLog, boolean archivar,
                                              boolean generarPagosBanco, boolean soloGenerarFactura, boolean esFacturacionRapida, boolean realizarEnvio) {
        TransactionStatus tx = null;

        if (transactionStatus != null) {
            tx = transactionStatus;
        }

        if (pLog == null) {
            pLog = getPathLogConfirmacion(fac);
        }

        fac.setArchivarfact(boolTo10.apply(archivar));
        fac.setFechaconfirmacion(new Date());

        if (soloGenerarFactura) {
            fac.setIdestadoconfirmacion(FacEstadosFacturacion.CONFIRM_FINALIZADA.getId());
        } else {
            try {
                llamadaProcConfirmacionFacturacion(fac, transactionStatus);
                if (generarPagosBanco) {
                    tratarPagosBanco(fac);
                }
                if (!esFacturacionRapida) {
                    String nameFile = generarInformesConfirmacion(fac);
                }
            } catch (Exception e) {
                tratarExcepcionesConfirmarFacturacion(fac, transactionStatus, e);
            }
            encolarTraspasoFacturas(fac);
        }

        facProgMapper.updateByPrimaryKey(fac);

        commit(tx);
        //////////// FIN TRANSACCION ////////////////

        LOGGER.info("CONFIRMAR Y PRESENTAR OK ");

        LOGGER.info("Entra a generar y enviar");
        boolean isGenerarPdf = s10ToBool.apply(fac.getGenerapdf()) && !esFacturacionRapida;
        boolean isGenerarEnvio = s10ToBool.apply(fac.getEnvio()) && realizarEnvio;

        if (isGenerarPdf) {
            generarPdfEnvioProgramacionFactura(fac, isGenerarEnvio, tx);
        }


    }

    private void generarPdfEnvioProgramacionFactura(FacFacturacionprogramada fac, boolean isGenerarEnvio, TransactionStatus tx) {
        generarPdfEnvioProgramacionFactura(fac, isGenerarEnvio, tx, null);
    }

    private void generarPdfEnvioProgramacionFactura(FacFacturacionprogramada fac, boolean isGenerarEnvio, TransactionStatus tx, SIGALogging log) {

        String nombreFichero = null;

        try {

            nombreFichero = getNombreFicheroLogConfirmacion(fac);

            fac.setIdestadopdf(FacEstadosFacturacion.PDF_PROCESANDO.getId());
            facProgMapper.updateByPrimaryKey(fac);

            commit(tx);

            //Hay que pasar el log
            int errorAlmacenar = generaryEnviarProgramacionFactura(fac, isGenerarEnvio, log);

            if (errorAlmacenar == 0) {
                fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADA.getId());
                if (isGenerarEnvio) {
                    fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADA.getId());
                }
                facProgMapper.updateByPrimaryKey(fac);
                LOGGER.info("OK TODO, CAMBIO DE ESTADOS");
            } else {
                tratarErrorAlmacenarFactura(errorAlmacenar, fac, isGenerarEnvio);
            }

            commit(tx);

        } catch (Exception e) {
            LOGGER.error("ERROR GENERAL EN TRY GENERAR/ENVIAR.");
            rollBack(tx);

            // ESCRIBO EN EL LOG mensaje general ??
            log.writeLogFactura("PDF/ENVIO", "N/A", "N/A", "Error general en el proceso de generacion o envio de facturas PDF: " + e);

            fac.setLogerror(nombreFichero);
            fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADAERRORES.getId());
            fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADAERRORES.getId());

            if (isGenerarEnvio) {
                fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
            }

            facProgMapper.updateByPrimaryKey(fac);
            commit(tx);

            throw e;
        }
    }

    private String tratarErrorAlmacenarFactura(int errorAlmacenar, FacFacturacionprogramada fac, boolean isGenerarEnvio) {

        String logError = getNombreFicheroLogConfirmacion(fac);
        String msjAviso;
        LOGGER.error("ERROR AL ALMACENAR FACTURA, RETORNO=" + errorAlmacenar);

        switch (errorAlmacenar) {
            case 1:// ERROR EN GENERAR PDF
                LOGGER.error("ERROR AL ALMACENAR FACTURA. RETORNO = " + errorAlmacenar);
                msjAviso = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
                fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADAERRORES.getId());
                fac.setLogerror(logError);
                if (isGenerarEnvio) {
                    fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
                }
                break;
            case 2:
                LOGGER.error("ERROR AL ALMACENAR FACTURA. RETORNO = " + errorAlmacenar);
                msjAviso = MESSAGES_FACTURACION_CONFIRMACION_ERROR_ENVIO;
                fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADA.getId());
                if (isGenerarEnvio) {
                    fac.setLogerror(logError);
                    fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
                }
                break;
            default:
                msjAviso = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
                fac.setLogerror(logError);
                fac.setIdestadopdf(FacEstadosFacturacion.PDF_FINALIZADAERRORES.getId());
                if (isGenerarEnvio) {
                    fac.setIdestadoenvio(FacEstadosFacturacion.ENVIO_FINALIZADAERRORES.getId());
                }
                LOGGER.error("ERROR GENERAL GENERAR/ENVIAR FACTURA. CAMBIO DE ESTADOS");
                break;
        }

        facProgMapper.updateByPrimaryKey(fac);
        return msjAviso;
    }

    private int generaryEnviarProgramacionFactura(FacFacturacionprogramada fac, boolean isGenerarEnvio, SIGALogging log) {

        int salida = 0;
        boolean existeAlgunErrorPdf = false;
        boolean existeAlgunErrorEnvio = false;

        try {

            // Obtengo las facturas a almacenar
            List<FacturaFacturacionProgramadaDTO> facturaFacturacionProgramadaDTOList = facFacturaExtendsMapper.getFacturasDeFacturacionProgramada(fac.getIdinstitucion().toString(), fac.getIdseriefacturacion().toString(), fac.getIdprogramacion().toString());

            if (facturaFacturacionProgramadaDTOList == null || facturaFacturacionProgramadaDTOList.size() < 1) {
                throw new Exception("No existen facturas generadas");
            }

            LOGGER.info("ALMACENAR >> " + fac.getIdinstitucion() + " " + fac.getIdseriefacturacion() + " " + fac.getIdprogramacion());

            // Obtengo la plantilla a utilizar
            List<FacPlantillafacturacion> facPlantillafacturacionList = facPlantillafacturacionExtendsMapper.getPlantillaSerieFacturacion(fac.getIdinstitucion().toString(), fac.getIdseriefacturacion().toString());
            String sPlantilla = facPlantillafacturacionList.get(0).getPlantillapdf();

            // Obtencion de la ruta donde se almacenan temporalmente los ficheros formato FOP
            String rutaTemporal = getProperty(FACTURACION_DIRECTORIO_FISICO_TEMPORAL_FACTURAS_JAVA) + getProperty(FACTURACION__DIRECTORIO_TEMPORAL_FACTURAS_JAVA) + File.separator + fac.getIdinstitucion();
            SIGAHelper.mkdirs(rutaTemporal);

            // Obtencion de la ruta de donde se obtiene la plantilla adecuada
            String rutaPlantilla = getProperty(FACTURACION_DIRECTORIO_FISICO_PLANTILLA_FACTURA_JAVA) + getProperty(FACTURACION_DIRECTORIO_PLANTILLA_FACTURA_JAVA) + File.separator + fac.getIdinstitucion() + File.separator + sPlantilla;
            File rutaModelo = new File(rutaPlantilla);

            //Comprobamos que exista la ruta y sino la creamos
            if (!rutaModelo.exists()) {
                throw new Exception("La ruta de acceso a la plantilla de la factura no existe");
            }

            LOGGER.info("ALMACENAR >> TERMINA DE OBTENER PLANTILLAS Y DATOS GENERALES");

            // recorro todas las facturas para ir creando lo sinformes pertinentes

            LOGGER.info("ALMACENAR >> NUMERO DE FACTURAS: " + facturaFacturacionProgramadaDTOList.size());

            String idFactura = "";

            /** CR7 - Se saca fuera ya que siempre se usa la misma plantilla para tdas las facturas **/
            //SE SELECCIONA LA PLANTILLA MAIL
            FacFacturacionprogramadaExample facFacturacionprogramadaExample = new FacFacturacionprogramadaExample();
            facFacturacionprogramadaExample.createCriteria()
                    .andIdinstitucionEqualTo(fac.getIdinstitucion())
                    .andIdseriefacturacionEqualTo(fac.getIdseriefacturacion())
                    .andIdprogramacionEqualTo(fac.getIdprogramacion());

            List<FacFacturacionprogramada> facFacturacionprogramadaList = facProgMapper.selectByExample(facFacturacionprogramadaExample);

            Integer plantillaMail = null;

            if (facFacturacionprogramadaList != null && facFacturacionprogramadaList.size() > 0 && facFacturacionprogramadaList.get(0).getIdtipoplantillamail() != null) {
                plantillaMail = facFacturacionprogramadaList.get(0).getIdtipoplantillamail();
            }

            //Aunque nos ha fallado esta factura es posible que la siguiente, no.
            //POR LO TANTO no COMPROBAMOS QUE HAYA SIDO CORRECTO EL CAMBIO ANTERIOR
            ArrayList<FacFicherosDescargaBean> listaFicherosPDFDescarga = new ArrayList<>();
            ArrayList<File> listaFicheros = new ArrayList<>();
            FacFicherosDescargaBean facFicherosDescargaBean = null;
            for (FacturaFacturacionProgramadaDTO f : facturaFacturacionProgramadaDTOList) {

                try {

                    idFactura = f.getIdFactura();
                    String idPersona = f.getIdPersona().toString();
                    String numFactura = f.getNumeroFactura();

                    LOGGER.info("ALMACENAR " + idFactura + " >> FACTURA: " + idFactura);

                    // TRY del proceso de generacion de la factura en PDF
                    File filePDF = null;

                    try {

                        // PROCESO DE CREAR EL PDF
                        // RGG 15/02/2007 CAMBIOS PARA INFORME MASTER REPOR
                        filePDF = facturacionHelper.generarPdfFacturaFirmada(idFactura, f.getIdInstitucion().toString());

                        if (filePDF == null) {
                            throw new Exception("Error al generar la factura. Fichero devuelto es nulo.");
                        } else {

                            // Obtenemos el nombre de la persona de la factura
                            String nombreColegiado = "";
                            if (idPersona != null && !"".equalsIgnoreCase(idPersona)) {
                                nombreColegiado = obtenerNombreApellidos(idPersona);
                                if (nombreColegiado != null && !"".equalsIgnoreCase(nombreColegiado)) {
                                    nombreColegiado = eliminarAcentosYCaracteresEspeciales(nombreColegiado) + "-";
                                } else {
                                    nombreColegiado = "";
                                }
                            }


                            FacSeriefacturacionExample facSeriefacturacionExample = new FacSeriefacturacionExample();
                            facSeriefacturacionExample.createCriteria()
                                    .andIdseriefacturacionEqualTo(fac.getIdseriefacturacion())
                                    .andIdinstitucionEqualTo(f.getIdInstitucion());

                            List<FacSeriefacturacion> facSeriefacturacionList = facSeriefacturacionExtendsMapper.selectByExample(facSeriefacturacionExample);

                            if (facSeriefacturacionList != null && facSeriefacturacionList.size() > 0) {
                                FacSeriefacturacion beanSerieFacturacion = facSeriefacturacionList.get(0);
                                facFicherosDescargaBean.setFormatoDescarga(beanSerieFacturacion.getIdNombreDescargaFac().intValue());
                                facFicherosDescargaBean.setFichero(filePDF);
                                facFicherosDescargaBean.setNombreFacturaFichero(nombreColegiado);
                                listaFicherosPDFDescarga.add(facFicherosDescargaBean);
                            }

                            listaFicheros.add(filePDF);

                        }

                        LOGGER.info("ALMACENAR " + idFactura + " >> FACTURA GENERADA EN PDF");

                    } catch (Exception e) {
                        LOGGER.error("ALMACENAR " + idFactura + " >> ERROR EN PROCESO DE FOP A PDF: " + e.getMessage());

                        // ESCRIBO EN EL LOG
                        log.writeLogFactura("PDF", idPersona, numFactura, "Error en el proceso de generacion de facturas PDF: " + e.getMessage());
                        salida = 1;
                        //Aunque nos ha fallado esta factura es posible que la siguiente, no.
                        //POR LO TANTO no cazamos la excepcion
                        //throw ee;
                        existeAlgunErrorPdf = true;
                    }

                    LOGGER.error("ALMACENAR " + idFactura + " >> VAMOS A VER SI ENVIARMOS: ENVIAR:" + isGenerarEnvio + " CORRECTO:" + (filePDF != null));


                    /***************    ENVIO FACTURAS *****************/
                    if (isGenerarEnvio && filePDF != null) {
//						enviarProgramacionFactura(idPersona, institucion.toString(), idFactura, plantillaMail, numFactura, filePDF, log, salida, existeAlgunErrorEnvio, tx);
                    }

                } catch (Exception e) {
                    throw e;
                }

                LOGGER.info("ALMACENAR " + idFactura + " >> PROCESO DE FACTURA OK ");
            }


        } catch (Exception e) {

        }

        return salida;
    }

//	private void enviarProgramacionFactura(String idPersona, String idInstitucion, String idFactura, Integer plantillaMail, String numeroFactura, File ficheroPdf,
//			/*SIGALogging log,*/ int salida, boolean existeAlgunErrorEnvio, UserTransaction tx) throws Exception {
//
//		UsrBean userbean = this.usrbean;
//
//		try {
//			LOGGER.info("ALMACENAR " + idFactura + " >> PROCESO DE ENVIO", 10);
//
//			//Obtenemos el bean del envio:
//			CenPersonaAdm admPersona = new CenPersonaAdm(userbean);
//			String descripcion = "Envio factura " + numeroFactura + " - " + admPersona.obtenerNombreApellidos(idPersona);
//			Envio envio = new Envio(userbean, descripcion);
//
//			// Bean envio
//			EnvEnviosBean enviosBean = envio.enviosBean;
//
//			// RGG
//			GenParametrosAdm paramAdm = new GenParametrosAdm(userbean);
//			String preferencia = paramAdm.getValor(idInstitucion, "ENV", "TIPO_ENVIO_PREFERENTE", "1");
//			Integer valorPreferencia = Envio.calculaTipoEnvio(preferencia);
//			enviosBean.setIdTipoEnvios(valorPreferencia);
//
//			// Preferencia del tipo de envio si el usuario tiene uno:
//			CenDireccionesAdm direccionAdm = new CenDireccionesAdm(userbean);
//			Hashtable<?, ?> direccion = direccionAdm.getEntradaDireccionEspecifica(idPersona, idInstitucion, preferencia);
//
//			if (direccion == null || direccion.size() == 0) {
//				direccion = direccionAdm.getEntradaDireccionEspecifica(idPersona, idInstitucion, "3");// si no hay direccion preferente mail, buscamos la de correo
//				if (direccion == null || direccion.size() == 0) {
//					direccion = direccionAdm.getEntradaDireccionEspecifica(idPersona, idInstitucion, "2");// si no hay direccion de despacho, buscamos la de despacho
//					if (direccion == null || direccion.size() == 0) {
//						direccion = direccionAdm.getEntradaDireccionEspecifica(idPersona, idInstitucion, "");// si no hay direccion de despacho, buscamos cualquier direcci�n.
//						if (direccion == null || direccion.size() == 0) {
//							LOGGER.info("ALMACENAR " + idFactura + " >> NO TIENE DIRECCION PREFERENTE " + preferencia, 10);
//							throw new Exception("No se ha encontrado direccion de la persona para el tipo de envio preferente: " + preferencia);
//						}
//					}
//				}
//			}
//
//			if (plantillaMail != null) {
//				enviosBean.setIdPlantillaEnvios(plantillaMail);
//				// Creacion documentos
//				Documento documento = new Documento(ficheroPdf, "Factura " + ficheroPdf.getName());
//				Vector<Documento> documentos = new Vector<Documento>(1);
//				documentos.add(documento);
//
//				/*************** INICIO TRANSACCION ***************/
//				if (tx != null)
//					tx.begin();
//
//				// Genera el envio:
//				envio.generarEnvio(idPersona, EnvDestinatariosBean.TIPODESTINATARIO_CENPERSONA, documentos, null, null);
//				if (tx != null)
//					tx.commit();
//				LOGGER.info("ALMACENAR " + idFactura + " >> ENVIO GENERADO OK");
//				/*************** FIN TRANSACCION ***************/
//
//			} else {
//				throw new Exception("No se han encontrado plantillas para el envio de facturas");
//			}
//
//		} catch (Exception eee) {
//			try { // Tratamiento rollback
//				if (tx != null && Status.STATUS_ACTIVE == tx.getStatus()) {
//					tx.rollback();
//				}
//			} catch (Exception e2) {
//			}
//
//			LOGGER.error("ALMACENAR " + idFactura + " >> ERROR EN PROCESO DE ENVIO: " + eee);
//			// ESCRIBO EN EL LOG
////			log.writeLogFactura("ENVIO",idPersona,numeroFactura,"message.facturacion.error.envio.factura"+eee.toString());
//			salida = 2;
//			//Aunque nos ha fallado esta factura es posible que la siguiente, no.
//			//POR LO TANTO no cazamos la excepcion
//			//throw eee;
//			existeAlgunErrorEnvio = true;
//		}
//	}

    private String obtenerNombreApellidos(String idPersona) throws Exception {

        String nombre = "";

        try {

            CenPersona cenPersona = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(idPersona));

            if (cenPersona != null) {

                nombre = cenPersona.getNombre();

                if (cenPersona.getApellidos1() != null && !cenPersona.getApellidos1().equals("#NA")) {
                    nombre += " " + cenPersona.getApellidos1();
                }

                if (cenPersona.getApellidos2() != null && !cenPersona.getApellidos2().equals("#NA")) {
                    nombre += " " + cenPersona.getApellidos2();
                }

            }

        } catch (Exception e) {
            throw new Exception("Error al obtener el nombre y apellidos", e);
        }

        return nombre;
    }

    /**
     * Función que elimina acentos y caracteres especiales de una cadena de texto.
     *
     * @param input
     * @return cadena de texto limpia de acentos y caracteres especiales.
     */
    private String eliminarAcentosYCaracteresEspeciales(String input) {

        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ!\"#$%&'()*+-,./:;<=>?@[\\]^_`{|}~";

        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC                                ";
        String output = input;

        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }// for i

        return output;
    }

    public void encolarTraspasoFacturas(FacFacturacionprogramada fac) throws BusinessException {
        FacEstadosFacturacion estado;
        if (isServicioTraspasoFacturasActivo(fac.getIdinstitucion()) &&
                isSerieFacturacionActiva(fac.getIdinstitucion(), fac.getIdseriefacturacion(), fac.getIdprogramacion())) {
            informarTraspasoEcomCola(fac);
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

        HashMap<String, String> map = new HashMap<>();

        map.put("IDINSTITUCION", fac.getIdinstitucion().toString());
        map.put("IDSERIEFACTURACION", fac.getIdseriefacturacion().toString());
        map.put("IDPROGRAMACION", fac.getIdprogramacion().toString());

        insertarColaParametros(ecomCola, map);
    }

    private void insertarColaParametros(EcomCola ecomCola, HashMap<String, String> map) {
        try {
            map.forEach((k, v) -> {
                EcomColaParametros param = new EcomColaParametros();
                param.setIdecomcola(ecomCola.getIdecomcola());
                param.setClave(k);
                param.setValor(v);
                int insertado = ecomColaParametrosMapper.insert(param);
                if (insertado != 1) {
                    throw new RuntimeException();
                }
            });
        } catch (Exception e) {
            throw new BusinessException("Error al insertar los parámetros de la cola.");
        }

    }

    private boolean isServicioTraspasoFacturasActivo(Short idInstitucion) {
        Boolean activo = getParametro(TRASPASO_FACTURAS_WS_ACTIVO, s10ToBool, false);
        return activo;
    }

    private boolean isSerieFacturacionActiva(Short idInstitucion, Long idSerieFacturacion, Long idProgramacion) {
        boolean bResultado = false;

        try {
            bResultado = facProgMapper.isSerieFacturacionActiva(idInstitucion, idSerieFacturacion, idProgramacion);
        } catch (Exception e) {
            LOGGER.error("@@@ Error al tratar de recuperar si la Serie de Facturación " + idSerieFacturacion + " está activa." + e);
        }

        return bResultado;
    }

    private void tratarExcepcionesConfirmarFacturacion(FacFacturacionprogramada fac, TransactionStatus transactionStatus, Exception e) {
        rollBack(transactionStatus);// TODO Auto-generated method stub
        LOGGER.error("ERROR AL CONFIRMAR Y PRESENTAR: " + e);
//		(SIGALogging) log.error("CONFIRMACION","N/A","N/A","Error en proceso de confirmacion: " + e.getMessage());
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
            throw new BusinessException("Error en la llamada a " + PROC_PAGOS_BANCO, e);
        }

        String codRetorno = resultado[1];
        if (!codRetorno.equals(COD_OK)) {
            String strError = getMensaje("", fac.getIdinstitucion()) + resultado[2];
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
            tratarExcepcionEnLlamadaConfirmacion(fac, e);
        }

        tratarResultadoProcConfirmacionFacturacion(fac, resultado, transactionPrincipal);

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
        params[10] = Integer.parseInt(getLenguajeInstitucion(fac.getIdinstitucion()));
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

        if (codretorno.equals(COD_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO)) {
            throw new BusinessException(MSG_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO);
        }

        if (codretorno.equals(COD_FACTURACION_CONFIRMACION_ERROR_PDF)) {
            throw new BusinessException(MSG_FACTURACION_CONFIRMACION_ERROR_PDF);
        }

        if (!codretorno.equals(COD_OK)) {
            String mensaje = getMensaje(MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR, fac.getIdinstitucion());
            throw new BusinessException(mensaje + resultado[1]);
        }

    }

    private void tratarExcepcionEnLlamadaConfirmacion(FacFacturacionprogramada fac, Exception e) {
        // TODO Auto-generated method stub
        String msgErr = getMensaje(MESSAGES_FACTURACION_CONFIRMACION_ERROR, fac.getIdinstitucion());
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
        if (!transactionStatus.isCompleted()) {
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
            tratarExcepcionEnLlamadaGeneracion(fac, e);
        }

        tratarResultadoProcGenerarFacturacion(fac, resultado, transactionPrincipal);

        return resultado;
    }

    private void logResultadoError(FacFacturacionprogramada fac) {
        LOGGER.error("### Fin GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), finalizada con errores");
    }

    private String getMensaje(String clave, Short idInstitucion) {
        String mensaje = null;
        return mensaje;
    }

    private void tratarResultadoProcGenerarFacturacion(FacFacturacionprogramada fac, String[] resultado, TransactionStatus transactionPrincipal) {
        String codretorno = resultado[0];
        if (Arrays.asList(CODIGOS_ERROR_FORMATO).contains(codretorno)) {
            throw new BusinessException(resultado[1]);

        } else if (!codretorno.equals(COD_OK)) {
            logResultadoError(fac);
            String msgExc = msgErr(fac, codretorno);
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
                    actualizarNombreFichero(fac, nameFile);
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
        fac.setLogerror(LOG_FAC_PREFIX + nombreFichero + LOG_FAC_SUFFIX);
        LOGGER.info("### GESTION ERROR GENERACION  ####");
        LOGGER.info("### CAMBIANDO A ESTADO: " + estadoFin);
        try {
            facProgMapper.updateByPrimaryKey(fac);
            logErrorFacturacion(fac);
        } catch (Exception e) {
            throw new BusinessException("### Error al actualizar el nombre del fichero de la GENERACION.", e);
        }

    }

    private void logErrorFacturacion(FacFacturacionprogramada fac) throws Exception {
        String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
        Path pLog = Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(fac.getLogerror());
        Files.deleteIfExists(pLog);
        try (PrintWriter log = new PrintWriter(pLog.toFile())) {
            log.println(TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA);
        } catch (Exception e) {
            throw new BusinessException("Error al crear el fichero de log:" + pLog, e);
        }
    }

    private void actualizarNombreFichero(FacFacturacionprogramada fac, String namefile) {
        LOGGER.info("### GENERACION finalizada correctamente con datos ");
        fac.setNombrefichero(namefile);
        fac.setLogerror(null);
        try {
            facProgMapper.updateByPrimaryKey(fac);
        } catch (Exception e) {
            throw new BusinessException("### Error al actualizar el nombre del fichero de la GENERACION.");
        }

    }

    private String generarInformesGeneracion(FacFacturacionprogramada fac) throws Exception {
        String nameFile = null;
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

        if (nameFiles.size() > 0) {
            nameFile = nameFiles.get(0);
        } else {
            nameFile = generaFicheroError(sRutaJava, nombreFichero);
        }

        return nameFile;
    }

    private String generaFicheroError(String sRutaJava, String nombreFichero) throws Exception {
        Path pLog = Paths.get(sRutaJava).resolve(nombreFichero + ".XLS");
        Files.deleteIfExists(pLog);
        try (PrintWriter log = new PrintWriter(pLog.toFile())) {
            log.println(TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA);
        } catch (Exception e) {
            throw new BusinessException("Error al crear el fichero de log:" + pLog, e);
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
        return getMensaje(FACTURACION_NUEVA_PREVISION_FACTURACION_MENSAJE_GENERACION_FICHERO_ERROR, fac.getIdinstitucion()) + "(Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:" + coderror + ")";
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
        params[3] = Integer.parseInt(getLenguajeInstitucion(fac.getIdinstitucion()));
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
        return getProperty(key, null, null);
    }

    private String getProperty(final String key, final String defaultValue) {
        return getProperty(key, null, defaultValue);
    }

    private <T> T getProperty(final String key, Function<String, T> convert, T defValue) {
        T value = null;
        String sValue = null;
        GenPropertiesExample pEx = new GenPropertiesExample();
        pEx.createCriteria().andParametroEqualTo(key);

        List<GenProperties> lParam = genPropertiesMapper.selectByExample(pEx);
        if (lParam.size() > 0) {
            sValue = lParam.get(0).getValor();
        }

        if (convert != null) {
            value = convert.apply(sValue);
        }

        return value;
    }

    private String getParametro(final String key) {
        return getParametro(key, null, null);
    }

    private String getParametro(final String key, final String defaultValue) {
        return getParametro(key, null, defaultValue);
    }

    private <T> T getParametro(final String key, Function<String, T> convert, T defValue) {
        T value = null;
        String sValue = null;
        GenParametrosExample pEx = new GenParametrosExample();
        pEx.createCriteria().andParametroEqualTo(key);

        List<GenParametros> lParam = genParametrosMapper.selectByExample(pEx);
        if (lParam.size() > 0) {
            sValue = lParam.get(0).getValor();
        }

        if (convert != null) {
            value = convert.apply(sValue);
        }

        return value;
    }

}
