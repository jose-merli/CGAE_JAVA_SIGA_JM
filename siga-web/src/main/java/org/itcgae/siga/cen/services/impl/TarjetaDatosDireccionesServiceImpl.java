package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaDireccionesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenPoblacionesExample;
import org.itcgae.siga.db.mappers.CenPoblacionesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPaisExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipoDireccionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarjetaDatosDireccionesServiceImpl implements ITarjetaDatosDireccionesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosDireccionesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenPoblacionesMapper cenPoblacionesMapper;
	
	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	
	@Autowired
	private CenPaisExtendsMapper cenPaisExtendsMapper;
	
	@Autowired
	private CenTipoDireccionExtendsMapper cenTipoDireccionExtendsMapper;
	
	
	@Override
	public DatosDireccionesDTO datosDireccionesSearch(int numPagina, DatosDireccionesSearchDTO datosDireccionesSearchDTO,	HttpServletRequest request) {
		LOGGER.info("searchBanksData() -> Entrada al servicio para la búsqueda por filtros de direcciones");
		
		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de direcciones");
				datosDireccionesItem = cenDireccionesExtendsMapper.selectDirecciones(datosDireccionesSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de direcciones");

				datosDireccionesDTO.setDatosDireccionesItem(datosDireccionesItem);;
			} 
			else {
				LOGGER.warn("datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("datosDireccionesSearch() -> idInstitucion del token nula");
		}
		
		LOGGER.info("datosDireccionesSearch() -> Salida del servicio para la búsqueda por filtros de direcciones");
		return datosDireccionesDTO;
	}	
	@Override
	public UpdateResponseDTO deleteDireccion(TarjetaDireccionesUpdateDTO[] tarjetaDireccionesUpdateDTO,
			HttpServletRequest request) {
		
		LOGGER.info("updateMember() -> Entrada al servicio para actualizar información de direcciones");
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
			for (int i = 0; i < tarjetaDireccionesUpdateDTO.length; i++) {
				LOGGER.info(
						"getCargos() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar datos de un direcciones");
				CenDirecciones recordUpdate = new CenDirecciones();
				recordUpdate.setFechabaja(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIddireccion(Long.valueOf(tarjetaDireccionesUpdateDTO[i].getIdDireccion()));
				recordUpdate.setIdinstitucion(idInstitucion);
				recordUpdate.setIdpersona(Long.valueOf(tarjetaDireccionesUpdateDTO[i].getIdPersona()));
				response = cenDireccionesExtendsMapper.updateByPrimaryKeySelective(recordUpdate );
				
				LOGGER.info(
						"getCargos() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para actualizar datos de un direcciones");
				
				updateResponseDTO.setStatus(SigaConstants.OK);
				if(response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"getCargos() / cenDireccionesExtendsMapper.updateMember() -> " + updateResponseDTO.getStatus() + ". No se pudo actualizar datos de un direcciones");
					
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
	@Override
	public ComboDTO getPais(HttpServletRequest request) {

		LOGGER.info(
				"getPais() -> Entrada al servicio para obtener los tipos de direccion disponibles");
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
					"getPais() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getPais() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = cenPaisExtendsMapper.selectPais(usuario.getIdlenguaje());
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
			
		}
		LOGGER.info(
				"getPais() -> Salida del servicio para obtener los tipos de direccion disponibles");
		
		return comboDTO;
	}
	@Override
	public ComboDTO getPoblacion(HttpServletRequest request, String idProvincia) {
		 
		ComboDTO poblacionesReturn = new ComboDTO();
		
		CenPoblacionesExample example  = new CenPoblacionesExample();
		example.createCriteria().andIdprovinciaEqualTo(idProvincia);
		example.setOrderByClause("NOMBRE");
		List<CenPoblaciones> poblaciones = cenPoblacionesMapper.selectByExample(example  );
		if (null != poblaciones && poblaciones.size()>0) {
			List<ComboItem> combooItems = new ArrayList<ComboItem>();
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			combooItems.add(comboItem);

			for (CenPoblaciones cenPoblaciones : poblaciones) {
				comboItem = new ComboItem();
				comboItem.setLabel(cenPoblaciones.getNombre());
				comboItem.setValue(cenPoblaciones.getIdpoblacion());
				combooItems.add(comboItem);
			}
		
			poblacionesReturn.setCombooItems(combooItems);
		}
		
		
		return poblacionesReturn;
	}
	@Override
	public ComboDTO getTipoDireccion(HttpServletRequest request) {

		LOGGER.info(
				"getTipoDireccion() -> Entrada al servicio para obtener los tipos de direccion disponibles");
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
					"getTipoDireccion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTipoDireccion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = cenTipoDireccionExtendsMapper.selectTipoDireccion(usuario.getIdlenguaje());
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
			
		}
		LOGGER.info(
				"getTipoDireccion() -> Salida del servicio para obtener los tipos de direccion disponibles");
		
		return comboDTO;
	}






	@Override
	public UpdateResponseDTO updateDirection(DatosDireccionesItem datosDireccionesItem,
			HttpServletRequest request) {
		
		LOGGER.info("insertBanksData() -> Entrada al servicio para insertar cuentas bancarias");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean tieneSCSJ = Boolean.FALSE;
		boolean tieneCargo = Boolean.FALSE;
		boolean tieneAbono = Boolean.FALSE;
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"insertBanksData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"insertBanksData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				
				// información a insertar
				//Obtenemos el nuevo idCuenta
				
				CenDireccionesKey key = new  CenDireccionesKey();
				key.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
				key.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
				key.setIdinstitucion(Short.valueOf(idInstitucion));
				CenDirecciones direcciones = cenDireccionesExtendsMapper.selectByPrimaryKey(key);
				
				
				direcciones.setFechamodificacion(new Date());
				direcciones.setUsumodificacion(usuario.getIdusuario());
				direcciones.setCodigopostal(datosDireccionesItem.getCodigoPostal());
				direcciones.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
				direcciones.setDomicilio(datosDireccionesItem.getDomicilio());
				direcciones.setFax1(datosDireccionesItem.getFax());
				direcciones.setIdpais(datosDireccionesItem.getIdPais());
				direcciones.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
				direcciones.setIdprovincia(datosDireccionesItem.getIdProvincia());
				direcciones.setMovil(datosDireccionesItem.getMovil());
				direcciones.setOtraprovincia(Short.valueOf(datosDireccionesItem.getOtraProvincia()));
				direcciones.setPaginaweb(datosDireccionesItem.getPaginaWeb());
				direcciones.setTelefono1(datosDireccionesItem.getTelefono());
/*
				//Gestionamos los abonos que nos llegan
				if (null != datosDireccionesItem.get && datosBancariosInsertDTO.getTipoCuenta().length>0) {

					for (String uso : datosBancariosInsertDTO.getTipoCuenta()) {
						if (uso.equals("S")) {
							cuentaBancaria.setAbonosjcs("1");
							tieneSCSJ = Boolean.TRUE;
						}
						if (uso.equals("C")) {
							tieneCargo = Boolean.TRUE;
						
						}
						if (uso.equals("A")) {
							tieneAbono = Boolean.TRUE;
						
						}
					}
					if (!tieneSCSJ) {
						cuentaBancaria.setAbonosjcs("0");
					}
					if (tieneCargo && tieneAbono) {
						cuentaBancaria.setAbonocargo("T");
						
					}else if(tieneCargo) {
						cuentaBancaria.setAbonocargo("C");
					}else if(tieneAbono) {
						cuentaBancaria.setAbonocargo("A");
					}else if(!tieneCargo && !tieneAbono) {
						cuentaBancaria.setAbonocargo("");
					}
				}
	*/			
				
				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
				response = cenDireccionesExtendsMapper.updateByPrimaryKeySelective(direcciones);
				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
		
				// comprobacion actualización
				if(response >= 1) {
					LOGGER.info("insertBanksData() -> OK. Insert para cuentas bancarias realizado correctamente");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
				else {
					LOGGER.info("insertBanksData() -> KO. Insert para cuentas bancarias  NO realizado correctamente");
					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al insertar la cuenta Bancaria");
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}
				
				
			} else {
				LOGGER.warn(
						"insertBanksData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		
		} else {
			LOGGER.warn("insertBanksData() -> idInstitucion del token nula");
		}
		
		
		LOGGER.info("insertBanksData() -> Salida del servicio para insertar cuentas bancarias ");
		return updateResponseDTO;
	}


	@Override
	public InsertResponseDTO createDirection(DatosDireccionesItem tarjetaIntegrantesCreateDTO,
			HttpServletRequest request) {
		
		LOGGER.info("updateMember() -> Entrada al servicio para crear un nuevo integrante");
		InsertResponseDTO updateResponseDTO = new InsertResponseDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		ComboItem comboItem = new ComboItem();
		/*
		
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
		
		*/
		LOGGER.info("updateMember() -> Salida del servicio para crear un nuevo integrante");
		return updateResponseDTO;
	}
	




	





}
