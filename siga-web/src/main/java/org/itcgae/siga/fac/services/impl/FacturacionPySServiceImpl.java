package org.itcgae.siga.fac.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenGruposclienteExample;
import org.itcgae.siga.db.entities.FacSufijo;
import org.itcgae.siga.db.entities.FacSufijoExample;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.mappers.FacSufijoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
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
					
					//comprobar primero si la lista de cuentas bancarias viene vacia
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

		LOGGER.info("comboEtiquetas() -> Entrada al servicio para recuperar el combo de destinatarios");

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
							"comboEtiquetas() / facBancoInstitucionExtendsMapper.comboEtiquetas() -> Entrada a facBancoInstitucionExtendsMapper para obtener el combo de destinatarios");
					
					//Logica
					comboItems = cenGruposclienteClienteExtendsMapper.comboDestinatarios(idInstitucion);
					
					//comprobar primero si la lista de cuentas bancarias viene vacia
					comboDTO.setCombooItems(comboItems);
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"FacturacionPySServiceImpl.comboEtiquetas() -> Se ha producido un error al obtener el combo de destinatarios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboEtiquetas() -> Salida del servicio para obtener el combo de destinatarios");
		
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
					exampleContador.or()
						.andIdinstitucionEqualTo(idInstitucion).andIdtablaNotEqualTo("FAC_ABONO");
					exampleContador.or()
						.andIdcontadorEqualTo("FAC_ABONOS_FCS");
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
					"FacturacionPySServiceImpl.comboEtiquetas() -> Se ha producido un error al obtener el combo de contadores",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboEtiquetas() -> Salida del servicio para obtener el combo de contadores");
		
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
	
}
