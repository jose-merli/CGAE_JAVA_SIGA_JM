package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.fac.services.IFacturacionProgramadaPySService;
import org.itcgae.siga.fac.services.IScheduledFacturacionPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledFacturacionPySServiceImpl implements IScheduledFacturacionPySService {

    private Logger LOGGER = Logger.getLogger(ScheduledFacturacionPySServiceImpl.class);

    @Autowired
    private IFacturacionProgramadaPySService facturacionPySService;

    @Scheduled(cron = "${cron.pattern.scheduled.procesoFacturacionPyS}")
    @Override
    public void ejecutaProcesoFacturacionPyS() {
        LOGGER.info("ScheduledFacturacionPySServiceImpl --> ENTRA ejecutaProcesoFacturacionPyS()");
        facturacionPySService.ejecutaProcesoFacturacionPyS();
        LOGGER.info("ScheduledFacturacionPySServiceImpl --> SALE ejecutaProcesoFacturacionPyS()");
    }

}
