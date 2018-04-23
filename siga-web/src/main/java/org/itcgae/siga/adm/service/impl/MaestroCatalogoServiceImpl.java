package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CatalogoDeleteDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroItem;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InstitucionDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
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
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MaestroCatalogoServiceImpl implements IMaestroCatalogoService {


	
	@Autowired
	GenTablasMaestrasExtendsMapper tablasMaestrasMapper;
	
	@Autowired
	GenRecursosCatalogosMapper recursosCatalogoMapper;
	
	@Autowired
	AdmUsuariosMapper usuariosMapper;

	@Override
	public ComboDTO getTabla() {
		ComboDTO response = new ComboDTO();
		//Cargamos la tabla maestra para ver qué catalogos queremos gestionar
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andFlagborradologicoEqualTo(new Long(0));
		List<GenTablasMaestras> tablasMaestras = tablasMaestrasMapper.selectByExample(exampleTablasMaestras);
		List<ComboItem> combos = new ArrayList<ComboItem>();
		ComboItem combo = new ComboItem();
		combo.setValue("");
		combo.setLabel("");
		combos.add(combo);

		if (null != tablasMaestras && tablasMaestras.size() > 0) {
			for (Iterator<GenTablasMaestras> iterator = tablasMaestras.iterator(); iterator.hasNext();) {
				GenTablasMaestras tablaMaestra = (GenTablasMaestras) iterator.next();
				combo = new ComboItem();
				combo.setValue(tablaMaestra.getIdtablamaestra());
				combo.setLabel(tablaMaestra.getAliastabla());
				combos.add(combo);
			}

		}
		
		response.setCombooItems(combos);
		return response;
	}




	@Override
	public CatalogoMaestroDTO getDatosCatalogo(CatalogoRequestDTO catalogoRequest,HttpServletRequest request) {
		//Obtenemos los datos del catálogo con la tabla maestra seleccionada
		
		//Obtenemos la institución del Token
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		catalogoRequest.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		
		// Obtenemos el DNI del token
		String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization")).substring(0,9);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoRequest.getIdInstitucion()));
		//Obtenemos el usuario para añadir el USUMODIFICACION
		List<AdmUsuarios> usuarios = usuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		catalogoRequest.setIdLenguaje(usuario.getIdlenguaje());
		CatalogoMaestroDTO response = new CatalogoMaestroDTO();
		List<CatalogoMaestroItem> catalogoMaestroItem = new ArrayList<CatalogoMaestroItem>();
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoRequest.getCatalogo());
		List<GenTablasMaestras> tablasMaestras = tablasMaestrasMapper.selectByExample(exampleTablasMaestras);
		
		if (null != tablasMaestras && tablasMaestras.size() > 0) {
				//Obtenemos el catálogo
				GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
   			    catalogoMaestroItem =tablasMaestrasMapper.selectCatalogosByTabla(tablaMaestra,catalogoRequest);
			}

		response.setCatalogoMaestroItem(catalogoMaestroItem);
		return response;
	}




	@Override
	public UpdateResponseDTO updateDatosCatalogo(CatalogoUpdateDTO catalogoUpdate,HttpServletRequest request) {
		//Editamos los datos del catálogo con la tabla maestra seleccionada
		
		//Obtenemos la institución del Token
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		catalogoUpdate.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		UpdateResponseDTO response = new UpdateResponseDTO();
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoUpdate.getTabla());
		List<GenTablasMaestras> tablasMaestras = tablasMaestrasMapper.selectByExample(exampleTablasMaestras);
		
		// Obtenemos el DNI del token
		String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization")).substring(0,9);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoUpdate.getIdInstitucion()));
		
		//Obtenemos el usuario para añadir el USUMODIFICACION
		List<AdmUsuarios> usuarios = usuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		catalogoUpdate.setIdLenguaje(usuario.getIdlenguaje());
		if (null != tablasMaestras && tablasMaestras.size() > 0) {

				GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
				//Editamos los datos que se han modificado
				if (!catalogoUpdate.getCodigoExt().equalsIgnoreCase("") ) {
					tablasMaestrasMapper.updateCodigoExterno(tablaMaestra,catalogoUpdate);
				}
				if (!catalogoUpdate.getDescripcion().equalsIgnoreCase("")) {
					tablasMaestrasMapper.updateRecursos(tablaMaestra,catalogoUpdate);
				}
				
			}

		response.setStatus(SigaConstants.OK);
		return response;
	}




	@Override
	public UpdateResponseDTO deleteDatosCatalogo(CatalogoDeleteDTO catalogoDelete,HttpServletRequest request) {
		//eliminamos los datos del catálogo con la tabla maestra seleccionada
		
		//Obtenemos la institución del Token
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		catalogoDelete.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		UpdateResponseDTO response = new UpdateResponseDTO();
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoDelete.getTabla());
		List<GenTablasMaestras> tablasMaestras = tablasMaestrasMapper.selectByExample(exampleTablasMaestras);
		//Modificamos la fecha de Baja para darle de baja
		if (null != tablasMaestras && tablasMaestras.size() > 0) {
				GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
				tablasMaestrasMapper.deleteRecursos(tablaMaestra,catalogoDelete);
			}
	
		response.setStatus(SigaConstants.OK);
		return response;
	
	}




	@Override
	public UpdateResponseDTO createDatosCatalogo(CatalogoUpdateDTO catalogoCreate,HttpServletRequest request) {
		//Creamos un nuevo registro en el catálogo con la tabla maestra seleccionada
		
		//Obtenemos la institución del Token
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		catalogoCreate.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		UpdateResponseDTO response = new UpdateResponseDTO();
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoCreate.getTabla());
		List<GenTablasMaestras> tablasMaestras = tablasMaestrasMapper.selectByExample(exampleTablasMaestras);
		
		if (null != tablasMaestras && tablasMaestras.size() > 0) {

				GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
				//Obtenemos el recurso para ver si ya existe
				GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
				exampleRecursos.createCriteria().andDescripcionEqualTo(catalogoCreate.getDescripcion());
				exampleRecursos.createCriteria().andNombretablaEqualTo(tablaMaestra.getIdtablamaestra());
				exampleRecursos.createCriteria().andIdinstitucionEqualTo(Short.valueOf(catalogoCreate.getIdInstitucion()));
				List<GenRecursosCatalogos> recursos = recursosCatalogoMapper.selectByExample(exampleRecursos );
				if (null != recursos && recursos.size() > 0) {
					Error error = new Error();
					error.setDescription("Ya existe un registro para esa descripción");
					response.setError(error);
					response.setStatus(SigaConstants.KO);
					return response;
				}
				InstitucionDTO idInstitucion = tablasMaestrasMapper.selectColumnName(tablaMaestra);
				Boolean isInstitucion = Boolean.FALSE;
				if (null != idInstitucion && idInstitucion.getIdInstitucion().equals("1")) {
					isInstitucion =  Boolean.TRUE;
				}
				// Obtenemos el DNI del token
				String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization")).substring(0,9);
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoCreate.getIdInstitucion()));
				
				//Obtenemos el usuario para añadir el USUMODIFICACION
				List<AdmUsuarios> usuarios = usuariosMapper.selectByExample(exampleUsuarios);
				AdmUsuarios usuario = usuarios.get(0);
				catalogoCreate.setIdLenguaje(usuario.getIdlenguaje());
				tablasMaestrasMapper.createRecursos(tablaMaestra,catalogoCreate,isInstitucion,usuario.getIdusuario());
				tablasMaestrasMapper.createCatalogo(tablaMaestra,catalogoCreate,isInstitucion,usuario.getIdusuario());
				
			}

		response.setStatus(SigaConstants.OK);
		return response;
	}

	
	
	@Override
	public CatalogoMaestroDTO getDatosCatalogoHistorico(CatalogoRequestDTO catalogoRequest,HttpServletRequest request) {
		//Obtenemos los datos del catálogo con la tabla maestra seleccionada
		
		//Obtenemos la institución del Token
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		catalogoRequest.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		
		// Obtenemos el DNI del token
		String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization")).substring(0,9);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(catalogoRequest.getIdInstitucion()));
		//Obtenemos el usuario para añadir el USUMODIFICACION
		List<AdmUsuarios> usuarios = usuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		catalogoRequest.setIdLenguaje(usuario.getIdlenguaje());
		CatalogoMaestroDTO response = new CatalogoMaestroDTO();
		List<CatalogoMaestroItem> catalogoMaestroItem = new ArrayList<CatalogoMaestroItem>();
		GenTablasMaestrasExample exampleTablasMaestras = new GenTablasMaestrasExample();
		exampleTablasMaestras.setDistinct(true);
		exampleTablasMaestras.setOrderByClause("ALIASTABLA ASC");
		exampleTablasMaestras.createCriteria().andIdtablamaestraEqualTo(catalogoRequest.getCatalogo());
		List<GenTablasMaestras> tablasMaestras = tablasMaestrasMapper.selectByExample(exampleTablasMaestras);
		
		if (null != tablasMaestras && tablasMaestras.size() > 0) {
				//Obtenemos el catálogo
				GenTablasMaestras tablaMaestra = (GenTablasMaestras) tablasMaestras.get(0);
   			    catalogoMaestroItem =tablasMaestrasMapper.selectCatalogosHistoricoByTabla(tablaMaestra,catalogoRequest);
			}

		response.setCatalogoMaestroItem(catalogoMaestroItem);
		return response;
	}



}
