package org.itcgae.siga.scs.services.impl.guardia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.DatosCalendarioDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.DTOs.scs.RangoFechasItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasKey;
import org.itcgae.siga.db.entities.ScsDictamenejg;
import org.itcgae.siga.db.entities.ScsDictamenejgExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoKey;
import org.itcgae.siga.db.entities.ScsPermutaCabecera;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraExample;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraKey;
import org.itcgae.siga.db.entities.ScsPermutaguardias;
import org.itcgae.siga.db.entities.ScsPermutaguardiasExample;
import org.itcgae.siga.db.entities.ScsPermutaguardiasKey;
import org.itcgae.siga.db.mappers.ScsPermutaCabeceraMapper;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCabeceraguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiascolegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPermutaguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSaltoscompensacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonapartidoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.guardia.GuardiasColegiadoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuardiasColegiadoServiceImpl implements GuardiasColegiadoService {
	private Logger LOGGER = Logger.getLogger(GuardiasColegiadoServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsSubzonapartidoExtendsMapper scsSubzonapartidoExtendsMapper;

	@Autowired
	private ScsCabeceraguardiasExtendsMapper scsCabeceraguardiasExtendsMapper;

	@Autowired
	private ScsSaltoscompensacionesExtendsMapper scsSaltoscompensacionesExtendsMapper;

	@Autowired
	private ScsPermutaguardiasMapper scsPermutaguardiasMapper;

	@Autowired
	private ScsPermutaCabeceraMapper scsPermutaCabeceraMapper;

	@Autowired
	private ScsPermutaguardiasExtendsMapper scsPermutaguardiasExtendsMapper;

	@Autowired
	private ScsGuardiascolegiadoExtendsMapper scsGuardiascolegiadoExtendsMapper;

	@Override
	public GuardiasDTO getGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request) {
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
					"getGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				GuardiasItem gi = new GuardiasItem();
				gi.setIdGuardia(guardiasItem.getIdGuardia());
				gi.setIdTurno(guardiasItem.getIdTurno());

				List<GuardiasItem> guardia = scsGuardiasturnoExtendsMapper.getGuardiaColeg(gi, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje());
				guardiaDTO.setGuardiaItems(guardia);

				LOGGER.info("getGuardiaColeg() -> Entrada para obtener las guardias");

				LOGGER.info("getGuardiaColeg() -> Salida ya con los datos recogidos");
			}
		}
		return guardiaDTO;
	}

	@Override
	public TurnosDTO getTurnoGuardiaColeg(TurnosItem turnosItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TurnosDTO turnoDTO = new TurnosDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getGuardiaColeg() -> Entrada para obtener la informacion del turno");

				TurnosItem ti = new TurnosItem();

				ti.setIdturno(turnosItem.getIdturno());
				ti.setHistorico(true);// mando el historico ya que la consulta obtiene siempre falso y no devuelve los
										// datos

				List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(ti, idInstitucion);

				turnoDTO.setTurnosItems(turnos);

				LOGGER.info("getGuardiaColeg() -> Salida ya con la informacion recogida");
			}
		}
		return turnoDTO;
	}

	@Override
	public List<DatosCalendarioItem> getCalendarioColeg(String[] datosCalendarioItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<DatosCalendarioItem> datos = new ArrayList<DatosCalendarioItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				String idTurno = datosCalendarioItem[0];
				String idGuardia = datosCalendarioItem[1];
				String idcalendarioguardias = datosCalendarioItem[2];

				// datos =
				// scsCabeceraguardiasExtendsMapper.getCalendarioGuardiaColegiado(idInstitucion.toString(),idTurno,
				// idGuardia, idcalendarioguardias);
				datos = scsGuardiasturnoExtendsMapper.getCalendario(idGuardia);
				LOGGER.info("getCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		return datos;
	}

	@Override
	public ColegiadoDTO getColegiado(ColegiadoItem guardiasItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UpdateResponseDTO updateGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("updateGuardiaColeg() ->  Entrada al servicio para eliminar prisiones");

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
					"updateGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsCabeceraguardias guardia = new ScsCabeceraguardias();
					guardia.setIdinstitucion(idInstitucion);
					guardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					guardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					guardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					guardia.setFechainicio(guardiasItem.getFechadesde());
					guardia.setObservacionesanulacion(guardiasItem.getObservacionesAnulacion());

					response = scsCabeceraguardiasExtendsMapper.updateByPrimaryKeySelective(guardia);

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

		LOGGER.info("updateGuardiaColeg() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("insertGuardiaColeg() ->  Entrada al servicio para eliminar prisiones");

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
					"insertGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsCabeceraguardias guardia = new ScsCabeceraguardias();
					guardia.setIdinstitucion(idInstitucion);
					guardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					guardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					guardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					guardia.setFechainicio(guardiasItem.getFechadesde());

					response = scsCabeceraguardiasExtendsMapper.insertSelective(guardia);

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info(
				"updateGuardiaColeg() -> Salida del servicio para insertar guardia de colegiado en cabeceras de guardias");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO sustituirGuardiaColeg(String[] datos, HttpServletRequest request) {
		LOGGER.info("sustituirGuardiaColeg() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int respCabGuar = 0;
		int respSaltoComp = 0;
		int respPerCab = 0;
		int respPerGuarSol = 0;
		int respPerGuarConf = 0;
		int insertadoSol = 0;
		int insertadoConf = 0;
		int insertadoPc = 0;
		Long idPermutaCabecera;
		SaltoCompGuardiaItem scgi = new SaltoCompGuardiaItem();
		MaxIdDto nuevoId;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int tieneGuardia;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"sustituirGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"sustituirGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					Long fechaHoy = new Date().getTime();

					String institucion = idInstitucion.toString();
					String idTurno = datos[0];
					String idGuardia = datos[1];
					String fechadesde = datos[2];
					String idPersona = datos[3];
					String newLetrado = datos[4];
					String fechaSustitucion = datos[5];
					String comensustitucion = datos[6];
					String saltoOcompensacion = datos[7];
					String calendarioGuardias = datos[8];

					if (scsCabeceraguardiasExtendsMapper.tieneGuardia(institucion, Long.parseLong(newLetrado)) == 0) {

						if (saltoOcompensacion == "N") {
							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia solicitante");

							// OBTENER PERMUTAS DE GUARDIA
							ScsPermutaguardiasExample pgeSol = new ScsPermutaguardiasExample();
							pgeSol.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdguardiaSolicitanteEqualTo(Integer.parseInt(idGuardia))
									.andIdturnoSolicitanteEqualTo(Integer.parseInt(idTurno))
									.andIdpersonaSolicitanteEqualTo(Long.parseLong(idPersona));
							List<ScsPermutaguardias> permGuarSol = scsPermutaguardiasMapper.selectByExample(pgeSol);
							List<ScsPermutaguardias> permGuarSolMod = new ArrayList<ScsPermutaguardias>();
							if (!permGuarSol.isEmpty()) {
								for (ScsPermutaguardias permutaSol : permGuarSol) {

									permutaSol.setIdpersonaSolicitante(Long.parseLong(newLetrado));
									permGuarSolMod.add(permutaSol);

									ScsPermutaguardiasKey key = new ScsPermutaguardiasKey();

									key.setIdinstitucion(permutaSol.getIdinstitucion());
									key.setNumero(permutaSol.getNumero());

									int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);

									if (borra == 1) {
										for (ScsPermutaguardias newPermuta : permGuarSolMod) {
											insertadoSol = scsPermutaguardiasMapper.insertSelective(newPermuta);
										}
									}

									LOGGER.info(
											"sustituirGuardiaColeg() / sustituir letrado en permutaguardia solicitante");
								}
							}

							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia confirmante");

							ScsPermutaguardiasExample pgeConf = new ScsPermutaguardiasExample();
							pgeConf.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdguardiaConfirmadorEqualTo(Integer.parseInt(idGuardia))
									.andIdturnoConfirmadorEqualTo(Integer.parseInt(idTurno))
									.andIdpersonaConfirmadorEqualTo(Long.parseLong(idPersona));
							List<ScsPermutaguardias> permGuarConf = scsPermutaguardiasMapper.selectByExample(pgeSol);
							List<ScsPermutaguardias> permGuarConfMod = new ArrayList<ScsPermutaguardias>();
							if (!permGuarConf.isEmpty()) {
								for (ScsPermutaguardias permutaConf : permGuarConf) {

									permutaConf.setIdpersonaSolicitante(Long.parseLong(newLetrado));
									permGuarConfMod.add(permutaConf);

									ScsPermutaguardiasKey key = new ScsPermutaguardiasKey();

									key.setIdinstitucion(permutaConf.getIdinstitucion());
									key.setNumero(permutaConf.getNumero());

									int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);

									if (borra == 1) {
										for (ScsPermutaguardias newPermuta : permGuarConfMod) {
											insertadoConf = scsPermutaguardiasMapper.insertSelective(newPermuta);
										}
									}

									LOGGER.info(
											"sustituirGuardiaColeg() / sustituir letrado en permutaguardia confirmante");
								}
							}

							// OBTENER PERMUTAS DE CABECERA
							ScsPermutaCabeceraExample pce = new ScsPermutaCabeceraExample();
							pce.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(Integer.parseInt(idTurno))
									.andIdguardiaEqualTo(Integer.parseInt(idGuardia))
									.andIdpersonaEqualTo(Long.parseLong(idPersona));

							List<ScsPermutaCabecera> pc = scsPermutaCabeceraMapper.selectByExample(pce);
							List<ScsPermutaCabecera> pcMod = new ArrayList<ScsPermutaCabecera>();

							if (!pc.isEmpty()) {
								for (ScsPermutaCabecera permutaCabecera : pc) {

									permutaCabecera.setIdpersona(Long.parseLong(newLetrado));
									pcMod.add(permutaCabecera);

									ScsPermutaCabeceraKey key = new ScsPermutaCabeceraKey();

									key.setIdinstitucion(permutaCabecera.getIdinstitucion());
									key.setIdPermutaCabecera(permutaCabecera.getIdPermutaCabecera());

									int borra = scsPermutaCabeceraMapper.deleteByPrimaryKey(key);
									if (borra == 1) {
										if (insertadoSol != 0 || insertadoConf != 0) {
											for (ScsPermutaCabecera newPc : pcMod) {
												insertadoPc = scsPermutaCabeceraMapper.insertSelective(newPc);
											}
										}

									}

									LOGGER.info(
											"sustituirGuardiaColeg() / sustituir letrado en permutaguardia confirmante");
								}
							}

							if (insertadoPc != 0) {
								respCabGuar = scsCabeceraguardiasExtendsMapper.sustituirLetrado(institucion, idTurno,
										idGuardia, fechadesde, Long.parseLong(idPersona), Long.parseLong(newLetrado),
										fechaSustitucion, comensustitucion);
							}

						} else {
							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia solicitante");

							// OBTENER PERMUTAS DE GUARDIA
							ScsPermutaguardiasExample pgeSol = new ScsPermutaguardiasExample();
							pgeSol.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdguardiaSolicitanteEqualTo(Integer.parseInt(idGuardia))
									.andIdturnoSolicitanteEqualTo(Integer.parseInt(idTurno))
									.andIdpersonaSolicitanteEqualTo(Long.parseLong(idPersona));
							List<ScsPermutaguardias> permGuarSol = scsPermutaguardiasMapper.selectByExample(pgeSol);
							List<ScsPermutaguardias> permGuarSolMod = new ArrayList<ScsPermutaguardias>();
							if (!permGuarSol.isEmpty()) {
								for (ScsPermutaguardias permutaSol : permGuarSol) {

									permutaSol.setIdpersonaSolicitante(Long.parseLong(newLetrado));
									permGuarSolMod.add(permutaSol);

									ScsPermutaguardiasKey key = new ScsPermutaguardiasKey();

									key.setIdinstitucion(permutaSol.getIdinstitucion());
									key.setNumero(permutaSol.getNumero());

									int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);

									if (borra == 1) {
										for (ScsPermutaguardias newPermuta : permGuarSolMod) {
											insertadoSol = scsPermutaguardiasMapper.insertSelective(newPermuta);
										}
									}

									LOGGER.info(
											"sustituirGuardiaColeg() / sustituir letrado en permutaguardia solicitante");
								}
							}

							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia confirmante");

							ScsPermutaguardiasExample pgeConf = new ScsPermutaguardiasExample();
							pgeConf.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdguardiaConfirmadorEqualTo(Integer.parseInt(idGuardia))
									.andIdturnoConfirmadorEqualTo(Integer.parseInt(idTurno))
									.andIdpersonaConfirmadorEqualTo(Long.parseLong(idPersona));
							List<ScsPermutaguardias> permGuarConf = scsPermutaguardiasMapper.selectByExample(pgeSol);
							List<ScsPermutaguardias> permGuarConfMod = new ArrayList<ScsPermutaguardias>();
							if (!permGuarConf.isEmpty()) {
								for (ScsPermutaguardias permutaConf : permGuarConf) {

									permutaConf.setIdpersonaSolicitante(Long.parseLong(newLetrado));
									permGuarConfMod.add(permutaConf);

									ScsPermutaguardiasKey key = new ScsPermutaguardiasKey();

									key.setIdinstitucion(permutaConf.getIdinstitucion());
									key.setNumero(permutaConf.getNumero());

									int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);

									if (borra == 1) {
										for (ScsPermutaguardias newPermuta : permGuarConfMod) {
											insertadoConf = scsPermutaguardiasMapper.insertSelective(newPermuta);
										}
									}

									LOGGER.info(
											"sustituirGuardiaColeg() / sustituir letrado en permutaguardia confirmante");
								}
							}

							// OBTENER PERMUTAS DE CABECERA
							ScsPermutaCabeceraExample pce = new ScsPermutaCabeceraExample();
							pce.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(Integer.parseInt(idTurno))
									.andIdguardiaEqualTo(Integer.parseInt(idGuardia))
									.andIdpersonaEqualTo(Long.parseLong(idPersona));

							List<ScsPermutaCabecera> pc = scsPermutaCabeceraMapper.selectByExample(pce);
							List<ScsPermutaCabecera> pcMod = new ArrayList<ScsPermutaCabecera>();

							if (!pc.isEmpty()) {
								for (ScsPermutaCabecera permutaCabecera : pc) {

									permutaCabecera.setIdpersona(Long.parseLong(newLetrado));
									pcMod.add(permutaCabecera);

									ScsPermutaCabeceraKey key = new ScsPermutaCabeceraKey();

									key.setIdinstitucion(permutaCabecera.getIdinstitucion());
									key.setIdPermutaCabecera(permutaCabecera.getIdPermutaCabecera());

									int borra = scsPermutaCabeceraMapper.deleteByPrimaryKey(key);
									if (borra == 1) {
										if (insertadoSol != 0 || insertadoConf != 0) {
											for (ScsPermutaCabecera newPc : pcMod) {
												insertadoPc = scsPermutaCabeceraMapper.insertSelective(newPc);
											}
										}

									}

									LOGGER.info(
											"sustituirGuardiaColeg() / sustituir letrado en permutaguardia confirmante");
								}
							}

							if (insertadoPc != 0) {
								respCabGuar = scsCabeceraguardiasExtendsMapper.sustituirLetrado(institucion, idTurno,
										idGuardia, fechadesde, Long.parseLong(idPersona), Long.parseLong(newLetrado),
										fechaSustitucion, comensustitucion);
							}
							if (respCabGuar == 1) {

								switch (saltoOcompensacion) {
								case "S":

									scgi.setIdPersona(idPersona);
									scgi.setIdGuardia(idGuardia);
									scgi.setIdTurno(idTurno);
									scgi.setFecha(fechaHoy.toString());
									scgi.setSaltoCompensacion(saltoOcompensacion);
									scgi.setMotivo("Sustitución");

									nuevoId = scsSaltoscompensacionesExtendsMapper
											.selectNuevoIdSaltosCompensaciones(scgi, idInstitucion.toString());

									respSaltoComp = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(
											scgi, idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);

									break;

								case "C":

									scgi.setIdPersona(idPersona);
									scgi.setIdGuardia(idGuardia);
									scgi.setIdTurno(idTurno);
									scgi.setFecha(fechaHoy.toString());
									scgi.setSaltoCompensacion(saltoOcompensacion);
									scgi.setMotivo("Sustitución");

									nuevoId = scsSaltoscompensacionesExtendsMapper
											.selectNuevoIdSaltosCompensaciones(scgi, idInstitucion.toString());

									respSaltoComp = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(
											scgi, idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);

									break;
								case "S/C":

									String salto = saltoOcompensacion.split("/")[0];
									String comp = saltoOcompensacion.split("/")[1];

									scgi.setIdPersona(idPersona);
									scgi.setIdGuardia(idGuardia);
									scgi.setIdTurno(idTurno);
									scgi.setFecha(fechaHoy.toString());
									scgi.setSaltoCompensacion(salto);
									scgi.setMotivo("Sustitución");

									nuevoId = scsSaltoscompensacionesExtendsMapper
											.selectNuevoIdSaltosCompensaciones(scgi, idInstitucion.toString());

									int respSaltoComp1 = scsSaltoscompensacionesExtendsMapper
											.guardarSaltosCompensaciones(scgi, idInstitucion.toString(),
													Long.toString(nuevoId.getIdMax()), usuario);
									if (respSaltoComp1 != 0) {
										scgi = new SaltoCompGuardiaItem();
										scgi.setIdPersona(idPersona);
										scgi.setIdGuardia(idGuardia);
										scgi.setIdTurno(idTurno);
										scgi.setFecha(fechaHoy.toString());
										scgi.setSaltoCompensacion(comp);
										scgi.setMotivo("Sustitución");

										nuevoId = scsSaltoscompensacionesExtendsMapper
												.selectNuevoIdSaltosCompensaciones(scgi, idInstitucion.toString());

										int respSaltoComp2 = scsSaltoscompensacionesExtendsMapper
												.guardarSaltosCompensaciones(scgi, idInstitucion.toString(),
														Long.toString(nuevoId.getIdMax()), usuario);

										if (respSaltoComp2 != 0) {
											error.setCode(200);
											updateResponseDTO.setStatus(SigaConstants.OK);
										} else {
											error.setCode(400);
											updateResponseDTO.setStatus(SigaConstants.KO);
										}
									}

									break;

								default:
									break;
								}
							}

						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (respCabGuar == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {

			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("sustituirGuardiaColeg() -> Salida del servicio para sustituir letrados");

		return updateResponseDTO;
	}

	@Override
	public String getIdConjuntoGuardia(String idGuardia, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> idConjuntoGuardias = new ArrayList<String>();
		String idConjuntoGuar = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getFechasProgramacionGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getFechasProgramacionGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getFechasProgramacionGuardia() -> Entrada para obtener los datos del calendario");

				idConjuntoGuardias = scsGuardiasturnoExtendsMapper.getConjuntoGuardiasIdFromGuardiaId(idGuardia,
						idInstitucion.toString());
				if (!idConjuntoGuardias.isEmpty() && idConjuntoGuardias != null) {
					idConjuntoGuar = idConjuntoGuardias.toString();

				}

				LOGGER.info("getCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		return idConjuntoGuar;
	}

	@Override
	public PermutaDTO getPemutasColeg(PermutaItem permutaItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PermutaDTO permutaDTO = new PermutaDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getGuardiaColeg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getGuardiaColeg() -> Entrada para obtener la informacion del turno");

				PermutaItem permItem = new PermutaItem();

				permItem.setIdguardia(permutaItem.getIdguardia());
				permItem.setIdpersona(permutaItem.getIdpersona());

				List<PermutaItem> permutas = scsPermutaguardiasExtendsMapper.getPermutaColeg(permItem, idInstitucion);

				permutaDTO.setPermutaItems(permutas);

				LOGGER.info("getGuardiaColeg() -> Salida ya con la informacion recogida");
			}
		}
		return permutaDTO;
	}

	@Override
	public ComboDTO getTurnoInscrito(String idPersona, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTurnoInscrito() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getTurnoInscrito() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getTurnoInscrito() / scsTurnosextendsMapper.getResoluciones() -> Entrada a scsImpugnacionEjgextendsMapper para obtener los combo");

				comboItems = scsPermutaguardiasExtendsMapper.getTurnoInscrito(Long.parseLong(idPersona), idInstitucion);

				LOGGER.info(
						"getTurnoInscrito() / scsTurnosextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTurnosTipo() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO getGuardiaDestinoInscrito(String idTurno, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTurnoInscrito() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getTurnoInscrito() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getTurnoInscrito() / scsTurnosextendsMapper.getResoluciones() -> Entrada a scsImpugnacionEjgextendsMapper para obtener los combo");

				comboItems = scsPermutaguardiasExtendsMapper.getGuardiaDestinoInscrito(idTurno, idInstitucion);

				LOGGER.info(
						"getTurnoInscrito() / scsTurnosextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTurnosTipo() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO validarPermuta(List<PermutaItem> permutas, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int responseUpdate = 0;
		int calendarioSoli;
		Date fechaInicioSoli;
		int calendarioConf;
		Date fechaInicioConf;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"validarPermuta() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"validarPermuta() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {
					int numPermutas = 0;
					// si la permuta esta sin validar suma 1 a la variable numPermutas
					for (PermutaItem p : permutas) {
						if (p.getFechaconfirmacion() != null) {
							numPermutas++;
						}

					}

					// si el tamaño de la lista es igual a numPermutas quiere decir que estan todas
					// sin validar y se puede proceder a la validacion de la permuta.
					// De no ser asi daria un error y no se valida ninguna.
					if (permutas.size() == numPermutas) {
						for (PermutaItem perm : permutas) {

							// se almacenan la fecha y motivos del confirmador en scs_permutaguardias
							ScsPermutaguardias permutaGuardia = new ScsPermutaguardias();
							permutaGuardia.setNumero(perm.getNumero());
							permutaGuardia.setIdinstitucion(idInstitucion);
							permutaGuardia.setFechaconfirmacion(new Date());
							permutaGuardia.setMotivosconfirmador(perm.getMotivosconfirmador());
							int response1 = scsPermutaguardiasExtendsMapper.updateByPrimaryKeySelective(permutaGuardia);

							// creacion de copias para los registros de SCS_PERMUTA_CABECERA para
							// intercambiar fecha de inicio e idcalendarioguardias
							ScsPermutaCabeceraKey permCabSol = new ScsPermutaCabeceraKey();
							permCabSol.setIdinstitucion(idInstitucion);
							permCabSol.setIdPermutaCabecera(perm.getIdPerCabSolicitante());
							ScsPermutaCabecera permCabSolCopy = scsPermutaCabeceraMapper.selectByPrimaryKey(permCabSol);

							ScsPermutaCabeceraKey permCabConf = new ScsPermutaCabeceraKey();
							permCabConf.setIdinstitucion(idInstitucion);
							permCabConf.setIdPermutaCabecera(perm.getIdPerCabConfirmador());
							ScsPermutaCabecera permCabConfCopy = scsPermutaCabeceraMapper
									.selectByPrimaryKey(permCabConf);

							// creacion de copias para los registros de SCS_CABECERAGUARDIAS y
							// SCS_GUARDIASCOLEGIADO para intercambiar fecha de inicio e
							// idcalendarioguardias

							// Copia de las guardias del solicitante
							ScsCabeceraguardiasKey cabGuardiaSol = new ScsCabeceraguardiasKey();
							cabGuardiaSol.setIdinstitucion(idInstitucion);
							cabGuardiaSol.setIdguardia(perm.getIdguardiaSolicitante());
							cabGuardiaSol.setIdturno(perm.getIdturnoSolicitante());
							cabGuardiaSol.setIdpersona(perm.getIdpersonaSolicitante());
							cabGuardiaSol.setFechainicio(perm.getFechainicioSolicitante());

							ScsCabeceraguardias cabGuardiaSolCopy = scsCabeceraguardiasExtendsMapper
									.selectByPrimaryKey(cabGuardiaSol);

							ScsGuardiascolegiadoKey guardiaSol = new ScsGuardiascolegiado();
							guardiaSol.setIdinstitucion(idInstitucion);
							guardiaSol.setIdguardia(cabGuardiaSolCopy.getIdguardia());
							guardiaSol.setIdturno(cabGuardiaSolCopy.getIdturno());
							guardiaSol.setIdpersona(cabGuardiaSolCopy.getIdpersona());
							guardiaSol.setFechainicio(cabGuardiaSolCopy.getFechainicio());
							guardiaSol.setFechafin(cabGuardiaSolCopy.getFechaFin());

							ScsGuardiascolegiado guardiaSolCopy = scsGuardiascolegiadoExtendsMapper
									.selectByPrimaryKey(guardiaSol);

							// copia de las guardias del confirmador
							ScsCabeceraguardiasKey cabGuardiaConf = new ScsCabeceraguardiasKey();
							cabGuardiaConf.setIdinstitucion(idInstitucion);
							cabGuardiaConf.setIdguardia(perm.getIdguardiaConfirmador());
							cabGuardiaConf.setIdturno(perm.getIdturnoConfirmador());
							cabGuardiaConf.setIdpersona(perm.getIdpersonaConfirmador());
							cabGuardiaConf.setFechainicio(perm.getFechainicioConfirmador());

							ScsCabeceraguardias cabGuardiaConfCopy = scsCabeceraguardiasExtendsMapper
									.selectByPrimaryKey(cabGuardiaConf);

							ScsGuardiascolegiadoKey guardiaConf = new ScsGuardiascolegiado();
							guardiaConf.setIdinstitucion(idInstitucion);
							guardiaConf.setIdguardia(cabGuardiaConfCopy.getIdguardia());
							guardiaConf.setIdturno(cabGuardiaConfCopy.getIdturno());
							guardiaConf.setIdpersona(cabGuardiaConfCopy.getIdpersona());
							guardiaConf.setFechainicio(cabGuardiaConfCopy.getFechainicio());
							guardiaConf.setFechafin(cabGuardiaConfCopy.getFechaFin());

							ScsGuardiascolegiado guardiaConfCopy = scsGuardiascolegiadoExtendsMapper
									.selectByPrimaryKey(guardiaConf);

							/*
							 * Debido a que se tiene que eliminar los registros de forma secuencial siempre
							 * y cuando se haya eliminado el anterior se realiza comprobado que la
							 * eliminacion anterior se realiza antes de realizar la siguiente
							 */
							if (response1 != 0) {
								// eliminacion de registros de SCS_GuardiasColegiado
								int delGuardiaSol = scsGuardiascolegiadoExtendsMapper
										.deleteByPrimaryKey(guardiaSolCopy);
								if (delGuardiaSol != 0) {
									int delGuardiaConf = scsGuardiascolegiadoExtendsMapper
											.deleteByPrimaryKey(guardiaConfCopy);
									if (delGuardiaConf != 0) {
										int delCabGuarSol = scsCabeceraguardiasExtendsMapper
												.deleteByPrimaryKey(cabGuardiaSolCopy);
										if (delCabGuarSol != 0) {
											int delCabGuarConf = scsCabeceraguardiasExtendsMapper
													.deleteByPrimaryKey(cabGuardiaConfCopy);
											if (delCabGuarConf != 0) {
												int delPermCabSol = scsPermutaCabeceraMapper
														.deleteByPrimaryKey(permCabSolCopy);
												if (delPermCabSol != 0) {
													int delPermCabConf = scsPermutaCabeceraMapper
															.deleteByPrimaryKey(permCabConfCopy);

													// si todos los registros han sido eliminados se intercambian los
													// valores de idcalendario y fecha y se vuelve a insertar.
													if (delPermCabConf != 0) {
														fechaInicioConf = guardiaConfCopy.getFechainicio();
														fechaInicioSoli = guardiaSolCopy.getFechainicio();
														guardiaSolCopy.setFechainicio(fechaInicioConf);
														int insertGuardiaSol = scsGuardiascolegiadoExtendsMapper
																.insert(guardiaSolCopy);

														if (insertGuardiaSol != 0) {
															guardiaConfCopy.setFechainicio(fechaInicioSoli);
															int insertGuardiaConf = scsGuardiascolegiadoExtendsMapper
																	.insert(guardiaConfCopy);

															if (insertGuardiaConf != 0) {

																fechaInicioConf = cabGuardiaConfCopy.getFechainicio();
																fechaInicioSoli = cabGuardiaSolCopy.getFechainicio();
																calendarioConf = cabGuardiaConfCopy
																		.getIdcalendarioguardias();
																calendarioSoli = cabGuardiaSolCopy
																		.getIdcalendarioguardias();

																cabGuardiaSolCopy.setFechainicio(fechaInicioConf);
																cabGuardiaSolCopy
																		.setIdcalendarioguardias(calendarioConf);
																int insertCabGuarSol = scsCabeceraguardiasExtendsMapper
																		.insertSelective(cabGuardiaSolCopy);

																if (insertCabGuarSol != 0) {
																	cabGuardiaConfCopy.setFechainicio(fechaInicioSoli);
																	cabGuardiaConfCopy
																			.setIdcalendarioguardias(calendarioSoli);
																	int insertCabGuarConf = scsCabeceraguardiasExtendsMapper
																			.insertSelective(cabGuardiaConfCopy);

																	if (insertCabGuarConf != 0) {

																		fechaInicioConf = permCabConfCopy.getFecha();
																		fechaInicioSoli = permCabSolCopy.getFecha();
																		calendarioConf = permCabConfCopy
																				.getIdcalendarioguardias();
																		calendarioSoli = permCabSolCopy
																				.getIdcalendarioguardias();

																		permCabSolCopy.setFecha(fechaInicioConf);
																		permCabSolCopy.setIdcalendarioguardias(
																				calendarioConf);
																		int insertPerSol = scsPermutaCabeceraMapper
																				.insertSelective(permCabSolCopy);

																		if (insertPerSol != 0) {
																			permCabConfCopy.setFecha(fechaInicioSoli);
																			permCabConfCopy.setIdcalendarioguardias(
																					calendarioSoli);
																			responseUpdate = scsPermutaCabeceraMapper
																					.insertSelective(permCabConfCopy);
																			//si el ultimo registro de todas las inserciones se lleva a cabo de forma satisfactoria
																			//querra decir que la validacion ha sido correcta
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

							}

						}
					} else {
						error.setCode(400);
						error.setDescription("Existen permutas ya validadas en la seleccion.");
						updateResponseDTO.setStatus(SigaConstants.KO);
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					LOGGER.info("permutarGuardia() / -> Error en la base de datos." + error.getDescription());
					updateResponseDTO.setStatus(SigaConstants.KO);
				} finally {

					if (responseUpdate != 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info("permutarGuardia() / -> Exito en el proceso de insercion");

					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.info("permutarGuardia() / -> Fallo en el proceso de insercion");
					}
				}

			}
		}

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO permutarGuardia(PermutaItem permutaItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int responseSol;
		int responseConf;
		int responsePerm = 0;
		Long idPermcabSol;
		Long idPermcabConf;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"permutarGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"permutarGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {
					LOGGER.info(
							"permutarGuardia() / Entrada en la recopilacion de datos necesarios para realizar la insercion del registro");
					// primero se crean los 2 registros necesarios en SCS_permuta_cabecera para las
					// dos guardias involucradas en la permuta.

					// CREACION DEL 1º REGISTRO EN SCS_PERMUTA_CABECERA

					// obtener los datos de SCS_CABECERAGUARDIAS para poder rellenar los campos de
					// idcalendario y fecha para el solicitante

					ScsCabeceraguardiasExample getDatosSolicitante = new ScsCabeceraguardiasExample();
					getDatosSolicitante.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(permutaItem.getIdturnoSolicitante())
							.andIdguardiaEqualTo(permutaItem.getIdguardiaSolicitante())
							.andIdpersonaEqualTo(permutaItem.getIdpersonaSolicitante())
							.andFechainicioEqualTo(permutaItem.getFechainicioSolicitante());

					List<ScsCabeceraguardias> guardiaSolicitante = scsCabeceraguardiasExtendsMapper
							.selectByExample(getDatosSolicitante);
					LOGGER.info(
							"permutarGuardia() / scsCabeceraguardiasExtendsMapper.selectByExample() -> Salida de scsCabeceraguardiasExtendsMapper para obtener la informacion de la guardia del solicitante");

					// obtener el ultimo id_permuta_cabecera para el solicitante
					ScsPermutaCabeceraExample permutaCabeceraExample = new ScsPermutaCabeceraExample();
					permutaCabeceraExample.setOrderByClause("ID_PERMUTA_CABECERA DESC");
					permutaCabeceraExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

					List<ScsPermutaCabecera> listIdPerCab = scsPermutaCabeceraMapper
							.selectByExample(permutaCabeceraExample);

					if (!listIdPerCab.isEmpty()) {
						idPermcabSol = listIdPerCab.get(0).getIdPermutaCabecera() + 1L;
					} else {
						idPermcabSol = 1L;
					}
					LOGGER.info(
							"permutarGuardia() / scsPermutaCabeceraMapper.selectByExample() -> Salida de scsPermutaCabeceraMapper para obtener el ultimo id_permuta_cabecera para el solicitante");

					// registro para guardia solicitante
					ScsPermutaCabecera permutaSolicitante = new ScsPermutaCabecera();

					permutaSolicitante.setIdPermutaCabecera(idPermcabSol);
					permutaSolicitante.setIdinstitucion(idInstitucion);
					permutaSolicitante.setIdturno(permutaItem.getIdturnoSolicitante());
					permutaSolicitante.setIdguardia(permutaItem.getIdguardiaSolicitante());
					permutaSolicitante.setIdpersona(permutaItem.getIdpersonaSolicitante());
					permutaSolicitante.setFecha(permutaItem.getFechainicioSolicitante());
					permutaSolicitante.setIdcalendarioguardias(guardiaSolicitante.get(0).getIdcalendarioguardias());
					permutaSolicitante.setUsumodificacion(usuarios.get(0).getIdusuario());
					permutaSolicitante.setFechamodificacion(new Date());

					responseSol = scsPermutaCabeceraMapper.insertSelective(permutaSolicitante);
					LOGGER.info(
							"permutarGuardia() / scsPermutaCabeceraMapper.insertSelective() -> insertado el registro en SCS_PERMUTA_CABECERA para el solicitante");
					if (responseSol != 0) {
						// CREACION DEL 2º REGISTRO EN SCS_PERMUTA_CABECERA

						// obtener los datos de SCS_CABECERAGUARDIAS para poder rellenar los campos de
						// idcalendario y fecha para el confirmador

						ScsCabeceraguardiasExample getDatosConfirmador = new ScsCabeceraguardiasExample();
						getDatosConfirmador.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(permutaItem.getIdturnoConfirmador())
								.andIdguardiaEqualTo(permutaItem.getIdguardiaConfirmador())
								.andIdpersonaEqualTo(permutaItem.getIdpersonaConfirmador())
								.andFechainicioEqualTo(permutaItem.getFechainicioConfirmador());

						List<ScsCabeceraguardias> guardiaConfirmador = scsCabeceraguardiasExtendsMapper
								.selectByExample(getDatosConfirmador);
						LOGGER.info(
								"permutarGuardia() / scsCabeceraguardiasExtendsMapper.selectByExample() -> Salida de scsCabeceraguardiasExtendsMapper para obtener la informacion de la guardia del confirmador");

						// obtener el ultimo id_permuta_cabecera para el confirmador
						ScsPermutaCabeceraExample permutaCabeceraConfExample = new ScsPermutaCabeceraExample();
						permutaCabeceraConfExample.setOrderByClause("ID_PERMUTA_CABECERA DESC");
						permutaCabeceraConfExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

						List<ScsPermutaCabecera> listIdPerCabConf = scsPermutaCabeceraMapper
								.selectByExample(permutaCabeceraExample);

						if (!listIdPerCabConf.isEmpty()) {
							idPermcabConf = listIdPerCabConf.get(0).getIdPermutaCabecera() + 1L;
						} else {
							idPermcabConf = 1L;
						}

						LOGGER.info(
								"permutarGuardia() / scsPermutaCabeceraMapper.selectByExample() -> Salida de scsPermutaCabeceraMapper para obtener el ultimo id_permuta_cabecera para el confirmador");

						// registro para guardia confirmador
						ScsPermutaCabecera permutaConfirmador = new ScsPermutaCabecera();

						permutaConfirmador.setIdPermutaCabecera(idPermcabConf);
						permutaConfirmador.setIdinstitucion(idInstitucion);
						permutaConfirmador.setIdturno(permutaItem.getIdturnoConfirmador());
						permutaConfirmador.setIdguardia(permutaItem.getIdguardiaConfirmador());
						permutaConfirmador.setIdpersona(permutaItem.getIdpersonaConfirmador());
						permutaConfirmador.setFecha(permutaItem.getFechainicioConfirmador());
						permutaConfirmador.setIdcalendarioguardias(guardiaConfirmador.get(0).getIdcalendarioguardias());
						permutaConfirmador.setUsumodificacion(usuarios.get(0).getIdusuario());
						permutaConfirmador.setFechamodificacion(new Date());

						responseConf = scsPermutaCabeceraMapper.insertSelective(permutaConfirmador);
						LOGGER.info(
								"permutarGuardia() / scsPermutaCabeceraMapper.insertSelective() -> insertado el registro en SCS_PERMUTA_CABECERA para el confirmador");
						if (responseConf != 0) {

							// obtener el ultimo numero para scs_permutaguardias
							ScsPermutaguardiasExample permutaGuardiaExample = new ScsPermutaguardiasExample();
							permutaGuardiaExample.setOrderByClause("NUMERO DESC");
							permutaGuardiaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

							List<ScsPermutaguardias> listNumero = scsPermutaguardiasExtendsMapper
									.selectByExample(permutaGuardiaExample);
							Long numero;
							if (!listNumero.isEmpty()) {
								numero = listNumero.get(0).getNumero() + 1L;
							} else {
								numero = 1L;
							}
							LOGGER.info(
									"permutarGuardia() / scsPermutaguardiasExtendsMapper.selectByExample() -> Obtener el ultimo NUMERO de la tabla SCS_PERMUTAGUARDIAS");

							ScsPermutaguardias permutaGuardia = new ScsPermutaguardias();
							permutaGuardia.setIdinstitucion(idInstitucion);
							permutaGuardia.setNumero(numero);
							permutaGuardia.setFechasolicitud(new Date());
							permutaGuardia.setAnulada(0);
							permutaGuardia.setFechamodificacion(new Date());
							permutaGuardia.setUsumodificacion(usuarios.get(0).getIdusuario());

							// solicitante
							permutaGuardia.setIdpersonaSolicitante(permutaSolicitante.getIdpersona());
							permutaGuardia.setIdturnoSolicitante(permutaSolicitante.getIdturno());
							permutaGuardia.setIdguardiaSolicitante(permutaSolicitante.getIdguardia());
							permutaGuardia
									.setIdcalendarioguardiasSolicitan(permutaSolicitante.getIdcalendarioguardias());
							permutaGuardia.setFechainicioSolicitante(permutaSolicitante.getFecha());
							permutaGuardia.setMotivossolicitante(permutaItem.getMotivossolicitante());
							permutaGuardia.setIdPerCabSolicitante(permutaSolicitante.getIdPermutaCabecera());

							// confirmador
							permutaGuardia.setIdpersonaConfirmador(permutaConfirmador.getIdpersona());
							permutaGuardia.setIdturnoConfirmador(permutaConfirmador.getIdturno());
							permutaGuardia.setIdguardiaConfirmador(permutaConfirmador.getIdguardia());
							permutaGuardia
									.setIdcalendarioguardiasConfirmad(permutaConfirmador.getIdcalendarioguardias());
							permutaGuardia.setFechainicioConfirmador(permutaConfirmador.getFecha());
							permutaGuardia.setIdPerCabConfirmador(permutaConfirmador.getIdPermutaCabecera());

							responsePerm = scsPermutaguardiasExtendsMapper.insertSelective(permutaGuardia);
							LOGGER.info(
									"permutarGuardia() / scsPermutaguardiasExtendsMapper.insertSelective() -> insertado el registro en SCS_PERMUTAGUARDIAS con los datos del solicitante y el confirmador");

						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					LOGGER.info("permutarGuardia() / -> Error en la base de datos." + error.getDescription());
					insertResponseDTO.setStatus(SigaConstants.KO);
				} finally {

					if (responsePerm != 0) {
						insertResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info("permutarGuardia() / -> Exito en el proceso de insercion");

					} else {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.info("permutarGuardia() / -> Fallo en el proceso de insercion");
					}
				}

			}
		}
		LOGGER.info("comboTurnosTipo() -> Salida del servicio para obtener los tipos ejg");
		return insertResponseDTO;
	}

}
