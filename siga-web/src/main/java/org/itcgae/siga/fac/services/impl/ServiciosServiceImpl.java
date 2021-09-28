package org.itcgae.siga.fac.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.BorrarSuscripcionBajaItem;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysFormapagoproducto;
import org.itcgae.siga.db.entities.PysFormapagoproductoKey;
import org.itcgae.siga.db.entities.PysFormapagoservicios;
import org.itcgae.siga.db.entities.PysFormapagoserviciosKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
import org.itcgae.siga.db.mappers.PysFormapagoserviciosMapper;
import org.itcgae.siga.db.mappers.PysServiciosinstitucionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.IServiciosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiciosServiceImpl implements IServiciosService {
	
	private Logger LOGGER = Logger.getLogger(ProductosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private PySTiposServiciosExtendsMapper pysTiposServiciosExtendsMapper;
	
	@Autowired
	private PysServiciosExtendsMapper pysServiciosExtendsMapper;
	
	@Autowired
	private PysServiciosinstitucionExtendsMapper pysServiciosInstitucionExtendsMapper;
	
	@Autowired
	private PysServiciosinstitucionMapper pysServiciosInstitucionMapper;
	
	@Autowired
	private PysFormapagoserviciosMapper pysFormaPagoServicios;
	
	@Autowired
	private EjecucionPlsServicios ejecucionPlsServicios;
	
	
	@Override
	public ListaServiciosDTO searchListadoServicios(HttpServletRequest request, FiltroServicioItem filtroServicioItem) {
		ListaServiciosDTO listaServiciosDTO = new ListaServiciosDTO();
		Error error = new Error();

		LOGGER.info("searchListadoServicios() -> Entrada al servicio para recuperar el listado de servicios segun la busqueda");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchListadoServicios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchListadoServicios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchListadoServicios() / pysTiposServiciosExtendsMapper.searchListadoServicios() -> Entrada a pysTiposServiciosExtendsMapper para obtener el listado de servicios segun la busqueda");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ListaServiciosItem> listaServicios = pysTiposServiciosExtendsMapper
							.searchListadoServiciosBuscador(idioma, idInstitucion, filtroServicioItem);

					LOGGER.info(
							"searchListadoServicios() / pysTiposServiciosExtendsMapper.searchListadoServicios() -> Salida de pysTiposServiciosExtendsMapper para obtener el listado de servicios segun la busqueda");

					//Necesario para agrupar los servicios, ya que la sql devuelve los servicios repetidos con distintas formas de pago, con esto se pretende agrupar las formas de pago del servicio en una sola linea (info mas detallada en el funcional)
					List<ListaServiciosItem> listaServiciosProcesada = new ArrayList<ListaServiciosItem>();
					int numFormasDePago = 1; //Numero de servicios que añades cuando realmente son el mismo pero con diferentes formas de pago
					for (int i = 0; i < listaServicios.size(); i++) {
						
						if(i == 0) {
							listaServiciosProcesada.add(listaServicios.get(i));
						}else if((listaServicios.get(i).getIdtiposervicios() == listaServicios.get(i - 1).getIdtiposervicios()) && (listaServicios.get(i).getIdservicio() == listaServicios.get(i - 1).getIdservicio()) && (listaServicios.get(i).getIdserviciosinstitucion() == listaServicios.get(i - 1).getIdserviciosinstitucion())) //Comprueba que el servicio actual es distinto al anterior no el mismo con distinta forma de pago 
						{
							//Este if comprueba si es el 3 servicio identico excepto por la forma de pago al primero que añadiste (es decir este seria el 4 por lo que al tener mas de 3 formas de pago se ha de mostrar el numero)
							if(numFormasDePago > 2) {
								listaServiciosProcesada.get(listaServiciosProcesada.size() - 1).setFormapago(String.valueOf(numFormasDePago));
							}else {
								listaServiciosProcesada.get(listaServiciosProcesada.size() - 1).setFormapago(listaServiciosProcesada.get(listaServiciosProcesada.size() - 1).getFormapago() + ", " + listaServicios.get(i).getFormapago());						
							}
							numFormasDePago++;
					
						}else{
							numFormasDePago = 0;
							listaServiciosProcesada.add(listaServicios.get(i));
						}
					}
					
					for (ListaServiciosItem servicio : listaServiciosProcesada) {
						if(servicio.getAutomatico().equals("1")) {
							servicio.setAutomatico("Automático");
						}else if(servicio.getAutomatico().equals("0")) {
							servicio.setAutomatico("Manual");
						}
					}
					
					if (listaServiciosProcesada != null && listaServiciosProcesada.size() > 0) {
						listaServiciosDTO.setListaServiciosItems(listaServiciosProcesada);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServicioserviceImpl.searchListadoServicios() -> Se ha producido un error al obtener el listado de servicios segun la busqueda",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listaServiciosDTO.setError(error);

		LOGGER.info("searchListadoServicios() -> Salida del servicio para obtener el listado de servicios segun la busqueda");

		return listaServiciosDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO reactivarBorradoFisicoLogicoServicios(ListaServiciosDTO listadoServicios, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		IdPeticionDTO idPeticionDTO = new IdPeticionDTO();
		

		LOGGER.info("reactivarBorradoFisicoLogicoServicios() -> Entrada al servicio para borrar fisicamente o logicamente o reactivar un servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"reactivarBorradoFisicoLogicoServicios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"reactivarBorradoFisicoLogicoServicios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"reactivarBorradoFisicoLogicoServicios() / pysTiposServiciosExtendsMapper.reactivarBorradoFisicoLogicoServicios() -> Entrada a pysTiposServiciosExtendsMapper para borrar fisicamente o logicamente o reactivar un servicio");
					
					for (ListaServiciosItem servicio : listadoServicios.getListaServiciosItems()) {
						
						//Comprueba que haya alguna solicitud realizada
						if(pysTiposServiciosExtendsMapper.comprobarUsoServicio(servicio, idInstitucion) != null)
							idPeticionDTO.setIdpeticionUso(pysTiposServiciosExtendsMapper.comprobarUsoServicio(servicio, idInstitucion)); //Tener en cuenta que comprobarUsoServicio no devuelve solo un id si no uno por cada uso, por eso se usa una lista.
				
						//Borrado logico --> Actualizamos la fechabaja del servicio a la actual (sysdate)
						//Borrado fisico --> Eliminamos el registro del servicio y posteriormente el identificador
						if(idPeticionDTO.getIdpeticionUso().size() > 0 ) { //Borrado logico ya que comprobarUsoServicio devolvio resultado por lo que el servicio tiene alguna solicitud
							status = pysTiposServiciosExtendsMapper.borradoLogicoServicios(usuarios.get(0), servicio, idInstitucion);			
							
							String resultado[] = ejecucionPlsServicios.ejecutarPL_ServiciosAutomaticosProcesoBaja(idInstitucion, servicio.getIdtiposervicios(), servicio.getIdservicio(), servicio.getIdserviciosinstitucion(), usuarios.get(0));
							 
							 if (!resultado[0].equalsIgnoreCase("0")) {
						            LOGGER.error("Error en PL = " + (String) resultado[1]);
						            throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. Error en PL = " + (String) resultado[1]);				          
						      }
							
							if(status == 0) {
								deleteResponseDTO.setStatus(SigaConstants.KO);
								throw new Exception("Error al realizar el borrado logico de un servicio");
							}else if(status == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							}
						}else{ //Borrado fisico al no tener ninguna solicitud, es decir comprobarUsoServicio no devolvio nada.
							//Borramos el registro
							status = pysTiposServiciosExtendsMapper.borradoFisicoServiciosRegistro(servicio, idInstitucion);
							
							if(status == 0) {
								deleteResponseDTO.setStatus(SigaConstants.KO);
								throw new Exception("Error al realizar el borrado fisico de un servicio");
							}else if(status == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							}
						}
					}
					
					LOGGER.info(
							"reactivarBorradoFisicoLogicoServicios() / pysTiposServiciosExtendsMapper.reactivarBorradoFisicoLogicoServicios() -> Salida de pysTiposServiciosExtendsMapper para borrar fisicamente o logicamente o reactivar un servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.reactivarBorradoFisicoLogicoServicios() -> Se ha producido un error al borrar fisicamente o logicamente o reactivar un servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		deleteResponseDTO.setError(error);

		LOGGER.info("reactivarBorradoFisicoLogicoServicios() -> Salida del servicio para borrar fisicamente o logicamente o reactivar un servicio");

		return deleteResponseDTO;
	}

	@Override
	public ListaCodigosPorColegioDTO obtenerCodigosPorColegioServicios(HttpServletRequest request) {
		ListaCodigosPorColegioDTO listaCodigosPorColegioDTO = new ListaCodigosPorColegioDTO();
		Error error = new Error();

		LOGGER.info("obtenerCodigosPorColegioServicios() -> Entrada al servicio para recuperar el listado de codigos en una institucion concreta");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"obtenerCodigosPorColegioServicios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"obtenerCodigosPorColegioServicios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"obtenerCodigosPorColegioServicios() / pysServiciosExtendsMapper.obtenerCodigosPorColegioServicios() -> Entrada a pysTiposProductosExtendsMapper para recuperar el listado de codigos en una institucion concreta");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<String> listaCodigosPorColegio = pysServiciosExtendsMapper.obtenerCodigosPorColegioServicios(idInstitucion);

					LOGGER.info(
							"obtenerCodigosPorColegioServicios() / pysServiciosExtendsMapper.obtenerCodigosPorColegioServicios() -> Salida de pysTiposProductosExtendsMapper para recuperar el listado de codigos en una institucion concreta");

					if (listaCodigosPorColegio != null && listaCodigosPorColegio.size() > 0) {
						listaCodigosPorColegioDTO.setListaCodigosPorColegio(listaCodigosPorColegio);
				}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.obtenerCodigosPorColegioServicios() -> Se ha producido un error al recuperar el listado de codigos en una institucion concreta",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listaCodigosPorColegioDTO.setError(error);

		LOGGER.info("obtenerCodigosPorColegioServicios() -> Salida del servicio para recuperar el listado de codigos en una institucion concreta");

		return listaCodigosPorColegioDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO nuevoServicio(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int status = 0;
		
		LOGGER.info("nuevoServicio() -> Entrada al servicio para crear un servicio (PYS_SERVICIOSINSTITUCION)");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"nuevoServicio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"nuevoServicio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"nuevoServicio() / pysServiciosInstitucionMapper.nuevoServicio() -> Entrada a pysServiciosInstitucionMapper para crear un servicio");

					NewIdDTO idOrdenacion = pysServiciosInstitucionExtendsMapper.getIndiceMaxProducto(servicio, idInstitucion);
					PysServiciosinstitucion servicioInstitucion = new PysServiciosinstitucion();
			
					servicioInstitucion.setIdinstitucion(idInstitucion);
					servicioInstitucion.setIdtiposervicios((short) servicio.getIdtiposervicios());
					servicioInstitucion.setIdservicio((long) servicio.getIdservicio());
					servicioInstitucion.setIdserviciosinstitucion(Long.parseLong(idOrdenacion.getNewId()));
					
					servicioInstitucion.setDescripcion(servicio.getDescripcion());
					servicioInstitucion.setCuentacontable(servicio.getCuentacontable());
					servicioInstitucion.setSolicitarbaja(servicio.getPermitirbaja());
					servicioInstitucion.setSolicitaralta(servicio.getPermitiralta());
					servicioInstitucion.setAutomatico(servicio.getAutomatico());
					servicioInstitucion.setIniciofinalponderado("P");
					servicioInstitucion.setIdconsulta((long) servicio.getIdconsulta());
					
					if(servicio.getIdconsulta() != 0) {
						String criterios = pysServiciosInstitucionExtendsMapper.getCriterioByIdConsulta(idInstitucion, servicio.getIdconsulta());
						criterios = criterios.replace("<SELECT>", "");
						criterios = criterios.replace("</SELECT>", "");
						criterios = criterios.replace("<FROM>", "");
						criterios = criterios.replace("</FROM>", "");
						criterios = criterios.replace("<WHERE>", "");
						criterios = criterios.replace("</WHERE>", "");

						servicioInstitucion.setCriterios(criterios);
					}
									
					servicioInstitucion.setFechabaja(null);
					servicioInstitucion.setFechamodificacion(new Date());
			
					if(servicio.getCodigoext() != null) {
						servicioInstitucion.setCodigoext(servicio.getCodigoext());
					}else {  
						servicioInstitucion.setCodigoext(servicio.getIdtiposervicios() + "|" + servicio.getIdservicio() + "|" + servicioInstitucion.getIdserviciosinstitucion());
					}
					
					servicioInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());

					status = pysServiciosInstitucionMapper.insertSelective(servicioInstitucion);			
					
					if(status == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						throw new Exception("No se ha podido crear el registro en PYS_SERVICIOSINSTITUCION");
					}else if(status == 1) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
					
					if(servicio.getAutomatico() != null) {
						if(servicio.getAutomatico().equals("1") && status == 1) {
							LOGGER.info(
									"nuevoServicio() / ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio() -> Entrada a ejecucionPlsServicios para comenzar el proceso de suscripcion automatica de servicios");
						
							
							 String resultado[] = ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio(idInstitucion, servicio.getIdtiposervicios(), servicio.getIdservicio(), idOrdenacion.getNewId(), usuarios.get(0));
							 
							 if (!resultado[0].equalsIgnoreCase("0")) {
						            LOGGER.error("Error en PL = " + (String) resultado[1]);
						            throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. Error en PL = " + (String) resultado[1]);				          
						      }
							 
								LOGGER.info(
										"nuevoServicio() / ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio() -> Salida de ejecucionPlsServicios, proceso de suscripcion automatica de servicios terminado");
						}
					}

					LOGGER.info(
							"nuevoServicio() / pysServiciosInstitucionMapper.nuevoServicio() -> Salida de pysServiciosInstitucionMapper para crear un servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.nuevoServicio() -> Se ha producido un error al crear un servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		insertResponseDTO.setError(error);

		LOGGER.info("nuevoServicio() -> Salida del servicio para crear un servicio");

		return insertResponseDTO;
	}
	
	@Override
	public DeleteResponseDTO editarServicio(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("editarServicio() -> Entrada al servicio para modificar un servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"editarServicio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"editarServicio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"editarServicio() / pysServiciosInstitucionMapper.editarServicio() -> Entrada a pysServiciosInstitucionMapper para modificar un servicio");
					
						PysServiciosinstitucion servicioInstitucion = new PysServiciosinstitucion();
							
						servicioInstitucion.setIdinstitucion(idInstitucion);
						servicioInstitucion.setIdtiposervicios((short) servicio.getIdtiposervicios());
						servicioInstitucion.setIdservicio((long) servicio.getIdservicio());
						servicioInstitucion.setIdserviciosinstitucion((long) servicio.getIdserviciosinstitucion());					
						
						servicioInstitucion.setDescripcion(servicio.getDescripcion());
						servicioInstitucion.setSolicitarbaja(servicio.getPermitirbaja());
						servicioInstitucion.setSolicitaralta(servicio.getPermitiralta());
						servicioInstitucion.setFechamodificacion(new Date());
						servicioInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
						servicioInstitucion.setCuentacontable(servicio.getCuentacontable());
						
						servicioInstitucion.setIdconsulta((long) servicio.getIdconsulta());
						
						if(servicio.getIdconsulta() != 0 && servicio.getIdconsulta() != servicio.getServiciooriginal().getIdconsulta()) {
							String criterios = pysServiciosInstitucionExtendsMapper.getCriterioByIdConsulta(idInstitucion, servicio.getIdconsulta());
							criterios = criterios.replace("<SELECT>", "");
							criterios = criterios.replace("</SELECT>", "");
							criterios = criterios.replace("<FROM>", "");
							criterios = criterios.replace("</FROM>", "");
							criterios = criterios.replace("<WHERE>", "");
							criterios = criterios.replace("</WHERE>", "");

							servicioInstitucion.setCriterios(criterios);
						}
					
						
						if(servicio.getCodigoext() != null) {
							servicioInstitucion.setCodigoext(servicio.getCodigoext());
						}else {  
							servicioInstitucion.setCodigoext(servicio.getIdtiposervicios() + "|" + servicio.getIdservicio() + "|" + servicioInstitucion.getIdserviciosinstitucion());
						}
					
						
						status = pysServiciosInstitucionMapper.updateByPrimaryKeySelective(servicioInstitucion);
						
						if(servicio.getAutomatico() != null) {
							if(servicio.getAutomatico().equals("1") && status == 1 && servicio.getIdconsulta() != servicio.getServiciooriginal().getIdconsulta()) {
								LOGGER.info(
										"editarServicio() / ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio() -> Entrada a ejecucionPlsServicios para comenzar el proceso de suscripcion automatica de servicios");
							
								
								 String resultado[] = ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio(idInstitucion, servicio.getIdtiposervicios(), servicio.getIdservicio(), String.valueOf(servicio.getIdserviciosinstitucion()), usuarios.get(0));
								 
								 if (!resultado[0].equalsIgnoreCase("0")) {
							            LOGGER.error("Error en PL = " + (String) resultado[1]);
							            throw new Exception("Ha ocurrido un error al ejecutar el proceso de suscripción automática. Error en PL = " + (String) resultado[1]);				          
							      }
								 
									LOGGER.info(
											"editarServicio() / ejecucionPlsServicios.ejecutarPL_SuscripcionAutomaticaServicio() -> Salida de ejecucionPlsServicios, proceso de suscripcion automatica de servicios terminado");
							}
						}
					
					if(status == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						throw new Exception("No se ha podido editar el registro en PYS_SERVICIOSINSTITUCION");
					}else if(status == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"editarServicio() / pysServiciosInstitucionMapper.editarServicio() -> Salida de pysServiciosInstitucionMapper para modificar un servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.editarServicio() -> Se ha producido un error al modificar un servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("editarServicio() -> Salida del servicio para modificar un servicio");

		return deleteResponseDTO;
	}
	
	@Override
	public ServicioDetalleDTO detalleServicio(HttpServletRequest request, int idTipoServicio, int idServicio, int idServiciosInstitucion) {
		ServicioDetalleDTO servicioDetalleDTO = new ServicioDetalleDTO();
		Error error = new Error();

		LOGGER.info("detalleServicio() -> Entrada al servicio para recuperar los detalles del servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"detalleServicio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"detalleServicio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"detalleServicio() / PysServiciosinstitucionExtendsMapper.detalleServicio() -> Entrada a PysServiciosinstitucionExtendsMapper para obtener los detalles del servicio");

					String idioma = usuarios.get(0).getIdlenguaje();
					servicioDetalleDTO = pysServiciosInstitucionExtendsMapper
							.detalleServicio(idTipoServicio, idServicio, idServiciosInstitucion, idInstitucion);

					LOGGER.info(
							"detalleServicio() / PysServiciosinstitucionExtendsMapper.detalleServicio() -> Salida de PysServiciosinstitucionExtendsMapper para obtener los detalles del servicio");
					
					LOGGER.info(
							"detalleServicio() / PysServiciosinstitucionExtendsMapper.obtenerFormasDePagoInternetByServicio() -> Entrada a PysServiciosinstitucionExtendsMapper para obtener las formas de pago de internet");
					List<Integer> listaFormasDePagoInternet = pysServiciosInstitucionExtendsMapper.obtenerFormasDePagoInternetByServicio(idTipoServicio, idServicio, idServiciosInstitucion, idInstitucion);
					
					if(listaFormasDePagoInternet != null && !listaFormasDePagoInternet.isEmpty()) {
						servicioDetalleDTO.setFormasdepagointernet(listaFormasDePagoInternet);
					}
					
					LOGGER.info(
							"detalleServicio() / PysServiciosinstitucionExtendsMapper.obtenerFormasDePagoInternetByServicio() -> Salida a PysServiciosinstitucionExtendsMapper para obtener las formas de pago de internet");
					
					LOGGER.info(
							"detalleServicio() / PysServiciosinstitucionExtendsMapper.obtenerFormasDePagoSecretariaByServicio() -> Entrada a PysServiciosinstitucionExtendsMapper para obtener las formas de pago de secretaria");
					List<Integer> listaFormasDePagoSecretaria = pysServiciosInstitucionExtendsMapper.obtenerFormasDePagoSecretariaByServicio(idTipoServicio, idServicio, idServiciosInstitucion, idInstitucion);
					
					if(listaFormasDePagoSecretaria != null && !listaFormasDePagoSecretaria.isEmpty()) {
						servicioDetalleDTO.setFormasdepagosecretaria(listaFormasDePagoSecretaria);
					}
					
					LOGGER.info(
							"detalleServicio() / PysServiciosinstitucionExtendsMapper.obtenerFormasDePagoSecretariaByServicio() -> Salida a PysServiciosinstitucionExtendsMapper para obtener las formas de pago de secretaria");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.detalleServicio() -> Se ha producido un error al obtener el los detalles del servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		servicioDetalleDTO.setError(error);

		LOGGER.info("detalleServicio() -> Salida del servicio para obtener los detalles del servicio");

		return servicioDetalleDTO;
	}
	
//	@Override
//	public ComboDTO comboCondicionSuscripcion(HttpServletRequest request, int idConsulta) {
//		ComboDTO comboDTO = new ComboDTO();
//		Error error = new Error();
//
//		LOGGER.info("comboCondicionSuscripcion() -> Entrada al servicio para recuperar el combo de condicion de suscripcion");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		try {
//			if (idInstitucion != null) {
//				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
//
//				LOGGER.info(
//						"comboCondicionSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//				LOGGER.info(
//						"comboCondicionSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				if (usuarios != null && !usuarios.isEmpty()) {
//					LOGGER.info(
//							"comboCondicionSuscripcion() / pysServiciosInstitucionExtendsMapper.comboCondicionSuscripcion() -> Entrada a pysServiciosInstitucionExtendsMapper para recuperar el combo de condicion de suscripcion");
//
//					String idioma = usuarios.get(0).getIdlenguaje();
//					List<ComboItem> listaComboCondicionSuscripcion = pysServiciosInstitucionExtendsMapper
//							.comboCondicionSuscripcion(idioma, idInstitucion, idConsulta);
//
//					LOGGER.info(
//							"comboCondicionSuscripcion() / pysServiciosInstitucionExtendsMapper.comboCondicionSuscripcion() -> Salida de pysServiciosInstitucionExtendsMapper para recuperar el combo de condicion de suscripcion");
//
//					if (listaComboCondicionSuscripcion != null && listaComboCondicionSuscripcion.size() > 0) {
//						comboDTO.setCombooItems(listaComboCondicionSuscripcion);
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			LOGGER.error(
//					"TiposServiciosServiceImpl.comboCondicionSuscripcion() -> Se ha producido un error al recuperar el combo de condicion de suscripcion",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//		}
//
//		comboDTO.setError(error);
//
//		LOGGER.info("comboCondicionSuscripcion() -> Salida del servicio para recuperar el combo de condicion de suscripcion");
//
//		return comboDTO;
//	}
	
	@Override
	public ComboDTO comboCondicionSuscripcion(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboCondicionSuscripcion() -> Entrada al servicio para recuperar el combo de condicion de suscripcion");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboCondicionSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboCondicionSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboCondicionSuscripcion() / pysServiciosInstitucionExtendsMapper.comboCondicionSuscripcion() -> Entrada a pysServiciosInstitucionExtendsMapper para recuperar el combo de condicion de suscripcion");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboCondicionSuscripcion = pysServiciosInstitucionExtendsMapper
							.comboCondicionSuscripcion(idioma, idInstitucion);

					LOGGER.info(
							"comboCondicionSuscripcion() / pysServiciosInstitucionExtendsMapper.comboCondicionSuscripcion() -> Salida de pysServiciosInstitucionExtendsMapper para recuperar el combo de condicion de suscripcion");

					if (listaComboCondicionSuscripcion != null && listaComboCondicionSuscripcion.size() > 0) {
						comboDTO.setCombooItems(listaComboCondicionSuscripcion);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.comboCondicionSuscripcion() -> Se ha producido un error al recuperar el combo de condicion de suscripcion",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboCondicionSuscripcion() -> Salida del servicio para recuperar el combo de condicion de suscripcion");

		return comboDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO crearEditarFormaPago(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception{
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		NewIdDTO idServicioInstitucion = new NewIdDTO();
		Error error = new Error();
		int statusInsertFormaPagoInternet = 0;
		int statusInsertFormaPagoSecretaria = 0;
		int statusEdicionFinalServicio = 0;
		int statusDeleteFormasDePagoInternet = 0;
		int statusDeleteFormasDePagoSecretaria = 0;
		

		LOGGER.info("crearEditarFormaPago() -> Entrada al servicio para crear/editar formas de pago y editar los campos restantes del servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"crearEditarFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"crearEditarFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"crearEditarFormaPago() / pysServiciosInstitucionMapper -> Entrada a pysServiciosInstitucionMapper para crear/editar formas de pago");
				
					idServicioInstitucion = (pysServiciosInstitucionExtendsMapper.getIdServicioInstitucion(servicio, idInstitucion));	
				//Si estamos creando un servicio a partir de cero, es decir le hemos dado a nuevo en filtros-servicios (front) recorremos las listas de pago de internet y secretaria insertando cada una de las formas
				if(!servicio.isEditar()) {
					if(servicio.getFormasdepagointernet() != null) {					
						for (int formasdepagointernet : servicio.getFormasdepagointernet()) {
							PysFormapagoservicios formaPagoServicio = new PysFormapagoservicios();
							
							formaPagoServicio.setIdinstitucion(idInstitucion);
							formaPagoServicio.setIdtiposervicios((short) servicio.getIdtiposervicios());
							formaPagoServicio.setIdservicio((long) servicio.getIdservicio());
							formaPagoServicio.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
							formaPagoServicio.setIdformapago((short) formasdepagointernet);
							formaPagoServicio.setInternet("A");
							formaPagoServicio.setFechamodificacion(new Date());
							formaPagoServicio.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							statusInsertFormaPagoInternet = pysFormaPagoServicios.insert(formaPagoServicio);
							
							if(statusInsertFormaPagoInternet == 0) {
								throw new Exception("Error al insertar una forma de pago de internet del servicio");
							}
						}
					}
					if(servicio.getFormasdepagosecretaria() != null)
						for (int formasdepagosecretaria : servicio.getFormasdepagosecretaria()) {
							PysFormapagoservicios formaPagoServicio = new PysFormapagoservicios();
							
							formaPagoServicio.setIdinstitucion(idInstitucion);
							formaPagoServicio.setIdtiposervicios((short) servicio.getIdtiposervicios());
							formaPagoServicio.setIdservicio((long) servicio.getIdservicio());
							formaPagoServicio.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
							formaPagoServicio.setIdformapago((short) formasdepagosecretaria);
							formaPagoServicio.setInternet("S");
							formaPagoServicio.setFechamodificacion(new Date());
							formaPagoServicio.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							statusInsertFormaPagoSecretaria = pysFormaPagoServicios.insert(formaPagoServicio);
							
							if(statusInsertFormaPagoSecretaria == 0) {
								throw new Exception("Error al insertar una forma de pago de secretaria del servicio");
							}
						}
				}else if (servicio.isEditar()) {
					PysFormapagoserviciosKey pysFormaPagoServicioPrimaryKey = new PysFormapagoserviciosKey();
					
					//PRIMERO ELIMINAMOS LAS FORMAS DE PAGO ORIGINALES DE INTERNET Y SECRETARIA
					//INTERNET
					if(servicio.getFormasdepagointernetoriginales() != null) {
						for (int formasdepagointernetoriginales : servicio.getFormasdepagointernetoriginales()) {
							
							pysFormaPagoServicioPrimaryKey.setIdinstitucion(idInstitucion);
							pysFormaPagoServicioPrimaryKey.setIdservicio((long) servicio.getIdservicio());
							pysFormaPagoServicioPrimaryKey.setIdformapago((short) formasdepagointernetoriginales);
							pysFormaPagoServicioPrimaryKey.setIdserviciosinstitucion((long) servicio.getIdserviciosinstitucion());
							pysFormaPagoServicioPrimaryKey.setIdtiposervicios((short) servicio.getIdtiposervicios());
								
								statusDeleteFormasDePagoInternet = pysFormaPagoServicios.deleteByPrimaryKey(pysFormaPagoServicioPrimaryKey);
								
								if(statusDeleteFormasDePagoInternet == 0) {
									throw new Exception("Error al eliminar una forma de pago de internet del servicio");
								}						
						}
					}
					
					//SECRETARIA
					if(servicio.getFormasdepagosecretariaoriginales() != null) {
						for (int formasdepagosecretariasoriginales : servicio.getFormasdepagosecretariaoriginales()) {
							
							pysFormaPagoServicioPrimaryKey.setIdinstitucion(idInstitucion);
							pysFormaPagoServicioPrimaryKey.setIdservicio((long) servicio.getIdservicio());
							pysFormaPagoServicioPrimaryKey.setIdformapago((short) formasdepagosecretariasoriginales);
							pysFormaPagoServicioPrimaryKey.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
							pysFormaPagoServicioPrimaryKey.setIdtiposervicios((short) servicio.getIdtiposervicios());
								
								statusDeleteFormasDePagoSecretaria = pysFormaPagoServicios.deleteByPrimaryKey(pysFormaPagoServicioPrimaryKey);
								
								if(statusDeleteFormasDePagoSecretaria == 0) {
									throw new Exception("Error al eliminar una forma de pago de secretaria del servicio");
								}							
						}
					}
					
					
					//SEGUNDO INSERTAMOS LAS FORMAS DE PAGO DE INTERNET Y SECRETARIA
					//INTERNET
					if(servicio.getFormasdepagointernet() != null) {	
						for(int formasdepagointernet : servicio.getFormasdepagointernet()) {
			
							PysFormapagoservicios formaPagoServicio = new PysFormapagoservicios();
								
							formaPagoServicio.setIdinstitucion(idInstitucion);
							formaPagoServicio.setIdtiposervicios((short) servicio.getIdtiposervicios());
							formaPagoServicio.setIdservicio((long) servicio.getIdservicio());
							formaPagoServicio.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
							formaPagoServicio.setIdformapago((short) formasdepagointernet);
							formaPagoServicio.setInternet("A");
							formaPagoServicio.setFechamodificacion(new Date());
							formaPagoServicio.setUsumodificacion(usuarios.get(0).getIdusuario());
								
								statusInsertFormaPagoInternet = pysFormaPagoServicios.insert(formaPagoServicio);
								
								if(statusInsertFormaPagoInternet == 0) {
									throw new Exception("Error al insertar una forma de pago de internet del servicio");
								}
						}
					}
					
					//SECRETARIA
					if(servicio.getFormasdepagosecretaria() != null) {	
						for(int formasdepagosecretaria : servicio.getFormasdepagosecretaria()) {
				
							PysFormapagoservicios formaPagoServicio = new PysFormapagoservicios();
								
							formaPagoServicio.setIdinstitucion(idInstitucion);
							formaPagoServicio.setIdtiposervicios((short) servicio.getIdtiposervicios());
							formaPagoServicio.setIdservicio((long) servicio.getIdservicio());
							formaPagoServicio.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
							formaPagoServicio.setIdformapago((short) formasdepagosecretaria);
							formaPagoServicio.setInternet("S");
							formaPagoServicio.setFechamodificacion(new Date());
							formaPagoServicio.setUsumodificacion(usuarios.get(0).getIdusuario());
								
								statusInsertFormaPagoSecretaria = pysFormaPagoServicios.insert(formaPagoServicio);
								
								if(statusInsertFormaPagoSecretaria == 0) {
									throw new Exception("Error al insertar una forma de pago de secretaria del servicio");
								}					
						}
					}
					
				}
				
				//Actualizamos la informacion restante la cual no fue introducida al crear el producto en la tarjeta datos generales
				PysServiciosinstitucion servicioInstitucion = new PysServiciosinstitucion();
				
				servicioInstitucion.setIdinstitucion(idInstitucion);
				servicioInstitucion.setIdtiposervicios((short) servicio.getIdtiposervicios());
				servicioInstitucion.setIdservicio((long) servicio.getIdservicio());
				servicioInstitucion.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
							
				servicioInstitucion.setNofacturable(servicio.getNofacturable());
				servicioInstitucion.setIdtipoiva(servicio.getIdtipoiva());
				servicioInstitucion.setFacturacionponderada(servicio.getFacturacionponderada());
				servicioInstitucion.setIniciofinalponderado(servicio.getIniciofinalponderado());
				
				//SOLO SI LA OPCION INICIOFINALPONDERADO ES P, LLAMAR A PL?
				
				servicioInstitucion.setFechamodificacion(new Date());
				servicioInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
				
				statusEdicionFinalServicio = pysServiciosInstitucionMapper.updateByPrimaryKeySelective(servicioInstitucion);
				
				if(statusEdicionFinalServicio == 0) {
					throw new Exception("Error al insertar la informacion restante de la tarjeta formas de pago en el servicio");
				}
				
					
					if(statusInsertFormaPagoInternet == 0 || statusInsertFormaPagoSecretaria == 0 || statusEdicionFinalServicio == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
					}else if(statusInsertFormaPagoInternet == 1 && statusInsertFormaPagoSecretaria == 1 && statusEdicionFinalServicio == 0) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"crearEditarFormaPago() / pysServiciosInstitucionMapper-> Salida de pysServiciosInstitucionMapper para crear/editar formas de pago y editar los campos restantes del servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.crearEditarFormaPago() -> Se ha producido un error al crear/editar formas de pago y editar los campos restantes del servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("crearEditarFormaPago() -> Salida del servicio para crear/editar formas de pago y editar los campos restantes del servicio");

		return insertResponseDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO borrarSuscripcionesBajas(BorrarSuscripcionBajaItem borrarSuscripcionBajaItem, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		
		LOGGER.info("borrarSuscripcionesBajas() -> Entrada al servicio para borrar suscripciones o bajas del servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"borrarSuscripcionesBajas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"borrarSuscripcionesBajas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"borrarSuscripcionesBajas() / ejecucionPlsServicios.ejecutarPL_ServiciosAutomaticosProcesoEliminarSuscripcion() -> Entrada al servicio para borrar suscripciones o bajas del servicio");
					
							String resultado[] = ejecucionPlsServicios.ejecutarPL_ServiciosAutomaticosProcesoEliminarSuscripcion(idInstitucion, borrarSuscripcionBajaItem, usuarios.get(0));
							 
							 if (!resultado[0].equalsIgnoreCase("0")) {
								 deleteResponseDTO.setStatus(SigaConstants.KO);					       
						      }else if(resultado[0].equalsIgnoreCase("0")){
						    	  deleteResponseDTO.setStatus(SigaConstants.OK);	
						      }
										
					LOGGER.info(
							"borrarSuscripcionesBajas() / ejecucionPlsServicios.ejecutarPL_ServiciosAutomaticosProcesoEliminarSuscripcion() -> Salida al servicio para borrar suscripciones o bajas del servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.borrarSuscripcionesBajas() -> Se ha producido un error al borrar suscripciones o bajas del servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		deleteResponseDTO.setError(error);

		LOGGER.info("borrarSuscripcionesBajas() -> Salida del servicio para borrar suscripciones o bajas del servicio");

		return deleteResponseDTO;
	}
}
