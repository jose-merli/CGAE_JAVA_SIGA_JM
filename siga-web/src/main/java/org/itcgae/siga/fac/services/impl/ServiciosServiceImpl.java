package org.itcgae.siga.fac.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.BorrarSuscripcionBajaItem;
import org.itcgae.siga.DTO.fac.ComboPreciosSuscripcionDTO;
import org.itcgae.siga.DTO.fac.ComboPreciosSuscripcionItem;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosDTO;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosItem;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTO.fac.TiposServiciosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO2;
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
import org.itcgae.siga.db.entities.PysPreciosservicios;
import org.itcgae.siga.db.entities.PysPreciosserviciosKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysServicios;
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
import org.itcgae.siga.db.mappers.PysFormapagoserviciosMapper;
import org.itcgae.siga.db.mappers.PysPreciosserviciosMapper;
import org.itcgae.siga.db.mappers.PysServiciosinstitucionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysPreciosserviciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.IServiciosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	@Autowired
	private PysPreciosserviciosExtendsMapper pysPreciosServiciosExtendsMapper;
	
	@Autowired
	private PysPreciosserviciosMapper pysPreciosServiciosMapper;
	
	@Autowired
	private PySTipoFormaPagoExtendsMapper pysTipoFormaPagoExtendsMapper;
	
	
	//Servicio que devuelve la informacion necesaria para la tabla en Facturacion --> Servicios.
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
						if(servicio.getValorminimo() != null) {
						servicio.setPrecioperiodicidad(String.valueOf(servicio.getValorminimo()) + "/" + servicio.getPeriodominimo() + " - " + String.valueOf(servicio.getValormaximo()) + "/" + servicio.getPeriodomaximo());
						}else {
							servicio.setPrecioperiodicidad("Sin precio");						}
						
					
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

	//Servicio que da de baja logica o lo reactiva (le establece fechabaja en la columna BD a null en caso de reactivar o la actual en caso de baja) al servicio en caso de que tenga usos existentes o lo borra fisicamente (elimina el registro de la bd) en caso de que no tenga ninguna.
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO reactivarBorradoFisicoLogicoServicios(ListaServiciosDTO listadoServicios, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		int statusBorradoFormasDePagoServicio = 0;
		int statusBorradoPreciosServicio= 0;
		IdPeticionDTO idPeticionDTO = new IdPeticionDTO();
		
		

		LOGGER.info("reactivarBorradoFisicoLogicoServicios() -> Entrada al servicio para borrar fisicamente o logicamente o reactivar un servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

	
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
							
							//Borramos las formas de pago del servicio (si es que las tiene)							
							if(servicio.getNoFacturable().equals("0")) {
								statusBorradoFormasDePagoServicio = pysTipoFormaPagoExtendsMapper.borradoFisicoFormasPagoByServicio(servicio, idInstitucion);
								
								if(statusBorradoFormasDePagoServicio == 0) {
									throw new Exception("No se pudo realizar el borrado de las formas de pago del servicio");
								}else if(statusBorradoFormasDePagoServicio == 1) {
									LOGGER.info(
											"reactivarBorradoFisicoLogicoServicios() / pysTipoFormaPagoExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Borrado de las formas de pago del servicio realizado con exito");
								}
							}
							
							//Borramos los precios del servicio (siempre tendra minimo uno ya que todos los servicios tienen un precio por defecto)
							statusBorradoPreciosServicio = pysPreciosServiciosExtendsMapper.borradoFisicoPreciosByServicio(servicio, idInstitucion);
							
							if(statusBorradoPreciosServicio == 0) {
								throw new Exception("No se pudo realizar el borrado de precios del servicio");
							}else if(statusBorradoPreciosServicio == 1) {
								LOGGER.info(
										"reactivarBorradoFisicoLogicoServicios() / pysTipoFormaPagoExtendsMapper.borradoFisicoPreciosByServicio() -> Borrado de los precios del servicio realizado con exito");
							}
							
							//Borramos el registro en pys_serviciosinstitucion
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
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO nuevoServicio(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int status = 0;
		int statusPrecioPorDefecto = 0;
		
		LOGGER.info("nuevoServicio() -> Entrada al servicio para crear un servicio (PYS_SERVICIOSINSTITUCION)");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		
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
					servicioInstitucion.setNofacturable("1");
					
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
					
					//Por defecto, al crear un Servicio, se creará una línea de Precio CERO, periodo MENSUAL y sin condiciones.
					if(status == 1) {
						LOGGER.info(
								"nuevoServicio() / pysPreciosServiciosMapper -> Entrada a pysPreciosServiciosMapper para crear el precio por defecto del servicio");
						
						PysPreciosservicios pysPreciosServicios = new PysPreciosservicios();
						
						pysPreciosServicios.setIdinstitucion(idInstitucion);
						pysPreciosServicios.setIdtiposervicios((short) servicio.getIdtiposervicios());
						pysPreciosServicios.setIdservicio ((long) servicio.getIdservicio());
						pysPreciosServicios.setIdserviciosinstitucion(Long.parseLong(idOrdenacion.getNewId()));
						pysPreciosServicios.setIdperiodicidad((short) 1);//Mensual
						
						NewIdDTO idOrdenacionPreciosServicios = pysPreciosServiciosExtendsMapper.selectMaxIdPrecioServicio(idInstitucion, pysPreciosServicios.getIdtiposervicios(), pysPreciosServicios.getIdservicio(), pysPreciosServicios.getIdserviciosinstitucion(), pysPreciosServicios.getIdperiodicidad());
						pysPreciosServicios.setIdpreciosservicios(Short.parseShort(idOrdenacionPreciosServicios.getNewId())); 
						
						pysPreciosServicios.setValor(new BigDecimal(0));//A cero el precio por defecto
						pysPreciosServicios.setCriterios("SELECT IDINSTITUCION, IDPERSONA FROM CEN_CLIENTE WHERE IDINSTITUCION = "+idInstitucion+"  AND IDPERSONA = @IDPERSONA@");//PROVISIONAL
						pysPreciosServicios.setFechamodificacion(new Date());
						pysPreciosServicios.setUsumodificacion(usuarios.get(0).getIdusuario());
						pysPreciosServicios.setPordefecto("1");
						pysPreciosServicios.setDescripcion("Precio por defecto");
					
						statusPrecioPorDefecto = pysPreciosServiciosMapper.insertSelective(pysPreciosServicios);
						
						if(statusPrecioPorDefecto == 0) {
							insertResponseDTO.setStatus(SigaConstants.KO);
							throw new Exception("No se ha podido crear el precio por defecto para el servicio");
						}else if(statusPrecioPorDefecto == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
						
						LOGGER.info(
								"nuevoServicio() / pysPreciosServiciosMapper -> Salida de pysPreciosServiciosMapper para crear el precio por defecto del servicio");
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
						servicioInstitucion.setAutomatico(servicio.getAutomatico());
						
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
							if(servicio.getAutomatico().equals("1") && status == 1) {
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
	@Transactional(rollbackFor = Exception.class)
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
		

		insertResponseDTO.setError(error);

		LOGGER.info("crearEditarFormaPago() -> Salida del servicio para crear/editar formas de pago y editar los campos restantes del servicio");

		return insertResponseDTO;
	}

	@Override
	public DeleteResponseDTO borrarSuscripcionesBajas(BorrarSuscripcionBajaItem borrarSuscripcionBajaItem, HttpServletRequest request) {
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
	
	@Override
	public FichaTarjetaPreciosDTO detalleTarjetaPrecios(HttpServletRequest request, ServicioDetalleDTO servicio) {
		FichaTarjetaPreciosDTO fichaTarjetaPreciosDTO = new FichaTarjetaPreciosDTO();
		Error error = new Error();

		LOGGER.info("detalleTarjetaPrecios() -> Entrada al servicio para recuperar los detalles necesarios para la tarjeta precios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"detalleTarjetaPrecios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"detalleTarjetaPrecios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"detalleTarjetaPrecios() / pysPreciosServiciosExtendsMapper.detalleTarjetaPrecios() -> Entrada a pysPreciosServiciosExtendsMapper para recuperar los detalles necesarios para la tarjeta precios");
					String idioma = usuarios.get(0).getIdlenguaje();
					NewIdDTO idServicioInstitucion = pysServiciosInstitucionExtendsMapper.getIdServicioInstitucion(servicio, idInstitucion);	
				
					List<FichaTarjetaPreciosItem> fichaTarjetaPreciosLista = new ArrayList<FichaTarjetaPreciosItem>();
					if(idServicioInstitucion != null) { 
						fichaTarjetaPreciosLista = pysPreciosServiciosExtendsMapper
							.detalleTarjetaPrecios(servicio.getIdtiposervicios(), servicio.getIdservicio(), Integer.parseInt(idServicioInstitucion.getNewId()), idInstitucion, idioma);
					}
					else {
						fichaTarjetaPreciosLista = pysPreciosServiciosExtendsMapper
								.detalleTarjetaPrecios(servicio.getIdtiposervicios(), servicio.getIdservicio(), servicio.getIdserviciosinstitucion(), idInstitucion, idioma);
					}
					
					for (FichaTarjetaPreciosItem fichaTarjetaPreciosItem : fichaTarjetaPreciosLista) {
						fichaTarjetaPreciosItem.setIdperiodicidadoriginal(fichaTarjetaPreciosItem.getIdperiodicidad());
						fichaTarjetaPreciosItem.setPrecio(String.valueOf(fichaTarjetaPreciosItem.getValor()));					
					}

					LOGGER.info(
							"detalleTarjetaPrecios() / pysPreciosServiciosExtendsMapper.detalleTarjetaPrecios() -> Salida de pysPreciosServiciosExtendsMapper para recuperar los detalles necesarios para la tarjeta precios");
					
					if (fichaTarjetaPreciosLista != null && fichaTarjetaPreciosLista.size() > 0) {
						fichaTarjetaPreciosDTO.setFichaTarjetaPreciosItem(fichaTarjetaPreciosLista);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.detalleTarjetaPrecios() -> Se ha producido un error al obtener los detalles necesarios para la tarjeta precios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		fichaTarjetaPreciosDTO.setError(error);

		LOGGER.info("detalleTarjetaPrecios() -> Salida del servicio para obtener los detalles necesarios para la tarjeta precios");

		return fichaTarjetaPreciosDTO;
	}
	
	@Override
	public ComboDTO comboPeriodicidad(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboPeriodicidad() -> Entrada al servicio para recuperar el combo de periodicidades");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboPeriodicidad() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboPeriodicidad() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboPeriodicidad() / PysPreciosserviciosExtendsMapper.comboPeriodicidad() -> Entrada a PysPreciosserviciosExtendsMapper para recuperar el combo de periodicidades");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboPeriodicidades = pysPreciosServiciosExtendsMapper.comboPeriodicidad(idioma);

					LOGGER.info(
							"comboPeriodicidad() / PysPreciosserviciosExtendsMapper.comboPeriodicidad() -> Salida de PysPreciosserviciosExtendsMapper para recuperar el combo de periodicidades");

					if (listaComboPeriodicidades != null && listaComboPeriodicidades.size() > 0) {
						comboDTO.setCombooItems(listaComboPeriodicidades);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.comboPeriodicidad() -> Se ha producido un error al recuperar el combo de periodicidades",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboPeriodicidad() -> Salida del servicio para recuperar el combo de periodicidades");

		return comboDTO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO crearEditarPrecios(FichaTarjetaPreciosDTO listaPrecios, HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int status = 0;
		int statusEliminacionPrecio = 0;
		

		LOGGER.info("crearEditarPrecios() -> Entrada al servicio para crear/editar precios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"crearEditarPrecios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"crearEditarPrecios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"crearEditarPrecios() / pysPreciosServiceMapper.crearEditarPrecios() -> Entrada a pysPreciosServiceMapper para crear/editar precios");

				for (FichaTarjetaPreciosItem precio : listaPrecios.getFichaTarjetaPreciosItem()) {
					if(precio.getNuevo().equals("1")) {
						PysPreciosservicios pysPreciosServicios = new PysPreciosservicios();

						pysPreciosServicios.setIdinstitucion(idInstitucion);
						pysPreciosServicios.setIdtiposervicios((short) precio.getIdtiposervicios());
						pysPreciosServicios.setIdservicio ((long) precio.getIdservicio());
						pysPreciosServicios.setIdserviciosinstitucion((long) precio.getIdserviciosinstitucion());
						pysPreciosServicios.setIdperiodicidad((short) precio.getIdperiodicidad());

						NewIdDTO idOrdenacionPreciosServicios = pysPreciosServiciosExtendsMapper.selectMaxIdPrecioServicio(idInstitucion, pysPreciosServicios.getIdtiposervicios(), pysPreciosServicios.getIdservicio(), pysPreciosServicios.getIdserviciosinstitucion(), pysPreciosServicios.getIdperiodicidad());
						pysPreciosServicios.setIdpreciosservicios(Short.parseShort(idOrdenacionPreciosServicios.getNewId()));


						if(precio.getPrecio().contains("€")) {
							String valor = precio.getPrecio();
							valor = valor.replace("€","");
							precio.setPrecio(valor);
						}

						if(precio.getPrecio().contains(",")) {
							String valor = precio.getPrecio();
							valor = valor.replace(",",".");
							precio.setPrecio(valor);
						}

						pysPreciosServicios.setValor(new BigDecimal(Double.valueOf(precio.getPrecio())));

						if(precio.getIdcondicion() != 0) {
							String criterios = pysServiciosInstitucionExtendsMapper.getCriterioByIdConsulta(idInstitucion, precio.getIdcondicion());
							criterios = criterios.replace("<SELECT>", "");
							criterios = criterios.replace("</SELECT>", "");
							criterios = criterios.replace("<FROM>", "");
							criterios = criterios.replace("</FROM>", "");
							criterios = criterios.replace("<WHERE>", "");
							criterios = criterios.replace("</WHERE>", "");

							pysPreciosServicios.setCriterios(criterios);
						}

						pysPreciosServicios.setFechamodificacion(new Date());
						pysPreciosServicios.setUsumodificacion(usuarios.get(0).getIdusuario());
						pysPreciosServicios.setPordefecto("0");
						pysPreciosServicios.setDescripcion(precio.getDescripcionprecio());
						pysPreciosServicios.setIdconsulta((long) precio.getIdcondicion());

						if(Double.parseDouble(precio.getPrecio()) >= 0 && precio.getIdperiodicidad() != 0 && precio.getIdcondicion() != 0) {
						status = pysPreciosServiciosMapper.insertSelective(pysPreciosServicios);
						}else {

							if(Integer.parseInt(precio.getPrecio()) < 0) {
								LOGGER.info(
										"crearEditarPrecios() / El precio no puede ser menor de 0");
							}

							if(precio.getIdperiodicidad() == 0) {
								LOGGER.info(
										"crearEditarPrecios() / El precio tiene que tener una periodicidad obligatoriamente");
							}

							if(precio.getIdcondicion() == 0) {
								LOGGER.info(
										"crearEditarPrecios() / El precio tiene que tener una condicion obligatoriamente excepto si es el por defecto creado al crear el servicio en tarjeta datos generales");
							}
						}

						if(status == 0) {
							insertResponseDTO.setStatus(SigaConstants.KO);
						}else if(status == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
					}else {

						//Al editar un precio debido a que la periodicidad es PK lo eliminaremos e insertaremos de nuevo
						PysPreciosserviciosKey preciosServiciosKey = new PysPreciosserviciosKey();

						preciosServiciosKey.setIdinstitucion(idInstitucion);
						preciosServiciosKey.setIdperiodicidad((short) precio.getIdperiodicidadoriginal());
						preciosServiciosKey.setIdpreciosservicios((short) precio.getIdpreciosservicios());
						preciosServiciosKey.setIdservicio((long) precio.getIdservicio());
						preciosServiciosKey.setIdserviciosinstitucion((long) precio.getIdserviciosinstitucion());
						preciosServiciosKey.setIdtiposervicios((short) precio.getIdtiposervicios());

						statusEliminacionPrecio = pysPreciosServiciosMapper.deleteByPrimaryKey(preciosServiciosKey);

						if(statusEliminacionPrecio == 1) {
							//Al hacer la insercion se distinguira entre el precio por defecto y el resto ya que en el precio por defecto no hay condicion.
							if(precio.getPordefecto().equals("1")){
								PysPreciosservicios pysPreciosServicios = new PysPreciosservicios();

								pysPreciosServicios.setIdinstitucion(idInstitucion);
								pysPreciosServicios.setIdtiposervicios((short) precio.getIdtiposervicios());
								pysPreciosServicios.setIdservicio ((long) precio.getIdservicio());
								pysPreciosServicios.setIdserviciosinstitucion((long) precio.getIdserviciosinstitucion());
								pysPreciosServicios.setIdperiodicidad((short) precio.getIdperiodicidad());

								NewIdDTO idOrdenacionPreciosServicios = pysPreciosServiciosExtendsMapper.selectMaxIdPrecioServicio(idInstitucion, pysPreciosServicios.getIdtiposervicios(), pysPreciosServicios.getIdservicio(), pysPreciosServicios.getIdserviciosinstitucion(), pysPreciosServicios.getIdperiodicidad());
								pysPreciosServicios.setIdpreciosservicios(Short.parseShort(idOrdenacionPreciosServicios.getNewId()));

								if(precio.getPrecio().contains("€")) {
									String valor = precio.getPrecio();
									valor = valor.replace("€","");
									precio.setPrecio(valor);
								}

								if(precio.getPrecio().contains(",")) {
									String valor = precio.getPrecio();
									valor = valor.replace(",",".");
									precio.setPrecio(valor);
								}

								pysPreciosServicios.setValor(new BigDecimal(Double.valueOf(precio.getPrecio())));
								pysPreciosServicios.setCriterios("SELECT IDINSTITUCION, IDPERSONA FROM CEN_CLIENTE WHERE IDINSTITUCION = "+idInstitucion+"  AND IDPERSONA = @IDPERSONA@");//PROVISIONAL
								pysPreciosServicios.setFechamodificacion(new Date());
								pysPreciosServicios.setUsumodificacion(usuarios.get(0).getIdusuario());
								pysPreciosServicios.setPordefecto("1");
								pysPreciosServicios.setDescripcion(precio.getDescripcionprecio());

								if(Double.parseDouble(precio.getPrecio()) >= 0 && precio.getIdperiodicidad() != 0) {
									status = pysPreciosServiciosMapper.insertSelective(pysPreciosServicios);
								}else {

									if(Integer.parseInt(precio.getPrecio()) < 0) {
										LOGGER.info(
												"crearEditarPrecios() / El precio no puede ser menor de 0");
									}

									if(precio.getIdperiodicidad() == 0) {
										LOGGER.info(
												"crearEditarPrecios() / El precio tiene que tener una periodicidad obligatoriamente");
									}

								}

								if(status == 0) {
									insertResponseDTO.setStatus(SigaConstants.KO);
								}else if(status == 1) {
									insertResponseDTO.setStatus(SigaConstants.OK);
								}

							}else {
								PysPreciosservicios pysPreciosServicios = new PysPreciosservicios();

								pysPreciosServicios.setIdinstitucion(idInstitucion);
								pysPreciosServicios.setIdtiposervicios((short) precio.getIdtiposervicios());
								pysPreciosServicios.setIdservicio ((long) precio.getIdservicio());
								pysPreciosServicios.setIdserviciosinstitucion((long) precio.getIdserviciosinstitucion());
								pysPreciosServicios.setIdperiodicidad((short) precio.getIdperiodicidad());


								NewIdDTO idOrdenacionPreciosServicios = pysPreciosServiciosExtendsMapper.selectMaxIdPrecioServicio(idInstitucion, pysPreciosServicios.getIdtiposervicios(), pysPreciosServicios.getIdservicio(), pysPreciosServicios.getIdserviciosinstitucion(), pysPreciosServicios.getIdperiodicidad());
								pysPreciosServicios.setIdpreciosservicios(Short.parseShort(idOrdenacionPreciosServicios.getNewId()));

								if(precio.getPrecio().contains("€")) {
									String valor = precio.getPrecio();
									valor = valor.replace("€","");
									precio.setPrecio(valor);
								}

								if(precio.getPrecio().contains(",")) {
									String valor = precio.getPrecio();
									valor = valor.replace(",",".");
									precio.setPrecio(valor);
								}

								pysPreciosServicios.setValor(new BigDecimal(Double.valueOf(precio.getPrecio())));

								if(precio.getIdcondicion() != 0) {
									String criterios = pysServiciosInstitucionExtendsMapper.getCriterioByIdConsulta(idInstitucion, precio.getIdcondicion());
									criterios = criterios.replace("<SELECT>", "");
									criterios = criterios.replace("</SELECT>", "");
									criterios = criterios.replace("<FROM>", "");
									criterios = criterios.replace("</FROM>", "");
									criterios = criterios.replace("<WHERE>", "");
									criterios = criterios.replace("</WHERE>", "");

									pysPreciosServicios.setCriterios(criterios);
								}

								pysPreciosServicios.setFechamodificacion(new Date());
								pysPreciosServicios.setUsumodificacion(usuarios.get(0).getIdusuario());
								pysPreciosServicios.setDescripcion(precio.getDescripcionprecio());
								pysPreciosServicios.setIdconsulta((long) precio.getIdcondicion());

								if(Double.parseDouble(precio.getPrecio()) >= 0 && precio.getIdperiodicidad() != 0 && precio.getIdcondicion() != 0) {
									status = pysPreciosServiciosMapper.insertSelective(pysPreciosServicios);
									}else {

										if(Integer.parseInt(precio.getPrecio()) < 0) {
											LOGGER.info(
													"crearEditarPrecios() / El precio no puede ser menor de 0");
										}

										if(precio.getIdperiodicidad() == 0) {
											LOGGER.info(
													"crearEditarPrecios() / El precio tiene que tener una periodicidad obligatoriamente");
										}

										if(precio.getIdcondicion() == 0) {
											LOGGER.info(
													"crearEditarPrecios() / El precio tiene que tener una condicion obligatoriamente excepto si es el por defecto creado al crear el servicio en tarjeta datos generales");
										}
									}

								if(status == 0) {
									insertResponseDTO.setStatus(SigaConstants.KO);
								}else if(status == 1) {
									insertResponseDTO.setStatus(SigaConstants.OK);
								}
							}
						}else {
							insertResponseDTO.setStatus(SigaConstants.KO);
						}

					}

				}

				LOGGER.info(
						"crearEditarPrecios() / pysPreciosServiceMapper.crearEditarPrecios() -> Salida de pysPreciosServiceMapper para crear/editar precios");
			}

		}
		
		insertResponseDTO.setError(error);

		LOGGER.info("crearEditarPrecios() -> Salida del servicio para crear/editar precios");

		return insertResponseDTO;
	}
	
	@Override
	public DeleteResponseDTO eliminarPrecio(FichaTarjetaPreciosDTO precios, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("eliminarPrecio() -> Entrada al servicio para eliminar precios del servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"eliminarPrecio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"eliminarPrecio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"eliminarPrecio() / pysPreciosServiceMapper.eliminarPrecio() -> Entrada a pysPreciosServiceMapper para eliminar precios del servicio");

					for (FichaTarjetaPreciosItem precio : precios.getFichaTarjetaPreciosItem()) {
						PysPreciosserviciosKey preciosServiciosKey = new PysPreciosserviciosKey();
						
						preciosServiciosKey.setIdinstitucion(idInstitucion);
						preciosServiciosKey.setIdperiodicidad((short) precio.getIdperiodicidad());
						preciosServiciosKey.setIdpreciosservicios((short) precio.getIdpreciosservicios());
						preciosServiciosKey.setIdservicio((long) precio.getIdservicio());
						preciosServiciosKey.setIdserviciosinstitucion((long) precio.getIdserviciosinstitucion());
						preciosServiciosKey.setIdtiposervicios((short) precio.getIdtiposervicios());
					
						status = pysPreciosServiciosMapper.deleteByPrimaryKey(preciosServiciosKey);

					}
					
					if(status == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}
					

					LOGGER.info(
							"eliminarPrecio() / pysPreciosServiceMapper.eliminarPrecio() -> Salida de pysPreciosServiceMapper para eliminar precios del servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiciosServiceImpl.eliminarPrecio() -> Se ha producido un error al eliminar precios del servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		deleteResponseDTO.setError(error);

		LOGGER.info("eliminarPrecio() -> Salida del servicio para eliminar precios del servicio");

		return deleteResponseDTO;
	}
	
	@Override
	public ComboPreciosSuscripcionDTO comboPreciosServPers(HttpServletRequest request, Long idPersona, int idServicio, int idTipoServicios, int idServiciosInstitucion) {
		ComboPreciosSuscripcionDTO comboDTO = new ComboPreciosSuscripcionDTO();
		Error error = new Error();

		LOGGER.info("comboCondicionSuscripcion() -> Entrada al servicio para recuperar el combo de condicion de precios del servicio con anotaciones para saber si la persona cumple los criterios o no");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboPreciosServPers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboPreciosServPers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					
					List<ComboPreciosSuscripcionItem> listaComboPrecios = null;
					String idioma = usuarios.get(0).getIdlenguaje();
					if(idPersona != null) {
						LOGGER.info(
								"comboPreciosServPers() / pysPreciosServiciosExtendsMapper.comboPreciosServPers() -> Entrada a pysPreciosserviciosExtendsMapper para recuperar el combo de precios del servicio con anotaciones para saber si la persona cumple los criterios o no");

						listaComboPrecios = pysPreciosServiciosExtendsMapper
							.comboPreciosServPers(idioma, idInstitucion, idPersona.toString(), String.valueOf(idServicio), String.valueOf(idTipoServicios), String.valueOf(idServiciosInstitucion));
						
						LOGGER.info(
								"comboPreciosServPers() / pysPreciosServiciosExtendsMapper.comboPreciosServPers() -> Salida de pysPreciosserviciosExtendsMapper para recuperar el combo de precios del servicio con anotaciones para saber si la persona cumple los criterios o no");

						
						for(ComboPreciosSuscripcionItem precio : listaComboPrecios) {
							if(precio.getValido() != null){
								String val = null;
								try {
									val = pysPreciosServiciosExtendsMapper
									.checkCriterioPrecio(precio.getValido());
								} catch (Exception e) {
									LOGGER.error(
											"TiposServiciosServiceImpl.comboPreciosServPers() -> Se ha producido un error al ejecutar la consulta de criterio de un precio",
											e);
									val = null;
								}
								if(val != null) {
									precio.setValido("1");
								}
								else {
									precio.setValido("0");
								}
							}
							else {
								precio.setValido("1");
							}
						}
					}

					
					if (listaComboPrecios != null && listaComboPrecios.size() > 0) {
						comboDTO.setPreciosSuscripcionItem(listaComboPrecios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.comboPreciosServPers() -> Se ha producido un error al recuperar el combo de precios del servicio con anotaciones para saber si la persona cumple los criterios o no",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboPreciosServPers() -> Salida del servicio para recuperar el combo de condicion de precios del servicio con anotaciones para saber si la persona cumple los criterios o no");

		return comboDTO;
	}
}
