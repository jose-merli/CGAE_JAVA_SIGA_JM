package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosGeneralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;

import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncvExample;
import org.itcgae.siga.db.entities.CenSolmodifacturacionservicio;
import org.itcgae.siga.db.mappers.CenSolicitudesmodificacionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosGeneralesServiceImpl implements ISearchSolModifDatosGeneralesService{

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosGeneralesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenSolicitmodifdatosbasicosExtendsMapper cenSolicitModifDatosBasicosExtendsMapper;
	
	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;
	
	@Override
	public SolModificacionDTO searchSolModifDatosGenerales(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosGenerales() -> Entrada al servicio para recuperar los datos de la búsqueda específica de datos generales");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"searchSolModifDatosGenerales() / cenSolicitModifDatosBasicosExtendsMapper.searchSolModifDatosGenerales() -> Entrada a cenSolicitModifDatosBasicosExtendsMapper para obtener los estados de solicitud");
				List<SolModificacionItem> solModificacionItems = cenSolicitModifDatosBasicosExtendsMapper.searchSolModifDatosGenerales(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				solModificacionDTO.setSolModificacionItems(solModificacionItems);

			}
		}

		LOGGER.info(
				"searchSolModifDatosGenerales() -> Salida del servicio para recuperar los datos de la búsqueda específica de datos generales");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosGenerales(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosGenerales() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");
		
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
				
			CenSolicitmodifdatosbasicos solicitud = new CenSolicitmodifdatosbasicos();
			CenSolicitmodifdatosbasicosExample example = new CenSolicitmodifdatosbasicosExample();
			
			example.createCriteria().andIdpersonaEqualTo(Long.parseLong(solModificacionItem.getIdPersona()))
			.andIdinstitucionEqualTo(idInstitucion).andIdsolicitudEqualTo(Short.valueOf(solModificacionItem.getIdSolicitud()));
			
			List<CenSolicitmodifdatosbasicos> lista = cenSolicitModifDatosBasicosExtendsMapper.selectByExample(example);

			solicitud = lista.get(0);
			
			CenCliente modificacion = new CenCliente();
			modificacion.setIdinstitucion(idInstitucion);
			modificacion.setIdlenguaje(solicitud.getIdlenguaje());
			modificacion.setPublicidad(solicitud.getPublicidad());
			modificacion.setGuiajudicial(solicitud.getGuiajudicial());
			modificacion.setIdpersona(solicitud.getIdpersona());
			modificacion.setFechaactualizacion(new Date());
			modificacion.setFechamodificacion(new Date());
			modificacion.setUsumodificacion(usuario.getIdusuario());
			
			int responseUpdate = cenClienteExtendsMapper.updateByPrimaryKeySelective(modificacion);
			
			if(responseUpdate >= 1) {
				CenSolicitmodifdatosbasicos record = new CenSolicitmodifdatosbasicos();
				record.setIdsolicitud(Short.valueOf(solModificacionItem.getIdSolicitud()));
				record.setIdestadosolic((short) 20);
				int response = cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKeySelective(record);
				
				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("processSolModifDatosGenerales() / cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKey() -> "
							+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

				} else {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			}else {
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
			
			}else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("processSolModifDatosGenerales() / No existe el usuario.");
			}
			
			LOGGER.info(
					"processSolModifDatosGenerales() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
			return updateResponseDTO;

	}

	@Override
	public UpdateResponseDTO denySolModifDatosGenerales(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosGenerales() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolicitmodifdatosbasicos record = new CenSolicitmodifdatosbasicos();
		record.setIdsolicitud(Short.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKeySelective(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModifDatosGenerales() / cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKey() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"denySolModifDatosGenerales() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}