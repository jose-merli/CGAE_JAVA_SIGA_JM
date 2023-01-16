package org.itcgae.siga.scs.services.impl.justiciables;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.AsuntosAsistenciaItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosDesignaItem;
import org.itcgae.siga.DTOs.scs.AsuntosEjgItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosSOJItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.scs.JusticiableDTO;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.DTOs.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.scs.JusticiableTelefonoItem;
import org.itcgae.siga.DTOs.scs.ScsUnidadfamiliarejgDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenPoblacionesExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgExample;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojExample;
import org.itcgae.siga.db.entities.ScsTelefonospersona;
import org.itcgae.siga.db.entities.ScsTelefonospersonaExample;
import org.itcgae.siga.db.entities.ScsTelefonospersonaKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgKey;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosDesignaMapperExtends;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPoblacionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipoviaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDefendidosdesignasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignasLetradoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsMinusvaliaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsParentescoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProfesionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTelefonosPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoGrupoLaboralExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoIngresoExtendsMapper;
import org.itcgae.siga.scs.services.justiciables.IGestionJusticiableService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionJusticiableServiceImpl implements IGestionJusticiableService {

	private Logger LOGGER = Logger.getLogger(GestionJusticiableServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;

	@Autowired
	ScsPersonajgMapper scsPersonajgMapper;

	@Autowired
	private ScsMinusvaliaExtendsMapper scsMinusvaliaExtendsMapper;

	@Autowired
	private ScsProfesionExtendsMapper scsProfesionExtendsMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private ScsTelefonosPersonaExtendsMapper scsTelefonosPersonaExtendsMapper;

	@Autowired
	private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;

	@Autowired
	private CenTipoviaExtendsMapper cenTipoviaExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private ScsDesignasLetradoExtendsMapper scsDesignasLetradoExtendsMapper;

	@Autowired
	private ScsDefendidosdesignasExtendsMapper scsDefendidosdesignasExtendsMapper;

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private ScsSojExtendsMapper scsSojExtendsMapper;

	@Autowired
	private ScsTipoGrupoLaboralExtendsMapper scsTipoGrupoLaboralExtendsMapper;

	@Autowired
	private ScsParentescoExtendsMapper scsParentescoExtendsMapper;

	@Autowired
	private ScsTipoIngresoExtendsMapper scsTipoIngresoExtendsMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private ScsDefendidosdesignaMapper scsDefendidosdesignaMapper;

	@Autowired
	private ScsDefendidosDesignaMapperExtends scsDefendidosDesignaMapperExtends;
	
	@Autowired
	private ScsAsistenciaMapper ScsAsistenciaMapper;

	private boolean validacionDireccion = false;
	private boolean validacionTipoVia = false;
	private boolean validacionCodigoPostal = false;
	private boolean validacionProvincia = false;
	private boolean validacionPoblacion = false;
	private boolean validacionSexo = false;
	private boolean validacionRegimenConyugal = false;
	private boolean validacionParentesco = false;
	private boolean validacionPais = false;
	private boolean validacionFechaNacimiento = false;
	private boolean validacionEstadoCivil = false;

	@Override
	public ComboDTO getMinusvalias(HttpServletRequest request) {
		LOGGER.info("getMinusvalia() -> Entrada al servicio para obtener combo minusvalias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getMinusvalia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getMinusvalia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getMinusvalia() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Entrada a scsMinusvaliaExtendsMapper para obtener minusvalias");

				List<ComboItem> comboItems = scsMinusvaliaExtendsMapper.getMinusvalias(idInstitucion,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"getMinusvalia() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Salida a scsMinusvaliaExtendsMapper para obtener minusvalias");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getMinusvalia() -> Salida del servicio para obtener combo minusvalias");
		return combo;
	}

	@Override
	public ComboDTO getGruposLaborales(HttpServletRequest request) {
		LOGGER.info("getGruposLaborales() -> Entrada al servicio para obtener grupos laborales");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getGruposLaborales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getGruposLaborales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getGruposLaborales() / scsTipoGrupoLaboralExtendsMapper.getGruposLaborales() -> Entrada a scsTipoGrupoLaboralExtendsMapper para obtener grupos laborales");

				List<ComboItem> comboItems = scsTipoGrupoLaboralExtendsMapper.getGruposLaborales(idInstitucion,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"getGruposLaborales() / scsTipoGrupoLaboralExtendsMapper.getGruposLaborales() -> Salida a scsTipoGrupoLaboralExtendsMapper para obtener grupos laborales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getGruposLaborales() -> Salida del servicio para obtener combo grupos laborales");
		return combo;
	}

	@Override
	public ComboDTO getParentesco(HttpServletRequest request) {
		LOGGER.info("getParentesco() -> Entrada al servicio para obtener combo parentesco");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getParentesco() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getParentesco() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getParentesco() / scsParentescoExtendsMapper.getParentesco() -> Entrada a scsParentescoExtendsMapper para obtener combo parentesco");

				List<ComboItem> comboItems = scsParentescoExtendsMapper.getParentesco(idInstitucion,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"getParentesco() / scsParentescoExtendsMapper.getParentesco() -> Salida a scsParentescoExtendsMapper para obtener combo parentesco");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getParentesco() -> Salida del servicio para obtener combo parentesco");
		return combo;
	}

	@Override
	public ComboDTO getTiposIngresos(HttpServletRequest request) {
		LOGGER.info("getTipoIngresos() -> Entrada al servicio para obtener combo tipoIngresos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getTipoIngresos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getTipoIngresos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getTipoIngresos() / scsParentescoExtendsMapper.getParentesco() -> Entrada a scsParentescoExtendsMapper para obtener combo tipoIngresos");

				List<ComboItem> comboItems = scsTipoIngresoExtendsMapper
						.getTiposIngresos(usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"getTipoIngresos() / scsParentescoExtendsMapper.getParentesco() -> Salida a scsParentescoExtendsMapper para obtener combo tipoIngresos");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getTipoIngresos() -> Salida del servicio para obtener combo tipoIngresos");
		return combo;
	}

	@Override
	public ComboDTO getProfesiones(HttpServletRequest request) {
		LOGGER.info("getProfesiones() -> Entrada al servicio para obtener combo profesiones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getProfesiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getProfesiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getProfesiones() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Entrada a scsMinusvaliaExtendsMapper para obtener minusvalias");

				List<ComboItem> comboItems = scsProfesionExtendsMapper.getProfesiones(usuario.getIdlenguaje());

				LOGGER.info(
						"getProfesiones() / scsMinusvaliaExtendsMapper.getJurisdicciones() -> Salida a scsMinusvaliaExtendsMapper para obtener minusvalias");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getProfesiones() -> Salida del servicio para obtener combo profesiones");
		return combo;
	}

	@Override
	public JusticiableDTO searchJusticiable(JusticiableBusquedaItem justiciableBusquedaItem,
			HttpServletRequest request) {

		LOGGER.info("searchJusticiable() -> Entrada al servicio para obtener los justiciables");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		JusticiableDTO justiciableDTO = new JusticiableDTO();
		List<ScsPersonajg> personajgList = new ArrayList<ScsPersonajg>();
		Error error = new Error();

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"searchJusticiable() / scsPersonajgExtendsMapper.searchJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

					ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
					scsPersonajgExample.createCriteria()
							.andIdpersonaEqualTo(Long.valueOf(justiciableBusquedaItem.getIdPersona()))
							.andIdinstitucionEqualTo(Short.valueOf(justiciableBusquedaItem.getIdInstitucion()));

					personajgList = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);

					LOGGER.info(
							"searchJusticiables() / scsPersonajgExtendsMapper.searchJusticiables() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");

					if (personajgList != null && personajgList.size() > 0) {

						ScsPersonajg personajg = personajgList.get(0);

						JusticiableItem justiciable = fillJusticiableItemOfScsPersonasjg(personajg);

						LOGGER.info(
								"searchJusticiable() / scsTelefonospersonaMapper.selectByExample() -> Entrada a scsTelefonospersonaMapper para obtener los telefonos del justiciables");

						ScsTelefonospersonaExample scsTelefonospersonaExamples = new ScsTelefonospersonaExample();
						scsTelefonospersonaExamples.createCriteria()
								.andIdpersonaEqualTo(Long.valueOf(justiciableBusquedaItem.getIdPersona()))
								.andIdinstitucionEqualTo(Short.valueOf(justiciableBusquedaItem.getIdInstitucion()));

						scsTelefonospersonaExamples.setOrderByClause("NOMBRETELEFONO asc");

						List<ScsTelefonospersona> telefonosList = scsTelefonosPersonaExtendsMapper
								.selectByExample(scsTelefonospersonaExamples);

						if (telefonosList != null && telefonosList.size() > 0) {

							List<JusticiableTelefonoItem> telefonos = new ArrayList<JusticiableTelefonoItem>();

							for (ScsTelefonospersona scsTelefonospersona : telefonosList) {
								JusticiableTelefonoItem telefono = new JusticiableTelefonoItem();
								telefono = fillJusticiableTelefonosItemOfScsTelefonospersona(scsTelefonospersona);

								telefonos.add(telefono);
							}

							justiciable.setTelefonos(telefonos);

						}

						LOGGER.info(
								"searchJusticiables() / scsTelefonospersonaMapper.selectByExample() -> Salida a scsTelefonospersonaMapper para obtener los telefonos del justiciables");

						justiciableDTO.setJusticiable(justiciable);

					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
				}

			}

		}
		LOGGER.info("searchJusticiable() -> Salida del servicio para obtener los justiciables");
		return justiciableDTO;
	}

	private JusticiableItem fillJusticiableItemOfScsPersonasjg(ScsPersonajg personajg) {

		JusticiableItem justiciableItem = new JusticiableItem();

		justiciableItem.setApellido1(personajg.getApellido1());
		justiciableItem.setApellido2(personajg.getApellido2());

		String apellidos = "";

		if (personajg.getApellido1() != null) {
			apellidos += personajg.getApellido1() + " ";
		}

		if (personajg.getApellido2() != null) {
			apellidos += personajg.getApellido2();
		}

		justiciableItem.setApellidos(apellidos);
		justiciableItem.setAsistidoAutorizaeejg(personajg.getAsistidoautorizaeejg());
		justiciableItem.setAsistidoSolicitajg(personajg.getAsistidosolicitajg());
		justiciableItem.setAutorizaAvisoTelematico(personajg.getAutorizaavisotelematico());
		justiciableItem.setAutorizaAvisoTelematico(personajg.getAutorizaavisotelematico());
		justiciableItem.setCnae(personajg.getCnae());
		justiciableItem.setCodigoPostal(personajg.getCodigopostal());
		justiciableItem.setCodigoPostal2(personajg.getCodigopostal2());
		justiciableItem.setCorreoElectronico(personajg.getCorreoelectronico());
		justiciableItem.setDescPaisDir1(personajg.getDescpaisdir1());
		justiciableItem.setDescPaisDir2(personajg.getDescpaisdir2());
		justiciableItem.setDireccion(personajg.getDireccion());
		justiciableItem.setDireccion2(personajg.getDireccion2());

		justiciableItem.setEdad(personajg.getEdad());

		justiciableItem.setEscaleraDir(personajg.getEscaleradir());
		justiciableItem.setEscaleraDir2(personajg.getEscaleradir2());
		justiciableItem.setExisteDomicilio(personajg.getExistedomicilio());
		justiciableItem.setFax(personajg.getFax());
		justiciableItem.setFechaModificacion(new Date());
		justiciableItem.setFechaNacimiento(personajg.getFechanacimiento());

		justiciableItem.setIdEstadoCivil(personajg.getIdestadocivil());

		if (personajg.getIdinstitucion() != null) {
			justiciableItem.setIdInstitucion(String.valueOf(personajg.getIdinstitucion()));
		}

		justiciableItem.setIdLenguaje(personajg.getIdlenguaje());

		justiciableItem.setIdMinusvalia(personajg.getIdminusvalia());

		justiciableItem.setIdPais(personajg.getIdpais());

		// justiciableItem.setIdpaisDir1(personajg.getIdpaisdir1());
		// justiciableItem.setIdpaisDir2(personajg.getIdpaisdir2());

		if (personajg.getIdpersona() != null) {
			justiciableItem.setIdPersona(String.valueOf(personajg.getIdpersona()));
		}

		justiciableItem.setIdPoblacion(personajg.getIdpoblacion());
		justiciableItem.setIdPoblacion2(personajg.getIdpoblacion2());

		justiciableItem.setIdProfesion(personajg.getIdprofesion());

		justiciableItem.setIdProvincia(personajg.getIdprovincia());
		justiciableItem.setIdProvincia2(personajg.getIdprovincia2());

		justiciableItem.setIdRepresentantejg(personajg.getIdrepresentantejg());

		justiciableItem.setIdTipodir(personajg.getIdtipodir());
		justiciableItem.setIdTipodir2(personajg.getIdtipodir2());

		justiciableItem.setIdTipoencalidad(personajg.getIdtipoencalidad());

		justiciableItem.setIdTipoIdentificacion(personajg.getIdtipoidentificacion());

		justiciableItem.setIdTipoIdentificacionotros(personajg.getIdtipoidentificacionotros());
		justiciableItem.setIdTipoVia(personajg.getIdtipovia());
		justiciableItem.setIdTipoVia2(personajg.getIdtipovia2());
		justiciableItem.setNif(personajg.getNif());
		justiciableItem.setNombre(personajg.getNombre());
		justiciableItem.setNumeroDir(personajg.getNumerodir());
		justiciableItem.setNumeroDir2(personajg.getNumerodir2());

		justiciableItem.setNumeroHijos(personajg.getNumerohijos());

		justiciableItem.setObservaciones(personajg.getObservaciones());
		justiciableItem.setPisoDir(personajg.getPisodir());
		justiciableItem.setPisoDir2(personajg.getPisodir2());
		justiciableItem.setPuertaDir(personajg.getPuertadir());
		justiciableItem.setPuertaDir2(personajg.getPuertadir2());
		justiciableItem.setRegimen_conyugal(personajg.getRegimenConyugal());
		justiciableItem.setSexo(personajg.getSexo());
		justiciableItem.setTipoPersonajg(personajg.getTipopersonajg());

		justiciableItem.setFechaAlta(personajg.getFechaalta());

		return justiciableItem;
	}

	private JusticiableTelefonoItem fillJusticiableTelefonosItemOfScsTelefonospersona(
			ScsTelefonospersona scsTelefonospersona) {

		JusticiableTelefonoItem justiciableTelefonoItem = new JusticiableTelefonoItem();

		if (scsTelefonospersona.getIdinstitucion() != null) {
			justiciableTelefonoItem.setIdInstitucion(String.valueOf(scsTelefonospersona.getIdinstitucion()));
		}

		if (scsTelefonospersona.getIdpersona() != null) {
			justiciableTelefonoItem.setIdPersona(String.valueOf(scsTelefonospersona.getIdpersona()));
		}

		justiciableTelefonoItem.setFechaModificacion(new Date());

		if (scsTelefonospersona.getIdtelefono() != null) {
			justiciableTelefonoItem.setIdTelefono(scsTelefonospersona.getIdtelefono().toString());
		}

		justiciableTelefonoItem.setNombreTelefono(scsTelefonospersona.getNombretelefono());
		justiciableTelefonoItem.setNumeroTelefono(scsTelefonospersona.getNumerotelefono());

		if (scsTelefonospersona.getPreferentesms() != null) {
			justiciableTelefonoItem.setPreferenteSms(scsTelefonospersona.getPreferentesms().toString());
		}

		return justiciableTelefonoItem;
	}

	@Override
	public ComboDTO getPoblacion(String idPoblacion, HttpServletRequest request) {

		ComboDTO poblacionesReturn = new ComboDTO();
		List<CenPoblaciones> poblaciones = new ArrayList<CenPoblaciones>();

		CenPoblacionesExample cenPoblacionesExample = new CenPoblacionesExample();
		cenPoblacionesExample.createCriteria().andIdpoblacionEqualTo(idPoblacion);

		poblaciones = cenPoblacionesExtendsMapper.selectByExample(cenPoblacionesExample);

		if (null != poblaciones && poblaciones.size() > 0) {

			List<ComboItem> combooItems = new ArrayList<ComboItem>();
			ComboItem comboItem = new ComboItem();

			for (CenPoblaciones cenPoblaciones : poblaciones) {
				comboItem = new ComboItem();
				comboItem.setLabel(cenPoblaciones.getNombre());
				comboItem.setValue(cenPoblaciones.getIdpoblacion());
				combooItems.add(comboItem);
			}

			poblacionesReturn.setCombooItems(combooItems);
		}

		return poblacionesReturn;
	}

	@Override
	public InsertResponseDTO createJusticiable(JusticiableItem justiciableItem, HttpServletRequest request) {
		LOGGER.info("createJusticiable() ->  Entrada al servicio para crear un nuevo justiciable");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idPersona = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {

					List<ScsPersonajg> personasList = new ArrayList<>();
					
					if(justiciableItem.getNif() == null) {
						justiciableItem.setNif("");

					}else {
						ScsPersonajgExample example = new ScsPersonajgExample();
						example.createCriteria().andNifEqualTo(justiciableItem.getNif())
						.andIdinstitucionEqualTo(idInstitucion);
						example.setOrderByClause("FECHAMODIFICACION DESC");
	
						LOGGER.info(
								"createJusticiable() / scsPersonajgExtendsMapper.selectByExample() -> Entrada a scsPersonajgExtendsMapper para comprobar que no este registrado el nif del nuevo justiciable");
	
						personasList = scsPersonajgExtendsMapper.selectByExample(example);
	
						LOGGER.info(
								"createJusticiable() / scsTelefonospersonaMapper.selectByExample() -> Salida de scsPersonajgExtendsMapper para comprobar que no este registrado el nif del nuevo justiciable");
					}
					if (null != personasList && personasList.size() > 0 && !justiciableItem.isValidacionRepeticion()) {

						ScsPersonajg persona = personasList.get(0);
						error.setCode(200);
						error.setMessage("C");
						insertResponseDTO.setId(persona.getIdpersona().toString());
						insertResponseDTO.setError(error);

					} else {
						ScsPersonajg scsPersonajg = new ScsPersonajg();
						ScsPersonajg justiciable = fillScsPersonasjsOfJusticiableItem(scsPersonajg, null, idInstitucion,
								justiciableItem);

						justiciable.setIdinstitucion(idInstitucion);
						justiciable.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"createJusticiable() / scsPersonajgExtendsMapper.getIdPersonajg() -> Entrada a scsPersonajgExtendsMapper para obtener idPersonajg");

						NewIdDTO idP = scsPersonajgExtendsMapper.getIdPersonajg(idInstitucion);

						LOGGER.info(
								"createJusticiable() / scsTelefonospersonaMapper.getIdPersonajg() -> Salida de scsPersonajgExtendsMapper para obtener idPersonajg");

						if (idP == null) {
							justiciable.setIdpersona((long) 1);
							idPersona = String.valueOf("1");
						} else {
							long idPersonajg = (long) (Integer.parseInt(idP.getNewId()) + 1);
							idPersona = String.valueOf(idPersonajg);
							justiciable.setIdpersona(idPersonajg);
						}

						LOGGER.info(
								"createJusticiable() / scsPersonajgExtendsMapper.insert() -> Entrada de scsPersonajgExtendsMapper para insertar el nuevo justiciable");

						response = scsPersonajgExtendsMapper.insert(justiciable);

						LOGGER.info(
								"createJusticiable() / scsPersonajgExtendsMapper.insert() -> Salida de scsPersonajgExtendsMapper para insertar el nuevo justiciable");

						long count = 1;

						if (justiciableItem.getTelefonos() != null && justiciableItem.getTelefonos().size() > 0) {

							for (JusticiableTelefonoItem telefono : justiciableItem.getTelefonos()) {

								ScsTelefonospersona scsTelefonospersona = new ScsTelefonospersona();

								scsTelefonospersona.setIdtelefono(count);
								scsTelefonospersona.setIdpersona(justiciable.getIdpersona());
								scsTelefonospersona.setIdinstitucion(idInstitucion);
								scsTelefonospersona.setNombretelefono(telefono.getNombreTelefono());
								scsTelefonospersona.setNumerotelefono(telefono.getNumeroTelefono());

								if (telefono.getPreferenteSms() != null && telefono.getPreferenteSms() != "") {
									scsTelefonospersona.setPreferentesms(Short.valueOf(telefono.getPreferenteSms()));
								}

								scsTelefonospersona.setFechamodificacion(new Date());
								scsTelefonospersona.setUsumodificacion(usuario.getIdusuario());

								LOGGER.info(
										"updateJusticiable() / scsTelefonospersonaMapper.insert() -> Entrada a scsTelefonospersonaMapper para insertar los telefonos del justiciable");

								response = scsTelefonosPersonaExtendsMapper.insert(scsTelefonospersona);

								LOGGER.info(
										"updateJusticiable() / scsTelefonospersonaMapper.insert() -> Salida de scsTelefonospersonaMapper para insertar los telefonos del justiciable");

								count += 1;
							}
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 1) {
			error.setCode(200);
			insertResponseDTO.setId(idPersona);
			insertResponseDTO.setStatus(SigaConstants.OK);

		}

		insertResponseDTO.setError(error);

		LOGGER.info("createJusticiable() -> Salida del servicio para crear un nuevo justiciable");

		return insertResponseDTO;
	}

	private ScsPersonajg fillScsPersonasjsOfJusticiableItem(ScsPersonajg scsPersonajg, Boolean datosGenerales,
			Short idInstitucion, JusticiableItem justiciableItem) {

		if (datosGenerales == null || datosGenerales) {

			scsPersonajg.setApellido1(justiciableItem.getApellido1());
			scsPersonajg.setApellido2(justiciableItem.getApellido2());
			scsPersonajg.setCnae(justiciableItem.getCnae());
			scsPersonajg.setCodigopostal(justiciableItem.getCodigoPostal());
			scsPersonajg.setCodigopostal2(justiciableItem.getCodigoPostal2());
			scsPersonajg.setCorreoelectronico(justiciableItem.getCorreoElectronico());
			scsPersonajg.setDescpaisdir1(justiciableItem.getDescPaisDir1());
			scsPersonajg.setDescpaisdir2(justiciableItem.getDescPaisDir2());
			scsPersonajg.setDireccion(justiciableItem.getDireccion());
			scsPersonajg.setDireccion2(justiciableItem.getDireccion2());

			scsPersonajg.setEdad(justiciableItem.getEdad());

			scsPersonajg.setEscaleradir(justiciableItem.getEscaleraDir());
			scsPersonajg.setEscaleradir2(justiciableItem.getEscaleraDir2());
			scsPersonajg.setExistedomicilio(justiciableItem.getExisteDomicilio());
			scsPersonajg.setFax(justiciableItem.getFax());
			scsPersonajg.setFechanacimiento(justiciableItem.getFechaNacimiento());

			scsPersonajg.setIdestadocivil(justiciableItem.getIdEstadoCivil());

			scsPersonajg.setIdinstitucion(idInstitucion);

			scsPersonajg.setIdlenguaje(justiciableItem.getIdLenguaje());

			scsPersonajg.setIdminusvalia(justiciableItem.getIdMinusvalia());

			scsPersonajg.setIdpais(justiciableItem.getIdPais());

			// scsPersonajg.setIdpaisdir1(justiciableItem.getIdpaisDir1());
			// scsPersonajg.setIdpaisdir2(justiciableItem.getIdpaisDir2());

			// if (justiciableItem.getIdPersona() != null && justiciableItem.getIdPersona()
			// != "") {
			// scsPersonajg.setIdpersona(Long.valueOf(justiciableItem.getIdPersona()));
			// }

			scsPersonajg.setIdpoblacion(justiciableItem.getIdPoblacion());
			scsPersonajg.setIdpoblacion2(justiciableItem.getIdPoblacion2());

			scsPersonajg.setIdprofesion(justiciableItem.getIdProfesion());

			scsPersonajg.setIdprovincia(justiciableItem.getIdProvincia());
			scsPersonajg.setIdprovincia2(justiciableItem.getIdProvincia2());

			if (justiciableItem.getAsociarRepresentante() != null && justiciableItem.getAsociarRepresentante()) {
				scsPersonajg.setIdrepresentantejg(justiciableItem.getIdRepresentantejg());
			}

			scsPersonajg.setIdtipodir(justiciableItem.getIdTipodir());
			scsPersonajg.setIdtipodir2(justiciableItem.getIdTipodir2());

			scsPersonajg.setIdtipoencalidad(justiciableItem.getIdTipoencalidad());

			scsPersonajg.setIdtipoidentificacion(justiciableItem.getIdTipoIdentificacion());

			scsPersonajg.setIdtipoidentificacionotros(justiciableItem.getIdTipoIdentificacionotros());
			scsPersonajg.setIdtipovia(justiciableItem.getIdTipoVia());
			scsPersonajg.setIdtipovia2(justiciableItem.getIdTipoVia2());
			
			if(justiciableItem.getNif() == null) {
				scsPersonajg.setNif("");
			}else {
				scsPersonajg.setNif(justiciableItem.getNif().toUpperCase());
			}
			scsPersonajg.setNombre(justiciableItem.getNombre());
			scsPersonajg.setNumerodir(justiciableItem.getNumeroDir());
			scsPersonajg.setNumerodir2(justiciableItem.getNumeroDir2());

			scsPersonajg.setNumerohijos(justiciableItem.getNumeroHijos());

			scsPersonajg.setObservaciones(justiciableItem.getObservaciones());
			scsPersonajg.setPisodir(justiciableItem.getPisoDir());
			scsPersonajg.setPisodir2(justiciableItem.getPisoDir2());
			scsPersonajg.setPuertadir(justiciableItem.getPuertaDir());
			scsPersonajg.setPuertadir2(justiciableItem.getPuertaDir2());
			scsPersonajg.setRegimenConyugal(justiciableItem.getRegimen_conyugal());
			scsPersonajg.setSexo(justiciableItem.getSexo());
			scsPersonajg.setTipopersonajg(justiciableItem.getTipoPersonajg());

			scsPersonajg.setFechaalta(new Date());

		}

		if (datosGenerales == null || !datosGenerales) {
			scsPersonajg.setAsistidoautorizaeejg(justiciableItem.getAsistidoAutorizaeejg());
			scsPersonajg.setAsistidosolicitajg(justiciableItem.getAsistidoSolicitajg());
			scsPersonajg.setAutorizaavisotelematico(justiciableItem.getAutorizaAvisoTelematico());
		}

		scsPersonajg.setFechamodificacion(new Date());

		return scsPersonajg;
	}

	@Override
	@Transactional
	public UpdateResponseDTO updateJusticiable(JusticiableItem justiciableItem, boolean datosGenerales,
			HttpServletRequest request) {
		LOGGER.info("updateJusticiable() ->  Entrada al servicio para modificar un justiciable");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idPersona = null;
		String validacion = null;
		List<String> roles = new ArrayList<String>();

		inicializarValidaciones();

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {

					if (datosGenerales) {
						AsuntosJusticiableDTO asuntosJusticiableDTO = searchAsuntosJusticiable(
								justiciableItem.getIdPersona(), request);
						List<AsuntosJusticiableItem> asuntosList = asuntosJusticiableDTO.getAsuntosJusticiableItems();

						for (AsuntosJusticiableItem asunto : asuntosList) {
							String rol = new String();
							rol = asunto.getRol().substring(0, 1) + asunto.getAsunto().substring(0, 1);
							roles.add(rol);
						}

						List<String> rolesDistinct = roles.stream().distinct().collect(Collectors.toList());

						for (String rol : rolesDistinct) {

							String rolJusticiable = obtenerTipoRolJusticiable(rol);

							validacion = validacionDatosSegunTipoPcajg(rolJusticiable, idInstitucion, justiciableItem);

						}
					}
					if (validacionCodigoPostal || validacionDireccion || validacionEstadoCivil
							|| validacionFechaNacimiento || validacionPais || validacionParentesco
							|| validacionPoblacion || validacionProvincia || validacionRegimenConyugal || validacionSexo
							|| validacionTipoVia
//							false
					) {
						error.setCode(600);

						String camposErroneos = "";

						if (validacionEstadoCivil)
							camposErroneos += ", Estado Civil ";
						if (validacionDireccion)
							camposErroneos += ", Direccion";
						if (validacionTipoVia)
							camposErroneos += ", Tipo de Vía";
						if (validacionCodigoPostal)
							camposErroneos += ", Código Postal ";
						if (validacionProvincia)
							camposErroneos += ", Provincia";
						if (validacionPoblacion)
							camposErroneos += ", Poblacion";
						if (validacionFechaNacimiento)
							camposErroneos += ", Fecha de Nacimiento";
						if (validacionPais)
							camposErroneos += ", Nacionalidad";
						if (validacionParentesco)
							camposErroneos += ", Parentesco";
						if (validacionRegimenConyugal)
							camposErroneos += ", Régimen Conyugal";
						if (validacionSexo)
							camposErroneos += ", Sexo";

						camposErroneos = camposErroneos.substring(1, camposErroneos.length());
						validacion = "Debe informar los siguientes campos para poder guardar el justiciable: "
								+ camposErroneos;

						error.setDescription(validacion);
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);

						return updateResponseDTO;

					} else {
//						ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
//						scsPersonajgExample.createCriteria()
//								.andIdpersonaEqualTo(Long.valueOf(justiciableItem.getIdPersona()))
//								.andIdinstitucionEqualTo(idInstitucion);
//
//						LOGGER.info(
//								"updateJusticiable() / scsPersonajgExtendsMapper.selectByExample() -> Entrada a scsPersonajgExtendsMapper para buscar al justiciable a editar");
//
//						List<ScsPersonajg> personaList = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);
//
//						LOGGER.info(
//								"updateJusticiable() / scsPersonajgExtendsMapper.selectByExample() -> Salida de scsPersonajgExtendsMapper para buscar al justiciable a editar");
//
//						ScsPersonajg scsPersonajg = personaList.get(0);

						ScsPersonajgKey personajgKey = new ScsPersonajgKey();

						personajgKey.setIdinstitucion(idInstitucion);
						personajgKey.setIdpersona(Long.valueOf(justiciableItem.getIdPersona()));

						LOGGER.info(
								"updateJusticiable() / scsPersonajgMapper.selectByPrimaryKey() -> Entrada a scsPersonajgMapper para buscar al justiciable a editar");

						ScsPersonajg scsPersonajg = scsPersonajgMapper.selectByPrimaryKey(personajgKey);

						LOGGER.info(
								"updateJusticiable() / scsPersonajgMapper.selectByPrimaryKey() -> Salida de scsPersonajgMapper para buscar al justiciable a editar");

						ScsPersonajg justiciable = fillScsPersonasjsOfJusticiableItem(scsPersonajg, datosGenerales,
								idInstitucion, justiciableItem);

						justiciable.setUsumodificacion(usuario.getIdusuario());
						justiciable.setFechamodificacion(new Date());

						LOGGER.info(
								"updateJusticiable() / scsPersonajgExtendsMapper.updateByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para modificar al justiciable");

						response = scsPersonajgExtendsMapper.updateByPrimaryKey(justiciable);

						LOGGER.info(
								"updateJusticiable() / scsPersonajgExtendsMapper.updateByPrimaryKey() -> Salida de scsPersonajgExtendsMapper para modificar al justiciable");

						ScsTelefonospersonaExample example = new ScsTelefonospersonaExample();
						example.createCriteria().andIdpersonaEqualTo(Long.valueOf(justiciableItem.getIdPersona()))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updateJusticiable() / scsTelefonosPersonaExtendsMapper.selectByExample() -> Entrada a scsTelefonosPersonaExtendsMapper para buscar telefonos al justiciable");

						List<ScsTelefonospersona> telefonosList = scsTelefonosPersonaExtendsMapper
								.selectByExample(example);

						LOGGER.info(
								"updateJusticiable() / scsTelefonosPersonaExtendsMapper.selectByExample() -> Entrada a scsTelefonosPersonaExtendsMapper para buscar telefonos al justiciable");

						// Eliminamos todos los telefonos guardado en bbdd
						if (telefonosList != null && telefonosList.size() > 0) {

							for (ScsTelefonospersona telefono : telefonosList) {

								ScsTelefonospersonaKey keyTel = new ScsTelefonospersonaKey();
								keyTel.setIdinstitucion(idInstitucion);
								keyTel.setIdpersona(telefono.getIdpersona());
								keyTel.setIdtelefono(telefono.getIdtelefono());

								LOGGER.info(
										"updateJusticiable() / scsTelefonosPersonaExtendsMapper.deleteByExample() -> Entrada a scsTelefonosPersonaExtendsMapper para eliminar telefonos al justiciable");

								response = scsTelefonosPersonaExtendsMapper.deleteByPrimaryKey(keyTel);

								LOGGER.info(
										"updateJusticiable() / scsTelefonosPersonaExtendsMapper.deleteByExample() -> Entrada a scsTelefonosPersonaExtendsMapper para eliminar telefonos al justiciable");

							}

						}

						if (justiciableItem.getTelefonos() != null && justiciableItem.getTelefonos().size() > 0
								&& datosGenerales) {

							// Añadimos los telefonos nuevos
							for (JusticiableTelefonoItem telefono : justiciableItem.getTelefonos()) {

								ScsTelefonospersona scsTelefonospersona = new ScsTelefonospersona();

								LOGGER.info(
										"updateJusticiable() / scsTelefonospersonaMapper.getIdTelefono() -> Entrada a scsTelefonospersonaMapper para obtener idTelefono a crear");

								NewIdDTO idT = scsTelefonosPersonaExtendsMapper.getIdTelefono(justiciableItem,
										idInstitucion);

								if (idT == null) {
									scsTelefonospersona.setIdtelefono((long) 1);
								} else {
									long idTelefono = (long) (Integer.parseInt(idT.getNewId()) + 1);
									scsTelefonospersona.setIdtelefono(idTelefono);
								}

								LOGGER.info(
										"updateJusticiable() / scsTelefonospersonaMapper.getIdTelefono() -> Salida de scsTelefonospersonaMapper para obtener idTelefono a crear");

								scsTelefonospersona.setIdpersona(Long.valueOf(justiciableItem.getIdPersona()));
								scsTelefonospersona.setIdinstitucion(idInstitucion);
								scsTelefonospersona.setNombretelefono(telefono.getNombreTelefono());
								scsTelefonospersona.setNumerotelefono(telefono.getNumeroTelefono());

								if (telefono.getPreferenteSms() != null && telefono.getPreferenteSms() != "") {
									scsTelefonospersona.setPreferentesms(Short.valueOf(telefono.getPreferenteSms()));
								}

								scsTelefonospersona.setFechamodificacion(new Date());
								scsTelefonospersona.setUsumodificacion(usuario.getIdusuario());

								LOGGER.info(
										"updateJusticiable() / scsTelefonospersonaMapper.updateByPrimaryKey() -> Entrada a scsTelefonospersonaMapper para modificar los telefonos del justiciable");

								response = scsTelefonosPersonaExtendsMapper.insert(scsTelefonospersona);

								LOGGER.info(
										"updateJusticiable() / scsTelefonospersonaMapper.updateByPrimaryKey() -> Salida de scsTelefonospersonaMapper para modificar los telefonos del justiciable");

							}

						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			updateResponseDTO.setId(idPersona);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateJusticiable() -> Salida del servicio para modificar un justiciable");

		return updateResponseDTO;
	}

	private void inicializarValidaciones() {
		validacionCodigoPostal = false;
		validacionDireccion = false;
		validacionEstadoCivil = false;
		validacionFechaNacimiento = false;
		validacionPais = false;
		validacionParentesco = false;
		validacionPoblacion = false;
		validacionProvincia = false;
		validacionRegimenConyugal = false;
		validacionSexo = false;
		validacionTipoVia = false;

	}

	@Override
	public JusticiableTelefonoDTO getTelefonos(JusticiableItem justiciableItem, HttpServletRequest request) {

		LOGGER.info("getTelefonos() -> Entrada al servicio para obtener telefonos de justiciables");

		JusticiableTelefonoDTO justiciableTelefonoDTO = new JusticiableTelefonoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getTelefonos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getTelefonos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				ScsTelefonospersonaExample scsTelefonospersonaExample = new ScsTelefonospersonaExample();
				scsTelefonospersonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdpersonaEqualTo(Long.valueOf(justiciableItem.getIdPersona()));

				scsTelefonospersonaExample.setOrderByClause("NOMBRETELEFONO asc");

				LOGGER.info(
						"getTelefonos() / scsTelefonosPersonaExtendsMapper.selectByExample() -> Entrada a scsTelefonosPersonaExtendsMapper para obtener los telefonos de un justiciable");

				List<ScsTelefonospersona> telefonospersonaList = scsTelefonosPersonaExtendsMapper
						.selectByExample(scsTelefonospersonaExample);

				LOGGER.info(
						"getTelefonos() / scsTelefonosPersonaExtendsMapper.selectByExample() -> Salida de scsTelefonosPersonaExtendsMapper para obtener los telefonos de un justiciable");

				if (telefonospersonaList != null && telefonospersonaList.size() > 0) {

					List<JusticiableTelefonoItem> telefonosList = new ArrayList<JusticiableTelefonoItem>();

					for (ScsTelefonospersona tel : telefonospersonaList) {

						JusticiableTelefonoItem telefono = new JusticiableTelefonoItem();
						telefono = fillJusticiableTelefonosItemOfScsTelefonospersona(tel);
						telefonosList.add(telefono);
					}

					justiciableTelefonoDTO.setTelefonosJusticiables(telefonosList);
				}
			}
		}

		LOGGER.info("getTelefonos() -> Salida del servicio para obtener telefonos de justiciables");

		return justiciableTelefonoDTO;
	}

	@Override
	public ComboDTO getTipoVias(HttpServletRequest request) {
		LOGGER.info("getTipoVias() -> Entrada al servicio para obtener combo tipo vias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getTipoVias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getTipoVias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getTipoVias() / cenTipoviaExtendsMapper.getJurisdicciones() -> Entrada a cenTipoviaExtendsMapper para obtener minusvalias");

				List<ComboItem> comboItems = cenTipoviaExtendsMapper.getTipoVias(idInstitucion,
						usuario.getIdlenguaje());

				LOGGER.info(
						"getTipoVias() / cenTipoviaExtendsMapper.getJurisdicciones() -> Salida a cenTipoviaExtendsMapper para obtener minusvalias");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getTipoVias() -> Salida del servicio para obtener combo tipo vias");
		return combo;
	}

	@Override
	public AsuntosJusticiableDTO searchAsuntosJusticiable(String idPersona, HttpServletRequest request) {

		LOGGER.info("searchAsuntosJusticiable() -> Entrada al servicio para obtener los asuntos de un justiciable");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosClaveJusticiableItem> asuntosClaveJusticiableItem = new ArrayList<AsuntosClaveJusticiableItem>();
		List<StringDTO> personasRepresentadas = null;
		Error error = new Error();

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchAsuntosJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchAsuntosJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"searchAsuntosJusticiable() / scsPersonajgExtendsMapper.searchClaveAsuntosJusticiable() -> Entrada a scsPersonajgExtendsMapper para obtener las claves de los asuntos");

					// Comprobamos si es representante legal de algún asunto en cuyo caso devolveremos las personas representadas
					personasRepresentadas = scsPersonajgExtendsMapper.getPersonaRepresentanteJG(idPersona, idInstitucion);
					
					if (personasRepresentadas != null && personasRepresentadas.size() > 0) {
						asuntosClaveJusticiableItem = scsPersonajgExtendsMapper.searchClaveAsuntosJusticiableRepresentanteJG(idPersona, personasRepresentadas,
								idInstitucion);
					} else {
						asuntosClaveJusticiableItem = scsPersonajgExtendsMapper.searchClaveAsuntosJusticiable(idPersona,
								idInstitucion);
					}

					LOGGER.info(
							"searchAsuntosJusticiable() / scsPersonajgExtendsMapper.searchClaveAsuntosJusticiable() -> Salida a scsPersonajgExtendsMapper para obtener las claves de los asuntos");

					if (asuntosClaveJusticiableItem != null && asuntosClaveJusticiableItem.size() > 0) {

						asuntosJusticiableDTO = searchAsuntosConClave(asuntosClaveJusticiableItem, false, request);
					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				}
			}
		}
		LOGGER.info("searchAsuntosJusticiable() -> Salida del servicio para obtener los asuntos de un justiciable");
		return asuntosJusticiableDTO;
	}

	@Override
	public AsuntosJusticiableDTO searchAsuntosConClave(List<AsuntosClaveJusticiableItem> asuntosClaveList,
			boolean fromJusticiable, HttpServletRequest request) {

		LOGGER.info(
				"searchAsuntosConClave() -> Entrada al servicio para obtener los asuntos a partir de las claves de un justiciable");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItem = new ArrayList<AsuntosJusticiableItem>();
		Error error = new Error();

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchAsuntosConClave() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchAsuntosConClave() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {

					if (asuntosClaveList != null && asuntosClaveList.size() > 0) {

						for (AsuntosClaveJusticiableItem asuntoClave : asuntosClaveList) {
							AsuntosJusticiableItem asunto = null;

							switch (asuntoClave.getTipo()) {
								case SigaConstants.TIPO_ASUNTO_EJG:
									asunto = getAsuntoTipoEjg(asuntoClave, idInstitucion, fromJusticiable, usuario);
									break;
								case SigaConstants.TIPO_ASUNTO_DESIGNA:
									asunto = getAsuntoTipoDesigna(asuntoClave, idInstitucion, fromJusticiable, usuario);
									break;
								case SigaConstants.TIPO_ASUNTO_ASISTENCIA:
									asunto = getAsuntoTipoAsistencia(asuntoClave, idInstitucion, fromJusticiable, usuario);
									break;
								case SigaConstants.TIPO_ASUNTO_SOJ:
									asunto = getAsuntoTipoSOJ(asuntoClave, idInstitucion, fromJusticiable, usuario);
									break;
	
								default:
									break;
							}
							if(asuntoClave.getAnio() != null && !asuntoClave.getAnio().isEmpty()) {
								asunto.setAnio(asuntoClave.getAnio());
							}
							if(asuntoClave.getNumero() != null && !asuntoClave.getNumero().isEmpty()) {
								asunto.setNumero(asuntoClave.getNumero());
							}
							if(asuntoClave.getTipo() != null && !asuntoClave.getTipo().isEmpty()) {
								asunto.setTipo(asuntoClave.getTipo());
							}
							if(asuntoClave.getClave() != null && !asuntoClave.getClave().isEmpty()) {
								asunto.setClave(asuntoClave.getClave());
							}
							if (asunto != null) {
								asuntosJusticiableItem.add(asunto);
							}
						}

						if (asuntosJusticiableItem != null && asuntosJusticiableItem.size() > 0) {

							/*Collections.sort(asuntosJusticiableItem, new Comparator<Object>() {
								@Override
								public int compare(Object item1, Object item2) {
									// use instanceof to verify the references are indeed of the type in question
									return ((AsuntosJusticiableItem) item1).getFecha()
											.compareTo(((AsuntosJusticiableItem) item2).getFecha());
								}
							});

							Collections.reverse(asuntosJusticiableItem);*/

							asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItem);
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
				}

			}

		}
		LOGGER.info(
				"searchAsuntosConClave() -> Salida del servicio para obtener los asuntos a partir de las claves de un justiciable");
		return asuntosJusticiableDTO;
	}

	private AsuntosJusticiableItem getAsuntoTipoEjg(AsuntosClaveJusticiableItem asuntoClave, short idInstitucion,
			boolean fromJusticiable, AdmUsuarios usuario) {

		LOGGER.info("getAsuntoTipoEjg() -> Entrada del servicio para obtener asunto EJG");

		AsuntosJusticiableItem asunto = new AsuntosJusticiableItem();
		AsuntosEjgItem asuntoEjg = new AsuntosEjgItem();

		LOGGER.info(
				"getAsuntoTipoEjg() / scsEjgExtendsMapper.getAsuntoTipoEjg() -> Entrada a scsEjgExtendsMapper para obtener el asunto tipo EJG");

		asuntoEjg = scsEjgExtendsMapper.getAsuntoTipoEjg(asuntoClave, usuario.getIdlenguaje());

		LOGGER.info(
				"getAsuntoTipoEjg() / scsEjgExtendsMapper.getAsuntoTipoEjg() -> Salida a scsEjgExtendsMapper para obtener el asunto tipo EJG");

		asunto.setAsunto(asuntoEjg.getAsunto());
		asunto.setFecha(asuntoEjg.getFecha());
		asunto.setIdInstitucion(asuntoEjg.getIdInstitucion());
		asunto.setTurnoGuardia(asuntoEjg.getTurnoGuardia());

		// Obtenemos colegiado

		FichaPersonaItem colegiado = null;

		if (asuntoEjg.getIdPersonaLetrado() != null) {

			LOGGER.info(
					"getAsuntoTipoEjg() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

			colegiado = getColegiadoByIdPersona(asuntoEjg.getIdPersonaLetrado(),
					Short.valueOf(asuntoClave.getIdInstitucion()));

			LOGGER.info(
					"getAsuntoTipoEjg() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		}

		if (colegiado != null) {
			asunto.setLetrado(colegiado.getApellido1() + " " + colegiado.getApellido2() + ", " + colegiado.getNombre());
			asunto.setnColegiado(colegiado.getNumeroColegiado());
		}

		// Rol
		if (fromJusticiable) {
			asunto.setRol(asuntoClave.getRol());

			// Interesado
		} else {

			if (asuntoEjg.getIdPersonajg() != null) {
				ScsPersonajgKey scsPersonajgKey = new ScsPersonajgKey();
				scsPersonajgKey.setIdpersona(Long.valueOf(asuntoEjg.getIdPersonajg()));
				scsPersonajgKey.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"getAsuntoTipoEjg() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

				ScsPersonajg interesado = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgKey);

				LOGGER.info(
						"getAsuntoTipoEjg() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

				if (interesado != null) {
					String nombre = "";

					if (interesado.getApellido1() != null) {
						nombre += interesado.getApellido1();
					}

					if (interesado.getApellido2() != null) {
						nombre += " " + interesado.getApellido2() + ",";
					}

					if (interesado.getApellido2() == null) {
						nombre += ", ";
					}

					if (interesado.getNombre() != null) {
						nombre += interesado.getNombre();
					}

					asunto.setInteresado(nombre);
				}
			}

		}

		// Datos Interes

		LOGGER.info(
				"getAsuntoTipoEjg() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		EstadoEjgItem estadoEjg = scsEstadoejgExtendsMapper.getEstadoEjg(asuntoClave, usuario.getIdlenguaje());

		LOGGER.info(
				"getAsuntoTipoEjg() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		String datosInteres = "";

		if (estadoEjg.getDescripcion() != null) {
			datosInteres += "<b>Estado</b>: " + estadoEjg.getDescripcion();
			datosInteres += "<br/>";
		}

		if (asuntoEjg.getExpedienteInsostenibilidad() != null) {
			datosInteres += "<b>Expediente de insostenibilidad</b>: " + asuntoEjg.getExpedienteInsostenibilidad();
			datosInteres += "<br/>";
		}

		if (estadoEjg.getIdEstadoejg() != null
				&& estadoEjg.getIdEstadoejg().equals(SigaConstants.TIPO_ESTADO_EJG_DICTAMINADO)) {

			if (asuntoEjg.getDictamen() != null) {
				datosInteres += "<b>Tipo Dictamen</b>: " + asuntoEjg.getDictamen();
				datosInteres += "<br/>";
			}

			if (asuntoEjg.getFundamentoCalificacion() != null) {
				datosInteres += "<b>Fundamento Calificación</b>: " + asuntoEjg.getFundamentoCalificacion();
				datosInteres += "<br/>";
			}

			if (asuntoEjg.getFecha() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(asuntoEjg.getFecha());
				datosInteres += "<b>Fecha</b>: " + fecha;
			}

		} else if (estadoEjg.getIdEstadoejg() != null
				&& estadoEjg.getIdEstadoejg().equals(SigaConstants.TIPO_ESTADO_EJG_RESUELTO_COMISION)) {

			if (asuntoEjg.getTipoResolucion() != null) {
				datosInteres += "<b>Resolución</b>: " + asuntoEjg.getTipoResolucion();
				datosInteres += "<br/>";
			}

			if (asuntoEjg.getTipoFundamento() != null) {
				datosInteres += "<b>Fundamento Jurídico</b>: " + asuntoEjg.getTipoFundamento();
				datosInteres += "<br/>";
			}

			if (asuntoEjg.getFecha() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(asuntoEjg.getFecha());
				datosInteres += "<b>Fecha</b>: " + fecha;
			}

		} else if (estadoEjg.getIdEstadoejg() != null
				&& estadoEjg.getIdEstadoejg().equals(SigaConstants.TIPO_ESTADO_EJG_RESUELTA_IMPUGNACION)) {

			if (asuntoEjg.getTipoResolucionAuto() != null) {
				datosInteres += "<b>Auto Resolutorio</b>: " + asuntoEjg.getTipoResolucionAuto();
				datosInteres += "<br/>";
			}

			if (asuntoEjg.getTipoSentidoAuto() != null) {
				datosInteres += "<b>Sentido Auto</b>: " + asuntoEjg.getTipoSentidoAuto();
				datosInteres += "<br/>";
			}

			if (asuntoEjg.getFecha() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(asuntoEjg.getFecha());
				datosInteres += "<b>Fecha</b>: " + fecha;
			}

		}

		asunto.setDatosInteres(datosInteres);

		LOGGER.info("getAsuntoTipoEjg() -> Salida del servicio para obtener asunto EJG");

		return asunto;
	}

	private FichaPersonaItem getColegiadoByIdPersona(String idPersona, short idInstitucion) {

		FichaPersonaItem colegiado = null;

		LOGGER.info(
				"getColegiadoByIdPersona() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		colegiado = cenPersonaExtendsMapper.getColegiadoByIdPersona(idPersona, idInstitucion);

		LOGGER.info(
				"getColegiadoByIdPersona() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		return colegiado;
	}

	private AsuntosJusticiableItem getAsuntoTipoDesigna(AsuntosClaveJusticiableItem asuntoClave, short idInstitucion,
			boolean fromJusticiable, AdmUsuarios usuario) {

		LOGGER.info("getAsuntoTipoDesigna() -> Entrada del servicio para obtener asunto designa");

		AsuntosJusticiableItem asunto = new AsuntosJusticiableItem();
		AsuntosDesignaItem asuntoDesigna = new AsuntosDesignaItem();

		LOGGER.info(
				"getAsuntoTipoDesigna() / scsDesignaExtendsMapper.getAsuntoTipoDesigna() -> Entrada a scsDesignaExtendsMapper para obtener el asunto tipo Designa");

		asuntoDesigna = scsDesignacionesExtendsMapper.getAsuntoTipoDesigna(asuntoClave, usuario.getIdlenguaje());

		LOGGER.info(
				"getAsuntoTipoDesigna() / scsDesignaExtendsMapper.getAsuntoTipoDesigna() -> Salida a scsDesignaExtendsMapper para obtener el asunto tipo Designa");

		asunto.setAsunto(asuntoDesigna.getAsunto());
		asunto.setFecha(asuntoDesigna.getFecha());
		asunto.setIdInstitucion(asuntoDesigna.getIdInstitucion());
		asunto.setTurnoGuardia(asuntoDesigna.getTurnoGuardia());
		asunto.setDatosInteres(asuntoDesigna.getDatosInteres());
		asunto.setNumero(asuntoDesigna.getNumero());
		asunto.setCodigo(asuntoDesigna.getCodigo());
		

		// Obtenemos colegiado

		FichaPersonaItem colegiado = null;

		LOGGER.info(
				"getAsuntoTipoDesigna() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		colegiado = getDesignaLetrado(asuntoClave, usuario, idInstitucion);

		LOGGER.info(
				"getAsuntoTipoDesigna() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

		if (colegiado != null) {
			asunto.setLetrado(colegiado.getColegiado().replace(colegiado.getNumeroColegiado(), ""));
			asunto.setnColegiado(colegiado.getNumeroColegiado());
		}

		// Rol
		if (fromJusticiable) {
			asunto.setRol(asuntoClave.getRol());

			// Interesado
		} else {

			LOGGER.info(
					"getAsuntoTipoDesigna() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

			StringDTO interesado = scsDefendidosdesignasExtendsMapper.getInteresadosDesigna(asuntoClave,
					usuario.getIdlenguaje());

			LOGGER.info(
					"getAsuntoTipoDesigna() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

			asunto.setInteresado(interesado.getValor());

		}

		LOGGER.info("getAsuntoTipoDesigna() -> Salida del servicio para obtener asunto designa");

		return asunto;
	}

	private FichaPersonaItem getDesignaLetrado(AsuntosClaveJusticiableItem asuntoClave, AdmUsuarios usuario,
			short idInstitucion) {

		LOGGER.info("getDesignaLetrado() -> Entrada del servicio para obtener colegiado del asunto designa");

		FichaPersonaItem designaLetrado = null;

		LOGGER.info(
				"getDesignaLetrado() / scsDesignaExtendsMapper.getAsuntoTipoDesigna() -> Entrada a scsDesignaExtendsMapper para obtener el asunto tipo Designa");

		designaLetrado = scsDesignasLetradoExtendsMapper.getDesignaLetrado(asuntoClave, usuario.getIdlenguaje());

		LOGGER.info(
				"getDesignaLetrado() / scsDesignaExtendsMapper.getAsuntoTipoDesigna() -> Salida a scsDesignaExtendsMapper para obtener el asunto tipo Designa");

		LOGGER.info("getDesignaLetrado() -> Salida del servicio para obtener colegiado del asunto designa");

		return designaLetrado;
	}

	private AsuntosJusticiableItem getAsuntoTipoAsistencia(AsuntosClaveJusticiableItem asuntoClave, short idInstitucion,
			boolean fromJusticiable, AdmUsuarios usuario) {

		LOGGER.info("getAsuntoTipoAsistencia() -> Entrada del servicio para obtener asunto asistencia");

		AsuntosJusticiableItem asunto = new AsuntosJusticiableItem();
		AsuntosAsistenciaItem asuntoAsistencia = new AsuntosAsistenciaItem();

		LOGGER.info(
				"getAsuntoTipoAsistencia() / scsAsistenciaExtendsMapper.getAsuntoTipoAsistencia() -> Entrada a scsAsistenciaExtendsMapper para obtener el asunto tipo Asistencia");

		asuntoAsistencia = scsAsistenciaExtendsMapper.getAsuntoTipoAsistencia(asuntoClave, usuario.getIdlenguaje());

		LOGGER.info(
				"getAsuntoTipoAsistencia() / scsAsistenciaExtendsMapper.getAsuntoTipoAsistencia() -> Salida a scsAsistenciaExtendsMapper para obtener el asunto tipo Asistencia");

		asunto.setAsunto(asuntoAsistencia.getAsunto());
		asunto.setFecha(asuntoAsistencia.getFecha());
		asunto.setIdInstitucion(asuntoAsistencia.getIdInstitucion());
		asunto.setTurnoGuardia(asuntoAsistencia.getTurnoGuardia());
		asunto.setDatosInteres(asuntoAsistencia.getDatosInteres());

		// Obtenemos colegiado

		FichaPersonaItem colegiado = null;

		if (asuntoAsistencia.getIdPersonaColegiado() != null) {

			colegiado = getColegiadoByIdPersona(asuntoAsistencia.getIdPersonaColegiado(),
					Short.valueOf(asuntoClave.getIdInstitucion()));

			if (colegiado != null) {
				asunto.setLetrado(colegiado.getApellido1() + " " + colegiado.getApellido2() + ", " + colegiado.getNombre());
				asunto.setnColegiado(colegiado.getNumeroColegiado());
			}

		}

		// Rol
		if (fromJusticiable) {
			asunto.setRol(asuntoClave.getRol());

			// Interesado
		} else {

			if (asuntoAsistencia.getIdPersonaAsistido() != null) {
				ScsPersonajgKey scsPersonajgKey = new ScsPersonajgKey();
				scsPersonajgKey.setIdpersona(Long.valueOf(asuntoAsistencia.getIdPersonaAsistido()));
				scsPersonajgKey.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"getAsuntoTipoAsistencia() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado del asunto");

				ScsPersonajg interesado = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgKey);

				LOGGER.info(
						"getAsuntoTipoAsistencia() / cenPersonaExtendsMapper.getColegiadoByIdPersona() -> Salida a cenPersonaExtendsMapper para obtener el colegiado del asunto");

				if (interesado != null) {
					String nombre = "";

					if (interesado.getApellido1() != null) {
						nombre += interesado.getApellido1();
					}

					if (interesado.getApellido2() != null) {
						nombre += " " + interesado.getApellido2() + ", ";
					}

					if (interesado.getApellido2() == null) {
						nombre += ", ";
					}

					if (interesado.getNombre() != null) {
						nombre += interesado.getNombre();
					}

					asunto.setInteresado(nombre);
				}
			}
		}

		LOGGER.info("getAsuntoTipoAsistencia() -> Salida del servicio para obtener asunto asistencia");

		return asunto;
	}

	private AsuntosJusticiableItem getAsuntoTipoSOJ(AsuntosClaveJusticiableItem asuntoClave, short idInstitucion,
			boolean fromJusticiable, AdmUsuarios usuario) {

		LOGGER.info("getAsuntoTipoSOJ() -> Entrada del servicio para obtener asunto SOJ");

		AsuntosJusticiableItem asunto = new AsuntosJusticiableItem();
		AsuntosSOJItem asuntosSOJ = new AsuntosSOJItem();

		LOGGER.info(
				"getAsuntoTipoSOJ() / scsSojExtendsMapper.getAsuntoTipoSoj() -> Entrada a scsSojExtendsMapper para obtener el asunto tipo SOJ");

		asuntosSOJ = scsSojExtendsMapper.getAsuntoTipoSoj(asuntoClave, usuario.getIdlenguaje());

		LOGGER.info(
				"getAsuntoTipoSOJ() / scsSojExtendsMapper.getAsuntoTipoSoj() -> Salida a scsSojExtendsMapper para obtener el asunto tipo SOJ");

		asunto.setAsunto(asuntosSOJ.getAsunto());
		asunto.setFecha(asuntosSOJ.getFecha());
		asunto.setIdInstitucion(asuntosSOJ.getIdInstitucion());
		asunto.setTurnoGuardia(asuntosSOJ.getTurnoGuardia());
		asunto.setDatosInteres(asuntosSOJ.getDatosInteres());

		// Obtenemos colegiado

		FichaPersonaItem colegiado = null;

		if (asuntosSOJ.getIdPersonaColegiado() != null) {

			colegiado = getColegiadoByIdPersona(asuntosSOJ.getIdPersonaColegiado(),
					Short.valueOf(asuntoClave.getIdInstitucion()));

			if (colegiado != null) {
				asunto.setLetrado(colegiado.getApellido1() + " " + colegiado.getApellido2() + ", " + colegiado.getNombre());
				asunto.setnColegiado(colegiado.getNumeroColegiado());
			}

		}

		// Rol
		if (fromJusticiable) {
			asunto.setRol(asuntoClave.getRol());

			// Interesado
		} else {

			if (asuntosSOJ.getIdPersonaSoj() != null) {
				ScsPersonajgKey scsPersonajgKey = new ScsPersonajgKey();
				scsPersonajgKey.setIdpersona(Long.valueOf(asuntosSOJ.getIdPersonaSoj()));
				scsPersonajgKey.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"getAsuntoTipoSOJ() / cenPersonaExtendsMapper.selectByPrimaryKey() -> Entrada a cenPersonaExtendsMapper para obtener el interesado del asunto");

				ScsPersonajg interesado = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgKey);

				LOGGER.info(
						"getAsuntoTipoSOJ() / cenPersonaExtendsMapper.selectByPrimaryKey() -> Salida a cenPersonaExtendsMapper para obtener el interesado del asunto");

				if (interesado != null) {
					String nombre = "";

					if (interesado.getApellido1() != null) {
						nombre += interesado.getApellido1();
					}

					if (interesado.getApellido2() != null) {
						nombre += " " + interesado.getApellido2() + ", ";
					}

					if (interesado.getApellido2() == null) {
						nombre += ", ";
					}

					if (interesado.getNombre() != null) {
						nombre += interesado.getNombre();
					}

					asunto.setInteresado(nombre);
				}
			}
		}

		LOGGER.info("getAsuntoTipoSOJ() -> Salida del servicio para obtener asunto SOJ");

		return asunto;
	}

	@Override
	public JusticiableDTO getJusticiableByNif(JusticiableBusquedaItem justiciableBusquedaItem,
			HttpServletRequest request) {

		LOGGER.info(
				"getJusticiableByNif() -> Entrada al servicio para obtener el representante del justiciable by nif");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		JusticiableDTO justiciableDTO = new JusticiableDTO();
		List<ScsPersonajg> personajgList = new ArrayList<ScsPersonajg>();
		Error error = new Error();

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"getJusticiableByNif() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getJusticiableByNif() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"getJusticiableByNif() / scsPersonajgExtendsMapper.selectByExample() -> Entrada a scsPersonajgExtendsMapper para obtener representante");
					ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();

					scsPersonajgExample.createCriteria().andNifEqualTo(justiciableBusquedaItem.getNif().toUpperCase())
							.andIdinstitucionEqualTo(idInstitucion);

					scsPersonajgExample.setOrderByClause("FECHAMODIFICACION DESC");

					personajgList = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);

					LOGGER.info(
							"getJusticiableByNif() / scsPersonajgExtendsMapper.selectByExample() -> Salida a scsPersonajgExtendsMapper para obtener representante");

					if (personajgList != null && personajgList.size() > 0) {

						ScsPersonajg personajg = personajgList.get(0);

						JusticiableItem justiciable = fillJusticiableItemOfScsPersonasjg(personajg);

						justiciableDTO.setJusticiable(justiciable);

					} else {

						JusticiableItem justiciable = new JusticiableItem();

						justiciable.setNif(justiciableBusquedaItem.getNif());

						justiciableDTO.setJusticiable(justiciable);

					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
				}

			}

		}
		LOGGER.info(
				"getJusticiableByNif() -> Salida del servicio para obtener el representante del justiciable by nif");
		return justiciableDTO;
	}

	@Override
	public UpdateResponseDTO associateRepresentante(JusticiableItem justiciableItem, HttpServletRequest request) {

		LOGGER.info("associateRepresentante() -> Entrada al servicio para asociar un representante del justiciable");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"associateRepresentante() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"associateRepresentante() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
					scsPersonajgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(justiciableItem.getIdPersona()));

					LOGGER.info(
							"associateRepresentante() / scsPersonajgExtendsMapper.selectByExample() -> Entrada a scsPersonajgExtendsMapper para obtener justiciable");

					List<ScsPersonajg> personajgList = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);

					LOGGER.info(
							"associateRepresentante() / scsPersonajgExtendsMapper.selectByExample() -> Salida a scsPersonajgExtendsMapper para obtener justiciable");

					if (personajgList != null && personajgList.size() > 0) {

						ScsPersonajg personajg = personajgList.get(0);

						personajg.setFechamodificacion(new Date());
						personajg.setUsumodificacion(usuario.getIdusuario());
						personajg.setIdrepresentantejg(Long.valueOf(justiciableItem.getIdRepresentantejg()));

						LOGGER.info(
								"associateRepresentante() / scsPersonajgExtendsMapper.updateByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para asociar representante a justiciable");

						response = scsPersonajgExtendsMapper.updateByPrimaryKey(personajg);

						LOGGER.info(
								"associateRepresentante() / scsPersonajgExtendsMapper.updateByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para asociar representante a justiciable");

					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				}

				if (response == 0) {
					error.setCode(400);
				} else {
					error.setCode(200);
				}

				updateResponseDTO.setError(error);
			}

		}
		LOGGER.info("associateRepresentante() -> Salida del servicio para asociar un representante del justiciable");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO disassociateRepresentante(JusticiableItem justiciableItem, HttpServletRequest request) {

		LOGGER.info(
				"disassociateRepresentante() -> Entrada al servicio para desasociar un representante del justiciable");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"disassociateRepresentante() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"disassociateRepresentante() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
					scsPersonajgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(justiciableItem.getIdPersona()));

					LOGGER.info(
							"disassociateRepresentante() / scsPersonajgExtendsMapper.selectByExample() -> Entrada a scsPersonajgExtendsMapper para obtener justiciable");

					List<ScsPersonajg> personajgList = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);

					LOGGER.info(
							"disassociateRepresentante() / scsPersonajgExtendsMapper.selectByExample() -> Salida a scsPersonajgExtendsMapper para obtener justiciable");

					if (personajgList != null && personajgList.size() > 0) {

						ScsPersonajg personajg = personajgList.get(0);

						personajg.setFechamodificacion(new Date());
						personajg.setUsumodificacion(usuario.getIdusuario());
						personajg.setIdrepresentantejg(null);

						LOGGER.info(
								"disassociateRepresentante() / scsPersonajgExtendsMapper.updateByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para asociar representante a justiciable");

						response = scsPersonajgExtendsMapper.updateByPrimaryKey(personajg);

						LOGGER.info(
								"disassociateRepresentante() / scsPersonajgExtendsMapper.updateByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para asociar representante a justiciable");

					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				}

				if (response == 0) {
					error.setCode(400);
				} else {
					error.setCode(200);
				}

				updateResponseDTO.setError(error);
			}

		}
		LOGGER.info(
				"disassociateRepresentante() -> Salida del servicio para desasociar un representante del justiciable");
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO asociarDesignacion(ScsDefendidosdesigna datosdesigna, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.asociarDesignacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.asociarDesignacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.asociarDesignacion() -> Entrada para asociar un Justiciable a una designacion");
				try {

					datosdesigna.setIdinstitucion(idInstitucion);
					List<ScsDefendidosdesigna> designas = scsDefendidosDesignaMapperExtends
							.seleccionarDesigna(datosdesigna);

					if (designas != null && designas.size() > 0) {
						ScsDefendidosdesigna interesadoDesigna = new ScsDefendidosdesigna();
						// IdInstitucion Defendidos Designa
						interesadoDesigna.setIdinstitucion(idInstitucion);
						// IdTurno Defendidos Designa
						if (datosdesigna.getIdturno() != null) {
							interesadoDesigna.setIdturno(datosdesigna.getIdturno());
						} else {
							interesadoDesigna.setIdturno(designas.get(0).getIdturno());
						}

						// Anio Defendidos Designa
						interesadoDesigna.setAnio(datosdesigna.getAnio());
						// Numero Defendidos Designa
						interesadoDesigna.setNumero(designas.get(0).getNumero());
						// Idpersona Defendidos Designa
						interesadoDesigna.setIdpersona(datosdesigna.getIdpersona());
						// Fecha Modificación es la fecha actual
						interesadoDesigna.setFechamodificacion(date);
						// Usuario Modificación
						interesadoDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
						// Calidad
						interesadoDesigna.setCalidad(String.valueOf('D')); // Demandante para que salga el Interesado en Designas.
						// Insertar la asociación Justiciable y Designas.
						response = scsDefendidosdesignaMapper.insertSelective(interesadoDesigna);
					}
				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {
						error.setCode(400);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarDesignacion() -> ERROR. No se ha asociado ningun elemento");
					} else {
						error.setCode(200);
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info(
								"GestionEJGServiceImpl.asociarDesignacion() -> Se ha asociado el elemento correctamente");
					}
				}
				
				updateResponseDTO.setError(error);
			}
		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO asociarEJG(List<String> itemEjg, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		int response = 0;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.asociarEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.asociarEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.asociarEJG() -> Entrada para asociar un Justiciable a un EJG");
				try {
					// Identificar el EJG
					ScsEjgExample paramejg = new ScsEjgExample();

					paramejg.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
							.andAnioEqualTo(Short.valueOf(itemEjg.get(0))).andNumeroEqualTo(Long.valueOf(itemEjg.get(1)));

					List<ScsEjgWithBLOBs> ejg = scsEjgMapper.selectByExampleWithBLOBs(paramejg);
					// Actualizar con Justiciable seleccionado.
					if (ejg != null && ejg.size() > 0) {
						ScsEjgWithBLOBs ejgItem = new ScsEjgWithBLOBs();
						ejgItem.setFechamodificacion(date); // Fecha Modificación
						ejgItem.setIdinstitucion(Short.valueOf(idInstitucion));
						// IdTurno EJG
						if (itemEjg.get(2) != null) {
							ejgItem.setIdtipoejg(Short.valueOf(itemEjg.get(2)));
						} else {
							ejgItem.setIdtipoejg(ejg.get(0).getIdtipoejg());
						}
						ejgItem.setIdtipoejg(ejg.get(0).getIdtipoejg());
						ejgItem.setAnio(ejg.get(0).getAnio());
						ejgItem.setNumero(ejg.get(0).getNumero());
						ejgItem.setIdpersonajg(Long.valueOf(itemEjg.get(3))); // Solicitante.
						response = scsEjgMapper.updateByPrimaryKeySelective(ejgItem);
					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				} finally {
					if (response != 1) {
						error.setCode(400);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarEJG() -> ERROR. No se ha asociado ningun elemento");
					} else {
						error.setCode(200);
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info(
								"GestionEJGServiceImpl.asociarEJG() -> Se ha asociado el elemento correctamente");
					}
				}
				
				updateResponseDTO.setError(error);
			}
		}

		return updateResponseDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO asociarAsistencia(List<String> itemAsistencia, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		int response = 0;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.asociarAsistencia() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.asociarAsistencia() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.asociarAsistencia() -> Entrada para asociar un Justiciable a una asistencia");
				try {
					// Identificar la Asistencia
					ScsAsistenciaExample paramAsis = new ScsAsistenciaExample();

					paramAsis.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
							.andAnioEqualTo(Short.valueOf(itemAsistencia.get(0))).andNumeroEqualTo(Long.valueOf(itemAsistencia.get(1)));

					List<ScsAsistencia> asistencia = scsAsistenciaExtendsMapper.selectByExample(paramAsis);
					// Actualizar con Justiciable seleccionado.
					if (asistencia != null && asistencia.size() > 0) {
						ScsAsistencia asisItem = new ScsAsistencia();
						asisItem.setIdinstitucion(Short.valueOf(idInstitucion));
						asisItem.setAnio(asistencia.get(0).getAnio());
						asisItem.setNumero(asistencia.get(0).getNumero());
						asisItem.setIdpersonajg(Long.valueOf(itemAsistencia.get(2))); // Solicitante
						asisItem.setFechamodificacion(date); // Fecha Modificación
						response =  ScsAsistenciaMapper.updateByPrimaryKeySelective(asisItem);
					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				} finally {
					if (response != 1) {
						error.setCode(400);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarAsistencia() -> ERROR. No se ha asociado ningun elemento");
					} else {
						error.setCode(200);
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info(
								"GestionEJGServiceImpl.asociarAsistencia() -> Se ha asociado el elemento correctamente");
					}
				}
				
				updateResponseDTO.setError(error);
			}
		}

		return updateResponseDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO asociarSOJ(List<String> itemSOJ, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		int response = 0;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.asociarSOJ() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.asociarSOJ() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.asociarSOJ() -> Entrada para asociar un Justiciable a un SOJ");
				try {
					// Identificar el SOJ
					ScsSojExample paramSOJ = new ScsSojExample();

					paramSOJ.createCriteria().andIdtiposojEqualTo(Short.valueOf(itemSOJ.get(2))).andIdinstitucionEqualTo(idInstitucion)
							.andAnioEqualTo(Short.valueOf(itemSOJ.get(0))).andNumsojEqualTo(String.valueOf(itemSOJ.get(1)));

					List<ScsSoj> soj = scsSojExtendsMapper.selectByExample(paramSOJ);
					// Actualizar con Justiciable seleccionado.
					if (soj != null && soj.size() > 0) {
						ScsSoj sojItem = new ScsSoj();
						sojItem.setIdinstitucion(idInstitucion);
						sojItem.setAnio(soj.get(0).getAnio());
						sojItem.setNumero(Long.valueOf(soj.get(0).getNumero()));
						if (itemSOJ.get(2) != null) {
							sojItem.setIdtiposoj(Short.valueOf(itemSOJ.get(2)));
						}else {
							sojItem.setIdtiposoj(soj.get(0).getIdtiposoj());
						}
						sojItem.setIdpersonajg(Long.valueOf(itemSOJ.get(3))); // justiciable
						sojItem.setFechamodificacion(date); // Fecha Modificación
						response =  scsSojExtendsMapper.updateByPrimaryKeySelective(sojItem);
					}

				} catch (Exception e) {
					error.setCode(400);
					LOGGER.error(e);
					error.setDescription("general.mensaje.error.bbdd");
				} finally {
					if (response != 1) {
						error.setCode(400);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarSOJ() -> ERROR. No se ha asociado ningun elemento");
					} else {
						error.setCode(200);
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info(
								"GestionEJGServiceImpl.asociarSOJ() -> Se ha asociado el elemento correctamente");
					}
				}
				
				updateResponseDTO.setError(error);
			}
		}

		return updateResponseDTO;
	}

	private String validacionDatosSegunTipoPcajg(String tipoJusticiable, Short idInstitucion,
			JusticiableItem justiciableItem) {

		/**
		 * 
		 * SCS_JUSTICIABLE = "0"; SCS_SOLICITANTE_EJG = "1"; SCS_UNIDAD_FAMILIAR_EJG =
		 * "2"; SCS_CONTRARIO_EJG = "3"; SCS_DESIGNACION = "4"; SCS_CONTRARIO_ASISTENCIA
		 * = "5"; SCS_SOLICITANTE_DESIGNACION = "6"; SCS_SOLICITANTE_ASISTENCIA = "7";
		 * 
		 **/

		LOGGER.info("validacionDatosSegunTipoPcajg() -> Entrada al servicio para validar los datos segun tipo Pcajg");
		String error = null;

		GenParametrosKey genParametrosKey = new GenParametrosKey();
		genParametrosKey.setIdinstitucion(idInstitucion);
		genParametrosKey.setParametro("PCAJG_TIPO");
		genParametrosKey.setModulo("SCS");

		GenParametros tipoPcajg = genParametrosExtendsMapper.selectByPrimaryKey(genParametrosKey);

		if (tipoPcajg != null) {

			switch (tipoPcajg.getValor()) {
			case "2":
				error = validateDatosTipoPcajg2y3(tipoJusticiable, justiciableItem);
				break;
			case "3":
				error = validateDatosTipoPcajg2y3(tipoJusticiable, justiciableItem);
				break;
			case "4":
				error = validateDatosTipoPcajg4(tipoJusticiable, justiciableItem);
				break;
			case "5":
				error = validateDatosTipoPcajg5(tipoJusticiable, justiciableItem);
				break;
			case "6":
				error = validateDatosTipoPcajg6(tipoJusticiable, justiciableItem);
				break;
			case "8":
				error = validateDatosTipoPcajg8(tipoJusticiable, justiciableItem);
				break;
			case "9":
				error = validateDatosTipoPcajg9(tipoJusticiable, justiciableItem);
				break;
			default:
				break;
			}
		}

		LOGGER.info("validacionDatosSegunTipoPcajg() -> Salida del servicio para validar los datos segun tipo Pcajg");

		return error;
	}

	private String obtenerTipoRolJusticiable(String rol) {

		LOGGER.info("obtenerTipoRolJusticiable() -> Entrada al servicio para obtener tipo rol de justiciable");

		String rolJusticiable = null;

		switch (rol) {
		case SigaConstants.ROL_SOLICITANTE_EJG:
			rolJusticiable = SigaConstants.SCS_SOLICITANTE_EJG;
			break;
		case SigaConstants.ROL_SOLICITANTE_ASISTENCIA:
			rolJusticiable = SigaConstants.SCS_SOLICITANTE_ASISTENCIA;
			break;
		case SigaConstants.ROL_SOLICITANTE_DESIGNACION:
			rolJusticiable = SigaConstants.SCS_SOLICITANTE_DESIGNACION;
			break;
		case SigaConstants.ROL_UNIDAD_FAMILIAR_EJG:
			rolJusticiable = SigaConstants.SCS_UNIDAD_FAMILIAR_EJG;
			break;
		case SigaConstants.ROL_CONTRADIO_DESIGNACION:
			rolJusticiable = SigaConstants.SCS_CONTRARIO_DESIGNACION;
			break;
		case SigaConstants.ROL_CONTRARIO_ASISTENCIA:
			rolJusticiable = SigaConstants.SCS_CONTRARIO_ASISTENCIA;
			break;
		case SigaConstants.ROL_CONTRARIO_EJG:
			rolJusticiable = SigaConstants.SCS_CONTRARIO_EJG;
			break;
		default:
			rolJusticiable = SigaConstants.SCS_JUSTICIABLE;
			break;
		}

		LOGGER.info("obtenerTipoRolJusticiable() -> Salida al servicio para obtener tipo rol de justiciable");

		return rolJusticiable;
	}

	private String validateDatosTipoPcajg2y3(String tipoJusticiable, JusticiableItem justiciableItem) {

		LOGGER.info("validateDatosTipoPcajg2y3() -> Entrada al servicio para validar los datos segun tipo Pcajg 2y3");
		String error = null;

		if (tipoJusticiable.equals(SigaConstants.SCS_UNIDAD_FAMILIAR_EJG)) {

			// if (justiciableItem.getParentesco() != "" && justiciableItem.getParentesco()
			// != null) {
			// error = null;
			// } else {
			// error =
			// "justiciaGratuita.justiciables.message.validacion.tipoPcajg.parentesco";
			// }
		}

		LOGGER.info("validateDatosTipoPcajg2y3() -> Salida del servicio para validar los datos segun tipo Pcajg 2y3");

		return error;
	}

	private String validateDatosTipoPcajg4(String tipoJusticiable, JusticiableItem justiciableItem) {

		LOGGER.info("validateDatosTipoPcajg4() -> Entrada al servicio para validar los datos segun tipo Pcajg 4");
		String error = null;

		if (tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_EJG)
				|| tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_DESIGNACION)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getDireccion()))
				validacionDireccion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdTipoVia()))
				validacionTipoVia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getCodigoPostal()))
				validacionCodigoPostal = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdProvincia()))
				validacionProvincia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPoblacion()))
				validacionPoblacion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getSexo()))
				validacionSexo = true;
			if (justiciableItem.getIdEstadoCivil() == null)
				validacionEstadoCivil = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getRegimen_conyugal()))
				validacionRegimenConyugal = true;

		}

		if (tipoJusticiable.equals(SigaConstants.SCS_UNIDAD_FAMILIAR_EJG)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getDireccion()))
				validacionDireccion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdTipoVia()))
				validacionTipoVia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getCodigoPostal()))
				validacionCodigoPostal = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdProvincia()))
				validacionProvincia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPoblacion()))
				validacionPoblacion = true;
			// if (UtilidadesString.esCadenaVacia(justiciableItem.getParentesco())) {
			// validacionParentesco = true;
			// }

		}

		if (tipoJusticiable.equals(SigaConstants.SCS_CONTRARIO_DESIGNACION)
				|| tipoJusticiable.equals(SigaConstants.SCS_CONTRARIO_EJG)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getDireccion()))
				validacionDireccion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdTipoVia()))
				validacionTipoVia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getCodigoPostal()))
				validacionCodigoPostal = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdProvincia()))
				validacionProvincia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPoblacion()))
				validacionPoblacion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPais())) {
				validacionPais = true;
			}

		}

		if (tipoJusticiable.equals(SigaConstants.SCS_CONTRARIO_ASISTENCIA)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPais())) {
				validacionPais = true;
			}
		}

		LOGGER.info("validateDatosTipoPcajg4() -> Salida del servicio para validar los datos segun tipo Pcajg 4");

		return error;
	}

	private String validateDatosTipoPcajg5(String tipoJusticiable, JusticiableItem justiciableItem) {

		LOGGER.info("validateDatosTipoPcajg5() -> Entrada al servicio para validar los datos segun tipo Pcajg 5");
		String error = null;

		if (tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_EJG)
				|| tipoJusticiable.equals(SigaConstants.SCS_UNIDAD_FAMILIAR_EJG)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getDireccion()))
				validacionDireccion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdTipoVia()))
				validacionTipoVia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getCodigoPostal()))
				validacionCodigoPostal = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdProvincia()))
				validacionProvincia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPoblacion()))
				validacionPoblacion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPais()))
				validacionPais = true;
			// if (UtilidadesString.esCadenaVacia(justiciableItem.getParentesco))
			// validacionParentesco = true;

		}

		LOGGER.info("validateDatosTipoPcajg5() -> Salida del servicio para validar los datos segun tipo Pcajg 5");

		return error;
	}

	private String validateDatosTipoPcajg6(String tipoJusticiable, JusticiableItem justiciableItem) {

		LOGGER.info("validateDatosTipoPcajg6() -> Entrada al servicio para validar los datos segun tipo Pcajg 6");
		String error = null;

		if (tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_EJG)
				|| tipoJusticiable.equals(SigaConstants.SCS_UNIDAD_FAMILIAR_EJG)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getDireccion()))
				validacionDireccion = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdTipoVia()))
				validacionTipoVia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getCodigoPostal()))
				validacionCodigoPostal = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdProvincia()))
				validacionProvincia = true;
			if (UtilidadesString.esCadenaVacia(justiciableItem.getIdPoblacion()))
				validacionPoblacion = true;
			if (justiciableItem.getFechaNacimiento() == null)
				validacionFechaNacimiento = true;
			// if (UtilidadesString.esCadenaVacia(justiciableItem.getParentesco))
			// validacionParentesco = true;

		}

		LOGGER.info("validateDatosTipoPcajg6() -> Salida del servicio para validar los datos segun tipo Pcajg 6");

		return error;
	}

	private String validateDatosTipoPcajg8(String tipoJusticiable, JusticiableItem justiciableItem) {

		LOGGER.info("validateDatosTipoPcajg8() -> Entrada al servicio para validar los datos segun tipo Pcajg 8");
		String error = null;

		// if (UtilidadesString.esCadenaVacia(justiciableItem.getParentesco())) {
		// validacionParentesco = true;
		// }

		LOGGER.info("validateDatosTipoPcajg8() -> Salida del servicio para validar los datos segun tipo Pcajg 8");

		return error;
	}

	private String validateDatosTipoPcajg9(String tipoJusticiable, JusticiableItem justiciableItem) {

		LOGGER.info("validateDatosTipoPcajg9() -> Entrada al servicio para validar los datos segun tipo Pcajg 9");
		String error = null;

		if (tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_EJG)
				|| tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_DESIGNACION)
				|| tipoJusticiable.equals(SigaConstants.SCS_SOLICITANTE_ASISTENCIA)
				|| tipoJusticiable.equals(SigaConstants.SCS_UNIDAD_FAMILIAR_EJG)) {

			if (UtilidadesString.esCadenaVacia(justiciableItem.getSexo())) {
				validacionSexo = true;
			}
		}

		LOGGER.info("validateDatosTipoPcajg9() -> Salida del servicio para validar los datos segun tipo Pcajg 9");

		return error;
	}

	@Override
	public JusticiableDTO getJusticiableByIdPersona(JusticiableBusquedaItem justiciableBusquedaItem,
			HttpServletRequest request) {

		LOGGER.info(
				"getJusticiableByIdPersona() -> Entrada al servicio para obtener el representante del justiciable por idpersona");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		JusticiableDTO justiciableDTO = new JusticiableDTO();
		List<ScsPersonajg> personajgList = new ArrayList<ScsPersonajg>();
		Error error = new Error();

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"getJusticiableByIdPersona() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getJusticiableByIdPersona() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"getJusticiableByIdPersona() / scsPersonajgExtendsMapper.selectByExample() -> Entrada a scsPersonajgExtendsMapper para obtener representante");
					ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
					scsPersonajgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(justiciableBusquedaItem.getIdPersona()));

					scsPersonajgExample.setOrderByClause("FECHAMODIFICACION DESC");

					personajgList = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);

					LOGGER.info(
							"getJusticiableByNif() / scsPersonajgExtendsMapper.selectByExample() -> Salida a scsPersonajgExtendsMapper para obtener representante");

					if (personajgList != null && personajgList.size() > 0) {

						ScsPersonajg personajg = personajgList.get(0);

						JusticiableItem justiciable = fillJusticiableItemOfScsPersonasjg(personajg);

						justiciableDTO.setJusticiable(justiciable);

					} else {

						JusticiableItem justiciable = new JusticiableItem();

						justiciable.setNif(justiciableBusquedaItem.getNif());

						justiciableDTO.setJusticiable(justiciable);

					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
				}

			}

		}
		LOGGER.info(
				"getJusticiableByIdPersona() -> Salida del servicio para obtener el representante del justiciable por idpersona");
		return justiciableDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO updateUnidadFamiliar(UnidadFamiliarEJGItem datos, HttpServletRequest request) {
		LOGGER.info("updateUnidadFamiliar() ->  Entrada al servicio para modificar un justiciable");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateUnidadFamiliar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateUnidadFamiliar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// Seleccionamos el familiar que vamos a modificar
					ScsUnidadfamiliarejgKey uniKey = new ScsUnidadfamiliarejgKey();

					// SIGARNV-2449@DTT.JAMARTIN@01/10/2021@INICIO
					if (datos.getUf_anio() != null) {
						uniKey.setAnio(Short.parseShort(datos.getUf_anio()));
					}

					uniKey.setIdinstitucion(idInstitucion);

					if (datos.getUf_idPersona() != null) {
						uniKey.setIdpersona(Long.parseLong(datos.getUf_idPersona()));
					}
					if (datos.getUf_idTipoejg() != null) {
						uniKey.setIdtipoejg(Short.parseShort(datos.getUf_idTipoejg()));
					}
					if (datos.getUf_numero() != null) {
						uniKey.setNumero(Long.parseLong(datos.getUf_numero()));
					}
					// SIGARNV-2449@DTT.JAMARTIN@01/10/2021@FIN

					ScsUnidadfamiliarejg familiar = scsUnidadfamiliarejgMapper.selectByPrimaryKey(uniKey);

					// Se comprueban y modifican los valores asociados a solicitante y rol

					ScsEjgKey ejgKey = new ScsEjgKey();

					// SIGARNV-2449@DTT.JAMARTIN@01/10/2021@INICIO
					if (datos.getUf_anio() != null) {
						ejgKey.setAnio(Short.parseShort(datos.getUf_anio()));
					}
					ejgKey.setIdinstitucion(idInstitucion);

					if (datos.getUf_idTipoejg() != null) {
						ejgKey.setIdtipoejg(Short.parseShort(datos.getUf_idTipoejg()));
					}
					if (datos.getUf_numero() != null) {
						ejgKey.setNumero(Long.parseLong(datos.getUf_numero()));
					}

					ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

					if (familiar != null && familiar.getEncalidadde() != null && datos.getUf_enCalidad() != null) {
						// En el caso que se le cambie el rol a un solicitante principal
						if (familiar.getEncalidadde().equals("3") && !datos.getUf_enCalidad().equals("3")) {
							ejg.setIdpersonajg(null);
							if (response == 1)
								response = scsEjgMapper.updateByPrimaryKey(ejg);
						}
					}
					if (datos.getUf_enCalidad() != null) {
						// En el caso que sea solicitante o solicitante principal respectivamente.
						if (datos.getUf_enCalidad().equals("2") || datos.getUf_enCalidad().equals("3")) {
							if (datos.getUf_enCalidad().equals("3")) {
								ejg.setIdpersonajg(Long.parseLong(datos.getUf_idPersona()));
								if (response == 1)
									response = scsEjgMapper.updateByPrimaryKey(ejg);
							}
							if (familiar != null) {
								familiar.setSolicitante((short) 1);
							}
						}
						// En el caso que se seleccione el rol de "Unidad familiar"
						else {
							if (familiar != null) {
								familiar.setSolicitante((short) 0);
							}
						}
					}

					// Modificamos el familiar
					if (familiar != null) {
						familiar.setEncalidadde(datos.getUf_enCalidad());
						if (datos.getIncapacitado() != null)
							familiar.setIncapacitado(datos.getIncapacitado());
						if (datos.getCircunsExcep() != null)
							familiar.setCircunstanciasExcepcionales(datos.getCircunsExcep());

						familiar.setIdtipogrupolab(datos.getIdTipoGrupoLab());
						familiar.setIdparentesco(datos.getIdParentesco());

						familiar.setIdtipoingreso(datos.getIdTipoIngreso());
						familiar.setDescripcioningresosanuales(datos.getDescrIngrAnuales());
						familiar.setImporteingresosanuales(datos.getImpIngrAnuales());
						familiar.setBienesinmuebles(datos.getBienesInmu());
						familiar.setImportebienesinmuebles(datos.getImpBienesInmu());
						familiar.setBienesmuebles(datos.getBienesMu());
						familiar.setImportebienesmuebles(datos.getImpBienesMu());
						familiar.setOtrosbienes(datos.getOtrosBienes());
						familiar.setImporteotrosbienes(datos.getImpOtrosBienes());

						familiar.setObservaciones(datos.getObservaciones());

						familiar.setUsumodificacion(usuarios.get(0).getIdusuario());
						familiar.setFechamodificacion(new Date());

						// Actualizamos la entrada
						if (response == 1) {
							response = scsUnidadfamiliarejgMapper.updateByPrimaryKey(familiar);
						}
					}
					// SIGARNV-2449@DTT.JAMARTIN@01/10/2021@FIN

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
					response = 0;
				}

				if (response == 0) {
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else {
					error.setCode(200);
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				updateResponseDTO.setError(error);
			}

		}
		return updateResponseDTO;
	}

	@Override
	public ScsUnidadfamiliarejgDTO getSolicitante(EjgItem datos, HttpServletRequest request) {
		LOGGER.info("getSolicitante() ->  Entrada al servicio para obtener el solicitante");

		ScsUnidadfamiliarejgDTO solicitante = new ScsUnidadfamiliarejgDTO();

		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getSolicitante() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getSolicitante() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// Buscamos aquella entrada que es la solicitante principal de nuestro ejg
					ScsUnidadfamiliarejgExample example = new ScsUnidadfamiliarejgExample();

					example.createCriteria().andAnioEqualTo(Short.parseShort(datos.getAnnio()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdtipoejgEqualTo(Short.parseShort(datos.getTipoEJG()))
							.andNumeroEqualTo(Long.parseLong(datos.getNumero())).andSolicitanteEqualTo((short) 1);

					// Actualizamos la entrada
					solicitante.setunidadFamiliarItems(scsUnidadfamiliarejgMapper.selectByExample(example));

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
				}

				if (error.getCode() == null) {
					error.setCode(200);
				}

				solicitante.setError(error);
			}

		}
		return solicitante;
	}

}