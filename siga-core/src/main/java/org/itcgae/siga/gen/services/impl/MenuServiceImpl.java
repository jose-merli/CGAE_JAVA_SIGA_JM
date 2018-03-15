package org.itcgae.siga.gen.services.impl;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.ldap.LdapName;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuItem;
import org.itcgae.siga.commons.utils.Converter;
import org.itcgae.siga.commons.utils.InvalidClientCerticateException;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmTiposaccesoExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.security.CgaeUserAuthenticationToken;
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

	@Override
	public MenuDTO getMenu(HttpServletRequest request) {
		MenuDTO response = new MenuDTO();

		AdmTiposaccesoExample exampleMenu = new AdmTiposaccesoExample();

		exampleMenu.setDistinct(true);
		exampleMenu.setOrderByClause(" MENU.ORDEN ASC");

		// TODO: idIntitución e idPerfil
		try {
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
			String user = null;
			if (null != certs && certs.length > 0) {
				String dn = certs[0].getSubjectX500Principal().getName();
				LdapName ldapDN = new LdapName(dn);
				try {
					user = ldapDN.getRdns().stream().filter(a -> a.getType().equals("CN")).findFirst().get().getValue()
							.toString();
				} catch (NoSuchElementException e) {
					throw new InvalidClientCerticateException(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		
		
		
		exampleMenu.createCriteria().andIdinstitucionEqualTo(Short.valueOf("2000")).andIdperfilEqualTo("ADG")
				.andDerechoaccesoGreaterThan(Short.valueOf("1"));
		List<GenMenu> menuEntities = menuExtend.selectMenuByExample(exampleMenu);

		if (null != menuEntities && !menuEntities.isEmpty()) {
			List<MenuItem> items = new ArrayList<MenuItem>();
			List<GenMenu> rootMenus = menuEntities.stream().filter(i -> i.getIdparent() == null)
					.collect(Collectors.toList());

			for (GenMenu dbItem : rootMenus) {
				
				MenuItem item = new MenuItem();
				item.setLabel(dbItem.getIdrecurso());
				item.setRouterLink(dbItem.getIdrecurso());

				items.add(item);
			}
			// TODO: extraer todos los niveles de menus
			for (Iterator iterator = menuEntities.iterator(); iterator.hasNext();) {

//				GenMenu genMenu = (GenMenu) iterator.next();
//				MenuItem item = new MenuItem();
//				item.setLabel(genMenu.getIdrecurso());
//				item.setRouterLink(genMenu.getIdrecurso());
//
//				items.add(item);

			}

			response.setMenuItems(items);
		}

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
			for (Iterator iterator = instituciones.iterator(); iterator.hasNext();) {
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
		examplePerfil.setOrderByClause("IDINSTITUCION ASC");
		List<AdmPerfil> perfiles = perfilMapper.selectComboPerfilByExample(examplePerfil);
		List<ComboItem> combos = new ArrayList<ComboItem>();
		if (null != perfiles && perfiles.size() > 0) {
			for (Iterator iterator = perfiles.iterator(); iterator.hasNext();) {
				AdmPerfil admPerfil = (AdmPerfil) iterator.next();
				ComboItem combo = new ComboItem();
				combo.setId(admPerfil.getIdinstitucion().toString());
				combo.setValue(admPerfil.getDescripcion());
				combos.add(combo);
			}

		}
		
		response.setCombooItems(combos);
		return response;
	}

}
