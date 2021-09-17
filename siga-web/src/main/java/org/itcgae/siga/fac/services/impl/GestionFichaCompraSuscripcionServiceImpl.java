package org.itcgae.siga.fac.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionDTO;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionExample;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysServiciossolicitados;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.mappers.PysProductossolicitadosMapper;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.IGestionFichaCompraSuscripcionService;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;

@Service
public class GestionFichaCompraSuscripcionServiceImpl implements IGestionFichaCompraSuscripcionService {

	private Logger LOGGER = Logger.getLogger(GestionFichaCompraSuscripcionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private PySTipoIvaExtendsMapper pysTipoIvaExtendsMapper;

	@Autowired
	private PySTipoFormaPagoExtendsMapper pysTipoFormaPagoExtendsMapper;

	@Autowired
	private PySTiposProductosExtendsMapper pysTiposProductosExtendsMapper;

	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;

	@Autowired
	private PysPeticioncomprasuscripcionMapper pysPeticioncomprasuscripcionMapper;

	@Autowired
	private PysProductosinstitucionExtendsMapper pysProductosInstitucionExtendsMapper;

	@Autowired
	private PysProductosinstitucionMapper pysProductosInstitucionMapper;

	@Autowired
	private PysFormapagoproductoMapper pysFormaPagoProducto;

	@Autowired
	private PysProductossolicitadosMapper pysProductossolicitadosMapper;
	
	@Autowired
	private PysServiciossolicitadosMapper pysServiciossolicitadosMapper;

	@Autowired
	private AdmContadorMapper admContadorMapper;

	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request) {

		FichaCompraSuscripcionItem ficha = null;
		Error error = new Error();

		LOGGER.info(
				"getFichaCompraSuscripcion() -> Entrada al servicio para recuperar los detalles de la compra/suscripcion");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getFichaCompraSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getFichaCompraSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");

					ficha = pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion(idInstitucion,
							new FichaCompraSuscripcionItem());

					LOGGER.info(
							"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"getFichaCompraSuscripcion() -> Se ha producido un error al obtener el los detalles de la compra/suscripcion",
					e);
		}

		LOGGER.info(
				"getFichaCompraSuscripcion() -> Salida del servicio para obtener los detalles de la compra/suscripcion");

		return ficha;
	}

	public Long getNewNSolicitud(HttpServletRequest request) {

		ProductoDetalleDTO productoDetalleDTO = new ProductoDetalleDTO();
		Error error = new Error();
		Long newNSolicitud = null;

		LOGGER.info(
				"getNewNSolicitud() -> Entrada al servicio para recuperar el numero de peticion de la nueva petición");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getNewNSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getNewNSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					PysPeticioncomprasuscripcionExample peticionExample = new PysPeticioncomprasuscripcionExample();

					peticionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);
					peticionExample.setOrderByClause("idpeticion desc");

					List<PysPeticioncomprasuscripcion> peticiones = pysPeticioncomprasuscripcionMapper
							.selectByExample(peticionExample);

					newNSolicitud = peticiones.get(0).getIdpeticion() + 1;
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"getNewNSolicitud() -> Se ha producido un error al obtener el el numero de peticion de la nueva petición",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		productoDetalleDTO.setError(error);

		LOGGER.info(
				"getNewNSolicitud() -> Salida del servicio para obtener el numero de peticion de la nueva petición");

		return newNSolicitud;
	}

	@Transactional
	public InsertResponseDTO solicitarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		int statusInsertAdmContador = 0;

		LOGGER.info("solicitarCompra() -> Entrada al servicio para crear una solicitud de compra");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"solicitarCompra() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"solicitarCompra() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"solicitarCompra() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear una solicitud de compra");

					PysPeticioncomprasuscripcion solicitud = new PysPeticioncomprasuscripcion();

					solicitud.setFecha(new Date());
					solicitud.setIdinstitucion(idInstitucion);
					solicitud.setIdpersona(Long.valueOf(ficha.getIdPersona()));
					solicitud.setIdpeticion((Long.valueOf(ficha.getnSolicitud())));
					solicitud.setTipopeticion("A");
					solicitud.setIdestadopeticion((short) 10);
					solicitud.setFecha(new Date());
					Long fechaActual = new Date().getTime();
					solicitud.setNumOperacion("1"+idInstitucion.toString()+ficha.getIdPersona()+fechaActual.toString()); //Necesita confirmacion de que es el valor correcto
					solicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
					solicitud.setFechamodificacion(new Date());

					response = pysPeticioncomprasuscripcionMapper.insert(solicitud);
					if (response == 0)
						throw new Exception("Error al insertar la solicitud de compra en la BBDD.");

					LOGGER.info(
							"solicitarCompra() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para crear una solicitud de compra");

					
					LOGGER.info(
							"solicitarCompra() / pysProductossolicitadosMapper.insert() -> Entrada a pysProductossolicitadosMapper para crear una solicitud de compra");

					for (ListaProductosItem producto : ficha.getProductos()) {
						PysProductossolicitados productoSolicitado = new PysProductossolicitados();

						productoSolicitado.setIdproducto((long) producto.getIdproducto());
						productoSolicitado.setIdtipoproducto((short) producto.getIdtipoproducto());
						productoSolicitado.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
						productoSolicitado.setValor(new BigDecimal(producto.getValor()));
						productoSolicitado.setIdinstitucion(idInstitucion);
						productoSolicitado.setFechamodificacion(new Date());
						productoSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

						response = pysProductossolicitadosMapper.insert(productoSolicitado);
						if (response == 0)
							throw new Exception("Error al insertar un producto solicitado en la BBDD.");
					}
					
					LOGGER.info(
							"solicitarCompra() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para crear una solicitud de compra");


					insertResponseDTO.setStatus("200");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"GestionFichaCompraSuscripcionServiceImpl.solicitarCompra() -> Se ha producido un error al crear una solicitud de compra",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus("500");
		}

		insertResponseDTO.setError(error);
		LOGGER.info("solicitarCompra() -> Salida del servicio para crear una solicitud de compra");

		return insertResponseDTO;
	}
	
	@Transactional
	public InsertResponseDTO solicitarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("solicitarSuscripcion() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"solicitarSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"solicitarSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"solicitarSuscripcion() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear una solicitud de suscripción");

					PysPeticioncomprasuscripcion solicitud = new PysPeticioncomprasuscripcion();

					solicitud.setFecha(new Date());
					solicitud.setFechamodificacion(new Date());
					solicitud.setIdinstitucion(idInstitucion);
					solicitud.setIdpersona(Long.valueOf(ficha.getIdPersona()));
					solicitud.setIdpeticion((Long.valueOf(ficha.getnSolicitud())));
					solicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
					solicitud.setTipopeticion("B");

					response = pysPeticioncomprasuscripcionMapper.insert(solicitud);
					if (response == 0)
						throw new Exception("Error al insertar la solicitud de suscripción en la BBDD.");

					LOGGER.info(
							"solicitarSuscripcion() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para crear una solicitud de suscripción");

					LOGGER.info(
							"solicitarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para crear una solicitud de suscripción");

						PysServiciossolicitados servicioSolicitado = new PysServiciossolicitados();

						servicioSolicitado.setFechamodificacion(new Date());
						servicioSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

						response = pysServiciossolicitadosMapper.insert(servicioSolicitado);
						if (response == 0)
							throw new Exception("Error al insertar un producto solicitado en la BBDD.");
					}
				
					LOGGER.info(
							"solicitarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para crear una solicitud de suscripción");


					insertResponseDTO.setStatus("200");
			}
		} catch (Exception e) {
			LOGGER.error(
					"GestionFichaCompraSuscripcionServiceImpl.solicitarSuscripcion() -> Se ha producido un error al crear una solicitud de suscripción",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus("500");
		}

		insertResponseDTO.setError(error);
		LOGGER.info("solicitarSuscripcion() -> Salida del servicio para crear una solicitud de suscripción");

		return insertResponseDTO;
	}

}
