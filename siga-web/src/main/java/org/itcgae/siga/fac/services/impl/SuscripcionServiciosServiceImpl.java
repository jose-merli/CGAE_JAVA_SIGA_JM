package org.itcgae.siga.fac.services.impl;

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
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.fac.services.ISuscripcionServiciosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SuscripcionServiciosServiceImpl implements ISuscripcionServiciosService {

	private Logger LOGGER = Logger.getLogger(SuscripcionServiciosServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private GestionFichaCompraSuscripcionServiceImpl gestionFichaCompraSuscripcionServiceImpl;
	
	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;
	
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
}
