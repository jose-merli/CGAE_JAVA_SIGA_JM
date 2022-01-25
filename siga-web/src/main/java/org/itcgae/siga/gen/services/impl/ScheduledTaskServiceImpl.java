package org.itcgae.siga.gen.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.age.service.IFichaEventosService;
import org.itcgae.siga.cen.services.IBusquedaSancionesService;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.itcgae.siga.gen.services.IScheduledTaskService;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskServiceImpl implements IScheduledTaskService {

	private Logger LOGGER = Logger.getLogger(ScheduledTaskServiceImpl.class);
	
	@Autowired
	private IFichaCursosService fichaCursosService;
	
	@Autowired
	private IFichaEventosService fichaEventosService;
	
	@Autowired
	private IBusquedaSancionesService busquedaSancionesService;
	
	@Autowired
	private IFacturacionSJCSServices facturacionServices;
	
	@Scheduled(cron = "${cron.pattern.scheduled.forCurso}")
	@Override
	public void cambiaCursoEnCurso() {
		LOGGER.info("ScheduledTaskServiceImpl --> cambiaCursoEnCurso --> ENTRA updateEstadoCursoAuto");
		fichaCursosService.updateEstadoCursoAuto();
		LOGGER.info("ScheduledTaskServiceImpl --> cambiaCursoEnCurso --> SALE updateEstadoCursoAuto");
	}
	
	@Scheduled(cron = "${cron.pattern.scheduled.festivos}")
	@Override
	public void insertarFestivos() {
		LOGGER.info("ScheduledTaskServiceImpl --> insertarFestivos --> ENTRA insertarFestivosAuto");
		fichaEventosService.insertarFestivosAuto();
		LOGGER.info("ScheduledTaskServiceImpl --> insertarFestivos --> SALE insertarFestivosAuto");
		LOGGER.info("ScheduledTaskServiceImpl --> insertarFestivos --> ENTRA generaEventosLaboral");
		fichaEventosService.generaEventosLaboral();
		LOGGER.info("ScheduledTaskServiceImpl --> insertarFestivos --> SALE generaEventosLaboral");
	}

	@Scheduled(cron = "${cron.pattern.scheduled.envioCorreoSancionesCgaeDiaHora}")
	//@Scheduled(cron = "0/59 * * * * ?")
	@Override
	public void archivarSancionesRehabilitado() {
		LOGGER.info("ScheduledTaskServiceImpl --> archivarSancionesRehabilitado --> ENTRA archivarSancionesRehabilitado");
		busquedaSancionesService.archivarSancionesRehabilitado();
		LOGGER.info("ScheduledTaskServiceImpl --> archivarSancionesRehabilitado --> SALE archivarSancionesRehabilitado");
	}
	
	@Scheduled(cron = "${cron.pattern.scheduled.ageEvento}")
	@Override
	public void cambiaEventosImpartido() {
		LOGGER.info("ScheduledTaskServiceImpl --> cambiaCursoEnCurso --> ENTRA updateEstadoCursoAuto");
		fichaEventosService.updateEstadoEventoAuto();
		LOGGER.info("ScheduledTaskServiceImpl --> cambiaCursoEnCurso --> SALE updateEstadoCursoAuto");
	}
	
	@Scheduled(cron = "${cron.pattern.scheduled.envioNotificacionesEventos}")
	@Override
	public void generarNotificaciones() {
	//	LOGGER.info("ScheduledTaskServiceImpl --> generarNotificaciones --> ENTRA generateNotificationAuto");
		fichaEventosService.generateNotificationsAuto();
		//LOGGER.info("ScheduledTaskServiceImpl --> generarNotificaciones --> SALE generateNotificationAuto");
	}
}
