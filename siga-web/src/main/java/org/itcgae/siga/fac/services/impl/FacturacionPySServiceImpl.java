package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenCuentasbancariasMapper;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionBancoMapper;
import org.itcgae.siga.db.mappers.FacSufijoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.*;
import org.itcgae.siga.db.services.form.mappers.PysFormapagoExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.springframework.transaction.annotation.Transactional;

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
    private FacFacturaMapper facFacturaMapper;

    @Autowired
    private CenCuentasbancariasMapper cenCuentasbancarias;

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
    private FacSeriefacturacionBancoMapper facSeriefacturacionBancoMapper;

    @Autowired
    private FacTipocliincluidoenseriefacExtendsMapper facTipocliincluidoenseriefacExtendsMapper;

    @Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;

    @Autowired
    private EnvPlantillaEnviosExtendsMapper envPlantillaEnviosExtendsMapper;

    @Autowired
    private PysFormapagoExtendsMapper pysFormapagoExtendsMapper;

    @Autowired
	private FacFormapagoserieExtendsMapper facFormapagoserieExtendsMapper;

    @Override
    public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request) {

        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();

        LOGGER.info("borrarCuentasBancarias() -> Entrada al servicio para dar de baja las cuentas bancarias");

        // Conseguimos información del usuario logeado
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

                LOGGER.info(
                        "FacturacionPySServiceImpl.borrarCuentasBancarias() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "borrarCuentasBancarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && !usuarios.isEmpty()) {
                    LOGGER.info(
                            "borrarCuentasBancarias() / facBancoInstitucionExtendsMapper.getCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para establecer la fecha de baja");

                    //Logica
                    for (CuentasBancariasItem cuenta : cuentasBancarias) {

                        CenCuentasbancariasExample cuentasbancariasExample = new CenCuentasbancariasExample();
                        cuentasbancariasExample.createCriteria().andIdcuentaEqualTo(cuenta.getId()).andIdinstitucionEqualTo(idInstitucion);

                        LOGGER.info("\n\nTratamiento de la cuenta con IBAN: " + cuenta.getIBAN() + "\n\n");

                        // *No hace falta* int usoCuenta = facSeriefacturacionExtendsMapper.getUsoSufijo(idInstitucion, cuenta.getCodBanco());

                        if (Integer.parseInt(cuenta.getNumUsos()) < 1) {
                            this.cenCuentasbancarias.deleteByExample(cuentasbancariasExample);
                        } else {
                            CenCuentasbancarias cuentaCambio = this.cenCuentasbancarias.selectByExample(cuentasbancariasExample).get(0);
                            cuentaCambio.setFechabaja(new Date());
                            this.cenCuentasbancarias.updateByExample(cuentaCambio, cuentasbancariasExample);
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

						for (SerieFacturacionItem serieItem : serieFacturacionItems) {
							String idSerieFacturacion = serieItem.getIdSerieFacturacion();

							LOGGER.info("getSeriesFacturacion() -> Obteniendo los tipos de servicios para idInstitucion=" + idInstitucion + ", idSerieFacturacion=" + idSerieFacturacion);
							List<ComboItem> tiposServicios = facFacturacionsuscripcionExtendsMapper.getTiposServicios(idSerieFacturacion, idInstitucion, idioma);
							LOGGER.info("getSeriesFacturacion() -> Obteniendo los tipos de productos para idInstitucion=" + idInstitucion + ", idSerieFacturacion=" + idSerieFacturacion);
							List<ComboItem> tiposProductos = pysCompraExtendsMapper.getTiposProductos(idSerieFacturacion, idInstitucion, idioma);

							List<String> tiposIncluidos = Stream.concat(tiposServicios.stream(), tiposProductos.stream())
									.map(t -> t.getLabel())
									.collect(Collectors.toList());

							serieItem.setTiposIncluidos(tiposIncluidos);
							serieItem.setTiposServicios(tiposServicios);
							serieItem.setTiposProductos(tiposProductos);
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
	@Transactional
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

						List<FacSeriefacturacion> sfResults = facSeriefacturacionExtendsMapper.selectByExample(seriefacturacionExample);
						if (null != sfResults && !sfResults.isEmpty()) {
							FacSeriefacturacion sfToUpdate = sfResults.get(0);

							if (sfToUpdate.getFechabaja() == null) {
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
							} else {
								LOGGER.warn("eliminaSerieFacturacion() -> Ya se encontraba eliminada la serie de facturación con id=" + serieFacturacion.getIdSerieFacturacion());
							}
						} else {
							LOGGER.warn("eliminaSerieFacturacion() -> No existe serie facturación con id=" + serieFacturacion.getIdSerieFacturacion());

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
	@Transactional
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
							if (sfToUpdate.getFechabaja() != null) {
								sfToUpdate.setFechabaja(null);
								facSeriefacturacionExtendsMapper.updateByExample(sfToUpdate, seriefacturacionExample);
							} else {
								LOGGER.warn("reactivarSerieFacturacion() -> Ya se encontraba activa la serie de facturación con id=" + serieFacturacion.getIdSerieFacturacion());
							}
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

	@Override
	public ComboDTO comboPlanificacion(String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboPlanificacion() -> Entrada al servicio para recuperar el combo de contadores rectificativas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null && idSerieFacturacion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.comboPlanificacion() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboPlanificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboPlanificacion() / fac.comboPlanificacion() -> Entrada a facSeriefacturacionExtendsMapper para obtener el combo de planificación");

					//Logica
					FacSeriefacturacionExample exampleSerieFacturacion = new FacSeriefacturacionExample();
					exampleSerieFacturacion.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdseriefacturacionNotEqualTo(Long.valueOf(idSerieFacturacion))
							.andFechabajaIsNull();
					exampleSerieFacturacion.setOrderByClause("descripcion");

					List<FacSeriefacturacion> seriesFacturacion = facSeriefacturacionExtendsMapper.selectByExample(exampleSerieFacturacion);

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
					"FacturacionPySServiceImpl.comboPlanificacion() -> Se ha producido un error al obtener el combo de planificación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboPlanificacion() -> Salida del servicio para obtener el combo de planificación");

		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion, HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int response = 0;
		Error error = new Error();

		LOGGER.info("guardarSerieFacturacion() -> Entrada al servicio para guardar una serie de facturación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"FacturacionPySServiceImpl.guardarSerieFacturacion() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarSerieFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				Integer idUsuario = usuarios.get(0).getIdusuario();
				LOGGER.info(
						"comboPlanificacion() / facSeriefacturacionExtendsMapper.selectByExample() -> Entrada a facSeriefacturacionExtendsMapper para obtener la serie de facturación");

				//Logica

				// 1. Actualizar FAC_SERIEFACTURACION
				Long idSerieFacturacion = Long.parseLong(serieFacturacion.getIdSerieFacturacion());
				
				FacSeriefacturacionKey serieKey = new FacSeriefacturacionKey();
				serieKey.setIdinstitucion(idInstitucion);
				serieKey.setIdseriefacturacion(idSerieFacturacion);
				
				FacSeriefacturacion serieToUpdate = facSeriefacturacionExtendsMapper.selectByPrimaryKey(serieKey);
				
				if (serieToUpdate != null) {

					if (serieFacturacion.getAbreviatura() != null
							&& !serieFacturacion.getAbreviatura().trim().isEmpty()
							&& serieFacturacion.getAbreviatura().length() <= 20)
						serieToUpdate.setNombreabreviado(serieFacturacion.getAbreviatura());

					if (serieFacturacion.getDescripcion() != null
							&& !serieFacturacion.getDescripcion().trim().isEmpty()
							&& serieFacturacion.getDescripcion().length() <= 100)
						serieToUpdate.setDescripcion(serieFacturacion.getDescripcion());

					if (serieFacturacion.getIdSerieFacturacionPrevia() != null && !serieFacturacion.getIdSerieFacturacionPrevia().trim().isEmpty())
						serieToUpdate.setIdseriefacturacionprevia(Long.parseLong(serieFacturacion.getIdSerieFacturacionPrevia()));
					else
						serieToUpdate.setIdseriefacturacionprevia(null);

					if (serieFacturacion.getObservaciones() == null
							|| (serieFacturacion.getObservaciones() != null
								&& serieFacturacion.getObservaciones().length() <= 4000))
					serieToUpdate.setObservaciones(serieFacturacion.getObservaciones());

					if (serieFacturacion.getSerieGenerica()) {
						serieToUpdate.setTiposerie("G");
					} else {
						serieToUpdate.setTiposerie(null);
					}

					serieToUpdate.setUsumodificacion(idUsuario);
					serieToUpdate.setFechamodificacion(new Date());
					response = facSeriefacturacionExtendsMapper.updateByPrimaryKeySelective(serieToUpdate);

					// 2. Actualizar FAC_SERIEFACTURACION_BANCO
					FacSeriefacturacionBancoExample bancoExample = new FacSeriefacturacionBancoExample();
					bancoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdseriefacturacionEqualTo(idSerieFacturacion);
					List<FacSeriefacturacionBanco> serieBancoItems = facSeriefacturacionBancoMapper.selectByExample(bancoExample);
					boolean isNewBanco = serieBancoItems == null && serieBancoItems.isEmpty();
					
					FacSeriefacturacionBanco serieBancoToUpdate = new FacSeriefacturacionBanco();
					if (isNewBanco) {
						serieBancoToUpdate.setIdinstitucion(idInstitucion);
						serieBancoToUpdate.setIdseriefacturacion(idSerieFacturacion);
					} else {
						serieBancoToUpdate = serieBancoItems.get(0);
					}
					
					serieBancoToUpdate.setBancosCodigo(serieFacturacion.getIdCuentaBancaria());
					
					if (serieFacturacion.getIdSufijo() != null && !serieFacturacion.getIdSufijo().trim().isEmpty())
						serieBancoToUpdate.setIdsufijo(Short.parseShort(serieFacturacion.getIdSufijo()));
					else
						serieBancoToUpdate.setIdsufijo(null);
							
					serieBancoToUpdate.setUsumodificacion(idUsuario);
					serieBancoToUpdate.setFechamodificacion(new Date());
				
					if (!isNewBanco) {
						response = facSeriefacturacionBancoMapper.updateByExample(serieBancoToUpdate, bancoExample);
					} else {
						response = facSeriefacturacionBancoMapper.insert(serieBancoToUpdate);
					}

					// 3. Actualizar tipos de productos
				


					// 4. Actualizar tipos de servicios

					
				}

			}
		}

		updateResponseDTO.setError(error);

		LOGGER.info("guardarSerieFacturacion() -> Salida del servicio para guardar la serie de facturación");

		return updateResponseDTO;
	}

	@Override
	public ComboDTO getEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("getEtiquetasSerie() -> Entrada al servicio para recuperar las etiquetas de la serie de facturación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null && idSerieFacturacion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"FacturacionPySServiceImpl.getEtiquetasSerie() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getEtiquetasSerie() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getEtiquetasSerie() / facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie() -> Entrada a facTipocliincluidoenseriefacExtendsMapper para obtener las etiquetas de la serie");

					//Logica
					String idioma = usuarios.get(0).getIdlenguaje();
					comboItems = facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie(idSerieFacturacion, idInstitucion, idioma);

					LOGGER.info(
							"getEtiquetasSerie() / facTipocliincluidoenseriefacExtendsMapper.getEtiquetasSerie() -> Saliendo de facTipocliincluidoenseriefacExtendsMapper para obtener las etiquetas de la serie");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getEtiquetasSerie() -> Se ha producido un error al obtener las etiquetas de la serie",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("getEtiquetasSerie() -> Salida del servicio para obtener las etiquetas de la serie");

		return comboDTO;
	}

	@Override
	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request) {
		DestinatariosSeriesDTO destinatariosSeriesDTO = new DestinatariosSeriesDTO();

		List<DestinatariosSeriesItem> destinatariosSeriesItems;
		Error error = new Error();

		LOGGER.info("getDestinatariosSeries() -> Entrada al servicio para recuperar los destinatarios de la serie de facturación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null && idSerieFacturacion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.debug(
						"FacturacionPySServiceImpl.getDestinatariosSeries() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.debug(
						"getDestinatariosSeries() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getDestinatariosSeries() / cenPersonaExtendsMapper.getDestinatariosSeries() -> Entrada a facTipocliincluidoenseriefacExtendsMapper para obtener los destinatarios de la serie");

					//Logica
					destinatariosSeriesItems = cenPersonaExtendsMapper.getDestinatariosSeries(idInstitucion, idSerieFacturacion);

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

		LOGGER.info("getDestinatariosSeries() -> Salida del servicio para obtener los destinatarios de la serie de facturación");

		return destinatariosSeriesDTO;
	}

	@Override
	public ComboDTO comboPlantillasEnvio(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("comboPlantillasEnvio() -> Entrada al servicio para recuperar lel combo de platillas envio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.debug(
						"FacturacionPySServiceImpl.getEtiquetasSerie() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.debug(
						"comboPlantillasEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboPlantillasEnvio() / envPlantillaEnviosExtendsMapper.comboPlantillasEnvio() -> Entrada a envPlantillaEnviosExtendsMapper para obtener el combo de platillas envio");

					//Logica
					comboItems = envPlantillaEnviosExtendsMapper.comboPlantillasEnvio(idInstitucion);

					LOGGER.debug(
							"comboPlantillasEnvio() / envPlantillaEnviosExtendsMapper.comboPlantillasEnvio() -> Saliendo de envPlantillaEnviosExtendsMapper para obtener el combo de platillas envio");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.comboPlantillasEnvio() -> Se ha producido un error al obtener el combo de platillas envio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboPlantillasEnvio() -> Salida del servicio para obtener el combo de platillas envio");

		return comboDTO;
	}

	@Override
	public ComboDTO getFormasPagosDisponiblesSeries(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("getFormasPagosDisponiblesSeries() -> Entrada al servicio para recuperar todas las formas de pago");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.debug(
						"FacturacionPySServiceImpl.getFormasPagosDisponiblesSeries() -> Entrada a admUsuariosExtendsMapper para obtener todas las formas de pago");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.debug(
						"comboPlantillasEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"comboPlantillasEnvio() / pysFormapagoExtendsMapper.getWayToPayWithIdFormapago() -> Entrada a pysFormapagoExtendsMapper para obtener todas las formas de pago");

					//Logica
					String idioma = usuarios.get(0).getIdlenguaje();
					comboItems = pysFormapagoExtendsMapper.getWayToPayWithIdFormapago(idioma);

					LOGGER.debug(
							"comboPlantillasEnvio() / pysFormapagoExtendsMapper.getWayToPayWithIdFormapago() -> Saliendo de pysFormapagoExtendsMapper para obtener todas las formas de pago");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getFormasPagosDisponiblesSeries() -> Se ha producido un error al obtener todas las formas de pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("getFormasPagosDisponiblesSeries() -> Salida del servicio para obtener todas las formas de pago");

		return comboDTO;
	}

	@Override
	public ComboDTO getFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();

		List<ComboItem> comboItems;
		Error error = new Error();

		LOGGER.info("getFormasPagosSerie() -> Entrada al servicio para recuperar las formas de pago de la serie de facturación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.debug(
						"FacturacionPySServiceImpl.getFormasPagosSerie() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.debug(
						"getFormasPagosSerie() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.debug(
							"getFormasPagosSerie() / facFormapagoserieExtendsMapper.getFormasPagosSerie() -> Entrada a facFormapagoserieExtendsMapper para obtener las formas de pago de la serie de facturación");

					//Logica
					String idioma = usuarios.get(0).getIdlenguaje();
					comboItems = facFormapagoserieExtendsMapper.getFormasPagosSerie(idSerieFacturacion, idInstitucion, idioma);

					LOGGER.debug(
							"getFormasPagosSerie() / facFormapagoserieExtendsMapper.getFormasPagosSerie() -> Saliendo de facFormapagoserieExtendsMapper para obtener las formas de pago de la serie de facturación");

					comboDTO.setCombooItems(comboItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.getFormasPagosSerie() -> Se ha producido un error al obtener las formas de pago de la serie de facturación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("getFormasPagosSerie() -> Salida del servicio para obtener las formas de pago de la serie de facturación");

		return comboDTO;
	}

}
