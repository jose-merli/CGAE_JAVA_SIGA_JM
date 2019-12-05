package org.itcgae.siga.scs.services.impl.maestros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ZonasDTO;
import org.itcgae.siga.DTOs.scs.ZonasItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPartidojudicial;
import org.itcgae.siga.db.entities.ScsSubzona;
import org.itcgae.siga.db.entities.ScsSubzonaExample;
import org.itcgae.siga.db.entities.ScsSubzonapartido;
import org.itcgae.siga.db.entities.ScsSubzonapartidoExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsZona;
import org.itcgae.siga.db.entities.ScsZonaExample;
import org.itcgae.siga.db.mappers.ScsSubzonapartidoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPartidojudicialExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsZonasExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IFichaZonasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FichaZonasServiceImpl implements IFichaZonasService {

	private Logger LOGGER = Logger.getLogger(FichaZonasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenPartidojudicialExtendsMapper cenPartidojudicialExtendsMapper;

	@Autowired
	private ScsZonasExtendsMapper scsZonasExtendsMapper;

	@Autowired
	private ScsSubzonaExtendsMapper scsSubzonaExtendsMapper;

	@Autowired
	private ScsSubzonapartidoMapper scsSubzonapartidoMapper;
	
	@Autowired
	private ScsTurnoMapper scsTurnoMapper;

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
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				List<CenPartidojudicial> cenPartidojudicialItems = cenPartidojudicialExtendsMapper.getPartidoByInstitucion(idInstitucion);
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				for(CenPartidojudicial cenPartidoJudicial: cenPartidojudicialItems) {
					ComboItem comboItem = new ComboItem();

					comboItem.setLabel(cenPartidoJudicial.getNombre());
					comboItem.setValue(cenPartidoJudicial.getIdpartido().toString());
					comboItems.add(comboItem);
				}
				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getPartidoJudicial() -> Salida del servicio para obtener combo partidos judiciales");
		return combo;
	}

	@Override
	public ZonasDTO searchSubzones(String idZona,String idSubZona, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener las zonas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ZonasDTO zonasDTO = new ZonasDTO();
		List<ZonasItem> zonasItems = null;

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

				zonasItems = scsSubzonaExtendsMapper.searchSubzonas(idZona,null, idInstitucion);

				LOGGER.info(
						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				for (ZonasItem zona : zonasItems) {

					if (zona.getIdpartido() != null) {

						String[] partidosJudiciales = zona.getIdpartido().split(";");
						zona.setJurisdiccion(partidosJudiciales);

					}
				}

				if (zonasItems != null) {
					zonasDTO.setZonasItems(zonasItems);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener las zonas");
		return zonasDTO;
	}

	@Override
	public InsertResponseDTO createGroupZone(ZonasItem zonaItem, HttpServletRequest request) {
		LOGGER.info("createGroupZone() ->  Entrada al servicio para crear un nuevo grupo zona");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		short idZona = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"createGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Entrada a ageEventoExtendsMapper para buscar la sesión");

					List<ZonasItem> zonasList = scsZonasExtendsMapper
							.searchGroupZoneOnlyByName(zonaItem.getDescripcionzona(), idInstitucion);

					LOGGER.info(
							"createGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					if (zonasList != null && zonasList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.gestionZonasySubzonas.existeGrupoZonaMismaDescripcion");

					} else {

						ScsZona grupoZona = new ScsZona();

						grupoZona.setFechabaja(null);
						grupoZona.setFechamodificacion(new Date());
						grupoZona.setUsumodificacion(usuario.getIdusuario().intValue());
						grupoZona.setIdinstitucion(idInstitucion);
						grupoZona.setNombre(zonaItem.getDescripcionzona());

						NewIdDTO idZ = scsZonasExtendsMapper.getIdZona(usuario.getIdinstitucion());

						if (idZ == null) {
							grupoZona.setIdzona((short) 1);
						} else {
							idZona = (short) (Integer.parseInt(idZ.getNewId()) + 1);
							grupoZona.setIdzona(idZona);
						}

						LOGGER.info(
								"createGroupZone() / scsZonasExtendsMapper.insert() -> Entrada a scsZonasExtendsMapper para insertar el nuevo grupo zona");

						response = scsZonasExtendsMapper.insert(grupoZona);

						LOGGER.info(
								"createGroupZone() / scsZonasExtendsMapper.insert() -> Salida de scsZonasExtendsMapper para insertar el nuevo grupo zona");
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idZona)));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createGroupZone() -> Salida del servicio para crear un nuevo grupo zona");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateGroupZone(ZonasItem zonaItem, HttpServletRequest request) {
		LOGGER.info("updateGroupZone() ->  Entrada al servicio para editar grupos zona");

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
					"updateGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Entrada a ageEventoExtendsMapper para buscar la sesión");

					List<ZonasItem> zonasList = scsZonasExtendsMapper.searchGroupZoneByName(zonaItem.getIdzona(),
							zonaItem.getDescripcionzona(), idInstitucion);

					LOGGER.info(
							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					if (zonasList != null && zonasList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.gestionZonasySubzonas.existeGrupoZonaMismaDescripcion");

					} else {
						ScsZonaExample scsZonaExample = new ScsZonaExample();
						scsZonaExample.createCriteria().andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
						.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Entrada a ageEventoExtendsMapper para buscar la sesión");

						List<ScsZona> scsZonasList = scsZonasExtendsMapper.selectByExample(scsZonaExample);

						LOGGER.info(
								"updateGroupsZona() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

						ScsZona zona = scsZonasList.get(0);

						zona.setNombre(zonaItem.getDescripcionzona());
						zona.setFechamodificacion(new Date());
						zona.setUsumodificacion(usuario.getIdusuario().intValue());

						response += scsZonasExtendsMapper.updateByPrimaryKey(zona);
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateGroupZone() -> Salida del servicio para editar grupos zona");

		return updateResponseDTO;
	}

	

	@Override
	public ZonasItem searchGroupZone(String idZona, HttpServletRequest request) {
		LOGGER.info("searchGroupZone() -> Entrada al servicio para obtener un grupo zona especifico");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ScsZona> zonas = null;
		ZonasItem zonaItem = new ZonasItem();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				ScsZonaExample scsZonaExample = new ScsZonaExample();
				scsZonaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdzonaEqualTo(Short.valueOf(idZona));

				LOGGER.info(
						"searchGroupZone() / scsZonasExtendsMapper.selectByExample() -> Entrada a scsZonasExtendsMapper para obtener un grupo zona especifico");

				zonas = scsZonasExtendsMapper.selectByExample(scsZonaExample);

				if (zonas != null && zonas.size() > 0) {
					ScsZona scsZona = zonas.get(0);
					zonaItem.setIdzona(scsZona.getIdzona().toString());
					zonaItem.setDescripcionzona(scsZona.getNombre());
					if(scsZona.getFechabaja() != null) {
						zonaItem.setFechabaja(scsZona.getFechabaja().toString());
					}
				}

				LOGGER.info(
						"searchGroupZone() / scsZonasExtendsMapper.selectByExample() -> Salida a scsZonasExtendsMapper para obtener un grupo zona especifico");

			}

		}
		LOGGER.info("searchZones() -> Salida del servicio para obtener un grupo zona especifico");
		return zonaItem;
	}

	@Override
	public UpdateResponseDTO updateZones(ZonasDTO zonasDTO, HttpServletRequest request) {
		LOGGER.info("updateZones() ->  Entrada al servicio para editar zonas");

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
					"updateZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {

						ScsSubzonaExample scsSubzonaExample = new ScsSubzonaExample();
						scsSubzonaExample.createCriteria().andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()));

						LOGGER.info(
								"updateZones() / scsSubzonaExtendsMapper.selectByExample() -> Entrada a scsSubzonaExtendsMapper para buscar la subzona");

						List<ScsSubzona> scsSubzonasList = scsSubzonaExtendsMapper.selectByExample(scsSubzonaExample);

						LOGGER.info(
								"updateZones() / scsSubzonaExtendsMapper.selectByExample() -> Salida a scsSubzonaExtendsMapper para buscar la subzona");

						ScsSubzona subZona = scsSubzonasList.get(0);

						subZona.setNombre(zonaItem.getDescripcionsubzona());
						subZona.setFechamodificacion(new Date());
						subZona.setUsumodificacion(usuario.getIdusuario().intValue());

						response += scsSubzonaExtendsMapper.updateByPrimaryKey(subZona);

						// MODIFICAR PARTIDOS
						if (zonaItem.getPartidosJudiciales() != null && zonaItem.getPartidosJudiciales().length > 0) {

							// Eliminamos el partido judicial que no se encuentre en la lista actual
							ScsSubzonapartidoExample scsSubzonapartidoExample = new ScsSubzonapartidoExample();
							scsSubzonapartidoExample.createCriteria()
									.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()));

							LOGGER.info(
									"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Entrada a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");

							List<ScsSubzonapartido> partidosAntiguosList = scsSubzonapartidoMapper
									.selectByExample(scsSubzonapartidoExample);

							LOGGER.info(
									"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Salida a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");

							List<ScsSubzonapartido> partidosJuridicosEliminar = new ArrayList<ScsSubzonapartido>();

							// Si hay temas que estan dados de alta, comprobamos que se encuentra en la
							// modificacion actual
							if (!partidosAntiguosList.isEmpty()) {

								for (ScsSubzonapartido partidosAsignadosAntiguos : partidosAntiguosList) {
									boolean flag = false;

									for (int i = 0; zonaItem.getPartidosJudiciales().length > i; i++) {

										if (partidosAsignadosAntiguos.getIdpartido()
												.equals(Long.valueOf(zonaItem.getPartidosJudiciales()[i].getValue()))) {
											flag = true;
											i = zonaItem.getPartidosJudiciales().length;
										}
									}

									// Si no se encuentra en la lista actual la borramos
									if (!flag) {
										partidosJuridicosEliminar.add(partidosAsignadosAntiguos);
									}
								}

								for (ScsSubzonapartido partidoBaja : partidosJuridicosEliminar) {

									LOGGER.info(
											"updateZones() / scsSubzonapartidoMapper.deleteByPrimaryKey() -> Entrada a scsSubzonapartidoMapper para eliminar un partido judicial");

									response = scsSubzonapartidoMapper.deleteByPrimaryKey(partidoBaja);

									LOGGER.info(
											"updateZones() / scsSubzonapartidoMapper.deleteByPrimaryKey() -> Salida a scsSubzonapartidoMapper para eliminar un partido judicial");
								}
							}

							// Añadimos partidos
							for (ComboItem partidoAdd : zonaItem.getPartidosJudiciales()) {
								// Para cada temas comprobamos si ya existe la relacion
								// Eliminamos el partido judicial que no se encuentre en la lista actual
								ScsSubzonapartidoExample example = new ScsSubzonapartidoExample();
								example.createCriteria().andIdpartidoEqualTo(Long.valueOf(partidoAdd.getValue()))
										.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
										.andIdinstitucionEqualTo(idInstitucion)
										.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()));

								LOGGER.info(
										"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Entrada a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");

								List<ScsSubzonapartido> partidosList = scsSubzonapartidoMapper.selectByExample(example);

								LOGGER.info(
										"updateZones() / scsSubzonapartidoMapper.selectByExample() -> Salida a scsSubzonapartidoMapper para buscar los partidos asociados de la zona");

								// Si no existe la creamos
								if (partidosList.isEmpty()) {

									ScsSubzonapartido partido = new ScsSubzonapartido();

									partido.setFechabaja(null);
									partido.setFechamodificacion(new Date());
									partido.setUsumodificacion(usuario.getIdusuario().intValue());
									partido.setIdinstitucion(idInstitucion);
									partido.setIdsubzona(Short.valueOf(zonaItem.getIdsubzona()));
									partido.setIdzona(Short.valueOf(zonaItem.getIdzona()));
									partido.setIdpartido(Long.valueOf(partidoAdd.getValue()));

									LOGGER.info(
											"createGroupZona() / scsSubzonapartidoMapper.insert() -> Entrada a scsSubzonapartidoMapper para insertar un partido judicial");

									response = scsSubzonapartidoMapper.insert(partido);

									LOGGER.info(
											"createGroupZona() / scsSubzonapartidoMapper.insert() -> Salida de scsSubzonapartidoMapper para insertar un partido judicial");

								}
							}

						}

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateGroupZone() -> Salida del servicio para editar grupos zona");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO createZone(ZonasItem zonaItem, HttpServletRequest request) {
		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsSubzona zona = new ScsSubzona();
					zona.setFechabaja(null);
					zona.setFechamodificacion(new Date());
					zona.setUsumodificacion(usuario.getIdusuario().intValue());
					zona.setIdinstitucion(idInstitucion);
					zona.setNombre(zonaItem.getDescripcionsubzona());
					zona.setIdzona(Short.valueOf(zonaItem.getIdzona()));

					NewIdDTO idS = scsSubzonaExtendsMapper.getIdSubzona(usuario.getIdinstitucion(),
							Short.valueOf(zonaItem.getIdzona()).shortValue());

					if (idS == null) {
						zona.setIdsubzona((short) 1);
					} else {
						short idSubzona = (short) (Integer.parseInt(idS.getNewId()) + 1);
						zona.setIdsubzona(idSubzona);
					}

					LOGGER.info(
							"createGroupZona() / scsSubzonaExtendsMapper.insert() -> Entrada a scsSubzonaExtendsMapper para insertar una nueva zona");

					response = scsSubzonaExtendsMapper.insert(zona);

					LOGGER.info(
							"createGroupZona() / scsSubzonaExtendsMapper.insert() -> Salida de scsSubzonaExtendsMapper para insertar una nueva zona");

					for (ComboItem p : zonaItem.getPartidosJudiciales()) {

						ScsSubzonapartido partido = new ScsSubzonapartido();

						partido.setFechabaja(null);
						partido.setFechamodificacion(new Date());
						partido.setUsumodificacion(usuario.getIdusuario().intValue());
						partido.setIdinstitucion(idInstitucion);
						partido.setIdsubzona(zona.getIdsubzona());
						partido.setIdzona(zona.getIdzona());
						partido.setIdpartido(Long.valueOf(p.getValue()));

						LOGGER.info(
								"createGroupZona() / scsSubzonapartidoMapper.insert() -> Entrada a scsSubzonapartidoMapper para insertar un partido judicial");

						response = scsSubzonapartidoMapper.insert(partido);

						LOGGER.info(
								"createGroupZona() / scsSubzonapartidoMapper.insert() -> Salida de scsSubzonapartidoMapper para insertar un partido judicial");

					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO deleteZones(ZonasDTO zonasDTO, HttpServletRequest request) {
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

					boolean existe = false;
					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {
						ScsTurnoExample ejemploTurno = new ScsTurnoExample();
						ejemploTurno.createCriteria().andIdzonaEqualTo(Short.parseShort(zonaItem.getIdzona())).andIdsubzonaEqualTo(Short.parseShort(zonaItem.getIdsubzona())).andIdinstitucionEqualTo(idInstitucion);
						List<ScsTurno> turnos = scsTurnoMapper.selectByExample(ejemploTurno);

						if(!(turnos == null || turnos.size() == 0)) {
							existe = true; 
						}
					}
					if(!existe) {
					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {

						// Eliminamos asociaciones partidos judiciales con zona

						ScsSubzonapartidoExample scsSubzonapartidoExample = new ScsSubzonapartidoExample();
						scsSubzonapartidoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
								.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()));

						LOGGER.info(
								"deleteZones() / scsSubzonapartidoMapper.deleteByExample() -> Entrada a scsSubzonapartidoMapper para eliminar los partidos judiciales asociadoas a la zona");

						response = scsSubzonapartidoMapper.deleteByExample(scsSubzonapartidoExample);

						LOGGER.info(
								"deleteZones() / scsSubzonapartidoMapper.deleteByExample() -> Salida de scsSubzonapartidoMapper para eliminar los partidos judiciales asociadoas a la zona");

						// Eliminamos zona
						ScsSubzonaExample scsSubzonaExample = new ScsSubzonaExample();
						scsSubzonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
								.andIdsubzonaEqualTo(Short.valueOf(zonaItem.getIdsubzona()));

						LOGGER.info(
								"deleteZones() / scsSubzonaExtendsMapper.deleteByExample() -> Entrada a scsSubzonapartidoMapper para eliminar los partidos judiciales asociadoas a la zona");

						response = scsSubzonaExtendsMapper.deleteByExample(scsSubzonaExample);

						LOGGER.info(
								"deleteZones() / scsSubzonaExtendsMapper.deleteByExample() -> Salida de scsSubzonapartidoMapper para eliminar los partidos judiciales asociadoas a la zona");

					}
				}else {
					response = 2;
				}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if(response == 2) {
			error.setCode(400);
			error.setDescription("zonasysubzonas.zonas.ficha.subzonaEnUso");
			updateResponseDTO.setStatus(SigaConstants.KO);	
		}else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar zonas de un grupo zona");

		return updateResponseDTO;
	}
}