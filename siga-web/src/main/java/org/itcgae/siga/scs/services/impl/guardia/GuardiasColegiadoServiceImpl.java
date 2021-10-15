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
			
				//datos = scsCabeceraguardiasExtendsMapper.getCalendarioGuardiaColegiado(idInstitucion.toString(),idTurno, idGuardia, idcalendarioguardias);
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

		LOGGER.info("updateGuardiaColeg() -> Salida del servicio para insertar guardia de colegiado en cabeceras de guardias");

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
					
					

					
					if(scsCabeceraguardiasExtendsMapper.tieneGuardia(institucion, Long.parseLong(newLetrado)) == 0) {
						
						if (saltoOcompensacion == "N") {
						LOGGER.info(
								"sustituirGuardiaColeg() / obtener permuta guardia solicitante");
						
						// OBTENER PERMUTAS DE GUARDIA
						ScsPermutaguardiasExample pgeSol = new ScsPermutaguardiasExample();
						pgeSol.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdguardiaSolicitanteEqualTo(Integer.parseInt(idGuardia))
						.andIdturnoSolicitanteEqualTo(Integer.parseInt(idTurno))
								.andIdpersonaSolicitanteEqualTo(Long.parseLong(idPersona));
						List<ScsPermutaguardias> permGuarSol = scsPermutaguardiasMapper.selectByExample(pgeSol);
						List<ScsPermutaguardias> permGuarSolMod = new ArrayList<ScsPermutaguardias>();
						if (!permGuarSol.isEmpty()) {
							for(ScsPermutaguardias permutaSol: permGuarSol) {
								
								permutaSol.setIdpersonaSolicitante(Long.parseLong(newLetrado));
								permGuarSolMod.add(permutaSol);
								
								ScsPermutaguardiasKey key =new ScsPermutaguardiasKey();
								
								key.setIdinstitucion(permutaSol.getIdinstitucion());
								key.setNumero(permutaSol.getNumero());
								
								int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);
								
								if(borra == 1) {
									for(ScsPermutaguardias newPermuta: permGuarSolMod) {
										 insertadoSol = scsPermutaguardiasMapper.insertSelective(newPermuta);
									}
								}
								
								
								LOGGER.info(
										"sustituirGuardiaColeg() / sustituir letrado en permutaguardia solicitante");
							}
						}
						
						LOGGER.info(
								"sustituirGuardiaColeg() / obtener permuta guardia confirmante");

						ScsPermutaguardiasExample pgeConf = new ScsPermutaguardiasExample();
						pgeConf.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdguardiaConfirmadorEqualTo(Integer.parseInt(idGuardia))
						.andIdturnoConfirmadorEqualTo(Integer.parseInt(idTurno))
						.andIdpersonaConfirmadorEqualTo(Long.parseLong(idPersona));
						List<ScsPermutaguardias> permGuarConf = scsPermutaguardiasMapper.selectByExample(pgeSol);
						List<ScsPermutaguardias> permGuarConfMod = new ArrayList<ScsPermutaguardias>();
						if (!permGuarConf.isEmpty()) {
							for(ScsPermutaguardias permutaConf: permGuarConf) {
								
								permutaConf.setIdpersonaSolicitante(Long.parseLong(newLetrado));
								permGuarConfMod.add(permutaConf);
								
								ScsPermutaguardiasKey key =new ScsPermutaguardiasKey();
								
								key.setIdinstitucion(permutaConf.getIdinstitucion());
								key.setNumero(permutaConf.getNumero());
								
								int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);
								
								if(borra == 1) {
									for(ScsPermutaguardias newPermuta: permGuarConfMod) {
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
							for(ScsPermutaCabecera permutaCabecera: pc) {
								
								permutaCabecera.setIdpersona(Long.parseLong(newLetrado));
								pcMod.add(permutaCabecera);
								
								ScsPermutaCabeceraKey key =new ScsPermutaCabeceraKey();
								
								key.setIdinstitucion(permutaCabecera.getIdinstitucion());
								key.setIdPermutaCabecera(permutaCabecera.getIdPermutaCabecera());
								
								int borra = scsPermutaCabeceraMapper.deleteByPrimaryKey(key);
								if(borra == 1) {
									if(insertadoSol != 0 || insertadoConf != 0) {
										for(ScsPermutaCabecera newPc : pcMod ) {
											insertadoPc = scsPermutaCabeceraMapper.insertSelective(newPc);
										}	
									}
									
								}
								
								
								LOGGER.info(
										"sustituirGuardiaColeg() / sustituir letrado en permutaguardia confirmante");
							}
						}
                        
                        if(insertadoPc != 0) {
                        	 respCabGuar = scsCabeceraguardiasExtendsMapper.sustituirLetrado(institucion, idTurno, idGuardia,
         							fechadesde, Long.parseLong(idPersona), Long.parseLong(newLetrado), fechaSustitucion, comensustitucion);
                        }
								
                       
						
						
						

						
					} else {
						LOGGER.info(
								"sustituirGuardiaColeg() / obtener permuta guardia solicitante");
						
						// OBTENER PERMUTAS DE GUARDIA
						ScsPermutaguardiasExample pgeSol = new ScsPermutaguardiasExample();
						pgeSol.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdguardiaSolicitanteEqualTo(Integer.parseInt(idGuardia))
						.andIdturnoSolicitanteEqualTo(Integer.parseInt(idTurno))
								.andIdpersonaSolicitanteEqualTo(Long.parseLong(idPersona));
						List<ScsPermutaguardias> permGuarSol = scsPermutaguardiasMapper.selectByExample(pgeSol);
						List<ScsPermutaguardias> permGuarSolMod = new ArrayList<ScsPermutaguardias>();
						if (!permGuarSol.isEmpty()) {
							for(ScsPermutaguardias permutaSol: permGuarSol) {
								
								permutaSol.setIdpersonaSolicitante(Long.parseLong(newLetrado));
								permGuarSolMod.add(permutaSol);
								
								ScsPermutaguardiasKey key =new ScsPermutaguardiasKey();
								
								key.setIdinstitucion(permutaSol.getIdinstitucion());
								key.setNumero(permutaSol.getNumero());
								
								int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);
								
								if(borra == 1) {
									for(ScsPermutaguardias newPermuta: permGuarSolMod) {
										 insertadoSol = scsPermutaguardiasMapper.insertSelective(newPermuta);
									}
								}
								
								
								LOGGER.info(
										"sustituirGuardiaColeg() / sustituir letrado en permutaguardia solicitante");
							}
						}
						
						LOGGER.info(
								"sustituirGuardiaColeg() / obtener permuta guardia confirmante");

						ScsPermutaguardiasExample pgeConf = new ScsPermutaguardiasExample();
						pgeConf.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdguardiaConfirmadorEqualTo(Integer.parseInt(idGuardia))
						.andIdturnoConfirmadorEqualTo(Integer.parseInt(idTurno))
						.andIdpersonaConfirmadorEqualTo(Long.parseLong(idPersona));
						List<ScsPermutaguardias> permGuarConf = scsPermutaguardiasMapper.selectByExample(pgeSol);
						List<ScsPermutaguardias> permGuarConfMod = new ArrayList<ScsPermutaguardias>();
						if (!permGuarConf.isEmpty()) {
							for(ScsPermutaguardias permutaConf: permGuarConf) {
								
								permutaConf.setIdpersonaSolicitante(Long.parseLong(newLetrado));
								permGuarConfMod.add(permutaConf);
								
								ScsPermutaguardiasKey key =new ScsPermutaguardiasKey();
								
								key.setIdinstitucion(permutaConf.getIdinstitucion());
								key.setNumero(permutaConf.getNumero());
								
								int borra = scsPermutaguardiasMapper.deleteByPrimaryKey(key);
								
								if(borra == 1) {
									for(ScsPermutaguardias newPermuta: permGuarConfMod) {
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
							for(ScsPermutaCabecera permutaCabecera: pc) {
								
								permutaCabecera.setIdpersona(Long.parseLong(newLetrado));
								pcMod.add(permutaCabecera);
								
								ScsPermutaCabeceraKey key =new ScsPermutaCabeceraKey();
								
								key.setIdinstitucion(permutaCabecera.getIdinstitucion());
								key.setIdPermutaCabecera(permutaCabecera.getIdPermutaCabecera());
								
								int borra = scsPermutaCabeceraMapper.deleteByPrimaryKey(key);
								if(borra == 1) {
									if(insertadoSol != 0 || insertadoConf != 0) {
										for(ScsPermutaCabecera newPc : pcMod ) {
											insertadoPc = scsPermutaCabeceraMapper.insertSelective(newPc);
										}	
									}
									
								}
								
								
								LOGGER.info(
										"sustituirGuardiaColeg() / sustituir letrado en permutaguardia confirmante");
							}
						}
                        
                        if(insertadoPc != 0) {
                        	 respCabGuar = scsCabeceraguardiasExtendsMapper.sustituirLetrado(institucion, idTurno, idGuardia,
         							fechadesde, Long.parseLong(idPersona), Long.parseLong(newLetrado), fechaSustitucion, comensustitucion);
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

								nuevoId = scsSaltoscompensacionesExtendsMapper.selectNuevoIdSaltosCompensaciones(scgi,
										idInstitucion.toString());

								respSaltoComp = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(scgi,
										idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);

								break;

							case "C":

								scgi.setIdPersona(idPersona);
								scgi.setIdGuardia(idGuardia);
								scgi.setIdTurno(idTurno);
								scgi.setFecha(fechaHoy.toString());
								scgi.setSaltoCompensacion(saltoOcompensacion);
								scgi.setMotivo("Sustitución");

								nuevoId = scsSaltoscompensacionesExtendsMapper.selectNuevoIdSaltosCompensaciones(scgi,
										idInstitucion.toString());

								respSaltoComp = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(scgi,
										idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);

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

								nuevoId = scsSaltoscompensacionesExtendsMapper.selectNuevoIdSaltosCompensaciones(scgi,
										idInstitucion.toString());

								int respSaltoComp1 = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(scgi,
										idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);
								if(respSaltoComp1 != 0) {
									scgi = new SaltoCompGuardiaItem();
									scgi.setIdPersona(idPersona);
									scgi.setIdGuardia(idGuardia);
									scgi.setIdTurno(idTurno);
									scgi.setFecha(fechaHoy.toString());
									scgi.setSaltoCompensacion(comp);
									scgi.setMotivo("Sustitución");

									nuevoId = scsSaltoscompensacionesExtendsMapper.selectNuevoIdSaltosCompensaciones(scgi,
											idInstitucion.toString());

									int respSaltoComp2 = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(scgi,
											idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);
									
									if(respSaltoComp2 != 0) {
										error.setCode(200);
										updateResponseDTO.setStatus(SigaConstants.OK);
									}else {
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
				
				idConjuntoGuardias =  scsGuardiasturnoExtendsMapper.getConjuntoGuardiasIdFromGuardiaId(idGuardia, idInstitucion.toString());
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

}
