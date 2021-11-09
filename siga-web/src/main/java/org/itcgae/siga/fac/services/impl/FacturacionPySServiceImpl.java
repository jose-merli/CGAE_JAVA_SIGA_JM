package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionBancoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionsuscripcionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFormapagoserieExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTipocliincluidoenseriefacExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysCompraExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturacionPySServiceImpl implements IFacturacionPySService {

	private Logger LOGGER = Logger.getLogger(FacturacionPySServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

	@Autowired
	private FacFacturaMapper facFacturaMapper;

	@Autowired
	private AdmContadorMapper admContadorMapper;

	@Autowired
	private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

	@Autowired
	private FacFacturacionsuscripcionExtendsMapper facFacturacionsuscripcionExtendsMapper;

	@Autowired
	private PysCompraExtendsMapper pysCompraExtendsMapper;

	@Autowired
	private FacSeriefacturacionBancoMapper facSeriefacturacionBancoMapper;

	@Autowired
	private FacTipocliincluidoenseriefacExtendsMapper facTipocliincluidoenseriefacExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private FacFormapagoserieExtendsMapper facFormapagoserieExtendsMapper;

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Autowired
	private FacDisquetecargosExtendsMapper facDisquetecargosExtendsMapper;

	@Override
	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) {

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("borrarCuentasBancarias() -> Entrada al servicio para dar de baja las cuentas bancarias");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"borrarCuentasBancarias() / facBancoInstitucionExtendsMapper.getCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para establecer la fecha de baja");

					// Logica
					for (CuentasBancariasItem cuenta : cuentasBancarias) {

						FacBancoinstitucionKey cuentasbancariasKey = new FacBancoinstitucionKey();
						cuentasbancariasKey.setIdinstitucion(Short.parseShort(idInstitucion));
						cuentasbancariasKey.setBancosCodigo(cuenta.getBancosCodigo());

						LOGGER.info("\n\nTratamiento de la cuenta con IBAN: " + cuenta.getIBAN() + "\n\n");

						if (Integer.parseInt(cuenta.getNumUsos()) < 1) {
							this.facBancoinstitucionExtendsMapper.deleteByPrimaryKey(cuentasbancariasKey);
						} else {
							FacBancoinstitucion cuentaCambio = this.facBancoinstitucionExtendsMapper.selectByPrimaryKey(cuentasbancariasKey);
							if (cuentaCambio != null) {
								cuentaCambio.setFechabaja(new Date());
								this.facBancoinstitucionExtendsMapper.updateByPrimaryKey(cuentaCambio);
							}
						}

					}
				}
				deleteResponseDTO.setStatus(HttpStatus.OK.toString());
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.borrarCuentasBancarias() -> Se ha producido un error al eliminar las cuentas bancarias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("borrarCuentasBancarias() -> Salida del servicio para eliminar las cuentas bancarias");

		return deleteResponseDTO;

	}

	@Override
	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request) {

		CuentasBancariasDTO cuentasBancariasDTO = new CuentasBancariasDTO();
		List<CuentasBancariasItem> listaCuentasBancarias;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("getCuentasBancarias() -> Entrada al servicio para recuperar el listado de cuentas bancarias");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getCuentasBancarias() / facBancoInstitucionExtendsMapper.getCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para obtener el listado de cuentas bancarias");

					// Logica
					listaCuentasBancarias = facBancoinstitucionExtendsMapper
							.getCuentasBancarias(Short.parseShort(idInstitucion));
					LOGGER.info("getCuentasBancarias() ->" + listaCuentasBancarias.toString());

					// comprobar primero si la lista de cuentas bancarias viene vacia
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
	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem,
			HttpServletRequest request) {
		LOGGER.info("getSeriesFacturacion() -> Entrada al servicio para buscar series de facturación");

		String dni, idInstitucion;
		Error error = new Error();
		SeriesFacturacionDTO seriesFacturacionDTO = new SeriesFacturacionDTO();

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					String idioma = usuarios.get(0).getIdlenguaje();
					List<SerieFacturacionItem> serieFacturacionItems = facSeriefacturacionExtendsMapper
							.getSeriesFacturacion(serieFacturacionItem, Short.parseShort(idInstitucion), idioma);

					if (null != serieFacturacionItems && !serieFacturacionItems.isEmpty()) {

						for (SerieFacturacionItem serieItem : serieFacturacionItems) {
							String idSerieFacturacion = serieItem.getIdSerieFacturacion();

							LOGGER.info(
									"getSeriesFacturacion() -> Obteniendo los tipos de servicios para idInstitucion="
											+ idInstitucion + ", idSerieFacturacion=" + idSerieFacturacion);
							List<ComboItem> tiposServicios = facFacturacionsuscripcionExtendsMapper
									.getTiposServicios(idSerieFacturacion, Short.parseShort(idInstitucion), idioma);
							LOGGER.info(
									"getSeriesFacturacion() -> Obteniendo los tipos de productos para idInstitucion="
											+ idInstitucion + ", idSerieFacturacion=" + idSerieFacturacion);
							List<ComboItem> tiposProductos = pysCompraExtendsMapper
									.getTiposProductos(idSerieFacturacion, Short.parseShort(idInstitucion), idioma);

							List<String> tiposIncluidos = Stream
									.concat(tiposServicios.stream(), tiposProductos.stream()).map(t -> t.getLabel())
									.collect(Collectors.toList());

							serieItem.setTiposIncluidos(tiposIncluidos);
							serieItem.setTiposServicios(tiposServicios);
							serieItem.setTiposProductos(tiposProductos);
						}
					}

					seriesFacturacionDTO.setSerieFacturacionItems(serieFacturacionItems);
				} else {
					LOGGER.debug(
							"getSeriesFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.debug("getSeriesFacturacion() -> idInstitucion del token nula");
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
	@Transactional
	public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request) {
		LOGGER.info("eliminaSerieFacturacion() -> Entrada al servicio para eliminar series de facturación");

		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		String dni, idInstitucion;

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					for (SerieFacturacionItem serieFacturacion : serieFacturacionItems) {
						FacFacturaExample facturaExample = new FacFacturaExample();
						facturaExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
								.andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

						FacSeriefacturacionExample seriefacturacionExample = new FacSeriefacturacionExample();
						seriefacturacionExample.createCriteria()
								.andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
								.andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

						List<FacSeriefacturacion> sfResults = facSeriefacturacionExtendsMapper
								.selectByExample(seriefacturacionExample);
						if (null != sfResults && !sfResults.isEmpty()) {
							FacSeriefacturacion sfToUpdate = sfResults.get(0);

							if (sfToUpdate.getFechabaja() == null) {
								long numFacturas = facFacturaMapper.countByExample(facturaExample);
								if (numFacturas == 0) {
									LOGGER.info(
											"eliminaSerieFacturacion() -> Baja física de la serie de facturación con idseriefacturacion="
													+ serieFacturacion.getIdSerieFacturacion());

									facSeriefacturacionExtendsMapper.deleteByExample(seriefacturacionExample);
								} else {
									LOGGER.info(
											"eliminaSerieFacturacion() -> Baja lógica de la serie de facturación con idseriefacturacion="
													+ serieFacturacion.getIdSerieFacturacion());

									FacSeriefacturacion sf = new FacSeriefacturacion();
									sf.setFechabaja(new Date());
									facSeriefacturacionExtendsMapper.updateByExampleSelective(sf,
											seriefacturacionExample);
								}
							} else {
								LOGGER.debug(
										"eliminaSerieFacturacion() -> Ya se encontraba eliminada la serie de facturación con id="
												+ serieFacturacion.getIdSerieFacturacion());
							}
						} else {
							LOGGER.debug("eliminaSerieFacturacion() -> No existe serie facturación con id="
									+ serieFacturacion.getIdSerieFacturacion());

						}

					}

				} else {
					LOGGER.debug(
							"eliminaSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.debug("eliminaSerieFacturacion() -> idInstitucion del token nula");
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
	@Transactional
	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request) {
		LOGGER.info("reactivarSerieFacturacion() -> Entrada al servicio para reactivar series de facturación");

		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"reactivarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"reactivarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {

					for (SerieFacturacionItem serieFacturacion : serieFacturacionItems) {
						LOGGER.info("reactivarSerieFacturacion() -> Reactivando serie facturación con id="
								+ serieFacturacion.getIdSerieFacturacion());

						FacSeriefacturacionExample seriefacturacionExample = new FacSeriefacturacionExample();
						seriefacturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

						List<FacSeriefacturacion> sfResults = facSeriefacturacionExtendsMapper
								.selectByExample(seriefacturacionExample);
						if (null != sfResults && !sfResults.isEmpty()) {
							FacSeriefacturacion sfToUpdate = sfResults.get(0);
							if (sfToUpdate.getFechabaja() != null) {
								sfToUpdate.setFechabaja(null);
								facSeriefacturacionExtendsMapper.updateByExample(sfToUpdate, seriefacturacionExample);
							} else {
								LOGGER.debug(
										"reactivarSerieFacturacion() -> Ya se encontraba activa la serie de facturación con id="
												+ serieFacturacion.getIdSerieFacturacion());
							}
						} else {
							LOGGER.debug("reactivarSerieFacturacion() -> No existe serie facturación con id="
									+ serieFacturacion.getIdSerieFacturacion());
						}
					}

				} else {
					LOGGER.debug(
							"reactivarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.debug("reactivarSerieFacturacion() -> idInstitucion del token nula");
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

	@Override
	@Transactional
	public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion,
			HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		Long idSerieFacturacion = null;
		String dni, idInstitucion;

		LOGGER.info("guardarSerieFacturacion() -> Entrada al servicio para guardar una serie de facturación");

		// Conseguimos información del usuario logeado
		HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

		dni = authentication.get("dni");
		idInstitucion = authentication.get("idInstitucion");

		if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
			List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

			if (usuarios != null && !usuarios.isEmpty()) {
				Integer idUsuario = usuarios.get(0).getIdusuario();
				LOGGER.info(
						"comboPlanificacion() / facSeriefacturacionExtendsMapper.selectByExample() -> Entrada a facSeriefacturacionExtendsMapper para obtener la serie de facturación");

				// Logica

				// 1. Actualizar FAC_SERIEFACTURACION
				boolean isNewSerie = serieFacturacion.getIdSerieFacturacion() == null
						|| serieFacturacion.getIdSerieFacturacion().trim().isEmpty();
				FacSeriefacturacion serieToUpdate = null;

				if (isNewSerie) {
					serieToUpdate = new FacSeriefacturacion();
					serieToUpdate.setIdinstitucion(Short.parseShort(idInstitucion));
					serieToUpdate.setIdNombreDescargaFac(Short.parseShort("1"));
					serieToUpdate.setTraspasofacturas("0");
					serieToUpdate.setIdcontador("FAC_GENERAL"); // Por consultar
					serieToUpdate.setIdplantilla(1); // Por consultar

					idSerieFacturacion = Long.parseLong(facSeriefacturacionExtendsMapper
							.getNextIdSerieFacturacion(Short.parseShort(idInstitucion)).getNewId());
					serieToUpdate.setIdseriefacturacion(idSerieFacturacion);
				} else {
					idSerieFacturacion = Long.parseLong(serieFacturacion.getIdSerieFacturacion());
					FacSeriefacturacionKey serieKey = new FacSeriefacturacionKey();
					serieKey.setIdinstitucion(Short.parseShort(idInstitucion));
					serieKey.setIdseriefacturacion(idSerieFacturacion);

					serieToUpdate = facSeriefacturacionExtendsMapper.selectByPrimaryKey(serieKey);
				}

				serieToUpdate.setUsumodificacion(idUsuario);
				serieToUpdate.setFechamodificacion(new Date());

				// 1. Actualizar datos generales
				if (serieFacturacion.getAbreviatura() != null && !serieFacturacion.getAbreviatura().trim().isEmpty()
						&& serieFacturacion.getAbreviatura().trim().length() <= 20) {
					serieToUpdate.setNombreabreviado(serieFacturacion.getAbreviatura().trim());

					// 1.1. Abreviatura única
					FacSeriefacturacionExample uniqueExample = new FacSeriefacturacionExample();
					if (isNewSerie)
						uniqueExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
								.andNombreabreviadoEqualTo(serieFacturacion.getAbreviatura().trim());
					else
						uniqueExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
								.andIdseriefacturacionNotEqualTo(idSerieFacturacion)
								.andNombreabreviadoEqualTo(serieFacturacion.getAbreviatura().trim());

					long found = facSeriefacturacionExtendsMapper.countByExample(uniqueExample);

					if (found > 0) {
						error.setCode(400);
						error.setDescription("facturacion.seriesFactura.abreviatura.unica");
						updateResponseDTO.setStatus(SigaConstants.KO);
					}

				}

				if (serieFacturacion.getDescripcion() != null && !serieFacturacion.getDescripcion().trim().isEmpty()
						&& serieFacturacion.getDescripcion().trim().length() <= 100) {
					serieToUpdate.setDescripcion(serieFacturacion.getDescripcion());

					// 1.2. Descripción única
					FacSeriefacturacionExample uniqueExample = new FacSeriefacturacionExample();
					if (isNewSerie)
						uniqueExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
								.andDescripcionEqualTo(serieFacturacion.getDescripcion().trim());
					else
						uniqueExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
								.andIdseriefacturacionNotEqualTo(idSerieFacturacion)
								.andDescripcionEqualTo(serieFacturacion.getDescripcion().trim());

					long found = facSeriefacturacionExtendsMapper.countByExample(uniqueExample);

					if (found > 0) {
						error.setCode(400);
						error.setDescription("facturacion.seriesFactura.descripcion.unica");
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				}

				if (serieFacturacion.getIdSerieFacturacionPrevia() != null
						&& !serieFacturacion.getIdSerieFacturacionPrevia().trim().isEmpty()) {
					serieToUpdate.setIdseriefacturacionprevia(
							Long.parseLong(serieFacturacion.getIdSerieFacturacionPrevia()));
				} else {
					serieToUpdate.setIdseriefacturacionprevia(null);
				}

				if (serieFacturacion.getObservaciones() != null && !serieFacturacion.getObservaciones().trim().isEmpty()
						&& serieFacturacion.getObservaciones().trim().length() <= 4000) {
					serieToUpdate.setObservaciones(serieFacturacion.getObservaciones().trim());
				} else {
					serieToUpdate.setObservaciones(null);
				}

				if (serieFacturacion.getSerieGenerica()) {
					serieToUpdate.setTiposerie("G");
				} else {
					serieToUpdate.setTiposerie(null);
				}

				// 2. Actualizar contadores
				if (serieFacturacion.getIdContadorFacturasRectificativas() != null
						&& !serieFacturacion.getIdContadorFacturasRectificativas().trim().isEmpty()) {
					serieToUpdate.setIdcontador(serieFacturacion.getIdContadorFacturasRectificativas());
				} else if (serieFacturacion.getIdContadorFacturas() != null
						&& !serieFacturacion.getIdContadorFacturas().trim().isEmpty()) {
					serieToUpdate.setIdcontador(serieFacturacion.getIdContadorFacturas());
				}

				// 3. Actualizar generación de ficheros
				serieToUpdate.setGenerarpdf(serieFacturacion.getGenerarPDF() ? "1" : "0");
				if (serieFacturacion.getIdModeloFactura() != null
						&& !serieFacturacion.getIdModeloFactura().trim().isEmpty()) {
					serieToUpdate.setIdmodelofactura(Long.parseLong(serieFacturacion.getIdModeloFactura()));
				} else {
					serieToUpdate.setIdmodelofactura(null);
				}

				if (serieFacturacion.getIdModeloRectificativa() != null
						&& !serieFacturacion.getIdModeloRectificativa().trim().isEmpty()) {
					serieToUpdate.setIdmodelorectificativa(Long.parseLong(serieFacturacion.getIdModeloRectificativa()));
				} else {
					serieToUpdate.setIdmodelorectificativa(null);
				}

				// 4. Envío de facturas
				serieToUpdate.setEnviofacturas(serieFacturacion.getEnvioFacturas() ? "1" : "0");

				if (serieFacturacion.getIdPlantillaMail() != null
						&& !serieFacturacion.getIdPlantillaMail().trim().isEmpty()) {
					serieToUpdate.setIdtipoenvios(Short.parseShort("1")); // Por corregir: Tiene que buscar el tipo
																			// correo electrónico
					serieToUpdate.setIdtipoplantillamail(Integer.parseInt(serieFacturacion.getIdPlantillaMail()));
				} else {
					serieToUpdate.setIdtipoenvios(null);
					serieToUpdate.setIdtipoplantillamail(null);
				}

				// 5. Actualizar traspaso de facturas
				serieToUpdate.setTraspasofacturas(serieFacturacion.getTraspasoFacturas() ? "1" : "0");

				if (serieFacturacion.getTraspasoPlantilla() != null
						&& !serieFacturacion.getTraspasoPlantilla().trim().isEmpty()
						&& serieFacturacion.getTraspasoPlantilla().trim().length() <= 10) {
					serieToUpdate.setTraspasoPlantilla(serieFacturacion.getTraspasoPlantilla().trim());
				} else {
					serieToUpdate.setTraspasoPlantilla(null);
				}

				if (serieFacturacion.getTraspasoCodAuditoriaDef() != null
						&& !serieFacturacion.getTraspasoCodAuditoriaDef().trim().isEmpty()
						&& serieFacturacion.getTraspasoCodAuditoriaDef().trim().length() <= 10) {
					serieToUpdate.setTraspasoCodauditoriaDef(serieFacturacion.getTraspasoCodAuditoriaDef().trim());
				} else {
					serieToUpdate.setTraspasoCodauditoriaDef(null);
				}

				// 6. Actualizar exportación contabilidad
				if (serieFacturacion.getConfDeudor() != null && !serieFacturacion.getConfDeudor().trim().isEmpty())
					serieToUpdate.setConfdeudor(serieFacturacion.getConfDeudor().trim());
				else
					serieToUpdate.setConfdeudor(null);

				if (serieFacturacion.getConfIngresos() != null && !serieFacturacion.getConfIngresos().trim().isEmpty())
					serieToUpdate.setConfingresos(serieFacturacion.getConfIngresos().trim());
				else
					serieFacturacion.setConfIngresos(null);

				serieToUpdate.setCtaclientes(serieFacturacion.getCtaClientes().trim());
				serieToUpdate.setCtaingresos(serieFacturacion.getCtaIngresos().trim());

				if (error.getCode() == null) {
					if (isNewSerie) {
						facSeriefacturacionExtendsMapper.insert(serieToUpdate);
					} else {
						facSeriefacturacionExtendsMapper.updateByPrimaryKey(serieToUpdate);
					}
				}

				// 7. Actualizar FAC_SERIEFACTURACION_BANCO
				FacSeriefacturacionBancoExample bancoExample = new FacSeriefacturacionBancoExample();
				bancoExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
						.andIdseriefacturacionEqualTo(idSerieFacturacion);
				List<FacSeriefacturacionBanco> serieBancoItems = facSeriefacturacionBancoMapper
						.selectByExample(bancoExample);
				boolean isNewBanco = serieBancoItems == null || serieBancoItems.isEmpty();

				FacSeriefacturacionBanco serieBancoToUpdate = new FacSeriefacturacionBanco();
				if (isNewBanco) {
					serieBancoToUpdate.setIdinstitucion(Short.parseShort(idInstitucion));
					serieBancoToUpdate.setIdseriefacturacion(idSerieFacturacion);
				} else {
					serieBancoToUpdate = serieBancoItems.get(0);
				}
				serieBancoToUpdate.setUsumodificacion(idUsuario);
				serieBancoToUpdate.setFechamodificacion(new Date());
				serieBancoToUpdate.setBancosCodigo(serieFacturacion.getIdCuentaBancaria());
				serieBancoToUpdate.setIdsufijo(Short.parseShort(serieFacturacion.getIdSufijo()));

				if (error.getCode() == null) {
					if (!isNewBanco) {
						facSeriefacturacionBancoMapper.updateByExample(serieBancoToUpdate, bancoExample);
					} else {
						facSeriefacturacionBancoMapper.insert(serieBancoToUpdate);
					}
				}

				// 8. Actualizar tipos de productos

				// 9. Actualizar tipos de servicios

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}

		updateResponseDTO.setId(String.valueOf(idSerieFacturacion));
		updateResponseDTO.setError(error);

		LOGGER.info("guardarSerieFacturacion() -> Salida del servicio para guardar la serie de facturación");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarEtiquetasSerieFacturacion(TarjetaPickListSerieDTO etiquetas,
			HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("guardarEtiquetasSerieFacturacion() -> Entrada al servicio para guardar las etiquetas de la serie");

		// Conseguimos información del usuario logeado
		HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

		dni = authentication.get("dni");
		idInstitucion = authentication.get("idInstitucion");

		if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
			List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

			if (usuarios != null && !usuarios.isEmpty()) {
				Integer idUsuario = usuarios.get(0).getIdusuario();

				// Logica
				FacTipocliincluidoenseriefac etiqueta = null;
				Long idSerie = Long.parseLong(etiquetas.getIdSerieFacturacion());

				// Borra las formas de pago anteriores
				// facFormapagoserieExtendsMapper.deleteByExample(formapagoExample);

				for (ComboItem item : etiquetas.getNoSeleccionados()) {
					Short idGrupo = Short.parseShort(item.getValue());

					FacTipocliincluidoenseriefacExample etiquetaExample = new FacTipocliincluidoenseriefacExample();
					etiquetaExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdseriefacturacionEqualTo(idSerie).andIdgrupoEqualTo(idGrupo)
							.andIdinstitucionGrupoEqualTo(Short.parseShort(idInstitucion));

					facTipocliincluidoenseriefacExtendsMapper.deleteByExample(etiquetaExample);
				}

				for (ComboItem item : etiquetas.getSeleccionados()) {
					Short idGrupo = Short.parseShort(item.getValue());

					FacTipocliincluidoenseriefacExample etiquetaExample = new FacTipocliincluidoenseriefacExample();
					etiquetaExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdseriefacturacionEqualTo(idSerie).andIdgrupoEqualTo(idGrupo)
							.andIdinstitucionGrupoEqualTo(Short.parseShort(idInstitucion));

					long size = facTipocliincluidoenseriefacExtendsMapper.countByExample(etiquetaExample);

					if (size == 0) {
						etiqueta = new FacTipocliincluidoenseriefac();
						etiqueta.setUsumodificacion(idUsuario);
						etiqueta.setFechamodificacion(new Date());
						etiqueta.setIdinstitucion(Short.parseShort(idInstitucion));
						etiqueta.setIdseriefacturacion(idSerie);
						etiqueta.setIdgrupo(idGrupo);
						etiqueta.setIdinstitucionGrupo(Short.parseShort(idInstitucion));

						facTipocliincluidoenseriefacExtendsMapper.insert(etiqueta);
					}
				}
			}
		}

		updateResponseDTO.setError(error);

		LOGGER.info("guardarEtiquetasSerieFacturacion() -> Salida del servicio para guardar las etiquetas de la serie");

		return updateResponseDTO;
	}

	@Override
	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request) {
		DestinatariosSeriesDTO destinatariosSeriesDTO = new DestinatariosSeriesDTO();

		List<DestinatariosSeriesItem> destinatariosSeriesItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info(
				"getDestinatariosSeries() -> Entrada al servicio para recuperar los destinatarios de la serie de facturación");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getDestinatariosSeries() / cenPersonaExtendsMapper.getDestinatariosSeries() -> Entrada a facTipocliincluidoenseriefacExtendsMapper para obtener los destinatarios de la serie");

					// Logica
					destinatariosSeriesItems = cenPersonaExtendsMapper
							.getDestinatariosSeries(Short.parseShort(idInstitucion), idSerieFacturacion);

					LOGGER.debug(
							"getDestinatariosSeries() / cenPersonaExtendsMapper.getDestinatariosSeries() -> Saliendo de facTipocliincluidoenseriefacExtendsMapper para obtener los destinatarios de la serie");

					destinatariosSeriesDTO.setDestinatariosSeriesItems(destinatariosSeriesItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getDestinatariosSeries() -> Se ha producido un error al obtener los destinatiarios de la serie",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		destinatariosSeriesDTO.setError(error);

		LOGGER.info(
				"getDestinatariosSeries() -> Salida del servicio para obtener los destinatarios de la serie de facturación");

		return destinatariosSeriesDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("guardarFormasPagosSerie() -> Entrada al servicio para guardar las formas de pago");

		// Conseguimos información del usuario logeado
		HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

		dni = authentication.get("dni");
		idInstitucion = authentication.get("idInstitucion");

		if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
			List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

			if (usuarios != null && !usuarios.isEmpty()) {
				Integer idUsuario = usuarios.get(0).getIdusuario();
				LOGGER.info(
						"guardarFormasPagosSerie() / facFormapagoserieExtendsMapper.insertSelective() -> Entrada a facFormapagoserieExtendsMapper para guardar las formas de pago");

				// Logica
				FacFormapagoserie formapagoserie = null;
				Long idSerie = Long.parseLong(formasPagos.getIdSerieFacturacion());

				for (ComboItem item : formasPagos.getNoSeleccionados()) {
					Short idFormaPago = Short.parseShort(item.getValue());

					FacFormapagoserieExample formapagoExample = new FacFormapagoserieExample();
					formapagoExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdseriefacturacionEqualTo(idSerie).andIdformapagoEqualTo(idFormaPago);

					facFormapagoserieExtendsMapper.deleteByExample(formapagoExample);
				}

				for (ComboItem item : formasPagos.getSeleccionados()) {
					Short idFormaPago = Short.parseShort(item.getValue());

					FacFormapagoserieExample formapagoExample = new FacFormapagoserieExample();
					formapagoExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdseriefacturacionEqualTo(idSerie).andIdformapagoEqualTo(idFormaPago);

					long size = facFormapagoserieExtendsMapper.countByExample(formapagoExample);

					if (size == 0) {
						formapagoserie = new FacFormapagoserie();
						formapagoserie.setUsumodificacion(idUsuario);
						formapagoserie.setFechamodificacion(new Date());
						formapagoserie.setIdinstitucion(Short.parseShort(idInstitucion));
						formapagoserie.setIdseriefacturacion(idSerie);
						formapagoserie.setIdformapago(idFormaPago);

						facFormapagoserieExtendsMapper.insert(formapagoserie);
					}
				}

			}
		}

		updateResponseDTO.setError(error);

		LOGGER.info("guardarFormasPagosSerie() -> Salida del servicio para guardar las formas de pago");

		return updateResponseDTO;
	}

	@Override
	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request) {
		ContadorSeriesDTO contadorSeriesDTO = new ContadorSeriesDTO();

		List<ContadorSeriesItem> contadorSeriesItems = null;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("getContadoresSerie() -> Entrada al servicio para recuperar los datos de los contadores");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getContadoresSerie() / admContadorMapper.selectByExample() -> Entrada a admContadorMapper para obtener los datos de los contadores");

					// Logica
					AdmContadorExample exampleContador = new AdmContadorExample();
					exampleContador.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdtablaEqualTo("FAC_FACTURA");
					exampleContador.setOrderByClause("NOMBRE");

					List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

					if (contadores != null) {
						contadorSeriesItems = new ArrayList<>();
						for (AdmContador admContador : contadores) {
							ContadorSeriesItem item = new ContadorSeriesItem();
							item.setIdContador(admContador.getIdcontador());
							item.setNombre(admContador.getNombre());
							item.setPrefijo(admContador.getPrefijo());
							item.setSufijo(admContador.getSufijo());
							item.setContador(String.valueOf(admContador.getContador()));

							contadorSeriesItems.add(item);
						}
					}

					LOGGER.debug(
							"getContadoresSerie() / admContadorMapper.selectByExample() -> Saliendo de admContadorMapper para obtener las formas de pago de la serie de facturación");

					contadorSeriesDTO.setContadorSeriesItems(contadorSeriesItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getContadoresSerie() -> Se ha producido un error al obtener las formas de pago de la serie de facturación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		contadorSeriesDTO.setError(error);

		LOGGER.info("getContadoresSerie() -> Salida del servicio para obtener los datos de los contadores");

		return contadorSeriesDTO;
	}

	@Override
	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request) {
		ContadorSeriesDTO contadorSeriesDTO = new ContadorSeriesDTO();

		List<ContadorSeriesItem> contadorSeriesItems = null;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info(
				"getContadoresRectificativasSerie() -> Entrada al servicio para recuperar los datos de los contadores de fact. rectificativas");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getContadoresRectificativasSerie() / admContadorMapper.selectByExample() -> Entrada a admContadorMapper para obtener los datos de los contadores rectificativas");

					// Logica
					AdmContadorExample exampleContador = new AdmContadorExample();
					exampleContador.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
							.andIdtablaEqualTo("FAC_ABONO").andIdcontadorNotEqualTo("FAC_ABONOS_FCS");
					exampleContador.setOrderByClause("NOMBRE");

					List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

					if (contadores != null) {
						contadorSeriesItems = new ArrayList<>();
						for (AdmContador admContador : contadores) {
							ContadorSeriesItem item = new ContadorSeriesItem();
							item.setIdContador(admContador.getIdcontador());
							item.setNombre(admContador.getNombre());
							item.setPrefijo(admContador.getPrefijo());
							item.setSufijo(admContador.getSufijo());
							item.setContador(String.valueOf(admContador.getContador()));

							contadorSeriesItems.add(item);
						}
					}

					LOGGER.debug(
							"getContadoresRectificativasSerie() / admContadorMapper.selectByExample() -> Saliendo de admContadorMapper para obtener los datos de los contadores de fact. rectificativas");

					contadorSeriesDTO.setContadorSeriesItems(contadorSeriesItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getContadoresRectificativasSerie() -> Se ha producido un error al obtener los datos de los contadores de fact. rectificativas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		contadorSeriesDTO.setError(error);

		LOGGER.info(
				"getContadoresRectificativasSerie() -> Salida del servicio para obtener los datos de los contadores rectificativas");

		return contadorSeriesDTO;
	}

	@Override
	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request) {
		FicherosAdeudosDTO ficherosAdeudosDTO = new FicherosAdeudosDTO();

		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosAdeudos() -> Entrada al servicio para obtener los ficheros de adeudos");

		// Conseguimos información del usuario logeado
		try {

			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"FacturacionPySServiceImpl.getFicherosAdeudos() -> obteniendo datos de ficheros de adeudos");

					List<FicherosAdeudosItem> items = facDisquetecargosExtendsMapper.getFicherosAdeudos(item,
							idInstitucion);

					ficherosAdeudosDTO.setFicherosAdeudosItems(items);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getFicherosAdeudos() -> Se ha producido un error al obtener los datos de ficheros de adeudos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		ficherosAdeudosDTO.setError(error);

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosAdeudos() -> Salida del servicio  para obtener los ficheros de adeudos");

		return ficherosAdeudosDTO;
	}

	// @Override
	@Transactional
	public CreateResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request) {
		CreateResponseDTO createResponseDTO = new CreateResponseDTO();

		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("guardarContadorSerie() -> Entrada al servicio para crear un nuevo contador");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					Integer idUsuario = usuarios.get(0).getIdusuario();
					String idSerieFacturacion = contador.getIdSerieFacturacion();
					LOGGER.info(
							"guardarContadorSerie() / admContadorMapper.insertSelective() -> Entrada a admContadorMapper para crear un nuevo contador");

					// Logica
					AdmContador nuevoContador = new AdmContador();
					nuevoContador.setNombre(contador.getNombre());
					nuevoContador.setDescripcion(contador.getNombre());
					nuevoContador.setPrefijo(contador.getPrefijo());
					nuevoContador.setContador(Long.parseLong(contador.getContador()));
					nuevoContador.setSufijo(contador.getSufijo());
					nuevoContador.setIdtabla("FAC_FACTURA");

					nuevoContador.setIdcontador("FAC_" + idSerieFacturacion + "_" + "1");

					nuevoContador.setIdcamposufijo("NUMEROFACTURA");
					nuevoContador.setIdcampoprefijo("NUMEROFACTURA");
					nuevoContador.setIdcampocontador("NUMEROFACTURA");

					nuevoContador.setIdinstitucion(Short.parseShort(idInstitucion));
					nuevoContador.setUsucreacion(idUsuario);
					nuevoContador.setFechacreacion(new Date());
					nuevoContador.setUsumodificacion(idUsuario);
					nuevoContador.setFechamodificacion(new Date());
					nuevoContador.setModificablecontador("1");

					admContadorMapper.insertSelective(nuevoContador);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.guardarContadorSerie() -> Se ha producido un error al crear un nuevo contador",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		createResponseDTO.setError(error);

		LOGGER.info("guardarContadorSerie() -> Salida del servicio para crear un nuevo contador");

		return createResponseDTO;
	}

	@Override
	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) {
		UsosSufijosDTO usosSufijosDTO = new UsosSufijosDTO();

		List<UsosSufijosItem> usosSufijosItems;
		Error error = new Error();
		String dni, idInstitucion;

		LOGGER.info("getUsosSufijos() -> Entrada al servicio para recuperar usos y sufijos");

		try {
			// Conseguimos información del usuario logeado
			HashMap<String, String> authentication = authenticationProvider.checkAuthentication(request);

			dni = authentication.get("dni");
			idInstitucion = authentication.get("idInstitucion");

			if (!dni.isEmpty() && !idInstitucion.isEmpty()) {
				List<AdmUsuarios> usuarios = authenticationProvider.getUsuarios(dni, idInstitucion);

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getUsosSufijos() / facSeriefacturacionExtendsMapper.getUsosSufijos() -> Entrada a facSeriefacturacionExtendsMapper para obtener los usos y sufijos");

					// Logica
					usosSufijosItems = facSeriefacturacionExtendsMapper.getUsosSufijos(Short.parseShort(idInstitucion),
							codBanco);
					usosSufijosDTO.setUsosSufijosItems(usosSufijosItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getUsosSufijos() -> Se ha producido un error al obtener los usos y sufijos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		usosSufijosDTO.setError(error);

		LOGGER.info("getUsosSufijos() -> Salida del servicio para obtener los usos y sufijos");

		return usosSufijosDTO;
	}
}
