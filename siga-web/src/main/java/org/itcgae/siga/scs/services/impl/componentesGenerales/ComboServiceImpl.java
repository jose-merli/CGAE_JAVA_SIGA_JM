package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.ScsActuaciondesignaMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpProcedimientosExeaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.*;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboServiceImpl implements ComboService {

	private Logger LOGGER = Logger.getLogger(ComboServiceImpl.class);

	@Autowired
	private ScsZonasExtendsMapper scsZonasExtendsMapper;

	@Autowired
	private ScsSubzonaExtendsMapper scsSubZonasExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsTurnoMapper scsTurnoMapper;

	@Autowired
	private ScsJurisdiccionExtendsMapper scsJurisdiccionExtendsMapper;

	@Autowired
	private ScsAreasMateriasExtendsMapper scsAreasMateriasExtendsMapper;

	@Autowired
	private ScsMateriaExtendsMapper scsMateriaExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsTipoTurnosExtendsMapper scsTipoTurnosExtendsMapper;

	@Autowired
	private ScsTiposGuardiasExtendsMapper scsTiposGuardiasExtendsMapper;

	@Autowired
	private ScsRequisitosGuardiasExtendsMapper scsRequisitosGuardiasExtendsMapper;

	@Autowired
	private ScsGrupofacturacionExtendsMapper scsGrupofacturacionExtendsMapper;

	@Autowired
	private ScsPartidasPresupuestariasExtendsMapper scsPartidasPresupuestariasExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private ScsOrdenacionColasExtendsMapper scsOrdenacionColasExtendsMapper;

	@Autowired
	private ScsTipoEJGExtendsMapper scsTipoEJGExtendsMapper;

	@Autowired
	private ScsTipoEJGColegioExtendsMapper scsTipoEJGColegioExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoEJGExtendsMapper;

	@Autowired
	private ScsTipoDesignaColegioExtendsMapper scsTipoDesignaColegioExtendsMapper;

	@Autowired
	private ScsTipoSOJExtendsMapper scsTipoSOJExtendsMapper;

	@Autowired
	private ScsComisariaExtendsMapper scsComisariaExtendsMapper;

	@Autowired
	private ScsJuzgadoExtendsMapper scsJuzgadoExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;
	
	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionesTurnoExtendsMapper;
	
	@Autowired
	private ScsInscripcionguardiaExtendsMapper scsInscripcionguardiaExtendsMapper;
	
	@Autowired
	private ScsEstadoasistenciaExtendsMapper scsEstadoasistenciaExtendsMapper;

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private ScsActuacionasistenciaExtendsMapper scsActuacionasistenciaExtendsMapper;

	@Autowired
	private ScsActuaciondesignaMapper scsActuaciondesignaMapper;

	@Autowired
	private ExpProcedimientosExeaExtendsMapper expProcedimientosExeaExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;
	

	@Override
	public ComboDTO comboTipoEjg(HttpServletRequest request) {

		LOGGER.info("comboTipoEjg() -> Entrada al servicio para combo TipoEjg");
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
						"comboTipoEjg() / scsTipoEJGExtendsMapper.comboTipoEjg() -> Entrada a scsTipoEJGExtendsMapper para obtener combo tipoejg");

				List<ComboItem> comboItems = scsTipoEJGExtendsMapper
						.comboTipoEjg(Short.parseShort(usuarios.get(0).getIdlenguaje()));

				LOGGER.info(
						"comboTipoEjg() / scsTipoEJGExtendsMapper.comboTipoEjg() -> Salida e scsTipoEJGExtendsMapper para obtener combo tipoejg");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoEjg() -> Salida del servicio para obtener combo tipoejg");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboEstadoEjg(HttpServletRequest request) {

		LOGGER.info("comboEstadoEjg() -> Entrada al servicio para combo estadoEJG");
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
						"comboEstadoEjg() / scsEstadoEJGExtendsMapper.comboEstadoEjg() -> Entrada a scsTipoEJGExtendsMapper para obtener combo EstadoEjg");

				List<ComboItem> comboItems = scsEstadoEJGExtendsMapper
						.comboEstadoEjg(Short.parseShort(usuarios.get(0).getIdlenguaje()));

				LOGGER.info(
						"comboEstadoEjg() / scsEstadoEJGExtendsMapper.comboEstadoEjg() -> Salida e scsTipoEJGExtendsMapper para obtener combo EstadoEjg");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboEstadoEjg() -> Salida del servicio para obtener combo EstadoEjg");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoEjgColegio(HttpServletRequest request) {

		LOGGER.info("comboTipoEjgColegio() -> Entrada al servicio para búsqueda de comboTipoDesignacion");
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
						"comboTipoEjgColegio() / scsTipoEJGColegioExtendsMapper.comboTipoEjgColegio() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo TipoEjgColegio");

				List<ComboItem> comboItems = scsTipoEJGColegioExtendsMapper
						.comboTipoEjgColegio(Short.parseShort(usuarios.get(0).getIdlenguaje()),idInstitucion.toString());

				LOGGER.info(
						"comboTipoEjgColegio() / scsTipoEJGColegioExtendsMapper.comboTipoEjgColegio() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo TipoEjgColegio");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoEjgColegio() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoDesignacion(HttpServletRequest request) {

		LOGGER.info("comboTipoDesignacion() -> Entrada al servicio para búsqueda de comboTipoDesignacion");
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
						"comboTipoDesignacion() / scsTipoDesignaColegioExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo TipoDesignacion");

				List<ComboItem> comboItems = scsTipoDesignaColegioExtendsMapper.comboTipoDesignacion(
						Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion.toString());

				LOGGER.info(
						"comboTipoDesignacion() / scsTipoDesignaColegioExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo TipoDesignacion");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoDesignacion() -> Salida del servicio para obtener combo TipoDesignacion");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoSOJ(HttpServletRequest request) {

		LOGGER.info("comboTipoSOJ() -> Entrada al servicio para búsqueda de comboTipoSOJ");
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
						"comboTipoSOJ() / scsTipoSOJExtendsMapper.comboTipoSOJ() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo TipoSOJ");

				List<ComboItem> comboItems = scsTipoSOJExtendsMapper
						.comboTipoSOJ(Short.parseShort(usuarios.get(0).getIdlenguaje()));

				LOGGER.info(
						"comboTipoSOJ() / scsTipoSOJExtendsMapper.comboTipoSOJ() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo TipoSOJ");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoSOJ() -> Salida del servicio para obtener combo combo TipoSOJ");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoSOJColegio(HttpServletRequest request) {

		LOGGER.info("comboTipoSOJColegio() -> Entrada al servicio para búsqueda de comboTipoSOJColegio");
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
						"comboTipoSOJColegio() / scsTipoSOJExtendsMapper.comboTipoSOJColegio() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo Tipo Soj Colegio");

				List<ComboItem> comboItems = scsTipoSOJExtendsMapper
						.comboTipoSOJColegio(Short.parseShort(usuarios.get(0).getIdlenguaje()),idInstitucion);

				LOGGER.info(
						"comboTipoSOJColegio() / scsTipoSOJExtendsMapper.comboTipoSOJColegio() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Tipo Soj Colegio");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoSOJColegio() -> Salida del servicio para obtener combo combo Tipo Soj Colegio");
		}
		return comboDTO;
	}
	@Override
	public ComboDTO comboTipoConsulta(HttpServletRequest request) {

		LOGGER.info("comboTipoConsulta() -> Entrada al servicio para búsqueda de comboTipoConsulta");
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
						"comboTipoConsulta() / scsTipoSOJExtendsMapper.comboTipoConsulta() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo Tipo Consulta");

				List<ComboItem> comboItems = scsTipoSOJExtendsMapper
						.comboTipoConsulta(Short.parseShort(usuarios.get(0).getIdlenguaje()),idInstitucion);

				LOGGER.info(
						"comboTipoConsulta() / scsTipoSOJExtendsMapper.comboTipoConsulta() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Tipo Consulta");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoConsulta() -> Salida del servicio para obtener combo combo Tipo Consulta");
		}
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboTipoRespuesta(HttpServletRequest request) {

		LOGGER.info("comboTipoRespuesta() -> Entrada al servicio para búsqueda de comboTipoRespuesta");
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
						"comboTipoRespuesta() / scsTipoSOJExtendsMapper.comboTipoRespuesta() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo Tipo Respuesta");

				List<ComboItem> comboItems = scsTipoSOJExtendsMapper
						.comboTipoRespuesta(Short.parseShort(usuarios.get(0).getIdlenguaje()),idInstitucion);

				LOGGER.info(
						"comboTipoRespuesta() / scsTipoSOJExtendsMapper.comboTipoRespuesta() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Tipo Respuesta");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoRespuesta() -> Salida del servicio para obtener combo combo Tipo Respuesta");
		}
		return comboDTO;
	}
	@Override
	public ComboDTO comboComisaria(HttpServletRequest request) {

		LOGGER.info("comboComisaria() -> Entrada al servicio para búsqueda de Comisaria");
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
						"comboComisaria() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo Comisaria");

				List<ComboItem> comboItems = scsComisariaExtendsMapper
						.comboComisaria(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion);

				LOGGER.info(
						"comboComisaria() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Comisaria");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboComisaria() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboJuzgado(HttpServletRequest request) {

		LOGGER.info("comboJuzgado() -> Entrada al servicio para búsqueda de Juzgado");
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
						"comboJuzgado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo Juzgado");

				List<ComboItem> comboItems = scsJuzgadoExtendsMapper
						.comboJuzgado(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion);

				LOGGER.info(
						"comboJuzgado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Juzgado");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboJuzgado() -> Salida del servicio para obtener combo Juzgado");
		}
		return comboDTO;
	}

	/**
	 * comboJuzgadoPorInstitucion
	 */
	public ComboDTO comboJuzgadoPorInstitucion(String idInstitucion, HttpServletRequest request) {
		LOGGER.info("comboJuzgadoPorInstitucion() -> Entrada al servicio para búsqueda de Juzgado por institucion");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("comboJuzgadoPorInstitucion() -> Obteniendo los juzgados por institucion....");

				List<ComboItem> comboItems = scsJuzgadoExtendsMapper.comboJuzgado(
						Short.parseShort(usuarios.get(0).getIdlenguaje()), Short.parseShort(idInstitucion));

				LOGGER.info("comboJuzgadoPorInstitucion() -> Salida del servicio");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboJuzgado() -> Salida del servicio para obtener combo Juzgado");
		} else {
			LOGGER.warn("comboJuzgadoPorInstitucion() -> Salida del servicio. No viene informado la idInstitucion");
		}

		return comboDTO;
	}

	// PK

	public ComboDTO getComboZonas(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboZonas = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboZonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboZonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getComboZonas() -> Entrada para obtener la información de las distintas zonas");

				ScsZonaExample example = new ScsZonaExample();

				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
				example.setOrderByClause("nombre");
				List<ScsZona> zonas = scsZonasExtendsMapper.selectByExample(example);

				List<ComboItem> comboItems = new ArrayList<ComboItem>();

				for (ScsZona zona : zonas) {
					ComboItem item = new ComboItem();
					item.setLabel(zona.getNombre());
					item.setValue(zona.getIdzona().toString());

					comboItems.add(item);

				}

				comboZonas.setCombooItems(comboItems);
				LOGGER.info("getComboZonas() -> Salida ya con los datos recogidos");
			}
		}
		return comboZonas;

	}

	public ComboDTO getComboSubZonas(HttpServletRequest request, String idZona) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboSubZonas = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboSubZonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboSubZonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getComboSubZonas() -> Entrada para obtener la información de las distintas subZonas");

				ScsSubzonaExample example = new ScsSubzonaExample();

				List<ScsSubzona> subZonas;
				if (idZona.contains(",")) {
					subZonas = scsSubZonasExtendsMapper.getIdSubzona2(idInstitucion, idZona);
				} else {
					example.createCriteria().andIdzonaEqualTo(Short.valueOf(idZona))
							.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
					example.setOrderByClause("nombre");
					subZonas = scsSubZonasExtendsMapper.selectByExample(example);
				}

				List<ComboItem> comboItems = new ArrayList<ComboItem>();

				for (ScsSubzona zona : subZonas) {
					ComboItem item = new ComboItem();
					item.setLabel(zona.getNombre());
					item.setValue(zona.getIdsubzona().toString());

					comboItems.add(item);

				}

				comboSubZonas.setCombooItems(comboItems);
				LOGGER.info("getComboZonas() -> Salida ya con los datos recogidos");

			}
		}
		return comboSubZonas;

	}

	public ComboDTO getJurisdicciones(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboJuris = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getJurisdicciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getJurisdicciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"getJurisdicciones() -> Entrada para obtener la información de las distintas jurisdicciones");

				List<ComboItem> items = scsJurisdiccionExtendsMapper.getComboJurisdiccion(usuarios.get(0).getIdlenguaje());
				List<String> valuesList = new ArrayList<String>();
				items.forEach(it -> {
					valuesList.add(it.getValue());
				});
				ComboItem todos = new ComboItem();
				todos.setLabel("");
				String valueAll = valuesList.stream().collect(Collectors.joining(","));
				todos.setValue(valueAll);
				
				items.add(todos);
				comboJuris.setCombooItems(items);

				LOGGER.info("getJurisdicciones() -> Salida ya con los datos recogidos");

			}
		}
		return comboJuris;

	}

	public ComboDTO getComboGrupoFacturacion(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboGrupoFact = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboGrupoFacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"getComboGrupoFacturacion() -> Entrada para obtener la información de los distintos grupos de facturacion");

				comboGrupoFact.setCombooItems(scsGrupofacturacionExtendsMapper
						.getComboGrupoFacturacion(idInstitucion.toString(), usuarios.get(0).getIdlenguaje()));

				LOGGER.info("getComboGrupoFacturacion() -> Salida ya con los datos recogidos");

			}
		}
		return comboGrupoFact;

	}

	public ComboDTO getComboPartidasPresupuestarias(HttpServletRequest request, String importe) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboPartPres = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboPartidasPresupuestarias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboPartidasPresupuestarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"getComboPartidasPresupuestarias() -> Entrada para obtener la información de las distintas subZonas");

				ScsPartidapresupuestariaExample example = new ScsPartidapresupuestariaExample();

				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
				example.setOrderByClause("nombrepartida");
				List<ScsPartidapresupuestaria> partidas = scsPartidasPresupuestariasExtendsMapper
						.selectByExample(example);

				List<ComboItem> comboItems = new ArrayList<ComboItem>();

				for (ScsPartidapresupuestaria partida : partidas) {
					ComboItem item = new ComboItem();
					item.setLabel(partida.getNombrepartida());
					item.setValue(partida.getIdpartidapresupuestaria().toString());

					comboItems.add(item);

				}

				comboPartPres.setCombooItems(comboItems);
				LOGGER.info("getComboPartidasPresupuestarias() -> Salida ya con los datos recogidos");

			}
		}
		return comboPartPres;
	}

	// Iván
	@Override
	public ComboDTO comboTurnos(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTurnos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTurnosExtendsMapper.comboTurnos(idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTurnos() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}

	
	@Override
	public ComboDTO comboTurnosDesignacion(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTurnosDesignacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTurnosDesignacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTurnosDesignacion() / scsTurnosExtendsMapper.comboTurnos() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTurnosExtendsMapper.comboTurnosDesignacion(idInstitucion);

				LOGGER.info(
						"comboTurnosDesignacion() / scsTurnosExtendsMapper.comboTurnos() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTurnos() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}
	@Override
	public ComboDTO comboAreas(HttpServletRequest request) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsAreasMateriasExtendsMapper.comboAreas(idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}

	@Override
	public ComboDTO comboTiposTurno(HttpServletRequest request) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String idLenguaje = "";
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				LOGGER.info(
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTipoTurnosExtendsMapper.comboTurnos(idLenguaje);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}

	@Override
	public ComboDTO comboMaterias(HttpServletRequest request, String idArea, String filtro) {
		ComboDTO materiasReturn = new ComboDTO();
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAreas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				List<ComboItem> comboItems = scsMateriaExtendsMapper.comboMaterias(idInstitucion, idArea, filtro);

				materiasReturn.setCombooItems(comboItems);
			}
		}
		return materiasReturn;
	}

	@Override
	public ComboDTO comboTiposGuardia(HttpServletRequest request) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String idLenguaje = "";
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				LOGGER.info(
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTiposGuardiasExtendsMapper.comboTiposGuardia(idLenguaje);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		}
		return comboDTO;

	}

	@Override
	public ComboDTO comboRequisitosGuardias(HttpServletRequest request) {
		LOGGER.info("comboAreas() -> Entrada al servicio para búsqueda de las areas");
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
						"comboAreas() / scsTipoactuacionExtendsMapper.comboAreas() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsRequisitosGuardiasExtendsMapper.comboRequisitosGuardia();

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.comboAreas() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		}
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

				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboGuardias(idTurno,
						idInstitucion.toString());

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboGuardias() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;

	}
	@Override
	public ComboDTO comboGuardiasNoBaja(HttpServletRequest request, String idTurno) {
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

				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboGuardiasNoBaja(idTurno,
						idInstitucion.toString());

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboGuardias() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;

	}

	@Override
	public ComboDTO comboProcedimientosEXEA(HttpServletRequest request) {
		LOGGER.info("comboProcedimientosEXEA() -> Entrada al servicio para obtener el combo de procedimientos de EXEA");
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
						"comboProcedimientosEXEA() / expProcedimientosExeaExtendsMapper.comboProcedimientosEXEA() -> Entrada a expProcedimientosExeaExtendsMapper para obtener los procedimientos");

				List<ComboItem> comboItems = expProcedimientosExeaExtendsMapper.comboProcedimientosEXEA(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());
				LOGGER.info(
						"comboProcedimientosEXEA() / expProcedimientosExeaExtendsMapper.comboProcedimientosEXEA() -> Salida a expProcedimientosExeaExtendsMapper para obtener los procedimientos");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboProcedimientosEXEA() -> Salida del servicio para obtener los procedimientos");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboGuardiasNoGrupo(HttpServletRequest request, String idTurno) {
		LOGGER.info("comboGuardiasNoGrupo() -> Entrada al servicio para búsqueda de las guardias que no son por grupo");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && !usuarios.isEmpty()) {

				LOGGER.info(
						"comboGuardiasNoGrupo() / scsGuardiasturnoExtendsMapper.comboGuardiasNoGrupo() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias que no son por grupo");

				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboGuardiasNoGrupo(idTurno,
						idInstitucion.toString());

				LOGGER.info(
						"comboGuardiasNoGrupo() / scsGuardiasturnoExtendsMapper.comboGuardiasNoGrupo() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias que no son por grupo");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info(
					"comboGuardiasNoGrupo() -> Salida del servicio para obtener combo guardias que no son por grupo");
		}
		return comboDTO;

	}
	
	@Override
	public ComboDTO comboGuardiasGrupo(HttpServletRequest request, String idTurno) {
		LOGGER.info("comboGuardiasNoGrupo() -> Entrada al servicio para búsqueda de las guardias que no son por grupo");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (usuarios != null && !usuarios.isEmpty()) {
				
				LOGGER.info(
						"comboGuardiasNoGrupo() / scsGuardiasturnoExtendsMapper.comboGuardiasNoGrupo() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias que no son por grupo");
				
				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboGuardiasGrupo(idTurno,
						idInstitucion.toString());
				
				LOGGER.info(
						"comboGuardiasNoGrupo() / scsGuardiasturnoExtendsMapper.comboGuardiasNoGrupo() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias que no son por grupo");
				
				comboDTO.setCombooItems(comboItems);
			}
			
			LOGGER.info(
					"comboGuardiasNoGrupo() -> Salida del servicio para obtener combo guardias que no son por grupo");
		}
		return comboDTO;
		
	}

	@Override
	public ComboDTO comboGuardiasUpdate(HttpServletRequest request, String idTurno) {
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

				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboGuardiasUpdate(idTurno,
						idInstitucion.toString());

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboGuardias() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;

	}

	public ComboColaOrdenadaDTO ordenCola(HttpServletRequest request, String idordenacioncolas) {
		LOGGER.info("getPerfiles() -> Entrada al servicio para obtener los perfiles disponibles");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboColaOrdenadaDTO comboDTO = new ComboColaOrdenadaDTO();
		List<ComboColaOrdenadaItem> comboItems = new ArrayList<ComboColaOrdenadaItem>();

		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				if (usuarios != null && usuarios.size() > 0) {

					comboItems = scsOrdenacionColasExtendsMapper.ordenColas(idordenacioncolas);

					comboDTO.setColaOrden(comboItems);
				}

			}
		} catch (Exception e) {
			LOGGER.error(e);
			Error error = new Error();
			error.setCode(500);
			error.setMessage(e.getMessage());
			comboDTO.setError(error);
			e.printStackTrace();
		}

		LOGGER.info("getPerfiles() -> Salida del servicio para obtener los perfiles disponibles");
		return comboDTO;
	}
	
	
	@Override
	public ComboDTO comboEstados(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTurnosExtendsMapper.comboEstados(idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboEstados() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}

	@Override
	public ComboDTO comboListasGuardias(HttpServletRequest request, String idTurno) {
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

				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboListasGuardias(	idInstitucion.toString());

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboGuardias() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;

	}
	
	@Override
	public ComboDTO comboConjuntoGuardias(HttpServletRequest request) {
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

				List<ComboItem> comboItems = scsGuardiasturnoExtendsMapper.comboConjuntoGuardias(	idInstitucion.toString());

				LOGGER.info(
						"comboGuardias() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener las guardias");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboGuardias() -> Salida del servicio para obtener combo guardias");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboJuzgadoDesignaciones(HttpServletRequest request,String idJuzgado) {

		LOGGER.info("comboJuzgadoDesignaciones() -> Entrada al servicio para búsqueda de Juzgado");
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
						"comboJuzgadoDesignaciones() / scsJuzgadoExtendsMapper.comboJuzgadoDesignaciones() -> Entrada a scsJuzgadoExtendsMapper para obtener combo Juzgado");

				List<JuzgadoItem> juzgadosItems = scsJuzgadoExtendsMapper
						.comboJuzgadoDesignaciones(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion,idJuzgado);

				LOGGER.info(
						"comboJuzgadoDesignaciones() / scsJuzgadoExtendsMapper.comboJuzgadoDesignaciones() -> Salida a scsJuzgadoExtendsMapper para obtener combo Juzgado");

				List<ComboItem> comboItems = new ArrayList<>();
				for (JuzgadoItem j : juzgadosItems) {
					ComboItem comboItem = new ComboItem();
					comboItem.setValue(j.getIdJuzgado());
					// PARENTESIS + Código de selección + PARENTESIS + ESPACIO + Nombre + 
					// ESPACIO + PARENTESIS + Nombre de Población + PARENTESIS. 
					//comboItem.setLabel(j.getCodigoExt2() + ": " + j.getNombre() + " (" + j.getNombrePoblacion() + ")");
					comboItem.setLabel(" (" + j.getCodigoExt2() + ") " + j.getNombre() + " (" + j.getNombrePoblacion() + ")");
					comboItems.add(comboItem);
					
				}
				// Ordenar Combo Juzgado.
				Collections.sort(comboItems);
				comboDTO.setCombooItems(comboItems);
				
			}

			LOGGER.info("comboJuzgadoDesignaciones() -> Salida del servicio para obtener combo Juzgado");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboModulo(HttpServletRequest request, ActuacionDesignaItem designaItem) {
		LOGGER.info("modulo() -> Entrada al servicio para obtener combo modulos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<ComboItem2> comboItems2 = new ArrayList<ComboItem2>();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			GenParametrosExample exampleParametro = new GenParametrosExample();
			exampleParametro.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(idInstitucion);
			
			List<GenParametros> valor = genParametrosMapper.selectByExample(exampleParametro);
			
			int filtro = 0; // Por fecha actual por defecto
			
			if(valor == null  || valor.isEmpty() ) {
				GenParametrosExample exampleParametroAux = new GenParametrosExample();
				exampleParametroAux.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(Short.valueOf("0"));
				List<GenParametros> valorAux = genParametrosMapper.selectByExample(exampleParametroAux);
				filtro = Integer.parseInt(valorAux.get(0).getValor());
			}else {
				filtro = Integer.parseInt(valor.get(0).getValor());
			}

			String fecha = "SYSDATE";
			//Filtros , (0-Fecha Actual, 1 - Fecha Designacion , 2- Fecha Actuación)
			if (filtro == 1 && !UtilidadesString.esCadenaVacia(designaItem.getNumero()) && !UtilidadesString.esCadenaVacia(designaItem.getAnio())
					&& !UtilidadesString.esCadenaVacia(designaItem.getIdTurno())) {
				ScsDesignaKey designaKey = new ScsDesignaKey();
				designaKey.setNumero(Long.parseLong(designaItem.getNumero()));
				designaKey.setAnio(Short.parseShort(designaItem.getAnio()));
				designaKey.setIdturno(Integer.parseInt(designaItem.getIdTurno()));
				designaKey.setIdinstitucion(idInstitucion);

				ScsDesigna designa = scsDesignacionesExtendsMapper.selectByPrimaryKey(designaKey);

				if (designa != null) {
					fecha = dateFormat.format(designa.getFechaentrada());
				} else {
					fecha = "SYSDATE";
					filtro = 0;
				}

			} else if (filtro == 2  && !UtilidadesString.esCadenaVacia(designaItem.getNumero()) && !UtilidadesString.esCadenaVacia(designaItem.getAnio())
					&& !UtilidadesString.esCadenaVacia(designaItem.getIdTurno()) && !UtilidadesString.esCadenaVacia(designaItem.getNumeroAsunto())) {
				ScsActuaciondesignaKey actuacionKey = new ScsActuaciondesignaKey();
				actuacionKey.setNumero(Long.parseLong(designaItem.getNumero()));
				actuacionKey.setAnio(Short.parseShort(designaItem.getAnio()));
				actuacionKey.setNumeroasunto(Long.parseLong(designaItem.getNumeroAsunto()));
				actuacionKey.setIdturno(Integer.parseInt(designaItem.getIdTurno()));
				actuacionKey.setIdinstitucion(idInstitucion);

				ScsActuaciondesigna actuacion = scsActuaciondesignaMapper.selectByPrimaryKey(actuacionKey);

				if (actuacion != null) {
					fecha = dateFormat.format(actuacion.getFecha());
				} else {
					fecha = "SYSDATE";
					filtro = 0;
				}
			} else {
				fecha = "SYSDATE";
				filtro = 0;
			}

			if (null != usuarios && usuarios.size() > 0) {

				comboItems2 = scsDesignacionesExtendsMapper.comboModulos(idInstitucion, filtro, fecha);

				for (ComboItem2 item : comboItems2) {
					String label = "";
					if (idInstitucion != 2005) {
						label = item.getLabel1() + ' ' + item.getLabel2();
					}else {
						label = item.getLabel2();
					}
					ComboItem combo = new ComboItem();
					combo.setLabel(label);
					combo.setValue(item.getValue());
					comboItems.add(combo);
				}
				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener combo modulo");

		return comboDTO;
	}
	
	@Override
	public ComboDTO comboDelitos(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("modulo() -> Entrada al servicio para obtener combo modulos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDesignacionesExtendsMapper.comboDelitos(designaItem,idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener combo modulo");

		return comboDTO;
	}

	@Override
	public ComboDTO comboProcedimientos(HttpServletRequest request) {
		LOGGER.info("comboProcedimientos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDesignacionesExtendsMapper.comboProcedimientos(idInstitucion, usuarios.get(0).getIdlenguaje());

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboProcedimientosConJuzgado(HttpServletRequest request, String idJuzgado, Short idPretension) {
		LOGGER.info("comboProcedimientos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<ComboItem> procedimientosJuzgados = new ArrayList<ComboItem>();
		List<ComboItem> pretensionProcedimiento = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				procedimientosJuzgados = scsDesignacionesExtendsMapper.getProcedimientosJuzgados(idInstitucion,
						idJuzgado);

				if (procedimientosJuzgados != null && procedimientosJuzgados.size() > 0) {
					List<String> idPretensiones = new ArrayList<String>();
					for (ComboItem label : procedimientosJuzgados) {
						idPretensiones.add(label.getValue());
					}

					pretensionProcedimiento = scsDesignacionesExtendsMapper.getProcedimientosPretension(idInstitucion,
							idPretensiones);

					if ((pretensionProcedimiento != null && pretensionProcedimiento.size() > 0) || idPretension != null) {
						idPretensiones = new ArrayList<String>();
						for (ComboItem label : pretensionProcedimiento) {
							idPretensiones.add(label.getLabel());
						}
						
						if(idPretension != null) {
							idPretensiones.add(idPretension.toString());
						}

						comboItems = scsDesignacionesExtendsMapper.comboProcedimientosConJuzgado(idInstitucion,
								idPretensiones);
					}
				}

				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}
	
	@Override
	public ComboDTO comboProcedimientosConJuzgadoEjg(HttpServletRequest request, EjgItem item) {
		
		return comboProcedimientosConJuzgado(request, item.getJuzgado(), item.getIdPretension());
	}

	@Override
	public ComboDTO comboModulosConJuzgado(HttpServletRequest request, String idJuzgado,String fecha) {
		LOGGER.info("comboProcedimientos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<ComboItem2> comboItems2 = new ArrayList<ComboItem2>();
		List<ComboItem> procedimientosJuzgados = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				procedimientosJuzgados = scsDesignacionesExtendsMapper.getProcedimientosJuzgados(idInstitucion,
						idJuzgado);
				GenParametrosExample exampleParametro = new GenParametrosExample();
				exampleParametro.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(idInstitucion);
				
				List<GenParametros> valor = genParametrosMapper.selectByExample(exampleParametro);
				
				int filtro = 0; // Por fecha actual por defecto
				
				if(valor == null  || valor.isEmpty() ) {
					GenParametrosExample exampleParametroAux = new GenParametrosExample();
					exampleParametroAux.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(Short.valueOf("0"));
					List<GenParametros> valorAux = genParametrosMapper.selectByExample(exampleParametroAux);
					filtro = Integer.parseInt(valorAux.get(0).getValor());
				}else {
					filtro = Integer.parseInt(valor.get(0).getValor());
				}
				if(fecha== null || fecha.isEmpty()) {
					fecha = "SYSDATE"; filtro = 0;
				}

				if (procedimientosJuzgados != null && procedimientosJuzgados.size() > 0) {
					List<String> idPretensiones = new ArrayList<String>();
					for (ComboItem label : procedimientosJuzgados) {
						idPretensiones.add(label.getValue());
					}

					comboItems = scsDesignacionesExtendsMapper.comboModulosConJuzgado(idInstitucion, idPretensiones,filtro, fecha);
					for (ComboItem2 item : comboItems2) {
						String label = "";
						if (idInstitucion != 2005) {
							label = item.getLabel1() + ' ' + item.getLabel2();
						}else {
							label = item.getLabel2();
						}
						ComboItem combo = new ComboItem();
						combo.setLabel(label);
						combo.setValue(item.getValue());
						comboItems.add(combo);
					}
				}

				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}
	
	@Override
	public ComboDTO comboAllModulos(HttpServletRequest request) {
		LOGGER.info("comboModulos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<ComboItem> procedimientosJuzgados = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				procedimientosJuzgados = scsDesignacionesExtendsMapper.getProcedimientosJuzgados2(idInstitucion);
				
				GenParametrosExample exampleParametro = new GenParametrosExample();
				exampleParametro.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(idInstitucion);
				
				List<GenParametros> valor = genParametrosMapper.selectByExample(exampleParametro);
				
				int filtro = 0; // Por fecha actual por defecto
				
				if (procedimientosJuzgados != null && procedimientosJuzgados.size() > 0) {
					List<String> idPretensiones = new ArrayList<String>();
					for (ComboItem label : procedimientosJuzgados) {
						idPretensiones.add(label.getValue());
					}

					comboItems = scsDesignacionesExtendsMapper.comboModulosConJuzgado(idInstitucion, idPretensiones,filtro, "SYSDATE");
				}

				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboModulosConProcedimientos(HttpServletRequest request, String idPretension, String fecha) {
		LOGGER.info("comboProcedimientos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<ComboItem> procedimientosJuzgados = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				procedimientosJuzgados = scsDesignacionesExtendsMapper.getProcedimientoPretension(idInstitucion,
						idPretension);

				GenParametrosExample exampleParametro = new GenParametrosExample();
				exampleParametro.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(idInstitucion);
				
				List<GenParametros> valor = genParametrosMapper.selectByExample(exampleParametro);
				
				int filtro = 0; // Por fecha actual por defecto
				
				if(valor == null  || valor.isEmpty() ) {
					GenParametrosExample exampleParametroAux = new GenParametrosExample();
					exampleParametroAux.createCriteria().andParametroEqualTo("FILTRAR_MODULOS_PORFECHA").andIdinstitucionEqualTo(Short.valueOf("0"));
					List<GenParametros> valorAux = genParametrosMapper.selectByExample(exampleParametroAux);
					filtro = Integer.parseInt(valorAux.get(0).getValor());
				}else {
					filtro = Integer.parseInt(valor.get(0).getValor());
				}
				if(fecha== null || fecha.isEmpty()) {
					fecha = "SYSDATE"; filtro = 0;
				}
				if (procedimientosJuzgados != null && procedimientosJuzgados.size() > 0) {
					List<String> idPretensiones = new ArrayList<String>();
					for (ComboItem label : procedimientosJuzgados) {
						idPretensiones.add(label.getValue());
					}

					comboItems = scsDesignacionesExtendsMapper.comboModulosConProcedimientos(idInstitucion,
							idPretensiones, filtro, fecha);

				}

				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboProcedimientosConModulo(HttpServletRequest request, String idModulo) {
		LOGGER.info("comboProcedimientos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		List<ComboItem> procedimientosJuzgados = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				procedimientosJuzgados = scsDesignacionesExtendsMapper.getPretensionModulo(idInstitucion, idModulo);
				if (procedimientosJuzgados != null && procedimientosJuzgados.size() > 0) {
					List<String> idPretensiones = new ArrayList<String>();
					for (ComboItem label : procedimientosJuzgados) {
						idPretensiones.add(label.getValue());
					}

					comboItems = scsDesignacionesExtendsMapper.comboProcedimientosConModulos(idInstitucion,
							idPretensiones);
				}
				comboDTO.setCombooItems(comboItems);
			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}

	/**
	 * 
	 */
	public ComboDTO comboAcreditacionesPorModulo(HttpServletRequest request, String idModulo, String idTurno) {
		LOGGER.info(
				"comboAcreditacionesPorModulo() -> Entrada al servicio para obtener el comboAcreditacionesPorModulo");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> acreditaciones = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				ScsTurnoKey scsTurnoKey = new ScsTurnoKey();
				scsTurnoKey.setIdinstitucion(idInstitucion);
				scsTurnoKey.setIdturno(Integer.valueOf(idTurno));

				ScsTurno scsTurno = scsTurnoMapper.selectByPrimaryKey(scsTurnoKey);

				if (scsTurno.getActivarretriccionacredit().toUpperCase().equals("1")
						|| scsTurno.getActivarretriccionacredit().toUpperCase().equals("S")) {

					acreditaciones = scsDesignacionesExtendsMapper.comboAcreditacionesPorTipo(idInstitucion, idModulo);
				} else {
					acreditaciones = scsDesignacionesExtendsMapper.comboAcreditacionesPorModulo(idInstitucion,
							idModulo);
				}

				comboDTO.setCombooItems(acreditaciones);
			}
		}

		LOGGER.info("comboAcreditacionesPorModulo() -> Salida del servicio para obtener comboAcreditacionesPorModulo");

		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoDocumentacionDesigna(HttpServletRequest request) {
		LOGGER.info(
				"comboTipoDocumentacionDesigna() -> Entrada al servicio para obtener el comboTipoDocumentacionDesigna");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> tiposDoc = new ArrayList<ComboItem>();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && !usuarios.isEmpty()) {

				tiposDoc = scsDesignacionesExtendsMapper.comboTipoDocumentacionDesigna();

				comboDTO.setCombooItems(tiposDoc);
			}
		}

		LOGGER.info(
				"comboTipoDocumentacionDesigna() -> Salida del servicio para obtener comboTipoDocumentacionDesigna");
		return comboDTO;
	}

	@Override
	public ComboDTO comboComisariaCdgoExt(HttpServletRequest request) {
		LOGGER.info("comboComisariaCdgoExt() -> Entrada al servicio para búsqueda de Comisaria con codigo externo");
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
						"comboComisariaCdgoExt() / scsComisariaExtendsMapper.comboComisariaCdgoExt() -> Entrada a scsComisariaExtendsMapper para obtener combo Comisaria");

				List<ComboItem> comboItems = scsComisariaExtendsMapper
						.comboComisariaCdgoExt(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion);

				LOGGER.info(
						"comboComisariaCdgoExt() / scsComisariaExtendsMapper.comboComisariaCdgoExt() -> Salida a scsComisariaExtendsMapper para obtener combo Comisaria");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboComisariaCdgoExt() -> Salida del servicio para obtener combo comisaria con codigo externo");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO comboJuzgadoCdgoExt(HttpServletRequest request) {
		LOGGER.info("comboJuzgadoCdgoExt() -> Entrada al servicio para búsqueda de Juzgado por codigo externo");
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
						"comboJuzgadoCdgoExt() / scsJuzgadoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener combo Juzgado");

				List<ComboItem> comboItems = scsJuzgadoExtendsMapper
						.comboJuzgadoCdgoExt(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion);

				LOGGER.info(
						"comboJuzgadoCdgoExt() / scsJuzgadoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Juzgado");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboJuzgadoCdgoExt() -> Salida del servicio para obtener combo Juzgado por codigo externo");
		}
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboTurnosInscritoLetrado(HttpServletRequest request, String idPersona) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTurnosInscritoLetrado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTurnosInscritoLetrado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTurnosInscritoLetrado() / scsInscripcionesTurnoExtendsMapper.comboTurnosInscritoLetrado() -> Entrada a scsInscripcionesTurnoExtendsMapper para obtener los turnos a los que esta inscrito el letrado");

				List<ComboItem> comboItems = scsInscripcionesTurnoExtendsMapper.comboTurnosInscritoLetrado(idInstitucion, idPersona);

				LOGGER.info(
						"comboTurnosInscritoLetrado() / scsInscripcionesTurnoExtendsMapper.comboTurnosInscritoLetrado() -> Salida a scsInscripcionesTurnoExtendsMapper para obtener los turnos a los que esta inscrito el letrado");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTurnosInscritoLetrado() -> Salida del servicio para obtener combo turnos a los que esta inscrito el letrado");
		return comboDTO;
	}

	@Override
	public ComboDTO comboGuardiasInscritoLetrado(HttpServletRequest request, String idPersona, String idTurno) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboGuardiasInscritoLetrado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboGuardiasInscritoLetrado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboGuardiasInscritoLetrado() / scsInscripcionguardiaExtendsMapper.comboGuardiasInscritoLetrado() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				List<ComboItem> comboItems = scsInscripcionguardiaExtendsMapper.comboGuardiasInscritoLetrado(idInstitucion, idPersona, idTurno);

				LOGGER.info(
						"comboGuardiasInscritoLetrado() / scsInscripcionguardiaExtendsMapper.comboGuardiasInscritoLetrado() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboGuardiasInscritoLetrado() -> Salida del servicio para obtener combo turnos a los que esta inscrito el letrado");
		return comboDTO;
	}

	@Override
	public ComboDTO comboEstadosAsistencia(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboEstadosAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboEstadosAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboEstadosAsistencia() / scsInscripcionguardiaExtendsMapper.comboGuardiasInscritoLetrado() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				List<ComboItem> comboItems = scsEstadoasistenciaExtendsMapper.comboEstadosAsistencia(usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"comboEstadosAsistencia() / scsInscripcionguardiaExtendsMapper.comboGuardiasInscritoLetrado() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboEstadosAsistencia() -> Salida del servicio para obtener combo turnos a los que esta inscrito el letrado");
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoDocAsistencia(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTipoDocAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTipoDocAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoDocAsistencia() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				List<ComboItem> comboItems = scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia();

				LOGGER.info(
						"comboTipoDocAsistencia() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTipoDocAsistencia() -> Salida del servicio para obtener los tipos de documentacion de una asistencia");
		return comboDTO;
	}

	@Override
	public ComboDTO comboAsociadoAsistencia(HttpServletRequest request, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboAsociadoAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboAsociadoAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0 && !UtilidadesString.esCadenaVacia(anioNumero)) {

				LOGGER.info(
						"comboAsociadoAsistencia() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				List<ComboItem> comboItems = scsAsistenciaExtendsMapper.comboAsociadoAsistencia(anioNumero.split("/")[0], anioNumero.split("/")[1], idInstitucion, Integer.valueOf(usuarios.get(0).getIdlenguaje()));

				LOGGER.info(
						"comboAsociadoAsistencia() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTipoDocAsistencia() -> Salida del servicio para obtener los tipos de documentacion de una asistencia");
		return comboDTO;
	}

	@Override
	public ComboDTO comboCosteFijo(HttpServletRequest request, String anioNumero, String idTipoActuacion) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0 && !UtilidadesString.esCadenaVacia(anioNumero)) {

				LOGGER.info(
						"comboCosteFijo() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
				scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
				scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
				scsAsistenciaKey.setIdinstitucion(idInstitucion);
				ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);
				if(scsAsistencia != null){
					List<ComboItem> comboItems = scsActuacionasistenciaExtendsMapper.comboCosteFijoTipoActuacion(idTipoActuacion,idInstitucion,scsAsistencia.getIdtipoasistencia(),Integer.valueOf(usuarios.get(0).getIdlenguaje()));
					comboDTO.setCombooItems(comboItems);
				}

				LOGGER.info(
						"comboCosteFijo() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");
			}

		}
		LOGGER.info("comboCosteFijo() -> Salida del servicio para obtener los tipos de documentacion de una asistencia");
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoActuacionAsistencia(HttpServletRequest request, String anioNumero, String idTipoAsistencia) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTipoActuacionAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTipoActuacionAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoActuacionAsistencia() / scsAsistenciaExtendsMapper.comboTipoActuacionAsistencia() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				if(!UtilidadesString.esCadenaVacia(anioNumero)) {
					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);
					if (scsAsistencia != null) {
						List<ComboItem> comboItems = scsActuacionasistenciaExtendsMapper.comboTipoActuacion(idInstitucion, scsAsistencia.getIdtipoasistenciacolegio(), Integer.valueOf(usuarios.get(0).getIdlenguaje()));
						comboDTO.setCombooItems(comboItems);
					}
				}else if(!UtilidadesString.esCadenaVacia(idTipoAsistencia)){
					List<ComboItem> comboItems = scsActuacionasistenciaExtendsMapper.comboTipoActuacion(idInstitucion, Short.valueOf(idTipoAsistencia), Integer.valueOf(usuarios.get(0).getIdlenguaje()));
					comboDTO.setCombooItems(comboItems);
				}

				LOGGER.info(
						"comboTipoActuacionAsistencia() / scsAsistenciaExtendsMapper.comboTipoActuacionAsistencia() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");
			}

		}
		LOGGER.info("comboTipoActuacionAsistencia() -> Salida del servicio para obtener los tipos de documentacion de una asistencia");
		return comboDTO;
	}

	@Override
	public ComboDTO comboOrigenContacto(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboOrigenContacto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboOrigenContacto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboOrigenContacto() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Entrada a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				List<ComboItem> comboItems = scsAsistenciaExtendsMapper.comboOrigenContacto(idInstitucion);

				LOGGER.info(
						"comboOrigenContacto() / scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia() -> Salida a scsInscripcionguardiaExtendsMapper para obtener las guardias a los que esta inscrito el letrado");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboOrigenContacto() -> Salida del servicio para obtener los tipos de documentacion de una asistencia");
		return comboDTO;
	}

	
}
