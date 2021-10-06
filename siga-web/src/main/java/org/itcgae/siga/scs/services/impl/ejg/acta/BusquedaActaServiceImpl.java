package org.itcgae.siga.scs.services.impl.ejg.acta;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;

import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsActacomision;
import org.itcgae.siga.db.entities.ScsActacomisionKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgActa;
import org.itcgae.siga.db.entities.ScsEjgActaExample;
import org.itcgae.siga.db.entities.ScsEjgActaKey;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgResolucion;
import org.itcgae.siga.db.entities.ScsEjgResolucionKey;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.mappers.ScsActacomisionMapper;
import org.itcgae.siga.db.mappers.ScsEjgActaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgResolucionMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActaExtendsMapper;
import org.itcgae.siga.scs.services.acta.IBusquedaActa;
import org.itcgae.siga.scs.services.impl.ejg.GestionEJGServiceImpl;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusquedaActaServiceImpl implements IBusquedaActa {

	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsActaExtendsMapper scsActaExtendsMapper;

	@Autowired
	private ScsActacomisionMapper scsActacomisionMapper;

	@Autowired
	private ScsEjgActaMapper scsEjgActaMapper;

	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private ScsEjgResolucionMapper scsEjgResolucionMapper;

	@Autowired
	GestionEJGServiceImpl gestionEJGServiceImpl;

	@Override
	public ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("busquedaActas() -> Entrada al servicio para obtener el acta");

		ActasDTO actasDTO = new ActasDTO();

		Error error = new Error();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			LOGGER.info(
					"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsActaExtendsMapper para obtener las actas");

			// Buscamos las actas con una query customizada

			List<ActasItem> listaActasItem = scsActaExtendsMapper.busquedaActas(actasItem,
					Short.valueOf(idInstitucion));

			for (ActasItem ActasItem : listaActasItem) {

				LOGGER.info("ver si mapea el anio al objeto o que le pasa " + ActasItem.getAnio());
			}

			if (listaActasItem != null) {

				// Guardamos la lista de actas en el objeto DTO

				actasDTO.actasItem(listaActasItem);

			} else {
				error.setCode(500);
				error.setDescription("Ha ocurrido un error a la hora de buscar las actas");
			}

			LOGGER.info(
					"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsActaExtendsMapper para obtener lista de Actas");

		} else {

			LOGGER.warn("busquedaActas() -> idInstitucion del token nula");
		}

		LOGGER.info("busquedaActas() -> Salida del servicio para obtener la lista de Actas");

		actasDTO.setError(error);

		return actasDTO;
	}

	@Override
	public DeleteResponseDTO borrarActas(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("borrarActas() -> Entrada al servicio para obtener el colegiado");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

		Error error = new Error();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"borrarActas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"borrarActas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			// Comprobamos que los datos del acta que vienen no son nulos
			if (actasItem.getAnio() != null && actasItem.getIdActa() != null && idInstitucion != null) {

				// Obtenemos el acta que vamos a borrar
				ScsActacomision acta = obtenerActa(actasItem, Short.valueOf("2011"));

				if (acta != null) {
					// Obtenemos el listado de EJG asociados al Acta
					List<ScsEjgActa> listaEjgAsociadosActa = obtenerEjgActa(acta);

					// Comprobamos que la lista esta vacia o nula ya que si tiene elementos
					// asociados al acta no vamos a
					if (listaEjgAsociadosActa.isEmpty() || listaEjgAsociadosActa == null) {

						LOGGER.info(
								"borrarActas() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsActaExtendsMapper para obtener las actas");

						int response = scsActacomisionMapper.deleteByPrimaryKey(acta);

						if (response == 1) {

							deleteResponseDTO.setStatus(SigaConstants.OK);

							LOGGER.info("borrarActas() -> OK. Acta eliminada correctamente");

						} else {
							deleteResponseDTO.setStatus(SigaConstants.KO);

							error.setCode(500);

							error.setDescription("Acta NO eliminada correctamente");

							deleteResponseDTO.setStatus(SigaConstants.KO);

							LOGGER.warn("borrarActas() -> KO. Acta NO eliminada correctamente");

						}

						LOGGER.info(
								"borrarActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsActaExtendsMapper para obtener lista de Actas");
					} else {

						deleteResponseDTO.setStatus(SigaConstants.KO);

						error.setCode(500);

						error.setDescription("Acta NO eliminada porque tiene elementos asociados");

						deleteResponseDTO.setStatus(SigaConstants.KO);

						LOGGER.info("El acta tiene elementos asociados, no se ha podido borrar");

						LOGGER.warn("borrarActas() -> KO. Acta NO eliminada correctamente");

						throw new SigaExceptions(
								"Ha ocurrido un error a la hora de borrar el actas, el acta tiene elementos asociados");

					}
				} else {
					throw new SigaExceptions("El acta es nula");
				}

			} else {
				error.setCode(500);

				error.setDescription("Faltan datos para encontrar el acta que se desea borrar");

				deleteResponseDTO.setStatus(SigaConstants.KO);
			}

		} else {
			deleteResponseDTO.setStatus(SigaConstants.KO);

			error.setCode(500);

			error.setDescription("Acta NO eliminada porque idInstitucion del token nulo");

			deleteResponseDTO.setStatus(SigaConstants.KO);

			LOGGER.warn("borrarActas() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de borrarActas");

		deleteResponseDTO.setError(error);

		return deleteResponseDTO;
	}

	@Override
	public ComboDTO comboSufijoActa(HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("comboSufijoActa() -> Entrada al servicio para obtener el colegiado");

		ComboDTO comboDTO = new ComboDTO();

		Error error = new Error();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		List<ComboItem> comboItems = null;

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboSufijoActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboSufijoActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			LOGGER.info(
					"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener el combo de sufijos para actas");

			// Obtenemos el combo de sufijos para el acta

			comboItems = scsActaExtendsMapper.comboSufijoActa(Short.valueOf("2011"));

			LOGGER.info(
					"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener el combo de sufijos para actas");

			if (comboItems != null) {

				comboDTO.setCombooItems(comboItems);

			} else {

				error.setCode(500);

				error.setDescription("El listado de combos es nulo");
			}

		} else {

			error.setCode(500);

			error.setDescription("Fallo a la hora de devolver los sufijos, idInstitucion del token nula");

			LOGGER.warn("comboSufijoActa() -> idInstitucion del token nula");

		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los sufijos de las actas");

		comboDTO.setError(error);

		return comboDTO;
	}

	@Override
	public InsertResponseDTO guardarActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("guardarActa() -> Entrada al servicio para obtener el colegiado");

		Error error = new Error();

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		boolean actualizar = true;

		int response;

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			int year = Calendar.getInstance().get(Calendar.YEAR);

			ScsActacomision acta = null;

			// Obtenemos el acta que vamos a guardar si los campos de su clave primaria no
			// son nulos, si son nulos crearemos el acta

			if (actasItem.getAnio() != null && actasItem.getIdActa() != null) {
				acta = obtenerActa(actasItem, Short.valueOf("2011"));
			}

			// Si es nulo significa que vamos a guardar un acta que es nueva por lo cual
			// debemos crear un objeto nuevo
			if (acta == null) {

				acta = new ScsActacomision();

				// Con este dato sabremos si actualizar o crear un nuevo registro mas tarde
				actualizar = false;
			}

			// Comprobamos que se cumplen los requisitos de cada campo
			if (actasItem.getAnio() != null && actasItem.getAnio().equalsIgnoreCase(String.valueOf(year))
					&& actasItem.getFechaReunion() != null && actasItem.getFechaResolucion() != null
					&& actasItem.getMiembros().length() <= 4000 && actasItem.getPendientes() != null
					&& actasItem.getPendientes().length() <= 4000) {
//				&& scsActaExtendsMapper.comprobarGuardarActaSufijo(actasItem,
//						Short.valueOf("2011")) == null
//				&& scsActaExtendsMapper.comprobarGuardarActaPonente(actasItem, Short.valueOf("2011")) == 0
				LOGGER.info(
						"guardarActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para guardar el acta");

				if (actasItem.getAnio() != null) {
					acta.setAnioacta(Short.valueOf(actasItem.getAnio()));

				}
				if (actasItem.getIdActa() != null) {
					acta.setIdacta(Long.valueOf(actasItem.getIdActa()));
					LOGGER.info("ACTA ES DISTINTO DE NULO");
				} else {
					int idActa;
					try {
						idActa = scsActaExtendsMapper.obtenerIdActa(actasItem, Short.valueOf("2011"));
						LOGGER.info("EL id DEL ACTA ES" + idActa);
						actasItem.setIdActa(String.valueOf(idActa));
						acta.setIdacta(Long.valueOf(idActa + 1));
					} catch (Exception e) {
						LOGGER.info("EL id DEL ACTA ES 1 si no encuentro el numero acta como lo busco");
						idActa = 1;
						acta.setIdacta(Long.valueOf(idActa));
						actasItem.setIdActa(String.valueOf(idActa));
					}

				}

				acta.setIdinstitucion(Short.valueOf("2011"));

				LOGGER.info(" NUMERO ACTA" + actasItem.getNumeroActa());
				LOGGER.info(" SUFIJO ACTA" + actasItem.getSufijo());

				if (actasItem.getNumeroActa() != null && actasItem.getSufijo() != null) {
					LOGGER.info("DENTRO DE NUMERO ACTA");
					acta.setNumeroacta(actasItem.getNumeroActa() + actasItem.getSufijo());

					String letrasActa;
					int numActaInt;

					LOGGER.info("VAMOS A OBTENER EL NUMERO DEL ACTA");

					List<String> numActa = scsActaExtendsMapper.obtenerNumActa(actasItem, Short.valueOf("2011"));

					for (String ejg : numActa) {
						LOGGER.info("Lista de numerosdeactas");
						LOGGER.info(ejg);
					}

					if (numActa.contains(actasItem.getNumeroActa() + actasItem.getSufijo())) {
						throw new SigaExceptions(
								"Ya existe un acta con ese numero, por favor introduzca uno mas grande");
					} else {
						acta.setNumeroacta(actasItem.getNumeroActa() + actasItem.getSufijo());
					}
				}

//					LOGGER.info("EL NUMERO DEL ACTA ES: " + numActa);
//
//					if (numActa.matches("[0-9]+")) {
//						LOGGER.info("SI SOLO TIENE NUMEROS");
//						numActaInt = Integer.parseInt(numActa); 
//						LOGGER.info("NUMERO ACTA VA A SER " + numActaInt);
//						acta.setNumeroacta(String.valueOf(numActaInt + 1));
//					}else {
//						LOGGER.info("SI  TIENE LETRAS TAMBIEN");
//						numActaInt = Integer.parseInt(numActa.substring(0, 2));
//						LOGGER.info("SUBESTRING NUMERO " + numActa);
//						letrasActa = numActa.substring(2, numActa.length());
//						LOGGER.info("SACO LAS LETRAS " + letrasActa);
//						numActa = String.valueOf(numActaInt + 1);
//						numActa += letrasActa;
//						LOGGER.info("SE LE SUMA UNO Y SE LE UNEN LAS LETRAS");
//						acta.setNumeroacta(numActa);
//
//					}				

				if (actasItem.getHoraInicio() != null) {
					acta.setHorainicioreunion(actasItem.getHoraInicio());

				}
				if (actasItem.getHoraFin() != null) {
					acta.setHorafinreunion(actasItem.getHoraFin());

				}
				if (actasItem.getIdPresidente() != null) {
					acta.setIdpresidente(Integer.parseInt(actasItem.getIdPresidente()));

				}
				if (actasItem.getIdSecretario() != null) {
					acta.setIdsecretario(Integer.parseInt(actasItem.getIdSecretario()));

				}
				if (actasItem.getMiembros() != null) {
					acta.setMiembroscomision(actasItem.getMiembros());

				}
				if (actasItem.getObservaciones() != null) {
					acta.setObservaciones(actasItem.getObservaciones());

				}
				if (actasItem.getPendientes() != null) {
					acta.setPendientes(actasItem.getPendientes());

				}
				if (actasItem.getFechaReunion() != null) {
					acta.setFechareunion(actasItem.getFechaReunion());

				}
				if (actasItem.getFechaResolucion() != null) {
					acta.setFecharesolucion(actasItem.getFechaResolucion());

				}
				if (usuario.getIdusuario() != null) {
					acta.setUsumodificacion(usuario.getIdusuario());

				}

				acta.setFechamodificacion(new Date());

				acta.setFechaintercambio(null);

				acta.setIdintercambio(null);

				// Actualizamos o creamos dependiendo del booleano
				if (actualizar == true) {
					response = scsActacomisionMapper.updateByPrimaryKey(acta);
					if (response == 0) {
						throw new SigaExceptions("Ha ocurrido un error a la hora de actualizar el acta");
					}
					insertResponseDTO.setStatus(SigaConstants.OK);

				} else {

					response = scsActacomisionMapper.insert(acta);

					if (response == 0) {
						throw new SigaExceptions("Ha ocurrido un error a la hora de guardar el acta");
					}

					insertResponseDTO.setStatus(SigaConstants.OK);
				}

				LOGGER.info(
						"guardarActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para guardar el acta");

			} else {

				error.setCode(500);

				error.setDescription("No se han cumplido los requisitos para guardar el acta");

				LOGGER.info("guardarActa() No se han cumplido los requisitos para guardar el acta");
			}

		} else {

			LOGGER.warn("guardarActa() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para guardar el Acta");

		insertResponseDTO.setError(error);

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO anadirEJGPendientesCAJG(ActasItem actasItem, HttpServletRequest request)
			throws SigaExceptions {

		Error error = new Error();

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String pendientes = "";

		int response;

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			// Obtenemos el acta con la que vamos a trabajar
			ScsActacomision acta = obtenerActa(actasItem, Short.valueOf("2011"));

			// Obtenemos la lista de ejg asociados al acta gracias a la tabla scs_ejg_acta
			List<ScsEjgActa> listaEjgActa = obtenerEjgActa(acta);

			if (!listaEjgActa.isEmpty()) {

				// recorreremos toda la lista para sacar los ejg que esten asociados al acata
				// para tratarlos
				for (ScsEjgActa scsEjgActa : listaEjgActa) {

					// Obtenemos el ejg
					ScsEjg ejgItem = obtenerEjg(scsEjgActa);

					if (ejgItem != null) {

						// Pendientes guardara el nombre de todos los ejg asociados a ese acta
						pendientes += scsEjgActa.getAnioacta() + "/" + scsEjgActa.getNumeroejg() + ",";

						// Obtendremos los objetos resolucion asociados al ejg para modificarlo
						ScsEjgResolucion scsEjgResolucionItem = obtenerEjgResolucion(scsEjgActa);

						// Solo nos interesaran los ejg que tengan el tipo ratificacion 4 o 6
						if (scsEjgResolucionItem.getIdtiporatificacionejg() != null
								&& scsEjgResolucionItem.getIdtiporatificacionejg() == Short.valueOf("4")) {

							LOGGER.info("El objeto ejg es: " + scsEjgActa.getAnioejg() + " "
									+ scsEjgActa.getIdinstitucionejg() + " " + scsEjgActa.getIdtipoejg() + " "
									+ scsEjgActa.getNumeroejg());

							LOGGER.info("Si el ejg es 4");

							// Buscaremos el estado de ese ejg
							ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();
							exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion())
									.andIdtipoejgEqualTo(ejgItem.getIdtipoejg()).andAnioEqualTo(ejgItem.getAnio())
									.andNumeroEqualTo(ejgItem.getNumero()).andIdestadoporejgEqualTo(Long.valueOf("9"));
							exampleEstado.setOrderByClause("FECHAMODIFICACION DESC");
							List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper.selectByExample(exampleEstado);

							LOGGER.info("Tamaño lista estado ejg" + listaEstadoEjg.size());
							// Sacaremos el primer elemento ya que sera el ultimo registro actualizado, por
							// lo cual sera el que este activo
							ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);

							LOGGER.info("Añadiendo observaciones");
							scsEstadoejg.setObservaciones("Expediente pendiente de la CAJG. Se retira del acta "
									+ acta.getAnioacta() + "/" + acta.getNumeroacta());

							LOGGER.info("Updateando el estado ejg");

							response = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

							if (response == 0) {
								throw new SigaExceptions("No se han actualizado el estado del ejg");
							}

							updateResponseDTO.setStatus(SigaConstants.OK);

						} else if (ejgItem.getNumeroresolucion() == "6") {

							LOGGER.info("Si el ejg es 6");

							LOGGER.info("El objeto ejg es: " + scsEjgActa.getAnioejg() + " "
									+ scsEjgActa.getIdinstitucionejg() + " " + scsEjgActa.getIdtipoejg() + " "
									+ scsEjgActa.getNumeroejg());

							ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();

							exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion())
									.andIdtipoejgEqualTo(ejgItem.getIdtipoejg()).andAnioEqualTo(ejgItem.getAnio())
									.andNumeroEqualTo(ejgItem.getNumero()).andIdestadoporejgEqualTo(Long.valueOf("21"));
							exampleEstado.setOrderByClause("FECHAMODIFICACION DESC");
							LOGGER.info("Se busca el estado ejg");

							List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper.selectByExample(exampleEstado);

							LOGGER.info("Tamaño lista estado ejg" + listaEstadoEjg.size());

							// Sacaremos el primer elemento ya que sera el ultimo registro actualizado, por
							// lo cual sera el que este activo
							ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);

							LOGGER.info("Añadiendo observaciones");

							scsEstadoejg.setObservaciones("Expediente pendiente de la CAJG. Se retira del acta "
									+ acta.getAnioacta() + "/" + acta.getNumeroacta());

							LOGGER.info("Updateando el estado ejg");

							response = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

							if (response == 0) {
								throw new SigaExceptions("No se han actualizado el estado del ejg");
							}

							updateResponseDTO.setStatus(SigaConstants.OK);

						}

					}

				}
				LOGGER.info("Seteando las observaciones");

				acta.setObservaciones(pendientes);

				LOGGER.info("Updateando el acta");

				response = scsActacomisionMapper.updateByPrimaryKey(acta);

				if (response == 0) {
					throw new SigaExceptions("No se han actualizado el acta");
				}

			} else {
				error.setCode(500);

				error.setDescription("La lista de objetos scs_ejg_acta esta vacia");

			}
			LOGGER.info(
					"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");
		} else {

			LOGGER.warn("busquedaEJGComision() -> idInstitucion del token nula");

			throw new SigaExceptions("IdInstitucion del token nula");

		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");

		error.setCode(200);
		error.setDescription(pendientes);
		updateResponseDTO.setError(error);

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO abrirActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("abrirActa() -> Entrada al servicio para obtener el colegiado");

		Error error = new Error();

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String informacionEjgActaAbierta = "";

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"abrirActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"abrirActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			LOGGER.info("abrirActa() / buscando el acta ");

			LOGGER.info("abrirActa() / valores para buscar el acta = " + actasItem.getAnio() + " id acta = "
					+ actasItem.getIdActa());

			// Buscamos el acta que vamos a abrir
			ScsActacomision acta = obtenerActa(actasItem, Short.valueOf("2011"));

			LOGGER.info("abrirActa() / el acta es: " + acta.getAnioacta() + acta.getIdinstitucion() + acta.getIdacta());
			LOGGER.info("abrirActa() / y tiene la fecha resolucion a : " + acta.getFecharesolucion());

			// Miramos que la fecha de resolucion sea distinta de nula ya que significa que
			// esta
			// cerrada, de lo contrario no hariamos nada porque ya esta abierta
			if (acta.getFecharesolucion() != null) {

				LOGGER.info("abrirActa() / acta encontrada");

				LOGGER.info("abrirActa() / buscando los ejg asociados al acta");

				ScsEjgExample example = new ScsEjgExample();

				LOGGER.info("abrirActa() / valores acta institucion = " + acta.getIdinstitucion() + " id acta = "
						+ acta.getIdacta() + " anioacta =  " + acta.getAnioacta());

				example.createCriteria().andIdactaEqualTo(acta.getIdacta())
						.andIdinstitucionEqualTo(acta.getIdinstitucion()).andAnioactaEqualTo(acta.getAnioacta());

				// Buscamos los ejg asociados al acta
				List<ScsEjg> listaEjgAsociadosActa = scsEjgMapper.selectByExample(example);

				LOGGER.info("abrirActa() / encontrado los ejg asociados al acta");

				LOGGER.info("abrirActa() / entro en el if");

				if (!listaEjgAsociadosActa.isEmpty()) {

					LOGGER.info("tamaño de la lista de ejg asociados" + listaEjgAsociadosActa.size());

					for (ScsEjg ejg : listaEjgAsociadosActa) {

						// Utilizamos este booleano para saber si existen mas actas asociadas a este ejg
						// y estan abiertas
						boolean masActasAbiertas = false;
						// String donde guardaremos la informacion si la hay del acta que esta abierta
						// para el ejg

						ejg.setFecharesolucioncajg(null);

						LOGGER.info("abrirActa() / valores ejg institucion = " + ejg.getIdinstitucion()
								+ " idtipoejg =  " + ejg.getIdtipoejg() + " anio =  " + ejg.getAnio() + " numero =  "
								+ ejg.getNumero());

						LOGGER.info("abrirActa() / buscando los estadosejg del ejg en cuestion");

						// Obtenemos la relaciones con las actas para este ejg
						List<ScsEjgActa> listaEjgActa = obtenerEjgActa(ejg);

						if (!listaEjgActa.isEmpty()) {

							// Recorremos la lista de ejg y actas asociados en busqueda de un acta abierta
							for (ScsEjgActa scsEjgActa : listaEjgActa) {

								// Sacamos las actas asociadas al ejg
								ScsActacomision actaComision = obtenerActa(scsEjgActa);

								// Si la fecha resolucion esta a nula significa que el acta esta abierta
								if (actaComision.getFecharesolucion() == null) {

									masActasAbiertas = true;

									informacionEjgActaAbierta += " - Ejg " + ejg.getAnio() + "/" + ejg.getNumero()
											+ " abierto para el acta " + actaComision.getAnioacta() + "/"
											+ actaComision.getNumeroacta() + " - ";
								}

							}
						} else {

							error.setCode(500);

							error.setDescription("La lista de objetos scs_ejg_acta esta vacia");
						}

						if (masActasAbiertas == false) {

							acta.setFecharesolucion(null);
							scsActacomisionMapper.updateByPrimaryKey(acta);

							String idEstadoPorEjg = scsActaExtendsMapper.getEstadosEjg(ejg.getIdinstitucion(),
									ejg.getIdtipoejg(), ejg.getAnio(), ejg.getNumero());

							LOGGER.info("ejg.getIdinstitucion()" + ejg.getIdinstitucion());
							LOGGER.info("Long.valueOf(idEstadoPorEjg)" + Long.valueOf(idEstadoPorEjg));
							LOGGER.info("ejg.getIdinstitucion()" + ejg.getIdinstitucion());
							LOGGER.info("ejg.getIdtipoejg()" + ejg.getIdtipoejg());
							LOGGER.info("ejg.getIdtipoejg()" + ejg.getIdtipoejg());

							// Buscamos el estado del ejg
							ScsEstadoejg estadoejgObject = obtenerEstadoEjg(ejg, idEstadoPorEjg);

							LOGGER.info("busquedaActas() / encontrado los estadosejg del ejg en cuestion");

							if (estadoejgObject != null) {

								LOGGER.info("scsestadoejg no es nulo ");

								if (estadoejgObject.getIdestadoporejg().equals(Long.valueOf("10"))) {

									LOGGER.info("scsestadoejg tiene el idestadopor ejg a 10  ");

									estadoejgObject.setFechabaja(new Date());

									int resultado = scsEstadoejgMapper.updateByPrimaryKey(estadoejgObject);

									if (resultado == 0) {
										throw new SigaExceptions("No se han actualizado el estado del ejg");
									}

									LOGGER.info("ha actualizado el registro????? " + resultado);
								}
							} else {

								error.setCode(500);

								error.setDescription("El objeto de estado para el ejg es nulo");

							}

						} else {

							error.setCode(500);

							error.setDescription(informacionEjgActaAbierta);

						}

					}

				} else {
					acta.setFecharesolucion(null);
					scsActacomisionMapper.updateByPrimaryKey(acta);

				}

				LOGGER.info(
						"abrirActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");

			} else {

				error.setCode(500);

				error.setDescription("El acta ya esta abierta");

			}

		} else {
			error.setCode(500);

			error.setDescription("el id de la instuticon es nulo");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");

		updateResponseDTO.setError(error);

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO cerrarActa(ActasItem actasItem, HttpServletRequest request) throws Exception {

		LOGGER.info("cerrarActa() -> Entrada al servicio para obtener el colegiado");

		Error error = new Error();

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int response = 0;

		// Si la institucion del token es nula, no ejecutaremos nada del código

		if (null != idInstitucion) {

			// Vamos a obtener el usuario para poder sacar sus datos y usarlos en el método

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cerrarActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"cerrarActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);

			usuario.setIdinstitucion(idInstitucion);

			GenParametrosExample genParametrosExample = new GenParametrosExample();

			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			LOGGER.info(
					"cerrarActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener las actas");

			// Obtenemos el acta que vamos a cerrar
			ScsActacomision acta = obtenerActa(actasItem, Short.valueOf("2011"));

			if (acta.getFecharesolucion() == null) {

				// Ponemos fecha de hoy para el cierre
				acta.setFecharesolucion(new Date());

				ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();

				ScsEjgExample example = new ScsEjgExample();

				example.createCriteria().andIdactaEqualTo(acta.getIdacta())
						.andIdinstitucionactaEqualTo(acta.getIdinstitucion()).andAnioactaEqualTo(acta.getAnioacta());

				LOGGER.info("Lista de ejgasociados cargandose");

				List<ScsEjg> listaEjgAsociados = scsEjgMapper.selectByExample(example);

				LOGGER.info("Lista de ejgasociados conseguida");

				if (!listaEjgAsociados.isEmpty()) {

					for (ScsEjg ejgItem : listaEjgAsociados) {

						LOGGER.info("Entro en el for");

						if (ejgItem.getIdfundamentojuridico() == null) {

							ejgItem.setObservaciones(
									ejgItem.getObservaciones() + "Expediente sin Resolución. Se retira del acta "
											+ ejgItem.getAnio() + "/" + ejgItem.getNumejg());

							response = scsEjgMapper.updateByPrimaryKey(ejgItem);

							if (response == 0)
								throw (new Exception("Error al eliminar el dictamen asociado al EJG"));

							LOGGER.info("updateo el ejg si el fundamento juridico es nulo");

							LOGGER.info("Datos ejg anio ->" + ejgItem.getAnio() + " idinstitucion -> "
									+ ejgItem.getIdinstitucion() + " idtipoejg ->" + ejgItem.getIdtipoejg()
									+ " numeroejg->" + ejgItem.getNumejg());

							LOGGER.info("Datos acta anio ->" + acta.getAnioacta() + " idinstitucion -> "
									+ acta.getIdinstitucion() + " idtipoejg ->" + acta.getIdacta());

							response = borrarActa(acta, ejgItem);

							LOGGER.info("RESPONSE " + response);

							LOGGER.info("borro la relacion ejgacta");

						} else {

							LOGGER.info("el fundamento juridico no es nulo");

							response = actualizarFecharesolucioncajg(idInstitucion, usuario, ejgItem);

							if (response == 0)
								throw (new Exception(
										"Error en el triggerEjgUpdatesResol al actualizar la fecha de resolucion del acta asociada"
												+ " asociada a un EJG"));

							ejgItem.setFecharesolucioncajg(acta.getFecharesolucion());

							ejgItem.setObservaciones(
									ejgItem.getObservaciones() + "Expediente sin Resolución. Se retira del acta "
											+ ejgItem.getAnio() + "/" + ejgItem.getNumejg());

							response = scsEjgMapper.updateByPrimaryKey(ejgItem);

							if (response == 0) {
								throw (new Exception(
										"No se ha podido actualizar el objeto ejg con la nueva observacion y la nueva fecha resolucion"));
							}

							LOGGER.info("updateo el ejg si el fundamento juridico no es nulo");

							exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion())
									.andIdtipoejgEqualTo(ejgItem.getIdtipoejg()).andAnioEqualTo(ejgItem.getAnio())
									.andNumeroEqualTo(ejgItem.getNumero());

							exampleEstado.setOrderByClause("FECHAMODIFICACION DESC");

							List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper.selectByExample(exampleEstado);

							LOGGER.info("lista con el estado ejg");

							if (!listaEstadoEjg.isEmpty()) {

								response = actualizarEstado(actasItem, ejgItem, listaEstadoEjg);

								if (response == 0) {
									throw (new SigaExceptions("No se ha podido actualizar el estado"));
								}

								LOGGER.info("updateando el estado ejg");

							}

							LOGGER.info("Datos ejg anio ->" + ejgItem.getAnio() + " idinstitucion -> "
									+ ejgItem.getIdinstitucion() + " idtipoejg ->" + ejgItem.getIdtipoejg()
									+ " numeroejg->" + ejgItem.getNumejg());

							LOGGER.info("Datos acta anio ->" + acta.getAnioacta() + " idinstitucion -> "
									+ acta.getIdinstitucion() + " idtipoejg ->" + acta.getIdacta());

							response = borrarActa(acta, ejgItem);

							if (response == 0) {
								throw (new Exception("No se ha podido borrar el acta"));
							}

							LOGGER.info("RESPONSE " + response);

							LOGGER.info("Borrando por example");

						}

					}

				}
			}else {
				error.setCode(500);
				error.setDescription("El acta ya esta cerrada");
			}
			LOGGER.info(
					"cerrarActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");

		} else {
			LOGGER.warn("cerrarActa() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");

		updateResponseDTO.setError(error);

		return updateResponseDTO;
	}

	/**
	 * @param idInstitucion
	 * @param usuario
	 * @param ejgItem
	 * @return
	 * @throws Exception
	 */
	private int actualizarFecharesolucioncajg(Short idInstitucion, AdmUsuarios usuario, ScsEjg ejgItem)
			throws Exception {
		int response;

		ResolucionEJGItem resolEjg = new ResolucionEJGItem();

		resolEjg.setAnio(ejgItem.getAnio());

		resolEjg.setIdInstitucion(ejgItem.getIdinstitucion());

		resolEjg.setIdTipoEJG(ejgItem.getIdtipoejg());

		resolEjg.setNumero(ejgItem.getNumero());

		resolEjg.setIdTiporatificacionEJG(ejgItem.getIdtiporatificacionejg());

		response = gestionEJGServiceImpl.triggersEjgUpdatesResol(resolEjg, usuario, idInstitucion);

		return response;
	}

	/**
	 * @param actasItem
	 * @param ejgItem
	 * @param listaEstadoEjg
	 * @throws ParseException
	 */
	private int actualizarEstado(ActasItem actasItem, ScsEjg ejgItem, List<ScsEstadoejg> listaEstadoEjg)
			throws ParseException {
		
		int resultado;
		
		ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);
		
		LOGGER.info("obtengo el objeto estado para el ejg " +scsEstadoejg.getIdtipoejg() + " "+ scsEstadoejg.getAnio() +" "+ scsEstadoejg.getNumero() +" " + scsEstadoejg.getIdestadoporejg() );

		
		scsEstadoejg.setIdestadoporejg(Long.valueOf(10));

		scsEstadoejg.setAnio(Short.valueOf(ejgItem.getAnio()));

		scsEstadoejg.setNumero(Long.valueOf(ejgItem.getNumejg()));

		scsEstadoejg.setIdtipoejg(Short.valueOf(ejgItem.getIdtipoejg()));

		scsEstadoejg.setIdestadoejg((short) (scsEstadoejg.getIdestadoejg() + 1));

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date fechaResolucion = actasItem.getFechaResolucion();

		Date fechaSinHora;

		fechaSinHora = formatter.parse(formatter.format(fechaResolucion));

		scsEstadoejg.setFechainicio(fechaSinHora);

		LOGGER.info("poniendo fecha inicio");

		resultado = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

		return resultado;
	}

	/**
	 * @param acta
	 * @param ejgItem
	 * @return
	 */
	private int borrarActa(ScsActacomision acta, ScsEjg ejgItem) {

		int response;

		ScsEjgActaKey keyEjgActa = new ScsEjgActaKey();

		keyEjgActa.setAnioacta(acta.getAnioacta());

		keyEjgActa.setAnioejg(ejgItem.getAnio());

		keyEjgActa.setIdacta(acta.getIdacta());

		keyEjgActa.setIdinstitucionacta(acta.getIdinstitucion());

		keyEjgActa.setIdinstitucionejg(ejgItem.getIdinstitucion());

		keyEjgActa.setIdtipoejg(ejgItem.getIdtipoejg());

		keyEjgActa.setNumeroejg(ejgItem.getNumero());

		response = scsEjgActaMapper.deleteByPrimaryKey(keyEjgActa);

		return response;
	}

	/**
	 * @param acta
	 * @return
	 */
	private List<ScsEjgActa> obtenerEjgActa(ScsActacomision acta) {

		ScsEjgActaExample scsEjgActaExample = new ScsEjgActaExample();

		scsEjgActaExample.createCriteria().andIdactaEqualTo(acta.getIdacta());

		scsEjgActaExample.createCriteria().andIdinstitucionactaEqualTo(acta.getIdinstitucion());

		scsEjgActaExample.createCriteria().andAnioactaEqualTo(acta.getAnioacta());

		List<ScsEjgActa> listaEjgAsociadosActa = scsEjgActaMapper.selectByExample(scsEjgActaExample);

		return listaEjgAsociadosActa;
	}

	private ScsActacomision obtenerActa(ActasItem actasItem, Short idInstitucion) {

		ScsActacomision acta = null;

		ScsActacomisionKey key = new ScsActacomisionKey();

		key.setAnioacta(Short.valueOf(actasItem.getAnio()));

		key.setIdacta(Long.valueOf(actasItem.getIdActa()));

		key.setIdinstitucion(Short.valueOf(idInstitucion));

		try {
			acta = scsActacomisionMapper.selectByPrimaryKey(key);

		} catch (Exception e) {
			LOGGER.info("No se encuentra el acta en la base de datos");
		}

		return acta;
	}

	/**
	 * @param scsEjgActa
	 * @return
	 */
	private ScsEjgResolucion obtenerEjgResolucion(ScsEjgActa scsEjgActa) {

		ScsEjgResolucionKey scsEjgResolucionKey = new ScsEjgResolucionKey();

		scsEjgResolucionKey.setAnio(scsEjgActa.getAnioejg());

		scsEjgResolucionKey.setIdinstitucion(scsEjgActa.getIdinstitucionejg());

		scsEjgResolucionKey.setIdtipoejg(scsEjgActa.getIdtipoejg());

		scsEjgResolucionKey.setNumero(scsEjgActa.getNumeroejg());

		ScsEjgResolucion scsEjgResolucionItem = scsEjgResolucionMapper.selectByPrimaryKey(scsEjgResolucionKey);

		return scsEjgResolucionItem;
	}

	/**
	 * @param scsEjgActa
	 * @return
	 */
	private ScsEjg obtenerEjg(ScsEjgActa scsEjgActa) {

		ScsEjgKey ejgkey = new ScsEjgKey();

		ejgkey.setAnio(scsEjgActa.getAnioejg());

		ejgkey.setIdinstitucion(scsEjgActa.getIdinstitucionejg());

		ejgkey.setIdtipoejg(scsEjgActa.getIdtipoejg());

		ejgkey.setNumero(scsEjgActa.getNumeroejg());

		ScsEjg ejgItem = scsEjgMapper.selectByPrimaryKey(ejgkey);

		return ejgItem;
	}

	/**
	 * @param ejg
	 * @param idEstadoPorEjg
	 * @return
	 */
	private ScsEstadoejg obtenerEstadoEjg(ScsEjg ejg, String idEstadoPorEjg) {

		ScsEstadoejgKey estadoejgKey = new ScsEstadoejgKey();

		estadoejgKey.setAnio(ejg.getAnio());

		estadoejgKey.setIdestadoporejg(Long.valueOf(idEstadoPorEjg));

		estadoejgKey.setIdinstitucion(ejg.getIdinstitucion());

		estadoejgKey.setIdtipoejg(ejg.getIdtipoejg());

		estadoejgKey.setNumero(ejg.getNumero());

		ScsEstadoejg estadoejgObject = scsEstadoejgMapper.selectByPrimaryKey(estadoejgKey);

		return estadoejgObject;
	}

	/**
	 * @param scsEjgActa
	 * @return
	 */
	private ScsActacomision obtenerActa(ScsEjgActa scsEjgActa) {

		ScsActacomisionKey actakey = new ScsActacomisionKey();

		actakey.setAnioacta(Short.valueOf(scsEjgActa.getAnioacta()));

		actakey.setIdacta(Long.valueOf(scsEjgActa.getIdacta()));

		actakey.setIdinstitucion(scsEjgActa.getIdinstitucionacta());

		ScsActacomision actaComision = scsActacomisionMapper.selectByPrimaryKey(actakey);

		return actaComision;
	}

	/**
	 * @param ejg
	 * @return
	 */
	private List<ScsEjgActa> obtenerEjgActa(ScsEjg ejg) {

		ScsEjgActaExample scsEjgActaExample = new ScsEjgActaExample();

		scsEjgActaExample.createCriteria().andIdinstitucionejgEqualTo(ejg.getIdinstitucion())
				.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andAnioejgEqualTo(ejg.getAnio())
				.andNumeroejgEqualTo(ejg.getNumero());

		List<ScsEjgActa> listaEjgActa = scsEjgActaMapper.selectByExample(scsEjgActaExample);

		return listaEjgActa;
	}

}