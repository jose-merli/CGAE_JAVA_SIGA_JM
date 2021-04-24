package org.itcgae.siga.scs.services.impl.oficio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaLetradosDesignaItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
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
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsTipodictamenejg;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.ScsActuaciondesignaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignasletradoMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
	private ScsDesignasletradoMapper scsDesignasletradoMapper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
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
	public InsertResponseDTO insertaJustificacionExpres(List<ActuacionesJustificacionExpressItem> item,
			HttpServletRequest request) {
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
					"DesignacionesServiceImpl.insertaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.insertaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.insertaJustificacionExpres() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Haciendo el insert...");

//					for(ActuacionesJustificacionExpressItem record : item) {
					// rellenar la key
//						key.setAnio(item.get);

//						response = scsActuaciondesignaMapper.insert();
//					}

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
	 * actualizaJustificacionExpres
	 */
	@Override
	public UpdateResponseDTO actualizaJustificacionExpres(List<ActuacionesJustificacionExpressItem> item,
			HttpServletRequest request) {
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
					"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Entrada a servicio para actualizar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Realizando update...");

//					for(ActuacionesJustificacionExpressItem record : item) {

					// rellenar la key
//						key.setAnio(item.get);

//						response = scsActuaciondesignaMapper.insert();
//					}

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

	/**
	 * eliminaJustificacionExpres
	 */
	@Override
	public DeleteResponseDTO eliminaJustificacionExpres(List<ActuacionesJustificacionExpressItem> item,
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

//					for(ActuacionesJustificacionExpressItem record : item) {

					// rellenar la key
//						key.setAnio(item.get);

//						response = scsActuaciondesignaMapper.insert();
//					}

					LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Borrado finalizado");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Se ha producido un error ",
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
	public MaxIdDto getNewIdActuDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		MaxIdDto maxIdDto = new MaxIdDto();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getNewIdActuDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getNewIdActuDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				LOGGER.info(
						"DesignacionesServiceImpl.getNewIdActuDesigna() / scsDesignacionesExtendsMapper.getNewIdActuDesigna() -> Se inicia la búsqueda del nuevo ID de actuación");

				maxIdDto = scsDesignacionesExtendsMapper.getNewIdActuDesigna(actuacionDesignaRequestDTO, idInstitucion);

				LOGGER.info(
						"DesignacionesServiceImpl.getNewIdActuDesigna() / scsDesignacionesExtendsMapper.getNewIdActuDesigna() -> Se finaliza la búsqueda del nuevo ID de actuación");
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getNewIdActuDesigna() -> Se ha producido un error al intentar recuperar el nuevo ID de actuación.");
		}

		return maxIdDto;
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
						int response = scsDesignacionesExtendsMapper.anularReactivarActDesigna(actuacionDesignaItem,
								Short.toString(idInstitucion), usuarios.get(0), anular);
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

					int response = scsDesignacionesExtendsMapper.eliminarActDesigna(actuacionDesignaItem,
							Short.toString(idInstitucion), usuarios.get(0));
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
					designa.setAnio((short) designaItem.getAno());
					designa.setNumero(new Long(designaItem.getNumero()));
					designa.setIdtipodesignacolegio((short) designaItem.getIdTipoDesignaColegio());
					designa.setArt27(designaItem.getArt27());

					ScsDesignasletrado designaLetrado = new ScsDesignasletrado();
					designaLetrado.setIdinstitucion(idInstitucion);
					designaLetrado.setIdturno(designaItem.getIdTurno());
					designaLetrado.setAnio((short) designaItem.getAno());
					designaLetrado.setNumero(new Long(designaItem.getNumero()));
					// designaLetrado.set

					LOGGER.info(
							"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

					// response = scsTurnosExtendsMapper.insert(turno);

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
	
	@Override
	public ProcuradorDTO busquedaProcurador(List<String> procurador, HttpServletRequest request) {
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
				procuradorItemList = scsDesignacionesExtendsMapper.busquedaProcurador(num, idinstitucion);

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
}