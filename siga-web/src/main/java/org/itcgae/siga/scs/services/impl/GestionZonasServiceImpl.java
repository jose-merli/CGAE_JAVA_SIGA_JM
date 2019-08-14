package org.itcgae.siga.scs.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.ZonasDTO;
import org.itcgae.siga.DTO.scs.ZonasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsZonasExtendsMapper;
import org.itcgae.siga.scs.service.IGestionZonasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionZonasServiceImpl implements IGestionZonasService {

	private Logger LOGGER = Logger.getLogger(GestionZonasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsZonasExtendsMapper scsZonasExtendsMapper;
	
	@Override
	public ZonasDTO searchZones(ZonasItem zonasItem, HttpServletRequest request) {
		LOGGER.info("searchZones() -> Entrada al servicio para obtener las zonas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ZonasDTO zonasDTO = new ZonasDTO();
		List<ZonasItem> zonasItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			LOGGER.info(
					"searchZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"searchZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
			
				LOGGER.info(
						"searchZones() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				zonasItems = scsZonasExtendsMapper.searchZonas(zonasItem, idInstitucion);
				
				LOGGER.info(
						"searchZones() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");


				if(zonasItems != null) {
					zonasDTO.setZonasItems(zonasItems);
				}
			}

		}
		LOGGER.info("searchZones() -> Salida del servicio para obtener las zonas");
		return zonasDTO;
	}

	@Override
	public UpdateResponseDTO deleteGroupZone(ZonasDTO zonasDTO, HttpServletRequest request) {
		LOGGER.info("deleteGroupZone() ->  Entrada al servicio para eliminar grupos zona");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			LOGGER.info(
					"updateGroupsZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"updateGroupsZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {

						//Eliminar zonas	
						
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("No se han actualizado las zonas");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Se han actualizado las zonas correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteGroupZone() -> Salida del servicio para eliminar un grupo zona");

		return updateResponseDTO;
	}
	
}
