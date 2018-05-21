package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.CatalogoDeleteDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroItem;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InstitucionDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboCatalogoDTO;
import org.itcgae.siga.DTOs.gen.ComboCatalogoItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.adm.service.IMaestroCatalogoService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.GenTablasMaestras;
import org.itcgae.siga.db.entities.GenTablasMaestrasExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.services.gen.mappers.GenTablasMaestrasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MaestroCatalogoServiceImpl implements IMaestroCatalogoService {

	private Logger LOGGER = Logger.getLogger(MaestroCatalogoServiceImpl.class);
	
	@Autowired
	private GenTablasMaestrasExtendsMapper genTablasMaestrasExtendsMapper;
	
	@Autowired
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;
	
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;

	@Override
	public ComboCatalogoDTO getTabla() {
		LOGGER.info("getTabla() -> Entrada al servicio para obtener las diferentes entidades");
		ComboCatalogoDTO response = new ComboCatalogoDTO();
		List<ComboCatalogoItem> combos = new ArrayList<ComboCatalogoItem>();
		ComboCatalogoItem combo = new ComboCatalogoItem();
		
		//Cargamos la tabla maestra para ver qué catalogos queremos gestionar
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andFlagborradologicoEqualTo(new Long(0));
		LOGGER.info("getTabla() / genTablasMaestrasExtendsMapper.selectByExample() -> Entrada a genTablasMaestrasExtendsMapper para obtener listado de entidades");
		List<GenTablasMaestras> tablasMaestras = genTablasMaestrasExtendsMapper.selectByExample(exampleTablasMaestras);
		LOGGER.info("getTabla() / genTablasMaestrasExtendsMapper.selectByExample() -> Salida de genTablasMaestrasExtendsMapper para obtener listado de entidades");
		combo.setValue("");
		combo.setLabel("");
		combos.add(combo);

		if (null != tablasMaestras && tablasMaestras.size() > 0) {
			for (Iterator<GenTablasMaestras> iterator = tablasMaestras.iterator(); iterator.hasNext();) {
				GenTablasMaestras tablaMaestra = (GenTablasMaestras) iterator.next();
				combo = new ComboCatalogoItem();
				combo.setValue(tablaMaestra.getIdtablamaestra());
				combo.setLabel(tablaMaestra.getAliastabla());
				combo.setLocal(tablaMaestra.getLocal());
				combos.add(combo);
			}
		}
		
		response.setComboCatalogoItems(combos);
		LOGGER.info("getTabla() -> Salida del servicio para obtener las diferentes entidades");
		return response;
	}


	@Override
	public CatalogoMaestroDTO getDatosCatalogo(CatalogoRequestDTO catalogoRequest,HttpServletRequest request) {
		LOGGER.info("getDatosCatalogo() -> Entrada al servicio para obtener información de una entidad");
		CatalogoMaestroDTO response = new CatalogoMaestroDTO();
		List<CatalogoMaestroItem> catalogoMaestroItem = new ArrayList<CatalogoMaestroItem>();
		
		//Obtenemos la institución del Token
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		// Obtenemos el DNI del token
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		// Obtenemos el usuario para añadir el USUMODIFICACION
		LOGGER.info("getDatosCatalogo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("getDatosCatalogo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		if(null != institucion && null != usuarios && usuarios.size() > 0) {
			catalogoRequest.setIdInstitucion(institucion);
			AdmUsuarios usuario = usuarios.get(0);
			catalogoRequest.setIdLenguaje(usuario.getIdlenguaje());
				
			GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
			exampleTablasMaestras.setDistinct(true);
			exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
			exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoRequest.getCatalogo());
			LOGGER.info("getDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() ->  Entrada a genTablasMaestrasExtendsMapper para obtener datos para tablas maestras");
			List<GenTablasMaestras> tablasMaestras = genTablasMaestrasExtendsMapper.selectByExample(exampleTablasMaestras);
			LOGGER.info("getDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() ->  Salida de genTablasMaestrasExtendsMapper para obtener datos para tablas maestras");
			
			if (null != tablasMaestras && tablasMaestras.size() > 0) {
					//Obtenemos el catálogo
					GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
					LOGGER.info("getDatosCatalogo() / genTablasMaestrasExtendsMapper.selectCatalogosByTabla() ->  Entrada a genTablasMaestrasExtendsMapper para seleccionar catálogos de una tabla maestra");
	   			    catalogoMaestroItem =genTablasMaestrasExtendsMapper.selectCatalogosByTabla(tablaMaestra,catalogoRequest);
	   			    LOGGER.info("getDatosCatalogo() / genTablasMaestrasExtendsMapper.selectCatalogosByTabla() ->  Salida de genTablasMaestrasExtendsMapper para seleccionar catálogos de una tabla maestra");
			}
			else {
				LOGGER.warn("getDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() ->  no existen datos para tablas maestras");
			}
		}
		else {
			LOGGER.warn("getDatosCatalogo() ->  no existen usuarios y/o la institucion son nulas");
		}
		
		response.setCatalogoMaestroItem(catalogoMaestroItem);
		LOGGER.info("getDatosCatalogo() -> Salida del servicio para obtener información de una entidad");
		return response;
	}




	@Override
	public UpdateResponseDTO updateDatosCatalogo(CatalogoUpdateDTO catalogoUpdate,HttpServletRequest request) {
		//Editamos los datos del catálogo con la tabla maestra seleccionada
		LOGGER.info("updateDatosCatalogo() -> Entrada al servicio para actualizar los datos del catálogo con la tabla maestra seleccionada");
		UpdateResponseDTO response = new UpdateResponseDTO();
		
		// Obtenemos el DNI del token e institución del Token
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoUpdate.getIdInstitucion()));
		LOGGER.info("updateDatosCatalogo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("updateDatosCatalogo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		if(null != institucion && null != usuarios && usuarios.size() > 0) {
			catalogoUpdate.setIdInstitucion(institucion);
			
			GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
			exampleTablasMaestras.setDistinct(true);
			exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
			exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoUpdate.getTabla());
			LOGGER.info("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> Entrada a genTablasMaestrasExtendsMapper para obtener información de la tabla de un catálogo");
			List<GenTablasMaestras> tablasMaestras = genTablasMaestrasExtendsMapper.selectByExample(exampleTablasMaestras);
			LOGGER.info("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> Salida de genTablasMaestrasExtendsMapper para obtener información de la tabla de un catálogo");

			//Obtenemos el usuario para añadir el USUMODIFICACION
			AdmUsuarios usuario = usuarios.get(0);
			catalogoUpdate.setIdLenguaje(usuario.getIdlenguaje());
			if (null != tablasMaestras && tablasMaestras.size() > 0) {

					GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
					//Editamos los datos que se han modificado
					if (null == catalogoUpdate.getCodigoExt()  ) {
						catalogoUpdate.setCodigoExt("");
					}
					LOGGER.info("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.updateCodigoExterno() -> Entrada a genTablasMaestrasExtendsMapper para actualizar el codigo externo de una tabla maestra");
					genTablasMaestrasExtendsMapper.updateCodigoExterno(tablaMaestra,catalogoUpdate);
					LOGGER.info("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.updateCodigoExterno() -> Salida de genTablasMaestrasExtendsMapper para actualizar el codigo externo de una tabla maestra");

					if (!catalogoUpdate.getDescripcion().equalsIgnoreCase("")) {
						LOGGER.info("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.updateRecursos() -> Entrada a genTablasMaestrasExtendsMapper para actualizar la descripcion de una tabla maestra");
						genTablasMaestrasExtendsMapper.updateRecursos(tablaMaestra,catalogoUpdate);
						LOGGER.info("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.updateRecursos() -> Salida de genTablasMaestrasExtendsMapper para actualizar la descripcion de una tabla maestra");
					}
					response.setStatus(SigaConstants.OK);
			}
			else {
				LOGGER.warn("updateDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> No se han encontrado datos");
				response.setStatus(SigaConstants.KO);
			}
			
		}
		else {
			LOGGER.warn("updateDatosCatalogo() ->  no existen usuarios y/o la institucion son nulas");
			response.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("updateDatosCatalogo() -> Salida del servicio para actualizar los datos del catálogo con la tabla maestra seleccionada");
		return response;
	}


	
	@Override
	public UpdateResponseDTO deleteDatosCatalogo(CatalogoDeleteDTO catalogoDelete,HttpServletRequest request) {
		//eliminamos los datos del catálogo con la tabla maestra seleccionada
		LOGGER.info("deleteDatosCatalogo() -> Entrada al servicio para eliminar los datos del catálogo con la tabla maestra seleccionada");
		int respuesta = 0;
		UpdateResponseDTO response = new UpdateResponseDTO();
		//Obtenemos la institución del Token
		String token = request.getHeader("Authorization");
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		if(null != institucion) {
			catalogoDelete.setIdInstitucion(institucion);
			
			GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
			exampleTablasMaestras.setDistinct(true);
			exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
			exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoDelete.getTabla());
			LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> Entrada a genTablasMaestrasExtendsMapper para obtener información de la tabla de un catálogo");
			List<GenTablasMaestras> tablasMaestras = genTablasMaestrasExtendsMapper.selectByExample(exampleTablasMaestras);
			LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> Salida de genTablasMaestrasExtendsMapper para obtener información de la tabla de un catálogo");
			//Modificamos la fecha de Baja para darle de baja
			if (null != tablasMaestras && tablasMaestras.size() > 0) {
				GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
				LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.selectColumnName() -> Entrada a genTablasMaestrasExtendsMapper para obtener institución de la columna de una tabla maestra");
				InstitucionDTO idInstitucion = genTablasMaestrasExtendsMapper.selectColumnName(tablaMaestra);
				LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.selectColumnName() -> Salida de genTablasMaestrasExtendsMapper para obtener institución de la columna de una tabla maestra");
				Boolean isInstitucion = Boolean.FALSE;
				if (null != idInstitucion && idInstitucion.getIdInstitucion().equals("1")) {
					isInstitucion =  Boolean.TRUE;
				}
				LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.deleteRecursos() -> Entrada a genTablasMaestrasExtendsMapper eliminar un recurso");
				respuesta = genTablasMaestrasExtendsMapper.deleteRecursos(tablaMaestra,catalogoDelete,isInstitucion);
				LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.deleteRecursos() -> Salida de genTablasMaestrasExtendsMapper eliminar un recurso");
				
				// comprobacion de si se realizo el borrado bien
				if(respuesta == 0) {
					response.setStatus(SigaConstants.KO);
					LOGGER.warn("deleteDatosCatalogo() -> KO. NO se ha podido eliminar los datos del catálogo con la tabla maestra seleccionada");
				}
				else {
					response.setStatus(SigaConstants.OK);
					LOGGER.info("deleteDatosCatalogo() -> OK. Se han eliminado los datos del catálogo con la tabla maestra seleccionada");
					
				}
			}
			else {
				response.setStatus(SigaConstants.KO);
				LOGGER.warn("deleteDatosCatalogo() -> KO. NO se ha podido obtener información de la tabla de un catálogo");
			}
		
			
		}
		else {
			response.setStatus(SigaConstants.KO);
			LOGGER.warn("deleteDatosCatalogo() -> KO. NO se ha podido recuperar la institucion del token");
		}
		
		LOGGER.info("deleteDatosCatalogo() -> Salida del servicio para eliminar los datos del catálogo con la tabla maestra seleccionada");
		return response;
	}




	@Override
	public UpdateResponseDTO createDatosCatalogo(CatalogoUpdateDTO catalogoCreate,HttpServletRequest request) {
		//Creamos un nuevo registro en el catálogo con la tabla maestra seleccionada
		LOGGER.info("createDatosCatalogo() -> Entrada al servicio para crear un nuevo registro en el catálogo con la tabla maestra seleccionada");
		UpdateResponseDTO response = new UpdateResponseDTO();
		int respuesta1 = 0;
		int respuesta2 = 0;
		//Obtenemos la institución del Token
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		if(null != institucion) {
			catalogoCreate.setIdInstitucion(institucion);
			
			GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
			exampleTablasMaestras.setDistinct(true);
			exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
			exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoCreate.getTabla());
			LOGGER.info("createDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> Entrada a genTablasMaestrasExtendsMapper para tabla maestra de un catálogo");
			List<GenTablasMaestras> tablasMaestras = genTablasMaestrasExtendsMapper.selectByExample(exampleTablasMaestras);
			LOGGER.info("createDatosCatalogo() / genTablasMaestrasExtendsMapper.selectByExample() -> Salida de genTablasMaestrasExtendsMapper para tabla maestra de un catálogo");
			
			if (null != tablasMaestras && tablasMaestras.size() > 0) {
					GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
					//Obtenemos el recurso para ver si ya existe
					GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
					exampleRecursos.createCriteria().andDescripcionEqualTo(catalogoCreate.getDescripcion()).andNombretablaEqualTo(tablaMaestra.getIdtablamaestra()).andIdinstitucionEqualTo(Short.valueOf(catalogoCreate.getIdInstitucion()));
					LOGGER.info("deleteDatosCatalogo() / genRecursosCatalogosMapper.selectByExample() -> Entrada a genRecursosCatalogosMapper para obtener institución de la columna de una tabla maestra");
					List<GenRecursosCatalogos> recursos = genRecursosCatalogosMapper.selectByExample(exampleRecursos);
					LOGGER.info("deleteDatosCatalogo() / genRecursosCatalogosMapper.selectByExample() -> Entrada a genRecursosCatalogosMapper para obtener institución de la columna de una tabla maestra");
					if (null != recursos && recursos.size() > 0) {
						Error error = new Error();
						error.setMessage("Ya existe un registro para esa descripción");
						response.setError(error);
						response.setStatus(SigaConstants.KO);
						LOGGER.warn("deleteDatosCatalogo() -> KO. Ya existe un registro para esa descripción");
						return response;
					}
					
					LOGGER.info("createDatosCatalogo() / genTablasMaestrasExtendsMapper.selectColumnName() -> Entrada a genTablasMaestrasExtendsMapper para obtener institución de la columna de una tabla maestra");
					InstitucionDTO idInstitucion = genTablasMaestrasExtendsMapper.selectColumnName(tablaMaestra);
					LOGGER.info("createDatosCatalogo() / genTablasMaestrasExtendsMapper.selectColumnName() -> Salida de genTablasMaestrasExtendsMapper para obtener institución de la columna de una tabla maestra");
					Boolean isInstitucion = Boolean.FALSE;
					if (null != idInstitucion && idInstitucion.getIdInstitucion().equals("1")) {
						isInstitucion =  Boolean.TRUE;
					}

					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoCreate.getIdInstitucion()));
					
					//Obtenemos el usuario para añadir el USUMODIFICACION
					LOGGER.info("deleteDatosCatalogo() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener información del usuario logeado");
					List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
					LOGGER.info("deleteDatosCatalogo() / admUsuariosMapper.selectByExample() -> Salida de admUsuariosMapper para obtener información del usuario logeado");
					if(null != usuarios && usuarios.size() > 0) {
						AdmUsuarios usuario = usuarios.get(0);
						catalogoCreate.setIdLenguaje(usuario.getIdlenguaje());
						LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.createRecursos() -> Entrada a genTablasMaestrasExtendsMapper para crear relación entre recursos-catalogos");
						respuesta1 = genTablasMaestrasExtendsMapper.createRecursos(tablaMaestra,catalogoCreate,isInstitucion,usuario.getIdusuario());
						LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.createRecursos() -> Salida de genTablasMaestrasExtendsMapper para crear relación entre recursos-catalogos");
						
						LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.createCatalogo() -> Entrada a genTablasMaestrasExtendsMapper para crear un catálogo de la tabla maestra");
						respuesta2 = genTablasMaestrasExtendsMapper.createCatalogo(tablaMaestra,catalogoCreate,isInstitucion,usuario.getIdusuario());
						LOGGER.info("deleteDatosCatalogo() / genTablasMaestrasExtendsMapper.createCatalogo() -> Salida de genTablasMaestrasExtendsMapper para crear un catálogo de la tabla maestra");
						
						if(respuesta1 == 1 && respuesta2 == 1) {
							response.setStatus(SigaConstants.OK);
						}
						else {
							response.setStatus(SigaConstants.KO);
							LOGGER.warn("deleteDatosCatalogo() -> KO. NO se ha realizado correctamente la creacion de tablas recursos-catálogos y/o crear un catálogo de la tabla maestra");
						}
					}
					else {
						response.setStatus(SigaConstants.KO);
						LOGGER.warn("deleteDatosCatalogo() -> KO. NO se han podido obtener los usuarios o no existen");
					}
					
			}
			else {
				response.setStatus(SigaConstants.KO);
				LOGGER.warn("deleteDatosCatalogo() -> KO. NO se ha podido obtener ninguna tabla maestra");
			}
		}
		else {
			response.setStatus(SigaConstants.KO);
			LOGGER.warn("deleteDatosCatalogo() -> KO. NO se ha podido recuperar la institucion del token");
		}
		
		LOGGER.info("createDatosCatalogo() -> Salida del servicio para crear un nuevo registro en el catálogo con la tabla maestra seleccionada");
		return response;
	}

	
	
	@Override
	public CatalogoMaestroDTO getDatosCatalogoHistorico(CatalogoRequestDTO catalogoRequest,HttpServletRequest request) {
		LOGGER.info("getDatosCatalogoHistorico() -> Entrada al servicio para obtener el histórico de datos del catálogo con la tabla maestra seleccionada");
		//Obtenemos los datos del catálogo con la tabla maestra seleccionada
		CatalogoMaestroDTO response = new CatalogoMaestroDTO();
		
		String token = request.getHeader("Authorization");
		// Obtenemos el DNI del token
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		//Obtenemos la institución del Token
		String institucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoRequest.getIdInstitucion()));
		//Obtenemos el usuario para añadir el USUMODIFICACION
		LOGGER.info("getDatosCatalogoHistorico() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("getDatosCatalogoHistorico() / admUsuariosMapper.selectByExample() -> Salida de admUsuariosMapper para obtener información del usuario logeado");
		
		if(null != institucion && null != usuarios && usuarios.size() > 0) {
			catalogoRequest.setIdInstitucion(institucion);
			
			AdmUsuarios usuario = usuarios.get(0);
			catalogoRequest.setIdLenguaje(usuario.getIdlenguaje());
			
			List<CatalogoMaestroItem> catalogoMaestroItem = new ArrayList<CatalogoMaestroItem>();
			GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
			exampleTablasMaestras.setDistinct(true);
			exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
			exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoRequest.getCatalogo());
			LOGGER.info("getDatosCatalogoHistorico() / genTablasMaestrasExtendsMapper.selectByExample() -> Entrada a genTablasMaestrasExtendsMapper para obtener catálogos de una tabla maestra");
			List<GenTablasMaestras> tablasMaestras = genTablasMaestrasExtendsMapper.selectByExample(exampleTablasMaestras);
			LOGGER.info("getDatosCatalogoHistorico() / genTablasMaestrasExtendsMapper.selectByExample() -> Salida de genTablasMaestrasExtendsMapper para obtener catálogos de una tabla maestra");
			
			if (null != tablasMaestras && tablasMaestras.size() > 0) {
					//Obtenemos el catálogo
					GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
					LOGGER.info("getDatosCatalogoHistorico() / genTablasMaestrasExtendsMapper.selectCatalogosHistoricoByTabla() -> Entrada a genTablasMaestrasExtendsMapper para obtener catálogos de una tabla maestra");
	   			    catalogoMaestroItem =genTablasMaestrasExtendsMapper.selectCatalogosHistoricoByTabla(tablaMaestra,catalogoRequest);
	   			    LOGGER.info("getDatosCatalogoHistorico() / genTablasMaestrasExtendsMapper.selectCatalogosHistoricoByTabla() -> Entrada a genTablasMaestrasExtendsMapper para obtener catálogos de una tabla maestra");
			}
			else {
				LOGGER.warn("getDatosCatalogoHistorico() -> No existen o no se han obtenido catálogos de una tabla maestra");
			}
			
			// añadimos resultados al objeto de respuesta
			response.setCatalogoMaestroItem(catalogoMaestroItem);
		}
		else {
			LOGGER.warn("getDatosCatalogoHistorico() ->  NO existen usuarios y/o la institucion son nulas");
		}
		
		LOGGER.info("getDatosCatalogoHistorico() -> Salida del servicio para obtener el histórico de datos del catálogo con la tabla maestra seleccionada");
		return response;
	}



}
