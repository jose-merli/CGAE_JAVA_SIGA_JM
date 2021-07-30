package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposServiciosExtendsMapper;
import org.itcgae.siga.fac.services.IServiciosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiciosServiceImpl implements IServiciosService {
	
	private Logger LOGGER = Logger.getLogger(ProductosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private PySTiposServiciosExtendsMapper pysTiposServiciosExtendsMapper;
	
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

}
