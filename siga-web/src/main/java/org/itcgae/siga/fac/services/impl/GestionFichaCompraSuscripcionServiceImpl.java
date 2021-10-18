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
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
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
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcion;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcionExample;
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
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionMapper;
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
	private FacFacturaMapper facFacturaMapper;

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
	private FacFacturacionsuscripcionMapper facFacturacionsuscripcionMapper;

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
						List<ListaProductosCompraItem> productos = null;
						
						//Si se viene de otra pantalla a consultar la ficha de compra
						if(ficha.getProductos().size()==0) {
							productos = pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra(idInstitucion, ficha.getnSolicitud());
							ficha.setProductos(productos);
						}
						else if(ficha.getProductos().size()>0){
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

				this.updateProductosPeticion(request, ficha);

				LOGGER.info(
						"solicitarCompra() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para crear una solicitud de compra");

				// Al no necesitar aprobación, se crea el registro de compra
				// inmediatamente
				if (aprobNecesaria.getValor() == "N") {
					
					this.aprobarCompra(request, ficha);

				}

				insertResponseDTO.setStatus("200");
			}

		}

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
							"aprobarCompra() / pysPeticioncomprasuscripcionMapper.selectByPrimaryKey() -> Entrada a pysPeticioncomprasuscripcionMapper para extraer la peticion asociada");
					
					PysPeticioncomprasuscripcionKey solicitudKey = new PysPeticioncomprasuscripcionKey();

					solicitudKey.setIdinstitucion(idInstitucion);
					solicitudKey.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));

					PysPeticioncomprasuscripcion solicitud = pysPeticioncomprasuscripcionMapper
							.selectByPrimaryKey(solicitudKey);

					LOGGER.info(
							"aprobarCompra() / pysPeticioncomprasuscripcionMapper.selectByPrimaryKey() -> Salida de pysPeticioncomprasuscripcionMapper para extraer la peticion asociada");

					if(solicitud==null) {
						this.solicitarCompra(request, ficha);
					}
					
					//En el caso que se realice la aprobación desde la pantalla "Compra de productos"
					if(ficha.getIdPersona()==null) {
						ficha.setIdPersona(solicitud.getIdpersona().toString());
					}

					GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);
					
					// Al necesitar aprobación, se crea el registro de compra
					// inmediatamente. Esto es a diferencia del servicio de solicitud.
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
	
						for (ListaProductosCompraItem producto : ficha.getProductos()) {
	
							compra.setIdproducto((long) producto.getIdproducto());
							compra.setIdtipoproducto((short) producto.getIdtipoproducto());
							compra.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
							compra.setDescripcion(producto.getDescripcion());
							//Si se ha seleccionado como forma de pago "No facturable"
							if(ficha.getIdFormaPagoSeleccionada().equals("-1")) {
								//productoSolicitado.setIdformapago(null);
								//Al no poder poner a nulo la forma de pago se el asigna el valor del 
								//elemento del combo equivalente a "No facturable" en el combo de formas de pago del front
								compra.setIdformapago((short) 20);
								compra.setNofacturable("1");
							}
							else{
								compra.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
								compra.setNofacturable("0");
							}
							if(ficha.getIdFormaPagoSeleccionada().equals("20")) {
								compra.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
							}
							else {
								compra.setIdcuenta(null);
							}
							compra.setCantidad(Integer.valueOf(producto.getCantidad()));
							compra.setIdtipoiva(Integer.valueOf(producto.getIdtipoiva()));
							if(producto.getPrecioUnitario()!=null) {
								compra.setImporteunitario(new BigDecimal(producto.getPrecioUnitario()));
							}
							else {
								compra.setImporteunitario(new BigDecimal(0));
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
						peticion.setProductos(pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra(idInstitucion, peticion.getnSolicitud()));
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
	
	@Override
	@Transactional
	public InsertResponseDTO updateProductosPeticion(HttpServletRequest request, FichaCompraSuscripcionItem peticion)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("updateProductosPeticion() -> Entrada al servicio para actualizar los productos solicitados asociados con una solicitud");

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
					"updateProductosPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateProductosPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"updateProductosPeticion() / pysPeticioncomprasuscripcionMapper.deleteByExample() -> Entrada a pysProductossolicitadosMapper para eliminar los productos solicitados asociados con una solicitud");

				PysProductossolicitadosExample prodExample = new PysProductossolicitadosExample();
				
				prodExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpeticionEqualTo(Long.valueOf(peticion.getnSolicitud()));
				//para comprobar que hay productos a eliminar
				List<PysProductossolicitados> productosViejos = pysProductossolicitadosMapper.selectByExample(prodExample);
				response = pysProductossolicitadosMapper.deleteByExample(prodExample);
				//La segunda condicion es para evitar que se lance una excepcion al intentar eliminar un conjunto vacio
				if(response == 0 && !productosViejos.isEmpty()) {
					throw new Exception("Eror al eliminar los productos solicitados de la petición");
				}
				
				LOGGER.info(
						"updateProductosPeticion() / pysProductossolicitadosMapper.deleteByExaple() -> Salida de pysProductossolicitadosMapper para eliminar los productos solicitados asociados con una solicitud");

				
				LOGGER.info(
						"updateProductosPeticion() / pysProductossolicitadosMapper.insert() -> Entrada a pysProductossolicitadosMapper para insertar los productos solicitados asociados con una solicitud");
				
				PysProductossolicitados productoSolicitado = new PysProductossolicitados();

				productoSolicitado.setIdinstitucion(idInstitucion);
				productoSolicitado.setIdpersona(Long.valueOf(peticion.getIdPersona()));
				productoSolicitado.setIdpeticion(Long.valueOf(peticion.getnSolicitud()));
				//Si se ha seleccionado como forma de pago "No facturable"
				if(peticion.getIdFormaPagoSeleccionada().equals("-1")) {
					//productoSolicitado.setIdformapago(null);
					//De forma temporal se utilizara el id 20
					productoSolicitado.setIdformapago((short) 20);
					productoSolicitado.setNofacturable("1");
				}
				else{
					productoSolicitado.setIdformapago(Short.valueOf(peticion.getIdFormaPagoSeleccionada()));
					productoSolicitado.setNofacturable("0");
				}
				//En el caso que la forma de pago sea domiciliación bancaria
				if(peticion.getIdFormaPagoSeleccionada().equals("20")) {
					productoSolicitado.setIdcuenta(Short.valueOf(peticion.getCuentaBancSelecc()));
				}
				else {
					productoSolicitado.setIdcuenta(null);
				}
				
				productoSolicitado.setFechamodificacion(new Date());
				productoSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

				for (ListaProductosCompraItem producto : peticion.getProductos()) {
					
					productoSolicitado.setIdproducto((long) producto.getIdproducto());
					productoSolicitado.setIdtipoproducto((short) producto.getIdtipoproducto());
					productoSolicitado.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
					if(producto.getPrecioUnitario()!=null) {
						productoSolicitado.setValor(new BigDecimal(producto.getPrecioUnitario()));
					}
					else productoSolicitado.setValor(null);
					productoSolicitado.setIdtipoiva(Integer.valueOf(producto.getIdtipoiva()));
					
					//REVISAR: 
					productoSolicitado.setAceptado("A");
					
					productoSolicitado.setOrden(Short.valueOf(producto.getOrden()));
					productoSolicitado.setCantidad(Integer.valueOf(producto.getCantidad()));
					productoSolicitado.setFecharecepcionsolicitud(new Date());
					//DUDA: Se le supone que se refiere a la misma institucion desde la cual se realiza la peticion
					//, por lo tanto, la actual.
					productoSolicitado.setIdinstitucionorigen(idInstitucion);
					productoSolicitado.setObservaciones(producto.getObservaciones());
					
					//productoSolicitado.set

					response = pysProductossolicitadosMapper.insert(productoSolicitado);
					if (response == 0)
						throw new Exception("Error al insertar un producto solicitado en la BBDD.");
				}

				LOGGER.info(
						"updateProductosPeticion() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para insertar los productos solicitados asociados con una solicitud");
			}

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.info("updateProductosPeticion() -> Salida del servicio para actualizar los productos solicitados asociados con una solicitud");

		return insertResponseDTO;
	}
	
	@Override
	public ListaProductosCompraDTO getListaProductosCompra(HttpServletRequest request, String idPeticion){

		ListaProductosCompraDTO listaProductosCompra = new ListaProductosCompraDTO();
		Error error = new Error();

		LOGGER.info("getListaProductosCompra() -> Entrada al servicio para obtener la informacion de los productos de una peticion");

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
					"getListaProductosCompra() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getListaProductosCompra() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				
				LOGGER.info(
						"getListaProductosCompra() / pysProductossolicitadosMapper.insert() -> Entrada a pysProductossolicitadosMapper para obtener la informacion de los productos de una peticion");

				List<ListaProductosCompraItem> productosCompra = pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra(idInstitucion, idPeticion);

				listaProductosCompra.setListaProductosCompraItems(productosCompra);
				
				error.setCode(200);
				
				listaProductosCompra.setError(error);
				
				LOGGER.info(
						"getListaProductosCompra() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para obtener la informacion de los productos de una peticion");
			}

			LOGGER.info(
					"getListaProductosCompra() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para obtener la informacion de los productos de una peticion");

		}
		LOGGER.info("getListaProductosCompra() -> Salida del servicio para obtener la informacion de los productos de una peticion");

		return listaProductosCompra;
	}
	
	@Override
	public String getPermisoModificarImporteProducto(HttpServletRequest request) 
	{
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				
		GenParametrosKey genKey = new GenParametrosKey();

		genKey.setModulo("PYS");
		genKey.setIdinstitucion(idInstitucion);
		genKey.setParametro("MODIFICAR_IMPORTE_UNITARIO_PRODUCTOS");

		return genParametrosMapper.selectByPrimaryKey(genKey).getValor();
	}
	
	
	@Override
	public ListaFacturasPeticionDTO getFacturasPeticion(HttpServletRequest request, String nSolicitud){

		ListaFacturasPeticionDTO listaFacturasDTO = new ListaFacturasPeticionDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.info("getFacturasPeticion() -> Entrada al servicio para crear la denegación de la petición");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getFacturasPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getFacturasPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getFacturasPeticion() / pysCompraMapper.selectByExample() -> Entrada a pysCompraMapper para recuperar las posibles compras asociadas a la peticion");

					
					PysCompraExample compraExample = new PysCompraExample();
					
					compraExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud)).andIdinstitucionEqualTo(idInstitucion);	
					
					//Se comprueba si la peticion es de compra o de suscripcion
					List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(compraExample);

					List<String> idFacturasPeticion = new ArrayList<String>();
					

					LOGGER.info(
							"getFacturasPeticion() / pysCompraMapper.selectByExample() -> Salida de pysCompraMapper para recuperar las posibles compras asociadas a la peticion");
					
					//Si la peticion tiene alguna compra asociada se extraen las facturas de las compras
					if(!comprasPeticion.isEmpty()) {
						for(PysCompra compra : comprasPeticion) {
							if(compra.getIdfactura() != null) idFacturasPeticion.add(compra.getIdfactura());
						}
						
						if(!idFacturasPeticion.isEmpty()) {
							FacFacturaExample facturaExample = new FacFacturaExample();
							
							facturaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdfacturaIn(idFacturasPeticion);
							

							LOGGER.info(
									"getFacturasPeticion() / facFacturaMapper.selectByExample() -> Entrada a facFacturaMapper para recuperar las posibles facturas asociadas a la peticion de compra");
							
							List<FacFactura> facturasPeticion = facFacturaMapper.selectByExample(facturaExample);
							
							LOGGER.info(
									"getFacturasPeticion() / facFacturaMapper.selectByExample() ->Salida de facFacturaMapper para recuperar las posibles facturas asociadas a la peticion de compra");
							
							List<ListaFacturasPeticionItem> facturasListaPeticion = new ArrayList<ListaFacturasPeticionItem>();
							
							for(FacFactura factura : facturasPeticion) {
								ListaFacturasPeticionItem f = new ListaFacturasPeticionItem();
								f.setFechaFactura(factura.getFechaemision());
								f.setEstado(factura.getEstado());
								f.setImporte(factura.getImptotal());// Preguntar sobre a que importe se refiere
								f.setnFactura(factura.getNumerofactura());
								f.setTipo("Factura"); //Como se distingue una factura de una anulación?
								facturasListaPeticion.add(f);
							}
							
							listaFacturasDTO.setListaFacturasPeticionItem(facturasListaPeticion);
						}
					}
					else {
						PysSuscripcionExample suscripcionExample = new PysSuscripcionExample();
						
						suscripcionExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud)).andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"getFacturasPeticion() / pysSuscripcionMapper.selectByExample() -> Entrada a pysSuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");
						
						List<PysSuscripcion> suscripcionesPeticion = pysSuscripcionMapper.selectByExample(suscripcionExample);
						
						LOGGER.info(
								"getFacturasPeticion() / pysSuscripcionMapper.selectByExample() -> Entrada a pysSuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");
						
						//Si la peticion tiene alguna suscripcion asociada se extraen las facturas de las suscripciones
						if(!suscripcionesPeticion.isEmpty()) {
							
							List<FacFacturacionsuscripcion> facturasPeticion = new ArrayList<FacFacturacionsuscripcion>();
							
							for(PysSuscripcion suscripcion : suscripcionesPeticion) {
								FacFacturacionsuscripcionExample facturaExample = new FacFacturacionsuscripcionExample();
								
								facturaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtiposerviciosEqualTo(suscripcion.getIdtiposervicios())
								.andIdservicioEqualTo(suscripcion.getIdservicio()).andIdserviciosinstitucionEqualTo(suscripcion.getIdserviciosinstitucion())
								.andIdsuscripcionEqualTo(suscripcion.getIdsuscripcion());
							
								LOGGER.info(
										"getFacturasPeticion() / facFacturacionsuscripcionMapper.selectByExample() -> Entrada a facFacturacionsuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");
								
								List<FacFacturacionsuscripcion> facturasSuscripcion = facFacturacionsuscripcionMapper.selectByExample(facturaExample);
								
								LOGGER.info(
										"getFacturasPeticion() / facFacturacionsuscripcionMapper.selectByExample() -> Entrada a facFacturacionsuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");
								
								facturasPeticion.addAll(facturasSuscripcion);
								
							}
							
							FacFacturaKey facKey = new FacFacturaKey();
							
							facKey.setIdinstitucion(idInstitucion);

							List<ListaFacturasPeticionItem> facturasListaPeticion = new ArrayList<ListaFacturasPeticionItem>();
							
							for(FacFacturacionsuscripcion facturaSus : facturasPeticion) {
								
								facKey.setIdfactura(facturaSus.getIdfactura());
								
								FacFactura facturaPeticion = facFacturaMapper.selectByPrimaryKey(facKey);
								
								ListaFacturasPeticionItem f = new ListaFacturasPeticionItem();
								
								f.setFechaFactura(facturaPeticion.getFechaemision());
								f.setEstado(facturaPeticion.getEstado());
								f.setImporte(facturaPeticion.getImptotal());
								f.setnFactura(facturaPeticion.getNumerofactura());
								f.setTipo("Factura"); // Como se distingue una factura de una anulación?
								facturasListaPeticion.add(f);
							}
							
							listaFacturasDTO.setListaFacturasPeticionItem(facturasListaPeticion);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"getFacturasPeticion() -> Se ha producido un error al obtener el los detalles de la compra/suscripcion",
					e);
			error.setCode(500);
			error.setDescription(e.getMessage());
			listaFacturasDTO.setError(error);
		}

		LOGGER.info("getFacturasPeticion() -> Salida del servicio para obtener las facturas de una petición");

		return listaFacturasDTO;
	}
	
	
}
