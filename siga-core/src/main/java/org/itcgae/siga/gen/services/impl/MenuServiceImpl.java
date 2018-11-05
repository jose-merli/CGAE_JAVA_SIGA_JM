package org.itcgae.siga.gen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.assertj.core.util.Strings;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.EntornoDTO;
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
import org.itcgae.siga.commons.utils.InvalidClientCerticateException;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmGestorinterfaz;
import org.itcgae.siga.db.entities.AdmGestorinterfazExample;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmPerfilKey;
import org.itcgae.siga.db.entities.AdmTiposacceso;
import org.itcgae.siga.db.entities.AdmTiposaccesoKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfil;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.entities.GenMenuExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.AdmGestorinterfazMapper;
import org.itcgae.siga.db.mappers.AdmPerfilMapper;
import org.itcgae.siga.db.mappers.AdmTiposaccesoMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenMenuMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenProcesosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;





@Service
public class MenuServiceImpl implements IMenuService {
	
	Logger LOGGER = Logger.getLogger(MenuServiceImpl.class);

	@Autowired
	private GenMenuExtendsMapper menuExtend;
	
	@Autowired
	private GenMenuMapper menuMapper;
	
	
	@Autowired
	private CenInstitucionExtendsMapper institucionMapper;
	
	@Autowired
	private AdmPerfilExtendsMapper perfilMapper;
	
	@Autowired
	private AdmUsuariosMapper usuarioMapper;
	
	@Autowired
	private GenProcesosExtendsMapper permisosMapper;
	
	@Autowired
	private AdmTiposaccesoMapper tiposAccesoMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private AdmUsuariosEfectivosPerfilMapper admUsuariosEfectivoMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private AdmGestorinterfazMapper admGestorinterfazMapper;

	@Autowired 
	AdmConfigMapper admConfigMapper;
	
	@Autowired 
	AdmPerfilMapper adminPerfilMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
	@Autowired
	private CenClienteMapper cenClienteMapper;
	
	@Override
	public MenuDTO getMenu(HttpServletRequest request) {
		MenuDTO response = new MenuDTO();
		List<GenMenu> menuEntities = new ArrayList<GenMenu>();
		String idLenguaje = new String();

		// Cargamos el Dni del Token
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);

		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		// Obtenemos el Usuario para comprobar todas sus instituciones
		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);

		if (usuarios == null || usuarios.isEmpty()) {
			Error error = new Error();
			error.setCode(400);
			error.setDescription("400");
			response.setError(error);
			return response;
		}

		

		idLenguaje = usuarios.get(0).getIdlenguaje();

		// Obtenemos todos los perfiles del Usuario para cargar sus puntos de
		// Menú
		if (perfiles == null) {
			Error error = new Error();
			error.setCode(400);
			error.setDescription("400");
			response.setError(error);
			return response;
		}
		
		
		String idPerfiles = "";
		for(int i=0 ;i< perfiles.size(); i++) {
			String contructPerfil = "";
			if(perfiles.size() == 1) {
				contructPerfil += perfiles.get(i);
				
			}
			else {
				if(i != perfiles.size()-1) {
					contructPerfil += perfiles.get(i);
				
					contructPerfil += ",";
					
				}
				else {
					contructPerfil += perfiles.get(i);
				
				}
				
			}
			idPerfiles+=contructPerfil;
		}

		// Obtenemos todos los puntos de Menú
		menuEntities = menuExtend.selectMenuByExample(String.valueOf(idInstitucion), idPerfiles);

		if (null != menuEntities && !menuEntities.isEmpty()) {
			// Componemos el menú
			Boolean tieneRuedaConf = Boolean.FALSE;
			Boolean tieneMenuConfi = Boolean.FALSE;
			for (GenMenu menu : menuEntities) {
				if (menu.getIdrecurso().equals("menu.configuracion")) {
					tieneRuedaConf = Boolean.TRUE;
					break;
				}else if(menu.getIdrecurso().equals("menu.administracion") || menu.getIdrecurso().equals("menu.administracion.gestionCatalogosMaestros")) {
					tieneMenuConfi= Boolean.TRUE;
				}

			}
			if (!tieneRuedaConf && tieneMenuConfi) {
				GenMenuExample exampleMenu = new GenMenuExample();
				exampleMenu.createCriteria().andIdrecursoEqualTo("menu.configuracion");
				List<GenMenu> menuConfig = menuMapper.selectByExample(exampleMenu );
				if (null != menuConfig && menuConfig.size()>0) {
					menuEntities.add(menuConfig.get(0));
					tieneRuedaConf = Boolean.TRUE;
					Collections.sort(menuEntities, new Comparator<GenMenu>() {
						@Override
						public int compare(GenMenu o1, GenMenu o2) {

							return new Long(o1.getOrden()).compareTo(new Long(o2.getOrden()));
						}

					});
				}
			}
			
			List<MenuItem> items = new ArrayList<MenuItem>();
			List<GenMenu> rootMenus = menuEntities.stream()
					.filter(i -> Strings.isNullOrEmpty(i.getIdparent()) || i.getIdparent().equals(" "))
					.collect(Collectors.toList());
			int posicionAInsertar = 0;
			// Componemos el menú
			for (GenMenu dbItem : rootMenus) {
				MenuItem item = processMenu(dbItem, menuEntities, idLenguaje);
				items.add(item);
			}
			List<String> idRecursos = new ArrayList<String>();
			int i = 0;
			for (MenuItem dbItem : items) {
				if (tieneRuedaConf) {
					if (dbItem.getLabel().equals("menu.configuracion")) {
						posicionAInsertar = i;
					}
				}
				idRecursos.addAll(recuperaridRecursos(dbItem));
				i++;

			}
			for (GenMenu menu : menuEntities) {
				if (!idRecursos.contains(menu.getIdrecurso())) {
					MenuItem menuItem = new MenuItem();
					menuItem.setLabel(menu.getIdrecurso());
					menuItem.setIdclass(menu.getIdclass());
					menuItem.setRouterLink(menu.getPath());
					menuItem.setItems(null);
					if (tieneRuedaConf) {
						items.add(posicionAInsertar,menuItem);
						posicionAInsertar++;
					}else{
						items.add(menuItem);
					}
				}

			}
			
			
	
			
			response.setMenuItems(items);
		}

		return response;

	}

	private Collection<String> recuperaridRecursos(MenuItem dbItem) {
		Collection<String> ids = new ArrayList<String>();
		ids.add(dbItem.getLabel());
		if (null != dbItem.getItems() && dbItem.getItems().size()>0) {
			for (MenuItem dbItemHijo : dbItem.getItems()) {
				ids.addAll(recuperaridRecursos(dbItemHijo));
				
			}
		}
		
		return ids;
	}

	private static MenuItem processMenu(GenMenu parent, List<GenMenu> childCandidatesList, String idLenguaje) {
		// Realizamos la carga del menú de forma cíclica dependiendo los
		// IdParents
		ArrayList<GenMenu> childList = new ArrayList<GenMenu>();
		ArrayList<GenMenu> childListTwo = new ArrayList<GenMenu>();
		MenuItem response = new MenuItem();
		response.setLabel(parent.getIdrecurso());
		response.setIdclass(parent.getIdclass());
		response.setRouterLink(parent.getPath());
		

		// Recorremos sus hijos
		for (GenMenu childTransactions : childCandidatesList) {
			childListTwo.add(childTransactions);
			if (childTransactions.getIdparent() != null) {

				if (childTransactions.getIdparent().equalsIgnoreCase(parent.getIdmenu())) {
					// Vamos almacenando los hijos
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
			// Si tenemos hijos los procesamos de forma individual para ver si
			// tienen más hijos
			responseChilds.add(processMenu(child, childListTwo, idLenguaje));

		}
		if (null != response.getItems() && response.getItems().size() > 0) {
			response.setItems(responseChilds);
		} else {
			response.setItems(null);
		}

		return response;

	}

	
	
	@Override
	public ComboDTO getInstituciones(HttpServletRequest request) {
		// Cargamos el combo de Instituciones
		ComboDTO response = new ComboDTO();

		CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
		exampleInstitucion.setDistinct(true);
		exampleInstitucion.setOrderByClause("ABREVIATURA ASC");

		List<CenInstitucion> instituciones = institucionMapper.selectByExample(exampleInstitucion);
		List<ComboItem> combos = new ArrayList<ComboItem>();
		ComboItem comboBlanco = new ComboItem();
		comboBlanco.setValue("");
		comboBlanco.setLabel("");
		combos.add(comboBlanco);
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
		// Cargamos el combo de Perfil
		ComboDTO response = new ComboDTO();

		AdmPerfilExample examplePerfil = new AdmPerfilExample();
		examplePerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechaBajaIsNull();
		examplePerfil.setOrderByClause(" DESCRIPCION ASC ");
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
	public PermisoDTO getPermisos(PermisoRequestItem permisoRequestItem, HttpServletRequest request)  {
		PermisoDTO permisoResponse = new PermisoDTO();
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String institucion = null;
		try {
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
			String commonName = null;
			String organizationName = null;
			String organizationNameNuevo = null;
			
			X509Certificate cert = null;
			if (null != certs && certs.length > 0) {
				cert = certs[0];
				try {
					X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();

					if (x500name.getAttributeTypes()[7].getId().equals("1.3.6.1.4.1.16533.30.3")) {
						RDN institucionnuevo = x500name.getRDNs(x500name.getAttributeTypes()[7])[0];
						organizationNameNuevo = IETFUtils.valueToString(institucionnuevo.getFirst().getValue());
					}else{
						RDN institucionRdn = x500name.getRDNs(BCStyle.O)[0];
						organizationName = IETFUtils.valueToString(institucionRdn.getFirst().getValue());
					}



					LOGGER.debug("Common Name: " + commonName);
					LOGGER.debug("Organization Name: " + organizationName);
				} catch (NoSuchElementException e) {
					throw new InvalidClientCerticateException(e);
				}
				
				if (null != organizationNameNuevo) {
					institucion = organizationNameNuevo.substring(0,
								4);
				}else{
					institucion = organizationName.substring(organizationName.length() - 4,
						organizationName.length());
				}

				LOGGER.debug("INSTITUCION: " + institucion);

				
			}
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}

		permisoRequestItem.setIdInstitucion(String.valueOf(idInstitucion));
		permisoRequestItem.setIdInstitucionCertificado(institucion);
		List<PermisoEntity> permisosEntity = this.permisosMapper.getProcesosPermisos(permisoRequestItem);

		if (null != permisosEntity && !permisosEntity.isEmpty()) {
			List<PermisoItem> items = new ArrayList<PermisoItem>();
			List<PermisoEntity> rootPermisos = permisosEntity.stream()
					.filter(i -> Strings.isNullOrEmpty(i.getParent()) || i.getParent().equals("0"))
					.collect(Collectors.toList());
			// Componemos el árbol de permisos
			for (PermisoEntity dbItem : rootPermisos) {
				PermisoItem item = processPermisos(dbItem, permisosEntity);
				items.add(item);
			}

			permisoResponse.setPermisoItems(items);
		}

		return permisoResponse;
	}

	private PermisoItem processPermisos(PermisoEntity parent, List<PermisoEntity> childCandidatesList) {
		// Realizamos la carga de la gestión de permisos de forma cíclica
		// dependiende los IdParents
		ArrayList<PermisoEntity> childList = new ArrayList<PermisoEntity>();
		ArrayList<PermisoEntity> childListTwo = new ArrayList<PermisoEntity>();
		PermisoItem response = new PermisoItem();
		response.setLabel(parent.getLabel());
		response.setData(parent.getData());
		response.setDerechoacceso(parent.getDerechoacceso());

		// Recorremos sus hijos
		for (PermisoEntity childTransactions : childCandidatesList) {
			childListTwo.add(childTransactions);
			if (childTransactions.getParent() != null) {

				if (childTransactions.getParent().equalsIgnoreCase(parent.getData())) {
					// Vamos almacenando los hijos
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
			// Si tenemos hijos los procesamos de forma individual para ver si
			// tienen más hijos
			responseChilds.add(processPermisos(child, childListTwo));

		}
		if (null != response.getChildren() && response.getChildren().size() > 0) {
			response.setChildren(responseChilds);
		} else {
			response.setChildren(null);
		}

		return response;

	}

	@Override
	public UpdateResponseDTO updatePermisos(PermisoUpdateItem permisoRequestItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		UpdateResponseDTO response = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short institucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(institucion));

		// Buscamos el perfil para ver si ya existe. En caso de que no exista
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);

		if (null != permisoRequestItem) {

			AdmTiposaccesoKey key = new AdmTiposaccesoKey();
			key.setIdperfil(permisoRequestItem.getIdGrupo());
			key.setIdproceso(permisoRequestItem.getId());
			key.setIdinstitucion(Short.valueOf(institucion));
			AdmTiposacceso acceso = this.tiposAccesoMapper.selectByPrimaryKey(key);

			if (acceso != null) {
				AdmTiposacceso record = new AdmTiposacceso();
				record.setDerechoacceso(Short.valueOf(permisoRequestItem.getDerechoacceso()));
				record.setFechamodificacion(new Date());
				record.setIdperfil(permisoRequestItem.getIdGrupo());
				record.setIdproceso(permisoRequestItem.getId());
				record.setIdinstitucion(Short.valueOf(institucion));
				record.setUsumodificacion(usuario.getIdusuario());
				this.tiposAccesoMapper.updateByPrimaryKey(record);
			} else {
				AdmTiposacceso record = new AdmTiposacceso();
				record.setDerechoacceso(Short.valueOf(permisoRequestItem.getDerechoacceso()));
				record.setFechamodificacion(new Date());
				record.setIdperfil(permisoRequestItem.getIdGrupo());
				record.setIdproceso(permisoRequestItem.getId());
				record.setIdinstitucion(Short.valueOf(institucion));
				record.setUsumodificacion(usuario.getIdusuario());
				this.tiposAccesoMapper.insert(record);
			}

		}
		response.setStatus(SigaConstants.OK);

		return response;

	}

	@Override
	public UsuarioLogeadoDTO getUserLog(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		List<UsuarioLogeadoItem> usuario = this.admUsuariosExtendsMapper.getUsersLog(request);
		UsuarioLogeadoDTO response = new UsuarioLogeadoDTO();
		usuario.get(0).setPerfiles(getDescripcion(perfiles, idInstitucion));
		response.setUsuarioLogeadoItem(usuario);

		for (UsuarioLogeadoItem usuarioLogeadoItem : usuario) {

			AdmUsuarios record = new AdmUsuarios();

			record.setIdinstitucion(Short.valueOf(idInstitucion));
			record.setIdusuario(usuarioLogeadoItem.getIdUsuario());
			record.setUltimaConexion(new Date());
			this.admUsuariosExtendsMapper.updateByPrimaryKeySelective(record);
		}
		return response;
	}

	private String getDescripcion(List<String> perfiles, Short idInstitucion) {
	
		String descripcionPerfil = "";
		
		for (String string : perfiles) {
			AdmPerfilKey adminPerfilKey = new AdmPerfilKey();
			adminPerfilKey.setIdinstitucion(idInstitucion);
			adminPerfilKey.setIdperfil(string.replace("'",""));
			
			AdmPerfil adminPerfil = this.adminPerfilMapper.selectByPrimaryKey(adminPerfilKey);
			descripcionPerfil += adminPerfil.getDescripcion() + ", ";
		}
		
		
		return descripcionPerfil.substring(0, descripcionPerfil.length() - 2);
	}
	
	@Override
	public PermisoDTO getAccessControl(ControlRequestItem controlItem, HttpServletRequest request) {

		PermisoDTO response = new PermisoDTO();
		String token = request.getHeader("Authorization");

		HashMap<String,String> permisos = UserTokenUtils.getPermisosFromJWTToken(token);
		PermisoItem permisoItem = new PermisoItem();
		permisoItem.setDerechoacceso(permisos.get(controlItem.getIdProceso()));
		
		List<PermisoItem> permisosItem = new ArrayList<PermisoItem>();
		permisosItem.add(permisoItem);
		response.setPermisoItems(permisosItem);

		return response;
	}

	@Override
	public HashMap<String, String> getAccessControlWithOutPerm(String nifInstitucion) {

		ControlRequestItem controlItem = new ControlRequestItem();
		HashMap<String, String> response = new HashMap<String, String>();
		// Cargamos el Dni del Token

		// String nifInstitucion =
		// UserAuthenticationToken.getUserFromJWTToken(authorization);

		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));

		String dni = nifInstitucion.substring(0, 9);
		AdmUsuariosExample usuarioExample = new AdmUsuariosExample();
		usuarioExample.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		// Obtenemos el Usuario para comprobar todas sus instituciones
		List<AdmUsuarios> usuarios = usuarioMapper.selectByExample(usuarioExample);

		/*
		 * if (usuarios == null || usuarios.isEmpty()) { Error error = new
		 * Error(); error.setCode(400); error.setDescription("400");
		 * response.setError(error); return response; }
		 */

		List<String> idperfiles = new ArrayList<String>();

		// Obtenemos todos los perfiles del Usuario para cargar sus puntos de
		// Menú
		AdmUsuariosEfectivosPerfilExample exampleUsuarioPerfil = new AdmUsuariosEfectivosPerfilExample();

		exampleUsuarioPerfil.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andIdusuarioEqualTo(usuarios.get(0).getIdusuario()).andFechaBajaIsNull();
		List<AdmUsuariosEfectivosPerfil> perfiles = admUsuariosEfectivoMapper.selectByExample(exampleUsuarioPerfil);

		/*
		 * if (perfiles == null) { Error error = new Error();
		 * error.setCode(400); error.setDescription("400");
		 * response.setError(error); return response; }
		 */
		for (AdmUsuariosEfectivosPerfil perfil : perfiles) {
			idperfiles.add("'" + perfil.getIdperfil() + "'");
		}
		// Nos quedamos con todos los perfiles para realizar la búsqueda.
		String[] listParameters = nifInstitucion.split("-");
		controlItem.setInstitucion(listParameters[2]);
		String str = idperfiles.toString().replace("[", "").replace("]", "");
		controlItem.setIdGrupo(str);

		// Añadimos los permisos a la lista
		List<PermisoEntity> permisos = this.admUsuariosExtendsMapper.getAccessControlsWithOutProcess(controlItem);
		if (null != permisos && permisos.size() > 0) {
			for (PermisoEntity permisoEntity : permisos) {
				response.put(permisoEntity.getData(), permisoEntity.getDerechoacceso());
			}

		}
		return response;
	}

	@Override
	public EntornoDTO getEntorno(HttpServletRequest request) {
		
		AdmConfigExample example = new AdmConfigExample();
		example.createCriteria().andClaveEqualTo("security.basic.enabled");
		List<AdmConfig> config = admConfigMapper.selectByExample(example );
		
		EntornoDTO response = new EntornoDTO();
		response.setentorno(config.get(0).getValor());
		return response;
	}

	@Override
	public void getHeaderLogo(HttpServletRequest httpRequest, HttpServletResponse response) {
		LOGGER.info("Servicio de recuperacion de logos");
		String pathFinal = "";
		List<GenProperties> genProperties = new ArrayList<GenProperties>();
		List<AdmGestorinterfaz> admGestorinterfaz = new ArrayList<AdmGestorinterfaz>();
	
		
		// Obtenemos atributos del usuario logeado
		LOGGER.debug("Obtenemos atributos del usuario logeado");
		String token = httpRequest.getHeader("Authorization");
		Short institucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.logos");
		genProperties = genPropertiesMapper.selectByExample(genPropertiesExample);
		
		if(!genProperties.isEmpty()) {
			String path = genProperties.get(0).getValor() + "/";
			pathFinal = pathFinal.concat(path);
			
			AdmGestorinterfazExample admGestorinterfazExample = new AdmGestorinterfazExample();
			admGestorinterfazExample.createCriteria().andAdmGestorinterfazIdEqualTo(Long.valueOf(String.valueOf(institucion)));
			admGestorinterfaz = admGestorinterfazMapper.selectByExample(admGestorinterfazExample);
			
			if(!admGestorinterfaz.isEmpty()) {
				String nameFile = admGestorinterfaz.get(0).getLogo();
				pathFinal = pathFinal.concat(nameFile);
				LOGGER.info("Se obtiene el logo del path:  " + pathFinal );
				
				if(null != pathFinal) {
					 // Se coge la imagen con el logo
					File file = new File(pathFinal);
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
						// Parece que soporta otros tipos, como png
						response.setContentType(MediaType.IMAGE_JPEG_VALUE);
						// se pasa el logo en la respuesta http
						IOUtils.copy(fis, response.getOutputStream());
						
					} catch (FileNotFoundException e) {
						LOGGER.error("No se ha encontrado el fichero", e);
						
					} catch (IOException e1) {
						LOGGER.error("No se han podido escribir los datos binarios del logo en la respuesta HttpServletResponse", e1);
						e1.printStackTrace();
					} 	
					finally{
						if(null!= fis)
							try {
								fis.close();
							} catch (IOException e) {
								LOGGER.error("No se ha cerrado el archivo que contiene el logo", e);
								e.printStackTrace();
							}
					}
				}
				
			}
		}
		LOGGER.info("Servicio de recuperacion de logos -> OK");
		return;
	}

	@Override
	public UpdateResponseDTO validaInstitucion(HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();
		try{
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		String organizationName = null;
		String organizationNameNuevo = null;
		X509Certificate cert = null;
		try {
			cert = certs[0];
			X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();


			if (x500name.getAttributeTypes()[7].getId().equals("1.3.6.1.4.1.16533.30.3")) {
				RDN institucionnuevo = x500name.getRDNs(x500name.getAttributeTypes()[7])[0];
				organizationNameNuevo = IETFUtils.valueToString(institucionnuevo.getFirst().getValue());
			}else{
				RDN institucionRdn = x500name.getRDNs(BCStyle.O)[0];
				organizationName = IETFUtils.valueToString(institucionRdn.getFirst().getValue());
			}
			
		} catch (CertificateEncodingException e) {
			throw new InvalidClientCerticateException(e);
		}


		String idInstitucion = null;
		if (null != organizationNameNuevo) {
			idInstitucion = organizationNameNuevo.substring(0,
						4);
		}else{
			idInstitucion = organizationName.substring(organizationName.length() - 4,
				organizationName.length());
		}
		if (!UtilidadesString.esCadenaVacia(idInstitucion)) {
			if (!idInstitucion.equals(SigaConstants.InstitucionGeneral)) {
				throw new BadCredentialsException("Certificado no validado para CGAE");
			}
		}

		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
		response.setStatus(SigaConstants.OK);
		return response;
	}

	@Override
	public ComboItem getInstitucionActual(HttpServletRequest request) {
		ComboItem comboItem = new ComboItem();
		CenInstitucion cenInstitucion = new CenInstitucion();
		// Obtenemos atributos del usuario logeado
		LOGGER.debug("Obtenemos atributos del usuario logeado");
		String token = request.getHeader("Authorization");
		Short institucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		cenInstitucion = institucionMapper.selectByPrimaryKey(institucion);
		
		comboItem.setLabel(cenInstitucion.getNombre());
		comboItem.setValue(String.valueOf(cenInstitucion.getIdinstitucion()));
		return comboItem;
	}

	@Override
	public UpdateResponseDTO setIdiomaUsuario(HttpServletRequest request, String idLenguaje) {
		LOGGER.info("setIdiomaUsuario() --> Entrada al servicio de cambio de idioma");
		UpdateResponseDTO response = new UpdateResponseDTO();
		int updateLenguaje = 0;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if(usuarios!= null &&usuarios.size()>0){
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdlenguaje(idLenguaje);
				usuario.setFechamodificacion(new Date());
				try{
					updateLenguaje = usuarioMapper.updateByPrimaryKey(usuario);
					
					CenPersonaExample examplePersona = new CenPersonaExample();
					examplePersona.createCriteria().andNifcifEqualTo(dni);
					List<CenPersona> personaList = cenPersonaMapper.selectByExample(examplePersona);
					
					CenColegiado colegiado = null;
					if(personaList.size() > 0){
						CenPersona persona = personaList.get(0);
						CenColegiadoKey key = new CenColegiadoKey();
						key.setIdinstitucion(idInstitucion);
						key.setIdpersona(persona.getIdpersona());
						colegiado = cenColegiadoMapper.selectByPrimaryKey(key);
						
					}
					CenCliente cliente = null;
					if(colegiado != null){
						CenClienteKey cke = new CenClienteKey();
						cke.setIdinstitucion(idInstitucion);
						cke.setIdpersona(colegiado.getIdpersona());
						cliente = cenClienteMapper.selectByPrimaryKey(cke);
					}
					
					if(cliente != null){
						cliente.setIdlenguaje(idLenguaje);
						cenClienteMapper.updateByPrimaryKey(cliente);
					}
					if(updateLenguaje==1){
						response.setStatus(SigaConstants.OK);
					}
				}catch(Exception e) {
					LOGGER.info("setIdiomaUsuario() --> error al actualizar tabla adm_usuarios:" + e.getMessage());
					response.setStatus(SigaConstants.KO);
				}
			}
			
		}
		LOGGER.info("setIdiomaUsuario() --> Salida del servicio de cambio de idioma");
		return response;
	}	


}
