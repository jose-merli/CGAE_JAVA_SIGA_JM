package org.itcgae.siga.scs.services.impl.guardia;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
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
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.CenBajastemporalesExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.FcsFactGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasKey;
import org.itcgae.siga.db.entities.ScsCalendarioguardias;
import org.itcgae.siga.db.entities.ScsCalendarioguardiasExample;
import org.itcgae.siga.db.entities.ScsDictamenejg;
import org.itcgae.siga.db.entities.ScsDictamenejgExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoKey;
import org.itcgae.siga.db.entities.ScsPermutaCabecera;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraExample;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraKey;
import org.itcgae.siga.db.entities.ScsPermutaguardias;
import org.itcgae.siga.db.entities.ScsPermutaguardiasExample;
import org.itcgae.siga.db.entities.ScsPermutaguardiasKey;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.mappers.CenBajastemporalesMapper;
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
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.guardia.GuardiasColegiadoService;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
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
						if(rese != 1)controlError++;
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

				guardiasColegiadoSaliente.forEach(scsGuardiascolegiadoExtendsMapper::deleteByPrimaryKey);

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
