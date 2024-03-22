package org.itcgae.siga.cen.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.DTOs.cen.ParametroColegioDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaPerJuridicaService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
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
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;	
	
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
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
//				comboItem2.setLabel("");
//				comboItem2.setValue("");
//				comboItem.add(0, comboItem2);
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
	public ComboInstitucionDTO getLabel(HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener los de grupos de clientes");
		List<ComboInstitucionItem> comboItem = new ArrayList<ComboInstitucionItem>();
		ComboInstitucionDTO comboInstitucionDTO = new ComboInstitucionDTO();
		
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
				comboInstitucionDTO.setComboInstitucionItem(comboItem);
			}
			else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return comboInstitucionDTO;
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
	public ComboEtiquetasDTO getLabelPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request) throws ParseException {
		LOGGER.info("getLabelPerson() -> Entrada al servicio para obtener etiquetas de una persona jurídica");
		ComboEtiquetasDTO comboEtiquetasDTO = new ComboEtiquetasDTO();
		List<ComboEtiquetasItem>comboEtiquetasItems = new ArrayList<ComboEtiquetasItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		LOGGER.info(
				"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
		comboEtiquetasItems = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(personaJuridicaSearchDTO.getIdPersona(), String.valueOf(idInstitucion));
		LOGGER.info(
				"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
		
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yy");
		String fechaHoy = simpleDateFormat.format(date);
		
		for (ComboEtiquetasItem comboEtiquetasItem : comboEtiquetasItems) {
			Date fechaInicio = simpleDateFormat.parse(comboEtiquetasItem.getFechaInicio());
		    Date fechaBaja = simpleDateFormat.parse( comboEtiquetasItem.getFechaBaja());
		    if (comboEtiquetasItem.getFechaBaja() != null) {
				fechaBaja = simpleDateFormat.parse(comboEtiquetasItem.getFechaBaja());
			} else {
				fechaBaja = null;
			} 
		    Date fechaActual = simpleDateFormat.parse(fechaHoy);
		    
		    if (fechaBaja == null) {
				comboEtiquetasItem.setColor("#024eff");
			} else {
				if (fechaInicio.before(fechaActual)
						&& (fechaBaja.after(fechaActual) || fechaBaja.compareTo(fechaActual) == 0)) {
					comboEtiquetasItem.setColor("#024eff");
				} else if (fechaActual.before(fechaInicio) && fechaBaja.after(fechaInicio)) {
					comboEtiquetasItem.setColor("#40E0D0");
				} else if (fechaInicio.before(fechaBaja) && fechaActual.after(fechaBaja)) {
					comboEtiquetasItem.setColor("#f70000");
				}
			} 
		}
		
		comboEtiquetasDTO.setComboEtiquetasItems(comboEtiquetasItems);
		LOGGER.info("getLabelPerson() -> Salida del servicio para obtener etiquetas de una persona jurídica");
		return comboEtiquetasDTO;
	}


	@Override
	public ParametroColegioDTO searchParametroColegio(StringDTO stringDTO, HttpServletRequest request) {
		
		ParametroColegioDTO parametroColegioDTO = new ParametroColegioDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				
		// obtenemos un parámetro general necesario para ocultar/mostrar la auditoria
		String parametro = stringDTO.getValor();
		//String parametro = "OCULTAR_MOTIVO_MODIFICACION";
		
		LOGGER.info(
				"searchParametroColegio() / genParametrosExtendsMapper.selectParametroPorInstitucion() -> Entrada a genParametrosExtendsMapper para obtener un parámetro de un colegio");
		
		stringDTO = genParametrosExtendsMapper.selectParametroPorInstitucion(parametro, String.valueOf(idInstitucion));
		LOGGER.info(
				"searchParametroColegio() / genParametrosExtendsMapper.selectParametroPorInstitucion() -> Salida de genParametrosExtendsMapper para obtener un parámetro de un colegio");
		
		
		if(UtilidadesString.esCadenaVacia(stringDTO.getValor())) {
			parametroColegioDTO.setError(new Error("no hay valor para el parametro especificado"));
		}
		
		parametroColegioDTO.setParametro(stringDTO.getValor());
		
		
		return parametroColegioDTO;
	}
	
	@Override
	public ParametroColegioDTO searchProperty(StringDTO stringDTO, HttpServletRequest request) {
		
		ParametroColegioDTO parametroColegioDTO = new ParametroColegioDTO();
		
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
		
				String parametro = stringDTO.getValor();
				GenPropertiesExample example = new GenPropertiesExample();
				example.createCriteria().andParametroEqualTo(parametro).andFicheroEqualTo("SIGA");
				
				LOGGER.info(
						"searchProperty() / genPropertiesMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener un parámetro de un colegio");
				
				List<GenProperties> properties = genPropertiesMapper.selectByExample(example);
				
				
				LOGGER.info(
						"searchProperty() / genPropertiesMapper.selectByExample() -> Salida de genParametrosExtendsMapper para obtener un parámetro de un colegio");
				
				
				if(properties == null || properties.isEmpty()) {
					parametroColegioDTO.setError(new Error("no hay valor para el property especificado"));
				}else {
					parametroColegioDTO.setParametro(properties.get(0).getValor());
				}
			}else {
				parametroColegioDTO.error(new Error("No existen usuarios con DNI: "+  dni ));
			}
		} else {
			parametroColegioDTO.error(new Error("Institución no encontrada"));
		}
		
		
		return parametroColegioDTO;
	}


	

}
