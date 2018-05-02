package org.itcgae.siga.gen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.assertj.core.util.Strings;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuItem;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoEntity;
import org.itcgae.siga.DTOs.gen.PermisoItem;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.Converter;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmTiposacceso;
import org.itcgae.siga.db.entities.AdmTiposaccesoExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.mappers.AdmTiposaccesoMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenProcesosExtendsMapper;
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
	
	@Autowired
	GenProcesosExtendsMapper permisosMapper;
	
	@Autowired
	AdmTiposaccesoMapper tiposAccesoMapper;
	
	@Autowired
    AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	

	@Override
	public MenuDTO getMenu(HttpServletRequest request) {
		MenuDTO response = new MenuDTO();
		List<GenMenu> menuEntities = new ArrayList<GenMenu>();
		HashMap<String, GenMenu> menuMap = new HashMap<String, GenMenu>();
		String idLenguaje = new String();

		//Cargamos el Dni del Token
		String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization")).substring(0,9);
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		
		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));

		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		//Obtenemos el Usuario para comprobar todas sus instituciones
		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);
		
		if (usuarios == null || usuarios.isEmpty()) {
			Error error = new Error();
			error.setCode(400);
			error.setDescription("400");
			response.setError(error);
			return response;
		}

			List<String> idperfiles = new ArrayList<String>();

			idLenguaje = usuarios.get(0).getIdlenguaje();
			AdmPerfilExample examplePerfil = new AdmPerfilExample();
			examplePerfil.setDistinct(Boolean.TRUE);
			examplePerfil.createCriteria().andIdinstitucionEqualTo(idInstitucion);
			examplePerfil.setOrderByClause("IDPERFIL ASC");
			//Obtenemos todos los perfiles para cargar sus puntos de Menú
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
				exampleMenu.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdperfilIn(idperfiles)
				.andDerechoaccesoGreaterThan(Short.valueOf("1"));
				//Obtenemos todos los puntos de Menú
				menuEntities = menuExtend.selectMenuByExample(exampleMenu);

				/*for(GenMenu menu: menuEntities){
						menuMap.put(menu.getIdmenu(), menu);
				}
			
			

		menuEntities = new ArrayList<GenMenu>();
		menuEntities.addAll(menuMap.values());
		*/

		if (null != menuEntities && !menuEntities.isEmpty()) {
			List<MenuItem> items = new ArrayList<MenuItem>();
			List<GenMenu> rootMenus = menuEntities.stream().filter(i -> Strings.isNullOrEmpty(i.getIdparent()) || i.getIdparent().equals(" "))
					.collect(Collectors.toList());
			//Componemos el menú
				for (GenMenu dbItem : rootMenus) {
					MenuItem item = processMenu(dbItem,menuEntities,idLenguaje); 
					items.add(item);
				}

			response.setMenuItems(items);
		}

		return response;

	}
	
	
	private static MenuItem processMenu(GenMenu parent, List<GenMenu> childCandidatesList, String idLenguaje ) {
		//Realizamos la carga del menú de forma cíclica dependiende los IdParents
	    ArrayList<GenMenu> childList = new ArrayList<GenMenu>();
	    ArrayList<GenMenu> childListTwo = new ArrayList<GenMenu>();
	    MenuItem response = new MenuItem();
	    response.setLabel(parent.getIdrecurso());
	    response.setIdclass(parent.getIdclass());
	    response.setRouterLink(parent.getPath());

	    //Recorremos sus hijos
	    for (GenMenu childTransactions : childCandidatesList) {
	        childListTwo.add(childTransactions);
	        if (childTransactions.getIdparent() != null) {
	            
	            if (childTransactions.getIdparent().equalsIgnoreCase(parent.getIdmenu())){
	            	//Vamos almacenando los hijos
	            	MenuItem responsechild = new MenuItem();
	            	responsechild.setLabel(childTransactions.getIdrecurso());
	            	responsechild.setIdclass(childTransactions.getIdclass());
	            	responsechild.setRouterLink(childTransactions.getPath());
	            	response.getItems().add(responsechild);
	                childList.add(childTransactions);
	                childListTwo.remove(childTransactions);
	            }
	        }
	    }
    	List<MenuItem> responseChilds = new ArrayList<MenuItem>();


	    for (GenMenu child : childList) {
	    	//Si tenemos hijos los procesamos de forma individual para ver si tienen más hijos
	    	responseChilds.add(processMenu(child, childListTwo,idLenguaje));
	    	
	    }
	    if (null != response.getItems() && response.getItems().size() >0) {
	    	response.setItems(responseChilds);
		}else {
			response.setItems(null);
		}
	    
	    return response;

	}

	@Override
	public ComboDTO getInstituciones() {
		//Cargamos el combo de Instituciones
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
				combo.setValue(cenInstitucion.getIdinstitucion().toString());
				if (null != cenInstitucion.getFechaenproduccion()) {

					combo.setLabel(cenInstitucion.getAbreviatura() + " (En producción: "
							+ Converter.dateToString(cenInstitucion.getFechaenproduccion()) + ")");
				} else {
					combo.setLabel(cenInstitucion.getAbreviatura());
				}

				combos.add(combo);
			}

		}
		
		response.setCombooItems(combos);
		return response;
	}

	@Override
	public ComboDTO getPerfiles(String idInstitucion) {
		//Cargamos el combo de Perfil
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
				combo.setValue(admPerfil.getIdperfil().toString());
				combo.setLabel(admPerfil.getDescripcion());
				combos.add(combo);
			}

		}
		
		response.setCombooItems(combos);
		return response;
	}


	@Override
	public PermisoDTO getPermisos(PermisoRequestItem permisoRequestItem,HttpServletRequest request) {
		PermisoDTO permisoResponse = new PermisoDTO();
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));

		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));
		
		permisoRequestItem.setIdInstitucion(String.valueOf(idInstitucion));
		List<PermisoEntity> permisosEntity = this.permisosMapper.getProcesosPermisos(permisoRequestItem);
		

		
		
		if (null != permisosEntity && !permisosEntity.isEmpty()) {
			List<PermisoItem> items = new ArrayList<PermisoItem>();
			List<PermisoEntity> rootPermisos = permisosEntity.stream().filter(i -> Strings.isNullOrEmpty(i.getParent()) || i.getParent().equals("0"))
					.collect(Collectors.toList());
			//Componemos el árbol de permisos
				for (PermisoEntity dbItem : rootPermisos) {
					PermisoItem item = processPermisos(dbItem,permisosEntity); 
					items.add(item);
				}

				permisoResponse.setPermisoItems(items);
		}
		
		
		
		
		return permisoResponse;
	}


	private PermisoItem processPermisos(PermisoEntity parent, List<PermisoEntity> childCandidatesList) {
		//Realizamos la carga de la gestión de permisos de forma cíclica dependiende los IdParents
	    ArrayList<PermisoEntity> childList = new ArrayList<PermisoEntity>();
	    ArrayList<PermisoEntity> childListTwo = new ArrayList<PermisoEntity>();
	    PermisoItem response = new PermisoItem();
	    response.setLabel(parent.getLabel());
	    response.setData(parent.getData());
	    response.setDerechoacceso(parent.getDerechoacceso());

	    //Recorremos sus hijos
	    for (PermisoEntity childTransactions : childCandidatesList) {
	        childListTwo.add(childTransactions);
	        if (childTransactions.getParent() != null) {
	            
	            if (childTransactions.getParent().equalsIgnoreCase(parent.getData())){
	            	//Vamos almacenando los hijos
	            	PermisoItem responsechild = new PermisoItem();
	            	responsechild.setLabel(childTransactions.getLabel());
	            	responsechild.setData(childTransactions.getData());
	            	responsechild.setDerechoacceso(childTransactions.getDerechoacceso());
	            	response.getChildren().add(responsechild);
	                childList.add(childTransactions);
	                childListTwo.remove(childTransactions);
	            }
	        }
	    }
    	List<PermisoItem> responseChilds = new ArrayList<PermisoItem>();


	    for (PermisoEntity child : childList) {
	    	//Si tenemos hijos los procesamos de forma individual para ver si tienen más hijos
	    	responseChilds.add(processPermisos(child, childListTwo));
	    	
	    }
	    if (null != response.getChildren() && response.getChildren().size() >0) {
	    	response.setChildren(responseChilds);
		}else {
			response.setChildren(null);
		}
	    
	    return response;

	}


	@Override
	public UpdateResponseDTO updatePermisos(PermisoUpdateItem permisoRequestItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		UpdateResponseDTO response = new UpdateResponseDTO();
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		String dni = nifInstitucion.substring(0,9);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));
		

		//Buscamos el perfil para ver si ya existe. En caso de que no exista
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);

		if (null != permisoRequestItem ) {

				
				AdmTiposacceso record = new AdmTiposacceso();
				record.setDerechoacceso(Short.valueOf(permisoRequestItem.getDerechoacceso()));
				record.setFechamodificacion(new Date());
				record.setIdperfil(permisoRequestItem.getIdGrupo());
				record.setIdproceso(permisoRequestItem.getId());
				record.setIdinstitucion(Short.valueOf(institucion));
				record.setUsumodificacion(usuario.getIdusuario());
				this.tiposAccesoMapper.updateByPrimaryKey(record );

				
				
			
		}
		response.setStatus(SigaConstants.OK);
		
		return response;
		
	}


	@Override
	public UsuarioLogeadoDTO getUserLog(HttpServletRequest request) {

		
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String institucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());

		
		
		List<UsuarioLogeadoItem> usuario = this.admUsuariosExtendsMapper.getUsersLog(request);
		UsuarioLogeadoDTO response = new UsuarioLogeadoDTO();
		
		response.setUsuarioLogeadoItem(usuario);
		
		for (UsuarioLogeadoItem usuarioLogeadoItem : usuario) {
			
			
			AdmUsuarios record =  new AdmUsuarios();
			
			record.setIdinstitucion(Short.valueOf(institucion));
			record.setIdusuario(usuarioLogeadoItem.getIdUsuario());
			record.setUltimaConexion(new Date());
			this.admUsuariosExtendsMapper.updateByPrimaryKeySelective(record );
		}
		return response;
	}


	@Override
	public PermisoDTO getAccessControl(ControlRequestItem controlItem, HttpServletRequest request) {
		
		PermisoDTO response= new PermisoDTO();
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String[] listParameters = nifInstitucion.split("-");
		controlItem.setInstitucion(listParameters[2]);
		controlItem.setIdGrupo(listParameters[1]);
		List<PermisoEntity> permisos =  this.admUsuariosExtendsMapper.getAccessControls(controlItem);
		if (null != permisos && permisos.size()>0) {
			List<PermisoItem> permisosItem = new ArrayList<PermisoItem>();
			for (PermisoEntity permisoEntity : permisos) {
				PermisoItem permisoItem = new PermisoItem();
				permisoItem.setDerechoacceso(permisoEntity.getDerechoacceso());
				permisosItem.add(permisoItem);
			}
			response.setPermisoItems(permisosItem);
		}
		return response;
	}

}
