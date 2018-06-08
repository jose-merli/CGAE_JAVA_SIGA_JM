package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosRegistralesDTO;
import org.itcgae.siga.DTOs.cen.DatosRegistralesItem;
import org.itcgae.siga.DTOs.cen.PerJuridicaDatosRegistralesUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosRegistralesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenActividadprofesionalExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaDatosRegistralesServiceImpl implements ITarjetaDatosRegistralesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosRegistralesServiceImpl.class);

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenActividadprofesionalExtendsMapper cenActividadprofesionalExtendsMapper;
	
	@Override
	public ComboDTO getActividadProfesionalPer(PersonaJuridicaActividadDTO personaJuridicaActividadDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"getActividadProfesionalPer() -> Entrada al servicio para obtener las actividades profesionales de una sociedad");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion)
		{
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getActividadProfesionalPer() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getActividadProfesionalPer() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			personaJuridicaActividadDTO.setIdInstitucion(String.valueOf(idInstitucion));
			
			
			if(null != usuarios && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getActividadProfesionalPer() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de actividades profesionales para una sociedad");
				comboItems = cenNocolegiadoExtendsMapper.selectProfesionalActivitiesSociety(personaJuridicaActividadDTO, usuario.getIdlenguaje());
				LOGGER.info(
						"getActividadProfesionalPer() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de actividades profesionales para una sociedad");
				
				comboDTO.setCombooItems(comboItems);
			}
			
		}
		
		LOGGER.info(
				"getActividadProfesionalPer() -> Salida del servicio para obtener las actividades profesionales de una sociedad");
		return comboDTO;
	}

	@Override
	public ComboDTO getActividadProfesional(HttpServletRequest request) {

		LOGGER.info(
				"getActividadProfesional() -> Entrada al servicio para obtener las actividades profesionales disponibles");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getActividadProfesional() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getActividadProfesional() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = cenActividadprofesionalExtendsMapper.selectProfesionalActivities(usuario.getIdlenguaje());
				comboDTO.setCombooItems(comboItems);
			}
		}
		LOGGER.info(
				"getActividadProfesional() -> Salida del servicio para obtener las actividades profesionales disponibles");
		
		return comboDTO;
	}

	@Override
	public DatosRegistralesDTO searchRegistryDataLegalPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"searchRegistryDataLegalPerson() -> Entrada al servicio para recuperar datos registrales de la persona jurídica");
		
		DatosRegistralesDTO datosRegistralesDTO = new DatosRegistralesDTO();
		List<DatosRegistralesItem> datosRegistralesItems = new ArrayList<DatosRegistralesItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getActividadProfesional() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getActividadProfesional() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				
				personaJuridicaSearchDTO.setIdInstitucion(String.valueOf(idInstitucion));
				personaJuridicaSearchDTO.setIdLenguaje(usuario.getIdlenguaje());
				
				datosRegistralesItems = cenActividadprofesionalExtendsMapper.searchRegistryDataLegalPerson(personaJuridicaSearchDTO);
				
				datosRegistralesDTO.setDatosRegistralesItems(datosRegistralesItems);
			}
		}
		
		
		LOGGER.info(
				"searchRegistryDataLegalPerson() -> Salida del servicio para recuperar datos registrales de la persona jurídica");
		return datosRegistralesDTO;
	}

	@Override
	public UpdateResponseDTO updateRegistryDataLegalPerson(
			PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, HttpServletRequest request) {
		LOGGER.info(
				"updateRegistryDataLegalPerson() -> Entrada al servicio para actualizar datos registrales de una persona jurídica");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		
		
		LOGGER.info(
				"updateRegistryDataLegalPerson() -> Salida del servicio para actualizar datos registrales de una persona jurídica");
		return updateResponseDTO;
	}

	

	
}
