package org.itcgae.siga.scs.services.impl.guardia;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasDTO;
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporalesExample;
import org.itcgae.siga.db.entities.FcsFactApunte;
import org.itcgae.siga.db.entities.FcsFactApunteExample;
import org.itcgae.siga.db.entities.FcsFactApunteKey;
import org.itcgae.siga.db.entities.FcsFactGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasKey;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoKey;
import org.itcgae.siga.db.entities.ScsPermutaCabecera;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraExample;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraKey;
import org.itcgae.siga.db.entities.ScsPermutaguardias;
import org.itcgae.siga.db.entities.ScsPermutaguardiasExample;
import org.itcgae.siga.db.entities.ScsPermutaguardiasKey;
import org.itcgae.siga.db.mappers.CenBajastemporalesMapper;
import org.itcgae.siga.db.mappers.FcsFactApunteMapper;
import org.itcgae.siga.db.mappers.FcsFactGuardiascolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsCalendarioguardiasMapper;
import org.itcgae.siga.db.mappers.ScsPermutaCabeceraMapper;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
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

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private CenBajastemporalesMapper cenBajastemporalesMapper;

	@Autowired
	private FcsFactGuardiascolegiadoMapper fcsFactGuardiascolegiadoMapper;
	
	@Autowired
	private ScsCalendarioguardiasMapper scsCalendarioguardiasMapper;
	
	@Autowired
	private GuardiasServiceImpl guardiasServiceImpl;
	
	//SIGARNV-2885@DTT.JAMARTIN@14/02/2023@INICIO
	@Autowired
	private FcsFactApunteMapper fcsFactApunteMapper;
	//SIGARNV-2885@DTT.JAMARTIN@14/02/2023@FIN 

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

				List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(ti, idInstitucion, usuarios.get(0).getIdlenguaje());

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
	
	private int crearGuardiaColegiado(ScsGuardiascolegiado item){
		ScsGuardiascolegiado scsGuardiascolegiado = new ScsGuardiascolegiado();
		scsGuardiascolegiado.setIdinstitucion(item.getIdinstitucion());
		scsGuardiascolegiado.setIdturno(item.getIdturno());
		scsGuardiascolegiado.setIdguardia(item.getIdguardia());
		//scsGuardiascolegiado.setFechainicio(new SimpleDateFormat("dd/MM/yyyy")
		//		.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
		scsGuardiascolegiado.setFechainicio(item.getFechainicio());
		scsGuardiascolegiado.setFechafin(item.getFechafin());
		scsGuardiascolegiado.setIdpersona(item.getIdpersona());
		scsGuardiascolegiado.setDiasguardia((long) 1);
		scsGuardiascolegiado.setDiasacobrar((long) 1);
		scsGuardiascolegiado.setObservaciones("Añadido Guardia Colegiado");
		scsGuardiascolegiado.setReserva("N");
		scsGuardiascolegiado.setFacturado("N");
		scsGuardiascolegiado.setIdfacturacion(null);
		scsGuardiascolegiado.setPagado("N");
		scsGuardiascolegiado.setFechamodificacion(new Date());
		scsGuardiascolegiado.setUsumodificacion(0);
		return scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);
	}
	
	private int crearCabeceraGuardiaColegiado(ScsCabeceraguardias itemCabecera) {
		ScsCabeceraguardias scsCabeceraguardias = new ScsCabeceraguardias();
		scsCabeceraguardias.setIdinstitucion(itemCabecera.getIdinstitucion());
		scsCabeceraguardias.setIdturno(itemCabecera.getIdturno());
		scsCabeceraguardias.setIdcalendarioguardias(itemCabecera.getIdcalendarioguardias());

		scsCabeceraguardias.setIdguardia(itemCabecera.getIdguardia());
		scsCabeceraguardias.setFechainicio(itemCabecera.getFechainicio());
		scsCabeceraguardias.setFechaFin(itemCabecera.getFechaFin());
		scsCabeceraguardias.setIdpersona(itemCabecera.getIdpersona());
		scsCabeceraguardias.setPosicion((short) 1);
		scsCabeceraguardias.setFechamodificacion(new Date());
		scsCabeceraguardias.setSustituto("0");
		scsCabeceraguardias.setFechaalta(new Date());

		// Las metemos validadas
		scsCabeceraguardias.setValidado("1");
		scsCabeceraguardias.setFechavalidacion(new Date());
		scsCabeceraguardias.setUsumodificacion(0);
		
		return  scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
	}
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO insertGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("insertGuardiaColeg() ->  Entrada al servicio para eliminar prisiones");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int controlError = 0;
		ScsCabeceraguardias cabeceraRes = new ScsCabeceraguardias();
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
					
					List<String> listaFechas = Arrays.asList( guardiasItem.getFechaIntro().split(","));
					

					ScsCabeceraguardias cabecera = new ScsCabeceraguardias();
					cabecera.setIdinstitucion(idInstitucion);
					cabecera.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					cabecera.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					cabecera.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					cabecera.setFechainicio(new SimpleDateFormat("dd/MM/yyyy").parse(listaFechas.get(0)));
					cabecera.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").parse(listaFechas.get(listaFechas.size() - 1 )));
					cabecera.setIdcalendarioguardias(Integer.parseInt(guardiasItem.getIdCalendarioGuardias()));
					cabeceraRes.setFechainicio(new SimpleDateFormat("dd/MM/yyyy").parse(listaFechas.get(0)));
					cabeceraRes.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").parse(listaFechas.get(listaFechas.size() - 1 )));
					ScsCabeceraguardias cabeceraControl = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabecera);
					if(cabeceraControl == null) {
						int res = crearCabeceraGuardiaColegiado(cabecera);
						if(res != 1)controlError++;
					}
					
					
					//Creamos un item con atributos basicos (no fechas)
					ScsGuardiascolegiado itemIn = new ScsGuardiascolegiado();
					itemIn.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					itemIn.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					itemIn.setIdinstitucion(idInstitucion);
					itemIn.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					
					//Añadimos un insert por cada fecha disponible- en guardia colegiado.
					for (int i = 0; i < listaFechas.size(); i++) {
						itemIn.setFechainicio(new SimpleDateFormat("dd/MM/yyyy").parse(listaFechas.get(0)));
						itemIn.setFechafin(new SimpleDateFormat("dd/MM/yyyy").parse(listaFechas.get(i)));
						int rese = crearGuardiaColegiado(itemIn);
						if(rese == 1) {
							try {
								this.guardiasServiceImpl.triggerGuardiaColegiadoAID(itemIn, 1);
							} catch (Exception e) {
								LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 1 (insert)");
							}
						}
						else {
							controlError++;
						}
					}
				//	response = scsCabeceraguardiasExtendsMapper.insertSelective(guardia);

				} catch (Exception e) {
					
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if ( controlError > 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			insertResponseDTO.setStatus(SigaConstants.OK);
			error.setDescription(cabeceraRes.getFechainicio().getTime() +"/"+cabeceraRes.getFechaFin().getTime());
		}

		insertResponseDTO.setError(error);

		LOGGER.info(
				"insertGuardiaColeg() -> Salida del servicio para insertar guardia de colegiado en cabeceras de guardias");

		return insertResponseDTO;
	}

	@Transactional
	public UpdateResponseDTO sustituirGuardiaColegA(String[] datos, HttpServletRequest request) throws Exception {
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

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					String fechaHoy = formatter.format(new Date());

					String institucion = idInstitucion.toString();
					String idTurno = datos[0].toString();
					String idGuardia = datos[1].toString();
					Long fechadesde = Long.parseLong(datos[2]);
					String idPersona = datos[3].toString();
					String newLetrado = datos[4].toString();
					String fechaSustitucion = datos[5].toString();
					String comensustitucion = datos[6].toString();
					String saltoOcompensacion = datos[7].toString();
					String calendarioGuardias = datos[8].toString();

					if (scsCabeceraguardiasExtendsMapper.tieneGuardia(institucion, Long.parseLong(newLetrado)) == 0) {

						if (saltoOcompensacion.equalsIgnoreCase("N")) {
							LOGGER.info("sustituirGuardiaColeg() / obtener permuta guardia solicitante");

							//obtiene la guardia del colegiado
							ScsCabeceraguardiasKey guardia = new ScsCabeceraguardiasKey();
							guardia.setIdinstitucion(idInstitucion);
							guardia.setIdguardia(Integer.parseInt(idGuardia));
							guardia.setIdturno(Integer.parseInt(idTurno));
							guardia.setIdpersona(Long.parseLong(idPersona));
							guardia.setFechainicio(new Date(fechadesde));
							ScsCabeceraguardias cabGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(guardia);
							
							//obtiene la permuta asociada a ese colegiado en una fecha concreta
							ScsPermutaCabeceraExample permCab =new ScsPermutaCabeceraExample();
							permCab.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdguardiaEqualTo(cabGuardia.getIdguardia())
							.andIdturnoEqualTo(cabGuardia.getIdturno())
							.andIdpersonaEqualTo(cabGuardia.getIdpersona())
							.andFechaEqualTo(cabGuardia.getFechainicio());
							List<ScsPermutaCabecera> permutaCabecera = scsPermutaCabeceraExtendsMapper.selectByExample(permCab);
							
							if(!permutaCabecera.isEmpty()) {
								ScsPermutaguardiasExample permutaGuardiaSolicitante = new ScsPermutaguardiasExample();
								permutaGuardiaSolicitante.createCriteria()
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdPerCabSolicitanteEqualTo(permutaCabecera.get(0).getIdPermutaCabecera());
								List<ScsPermutaguardias> permutaGS = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaSolicitante);
								if(!permutaGS.isEmpty()) {
									
										int delPG = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(permutaGS.get(0));
										if(delPG != 0) {
											int delPC = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(permutaCabecera.get(0));
											if(delPC != 0) {
												int delGC = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
												if(delGC != 0) {
													cabGuardia.setIdpersona(Long.parseLong(newLetrado));
													cabGuardia.setComensustitucion(comensustitucion);
													cabGuardia.setSustituto("1");
													cabGuardia.setLetradosustituido(Long.parseLong(idPersona));
													cabGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
													cabGuardia.setFechamodificacion(new Date());
													cabGuardia.setUsumodificacion(usuario.getIdusuario());
													int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabGuardia);
													if(insertcabGuar != 0) {
														permutaCabecera.get(0).setIdpersona(Long.parseLong(newLetrado));
														int insertPerCabSol = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabecera.get(0));
														if(insertPerCabSol != 0) {
															permutaGS.get(0).setIdpersonaSolicitante(Long.parseLong(newLetrado));
															respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(permutaGS.get(0));
														}
													}
												}
											}
										}
									
								}
								ScsPermutaguardiasExample permutaGuardiaConfirmador = new ScsPermutaguardiasExample();
								permutaGuardiaConfirmador.createCriteria()
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdPerCabConfirmadorEqualTo(permutaCabecera.get(0).getIdPermutaCabecera());
								
								List<ScsPermutaguardias> permutaGC = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaConfirmador);
								if(!permutaGC.isEmpty()) {
									int delPG = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(permutaGC.get(0));
									if(delPG != 0) {
										int delPC = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(permutaCabecera.get(0));
										if(delPC != 0) {
											int delGC = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
											if(delGC != 0) {
												cabGuardia.setIdpersona(Long.parseLong(newLetrado));
												cabGuardia.setComensustitucion(comensustitucion);
												cabGuardia.setSustituto("1");
												cabGuardia.setLetradosustituido(Long.parseLong(idPersona));
												cabGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
												cabGuardia.setFechamodificacion(new Date());
												cabGuardia.setUsumodificacion(usuario.getIdusuario());
												int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabGuardia);
												if(insertcabGuar != 0) {
													permutaCabecera.get(0).setIdpersona(Long.parseLong(newLetrado));
													int insertPerCabConf = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabecera.get(0));
													if(insertPerCabConf != 0) {
														permutaGC.get(0).setIdpersonaConfirmador(Long.parseLong(newLetrado));
														respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(permutaGC.get(0));
													}
												}
											}
										}
									}
								}
							}else {
								int cabeGua = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
								if(cabeGua != 0) {
									cabGuardia.setIdpersona(Long.parseLong(newLetrado));
									cabGuardia.setComensustitucion(comensustitucion);
									cabGuardia.setSustituto("1");
									cabGuardia.setLetradosustituido(Long.parseLong(idPersona));
									cabGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
									cabGuardia.setFechamodificacion(new Date());
									cabGuardia.setUsumodificacion(usuario.getIdusuario());
									respCabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabGuardia);
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

							//obtiene la guardia del colegiado
							ScsCabeceraguardiasKey guardia = new ScsCabeceraguardiasKey();
							guardia.setIdinstitucion(idInstitucion);
							guardia.setIdguardia(Integer.parseInt(idGuardia));
							guardia.setIdturno(Integer.parseInt(idTurno));
							guardia.setIdpersona(Long.parseLong(idPersona));
							guardia.setFechainicio(new Date(fechadesde));
							ScsCabeceraguardias cabGuardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(guardia);
							
							//obtiene la permuta asociada a ese colegiado en una fecha concreta
							ScsPermutaCabeceraExample permCab =new ScsPermutaCabeceraExample();
							permCab.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdguardiaEqualTo(cabGuardia.getIdguardia())
							.andIdturnoEqualTo(cabGuardia.getIdturno())
							.andIdpersonaEqualTo(cabGuardia.getIdpersona())
							.andFechaEqualTo(cabGuardia.getFechainicio());
							List<ScsPermutaCabecera> permutaCabecera = scsPermutaCabeceraExtendsMapper.selectByExample(permCab);
							
							if(!permutaCabecera.isEmpty()) {
								ScsPermutaguardiasExample permutaGuardiaSolicitante = new ScsPermutaguardiasExample();
								permutaGuardiaSolicitante.createCriteria()
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdPerCabSolicitanteEqualTo(permutaCabecera.get(0).getIdPermutaCabecera());
								List<ScsPermutaguardias> permutaGS = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaSolicitante);
								if(!permutaGS.isEmpty()) {
									
										int delPG = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(permutaGS.get(0));
										if(delPG != 0) {
											int delPC = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(permutaCabecera.get(0));
											if(delPC != 0) {
												int delGC = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
												if(delGC != 0) {
													cabGuardia.setIdpersona(Long.parseLong(newLetrado));
													cabGuardia.setComensustitucion(comensustitucion);
													cabGuardia.setSustituto("1");
													cabGuardia.setLetradosustituido(Long.parseLong(idPersona));
													cabGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
													cabGuardia.setFechamodificacion(new Date());
													cabGuardia.setUsumodificacion(usuario.getIdusuario());
													int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabGuardia);
													if(insertcabGuar != 0) {
														permutaCabecera.get(0).setIdpersona(Long.parseLong(newLetrado));
														int insertPerCabSol = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabecera.get(0));
														if(insertPerCabSol != 0) {
															permutaGS.get(0).setIdpersonaSolicitante(Long.parseLong(newLetrado));
															respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(permutaGS.get(0));
														}
													}
												}
											}
										}
									
								}
								ScsPermutaguardiasExample permutaGuardiaConfirmador = new ScsPermutaguardiasExample();
								permutaGuardiaConfirmador.createCriteria()
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdPerCabConfirmadorEqualTo(permutaCabecera.get(0).getIdPermutaCabecera());
								
								List<ScsPermutaguardias> permutaGC = scsPermutaguardiasExtendsMapper.selectByExample(permutaGuardiaConfirmador);
								if(!permutaGC.isEmpty()) {
									int delPG = scsPermutaguardiasExtendsMapper.deleteByPrimaryKey(permutaGC.get(0));
									if(delPG != 0) {
										int delPC = scsPermutaCabeceraExtendsMapper.deleteByPrimaryKey(permutaCabecera.get(0));
										if(delPC != 0) {
											int delGC = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
											if(delGC != 0) {
												cabGuardia.setIdpersona(Long.parseLong(newLetrado));
												cabGuardia.setComensustitucion(comensustitucion);
												cabGuardia.setSustituto("1");
												cabGuardia.setLetradosustituido(Long.parseLong(idPersona));
												cabGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
												cabGuardia.setFechamodificacion(new Date());
												cabGuardia.setUsumodificacion(usuario.getIdusuario());
												int insertcabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabGuardia);
												if(insertcabGuar != 0) {
													permutaCabecera.get(0).setIdpersona(Long.parseLong(newLetrado));
													int insertPerCabConf = scsPermutaCabeceraExtendsMapper.insertSelective(permutaCabecera.get(0));
													if(insertPerCabConf != 0) {
														permutaGC.get(0).setIdpersonaConfirmador(Long.parseLong(newLetrado));
														respCabGuar = scsPermutaguardiasExtendsMapper.insertSelective(permutaGC.get(0));
													}
												}
											}
										}
									}
								}
							}else {
								int cabeGua = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardia);
								if(cabeGua != 0) {
									cabGuardia.setIdpersona(Long.parseLong(newLetrado));
									cabGuardia.setComensustitucion(comensustitucion);
									cabGuardia.setSustituto("1");
									cabGuardia.setLetradosustituido(Long.parseLong(idPersona));
									cabGuardia.setFechasustitucion(new Date(Long.parseLong(fechaSustitucion)));
									cabGuardia.setFechamodificacion(new Date());
									cabGuardia.setUsumodificacion(usuario.getIdusuario());
									respCabGuar = scsCabeceraguardiasExtendsMapper.insertSelective(cabGuardia);
								}
							}
							
							if (respCabGuar != 0) {

								switch (saltoOcompensacion) {
								case "S":

									scgi.setIdPersona(idPersona);
									scgi.setIdGuardia(idGuardia);
									scgi.setIdTurno(idTurno);
									scgi.setFecha(fechaHoy);
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
									scgi.setFecha(fechaHoy);
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
									scgi.setFecha(fechaHoy);
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
										scgi.setFecha(fechaHoy);
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
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO sustituirGuardiaColeg(String[] datos, HttpServletRequest request) throws Exception {
		LOGGER.info("sustituirGuardiaColeg() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				String fechaHoy = formatter.format(new Date());

				String idTurno = datos[0];
				String idGuardia = datos[1];
				Long fechadesde = Long.parseLong(datos[2]);
				String idPersona = datos[3];
				String newLetrado = datos[4];
				String fechaSustitucion = datos[5];
				String comensustitucion = datos[6];
				String saltoOcompensacion = datos[7];
				String calendarioGuardias = datos[8];
				Boolean borrarGuardiaSustituida = Boolean.parseBoolean(datos[10]);

				//-------------------------------------------------------------------------------------------------------
				// Obtenemos todas las guardias del letrado que solicita la sustitucion(saliente) para las que la fecha
				// de inicio sea mayor que la actual. La informacion se saca de la tabla SCS_CABECERAGUARDIAS
				//
				//-------------------------------------------------------------------------------------------------------

				ScsCabeceraguardiasExample cabeceraguardiasExample = new ScsCabeceraguardiasExample();
				cabeceraguardiasExample.createCriteria()
						.andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.parseInt(idTurno))
						.andIdguardiaEqualTo(Integer.parseInt(idGuardia))
						.andIdpersonaEqualTo(Long.parseLong(idPersona))
						.andFechainicioEqualTo(new Date(fechadesde));

				List<ScsCabeceraguardias> cabeceraguardias = scsCabeceraguardiasExtendsMapper.selectByExample(cabeceraguardiasExample);
				if (cabeceraguardias != null) {
					CenBajastemporalesExample bajastemporalesExample = new CenBajastemporalesExample();
					bajastemporalesExample.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.parseLong(idPersona))
							.andValidadoEqualTo("1");

					//List<CenBajastemporales> bajastemporales = cenBajastemporalesMapper.selectByExample(bajastemporalesExample);
					//bajastemporales.stream().map(bt -> bt.getfecha)

					for (ScsCabeceraguardias scsCabeceraguardias : cabeceraguardias) {
						sustitucionLetradoGuardiaPuntual(scsCabeceraguardias.getIdinstitucion(), usuarios.get(0),
								fechaHoy, scsCabeceraguardias.getFechainicio(), scsCabeceraguardias.getFechaFin(), scsCabeceraguardias.getIdturno(), scsCabeceraguardias.getIdguardia(),
								Long.parseLong(idPersona), Long.parseLong(newLetrado), new Date(Long.parseLong(fechaSustitucion)), comensustitucion, saltoOcompensacion,
								Integer.parseInt(calendarioGuardias), borrarGuardiaSustituida);
					}
				}
			}
		}

		error.setCode(200);
		updateResponseDTO.setError(error);
		updateResponseDTO.setStatus(SigaConstants.OK);

		LOGGER.info("sustituirGuardiaColeg() -> Salida del servicio para sustituir letrados");
		return updateResponseDTO;
	}

	private void sustitucionLetradoGuardiaPuntual(Short idInstitucion, AdmUsuarios usuario, String fechaHoy, Date fechaInicio, Date fechaFin, Integer idTurno,
												  Integer idGuardia, Long idPersona, Long newLetrado, Date fechaSustitucion, String comensustitucion,
												  String saltoOcompensacion, Integer calendarioGuardias, Boolean moverAsistencias) {
		//-----------------------------------------------------------------------------------------------------
		//  Comprobamos si el letrado entrante cumple los criteriosde separación de guardias y de incompatibilidades
		//------------------------------------------------------------------------------------------------------

		//-----------------------------------------------------------------------------------------------------
		// Recuperamos todas las permutas en las que el saliente sea o confirmador o solicitante. Posteriormente
		// borraremos dichas permutas y volveremos a insertar aquellas para las que fecha de confirmacion sea
		// distinta de null
		//-----------------------------------------------------------------------------------------------------
		
		ScsPermutaguardiasExample permutaComoSolicitanteExample = new ScsPermutaguardiasExample();
		permutaComoSolicitanteExample.createCriteria()
				.andIdinstitucionEqualTo(idInstitucion)
				.andIdguardiaSolicitanteEqualTo(idGuardia)
				.andIdturnoSolicitanteEqualTo(idTurno)
				.andIdcalendarioguardiasSolicitanEqualTo(calendarioGuardias)
				.andFechainicioSolicitanteEqualTo(fechaInicio)
				.andIdpersonaSolicitanteEqualTo(idPersona);
		List<ScsPermutaguardias> permutaComoSolicitante = scsPermutaguardiasMapper.selectByExample(permutaComoSolicitanteExample);

		ScsPermutaguardiasExample permutaComoConfirmadorExample = new ScsPermutaguardiasExample();
		permutaComoConfirmadorExample.createCriteria()
				.andIdinstitucionEqualTo(idInstitucion)
				.andIdguardiaConfirmadorEqualTo(idGuardia)
				.andIdturnoConfirmadorEqualTo(idTurno)
				.andIdcalendarioguardiasConfirmadEqualTo(calendarioGuardias)
				.andFechainicioConfirmadorEqualTo(fechaInicio)
				.andIdpersonaConfirmadorEqualTo(idPersona);
		List<ScsPermutaguardias> permutaComoConfirmador = scsPermutaguardiasMapper.selectByExample(permutaComoConfirmadorExample);


		//----------------------------------------------------------------------------------------------
		// guardamos los registros de cabecera de guardias y guardias colegiado para el colegiado saliente
		//
		//----------------------------------------------------------------------------------------------

		ScsCabeceraguardiasKey cabeceraGuardiaSalienteKey = new ScsCabeceraguardiasKey();
		cabeceraGuardiaSalienteKey.setIdinstitucion(idInstitucion);
		cabeceraGuardiaSalienteKey.setIdturno(idTurno);
		cabeceraGuardiaSalienteKey.setIdguardia(idGuardia);
		cabeceraGuardiaSalienteKey.setIdpersona(idPersona);
		cabeceraGuardiaSalienteKey.setFechainicio(fechaInicio);
		ScsCabeceraguardias cabeceraGuardiaSaliente = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabeceraGuardiaSalienteKey);

		ScsGuardiascolegiadoExample guardiasColegiadoExample = new ScsGuardiascolegiadoExample();
		guardiasColegiadoExample.createCriteria()
				.andIdinstitucionEqualTo(idInstitucion)
				.andIdturnoEqualTo(idTurno)
				.andIdguardiaEqualTo(idGuardia)
				.andIdpersonaEqualTo(idPersona)
				.andFechainicioEqualTo(fechaInicio);
		List<ScsGuardiascolegiado> guardiasColegiadoSaliente = scsGuardiascolegiadoExtendsMapper.selectByExample(guardiasColegiadoExample);

		//-----------------------------------------------------------------------------
		//  Borramos los registros de las permutas obtenidas para el saliente tanto aquellas en las que actua como
		// solicitante como las que actua como confirmador
		//-----------------------------------------------------------------------------

		if (permutaComoSolicitante != null) {
			for (ScsPermutaguardias permGuarComoSol : permutaComoSolicitante) {
				scsPermutaguardiasMapper.deleteByPrimaryKey(permGuarComoSol);
			}
		}

		if (permutaComoConfirmador != null) {
			for (ScsPermutaguardias permGuarComoConf : permutaComoConfirmador) {
				scsPermutaguardiasMapper.deleteByPrimaryKey(permGuarComoConf);
			}
		}

		ScsPermutaCabeceraExample permutaCabeceraExample = new ScsPermutaCabeceraExample();
		permutaCabeceraExample.createCriteria()
			.andIdinstitucionEqualTo(idInstitucion)
			.andIdguardiaEqualTo(idGuardia)
			.andIdturnoEqualTo(idTurno)
			.andIdpersonaEqualTo(idPersona)
			.andFechaEqualTo(fechaInicio);

		// Realiza los cambios previos a la sustitucion de una guardia para SCS_PERMUTA_CABECERA
		List<ScsPermutaCabecera> permutaCabeceras = scsPermutaCabeceraMapper.selectByExample(permutaCabeceraExample);
		if (permutaCabeceras != null) {
			for (ScsPermutaCabecera permuta: permutaCabeceras) {
				permuta.setIdpersona(null);
				scsPermutaCabeceraMapper.updateByPrimaryKey(permuta);
			}
		}

		if (moverAsistencias) {
			//----------------------------------------------------------------------------------------------------
			// Borramos los registros de la tabla SCS_GUARDIASCOLEGIADO para el letrado saliente
			//-----------------------------------------------------------------------------------------------------

			for (ScsGuardiascolegiado guardiascolegiado: guardiasColegiadoSaliente){
				scsGuardiascolegiadoExtendsMapper.deleteByPrimaryKey(guardiascolegiado);
				try {
					this.guardiasServiceImpl.triggerGuardiaColegiadoAID(guardiascolegiado, 2);
				} catch (Exception e) {
					LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 2 (delete)");
				}
				
			}

			//----------------------------------------------------------------------------------------------------
			// Borramos el registro de la tabla SCS_CABECERAGUARDIAS para el letrado saliente
			//-----------------------------------------------------------------------------------------------------

			scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabeceraGuardiaSaliente);
		}

		//---------------------------------------------------------------------------------------------------
		// Insertamos el registro antes obtenido de la tabla SCS_CABECERAGUARDIAS cambiando el idpersona
		// (correspondiente al saliente) por le idepersona del entrante
		//---------------------------------------------------------------------------------------------------

		cabeceraGuardiaSaliente.setIdpersona(newLetrado);
		cabeceraGuardiaSaliente.setLetradosustituido(idPersona);
		cabeceraGuardiaSaliente.setFechasustitucion(fechaSustitucion);
		cabeceraGuardiaSaliente.setComensustitucion(UtilidadesString.esCadenaVacia(comensustitucion)
				? "Letrado sustituido" : comensustitucion);
		cabeceraGuardiaSaliente.setFechaalta(new Date());
		cabeceraGuardiaSaliente.setUsualta(usuario.getIdusuario());

		// Antes de insertar el registro se comprueba si el letrado ya tiene una guardia en ese turno y periodo
		ScsCabeceraguardiasExample existeGuardiaExample = new ScsCabeceraguardiasExample();
		existeGuardiaExample.createCriteria()
				.andLetradosustituidoEqualTo(idPersona)
				.andIdinstitucionEqualTo(idInstitucion)
				.andIdturnoEqualTo(idTurno)
				.andIdguardiaEqualTo(idGuardia)
				.andFechainicioGreaterThanOrEqualTo(fechaInicio)
				.andFechaFinLessThanOrEqualTo(cabeceraGuardiaSaliente.getFechaFin());

		List<ScsCabeceraguardias> sutitucionesAnteriores = scsCabeceraguardiasExtendsMapper.selectByExample(existeGuardiaExample);
		if (sutitucionesAnteriores.size() > 0) {
			cabeceraGuardiaSaliente.setComensustitucion("Sustituido previamente " + idPersona);
			scsCabeceraguardiasExtendsMapper.deleteByExample(existeGuardiaExample);
		}

		scsCabeceraguardiasExtendsMapper.insert(cabeceraGuardiaSaliente);

		//---------------------------------------------------------------------------------------------------
		// Insertamos los registros antes obtenidos de la tabla SCS_GUARDIASCOLEGIADO cambiando el idpersona
		// (correspondiente al saliente) por el idepersona del entrante
		//---------------------------------------------------------------------------------------------------

		if (guardiasColegiadoSaliente != null) {
			for (ScsGuardiascolegiado guardiascolegiado: guardiasColegiadoSaliente) {
				guardiascolegiado.setIdpersona(newLetrado);
				scsGuardiascolegiadoExtendsMapper.insert(guardiascolegiado);
				try {
					this.guardiasServiceImpl.triggerGuardiaColegiadoAID(guardiascolegiado, 1);
				} catch (Exception e) {
					LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 1 (insert)");
				}
			}
		}

		//----------------------------------------------------------------------------------------------------
		// Insertamos los registros de permutas obtenidos anteriormente para el saliente como confirmador y
		// solicitante cambiando le idpersona por el del letrado entrante.Solo insertamos aquellos regstros
		// cuya fecha de confirmación no sea null (las peticiones de permuta las desechamos, solo interesan permutas confirmadas)
		//---------------------------------------------------------------------------------------------------

		// Realiza los cambios posteriores a la sustituacion de una guardia para SCS_PERMUTA_CABECERA
		for (ScsPermutaCabecera permutaCabecera: permutaCabeceras) {
			permutaCabecera.setIdpersona(newLetrado);
			permutaCabecera.setUsumodificacion(usuario.getIdusuario());
			permutaCabecera.setFechamodificacion(new Date());
			scsPermutaCabeceraMapper.updateByPrimaryKey(permutaCabecera);
		}

		for (ScsPermutaguardias permuta: permutaComoConfirmador){
			if (permuta.getFechaconfirmacion() != null) {
				permuta.setIdpersonaSolicitante(newLetrado);
				scsPermutaguardiasMapper.insert(permuta);
			}
		}

		for (ScsPermutaguardias permuta: permutaComoConfirmador){
			if (permuta.getFechaconfirmacion() != null) {
				permuta.setIdpersonaConfirmador(newLetrado);
				//numero mayor al más alto de las permutas por ahora
				permuta.setNumero(Long.valueOf(scsPermutaguardiasExtendsMapper.maxIdPermutaGuardia(String.valueOf(idInstitucion)))+1);
				scsPermutaguardiasMapper.insert(permuta);
			}
		}

		//--------------------------------------------------------------------------------------------------
		// Incluimos saltos (al entrante) y compensaciones  (al saliente) en funcion de los checks correspondientes
		//--------------------------------------------------------------------------------------------------

		if ("S/C".equals(saltoOcompensacion) || "S".equals(saltoOcompensacion)) {
			SaltoCompGuardiaItem scgi = new SaltoCompGuardiaItem();
			scgi.setIdPersona(newLetrado.toString());
			scgi.setIdGuardia(idGuardia.toString());
			scgi.setIdTurno(idTurno.toString());
			scgi.setFecha(fechaHoy);
			scgi.setSaltoCompensacion("S");
			scgi.setMotivo("Sustitución");

			MaxIdDto nuevoId = scsSaltoscompensacionesExtendsMapper.selectNuevoIdSaltosCompensaciones(scgi, idInstitucion.toString());

			 int respSalto = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(
					scgi, idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);
		}

		if ("S/C".equals(saltoOcompensacion) || "C".equals(saltoOcompensacion)) {
			SaltoCompGuardiaItem scgi = new SaltoCompGuardiaItem();
			scgi.setIdPersona(idPersona.toString());
			scgi.setIdGuardia(idGuardia.toString());
			scgi.setIdTurno(idTurno.toString());
			scgi.setFecha(fechaHoy);
			scgi.setSaltoCompensacion("C");
			scgi.setMotivo("Sustitución");

			MaxIdDto nuevoId = scsSaltoscompensacionesExtendsMapper.selectNuevoIdSaltosCompensaciones(scgi, idInstitucion.toString());

			int	respSalto = scsSaltoscompensacionesExtendsMapper.guardarSaltosCompensaciones(
					scgi, idInstitucion.toString(), Long.toString(nuevoId.getIdMax()), usuario);
		}

		if (moverAsistencias) {
			//-------------------------------------------------------------------------------------------------
			// Actualizamos la tabla de asistencias cambiando en dicha tabla el idpersona del saliente por el
			//idpersona del entrante. Actualizamos aquellas asistencias para las que la fecha de realizacion sea
			// igual al campo fechafin de cada uno de los registros de la tabla gusrdiascolegiado
			//-------------------------------------------------------------------------------------------------

			ScsAsistenciaExample asistenciasExample = new ScsAsistenciaExample();
			asistenciasExample.createCriteria()
					.andIdinstitucionEqualTo(idInstitucion)
					.andIdpersonacolegiadoEqualTo(idPersona)
					.andFechahoraBetween(startOfDay(cabeceraGuardiaSaliente.getFechainicio()), endOfDay(cabeceraGuardiaSaliente.getFechaFin()));

			List<ScsAsistencia> asistencias = scsAsistenciaExtendsMapper.selectByExample(asistenciasExample);
			for (ScsAsistencia asistencia: asistencias) {
				asistencia.setIdpersonacolegiado(newLetrado);
				scsAsistenciaExtendsMapper.updateByPrimaryKey(asistencia);
			}

			cabeceraGuardiaSaliente.setFechavalidacion(new Date());
			scsCabeceraguardiasExtendsMapper.validarSolicitudGuardia(cabeceraGuardiaSaliente);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void validarGuardiaColegiado(ScsCabeceraguardias guardiaKey) {
		ScsCabeceraguardias guardia = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(guardiaKey);
		if (guardia != null) {
			ScsCabeceraguardiasKey cabeceraGuardiaSalienteKey = new ScsCabeceraguardiasKey();
			cabeceraGuardiaSalienteKey.setIdinstitucion(guardia.getIdinstitucion());
			cabeceraGuardiaSalienteKey.setIdturno(guardia.getIdturno());
			cabeceraGuardiaSalienteKey.setIdguardia(guardia.getIdguardia());
			cabeceraGuardiaSalienteKey.setIdpersona(guardia.getLetradosustituido());
			cabeceraGuardiaSalienteKey.setFechainicio(guardia.getFechainicio());
			ScsCabeceraguardias cabeceraGuardiaSaliente = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabeceraGuardiaSalienteKey);

			if (guardia.getLetradosustituido() != null) {
				ScsGuardiascolegiadoExample guardiasColegiadoExample = new ScsGuardiascolegiadoExample();
				guardiasColegiadoExample.createCriteria()
						.andIdinstitucionEqualTo(guardia.getIdinstitucion())
						.andIdturnoEqualTo(guardia.getIdturno())
						.andIdguardiaEqualTo(guardia.getIdguardia())
						.andIdpersonaEqualTo(guardia.getLetradosustituido())
						.andFechainicioEqualTo(guardia.getFechainicio());
				List<ScsGuardiascolegiado> guardiasColegiadoSaliente = scsGuardiascolegiadoExtendsMapper.selectByExample(guardiasColegiadoExample);

				//----------------------------------------------------------------------------------------------------
				// Borramos los registros de la tabla SCS_GUARDIASCOLEGIADO para el letrado saliente
				//-----------------------------------------------------------------------------------------------------

				guardiasColegiadoSaliente.forEach(guardiaColegiado -> {
					scsGuardiascolegiadoExtendsMapper.deleteByPrimaryKey(guardiaColegiado);
					try {
						this.guardiasServiceImpl.triggerGuardiaColegiadoAID(guardiaColegiado, 2);
					} catch (Exception e) {
						LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 2 (delete)");
					}
				}); 

				//----------------------------------------------------------------------------------------------------
				// Borramos el registro de la tabla SCS_CABECERAGUARDIAS para el letrado saliente
				//-----------------------------------------------------------------------------------------------------

				if (cabeceraGuardiaSaliente != null) {
					scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabeceraGuardiaSaliente);
				}

				//-------------------------------------------------------------------------------------------------
				// Actualizamos la tabla de asistencias cambiando en dicha tabla el idpersona del saliente por el
				//idpersona del entrante. Actualizamos aquellas asistencias para las que la fecha de realizacion sea
				// igual al campo fechafin de cada uno de los registros de la tabla gusrdiascolegiado
				//-------------------------------------------------------------------------------------------------

				ScsAsistenciaExample asistenciasExample = new ScsAsistenciaExample();
				asistenciasExample.createCriteria()
						.andIdinstitucionEqualTo(guardia.getIdinstitucion())
						.andIdpersonacolegiadoEqualTo(guardia.getLetradosustituido())
						.andFechahoraBetween(startOfDay(guardia.getFechainicio()), endOfDay(guardia.getFechaFin()));

				List<ScsAsistencia> asistencias = scsAsistenciaExtendsMapper.selectByExample(asistenciasExample);
				asistencias.stream().peek(a -> a.setIdpersonacolegiado(guardia.getIdpersona()))
						.forEach(scsAsistenciaExtendsMapper::updateByPrimaryKey);
			}
		}
		scsCabeceraguardiasExtendsMapper.validarSolicitudGuardia(guardiaKey);
	}

	@Override
	public ResponseDataDTO existeFacturacionGuardiaColegiado(String[] datos, HttpServletRequest request) {
		LOGGER.info("existeFacturacionGuardiaColegiado() ->  Entrada al servicio para comprobar las facturaciones de guardia colegiado");

		ResponseDataDTO responseDataDTO = new ResponseDataDTO();
		Error error = new Error();
		responseDataDTO.setError(error);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String idTurno = datos[0];
		String idGuardia = datos[1];
		Long fechadesde = Long.parseLong(datos[2]);
		String idPersona = datos[3];
		Long fechahasta = Long.parseLong(datos[9]);

		ScsGuardiascolegiadoKey guardiascolegiadoKey = new ScsGuardiascolegiadoKey();
		guardiascolegiadoKey.setIdinstitucion(idInstitucion);
		guardiascolegiadoKey.setIdturno(Integer.parseInt(idTurno));
		guardiascolegiadoKey.setIdguardia(Integer.parseInt(idGuardia));
		guardiascolegiadoKey.setIdpersona(Long.parseLong(idPersona));
		guardiascolegiadoKey.setFechainicio(new Date(fechadesde));
		guardiascolegiadoKey.setFechafin(new Date(fechahasta));

		ScsGuardiascolegiado guardiascolegiado = scsGuardiascolegiadoExtendsMapper.selectByPrimaryKey(guardiascolegiadoKey);

		if (guardiascolegiadoKey == null) {
			error.setCode(200);
			responseDataDTO.setData("0");
			return responseDataDTO;
		}

		FcsFactGuardiascolegiadoExample factExample = new FcsFactGuardiascolegiadoExample();
		factExample.createCriteria()
				.andIdinstitucionEqualTo(guardiascolegiado.getIdinstitucion())
				.andIdturnoEqualTo(guardiascolegiado.getIdturno())
				.andIdguardiaEqualTo(guardiascolegiado.getIdguardia())
				.andIdpersonaEqualTo(guardiascolegiado.getIdpersona())
				.andFechainicioEqualTo(guardiascolegiado.getFechainicio())
				.andFechafinEqualTo(guardiascolegiado.getFechafin());

		Long numAsistencias = fcsFactGuardiascolegiadoMapper.countByExample(factExample);
		LOGGER.info("existeFacturacionGuardiaColegiado() ->  Num. facturaciones: " + numAsistencias);
		error.setCode(200);
		responseDataDTO.setData(numAsistencias.toString());

		LOGGER.info("existeFacturacionGuardiaColegiado() ->  Saliendo del servicio para comprobar las facturaciones de guardia colegiado");
		return responseDataDTO;
	}

	@Override
	public ResponseDataDTO existeAsistenciasGuardiaColegiado(String[] datos, HttpServletRequest request) {
		LOGGER.info("existeAsistenciasGuardiaColegiado() ->  Entrada al servicio para comprobar las asistencias de guardia colegiado");

		ResponseDataDTO responseDataDTO = new ResponseDataDTO();
		Error error = new Error();
		responseDataDTO.setError(error);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String idTurno = datos[0];
		String idGuardia = datos[1];
		Long fechadesde = Long.parseLong(datos[2]);
		String idPersona = datos[3];
		Long fechahasta = Long.parseLong(datos[9]);

		ScsGuardiascolegiadoKey guardiascolegiadoKey = new ScsGuardiascolegiadoKey();
		guardiascolegiadoKey.setIdinstitucion(idInstitucion);
		guardiascolegiadoKey.setIdturno(Integer.parseInt(idTurno));
		guardiascolegiadoKey.setIdguardia(Integer.parseInt(idGuardia));
		guardiascolegiadoKey.setIdpersona(Long.parseLong(idPersona));
		guardiascolegiadoKey.setFechainicio(new Date(fechadesde));
		guardiascolegiadoKey.setFechafin(new Date(fechahasta));

		ScsGuardiascolegiado guardiascolegiado = scsGuardiascolegiadoExtendsMapper.selectByPrimaryKey(guardiascolegiadoKey);

		if (guardiascolegiadoKey == null) {
			error.setCode(200);
			responseDataDTO.setData("0");
			return responseDataDTO;
		}

		ScsAsistenciaExample asistenciasExample = new ScsAsistenciaExample();
		asistenciasExample.createCriteria()
				.andIdinstitucionEqualTo(guardiascolegiado.getIdinstitucion())
				.andIdpersonacolegiadoEqualTo(guardiascolegiado.getIdpersona())
				.andFechahoraBetween(startOfDay(guardiascolegiado.getFechainicio()), endOfDay(guardiascolegiado.getFechafin()));

		Long numAsistencias = scsAsistenciaExtendsMapper.countByExample(asistenciasExample);
		LOGGER.info("existeAsistenciasGuardiaColegiado() ->  Num. asistencias: " + numAsistencias);
		error.setCode(200);
		responseDataDTO.setData(numAsistencias.toString());

		LOGGER.info("existeAsistenciasGuardiaColegiado() ->  Saliendo del servicio para comprobar las asistencias de guardia colegiado");
		return responseDataDTO;
	}

	private Date startOfDay(Date fecha) {
		return Date.from(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(LocalTime.MIN)
				.atZone(ZoneId.systemDefault()).toInstant());
	}

	private Date endOfDay(Date fecha) {
		return Date.from(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(LocalTime.MAX)
				.atZone(ZoneId.systemDefault()).toInstant());
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
				permItem.setIdpersona(permutaItem.getIdpersona());
				permItem.setFechainicioSolicitante(permutaItem.getFechasolicitud());

				List<PermutaItem> permutas = scsPermutaguardiasExtendsMapper.getPermutasGuardiaColeg(permItem, idInstitucion);

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
		Date hoy = new Date();
		String fechaInscrito = hoy.toString();

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
	
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@INICIO
	@Override
	public Date getFechaSolicitante(String idPersona, Short idCalendarioGuardias, Short idGuardia, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		ComboDTO comboDTO = new ComboDTO();
//		List<ComboItem> comboItems = null;
		Date fechaConfirmacion = new Date();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getFechaConfirmacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getFechaConfirmacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getFechaConfirmacion() / scsTurnosextendsMapper.getResoluciones() -> Entrada a scsImpugnacionEjgextendsMapper para obtener los combo");

//				comboItems = scsPermutaguardiasExtendsMapper.getTurnoInscrito(Long.parseLong(idPersona), idInstitucion);
				List <Date> listFechas = scsPermutaguardiasExtendsMapper.getFechaSolicitanteInicio(idPersona, idCalendarioGuardias, idGuardia, idInstitucion);

				LOGGER.info(
						"getFechaConfirmacion() / scsTurnosextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (listFechas != null && !listFechas.isEmpty()) {
					fechaConfirmacion = listFechas.get(0);
				}
			}
		}
		LOGGER.info("getFechaConfirmacion() -> Salida del servicio para obtener los tipos ejg");
		return fechaConfirmacion;
	}
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@FIN 

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
						// creacion de copias
						// se almacenan la fecha y motivos del confirmador en scs_permutaguardias

						LOGGER.info("validarPermuta() / modificacion de scs_permutaguardia");

						// creacion de la copia de cabecera guardia
						ScsPermutaguardiasKey permutaguardia = new ScsPermutaguardiasKey();
						permutaguardia.setIdinstitucion(idInstitucion);
						permutaguardia.setNumero(perm.getNumero());
						ScsPermutaguardias permutaGuardiaCopy = scsPermutaguardiasExtendsMapper.selectByPrimaryKey(permutaguardia);

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

						ScsCabeceraguardias cabGuardiaSolCopy = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabGuardiaSol);

						//SIGARNV-2885@DTT.JAMARTIN@14/02/2023@INICIO
						// Comprobamos que el letrado solicitante no tenga asignado la guardia del
						// letrado confirmador previamente
						ScsCabeceraguardiasKey cabGuardiaSolConf = new ScsCabeceraguardiasKey();
						cabGuardiaSolConf.setIdinstitucion(idInstitucion);
						cabGuardiaSolConf.setIdguardia(perm.getIdguardiaConfirmador());
						cabGuardiaSolConf.setIdturno(perm.getIdturnoConfirmador());
						cabGuardiaSolConf.setIdpersona(perm.getIdpersonaSolicitante());
						cabGuardiaSolConf.setFechainicio(perm.getFechainicioConfirmador());

						ScsCabeceraguardias cabGuardiaConfdeSol = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabGuardiaSolConf);

						// Si el solicitante ya tiene asignada la guardia del confirmador no puede haber
						// permuta porque ya tiene ese dia
						if (cabGuardiaConfdeSol != null) {
							error.setCode(400);
							error.setDescription("El solicitante ya tiene asignada la guardia del confirmador para ese día.");
							updateResponseDTO.setStatus(SigaConstants.KO);
							throw (new Exception(
									"Error al permutar la guardia: El solicitante ya tiene asignada la guardia del confirmador para ese día."));
						}
						//SIGARNV-2885@DTT.JAMARTIN@14/02/2023@FIN 
						
						ScsGuardiascolegiadoKey guardiaSol = new ScsGuardiascolegiado();
						guardiaSol.setIdinstitucion(idInstitucion);
						guardiaSol.setIdguardia(cabGuardiaSolCopy.getIdguardia());
						guardiaSol.setIdturno(cabGuardiaSolCopy.getIdturno());
						guardiaSol.setIdpersona(cabGuardiaSolCopy.getIdpersona());
						guardiaSol.setFechainicio(cabGuardiaSolCopy.getFechainicio());
						guardiaSol.setFechafin(cabGuardiaSolCopy.getFechainicio());

						ScsGuardiascolegiado guardiaSolCopy = scsGuardiascolegiadoExtendsMapper.selectByPrimaryKey(guardiaSol);

						// copia de las guardias del confirmador
						ScsCabeceraguardiasKey cabGuardiaConf = new ScsCabeceraguardiasKey();
						cabGuardiaConf.setIdinstitucion(idInstitucion);
						cabGuardiaConf.setIdguardia(perm.getIdguardiaConfirmador());
						cabGuardiaConf.setIdturno(perm.getIdturnoConfirmador());
						cabGuardiaConf.setIdpersona(perm.getIdpersonaConfirmador());
						cabGuardiaConf.setFechainicio(perm.getFechainicioConfirmador());

						ScsCabeceraguardias cabGuardiaConfCopy = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(cabGuardiaConf);

						ScsGuardiascolegiadoKey guardiaConf = new ScsGuardiascolegiado();
						guardiaConf.setIdinstitucion(idInstitucion);
						guardiaConf.setIdguardia(cabGuardiaConfCopy.getIdguardia());
						guardiaConf.setIdturno(cabGuardiaConfCopy.getIdturno());
						guardiaConf.setIdpersona(cabGuardiaConfCopy.getIdpersona());
						guardiaConf.setFechainicio(cabGuardiaConfCopy.getFechainicio());
						guardiaConf.setFechafin(cabGuardiaConfCopy.getFechainicio());

						ScsGuardiascolegiado guardiaConfCopy = scsGuardiascolegiadoExtendsMapper.selectByPrimaryKey(guardiaConf);

						//SIGARNV-2885@DTT.JAMARTIN@14/02/2023@INICIO 
						/*
						 * Debido a que se tiene que eliminar los registros de forma secuencial siempre
						 * y cuando se haya eliminado el anterior se realiza comprobado que la
						 * eliminacion anterior se realiza antes de realizar la siguiente
						 */

						// Insertamos en SCS_CABECERAGUARDIAS el Sol
						if (error.getCode() == null) {
							LOGGER.info("validarPermuta() / insertando nueva cabecera guardia colegiado del solicitante");
//							ScsCabeceraguardias cabGuardiaSolAux = cabGuardiaConfCopy;
							ScsCabeceraguardias cabGuardiaSolAux = copyScsCabeceraguardias(cabGuardiaConfCopy);

							cabGuardiaSolAux.setIdpersona(perm.getIdpersonaSolicitante());
							cabGuardiaSolAux.setLetradosustituido(perm.getIdpersonaConfirmador());

							int insertCabGuarSol = scsCabeceraguardiasExtendsMapper.insert(cabGuardiaSolAux);

							// Insertamos en SCS_GUARDIASCOLEGIADO el Sol
							if (insertCabGuarSol != 0) {
								LOGGER.info("validarPermuta() / insertando nueva guardia colegiado del solicitante");
//								ScsGuardiascolegiado guardiaSolAux = guardiaConfCopy;
								ScsGuardiascolegiado guardiaSolAux = copyScsGuardiascolegiado(guardiaConfCopy);

								guardiaSolAux.setIdpersona(perm.getIdpersonaSolicitante());

								int insertGuarColSol = scsGuardiascolegiadoExtendsMapper.insert(guardiaSolAux);

								// Insertamos en SCS_CABECERAGUARDIAS el Conf
								if (insertGuarColSol != 0) {
									try {
										this.guardiasServiceImpl.triggerGuardiaColegiadoAID(guardiaSolAux, 1);
									} catch (Exception e) {
										LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 1 (insert)");
									}									
									LOGGER.info("validarPermuta() / insertando nueva cabecera guardia del confirmador");
//									ScsCabeceraguardias cabGuardiaConfAux = cabGuardiaSolCopy;
									ScsCabeceraguardias cabGuardiaConfAux = copyScsCabeceraguardias(cabGuardiaSolCopy);

									cabGuardiaConfAux.setIdpersona(perm.getIdpersonaConfirmador());
									cabGuardiaConfAux.setLetradosustituido(perm.getIdpersonaSolicitante());

									int insertCabGuarConf = scsCabeceraguardiasExtendsMapper.insert(cabGuardiaConfAux);

									// Insertamos en SCS_GUARDIASCOLEGIADO el Conf
									if (insertCabGuarConf != 0) {
										LOGGER.info(
												"validarPermuta() / insertando nueva guardia colegiado del confirmador");
//										ScsGuardiascolegiado guardiaConfAux = guardiaSolCopy;
										ScsGuardiascolegiado guardiaConfAux = copyScsGuardiascolegiado(guardiaSolCopy);

										guardiaConfAux.setIdpersona(perm.getIdpersonaConfirmador());

										int insertGuarColConf = scsGuardiascolegiadoExtendsMapper.insert(guardiaConfAux);

										// Actualizamos SCS_PERMUTAGUARDIAS con la nueva permuta
										if (insertGuarColConf != 0) {
											try {
												this.guardiasServiceImpl.triggerGuardiaColegiadoAID(guardiaConfAux, 1);
											} catch (Exception e) {
												LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 1 (insert)");
											}
											LOGGER.info("validarPermuta() / actualizando permuta guardia");
//											ScsPermutaguardias permutaGuardiaAux = permutaGuardiaCopy;
											ScsPermutaguardias permutaGuardiaAux = copyScsPermutaguardias(permutaGuardiaCopy);

											permutaGuardiaAux.setIdPerCabSolicitante(permutaGuardiaCopy.getIdPerCabConfirmador());
											permutaGuardiaAux.setIdPerCabConfirmador(permutaGuardiaCopy.getIdPerCabSolicitante());
											permutaGuardiaAux.setFechaconfirmacion(new Date());
											permutaGuardiaAux.setMotivosconfirmador(perm.getMotivos());
											permutaGuardiaAux.setNumero(perm.getNumero());

											int updatePermutaGuardia = scsPermutaguardiasExtendsMapper.updateByPrimaryKey(permutaGuardiaAux);

											// Actualizamos SCS_PERMUTA_CABECERA del Sol
											if (updatePermutaGuardia != 0) {
												LOGGER.info("validarPermuta() / actualizando permuta cabecera del solicitante");
//												ScsPermutaCabecera permCabSolCopyAux = permCabSolCopy;
												ScsPermutaCabecera permCabSolCopyAux = copyScsPermutaCabecera(permCabConfCopy);
												//permCabSolCopyAux.setFecha(permCabConfCopy.getFecha());
												permCabSolCopyAux.setIdpersona(permCabSolCopy.getIdpersona());
												permCabSolCopyAux.setIdPermutaCabecera(permCabSolCopy.getIdPermutaCabecera());
												int updatePerCabSol = scsPermutaCabeceraMapper.updateByPrimaryKey(permCabSolCopyAux);

												// Actualizamos SCS_PERMUTA_CABECERA del Conf
												if (updatePerCabSol != 0) {
													LOGGER.info("validarPermuta() / actualizando permuta cabecera del confirmador");
//													ScsPermutaCabecera permCabConfCopyAux = permCabConfCopy;
													ScsPermutaCabecera permCabConfCopyAux = copyScsPermutaCabecera(permCabSolCopy);
													//permCabConfCopyAux.setFecha(permCabSolCopy.getFecha());
													permCabConfCopyAux.setIdpersona(permCabConfCopy.getIdpersona());
													permCabConfCopyAux.setIdPermutaCabecera(permCabConfCopy.getIdPermutaCabecera());

													int updatePerCabConf = scsPermutaCabeceraMapper.updateByPrimaryKey(permCabConfCopyAux);

													// Borramos SCS_GUARDIASCOLEGIADO antigua del Sol
													if (updatePerCabConf != 0) {
														LOGGER.info("validarPermuta() / borrando guardia antigua del solicitante");
														guardiaSolCopy.setIdpersona(perm.getIdpersonaSolicitante());
//														int deleteGuardColSol = scsGuardiascolegiadoExtendsMapper.deleteByPrimaryKey(guardiaSolCopy);
														ScsGuardiascolegiadoExample scsGuardiascolegiadoSolExample = new ScsGuardiascolegiadoExample();
														scsGuardiascolegiadoSolExample.createCriteria()
																.andIdinstitucionEqualTo(guardiaSolCopy.getIdinstitucion())
																.andIdturnoEqualTo(guardiaSolCopy.getIdturno())
																.andIdguardiaEqualTo(guardiaSolCopy.getIdguardia())
																.andIdpersonaEqualTo(guardiaSolCopy.getIdpersona())
																.andFechainicioEqualTo(guardiaSolCopy.getFechainicio());
														
														int deleteGuardColSol = scsGuardiascolegiadoExtendsMapper.deleteByExample(scsGuardiascolegiadoSolExample);

														// Borramos SCS_GUARDIASCOLEGIADO antigua del Conf
														if (deleteGuardColSol != 0) {
															try {
																ScsGuardiascolegiado wrapperGuardiasColegiado = new ScsGuardiascolegiado();
																wrapperGuardiasColegiado.setIdinstitucion(guardiaSolCopy.getIdinstitucion());
																wrapperGuardiasColegiado.setIdpersona(guardiaSolCopy.getIdpersona());
																wrapperGuardiasColegiado.setIdturno(guardiaSolCopy.getIdturno());
																wrapperGuardiasColegiado.setIdguardia(guardiaSolCopy.getIdguardia());
																wrapperGuardiasColegiado.setFechainicio(guardiaSolCopy.getFechainicio());
																wrapperGuardiasColegiado.setFechafin(guardiaSolCopy.getFechafin());
																this.guardiasServiceImpl.triggerGuardiaColegiadoAID(wrapperGuardiasColegiado, 2);
															} catch (Exception e) {
																LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 2 (delete)");
															}
															LOGGER.info("validarPermuta() / borrando guardia antigua del confirmador");
															guardiaConfCopy.setIdpersona(perm.getIdpersonaConfirmador());
//															int deleteGuardColConf = scsGuardiascolegiadoExtendsMapper.deleteByPrimaryKey(guardiaConfCopy);
															ScsGuardiascolegiadoExample scsGuardiascolegiadoConfExample = new ScsGuardiascolegiadoExample();
															scsGuardiascolegiadoConfExample.createCriteria()
																	.andIdinstitucionEqualTo(guardiaConfCopy.getIdinstitucion())
																	.andIdturnoEqualTo(guardiaConfCopy.getIdturno())
																	.andIdguardiaEqualTo(guardiaConfCopy.getIdguardia())
																	.andIdpersonaEqualTo(guardiaConfCopy.getIdpersona())
																	.andFechainicioEqualTo(guardiaConfCopy.getFechainicio());
															
															int deleteGuardColConf = scsGuardiascolegiadoExtendsMapper.deleteByExample(scsGuardiascolegiadoConfExample);

															// Borramos SCS_CABECERAGUARDIAS antigua del Sol
															if (deleteGuardColConf != 0) {
																try {
																	ScsGuardiascolegiado wrapperGuardiasColegiado = new ScsGuardiascolegiado();
																	wrapperGuardiasColegiado.setIdinstitucion(guardiaConfCopy.getIdinstitucion());
																	wrapperGuardiasColegiado.setIdpersona(guardiaConfCopy.getIdpersona());
																	wrapperGuardiasColegiado.setIdturno(guardiaConfCopy.getIdturno());
																	wrapperGuardiasColegiado.setIdguardia(guardiaConfCopy.getIdguardia());
																	wrapperGuardiasColegiado.setFechainicio(guardiaConfCopy.getFechainicio());
																	wrapperGuardiasColegiado.setFechafin(guardiaConfCopy.getFechafin());
																	this.guardiasServiceImpl.triggerGuardiaColegiadoAID(wrapperGuardiasColegiado, 2);
																} catch (Exception e) {
																	LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 2 (delete)");
																}
																LOGGER.info("validarPermuta() / borrando cabecera guardia antigua del solicitante");
																cabGuardiaSolCopy.setIdpersona(perm.getIdpersonaSolicitante());
																int deleteCabGuardSol = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardiaSolCopy);

																// Borramos SCS_CABECERAGUARDIAS antigua del Conf
																if (deleteCabGuardSol != 0) {
																	LOGGER.info("validarPermuta() / borrando cabecera guardia antigua del confirmador");
																	cabGuardiaConfCopy.setIdpersona(perm.getIdpersonaConfirmador());
																	responseUpdate = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(cabGuardiaConfCopy);

																	// Si la ultima sentencia se hace correctamente se
																	// ha completado la permuta
																	LOGGER.info("validarPermuta() / permuta realizada con éxito");
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
					//SIGARNV-2885@DTT.JAMARTIN@14/02/2023@FIN 
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
//		int responseSol;
//		int responseConf;
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
				LOGGER.info(
						"permutarGuardia() / Entrada en la recopilacion de datos necesarios para realizar la insercion del registro");
				
				//SIGARNV-2885@DTT.JAMARTIN@09/02/2023@INICIO
				// Comprobamos que el letrado Solicitante previamente no tenga asignada la guardia del letrado Confirmador
				Date fechaActual = new Date();
				boolean guardiaRepetida = false;

				// Comprobamos que la fecha con la que vamos a permutar sea una fecha futura, no podemos permutar fechas de guardias presente-pasado
				if (permutaItem.getFechainicioSolicitante().after(fechaActual)
						&& permutaItem.getFechainicioConfirmador().after(fechaActual)) {

					// Comprobamos que la guardia no este facturada
					// Consultamos FCS_FACT_APUNTE
					FcsFactApunteExample fcsFactApunteExample = new FcsFactApunteExample();
					fcsFactApunteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(permutaItem.getIdpersonaSolicitante())
							.andIdturnoEqualTo(permutaItem.getIdturnoSolicitante())
							.andIdguardiaEqualTo(permutaItem.getIdguardiaSolicitante())
							.andFechainicioEqualTo(permutaItem.getFechainicioSolicitante());

					LOGGER.info("Consulto FCS_FACT_APUNTE solicitante");
					List<FcsFactApunte> listGuardiaFacturada = fcsFactApunteMapper.selectByExample(fcsFactApunteExample);

					// Si hay registros en FCS_FACT_APUNTE la guardia ya fue facturada no puede permutarla
					if (listGuardiaFacturada.size() == 0) {

						// Consulta a SCS_CABECERAGUARDIAS
						// Comprobamos solicitante
						ScsCabeceraguardiasExample scsCabeceraguardiasExample = new ScsCabeceraguardiasExample();
						scsCabeceraguardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(permutaItem.getIdpersonaSolicitante())
								.andIdturnoEqualTo(permutaItem.getIdturnoConfirmador())
								.andIdguardiaEqualTo(permutaItem.getIdguardiaConfirmador())
								.andFechainicioEqualTo(permutaItem.getFechainicioConfirmador());
						
						LOGGER.info("Consulto SCS_CABECERAGUARDIAS solicitante");
						List<ScsCabeceraguardias> listCabGuardSol = scsCabeceraguardiasExtendsMapper.selectByExample(scsCabeceraguardiasExample);

						if (listCabGuardSol.size() == 0) {
							// Comprobamos confirmador
							scsCabeceraguardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(permutaItem.getIdpersonaConfirmador())
									.andIdturnoEqualTo(permutaItem.getIdturnoSolicitante())
									.andIdguardiaEqualTo(permutaItem.getIdguardiaSolicitante())
									.andFechainicioEqualTo(permutaItem.getFechainicioSolicitante());
							
							LOGGER.info("Consulto SCS_CABECERAGUARDIAS confirmador");
							List<ScsCabeceraguardias> listCabGuardConf = scsCabeceraguardiasExtendsMapper.selectByExample(scsCabeceraguardiasExample);

							if (listCabGuardConf.size() == 0) {
								// Consulta a SCS_GUARDIASCOLEGIADO
								// Comprobamos solicitante
								ScsGuardiascolegiadoExample scsGuardiascolegiadoExample = new ScsGuardiascolegiadoExample();
								scsGuardiascolegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdpersonaEqualTo(permutaItem.getIdpersonaSolicitante())
										.andIdturnoEqualTo(permutaItem.getIdturnoConfirmador())
										.andIdguardiaEqualTo(permutaItem.getIdguardiaConfirmador())
										.andFechainicioEqualTo(permutaItem.getFechainicioConfirmador());
								
								LOGGER.info("Consulto SCS_GUARDIASCOLEGIADO solicitante");
								List<ScsGuardiascolegiado> listTuardiaColegiSol = scsGuardiascolegiadoExtendsMapper.selectByExample(scsGuardiascolegiadoExample);

								if (listTuardiaColegiSol.size() == 0) {
									// Comprobamos confirmador
									scsGuardiascolegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
											.andIdpersonaEqualTo(permutaItem.getIdpersonaConfirmador())
											.andIdturnoEqualTo(permutaItem.getIdturnoSolicitante())
											.andIdguardiaEqualTo(permutaItem.getIdguardiaSolicitante())
											.andFechainicioEqualTo(permutaItem.getFechainicioSolicitante());
									
									LOGGER.info("Consulto SCS_GUARDIASCOLEGIADO confirmador");
									List<ScsGuardiascolegiado> listTuardiaColegiConf = scsGuardiascolegiadoExtendsMapper.selectByExample(scsGuardiascolegiadoExample);

									if (listTuardiaColegiConf.size() != 0) {
										guardiaRepetida = true;
									}
								} else {
									guardiaRepetida = true;
								}
							} else {
								guardiaRepetida = true;
							}
						} else {
							guardiaRepetida = true;
						}
					} else {
						guardiaRepetida = true;
					}
				} else {
					guardiaRepetida = true;
				}

				if (guardiaRepetida) {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.info(
							"permutarGuardia() / -> No se puede crear la permuta porque el colegiado ya tiene asociada la guardia para el mismo día.");
					throw (new Exception("Error al permutar la guardia"));
				}
				//SIGARNV-2885@DTT.JAMARTIN@09/02/2023@FIN 

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
				
				//SIGARNV-2885@DTT.JAMARTIN@01/02/2023@INICIO
//				idPermcabSol = scsPermutaCabeceraExtendsMapper.maxIdPermutaCabecera(idInstitucion.toString());
				LOGGER.info("permutarGuardia() / obtiene id de la permuta del solicitante");
				
				ScsPermutaCabecera permutaSolicitante = new ScsPermutaCabecera();

				//permutaSolicitante.setIdPermutaCabecera(Long.parseLong(idPermcabSol));
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
					LOGGER.info("permutarGuardia() / insertando la permuta para el soliciante");
					idPermcabSol = scsPermutaCabeceraExtendsMapper.maxIdPermutaCabecera(idInstitucion.toString());
					permutaSolicitante.setIdPermutaCabecera(Long.parseLong(idPermcabSol));
//					responseSol = scsPermutaCabeceraMapper.insert(permutaSolicitante);
					scsPermutaCabeceraMapper.insert(permutaSolicitante);
				} else {
					permutaSolicitante.setIdPermutaCabecera(existeSol.get(0).getIdPermutaCabecera());
				}

//				idPermcabConf = scsPermutaCabeceraExtendsMapper.maxIdPermutaCabecera(idInstitucion.toString());
				LOGGER.info("permutarGuardia() / obtiene id de la permuta del confirmador");

				// registro para guardia confirmador
				ScsPermutaCabecera permutaConfirmador = new ScsPermutaCabecera();

//				permutaConfirmador.setIdPermutaCabecera(Long.parseLong(idPermcabConf));
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
					LOGGER.info("permutarGuardia() / insertando la permuta para el confirmador");
					idPermcabConf = scsPermutaCabeceraExtendsMapper.maxIdPermutaCabecera(idInstitucion.toString());
					permutaConfirmador.setIdPermutaCabecera(Long.parseLong(idPermcabConf));
//					responseConf = scsPermutaCabeceraMapper.insertSelective(permutaConfirmador);
					scsPermutaCabeceraMapper.insertSelective(permutaConfirmador);
				} else {
					permutaConfirmador.setIdPermutaCabecera(existeConf.get(0).getIdPermutaCabecera());
				}
				//SIGARNV-2885@DTT.JAMARTIN@01/02/2023@FIN 
				
				LOGGER.info("permutarGuardia() / recuperando el numero de la permuta");
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
					if(permutaItem.getFechaconfirmacion()!=null) {
						List<PermutaItem> listaPerm = new ArrayList<PermutaItem>();
						permutaItem.setNumero(permutaGuardia.getNumero());
						permutaItem.setIdPerCabConfirmador(permutaGuardia.getIdPerCabConfirmador());
						permutaItem.setIdPerCabSolicitante(permutaGuardia.getIdPerCabSolicitante());
						permutaItem.setFechaconfirmacion(permutaGuardia.getFechaconfirmacion());
						listaPerm.add(permutaItem);
						this.validarPermuta(listaPerm, request);
					}	
				} else {
					// Si existe, se actualizan los datos
				    ScsPermutaguardias permutaGuardiaExistente = existePer.get(0);
				    // Actualiza los campos de la permuta
				    permutaGuardiaExistente.setMotivossolicitante(permutaItem.getMotivossolicitante());
				    permutaGuardiaExistente.setFechaconfirmacion(permutaItem.getFechaconfirmacion());
				    permutaGuardiaExistente.setFechamodificacion(new Date());
				    permutaGuardiaExistente.setUsumodificacion(usuarios.get(0).getIdusuario());

				    responsePerm = scsPermutaguardiasExtendsMapper.updateByPrimaryKey(permutaGuardiaExistente);
				    if(permutaItem.getFechaconfirmacion()!=null) {
				    	List<PermutaItem> listaPerm = new ArrayList<PermutaItem>();
						permutaItem.setNumero(permutaGuardia.getNumero());
						permutaItem.setIdPerCabConfirmador(permutaGuardia.getIdPerCabConfirmador());
						permutaItem.setIdPerCabSolicitante(permutaGuardia.getIdPerCabSolicitante());
						permutaItem.setFechaconfirmacion(permutaGuardia.getFechaconfirmacion());
						listaPerm.add(permutaItem);
						this.validarPermuta(listaPerm, request);
				    }
				    
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
	
	//SIGARNV-2885@DTT.JAMARTIN@13/02/2023@INICIO
	/**
	 * Métodos que clonan objetos copiando por Valor en lugar de por Referencia
	 */
	private ScsCabeceraguardias copyScsCabeceraguardias(ScsCabeceraguardias scsCabeceraguardias) {
		ScsCabeceraguardias scsCabeceraguardiasCopy = new ScsCabeceraguardias();
		
		scsCabeceraguardiasCopy.setIdinstitucion(scsCabeceraguardias.getIdinstitucion());
		scsCabeceraguardiasCopy.setIdturno(scsCabeceraguardias.getIdturno());
		scsCabeceraguardiasCopy.setIdguardia(scsCabeceraguardias.getIdguardia());
		scsCabeceraguardiasCopy.setIdcalendarioguardias(scsCabeceraguardias.getIdcalendarioguardias());
		scsCabeceraguardiasCopy.setIdpersona(scsCabeceraguardias.getIdpersona());
		scsCabeceraguardiasCopy.setFechainicio(scsCabeceraguardias.getFechainicio());
		scsCabeceraguardiasCopy.setFechaFin(scsCabeceraguardias.getFechaFin());
		scsCabeceraguardiasCopy.setFechamodificacion(scsCabeceraguardias.getFechamodificacion());
		scsCabeceraguardiasCopy.setSustituto(scsCabeceraguardias.getSustituto());
		scsCabeceraguardiasCopy.setUsumodificacion(scsCabeceraguardias.getUsumodificacion());
		scsCabeceraguardiasCopy.setFacturado(scsCabeceraguardias.getFacturado());
		scsCabeceraguardiasCopy.setPagado(scsCabeceraguardias.getPagado());
		scsCabeceraguardiasCopy.setValidado(scsCabeceraguardias.getValidado());
		scsCabeceraguardiasCopy.setLetradosustituido(scsCabeceraguardias.getLetradosustituido());
		scsCabeceraguardiasCopy.setFechasustitucion(scsCabeceraguardias.getFechasustitucion());
		scsCabeceraguardiasCopy.setComensustitucion(scsCabeceraguardias.getComensustitucion());
		scsCabeceraguardiasCopy.setFechavalidacion(scsCabeceraguardias.getFechavalidacion());
		scsCabeceraguardiasCopy.setIdfacturacion(scsCabeceraguardias.getIdfacturacion());
		scsCabeceraguardiasCopy.setFechaalta(scsCabeceraguardias.getFechaalta());
		scsCabeceraguardiasCopy.setPosicion(scsCabeceraguardias.getPosicion());
		scsCabeceraguardiasCopy.setUsualta(scsCabeceraguardias.getUsualta());
		scsCabeceraguardiasCopy.setNumerogrupo(scsCabeceraguardias.getNumerogrupo());
		scsCabeceraguardiasCopy.setObservacionesanulacion(scsCabeceraguardias.getObservacionesanulacion());
		scsCabeceraguardiasCopy.setIdmovimiento(scsCabeceraguardias.getIdmovimiento());
		
		return scsCabeceraguardiasCopy;
	}
	
	private ScsGuardiascolegiado copyScsGuardiascolegiado(ScsGuardiascolegiado scsGuardiascolegiado) {
		ScsGuardiascolegiado scsGuardiascolegiadoCopy = new ScsGuardiascolegiado();
		
		scsGuardiascolegiadoCopy.setIdinstitucion(scsGuardiascolegiado.getIdinstitucion());
		scsGuardiascolegiadoCopy.setIdturno(scsGuardiascolegiado.getIdturno());
		scsGuardiascolegiadoCopy.setIdguardia(scsGuardiascolegiado.getIdguardia());
		scsGuardiascolegiadoCopy.setIdpersona(scsGuardiascolegiado.getIdpersona());
		scsGuardiascolegiadoCopy.setFechainicio(scsGuardiascolegiado.getFechainicio());
		scsGuardiascolegiadoCopy.setFechafin(scsGuardiascolegiado.getFechafin());
		scsGuardiascolegiadoCopy.setDiasguardia(scsGuardiascolegiado.getDiasguardia());
		scsGuardiascolegiadoCopy.setDiasacobrar(scsGuardiascolegiado.getDiasacobrar());
		scsGuardiascolegiadoCopy.setFechamodificacion(scsGuardiascolegiado.getFechamodificacion());
		scsGuardiascolegiadoCopy.setUsumodificacion(scsGuardiascolegiado.getUsumodificacion());
		scsGuardiascolegiadoCopy.setReserva(scsGuardiascolegiado.getReserva());
		scsGuardiascolegiadoCopy.setObservaciones(scsGuardiascolegiado.getObservaciones());
		scsGuardiascolegiadoCopy.setFacturado(scsGuardiascolegiado.getFacturado());
		scsGuardiascolegiadoCopy.setPagado(scsGuardiascolegiado.getPagado());
		scsGuardiascolegiadoCopy.setIdfacturacion(scsGuardiascolegiado.getIdfacturacion());
		scsGuardiascolegiadoCopy.setGuardiaSeleccionlaborables(scsGuardiascolegiado.getGuardiaSeleccionlaborables());
		scsGuardiascolegiadoCopy.setGuardiaSeleccionfestivos(scsGuardiascolegiado.getGuardiaSeleccionfestivos());
		
		return scsGuardiascolegiadoCopy;
	}
	
	private ScsPermutaguardias copyScsPermutaguardias(ScsPermutaguardias scsPermutaguardias) {
		ScsPermutaguardias scsPermutaguardiasCopy = new ScsPermutaguardias();
		
		scsPermutaguardiasCopy.setNumero(scsPermutaguardias.getNumero());
		scsPermutaguardiasCopy.setFechasolicitud(scsPermutaguardias.getFechasolicitud());
		scsPermutaguardiasCopy.setAnulada(scsPermutaguardias.getAnulada());
		scsPermutaguardiasCopy.setFechamodificacion(scsPermutaguardias.getFechamodificacion());
		scsPermutaguardiasCopy.setUsumodificacion(scsPermutaguardias.getUsumodificacion());
		scsPermutaguardiasCopy.setIdpersonaSolicitante(scsPermutaguardias.getIdpersonaSolicitante());
		scsPermutaguardiasCopy.setIdturnoSolicitante(scsPermutaguardias.getIdturnoSolicitante());
		scsPermutaguardiasCopy.setIdcalendarioguardiasSolicitan(scsPermutaguardias.getIdcalendarioguardiasSolicitan());
		scsPermutaguardiasCopy.setIdguardiaSolicitante(scsPermutaguardias.getIdguardiaSolicitante());
		scsPermutaguardiasCopy.setIdpersonaConfirmador(scsPermutaguardias.getIdpersonaConfirmador());
		scsPermutaguardiasCopy.setIdturnoConfirmador(scsPermutaguardias.getIdturnoConfirmador());
		scsPermutaguardiasCopy.setIdcalendarioguardiasConfirmad(scsPermutaguardias.getIdcalendarioguardiasConfirmad());
		scsPermutaguardiasCopy.setIdguardiaConfirmador(scsPermutaguardias.getIdguardiaConfirmador());
		scsPermutaguardiasCopy.setIdinstitucion(scsPermutaguardias.getIdinstitucion());
		scsPermutaguardiasCopy.setFechainicioSolicitante(scsPermutaguardias.getFechainicioSolicitante());
		scsPermutaguardiasCopy.setFechainicioConfirmador(scsPermutaguardias.getFechainicioConfirmador());
		scsPermutaguardiasCopy.setMotivossolicitante(scsPermutaguardias.getMotivossolicitante());
		scsPermutaguardiasCopy.setMotivosconfirmador(scsPermutaguardias.getMotivosconfirmador());
		scsPermutaguardiasCopy.setFechaconfirmacion(scsPermutaguardias.getFechaconfirmacion());
		scsPermutaguardiasCopy.setIdPerCabSolicitante(scsPermutaguardias.getIdPerCabSolicitante());
		scsPermutaguardiasCopy.setIdPerCabConfirmador(scsPermutaguardias.getIdPerCabConfirmador());
		
		return scsPermutaguardiasCopy;
	}
	
	private ScsPermutaCabecera copyScsPermutaCabecera(ScsPermutaCabecera scsPermutaCabecera) {
		ScsPermutaCabecera scsPermutaCabeceraCopy = new ScsPermutaCabecera();
		
		scsPermutaCabeceraCopy.setIdPermutaCabecera(scsPermutaCabecera.getIdPermutaCabecera());
		scsPermutaCabeceraCopy.setIdinstitucion(scsPermutaCabecera.getIdinstitucion());
		scsPermutaCabeceraCopy.setIdturno(scsPermutaCabecera.getIdturno());
		scsPermutaCabeceraCopy.setIdguardia(scsPermutaCabecera.getIdguardia());
		scsPermutaCabeceraCopy.setIdcalendarioguardias(scsPermutaCabecera.getIdcalendarioguardias());
		scsPermutaCabeceraCopy.setIdpersona(scsPermutaCabecera.getIdpersona());
		scsPermutaCabeceraCopy.setFecha(scsPermutaCabecera.getFecha());
		scsPermutaCabeceraCopy.setFechamodificacion(scsPermutaCabecera.getFechamodificacion());
		scsPermutaCabeceraCopy.setUsumodificacion(scsPermutaCabecera.getUsumodificacion());
		
		return scsPermutaCabeceraCopy;
	}
	//SIGARNV-2885@DTT.JAMARTIN@13/02/2023@FIN 
}
