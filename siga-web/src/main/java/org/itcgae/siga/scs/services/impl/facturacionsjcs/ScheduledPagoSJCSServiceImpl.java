package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.FcsPagosjg;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagosjgExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IScheduledPagoSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduledPagoSJCSServiceImpl implements IScheduledPagoSJCSService {

    private final Logger LOGGER = Logger.getLogger(ScheduledPagoSJCSServiceImpl.class);

    @Autowired
    private CenInstitucionExtendsMapper institucionMapper;

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private FcsPagosjgExtendsMapper fcsPagosjgExtendsMapper;

    @Autowired
    private UtilidadesPagoSJCS utilidadesPagoSJCS;

    @Scheduled(cron = "${cron.pattern.scheduled.procesoPagosBloqueados}")
    public void ejecutaPagosSJCSBloqueados() {
        CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
        exampleInstitucion.setDistinct(true);
        exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull();

        List<CenInstitucion> listaInstituciones = institucionMapper.selectByExample(exampleInstitucion);

        for (CenInstitucion institucion : listaInstituciones) {
            pagosSJCSBloqueados(institucion.getIdinstitucion());
        }
    }

    private void pagosSJCSBloqueados(Short idInstitucion) {
        LOGGER.info("ENTRA -> ScheduledPagoSJCSServiceImpl.pagosSJCSBloqueados()");

        //Recuperamos el tiempo estimado como bloqueo
        GenPropertiesKey propertiesPK = new GenPropertiesKey();
        propertiesPK.setFichero("SIGA");
        propertiesPK.setParametro("facturacion.programacionAutomaticaPagosJG.maxMinutosEnEjecucion");
        GenProperties tiempoMaximo = genPropertiesMapper.selectByPrimaryKey(propertiesPK);

        long tiempoMaximoMinutos = Long.parseLong(tiempoMaximo.getValor());
        List<FcsPagosjg> pagosjgEjecutandoList = fcsPagosjgExtendsMapper.getPagosSJCSBloqueadosEnEjecucion(idInstitucion, tiempoMaximoMinutos);
        List<FcsPagosjg> pagosjgCerrandoList = fcsPagosjgExtendsMapper.getPagosSJCSBloqueadosEnCierre(idInstitucion, tiempoMaximoMinutos);
        List<FcsPagosjg> pagosjgDeshaciendoCierreList = fcsPagosjgExtendsMapper.getPagosSJCSBloqueadosEnDeshacerCierre(idInstitucion, tiempoMaximoMinutos);

        LOGGER.info("pagosSJCSBloqueados() -> Pagos SJCS Bloqueados en Ejecutando: " + pagosjgEjecutandoList.size());
        for (FcsPagosjg pago: pagosjgEjecutandoList) {
            try {
                pagoSJCSBloqueadoEjecutando(pago);
            } catch (Exception ex) {
                LOGGER.error("Error al restaurar el pago SJCS:" +ex);
            }
        }

        LOGGER.info("pagosSJCSBloqueados() -> Pagos SJCS Bloqueados en Cerrando: " + pagosjgCerrandoList.size());
        for (FcsPagosjg pago: pagosjgCerrandoList) {
            try {
                pagoSJCSBloqueadoCerrando(pago);
            } catch (Exception ex) {
                LOGGER.error("Error al restaurar el pago SJCS:" +ex);
            }
        }

        LOGGER.info("pagosSJCSBloqueados() -> Pagos SJCS Bloqueados en Deshaciendo CIerre: " + pagosjgDeshaciendoCierreList.size());
        for (FcsPagosjg pago: pagosjgDeshaciendoCierreList) {
            try {
                pagoSJCSBloqueadoDeshaciendoCierre(pago);
            } catch (Exception ex) {
                LOGGER.error("Error al restaurar el pago SJCS:" +ex);
            }
        }

        LOGGER.info("SALE -> ScheduledPagoSJCSServiceImpl.pagosSJCSBloqueados()");
    }

    @Transactional(rollbackFor = Exception.class, timeout=24000)
    private void pagoSJCSBloqueadoEjecutando(FcsPagosjg pago) throws Exception {
        LOGGER.info("ENTRA -> ScheduledPagoSJCSServiceImpl.pagoSJCSBloqueadoEjecutando()");

        // Se pone el pago a estado abierto para que se pueda volver a ejecutar
        AdmUsuarios usuario = new AdmUsuarios();
        usuario.setIdusuario(SigaConstants.USUMODIFICACION_0);
        utilidadesPagoSJCS.ponerPagoEstadoAbierto(pago, pago.getIdinstitucion(), usuario);

        LOGGER.info("SALE -> ScheduledPagoSJCSServiceImpl.pagoSJCSBloqueadoEjecutando()");
    }

    @Transactional(rollbackFor = Exception.class, timeout=24000)
    private void pagoSJCSBloqueadoCerrando(FcsPagosjg pago) throws Exception {
        LOGGER.info("ENTRA -> ScheduledPagoSJCSServiceImpl.pagoSJCSBloqueadoCerrando()");

        // Se pone el pago a estado ejecutado para que se pueda volver a cerrar
        AdmUsuarios usuario = new AdmUsuarios();
        usuario.setIdusuario(SigaConstants.USUMODIFICACION_0);
        utilidadesPagoSJCS.deshacerCierre(pago, pago.getIdinstitucion(), usuario);

        LOGGER.info("SALE -> ScheduledPagoSJCSServiceImpl.pagoSJCSBloqueadoCerrando()");
    }

    @Transactional(rollbackFor = Exception.class, timeout=24000)
    private void pagoSJCSBloqueadoDeshaciendoCierre(FcsPagosjg pago) throws Exception {
        LOGGER.info("ENTRA -> ScheduledPagoSJCSServiceImpl.pagoSJCSBloqueadoDeshaciendoCierre()");

        // Se pone el pago a estado ejecutado para que se pueda volver a cerrar
        AdmUsuarios usuario = new AdmUsuarios();
        usuario.setIdusuario(SigaConstants.USUMODIFICACION_0);
        utilidadesPagoSJCS.deshacerCierre(pago, pago.getIdinstitucion(), usuario);

        LOGGER.info("SALE -> ScheduledPagoSJCSServiceImpl.pagoSJCSBloqueadoDeshaciendoCierre()");
    }

}
