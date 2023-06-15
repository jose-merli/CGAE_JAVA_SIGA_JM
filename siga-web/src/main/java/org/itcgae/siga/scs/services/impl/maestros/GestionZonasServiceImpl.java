package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ZonasDTO;
import org.itcgae.siga.DTOs.scs.ZonasItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteExample;
import org.itcgae.siga.db.entities.ScsSubzona;
import org.itcgae.siga.db.entities.ScsSubzonaExample;
import org.itcgae.siga.db.entities.ScsSubzonapartido;
import org.itcgae.siga.db.entities.ScsSubzonapartidoExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsZona;
import org.itcgae.siga.db.entities.ScsZonaExample;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.ScsSubzonapartidoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsZonasExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IGestionZonasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionZonasServiceImpl implements IGestionZonasService {

	private Logger LOGGER = Logger.getLogger(GestionZonasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsZonasExtendsMapper scsZonasExtendsMapper;
	
	@Autowired
	private ScsSubzonaExtendsMapper scsSubzonaExtendsMapper;
	
	@Autowired
	private ScsSubzonapartidoMapper scsSubzonapartidoMapper;
	
	@Autowired
	private ScsTurnoMapper scsTurnoMapper;
	
	@Autowired
	private ExpExpedienteMapper expExpedienteMapper;
	
	@Override
	public ZonasDTO searchZones(ZonasItem zonasItem, HttpServletRequest request) {
		LOGGER.info("searchZones() -> Entrada al servicio para obtener las zonas");

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
					"searchZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"searchZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchZones() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				zonasItems = scsZonasExtendsMapper.searchZonas(zonasItem, idInstitucion);
				
				LOGGER.info(
						"searchZones() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");


				if(zonasItems != null) {
					zonasDTO.setZonasItems(zonasItems);
				}
			}

		}
		LOGGER.info("searchZones() -> Salida del servicio para obtener las zonas");
		return zonasDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteGroupZones(ZonasDTO zonasDTO, HttpServletRequest request) {
		LOGGER.info("deleteGroupZona() ->  Entrada al servicio para eliminar grupos zona");

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
					"deleteGroupZona() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteGroupZona() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					boolean existe = false;
					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {
						ScsTurnoExample ejemploTurno = new ScsTurnoExample();
						ejemploTurno.createCriteria().andIdzonaEqualTo(Short.parseShort(zonaItem.getIdzona())).andIdinstitucionEqualTo(idInstitucion);
						List<ScsTurno> turnos = scsTurnoMapper.selectByExample(ejemploTurno);

						if(!(turnos == null || turnos.size() == 0)) {
							int totalTurnos = turnos.size();
							int turnosBaja = 0;
							for (Iterator iterator = turnos.iterator(); iterator.hasNext();) {
								ScsTurno scsTurno = (ScsTurno) iterator.next();
								if (null != scsTurno.getFechabaja() || scsTurno.getVisibilidad().equals("0") ) {
									turnosBaja++;
								}
								
							}
							if (turnosBaja < totalTurnos ) {
								//Todos los turnos están de baja
								existe = true; 
							}
						}
					}
					if(!existe) {
					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {
						// Eliminamos asociaciones partidos judiciales con zona
						if (zonaItem.getIdsConjuntoSubzonas() != null) {

							String[] idSubzonasZonas = zonaItem.getIdsConjuntoSubzonas().split(";");

							for (String idSubzona : idSubzonasZonas) {

								ScsSubzonapartidoExample scsSubzonapartidoExample = new ScsSubzonapartidoExample();
								scsSubzonapartidoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
										.andIdsubzonaEqualTo(Short.valueOf(idSubzona));

								LOGGER.info(
										"deleteGroupZona() / scsSubzonapartidoMapper.selectByExample() -> Entrada a scsSubzonapartidoMapper para buscar los partidos judiciales asociados a la zona");

								List<ScsSubzonapartido> partidosZonasList = scsSubzonapartidoMapper
										.selectByExample(scsSubzonapartidoExample);

								LOGGER.info(
										"deleteGroupZona() / scsSubzonapartidoMapper.selectByExample() -> Salida de scsSubzonapartidoMapper para buscar los partidos judiciales asociados a la zona");

								if (null != partidosZonasList && partidosZonasList.size() > 0) {

									for (ScsSubzonapartido scsSubzonapartido : partidosZonasList) {

										scsSubzonapartido.setFechabaja(new Date());
										scsSubzonapartido.setFechamodificacion(new Date());
										scsSubzonapartido.setUsumodificacion(usuario.getIdusuario());

										LOGGER.info(
												"deleteGroupZona() / scsSubzonapartidoMapper.updateByPrimaryKey() -> Entrada a scsSubzonapartidoMapper para dar de baja a los partidos judiciales asociadoas a la zona");

										response = scsSubzonapartidoMapper.updateByPrimaryKey(scsSubzonapartido);

										LOGGER.info(
												"deleteGroupZona() / scsSubzonapartidoMapper.updateByPrimaryKey() -> Salida de scsSubzonapartidoMapper para dar de baja a los partidos judiciales asociadoas a la zona");

									}
								}

								// Damos alta zonas

								ScsSubzonaExample scsSubzonaExample = new ScsSubzonaExample();
								scsSubzonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
										.andIdsubzonaEqualTo(Short.valueOf(idSubzona));

								LOGGER.info(
										"deleteGroupZona() / scsSubzonaExtendsMapper.selectByExample() -> Entrada a scsSubzonaExtendsMapper para buscar las zonas");

								List<ScsSubzona> zonasList = scsSubzonaExtendsMapper.selectByExample(scsSubzonaExample);

								LOGGER.info(
										"deleteGroupZona() / scsSubzonaExtendsMapper.selectByExample() -> Salida de scsSubzonaExtendsMapper para buscar las zonas");

								if (null != zonasList && zonasList.size() > 0) {

									for (ScsSubzona zona : zonasList) {

										zona.setFechabaja(new Date());
										zona.setFechamodificacion(new Date());
										zona.setUsumodificacion(usuario.getIdusuario());

										LOGGER.info(
												"deleteGroupZona() / scsSubzonaExtendsMapper.updateByPrimaryKey() -> Entrada a scsSubzonaExtendsMapper para dar de alta las zonass asociadas al grupo zona");

										response = scsSubzonaExtendsMapper.updateByPrimaryKey(zona);

										LOGGER.info(
												"deleteGroupZona() / scsSubzonaExtendsMapper.updateByPrimaryKey() -> Salida de scsSubzonaExtendsMapper para dar de alta las zonass asociadas al grupo zona");
									}
								}
							}

						}

						// Eliminamos grupo zona
						ScsZonaExample scsZonaExample = new ScsZonaExample();
						scsZonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()));

						LOGGER.info(
								"deleteGroupZona() / scsSubzonapartidoMapper.deleteByExample() -> Entrada a scsSubzonapartidoMapper para buscar el grupo zona");

						List<ScsZona> grupoZonasList = scsZonasExtendsMapper.selectByExample(scsZonaExample);

						LOGGER.info(
								"deleteGroupZona() / scsSubzonapartidoMapper.deleteByExample() -> Salida de scsSubzonapartidoMapper para buscar el grupo zona");

						if (null != grupoZonasList && grupoZonasList.size() > 0) {

							ScsZona grupoZona = grupoZonasList.get(0);

							grupoZona.setFechabaja(new Date());
							grupoZona.setFechamodificacion(new Date());
							grupoZona.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteGroupZona() / scsZonasExtendsMapper.deleteByExample() -> Entrada a scsZonasExtendsMapper para dar de baja a un grupo zona");

							response = scsZonasExtendsMapper.updateByPrimaryKey(grupoZona);

							LOGGER.info(
									"deleteGroupZona() / scsZonasExtendsMapper.deleteByExample() -> Salida de scsZonasExtendsMapper para dar de baja a un grupo zona");
						}

					}
				}else {
					response = 2;
				}

				} catch (Exception e) {
					LOGGER.info(e);
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
			error.setDescription("zonasysubzonas.zonas.ficha.zonaEnUso");
			updateResponseDTO.setStatus(SigaConstants.KO);	
		}else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteGroupZona() -> Salida del servicio para crear un nuevo grupo zona");

		return updateResponseDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO activateGroupZones(ZonasDTO zonasDTO, HttpServletRequest request) {
		LOGGER.info("activateGroupZones() ->  Entrada al servicio para dar de alta a grupos zona");

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
					"activateGroupZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateGroupZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ZonasItem zonaItem : zonasDTO.getZonasItems()) {

						// Damos de alta Grupo Zona
						ScsZonaExample scsZonaExample = new ScsZonaExample();
						scsZonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()));

						LOGGER.info(
								"activateGroupZones() / scsSubzonapartidoMapper.deleteByExample() -> Entrada a scsSubzonapartidoMapper para buscar el grupo zona");

						List<ScsZona> grupoZonasList = scsZonasExtendsMapper.selectByExample(scsZonaExample);

						LOGGER.info(
								"activateGroupZones() / scsSubzonapartidoMapper.deleteByExample() -> Salida de scsSubzonapartidoMapper para buscar el grupo zona");

						if (null != grupoZonasList && grupoZonasList.size() > 0) {

							ScsZona grupoZona = grupoZonasList.get(0);

							grupoZona.setFechabaja(null);
							grupoZona.setFechamodificacion(new Date());
							grupoZona.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateGroupZones() / scsZonasExtendsMapper.deleteByExample() -> Entrada a scsZonasExtendsMapper para dar de baja a un grupo zona");

							response = scsZonasExtendsMapper.updateByPrimaryKey(grupoZona);

							LOGGER.info(
									"activateGroupZones() / scsZonasExtendsMapper.deleteByExample() -> Salida de scsZonasExtendsMapper para dar de baja a un grupo zona");
						}

						// Damos de alta a las asociaciones partidos judiciales con zona
						if (zonaItem.getIdsConjuntoSubzonas() != null) {

							String[] idSubzonasZonas = zonaItem.getIdsConjuntoSubzonas().split(";");

							for (String idSubzona : idSubzonasZonas) {

								ScsSubzonapartidoExample scsSubzonapartidoExample = new ScsSubzonapartidoExample();
								scsSubzonapartidoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
										.andIdsubzonaEqualTo(Short.valueOf(idSubzona)).andFechabajaIsNull();

								LOGGER.info(
										"activateGroupZones() / scsSubzonapartidoMapper.selectByExample() -> Entrada a scsSubzonapartidoMapper para buscar los partidos judiciales asociados a la zona");

								List<ScsSubzonapartido> partidosZonasList = scsSubzonapartidoMapper
										.selectByExample(scsSubzonapartidoExample);

								LOGGER.info(
										"activateGroupZones() / scsSubzonapartidoMapper.selectByExample() -> Salida de scsSubzonapartidoMapper para buscar los partidos judiciales asociados a la zona");

								if (null != partidosZonasList && partidosZonasList.size() > 0) {

									for (ScsSubzonapartido scsSubzonapartido : partidosZonasList) {

										scsSubzonapartido.setFechabaja(null);
										scsSubzonapartido.setFechamodificacion(new Date());
										scsSubzonapartido.setUsumodificacion(usuario.getIdusuario());

										LOGGER.info(
												"activateGroupZones() / scsSubzonapartidoMapper.updateByPrimaryKey() -> Entrada a scsSubzonapartidoMapper para dar de baja a los partidos judiciales asociadoas a la zona");

										response = scsSubzonapartidoMapper.updateByPrimaryKey(scsSubzonapartido);

										LOGGER.info(
												"activateGroupZones() / scsSubzonapartidoMapper.updateByPrimaryKey() -> Salida de scsSubzonapartidoMapper para dar de baja a los partidos judiciales asociadoas a la zona");

									}
								}

								// Eliminamos zonas

								ScsSubzonaExample scsSubzonaExample = new ScsSubzonaExample();
								scsSubzonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdzonaEqualTo(Short.valueOf(zonaItem.getIdzona()))
										.andIdsubzonaEqualTo(Short.valueOf(idSubzona));

								LOGGER.info(
										"activateGroupZones() / scsSubzonaExtendsMapper.selectByExample() -> Entrada a scsSubzonaExtendsMapper para buscar las zonas");

								List<ScsSubzona> zonasList = scsSubzonaExtendsMapper.selectByExample(scsSubzonaExample);

								LOGGER.info(
										"activateGroupZones() / scsSubzonaExtendsMapper.selectByExample() -> Salida de scsSubzonaExtendsMapper para buscar las zonas");

								if (null != zonasList && zonasList.size() > 0) {

									for (ScsSubzona zona : zonasList) {

										zona.setFechabaja(null);
										zona.setFechamodificacion(new Date());
										zona.setUsumodificacion(usuario.getIdusuario());

										LOGGER.info(
												"activateGroupZones() / scsSubzonaExtendsMapper.updateByPrimaryKey() -> Entrada a scsSubzonaExtendsMapper para dar de alta las zonass asociadas al grupo zona");

										response = scsSubzonaExtendsMapper.updateByPrimaryKey(zona);

										LOGGER.info(
												"activateGroupZones() / scsSubzonaExtendsMapper.updateByPrimaryKey() -> Salida de scsSubzonaExtendsMapper para dar de alta las zonass asociadas al grupo zona");
									}
								}
							}

						}

					}

				} catch (Exception e) {
					LOGGER.info(e);
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

		LOGGER.info("activateGroupZones() -> Salida del servicio para dar de alta a grupos zona");

		return updateResponseDTO;
	}

}
