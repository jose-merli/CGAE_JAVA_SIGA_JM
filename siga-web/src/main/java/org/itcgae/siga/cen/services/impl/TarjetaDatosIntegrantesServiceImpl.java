package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenComponentes;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarjetaDatosIntegrantesServiceImpl implements ITarjetaDatosIntegrantesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosIntegrantesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;
	
	@Autowired
	private CenProvinciasExtendsMapper cenProvinciasExtendsMapper;
	
	@Autowired
	private CenCargoExtendsMapper cenCargoExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;

	@Override
	public DatosIntegrantesDTO searchIntegrantesData(int numPagina, DatosIntegrantesSearchDTO datosIntegrantesSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchIntegrantesData() -> Entrada al servicio para la búsqueda por filtros de integrantes ");
		
		List<DatosIntegrantesItem> datosIntegrantesItem = new ArrayList<DatosIntegrantesItem>();
		DatosIntegrantesDTO datosIntegrantesDTO = new DatosIntegrantesDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de integrantes ");
				datosIntegrantesSearchDTO.setIdInstitucion(idInstitucion.toString());
				datosIntegrantesItem = cenComponentesExtendsMapper.selectIntegrantes(datosIntegrantesSearchDTO);
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de integrantes ");

				datosIntegrantesDTO.setDatosIntegrantesItem(datosIntegrantesItem);
			} 
			else {
				LOGGER.warn("searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchIntegrantesData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchIntegrantesData() -> Salida del servicio para la búsqueda por filtros de integrantes ");
		return datosIntegrantesDTO;
	}


	@Override
	public ComboDTO getProvinces(HttpServletRequest request) {
		LOGGER.info("getProvinces() -> Entrada al servicio para búsqueda de las provincias");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Entrada a cenProvinciasExtendsMapper para obtener listado de provincias ");
		comboItems = cenProvinciasExtendsMapper.selectDistinctProvinces();
		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Salida de cenProvinciasExtendsMapper para obtener listado de provincias ");
		
		
		if(null != comboItems && comboItems.size() > 0) {
			ComboItem element = new ComboItem();
			element.setLabel("");
			element.setValue("");
			comboItems.add(0, element);
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getProvinces() -> Salida al servicio para búsqueda de las provincias");
		return comboDTO;
	}


	@Override
	public ComboDTO getCargos(HttpServletRequest request) {
		LOGGER.info("getCargos() -> Entrada al servicio para búsqueda de cargos");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				usuario = usuarios.get(0);
				
				LOGGER.info(
						"getCargos() / cenCargoExtendsMapper.getCargos() -> Entrada a cenCargoExtendsMapper para obtener listado de cargos");
				comboItems = cenCargoExtendsMapper.getCargos(usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				LOGGER.info(
						"getCargos() / cenCargoExtendsMapper.getCargos() -> Salida de cenCargoExtendsMapper para obtener listado de cargos ");
				
				
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}
			}

		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCargos() -> Salida al servicio para búsqueda de cargos");
		return comboDTO;
	}


	@Override
	public UpdateResponseDTO updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO,
			HttpServletRequest request) {
		
		LOGGER.info("updateMember() -> Entrada al servicio para actualizar información de integrantes");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			
			LOGGER.info(
					"getCargos() / cenComponentesExtendsMapper.updateMember() -> Entrada a cenComponentesExtendsMapper para actualizar datos de un integrante");
			response = cenComponentesExtendsMapper.updateMember(tarjetaIntegrantesUpdateDTO, usuario, String.valueOf(idInstitucion));
			LOGGER.info(
					"getCargos() / cenComponentesExtendsMapper.updateMember() -> Salida de cenComponentesExtendsMapper para actualizar datos de un integrante");
			
			updateResponseDTO.setStatus(SigaConstants.OK);
			if(response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"getCargos() / cenComponentesExtendsMapper.updateMember() -> " + updateResponseDTO.getStatus() + ". No se pudo actualizar datos de un integrantes");
				
			}
		}
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}
		
		
		
		LOGGER.info("updateMember() -> Salida del servicio para actualizar información de integrantes");
		return updateResponseDTO;
	}


	@Override
	public UpdateResponseDTO createMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,
			HttpServletRequest request) {
		
		LOGGER.info("updateMember() -> Entrada al servicio para crear un nuevo integrante");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		ComboItem comboItem = new ComboItem();
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			
			// 1. Ya existe un idpersona para el nuevo integrante
			if(!tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante().equals(""))
			{
				int responseCenCliente = 1; // se puede crear o no => se inicializa a 1
				int responseCenComponentes = 0; // se debe crear siempre
				
				// 1.1 Comprobamos que existe en tabla cen_cliente
				CenCliente cenCliente = new CenCliente();
				CenClienteKey key = new CenClienteKey();
				key.setIdinstitucion(Short.valueOf(tarjetaIntegrantesCreateDTO.getIdInstitucionIntegrante()));
				key.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));
				cenCliente = cenClienteMapper.selectByPrimaryKey(key);
				
				// 1.2 Si no existe, se crea un registro
				if(null == cenCliente) {
					CenCliente record = new CenCliente();
					record = rellenarInsertCenCliente(tarjetaIntegrantesCreateDTO, usuario);
					responseCenCliente = cenClienteMapper.insertSelective(record);
				}
				
				if(responseCenCliente == 1) {
					// 1.3 Comprobar si esta en cen_nocolegiado/cen_colegiado (no se hace ahora mismo)
					
					// 1.4 Insertamos un registro en tabla cen_componentes
					comboItem = cenComponentesExtendsMapper.selectMaxIDComponente(tarjetaIntegrantesCreateDTO.getIdPersonaPadre(), String.valueOf(idInstitucion));
					int siguienteIDComponente;
					if(null != comboItem) {
						siguienteIDComponente = Integer.valueOf(comboItem.getValue()) + 1;
					}
					else {
						siguienteIDComponente = 1;
					}
					
					tarjetaIntegrantesCreateDTO.setIdComponente(String.valueOf(siguienteIDComponente));
					responseCenComponentes = cenComponentesExtendsMapper.insertSelectiveForcreateMember(tarjetaIntegrantesCreateDTO, usuario, String.valueOf(idInstitucion));
					
					if(responseCenComponentes == 1) {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
					else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				}
				else {
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}
			else {
				// 2. No existe un idpersona para el nuevo integrante
				
				// 2.1. Insertar en cen_persona
				int responseCenPersona = 0;
				int responseCenCliente = 0;
				int responseCenComponentes = 0;
				CrearPersonaDTO crearPersonaDTO = new CrearPersonaDTO();
				crearPersonaDTO = rellenarInsertCenPersona(tarjetaIntegrantesCreateDTO, usuario);
				responseCenPersona = cenPersonaExtendsMapper.insertSelectiveForPersonFile(crearPersonaDTO, usuario);
				
				if(responseCenPersona == 1) {
					// 2.2. Insertar en cen_cliente
					
					comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
					String maxIdPersona = comboItems.get(0).getValue();
					
					tarjetaIntegrantesCreateDTO.setIdPersonaIntegrante(maxIdPersona);
					tarjetaIntegrantesCreateDTO.setIdInstitucionIntegrante(String.valueOf(idInstitucion));
					
					CenCliente record = new CenCliente();
					record = rellenarInsertCenCliente(tarjetaIntegrantesCreateDTO, usuario);
					responseCenCliente = cenClienteMapper.insertSelective(record);
					if(responseCenCliente == 1) {
						// 2.3. Insertar en cen_nocolegiado/cen_colegiado { tipo = session de busqueda no colegiados (para busqueda juridica) || tipo Personal = '1' (para busqueda fisica) 
						// PUNTO 2.3 AHORA MISMO NO SE HACE
						// 2.4. Insertar en cen_componente
						comboItem = cenComponentesExtendsMapper.selectMaxIDComponente(tarjetaIntegrantesCreateDTO.getIdPersonaPadre(), String.valueOf(idInstitucion));
						int siguienteIDComponente;
						if(null != comboItem) {
							siguienteIDComponente = Integer.valueOf(comboItem.getValue()) + 1;
						}
						else {
							siguienteIDComponente = 1;
						}
						
						tarjetaIntegrantesCreateDTO.setIdComponente(String.valueOf(siguienteIDComponente));
						responseCenComponentes = cenComponentesExtendsMapper.insertSelectiveForcreateMember(tarjetaIntegrantesCreateDTO, usuario, String.valueOf(idInstitucion));
						if(responseCenComponentes == 1) {
							updateResponseDTO.setStatus(SigaConstants.OK);
						}
						else {
							updateResponseDTO.setStatus(SigaConstants.KO);
						}
						
					}
					else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
					
				}
				else {
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
				
				
			}
		}
		
		
		LOGGER.info("updateMember() -> Salida del servicio para crear un nuevo integrante");
		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO deleteMember(TarjetaIntegrantesUpdateDTO[] tarjetaIntegrantesUpdateDTO,
			HttpServletRequest request) {
		
		LOGGER.info("updateMember() -> Entrada al servicio para actualizar información de integrantes");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			for (int i = 0; i < tarjetaIntegrantesUpdateDTO.length; i++) {
				LOGGER.info(
						"getCargos() / cenComponentesExtendsMapper.updateMember() -> Entrada a cenComponentesExtendsMapper para actualizar datos de un integrante");
				CenComponentes recordUpdate = new CenComponentes();
				recordUpdate.setFechabaja(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIdcomponente(Short.valueOf(tarjetaIntegrantesUpdateDTO[i].getIdComponente()));
				recordUpdate.setIdinstitucion(idInstitucion);
				recordUpdate.setIdcomponente(Short.valueOf(tarjetaIntegrantesUpdateDTO[i].getIdComponente()));
				response = cenComponentesExtendsMapper.updateByPrimaryKeySelective(recordUpdate );
				
				LOGGER.info(
						"getCargos() / cenComponentesExtendsMapper.updateMember() -> Salida de cenComponentesExtendsMapper para actualizar datos de un integrante");
				
				updateResponseDTO.setStatus(SigaConstants.OK);
				if(response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"getCargos() / cenComponentesExtendsMapper.updateMember() -> " + updateResponseDTO.getStatus() + ". No se pudo actualizar datos de un integrantes");
					
				}
			}
			
		}
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}
		
		
		
		LOGGER.info("updateMember() -> Salida del servicio para actualizar información de integrantes");
		return updateResponseDTO;
	}
	
	
	protected CenCliente rellenarInsertCenCliente(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,AdmUsuarios usuario) {
		CenCliente record = new CenCliente();
		
		record.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));
		record.setIdinstitucion(Short.valueOf(tarjetaIntegrantesCreateDTO.getIdInstitucionIntegrante()));
		record.setFechaalta(new Date());
		record.setCaracter("P");
		record.setPublicidad(SigaConstants.DB_FALSE);
		record.setGuiajudicial(SigaConstants.DB_FALSE);
		record.setComisiones(SigaConstants.DB_FALSE);
		record.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setIdlenguaje(usuario.getIdlenguaje());
		record.setExportarfoto(SigaConstants.DB_FALSE);
		
		return record;
	}
	
	protected  CrearPersonaDTO rellenarInsertCenPersona(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO, AdmUsuarios usuario) {
		CrearPersonaDTO crearPersonaDTO = new CrearPersonaDTO();
		
		crearPersonaDTO.setNombre(tarjetaIntegrantesCreateDTO.getNombre());
		crearPersonaDTO.setApellido1(tarjetaIntegrantesCreateDTO.getApellidos1());
		crearPersonaDTO.setApellido2(tarjetaIntegrantesCreateDTO.getApellidos2());
		crearPersonaDTO.setNif(tarjetaIntegrantesCreateDTO.getNifCif());
		crearPersonaDTO.setTipoIdentificacion(tarjetaIntegrantesCreateDTO.getTipoIdentificacion());
		
		return crearPersonaDTO;
	}


	

	
	
}
