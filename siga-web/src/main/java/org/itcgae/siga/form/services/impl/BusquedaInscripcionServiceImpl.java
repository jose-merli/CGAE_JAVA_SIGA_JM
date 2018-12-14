package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.form.InscripcionDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadoinscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.form.services.IBusquedaInscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaInscripcionServiceImpl implements IBusquedaInscripcionService{
	
	private Logger LOGGER = Logger.getLogger(BusquedaInscripcionServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ForEstadoinscripcionExtendsMapper forEstadoInscripcionExtendsMapper;
	
	@Autowired
	private ForInscripcionExtendsMapper forInscripcionExtendsMapper;
	
	@Autowired
	AdmUsuariosMapper admUsuariosMapper;
	
	@Override
	public ComboDTO getEstadosInscripcion(HttpServletRequest request) {
		
		LOGGER.info("getEstadosInscripcion() -> Entrada al servicio para obtener los estados de una inscripcion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadosInscripcion() / forEstadocursolExtendsMapper.distinctEstadoCurso -> Entrada a forEstadocursolExtendsMapper para obtener los diferentes estados de un curso");
				comboItems = forEstadoInscripcionExtendsMapper.distinctEstadoInscripcion(usuario.getIdlenguaje());
				LOGGER.info(
						"getEstadosInscripcion() / forEstadoInscripcionExtendsMapper.distinctEstadoInscripcion -> Salida de forEstadoInscripcionExtendsMapper para obtener los diferentes estados de una inscripcion");
				
				//TODO Quitar en un futuro
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getEstadosInscripcion() -> Salida del servicio para obtener los diferentes estados de una inscripcion");
		
		return comboDTO;
	}

	@Override
	public InscripcionDTO searchInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request) {

		LOGGER.info("searchInscripcion() -> Entrada al servicio para obtener inscripciones");

		InscripcionDTO inscripcionDTO = new InscripcionDTO();
		List<InscripcionItem> inscripcionItemList = new ArrayList<InscripcionItem>();
		
		inscripcionItemList = forInscripcionExtendsMapper.selectInscripciones(inscripcionItem);
		inscripcionDTO.setInscripcionItem(inscripcionItemList);

		//TODO comprobar caso de null
		if (inscripcionItemList.isEmpty()) {
			LOGGER.warn(
					"searchInscripcion() / forInscripcionExtendsMapper.selectInscripciones() -> No existen inscripciones para el filtro introducido");
		}

		return inscripcionDTO;
	}
	
	@Override
	public ComboDTO getCalificacionesEmitidas(HttpServletRequest request) {
		
		LOGGER.info("getCalificacionesEmitidas() -> Entrada al servicio para obtener las calificaciones emitidas");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCalificacionesEmitidas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCalificacionesEmitidas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCalificacionesEmitidas() / forEstadocursolExtendsMapper.getCalificacionesEmitidas -> Entrada a forEstadocursolExtendsMapper para obtener las diferentes calificaciones");
				comboItems = forInscripcionExtendsMapper.getCalificacionesEmitidas(usuario.getIdlenguaje());
				LOGGER.info(
						"getCalificacionesEmitidas() / forEstadoInscripcionExtendsMapper.getCalificacionesEmitidas -> Salida de forEstadoInscripcionExtendsMapper para obtener las diferentes calificaciones");
				
				//TODO Quitar en un futuro
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCalificacionesEmitidas() -> Salida del servicio para obtener los diferentes estados de una inscripcion");
		
		return comboDTO;
	}

}
