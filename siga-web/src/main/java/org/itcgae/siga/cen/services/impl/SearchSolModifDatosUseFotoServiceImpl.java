package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CenSolicmodifexportarfotoDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosUseFotoService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfotoExample;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.CenSolimodidireccionesExample;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodifexportarfotoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosUseFotoServiceImpl implements ISearchSolModifDatosUseFotoService{

	private Logger LOGGER = Logger.getLogger(SolicitudModificacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenSolicmodifexportarfotoExtendsMapper cenSolicModifExportarFotoExtendsMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;
	
	@Override
	public SolModificacionDTO searchSolModifDatosUseFoto(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosUseFoto() -> Entrada al servicio para recuperar los datos de la foto");

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
					"searchSolModifDatosUseFoto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosUseFoto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				if (letrado.equalsIgnoreCase("S")) {
					CenPersonaExample example = new CenPersonaExample();
					example.createCriteria().andNifcifEqualTo(usuario.getNif());
					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(example );
					if (null != cenPersona && cenPersona.size()>0) {
	
	
						LOGGER.info(
								"searchSolModifDatosUseFoto() / cenSolicModifExportarFotoExtendsMapper.searchSolModifDatosUseFoto() -> Entrada a cenSolicModifExportarFotoExtendsMapper para obtener los datos de la foto");
						//solicitudModificacionSearchDTO.setIdInstitucion(idInstitucion.toString());
						List<SolModificacionItem> solModificacionItems = cenSolicModifExportarFotoExtendsMapper.searchSolModifDatosUseFoto(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), 
								String.valueOf(idInstitucion),cenPersona.get(0).getIdpersona());
						solModificacionDTO.setSolModificacionItems(solModificacionItems);
					}
	
				}else{
	
					LOGGER.info(
							"searchSolModifDatosUseFoto() / cenSolicModifExportarFotoExtendsMapper.searchSolModifDatosUseFoto() -> Entrada a cenSolicModifExportarFotoExtendsMapper para obtener los datos de la foto");
					//solicitudModificacionSearchDTO.setIdInstitucion(idInstitucion.toString());
					List<SolModificacionItem> solModificacionItems = cenSolicModifExportarFotoExtendsMapper.searchSolModifDatosUseFoto(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion),null);
					solModificacionDTO.setSolModificacionItems(solModificacionItems);
	
				}
			}
		}

		LOGGER.info(
				"searchSolModifDatosUseFoto() -> Salida del servicio para recuperar los datos de la foto");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosUseFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosUseFoto() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		
		LOGGER.info(
				"processSolModifDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
		usuario = usuarios.get(0);
			
		//CenSolicmodifexportarfoto solicitud = new CenSolicmodifexportarfoto();
//		CenSolicmodifexportarfotoExample example = new CenSolicmodifexportarfotoExample();
//		
//		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(solModificacionItem.getIdPersona()))
//		.andIdinstitucionEqualTo(idInstitucion).andIdsolicitudEqualTo(Short.valueOf(solModificacionItem.getIdSolicitud()));
//		
		CenSolicmodifexportarfotoDTO lista = cenSolicModifExportarFotoExtendsMapper.selectByPrimaryKeyDTO(Long.valueOf(solModificacionItem.getIdSolicitud()));


		
		CenCliente modificacion = new CenCliente();
		modificacion.setIdinstitucion(idInstitucion);
		modificacion.setIdpersona(lista.getIdpersona());
		modificacion.setFechamodificacion(new Date());
		modificacion.setUsumodificacion(usuario.getIdusuario());
		modificacion.setExportarfoto(lista.getExportarfoto());
//		modificacion.setid
		
		
		int responseUpdate = cenClienteExtendsMapper.updateByPrimaryKeySelective(modificacion);
		
		if(responseUpdate >= 1) {
		CenSolicmodifexportarfotoDTO record = new CenSolicmodifexportarfotoDTO();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 20);
		int response = cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelectiveDTO(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosUseFoto() / cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		}
		
		}else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosGenerales() / No existe el usuario.");
		}
		
		LOGGER.info(
				"processSolModifDatosUseFoto() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO denySolModifDatosUseFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosUseFoto() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolicmodifexportarfotoDTO record = new CenSolicmodifexportarfotoDTO();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelectiveDTO(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModifDatosUseFoto() / cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"denySolModifDatosUseFoto() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
