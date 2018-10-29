package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ComboColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IFichaColegialOtrasColegiacionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialOtrasColegiacionesServiceImpl implements IFichaColegialOtrasColegiacionesService {

	private Logger LOGGER = Logger.getLogger(FichaColegialOtrasColegiacionesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Override
	public ColegiadoDTO searchOtherCollegues(int numPagina, String idPersona, HttpServletRequest request) {
	LOGGER.info("searchOtherCollegues() -> Entrada al servicio para la búsqueda de un abogado en distintos colegios");
		
		List<ColegiadoItem> colegiadoItems = new ArrayList<ColegiadoItem>();
		ColegiadoDTO colegiadoDTO = new ColegiadoDTO();
		String idLenguaje = null;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				
			LOGGER.info(
						"searchOtherCollegues() / cenComponentesExtendsMapper.searchSocieties() -> Entrada a cenComponentesExtendsMapper para obtener sociedades");
			    colegiadoItems = cenColegiadoExtendsMapper.searchOtherCollegues(idPersona, idLenguaje);
				LOGGER.info("searchOtherCollegues() / cenComponentesExtendsMapper.searchSocieties() -> Salida de cenComponentesExtendsMapper para obtener sociedades");
				colegiadoDTO.setColegiadoItem(colegiadoItems);
			} 
			else {
				LOGGER.warn("searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchOtherCollegues() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchOtherCollegues() -> Salida del servicio para la búsqueda de un abogado en distintos colegios");
		return colegiadoDTO;
	}

	@Override
	public ComboColegiadoDTO getLabelColegios(String idPersona, HttpServletRequest request) {
		LOGGER.info("getLabelColegios() -> Entrada al servicio para la búsqueda de todos los colegios");
		
		ComboColegiadoDTO combo = new ComboColegiadoDTO();
		List<ComboColegiadoItem> comboItems = new ArrayList<ComboColegiadoItem>();

		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Entrada a cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
		comboItems = cenColegiadoExtendsMapper.getLabelColegios(idPersona);
		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Salida de cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
	
//		if (!comboItems.equals(null) && comboItems.size() > 0) {
//			// añade elemento vacio al principio para el dropdown de parte front
//			ComboColegiadoItem comboItem = new ComboColegiadoItem();
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			comboItems.add(0, comboItem);
//			combo.setCombooItems(comboItems);
//		}
//		
		combo.setCombooItems(comboItems);
		LOGGER.info("getLabelColegios() -> Salida del servicio para la búsqueda de todos los colegios");
		return combo;
	}

}