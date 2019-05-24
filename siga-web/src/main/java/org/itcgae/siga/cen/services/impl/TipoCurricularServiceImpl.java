package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ComboTipoCVItem;
import org.itcgae.siga.DTOs.cen.ComboTiposCVDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ITipoCurricularService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
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
//					ComboItem element = new ComboItem();
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("getComboCategoriaCurricular() -> Salida del servicio para obtener la categoría curricular");
		return combo;
	}
	
	@Override
	public TipoCurricularDTO searchTipoCurricular(int numPagina, TipoCurricularItem tipoCurricularItem, HttpServletRequest request) {
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
				tipoCurricularItems = cenTiposCVSubtipo1ExtendsMapper.searchTipoCurricular(tipoCurricularItem, idLenguaje,
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
					insertarRestoIdiomas(genRecursosCatalogo);
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
					Error error = new Error();
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

	
	private void insertarRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {
		// TODO Auto-generated method stub
		String idLenguaje = genRecursosCatalogo.getIdlenguaje();
		String descripcion = genRecursosCatalogo.getDescripcion();
		switch (idLenguaje) {
		case "1":
			genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
			genRecursosCatalogo.setIdlenguaje("2");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

			genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
			genRecursosCatalogo.setIdlenguaje("3");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			
			genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
			genRecursosCatalogo.setIdlenguaje("4");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			break;
		case "2":
			genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
			genRecursosCatalogo.setIdlenguaje("1");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

			genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
			genRecursosCatalogo.setIdlenguaje("3");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			
			genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
			genRecursosCatalogo.setIdlenguaje("4");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			break;
		case "3":
			genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
			genRecursosCatalogo.setIdlenguaje("2");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

			genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
			genRecursosCatalogo.setIdlenguaje("1");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			
			genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
			genRecursosCatalogo.setIdlenguaje("4");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			break;
		case "4":
			genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
			genRecursosCatalogo.setIdlenguaje("2");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

			genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
			genRecursosCatalogo.setIdlenguaje("3");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			
			genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
			genRecursosCatalogo.setIdlenguaje("1");
			genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
			break;
	
		}
		
		
		
	}
	
	
	@Override
	public UpdateResponseDTO updateTipoCurricular(TipoCurricularDTO tipoCurricularDTO, HttpServletRequest request) {
		LOGGER.info("updateTipoCurricular() -> Entrada al servicio para actualizar información");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		CenTiposcvsubtipo1 record = new CenTiposcvsubtipo1();

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

				for (TipoCurricularItem tipoCurricularItem : tipoCurricularDTO.getTipoCurricularItems()) {

					// consultamos si existe la descripción
					GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
					genRecursosCatalogosExample.createCriteria()
							.andDescripcionEqualTo(tipoCurricularItem.getDescripcion())
							.andIdinstitucionEqualTo(idInstitucion).andIdlenguajeEqualTo(usuario.getIdlenguaje());
					List<GenRecursosCatalogos> genRecursosCatalogos = genRecursosCatalogosExtendsMapper
							.selectByExample(genRecursosCatalogosExample);

					if (null != genRecursosCatalogos && !genRecursosCatalogos.isEmpty()) {
						record.setDescripcion(genRecursosCatalogos.get(0).getIdrecurso());
					} else {
						GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

						genRecursosCatalogo.setCampotabla("DESCRIPCION");
						genRecursosCatalogo.setDescripcion(tipoCurricularItem.getDescripcion());
						genRecursosCatalogo.setFechamodificacion(new Date());
						genRecursosCatalogo.setIdinstitucion(idInstitucion);
						genRecursosCatalogo.setIdlenguaje(usuario.getIdlenguaje());

						NewIdDTO idRecursoBD = genRecursosCatalogosExtendsMapper
								.getMaxIdRecursoCatalogo(String.valueOf(idInstitucion), usuario.getIdlenguaje());
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

					if (tipoCurricularItem.getIdTipoCV() != null && tipoCurricularItem.getIdTipoCvSubtipo1() != null
							&& usuario.getIdinstitucion() != null) {
						record.setCodigoext(tipoCurricularItem.getCodigoExterno());
						record.setFechamodificacion(new Date());
						record.setIdinstitucion(idInstitucion);
						record.setIdtipocv(Short.valueOf(tipoCurricularItem.getIdTipoCV()));
						record.setIdtipocvsubtipo1(Short.valueOf(tipoCurricularItem.getIdTipoCvSubtipo1()));
						record.setUsumodificacion(usuario.getUsumodificacion());

						if (cenTiposCVSubtipo1ExtendsMapper.updateByPrimaryKey(record) == 1) {
							updateResponseDTO.setStatus(SigaConstants.OK);
						} else {
							updateResponseDTO.setStatus(SigaConstants.KO);
						}
					}
				}
				
			}
		}

		LOGGER.info("updateTipoCurricular() -> Salida del servicio para actualizar información");
		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO deleteTipoCurricular(TipoCurricularDTO tipoCurricularDTO, HttpServletRequest request) {
		LOGGER.info("deleteTipoCurricular() -> Entrada al servicio para eliminar tipo curricular");
		
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"deleteTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// información a modificar
				CenTiposcvsubtipo1 cenTiposcvsubtipo1 = new CenTiposcvsubtipo1();
				cenTiposcvsubtipo1.setFechamodificacion(new Date());
				cenTiposcvsubtipo1.setUsumodificacion(usuario.getIdusuario());
				cenTiposcvsubtipo1.setFechaBaja(new Date());

				// filtrado para sentencia sql
				List<Short> idTipoCvDelete = new ArrayList<Short>();
				List<Short> idTipoCvSuptipo1Delete = new ArrayList<Short>();
				for (TipoCurricularItem tipoCurricularItem : tipoCurricularDTO.getTipoCurricularItems()) {
					idTipoCvDelete.add(Short.valueOf(tipoCurricularItem.getIdTipoCV()));
					idTipoCvSuptipo1Delete.add(Short.valueOf(tipoCurricularItem.getIdTipoCvSubtipo1()));
				}

				CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
				cenTiposcvsubtipo1Example.createCriteria().andIdtipocvIn(idTipoCvDelete).andIdtipocvsubtipo1In(idTipoCvSuptipo1Delete).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info(
						"deleteTipoCurricular() / cenTiposcvExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para eliminar no colegiado");
				response = cenTiposCVSubtipo1ExtendsMapper.updateByExampleSelective(cenTiposcvsubtipo1, cenTiposcvsubtipo1Example);
				LOGGER.info(
						"deleteTipoCurricular() / cenTiposcvExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para eliminar no colegiado");

			} else {
				LOGGER.warn(
						"deleteTipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("deleteTipoCurricular() -> idInstitucion del token nula");
		}

		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("deleteTipoCurricular() -> OK. Delete para tipo curricular realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("deleteTipoCurricular() -> KO. Delete para tipo curricular NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteTipoCurricular() -> Salida del servicio para eliminar tipo curricular");
		return deleteResponseDTO;
	}

	@Override
	public TipoCurricularDTO getHistory(TipoCurricularItem tipoCurricularItem, HttpServletRequest request) {
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
						"search() / cenTiposCVSubtipo1ExtendsMapper.getHistory() -> Entrada a cenTiposCVSubtipo1ExtendsMapper para busqueda");
				
				tipoCurricularItems = cenTiposCVSubtipo1ExtendsMapper.getHistory(tipoCurricularItem, String.valueOf(idInstitucion), idLenguaje);
				LOGGER.info(
						"search() / cenTiposCVSubtipo1ExtendsMapper.getHistory() -> Salida de cenTiposCVSubtipo1ExtendsMapper para busqueda");

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
	public ComboTiposCVDTO getCurricularTypeCombo (String idTipoCV, boolean historico, HttpServletRequest request) {
		
		LOGGER.info("getCurricularTypeCombo() -> Entrada al servicio para obtener los tipos curriculares");
		
		ComboTiposCVDTO comboDTO = new ComboTiposCVDTO();
		List<ComboTipoCVItem> comboItems = new ArrayList<ComboTipoCVItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCurricularTypeCombo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCurricularTypeCombo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				LOGGER.info(
						"getCurricularTypeCombo() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Entrada a cenTiposcvExtendsMapper para obtener los diferentes tipos curriculares");
				
				comboItems = cenTiposCVSubtipo1ExtendsMapper.searchCurricularTypeCombo(idTipoCV, historico, usuario.getIdlenguaje(), idInstitucion.toString());
				
				LOGGER.info(
						"getCurricularTypeCombo() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Salida de cenTiposcvExtendsMapper para obtener los diferentes tipos curriculares");
				
			}
		}
		
		comboDTO.setComboTipoCVItem(comboItems);
		
		LOGGER.info("getCurricularTypeCombo() -> Salida del servicio para obtener los tipos curriculares");
		
		return comboDTO;
	}

}
