package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosExpedientesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.ExpSolicitudborrado;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.ExpSolicitudBorradoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosExpedientesServiceImpl implements ISearchSolModifDatosExpedientesService {

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosExpedientesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ExpSolicitudBorradoExtendsMapper expSolicitudBorradoExtendsMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Override
	public SolModificacionDTO searchSolModifDatosExpedientes(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosExpedientes() -> Entrada al servicio para recuperar los datos de la búsqueda específica de datos expedientes");

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
					"searchSolModifDatosExpedientes() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosExpedientes() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				if (letrado.equalsIgnoreCase("S")) {
					CenPersonaExample example = new CenPersonaExample();
					example.createCriteria().andNifcifEqualTo(usuario.getNif());
					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(example );
					if (null != cenPersona && cenPersona.size()>0) {



						LOGGER.info(
								"searchSolModifDatosExpedientes() / expSolicitudBorradoExtendsMapper.searchSolModifDatosExpedientes() -> Entrada a expSolicitudBorradoExtendsMapper para obtener los resultados de la búsqueda");
						List<SolModificacionItem> solModificacionItems = expSolicitudBorradoExtendsMapper
								.searchSolModifDatosExpedientes(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
										String.valueOf(idInstitucion),cenPersona.get(0).getIdpersona());
						solModificacionDTO.setSolModificacionItems(solModificacionItems);
					}

				}else{



					LOGGER.info(
							"searchSolModifDatosExpedientes() / expSolicitudBorradoExtendsMapper.searchSolModifDatosExpedientes() -> Entrada a expSolicitudBorradoExtendsMapper para obtener los resultados de la búsqueda");
					List<SolModificacionItem> solModificacionItems = expSolicitudBorradoExtendsMapper
							.searchSolModifDatosExpedientes(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
									String.valueOf(idInstitucion),null);
					solModificacionDTO.setSolModificacionItems(solModificacionItems);
				}

			}
		}

		LOGGER.info(
				"searchSolModifDatosExpedientes() -> Salida del servicio para recuperar los datos de la búsqueda específica de datos expedientes");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosExpedientes(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosExpedientes() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		ExpSolicitudborrado record = new ExpSolicitudborrado();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 20);
		int response = expSolicitudBorradoExtendsMapper.updateByPrimaryKeySelective(record);

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosExpedientes() / expSolicitudBorradoExtendsMapper.updateByPrimaryKey() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		}else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		LOGGER.info(
				"processSolModifDatosExpedientes() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO denySolModifDatosExpedientes(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosExpedientes() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		ExpSolicitudborrado record = new ExpSolicitudborrado();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = expSolicitudBorradoExtendsMapper.updateByPrimaryKeySelective(record);

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModifDatosExpedientes() / expSolicitudBorradoExtendsMapper.updateByPrimaryKey() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		}else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		LOGGER.info(
				"denySolModifDatosExpedientes() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
