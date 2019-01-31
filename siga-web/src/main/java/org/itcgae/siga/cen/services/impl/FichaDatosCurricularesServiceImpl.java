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
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvExample;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
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

	@Autowired
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;

	@Autowired
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;

	@Autowired
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

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
			fichaDatosCurricularesItem = cenDatoscvExtendsMapper.searchDatosCurriculares(
					fichaDatosCurricularesSearchDTO.getIdPersona(), fichaDatosCurricularesSearchDTO.getHistorico(),
					String.valueOf(idInstitucion));
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

			recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordUpdate.setCertificado(fichaDatosCurricularesItem.getCertificado());
			recordUpdate.setFechamovimiento(fichaDatosCurricularesItem.getFechaMovimientoDate());
			recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			recordUpdate.setIdinstitucion(idInstitucion);

			recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));

			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));
			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
			}
			if (null != fichaDatosCurricularesItem.getCreditos() && "" != fichaDatosCurricularesItem.getCreditos()) {
				recordUpdate.setCreditos(Long.parseLong(fichaDatosCurricularesItem.getCreditos()));
			} else {
				recordUpdate.setCreditos(null);
			}

			response = cenDatoscvExtendsMapper.updateCurriculo(recordUpdate);

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

			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));

				CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
				cenTiposcvsubtipo1Example.createCriteria()
						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
						.andIdtipocvsubtipo1EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()))
						.andIdinstitucionEqualTo(idInstitucion);

				List<CenTiposcvsubtipo1> listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper
						.selectByExample(cenTiposcvsubtipo1Example);

				if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
					recordUpdate.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
				} else {

					cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
					cenTiposcvsubtipo1Example.createCriteria()
							.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
							.andIdtipocvsubtipo1EqualTo(
									Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()))
							.andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_2000);

					listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper.selectByExample(cenTiposcvsubtipo1Example);

					if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
						recordUpdate.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
					}
				}

			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));

				CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
				cenTiposcvsubtipo2Example.createCriteria()
						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
						.andIdtipocvsubtipo2EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
						.andIdinstitucionEqualTo(idInstitucion);

				List<CenTiposcvsubtipo2> listCensubtipos2 = cenTiposCVSubtipo2ExtendsMapper
						.selectByExample(cenTiposcvsubtipo2Example);

				if (null != listCensubtipos2 && listCensubtipos2.size() > 0) {
					recordUpdate.setIdinstitucionSubt2(listCensubtipos2.get(0).getIdinstitucion());
				} else {
					cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
					cenTiposcvsubtipo2Example.createCriteria()
							.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
							.andIdtipocvsubtipo2EqualTo(
									Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
							.andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_2000);

					listCensubtipos2 = cenTiposCVSubtipo2ExtendsMapper.selectByExample(cenTiposcvsubtipo2Example);

					if (null != listCensubtipos2 && listCensubtipos2.size() > 0) {
						recordUpdate.setIdinstitucionSubt2(listCensubtipos2.get(0).getIdinstitucion());
					}
				}

			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
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
			response = cenDatoscvExtendsMapper.updateCurriculo(recordUpdate);

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
						.andIdinstitucionEqualTo(idInstitucion);

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
							.andIdinstitucionEqualTo(SigaConstants.IDINSTITUCION_2000);

					listCensubtipos1 = cenTiposCVSubtipo1ExtendsMapper.selectByExample(cenTiposcvsubtipo1Example);

					if (null != listCensubtipos1 && listCensubtipos1.size() > 0) {
						recordInsert.setIdinstitucionSubt1(listCensubtipos1.get(0).getIdinstitucion());
					}
				}

			} else {
				recordInsert.setIdtipocvsubtipo1(null);
			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordInsert.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));

				CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
				cenTiposcvsubtipo2Example.createCriteria()
						.andIdtipocvEqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()))
						.andIdtipocvsubtipo2EqualTo(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()))
						.andIdinstitucionEqualTo(idInstitucion);

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
				insertResponseDTO.setStatus(SigaConstants.OK);
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
			LOGGER.info(
					"updateDatosCurriculares() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar un curriculum");
			CenSolicitudmodificacioncv recordUpdate = new CenSolicitudmodificacioncv();
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setIdpersona(Long.parseLong(fichaDatosCurricularesItem.getIdPersona()));
			recordUpdate.setIdtipocv(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCv()));
			recordUpdate.setMotivo(fichaDatosCurricularesItem.getMotivo());

			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo1()) {
				recordUpdate.setIdtipocvsubtipo1(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo1()));
			} else {
				recordUpdate.setIdtipocvsubtipo1(null);
			}
			if ("" != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()
					&& null != fichaDatosCurricularesItem.getIdTipoCvSubtipo2()) {
				recordUpdate.setIdtipocvsubtipo2(Short.parseShort(fichaDatosCurricularesItem.getIdTipoCvSubtipo2()));
			} else {
				recordUpdate.setIdtipocvsubtipo2(null);
			}
			if (null != fichaDatosCurricularesItem.getDescripcion()
					&& "" != fichaDatosCurricularesItem.getDescripcion()) {
				recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			}
			// if(null != fichaDatosCurricularesItem.getIdTipoCv()){
			// }
			recordUpdate.setFechamodificacion(new Date());
			recordUpdate.setUsumodificacion(usuario.getIdusuario());
			recordUpdate.setFechaalta(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
			recordUpdate.setDescripcion(fichaDatosCurricularesItem.getDescripcion());
			recordUpdate.setIdinstitucion(idInstitucion);
			recordUpdate.setIdinstitucion(idInstitucion);
			recordUpdate.setFechainicio(fichaDatosCurricularesItem.getFechaDesdeDate());
			recordUpdate.setFechafin(fichaDatosCurricularesItem.getFechaHastaDate());
			recordUpdate.setIdcv(Short.parseShort(fichaDatosCurricularesItem.getIdCv()));
			// recordUpdate.setIdpersona(Long.valueOf(fichaDatosCurricularesDTO[i].getIdPersona()));

			NewIdDTO idSolicitudBD = cenSolicitudmodificacioncvExtendsMapper
					.getMaxIdSolicitud(String.valueOf(idInstitucion), fichaDatosCurricularesItem.getIdPersona());
			if (idSolicitudBD == null) {
				recordUpdate.setIdsolicitud(Long.parseLong("1"));
			} else {
				int idSolic = Integer.parseInt(idSolicitudBD.getNewId()) + 1;
				recordUpdate.setIdsolicitud(Long.parseLong("" + idSolic));
			}

			// Esto informa la fecha baja de los demás datos curriculares cuando no está
			// informada la del actual
			// if(recordUpdate.getFechafin() == null) {
			// CenSolicitudmodificacioncvExample example = new
			// CenSolicitudmodificacioncvExample();
			// Long idPers = Long.parseLong(fichaDatosCurricularesItem.getIdPersona());
			// example.createCriteria().andIdpersonaEqualTo(idPers).andIdinstitucionEqualTo(idInstitucion).andFechafinIsNull();
			// List<CenSolicitudmodificacioncv> datosCurricularesActivos =
			// cenSolicitudmodificacioncvExtendsMapper.selectByExample(example);
			//
			// if(datosCurricularesActivos != null && datosCurricularesActivos.size() != 0)
			// {
			// for(CenSolicitudmodificacioncv dato: datosCurricularesActivos) {
			// CenSolicitudmodificacioncv Actualizar = new CenSolicitudmodificacioncv();
			// Actualizar = dato;
			// Actualizar.setFechafin(new Date());
			// Actualizar.setFechamodificacion(new Date());
			// Actualizar.setUsumodificacion(usuario.getIdusuario());
			// response =
			// cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKey(Actualizar);
			// }
			// }
			// }

			response = cenSolicitudmodificacioncvExtendsMapper.solicitudUpdateCurriculo(recordUpdate);

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