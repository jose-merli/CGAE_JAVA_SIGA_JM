package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitudesmodificacion;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudesmodificacionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifServiceImpl implements ISearchSolModifService{

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosFacturacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private CenSolicitudesmodificacionExtendsMapper cenSolicitudesModificacionExtendsMapper;
	
	@Override
	public SolModificacionDTO searchSolModif(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		
		LOGGER.info(
				"searchSolModif() -> Entrada al servicio para recuperar los datos de la solicitud de modificación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModif() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModif() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				if (letrado.equalsIgnoreCase("S")) {
					CenPersonaExample example = new CenPersonaExample();
					example.createCriteria().andNifcifEqualTo(usuario.getNif());
					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(example );
					if (null != cenPersona && cenPersona.size()>0) {
	
						LOGGER.info(
								"searchSolModif() / cenSoliModiDireccionesExtendsMapper.searchSolModifDatosDirecciones() -> Entrada a cenSolicitudesModificacionExtendsMapper para obtener los resultados de la búsqueda");
						List<SolModificacionItem> solModificacionItems = cenSolicitudesModificacionExtendsMapper.searchSolModif(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), 
								String.valueOf(idInstitucion),cenPersona.get(0).getIdpersona());
						solModificacionDTO.setSolModificacionItems(solModificacionItems);
					}
	
				}else{
	
					LOGGER.info(
							"searchSolModif() / cenSoliModiDireccionesExtendsMapper.searchSolModifDatosDirecciones() -> Entrada a cenSolicitudesModificacionExtendsMapper para obtener los resultados de la búsqueda");
					List<SolModificacionItem> solModificacionItems = cenSolicitudesModificacionExtendsMapper.searchSolModif(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), 
							String.valueOf(idInstitucion),null);
					solModificacionDTO.setSolModificacionItems(solModificacionItems);
	
				}
			}

			
		}

		LOGGER.info(
				"searchSolModif() -> Salida del servicio para recuperar los datos de la solicitud de modificación");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModif(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModif() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolicitudesmodificacion record = new CenSolicitudesmodificacion();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 20);
		int response = cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModif() / cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"processSolModif() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO denySolModif(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModif() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolicitudesmodificacion record = new CenSolicitudesmodificacion();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModif() / cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"denySolModif() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
