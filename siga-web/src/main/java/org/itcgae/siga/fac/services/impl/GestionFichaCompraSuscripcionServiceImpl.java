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
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.entities.PysCompraExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysProductossolicitadosExample;
import org.itcgae.siga.db.entities.PysServiciossolicitados;
import org.itcgae.siga.db.entities.PysServiciossolicitadosExample;
import org.itcgae.siga.db.entities.PysSuscripcion;
import org.itcgae.siga.db.entities.PysSuscripcionExample;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.PysCompraMapper;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.mappers.PysProductossolicitadosMapper;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.mappers.PysSuscripcionMapper;
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
	private PysSuscripcionMapper pysSuscripcionMapper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
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

	@Override
	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		FichaCompraSuscripcionItem fichaCompleta = null;
		Error error = new Error();

		LOGGER.info(
				"getFichaCompraSuscripcion() -> Entrada al servicio para recuperar los detalles de la compra/suscripcion");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//Este String sirve para saber si el usuario conectado es una colegiado o no.
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
						
						if(!letrado.equals("N")) {
							
							LOGGER.info(
									"getFichaCompraSuscripcion() / cenPersonaMapper.selectByExample() -> Entrada a CenPersonaMapper los detalles del usuario logeado");

							CenPersonaExample personaExmaple = new CenPersonaExample();
							
							personaExmaple.createCriteria().andNifcifEqualTo(dni);
							
							CenPersona persona = cenPersonaMapper.selectByExample(personaExmaple).get(0);
							
							LOGGER.info(
									"getFichaCompraSuscripcion() / cenPersonaMapper.selectByExample -> Salida de CenPersonaMapper los detalles del usuario logeado");
							
							ficha.setIdPersona(persona.getIdpersona().toString());
						}
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la nueva compra/suscripcion");

						fichaCompleta = pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion(ficha, !letrado.equals("N"));
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la nueva compra/suscripcion");

						fichaCompleta.setIdInstitucion(idInstitucion.toString());
						fichaCompleta.setProductos(ficha.getProductos());
						if(fichaCompleta.getIdFormasPagoComunes() != null)fichaCompleta.setIdFormaPagoSeleccionada(fichaCompleta.getIdFormasPagoComunes().split(",")[0]);
					}
					//Para obtener toda la informacion de una compra/suscripcion ya creada
					else { 
						ListaProductosItem[] productos = null;
						
						//Si se viene de otra pantalla a consultar la ficha de compra
						if(ficha.getProductos().length==0) {
							productos = pysPeticioncomprasuscripcionExtendsMapper.getProductosSolicitadosPeticion(usuarios.get(0).getIdlenguaje(), idInstitucion, ficha);
							ficha.setProductos(productos);
						}
						else if(ficha.getProductos().length>0){
							productos = ficha.getProductos();
						}
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");

						fichaCompleta = pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion(ficha, !letrado.equals("N"), idInstitucion);
						
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");
					

						fichaCompleta.setIdInstitucion(idInstitucion.toString());
						if(productos != null)fichaCompleta.setProductos(productos);
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

	@Override
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
		//Este String sirve para saber si el usuario conectado es una colegiado o no.
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);

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
				
				//Si no es un colegiado se lanza un error ya que no deberia tener acceso a este servicio.
//				if(letrado.equals("N"))
//					throw new Exception("El usuario conectado no es un colegiado y no deberia tener acceso a este servicio");
				
				GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);

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
				if (aprobNecesaria.getValor().equals("N"))
					solicitud.setIdestadopeticion((short) 20);
				else
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
				productoSolicitado.setIdpersona(Long.valueOf(ficha.getIdPersona()));
				productoSolicitado.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));
				if(productoSolicitado.getIdformapago()!=null)productoSolicitado.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
				else productoSolicitado.setIdformapago(null);
				//En el caso que la forma de pago sea domiciliación bancaria
				if(ficha.getIdFormaPagoSeleccionada().equals("20"))productoSolicitado.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
				else productoSolicitado.setIdcuenta(null);
				productoSolicitado.setFechamodificacion(new Date());
				productoSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

				for (ListaProductosItem producto : ficha.getProductos()) {
					
					productoSolicitado.setIdproducto((long) producto.getIdproducto());
					productoSolicitado.setIdtipoproducto((short) producto.getIdtipoproducto());
					productoSolicitado.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
					if(producto.getValor()!=null) {
						productoSolicitado.setValor(new BigDecimal(String.join(".",producto.getValor().split(",")[0],producto.getValor().split(",")[1].substring(0, 2))));
					}
					else productoSolicitado.setValor(null);
					
					//REVISAR: A revisar ya que no tenemos tarjeta productos implmentada todavia
					productoSolicitado.setCantidad(1);
					productoSolicitado.setAceptado("A");
					
					productoSolicitado.setNofacturable(producto.getNoFacturable());
					productoSolicitado.setFecharecepcionsolicitud(new Date());
					//DUDA: Se le supone que se refiere a la misma institucion desde la cual se realiza la peticion
					//, por lo tanto, la actual.
					productoSolicitado.setIdinstitucionorigen(idInstitucion);

					response = pysProductossolicitadosMapper.insert(productoSolicitado);
					if (response == 0)
						throw new Exception("Error al insertar un producto solicitado en la BBDD.");
				}

				LOGGER.info(
						"solicitarCompra() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para crear una solicitud de compra");

				// Al no necesitar aprobación, se crea el registro de compra
				// inmediatamente
				if (aprobNecesaria.getValor() == "N") {
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
						if(ficha.getIdFormaPagoSeleccionada()!=null)compra.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
						else compra.setIdformapago(null);

						response = pysCompraMapper.insert(compra);
						if (response == 0)
							throw new Exception("Error al insertar un registro de compra en la BBDD.");
					}

				}

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

	private GenParametros getParametroAprobarSolicitud(Short idInstitucion) {
		GenParametrosKey genKey = new GenParametrosKey();

		genKey.setModulo("PYS");
		genKey.setIdinstitucion(idInstitucion);
		genKey.setParametro("APROBAR_SOLICITUD_COMPRA");

		return genParametrosMapper.selectByPrimaryKey(genKey);
	}

	@Override
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
				solicitud.setTipopeticion("A");

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

	@Override
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
					solicitud.setTipopeticion("A");

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

	@Override
	@Transactional
	public UpdateResponseDTO aprobarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("aprobarCompra() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

//		try {
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
					
					if(solicitud==null) {
						this.solicitarCompra(request, ficha);
					}
					
					//En el caso que se realice la aprobación desde la pantalla "Compra de productos"
					if(ficha.getIdPersona()==null) {
						ficha.setIdPersona(solicitud.getIdpersona().toString());
					}

					LOGGER.info(
							"aprobarCompra() / pysPeticioncomprasuscripcionMapper.updateByPrimaryKey() -> Salida de pysPeticioncomprasuscripcionMapper para aprobar una solicitud de compra");

					GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);
					
					// Al necesitar aprobación, se crea el registro de compra
					// inmediatamente
					if (aprobNecesaria.getValor().equals("S")) {

					
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
							if(compra.getIdformapago()!=null) {
								compra.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
							}
							else {
								compra.setIdformapago(null);
							}
							//Revisar posteriormente
							compra.setCantidad(1);
							if(producto.getValor()!=null) {
								compra.setImporteunitario(new BigDecimal(String.join(".",producto.getValor().split(",")[0],producto.getValor().split(",")[1].substring(0, 2))));
							}
							else {
								compra.setImporteunitario(new BigDecimal(0));
							}
							if(ficha.getNoFact() != null) {
								compra.setNofacturable(ficha.getNoFact());
							}
							else {
								PysProductossolicitadosExample productosExample = new PysProductossolicitadosExample();

								productosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpeticionEqualTo(solicitud.getIdpeticion());

								compra.setNofacturable(pysProductossolicitadosMapper
										.selectByExample(productosExample).get(0).getNofacturable());
							}
							
							response = pysCompraMapper.insert(compra);
							if (response == 0)
								throw new Exception("Error al insertar un registro de compra en la BBDD.");
						}
						
						LOGGER.info(
								"aprobarCompra() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para aprobar una solicitud de compra");

					}
				}

				
				updateResponseDTO.setStatus("200");
			}
//		} catch (Exception e) {
//			LOGGER.error(
//					"GestionFichaCompraSuscripcionServiceImpl.aprobarCompra() -> Se ha producido un error al aprobar una solicitud de compra",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//			updateResponseDTO.setStatus("500");
//		}

		updateResponseDTO.setError(error);
		LOGGER.info("aprobarCompra() -> Salida del servicio para aprobar una solicitud de compra");

		return updateResponseDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO savePagoCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha)
			throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info(
				"savePagoCompraSuscripcion() -> Entrada al servicio para actualizar información de pago de una compra/suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

//		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"savePagoCompraSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"savePagoCompraSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					if (ficha.getProductos() != null) {
						
						LOGGER.info(
								"savePagoCompraSuscripcion() / pysProductossolicitadosMapper.updateByPrimaryKey() -> Entrada a pysProductossolicitadosMapper para actualizar información de pago de las compras asociadas a la peticion");

						//Se actualizan las solicitud es de productos asociadas a la peticion
						PysProductossolicitadosExample productosSolicitadosExample = new PysProductossolicitadosExample();

						productosSolicitadosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpeticionEqualTo(Long.valueOf(ficha.getnSolicitud()));

						List<PysProductossolicitados> productosSolicitados = pysProductossolicitadosMapper.selectByExample(productosSolicitadosExample);

						for (PysProductossolicitados productoSolicitado : productosSolicitados) {
							if(productoSolicitado.getIdformapago()!=null)productoSolicitado.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
							else productoSolicitado.setIdformapago(null);
							// En el caso que se trate de una domiciliación bancaria
							if (ficha.getIdFormaPagoSeleccionada().equals("20"))
								productoSolicitado.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
							else
								productoSolicitado.setIdcuenta(null);
							productoSolicitado.setNofacturable(ficha.getNoFact());
							
							response = pysProductossolicitadosMapper.updateByPrimaryKey(productoSolicitado);
							if(response == 0) throw new Exception("Eror al actualizar la información de pago de una solicitud de producto");
						}
						
						LOGGER.info(
								"savePagoCompraSuscripcion() / pysProductossolicitadosMapper.updateByPrimaryKey() -> Entrada a pysProductossolicitadosMapper para actualizar información de pago de las compras asociadas a la peticion");
					

						LOGGER.info(
								"savePagoCompraSuscripcion() / pysCompraMapper.updateByPrimaryKey() -> Entrada a pysCompraMapper para actualizar información de pago de las compras asociadas a la peticion");

						//Se actualizan los registros de compra asociados a la peticion
						PysCompraExample comprasExample = new PysCompraExample();

						comprasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpeticionEqualTo(Long.valueOf(ficha.getnSolicitud()));

						List<PysCompra> compras = pysCompraMapper.selectByExample(comprasExample);

						for (PysCompra compra : compras) {
							if(compra.getIdformapago()!=null)compra.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
							else compra.setIdformapago(null);
							// En el caso que se trate de una domiciliación bancaria
							if (ficha.getIdFormaPagoSeleccionada().equals("20"))
								compra.setIdcuentadeudor(Short.valueOf(ficha.getCuentaBancSelecc()));
							else
								compra.setIdcuentadeudor(null);
							compra.setNofacturable(ficha.getNoFact());
							
							response = pysCompraMapper.updateByPrimaryKey(compra);
							if(response == 0) throw new Exception("Eror al actualizar la información de pago de una compra");
						}
						
						LOGGER.info(
								"savePagoCompraSuscripcion() / pysCompraMapper.updateByPrimaryKey() -> Entrada a pysCompraMapper para actualizar información de pago de las compras asociadas a la peticion");
					}
					//Para modificaciones a servicios
					else {
						
						LOGGER.info(
								"savePagoCompraSuscripcion() / pysServiciossolicitadosMapper.updateByPrimaryKey() -> Entrada a pysServiciossolicitadosMapper para actualizar información de pago de las peticiones de suscripciones asociadas a la peticion");

						//Se actualizan las solicitud es de productos asociadas a la peticion
						PysServiciossolicitadosExample serviciosSolicitadosExample = new PysServiciossolicitadosExample();

						serviciosSolicitadosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpeticionEqualTo(Long.valueOf(ficha.getnSolicitud()));

						List<PysServiciossolicitados> serviciosSolicitados = pysServiciossolicitadosMapper.selectByExample(serviciosSolicitadosExample);

						for (PysServiciossolicitados servicioSolicitado : serviciosSolicitados) {
							if(servicioSolicitado.getIdformapago()!=null)servicioSolicitado.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
							else servicioSolicitado.setIdformapago(null);
							// En el caso que se trate de una domiciliación bancaria
							if (ficha.getIdFormaPagoSeleccionada().equals("20"))
								servicioSolicitado.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
							else
								servicioSolicitado.setIdcuenta(null);
							//serviciosSolicitado.setNofacturable(ficha.getNoFact());
							
							response = pysServiciossolicitadosMapper.updateByPrimaryKey(servicioSolicitado);
							if(response == 0) throw new Exception("Eror al actualizar la información de pago de una solicitud de suscripcion");
						}
						
						LOGGER.info(
								"savePagoCompraSuscripcion() / pysServiciossolicitadosMapper.updateByPrimaryKey() -> Entrada a pysServiciossolicitadosMapper para actualizar información de pago de las suscripciones asociadas a la peticion");
					

						LOGGER.info(
								"savePagoCompraSuscripcion() / pysCompraMapper.updateByPrimaryKey() -> Entrada a pysCompraMapper para actualizar información de pago de las suscripciones asociadas a la peticion");

						//Se actualizan los registros de suscripciones asociadas a la peticion
						PysSuscripcionExample suscripcionExample = new PysSuscripcionExample();

						suscripcionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpeticionEqualTo(Long.valueOf(ficha.getnSolicitud()));

						List<PysSuscripcion> suscripciones = pysSuscripcionMapper.selectByExample(suscripcionExample);

						for (PysSuscripcion suscripcion : suscripciones) {
							if(suscripcion.getIdformapago()!=null)suscripcion.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
							else suscripcion.setIdformapago(null);
							// En el caso que se trate de una domiciliación bancaria
							if (ficha.getIdFormaPagoSeleccionada().equals("20"))
								suscripcion.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
							else
								suscripcion.setIdcuenta(null);
							//suscripcion.setNofacturable(ficha.getNoFact());
							
							response = pysSuscripcionMapper.updateByPrimaryKey(suscripcion);
							if(response == 0) throw new Exception("Eror al actualizar la información de pago de una suscripcion");
						}
						
						LOGGER.info(
								"savePagoCompraSuscripcion() / pysSuscripcionMapper.updateByPrimaryKey() -> Entrada a pysSuscripcionMapper para actualizar información de pago de las compras asociadas a la peticion");
						
					}
				}
				LOGGER.info(
						"savePagoCompraSuscripcion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para actualizar información de pago de una compra/suscripción");

				updateResponseDTO.setStatus("200");
			}
//		} catch (Exception e) {
//			LOGGER.error(
//					"GestionFichaCompraSuscripcionServiceImpl.savePagoCompraSuscripcion() -> Se ha producido un error al actualizar información de pago de una compra/suscripción",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//			updateResponseDTO.setStatus("500");
//		}

		updateResponseDTO.setError(error);
		LOGGER.info("savePagoCompraSuscripcion() -> Salida del servicio para aprobar una solicitud de compra");

		return new UpdateResponseDTO();
	}
	
	@Override
	@Transactional
	public InsertResponseDTO denegarPeticion(HttpServletRequest request, String nSolicitud)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("denegarPeticion() -> Entrada al servicio para crear la denegación de la petición");

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
					"denegarPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"denegarPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"denegarPeticion() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear la denegación de la petición");

				PysPeticioncomprasuscripcionKey solicitudKey = new PysPeticioncomprasuscripcionKey();

				solicitudKey.setIdinstitucion(idInstitucion);
				solicitudKey.setIdpeticion(Long.valueOf(nSolicitud));

				PysPeticioncomprasuscripcion solicitudAlta = pysPeticioncomprasuscripcionMapper
						.selectByPrimaryKey(solicitudKey);
				
				
				PysPeticioncomprasuscripcion solicitudBaja = new PysPeticioncomprasuscripcion();

				solicitudBaja.setFecha(new Date());
				solicitudBaja.setFechamodificacion(new Date());
				solicitudBaja.setIdinstitucion(idInstitucion);
				solicitudBaja.setIdpersona(solicitudAlta.getIdpersona());
				solicitudBaja.setIdpeticion(Long.valueOf(pysPeticioncomprasuscripcionExtendsMapper.selectMaxIdPeticion(idInstitucion).getNewId())+1);
				solicitudBaja.setIdpeticionalta((Long.valueOf(nSolicitud)));
				solicitudBaja.setUsumodificacion(usuarios.get(0).getIdusuario());
				solicitudBaja.setTipopeticion("B");
				solicitudBaja.setIdestadopeticion((short) 20);
				Long fechaActual = new Date().getTime();
				solicitudBaja.setNumOperacion(
						"1" + idInstitucion.toString() + solicitudAlta.getIdpersona() + fechaActual.toString());

				response = pysPeticioncomprasuscripcionMapper.insert(solicitudBaja);
				if (response == 0)
					throw new Exception("Error al insertar la denegación de la petición en la BBDD.");

				LOGGER.info(
						"denegarPeticion() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para crear la denegación de la petición");

				LOGGER.info(
						"denegarPeticion() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para crear la denegación de la petición");
			}

			LOGGER.info(
					"denegarPeticion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para crear una solicitud de suscripción");

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
		LOGGER.info("denegarPeticion() -> Salida del servicio para crear una solicitud de suscripción");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO denegarPeticionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("denegarPeticionMultiple() -> Entrada al servicio para crear la denegación de una o varias peticiones");

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
					"denegarPeticionMultiple() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"denegarPeticionMultiple() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"denegarPeticionMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear la denegación de una o varias peticiones");

				//Se guardaran aqui los numeros de solicitud de aquellas solicitudes no validas por el estado en el que vienen
				error.setDescription("");
				for(FichaCompraSuscripcionItem peticion : peticiones) {
					if(peticion.getFechaDenegada() != null || peticion.getFechaAceptada() != null) {
						error.setDescription(error.getDescription()+" "+peticion.getnSolicitud()+",");
					}
					else this.denegarPeticion(request, peticion.getnSolicitud());
				}

				if(!error.getDescription().equals(""))error.setDescription(error.getDescription().substring(0, error.getDescription().length()-1));
				
				
				LOGGER.info(
						"denegarPeticionMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para crear la denegación de una o varias peticiones");

				LOGGER.info(
						"denegarPeticionMultiple() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para crear la denegación de una o varias peticiones");
			}

			LOGGER.info(
					"denegarPeticionMultiple() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para crear una solicitud de una o varias peticiones");

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.info("denegarPeticionMultiple() -> Salida del servicio para crear la denegación de una petición");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO aprobarCompraMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("aprobarCompraMultiple() -> Entrada al servicio para aprobar una o varias peticiones de compra");

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
					"aprobarCompraMultiple() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"aprobarCompraMultiple() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"aprobarCompraMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para aprobar una o varias peticiones de compra");

				//Se guardaran aqui los numeros de solicitud de aquellas solicitudes no validas por el estado en el que vienen
				error.setDescription("");
				for(FichaCompraSuscripcionItem peticion : peticiones) {
					if(peticion.getFechaDenegada() != null || peticion.getFechaAceptada() != null) {
						error.setDescription(error.getDescription()+" "+peticion.getnSolicitud()+",");
					}
					else {
						peticion.setProductos(pysPeticioncomprasuscripcionExtendsMapper.getProductosSolicitadosPeticion(usuarios.get(0).getIdlenguaje(), idInstitucion, peticion));
						this.aprobarCompra(request, peticion);
					}
				}

				if(!error.getDescription().equals(""))error.setDescription(error.getDescription().substring(0, error.getDescription().length()-1));
				
				
				LOGGER.info(
						"aprobarCompraMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para aprobar una o varias peticiones de compra");

				LOGGER.info(
						"aprobarCompraMultiple() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para aprobar una o varias peticiones de compra");
			}

			LOGGER.info(
					"aprobarCompraMultiple() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para crear una solicitud de una o varias peticiones de compra");

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.info("aprobarCompraMultiple() -> Salida del servicio para aprobar una o varias peticiones de compra");

		return insertResponseDTO;
	}
}
