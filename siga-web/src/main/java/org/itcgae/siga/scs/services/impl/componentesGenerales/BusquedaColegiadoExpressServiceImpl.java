package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTOs.scs.ColegiadoJGItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.IBusquedaColegiadosExpressService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaColegiadoExpressServiceImpl implements IBusquedaColegiadosExpressService{

	private Logger LOGGER = Logger.getLogger(BusquedaColegiadoExpressServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosextendsMapper;
	
	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	public ColegiadoJGDTO busquedaColegiadosExpress(String colegiadoJGItem, HttpServletRequest request) {
		
		LOGGER.info("busquedaColegiadosExpress() -> Entrada al servicio para obtener el colegiado");
		
		ColegiadoJGDTO colegiadoJGDTO = new ColegiadoJGDTO();
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logueado
				usuario.setIdinstitucion(idInstitucion);
				
				LOGGER.info("busquedaColegiadosExpress() / cenPersonaExtendsMapper.busquedaColegiadoExpress() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado");
				colegiadoJGDTO.setColegiadoJGItem(cenPersonaExtendsMapper.busquedaColegiadoExpress(colegiadoJGItem, idInstitucion.toString()));
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
			}else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return colegiadoJGDTO;
	}
	
	@Override
	public ColegiadosSJCSDTO busquedaColegiadoEJG(ColegiadosSJCSItem datos, HttpServletRequest request) {
		ColegiadosSJCSDTO responsedto = new ColegiadosSJCSDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Error error = new Error();

		if (idInstitucion != null) {
			LOGGER.debug(
					"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Entrada para obtener los datos del colegiado segun los filtros");

				try {
					responsedto.setColegiadosSJCSItem(
							scsEjgExtendsMapper.busquedaColegiadoEJG(datos, usuarios.get(0).getIdlenguaje()));
				} catch (Exception e) {
					LOGGER.error(
							"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Se ha producido un error al tratar de obtener los datos del colegiado. ",
							e);

					error.setCode(500);
					error.setDescription("Error al obtener los datos.");
					error.setMessage(e.getMessage());

					responsedto.setError(error);
				}
			}
		}
		return responsedto;
	}

	@Override
	public ComboDTO comboTurnos(String pantalla, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTurnosTipo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTurnosTipo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"BusquedaEJGServiceImpl.comboTurnos() -> Entrada al servicio para obtener los turnos. Viene desde "
								+ pantalla);

				if (pantalla != null && !pantalla.isEmpty()) {
					comboItems = scsTurnosextendsMapper.comboTurnosBusqueda(idInstitucion, pantalla);
				}

				LOGGER.info(
						"BusquedaEJGServiceImpl.comboTurnos()-> Salida del servicio para obtener los datos del combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("cBusquedaEJGServiceImpl.comboTurnos() -> Salida del servicio para obtener los turnos");
		return comboDTO;
	}
}
