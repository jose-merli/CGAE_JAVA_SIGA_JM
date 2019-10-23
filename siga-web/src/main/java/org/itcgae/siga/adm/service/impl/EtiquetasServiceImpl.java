package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.EtiquetaDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaItem;
import org.itcgae.siga.DTOs.adm.EtiquetaSearchDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IEtiquetasService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionLenguajes;
import org.itcgae.siga.db.entities.CenInstitucionLenguajesExample;
import org.itcgae.siga.db.entities.CenInstitucionLenguajesKey;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.mappers.CenInstitucionLenguajesMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtiquetasServiceImpl implements IEtiquetasService{

	private Logger LOGGER = Logger.getLogger(EtiquetasServiceImpl.class);
	
	@Autowired
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenInstitucionLenguajesMapper cenInstitucionLenguajesMapper;
	
	@Override
	public ComboDTO getLabelLenguage() {
		
		LOGGER.info("getLabelLenguage() -> Entrada al servicio para obtener los idiomas disponibles");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		LOGGER.info("getLabelLenguage() / genDiccionarioExtendsMapper.getLabelLenguage() -> Entrada a genDiccionarioExtendsMapper para obtener lista de idiomas disponibles en la aplicación");
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
		LOGGER.info("getLabelLenguage() / genDiccionarioExtendsMapper.getLabelLenguage() -> Salida de genDiccionarioExtendsMapper para obtener lista de idiomas disponibles en la aplicación");
		
		if(null != combooItems && combooItems.size() > 0){
			// primer elemento a vacio para componente de pantalla dropdown
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			combooItems.add(0,comboItem);
			comboDTO.setCombooItems(combooItems);
		}
		else {
			LOGGER.warn("getLabelLenguage() / genDiccionarioExtendsMapper.getLabelLenguage() -> No hay idiomas disponibles en la base de datos");
		}
		
		
		LOGGER.info("getLabelLenguage() -> Salida del servicio para obtener los idiomas disponibles");
		return comboDTO;
	}

	@Override
	public EtiquetaDTO searchLabels(int numPagina, EtiquetaSearchDTO etiquetaSearchDTO, HttpServletRequest request) {
		LOGGER.info("searchLabels() -> Entrada al servicio para buscar las etiquetas de la aplicación");
		EtiquetaDTO etiquetaDTO = new EtiquetaDTO();
		List<EtiquetaItem> etiquetaItems =  new ArrayList<EtiquetaItem>();
		
		if(!etiquetaSearchDTO.getIdiomaBusqueda().equals(null) && !etiquetaSearchDTO.getIdiomaTraduccion().equals(null)) {
			
			LOGGER.info("searchLabels() / genDiccionarioExtendsMapper.searchLabels() -> Entrada a genDiccionarioExtendsMapper para obtener lista de etiquetas disponibles");
			etiquetaItems = genDiccionarioExtendsMapper.searchLabels(numPagina, etiquetaSearchDTO);
			LOGGER.info("searchLabels() / genDiccionarioExtendsMapper.searchLabels() -> Salida de genDiccionarioExtendsMapper para obtener lista de etiquetas disponibles");
			if(null != etiquetaItems && etiquetaItems.size() > 0) {
				etiquetaDTO.setEtiquetaItem(etiquetaItems);
			}
			else {
				LOGGER.warn("searchLabels() / genDiccionarioExtendsMapper.searchLabels() -> No hay etiquetas disponibles en la base de datos");
			}
		}
		else {
			LOGGER.warn("searchLabels() -> No hay disponible un idioma de busqueda y/o de traducción");
		}
		
		LOGGER.info("searchLabels() -> Salida del servicio para buscar las etiquetas de la aplicación");
		return etiquetaDTO;
	}

	@Override
	public UpdateResponseDTO updateLabel(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request) {
		LOGGER.info("updateLabel() -> Entrada al servicio para actualizar las etiquetas de la aplicación");
		int response = 0;
		GenDiccionario record = new GenDiccionario();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Conseguimos idUsuario del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info("updateLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("updateLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		AdmUsuarios usuario = usuarios.get(0);
		
		if(null != usuarios && usuarios.size() > 0) {
			if(null != usuario && !usuario.getIdusuario().equals(null) && 
					!etiquetaUpdateDTO.getIdLenguaje().equals(null) && !etiquetaUpdateDTO.getIdLenguaje().equalsIgnoreCase("") &&
					!etiquetaUpdateDTO.getIdRecurso().equals(null) && !etiquetaUpdateDTO.getIdRecurso().equalsIgnoreCase("")
					) {
				record.setDescripcion(etiquetaUpdateDTO.getDescripcion());
				record.setIdlenguaje(etiquetaUpdateDTO.getIdLenguaje());
				record.setIdrecurso(etiquetaUpdateDTO.getIdRecurso());
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
				
				LOGGER.info("updateLabel() / genDiccionarioExtendsMapper.updateByPrimaryKeySelective() -> Entrada a genDiccionarioExtendsMapper para actualizar etiqueta");
				response  = genDiccionarioExtendsMapper.updateByPrimaryKeySelective(record);
				LOGGER.info("updateLabel() / genDiccionarioExtendsMapper.updateByPrimaryKeySelective() -> Salida de genDiccionarioExtendsMapper para actualizar etiqueta");
			}
			else {
				LOGGER.warn("updateLabel() -> No existe usuario y/o idusuario logeado. Tampoco se ha seleccionado un recurso y lenguaje al que actualizar la etiqueta de la aplicación");
			}
		}
		else {
			LOGGER.info("updateLabel() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
		}
		
		
		// respuesta si se actualiza correctamente
		if(response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("updateLabel() / genDiccionarioExtendsMapper.updateByPrimaryKeySelective()-> OK. Etiqueta actualizada correctamente");
		}
			
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateLabel() / genDiccionarioExtendsMapper.updateByPrimaryKeySelective()-> KO. Etiqueta NO actualizada correctamente");
		}
			
		
		LOGGER.info("updateLabel() -> Salida del servicio para actualizar las etiquetas de la aplicación");
		return updateResponseDTO;
	}

	@Override
	public ComboDTO getLabelLenguageFiltered(HttpServletRequest request) {
		
		LOGGER.info("getLabelLenguage() -> Entrada al servicio para obtener los idiomas disponibles");
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		CenInstitucionLenguajesExample example = new CenInstitucionLenguajesExample();
		example.createCriteria().andIdinstitucionEqualTo(idInstitucion);
		List<CenInstitucionLenguajes> lenguajes = cenInstitucionLenguajesMapper.selectByExample(example);
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
	
		if(null != combooItems && combooItems.size() > 0){
			// primer elemento a vacio para componente de pantalla dropdown
//			comboItem.setLabel("");
//			comboItem.setValue("");
			List<ComboItem> comboFiltrado = new ArrayList<ComboItem>();
//			comboFiltrado.add(0,comboItem);
			
			for (ComboItem item : combooItems) {
				for (CenInstitucionLenguajes lenguaje : lenguajes) {
					if(item.getValue().equals(lenguaje.getIdlenguaje())){
						comboFiltrado.add(item);
					}
				}
			}
			
			comboDTO.setCombooItems(comboFiltrado);
		}
		else {
			LOGGER.warn("getLabelLenguage() / genDiccionarioExtendsMapper.getLabelLenguage() -> No hay idiomas disponibles en la base de datos");
		}
		
		
		LOGGER.info("getLabelLenguage() -> Salida del servicio para obtener los idiomas disponibles");
		return comboDTO;
	}

}
