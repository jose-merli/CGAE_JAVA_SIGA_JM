package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionItem;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcion;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.PysAnticipoletrado;
import org.itcgae.siga.db.entities.PysAnticipoletradoExample;
import org.itcgae.siga.db.entities.PysAnticipoletradoKey;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.entities.PysCompraExample;
import org.itcgae.siga.db.entities.PysLineaanticipo;
import org.itcgae.siga.db.entities.PysLineaanticipoExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionKey;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysProductossolicitadosExample;
import org.itcgae.siga.db.entities.PysServicioanticipo;
import org.itcgae.siga.db.entities.PysServicioanticipoExample;
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
import org.itcgae.siga.db.mappers.PysAnticipoletradoMapper;
import org.itcgae.siga.db.mappers.PysCompraMapper;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
import org.itcgae.siga.db.mappers.PysLineaanticipoExtendsMapper;
import org.itcgae.siga.db.mappers.PysLineaanticipoMapper;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.mappers.PysPreciosserviciosMapper;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.mappers.PysProductossolicitadosMapper;
import org.itcgae.siga.db.mappers.PysServicioanticipoMapper;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.mappers.PysSuscripcionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.IGestionFichaCompraSuscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	
	@Autowired
	private PysLineaanticipoMapper pysLineaAnticipoMapper;

	@Autowired
	private PysAnticipoletradoMapper pysAnticipoLetradoMapper;
	
	@Autowired
	private PysServicioanticipoMapper pysServicioanticipoMapper;
	
	@Autowired
	private PysPreciosserviciosMapper pysPreciosserviciosMapper;
	
	@Autowired
	private PysLineaanticipoExtendsMapper pysLineaanticipoExtendsMapper;
	
	
	@Override
	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) {

		FichaCompraSuscripcionItem fichaCompleta = null;

		LOGGER.debug(
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
						
						//Si es colegiado se debe coger el idpersona del usuario conectado
						if(!letrado.equals("N")) {
							
							LOGGER.info(
									"getFichaCompraSuscripcion() / cenPersonaMapper.selectByExample() -> Entrada a CenPersonaMapper los detalles del usuario logeado");

							CenPersonaExample personaExmaple = new CenPersonaExample();
							
							personaExmaple.createCriteria().andNifcifEqualTo(dni);
							
							CenPersona persona = cenPersonaMapper.selectByExample(personaExmaple).get(0);
							
							LOGGER.info(
									"getFichaCompraSuscripcion() / cenPersonaMapper.selectByExample -> Salida de CenPersonaMapper los detalles del usuario logeado");
							
							ficha.setIdPersona(persona.getIdpersona());
						}
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la nueva compra/suscripcion");

						fichaCompleta = pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion(ficha, !letrado.equals("N"));
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getNuevaFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la nueva compra/suscripcion");

						fichaCompleta.setIdInstitucion(idInstitucion.toString());
						fichaCompleta.setProductos(ficha.getProductos());
						fichaCompleta.setServicios(ficha.getServicios());
						if(fichaCompleta.getIdFormasPagoComunes() != null)fichaCompleta.setIdFormaPagoSeleccionada(fichaCompleta.getIdFormasPagoComunes().split(",")[0]);
					}
					//Para obtener toda la informacion de una compra/suscripcion ya creada
					else { 
						
						List<ListaProductosCompraItem> productos = null;
						List<ListaServiciosSuscripcionItem> servicios = null;
						
						
						//Si es una ficha de compra
						if(ficha.getProductos() != null) {
							
							//Si se viene de otra pantalla a consultar la ficha de compra
							if(ficha.getProductos().size()==0) {
								productos = pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra(idInstitucion, ficha.getnSolicitud());
								ficha.setProductos(productos);
							}
							else if(ficha.getProductos().size()>0){
								productos = ficha.getProductos();
							}
						}
						//Si es una ficha de suscripcion
						else {
							
							//Si se viene de otra pantalla a consultar la ficha de compra
							if(ficha.getServicios().size()==0) {
								servicios = pysPeticioncomprasuscripcionExtendsMapper.getListaServiciosSuscripcion(idInstitucion, ficha.getnSolicitud(), usuarios.get(0).getIdlenguaje(), null);
								ficha.setServicios(servicios);
							}
							else if(ficha.getServicios().size()>0){
								servicios = ficha.getServicios();
							}
						}
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");

						fichaCompleta = pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion(ficha, !letrado.equals("N"), idInstitucion);
						
						
						LOGGER.info(
								"getFichaCompraSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getFichaCompraSuscripcion() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener los detalles de la compra/suscripcion");
					

						fichaCompleta.setIdInstitucion(idInstitucion.toString());
						if(productos != null) {
							fichaCompleta.setProductos(productos);
						}
						if(servicios != null) {
							fichaCompleta.setServicios(servicios);
						}
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"getFichaCompraSuscripcion() -> Se ha producido un error al obtener el los detalles de la compra/suscripcion",
					e);
		}

		LOGGER.debug(
				"getFichaCompraSuscripcion() -> Salida del servicio para obtener los detalles de la compra/suscripcion");

		return fichaCompleta;
	}

	@Override
	@Transactional(timeout=24000)
	public InsertResponseDTO solicitarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("solicitarCompra() -> Entrada al servicio para crear una solicitud de compra");

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
				//Se omite esta llamada para que no afecte a llamadas desde otros puntos de la aplicacion
//				if(letrado.equals("N"))
//					throw new Exception("El usuario conectado no es un colegiado y no deberia tener acceso a este servicio");
				
				Long idPersona = Long.valueOf(ficha.getIdPersona());
				//Si es colegiado se debe coger el idpersona del usuario conectado
				if(!letrado.equals("N")) {
				CenPersonaExample persExample = new CenPersonaExample();

				persExample.createCriteria().andNifcifEqualTo(dni);

				List<CenPersona> personas = cenPersonaMapper.selectByExample(persExample);

				idPersona = personas.get(0).getIdpersona();
				}
				
				GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);

				LOGGER.info(
						"solicitarCompra() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear una solicitud de compra");

				PysPeticioncomprasuscripcion solicitud = new PysPeticioncomprasuscripcion();
				
				if(ficha.getFechaCompra()!=null) {
					solicitud.setFecha(ficha.getFechaCompra());
				}else {
					solicitud.setFecha(new Date());	
				}
				solicitud.setIdinstitucion(idInstitucion);
				solicitud.setIdpersona(idPersona);
				solicitud.setIdpeticion((Long.valueOf(ficha.getnSolicitud())));
				solicitud.setTipopeticion("A");
				// Caso en el que el colegio no necesite aprobacion para realizar compras o
				// suscripciones
				if (aprobNecesaria.getValor().equals("N")) {
					solicitud.setIdestadopeticion((short) 20);
				}
				else {
					solicitud.setIdestadopeticion((short) 10);
				}
				Long fechaActual = new Date().getTime();
				//Con el format logramos que siempre una longitud de 10 precedida por 0s
				solicitud.setNumOperacion(
						"1" + idInstitucion.toString() + String.format("%010d", idPersona) + fechaActual.toString()); 
				solicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
				solicitud.setFechamodificacion(new Date());

				response = pysPeticioncomprasuscripcionMapper.insert(solicitud);
				if (response == 0)
					throw new Exception("Error al insertar la solicitud de compra en la BBDD.");

				LOGGER.info(
						"solicitarCompra() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para crear una solicitud de compra");

				LOGGER.info(
						"solicitarCompra() / updateProductosPeticion() -> Entrada a pysProductossolicitadosMapper para introducir los productos solicitado");

				this.updateProductosPeticion(request, ficha);

				LOGGER.info(
						"solicitarCompra() / updateProductosPeticion() -> Salida de pysProductossolicitadosMapper para introducir los productos solicitados");

				// Al no necesitar aprobación, se crea el registro de compra
				// inmediatamente
				if (aprobNecesaria.getValor().equals("N")) {
					
					this.aprobarCompra(request, ficha);

				}

				insertResponseDTO.setStatus("200");
			}

		}

		insertResponseDTO.setError(error);
		LOGGER.debug("solicitarCompra() -> Salida del servicio para crear una solicitud de compra");
		
		return insertResponseDTO;
	}

	private GenParametros getParametroAprobarSolicitud(Short idInstitucion) {
		
		GenParametrosExample genParametrosExample = new GenParametrosExample();
        genParametrosExample.createCriteria().andModuloEqualTo("PYS").andParametroEqualTo("APROBAR_SOLICITUD_COMPRA")
                .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
        genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

        List<GenParametros> params = genParametrosMapper.selectByExample(genParametrosExample);

		return params.get(0);
	}

	@Override
	@Transactional(timeout=24000)
	public InsertResponseDTO solicitarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("solicitarSuscripcion() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"solicitarSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"solicitarSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				// Si no es un colegiado se lanza un error ya que no deberia tener acceso a este
				// servicio.
				// Se omite esta llamada para que no afecte a llamadas desde otros puntos de la
				// aplicacion
//				if(letrado.equals("N"))
//					throw new Exception("El usuario conectado no es un colegiado y no deberia tener acceso a este servicio");
				
				Long idPersona = Long.valueOf(ficha.getIdPersona());
				String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
				//Si es colegiado se debe coger el idpersona del usuario conectado
				if(!letrado.equals("N")) {
				CenPersonaExample persExample = new CenPersonaExample();

				persExample.createCriteria().andNifcifEqualTo(dni);

				List<CenPersona> personas = cenPersonaMapper.selectByExample(persExample);

				idPersona = personas.get(0).getIdpersona();
				}

				GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);

				LOGGER.info(
						"solicitarSuscripcion() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para crear una solicitud de suscripcion");

				PysPeticioncomprasuscripcion solicitud = new PysPeticioncomprasuscripcion();

				solicitud.setFecha(new Date());
				solicitud.setIdinstitucion(idInstitucion);
				solicitud.setIdpersona(idPersona);
				solicitud.setIdpeticion((Long.valueOf(ficha.getnSolicitud())));
				solicitud.setTipopeticion("A");
				// Caso en el que el colegio no necesite aprobacion para realizar compras o
				// suscripciones
				if (aprobNecesaria.getValor().equals("N")) {
					solicitud.setIdestadopeticion((short) 20);
				} else {
					solicitud.setIdestadopeticion((short) 10);
				}
				solicitud.setFecha(new Date());
				Long fechaActual = new Date().getTime();
				//Con el format logramos que siempre una longitud de 10 precedida por 0s
				solicitud.setNumOperacion(
						"1" + idInstitucion.toString() + String.format("%010d", idPersona) + fechaActual.toString());
				
				solicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
				solicitud.setFechamodificacion(new Date());

				response = pysPeticioncomprasuscripcionMapper.insert(solicitud);
				if (response == 0)
					throw new Exception("Error al insertar la solicitud de suscripcion en la BBDD.");

				LOGGER.info(
						"solicitarSuscripcion() / pysPeticionsuscripcionsuscripcionMapper.insert() -> Salida de pysPeticionsuscripcionsuscripcionMapper para crear una solicitud de suscripcion");

				LOGGER.info(
						"solicitarSuscripcion() / updateServiciosPeticion() -> Entrada a updateServiciosPeticion para introducir el servicio solicitado");

				this.updateServiciosPeticion(request, ficha);

				LOGGER.info(
						"solicitarSuscripcion() / updateServiciosPeticion() -> Salida de updateServiciosPeticion para introducir el servicio solicitado");

				// Al no necesitar aprobación, se crea el registro de suscripcion
				// inmediatamente. Aunque el nombre del parametro puede inducir error,
				// tambien hace referencia a servicios como se indica en el documento funcional.
				LOGGER.info("solicitarSuscripcion() EL VALOR QUE TENEMOS PARA LA NECESIDAD DE APROBACION ES "+aprobNecesaria.getValor());
				if (aprobNecesaria.getValor().equals("N")) {
					LOGGER.info(
							"solicitarSuscripcion() / aprobarSuscripcion() -> Entrada a  aprobarSuscripcion ya que la institucion aprueba las solicitudes automaticamente");


					this.aprobarSuscripcion(request, ficha);

				}

				insertResponseDTO.setStatus("200");
			}
		}

		insertResponseDTO.setError(error);
		LOGGER.debug("solicitarSuscripcion() -> Salida del servicio para crear una solicitud de suscripción");

		return insertResponseDTO;
	}

	@Override
	@Transactional(timeout=24000)
	public UpdateResponseDTO aprobarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("aprobarSuscripcion() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

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
							"aprobarSuscripcion() / pysPeticioncomprasuscripcionMapper.selectByPrimaryKey() -> Entrada a pysPeticioncomprasuscripcionMapper para extraer la peticion asociada");
					
					PysPeticioncomprasuscripcionKey solicitudKey = new PysPeticioncomprasuscripcionKey();

					solicitudKey.setIdinstitucion(idInstitucion);
					solicitudKey.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));

					PysPeticioncomprasuscripcion solicitud = pysPeticioncomprasuscripcionMapper
							.selectByPrimaryKey(solicitudKey);

					LOGGER.info(
							"aprobarSuscripcion() / pysPeticioncomprasuscripcionMapper.selectByPrimaryKey() -> Salida de pysPeticioncomprasuscripcionMapper para extraer la peticion asociada");

					
					
					
					//En el caso que se realice la aprobación desde la pantalla "Suscripcion de servicios"
					//O se realice una aprobación directa
					if(solicitud==null) {
						this.solicitarSuscripcion(request, ficha);
						solicitud = pysPeticioncomprasuscripcionMapper
								.selectByPrimaryKey(solicitudKey);
					}

					GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);
					

					
						LOGGER.info(
								"aprobarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para aprobar una solicitud de suscripcion");
	
						PysSuscripcion suscripcion = new PysSuscripcion();
	
						suscripcion.setFechasuscripcion(new Date());
						suscripcion.setFechamodificacion(new Date());
						suscripcion.setIdinstitucion(idInstitucion);
						suscripcion.setIdpersona(solicitud.getIdpersona());
						suscripcion.setIdpeticion(Long.valueOf(ficha.getnSolicitud()));
						suscripcion.setUsumodificacion(usuarios.get(0).getIdusuario());
	
						for (ListaServiciosSuscripcionItem servicio : ficha.getServicios()) {
	
							suscripcion.setIdservicio((long) servicio.getIdServicio());
							suscripcion.setIdtiposervicios((short) servicio.getIdTipoServicios());
							suscripcion.setIdserviciosinstitucion((long) servicio.getIdServiciosInstitucion());
							
							//Obtenemos el id para la nueva suscripcion
							LOGGER.info(
									"aprobarSuscripcion() / pysSuscripcionMapper.getNewIdCola() -> Entrada a pysSuscripcionMapper para obtener el id para la nueva suscripcion");
							
							PysSuscripcion sus = new PysSuscripcion();
							
							sus.setIdinstitucion(idInstitucion);
							long newIdSus = pysSuscripcionMapper.getNewIdSus(sus);

							LOGGER.info(
									"aprobarSuscripcion() / pysSuscripcionMapper.getNewIdCola() -> Salida de pysSuscripcionMapper para obtener el id para la nueva suscripcion");
							
							suscripcion.setIdsuscripcion(newIdSus);
							suscripcion.setDescripcion(servicio.getDescripcion());
							//Si se ha seleccionado como forma de pago "No facturable"
							if(ficha.getIdFormaPagoSeleccionada().equals("-1")) {
								//servicioSolicitado.setIdformapago(null);
								//Al no poder poner a nulo la forma de pago se el asigna el valor del 
								//elemento del combo equivalente a "No facturable" en el combo de formas de pago del front
								suscripcion.setIdformapago((short) 80);
							}
							else{
								suscripcion.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
							}
							if(ficha.getIdFormaPagoSeleccionada().equals("80") || ficha.getIdFormaPagoSeleccionada().equals("20")) {
								suscripcion.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
							}
							else {
								suscripcion.setIdcuenta(null);
							}
							suscripcion.setCantidad(Integer.valueOf(servicio.getCantidad()));
							if(servicio.getIdPrecioServicio()!=null) {
								suscripcion.setImporteunitario(new BigDecimal(servicio.getPrecioServicioValor()));
							}
							else {
								suscripcion.setImporteunitario(new BigDecimal(0));
							}
							
							response = pysSuscripcionMapper.insert(suscripcion);
							if (response == 0)
								throw new Exception("Error al insertar un registro de suscripcion en la BBDD.");
						}
						
						LOGGER.info(
								"aprobarSuscripcion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para aprobar una solicitud de suscripcion");

					}
				

				
				updateResponseDTO.setStatus("200");
			}

		updateResponseDTO.setError(error);
		LOGGER.debug("aprobarSuscripcion() -> Salida del servicio para aprobar una solicitud de compra");

		return updateResponseDTO;
	}

	@Override
	@Transactional(timeout=24000)
	public UpdateResponseDTO aprobarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("aprobarCompra() -> Entrada al servicio para crear una solicitud de suscripción");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

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
						solicitud = pysPeticioncomprasuscripcionMapper
								.selectByPrimaryKey(solicitudKey);
					}
					

					GenParametros aprobNecesaria = getParametroAprobarSolicitud(idInstitucion);
					

					
						LOGGER.info(
								"aprobarCompra() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para aprobar una solicitud de compra");
	
						PysCompra compra = new PysCompra();
	
						compra.setFecha(new Date());
						compra.setFechamodificacion(new Date());
						compra.setIdinstitucion(idInstitucion);
						compra.setIdpersona(solicitud.getIdpersona());
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
								compra.setIdformapago((short) 80);
								compra.setNofacturable("1");
							}
							else{
								compra.setIdformapago(Short.valueOf(ficha.getIdFormaPagoSeleccionada()));
								compra.setNofacturable("0");
							}
							if(ficha.getIdFormaPagoSeleccionada().equals("80") || ficha.getIdFormaPagoSeleccionada().equals("20")) {
								if (ficha.getCuentaBancSelecc() != null) {
									compra.setIdcuenta(Short.valueOf(ficha.getCuentaBancSelecc()));
								}else {
									compra.setIdcuenta(null);
								}
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
				

				
				updateResponseDTO.setStatus("200");
			}

		updateResponseDTO.setError(error);
		LOGGER.debug("aprobarCompra() -> Salida del servicio para aprobar una solicitud de compra");

		return updateResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO denegarPeticion(HttpServletRequest request, String nSolicitud)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("denegarPeticion() -> Entrada al servicio para crear la denegación de la petición");

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
				//Con el format logramos que siempre una longitud de 10 precedida por 0s
				solicitudBaja.setNumOperacion(
						"1" + idInstitucion.toString() + String.format("%010d", solicitudAlta.getIdpersona()) + fechaActual.toString());

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
		LOGGER.debug("denegarPeticion() -> Salida del servicio para crear una solicitud de suscripción");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO denegarPeticionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("denegarPeticionMultiple() -> Entrada al servicio para crear la denegación de una o varias peticiones");

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
		LOGGER.debug("denegarPeticionMultiple() -> Salida del servicio para crear la denegación de una o varias peticiones");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO aprobarCompraMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("aprobarCompraMultiple() -> Entrada al servicio para aprobar una o varias peticiones de compra");

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
		LOGGER.debug("aprobarCompraMultiple() -> Salida del servicio para aprobar una o varias peticiones de compra");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO aprobarSuscripcionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("aprobarSuscripcionMultiple() -> Entrada al servicio para aprobar una o varias peticiones de suscripcion");

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
					"aprobarSuscripcionMultiple() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"aprobarSuscripcionMultiple() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"aprobarSuscripcionMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para aprobar una o varias peticiones de suscripcion");

				//Se guardaran aqui los numeros de solicitud de aquellas solicitudes no validas por el estado en el que vienen
				error.setDescription("");
				for(FichaCompraSuscripcionItem peticion : peticiones) {
					if(peticion.getFechaDenegada() != null || peticion.getFechaAceptada() != null) {
						error.setDescription(error.getDescription()+" "+peticion.getnSolicitud()+",");
					}
					else {
						peticion.setServicios(pysPeticioncomprasuscripcionExtendsMapper.getListaServiciosSuscripcion(idInstitucion, peticion.getnSolicitud(), usuarios.get(0).getIdlenguaje(), null));
						this.aprobarSuscripcion(request, peticion);
					}
				}

				if(!error.getDescription().equals(""))error.setDescription(error.getDescription().substring(0, error.getDescription().length()-1));
				
				
				LOGGER.info(
						"aprobarSuscripcionMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para aprobar una o varias peticiones de suscripcion");

				LOGGER.info(
						"aprobarSuscripcionMultiple() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para aprobar una o varias peticiones de suscripcion");
			}

			LOGGER.info(
					"aprobarSuscripcionMultiple() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para crear una solicitud de una o varias peticiones de suscripcion");

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.debug("aprobarSuscripcionMultiple() -> Salida del servicio para aprobar una o varias peticiones de suscripcion");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO updateProductosPeticion(HttpServletRequest request, FichaCompraSuscripcionItem peticion)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("updateProductosPeticion() -> Entrada al servicio para actualizar los productos solicitados asociados con una solicitud");

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
				
				Long idPersona = Long.valueOf(peticion.getIdPersona());
				String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
				//Si es colegiado se debe coger el idpersona del usuario conectado
				if(!letrado.equals("N")) {
				CenPersonaExample persExample = new CenPersonaExample();

				persExample.createCriteria().andNifcifEqualTo(dni);

				List<CenPersona> personas = cenPersonaMapper.selectByExample(persExample);

				idPersona = personas.get(0).getIdpersona();
				}
				productoSolicitado.setIdpersona(idPersona);
				productoSolicitado.setIdpeticion(Long.valueOf(peticion.getnSolicitud()));
				
				
				//Si se ha seleccionado como forma de pago "No facturable"
				if(peticion.getIdFormaPagoSeleccionada().equals("-1")) {
					//De forma temporal se utilizara el id 80 que se refiere a pago por domiciliacion bancaria 
					//que no tendra cuenta bancaria seleccionada.
					productoSolicitado.setIdformapago((short) 80);
					productoSolicitado.setNofacturable("1");
				}
				else{
					productoSolicitado.setIdformapago(Short.valueOf(peticion.getIdFormaPagoSeleccionada()));
					productoSolicitado.setNofacturable("0");
				}
				//En el caso que la forma de pago sea domiciliación bancaria
				if(peticion.getIdFormaPagoSeleccionada().equals("80") || peticion.getIdFormaPagoSeleccionada().equals("20")) {
					productoSolicitado.setIdcuenta(Short.valueOf(peticion.getCuentaBancSelecc()));
				}
				else {
					productoSolicitado.setIdcuenta(null);
				}
				
				productoSolicitado.setFechamodificacion(new Date());
				productoSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());
				
				
				
				int i = 0;
				
				for (ListaProductosCompraItem producto : peticion.getProductos()) {
					
					productoSolicitado.setIdproducto((long) producto.getIdproducto());
					productoSolicitado.setIdtipoproducto((short) producto.getIdtipoproducto());
					productoSolicitado.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());

					productoSolicitado.setObservaciones(producto.getObservaciones());
					
					//Se realiza una comprobación extra para comprobar el estado de la compra para saber los elementos que se pueden editar.
					PysCompraExample compraExample = new PysCompraExample();
					
					compraExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpeticionEqualTo(Long.valueOf(peticion.getnSolicitud()));
					
					
					List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(compraExample);
					
					//Se hace la comprobación extra para saber si la peticion de compra ha sido aceptada ya o no
					if(comprasPeticion.isEmpty()) {
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
						
						response = pysProductossolicitadosMapper.insert(productoSolicitado);
					}
					else {
						productosViejos.get(i).setObservaciones(producto.getObservaciones());
						
						productosViejos.get(i).setFechamodificacion(new Date());
						productosViejos.get(i).setUsumodificacion(usuarios.get(0).getIdusuario());
						
						response = pysProductossolicitadosMapper.insert(productosViejos.get(i));
					}

					i++;
					
					if (response == 0)
						throw new Exception("Error al insertar un producto solicitado en la BBDD.");
				}

				LOGGER.info(
						"updateProductosPeticion() / pysProductossolicitadosMapper.insert() -> Salida de pysProductossolicitadosMapper para insertar los productos solicitados asociados con una solicitud");
			}

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.debug("updateProductosPeticion() -> Salida del servicio para actualizar los productos solicitados asociados con una solicitud");

		return insertResponseDTO;
	}
	
	@Override
	public ListaProductosCompraDTO getListaProductosCompra(HttpServletRequest request, String idPeticion){

		ListaProductosCompraDTO listaProductosCompra = new ListaProductosCompraDTO();
		Error error = new Error();

		LOGGER.debug("getListaProductosCompra() -> Entrada al servicio para obtener la informacion de los productos de una peticion");

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
						"getListaProductosCompra() / pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra() -> Entrada a pysPeticioncomprasuscripcionExtendsMapper para obtener la informacion de los productos de una peticion");

				List<ListaProductosCompraItem> productosCompra = pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra(idInstitucion, idPeticion);

				listaProductosCompra.setListaProductosCompraItems(productosCompra);
				
				error.setCode(200);
				
				listaProductosCompra.setError(error);
				
				LOGGER.info(
						"getListaProductosCompra() / pysPeticioncomprasuscripcionExtendsMapper.getListaProductosCompra() -> Salida de pysPeticioncomprasuscripcionExtendsMapper para obtener la informacion de los productos de una peticion");
			}
		}
		LOGGER.debug("getListaProductosCompra() -> Salida del servicio para obtener la informacion de los productos de una peticion");

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

		LOGGER.debug("getFacturasPeticion() -> Entrada al servicio para obtener las facturas de una petición");

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
							
							facturaExample.setOrderByClause("fechaEmision desc");

							LOGGER.info(
									"getFacturasPeticion() / facFacturaMapper.selectByExample() -> Entrada a facFacturaMapper para recuperar las posibles facturas asociadas a la peticion de compra");
							
							List<FacFactura> facturasPeticion = facFacturaMapper.selectByExample(facturaExample);
							
							LOGGER.info(
									"getFacturasPeticion() / facFacturaMapper.selectByExample() ->Salida de facFacturaMapper para recuperar las posibles facturas asociadas a la peticion de compra");
							
							List<ListaFacturasPeticionItem> facturasListaPeticion = new ArrayList<ListaFacturasPeticionItem>();
							
							for(FacFactura factura : facturasPeticion) {
								ListaFacturasPeticionItem f = new ListaFacturasPeticionItem();
								f.setIdFactura(factura.getIdfactura());
								f.setFechaFactura(factura.getFechaemision());
								f.setEstado(factura.getEstado());
								f.setImporte(factura.getImptotal());// REVISAR: ¿Preguntar sobre a que importe se refiere
								f.setnFactura(factura.getNumerofactura());
								f.setTipo("Factura"); //REVISAR ¿Como se distingue una factura de una anulación?
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
								
								facturaExample.setOrderByClause("fechaInicio desc");
							
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
								f.setTipo("Factura"); // REVISAR: ¿Como se distingue una factura de una anulación?
								facturasListaPeticion.add(f);
							}
							
							listaFacturasDTO.setListaFacturasPeticionItem(facturasListaPeticion);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"getFacturasPeticion() -> Se ha producido un error al obtener las facturas de una petición",
					e);
			error.setCode(500);
			error.setDescription(e.getMessage());
			listaFacturasDTO.setError(error);
		}

		LOGGER.debug("getFacturasPeticion() -> Salida del servicio para obtener las facturas de una petición");

		return listaFacturasDTO;
	}
	
	@Override
	public ListaDescuentosPeticionDTO getDescuentosPeticion(HttpServletRequest request, String nSolicitud) {

		ListaDescuentosPeticionDTO listaDescuentosDTO = new ListaDescuentosPeticionDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("getDescuentosPeticion() -> Entrada al servicio para obterner los descuentos y anticipos de una petición");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getDescuentosPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getDescuentosPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getDescuentosPeticion() / pysProductossolicitadosMapper.selectByExample() -> Entrada a pysProductossolicitadosMapper para recuperar los posibles productos solicitados asociados a la peticion");

					PysProductossolicitadosExample productosExample = new PysProductossolicitadosExample();

					productosExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud))
							.andIdinstitucionEqualTo(idInstitucion);

					// Se comprueba si la peticion es de compra o de suscripcion
					List<PysProductossolicitados> productosPeticion = pysProductossolicitadosMapper.selectByExample(productosExample);

					LOGGER.info(
							"getDescuentosPeticion() / pysProductossolicitadosMapper.selectByExample() -> Salida de pysProductossolicitadosMapper para recuperar los posibles productos solicitados asociados a la peticion");

					// Si la peticion tiene alguna compra asociada se extraen las facturas de las
					// compras
					if (!productosPeticion.isEmpty()) {
						
						List<Long> numLineasPeticion = new ArrayList<Long>();
						
						LOGGER.info(
								"getDescuentosPeticion() / pysProductossolicitadosMapper.selectByExample() -> Entrada a pysCompraMapper para recuperar las posibles compras asociadas a la peticion");

						
						PysCompraExample comprasExample = new PysCompraExample();

						comprasExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud))
								.andIdinstitucionEqualTo(idInstitucion);

						// Se comprueba si la peticion es de compra o de suscripcion
						List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(comprasExample);
						
						LOGGER.info(
								"getDescuentosPeticion() / pysProductossolicitadosMapper.selectByExample() -> Salida de pysCompraMapper para recuperar las posibles compras asociadas a la peticion");

						
//						for(PysCompra compra : comprasPeticion) {
//							
//							if(compra.getNumerolinea() != null) {
//								numLineasPeticion.add(compra.getNumerolinea());
//							}
//							
//							
//						}
//						
//						if(!numLineasPeticion.isEmpty()) {
//							// Se obtienen las lineas de anticipo asociadas a la peticion
//							PysLineaanticipoExample lineaAnticipoExample = new PysLineaanticipoExample();
//							
//							lineaAnticipoExample.createCriteria().andNumerolineaIn(numLineasPeticion).andIdinstitucionEqualTo(idInstitucion);
//							
//							List<PysLineaanticipo> lineasPeticion = pysLineaAnticipoMapper.selectByExample(lineaAnticipoExample);
//							
//							List<Short> idAnticiposPeticion = new ArrayList<Short>();
//							
//							for(PysLineaanticipo linea : lineasPeticion) {
//								idAnticiposPeticion.add(linea.getIdanticipo());
//							}
//	
//							
//							PysAnticipoletradoExample anticipoExample = new PysAnticipoletradoExample();
//	
//							anticipoExample.createCriteria()
//									.andIdanticipoIn(idAnticiposPeticion)
//									.andIdinstitucionEqualTo(idInstitucion);
//	
//							// Se obtienen los anticipos asociados a la peticion
//							List<PysAnticipoletrado> anticiposPeticion = pysAnticipoLetradoMapper.selectByExample(anticipoExample);
	
						
						//Se obtiene el importe anticipado en las distintas compras
							float anti = 0;
							
							for (PysCompra compra : comprasPeticion) {
								
								if(compra.getImporteanticipado() != null) {
									anti += (float) compra.getImporteanticipado().doubleValue();
								}
								
							}
							
							ListaDescuentosPeticionItem descuentoPeticion = new ListaDescuentosPeticionItem();
	
							descuentoPeticion.setIdPeticion(comprasPeticion.get(0).getIdpeticion().toString());
							descuentoPeticion.setImporte(new BigDecimal(anti));
							descuentoPeticion.setTipo("1"); //En el front se procesará y representará "Anticipo / Descuento"
	
							List<ListaDescuentosPeticionItem> descuentosListaPeticion = new ArrayList<ListaDescuentosPeticionItem>();
							
							if(anti != 0) {
								descuentosListaPeticion.add(descuentoPeticion);
							}
							
							listaDescuentosDTO.setListaDescuentosPeticionItem(descuentosListaPeticion);
						
					} else {
						PysServiciossolicitadosExample serviciosExample = new PysServiciossolicitadosExample();

						serviciosExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"getDescuentosPeticion() / pysServiciossolicitadosMapper.selectByExample() -> Entrada a pysServiciossolicitadosMapper para recuperar los servicios solicitados asociados a la peticion de suscripcion");

						List<PysServiciossolicitados> serviciosPeticion = pysServiciossolicitadosMapper
								.selectByExample(serviciosExample);

						LOGGER.info(
								"getDescuentosPeticion() / pysServiciossolicitadosMapper.selectByExample() -> Entrada a pysServiciossolicitadosMapper para recuperar los servicios solicitados asociados a la peticion de suscripcion");

						// Si la peticion tiene algun servicio asociado se extrae el monedero del cliente
						// si lo tuviera
						if (!serviciosPeticion.isEmpty()) {

							//Buscamos los monederos asociados al servicio de la peticion y a esa persona
							List<PysServicioanticipo> serviciosAnticipo = new ArrayList<PysServicioanticipo>();
							
							PysServicioanticipoExample servicioAnticipoExample = new PysServicioanticipoExample();
							
							
							for(PysServiciossolicitados servicio : serviciosPeticion) {
								servicioAnticipoExample.createCriteria().andIdpersonaEqualTo(serviciosPeticion.get(0).getIdpersona()).andIdinstitucionEqualTo(idInstitucion)
								.andIdservicioEqualTo(servicio.getIdservicio()).andIdserviciosinstitucionEqualTo(servicio.getIdserviciosinstitucion())
								.andIdtiposerviciosEqualTo(servicio.getIdtiposervicios());
								LOGGER.info(
										"getDescuentosPeticion() / pysServicioanticipoMapper.selectByExample() -> Entrada a pysServicioanticipoMapper para comprobar si el cliente tiene algún monedero con los servicios solicitados asociados a la peticion de suscripcion");

								
								List<PysServicioanticipo> anticiposSuscripcion =  pysServicioanticipoMapper.selectByExample(servicioAnticipoExample);
								serviciosAnticipo.addAll(anticiposSuscripcion);
								
								LOGGER.info(
										"getDescuentosPeticion() / pysServicioanticipoMapper.selectByExample() -> Entrada a pysServicioanticipoMapper para comprobar si el cliente tiene algún monedero con los servicios solicitados asociados a la peticion de suscripcion");

							}
							
							//Si hay un monedero asociado al servicio y a esa persona
							if(!serviciosAnticipo.isEmpty()) {

								List<ListaDescuentosPeticionItem> descuentosListaPeticion = new ArrayList<ListaDescuentosPeticionItem>();
								
								//Se debe comprobar si dichos monederos tienen saldo positivo y elegir uno
								//Este es el monedero que se mostrara en la ficha
								LOGGER.info(
										"getDescuentosPeticion() / pysLineaanticipoExtendsMapper.getMonederoServicio() -> Entrada a pysServicioanticipoMapper para comprobar si el cliente tiene algún monedero con saldo con los servicios solicitados asociados a la peticion de suscripcion");
 
								ListaDescuentosPeticionItem monedero = pysLineaanticipoExtendsMapper.getMonederoServicio(serviciosAnticipo.get(0));

								LOGGER.info(
										"getDescuentosPeticion() / pysLineaanticipoExtendsMapper.getMonederoServicio() -> Entrada a pysServicioanticipoMapper para comprobar si el cliente tiene algún monedero con saldo con los servicios solicitados asociados a la peticion de suscripcion");

								if(monedero != null) {
									monedero.setIdPeticion(nSolicitud);
									monedero.setTipo("2"); //En el front se procesará y representará como "Monedero"
									monedero.setIdAnticipo(monedero.getIdAnticipo());
	
									descuentosListaPeticion.add(monedero);
	
									listaDescuentosDTO.setListaDescuentosPeticionItem(descuentosListaPeticion);
								}
							}
						}
					}
				}
			}
		} catch (

		Exception e) {
			LOGGER.error(
					"getDescuentosPeticion() -> Se ha producido un error al obtener los descuentos y anticipos de una petición",
					e);
			error.setCode(500);
			error.setDescription(e.getMessage());
			listaDescuentosDTO.setError(error);
		}

		LOGGER.debug("getDescuentosPeticion() -> Salida del servicio para obtener los descuentos y anticipos de una petición");

		return listaDescuentosDTO;
	}


	@Override
	@Transactional
	public DeleteResponseDTO deleteAnticipoPeticion(HttpServletRequest request, List<ListaDescuentosPeticionItem> anticiposLista)
			throws SigaExceptions {

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug(
				"deleteAnticipoPeticion() -> Entrada al servicio para eliminar anticipos de a una solicitud");

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
					"deleteAnticipoPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteAnticipoPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				
				for(ListaDescuentosPeticionItem anticipoLista : anticiposLista) {
				
					PysPeticioncomprasuscripcionKey peticionKey = new PysPeticioncomprasuscripcionKey();
	
					peticionKey.setIdinstitucion(idInstitucion);
					peticionKey.setIdpeticion(Long.valueOf(anticipoLista.getIdPeticion()));
	
					PysPeticioncomprasuscripcion peticion = pysPeticioncomprasuscripcionMapper
							.selectByPrimaryKey(peticionKey);
	
					
					PysAnticipoletradoKey anticipoKey = new PysAnticipoletradoKey();
					
					anticipoKey.setIdanticipo(anticipoLista.getIdAnticipo());
					anticipoKey.setIdinstitucion(idInstitucion);
					anticipoKey.setIdpersona(peticion.getIdpersona());
	
					LOGGER.info(
							"deleteAnticipoPeticion() / pysAnticipoLetradoMapper.insert() -> Entrada a pysAnticipoLetradoMapper para insertar el nuevo anticipo de una solicitud");
	
					// Se elimina un anticipo
					response = pysAnticipoLetradoMapper.deleteByPrimaryKey(anticipoKey);
					if (response == 0) {
						throw new SigaExceptions("Error al eliminar un anticipo de una solicitud");
					}
	
					LOGGER.info(
							"deleteAnticipoPeticion() / pysAnticipoLetradoMapper.insert() -> Salida de pysAnticipoLetradoMapper para insertar el nuevo anticipo de una solicitud");
	
					LOGGER.info(
							"deleteAnticipoPeticion() / pysCompraMapper.selectByExample() -> Entrada a pysCompraMapper para recuperar las posibles compras asociadas a la peticion");
	
					PysCompraExample compraExample = new PysCompraExample();
	
					compraExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(anticipoLista.getIdPeticion()))
							.andIdinstitucionEqualTo(idInstitucion);
	
					// Se comprueba si la peticion es de compra o de suscripcion
					List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(compraExample);
	
					LOGGER.info(
							"deleteAnticipoPeticion() / pysCompraMapper.selectByExample() -> Salida de pysCompraMapper para recuperar las posibles compras asociadas a la peticion");
	
					// Si la peticion tiene alguna compra asociada se extraen las lineas de compra
					if (!comprasPeticion.isEmpty()) {		
						
						PysLineaanticipoExample lineaAnticipoExample = new PysLineaanticipoExample();
						
						lineaAnticipoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdanticipoEqualTo(anticipoLista.getIdAnticipo());
					
						
						LOGGER.info(
								"deleteAnticipoPeticion() / pysLineaAnticipoMapper.deleteByExample() -> Entrada a pysLineaAnticipoMapper para insertar una nueva linea de anticipo de una solicitud");
		
						response = pysLineaAnticipoMapper.deleteByExample(lineaAnticipoExample);
		
						LOGGER.info(
								"deleteAnticipoPeticion() / pysLineaAnticipoMapper.deleteByExample() -> Salida de pysLineaAnticipoMapper para insertar una nueva linea de anticipo de una solicitud");
		
						if (response == 0) {
							throw new SigaExceptions("Error al eliminar una linea de anticipo de una solicitud");
						}
					}else {
						PysSuscripcionExample suscripcionExample = new PysSuscripcionExample();

						suscripcionExample.createCriteria().andIdpeticionEqualTo(peticion.getIdpeticion())
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"deleteAnticipoPeticion() / pysSuscripcionMapper.selectByExample() -> Entrada a pysSuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");

						List<PysSuscripcion> suscripcionesPeticion = pysSuscripcionMapper
								.selectByExample(suscripcionExample);

						LOGGER.info(
								"deleteAnticipoPeticion() / pysSuscripcionMapper.selectByExample() -> Entrada a pysSuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");

						// Si la peticion tiene alguna suscripcion asociada se extraen las facturas de
						// las suscripciones
						if (!suscripcionesPeticion.isEmpty()) {
							
							PysServicioanticipoExample servicioAnticipoExample = new PysServicioanticipoExample();
							
							servicioAnticipoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdanticipoEqualTo(anticipoLista.getIdAnticipo())
							.andIdservicioEqualTo(suscripcionesPeticion.get(0).getIdservicio()).andIdserviciosinstitucionEqualTo(suscripcionesPeticion.get(0).getIdserviciosinstitucion())
							.andIdtiposerviciosEqualTo(suscripcionesPeticion.get(0).getIdtiposervicios());
						
							
							LOGGER.info(
									"deleteAnticipoPeticion() / pysServicioanticipoMapper.deleteByExample() -> Entrada a pysServicioanticipoMapper para eliminar una linea de anticipo de una solicitud");
			
							response = pysServicioanticipoMapper.deleteByExample(servicioAnticipoExample);
			
							LOGGER.info(
									"deleteAnticipoPeticion() / pysServicioanticipoMapper.deleteByExample() -> Salida de pysServicioanticipoMapper para eliminar una linea de anticipo de una solicitud");
			
							if (response == 0) {
								throw new SigaExceptions("Error al eliminar anticipos de a una solicitud");
							}
						}
					}
				}

				deleteResponseDTO.setStatus("200");
			}

			deleteResponseDTO.setError(error);
			LOGGER.debug(
					"deleteAnticipoPeticion() -> Salida del servicio para eliminar anticipos de a una solicitud");

			
		}
		
		return deleteResponseDTO;
	}
	
	
	@Override
	@Transactional
	public InsertResponseDTO saveAnticipoPeticion(HttpServletRequest request, ListaDescuentosPeticionItem anticipoLista)
			throws SigaExceptions {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug(
				"saveAnticipo() -> Entrada al servicio para asociar un nuevo anticipo a una solicitud");

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
					"saveAnticipo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"saveAnticipo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				
				PysPeticioncomprasuscripcionKey peticionKey = new PysPeticioncomprasuscripcionKey();

				peticionKey.setIdinstitucion(idInstitucion);
				peticionKey.setIdpeticion(Long.valueOf(anticipoLista.getIdPeticion()));

				PysPeticioncomprasuscripcion peticion = pysPeticioncomprasuscripcionMapper
						.selectByPrimaryKey(peticionKey);

				// Se inicializa el nuevo anticipo
				PysAnticipoletrado anticipo = new PysAnticipoletrado();

				anticipo.setDescripcion("Anticipo creado para la solicitud Nº " + anticipoLista.getIdPeticion());
				anticipo.setFecha(new Date()); // REVISAR: ¿QUE REPRESENTA ESTA FECHA? SE SOSPECHA QUE SE REFIERE A LA FECHA DE CREACION
				anticipo.setIdpersona(peticion.getIdpersona());

				anticipo.setUsumodificacion(usuarios.get(0).getIdusuario());
				anticipo.setFechamodificacion(new Date());
				anticipo.setIdinstitucion(idInstitucion);

				// Calculamos el ID del anticipo
				PysAnticipoletradoExample anticipoExample = new PysAnticipoletradoExample();

				anticipoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdpersonaEqualTo(peticion.getIdpersona());

				short nuevoIdAnticipo = (short) (pysAnticipoLetradoMapper.countByExample(anticipoExample) + 1);

				anticipo.setIdanticipo(nuevoIdAnticipo);

				anticipo.setImporteinicial(anticipoLista.getImporte());

				LOGGER.info(
						"saveAnticipo() / pysAnticipoLetradoMapper.insert() -> Entrada a pysAnticipoLetradoMapper para insertar el nuevo anticipo de una solicitud");

				// Se introduce el nuevo anticipo
				response = pysAnticipoLetradoMapper.insert(anticipo);
				if (response == 0) {
					throw new SigaExceptions("Error al insertar el nuevo anticipo de una solicitud");
				}

				LOGGER.info(
						"saveAnticipo() / pysAnticipoLetradoMapper.insert() -> Salida de pysAnticipoLetradoMapper para insertar el nuevo anticipo de una solicitud");

				LOGGER.info(
						"saveAnticipo() / pysCompraMapper.selectByExample() -> Entrada a pysCompraMapper para recuperar las posibles compras asociadas a la peticion");

				PysCompraExample compraExample = new PysCompraExample();

				compraExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(anticipoLista.getIdPeticion()))
						.andIdinstitucionEqualTo(idInstitucion);

				// Se comprueba si la peticion es de compra o de suscripcion
				List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(compraExample);

				LOGGER.info(
						"saveAnticipo() / pysCompraMapper.selectByExample() -> Salida de pysCompraMapper para recuperar las posibles compras asociadas a la peticion");

				// Si la peticion tiene alguna compra asociada se extraen las facturas de las
				// compras
				if (!comprasPeticion.isEmpty()) {				
				
					// Se crea la linea de anticipo que servira para relacionar el anticipo con las
					// compras de la peticion
					PysLineaanticipo lineaAnticipo = new PysLineaanticipo();
	
					lineaAnticipo.setIdanticipo(nuevoIdAnticipo);
					lineaAnticipo.setIdpersona(peticion.getIdpersona());
					lineaAnticipo.setIdlinea((short) 1);
					lineaAnticipo.setNumerolinea((long) 1);
					lineaAnticipo.setImporteanticipado(anticipoLista.getImporte());
					lineaAnticipo.setLiquidacion("0");
	
					lineaAnticipo.setFechamodificacion(new Date());
					lineaAnticipo.setUsumodificacion(usuarios.get(0).getIdusuario());
					lineaAnticipo.setIdinstitucion(idInstitucion);
	
					LOGGER.info(
							"saveAnticipo() / pysLineaAnticipoMapper.insert() -> Entrada a pysLineaAnticipoMapper para insertar una nueva linea de anticipo de una solicitud");
	
					response = pysLineaAnticipoMapper.insert(lineaAnticipo);
	
					LOGGER.info(
							"saveAnticipo() / pysLineaAnticipoMapper.insert() -> Salida de pysLineaAnticipoMapper para insertar una nueva linea de anticipo de una solicitud");
	
					if (response == 0) {
						throw new SigaExceptions("Error al insertar una nueva linea de anticipo de una solicitud");
					}
				}

				insertResponseDTO.setStatus("200");
			}

			insertResponseDTO.setError(error);
			LOGGER.debug(
					"saveAnticipo() -> Salida del servicio para asociar un nuevo anticipo a una solicitud");

			
		}
		
		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO anularPeticion(HttpServletRequest request, String nSolicitud) throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("anularPeticion() -> Entrada al servicio para anular una petición");

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
						"anularPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"anularPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
						
						//Si es letrado, se considera que no es personal del colegio y que
						//por lo tanto no puede anular la petición directamente sino solicitarla
						if(!letrado.equals("N")) {
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
							//Con el format logramos que siempre una longitud de 10 precedida por 0s
							solicitudBaja.setNumOperacion(
									"1" + idInstitucion.toString() + String.format("%010d", solicitudAlta.getIdpersona()) + fechaActual.toString());

							
							LOGGER.info(
									"anularPeticion() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para solicitar la anulacion una petición");
					

							response = pysPeticioncomprasuscripcionMapper.insert(solicitudBaja);
							
							LOGGER.info(
									"anularPeticion() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para solicitar la anulacion una petición");
		
							if (response == 0)
								throw new Exception("Error al insertar la petición de anulación de la petición en la BBDD.");
						}
						
						else {
							
							//REVISAR ANULACIÓN DE FACTURAS (SE DEBE IMPLEMENTAR)
							
							PysCompraExample compraExample = new PysCompraExample();
	
							compraExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud))
									.andIdinstitucionEqualTo(idInstitucion);
							
							LOGGER.info(
									"anularPeticion() / pysCompraMapper.selectByExample() -> Entrada a pysCompraMapper para recuperar las posibles compras asociadas a la peticion");
	
	
							// Se comprueba si la peticion es de compra o de suscripcion
							List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(compraExample);
	
							LOGGER.info(
									"anularPeticion() / pysCompraMapper.selectByExample() -> Salida de pysCompraMapper para recuperar las posibles compras asociadas a la peticion");
	
							// Si la peticion tiene alguna compra asociada 
							if (!comprasPeticion.isEmpty()) {
		
								for (PysCompra compra : comprasPeticion) {
			
									
									compra.setFechabaja(new Date());
									compra.setFecha(new Date());
									LOGGER.info(
											"anularPeticion() / pysCompraMapper.updateByPrimaryKey() -> Entrada a pysCompraMapper para introducir la fecha de anulacion de la compra asociada a una peticion");
									
									response = pysCompraMapper.updateByPrimaryKey(compra);
									
									LOGGER.info(
											"anularPeticion() / pysCompraMapper.updateByPrimaryKey() -> Salida de pysCompraMapper para introducir la fecha de anulacion de la compra asociada a una peticion");
			
									if (response == 0)
										throw new Exception("Error al anular un registro de compra en la BBDD.");
								}
							} else {
								PysSuscripcionExample suscripcionExample = new PysSuscripcionExample();

								suscripcionExample.createCriteria().andIdpeticionEqualTo(Long.valueOf(nSolicitud))
										.andIdinstitucionEqualTo(idInstitucion);

								LOGGER.info(
										"anularPeticion() / pysSuscripcionMapper.selectByExample() -> Entrada a pysSuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");

								List<PysSuscripcion> suscripcionesPeticion = pysSuscripcionMapper
										.selectByExample(suscripcionExample);

								LOGGER.info(
										"anularPeticion() / pysSuscripcionMapper.selectByExample() -> Entrada a pysSuscripcionMapper para recuperar las posibles facturaciones asociadas a la peticion de suscripcion");

								// Si la peticion tiene alguna suscripcion asociada 
								if (!suscripcionesPeticion.isEmpty()) {
									
									for(PysSuscripcion suscripcion : suscripcionesPeticion) {
										
										suscripcion.setFechabaja(new Date());
										suscripcion.setFechabajafacturacion(new Date());
								
									
										LOGGER.info(
												"anularPeticion() / pysSuscripcionMapper.updateByPrimaryKey() -> Entrada a pysSuscripcionMapper para eliminar una linea de anticipo de una solicitud");
						
										response = pysSuscripcionMapper.updateByPrimaryKey(suscripcion);
						
										LOGGER.info(
												"anularPeticion() / pysSuscripcionMapper.updateByPrimaryKey() -> Salida de pysSuscripcionMapper para eliminar una linea de anticipo de una solicitud");
						
										if (response == 0) {
											throw new SigaExceptions("Error al anular una de las suscripciones asociadas a una solicitud");
										}
									}
								}
							}
						}
					}
				
				updateResponseDTO.setStatus("200");
			}

		updateResponseDTO.setError(error);
		LOGGER.debug("anularPeticion() -> Salida del servicio para anular una petición");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO anularPeticionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("anularPeticionMultiple() -> Entrada al servicio para anular una o varias peticiones");

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
					"anularPeticionMultiple() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"anularPeticionMultiple() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"anularPeticionMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Entrada a pysPeticioncomprasuscripcionMapper para anular una o varias peticiones");

				//Se guardaran aqui los numeros de solicitud de aquellas solicitudes no validas por el estado en el que vienen
				error.setDescription("");
				for(FichaCompraSuscripcionItem peticion : peticiones) {
					if(peticion.getFechaAnulada() == null && (peticion.getFechaAceptada() != null || peticion.getFechaSolicitadaAnulacion() != null)) {
						this.anularPeticion(request, peticion.getnSolicitud());
					}
					else {
						error.setDescription(error.getDescription()+" "+peticion.getnSolicitud()+",");
					}
				}

				if(!error.getDescription().equals(""))error.setDescription(error.getDescription().substring(0, error.getDescription().length()-1));
				
				
				LOGGER.info(
						"anularPeticionMultiple() / pysPeticioncomprasuscripcionMapper.insert() -> Salida de pysPeticioncomprasuscripcionMapper para anular una o varias peticiones");

				LOGGER.info(
						"anularPeticionMultiple() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para anular una o varias peticiones");
			}

			LOGGER.info(
					"anularPeticionMultiple() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para anular una o varias peticiones");

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.debug("anularPeticionnMultiple() -> Salida del servicio para anular una o varias peticiones");

		return insertResponseDTO;
	}

	@Override
	public ListaServiciosSuscripcionDTO getListaServiciosSuscripcion(HttpServletRequest request, String nSolicitud, Date aFechaDe) {
		
			ListaServiciosSuscripcionDTO listaServiciosSuscripcion = new ListaServiciosSuscripcionDTO();
			Error error = new Error();
	
			LOGGER.debug("getListaServiciosSuscripcion() -> Entrada al servicio para obtener la informacion de los servicios de una peticion");
	
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
						"getListaServiciosSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getListaServiciosSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && !usuarios.isEmpty()) {
					
					LOGGER.info(
							"getListaServiciosSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getListaServicios() -> Entrada a pysPeticioncomprasuscripcionExtendsMapper para obtener la informacion de los servicios de una peticion");
	
					List<ListaServiciosSuscripcionItem> serviciosSuscripcion = pysPeticioncomprasuscripcionExtendsMapper.getListaServiciosSuscripcion(idInstitucion, nSolicitud, usuarios.get(0).getIdlenguaje(), aFechaDe);
	
					LOGGER.info(
							"getListaServiciosSuscripcion() / pysPeticioncomprasuscripcionExtendsMapper.getListaServiciosSuscripcion() -> Salida de pysPeticioncomprasuscripcionExtendsMapper para obtener la informacion de los servicios de una peticion");
				
					listaServiciosSuscripcion.setListaServiciosSuscripcionItems(serviciosSuscripcion);
					
					error.setCode(200);
					
					listaServiciosSuscripcion.setError(error);
					
				}
			}
			LOGGER.debug("getListaServiciosSuscripcion() -> Salida del servicio para obtener la informacion de los servicios de una peticion");
	
			return listaServiciosSuscripcion;
		}
	
	
	@Override
	@Transactional
	public InsertResponseDTO updateServiciosPeticion(HttpServletRequest request, FichaCompraSuscripcionItem peticion)
			throws Exception {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("updateServiciosPeticion() -> Entrada al servicio para actualizar los servicios solicitados asociados con una solicitud");

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
					"updateServiciosPeticion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateServiciosPeticion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				LOGGER.info(
						"updateServiciosPeticion() / pysServiciossolicitadosMapper.deleteByExample() -> Entrada a pysServiciossolicitadosMapper para eliminar los servicios solicitados asociados con una solicitud");

				PysServiciossolicitadosExample prodExample = new PysServiciossolicitadosExample();
				
				prodExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpeticionEqualTo(Long.valueOf(peticion.getnSolicitud()));
				//para comprobar que hay servicios a eliminar
				List<PysServiciossolicitados> serviciosViejos = pysServiciossolicitadosMapper.selectByExample(prodExample);
				response = pysServiciossolicitadosMapper.deleteByExample(prodExample);
				//La segunda condicion es para evitar que se lance una excepcion al intentar eliminar un conjunto vacio
				if(response == 0 && !serviciosViejos.isEmpty()) {
					throw new Exception("Eror al eliminar los servicios solicitados de la petición");
				}
				
				LOGGER.info(
						"updateServiciosPeticion() / pysServiciossolicitadosMapper.deleteByExaple() -> Salida de pysServiciossolicitadosMapper para eliminar los servicios solicitados asociados con una solicitud");

				
				
				PysServiciossolicitados servicioSolicitado = new PysServiciossolicitados();

				servicioSolicitado.setIdinstitucion(idInstitucion);

				Long idPersona = Long.valueOf(peticion.getIdPersona());
				String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
				//Si es colegiado se debe coger el idpersona del usuario conectado
				if(!letrado.equals("N")) {
					CenPersonaExample persExample = new CenPersonaExample();
	
					persExample.createCriteria().andNifcifEqualTo(dni);
	
					List<CenPersona> personas = cenPersonaMapper.selectByExample(persExample);
	
					idPersona = personas.get(0).getIdpersona();
				}
				
				servicioSolicitado.setIdpersona(idPersona);
				servicioSolicitado.setIdpeticion(Long.valueOf(peticion.getnSolicitud()));
				//Si se ha seleccionado como forma de pago "No facturable"
				if(peticion.getIdFormaPagoSeleccionada().equals("-1")) {
					//De forma temporal se utilizara el id 80 que se refiere a pago por domiciliacion bancaria 
					//que no tendra cuenta bancaria seleccionada.
					servicioSolicitado.setIdformapago((short) 80);
//					servicioSolicitado.setNofacturable("1");
				}
				else{
					servicioSolicitado.setIdformapago(Short.valueOf(peticion.getIdFormaPagoSeleccionada()));
//					servicioSolicitado.setNofacturable("0");
				}
				//En el caso que la forma de pago sea domiciliación bancaria
				if(peticion.getIdFormaPagoSeleccionada().equals("80") || peticion.getIdFormaPagoSeleccionada().equals("20")) {
					servicioSolicitado.setIdcuenta(Short.valueOf(peticion.getCuentaBancSelecc()));
				}
				else {
					servicioSolicitado.setIdcuenta(null);
				}
				
				servicioSolicitado.setFechamodificacion(new Date());
				servicioSolicitado.setUsumodificacion(usuarios.get(0).getIdusuario());

				for (ListaServiciosSuscripcionItem servicio : peticion.getServicios()) {
					
					servicioSolicitado.setIdservicio((long) servicio.getIdServicio());
					servicioSolicitado.setIdtiposervicios((short) servicio.getIdTipoServicios());
					servicioSolicitado.setIdserviciosinstitucion((long) servicio.getIdServiciosInstitucion());
					if(servicio.getIdPrecioServicio() != null) {
						servicioSolicitado.setIdpreciosservicios(Short.valueOf(servicio.getIdPrecioServicio()));
						servicioSolicitado.setIdperiodicidad(Short.valueOf(servicio.getIdPeriodicidad()));
					}
					else {
						servicioSolicitado.setIdpreciosservicios(null);
						servicioSolicitado.setIdperiodicidad(null);
					}
					
					//REVISAR: 
					servicioSolicitado.setAceptado("A");
					
					servicioSolicitado.setOrden(Short.valueOf(servicio.getOrden()));
					servicioSolicitado.setCantidad(1);
					
					LOGGER.info(
							"updateServiciosPeticion() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para insertar los servicios solicitados asociados con una solicitud");
					
					response = pysServiciossolicitadosMapper.insert(servicioSolicitado);
					if (response == 0)
						throw new Exception("Error al insertar un servicio solicitado en la BBDD.");
				}
				LOGGER.info(
						"updateServiciosPeticion() / pysServiciossolicitadosMapper.insert() -> Salida de pysServiciossolicitadosMapper para insertar los servicios solicitados asociados con una solicitud");
			
				
				
				if(peticion.getFechaAceptada() != null) {
					LOGGER.info(
							"updateServiciosPeticion() / pysSuscripcionMapper.updateByPrimaryKey() -> Entrada a pysSuscripcionesMapper para actualizar las suscripciones asociadas con una solicitud");

					PysSuscripcionExample suscExample = new PysSuscripcionExample();
					
					suscExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpeticionEqualTo(Long.valueOf(peticion.getnSolicitud()));
					//para comprobar que hay suscripciones
					List<PysSuscripcion> listSuscPeticion = pysSuscripcionMapper.selectByExample(suscExample);

					//Se comprueba si se han cambiado la fecha de alta o de baja en el front. Se comprueba solo el primera ya que supuestamente solo se manejara un servicio por suscripcion.
					if(!listSuscPeticion.isEmpty()) {
						for(PysSuscripcion susc : listSuscPeticion) {
							if(!peticion.getFechaAceptada().equals(peticion.getServicios().get(0).getFechaAlta())){
								susc.setFechasuscripcion(peticion.getServicios().get(0).getFechaAlta());
							}
							if(susc.getFechabajafacturacion() != null && !susc.getFechabajafacturacion().equals(peticion.getServicios().get(0).getFechaBaja())){
								//Se modifica unicamente la fecha de bajafacturacion sin importar que el servicio sea automatico o no
								//ya que la fecha de baja determina la fecha de anulacion de la ficha de suscripcion
								//y eso no viene reflejado en el funcional.
								susc.setFechabajafacturacion(peticion.getServicios().get(0).getFechaBaja());
							}
							if(!peticion.getFechaAceptada().equals(peticion.getServicios().get(0).getFechaAlta()) ||
									(susc.getFechabajafacturacion() != null &&!susc.getFechabajafacturacion().equals(peticion.getServicios().get(0).getFechaBaja()))) {
								pysSuscripcionMapper.updateByPrimaryKey(susc);
								if(response == 0) {
									throw new Exception("Eror al actualizar las suscripciones solicitadas de la petición");
								}
							}
						}
					}
					
					LOGGER.info(
							"updateServiciosPeticion() / pysSuscripcionMapper.updateByPrimaryKey() -> Salida de pysSuscripcionesMapper para eliminar las suscripciones asociadas con una solicitud");

				}

				}

			insertResponseDTO.setStatus("200");
		}

		insertResponseDTO.setError(error);
		LOGGER.debug("updateServiciosPeticion() -> Salida del servicio para actualizar los servicios solicitados asociados con una solicitud");

		return insertResponseDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO anadirAnticipoCompra(HttpServletRequest request, ListaDescuentosPeticionItem anticipo)
			throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		LOGGER.debug("anadirAnticipoCompra() -> Entrada al servicio para añadir un anticipo a una compra");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"anadirAnticipoCompra() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"anadirAnticipoCompra() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				// Obtenemos las compras de los productose introducimos el nuevo anticipo
				PysCompraExample comprasPeticionExample = new PysCompraExample();

				comprasPeticionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdpeticionEqualTo(Long.valueOf(anticipo.getIdPeticion()));

				List<PysCompra> comprasPeticion = pysCompraMapper.selectByExample(comprasPeticionExample);
				// Obtenemos los importes totales de las distintas compras
				List<String> importesCompras = pysTiposProductosExtendsMapper.getImpTotalesCompra(idInstitucion,
						Long.valueOf(anticipo.getIdPeticion()));

				int i = 0;

				float anti = (float) anticipo.getImporte().doubleValue();

				// Recorremos las distintas compras asignando el anticipo hasta que se agote o
				// hasta que no haya mas productos
				while (i < importesCompras.size() && anti > 0) {
					float impTotalNetoCompra = Float.valueOf(importesCompras.get(i));
					anti -= impTotalNetoCompra;

					if (anti < 0) {
						comprasPeticion.get(i).setImporteanticipado(new BigDecimal(impTotalNetoCompra + anti));
					} else {
						comprasPeticion.get(i).setImporteanticipado(new BigDecimal(impTotalNetoCompra));
					}
					i++;
				}
				// En el caso que se acabe el anticipo antes de asignar a todos,
				// se reinicia el importe anticipado del resto de compras
				while (i < importesCompras.size()) {
					comprasPeticion.get(i).setImporteanticipado(new BigDecimal(0));
					i++;
				}

				for (PysCompra compra : comprasPeticion) {
					LOGGER.info(
							"anadirAnticipoCompra() / pysCompraMapper.updateByPrimaryKey() -> Entrada a pysCompraMapper para actualizar el importe anticipado de una petición de compra");

					response = pysCompraMapper.updateByPrimaryKey(compra);

					LOGGER.info(
							"anadirAnticipoCompra() / pysCompraMapper.updateByPrimaryKey() -> Salida de pysCompraMapper para actualizar el importe anticipado de una petición de compra");

					if (response == 0)
						throw new Exception(
								"Error al actualizar el importe anticipado de una petición de compra en la BBDD.");
				}

			}
		}

		updateResponseDTO.setStatus("200");

		updateResponseDTO.setError(error);
		LOGGER.debug("anadirAnticipoCompra() -> Salida del servicio para actualizar el importe anticipado de una petición de compra");

		return updateResponseDTO;
	}
}
	