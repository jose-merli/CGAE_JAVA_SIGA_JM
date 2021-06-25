package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaAsuntosService;
import org.itcgae.siga.scs.services.impl.justiciables.GestionJusticiableServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaAsuntosServiceImpl implements BusquedaAsuntosService {

	private Logger LOGGER = Logger.getLogger(BusquedaAsuntosServiceImpl.class);
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

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
					for (AsuntosJusticiableItem data : asuntosJusticiableItems) {
						String datoInteres = "";

						if (data.getTipo() != null && !data.getTipo().isEmpty()) {
							datoInteres += "<b>Tipo: </b>" + data.getTipo();
						}

						if (data.getNumProcedimiento() != null && !data.getNumProcedimiento().isEmpty()) {
							if (!datoInteres.isEmpty()) {
								datoInteres += "<p>";
							}

							datoInteres += "<b>Núm. Procedimiento: </b>" + data.getNumProcedimiento();
						}

						if (data.getNig() != null && !data.getNig().isEmpty()) {
							if (!datoInteres.isEmpty()) {
								datoInteres += "<p>";
							}

							datoInteres += "<p><b>NIG: </b>" + data.getNig();
						}

						data.setDatosInteres(datoInteres);
					}

					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
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

						if (data.getNumProcedimiento() != null && !data.getNumProcedimiento().isEmpty()) {
							if (!datoInteres.isEmpty()) {
								datoInteres += "<p>";
							}

							datoInteres += "<b>Núm. Procedimiento: </b>" + data.getNumProcedimiento();
						}

						data.setDatosInteres(datoInteres);
					}

					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
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

				if (asuntosJusticiableItems != null && asuntosJusticiableItems.size()>0) {					
					//vamos a crear los datos de interes
					for(AsuntosJusticiableItem data : asuntosJusticiableItems) {
						String datoInteres = "";
						
						if(data.getIdEstadoDesigna()!=null && !data.getIdEstadoDesigna().isEmpty()) {
							datoInteres+="<b>Estado: </b>"+data.getTipo();
						}
						
						if(data.getNumProcedimiento()!=null && !data.getNumProcedimiento().isEmpty()) {
							if(!datoInteres.isEmpty()) {
								datoInteres+="<p>";
							}
							
							datoInteres+="<b>Núm. Procedimiento: </b>"+data.getNumProcedimiento();
						}
						
						if (data.getNig() != null && !data.getNig().isEmpty()) {
							if (!datoInteres.isEmpty()) {
								datoInteres += "<p>";
							}

							datoInteres += "<p><b>NIG: </b>" + data.getNig();
						}
						
						data.setDatosInteres(datoInteres);						
					}
					
					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
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
				
				//buscamos la idpersona del colegiado
				if(asuntosJusticiableItem.getnColegiado()!=null && !asuntosJusticiableItem.getnColegiado().trim().isEmpty()) {
					String idPersonaColegiado = getIdPersonaColegiado(asuntosJusticiableItem.getnColegiado(), asuntosJusticiableItem.getIdInstitucion());
					
					if(!idPersonaColegiado.isEmpty()) {
						asuntosJusticiableItem.setIdPersonaColegiado(idPersonaColegiado);
					}
				}

				LOGGER.info(
						"searchClaveAsuntosSOJ() / scsEjgExtendsMapper.searchClaveAsuntosSOJ() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItem.setIdInstitucion(idInstitucion.toString());
				asuntosJusticiableItems = scsSojExtendsMapper.searchClaveSoj(asuntosJusticiableItem, tamMaximo, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchClaveAsuntosSOJ() / scsEjgExtendsMapper.searchClaveAsuntosSOJ() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null && asuntosJusticiableItems.size()>0) {					
					//vamos a crear los datos de interes
//					for(AsuntosJusticiableItem data : asuntosJusticiableItems) {
//						String datoInteres = "";
//						
//						if(data.getTipo()!=null && !data.getTipo().isEmpty()) {
//							datoInteres+="<b>Tipo: </b>"+data.getTipo();
//						}
//						
//						if(data.getNumProcedimiento()!=null && !data.getNumProcedimiento().isEmpty()) {
//							if(!datoInteres.isEmpty()) {
//								datoInteres+="<p>";
//							}
//							
//							datoInteres+="<b>Núm. Procedimiento: </b>"+data.getNumProcedimiento();
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
				}
//				asuntosJusticiableDTO = gestionJusticiableServiceImpl
//						.searchAsuntosConClave(asuntosJusticiableItems, false, request);
			}
		}
		LOGGER.info("searchClaveAsuntosSOJ() -> Salida del servicio para obtener los AsuntosEJG");
		
		return asuntosJusticiableDTO;
	}
}
