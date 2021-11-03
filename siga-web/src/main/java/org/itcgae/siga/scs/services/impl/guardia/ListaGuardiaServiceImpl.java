package org.itcgae.siga.scs.services.impl.guardia;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsConfConjuntoGuardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsConjuntoguardiasExtendsMapper;
import org.itcgae.siga.scs.services.guardia.ListaGuardiaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListaGuardiaServiceImpl implements ListaGuardiaService {

	private final Logger LOGGER = Logger.getLogger(ActuacionAsistenciaServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsConjuntoguardiasExtendsMapper scsConjuntoguardiasExtendsMapper;
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	@Autowired
	private ScsConfConjuntoGuardiasExtendsMapper scsConfConjuntoGuardiasExtendsMapper;

	@Override
	public ListaGuardiasDTO searchListaGuardias(HttpServletRequest request, ListaGuardiasItem filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ListaGuardiasDTO listaGuardiasDTO = new ListaGuardiasDTO();
		List<ListaGuardiasItem> listaGuardiasItems = new ArrayList<>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchListaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchListaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
					this.LOGGER.info("ListaGuardiaServiceImpl.getGuardiasFromLista() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					List<GenParametros> tamMax = this.genParametrosExtendsMapper.selectByExample(genParametrosExample);
					this.LOGGER.info("ListaGuardiaServiceImpl.getGuardiasFromLista() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					Integer tamMaximo;
					if (tamMax != null) {
						tamMaximo = Integer.valueOf(((GenParametros)tamMax.get(0)).getValor());
					} else {
						tamMaximo = null;
					}

					listaGuardiasItems = scsConjuntoguardiasExtendsMapper.searchListaGuardias(filtro,idInstitucion,tamMaximo);

					if (tamMaximo != null && listaGuardiasItems.size() > tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						listaGuardiasDTO.setError(error);
						listaGuardiasItems.remove(listaGuardiasItems.size() - 1);
					}

					listaGuardiasDTO.setListaGuardiasItems(listaGuardiasItems);
				}
			}
		}catch(Exception e) {
			LOGGER.error("searchListaGuardias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las listas de guardias: " + e);
			error.description("Error al buscar las listas de guardias: " + e);
			listaGuardiasDTO.setError(error);
		}
		return listaGuardiasDTO;
	}

	@Transactional
	public InsertResponseDTO saveListaGuardias(HttpServletRequest request, ListaGuardiasItem lista) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int affectedRows = 0;

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				this.LOGGER.info("saveListaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				this.LOGGER.info("saveListaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty() && lista != null) {
					ScsConjuntoguardias scsConjuntoguardias;
					if (!UtilidadesString.esCadenaVacia(lista.getIdLista())) {
						ScsConjuntoguardiasKey scsConjuntoguardiasKey = new ScsConjuntoguardias();
						scsConjuntoguardiasKey.setIdconjuntoguardia(Short.valueOf(lista.getIdLista()));
						scsConjuntoguardiasKey.setIdinstitucion(idInstitucion);
						scsConjuntoguardias = this.scsConjuntoguardiasExtendsMapper.selectByPrimaryKey(scsConjuntoguardiasKey);
						if (scsConjuntoguardias != null) {
							scsConjuntoguardias.setDescripcion(lista.getNombre());
							scsConjuntoguardias.setLugar(lista.getLugar());
							if (!UtilidadesString.esCadenaVacia(lista.getIdTipo())) {
								scsConjuntoguardias.setTipo(Short.valueOf(lista.getIdTipo()));
							} else {
								scsConjuntoguardias.setTipo((Short)null);
							}

							scsConjuntoguardias.setObservaciones(lista.getObservaciones());
							scsConjuntoguardias.setUsumodificacion(((AdmUsuarios)usuarios.get(0)).getIdusuario());
							scsConjuntoguardias.setFechamodificacion(new Date());
							affectedRows += this.scsConjuntoguardiasExtendsMapper.updateByPrimaryKey(scsConjuntoguardias);
						}
					} else {
						scsConjuntoguardias = new ScsConjuntoguardias();
						//Se comenta el seteo del ID, a partir de ahora se usara la secuencia de SCS_CONJUNTOGUARDIAS
						/*String newIDLista = this.scsConjuntoguardiasExtendsMapper.getNextIdLista(idInstitucion);
						scsConjuntoguardias.setIdconjuntoguardia(Short.valueOf(newIDLista));*/
						scsConjuntoguardias.setIdinstitucion(idInstitucion);
						scsConjuntoguardias.setDescripcion(lista.getNombre());
						scsConjuntoguardias.setLugar(lista.getLugar());
						if (!UtilidadesString.esCadenaVacia(lista.getIdTipo())) {
							scsConjuntoguardias.setTipo(Short.valueOf(lista.getIdTipo()));
						} else {
							scsConjuntoguardias.setTipo(null);
						}

						scsConjuntoguardias.setObservaciones(lista.getObservaciones());
						scsConjuntoguardias.setUsumodificacion(((AdmUsuarios)usuarios.get(0)).getIdusuario());
						scsConjuntoguardias.setFechamodificacion(new Date());
						affectedRows += this.scsConjuntoguardiasExtendsMapper.insert(scsConjuntoguardias);
					}

					if (affectedRows > 0) {
						insertResponseDTO.setStatus(SigaConstants.OK);
						insertResponseDTO.setId(scsConjuntoguardias.getIdconjuntoguardia().toString());
					} else {
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setMessage("No se guardó ningún registro");
						error.setDescription("No se guardó ningún registro");
						insertResponseDTO.setError(error);
					}
				}
			}
		} catch (Exception var13) {
			this.LOGGER.error("saveListaGuardias() / ERROR: " + var13.getMessage(), var13);
			error.setCode(500);
			error.setMessage("Error al guardar la lista de guardias: " + var13);
			error.description("Error al guardar la lista de guardias: " + var13);
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public GuardiasDTO getGuardiasFromLista(HttpServletRequest request, String idLista) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GuardiasDTO guardiasDTO = new GuardiasDTO();
		new ArrayList();
		Error error = new Error();

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				this.LOGGER.info("getGuardiasFromLista() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				this.LOGGER.info("getGuardiasFromLista() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
					this.LOGGER.info("ListaGuardiaServiceImpl.getGuardiasFromLista() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					List<GenParametros> tamMax = this.genParametrosExtendsMapper.selectByExample(genParametrosExample);
					this.LOGGER.info("ListaGuardiaServiceImpl.getGuardiasFromLista() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					Integer tamMaximo;
					if (tamMax != null) {
						tamMaximo = Integer.valueOf(((GenParametros)tamMax.get(0)).getValor());
					} else {
						tamMaximo = null;
					}

					List<GuardiasItem> listaGuardiasItems = this.scsConfConjuntoGuardiasExtendsMapper.searchGuardiasFromLista(idLista, idInstitucion, tamMaximo);
					if (listaGuardiasItems != null && !listaGuardiasItems.isEmpty()) {
						listaGuardiasItems = (List)listaGuardiasItems.stream().map((it) -> {
							it.setTipoDia(("Labor. " + it.getSeleccionLaborables() + ", Fest. " + it.getSeleccionFestivos()).replace("null", ""));
							return it;
						}).collect(Collectors.toList());
						if (tamMaximo != null && listaGuardiasItems.size() > tamMaximo) {
							error.setCode(200);
							error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
							guardiasDTO.setError(error);
							listaGuardiasItems.remove(listaGuardiasItems.size() - 1);
						}
					}

					guardiasDTO.setGuardiaItems(listaGuardiasItems);
				}
			}
		} catch (Exception var14) {
			this.LOGGER.error("getGuardiasFromLista() / ERROR: " + var14.getMessage(), var14);
			error.setCode(500);
			error.setMessage("Error al buscar las guardias de la lista: " + var14);
			error.description("Error al buscar las guardias de la lista: " + var14);
			guardiasDTO.setError(error);
		}

		return guardiasDTO;
	}

	@Transactional
	@Override
	public UpdateResponseDTO saveGuardiasEnLista(HttpServletRequest request, List<GuardiasItem> guardias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				this.LOGGER.info("saveGuardiasEnLista() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				this.LOGGER.info("saveGuardiasEnLista() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty() && guardias != null && !guardias.isEmpty()) {
					ScsConfConjuntoGuardiasExample scsConfConjuntoGuardiasExample = new ScsConfConjuntoGuardiasExample();
					scsConfConjuntoGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdconjuntoguardiaEqualTo(Short.valueOf(((GuardiasItem)guardias.get(0)).getIdListaGuardia()));
					this.scsConfConjuntoGuardiasExtendsMapper.deleteByExample(scsConfConjuntoGuardiasExample);

					ScsConfConjuntoGuardias scsConfConjuntoGuardias;
					for(Iterator var12 = guardias.iterator(); var12.hasNext(); affectedRows += this.scsConfConjuntoGuardiasExtendsMapper.insert(scsConfConjuntoGuardias)) {
						GuardiasItem guardia = (GuardiasItem)var12.next();
						scsConfConjuntoGuardias = new ScsConfConjuntoGuardias();
						scsConfConjuntoGuardias.setIdinstitucion(idInstitucion);
						scsConfConjuntoGuardias.setIdconjuntoguardia(Short.valueOf(guardia.getIdListaGuardia()));
						scsConfConjuntoGuardias.setIdguardia(Integer.valueOf(guardia.getIdGuardia()));
						scsConfConjuntoGuardias.setIdturno(Integer.valueOf(guardia.getIdTurno()));
						scsConfConjuntoGuardias.setOrden(Integer.valueOf(guardia.getOrden()));
						scsConfConjuntoGuardias.setUsumodificacion(((AdmUsuarios)usuarios.get(0)).getIdusuario());
						scsConfConjuntoGuardias.setFechamodificacion(new Date());
					}

					if (affectedRows > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setMessage("No se guardó ningún registro");
						error.setDescription("No se guardó ningún registro");
						updateResponseDTO.setError(error);
					}
				}
			}
		} catch (Exception var15) {
			this.LOGGER.error("saveGuardiasEnLista() / ERROR: " + var15.getMessage(), var15);
			error.setCode(500);
			error.setMessage("Error al guardar las guardias en la lista: " + var15);
			error.description("Error al guardar las guardias en la lista: " + var15);
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Transactional
	@Override
	public DeleteResponseDTO deleteGuardiasFromLista(HttpServletRequest request, List<GuardiasItem> guardias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int affectedRows = 0;

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				this.LOGGER.info("deleteGuardiasFromLista() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				this.LOGGER.info("deleteGuardiasFromLista() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty() && guardias != null && !guardias.isEmpty()) {
					ScsConfConjuntoGuardiasKey scsConfConjuntoGuardiasKey;
					for(Iterator var11 = guardias.iterator(); var11.hasNext(); affectedRows += this.scsConfConjuntoGuardiasExtendsMapper.deleteByPrimaryKey(scsConfConjuntoGuardiasKey)) {
						GuardiasItem guardia = (GuardiasItem)var11.next();
						scsConfConjuntoGuardiasKey = new ScsConfConjuntoGuardiasKey();
						scsConfConjuntoGuardiasKey.setIdconjuntoguardia(Short.valueOf(guardia.getIdListaGuardia()));
						scsConfConjuntoGuardiasKey.setIdguardia(Integer.valueOf(guardia.getIdGuardia()));
						scsConfConjuntoGuardiasKey.setIdturno(Integer.valueOf(guardia.getIdTurno()));
						scsConfConjuntoGuardiasKey.setIdinstitucion(idInstitucion);
					}

					if (affectedRows > 0) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					} else {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setMessage("No se guardó ningún registro");
						error.setDescription("No se guardó ningún registro");
						deleteResponseDTO.setError(error);
					}
				}
			}
		} catch (Exception var14) {
			this.LOGGER.error("deleteGuardiasFromLista() / ERROR: " + var14.getMessage(), var14);
			error.setCode(500);
			error.setMessage("Error al eliminar las guardias de la lista: " + var14);
			error.description("Error al eliminar las guardias de la lista: " + var14);
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}
}
