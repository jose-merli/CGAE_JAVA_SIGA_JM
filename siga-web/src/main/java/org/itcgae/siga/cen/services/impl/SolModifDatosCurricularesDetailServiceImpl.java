package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.cen.services.ISolModifDatosCurricularesDetailService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvKey;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Key;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolModifDatosCurricularesDetailServiceImpl implements ISolModifDatosCurricularesDetailService {

	private Logger LOGGER = Logger.getLogger(SolModifDatosCurricularesDetailServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;

	@Autowired
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Autowired
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;

	@Override
	public SolModifDatosCurricularesDTO searchDatosCurricularesDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request) {

		LOGGER.info("searchDatosCurricularesDetail() -> Entrada al servicio para recuperar los datos curriculares");

		SolModifDatosCurricularesDTO solModifDatosCurricularesDTO = new SolModifDatosCurricularesDTO();
		List<SolModifDatosCurricularesItem> solModifDatosCurricularesItems = new ArrayList<SolModifDatosCurricularesItem>();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchDatosCurricularesDetail() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchDatosCurricularesDetail() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				fichaDatosCurricularesItem = cenDatoscvExtendsMapper
						.searchDatosCurriculares(solModificacionItem.getIdPersona(), String.valueOf(idInstitucion));

				for (FichaDatosCurricularesItem element : fichaDatosCurricularesItem) {
					SolModifDatosCurricularesItem solModifDatosCurricularesItem = new SolModifDatosCurricularesItem();

					solModifDatosCurricularesItem.setCategoriaCurricular(element.getCategoriaCurricular());
					solModifDatosCurricularesItem.setDescripcion(element.getDescripcion());
					solModifDatosCurricularesItem.setFechaDesde(element.getFechaDesde());
					solModifDatosCurricularesItem.setFechaHasta(element.getFechaHasta());

					// Buscar subtipo
					CenTiposcvsubtipo1Key key = new CenTiposcvsubtipo1Key();
					key.setIdinstitucion(idInstitucion);
					key.setIdtipocv(Short.valueOf(element.getIdCv()));
					key.setIdtipocvsubtipo1(Short.valueOf(element.getIdTipoCvSubtipo1()));

					LOGGER.info(
							"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Entrada a cenTiposCVSubtipo1ExtendsMapper para obtener el id de la descripcion");
					CenTiposcvsubtipo1 cenTiposcvsubtipo1 = cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey(key);

					LOGGER.info(
							"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Salida a cenTiposCVSubtipo1ExtendsMapper para obtener el id de la descripcion");

					// Buscar descripcion del subtipo en genRecursosCatalogos
					LOGGER.info(
							"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Entrada a genRecursosCatalogosExtendsMapper para obtener la descripcion del subtipo");
					GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
					genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
					genRecursosCatalogosKey.setIdrecurso(cenTiposcvsubtipo1.getDescripcion());

					GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosExtendsMapper
							.selectByPrimaryKey(genRecursosCatalogosKey);

					LOGGER.info(
							"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Salida a genRecursosCatalogosExtendsMapper para obtener la descripcion del subtipo");
					solModifDatosCurricularesItem.setSubtiposCurriculares(genRecursosCatalogos.getDescripcion());
					solModifDatosCurricularesItem.setTipoCurricular(element.getTipoSubtipo());

					solModifDatosCurricularesItems.add(solModifDatosCurricularesItem);

				}
				solModifDatosCurricularesDTO.setSolModifDatosCurricularesItems(solModifDatosCurricularesItems);

			}

		}
		LOGGER.info("searchDatosCurricularesDetail() -> Salida del servicio para recuperar los datos curriculares");

		return solModifDatosCurricularesDTO;
	}

	@Override
	public SolModifDatosCurricularesItem searchSolModifDatosCurricularesDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request) {
//		SolModifDatosCurricularesItem solModifDatosCurricularesItem = new SolModifDatosCurricularesItem();
//		
//		// Conseguimos información del usuario logeado
//				String token = request.getHeader("Authorization");
//				String dni = UserTokenUtils.getDniFromJWTToken(token);
//				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//				if (idInstitucion != null) {
//					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//					LOGGER.info(
//							"searchDatosCurricularesDetail() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//					LOGGER.info(
//							"searchDatosCurricularesDetail() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//					if (usuarios != null && usuarios.size() > 0) {
//
//						AdmUsuarios usuario = usuarios.get(0);
//		CenSolicitudmodificacioncv cenSolicitudmodificacioncv = cenSolicitudmodificacioncvExtendsMapper.selectByPrimaryKey(Long.valueOf(solModificacionItem.getIdSolicitud()));
//		
//		CenDatoscvKey cenDatoscvKey = new CenDatoscvKey();
//		cenDatoscvKey.setIdcv(cenSolicitudmodificacioncv.getIdcv());
//		cenDatoscvKey.setIdinstitucion(usuario.getIdinstitucion());
//		cenDatoscvKey.setIdpersona(Long.valueOf(solModificacionItem.getIdPersona()));
//		
//		// llamar a cenDatos
//		CenDatoscv cenDatoscv = cenDatoscvExtendsMapper.selectByPrimaryKey(cenDatoscvKey);
//		solModifDatosCurricularesItem.setCategoriaCurricular(cenDatoscv.ge);
//		solModifDatosCurricularesItem.setDescripcion(cenSolicitudmodificacioncv.getDescripcion());
//					}
		return null;
	}

}
