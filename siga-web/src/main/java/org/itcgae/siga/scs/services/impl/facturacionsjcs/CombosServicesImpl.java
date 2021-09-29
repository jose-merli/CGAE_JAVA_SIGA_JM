package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsEstadosFacturacionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsHitoGeneralExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagosjgExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICombosServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombosServicesImpl implements ICombosServices {

	private Logger LOGGER = Logger.getLogger(CombosServicesImpl.class);

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
		
		LOGGER.info("comboFactColegio() -> Entrada del servicio para obtener el listado de facturaciones cerradas del colegio");
		
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

		LOGGER.info("comboFactColegio() -> Salida del servicio para obtener el listado de facturaciones cerradas del colegio");
		return comboEstadosFact;
	}

	@Override
	public ComboDTO comboPagosColegio(HttpServletRequest request) {
		
		LOGGER.info("comboPagosColegio() -> Entrada del servicio para obtener el listado de facturaciones cerradas del colegio");
		
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
				
				List<ComboItem> comboItems = fcsPagosjgExtendsMapper.comboPagosColegio(usuario.getIdlenguaje(), idInstitucion);
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

		LOGGER.info("comboPagosColegio() -> Salida del servicio para obtener el listado de facturaciones cerradas del colegio");
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
			
			LOGGER.info("comboPagoEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info("comboPagoEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info("comboPagoEstados() / FcsEstadosFacturacionExtendsMapper.estadosPagos() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener los estados de los pagos");
				
				List<ComboItem> comboItems = fcsEstadosFacturacionExtendsMapper.estadosPagos(idLenguaje);
				comboEstadosFact.setCombooItems(comboItems);
				
				LOGGER.info("comboPagoEstados() / FcsEstadosFacturacionExtendsMapper.estadosPagos() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener los estados de los pagos");
			} else {
				LOGGER.warn("comboPagoEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboPagoEstados() -> idInstitucion del token nula");
		}
		
		LOGGER.info("comboPagoEstados() -> Salida del servicio para obtener los estados de los pagos");
		
		return comboEstadosFact;
	}

	@Override
	public ComboDTO comboColegiosProcuradores(HttpServletRequest request) {

		LOGGER.info("comboColegiosProcuradores() -> Entrada del servicio para obtener el listado de colegios de un procurador");

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

				List<ComboItem> comboItems = fcsPagosjgExtendsMapper.comboPagosColegio(usuario.getIdlenguaje(), idInstitucion);
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

		LOGGER.info("comboColegiosProcuradores() -> Salida del servicio para obtener el listado de colegios de un procurador");
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
			
			LOGGER.info("comboFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info("comboFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info("comboFacturaciones() / FcsEstadosFacturacionExtendsMapper.estadosPagos() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");
				
				List<ComboItem> comboItems = fcsFacturacionJGExtendsMapper.comboFacturaciones(idInstitucion.toString());
				comboFact.setCombooItems(comboItems);
				
				LOGGER.info("comboFacturaciones() / FcsEstadosFacturacionExtendsMapper.comboFacturaciones() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener las facturaciones");
			} else {
				LOGGER.warn("comboFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("comboPagoEstados() -> idInstitucion del token nula");
		}
		
		LOGGER.info("comboPagoEstados() -> Salida del servicio para obtener los estados de los pagos");
		
		return comboFact;
	}
}