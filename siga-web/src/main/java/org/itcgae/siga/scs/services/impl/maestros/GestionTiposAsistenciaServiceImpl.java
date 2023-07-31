package org.itcgae.siga.scs.services.impl.maestros;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
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
import org.itcgae.siga.DTOs.scs.AcreditacionDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionItem;
import org.itcgae.siga.DTOs.scs.AreasDTO;
import org.itcgae.siga.DTOs.scs.AreasItem;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoItem;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.scs.PartidasDTO;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciasDTO;
import org.itcgae.siga.DTOs.scs.ZonasItem;
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
import org.itcgae.siga.db.services.scs.mappers.ScsTipoasistenciaExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IGestionTiposAsistenciaService;
import org.itcgae.siga.scs.services.maestros.IModulosYBasesService;
import org.itcgae.siga.scs.services.maestros.IPartidasJudicialesService;
import org.itcgae.siga.scs.services.maestros.IPartidasPresupuestariasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionTiposAsistenciaServiceImpl implements IGestionTiposAsistenciaService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private ScsTipoasistenciaExtendsMapper scsTipoasistenciaExtendsMapper;

	@Autowired
	private ScsTipoasistenciacolegioMapper scsTipoAsistenciaColegioMapper;
	
	@Autowired
	private ScsTipoactuacionMapper scsTipoActuacionMapper;
	
	@Autowired
	private ScsTipoasistenciaguardiaMapper scsTipoAsistenciaGuardiaMapper;

	@Autowired
	private ScsTipoAsistenciaColegioExtendsMapper scsTipoAsistenciaColegioExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;

	@Autowired
	private CenPartidasJudicialExtendsMapper cenPartidaJudicialExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public TiposAsistenciasDTO searchTiposAsistencia(boolean historico, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TiposAsistenciasDTO tiposAsistenciasDTO = new TiposAsistenciasDTO();
		List<TiposAsistenciaItem> tiposAsistenciaItems = null;

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

				tiposAsistenciaItems = scsTipoasistenciaExtendsMapper.searchTiposAsistencia(historico,
						usuario.getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (tiposAsistenciaItems != null) {
					tiposAsistenciasDTO.setPartidasItem(tiposAsistenciaItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return tiposAsistenciasDTO;
	}

	@Override
	public ComboDTO getTiposGuardia(HttpServletRequest request) {
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

				List<ComboItem> comboItems = scsTipoasistenciaExtendsMapper.getTiposGuardia(usuario.getIdlenguaje(),
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
	public ComboDTO getTiposGuardia2(HttpServletRequest request) {
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

				List<ComboItem> comboItems = scsTipoasistenciaExtendsMapper.getTiposGuardia2(usuario.getIdlenguaje(),
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
	public ComboDTO getComboAsistencia(HttpServletRequest request) {
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

				List<ComboItem> comboItems = scsTipoasistenciaExtendsMapper.getComboAsistencia(usuario.getIdlenguaje(),
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
	public UpdateResponseDTO updateTiposAsistencias(TiposAsistenciasDTO tiposAsistenciasDTO,
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
					for (TiposAsistenciaItem tiposAsistenciaItem : tiposAsistenciasDTO.getTiposAsistenciasItem()) {
																				
						String pordefectovalor = tiposAsistenciaItem.getPordefecto();
						List<TiposAsistenciaItem> tiposAsistenciaItems = null;
						
						if(pordefectovalor.equals("1")) {
							tiposAsistenciaItems =	scsTipoasistenciaExtendsMapper.searchTiposAsistenciaPorDefecto(true,
									usuario.getIdlenguaje(), idInstitucion);

							for(TiposAsistenciaItem asistenciasActuales : tiposAsistenciaItems) {
								ScsTipoasistenciacolegio colegio = new ScsTipoasistenciacolegio();
								
								ScsTipoasistenciacolegioExample asistenciasNuevas = new ScsTipoasistenciacolegioExample();
								asistenciasNuevas.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoasistenciacolegioEqualTo(Short.parseShort(asistenciasActuales.getIdtipoasistenciacolegio())).andPordefectoEqualTo(Short.parseShort("1"));
								
								if(tiposAsistenciaItem.getIdtipoasistenciacolegio() == asistenciasActuales.getIdtipoasistenciacolegio()) {
									colegio.setPordefecto(Short.parseShort("1"));
								}else {
									colegio.setPordefecto(Short.parseShort("0"));
								}
								colegio.setIdinstitucion(idInstitucion);
								colegio.setIdtipoasistenciacolegio(Short.parseShort(asistenciasActuales.getIdtipoasistenciacolegio()));
								colegio.setUsumodificacion(usuario.getIdusuario());
								colegio.setFechamodificacion(new Date());
								scsTipoAsistenciaColegioMapper.updateByExampleSelective(colegio, asistenciasNuevas);						
								}
						}
							
						
						LOGGER.info(
								"updateTiposAsistencias() / scsTipoAsistenciaColegioMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");
						
						
						ScsTipoasistenciacolegioExample example = new ScsTipoasistenciacolegioExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoasistenciacolegioEqualTo(
										Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));
						
						List<ScsTipoasistenciacolegio> scsTipoasistenciacolegios = scsTipoAsistenciaColegioMapper
								.selectByExample(example);

						LOGGER.info(
								"updateTiposAsistencias() / scsTipoAsistenciaColegioMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

						if (scsTipoasistenciacolegios != null && scsTipoasistenciacolegios.size() > 0) {

							ScsTipoasistenciacolegio scsTipoAsistencia = scsTipoasistenciacolegios.get(0);

							// Buscamos si existe una descripcion que sea igual en fundamentos q no sea el
							// propio

							GenRecursosCatalogosExample exampleRecursosRepetidos = new GenRecursosCatalogosExample();
							exampleRecursosRepetidos.createCriteria()
									.andDescripcionEqualTo(tiposAsistenciaItem.getTipoasistencia())
									.andCampotablaEqualTo("DESCRIPCION")
									.andNombretablaEqualTo("SCS_TIPOASISTENCIACOLEGIO")
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdrecursoNotEqualTo(scsTipoAsistencia.getDescripcion());

							List<GenRecursosCatalogos> recursosRepetidos = genRecursosCatalogosExtendsMapper
									.selectByExample(exampleRecursosRepetidos);

							// Si la descripcion se repite
							if (recursosRepetidos != null && recursosRepetidos.size() > 0) {
								error.setCode(400);
								error.setDescription(
										"messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion");
							} else {

								// Si no se repite se modifica tanto la el fundamento como la descripcion del
								// fundamento en recurso catalago

								GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
								exampleRecursos.createCriteria().andCampotablaEqualTo("DESCRIPCION")
										.andNombretablaEqualTo("SCS_TIPOASISTENCIACOLEGIO")
										.andIdinstitucionEqualTo(idInstitucion)
										.andIdrecursoEqualTo(scsTipoAsistencia.getDescripcion());

								LOGGER.info(
										"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Entrada a genRecursosCatalogosExtendsMapper para buscar descripcion del fundamento resolucion");

								List<GenRecursosCatalogos> recursos = genRecursosCatalogosExtendsMapper
										.selectByExample(exampleRecursos);

								LOGGER.info(
										"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Salida a genRecursosCatalogosExtendsMapper para buscar descripcion del fundamento resolucion");

								GenRecursosCatalogos recursoFundamento = recursos.get(0);

								if (!tiposAsistenciaItem.getTipoasistencia()
										.equals(recursoFundamento.getDescripcion())) {
									recursoFundamento.setDescripcion(tiposAsistenciaItem.getTipoasistencia());
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

								BigDecimal importe = new BigDecimal(tiposAsistenciaItem.getImporte());
								BigDecimal importemaximo = new BigDecimal(tiposAsistenciaItem.getImportemaximo());
								scsTipoAsistencia.setImporte(importe);
								scsTipoAsistencia.setImportemaximo(importemaximo);
								scsTipoAsistencia.setPordefecto(Short.parseShort(tiposAsistenciaItem.getPordefecto()));
								scsTipoAsistencia
										.setVisiblemovil(Short.parseShort(tiposAsistenciaItem.getVisiblemovil()));
								scsTipoAsistencia.setFechamodificacion(new Date());
								scsTipoAsistencia.setUsumodificacion(usuario.getIdusuario());
								scsTipoAsistencia.setIdinstitucion(idInstitucion);
								scsTipoAsistencia.setIdtipoasistenciacolegio(
										Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));
								response = scsTipoAsistenciaColegioMapper.updateByPrimaryKey(scsTipoAsistencia);

								String[] multiSelectTipos = tiposAsistenciaItem.getIdtiposguardia().trim().split(",");
								ScsTipoasistenciaguardiaExample example2 = new ScsTipoasistenciaguardiaExample();
								example2.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdtipoasistenciacolegioEqualTo(
												Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));

								List<ScsTipoasistenciaguardia> scsTipoasistenciaguardiaAnteriores = scsTipoAsistenciaGuardiaMapper
										.selectByExample(example2);

								for (ScsTipoasistenciaguardia tiposguardia : scsTipoasistenciaguardiaAnteriores) {
									scsTipoAsistenciaGuardiaMapper.deleteByPrimaryKey(tiposguardia);
								}

								if (multiSelectTipos[0] != "")
									for (String idtiposguardia : multiSelectTipos) {
										ScsTipoasistenciaguardia tipoAsistenciaGuardia = new ScsTipoasistenciaguardia();
										tipoAsistenciaGuardia.setFechamodificacion(new Date());
										tipoAsistenciaGuardia.setIdinstitucion(idInstitucion);
										tipoAsistenciaGuardia.setIdtipoasistenciacolegio(
												Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));
										tipoAsistenciaGuardia.setUsumodificacion(usuarios.get(0).getIdusuario());
										tipoAsistenciaGuardia.setIdtipoguardia(Short.parseShort(idtiposguardia));
										scsTipoAsistenciaGuardiaMapper.insert(tipoAsistenciaGuardia);
									}
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
	public UpdateResponseDTO deleteTipoAsitencia(TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request) {
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

					for (TiposAsistenciaItem tiposAsistenciaItem : tiposAsistenciasDTO.getTiposAsistenciasItem()) {

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

						ScsTipoasistenciacolegioExample example = new ScsTipoasistenciacolegioExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoasistenciacolegioEqualTo(
										Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));

						List<ScsTipoasistenciacolegio> scsTipoasistenciacolegios = scsTipoAsistenciaColegioMapper
								.selectByExample(example);
						
						ScsTipoactuacionExample example2 = new ScsTipoactuacionExample();
						example2.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoasistenciaEqualTo(Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));
						
						List<ScsTipoactuacion> scsTipoActuacion = scsTipoActuacionMapper.selectByExample(example2);
						
						if (scsTipoasistenciacolegios != null && scsTipoasistenciacolegios.size() > 0) {
							if(scsTipoActuacion != null && scsTipoActuacion.size() > 0) {
								error.setCode(400);
								error.setDescription("general.message.nosepuedeeliminardependetipoactuacion");
								updateResponseDTO.setStatus(SigaConstants.KO);
							}else {
							ScsTipoasistenciacolegio scsTipoAsistencia = scsTipoasistenciacolegios.get(0);
							scsTipoAsistencia.setPordefecto(Short.parseShort("0"));
							scsTipoAsistencia.setFechaBaja(new Date());
							scsTipoAsistencia.setFechamodificacion(new Date());
							scsTipoAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Entrada a scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");

							response = scsTipoAsistenciaColegioMapper.updateByPrimaryKey(scsTipoAsistencia);

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Salida de scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");
							}
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
	public UpdateResponseDTO activateTipoAsitencia(TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request) {
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

					for (TiposAsistenciaItem tiposAsistenciaItem : tiposAsistenciasDTO.getTiposAsistenciasItem()) {

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

						ScsTipoasistenciacolegioExample example = new ScsTipoasistenciacolegioExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoasistenciacolegioEqualTo(
										Short.parseShort(tiposAsistenciaItem.getIdtipoasistenciacolegio()));

						List<ScsTipoasistenciacolegio> scsTipoasistenciacolegios = scsTipoAsistenciaColegioMapper
								.selectByExample(example);
						if (scsTipoasistenciacolegios != null && scsTipoasistenciacolegios.size() > 0) {

							ScsTipoasistenciacolegio scsTipoAsistencia = scsTipoasistenciacolegios.get(0);

							scsTipoAsistencia.setFechaBaja(null);
							scsTipoAsistencia.setFechamodificacion(new Date());
							scsTipoAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Entrada a scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");

							response = scsTipoAsistenciaColegioMapper.updateByPrimaryKey(scsTipoAsistencia);

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
	public InsertResponseDTO createTiposAsistencia(TiposAsistenciaItem tiposAsistenciaItem,
			HttpServletRequest request) {
		LOGGER.info("createFundamentosResolucion() ->  Entrada al servicio para crear un fundamento resolucion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Integer idTipoAsistencia = null;

		ScsTipoasistenciacolegio scsTipoasistenciacolegio = null;
		
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

					if (tiposAsistenciaItem != null) {							
							ScsTipoasistenciacolegioExample example3 = new ScsTipoasistenciacolegioExample();
							example3.createCriteria().andIdinstitucionEqualTo(idInstitucion);
							
							String pordefectovalor = tiposAsistenciaItem.getPordefecto();
							List<TiposAsistenciaItem> tiposAsistenciaItems = null;
							
							if(pordefectovalor.equals("1")) {
								tiposAsistenciaItems =	scsTipoasistenciaExtendsMapper.searchTiposAsistenciaPorDefecto(true,
										usuario.getIdlenguaje(), idInstitucion);
								for(TiposAsistenciaItem asistenciasActuales : tiposAsistenciaItems) {
									ScsTipoasistenciacolegio colegio = new ScsTipoasistenciacolegio();
									
									ScsTipoasistenciacolegioExample asistenciasNuevas = new ScsTipoasistenciacolegioExample();
									asistenciasNuevas.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdtipoasistenciacolegioEqualTo(Short.parseShort(asistenciasActuales.getIdtipoasistenciacolegio())).andPordefectoEqualTo(Short.parseShort("1"));

									if(tiposAsistenciaItem.getIdtipoasistenciacolegio() == asistenciasActuales.getIdtipoasistenciacolegio()) {
										colegio.setPordefecto(Short.parseShort("1"));
									}else {
										colegio.setPordefecto(Short.parseShort("0"));
									}
									colegio.setIdinstitucion(idInstitucion);
									colegio.setIdtipoasistenciacolegio(Short.parseShort(asistenciasActuales.getIdtipoasistenciacolegio()));
									colegio.setUsumodificacion(usuario.getIdusuario());
									colegio.setFechamodificacion(new Date());
									scsTipoAsistenciaColegioMapper.updateByExampleSelective(colegio, asistenciasNuevas);						
									}
							}
							
						
						// Buscamos si se encuentra la descripcion del nuevo fundamento
						GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
						exampleRecursos.createCriteria()
								.andDescripcionEqualTo(tiposAsistenciaItem.getTipoasistencia())
								.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("SCS_TIPOASISTENCIACOLEGIO")
								.andIdinstitucionEqualTo(idInstitucion);

						List<GenRecursosCatalogos> recursos = genRecursosCatalogosExtendsMapper
								.selectByExample(exampleRecursos);

						if (recursos != null && recursos.size() > 0) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion");

						} else {

							scsTipoasistenciacolegio = new ScsTipoasistenciacolegio();
							GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

							genRecursosCatalogo.setCampotabla("DESCRIPCION");
							genRecursosCatalogo.setDescripcion(tiposAsistenciaItem.getTipoasistencia());
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

							genRecursosCatalogo.setIdrecursoalias("SCS_TIPOASISTENCIACOLEGIO.descripcion." + idInstitucion
									+ "." + genRecursosCatalogo.getIdrecurso());

							genRecursosCatalogo.setNombretabla("SCS_TIPOASISTENCIACOLEGIO");
							genRecursosCatalogo.setUsumodificacion(usuario.getUsumodificacion());

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Entrada a genRecursosCatalogosExtendsMapper para guardar descripcion");

							response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Salida a genRecursosCatalogosExtendsMapper para guardar descripcion");

							scsTipoasistenciacolegio.setDescripcion(genRecursosCatalogo.getIdrecurso());

							insertarRestoIdiomas(genRecursosCatalogo);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

							NewIdDTO idF = scsTipoAsistenciaColegioExtendsMapper.getIdTipoasistenciacolegio(idInstitucion);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

							if (idF == null) {
								scsTipoasistenciacolegio.setIdtipoasistenciacolegio((short) 1);
							} else {
								idTipoAsistencia = (Integer.parseInt(idF.getNewId()) + 1);
								scsTipoasistenciacolegio.setIdtipoasistenciacolegio(Short.valueOf(idTipoAsistencia.toString()));
							}
							BigDecimal importe = new BigDecimal(tiposAsistenciaItem.getImporte());
							BigDecimal importemaximo = new BigDecimal(tiposAsistenciaItem.getImportemaximo());
							scsTipoasistenciacolegio.setImporte(importe);
							scsTipoasistenciacolegio.setImportemaximo(importemaximo);
							scsTipoasistenciacolegio.setPordefecto(Short.parseShort(tiposAsistenciaItem.getPordefecto()));
							scsTipoasistenciacolegio.setVisiblemovil(Short.parseShort(tiposAsistenciaItem.getVisiblemovil()));
							scsTipoasistenciacolegio.setFechamodificacion(new Date());
							scsTipoasistenciacolegio.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsTipoasistenciacolegio.setIdinstitucion(idInstitucion);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Entrada a scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");

							response = scsTipoAsistenciaColegioMapper.insert(scsTipoasistenciacolegio);
							
							
							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Salida de scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");
							
							String[] multiSelectTipos = tiposAsistenciaItem.getIdtiposguardia().trim().split(",");


							if (multiSelectTipos[0] != "")
								for (String idtiposguardia : multiSelectTipos) {
									ScsTipoasistenciaguardia tipoAsistenciaGuardia = new ScsTipoasistenciaguardia();
									tipoAsistenciaGuardia.setFechamodificacion(new Date());
									tipoAsistenciaGuardia.setIdinstitucion(idInstitucion);
									tipoAsistenciaGuardia.setIdtipoasistenciacolegio(Short.parseShort(idTipoAsistencia.toString()));
									tipoAsistenciaGuardia.setUsumodificacion(usuarios.get(0).getIdusuario());
									tipoAsistenciaGuardia.setIdtipoguardia(Short.parseShort(idtiposguardia));
									scsTipoAsistenciaGuardiaMapper.insert(tipoAsistenciaGuardia);
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
			insertResponseDTO.setId(String.valueOf(scsTipoasistenciacolegio.getIdtipoasistenciacolegio()));

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
