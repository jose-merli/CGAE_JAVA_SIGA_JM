package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.itcgae.siga.scs.services.facturacionsjcs.IScheduledCertificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledCertificacionServiceImpl implements IScheduledCertificacionService {

    private Logger LOGGER = Logger.getLogger(ScheduledCertificacionServiceImpl.class);

    @Autowired
    private ICertificacionFacSJCSService certificacionFacSJCSService;

    @Scheduled(cron = "${cron.pattern.scheduled.certificacion.procesoMarcarVisible}")
    @Override
    public void marcaVisiblesFacturacionesCerradas() {
        LOGGER.info("ScheduledCertificacionServiceImpl.marcaVisiblesFacturacionesCerradas() --> ENTRA certificacionFacSJCSService.marcaVisiblesFacturacionesCerradas()");
        certificacionFacSJCSService.marcaVisiblesFacturacionesCerradas();
        LOGGER.info("ScheduledCertificacionServiceImpl.marcaVisiblesFacturacionesCerradas() --> SALE certificacionFacSJCSService.marcaVisiblesFacturacionesCerradas()");
    }
}
