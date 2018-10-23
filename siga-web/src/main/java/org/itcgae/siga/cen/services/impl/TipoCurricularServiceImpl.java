package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ITipoCurricularService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Key;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoCurricularServiceImpl implements ITipoCurricularService {

	private Logger LOGGER = Logger.getLogger(TipoCurricularServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;

	@Autowired
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public ComboDTO getComboCategoriaCurricular(HttpServletRequest request) {
		LOGGER.info("getTipoSolicitud() -> Entrada al servicio para cargar la categoría curricular");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getComboCategoriaCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getComboCategoriaCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getComboCategoriaCurricular() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				List<ComboItem> comboItems = cenTiposcvExtendsMapper.selectCategoriaCV(usuario.getIdlenguaje());

				if (comboItems != null && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("getComboCategoriaCurricular() -> Salida del servicio para obtener la categoría curricular");
		return combo;
	}

	@Override
	public TipoCurricularDTO search(int numPagina, TipoCurricularItem tipoCurricularItem, HttpServletRequest request) {
		LOGGER.info("search() -> Entrada al servicio para la búsqueda por filtro de categoría curricular");

		List<TipoCurricularItem> tipoCurricularItems = new ArrayList<TipoCurricularItem>();
		TipoCurricularDTO tipoCurricular = new TipoCurricularDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"search() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"search() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"search() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Entrada a cenNocolegiadoExtendsMapper para busqueda de personas colegiadas por filtro");
				tipoCurricularItems = cenTiposCVSubtipo1ExtendsMapper.search(tipoCurricularItem, idLenguaje,
						String.valueOf(idInstitucion));
				LOGGER.info(
						"search() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para busqueda de personas no colegiadas por filtro");

				tipoCurricular.setTipoCurricularItems(tipoCurricularItems);
				;
			} else {
				LOGGER.warn(
						"search() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("search() -> idInstitucion del token nula");
		}

		LOGGER.info("search() -> Salida del servicio para la búsqueda por filtro de categoría curricular");
		return tipoCurricular;
	}

	@Override
	public InsertResponseDTO createTipoCurricular(TipoCurricularItem tipoCurricularItem, HttpServletRequest request)
			throws Exception {
		LOGGER.info(
				"createTipoCurricular() -> Entrada al servicio para actualizar información general de una persona jurídica");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idLenguaje = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"createTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"createTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"createTipoCurricular() / cenTiposCVSubtipo1ExtendsMapper.insert() -> Entrada a cenTiposCVSubtipo1ExtendsMapper para insertar un registro");

				// Vamos a insertar el registro en cenTiposCVSubtipo1
				CenTiposcvsubtipo1 record = new CenTiposcvsubtipo1();
				record.setCodigoext(tipoCurricularItem.getCodigoExterno());

				// Consultamos para ver si la descripción existe en genRecursosCatalogos
				GenRecursosCatalogosExample example = new GenRecursosCatalogosExample();
				example.createCriteria().andNombretablaEqualTo("CEN_TIPOSCVSUBTIPO1")
						.andCampotablaEqualTo("DESCRIPCION").andDescripcionEqualTo(tipoCurricularItem.getDescripcion())
						.andIdinstitucionEqualTo(idInstitucion).andIdlenguajeEqualTo(idLenguaje);
				List<GenRecursosCatalogos> genRecursosCatalogos = genRecursosCatalogosExtendsMapper
						.selectByExample(example);

				if (null != genRecursosCatalogos && genRecursosCatalogos.size() > 0) {
					record.setDescripcion(genRecursosCatalogos.get(0).getIdrecurso());
				} else {
					// No está la descripción, procedemos a insertarla
					GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

					genRecursosCatalogo.setCampotabla("DESCRIPCION");
					genRecursosCatalogo.setDescripcion(tipoCurricularItem.getDescripcion());
					genRecursosCatalogo.setFechamodificacion(new Date());
					genRecursosCatalogo.setIdinstitucion(idInstitucion);
					genRecursosCatalogo.setIdlenguaje(idLenguaje);

					NewIdDTO idRecursoBD = genRecursosCatalogosExtendsMapper
							.getMaxIdRecursoCatalogo(String.valueOf(idInstitucion), idLenguaje);
					if (idRecursoBD == null) {
						genRecursosCatalogo.setIdrecurso("1");
					} else {
						long idRecurso = Long.parseLong(idRecursoBD.getNewId()) + 1;
						genRecursosCatalogo.setIdrecurso(String.valueOf(idRecurso));
					}

					genRecursosCatalogo.setIdrecursoalias("cen_tiposcvsubtipo1.descripcion." + idInstitucion + "."
							+ genRecursosCatalogo.getIdrecurso());

					genRecursosCatalogo.setNombretabla("CEN_TIPOSCVSUBTIPO1");
					genRecursosCatalogo.setUsumodificacion(usuario.getUsumodificacion());

					if (genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo) == 1) {
						record.setDescripcion(genRecursosCatalogo.getIdrecurso());
					}
				}

				record.setFechamodificacion(new Date());
				record.setIdinstitucion(idInstitucion);
				record.setIdtipocv(Short.valueOf(tipoCurricularItem.getTipoCategoriaCurricular()));

				NewIdDTO idTipoCvSubtipo1 = cenTiposCVSubtipo1ExtendsMapper.getMaxIdCvSubtipo1(
						String.valueOf(idInstitucion), tipoCurricularItem.getTipoCategoriaCurricular());

				if (idTipoCvSubtipo1 == null) {
					record.setIdtipocvsubtipo1(new Short("1"));
				} else {
					int newIdTipoCvSubtipo1 = Integer.parseInt(idTipoCvSubtipo1.getNewId()) + 1;
					record.setIdtipocvsubtipo1((short) newIdTipoCvSubtipo1);
				}

				record.setUsumodificacion(usuario.getUsumodificacion());

				if (cenTiposCVSubtipo1ExtendsMapper.insert(record) == 1) {
					insertResponseDTO.setStatus(SigaConstants.OK);
				} else {
					insertResponseDTO.setStatus(SigaConstants.KO);
					org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
					error.setMessage("general.message.error.realiza.accion");
					insertResponseDTO.setError(error);
				}
				LOGGER.info(
						"createTipoCurricular() / cenTiposCVSubtipo1ExtendsMapper.insert() -> Salida de cenTiposCVSubtipo1ExtendsMapper para para insertar un registro");

			} else {
				LOGGER.warn(
						"createTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("createTipoCurricular() -> idInstitucion del token nula");
		}

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateTipoCurricular(TipoCurricularDTO tipoCurricularDTO, HttpServletRequest request) {
		LOGGER.info("updateTipoCurricular() -> Entrada al servicio para actualizar información");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				for (TipoCurricularItem tipoCurricularItem: tipoCurricularDTO.getTipoCurricularItems()) {
					
				  // buscar los datos en subtipocv
				}
			}
		}
		return null;
	}

}
