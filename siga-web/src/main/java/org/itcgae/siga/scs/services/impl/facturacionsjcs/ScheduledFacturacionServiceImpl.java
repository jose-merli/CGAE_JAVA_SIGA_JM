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
  //COMENTAR SCHEDULED PARA USAR EN LOCAL
    @Scheduled(cron = "${cron.pattern.scheduled.procesoFacturacion}")
    @Override
    public void ejecutaFacturacionSJCS() {
        LOGGER.debug("ScheduledFacturacionServiceImpl --> ejecutaFacturacionSJCS --> ENTRA ejecutaFacturacionSJCS");
        facturacionServices.ejecutaFacturacionSJCS();
        LOGGER.debug("ScheduledFacturacionServiceImpl --> ejecutaFacturacionSJCS --> SALE ejecutaFacturacionSJCS");
    }
  //COMENTAR SCHEDULED PARA USAR EN LOCAL
    @Scheduled(cron = "${cron.pattern.scheduled.procesoFacturacionBloqueadas}")
    @Override
    public void ejecutaFacturacionesSJCSBloqueadas() {
        LOGGER.debug("ScheduledFacturacionServiceImpl --> ENTRA ejecutaFacturacionesSJCSBloqueadas");
        facturacionServices.ejecutaFacturacionesSJCSBloqueadas();
        LOGGER.debug("ScheduledFacturacionServiceImpl --> SALE ejecutaFacturacionesSJCSBloqueadas");
    }

}