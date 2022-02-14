package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacEstadoConfirmacionFact;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaExtendsDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGALogging;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.FacFacturacionprogramadaKey;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProComprobacionTraspasoFacturas extends ProcesoFacPyS {

    private final Logger LOGGER = Logger.getLogger(ProComprobacionTraspasoFacturas.class);

    @Override
    protected void execute(String idInstitucion) {

        try {

            LOGGER.info("FACTURAS: " + idInstitucion);

            // Obtencion de la propiedad que contiene el tiempo de espera
            Double tiempoMaximoEjecucionBloqueada = getMaxMinutosEnEjecucion();

            // OBTENCION DE LAS FACTURACIONES CON ESTADO PROCESANDO Y TODAS SUS FACTURAS HAN SIDO TRASPASADAS
            List<FacFacturacionprogramadaExtendsDTO> vDatos = facProgMapper.getFacturacionesProComprobacionTraspasoFacturas(Short.valueOf(idInstitucion), tiempoMaximoEjecucionBloqueada);

            for (FacFacturacionprogramadaExtendsDTO factBean : vDatos) {

                // PROCESO PARA CADA FACTURACION PROGRAMADA

                LOGGER.info("ENVIAR FACTURACION PROGRAMADA: " + idInstitucion + " " + factBean.getIdseriefacturacion() + " " + factBean.getIdprogramacion());

                try {

                    FacFacturacionprogramada hashNew = new FacFacturacionprogramada();
                    hashNew.setIdinstitucion(factBean.getIdinstitucion());
                    hashNew.setIdprogramacion(factBean.getIdprogramacion());
                    hashNew.setIdseriefacturacion(factBean.getIdseriefacturacion());

                    TransactionStatus tx = facturacionHelper.getNewLongTransaction(getTimeoutLargo());

                    cambiarEstadoTraspasoFacturacion(factBean, hashNew, tx);

                } catch (Exception e) {
                    LOGGER.error("@@@ Error al traspasar facturas pendientes (Proceso automatico) Programacion: ", e);
                }

            }

        } catch (Exception e) {
            // Error general (No hacemos nada, para que continue con la siguiente institucion
            LOGGER.error("@@@ Error general al traspasar facturas pendientes (Proceso automatico) INSTITUCION: " + idInstitucion, e);
        }
    }

    private String cambiarEstadoTraspasoFacturacion(FacFacturacionprogramada beanP, FacFacturacionprogramada hashFactura, TransactionStatus tx) throws Exception {

        String msjAviso;

        // fichero de log:
        Path plogTraspaso = getPathLogTraspaso(beanP);
        File ficheroLogTraspaso = new File(plogTraspaso.toString());

        if (ficheroLogTraspaso.exists()) {
            ficheroLogTraspaso.delete(); //Borramos el fichero de traspaso de logs si ya existe previamente.
        }

        Integer logLevel = null;

        GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
        genPropertiesKey.setFichero(SigaConstants.FICHERO_SIGA);
        genPropertiesKey.setParametro(SigaConstants.PARAMETRO_LOG_COLALETRADOS_LEVEL);

        GenProperties genProperties = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey);

        if (genProperties != null && !UtilidadesString.esCadenaVacia(genProperties.getValor())) {
            logLevel = Integer.valueOf(genProperties.getValor());
        }

        SIGALogging log = new SIGALogging(plogTraspaso.toString(), logLevel);

        // fichero de log confirmacion:
        Path plogConfirmacion = getPathLogConfirmacion(beanP);
        SIGALogging logConfirmacion = new SIGALogging(plogConfirmacion.toString(), logLevel);

        try {

            LOGGER.info("TIENE QUE TRASPASAR FACTURA");

            //SELECT QUE COMPRUEBA SI HAY ALGUNA FACTURA QUE HA SIDO TRASPADA CON ERROR:
            FacFacturaExample facFacturaExample = new FacFacturaExample();
            facFacturaExample.createCriteria().andIdinstitucionEqualTo(beanP.getIdinstitucion())
                    .andIdseriefacturacionEqualTo(beanP.getIdseriefacturacion())
                    .andIdprogramacionEqualTo(beanP.getIdprogramacion())
                    .andTraspasadaEqualTo(SigaConstants.ESTADO_TRASPASADA_ERROR);

            List<FacFactura> vDatos = facFacturaExtendsMapper.selectByExample(facFacturaExample);

            if (vDatos != null && vDatos.size() > 0) { //HA HABIDO ALGUNA FACTURA CON ERROR.
                msjAviso = getRecurso("messages.facturacion.confirmacion.errorPdf"); //ME VALE ESTE MISMO MENSAJE DE AVISO.
                hashFactura.setIdestadotraspaso(FacEstadoConfirmacionFact.TRASPASO_FINALIZADAERRORES.getId()); // cambio de estado ENVIO a FINALIZADO CON ERRORES.
                hashFactura.setLogtraspaso(getNombreFicheroLogTraspaso(beanP));
                hashFactura.setLogerror(getNombreFicheroLogConfirmacion(beanP));
                facProgMapper.updateByPrimaryKeySelective(hashFactura);
                logConfirmacion.writeLogFactura("Traspaso", "", "", "Error en el proceso de Traspaso de Facturas");
                LOGGER.info("ERROR GENERAL TRASPASO FACTURA. CAMBIO DE ESTADOS");
            } else {
                msjAviso = getRecurso("messages.facturacion.confirmacion.errorPdf"); //ME VALE ESTE MISMO MENSAJE DE AVISO.
                hashFactura.setIdestadotraspaso(FacEstadoConfirmacionFact.TRASPASO_FINALIZADA.getId()); // cambio de estado TRASPASO a FINALIZADO.
                facProgMapper.updateByPrimaryKeySelective(hashFactura);
                LOGGER.info("OK TODO. CAMBIO DE ESTADOS");
            }

            commit(tx);

            //GENERACION LOG FACTURAS:
            //BUSCAMOS TODAS LAS FACTURAS DE LA FACTURACION:
            facFacturaExample.clear();
            facFacturaExample.createCriteria().andIdinstitucionEqualTo(beanP.getIdinstitucion())
                    .andIdseriefacturacionEqualTo(beanP.getIdseriefacturacion())
                    .andIdprogramacionEqualTo(beanP.getIdprogramacion());

            if (vDatos != null) {
                vDatos.clear();
            }
            vDatos = facFacturaExtendsMapper.selectByExample(facFacturaExample);

            for (FacFactura factBean : vDatos) {
                log.writeLogTraspasoFactura(factBean.getNumerofactura(), factBean.getTraspasada(), (factBean.getErrortraspaso() != null) ? factBean.getErrortraspaso() : "");
            }

            //ENVIO DE CORREO ELECTRONICO CON EL LOG:
            String from = getParametro(SigaConstants.IDINSTITUCION_2000, SigaConstants.MODULO_ECOM, SigaConstants.PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_FROM).getValor();
            String bcc = getParametro(SigaConstants.IDINSTITUCION_2000, SigaConstants.MODULO_ECOM, SigaConstants.PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_BCC).getValor();
            String body = getParametro(SigaConstants.IDINSTITUCION_2000, SigaConstants.MODULO_ECOM, SigaConstants.PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_BODY).getValor();

            //SELECT PARA OBTENER EL NOMBRE Y LA DESCRIPCION DE LA SERIE DE FACTURACION:
            FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
            facSeriefacturacionKey.setIdinstitucion(beanP.getIdinstitucion());
            facSeriefacturacionKey.setIdseriefacturacion(beanP.getIdseriefacturacion());

            FacSeriefacturacion facSeriefacturacion = facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey);

            String descripcionSerieFacturacion = "";
            if (facSeriefacturacion != null) {
                descripcionSerieFacturacion = facSeriefacturacion.getNombreabreviado() + " - " + facSeriefacturacion.getDescripcion();
            }

            //SELECT PARA OBTENER LA DESCRIPCION DE LA PROGRAMACION:
            FacFacturacionprogramadaKey facFacturacionprogramadaKey = new FacFacturacionprogramadaKey();
            facFacturacionprogramadaKey.setIdinstitucion(beanP.getIdinstitucion());
            facFacturacionprogramadaKey.setIdseriefacturacion(beanP.getIdseriefacturacion());
            facFacturacionprogramadaKey.setIdprogramacion(beanP.getIdprogramacion());

            FacFacturacionprogramada facFacturacionprogramada = facProgMapper.selectByPrimaryKey(facFacturacionprogramadaKey);

            String descripcionProgramacion = "";
            if (facFacturacionprogramada != null) {
                descripcionProgramacion = facFacturacionprogramada.getDescripcion();
            }

            //CONSTRUIMOS EL CUERPO DEL MENSAJE:
            body = String.format(body, descripcionSerieFacturacion, descripcionProgramacion);
            String[] bccArray = bcc.split(";");

            String asunto = getParametro(SigaConstants.IDINSTITUCION_2000, SigaConstants.MODULO_ECOM, SigaConstants.PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_ASUNTO).getValor();

            enviosCommonsService.enviarCorreo(from, bccArray, asunto, body, new File(plogTraspaso.toString()),
                    SigaConstants.MAIL_SMTP_ACTUALIZACIONCENSO_HOST, SigaConstants.MAIL_SMTP_ACTUALIZACIONCENSO_PORT,
                    SigaConstants.MAIL_SMTP_ACTUALIZACIONCENSO_USER, SigaConstants.MAIL_SMTP_ACTUALIZACIONCENSO_PWD);

        } catch (Exception e) {
            LOGGER.error("ERROR GENERAL EN TRY TRASPASO FACTURAS.");
            rollBack(tx);
            logConfirmacion.writeLogFactura("TRASPASO", "N/A", "N/A", "Error general en el proceso de Traspaso de Facturas: " + e);
            throw e;
        }

        return msjAviso;
    }

    private Path getPathLogTraspaso(FacFacturacionprogramada fac) {
        String nombreFichero = getNombreFicheroLogTraspaso(fac);
        String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
        return Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(nombreFichero);
    }

    private String getNombreFicheroLogTraspaso(FacFacturacionprogramada fac) {
        return LOG_FAC_TRASPASO_PREFIX + fac.getIdseriefacturacion() + "_" + fac.getIdprogramacion() + LOG_XLS;
    }
}
