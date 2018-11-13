package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaSancionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenTiposancionExample;
import org.itcgae.siga.db.mappers.CenTiposancionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposancionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaSancionesServiceImpl implements IBusquedaSancionesService {

	private Logger LOGGER = Logger.getLogger(BusquedaSancionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenTiposancionExtendsMapper cenTiposancionExtendsMapper;

	@Override
	public ComboDTO getComboTipoSancion(HttpServletRequest request) {
		LOGGER.info("getComboTipoSancion() -> Entrada al servicio para obtener los tipos de sanciones");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info(
				"getComboTipoSancion() / cenTiposancionExtendsMapper.getComboTipoSancion() -> Entrada a cenTiposancionMapper para obtener los diferentes tipos de sanciones");
		comboItems = cenTiposancionExtendsMapper.getComboTipoSancion();
		LOGGER.info(
				"getComboTipoSancion() / cenTiposancionExtendsMapper.getComboTipoSancion() -> Salida de cenTiposancionMapper para obtener los diferentes tipos de sanciones");

		if (!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al principio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem);
			comboDTO.setCombooItems(comboItems);
		}
		
		LOGGER.info("getComboTipoSancion() -> Salida del servicio para obtener los tipos de sanciones");
		return comboDTO;
	}

}
