package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ComboSubtipoCVItem;
import org.itcgae.siga.DTOs.cen.ComboSubtiposCVDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ISubtipoCurricularService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubtipoCurricularServiceImpl implements ISubtipoCurricularService {

	private Logger LOGGER = Logger.getLogger(SubtipoCurricularServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	

	@Override
	public SubtipoCurricularDTO searchSubtipoCurricular(int numPagina, SubtipoCurricularItem subtipoCurricularItem,
			HttpServletRequest request) {
		LOGGER.info(
				"searchSubtipoCurricular() -> Entrada al servicio para la búsqueda por filtro de categoría curricular");

		List<SubtipoCurricularItem> subtipoCurricularItems = new ArrayList<SubtipoCurricularItem>();
		SubtipoCurricularDTO subtipoCurricularDTO = new SubtipoCurricularDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"searchSubtipoCurricular() / cenTiposCVSubtipo2ExtendsMapper.searchSubtipoCurricular() -> Entrada a cenNocolegiadoExtendsMapper para busqueda de personas colegiadas por filtro");
				subtipoCurricularItems = cenTiposCVSubtipo2ExtendsMapper.searchSubtipoCurricular(subtipoCurricularItem,
						idLenguaje, String.valueOf(idInstitucion));
				LOGGER.info(
						"searchSubtipoCurricular() / cenTiposCVSubtipo2ExtendsMapper.searchSubtipoCurricular() -> Salida de cenNocolegiadoExtendsMapper para busqueda de personas no colegiadas por filtro");

				subtipoCurricularDTO.setSubtipoCurricularItems(subtipoCurricularItems);
			} else {
				LOGGER.warn(
						"searchSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchSubtipoCurricular() -> idInstitucion del token nula");
		}

		LOGGER.info("search() -> Salida del servicio para la búsqueda por filtro de categoría curricular");
		return subtipoCurricularDTO;
	}
	
	@Override
	public InsertResponseDTO createSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem,
			HttpServletRequest request) throws Exception {
		LOGGER.info("createTipoCurricular() -> Entrada al servicio crear un registro subtipo curricular");
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
						"createTipoCurricular() / cenTiposCVSubtipo2ExtendsMapper.insert() -> Entrada a cenTiposCVSubtipo1ExtendsMapper para insertar un registro");

				// Vamos a insertar el registro en cenTiposCVSubtipo1
				CenTiposcvsubtipo2 record = new CenTiposcvsubtipo2();
				record.setCodigoext(subtipoCurricularItem.getCodigoExterno());

				// Consultamos para ver si la descripción existe en genRecursosCatalogos
				GenRecursosCatalogosExample example = new GenRecursosCatalogosExample();
				example.createCriteria().andNombretablaEqualTo("CEN_TIPOSCVSUBTIPO2")
						.andCampotablaEqualTo("DESCRIPCION")
						.andDescripcionEqualTo(subtipoCurricularItem.getDescripcion())
						.andIdinstitucionEqualTo(idInstitucion).andIdlenguajeEqualTo(idLenguaje);
				List<GenRecursosCatalogos> genRecursosCatalogos = genRecursosCatalogosExtendsMapper
						.selectByExample(example);

				if (null != genRecursosCatalogos && genRecursosCatalogos.size() > 0) {
					record.setDescripcion(genRecursosCatalogos.get(0).getIdrecurso());
				} else {
					// No está la descripción, procedemos a insertarla
					GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

					genRecursosCatalogo.setCampotabla("DESCRIPCION");
					genRecursosCatalogo.setDescripcion(subtipoCurricularItem.getDescripcion());
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

					genRecursosCatalogo.setIdrecursoalias("cen_tiposcvsubtipo2.descripcion." + idInstitucion + "."
							+ genRecursosCatalogo.getIdrecurso());

					genRecursosCatalogo.setNombretabla("CEN_TIPOSCVSUBTIPO2");
					genRecursosCatalogo.setUsumodificacion(usuario.getUsumodificacion());

					if (genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo) == 1) {
						record.setDescripcion(genRecursosCatalogo.getIdrecurso());
					}
					insertarRestoIdiomas(genRecursosCatalogo);
					
				}

				record.setFechamodificacion(new Date());
				record.setIdinstitucion(idInstitucion);
				record.setIdtipocv(Short.valueOf(subtipoCurricularItem.getTipoCategoriaCurricular()));

				NewIdDTO idTipoCvSubtipo1 = cenTiposCVSubtipo2ExtendsMapper.getMaxIdCvSubtipo2(
						String.valueOf(idInstitucion), subtipoCurricularItem.getTipoCategoriaCurricular());

				if (idTipoCvSubtipo1 == null) {
					record.setIdtipocvsubtipo2(new Short("1"));
				} else {
					int newIdTipoCvSubtipo1 = Integer.parseInt(idTipoCvSubtipo1.getNewId()) + 1;
					record.setIdtipocvsubtipo2((short) newIdTipoCvSubtipo1);
				}

				record.setUsumodificacion(usuario.getUsumodificacion());

				if (cenTiposCVSubtipo2ExtendsMapper.insert(record) == 1) {
					insertResponseDTO.setStatus(SigaConstants.OK);
				} else {
					insertResponseDTO.setStatus(SigaConstants.KO);
					org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
					error.setMessage("general.message.error.realiza.accion");
					insertResponseDTO.setError(error);
				}
				LOGGER.info(
						"createTipoCurricular() / cenTiposCVSubtipo2ExtendsMapper.insert() -> Salida de cenTiposCVSubtipo2ExtendsMapper para para insertar un registro");

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
	public UpdateResponseDTO updateSubtipoCurricular(SubtipoCurricularDTO subtipoCurricularDTO,
			HttpServletRequest request) {
		LOGGER.info("updateSubtipoCurricular() -> Entrada al servicio para actualizar información");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		CenTiposcvsubtipo2 record = new CenTiposcvsubtipo2();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				for (SubtipoCurricularItem subtipoCurricularItem : subtipoCurricularDTO.getSubtipoCurricularItems()) {

					// consultamos si existe la descripción
					GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
					genRecursosCatalogosExample.createCriteria()
							.andDescripcionEqualTo(subtipoCurricularItem.getDescripcion())
							.andIdinstitucionEqualTo(idInstitucion).andIdlenguajeEqualTo(usuario.getIdlenguaje());
					List<GenRecursosCatalogos> genRecursosCatalogos = genRecursosCatalogosExtendsMapper
							.selectByExample(genRecursosCatalogosExample);

					if (null != genRecursosCatalogos && !genRecursosCatalogos.isEmpty()) {
						record.setDescripcion(genRecursosCatalogos.get(0).getIdrecurso());
					} else {
						GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

						genRecursosCatalogo.setCampotabla("DESCRIPCION");
						genRecursosCatalogo.setDescripcion(subtipoCurricularItem.getDescripcion());
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

						genRecursosCatalogo.setIdrecursoalias("cen_tiposcvsubtipo2.descripcion." + idInstitucion + "."
								+ genRecursosCatalogo.getIdrecurso());

						genRecursosCatalogo.setNombretabla("CEN_TIPOSCVSUBTIPO2");
						genRecursosCatalogo.setUsumodificacion(usuario.getUsumodificacion());

						if (genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo) == 1) {
							record.setDescripcion(genRecursosCatalogo.getIdrecurso());
						}
					}

					if (subtipoCurricularItem.getIdTipoCV() != null
							&& subtipoCurricularItem.getIdTipoCvSubtipo2() != null
							&& usuario.getIdinstitucion() != null) {
						record.setCodigoext(subtipoCurricularItem.getCodigoExterno());
						record.setFechamodificacion(new Date());
						record.setIdinstitucion(idInstitucion);
						record.setIdtipocv(Short.valueOf(subtipoCurricularItem.getIdTipoCV()));
						record.setIdtipocvsubtipo2(Short.valueOf(subtipoCurricularItem.getIdTipoCvSubtipo2()));
						record.setUsumodificacion(usuario.getUsumodificacion());

						if (cenTiposCVSubtipo2ExtendsMapper.updateByPrimaryKey(record) == 1) {
							updateResponseDTO.setStatus(SigaConstants.OK);
						} else {
							updateResponseDTO.setStatus(SigaConstants.KO);
						}
					}
				}
			}
		}

		LOGGER.info("updateSubtipoCurricular() -> Salida del servicio para actualizar información");
		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO deleteSubtipoCurricular(SubtipoCurricularDTO subtipoCurricularDTO,
			HttpServletRequest request) {
		LOGGER.info("deleteSubtipoCurricular() -> Entrada al servicio para eliminar subtipo curricular");

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
					"deleteSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// información a modificar
				CenTiposcvsubtipo2 cenTiposcvsubtipo2 = new CenTiposcvsubtipo2();
				cenTiposcvsubtipo2.setFechamodificacion(new Date());
				cenTiposcvsubtipo2.setUsumodificacion(usuario.getIdusuario());
				cenTiposcvsubtipo2.setFechaBaja(new Date());

				// filtrado para sentencia sql
				List<Short> idTipoCvDelete = new ArrayList<Short>();
				List<Short> idTipoCvSuptipo2Delete = new ArrayList<Short>();
				for (SubtipoCurricularItem subtipoCurricularItem : subtipoCurricularDTO.getSubtipoCurricularItems()) {
					idTipoCvDelete.add(Short.valueOf(subtipoCurricularItem.getIdTipoCV()));
					idTipoCvSuptipo2Delete.add(Short.valueOf(subtipoCurricularItem.getIdTipoCvSubtipo2()));
				}

				CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
				cenTiposcvsubtipo2Example.createCriteria().andIdtipocvIn(idTipoCvDelete)
						.andIdtipocvsubtipo2In(idTipoCvSuptipo2Delete).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info(
						"deleteSubtipoCurricular() / subtipoCurricularItem.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para eliminar no colegiado");
				response = cenTiposCVSubtipo2ExtendsMapper.updateByExampleSelective(cenTiposcvsubtipo2,
						cenTiposcvsubtipo2Example);
				LOGGER.info(
						"deleteSubtipoCurricular() / subtipoCurricularItem.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para eliminar no colegiado");

			} else {
				LOGGER.warn(
						"deleteSubtipoCurricular() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("deleteSubtipoCurricular() -> idInstitucion del token nula");
		}

		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("deleteSubtipoCurricular() -> OK. Delete para tipo curricular realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("deleteSubtipoCurricular() -> KO. Delete para tipo curricular NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteSubtipoCurricular() -> Salida del servicio para eliminar subtipo curricular");
		return deleteResponseDTO;
	}

	@Override
	public SubtipoCurricularDTO getHistory(SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request) {
		LOGGER.info("getHistory() -> Entrada al servicio para la búsqueda");

		List<SubtipoCurricularItem> subtipoCurricularItems = new ArrayList<SubtipoCurricularItem>();
		SubtipoCurricularDTO subtipoCurricularDTO = new SubtipoCurricularDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getHistory() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getHistory() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				LOGGER.info(
						"getHistory() / cenTiposCVSubtipo2ExtendsMapper.getHistory() -> Entrada a cenTiposCVSubtipo2ExtendsMapper para busqueda");
				
				subtipoCurricularItems = cenTiposCVSubtipo2ExtendsMapper.getHistory(subtipoCurricularItem, String.valueOf(idInstitucion), idLenguaje);
				LOGGER.info(
						"getHistory() / cenTiposCVSubtipo2ExtendsMapper.getHistory() -> Salida de cenTiposCVSubtipo2ExtendsMapper para busqueda");

				subtipoCurricularDTO.setSubtipoCurricularItems(subtipoCurricularItems);
			} else {
				LOGGER.warn(
						"getHistory() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getHistory() -> idInstitucion del token nula");
		}

		LOGGER.info("getHistory() -> Salida del servicio para la búsqueda");
		return subtipoCurricularDTO;
	}
	
	@Override
	public ComboSubtiposCVDTO getCurricularSubtypeCombo (String idTipoCV, boolean historico, HttpServletRequest request) {
		
		LOGGER.info("getCurricularSubtypeCombo() -> Entrada al servicio para obtener los tipos curriculares");
		
		ComboSubtiposCVDTO comboDTO = new ComboSubtiposCVDTO();
		List<ComboSubtipoCVItem> comboItems = new ArrayList<ComboSubtipoCVItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			LOGGER.info(
					"getCurricularSubtypeCombo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"getCurricularSubtypeCombo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				LOGGER.info(
						"getCurricularSubtypeCombo() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Entrada a cenTiposcvExtendsMapper para obtener los diferentes tipos curriculares");
				
				comboItems = cenTiposCVSubtipo2ExtendsMapper.searchCurricularSubtypeCombo(idTipoCV, historico, usuario.getIdlenguaje(), idInstitucion.toString());
				
				LOGGER.info(
						"getCurricularSubtypeCombo() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Salida de cenTiposcvExtendsMapper para obtener los diferentes tipos curriculares");
				
			}
		}
		
		comboDTO.setComboSubtipoCVItem(comboItems);
		
		LOGGER.info("getCurricularSubtypeCombo() -> Salida del servicio para obtener los tipos curriculares");
		
		return comboDTO;
	}
}
