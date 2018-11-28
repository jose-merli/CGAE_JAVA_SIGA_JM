package org.itcgae.siga.cen.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Key;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Key;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
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
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Autowired
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;

	@Autowired
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;

	@Override
	public SolModifDatosCurricularesItem searchDatosCurricularesDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request) {

		LOGGER.info("searchDatosCurricularesDetail() -> Entrada al servicio para recuperar los datos curriculares");

		SolModifDatosCurricularesItem solModifDatosCurricularesItem = new SolModifDatosCurricularesItem();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
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

				CenDatoscvKey cenDatoscvKey = new CenDatoscvKey();
				cenDatoscvKey.setIdcv(Short.valueOf(solModificacionItem.getCodigo()));
				cenDatoscvKey.setIdinstitucion(idInstitucion);
				cenDatoscvKey.setIdpersona(Long.valueOf(solModificacionItem.getIdPersona()));

				CenDatoscv cenDatoscv = cenDatoscvExtendsMapper.selectByPrimaryKey(cenDatoscvKey);

				if (cenDatoscv != null) {

					// Buscar categoría curricular
					CenTiposcv cenTiposcv = cenTiposcvExtendsMapper.selectByPrimaryKey(cenDatoscv.getIdtipocv());

					GenRecursosCatalogosKey genRecursosCatalogosKeyCC = new GenRecursosCatalogosKey();
					genRecursosCatalogosKeyCC.setIdlenguaje(usuario.getIdlenguaje());
					genRecursosCatalogosKeyCC.setIdrecurso(cenTiposcv.getDescripcion());

					GenRecursosCatalogos genRecursosCatalogosCC = genRecursosCatalogosExtendsMapper
							.selectByPrimaryKey(genRecursosCatalogosKeyCC);

					solModifDatosCurricularesItem.setCategoriaCurricular(genRecursosCatalogosCC.getDescripcion());
					solModifDatosCurricularesItem.setDescripcion(cenDatoscv.getDescripcion());
					solModifDatosCurricularesItem.setFechaDesde(dateFormat.format(cenDatoscv.getFechainicio()));
					solModifDatosCurricularesItem.setFechaHasta(dateFormat.format(cenDatoscv.getFechafin()));

					if (cenDatoscv.getIdtipocvsubtipo1() != null) {
						// Buscar tipo curricular
						CenTiposcvsubtipo1Key key = new CenTiposcvsubtipo1Key();
						key.setIdinstitucion(idInstitucion);
						key.setIdtipocv(Short.valueOf(cenDatoscv.getIdcv()));
						key.setIdtipocvsubtipo1(Short.valueOf(cenDatoscv.getIdtipocvsubtipo1()));

						LOGGER.info(
								"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Entrada a cenTiposCVSubtipo1ExtendsMapper para obtener el id de la descripcion");
						CenTiposcvsubtipo1 cenTiposcvsubtipo1 = cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey(key);

						LOGGER.info(
								"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Salida a cenTiposCVSubtipo1ExtendsMapper para obtener el id de la descripcion");

						if (cenTiposcvsubtipo1 != null) {
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

							solModifDatosCurricularesItem.setTipoCurricular(genRecursosCatalogos.getDescripcion());
						}
					}
					
					
					if(cenDatoscv.getIdtipocvsubtipo2() != null) {
						// Buscar subtipo
						CenTiposcvsubtipo2Key keySC = new CenTiposcvsubtipo2Key();
						keySC.setIdinstitucion(idInstitucion);
						keySC.setIdtipocv(Short.valueOf(cenDatoscv.getIdcv()));
						keySC.setIdtipocvsubtipo2(Short.valueOf(cenDatoscv.getIdtipocvsubtipo2()));

						CenTiposcvsubtipo2 cenTiposcvsubtipo2 = cenTiposCVSubtipo2ExtendsMapper.selectByPrimaryKey(keySC);

						if (cenTiposcvsubtipo2 != null) {
							GenRecursosCatalogosKey genRecursosCatalogosKeySC = new GenRecursosCatalogosKey();
							genRecursosCatalogosKeySC.setIdlenguaje(usuario.getIdlenguaje());
							genRecursosCatalogosKeySC.setIdrecurso(cenTiposcvsubtipo2.getDescripcion());

							GenRecursosCatalogos genRecursosCatalogosSC = genRecursosCatalogosExtendsMapper
									.selectByPrimaryKey(genRecursosCatalogosKeySC);

							solModifDatosCurricularesItem.setSubtiposCurriculares(genRecursosCatalogosSC.getDescripcion());
						}
					}
				}
			}
		}

		LOGGER.info("searchDatosCurricularesDetail() -> Salida del servicio para recuperar los datos curriculares");
		return solModifDatosCurricularesItem;
	}

	@Override
	public SolModifDatosCurricularesItem searchSolModifDatosCurricularesDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request) {

		LOGGER.info(
				"searchSolModifDatosCurricularesDetail() -> Entrada del servicio para recuperar los datos de la solicitud de datos curriculares");

		SolModifDatosCurricularesItem solModifDatosCurricularesItem = new SolModifDatosCurricularesItem();
		DateFormat formatter = new SimpleDateFormat("dd/MMM/yy");

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

				// Obtenemos los datos de la solicitud consultando por el id de la solicitud
				LOGGER.info(
						"searchDatosCurricularesDetail() / cenSolicitudmodificacioncvExtendsMapper.selectByPrimaryKey() -> Entrada a cenSolicitudmodificacioncvExtendsMapper para obtener los datos curriculares de la solicitud");

				CenSolicitudmodificacioncv cenSolicitudmodificacioncv = cenSolicitudmodificacioncvExtendsMapper
						.selectByPrimaryKey(Long.valueOf(solModificacionItem.getIdSolicitud()));

				solModifDatosCurricularesItem.setIdPersona(String.valueOf(cenSolicitudmodificacioncv.getIdpersona()));
				solModifDatosCurricularesItem.setIdCv(String.valueOf(cenSolicitudmodificacioncv.getIdcv()));

				LOGGER.info(
						"searchDatosCurricularesDetail() / cenSolicitudmodificacioncvExtendsMapper.selectByPrimaryKey() -> Salida a cenSolicitudmodificacioncvExtendsMapper para obtener los datos curriculares de la solicitud");

				// Tratamiento necesario para obtener la categoría curricular

				LOGGER.info(
						"searchDatosCurricularesDetail() / cenTiposcvExtendsMapper.selectByPrimaryKey() -> Entrada a cenTiposcvExtendsMapper para obtener el campo descripcion de la categoría curricular");

				CenTiposcv cenTiposcv = cenTiposcvExtendsMapper
						.selectByPrimaryKey(cenSolicitudmodificacioncv.getIdtipocv());

				LOGGER.info(
						"searchDatosCurricularesDetail() / cenTiposcvExtendsMapper.selectByPrimaryKey() -> Salida a cenTiposcvExtendsMapper para obtener el campo descripcion de la categoría curricular");

				GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
				genRecursosCatalogosKey.setIdrecurso(cenTiposcv.getDescripcion());
				genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());

				LOGGER.info(
						"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Entrada a genRecursosCatalogosExtendsMapper para obtener la descripcion de la categoría curricular");
				GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosExtendsMapper
						.selectByPrimaryKey(genRecursosCatalogosKey);
				LOGGER.info(
						"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Salida a genRecursosCatalogosExtendsMapper para obtener la descripcion de la categoría curricular");

				solModifDatosCurricularesItem.setCategoriaCurricular(genRecursosCatalogos.getDescripcion());

				// Rellenamos el resto de datos que podemos rellenar con lo obtenido en la
				// consulta de cenSolicitudmodificacioncv
				solModifDatosCurricularesItem.setDescripcion(cenSolicitudmodificacioncv.getDescripcion());
				solModifDatosCurricularesItem
						.setFechaDesde(formatter.format(cenSolicitudmodificacioncv.getFechainicio()));
				solModifDatosCurricularesItem.setFechaHasta(formatter.format(cenSolicitudmodificacioncv.getFechafin()));

				// Tratamiento necesario para obtener el tipo curricular
				LOGGER.info(
						"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Entrada a cenTiposcvExtendsMapper para obtener el campo descripcion del tipo curricular");

				CenTiposcvsubtipo1Key cenTiposcvsubtipo1Key = new CenTiposcvsubtipo1Key();
				cenTiposcvsubtipo1Key.setIdinstitucion(idInstitucion);
				cenTiposcvsubtipo1Key.setIdtipocv(cenSolicitudmodificacioncv.getIdtipocv());
				cenTiposcvsubtipo1Key.setIdtipocvsubtipo1(cenSolicitudmodificacioncv.getIdtipocvsubtipo1());

				CenTiposcvsubtipo1 cenTiposcvsubtipo1 = cenTiposCVSubtipo1ExtendsMapper
						.selectByPrimaryKey(cenTiposcvsubtipo1Key);

				LOGGER.info(
						"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Salida a cenTiposcvExtendsMapper para obtener el campo descripcion del tipo curricular");

				if (cenTiposcvsubtipo1 != null) {
					LOGGER.info(
							"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Entrada a genRecursosCatalogosExtendsMapper para obtener la descripcion del tipo curricular");
					GenRecursosCatalogosKey genRecursosCatalogosKeyTC = new GenRecursosCatalogosKey();
					genRecursosCatalogosKeyTC.setIdrecurso(cenTiposcvsubtipo1.getDescripcion());
					genRecursosCatalogosKeyTC.setIdlenguaje(usuario.getIdlenguaje());

					GenRecursosCatalogos genRecursosCatalogosTC = genRecursosCatalogosExtendsMapper
							.selectByPrimaryKey(genRecursosCatalogosKeyTC);
					LOGGER.info(
							"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Salida a genRecursosCatalogosExtendsMapper para obtener la descripcion del tipo curricular");

					solModifDatosCurricularesItem.setTipoCurricular(genRecursosCatalogosTC.getDescripcion());
				}

				// Tratamiento necesario para obtener el subtipo curricular
				LOGGER.info(
						"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Entrada a cenTiposcvExtendsMapper para obtener el campo descripcion del subtipo curricular");

				CenTiposcvsubtipo2Key cenTiposcvsubtipo2Key = new CenTiposcvsubtipo2Key();
				cenTiposcvsubtipo2Key.setIdinstitucion(idInstitucion);
				cenTiposcvsubtipo2Key.setIdtipocv(cenSolicitudmodificacioncv.getIdtipocv());
				cenTiposcvsubtipo2Key.setIdtipocvsubtipo2(cenSolicitudmodificacioncv.getIdtipocvsubtipo1());

				CenTiposcvsubtipo2 cenTiposcvsubtipo2 = cenTiposCVSubtipo2ExtendsMapper
						.selectByPrimaryKey(cenTiposcvsubtipo2Key);

				LOGGER.info(
						"searchDatosCurricularesDetail() / cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey() -> Salida a cenTiposcvExtendsMapper para obtener el campo descripcion del subtipo curricular");

				if (cenTiposcvsubtipo2 != null) {
					LOGGER.info(
							"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Entrada a genRecursosCatalogosExtendsMapper para obtener la descripcion del subtipo curricular");
					GenRecursosCatalogosKey genRecursosCatalogosKeySubt = new GenRecursosCatalogosKey();
					genRecursosCatalogosKeySubt.setIdrecurso(cenTiposcvsubtipo2.getDescripcion());
					genRecursosCatalogosKeySubt.setIdlenguaje(usuario.getIdlenguaje());

					GenRecursosCatalogos genRecursosCatalogosSubt = genRecursosCatalogosExtendsMapper
							.selectByPrimaryKey(genRecursosCatalogosKeySubt);
					LOGGER.info(
							"searchDatosCurricularesDetail() / genRecursosCatalogosExtendsMapper.selectByPrimaryKey() -> Salida a genRecursosCatalogosExtendsMapper para obtener la descripcion del subtipo curricular");

					solModifDatosCurricularesItem.setSubtiposCurriculares(genRecursosCatalogosSubt.getDescripcion());
				}

			}
		}

		LOGGER.info(
				"searchSolModifDatosCurricularesDetail() -> Salida del servicio para recuperar los datos de la solicitud de datos curriculares");

		return solModifDatosCurricularesItem;
	}

}
