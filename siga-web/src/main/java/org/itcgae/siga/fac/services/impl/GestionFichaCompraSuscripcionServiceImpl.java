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
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysServiciossolicitados;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.PysCompraMapper;
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
	private CenPersonaMapper cenPersonaMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

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
	private PysCompraMapper pysCompraMapper;

	@Autowired
	private AdmContadorMapper admContadorMapper;

	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		FichaCompraSuscripcionItem fichaCompleta = null;
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
					
					//Si es una compra/suscrpición nueva
					if(ficha.getnSolicitud() == null) {
						ficha.setIdInstitucion(idInstitucion.toString());
						ficha.setUsuModificacion(usuarios.get(0).getIdusuario().toString());

						//Se obtiene el idpersona del colegiado conectado en el caso se este creando una nueva compra/suscripción
						if(!letrado.equals("N")) {
						CenPersonaExample personaExmaple = new CenPersonaExample();
						
						personaExmaple.createCriteria().andNifcifEqualTo(dni);
						
						CenPersona colegiado = cenPersonaMapper.selectByExample(personaExmaple).get(0);
						
						ficha.setIdPersona(colegiado.getIdpersona().toString());
						}
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la nueva compra/suscripcion");

						fichaCompleta = pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion(ficha);
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la nueva compra/suscripcion");

						fichaCompleta.setIdInstitucion(idInstitucion.toString());
						fichaCompleta.setProductos(ficha.getProductos());
						fichaCompleta.setIdFormaPagoSeleccionada(Short.valueOf(fichaCompleta.getIdFormasPagoComunes().split(",")[0]));
					}
					//Para obtener toda la informacion de una compra/suscripcion ya creada
					else { 
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");

						fichaCompleta = pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion(ficha);
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");
					}
					}

			}
		} catch (Exception e) {
			LOGGER.error(
					"getFichaCompraSuscripcion() -> Se ha producido un error al obtener el los detalles de la compra/suscripcion",
					e);
		}

		LOGGER.info(
				"getFichaCompraSuscripcion() -> Salida del servicio para obtener los detalles de la compra/suscripcion");

		return fichaCompleta;
	}

	@Transactional
	public InsertResponseDTO solicitarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("solicitarCompra() -> Entrada al servicio para crear una solicitud de compra");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Se comentan el try y el catch para que la anotación @Transactional funcione
		// correctamente
//		try {
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"solicitarCompra() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"solicitarCompra() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				GenParametrosKey genKey = new GenParametrosKey();

				genKey.setModulo("PYS");
				genKey.setIdinstitucion(idInstitucion);
				genKey.setParametro("APROBAR_SOLICITUD_COMPRA");

				GenParametros aprobNecesaria = genParametrosMapper.selectByPrimaryKey(genKey);

				LOGGER.info(
						"solicitarCompra() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear una solicitud de compra");

				PysPeticioncomprasuscripcion solicitud = new PysPeticioncomprasuscripcion();

				solicitud.setFecha(new Date());
				solicitud.setIdinstitucion(idInstitucion);
				solicitud.setIdpersona(Long.valueOf(ficha.getIdPersona()));
				solicitud.setIdpeticion((Long.valueOf(ficha.getnSolicitud())));
				solicitud.setTipopeticion("A");
				// Caso en el que el colegio no necesite aprobacion para realizar compras o
				// suscripciones
//				if (aprobNecesaria.getValor() == "N")
//					solicitud.setIdestadopeticion((short) 20);
//				else
					solicitud.setIdestadopeticion((short) 10);
				solicitud.setFecha(new Date());
				Long fechaActual = new Date().getTime();
				solicitud.setNumOperacion(
						"1" + idInstitucion.toString() + ficha.getIdPersona() + fechaActual.toString()); // Necesita
																											// confirmacion
																											// de que es
																											// el valor
																											// correcto
				solicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
				solicitud.setFechamodificacion(new Date());

				response = pysPeticioncomprasuscripcionMapper.insert(solicitud);
				if (response == 0)
					throw new Exception("Error al insertar la solicitud de compra en la BBDD.");

				LOGGER.info(
						"solicitarCompra() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para crear una solicitud de compra");

				LOGGER.info(
						"solicitarCompra() / pysProductossolicitadosMapper.insert() -> Entrada a pysProductossolicitadosMapper para crear una solicitud de compra");

				PysProductossolicitados productoSolicitado = new PysProductossolicitados();

				productoSolicitado.setIdinstitucion(idInstitucion);
				productoSolicitado.setFechamodificacion(new Date());
				productoSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

				for (ListaProductosItem producto : ficha.getProductos()) {

					productoSolicitado.setIdproducto((long) producto.getIdproducto());
					productoSolicitado.setIdtipoproducto((short) producto.getIdtipoproducto());
					productoSolicitado.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
					productoSolicitado.setValor(new BigDecimal(producto.getValor()));
					productoSolicitado.setIdformapago(ficha.getIdFormaPagoSeleccionada());

					response = pysProductossolicitadosMapper.insert(productoSolicitado);
					if (response == 0)
						throw new Exception("Error al insertar un producto solicitado en la BBDD.");
				}

				LOGGER.info(
						"solicitarCompra() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para crear una solicitud de compra");

				// Al no necesitar aprobación, se crea el registro de compra/suscripción
				// inmediatamente
//				if (aprobNecesaria.getValor() == "N") {
//					PysCompra compra = new PysCompra();
//
//					compra.setFecha(new Date());
//					compra.setFechamodificacion(new Date());
//					compra.setIdinstitucion(idInstitucion);
//					compra.setIdpersona(Long.valueOf(ficha.getIdPersona()));
//					compra.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));
//					compra.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//					for (ListaProductosItem producto : ficha.getProductos()) {
//
//						compra.setIdproducto((long) producto.getIdproducto());
//						compra.setIdtipoproducto((short) producto.getIdtipoproducto());
//						compra.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
//						compra.setDescripcion(producto.getDescripcion());
//						compra.setIdformapago(ficha.getIdFormaPagoSeleccionada());
//
//						response = pysCompraMapper.insert(compra);
//						if (response == 0)
//							throw new Exception("Error al insertar un registro de compra en la BBDD.");
//					}
//
//				}

				insertResponseDTO.setStatus("200");
			}

		}
//		} catch (Exception e) {
//			LOGGER.error(
//					"GestionFichaCompraSuscripcionServiceImpl.solicitarCompra() -> Se ha producido un error al crear una solicitud de compra",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//			insertResponseDTO.setStatus("500");
//		}

		insertResponseDTO.setError(error);
		LOGGER.info("solicitarCompra() -> Salida del servicio para crear una solicitud de compra");
		
		return insertResponseDTO;
	}

	@Transactional
	public InsertResponseDTO solicitarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("solicitarSuscripcion() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Se comentan el try y el catch para que la anotación @Transactional funcione
		// correctamente
//		try {
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
					throw new Exception("Error al insertar un servicio solicitado en la BBDD.");
			}

			LOGGER.info(
					"solicitarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para crear una solicitud de suscripción");

			insertResponseDTO.setStatus("200");
		}
//		} catch (Exception e) {
//			LOGGER.error(
//					"GestionFichaCompraSuscripcionServiceImpl.solicitarSuscripcion() -> Se ha producido un error al crear una solicitud de suscripción",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//			insertResponseDTO.setStatus("500");
//		}

		insertResponseDTO.setError(error);
		LOGGER.info("solicitarSuscripcion() -> Salida del servicio para crear una solicitud de suscripción");

		return insertResponseDTO;
	}

	@Transactional
	public UpdateResponseDTO aprobarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("aprobarSuscripcion() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"aprobarSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"aprobarSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"aprobarSuscripcion() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para aprobar una solicitud de suscripción");

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
						throw new Exception(
								"Error al modificar la solicitud de suscripción en la BBDD para su aprobación.");

					LOGGER.info(
							"aprobarSuscripcion() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para aprobar una solicitud de suscripción");

					LOGGER.info(
							"aprobarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para aprobar una solicitud de suscripción");

					PysServiciossolicitados servicioSolicitado = new PysServiciossolicitados();

					servicioSolicitado.setFechamodificacion(new Date());
					servicioSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

					response = pysServiciossolicitadosMapper.insert(servicioSolicitado);
					if (response == 0)
						throw new Exception("Error al insertar un producto solicitado en la BBDD.");
				}

				LOGGER.info(
						"aprobarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para aprobar una solicitud de suscripción");

				updateResponseDTO.setStatus("200");
			}
		} catch (Exception e) {
			LOGGER.error(
					"GestionFichaCompraSuscripcionServiceImpl.aprobarSuscripcion() -> Se ha producido un error al aprobar una solicitud de suscripción",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setStatus("500");
		}

		updateResponseDTO.setError(error);
		LOGGER.info("aprobarSuscripcion() -> Salida del servicio para aprobar una solicitud de suscripción");

		return updateResponseDTO;
	}

	@Transactional
	public UpdateResponseDTO aprobarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("aprobarCompra() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"aprobarCompra() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"aprobarCompra() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"aprobarCompra() / pysPeticioncomprasuscripcionMapper.updateByPrimaryKey() -> Entrada a pysPeticioncomprasuscripcionMapper para aprobar una solicitud de compra");

					PysPeticioncomprasuscripcionKey solicitudKey = new PysPeticioncomprasuscripcionKey();

					solicitudKey.setIdinstitucion(idInstitucion);
					solicitudKey.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));

					PysPeticioncomprasuscripcion solicitud = pysPeticioncomprasuscripcionMapper
							.selectByPrimaryKey(solicitudKey);

					solicitud.setIdestadopeticion((short) 20);

					response = pysPeticioncomprasuscripcionMapper.updateByPrimaryKey(solicitud);
					if (response == 0)
						throw new Exception("Error al modificar la solicitud de compra en la BBDD para su aprobación.");

					LOGGER.info(
							"aprobarCompra() / pysPeticioncomprasuscripcionMapper.updateByPrimaryKey() -> Salida de pysPeticioncomprasuscripcionMapper para aprobar una solicitud de compra");

					LOGGER.info(
							"aprobarCompra() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para aprobar una solicitud de compra");

					PysCompra compra = new PysCompra();

					compra.setFecha(new Date());
					compra.setFechamodificacion(new Date());
					compra.setIdinstitucion(idInstitucion);
					compra.setIdpersona(Long.valueOf(ficha.getIdPersona()));
					compra.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));
					compra.setUsumodificacion(usuarios.get(0).getIdusuario());

					for (ListaProductosItem producto : ficha.getProductos()) {

						compra.setIdproducto((long) producto.getIdproducto());
						compra.setIdtipoproducto((short) producto.getIdtipoproducto());
						compra.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
						compra.setDescripcion(producto.getDescripcion());
						compra.setIdformapago(ficha.getIdFormaPagoSeleccionada());

						response = pysCompraMapper.insert(compra);
						if (response == 0)
							throw new Exception("Error al insertar un registro de compra en la BBDD.");
					}
				}

				LOGGER.info(
						"aprobarCompra() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para aprobar una solicitud de compra");

				updateResponseDTO.setStatus("200");
			}
		} catch (Exception e) {
			LOGGER.error(
					"GestionFichaCompraSuscripcionServiceImpl.aprobarCompra() -> Se ha producido un error al aprobar una solicitud de compra",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setStatus("500");
		}

		updateResponseDTO.setError(error);
		LOGGER.info("aprobarCompra() -> Salida del servicio para aprobar una solicitud de compra");

		return updateResponseDTO;
	}
}
