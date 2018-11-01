package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.IFichaDatosCurricularesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FichaDatosCurricularesServiceImpl implements IFichaDatosCurricularesService {

	private Logger LOGGER = Logger.getLogger(FichaDatosCurricularesServiceImpl.class);

	@Autowired
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public FichaDatosCurricularesDTO searchDatosCurriculares(
			FichaDatosCurricularesSearchDTO fichaDatosCurricularesSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a searchDatosCurriculares");
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = new FichaDatosCurricularesDTO();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {				
				fichaDatosCurricularesItem = cenDatoscvExtendsMapper.searchDatosCurriculares(fichaDatosCurricularesSearchDTO.getIdPersona(), String.valueOf(idInstitucion));
				fichaDatosCurricularesDTO.setFichaDatosCurricularesItem(fichaDatosCurricularesItem);
			
		}
		LOGGER.info(
				"searchDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de searchDatosCurriculares");
		return fichaDatosCurricularesDTO;
	}

	@Override
	public UpdateResponseDTO deleteDatosCurriculares(FichaDatosCurricularesItem fichaDatosCurricularesItem, HttpServletRequest request) {
		LOGGER.info("deleteDatosCurriculares() -> Entrada al servicio para actualizar información de direcciones");
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
				"deleteDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"deleteDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
//			for (int i = 0; i < FichaDatosCurricularesItem.length; i++) {
				LOGGER.info(
						"deleteDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para eliminar un curriculum");
				CenDatoscv recordUpdate = new CenDatoscv();
				recordUpdate.setFechabaja(new Date());
				recordUpdate.setFechafin(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
//				recordUpdate.setIdcv(fichaDatosCurricularesItem.getid);
//				recordUpdate.setIddireccion(Long.valueOf(fichaDatosCurricularesDTO[i].getIdDireccion()));
				recordUpdate.setIdinstitucion(idInstitucion);
				recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
//				recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));
				
				

				
				
				
				response = cenDatoscvExtendsMapper.updateCurriculo(recordUpdate);

				LOGGER.info(
						"deleteDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para eliminar un curriculum");

				updateResponseDTO.setStatus(SigaConstants.OK);
				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("deleteDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
							+ updateResponseDTO.getStatus() + ". No se pudo eliminar el curriculum");

				}
//			}

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("deleteDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus()
					+ ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("deleteDatosCurriculares() -> Salida del servicio para eliminar un curriculum");
		return updateResponseDTO;
	}
	
	
	
	
	
	@Override
	public UpdateResponseDTO updateDatosCurriculares(FichaDatosCurricularesItem fichaDatosCurricularesItem, HttpServletRequest request) {
		LOGGER.info("updateDatosCurriculares() -> Entrada al servicio para actualizar información de Datos curriculares");
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
				"updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
//			for (int i = 0; i < FichaDatosCurricularesItem.length; i++) {
				LOGGER.info(
						"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar un curriculum");
				CenDatoscv recordUpdate = new CenDatoscv();
//				recordUpdate.setFechabaja(new Date());
//				recordUpdate.setFechafin(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
				recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));

				if(null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()){
					recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
				}
				if(null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()){
					recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));

				}
				if(null != fichaDatosCurricularesItem.getCreditos()){
					recordUpdate.setCreditos(Long.parseLong(fichaDatosCurricularesItem.getCreditos()));
				}
				if(null != fichaDatosCurricularesItem.getCertificado()){
					recordUpdate.setCertificado(fichaDatosCurricularesItem.getCertificado());

				}
				if(null != fichaDatosCurricularesItem.getDescripcion()){
					recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());

				}
//				if(null != fichaDatosCurricularesItem.getIdTipoCv()){
//				}
				recordUpdate.setIdinstitucion(idInstitucion);

//				recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
//				recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
//				recordUpdate.setFechamovimiento(fichaDatosCurricularesItem.getFechaMovimientoDate());
				recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
//				recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));
				

				
				response = cenDatoscvExtendsMapper.updateCurriculo(recordUpdate);

				LOGGER.info(
						"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para actualizar un curriculum");

				updateResponseDTO.setStatus(SigaConstants.OK);
				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
							+ updateResponseDTO.getStatus() + ". No se pudo modificar el curriculum");

				}else {
					updateResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
							+ updateResponseDTO.getStatus() + ". se modificó correctamente el curriculum");
				}
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus()
					+ ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("updateDatosCurriculares() -> Salida del servicio para actualizar un curriculum");
		return updateResponseDTO;
	}
	
	
	@Override
	public InsertResponseDTO insertDatosCurriculares(FichaDatosCurricularesItem fichaDatosCurricularesItem, HttpServletRequest request) {
		LOGGER.info("updateDatosCurriculares() -> Entrada al servicio para actualizar información de Datos curriculares");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
//			for (int i = 0; i < FichaDatosCurricularesItem.length; i++) {
				LOGGER.info(
						"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar un curriculum");
				CenDatoscv recordUpdate = new CenDatoscv();
//				recordUpdate.setFechabaja(new Date());
//				recordUpdate.setFechafin(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
				recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getCategoriaCurricular()));
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));
//				recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
//				recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
				recordUpdate.setCreditos(Long.parseLong(fichaDatosCurricularesItem.getCreditos()));
				recordUpdate.setCertificado(fichaDatosCurricularesItem.getCertificado());
//				recordUpdate.setFechamovimiento(fichaDatosCurricularesItem.getFechaMovimientoDate());
				recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
				recordUpdate.setIdinstitucion(idInstitucion);
//				recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
//				recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));
				
				NewIdDTO idCvBD = cenDatoscvExtendsMapper
						.getMaxIdCv(String.valueOf(idInstitucion), usuario.getIdlenguaje());
				if (idCvBD == null) {
					recordUpdate.setIdcv(Short.parseShort("1"));
				} else {
					int idCv = Integer.parseInt(idCvBD.getNewId()) + 1;
					recordUpdate.setIdcv(Short.parseShort(""+idCv));
				}
				
				response = cenDatoscvExtendsMapper.updateCurriculo(recordUpdate);

				LOGGER.info(
						"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para eliminar un curriculum");

				insertResponseDTO.setStatus(SigaConstants.OK);
				if (response == 0) {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
							+ insertResponseDTO.getStatus() + ". No se pudo eliminar el curriculum");

				}
//			}

		} else {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> " + insertResponseDTO.getStatus()
					+ ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("updateDatosCurriculares() -> Salida del servicio para eliminar un curriculum");
		return insertResponseDTO;
	}
	
	
	
}
