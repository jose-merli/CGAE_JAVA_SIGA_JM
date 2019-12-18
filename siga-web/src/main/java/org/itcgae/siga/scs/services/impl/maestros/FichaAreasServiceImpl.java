package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.AreasDTO;
import org.itcgae.siga.DTOs.scs.AreasItem;
import org.itcgae.siga.DTOs.scs.MateriasDTO;
import org.itcgae.siga.DTOs.scs.MateriasItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteExample;
import org.itcgae.siga.db.entities.ScsArea;
import org.itcgae.siga.db.entities.ScsAreaExample;
import org.itcgae.siga.db.entities.ScsMateria;
import org.itcgae.siga.db.entities.ScsMateriaExample;
import org.itcgae.siga.db.entities.ScsMateriajurisdiccion;
import org.itcgae.siga.db.entities.ScsMateriajurisdiccionExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.ScsAreaMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAreasMateriasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJurisdiccionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsMateriaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsMateriaJurisdiccionExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IFichaAreasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FichaAreasServiceImpl implements IFichaAreasService {

	private Logger LOGGER = Logger.getLogger(FichaAreasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsJurisdiccionExtendsMapper ScsJurisdiccionExtendsMapper;

	@Autowired
	private ScsAreasMateriasExtendsMapper scsAreasMateriasExtendsMapper;

	@Autowired
	private ScsAreaMapper scsAreasMapper;

	@Autowired
	private ScsMateriaExtendsMapper scsMateriaExtendsMapper;

	@Autowired
	private ScsTurnoMapper scsTurnoMapper;

	@Autowired
	private ExpExpedienteMapper expExpedienteMapper;

	@Autowired
	private ScsMateriaJurisdiccionExtendsMapper scsMateriaJurisdiccionExtendsMapper;

	@Override
	public ComboDTO getJurisdicciones(HttpServletRequest request) {
		LOGGER.info("getJurisdicciones() -> Entrada al servicio para obtener combo jurisdicciones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getJurisdicciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getJurisdicciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getJurisdicciones() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Entrada a cenJurisdiccionesExtendsMapper para obtener los jurisdicciones");

				List<ComboItem> comboItems = ScsJurisdiccionExtendsMapper.getJurisdicciones(idInstitucion.toString(),
						dni);

				LOGGER.info(
						"getJurisdicciones() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Salida a cenJurisdiccionesExtendsMapper para obtener los jurisdicciones");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getJurisdicciones() -> Salida del servicio para obtener combo jurisdicciones");
		return combo;
	}

	@Override
	public AreasDTO searchAreas(AreasItem areasItem, HttpServletRequest request) {
		LOGGER.info("searchareas() -> Entrada al servicio para obtener las areas");

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
					"searchareas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchareas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchareas() / scsAreasMateriasExtendsMapper.searchareas() -> Entrada a scsAreasMateriasExtendsMapper para obtener las areas");
				areasItem.setidInstitucion(idInstitucion);
				areasItems = scsAreasMateriasExtendsMapper.searchAreas(areasItem);

				LOGGER.info(
						"searchareas() / scsAreasMateriasExtendsMapper.searchareas() -> Salida a scsAreasMateriasExtendsMapper para obtener las areas");

				if (areasItems != null) {
					areasDTO.setAreasItems(areasItems);
				}
			}

		}
		LOGGER.info("searchareas() -> Salida del servicio para obtener las areas");
		return areasDTO;
	}

	@Override
	public UpdateResponseDTO updateArea(AreasItem areasItem, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar areas de un grupo zona");

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
					// Eliminamos asociaciones jurisdicciones con zona

					ScsArea area = new ScsArea();
					area.setContenido(areasItem.getContenido());
					area.setFechamodificacion(new Date());
					area.setIdarea(Short.parseShort(areasItem.getIdArea()));
					area.setIdinstitucion(idInstitucion);
					area.setNombre(areasItem.getNombreArea());
					area.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"deleteAreas() / scsAreasMateriasExtendsMapper.deleteByExample() -> Entrada a scsAreasMateriasExtendsMapper para eliminar las Areas seleccionadas");

					response = scsAreasMateriasExtendsMapper.updateByPrimaryKey(area);

					LOGGER.info(
							"deleteAreas() / scsAreasMateriasExtendsMapper.deleteByExample() -> Salida de scsAreasMateriasExtendsMapper para eliminar las Areas seleccionadas");

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
			error.setDescription("messages.censo.nombreExiste");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar areas de un grupo zona");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO deleteAreas(AreasDTO areasDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar areas de un grupo zona");

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
					for (AreasItem areasItem : areasDTO.getAreasItems()) {

						ScsTurnoExample ejemploTurno = new ScsTurnoExample();
						ejemploTurno.createCriteria().andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()))
								.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
						List<ScsTurno> turnos = scsTurnoMapper.selectByExample(ejemploTurno);

						// ExpExpedienteExample ejemploExpediente = new ExpExpedienteExample();
						// ejemploExpediente.createCriteria().andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdinstitucionEqualTo(idInstitucion);
						// List<ExpExpediente> expedientes =
						// expExpedienteMapper.selectByExample(ejemploExpediente);

						// if(!(turnos == null || turnos.size() == 0) || !(expedientes == null ||
						// expedientes.size() == 0)) {
						if (!(turnos == null || turnos.size() == 0)) {
							int totalTurnos = turnos.size();
							int turnosBaja = 0;
							for (Iterator iterator = turnos.iterator(); iterator.hasNext();) {
								ScsTurno scsTurno = (ScsTurno) iterator.next();
								if (null != scsTurno.getFechabaja() || scsTurno.getVisibilidad().equals("0")) {
									turnosBaja++;
								}

							}
							if (turnosBaja < totalTurnos) {
								// Todos los turnos están de baja
								existe = true;
							}
						}
					}

					if (!existe) {

						for (AreasItem areasItem : areasDTO.getAreasItems()) {

							// Eliminamos areas

							ScsArea area = new ScsArea();
							area.setContenido(areasItem.getContenido());
							if (areasItem.getFechabaja() == null) {
								area.setFechabaja(new Date());
							} else {
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
					} else {

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
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 2) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.areaEnUso");
			updateResponseDTO.setStatus(SigaConstants.KO);

		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar areas de un grupo zona");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO activateAreas(AreasDTO areasDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar areas de un grupo zona");

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
					for (AreasItem areasItem : areasDTO.getAreasItems()) {

						// Activamos areas

						ScsArea area = new ScsArea();
						area.setContenido(areasItem.getContenido());
						area.setFechabaja(null);
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
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 2) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.areaEnUso");
			updateResponseDTO.setStatus(SigaConstants.KO);

		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar areas de un grupo zona");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO createArea(AreasItem areasItem, HttpServletRequest request) {
		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Short idAreaNueva = 0;
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

					ScsAreaExample example = new ScsAreaExample();
					example.createCriteria().andNombreEqualTo(areasItem.getNombreArea())
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsArea> areasDuplicadas = scsAreasMapper.selectByExample(example);

					LOGGER.info(
							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					if (areasDuplicadas != null && areasDuplicadas.size() > 0) {

						error.setCode(400);
						error.setDescription("messages.censo.nombreExiste");

					} else {

						ScsArea area = new ScsArea();
						area.setContenido(areasItem.getContenido());
						area.setFechamodificacion(new Date());
						area.setIdinstitucion(idInstitucion);
						area.setNombre(areasItem.getNombreArea());
						area.setUsumodificacion(usuarios.get(0).getIdusuario());

						NewIdDTO idArea = scsAreasMateriasExtendsMapper.getIdArea(usuario.getIdinstitucion());

						if (idArea == null) {
							area.setIdarea((short) 1);
						} else {
							idAreaNueva = (short) (Integer.parseInt(idArea.getNewId()) + 1);
							area.setIdarea(idAreaNueva);
						}

						LOGGER.info(
								"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva zona");

						response = scsAreasMateriasExtendsMapper.insert(area);

						LOGGER.info(
								"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva area");
					}

				} catch (Exception e) {
					LOGGER.info(e);
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
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idAreaNueva)));
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");

		return insertResponseDTO;
	}

	@Override
	public MateriasDTO searchMaterias(String idArea, HttpServletRequest request) {
		LOGGER.info("searchMaterias() -> Entrada al servicio para obtener las Materias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		MateriasDTO materiasDTO = new MateriasDTO();
		List<MateriasItem> materiasItems = null;
		MateriasItem materia = new MateriasItem();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchMaterias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchMaterias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchMaterias() / scsMateriaExtendsMapper.searchMateria() -> Entrada a scsMateriaExtendsMapper para obtener las Materias");
				materia.setIdArea(idArea);
				materia.setidInstitucion(idInstitucion);
				materiasItems = scsMateriaExtendsMapper.searchMateria(materia);

				LOGGER.info(
						"searchMaterias() / scsMateriaExtendsMapper.searchMateria() -> Salida a scsMateriaExtendsMapper para obtener las Materias");

				for (MateriasItem materias : materiasItems) {

					if (materias.getJurisdiccion() != null) {

						String[] materiasEnvio = materias.getJurisdiccion().split(";");
						materias.setJurisdicciones(materiasEnvio);

					}
				}
				materiasDTO.setmateriasItems(materiasItems);
			}

		}
		LOGGER.info("searchMateria() -> Salida del servicio para obtener las Materias");
		return materiasDTO;
	}

	@Override
	public UpdateResponseDTO updateMaterias(AreasDTO areasDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
		// PENDIENTE DE ACABAR
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

					for (AreasItem areasItem : areasDTO.getAreasItems()) {

						ScsMateria materia = new ScsMateria();
						materia.setIdmateria(Short.parseShort(areasItem.getIdMateria()));
						materia.setFechamodificacion(new Date());
						materia.setIdarea(Short.parseShort(areasItem.getIdArea()));
						materia.setIdinstitucion(areasItem.getidInstitucion());
						materia.setNombre(areasItem.getNombreMateria());
						materia.setContenido(areasItem.getContenido());
						materia.setUsumodificacion(usuarios.get(0).getIdusuario());
						response = scsMateriaExtendsMapper.updateByPrimaryKeySelective(materia);

						if (null != areasItem.getJurisdiccion()) {

							String[] jurisdiccionesCombo = areasItem.getJurisdiccion().split(";");

							ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
							ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion())
									.andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()))
									.andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
							List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper
									.selectByExample(ejemploJurisdiccion);

							for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
								scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
							}

							if (jurisdiccionesCombo[0] != "")
								for (String jurisdicciones : jurisdiccionesCombo) {
									ScsMateriajurisdiccion materiaJurisdiccion = new ScsMateriajurisdiccion();
									materiaJurisdiccion.setFechamodificacion(new Date());
									materiaJurisdiccion.setIdarea(Short.parseShort(areasItem.getIdArea()));
									materiaJurisdiccion.setIdmateria(Short.parseShort(areasItem.getIdMateria()));
									materiaJurisdiccion.setIdinstitucion(areasItem.getidInstitucion());
									materiaJurisdiccion.setIdjurisdiccion(Short.parseShort(jurisdicciones));
									materiaJurisdiccion.setUsumodificacion(usuarios.get(0).getIdusuario());
									scsMateriaJurisdiccionExtendsMapper.insert(materiaJurisdiccion);
								}

						} else {
							ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
							ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion())
									.andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()))
									.andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
							List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper
									.selectByExample(ejemploJurisdiccion);

							for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
								scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
							}
						}

					}
				} catch (Exception e) {
					LOGGER.debug(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.actualizarerror");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para actualizar areas de un grupo zona");

		return updateResponseDTO;

	}

	@Override
	public UpdateResponseDTO deleteMaterias(AreasDTO areasDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
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

					for (AreasItem areasItem : areasDTO.getAreasItems()) {

						ScsTurnoExample ejemploTurno = new ScsTurnoExample();
						ejemploTurno.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()))
								.andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()))
								.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
						List<ScsTurno> turnos = scsTurnoMapper.selectByExample(ejemploTurno);

						// ExpExpedienteExample ejemploExpediente = new ExpExpedienteExample();
						// ejemploExpediente.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdinstitucionEqualTo(idInstitucion);
						// List<ExpExpediente> expedientes =
						// expExpedienteMapper.selectByExample(ejemploExpediente);
						//
						// if(!(turnos == null || turnos.size() == 0) || !(expedientes == null ||
						// expedientes.size() == 0)) {
						if (!(turnos == null || turnos.size() == 0)) {
							int totalTurnos = turnos.size();
							int turnosBaja = 0;
							for (Iterator iterator = turnos.iterator(); iterator.hasNext();) {
								ScsTurno scsTurno = (ScsTurno) iterator.next();
								if (null != scsTurno.getFechabaja() || scsTurno.getVisibilidad().equals("0")) {
									turnosBaja++;
								}

							}
							if (turnosBaja < totalTurnos) {
								// Todos los turnos están de baja
								existe = true;
							}
						}
					}

					if (!existe) {
						for (AreasItem areasItem : areasDTO.getAreasItems()) {

							ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
							ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion())
									.andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()))
									.andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
							List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper
									.selectByExample(ejemploJurisdiccion);

							for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
								scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
							}

							ScsMateriaExample example = new ScsMateriaExample();
							example.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()))
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()));
							response = scsMateriaExtendsMapper.deleteByExample(example);

						}
					} else {
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
			error.setDescription("areasmaterias.materias.ficha.erroreliminadomaterias");
			updateResponseDTO.setStatus(SigaConstants.KO);

		} else if (response == 2) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.materiaEnUso");
			updateResponseDTO.setStatus(SigaConstants.KO);

		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar materias de un area");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO createMaterias(AreasItem areasItem, HttpServletRequest request) {
		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Short idMateriaNueva = 0;
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createMaterias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createMaterias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// MateriasItem ejemplo = new MateriasItem();
					// ejemplo.setNombreMateria(areasItem.getNombreMateria());
					// ejemplo.setIdArea(areasItem.getIdArea());
					// ejemplo.setidInstitucion(idInstitucion);
					//
					// List<MateriasItem> materiasDuplicadas =
					// scsMateriaExtendsMapper.searchMateria(ejemplo);

					LOGGER.info(
							"createMaterias() / scsAreasMateriasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					// if (materiasDuplicadas != null && materiasDuplicadas.size() > 0) {
					//
					// error.setCode(400);
					// error.setDescription("messages.censo.nombreExiste");
					//
					// } else {

					ScsMateria materia = new ScsMateria();
					materia.setContenido(areasItem.getContenido());
					materia.setFechamodificacion(new Date());
					materia.setIdinstitucion(idInstitucion);
					materia.setNombre(areasItem.getNombreMateria());
					materia.setUsumodificacion(usuarios.get(0).getIdusuario());
					materia.setIdarea(Short.parseShort(areasItem.getIdArea()));

					String[] jurisdiccionesCombo = null;

					NewIdDTO idMateria = scsMateriaExtendsMapper.getIdMateria(usuario.getIdinstitucion());
					if (areasItem.getJurisdiccion() != "" && areasItem.getJurisdiccion() != null) {
						jurisdiccionesCombo = areasItem.getJurisdiccion().split(";");
					}

					if (idMateria == null) {
						idMateriaNueva = 1;
						materia.setIdmateria((short) 1);
					} else {
						idMateriaNueva = (short) (Integer.parseInt(idMateria.getNewId()) + 1);
						materia.setIdmateria(idMateriaNueva);
					}

					LOGGER.info(
							"createMaterias() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva materia");

					response = scsMateriaExtendsMapper.insert(materia);
					if (jurisdiccionesCombo != null) {
						for (String jurisdicciones : jurisdiccionesCombo) {
							ScsMateriajurisdiccion materiaJurisdiccion = new ScsMateriajurisdiccion();
							materiaJurisdiccion.setFechamodificacion(new Date());
							materiaJurisdiccion.setIdarea(Short.parseShort(areasItem.getIdArea()));
							materiaJurisdiccion.setIdmateria(idMateriaNueva);
							materiaJurisdiccion.setIdinstitucion(idInstitucion);
							materiaJurisdiccion.setIdjurisdiccion(Short.parseShort(jurisdicciones));
							materiaJurisdiccion.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsMateriaJurisdiccionExtendsMapper.insert(materiaJurisdiccion);
						}
					}

					LOGGER.info(
							"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva materia");
					// }

				} catch (Exception e) {
					LOGGER.info(e);
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
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idMateriaNueva)));
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createMaterias() -> Salida del servicio para crear una nueva materia");

		return insertResponseDTO;
	}

}
