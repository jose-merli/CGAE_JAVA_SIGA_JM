package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BancoBicDTO;
import org.itcgae.siga.DTOs.cen.BancoBicItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosAnexoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosAnexoItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosInsertDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchAnexosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosDownloadDTO;
import org.itcgae.siga.DTOs.cen.MandatosItem;
import org.itcgae.siga.DTOs.cen.MandatosUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosBancariosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancarias;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancarias;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancariasKey;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenAnexosCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenMandatosCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GenFicheroExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Service
public class TarjetaDatosBancariosServiceImpl implements ITarjetaDatosBancariosService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosBancariosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;
	
	@Autowired
	private CenMandatosCuentasbancariasMapper cenMandatosCuentasbancariasMapper;

	@Autowired
	private CenAnexosCuentasbancariasMapper cenAnexosCuentasbancariasMapper;
	
	
	@Autowired
	private GenFicheroExtendsMapper genFicheroExtendsMapper;

	@Autowired
	private GenRecursosMapper genRecursosMapper;
	

	@Override
	public DatosBancariosDTO searchBanksData(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchBanksData() -> Entrada al servicio para la búsqueda por filtros de cuentas bancarias");
		
		List<DatosBancariosItem> datosBancariosItem = new ArrayList<DatosBancariosItem>();
		DatosBancariosDTO datosBancariosDTO = new DatosBancariosDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchBanksData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchBanksData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchBanksData() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");
				datosBancariosItem = cenCuentasbancariasExtendsMapper.selectCuentasBancarias(datosBancariosSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"searchBanksData() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");

				datosBancariosDTO.setDatosBancariosItem(datosBancariosItem);;
			} 
			else {
				LOGGER.warn("searchBanksData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchBanksData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchBanksData() -> Salida del servicio para la búsqueda por filtros de cuentas bancarias");
		return datosBancariosDTO;
	}




	@Override
	public DeleteResponseDTO deleteBanksData(DatosBancariosDeleteDTO datosBancariosDeleteDTO,
			HttpServletRequest request) {
		
		LOGGER.info("deleteBanksData() -> Entrada al servicio para eliminar cuentas bancarias");
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
					"deleteBanksData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteBanksData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				// información a modificar
				CenCuentasbancarias cuentaBancaria = new CenCuentasbancarias();
				cuentaBancaria.setFechabaja(new Date());
				cuentaBancaria.setFechamodificacion(new Date());
				cuentaBancaria.setUsumodificacion(usuario.getIdusuario());
				
				// filtrado para sentencia sql
				List <Short> idCuentasDelete = new ArrayList<Short>();
				for(int i=0; i<datosBancariosDeleteDTO.getIdCuentas().length; i++) {
					idCuentasDelete.add(Short.valueOf(datosBancariosDeleteDTO.getIdCuentas()[i]));
				}
				
				CenCuentasbancariasExample cenCuentasbancariasDelete = new CenCuentasbancariasExample();
				cenCuentasbancariasDelete.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(datosBancariosDeleteDTO.getIdPersona())).andIdcuentaIn(idCuentasDelete);
			
				LOGGER.info(
						"deleteBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para eliminar cuentas bancarias");
				response = cenCuentasbancariasExtendsMapper.updateByExampleSelective(cuentaBancaria, cenCuentasbancariasDelete);
				LOGGER.info(
						"deleteBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para eliminar cuentas bancarias");

			} else {
				LOGGER.warn(
						"deleteBanksData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("deleteBanksData() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("deleteBanksData() -> OK. Delete para cuentas bancarias realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		}
		else {
			LOGGER.info("deleteBanksData() -> KO. Delete para cuentas bancarias  NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteBanksData() -> Salida del servicio para eliminar cuentas bancarias ");
		return deleteResponseDTO;
	}




	@Override
	public DatosBancariosDTO searchGeneralData(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchGeneralData() -> Entrada al servicio para la búsqueda por filtros de cuentas bancarias");
		
		List<DatosBancariosItem> datosBancariosItem = new ArrayList<DatosBancariosItem>();
		DatosBancariosDTO datosBancariosDTO = new DatosBancariosDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchGeneralData() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");
				datosBancariosItem = cenCuentasbancariasExtendsMapper.selectGeneralCuentasBancarias(datosBancariosSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"searchGeneralData() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");

				if (null != datosBancariosItem && datosBancariosItem.size() >0 ) {
					for (DatosBancariosItem datosBancarios : datosBancariosItem) {
						if (null != datosBancarios.getUso()) {
							List<String> usos = new ArrayList<String>();
							if (datosBancarios.getUso().contains("ABONO")) {
								usos.add("A");
							}
							if (datosBancarios.getUso().contains("CARGO")) {
								usos.add("C");
							}
							if (datosBancarios.getUso().contains("SJCS")) {
								usos.add("S");
							}
							if (null != usos && usos.size()>0) {
								String[] tiposCuenta = new String[usos.size()];
								int i = 0;
								for (String uso : usos) {
									tiposCuenta[i] = new String();
									tiposCuenta[i] = uso;
									i++;
								}
								datosBancarios.setTipoCuenta(tiposCuenta);
							}
							
						}
					}
				}
				datosBancariosDTO.setDatosBancariosItem(datosBancariosItem);;
			} 
			else {
				LOGGER.warn("searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchGeneralData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchBanksData() -> Salida del servicio para la búsqueda por filtros de cuentas bancarias");
		return datosBancariosDTO;
	}




	@Override
	public MandatosDTO searchMandatos(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchMandatos() -> Entrada al servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		
		List<MandatosItem> mandatosItem = new ArrayList<MandatosItem>();
		MandatosDTO mandatosDTO = new MandatosDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchMandatos() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de mandatos de cuentas bancarias");
				mandatosItem = cenCuentasbancariasExtendsMapper.selectMandatos(datosBancariosSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"searchMandatos() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de mandatos de cuentas bancarias");

				mandatosDTO.setMandatosItem(mandatosItem);;
			} 
			else {
				LOGGER.warn("searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchMandatos() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchMandatos() -> Salida del servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		return mandatosDTO;
	}




	@Override
	public InsertResponseDTO insertBanksData(DatosBancariosInsertDTO datosBancariosInsertDTO,
			HttpServletRequest request) throws Exception {
		
		LOGGER.info("insertBanksData() -> Entrada al servicio para insertar cuentas bancarias");
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean tieneSCSJ = Boolean.FALSE;
		boolean tieneCargo = Boolean.FALSE;
		boolean tieneAbono = Boolean.FALSE;
		Short idCuenta= 1;
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
				
				List<DatosBancariosItem> newIdCuenta = cenCuentasbancariasExtendsMapper.selectNewIdCuenta(datosBancariosInsertDTO.getIdPersona());
				if (null != newIdCuenta && newIdCuenta.size() > 0 ) {
					if (null!= newIdCuenta.get(0)) {
						idCuenta = Short.valueOf(newIdCuenta.get(0).getIdCuenta());
					}
					
				}
				CenCuentasbancarias cuentaBancaria = new CenCuentasbancarias();
				
				cuentaBancaria.setFechamodificacion(new Date());
				cuentaBancaria.setUsumodificacion(usuario.getIdusuario());
				cuentaBancaria.setCuentacontable(datosBancariosInsertDTO.getCuentaContable());
				cuentaBancaria.setIban(datosBancariosInsertDTO.getIban());
				cuentaBancaria.setIdcuenta(idCuenta);
				cuentaBancaria.setIdinstitucion(idInstitucion);
				cuentaBancaria.setIdpersona(Long.valueOf(datosBancariosInsertDTO.getIdPersona()));
				cuentaBancaria.setTitular(datosBancariosInsertDTO.getTitular());

				//Gestionamos los abonos que nos llegan
				if (null != datosBancariosInsertDTO.getTipoCuenta() && datosBancariosInsertDTO.getTipoCuenta().length>0) {

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
				
				
				cuentaBancaria.setCboCodigo(datosBancariosInsertDTO.getIban().substring(4, 8));
				cuentaBancaria.setCodigosucursal(datosBancariosInsertDTO.getIban().substring(8, 12));
				cuentaBancaria.setDigitocontrol(datosBancariosInsertDTO.getIban().substring(12, 14));
				cuentaBancaria.setNumerocuenta(datosBancariosInsertDTO.getIban().substring(14, 24));			

				
				//Si se ha marcado el check abono SJCS se comprueba si existe otra cuenta que ya es abono SJCS
				if(tieneSCSJ){
					CenCuentasbancariasExample example = new CenCuentasbancariasExample();
					example.createCriteria().andIdpersonaEqualTo(Long.valueOf(datosBancariosInsertDTO.getIdPersona())).andIdinstitucionEqualTo(idInstitucion).andAbonosjcsEqualTo("1");
					List<CenCuentasbancarias> cuenta = cenCuentasbancariasExtendsMapper.selectByExample(example );
					
					
					//if (cuentasAdm.existeCuentaAbonoSJCS(beanCuentas.getIdPersona(), beanCuentas.getIdInstitucion(), beanCuentas.getIdCuenta())) {
					if (null != cuenta && !cuenta.isEmpty()) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("messages.censo.existeAbonoSJCS");
						insertResponseDTO.setError(error);
						return insertResponseDTO;
					}
				}
				
				
				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
				response = cenCuentasbancariasExtendsMapper.insertSelective(cuentaBancaria);
				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
		
				// comprobacion actualización
				if(response >= 1) {
					LOGGER.info("insertBanksData() -> OK. Insert para cuentas bancarias realizado correctamente");
					insertResponseDTO.setStatus(SigaConstants.OK);
					insertResponseDTO.setId(idCuenta.toString());
				}
				else {
					LOGGER.info("insertBanksData() -> KO. Insert para cuentas bancarias  NO realizado correctamente");
					insertResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al insertar la cuenta Bancaria");
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}
				
				//Si se ha generado correctamente el registro, procedemos a generar los mandatos.
				
		
				
				// Se insertan dos mandatos nuevos a la cuenta, uno para productos y otro para servicios
				if (tieneCargo) {
					Object[] paramMandatos = new Object[4];
					paramMandatos[0] = idInstitucion.toString();
					paramMandatos[1] = datosBancariosInsertDTO.getIdPersona();
					paramMandatos[2] = idCuenta.toString();
					paramMandatos[3] = usuario.getIdusuario().toString();
					
					String resultado[] = new String[2];
					resultado = callPLProcedure("{call PKG_SIGA_CARGOS.InsertarMandatos(?,?,?,?,?,?)}", 2, paramMandatos);
					if (resultado == null) {
						LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("Error al insertar los mandatos de las cuentas");
						insertResponseDTO.setError(error);
						return insertResponseDTO;
						
					} else {
						if (resultado[0].equals("1")) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							insertResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("messages.censo.direcciones.facturacion");
							insertResponseDTO.setError(error);
							return insertResponseDTO;
							
						} else if (resultado[0].equals("2")) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							insertResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("messages.censo.direcciones.facturacion");
							insertResponseDTO.setError(error);
							return insertResponseDTO;
							
						} else if (!resultado[0].equals("0")) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							insertResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("Error al insertar los mandatos de las cuentas");
							insertResponseDTO.setError(error);
							return insertResponseDTO;
						}
					}
				}
				

				//Se comprueba si se deben revisar las cuentas y se ejecutan los scripts que se encargan de ello
				
				// Lanzamos el proceso de revision de suscripciones del letrado 
				String resultado[] = ejecutarPL_RevisionSuscripcionesLetrado(""+idInstitucion.toString(),
																						  ""+datosBancariosInsertDTO.getIdPersona(),
																						  "",
																						  ""+ usuario.getIdusuario().toString());
				if ((resultado == null) || (!resultado[0].equals("0"))){
					insertResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO"+resultado[1]);
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}
				
				// Este proceso se encarga de actualizar las cosas pendientes asociadas a la cuenta de la persona 
				String[] resultado1 = ejecutarPL_Revision_Cuenta(
					""+idInstitucion.toString(),
					  ""+datosBancariosInsertDTO.getIdPersona(),
					  ""+idCuenta.toString(),
					  ""+ usuario.getIdusuario().toString());
				if (resultado1 == null || !resultado1[0].equals("0")) {

					insertResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_CUENTA" + resultado[1]);
					insertResponseDTO.setError(error);
					return insertResponseDTO;

				}
				
				// Comprueba si va a lanzar el proceso que asocia las suscripciones activas con forma de pago en metalico a la nueva cuenta bancaria
				if (datosBancariosInsertDTO.getRevisionCuentas()) { 
					// Este proceso asocia las suscripciones activas con forma de pago en metalico a la nueva cuenta bancaria 
					resultado1 = ejecutarPL_AltaCuentaCargos(
						""+idInstitucion.toString(),
						  ""+datosBancariosInsertDTO.getIdPersona(),
						  ""+idCuenta.toString(),
						  ""+ usuario.getIdusuario().toString());
					if (resultado1 == null || !resultado1[0].equals("0")) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_ALTA_CUENTA_CARGOS" + resultado[1]);
						insertResponseDTO.setError(error);
						return insertResponseDTO;
					}
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
		return insertResponseDTO;
	}



	@Override
	public ComboDTO getLabelEsquema(HttpServletRequest request) {
		LOGGER.info("getLabelEsquema() -> Entrada al servicio para la búsqueda de esquemas de mandatos");
		
		ComboDTO combo = new ComboDTO();
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
					"getLabelEsquema() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if(null != usuarios && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getLabelEsquema() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de esquemas de mandatos");
				comboItems = cenCuentasbancariasExtendsMapper.getComboEsquemas(usuario.getIdlenguaje());
				LOGGER.info(
						"getLabelEsquema() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de esquemas de mandatos");
				if (!comboItems.equals(null) && comboItems.size() > 0) {
					// añade elemento vacio al principio para el dropdown de parte front
					ComboItem comboItem = new ComboItem();
					comboItem.setLabel("");
					comboItem.setValue("");
					comboItems.add(0, comboItem);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		
		
		LOGGER.info("getLabelEsquema() -> Salida del servicio para la búsqueda de esquemas de mandatos");
		return combo;

	}




	@Override
	public UpdateResponseDTO updateMandatos(MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) {
		
		LOGGER.info("updateMandatos() -> Entrada al servicio para actualizar mandatos");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateMandatos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateMandatos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
					
				// información a modificar
				CenMandatosCuentasbancarias record = new CenMandatosCuentasbancarias();
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuario.getIdusuario());
				record.setIdmandato(Short.valueOf(mandatosUpdateDTO.getIdMandato()));
				record.setIdcuenta(Short.valueOf(mandatosUpdateDTO.getIdCuenta()));
				record.setIdpersona(Long.valueOf(mandatosUpdateDTO.getIdPersona()));
				record.setIdinstitucion(Short.valueOf(idInstitucion));
				record.setEsquema(Short.valueOf(mandatosUpdateDTO.getEsquema()));
				if (null != mandatosUpdateDTO.getEsquema() && mandatosUpdateDTO.getEsquema().equals("2")) {
					record.setAutorizacionb2b(Short.valueOf("1"));	
				}
				// filtrado para sentencia sql
			
				LOGGER.info(
						"updateMandatos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar mandatos");
				
				response = cenMandatosCuentasbancariasMapper.updateByPrimaryKeySelective(record);
				
				LOGGER.info(
						"updateMandatos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar mandatos");

			} else {
				LOGGER.warn(
						"updateMandatos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("updateMandatos() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("updateMandatos() -> OK. Update para mandatos realizado correctamente");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		else {
			LOGGER.info("updateMandatos() -> KO. Update para mandatos  NO realizado correctamente");
			updateResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteBanksData() -> Salida del servicio para actualizar mandatos ");
		return updateResponseDTO;
	}




	@Override
	public BancoBicDTO searchBanks(DatosBancariosSearchBancoDTO datosBancariosSearchBancoDTO,
			HttpServletRequest request) {
		LOGGER.info("searchMandatos() -> Entrada al servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		
		List<BancoBicItem> bancoBicItem = new ArrayList<BancoBicItem>();
		BancoBicDTO bancoBic = new BancoBicDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchMandatos() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de mandatos de cuentas bancarias");
				datosBancariosSearchBancoDTO.setiban(datosBancariosSearchBancoDTO.getiban().substring(4, 8));
				bancoBicItem = cenCuentasbancariasExtendsMapper.selectBanks(datosBancariosSearchBancoDTO);
				LOGGER.info(
						"searchMandatos() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de mandatos de cuentas bancarias");

				bancoBic.setBancoBicItem(bancoBicItem);;
			} 
			else {
				LOGGER.warn("searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchMandatos() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchMandatos() -> Salida del servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		return bancoBic;
	}
	
	
	@Override
	public UpdateResponseDTO updateBanksData(DatosBancariosInsertDTO datosBancariosInsertDTO,
			HttpServletRequest request) throws Exception {
		
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
				
				CenCuentasbancariasKey key = new  CenCuentasbancariasKey();
				key.setIdcuenta(Short.valueOf(datosBancariosInsertDTO.getIdCuenta()));
				key.setIdpersona(Long.valueOf(datosBancariosInsertDTO.getIdPersona()));
				key.setIdinstitucion(Short.valueOf(idInstitucion));
				CenCuentasbancarias cuentaBancaria = cenCuentasbancariasExtendsMapper.selectByPrimaryKey(key);
				
				
				cuentaBancaria.setFechamodificacion(new Date());
				cuentaBancaria.setUsumodificacion(usuario.getIdusuario());
				cuentaBancaria.setCuentacontable(datosBancariosInsertDTO.getCuentaContable());
				cuentaBancaria.setIban(datosBancariosInsertDTO.getIban());

				cuentaBancaria.setTitular(datosBancariosInsertDTO.getTitular());

				//Gestionamos los abonos que nos llegan
				if (null != datosBancariosInsertDTO.getTipoCuenta() && datosBancariosInsertDTO.getTipoCuenta().length>0) {

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
				
				
				cuentaBancaria.setCboCodigo(datosBancariosInsertDTO.getIban().substring(4, 8));
				cuentaBancaria.setCodigosucursal(datosBancariosInsertDTO.getIban().substring(8, 12));
				cuentaBancaria.setDigitocontrol(datosBancariosInsertDTO.getIban().substring(12, 14));
				cuentaBancaria.setNumerocuenta(datosBancariosInsertDTO.getIban().substring(14, 24));			

				
				//Si se ha marcado el check abono SJCS se comprueba si existe otra cuenta que ya es abono SJCS
				if(tieneSCSJ){
					CenCuentasbancariasExample example = new CenCuentasbancariasExample();
					example.createCriteria().andIdpersonaEqualTo(Long.valueOf(datosBancariosInsertDTO.getIdPersona())).andIdinstitucionEqualTo(idInstitucion).andAbonosjcsEqualTo("1");
					List<CenCuentasbancarias> cuenta = cenCuentasbancariasExtendsMapper.selectByExample(example );
					
					
					//if (cuentasAdm.existeCuentaAbonoSJCS(beanCuentas.getIdPersona(), beanCuentas.getIdInstitucion(), beanCuentas.getIdCuenta())) {
					if (null != cuenta && cuenta.size()>0) {
						if (!cuenta.get(0).getIdcuenta().equals(Short.valueOf(datosBancariosInsertDTO.getIdCuenta()))) {
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("messages.censo.existeAbonoSJCS");
							updateResponseDTO.setError(error);
							return updateResponseDTO;
						}

					}
				
				}
				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
				response = cenCuentasbancariasExtendsMapper.updateByPrimaryKeySelective(cuentaBancaria);
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
				
				//Si se ha generado correctamente el registro, procedemos a generar los mandatos.
				
		
				
				// Se insertan dos mandatos nuevos a la cuenta, uno para productos y otro para servicios
				if (tieneCargo) {
					
					CenMandatosCuentasbancariasExample exampleMandatos = new CenMandatosCuentasbancariasExample();
					exampleMandatos.createCriteria().andIdcuentaEqualTo(Short.valueOf(datosBancariosInsertDTO.getIdCuenta())).andIdpersonaEqualTo(Long.valueOf(datosBancariosInsertDTO.getIdPersona()));
					List<CenMandatosCuentasbancarias> mandatos = cenMandatosCuentasbancariasMapper.selectByExample(exampleMandatos );
					if (!(null != mandatos && mandatos.size()>0)) {
						Object[] paramMandatos = new Object[4];
						paramMandatos[0] = idInstitucion.toString();
						paramMandatos[1] = datosBancariosInsertDTO.getIdPersona();
						paramMandatos[2] = datosBancariosInsertDTO.getIdCuenta();
						paramMandatos[3] = usuario.getIdusuario().toString();
						
						String resultado[] = new String[2];
						resultado = callPLProcedure("{call PKG_SIGA_CARGOS.InsertarMandatos(?,?,?,?,?,?)}", 2, paramMandatos);
						if (resultado == null) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("Error al insertar los mandatos de las cuentas");
							updateResponseDTO.setError(error);
							return updateResponseDTO;
							
						} else {
							if (resultado[0].equals("1")) {
								LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
								updateResponseDTO.setStatus(SigaConstants.KO);
								error.setMessage("messages.censo.direcciones.facturacion");
								updateResponseDTO.setError(error);
								return updateResponseDTO;
								
							} else if (resultado[0].equals("2")) {
								LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
								updateResponseDTO.setStatus(SigaConstants.KO);
								error.setMessage("messages.censo.direcciones.facturacion");
								updateResponseDTO.setError(error);
								return updateResponseDTO;
								
							} else if (!resultado[0].equals("0")) {
								LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
								updateResponseDTO.setStatus(SigaConstants.KO);
								error.setMessage("Error al insertar los mandatos de las cuentas");
								updateResponseDTO.setError(error);
								return updateResponseDTO;
							}
						}
					}
				}
				

				//Se comprueba si se deben revisar las cuentas y se ejecutan los scripts que se encargan de ello
				
				// Lanzamos el proceso de revision de suscripciones del letrado 
				String resultado[] = ejecutarPL_RevisionSuscripcionesLetrado(""+idInstitucion.toString(),
																						  ""+datosBancariosInsertDTO.getIdPersona(),
																						  "",
																						  ""+ usuario.getIdusuario().toString());
				if ((resultado == null) || (!resultado[0].equals("0"))){
					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO"+resultado[1]);
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}
				
				// Este proceso se encarga de actualizar las cosas pendientes asociadas a la cuenta de la persona 
				String[] resultado1 = ejecutarPL_Revision_Cuenta(
					""+idInstitucion.toString(),
					  ""+datosBancariosInsertDTO.getIdPersona(),
					  ""+datosBancariosInsertDTO.getIdCuenta(),
					  ""+ usuario.getIdusuario().toString());
				if (resultado1 == null || !resultado1[0].equals("0")) {

					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_CUENTA" + resultado[1]);
					updateResponseDTO.setError(error);
					return updateResponseDTO;

				}
				
				// Comprueba si va a lanzar el proceso que asocia las suscripciones activas con forma de pago en metalico a la nueva cuenta bancaria
				if (datosBancariosInsertDTO.getRevisionCuentas()) { 
					// Este proceso asocia las suscripciones activas con forma de pago en metalico a la nueva cuenta bancaria 
					resultado1 = ejecutarPL_AltaCuentaCargos(
						""+idInstitucion.toString(),
						  ""+datosBancariosInsertDTO.getIdPersona(),
						  ""+datosBancariosInsertDTO.getIdCuenta(),
						  ""+ usuario.getIdusuario().toString());
					if (resultado1 == null || !resultado1[0].equals("0")) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_ALTA_CUENTA_CARGOS" + resultado[1]);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					}
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
	public DatosBancariosAnexoDTO searchAnexos(int numPagina,
			DatosBancariosSearchAnexosDTO datosBancariosSearchAnexosDTO, HttpServletRequest request) {
		LOGGER.info("searchAnexos() -> Entrada al servicio para la búsqueda por filtros de anexos de mandatos de cuentas bancarias");
		
		List<DatosBancariosAnexoItem> anexosItem = new ArrayList<DatosBancariosAnexoItem>();
		DatosBancariosAnexoDTO anexosDTO = new DatosBancariosAnexoDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchAnexos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchAnexos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchAnexos() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de anexos de mandatos de cuentas bancarias");
				datosBancariosSearchAnexosDTO.setIdInstitucion(idInstitucion.toString());
				anexosItem = cenCuentasbancariasExtendsMapper.selectAnexos(datosBancariosSearchAnexosDTO);
				LOGGER.info(
						"searchAnexos() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de anexos de mandatos de cuentas bancarias");

				anexosDTO.setDatosBancariosAnexoItem(anexosItem);
			} 
			else {
				LOGGER.warn("searchAnexos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchAnexos() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchAnexos() -> Salida del servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		return anexosDTO;
	}
	

	@Override
	public UpdateResponseDTO updateAnexos(MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) {
		
		LOGGER.info("updateAnexos() -> Entrada al servicio para actualizar anexos y mandatos");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateAnexos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateAnexos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
					
				//Lo primero es comprobar si debemos modificar un Mandato o un Anexo.
				
				if (null != mandatosUpdateDTO.getIdAnexo()) {
					
						// información a modificar
						CenAnexosCuentasbancarias record = new CenAnexosCuentasbancarias();
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuario.getIdusuario());
						record.setIdanexo(Short.valueOf(mandatosUpdateDTO.getIdAnexo()));
						record.setIdmandato(Short.valueOf(mandatosUpdateDTO.getIdMandato()));
						record.setIdcuenta(Short.valueOf(mandatosUpdateDTO.getIdCuenta()));
						record.setIdpersona(Long.valueOf(mandatosUpdateDTO.getIdPersona()));
						record.setIdinstitucion(Short.valueOf(idInstitucion));
						record.setFirmaFecha(mandatosUpdateDTO.getFirmafecha());
						record.setFirmaLugar(mandatosUpdateDTO.getFirmaLugar());
						record.setOrigen(mandatosUpdateDTO.getDescripcion());
	
						// filtrado para sentencia sql
					
						LOGGER.info(
								"updateAnexos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar anexos y mandatos");
						
						response = cenAnexosCuentasbancariasMapper.updateByPrimaryKeySelective(record);
						
						LOGGER.info(
								"updateAnexos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar anexos y mandatos");
				
				}else {
						// información a modificar
						CenMandatosCuentasbancarias record = new CenMandatosCuentasbancarias();
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuario.getIdusuario());
						record.setIdmandato(Short.valueOf(mandatosUpdateDTO.getIdMandato()));
						record.setIdcuenta(Short.valueOf(mandatosUpdateDTO.getIdCuenta()));
						record.setIdpersona(Long.valueOf(mandatosUpdateDTO.getIdPersona()));
						record.setIdinstitucion(Short.valueOf(idInstitucion));
						record.setFechauso(mandatosUpdateDTO.getFechaUso());
						record.setFirmaFecha(mandatosUpdateDTO.getFirmafecha());
						record.setFirmaLugar(mandatosUpdateDTO.getFirmaLugar());
						// filtrado para sentencia sql
					
						LOGGER.info(
								"updateAnexos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar anexos y mandatos");
						
						response = cenMandatosCuentasbancariasMapper.updateByPrimaryKeySelective(record);
						
						LOGGER.info(
								"updateAnexos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar anexos y mandatos");

					
				}

			} else {
				LOGGER.warn(
						"updateAnexos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("updateAnexos() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("updateAnexos() -> OK. Update para anexos y mandatos realizado correctamente");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		else {
			LOGGER.info("updateMandatos() -> KO. Update para anexos y mandatos  NO realizado correctamente");
			updateResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteBanksData() -> Salida del servicio para actualizar anexos y mandatos ");
		return updateResponseDTO;
	}
	

	@Override
	public InsertResponseDTO InsertAnexos(MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) {
		
		LOGGER.info("InsertAnexos() -> Entrada al servicio para insertar anexos y mandatos");
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Short idCuenta= 1;
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"InsertAnexos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"InsertAnexos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
					
				//Lo primero es comprobar si debemos modificar un Mandato o un Anexo.
				
				
				List<NewIdDTO> newIdCuenta = cenCuentasbancariasExtendsMapper.
						selectNewIdAnexo(mandatosUpdateDTO.getIdPersona(), mandatosUpdateDTO.getIdCuenta(), mandatosUpdateDTO.getIdMandato(),idInstitucion.toString());
				if (null != newIdCuenta && newIdCuenta.size() > 0 ) {
					if (null!= newIdCuenta.get(0)) {
						idCuenta = Short.valueOf(newIdCuenta.get(0).getNewId());
					}
					
				}
					
						// información a modificar
						CenAnexosCuentasbancarias record = new CenAnexosCuentasbancarias();
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuario.getIdusuario());
						record.setIdanexo(idCuenta);
						record.setIdmandato(Short.valueOf(mandatosUpdateDTO.getIdMandato()));
						record.setIdcuenta(Short.valueOf(mandatosUpdateDTO.getIdCuenta()));
						record.setIdpersona(Long.valueOf(mandatosUpdateDTO.getIdPersona()));
						record.setIdinstitucion(idInstitucion);
						record.setFechacreacion(mandatosUpdateDTO.getFechaUso());
						record.setOrigen(mandatosUpdateDTO.getDescripcion());
						record.setEsautomatico(Short.valueOf("1"));
						record.setUsucreacion(usuario.getIdusuario());
	
						// filtrado para sentencia sql
					
						LOGGER.info(
								"InsertAnexos() / cenAnexosCuentasbancariasMapper.insertSelective() -> Entrada a cenAnexosCuentasbancariasMapper para insertar anexos y mandatos");
						
						response = cenAnexosCuentasbancariasMapper.insertSelective(record);
						
						LOGGER.info(
								"InsertAnexos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenAnexosCuentasbancariasMapper para insertar anexos y mandatos");
				
			} else {
				LOGGER.warn(
						"InsertAnexos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("InsertAnexos() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("InsertAnexos() -> OK. Insert para anexos y mandatos realizado correctamente");
			insertResponseDTO.setStatus(SigaConstants.OK);
			insertResponseDTO.setId(idCuenta.toString());
		}
		else {
			LOGGER.info("InsertAnexos() -> KO. Insert para anexos y mandatos  NO realizado correctamente");
			insertResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteBanksData() -> Salida del servicio para actualizar anexos y mandatos ");
		return insertResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IOException {
		LOGGER.info(
				"uploadFile() -> Entrada al servicio para guardar una fotografía de una persona jurídica");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();	
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<GenRecursos> genRecursos = new ArrayList<GenRecursos>();
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		
		String idPersona = request.getParameter("idPersona");
		String idCuenta = request.getParameter("idCuenta");
		String idMandato = request.getParameter("idMandato");
		String idAnexo = request.getParameter("idAnexo");
		String tipoMandato = request.getParameter("tipoMandato");
		
		int responseGenFichero = 0;
		int responseMandatoOAnexo = 0;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		// crear path para almacenar el fichero
		String pathFichero = "/FILERMSA1000/SIGA/ficheros/archivo/" + String.valueOf(idInstitucion) + "/mandatos/";
		//String pathFichero = "C://IISIGA/anexos/";
		String fileNewName = idPersona + idCuenta + idMandato;
		
		if(null == idAnexo || idAnexo.equals("") || idAnexo.equals("null")) {
			if(tipoMandato.equals("SERVICIO"))
				fileNewName += "0";
			else if(tipoMandato.equals("PRODUCTO"))
				fileNewName += "1";
		}
		else {
			fileNewName += idAnexo;
		}
		
		// 1. Coger archivo del request
		LOGGER.debug("uploadFile() -> Coger documento de cuenta bancaria del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		
		
		String fileNewNameNoExtension = fileNewName;
		fileNewName += extension;
		BufferedOutputStream stream = null;
		// 2. Guardar el archivo
		LOGGER.debug("uploadFile() -> Guardar el documento de cuenta bancaria");
		try {
			File aux = new File(pathFichero);
			// creo directorio si no existe
			aux.mkdirs();
			File serverFile = new File(pathFichero, fileNewName);
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(file.getBytes());
		} catch (FileNotFoundException e) {
			LOGGER.error("uploadFile() -> Error al buscar el documento de cuenta bancaria en el directorio indicado", e);
		} catch (IOException ioe) {
			LOGGER.error("uploadFile() -> Error al guardar el documento de cuenta bancaria en el directorio indicado",
					ioe);
		} finally {
			// close the stream
			LOGGER.debug("uploadFile() -> Cierre del stream de la fotografía de la persona jurídica");
			stream.close();
		}
		
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		if(null != usuarios && usuarios.size() > 0) {
			// 3. Crear registro en tabla gen_fichero
			usuario = usuarios.get(0);
			comboItems = genFicheroExtendsMapper.selectMaxIdFichero();
			int newIdFichero = 0;
			if(comboItems.isEmpty()) {
				newIdFichero = 1;
			}
			else {
				newIdFichero = Integer.valueOf(comboItems.get(0).getValue()) + 1;
			}
			
			GenFichero genFichero = new GenFichero();
			genFichero.setIdfichero(Long.valueOf(newIdFichero));
			genFichero.setIdinstitucion(idInstitucion);
			genFichero.setExtension(extension);
			genFichero.setFechamodificacion(new Date());
			genFichero.setUsumodificacion(usuario.getIdusuario());
			
			// obtenemos descripcion de gen_recursos
			GenRecursosExample genRecursosExample = new GenRecursosExample();
			genRecursosExample.createCriteria().andIdrecursoEqualTo("fichero.mandatos.descripcion");
			genRecursos = genRecursosMapper.selectByExample(genRecursosExample);
			
			genFichero.setDescripcion(genRecursos.get(0).getDescripcion());
			// unimos el path + nombre del fichero (sin extension)
			String directorio = pathFichero + fileNewNameNoExtension;
			genFichero.setDirectorio(directorio);
			responseGenFichero = genFicheroExtendsMapper.insertSelective(genFichero);
			if(responseGenFichero == 1) {
				
				// 4. Cambiar idfichero en tabla CEN_MANDATOS_CUENTASBANCARIAS o CEN_ANEXOS_CUENTASBANCARIAS
				if(!idAnexo.equals("") && !idAnexo.equals("null") && null != idAnexo) {
					
					// actualiza CEN_ANEXOS_CUENTASBANCARIAS
					CenAnexosCuentasbancarias cenAnexosCuentasbancarias = new CenAnexosCuentasbancarias();
					cenAnexosCuentasbancarias.setIdficherofirma(Long.valueOf(newIdFichero));
					CenAnexosCuentasbancariasExample cenAnexosCuentasbancariasExample = new CenAnexosCuentasbancariasExample();
					cenAnexosCuentasbancariasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(idPersona)).
					andIdcuentaEqualTo(Short.valueOf(idCuenta)).andIdmandatoEqualTo(Short.valueOf(idMandato)).andIdanexoEqualTo(Short.valueOf(idAnexo));
					
					responseMandatoOAnexo = cenAnexosCuentasbancariasMapper.updateByExampleSelective(cenAnexosCuentasbancarias, cenAnexosCuentasbancariasExample);
					if(responseMandatoOAnexo == 1) {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
					else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				}
				else{
					
					// actualiza CEN_MANDATOS_CUENTASBANCARIAS
					String auxTipoMandato = "";
					if(tipoMandato.equals("SERVICIO"))
						auxTipoMandato = "0";
					else if(tipoMandato.equals("PRODUCTO"))
						auxTipoMandato = "1";
					CenMandatosCuentasbancarias cenMandatosCuentasbancarias = new CenMandatosCuentasbancarias();
					cenMandatosCuentasbancarias.setIdficherofirma(Long.valueOf(newIdFichero));
					CenMandatosCuentasbancariasExample cenMandatosCuentasbancariasExample = new CenMandatosCuentasbancariasExample();
					cenMandatosCuentasbancariasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(idPersona)).
					andIdcuentaEqualTo(Short.valueOf(idCuenta)).andIdmandatoEqualTo(Short.valueOf(idMandato)).andTipomandatoEqualTo(Short.valueOf(auxTipoMandato));
					
					responseMandatoOAnexo = cenMandatosCuentasbancariasMapper.updateByExampleSelective(cenMandatosCuentasbancarias, cenMandatosCuentasbancariasExample);
					if(responseMandatoOAnexo == 1) {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
					else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				}
			}
			else {
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
			
			
		}
		return updateResponseDTO;		
	}


	@Override
	public ComboItem downloadFile(MandatosDownloadDTO mandatosDownloadDTO, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		CenMandatosCuentasbancarias cenMandatosCuentasbancarias = new CenMandatosCuentasbancarias();
		CenAnexosCuentasbancarias cenAnexosCuentasbancarias = new CenAnexosCuentasbancarias();
		GenFichero genFichero = new GenFichero();
		Long idFichero = null;
		ComboItem comboItem = new ComboItem();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			
		
		if(null == mandatosDownloadDTO.getIdAnexo() || mandatosDownloadDTO.getIdAnexo().equals("") || mandatosDownloadDTO.getIdAnexo().equals("null")) {
			// consulta CEN_MANDATOS_CUENTASBANCARIAS
			CenMandatosCuentasbancariasKey cenMandatosCuentasbancariasKey = new CenMandatosCuentasbancariasKey();
			cenMandatosCuentasbancariasKey.setIdcuenta(Short.valueOf(mandatosDownloadDTO.getIdCuenta()));
			cenMandatosCuentasbancariasKey.setIdinstitucion(idInstitucion);
			cenMandatosCuentasbancariasKey.setIdmandato(Short.valueOf(mandatosDownloadDTO.getIdMandato()));
			cenMandatosCuentasbancariasKey.setIdpersona(Long.valueOf(mandatosDownloadDTO.getIdPersona()));
			cenMandatosCuentasbancarias = cenMandatosCuentasbancariasMapper.selectByPrimaryKey(cenMandatosCuentasbancariasKey);
			
			if(null != cenMandatosCuentasbancarias)
				idFichero = cenMandatosCuentasbancarias.getIdficherofirma();
		}
		else {
			// consulta CEN_ANEXOS_CUENTASBANCARIAS
			
			CenAnexosCuentasbancariasKey cenAnexosCuentasbancariasKey = new CenAnexosCuentasbancariasKey();
			cenAnexosCuentasbancariasKey.setIdanexo(Short.valueOf(mandatosDownloadDTO.getIdAnexo()));
			cenAnexosCuentasbancariasKey.setIdcuenta(Short.valueOf(mandatosDownloadDTO.getIdCuenta()));
			cenAnexosCuentasbancariasKey.setIdinstitucion(idInstitucion);
			cenAnexosCuentasbancariasKey.setIdmandato(Short.valueOf(mandatosDownloadDTO.getIdMandato()));
			cenAnexosCuentasbancariasKey.setIdpersona(Long.valueOf(mandatosDownloadDTO.getIdPersona()));
			cenAnexosCuentasbancarias = cenAnexosCuentasbancariasMapper.selectByPrimaryKey(cenAnexosCuentasbancariasKey);
			
			if(null != cenMandatosCuentasbancarias) {
				idFichero = cenAnexosCuentasbancarias.getIdficherofirma();
			}
				
			
		}
		
		GenFicheroKey genFicheroKey = new GenFicheroKey();
		genFicheroKey.setIdfichero(idFichero);
		genFicheroKey.setIdinstitucion(idInstitucion);
		genFichero = genFicheroExtendsMapper.selectByPrimaryKey(genFicheroKey);
		
		if(null != genFichero) {
			String pathAbsolute = genFichero.getDirectorio();
			pathAbsolute += genFichero.getExtension();
			
			//File file = new File("C://IISIGA/anexos/2006002472110.pdf");
			File file = new File(pathAbsolute);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				IOUtils.copy(fis, response.getOutputStream());

			} catch (FileNotFoundException e) {
				LOGGER.error("No se ha encontrado el fichero", e);

			} catch (IOException e1) {
				LOGGER.error("No se han podido escribir los datos binarios del logo en la respuesta HttpServletResponse", e1);
			} finally {
				if (null != fis)
					try {
						fis.close();
					} catch (IOException e) {
						LOGGER.error("No se ha cerrado el archivo correctamente", e);
					}
			}
			return comboItem;
			
		}
		else {
			return null;
		}
		
//		ComboItem comboItem = new ComboItem();
//		comboItem.setLabel("2006002472110.pdf");
		
		
	}
	
	
	
	
	@Override
	public ComboItem fileDownloadInformation(MandatosDownloadDTO mandatosDownloadDTO, HttpServletRequest request) {
		CenMandatosCuentasbancarias cenMandatosCuentasbancarias = new CenMandatosCuentasbancarias();
		CenAnexosCuentasbancarias cenAnexosCuentasbancarias = new CenAnexosCuentasbancarias();
		GenFichero genFichero = new GenFichero();
		Long idFichero = null;
		ComboItem comboItem = new ComboItem();
		
		

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null == mandatosDownloadDTO.getIdAnexo() || mandatosDownloadDTO.getIdAnexo().equals("") || mandatosDownloadDTO.getIdAnexo().equals("null")) {
			// consulta CEN_MANDATOS_CUENTASBANCARIAS
			CenMandatosCuentasbancariasKey cenMandatosCuentasbancariasKey = new CenMandatosCuentasbancariasKey();
			cenMandatosCuentasbancariasKey.setIdcuenta(Short.valueOf(mandatosDownloadDTO.getIdCuenta()));
			cenMandatosCuentasbancariasKey.setIdinstitucion(idInstitucion);
			cenMandatosCuentasbancariasKey.setIdmandato(Short.valueOf(mandatosDownloadDTO.getIdMandato()));
			cenMandatosCuentasbancariasKey.setIdpersona(Long.valueOf(mandatosDownloadDTO.getIdPersona()));
			cenMandatosCuentasbancarias = cenMandatosCuentasbancariasMapper.selectByPrimaryKey(cenMandatosCuentasbancariasKey);
			
			if(null != cenMandatosCuentasbancarias)
				idFichero = cenMandatosCuentasbancarias.getIdficherofirma();
		}
		else {
			// consulta CEN_ANEXOS_CUENTASBANCARIAS
			
			CenAnexosCuentasbancariasKey cenAnexosCuentasbancariasKey = new CenAnexosCuentasbancariasKey();
			cenAnexosCuentasbancariasKey.setIdanexo(Short.valueOf(mandatosDownloadDTO.getIdAnexo()));
			cenAnexosCuentasbancariasKey.setIdcuenta(Short.valueOf(mandatosDownloadDTO.getIdCuenta()));
			cenAnexosCuentasbancariasKey.setIdinstitucion(idInstitucion);
			cenAnexosCuentasbancariasKey.setIdmandato(Short.valueOf(mandatosDownloadDTO.getIdMandato()));
			cenAnexosCuentasbancariasKey.setIdpersona(Long.valueOf(mandatosDownloadDTO.getIdPersona()));
			cenAnexosCuentasbancarias = cenAnexosCuentasbancariasMapper.selectByPrimaryKey(cenAnexosCuentasbancariasKey);
			
			if(null != cenMandatosCuentasbancarias) {
				idFichero = cenAnexosCuentasbancarias.getIdficherofirma();
			}
				
			
		}
		
		GenFicheroKey genFicheroKey = new GenFicheroKey();
		genFicheroKey.setIdfichero(idFichero);
		genFicheroKey.setIdinstitucion(idInstitucion);
		genFichero = genFicheroExtendsMapper.selectByPrimaryKey(genFicheroKey);
		
		if(null != genFichero) {
			comboItem.setLabel(genFichero.getExtension());
			String ruta = genFichero.getDirectorio();
			String [] division = ruta.split("/");
			String nombreArchivo = division[division.length-1];
			if(nombreArchivo.contains("/")) {
				nombreArchivo = nombreArchivo.replace("/", "");
			}
			comboItem.setValue(nombreArchivo);
		}
		
		
		return comboItem;
	}
	
	
	
	/**
	   * Calls a PL Funtion
	   * @author CSD
	   * @param functionDefinition string that defines the function
	   * @param inParameters input parameters
	   * @param outParameters number of output parameters
	   * @return error code, '0' if ok
	 * @throws NamingException 
	 * @throws IOException 
	 * @throws SQLException 
	   * @throws ClsExceptions  type Exception
	   */
	  private  String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters) throws IOException, NamingException, SQLException  {
	    String result[] = null;
	    
	    if (outParameters>0) result= new String[outParameters];
	    DataSource ds = getOracleDataSource();
	    Connection con=ds.getConnection();
	    try{
	      CallableStatement cs=con.prepareCall(functionDefinition);
	      int size=inParameters.length;
	      
	      //input Parameters
	      for(int i=0;i<size;i++){
	    	  

	        cs.setString(i+1,(String)inParameters[i]);
	      }
	      //output Parameters
	      for(int i=0;i<outParameters;i++){
	        cs.registerOutParameter(i+size+1,Types.VARCHAR);
	      }
	      
			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					break;
					
				} catch (SQLTimeoutException tex) {
					throw tex;
		
				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la segunda vez deberia funcionar)
						throw ex;
					}
				}

			}      

	      for(int i=0;i<outParameters;i++){
	        result[i]=cs.getString(i+size+1);
	      }
	      cs.close();
	      return result;
	      
	    }catch(SQLTimeoutException ex){
	        return null;
	    }catch(SQLException ex){
	    	return null;
	    }catch(Exception e){
	    	return null;
	    }finally{
	      con.close();
	      con = null;
	    }
	  }
	  
	  
		/**
		 * Recupera el datasource con los datos de conexión sacados del fichero de
		 * configuracion
		 * 
		 * @return
		 * @throws IOException
		 * @throws NamingException
		 */
		private  DataSource getOracleDataSource() throws IOException, NamingException {
			try {
				
				LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");
				
				AdmConfigExample example = new AdmConfigExample();
				example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
				List<AdmConfig> config = admConfigMapper.selectByExample(example );
				Context ctx = new InitialContext();
				return (DataSource) ctx.lookup(config.get(0).getValor());
			} catch (NamingException e) {
				throw e;
			}
		}




		



	
		/**
		 * PL que realiza una revision de letrado
		 * @param idInstitucion
		 * @param idPersona
		 * @param usuario
		 * @return
		 * @throws ClsExceptions
		 */
		private  String[] ejecutarPL_RevisionSuscripcionesLetrado (String idInstitucion, String idPersona, String fecha, String usuario) throws Exception {

			Object[] paramIn = new Object[4]; //Parametros de entrada del PL
			String resultado[] = new String[2]; //Parametros de salida del PL
		
			try {
		 		// Parametros de entrada del PL
		        paramIn[0] = idInstitucion;
		        paramIn[1] = idPersona;
		        paramIn[2] = fecha;
		        paramIn[3] = usuario;

		        // Ejecucion del PL
				resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO (?,?,?,?,?,?)}", 2, paramIn);
				
			} catch (Exception e) {
				resultado[0] = "1"; 	// P_NUMREGISTRO
		    	resultado[1] = "ERROR"; // ERROR P_DATOSERROR        	
			}
			
		    return resultado;
		}
		
		
		/**
		 * Este proceso se encarga de actualizar las cosas pendientes asociadas a la cuenta de la persona 
		 * @param idInstitucion
		 * @param idPersona
		 * @param idCuenta
		 * @param usuario
		 * @return Codigo y mensaje de error
		 * @throws ClsExceptions
		 */
		private  String[] ejecutarPL_Revision_Cuenta (String idInstitucion, String idPersona, String idCuenta, String usuario) throws Exception {
			Object[] paramIn = new Object[4]; 	//Parametros de entrada del PL
			String resultado[] = new String[2]; //Parametros de salida del PL
		
			try {
		 		// Parametros de entrada del PL
		        paramIn[0] = idInstitucion;
		        paramIn[1] = idPersona;
		        paramIn[2] = idCuenta;
		        paramIn[3] = usuario;

		        // Ejecucion del PL
				resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_CUENTA(?,?,?,?,?,?)}", 2, paramIn);
				
			} catch (Exception e) {
				resultado[0] = "1"; 	// P_CODRETORNO
		    	resultado[1] = "ERROR"; // ERROR P_DATOSERROR        	
			}
			
		    return resultado;
		}
		/**
		 * @param idInstitucion
		 * @param idPersona
		 * @param idCuenta
		 * @param usuario
		 * @return Codigo y mensaje de error
		 * @throws ClsExceptions
		 */
		private String[] ejecutarPL_AltaCuentaCargos (String idInstitucion, String idPersona, String idCuenta, String usuario) throws Exception {
			Object[] paramIn = new Object[4]; 	//Parametros de entrada del PL
			String resultado[] = new String[2]; //Parametros de salida del PL
		
			try {
		 		// Parametros de entrada del PL
		        paramIn[0] = idInstitucion;
		        paramIn[1] = idPersona;
		        paramIn[2] = idCuenta;
		        paramIn[3] = usuario;

		        // Ejecucion del PL
				resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_ALTA_CUENTA_CARGOS(?,?,?,?,?,?)}", 2, paramIn);
				
			} catch (Exception e) {
				resultado[0] = "1"; 	// P_CODRETORNO
		    	resultado[1] = "ERROR"; // ERROR P_DATOSERROR        	
			}
			
		    return resultado;
		}




		








	





}
