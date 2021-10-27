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
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasDTO;
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasItem;
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
import org.itcgae.siga.db.services.scs.mappers.ScsPermutaCabeceraExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPermutaguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSaltoscompensacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonapartidoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.guardia.GuardiasColegiadoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private ScsPermutaCabeceraExtendsMapper scsPermutaCabeceraExtendsMapper;

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
	@Transactional
	public UpdateResponseDTO sustituirGuardiaColeg(String[] datos, HttpServletRequest request) throws Exception {
		LOGGER.info("sustituirGuardiaColeg() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		
		Long idPermutaCabecera;
		SaltoCompGuardiaItem scgi = new SaltoCompGuardiaItem();
		MaxIdDto nuevoId;
		int	respCabGuar =0;
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

				

					Long fechaHoy = new Date().getTime();

					String institucion = idInstitucion.toString();
					String idTurno = datos[0].toString();
					String idGuardia = datos[1].toString();
					String fechadesde = datos[2].toString();
					String idPersona = datos[3].toString();
					String newLetrado = datos[4].toString();
					String fechaSustitucion = datos[5].toString();
					String comensustitucion = datos[6].toString();
					String saltoOcompensacion = datos[7].toString();
					String calendarioGuardias = datos[8].toString();

					if (scsCabeceraguardiasExtendsMapper.tieneGuardia(institucion, Long.parseLong(newLetrado)) == 0) {

						if (saltoOcompensacion.equalsIgnoreCase("N")) {
							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia solicitante");

						
							ScsPermutaguardiasExample permutaGuardiaSolicitante = new ScsPermutaguardiasExample();
							permutaGuardiaSolicitante.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaSolicitanteEqualTo(Long.parseLong(idPersona))
							.andIdguardiaSolicitanteEqualTo(Integer.parseInt(idGuardia))
							.andIdturnoSolicitanteEqualTo(Integer.parseInt(idTurno))
							.andFechainicioSolicitanteEqualTo(new Date(Long.parseLong(fechadesde)))
							.andIdcalendarioguardiasSolicitanEqualTo(Integer.parseInt(calendarioGuardias));
				
							
							List<ScsPermutaguardias> listPerGuarSol = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaSolicitante);
							if(!listPerGuarSol.isEmpty()) {
								for(ScsPermutaguardias item:listPerGuarSol) {
									
									ScsPermutaCabeceraKey keypermutaCabSol = new ScsPermutaCabeceraKey();
									keypermutaCabSol.setIdinstitucion(idInstitucion);
									keypermutaCabSol.setIdPermutaCabecera(item.getIdPerCabSolicitante());
									
									ScsPermutaCabecera permutaCabsol = scsPermutaCabeceraExtendsMapper.selectByPrimaryKey(keypermutaCabSol);
									
									ScsCabeceraguardiasKey keycabeceraGuardiaSol = new ScsCabeceraguardiasKey();
									keycabeceraGuardiaSol.setIdinstitucion(idInstitucion);
									keycabeceraGuardiaSol.setIdguardia(permutaCabsol.getIdguardia());
									keycabeceraGuardiaSol.setIdturno(permutaCabsol.getIdturno());
									keycabeceraGuardiaSol.setIdpersona(permutaCabsol.getIdpersona());
									keycabeceraGuardiaSol.setFechainicio(permutaCabsol.getFecha());
									
									ScsCabeceraguardias cabeceraGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(keycabeceraGuardiaSol);
									
									int delPerGuar = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(item);
									
									if(delPerGuar !=0) {
										int delPerCab = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(keypermutaCabSol);
										if(delPerCab != 0) {
											int delcabGuar = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(keycabeceraGuardiaSol);
											
											//una vez eliminadas las pemutas se inserta los registros con el colegiado cambiado
											if(delcabGuar != 0) {
												cabeceraGuardia.setIdpersona(Long.parseLong(newLetrado));
												cabeceraGuardia.setComensustitucion(comensustitucion);
												cabeceraGuardia.setSustituto("1");
												cabeceraGuardia.setLetradosustituido(Long.parseLong(idPersona));
												cabeceraGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
												cabeceraGuardia.setFechamodificacion(new Date());
												cabeceraGuardia.setUsumodificacion(usuario.getIdusuario());
												int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabeceraGuardia);
												if(insertcabGuar != 0) {
													permutaCabsol.setIdpersona(Long.parseLong(newLetrado));
													int insertPerCabSol = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabsol);
													if(insertPerCabSol != 0) {
														item.setIdpersonaSolicitante(Long.parseLong(newLetrado));
														respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(item);
													}
												}
											}
										}
									}
									
							}
							}
							
							ScsPermutaguardiasExample permutaGuardiaConfirmador = new ScsPermutaguardiasExample();
							permutaGuardiaConfirmador.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaConfirmadorEqualTo(Long.parseLong(idPersona))
							.andIdguardiaConfirmadorEqualTo(Integer.parseInt(idGuardia))
							.andIdturnoConfirmadorEqualTo(Integer.parseInt(idTurno))
							.andFechainicioConfirmadorEqualTo(new Date(Long.parseLong(fechadesde)))
							.andIdcalendarioguardiasConfirmadEqualTo(Integer.parseInt(calendarioGuardias));
				
							
							List<ScsPermutaguardias> listPerGuarConf = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaConfirmador);
							if(!listPerGuarConf.isEmpty()) {
								for(ScsPermutaguardias item:listPerGuarConf) {
									
									ScsPermutaCabeceraKey keypermutaCabConf = new ScsPermutaCabeceraKey();
									keypermutaCabConf.setIdinstitucion(idInstitucion);
									keypermutaCabConf.setIdPermutaCabecera(item.getIdPerCabSolicitante());
									
									ScsPermutaCabecera permutaCabsol = scsPermutaCabeceraExtendsMapper.selectByPrimaryKey(keypermutaCabConf);
									
									ScsCabeceraguardiasKey keycabeceraGuardiaConf = new ScsCabeceraguardiasKey();
									keycabeceraGuardiaConf.setIdinstitucion(idInstitucion);
									keycabeceraGuardiaConf.setIdguardia(permutaCabsol.getIdguardia());
									keycabeceraGuardiaConf.setIdturno(permutaCabsol.getIdturno());
									keycabeceraGuardiaConf.setIdpersona(permutaCabsol.getIdpersona());
									keycabeceraGuardiaConf.setFechainicio(permutaCabsol.getFecha());
									
									ScsCabeceraguardias cabeceraGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(keycabeceraGuardiaConf);
									
									int delPerGuar = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(item);
									
									if(delPerGuar !=0) {
										int delPerCab = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(keypermutaCabConf);
										if(delPerCab != 0) {
											int delcabGuar = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(keycabeceraGuardiaConf);
											
											//una vez eliminadas las pemutas se inserta los registros con el colegiado cambiado
											if(delcabGuar != 0) {
												cabeceraGuardia.setIdpersona(Long.parseLong(newLetrado));
												cabeceraGuardia.setComensustitucion(comensustitucion);
												cabeceraGuardia.setSustituto("1");
												cabeceraGuardia.setLetradosustituido(Long.parseLong(idPersona));
												cabeceraGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
												cabeceraGuardia.setFechamodificacion(new Date());
												cabeceraGuardia.setUsumodificacion(usuario.getIdusuario());
												int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabeceraGuardia);
												if(insertcabGuar != 0) {
													permutaCabsol.setIdpersona(Long.parseLong(newLetrado));
													int insertPerCabConf = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabsol);
													if(insertPerCabConf != 0) {
														item.setIdpersonaConfirmador(Long.parseLong(newLetrado));
														respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(item);
													}
												}
											}
										}
									}
								}
							}
							if(listPerGuarSol.isEmpty() && listPerGuarConf.isEmpty()) {
								ScsCabeceraguardiasKey cabGuardia = new ScsCabeceraguardiasKey();
								cabGuardia .setIdinstitucion(idInstitucion);
								cabGuardia .setIdguardia(Integer.parseInt(idGuardia));
								cabGuardia .setIdturno(Integer.parseInt(idTurno));
								cabGuardia .setIdpersona(Long.parseLong(idPersona));
								cabGuardia .setFechainicio(new Date(Long.parseLong(fechadesde)));
								
								ScsCabeceraguardias cabeceraGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabGuardia);
								
								int delcabGuar = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
								if(delcabGuar != 0) {
									cabeceraGuardia.setIdpersona(Long.parseLong(newLetrado));
									cabeceraGuardia.setComensustitucion(comensustitucion);
									cabeceraGuardia.setSustituto("1");
									cabeceraGuardia.setLetradosustituido(Long.parseLong(idPersona));
									cabeceraGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
									cabeceraGuardia.setFechamodificacion(new Date());
									cabeceraGuardia.setUsumodificacion(usuario.getIdusuario());
									respCabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabeceraGuardia);
								}
							
							}
							if (respCabGuar == 0) {
								error.setCode(400);
								updateResponseDTO.setStatus(SigaConstants.KO);
								throw (new Exception("Error al permutar la guardia"));
							} else {

								error.setCode(200);
								updateResponseDTO.setStatus(SigaConstants.OK);

							}
							
						} else {
							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia solicitante");

							
							ScsPermutaguardiasExample permutaGuardiaSolicitante = new ScsPermutaguardiasExample();
							permutaGuardiaSolicitante.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaSolicitanteEqualTo(Long.parseLong(idPersona))
							.andIdguardiaSolicitanteEqualTo(Integer.parseInt(idGuardia))
							.andIdturnoSolicitanteEqualTo(Integer.parseInt(idTurno))
							.andFechainicioSolicitanteEqualTo(new Date(Long.parseLong(fechadesde)))
							.andIdcalendarioguardiasSolicitanEqualTo(Integer.parseInt(calendarioGuardias));
				
							
							List<ScsPermutaguardias> listPerGuarSol = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaSolicitante);
							if(!listPerGuarSol.isEmpty()) {
								for(ScsPermutaguardias item:listPerGuarSol) {
									
									ScsPermutaCabeceraKey keypermutaCabSol = new ScsPermutaCabeceraKey();
									keypermutaCabSol.setIdinstitucion(idInstitucion);
									keypermutaCabSol.setIdPermutaCabecera(item.getIdPerCabSolicitante());
									
									ScsPermutaCabecera permutaCabsol = scsPermutaCabeceraExtendsMapper.selectByPrimaryKey(keypermutaCabSol);
									
									ScsCabeceraguardiasKey keycabeceraGuardiaSol = new ScsCabeceraguardiasKey();
									keycabeceraGuardiaSol.setIdinstitucion(idInstitucion);
									keycabeceraGuardiaSol.setIdguardia(permutaCabsol.getIdguardia());
									keycabeceraGuardiaSol.setIdturno(permutaCabsol.getIdturno());
									keycabeceraGuardiaSol.setIdpersona(permutaCabsol.getIdpersona());
									keycabeceraGuardiaSol.setFechainicio(permutaCabsol.getFecha());
									
									ScsCabeceraguardias cabeceraGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(keycabeceraGuardiaSol);
									
									int delPerGuar = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(item);
									
									if(delPerGuar !=0) {
										int delPerCab = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(keypermutaCabSol);
										if(delPerCab != 0) {
											int delcabGuar = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(keycabeceraGuardiaSol);
											
											//una vez eliminadas las pemutas se inserta los registros con el colegiado cambiado
											if(delcabGuar != 0) {
												cabeceraGuardia.setIdpersona(Long.parseLong(newLetrado));
												cabeceraGuardia.setComensustitucion(comensustitucion);
												cabeceraGuardia.setSustituto("1");
												cabeceraGuardia.setLetradosustituido(Long.parseLong(idPersona));
												cabeceraGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
												cabeceraGuardia.setFechamodificacion(new Date());
												cabeceraGuardia.setUsumodificacion(usuario.getIdusuario());
												int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabeceraGuardia);
												if(insertcabGuar != 0) {
													permutaCabsol.setIdpersona(Long.parseLong(newLetrado));
													int insertPerCabSol = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabsol);
													if(insertPerCabSol != 0) {
														item.setIdpersonaSolicitante(Long.parseLong(newLetrado));
														respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(item);
													}
												}
											}
										}
									}
									
							}
							}
							
							ScsPermutaguardiasExample permutaGuardiaConfirmador = new ScsPermutaguardiasExample();
							permutaGuardiaConfirmador.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaConfirmadorEqualTo(Long.parseLong(idPersona))
							.andIdguardiaConfirmadorEqualTo(Integer.parseInt(idGuardia))
							.andIdturnoConfirmadorEqualTo(Integer.parseInt(idTurno))
							.andFechainicioConfirmadorEqualTo(new Date(Long.parseLong(fechadesde)))
							.andIdcalendarioguardiasConfirmadEqualTo(Integer.parseInt(calendarioGuardias));
				
							
							List<ScsPermutaguardias> listPerGuarConf = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaConfirmador);
							if(!listPerGuarConf.isEmpty()) {
								for(ScsPermutaguardias item:listPerGuarConf) {
									
									ScsPermutaCabeceraKey keypermutaCabConf = new ScsPermutaCabeceraKey();
									keypermutaCabConf.setIdinstitucion(idInstitucion);
									keypermutaCabConf.setIdPermutaCabecera(item.getIdPerCabSolicitante());
									
									ScsPermutaCabecera permutaCabsol = scsPermutaCabeceraExtendsMapper.selectByPrimaryKey(keypermutaCabConf);
									
									ScsCabeceraguardiasKey keycabeceraGuardiaConf = new ScsCabeceraguardiasKey();
									keycabeceraGuardiaConf.setIdinstitucion(idInstitucion);
									keycabeceraGuardiaConf.setIdguardia(permutaCabsol.getIdguardia());
									keycabeceraGuardiaConf.setIdturno(permutaCabsol.getIdturno());
									keycabeceraGuardiaConf.setIdpersona(permutaCabsol.getIdpersona());
									keycabeceraGuardiaConf.setFechainicio(permutaCabsol.getFecha());
									
									ScsCabeceraguardias cabeceraGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(keycabeceraGuardiaConf);
									
									int delPerGuar = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(item);
									
									if(delPerGuar !=0) {
										int delPerCab = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(keypermutaCabConf);
										if(delPerCab != 0) {
											int delcabGuar = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(keycabeceraGuardiaConf);
											
											//una vez eliminadas las pemutas se inserta los registros con el colegiado cambiado
											if(delcabGuar != 0) {
												cabeceraGuardia.setIdpersona(Long.parseLong(newLetrado));
												cabeceraGuardia.setComensustitucion(comensustitucion);
												cabeceraGuardia.setSustituto("1");
												cabeceraGuardia.setLetradosustituido(Long.parseLong(idPersona));
												cabeceraGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
												cabeceraGuardia.setFechamodificacion(new Date());
												cabeceraGuardia.setUsumodificacion(usuario.getIdusuario());
												int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabeceraGuardia);
												if(insertcabGuar != 0) {
													permutaCabsol.setIdpersona(Long.parseLong(newLetrado));
													int insertPerCabConf = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabsol);
													if(insertPerCabConf != 0) {
														item.setIdpersonaConfirmador(Long.parseLong(newLetrado));
														respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(item);
													}
												}
											}
										}
									}
								}
							}
							
							if(listPerGuarSol.isEmpty() && listPerGuarConf.isEmpty()) {
								ScsCabeceraguardiasKey cabGuardia = new ScsCabeceraguardiasKey();
								cabGuardia .setIdinstitucion(idInstitucion);
								cabGuardia .setIdguardia(Integer.parseInt(idGuardia));
								cabGuardia .setIdturno(Integer.parseInt(idTurno));
								cabGuardia .setIdpersona(Long.parseLong(idPersona));
								cabGuardia .setFechainicio(new Date(Long.parseLong(fechadesde)));
								
								ScsCabeceraguardias cabeceraGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabGuardia);
								
								int delcabGuar = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
								if(delcabGuar != 0) {
									cabeceraGuardia.setIdpersona(Long.parseLong(newLetrado));
									cabeceraGuardia.setComensustitucion(comensustitucion);
									cabeceraGuardia.setSustituto("1");
									cabeceraGuardia.setLetradosustituido(Long.parseLong(idPersona));
									cabeceraGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
									cabeceraGuardia.setFechamodificacion(new Date());
									cabeceraGuardia.setUsumodificacion(usuario.getIdusuario());
									respCabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabeceraGuardia);
								}
							
							}
							
							if (respCabGuar != 0) {

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

								int	respSalto = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(
											scgi, idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);
								if (respSalto != 0) {
									error.setCode(200);
									updateResponseDTO.setStatus(SigaConstants.OK);
								} else {
									error.setCode(400);
									updateResponseDTO.setStatus(SigaConstants.KO);
								}

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

									int respComp = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(
											scgi, idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);
									if (respComp != 0) {
										error.setCode(200);
										updateResponseDTO.setStatus(SigaConstants.OK);
									} else {
										error.setCode(400);
										updateResponseDTO.setStatus(SigaConstants.KO);
									}

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
					}else {
						error.setCode(400);
						error.setDescription("El letrado sustituto se encuentra en guardia.");
						updateResponseDTO.setStatus(SigaConstants.KO);
						
					}

				
			}

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
				permItem.setIdturno(permutaItem.getIdturno());

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
	public ComboGuardiasFuturasDTO getGuardiaDestinoInscrito(GuardiasItem guardiaItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboGuardiasFuturasDTO comboDTO = new ComboGuardiasFuturasDTO();
		List<ComboGuardiasFuturasItem> comboItems = null;

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

				comboItems = scsPermutaguardiasExtendsMapper.getGuardiaDestinoInscrito(guardiaItem,
						idInstitucion.toString());

				for (ComboGuardiasFuturasItem item : comboItems) {
					item.setValue(item.getValue() + "|" + item.getFechainicio().getTime());
				}

				LOGGER.info(
						"getTurnoInscrito() / scsTurnosextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setComboGuardiasFuturasItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTurnosTipo() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO validarPermuta(List<PermutaItem> permutas, HttpServletRequest request) throws Exception {
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

				int numPermutas = 0;
				// si la permuta esta sin validar suma 1 a la variable numPermutas
				for (PermutaItem p : permutas) {
					if (p.getFechaconfirmacion() == null) {
						numPermutas++;
					}

				}

				// si el tamaño de la lista es igual a numPermutas quiere decir que estan todas
				// sin validar y se puede proceder a la validacion de la permuta.
				// De no ser asi daria un error y no se valida ninguna.
				if (permutas.size() == numPermutas) {
					for (PermutaItem perm : permutas) {

						// creacion de copia de

						// se almacenan la fecha y motivos del confirmador en scs_permutaguardias

						LOGGER.info("validarPermuta() / modificacion de scs_permutaguardia");

						// creacion de la copia de cabecera guardia

						ScsPermutaguardiasKey permutaguardia = new ScsPermutaguardiasKey();
						permutaguardia.setIdinstitucion(idInstitucion);
						permutaguardia.setNumero(perm.getNumero());
						ScsPermutaguardias permutaGuardiaCopy = scsPermutaguardiasExtendsMapper
								.selectByPrimaryKey(permutaguardia);

						// creacion de copias para los registros de SCS_PERMUTA_CABECERA para
						// intercambiar fecha de inicio e idcalendarioguardias
						ScsPermutaCabeceraKey permCabSol = new ScsPermutaCabeceraKey();
						permCabSol.setIdinstitucion(idInstitucion);
						permCabSol.setIdPermutaCabecera(perm.getIdPerCabSolicitante());
						ScsPermutaCabecera permCabSolCopy = scsPermutaCabeceraMapper.selectByPrimaryKey(permCabSol);

						ScsPermutaCabeceraKey permCabConf = new ScsPermutaCabeceraKey();
						permCabConf.setIdinstitucion(idInstitucion);
						permCabConf.setIdPermutaCabecera(perm.getIdPerCabConfirmador());
						ScsPermutaCabecera permCabConfCopy = scsPermutaCabeceraMapper.selectByPrimaryKey(permCabConf);

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

						LOGGER.info("validarPermuta() / dentro de if para eliminacion");
						// eliminacion de registros de SCS_GuardiasColegiado

						// permutas de cabecera
						int delPermGuard = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(permutaGuardiaCopy);
						LOGGER.info("validarPermuta() / elimina permuta guardia");
						if (delPermGuard != 0) {
							int delPermCabSol = scsPermutaCabeceraMapper.deleteByPrimaryKey(permCabSolCopy);
							LOGGER.info("validarPermuta() / elimina permutacabecera solicitante");
							if (delPermCabSol != 0) {
								int delPermCabConf = scsPermutaCabeceraMapper.deleteByPrimaryKey(permCabConfCopy);
								LOGGER.info("validarPermuta() / elimina permutacabecera confirmador");
								if (delPermCabConf != 0) {
									int delCabGuarSol = scsCabeceraguardiasExtendsMapper
											.deleteByPrimaryKey(cabGuardiaSolCopy);
									LOGGER.info("validarPermuta() / elimina cabecera guardia solicitante");
									if (delCabGuarSol != 0) {
										int delCabGuarConf = scsCabeceraguardiasExtendsMapper
												.deleteByPrimaryKey(cabGuardiaConfCopy);
										LOGGER.info("validarPermuta() / elimina cabecera guardia confirmador");

										if (delCabGuarConf != 0) {

											fechaInicioConf = cabGuardiaConfCopy.getFechainicio();
											fechaInicioSoli = cabGuardiaSolCopy.getFechainicio();
											calendarioConf = cabGuardiaConfCopy.getIdcalendarioguardias();
											calendarioSoli = cabGuardiaSolCopy.getIdcalendarioguardias();

											cabGuardiaSolCopy.setFechainicio(fechaInicioConf);
											cabGuardiaSolCopy.setIdcalendarioguardias(calendarioConf);
											int insertCabGuarSol = scsCabeceraguardiasExtendsMapper
													.insertSelective(cabGuardiaSolCopy);
											LOGGER.info("validarPermuta() / inserta cabecera guardia para solicitante");
											if (insertCabGuarSol != 0) {
												cabGuardiaConfCopy.setFechainicio(fechaInicioSoli);
												cabGuardiaConfCopy.setIdcalendarioguardias(calendarioSoli);
												int insertCabGuarConf = scsCabeceraguardiasExtendsMapper
														.insertSelective(cabGuardiaConfCopy);
												LOGGER.info(
														"validarPermuta() / inserta cabecera guardia para confirmador");
												if (insertCabGuarConf != 0) {

													fechaInicioConf = permCabConfCopy.getFecha();
													fechaInicioSoli = permCabSolCopy.getFecha();
													calendarioConf = permCabConfCopy.getIdcalendarioguardias();
													calendarioSoli = permCabSolCopy.getIdcalendarioguardias();

													permCabSolCopy.setFecha(fechaInicioConf);
													permCabSolCopy.setIdcalendarioguardias(calendarioConf);
													int insertPerSol = scsPermutaCabeceraMapper
															.insertSelective(permCabSolCopy);
													LOGGER.info(
															"validarPermuta() / inserta permuta de cabcera para solicitante");
													if (insertPerSol != 0) {
														permCabConfCopy.setFecha(fechaInicioSoli);
														permCabConfCopy.setIdcalendarioguardias(calendarioSoli);
														int insertpermCabConf = scsPermutaCabeceraMapper
																.insertSelective(permCabConfCopy);
														LOGGER.info(
																"validarPermuta() / inserta permuta de cabcera para confirmador");
														if (insertpermCabConf != 0) {

															permutaGuardiaCopy
																	.setUsumodificacion(usuarios.get(0).getIdusuario());
															;
															permutaGuardiaCopy.setFechaconfirmacion(new Date());
															permutaGuardiaCopy.setMotivosconfirmador(
																	perm.getMotivossolicitante());
															responseUpdate = scsPermutaguardiasExtendsMapper
																	.insertSelective(permutaGuardiaCopy);
															LOGGER.info(
																	"validarPermuta() / inserta permuta de guardia");

														}
														// si el ultimo registro de todas las
														// inserciones se lleva a cabo de
														// forma satisfactoria
														// querra decir que la validacion ha sido
														// correcta
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

				if (responseUpdate != 0) {
					updateResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.info("permutarGuardia() / -> Exito en el proceso de insercion");

				} else {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.info("permutarGuardia() / -> Fallo en el proceso de insercion");
					throw (new Exception("Error al permutar la guardia"));
				}

			}
		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO permutarGuardia(PermutaItem permutaItem, HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int responseSol;
		int responseConf;
		int responsePerm = 0;
		String idPermcabSol;
		String idPermcabConf;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"permutarGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"permutarGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				// try {
				LOGGER.info(
						"permutarGuardia() / Entrada en la recopilacion de datos necesarios para realizar la insercion del registro");
				// primero se crean los 2 registros necesarios en SCS_permuta_cabecera para las
				// dos guardias involucradas en la permuta.

				// CREACION DEL 1º REGISTRO EN SCS_PERMUTA_CABECERA

				// obtener los datos de SCS_CABECERAGUARDIAS para poder rellenar los campos de
				// idcalendario y fecha para el solicitante

				LOGGER.info(
						"permutarGuardia() / scsPermutaCabeceraMapper.selectByExample() -> Salida de scsPermutaCabeceraMapper para obtener el ultimo id_permuta_cabecera para el solicitante");

				// registro para guardia solicitante

				// comprobacion de si existe las permutas
				ScsPermutaCabeceraExample perCabsol = new ScsPermutaCabeceraExample();
				perCabsol.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(permutaItem.getIdturnoSolicitante())
						.andIdguardiaEqualTo(permutaItem.getIdguardiaSolicitante())
						.andIdcalendarioguardiasEqualTo(permutaItem.getIdcalendarioguardiasSolicitan())
						.andIdpersonaEqualTo(permutaItem.getIdpersonaSolicitante())
						.andFechaEqualTo(permutaItem.getFechainicioSolicitante());

				List<ScsPermutaCabecera> existeSol = scsPermutaCabeceraExtendsMapper.selectByExample(perCabsol);

				idPermcabSol = scsPermutaCabeceraExtendsMapper.maxIdPermutaCabecera(idInstitucion.toString());
				LOGGER.info("permutarGuardia() / obtiene id de la permuta del solicitante");
				ScsPermutaCabecera permutaSolicitante = new ScsPermutaCabecera();

				permutaSolicitante.setIdPermutaCabecera(Long.parseLong(idPermcabSol));
				permutaSolicitante.setIdinstitucion(idInstitucion);
				permutaSolicitante.setIdturno(permutaItem.getIdturnoSolicitante());
				permutaSolicitante.setIdguardia(permutaItem.getIdguardiaSolicitante());
				permutaSolicitante.setIdpersona(permutaItem.getIdpersonaSolicitante());
				permutaSolicitante.setFecha(permutaItem.getFechainicioSolicitante());
				permutaSolicitante.setIdcalendarioguardias(permutaItem.getIdcalendarioguardiasSolicitan());
				permutaSolicitante.setUsumodificacion(usuarios.get(0).getIdusuario());
				permutaSolicitante.setFechamodificacion(new Date());

				LOGGER.info("permutarGuardia() / comprobando si existe la permuta para el soliciante");
				// si el tamaño de la lista es = 0 quiere decir que no existe registro y lo crea
				// y si existe solo mandara los datos a permuta guardia
				if (existeSol.size() == 0) {
					responseSol = scsPermutaCabeceraMapper.insert(permutaSolicitante);
				}

				idPermcabConf = scsPermutaCabeceraExtendsMapper.maxIdPermutaCabecera(idInstitucion.toString());
				LOGGER.info("permutarGuardia() / obtiene id de la permuta del confirmador");

				// registro para guardia confirmador

				ScsPermutaCabecera permutaConfirmador = new ScsPermutaCabecera();

				permutaConfirmador.setIdPermutaCabecera(Long.parseLong(idPermcabConf));
				permutaConfirmador.setIdinstitucion(idInstitucion);
				permutaConfirmador.setIdturno(permutaItem.getIdturnoConfirmador());
				permutaConfirmador.setIdguardia(permutaItem.getIdguardiaConfirmador());
				permutaConfirmador.setIdpersona(permutaItem.getIdpersonaConfirmador());
				permutaConfirmador.setFecha(permutaItem.getFechainicioConfirmador());
				permutaConfirmador.setIdcalendarioguardias(permutaItem.getIdcalendarioguardiasConfirmad());
				permutaConfirmador.setUsumodificacion(usuarios.get(0).getIdusuario());
				permutaConfirmador.setFechamodificacion(new Date());

				ScsPermutaCabeceraExample perCabConf = new ScsPermutaCabeceraExample();
				perCabConf.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(permutaItem.getIdturnoConfirmador())
						.andIdguardiaEqualTo(permutaItem.getIdguardiaConfirmador())
						.andIdcalendarioguardiasEqualTo(permutaItem.getIdcalendarioguardiasConfirmad())
						.andIdpersonaEqualTo(permutaItem.getIdpersonaConfirmador())
						.andFechaEqualTo(permutaItem.getFechainicioConfirmador());

				LOGGER.info("permutarGuardia() / comprobando si existe la permuta para el confirmador");
				List<ScsPermutaCabecera> existeConf = scsPermutaCabeceraExtendsMapper.selectByExample(perCabConf);
				// si el tamaño de la lista es = 0 quiere decir que no existe registro y lo crea
				// y si existe solo mandara los datos a permuta guardia
				if (existeConf.size() == 0) {
					LOGGER.info("permutarGuardia() / crea permuta para confirmador si no existe");
					responseConf = scsPermutaCabeceraMapper.insertSelective(permutaConfirmador);
				}

				String numero = scsPermutaguardiasExtendsMapper.maxIdPermutaGuardia(idInstitucion.toString());

				ScsPermutaguardias permutaGuardia = new ScsPermutaguardias();
				permutaGuardia.setIdinstitucion(idInstitucion);
				permutaGuardia.setNumero(Long.parseLong(numero));
				permutaGuardia.setFechasolicitud(new Date());
				permutaGuardia.setAnulada(0);
				permutaGuardia.setFechamodificacion(new Date());
				permutaGuardia.setUsumodificacion(usuarios.get(0).getIdusuario());

				// solicitante
				permutaGuardia.setIdpersonaSolicitante(permutaSolicitante.getIdpersona());
				permutaGuardia.setIdturnoSolicitante(permutaSolicitante.getIdturno());
				permutaGuardia.setIdguardiaSolicitante(permutaSolicitante.getIdguardia());
				permutaGuardia.setIdcalendarioguardiasSolicitan(permutaSolicitante.getIdcalendarioguardias());
				permutaGuardia.setFechainicioSolicitante(permutaSolicitante.getFecha());
				permutaGuardia.setMotivossolicitante(permutaItem.getMotivossolicitante());
				permutaGuardia.setIdPerCabSolicitante(permutaSolicitante.getIdPermutaCabecera());

				// confirmador
				permutaGuardia.setIdpersonaConfirmador(permutaConfirmador.getIdpersona());
				permutaGuardia.setIdturnoConfirmador(permutaConfirmador.getIdturno());
				permutaGuardia.setIdguardiaConfirmador(permutaConfirmador.getIdguardia());
				permutaGuardia.setIdcalendarioguardiasConfirmad(permutaConfirmador.getIdcalendarioguardias());
				permutaGuardia.setFechainicioConfirmador(permutaConfirmador.getFecha());
				permutaGuardia.setIdPerCabConfirmador(permutaConfirmador.getIdPermutaCabecera());

				ScsPermutaguardiasExample existePerGuar = new ScsPermutaguardiasExample();
				existePerGuar.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoSolicitanteEqualTo(permutaGuardia.getIdturnoSolicitante())
						.andIdguardiaSolicitanteEqualTo(permutaGuardia.getIdguardiaSolicitante())
						.andIdcalendarioguardiasSolicitanEqualTo(permutaGuardia.getIdcalendarioguardiasSolicitan())
						.andIdpersonaSolicitanteEqualTo(permutaGuardia.getIdpersonaSolicitante())
						.andFechainicioSolicitanteEqualTo(permutaGuardia.getFechainicioSolicitante())

						.andIdturnoConfirmadorEqualTo(permutaGuardia.getIdturnoConfirmador())
						.andIdguardiaConfirmadorEqualTo(permutaGuardia.getIdguardiaConfirmador())
						.andIdcalendarioguardiasConfirmadEqualTo(permutaGuardia.getIdcalendarioguardiasConfirmad())
						.andIdpersonaConfirmadorEqualTo(permutaGuardia.getIdpersonaConfirmador())
						.andFechainicioConfirmadorEqualTo(permutaGuardia.getFechainicioConfirmador());

				List<ScsPermutaguardias> existePer = scsPermutaguardiasExtendsMapper.selectByExample(existePerGuar);
				LOGGER.info("permutarGuardia() / comprobando si existe la permuta de guardia ");
				if (existePer.size() == 0) {
					responsePerm = scsPermutaguardiasExtendsMapper.insert(permutaGuardia);
				} else {
					ScsPermutaguardias permutaGuardiaMod = new ScsPermutaguardias();
					permutaGuardiaMod.setIdinstitucion(idInstitucion);
					permutaGuardiaMod.setNumero(existePer.get(0).getNumero());
					permutaGuardiaMod.setFechasolicitud(new Date());
					permutaGuardiaMod.setFechamodificacion(new Date());
					permutaGuardiaMod.setUsumodificacion(usuarios.get(0).getIdusuario());
					permutaGuardiaMod.setMotivossolicitante(permutaItem.getMotivossolicitante());
					responsePerm = scsPermutaguardiasExtendsMapper.updateByPrimaryKey(permutaGuardiaMod);
				}

				LOGGER.info(
						"permutarGuardia() / scsPermutaguardiasExtendsMapper.insertSelective() -> insertado el registro en SCS_PERMUTAGUARDIAS con los datos del solicitante y el confirmador");

				if (responsePerm != 0) {
					insertResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.info("permutarGuardia() / -> Exito en el proceso de insercion");

				} else {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.info("permutarGuardia() / -> Fallo en el proceso de insercion");
					throw (new Exception("Error al permutar la guardia"));

				}

			}
		}


		LOGGER.info("comboTurnosTipo() -> Salida del servicio para obtener los tipos ejg");
		return insertResponseDTO;
	}

}
