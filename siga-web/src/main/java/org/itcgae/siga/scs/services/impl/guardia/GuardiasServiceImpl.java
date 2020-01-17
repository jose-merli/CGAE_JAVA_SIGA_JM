package org.itcgae.siga.scs.services.impl.guardia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
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

	@Override
	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
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
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				List<GuardiasItem> guardias = scsGuardiasturnoExtendsMapper.searchGuardias(guardiasItem,
						idInstitucion.toString(), usuarios.get(0).getIdlenguaje());

				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
							+ it.getSeleccionFestivos()).replace("null", ""));
					return it;
				}).collect(Collectors.toList());

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

		LOGGER.info("getGuardia() ->  Entrada al servicio para obtener una guardia");

		GuardiasItem guardiaItem = new GuardiasItem();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activagetGuardiateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"getGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a la busqueda de la guardia");

					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(guardiaTurno.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiaTurno.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper.selectByExample(example);
					if (guardiasList != null && guardiasList.size() > 0) {

						LOGGER.info(
								"getGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Mapeamos lo recogido");
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
								guardiaItem.setIdGuardiaVinculada(it.getNombre() + ",");
								guardiaItem.setIdTurnoVinculada(it.getTurno() + ",");
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
						guardiaItem.setRequeridaValidacion(guardia.getRequeridavalidacion().equals("S") ? true : false);
						// String diasSeparacion =
						// scsGuardiasturnoExtendsMapper.separarGuardias(idGuardia,
						// guardia.getIdturno().toString(), idInstitucion.toString()).get(0);
						// guardiaItem.setDiasSeparacionGuardias(UtilidadesString.esCadenaVacia(diasSeparacion)?
						// diasSeparacion : "0");

						// Check de separarGuardiasCalendario
						List<GuardiasItem> checkeado = scsHitofacturableguardiaExtendsMapper.getCheckSeparacionGuardias(
								guardia.getIdturno().toString(), guardia.getIdguardia().toString(),
								idInstitucion.toString());
						if (checkeado.size() > 0)
							guardiaItem.setSepararGuardia(checkeado.get(0).getSepararGuardia());

						guardiaItem.setSeleccionLaborables(guardia.getSeleccionlaborables());
						guardiaItem.setSeleccionFestivos(guardia.getSeleccionfestivos());

						// Cola de guardia
						if (guardia.getIdpersonaUltimo() != null)
							guardiaItem.setIdPersonaUltimo(guardia.getIdpersonaUltimo() + "");

					}
					LOGGER.info(
							"getGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de la busqueda de guardia");

				} catch (Exception e) {
					LOGGER.error(e);
				}
			}

		}

		LOGGER.info("getGuardia() -> Salida del servicio para recoger una guardia");

		return guardiaItem;

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

					LOGGER.info(
							"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear DatosGenerales de guardias");

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
						guardia.setIdturnoprincipal(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()));
						guardia.setIdguardiaprincipal(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()));

					} else if (guardiasItem.getLetradosGuardia() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Configuracion de Cola de guardias");
						ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
						example.createCriteria().andIdturnoEqualTo(guardia.getIdturno())
								.andIdguardiaEqualTo(guardia.getIdguardia()).andIdinstitucionEqualTo(idInstitucion);
						List<ScsGuardiasturno> item = scsGuardiasturnoExtendsMapper.selectByExample(example);
						resetGrupos = false;
						if (item.get(0).getPorgrupos().equals("1") && !Boolean.valueOf(guardiasItem.getPorGrupos())
								&& Short.valueOf(guardiasItem.getFiltros().split(",")[4]) != 0) {
							resetGrupos = true;
						}
						guardia.setPorgrupos((Boolean.valueOf(guardiasItem.getPorGrupos()) ? "1" : "0"));
						guardia.setIdordenacioncolas(Integer.valueOf(guardiasItem.getIdOrdenacionColas()));
						// ROTAR COMPONENTES?
						guardia.setNumeroletradosguardia(Integer.valueOf(guardiasItem.getLetradosGuardia()));

						ScsOrdenacioncolasExample ordExample = new ScsOrdenacioncolasExample();
						ordExample.createCriteria()
								.andAlfabeticoapellidosEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[0]))
								.andFechanacimientoEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[1]))
								.andNumerocolegiadoEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[2]))
								.andAntiguedadcolaEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[3]))
								.andOrdenacionmanualEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[4]));

						List<ScsOrdenacioncolas> colas = scsOrdenacionColasExtendsMapper.selectByExample(ordExample);

						if (colas.isEmpty()) {
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

					} else {

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
						guardia.setIdordenacioncolas(1); // Esta creo que esta especialmente mal
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
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				guardias = scsGuardiasturnoExtendsMapper.getResumen(guardiasItem.getIdGuardia(),
						guardiasItem.getIdTurno(), idInstitucion.toString(), usuarios.get(0).getIdlenguaje());

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
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
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");
				guardias = scsGuardiasturnoExtendsMapper.resumenConfCola(guardia.getIdGuardia(), guardia.getIdTurno(),
						idInstitucion.toString());
				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
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
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				guardias = scsIncompatibilidadguardiasExtendsMapper.tarjetaIncompatibilidades(idGuardia,
						idInstitucion.toString());
				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
							+ it.getSeleccionFestivos()).replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
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
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				datos = scsHitofacturableguardiaExtendsMapper.getBaremos(idGuardia, usuarios.get(0).getIdlenguaje());

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
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
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				datos = scsGuardiasturnoExtendsMapper.getCalendario(idGuardia);

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
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
		String ordenaciones = "";
		InscripcionGuardiaDTO inscritos = new InscripcionGuardiaDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try {

				if (usuarios != null && usuarios.size() > 0) {
					LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");
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

					if (!UtilidadesString.esCadenaVacia(ultimo))
						ultimo = "WHERE\r\n" + "		idpersona =" + ultimo;

					List<InscripcionGuardiaItem> colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(
							guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(),
							ultimo, ordenaciones, idInstitucion.toString());
					inscritos.setInscripcionesItem(colaGuardia);

					// Si se ha pasado de por grupos a sin grupos hay que updatear todos los grupos
					// y generarlos con el mismo valor de la posicion que tienen en la lista.
					if (colaGuardia != null && colaGuardia.size() > 0 && resetGrupos) {
						resetGrupos = false;

						ScsGrupoguardia grupo = null;
						ScsGrupoguardiacolegiado grupoColegiado = null;

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						String strDate = formatter.format(new Date());

						List<InscripcionGuardiaItem> todaColaGuardia = scsInscripcionguardiaExtendsMapper
								.getColaGuardias(guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), strDate,
										ultimo, ordenaciones, idInstitucion.toString());
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

							for (ScsGrupoguardia g : gruposGuardia)
								if ((i + 1) == g.getNumerogrupo())
									grupoColegiado.setIdgrupoguardia(g.getIdgrupoguardia());

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
								grupoColegiado.setIdgrupoguardiacolegiado(
										Long.valueOf(scsGrupoguardiacolegiadoExtendsMapper.getLastId().getNewId()) + 1);
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
								// Setteando el nuevo grupo colegiado
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

								if (grupoColegiado.getIdgrupoguardia() != null)
									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
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
								idInstitucion.toString());

						inscritos.setInscripcionesItem(colaGuardia);
					}

				}

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		return inscritos;
	}

	@Override
	public InsertResponseDTO insertColaGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {
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
					//
					// ScsGrupoguardiaExample scsGrupoguardiaExample = new ScsGrupoguardiaExample();
					// scsGrupoguardiaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).
					// andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno())).
					// andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia())).
					// andNumerogrupoEqualsTo(guardiasItem.getPorGrupos());

				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateUltimoCola(GuardiasItem guardiasItem, HttpServletRequest request) {
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
					ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(guardiasItem.getIdPersonaUltimo()))
							.andFechasuscripcionIsNotNull()
							.andFechabajaIsNull().andFechavalidacionIsNotNull()
							.andFechadenegacionIsNull();
					List<ScsInscripcionguardia> inscripciones = scsInscripcionguardiaExtendsMapper
							.selectByExample(example);
					if (!inscripciones.isEmpty()) {
						guardia.setFechasuscripcionUltimo(inscripciones.get(0).getFechasuscripcion());
						guardia.setIdpersonaUltimo(Long.valueOf(guardiasItem.getIdPersonaUltimo()));
						// if(!UtilidadesString.esCadenaVacia(guardiasItem.getIdGrupoUltimo()))
						// guardia.setIdgrupoguardiaUltimo(null);

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
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem,
						idInstitucion.toString());
				guardiaDTO.setGuardiaItems(guardias);
				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
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
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				turnos = scsTurnosExtendsMapper.resumenTurnoColaGuardia(idTurno, idInstitucion.toString());
				List<TurnosItem> partidoJudicial = scsSubzonapartidoExtendsMapper.getPartidoJudicialTurno(
						turnos.get(0).getIdzona(), turnos.get(0).getIdzubzona(), idInstitucion.toString());
				turnos.get(0).setPartidoJudicial(partidoJudicial.get(0).getPartidoJudicial());
				turnoDTO.setTurnosItems(turnos);
				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			}
		}

		return turnoDTO;
	}

	@Transactional
	@Override
	public UpdateResponseDTO guardarColaGuardias(List<InscripcionGuardiaItem> inscripciones,
			HttpServletRequest request) {
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

					// ESTO ES EN CASO DE QUE HAYA QUE COMPROBAR DENTRO
					// List<InscripcionGuardiaItem> todaColaGuardia =
					// scsInscripcionguardiaExtendsMapper.getColaGuardias(
					// guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), "",
					// "", "",idInstitucion.toString());
					//
					// int repe = 0;
					// for(InscripcionGuardiaItem ins: inscripciones) {
					// if(repe<=1)
					// repe = todaColaGuardia.stream().filter(it ->
					// it.getNumeroGrupo().equals(ins.getNumeroGrupo()) &&
					// it.getOrden().equals(ins.getOrden())).collect(Collectors.toList()).size();
					// if(repe<=1)
					// repe = todaColaGuardia.stream().filter(it ->
					// it.getNumeroGrupo().equals(ins.getNumeroGrupo()) &&
					// it.getIdPersona().equals(ins.getIdPersona())).collect(Collectors.toList()).size();
					// }

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

						if (!element.getNumeroGrupo().equals(""))
							grupoPerteneciente = gruposExistentes.stream()
									.filter(it -> Integer.valueOf(element.getNumeroGrupo()).equals(it.getNumerogrupo()))
									.collect(Collectors.toList());
						if (grupoPerteneciente == null || grupoPerteneciente.size() == 0) {
							inscripcionesGrupoNuevo.add(element); // Aqui vemos si hay alguno nuevo y si lo hay
							inscripciones.remove(element); // lo metemos en otra lista.
						} else {
							grupoColegiado = new ScsGrupoguardiacolegiado();

							if (element.getIdGrupoGuardiaColegiado().equals("")) {
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

								if (inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado().equals("")) {
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
				}
			}
		}

		return updateResponseDTO;
	}

}
