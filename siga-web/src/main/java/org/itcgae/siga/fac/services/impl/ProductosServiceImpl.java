package org.itcgae.siga.fac.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductosServiceImpl implements IProductosService{
	private Logger LOGGER = Logger.getLogger(ProductosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private PySTipoIvaExtendsMapper pysTipoIvaExtendsMapper;
	
	@Autowired
	private PySTipoFormaPagoExtendsMapper pysTipoFormaPagoExtendsMapper;
	
	
	@Override
	public ComboDTO comboIva(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboIva() -> Entrada al servicio para recuperar el combo de ivas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboIva() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboIva() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboIva() / pysTipoIvaExtendsMapper.comboIva() -> Entrada a pysTipoIvaExtendsMapper para recuperar el combo de ivas");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboIva = pysTipoIvaExtendsMapper
							.comboIva(idioma);

					LOGGER.info(
							"comboIva() / pysTipoIvaExtendsMapper.comboIva() -> Salida de pysTipoIvaExtendsMapper para recuperar el combo de ivas");

					if (listaComboIva != null && listaComboIva.size() > 0) {
						comboDTO.setCombooItems(listaComboIva);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboIva() -> Se ha producido un error al recuperar el combo de ivas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboIva() -> Salida del servicio para recuperar el combo de ivas");

		return comboDTO;
	}
	
	@Override
	public ComboDTO comboTipoFormaPago(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboTipoFormaPago() -> Entrada al servicio para recuperar el combo de formas de pago");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboTipoFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboTipoFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboTipoFormaPago() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPago() -> Entrada a pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboFormaPago = pysTipoFormaPagoExtendsMapper
							.comboTipoFormaPago(idioma);

					LOGGER.info(
							"comboTipoFormaPago() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPago() -> Salida de pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago");

					if (listaComboFormaPago != null && listaComboFormaPago.size() > 0) {
						comboDTO.setCombooItems(listaComboFormaPago);
				}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboTipoFormaPago() -> Se ha producido un error al recuperar el combo de formas de pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboTipoFormaPago() -> Salida del servicio para recuperar el combo de formas de pago");

		return comboDTO;
	}
}
