package org.itcgae.siga.scs.services.impl.ejg.comision;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;

import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ScsActacomision;
import org.itcgae.siga.db.entities.ScsActacomisionKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgActa;
import org.itcgae.siga.db.entities.ScsEjgActaExample;
import org.itcgae.siga.db.entities.ScsEjgActaKey;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgResolucion;
import org.itcgae.siga.db.entities.ScsEjgResolucionKey;
import org.itcgae.siga.db.entities.ScsEjgResolucionWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsActacomisionMapper;
import org.itcgae.siga.db.mappers.ScsEjgActaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgComisionExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJGComision;
import org.itcgae.siga.scs.services.impl.ejg.GestionEJGServiceImpl;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.itcgae.siga.db.mappers.ScsEjgResolucionMapper;

@Service
public class BusquedaEJGComisionServiceImpl implements IBusquedaEJGComision {
	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private ScsEjgComisionExtendsMapper scsEjgComisionExtendsMapper;
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	@Autowired
	private ScsEjgActaMapper scsEjgActaMapper;
	@Autowired
	private ScsActacomisionMapper scsActacomisionMapper;
	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;
	@Autowired
	private ScsEjgMapper scsEjgMapper;
	@Autowired
	GestionEJGServiceImpl gestionEJGServiceImpl;
	@Autowired
	private GenParametrosMapper genParametrosMapper;
	
	@Autowired
	private ScsEjgResolucionMapper scsEjgResolucionMapper;

	@Override
	public ComboDTO comboDictamen(HttpServletRequest request) {
		LOGGER.info("comboDictamen() -> Entrada al servicio para obtener los dictamenes");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboDictamen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboDictamen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboDictamen() / sqScsTipodictamenejgExtendsMapper.comboDic() -> Entrada a sqScsTipodictamenejgExtendsMapper para obtener los dictamenes");

				comboItems = scsEjgComisionExtendsMapper.comboDictamen(Short.valueOf(usuarios.get(0).getIdlenguaje()),
						idInstitucion.toString());

				LOGGER.info(
						"comboDictamen() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Salida a sqScsTipodictamenejgExtendsMapper para obtener los dictamenes");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboDictamen() -> Salida del servicio para obtener los dictamenes");
		return comboDTO;
	}

	@Override
	public ComboDTO comboGuardias(HttpServletRequest request, String idTurno) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");

				List<ComboItem> comboItems = scsEjgComisionExtendsMapper.comboGuardias(idTurno,
						idInstitucion.toString());

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboGuardias() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;

	}

	public ComboDTO comboPonente(HttpServletRequest request) {

		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPonenteComision() / scsPonenteextendsMapper.getResoluciones() -> Entrada a scsImpugnacionEjgextendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper
						.comboPonenteComision(Short.valueOf(usuarios.get(0).getIdlenguaje()), idInstitucion.toString());

				LOGGER.info(
						"comboPonenteComision() / scsPonenteextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboFundamentoJurid(HttpServletRequest request, String resolucion) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboFundamentoJuridComision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFundamentoJuridComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"comboFundamentoJuridComision() / scsTipofundamentosSqlExtendsMapper.ComboFundamentoJurid() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener combo");

			comboItems = scsEjgComisionExtendsMapper.comboFundamentoJuridComision(
					Short.valueOf(usuarios.get(0).getIdlenguaje()), idInstitucion.toString(), resolucion);

			LOGGER.info(
					"comboFundamentoJuridComision() / scsTipofundamentosSqlExtendsMapper.ComboFundamentoJurid() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener combo");

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener combo");
		return comboDTO;
	}

	@Override
	public EjgDTO busquedaEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("busquedaEJGComision() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		EjgDTO ejgDTO = new EjgDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//String idUltimoEstado = "";
		if(ejgItem.getAnnio() == null) {
			int anioActa = new Date().getYear();
			ejgItem.setAnnio(String.valueOf(anioActa));
		}

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"busquedaEJGComision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"busquedaEJGComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"busquedaEJGComision() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"busquedaEJGComision() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"busquedaEJGComision() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				//idUltimoEstado = scsEjgComisionExtendsMapper.idUltimoEstado(ejgItem, idInstitucion.toString());

				LOGGER.info(
						"busquedaEJGComision() / scsEjgComisionExtendsMapper.busquedaEJGComision() -> Entrada a scsEjgComisionExtendsMapper para obtener el EJG");
				List<EjgItem> listaEjgs = scsEjgComisionExtendsMapper.busquedaEJGComision(ejgItem,
						idInstitucion.toString(), tamMaximo, "1");
				String stringListaEJG = "";
				if(listaEjgs.size() > 0 && listaEjgs != null) {
					for(int i = 0; i < listaEjgs.size(); i++) {
						if(i == listaEjgs.size()-1) {
							stringListaEJG += "(" + listaEjgs.get(i).getAnnio() + ", " + listaEjgs.get(i).getNumEjg() + ")";
						}else {
							stringListaEJG += "(" + listaEjgs.get(i).getAnnio() + ", " + listaEjgs.get(i).getNumEjg() + "), ";
						}
					}
					//se carga los registros obtenidos de la consulta (se espera uno solo)
					ejgDTO.setEjgItems(scsEjgComisionExtendsMapper.busquedaEJGComisionFinal(ejgItem, idInstitucion.toString(), tamMaximo,
							usuarios.get(0).getIdlenguaje().toString(), stringListaEJG));
				}
				
				LOGGER.info(
						"busquedaEJGComision() / scsEjgComisionExtendsMapper.busquedaEJGComision() -> Salida de scsEjgComisionExtendsMapper para obtener lista de EJGs");
				if (ejgDTO.getEjgItems() != null && tamMaximo != null && ejgDTO.getEjgItems().size() >= tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					ejgDTO.setError(error);
				}
			}
		} else {
			LOGGER.warn("busquedaEJGComision() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return ejgDTO;
	}

	@Override
	public ComboDTO getLabelColegiosCol(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			// exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTipoColegioEjgComision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoColegioEjgComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboTipoColegioEjgComision() / scsTipoEjgextendsMapper.comboTipoejg() -> Entrada a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

				comboItems = scsEjgComisionExtendsMapper.comboColegioEjgComision(idInstitucion.toString());

				LOGGER.info(
						"comboTipoColegioEjgComision() / scsTipoEjgextendsMapper.comboTipoejg() -> Salida a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoColegioEjg(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboTipoEJG() / scsTipoEjgextendsMapper.comboTipoejg() -> Entrada a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

				comboItems = scsEjgComisionExtendsMapper
						.comboTipoColegioEjg(Short.valueOf(usuarios.get(0).getIdlenguaje()), idInstitucion.toString());

				LOGGER.info(
						"comboTipoEJG() / scsTipoEjgextendsMapper.comboTipoejg() -> Salida a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;

	}

	@Override
	public ComboDTO comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboTipoEJG() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper.comboFundamentoCalificacion(
						Short.valueOf(usuarios.get(0).getIdlenguaje()), idInstitucion.toString(), list_dictamen);

				LOGGER.info(
						"comboTipoEJG() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboEstadoEJG(HttpServletRequest request, String resolucion) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"comboEstadoEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboEstadoEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboEstadoEJG() / scsTipoEjgextendsMapper.comboTipoejg() -> Entrada a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

				comboItems = scsEjgComisionExtendsMapper.comboEstadoEjg(Short.valueOf(usuarios.get(0).getIdlenguaje()),
						idInstitucion.toString(), resolucion);

				LOGGER.info(
						"comboEstadoEJG() / scsTipoEjgextendsMapper.comboTipoejg() -> Salida a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboJuzgados(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			// exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboJuzgados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboJuzgados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboJuzgados() / scsJuzgadoextendsMapper.getResoluciones() -> Entrada a scsImpugnacionEjgextendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper.comboJuzgados(idInstitucion.toString());

				LOGGER.info(
						"comboJuzgados() / scsJuzgadoextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboJuzgados() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboTurnosTipo(HttpServletRequest request, String idtipoturno) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			// exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTurnosTipo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTurnosTipo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboTurnosTipo() / scsTurnosextendsMapper.getResoluciones() -> Entrada a scsImpugnacionEjgextendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper.comboTurnosTipo(idInstitucion.toString(), idtipoturno);

				LOGGER.info(
						"comboTurnosTipo() / scsTurnosextendsMapper.getResoluciones() -> Salida a scsImpugnacionEjgextendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTurnosTipo() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboAnioActa(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			LOGGER.info(
					"comboAnioActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"comboAnioActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"comboAnioActa() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

			comboItems = scsEjgComisionExtendsMapper.comboAnioActa(idInstitucion);

			LOGGER.info(
					"comboAnioActa() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboAnioActa() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboResolucion(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			LOGGER.info(
					"comboAnioActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboAnioActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboAnioActa() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper.comboResolucion(Short.valueOf(usuarios.get(0).getIdlenguaje()),
						idInstitucion.toString());

				LOGGER.info(
						"comboAnioActa() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboAnioActa() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO obligatoriedadResolucion(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			LOGGER.info(
					"obligatoriedadResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"obligatoriedadResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"obligatoriedadResolucion() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

			comboItems = scsEjgComisionExtendsMapper.obligatoriedadResolucion(idInstitucion);

			LOGGER.info(
					"obligatoriedadResolucion() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("obligatoriedadResolucion() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboPresidente(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			LOGGER.info(
					"comboPresidente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPresidente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboPresidente() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper.comboPresidente(usuarios.get(0).getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"comboPresidente() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
			LOGGER.info("comboPresidente() -> Salida del servicio para obtener los tipos ejg");
		}
		return comboDTO;

	}

	@Override
	public ComboDTO comboSecretario(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			LOGGER.info(
					"comboSecretario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboSecretario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				LOGGER.info(
						"comboSecretario() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				comboItems = scsEjgComisionExtendsMapper.comboPresidente(usuarios.get(0).getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"comboSecretario() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboSecretario() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO editarActaAnio(List<EjgItem> ejgItems, HttpServletRequest request) throws SigaExceptions {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		String dni = UserTokenUtils.getDniFromJWTToken(token);
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);


		int resultado;

		LOGGER.info("Entra en el metodo editarActaAnio con la institucion " + idInstitucion);

		if (idInstitucion != null) {
//			try {

				for (EjgItem ejgItem : ejgItems) {

					ScsEstadoejg scsEstadoEjg;

					LOGGER.info("intentamos conseguir el ejg");

					ScsEjg scsEjg = obtenerEjg(ejgItem, idInstitucion);
					
					boolean isMismoActa = false;
					
					if(scsEjg.getIdacta() != null && String.valueOf(scsEjg.getIdacta()).equals(ejgItem.getNumActa())) {
						isMismoActa = true;
					}
					
					LOGGER.info("la comprobacion sobre si es el mismo ponente es:"+isMismoActa);
					
					if (!isMismoActa) {

						LOGGER.info("intentamos conseguir el estado del ejg");
	
						scsEstadoEjg = obtenerEstadoEjg(scsEjg, idInstitucion, (short) 9);
	
						List<ScsEjgActa> listaEjgAsociadosActa = obtenerEjgActa(scsEjg, idInstitucion);
	
						if (!listaEjgAsociadosActa.isEmpty()) {
	
							LOGGER.info("La lista tiene este tamaño " + listaEjgAsociadosActa.size());
	
							for (ScsEjgActa scsEjgActa : listaEjgAsociadosActa) {
	
								LOGGER.info("Si borrar es 0 entro en el for " + listaEjgAsociadosActa.size());
	
								LOGGER.info("ID DEL ACTA EN LA RELACION EJGACTA = " + scsEjgActa.getIdinstitucionacta());
	
								ScsActacomision acta = new ScsActacomision();
	
								if (scsEjgActa.getIdinstitucionacta().toString().equals(idInstitucion.toString())) {
	
									acta = obtenerActa(scsEjgActa, idInstitucion);
	
									if (acta.getFecharesolucion() == null) {
	
										LOGGER.info("La fecha resolucion del acta es nula");
	
										LOGGER.info(
												"Datos del scsEstadoEjg -> idInstitucion " + scsEstadoEjg.getIdinstitucion()
														+ " idTipoEjg -> " + scsEstadoEjg.getIdtipoejg() + " anio -> "
														+ scsEstadoEjg.getAnio() + " numero -> " + scsEstadoEjg.getNumero()
														+ " idEstadoporEjg ->" + scsEstadoEjg.getIdestadoejg());
	
										if(scsEstadoEjg.getObservaciones() != null) {
											scsEstadoEjg.setObservaciones(
													scsEstadoEjg.getObservaciones() + ". \n" 
															+ " Expediente excluido del acta "
															+ acta.getAnioacta() + "/" + acta.getNumeroacta());
										}else {
											scsEstadoEjg.setObservaciones(
													"Expediente excluido del acta "
															+ acta.getAnioacta() + "/" + acta.getNumeroacta());
	
										}
									
										LOGGER.info(scsEstadoEjg.getObservaciones());
	
										resultado = scsEjgActaMapper.deleteByPrimaryKey(scsEjgActa);
	
										if (resultado == 0) {
											throw (new SigaExceptions(
													"Error no se ha podido borrar la relacion entre el Ejg y acta"));
										}
									}
	
									/*if(scsEstadoEjg.getObservaciones() != null) {
										scsEstadoEjg.setObservaciones(
												scsEstadoEjg.getObservaciones() + " Expediente incluido masivamente en el acta "
														+ acta.getAnioacta() + "/" + acta.getNumeroacta());
									}else {
										scsEstadoEjg.setObservaciones(
												"Expediente incluido masivamente en el acta "
														+ acta.getAnioacta() + "/" + acta.getNumeroacta());
									}*/
								}
	
							}
	
						}
						LOGGER.info("Seteando las observaciones");
						
						ScsEjgActa scsEjgActa = new ScsEjgActa();
						scsEjgActa.setIdacta(Long.valueOf(ejgItem.getNumActa()));
						scsEjgActa.setIdinstitucionacta(idInstitucion);
						scsEjgActa.setAnioacta(Short.valueOf(ejgItem.getAnnioActa()));
						ScsActacomision acta = obtenerActa(scsEjgActa, idInstitucion);
	
						if(scsEstadoEjg.getObservaciones() != null) {
							scsEstadoEjg.setObservaciones(
									scsEstadoEjg.getObservaciones() + ". \n"
											+ "Expediente incluido masivamente en el acta "
											+ acta.getAnioacta() + "/" + acta.getNumeroacta());
						}else {
							scsEstadoEjg.setObservaciones(
									"Expediente incluido masivamente en el acta "
											+ acta.getAnioacta() + "/" + acta.getNumeroacta());
						}
	
						ScsEjgActa scsEjgActaNuevo = new ScsEjgActa();
	
						LOGGER.info("Creando la nueva relacion acta y ejg");
	
						scsEjgActaNuevo.setNumeroejg(Long.valueOf(scsEjg.getNumero()));
						scsEjgActaNuevo.setIdtipoejg(scsEjg.getIdtipoejg());
						scsEjgActaNuevo.setAnioejg(scsEjg.getAnio());
						scsEjgActaNuevo.setIdinstitucionejg(idInstitucion);
						scsEjgActaNuevo.setIdinstitucionacta(idInstitucion);
						scsEjgActaNuevo.setAnioacta(Short.valueOf(ejgItem.getAnnioActa()));
						scsEjgActaNuevo.setIdacta(Long.valueOf(ejgItem.getNumActa()));
						scsEjgActaNuevo.setFechamodificacion(new Date());
						scsEjgActaNuevo.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						scsEjg.setIdacta(Long.valueOf(ejgItem.getNumActa()));
						scsEjg.setIdinstitucionacta(idInstitucion);
						scsEjg.setAnioacta(Short.valueOf(Short.valueOf(ejgItem.getAnnioActa())));
						scsEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsEjg.setFechamodificacion(new Date());
	
						LOGGER.info("Guardando");
						
						
						resultado = scsEjgActaMapper.insert(scsEjgActaNuevo);
							
						if (resultado == 0) {
							throw (new SigaExceptions("Error no se ha podido crear la relacion entre el Ejg y acta"));
						}
	
						resultado = scsEjgMapper.updateByPrimaryKey(scsEjg);
	
						if (resultado == 0) {
							throw (new SigaExceptions("Error no se ha podido actualizar el ejg"));
						}
	
						if(scsEstadoEjg != null) {
							resultado = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoEjg);
		
							if (resultado == 0) {
								throw (new SigaExceptions("Error no se ha podido actualizar el estado del Ejg"));
							}
						}
						
						LOGGER.info("Se busca y se actualiza la resolucion asociada al ejg");
						
						ScsEjgResolucionKey resolKey = new ScsEjgResolucionKey();
						
						resolKey.setAnio(scsEjg.getAnio());
						resolKey.setIdinstitucion(idInstitucion);
						resolKey.setIdtipoejg(scsEjg.getIdtipoejg());
						resolKey.setNumero(Long.valueOf(scsEjg.getNumero()));
						
						ScsEjgResolucionWithBLOBs resolEjg = scsEjgResolucionMapper.selectByPrimaryKey(resolKey);
						
						//Se comprueba si el EJG tiene una resolucion asociada
						if(resolEjg != null) {

							resolEjg.setIdacta(Long.valueOf(ejgItem.getNumActa()));
							resolEjg.setIdinstitucionacta(idInstitucion);
							resolEjg.setAnioacta(Short.valueOf(Short.valueOf(ejgItem.getAnnioActa())));
							
							resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
							resolEjg.setFechamodificacion(new Date());
							
							resultado = scsEjgResolucionMapper.updateByPrimaryKey(resolEjg);
							
							if (resultado == 0) {
								throw (new SigaExceptions("Error no se ha podido actualizar la resolucion del Ejg"));
							}
						}
						else {
							
							resolEjg = new ScsEjgResolucionWithBLOBs();

							resolEjg.setIdacta(Long.valueOf(ejgItem.getNumActa()));
							resolEjg.setIdinstitucionacta(idInstitucion);
							resolEjg.setAnioacta(Short.valueOf(Short.valueOf(ejgItem.getAnnioActa())));
							

							resolEjg.setAnio(scsEjg.getAnio());
							resolEjg.setIdinstitucion(idInstitucion);
							resolEjg.setIdtipoejg(scsEjg.getIdtipoejg());
							resolEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
							
							resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
							resolEjg.setFechamodificacion(new Date());
							
							resultado = scsEjgResolucionMapper.insert(resolEjg);
							
							if (resultado == 0) {
								throw (new SigaExceptions("Error no se ha podido insertar la resolucion del Ejg"));
							}
						}
					}
				}
				//Se comenta para que el @Transactional funcione adecuadamente
//			} catch (SigaExceptions e) {
//				if (error.getCode() == null) {
//					error.setCode(500);
//				}
//				error.setDescription(e.getMsg());
//				updateResponseDTO.setStatus(SigaConstants.KO);
//			}
		} else {
			error.setCode(500);
			error.setDescription("El idInstitucion es nulo");
		}
		updateResponseDTO.setStatus(SigaConstants.OK);
		LOGGER.info("guardarEditarSelecionados() -> Salida del servicio para obtener los tipos ejg");
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarActaAnio(List<EjgItem> ejgItems, HttpServletRequest request) throws SigaExceptions {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		int resultado;

		LOGGER.info("Entra en el metodo editarActaAnio con la institucion " + idInstitucion);

		if (idInstitucion != null) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

//			try {
				for (EjgItem ejgItem : ejgItems) {

					ScsEstadoejg scsEstadoEjg;

					LOGGER.info("intentamos conseguir el ejg");

					ScsEjg scsEjg = obtenerEjg(ejgItem, idInstitucion);

					LOGGER.info("intentamos conseguir el estado del ejg");

					scsEstadoEjg = obtenerEstadoEjg(scsEjg, idInstitucion, (short) 9);

					List<ScsEjgActa> listaEjgAsociadosActa = obtenerEjgActa(scsEjg, idInstitucion);

					if (!listaEjgAsociadosActa.isEmpty()) {

						LOGGER.info("La lista tiene este tamaño " + listaEjgAsociadosActa.size());

						for (ScsEjgActa scsEjgActa : listaEjgAsociadosActa) {

							LOGGER.info("Si borrar es 1 entro en el for " + listaEjgAsociadosActa.size());

							ScsActacomision acta = obtenerActa(scsEjgActa, idInstitucion);

							if (acta.getFecharesolucion() == null) {

								LOGGER.info("El estado que voy a actualizar es:   " + scsEstadoEjg.getIdestadoejg());

								
								 if(scsEstadoEjg.getObservaciones() != null) {
									 scsEstadoEjg.setObservaciones( 
											 scsEstadoEjg.getObservaciones() + ".  \n"
											 		+ "Expediente excluido masivamente del acta " + acta.getAnioacta() + "/" +
									  acta.getNumeroacta());
								 }else {
									 scsEstadoEjg.setObservaciones( 
											 "Expediente excluido masivamente del acta " + acta.getAnioacta() + "/" +
									  acta.getNumeroacta());
								 }
								 
								//acta.setFecharesolucion(new Date());
								
								scsEjg.setIdacta(null);
								scsEjg.setAnioacta(null);
								scsEjg.setIdinstitucionacta(null);

								//scsActacomisionMapper.updateByPrimaryKey(acta);

								LOGGER.info("VOY A BORRAR A  " + listaEjgAsociadosActa.size());

								resultado = scsEjgActaMapper.deleteByPrimaryKey(scsEjgActa);
								
								if (resultado == 0) {
									throw (new SigaExceptions("Error no se ha podido eliminar una relacionEJG-Acta (borrarActaAnio)"));
								}
								
								if (resultado == 0) {
									throw (new SigaExceptions(
											"Error no se ha podido borrar la relacion entre el Ejg y acta"));
								}
								
								resultado = scsEjgMapper.updateByPrimaryKey(scsEjg);
								
								if (resultado == 0) {
									throw (new SigaExceptions("Error no se ha podido actualizar un Ejg (borrarActaAnio)"));
								}
								
								
								LOGGER.info("Se busca y se actualiza la resolucion asociada al ejg");
								
								ScsEjgResolucionKey resolKey = new ScsEjgResolucionKey();
								
								resolKey.setAnio(scsEjg.getAnio());
								resolKey.setIdinstitucion(idInstitucion);
								resolKey.setIdtipoejg(scsEjg.getIdtipoejg());
								resolKey.setNumero(Long.valueOf(scsEjg.getNumero()));
								
								ScsEjgResolucionWithBLOBs resolEjg = scsEjgResolucionMapper.selectByPrimaryKey(resolKey);
								
								//Se comprueba si el EJG tiene una resolucion asociada
								if(resolEjg != null) {

									resolEjg.setIdacta(null);
									resolEjg.setIdinstitucionacta(null);
									resolEjg.setAnioacta(null);
									
									resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
									resolEjg.setFechamodificacion(new Date());
									
									resultado = scsEjgResolucionMapper.updateByPrimaryKey(resolEjg);
									
									if (resultado == 0) {
										throw (new SigaExceptions("Error no se ha podido actualizar la resolucion del Ejg"));
									}
								}
								else {
									
									resolEjg = new ScsEjgResolucionWithBLOBs();

									resolEjg.setIdacta(null);
									resolEjg.setIdinstitucionacta(null);
									resolEjg.setAnioacta(null);
									

									resolEjg.setAnio(scsEjg.getAnio());
									resolEjg.setIdinstitucion(idInstitucion);
									resolEjg.setIdtipoejg(scsEjg.getIdtipoejg());
									resolEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
									
									resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
									resolEjg.setFechamodificacion(new Date());
									
									resultado = scsEjgResolucionMapper.insert(resolEjg);
									
									if (resultado == 0) {
										throw (new SigaExceptions("Error no se ha podido insertar la resolucion del Ejg"));
									}
								}
								

								updateResponseDTO.setStatus(SigaConstants.OK);

							}
						}
					}
				}
			}
//			} catch (SigaExceptions e) {
//				if (error.getCode() == null) {
//					error.setCode(500);
//				}
//				error.setDescription(e.getMsg());
//				updateResponseDTO.setStatus(SigaConstants.KO);
//			}

			LOGGER.info("guardarEditarSelecionados() -> Salida del servicio para obtener los tipos ejg");
			updateResponseDTO.setError(error);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO editarResolucionFundamento(List<EjgItem> ejgItems, HttpServletRequest request)
			throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (usuarios != null && usuarios.size() > 0) {

			AdmUsuarios usuario = usuarios.get(0);

			if (idInstitucion != null) {

				LOGGER.info("intentamos conseguir el ejg");
				for (EjgItem ejgItem : ejgItems) {

					LOGGER.info("INFORMACION DEL EJG QUE HE ENVIADO SUPUESTAMENTE " + ejgItem.getNumero());

					ScsEjg scsEjg = obtenerEjg(ejgItem, idInstitucion);

					LOGGER.info("intentamos conseguir el estado del ejg");
					
					//compruebo si es obligatorio el fundamento juridico
					String comprbarJuramentoJuridico = obligatorioFundamento(request);

					// uso tipo dictamen para traer los datos de la resolucion
					if (ejgItem.getIdTipoDictamen() != null && (ejgItem.getFundamentoJuridico() != null || comprbarJuramentoJuridico.equals("0")) ) {
						
						/*if(scsEjg.getFecharesolucioncajg() == null) {
							scsEjg.setFecharesolucioncajg(new Date());
						}*/
						int response = 0;

						//Este método sustituye a la ejecución de los triggers de EJG y se parametriza su ejecucion
						GenParametrosExample exampleParam = new GenParametrosExample();
						exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS)
							.andParametroEqualTo("ENABLETRIGGERSEJG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
						exampleParam.setOrderByClause("IDINSTITUCION DESC");

						List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);
						
						if(parametrosTrigger != null && !parametrosTrigger.isEmpty() 
								&& parametrosTrigger.get(0).getValor().equals("1")) {
							response = actualizarFecharesolucioncajg(idInstitucion, usuario, scsEjg);
	
							if (response == 0)
								throw (new Exception(
										"Error en el triggerEjgUpdatesResol al actualizar la fecha de resolucion del acta asociada"
												+ " asociada a un EJG"));
						}
						scsEjg.setIdtiporatificacionejg(ejgItem.getIdTipoDictamen());
						if(ejgItem.getFundamentoJuridico() != null) {
							scsEjg.setIdfundamentojuridico(Short.valueOf(ejgItem.getFundamentoJuridico()));
						}
						else {
							scsEjg.setIdfundamentojuridico(null);
						}
						


						LOGGER.info("Se busca y se actualiza la resolucion asociada al ejg");
						
						ScsEjgResolucionKey resolKey = new ScsEjgResolucionKey();
						
						resolKey.setAnio(scsEjg.getAnio());
						resolKey.setIdinstitucion(idInstitucion);
						resolKey.setIdtipoejg(scsEjg.getIdtipoejg());
						resolKey.setNumero(Long.valueOf(scsEjg.getNumero()));
						
						ScsEjgResolucionWithBLOBs resolEjg = scsEjgResolucionMapper.selectByPrimaryKey(resolKey);
						
						//Se comprueba si el EJG tiene una resolucion asociada
						if(resolEjg != null) {
							resolEjg.setIdtiporatificacionejg(ejgItem.getIdTipoDictamen());
							if(ejgItem.getFundamentoJuridico() != null) {
								resolEjg.setIdfundamentojuridico(Short.valueOf(ejgItem.getFundamentoJuridico()));
							}
							else {
								resolEjg.setIdfundamentojuridico(null);
							}
							resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
							resolEjg.setFechamodificacion(new Date());
							
							response = scsEjgResolucionMapper.updateByPrimaryKey(resolEjg);
							
							if (response == 0) {
								throw (new SigaExceptions("Error no se ha podido actualizar la resolucion del Ejg"));
							}
						}
						else {
							
							resolEjg = new ScsEjgResolucionWithBLOBs();
							
							if(ejgItem.getFundamentoJuridico() != null) {
								resolEjg.setIdfundamentojuridico(Short.valueOf(ejgItem.getFundamentoJuridico()));
							}
							else {
								resolEjg.setIdfundamentojuridico(null);
							}
							resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
							resolEjg.setFechamodificacion(new Date());
							

							resolEjg.setAnio(scsEjg.getAnio());
							resolEjg.setIdinstitucion(idInstitucion);
							resolEjg.setIdtipoejg(scsEjg.getIdtipoejg());
							resolEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
							
							resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
							resolEjg.setFechamodificacion(new Date());
							
							response = scsEjgResolucionMapper.insert(resolEjg);
							
							if (response == 0) {
								throw (new SigaExceptions("Error no se ha podido insertar la resolucion del Ejg"));
							}
						}
						
					}

					LOGGER.info("INFORMACION EJG PARA ENCONTRAR CLAVE PRINCIPAL -> ");
					LOGGER.info("INFORMACION EJG PARA ENCONTRAR CLAVE PRINCIPAL anio -> " + scsEjg.getAnio());
					LOGGER.info("INFORMACION EJG PARA ENCONTRAR CLAVE PRINCIPAL idtipoejg -> " + scsEjg.getIdtipoejg());
					LOGGER.info("INFORMACION EJG PARA ENCONTRAR CLAVE PRINCIPAL idinstitucion-> "
							+ scsEjg.getIdinstitucion());
					LOGGER.info("INFORMACION EJG PARA ENCONTRAR CLAVE PRINCIPAL numero -> " + scsEjg.getNumero());

					ScsEjgWithBLOBs ejgBlobs = (ScsEjgWithBLOBs) scsEjg;
					
					int response = scsEjgMapper.updateByPrimaryKeySelective(ejgBlobs);
					
					if (response == 0) {
						throw (new Exception(
								"Error en al actualizar el EJG"));
					}
					
					updateResponseDTO.setStatus(SigaConstants.OK);

					LOGGER.info(
							"guardarEditarSelecionados() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");
				}
			} else {
				error.setCode(500);
				error.setDescription("El idInstitucion es nulo");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
		}
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarResolucionFundamento(List<EjgItem> ejgItems, HttpServletRequest request)
			throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		
		int response = 0;
		
		if (usuarios != null && usuarios.size() > 0) {

			AdmUsuarios usuario = usuarios.get(0);

			if (idInstitucion != null) {

				LOGGER.info("intentamos conseguir el ejg");
				for (EjgItem ejgItem : ejgItems) {

					ScsEjg scsEjg = obtenerEjg(ejgItem, idInstitucion);

					LOGGER.info("intentamos conseguir el estado del ejg");

					scsEjg.setIdtiporatificacionejg(null);
					scsEjg.setIdfundamentojuridico(null);

					response = scsEjgMapper.updateByPrimaryKey(scsEjg);

					if (response == 0) {
						throw (new SigaExceptions("Error no se ha podido actualizar el Ejg"));
					}
					
					LOGGER.info("Se busca y se actualiza la resolucion asociada al ejg");
					
					ScsEjgResolucionKey resolKey = new ScsEjgResolucionKey();
					
					resolKey.setAnio(scsEjg.getAnio());
					resolKey.setIdinstitucion(idInstitucion);
					resolKey.setIdtipoejg(scsEjg.getIdtipoejg());
					resolKey.setNumero(Long.valueOf(scsEjg.getNumero()));
					
					ScsEjgResolucionWithBLOBs resolEjg = scsEjgResolucionMapper.selectByPrimaryKey(resolKey);
					
					//Se comprueba si el EJG tiene una resolucion asociada
					if(resolEjg != null) {
						resolEjg.setIdtiporatificacionejg(ejgItem.getIdTipoDictamen());
						if(ejgItem.getFundamentoJuridico() != null) {
							resolEjg.setIdfundamentojuridico(Short.valueOf(ejgItem.getFundamentoJuridico()));
						}
						else {
							resolEjg.setIdfundamentojuridico(null);
						}
						resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
						resolEjg.setFechamodificacion(new Date());
						
						response = scsEjgResolucionMapper.updateByPrimaryKey(resolEjg);
						
						if (response == 0) {
							throw (new SigaExceptions("Error no se ha podido actualizar la resolucion del Ejg"));
						}
					}
					else {
						
						resolEjg = new ScsEjgResolucionWithBLOBs();
						
						if(ejgItem.getFundamentoJuridico() != null) {
							resolEjg.setIdfundamentojuridico(Short.valueOf(ejgItem.getFundamentoJuridico()));
						}
						else {
							resolEjg.setIdfundamentojuridico(null);
						}
						resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
						resolEjg.setFechamodificacion(new Date());
						

						resolEjg.setAnio(scsEjg.getAnio());
						resolEjg.setIdinstitucion(idInstitucion);
						resolEjg.setIdtipoejg(scsEjg.getIdtipoejg());
						resolEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
						
						resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
						resolEjg.setFechamodificacion(new Date());
						
						response = scsEjgResolucionMapper.insert(resolEjg);
						
						if (response == 0) {
							throw (new SigaExceptions("Error no se ha podido insertar la resolucion del Ejg"));
						}
					}

					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			} else {
				error.setCode(500);
				error.setDescription("El idInstitucion es nulo");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
		}

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO editarPonente(List<EjgItem> ejgItems, HttpServletRequest request) throws SigaExceptions {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (usuarios != null && usuarios.size() > 0) {

			AdmUsuarios usuario = usuarios.get(0);

			if (idInstitucion != null) {
				LOGGER.info("ESTAMOS AQUIIIIIIIIIIIIIIIIII");
				for (EjgItem ejgItem : ejgItems) {

					ScsEjg scsEjg = obtenerEjg(ejgItem, idInstitucion);
					
					boolean isMismoPonente = false;
					if(scsEjg.getIdponente() != null && String.valueOf(scsEjg.getIdponente()).equals(ejgItem.getPonente())) {
						isMismoPonente = true;
					}
					
					LOGGER.info("la comprobacion sobre si es el mismo ponente es:"+isMismoPonente);
					
					if (!isMismoPonente) {

					

					LOGGER.info("intentamos conseguir el estado del ejg " + ejgItem.getBis());
/*
					if (ejgItem.getBis()) {
						LOGGER.info("Si hay que borrar entramos aqui");
						ponerFechaBajaEstadosEjg(scsEjg, idInstitucion);
					} else {*/
						if (ejgItem.getPonente() != null && ejgItem.getFechaPonenteDesd() != null && scsEjg != null) {
/*
							ResolucionEJGItem resolEjg = new ResolucionEJGItem();
							resolEjg.setAnio(scsEjg.getAnio());
							resolEjg.setIdTipoEJG(scsEjg.getIdtipoejg());
							resolEjg.setNumero(Long.valueOf(scsEjg.getNumejg()));
							resolEjg.setFechaPresentacionPonente(ejgItem.getFechaPonenteDesd());
							resolEjg.setIdPonente(Integer.valueOf(ejgItem.getPonente()));*/

							LOGGER.info(
									"Datos del ponente que vamos a actualizar primero la fecha y despues el id ponente "
											+ ejgItem.getFechaPonenteDesd() + " " + ejgItem.getPonente());

							scsEjg.setFechapresentacionponente(ejgItem.getFechaPonenteDesd());
							scsEjg.setIdponente(Integer.valueOf(ejgItem.getPonente()));
							scsEjg.setIdinstitucionponente(Short.valueOf(ejgItem.getidInstitucion()));
							
							int response = scsEjgMapper.updateByPrimaryKey(scsEjg);
							
							if (response == 0) {
								throw (new SigaExceptions("Error no se ha podido actualizar el ponente del Ejg"));
							}
							
							ponerFechaBajaEstadosEjg(scsEjg, idInstitucion);

							//obtengo el ponente
							String nombrePonente = obtenerPonente(token, ejgItem.getPonente().toString());
							
							ScsEstadoejg scsEstadoEjg = new ScsEstadoejg();
							
							scsEstadoEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
							scsEstadoEjg.setIdtipoejg(scsEjg.getIdtipoejg());
							scsEstadoEjg.setAnio(scsEjg.getAnio());
							scsEstadoEjg.setIdinstitucion(Short.valueOf(idInstitucion));
							scsEstadoEjg.setFechamodificacion(new Date());
							scsEstadoEjg.setFechainicio(new Date());
							scsEstadoEjg.setUsumodificacion(0);
							scsEstadoEjg.setAutomatico("0");
							scsEstadoEjg.setIdestadoejg(Short.valueOf("20"));
							scsEstadoEjg.setIdestadoporejg(obtenerUltimoEstadoPorEjg(scsEjg, idInstitucion) + 1);

							scsEstadoEjg.setObservaciones(scsEjgComisionExtendsMapper
									.getEtiquetasPonente(Short.valueOf(usuarios.get(0).getIdlenguaje()))+" "+ nombrePonente);
								
								
							response = scsEstadoejgMapper.insert(scsEstadoEjg);
							
							if (response == 0) {
								throw (new SigaExceptions("Error no se ha podido insertar un nuevo estado al Ejg (Ponente)"));
							}
							

							LOGGER.info("Se busca y se actualiza la resolucion asociada al ejg");
							
							ScsEjgResolucionKey resolKey = new ScsEjgResolucionKey();
							
							resolKey.setAnio(scsEjg.getAnio());
							resolKey.setIdinstitucion(idInstitucion);
							resolKey.setIdtipoejg(scsEjg.getIdtipoejg());
							resolKey.setNumero(Long.valueOf(scsEjg.getNumero()));
							
							ScsEjgResolucionWithBLOBs resolEjg = scsEjgResolucionMapper.selectByPrimaryKey(resolKey);
							
							//Se comprueba si el EJG tiene una resolucion asociada
							if(resolEjg != null) {

								resolEjg.setFechapresentacionponente(ejgItem.getFechaPonenteDesd());
								resolEjg.setIdponente(Integer.valueOf(ejgItem.getPonente()));
								resolEjg.setIdinstitucionponente(Short.valueOf(ejgItem.getidInstitucion()));
								
								resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
								resolEjg.setFechamodificacion(new Date());
								
								response = scsEjgResolucionMapper.updateByPrimaryKey(resolEjg);
								
								if (response == 0) {
									throw (new SigaExceptions("Error no se ha podido actualizar la resolucion del Ejg"));
								}
							}
							else {
								
								resolEjg = new ScsEjgResolucionWithBLOBs();
								
								resolEjg.setFechapresentacionponente(ejgItem.getFechaPonenteDesd());
								resolEjg.setIdponente(Integer.valueOf(ejgItem.getPonente()));
								resolEjg.setIdinstitucionponente(Short.valueOf(ejgItem.getidInstitucion()));
								

								resolEjg.setAnio(scsEjg.getAnio());
								resolEjg.setIdinstitucion(idInstitucion);
								resolEjg.setIdtipoejg(scsEjg.getIdtipoejg());
								resolEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
								
								resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
								resolEjg.setFechamodificacion(new Date());
								
								response = scsEjgResolucionMapper.insert(resolEjg);
								
								if (response == 0) {
									throw (new SigaExceptions("Error no se ha podido insertar la resolucion del Ejg"));
								}
							}
							

							LOGGER.info("Respuesta al actualizar" + response);
							
							updateResponseDTO.setStatus(SigaConstants.OK);

						} else {
							error.setCode(500);
							error.setDescription("Falta alguno de los datos para cambiar de ponente");
							updateResponseDTO.setStatus(SigaConstants.KO);

						}
					
					}
				}
			} else {
				error.setCode(500);
				error.setDescription("El idInstitucion es nulo");
				updateResponseDTO.setStatus(SigaConstants.KO);

			}
		}
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarPonente(List<EjgItem> ejgItems, HttpServletRequest request) throws SigaExceptions {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (usuarios != null && usuarios.size() > 0) {

			AdmUsuarios usuario = usuarios.get(0);

			if (idInstitucion != null) {
				LOGGER.info("ESTAMOS AQUIIIIIIIIIIIIIIIIII");
				for (EjgItem ejgItem : ejgItems) {

					ScsEjg scsEjg = obtenerEjg(ejgItem, idInstitucion);

					ScsEstadoejg scsEstadoEjg = new ScsEstadoejg();

					LOGGER.info("intentamos conseguir el estado del ejg " + ejgItem.getBis());

					LOGGER.info("Si hay que borrar entramos aqui");
					ponerFechaBajaEstadosEjg(scsEjg, idInstitucion);

					scsEjg.setIdponente(null);
					scsEjg.setIdinstitucionponente(null);
					scsEjg.setFechapresentacionponente(null);

					int response = scsEjgMapper.updateByPrimaryKey(scsEjg);
					
					if (response == 0) {
						throw (new SigaExceptions("Error no se ha podido actualizar el ponente del Ejg"));
					}
					
					LOGGER.info("Se busca y se actualiza la resolucion asociada al ejg");
					
					ScsEjgResolucionKey resolKey = new ScsEjgResolucionKey();
					
					resolKey.setAnio(scsEjg.getAnio());
					resolKey.setIdinstitucion(idInstitucion);
					resolKey.setIdtipoejg(scsEjg.getIdtipoejg());
					resolKey.setNumero(Long.valueOf(scsEjg.getNumero()));
					
					ScsEjgResolucionWithBLOBs resolEjg = scsEjgResolucionMapper.selectByPrimaryKey(resolKey);
					
					//Se comprueba si el EJG tiene una resolucion asociada
					if(resolEjg != null) {

						resolEjg.setFechapresentacionponente(null);
						resolEjg.setIdponente(null);
						resolEjg.setIdinstitucionponente(null);
						
						resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
						resolEjg.setFechamodificacion(new Date());
						
						response = scsEjgResolucionMapper.updateByPrimaryKey(resolEjg);
						
						if (response == 0) {
							throw (new SigaExceptions("Error no se ha podido actualizar la resolucion del Ejg"));
						}
					}
					else {
						
						resolEjg = new ScsEjgResolucionWithBLOBs();
						
						resolEjg.setFechapresentacionponente(null);
						resolEjg.setIdponente(null);
						resolEjg.setIdinstitucionponente(null);
						

						resolEjg.setAnio(scsEjg.getAnio());
						resolEjg.setIdinstitucion(idInstitucion);
						resolEjg.setIdtipoejg(scsEjg.getIdtipoejg());
						resolEjg.setNumero(Long.valueOf(scsEjg.getNumero()));
						
						resolEjg.setUsumodificacion(usuarios.get(0).getIdusuario());
						resolEjg.setFechamodificacion(new Date());
						
						response = scsEjgResolucionMapper.insert(resolEjg);
						
						if (response == 0) {
							throw (new SigaExceptions("Error no se ha podido insertar la resolucion del Ejg"));
						}
					}

					LOGGER.info("Respuesta al borrar el ponente" + response);
					
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			} else {
				error.setCode(500);
				error.setDescription("Falta alguno de los datos para cambiar de ponente");
				updateResponseDTO.setStatus(SigaConstants.KO);

			}
		}
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request) {
		LOGGER.info("busquedaEJGComision() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		ActasDTO actasDTO = new ActasDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idUltimoEstado = "";
		

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"busquedaEJGComision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"busquedaEJGComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (1 > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"busquedaEJGComision() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"busquedaEJGComision() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"busquedaEJGComision() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				LOGGER.info(
						"busquedaEJGComision() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				actasDTO.actasItem(scsEjgComisionExtendsMapper.busquedaActas(actasItem));
				LOGGER.info(
						"busquedaEJGComision() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			} else {
				LOGGER.warn(
						"busquedaEJGComision() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("busquedaEJGComision() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return actasDTO;
	}
	/**
	 * @param ejgItem
	 * @param idInstitucion
	 */
	private List<ScsEjgActa> obtenerEjgActa(ScsEjg scsEjg, Short idInstitucion) {

		ScsEjgActaExample scsEjgActaExample = new ScsEjgActaExample();

		LOGGER.info("datos ejg a buscar " + scsEjg.getAnio() + " " + scsEjg.getNumero() + " " + scsEjg.getIdtipoejg());

		scsEjgActaExample.createCriteria().andIdtipoejgEqualTo(Short.valueOf(scsEjg.getIdtipoejg()))
				.andNumeroejgEqualTo(Long.valueOf(scsEjg.getNumero())).andIdinstitucionejgEqualTo(idInstitucion)
				.andIdinstitucionactaEqualTo(idInstitucion).andAnioejgEqualTo(Short.valueOf(scsEjg.getAnio()));

		List<ScsEjgActa> listaEjgAsociadosActa = scsEjgActaMapper.selectByExample(scsEjgActaExample);

		return listaEjgAsociadosActa;
	}

	private int actualizarFecharesolucioncajg(Short idInstitucion, AdmUsuarios usuario, ScsEjg ejgItem)
			throws Exception {
		int response;

		ResolucionEJGItem resolEjg = new ResolucionEJGItem();

		resolEjg.setAnio(ejgItem.getAnio());

		resolEjg.setIdInstitucion(ejgItem.getIdinstitucion());

		resolEjg.setIdTipoEJG(ejgItem.getIdtipoejg());

		resolEjg.setNumero(Long.valueOf(ejgItem.getNumero()));

		resolEjg.setIdTiporatificacionEJG(ejgItem.getIdtiporatificacionejg());

		try {
			response = gestionEJGServiceImpl.triggersEjgUpdatesResol(resolEjg, usuario, idInstitucion);
		} catch (Exception e) {
			LOGGER.info("No se ha podido ejecutar el triggersEjgUpdatesResol");
			throw new SigaExceptions("No se ha podido ejecutar el triggersEjgUpdatesResol");
		}

		return response;
	}

	private ScsActacomision obtenerActa(ScsEjgActa scsEjgActa, Short idInstitucion) throws SigaExceptions {

		LOGGER.info("Anio acta no nula = " + scsEjgActa.getAnioacta());
		LOGGER.info("Id Acta no nula = " + scsEjgActa.getIdacta());

		ScsActacomision acta = new ScsActacomision();

		ScsActacomisionKey key = new ScsActacomisionKey();

		key.setAnioacta(Short.valueOf(scsEjgActa.getAnioacta()));

		key.setIdacta(Long.valueOf(scsEjgActa.getIdacta()));

		key.setIdinstitucion(Short.valueOf(idInstitucion));

		try {
			acta = scsActacomisionMapper.selectByPrimaryKey(key);

		} catch (Exception e) {
			LOGGER.info("No se encuentra el acta en la base de datos");
			throw new SigaExceptions("No se encuentra el acta en la base de datos");
		}

		return acta;
	}

	private ScsEstadoejg obtenerEstadoEjg(ScsEjg scsEjg, Short idInstitucion, Short estado) throws SigaExceptions {

		ScsEstadoejgExample estadoejgExample = new ScsEstadoejgExample();

		LOGGER.info("Datos para filtrar el estado ejg " + scsEjg.getAnio() + " ");

		estadoejgExample.createCriteria().andAnioEqualTo(Short.valueOf(scsEjg.getAnio()))
				.andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(scsEjg.getIdtipoejg()))
				.andNumeroEqualTo(Long.valueOf(scsEjg.getNumero())).andFechabajaIsNull().andIdestadoejgEqualTo(estado);

		estadoejgExample.setOrderByClause("FECHAMODIFICACION DESC");

		List<ScsEstadoejg> estadoejgObject;

		LOGGER.info("AQUI BUSCANDO EL ESTADO DEL EJG");

		try {
			estadoejgObject = scsEstadoejgMapper.selectByExample(estadoejgExample);
			LOGGER.info("AQUI BUSCANDO EL ESTADO DEL EJG PARA SABER EL TAMAÑO " + estadoejgObject.size());

		} catch (Exception e) {
			LOGGER.info("No se encuentra el estado para el ejg seleccionado");
			throw new SigaExceptions("No se encuentra el estado para el ejg seleccionado");
		}

		for (ScsEstadoejg scsEstadoejg : estadoejgObject) {
			LOGGER.info("Informacion de los estados");
			LOGGER.info(scsEstadoejg.getIdestadoporejg());
		}
		ScsEstadoejg scsEstadoejg;
		if (!estadoejgObject.isEmpty()) {
			scsEstadoejg = estadoejgObject.get(0);
		} else {
			scsEstadoejg = null;
//			throw new SigaExceptions("No existen estados para este ejg");
		}
		return scsEstadoejg;
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
				if (scsEstadoejg.getIdestadoejg() == 20) {
					scsEstadoejg.setFechabaja(new Date());
					int resultado = scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);
					LOGGER.info("Se ha actualizado la fecha de baja? " + resultado);
				}
			}
		}
		return estadoejgObject.size();
	}

	private ScsEjg obtenerEjg(EjgItem ejgItem, Short idInstitucion) throws SigaExceptions {

		LOGGER.info(
				"IDINSTITUCION DEL EJG Y EL QUE SALE DEL TOKEN " + ejgItem.getidInstitucion() + " " + idInstitucion);

		ScsEjgKey ejgkey = new ScsEjgKey();

		ejgkey.setAnio(Short.valueOf(ejgItem.getAnnio()));

		ejgkey.setIdinstitucion(idInstitucion);

		ejgkey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));

		ejgkey.setNumero(Long.valueOf(ejgItem.getNumero()));

		LOGGER.info("EJGKEY: anio idinstitucion tipoejg numero " + ejgkey.getAnio().toString() + " "
				+ ejgkey.getIdinstitucion().toString() + " " + ejgkey.getIdtipoejg().toString() + " "
				+ ejgkey.getNumero().toString());

		ScsEjg scsEjgItem;

		try {
			scsEjgItem = scsEjgMapper.selectByPrimaryKey(ejgkey);

		} catch (Exception e) {
			LOGGER.info("No se encuentra el ejg en la base de datos");
			throw new SigaExceptions("No se encuentra el ejg en la base de datos");

		}

		return scsEjgItem;

	}

	@Override
	public String obligatorioFundamento(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();
		
		if(idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			LOGGER.debug(
					"obligatorioFundamento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());
			
			LOGGER.debug(
					"obligatorioFundamento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			LOGGER.debug(
					"obligatorioFundamento() / genParametrosMapper.selectByPrimaryKey() -> Entrada a GenParametrosMapper para obtener el valor de la VALIDACION DE OBLIGATORIEDAD PARA RESOLUCION de la institucion logeada");
		
			GenParametrosKey key = new GenParametrosKey();

			key.setIdinstitucion(idInstitucion);
			key.setModulo("SCS");
			key.setParametro("VALIDAR_OBLIGATORIEDAD_RESOLUCION");

			parametro = genParametrosMapper.selectByPrimaryKey(key);
			
			if(parametro == null) {
				key.setIdinstitucion(new Short("0"));
				parametro = genParametrosMapper.selectByPrimaryKey(key);	
			}
			
			LOGGER.debug(
					"obligatorioFundamento() / genParametrosMapper.selectByPrimaryKey() -> Salida a GenParametrosMapper para obtener el valor de la VALIDACION DE OBLIGATORIEDAD PARA RESOLUCION de la institucion logeada");
		
		}
		
		return parametro.getValor();
	}
	public String obtenerPonente(String token, String idPonente) {
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ComboItem> comboItems = null;
		String nombrePonenete = null;
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		comboItems = scsEjgComisionExtendsMapper
				.comboPonenteComision(Short.valueOf(usuarios.get(0).getIdlenguaje()), idInstitucion.toString());
		
		for(int i=0; i<comboItems.size();i++) {
			ComboItem ponente = comboItems.get(i);
			if(idPonente.equals(ponente.getValue())) {
				nombrePonenete = ponente.getLabel();
			}
			
		}
		return nombrePonenete;
	}

	@Override
	public EjgDTO busquedaEJGActaComision(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("busquedaEJGActaComision() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		EjgDTO ejgDTO = new EjgDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//String idUltimoEstado = "";

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"busquedaEJGActaComision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"busquedaEJGActaComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"busquedaEJGActaComision() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"busquedaEJGActaComision() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"busquedaEJGActaComision() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}
				//idUltimoEstado = scsEjgComisionExtendsMapper.idUltimoEstado(ejgItem, idInstitucion.toString());

				LOGGER.info(
						"busquedaEJGActaComision() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgComisionExtendsMapper.busquedaEJGActaComision(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje()));
				LOGGER.info(
						"busquedaEJGActaComision() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
				if (ejgDTO.getEjgItems() != null && tamMaximo != null && ejgDTO.getEjgItems().size() > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					ejgDTO.setError(error);
				}
			}
		} else {
			LOGGER.warn("busquedaEJGActaComision() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return ejgDTO;
	}
	
	@Override
	public UpdateResponseDTO asociarEJGActa(EjgItem ejgItem, HttpServletRequest request) throws SigaExceptions {
		LOGGER.info("asociarEJGActa() -> Entrada al servicio para asociar un EJG con un acta");
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"asociarEJGActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"asociarEJGActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				
				LOGGER.info(
						"asociarEJGActa() / editarActaAnio() -> Entrada a editarActaAnio() para modificar las tablas relacionadas.");
                
                List<EjgItem> list = Arrays.asList(ejgItem);
				
				this.editarActaAnio(list, request);
				
				LOGGER.info(
						"asociarEJGActa() / editarActaAnio() -> Salida de editarActaAnio() para modificar las tablas relacionadas");
				
				responseDTO.setStatus("200");
			}
		} else {
			LOGGER.warn("asociarEJGActa() -> idInstitucion del token nula");
		}

		LOGGER.info("asociarEJGActa() -> Salida del servicio para obtener los de grupos de clientes");
		return responseDTO;
	}

}
