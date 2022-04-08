package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoSJCSExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsCertificacionesExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsDestinatariosRetencionesExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsEstadosFacturacionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsHitoGeneralExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsMovimientosvariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagosjgExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupofacturacionExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICombosServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

@Service
public class CombosServicesImpl implements ICombosServices {

	private Logger LOGGER = Logger.getLogger(CombosServicesImpl.class);

	@Autowired
	private FcsDestinatariosRetencionesExtendsMapper fcsDestinatariosRetencionesExtendsMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

	@Autowired
	private ScsGrupofacturacionExtendsMapper scsGrupofacturacionExtendsMapper;

	@Autowired
	private FcsMovimientosvariosExtendsMapper fcsMovimientosvariosExtendsMapper;

	@Autowired
	private FcsCertificacionesExtendsMapper fcsCertificacionesExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private FcsEstadosFacturacionExtendsMapper fcsEstadosFacturacionExtendsMapper;

	@Autowired
	private FcsHitoGeneralExtendsMapper fcsHitoGeneralExtendsMapper;

	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

	@Autowired
	private FcsPagosjgExtendsMapper fcsPagosjgExtendsMapper;

	@Autowired
	private FacAbonoSJCSExtendsMapper facAbonosjgExtendsMapper;
	
    @Autowired
    private GenParametrosMapper genParametrosMapper;

	@Override
	public ComboDTO comboPagosColegio(HttpServletRequest request) {

		LOGGER.info(
				"comboPagosColegio() -> Entrada del servicio para obtener el listado de facturaciones cerradas del colegio");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboEstadosFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboPagosColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboPagosColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"comboPagosColegio() / fcsPagosjgExtendsMapper.getComboFactColegio() -> Entrada a fcsPagosjgExtendsMapper para obtener el listado");
				List<ComboItem> comboItems = fcsPagosjgExtendsMapper.comboPagosColegio(usuario.getIdlenguaje(),
						idInstitucion);
				comboEstadosFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboPagosColegio() / fcsPagosjgExtendsMapper.getComboFactColegio() -> Salida a fcsPagosjgExtendsMapper para obtener el listado");
			} else {
				LOGGER.warn(
						"comboPagosColegio() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboPagosColegio() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"comboPagosColegio() -> Salida del servicio para obtener el listado de facturaciones cerradas del colegio");
		return comboEstadosFact;
	}

	public ComboDTO comboFactEstados(HttpServletRequest request) {

		LOGGER.info("comboFactEstados() -> Entrada del servicio para obtener los estados de las facturas");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idLenguaje;
		ComboDTO comboEstadosFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboFactEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboFactEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"comboFactEstados() / FcsEstadosFacturacionExtendsMapper.estadosFacturacion() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener los estados");
				List<ComboItem> comboItems = fcsEstadosFacturacionExtendsMapper.estadosFacturacion(idLenguaje);
				comboEstadosFact.setCombooItems(comboItems);
				LOGGER.info(
						"comboFactEstados() / FcsEstadosFacturacionExtendsMapper.estadosFacturacion() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener los estados");
			} else {
				LOGGER.warn(
						"comboFactEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactEstados() -> idInstitucion del token nula");
		}

		LOGGER.info("comboFactEstados() -> Salida del servicio para obtener los estados de las facturas");
		return comboEstadosFact;
	}

	public ComboDTO comboFactConceptos(HttpServletRequest request) {

		LOGGER.info("comboFactConceptos() -> Entrada del servicio para obtener los conceptos de las facturas");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idLenguaje;
		ComboDTO comboEstadosFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboFactConceptos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboFactConceptos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"comboFactConceptos() / FcsEstadosFacturacionExtendsMapper.estadosFacturacion() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener los estados");
				List<ComboItem> comboItems = fcsHitoGeneralExtendsMapper.factConceptos(idLenguaje);
				comboEstadosFact.setCombooItems(comboItems);
				LOGGER.info(
						"comboFactConceptos() / FcsEstadosFacturacionExtendsMapper.estadosFacturacion() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener los estados");
			} else {
				LOGGER.warn(
						"comboFactConceptos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactConceptos() -> idInstitucion del token nula");
		}

		LOGGER.info("comboFactConceptos() -> Salida del servicio para obtener los conceptos de las facturas");
		return comboEstadosFact;
	}

	@Override
	public ComboDTO comboFactColegio(HttpServletRequest request) {

		LOGGER.info(
				"comboFactColegio() -> Entrada del servicio para obtener el listado de facturaciones cerradas del colegio");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboEstadosFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboFactColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboFactColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboFactColegio() / fcsFacturacionJGExtendsMapper.getComboFactColegio() -> Entrada a fcsFacturacionJGExtendsMapper para obtener el listado");

				List<ComboItem> comboItems = fcsFacturacionJGExtendsMapper.getComboFactColegio(idInstitucion);

				comboEstadosFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboFactColegio() / fcsFacturacionJGExtendsMapper.getComboFactColegio() -> Salida a fcsFacturacionJGExtendsMapper para obtener el listado");
			} else {
				LOGGER.warn(
						"comboFactColegio() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactColegio() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"comboFactColegio() -> Salida del servicio para obtener el listado de facturaciones cerradas del colegio");
		return comboEstadosFact;
	}

	public ComboDTO comboPagoEstados(HttpServletRequest request) {
		LOGGER.info("comboPagoEstados() -> Entrada del servicio para obtener los estados de los pagos");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idLenguaje;
		ComboDTO comboEstadosFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboPagoEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboPagoEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"comboPagoEstados() / FcsEstadosFacturacionExtendsMapper.estadosPagos() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener los estados de los pagos");

				List<ComboItem> comboItems = fcsEstadosFacturacionExtendsMapper.estadosPagos(idLenguaje);
				comboEstadosFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboPagoEstados() / FcsEstadosFacturacionExtendsMapper.estadosPagos() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener los estados de los pagos");
			} else {
				LOGGER.warn(
						"comboPagoEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboPagoEstados() -> idInstitucion del token nula");
		}

		LOGGER.info("comboPagoEstados() -> Salida del servicio para obtener los estados de los pagos");

		return comboEstadosFact;
	}

	@Override
	public ComboDTO comboColegiosProcuradores(HttpServletRequest request) {

		LOGGER.info(
				"comboColegiosProcuradores() -> Entrada del servicio para obtener el listado de colegios de un procurador");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboEstadosFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboColegiosProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboColegiosProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"comboColegiosProcuradores() / fcsPagosjgExtendsMapper.getComboFactColegio() -> Entrada a fcsPagosjgExtendsMapper para obtener el listado de colegios de un procurador");

				List<ComboItem> comboItems = fcsPagosjgExtendsMapper.comboPagosColegio(usuario.getIdlenguaje(),
						idInstitucion);
				comboEstadosFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboPagosColegio() / fcsPagosjgExtendsMapper.getComboFactColegio() -> Salida a fcsPagosjgExtendsMapper para obtener el listado de colegios de un procurador");
			} else {
				LOGGER.warn(
						"comboColegiosProcuradores() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboColegiosProcuradores() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"comboColegiosProcuradores() -> Salida del servicio para obtener el listado de colegios de un procurador");
		return comboEstadosFact;
	}

	public ComboDTO comboFacturaciones(HttpServletRequest request) {

		LOGGER.info("comboFacturaciones() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboFacturaciones() / FcsEstadosFacturacionExtendsMapper.estadosPagos() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");

				List<ComboItem> comboItems = fcsFacturacionJGExtendsMapper.comboFacturaciones(idInstitucion.toString());
				comboFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboFacturaciones() / FcsEstadosFacturacionExtendsMapper.comboFacturaciones() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn(
						"comboFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboPagoEstados() -> idInstitucion del token nula");
		}

		LOGGER.info("comboPagoEstados() -> Salida del servicio para obtener los estados de los pagos");

		return comboFact;
	}

	@Override
	public ComboDTO getComboDestinatarios(HttpServletRequest request) {

		LOGGER.info(
				"CombosServicesImpl.getComboDestinatarios() -> Entrada al servicio para obtener el combo de destinatarios");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		ComboDTO comboDTO = new ComboDTO();

		try {

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"CombosServicesImpl.getComboDestinatarios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"CombosServicesImpl.getComboDestinatarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"CombosServicesImpl.getComboDestinatarios() / fcsDestinatariosRetencionesExtendsMapper.getComboDestinatarios() -> Entrada para obtener el combo de destinatarios");

					List<ComboItem> comboItems = fcsDestinatariosRetencionesExtendsMapper
							.getComboDestinatarios(idInstitucion);
					comboDTO.setCombooItems(comboItems);

					LOGGER.info(
							"CombosServicesImpl.getComboDestinatarios() / fcsDestinatariosRetencionesExtendsMapper.getComboDestinatarios() -> Salida de obtener el combo de destinatarios");
				} else {
					LOGGER.warn(
							"CombosServicesImpl.getComboDestinatarios() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("CombosServicesImpl.getComboDestinatarios() -> idInstitucion del token nula");
			}

		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.message.error.realiza.accion");
		}

		comboDTO.setError(error);

		LOGGER.info(
				"CombosServicesImpl.getComboDestinatarios() -> Salida del servicio para obtener el combo de destinatarios");

		return comboDTO;
	}

	@Override
	public ComboDTO getComboPagosRetenciones(HttpServletRequest request) {

		LOGGER.info(
				"CombosServicesImpl.getComboPagosRetenciones() -> Entrada al servicio para obtener el combo de pagos para la busqueda de retenciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		ComboDTO comboDTO = new ComboDTO();

		try {

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"CombosServicesImpl.getComboPagosRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"CombosServicesImpl.getComboPagosRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"CombosServicesImpl.getComboPagosRetenciones() / fcsDestinatariosRetencionesExtendsMapper.getComboDestinatarios() -> Entrada para obtener el combo de destinatarios");

					List<ComboItem> comboItems = fcsPagosjgExtendsMapper
							.comboPagosColegio(usuarios.get(0).getIdlenguaje(), idInstitucion);
					comboDTO.setCombooItems(comboItems);

					LOGGER.info(
							"CombosServicesImpl.getComboPagosRetenciones() / fcsDestinatariosRetencionesExtendsMapper.getComboDestinatarios() -> Salida de obtener el combo de destinatarios");
				} else {
					LOGGER.warn(
							"CombosServicesImpl.getComboPagosRetenciones()) / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("CombosServicesImpl.getComboPagosRetenciones() -> idInstitucion del token nula");
			}

		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.message.error.realiza.accion");
		}

		comboDTO.setError(error);

		LOGGER.info(
				"CombosServicesImpl.getComboPagosRetenciones() -> Salida del servicio para obtener el combo de pagos para la busqueda de retenciones");

		return comboDTO;
	}

	@Override
	public ComboDTO getComboColegios(HttpServletRequest request) {
		LOGGER.info("CombosServicesImpl.getColegios() -> Entrada al servicio para obtener el combo de colegios");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		ComboDTO comboDTO = new ComboDTO();

		try {

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"CombosServicesImpl.getColegios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"CombosServicesImpl.getColegios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"CombosServicesImpl.getColegios() / fcsDestinatariosRetencionesExtendsMapper.getComboDestinatarios() -> Entrada para obtener el combo de destinatarios");
					// Comprobamos si es Consejo
					List<ComboItem> consejo = cenInstitucionExtendsMapper.isConsejo(String.valueOf(idInstitucion));
					List<ComboItem> comboItems = null;
					if (consejo.size() > 0) {
						comboItems = cenInstitucionExtendsMapper.getInstitucionesConsejo(String.valueOf(idInstitucion));
					} else {
						comboItems = cenInstitucionExtendsMapper.getComboInstitucionesNombre();
					}
					comboDTO.setCombooItems(comboItems);

					LOGGER.info(
							"CombosServicesImpl.getColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Salida de obtener el combo de colegios");
				} else {
					LOGGER.warn(
							"CombosServicesImpl.getColegios()) / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("CombosServicesImpl.getColegios() -> idInstitucion del token nula");
			}

		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.message.error.realiza.accion");
		}

		comboDTO.setError(error);

		LOGGER.info("CombosServicesImpl.getColegios() -> Salida del servicio para obtener el combo de colegios");

		return comboDTO;
	}

	@Override
	public ComboDTO getComboGrupoFacturacionByColegios(List<String> idColegios, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboGrupoFact = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboGrupoFacturacionByColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboGrupoFacturacionByColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"getComboGrupoFacturacionByColegio() -> Entrada para obtener la información de los distintos grupos de facturacion");

				comboGrupoFact.setCombooItems(scsGrupofacturacionExtendsMapper.grupoFacturacionByColegios(idColegios,
						usuarios.get(0).getIdlenguaje()));

				LOGGER.info("getComboGrupoFacturacionByColegio() -> Salida ya con los datos recogidos");

			}
		}
		return comboGrupoFact;

	}

	public ComboDTO comboFactMovimientos(HttpServletRequest request) {

		LOGGER.info("comboFactMovimientos() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboFactMovimientos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFactMovimientos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboFactMovimientos() / fcsFacturacionJGExtendsMapper.comboFactMovimientos() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");

				List<ComboItem> comboItems = fcsFacturacionJGExtendsMapper
						.comboFactMovimientos(idInstitucion.toString());
				comboFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboFactMovimientos() / fcsFacturacionJGExtendsMapper.comboFactMovimientos() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn(
						"comboFactMovimientos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactMovimientos() -> idInstitucion del token nula");
		}

		LOGGER.info("comboFactMovimientos() -> Salida del servicio para obtener los estados de los pagos");

		return comboFact;
	}

	public ComboDTO comboAplicadoEnPago(HttpServletRequest request) {

		LOGGER.info("comboFactMovimientos() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboAplicadoEnPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAplicadoEnPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboAplicadoEnPago() / FcsPagosjgExtendsMapper.comboAplicadoEnPago() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");

				List<ComboItem> comboItems = fcsPagosjgExtendsMapper.comboAplicadoEnPago(idInstitucion.toString());
				comboFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboAplicadoEnPago() / FcsPagosjgExtendsMapper.comboAplicadoEnPago() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn(
						"comboAplicadoEnPago() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboAplicadoEnPago() -> idInstitucion del token nula");
		}

		LOGGER.info("comboAplicadoEnPago() -> Salida del servicio para obtener los estados de los pagos");

		return comboFact;
	}

	public ComboDTO comboAgrupacionEnTurnos(HttpServletRequest request) {

		LOGGER.info("comboAgrupacionEnTurnos() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFact = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboAplicadoEnPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAplicadoEnPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboAgrupacionEnTurnos() / FcsPagosjgExtendsMapper.comboAgrupacionEnTurnos() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");

				List<ComboItem> comboItems = fcsFacturacionJGExtendsMapper
						.comboAgrupacionEnTurnos(idInstitucion.toString());
				comboFact.setCombooItems(comboItems);

				LOGGER.info(
						"comboAgrupacionEnTurnos() / FcsPagosjgExtendsMapper.comboAgrupacionEnTurnos() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn(
						"comboAgrupacionEnTurnos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboAgrupacionEnTurnos() -> idInstitucion del token nula");
		}

		LOGGER.info("comboAgrupacionEnTurnos() -> Salida del servicio para obtener los estados de los pagos");

		return comboFact;
	}

	public ComboDTO comboTiposMovimientos(HttpServletRequest request) {

		LOGGER.info("comboTiposMovimientos() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFTipos = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboTiposMovimientos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTiposMovimientos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboTiposMovimientos() / FcsMovimientosvariosExtendsMapper.comboTiposMovimientos() -> Entrada a FcsMovimientosvariosExtendsMapper para obtener los tipos ");

				List<ComboItem> comboItems = fcsMovimientosvariosExtendsMapper
						.comboTiposMovimientos(idInstitucion.toString());
				comboFTipos.setCombooItems(comboItems);

				/*
				 * for(ComboItem combo : comboItems) { String descripcion =
				 * (combo.getLabel()).substring(0, (combo.getLabel()).length() -3);
				 * combo.setLabel(descripcion); }
				 */

				LOGGER.info(
						"comboTiposMovimientos() / FcsMovimientosvariosExtendsMapper.comboTiposMovimientos() -> Salida a FcsMovimientosvariosExtendsMapper para obtener los tipos");
			} else {
				LOGGER.warn(
						"comboTiposMovimientos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboTiposMovimientos() -> idInstitucion del token nula");
		}

		LOGGER.info("comboTiposMovimientos() -> Salida del servicio para obtener los tipos");

		return comboFTipos;
	}

	public ComboDTO comboCertificacionSJCS(HttpServletRequest request) {

		LOGGER.info("comboCertificacionSJCS() -> Entrada del servicio para obtener las certificaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFCertificacion = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboCertificacionSJCS() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboCertificacionSJCS() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboCertificacionSJCS() / FcsMovimientosvariosExtendsMapper.comboCertificacionSJCS() -> Entrada a FcsMovimientosvariosExtendsMapper para obtener las certificaciones ");

				List<ComboItem> comboItems = fcsMovimientosvariosExtendsMapper
						.comboCertificacionSJCS(idInstitucion.toString());
				comboFCertificacion.setCombooItems(comboItems);

				/*
				 * for(ComboItem combo : comboItems) { String descripcion =
				 * (combo.getLabel()).substring(0, (combo.getLabel()).length() -3);
				 * combo.setLabel(descripcion); }
				 */

				LOGGER.info(
						"comboCertificacionSJCS() / FcsMovimientosvariosExtendsMapper.comboCertificacionSJCS() -> Salida a FcsMovimientosvariosExtendsMapper para obtener las certificaciones");
			} else {
				LOGGER.warn(
						"comboCertificacionSJCS() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboCertificacionSJCS() -> idInstitucion del token nula");
		}

		LOGGER.info("comboCertificacionSJCS() -> Salida del servicio para obtener los tipos");

		return comboFCertificacion;
	}

	@Override
	public ComboDTO comboFactByPartidaPresu(String idpartidapresupuestaria, HttpServletRequest request) {

		LOGGER.info("comboFactByPartidaPresu() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFactByPartidaPresu = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboFactByPartidaPresu() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFactByPartidaPresu() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboFactByPartidaPresu() / fcsCertificacionesExtendsMapper.comboFactByPartidaPresu() -> Entrada a  fcsCertificacionesExtendsMapper para obtener las facturaciones");

				List<ComboItem> comboItems = fcsCertificacionesExtendsMapper
						.comboFactByPartidaPresu(idpartidapresupuestaria, idInstitucion.toString());
				comboFactByPartidaPresu.setCombooItems(comboItems);

				LOGGER.info(
						"comboFactByPartidaPresu() / fcsCertificacionesExtendsMapper.comboFactByPartidaPresu() -> Salida a  fcsCertificacionesExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn(
						"comboFactByPartidaPresu() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactByPartidaPresu() -> idInstitucion del token nula");
		}

		LOGGER.info("comboFactByPartidaPresu() -> Salida del servicio para obtener los tipos");

		return comboFactByPartidaPresu;
	}

	@Override
	public ComboDTO comboFactNull(String idCertificacion, HttpServletRequest request) {
		LOGGER.info("comboFactNull() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GenParametros> tamMax;
		Integer tamMaximo;
		String IdCertificados = "";

		ComboDTO comboFactNull = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboFactNull() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFactNull() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboFactNull() / fcsCertificacionesExtendsMapper.comboFactNull() -> Entrada a fcsCertificacionesExtendsMapper para obtener las facturaciones nulas ");

				 GenParametrosExample genParametrosExample = new GenParametrosExample();
                 genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                 genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				// Recuperar ID de las facturas que ya estan creadas.
				List<String> listaIdCertificados = fcsCertificacionesExtendsMapper.getFactIdCertificaciones(
						idCertificacion, idInstitucion.toString());
				
				
				if (listaIdCertificados != null  && !listaIdCertificados.isEmpty()) {
					
					// Añadir facturas a una array para no mostrar en el combo.
					for (String certificado : listaIdCertificados) {
						IdCertificados += certificado + ",";
					}
					
					// Quitar la última , para evitar un error.
					IdCertificados = IdCertificados.substring(0, IdCertificados.length()-1);
					
					LOGGER.info(
							"getFactIdCertificaciones() / fcsCertificacionesExtendsMapper.getFactIdCertificaciones() -> Salida de fcsCertificacionesExtendsMapper para obtener las facturaciones creadas ");
				}else {
					LOGGER.info(
							"getFactIdCertificaciones() / fcsCertificacionesExtendsMapper.getFactIdCertificaciones() -> No existen Facturas creadas para esta certificación ");
				}
				
				// Obtener todas las facturas que no han sido añadidas ya al certificado. 
				List<ComboItem> comboItems = fcsCertificacionesExtendsMapper.comboFactNull(idInstitucion.toString(),IdCertificados);
				comboFactNull.setCombooItems(comboItems);
				
				LOGGER.info(
						"comboFactNull() / fcsCertificacionesExtendsMapper.comboFactNull() -> Salida de fcsCertificacionesExtendsMapper para obtener las facturaciones nulas ");
			} else {
				LOGGER.warn(
						"comboFactNull() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactNull() -> idInstitucion del token nula");
		}

		LOGGER.info("comboFactNull() -> Salida del servicio para obtener los tipos");

		return comboFactNull;
	}

	@Override
	public ComboDTO comboFactBaremos(HttpServletRequest request) {
		LOGGER.info("comboFactByPartidaPresu() -> Entrada del servicio para obtener las facturaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboDTO comboFactBaremos = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"comboFactByPartidaPresu() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFactByPartidaPresu() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"comboFactByPartidaPresu() / fcsCertificacionesExtendsMapper.comboFactByPartidaPresu() -> Entrada a  fcsCertificacionesExtendsMapper para obtener las facturaciones");

				List<ComboItem> comboItems = fcsFacturacionJGExtendsMapper.comboFactBaremos(idInstitucion.toString());
				comboFactBaremos.setCombooItems(comboItems);

				LOGGER.info(
						"comboFactByPartidaPresu() / fcsCertificacionesExtendsMapper.comboFactByPartidaPresu() -> Salida a  fcsCertificacionesExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn(
						"comboFactByPartidaPresu() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboFactByPartidaPresu() -> idInstitucion del token nula");
		}

		LOGGER.info("comboFactByPartidaPresu() -> Salida del servicio para obtener los tipos");

		return comboFactBaremos;
	}

	@Override
	public ComboDTO comboEstadoAbonos(HttpServletRequest request) {

		LOGGER.info("comboEstadoAbonos() -> Entrada del servicio para obtener el listado de estados de Abonos");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboEstadosAbono = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboEstadoAbonos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboEstadoAbonos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"comboEstadoAbonos() / facAbonosjgExtendsMapper.comboEstadoAbonos() -> Entrada a facAbonosjgExtendsMapper para obtener el listado de estados de un abono");

				List<ComboItem> comboItems = facAbonosjgExtendsMapper
						.comboEstadosAbono(usuario.getIdlenguaje().toString());
				if (comboItems == null || comboItems.isEmpty())
					comboItems = facAbonosjgExtendsMapper.comboEstadosAbono("1");
				comboEstadosAbono.setCombooItems(comboItems);

				LOGGER.info(
						"comboEstadoAbonos() / facAbonosjgExtendsMapper.comboEstadoAbonos() -> Salida a facAbonosjgExtendsMapper para obtener el listado de estados de un abono");
			} else {
				LOGGER.warn(
						"comboEstadoAbonos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboEstadoAbonos() -> idInstitucion del token nula");
		}

		LOGGER.info("comboEstadoAbonos() -> Salida del servicio para obtener el listado de estados de un abono");
		return comboEstadosAbono;
	}

	@Override
	public ComboDTO comboGrupoFacturacion(HttpServletRequest request) {
		LOGGER.info("comboGrupoFacturacion() -> Entrada del servicio para obtener el listado de grupos de facturacion");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboGrupoFacturacion = new ComboDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"comboGrupoFacturacion() / facAbonosjgExtendsMapper.comboGrupoFacturacion() -> Entrada a facAbonosjgExtendsMapper para obtener el listado de grupos de facturacion");

				List<ComboItem> comboItems = facAbonosjgExtendsMapper
						.comboGrupoFacturacion(usuario.getIdlenguaje().toString(), idInstitucion.toString());
				comboGrupoFacturacion.setCombooItems(comboItems);

				LOGGER.info(
						"comboGrupoFacturacion() / facAbonosjgExtendsMapper.comboGrupoFacturacion() -> Salida a facAbonosjgExtendsMapper para obtener el listado de grupos de facturacion");
			} else {
				LOGGER.warn(
						"comboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboGrupoFacturacion() -> idInstitucion del token nula");
		}

		LOGGER.info("comboGrupoFacturacion() -> Salida del servicio para obtener el listado de grupos de facturacion");
		return comboGrupoFacturacion;
	}

	@Override
	public ComboDTO comboPagos(HttpServletRequest request) {

		LOGGER.info("comboPagos() -> Entrada del servicio para obtener el listado de grupos de pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboEstadosAbono = new ComboDTO();

		if (null != idInstitucion) {

			LOGGER.info(
					"comboPagos() / facAbonosjgExtendsMapper.comboPagos() -> Entrada a facAbonosjgExtendsMapper para obtener el listado de grupos de pago");

			List<ComboItem> comboItems = facAbonosjgExtendsMapper.comboPago(idInstitucion.toString());
			comboEstadosAbono.setCombooItems(comboItems);

		} else {
			LOGGER.warn("comboPagos() -> idInstitucion del token nula");
		}

		LOGGER.info("comboPagos() -> Salida del servicio para obtener el listado de grupos de pago");
		return comboEstadosAbono;
	}

}