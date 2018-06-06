package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaPerService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaPerServiceImpl implements IBusquedaPerService {

	private Logger LOGGER = Logger.getLogger(BusquedaPerServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Override
	public ComboDTO getLabelColegios(HttpServletRequest request) {
		LOGGER.info("getLabelColegios() -> Entrada al servicio para la búsqueda de todos los colegios");
	
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Entrada a cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
		comboItems = cenInstitucionExtendsMapper.getComboInstituciones();
		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Salida de cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
	
		if (!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al principio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem);
			combo.setCombooItems(comboItems);
		}
		
		LOGGER.info("getLabelColegios() -> Salida del servicio para la búsqueda de todos los colegios");
		return combo;

	}

	@Override
	public BusquedaPerJuridicaDTO searchPerJuridica(int numPagina, BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchJuridica() -> Entrada al servicio para la búsqueda por filtros de personas jurídicas");

		List<BusquedaPerJuridicaItem> busquedaJuridicaItems = new ArrayList<BusquedaPerJuridicaItem>();
		BusquedaPerJuridicaDTO busquedaPerJuridicaDTO = new BusquedaPerJuridicaDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchJuridica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchJuridica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"searchJuridica() / cenPersonaExtendsMapper.searchPerJuridica() -> Entrada a cenNocolegiadoExtendsMapper para búsqueda de personas juridicas por filtro");
				busquedaJuridicaItems = cenPersonaExtendsMapper.searchPerJuridica(numPagina, busquedaPerJuridicaSearchDTO, idLenguaje);
				LOGGER.info(
						"searchJuridica() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para búsqueda de personas juridicas por filtro");

				busquedaPerJuridicaDTO.setBusquedaPerJuridicaItems(busquedaJuridicaItems);
				
			} 
			else {
				LOGGER.warn(
						"searchJuridica() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchJuridica() -> idInstitucion del token nula");
		}

		LOGGER.info("searchJuridica() -> Salida del servicio para la búsqueda por filtros de personas jurídicas");
		
		return busquedaPerJuridicaDTO;
	}

	@Override
	public BusquedaPerFisicaDTO searchPerFisica(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"searchPerFisica() -> Entrada al servicio para la búsqueda de personas físicas");

		List<BusquedaPerFisicaItem> busquedaPerFisicaItems = new ArrayList<BusquedaPerFisicaItem>();
		BusquedaPerFisicaDTO busquedaPerFisicaDTO = new BusquedaPerFisicaDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchPerFisica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchPerFisica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"searchPerFisica() / cenPersonaExtendsMapper.searchPerFisica() -> Entrada a cenPersonaExtendsMapper para obtener lista de personas físicas");
				busquedaPerFisicaItems = cenPersonaExtendsMapper.searchPerFisica(busquedaPerFisicaSearchDTO, idLenguaje);
				LOGGER.info(
						"searchPerFisica() / cenPersonaExtendsMapper.searchPerFisica() -> Salida de cenPersonaExtendsMapper para obtener lista de personas físicas");

				busquedaPerFisicaDTO.setBusquedaFisicaItems(busquedaPerFisicaItems); 
			} else {
				LOGGER.warn(
						"searchPerFisica() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchPerFisica() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"searchPerFisica() -> Salida del servicio para la búsqueda de personas físicas");
		return busquedaPerFisicaDTO;
	}

}
