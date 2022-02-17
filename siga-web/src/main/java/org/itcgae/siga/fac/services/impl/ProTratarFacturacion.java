package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaExtendsDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmInforme;
import org.itcgae.siga.db.entities.AdmInformeExample;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ProTratarFacturacion extends ProcesoFacPyS {

    private final Logger LOGGER = Logger.getLogger(ProTratarFacturacion.class);

    @Override
    protected void execute(String idInstitucion) {

        TransactionStatus transactionStatus = null;

        try {

            transactionStatus = facturacionHelper.getNewLongTransaction(getTimeoutLargo());

            // Obtencion de la propiedad que contiene el tiempo de espera que se les da a las facturaciones en ejcucion no generadas por alguna anomalia
            Double tiempoMaximoEjecucion = getMaxMinutosEnEjecucion();

            List<FacFacturacionprogramadaExtendsDTO> facFacturacionprogramadaList = facProgMapper.getFacturacionesProTratarFacturacion(Short.valueOf(idInstitucion), tiempoMaximoEjecucion);

            if (facFacturacionprogramadaList != null && facFacturacionprogramadaList.size() > 0) {

                FacFacturacionprogramada facFacturacionprogramada = facFacturacionprogramadaList.get(0);

                try {

                    // Pasamos a estado ejecutando
                    marcaEjecutandoGeneracion(facFacturacionprogramada);
                    commit(transactionStatus);

                    // Generamos la Facturacion (LA TRANSACCION VA DENTRO DEL METODO)
                    generandoFacturacion(facFacturacionprogramada, idInstitucion);

                    LOGGER.info("### PROCESADO facturacion AUTOMATICA");

                } catch (Exception e) {
                    rollBack(transactionStatus);
                    LOGGER.error("### Error procesando facturacion AUTOMATICA", e);
                }

            }

        } catch (Exception e) {
            LOGGER.error("### Error general al procesar facturas (INSTITUCION:" + idInstitucion + ")", e);

        } finally {
            if (transactionStatus != null) {
                finalizaTransaccion(transactionStatus);
            }
        }
    }

    private void generandoFacturacion(FacFacturacionprogramada fac, String idInstitucion) {

        LOGGER.info("### Inicio generarFicheroPrevisiones institucion: " + idInstitucion);

        TransactionStatus transactionStatus = null;
        String nombreFichero = null;

        String resultado[] = new String[2];
        try {

            transactionStatus = facturacionHelper.getNewLongTransaction(getTimeoutLargo());
            nombreFichero = "GENERACION_" + fac.getIdseriefacturacion() + "_" + fac.getIdprogramacion();

            // Carga los parametros
            Object[] param_in = generaParametrosGeneracion(fac, idInstitucion);

            try {

                LOGGER.info("### Inicio GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + ")");
                resultado = wsCommons.callPLProcedureFacturacionPyS(PROC_GENERACION_FACTURACION, 2, param_in);

            } catch (Exception e) {
                tratarExcepcionEnLlamadaGeneracion(fac, e);
            }

            tratarResultadoProcGenerarFacturacion(idInstitucion, fac, resultado, nombreFichero, transactionStatus);

        } catch (Exception e) {

            LOGGER.error("### ERROR GLOBAL GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), empezamos la gestion del error");
            rollBack(transactionStatus);

            // Le cambio el estado a error
            try {
                String sMensaje;

                if (resultado[0] != null && Arrays.asList(CODIGOS_ERROR_FORMATO).contains(resultado[0])) {
                    sMensaje = resultado[1];
                } else if (e.getMessage().indexOf("TimedOutException") != -1 || e.getMessage().indexOf("timed out") != -1) {
                    sMensaje = "La facturación ha tardado más de la cuenta y ha sido cancelada. Pruebe a facturar en otro momento o limitar las condiciones de facturación.";
                } else {
                    sMensaje = "Ha ocurrido un error no controlado en el proceso de Generación de la Facturación. Puede contactar con su Administrador o con el Centro de Atención: 902 41 11 41 para conocer mas detalles de lo sucedido.";
                }

                controlarEstadoErrorGeneracion(transactionStatus, fac, nombreFichero, FacEstadosFacturacion.ERROR_GENERACION, sMensaje);

            } catch (Exception e2) {
                rollBack(transactionStatus);
            }

        }
    }

    private Object[] generaParametrosGeneracion(FacFacturacionprogramada fac, String idInstitucion) {
        Object[] params = new Object[6];
        params[0] = idInstitucion;
        params[1] = fac.getIdseriefacturacion();
        params[2] = fac.getIdprogramacion();
        params[3] = getLenguajeInstitucion(Short.valueOf(idInstitucion));
        params[4] = ""; // IdPeticion
        params[5] = USUARIO_AUTO.toString();
        return params;
    }

    private void tratarExcepcionEnLlamadaGeneracion(FacFacturacionprogramada fac, Exception e) throws Exception {
        if (esTimeout(e)) {
            LOGGER.error("### Fin GENERACION - ERROR TIMEOUT (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), excepcion " + e.getMessage());
            throw new Exception(
                    "TimedOutException al generar una Facturacion (Serie:" + fac.getIdseriefacturacion()
                            + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:" + e.getMessage() + ")");

        } else {
            LOGGER.error("### Fin GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), excepcion " + e.getMessage());
            throw new Exception("facturacion.nuevaPrevisionFacturacion.mensaje.procesoPlSQLERROR" + "(Serie:"
                    + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:"
                    + e.getMessage() + ")");
        }
    }

    private boolean esTimeout(Exception e) {
        return e.getMessage().indexOf("TimedOutException") != -1 || e.getMessage().indexOf("timed out") != -1;
    }

    private void tratarResultadoProcGenerarFacturacion(String idInstitucion, FacFacturacionprogramada fac, String[] resultado, String nombreFichero, TransactionStatus transactionPrincipal) throws Exception {

        String codretorno = resultado[0];

        if (Arrays.asList(CODIGOS_ERROR_FORMATO).contains(codretorno)) {
            logResultadoError(fac);
            throw new Exception(resultado[1]);

        } else if (!codretorno.equals(COD_OK)) {
            logResultadoError(fac);
            throw new Exception("Error al generar el fichero asociado a la prevision " + "(Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:" + codretorno + ")");

        } else {
            LOGGER.info("### Fin GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), finalizada correctamente");

            /** ACTUALIZAMOS ESTADO A GENERADA **/
            actualizarAGenerada(fac);
            commit(transactionPrincipal);

            /****** INICIAMOS LA GENERACION DEL INFORME *******/
            try {
                LOGGER.info("### Inicio datosInforme GENERACION");
                String nameFile = generarInformeGeneracion(idInstitucion, fac);
                // Si la previsón está vacía
                if (nameFile == null || nameFile.length() == 0) {
                    LOGGER.info("### Inicio creación fichero log GENERACION sin datos");
                    controlarEstadoErrorGeneracion(transactionPrincipal, fac, nombreFichero, FacEstadosFacturacion.GENERADA, null);
                    LOGGER.info("### Fin creación fichero log GENERACION sin datos");

                } else {
                    LOGGER.info("### GENERACION finalizada correctamente con datos ");
                    actualizarNombreFichero(fac, nameFile, transactionPrincipal);
                }
            } catch (Exception e) {
                LOGGER.error("### Excepcion " + e.getMessage());
                throw new Exception("Error al generar el fichero asociado a la prevision " + "(Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "; CodigoError:" + e.getMessage() + ")");
            }
        }

    }

    private void logResultadoError(FacFacturacionprogramada fac) {
        LOGGER.error("### Fin GENERACION (Serie:" + fac.getIdseriefacturacion() + "; IdProgramacion:" + fac.getIdprogramacion() + "), finalizada con errores");
    }

    private void actualizarAGenerada(FacFacturacionprogramada fac) throws Exception {
        LOGGER.info("### CAMBIANDO A ESTADO GENERADA ");
        fac.setIdestadoconfirmacion(FacEstadosFacturacion.GENERADA.getId());
        fac.setLogerror(null);
        if (facProgMapper.updateByPrimaryKeySelective(fac) != 1) {
            throw new Exception("### Error al actualizar el estado de la GENERACION.");
        }
    }

    private String generarInformeGeneracion(String idInstitucion, FacFacturacionprogramada fac) throws Exception {

        String idSerieFacturacion = fac.getIdseriefacturacion().toString();
        String idProgramacion = fac.getIdprogramacion().toString();

        String nameFile = null;
        String dirPrevisiones = getProperty(PROP_FACTURACION_DIRECTORIO_PREVISIONES_JAVA);
        String sRutaFisicaJava = getProperty(PROP_FACTURACION_DIRECTORIO_FISICO_PREVISIONES_JAVA);
        String sRutaJava = Paths.get(sRutaFisicaJava, dirPrevisiones, idInstitucion).toString();
        String nombreFichero = "GENERACION_" + idSerieFacturacion + "_" + idProgramacion;

        try {

            List<AdmInforme> lInformes = getListaInformes();

            if (lInformes != null && !lInformes.isEmpty()) {

                for (AdmInforme admInforme : lInformes) {

                    ArrayList<HashMap<String, String>> filtrosInforme = new ArrayList<>();

                    HashMap<String, String> filtro = new HashMap<>();
                    filtro.put("NOMBRECAMPO", "IDIOMA");
                    filtro.put("VALOR", getLenguajeInstitucion(fac.getIdinstitucion()));
                    filtrosInforme.add(filtro);

                    filtro = new HashMap<>();
                    filtro.put("NOMBRECAMPO", "IDSERIEFACTURACION");
                    filtro.put("VALOR", idSerieFacturacion);
                    filtrosInforme.add(filtro);

                    filtro = new HashMap<>();
                    filtro.put("NOMBRECAMPO", "IDPROGRAMACION");
                    filtro.put("VALOR", idProgramacion);
                    filtrosInforme.add(filtro);

                    filtro = new HashMap<>();
                    filtro.put("NOMBRECAMPO", "IDINSTITUCION");
                    filtro.put("VALOR", idInstitucion);
                    filtrosInforme.add(filtro);

                    admInforme.setNombresalida(nombreFichero);

                    LOGGER.info("### Inicio generacion fichero excel GENERACION");
                    ArrayList<File> fichPrev = generarInformeXLS(admInforme, filtrosInforme, sRutaJava, fac.getIdinstitucion().toString());
                    LOGGER.info("### Fin generacion fichero excel GENERACION");

                    if (fichPrev != null && fichPrev.size() > 0) {
                        nameFile = fichPrev.get(0).getName();
                    } else {
                        //Generamos un fichero de Error
                        File ficheroGenerado;
                        BufferedWriter out;
                        ficheroGenerado = new File(sRutaJava + File.separator + nombreFichero + ".xls");
                        if (ficheroGenerado.exists()) {
                            ficheroGenerado.delete();
                        }
                        ficheroGenerado.createNewFile();
                        out = new BufferedWriter(new FileWriter(ficheroGenerado));
                        out.write("No se ha podido facturar nada. Compruebe la configuracion y el periodo indicado\t");
                        out.close();

                        nameFile = ficheroGenerado.getName();
                    }
                }

            }

        } catch (Exception e) {
            throw e;
        }

        return nameFile;
    }

    private List<AdmInforme> getListaInformes() {
        AdmInformeExample ex = new AdmInformeExample();
        ex.createCriteria().andIdtipoinformeLike(TIPO_ADM_INFORME_PREV).andIdinstitucionEqualTo(DEFAULT_INSTITUCION);
        return adInformeMapper.selectByExample(ex);
    }

    private void controlarEstadoErrorGeneracion(TransactionStatus transactionPrincipal, FacFacturacionprogramada fac,
                                                String nombreFichero, FacEstadosFacturacion estadoFin, String mensaje) throws Exception {
        fac.setIdestadoconfirmacion(estadoFin.getId());
        fac.setLogerror(LOG_FAC_PREFIX + nombreFichero + LOG_FAC_SUFFIX);
        LOGGER.info("### GESTION ERROR GENERACION  ####");
        LOGGER.info("### CAMBIANDO A ESTADO: " + estadoFin);
        try {

            if (facProgMapper.updateByPrimaryKeySelective(fac) == 0) {
                throw new Exception("Error al actualizar el estado de la generacion. finalizada con errores.");
            }

            logErrorFacturacion(fac, mensaje);

            commit(transactionPrincipal);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void logErrorFacturacion(FacFacturacionprogramada fac, String sMensaje) throws Exception {
        String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
        Path pLog = Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(fac.getLogerror());
        Files.deleteIfExists(pLog);
        try (PrintWriter log = new PrintWriter(pLog.toFile())) {

            if (!UtilidadesString.esCadenaVacia(sMensaje)) {
                log.println(sMensaje);
            } else {
                log.println(TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA);
            }
        } catch (Exception e) {
            throw new BusinessException("Error al crear el fichero de log:" + pLog, e);
        }
    }

    private void actualizarNombreFichero(FacFacturacionprogramada fac, String namefile, TransactionStatus transactionStatus) throws Exception {
        LOGGER.info("### GENERACION finalizada correctamente con datos ");
        fac.setNombrefichero(namefile);
        fac.setLogerror(null);

        if (facProgMapper.updateByPrimaryKeySelective(fac) != 1) {
            throw new Exception("### Error al actualizar el nombre del fichero de la GENERACION.");
        }

        commit(transactionStatus);
    }
}
