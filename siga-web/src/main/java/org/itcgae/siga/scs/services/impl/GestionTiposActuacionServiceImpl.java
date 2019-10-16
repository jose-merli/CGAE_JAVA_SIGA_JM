package org.itcgae.siga.scs.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.AcreditacionDTO;
import org.itcgae.siga.DTO.scs.AcreditacionItem;
import org.itcgae.siga.DTO.scs.AreasDTO;
import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTO.scs.CosteFijoDTO;
import org.itcgae.siga.DTO.scs.CosteFijoItem;
import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTO.scs.TiposActuacionDTO;
import org.itcgae.siga.DTO.scs.TiposActuacionItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciasDTO;
import org.itcgae.siga.DTO.scs.ZonasItem;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroItem;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.InstitucionDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInfluencia;
import org.itcgae.siga.db.entities.CenInfluenciaExample;
import org.itcgae.siga.db.entities.CenPartidojudicial;
import org.itcgae.siga.db.entities.CenPartidojudicialExample;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.GenTablasMaestras;
import org.itcgae.siga.db.entities.GenTablasMaestrasExample;
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimiento;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuacionasistenciaExample;
import org.itcgae.siga.db.entities.ScsArea;
import org.itcgae.siga.db.entities.ScsMateriaExample;
import org.itcgae.siga.db.entities.ScsMateriajurisdiccion;
import org.itcgae.siga.db.entities.ScsMateriajurisdiccionExample;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsTipoactuacion;
import org.itcgae.siga.db.entities.ScsTipoactuacionExample;
import org.itcgae.siga.db.entities.ScsTipoactuacioncostefijo;
import org.itcgae.siga.db.entities.ScsTipoactuacioncostefijoExample;
import org.itcgae.siga.db.entities.ScsTipoasistenciaExample;
import org.itcgae.siga.db.entities.ScsTipoasistenciacolegio;
import org.itcgae.siga.db.entities.ScsTipoasistenciacolegioExample;
import org.itcgae.siga.db.entities.ScsTipoasistenciaguardia;
import org.itcgae.siga.db.entities.ScsTipoasistenciaguardiaExample;
import org.itcgae.siga.db.entities.ScsTipofundamentos;
import org.itcgae.siga.db.entities.ScsTipofundamentosExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsZona;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaMapper;
import org.itcgae.siga.db.mappers.ScsTipoactuacionMapper;
import org.itcgae.siga.db.mappers.ScsTipoasistenciacolegioMapper;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaguardiaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.CenInfluenciaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPartidasJudicialExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCostefijoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPartidasPresupuestariasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcedimientosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoAsistenciaColegioExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoactuacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoasistenciaExtendsMapper;
import org.itcgae.siga.scs.service.IGestionTiposActuacionService;
import org.itcgae.siga.scs.service.IGestionTiposAsistenciaService;
import org.itcgae.siga.scs.service.IModulosYBasesService;
import org.itcgae.siga.scs.service.IPartidasJudicialesService;
import org.itcgae.siga.scs.service.IPartidasPresupuestariasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionTiposActuacionServiceImpl implements IGestionTiposActuacionService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private ScsTipoactuacionExtendsMapper scsTipoactuacionExtendsMapper;

	@Autowired
	private ScsTipoactuacionMapper scsTipoActuacionMapper;
	
	@Autowired
	private ScsActuacionasistenciaMapper scsTipoActuacionAsistenciaMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public TiposActuacionDTO searchTiposActuacion(boolean historico, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TiposActuacionDTO tiposActuacionDTO = new TiposActuacionDTO();
		List<TiposActuacionItem> tiposActuacionItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				tiposActuacionItems = scsTipoactuacionExtendsMapper.searchTiposActuacion(historico,
						usuario.getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (tiposActuacionItems != null) {
					tiposActuacionDTO.setPartidasItem(tiposActuacionItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return tiposActuacionDTO;
	}

	@Override
	public ComboDTO getTiposActuacion(HttpServletRequest request) {
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
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				List<ComboItem> comboItems = scsTipoactuacionExtendsMapper.getTiposAsistencia(usuario.getIdlenguaje(),
						idInstitucion);

				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getPartidoJudicial() -> Salida del servicio para obtener combo partidos judiciales");
		return combo;
	}

	@Override
	public UpdateResponseDTO updateTiposActuaciones(TiposActuacionDTO tiposActuacionDTO,
			HttpServletRequest request) {
		LOGGER.info("updateTiposAsistencias() ->  Entrada al servicio para modificar un tipoasistencia");

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
					"updateTiposAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateTiposAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// Obtenermos fundamento de resolucion que queremos modificar
					// Obtenemos el fundamento de resolucion que queremos modificar
					for (TiposActuacionItem tiposActuacionItem : tiposActuacionDTO.getTiposActuacionItem()) {
												
						LOGGER.info(
								"updateTiposAsistencias() / scsTipoAsistenciaColegioMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");
						
						
						ScsTipoactuacionExample example = new ScsTipoactuacionExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoactuacionEqualTo(Short.parseShort(tiposActuacionItem.getIdtipoactuacion()));
							
						
						List<ScsTipoactuacion> scsTipoactuaciones = scsTipoActuacionMapper.selectByExample(example);

						LOGGER.info(
								"updateTiposAsistencias() / scsTipoAsistenciaColegioMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

						if (scsTipoactuaciones != null && scsTipoactuaciones.size() > 0) {

							ScsTipoactuacion scsTipoActuacion = scsTipoactuaciones.get(0);
							
							// Buscamos si existe una descripcion que sea igual en fundamentos q no sea el
							// propio

							GenRecursosCatalogosExample exampleRecursosRepetidos = new GenRecursosCatalogosExample();
							exampleRecursosRepetidos.createCriteria()
									.andDescripcionEqualTo(tiposActuacionItem.getDescripciontipoactuacion())
									.andCampotablaEqualTo("DESCRIPCION")
									.andNombretablaEqualTo("SCS_TIPOACTUACION")
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdrecursoNotEqualTo(scsTipoActuacion.getDescripcion());

							List<GenRecursosCatalogos> recursosRepetidos = genRecursosCatalogosExtendsMapper
									.selectByExample(exampleRecursosRepetidos);

							
							if (recursosRepetidos != null && recursosRepetidos.size() > 0) {
								error.setCode(400);
								error.setDescription(
										"messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion");
							} else {

								// Si no se repite se modifica tanto la el fundamento como la descripcion del
								// fundamento en recurso catalago

								GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
								exampleRecursos.createCriteria().andCampotablaEqualTo("DESCRIPCION")
										.andNombretablaEqualTo("SCS_TIPOACTUACION")
										.andIdinstitucionEqualTo(idInstitucion)
										.andIdrecursoEqualTo(scsTipoActuacion.getDescripcion());

								LOGGER.info(
										"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Entrada a genRecursosCatalogosExtendsMapper para buscar descripcion del fundamento resolucion");

								List<GenRecursosCatalogos> recursos = genRecursosCatalogosExtendsMapper
										.selectByExample(exampleRecursos);

								LOGGER.info(
										"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Salida a genRecursosCatalogosExtendsMapper para buscar descripcion del fundamento resolucion");

								GenRecursosCatalogos recursoFundamento = recursos.get(0);

								if (!tiposActuacionItem.getDescripciontipoactuacion()
										.equals(recursoFundamento.getDescripcion())) {
									recursoFundamento.setDescripcion(tiposActuacionItem.getDescripciontipoactuacion());
									recursoFundamento.setFechamodificacion(new Date());
									recursoFundamento.setUsumodificacion(usuario.getIdusuario());
									recursoFundamento.setIdinstitucion(idInstitucion);

									LOGGER.info(
											"updateTiposAsistencias() / genRecursosCatalogosExtendsMapper.updateByExample() -> Entrada a genRecursosCatalogosExtendsMapper para modificar un fundamento de resolucion");

									response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(recursoFundamento);

									LOGGER.info(
											"updateTiposAsistencias() / genRecursosCatalogosExtendsMapper.updateByExample() -> Salida de genRecursosCatalogosExtendsMapper para modificar un fundamento de resolucion");

									updateRestoIdiomas(recursoFundamento);
								}
								

								String[] multiSelectTipos = tiposActuacionItem.getIdtipoasistencia().trim().split(",");
								ScsTipoactuacionExample example2 = new ScsTipoactuacionExample();
								example2.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoactuacionEqualTo(Short.parseShort(tiposActuacionItem.getIdtipoactuacion()));
								scsTipoActuacionMapper.deleteByExample(example2);
								
//								
								
								
								if (multiSelectTipos[0] != "")
									for (String idtiposguardia : multiSelectTipos) {
										BigDecimal importe = new BigDecimal(tiposActuacionItem.getImporte());
										BigDecimal importemaximo = new BigDecimal(tiposActuacionItem.getImportemaximo());
										scsTipoActuacion.setImporte(importe);
										scsTipoActuacion.setImportemaximo(importemaximo);												
										scsTipoActuacion.setIdtipoasistencia(Short.parseShort(idtiposguardia));
										scsTipoActuacion.setFechamodificacion(new Date());
										scsTipoActuacion.setIdtipoactuacion(Short.parseShort(tiposActuacionItem.getIdtipoactuacion()));
										scsTipoActuacion.setUsumodificacion(usuario.getIdusuario());
										scsTipoActuacion.setIdinstitucion(idInstitucion);
										response = scsTipoActuacionMapper.insert(scsTipoActuacion);
									}															
								
//									response = scsTipoActuacionMapper.insert(scsTipoActuacion2);
								LOGGER.info(
										"updateTiposAsistencias() / scsTipofundamentosExtendsMapper.updateByExample() -> Entrada a scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");

								LOGGER.info(
										"updateTiposAsistencias() / scsTipofundamentosExtendsMapper.updateByExample() -> Salida de scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");
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

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateTiposAsistencias() -> Salida del servicio para editar un tipo asistencia");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO deleteTipoActuacion(TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (TiposActuacionItem tiposActuacionItem : tiposActuacionDTO.getTiposActuacionItem()) {

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

						ScsTipoactuacionExample example = new ScsTipoactuacionExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoactuacionEqualTo(Short.parseShort(tiposActuacionItem.getIdtipoactuacion()));

						List<ScsTipoactuacion> scsTipoactuacionExample = scsTipoActuacionMapper
								.selectByExample(example);					
						
						if (scsTipoactuacionExample != null && scsTipoactuacionExample.size() > 0) {
							
							ScsTipoactuacion scsTipoActuaciones = scsTipoactuacionExample.get(0);
							scsTipoActuaciones.setFechabaja(new Date());
							scsTipoActuaciones.setFechamodificacion(new Date());
							scsTipoActuaciones.setUsumodificacion(usuarios.get(0).getIdusuario());

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Entrada a scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");

							response = scsTipoActuacionMapper.updateByPrimaryKey(scsTipoActuaciones);

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Salida de scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");
							}
						
					}

				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}

		}
		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO activateTipoActuacion(TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (TiposActuacionItem tiposActuacionItem : tiposActuacionDTO.getTiposActuacionItem()) {

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

						ScsTipoactuacionExample example = new ScsTipoactuacionExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoactuacionEqualTo(Short.parseShort(tiposActuacionItem.getIdtipoactuacion()));

						List<ScsTipoactuacion> scsTipoactuacionExample = scsTipoActuacionMapper
								.selectByExample(example);	
						if (scsTipoactuacionExample != null && scsTipoactuacionExample.size() > 0) {

							ScsTipoactuacion scsTipoActuaciones = scsTipoactuacionExample.get(0);
							scsTipoActuaciones.setFechabaja(null);
							scsTipoActuaciones.setFechamodificacion(new Date());
							scsTipoActuaciones.setUsumodificacion(usuarios.get(0).getIdusuario());

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Entrada a scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");

							response = scsTipoActuacionMapper.updateByPrimaryKey(scsTipoActuaciones);

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Salida de scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");
						}
						
					}

				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}

		}
		return updateResponseDTO;
	}	
	
	
	@Override
	public InsertResponseDTO createTiposActuacion(TiposActuacionItem tiposActuacionItem,
			HttpServletRequest request) {
		LOGGER.info("createFundamentosResolucion() ->  Entrada al servicio para crear un fundamento resolucion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Integer idTipoActuacion = null;

		ScsTipoactuacion scsTipoactuacion = null;
		
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createTiposAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createTiposAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					if (tiposActuacionItem != null) {							
							ScsTipoactuacionExample example3 = new ScsTipoactuacionExample();
							example3.createCriteria().andIdinstitucionEqualTo(idInstitucion);
					
						// Buscamos si se encuentra la descripcion del nuevo fundamento
						GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
						exampleRecursos.createCriteria()
								.andDescripcionEqualTo(tiposActuacionItem.getDescripciontipoactuacion())
								.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("SCS_TIPOACTUACION")
								.andIdinstitucionEqualTo(idInstitucion);

						List<GenRecursosCatalogos> recursos = genRecursosCatalogosExtendsMapper
								.selectByExample(exampleRecursos);

						if (recursos != null && recursos.size() > 0) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion");

						} else {

							scsTipoactuacion = new ScsTipoactuacion();
							GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

							genRecursosCatalogo.setCampotabla("DESCRIPCION");
							genRecursosCatalogo.setDescripcion(tiposActuacionItem.getDescripciontipoactuacion());
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

							genRecursosCatalogo.setIdrecursoalias("SCS_TIPOACTUACION.descripcion." + idInstitucion
									+ "." + genRecursosCatalogo.getIdrecurso());

							genRecursosCatalogo.setNombretabla("SCS_TIPOACTUACION");
							genRecursosCatalogo.setUsumodificacion(usuario.getUsumodificacion());

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Entrada a genRecursosCatalogosExtendsMapper para guardar descripcion");

							response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Salida a genRecursosCatalogosExtendsMapper para guardar descripcion");

							scsTipoactuacion.setDescripcion(genRecursosCatalogo.getIdrecurso());

							insertarRestoIdiomas(genRecursosCatalogo);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

							NewIdDTO idF = scsTipoactuacionExtendsMapper.getIdTipoactuacion(idInstitucion);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

							if (idF == null) {
								scsTipoactuacion.setIdtipoactuacion((short) 1);
							} else {
								idTipoActuacion = (Integer.parseInt(idF.getNewId()) + 1);
								scsTipoactuacion.setIdtipoactuacion(Short.valueOf(idTipoActuacion.toString()));
							}						

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Entrada a scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");
								
							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Salida de scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");
							
							String[] multiSelectTipos = tiposActuacionItem.getIdtipoasistencia().trim().split(",");


							if (multiSelectTipos[0] != "")
								for (String idtiposasistencias : multiSelectTipos) {
									BigDecimal importe = new BigDecimal(tiposActuacionItem.getImporte());
									BigDecimal importemaximo = new BigDecimal(tiposActuacionItem.getImportemaximo());
									scsTipoactuacion.setImporte(importe);
									scsTipoactuacion.setImportemaximo(importemaximo);
									scsTipoactuacion.setFechamodificacion(new Date());
									scsTipoactuacion.setUsumodificacion(usuarios.get(0).getIdusuario());
									scsTipoactuacion.setIdinstitucion(idInstitucion);
									scsTipoactuacion.setIdtipoactuacion(Short.parseShort(idTipoActuacion.toString()));
									scsTipoactuacion.setIdtipoasistencia(Short.parseShort(idtiposasistencias));
									scsTipoActuacionMapper.insert(scsTipoactuacion);
								}
								
						}

					}
					}
				 catch (Exception e) {
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
			insertResponseDTO.setStatus(SigaConstants.OK);
			insertResponseDTO.setId(String.valueOf(scsTipoactuacion.getIdtipoactuacion()));

		}

		insertResponseDTO.setError(error);

		LOGGER.info("createFundamentosResolucion() -> Salida del servicio para crear un fundamento de resolucion");

		}
		return insertResponseDTO;
	}
				
	
	private int insertarRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

	private int updateRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

}
