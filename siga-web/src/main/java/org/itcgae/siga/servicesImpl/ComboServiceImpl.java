package org.itcgae.siga.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.entities.ScsSubzona;
import org.itcgae.siga.db.entities.ScsSubzonaExample;
import org.itcgae.siga.db.entities.ScsZona;
import org.itcgae.siga.db.entities.ScsZonaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupofacturacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJurisdiccionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPartidasPresupuestariasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsZonasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboServiceImpl implements ComboService {

	private Logger LOGGER = Logger.getLogger(ComboServiceImpl.class);

	@Autowired
	private ScsZonasExtendsMapper scsZonasExtendsMapper;

	@Autowired
	private ScsSubzonaExtendsMapper scsSubZonasExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired 
	private ScsJurisdiccionExtendsMapper scsJurisdiccionExtendsMapper;
	
	@Autowired 
	private ScsGrupofacturacionExtendsMapper scsGrupofacturacionExtendsMapper;
	
	@Autowired 
	private ScsPartidasPresupuestariasExtendsMapper scsPartidasPresupuestariasExtendsMapper;
	
	// PK

	public ComboDTO getComboZonas(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboZonas = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboZonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboZonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getComboZonas() -> Entrada para obtener la información de las distintas zonas");
				
				ScsZonaExample example = new ScsZonaExample();
				
				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).
					andFechabajaIsNull();
				
				List<ScsZona> zonas = scsZonasExtendsMapper.selectByExample(example);
				
				
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				
				for(ScsZona zona: zonas) {
					ComboItem item = new ComboItem();
					item.setLabel(zona.getNombre());
					item.setValue(zona.getIdzona().toString());
					
					comboItems.add(item);
					
				}
				
				comboZonas.setCombooItems(comboItems);
				LOGGER.info("getComboZonas() -> Salida ya con los datos recogidos");
			}
		}
		return comboZonas;

	}

	public ComboDTO getComboSubZonas(HttpServletRequest request, String idZona) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboSubZonas = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboSubZonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboSubZonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getComboSubZonas() -> Entrada para obtener la información de las distintas subZonas");
				
				ScsSubzonaExample example = new ScsSubzonaExample();
				
				example.createCriteria().andIdzonaEqualTo(Short.valueOf(idZona)).andIdinstitucionEqualTo(idInstitucion).
					andFechabajaIsNull();
				
				List<ScsSubzona> subZonas = scsSubZonasExtendsMapper.selectByExample(example);
				
				
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				
				for(ScsSubzona zona: subZonas) {
					ComboItem item = new ComboItem();
					item.setLabel(zona.getNombre());
					item.setValue(zona.getIdsubzona().toString());
					
					comboItems.add(item);
					
				}
				
				comboSubZonas.setCombooItems(comboItems);
				LOGGER.info("getComboZonas() -> Salida ya con los datos recogidos");

			}
		}
		return comboSubZonas;

	}

	public ComboDTO getJurisdicciones(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboJuris= new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getJurisdicciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getJurisdicciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getJurisdicciones() -> Entrada para obtener la información de las distintas jurisdicciones");

				comboJuris.setCombooItems(
						scsJurisdiccionExtendsMapper.getComboJurisdiccion(usuarios.get(0).getIdlenguaje()));
				
				LOGGER.info("getJurisdicciones() -> Salida ya con los datos recogidos");

			}
		}
		return comboJuris;

	}
	
	public ComboDTO getComboGrupoFacturacion(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboGrupoFact= new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getComboGrupoFacturacion() -> Entrada para obtener la información de los distintos grupos de facturacion");

				comboGrupoFact.setCombooItems(
						scsGrupofacturacionExtendsMapper.getComboGrupoFacturacion(idInstitucion.toString(), usuarios.get(0).getIdlenguaje()));
				
				LOGGER.info("getComboGrupoFacturacion() -> Salida ya con los datos recogidos");

			}
		}
		return comboGrupoFact;

	}


	public ComboDTO getComboPartidasPresupuestarias(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboPartPres = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboPartidasPresupuestarias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboPartidasPresupuestarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getComboPartidasPresupuestarias() -> Entrada para obtener la información de las distintas subZonas");
				
				ScsPartidapresupuestariaExample example = new ScsPartidapresupuestariaExample();
				
				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).
					andFechabajaIsNull();
				
				List<ScsPartidapresupuestaria> partidas= scsPartidasPresupuestariasExtendsMapper.selectByExample(example);
				
				
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				
				for(ScsPartidapresupuestaria partida: partidas) {
					ComboItem item = new ComboItem();
					item.setLabel(partida.getNombrepartida());
					item.setValue(partida.getIdpartidapresupuestaria().toString());
					
					comboItems.add(item);
					
				}
				
				comboPartPres.setCombooItems(comboItems);
				LOGGER.info("getComboPartidasPresupuestarias() -> Salida ya con los datos recogidos");

			}
		}
		return comboPartPres;

	}
	
	// ------------------------------------------------

	// Iván

}
