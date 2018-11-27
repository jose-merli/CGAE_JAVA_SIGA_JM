package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.DTOs.cen.SolicitModifDatosBasicosDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosCurricularesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvExample;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncvExample;
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
		
		CenSolicitudmodificacioncv solicitud = new CenSolicitudmodificacioncv();
		CenSolicitudmodificacioncvExample example = new CenSolicitudmodificacioncvExample();
		
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(solModifDatosCurricularesItem.getIdPersona()))
		.andIdinstitucionEqualTo(idInstitucion).andFechafinIsNull().andIdsolicitudEqualTo(Long.parseLong(solModifDatosCurricularesItem.getIdSolicitud()));
		
		List<CenSolicitudmodificacioncv> lista = cenSolicitudmodificacioncvExtendsMapper.selectByExample(example);

		solicitud = lista.get(0);
		
		
		if(solicitud.getIdcv() != null) {
			CenDatoscv recordUpdate = new CenDatoscv();
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setIdpersona(solicitud.getIdpersona());
			recordUpdate.setIdtipocv(solicitud.getIdcv());

			if (solicitud.getIdtipocvsubtipo1().equals("")
					&& null != solicitud.getIdtipocvsubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(solicitud.getIdtipocvsubtipo1());
			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
			}
			if (solicitud.getIdtipocvsubtipo2().equals("")
					&& null != solicitud.getIdtipocvsubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(solicitud.getIdtipocvsubtipo2());
			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
			}
			if (null != solicitud.getDescripcion()
					&& "" != solicitud.getDescripcion()) {
				recordUpdate.setDescripcion(solicitud.getDescripcion());
			}
			recordUpdate.setIdinstitucion(idInstitucion);
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setFechainicio(solicitud.getFechainicio());
			recordUpdate.setFechafin(solicitud.getFechafin());
			recordUpdate.setFechabaja(solicitud.getFechafin());
			recordUpdate.setIdcv((solicitud.getIdcv()));
			
			if(recordUpdate.getFechafin() == null) {
				CenDatoscvExample example1 = new CenDatoscvExample();
				Long idPers = solicitud.getIdpersona();
				example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion).andFechafinIsNull();
				List<CenDatoscv> datosCurricularesActivos = cenDatoscvExtendsMapper.selectByExample(example1);
				
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
			CenDatoscv recordInsert = new CenDatoscv();
			recordInsert.setFechamodificacion(new Date());
			recordInsert.setUsumodificacion(usuario.getIdusuario());
			recordInsert.setIdpersona(solicitud.getIdpersona());
			recordInsert.setIdtipocv(solicitud.getIdcv());

			if (solicitud.getIdtipocvsubtipo1().equals("")
					&& null != solicitud.getIdtipocvsubtipo1()) {
				recordInsert.setIdtipocvsubtipo1(solicitud.getIdtipocvsubtipo1());
			} else {
				recordInsert.setIdtipocvsubtipo1(null);
			}
			if (solicitud.getIdtipocvsubtipo2().equals("")
					&& null != solicitud.getIdtipocvsubtipo2()) {
				recordInsert.setIdtipocvsubtipo2(solicitud.getIdtipocvsubtipo2());
			} else {
				recordInsert.setIdtipocvsubtipo2(null);
			}

			recordInsert.setFechainicio(solicitud.getFechainicio());
			recordInsert.setFechafin(solicitud.getFechafin());
			recordInsert.setFechabaja(solicitud.getFechafin());
			recordInsert.setDescripcion(solicitud.getDescripcion());
			recordInsert.setIdinstitucion(idInstitucion);

			NewIdDTO idCvBD = cenDatoscvExtendsMapper.getMaxIdCv(String.valueOf(idInstitucion),
					String.valueOf(solicitud.getIdpersona()));
			if (idCvBD == null) {
				recordInsert.setIdcv(Short.parseShort("1"));
			} else {
				int idCv = Integer.parseInt(idCvBD.getNewId()) + 1;
				recordInsert.setIdcv(Short.parseShort("" + idCv));
			}
			
			if(recordInsert.getFechafin() == null) {
				CenDatoscvExample example2 = new CenDatoscvExample();
				Long idPers = solicitud.getIdpersona();
				example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion).andFechafinIsNull();
				List<CenDatoscv> datosCurricularesActivos = cenDatoscvExtendsMapper.selectByExample(example2);
				
				if(datosCurricularesActivos != null && datosCurricularesActivos.size() != 0) {
					for(CenDatoscv dato: datosCurricularesActivos) {
						CenDatoscv Actualizar = new CenDatoscv();
						Actualizar = dato;
						Actualizar.setFechabaja(new Date());
						Actualizar.setFechafin(new Date());
						Actualizar.setFechamodificacion(new Date());
						Actualizar.setUsumodificacion(usuario.getIdusuario());
						responseSolicitud = cenDatoscvExtendsMapper.updateByPrimaryKey(Actualizar);
					}

				}
				
			}

			responseSolicitud = cenDatoscvExtendsMapper.insertSelective(recordInsert);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		if(responseSolicitud == 1) {
			CenSolicitudmodificacioncv record = new CenSolicitudmodificacioncv();
			record.setIdsolicitud(solicitud.getIdsolicitud());
			record.setIdestadosolic((short) 20); 
			int response = cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKeySelective(record);
			
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
		int response = cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKeySelective(record);
		
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
