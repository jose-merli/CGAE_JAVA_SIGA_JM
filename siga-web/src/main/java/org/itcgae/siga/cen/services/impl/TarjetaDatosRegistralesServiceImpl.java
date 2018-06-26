package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
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
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiadoActividad;
import org.itcgae.siga.db.entities.CenNocolegiadoActividadExample;
import org.itcgae.siga.db.mappers.CenNocolegiadoActividadMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenActividadprofesionalExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
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
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private CenNocolegiadoActividadMapper cenNocolegiadoActividadMapper;
	
	
	
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
					"searchRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				
				personaJuridicaSearchDTO.setIdInstitucion(String.valueOf(idInstitucion));
				personaJuridicaSearchDTO.setIdLenguaje(usuario.getIdlenguaje());
				LOGGER.info(
						"searchRegistryDataLegalPerson() / cenActividadprofesionalExtendsMapper.searchRegistryDataLegalPerson() -> Entrada a cenActividadprofesionalExtendsMapper para recuperar datos de registro de persona jurídica");
				datosRegistralesItems = cenActividadprofesionalExtendsMapper.searchRegistryDataLegalPerson(personaJuridicaSearchDTO);
				LOGGER.info(
						"searchRegistryDataLegalPerson() / cenActividadprofesionalExtendsMapper.searchRegistryDataLegalPerson() -> Salida de cenActividadprofesionalExtendsMapper para recuperar datos de registro de persona jurídica");
				
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
		
		int responseCenPersona = 0;
		int responseCenNocolegiado = 0;
		int responseCenNoColegiadoActividad = 0;
		int responseInsertCenNoColegiadoActividad = 0;
		int responseBorrarCenNoColegiadoActividad = 0;
		boolean cerrojoDarAlta = true;
		boolean cerrojoCrearRegistro = true;
		boolean cerrojoBorrar = true;
		PersonaJuridicaActividadDTO personaJuridicaActividadDTO = new PersonaJuridicaActividadDTO();
		ComboDTO comboDTO = new ComboDTO();
		List<String> actividadesABorrar = new ArrayList<String>();
		List<CenNocolegiadoActividad> actividadExistente = new  ArrayList<CenNocolegiadoActividad>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"updateRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			
			// 1. Actualizar tabla cen_persona
			LOGGER.info(
					"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updatebyExampleDataLegalPerson() -> Entrada a cenPersonaExtendsMapper para actualizar fecha de constitución de una persona jurídica");
			
			responseCenPersona = cenPersonaExtendsMapper.updatebyExampleDataLegalPerson(perJuridicaDatosRegistralesUpdateDTO, usuario);
			LOGGER.info(
					"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updatebyExampleDataLegalPerson() -> Salida de cenPersonaExtendsMapper para actualizar fecha de constitución de una persona jurídica");
			
			// 2. Actualizar tabla cen_nocolegiado
			if(responseCenPersona == 1) {
				LOGGER.info(
						"updateRegistryDataLegalPerson() / cenNocolegiadoExtendsMapper.updateByExampleDataLegalPerson() -> Entrada a cenNocolegiadoExtendsMapper para actualizar datos de una persona jurídica");
				
				responseCenNocolegiado = cenNocolegiadoExtendsMapper.updateByExampleDataLegalPerson(perJuridicaDatosRegistralesUpdateDTO, String.valueOf(idInstitucion), usuario);
				LOGGER.info(
						"updateRegistryDataLegalPerson() / cenNocolegiadoExtendsMapper.updateByExampleDataLegalPerson() -> Salida de cenNocolegiadoExtendsMapper para actualizar datos de una persona jurídica");
				
			}
			else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> "+ updateResponseDTO.getStatus() + ". No se actualizó correctamente la tabla cen_persona");
			}
			
			// 3. Actualizar tabla CEN_NOCOLEGIADO_ACTIVIDAD
			if(responseCenNocolegiado == 1) {
				
				// busca las actividades que estaban asociadas a la persona juridica
				personaJuridicaActividadDTO.setIdInstitucion(String.valueOf(idInstitucion));
				personaJuridicaActividadDTO.setIdPersona(perJuridicaDatosRegistralesUpdateDTO.getIdPersona());
				comboDTO = getActividadProfesionalPer(personaJuridicaActividadDTO, request);
				
				for(int i=0;i< comboDTO.getCombooItems().size();i++) {
					actividadesABorrar.add(comboDTO.getCombooItems().get(i).getValue());
				}
				
				// añadimos las nuevas actividades
				for (String actividad: perJuridicaDatosRegistralesUpdateDTO.getActividades()) {
					// comprobar si existe registro en tabla
					CenNocolegiadoActividadExample cenNocolegiadoActividadExample = new CenNocolegiadoActividadExample();
					cenNocolegiadoActividadExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona())).andIdactividadprofesionalEqualTo(Short.valueOf(actividad));
					
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.selectByExample() -> Entrada a cenNocolegiadoActividadMapper para comprobar si existe una actividad para una persona jurídica");
					
					actividadExistente = cenNocolegiadoActividadMapper.selectByExample(cenNocolegiadoActividadExample);
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.selectByExample() -> Salida de cenNocolegiadoActividadMapper para comprobar si existe una actividad para una persona jurídica");
					
					if(!actividadExistente.isEmpty()) { // hay registros => poner fecha_baja = null
						
						if(null != actividadExistente.get(0).getFechaBaja()) {
							// dar de alta una actividad asociada a una persona juridica
							CenNocolegiadoActividad record = new CenNocolegiadoActividad();
							record.setIdinstitucion(idInstitucion);
							record.setIdpersona(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()));
							record.setIdactividadprofesional(Short.valueOf(actividad));
							record.setFechamodificacion(new Date());
							record.setUsumodificacion(usuario.getIdusuario()); 
							record.setFechaBaja(null);
							
							CenNocolegiadoActividadExample example = new CenNocolegiadoActividadExample();
							example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona())).andIdactividadprofesionalEqualTo(Short.valueOf(actividad));
							LOGGER.info(
									"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoActividadMapper para dar de alta una actividad para una persona jurídica");
							
							//responseCenNoColegiadoActividad = cenNocolegiadoActividadMapper.updateByExampleSelective(record, example);
							responseCenNoColegiadoActividad = cenNocolegiadoActividadMapper.updateByExample(record, example);
							LOGGER.info(
									"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Salida de cenNocolegiadoActividadMapper para dar de alta una actividad para una persona jurídica");
							
							
							if(cerrojoDarAlta) {
								updateResponseDTO.setStatus(SigaConstants.OK);
								cerrojoDarAlta = false;
							}
							
							if(responseCenNoColegiadoActividad == 0) {
								updateResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> " + updateResponseDTO.getStatus() + ".No se pudo dar de alta una actividad asociada a una persona jurídica");
								
							}
						}
					}
					else {  // no hay registros =>  crear registro
						CenNocolegiadoActividad insertActividad = new CenNocolegiadoActividad();
						insertActividad.setFechaBaja(null);
						insertActividad.setFechamodificacion(new Date());
						insertActividad.setIdactividadprofesional(Short.valueOf(actividad));
						insertActividad.setIdinstitucion(idInstitucion);
						insertActividad.setIdpersona(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()));
						insertActividad.setUsumodificacion(usuario.getIdusuario());
						LOGGER.info(
								"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.insert() -> Entrada a cenNocolegiadoActividadMapper para crear una actividad asociada a una persona jurídica");
						
						responseInsertCenNoColegiadoActividad = cenNocolegiadoActividadMapper.insert(insertActividad);
						LOGGER.info(
								"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.insert() -> Salida de cenNocolegiadoActividadMapper para crear una actividad asociada a una persona jurídica");
						
						if(cerrojoCrearRegistro) {
							updateResponseDTO.setStatus(SigaConstants.OK);
							cerrojoCrearRegistro = false;
						}
						
						if(responseInsertCenNoColegiadoActividad == 0) {
							updateResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn(
									"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.insert() -> " + updateResponseDTO.getStatus() + ".No se pudo crear una actividad asociada a una persona jurídica");
							
						}
					}
					
					// dejamos en el array solo las actividades a desasociar
					if(actividadesABorrar.contains(actividad)) {
						actividadesABorrar.remove(actividad);
					}
					
					
				}
				
				// borrar actividades que ya no forman parte de una persona juridica => fecha_baja = new Date()
				for (String actividadABorrar : actividadesABorrar) {
					CenNocolegiadoActividad recordBorrar = new CenNocolegiadoActividad();
					recordBorrar.setFechamodificacion(new Date());
					recordBorrar.setUsumodificacion(usuario.getIdusuario());
					recordBorrar.setFechaBaja(new Date());
					
					CenNocolegiadoActividadExample exampleBorrar = new CenNocolegiadoActividadExample();
					exampleBorrar.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona())).andIdactividadprofesionalEqualTo(Short.valueOf(actividadABorrar));
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoActividadMapper para desasignar una actividad asociada a una persona jurídica");
					
					responseBorrarCenNoColegiadoActividad = cenNocolegiadoActividadMapper.updateByExampleSelective(recordBorrar, exampleBorrar);
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Salida de cenNocolegiadoActividadMapper para desasignar una actividad asociada a una persona jurídica");
					
					if(cerrojoBorrar) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						cerrojoBorrar = false;
					}
						
					if(responseBorrarCenNoColegiadoActividad == 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn(
								"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> " + updateResponseDTO.getStatus() + ".No se pudo desasignar una actividad asociada a una persona jurídica");
						
					}
				}
			}
			else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"updateRegistryDataLegalPerson() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> "+ updateResponseDTO.getStatus() + ". No se actualizó correctamente la tabla cen_nocolegiado");
			}
		}
		
		LOGGER.info(
				"updateRegistryDataLegalPerson() -> Salida del servicio para actualizar datos registrales de una persona jurídica");
		return updateResponseDTO;
	}

	

	
}
