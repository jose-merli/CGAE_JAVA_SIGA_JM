package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaKey;
import org.itcgae.siga.db.entities.ScsContrariosasistencia;
import org.itcgae.siga.db.entities.ScsContrariosasistenciaExample;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaExample;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsContrariosejgExample;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaExample;
import org.itcgae.siga.db.entities.ScsDelitosasistencia;
import org.itcgae.siga.db.entities.ScsDelitosasistenciaExample;
import org.itcgae.siga.db.entities.ScsDelitosdesigna;
import org.itcgae.siga.db.entities.ScsDelitosdesignaExample;
import org.itcgae.siga.db.entities.ScsDelitosejg;
import org.itcgae.siga.db.entities.ScsDelitosejgExample;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsDesignaprocurador;
import org.itcgae.siga.db.entities.ScsDesignaprocuradorExample;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.entities.ScsDesignasletradoExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosejgMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaprocuradorMapper;
import org.itcgae.siga.db.mappers.ScsDesignasletradoMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.mappers.ScsSojMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsContrariosejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaAsuntosService;
import org.itcgae.siga.scs.services.impl.justiciables.GestionJusticiableServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusquedaAsuntosServiceImpl implements BusquedaAsuntosService {

	private Logger LOGGER = Logger.getLogger(BusquedaAsuntosServiceImpl.class);
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosdesignaMapper;
	
	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsDesignasletradoMapper scsDesignasletradoMapper;
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsDesignaMapper scsDesignaMapper;
	
	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private ScsSojMapper scsSojMapper;
	
	@Autowired
	private ScsDefendidosdesignaMapper scsDefendidosdesignaMapper;
	
	@Autowired
	private ScsPersonajgMapper scsPersonajgMapper;
	
	@Autowired
	private ScsComisariaExtendsMapper scsComisariaExtendsMapper;

	@Autowired
	private ScsContrariosejgExtendsMapper scsContrariosejgExtendsMapper;

	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;
	
	@Autowired
	private ScsDelitosdesignaMapper scsDelitosdesignaMapper;
	
	@Autowired
	private ScsAsistenciaMapper scsAsistenciaMapper;
	
	@Autowired
	private ScsContrariosejgMapper scsContrariosejgMapper;
	
	@Autowired
	private ScsContrariosasistenciaMapper scsContrariosasistenciaMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Autowired
	private ScsDesignaprocuradorMapper scsDesignaProcuradorMapper;
	
	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private ScsDelitosejgMapper scsDelitosejgMapper;
	
	@Autowired 
	private ScsDelitosasistenciaMapper scsDelitosasistenciaMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private ScsSojExtendsMapper scsSojExtendsMapper;

	@Autowired
	private GestionJusticiableServiceImpl gestionJusticiableServiceImpl;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;

	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosEJG(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = 200;
				}

				// buscamos la idpersona del colegiado
				if (asuntosJusticiableItem.getnColegiado() != null
						&& !asuntosJusticiableItem.getnColegiado().trim().isEmpty()) {
					String idPersonaColegiado = getIdPersonaColegiado(asuntosJusticiableItem.getnColegiado(),
							asuntosJusticiableItem.getIdInstitucion());

					if (!idPersonaColegiado.isEmpty()) {
						asuntosJusticiableItem.setIdPersonaColegiado(idPersonaColegiado);
					}
				}

				LOGGER.info(
						"searchClaveAsuntosEJG() / scsEjgExtendsMapper.searchClaveAsuntosEJG() -> Entrada a scsEjgExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItems = scsEjgExtendsMapper.searchClaveAsuntosEJG(asuntosJusticiableItem, tamMaximo,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchClaveAsuntosEJG() / scsEjgExtendsMapper.searchClaveAsuntosEJG() -> Salida a scsEjgExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null && asuntosJusticiableItems.size() > 0) {
					// vamos a crear los datos de interes
//					for (AsuntosJusticiableItem data : asuntosJusticiableItems) {
//						String datoInteres = "";
//
//						if (data.getTipo() != null && !data.getTipo().isEmpty()) {
//							datoInteres += "<b>Tipo: </b>" + data.getTipo();
//						}
//
//						if (data.getNumProcedimiento() != null && !data.getNumProcedimiento().isEmpty()) {
//							if (!datoInteres.isEmpty()) {
//								datoInteres += "<p>";
//							}
//
//							datoInteres += "<b>Núm. Procedimiento: </b>" + data.getNumProcedimiento();
//						}
//
//						if (data.getNig() != null && !data.getNig().isEmpty()) {
//							if (!datoInteres.isEmpty()) {
//								datoInteres += "<p>";
//							}
//
//							datoInteres += "<p><b>NIG: </b>" + data.getNig();
//						}
//
//						data.setDatosInteres(datoInteres);
//					}

					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
					if (asuntosJusticiableDTO.getAsuntosJusticiableItems() != null && tamMaximo != null
							&& asuntosJusticiableDTO.getAsuntosJusticiableItems().size() >= tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						asuntosJusticiableDTO.setError(error);
					}
				}
			}
		}

		LOGGER.info("searchClaveAsuntosEJG() -> Salida del servicio para obtener los Asistencias");
		return asuntosJusticiableDTO;
	}

	private String getIdPersonaColegiado(String nColegiado, String idInstitucion) {
		String result = "";

		CenColegiadoExample example = new CenColegiadoExample();

		example.createCriteria().andIdinstitucionEqualTo(Short.parseShort(idInstitucion))
				.andNcolegiadoEqualTo(nColegiado);

		example.or().andIdinstitucionEqualTo(Short.parseShort(idInstitucion)).andNcomunitarioEqualTo(nColegiado);

		List<CenColegiado> colegiado = cenColegiadoMapper.selectByExample(example);

		if (colegiado.size() > 0) {
			result = colegiado.get(0).getIdpersona().toString();
		}

		return result;
	}

	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosAsistencias(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = 200;
				}

				// buscamos la idpersona del colegiado
				if (asuntosJusticiableItem.getnColegiado() != null
						&& !asuntosJusticiableItem.getnColegiado().trim().isEmpty()) {
					String idPersonaColegiado = getIdPersonaColegiado(asuntosJusticiableItem.getnColegiado(),
							asuntosJusticiableItem.getIdInstitucion());

					if (!idPersonaColegiado.isEmpty()) {
						asuntosJusticiableItem.setIdPersonaColegiado(idPersonaColegiado);
					}
				}

				LOGGER.info(
						"searchClaveAsuntosAsistencias() / scsAsistenciaExtendsMapper.searchClaveAsuntosAsistencias() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los Asistencias");
				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				asuntosJusticiableItems = scsAsistenciaExtendsMapper.searchClaveAsistencia(asuntosJusticiableItem,
						tamMaximo, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchClaveAsuntosAsistencias() / scsAsistenciaExtendsMapper.searchClaveAsuntosAsistencias() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los Asistencias");

				if (asuntosJusticiableItems != null && asuntosJusticiableItems.size() > 0) {
					// vamos a crear los datos de interes
					for (AsuntosJusticiableItem data : asuntosJusticiableItems) {
						String datoInteres = "";

						if (data.getTipo() != null && !data.getTipo().isEmpty()) {
							datoInteres += "<b>Tipo: </b>" + data.getTipo();
						}
						if (data.getIdEstadoDesigna() != null && !data.getIdEstadoDesigna().isEmpty()) {
							datoInteres += "<b>Tipo: </b>" + data.getIdEstadoDesigna();
						}

						if (data.getNumeroProcedimiento() != null && !data.getNumeroProcedimiento().isEmpty()) {
							if (!datoInteres.isEmpty()) {
								datoInteres += "<p>";
							}

							datoInteres += "<b>Núm. Procedimiento: </b>" + data.getNumeroProcedimiento();
						}
						if (data.getNumeroDiligencia() != null && !data.getNumeroDiligencia().isEmpty()) {
							if (!datoInteres.isEmpty()) {
								datoInteres += "<p>";
							}

							datoInteres += "<b>Núm. Diligencia: </b>" + data.getNumeroDiligencia();
						}

						data.setDatosInteres(datoInteres);
					}

					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
					if (asuntosJusticiableDTO.getAsuntosJusticiableItems() != null && tamMaximo != null
							&& asuntosJusticiableDTO.getAsuntosJusticiableItems().size() >= tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						asuntosJusticiableDTO.setError(error);
					}
				}
//					asuntosJusticiableDTO = gestionJusticiableServiceImpl
//							.searchAsuntosConClave(asuntosJusticiableItems, false, request);
			}

		}
		LOGGER.info("searchClaveAsuntosAsistencias() -> Salida del servicio para obtener los Asistencias");
		return asuntosJusticiableDTO;

	}

	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosDesignaciones(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosDesignaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosDesignaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = 200;
				}

				// buscamos la idpersona del colegiado
				if (asuntosJusticiableItem.getnColegiado() != null
						&& !asuntosJusticiableItem.getnColegiado().trim().isEmpty()) {
					String idPersonaColegiado = getIdPersonaColegiado(asuntosJusticiableItem.getnColegiado(),
							asuntosJusticiableItem.getIdInstitucion());

					if (!idPersonaColegiado.isEmpty()) {
						asuntosJusticiableItem.setIdPersonaColegiado(idPersonaColegiado);
					}
				}

				LOGGER.info(
						"searchClaveAsuntosDesignaciones() / scsEjgExtendsMapper.searchClaveAsuntosDesignaciones() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				asuntosJusticiableItems = scsDesignacionesExtendsMapper.searchClaveDesignaciones(asuntosJusticiableItem,
						tamMaximo, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchClaveAsuntosDesignaciones() / scsEjgExtendsMapper.searchClaveAsuntosDesignaciones() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null && asuntosJusticiableItems.size() > 0) {
					// vamos a crear los datos de interes
//					for(AsuntosJusticiableItem data : asuntosJusticiableItems) {
//						String datoInteres = "";
//						
//						if (data.getTipo() != null && !data.getTipo().isEmpty()) {
//							datoInteres += "<b>Tipo: </b>" + data.getTipo();
//						}
//
//						if (data.getNumeroProcedimiento() != null && !data.getNumeroProcedimiento().isEmpty()) {
//							if (!datoInteres.isEmpty()) {
//								datoInteres += "<p>";
//							}
//
//							datoInteres += "<b>Núm. Procedimiento: </b>" + data.getNumeroProcedimiento();
//						}
//
//						if (data.getJuzgado() != null && !data.getJuzgado().isEmpty()) {
//							if (!datoInteres.isEmpty()) {
//								datoInteres += "<p>";
//							}
//
//							datoInteres += "<p><b>Juzgado: </b>" + data.getJuzgado();
//						}
//						if (data.getNig() != null && !data.getNig().isEmpty()) {
//							if (!datoInteres.isEmpty()) {
//								datoInteres += "<p>";
//							}
//
//							datoInteres += "<p><b>NIG: </b>" + data.getNig();
//						}
//						if (data.getIdTipoDesigna() != null && !data.getIdTipoDesigna().isEmpty()) {
//							if (!datoInteres.isEmpty()) {
//								datoInteres += "<p>";
//							}
//
//							datoInteres += "<p><b>Tipo: </b>" + data.getIdTipoDesigna();
//						}
//						
//						data.setDatosInteres(datoInteres);						
//					}

					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
					if (asuntosJusticiableDTO.getAsuntosJusticiableItems() != null && tamMaximo != null
							&& asuntosJusticiableDTO.getAsuntosJusticiableItems().size() >= tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						asuntosJusticiableDTO.setError(error);
					}
				}

//				asuntosJusticiableDTO = gestionJusticiableServiceImpl.searchAsuntosConClave(asuntosJusticiableItems,
//						false, request);
			}
		}
		LOGGER.info("searchClaveAsuntosDesignaciones() -> Salida del servicio para obtener los AsuntosEJG");

		return asuntosJusticiableDTO;
	}

	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosSOJ(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosSOJ() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosSOJ() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"searchJusticiables() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = 200;
				}

				// buscamos la idpersona del colegiado
				if (asuntosJusticiableItem.getnColegiado() != null
						&& !asuntosJusticiableItem.getnColegiado().trim().isEmpty()) {
					String idPersonaColegiado = getIdPersonaColegiado(asuntosJusticiableItem.getnColegiado(),
							asuntosJusticiableItem.getIdInstitucion());

					if (!idPersonaColegiado.isEmpty()) {
						asuntosJusticiableItem.setIdPersonaColegiado(idPersonaColegiado);
					}
				}

				LOGGER.info(
						"searchClaveAsuntosSOJ() / scsEjgExtendsMapper.searchClaveAsuntosSOJ() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				asuntosJusticiableItems = scsSojExtendsMapper.searchClaveSoj(asuntosJusticiableItem, tamMaximo,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchClaveAsuntosSOJ() / scsEjgExtendsMapper.searchClaveAsuntosSOJ() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null && asuntosJusticiableItems.size() > 0) {
					// vamos a crear los datos de interes
					for (AsuntosJusticiableItem data : asuntosJusticiableItems) {
//						String datoInteres = "";
//						
//						if(data.getTipo()!=null && !data.getTipo().isEmpty()) {
//							datoInteres+="<b>Tipo: </b>"+data.getTipo();
//						}
//						
//						if(data.getEstado()!=null && !data.getEstado().isEmpty()) {
//							if(!datoInteres.isEmpty()) {
//								datoInteres+="<p>";
//							}
//							
//							datoInteres+="<b>Estado: </b>"+data.getEstado();
//						}
//						
//						
//						data.setDatosInteres(datoInteres);	
						if (!(data.getDilnigproc() != null && !data.getDilnigproc().isEmpty())) {
							data.setDilnigproc("Sin numero - Sin numero - Sin numero");
						}

					}

					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
					if (asuntosJusticiableDTO.getAsuntosJusticiableItems() != null && tamMaximo != null
							&& asuntosJusticiableDTO.getAsuntosJusticiableItems().size() >= tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						asuntosJusticiableDTO.setError(error);
					}
				}

//				asuntosJusticiableDTO = gestionJusticiableServiceImpl
//						.searchAsuntosConClave(asuntosJusticiableItems, false, request);
			}
		}
		LOGGER.info("searchClaveAsuntosSOJ() -> Salida del servicio para obtener los AsuntosEJG");

		return asuntosJusticiableDTO;
	}
	
	@Transactional
	@Override
	public UpdateResponseDTO copyEjg2Soj(List<String> datos,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				//Se comentan el try y el catch para que @Transactional funcione adecuadamente.
//				try {
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Iniciando los inserts...");

					//Tareas:
					//1. Obtenemos los asuntos que vamos a manipular.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Seleccionando EJG y SOJ.");
					
					ScsSojKey sojKey = new ScsSojKey();
					
					sojKey.setIdinstitucion(idInstitucion);
					sojKey.setNumero(Long.parseLong(datos.get(2)));
					sojKey.setAnio(Short.parseShort(datos.get(1)));
					sojKey.setIdtiposoj(Short.parseShort(datos.get(3)));
					
					ScsSoj soj = scsSojMapper.selectByPrimaryKey(sojKey);
					
					ScsEjgKey ejgKey = new ScsEjgKey();
					
					ejgKey.setIdinstitucion(idInstitucion);
					ejgKey.setAnio(Short.parseShort(datos.get(5)));
					ejgKey.setIdtipoejg(Short.parseShort(datos.get(4)));
					ejgKey.setNumero(Long.parseLong(datos.get(6)));

					ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> EJG y SOJ seleccionados.");
					
					//2. Se asignan el letrado y el solicitante principal del EJG al SOJ.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Copiando informacion del EJG al SOJ.");
					soj.setIdpersona(ejg.getIdpersona());
					soj.setIdpersonajg(ejg.getIdpersonajg());
					
					soj.setUsumodificacion(usuarios.get(0).getIdusuario());
					soj.setFechamodificacion(new Date());
					
					response = scsSojMapper.updateByPrimaryKey(soj);
					if(response == 0)throw (new Exception("Error en copyEjg2Soj() al copiar los datos del EJG al SOJ."));
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> INformacion copiada del EJG al SOJ.");
//				} catch (Exception e) {
//					LOGGER.error("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Soj() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}
	
	
	@Transactional
	@Override
	public UpdateResponseDTO copyEjg2Asis(List<String> datos,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				//Se comentan el try y el catch para que @Transactional funcione adecuadamente.
//				try {
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Iniciando los inserts...");

					//Tareas:
					//1. Obtenemos los asuntos que vamos a manipular.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Seleccionando EJG y la asistencia correspondientes.");
					
					ScsAsistenciaKey asisKey = new ScsAsistenciaKey();
					
					asisKey.setIdinstitucion(idInstitucion);
					asisKey.setNumero(Long.parseLong(datos.get(2)));
					asisKey.setAnio(Short.parseShort(datos.get(1)));
					
					ScsAsistencia asis = scsAsistenciaMapper.selectByPrimaryKey(asisKey);
					
					ScsEjgKey ejgKey = new ScsEjgKey();
					
					ejgKey.setIdinstitucion(idInstitucion);
					ejgKey.setAnio(Short.parseShort(datos.get(4)));
					ejgKey.setIdtipoejg(Short.parseShort(datos.get(3)));
					ejgKey.setNumero(Long.parseLong(datos.get(5)));

					ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> EJG y Asistencia seleccionados.");
					
					//2. Actualizamos los delitos de la asistencia asignando los del EJG.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Copiando delitos del EJG a la asistencia.");
					
					ScsDelitosasistencia delitoAsistencia = new ScsDelitosasistencia();

					delitoAsistencia.setIdinstitucion(idInstitucion);
					delitoAsistencia.setAnio(asis.getAnio());
					delitoAsistencia.setNumero(asis.getNumero());
					
					delitoAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
					delitoAsistencia.setFechamodificacion(new Date());

					ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();
					
					delitosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero())
					.andIdtipoejgEqualTo(ejg.getIdtipoejg());
					
					List<ScsDelitosejg> delitosEjg = scsDelitosejgMapper.selectByExample(delitosEjgExample);
					
					ScsDelitosasistenciaExample delitosAsistenciaExample = new ScsDelitosasistenciaExample();
					
					delitosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero());
					
					List<ScsDelitosasistencia> delitosAsistencia = scsDelitosasistenciaMapper.selectByExample(delitosAsistenciaExample);
					
					if(!delitosAsistencia.isEmpty()){
						response = scsDelitosasistenciaMapper.deleteByExample(delitosAsistenciaExample);
						if(response == 0) throw(new Exception("Error al eliminar los delitos anteriores de la asistencia"));

					}
					
					if(!delitosEjg.isEmpty()){
						for (ScsDelitosejg delitoEjg : delitosEjg) {
							delitoAsistencia.setIddelito(delitoEjg.getIddelito());
							response = scsDelitosasistenciaMapper.insert(delitoAsistencia);
							if(response == 0) throw(new Exception("Error al introducir un delito en la asistencia proveniente del EJG"));
						}
					}
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Delitos copiados del EJG a la asistencia.");
					
					//3. Actualizamos los contrarios de la asistencia asignando los del EJG.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Copiando contrarios del EJG a la asistencia.");
					
					ScsContrariosasistencia contrarioAsistencia = new ScsContrariosasistencia();

					contrarioAsistencia.setIdinstitucion(idInstitucion);
					contrarioAsistencia.setAnio(asis.getAnio());
					contrarioAsistencia.setNumero(asis.getNumero());
					
					contrarioAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
					contrarioAsistencia.setFechamodificacion(new Date());

					ScsContrariosejgExample contrariosEjgExample = new ScsContrariosejgExample();
					
					contrariosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero())
					.andIdtipoejgEqualTo(ejg.getIdtipoejg());
					
					List<ScsContrariosejg> contrariosEjg = scsContrariosejgMapper.selectByExample(contrariosEjgExample);
					
					ScsContrariosasistenciaExample contrariosAsistenciaExample = new ScsContrariosasistenciaExample();
					
					contrariosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero());
					
					List<ScsContrariosasistencia> contrariosAsistencia = scsContrariosasistenciaMapper.selectByExample(contrariosAsistenciaExample);
					
					if(!contrariosAsistencia.isEmpty()){
						response = scsContrariosasistenciaMapper.deleteByExample(contrariosAsistenciaExample);
						if(response == 0) throw(new Exception("Error al eliminar los contrarios anteriores de la asistencia"));

					}
					
					String contrariosAsisString = "";
					if(!contrariosEjg.isEmpty()){
						for (ScsContrariosejg contrarioEjg : contrariosEjg) {
							contrarioAsistencia.setIdpersona(contrarioEjg.getIdpersona());
							contrariosAsisString += contrarioEjg.getIdpersona();
							response = scsContrariosasistenciaMapper.insert(contrarioAsistencia);
							if(response == 0) throw(new Exception("Error al introducir un contrario en la asistencia proveniente del EJG"));
						}
					}
					
					asis.setUsumodificacion(usuarios.get(0).getIdusuario());
					asis.setFechamodificacion(new Date());
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Contrarios copiados del EJG a la asistencia.");
					
					//4. Se asignan los datos del EJG a la asistencia.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Copiando informacion del EJG a la asistencia.");
					
					if(ejg.getIdpersona() != null)asis.setIdpersonacolegiado(ejg.getIdpersona());
					asis.setIdpersonajg(ejg.getIdpersonajg());
					
					asis.setJuzgado(ejg.getJuzgado());
					asis.setJuzgadoidinstitucion(ejg.getJuzgadoidinstitucion());
					
					asis.setComisaria(ejg.getComisaria());
					asis.setComisariaidinstitucion(ejg.getComisariaidinstitucion());
					
					asis.setNumeroprocedimiento(ejg.getAnioprocedimiento()+ejg.getNumeroprocedimiento());
					asis.setNumerodiligencia(ejg.getNumerodiligencia());
					asis.setNig(ejg.getNig());
					asis.setIdpretension(ejg.getIdpretension());
					
					asis.setDelitosimputados(ejg.getDelitos());
					asis.setContrarios(contrariosAsisString);
					
					asis.setUsumodificacion(usuarios.get(0).getIdusuario());
					asis.setFechamodificacion(new Date());
					
					response = scsAsistenciaMapper.updateByPrimaryKey(asis);
					if(response == 0)throw (new Exception("Error en copyEjg2Asis() al copiar los datos del EJG a la asistencia."));
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Informacion del EJG a la asistencia copiada.");
					
//				} catch (Exception e) {
//					LOGGER.error("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Asis() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}


	@Transactional
	@Override
	public InsertResponseDTO copyEjg2Designa(List<String> datos,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO responseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Entrada a servicio para insertar las justificaciones express");
				//Se comentan el try y el catch para que @Transactional funcone adecuadamente.
//				try {
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Iniciando los inserts...");

					//Tareas:
					//0. Seleccionamos los datos implicados
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Saliendo a la actualizacion de algunos datos juridicos de designa");
					
					
					ScsDesignaKey designaKey = new ScsDesignaKey();
					
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(Short.parseShort(datos.get(1)));
					designaKey.setNumero(Long.parseLong(datos.get(2)));
					
					TurnosItem turnosItem = new TurnosItem();
					String turnoDesc = datos.get(7).substring(0, datos.get(7).length() - 1);
					turnosItem.setAbreviatura(turnoDesc);
					List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion);
					
					designaKey.setIdturno(Integer.parseInt(turnos.get(0).getIdturno()));
					
					ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);

					ScsEjgKey ejgKey = new ScsEjgKey();
					
					ejgKey.setIdinstitucion(idInstitucion);
					ejgKey.setAnio(Short.parseShort(datos.get(5)));
					ejgKey.setIdtipoejg(Short.parseShort(datos.get(4)));
					ejgKey.setNumero(Long.parseLong(datos.get(6)));
					
					ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
					
					//1. Se debe modificar los atributos asociados en la ficha predesignacion del EJG en la designa.
					
					designa.setNumprocedimiento(ejg.getNumeroprocedimiento());
					designa.setAnioprocedimiento(ejg.getAnioprocedimiento());	
					designa.setNig(ejg.getNig());
					designa.setObservaciones(ejg.getObservaciones());
					designa.setIdpretension(ejg.getIdpretension());
					designa.setIdjuzgado(ejg.getJuzgado());
					
					//Actualizamos los delitos de la designacion eliminando los anteriores y asignando los designados en EJG.
					ScsDelitosdesigna delitoDesigna = new ScsDelitosdesigna();

					delitoDesigna.setIdinstitucion(idInstitucion);
					delitoDesigna.setAnio(designa.getAnio());
					delitoDesigna.setNumero(designa.getNumero());
					delitoDesigna.setIdturno(designa.getIdturno());
					delitoDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
					delitoDesigna.setFechamodificacion(new Date());
					
					//Seleccionamos y eliminamos los delitos anteriormente seleccionados en la designacion.
					
					ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();
					
					delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsDelitosdesigna> delitosDesigna = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);

					if(!delitosDesigna.isEmpty()) {
						response = scsDelitosdesignaMapper.deleteByExample(delitosDesignaExample);
						if(response == 0) throw(new Exception("Error al eliminar los delitos de la designacion"));
					}
					
					ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();
					
					delitosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero())
					.andIdtipoejgEqualTo(ejg.getIdtipoejg());
					
					List<ScsDelitosejg> delitosEjg = scsDelitosejgMapper.selectByExample(delitosEjgExample);
					
					if(!delitosEjg.isEmpty()){
						for (ScsDelitosejg delitoEjg : delitosEjg) {
							delitoDesigna.setIddelito(delitoEjg.getIddelito());
							response = scsDelitosdesignaMapper.insert(delitoDesigna);
							if(response == 0) throw(new Exception("Error al introducir un delito en la designacion proveniente del EJG"));
						}
					}
					
					designa.setUsumodificacion(usuarios.get(0).getIdusuario());
					designa.setFechamodificacion(new Date());
					
					response = scsDesignaMapper.updateByPrimaryKeySelective(designa);
					if(response == 0) throw(new Exception("Error al introducir los datos juridicos en la designacion proveniente del EJG"));

					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Saliendo de la actualizacion de algunos datos juridicos de designa");
					
					//2. Se debe insertar los contrarios seleccionados en EJG.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Entrando a los inserts para los contrarios de designa");
					
					//Obtenemos los contrarios ejg a introducir. Se seleccionan solo los activos (con fecha de baja nula).
					
					ScsContrariosejgExample contrariosEjgExample = new ScsContrariosejgExample();
					
					contrariosEjgExample.createCriteria().andAnioEqualTo(ejg.getAnio())
					.andNumeroEqualTo(ejg.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andFechabajaIsNull();
					
					List<ScsContrariosejg> contrariosEjg = scsContrariosejgMapper.selectByExample(contrariosEjgExample);
									
					//Seleccionamos y borramos los contrarios presentes anteriormente en la designacion
					
					ScsContrariosdesignaExample contrariosDesignaExample = new ScsContrariosdesignaExample();
					
					contrariosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsContrariosdesigna> contrariosDesigna = scsContrariosdesignaMapper.selectByExample(contrariosDesignaExample);

					if(!contrariosDesigna.isEmpty()) {
						response = scsDelitosdesignaMapper.deleteByExample(delitosDesignaExample);
						if(response == 0) throw(new Exception("Error al eliminar los contrarios de la designacion"));
					}
					
					
					//Se crean los nuevos contrarios y se asignan
					
					ScsContrariosdesigna newContrarioDesigna = new ScsContrariosdesigna();
					newContrarioDesigna.setAnio(designa.getAnio());
					newContrarioDesigna.setNumero(designa.getNumero());
					newContrarioDesigna.setIdturno(designa.getIdturno());
					newContrarioDesigna.setIdinstitucion(designa.getIdinstitucion());
					
					newContrarioDesigna.setFechamodificacion(new Date());
					newContrarioDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());

					
					for(ScsContrariosejg contrarioEjg: contrariosEjg) {
						
						ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
						scsPersonajgkey.setIdpersona(Long.valueOf(contrarioEjg.getIdpersona()));
						scsPersonajgkey.setIdinstitucion(idInstitucion);

						LOGGER.info(
								"copyEjg2Designa() / scsPersonajgMapper.selectByPrimaryKey() -> Entrada a scsPersonajgMapper para obtener justiciables de los contrarios ejg");

						ScsPersonajg personajg = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

						LOGGER.info(
								"copyEjg2Designa() / scsPersonajgMapper.selectByPrimaryKey() -> Salida de scsPersonajgMapper para obtener justiciables de los contrarios ejg");

						// Se comprueba si tiene representante y se busca.
						if (personajg.getIdrepresentantejg() != null) {

							scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

							ScsPersonajg representante = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

							newContrarioDesigna.setNombrerepresentante(representante.getApellido1() + " " + representante.getApellido2()
							+ ", " + representante.getNombre());
						}
						
						
						//Se le van asignando los distintos valores de IdPersona correspondientes
						newContrarioDesigna.setIdpersona(Long.valueOf(contrarioEjg.getIdpersona()));
						
						LOGGER.info(
								"copyEjg2Designa() / ScsContrariosdesignaMapper.insert() -> Entrada a ScsContrariosdesignaMapper para insertar los contrarios ejg");

						response = scsContrariosdesignaMapper.insert(newContrarioDesigna);
						if(response == 0) throw(new Exception("Error al introducir contrarios en la designa provenientes del EJG"));

						
					}
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Saliendo de los inserts para los contrarios de designa");
					
					//3. Se debe introducir el procurador seleccionado en el EJG.
					
					//Se comprueba que hay un procurador definido en el ejg para prevenir inserciones fallidas
					if(ejg.getIdprocurador()!=null) {
						
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Iniciando los inserts...");
					
					ScsDesignaprocurador procDesigna = new ScsDesignaprocurador();
					
					procDesigna.setIdinstitucion(idInstitucion);
					procDesigna.setIdturno(designa.getIdturno());
					procDesigna.setNumero(designa.getNumero());
					procDesigna.setNumerodesignacion(ejg.getNumerodesignaproc());
					procDesigna.setAnio(designa.getAnio());
					
					procDesigna.setIdinstitucionProc(ejg.getIdinstitucionProc());
					procDesigna.setIdprocurador(ejg.getIdprocurador());
					procDesigna.setFechadesigna(ejg.getFechaDesProc());	
					
					procDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
					procDesigna.setFechamodificacion(new Date());						
					
					response = scsDesignaProcuradorMapper.insert(procDesigna);
					if(response == 0) throw(new Exception("Error al introducir un procurador en la designa proveniente del EJG"));

					}
					
					//4. Se debe insertar los interesados seleccionados en EJG en Unidad Familiar.
					
					ScsUnidadfamiliarejgExample familiaresEJGExample = new ScsUnidadfamiliarejgExample();
					
					familiaresEJGExample.createCriteria().andAnioEqualTo(ejg.getAnio()).andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andNumeroEqualTo(ejg.getNumero()).andFechabajaIsNull();
					
					List<ScsUnidadfamiliarejg> familiaresEJG = scsUnidadfamiliarejgMapper.selectByExample(familiaresEJGExample);
					
					for(ScsUnidadfamiliarejg familiar : familiaresEJG) {
						
						//Seleccionamos y borramos los interesados presentes anteriormente en la designacion
						
						ScsDefendidosdesignaExample interesadosDesignaExample = new ScsDefendidosdesignaExample();
						
						interesadosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
						.andIdturnoEqualTo(designa.getIdturno());
						
						List<ScsDefendidosdesigna> interesadosDesigna = scsDefendidosdesignaMapper.selectByExample(interesadosDesignaExample);

						if(!interesadosDesigna.isEmpty()) {
							response = scsDefendidosdesignaMapper.deleteByExample(interesadosDesignaExample);
							if(response == 0) throw(new Exception("Error al eliminar los interesados de la designacion"));
						}
						

						//Se crea el interesado que se introducira en la designacion
						ScsDefendidosdesigna interesado = new ScsDefendidosdesigna();
						
						interesado.setAnio(designa.getAnio());
						interesado.setNumero(designa.getNumero());
						interesado.setIdinstitucion(idInstitucion);
						interesado.setIdpersona(familiar.getIdpersona());
						interesado.setIdturno(designa.getIdturno());
						
						//Se comprueba si el interesado introducido tiene un representante asociado
						ScsPersonajgKey personajgKey = new ScsPersonajgKey();
						
						personajgKey.setIdinstitucion(idInstitucion);
						personajgKey.setIdpersona(familiar.getIdpersona());
						
						ScsPersonajg personajg = scsPersonajgMapper.selectByPrimaryKey(personajgKey);
						
						if(personajg.getIdrepresentantejg() != null) {personajgKey.setIdpersona(personajg.getIdrepresentantejg());
						
						ScsPersonajg representanteFamiliar = scsPersonajgMapper.selectByPrimaryKey(personajgKey);
						
						interesado.setNombrerepresentante(representanteFamiliar.getNombre());
						}
						
						interesado.setUsumodificacion(usuarios.get(0).getIdusuario());
						interesado.setFechamodificacion(new Date());
						
						response = scsDefendidosdesignaMapper.insert(interesado);
						if(response == 0) throw(new Exception("Error al introducir interesados en la designa proveniente de la unidad familiar del EJG"));

					}
					
					
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Inserts finalizados");
//				} catch (Exception e) {
//					LOGGER.error("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}
	

	@Transactional
	@Override
	public UpdateResponseDTO copyDesigna2Asis(List<String> datos,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				//Se comentan el try y el catch para que @Transactional funcione adecuadamente.
//				try {
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Iniciando los inserts...");

					//Tareas:
					//1. Obtenemos los asuntos que vamos a manipular.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Seleccionando la designacion y la asistencia correspondientes.");
					
					ScsDesignaKey designaKey = new ScsDesignaKey();
					
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(Short.parseShort(datos.get(0)));
					designaKey.setNumero(Long.parseLong(datos.get(2)));
					designaKey.setIdturno(Integer.parseInt(datos.get(1)));

					ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);
					
					ScsAsistenciaKey asisKey = new ScsAsistenciaKey();
					
					asisKey.setIdinstitucion(idInstitucion);
					asisKey.setNumero(Long.parseLong(datos.get(5)));
					asisKey.setAnio(Short.parseShort(datos.get(4)));
					
					ScsAsistencia asis = scsAsistenciaMapper.selectByPrimaryKey(asisKey);
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> EJG y Asistencia seleccionados.");
					
					//2. Actualizamos los delitos de la asistencia asignando los de la designacion.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Copiando delitos de la designacion a la asistencia.");
					
					ScsDelitosasistencia delitoAsistencia = new ScsDelitosasistencia();

					delitoAsistencia.setIdinstitucion(idInstitucion);
					delitoAsistencia.setAnio(asis.getAnio());
					delitoAsistencia.setNumero(asis.getNumero());
					
					delitoAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
					delitoAsistencia.setFechamodificacion(new Date());

					ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();
					
					delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsDelitosdesigna> delitosDesigna = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);
					
					ScsDelitosasistenciaExample delitosAsistenciaExample = new ScsDelitosasistenciaExample();
					
					delitosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero());
					
					List<ScsDelitosasistencia> delitosAsistencia = scsDelitosasistenciaMapper.selectByExample(delitosAsistenciaExample);
					
					if(!delitosAsistencia.isEmpty()){
						response = scsDelitosasistenciaMapper.deleteByExample(delitosAsistenciaExample);
						if(response == 0) throw(new Exception("Error al eliminar los delitos anteriores de la asistencia"));

					}
					
					if(!delitosDesigna.isEmpty()){
						for (ScsDelitosdesigna delitoDesigna : delitosDesigna) {
							delitoAsistencia.setIddelito(delitoDesigna.getIddelito());
							response = scsDelitosasistenciaMapper.insert(delitoAsistencia);
							if(response == 0) throw(new Exception("Error al introducir un delito en la asistencia proveniente de la designacion"));
						}
					}
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Delitos copiados de la designacion a la asistencia.");
					
					//3. Actualizamos los contrarios de la asistencia asignando los de la designacion.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Copiando contrarios de la designacion a la asistencia.");
					
					ScsContrariosasistencia contrarioAsistencia = new ScsContrariosasistencia();

					contrarioAsistencia.setIdinstitucion(idInstitucion);
					contrarioAsistencia.setAnio(asis.getAnio());
					contrarioAsistencia.setNumero(asis.getNumero());
					
					contrarioAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
					contrarioAsistencia.setFechamodificacion(new Date());

					ScsContrariosdesignaExample contrariosDesignaExample = new ScsContrariosdesignaExample();
					
					contrariosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsContrariosdesigna> contrariosDesigna = scsContrariosdesignaMapper.selectByExample(contrariosDesignaExample);
					
					ScsContrariosasistenciaExample contrariosAsistenciaExample = new ScsContrariosasistenciaExample();
					
					contrariosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero());
					
					List<ScsContrariosasistencia> contrariosAsistencia = scsContrariosasistenciaMapper.selectByExample(contrariosAsistenciaExample);
					
					if(!contrariosAsistencia.isEmpty()){
						response = scsContrariosasistenciaMapper.deleteByExample(contrariosAsistenciaExample);
						if(response == 0) throw(new Exception("Error al eliminar los contrarios anteriores de la asistencia"));

					}
					
					String contrariosAsisString = "";
					if(!contrariosDesigna.isEmpty()){
						for (ScsContrariosdesigna contrarioDesigna : contrariosDesigna) {
							contrarioAsistencia.setIdpersona(contrarioDesigna.getIdpersona());
							if(contrariosAsisString != "") contrariosAsisString += ",";
							contrariosAsisString += contrarioDesigna.getIdpersona();
							response = scsContrariosasistenciaMapper.insert(contrarioAsistencia);
							if(response == 0) throw(new Exception("Error al introducir un contrario en la asistencia proveniente de la designacion"));
						}
					}
					
					asis.setUsumodificacion(usuarios.get(0).getIdusuario());
					asis.setFechamodificacion(new Date());
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Contrarios copiados de la designacion a la asistencia.");
					
					//4. Se asignan los datos de la designacion a la asistencia.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Copiando informacion de la designacion a la asistencia.");
					
					ScsDesignasletradoExample letradoDesignaExample = new ScsDesignasletradoExample();
					
					letradoDesignaExample.setOrderByClause("FECHADESIGNA DESC");

					letradoDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsDesignasletrado> letradoDesigna = scsDesignasletradoMapper.selectByExample(letradoDesignaExample);
							
					
					if(!letradoDesigna.isEmpty())asis.setIdpersonacolegiado(letradoDesigna.get(0).getIdpersona());
					
					asis.setJuzgado(designa.getIdjuzgado());
					asis.setJuzgadoidinstitucion(designa.getIdinstitucionJuzg());
					
					asis.setNumeroprocedimiento(designa.getAnioprocedimiento()+designa.getNumprocedimiento());
					asis.setNig(designa.getNig());
					asis.setIdpretension(designa.getIdpretension());
					
					asis.setDelitosimputados(designa.getDelitos());
					asis.setContrarios(contrariosAsisString);
					
					asis.setUsumodificacion(usuarios.get(0).getIdusuario());
					asis.setFechamodificacion(new Date());
					
					response = scsAsistenciaMapper.updateByPrimaryKey(asis);
					if(response == 0)throw (new Exception("Error en copyDesigna2Asis() al copiar los datos de la designacion a la asistencia."));
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Informacion de la designacino a la asistencia copiada.");
					
//				} catch (Exception e) {
//					LOGGER.error("BusquedaAsuntosServiceImpl.extraerPreDesignaEJG() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Asis() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}


	@Transactional
	@Override
	public InsertResponseDTO copyDesigna2Ejg(List<String> datos,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO responseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Entrada a servicio para insertar las justificaciones express");
				//Se comentan el try y el catch para que @Transactional funcone adecuadamente.
//				try {
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Iniciando los inserts...");

					//Tareas:
					//0. Se debe modificar los atributos asociados con predesignacion en la designa.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Saliendo a la actualizacion de algunos datos juridicos de designa");
					
					
					ScsEjgdesigna item = getEjgDesigna(datos, idInstitucion, usuarios.get(0));
					
					ScsEjgKey ejgKey = new ScsEjgKey();
					
					ejgKey.setIdinstitucion(idInstitucion);
					ejgKey.setAnio(item.getAnioejg());
					ejgKey.setIdtipoejg(item.getIdtipoejg());
					ejgKey.setNumero(item.getNumeroejg());
					
					ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
					
					ScsDesignaKey designaKey = new ScsDesignaKey();
					
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setIdturno(item.getIdturno());
					designaKey.setAnio(item.getAniodesigna());
					designaKey.setNumero(item.getNumerodesigna());
					
					ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);
					
					//1. Actualizamos los delitos del EJG.
					
					ScsDelitosejg delitoEjg = new ScsDelitosejg();

					delitoEjg.setIdinstitucion(idInstitucion);
					delitoEjg.setAnio(designa.getAnio());
					delitoEjg.setNumero(designa.getNumero());
					delitoEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
					delitoEjg.setFechamodificacion(new Date());

					ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();
					
					delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsDelitosdesigna> delitosDesigna = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);
					
					String delitosEjg = "";
					if(!delitosDesigna.isEmpty()){
						for (ScsDelitosdesigna delitoDesigna : delitosDesigna) {
							if(delitosEjg != "")delitosEjg += ",";
							delitosEjg += delitoDesigna.getIddelito();
							delitoEjg.setIddelito(delitoDesigna.getIddelito());
							response = scsDelitosdesignaMapper.insert(delitoDesigna);
							if(response == 0) throw(new Exception("Error al introducir un delito en el EJG proveniente de la designacion."));
						}
					}
					
					response = scsDesignaMapper.updateByPrimaryKeySelective(designa);
					if(response == 0) throw(new Exception("Error al introducir los datos juridicos en la designa proveniente del EJG"));

					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Saliendo de la actualizacion de algunos datos juridicos de designa");
					
					//2. Se debe insertar los contrarios seleccionados en la designacion.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Entrando a los inserts para los contrarios de designa");
					
					//Obtenemos los contrarios ejg a introducir
					
					ScsContrariosdesignaExample contrariosDesignaExample = new ScsContrariosdesignaExample();
					
					contrariosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsContrariosdesigna> contrariosDesigna = scsContrariosdesignaMapper.selectByExample(contrariosDesignaExample);
									
					
					ScsContrariosejg contrariosEjg = new ScsContrariosejg();
					contrariosEjg.setAnio(ejg.getAnio());
					contrariosEjg.setNumero(ejg.getNumero());
					contrariosEjg.setIdtipoejg(ejg.getIdtipoejg());
					contrariosEjg.setIdinstitucion(item.getIdinstitucion());
					
					contrariosEjg.setFechamodificacion(new Date());
					contrariosEjg.setUsumodificacion(usuarios.get(0).getIdusuario());

					
					for(ScsContrariosdesigna contrarioDesigna: contrariosDesigna) {
						
						ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
						scsPersonajgkey.setIdpersona(Long.valueOf(contrarioDesigna.getIdpersona()));
						scsPersonajgkey.setIdinstitucion(idInstitucion);

						LOGGER.info(
								"copyDesigna2Ejg() / scsPersonajgMapper.selectByPrimaryKey() -> Entrada a scsPersonajgMapper para obtener justiciables de los contrarios de la designacion");

						ScsPersonajg personajg = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

						LOGGER.info(
								"copyDesigna2Ejg() / scsPersonajgMapper.selectByPrimaryKey() -> Salida de scsPersonajgMapper para obtener justiciables de los contrarios de la designacion");

						// Se comprueba si tiene representante y se busca.
						if (personajg.getIdrepresentantejg() != null) {

							scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

							ScsPersonajg representante = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

							contrariosEjg.setNombrerepresentanteejg(representante.getApellido1() + " " + representante.getApellido2()
							+ ", " + representante.getNombre());
						}
						
						//Se le van asignando los distintos valores de IdPersona correspondientes
						contrariosEjg.setIdpersona(Long.valueOf(contrarioDesigna.getIdpersona()));
						

						LOGGER.info(
								"copyDesigna2Ejg() / ScsContrariosdesignaMapper.insert() -> Entrada a ScsContrariosejgMapper para insertar los contrarios en ejg");

						//Hacemos esto junto a la iniciacion response2 = 1 para evitar que si un contrario se introduce erroneamente,
						//pase desapercibido al insertar bien el siguiente.
						response = scsContrariosejgMapper.insert(contrariosEjg);
						if(response == 0) throw(new Exception("Error al introducir contrarios en el EJG provenientes de la designacion"));


						LOGGER.info(
								"copyDesigna2Ejg() / ScsContrariosdesignaMapper.insert() -> Salida de ScsContrariosejgMapper para insertar los contrarios en ejg");

					}
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Saliendo de los inserts para los contrarios de designa");
					
					//3. Se asignan los valores de defensa juridica de EJG
					
//					PRE
					ejg.setNumeroprocedimiento(designa.getNumprocedimiento());
					ejg.setNig(designa.getNig());
					ejg.setObservaciones(designa.getObservaciones());
					ejg.setIdpretension(designa.getIdpretension());
					ejg.setJuzgado(designa.getIdjuzgado());
					
					//Se asocia el ultimo letrado asignado.
					
					ScsDesignasletradoExample letradosDesignaExample = new ScsDesignasletradoExample();
					
					letradosDesignaExample.setOrderByClause("FECHADESIGNA DESC");
					letradosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno());
					
					//Buscamos el ultmo letrado designado en la designa.
					List<ScsDesignasletrado> letradosDesigna = scsDesignasletradoMapper.selectByExample(letradosDesignaExample);
					
					if(!letradosDesigna.isEmpty())ejg.setIdpersona(letradosDesigna.get(0).getIdpersona());
					else ejg.setIdpersona(null);
	
					ejg.setUsumodificacion(usuarios.get(0).getIdusuario());
					ejg.setFechamodificacion(new Date());
					
					//Se busca el id del letrado asignado
					
					//4. Se debe introducir el procurador seleccionado en la designacion.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Iniciando la introduccion del procurador de la designacion al EJG");
					
					//Obtenemos la lista de procuradores asociados a la designacion
					ScsDesignaprocuradorExample procuradoresDesignaExample = new ScsDesignaprocuradorExample();
					
					procuradoresDesignaExample.setOrderByClause("FECHADESIGNA DESC");
					procuradoresDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno());
					
					List<ScsDesignaprocurador> procuradoresDesigna = scsDesignaProcuradorMapper.selectByExample(procuradoresDesignaExample);
					
					//Se comprueba si hay algún procurador asociado antes de continuar
					if(!procuradoresDesigna.isEmpty()) {
					ejg.setIdprocurador(designa.getIdprocurador());
					ejg.setIdinstitucionProc(designa.getIdinstitucionProcur());
					ejg.setFechaDesProc(procuradoresDesigna.get(0).getFechadesigna());	
					ejg.setNumerodesignaproc(procuradoresDesigna.get(0).getNumerodesignacion());
					}
					//Si no hay ningún procurador asociado, se desasocia el existente en EJG.
					else {
						ejg.setIdprocurador(null);
						ejg.setIdinstitucionProc(null);
						ejg.setFechaDesProc(null);	
						ejg.setNumerodesignaproc(null);
					}

					response = scsEjgMapper.updateByPrimaryKey(ejg);
					if(response == 0) throw(new Exception("Error al introducir un procurador en el EJG proveniente de la designacion"));
					
					//4. Se debe insertar los interesados seleccionados en la designacion en Unidad Familiar del EJG.
					
					ScsUnidadfamiliarejgExample familiaresEJGExample = new ScsUnidadfamiliarejgExample();
					
					familiaresEJGExample.createCriteria().andAnioEqualTo(ejg.getAnio()).andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andNumeroEqualTo(ejg.getNumero()).andFechabajaIsNull();
					
					List<ScsUnidadfamiliarejg> familiaresEJG = scsUnidadfamiliarejgMapper.selectByExample(familiaresEJGExample);
					
					//Se eliminan los familiares presentes en la tarjeta Unidad Familiar del EJG.
					if(!familiaresEJG.isEmpty()) {
						response = scsUnidadfamiliarejgMapper.deleteByExample(familiaresEJGExample);
						if(response==0) throw(new Exception("Error al eliminar la unidad familiar asociada al EJG"));
					}
					
					
					ScsDefendidosdesignaExample defendidosDesignaExample = new ScsDefendidosdesignaExample();
					
					defendidosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno()).andNumeroEqualTo(designa.getNumero());
					
					List<ScsDefendidosdesigna> defendidosDesigna = scsDefendidosdesignaMapper.selectByExample(defendidosDesignaExample);
					
					for(ScsDefendidosdesigna interesado : defendidosDesigna) {

						//Se crea el familiar que se introducira en la unidad familiar del EJG
						ScsUnidadfamiliarejg familiar = new ScsUnidadfamiliarejg();
						
						familiar.setAnio(ejg.getAnio());
						familiar.setNumero(ejg.getNumero());
						familiar.setIdtipoejg(ejg.getIdtipoejg());
						familiar.setIdinstitucion(idInstitucion);
						familiar.setIdpersona(interesado.getIdpersona());
						familiar.setEncalidadde(interesado.getCalidad());
						familiar.setObservaciones(interesado.getObservaciones());
						
						
						interesado.setUsumodificacion(usuarios.get(0).getIdusuario());
						interesado.setFechamodificacion(new Date());
						
						response = scsUnidadfamiliarejgMapper.insert(familiar);
						if(response == 0) throw(new Exception("Error al introducir familiares en la unidad familiar del EJG desde los interesados de la designacion."));

					}
					
					}
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Inserts finalizados");
//				} catch (Exception e) {
//					LOGGER.error("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Saliendo del servicio... ");
			}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}


	@Transactional
	@Override
	public UpdateResponseDTO copyDesigna2Soj(List<String> datos,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				//Se comentan el try y el catch para que @Transactional funcione adecuadamente.
//				try {
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Iniciando los inserts...");

					//Tareas:
					//1. Obtenemos los asuntos que vamos a manipular.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Seleccionando Designacion y SOJ.");
					
					ScsSojKey sojKey = new ScsSojKey();
					
					sojKey.setIdinstitucion(idInstitucion);
					sojKey.setNumero(Long.parseLong(datos.get(2)));
					sojKey.setAnio(Short.parseShort(datos.get(1)));
					sojKey.setIdtiposoj(Short.parseShort(datos.get(3)));
					
					ScsSoj soj = scsSojMapper.selectByPrimaryKey(sojKey);
					
					ScsDesignaKey designaKey = new ScsDesignaKey();
					
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(Short.parseShort(datos.get(5)));
					designaKey.setIdturno(Integer.valueOf(datos.get(4)));
					designaKey.setNumero(Long.parseLong(datos.get(6)));

					ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Designacion y SOJ seleccionados.");
					
					//2. Se asignan el letrado y el solicitante principal del EJG al SOJ.
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Copiando informacion de la designacion al SOJ.");
					
					ScsDesignasletradoExample letradosDesignaExample = new ScsDesignasletradoExample();
					
					letradosDesignaExample.setOrderByClause("FECHADESIGNA DESC");
					letradosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno());
					
					//Buscamos el ultmo letrado designado en la designa.
					List<ScsDesignasletrado> letradosDesigna = scsDesignasletradoMapper.selectByExample(letradosDesignaExample);
					
					if(!letradosDesigna.isEmpty())soj.setIdpersona(letradosDesigna.get(0).getIdpersona());
					else soj.setIdpersona(null);
					
					soj.setUsumodificacion(usuarios.get(0).getIdusuario());
					soj.setFechamodificacion(new Date());
					
					response = scsSojMapper.updateByPrimaryKey(soj);
					if(response == 0)throw (new Exception("Error en copyDesigna2Soj() al copiar los datos del EJG al SOJ."));
					
					LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Informacion copiada de la designacion al SOJ.");
//				} catch (Exception e) {
//					LOGGER.error("BusquedaAsuntosServiceImpl.copyEjg2Designa() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("BusquedaAsuntosServiceImpl.copyDesigna2Soj() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}
	
	ScsEjgdesigna getEjgDesigna(List<String> item, Short idInstitucion, AdmUsuarios usuario) {
		//Transformamos la lista de valores en objetos para mejor comprension
		//item = [newDesigna.ano.toString(), this.datosEJG.annio, this.datosEJG.tipoEJG, 
		//newDesigna.idTurno.toString(), newDesigna.numero.toString(), this.datosEJG.numero]
		//Designacion a asociar
		DesignaItem designaItem = new DesignaItem();

		designaItem.setAno(Integer.parseInt(item.get(0)));
		designaItem.setNumero(Integer.parseInt(item.get(4)));
		//Comprobamos si se ha enviado el nombre del turno desde 
		//la busqueda de asuntos en lugar de el id desde la ficha de designacion.
//		contains("[a-zA-Z]+")   .matches("[0-9]+")
		if(item.get(3).matches("[0-9]+") && item.get(3).length() >2) {
			designaItem.setIdTurno(Integer.parseInt(item.get(3)));
		}
		else {
			TurnosItem turnosItem = new TurnosItem();
			turnosItem.setAbreviatura(item.get(3));
			List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion);
			designaItem.setIdTurno(Integer.parseInt(turnos.get(0).getIdturno()));
		}
			
		//EJG a asociar
		EjgItem ejg = new EjgItem();
		
		ejg.setAnnio(item.get(1));
		ejg.setTipoEJG(item.get(2));
		ejg.setNumero(item.get(5));
		
		//Objeto que vamos a insertar en la base de datos
		ScsEjgdesigna record = new ScsEjgdesigna();
		
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setIdinstitucion(idInstitucion);
		record.setAnioejg(Short.parseShort(ejg.getAnnio()));
		record.setIdtipoejg(Short.parseShort(ejg.getTipoEJG()));
		record.setNumeroejg(Long.parseLong(ejg.getNumero()));
		record.setIdturno(designaItem.getIdTurno());
		record.setAniodesigna( (short) designaItem.getAno());
		//record.setNumerodesigna((long) designaItem.getNumero());
		
		//Debido a que no podemos obtener el numero de la designacion sino su codigo,
		//tendremos que realizar una busqueda extra para poder extraer el numero de la designacion.
		
		//Replicamos el codigo que se utiliza para obtener el codigo para determinar 
		//la longitud a utilizar a la hora de la busqueda.
		
		Integer longitudDesigna;
		String codigoDesigna = Integer.toString(designaItem.getNumero());

		StringDTO parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
				idInstitucion.toString());

		// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
		// la institucion 0
		if (parametros != null && parametros.getValor() != null) {
			longitudDesigna = Integer.parseInt(parametros.getValor());
		} else {
			parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
					"0");
			longitudDesigna = Integer.parseInt(parametros.getValor());
		}
		
		// Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
		while (codigoDesigna.length() < longitudDesigna) {
			codigoDesigna = "0" + codigoDesigna;
		}
		
		ScsDesignaExample designaExample = new ScsDesignaExample();
		
		designaExample.createCriteria().andAnioEqualTo((short) designaItem.getAno()).andIdinstitucionEqualTo(idInstitucion)
		.andIdturnoEqualTo(designaItem.getIdTurno()).andCodigoEqualTo(codigoDesigna);			
		
		List<ScsDesigna> designasCodigo = scsDesignaMapper.selectByExample(designaExample);				
		
		record.setNumerodesigna((long) designasCodigo.get(0).getNumero());
		
		return record;
	}
}
