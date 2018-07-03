package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaDireccionesUpdateDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarjetaDatosDireccionesServiceImpl implements ITarjetaDatosDireccionesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosDireccionesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	

	
	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
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











	





}
