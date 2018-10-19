package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadocursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForVisibilidadcursoExtendsMapper;
import org.itcgae.siga.form.services.IBusquedaCursosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaCursosServiceImpl implements IBusquedaCursosService{
	
	private Logger LOGGER = Logger.getLogger(BusquedaCursosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ForEstadocursoExtendsMapper forEstadocursoExtendsMapper;
	
	@Autowired
	private ForVisibilidadcursoExtendsMapper forVisibilidadcursoExtendsMapper;
	
	@Autowired
	private ForTemacursoExtendsMapper forTemacursoExtendsMapper;
	
	@Autowired
	private ForCursoExtendsMapper forCursoExtendsMapper;
	
	@Override
	public ComboDTO getVisibilidadCursos(HttpServletRequest request) {
		
		LOGGER.info("getVisibilidadCursos() -> Entrada al servicio para obtener la visibilidad de los cursos");
		
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
					"getVisibilidadCursos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getVisibilidadCursos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getVisibilidadCursos() / forVisibilidadcursolExtendsMapper.distinctVisibilidadCurso() -> Entrada a forVisibilidadcursolExtendsMapper para obtener la visibilidad de los cursos");
				comboItems = forVisibilidadcursoExtendsMapper.distinctVisibilidadCurso(usuario.getIdlenguaje());
				LOGGER.info(
						"getVisibilidadCursos() / forVisibilidadcursolExtendsMapper.distinctVisibilidadCurso() -> Salida de forVisibilidadcursolExtendsMapper para obtener la visibilidad de los cursos");
				
				//Quitar en un futuro
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getVisibilidadCursos() -> Salida del servicio para obtener la visibilidad de los cursos");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getEstadosCursos(HttpServletRequest request) {
		
		LOGGER.info("getEstadosCursos() -> Entrada al servicio para obtener los tipos situacion de un colegiado");
		
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
					"getEstadosCursos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadosCursos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadosCursos() / forEstadocursolExtendsMapper.distinctEstadoCurso -> Entrada a forEstadocursolExtendsMapper para obtener los diferentes estados de un curso");
				comboItems = forEstadocursoExtendsMapper.distinctEstadoCurso(usuario.getIdlenguaje());
				LOGGER.info(
						"getEstadosCursos() / forEstadocursolExtendsMapper.distinctEstadoCurso -> Salida de forEstadocursolExtendsMapper para obtener los diferentes estados de un curso");
				
				//Quitar en un futuro
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getEstadosCursos() -> Salida del servicio para obtener los diferentes estados de un curso");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getTemasCursos(HttpServletRequest request) {
		
		LOGGER.info("getTemasCursos() -> Entrada al servicio para obtener los diferentes temas para un curso");
		
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
					"getTemasCursos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTemasCursos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTemasCursos() / forTemacursoExtendsMapper.distinctTemaCurso() -> Entrada a forTemacursoExtendsMapper para obtener los diferentes temas para un curso");
				comboItems = forTemacursoExtendsMapper.distinctTemaCurso(usuario.getIdlenguaje());
				LOGGER.info(
						"getTemasCursos() / forTemacursoExtendsMapper.distinctTemaCurso() -> Salida de forTemacursoExtendsMapper para obtener los diferentes temas para un curso");
				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getTemasCursos() -> Salida del servicio para obtener los temas para un curso");
		
		return comboDTO;
	}
	
	@Override
	public CursoDTO searchCurso(CursoItem cursoItem, HttpServletRequest request) {

		LOGGER.info("searchCurso() -> Entrada al servicio para obtener cursos");

		CursoDTO cursoDTO = new CursoDTO();
		List<CursoItem> cursoItemList = new ArrayList<CursoItem>();
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			cursoItemList = forCursoExtendsMapper.selectCursos(idInstitucion, cursoItem);
			cursoDTO.setCursoItem(cursoItemList);
	
			if (cursoItemList == null || cursoItemList.size() == 0) {
	
				LOGGER.warn(
						"searchCurso() / forCursoExtendsMapper.selectCursos() -> No existen cursos para el filtro introducido");
			}
		}

		return cursoDTO;
	}
	
	
}
