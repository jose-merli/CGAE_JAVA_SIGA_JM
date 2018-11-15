package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaSancionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
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

	@Override
	public BusquedaSancionesDTO searchBusquedaSanciones(int numPagina,
			BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request) {
		LOGGER.info("searchBusquedaSanciones() -> Entrada al servicio para la búsqueda por filtros de sanciones");

		List<BusquedaSancionesItem> busquedaSancionesItems = new ArrayList<BusquedaSancionesItem>();
		BusquedaSancionesDTO busquedaSancionesDTO = new BusquedaSancionesDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"searchBusquedaSanciones() / cenTiposancionExtendsMapper.searchBusquedaSanciones() -> Entrada a cenNocolegiadoExtendsMapper para busqueda de personas colegiadas por filtro");
				busquedaSancionesItems = cenTiposancionExtendsMapper.searchBusquedaSanciones(busquedaSancionesSearchDTO,
						idLenguaje, String.valueOf(idInstitucion));
				LOGGER.info(
						"searchBusquedaSanciones() / cenTiposancionExtendsMapper.searchBusquedaSanciones() -> Salida de cenNocolegiadoExtendsMapper para busqueda de personas no colegiadas por filtro");

				busquedaSancionesDTO.setBusquedaSancionesItem(busquedaSancionesItems);
			} else {
				LOGGER.warn(
						"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchBusquedaSanciones() -> idInstitucion del token nula");
		}

		LOGGER.info("searchLegalPersons() -> Salida del servicio para la búsqueda por filtros de sanciones");
		return busquedaSancionesDTO;
	}

}
