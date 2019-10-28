package org.itcgae.siga.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesDeleteDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.cen.services.impl.TarjetaDatosIntegrantesServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenComponentes;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.ScsMateria;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.ScsTiposguardiasMapper;
import org.itcgae.siga.db.mappers.ScsTipoturnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegioprocuradorExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAreasMateriasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsMateriaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoTurnosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTiposGuardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboServiceImpl implements ComboService{
	private Logger LOGGER = Logger.getLogger(TarjetaDatosIntegrantesServiceImpl.class);
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
	
	@Autowired
	private ScsTipoTurnosExtendsMapper scsTipoTurnosExtendsMapper;
	
	@Autowired
	private ScsTiposGuardiasExtendsMapper scsTiposGuardiasExtendsMapper;
	
	@Autowired
	private ScsAreasMateriasExtendsMapper scsAreasMateriasExtendsMapper;
	
	@Autowired
	private ScsMateriaExtendsMapper scsMateriaExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	//PK
	
	public ComboDTO getComboEjemplo(HttpServletRequest request) {
		return null;
	
	}
	
	// ------------------------------------------------

	//Iván
	@Override
	public ComboDTO comboTurnos(HttpServletRequest request) {
		LOGGER.info("comboTurnos() -> Entrada al servicio para búsqueda de las turnos");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTurnos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTurnosExtendsMapper.comboTurnos(idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTurnos() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboAreas(HttpServletRequest request) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsAreasMateriasExtendsMapper.comboAreas(idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboTiposTurno(HttpServletRequest request,String idLenguaje) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				LOGGER.info(
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTipoTurnosExtendsMapper.comboTurnos(idLenguaje);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboMaterias(HttpServletRequest request, String idArea,String filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO materiasReturn = new ComboDTO();

		List<ComboItem> comboItems = scsMateriaExtendsMapper.comboMaterias(idInstitucion,idArea,filtro);

			materiasReturn.setCombooItems(comboItems);		

		return materiasReturn;
	}
	
	@Override
	public ComboDTO comboTiposGuardia(HttpServletRequest request,String idLenguaje) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				LOGGER.info(
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTiposGuardiasExtendsMapper.comboTiposGuardia(idLenguaje);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}
	
}
