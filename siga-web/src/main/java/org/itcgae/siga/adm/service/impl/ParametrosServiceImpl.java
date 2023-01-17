package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.ParametroDTO;
import org.itcgae.siga.DTOs.adm.ParametroDeleteDTO;
import org.itcgae.siga.DTOs.adm.ParametroItem;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IParametrosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrosServiceImpl implements IParametrosService {

	private Logger LOGGER = Logger.getLogger(ParametrosServiceImpl.class);
	
	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;

	@Override
	public ComboDTO getParameterModules() {
		LOGGER.info("getParameterModules() -> Entrada al servicio para obtener los módulos disponibles para los parámetros generales");
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		
		LOGGER.info("getParameterModules() / genParametrosExtendsMapper.getModules() -> Entrada a genParametrosExtendsMapper para obtener los módulos disponibles para los parámetros generales");
		comboItems = genParametrosExtendsMapper.getModules();
		LOGGER.info("getParameterModules() / genParametrosExtendsMapper.getModules() -> Salida de genParametrosExtendsMapper para obtener los módulos disponibles para los parámetros generales");

		if (!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			comboItems.add(0, comboItem);
			combo.setCombooItems(comboItems);
		}

		LOGGER.info("getParameterModules() -> Salida del servicio para obtener los módulos disponibles para los parámetros generales");
		return combo;
	}

	@Override
	public ParametroDTO getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO,
			HttpServletRequest request) {

		LOGGER.info("getParametersSearch() -> Entrada al servicio para buscar los módulos disponibles para los catálogos generales");
		ParametroDTO parametroDTO = new ParametroDTO();
		List<ParametroItem> parametroItems = new ArrayList<ParametroItem>();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("updateParameter() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("updateParameter() / admUsuariosMapper.selectByExample() -> Salida de admUsuariosMapper para obtener información del usuario logeado");
		
		

		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
			
			if (!parametroRequestDTO.getParametrosGenerales().equalsIgnoreCase("")
					&& !parametroRequestDTO.getModulo().equalsIgnoreCase("")
					&& parametroRequestDTO.getIdInstitucion() != null) {
//				if (!String.valueOf(idInstitucion).equals(SigaConstants.InstitucionGeneral)) {
//					// Buscar en gen_parametros por modulo e institucion
//					LOGGER.info("getParametersSearch() / genParametrosExtendsMapper.getParametersSearch() -> Entrada a genParametrosExtendsMapper para obtener listado de los módulos disponibles de una institución");
//					parametroItems = genParametrosExtendsMapper.getParametersSearch(numPagina, parametroRequestDTO, usuario.getIdlenguaje());
//					LOGGER.info("getParametersSearch() / genParametrosExtendsMapper.getParametersSearch() -> Salida de genParametrosExtendsMapper para obtener listado de los módulos disponibles de una institución");
//
//					parametroDTO.setParametrosItems(parametroItems);
//				}
//				 else if (String.valueOf(idInstitucion).equals(SigaConstants.InstitucionGeneral)) {
					 
					LOGGER.info("getParametersSearch() / genParametrosExtendsMapper.getParametersSearch() -> Entrada a genParametrosExtendsMapper para obtener listado de los módulos comunes a todas las instituciones");
					parametroItems = genParametrosExtendsMapper.getParametersSearchGeneral(numPagina, parametroRequestDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion));
					LOGGER.info("getParametersSearch() / genParametrosExtendsMapper.getParametersSearch() -> Salida de genParametrosExtendsMapper para obtener listado de los módulos comunes a todas las instituciones");
					List<ParametroItem> parametroItemsFinal = new ArrayList<ParametroItem>();
					for (ParametroItem parameter : parametroItems) {
						if (parameter.getIdInstitucion().equalsIgnoreCase(String.valueOf(idInstitucion)) || "0".equals(parameter.getIdInstitucion())) {
							parametroItemsFinal.add(parameter);
						}
					}
					if(parametroItemsFinal.size() == 0) {
						parametroItemsFinal = parametroItems;
					}
					parametroDTO.setParametrosItems(parametroItemsFinal);
//				}
			}
		}	
		else {
				
		}
		
		

		LOGGER.info("getParametersSearch() -> Salida del servicio para buscar los módulos disponibles para los catálogos generales");
		return parametroDTO;

	}

	@Override
	public ParametroDTO getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO,
			HttpServletRequest request) {
		LOGGER.info("getParametersRecord() -> Entrada al servicio para buscar histórico de módulos disponibles para la institución del usuario");
		ParametroDTO parametroDTO = new ParametroDTO();
		List<ParametroItem> parametroItems = new ArrayList<ParametroItem>();

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		// Obtener idInstitucion del certificado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		parametroRequestDTO.setIdInstitucion(institucion);

		if (!parametroRequestDTO.getParametrosGenerales().equalsIgnoreCase("")
				&& !parametroRequestDTO.getModulo().equalsIgnoreCase("")
				&& parametroRequestDTO.getIdInstitucion() != null) {
			
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);
			
			LOGGER.info("getParametersRecord() / genParametrosExtendsMapper.getParametersRecord() -> Entrada a genParametrosExtendsMapper para obtener histórico de parámetros generales de un determinado módulo");
			parametroItems = genParametrosExtendsMapper.getParametersRecord(numPagina, parametroRequestDTO, usuario.getIdlenguaje());
			LOGGER.info("getParametersRecord() / genParametrosExtendsMapper.getParametersRecord() -> Salida de genParametrosExtendsMapper para obtener histórico de parámetros generales de un determinado módulo");
			parametroDTO.setParametrosItems(parametroItems);
		}
		else {
			LOGGER.warn("getParametersRecord() -> No hay suficiente informaciñon para la búsqueda");
		}
		
		LOGGER.info("getParametersRecord() -> Salida del servicio para buscar histórico de módulos disponibles para la institución del usuario");
		return parametroDTO;
	}

	@Override
	public UpdateResponseDTO updateParameter(ParametroUpdateDTO parametroUpdateDTO, HttpServletRequest request) {
		LOGGER.info("updateParameter() -> Entrada al servicio para actualizar el valor de un modulo-parámetro");
		int response = 0;
		GenParametros genParametros = new GenParametros();
		GenParametros genParametrosSelect = new GenParametros();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		GenParametrosKey genParametrosKey = new GenParametrosKey();

		// Obtener idInstitucion del certificado y idUsuario del certificado
		// Cargamos el Dni del Token
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("updateParameter() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("updateParameter() / admUsuariosMapper.selectByExample() -> Salida de admUsuariosMapper para obtener información del usuario logeado");
		
		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);

			if (!parametroUpdateDTO.getIdInstitucion().equalsIgnoreCase("")
					&& !parametroUpdateDTO.getModulo().equalsIgnoreCase("")
					&& !parametroUpdateDTO.getParametro().equalsIgnoreCase("")
					&& !parametroUpdateDTO.getValor().equalsIgnoreCase("")) {

				// crear registro igual al existente en tabla gen_parametros, pero
				// con el valor recibido y idInstitucion del usuario logeado
				if (parametroUpdateDTO.getIdInstitucion().equals("0") || parametroUpdateDTO.getIdInstitucion().equals(SigaConstants.InstitucionGeneral)) {

					// seteo de parametros a GenParametros. idInstitucion de
					// certificado
					genParametros.setIdinstitucion(idInstitucion);
					genParametros.setModulo(parametroUpdateDTO.getModulo());
					genParametros.setParametro(parametroUpdateDTO.getParametro());
					genParametros.setValor(parametroUpdateDTO.getValor());
					genParametros.setFechamodificacion(new Date());
					genParametros.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));

					if (!genParametros.getIdinstitucion().equals(null)) {

						genParametrosKey.setIdinstitucion(genParametros.getIdinstitucion());
						genParametrosKey.setModulo(genParametros.getModulo());
						genParametrosKey.setParametro(genParametros.getParametro());

						// comprobamos si realmente existe el registro
						// {idinstitucion,modulo, parametro}, ya que fecha de baja
						// puede ser distinta de null y no se muestra al buscar en
						// pantalla
						LOGGER.info("updateParameter() / genParametrosMapper.selectByPrimaryKey() -> Entrada a genParametrosMapper para comprobar si existe un registro idinstitucion-modulo-parametro");
						genParametrosSelect = genParametrosMapper.selectByPrimaryKey(genParametrosKey);
						LOGGER.info("updateParameter() / genParametrosMapper.selectByPrimaryKey() -> Salida de genParametrosMapper para comprobar si existe un registro idinstitucion-modulo-parametro");

						// si no es nulo, entonces hay que actualizar en vez de
						// crear
						if (genParametrosSelect != null) {
							// actualizamos fecha de baja a null, para que vuelva a
							// estar disponible
							genParametros.setFechaBaja(null);
							
							LOGGER.info("updateParameter() / genParametrosMapper.updateByPrimaryKeySelective() -> Entrada a genParametrosMapper para habilitar un modulo-parametro");
							response = genParametrosExtendsMapper.updateByExampleFechaBaja(genParametros);
							//response = genParametrosMapper.updateByPrimaryKeySelective(genParametros);
							LOGGER.info("updateParameter() / genParametrosMapper.updateByPrimaryKeySelective() -> Salida de genParametrosMapper para habilitar un modulo-parametro");
						}
						// si es nulo, se crea un nuevo registro
						else {
							genParametros.setIdrecurso(parametroUpdateDTO.getIdRecurso());
							LOGGER.info("updateParameter() / genParametrosMapper.insertSelective() -> Entrada a genParametrosMapper para crear un registro modulo-parametro-valor");
							response = genParametrosMapper.insertSelective(genParametros);
							LOGGER.info("updateParameter() / genParametrosMapper.insertSelective() -> Salida de genParametrosMapper para crear un registro modulo-parametro-valor");
						}

					}

				}
				// actualiza registro en tabla gen_parametros con idInstitucion
				// recibido y valor recibido
				else {

					// seteo de parametros a GenParametros. idInstitucion es el
					// recibido
					genParametros.setIdinstitucion(Short.valueOf(parametroUpdateDTO.getIdInstitucion()));
					genParametros.setModulo(parametroUpdateDTO.getModulo());
					genParametros.setParametro(parametroUpdateDTO.getParametro());
					genParametros.setValor(parametroUpdateDTO.getValor());
					genParametros.setFechamodificacion(new Date());
					genParametros.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
					LOGGER.info("updateParameter() / genParametrosMapper.updateByPrimaryKeySelective() -> Entrada a genParametrosMapper para actualizar parámetro a una institucion y valor concretos");
					response = genParametrosMapper.updateByPrimaryKeySelective(genParametros);
					LOGGER.info("updateParameter() / genParametrosMapper.updateByPrimaryKeySelective() -> Salida de genParametrosMapper para actualizar parámetro a una institucion y valor concretos");
				}
			}
		}
		else {
			response = 0;
			LOGGER.warn("updateParameter() -> no existen usuarios y/o la institucion son nulas");
		}
		

		// Comprobacion para generar respuesta
		if (response == 1){
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.warn("updateParameter() -> OK. Se ha actualizado el valor de un módulo-parámetro");
		}
		else{
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateParameter() -> KO. NO se ha podido actualizar el valor de un módulo-parámetro");
		}

		LOGGER.info("updateParameter() -> Salida del servicio para actualizar el valor de un módulo-parámetro");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO deleteParameter(ParametroDeleteDTO parametroDeleteDTO, HttpServletRequest request) {
		LOGGER.info("deleteParameter() -> Entrada al servicio para eliminar el valor de un modulo-parámetro para la institución del usuario logeado");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

		// Obtener idInstitucion del certificado y idUsuario del certificado
		// Obtener idInstitucion del certificado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("deleteParameter() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("deleteParameter() / admUsuariosMapper.selectByExample() -> Salida de admUsuariosMapper para obtener información del usuario logeado");
		
		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);

			if (!parametroDeleteDTO.getIdInstitucion().equalsIgnoreCase("")
					&& !parametroDeleteDTO.getModulo().equalsIgnoreCase("")
					&& !parametroDeleteDTO.getParametro().equalsIgnoreCase("")) {

				GenParametros genParametros = new GenParametros();
				genParametros.setModulo(parametroDeleteDTO.getModulo());
				genParametros.setParametro(parametroDeleteDTO.getParametro());
				genParametros.setIdinstitucion(Short.valueOf(parametroDeleteDTO.getIdInstitucion()));
				genParametros.setFechaBaja(new Date());
				genParametros.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));

				LOGGER.info("deleteParameter() / genParametrosMapper.updateByPrimaryKeySelective() -> Entrada a genParametrosMapper para eliminar el valor de un módulo-parámetro");
				response = genParametrosMapper.updateByPrimaryKeySelective(genParametros);
				LOGGER.info("deleteParameter() / genParametrosMapper.updateByPrimaryKeySelective() -> Salida de genParametrosMapper para eliminar el valor de un módulo-parámetro");
			}
			else {
				LOGGER.warn("deleteParameter() -> No hay suficiente información para eliminar el valor de un módulo-parámetro");
			}
		}
		else {
			response = 0;
			LOGGER.warn("deleteParameter() -> No existen usuarios y/o la institucion son nulas");
		}
		

		if (response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.warn("deleteParameter() -> KO. Se ha eliminado correctamente el valor de un módulo-parámetro");
		}
			
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("deleteParameter() -> KO. NO se ha podido eliminar el valor de un módulo-parámetro");
		}
			

		LOGGER.info("deleteParameter() -> Salida del servicio para eliminar el valor de un modulo-parámetro para la institución del usuario logeado");
		return updateResponseDTO;
	}

}
