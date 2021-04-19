package org.itcgae.siga.scs.services.impl.oficio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.LetradoInscripcionItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaKey;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atos.utils.Row;
import com.siga.Utilidades.UtilidadesString;
import com.siga.beans.Hashtable;
import com.siga.beans.ScsDesignaBean;
import com.siga.beans.String;

@Service
public class DesignacionesServiceImpl implements IDesignacionesService {

	private Logger LOGGER = Logger.getLogger(DesignacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
	
	@Autowired
	private ScsContrariosdesignaMapper scsContrariosDesignaMapper;
	
	@Autowired 
	private ScsDefendidosdesignaMapper scsDefendidosdesignaMapper;
	
	@Autowired 
	private ScsContrariosdesignaMapper scsContrariosdesignaMapper;
	
	@Override
	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item,
			HttpServletRequest request) {
		List<JustificacionExpressItem> result = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO parametros = new StringDTO();
		String idPersona = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo longitud_codeejg...");
					// cargamos los parámetros necesarios
					String longitudCodEJG;

					// LONGITUD_CODEJG
					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", idInstitucion.toString());

					// si el ncolegiado, viene relleno, debemos obtener la idpersona
					if (item.getnColegiado() != null && !item.getnColegiado().trim().isEmpty()) {
						LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo la idpersona...");
						
						// obtenemos la idpersona
						ColegiadosSJCSItem colegiadosSJCSItem = new ColegiadosSJCSItem();
						colegiadosSJCSItem.setnColegiado(item.getnColegiado());

						List<ColegiadosSJCSItem> colegiadosSJCSItems = cenColegiadoExtendsMapper.busquedaColegiadosSJCS(idInstitucion.toString(), colegiadosSJCSItem);

						if (colegiadosSJCSItems.size() > 0) {
							idPersona = colegiadosSJCSItems.get(0).getIdPersona();
						}
					}

					
					// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
					// la institucion 0
					if (parametros != null && parametros.getValor() != null) {
						longitudCodEJG = parametros.getValor();
					} else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", "0");
						longitudCodEJG = parametros.getValor();
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo justificacion pendientes...");
					//busqueda de designaciones segun los filtros (max 200)
					result = scsDesignacionesExtendsMapper.busquedaJustificacionExpresPendientes(item, idInstitucion.toString(), longitudCodEJG, idPersona);

					
					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo las actuaciones...");
					//obtenemos las actuaciones
					
					for(JustificacionExpressItem record : result) {
						record.setActuaciones(scsDesignacionesExtendsMapper.busquedaActuacionesJustificacionExpres(record.getIdInstitucion(), record.getIdTurno(),
								record.getAnioDesignacion(), record.getNumDesignacion()));
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> tratando expedientes...");
					// cogemos los expedientes devueltos de la consulta y los tratamos para el front
					for (int i = 0; i < result.size(); i++) {
						List<String> expedientes = new ArrayList<String>();

						if (result.get(i).getEjgs() != null && !result.get(i).getEjgs().isEmpty()) {
							String[] parts = result.get(i).getEjgs().split(",");

							for (String str : parts) {
								if (str.indexOf("##") != -1) {
									expedientes.add(str.substring(0, str.indexOf("##")).trim());
								}
							}
						}

						if (expedientes.size() > 0) {
							result.get(i).setExpedientes(expedientes);
						}
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida del servicio");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.busquedaJustificacionExpres -> ERROR: al consultar datos de la bd. ",e);
				}
			}
		}

		return result;
	}

	@Override
	public List<DesignaItem> busquedaDesignas(DesignaItem designaItem, HttpServletRequest request) {
		DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaDesignas -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaDesignaciones(designaItem, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
			}
		}

		return designas;
	}	
	
	@Override
	public List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem item, HttpServletRequest request) {
		
		// [designaItem.idTurno, designaItem.nombreTurno, designaItem.numero, designaItem.anio]
				 
		LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada al servicio servicio");
		List<ListaInteresadoJusticiableItem> interesados = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		
		if (idInstitucion != null) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
		    genParametrosExample.createCriteria().andModuloEqualTo("CEN").andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO").andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
		    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
		    LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
		    tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
		    LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	        if (tamMax != null) {
	            tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
	        } else {
	            tamMaximo = null;
	        }
			
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Entrada a servicio para la busqueda de contrarios");
				try {
				interesados = scsDesignacionesExtendsMapper.busquedaListaInteresados(item, idInstitucion);
				}catch(Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
			}
		}
		
		return interesados;
	}	
	
	@Override
	public DeleteResponseDTO deleteInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteInteresado() ->  Entrada al servicio para eliminar contrarios");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					    ScsDefendidosdesignaKey key = new ScsDefendidosdesignaKey();
						key.setAnio(item.getAnio());
						key.setNumero(item.getNumero());
						key.setIdturno(item.getIdturno());
						key.setIdinstitucion(item.getIdinstitucion());
						key.setIdpersona(item.getIdpersona());

						LOGGER.info(
								"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

						response = scsDefendidosdesignaMapper.deleteByPrimaryKey(key);

						LOGGER.info(
								"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("deleteInteresado() -> Salida del servicio para eliminar contrarios");

		return deleteResponseDTO;
	}
	
	@Override
	public InsertResponseDTO insertInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteInteresado() ->  Entrada al servicio para eliminar contrarios");

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
					"insertInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					    ScsDefendidosdesigna designa = new ScsDefendidosdesigna();
						designa.setAnio(item.getAnio());
						designa.setNumero(item.getNumero());
						designa.setIdturno(item.getIdturno());
						designa.setIdinstitucion(item.getIdinstitucion());
						designa.setIdpersona(item.getIdpersona());
						designa.setFechamodificacion(new Date());
						designa.setUsumodificacion(usuarios.get(0).getIdusuario());

						
						LOGGER.info(
								"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

						response = scsDefendidosdesignaMapper.insert(designa);

						LOGGER.info(
								"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					

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
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertInteresado() -> Salida del servicio para eliminar contrarios");

		return insertResponseDTO;
	}
	

	@Override
	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request, Boolean historico) {
		LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada al servicio servicio");
		List<ListaContrarioJusticiableItem> contrarios = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaListaContrarios -> Entrada a servicio para la busqueda de contrarios");
				
				try {
					contrarios = scsDesignacionesExtendsMapper.busquedaListaContrarios(item, idInstitucion, historico);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
			}
		}

		return contrarios;
	}

	@Override
	public UpdateResponseDTO deleteContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteContrarios() ->  Entrada al servicio para eliminar contrarios");

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
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					    ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
						key.setAnio(item.getAnio());
						key.setNumero(item.getNumero());
						key.setIdturno(item.getIdturno());
						key.setIdinstitucion(item.getIdinstitucion());
						key.setIdpersona(item.getIdpersona());

						ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

						if (contrario.getFechabaja() == null) {
							contrario.setFechabaja(new Date());
						} else {
							contrario.setFechabaja(null);
						}

						contrario.setFechamodificacion(new Date());
						contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"deleteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

						response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

						LOGGER.info(
								"deleteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					

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
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}
	

	@Override
	public InsertResponseDTO insertContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("insertContrario() ->  Entrada al servicio para eliminar contrarios");

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
					"insertContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					    ScsContrariosdesigna designa = new ScsContrariosdesigna();
						designa.setAnio(item.getAnio());
						designa.setNumero(item.getNumero());
						designa.setIdturno(item.getIdturno());
						designa.setIdinstitucion(item.getIdinstitucion());
						designa.setIdpersona(item.getIdpersona());
						designa.setFechamodificacion(new Date());
						designa.setUsumodificacion(usuarios.get(0).getIdusuario());

						
						LOGGER.info(
								"insertContrario() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

						response = scsContrariosdesignaMapper.insert(designa);

						LOGGER.info(
								"insertContrario() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					

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
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertContrario() -> Salida del servicio para eliminar contrarios");

		return insertResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO updateDetalleDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDetalleDesigna() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDetalleDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDetalleDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updateDetalleDesigna()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample ejemplo = new ScsDesignaExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdprocedimientoEqualTo(designaItem.getNumProcedimiento())
							.andIdjuzgadoEqualTo(new Long(designaItem.getIdJuzgado()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(ejemplo);

					if ((designaExistentes != null && designaExistentes.size() > 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} else {

						ScsDesignaExample example = new ScsDesignaExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(designaItem.getIdTurno())
								.andAnioEqualTo((short) designaItem.getAno())
								.andNumeroEqualTo(new Long(designaItem.getNumero()));

						ScsDesigna scsDesigna = new ScsDesigna();
						List<ScsDesigna> scsDesignaLista = scsDesignacionesExtendsMapper.selectByExample(example);
						scsDesigna = scsDesignaLista.get(0);

						scsDesigna.setEstado(designaItem.getEstado());
						scsDesigna.setFechaestado(designaItem.getFechaEstado());
						scsDesigna.setFechafin(designaItem.getFechaFin()); // Fecha cierre
						scsDesigna.setNig(designaItem.getNig());
						scsDesigna.setNumprocedimiento(designaItem.getNumProcedimiento());
						scsDesigna.setIdjuzgado(new Long(designaItem.getIdJuzgado()));
						scsDesigna.setIdprocedimiento(designaItem.getIdProcedimiento());
						// TODO modulo
						scsDesigna.setDelitos(designaItem.getDelitos());

						LOGGER.info("updateDetalleDesigna() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDetalleDesigna() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

						LOGGER.info(
								"updateDetalleDesigna() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					}
				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateDetalleDesigna() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDatosAdicionales(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDatosAdicionales() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDatosAdicionales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDatosAdicionales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updateDatosAdicionales()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample ejemplo = new ScsDesignaExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdprocedimientoEqualTo(designaItem.getNumProcedimiento())
							.andIdjuzgadoEqualTo(new Long(designaItem.getIdJuzgado()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(ejemplo);

					if ((designaExistentes != null && designaExistentes.size() > 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} else {

						ScsDesignaExample example = new ScsDesignaExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(designaItem.getIdTurno())
								.andAnioEqualTo((short) designaItem.getAno())
								.andNumeroEqualTo(new Long(designaItem.getNumero()));

						ScsDesigna scsDesigna = new ScsDesigna();
						List<ScsDesigna> scsDesignaLista = scsDesignacionesExtendsMapper.selectByExample(example);
						scsDesigna = scsDesignaLista.get(0);

						scsDesigna.setFechaoficiojuzgado(designaItem.getFechaOficioJuzgado());
						// TODO faltan dos campos observaciones
						scsDesigna.setObservaciones(designaItem.getObservaciones());
						scsDesigna.setFecharecepcioncolegio(designaItem.getFechaRecepcionColegio());
						// TODO hora juicio?
						scsDesigna.setFechajuicio(designaItem.getFechaJuicio());

						LOGGER.info("updateDatosAdicionales() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					}
				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info(
						"updateDatosAdicionales() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public ActuacionDesignaDTO busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		ActuacionDesignaDTO actuacionDedignaDTO = new ActuacionDesignaDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				LOGGER.info(
						"DesignacionesServiceImpl.busquedaActDesigna() -> Se inicia la búsqueda de actuaciones asociadas a una designación");

				List<ActuacionDesignaItem> listaActuacionDesignaItem = scsDesignacionesExtendsMapper
						.busquedaActDesigna(actuacionDesignaRequestDTO, Short.toString(idInstitucion));

				actuacionDedignaDTO.setActuacionesDesignaItems(listaActuacionDesignaItem);

				LOGGER.info(
						"DesignacionesServiceImpl.busquedaActDesigna() -> Se finaliza la búsqueda de actuaciones asociadas a una designación");
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.busquedaActDesigna() -> Se ha producido un error al consultar las actuaciones asociadas a una designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			actuacionDedignaDTO.setError(error);
		}

		return actuacionDedignaDTO;
	}

	@Override
	public DeleteResponseDTO anularReactivarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			boolean anular, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.anularReactivarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.anularReactivarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for (ActuacionDesignaItem actuacionDesignaItem : listaActuacionDesignaItem) {

					if ((anular && !actuacionDesignaItem.isFacturado()) || !anular) {
						int response = scsDesignacionesExtendsMapper.anularReactivarActDesigna(actuacionDesignaItem,
								Short.toString(idInstitucion), usuarios.get(0), anular);
						responses.add(response);
					}

				}

				deleteResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al anular/reactivar las actuaciones asociadas a la designación");
					deleteResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.activarActDesigna() -> Se ha producido un error al anular/reactivar las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO createDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("createDesigna() ->  Entrada al servicio para insertar designacion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		Integer idTurnoNuevo = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);

			LOGGER.info(
					"createDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() >= 0) {
				try {

					
					ScsDesigna designa = new ScsDesigna();
					designa.setIdinstitucion(idInstitucion);
					designa.setIdturno(designaItem.getIdTurno());
					
					Calendar cal= Calendar.getInstance();
					short year= (short) cal.get(Calendar.YEAR);
					designa.setAnio(year);
					
					//CALCULO CAMPO CODIGO (NUMERO EN FRONT)
					String codigoDesigna = scsDesignacionesExtendsMapper.obtenerCodigoDesigna(
							String.valueOf(designaItem.getIdInstitucion()), String.valueOf(designaItem.getAno()));

					if (codigoDesigna.equals("")) {
						codigoDesigna = "1";
						designa.setCodigo(codigoDesigna);
					} else {
						designa.setCodigo(codigoDesigna);
					}
					
					
					//CALCULO CAMPO NUMERO 
					
					//Limitacion campo numero en updateDesigna

					//Obtenemos el parametro de limite para el campo CODIGO en BBDD
					StringDTO parametros = new StringDTO();
					Integer longitudDesigna;

					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA", idInstitucion.toString());

					// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
					// la institucion 0
					if (parametros != null && parametros.getValor() != null) {
						longitudDesigna =  Integer.parseInt(parametros.getValor())  ;
					} else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA", "0");
						longitudDesigna =  Integer.parseInt(parametros.getValor())  ;
					}
					
					//Obtenemos el ultimo numero + 1
					String numeroDesigna = scsDesignacionesExtendsMapper.obtenerNumeroDesigna(
							String.valueOf(designaItem.getIdInstitucion()), String.valueOf(designaItem.getAno()));

					
					if (numeroDesigna.equals("")) {
						//Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
						while(numeroDesigna.length() < longitudDesigna) {
							numeroDesigna = "1";
							numeroDesigna = "0" + numeroDesigna;
						}
						designa.setNumero(Long.parseLong(numeroDesigna) );
					} else {
						//Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
						while(numeroDesigna.length() < longitudDesigna) {
							numeroDesigna = "0" + numeroDesigna;
						}
						designa.setNumero(Long.parseLong(numeroDesigna));
					}
					
					
					designa.setIdtipodesignacolegio((short) designaItem.getIdTipoDesignaColegio());
					designa.setArt27(designaItem.getArt27());

					
					//ALGORITMO ELECCION LETRADO
					
					
					//SCS_INSCRIPCIONTURNO por idPersona idinstitucion idturno. 
					
					String numeroColegiado = designaItem.getNumColegiado();
					if(StringUtils.isEmpty(numeroColegiado)) {
						//Se realiza busqueda en la cola de oficio
						
//						this.getLetradoTurno();
						
//						List<TurnosItem> listadoTurnosLetrados =  scsTurnosExtendsMapper.seleccionLetradoColaOficio( designaItem.getIdTurno() , busquedaOrden, strDate, idInstitucion );
//						
////					    Se obtiene la cola de oficio actual dada por lo órdenes establecidos. Si no es la primera vez que se utiliza dicha cola para la obtención de un
////						letrado tendremos el valor ultimo letrado en la cola y con este sabremos cual es el primero que le toca (al siguiente).
////						Con esta información ya se tiene la cola ordenada. 
//						
//						
//						//Anotamos log
//						short mes= (short) cal.get(Calendar.MONTH);
//						short dia = (short) cal.get(Calendar.DAY_OF_MONTH);
//						LOGGER.info("Buscando letrado para el turno " + designaItem.getIdTurno() +   " en la fecha " + dia + "/" + mes + "/"+ year  ); 
//						
//						
//						//Si hay compensaciones se coge el letrado con la compensación mas antigua.
//						LOGGER.info("Buscando compensaciones…" ); 
//						
//						
//						//Si no hay compensaciones se coge el primer letrado de la cola 
//						LOGGER.info("Buscando en la cola" ); 
//						
//						
//						
//						ScsDesignasletrado designaLetrado = new ScsDesignasletrado();
//						designaLetrado.setIdinstitucion(idInstitucion);
//						designaLetrado.setIdturno(designaItem.getIdTurno());
//						designaLetrado.setAnio(year);
//						designaLetrado.setNumero(Long.parseLong(numeroDesigna));
//						designaLetrado.setFechadesigna(null);
//						designaLetrado.set
						
						
					}else {
						
						ScsDesignasletrado designaLetrado = new ScsDesignasletrado();
						designaLetrado.setIdinstitucion(idInstitucion);
						designaLetrado.setIdturno(designaItem.getIdTurno());
						designaLetrado.setAnio(year);
						designaLetrado.setNumero(Long.parseLong(numeroDesigna));
						designaLetrado.setFechadesigna(null);
						//designaLetrado.setId
						
					}
					
					
//
//					MANUAL = 0, LETRADODELTURNO = 1 si el sistema elige automáticamente de la cola
//
//					MANUAL = 1, LETRADODELTURNO = 1 cuando el colegiado elegido manualmente por el usuario esté inscrito en el turno (en ese momento)
//
//					MANUAL = 1, LETRADODELTURNO = 0 cuando el colegiado elegido manualmente por el usuario NO esté inscrito en el turno
					
					
					
					LOGGER.info(
							"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

					 //response = scsTurnosExtendsMapper.insert(turno);

					LOGGER.info(
							"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if (error.getDescription() == null) {
				error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(String.valueOf(idTurnoNuevo));
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("updateModules() -> Salida del servicio para insertar modulos");

		return insertResponseDTO;

	}

//	public LetradoInscripcionItem getLetradoTurno(){
//		
//		// Controles generales
//		//CenBajasTemporalesAdm bajasAdm = new CenBajasTemporalesAdm(this.usrBean);
//		//ScsSaltosCompensacionesAdm scAdm = new ScsSaltosCompensacionesAdm(this.usrBean);
//		
//		// Variables generales
//		ArrayList<String> diasGuardia; // Periodo o dia de guardia para rellenar con letrado
//		
//		HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales;
//		
//		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos; // Lista de saltos
//		
//		List<LetradoInscripcionItem> alCompensaciones; // Lista de compensaciones
//		
//		ArrayList<LetradoInscripcionItem> alLetradosOrdenados; // Cola de letrados en la guardia
//		
//		Puntero punteroListaLetrados;
//		
//		LetradoInscripcionItem unLetrado;
//		
//
//		try {
//			// obteniendo bajas temporales por letrado
//			hmBajasTemporales = bajasAdm.getLetradosDiasBajaTemporalTurno(this.idInstitucion, this.idTurno, this.fechaInicio);
//
//			// obteniendo saltos
//			hmPersonasConSaltos = scAdm.getSaltos(this.idInstitucion, this.idTurno, null);
//			diasGuardia = new ArrayList<String>();
//			diasGuardia.add(this.fechaInicio);
//
//			// obteniendo cola de letrados
//			punteroListaLetrados = new Puntero();
//			alLetradosOrdenados = (ArrayList<LetradoInscripcion>) InscripcionTurno.getColaTurno(idInstitucion, idTurno,this.fechaInicio ,false, usrBean);
//			
//
//			if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//				throw new SIGAException("No existe cola de letrados de guardia");
//
//			// obteniendo las compensaciones. Se obtienen dentro de este
//			// bucle, ya que si hay incompatibilidades se a�ade una compensacion
//			alCompensaciones = scAdm.getCompensaciones(this.idInstitucion, this.idTurno, null,this.fechaInicio);
//			// obteniendo el letrado a asignar.
//			// ATENCION: este metodo es el nucleo del proceso
//			LetradoInscripcion letradoGuardia = getSiguienteLetradoTurno(alCompensaciones, alLetradosOrdenados,
//					punteroListaLetrados, diasGuardia, hmPersonasConSaltos, hmBajasTemporales);
//			
//			
//			if (letradoGuardia == null) {
//				throw new SIGAException("gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");			}
//			int punteroUltimo = 0;
//			if (punteroListaLetrados.getValor() == 0)
//				punteroUltimo = alLetradosOrdenados.size() - 1;
//			else
//				punteroUltimo = punteroListaLetrados.getValor() - 1;
//
//			unLetrado = alLetradosOrdenados.get(punteroUltimo);
//			// actualizando el ultimo letrado en la guardia solo si no es de la lista de compensaciones
//			if (unLetrado.getSaltoCompensacion() == null){
//				ScsTurnoAdm turnoAdm = new ScsTurnoAdm(this.usrBean);
//				turnoAdm.cambiarUltimoCola(unLetrado.getIdInstitucion(), unLetrado.getIdTurno(), 
//						 unLetrado.getIdPersona(),unLetrado.getInscripcionTurno().getFechaSolicitud());
//				
//			}
//
//			return letradoGuardia;
//
//		} catch (SIGAException e) {
//			throw e;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ClsExceptions(e, e.getMessage());
//		}
//	} // calcularMatrizLetradosGuardia()
//	
//	private LetradoInscripcion getSiguienteLetradoTurno(List<LetradoInscripcion> alCompensaciones,
//			List<LetradoInscripcion> alLetradosOrdenados,
//			Puntero punteroLetrado,
//			ArrayList<String> diasGuardia,
//			HashMap<Long, ArrayList<LetradoInscripcion>> hmPersonasConSaltos,
//			HashMap<Long, TreeMap<String,CenBajasTemporalesBean>> hmBajasTemporales) throws SIGAException, ClsExceptions
//	{
//		LetradoInscripcion letradoGuardia, auxLetradoSeleccionado;
//
//		letradoGuardia = null;
//
//		// recorriendo compensaciones
//		if (alCompensaciones != null && alCompensaciones.size() > 0) {
//
//			Iterator<LetradoInscripcion> iterador = alCompensaciones.iterator();
//			while (iterador.hasNext()) {
//				auxLetradoSeleccionado = (LetradoInscripcion) iterador.next();
//				// vale
//				if (comprobarRestriccionesLetradoCompensadoTurno(auxLetradoSeleccionado, diasGuardia, iterador, null, hmBajasTemporales)) {
//					letradoGuardia = auxLetradoSeleccionado;
//					break;
//				}
//			}
//		}
//		if (letradoGuardia != null)
//			return letradoGuardia;
//
//		// recorriendo la cola
//		if (alLetradosOrdenados != null && alLetradosOrdenados.size() > 0) {
//
//			int fin = punteroLetrado.getValor();
//			do {
//				auxLetradoSeleccionado = (LetradoInscripcion) alLetradosOrdenados.get(punteroLetrado.getValor());
//				// vale
//				if (comprobarRestriccionesLetradoColaTurno(auxLetradoSeleccionado, diasGuardia, hmPersonasConSaltos, hmBajasTemporales))
//					letradoGuardia = auxLetradoSeleccionado;
//
//				// obteniendo siguiente en la cola
//				if (punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
//					punteroLetrado.setValor(punteroLetrado.getValor() + 1);
//				else
//					punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio
//
//			} while (letradoGuardia == null && fin != punteroLetrado.getValor());
//		}
//
//		if (letradoGuardia != null)
//			return letradoGuardia;
//		else
//			return null;
//	} 
	
	
	
	@Override
	public ProcuradorDTO busquedaProcurador(List<String>procurador, HttpServletRequest request) {
		LOGGER.info("searchPrisiones() -> Entrada al servicio para obtener prisiones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				String num = procurador.get(1);
				String idinstitucion = procurador.get(0);
				procuradorItemList = scsDesignacionesExtendsMapper.busquedaProcurador(num,idinstitucion);
				
				LOGGER.info(
						"searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("searchComisarias() -> Salida del servicio para obtener prisiones");
		return procuradorDTO;
	}
	
	@Override
	public ComboDTO comboTipoMotivo(HttpServletRequest request) {

		LOGGER.info("comboTipoMotivo() -> Entrada al servicio para combo comboTipoMotivo");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoMotivo() / scsDesignacionesExtendsMapper.comboTipoMotivo() -> Entrada a scsDesignacionesExtendsMapper para obtener combo TipoMotivo");

				List<ComboItem> comboItems = scsDesignacionesExtendsMapper.comboTipoMotivo(idInstitucion);

				LOGGER.info(
						"comboTipoMotivo() / scsDesignacionesExtendsMapper.comboTipoMotivo() -> Salida e scsDesignacionesExtendsMapper para obtener combo TipoMotivo");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoMotivo() -> Salida del servicio para obtener combo TipoMotivo");
		}
		return comboDTO;
	}

}