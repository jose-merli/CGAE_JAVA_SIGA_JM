package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacEstadoConfirmacionFact;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaExtendsDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGALogging;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Service
public class ProGenerarEnviosFacturasPendientes extends ProcesoFacPyS {

    private final Logger LOGGER = Logger.getLogger(ProGenerarEnviosFacturasPendientes.class);

    @Override
    protected void execute(String idInstitucion) {

        try {

            LOGGER.info("GENERAR ENVIOS DE FACTURAS POR INSTITUCION: " + idInstitucion);

            // Obtencion de la propiedad que contiene el tiempo de espera que se les da a las facturaciones en ejcucion no generadas por alguna anomalia
            Double tiempoMaximoEjecucionBloqueada = getMaxMinutosEnEjecucion();

            // Obtencion de las facturaciones programadas y pendientes con fecha de prevista confirmacion pasada a ahora
            List<FacFacturacionprogramadaExtendsDTO> vDatos = facProgMapper.getFacturacionesProGenerarEnviosFacturasPendientes(Short.valueOf(idInstitucion), tiempoMaximoEjecucionBloqueada);

            for (FacFacturacionprogramada factBean : vDatos) {

                // PROCESO PARA CADA FACTURACION PROGRAMADA

                LOGGER.info("ENVIAR FACTURACION PROGRAMADA: " + idInstitucion + " " + factBean.getIdseriefacturacion() + " " + factBean.getIdprogramacion());

                // fichero de log
                Integer logLevel = null;

                GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
                genPropertiesKey.setFichero(SigaConstants.FICHERO_SIGA);
                genPropertiesKey.setParametro(SigaConstants.PARAMETRO_LOG_COLALETRADOS_LEVEL);

                GenProperties genProperties = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey);

                if (genProperties != null && !UtilidadesString.esCadenaVacia(genProperties.getValor())) {
                    logLevel = Integer.valueOf(genProperties.getValor());
                }

                Path plog = getPathLogConfirmacion(factBean);
                SIGALogging log = new SIGALogging(plog.toString(), logLevel);
                TransactionStatus tx = null;

                try {

                    FacFacturacionprogramada facAactualizar = new FacFacturacionprogramada();
                    facAactualizar.setIdinstitucion(factBean.getIdinstitucion());
                    facAactualizar.setIdprogramacion(factBean.getIdprogramacion());
                    facAactualizar.setIdseriefacturacion(factBean.getIdseriefacturacion());
                    facAactualizar.setFechaconfirmacion(new Date());
                    facAactualizar.setIdestadoconfirmacion(FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.getId());

                    tx = facturacionHelper.getNewLongTransaction(getTimeoutLargo());
                    generarPdfEnvioProgramacionFactura(factBean, log, factBean.getIdseriefacturacion(), factBean.getIdprogramacion(), facAactualizar, true, tx);

                } catch (Exception e) {
                    LOGGER.error("@@@ Error al confirmar facturas (Proceso automatico) Programacion:", e);
                } finally {
                    if (tx != null) {
                        finalizaTransaccion(tx);
                    }
                }

            }

        } catch (Exception e) {
            // Error general (No hacemos nada, para que continue con la siguiente institucion
            LOGGER.error("@@@ Error general al confirmar facturas (Proceso automatico) INSTITUCION: " + idInstitucion, e);
        }
    }
}
