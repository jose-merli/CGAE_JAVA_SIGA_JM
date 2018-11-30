package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IFichaDatosColegialesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposseguroExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaDatosColegialesServiceImpl implements IFichaDatosColegialesService{

	private Logger LOGGER = Logger.getLogger(FichaDatosColegialesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenTratamientoExtendsMapper cenTratamientoExtendsMapper;
	
	@Autowired
	private CenTiposseguroExtendsMapper cenTiposseguroExtendsMapper;

	@Autowired 
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	@Override
	public ComboDTO getSocietyTypes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboDTO getTratamiento(HttpServletRequest request) {

		LOGGER.info("getTratamiento() -> Entrada al servicio para obtener los tipos de tratamiento disponibles");
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
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = cenTratamientoExtendsMapper.selectTratamiento("a");				

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getPais() -> Salida del servicio para obtener los tipos de tratamiento disponibles");

		return comboDTO;
	}

	@Override
	public ComboDTO getLabel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboDTO getTypeInsurances(HttpServletRequest request) {
		LOGGER.info("getTypeInsurances() -> Entrada al servicio para obtener los tipos seguros disponibles");
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
					"getTypeInsurances() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeInsurances() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeInsurances() / cenTiposseguroExtendsMapper.getTypeInsurances() -> Entrada a cenTiposseguroExtendsMapper para obtener tipos de seguro");
				comboItems = cenTiposseguroExtendsMapper.getTypeInsurances(usuario.getIdlenguaje());

				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);

				}		
								
				LOGGER.info(
						"getTypeInsurances() / cenTiposseguroExtendsMapper.getTypeInsurances() -> Salida de cenTiposseguroExtendsMapper para obtener tipos de seguro");
				
			}

		}
		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTypeInsurances() -> Salida del servicio para obtener los tipos seguros disponibles");
		return comboDTO;

	}

	@Override
	public ColegiadoDTO datosColegialesSearch(int numPagina, ColegiadoItem colegiadoItem,
			HttpServletRequest request) {
		
		LOGGER.info("datosColegialesSearch() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		List<ColegiadoItem> colegiadoListItem = new ArrayList<ColegiadoItem>();
		ColegiadoDTO datosColegialesDTO = new ColegiadoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
//				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosColegialesSearch() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");
				colegiadoListItem = cenColegiadoExtendsMapper.selectColegiaciones(idInstitucion, usuario.getIdlenguaje(), colegiadoItem);
				LOGGER.info(
						"datosColegialesSearch() / CenColegiadoExtendsMapper.selectDirecciones() -> Salida de CenColegiadoExtendsMapper para busqueda de Colegiados");

				datosColegialesDTO.setColegiadoItem(colegiadoListItem);
			} else {
				LOGGER.warn(
						"datosColegialesSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosColegialesSearch() -> idInstitucion del token nula");
		}

		LOGGER.info("datosColegialesSearch() -> Salida del servicio para la búsqueda por filtros de Colegiados");
		return datosColegialesDTO;
	}	
	

	@Override
	public UpdateResponseDTO datosColegialesUpdate(ColegiadoItem colegiadoItem,
			HttpServletRequest request) {
		
		LOGGER.info("datosColegialesSearch() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		UpdateResponseDTO response = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
//				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosColegialesUpdate() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");
				
				CenColegiado colegiado = new CenColegiado();
				colegiado.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				colegiado.setIdinstitucion(idInstitucion);
				colegiado.setFechamodificacion(new Date());
				colegiado.setUsumodificacion(usuario.getIdusuario());
				colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
				colegiado.setIdtiposseguro(Short.parseShort(colegiadoItem.getIdTiposSeguro()));
				if(colegiadoItem.getSituacion()!=null) {
				colegiado.setSituacionresidente(colegiadoItem.getSituacionresidente());
				}
				if(colegiadoItem.getComunitario()!=null) {
					colegiado.setComunitario(colegiadoItem.getComunitario());
				}
				colegiado.setNmutualista(colegiadoItem.getnMutualista());
				if(colegiadoItem.getIncorporacionDate()!=null) {
				colegiado.setFechaincorporacion(colegiadoItem.getIncorporacionDate());
				}
				if(colegiadoItem.getFechapresentacionDate()!=null) {
				colegiado.setFechapresentacion(colegiadoItem.getFechapresentacionDate());
				}
				colegiado.setFechajura(colegiadoItem.getFechaJuraDate());
				colegiado.setFechatitulacion(colegiadoItem.getFechaTitulacionDate());
				
				int responseUpdate = cenColegiadoExtendsMapper.updateByPrimaryKeySelective(colegiado);
				LOGGER.info(
						"datosColegialesUpdate() / CenColegiadoExtendsMapper.selectDirecciones() -> Salida de CenColegiadoExtendsMapper para actualización de Colegiados");

				if(responseUpdate >= 1) {
					response.setStatus(SigaConstants.OK);
				}else {
					response.setStatus(SigaConstants.KO);
				}
			} else {
				LOGGER.warn(
						"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
				response.setStatus(SigaConstants.KO);
			}
			
		} else {
			LOGGER.warn("datosColegialesUpdate() -> idInstitucion del token nula");
			response.setStatus(SigaConstants.KO);
		}

		LOGGER.info("datosColegialesUpdate() -> Salida del servicio para la búsqueda por filtros de Colegiados");
		return response;
	}	
	
	
}
