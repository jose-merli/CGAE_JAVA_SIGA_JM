package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoItem;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IMultiidiomaCatalogosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.GenTablasMaestras;
import org.itcgae.siga.db.entities.GenTablasMaestrasExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenTablasMaestrasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiidiomaCatalogosServiceImpl implements IMultiidiomaCatalogosService {

	
	private Logger LOGGER = Logger.getLogger(MultiidiomaCatalogosServiceImpl.class);
	
	@Autowired
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;
	
	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private GenTablasMaestrasExtendsMapper genTablasMaestrasExtendsMapper;
	
	@Override
	public ComboDTO getCatalogLenguage() {
		LOGGER.info("getCatalogLenguage() -> Entrada al servicio para obtener los idiomas disponibles");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		LOGGER.info("getCatalogLenguage() / genDiccionarioExtendsMapper.getLabelLenguage() -> Entrada a genDiccionarioExtendsMapper para obtener los idiomas");
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
		LOGGER.info("getCatalogLenguage() / genDiccionarioExtendsMapper.getLabelLenguage() -> Salida de genDiccionarioExtendsMapper para obtener los idiomas");
		// primer elemento a vacio para componente de pantalla dropdown
		comboItem.setLabel("");
		comboItem.setValue("");
		combooItems.add(0,comboItem);
		comboDTO.setCombooItems(combooItems);
		
		LOGGER.info("getCatalogLenguage() -> Salida del servicio para obtener los idiomas disponibles");
		return comboDTO;
	}

	@Override
	public ComboDTO getCatalogEntity(HttpServletRequest request) {
		LOGGER.info("getCatalogEntity() -> Entrada al servicio para obtener las entidades de una institución");
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		ComboItem comboItem = new ComboItem();
		// obtener idInstitucion del usuario logeado
		String token = request.getHeader("Authorization");
		//Obtenemos la institución del Token
		String idInstitucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		if(!idInstitucion.equals(null)) {
			
			LOGGER.info("getCatalogEntity() / genRecursosCatalogosExtendsMapper.getCatalogEntity() -> Entrada a genRecursosCatalogosExtendsMapper para obtener listado de entidades por institución");
			combooItems = genRecursosCatalogosExtendsMapper.getCatalogEntity(idInstitucion);
			LOGGER.info("getCatalogEntity() / genRecursosCatalogosExtendsMapper.getCatalogEntity() -> Salida de genRecursosCatalogosExtendsMapper para obtener listado de entidades por institución");
			// primer elemento a vacio para componente de pantalla dropdown
			comboItem.setLabel("");
			comboItem.setValue("");
			combooItems.add(0,comboItem);
			comboDTO.setCombooItems(combooItems);
		}
		else {
			LOGGER.warn("getCatalogEntity() -> No se ha podido obtener la institución del token");
		}
		
		LOGGER.info("getCatalogEntity() -> Salida del servicio para obtener las entidades de una institución");
		return comboDTO;
	}

	@Override
	public MultiidiomaCatalogoDTO catalogSearch(int numPagina,
			MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO, HttpServletRequest request) {
		
		LOGGER.info("catalogSearch() -> Entrada al servicio para búsqueda de catálogos de una institución por idiomas");
		List<MultiidiomaCatalogoItem> multiidiomaCatalogoItem = new ArrayList<MultiidiomaCatalogoItem>();
		MultiidiomaCatalogoDTO multiidiomaCatalogoDTO =  new MultiidiomaCatalogoDTO();
		
		// obtener idInstitucion del usuario logeado
		String token = request.getHeader("Authorization");
		//Obtenemos la institución del Token
		String idInstitucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		
		if(!multiidiomaCatalogoSearchDTO.getIdiomaBusqueda().equals(null) &&  !multiidiomaCatalogoSearchDTO.getIdiomaBusqueda().equalsIgnoreCase("") &&
				!multiidiomaCatalogoSearchDTO.getIdiomaTraduccion().equals(null) && !multiidiomaCatalogoSearchDTO.getIdiomaTraduccion().equalsIgnoreCase("") &&
				!multiidiomaCatalogoSearchDTO.getNombreTabla().equals(null) && !multiidiomaCatalogoSearchDTO.getNombreTabla().equalsIgnoreCase("")){
			
			if(!idInstitucion.equals(null)) {
				
				
				GenTablasMaestrasExample example = new GenTablasMaestrasExample();
				example.createCriteria().andIdtablamaestraEqualTo(multiidiomaCatalogoSearchDTO.getNombreTabla());
				
				String campoTabla ="";
				List<GenTablasMaestras> tablasmaestra = new ArrayList<GenTablasMaestras>();
				tablasmaestra =  genTablasMaestrasExtendsMapper.selectByExample(example);
				if(!tablasmaestra.isEmpty())
					campoTabla = tablasmaestra.get(0).getIdcampodescripcion();
				
				LOGGER.info("catalogSearch() / genRecursosCatalogosExtendsMapper.getCatalogSearch() -> Entrada a genRecursosCatalogosExtendsMapper para obtener listado de catálogos de una institución por idiomas");
				multiidiomaCatalogoItem = genRecursosCatalogosExtendsMapper.getCatalogSearch(numPagina, multiidiomaCatalogoSearchDTO, idInstitucion, campoTabla);
				LOGGER.info("catalogSearch() / genRecursosCatalogosExtendsMapper.getCatalogSearch() -> Salida de genRecursosCatalogosExtendsMapper para obtener listado de catálogos de una institución por idiomas");
				multiidiomaCatalogoDTO.setMultiidiomaCatalogoItem(multiidiomaCatalogoItem);
			}
			else {
				LOGGER.warn("catalogSearch() -> No se ha podido obtener la institución del token");
			}
		}
		else {
			LOGGER.warn("catalogSearch() -> Insuficientes datos para realizar la búsqueda de catálogos");
		}
		
		LOGGER.info("catalogSearch() -> Salida al servicio para búsqueda de catálogos de una institución por idiomas");
		return multiidiomaCatalogoDTO;
	}

	@Override
	public UpdateResponseDTO catalogUpdate(MultiidiomaCatalogoUpdateDTO multiidiomaCatalogoUpdateDTO,HttpServletRequest request) {
		LOGGER.info("catalogUpdate() -> Entrada al servicio para actualizar el idioma de traducción de un catálogo");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		GenRecursosCatalogos record = new GenRecursosCatalogos();
		GenRecursosCatalogosExample example = new GenRecursosCatalogosExample();
		
		// Conseguimos idUsuario del usuario logeado
		String token = request.getHeader("Authorization");
		// Obtenemos el DNI del token
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		//Obtenemos la institución del Token
		String idInstitucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info("catalogUpdate() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("catalogUpdate() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			
			
			if(!idInstitucion.equals(null) &&
					!multiidiomaCatalogoUpdateDTO.getIdRecurso().equals(null) && !multiidiomaCatalogoUpdateDTO.getIdRecurso().equalsIgnoreCase("") &&
					!multiidiomaCatalogoUpdateDTO.getIdLenguaje().equals(null) && !multiidiomaCatalogoUpdateDTO.getIdLenguaje().equalsIgnoreCase("") &&
					!multiidiomaCatalogoUpdateDTO.getDescripcion().equals(null) && !multiidiomaCatalogoUpdateDTO.getDescripcion().equalsIgnoreCase("") 
					) {
				
				
				
				// se actualiza descripcion, fecha modificacion y usuario modificacion
				record.setDescripcion(multiidiomaCatalogoUpdateDTO.getDescripcion());
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuario.getIdusuario());
				
				// el where sera por idRecurso, idInstitucion, idLenguaje
				example.createCriteria().andIdrecursoEqualTo(multiidiomaCatalogoUpdateDTO.getIdRecurso())
						.andIdlenguajeEqualTo(multiidiomaCatalogoUpdateDTO.getIdLenguaje())
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info("catalogUpdate() / genRecursosCatalogosExtendsMapper.updateByExampleSelective() -> Entrada a genRecursosCatalogosExtendsMapper actualizar el idioma de tradución de un catálogo");
				response = genRecursosCatalogosExtendsMapper.updateByExampleSelective(record, example);
				LOGGER.info("catalogUpdate() / genRecursosCatalogosExtendsMapper.updateByExampleSelective() -> Salida de genRecursosCatalogosExtendsMapper actualizar el idioma de tradución de un catálogo");
			}
			
		}
		else {
			LOGGER.warn("catalogUpdate() / admUsuariosExtendsMapper.selectByExample() -> Usuarios nulos o no existen en la base de datos");
			response = 0;
		}
		
		if(response == 1){
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.warn("catalogUpdate() -> OK. Se ha podido actualizar el idioma de tradución de un catálogo");
		}
		else{
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("catalogUpdate() -> KO. NO se ha podido actualizar el idioma de tradución de un catálogo");
		}
		
		LOGGER.info("catalogUpdate() -> Salida del servicio para actualizar el idioma de traducción de un catálogo");
		return updateResponseDTO;
	}

	

}
