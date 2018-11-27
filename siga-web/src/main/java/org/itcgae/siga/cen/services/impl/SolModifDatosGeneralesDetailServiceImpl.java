package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.cen.services.ISolModifDatosGeneralesDetailService;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolModifDatosGeneralesDetailServiceImpl implements ISolModifDatosGeneralesDetailService {

	private Logger LOGGER = Logger.getLogger(SolModifDatosGeneralesDetailServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenSolicitmodifdatosbasicosExtendsMapper cenSolicitModifDatosBasicosExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private AdmLenguajesMapper admLenguajesMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public StringDTO searchDatosGeneralesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info("searchDatosGeneralesDetail() -> Entrada al servicio para recuperar los datos generales");

		StringDTO rdo = new StringDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		LOGGER.info(
				"searchDatosGeneralesDetail() / cenColegiadoExtendsMapper.selectByPrimaryKey() -> Entrada a cenColegiadoExtendsMapper");

		CenColegiadoKey cenColegiadoKey = new CenColegiadoKey();
		cenColegiadoKey.setIdinstitucion(idInstitucion);
		cenColegiadoKey.setIdpersona(Long.valueOf(solModificacionItem.getIdPersona()));
		CenColegiado cenColegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(cenColegiadoKey);

		LOGGER.info(
				"searchDatosGeneralesDetail() / cenColegiadoExtendsMapper.selectByPrimaryKey() -> Salida a cenColegiadoExtendsMapper");

		if (cenColegiado != null) {
			// Aquí hay que traer el idioma la descripción
			rdo.setValor("");
		}

		LOGGER.info("searchDatosGeneralesDetail() -> Salida al servicio para recuperar los datos generales");

		return rdo;
	}

	@Override
	public StringDTO searchSolModifDatosGeneralesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info("searchSolModifDatosGeneralesDetail() -> Entrada al servicio para recuperar los datos generales");

		StringDTO rdo = new StringDTO();

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

				LOGGER.info(
						"searchSolModifDatosGeneralesDetail() / cenSolicitModifDatosBasicosExtendsMapper.selectByPrimaryKey() -> Entrada a cenSolicitModifDatosBasicosExtendsMapper");

				CenSolicitmodifdatosbasicos cenSolicitmodifdatosbasicos = cenSolicitModifDatosBasicosExtendsMapper
						.selectByPrimaryKey(Short.valueOf(solModificacionItem.getIdSolicitud()));

				LOGGER.info(
						"searchSolModifDatosGeneralesDetail() / cenSolicitModifDatosBasicosExtendsMapper.selectByPrimaryKey() -> Salida a cenSolicitModifDatosBasicosExtendsMapper");

				if (cenSolicitmodifdatosbasicos != null) {
					AdmLenguajes admLenguajes = admLenguajesMapper
							.selectByPrimaryKey(cenSolicitmodifdatosbasicos.getIdlenguaje());

					LOGGER.info(
							"searchDatosCurricularesDetail() / searchSolModifDatosGeneralesDetail.selectByPrimaryKey() -> Entrada a genRecursosCatalogosExtendsMapper para obtener la descripcion del idioma");
					GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
					genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
					genRecursosCatalogosKey.setIdrecurso(admLenguajes.getDescripcion());

					GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosExtendsMapper
							.selectByPrimaryKey(genRecursosCatalogosKey);

					LOGGER.info(
							"searchDatosCurricularesDetail() / searchSolModifDatosGeneralesDetail.selectByPrimaryKey() -> Salida a genRecursosCatalogosExtendsMapper para obtener la descripcion del idioma");

					rdo.setValor(genRecursosCatalogos.getDescripcion());
				}
			}

		}
		LOGGER.info("searchSolModifDatosGeneralesDetail() -> Salida al servicio para recuperar los datos generales");

		return rdo;

	}
}
