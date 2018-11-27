package org.itcgae.siga.gen.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.itcgae.siga.gen.services.IScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskServiceImpl implements IScheduledTaskService {

	private Logger LOGGER = Logger.getLogger(ScheduledTaskServiceImpl.class);
	
	@Autowired
	private IFichaCursosService fichaCursosService;
	
	//@Scheduled(cron = "${cron.pattern.scheduled.forCurso}")
	@Override
	public void cambiaCursoEnCurso() {
		//fichaCursosService.updateEstadoCursoAuto();
	}

}
