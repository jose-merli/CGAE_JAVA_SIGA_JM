package org.itcgae.siga.gen.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Strings;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuItem;
import org.itcgae.siga.commons.utils.Converter;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmTiposaccesoExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	GenMenuExtendsMapper menuExtend;
	
	@Autowired
	CenInstitucionExtendsMapper institucionMapper;
	
	@Autowired
	AdmPerfilExtendsMapper perfilMapper;
	
	@Autowired
	AdmUsuariosMapper usuarioMapper;

	@Override
	public MenuDTO getMenu(HttpServletRequest request) {
		MenuDTO response = new MenuDTO();
		List<GenMenu> menuEntities = new ArrayList<GenMenu>();
		HashMap<String, GenMenu> menuMap = new HashMap<String, GenMenu>();
		String idLenguaje = new String();


		String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		

		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni);
		
		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);
		
		if (usuarios == null || usuarios.isEmpty()) {
			Error error = new Error();
			error.setCode(400);
			error.setDescription("400");
			response.setError(error);
			return response;
		}
			List<Short> instituciones = new ArrayList<Short>();
			List<String> idperfiles = new ArrayList<String>();
			for (AdmUsuarios admUsuarios : usuarios) {
				instituciones.add(admUsuarios.getIdinstitucion());
			}
			idLenguaje = usuarios.get(0).getIdlenguaje();
			AdmPerfilExample examplePerfil = new AdmPerfilExample();
			examplePerfil.setDistinct(Boolean.TRUE);
			examplePerfil.createCriteria().andIdinstitucionIn(instituciones);
			examplePerfil.setOrderByClause("IDPERFIL ASC");
			List<AdmPerfil> perfiles = perfilMapper.selectByExample(examplePerfil);

			if (perfiles == null) {
				Error error = new Error();
				error.setCode(400);
				error.setDescription("400");
				response.setError(error);
				return response;
			}
			for(AdmPerfil perfil:perfiles){
				idperfiles.add(perfil.getIdperfil());
			}
				AdmTiposaccesoExample exampleMenu = new AdmTiposaccesoExample();

				exampleMenu.setDistinct(true);
				exampleMenu.setOrderByClause(" MENU.ORDEN ASC");
				exampleMenu.createCriteria().andIdinstitucionIn(instituciones).andIdperfilIn(idperfiles)
				.andDerechoaccesoGreaterThan(Short.valueOf("1"));
				menuEntities = menuExtend.selectMenuByExample(exampleMenu);

				for(GenMenu menu: menuEntities){
						menuMap.put(menu.getIdmenu(), menu);
				}
			
			

		menuEntities = new ArrayList<GenMenu>();
		menuEntities.addAll(menuMap.values());
		

		if (null != menuEntities && !menuEntities.isEmpty()) {
			List<MenuItem> items = new ArrayList<MenuItem>();
			List<GenMenu> rootMenus = menuEntities.stream().filter(i -> Strings.isNullOrEmpty(i.getIdparent()) || i.getIdparent().equals(" "))
					.collect(Collectors.toList());
			
				for (GenMenu dbItem : rootMenus) {
					MenuItem item = processMenu(dbItem,menuEntities,idLenguaje); 
					items.add(item);
				}

			response.setMenuItems(items);
		}

		return response;

	}
	
	
	private static MenuItem processMenu(GenMenu parent, List<GenMenu> childCandidatesList, String idLenguaje ) {
	    ArrayList<GenMenu> childList = new ArrayList<GenMenu>();
	    ArrayList<GenMenu> childListTwo = new ArrayList<GenMenu>();
	    MenuItem response = new MenuItem();
	    response.setLabel(parent.getIdrecurso());
	    response.setRouterLink(parent.getIdrecurso());
	    //response.setRouterLink(parent.getIdrecurso());
	    for (GenMenu childTransactions : childCandidatesList) {
	        childListTwo.add(childTransactions);
	        if (childTransactions.getIdparent() != null) {
	            
	            if (childTransactions.getIdparent().equalsIgnoreCase(parent.getIdmenu())){
	            	MenuItem responsechild = new MenuItem();
	            	responsechild.setLabel(childTransactions.getIdrecurso());
	            	responsechild.setRouterLink(childTransactions.getIdrecurso());
	            	response.getItems().add(responsechild);
	                childList.add(childTransactions);
	                childListTwo.remove(childTransactions);
	            }
	        }
	    }
    	List<MenuItem> responseChilds = new ArrayList<MenuItem>();


	    for (GenMenu child : childList) {
	    	
	    	responseChilds.add(processMenu(child, childListTwo,idLenguaje));
	    	
	    }
	    response.setItems(responseChilds);
	    return response;

	}

	@Override
	public ComboDTO getInstituciones() {
		ComboDTO response = new ComboDTO();
		
		CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
		exampleInstitucion.setDistinct(true);
		exampleInstitucion.setOrderByClause("ABREVIATURA ASC");

		List<CenInstitucion> instituciones = institucionMapper.selectByExample(exampleInstitucion);
		List<ComboItem> combos = new ArrayList<ComboItem>();
		if (null != instituciones && instituciones.size() > 0) {
			for (Iterator<CenInstitucion> iterator = instituciones.iterator(); iterator.hasNext();) {
				CenInstitucion cenInstitucion = (CenInstitucion) iterator.next();
				ComboItem combo = new ComboItem();
				combo.setId(cenInstitucion.getIdinstitucion().toString());
				if (null != cenInstitucion.getFechaenproduccion()) {

					combo.setValue(cenInstitucion.getAbreviatura() + " (En producción: "
							+ Converter.dateToString(cenInstitucion.getFechaenproduccion()) + ")");
				} else {
					combo.setValue(cenInstitucion.getAbreviatura());
				}

				combos.add(combo);
			}

		}
		
		response.setCombooItems(combos);
		return response;
	}

	@Override
	public ComboDTO getPerfiles(String idInstitucion) {
		ComboDTO response = new ComboDTO();
		
		AdmPerfilExample examplePerfil = new AdmPerfilExample();
		examplePerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		examplePerfil.setOrderByClause("IDPERFIL ASC");
		List<AdmPerfil> perfiles = perfilMapper.selectComboPerfilByExample(examplePerfil);
		List<ComboItem> combos = new ArrayList<ComboItem>();
		if (null != perfiles && perfiles.size() > 0) {
			for (Iterator<AdmPerfil> iterator = perfiles.iterator(); iterator.hasNext();) {
				AdmPerfil admPerfil = (AdmPerfil) iterator.next();
				ComboItem combo = new ComboItem();
				combo.setId(admPerfil.getIdperfil().toString());
				combo.setValue(admPerfil.getDescripcion());
				combos.add(combo);
			}

		}
		
		response.setCombooItems(combos);
		return response;
	}

}
