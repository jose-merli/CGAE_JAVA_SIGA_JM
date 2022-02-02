package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SIGALogging;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmInforme;
import org.itcgae.siga.db.entities.AdmInformeExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.springframework.transaction.TransactionStatus;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ProTratarConfirmacion extends ProcesoFacPyS {

    private final Logger LOGGER = Logger.getLogger(ProTratarConfirmacion.class);

    private final Function<Boolean, String> boolTo10 = v -> v ? "1" : "0";
    private final Function<String, Boolean> s10ToBool = v -> v.equals("1");

    @Override
    protected void execute(String idInstitucion) {

        try {

            LOGGER.info("CONFIRMAR PROGRAMACIONES FACTURAS INSTITUCION: " + idInstitucion);

            // ficheros de log
            SIGALogging log;

            // obtenciOn de las facturaciones programadas y pendientes con fecha de prevista confirmacion pasada a ahora
            List<FacFacturacionprogramada> facFacturacionprogramadaList = facProgMapper.getListaNConfirmarFacturacionesProgramadas(idInstitucion);

            if (facFacturacionprogramadaList != null && facFacturacionprogramadaList.size() > 0) {

                // PROCESO PARA CADA FACTURACION PROGRAMADA
                FacFacturacionprogramada facFacturacionprogramada = facFacturacionprogramadaList.get(0);

                LOGGER.info("CONFIRMAR FACTURACION PROGRAMADA: " + idInstitucion + " " + facFacturacionprogramada.getIdseriefacturacion() + " " + facFacturacionprogramada.getIdprogramacion());

                // fichero de log
                Integer logLevel = null;

                GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
                genPropertiesKey.setFichero(SigaConstants.FICHERO_SIGA);
                genPropertiesKey.setParametro(SigaConstants.PARAMETRO_LOG_COLALETRADOS_LEVEL);

                GenProperties genProperties = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey);

                if (genProperties != null && !UtilidadesString.esCadenaVacia(genProperties.getValor())) {
                    logLevel = Integer.valueOf(genProperties.getValor());
                }

                Path pLog = getPathLogConfirmacion(facFacturacionprogramada);
                log = new SIGALogging(pLog.toString(), logLevel);

                try {

                    confirmarProgramacionFactura(facFacturacionprogramada, false, log, true, false, 1, false);

                } catch (Exception e) {
                    LOGGER.error("@@@ Error al confirmar facturas (Proceso automatico) Programacion:", e);
                }


            }

        } catch (Exception e) {
            // Error general (No hacemos nada, para que continue con la siguiente institucion
            LOGGER.error("@@@ Error general al confirmar facturas (Proceso automatico) INSTITUCION:" + idInstitucion, e);
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

    private void confirmarProgramacionFactura(FacFacturacionprogramada facFacturacionprogramada, boolean archivarFacturacion, SIGALogging log, boolean generarPagosBanco, boolean soloGenerarFactura, int iTransaccionInterna,
                                              boolean esFacturacionRapida) throws Exception {

        TransactionStatus tx = null;

        if (iTransaccionInterna == 1) {
            tx = getNewLongTransaction();
        } else if (iTransaccionInterna == 2) {
            //TODO POR DEFINIR
        }

        String msjAviso = null;

        try {

            // fichero de log

            if (log == null) {
                Integer logLevel = null;

                GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
                genPropertiesKey.setFichero(SigaConstants.FICHERO_SIGA);
                genPropertiesKey.setParametro(SigaConstants.PARAMETRO_LOG_COLALETRADOS_LEVEL);

                GenProperties genProperties = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey);

                if (genProperties != null && !UtilidadesString.esCadenaVacia(genProperties.getValor())) {
                    logLevel = Integer.valueOf(genProperties.getValor());
                }

                Path pLog = getPathLogConfirmacion(facFacturacionprogramada);
                log = new SIGALogging(pLog.toString(), logLevel);
            }

            Long idSerieFacturacion = facFacturacionprogramada.getIdseriefacturacion();
            Long idProgramacion = facFacturacionprogramada.getIdprogramacion();
            String pathFichero = generaRutaFicheroPago(facFacturacionprogramada);

            // Se confirma la facturacion
            FacFacturacionprogramada facAactualizar = new FacFacturacionprogramada();
            facAactualizar.setIdinstitucion(facFacturacionprogramada.getIdinstitucion());
            facAactualizar.setIdprogramacion(idProgramacion);
            facAactualizar.setIdseriefacturacion(idSerieFacturacion);
            facAactualizar.setFechaconfirmacion(new Date());
            facAactualizar.setArchivarfact(boolTo10.apply(archivarFacturacion));

            if (!soloGenerarFactura) {

                try {

                    // Lo primero que se hace es poner la facturacion en estado EJECUTANDO CONFIRMACION
                    facAactualizar.setIdestadoconfirmacion(FacEstadosFacturacion.EJECUTANDO_CONFIRMACION.getId());
                    facProgMapper.updateByPrimaryKeySelective(facAactualizar);
                    commit(tx);

                    LOGGER.info("### Procesando CONFIRMACION: " + idProgramacion);

                    //Se genera numero de factura definitivo
                    Object[] param_in_confirmacion = generaParametrosConfirmacion(facFacturacionprogramada);

                    String resultadoConfirmar[] = wsCommons.callPLProcedureFacturacionPyS(PROC_CONFIRMACION_FACTURACION, 2, param_in_confirmacion);

                    tratarResultadoProcConfirmacionFacturacion(resultadoConfirmar);

                    // RGG 05/05/2009 Cambio (solo se generan los pagos por banco cuando se indica por parametro)
                    if (generarPagosBanco) {

                        // Se envian a banco para su cobro
                        Object[] param_in_banco = generaParametrosPagoBanco(facFacturacionprogramada, pathFichero);
                        String resultado[] = wsCommons.callPLProcedureFacturacionPyS(PROC_CARGOS_PRESENTACION, 3, param_in_banco);

                        tratarResultadoProcCargosPresentacion(resultado);

                    }

                    // JPT 17/11/2016: Se realiza el informe de confirmacion
                    if (!esFacturacionRapida) {

                        /**************  CREAMOS EL INFORME DE CONFIRMACION DE FACTURACION    ****************/
                        AdmInformeExample admInformeExample = new AdmInformeExample();
                        admInformeExample.createCriteria().andIdtipoinformeEqualTo("FACT").andIdinstitucionEqualTo(Short.valueOf("0"));

                        LOGGER.info("### Inicio datosInforme CONFIRMACION");

                        List<AdmInforme> admInformeList = adInformeMapper.selectByExample(admInformeExample);

                        if (admInformeList != null && admInformeList.size() > 0) {

                            ArrayList<HashMap<String, String>> filtrosInforme = new ArrayList<>();
                            HashMap<String, String> filtro = new HashMap<>();
                            filtro.put("NOMBRECAMPO", "IDIOMA");
                            filtro.put("VALOR", getLenguajeInstitucion(facFacturacionprogramada.getIdinstitucion()));
                            filtrosInforme.add(filtro);

                            filtro = new HashMap<>();
                            filtro.put("NOMBRECAMPO", "IDSERIEFACTURACION");
                            filtro.put("VALOR", idSerieFacturacion.toString());
                            filtrosInforme.add(filtro);

                            filtro = new HashMap<>();
                            filtro.put("NOMBRECAMPO", "IDPROGRAMACION");
                            filtro.put("VALOR", idProgramacion.toString());
                            filtrosInforme.add(filtro);

                            filtro = new HashMap<>();
                            filtro.put("NOMBRECAMPO", "IDINSTITUCION");
                            filtro.put("VALOR", facFacturacionprogramada.getIdinstitucion().toString());
                            filtrosInforme.add(filtro);

                            String ruta = Paths.get(getProperty(FACTURACION_DIRECTORIO_FISICO_FACTURAS_PDF_JAVA))
                                    .resolve(getProperty(FACTURACION_DIRECTORIO_FACTURA_PDF_JAVA))
                                    .resolve(facFacturacionprogramada.getIdinstitucion().toString())
                                    .resolve(idSerieFacturacion.toString()).resolve("_" + idProgramacion).toString();

                            SIGAHelper.mkdirs(ruta);
                            File rutaPDF = new File(ruta);
                            if (!rutaPDF.exists()) {
                                throw new Exception(getRecurso("messages.facturacion.comprueba.noPathFacturas"));
                            } else {
                                if (!rutaPDF.canWrite()) {
                                    throw new Exception(getRecurso("messages.facturacion.comprueba.noPermisosPathFacturas"));
                                }
                            }

                            for (AdmInforme informe : admInformeList) {

                                informe.setNombresalida("CONFIRMACION_" + idSerieFacturacion + "_" + idProgramacion);

                                LOGGER.info("### Inicio generacion fichero excel CONFIRMACION");

                                ArrayList<File> listaFicherosConfirmacion = generarInformeXLS(informe, filtrosInforme, ruta, facFacturacionprogramada.getIdinstitucion().toString());

                                LOGGER.info("### Fin generacion fichero excel CONFIRMACION");

                                // Si no se generan los informes de confirmacion
                                if (listaFicherosConfirmacion == null || listaFicherosConfirmacion.size() == 0) {
                                    LOGGER.error("### Error al generar el informe de la confirmacion. Inicio creacion fichero log CONFIRMACION sin datos");
                                    throw new Exception(getRecurso("message.facturacion.error.fichero.nulo"));
                                }

                                File ficheroXls = listaFicherosConfirmacion.get(0);
                                facAactualizar.setNombrefichero(ficheroXls.getName());

                            }

                        }

                    }

                    facAactualizar.setIdestadoconfirmacion(FacEstadosFacturacion.CONFIRM_FINALIZADA.getId());
                    facAactualizar.setLogerror("");
                    facProgMapper.updateByPrimaryKeySelective(facAactualizar);

                    commit(tx);

                    LOGGER.info("CONFIRMAR Y PRESENTAR OK ");

                } catch (Exception e) {

                    rollBack(tx);

                    String sms = e.getMessage() != null ? e.getMessage() : e.toString();
                    LOGGER.error("ERROR AL CONFIRMAR Y PRESENTAR: " + sms);
                    log.writeLogFactura("CONFIRMACION", "N/A", "N/A", "Error en proceso de confirmacion: " + sms);

                    facAactualizar.setIdestadoconfirmacion(FacEstadosFacturacion.ERROR_CONFIRMACION.getId());
                    facAactualizar.setFechaconfirmacion(null);
                    facAactualizar.setLogerror(getNombreFicheroLogConfirmacion(facFacturacionprogramada));

                    facProgMapper.updateByPrimaryKeySelective(facAactualizar);
                    commit(tx);

                    LOGGER.info("CAMBIA ESTADO A FINALIZADA ERRORES.");
                    throw new Exception("Ha ocurrido un error al confirmar facturación " + sms);
                }

                //INSERTAMOS EN LA COLA LA OPERACION CREARCLIENTE(): (NO HAY PROBLEMA PORQUE SI EL CLIENTE YA EXISTE LO ACTUALIZA, Y HACE UN INTENTO DE TRASPASAR UNICAMENTE LAS FACTURAS QUE TENGA SIN TRASPASAR, QUE ES LO QUE NOS INTERESA).
                facAactualizar.setIdestadotraspaso(TRASPASO_PROGRAMADA);
                Short idInstitucion = facFacturacionprogramada.getIdinstitucion();
                encolarTraspasoFacturas(idInstitucion, idSerieFacturacion, idProgramacion);

            } else {
                facAactualizar.setIdestadoconfirmacion(CONFIRM_FINALIZADA); //Si entramos por aqui es que ya hemos confirmado previamente
            }// FIN IF EJECUTAR CONFIRMACION

            LOGGER.info("ENTRA A GENERAR Y ENVIAR");

            boolean isGenerarPdf = facFacturacionprogramada.getGenerapdf() != null && facFacturacionprogramada.getGenerapdf().trim().equals("1") && !esFacturacionRapida;
            boolean isGenerarEnvio = facFacturacionprogramada.getEnvio() != null && facFacturacionprogramada.getEnvio().trim().equals("1") && (facFacturacionprogramada.getEnvio() == null || facFacturacionprogramada.getEnvio().equalsIgnoreCase("1"));

            if (isGenerarPdf) {
                msjAviso = generarPdfEnvioProgramacionFactura(facFacturacionprogramada, log, idSerieFacturacion, idProgramacion, facAactualizar, isGenerarEnvio, tx);
            }

        } catch (Exception e) {
            rollBack(tx);
            throw new Exception("Ha ocurrido un error al confirmar facturación");
        }

        if (msjAviso != null) {
            throw new Exception(msjAviso);
        }
    }

    private String generaRutaFicheroPago(FacFacturacionprogramada fac) {
        String ruta = getProperty(FACTURACION_DIRECTORIO_BANCOS_ORACLE);
        Path pRuta = Paths.get(ruta).resolve(fac.getIdinstitucion().toString());
        return pRuta.toString();
    }

    private Object[] generaParametrosConfirmacion(FacFacturacionprogramada fac) {
        Object[] params = new Object[4];
        params[0] = fac.getIdinstitucion().toString();
        params[1] = fac.getIdseriefacturacion().toString();
        params[2] = fac.getIdprogramacion().toString();
        params[3] = USUARIO_AUTO;
        return params;
    }

    private void tratarResultadoProcConfirmacionFacturacion(String[] resultado) throws Exception {

        String codretorno = resultado[0];

        if (codretorno.equals(COD_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO)) {
            throw new Exception(getRecurso(MSG_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO));
        }

        if (codretorno.equals(COD_FACTURACION_CONFIRMACION_ERROR_PDF)) {
            throw new Exception(getRecurso(MSG_FACTURACION_CONFIRMACION_ERROR_PDF));
        }

        if (!codretorno.equals(COD_OK)) {
            String mensaje = getRecurso(MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR);
            throw new Exception(mensaje + resultado[1]);
        }

    }

    private Object[] generaParametrosPagoBanco(FacFacturacionprogramada fac, String pathFichero) {
        Object[] params = new Object[11];
        params[0] = fac.getIdinstitucion().toString();
        params[1] = fac.getIdseriefacturacion().toString();
        params[2] = fac.getIdprogramacion().toString();
        params[3] = "";
        params[4] = "";
        params[5] = "";
        params[6] = "";
        params[7] = "";
        params[8] = pathFichero;
        params[9] = USUARIO_AUTO;
        params[10] = getLenguajeInstitucion(fac.getIdinstitucion());
        return params;
    }

    private void tratarResultadoProcCargosPresentacion(String[] resultado) throws Exception {

        String codretorno = resultado[1];

        if (!codretorno.equals(COD_OK)) {
            throw new Exception(getRecurso(MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR) + resultado[2]);
        }
    }

    private void encolarTraspasoFacturas(Short idInstitucion, Long idSerieFacturacion, Long idProgramacion) throws Exception {

        short estado;

        //CONSULTA DE LA SERIE DE FACTURACION (idinstitucion e idseriefacturacion) PARA VER SI HAY QUE TRASPASARLA O NO: FAC_FACTURACION.TRASPASOFACTURAS (1, 0):
        //SI LA TRANSFERENCIA DE FACTURAS ACTIVA ES NAVISION Y ESTA ACTIVA LA SERIE DE FACTURACION:
        if (isServicioTraspasoFacturasActivo(idInstitucion) && isSerieFacturacionActiva(idInstitucion, idSerieFacturacion, idProgramacion)) {

            //INSERTAMOS EN LA COLA LA OPERACION CREARCLIENTE(): (NO HAY PROBLEMA PORQUE SI EL CLIENTE YA EXISTE LO ACTUALIZA, Y HACE UN INTENTO DE TRASPASAR UNICAMENTE LAS FACTURAS QUE TENGA SIN TRASPASAR, QUE ES LO QUE NOS INTERESA).
            HashMap map = new HashMap<String, String>();
            map.put("IDINSTITUCION", idInstitucion);
            map.put("IDSERIEFACTURACION", idSerieFacturacion);
            map.put("IDPROGRAMACION", idProgramacion);

            estado = FacEstadosFacturacion.TRASPASO_PROGRAMADA.getId();

            EcomCola ecomColaCrearCliente = new EcomCola();
            ecomColaCrearCliente.setIdinstitucion(idInstitucion);
            ecomColaCrearCliente.setIdoperacion(SigaConstants.ECOM_OPERACION.TRASPASAR_FACTURAS_CREARCLIENTE_NAVISION.getId());

            insertaColaConParametros(ecomColaCrearCliente, map);

        } else { //EN CASO DE ANADIR NUEVOS METODOS DE TRASPASO, IR ANADIENDO LLAMADAS AQUI.
            estado = FacEstadosFacturacion.TRASPASO_NOAPLICA.getId();
        }

        //CAMBIO DE ESTADO DE TRASPASO DE LA FACTURACION:

        FacFacturacionprogramada fac = new FacFacturacionprogramada();
        fac.setIdinstitucion(idInstitucion);
        fac.setIdprogramacion(idProgramacion);
        fac.setIdseriefacturacion(idSerieFacturacion);
        fac.setFechamodificacion(new Date());
        fac.setIdestadotraspaso(estado);

        facProgMapper.updateByPrimaryKeySelective(fac);
    }

    private boolean isServicioTraspasoFacturasActivo(Short idInstitucion) {

        String valor = "0";

        GenParametros parametro = getParametro(idInstitucion, SigaConstants.MODULO_ECOM, TRASPASO_FACTURAS_WS_ACTIVO);

        if (parametro != null && parametro.getValor() != null) {
            valor = parametro.getValor();
        }

        return s10ToBool.apply(valor);
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


}
