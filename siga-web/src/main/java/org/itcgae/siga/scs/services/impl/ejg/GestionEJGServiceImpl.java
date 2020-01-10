package org.itcgae.siga.scs.services.impl.ejg;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsExpedienteEconomicoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrestacionExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	
	@Override
	public EjgDTO datosEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("datosEJG() -> Entrada al servicio para obtener el colegiado");
		EjgDTO ejgDTO = new EjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("datosEJG() / scsEjgExtendsMapper.datosEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgExtendsMapper.datosEJG(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("datosEJG() / scsEjgExtendsMapper.datosEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			}else {
				LOGGER.warn("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}else {
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

				comboItems = scsPrestacionesExtendsMapper.comboPrestaciones(usuarios.get(0).getIdlenguaje().toString(), idInstitucion.toString());

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
	public UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("unidadFamiliarEJG() -> Entrada al servicio para obtener unidad Familiar");
		UnidadFamiliarEJGDTO unidadFamiliarEJGDTO = new UnidadFamiliarEJGDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				 GenParametrosExample genParametrosExample = new GenParametrosExample();
			        genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
			                .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			        genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			        LOGGER.info(
			                "busquedaEJG() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			        tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			        LOGGER.info(
			                "busquedaEJG() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			        LOGGER.info(
			                "busquedaEJG() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
			        if (tamMax != null) {
			            tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			        } else {
			            tamMaximo = null;
			        }
		
				LOGGER.info("unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Entrada a scsEjgExtendsMapper para obtener Unidad Familiar");
				unidadFamiliarEJGDTO.setUnidadFamiliarEJGItems(scsPersonajgExtendsMapper.unidadFamiliarEJG(ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Salida de scsEjgExtendsMapper para obtener Unidad Familiar");
			}else {
				LOGGER.warn("unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}else {
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

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
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
		
				LOGGER.info("getExpedientesEconomicos() / setUnidadFamiliarEJGItems.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener Expedienets Económicos");
				expedienteEconomicoDTO.setExpEconItems(scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("getExpedientesEconomicos() / setUnidadFamiliarEJGItems.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener Expedienets Económicos");
			}else {
				LOGGER.warn("getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}else {
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

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getEstados() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEstadoejgExtendsMapper para obtener los estados del EJG");
				estadoEjgDTO.setEstadoEjgItems(scsEstadoejgExtendsMapper.getEstados(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("getEstados() / scsEjgExtendsMapper.getEstados() -> Salida de scsEstadoejgExtendsMapper para obtener los estados del EJG");
			}else {
				LOGGER.warn("getEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}else {
			LOGGER.warn("getEstados() -> idInstitucion del token nula");
		}
	return estadoEjgDTO;
	}
	@Override
	public EjgDocumentacionDTO getDocumentos(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
