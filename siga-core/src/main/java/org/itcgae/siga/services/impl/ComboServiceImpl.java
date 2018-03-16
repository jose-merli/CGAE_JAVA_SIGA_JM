package org.itcgae.siga.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.itcgae.siga.DTOs.common.ComboDTO;
import org.itcgae.siga.DTOs.common.ComboDTORequest;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.Converter;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.services.IComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboServiceImpl implements IComboService{

	@Autowired
	private CenInstitucionExtendsMapper institucionExtendsExtend;
	
	@Autowired
	private AdmPerfilExtendsMapper perfil;
		
	@Override
	public  List<ComboDTO> getCombo(String combo){
		
		
		if (combo.contentEquals(SigaConstants.COMBO_INSTITUCIONES)) {
			return getInstituciones();
		}
		
		
		return null;
		
	}

	
	
	@Override
	public List<ComboDTO> getComboParametros(String combo, ComboDTORequest institucion) {
		if (combo.contentEquals(SigaConstants.COMBO_PERFILES)) {
			return getPerfiles(institucion.getValue());
		}
		
		
		return null;
	}
	
	
	private  List<ComboDTO> getInstituciones() {		
		CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
		exampleInstitucion.setDistinct(true);
		exampleInstitucion.setOrderByClause("ABREVIATURA ASC");
		
		List<CenInstitucion> instituciones =  institucionExtendsExtend.selectByExample(exampleInstitucion );
		List<ComboDTO> combos = new ArrayList<ComboDTO>();
		if (null != instituciones && instituciones.size()>0) {
			for (Iterator iterator = instituciones.iterator(); iterator.hasNext();) {
				CenInstitucion cenInstitucion = (CenInstitucion) iterator.next();
				ComboDTO combo = new ComboDTO();
				combo.setId(cenInstitucion.getIdinstitucion().toString());
				if (null != cenInstitucion.getFechaenproduccion()) {
			
					combo.setValue(cenInstitucion.getAbreviatura() + " (En producción: " + Converter.dateToString(cenInstitucion.getFechaenproduccion()) + ")");
				}else{
					combo.setValue(cenInstitucion.getAbreviatura());
				}
				
				combos.add(combo);
			}
			
		}
		
		
		
		return combos;
	}



	private List<ComboDTO> getPerfiles(String institucion) {
		AdmPerfilExample examplePerfil = new AdmPerfilExample();
		examplePerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion));
		examplePerfil.setOrderByClause("IDINSTITUCION ASC");
		List<AdmPerfil> perfiles = perfil.selectComboPerfilByExample(examplePerfil );
		List<ComboDTO> combos = new ArrayList<ComboDTO>();
		if (null != perfiles && perfiles.size()>0) {
			for (Iterator iterator = perfiles.iterator(); iterator.hasNext();) {
				AdmPerfil admPerfil = (AdmPerfil) iterator.next();
				ComboDTO combo = new ComboDTO();
				combo.setId(admPerfil.getIdinstitucion().toString());
				combo.setValue(admPerfil.getDescripcion());
				combos.add(combo);
			}
			
		}
		
		
		
		return combos;
	}
}
