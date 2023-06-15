package org.itcgae.siga.scs.services.impl.ejg.acta;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
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
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsActacomisionMapper;
import org.itcgae.siga.db.mappers.ScsEjgActaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgResolucionMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgActaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
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
	private ScsEjgActaExtendsMapper scsEjgActaExtendsMapper;

	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;
	
	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private ScsEjgResolucionMapper scsEjgResolucionMapper;
	
	@Autowired
	private CenInstitucionMapper cenInstitucionMapper;

	@Autowired
	GestionEJGServiceImpl gestionEJGServiceImpl;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Override
	public ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions {

		LOGGER.info("busquedaActas() -> Entrada al servicio para obtener el acta");

		LOGGER.info("*******************datos que entran del front ********************"
				+ actasItem.getFecharesolucion() + " " + actasItem.getAnioacta() + " " + actasItem.getIdpresidente());

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
			if (usuarios != null && usuarios.size() > 0) {

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

				if (listaActasItem != null) {

					// Guardamos la lista de actas en el objeto DTO

					actasDTO.actasItem(listaActasItem);

				} else {
					error.setCode(500);
					error.setDescription("Ha ocurrido un error a la hora de buscar las actas");
				}

				LOGGER.info(
						"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsActaExtendsMapper para obtener lista de Actas");
			}
		} else {

			LOGGER.warn("busquedaActas() -> idInstitucion del token nula");
		}

		LOGGER.info("busquedaActas() -> Salida del servicio para obtener la lista de Actas");

		actasDTO.setError(error);

		return actasDTO;
	}

	public DeleteResponseDTO borrarActas(List<ActasItem> actasItems, HttpServletRequest request) throws SigaExceptions {

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
			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				for (ActasItem actasItem : actasItems) {

					// Comprobamos que los datos del acta que vienen no son nulos
					if (actasItem.getAnioacta() != null && actasItem.getIdacta() != null && idInstitucion != null) {

						// Obtenemos el acta que vamos a borrar
						ScsActacomision acta = obtenerActa(actasItem, idInstitucion);

						if (acta != null) {
							// Obtenemos el listado de EJG asociados al Acta
							List<ScsEjgActa> listaEjgAsociadosActa = obtenerEjgActa(acta);

							// Comprobamos que la lista esta vacia o nula ya que si tiene elementos
							// asociados al acta no vamos a

							try {

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

									error.setDescription("Acta " + acta.getAnioacta() + "/" + acta.getNumeroacta()
											+ " NO eliminada porque tiene elementos asociados");

									deleteResponseDTO.setStatus(SigaConstants.KO);

									throw new SigaExceptions(
											"Ha ocurrido un error a la hora de borrar el actas, el acta tiene elementos asociados");
								}

							} catch (SigaExceptions e) {
								LOGGER.info("El acta tiene elementos asociados, no se ha podido borrar");
							}
						} else {
							throw new SigaExceptions("El acta es nula");
						}

					} else {
						error.setCode(500);

						error.setDescription("Faltan datos para encontrar el acta que se desea borrar");

						deleteResponseDTO.setStatus(SigaConstants.KO);
					}
				}
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

		List<ComboItem> comboItems = new ArrayList<ComboItem>();

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
			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener el combo de sufijos para actas");

				// Obtenemos el combo de sufijos para el acta

				String combo = scsActaExtendsMapper.comboSufijoActa(idInstitucion);
				if(combo != null && !combo.isEmpty()) {
					String [] listCombo = combo.split(","); 
					for(String valor : listCombo) {
						ComboItem itemA = new ComboItem();
						itemA.setLabel(valor);
						itemA.setValue(valor);
						comboItems.add(itemA);
					}
				}else {
					error.setCode(404);
					error.setDescription("Empty");

					LOGGER.warn("comboSufijoActa() -> Combo vacio.");
				}
				
				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener el combo de sufijos para actas");

				if (comboItems != null) {

					comboDTO.setCombooItems(comboItems);

				} 
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

		int response = 0;

		ScsActacomision acta = null;

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

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				// Obtenemos el acta que vamos a guardar si los campos de su clave primaria no
				// son nulos, si son nulos crearemos el acta

				if (actasItem.getAnioacta() != null && actasItem.getIdacta() != null) {
					LOGGER.info("NO DEBERIA ENTRAR AQUI SI VA A CREAR UN NUEVO ACTA ");
					acta = obtenerActa(actasItem, idInstitucion);

				}

				// Si es nulo significa que vamos a guardar un acta que es nueva por lo cual
				// debemos crear un objeto nuevo
				if (acta == null) {
					LOGGER.info(" DEBERIA ENTRAR AQUI SI VA A CREAR UN NUEVO ACTA ");
					acta = new ScsActacomision();

					// Con este dato sabremos si actualizar o crear un nuevo registro mas tarde
					actualizar = false;
				}

				LOGGER.info("Estoy aqui para ver el objeto actasItem " + actasItem);
				LOGGER.info("Estoy aqui para ver el objeto actasItem " + actasItem.getAnioacta() + " "
						+ actasItem.getFechareunion() + " " + actasItem.getFecharesolucion() + " "
						+ actasItem.getPendientes());

				// Comprobamos que se cumplen los requisitos de cada campo
				if (actasItem.getAnioacta() != null
						&& ((actualizar == true
								&& actasItem.getAnioacta() != String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
								|| actualizar == false)
						&& actasItem.getFechareunion() != null && actasItem.getNumeroacta() != null
						&& scsActaExtendsMapper.comprobarGuardarActaSufijo(actasItem, idInstitucion) == null
						&& scsActaExtendsMapper.comprobarGuardarActaPonente(actasItem, idInstitucion) != null) {

					LOGGER.info(
							"guardarActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para guardar el acta");

					acta.setAnioacta(Short.valueOf(actasItem.getAnioacta()));

					if (actualizar == false) {
						String idActa;
						
							idActa = scsActaExtendsMapper.obtenerIdActa(actasItem,
									idInstitucion);
									//Short.valueOf(actasItem.getIdacta()));
							if(idActa == null) {
								idActa = "0";
							}
							LOGGER.info("EL id DEL ACTA ES" + idActa);
							actasItem.setIdacta(idActa);
							acta.setIdacta(Long.valueOf(Integer.parseInt(idActa) + 1));
					}

					// Tengo que cambiarlo
					acta.setIdinstitucion(idInstitucion);

					if(!actualizar) {
						if(actasItem.getSufijo() != null){
							String aux = scsActaExtendsMapper.comprobarNumeroActa(getAnioHoy(),actasItem.getNumeroacta() + actasItem.getSufijo(),idInstitucion);
							if(aux != null){
								error.setCode(404);
								error.setDescription("InvalidNumActa");
								insertResponseDTO.setStatus(SigaConstants.KO);
								insertResponseDTO.setError(error);
								return insertResponseDTO;
							}else {
								acta.setNumeroacta(actasItem.getNumeroacta() + actasItem.getSufijo());
							}
						}
						
						if(actasItem.getSufijo() == null){
							String aux = scsActaExtendsMapper.comprobarNumeroActa(getAnioHoy(),actasItem.getNumeroacta(),idInstitucion);	
							if(aux != null){
								error.setCode(404);
								error.setDescription("InvalidNumActa");
								insertResponseDTO.setStatus(SigaConstants.KO);
								insertResponseDTO.setError(error);
								return insertResponseDTO;
							}else {
								acta.setNumeroacta(actasItem.getNumeroacta());
							}
							acta.setNumeroacta(actasItem.getNumeroacta());	
						}
					}
					

					if (actasItem.getHorainicio() != null) {
						//acta.setHorainicioreunion(actasItem.getHorainicio());
						SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						Date fechaFormat = null;
						String anioInicio = Integer.toString(actasItem.getFechareunion().getYear() + 1900);
						String mesInico =  Integer.toString(actasItem.getFechareunion().getMonth() + 1);
						String diaIncio = Integer.toString(actasItem.getFechareunion().getDate());
						String fecha = anioInicio+"/"+mesInico+"/"+diaIncio+" "+actasItem.getHorainicio();
						
						try {
						 fechaFormat = formato.parse(fecha);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						acta.setHorainicioreunion(fechaFormat);
					}
					if (actasItem.getHorafin() != null) {
						//acta.setHorafinreunion(actasItem.getHorafin());
						SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						Date fechaFormat = null;
						String anioFin = Integer.toString(actasItem.getFechareunion().getYear() + 1900);
						String mesFin =  Integer.toString(actasItem.getFechareunion().getMonth() + 1);
						String diaFin = Integer.toString(actasItem.getFechareunion().getDate());
						String fecha = anioFin+"/"+mesFin+"/"+diaFin+" "+actasItem.getHorafin();
						
						try {
							fechaFormat = formato.parse(fecha);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						acta.setHorafinreunion(fechaFormat);
					}
					if (actasItem.getIdpresidente() != null) {
						acta.setIdpresidente(Integer.parseInt(actasItem.getIdpresidente()));

					}
					if (actasItem.getIdsecretario() != null) {
						acta.setIdsecretario(Integer.parseInt(actasItem.getIdsecretario()));

					}
					if (actasItem.getMiembroscomision() != null) {
						acta.setMiembroscomision(actasItem.getMiembroscomision());

					}
					if (actasItem.getObservaciones() != null) {
						acta.setObservaciones(actasItem.getObservaciones());
					}
					if (actasItem.getPendientes() != null) {
						acta.setPendientes(actasItem.getPendientes());

					}
					if (actasItem.getFechareunion() != null) {
						acta.setFechareunion(actasItem.getFechareunion());

					}
					if (actasItem.getFecharesolucion() != null) {
						acta.setFecharesolucion(actasItem.getFecharesolucion());

					}
					if (usuario.getIdusuario() != null) {
						acta.setUsumodificacion(usuario.getIdusuario());
					}

					acta.setFechamodificacion(new Date());

					acta.setFechaintercambio(null);

					acta.setIdintercambio(null);

					// Actualizamos o creamos dependiendo del booleano
					if (actualizar == true) {
						try {
							if(actasItem.getFecharesolucion() != null) cerrarActaFuncion(acta,actasItem,response,usuario, idInstitucion);
							
							LOGGER.info("ACTUALIZA " + acta.getIdacta() + " " + acta.getNumeroacta() + " "+ acta.getAnioacta() + " " + acta.getIdinstitucion());
							
							if(actasItem.getObservaciones() != null)response = scsActacomisionMapper.updateByPrimaryKeyWithBLOBs(acta);
							else response = scsActacomisionMapper.updateByPrimaryKey(acta);
							
							
						}catch(Exception e) {
							error.setCode(404);
							error.setDescription("InvalidUpdate");
							insertResponseDTO.setStatus(SigaConstants.KO);
							insertResponseDTO.setError(error);
							return insertResponseDTO;
						}
						if (response == 0) {
							error.setCode(404);
							error.setDescription("InvalidUpdate");
							insertResponseDTO.setStatus(SigaConstants.KO);
							insertResponseDTO.setError(error);
							return insertResponseDTO;
						}
						insertResponseDTO.setStatus(SigaConstants.OK);

					} else {
						LOGGER.info("INSERTA");
						response = scsActacomisionMapper.insert(acta);
						insertResponseDTO.setId(acta.getIdacta().toString());
						if (response == 0) {
							error.setCode(404);
							error.setDescription("InvalidInsert");
							insertResponseDTO.setStatus(SigaConstants.KO);
							insertResponseDTO.setError(error);
							return insertResponseDTO;
						}

						insertResponseDTO.setStatus(SigaConstants.OK);
					}


					if (response == 0) {
						error.setCode(404);
						error.setDescription("InvalidUpdate");
						insertResponseDTO.setStatus(SigaConstants.KO);
						insertResponseDTO.setError(error);
						return insertResponseDTO;
					}
					LOGGER.info(
							"guardarActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para guardar el acta");
				}
			} else {

				error.setCode(500);

				error.setDescription("No se han cumplido los requisitos para guardar el acta");

				insertResponseDTO.setStatus(SigaConstants.KO);

				LOGGER.info("guardarActa() No se han cumplido los requisitos para guardar el acta");
			}

		} else {

			LOGGER.warn("guardarActa() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para guardar el Acta");

		error.setInfoURL(acta.getIdacta().toString());
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

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info("DATOS ACTA PARA BUSCAR ****************" + actasItem.getIdacta() + " "
						+ actasItem.getNumeroacta() + " " + actasItem.getIdinstitucion());
				
				// Obtenemos el acta con la que vamos a trabajar
				ScsActacomision acta = obtenerActa(actasItem, idInstitucion);

				// Obtenemos la lista de ejg asociados al acta gracias a la tabla scs_ejg_acta
				List<ScsEjgActa> listaEjgActa = obtenerEjgActa(acta);

				LOGGER.info("esta vacio la lista? ****************" + listaEjgActa.isEmpty());

				if (!listaEjgActa.isEmpty()) {

					// recorreremos toda la lista para sacar los ejg que esten asociados al acta para tratarlos
					for (ScsEjgActa scsEjgActa : listaEjgActa) {

						// Obtenemos el ejg
						ScsEjg ejgItem = obtenerEjg(scsEjgActa);

						if (ejgItem != null) {

							// Solo nos interesaran los ejg que tengan el tipo ratificacion 4 o 6
							if (ejgItem.getIdtiporatificacionejg() != null) {
								if (ejgItem.getIdtiporatificacionejg() == Short.valueOf("4")) {
									
									LOGGER.debug("Se añade el EJG: " + "E" + ejgItem.getAnio() + "/" + ejgItem.getNumejg() +" a la variable pendiente");
									
									// Pendientes guardara el nombre de todos los ejg asociados a ese acta
									pendientes += "E" + ejgItem.getAnio() + "/" + ejgItem.getNumejg() + ",";

									LOGGER.info("El objeto ejg es: " + scsEjgActa.getAnioejg() + " "
											+ scsEjgActa.getIdinstitucionejg() + " " + scsEjgActa.getIdtipoejg() + " "
											+ scsEjgActa.getNumeroejg());

									LOGGER.info("Si el ejg es 4");

									// Buscaremos el estado de ese ejg
									ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();

									exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion())
											.andIdtipoejgEqualTo(ejgItem.getIdtipoejg())
											.andAnioEqualTo(ejgItem.getAnio()).andNumeroEqualTo(ejgItem.getNumero())
											.andIdestadoejgEqualTo(Short.valueOf("9"));

									exampleEstado.setOrderByClause("FECHAMODIFICACION DESC");

									List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper
											.selectByExample(exampleEstado);
									
									// Sacaremos el primer elemento ya que sera el ultimo registro actualizado, por
									// lo cual sera el que este activo
									if (!listaEstadoEjg.isEmpty()) {
										
										LOGGER.info("Tamaño lista estado ejg" + listaEstadoEjg.size());
										
										ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);

										LOGGER.info("Añadiendo observaciones");
										scsEstadoejg
												.setObservaciones("Expediente pendiente de la CAJG. Se retira del acta "
														+ acta.getAnioacta() + "/" + acta.getNumeroacta());

										LOGGER.info("Updateando el estado ejg");

										response = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

										if (response == 0) {
											throw new SigaExceptions("No se han actualizado el estado del ejg");
										}
										
										ejgItem.setIdacta(null);
										ejgItem.setAnioacta(null);
										ejgItem.setIdinstitucionacta(null);
										
										response = scsEjgMapper.updateByPrimaryKey(ejgItem);
										
										if (response == 0) {
											throw (new SigaExceptions("Error al actuaizar el EJG"));
										}
										
										response = borrarActa(acta, ejgItem);

										if (response == 0) {
											throw new SigaExceptions("No se han podido borrar la relacion entre el acta y el ejg");
										}

										updateResponseDTO.setStatus(SigaConstants.OK);
									}

								} else if (ejgItem.getIdtiporatificacionejg() == Short.valueOf("6")) {

									LOGGER.info("Si el ejg es 6");

									LOGGER.info("El objeto ejg es: " + scsEjgActa.getAnioejg() + " "
											+ scsEjgActa.getIdinstitucionejg() + " " + scsEjgActa.getIdtipoejg() + " "
											+ scsEjgActa.getNumeroejg());
									
									LOGGER.debug("Se busca el ultimo idestadoejg == 20 y se pone fechabaja");
									
									LOGGER.debug("Se añade el EJG: " + "E" + ejgItem.getAnio() + "/" + ejgItem.getNumejg() +" a la variable pendiente");
									
									// Pendientes guardara el nombre de todos los ejg asociados a ese acta
									pendientes += "E" + ejgItem.getAnio() + "/" + ejgItem.getNumejg() + ",";
									
									ponerFechaBajaEstadosEjg(ejgItem, idInstitucion);

									ScsEstadoejg scsEstadoejg = new ScsEstadoejg();
									
									scsEstadoejg.setAnio(ejgItem.getAnio());
									scsEstadoejg.setIdinstitucion(idInstitucion);
									scsEstadoejg.setIdtipoejg(ejgItem.getIdtipoejg());
									scsEstadoejg.setNumero(ejgItem.getNumero());
									scsEstadoejg.setObservaciones("Expediente retirado del acta "
											+ acta.getAnioacta() + "/" + acta.getNumeroacta());
									scsEstadoejg.setIdestadoejg(Short.valueOf("21"));
									scsEstadoejg.setIdestadoporejg(obtenerUltimoEstadoPorEjg(ejgItem, idInstitucion)+1);
									scsEstadoejg.setFechainicio(new Date());
									scsEstadoejg.setFechamodificacion(new Date());
									scsEstadoejg.setAutomatico("0");
									scsEstadoejg.setUsumodificacion(usuario.getUsumodificacion());

									response = scsEstadoejgMapper.insert(scsEstadoejg);

									if (response == 0) {
										throw new SigaExceptions("No se ha insertado el estado \"Devuelto Colegio\" del ejg");
									}
									
									ejgItem.setIdacta(null);
									ejgItem.setAnioacta(null);
									ejgItem.setIdinstitucionacta(null);
									
									response = scsEjgMapper.updateByPrimaryKey(ejgItem);
									
									if (response == 0) {
										throw (new SigaExceptions("Error al actuaizar el EJG"));
									}
									
									response = borrarActa(acta, ejgItem);
	
									if (response == 0) {
										throw new SigaExceptions("No se han podido borrar la relacion entre el acta y el ejg");
									}
	
									updateResponseDTO.setStatus(SigaConstants.OK);
									
								}

							}

						}
						
						LOGGER.info("Seteando las observaciones");

						acta.setPendientes(pendientes);

						error.setDescription(acta.getPendientes());

						LOGGER.info("Updateando el acta");

						response = scsActacomisionMapper.updateByPrimaryKey(acta);

						if (response == 0) {
							throw new SigaExceptions("No se ha actualizado el acta");
						}
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
				}
			} else {
				LOGGER.info("ENTRO EN EL ELSE ****************");
				error.setCode(500);
				error.setDescription("No existen ejg relacionados con este acta");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}

		} else {

			LOGGER.warn("busquedaEJGComision() -> idInstitucion del token nula");

			throw new SigaExceptions("IdInstitucion del token nula");

		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");

		updateResponseDTO.setError(error);

		return updateResponseDTO;
	}
	
	private Long obtenerUltimoEstadoPorEjg(ScsEjg scsEjg, Short idInstitucion) throws SigaExceptions {

		ScsEstadoejgExample estadoejgExample = new ScsEstadoejgExample();

		LOGGER.info("Datos para filtrar el estado ejg " + scsEjg.getAnio() + " ");

		estadoejgExample.createCriteria().andAnioEqualTo(Short.valueOf(scsEjg.getAnio()))
				.andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(scsEjg.getIdtipoejg()))
				.andNumeroEqualTo(Long.valueOf(scsEjg.getNumero()));

		estadoejgExample.setOrderByClause("IDESTADOPOREJG DESC");

		List<ScsEstadoejg> estadoejgObject;

		try {
			estadoejgObject = scsEstadoejgMapper.selectByExample(estadoejgExample);
			LOGGER.info("AQUI BUSCANDO EL ESTADO DEL EJG PARA SABER EL TAMAÑO " + estadoejgObject.size());

		} catch (Exception e) {
			LOGGER.info("No se encuentra el estado para el ejg seleccionado");
			throw new SigaExceptions("No se encuentra el estado para el ejg seleccionado");
		}

		return estadoejgObject.get(0).getIdestadoporejg();
	}
	
	private int ponerFechaBajaEstadosEjg(ScsEjg scsEjg, Short idInstitucion) throws SigaExceptions {

		ScsEstadoejgExample estadoejgExample = new ScsEstadoejgExample();

		estadoejgExample.createCriteria().andAnioEqualTo(Short.valueOf(scsEjg.getAnio()))
				.andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(scsEjg.getIdtipoejg()))
				.andNumeroEqualTo(Long.valueOf(scsEjg.getNumero())).andFechabajaIsNull();

		estadoejgExample.setOrderByClause("IDESTADOPOREJG DESC");

		List<ScsEstadoejg> estadoejgObject;

		try {
			estadoejgObject = scsEstadoejgMapper.selectByExample(estadoejgExample);
		} catch (Exception e) {
			LOGGER.info("No se encuentran los estaodos para este ejg");
			throw new SigaExceptions("No se encuentran los estaodos para este ejg");

		}

		if (!estadoejgObject.isEmpty()) {
			LOGGER.info("tamaño de la lista " + estadoejgObject.size());
			for (ScsEstadoejg scsEstadoejg : estadoejgObject) {
				if (scsEstadoejg.getIdestadoejg() == 21) {
					scsEstadoejg.setFechabaja(new Date());
					int resultado = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);
					LOGGER.info("Se ha actualizado la fecha de baja? " + resultado);
				}
			}
		}
		return estadoejgObject.size();
	}

	@Override
	@Transactional
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

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info("abrirActa() / buscando el acta ");

				LOGGER.info("abrirActa() / valores para buscar el acta = " + actasItem.getAnioacta() + " id acta = "
						+ actasItem.getIdacta());

				// Buscamos el acta que vamos a abrir
				ScsActacomision acta = obtenerActa(actasItem, idInstitucion);

				LOGGER.info(
						"abrirActa() / el acta es: " + acta.getAnioacta() + acta.getIdinstitucion() + acta.getIdacta());
				LOGGER.info("abrirActa() / y tiene la fecha resolucion a : " + acta.getFecharesolucion());

				// Miramos que la fecha de resolucion sea distinta de nula ya que significa que
				// esta
				// cerrada, de lo contrario no hariamos nada porque ya esta abierta
				try {
					if (acta.getFecharesolucion() != null) {

						LOGGER.info("abrirActa() / acta encontrada");

						LOGGER.info("abrirActa() / buscando los ejg asociados al acta");

						LOGGER.info("abrirActa() / valores acta institucion = " + acta.getIdinstitucion()
								+ " id acta = " + acta.getIdacta() + " anioacta =  " + acta.getAnioacta());

						List<ScsEjgActa> listaEjgAsociadosActa = obtenerEjgActa(acta);

						// Buscamos los ejg asociados al acta

						if (!listaEjgAsociadosActa.isEmpty()) {

							LOGGER.info("tamaño de la lista de ejg asociados" + listaEjgAsociadosActa.size());
							for (ScsEjgActa scsEjgActalista : listaEjgAsociadosActa) {

								List<ScsEjg> ejgItemLista = obtenerListaEjg(scsEjgActalista);

								if (!ejgItemLista.isEmpty()) {
									for (ScsEjg ejg : ejgItemLista) {

										// Utilizamos este booleano para saber si existen mas actas asociadas a este ejg
										// y estan abiertas
										boolean masActasAbiertas = false;

										LOGGER.info("abrirActa() / valores ejg institucion = " + ejg.getIdinstitucion()
												+ " idtipoejg =  " + ejg.getIdtipoejg() + " anio =  " + ejg.getAnio()
												+ " numero =  " + ejg.getNumero());

										LOGGER.info("abrirActa() / buscando los estadosejg del ejg en cuestion");

										// Obtenemos la relaciones con las actas para este ejg
										List<ScsEjgActa> listaEjgActa = obtenerEjgActa(ejg);

										LOGGER.info("abrirActa() / TAMAÑO DE LA LISTA CON LA RELACION EJGACTA "
												+ listaEjgActa.size());

										if (!listaEjgActa.isEmpty()) {

											// Recorremos la lista de ejg y actas asociados en busqueda de un acta
											// abierta
											for (ScsEjgActa scsEjgActa : listaEjgActa) {

												// Sacamos las actas asociadas al ejg
												ScsActacomision actaComision = obtenerActa(scsEjgActa);

												// Si la fecha resolucion esta a nula significa que el acta esta abierta
												if (actaComision.getFecharesolucion() == null) {
													LOGGER.info(
															"ENTRO EN LA CREACION DEL MENSAJE PARA DECIR QUE ACTA ESTA ABIERTA Y CERRAR EL PROCESO");

													masActasAbiertas = true;

													informacionEjgActaAbierta += " - Ejg " + ejg.getAnio() + "/"
															+ ejg.getNumero() + " abierto para el acta "
															+ actaComision.getAnioacta() + "/"
															+ actaComision.getNumeroacta() + " - ";
													error.setCode(500);
													error.setDescription(informacionEjgActaAbierta);
													updateResponseDTO.setStatus(SigaConstants.KO);
													throw new SigaExceptions("El objeto de estado para el ejg es nulo");
												}

											}
										}

										if (masActasAbiertas == false) {

											LOGGER.info(
													"AQUI LLEGA SI TODO HA SALIDO BIEN Y COGEMOS Y PONEMOS LA FECHA RESOLUCIONCAJ DEL EJG A NULO");
											ejg.setFecharesolucioncajg(null);
											scsEjgMapper.updateByPrimaryKey(ejg);

											String idEstadoPorEjg = scsActaExtendsMapper.getEstadosEjg(
													ejg.getIdinstitucion(), ejg.getIdtipoejg(), ejg.getAnio(),
													ejg.getNumero());

											LOGGER.info("ejg.getIdinstitucion()" + ejg.getIdinstitucion());
											LOGGER.info("Long.valueOf(idEstadoPorEjg)" + Long.valueOf(idEstadoPorEjg));
											LOGGER.info("ejg.getIdinstitucion()" + ejg.getIdinstitucion());
											LOGGER.info("ejg.getIdtipoejg()" + ejg.getIdtipoejg());
											LOGGER.info("ejg.getIdtipoejg()" + ejg.getIdtipoejg());

											// Buscamos el estado del ejg
											ScsEstadoejg estadoejgObject = obtenerEstadoEjg(ejg, idEstadoPorEjg);

											LOGGER.info(
													"busquedaActas() / encontrado los estadosejg del ejg en cuestion");

											if (estadoejgObject != null) {

												LOGGER.info("scsestadoejg no es nulo ");

												if (estadoejgObject.getIdestadoejg().equals(Short.valueOf("10"))) {

													LOGGER.info("scsestadoejg tiene el idestadopor ejg a 10  ");

													estadoejgObject.setFechabaja(new Date());

													int resultado = scsEstadoejgMapper
															.updateByPrimaryKey(estadoejgObject);

													if (resultado == 0) {
														throw new SigaExceptions(
																"No se han actualizado el estado del ejg");
													}

													LOGGER.info("ha actualizado el registro????? " + resultado);
												}
											} else {
												error.setCode(500);
												error.setDescription("El objeto de estado para el ejg es nulo");
												updateResponseDTO.setStatus(SigaConstants.KO);
												throw new SigaExceptions("El objeto de estado para el ejg es nulo");

											}
										}

									}
								}
							}

							LOGGER.info("AQUI LLEGA SI TODO HA SALIDO BIEN Y COGEMOS ABRIMOS EL ACTA");
							acta.setFecharesolucion(null);
							scsActacomisionMapper.updateByPrimaryKey(acta);
							updateResponseDTO.setStatus(SigaConstants.OK);

						} else {
							acta.setFecharesolucion(null);
							scsActacomisionMapper.updateByPrimaryKey(acta);
							updateResponseDTO.setStatus(SigaConstants.OK);
						}

						LOGGER.info(
								"abrirActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");

					} else {

						error.setCode(500);
						error.setDescription("El acta ya esta abierta");
						updateResponseDTO.setStatus(SigaConstants.KO);
					}

				} catch (SigaExceptions e) {
					LOGGER.info("AQUI ESTA EL CATCH DE QUE ALGO HA SALIDO MAL****************");
					LOGGER.info("Nos se ha podido completar el abrir el acta " + e.getMsg());
					LOGGER.info("AQUI ESTA EL CATCH DE QUE ALGO HA SALIDO MAL****************");

				}
			}
		} else {
			error.setCode(500);
			error.setDescription("el id de la instuticon es nulo");
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");

		updateResponseDTO.setError(error);

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO cerrarActa(ActasItem actasItem, HttpServletRequest request) throws Exception {

		LOGGER.info("cerrarActa() -> Entrada al servicio para obtener el colegiado" + actasItem.getFecharesolucion());

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

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"cerrarActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener las actas");
				
				// Obtenemos el acta que vamos a cerrar
				ScsActacomision acta = obtenerActa(actasItem, idInstitucion);

				if (acta.getFecharesolucion() == null) {

					if (actasItem.getFecharesolucion() == null) {
						actasItem.setFecharesolucion(new Date());
					}

					cerrarActaFuncion(acta, actasItem,response,usuario, idInstitucion);
					
					// Ponemos fecha de hoy para el cierre
					acta.setFecharesolucion(actasItem.getFecharesolucion());
					scsActacomisionMapper.updateByPrimaryKey(acta);
					updateResponseDTO.setStatus(SigaConstants.OK);
				}else {
					error.setCode(500);
					error.setDescription("El acta ya esta cerrada");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
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

	private void cerrarActaFuncion(ScsActacomision acta,ActasItem actasItem , int response , AdmUsuarios usuario, Short idInstitucion) throws Exception {
		
		List<ScsEjgActa> listaEjgActa = obtenerEjgActa(acta);

		LOGGER.info("Lista de ejgasociados conseguida");

		if (!listaEjgActa.isEmpty()) {

			LOGGER.info("*****************lista de ejg asociados al acta no es nula************** "
					+ listaEjgActa.size());

			for (ScsEjgActa scsEjgActa : listaEjgActa) {

				List<ScsEjg> ejgItemLista = obtenerListaEjg(scsEjgActa);

				if (!ejgItemLista.isEmpty()) {

					LOGGER.info("*****************tamaño de la lista de ejg asociados al acta**************"
							+ ejgItemLista.size());

					for (ScsEjg ejgItem : ejgItemLista) {

						LOGGER.info("DATOS DEL EJG NADA MAS EMPEZAR EL FOR =  " + ejgItem.getIdinstitucion()
								+ " ANIO EJG = " + ejgItem.getAnio() + " idtipoeEJG = "
								+ ejgItem.getIdtipoejg() + " NUMERO = " + ejgItem.getNumero());

						if (ejgItem.getIdtiporatificacionejg() == null) {

							// Buscamos el estado del ejg remitido comision y le añadimos a la observacion
							// esta informacion
							LOGGER.info(
									"*****************si el idfundamentojuridico es nulo**************");

							List<ScsEstadoejg> listaEstadosEjg = obtenerEstadosEjg(ejgItem,
									ejgItem.getIdinstitucion());

							if (!listaEstadosEjg.isEmpty()) {
								LOGGER.info(
										"*****************tamaño de la lista de estados ejg ************** "
												+ listaEstadosEjg.size());

								for (ScsEstadoejg estado : listaEstadosEjg) {

									if (estado.getIdestadoejg() == 9) {
										LOGGER.info("*****************el estado es 9 ************** ");
										estado.setObservaciones(estado.getObservaciones()
												+ "Expediente sin Resolución. Se retira del acta "
												+ ejgItem.getAnio() + "/" + ejgItem.getNumejg());
										response = scsEstadoejgMapper.updateByPrimaryKey(estado);
										if (response == 0)
											throw (new Exception(
													"Error al eliminar el dictamen asociado al EJG"));
									}
								}
							}
							
							ejgItem.setIdacta(null);
							ejgItem.setAnioacta(null);
							ejgItem.setIdinstitucionacta(null);
							
							response = scsEjgMapper.updateByPrimaryKey(ejgItem);
							
							if (response == 0)
								throw (new Exception(
										"Error al actuaizar el EJG"));
							
							LOGGER.info(
									"*****************datos que se van a borrar de la tabla ejgacta ACTA **************"
											+ acta.getIdacta() + " " + acta.getAnioacta() + " "
											+ acta.getIdinstitucion());
							LOGGER.info(
									"*****************datos que se van a borrar de la tabla ejgacta EJG ************** =  "
											+ ejgItem.getIdinstitucion() + " ANIO EJG = "
											+ ejgItem.getAnio() + " idtipoeEJG = " + ejgItem.getIdtipoejg()
											+ " NUMERO = " + ejgItem.getNumero());

							response = borrarActa(acta, ejgItem);

							LOGGER.info("RESPONSE " + response);

							LOGGER.info("borro la relacion ejgacta");

						} else {

							LOGGER.info("el fundamento juridico no es nulo");
							//Este método sustituye a un trigger y parametrizamos su ejecución
							GenParametrosExample exampleParam = new GenParametrosExample();
							exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS)
								.andParametroEqualTo("ENABLETRIGGERSEJG")
								.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
							exampleParam.setOrderByClause("IDINSTITUCION DESC");

							List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);
							
							if(parametrosTrigger != null && !parametrosTrigger.isEmpty() 
									&& parametrosTrigger.get(0).getValor().equals("1")) {
								response = actualizarFecharesolucioncajg(ejgItem.getIdinstitucion(), usuario,
										ejgItem);
	
								if (response == 0)
									throw (new Exception(
											"Error en el triggerEjgUpdatesResol al actualizar la fecha de resolucion del acta asociada"
													+ " asociada a un EJG"));
							}
							ejgItem.setFecharesolucioncajg(actasItem.getFecharesolucion());

							response = scsEjgMapper.updateByPrimaryKey(ejgItem);

							if (response == 0) {
								throw (new Exception(
										"No se ha podido actualizar el objeto ejg con la nueva observacion y la nueva fecha resolucion"));
							}

							LOGGER.info(
									"VALORES PARA BUSCAR EL ESTADO DEL EJG =  " + ejgItem.getIdinstitucion()
											+ " ANIO EJG = " + ejgItem.getAnio() + " idtipoeEJG = "
											+ ejgItem.getIdtipoejg() + " NUMERO = " + ejgItem.getNumero());

							List<ScsEstadoejg> listaEstadosEjg = obtenerEstadosEjg(ejgItem,
									ejgItem.getIdinstitucion());

							LOGGER.info("CUANTOS ESTADOS TIENE ESTE EJG? " + listaEstadosEjg.size());

							for (ScsEstadoejg ScsEstadoejg : listaEstadosEjg) {
								LOGGER.info("ESTOS SON LOS DATOS DEL ESTADO EJG ");

								LOGGER.info("VALORES DENTRO DEL FOR PARA VER LOS DATOS DEL ESTADO EJG =  "
										+ ScsEstadoejg.getIdinstitucion() + " ANIO EJG = "
										+ ScsEstadoejg.getAnio() + " idtipoeEJG = "
										+ ScsEstadoejg.getIdtipoejg() + " NUMERO = "
										+ ScsEstadoejg.getNumero() + " IDESTADOPOREJG = "
										+ ScsEstadoejg.getIdestadoporejg());
							}

							if (!listaEstadosEjg.isEmpty()) {

								LOGGER.info("CREAMOS EL ESTADO ");

								response = crearEstado(actasItem, ejgItem, listaEstadosEjg);

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

							response = scsEjgActaExtendsMapper.updateResolucion(actasItem, idInstitucion);

							if (response == 0) {
								throw (new Exception("No se ha podido actualizar la relacion del ejg con el acta"));
							}

						}

					}

				}
			}
		}
	}
	
	@Override
	public ScsActacomision getActa(ActasItem actasItem, HttpServletRequest request) {

		LOGGER.info("comboSufijoActa() -> Entrada al servicio para obtener el colegiado");

		Error error = new Error();

		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsActacomision acta = new ScsActacomision();

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
			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				usuario.setIdinstitucion(idInstitucion);

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener el combo de sufijos para actas");

				// Obtenemos el combo de sufijos para el acta

				acta = obtenerActa(actasItem, idInstitucion);

				List<ScsEjgActa> listaEjgActaRelacionado = obtenerEjgActa(acta);

				if (!listaEjgActaRelacionado.isEmpty()) {
					acta.setUsumodificacion(listaEjgActaRelacionado.size());
				}

				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener el combo de sufijos para actas");
			}
		} else {

			error.setCode(500);

			error.setDescription("Fallo a la hora de devolver los sufijos, idInstitucion del token nula");

			LOGGER.warn("comboSufijoActa() -> idInstitucion del token nula");

		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los sufijos de las actas");

		return acta;
	}

	@Override
	public String getNumActa( HttpServletRequest request) throws SigaExceptions {
		LOGGER.info("comboSufijoActa() -> Entrada al servicio para obtener el colegiado");

		String token = request.getHeader("Authorization");


		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ActasItem acta = new ActasItem();
		int aux = 1;
		acta.setNumeroacta(String.valueOf(aux));
		
		if(idInstitucion != null) {
		
		String anio = getAnioHoy();

		String combo = scsActaExtendsMapper.comboSufijoActa(idInstitucion);

			if( combo != null && !combo.isEmpty()  ) {
				String [] listCombo = combo.split(","); 
				acta.setNumeroacta(getNumeroActaSufijo(idInstitucion.toString(), anio, listCombo[0]));
			}else {
				acta.setNumeroacta(getNumeroActa(idInstitucion.toString(), anio));
			}
			
		}
		
		return acta.getNumeroacta();
	}
	
	@Override
	public String getNumActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions {

		Error error = new Error();

		String token = request.getHeader("Authorization");

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ActasItem acta = new ActasItem();
		
		if(idInstitucion != null) {

		acta.setNumeroacta(getNumeroActaSufijo(idInstitucion.toString(), actasItem.getAnioacta(), actasItem.getSufijo()));

		}	
		
		return acta.getNumeroacta();
	}
	
	private String getAnioHoy() {
		Date date = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        String anio = formato.format(date);
        return anio;
	}
	
	private String getNumeroActa(String idInstitucion, String anio) {
		String numActa;
		int nextNum;
		try {
			numActa = scsActaExtendsMapper.getNuevoNumActaSimple(idInstitucion,anio );
			nextNum = Integer.parseInt(numActa)+1;
		}catch(Exception e) {
			numActa = scsActaExtendsMapper.getNuevoNumActaAuxSimple(idInstitucion,anio );
			nextNum = Integer.parseInt(numActa)+1;
		}
		return String.valueOf(nextNum);
		
	}
	
	private String getNumeroActaSufijo(String idInstitucion, String anio, String sufijo) {
		String numActa;
		int nextNum;
		try {
			numActa = scsActaExtendsMapper.getNuevoNumActaComp(idInstitucion,anio,sufijo );
			numActa = UtilidadesString.replaceAllIgnoreCase(numActa, sufijo, "");
			nextNum = Integer.parseInt(numActa)+1;
		}catch(Exception e) {
			numActa = scsActaExtendsMapper.getNuevoNumActaAuxComp(idInstitucion,anio,sufijo );
			numActa = UtilidadesString.replaceAllIgnoreCase(numActa, sufijo, "");
			nextNum = Integer.parseInt(numActa)+1;
		}
		return String.valueOf(nextNum);
		
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

		response = gestionEJGServiceImpl.triggersEjgUpdatesResol(resolEjg, usuario, ejgItem.getIdinstitucion());

		return response;
	}

	/**
	 * @param actasItem
	 * @param ejgItem
	 * @param listaEstadoEjg
	 * @throws ParseException
	 */
	private int crearEstado(ActasItem actasItem, ScsEjg ejgItem, List<ScsEstadoejg> listaEstadoEjg)
			throws ParseException {

		int resultado;
		int numeroMayor = -99;

		for (ScsEstadoejg estado : listaEstadoEjg) {
			if (estado.getIdestadoporejg() > numeroMayor) { //
				numeroMayor = estado.getIdestadoporejg().intValue();
				LOGGER.info("*******************************" + numeroMayor);

			}
		}

		ScsEstadoejg scsEstadoejg = new ScsEstadoejg();

		scsEstadoejg.setIdestadoejg(Short.valueOf("10"));

		scsEstadoejg.setAnio(Short.valueOf(ejgItem.getAnio()));

		scsEstadoejg.setNumero(Long.valueOf(ejgItem.getNumero()));

		scsEstadoejg.setIdtipoejg(Short.valueOf(ejgItem.getIdtipoejg()));

		scsEstadoejg.setIdinstitucion(Short.valueOf(ejgItem.getIdinstitucion()));

		scsEstadoejg.setIdestadoporejg(Long.valueOf(numeroMayor + 1));

		scsEstadoejg.setUsumodificacion(0);

		scsEstadoejg.setFechainicio(new Date());

		scsEstadoejg.setFechamodificacion(new Date());

		scsEstadoejg.setAutomatico("0");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date fechaResolucion = actasItem.getFecharesolucion();

		Date fechaSinHora;

		fechaSinHora = formatter.parse(formatter.format(fechaResolucion));

		scsEstadoejg.setFechainicio(fechaSinHora);

		LOGGER.info("poniendo fecha inicio");

		LOGGER.info("obtengo el objeto estado para el ejg " + scsEstadoejg.getIdinstitucion() + " "
				+ scsEstadoejg.getIdtipoejg() + " " + scsEstadoejg.getAnio() + " " + scsEstadoejg.getNumero() + " "
				+ scsEstadoejg.getIdestadoporejg() + " " + scsEstadoejg.getFechainicio() + " "
				+ scsEstadoejg.getIdestadoejg());

		resultado = scsEstadoejgMapper.insert(scsEstadoejg);

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

		scsEjgActaExample.createCriteria().andIdinstitucionactaEqualTo(acta.getIdinstitucion())
				.andIdactaEqualTo(acta.getIdacta()).andAnioactaEqualTo(acta.getAnioacta());

		List<ScsEjgActa> listaEjgAsociadosActa = scsEjgActaMapper.selectByExample(scsEjgActaExample);

		return listaEjgAsociadosActa;
	}

	private List<ScsEjg> obtenerListaEjg(ScsEjgActa scsEjgActa) {

		ScsEjgExample scsEjgExample = new ScsEjgExample();

		scsEjgExample.createCriteria().andIdinstitucionEqualTo(scsEjgActa.getIdinstitucionejg())
				.andNumeroEqualTo(scsEjgActa.getNumeroejg()).andAnioEqualTo(scsEjgActa.getAnioejg())
				.andIdtipoejgEqualTo(scsEjgActa.getIdtipoejg());

		List<ScsEjg> listaEjgAsociadosActa = scsEjgMapper.selectByExample(scsEjgExample);

		return listaEjgAsociadosActa;
	}

	private ScsActacomision obtenerActa(ActasItem actasItem, Short idInstitucion) {

		LOGGER.info("DATOS DEL ACTA QUE SE ESTA ENVIANDO DESDE EL FRONT" + actasItem.getAnioacta() + " "
				+ actasItem.getIdacta() + " " + actasItem.getIdinstitucion());

		ScsActacomision acta = null;

		ScsActacomisionKey key = new ScsActacomisionKey();

		key.setAnioacta(Short.valueOf(actasItem.getAnioacta()));
		
		key.setIdacta(Long.valueOf(actasItem.getIdacta()));

		key.setIdinstitucion(idInstitucion);

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
	private ScsEjgResolucion obtenerEjgResolucion(ScsEjg scsEjg) {

		ScsEjgResolucionKey scsEjgResolucionKey = new ScsEjgResolucionKey();

		LOGGER.info("****************************************" + scsEjg.getAnio() + " " + scsEjg.getIdinstitucion()
				+ " " + scsEjg.getIdtipoejg() + " " + scsEjg.getNumero());

		scsEjgResolucionKey.setAnio(scsEjg.getAnio());

		scsEjgResolucionKey.setIdinstitucion(scsEjg.getIdinstitucion());

		scsEjgResolucionKey.setIdtipoejg(scsEjg.getIdtipoejg());

		scsEjgResolucionKey.setNumero(scsEjg.getNumero());

		ScsEjgResolucion scsEjgResolucionItem = scsEjgResolucionMapper.selectByPrimaryKey(scsEjgResolucionKey);

		return scsEjgResolucionItem;
	}

	/**
	 * @param scsEjgActa
	 * @return
	 */
	private ScsEjg obtenerEjg(ScsEjgActa scsEjgActa) {

		ScsEjgKey ejgkey = new ScsEjgKey();

		LOGGER.info("DATOS DEL EJG QUE VAMOS A COMPROBAR " + scsEjgActa.getAnioejg() + " "
				+ scsEjgActa.getIdinstitucionejg() + " " + scsEjgActa.getIdtipoejg() + " " + scsEjgActa.getNumeroejg());

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

	private List<ScsEstadoejg> obtenerEstadosEjg(ScsEjg scsEjg, Short idInstitucion) throws SigaExceptions {

		ScsEstadoejgExample estadoejgExample = new ScsEstadoejgExample();

		LOGGER.info("Datos para filtrar el estado ejg " + scsEjg.getAnio() + " ");

		estadoejgExample.createCriteria().andAnioEqualTo(Short.valueOf(scsEjg.getAnio()))
				.andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(scsEjg.getIdtipoejg()))
				.andNumeroEqualTo(Long.valueOf(scsEjg.getNumero()));

		estadoejgExample.setOrderByClause("FECHAMODIFICACION DESC");

		List<ScsEstadoejg> estadosejg;

		LOGGER.info("AQUI BUSCANDO EL ESTADO DEL EJG");

		try {
			estadosejg = scsEstadoejgMapper.selectByExample(estadoejgExample);
			LOGGER.info("AQUI BUSCANDO EL ESTADO DEL EJG PARA SABER EL TAMAÑO " + estadosejg.size());

		} catch (Exception e) {
			LOGGER.info("No se encuentra el estado para el ejg seleccionado");
			throw new SigaExceptions("No se encuentra el estado para el ejg seleccionado");
		}

		return estadosejg;
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

	@Override
	public CenInstitucion getAbreviatura(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		String dni = UserTokenUtils.getDniFromJWTToken(token);

		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		CenInstitucion institucion = new CenInstitucion();

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

			if (usuarios != null && usuarios.size() > 0) {
				
				institucion = cenInstitucionMapper.selectByPrimaryKey(idInstitucion);
				
			}
		}

		return institucion;
	}

}