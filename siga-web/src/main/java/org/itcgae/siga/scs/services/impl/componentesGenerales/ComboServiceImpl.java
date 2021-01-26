package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.entities.ScsSubzona;
import org.itcgae.siga.db.entities.ScsSubzonaExample;
import org.itcgae.siga.db.entities.ScsZona;
import org.itcgae.siga.db.entities.ScsZonaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAreasMateriasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupofacturacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJurisdiccionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsMateriaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPartidasPresupuestariasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRequisitosGuardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoDesignaColegioExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoEJGColegioExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoEJGExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoSOJExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoTurnosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTiposGuardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsZonasExtendsMapper;
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

				List<ComboItem> comboItems = scsTipoEJGExtendsMapper.comboTipoEjg(Short.parseShort(usuarios.get(0).getIdlenguaje()));

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

				List<ComboItem> comboItems = scsEstadoEJGExtendsMapper.comboEstadoEjg(Short.parseShort(usuarios.get(0).getIdlenguaje()));

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

				List<ComboItem> comboItems = scsTipoEJGColegioExtendsMapper.comboTipoEjgColegio(Short.parseShort(usuarios.get(0).getIdlenguaje()));

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

				List<ComboItem> comboItems = scsTipoDesignaColegioExtendsMapper.comboTipoDesignacion(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion.toString());

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

				List<ComboItem> comboItems = scsTipoSOJExtendsMapper.comboTipoSOJ(Short.parseShort(usuarios.get(0).getIdlenguaje()));

				LOGGER.info(
						"comboTipoSOJ() / scsTipoSOJExtendsMapper.comboTipoSOJ() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo TipoSOJ");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoSOJ() -> Salida del servicio para obtener combo combo TipoSOJ");
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

				List<ComboItem> comboItems = scsComisariaExtendsMapper.comboComisaria(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion);

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

				List<ComboItem> comboItems = scsJuzgadoExtendsMapper.comboJuzgado(Short.parseShort(usuarios.get(0).getIdlenguaje()), idInstitucion);

				LOGGER.info(
						"comboJuzgado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Salida a scsGuardiasturnoExtendsMapper para obtener combo Juzgado");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboJuzgado() -> Salida del servicio para obtener combo Juzgado");
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

				example.createCriteria().andIdzonaEqualTo(Short.valueOf(idZona)).andIdinstitucionEqualTo(idInstitucion)
						.andFechabajaIsNull();
				example.setOrderByClause("nombre");
				List<ScsSubzona> subZonas = scsSubZonasExtendsMapper.selectByExample(example);

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

				comboJuris.setCombooItems(
						scsJurisdiccionExtendsMapper.getComboJurisdiccion(usuarios.get(0).getIdlenguaje()));

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
										
					if(!UtilidadesString.esCadenaVacia(importe) && "1".equals(importe)) {
						item.setLabel(partida.getNombrepartida()+" - "+partida.getImportepartida().toString().replace('.', ',')+" €");
					}else{
						item.setLabel(partida.getNombrepartida());
					}

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

}
