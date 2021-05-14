package org.itcgae.siga.scs.services.impl.ejg;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazadaExample;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.entities.ScsParentesco;
import org.itcgae.siga.db.entities.ScsParentescoKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgKey;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgPrestacionRechazadaMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsParentescoMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpTipoexpedienteExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActacomisionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDocumentacionEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsExpedienteEconomicoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrigencajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrestacionExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IEEJGServices;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionEJGServiceImpl implements IGestionEJG {
	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsPrestacionExtendsMapper scsPrestacionesExtendsMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private ScsExpedienteEconomicoExtendsMapper scsExpedienteEconomicoExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Autowired
	private ScsDocumentacionEjgExtendsMapper scsDocumentacionejgExtendsMapper;

	@Autowired
	private ScsOrigencajgExtendsMapper scsOrigencajgExtendsMapper;

	@Autowired
	private ScsActacomisionExtendsMapper scsActacomisionExtendsMapper;

	@Autowired
	private ExpTipoexpedienteExtendsMapper expExpedienteExtendsMapper;

	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;
	
	@Autowired
	private ScsEejgPeticionesMapper scsEejgPeticionesMapper;

	@Autowired
	private ExpExpedienteMapper expExpedienteMapper;
	
	@Autowired
	private ScsEjgPrestacionRechazadaMapper scsEjgPrestacionRechazadaMapper;
	
	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;
	
	@Autowired
	private ScsParentescoMapper scsParentescoMapper;
	
	@Autowired
	private IEEJGServices eejgService;

	@Override
	public EjgDTO datosEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("datosEJG() -> Entrada al servicio para obtener el colegiado");
		EjgDTO ejgDTO = new EjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"datosEJG() / scsEjgExtendsMapper.datosEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgExtendsMapper.datosEJG(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"datosEJG() / scsEjgExtendsMapper.datosEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			} else {
				LOGGER.warn(
						"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return ejgDTO;
	}

	@Override
	public ComboDTO comboPrestaciones(HttpServletRequest request) {
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
					"comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPrestaciones() / scsTiporesolucionExtendsMapper.comboPrestaciones() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				comboItems = scsPrestacionesExtendsMapper.comboPrestaciones(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());

				LOGGER.info(
						"comboPrestaciones() / scsTiporesolucionExtendsMapper.comboPrestaciones() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}
	
	@Override
	public UpdateResponseDTO guardarServiciosTramitacion(EjgItem datos, HttpServletRequest request) {

		UpdateResponseDTO responsedto = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {
					
					//Seleccionamos el EJG que vamos a actualizar
					ScsEjgKey key = new ScsEjgKey();
					
					key.setIdinstitucion(idInstitucion);
					key.setAnio(Short.parseShort(datos.getAnnio()));
					key.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					key.setNumero(Long.parseLong(datos.getNumero()));
					
					ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(key);
					
					//Una vez tenemos el EJG, introducimos la informacion seleccionada en la tarjeta
					
					//Por ahora suponemos que los valores de id no pueden ser nulos.
					ejg.setGuardiaturnoIdguardia(Integer.parseInt(datos.getIdGuardia()));
					ejg.setGuardiaturnoIdturno(Integer.parseInt(datos.getIdTurno()));
					ejg.setIdpersona(Long.parseLong(datos.getIdPersona()));
					
					LOGGER.info(
							"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Entrada a scsEjgMapper para actualizar el ejg");

					response = scsEjgMapper.updateByPrimaryKeySelective(ejg);

					LOGGER.info(
							"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Salida a scsEjgMapper para actualizar el ejg");

				} catch (Exception e) {
					
				}
			}

		}
		LOGGER.info(
				"guardarServiciosTramitacion() -> Salida del servicio para actualizar turno, guardia y letrado asociados a un EJG.");
		return responsedto;
	}

	@Override
	public List<ScsEjgPrestacionRechazada> searchPrestacionesRechazadas(EjgItem ejgItem, HttpServletRequest request) {
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ScsEjgPrestacionRechazada> idsPrestRech = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				idsPrestRech = scsPrestacionesExtendsMapper.prestacionesRechazadas(ejgItem, idInstitucion);

				LOGGER.info(
						"searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

			}

		}
		LOGGER.info("searchPrestacionesRechazadas() -> Salida del servicio para obtener las prestaciones rechazadas del ejg");
		return idsPrestRech;
	}

	@Override
	public UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("unidadFamiliarEJG() -> Entrada al servicio para obtener unidad Familiar");
		UnidadFamiliarEJGDTO unidadFamiliarEJGDTO = new UnidadFamiliarEJGDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Entrada a scsEjgExtendsMapper para obtener Unidad Familiar");
				unidadFamiliarEJGDTO.setUnidadFamiliarEJGItems(scsPersonajgExtendsMapper.unidadFamiliarEJG(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Salida de scsEjgExtendsMapper para obtener Unidad Familiar");
			} else {
				LOGGER.warn(
						"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("unidadFamiliarEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return unidadFamiliarEJGDTO;
	}

	@Override
	public ExpedienteEconomicoDTO getExpedientesEconomicos(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getExpedientesEconomicos() -> Entrada al servicio para obtener expedientes económicos");
		ExpedienteEconomicoDTO expedienteEconomicoDTO = new ExpedienteEconomicoDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				Error error = new Error();

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"getExpedientesEconomicos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener Expedienets Económicos");
				expedienteEconomicoDTO.setExpEconItems(scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(
						ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener Expedienets Económicos");
				if ((scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(ejgItem, idInstitucion.toString(),
						tamMaximo, usuarios.get(0).getIdlenguaje().toString())) != null
						&& tamMaximo != null
						&& (scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(ejgItem,
								idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()))
										.size() > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					expedienteEconomicoDTO.setError(error);
					// justiciablesItems.remove(justiciablesItems.size()-1);
				}

			} else {
				LOGGER.warn(
						"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getExpedientesEconomicos() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return expedienteEconomicoDTO;
	}

	@Override
	public EstadoEjgDTO getEstados(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getEstados() -> Entrada al servicio para obtener el colegiado");
		EstadoEjgDTO estadoEjgDTO = new EstadoEjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getEstados() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEstadoejgExtendsMapper para obtener los estados del EJG");
				estadoEjgDTO.setEstadoEjgItems(scsEstadoejgExtendsMapper.getEstados(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getEstados() / scsEjgExtendsMapper.getEstados() -> Salida de scsEstadoejgExtendsMapper para obtener los estados del EJG");
			} else {
				LOGGER.warn(
						"getEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEstados() -> idInstitucion del token nula");
		}
		return estadoEjgDTO;
	}

	@Override
	public EjgDocumentacionDTO getDocumentos(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getExpedientesEconomicos() -> Entrada al servicio para obtener expedientes económicos");
		EjgDocumentacionDTO ejgDocumentacionDTO = new EjgDocumentacionDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"getDocumentos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"getDocumentos() / setUnidadFamiliarEJGItems.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener la documentación de EJG");
				ejgDocumentacionDTO.setEjgDocItems(scsDocumentacionejgExtendsMapper.getDocumentacion(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getDocumentos() / setUnidadFamiliarEJGItems.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener la documentación de EJG");
			} else {
				LOGGER.warn(
						"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDocumentos() -> idInstitucion del token nula");
		}

		LOGGER.info("getDocumentos() -> Salida del servicio para obtener la documentación de EJG");
		return ejgDocumentacionDTO;
	}

	@Override
	public EjgItem getDictamen(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getDictamen() -> Entrada al servicio para obtener el colegiado");
		EjgItem dictamen = new EjgItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getDictamen() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEjgExtendsMapper para obtener información del Informe de Calificación");
				dictamen = scsEjgExtendsMapper.getDictamen(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());
				LOGGER.info(
						"getDictamen() / scsEjgExtendsMapper.getEstados() -> Salida de scsEjgExtendsMapper para obtener información del Informe de Calificación");
			} else {
				LOGGER.warn(
						"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDictamen() -> idInstitucion del token nula");
		}
		return dictamen;
	}

	@Override
	public ComboDTO comboOrigen(HttpServletRequest request) {
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
					"comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Entrada a scsOrigencajgExtendsMapper para rellenar el combo");
				comboItems = scsOrigencajgExtendsMapper.comboOrigen(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());
				LOGGER.info(
						"comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Salida a scsOrigencajgExtendsMapper para rellenar el combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboActaAnnio(HttpServletRequest request) {
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
					"comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Entrada a scsActacomisionExtendsMapper para obtener los combo");
				comboItems = scsActacomisionExtendsMapper.getActaAnnio(idInstitucion.toString());
				LOGGER.info(
						"comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Salida a scsActacomisionExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboActaAnnio() -> Salida del servicio");
		return comboDTO;
	}

	@Override
	public ResolucionEJGItem getResolucion(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getResolucion() -> Entrada al servicio para obtener el colegiado");
		ResolucionEJGItem resolucion = new ResolucionEJGItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getResolucion() / scsEjgExtendsMapper.getResolucion() -> Entrada a scsEjgExtendsMapper para obtener información de la Resolución");
				resolucion = scsEjgExtendsMapper.getResolucion(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());
				LOGGER.info(
						"getResolucion() / scsEjgExtendsMapper.getResolucion() -> Salida de scsEjgExtendsMapper para obtener información de la Resolución");
			} else {
				LOGGER.warn(
						"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getResolucion() -> idInstitucion del token nula");
		}
		return resolucion;
	}

	@Override
	public ComboDTO comboTipoExpediente(HttpServletRequest request) {
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
					"comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");
				comboItems = expExpedienteExtendsMapper.comboTipoExpediente(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());
				LOGGER.info(
						"comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO cambioEstadoMasivo(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para cambiar los estados y la fecha de estado para los ejgs");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");

						// obtenemos el maximo de idestadoporejg
						ScsEstadoejgExample example = new ScsEstadoejgExample();
						example.setOrderByClause("IDESTADOPOREJG DESC");
						example.createCriteria().andAnioEqualTo(Short.parseShort(datos.get(i).getAnnio()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoejgEqualTo(Short.parseShort(datos.get(i).getTipoEJG()))
								.andNumeroEqualTo(Long.parseLong(datos.get(i).getNumero()));

						List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

						// damos el varlo al idestadoporejg + 1
						if (listEjg.size() > 0) {
							record.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
						} else {
							record.setIdestadoporejg(Long.parseLong("0"));
						}

						// scsEstadoejgMapper.selectByExample(example)

						response = scsEstadoejgMapper.insert(record);
					}

					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.cambiarEstadoEJGs() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.cambiarEstadoEJGs() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio.");

		return responsedto;
	}

	

	@Override
	@Transactional
	public UpdateResponseDTO descargarExpedientesJG(List<EjgItem> datos, HttpServletRequest request) {
		Error error = new Error(); 
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();
		int response = 0;
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		String salida;
		
		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Generando fichero y creando la descarga...");

				try {
					//recorremos la lista para generar el documento de cada uno de los ejgs 
					for(EjgItem ejg : datos) {
											
						//obtenemos la peticion y el idXML
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos de la petición...");
						
						ScsEejgPeticionesExample scsEejgPeticionesExample = new ScsEejgPeticionesExample();
						
						scsEejgPeticionesExample.createCriteria().andAnioEqualTo(Short.parseShort(ejg.getidInstitucion()))
						.andIdpersonaEqualTo(Long.parseLong(ejg.getIdPersona())).andAnioEqualTo(Short.parseShort(ejg.getAnnio()))
						.andIdtipoejgEqualTo(Short.parseShort(ejg.getIdTipoExpediente())).andNumeroEqualTo(Long.parseLong(ejg.getNumEjg()));
						
						List<ScsEejgPeticiones> peticiones = scsEejgPeticionesMapper.selectByExample(scsEejgPeticionesExample);
						
						if(peticiones==null || peticiones.size()>0) {
							throw new Exception("No existe peticiones para el ejg seleccionado");
						}
						
						//creamos el objeto de la unidad familiar
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos de la unidad familiar...");
						ScsUnidadfamiliarejg uFamiliar = null;
						
						ScsUnidadfamiliarejgKey key = new ScsUnidadfamiliarejgKey();
						
						key.setIdinstitucion(Short.parseShort(ejg.getidInstitucion()));
						key.setIdpersona(Long.parseLong(ejg.getIdPersona()));
						key.setIdtipoejg(Short.parseShort(ejg.getIdTipoExpediente()));
						key.setAnio(Short.parseShort(ejg.getAnnio()));
						key.setNumero(Long.parseLong(ejg.getNumeroexpediente())); //NUMEJG
						
						uFamiliar = scsUnidadfamiliarejgMapper.selectByPrimaryKey(key);
						
						//comprobamos el parentesco, si no tiene, se le pone solicitante 
						ScsParentesco parentesco = null;
						
						if(uFamiliar!=null && uFamiliar.getIdparentesco()!=null){
							ScsParentescoKey keyParentesco = new ScsParentescoKey();
							
							keyParentesco.setIdinstitucion(Short.parseShort(ejg.getidInstitucion()));
							keyParentesco.setIdparentesco(uFamiliar.getIdparentesco());
							
							parentesco = scsParentescoMapper.selectByPrimaryKey(keyParentesco);
						}else {
							String literalSolicitante = UtilidadesString.getMensajeIdioma(usuarios.get(0).getIdlenguaje(), "gratuita.busquedaEJG.literal.solicitante"); 
									
							parentesco.setDescripcion(literalSolicitante);
						}
						
						//obtenemos los datos del fichero
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos para el informe...");
						Map<Integer, Map<String, String>> mapInformeEejg = eejgService.getDatosInformeEejg(ejg, peticiones.get(0));
						
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo el informe...");
						File fichero = eejgService.getInformeEejg(mapInformeEejg, ejg.getidInstitucion());
						
						if(fichero!= null){
							response=1;
							
							request.setAttribute("nombreFichero", fichero.getName());
							request.setAttribute("rutaFichero", fichero.getAbsolutePath());			
							request.setAttribute("borrarFichero", "true");			
							request.setAttribute("generacionOK","OK");
							
							salida= "descarga";
						}else{
							throw new Exception("ERROR al generar fichero");
						}
						
						
					}
					
					LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Acción realizada correctamente");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Se ha producido un error: ", e);
					response = 0;
				}
			}
		}
		
		if (response == 0) {
			error.setCode(400);
			error.setDescription("Se ha producido un error");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Acción realizada correctamente");
		}

		responseDTO.setError(error);

		LOGGER.info("GestionEJGServiceImpl.descargarExpedientesJG() -> Salida del servicio.");

		return responseDTO;
	}

	@Override
	@Transactional
	public EjgDTO insertaDatosGenerales(EjgItem datos, HttpServletRequest request) {
		EjgDTO ejgdto = new EjgDTO();
		int response = 0;
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para insertar los datos generales del ejg");
				
				try{
					record = setDatosGeneralesEJG(datos);
					
					//AGREGAMOS DATOS QUE FALTAN EN EL RECORD
					
					//longitud maxima para num ejg
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("LONGITUD_CODEJG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
					

					List<GenParametros> listParam= genParametrosExtendsMapper.selectByExample(genParametrosExample);
					
					String longitudEJG = listParam.get(0).getValor();
					
					//numejg
					
					String numEJG = scsEjgExtendsMapper.getNumeroEJG(record.getIdtipoejg(), record.getAnio(), record.getIdinstitucion());
					
					int numCeros = Integer.parseInt(longitudEJG) - numEJG.length();
					
					String ceros ="";
					for(int i=0; i<numCeros; i++) {
						ceros += "0";
					}
					
					ceros+=numEJG;
					
					record.setNumejg(ceros);
					
					// Repetimos el proceso con numero
					
					String numero = scsEjgExtendsMapper.getNumero(record.getIdtipoejg(), record.getAnio(), record.getIdinstitucion());
					
					numCeros = Integer.parseInt(longitudEJG) - numero.length();
					
					ceros ="";
					for(int i=0; i<numCeros; i++) {
						ceros += "0";
					}
					
					ceros+=numero;
					
					record.setNumero(Long.parseLong(ceros));
					
					//Determinamos el origen de apertura ya que, aunque no sea una clave primaria,
					//no se permita que tenga valor null. Por ahora se introducira el valor de idinstitucion. Preguntar mas tarde.
					record.setOrigenapertura("M");
					
					//Sucede lo mismo
					record.setTipoletrado("M");
					record.setFechacreacion(new Date());
					record.setFechamodificacion(new Date());
					record.setUsucreacion(usuarios.get(0).getIdusuario());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					
					response = scsEjgMapper.insertSelective(record);
					
				} catch (Exception e) {
					LOGGER.error("GestionEJGServiceImpl.insertaDatosGenerales(). ERROR: al hacer el insert de datos generales. ", e);
				} finally {
					
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						List<EjgItem> list = new ArrayList<>();
						
						EjgItem item = new EjgItem();
						
						item.seteidInstitucion(idInstitucion.toString());
						item.setAnnio(datos.getAnnio());
						item.setNumEjg(record.getNumejg());
						item.setNumero(record.getNumero().toString());
						item.setTipoEJG(datos.getTipoEJG());
						
						list.add(item);
						
						ejgdto.setEjgItems(list);
//						responsedto.setStatus(SigaConstants.OK);
						error.setCode(200);
						LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> OK. Datos generales insertados");
					} else {
//						responsedto.setStatus(SigaConstants.KO);
						error.setCode(400);
						LOGGER.error("GestionEJGServiceImpl.insertaDatosGenerales() -> KO. No se ha insertado los datos generales");
					}
					ejgdto.setError(error);
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.insertaDatosGenerales() -> Salida del servicio.");

		return ejgdto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO actualizaDatosGenerales(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.info(
					"GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para actualizar los datos generales del ejg");

				try {
//					for (int i = 0; datos.get) > i; i++) {
						response = 0;
						
//						Clave primaria scsEstadoejg
//						IDINSTITUCION
//						IDTIPOEJG
//						ANIO
//						NUMERO
//						IDESTADOPOREJG

						// seleccionamos el objeto para el update
						
						ScsEjgKey ejgKey = new ScsEjgKey();
						
						ejgKey.setIdinstitucion(idInstitucion);
						ejgKey.setAnio(Short.parseShort(datos.getAnnio()));
						ejgKey.setNumero(Long.parseLong(datos.getNumero()));
						ejgKey.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
//						key.setIdestadoporejg(Long.parseLong(datos.getEstadoEJG()));
						
						ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
						
						// Modificamos el objeto obtenido
						
						ejg.setFechaapertura(datos.getFechaApertura());
						ejg.setFechapresentacion(datos.getFechapresentacion());
						ejg.setFechalimitepresentacion(datos.getFechalimitepresentacion());
						if(datos.getTipoEJGColegio()!=null)ejg.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));
						ejg.setUsumodificacion(usuarios.get(0).getIdusuario());
						ejg.setFechamodificacion(new Date());
						
						//Actualizamos la entrada en la BBDD
						response = scsEjgMapper.updateByPrimaryKeySelective(ejg);
						
						//Actualizar el expediente del que se extrae el tipo de expediente
						//Clave primaria
//						IDINSTITUCION
//						IDINSTITUCION_TIPOEXPEDIENTE
//						IDTIPOEXPEDIENTE
//						NUMEROEXPEDIENTE
//						ANIOEXPEDIENTE
						
						//Pendiente de completar. Falta determinar algunos campos y obterner los valores para seleccionar nuestro expediente
						ExpExpedienteKey expKey = new ExpExpedienteKey();
						
						expKey.setIdinstitucion(idInstitucion);
						expKey.setAnioexpediente(Short.parseShort(datos.getAnnio()));
						expKey.setNumeroexpediente(Integer.parseInt(datos.getNumero()));
						expKey.setIdtipoexpediente(Short.parseShort(datos.getTipoEJG()));
						if(datos.getIdInstTipoExp()!=null)expKey.setIdinstitucionTipoexpediente(Short.parseShort(datos.getIdInstTipoExp()));
						
						ExpExpediente newExp = expExpedienteMapper.selectByPrimaryKey(expKey);
						
						if(newExp!= null) {
							if(datos.getTipoEJG()!=null)newExp.setIdtipoexpediente(Short.parseShort(datos.getTipoEJG()));
							newExp.setUsumodificacion(usuarios.get(0).getIdusuario());
							newExp.setFechamodificacion(new Date());
							
							response = expExpedienteMapper.updateByPrimaryKeySelective(newExp);

						}
						
						//Manejo de prestaciones
						//Se comprueban las prestaciones rechazadas en la ficha.
						//El proceso que se va a seguir sera comprobar si se recibe una lista prestaciones rechazadas 
						//en tal caso, eliminar las existentes y despues  recorrer las recibidas para i

//						Clave primaria
//						IDINSTITUCION
//						ANIO
//						NUMERO
//						IDTIPOEJG
//						IDPRESTACION
						//Comprobar en el caso de que no se haga ningún cambio y con algún cambio.
						if(datos.getPrestacionesRechazadas()!=null) {
							ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();
							
							preRe.setIdinstitucion(idInstitucion);
							preRe.setAnio(Short.parseShort(datos.getAnnio()));
							preRe.setNumero(Long.parseLong(datos.getNumero()));
							preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
							preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
							preRe.setFechamodificacion(new Date());
							preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
							
							ScsEjgPrestacionRechazadaExample examplePresRe = new ScsEjgPrestacionRechazadaExample();
							
							examplePresRe.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(preRe.getAnio())
							.andNumeroEqualTo(preRe.getNumero()).andIdtipoejgEqualTo(preRe.getIdtipoejg())
							.andIdtipoejgEqualTo(preRe.getIdtipoejg());
							
							//Eliminamos las entradas existentes relacionadas si las hubiera.
							response = scsEjgPrestacionRechazadaMapper.deleteByExample(examplePresRe);
							
							
							
							for(String idprestacion: datos.getPrestacionesRechazadas()) {
								preRe.setIdprestacion(Short.parseShort(idprestacion));
								
								response = scsEjgPrestacionRechazadaMapper.insert(preRe);
							}
							
							
						}
						
						LOGGER.info(
								"GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio para actualizar los datos generales del ejg");
					
						
					}catch (Exception e) {
						LOGGER.info(
								"GestionEJGServiceImpl.actualizaDatosGenerales() -> Se ha producido un error al actualizar los datos generales del ejg",
								e);
					} 
				} 
					
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.info(
								"GestionEJGServiceImpl.actualizaDatosGenerales() -> OK. Datos Generales actualizados para el ejg");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.info(
								"GestionEJGServiceImpl.actualizaDatosGenerales() -> KO. No se ha actualizado ningún dato general del ejg");
					}
				
			}
		

		LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio.");

		return responsedto;
	}
	/**
	 * metodo para settear los datos del objeto para el insert o update
	 * @param item
	 * @return
	 */
	private ScsEjgWithBLOBs setDatosGeneralesEJG(EjgItem item) {
		ScsEjgWithBLOBs result = new ScsEjgWithBLOBs();
		
		
		if(item.getidInstitucion()!=null) {
			result.setIdinstitucion(Short.parseShort(item.getidInstitucion()));
		}
		
		if(item.getFechaApertura()!=null) {
			result.setFechaapertura(item.getFechaApertura());
		}
		
		if(item.getAnnio()!=null) {
			result.setAnio(Short.parseShort(item.getAnnio()));
		}
		
		if(item.getNumero()!=null) {
			result.setNumero(Long.parseLong(item.getNumEjg()));
		}
		
		if(item.getFechapresentacion()!=null) {
			result.setFechapresentacion(item.getFechapresentacion());
		}
		
		if(item.getFechalimitepresentacion()!=null) {
			result.setFechalimitepresentacion(item.getFechalimitepresentacion());
		}
		
		//ver si id o nombre de estado ¿situacion?
		if(item.getEstadoEJG()!=null) {
			result.setIdsituacion(Short.parseShort(item.getEstadoEJG()));
		}
		
		//tenemos q ver si devuleve id o nombre de tipo ejg
		if(item.getTipoEJG()!=null) {
			
			result.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
		}
		
		if(item.getTipoEJGColegio()!=null) {
			result.setIdtipoejgcolegio(Short.parseShort(item.getTipoEJGColegio()));
		}
		
		//PRETENSIONES FALTA POR HACER
//		if(item.getPr!=null) {
//			result.set.setPr();
//		}
		
//		if(item.getAnioexpediente()!=null) {
//			result.setAn(Short.parseShort(item.getAnioexpediente()));
//		}
//		
//		if(item.getNumeroexpediente()!=null) {
//			result.setNum(Short.parseShort(item.getNumeroexpediente()));
//		}
//		
//
//		if(item.getTurno()!=null) {
//			result.setTu(Short.parseShort(item.getTurno()));
//		}
//		
//		if(item.getGuardia()!=null) {
//			result.setGuardiaturnoIdguardia(item.getGuardia());
//		}
//		
//		if(item.getNumColegiado()!=null) {
//			result.setNum(Short.parseShort(item.getNumColegiado()));
//		}
//		
//		if(item.getNumeroexpediente()!=null) {
//			result.setNum(Short.parseShort(item.getNumeroexpediente()));
//		}
		
		
		return result;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarEstado(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarEstado() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");
						record.setFechabaja(new Date());

						response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarEstado() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarEstado() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarFamiliar(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarFamiliar() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarFamiliar() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");
						record.setFechabaja(new Date());

						response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarFamiliar() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarFamiliar() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarFamiliar() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public InsertResponseDTO nuevoEstado(List<EjgItem> datos, HttpServletRequest request) {
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoEJG()));
						record.setFechainicio(new Date());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");

						response = scsEstadoejgMapper.insertSelective(record);
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.nuevoEstado() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.nuevoEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarImpugnacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarImpugnacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.guardarImpugnacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.guardarImpugnacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarImpugnacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarResolucion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarResolucion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarResolucion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarResolucion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.guardarResolucion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.guardarResolucion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarResolucion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.guardarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.guardarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.borrarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO descargarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.descargarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.descargarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO descargarDocumentacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarDocumentacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarDocumentacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarDocumentacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.descargarDocumentacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.descargarDocumentacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarDocumentacion() -> Salida del servicio.");

		return responsedto;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO borrarRelacion(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");
						record.setFechabaja(new Date());

						response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}
}