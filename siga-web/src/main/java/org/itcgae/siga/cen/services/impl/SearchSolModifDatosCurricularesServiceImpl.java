package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosCurricularesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvExample;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosCurricularesServiceImpl implements ISearchSolModifDatosCurricularesService{

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosBancariosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;

	@Autowired
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;
	
	@Override
	public SolModificacionDTO searchSolModifDatosCurriculares(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosCurriculares() -> Entrada al servicio para recuperar los datos de la búsqueda específica de datos cv");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"searchSolModifDatosCurriculares() / cenSolicitudmodificacioncvExtendsMapper.searchSolModifDatosCurriculares() -> Entrada a cenSoliModiDireccionesExtendsMapper para obtener los resultados de la búsqueda");
				List<SolModificacionItem> solModificacionItems = cenSolicitudmodificacioncvExtendsMapper.searchSolModifDatosCurriculares(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				solModificacionDTO.setSolModificacionItems(solModificacionItems);

			}
		}

		LOGGER.info(
				"searchSolModifDatosCurriculares() -> Salida del servicio para recuperar los datos de la búsqueda específica de datos cv");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosCurriculares(int numPagina, SolModifDatosCurricularesItem solModifDatosCurricularesItem,
			HttpServletRequest request) {
		
		AdmUsuarios usuario = new AdmUsuarios();
		int responseSolicitud = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

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

		LOGGER.info(
				"processSolModifDatosCurriculares() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");
		
		if(solModifDatosCurricularesItem.getIdCv() != null) {
			CenDatoscv recordUpdate = new CenDatoscv();
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setIdpersona(Long.parseLong(solModifDatosCurricularesItem.getIdPersona()));
			recordUpdate.setIdtipocv(Short.parseShort(solModifDatosCurricularesItem.getIdTipoCv()));

			if ("" != solModifDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != solModifDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(solModifDatosCurricularesItem.getIdTipoCvSubtipo1()));
			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
			}
			if ("" != solModifDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != solModifDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(solModifDatosCurricularesItem.getIdTipoCvSubtipo2()));
			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
			}
//			if (null != solModifDatosCurricularesItem.getCreditos() && "" != solModifDatosCurricularesItem.getCreditos()) {
//				recordUpdate.setCreditos(Long.parseLong(solModifDatosCurricularesItem.getCreditos()));
//			} else {
//				recordUpdate.setCreditos(null);
//			}
//			// if(null != fichaDatosCurricularesItem.getCertificado()){
//			recordUpdate.setCertificado(solModifDatosCurricularesItem.getCertificado());
			// }
			if (null != solModifDatosCurricularesItem.getDescripcion()
					&& "" != solModifDatosCurricularesItem.getDescripcion()) {
				recordUpdate.setDescripcion(solModifDatosCurricularesItem.getDescripcion());
			}
			// if(null != fichaDatosCurricularesItem.getIdTipoCv()){
			// }
			recordUpdate.setIdinstitucion(idInstitucion);
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setFechainicio(solModifDatosCurricularesItem.getDateFechaInicio());
			recordUpdate.setFechafin(solModifDatosCurricularesItem.getDateFechaFin());
			recordUpdate.setFechabaja(solModifDatosCurricularesItem.getDateFechaFin());
			recordUpdate.setFechamovimiento(solModifDatosCurricularesItem.getDateFechaMovimiento());
			recordUpdate.setIdcv(Short.parseShort(solModifDatosCurricularesItem.getIdCv()));
			// recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));
			
			if(recordUpdate.getFechafin() == null) {
				CenDatoscvExample example = new CenDatoscvExample();
				Long idPers = Long.parseLong(solModifDatosCurricularesItem.getIdPersona());
				example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
				List<CenDatoscv> datosCurricularesActivos = cenDatoscvExtendsMapper.selectByExample(example);
				
				if(datosCurricularesActivos != null && datosCurricularesActivos.size() != 0) {
					for(CenDatoscv dato: datosCurricularesActivos) {
						CenDatoscv Actualizar = new CenDatoscv();
						Actualizar = dato;
						Actualizar.setFechabaja(new Date());
						Actualizar.setFechafin(new Date());
						Actualizar.setFechamodificacion(new Date());
						Actualizar.setUsumodificacion(usuario.getIdusuario());
						responseSolicitud = cenDatoscvExtendsMapper.updateByPrimaryKey(Actualizar);
//						update by primarykey
					}

				}
				
			}
			responseSolicitud = cenDatoscvExtendsMapper.updateCurriculo(recordUpdate);

		}else {
//REVISAR LOS CAMPOS QUE NO DEBO PODER INSERTAR.
			CenDatoscv recordInsert = new CenDatoscv();
			recordInsert.setFechamodificacion(new Date());
			recordInsert.setUsumodificacion(usuario.getIdusuario());
			recordInsert.setIdpersona(Long.parseLong(solModifDatosCurricularesItem.getIdPersona()));
			recordInsert.setIdtipocv(Short.parseShort(solModifDatosCurricularesItem.getIdTipoCv()));

			if ("" != solModifDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != solModifDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordInsert.setIdtipocvsubtipo1(Short.parseShort(solModifDatosCurricularesItem.getIdTipoCvSubtipo1()));
			} else {
				recordInsert.setIdtipocvsubtipo1(null);
			}
			if ("" != solModifDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != solModifDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordInsert.setIdtipocvsubtipo2(Short.parseShort(solModifDatosCurricularesItem.getIdTipoCvSubtipo2()));
			} else {
				recordInsert.setIdtipocvsubtipo2(null);
			}
			if (null != solModifDatosCurricularesItem.getCreditos() && "" != solModifDatosCurricularesItem.getCreditos()) {
				recordInsert.setCreditos(Long.parseLong(solModifDatosCurricularesItem.getCreditos()));
			} else {
				recordInsert.setCreditos(null);
			}

			recordInsert.setFechainicio(solModifDatosCurricularesItem.getDateFechaInicio());
			recordInsert.setFechafin(solModifDatosCurricularesItem.getDateFechaFin());
			recordInsert.setFechabaja(solModifDatosCurricularesItem.getDateFechaFin());
			recordInsert.setCertificado(solModifDatosCurricularesItem.getCertificado());
			recordInsert.setFechamovimiento(solModifDatosCurricularesItem.getDateFechaMovimiento());
			recordInsert.setDescripcion(solModifDatosCurricularesItem.getDescripcion());
			recordInsert.setIdinstitucion(idInstitucion);

			NewIdDTO idCvBD = cenDatoscvExtendsMapper.getMaxIdCv(String.valueOf(idInstitucion),
					solModifDatosCurricularesItem.getIdPersona());
			if (idCvBD == null) {
				recordInsert.setIdcv(Short.parseShort("1"));
			} else {
				int idCv = Integer.parseInt(idCvBD.getNewId()) + 1;
				recordInsert.setIdcv(Short.parseShort("" + idCv));
			}
			
			if(recordInsert.getFechafin() == null) {
				CenDatoscvExample example = new CenDatoscvExample();
				Long idPers = Long.parseLong(solModifDatosCurricularesItem.getIdPersona());
				example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
				List<CenDatoscv> datosCurricularesActivos = cenDatoscvExtendsMapper.selectByExample(example);
				
				if(datosCurricularesActivos != null && datosCurricularesActivos.size() != 0) {
					for(CenDatoscv dato: datosCurricularesActivos) {
						CenDatoscv Actualizar = new CenDatoscv();
						Actualizar = dato;
						Actualizar.setFechabaja(new Date());
						Actualizar.setFechafin(new Date());
						Actualizar.setFechamodificacion(new Date());
						Actualizar.setUsumodificacion(usuario.getIdusuario());
						responseSolicitud = cenDatoscvExtendsMapper.updateByPrimaryKey(Actualizar);
//						update by primarykey
					}

				}
				
			}

			responseSolicitud = cenDatoscvExtendsMapper.insertSelective(recordInsert);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		if(responseSolicitud == 1) {
			CenSolicitudmodificacioncv record = new CenSolicitudmodificacioncv();
			record.setIdsolicitud(Long.valueOf(solModifDatosCurricularesItem.getIdSolicitud()));
			record.setIdestadosolic((short) 20); 
			int response = cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKey(record);
			
			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("processSolModifDatosCurriculares() / cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKey() -> "
						+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

			} 
			
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info(
					"processSolModifDatosCurriculares() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		}else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.info(
					"processSolModifDatosCurriculares() -> Salida del servicio para actualizar el estado de la solicitud por FALLO EN UPDATE");
		}
		}else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> "
					+ updateResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}
		return updateResponseDTO;

	}

	@Override
	public UpdateResponseDTO denySolModifDatosCurriculares(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosCurriculares() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolicitudmodificacioncv record = new CenSolicitudmodificacioncv();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKey(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModifDatosCurriculares() / cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKey() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} 
		
		updateResponseDTO.setStatus(SigaConstants.OK);
		LOGGER.info(
				"denySolModifDatosCurriculares() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
