package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForCursoExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadocursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForVisibilidadcursoExtendsMapper;
import org.itcgae.siga.form.services.IBusquedaCursosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.itcgae.siga.commons.constants.SigaConstants;

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
	
	@Autowired
	AdmUsuariosMapper admUsuariosMapper;
	
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

	@Override
	public int archivarCursos(List<CursoItem> listCursoItem, HttpServletRequest request) {
		
		LOGGER.info("archivarCursos() -> Entrada al servicio para archivar cursos");
		
		List<Long> arrayIds = new ArrayList<>();
		int resultado = 0;
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		String idInstitucionToString =  String.valueOf(idInstitucion);
		
		//Para obtener los ids de los cursos a actualizar
		for(CursoItem curso : listCursoItem) {
			
			//Añadimos a la lista de ids únicamente los ids de los cursos que pueden archivarse
			if(idInstitucionToString.equals(curso.getIdInstitucion()) && (SigaConstants.CURSO_SIN_ARCHIVAR == curso.getFlagArchivado()) && (SigaConstants.ESTADO_CURSO_CANCELADO.equals(curso.getIdEstado()) || SigaConstants.ESTADO_CURSO_FINALIZADO.equals(curso.getIdEstado()) )) {
				arrayIds.add(curso.getIdCurso());
			}
		}
		
		//Si no hay curso que cumpla las condiciones para archivar devolvemos 0
		if(arrayIds.isEmpty()) {
			return 0;
		}else {
			//Entidad que se va a rellenar con los valores a actualizar
			ForCurso record = new ForCurso();
			
			record.setFlagarchivado((short)1); // estado archivado
			
			//Obtenemos el usuario para setear el campo "usumodificiacion"
			String dniUser = UserTokenUtils.getDniFromJWTToken(token);
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios .createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);
			
			LOGGER.info(
					"archivarCursos() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
			List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
			
			AdmUsuarios usuario = usuarios.get(0);
			
			if(usuario == null) {
				LOGGER.warn(
						"archivarCursos() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
				return 0; //Devolvemos 0 cursos archivados porque no se va a poder realizar el update al no haber recuperado al usuario
			}else {
				record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
			}
			
			record.setFechamodificacion(new Date()); // seteamos la fecha de modificación
			
			ForCursoExample example = new ForCursoExample();
			example.createCriteria().andIdcursoIn(arrayIds);
			
			LOGGER.info(
					"archivarCursos() / forCursoExtendsMapper.updateByExampleSelective() -> Entrada a forCursoExtendsMapper para invocar a updateByExampleSelective para actualizar cursos según los criterios establecidos");
			resultado = forCursoExtendsMapper.updateByExampleSelective(record, example);
		}
		
		return resultado;
	}

	@Override
	public int desarchivarCursos(List<CursoItem> listCursoItem, HttpServletRequest request) {
		LOGGER.info("desarchivarCursos() -> Entrada al servicio para desarchivar cursos");
		
		List<Long> arrayIds = new ArrayList<>();
		int resultado = 0;
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		String idInstitucionToString =  String.valueOf(idInstitucion);
		
		//Para obtener los ids de los cursos a actualizar
		for(CursoItem curso : listCursoItem) {
			
			//Añadimos a la lista de ids únicamente los ids de los cursos que pueden desarchivarse
			if(idInstitucionToString.equals(curso.getIdInstitucion()) && (SigaConstants.CURSO_ARCHIVADO == curso.getFlagArchivado())) {
				arrayIds.add(curso.getIdCurso());
			}
		}
		
		//Si no hay curso que cumpla las condiciones para desarchivar devolvemos 0
		if(arrayIds.isEmpty()) {
			return 0;
		}else {
		
			//Entidad que se va a rellenar con los valores a actualizar
			ForCurso record = new ForCurso();
			
			record.setFlagarchivado((short)0); // estado desarchivado
			
			//Obtenemos el usuario para setear el campo "usumodificiacion"
			String dniUser = UserTokenUtils.getDniFromJWTToken(token);
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios .createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);
			
			LOGGER.info(
					"desarchivarCursos() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
			List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
			
			AdmUsuarios usuario = usuarios.get(0);
			
			if(usuario == null) {
				LOGGER.warn(
						"desarchivarCursos() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
				return 0;  //Devolvemos 0 cursos desarchivados porque no se va a poder realizar el update al no haber recuperado al usuario
			}else {
				record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
			}
			
			record.setFechamodificacion(new Date()); // seteamos la fecha de modificación
			
			ForCursoExample example = new ForCursoExample();
			example.createCriteria().andIdcursoIn(arrayIds);
			
			LOGGER.info(
					"desarchivarCursos() / forCursoExtendsMapper.updateByExampleSelective() -> Entrada a forCursoExtendsMapper para invocar a updateByExampleSelective para actualizar cursos según los criterios establecidos");
			resultado = forCursoExtendsMapper.updateByExampleSelective(record, example);
			
		}
		
		return resultado;
	}
	
	
}
