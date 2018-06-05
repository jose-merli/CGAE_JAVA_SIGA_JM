package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDeleteDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaPerJuridicaService;
import org.itcgae.siga.cen.services.IBusquedaPerService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposociedadExtendsMapper;
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
		// TODO Auto-generated method stub
		// LOGGER.info("getParameterModules() -> Entrada al servicio para obtener los
		// módulos disponibles para los parámetros generales");
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		//
		//
		// LOGGER.info("getParameterModules() / genParametrosExtendsMapper.getModules()
		// -> Entrada a genParametrosExtendsMapper para obtener los módulos disponibles
		// para los parámetros generales");
		comboItems = cenInstitucionExtendsMapper.getComboInstituciones();
		// LOGGER.info("getParameterModules() / genParametrosExtendsMapper.getModules()
		// -> Salida de genParametrosExtendsMapper para obtener los módulos disponibles
		// para los parámetros generales");
		//
		if (!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem);
			combo.setCombooItems(comboItems);
		}
		//
		// LOGGER.info("getParameterModules() -> Salida del servicio para obtener los
		// módulos disponibles para los parámetros generales");
		return combo;

	}

	@Override
	public BusquedaPerJuridicaDTO searchJuridica(int numPagina,
			BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchLegalPersons() -> Entrada al servicio para la búsqueda por filtros de personas no colegiadas");

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
					"searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"searchLegalPersons() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Entrada a cenNocolegiadoExtendsMapper para busqueda de personas colegiadas por filtro");
				busquedaJuridicaItems = cenPersonaExtendsMapper.searchPerJuridica(1, busquedaPerJuridicaSearchDTO);
				LOGGER.info(
						"searchLegalPersons() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para busqueda de personas no colegiadas por filtro");

				busquedaPerJuridicaDTO.setBusquedaPerJuridicaItems(busquedaJuridicaItems);
			} else {
				LOGGER.warn(
						"searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchLegalPersons() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"searchLegalPersons() -> Salida del servicio para la búsqueda por filtros de personas no colegiadas");
		return busquedaPerJuridicaDTO;
	}

	@Override
	public BusquedaPerFisicaDTO searchFisica(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"searchLegalPersons() -> Entrada al servicio para la búsqueda por filtros de personas no colegiadas");

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
					"searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"searchLegalPersons() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Entrada a cenNocolegiadoExtendsMapper para busqueda de personas colegiadas por filtro");
				busquedaPerFisicaItems = cenPersonaExtendsMapper.searchPerFisica(1, busquedaPerFisicaSearchDTO);
				LOGGER.info(
						"searchLegalPersons() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para busqueda de personas no colegiadas por filtro");

				busquedaPerFisicaDTO.setBusquedaFisicaItems(busquedaPerFisicaItems); 
			} else {
				LOGGER.warn(
						"searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchLegalPersons() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"searchLegalPersons() -> Salida del servicio para la búsqueda por filtros de personas no colegiadas");
		return busquedaPerFisicaDTO;
	}

}
