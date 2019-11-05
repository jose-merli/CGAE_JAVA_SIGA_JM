package org.itcgae.siga.scs.services.impl.justiciables;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsMinusvaliaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProfesionExtendsMapper;
import org.itcgae.siga.scs.services.justiciables.IGestionJusticiableService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionJusticiableServiceImpl implements IGestionJusticiableService {

	private Logger LOGGER = Logger.getLogger(GestionJusticiableServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsMinusvaliaExtendsMapper scsMinusvaliaExtendsMapper;

	@Autowired
	private ScsProfesionExtendsMapper scsProfesionExtendsMapper;


	@Override
	public ComboDTO getMinusvalias(HttpServletRequest request) {
		LOGGER.info("getMinusvalia() -> Entrada al servicio para obtener combo minusvalias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getMinusvalia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getMinusvalia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getMinusvalia() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Entrada a scsMinusvaliaExtendsMapper para obtener minusvalias");

				List<ComboItem> comboItems = scsMinusvaliaExtendsMapper.getMinusvalias(idInstitucion);

				LOGGER.info(
						"getMinusvalia() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Salida a scsMinusvaliaExtendsMapper para obtener minusvalias");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getMinusvalia() -> Salida del servicio para obtener combo minusvalias");
		return combo;
	}



	@Override
	public ComboDTO getProfesiones(HttpServletRequest request) {
		LOGGER.info("getProfesiones() -> Entrada al servicio para obtener combo profesiones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getProfesiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getProfesiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				
				LOGGER.info(
						"getProfesiones() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Entrada a scsMinusvaliaExtendsMapper para obtener minusvalias");

				List<ComboItem> comboItems = scsProfesionExtendsMapper.getProfesiones(usuario.getIdlenguaje());

				LOGGER.info(
						"getProfesiones() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Salida a scsMinusvaliaExtendsMapper para obtener minusvalias");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getProfesiones() -> Salida del servicio para obtener combo profesiones");
		return combo;
	}

	

}