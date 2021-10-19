package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TiposIncluidosItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FacSufijoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionsuscripcionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysCompraExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;

@Service
public class FacturacionPySServiceImpl implements IFacturacionPySService {

	private Logger LOGGER = Logger.getLogger(FacturacionPySServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
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
	private FacFacturacionsuscripcionExtendsMapper facFacturacionsuscripcionExtendsMapper;

	@Autowired
	private PysCompraExtendsMapper pysCompraExtendsMapper;

	@Autowired
	private FacFacturaMapper facFacturaMapper;

	@Override
	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request) {
		
		CuentasBancariasDTO cuentasBancariasDTO = new CuentasBancariasDTO();
		List<CuentasBancariasItem> listaCuentasBancarias;
		Error error = new Error();

		LOGGER.info("getCuentasBancarias() -> Entrada al servicio para recuperar el listado de cuentas bancarias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.getCuentasBancarias() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getCuentasBancarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getCuentasBancarias() / facBancoInstitucionExtendsMapper.getCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para obtener el listado de cuentas bancarias");
					
					//Logica
					listaCuentasBancarias = facBancoinstitucionExtendsMapper.getCuentasBancarias(idInstitucion);
					LOGGER.info("getCuentasBancarias() ->" + listaCuentasBancarias.toString());
					
					//comprobar primero si la lista de cuentas bancarias viene vacia
					cuentasBancariasDTO.setCuentasBancariasITem(listaCuentasBancarias);
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getCuentasBancarias() -> Se ha producido un error al obtener el listado de cuentas bancarias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		cuentasBancariasDTO.setError(error);

		LOGGER.info("getCuentasBancarias() -> Salida del servicio para obtener el listado de cuentas bancarias");

		return cuentasBancariasDTO;
	}

	@Override
	public ComboDTO comboCuentasBancarias(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboCuentasBancarias() -> Entrada al servicio para recuperar el combo de cuentas bancarias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboCuentasBancarias() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboCuentasBancarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboCuentasBancarias() / facBancoInstitucionExtendsMapper.comboCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de cuentas bancarias");
					
					//Logica
					comboItems = facBancoinstitucionExtendsMapper.comboCuentasBancarias(idInstitucion);
					LOGGER.info("comboCuentasBancarias() ->" + comboItems.toString());
					
					//comprobar primero si la lista de cuentas bancarias viene vacia
					comboDTO.setCombooItems(comboItems);
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.comboCuentasBancarias() -> Se ha producido un error al obtener el combo de cuentas bancarias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboCuentasBancarias() -> Salida del servicio para obtener el combo de cuentas bancarias");
		
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboSufijos(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboSufijos() -> Entrada al servicio para recuperar el combo de sufijos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboSufijos() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboSufijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboSufijos() / facBancoInstitucionExtendsMapper.comboSufijos() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de sufijos");
					
					//Logica
					FacSufijoExample exampleSufijo = new FacSufijoExample();
					exampleSufijo.createCriteria().andIdinstitucionEqualTo(idInstitucion);
					exampleSufijo.setOrderByClause("SUFIJO, CONCEPTO");
					
					List<FacSufijo> sufijos = facSufijoMapper.selectByExample(exampleSufijo);
					
					if (sufijos != null) {
						comboItems = new ArrayList<>();
						for (FacSufijo facSufijo : sufijos) {
							ComboItem comboItem = new ComboItem();
							comboItem.setValue(facSufijo.getIdsufijo().toString());
							comboItem.setLabel(facSufijo.getSufijo().toString() + " - " +
									facSufijo.getConcepto());
							
							comboItems.add(comboItem);
						}
						
						LOGGER.info("comboSufijos() ->" + comboItems.toString());
						
						//comprobar primero si la lista de cuentas bancarias viene vacia
						comboDTO.setCombooItems(comboItems);
					}
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.comboSufijos() -> Se ha producido un error al obtener el combo de sufijos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboSufijos() -> Salida del servicio para obtener el combo de sufijos");
		
		return comboDTO;
	}

	@Override
	public ComboDTO comboEtiquetas(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboEtiquetas() -> Entrada al servicio para recuperar el combo de etiquetas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboEtiquetas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboEtiquetas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboEtiquetas() / facBancoInstitucionExtendsMapper.comboEtiquetas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de etiquetas");
					
					String idioma = usuarios.get(0).getIdlenguaje();
					
					//Logica
					comboItems = cenGruposclienteExtendsMapper.comboEtiquetas(idioma, idInstitucion);
					
					comboDTO.setCombooItems(comboItems);
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.comboEtiquetas() -> Se ha producido un error al obtener el combo de etiquetas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboEtiquetas() -> Salida del servicio para obtener el combo de etiquetas");
		
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboDestinatarios(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboDestinatarios() -> Entrada al servicio para recuperar el combo de destinatarios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboDestinatarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboDestinatarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboDestinatarios() / facBancoInstitucionExtendsMapper.comboDestinatarios() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de destinatarios");
					
					//Logica
					comboItems = cenGruposclienteClienteExtendsMapper.comboDestinatarios(idInstitucion);
					
					comboDTO.setCombooItems(comboItems);
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.comboDestinatarios() -> Se ha producido un error al obtener el combo de destinatarios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboDestinatarios() -> Salida del servicio para obtener el combo de destinatarios");
		
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboContadores(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboContadores() -> Entrada al servicio para recuperar el combo de contadores");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboContadores() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboContadores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboContadores() / facBancoInstitucionExtendsMapper.comboContadores() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de contadores");
					
					//Logica
					AdmContadorExample exampleContador = new AdmContadorExample();
					exampleContador.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtablaEqualTo("FAC_FACTURA");
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
					"FacturacionPySServiceImpl.comboContadores() -> Se ha producido un error al obtener el combo de contadores",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboContadores() -> Salida del servicio para obtener el combo de contadores");
		
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboContadoresRectificativas(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		
		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboContadoresRectificativas() -> Entrada al servicio para recuperar el combo de contadores rectificativas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboContadoresRectificativas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboContadoresRectificativas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboContadoresRectificativas() / facBancoInstitucionExtendsMapper.comboContadoresRectificativas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de contadores rectificativas");
					
					//Logica
					AdmContadorExample exampleContador = new AdmContadorExample();
					exampleContador.or()
						.andIdinstitucionEqualTo(idInstitucion)
						.andIdtablaEqualTo("FAC_ABONO")
						.andIdcontadorNotEqualTo("FAC_ABONOS_FCS");
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
					"FacturacionPySServiceImpl.comboContadoresRectificativas() -> Se ha producido un error al obtener el combo de contadores rectificativas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboContadoresRectificativas() -> Salida del servicio para obtener el combo de contadores rectificativas");
		
		return comboDTO;
	}
	
	@Override
	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, HttpServletRequest request) {
		LOGGER.info("getSeriesFacturacion() -> Entrada al servicio para buscar series de facturación");

		Error error = new Error();
		SeriesFacturacionDTO seriesFacturacionDTO = new SeriesFacturacionDTO();
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"getSeriesFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"getSeriesFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					String idioma = usuarios.get(0).getIdlenguaje();
					List<SerieFacturacionItem> serieFacturacionItems = facSeriefacturacionExtendsMapper.getSeriesFacturacion(serieFacturacionItem, idInstitucion, idioma);

					if (null != serieFacturacionItems && !serieFacturacionItems.isEmpty()) {
						List<TiposIncluidosItem> tiposIncluidos = new ArrayList<>();

						LOGGER.info("getSeriesFacturacion() -> Obteniendo los tipos de servicios para la institución");
						tiposIncluidos.addAll(facFacturacionsuscripcionExtendsMapper.getTiposServicios(idInstitucion, idioma));
						LOGGER.info("getSeriesFacturacion() -> Obteniendo los tipos de productos para la institución");
						tiposIncluidos.addAll(pysCompraExtendsMapper.getTiposProductos(idInstitucion, idioma));

						for (SerieFacturacionItem serieItem : serieFacturacionItems) {
							String idSerieFacturacion = serieItem.getIdSerieFacturacion();

							List<String> tiposIncluidosSerie = tiposIncluidos.stream()
									.filter(t -> idSerieFacturacion.equals(t.getIdSerieFacturacion()))
									.map(t -> t.getDescripcion())
									.collect(Collectors.toList());

							serieItem.setTiposIncluidos(tiposIncluidosSerie);
						}
					}

					seriesFacturacionDTO.setSerieFacturacionItems(serieFacturacionItems);
				} else {
					LOGGER.warn(
							"getSeriesFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("getSeriesFacturacion() -> idInstitucion del token nula");
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getSeriesFacturacion() -> Se ha producido un error al obtener las series de facturación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}
		seriesFacturacionDTO.setError(error);
		
		LOGGER.info("getSeriesFacturacion() -> Salida del servicio para buscar series de facturación");
		return seriesFacturacionDTO;
	}

	@Override
	public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		LOGGER.info("eliminaSerieFacturacion() -> Entrada al servicio para eliminar series de facturación");

		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"eliminaSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"eliminaSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					for (SerieFacturacionItem serieFacturacion : serieFacturacionItems) {
						FacFacturaExample facturaExample = new FacFacturaExample();
						facturaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

						FacSeriefacturacionExample seriefacturacionExample = new FacSeriefacturacionExample();
						seriefacturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

						long numFacturas = facFacturaMapper.countByExample(facturaExample);
						if (numFacturas == 0) {
							LOGGER.info("eliminaSerieFacturacion() -> Baja física de la serie de facturación con idseriefacturacion=" + serieFacturacion.getIdSerieFacturacion());

							facSeriefacturacionExtendsMapper.deleteByExample(seriefacturacionExample);
						} else {
							LOGGER.info("eliminaSerieFacturacion() -> Baja lógica de la serie de facturación con idseriefacturacion=" + serieFacturacion.getIdSerieFacturacion());

							FacSeriefacturacion sf = new FacSeriefacturacion();
							sf.setFechabaja(new Date());
							facSeriefacturacionExtendsMapper.updateByExampleSelective(sf, seriefacturacionExample);
						}
					}

				} else {
					LOGGER.warn(
							"eliminaSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("eliminaSerieFacturacion() -> idInstitucion del token nula");
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.eliminaSerieFacturacion() -> Se ha producido un error al eliminar las series de facturación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}
		deleteResponseDTO.setError(error);

		LOGGER.info("eliminaSerieFacturacion() -> Salida del servicio para eliminar series de facturación");
		return deleteResponseDTO;
	}

	@Override
	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		LOGGER.info("reactivarSerieFacturacion() -> Entrada al servicio para reactivar series de facturación");

		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"reactivarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"reactivarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					String idioma = usuarios.get(0).getIdlenguaje();

					for (SerieFacturacionItem serieFacturacion : serieFacturacionItems) {
						LOGGER.info("reactivarSerieFacturacion() -> Reactivando serie facturación con id=" + serieFacturacion.getIdSerieFacturacion());

						FacSeriefacturacionExample seriefacturacionExample = new FacSeriefacturacionExample();
						seriefacturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

						List<FacSeriefacturacion> sfResults = facSeriefacturacionExtendsMapper.selectByExample(seriefacturacionExample);
						if (null != sfResults && !sfResults.isEmpty()) {
							FacSeriefacturacion sfToUpdate = sfResults.get(0);
							sfToUpdate.setFechabaja(null);
							facSeriefacturacionExtendsMapper.updateByExample(sfToUpdate, seriefacturacionExample);
						} else {
							LOGGER.warn("reactivarSerieFacturacion() -> No existe serie facturación con id=" + serieFacturacion.getIdSerieFacturacion());
						}
					}

				} else {
					LOGGER.warn(
							"reactivarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("reactivarSerieFacturacion() -> idInstitucion del token nula");
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.reactivarSerieFacturacion() -> Se ha producido un error al reactivar las series de facturación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}
		updateResponseDTO.setError(error);

		LOGGER.info("reactivarSerieFacturacion() -> Salida del servicio para reactivar series de facturación");
		return updateResponseDTO;
	}
	
}
