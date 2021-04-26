package org.itcgae.siga.scs.services.impl.oficio;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.LetradoInscripcionItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaLetradosDesignaItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.Puntero;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsActuaciondesigna;
import org.itcgae.siga.db.entities.ScsActuaciondesignaKey;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaKey;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.entities.ScsTipodictamenejg;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsTurnoKey;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ScsActuaciondesignaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignasletradoMapper;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
import org.itcgae.siga.db.mappers.ScsSaltoscompensacionesMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrisionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipodictamenejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

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

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private ScsTipodictamenejgExtendsMapper scsTipodictamenejgExtendsMapper;

	@Autowired
	private ScsPrisionExtendsMapper scsPrisionExtendsMapper;

	@Autowired
	private ScsActuaciondesignaMapper scsActuaciondesignaMapper;

	@Autowired
	private ScsDesignaMapper scsDesignaMapper;

	@Autowired
	private ScsSaltoscompensacionesMapper scsSaltoscompensacionesMapper;
	
	@Autowired
	private ScsTurnoMapper scsTurnoMapper;

	@Autowired
	private ScsOrdenacioncolasMapper scsOrdenacioncolasMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private ScsDesignasletradoMapper scsDesignasletradoMapper;

	/**
	 * busquedaJustificacionExpres
	 */
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

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaJustificacionExpres -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo longitud_codeejg...");
					// cargamos los parámetros necesarios
					String longitudCodEJG;

					// LONGITUD_CODEJG
					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG",
							idInstitucion.toString());

					// si el ncolegiado, viene relleno, debemos obtener la idpersona
					if (item.getnColegiado() != null && !item.getnColegiado().trim().isEmpty()) {
						LOGGER.info(
								"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo la idpersona...");

						// obtenemos la idpersona
						ColegiadosSJCSItem colegiadosSJCSItem = new ColegiadosSJCSItem();
						colegiadosSJCSItem.setnColegiado(item.getnColegiado());

						List<ColegiadosSJCSItem> colegiadosSJCSItems = cenColegiadoExtendsMapper
								.busquedaColegiadosSJCS(idInstitucion.toString(), colegiadosSJCSItem);

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

					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo justificaciones...");
					// busqueda de designaciones segun los filtros (max 200)
					result = scsDesignacionesExtendsMapper.busquedaJustificacionExpresPendientes(item,
							idInstitucion.toString(), longitudCodEJG, idPersona);

					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo las actuaciones...");
					// obtenemos las actuaciones

					for (JustificacionExpressItem record : result) {
						record.setActuaciones(scsDesignacionesExtendsMapper.busquedaActuacionesJustificacionExpres(
								record.getIdInstitucion(), record.getIdTurno(), record.getAnioDesignacion(),
								record.getNumDesignacion()));
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> tratando expedientes...");
					// obtenemos los estados para los expedientes

					List<ScsTipodictamenejg> estadosExpedientes = scsTipodictamenejgExtendsMapper
							.estadosDictamen(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());
					String idFavorable = null;
					String idDesfavorable = null;

					for (ScsTipodictamenejg tipoDictamen : estadosExpedientes) {
						if ("FAVORABLE".equalsIgnoreCase(tipoDictamen.getDescripcion())) {
							idFavorable = tipoDictamen.getIdtipodictamenejg().toString();
						} else if ("DESFAVORABLE".equalsIgnoreCase(tipoDictamen.getDescripcion())) {
							idDesfavorable = tipoDictamen.getIdtipodictamenejg().toString();
						}
					}

					// cogemos los expedientes devueltos de la consulta y los tratamos para el front
					for (int i = 0; i < result.size(); i++) {
						Map<String, String> expedientes = new HashMap<String, String>();

						if (result.get(i).getEjgs() != null && !result.get(i).getEjgs().isEmpty()) {
							String[] parts = result.get(i).getEjgs().split(",");

							for (String str : parts) {
								if (str.indexOf("##") != -1) {
									expedientes.put(str.substring(0, str.indexOf("##")).trim(),
											(str.substring(str.length() - 1) == idFavorable ? "FAVORABLE"
													: (str.substring(str.length() - 1) == idDesfavorable
															? "DESFAVORABLE"
															: "''")));
								}
							}
						}

						if (expedientes.size() > 0) {
							result.get(i).setExpedientes(expedientes);
						}
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida del servicio");
				} catch (Exception e) {
					LOGGER.error(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> ERROR: al consultar datos de la bd. ",
							e);
				}
			}
		}

		return result;
	}

	/**
	 * insertaJustificacionExpres
	 */
	@Override
	public InsertResponseDTO insertaJustificacionExpres(ActuacionesJustificacionExpressItem item,
			HttpServletRequest request) {
		InsertResponseDTO responseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.insertaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.insertaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.insertaJustificacionExpres() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Haciendo el insert...");

					ScsActuaciondesigna record = new ScsActuaciondesigna();
					
					if(item.getAnio()!=null && !item.getAnio().trim().isEmpty()){
						record.setAnio(Short.parseShort(item.getAnio()));
					}
					
					if(item.getAnioProcedimiento() !=null && !item.getAnioProcedimiento().trim().isEmpty()){
						record.setAnioprocedimiento(Short.parseShort(item.getAnioProcedimiento()));
					}
					
					if(item.getFecha()!=null && !item.getFecha().trim().isEmpty()){
						fecha = formatter.parse(item.getFecha());  
						record.setFecha(fecha);
					}
					
					if(item.getFechaJustificacion()!=null && !item.getFechaJustificacion().trim().isEmpty()){
						fecha = formatter.parse(item.getFechaJustificacion());  
						record.setFechajustificacion(fecha);
					}
					
					record.setFechamodificacion(new Date());
					
					if(item.getIdAcreditacion()!=null && !item.getIdAcreditacion().trim().isEmpty()){
						record.setIdacreditacion(Short.parseShort(item.getIdAcreditacion()));
					}
					
					if(item.getIdInstitucion()!=null && !item.getIdInstitucion().trim().isEmpty()){
						record.setIdinstitucion(Short.parseShort(item.getIdInstitucion()));
					}
				
					if(item.getIdJuzgado()!=null && !item.getIdJuzgado().trim().isEmpty()){
						record.setIdjuzgado(Long.parseLong(item.getIdJuzgado()));
					}
					
					if(item.getIdProcedimiento()!=null && !item.getIdProcedimiento().trim().isEmpty()){
						record.setIdprocedimiento(item.getIdProcedimiento());
					}
					
					if(item.getIdTurno()!=null && !item.getIdTurno().trim().isEmpty()){
						record.setIdturno(Integer.parseInt(item.getIdTurno()));
					}
					
					response = scsActuaciondesignaMapper.insertSelective(record);

					LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Insert finalizado");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.insertaJustificacionExpres() -> Se ha producido un error ",
							e);
					response = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}

		responseDTO.setError(error);

		return responseDTO;
	}

	
	/**
	 * eliminaJustificacionExpres
	 */
	public DeleteResponseDTO eliminaJustificacionExpres(List<ActuacionesJustificacionExpressItem> listaItem,
			HttpServletRequest request) {
		DeleteResponseDTO responseDTO = new DeleteResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Realizando borrado...");

					ScsActuaciondesignaKey key = new ScsActuaciondesignaKey();
					
					for(ActuacionesJustificacionExpressItem item : listaItem) {
						if(item.getAnio()!=null && !item.getAnio().isEmpty()){
							key.setAnio(Short.parseShort(item.getAnio()));
						}
						
						if(item.getIdInstitucion()!=null && !item.getIdInstitucion().isEmpty()){
							key.setIdinstitucion(Short.parseShort(item.getIdInstitucion()));
						}
						
						if(item.getIdTurno()!=null && !item.getIdTurno().isEmpty()){
							key.setIdturno(Integer.parseInt(item.getIdTurno()));
						}
						
						if(item.getNumActuacion()!=null && !item.getNumActuacion().isEmpty()){
							key.setNumero(Long.parseLong(item.getNumActuacion()));
						}
						
						if(item.getNumAsunto()!=null && !item.getNumAsunto().isEmpty()){
							key.setNumeroasunto(Long.parseLong(item.getNumAsunto()));
						}
						
						response += scsActuaciondesignaMapper.deleteByPrimaryKey(key);
					}
					
					LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Borrado finalizado");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Se ha producido un error al borrar la actuacion. ",
							e);
					response = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Saliendo del servicio");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.eliminado");
		}

		responseDTO.setError(error);

		return responseDTO;
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
	public List<DesignaItem> busquedaProcedimientoDesignas(DesignaItem designaItem, HttpServletRequest request) {
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
					"DesignacionesServiceImpl.busquedaProcedimientoDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
						"DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Entrada a servicio para la busqueda de ProcedimientoDesignas");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaProcedimientoDesignas(designaItem, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<DesignaItem> busquedaModuloDesignas(DesignaItem designaItem, HttpServletRequest request) {
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
					"DesignacionesServiceImpl.busquedaModuloDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaModuloDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
						"DesignacionesServiceImpl.busquedaModuloDesignas -> Entrada a servicio para la busqueda de ModuloDesignas");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaModuloDesignas(designaItem, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaModuloDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaModuloDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem item, HttpServletRequest request) {

		// [designaItem.idTurno, designaItem.nombreTurno, designaItem.numero,
		// designaItem.anio]

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
					interesados = scsDesignacionesExtendsMapper.busquedaListaInteresados(item, idInstitucion);
				} catch (Exception e) {
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
								"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

						ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
						scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
						scsPersonajgkey.setIdinstitucion(idInstitucion);

						ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						LOGGER.info(
								"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");

						//Se comprueba si tiene representante y se busca.
						if(personajg.getIdrepresentantejg()!=null) {

							scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

	
							ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);
							
							designa.setNombrerepresentante(representante.getApellido1()+" "+representante.getApellido2()+", "+representante.getNombre());
						}
						
						
						LOGGER.info(
								"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");


					response = scsDefendidosdesignaMapper.insert(designa);

					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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
	public UpdateResponseDTO updateRepresentanteInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
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
					"updateRepresentanteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRepresentanteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsDefendidosdesignaKey key = new ScsDefendidosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsDefendidosdesigna interesado = scsDefendidosdesignaMapper.selectByPrimaryKey(key);

					interesado.setNombrerepresentante(item.getNombrerepresentante());

					interesado.setFechamodificacion(new Date());
					interesado.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateRepresentanteInteresado() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					response = scsDefendidosdesignaMapper.updateByPrimaryKey(interesado);

					LOGGER.info(
							"updateRepresentanteInteresado() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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

		LOGGER.info("updateRepresentanteInteresado() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO updateRepresentanteContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("updateRepresentanteContrario() ->  Entrada al servicio para eliminar contrarios");

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
					"updateRepresentanteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRepresentanteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					
					//for(ScsContrariosdesigna item: items) {

						ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
						key.setAnio(item.getAnio());
						key.setNumero(item.getNumero());
						key.setIdturno(item.getIdturno());
						key.setIdinstitucion(item.getIdinstitucion());
						key.setIdpersona(item.getIdpersona());

						ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

						
						contrario.setNombrerepresentante(item.getNombrerepresentante());
						

						contrario.setFechamodificacion(new Date());
						contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"updateRepresentanteContrario() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

						response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

						LOGGER.info(
								"updateRepresentanteContrario() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					//}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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

		LOGGER.info("updateRepresentanteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateAbogadoContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("updateAbogadoContrario() ->  Entrada al servicio para eliminar contrarios");

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
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					
					//for(ScsContrariosdesigna item: items) {

						ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
						key.setAnio(item.getAnio());
						key.setNumero(item.getNumero());
						key.setIdturno(item.getIdturno());
						key.setIdinstitucion(item.getIdinstitucion());
						key.setIdpersona(item.getIdpersona());

						ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

						
						contrario.setIdabogadocontrario(item.getIdabogadocontrario());
						contrario.setNombreabogadocontrario(item.getNombreabogadocontrario());

						contrario.setFechamodificacion(new Date());
						contrario.setUsumodificacion(usuarios.get(0).getIdusuario());
						
//						List<ColegiadoItem> colegiadosItems = cenColegiadoExtendsMapper.selectColegiadosByIdPersona(idInstitucion, contrario.getIdabogadocontrario().toString());
//						
//						FichaDatosColegialesItem abogado = colegiadosItems.get(0);
						
						
//						contrario.set

						LOGGER.info(
								"updateAbogadoContrario() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

						response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

						LOGGER.info(
								"updateAbogadoContrario() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					//}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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

		LOGGER.info("updateAbogadoContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}
	
	@Override
	public  ColegiadoItemDTO SearchAbogadoByIdPersona(String idPersona, HttpServletRequest request) {
		LOGGER.info("updateAbogadoContrario() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		
		 ColegiadoItemDTO abogado = new  ColegiadoItemDTO();
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					
					
						
						List<ColegiadoItem> colegiadosItems = cenColegiadoExtendsMapper.selectColegiadosByIdPersona(idInstitucion, idPersona);
						
						abogado.setColegiadoItem(colegiadosItems.get(0));

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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

		abogado.setError(error);

		LOGGER.info("updateAbogadoContrario() -> Salida del servicio para eliminar contrarios");

		return abogado;
	}
	
	@Override
	public UpdateResponseDTO updateProcuradorContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("updateProcuradorContrario() ->  Entrada al servicio para eliminar contrarios");

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
					"updateProcuradorContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateProcuradorContrarioo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					
					//for(ScsContrariosdesigna item: items) {

						ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
						key.setAnio(item.getAnio());
						key.setNumero(item.getNumero());
						key.setIdturno(item.getIdturno());
						key.setIdinstitucion(item.getIdinstitucion());
						key.setIdpersona(item.getIdpersona());

						ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

						
						
						contrario.setIdprocurador(item.getIdprocurador());
						
						List<FichaDatosColegialesItem> colegiadosSJCSItems = cenColegiadoExtendsMapper.selectDatosColegiales(item.getIdprocurador().toString(),idInstitucion.toString());
						
						FichaDatosColegialesItem procurador = colegiadosSJCSItems.get(0);
//						contrario.set


						contrario.setFechamodificacion(new Date());
						contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"updateProcuradorContrario() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

						response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

						LOGGER.info(
								"updateProcuradorContrario() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					//}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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

		LOGGER.info("updateProcuradorContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}
	
	@Override
	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request,
			Boolean historico) {
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

					// for(ScsContrariosdesigna item: items) {

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

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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
							"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info(
							"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");

					//Se comprueba si tiene representante y se busca.
					if(personajg.getIdrepresentantejg()!=null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());


						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);
						
						designa.setNombrerepresentante(representante.getApellido1()+" "+representante.getApellido2()+", "+representante.getNombre());
					}
					
					
					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosdesignaMapper.insert(designa);

					LOGGER.info(
							"insertContrario() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
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

					LOGGER.info("updateDatosAdicionales()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample example = new ScsDesignaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designaItem.getIdTurno())
					.andAnioEqualTo((short) designaItem.getAno())
					.andNumeroEqualTo(new Long(designaItem.getNumero()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(example);

					if ((designaExistentes == null && designaExistentes.size() == 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} 

						ScsDesigna scsDesigna = new ScsDesigna();
						
						scsDesigna.setIdturno(designaItem.getIdTurno());
						Integer a = new Integer(idInstitucion);
						scsDesigna.setIdinstitucion(idInstitucion);
						a = designaItem.getAno();
						scsDesigna.setAnio(a.shortValue());
						Long b = new Long(designaItem.getNumero());
						scsDesigna.setNumero(b.longValue());

						if(designaItem.getFechaAnulacion() == null) {
							scsDesigna.setEstado(designaItem.getEstado());
							scsDesigna.setFechaestado(designaItem.getFechaEstado());
						}else {
						      scsDesigna.setNig(designaItem.getNig());
						      scsDesigna.setNumprocedimiento(designaItem.getNumProcedimiento());
						      scsDesigna.setEstado(designaItem.getEstado());
						      Long juzgado = new Long(designaItem.getIdJuzgado());
						      scsDesigna.setIdjuzgado(juzgado);
						      Short idPretension = new Short((short) designaItem.getIdPretension());
						      scsDesigna.setIdpretension(idPretension);
						      scsDesigna.setIdprocedimiento(designaItem.getIdProcedimiento());
						      scsDesigna.setDelitos(designaItem.getDelitos());
						      scsDesigna.setFechaestado(designaItem.getFechaEstado());
						      scsDesigna.setFechafin(designaItem.getFechaFin());
						    		  
						}

						LOGGER.info("updateDatosAdicionales() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					
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
					ScsDesignaExample example = new ScsDesignaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designaItem.getIdTurno())
					.andAnioEqualTo((short) designaItem.getAno())
					.andNumeroEqualTo(new Long(designaItem.getNumero()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(example);

					if ((designaExistentes == null && designaExistentes.size() == 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} 

						ScsDesigna scsDesigna = new ScsDesigna();
						scsDesigna.setIdturno(designaItem.getIdTurno());
						Integer a = new Integer(idInstitucion);
						scsDesigna.setIdinstitucion(idInstitucion);
						a = designaItem.getAno();
						scsDesigna.setAnio(a.shortValue());
						Long b = new Long(designaItem.getNumero());
						scsDesigna.setNumero(b.longValue());

						scsDesigna.setFechaoficiojuzgado(designaItem.getFechaOficioJuzgado());
						scsDesigna.setDelitos(designaItem.getDelitos());
						// TODO faltan dos campos observaciones
						scsDesigna.setObservaciones(designaItem.getObservaciones());
						scsDesigna.setFecharecepcioncolegio(designaItem.getFechaRecepcionColegio());
						// TODO hora juicio?
						scsDesigna.setFechajuicio(designaItem.getFechaJuicio());
						scsDesigna.setDefensajuridica(designaItem.getDefensaJuridica());

						LOGGER.info("updateDatosAdicionales() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					
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
	public UpdateResponseDTO updatePartidaPresupuestaria(DesignaItem designaItem, HttpServletRequest request) {
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
					"updatePartidaPresupuestaria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updatePartidaPresupuestaria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updatePartidaPresupuestaria()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsTurnoExample example = new ScsTurnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designaItem.getIdTurno());

					List<ScsTurno> turnoExistente = scsTurnosExtendsMapper.selectByExample(example);

					if ((turnoExistente == null && turnoExistente.size() == 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} 

						ScsTurno scsTurno = new ScsTurno();
						scsTurno.setIdturno(designaItem.getIdTurno());
						Integer a = new Integer(idInstitucion);
						scsTurno.setIdinstitucion(idInstitucion);

						scsTurno.setIdpartidapresupuestaria(designaItem.getIdPartidaPresupuestaria());

						LOGGER.info("updatePartidaPresupuestaria() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updatePartidaPresupuestaria() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

						scsTurnosExtendsMapper.updateByPrimaryKeySelective(scsTurno);

						LOGGER.info(
								"updatePartidaPresupuestaria() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					
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
						"updatePartidaPresupuestaria() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO anularReactivarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			boolean anular, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

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
						int response = scsDesignacionesExtendsMapper.anularReactivarActDesigna(actuacionDesignaItem, idInstitucion, usuarios.get(0), anular);
						responses.add(response);
					}

				}

				updateResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar alguna de las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al anular/reactivar alguna de las actuaciones asociadas a la designación");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, boolean validar,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.validarDesvalidarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.validarDesvalidarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				int response = scsDesignacionesExtendsMapper.validarDesvalidarActDesigna(actuacionDesignaItem,
						Short.toString(idInstitucion), usuarios.get(0), validar);

				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.validarDesvalidarActDesigna() -> Se ha producido un error al validar/desvalidar la actuación asociada a la designación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al validar/desvalidar la actuación asociada a la designación");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.validarDesvalidarActDesigna() -> Se ha producido un error al validar/desvalidar la actuación asociada a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for (ActuacionDesignaItem actuacionDesignaItem : listaActuacionDesignaItem) {

					int response = scsDesignacionesExtendsMapper.eliminarActDesigna(actuacionDesignaItem, idInstitucion, usuarios.get(0));
					responses.add(response);

				}

				deleteResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al eliminar alguna de las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al eliminar alguna las actuaciones asociadas a la designación");
					deleteResponseDTO.setError(error);
				} else {
					error.setCode(200);
					error.setDescription("messages.deleted.selected.success");
					deleteResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al anular las actuaciones asociadas a la designación",
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
	public InsertResponseDTO guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.guardarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.guardarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {
				
				MaxIdDto maxIdDto = scsDesignacionesExtendsMapper.getNewIdActuDesigna(actuacionDesignaItem, idInstitucion);

				int response = scsDesignacionesExtendsMapper.guardarActDesigna(actuacionDesignaItem,
						Short.toString(idInstitucion), usuarios.get(0));

				if (response == 1) {
					insertResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.guardarActDesigna() -> Se ha producido un error al guardar la actuación asociada a la designación");
					error.setCode(500);
					error.setDescription("Se ha producido un error al guardar la actuación asociada a la designación");
					insertResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.guardarActDesigna() -> Se ha producido un error al guardar la actuación asociada a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO actualizarActDesigna(ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.actualizarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.actualizarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				int response = scsDesignacionesExtendsMapper.actualizarActDesigna(actuacionDesignaItem,
						Short.toString(idInstitucion), usuarios.get(0));

				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.actualizarActDesigna() -> Se ha producido un error al actualizar la actuación asociada a la designación");
					error.setCode(500);
					error.setDescription("Se ha producido un error al actualizar la actuación asociada a la designación");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.actualizarActDesigna() -> Se ha producido un error al actualizar la actuación asociada a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO createDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("createDesigna() ->  Entrada al servicio para insertar designacion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		String numeroDesigna = "";
		
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
							String.valueOf(idInstitucion), String.valueOf(designaItem.getAno()));

					if (codigoDesigna != null && !codigoDesigna.equals("")) {
						designa.setCodigo(codigoDesigna);
					} else {
						codigoDesigna = "1";
						designa.setCodigo(codigoDesigna);
						
					}
					
					designa.setFechaentrada(new Date());
					designa.setFechamodificacion(new Date());
					designa.setUsumodificacion(usuario.getIdusuario());
					designa.setEstado("A");
					designa.setFechaestado(new Date());
					designa.setFechaalta(new Date());
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
					 numeroDesigna = scsDesignacionesExtendsMapper.obtenerNumeroDesigna(
							String.valueOf(idInstitucion), String.valueOf(designaItem.getAno()));

					
					if (numeroDesigna == null) {
						numeroDesigna = "1";
						//Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
						while(numeroDesigna.length() < longitudDesigna) {
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

					
					//SCS_INSCRIPCIONTURNO por idPersona idinstitucion idturno. 
					ScsDesignasletrado designaLetrado = new ScsDesignasletrado();
					String numeroColegiado = designaItem.getNumColegiado();
					if(StringUtils.isEmpty(numeroColegiado)) {
						//Se realiza busqueda en la cola de oficio
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						String fechaform = sdf.format(designaItem.getFechaAlta());
						
						LetradoInscripcionItem letradoAlgoritmoSeleccion = 	this.getLetradoTurno( idInstitucion.toString(), String.valueOf(designaItem.getIdTurno()), fechaform,  usuario);
						
//						//Anotamos log
						short mes= (short) cal.get(Calendar.MONTH);
						short dia = (short) cal.get(Calendar.DAY_OF_MONTH);
						LOGGER.info("Buscando letrado para el turno " + designaItem.getIdTurno() +   " en la fecha " + dia + "/" + mes + "/"+ year  ); 
//						
//						//MANUAL = 0, LETRADODELTURNO = 1 si el sistema elige automáticamente de la cola
//						
						designaLetrado.setIdinstitucion(idInstitucion);
						designaLetrado.setIdturno(designaItem.getIdTurno());
						designaLetrado.setAnio(year);
						designaLetrado.setNumero(Long.parseLong(numeroDesigna));
						designaLetrado.setFechadesigna(new Date());
						designaLetrado.setFechamodificacion(new Date());
						designaLetrado.setUsumodificacion(usuario.getIdusuario());
						designaLetrado.setIdpersona(letradoAlgoritmoSeleccion.getIdpersona());
						designaLetrado.setManual((short)0);
						designaLetrado.setLetradodelturno("1");
						
					}else {
						
						designaLetrado.setIdinstitucion(idInstitucion);
						designaLetrado.setIdturno(designaItem.getIdTurno());
						designaLetrado.setAnio(year);
						designaLetrado.setNumero(Long.parseLong(numeroDesigna));
						designaLetrado.setFechadesigna(new Date());
						designaLetrado.setFechamodificacion(new Date());
						designaLetrado.setUsumodificacion(usuario.getIdusuario());					
						designaLetrado.setManual((short)1);
						//TODO 
//						MANUAL = 1, LETRADODELTURNO = 1 cuando el colegiado elegido manualmente por el usuario esté inscrito en el turno (en ese momento)
//						MANUAL = 1, LETRADODELTURNO = 0 cuando el colegiado elegido manualmente por el usuario NO esté inscrito en el turno
						designaLetrado.setLetradodelturno("1");
						String idPersona = scsDesignacionesExtendsMapper.obtenerIdPersonaByNumCol(idInstitucion.toString(), numeroColegiado);
						designaLetrado.setIdpersona(Long.parseLong(idPersona));
						
					}
					
					
					
					LOGGER.info(
							"createDesigna() / scsDesignaMapper.insert() -> Entrada a scsDesignaMapper para insertar la designacion");

					  scsDesignaMapper.insert(designa);

					LOGGER.info(
							"createDesigna() / scsDesignaMapper.insert() -> Salida de scsDesignaMapper para insertar la designacion");

					LOGGER.info(
							"createDesigna() / scsDesignasletradoMapper.insert() -> Entrada a scsDesignasletradoMapper para insertar designaLetrado");

					
					scsDesignasletradoMapper.insert(designaLetrado);
					
					LOGGER.info(
							"createDesigna() / scsDesignasletradoMapper.insert() -> Salida de scsDesignasletradoMapper para insertar designaLetrado");


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
			insertResponseDTO.setId(numeroDesigna);
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("createDesigna() -> Salida del servicio para insertar modulos");

		return insertResponseDTO;

	}
	
	@Override
	public DeleteResponseDTO deleteDesigna(List<DesignaItem> item, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;
		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for(DesignaItem designa: item) {


					ScsDesignaKey key = new ScsDesignaKey();
					Short anio =  new Short((short) designa.getAno());
					key.setAnio(anio);
					key.setNumero(new Long(designa.getNumero()));
					key.setIdturno(designa.getIdTurno());
					key.setIdinstitucion(idInstitucion);

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsDesignacionesExtendsMapper.deleteByPrimaryKey(key);

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} 
				deleteResponseDTO.setStatus(SigaConstants.OK);

				if (response == 0) {
					error.setCode(400);
					error.setDescription("areasmaterias.materias.ficha.eliminarError");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				} else {
					error.setCode(200);
					error.setDescription("general.message.registro.actualizado");
				}

				deleteResponseDTO.setError(error);


			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al anular las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		return deleteResponseDTO;
	}
			
			

	public LetradoInscripcionItem getLetradoTurno(String idInstitucion, String idTurno, String fechaForm, AdmUsuarios usuario) throws java.lang.Exception{
		
		// Variables generales
		ArrayList<String> diasGuardia; // Periodo o dia de guardia para rellenar con letrado
		
		List<BajasTemporalesItem> hmBajasTemporalesList;
		
		HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales; //variable siga classique
		
		List<LetradoInscripcionItem> hmPersonasConSaltosList; // Lista de saltos
		
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos;  //variable siga classique
		
		List<LetradoInscripcionItem> alCompensaciones; // Lista de compensaciones
		
		ArrayList<LetradoInscripcionItem> alLetradosOrdenados; // Cola de letrados en la guardia
		
		Puntero punteroListaLetrados;
		
		LetradoInscripcionItem unLetrado;
		
		LetradoInscripcionItem letradoGuardia;

		try {
			
			
			// obteniendo bajas temporales por letrado
			hmBajasTemporalesList = scsDesignacionesExtendsMapper.getLetradosDiasBajaTemporalTurno(idInstitucion, idTurno, fechaForm);
			
			hmBajasTemporales = this.getLetradosDiasBajaTemporalTurnoHash(hmBajasTemporalesList);
			
			
			// obteniendo saltos
			hmPersonasConSaltosList = scsDesignacionesExtendsMapper.getSaltos(idInstitucion, idTurno, null);
			
			hmPersonasConSaltos = this.getSaltosHash(hmPersonasConSaltosList, Integer.parseInt(idInstitucion), Integer.parseInt(idTurno), null,usuario);
			
			diasGuardia = new ArrayList<String>();
			diasGuardia.add(fechaForm);

			// obteniendo cola de letrados
			punteroListaLetrados = new Puntero();
			alLetradosOrdenados = (ArrayList<LetradoInscripcionItem>) this.getColaTurno(Integer.parseInt(idInstitucion), Integer.parseInt(idTurno),fechaForm ,false, usuario);
			

			if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0) {
				throw new Exception("No existe cola de letrados de guardia");
				
			}
			

			// obteniendo las compensaciones. Se obtienen dentro de este
			// bucle, ya que si hay incompatibilidades señade una compensacion
			alCompensaciones =  this.getLogicaCompensaciones( idInstitucion,  idTurno,  fechaForm);
			
			// obteniendo el letrado a asignar.
			// ATENCION: este metodo es el nucleo del proceso
			letradoGuardia = getSiguienteLetradoTurno(alCompensaciones, alLetradosOrdenados,
					punteroListaLetrados, diasGuardia, hmPersonasConSaltos, hmBajasTemporales,  idInstitucion,  idTurno,  null, null ,  usuario);
			
			
			if (letradoGuardia == null) {
				 throw new  Exception("gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
			}
			
			int punteroUltimo = 0;
			
			if (punteroListaLetrados.getValor() == 0)
				punteroUltimo = alLetradosOrdenados.size() - 1;
			else
				punteroUltimo = punteroListaLetrados.getValor() - 1;

			unLetrado = alLetradosOrdenados.get(punteroUltimo);
			
			// actualizando el ultimo letrado en la guardia solo si no es de la lista de compensaciones
			if (unLetrado.getSaltoocompensacion() == null){
				
				scsDesignacionesExtendsMapper.cambiarUltimoCola(unLetrado.getIdinstitucion().toString(), unLetrado.getIdturno().toString(),  unLetrado.getIdpersona().toString(), unLetrado.getInscripcionTurno().getFechasolicitud(), usuario);
				
			}

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		
		return letradoGuardia;
	} 
	
	
	
	public CenPersonaItem getPersonaPorId (String idPersona) throws Exception {

		
		try {

			CenPersona cenPersona = null;
			CenPersonaItem cenPersonaItem = new CenPersonaItem();
			
			CenPersonaExample cenPersonaExample  = new CenPersonaExample();
			cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona));
			
			List<CenPersona> personas = cenPersonaMapper.selectByExample(cenPersonaExample);
			
			
			if ((personas != null) && personas.size() == 1) {
				cenPersona = personas.get(0);
			}
			
			if(cenPersona != null) {
				cenPersonaItem.setApellidos1(cenPersona.getApellidos1());
				cenPersonaItem.setApellidos2(cenPersona.getApellidos2());
				cenPersonaItem.setFallecido(cenPersona.getFallecido());
				cenPersonaItem.setFechanacimiento(cenPersona.getFechanacimiento());
				cenPersonaItem.setIdestadocivil(cenPersona.getIdestadocivil());
				cenPersonaItem.setIdpersona(cenPersona.getIdpersona());
				cenPersonaItem.setIdtipoidentificacion(cenPersona.getIdtipoidentificacion());
				cenPersonaItem.setNaturalde(cenPersona.getNaturalde());
				cenPersonaItem.setNifcif(cenPersona.getNifcif());
				cenPersonaItem.setNombre(cenPersona.getNombre());
				cenPersonaItem.setSexo(cenPersona.getSexo());
				
			}
			
			return cenPersonaItem;
		}
		catch (Exception e) {
			throw new Exception ( "Error al recuperar los datos", e);
		}
	}
		
	

	
	/**
	 * Obtiene la cola de turno dada una fecha
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @param fecha
	 * @param usr
	 * @return
	 * @throws Exception 
	 * @throws ClsExceptions
	 */
	public  List<LetradoInscripcionItem> getColaTurno(Integer idInstitucion, Integer idTurno, String fecha, boolean quitarSaltos, AdmUsuarios usr) throws Exception {
		try {
			
			ArrayList<LetradoInscripcionItem> colaLetrados = new ArrayList<LetradoInscripcionItem>();
//			ScsInscripcionTurnoAdm insadm = new ScsInscripcionTurnoAdm(usr);
//			ScsSaltosCompensacionesAdm saladm = new ScsSaltosCompensacionesAdm(usr);
//			ScsOrdenacionColasAdm ordenacionColasAdm = new ScsOrdenacionColasAdm(usr);
			
			
			// obteniendo la guardia
			ScsTurnoKey scsTurnoKey = new ScsTurnoKey();
			scsTurnoKey.setIdinstitucion(Short.valueOf(idInstitucion.toString()));
			scsTurnoKey.setIdturno(idTurno);
			
			ScsTurno beanTurno = scsTurnoMapper.selectByPrimaryKey(scsTurnoKey);
			
			Integer idOrdenacionColas = beanTurno.getIdordenacioncolas();
			if(idOrdenacionColas==null)
				throw new Exception("messages.general.error");
			
			Long idPersonaUltimo = beanTurno.getIdpersonaUltimo();
			
			// obteniendo ordenacion de la guardia
			String orden = this.getOrderBy(idOrdenacionColas.toString());
			
			InscripcionTurnoItem ultimoAnterior;
			
			// obteniendo ultimo apuntado de la guardia
			if (idPersonaUltimo == null) {
				ultimoAnterior = null;
			}else {
				ultimoAnterior = new InscripcionTurnoItem(Short.valueOf(idInstitucion.toString()), idTurno, idPersonaUltimo, beanTurno.getFechasolicitudUltimo());
			}
				

			Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String fechaBBDD2 = formatter.format(fechaBBDD);
	//		obteniendo lista de letrados (ordenada)
			List<InscripcionTurnoItem> listaLetrados = scsDesignacionesExtendsMapper.getColaTurnoBBDD(idInstitucion.toString(), idTurno.toString(), fechaBBDD2, orden);
			
//			Format formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			
//			Date fechaprueba = listaLetrados.get(0).getFechasolicitud();
//			LOGGER.info(formatter2.format(fechaprueba));
			
			if (listaLetrados == null || listaLetrados.size()==0) {
				return colaLetrados;
			}
			
			
			InscripcionTurnoItem punteroInscripciones = null;
			boolean foundUltimo;
			LetradoInscripcionItem letradoTurno;
			if (ultimoAnterior == null) {
				// si no existe ultimo colegiado, se empieza la cola desde el
				// primero en la lista
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = (InscripcionTurnoItem) listaLetrados.get(i);
					if (punteroInscripciones.getEstado().equals("1")) {
						colaLetrados.add(new LetradoInscripcionItem(punteroInscripciones));
					}
				}
			} else {
				// ordenando la cola en funcion del idPersonaUltimo guardado
				List<LetradoInscripcionItem> colaAuxiliar = new ArrayList<LetradoInscripcionItem>();
				foundUltimo = false;
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = listaLetrados.get(i);
	
					// insertando en la cola si la inscripcion esta activa
					if (punteroInscripciones.getEstado().equals("1")) {
						// El primero que se anyade es el siguiente al ultimo
						if (foundUltimo) {
							colaLetrados.add(new LetradoInscripcionItem(punteroInscripciones));
						} else {
							colaAuxiliar.add(new LetradoInscripcionItem(punteroInscripciones));
						}
					}
	
					// revisando si se encontro ya al ultimo
					if (!foundUltimo && punteroInscripciones.equals(ultimoAnterior)) {
						foundUltimo = true;
					}
						
				}
				colaLetrados.addAll(colaAuxiliar);
			}
			
			
			// quitando letrados de la cola si tienen saltos
			if (quitarSaltos) {
				List<LetradoInscripcionItem> personasConSaltosList = scsDesignacionesExtendsMapper.getSaltos(idInstitucion.toString(), idTurno.toString(), null);
				HashMap<Long, ArrayList<LetradoInscripcionItem>> personasConSaltos = this.getSaltosHash(personasConSaltosList,  idInstitucion,  idTurno,  null,  usr); 
				for (Iterator iter = colaLetrados.iterator(); iter.hasNext(); ) {
					letradoTurno = (LetradoInscripcionItem) iter.next();
					if ( personasConSaltos.get(letradoTurno.getIdpersona()) != null ) {
						iter.remove();
					}
				}
			}
			
			return colaLetrados;
			
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception ( "Error al ejecutar getColaTurno()", e);
		}			
	} 
	
	private HashMap<Long, TreeMap<String,BajasTemporalesItem>> getLetradosDiasBajaTemporalTurnoHash(List<BajasTemporalesItem> hmBajasTemporalesList) throws java.lang.Exception{
		
		HashMap<Long, TreeMap<String, BajasTemporalesItem>> mSalida = new HashMap<Long, TreeMap<String, BajasTemporalesItem>>();
		
		for (int i = 0; i < hmBajasTemporalesList.size(); i++) {
			
		
			
			BajasTemporalesItem bajasBean = hmBajasTemporalesList.get(i);
			
			Long idPersona = new Long(bajasBean.getIdpersona());
			String fechaBT = bajasBean.getFechabt().toString();
			
			TreeMap<String, BajasTemporalesItem> bajasDePersona = (TreeMap<String, BajasTemporalesItem>) mSalida.get(idPersona);
			
			if (bajasDePersona != null)
				bajasDePersona.put(this.getFormatedDateShort("", fechaBT), bajasBean);
			else {
				bajasDePersona = new TreeMap<String, BajasTemporalesItem>();
				bajasDePersona.put(this.getFormatedDateShort("", fechaBT), bajasBean);
				mSalida.put(idPersona, bajasDePersona);
			}
		}
		
		
		return mSalida;
	}
	
	
	private  String getFormatedDateShort(String lang, String date) throws java.lang.Exception {
		String dat = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date datFormat = null;

		if (date != null && !date.trim().equals("")) {
			try {

				// DCG date = GstDate.getFechaLenguaje(lang, date);
				datFormat = sdf.parse(date.trim());
			} catch (Exception ex) {
				Exception exc = new Exception( "THIS DATE " + date + " IS BAD FORMATED");
				//exc.setErrorCode("DATEFORMAT");
				throw exc;
			}

			if (lang.equalsIgnoreCase("EN")) {
				sdf.applyPattern("dd/MM/yyyy");
			} else {
				sdf.applyPattern("dd/MM/yyyy");
			}
			dat = sdf.format(datFormat);
		}
		return dat;
	}
	
	private  String getApplicationFormatDate(String lang, String date)
			throws Exception {
		if (date == null || date.length() == 0)
			return null;
		String dat = "";
		Date oDate = null;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (date.length() == 11 && date.substring(2, 3).equals(".")) {
			String DATE_FORMAT_LOGBOOK = "dd.MMM.yyyy";
			if (lang.equalsIgnoreCase("EN")) {
				oDate =  this.parseStringToDate(date,
						DATE_FORMAT_LOGBOOK, Locale.ENGLISH);
			} else {
				// Espanhol oDate = new
				// GstDate().parseStringToDate(date,DATE_FORMAT_LOGBOOK,Locale.ENGLISH);
			}
		} else if (date.length() == 20 && date.substring(4, 5).equals("-")) {
			String DATE_FORMAT_LOGBOOK = "dd.MMM.yyyy";
			if (lang.equalsIgnoreCase("EN")) {
				oDate =  this.parseStringToDate(date,
						DATE_FORMAT_LOGBOOK, Locale.ENGLISH);
			} else {
				// Espanhol oDate=new
				// GstDate().parseStringToDate(date,DATE_FORMAT_LOGBOOK,Locale.ENGLISH);
			}
		} else {
			if (date.length() > 11) {
				// LONG
				if (lang.equalsIgnoreCase("EN")) {
					sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				} else {
					sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				}
			} else {
				// SHORT
				if (lang.equalsIgnoreCase("EN")) {
					sdf = new SimpleDateFormat("dd/MM/yyyy");
				} else {
					sdf = new SimpleDateFormat("dd/MM/yyyy");
				}
			}

			try {
				oDate = sdf.parse(date);
			} catch (Exception ex) {
				Exception exc = new Exception( "THIS DATE " + date
						+ " IS BAD FORMATED",ex);
				//exc.setErrorCode("DATEFORMAT");
				throw exc;
			}
		}
		if (oDate == null) {
			sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
			try {
				oDate = sdf.parse(date);
			} catch (Exception ex) {
				Exception exc = new Exception( "THIS DATE " + date
						+ " IS BAD FORMATED",ex);
				//exc.setErrorCode("DATEFORMAT");
				throw exc;
			}
		}

		sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
		dat = sdf.format(oDate);
		return dat;
	}

	private Date parseStringToDate(String date, String format, Locale locale)
			throws Exception {
		Date dat = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		try {
			dat = sdf.parse(date);
		} catch (Exception e) {
			Exception exc = new Exception( "error en GstDate     :"+ e.toString(),e);
			//exc.setErrorCode("DATEFORMAT");
			throw exc;
		}
		return dat;
	}
	
	
	private List<LetradoInscripcionItem> getLogicaCompensaciones(String idInstitucion, String idTurno,
			String fechaForm) throws Exception {

		List<LetradoInscripcionItem> alLetradosCompensados;
		LetradoInscripcionItem letradoSeleccionado;
		String idPersona;


		alLetradosCompensados = scsDesignacionesExtendsMapper.getCompensaciones(idInstitucion, idTurno, fechaForm);

		try {

			for (LetradoInscripcionItem elem : alLetradosCompensados) {

				idPersona = elem.getIdpersona().toString();

				
				 Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(fechaForm);
				 Format formatter = new SimpleDateFormat("dd/MM/yyyy");
				 String fechaBBDD2 = formatter.format(fechaBBDD);
				
				InscripcionTurnoItem inscripcionTurno = scsDesignacionesExtendsMapper.getInscripcionTurnoActiva(idInstitucion.toString(), idTurno.toString(), idPersona, fechaBBDD2);
				
				if (inscripcionTurno == null) {
					continue;
				}

				letradoSeleccionado = new LetradoInscripcionItem();

				letradoSeleccionado.setIdpersona(inscripcionTurno.getIdpersona());
				letradoSeleccionado.setIdinstitucion(Short.valueOf(inscripcionTurno.getIdinstitucion().toString()));
				letradoSeleccionado.setIdturno(inscripcionTurno.getIdturno());
				letradoSeleccionado.setInscripcionTurno(inscripcionTurno);
				letradoSeleccionado.setSaltoocompensacion("C");
				alLetradosCompensados.add(letradoSeleccionado);

			}

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception("Error al obtener letrados compensados  en BD.", e);
		}

		return alLetradosCompensados;
	}
	
	private HashMap<Long, ArrayList<LetradoInscripcionItem>> getSaltosHash(List<LetradoInscripcionItem> hmPersonasConSaltosList, Integer idInstitucion, Integer idTurno, Integer idGuardia, AdmUsuarios usuario) throws Exception{
		
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
		ArrayList<LetradoInscripcionItem> alLetradosSaltados;
		
		for (int i = 0; i < hmPersonasConSaltosList.size(); i++) {
			
			LetradoInscripcionItem htFila = hmPersonasConSaltosList.get(i);

			Long idPersona =  htFila.getIdpersona();
			
			LetradoInscripcionItem letradoSeleccionado = new LetradoInscripcionItem(this.getPersonaPorId(idPersona.toString()), idInstitucion, idTurno, idGuardia, "S");

			if (hmPersonasConSaltos.containsKey(idPersona))
				alLetradosSaltados = hmPersonasConSaltos.get(idPersona);
			else
				alLetradosSaltados = new ArrayList<LetradoInscripcionItem>();

			alLetradosSaltados.add(letradoSeleccionado);
			hmPersonasConSaltos.put(idPersona, alLetradosSaltados);
		}
		
		return hmPersonasConSaltos;
	}
	
	private String getOrderBy(String idOrdenacionColas)  throws Exception{
		
		// obteniendo el orden
		ScsOrdenacioncolas ordenBean = scsOrdenacioncolasMapper.selectByPrimaryKey(Integer.parseInt(idOrdenacionColas));
		
		
		Integer apellidos = Integer.parseInt( ordenBean.getAlfabeticoapellidos().toString());
		Integer antiguedad = Integer.parseInt(  ordenBean.getAntiguedadcola().toString());
		Integer fechanacimiento = Integer.parseInt(ordenBean.getFechanacimiento().toString());
		Integer numerocolegiado = Integer.parseInt( ordenBean.getNumerocolegiado().toString());
		
		// calculando order by
		String orden = "";
		for (int i=4; i>0; i--) {
			if (Math.abs(apellidos) == i) {
				orden += "ALFABETICOAPELLIDOS";
				if (Math.abs(apellidos) != apellidos) orden += " desc";
				orden += "," + "NOMBRE";
				if (Math.abs(apellidos) != apellidos) orden += " desc";
				orden += ", ";
			}
			if (Math.abs(antiguedad) == i) {
				orden += "ANTIGUEDADCOLA";
				if (Math.abs(antiguedad) != antiguedad) orden += " desc";
				orden += ", ";
			}
			if (Math.abs(fechanacimiento) == i) {
				orden += "FECHANACIMIENTO";
				if (Math.abs(fechanacimiento) != fechanacimiento) orden += " desc";
				orden += ", ";
			}
			if (Math.abs(numerocolegiado) == i) {
				//orden += "to_number("+ScsOrdenacionColasBean.C_NUMEROCOLEGIADO+")"; esta linea queda comentada para que se vea que el to_number es peligroso				 
				orden += "lpad("+"NUMEROCOLEGIADO"+",20,'0')";
				if (Math.abs(numerocolegiado) != numerocolegiado) orden += " desc";
				orden += ", ";
			}
		}
		
		if (orden.equals("")) { 
			// En el caso de que no se haya establecido orden, se ordena por antiguedad en la cola
			orden = " "+"ANTIGUEDADCOLA"+" ";
			orden += ", ";
		}
		orden = orden.substring(0, orden.length()-2); // quitando ultima coma
		
		return orden;
	} 
	
	private LetradoInscripcionItem getSiguienteLetradoTurno(List<LetradoInscripcionItem> alCompensaciones,
			ArrayList<LetradoInscripcionItem> alLetradosOrdenados, Puntero punteroLetrado, ArrayList<String> diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales, String idInstitucion, String idTurno, String idGuardia,String idCalendarioGuardias , AdmUsuarios usuario) throws Exception {
		
		LetradoInscripcionItem letradoGuardia, auxLetradoSeleccionado;

		letradoGuardia = null;

		// recorriendo compensaciones
		if (alCompensaciones != null && alCompensaciones.size() > 0) {

			//Si hay compensaciones se coge el letrado con la compensación mas antigua.
			LOGGER.info("Buscando compensaciones…" );
			
			Iterator<LetradoInscripcionItem> iterador = alCompensaciones.iterator();
			while (iterador.hasNext()) {
				auxLetradoSeleccionado = (LetradoInscripcionItem) iterador.next();
				// vale
				if (comprobarRestriccionesLetradoCompensadoTurno(auxLetradoSeleccionado, diasGuardia, iterador, null, hmBajasTemporales,   idInstitucion,  idTurno,  idGuardia, idCalendarioGuardias ,  usuario)) {
					letradoGuardia = auxLetradoSeleccionado;
					LOGGER.info("Letrado encontrado. " + letradoGuardia.getPersona().getNombre() + " " +  letradoGuardia.getPersona().getApellidos1() + " " +  letradoGuardia.getPersona().getApellidos2() );
					break;
				}
			}
		}
		if (letradoGuardia != null)
			return letradoGuardia;

		// recorriendo la cola
		if (alLetradosOrdenados != null && alLetradosOrdenados.size() > 0) {

//			//Si no hay compensaciones se coge el primer letrado de la cola 
			LOGGER.info("Buscando en la cola" ); 
			
			int fin = punteroLetrado.getValor();
			do {
				auxLetradoSeleccionado = (LetradoInscripcionItem) alLetradosOrdenados.get(punteroLetrado.getValor());
				// vale
				if (comprobarRestriccionesLetradoColaTurno(auxLetradoSeleccionado, diasGuardia, hmPersonasConSaltos, hmBajasTemporales,  idInstitucion,  idTurno,  idGuardia, idCalendarioGuardias ,  usuario) )
					letradoGuardia = auxLetradoSeleccionado;
				if(letradoGuardia.getInscripcionTurno() != null) {
					LOGGER.info("Letrado encontrado. " + letradoGuardia.getInscripcionTurno().getNombre() + " " +  letradoGuardia.getInscripcionTurno().getApellidos1() + " " +  letradoGuardia.getInscripcionTurno().getApellidos2() );
				}
				    
				
				// obteniendo siguiente en la cola
				if (punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
					punteroLetrado.setValor(punteroLetrado.getValor() + 1);
				else
					punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio

			} while (letradoGuardia == null && fin != punteroLetrado.getValor());
		}

		if (letradoGuardia != null)
			return letradoGuardia;
		else
			return null;
	} 

	
	
	private boolean comprobarRestriccionesLetradoColaTurno(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String, BajasTemporalesItem>> hmBajasTemporales, String idInstitucion, String idTurno, String idGuardia,String idCalendarioGuardias , AdmUsuarios usuario) throws Exception {

		LOGGER.info("Probando letrados");
		
		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
			// ... crear un salto cumplido (como si fuera un log)
			insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Salto por BT", idInstitucion,  idTurno,  idGuardia, idCalendarioGuardias ,  usuario);

			return false; // y no seleccionar
		}

		// si tiene saltos, ...
		List<LetradoInscripcionItem> alSaltos;
		if ((alSaltos = hmPersonasConSaltos.get(letradoGuardia.getIdpersona())) != null) {
			// ... compensar uno
			cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "S", " - Salto cumplido", idInstitucion,  idTurno,  idGuardia,  idCalendarioGuardias,usuario);
			alSaltos.remove(0);
			if (alSaltos.size() == 0)
				hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());
			return false; // y no seleccionar
		}

		
		// una vez comprobado todo, se selecciona a este letrado
		return true;
	} // comprobarRestriccionesLetradoCola()
	
	
	private boolean comprobarRestriccionesLetradoCompensadoTurno(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, Iterator<LetradoInscripcionItem> iteCompensaciones,
			String idSaltoCompensacionGrupo, HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales, String idInstitucion, String idTurno, String idGuardia,String idCalendarioGuardias , AdmUsuarios usuario) throws Exception {


				LOGGER.info("Probando letrados");
				
				// si esta de vacaciones, ...
				if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
						// ... crear un salto cumplido (como si fuera un log)
					insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Salto por BT", idInstitucion,  idTurno,  idGuardia, idCalendarioGuardias ,  usuario);
					return false; // y no seleccionar
				}

				// cumpliendo compensacion
				cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "C", " - Compensacion cumplida",  idInstitucion,  idTurno,  idGuardia,  idCalendarioGuardias,usuario);
				iteCompensaciones.remove();
				

				// una vez comprobado todo, se selecciona a este letrado
				return true;
		}

		
	
	
	
	/**
	 * Si el letrado esta de baja seteamos la baja temporarl en el objeto LetradoGuardia, para luego insertar un salto
	 */
	private boolean isLetradoBajaTemporal(TreeMap<String, BajasTemporalesItem> hmBajasTemporales,
			ArrayList<String> diasGuardia, LetradoInscripcionItem letradoGuardia) {
		boolean isLetradoBaja = false;
		BajasTemporalesItem bajaTemporal;
		
		LOGGER.info("Se comprueba que no tenga baja temporal");

		if (hmBajasTemporales == null)
			return isLetradoBaja;

		for (int j = 0; j < diasGuardia.size(); j++) {
			String fechaPeriodo = (String) diasGuardia.get(j);
			if (hmBajasTemporales.containsKey(fechaPeriodo)) {
				bajaTemporal = hmBajasTemporales.get(fechaPeriodo);
				letradoGuardia.setBajaTemporal(bajaTemporal);
				isLetradoBaja = true;
				LOGGER.info("Se anota en el log Letrado"+letradoGuardia.getPersona().getNombre() + " " + letradoGuardia.getPersona().getApellidos1() + " "+ letradoGuardia.getPersona().getApellidos2() +" saltado por baja temporal");
				break;
			}
		}

		return isLetradoBaja;
	}
	
	
	private void insertarNuevoSaltoBT(LetradoInscripcionItem letradoGuardia, ArrayList<String> diasGuardia, String motivo, String idInstitucion, String idTurno, String idGuardia,String idCalendarioGuardias , AdmUsuarios usuario ) throws Exception {
		
		String saltoOCompensacion = "S";

		Integer idGuardiaInt = null;
		if(idGuardia != null) {
			idGuardiaInt = Integer.parseInt(idGuardia);
		}
		Integer idCalendarioGuardiasInt = null;
		if(idCalendarioGuardias != null) {
			idCalendarioGuardiasInt = Integer.parseInt(idCalendarioGuardias);
		}
		
		// obteniendo motivo
		StringBuffer descripcion = new StringBuffer();
		BajasTemporalesItem bajaTemporalBean = letradoGuardia.getBajaTemporal();
		
		if (bajaTemporalBean.getTipo().equals("V")){
			descripcion.append(UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "censo.bajastemporales.tipo.vacaciones" ));
		} 	else if (bajaTemporalBean.getTipo().equals("B")) {
			descripcion.append(UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "censo.bajastemporales.tipo.baja"));
		}
		descripcion.append(" ");
		descripcion.append(bajaTemporalBean.getDescripcion());

		//Creamos la entity para insertar
		ScsSaltoscompensaciones scsSaltoscompensaciones = new ScsSaltoscompensaciones();
		scsSaltoscompensaciones.setIdinstitucion(Short.parseShort( idInstitucion));
		scsSaltoscompensaciones.setIdturno(Integer.parseInt(idTurno));
		scsSaltoscompensaciones.setIdguardia(idGuardiaInt);
		scsSaltoscompensaciones.setIdcalendarioguardiascreacion(idCalendarioGuardiasInt);
		Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(diasGuardia.get(0).toString());
		scsSaltoscompensaciones.setFechacumplimiento(fechaBBDD);
		scsSaltoscompensaciones.setIdcalendarioguardias(idCalendarioGuardiasInt);
		scsSaltoscompensaciones.setMotivos(descripcion + ": " + motivo);
		scsSaltoscompensaciones.setSaltoocompensacion(saltoOCompensacion);
		
		scsSaltoscompensacionesMapper.insert(scsSaltoscompensaciones);
	}
	


	private void cumplirSaltoCompensacion(LetradoInscripcionItem letradoGuardia, ArrayList<String> diasGuardia,
			String saltoOCompensacion, String motivo,String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias, AdmUsuarios usuario) throws Exception  {
	
		Integer idGuardiaInt = null;
		if(idGuardia != null) {
			idGuardiaInt = Integer.parseInt(idGuardia);
		}
		Integer idCalendarioGuardiasInt = null;
		if(idCalendarioGuardias != null) {
			idCalendarioGuardiasInt = Integer.parseInt(idCalendarioGuardias);
		}
		
		
		ScsSaltoscompensaciones scsSaltoscompensaciones = new ScsSaltoscompensaciones();
		scsSaltoscompensaciones.setIdinstitucion(Short.parseShort( idInstitucion));
		scsSaltoscompensaciones.setIdturno(Integer.parseInt(idTurno));
		scsSaltoscompensaciones.setIdguardia(idGuardiaInt);
		scsSaltoscompensaciones.setIdpersona(letradoGuardia.getIdpersona());
		scsSaltoscompensaciones.setSaltoocompensacion(saltoOCompensacion);
		Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(diasGuardia.get(0).toString());
		scsSaltoscompensaciones.setFechacumplimiento(fechaBBDD);
		if (idGuardia != null) {
			scsSaltoscompensaciones.setIdcalendarioguardias(idCalendarioGuardiasInt);
		}
		scsSaltoscompensaciones.setMotivos( motivo);
		
		
		scsDesignacionesExtendsMapper.marcarSaltoCompensacion(scsSaltoscompensaciones, usuario);
	}

	
	
	
	
	@Override
	public ProcuradorDTO busquedaProcurador(List<String> procurador, HttpServletRequest request) {
		LOGGER.info("busquedaProcurador() -> Entrada al servicio para obtener prisiones");
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaProcurador() / scsProcuradorExtendsMapper.busquedaProcurador() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				String num = procurador.get(0);
				String idinstitucion = procurador.get(1);
				String idturno = procurador.get(2);
				procuradorItemList = scsDesignacionesExtendsMapper.busquedaProcurador(num, idinstitucion,idturno);

				LOGGER.info(
						"busquedaProcurador() / scsProcuradorExtendsMapper.busquedaProcurador() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("busquedaProcurador() -> Salida del servicio para obtener procuradores");
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
	
	@Override
	public ProcuradorDTO compruebaProcurador(String procurador, HttpServletRequest request) {
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

				String[] parts = procurador.split("/");
				String anio = parts[0].substring(1);
				String num = parts[1];
				
				procuradorItemList = scsDesignacionesExtendsMapper.compruebaProcurador(num, anio);

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
	public ProcuradorDTO compruebaFechaProcurador(String procurador, HttpServletRequest request) {
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

				String[] parts = procurador.split("/");
				String anio = parts[0];
				String num = parts[1];
				
				procuradorItemList = scsDesignacionesExtendsMapper.compruebaProcurador(num, anio);

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
	public UpdateResponseDTO guardarProcurador( List<String> procurador, HttpServletRequest request) {
		LOGGER.info("deleteBaja() ->  Entrada al servicio para eliminar bajas temporales");

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
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					

					ProcuradorItem procuradorItem = new ProcuradorItem();
					
					procuradorItem.setFechaDesigna(procurador.get(0));
					procuradorItem.setNumerodesignacion(procurador.get(1));
					procuradorItem.setnColegiado(procurador.get(2));
					procuradorItem.setNombre(procurador.get(3));
					procuradorItem.setMotivosRenuncia(procurador.get(4));
					procuradorItem.setObservaciones(procurador.get(5));
					procuradorItem.setFecharenunciasolicita(procurador.get(6));
					procuradorItem.setIdInstitucion(procurador.get(7));
					procuradorItem.setNumero(procurador.get(8));
					procuradorItem.setIdTurno(procurador.get(9));

					response = scsDesignacionesExtendsMapper.guardarProcurador(procuradorItem);

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha eliminado la baja temporal");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han elimiando las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha eliminado la baja temporal correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("deleteBaja() -> Salida del servicio para eliminar bajas temporales");

			}

		}

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO nuevoProcurador(ProcuradorItem procuradorItem, HttpServletRequest request) {
		LOGGER.info("deleteBaja() ->  Entrada al servicio para eliminar bajas temporales");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
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
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				response = scsDesignacionesExtendsMapper.nuevoProcurador(procuradorItem,
						usuarios.get(0).getIdusuario());

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha eliminado la baja temporal");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han elimiando las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha eliminado la baja temporal correctamente");
				}

				insertResponseDTO.setError(error);

				LOGGER.info("deleteBaja() -> Salida del servicio para eliminar bajas temporales");

			}

		}

		return insertResponseDTO;
	}
	
	@Override
	public DesignaItem existeDesginaJuzgadoProcedimiento(DesignaItem designa, HttpServletRequest request) {
		DesignaItem result = new DesignaItem();
		DesignaItem designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
						"DesignacionesServiceImpl.getDatosAdicionales -> Entrada a servicio para la busqueda de datos adicionales de una designa");

				try {
					designas = scsDesignacionesExtendsMapper.existeDesginaJuzgadoProcedimiento(idInstitucion, designa);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<DesignaItem> getDatosAdicionales(DesignaItem designa, HttpServletRequest request) {
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
					"DesignacionesServiceImpl.getDatosAdicionales() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
						"DesignacionesServiceImpl.getDatosAdicionales -> Entrada a servicio para la busqueda de datos adicionales de una designa");

				try {
					designas = scsDesignacionesExtendsMapper.getDatosAdicionales(idInstitucion, tamMaximo, designa);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public ComboDTO comboPrisiones(HttpServletRequest request) {

		LOGGER.info("comboPrisiones() -> Entrada al servicio para combo comboPrisiones");
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
						"comboPrisiones() / scsPrisionExtendsMapper.getComboPrisiones() -> Entrada a scsPrisionExtendsMapper para obtener combo de prisiones");

				List<ComboItem> comboItems = scsPrisionExtendsMapper.getComboPrisiones(idInstitucion);

				LOGGER.info(
						"comboPrisiones() / scsPrisionExtendsMapper.getComboPrisiones() -> Salida e scsPrisionExtendsMapper para obtener combo de prisiones");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboPrisiones() -> Salida del servicio para obtener combo de prisiones");
		}
		return comboDTO;
	}
	
	
	@Override
	public ComboDTO getPartidaPresupuestariaDesigna(HttpServletRequest request, @RequestBody DesignaItem designaItem) {
		DesignaItem result = new DesignaItem();
		ComboDTO comboDTO = new ComboDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getPartidaPresupuestariaDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Entrada a servicio ");

				try {
					List<ComboItem> comboItems  = scsDesignacionesExtendsMapper.getPartidaPresupuestariaDesigna(idInstitucion, designaItem);

					comboDTO.setCombooItems(comboItems);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Salida del servicio");
			}
		}

		return comboDTO;
	}
	
	@Override
	public ScsDesigna busquedaDesigna(ScsDesigna item, HttpServletRequest request) {
		LOGGER.info("DesignacionesServiceImpl.busquedaDesigna() -> Entrada al servicio servicio");
		ScsDesigna designa = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesigna() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesigna() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesigna() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaDesigna -> Entrada a servicio para la busqueda de contrarios");

				try {

					ScsDesignaKey key = new ScsDesignaKey();
					key.setIdinstitucion(idInstitucion);
					key.setAnio(item.getAnio());
					key.setIdturno(item.getIdturno());
					key.setNumero(item.getNumero());

					designa = scsDesignaMapper.selectByPrimaryKey(key);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesigna -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesigna -> Salida del servicio");
			}
		}

		return designa;
	}

	@Override
	public List<ListaLetradosDesignaItem> busquedaLetradosDesigna(ScsDesigna item, HttpServletRequest request) {
		LOGGER.info(
				"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada al servicio para buscar los letrados asociados a una designacion");
		List<ListaLetradosDesignaItem> listaLetrados = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada a servicio para la busqueda de letrados de la designacion");

				try {

//						ScsDesignasletradoExample example = new ScsDesignasletradoExample();
//						example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(item.getAnio())
//						.andIdturnoEqualTo(item.getIdturno()).andNumeroEqualTo(item.getNumero());
//						
//						List<ScsDesignasletrado> letrados = scsDesignasletradoMapper.selectByExample(example);

//						int i = 0;
					// Buscamos las columnas de la tabla que no podemos extraer de latabla
					// designasletado
					// Nº COlegiado y nombre y apellido
//						for (ScsDesignasletrado letrado: letrados) {

//							listaLetrados.add(new ListaLetradosDesignaItem());

					listaLetrados = scsDesignacionesExtendsMapper.getListaLetradosDesigna(item, idInstitucion);
//							listaLetrados.get(i).setFechaDesignacion(letrado.getFechadesigna());
//							listaLetrados.get(i).setFechaSolRenuncia(letrado.getFecharenunciasolicita());
//							listaLetrados.get(i).setFechaEfecRenuncia(letrado.getFecharenuncia());
//							listaLetrados.get(i).setMotivoRenuncia(letrado.getMotivosrenuncia());
//							
//							CenColegiadoKey key = new CenColegiadoKey();
//							key.setIdinstitucion(idInstitucion);
//							key.setIdpersona(letrado.getIdpersona());
//							
//							CenColegiado colegiado = cenColegiadoMapper.selectByPrimaryKey(key);
//							colegiado.getNcolegiado();
//							colegiado.getn
//							
//							
//							i++;
//						}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida del servicio");
			}
		}

		return listaLetrados;
	}

	public ComboDTO comboMotivosCambioActDesigna(HttpServletRequest request) {

		LOGGER.info(
				"comboMotivosCambioActDesigna() -> Entrada al servicio para obtener combo de movitos de cambio de la actuación");
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
						"comboMotivosCambioActDesigna() / scsDesignacionesExtendsMapper.comboMotivosCambioActDesigna() -> Entrada a scsDesignacionesExtendsMapper para obtener combo de movitos de cambio de la actuación");

				List<ComboItem> comboItems = scsDesignacionesExtendsMapper.comboMotivosCambioActDesigna(idInstitucion);

				LOGGER.info(
						"comboMotivosCambioActDesigna() / scsDesignacionesExtendsMapper.comboMotivosCambioActDesigna() -> Salida e scsDesignacionesExtendsMapper para obtener combo de movitos de cambio de la actuación");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info(
					"comboMotivosCambioActDesigna() -> Salida del servicio para obtener combo de movitos de cambio de la actuación");
		}
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO updateJustiActDesigna(ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.updateJustiActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.updateJustiActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				int response = scsDesignacionesExtendsMapper.updateJustiActDesigna(actuacionDesignaItem,
						Short.toString(idInstitucion), usuarios.get(0));
				
				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.updateJustiActDesigna() -> Se ha producido un error al actualizar los datos de justificación de la actuación");
					error.setCode(500);
					error.setDescription(
							"Se ha producido un error al actualizar los datos de justificación de la actuación");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.updateJustiActDesigna() -> Se ha producido un error al actualizar los datos de justificación de la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	public RelacionesDTO busquedaRelaciones(List<String> relaciones, HttpServletRequest request) {
		LOGGER.info("busquedaProcurador() -> Entrada al servicio para obtener prisiones");
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RelacionesDTO relacionesDTO = new RelacionesDTO();
		List<RelacionesItem> relacionesItem = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaProcurador() / scsProcuradorExtendsMapper.busquedaProcurador() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");
				
				String[] parts = relaciones.get(0).split("/");
				String anio = parts[0].substring(1);
				String num = parts[1];
				String idTurno = relaciones.get(1);
				String idinstitucion = relaciones.get(2);
				relacionesItem = scsDesignacionesExtendsMapper.busquedaRelaciones(anio, num, idTurno, idinstitucion);

				LOGGER.info(
						"busquedaProcurador() / scsProcuradorExtendsMapper.busquedaProcurador() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (relacionesItem != null) {
					relacionesDTO.setRelacionesItem(relacionesItem);
				}
			}

		}
		LOGGER.info("busquedaProcurador() -> Salida del servicio para obtener procuradores");
		return relacionesDTO;
	}
	

	@Override
	public DeleteResponseDTO eliminarRelacion(RelacionesItem listaRelaciones, HttpServletRequest request) {
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

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");
					
					String anio = listaRelaciones.getAnio();
					String num = listaRelaciones.getNumero();
					String idTurno = listaRelaciones.getIdturno();
					String institucion = listaRelaciones.getIdinstitucion();
					
					response = scsDesignacionesExtendsMapper.eliminarRelacion(anio, num, idTurno, institucion);

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

	/**
	 * actualizaJustificacionExpres
	 */
	@Override
	public UpdateResponseDTO actualizaJustificacionExpres(List<ActuacionesJustificacionExpressItem> listaItem,
			HttpServletRequest request) {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = null;
				 
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Entrada a servicio para actualizar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Realizando update...");

					ScsActuaciondesigna record = new ScsActuaciondesigna();
					
					for(ActuacionesJustificacionExpressItem item : listaItem) {
						if(item.getAnio()!=null && !item.getAnio().trim().isEmpty()){
							record.setAnio(Short.parseShort(item.getAnio()));
						}
						
						if(item.getAnioProcedimiento() !=null && !item.getAnioProcedimiento().trim().isEmpty()){
							record.setAnioprocedimiento(Short.parseShort(item.getAnioProcedimiento()));
						}
						
						if(item.getFecha()!=null && !item.getFecha().trim().isEmpty()){
							fecha = formatter.parse(item.getFecha());  
							record.setFecha(fecha);
						}
						
						if(item.getFechaJustificacion()!=null && !item.getFechaJustificacion().trim().isEmpty()){
							fecha = formatter.parse(item.getFechaJustificacion());  
							record.setFechajustificacion(fecha);
						}
						
						record.setFechamodificacion(new Date());
						
						if(item.getIdAcreditacion()!=null && !item.getIdAcreditacion().trim().isEmpty()){
							record.setIdacreditacion(Short.parseShort(item.getIdAcreditacion()));
						}
						
						if(item.getIdInstitucion()!=null && !item.getIdInstitucion().trim().isEmpty()){
							record.setIdinstitucion(Short.parseShort(item.getIdInstitucion()));
						}
					
						if(item.getIdJuzgado()!=null && !item.getIdJuzgado().trim().isEmpty()){
							record.setIdjuzgado(Long.parseLong(item.getIdJuzgado()));
						}
						
						if(item.getIdProcedimiento()!=null && !item.getIdProcedimiento().trim().isEmpty()){
							record.setIdprocedimiento(item.getIdProcedimiento());
						}
						
						if(item.getIdTurno()!=null && !item.getIdTurno().trim().isEmpty()){
							record.setIdturno(Integer.parseInt(item.getIdTurno()));
						}
						
						response += scsActuaciondesignaMapper.updateByPrimaryKeySelective(record);
					}

					LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Actualizacion realizada");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Se ha producido un error ",
							e);
					response = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Saliendo del servicio. ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		responseDTO.setError(error);

		return responseDTO;
	}
}