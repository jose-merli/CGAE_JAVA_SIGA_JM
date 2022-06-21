package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ComboConsultaInstitucionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO2;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionExample;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionExample;
import org.itcgae.siga.db.entities.FacSufijo;
import org.itcgae.siga.db.entities.FacSufijoExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModClasecomunicacionesExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.entities.PysProductos;
import org.itcgae.siga.db.entities.PysProductosExample;
import org.itcgae.siga.db.entities.PysServicios;
import org.itcgae.siga.db.entities.PysServiciosExample;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.FacPlantillafacturacionMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionMapper;
import org.itcgae.siga.db.mappers.FacSufijoMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.PysProductosMapper;
import org.itcgae.siga.db.mappers.PysServiciosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModClasecomunicacionesExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacEstadoconfirmfactExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacEstadosabonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFormapagoserieExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacGrupcritincluidosenserieExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacMotivodevolucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTipocliincluidoenseriefacExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FactEstadosfacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysFormapagoExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionPySGeneralService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturacionPySGeneralServiceImpl implements IFacturacionPySGeneralService {

	private Logger LOGGER = Logger.getLogger(FacturacionPySGeneralServiceImpl.class);

	@Autowired
	private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

	@Autowired
	private FacSufijoMapper facSufijoMapper;

	@Autowired
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;

	@Autowired
	private AdmContadorMapper admContadorMapper;

	@Autowired
	private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

	@Autowired
	private FacTipocliincluidoenseriefacExtendsMapper facTipocliincluidoenseriefacExtendsMapper;

	@Autowired
	private EnvPlantillaEnviosExtendsMapper envPlantillaEnviosExtendsMapper;

	@Autowired
	private PysFormapagoExtendsMapper pysFormapagoExtendsMapper;

	@Autowired
	private FacFormapagoserieExtendsMapper facFormapagoserieExtendsMapper;

	@Autowired
	FacSeriefacturacionMapper facSeriefacturacionMapper;

	@Autowired
	private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Autowired
	private PysProductosMapper pysProductosMapper;

	@Autowired
	private PysServiciosMapper pysServiciosMapper;

	@Autowired
	private FacEstadoconfirmfactExtendsMapper facEstadoconfirmfactExtendsMapper;

	@Autowired
	private FactEstadosfacturaExtendsMapper factEstadosfacturaExtendsMapper;

	@Autowired
	private FacEstadosabonoExtendsMapper facEstadosabonoExtendsMapper;

	@Autowired
	private FacFacturacionprogramadaExtendsMapper facFacturacionprogramadaExtendsMapper;

	@Autowired
	private ConConsultasExtendsMapper conConsultasExtendsMapper;

	@Autowired
	private FacGrupcritincluidosenserieExtendsMapper facGrupcritincluidosenserieExtendsMapper;

	@Autowired
	private FacMotivodevolucionExtendsMapper facMotivodevolucionExtendsMapper;

	@Autowired
	private ModClasecomunicacionesMapper modClasecomunicacionesMapper;

	@Autowired
	private ModModeloComunicacionExtendsMapper modModeloComunicacionExtendsMapper;

	@Override
	public ComboDTO comboCuentasBancarias(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboCuentasBancarias() -> Entrada al servicio para recuperar el combo de cuentas bancarias");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboCuentasBancarias() / facBancoInstitucionExtendsMapper.comboCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de cuentas bancarias");

			// Logica
			FacBancoinstitucionExample bancoExample = new FacBancoinstitucionExample();
			bancoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion()).andFechabajaIsNull();
			bancoExample.setOrderByClause("descripcion");

			List<FacBancoinstitucion> cuentasBancarias = facBancoinstitucionExtendsMapper.selectByExample(bancoExample);
			comboItems = cuentasBancarias.stream().map(m -> {
				ComboItem item = new ComboItem();
				item.setValue(String.valueOf(m.getBancosCodigo()));
				item.setLabel(m.getDescripcion());
				return item;
			}).collect(Collectors.toList());
			LOGGER.debug("comboCuentasBancarias() ->" + comboItems.toString());

			// comprobar primero si la lista de cuentas bancarias viene vacia
			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboCuentasBancarias() -> Salida del servicio para obtener el combo de cuentas bancarias");

		return comboDTO;
	}

	@Override
	public ComboDTO comboTiposProductos(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboTiposProductos() -> Entrada al servicio para recuperar el combo de productos");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboTiposProductos() / pysProductosMapper.selectByExample() -> Entrada a pysProductosMapper para obtener el combo de productos");

			// Logica
			PysProductosExample exampleProductos = new PysProductosExample();
			exampleProductos.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion());
			exampleProductos.setOrderByClause("DESCRIPCION");

			List<PysProductos> productos = pysProductosMapper.selectByExample(exampleProductos);

			if (productos != null) {
				comboItems = new ArrayList<>();
				for (PysProductos pysProductos : productos) {
					ComboItem comboItem = new ComboItem();
					// idtipoproducto-idproducto
					comboItem.setValue(pysProductos.getIdtipoproducto().toString() + "-"
							+ pysProductos.getIdproducto().toString());
					comboItem.setLabel(pysProductos.getDescripcion());

					comboItems.add(comboItem);
				}

				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.debug("comboTiposProductos() -> Salida del servicio para obtener el combo de productos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboTiposServicios(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboTiposServicios() -> Entrada al servicio para recuperar el combo de servicios");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboTiposServicios() / facBancoInstitucionExtendsMapper.comboSufijos() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de sufijos");

			// Logica
			PysServiciosExample exampleServicios = new PysServiciosExample();
			exampleServicios.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion());
			exampleServicios.setOrderByClause("DESCRIPCION");

			List<PysServicios> servicios = pysServiciosMapper.selectByExample(exampleServicios);

			if (servicios != null) {
				comboItems = new ArrayList<>();
				for (PysServicios pysServicios : servicios) {
					ComboItem comboItem = new ComboItem();
					// idtiposervicios-idservicio
					comboItem.setValue(pysServicios.getIdtiposervicios().toString() + "-"
							+ pysServicios.getIdservicio().toString());
					comboItem.setLabel(pysServicios.getDescripcion());

					comboItems.add(comboItem);
				}

				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.debug("comboTiposServicios() -> Salida del servicio para obtener el combo de servicios");

		return comboDTO;
	}

	@Override
	public ComboDTO comboSufijos(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboSufijos() -> Entrada al servicio para recuperar el combo de sufijos");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboSufijos() / facBancoInstitucionExtendsMapper.comboSufijos() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de sufijos");

			// Logica
			FacSufijoExample exampleSufijo = new FacSufijoExample();
			exampleSufijo.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion());
			exampleSufijo.setOrderByClause("SUFIJO, CONCEPTO");

			List<FacSufijo> sufijos = facSufijoMapper.selectByExample(exampleSufijo);

			if (sufijos != null) {
				comboItems = new ArrayList<>();
				for (FacSufijo facSufijo : sufijos) {
					ComboItem comboItem = new ComboItem();
					comboItem.setValue(facSufijo.getIdsufijo().toString());
					comboItem.setLabel(facSufijo.getSufijo().toString() + " - " + facSufijo.getConcepto());

					comboItems.add(comboItem);
				}

				LOGGER.debug("comboSufijos() ->" + comboItems.toString());

				// comprobar primero si la lista de cuentas bancarias viene vacia
				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.debug("comboSufijos() -> Salida del servicio para obtener el combo de sufijos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboEtiquetas(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		AdmUsuarios usuario = new AdmUsuarios();
		List<ComboItem> comboItems;

		LOGGER.debug("comboEtiquetas() -> Entrada al servicio para recuperar el combo de etiquetas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboEtiquetas() / facBancoInstitucionExtendsMapper.comboEtiquetas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de etiquetas");

			String idioma = usuario.getIdlenguaje();

			// Logica
			comboItems = cenGruposclienteExtendsMapper.comboEtiquetas(idioma, usuario.getIdinstitucion());

			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboEtiquetas() -> Salida del servicio para obtener el combo de etiquetas");

		return comboDTO;
	}

	@Override
	public ComboDTO comboDestinatarios(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboDestinatarios() -> Entrada al servicio para recuperar el combo de destinatarios");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboDestinatarios() / facGrupcritincluidosenserieExtendsMapper.comboDestinatarios() -> Entrada a facGrupcritincluidosenserieExtendsMapper para obtener el combo de destinatarios");

			// Logica
			comboItems = facGrupcritincluidosenserieExtendsMapper.comboDestinatarios(usuario.getIdinstitucion());

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("comboDestinatarios() -> Salida del servicio para obtener el combo de destinatarios");

		return comboDTO;
	}

	@Override
	public ComboDTO comboContadores(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboContadores() -> Entrada al servicio para recuperar el combo de contadores");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboContadores() / facBancoInstitucionExtendsMapper.comboContadores() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de contadores");

			// Logica
			AdmContadorExample exampleContador = new AdmContadorExample();
			exampleContador.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdtablaEqualTo("FAC_FACTURA");
			exampleContador.setOrderByClause("NOMBRE");

			List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

			if (contadores != null) {
				comboItems = new ArrayList<>();
				for (AdmContador contador : contadores) {
					ComboItem comboItem = new ComboItem();
					comboItem.setValue(contador.getIdcontador());
					comboItem.setLabel(contador.getIdcontador() + " (" + contador.getNombre() + ")");

					comboItems.add(comboItem);
				}

				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.debug("comboContadores() -> Salida del servicio para obtener el combo de contadores");

		return comboDTO;
	}

	@Override
	public ComboDTO comboContadoresRectificativas(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug(
				"comboContadoresRectificativas() -> Entrada al servicio para recuperar el combo de contadores rectificativas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboContadoresRectificativas() / facBancoInstitucionExtendsMapper.comboContadoresRectificativas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de contadores rectificativas");

			// Logica
			AdmContadorExample exampleContador = new AdmContadorExample();
			exampleContador.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdtablaEqualTo("FAC_ABONO").andIdcontadorNotEqualTo("FAC_ABONOS_FCS");
			exampleContador.setOrderByClause("NOMBRE");

			List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

			if (contadores != null) {
				comboItems = new ArrayList<>();
				for (AdmContador contador : contadores) {
					ComboItem comboItem = new ComboItem();
					comboItem.setValue(contador.getIdcontador());
					comboItem.setLabel(contador.getIdcontador() + " (" + contador.getNombre() + ")");

					comboItems.add(comboItem);
				}

				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.debug(
				"comboContadoresRectificativas() -> Salida del servicio para obtener el combo de contadores rectificativas");

		return comboDTO;
	}

	@Override
	public ComboDTO comboPlanificacion(String idSerieFacturacion, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug(
				"comboPlanificacion() -> Entrada al servicio para recuperar el combo de contadores rectificativas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboPlanificacion() / fac.comboPlanificacion() -> Entrada a facSeriefacturacionExtendsMapper para obtener el combo de planificación");

			// Logica
			FacSeriefacturacionExample exampleSerieFacturacion = new FacSeriefacturacionExample();

			if (idSerieFacturacion == null) {
				exampleSerieFacturacion.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andFechabajaIsNull();
			} else {
				exampleSerieFacturacion.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionNotEqualTo(Long.valueOf(idSerieFacturacion)).andFechabajaIsNull();
			}

			exampleSerieFacturacion.setOrderByClause("descripcion");

			List<FacSeriefacturacion> seriesFacturacion = facSeriefacturacionExtendsMapper
					.selectByExample(exampleSerieFacturacion);

			if (seriesFacturacion != null) {
				comboItems = new ArrayList<>();
				for (FacSeriefacturacion serieFacturacion : seriesFacturacion) {
					ComboItem comboItem = new ComboItem();
					comboItem.setValue(serieFacturacion.getIdseriefacturacion().toString());
					comboItem.setLabel(serieFacturacion.getNombreabreviado());

					comboItems.add(comboItem);
				}

				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.debug("comboPlanificacion() -> Salida del servicio para obtener el combo de planificación");

		return comboDTO;
	}

	@Override
	public ComboDTO comboEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug(
				"getEtiquetasSerie() -> Entrada al servicio para recuperar las etiquetas de la serie de facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getEtiquetasSerie() / facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie() -> Entrada a facTipocliincluidoenseriefacExtendsMapper para obtener las etiquetas de la serie");

			// Logica
			String idioma = usuario.getIdlenguaje();
			comboItems = facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie(idSerieFacturacion,
					usuario.getIdinstitucion(), idioma);

			LOGGER.debug(
					"getEtiquetasSerie() / facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie() -> Saliendo de facTipocliincluidoenseriefacExtendsMapper para obtener las etiquetas de la serie");

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("getEtiquetasSerie() -> Salida del servicio para obtener las etiquetas de la serie");

		return comboDTO;
	}

	@Override
	public ComboConsultaInstitucionDTO comboConsultas(HttpServletRequest request) throws Exception {
		ComboConsultaInstitucionDTO comboDTO = new ComboConsultaInstitucionDTO();

		List<ComboItemConsulta> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboConsultas() -> Entrada al servicio para recuperar las consultas disponibles");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboConsultas() / conConsultasExtendsMapper.selectConsultasDisponibles() -> Entrada a conConsultasExtendsMapper para obtener las consultas disponibles");

			comboItems = conConsultasExtendsMapper.selectConsultasDisponibles(usuario.getIdinstitucion(), 1l, 1l);

			LOGGER.debug(
					"comboConsultas() / conConsultasExtendsMapper.selectConsultasDisponibles() -> Saliendo de conConsultasExtendsMapper para obtener las consultas disponibles");

			comboDTO.setConsultas(comboItems);
		}

		LOGGER.debug("comboConsultas() -> Salida del servicio para obtener las consultas disponibles");

		return comboDTO;
	}

	@Override
	public ComboDTO comboPlantillasEnvio(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboPlantillasEnvio() -> Entrada al servicio para recuperar lel combo de platillas envio");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboPlantillasEnvio() / envPlantillaEnviosExtendsMapper.comboPlantillasEnvio() -> Entrada a envPlantillaEnviosExtendsMapper para obtener el combo de platillas envio");

			// Logica
			comboItems = envPlantillaEnviosExtendsMapper.comboPlantillasEnvio(usuario.getIdinstitucion());

			LOGGER.debug(
					"comboPlantillasEnvio() / envPlantillaEnviosExtendsMapper.comboPlantillasEnvio() -> Saliendo de envPlantillaEnviosExtendsMapper para obtener el combo de platillas envio");

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("comboPlantillasEnvio() -> Salida del servicio para obtener el combo de platillas envio");

		return comboDTO;
	}

	@Override
	public ComboDTO comboFormasPagoFactura(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboFormasPagoFactura() -> Entrada al servicio para recuperar todas las formas de pago");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboFormasPagoFactura() / pysFormapagoExtendsMapper.getWayToPayWithIdFormapago() -> Entrada a pysFormapagoExtendsMapper para obtener todas las formas de pago");

			// Logica
			String idioma = usuario.getIdlenguaje();
			comboItems = pysFormapagoExtendsMapper.getWayToPayWithIdFormapago(idioma);

			LOGGER.debug(
					"comboFormasPagoFactura() / pysFormapagoExtendsMapper.getWayToPayWithIdFormapago() -> Saliendo de pysFormapagoExtendsMapper para obtener todas las formas de pago");

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("comboFormasPagoFactura() -> Salida del servicio para obtener todas las formas de pago");

		return comboDTO;
	}

	@Override
	public ComboDTO comboFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug(
				"getFormasPagosSerie() -> Entrada al servicio para recuperar las formas de pago de la serie de facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getFormasPagosSerie() / facFormapagoserieExtendsMapper.getFormasPagosSerie() -> Entrada a facFormapagoserieExtendsMapper para obtener las formas de pago de la serie de facturación");

			// Logica
			String idioma = usuario.getIdlenguaje();
			comboItems = facFormapagoserieExtendsMapper.getFormasPagosSerie(idSerieFacturacion,
					usuario.getIdinstitucion(), idioma);

			LOGGER.debug(
					"getFormasPagosSerie() / facFormapagoserieExtendsMapper.getFormasPagosSerie() -> Saliendo de facFormapagoserieExtendsMapper para obtener las formas de pago de la serie de facturación");

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug(
				"getFormasPagosSerie() -> Salida del servicio para obtener las formas de pago de la serie de facturación");

		return comboDTO;
	}

	@Override
	public ComboDTO comboModelosComunicacion(Boolean esRectificativa, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboModelosComunicacion() -> Entrada al servicio para recuperar los modelos de comunicación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {

			// Logica

			// Obtenemos la clase de comunicación dependiendo de si buscamos modelos de factura o de factura rectificativa
			ModClasecomunicacionesExample claseExample = new ModClasecomunicacionesExample();
			if (esRectificativa) {
				claseExample.createCriteria().andNombreEqualTo("Facturas Rectificativas");
			} else {
				claseExample.createCriteria().andNombreEqualTo("Facturas");
			}

			List<ModClasecomunicaciones> clases = modClasecomunicacionesMapper.selectByExample(claseExample);

			// Obtenemos los modelos de la clase de comunicación seleccionada (si existe la clase)
			List<ModModelocomunicacion> modelos = new ArrayList<>();
			if (clases != null && !clases.isEmpty()) {
				ModModelocomunicacionExample comunicacionExample = new ModModelocomunicacionExample();
				comunicacionExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdclasecomunicacionEqualTo(clases.get(0).getIdclasecomunicacion())
						.andFechabajaIsNull();
				comunicacionExample.setOrderByClause("nombre");

				modelos = modModeloComunicacionExtendsMapper.selectByExample(comunicacionExample);
			}

			comboItems = modelos.stream().map(m -> {
				ComboItem item = new ComboItem();
				item.setValue(String.valueOf(m.getIdmodelocomunicacion()));
				item.setLabel(m.getNombre());
				return item;
			}).collect(Collectors.toList());


			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("comboModelosComunicacion() -> Salida del servicio para obtener los modelos de comunicación");

		return comboDTO;
	}

	@Override
	public ComboDTO comboSeriesFacturacion(HttpServletRequest request) throws Exception {

		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug(
				"FacturacionPySGeneralServiceImpl.comboSeriesFacturacion() -> Entrada al servicio para recuperar el combo de series de facturacion");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"FacturacionPySGeneralServiceImpl.comboSeriesFacturacion() -> obteniendo datos para el el combo de series de facturación");

			FacSeriefacturacionExample example = new FacSeriefacturacionExample();
			example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion()).andFechabajaIsNull();

			List<FacSeriefacturacion> seriesFac = facSeriefacturacionMapper.selectByExample(example);

			if (seriesFac.size() > 0) {

				comboItems = seriesFac.stream().map(m -> {

					ComboItem item = new ComboItem();

					item.setValue(String.valueOf(m.getIdseriefacturacion()));
					item.setLabel(m.getNombreabreviado());

					return item;

				}).collect(Collectors.toList());

				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.debug("comboCuentasBancarias() -> Salida del servicio para obtener el combo de cuentas bancarias");

		return comboDTO;
	}
	
	@Override
	public ComboDTO2 comboSeriesFacturacionConDesc(HttpServletRequest request) throws Exception {

		ComboDTO2 comboDTO = new ComboDTO2();
		List<ComboItem2> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug(
				"FacturacionPySGeneralServiceImpl.comboSeriesFacturacion() -> Entrada al servicio para recuperar el combo de series de facturacion");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"FacturacionPySGeneralServiceImpl.comboSeriesFacturacion() -> obteniendo datos para el el combo de series de facturación");

			FacSeriefacturacionExample example = new FacSeriefacturacionExample();
			example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion()).andFechabajaIsNull();

			List<FacSeriefacturacion> seriesFac = facSeriefacturacionMapper.selectByExample(example);

			if (seriesFac.size() > 0) {

				comboItems = seriesFac.stream().map(m -> {

					ComboItem2 item = new ComboItem2();

					item.setValue(String.valueOf(m.getIdseriefacturacion()));
					item.setLabel1(m.getNombreabreviado());
					item.setLabel2(m.getDescripcion());

					return item;

				}).collect(Collectors.toList());

				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.debug("comboCuentasBancarias() -> Salida del servicio para obtener el combo de cuentas bancarias");

		return comboDTO;
	}

	@Override
	public ComboDTO2 comboTiposIVA(HttpServletRequest request) throws Exception {
		ComboDTO2 comboDTO = new ComboDTO2();

		List<ComboItem2> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboTiposIVA() -> Entrada al servicio para recuperar el combo de tipos de IVA");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboTiposIVA() / pySTipoIvaExtendsMapper.comboTiposIVA() -> Entrada a pySTipoIvaExtendsMapper para obtener el combo de tipos de IVA");

			String idioma = usuario.getIdlenguaje();

			// Logica
			comboItems = pySTipoIvaExtendsMapper.comboTiposIVA(idioma);

			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboTiposIVA() -> Salida del servicio para obtener el combo de tipos de IVA");

		return comboDTO;
	}
	
	@Override
	public ComboDTO2 comboTiposIVACuentaBancariaEntidad(String idTipoIVA,HttpServletRequest request) throws Exception {
		ComboDTO2 comboDTO = new ComboDTO2();

		List<ComboItem2> comboItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.debug("comboTiposIVA() -> Entrada al servicio para recuperar el combo de tipos de IVA");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboTiposIVA() / pySTipoIvaExtendsMapper.comboTiposIVA() -> Entrada a pySTipoIvaExtendsMapper para obtener el combo de tipos de IVA");

			String idioma = usuario.getIdlenguaje();

			// Logica
			comboItems = pySTipoIvaExtendsMapper.comboIVACuentasBancariasEntidad(idTipoIVA,idioma);

			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboTiposIVA() -> Salida del servicio para obtener el combo de tipos de IVA");

		return comboDTO;
	}

	@Override
	public ComboDTO comboEstadosFact(String tipo, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		AdmUsuarios usuario = new AdmUsuarios();
		List<ComboItem> comboItems;

		LOGGER.debug("comboEstadosFact() -> Entrada al servicio para recuperar el combo de estados con tipo=" + tipo);

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboEstadosFact() / facEstadoconfirmfactExtendsMapper.comboEstados() -> Entrada a facEstadoconfirmfactExtendsMapper para obtener combo de estados con tipo="
							+ tipo);

			String idioma = usuario.getIdlenguaje();

			// Logica
			comboItems = facEstadoconfirmfactExtendsMapper.comboEstados(tipo, idioma);

			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboEstadosFact() -> Salida del servicio recuperar el combo de estados con tipo=" + tipo);

		return comboDTO;
	}

	@Override
	public ComboDTO2 comboEstadosFacturas(HttpServletRequest request) throws Exception {
		ComboDTO2 comboDTO = new ComboDTO2();

		AdmUsuarios usuario = new AdmUsuarios();
		List<ComboItem2> comboItems;

		LOGGER.debug("comboEstadosFacturas() -> Entrada al servicio para recuperar el combo de estados facturas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboEstadosFacturas() / facEstadoconfirmfactExtendsMapper.comboEstadosFacturas() -> Entrada a facEstadoconfirmfactExtendsMapper para obtener combo de estados de facturas");

			String idioma = usuario.getIdlenguaje();

			// Logica
			comboItems = factEstadosfacturaExtendsMapper.comboEstadosFacturas(idioma);
			comboItems.addAll(facEstadosabonoExtendsMapper.comboEstadosAbonos(idioma));

			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboEstadosFacturas() -> Salida del servicio recuperar el combo de estados facturas");

		return comboDTO;
	}

	@Override
	public ComboDTO comboFacturaciones(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		AdmUsuarios usuario = new AdmUsuarios();
		List<ComboItem> comboItems;

		LOGGER.debug(
				"comboFacturaciones() -> Entrada al servicio para recuperar el combo de formas de pagos para facturas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboFacturaciones() / facEstadoconfirmfactExtendsMapper.comboFormasPagoFactura() -> Entrada a facEstadoconfirmfactExtendsMapper para obtener combo de formas de pagos para facturas");

			Short idInstitucion = usuario.getIdinstitucion();

			// Logica
			comboItems = facFacturacionprogramadaExtendsMapper.comboFacturaciones(idInstitucion);

			comboDTO.setCombooItems(comboItems);

		}

		LOGGER.debug("comboFacturaciones() -> Salida del servicio recuperar el combo de formas de pagos para facturas");

		return comboDTO;
	}

	@Override
	public ComboDTO parametrosSEPA(String idInstitucion, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		ComboItem item = new ComboItem();

		AdmUsuarios usuario = new AdmUsuarios();
		GenParametrosExample example = new GenParametrosExample();
		List<GenParametros> parametros;
		short institucion;

		LOGGER.debug("parametrosSEPA() -> Entrada al servicio para recuperar los valores de los parámetros");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {

			if (idInstitucion != null) {
				institucion = Short.parseShort(idInstitucion);
			} else {
				institucion = usuario.getIdinstitucion();
			}

			// Tipo ficheros (Ficha de cuentas bancarias)
			item.setValue("SEPA_TIPO_FICHEROS");

			example.createCriteria().andIdinstitucionEqualTo(institucion).andParametroEqualTo("SEPA_TIPO_FICHEROS");
			parametros = genParametrosMapper.selectByExample(example);

			if (null != parametros && parametros.size() > 0) {
				item.setLabel(parametros.get(0).getValor());
			} else {
				item.setLabel("0");
			}

			comboItems.add(item);

			// primeros recibos
			item = new ComboItem();
			example = new GenParametrosExample();

			example.createCriteria().andIdinstitucionEqualTo(institucion)
					.andParametroEqualTo("SEPA_DIAS_HABILES_PRIMEROS_RECIBOS");
			parametros = genParametrosMapper.selectByExample(example);

			item.setValue("SEPA_DIAS_HABILES_PRIMEROS_RECIBOS");

			if (null != parametros && parametros.size() > 0) {

				item.setLabel(parametros.get(0).getValor());
			} else {
				item.setLabel("7");
			}

			comboItems.add(item);

			// recibosRecurrentes
			item = new ComboItem();
			example = new GenParametrosExample();

			item.setValue("SEPA_DIAS_HABILES_RECIBOS_RECURRENTES");

			example.createCriteria().andIdinstitucionEqualTo(institucion)
					.andParametroEqualTo("SEPA_DIAS_HABILES_RECIBOS_RECURRENTES");
			parametros = genParametrosMapper.selectByExample(example);

			if (null != parametros && parametros.size() > 0) {
				item.setLabel(parametros.get(0).getValor());
			} else {
				item.setLabel("4");
			}

			comboItems.add(item);

			// recibos cor1
			item = new ComboItem();
			example = new GenParametrosExample();

			item.setValue("SEPA_DIAS_HABILES_RECIBOS_COR1");

			example.createCriteria().andIdinstitucionEqualTo(institucion)
					.andParametroEqualTo("SEPA_DIAS_HABILES_RECIBOS_COR1");
			parametros = genParametrosMapper.selectByExample(example);

			if (null != parametros && parametros.size() > 0) {
				item.setLabel(parametros.get(0).getValor());
			} else {
				item.setLabel("3");
			}

			comboItems.add(item);

			// recibos b2b
			item = new ComboItem();
			example = new GenParametrosExample();

			item.setValue("SEPA_DIAS_HABILES_RECIBOS_B2B");

			example.createCriteria().andIdinstitucionEqualTo(institucion)
					.andParametroEqualTo("SEPA_DIAS_HABILES_RECIBOS_B2B");
			parametros = genParametrosMapper.selectByExample(example);

			if (null != parametros && parametros.size() > 0) {
				item.setLabel(parametros.get(0).getValor());
			} else {
				item.setLabel("3");
			}

			comboItems.add(item);

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("parametrosSEPA() -> Salida del servicio");

		return comboDTO;
	}

	@Override
	public ComboDTO parametrosCONTROL(String idInstitucion, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		short institucion;

		LOGGER.debug("parametrosCONTROL() -> Entrada al servicio para recuperar los valores de los parámetros");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {

			if (idInstitucion != null) {
				institucion = Short.parseShort(idInstitucion);
			} else {
				institucion = usuario.getIdinstitucion();
			}

			comboItems.add(getParametro("CONTROL_EMISION_FACTURAS_SII", "FAC", institucion));
			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("parametrosCONTROL() -> Salida del servicio");

		return comboDTO;
	}

	public ComboItem getParametro(String parametro, String modulo, Short idIntritucion) {
		ComboItem item = new ComboItem();
		item.setLabel(parametro);

		GenParametrosKey key = new GenParametros();
		key.setIdinstitucion(idIntritucion);
		key.setModulo(modulo);
		key.setParametro(parametro);

		GenParametros param = genParametrosMapper.selectByPrimaryKey(key);
		if (param != null)
			item.setValue(param.getValor());

		return item;
	}

	@Override
	public ComboDTO parametrosLINEAS(String idInstitucion, HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();
		ComboItem item1 = new ComboItem();
		ComboItem item2 = new ComboItem();
		ComboItem item3 = new ComboItem();

		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		AdmUsuarios usuario = new AdmUsuarios();
		GenParametros param = null;
		short institucion;

		LOGGER.debug("parametrosCONTROL() -> Entrada al servicio para recuperar los valores de los parámetros");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {

			if (idInstitucion != null) {
				institucion = Short.parseShort(idInstitucion);
			} else {
				institucion = usuario.getIdinstitucion();
			}

			GenParametrosKey genKey = new GenParametros();
			genKey.setIdinstitucion(institucion);
			genKey.setModulo("FAC");

			genKey.setParametro("MODIFICAR_DESCRIPCION");
			item1.setLabel("MODIFICAR_DESCRIPCION");
			param = genParametrosMapper.selectByPrimaryKey(genKey);
			item1.setValue(param == null || param.getValor().equals("0") ? "0" : "1");
			comboItems.add(item1);

			genKey.setParametro("MODIFICAR_IMPORTE_UNITARIO");
			item2.setLabel("MODIFICAR_IMPORTE_UNITARIO");
			param = genParametrosMapper.selectByPrimaryKey(genKey);
			item2.setValue(param == null || param.getValor().equals("0") ? "0" : "1");
			comboItems.add(item2);

			genKey.setParametro("MODIFICAR_IVA");
			item3.setLabel("MODIFICAR_IVA");
			param = genParametrosMapper.selectByPrimaryKey(genKey);
			item3.setValue(param == null || param.getValor().equals("0") ? "0" : "1");
			comboItems.add(item3);

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("parametrosCONTROL() -> Salida del servicio");

		return comboDTO;
	}

	@Override
	public ComboDTO comboMotivosDevolucion(HttpServletRequest request) throws Exception {
		ComboDTO comboDTO = new ComboDTO();

		AdmUsuarios usuario = new AdmUsuarios();
		List<ComboItem> comboItems;

		LOGGER.debug(
				"comboMotivosDevolucion() -> Entrada al servicio para recuperar el combo de motivos de devolucion");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"comboMotivosDevolucion() / facEstadoconfirmfactExtendsMapper.comboFormasPagoFactura() -> Entrada a facEstadoconfirmfactExtendsMapper para obtener combo de motivos de devolucion");

			comboItems = facMotivodevolucionExtendsMapper.comboMotivosDevolucion(usuario.getIdlenguaje());
			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.debug("comboMotivosDevolucion() -> Salida del servicio recuperar el combo de motivos de devolucion");

		return comboDTO;
	}
}
