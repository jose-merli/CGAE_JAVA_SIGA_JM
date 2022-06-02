package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.PysColaSuscripcionesAuto;
import org.itcgae.siga.db.entities.PysColaSuscripcionesAutoExample;
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
import org.itcgae.siga.db.entities.PysServiciosinstitucionExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.PysColaSuscripcionesAutoMapper;
import org.itcgae.siga.db.mappers.PysSuscripcionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.ISuscripcionServiciosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	@Autowired
	private PysColaSuscripcionesAutoMapper pysColaSuscripcionesAutoMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
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

						Integer tamMaximo = getTamanoMaximo(usuarios.get(0).getIdinstitucion());
						
						listaSuscripciones.setListaSuscripcionesItems(pysPeticioncomprasuscripcionExtendsMapper.getListaSuscripciones(peticion, idInstitucion, usuarios.get(0).getIdlenguaje(), tamMaximo));
						
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
		
		LOGGER.info(
				"getListaSuscripciones() -> Salida del servicio para obtener las peticiones de suscripcion que cumplan las condiciones");


		LOGGER.debug(
				"getListaSuscripciones() -> Salida del servicio para obtener las peticiones de suscripcion que cumplan las condiciones");

		listaSuscripciones.setError(error);
		
		return listaSuscripciones;
		
	}
	
		
	/*@Scheduled(cron = "${cron.pattern.scheduled.procesoSuscripAut}")
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
	}*/
	
	@Scheduled(cron = "${cron.pattern.scheduled.procesoRevisionAutomatica}")
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

		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarSuscripcionesAut --> Entrada a pysServiciosinstitucionExtendsMapper.selectByExample() para seleccionar todos los servicios automaticos que no esten dados de baja");

		List<PysServiciosinstitucion> serviciosAutomaticos = pysServiciosinstitucionExtendsMapper.selectByExample(serviciosAutomaticosExample);

		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarSuscripcionesAut --> Salida de pysServiciosinstitucionExtendsMapper.selectByExample() para seleccionar todos los servicios automaticos que no esten dados de baja");

		AdmUsuarios usu = new AdmUsuarios();

		//REVISAR: Al ser un servicio que se ejecuta de forma automatica, se considera que el usuario sera el 0.
		usu.setIdusuario(0);
		
		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarSuscripcionesAut --> Entrada al bucle con "+serviciosAutomaticos.size()+" servicios automaticos seleccionados");

		//Se realizan las suscripciones automaticas que sean posibles que no se hayan realizado todavia
		for(PysServiciosinstitucion servicioAutomatico: serviciosAutomaticos) {
			try {
				//Puede que se lance un error ya que actualmente(03/01/22) existe una entrada en la tabla PYS_SERVICIOSINSTITUCION
				//IDINSTITUCION=2005; IDTIPOSERVICIOS=12; IDSERVICIO=1; IDSERVICIOSINSTITUCION=2;
				//tiene sus criterios definidos de forma anomala. 
				ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio(servicioAutomatico.getIdinstitucion(), Integer.valueOf(servicioAutomatico.getIdtiposervicios()), Integer.valueOf(servicioAutomatico.getIdservicio().toString()), servicioAutomatico.getIdserviciosinstitucion().toString(), usu);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarSuscripcionesAut --> Salida del bucle para comprobar las suscripciones de los servicios automaticos");
	}
	
	
	private void procesarRevisionAut() {
		
		AdmUsuarios usu = new AdmUsuarios();

		//REVISAR: Al ser un servicio que se ejecuta de forma automatica, se considera que el usuario sera el 0.
		usu.setIdusuario(0);
		
		CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
		exampleInstitucion.setDistinct(true);
		exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull();
		

		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarRevisionAut --> Entrada a cenInstitucionMapper.selectByExample para seleccionar todas las instituciones disponibles");

		List<CenInstitucion> listaInstituciones = cenInstitucionMapper.selectByExample(exampleInstitucion);

		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarRevisionAut --> Salida de cenInstitucionMapper.selectByExample para seleccionar todas las instituciones disponibles");
		
		
		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarRevisionAut --> Entrada al bucle para comprobar que las condiciones de suscripcion de todos los servicios se cumplen");

		//Se comprueba que las suscripciones de TODOS los servicios cumplen los requisitos
		//Si no lo hacen, se da de baja las suscripciones correspondientes.
		for(CenInstitucion institucion: listaInstituciones) {
			try {
				//Puede que se lance un error ya que actualmente (03/01/22) existe una entrada en la tabla PYS_SERVICIOSINSTITUCION
				//IDINSTITUCION=2005; IDTIPOSERVICIOS=12; IDSERVICIO=1; IDSERVICIOSINSTITUCION=2;
				//tiene sus criterios definidos de forma anomala.
				ejecucionPlsServicios.ejecutarPL_RevisionAutomaticaServicios(institucion.getIdinstitucion(), usu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LOGGER.info("SuscripcionServiciosServiceImpl --> procesarRevisionAut --> Salida del bucle para comprobar que las condiciones de suscripcion de todos los servicios se cumplen");

	}


	@Scheduled(cron = "${cron.pattern.scheduled.procesoRevisionLetrado}")
	@Override
	public void actualizacionSuscripcionesPersona() {

		
		LOGGER.info(
				"SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> ENTRA actualizacionSuscripcionesPersona");
		// this.ejecutaFacturacionSJCS();
//		if (isAlguienEjecutando()) {
//			LOGGER.debug(
//					"YA SE ESTAN EJECUTANDO LOS SERVICIOS AUTOMATICOS EN BACKGROUND. CUANDO SE TERMINE SE INICIARA OTRA VEZ EL PROCESO.");
//		}
		
		//Se seleccionan las entradas a procesar
		PysColaSuscripcionesAutoExample personasActualizarExample = new PysColaSuscripcionesAutoExample();
		
		//Se eligen las entradas que tengan una fecha de modificacion de hace más de diez minutos y que esten sin procesar
		
		personasActualizarExample.createCriteria().andFechamodificacionLessThan(new Date(new Date().getTime() - (10 * 60 * 1000)))
		.andIdestadoEqualTo((short) 0);
		
		List<PysColaSuscripcionesAuto> personasActualizar = pysColaSuscripcionesAutoMapper.selectByExample(personasActualizarExample);
		try {
			
			AdmUsuarios usu = new AdmUsuarios();

			//REVISAR: Al ser un servicioque se ejecuta de forma automatica, se considera que el usuario sera el 0.
			usu.setIdusuario(0);
			
			LOGGER.info("SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> Entrada al bucle con "+personasActualizar.size()+" personas a las que revisar sus suscripciones");

			
			for(PysColaSuscripcionesAuto persona: personasActualizar) {
			
				ejecucionPlsServicios.ejecutarPL_ProcesoRevisionLetrado(persona.getIdinstitucion(), persona.getIdpersona(), persona.getFechaclave(), usu);

				persona.setIdestado((short) 1);
				
				pysColaSuscripcionesAutoMapper.updateByPrimaryKey(persona);
			}
			
			LOGGER.info("SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> Salida del bucle con personas a las que revisar sus suscripciones");

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			setNadieEjecutando();
//		}
		//Se seleccionan las filas a eliminar
		PysColaSuscripcionesAutoExample personasBorrarExample = new PysColaSuscripcionesAutoExample();

	      Calendar calendar = Calendar.getInstance();
		
		//Se eligen las entradas que tengan una fecha de modificacion de hace más de un día
	    calendar.add(Calendar.DATE, -1);
		
	    personasBorrarExample.createCriteria().andIdestadoEqualTo((short) 1).andFechamodificacionLessThan(calendar.getTime());
		
		List<PysColaSuscripcionesAuto> personasBorrar = pysColaSuscripcionesAutoMapper.selectByExample(personasBorrarExample);
		
		LOGGER.info("SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> Se seleccionan para eliminar "+personasBorrar.size()+" entradas del historico");
		
		int response = pysColaSuscripcionesAutoMapper.deleteByExample(personasBorrarExample);
		
		if(!personasBorrar.isEmpty() && response == 0) {
			LOGGER.warn(
					"SuscripcionServiciosServiceImpl --> actualizacionSuscripcionesPersona --> Error al intentar eliminar el historico de procesos realizados");
		}
		
		
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

				
				LOGGER.info(
						"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.selectByExample() -> Salida de pysColasuscripcionesAutoMapper para obtener el id maximo");

				
					
					PysColaSuscripcionesAutoExample personaColaExample = new PysColaSuscripcionesAutoExample();
					
					//Se comprueba si esta persona tiene alguna entrada en la cola sin procesar
					personaColaExample.createCriteria().andIdpersonaEqualTo(peticion.getIdPersona()).andIdestadoEqualTo((short) 0)
					.andIdinstitucionEqualTo(idInstitucion);
					
					
					LOGGER.info(
							"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.selectByExample() -> Entrada a pysColasuscripcionesAutoMapper para comprobar si ya hay una entrada en la cola proprocesar para esta persona");
	
					List<PysColaSuscripcionesAuto> personaColaVieja = pysColaSuscripcionesAutoMapper.selectByExample(personaColaExample);
					
					LOGGER.info(
							"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.selectByExample() -> Salida de pysColasuscripcionesAutoMapper para comprobar si ya hay una entrada en la cola proprocesar para esta persona");
	
					if(personaColaVieja.isEmpty()) {
						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() -> Persona no presente en la cola");
		
						PysColaSuscripcionesAuto personaCola = new PysColaSuscripcionesAuto();
						
						personaCola.setFechaclave(peticion.getFechaProcesamiento());
						personaCola.setIdpersona(peticion.getIdPersona());
						personaCola.setIdinstitucion(idInstitucion);
						personaCola.setIdestado((short) 0);

						//REVISAR: PENDIENTE DE REALIZAR UNA CONSULTA MÁS EFICIENTE
						List<PysColaSuscripcionesAuto> colaSuscripcion = pysColaSuscripcionesAutoMapper.selectByExample(personaColaExample);
						
						//Obtiene nuevo id
						
						int maxIdCola = pysColaSuscripcionesAutoMapper.getNewIdCola();
						
						personaCola.setIdcolasuscripcion((long) maxIdCola);
						
						personaCola.setFechamodificacion(new Date());
						personaCola.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.insert() -> Entrada a pysColasuscripcionesAutoMapper para insertar una entrada en la cola por procesar para esta persona");
						
						pysColaSuscripcionesAutoMapper.insert(personaCola);

						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.insert() -> Salida de pysColasuscripcionesAutoMapper para insertar una entrada en la cola por procesar para esta persona");
		
					}
					else {
						LOGGER.info(
								"actualizacionColaSuscripcionesPersona()  -> Persona presente en la cola");
						
						PysColaSuscripcionesAuto personaCola = personaColaVieja.get(0);
		
						personaCola.setFechamodificacion(new Date());
						personaCola.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						if(personaCola.getFechaclave() == null || personaCola.getFechaclave().getTime()<peticion.getFechaProcesamiento().getTime()) {
							personaCola.setFechaclave(peticion.getFechaProcesamiento());
						}

						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.updateByPrimaryKey() -> Entrada a pysColasuscripcionesAutoMapper para actualizar una entrada en la cola por procesar para esta persona");
		
						pysColaSuscripcionesAutoMapper.updateByPrimaryKey(personaCola);
						

						LOGGER.info(
								"actualizacionColaSuscripcionesPersona() / pysColasuscripcionesAutoMapper.updateByPrimaryKey() -> Entrada a pysColasuscripcionesAutoMapper para actualizar una entrada en la cola por procesar para esta persona");
		
						
					}
			}
			
		LOGGER.info(
				"actualizacionColaSuscripcionesPersona() -> Salida del servicio para obtener las peticiones de suscripcion que cumplan las condiciones");
			
			
		LOGGER.debug(
				"actualizacionColaSuscripcionesPersona() -> Salida del servicio para obtener las peticiones de suscripcion que cumplan las condiciones");
		
		response.setStatus("200");

		return response;
	
	}
	
	private Integer getTamanoMaximo(Short idinstitucion) {
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("FAC").andParametroEqualTo("TAM_MAX_CONSULTA_FAC")
	    		.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idinstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    List<GenParametros> tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        Integer tamMaximo = null;
		if (tamMax != null) {
            tamMaximo  = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
		return tamMaximo;
	}
}
