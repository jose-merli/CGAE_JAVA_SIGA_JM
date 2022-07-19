package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosRegistralesDTO;
import org.itcgae.siga.DTOs.cen.DatosRegistralesItem;
import org.itcgae.siga.DTOs.cen.PerJuridicaDatosRegistralesUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.ITarjetaDatosRegistralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoActividad;
import org.itcgae.siga.db.entities.CenNocolegiadoActividadExample;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenRegMercantil;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenNocolegiadoActividadMapper;
import org.itcgae.siga.db.mappers.CenRegMercantilMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenActividadprofesionalExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaDatosRegistralesServiceImpl implements ITarjetaDatosRegistralesService {

	private Logger LOGGER = Logger.getLogger(TarjetaDatosRegistralesServiceImpl.class);

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenActividadprofesionalExtendsMapper cenActividadprofesionalExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenNocolegiadoActividadMapper cenNocolegiadoActividadMapper;

	@Autowired
	private CenRegMercantilMapper cenRegMercantilMapper;

	@Autowired
	private AdmContadorExtendsMapper admContadorMapper;
	
	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Override
	public ComboDTO getActividadProfesionalPer(PersonaJuridicaActividadDTO personaJuridicaActividadDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"getActividadProfesionalPer() -> Entrada al servicio para obtener las actividades profesionales de una sociedad");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getActividadProfesionalPer() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getActividadProfesionalPer() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			personaJuridicaActividadDTO.setIdInstitucion(String.valueOf(idInstitucion));

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getActividadProfesionalPer() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de actividades profesionales para una sociedad");
				comboItems = cenNocolegiadoExtendsMapper.selectProfesionalActivitiesSociety(personaJuridicaActividadDTO,
						usuario.getIdlenguaje());
				LOGGER.info(
						"getActividadProfesionalPer() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de actividades profesionales para una sociedad");

				comboDTO.setCombooItems(comboItems);
			}

		}

		LOGGER.info(
				"getActividadProfesionalPer() -> Salida del servicio para obtener las actividades profesionales de una sociedad");
		return comboDTO;
	}

	@Override
	public ComboDTO getActividadProfesional(HttpServletRequest request) {

		LOGGER.info(
				"getActividadProfesional() -> Entrada al servicio para obtener las actividades profesionales disponibles");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getActividadProfesional() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getActividadProfesional() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = cenActividadprofesionalExtendsMapper.selectProfesionalActivities(usuario.getIdlenguaje());

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info(
				"getActividadProfesional() -> Salida del servicio para obtener las actividades profesionales disponibles");

		return comboDTO;
	}

	@Override
	public DatosRegistralesDTO searchRegistryDataLegalPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"searchRegistryDataLegalPerson() -> Entrada al servicio para recuperar datos registrales de la persona jurídica");

		DatosRegistralesDTO datosRegistralesDTO = new DatosRegistralesDTO();
		List<DatosRegistralesItem> datosRegistralesItems = new ArrayList<DatosRegistralesItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				personaJuridicaSearchDTO.setIdInstitucion(String.valueOf(idInstitucion));
				personaJuridicaSearchDTO.setIdLenguaje(usuario.getIdlenguaje());
				LOGGER.info(
						"searchRegistryDataLegalPerson() / cenActividadprofesionalExtendsMapper.searchRegistryDataLegalPerson() -> Entrada a cenActividadprofesionalExtendsMapper para recuperar datos de registro de persona jurídica");
				datosRegistralesItems = cenActividadprofesionalExtendsMapper
						.searchRegistryDataLegalPerson(personaJuridicaSearchDTO);
				LOGGER.info(
						"searchRegistryDataLegalPerson() / cenActividadprofesionalExtendsMapper.searchRegistryDataLegalPerson() -> Salida de cenActividadprofesionalExtendsMapper para recuperar datos de registro de persona jurídica");

				datosRegistralesDTO.setDatosRegistralesItems(datosRegistralesItems);
			}
		}

		LOGGER.info(
				"searchRegistryDataLegalPerson() -> Salida del servicio para recuperar datos registrales de la persona jurídica");
		return datosRegistralesDTO;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO updateRegistryDataLegalPerson(
			PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, HttpServletRequest request) {
		LOGGER.info(
				"updateRegistryDataLegalPerson() -> Entrada al servicio para actualizar datos registrales de una persona jurídica");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		int responseCenPersona = 0;
		int responseCenNocolegiado = 0;
		int responseCenRegMercantil = 0;
		int responseCenNoColegiadoActividad = 0;
		int responseInsertCenNoColegiadoActividad = 0;
		int responseBorrarCenNoColegiadoActividad = 0;
		boolean cerrojoDarAlta = true;
		boolean cerrojoCrearRegistro = true;
		boolean cerrojoBorrar = true;
		PersonaJuridicaActividadDTO personaJuridicaActividadDTO = new PersonaJuridicaActividadDTO();
		ComboDTO comboDTO = new ComboDTO();
		List<String> actividadesABorrar = new ArrayList<String>();
		List<CenNocolegiadoActividad> actividadExistente = new ArrayList<CenNocolegiadoActividad>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"updateRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateRegistryDataLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			// Validamos el número de contador para no duplicarlo
			Boolean registroValido = Boolean.TRUE;
			if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp())) {
				CenNocolegiadoExample exampleNocolegiado = new CenNocolegiadoExample();
				if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp())
						&& !UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp())) {
					exampleNocolegiado.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andPrefijoNumssppEqualTo(perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp())
							.andContadorNumssppEqualTo(
									Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp()))
							.andSufijoNumssppEqualTo(perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp());

				} else if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp())) {
					exampleNocolegiado.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andPrefijoNumssppEqualTo(perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp())
							.andContadorNumssppEqualTo(
									Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp()))
							.andSufijoNumssppIsNull();

				} else if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp())) {
					exampleNocolegiado.createCriteria().andIdinstitucionEqualTo(idInstitucion).andPrefijoNumssppIsNull()
							.andContadorNumssppEqualTo(
									Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp()))
							.andSufijoNumssppEqualTo(perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp());
				}

				if (perJuridicaDatosRegistralesUpdateDTO.getResena().length() < 3) {
					Error error = new Error();
					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("La reseña no es válida");
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}
				
				List<CenNocolegiado> nocolegiado = cenNocolegiadoExtendsMapper.selectByExample(exampleNocolegiado);

				if (null != nocolegiado && nocolegiado.size() > 0) {
					for (Iterator iterator = nocolegiado.iterator(); iterator.hasNext();) {
						CenNocolegiado cenNocolegiado = (CenNocolegiado) iterator.next();
						if (!cenNocolegiado.getIdpersona().toString()
								.equals(perJuridicaDatosRegistralesUpdateDTO.getIdPersona())) {
							Error error = new Error();
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("messages.censo.existeSociedadIgualNumeroRegistro");
							updateResponseDTO.setError(error);
							return updateResponseDTO;

						}
					}
				}
			}

			// Caso para extraer el idPersona cuando es una nueva sociedad
			if (perJuridicaDatosRegistralesUpdateDTO.getIdPersona() == null) {
				CenPersonaExample cenPersonaExample = new CenPersonaExample();
				cenPersonaExample.createCriteria().andNifcifEqualTo(perJuridicaDatosRegistralesUpdateDTO.getCif());
				List<CenPersona> cenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);

				if (null != cenPersona && !cenPersona.isEmpty()) {
					perJuridicaDatosRegistralesUpdateDTO.setIdPersona(String.valueOf(cenPersona.get(0).getIdpersona()));
				}
			}

			// 1. Actualizar tabla cen_persona
			LOGGER.info(
					"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updatebyExampleDataLegalPerson() -> Entrada a cenPersonaExtendsMapper para actualizar fecha de constitución de una persona jurídica");

			responseCenPersona = cenPersonaExtendsMapper
					.updatebyExampleDataLegalPerson(perJuridicaDatosRegistralesUpdateDTO, usuario);
			LOGGER.info(
					"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updatebyExampleDataLegalPerson() -> Salida de cenPersonaExtendsMapper para actualizar fecha de constitución de una persona jurídica");

			// 2. Actualizar tabla cen_reg_mercantil
			if (responseCenPersona == 1) {
				// if (null != perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional() &&
				// perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional().equals("1") ) {
				LOGGER.info(
						"updateRegistryDataLegalPerson() / cenRegMercantilMapper.updateByPrimaryKey(); -> Entrada a cenRegMercantilMapper para actualizar datos de una persona jurídica");
				if (perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro() != null) {

					CenRegMercantil datosRegistro = new CenRegMercantil();
//					if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getnumRegistro())) {
					datosRegistro.setIdDatosReg(perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro());
					datosRegistro.setNumRegistro(perJuridicaDatosRegistralesUpdateDTO.getnumRegistro());

//					if(perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro() != null) {
//						datosRegistro.setIdDatosReg(perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro());
//					}else {
//						datosRegistro.setIdDatosReg(Long.parseLong(" "));
//					}
//				
//
//						datosRegistro.setNumRegistro(perJuridicaDatosRegistralesUpdateDTO.getnumRegistro());
//					}else {
//						datosRegistro.setNumRegistro(" ");
//					}

					datosRegistro.setIdentificacionReg(perJuridicaDatosRegistralesUpdateDTO.getIdentificacionReg());
					datosRegistro.setFechaInscripcion(perJuridicaDatosRegistralesUpdateDTO.getFechaInscripcion());
					datosRegistro.setFechaCancelacion(perJuridicaDatosRegistralesUpdateDTO.getFechaCancelacion());
					datosRegistro.setFechamodificacion(new Date());
					datosRegistro.setUsumodificacion(usuario.getIdusuario());

					responseCenRegMercantil = cenRegMercantilMapper.updateByPrimaryKey(datosRegistro);
//					} else {
//						CenNocolegiado record = new CenNocolegiado();
//						CenNocolegiadoKey key = new CenNocolegiadoKey();
//						key.setIdinstitucion(idInstitucion);
//						key.setIdpersona(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()));
//
//						record = cenNocolegiadoExtendsMapper.selectByPrimaryKey(key);
//						record.setIdDatosReg(null);
//
//						responseCenNocolegiado = cenNocolegiadoExtendsMapper.updateByPrimaryKey(record);
//						responseCenRegMercantil = cenRegMercantilMapper
//								.deleteByPrimaryKey(perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro());
//						perJuridicaDatosRegistralesUpdateDTO.setIdDatosRegistro(null);
//					}
				} else {
					if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getnumRegistro())) {
						CenRegMercantil datosRegistro = new CenRegMercantil();
						datosRegistro.setNumRegistro(perJuridicaDatosRegistralesUpdateDTO.getnumRegistro());
						datosRegistro.setIdentificacionReg(perJuridicaDatosRegistralesUpdateDTO.getIdentificacionReg());
						datosRegistro.setFechaInscripcion(perJuridicaDatosRegistralesUpdateDTO.getFechaInscripcion());
						datosRegistro.setFechaCancelacion(perJuridicaDatosRegistralesUpdateDTO.getFechaCancelacion());
						datosRegistro.setFechamodificacion(new Date());
						datosRegistro.setUsumodificacion(usuario.getIdusuario());

						responseCenRegMercantil = cenRegMercantilMapper.insert(datosRegistro);
						perJuridicaDatosRegistralesUpdateDTO.setIdDatosRegistro(datosRegistro.getIdDatosReg());

					}
				}
				// }
				responseCenNocolegiado = cenNocolegiadoExtendsMapper.updateByExampleDataLegalPerson(
						perJuridicaDatosRegistralesUpdateDTO, String.valueOf(idInstitucion), usuario);
				LOGGER.info(
						"updateRegistryDataLegalPerson() / cenNocolegiadoExtendsMapper.updateByExampleDataLegalPerson() -> Salida de cenNocolegiadoExtendsMapper para actualizar datos de una persona jurídica");

				if (perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional().equals("1")
						&& !UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp())) {
					// 4. Actualizamos el registro en el contador en caso de que el modo sea
					// correcto.
					AdmContador contadorDTO = new AdmContador();
					List<AdmContadorDTO> contadorItem = new ArrayList<AdmContadorDTO>();
					AdmContadorKey exampleContador = new AdmContadorKey();
					exampleContador.setIdinstitucion(idInstitucion);
					exampleContador.setIdcontador("SSPP");
					contadorDTO = admContadorMapper.selectByPrimaryKey(exampleContador);
					LOGGER.info(
							"getDatosContador() / admContadorMapper.getContadoresSearch() -> Entrada a AdmContador para buscar el contador a aplicar");
					if (null != contadorDTO) {
						if (!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp()) &&
								Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp()) > contadorDTO.getContador() ) {
					
							contadorDTO.setContador(
									Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp()));
							contadorDTO.setFechamodificacion(new Date());
							contadorDTO.setUsumodificacion(usuario.getIdusuario());
							admContadorMapper.updateByPrimaryKey(contadorDTO);
						}
					}
				}

			} else {
				Error error = new Error();
				updateResponseDTO.setStatus(SigaConstants.KO);
				error.setMessage("general.message.error.realiza.accion");
				updateResponseDTO.setError(error);
				LOGGER.warn("updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> "
						+ updateResponseDTO.getStatus() + ". No se actualizó correctamente la tabla cen_persona");

				return updateResponseDTO;
			}

			// 3. Actualizar tabla CEN_NOCOLEGIADO_ACTIVIDAD
			if (responseCenNocolegiado == 1) {

				// busca las actividades que estaban asociadas a la persona juridica
				personaJuridicaActividadDTO.setIdInstitucion(String.valueOf(idInstitucion));
				personaJuridicaActividadDTO.setIdPersona(perJuridicaDatosRegistralesUpdateDTO.getIdPersona());
				comboDTO = getActividadProfesionalPer(personaJuridicaActividadDTO, request);

				for (int i = 0; i < comboDTO.getCombooItems().size(); i++) {
					actividadesABorrar.add(comboDTO.getCombooItems().get(i).getValue());
				}

				// añadimos las nuevas actividades
				for (String actividad : perJuridicaDatosRegistralesUpdateDTO.getActividades()) {
					// comprobar si existe registro en tabla
					CenNocolegiadoActividadExample cenNocolegiadoActividadExample = new CenNocolegiadoActividadExample();
					cenNocolegiadoActividadExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()))
							.andIdactividadprofesionalEqualTo(Short.valueOf(actividad));

					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.selectByExample() -> Entrada a cenNocolegiadoActividadMapper para comprobar si existe una actividad para una persona jurídica");

					actividadExistente = cenNocolegiadoActividadMapper.selectByExample(cenNocolegiadoActividadExample);
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.selectByExample() -> Salida de cenNocolegiadoActividadMapper para comprobar si existe una actividad para una persona jurídica");

					if (!actividadExistente.isEmpty()) { // hay registros => poner fecha_baja = null

						if (null != actividadExistente.get(0).getFechaBaja()) {
							// dar de alta una actividad asociada a una persona juridica
							CenNocolegiadoActividad record = new CenNocolegiadoActividad();
							record.setIdinstitucion(idInstitucion);
							record.setIdpersona(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()));
							record.setIdactividadprofesional(Short.valueOf(actividad));
							record.setFechamodificacion(new Date());
							record.setUsumodificacion(usuario.getIdusuario());
							record.setFechaBaja(null);

							CenNocolegiadoActividadExample example = new CenNocolegiadoActividadExample();
							example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(
											Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()))
									.andIdactividadprofesionalEqualTo(Short.valueOf(actividad));
							LOGGER.info(
									"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoActividadMapper para dar de alta una actividad para una persona jurídica");

							// responseCenNoColegiadoActividad =
							// cenNocolegiadoActividadMapper.updateByExampleSelective(record, example);
							responseCenNoColegiadoActividad = cenNocolegiadoActividadMapper.updateByExample(record,
									example);
							LOGGER.info(
									"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Salida de cenNocolegiadoActividadMapper para dar de alta una actividad para una persona jurídica");

							if (cerrojoDarAlta) {
								updateResponseDTO.setStatus(SigaConstants.OK);
								cerrojoDarAlta = false;
							}

							if (responseCenNoColegiadoActividad == 0) {
								Error error = new Error();
								updateResponseDTO.setStatus(SigaConstants.KO);
								error.setMessage("general.message.error.realiza.accion");
								updateResponseDTO.setError(error);
								LOGGER.warn(
										"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> "
												+ updateResponseDTO.getStatus()
												+ ". No se actualizó correctamente la tabla cen_persona");

								return updateResponseDTO;

							}
						}
					} else { // no hay registros => crear registro
						CenNocolegiadoActividad insertActividad = new CenNocolegiadoActividad();
						insertActividad.setFechaBaja(null);
						insertActividad.setFechamodificacion(new Date());
						insertActividad.setIdactividadprofesional(Short.valueOf(actividad));
						insertActividad.setIdinstitucion(idInstitucion);
						insertActividad.setIdpersona(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()));
						insertActividad.setUsumodificacion(usuario.getIdusuario());
						LOGGER.info(
								"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.insert() -> Entrada a cenNocolegiadoActividadMapper para crear una actividad asociada a una persona jurídica");

						responseInsertCenNoColegiadoActividad = cenNocolegiadoActividadMapper.insert(insertActividad);
						LOGGER.info(
								"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.insert() -> Salida de cenNocolegiadoActividadMapper para crear una actividad asociada a una persona jurídica");

						if (cerrojoCrearRegistro) {
							updateResponseDTO.setStatus(SigaConstants.OK);
							cerrojoCrearRegistro = false;
						}

						if (responseInsertCenNoColegiadoActividad == 0) {
							Error error = new Error();
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("general.message.error.realiza.accion");
							updateResponseDTO.setError(error);
							LOGGER.warn(
									"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> "
											+ updateResponseDTO.getStatus()
											+ ". No se actualizó correctamente la tabla cen_persona");

							return updateResponseDTO;

						}
					}

					// dejamos en el array solo las actividades a desasociar
					if (actividadesABorrar.contains(actividad)) {
						actividadesABorrar.remove(actividad);
					}

				}

				// borrar actividades que ya no forman parte de una persona juridica =>
				// fecha_baja = new Date()
				for (String actividadABorrar : actividadesABorrar) {
					CenNocolegiadoActividad recordBorrar = new CenNocolegiadoActividad();
					recordBorrar.setFechamodificacion(new Date());
					recordBorrar.setUsumodificacion(usuario.getIdusuario());
					recordBorrar.setFechaBaja(new Date());

					CenNocolegiadoActividadExample exampleBorrar = new CenNocolegiadoActividadExample();
					exampleBorrar.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()))
							.andIdactividadprofesionalEqualTo(Short.valueOf(actividadABorrar));
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoActividadMapper para desasignar una actividad asociada a una persona jurídica");

					responseBorrarCenNoColegiadoActividad = cenNocolegiadoActividadMapper
							.updateByExampleSelective(recordBorrar, exampleBorrar);
					LOGGER.info(
							"updateRegistryDataLegalPerson() / cenNocolegiadoActividadMapper.updateByExampleSelective() -> Salida de cenNocolegiadoActividadMapper para desasignar una actividad asociada a una persona jurídica");

					if (cerrojoBorrar) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						cerrojoBorrar = false;
					}

					if (responseBorrarCenNoColegiadoActividad == 0) {
						Error error = new Error();
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("general.message.error.realiza.accion");
						updateResponseDTO.setError(error);
						LOGGER.warn(
								"updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> "
										+ updateResponseDTO.getStatus()
										+ ". No se actualizó correctamente la tabla cen_persona");

						return updateResponseDTO;

					}
				}

			} else {
				Error error = new Error();
				updateResponseDTO.setStatus(SigaConstants.KO);
				error.setMessage("general.message.error.realiza.accion");
				updateResponseDTO.setError(error);
				LOGGER.warn("updateRegistryDataLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> "
						+ updateResponseDTO.getStatus() + ". No se actualizó correctamente la tabla cen_persona");

				return updateResponseDTO;
			}
			
			CenClienteKey key = new CenClienteKey();
			key.setIdinstitucion(idInstitucion);
			key.setIdpersona(Long.valueOf(perJuridicaDatosRegistralesUpdateDTO.getIdPersona()));
			CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key);
			cliente.setFechaactualizacion(new Date());
			cenClienteMapper.updateByPrimaryKey(cliente);
		}

		LOGGER.info(
				"updateRegistryDataLegalPerson() -> Salida del servicio para actualizar datos registrales de una persona jurídica");
		updateResponseDTO.setStatus(SigaConstants.OK);
		return updateResponseDTO;
	}

	@Override
	public AdmContadorDTO getDatosContador(HttpServletRequest request) {
		LOGGER.info("getDatosContador() -> Entrada al servicio para obtener el contador a aplicar a una sociedad");
		AdmContadorDTO contadorDTO = new AdmContadorDTO();
		List<AdmContadorDTO> contadorItem = new ArrayList<AdmContadorDTO>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDatosContador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDatosContador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			// personaJuridicaActividadDTO.setIdInstitucion(String.valueOf(idInstitucion));

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"getDatosContador() / admContadorMapper.getContadoresSearch() -> Entrada a AdmContador para buscar el contador a aplicar");
				ContadorRequestDTO exampleContador = new ContadorRequestDTO();
				exampleContador.setIdInstitucion(idInstitucion.toString());
				exampleContador.setIdContador("SSPP");
				contadorItem = admContadorMapper.getContadoresSearch(0, exampleContador);
				LOGGER.info(
						"getDatosContador() / admContadorMapper.getContadoresSearch() -> Entrada a AdmContador para buscar el contador a aplicar");
				if (null != contadorItem && contadorItem.size() > 0) {
					contadorDTO = contadorItem.get(0);
				}
			}

		}

		LOGGER.info("getDatosContador() -> Entrada al servicio para obtener el contador a aplicar a una sociedad");
		return contadorDTO;
	}

}
