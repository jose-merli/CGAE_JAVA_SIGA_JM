package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosItem;
import org.itcgae.siga.cen.services.ITarjetaDatosBancariosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaDatosBancariosServiceImpl implements ITarjetaDatosBancariosService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosBancariosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;
	
	


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
				for(int i=0; i<datosBancariosDeleteDTO.getIdCuenta().length; i++) {
					idCuentasDelete.add(Short.valueOf(datosBancariosDeleteDTO.getIdCuenta()[i]));
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







	

}