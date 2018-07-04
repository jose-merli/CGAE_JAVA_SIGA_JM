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
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaPerJuridicaService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposociedadExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaPerJuridicaServiceImpl implements IBusquedaPerJuridicaService{

	private Logger LOGGER = Logger.getLogger(BusquedaPerJuridicaServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenTiposociedadExtendsMapper cenTiposociedadExtendsMapper;
	
	@Autowired
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;
	
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;
	
	@Override
	public ComboDTO getSocietyTypes(HttpServletRequest request) {
		LOGGER.info("getSocietyTypes() -> Entrada al servicio para obtener los tipos de sociedad");
		
		List<ComboItem> comboItem = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getSocietyTypes() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getSocietyTypes() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				LOGGER.info(
						"getSocietyTypes() / cenTiposociedadExtendsMapper.getSocietyTypes() -> Entrada a cenTiposociedadExtendsMapper para obtener lista de sociedades");
				comboItem = cenTiposociedadExtendsMapper.getSocietyTypes(usuario.getIdlenguaje());
				LOGGER.info(
						"getSocietyTypes() / cenTiposociedadExtendsMapper.getSocietyTypes() -> Salida de cenTiposociedadExtendsMapper para obtener lista de sociedades");
				
				ComboItem comboItem2 = new ComboItem();
				comboItem2.setLabel("");
				comboItem2.setValue("");
				comboItem.add(0, comboItem2);
				comboDTO.setCombooItems(comboItem);
				
			}
			else {
				LOGGER.warn("getSocietyTypes() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			LOGGER.warn("getSocietyTypes() -> idInstitucion del token nula");
		}
		
		
		
		LOGGER.info("getSocietyTypes() -> Salida del servicio para obtener los tipos de sociedad");
		return comboDTO;
	}

	
	@Override
	public ComboDTO getLabel(HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener los de grupos de clientes");
		List<ComboItem> comboItem = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logeado
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Entrada a cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboItem = cenGruposclienteExtendsMapper.getLabel(usuario);
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboDTO.setCombooItems(comboItem);
			}
			else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return comboDTO;
	}


	@Override
	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchLegalPersons() -> Entrada al servicio para la búsqueda por filtros de personas no colegiadas");
		
		List<BusquedaJuridicaItem> busquedaJuridicaItems = new ArrayList<BusquedaJuridicaItem>();
		BusquedaJuridicaDTO busquedaJuridicaDTO = new BusquedaJuridicaDTO();
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
				busquedaJuridicaItems = cenNocolegiadoExtendsMapper.searchLegalPersons(busquedaJuridicaSearchDTO, idLenguaje, String.valueOf(idInstitucion));
				LOGGER.info(
						"searchLegalPersons() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para busqueda de personas no colegiadas por filtro");

				busquedaJuridicaDTO.setBusquedaJuridicaItems(busquedaJuridicaItems);
			} 
			else {
				LOGGER.warn("searchLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchLegalPersons() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchLegalPersons() -> Salida del servicio para la búsqueda por filtros de personas no colegiadas");
		return busquedaJuridicaDTO;
	}

	@Override
	public BusquedaJuridicaDTO searchHistoricLegalPersons(int numPagina,
			BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request) {
		
		LOGGER.info("searchHistoricLegalPersons() -> Entrada al servicio para la búsqueda por filtros de personas no colegiadas");
		
		List<BusquedaJuridicaItem> busquedaJuridicaItems = new ArrayList<BusquedaJuridicaItem>();
		BusquedaJuridicaDTO busquedaJuridicaDTO = new BusquedaJuridicaDTO();
		String idLenguaje = null;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchHistoricLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchHistoricLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				
			LOGGER.info(
						"searchHistoricLegalPersons() / cenNocolegiadoExtendsMapper.searchHistoricLegalPersons() -> Entrada a cenNocolegiadoExtendsMapper para histórico de personas no colegiadas");
			busquedaJuridicaItems = cenNocolegiadoExtendsMapper.searchHistoricLegalPersons(busquedaJuridicaSearchDTO, idLenguaje, String.valueOf(idInstitucion));
				LOGGER.info(
						"searchHistoricLegalPersons() / cenNocolegiadoExtendsMapper.searchHistoricLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para histórico de personas colegiadas");

				busquedaJuridicaDTO.setBusquedaJuridicaItems(busquedaJuridicaItems);
			} 
			else {
				LOGGER.warn("searchHistoricLegalPersons() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchHistoricLegalPersons() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchHistoricLegalPersons() -> Salida del servicio para la búsqueda por filtros de personas no colegiadas");
		return busquedaJuridicaDTO;
	}

	@Override
	public DeleteResponseDTO deleteNotCollegiate(BusquedaJuridicaDeleteDTO busquedaJuridicaDeleteDTO,
			HttpServletRequest request) {
		
		LOGGER.info("deleteNotCollegiate() -> Entrada al servicio para eliminar personas no colegiadas");
		int response = 0;
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"deleteNotCollegiate() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteNotCollegiate() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				// información a modificar
				CenNocolegiado noColegiadoDelete = new CenNocolegiado();
				noColegiadoDelete.setFechaBaja(new Date());
				noColegiadoDelete.setFechamodificacion(new Date());
				noColegiadoDelete.setUsumodificacion(usuario.getIdusuario());
				
				// filtrado para sentencia sql
				List <Long> idPersonasDelete = new ArrayList<Long>();
				for(int i=0; i<busquedaJuridicaDeleteDTO.getIdPersona().length; i++) {
					idPersonasDelete.add(Long.valueOf(busquedaJuridicaDeleteDTO.getIdPersona()[i]));
				}
				
				CenNocolegiadoExample noColegiadoFiltroDelete = new CenNocolegiadoExample();
				noColegiadoFiltroDelete.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaIn(idPersonasDelete);
				LOGGER.info(
						"deleteNotCollegiate() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para eliminar no colegiado");
				response = cenNocolegiadoExtendsMapper.updateByExampleSelective(noColegiadoDelete, noColegiadoFiltroDelete);
				LOGGER.info(
						"deleteNotCollegiate() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para eliminar no colegiado");

			} else {
				LOGGER.warn(
						"deleteNotCollegiate() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("deleteNotCollegiate() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("deleteNotCollegiate() -> OK. Delete para no colegiado realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		}
		else {
			LOGGER.info("deleteNotCollegiate() -> KO. Delete para no colegiado NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteNotCollegiate() -> Salida del servicio para eliminar personas no colegiadas");
		return deleteResponseDTO;
	}


	@Override
	public ComboDTO getLabelPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request) {
		LOGGER.info("getLabelPerson() -> Entrada al servicio para obtener etiquetas de una persona jurídica");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem>comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		LOGGER.info(
				"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
		comboItems = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(personaJuridicaSearchDTO.getIdPersona(), String.valueOf(idInstitucion));
		LOGGER.info(
				"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
		
		comboDTO.setCombooItems(comboItems);
		LOGGER.info("getLabelPerson() -> Salida del servicio para obtener etiquetas de una persona jurídica");
		return comboDTO;
	}


	

}
