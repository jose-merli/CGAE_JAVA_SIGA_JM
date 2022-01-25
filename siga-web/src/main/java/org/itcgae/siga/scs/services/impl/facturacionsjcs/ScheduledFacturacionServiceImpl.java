package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSServices;
import org.itcgae.siga.scs.services.facturacionsjcs.IScheduledFacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledFacturacionServiceImpl implements IScheduledFacturacionService {

    private Logger LOGGER = Logger.getLogger(ScheduledFacturacionServiceImpl.class);

    @Autowired
    private IFacturacionSJCSServices facturacionServices;

    @Scheduled(cron = "${cron.pattern.scheduled.procesoFacturacion}")
    @Override
    public void ejecutaFacturacionSJCS() {
        LOGGER.info("ScheduledFacturacionServiceImpl --> ejecutaFacturacionSJCS --> ENTRA ejecutaFacturacionSJCS");
        facturacionServices.ejecutaFacturacionSJCS();
        LOGGER.info("ScheduledFacturacionServiceImpl --> ejecutaFacturacionSJCS --> SALE ejecutaFacturacionSJCS");
    }

    @Scheduled(cron = "${cron.pattern.scheduled.procesoFacturacionBloqueadas}")
    @Override
    public void ejecutaFacturacionesSJCSBloqueadas() {
        LOGGER.info("ScheduledFacturacionServiceImpl --> ENTRA ejecutaFacturacionesSJCSBloqueadas");
        facturacionServices.ejecutaFacturacionesSJCSBloqueadas();
        LOGGER.info("ScheduledFacturacionServiceImpl --> SALE ejecutaFacturacionesSJCSBloqueadas");
    }

}