package org.itcgae.siga.scs.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.AreasDTO;
import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsArea;
import org.itcgae.siga.db.entities.ScsAreaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.ScsAreasMateriasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.ScsJurisdiccionExtendsMapper;
import org.itcgae.siga.scs.service.IFichaAreasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaAreasServiceImpl implements IFichaAreasService {

	private Logger LOGGER = Logger.getLogger(FichaAreasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsJurisdiccionExtendsMapper ScsJurisdiccionExtendsMapper;

	@Autowired
	private ScsAreasMateriasExtendsMapper scsAreasMateriasExtendsMapper;

	@Override
	public ComboDTO getPartidoJudicial(HttpServletRequest request) {
		LOGGER.info("getPartidoJudicial() -> Entrada al servicio para obtener combo partidos judiciales");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getPartidoJudicial() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getPartidoJudicial() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getPartidoJudicial() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");
	
				List<ComboItem> comboItems = ScsJurisdiccionExtendsMapper.getJurisdicciones(idInstitucion.toString(), dni);

				LOGGER.info(
						"getPartidoJudicial() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getPartidoJudicial() -> Salida del servicio para obtener combo jurisdicciones");
		return combo;
	}
	
	@Override
	public AreasDTO searchAreas(AreasItem areasItem, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener las zonas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AreasDTO areasDTO = new AreasDTO();
		List<AreasItem> areasItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchSubzonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchSubzonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
				areasItem.setidInstitucion(idInstitucion);
				areasItems = scsAreasMateriasExtendsMapper.searchAreas(areasItem);

				LOGGER.info(
						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

//				for (AreasItem area : areasItems) {
//
//					if (area.getIdpartido() != null) {
//
//						String[] partidosJudiciales = zona.getIdpartido().split(";");
//						zona.setJurisdiccion(partidosJudiciales);
//
//					}
//				}

				if (areasItems != null) {
					areasDTO.setareasItems(areasItems);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener las zonas");
		return areasDTO;
	}
	
	
	@Override
	public UpdateResponseDTO deleteAreas(AreasDTO areasDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar zonas de un grupo zona");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (AreasItem areasItem : areasDTO.getareasItems()) {

						// Eliminamos asociaciones partidos judiciales con zona

						ScsArea area = new ScsArea();
						area.setContenido(areasItem.getContenido());
						if(areasItem.getFechabaja() == null) {
							area.setFechabaja(new Date());
						}else {
							area.setFechabaja(null);
						}
						area.setFechamodificacion(new Date());
						area.setIdarea(Short.parseShort(areasItem.getIdArea()));
						area.setIdinstitucion(areasItem.getidInstitucion());
						area.setNombre(areasItem.getNombreArea());
						area.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"deleteAreas() / scsAreasMateriasExtendsMapper.deleteByExample() -> Entrada a scsAreasMateriasExtendsMapper para eliminar las Areas seleccionadas");

						response = scsAreasMateriasExtendsMapper.updateByPrimaryKey(area);

						LOGGER.info(
								"deleteAreas() / scsAreasMateriasExtendsMapper.deleteByExample() -> Salida de scsAreasMateriasExtendsMapper para eliminar las Areas seleccionadas");

					}
					
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("No se han eliminado las zonas");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Se han actualizado las zonas correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar zonas de un grupo zona");

		return updateResponseDTO;
	}


//	@Override
//	public UpdateResponseDTO updateZones(ZonasDTO zonasDTO, HttpServletRequest request) {
//		LOGGER.info("updateZones() ->  Entrada al servicio para editar zonas");
//
//		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"updateZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"updateZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//				AdmUsuarios usuario = usuarios.get(0);
//
//				try {
//
//					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {
//
//						ScsSubzonaExample scsSubzonaExample = new ScsSubzonaExample();
//						scsSubzonaExample.createCriteria().andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
//								.andIdinstitucionEqualTo(idInstitucion)
//								.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()));
//
//						LOGGER.info(
//								"updateZones() / scsSubzonaExtendsMapper.selectByExample() -> Entrada a scsSubzonaExtendsMapper para buscar la subzona");
//
//						List<ScsSubzona> scsSubzonasList = scsSubzonaExtendsMapper.selectByExample(scsSubzonaExample);
//
//						LOGGER.info(
//								"updateZones() / scsSubzonaExtendsMapper.selectByExample() -> Salida a scsSubzonaExtendsMapper para buscar la subzona");
//
//						ScsSubzona subZona = scsSubzonasList.get(0);
//
//						subZona.setNombre(zonaItem.getDescripcionsubzona());
//						subZona.setFechamodificacion(new Date());
//						subZona.setUsumodificacion(usuario.getIdusuario().intValue());
//
//						response += scsSubzonaExtendsMapper.updateByPrimaryKey(subZona);
//
//						// MODIFICAR PARTIDOS
//						if (zonaItem.getPartidosJudiciales() != null && zonaItem.getPartidosJudiciales().length > 0) {
//
//							// Eliminamos el partido judicial que no se encuentre en la lista actual
//							ScsSubzonapartidoExample scsSubzonapartidoExample = new ScsSubzonapartidoExample();
//							scsSubzonapartidoExample.createCriteria()
//									.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
//									.andIdinstitucionEqualTo(idInstitucion)
//									.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona())).andFechabajaIsNull();
//
//							LOGGER.info(
//									"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Entrada a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");
//
//							List<ScsSubzonapartido> partidosAntiguosList = scsSubzonapartidoMapper
//									.selectByExample(scsSubzonapartidoExample);
//
//							LOGGER.info(
//									"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Salida a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");
//
//							List<ScsSubzonapartido> partidosJuridicosEliminar = new ArrayList<ScsSubzonapartido>();
//
//							// Si hay temas que estan dados de alta, comprobamos que se encuentra en la
//							// modificacion actual
//							if (!partidosAntiguosList.isEmpty()) {
//
//								for (ScsSubzonapartido partidosAsignadosAntiguos : partidosAntiguosList) {
//									boolean flag = false;
//
//									for (int i = 0; zonaItem.getPartidosJudiciales().length > i; i++) {
//
//										if (partidosAsignadosAntiguos.getIdpartido()
//												.equals(Long.valueOf(zonaItem.getPartidosJudiciales()[i].getValue()))) {
//											flag = true;
//											i = zonaItem.getPartidosJudiciales().length;
//										}
//									}
//
//									// Si no se encuentra en la lista actual la borramos
//									if (!flag) {
//										partidosJuridicosEliminar.add(partidosAsignadosAntiguos);
//									}
//								}
//
//								for (ScsSubzonapartido partidoBaja : partidosJuridicosEliminar) {
//
//									LOGGER.info(
//											"updateZones() / scsSubzonapartidoMapper.deleteByPrimaryKey() -> Entrada a scsSubzonapartidoMapper para eliminar un partido judicial");
//
//									response = scsSubzonapartidoMapper.deleteByPrimaryKey(partidoBaja);
//
//									LOGGER.info(
//											"updateZones() / scsSubzonapartidoMapper.deleteByPrimaryKey() -> Salida a scsSubzonapartidoMapper para eliminar un partido judicial");
//								}
//							}
//
//							// Añadimos partidos
//							for (ComboItem partidoAdd : zonaItem.getPartidosJudiciales()) {
//								// Para cada temas comprobamos si ya existe la relacion
//								// Eliminamos el partido judicial que no se encuentre en la lista actual
//								ScsSubzonapartidoExample example = new ScsSubzonapartidoExample();
//								example.createCriteria().andIdpartidoEqualTo(Long.valueOf(partidoAdd.getValue()))
//										.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
//										.andIdinstitucionEqualTo(idInstitucion)
//										.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()))
//										.andFechabajaIsNull();
//
//								LOGGER.info(
//										"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Entrada a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");
//
//								List<ScsSubzonapartido> partidosList = scsSubzonapartidoMapper.selectByExample(example);
//
//								LOGGER.info(
//										"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Salida a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");
//
//								// Si no existe la creamos
//								if (partidosList.isEmpty()) {
//
//									ScsSubzonapartido partido = new ScsSubzonapartido();
//
//									partido.setFechabaja(null);
//									partido.setFechamodificacion(new Date());
//									partido.setUsumodificacion(usuario.getIdusuario().intValue());
//									partido.setIdinstitucion(idInstitucion);
//									partido.setIdsubzona(Short.valueOf(zonaItem.getIdsubzona()));
//									partido.setIdzona(Short.valueOf(zonaItem.getIdzona()));
//									partido.setIdpartido(Long.valueOf(partidoAdd.getValue()));
//
//									LOGGER.info(
//											"createGroupZona() / scsSubzonapartidoMapper.insert() -> Entrada a scsSubzonapartidoMapper para insertar un partido judicial");
//
//									response = scsSubzonapartidoMapper.insert(partido);
//
//									LOGGER.info(
//											"createGroupZona() / scsSubzonapartidoMapper.insert() -> Salida de scsSubzonapartidoMapper para insertar un partido judicial");
//
//								}
//							}
//
//						}
//
//					}
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("No se han actualizado las zonas");
//			updateResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			error.setDescription("Se han actualizado las zonas correctamente");
//		}
//
//		updateResponseDTO.setError(error);
//
//		LOGGER.info("updateGroupZone() -> Salida del servicio para editar grupos zona");
//
//		return updateResponseDTO;
//	}

//	@Override
//	@Transactional
//	public InsertResponseDTO createZone(ZonasItem zonaItem, HttpServletRequest request) {
//		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");
//
//		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//				AdmUsuarios usuario = usuarios.get(0);
//
//				try {
//
//					ScsSubzona zona = new ScsSubzona();
//					zona.setFechabaja(null);
//					zona.setFechamodificacion(new Date());
//					zona.setUsumodificacion(usuario.getIdusuario().intValue());
//					zona.setIdinstitucion(idInstitucion);
//					zona.setNombre(zonaItem.getDescripcionsubzona());
//					zona.setIdzona(Short.valueOf(zonaItem.getIdzona()));
//
//					NewIdDTO idS = scsSubzonaExtendsMapper.getIdSubzona(usuario.getIdinstitucion(),
//							Short.valueOf(zonaItem.getIdzona()).shortValue());
//
//					if (idS == null) {
//						zona.setIdsubzona((short) 1);
//					} else {
//						short idSubzona = (short) (Integer.parseInt(idS.getNewId()) + 1);
//						zona.setIdsubzona(idSubzona);
//					}
//
//					LOGGER.info(
//							"createGroupZona() / scsSubzonaExtendsMapper.insert() -> Entrada a scsSubzonaExtendsMapper para insertar una nueva zona");
//
//					response = scsSubzonaExtendsMapper.insert(zona);
//
//					LOGGER.info(
//							"createGroupZona() / scsSubzonaExtendsMapper.insert() -> Salida de scsSubzonaExtendsMapper para insertar una nueva zona");
//
//					for (ComboItem p : zonaItem.getPartidosJudiciales()) {
//
//						ScsSubzonapartido partido = new ScsSubzonapartido();
//
//						partido.setFechabaja(null);
//						partido.setFechamodificacion(new Date());
//						partido.setUsumodificacion(usuario.getIdusuario().intValue());
//						partido.setIdinstitucion(idInstitucion);
//						partido.setIdsubzona(zona.getIdsubzona());
//						partido.setIdzona(zona.getIdzona());
//						partido.setIdpartido(Long.valueOf(p.getValue()));
//
//						LOGGER.info(
//								"createGroupZona() / scsSubzonapartidoMapper.insert() -> Entrada a scsSubzonapartidoMapper para insertar un partido judicial");
//
//						response = scsSubzonapartidoMapper.insert(partido);
//
//						LOGGER.info(
//								"createGroupZona() / scsSubzonapartidoMapper.insert() -> Salida de scsSubzonapartidoMapper para insertar un partido judicial");
//
//					}
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
//					insertResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("No se ha insertado la zona");
//			insertResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			error.setDescription("Se ha insertado la zona correctamente");
//		}
//
//		insertResponseDTO.setError(error);
//
//		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");
//
//		return insertResponseDTO;
//	}

}
