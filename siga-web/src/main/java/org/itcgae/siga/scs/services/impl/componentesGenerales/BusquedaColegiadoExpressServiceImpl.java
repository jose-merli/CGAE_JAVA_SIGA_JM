package org.itcgae.siga.scs.services.impl.componentesGenerales;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.IBusquedaColegiadosExpressService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.internal.filter.ValueNode.UndefinedNode;

@Service
public class BusquedaColegiadoExpressServiceImpl implements IBusquedaColegiadosExpressService {

	private Logger LOGGER = Logger.getLogger(BusquedaColegiadoExpressServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosextendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	public ColegiadoJGDTO busquedaColegiadosExpress(String colegiadoJGItem, HttpServletRequest request) {

		LOGGER.info("busquedaColegiadosExpress() -> Entrada al servicio para obtener el colegiado");

		ColegiadoJGDTO colegiadoJGDTO = new ColegiadoJGDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logueado
				usuario.setIdinstitucion(idInstitucion);

				LOGGER.info("busquedaColegiadosExpress() / cenPersonaExtendsMapper.busquedaColegiadoExpress() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado");
				colegiadoJGDTO.setColegiadoJGItem(cenPersonaExtendsMapper.busquedaColegiadoExpress(colegiadoJGItem, null, idInstitucion.toString()));
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
			} else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return colegiadoJGDTO;
	}
	@Override
	public ColegiadosSJCSDTO busquedaColegiadoEJG(ColegiadosSJCSItem datos, HttpServletRequest request) {
		ColegiadosSJCSDTO responsedto = new ColegiadosSJCSDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		Error error = new Error();

		if (idInstitucion != null) {
			LOGGER.debug("busquedaColegiadosExpress.busquedaColegiadoEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("busquedaColegiadosExpress.busquedaColegiadoEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("busquedaColegiadosExpress.busquedaColegiadoEJG() -> Entrada para obtener los datos del colegiado segun los filtros");

				try {
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("CEN")
							.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
					LOGGER.info(
							"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
					LOGGER.info(
							"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					if (tamMax != null) {
						tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
					} else {
						tamMaximo = null;
					}
//					if(datos.getIdGuardia() != null) {
//						datos.setIdGuardia(datos.getIdGuardia().substring(0,datos.getIdGuardia().length>0));
//					}
					
//					if(datos.getIdTurno() != null) {
//						datos.setIdTurno(datos.getIdTurno().substring(0,datos.getIdTurno().length-1));
//					}
					if(datos.getIdInstitucion()==null || datos.getIdInstitucion().isEmpty()) {
						datos.setIdInstitucion(idInstitucion.toString());
					}

					List<ColegiadosSJCSItem> colegiadosSJCSItemList = scsEjgExtendsMapper.busquedaColegiadoEJG(datos,
							usuarios.get(0).getIdlenguaje(), tamMaximo);

					
					if ((colegiadosSJCSItemList != null) && tamMaximo != null
							&& (colegiadosSJCSItemList.size()) > tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						responsedto.setError(error);
						colegiadosSJCSItemList.remove(colegiadosSJCSItemList.size() - 1);
					}

					responsedto.setColegiadosSJCSItem(colegiadosSJCSItemList);
					
					
					/*for(int x=0;x<colegiadosSJCSItemList.size();x++) {
						List<ColegiadosSJCSItem> tieneTurno=scsEjgExtendsMapper.tieneTurnos(colegiadosSJCSItemList.get(x).getIdInstitucion(), colegiadosSJCSItemList.get(x).getIdPersona());
//						List<String> tieneGuardia = null;
						List<List<String>> tieneGuardia = new ArrayList<List<String>>();
						List<String> guardiasPendientes = new ArrayList<String>();
						
						if(tieneTurno != null && !tieneTurno.isEmpty()) {
							colegiadosSJCSItemList.get(x).setTieneTurno("Si");

							for(int z = 0; z<tieneTurno.size();z++) {
								tieneGuardia.add(scsEjgExtendsMapper.tieneGuardias(colegiadosSJCSItemList.get(x).getIdInstitucion(), tieneTurno.get(z)));
							}
							
							for(int z = 0; z<tieneTurno.size();z++) {
								for(int guardia = 0; guardia<tieneGuardia.get(z).size(); guardia++) {
									guardiasPendientes.add(scsEjgExtendsMapper.tieneGuardiasPendientes(colegiadosSJCSItemList.get(x).getIdInstitucion(), tieneTurno.get(z),tieneGuardia.get(z).get(guardia)));
								}
							}

							colegiadosSJCSItemList.get(x).setGuardiasPendientes(String.valueOf(guardiasPendientes.size()));
							
						}else {
							colegiadosSJCSItemList.get(x).setTieneTurno("No");
						}
						
						if(tieneGuardia.size() != 0) {
							colegiadosSJCSItemList.get(x).setTieneGuardia("Si");
						}else {
							colegiadosSJCSItemList.get(x).setTieneGuardia("No");
						}
					}*/

				} catch (Exception e) {
					LOGGER.error(
							"BusquedaEJGServiceImpl.busquedaColegiadoEJG() -> Se ha producido un error al tratar de obtener los datos del colegiado. ",
							e);

					error.setCode(500);
					error.setDescription("Error al obtener los datos.");
					error.setMessage(e.getMessage());

					responsedto.setError(error);
				}
			}
		}
		return responsedto;
	}

	@Override
	public ComboDTO comboTurnos(String pantalla, String idTurno, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboTurnosTipo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("comboTurnosTipo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("busquedaColegiadosExpress.comboTurnos() -> Entrada al servicio para obtener los turnos. Viene desde "+ pantalla);

				if (pantalla != null && !pantalla.isEmpty()) {
					comboItems = scsTurnosextendsMapper.comboTurnosBusqueda(idInstitucion, pantalla, idTurno);
				}

				LOGGER.info("busquedaColegiadosExpress.comboTurnos()-> Salida del servicio para obtener los datos del combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("busquedaColegiadosExpress.comboTurnos() -> Salida del servicio para obtener los turnos");
		return comboDTO;
	}
	
	@Override
	public String getTipoTurnos(String idTurno, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				String idTipoTurno = null;
				if (idInstitucion != null) {
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
					LOGGER.info("getTipoTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

					LOGGER.info("getTipoTurnos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

					if (usuarios != null && usuarios.size() > 0) {

						LOGGER.info("busquedaColegiadosExpress.getTipoTurnos() -> Entrada al servicio para obtener el tipo del turnos");
						List<String> tipoTurnos = scsTurnosextendsMapper.getTipoTurno(idInstitucion, idTurno);
						if (!tipoTurnos.isEmpty()) {
							idTipoTurno = tipoTurnos.get(0);
						}
						
					}
				}
				LOGGER.info("busquedaColegiadosExpress.getTipoTurnos() -> Salida del servicio par aobtener el tipo del turnos");
		return idTipoTurno;
	}

}

