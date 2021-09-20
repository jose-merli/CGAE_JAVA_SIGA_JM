package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
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
								listaServiciosProcesada.get(listaServiciosProcesada.size() - 1).setFormapago("4");
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
	public DeleteResponseDTO reactivarBorradoFisicoLogicoServicios(ListaServiciosDTO listadoServicios, HttpServletRequest request) {
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
							if(status == 0) {
								deleteResponseDTO.setStatus(SigaConstants.KO);
							}else if(status == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							}
						}else{ //Borrado fisico al no tener ninguna solicitud, es decir comprobarUsoServicio no devolvio nada.
							//Borramos el registro
							status = pysTiposServiciosExtendsMapper.borradoFisicoServiciosRegistro(servicio, idInstitucion);
							
							if(status == 0) {
								deleteResponseDTO.setStatus(SigaConstants.KO);
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
					servicioInstitucion.setIniciofinalponderado("I");//INICIOFINALPONDERADO??
					//Falta condicion de suscripcion servicioInstitucion.setIdconsulta((long) servicio.getIdconsulta());
					
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
}
