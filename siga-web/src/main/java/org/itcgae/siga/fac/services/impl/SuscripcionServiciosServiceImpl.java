package org.itcgae.siga.fac.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltrosSuscripcionesItem;
import org.itcgae.siga.DTO.fac.ListaCompraProductosDTO;
import org.itcgae.siga.DTO.fac.ListaCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.ListaSuscripcionesDTO;
import org.itcgae.siga.DTO.fac.ListaSuscripcionesItem;
import org.itcgae.siga.DTO.fac.RevisionAutLetradoItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
import org.itcgae.siga.db.entities.PysServiciosinstitucionExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.PysSuscripcionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.ISuscripcionServiciosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.itcgae.siga.fac.services.impl.EjecucionPlsServicios;

@Service
public class SuscripcionServiciosServiceImpl implements ISuscripcionServiciosService {

	private Logger LOGGER = Logger.getLogger(SuscripcionServiciosServiceImpl.class);

	private static Boolean alguienEjecutando=Boolean.FALSE;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private GestionFichaCompraSuscripcionServiceImpl gestionFichaCompraSuscripcionServiceImpl;
	
	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;
	
	@Autowired
	private PysServiciosinstitucionExtendsMapper pysServiciosinstitucionExtendsMapper;
	
	@Autowired
	private PysSuscripcionMapper pysSuscripcionMapper;
	
	@Autowired
	private CenInstitucionMapper cenInstitucionMapper;
	
	@Autowired
	private EjecucionPlsServicios ejecucionPlsServicios;
	
	@Override
	public ListaSuscripcionesDTO getListaSuscripciones(HttpServletRequest request, FiltrosSuscripcionesItem peticion) {
		ListaSuscripcionesDTO listaSuscripciones = new ListaSuscripcionesDTO();
		Error error = new Error();

		LOGGER.debug(
				"getListaSuscripciones() -> Entrada al servicio para recuperar la lista de suscripciones de servicios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getListaSuscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getListaSuscripciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					//Para obtener toda la informacion de una compra/suscripcion ya creada
						LOGGER.info(
								"getListaSuscripciones() / pysPeticioncomprasuscripcionExtendsMapper.getListaSuscripciones() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener las peticiones de suscripcion que cumplan las condiciones");

						listaSuscripciones.setListaSuscripcionesItems(pysPeticioncomprasuscripcionExtendsMapper.getListaSuscripciones(peticion, idInstitucion, usuarios.get(0).getIdlenguaje()));
						
						//Revisamos las fechas obtenidas para determinar el idestado que se devuelve
						for(ListaSuscripcionesItem suscripcion : listaSuscripciones.getListaSuscripcionesItems()) {
							if(suscripcion.getFechaAnulada() != null) suscripcion.setIdEstadoSolicitud("5");
							else if(suscripcion.getFechaSolicitadaAnulacion() != null) suscripcion.setIdEstadoSolicitud("4");
							else if(suscripcion.getFechaEfectiva() != null) suscripcion.setIdEstadoSolicitud("3");
							else if(suscripcion.getFechaDenegada() != null) suscripcion.setIdEstadoSolicitud("2");
							else suscripcion.setIdEstadoSolicitud("1");
							
							//REVISAR
//							List<ListaServiciosItem> serviciosSuscripcion = gestionFichaCompraSuscripcionServiceImpl.getListaServiciosSuscripcion(request, suscripcion.getnSolicitud()).getListaServiciosItems();
							
							//Float totalSuscripcion = (float) 0;
//							for(ListaServiciosItem servicioSuscripcion : serviciosSuscripcion) {
//								//(prodSol.VALOR*prodSol.cantidad)*(1+TIVA.VALOR/100)
//								totalCompra =  ((Float.parseFloat(servicioSuscripcion.getPrecioperiodicidad()))*(1+(Float.parseFloat(servicioSuscripcion.getIva())/100)));
//							}
							
							//suscripcion.setImporte(totalSuscripcion.toString());
						
						}
						
						LOGGER.info(
								"getListaSuscripciones() / pysPeticioncomprasuscripcionExtendsMapper.getListaSuscripciones() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener las peticiones de compra que cumplan las condiciones");
					
						error.setCode(200);
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"getListaSuscripciones() -> Se ha producido un error al obtener las peticiones de suscripcion que cumplan las condiciones",
					e);
			error.setCode(500);
		}

		LOGGER.debug(
				"getListaSuscripciones() -> Salida del servicio para obtener las peticiones de suscripcion que cumplan las condiciones");

		listaSuscripciones.setError(error);
		
		return listaSuscripciones;
		
	}
	
		
	@Scheduled(cron = "0 0/2 * * * *")//    @Scheduled(cron = "${cron.pattern.scheduled.procesoServicios}")
	@Override
	public void ejecutaSuscripcionesAutomaticas() {
		LOGGER.info("SuscripcionServiciosServiceImpl --> ejecutaSuscripcionesAutomaticas --> ENTRA ejecutaSuscripcionesAutomaticas");
		//this.ejecutaFacturacionSJCS();
//		if (isAlguienEjecutando()){
//			LOGGER.debug("YA SE ESTAN EJECUTANDO LOS SERVICIOS AUTOMATICOS EN BACKGROUND. CUANDO SE TERMINE SE INICIARA OTRA VEZ EL PROCESO.");
//		}
		try {
			procesarSuscripcionesAut();

		} catch(Exception e){
			throw e;
		}
//		finally {
//			setNadieEjecutando();
//		}
		LOGGER.info("SuscripcionServiciosServiceImpl --> ejecutaSuscripcionesAutomaticas --> SALE ejecutaSuscripcionesAutomaticas");
	}
	
	@Scheduled(cron = "0 0/2 * * * *")
	@Override
	public void ejecutaRevisionAutomatica() {
		LOGGER.info("SuscripcionServiciosServiceImpl --> ejecutaRevisionAutomatica --> ENTRA ejecutaRevisionAutomatica");
		//this.ejecutaFacturacionSJCS();
//		if (isAlguienEjecutando()){
//			LOGGER.debug("YA SE ESTAN EJECUTANDO LOS SERVICIOS AUTOMATICOS EN BACKGROUND. CUANDO SE TERMINE SE INICIARA OTRA VEZ EL PROCESO.");
//		}
		try {
			procesarRevisionAut();

		} catch(Exception e){
			throw e;
		}
//		finally {
//			setNadieEjecutando();
//		}
		LOGGER.info("SuscripcionServiciosServiceImpl --> ejecutaRevisionAutomatica --> SALE ejecutaRevisionAutomatica");
	}
	
	private void setNadieEjecutando(){
		synchronized(SuscripcionServiciosServiceImpl.alguienEjecutando){
			SuscripcionServiciosServiceImpl.alguienEjecutando=Boolean.FALSE;
		}
	}
	
	public boolean isAlguienEjecutando(){
		synchronized(SuscripcionServiciosServiceImpl.alguienEjecutando){
			if (!SuscripcionServiciosServiceImpl.alguienEjecutando){
				SuscripcionServiciosServiceImpl.alguienEjecutando=Boolean.TRUE;
				return false;
			} else {
				return true;
			}
		}
	}
	
	private void procesarSuscripcionesAut() {

		PysServiciosinstitucionExample serviciosAutomaticosExample = new PysServiciosinstitucionExample();
		
		//Se tendran en cuenta aquellos servicios que sean automaticos y que no se hayan dado de baja
		serviciosAutomaticosExample.createCriteria().andAutomaticoEqualTo("1").andFechabajaIsNull();

		List<PysServiciosinstitucion> serviciosAutomaticos = pysServiciosinstitucionExtendsMapper.selectByExample(serviciosAutomaticosExample);

		AdmUsuarios usu = new AdmUsuarios();

		//REVISAR: Al ser un servicioque se ejecuta de forma automatica, se considera que el usuario sera el 0.
		usu.setIdusuario(0);
		//Se realizan las suscripciones automaticas que sean posibles que no se hayan realizado todavia
		for(PysServiciosinstitucion servicioAutomatico: serviciosAutomaticos) {
			try {
				ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio(servicioAutomatico.getIdinstitucion(), Integer.valueOf(servicioAutomatico.getIdtiposervicios()), Integer.valueOf(servicioAutomatico.getIdservicio().toString()), servicioAutomatico.getIdserviciosinstitucion().toString(), usu);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void procesarRevisionAut() {
		
		AdmUsuarios usu = new AdmUsuarios();

		//REVISAR: Al ser un servicioque se ejecuta de forma automatica, se considera que el usuario sera el 0.
		usu.setIdusuario(0);
		
		CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
		exampleInstitucion.setDistinct(true);
		exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull();

		List<CenInstitucion> listaInstituciones = cenInstitucionMapper.selectByExample(exampleInstitucion);
		
		//Se comprueba que las suscripciones de TODOS los servicios cumplen los requisitos
		//Si no lo hacen, se da de baja las suscripciones correspondientes.
		for(CenInstitucion institucion: listaInstituciones) {
			try {
				ejecucionPlsServicios.ejecutarPL_RevisionAutomaticaServicios(institucion.getIdinstitucion(), usu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actualizacionSuscripcionesPersona(HttpServletRequest request, RevisionAutLetradoItem peticion) {

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

		LOGGER.info(
				"actualizacionSuscripcionesPersona() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		LOGGER.info(
				"actualizacionSuscripcionesPersona() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");


		LOGGER.info(
				"SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> ENTRA actualizacionSuscripcionesPersona");
		// this.ejecutaFacturacionSJCS();
//		if (isAlguienEjecutando()) {
//			LOGGER.debug(
//					"YA SE ESTAN EJECUTANDO LOS SERVICIOS AUTOMATICOS EN BACKGROUND. CUANDO SE TERMINE SE INICIARA OTRA VEZ EL PROCESO.");
//		}
		
		//Se seleccionan las entradas a procesar
//		PysColasuscripcionesAutoExample personasActualizarExample = new PysColasuscripcionesAutoExample();
		
		//Se eligen las entradas que tengan una fecha de modificacion de hace más de diez minutos y que esten sin procesar
//		personasActualizarExample.createCriteria().set;
//		
//		List<PysColasuscripcionesAuto> personasActualizar = pysColasuscripcionesAutoMapper.selectByExample(personasActualizarExample);
		try {
			
//			for(PysColasuscripcionesAuto peticion: personasActualizar) {
//			
//				ejecucionPlsServicios.ejecutarPL_ProcesoRevisionLetrado(idInstitucion, peticion.getIdPersona(), peticion.getFechaProcesamiento(), usuarios.get(0));
//
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			setNadieEjecutando();
//		}
		//Se seleccionan las filas a eliminar
//		PysColasuscripcionesAutoExample personasActualizarExample = new PysColasuscripcionesAutoExample();
		
		//Se eligen las entradas que tengan una fecha de modificacion de hace más de un día
//		personasActualizarExample.createCriteria().set;
//		
//		List<PysColasuscripcionesAuto> personasBorrar = pysColasuscripcionesAutoMapper.selectByExample(personasActualizarExample);
		
		
		LOGGER.info(
				"SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> SALE actualizacionSuscripcionesPersona");
	}
	
	@Override
	public InsertResponseDTO actualizacionColaSuscripcionesPersona(HttpServletRequest request, RevisionAutLetradoItem peticion) {

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		
		InsertResponseDTO response = new InsertResponseDTO();

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

		LOGGER.info(
				"actualizacionColaSuscripcionesPersona() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		LOGGER.info(
				"actualizacionColaSuscripcionesPersona() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
			if (usuarios != null && !usuarios.isEmpty()) {


					
//					pysColasuscripcionesAutoExample personaColaExample = new pysColasuscripcionesAutoExample();
//					
//					personaColaExample.createCriteria().set;
					
					
					LOGGER.info(
							"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.selectByExample() -> Entrada a pysColasuscripcionesAutoMapper para comprobar si ya hay una entrada en la cola proprocesar para esta persona");
	
//					List<pysColasuscripcionesAuto> personaColaVieja = pysColasuscripcionesAutoMapper.selectByExample(personaColaExample);
					
					LOGGER.info(
							"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.selectByExample() -> Salida de pysColasuscripcionesAutoMapper para comprobar si ya hay una entrada en la cola proprocesar para esta persona");
	
//					if(personaColaVieja.isEmpty()) {
						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() -> Persona no presente en la cola");
		
//						PysColasuscripcionesAuto personaCola = new pysColasuscripcionesAuto();
//						
//						personaCola.setFechaClave(peticion.getFechaProcesamiento());
//						personaCola.setIdpersona(peticion.getIdPersona());
//						personaCola.setIdinstitucion(idInstitucion);
//						personaCola.setIdestado("0");
//						
//						personaCola.setFechaModificacion(new Date());
//						personaCola.setUsuModificacion(usuarios.get(0));
						
						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.insert() -> Entrada a pysColasuscripcionesAutoMapper para insertar una entrada en la cola por procesar para esta persona");
						
//						pysColasuscripcionesAutoMapper.insert(personaCola);

						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.insert() -> Salida de pysColasuscripcionesAutoMapper para insertar una entrada en la cola por procesar para esta persona");
		
//					}
//					else {
						LOGGER.info(
								"actualizacionColaSuscripcionesPersona()  -> Persona presente en la cola");
						
//						PysColasuscripcionesAuto personaCola = personaColaVieja.get(0);
//		
//						personaCola.setFechaModificacion(new Date());
//						personaCola.setUsuModificacion(usuarios.get(0));
//						
//						if(personaCola.getFechaClave()<peticion.getFechaProcesamiento()) {
//							personaCola.setFechaClave(peticion.getFechaProcesamiento());
//						}

						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.updateByPrimaryKey() -> Entrada a pysColasuscripcionesAutoMapper para actualizar una entrada en la cola por procesar para esta persona");
		
//						pysColasuscripcionesAutoMapper.updateByPrimaryKey(personaCola);
						

						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.updateByPrimaryKey() -> Entrada a pysColasuscripcionesAutoMapper para actualizar una entrada en la cola por procesar para esta persona");
		
						
//					}
			}
			
		LOGGER.debug(
				"actualizacionColaSuscripcionesPersona() -> Salida del servicio para obtener las peticiones de suscripcion que cumplan las condiciones");
		
		response.setStatus("200");

		return response;
	
	}
}
