package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoItem;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IMultiidiomaCatalogosService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiidiomaCatalogosServiceImpl implements IMultiidiomaCatalogosService {

	@Autowired
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;
	
	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Override
	public ComboDTO getCatalogLenguage() {
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
		// primer elemento a vacio para componente de pantalla dropdown
		comboItem.setLabel("");
		comboItem.setValue("");
		combooItems.add(0,comboItem);
		comboDTO.setCombooItems(combooItems);
		
		return comboDTO;
	}

	@Override
	public ComboDTO getCatalogEntity(HttpServletRequest request) {
		
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		ComboItem comboItem = new ComboItem();
		// obtener idInstitucion del usuario logeado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String idInstitucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		
		if(!idInstitucion.equals(null)) {
			combooItems = genRecursosCatalogosExtendsMapper.getCatalogEntity(idInstitucion);
			// primer elemento a vacio para componente de pantalla dropdown
			comboItem.setLabel("");
			comboItem.setValue("");
			combooItems.add(0,comboItem);
			comboDTO.setCombooItems(combooItems);
			
			
		}
		
		return comboDTO;
	}

	@Override
	public MultiidiomaCatalogoDTO catalogSearch(int numPagina,
			MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO, HttpServletRequest request) {
		
		List<MultiidiomaCatalogoItem> multiidiomaCatalogoItem = new ArrayList<MultiidiomaCatalogoItem>();
		MultiidiomaCatalogoDTO multiidiomaCatalogoDTO =  new MultiidiomaCatalogoDTO();
		
		// obtener idInstitucion del usuario logeado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String idInstitucion = nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length());
		
		if(!multiidiomaCatalogoSearchDTO.getIdiomaBusqueda().equals(null) &&  !multiidiomaCatalogoSearchDTO.getIdiomaBusqueda().equalsIgnoreCase("") &&
				!multiidiomaCatalogoSearchDTO.getIdiomaTraduccion().equals(null) && !multiidiomaCatalogoSearchDTO.getIdiomaTraduccion().equalsIgnoreCase("") &&
				!multiidiomaCatalogoSearchDTO.getNombreTabla().equals(null) && !multiidiomaCatalogoSearchDTO.getNombreTabla().equalsIgnoreCase("")){
			
			if(!idInstitucion.equals(null)) {
				multiidiomaCatalogoItem = genRecursosCatalogosExtendsMapper.getCatalogSearch(numPagina, multiidiomaCatalogoSearchDTO, idInstitucion);
				multiidiomaCatalogoDTO.setMultiidiomaCatalogoItem(multiidiomaCatalogoItem);
			}
		}
		return multiidiomaCatalogoDTO;
	}

	@Override
	public UpdateResponseDTO catalogUpdate(MultiidiomaCatalogoUpdateDTO multiidiomaCatalogoUpdateDTO,HttpServletRequest request) {

		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		GenRecursosCatalogos record = new GenRecursosCatalogos();
		GenRecursosCatalogosExample example = new GenRecursosCatalogosExample();
		
		// Conseguimos idUsuario del usuario logeado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);
		String idInstitucion = nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length());
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
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
			response = genRecursosCatalogosExtendsMapper.updateByExampleSelective(record, example);
		}
		
		
		if(response == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");
		
		return updateResponseDTO;
	}

	

}
