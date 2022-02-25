package org.itcgae.siga.scs.services.impl.maestros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoDTO;
import org.itcgae.siga.DTOs.scs.ProcedimientoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsJuzgado;
import org.itcgae.siga.db.entities.ScsJuzgadoExample;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimiento;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimientoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoProcedimientoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcedimientosExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IGestionJuzgadosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionJuzgadoServiceImpl implements IGestionJuzgadosService {

	private Logger LOGGER = Logger.getLogger(GestionJuzgadoServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsJuzgadoExtendsMapper scsJuzgadoExtendsMapper;

	@Autowired
	private ScsProcedimientosExtendsMapper scsProcedimientosExtendsMapper;

	@Autowired
	private ScsJuzgadoProcedimientoExtendsMapper scsJuzgadoProcedimientoExtendsMapper;

	@Override
	public InsertResponseDTO createCourt(JuzgadoItem juzgadoItem, HttpServletRequest request) {
		LOGGER.info("createCourt() ->  Entrada al servicio para crear un nuevo juzgado");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		long idJuzgado = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createCourt() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createCourt() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {


					LOGGER.info(
							"createCourt() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Entrada a ageEventoExtendsMapper para buscar la sesión");
					

					LOGGER.info(
							"createCourt() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					 

						ScsJuzgado juzgado = new ScsJuzgado();

						juzgado.setFechabaja(null);
						juzgado.setVisible("1");
						juzgado.setFechamodificacion(new Date());
						juzgado.setUsumodificacion(usuario.getIdusuario().intValue());
						juzgado.setIdinstitucion(idInstitucion);
						juzgado.setNombre(juzgadoItem.getNombre());
						juzgado.setDomicilio(juzgadoItem.getDomicilio());
						juzgado.setCodigopostal(juzgadoItem.getCodigoPostal());
						juzgado.setIdpoblacion(juzgadoItem.getIdPoblacion());
						juzgado.setIdprovincia(juzgadoItem.getIdProvincia());
						juzgado.setTelefono1(juzgadoItem.getTelefono1());
						juzgado.setTelefono2(juzgadoItem.getTelefono2());
						juzgado.setFax1(juzgadoItem.getFax());
						juzgado.setCodigoext(juzgadoItem.getCodigoExt());
						juzgado.setCodigoprocurador(juzgadoItem.getCodigoProcurador());
						juzgado.setMovil(juzgadoItem.getMovil());
						juzgado.setCodigoejis(juzgadoItem.getCodigoEjis());

						if (juzgadoItem.getEsDecano() != null && juzgadoItem.getEsDecano() != "") {
							juzgado.setEsdecano(Short.valueOf(juzgadoItem.getEsDecano()));
						} else {
							juzgado.setEsdecano(Short.decode("0"));
						}

						juzgado.setCodigoext2(juzgadoItem.getCodigoExt2());
						juzgado.setEmail(juzgadoItem.getEmail());

						if (juzgadoItem.getIsCodigoEjis() != null && juzgadoItem.getIsCodigoEjis() != "") {
							juzgado.setIscodigoejis(Short.valueOf(juzgadoItem.getIsCodigoEjis()));
						} else {
							juzgado.setIscodigoejis(Short.valueOf("0"));
						}

						if (juzgadoItem.getVisibleMovil() != null && juzgadoItem.getVisibleMovil() != "") {
							juzgado.setVisiblemovil(Short.valueOf(juzgadoItem.getVisibleMovil()));
						} else {
							juzgado.setVisiblemovil(Short.valueOf("1"));
						}

						if (juzgadoItem.getFechaCodigoEjis() != null) {
							juzgado.setFechacodigoejis(juzgadoItem.getFechaCodigoEjis());
						} else {
							juzgado.setFechacodigoejis(null);
						}


						NewIdDTO idJ = scsJuzgadoExtendsMapper.getIdJuzgado(usuario.getIdinstitucion());

						if (idJ == null) {
							juzgado.setIdjuzgado((long) 1);
							idJuzgado = 1;
						} else {
							idJuzgado = (Integer.parseInt(idJ.getNewId()) + 1);
							juzgado.setIdjuzgado(idJuzgado);
						}

						LOGGER.info(
								"createCourt() / scsJuzgadoExtendsMapper.insert() -> Entrada a scsJuzgadoExtendsMapper para insertar el nuevo juzgado");

						response = scsJuzgadoExtendsMapper.insert(juzgado);

						LOGGER.info(
								"createCourt() / scsJuzgadoExtendsMapper.insert() -> Salida de scsJuzgadoExtendsMapper para insertar el nuevo juzgado");
					

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idJuzgado));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createCourt() -> Salida del servicio para crear un nuevo juzgado");
		}
		return insertResponseDTO;
	}

	@Override
	public ProcedimientoDTO searchProcess(HttpServletRequest request) {
		LOGGER.info("searchProcess() -> Entrada al servicio para obtener los procedimientos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ProcedimientoItem> procedimientos = null;
		ProcedimientoDTO procedimientoDTO = new ProcedimientoDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchProcess() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchProcess() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchProcess() / scsProcedimientosExtendsMapper.searchProcess() -> Entrada a scsProcedimientosExtendsMapper para obtener procedimientos");

				procedimientos = scsProcedimientosExtendsMapper.searchProcess(usuario.getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"searchProcess() / scsProcedimientosExtendsMapper.searchProcess() -> Salida a scsProcedimientosExtendsMapper para obtener procedimientos");

				if (procedimientos != null) {
					procedimientoDTO.setProcedimientosItems(procedimientos);
				}
			}

		}
		LOGGER.info("searchProcess() -> Salida del servicio para obtener los procedimientos");
		return procedimientoDTO;
	}

	@Override
	public ProcedimientoDTO searchProcCourt(String idJuzgado, HttpServletRequest request) {
		LOGGER.info("searchProcCourt() -> Entrada al servicio para obtener los procedimientos asigandos a juzgados");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ProcedimientoItem> procedimientos = null;
		ProcedimientoDTO procedimientoDTO = new ProcedimientoDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchProcCourt() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchProcCourt() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchProcCourt() / scsProcedimientosExtendsMapper.searchProcJudged() -> Entrada a scsProcedimientosExtendsMapper para obtener procedimientos asigandos a juzgados");

				procedimientos = scsJuzgadoProcedimientoExtendsMapper.searchProcJudged(usuario.getIdlenguaje(),
						idInstitucion, idJuzgado);

				LOGGER.info(
						"searchProcCourt() / scsProcedimientosExtendsMapper.searchProcJudged() -> Salida a scsProcedimientosExtendsMapper para obtener procedimientos asigandos a juzgados");

				if (procedimientos != null) {
					procedimientoDTO.setProcedimientosItems(procedimientos);
				}
			}

		}
		LOGGER.info(
				"searchProcCourt() -> Salida del servicio para obtener los procedimientos los procedimientos asigandos a juzgados");
		return procedimientoDTO;
	}

	@Override
	public UpdateResponseDTO associateProcess(ProcedimientoDTO procedimientoDTO, HttpServletRequest request) {
		LOGGER.info("associateProcess() ->  Entrada al servicio para asociar procedimientos a juzgados");

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
					"associateProcess() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"associateProcess() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// Eliminamos el los procedimientos que no se encuentren en la lista actual
					ScsJuzgadoprocedimientoExample scsJuzgadoprocedimientoExample = new ScsJuzgadoprocedimientoExample();
					scsJuzgadoprocedimientoExample.createCriteria()
							.andIdjuzgadoEqualTo(Long.valueOf(procedimientoDTO.getIdJuzgado()))
							.andIdinstitucionJuzgEqualTo(idInstitucion);

					LOGGER.info(
							"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoProcedimientoExtendsMapper para buscar los procedimientos asociados a un juzgado");

					List<ScsJuzgadoprocedimiento> procedimientosAntiguosList = scsJuzgadoProcedimientoExtendsMapper
							.selectByExample(scsJuzgadoprocedimientoExample);

					LOGGER.info(
							"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.selectByExample() -> Salida a scsJuzgadoProcedimientoExtendsMapper para buscar los procedimientos asociados a un juzgado");

					List<ScsJuzgadoprocedimiento> procedimientosEliminar = new ArrayList<ScsJuzgadoprocedimiento>();

					// Si hay temas que estan dados de alta, comprobamos que se encuentra en la
					// modificacion actual
					if (!procedimientosAntiguosList.isEmpty()) {

						for (ScsJuzgadoprocedimiento procedimientoAsignadoAntiguos : procedimientosAntiguosList) {
							boolean flag = false;

							LOOP: for (ProcedimientoItem p : procedimientoDTO.getProcedimientosItems()) {

								if (procedimientoAsignadoAntiguos.getIdprocedimiento().equals(p.getIdProcedimiento())) {
									flag = true;
									break LOOP;
								}
							}

							// Si no se encuentra en la lista actual la borramos
							if (!flag) {
								procedimientosEliminar.add(procedimientoAsignadoAntiguos);
							}
						}

						for (ScsJuzgadoprocedimiento procedimientoBaja : procedimientosEliminar) {

							LOGGER.info(
									"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.deleteByPrimaryKey() -> Entrada a scsJuzgadoProcedimientoExtendsMapper para eliminar un procedimiento");

							response = scsJuzgadoProcedimientoExtendsMapper.deleteByPrimaryKey(procedimientoBaja);

							LOGGER.info(
									"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.deleteByPrimaryKey() -> Salida a scsJuzgadoProcedimientoExtendsMapper para eliminar un procedimiento");
						}
					}

					// Añadimos partidos
					for (ProcedimientoItem procedimientoAdd : procedimientoDTO.getProcedimientosItems()) {

						// Para cada procedimiento comprobamos si ya existe la relacion
						ScsJuzgadoprocedimientoExample example = new ScsJuzgadoprocedimientoExample();
						example.createCriteria().andIdprocedimientoEqualTo(procedimientoAdd.getIdProcedimiento())
								.andIdjuzgadoEqualTo(Long.valueOf(procedimientoDTO.getIdJuzgado()))
								.andIdinstitucionJuzgEqualTo(idInstitucion);

						LOGGER.info(
								"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoProcedimientoExtendsMapper para buscar los procedimientos asociados a un juzgado");

						List<ScsJuzgadoprocedimiento> procedimientoList = scsJuzgadoProcedimientoExtendsMapper
								.selectByExample(example);

						LOGGER.info(
								"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.selectByExample() -> Salida a scsJuzgadoProcedimientoExtendsMapper para buscar los procedimientos asociados a un juzgado");

						// Si no existe la creamos
						if (procedimientoList.isEmpty()) {

							ScsJuzgadoprocedimiento procedimiento = new ScsJuzgadoprocedimiento();

							procedimiento.setFechabaja(null);
							procedimiento.setFechamodificacion(new Date());
							procedimiento.setUsumodificacion(usuario.getIdusuario().intValue());
							procedimiento.setIdinstitucion(Short.decode(procedimientoAdd.getIdInstitucion()));
							procedimiento.setIdinstitucionJuzg(idInstitucion);
							procedimiento.setIdjuzgado(Long.valueOf(procedimientoDTO.getIdJuzgado()));
							procedimiento.setIdprocedimiento(procedimientoAdd.getIdProcedimiento());

							LOGGER.info(
									"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.insert() -> Entrada a scsJuzgadoProcedimientoExtendsMapper para insertar un procedimiento");

							response = scsJuzgadoProcedimientoExtendsMapper.insert(procedimiento);

							LOGGER.info(
									"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.insert() -> Salida de scsJuzgadoProcedimientoExtendsMapper para insertar un procedimiento");

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
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("associateProcess() -> Salida del servicio para asociar procedimientos a juzgados");

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO asociarModulosAJuzgados(ProcedimientoDTO procedimientoDTO, HttpServletRequest request) {
		LOGGER.info("asociarModulosAJuzgados() ->  Entrada al servicio para asociar modulos masivamente a juzgados");

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
					"asociarModulosAJuzgados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"asociarModulosAJuzgados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// Añadimos partidos
					for (ProcedimientoItem procedimientoAdd : procedimientoDTO.getProcedimientosItems()) {

						// Para cada procedimiento comprobamos si ya existe la relacion
						ScsJuzgadoprocedimientoExample example = new ScsJuzgadoprocedimientoExample();
						example.createCriteria().andIdprocedimientoEqualTo(procedimientoAdd.getIdProcedimiento())
								.andIdjuzgadoEqualTo(Long.valueOf(procedimientoDTO.getIdJuzgado()))
								.andIdinstitucionJuzgEqualTo(idInstitucion);

						LOGGER.info(
								"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoProcedimientoExtendsMapper para buscar los procedimientos asociados a un juzgado");

						List<ScsJuzgadoprocedimiento> procedimientoList = scsJuzgadoProcedimientoExtendsMapper
								.selectByExample(example);

						LOGGER.info(
								"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.selectByExample() -> Salida a scsJuzgadoProcedimientoExtendsMapper para buscar los procedimientos asociados a un juzgado");

						// Si no existe la creamos
						if (procedimientoList.isEmpty()) {

							ScsJuzgadoprocedimiento procedimiento = new ScsJuzgadoprocedimiento();

							procedimiento.setFechabaja(null);
							procedimiento.setFechamodificacion(new Date());
							procedimiento.setUsumodificacion(usuario.getIdusuario().intValue());
							procedimiento.setIdinstitucion(idInstitucion);
							procedimiento.setIdinstitucionJuzg(idInstitucion);
							procedimiento.setIdjuzgado(Long.valueOf(procedimientoDTO.getIdJuzgado()));
							procedimiento.setIdprocedimiento(procedimientoAdd.getIdProcedimiento());

							LOGGER.info(
									"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.insert() -> Entrada a scsJuzgadoProcedimientoExtendsMapper para insertar un procedimiento");

							response = scsJuzgadoProcedimientoExtendsMapper.insert(procedimiento);

							LOGGER.info(
									"associateProcess() / scsJuzgadoProcedimientoExtendsMapper.insert() -> Salida de scsJuzgadoProcedimientoExtendsMapper para insertar un procedimiento");

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
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("associateProcess() -> Salida del servicio para asociar procedimientos a juzgados");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateCourt(JuzgadoItem juzgadoItem, HttpServletRequest request) {
		LOGGER.info("updateCourt() ->  Entrada al servicio para editar juzgado");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCourt() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCourt() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

//					ScsJuzgadoExample example = new ScsJuzgadoExample();
//					example.createCriteria().andNombreLike(juzgadoItem.getNombre())
//							.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull()
//							.andIdjuzgadoNotEqualTo(Long.decode(juzgadoItem.getIdJuzgado()));
//
//					LOGGER.info(
//							"updateCourt() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar juzgado");
//
//					List<ScsJuzgado> juzgadoList = scsJuzgadoExtendsMapper.selectByExample(example);
//
//					LOGGER.info(
//							"updateCourt() / scsJuzgadoExtendsMapper.selectByExample() -> Salida a scsJuzgadoExtendsMapper para buscar juzgado");
//
//					if (juzgadoList != null && juzgadoList.size() > 0) {
//						error.setCode(400);
//						error.setDescription("messages.jgr.maestros.gestionJuzgado.existeJuzgadoMismoNombre");
//
//					} else {

						ScsJuzgadoExample scsJuzgadoExample = new ScsJuzgadoExample();
						scsJuzgadoExample.createCriteria().andIdjuzgadoEqualTo(Long.valueOf(juzgadoItem.getIdJuzgado()))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updateCourt() / scsSubzonaExtendsMapper.selectByExample() -> Entrada a scsSubzonaExtendsMapper para buscar la subzona");

						List<ScsJuzgado> ScsJuzgadosList = scsJuzgadoExtendsMapper.selectByExample(scsJuzgadoExample);

						LOGGER.info(
								"updateCourt() / scsSubzonaExtendsMapper.selectByExample() -> Salida a scsSubzonaExtendsMapper para buscar la subzona");

						ScsJuzgado juzgado = ScsJuzgadosList.get(0);

						juzgado.setNombre(juzgadoItem.getNombre());
						juzgado.setFechamodificacion(new Date());
						juzgado.setUsumodificacion(usuario.getIdusuario().intValue());
						juzgado.setDomicilio(juzgadoItem.getDomicilio());
						juzgado.setCodigopostal(juzgadoItem.getCodigoPostal());
						juzgado.setIdpoblacion(juzgadoItem.getIdPoblacion());
						juzgado.setIdprovincia(juzgadoItem.getIdProvincia());
						juzgado.setTelefono1(juzgadoItem.getTelefono1());
						juzgado.setTelefono2(juzgadoItem.getTelefono2());
						juzgado.setFax1(juzgadoItem.getFax());
						juzgado.setCodigoext(juzgadoItem.getCodigoExt());
						juzgado.setCodigoprocurador(juzgadoItem.getCodigoProcurador());
						juzgado.setMovil(juzgadoItem.getMovil());
						juzgado.setCodigoejis(juzgadoItem.getCodigoEjis());
						
						if (juzgadoItem.getEsDecano() != null) {
							juzgado.setEsdecano(Short.valueOf(juzgadoItem.getEsDecano()));
						}

						juzgado.setCodigoext2(juzgadoItem.getCodigoExt2());
						juzgado.setEmail(juzgadoItem.getEmail());

						if (juzgadoItem.getIsCodigoEjis() != null) {
							juzgado.setIscodigoejis(Short.valueOf(juzgadoItem.getIsCodigoEjis()));
						}

						if (juzgadoItem.getFechaCodigoEjis() != null) {
							juzgado.setFechacodigoejis(juzgadoItem.getFechaCodigoEjis());
						}
						
						if (juzgadoItem.getVisibleMovil() != null) {
							juzgado.setVisiblemovil(Short.valueOf(juzgadoItem.getVisibleMovil()));
						}

						response = scsJuzgadoExtendsMapper.updateByPrimaryKey(juzgado);
//					}
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
		} else if(response == 1){
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateCourt() -> Salida del servicio para editar juzgado");

		return updateResponseDTO;
	}

}