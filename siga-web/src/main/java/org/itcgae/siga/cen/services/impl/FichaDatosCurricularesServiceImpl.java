package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.IFichaDatosCurricularesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvKey;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
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
	private GenParametrosExtendsMapper genParametrosMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;

	@Autowired
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;

	@Autowired
	private  CenSolicitmodifdatosbasicosExtendsMapper  cenSolicitmodifdatosbasicosMapper;
	
	@Autowired
	private IAuditoriaCenHistoricoService auditoriaCenHistoricoService;

	@Autowired
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

	@Override
	public FichaDatosCurricularesDTO searchDatosCurriculares(
			FichaDatosCurricularesSearchDTO fichaDatosCurricularesSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a searchDatosCurriculares");
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = new FichaDatosCurricularesDTO();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String dni = UserTokenUtils.getDniFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"deleteDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"deleteDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		usuario = usuarios.get(0);
		if (null != idInstitucion) {
			fichaDatosCurricularesItem = cenDatoscvExtendsMapper.searchDatosCurriculares(
					fichaDatosCurricularesSearchDTO.getIdPersona(), fichaDatosCurricularesSearchDTO.getHistorico(),
					String.valueOf(idInstitucion),usuario.getIdlenguaje());
			fichaDatosCurricularesDTO.setFichaDatosCurricularesItem(fichaDatosCurricularesItem);

		}
		LOGGER.info(
				"searchDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de searchDatosCurriculares");
		return fichaDatosCurricularesDTO;
	}

	@Override
	public UpdateResponseDTO deleteDatosCurriculares(FichaDatosCurricularesDTO fichaDatosCurricularesDTO,
			HttpServletRequest request) {
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
			
			for (FichaDatosCurricularesItem fichaDatosCurricularesItem : fichaDatosCurricularesDTO.getFichaDatosCurricularesItem()) {
				
			
			// for (int i = 0; i < FichaDatosCurricularesItem.length; i++) {
			LOGGER.info(
					"deleteDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para eliminar un curriculum");
			CenDatoscv recordUpdate = new CenDatoscv();
			recordUpdate.setFechabaja(new Date());
			recordUpdate.setFechafin(new Date());
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			// recordUpdate.setIdcv(fichaDatosCurricularesItem.getid);
			// recordUpdate.setIddireccion(Long.valueOf(fichaDatosCurricularesDTO[i].getIdDireccion()));
			recordUpdate.setIdinstitucion(idInstitucion);
			recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
			// recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));

//			recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
//			recordUpdate.setCertificado(fichaDatosCurricularesItem.getCertificado());
//			recordUpdate.setFechamovimiento(fichaDatosCurricularesItem.getFechaMovimientoDate());
//			recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
//			recordUpdate.setIdinstitucion(idInstitucion);

//			recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
//			recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));

//			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
//					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
//				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
//			} else {
//				recordUpdate.setIdtipocvsubtipo1(null);
//			}
//			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
//					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
//				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));
//			} else {
//				recordUpdate.setIdtipocvsubtipo2(null);
//			}
//			if (null != fichaDatosCurricularesItem.getCreditos() && "" != fichaDatosCurricularesItem.getCreditos()) {
//				recordUpdate.setCreditos(Long.parseLong(fichaDatosCurricularesItem.getCreditos()));
//			} else {
//				recordUpdate.setCreditos(null);
//			}

			response = cenDatoscvExtendsMapper.updateByPrimaryKeySelective(recordUpdate);
			LOGGER.info(
					"deleteDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para eliminar un curriculum");

			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("deleteDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
						+ updateResponseDTO.getStatus() + ". No se pudo eliminar el curriculum");

			}else {
				updateResponseDTO.setStatus(SigaConstants.OK);
			}
		 }

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("deleteDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> "
					+ updateResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}

		
		LOGGER.info("deleteDatosCurriculares() -> Salida del servicio para eliminar un curriculum");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDatosCurriculares(FichaDatosCurricularesItem fichaDatosCurricularesItem,
			HttpServletRequest request) {
		LOGGER.info(
				"updateDatosCurriculares() -> Entrada al servicio para actualizar información de Datos curriculares");
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
			// for (int i = 0; i < FichaDatosCurricularesItem.length; i++) {
			LOGGER.info(
					"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar un curriculum");
			CenDatoscv recordUpdate = new CenDatoscv();
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));
			
			CenDatoscvKey cenDatoscvKey= new CenDatoscvKey();
			cenDatoscvKey.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
			cenDatoscvKey.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			cenDatoscvKey.setIdinstitucion(idInstitucion);
			
			CenDatoscv cenDatoscvAnterior = cenDatoscvExtendsMapper.selectByPrimaryKey(cenDatoscvKey);
			CenDatoscv cenDatoscvPosterior = recordUpdate;

			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
				recordUpdate.setIdinstitucionSubt1(Short.valueOf(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo1()));
//				CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
//				cenTiposcvsubtipo1Example.createCriteria()
//						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
//						.andIdtipocvsubtipo1EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()))
//						.andIdinstitucionEqualTo(idInstitucion);
//
//				List<CenTiposcvsubtipo1> listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper
//						.selectByExample(cenTiposcvsubtipo1Example);
//
//				if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
//					recordUpdate.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
//				} else {
//
//					cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
//					cenTiposcvsubtipo1Example.createCriteria()
//							.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
//							.andIdtipocvsubtipo1EqualTo(
//									Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()))
//							.andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_2000);
//
//					listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper.selectByExample(cenTiposcvsubtipo1Example);
//
//					if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
//						recordUpdate.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
//					}
//				}

			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
				recordUpdate.setIdinstitucionSubt1(null);
			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));
				recordUpdate.setIdinstitucionSubt2(Short.valueOf(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo2()));

//				CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
//				cenTiposcvsubtipo2Example.createCriteria()
//						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
//						.andIdtipocvsubtipo2EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
//						.andIdinstitucionEqualTo(idInstitucion);
//
//				List<CenTiposcvsubtipo2> listCensubtipos2 = cenTiposCVSubtipo2ExtendsMapper
//						.selectByExample(cenTiposcvsubtipo2Example);
//
//				if (null != listCensubtipos2 && listCensubtipos2.size() > 0) {
//					recordUpdate.setIdinstitucionSubt2(listCensubtipos2.get(0).getIdinstitucion());
//				} else {
//					cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
//					cenTiposcvsubtipo2Example.createCriteria()
//							.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
//							.andIdtipocvsubtipo2EqualTo(
//									Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
//							.andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_2000);
//
//					listCensubtipos2 = cenTiposCVSubtipo2ExtendsMapper.selectByExample(cenTiposcvsubtipo2Example);
//
//					if (null != listCensubtipos2 && listCensubtipos2.size() > 0) {
//						recordUpdate.setIdinstitucionSubt2(listCensubtipos2.get(0).getIdinstitucion());
//					}
//				}

			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
				recordUpdate.setIdinstitucionSubt2(null);

			}
			if (null != fichaDatosCurricularesItem.getCreditos() && "" != fichaDatosCurricularesItem.getCreditos()) {
				recordUpdate.setCreditos(Long.parseLong(fichaDatosCurricularesItem.getCreditos()));
			} else {
				recordUpdate.setCreditos(null);
			}
			// if(null != fichaDatosCurricularesItem.getCertificado()){
			recordUpdate.setCertificado(fichaDatosCurricularesItem.getCertificado());
			// }
			if (null != fichaDatosCurricularesItem.getDescripcion()
					&& "" != fichaDatosCurricularesItem.getDescripcion()) {
				recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			}
			// if(null != fichaDatosCurricularesItem.getIdTipoCv()){
			// }
			recordUpdate.setIdinstitucion(idInstitucion);

			recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
			// recordUpdate.setFechabaja(fichaDatosCurricularesItem.getFechaHastaDate());
			recordUpdate.setFechamovimiento(fichaDatosCurricularesItem.getFechaMovimientoDate());
			recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
			// recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));

			// if (recordUpdate.getFechafin() == null) {
			// CenDatoscvExample example = new CenDatoscvExample();
			// Long idPers = Long.parseLong(fichaDatosCurricularesItem.getIdPersona());
			// example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion)
			// .andFechabajaIsNull();
			// List<CenDatoscv> datosCurricularesActivos =
			// cenDatoscvExtendsMapper.selectByExample(example);
			//
			// if (datosCurricularesActivos != null && datosCurricularesActivos.size() != 0)
			// {
			// for (CenDatoscv dato : datosCurricularesActivos) {
			// CenDatoscv Actualizar = new CenDatoscv();
			// Actualizar = dato;
			// Actualizar.setFechabaja(new Date());
			// Actualizar.setFechafin(new Date());
			// Actualizar.setFechamodificacion(new Date());
			// Actualizar.setUsumodificacion(usuario.getIdusuario());
			// response = cenDatoscvExtendsMapper.updateByPrimaryKey(Actualizar);
			// // update by primarykey
			// }
			//
			// }
			//
			// }
			response = cenDatoscvExtendsMapper.updateByPrimaryKey(recordUpdate);

			LOGGER.info(
					"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para actualizar un curriculum");

			updateResponseDTO.setStatus(SigaConstants.OK);
			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
						+ updateResponseDTO.getStatus() + ". No se pudo modificar el curriculum");

			} else {
				updateResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
						+ updateResponseDTO.getStatus() + ". se modificó correctamente el curriculum");
				
				// AUDITORIA si se actualizó un dato curricular correctamente

				auditoriaCenHistoricoService.manageAuditoriaDatosCv(cenDatoscvAnterior,
						cenDatoscvPosterior, "UPDATE", request, "Modificacion de curriculum");
			}
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> "
					+ updateResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("updateDatosCurriculares() -> Salida del servicio para actualizar un curriculum");
		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertDatosCurriculares(FichaDatosCurricularesItem fichaDatosCurricularesItem,
			HttpServletRequest request) {
		LOGGER.info("insertDatosCurriculares() -> Entrada al servicio para insertar Datos curriculares");
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
				"insertDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"insertDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			// for (int i = 0; i < FichaDatosCurricularesItem.length; i++) {
			LOGGER.info(
					"insertDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para insertar un curriculum");
			CenDatoscv recordInsert = new CenDatoscv();
			recordInsert.setFechamodificacion(new Date());
			recordInsert.setUsumodificacion(usuario.getIdusuario());
			recordInsert.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			recordInsert.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));

			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordInsert.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));

				CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
				cenTiposcvsubtipo1Example.createCriteria()
						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
						.andIdtipocvsubtipo1EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()))
						.andIdinstitucionEqualTo(Short.valueOf(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo1()));

				List<CenTiposcvsubtipo1> listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper
						.selectByExample(cenTiposcvsubtipo1Example);

				if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
					recordInsert.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
				} else {

					cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
					cenTiposcvsubtipo1Example.createCriteria()
							.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
							.andIdtipocvsubtipo1EqualTo(
									Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()))
							.andIdinstitucionEqualTo(Short.valueOf(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo1()));

					listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper.selectByExample(cenTiposcvsubtipo1Example);

					if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
						recordInsert.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
					}
				}

			} else {
				recordInsert.setIdtipocvsubtipo1(null);
				recordInsert.setIdinstitucionSubt1(null);

			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordInsert.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));

				CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
				cenTiposcvsubtipo2Example.createCriteria()
						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
						.andIdtipocvsubtipo2EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
						.andIdinstitucionEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo2()));

				List<CenTiposcvsubtipo2> listCensubtipos2 = cenTiposCVSubtipo2ExtendsMapper
						.selectByExample(cenTiposcvsubtipo2Example);

				if (null != listCensubtipos2 && listCensubtipos2.size() > 0) {
					recordInsert.setIdinstitucionSubt2(listCensubtipos2.get(0).getIdinstitucion());
				} else {
					cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
					cenTiposcvsubtipo2Example.createCriteria()
							.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
							.andIdtipocvsubtipo2EqualTo(
									Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
							.andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_2000);

					listCensubtipos2 = cenTiposCVSubtipo2ExtendsMapper.selectByExample(cenTiposcvsubtipo2Example);

					if (null != listCensubtipos2 && listCensubtipos2.size() > 0) {
						recordInsert.setIdinstitucionSubt2(listCensubtipos2.get(0).getIdinstitucion());
					}
				}

			} else {
				recordInsert.setIdtipocvsubtipo2(null);
				recordInsert.setIdinstitucionSubt2(null);

			}
			if (null != fichaDatosCurricularesItem.getCreditos() && "" != fichaDatosCurricularesItem.getCreditos()) {
				recordInsert.setCreditos(Long.parseLong(fichaDatosCurricularesItem.getCreditos()));
			} else {
				recordInsert.setCreditos(null);
			}

			recordInsert.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordInsert.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
			// recordInsert.setFechabaja(fichaDatosCurricularesItem.getFechaHastaDate());
			recordInsert.setCertificado(fichaDatosCurricularesItem.getCertificado());
			recordInsert.setFechamovimiento(fichaDatosCurricularesItem.getFechaMovimientoDate());
			recordInsert.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			recordInsert.setIdinstitucion(idInstitucion);

			NewIdDTO idCvBD = cenDatoscvExtendsMapper.getMaxIdCv(String.valueOf(idInstitucion),
					fichaDatosCurricularesItem.getIdPersona());
			if (idCvBD == null) {
				recordInsert.setIdcv(Short.parseShort("1"));
			} else {
				int idCv = Integer.parseInt(idCvBD.getNewId()) + 1;
				recordInsert.setIdcv(Short.parseShort("" + idCv));
			}

			// if(recordInsert.getFechafin() == null) {
			// CenDatoscvExample example = new CenDatoscvExample();
			// Long idPers = Long.parseLong(fichaDatosCurricularesItem.getIdPersona());
			// example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
			// List<CenDatoscv> datosCurricularesActivos =
			// cenDatoscvExtendsMapper.selectByExample(example);
			//
			// if(datosCurricularesActivos != null && datosCurricularesActivos.size() != 0)
			// {
			// for(CenDatoscv dato: datosCurricularesActivos) {
			// CenDatoscv Actualizar = new CenDatoscv();
			// Actualizar = dato;
			// Actualizar.setFechabaja(new Date());
			// Actualizar.setFechafin(new Date());
			// Actualizar.setFechamodificacion(new Date());
			// Actualizar.setUsumodificacion(usuario.getIdusuario());
			// response = cenDatoscvExtendsMapper.updateByPrimaryKey(Actualizar);
			//// update by primarykey
			// }
			//
			// }
			//
			// }

			response = cenDatoscvExtendsMapper.insertSelective(recordInsert);

			LOGGER.info(
					"insertDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para insertar un curriculum");

			if (response == 0) {
				insertResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("insertDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
						+ insertResponseDTO.getStatus() + ". No se pudo eliminar el curriculum");

			}else {
				LOGGER.warn("insertDatosCurriculares() -> OK. Insert para datos curriculares realizado correctamente");
				insertResponseDTO.setStatus(SigaConstants.OK);

				// AUDITORIA si se creó un dato curricular correctamente
				CenDatoscv cenDatoscvPosterior = recordInsert;

				auditoriaCenHistoricoService.manageAuditoriaDatosCv(null,
						cenDatoscvPosterior, "INSERT", request, "Insertado curriculum");
			}
			// }

		} else {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("insertDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> "
					+ insertResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("insertDatosCurriculares() -> Salida del servicio para insertar un curriculum");
		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO solicitudUpdateDatosCurriculares(FichaDatosCurricularesItem fichaDatosCurricularesItem,
			HttpServletRequest request) {
		LOGGER.info(
				"updateDatosCurriculares() -> Entrada al servicio para actualizar información de Datos curriculares");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;
		int	responseSolicitud;
		
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
		Error error = new Error();
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			LOGGER.info(
					"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar un curriculum");
			CenDatoscvKey cenDatoscvKey= new CenDatoscvKey();
			cenDatoscvKey.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
			cenDatoscvKey.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			cenDatoscvKey.setIdinstitucion(idInstitucion);
			
			CenDatoscv cenDatoscvAnterior = cenDatoscvExtendsMapper.selectByPrimaryKey(cenDatoscvKey);
			CenDatoscv cenDatoscvPosterior = new CenDatoscv();
			
			CenSolicitudmodificacioncv recordUpdate = new CenSolicitudmodificacioncv();
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));
			recordUpdate.setMotivo(fichaDatosCurricularesItem.getMotivo());

			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
				recordUpdate.setIdinstitucionSubt1(Short.parseShort(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo1()));

			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
				recordUpdate.setIdinstitucionSubt1(null);
			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));
				recordUpdate.setIdinstitucionSubt2(Short.parseShort(fichaDatosCurricularesItem.getIdInsTipoCvSubtipo2()));

			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
				recordUpdate.setIdinstitucionSubt2(null);

			}
			if (null != fichaDatosCurricularesItem.getDescripcion()
					&& "" != fichaDatosCurricularesItem.getDescripcion()) {
				recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			}
			// if(null != fichaDatosCurricularesItem.getIdTipoCv()){
			// }

			List<GenParametros> autoAceptar = new ArrayList<GenParametros>();
			
			GenParametros param = new GenParametros();
			ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
			parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
			parametroRequestDTO.setModulo("CEN");
			parametroRequestDTO.setParametrosGenerales("SOLICITUDES_MODIF_CENSO");
			StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
			param.setParametro("SOLICITUDES_MODIF_CENSO");
			param.setValor(paramRequest.getValor());
			autoAceptar.add(param);
			
			if(autoAceptar.size() == 0) {
				GenParametros combo = new GenParametros();
				combo.setValor("N");
				autoAceptar.add(combo);
			}
						if(autoAceptar.size() > 0) {
				if(autoAceptar.get(0).getValor().equals("S")) {
					recordUpdate.setIdestadosolic(Short.parseShort("20"));
				}else {
					recordUpdate.setIdestadosolic(Short.parseShort("10"));
				}
			}else {
				recordUpdate.setIdestadosolic(Short.parseShort("10"));
			}
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setFechaalta(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
			recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			recordUpdate.setIdinstitucion(idInstitucion);
			recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
			recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
			// recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));

			NewIdDTO idSolicitudBD = cenSolicitudmodificacioncvExtendsMapper
					.getMaxIdSolicitud(String.valueOf(idInstitucion), fichaDatosCurricularesItem.getIdPersona());
				int idSolic = Integer.parseInt(idSolicitudBD.getNewId()) + 1;
				recordUpdate.setIdsolicitud(Long.parseLong("" + idSolic));
				

			response = cenSolicitudmodificacioncvExtendsMapper.solicitudUpdateCurriculo(recordUpdate);
			
			// Generamos el objeto dato curricular posterior
			cenDatoscvPosterior.setFechafin(new Date());
			cenDatoscvPosterior.setFechamodificacion(new Date());
			cenDatoscvPosterior.setUsumodificacion(usuario.getIdusuario());
			cenDatoscvPosterior.setFechamodificacion(new Date());
			cenDatoscvPosterior.setUsumodificacion(usuario.getIdusuario());
			cenDatoscvPosterior.setIdpersona(recordUpdate.getIdpersona());
			cenDatoscvPosterior.setIdtipocv(recordUpdate.getIdtipocv());
			if (!UtilidadesString.esCadenaVacia(String.valueOf(recordUpdate.getIdtipocvsubtipo1()))
					|| null != recordUpdate.getIdtipocvsubtipo1()) {
				cenDatoscvPosterior.setIdtipocvsubtipo1(recordUpdate.getIdtipocvsubtipo1());
			} else {
				cenDatoscvPosterior.setIdtipocvsubtipo1(null);
			}
			if (!UtilidadesString.esCadenaVacia(String.valueOf(recordUpdate.getIdtipocvsubtipo2()))
					|| null != recordUpdate.getIdtipocvsubtipo2()) {
				cenDatoscvPosterior.setIdtipocvsubtipo2(recordUpdate.getIdtipocvsubtipo2());
			} else {
				cenDatoscvPosterior.setIdtipocvsubtipo2(null);
			}
			if (null != recordUpdate.getDescripcion() && "" != recordUpdate.getDescripcion()) {
				cenDatoscvPosterior.setDescripcion(recordUpdate.getDescripcion());
			}
			cenDatoscvPosterior.setIdinstitucion(idInstitucion);
			cenDatoscvPosterior.setFechamodificacion(new Date());
			cenDatoscvPosterior.setUsumodificacion(usuario.getIdusuario());
			cenDatoscvPosterior.setFechainicio(recordUpdate.getFechainicio());
			cenDatoscvPosterior.setFechafin(recordUpdate.getFechafin());
			cenDatoscvPosterior.setIdcv((recordUpdate.getIdcv()));
			
		if(autoAceptar.size() > 0) {
			if(autoAceptar.get(0).getValor().equals("S")) {
				
							responseSolicitud = cenDatoscvExtendsMapper.updateCurriculo(cenDatoscvPosterior);
							if(responseSolicitud == 1) {
								LOGGER.info("updateDatosCurriculares() -> Salida del servicio para actualizar un curriculum");
							}
							error.setCode(200);
							error.setDescription("Su petición ha sido aceptada automáticamente. Puede ver ya los datos actualizados");
						}else {
							GenParametrosExample ejemploParam = new GenParametrosExample();
							List<GenParametros> xDias = new ArrayList<GenParametros>();
							ejemploParam.createCriteria().andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION").andIdinstitucionEqualTo(idInstitucion);
							xDias= genParametrosMapper.selectByExample(ejemploParam);
							error.setCode(200);
							if(xDias.size() == 0) {
								GenParametrosExample ejemploParam2 = new GenParametrosExample();
								ejemploParam2.createCriteria().andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION").andIdinstitucionEqualTo((short)2000);
								xDias= genParametrosMapper.selectByExample(ejemploParam2);
							}
							error.setDescription("Su petición ha sido registrada y será revisada en los próximos "+xDias.get(0).getValor()+" días. Puede comprobar el estado de su petición en el menú Solicitudes de modificación");
						}
		}else {
			GenParametrosExample ejemploParam = new GenParametrosExample();
			List<GenParametros> xDias = new ArrayList<GenParametros>();
			ejemploParam.createCriteria().andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION").andIdinstitucionEqualTo(idInstitucion);
			xDias= genParametrosMapper.selectByExample(ejemploParam);
			error.setCode(200);
			if(xDias.size() == 0) {
				GenParametrosExample ejemploParam2 = new GenParametrosExample();
				ejemploParam2.createCriteria().andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION").andIdinstitucionEqualTo((short)2000);
				xDias= genParametrosMapper.selectByExample(ejemploParam2);
			}
			error.setDescription("Su petición ha sido registrada y será revisada en los próximos "+xDias.get(0).getValor()+" días. Puede comprobar el estado de su petición en el menú Solicitudes de modificación");

		}
						
			updateResponseDTO.setError(error);
			
			LOGGER.info(
					"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para actualizar un curriculum");

			updateResponseDTO.setStatus(SigaConstants.OK);
			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
						+ updateResponseDTO.getStatus() + ". No se pudo modificar el curriculum");
				
			} else {
				updateResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.warn("updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> "
						+ updateResponseDTO.getStatus() + ". se modificó correctamente el curriculum");
				
				// AUDITORIA si se actualizó un dato curricular correctamente

				auditoriaCenHistoricoService.manageAuditoriaDatosCv(cenDatoscvAnterior,
						cenDatoscvPosterior, "UPDATE", request, fichaDatosCurricularesItem.getMotivo());
			}
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> "
					+ updateResponseDTO.getStatus() + ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("updateDatosCurriculares() -> Salida del servicio para actualizar un curriculum");
		return updateResponseDTO;
	}

}
