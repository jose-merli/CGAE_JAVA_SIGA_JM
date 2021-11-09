package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionExample;
import org.itcgae.siga.db.entities.FacSufijo;
import org.itcgae.siga.db.entities.FacSufijoExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionMapper;
import org.itcgae.siga.db.mappers.FacSufijoMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFormapagoserieExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTipocliincluidoenseriefacExtendsMapper;
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
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;

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
	private ModModeloComunicacionExtendsMapper modModeloComunicacionExtendsMapper;

	@Autowired
	FacSeriefacturacionMapper facSeriefacturacionMapper;

	@Autowired
	private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Override
	public ComboDTO comboCuentasBancarias(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboCuentasBancarias() -> Entrada al servicio para recuperar el combo de cuentas bancarias");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboCuentasBancarias() / facBancoInstitucionExtendsMapper.comboCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de cuentas bancarias");

					// Logica
					comboItems = facBancoinstitucionExtendsMapper
							.comboCuentasBancarias(Short.parseShort(idInstitucion));
					LOGGER.debug("comboCuentasBancarias() ->" + comboItems.toString());

					// comprobar primero si la lista de cuentas bancarias viene vacia
					comboDTO.setCombooItems(comboItems);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboCuentasBancarias() -> Se ha producido un error al obtener el combo de cuentas bancarias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboCuentasBancarias() -> Salida del servicio para obtener el combo de cuentas bancarias");

		return comboDTO;
	}

	@Override
	public ComboDTO comboSufijos(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboSufijos() -> Entrada al servicio para recuperar el combo de sufijos");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboSufijos() / facBancoInstitucionExtendsMapper.comboSufijos() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de sufijos");

					// Logica
					FacSufijoExample exampleSufijo = new FacSufijoExample();
					exampleSufijo.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion));
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
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboSufijos() -> Se ha producido un error al obtener el combo de sufijos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboSufijos() -> Salida del servicio para obtener el combo de sufijos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboEtiquetas(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		String dni, idInstitucion;
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.debug("comboEtiquetas() -> Entrada al servicio para recuperar el combo de etiquetas");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboEtiquetas() / facBancoInstitucionExtendsMapper.comboEtiquetas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de etiquetas");

					String idioma = usuarios.get(0).getIdlenguaje();

					// Logica
					comboItems = cenGruposclienteExtendsMapper.comboEtiquetas(idioma, Short.parseShort(idInstitucion));

					comboDTO.setCombooItems(comboItems);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboEtiquetas() -> Se ha producido un error al obtener el combo de etiquetas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboEtiquetas() -> Salida del servicio para obtener el combo de etiquetas");

		return comboDTO;
	}

	@Override
	public ComboDTO comboDestinatarios(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboDestinatarios() -> Entrada al servicio para recuperar el combo de destinatarios");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboDestinatarios() / facBancoInstitucionExtendsMapper.comboDestinatarios() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de destinatarios");

					// Logica
					comboItems = cenGruposclienteClienteExtendsMapper
							.comboDestinatarios(Short.parseShort(idInstitucion));

					comboDTO.setCombooItems(comboItems);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboDestinatarios() -> Se ha producido un error al obtener el combo de destinatarios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboDestinatarios() -> Salida del servicio para obtener el combo de destinatarios");

		return comboDTO;
	}

	@Override
	public ComboDTO comboContadores(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboContadores() -> Entrada al servicio para recuperar el combo de contadores");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboContadores() / facBancoInstitucionExtendsMapper.comboContadores() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de contadores");

					// Logica
					AdmContadorExample exampleContador = new AdmContadorExample();
					exampleContador.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdtablaEqualTo("FAC_FACTURA");
					exampleContador.setOrderByClause("NOMBRE");

					List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

					if (contadores != null) {
						comboItems = new ArrayList<>();
						for (AdmContador contador : contadores) {
							ComboItem comboItem = new ComboItem();
							comboItem.setValue(contador.getIdcontador());
							comboItem.setLabel(contador.getNombre());

							comboItems.add(comboItem);
						}

						comboDTO.setCombooItems(comboItems);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboContadores() -> Se ha producido un error al obtener el combo de contadores",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboContadores() -> Salida del servicio para obtener el combo de contadores");

		return comboDTO;
	}

	@Override
	public ComboDTO comboContadoresRectificativas(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug(
				"comboContadoresRectificativas() -> Entrada al servicio para recuperar el combo de contadores rectificativas");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboContadoresRectificativas() / facBancoInstitucionExtendsMapper.comboContadoresRectificativas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de contadores rectificativas");

					// Logica
					AdmContadorExample exampleContador = new AdmContadorExample();
					exampleContador.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdtablaEqualTo("FAC_ABONO").andIdcontadorNotEqualTo("FAC_ABONOS_FCS");
					exampleContador.setOrderByClause("NOMBRE");

					List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

					if (contadores != null) {
						comboItems = new ArrayList<>();
						for (AdmContador contador : contadores) {
							ComboItem comboItem = new ComboItem();
							comboItem.setValue(contador.getIdcontador());
							comboItem.setLabel(contador.getNombre());

							comboItems.add(comboItem);
						}

						comboDTO.setCombooItems(comboItems);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboContadoresRectificativas() -> Se ha producido un error al obtener el combo de contadores rectificativas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug(
				"comboContadoresRectificativas() -> Salida del servicio para obtener el combo de contadores rectificativas");

		return comboDTO;
	}

	@Override
	public ComboDTO comboPlanificacion(String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboPlanificacion() -> Entrada al servicio para recuperar el combo de contadores rectificativas");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboPlanificacion() / fac.comboPlanificacion() -> Entrada a facSeriefacturacionExtendsMapper para obtener el combo de planificación");

					// Logica
					FacSeriefacturacionExample exampleSerieFacturacion = new FacSeriefacturacionExample();
					exampleSerieFacturacion.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdseriefacturacionNotEqualTo(Long.valueOf(idSerieFacturacion)).andFechabajaIsNull();
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
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboPlanificacion() -> Se ha producido un error al obtener el combo de planificación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboPlanificacion() -> Salida del servicio para obtener el combo de planificación");

		return comboDTO;
	}

	@Override
	public ComboDTO comboEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug(
				"getEtiquetasSerie() -> Entrada al servicio para recuperar las etiquetas de la serie de facturación");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getEtiquetasSerie() / facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie() -> Entrada a facTipocliincluidoenseriefacExtendsMapper para obtener las etiquetas de la serie");

					// Logica
					String idioma = usuarios.get(0).getIdlenguaje();
					comboItems = facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie(idSerieFacturacion,
							Short.parseShort(idInstitucion), idioma);

					LOGGER.debug(
							"getEtiquetasSerie() / facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie() -> Saliendo de facTipocliincluidoenseriefacExtendsMapper para obtener las etiquetas de la serie");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.getEtiquetasSerie() -> Se ha producido un error al obtener las etiquetas de la serie",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("getEtiquetasSerie() -> Salida del servicio para obtener las etiquetas de la serie");

		return comboDTO;
	}

	@Override
	public ComboDTO comboPlantillasEnvio(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboPlantillasEnvio() -> Entrada al servicio para recuperar lel combo de platillas envio");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboPlantillasEnvio() / envPlantillaEnviosExtendsMapper.comboPlantillasEnvio() -> Entrada a envPlantillaEnviosExtendsMapper para obtener el combo de platillas envio");

					// Logica
					comboItems = envPlantillaEnviosExtendsMapper.comboPlantillasEnvio(Short.parseShort(idInstitucion));

					LOGGER.debug(
							"comboPlantillasEnvio() / envPlantillaEnviosExtendsMapper.comboPlantillasEnvio() -> Saliendo de envPlantillaEnviosExtendsMapper para obtener el combo de platillas envio");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboPlantillasEnvio() -> Se ha producido un error al obtener el combo de platillas envio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboPlantillasEnvio() -> Salida del servicio para obtener el combo de platillas envio");

		return comboDTO;
	}

	@Override
	public ComboDTO getFormasPagosDisponiblesSeries(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("getFormasPagosDisponiblesSeries() -> Entrada al servicio para recuperar todas las formas de pago");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboPlantillasEnvio() / pysFormapagoExtendsMapper.getWayToPayWithIdFormapago() -> Entrada a pysFormapagoExtendsMapper para obtener todas las formas de pago");

					// Logica
					String idioma = usuarios.get(0).getIdlenguaje();
					comboItems = pysFormapagoExtendsMapper.getWayToPayWithIdFormapago(idioma);

					LOGGER.debug(
							"comboPlantillasEnvio() / pysFormapagoExtendsMapper.getWayToPayWithIdFormapago() -> Saliendo de pysFormapagoExtendsMapper para obtener todas las formas de pago");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.getFormasPagosDisponiblesSeries() -> Se ha producido un error al obtener todas las formas de pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("getFormasPagosDisponiblesSeries() -> Salida del servicio para obtener todas las formas de pago");

		return comboDTO;
	}

	@Override
	public ComboDTO getFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug(
				"getFormasPagosSerie() -> Entrada al servicio para recuperar las formas de pago de la serie de facturación");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getFormasPagosSerie() / facFormapagoserieExtendsMapper.getFormasPagosSerie() -> Entrada a facFormapagoserieExtendsMapper para obtener las formas de pago de la serie de facturación");

					// Logica
					String idioma = usuarios.get(0).getIdlenguaje();
					comboItems = facFormapagoserieExtendsMapper.getFormasPagosSerie(idSerieFacturacion,
							Short.parseShort(idInstitucion), idioma);

					LOGGER.debug(
							"getFormasPagosSerie() / facFormapagoserieExtendsMapper.getFormasPagosSerie() -> Saliendo de facFormapagoserieExtendsMapper para obtener las formas de pago de la serie de facturación");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.getFormasPagosSerie() -> Se ha producido un error al obtener las formas de pago de la serie de facturación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug(
				"getFormasPagosSerie() -> Salida del servicio para obtener las formas de pago de la serie de facturación");

		return comboDTO;
	}

	@Override
	public ComboDTO comboModelosComunicacion(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboModelosComunicacion() -> Entrada al servicio para recuperar los modelos de comunicación");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboModelosComunicacion() / modModeloComunicacionExtendsMapper.selectByExample() -> Entrada a modModeloComunicacionExtendsMapper para obtener los modelos de comunicación");

					// Logica
					ModModelocomunicacionExample modeloExample = new ModModelocomunicacionExample();
					modeloExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion));
					modeloExample.setOrderByClause("nombre");

					List<ModModelocomunicacion> modelos = modModeloComunicacionExtendsMapper
							.selectByExample(modeloExample);
					comboItems = modelos.stream().map(m -> {
						ComboItem item = new ComboItem();
						item.setValue(String.valueOf(m.getIdmodelocomunicacion()));
						item.setLabel(m.getNombre());
						return item;
					}).collect(Collectors.toList());

					LOGGER.debug(
							"comboModelosComunicacion() / modModeloComunicacionExtendsMapper.selectByExample() -> Saliendo de modModeloComunicacionExtendsMapper para obtener los modelos de comunicación");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboModelosComunicacion() -> Se ha producido un error al obtener los modelos de comunicación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboModelosComunicacion() -> Salida del servicio para obtener los modelos de comunicación");

		return comboDTO;
	}

	@Override
	public ComboDTO comboSeriesFacturacion(HttpServletRequest request) {

		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug(
				"FacturacionPySGeneralServiceImpl.comboSeriesFacturacion() -> Entrada al servicio para recuperar el combo de series de facturacion");

		// Conseguimos información del usuario logeado
		try {

			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"FacturacionPySGeneralServiceImpl.comboSeriesFacturacion() -> obteniendo datos para el el combo de series de facturación");

					FacSeriefacturacionExample example = new FacSeriefacturacionExample();
					example.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andFechabajaIsNull();

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
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboCuentasBancarias() -> Se ha producido un error al obtener el combo de cuentas bancarias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboCuentasBancarias() -> Salida del servicio para obtener el combo de cuentas bancarias");

		return comboDTO;
	}

	@Override
	public ComboDTO comboTiposIVA(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.debug("comboTiposIVA() -> Entrada al servicio para recuperar el combo de tipos de IVA");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboTiposIVA() / pySTipoIvaExtendsMapper.comboTiposIVA() -> Entrada a pySTipoIvaExtendsMapper para obtener el combo de tipos de IVA");

					String idioma = usuarios.get(0).getIdlenguaje();

					// Logica
					comboItems = pySTipoIvaExtendsMapper.comboTiposIVA(idioma);

					comboDTO.setCombooItems(comboItems);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySGeneralServiceImpl.comboTiposIVA() -> Se ha producido un error al obtener el combo de tipos de IVA",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboTiposIVA() -> Salida del servicio para obtener el combo de tipos de IVA");

		return comboDTO;
	}

}
