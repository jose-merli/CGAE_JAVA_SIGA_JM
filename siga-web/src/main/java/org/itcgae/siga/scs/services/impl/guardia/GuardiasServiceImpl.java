package org.itcgae.siga.scs.services.impl.guardia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DeleteIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDTO;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.LetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaveIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsGrupoguardia;
import org.itcgae.siga.db.entities.ScsGrupoguardiaExample;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiado;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiadoExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupoguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupoguardiacolegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsHitofacturableguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsIncompatibilidadguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonapartidoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuardiasServiceImpl implements GuardiasService {

	private Logger LOGGER = Logger.getLogger(GuardiasServiceImpl.class);
	private boolean resetGrupos = false;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private ScsIncompatibilidadguardiasExtendsMapper scsIncompatibilidadguardiasExtendsMapper;

	@Autowired
	private ScsHitofacturableguardiaExtendsMapper scsHitofacturableguardiaExtendsMapper;

	@Autowired
	private ScsOrdenacionColasExtendsMapper scsOrdenacionColasExtendsMapper;

	@Autowired
	private ScsInscripcionguardiaExtendsMapper scsInscripcionguardiaExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsSubzonapartidoExtendsMapper scsSubzonapartidoExtendsMapper;

	@Autowired
	private ScsGrupoguardiaExtendsMapper scsGrupoguardiaExtendsMapper;

	@Autowired
	private ScsGrupoguardiacolegiadoExtendsMapper scsGrupoguardiacolegiadoExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Override
	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GuardiasDTO guardiaDTO = new GuardiasDTO();
		Error error = new Error();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchGuardias() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS)
						.andParametroEqualTo(SigaConstants.TAM_MAX_CONSULTA_JG)
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause(SigaConstants.C_IDINSTITUCION + " DESC");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchGuardias() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info("searchGuardias() -> Entrada para obtener las guardias");

				List<GuardiasItem> guardias = scsGuardiasturnoExtendsMapper.searchGuardias(guardiasItem,
						idInstitucion.toString(), usuarios.get(0).getIdlenguaje(), tamMaximo);

				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
							+ it.getSeleccionFestivos()).replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				if ((guardias != null) && tamMaximo != null && (guardias.size()) > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					guardiaDTO.setError(error);
					guardias.remove(guardias.size() - 1);
				}

				guardiaDTO.setGuardiaItems(guardias);

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			}
		}
		return guardiaDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request) {

		LOGGER.info("deleteGuardias() ->  Entrada al servicio para eliminar prisiones");

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
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample scsGuardiasExample = new ScsGuardiasturnoExample();
						scsGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdguardiaEqualTo(Integer.valueOf(guardiaItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiaItem.getIdTurno()));

						LOGGER.info(
								"deleteGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a scsGuardiasturnoExtendsMapper para buscar la guardia");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"deleteGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de scsGuardiasturnoExtendsMapper para buscar la guardia");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(new Date());
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Entrada a scsGuardiasturnoExtendsMapper para dar de baja a una guardia");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"deleteGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Salida de scsGuardiasturnoExtendsMapper para dar de baja a una guardia");
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("deleteGuardias() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;

	}

	@Override
	@Transactional
	public UpdateResponseDTO activateGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request) {

		LOGGER.info("activateGuardias() ->  Entrada al servicio para dar de alta a prisiones");

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
					"activateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample scsGuardiasExample = new ScsGuardiasturnoExample();
						scsGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdguardiaEqualTo(Integer.valueOf(guardiaItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiaItem.getIdTurno()));

						LOGGER.info(
								"activateGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a scsGuardiasturnoExtendsMapper para buscar la guardia");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"activateGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de scsGuardiasturnoExtendsMapper para buscar la guardia");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(null);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Entrada a scsGuardiasturnoExtendsMapper para dar de baja a una prision");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"activateGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Salida de scsGuardiasturnoExtendsMapper para dar de baja a una prision");
						}

					}

				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("activateGuardias() -> Salida del servicio para dar de alta las guardias");

		return updateResponseDTO;

	}

	@Transactional
	@Override
	public GuardiasItem detalleGuardia(GuardiasItem guardiaTurno, HttpServletRequest request) {

		LOGGER.info("detalleGuardia() ->  Entrada al servicio para obtener una guardia");

		GuardiasItem guardiaItem = new GuardiasItem();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"detalleGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"detalleGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a la busqueda de la guardia");

					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(guardiaTurno.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiaTurno.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper.selectByExample(example);
					if (guardiasList != null && guardiasList.size() > 0) {

						LOGGER.info(
								"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Mapeamos lo recogido");
						// Mapeo para Datos Generales
						ScsGuardiasturno guardia = guardiasList.get(0);
						guardiaItem.setNombre(guardia.getNombre());
						guardiaItem.setDescripcion(guardia.getDescripcion());
						if (guardia.getIdtipoguardia() != null)
							guardiaItem.setTipoGuardia(guardia.getIdtipoguardia() + "");
						if (guardia.getEnviocentralita() != null)
							guardiaItem.setEnvioCentralita(guardia.getEnviocentralita().equals(Short.valueOf("1")));
						guardiaItem.setDescripcionFacturacion(guardia.getDescripcionfacturacion());
						guardiaItem.setDescripcionPago(guardia.getDescripcionpago());
						guardiaItem.setIdTurno(guardia.getIdturno() + "");
						guardiaItem.setIdTipoGuardia(guardia.getIdtipoguardia().toString());
						guardiaItem.setIdGuardia(guardia.getIdguardia() + "");

						// AQUI VEMOS SI ALGUNA GUARDIA ESTA VINCULADA Y SI ESTÁ SE LA AÑADIMOS AL
						// OBJETO

						List<GuardiasItem> hilfe = scsGuardiasturnoExtendsMapper.getGuardiasVinculadas(
								guardia.getIdguardia().toString(), guardia.getIdturno().toString(),
								idInstitucion.toString());

						if (hilfe != null && hilfe.size() > 0) {
							guardiaItem.setIdGuardiaVinculada("");
							guardiaItem.setIdTurnoVinculada("");
							hilfe.forEach(it -> {
								guardiaItem.setIdGuardiaVinculada(
										guardiaItem.getIdGuardiaVinculada() + it.getNombre() + ",");
								guardiaItem
										.setIdTurnoVinculada(guardiaItem.getIdTurnoVinculada() + it.getTurno() + ",");
							});

						}

						if (guardia.getIdguardiaprincipal() != null && guardia.getIdturnoprincipal() != null) {
							hilfe = scsGuardiasturnoExtendsMapper.getGuardiaPrincipal(
									guardia.getIdguardiaprincipal().toString(),
									guardia.getIdturnoprincipal().toString(), idInstitucion.toString());

							guardiaItem.setIdGuardiaPrincipal(hilfe.get(0).getNombre());
							guardiaItem.setIdTurnoPrincipal(hilfe.get(0).getTurno());
						}

						// Configuracion de cola
						guardiaItem.setPorGrupos(guardia.getPorgrupos());
						if (guardia.getIdordenacioncolas() != null)
							guardiaItem.setIdOrdenacionColas(guardia.getIdordenacioncolas() + "");
						if (guardia.getDiasperiodo() != null)
							guardiaItem.setDiasPeriodo(guardia.getDiasperiodo() + "");
						guardiaItem.setRotarComponentes(guardia.getRotarcomponentes());
						guardiaItem.setLetradosGuardia(guardia.getNumeroletradosguardia().toString());
						// Configuracion calendarios
						if (guardia.getDiasguardia() != null)
							guardiaItem.setDiasGuardia(guardia.getDiasguardia() + "");
						if (guardia.getDiasperiodo() != null)
							guardiaItem.setDiasPeriodo(guardia.getDiasperiodo() + "");
						guardiaItem.setTipoDiasGuardia(guardia.getTipodiasguardia());
						guardiaItem.setTipoDiasPeriodo(guardia.getTipodiasperiodo());
						guardiaItem.setDiasSeparacionGuardias(guardia.getDiasseparacionguardias() != null
								? guardia.getDiasseparacionguardias().toString()
								: "0");
						guardiaItem.setRequeridaValidacion("S".equals(guardia.getRequeridavalidacion()) ? true : false);

						List<GuardiasItem> checkeado = scsHitofacturableguardiaExtendsMapper.getCheckSeparacionGuardias(
								guardia.getIdturno().toString(), guardia.getIdguardia().toString(),
								idInstitucion.toString());
						if (checkeado.size() > 0)
							guardiaItem.setSepararGuardia(checkeado.get(0).getSepararGuardia());

						guardiaItem.setSeleccionLaborables(guardia.getSeleccionlaborables());
						guardiaItem.setSeleccionFestivos(guardia.getSeleccionfestivos());

						if (guardia.getIdgrupoguardiaUltimo() != null)
							guardiaItem.setIdGrupoUltimo(guardia.getIdgrupoguardiaUltimo().toString());
						// Cola de guardia
						if (guardia.getIdpersonaUltimo() != null)
							guardiaItem.setIdPersonaUltimo(guardia.getIdpersonaUltimo() + "");
						LOGGER.info(
								"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Acabamos de mapear");

					}
					LOGGER.info(
							"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de la busqueda de guardia");

				} catch (Exception e) {
					LOGGER.error(e);
				}
			}

		}

		LOGGER.info("detalleGuardia() -> Salida del servicio para recoger una guardia");

		return guardiaItem;

	}
			
	@Override
	public UpdateResponseDTO updateColaGuardia(InscripcionGuardiaDTO guardiaBody, HttpServletRequest request) {
		UpdateResponseDTO res = guardarColaGuardias(guardiaBody.getInscripcionesItem(), request);
		return res;

	
	}
	@Override
	public UpdateResponseDTO updateGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("updateGuardia() ->  Entrada al servicio para editar guardia");

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
					"updateGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsGuardiasturno guardia = new ScsGuardiasturno();
					guardia.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
					guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
					guardia.setIdinstitucion(idInstitucion);

					// Se entrara en cada if dependiendo de que tarjeta se haya editado.
					// Se define donde entrara segun los datos que lleguen.
					if (guardiasItem.getDescripcion() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Datos Generales de guardias");

						guardia.setDescripcion(guardiasItem.getDescripcion());
						guardia.setNombre(guardiasItem.getNombre());
						guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
						if (guardiasItem.getEnvioCentralita() != null)
							guardia.setEnviocentralita(
									Short.valueOf((short) (guardiasItem.getEnvioCentralita() ? 1 : 0)));
						guardia.setDescripcionfacturacion(guardiasItem.getDescripcionFacturacion());
						guardia.setDescripcionpago(guardiasItem.getDescripcionPago());
						// guardia.setIdturnoprincipal(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()));
						// guardia.setIdguardiaprincipal(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()));

					} else if (guardiasItem.getLetradosGuardia() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Configuracion de Cola de guardias");
						ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
						example.createCriteria().andIdturnoEqualTo(guardia.getIdturno())
								.andIdguardiaEqualTo(guardia.getIdguardia()).andIdinstitucionEqualTo(idInstitucion);
						List<ScsGuardiasturno> item = scsGuardiasturnoExtendsMapper.selectByExample(example);

						ScsOrdenacioncolasExample ordExample = new ScsOrdenacioncolasExample();
						ordExample.createCriteria().andIdordenacioncolasEqualTo(item.get(0).getIdordenacioncolas());

						List<ScsOrdenacioncolas> colas = scsOrdenacionColasExtendsMapper.selectByExample(ordExample);

						// Esto sirve para ver si habrá que generar grupos automaticamente despues de
						// updatear
						resetGrupos = false;
						if ((item.get(0).getPorgrupos().equals("1") || colas.get(0).getOrdenacionmanual() == 0)
								&& !Boolean.valueOf(guardiasItem.getPorGrupos())
								&& Short.valueOf(guardiasItem.getFiltros().split(",")[4]) != 0) {
							resetGrupos = true;
						}
						guardia.setPorgrupos((Boolean.valueOf(guardiasItem.getPorGrupos()) ? "1" : "0"));
						guardia.setIdordenacioncolas(Integer.valueOf(guardiasItem.getIdOrdenacionColas()));
						// ROTAR COMPONENTES?
						guardia.setNumeroletradosguardia(Integer.valueOf(guardiasItem.getLetradosGuardia()));

						ordExample = new ScsOrdenacioncolasExample();
						ordExample.createCriteria()
								.andAlfabeticoapellidosEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[0]))
								.andFechanacimientoEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[1]))
								.andNumerocolegiadoEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[2]))
								.andAntiguedadcolaEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[3]))
								.andOrdenacionmanualEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[4]));

						colas = scsOrdenacionColasExtendsMapper.selectByExample(ordExample);

						if (colas.isEmpty()) { // Setteo de cola
							ScsOrdenacioncolas ordenacion = new ScsOrdenacioncolas();
							ordenacion.setAlfabeticoapellidos(Short.valueOf(guardiasItem.getFiltros().split(",")[0]));
							ordenacion.setFechanacimiento(Short.valueOf(guardiasItem.getFiltros().split(",")[1]));
							ordenacion.setNumerocolegiado(Short.valueOf(guardiasItem.getFiltros().split(",")[2]));
							ordenacion.setAntiguedadcola(Short.valueOf(guardiasItem.getFiltros().split(",")[3]));
							ordenacion.setOrdenacionmanual(Short.valueOf(guardiasItem.getFiltros().split(",")[4]));
							ordenacion.setFechamodificacion(new Date());
							ordenacion.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());

							NewIdDTO idP = scsOrdenacionColasExtendsMapper.getIdOrdenacion();

							if (idP == null) {
								ordenacion.setIdordenacioncolas((int) 1);
							} else {
								ordenacion.setIdordenacioncolas(Integer.parseInt(idP.getNewId()) + 1);
							}
							scsOrdenacionColasExtendsMapper.insert(ordenacion);
							guardia.setIdordenacioncolas(ordenacion.getIdordenacioncolas());

						} else {
							guardia.setIdordenacioncolas(colas.get(0).getIdordenacioncolas());
							scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);

						}

					} else if (guardiasItem.getDiasGuardia() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Configuracion de calendario de guardias");
						guardia.setDiasguardia(Short.valueOf(guardiasItem.getDiasGuardia()));
						guardia.setTipodiasguardia(guardiasItem.getTipoDiasGuardia());
						if (guardiasItem.getDiasPeriodo() != null)
							guardia.setDiasperiodo(Short.valueOf(guardiasItem.getDiasPeriodo()));
						guardia.setTipodiasperiodo(guardiasItem.getTipoDiasPeriodo());
						guardia.setSeleccionfestivos(guardiasItem.getSeleccionFestivos());
						guardia.setSeleccionlaborables(guardiasItem.getSeleccionLaborables());
						guardia.setRequeridavalidacion(guardiasItem.isRequeridaValidacion() ? "S" : "N");

					}

					guardia.setFechamodificacion(new Date());
					guardia.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
					response = scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);

				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("updateGuardia() -> Salida del servicio para editar guardia");

		return updateResponseDTO;

	}

	@Override
	public InsertResponseDTO createGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("createPrision() ->  Entrada al servicio para crear una nueva prisión");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		long idGuardia = 0;
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsGuardiasturnoExample scsGuardiasturnoExample = null;
					LOGGER.info(
							"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Setteo de los campos que se han introducido y el resto de datos heredados de la guardia vinculada");

					ScsGuardiasturno guardia = new ScsGuardiasturno();
					if (!UtilidadesString.esCadenaVacia(guardiasItem.getIdGuardiaPrincipal())
							&& !UtilidadesString.esCadenaVacia(guardiasItem.getIdTurnoPrincipal())) {
						scsGuardiasturnoExample = new ScsGuardiasturnoExample();
						scsGuardiasturnoExample.createCriteria()
								.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()))
								.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()))
								.andIdinstitucionEqualTo(idInstitucion);
						guardia = scsGuardiasturnoExtendsMapper.selectByExample(scsGuardiasturnoExample).get(0);

						guardia.setFechabaja(null);
						guardia.setFechamodificacion(new Date());
						guardia.setUsumodificacion(usuario.getIdusuario().intValue());
						guardia.setIdinstitucion(idInstitucion);
						guardia.setNombre(guardiasItem.getNombre());
						guardia.setDescripcion(guardiasItem.getDescripcion());
						guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
						guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
						guardia.setEnviocentralita((short) (guardiasItem.getEnvioCentralita() ? 1 : 0));
						guardia.setDescripcionfacturacion(guardiasItem.getDescripcionFacturacion());
						guardia.setDescripcionpago(guardiasItem.getDescripcionPago());
						guardia.setIdguardiaprincipal(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()));
						guardia.setIdturnoprincipal(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()));
						guardia.setFechasuscripcionUltimo(null);
						guardia.setIdpersonaUltimo(null);

					} else {
						LOGGER.info(
								"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Setteo de los campos que se han introducido y el resto de datos por defecto");

						guardia.setFechabaja(null);
						guardia.setEnviocentralita((short) (guardiasItem.getEnvioCentralita() ? 1 : 0));
						guardia.setFechamodificacion(new Date());
						guardia.setUsumodificacion(usuario.getIdusuario().intValue());
						guardia.setIdinstitucion(idInstitucion);
						guardia.setNombre(guardiasItem.getNombre());
						guardia.setDescripcion(guardiasItem.getDescripcion());
						guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
						guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
						guardia.setDescripcionfacturacion(guardiasItem.getDescripcionFacturacion());
						guardia.setDescripcionpago(guardiasItem.getDescripcionPago());
						// VALORES POR DEFECTO ESTABLECIDOS
						guardia.setTipodiasguardia("D");
						guardia.setNumeroletradosguardia(1);
						guardia.setNumerosustitutosguardia(1);
						guardia.setDiasguardia((short) 1);
						guardia.setDiaspagados((short) 1);
						guardia.setDiasseparacionguardias((short) 0);
						guardia.setSeleccionfestivos("LMXJVSD");
						guardia.setSeleccionlaborables("LMXJVSD");
						guardia.setValidarjustificaciones("N");
						guardia.setIdordenacioncolas(18033); // Esta puesto este id porque es el que tiene la conf por
																// defecto.
					}
					NewIdDTO idP = scsGuardiasturnoExtendsMapper.getIdGuardia();

					if (idP == null) {
						guardia.setIdguardia((int) 1);
						idGuardia = 1;
					} else {
						idGuardia = (Integer.parseInt(idP.getNewId()) + 1);
						guardia.setIdguardia((int) idGuardia);
					}

					LOGGER.info(
							"createGuardia() / scsGuardiasturnoExtendsMapper.insert() -> Entrada a scsGuardiasturnoExtendsMapper para insertar la nueva guardia");

					response = scsGuardiasturnoExtendsMapper.insertSelective(guardia);

					LOGGER.info(
							"createGuardia() / scsGuardiasturnoExtendsMapper.insert() -> Salida de scsGuardiasturnoExtendsMapper para insertar la nueva guardia");

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null)

		{
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idGuardia));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createGuardia() -> Salida del servicio para crear una nueva guardia");

		return insertResponseDTO;

	}

	@Override
	public GuardiasItem resumenGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"resumenGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"resumenGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenGuardia() -> Entrada para obtener datos resumen");

				guardias = scsGuardiasturnoExtendsMapper.getResumen(guardiasItem.getIdGuardia(),
						guardiasItem.getIdTurno(), idInstitucion.toString(), usuarios.get(0).getIdlenguaje());

				LOGGER.info("resumenGuardia() -> Salida ya con los datos recogidos");
			}
		}
		return guardias.get(0);
	}

	@Override
	public GuardiasItem resumenConfiguracionCola(GuardiasItem guardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"resumenConfiguracionCola() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"resumenConfiguracionCola() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenConfiguracionCola() -> Entrada para obtener los resumen Conf. Cola");
				guardias = scsGuardiasturnoExtendsMapper.resumenConfCola(guardia.getIdGuardia(), guardia.getIdTurno(),
						idInstitucion.toString());
				LOGGER.info("resumenConfiguracionCola() -> Salida ya con los datos recogidos");
			}
		}
		return guardias.get(0);
	}

	@Override
	public GuardiasDTO tarjetaIncompatibilidades(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		GuardiasDTO guardia = new GuardiasDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				//guardias = scsIncompatibilidadguardiasExtendsMapper.tarjetaIncompatibilidades(idGuardia,
						//idInstitucion.toString());
				guardias = scsIncompatibilidadguardiasExtendsMapper.tarjetaIncompatibilidades(idGuardia,
						"2003");
				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
							+ it.getSeleccionFestivos()).replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				LOGGER.info("tarjetaIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}
		guardia.setGuardiaItems(guardias);
		return guardia;
	}

	@Override
	public ComboDTO getBaremos(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ComboItem> datos = new ArrayList<ComboItem>();
		ComboDTO baremos = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getBaremos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getBaremos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getBaremos() -> Entrada para obtener los datos de baremos");

				datos = scsHitofacturableguardiaExtendsMapper.getBaremos(idGuardia, usuarios.get(0).getIdlenguaje());

				LOGGER.info("getBaremos() -> Salida ya con los datos recogidos");
			}
		}
		baremos.setCombooItems(datos);
		return baremos;
	}

	@Override
	public DatosCalendarioItem getCalendario(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<DatosCalendarioItem> datos = new ArrayList<DatosCalendarioItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getCalendario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCalendario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getCalendario() -> Entrada para obtener los datos del calendario");

				datos = scsGuardiasturnoExtendsMapper.getCalendario(idGuardia);

				LOGGER.info("getCalendario() -> Salida ya con los datos recogidos");
			}
		}

		return datos.size() > 0 ? datos.get(0) : null;
	}

	@Transactional
	@Override
	public InscripcionGuardiaDTO searchColaGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//idInstitucion = (short) 2005; // borrar
		String ordenaciones = "";
		InscripcionGuardiaDTO inscritos = new InscripcionGuardiaDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchColaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchColaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try {

				if (usuarios != null && usuarios.size() > 0) {
					LOGGER.info("searchGuardias() -> Entrada para obtener las colas de guardia");
					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()));

					List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper.selectByExample(example);
					String ultimo = "";

					if (guardias != null && !guardias.isEmpty())
						ultimo = guardias.get(0).getIdpersonaUltimo() == null ? ""
								: guardias.get(0).getIdpersonaUltimo().toString();

					String fecha = "";

					ScsOrdenacioncolasExample example2 = new ScsOrdenacioncolasExample();
					example2.createCriteria()
							.andIdordenacioncolasEqualTo(Integer.valueOf(guardiasItem.getIdOrdenacionColas()));
					// Segun lo que nos llega del front preparamos el ORDER BY que habra en la
					// query.
					List<ScsOrdenacioncolas> cola = scsOrdenacionColasExtendsMapper.selectByExample(example2);
					Map<Short, String> mapilla = new HashMap();
					Map<Short, String> mapa = new TreeMap<Short, String>(Collections.reverseOrder());
					if (cola != null && !cola.isEmpty()) {
						if (cola.get(0).getAntiguedadcola() > 0)
							mapilla.put(cola.get(0).getAntiguedadcola(), "ANTIGUEDADCOLA,");
						else if (cola.get(0).getAntiguedadcola() < 0) {
							cola.get(0).setAntiguedadcola((short) -cola.get(0).getAntiguedadcola());
							mapilla.put(cola.get(0).getAntiguedadcola(), "ANTIGUEDADCOLA desc,");
						}
						if (cola.get(0).getAlfabeticoapellidos() > 0)
							mapilla.put(cola.get(0).getAlfabeticoapellidos(), "ALFABETICOAPELLIDOS,");
						else if (cola.get(0).getAlfabeticoapellidos() < 0) {
							cola.get(0).setAlfabeticoapellidos((short) -cola.get(0).getAlfabeticoapellidos());
							mapilla.put(cola.get(0).getAlfabeticoapellidos(), "ALFABETICOAPELLIDOS desc,");
						}
						if (cola.get(0).getFechanacimiento() > 0)
							mapilla.put(cola.get(0).getFechanacimiento(), "FECHANACIMIENTO,");
						else if (cola.get(0).getFechanacimiento() < 0) {
							cola.get(0).setFechanacimiento((short) -cola.get(0).getFechanacimiento());
							mapilla.put(cola.get(0).getFechanacimiento(), "FECHANACIMIENTO desc,");

						}
						if (cola.get(0).getOrdenacionmanual() > 0)
							mapilla.put(cola.get(0).getOrdenacionmanual(), "NUMEROGRUPO, ORDENGRUPO,");

						if (cola.get(0).getNumerocolegiado() > 0)
							mapilla.put(cola.get(0).getNumerocolegiado(), "NUMEROCOLEGIADO,");
						else if (cola.get(0).getNumerocolegiado() < 0) {
							cola.get(0).setNumerocolegiado((short) -cola.get(0).getNumerocolegiado());
							mapilla.put(cola.get(0).getNumerocolegiado(), "NUMEROCOLEGIADO desc,");
						}
						mapa.putAll(mapilla);
						if (mapa.size() > 0)
							for (String orden : mapa.values()) {
								ordenaciones += orden;
							}
						if (!ordenaciones.isEmpty())
							ordenaciones.substring(0, ordenaciones.length() - 1);
					}
					// Si hay ultimo se prepara su WHERE correspondiente
					if (!UtilidadesString.esCadenaVacia(ultimo))
						ultimo = "WHERE\r\n" + "		idpersona =" + ultimo;
					String grupoUltimo = "";

					if (guardias.get(0).getIdgrupoguardiaUltimo() != null)
						grupoUltimo = "and idgrupoguardia = " + guardias.get(0).getIdgrupoguardiaUltimo();

					List<InscripcionGuardiaItem> colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(
							guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(),
							ultimo, ordenaciones, idInstitucion.toString(), grupoUltimo);
					
					for (int i = 0; i < colaGuardia.size(); i++) {
						if (colaGuardia.get(i).getNumeroGrupo() == null || colaGuardia.get(i).getNumeroGrupo().equals("null")) {
							colaGuardia.remove(i);
						}
					}
					inscritos.setInscripcionesItem(colaGuardia);

					// Si se ha pasado de por grupos a sin grupos o que estuviese sin grupos y se
					// añada la ord. manual
					// hay que updatear todos los grupos
					// y generarlos con el mismo valor de la posicion que tienen en la lista.
					if (colaGuardia != null && colaGuardia.size() > 0 && resetGrupos) {
						resetGrupos = false;

						ScsGrupoguardia grupo = null;
						ScsGrupoguardiacolegiado grupoColegiado = null;

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						String strDate = formatter.format(new Date());

						List<InscripcionGuardiaItem> todaColaGuardia = scsInscripcionguardiaExtendsMapper
								.getColaGuardias(guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), strDate,
										ultimo, ordenaciones, idInstitucion.toString(), grupoUltimo);
						// Obtenemos el ultimo id generado en los grupos
						ScsGrupoguardiaExample grupoGuardiaExample = new ScsGrupoguardiaExample();
						grupoGuardiaExample.createCriteria()
								.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
								.andIdinstitucionEqualTo(idInstitucion);
						List<ScsGrupoguardia> gruposGuardia = scsGrupoguardiaExtendsMapper
								.selectByExample(grupoGuardiaExample);

						ScsGrupoguardiacolegiadoExample scsGrupoguardiacolegiadoExample = null;

						for (int i = 0; i < todaColaGuardia.size(); i++) {
							grupoColegiado = new ScsGrupoguardiacolegiado();
							// Aqui se buscan de los grupos existentes uno cuyo numero sea adecuado para el
							// orden cola. Si encuentra uno le settea el campo FK.
							for (ScsGrupoguardia g : gruposGuardia)
								if ((i + 1) == g.getNumerogrupo())
									grupoColegiado.setIdgrupoguardia(g.getIdgrupoguardia());
							// Aqui creamos un Grupoguardiacolegiado en caso de que no tenga uno asignado.
							if (todaColaGuardia.get(i).getIdGrupoGuardiaColegiado() == null) {
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setFechacreacion(new Date());
								grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(1);
								grupoColegiado.setFechasuscripcion(todaColaGuardia.get(i).getFechaSuscripcion());
								grupoColegiado.setIdinstitucion(idInstitucion);
								grupoColegiado.setIdpersona(Long.valueOf(todaColaGuardia.get(i).getIdPersona()));
								grupoColegiado.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
								grupoColegiado.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));

								NewIdDTO idP = scsGrupoguardiacolegiadoExtendsMapper.getLastId();

								if (idP == null)
									grupoColegiado.setIdgrupoguardiacolegiado((long) 1);
								else
									grupoColegiado.setIdgrupoguardiacolegiado(Long.parseLong(idP.getNewId()) + 1);

								// Si se encontro un grupo se inserta sino se crea uno nuevo y se asigna.
								if (grupoColegiado.getIdgrupoguardia() != null)
									scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);
								else {
									grupo = new ScsGrupoguardia();

									// grupo.setIdgrupoguardia((long) idGrupo);
									grupo.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
									grupo.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
									grupo.setFechamodificacion(new Date());
									grupo.setFechacreacion(new Date());
									grupo.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setNumerogrupo(i + 1);
									grupo.setIdinstitucion(idInstitucion);

									scsGrupoguardiaExtendsMapper.insertSelective(grupo);

									grupoColegiado.setIdgrupoguardia(grupo.getIdgrupoguardia());

									scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

								}

							} else {
								// En este caso el Grupoguardiacolegiado existe y solo setteamos
								// lo necesario.
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(1);

								scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();
								scsGrupoguardiacolegiadoExample.createCriteria()
										.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
										.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
										.andIdgrupoguardiacolegiadoEqualTo(
												Long.valueOf(todaColaGuardia.get(i).getIdGrupoGuardiaColegiado()))
										.andIdinstitucionEqualTo(idInstitucion);

								// Si previamente se le encontro grupo se inserta directamente
								if (grupoColegiado.getIdgrupoguardia() != null)
									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
								// Sino se crea uno, se asigna y entonces insertamos
								else {
									grupo = new ScsGrupoguardia();

									// grupo.setIdgrupoguardia((long) idGrupo);
									grupo.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
									grupo.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
									grupo.setFechamodificacion(new Date());
									grupo.setFechacreacion(new Date());
									grupo.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setNumerogrupo(i + 1);
									grupo.setIdinstitucion(idInstitucion);

									scsGrupoguardiaExtendsMapper.insertSelective(grupo);

									grupoColegiado.setIdgrupoguardia(grupo.getIdgrupoguardia());

									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
								}
							}

						}
						colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(guardiasItem.getIdGuardia(),
								guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(), ultimo, ordenaciones,
								idInstitucion.toString(), grupoUltimo);
						for (int i = 0; i < colaGuardia.size(); i++) {
							if(colaGuardia.get(i).getNombre() == "CARLA") {
								String duda = colaGuardia.get(i).getNumeroGrupo();
							}
							if (colaGuardia.get(i).getNumeroGrupo() == null || colaGuardia.get(i).getNumeroGrupo() == "null") {
								colaGuardia.remove(i);
							}
						}
						inscritos.setInscripcionesItem(colaGuardia);
					}

				}

				LOGGER.info("searchColaGuardia() -> Salida ya con los datos recogidos");
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		return inscritos;
	}

	@Override
	public UpdateResponseDTO updateUltimoCola(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("updateUltimoCola() ->  Entrada al servicio para editar guardia");

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
					"updateUltimoCola() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateUltimoCola() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsGuardiasturno guardia = new ScsGuardiasturno();
					guardia.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
					guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
					guardia.setIdinstitucion(idInstitucion);

					LOGGER.info(
							"updateUltimoCola() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear DatosGenerales de guardias");
					// Se obtiene de la tabla de inscripciones la fecha de suscripcion y se asigna
					// el idpersona.
					// Hacen falta ambos para definir el ultimo de la cola.
					ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(guardiasItem.getIdPersonaUltimo()))
							.andFechasuscripcionIsNotNull().andFechabajaIsNull().andFechavalidacionIsNotNull()
							.andFechadenegacionIsNull();
					List<ScsInscripcionguardia> inscripciones = scsInscripcionguardiaExtendsMapper
							.selectByExample(example);
					if (!inscripciones.isEmpty()) {
						guardia.setFechasuscripcionUltimo(inscripciones.get(0).getFechasuscripcion());
						guardia.setIdpersonaUltimo(Long.valueOf(guardiasItem.getIdPersonaUltimo()));
						// if(!UtilidadesString.esCadenaVacia(guardiasItem.getIdGrupoUltimo()))
						guardia.setIdgrupoguardiaUltimo(guardiasItem.getIdGrupoUltimo() == null ? null
								: Long.valueOf(guardiasItem.getIdGrupoUltimo()));

						response = scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);
					} else
						throw new Exception("No se encontró la inscripcion a la que asignarle último");
				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("updateUltimoCola() -> Salida del servicio para editar guardia");

		return updateResponseDTO;

	}

	@Override
	public GuardiasDTO resumenIncompatibilidades(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		List<GuardiasItem> guardias2 = new ArrayList<GuardiasItem>();
		GuardiasDTO guardiaDTO = new GuardiasDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem, idInstitucion.toString());
//				guardiasItem.setIdGuardia("358");//borrar
//				guardiasItem.setIdTurno("177");//borrar
//				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem,
//						"2003"); //borrar
				String numIcompatibilidades = guardias.get(0).getIncompatibilidades();
				guardias2 = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades2(guardiasItem, "2003");
				guardias2.forEach(guardia -> {
					guardia.setIncompatibilidades(numIcompatibilidades);
					guardia.setTipoDia(("Selección: Labor. " + guardia.getSeleccionLaborables() + ", Fest. "
							+ guardia.getSeleccionFestivos()).replace("null", ""));
				});
				guardiaDTO.setGuardiaItems(guardias);
				LOGGER.info("resumenIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}

		return guardiaDTO;
	}

	@Override
	public TurnosDTO resumenTurno(String idTurno, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<TurnosItem> turnos = new ArrayList<TurnosItem>();
		TurnosDTO turnoDTO = new TurnosDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"resumenTurno() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"resumenTurno() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenTurno() -> Entrada para obtener los resumen turno");

				turnos = scsTurnosExtendsMapper.resumenTurnoColaGuardia(idTurno, idInstitucion.toString());
				List<TurnosItem> partidoJudicial = scsSubzonapartidoExtendsMapper.getPartidoJudicialTurno(
						turnos.get(0).getIdzona(), turnos.get(0).getIdzubzona(), idInstitucion.toString());
				if (partidoJudicial.size() > 0)
					turnos.get(0).setPartidoJudicial(partidoJudicial.get(0).getPartidoJudicial());
				turnoDTO.setTurnosItems(turnos);
				LOGGER.info("resumenTurno() -> Salida ya con los datos recogidos");
			}
		}

		return turnoDTO;
	}

	@Transactional
	@Override
	public UpdateResponseDTO guardarColaGuardias(List<InscripcionGuardiaItem> inscripciones,
			HttpServletRequest request) {
		LOGGER.info("guardarColaGuardias() ->  Entrada al servicio para editar guardia");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarColaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarColaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					GuardiasItem guardiasItem = new GuardiasItem();
					if (inscripciones != null && inscripciones.size() > 0) {
						guardiasItem.setIdTurno(inscripciones.get(0).getIdTurno());
						guardiasItem.setIdGuardia(inscripciones.get(0).getIdGuardia());
					}
					List<InscripcionGuardiaItem> inscripcionesGrupoNuevo = new ArrayList<InscripcionGuardiaItem>();
					// Obtenemos el ultimo id generado en los grupos
					NewIdDTO idGrupoDTO = scsGrupoguardiaExtendsMapper.getLastId();
					Integer idGrupo = 0;

					ScsGrupoguardia grupo = null;
					ScsGrupoguardiacolegiado grupoColegiado = null;
					ScsGrupoguardiacolegiadoExample scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();

					if (idGrupoDTO != null)
						idGrupo = Integer.valueOf(idGrupoDTO.getNewId());

					// Comprobamos si hay algun grupo nuevo
					ScsGrupoguardiaExample grupoGuardiaExample = new ScsGrupoguardiaExample();
					grupoGuardiaExample.createCriteria()
							.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGrupoguardia> gruposExistentes = scsGrupoguardiaExtendsMapper
							.selectByExample(grupoGuardiaExample);
					List<ScsGrupoguardia> grupoPerteneciente = null;
					for (int i = 0; i < inscripciones.size(); i++) {
						InscripcionGuardiaItem element = inscripciones.get(i);
						// Vemos si la inscripcion tiene grupo y si existe
						if (element.getNumeroGrupo() != null && !"".equals(element.getNumeroGrupo()))
							grupoPerteneciente = gruposExistentes.stream()
									.filter(it -> Integer.valueOf(element.getNumeroGrupo()).equals(it.getNumerogrupo()))
									.collect(Collectors.toList());
						// Si no pertenece a ninguno se añade a la lista encargada de los nuevos grupos.
						if (grupoPerteneciente == null || grupoPerteneciente.size() == 0) {
							inscripcionesGrupoNuevo.add(element); // Aqui vemos si hay alguno nuevo y si lo hay
						} else {
							grupoColegiado = new ScsGrupoguardiacolegiado();
							// Si tenia Grupoguardiacolegiado, es decir, no tenia ni grupo ni orden
							// creamos uno. Para crear es importante la Fechasuscripcion que se obtiene
							// con mybatis
							if (element.getIdGrupoGuardiaColegiado() == null
									|| element.getIdGrupoGuardiaColegiado().equals("")) {
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setFechacreacion(new Date());
								grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(Integer.valueOf(element.getOrden()));
								grupoColegiado.setIdgrupoguardia(grupoPerteneciente.get(0).getIdgrupoguardia());
								grupoColegiado.setIdinstitucion(idInstitucion);
								grupoColegiado.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
								grupoColegiado.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
								grupoColegiado.setIdpersona(Long.valueOf(element.getIdPersona()));

								ScsInscripcionguardiaExample inscripExample = new ScsInscripcionguardiaExample();
								inscripExample.createCriteria()
										.andIdpersonaEqualTo(Long.valueOf(element.getIdPersona()))
										.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
										.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
										.andIdinstitucionEqualTo(idInstitucion).andFechasuscripcionIsNotNull()
										.andFechabajaIsNull().andFechavalidacionIsNotNull().andFechadenegacionIsNull();
								ScsInscripcionguardia inscripcionFechSus = scsInscripcionguardiaExtendsMapper
										.selectByExample(inscripExample).get(0);

								grupoColegiado.setFechasuscripcion(inscripcionFechSus.getFechasuscripcion());

								scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

							} else {

								// Aqui cambiamos los datos necesarios del grupo guardia en caso de que
								// haya que actualizar.
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(Integer.valueOf(element.getOrden()));

								scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();
								scsGrupoguardiacolegiadoExample.createCriteria()
										.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
										.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
										.andIdgrupoguardiacolegiadoEqualTo(
												Long.valueOf(element.getIdGrupoGuardiaColegiado()))
										.andIdinstitucionEqualTo(idInstitucion);

								grupoColegiado.setIdgrupoguardia(grupoPerteneciente.get(0).getIdgrupoguardia());

								scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
										scsGrupoguardiacolegiadoExample);
							}
						}
					}

					// Si hay grupos nuevos los creamos
					if (inscripcionesGrupoNuevo != null && inscripcionesGrupoNuevo.size() > 0) {
						Integer ultimoGrupo = 1;

						for (ScsGrupoguardia it : gruposExistentes)
							if (it.getNumerogrupo() > ultimoGrupo)
								ultimoGrupo = (int) it.getNumerogrupo();

						for (int i = 0; i < inscripcionesGrupoNuevo.size(); i++) {
							if (inscripcionesGrupoNuevo.get(i).getNumeroGrupo() == null
									|| inscripcionesGrupoNuevo.get(i).getNumeroGrupo().equals("")) {

								ScsGrupoguardiacolegiadoExample grupoGuardiaColegiadoExample = new ScsGrupoguardiacolegiadoExample();
								grupoGuardiaColegiadoExample.createCriteria().andIdgrupoguardiacolegiadoEqualTo(
										Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado()));
								scsGrupoguardiacolegiadoExtendsMapper.deleteByExample(grupoGuardiaColegiadoExample);

							} else {
								idGrupo += 1;
								ultimoGrupo += 1;
								grupo = new ScsGrupoguardia();

								grupo.setIdgrupoguardia((long) idGrupo);
								grupo.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
								grupo.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
								grupo.setFechamodificacion(new Date());
								grupo.setFechacreacion(new Date());
								grupo.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
								grupo.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupo.setNumerogrupo(Integer.valueOf(inscripcionesGrupoNuevo.get(i).getNumeroGrupo()));
								grupo.setIdinstitucion(idInstitucion);

								scsGrupoguardiaExtendsMapper.insert(grupo);

								grupoColegiado = new ScsGrupoguardiacolegiado();
								// Aqui se crea un nuevo grupo colegiado en caso de que el recibido no tenga
								// idgrupoguardiacolegiado
								if (inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado() == null) {
									grupoColegiado.setFechamodificacion(new Date());
									grupoColegiado.setFechacreacion(new Date());
									grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setOrden(Integer.valueOf(inscripcionesGrupoNuevo.get(i).getOrden()));
									grupoColegiado.setIdgrupoguardia(grupoPerteneciente.get(0).getIdgrupoguardia());
									grupoColegiado.setIdinstitucion(idInstitucion);
									grupoColegiado.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
									grupoColegiado.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
									grupoColegiado
											.setIdpersona(Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdPersona()));

									ScsInscripcionguardiaExample inscripExample = new ScsInscripcionguardiaExample();
									inscripExample.createCriteria()
											.andIdpersonaEqualTo(
													Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdPersona()))
											.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
											.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
											.andIdinstitucionEqualTo(idInstitucion).andFechasuscripcionIsNotNull()
											.andFechabajaIsNull().andFechavalidacionIsNotNull()
											.andFechadenegacionIsNull();
									ScsInscripcionguardia inscripcionFechSus = scsInscripcionguardiaExtendsMapper
											.selectByExample(inscripExample).get(0);

									grupoColegiado.setFechasuscripcion(inscripcionFechSus.getFechasuscripcion());

									scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

								} else {
									// Aqui cambiamos los datos necesarios del grupo guardia y lo actualizamos.
									grupoColegiado.setFechamodificacion(new Date());
									grupoColegiado.setFechacreacion(new Date());
									grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());

									scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();
									scsGrupoguardiacolegiadoExample.createCriteria()
											.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
											.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
											.andIdgrupoguardiacolegiadoEqualTo(Long.valueOf(
													inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado()))
											.andIdinstitucionEqualTo(idInstitucion);

									grupoColegiado.setIdgrupoguardia((long) idGrupo);
									grupoColegiado.setOrden(Integer.valueOf(inscripcionesGrupoNuevo.get(i).getOrden()));

									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
								}
							}
						}
					}
				} catch (Exception e) {
					LOGGER.error(e);
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

		LOGGER.info("guardarColaGuardia() -> Salida del servicio para guardar la cola de guardia");

		return updateResponseDTO;
	}

	@Override
	public LetradosGuardiaDTO letradosGuardia(String idTurno, String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		LetradosGuardiaDTO letradosGuardiaDTO = new LetradosGuardiaDTO();
		Error error = new Error();

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"letradosGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"letradosGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					List<LetradoGuardiaItem> letradosGuardiaItem = scsGuardiasturnoExtendsMapper
							.searchLetradosGuardia(Short.toString(idInstitucion), idTurno, idGuardia);

					letradosGuardiaDTO.setLetradosGuardiaItem(letradosGuardiaItem);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"GuardiasServiceImpl.letradosGuardia() -> Se ha producido un error en consultando los letrados", e);

			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());

			letradosGuardiaDTO.setError(error);
		}

		return letradosGuardiaDTO;
	}

	
	@Override
	public IncompatibilidadesDTO getIncompatibilidades( IncompatibilidadesDatosEntradaItem incompBody, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<IncompatibilidadesItem> incompatibilidades = new ArrayList<IncompatibilidadesItem>();
		IncompatibilidadesDTO inc = new IncompatibilidadesDTO();
		String idGuardia = ""; 
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				//incompatibilidades = scsIncompatibilidadguardiasExtendsMapper.getListadoIncompatibilidades(incompBody, idInstitucion.toString(), idGuardia);

				
				LOGGER.info("tarjetaIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}
		/*BORRAR*/
		IncompatibilidadesItem item = new IncompatibilidadesItem(); 
		item.setDiasSeparacionGuardias("0");
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba1");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("100");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("101");
		item.setNombreTurnoIncompatible("prueba");
		incompatibilidades.add(item);
		IncompatibilidadesItem item1 = new IncompatibilidadesItem(); 
		item1.setDiasSeparacionGuardias("0");
		item1.setMotivos("prueba");
		item1.setNombreGuardia("prueba");
		item1.setNombreGuardiaIncompatible("prueba2");
		item1.setNombreTurno("prueba");
		item1.setExiste("existe");
		item1.setIdGuardia("14");
		item1.setIdGuardiaIncompatible("102");
		item1.setIdTurno("idTurno");
		item1.setIdTurnoIncompatible("103");
		item1.setNombreTurnoIncompatible("prueba");
		incompatibilidades.add(item1);
		IncompatibilidadesItem item2 = new IncompatibilidadesItem(); 
		item2.setDiasSeparacionGuardias("0");
		item2.setMotivos("prueba");
		item2.setNombreGuardia("prueba");
		item2.setNombreGuardiaIncompatible("prueba3");
		item2.setNombreTurno("prueba");
		item2.setExiste("existe");
		item2.setIdGuardia("14");
		item2.setIdGuardiaIncompatible("104");
		item2.setIdTurno("idTurno");
		item2.setIdTurnoIncompatible("105");
		item2.setNombreTurnoIncompatible("prueba");
		incompatibilidades.add(item2);
		IncompatibilidadesItem item3 = new IncompatibilidadesItem(); 
		item3.setDiasSeparacionGuardias("1");
		item3.setMotivos("prueba");
		item3.setNombreGuardia("prueba");
		item3.setNombreGuardiaIncompatible("prueba4");
		item3.setNombreTurno("prueba");
		item3.setExiste("existe");
		item3.setIdGuardia("14");
		item3.setIdGuardiaIncompatible("106");
		item3.setIdTurno("idTurno");
		item3.setIdTurnoIncompatible("107");
		item3.setNombreTurnoIncompatible("prueba");
		incompatibilidades.add(item3);
		IncompatibilidadesItem item4 = new IncompatibilidadesItem(); 
		item4.setDiasSeparacionGuardias("3");
		item4.setMotivos("prueba");
		item4.setNombreGuardia("prueba");
		item4.setNombreGuardiaIncompatible("prueba5");
		item4.setNombreTurno("prueba");
		item4.setExiste("existe");
		item4.setIdGuardia("14");
		item4.setIdGuardiaIncompatible("108");
		item4.setIdTurno("idTurno");
		item4.setIdTurnoIncompatible("109");
		item4.setNombreTurnoIncompatible("prueba");
		item4.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item4);
		IncompatibilidadesItem item5 = new IncompatibilidadesItem(); 
		item5.setMotivos("prueba");
		item5.setNombreGuardia("prueba");
		item5.setNombreGuardiaIncompatible("prueba6");
		item5.setNombreTurno("prueba");
		item5.setExiste("existe");
		item5.setIdGuardia("14");
		item5.setIdGuardiaIncompatible("110");
		item5.setIdTurno("idTurno");
		item5.setIdTurnoIncompatible("111");
		item5.setNombreTurnoIncompatible("prueba");
		item5.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item5);
		IncompatibilidadesItem item6 = new IncompatibilidadesItem(); 
		item6.setMotivos("prueba");
		item6.setNombreGuardia("prueba");
		item6.setNombreGuardiaIncompatible("prueba7");
		item6.setNombreTurno("prueba");
		item6.setExiste("existe");
		item6.setIdGuardia("14");
		item6.setIdGuardiaIncompatible("112");
		item6.setIdTurno("idTurno");
		item6.setIdTurnoIncompatible("113");
		item6.setNombreTurnoIncompatible("prueba");
		item6.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item6);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba8");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("114");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("115");
		item.setNombreTurnoIncompatible("prueba");
		incompatibilidades.add(item);
		item.setDiasSeparacionGuardias("0");
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba9");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("100");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("101");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba10");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("102");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("103");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba11");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("104");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("105");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba12");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("106");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("107");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba13");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("108");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("109");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba14");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("110");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("111");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba15");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("112");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("113");
		item.setNombreTurnoIncompatible("prueba");
		item.setDiasSeparacionGuardias("0");
		incompatibilidades.add(item);
		item.setMotivos("prueba");
		item.setNombreGuardia("prueba");
		item.setNombreGuardiaIncompatible("prueba16");
		item.setNombreTurno("prueba");
		item.setExiste("existe");
		item.setIdGuardia("14");
		item.setIdGuardiaIncompatible("114");
		item.setIdTurno("idTurno");
		item.setIdTurnoIncompatible("115");
		item.setNombreTurnoIncompatible("prueba");
		incompatibilidades.add(item);
		
		
		/*BORRAR*/
		inc.setIncompatibilidadesItem(incompatibilidades);
		return inc;
	}
	
	@Override
	public DeleteResponseDTO deleteIncompatibilidades(DeleteIncompatibilidadesDatosEntradaItem deleteIncompatibilidadesBody, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idGuardia = ""; // DUDA

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");
				scsIncompatibilidadguardiasExtendsMapper.deleteIncompatibilidades(deleteIncompatibilidadesBody.getIdTurno(), deleteIncompatibilidadesBody.getIdInstitucion(), deleteIncompatibilidadesBody.getIdGuardia(), deleteIncompatibilidadesBody.getIdTurnoIncompatible(), deleteIncompatibilidadesBody.getIdGuardiaIncompatible());
				LOGGER.info("tarjetaIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}
		
		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("deleteSubtipoCurricular() -> OK. Delete para tipo curricular realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("deleteSubtipoCurricular() -> KO. Delete para tipo curricular NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteSubtipoCurricular() -> Salida del servicio para eliminar subtipo curricular");
		return deleteResponseDTO;
	}
	
	
	@Override
	public DeleteResponseDTO saveIncompatibilidades(SaveIncompatibilidadesDatosEntradaItem incompatibilidadesBody,
			HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String idInstitucion = incompatibilidadesBody.getIdInstitucion();
		String idGuardia = ""; // DUDA

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			//if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");
				
				int existe = scsIncompatibilidadguardiasExtendsMapper.checkIncompatibilidadesExists(incompatibilidadesBody.getIdTurno(), incompatibilidadesBody.getIdInstitucion(), incompatibilidadesBody.getIdGuardia(), incompatibilidadesBody.getIdTurnoIncompatible(), incompatibilidadesBody.getIdGuardiaIncompatible());
				if (existe == 2) {
					//existe en ambas direcciones - la actualizamos
					scsIncompatibilidadguardiasExtendsMapper.updateIfExists(incompatibilidadesBody.getIdTurno(), incompatibilidadesBody.getIdInstitucion(), incompatibilidadesBody.getIdGuardia(), incompatibilidadesBody.getIdTurnoIncompatible(), incompatibilidadesBody.getIdGuardiaIncompatible(), incompatibilidadesBody.getMotivos(), incompatibilidadesBody.getDiasSeparacionGuardias());
					
				}else {
					String pattern = "dd/MM/YY";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					String fechaModificacion = simpleDateFormat.format(new Date());	
					//no existe - llamamos dos veces para guardar en ambas direcciones
					scsIncompatibilidadguardiasExtendsMapper.saveListadoIncompatibilidades(Integer.parseInt(incompatibilidadesBody.getIdTurno()), Integer.parseInt(incompatibilidadesBody.getIdInstitucion()), Integer.parseInt(incompatibilidadesBody.getIdGuardia()), Integer.parseInt(incompatibilidadesBody.getIdTurnoIncompatible()), Integer.parseInt(incompatibilidadesBody.getIdGuardiaIncompatible()), usuarios.get(0).getIdusuario(), incompatibilidadesBody.getMotivos(), Integer.parseInt(incompatibilidadesBody.getDiasSeparacionGuardias()), fechaModificacion);
					scsIncompatibilidadguardiasExtendsMapper.saveListadoIncompatibilidades(Integer.parseInt(incompatibilidadesBody.getIdTurno()), Integer.parseInt(incompatibilidadesBody.getIdInstitucion()), Integer.parseInt(incompatibilidadesBody.getIdGuardiaIncompatible()), Integer.parseInt(incompatibilidadesBody.getIdTurnoIncompatible()), Integer.parseInt(incompatibilidadesBody.getIdGuardia()), usuarios.get(0).getIdusuario(), incompatibilidadesBody.getMotivos(), Integer.parseInt(incompatibilidadesBody.getDiasSeparacionGuardias()), fechaModificacion);

				}
				
				LOGGER.info("tarjetaIncompatibilidades() -> Salida ya con los datos recogidos");
			//}
		}
		
		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("deleteSubtipoCurricular() -> OK. Delete para tipo curricular realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("deleteSubtipoCurricular() -> KO. Delete para tipo curricular NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteSubtipoCurricular() -> Salida del servicio para eliminar subtipo curricular");
		return deleteResponseDTO;
	}
	
}

