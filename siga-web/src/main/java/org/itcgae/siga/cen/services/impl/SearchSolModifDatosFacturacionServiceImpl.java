package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosFacturacionService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.CenSolmodifacturacionservicio;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolModifFacturacionServicioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosFacturacionServiceImpl implements ISearchSolModifDatosFacturacionService{

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosFacturacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	

	@Autowired
	private CenSolModifFacturacionServicioExtendsMapper cenSolModifFacturacionServicioExtendsMapper;
	
	@Override
	public SolModificacionDTO searchSolModifDatosFacturacion(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosFacturacion() -> Entrada al servicio para recuperar los datos de la búsqueda específica de datos facturación");

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
					"searchSolModifDatosFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);
				if (letrado.equalsIgnoreCase("S")) {
					CenPersonaExample example = new CenPersonaExample();
					example.createCriteria().andNifcifEqualTo(usuario.getNif());
					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(example );
					if (null != cenPersona && cenPersona.size()>0) {

						LOGGER.info(
								"searchSolModifDatosFacturacion() / cenSolModifFacturacionServicioExtendsMapper.searchSolModifDatosFacturacion() -> Entrada a cenSoliModiDireccionesExtendsMapper para obtener los resultados de la búsqueda");
						List<SolModificacionItem> solModificacionItems = cenSolModifFacturacionServicioExtendsMapper.searchSolModifDatosFacturacion(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), 
								String.valueOf(idInstitucion),cenPersona.get(0).getIdpersona());
						solModificacionDTO.setSolModificacionItems(solModificacionItems);
					}

				}else{
					LOGGER.info(
							"searchSolModifDatosFacturacion() / cenSolModifFacturacionServicioExtendsMapper.searchSolModifDatosFacturacion() -> Entrada a cenSoliModiDireccionesExtendsMapper para obtener los resultados de la búsqueda");
					List<SolModificacionItem> solModificacionItems = cenSolModifFacturacionServicioExtendsMapper.searchSolModifDatosFacturacion(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion),null);
					solModificacionDTO.setSolModificacionItems(solModificacionItems);
				}

				

			}
		}

		LOGGER.info(
				"searchSolModifDatosFacturacion() -> Salida del servicio para recuperar los datos de la búsqueda específica de datos facturación");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosFacturacion(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosFacturacion() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolmodifacturacionservicio record = new CenSolmodifacturacionservicio();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 20);
		int response = cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosFacturacion() / cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"processSolModifDatosFacturacion() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO denySolModifDatosFacturacion(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosFacturacion() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolmodifacturacionservicio record = new CenSolmodifacturacionservicio();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModifDatosFacturacion() / cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		}else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"denySolModifDatosFacturacion() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
