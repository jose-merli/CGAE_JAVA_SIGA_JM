package org.itcgae.siga.cen.services.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesDeleteDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenComponentes;
import org.itcgae.siga.db.entities.CenComponentesExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaDatosIntegrantesServiceImpl implements ITarjetaDatosIntegrantesService {

	private Logger LOGGER = Logger.getLogger(TarjetaDatosIntegrantesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;

	@Autowired
	private CenProvinciasExtendsMapper cenProvinciasExtendsMapper;

	@Autowired
	private CenCargoExtendsMapper cenCargoExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;
	
	@Autowired
	private IAuditoriaCenHistoricoService auditoriaCenHistoricoService;

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Override
	public DatosIntegrantesDTO searchIntegrantesData(int numPagina, DatosIntegrantesSearchDTO datosIntegrantesSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchIntegrantesData() -> Entrada al servicio para la búsqueda por filtros de integrantes ");

		List<DatosIntegrantesItem> datosIntegrantesItem = new ArrayList<DatosIntegrantesItem>();
		DatosIntegrantesDTO datosIntegrantesDTO = new DatosIntegrantesDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de integrantes ");
				datosIntegrantesSearchDTO.setIdInstitucion(idInstitucion.toString());
				datosIntegrantesItem = cenComponentesExtendsMapper.selectIntegrantes(datosIntegrantesSearchDTO,
						String.valueOf(idInstitucion));
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de integrantes ");

				datosIntegrantesDTO.setDatosIntegrantesItem(datosIntegrantesItem);
			} else {
				LOGGER.warn(
						"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchIntegrantesData() -> idInstitucion del token nula");
		}

		LOGGER.info("searchIntegrantesData() -> Salida del servicio para la búsqueda por filtros de integrantes ");
		return datosIntegrantesDTO;
	}

	@Override
	public ComboDTO getProvinces(HttpServletRequest request) {
		LOGGER.info("getProvinces() -> Entrada al servicio para búsqueda de las provincias");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Entrada a cenProvinciasExtendsMapper para obtener listado de provincias ");
		comboItems = cenProvinciasExtendsMapper.selectDistinctProvinces();
		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Salida de cenProvinciasExtendsMapper para obtener listado de provincias ");

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getProvinces() -> Salida al servicio para búsqueda de las provincias");
		return comboDTO;
	}

	@Override
	public ComboDTO getCargos(HttpServletRequest request) {
		LOGGER.info("getCargos() -> Entrada al servicio para búsqueda de cargos");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				usuario = usuarios.get(0);

				LOGGER.info(
						"getCargos() / cenCargoExtendsMapper.getCargos() -> Entrada a cenCargoExtendsMapper para obtener listado de cargos");
				comboItems = cenCargoExtendsMapper.getCargos(usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				LOGGER.info(
						"getCargos() / cenCargoExtendsMapper.getCargos() -> Salida de cenCargoExtendsMapper para obtener listado de cargos ");

			}

		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getCargos() -> Salida al servicio para búsqueda de cargos");
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO,
			HttpServletRequest request) {

		LOGGER.info("updateMember() -> Entrada al servicio para actualizar información de integrantes");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);

			// 1. Ya existe un idpersona para el nuevo integrante
			if (!tarjetaIntegrantesUpdateDTO.getIdPersonaComponente().equals("")) {

				int responseCenCliente = 1;
				int responseInsertNoColegiado = 1;

				// 1.1 Comprobamos que existe en tabla cen_cliente
				CenCliente cenCliente = new CenCliente();
				CenClienteKey key = new CenClienteKey();
				key.setIdinstitucion(Short.valueOf(tarjetaIntegrantesUpdateDTO.getColegio()));
				key.setIdpersona(Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersonaComponente()));
				cenCliente = cenClienteMapper.selectByPrimaryKey(key);
				
				CenComponentesExample cenComponentesExample = new CenComponentesExample();
				cenComponentesExample.createCriteria().andCenClienteIdinstitucionEqualTo(Short.valueOf(tarjetaIntegrantesUpdateDTO.getColegio()))
						.andCenClienteIdpersonaEqualTo(Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersonaComponente()))
						.andIdcomponenteEqualTo(Short.valueOf(tarjetaIntegrantesUpdateDTO.getIdComponente()));
				
				CenComponentes cenComponentesAnterior =  cenComponentesExtendsMapper.selectByExample(cenComponentesExample).get(0);

				// 1.2 Si no existe, se crea un registro
				if (null == cenCliente) {
					CenCliente record = new CenCliente();
					record = rellenarInsertCenClienteUpdate(tarjetaIntegrantesUpdateDTO, usuario);
					responseCenCliente = cenClienteMapper.insertSelective(record);
				}

				if (responseCenCliente == 1) {
					
					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersona()));

					LOGGER.info(
							"updateMember() -> Entrada a cenColegiadoExtendsMapper para comprobar si es un colegiado");

					List<CenColegiado> cenColegiadoList = cenColegiadoExtendsMapper
							.selectByExample(cenColegiadoExample);

					LOGGER.info(
							"updateMember() -> Salida de cenColegiadoExtendsMapper para comprobar si es un colegiado");
					if(cenColegiadoList == null || cenColegiadoList.size() == 0) {

						LOGGER.info(
								"updateMember() / cenNocolegiadoExtendsMapper.insertSelective() -> Entrada a cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");
	
						CenNocolegiadoExample cenNocolegiadoExample = new CenNocolegiadoExample();
						cenNocolegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersona()));
	
						LOGGER.info(
								"createMember() / cenNocolegiadoExtendsMapper.selectByExample() -> Entrada a cenNocolegiadoExtendsMapper para obtener información no colegiado");
	
						List<CenNocolegiado> cenNocolegiadoList = cenNocolegiadoExtendsMapper
								.selectByExample(cenNocolegiadoExample);
	
						LOGGER.info(
								"createMember() / cenNocolegiadoExtendsMapper.selectByExample() -> Salida de cenNocolegiadoExtendsMapper para obtener información no colegiado");
	
						if (null == cenNocolegiadoList || cenNocolegiadoList.size() == 0) {
	
							CenNocolegiado cenNocolegiado = rellenarInsertCenNoColegiado(usuario,
									Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersonaComponente()), idInstitucion);
							responseInsertNoColegiado = cenNocolegiadoExtendsMapper.insertSelective(cenNocolegiado);
	
							LOGGER.info(
									"updateMember() / cenNocolegiadoExtendsMapper.insertSelective() -> Salida de cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");
	
						}
					}

					if (responseInsertNoColegiado == 1) {

						LOGGER.info(
								"getCargos() / cenComponentesExtendsMapper.updateMember() -> Entrada a cenComponentesExtendsMapper para actualizar datos de un integrante");

						response = cenComponentesExtendsMapper.updateMember(tarjetaIntegrantesUpdateDTO, usuario,
								String.valueOf(idInstitucion));

						LOGGER.info(
								"getCargos() / cenComponentesExtendsMapper.updateMember() -> Salida de cenComponentesExtendsMapper para actualizar datos de un integrante");

						CenClienteKey keyCliente = new CenClienteKey();
						keyCliente.setIdinstitucion(idInstitucion);
						keyCliente.setIdpersona(Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersona()));
						CenCliente cliente = cenClienteMapper.selectByPrimaryKey(keyCliente);
						cliente.setFechaactualizacion(new Date());
						cenClienteMapper.updateByPrimaryKey(cliente);
						
						updateResponseDTO.setStatus(SigaConstants.OK);
						
						// AUDITORIA si se actualizó un componente correctamente
						
						CenComponentes cenComponentes = getCenComponentes(tarjetaIntegrantesUpdateDTO,
								idInstitucion, usuario.getUsumodificacion());
						
						CenComponentes cenComponentesPosterior = cenComponentes;
						
						cenComponentesPosterior.setIdpersona(cenComponentesAnterior.getIdpersona());
						cenComponentesPosterior.setCenClienteIdpersona(cenComponentesAnterior.getCenClienteIdpersona());

						auditoriaCenHistoricoService.manageAuditoriaComponentes(cenComponentesAnterior,
								cenComponentesPosterior, "UPDATE", request, "Modificado integrante de sociedad");
					}

					if (response == 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn("getCargos() / cenComponentesExtendsMapper.updateMember() -> "
								+ updateResponseDTO.getStatus() + ". No se pudo actualizar datos de un integrantes");

					}
				}

			}

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("getCargos() / admUsuariosExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus()
					+ ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("updateMember() -> Salida del servicio para actualizar información de integrantes");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO createMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,
			HttpServletRequest request) {

		LOGGER.info("updateMember() -> Entrada al servicio para crear un nuevo integrante");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		ComboItem comboItem = new ComboItem();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null == tarjetaIntegrantesCreateDTO.getColegio()) {
			tarjetaIntegrantesCreateDTO.setColegio(idInstitucion.toString());
		}

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

		LOGGER.info(
				"createMember() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		LOGGER.info(
				"createMember() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			CenComponentes cenComponentesPosterior = new CenComponentes();

			// 1. Ya existe un idpersona para el nuevo integrante
			if (!tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante().equals("")) {
				int responseCenCliente = 1; // se puede crear o no => se inicializa a 1
				int responseCenComponentes = 0; // se debe crear siempre
				int responseInsertNoColegiado = 1;

				// 1.1 Comprobamos que existe en tabla cen_cliente
				CenCliente cenCliente = new CenCliente();
				CenClienteKey key = new CenClienteKey();
				key.setIdinstitucion(idInstitucion);
				key.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));
				cenCliente = cenClienteMapper.selectByPrimaryKey(key);

				// 1.2 Si no existe, se crea un registro
				if (null == cenCliente) {
					CenCliente record = new CenCliente();
					record = rellenarInsertCenCliente(tarjetaIntegrantesCreateDTO, usuario);
					record.setIdinstitucion(idInstitucion);
					responseCenCliente = cenClienteMapper.insertSelective(record);
				}

				if (responseCenCliente == 1) {
					// 1.3 Comprobar si esta en cen_nocolegiado/cen_colegiado (no se hace ahora
					// mismo)

					CenNocolegiadoExample cenNocolegiadoExample = new CenNocolegiadoExample();
					cenNocolegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));

					LOGGER.info(
							"createMember() / cenNocolegiadoExtendsMapper.selectByExample() -> Entrada a cenNocolegiadoExtendsMapper para obtener información no colegiado");

					List<CenNocolegiado> cenNocolegiadoList = cenNocolegiadoExtendsMapper
							.selectByExample(cenNocolegiadoExample);

					LOGGER.info(
							"createMember() / cenNocolegiadoExtendsMapper.selectByExample() -> Salida de cenNocolegiadoExtendsMapper para obtener información no colegiado");

					if (!(null != cenNocolegiadoList && cenNocolegiadoList.size() > 0)) {

						LOGGER.info(
								"createMember() / cenNocolegiadoExtendsMapper.insertSelective() -> Entrada a cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");

						CenNocolegiado cenNocolegiado = rellenarInsertCenNoColegiado(usuario,
								Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()), idInstitucion);
						responseInsertNoColegiado = cenNocolegiadoExtendsMapper.insertSelective(cenNocolegiado);

						LOGGER.info(
								"createMember() / cenNocolegiadoExtendsMapper.insertSelective() -> Salida de cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");

					}

					if (responseInsertNoColegiado == 1) {

						// 1.4 Insertamos un registro en tabla cen_componentes
						comboItem = cenComponentesExtendsMapper.selectMaxIDComponente(
								tarjetaIntegrantesCreateDTO.getIdPersonaPadre(), String.valueOf(idInstitucion));
						int siguienteIDComponente;
						if (null != comboItem) {
							siguienteIDComponente = Integer.valueOf(comboItem.getValue()) + 1;
						} else {
							siguienteIDComponente = 1;
						}

						tarjetaIntegrantesCreateDTO.setIdComponente(String.valueOf(siguienteIDComponente));

						CenComponentes cenComponentes = getCenComponentes(tarjetaIntegrantesCreateDTO, idInstitucion,
								usuario.getUsumodificacion());
						responseCenComponentes = cenComponentesExtendsMapper.insertSelective(cenComponentes);
						
						cenComponentesPosterior = cenComponentes;
					}

					if (responseCenComponentes == 1) {
						CenClienteKey keyCliente = new CenClienteKey();
						keyCliente.setIdinstitucion(idInstitucion);
						keyCliente.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaPadre()));
						CenCliente cliente = cenClienteMapper.selectByPrimaryKey(keyCliente);
						cliente.setFechaactualizacion(new Date());
						cenClienteMapper.updateByPrimaryKey(cliente);
						
						updateResponseDTO.setStatus(SigaConstants.OK);
						
						// AUDITORIA si se insertó un componente correctamente

						auditoriaCenHistoricoService.manageAuditoriaComponentes(null,
								cenComponentesPosterior, "INSERT", request, "Insertado integrante de sociedad");
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				} else {
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			} else {
				// 2. No existe un idpersona para el nuevo integrante

				// 2.1. Insertar en cen_persona
				int responseCenPersona = 0;
				int responseCenCliente = 0;
				int responseCenComponentes = 0;
				CrearPersonaDTO crearPersonaDTO = new CrearPersonaDTO();
				crearPersonaDTO = rellenarInsertCenPersona(tarjetaIntegrantesCreateDTO, usuario);
				responseCenPersona = cenPersonaExtendsMapper.insertSelectiveForPersonFile(crearPersonaDTO, usuario);

				if (responseCenPersona == 1) {
					// 2.2. Insertar en cen_cliente

					comboItems = cenPersonaExtendsMapper.selectMaxIdPersona(usuario.getIdinstitucion().toString());
					String maxIdPersona = comboItems.get(0).getValue();

					tarjetaIntegrantesCreateDTO.setIdPersonaIntegrante(maxIdPersona);
					tarjetaIntegrantesCreateDTO.setIdInstitucionIntegrante(String.valueOf(idInstitucion));

					CenCliente record = new CenCliente();
					record = rellenarInsertCenCliente(tarjetaIntegrantesCreateDTO, usuario);
					responseCenCliente = cenClienteMapper.insertSelective(record);
					if (responseCenCliente == 1) {
						// 2.3. Insertar en cen_nocolegiado

						LOGGER.info(
								"createMember() / cenNocolegiadoExtendsMapper.insertSelective() -> Entrada a cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");

						CenNocolegiado cenNocolegiado = rellenarInsertCenNoColegiado(usuario,
								Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()), idInstitucion);
						int responseInsertNoColegiado = cenNocolegiadoExtendsMapper.insertSelective(cenNocolegiado);
						LOGGER.info(
								"createMember() / cenNocolegiadoExtendsMapper.insertSelective() -> Salida de cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");

						if (responseInsertNoColegiado == 1) {
							// 2.4. Insertar en cen_componente
							comboItem = cenComponentesExtendsMapper.selectMaxIDComponente(
									tarjetaIntegrantesCreateDTO.getIdPersonaPadre(), String.valueOf(idInstitucion));
							int siguienteIDComponente;
							if (null != comboItem) {
								siguienteIDComponente = Integer.valueOf(comboItem.getValue()) + 1;
							} else {
								siguienteIDComponente = 1;
							}

							tarjetaIntegrantesCreateDTO.setIdComponente(String.valueOf(siguienteIDComponente));

							CenComponentes cenComponentes = getCenComponentes(tarjetaIntegrantesCreateDTO,
									idInstitucion, usuario.getUsumodificacion());
							responseCenComponentes = cenComponentesExtendsMapper.insertSelective(cenComponentes);
							
							cenComponentesPosterior = cenComponentes;
						}

						if (responseCenComponentes == 1) {
							CenClienteKey keyCliente = new CenClienteKey();
							keyCliente.setIdinstitucion(idInstitucion);
							keyCliente.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaPadre()));
							CenCliente cliente = cenClienteMapper.selectByPrimaryKey(keyCliente);
							cliente.setFechaactualizacion(new Date());
							cenClienteMapper.updateByPrimaryKey(cliente);
							
							updateResponseDTO.setStatus(SigaConstants.OK);
							
							// AUDITORIA si se insertó un componente correctamente

							auditoriaCenHistoricoService.manageAuditoriaComponentes(null,
									cenComponentesPosterior, "INSERT", request, "Insertado integrante de sociedad");
						} else {
							updateResponseDTO.setStatus(SigaConstants.KO);
						}

					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}

				} else {
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

			}
		}

		LOGGER.info("updateMember() -> Salida del servicio para crear un nuevo integrante");
		return updateResponseDTO;
	}

	private CenComponentes getCenComponentes(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,
			Short idInstitucion, int usuario) {
		CenComponentes cenComponentes = new CenComponentes();

		if (null != tarjetaIntegrantesCreateDTO.getCapitalSocial()) {
			cenComponentes.setCapitalsocial(
					new BigDecimal(tarjetaIntegrantesCreateDTO.getCapitalSocial(), MathContext.DECIMAL64));
		}

		cenComponentes.setCargo(tarjetaIntegrantesCreateDTO.getCargo());

		if (null != tarjetaIntegrantesCreateDTO.getColegio()) {
			cenComponentes.setCenClienteIdinstitucion(Short.valueOf(tarjetaIntegrantesCreateDTO.getColegio()));
		} else {
			cenComponentes.setCenClienteIdinstitucion(Short.valueOf(idInstitucion));
		}

		if (null != tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()) {
			cenComponentes.setCenClienteIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));
		}

		if (null != tarjetaIntegrantesCreateDTO.getFechaBajaCargo()) {
			cenComponentes.setFechabaja(tarjetaIntegrantesCreateDTO.getFechaBajaCargo());
		}

		if (null != tarjetaIntegrantesCreateDTO.getFechaCargo()) {
			cenComponentes.setFechacargo(tarjetaIntegrantesCreateDTO.getFechaCargo());
		}
		cenComponentes.setFechamodificacion(new Date());
		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getFlagSocio())) {
			cenComponentes.setFlagSocio(Short.valueOf(tarjetaIntegrantesCreateDTO.getFlagSocio()));
		}
		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getIdCargo())) {
			cenComponentes.setIdcargo(Short.valueOf(tarjetaIntegrantesCreateDTO.getIdCargo()));
		}
		cenComponentes.setIdcomponente(Short.valueOf(tarjetaIntegrantesCreateDTO.getIdComponente()));
		cenComponentes.setIdinstitucion(idInstitucion);
		cenComponentes.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaPadre()));

		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getIdProvincia())) {
			cenComponentes.setIdprovincia(tarjetaIntegrantesCreateDTO.getIdProvincia());
		}

		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getNumColegiado())) {
			cenComponentes.setNumcolegiado(tarjetaIntegrantesCreateDTO.getNumColegiado());
		}

		if (!tarjetaIntegrantesCreateDTO.getIdTipoColegio().equals("")) {
			cenComponentes.setIdtipocolegio(Short.valueOf(tarjetaIntegrantesCreateDTO.getIdTipoColegio()));
		}

		cenComponentes.setSociedad("0");
		cenComponentes.setUsumodificacion(usuario);

		return cenComponentes;
	}
	
	private CenComponentes getCenComponentes(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO,
			Short idInstitucion, int usuario) {
		CenComponentes cenComponentes = new CenComponentes();

		if (null != tarjetaIntegrantesUpdateDTO.getCapitalSocial()) {
			cenComponentes.setCapitalsocial(
					new BigDecimal(tarjetaIntegrantesUpdateDTO.getCapitalSocial(), MathContext.DECIMAL64));
		}

		cenComponentes.setCargo(tarjetaIntegrantesUpdateDTO.getCargo());

		if (null != tarjetaIntegrantesUpdateDTO.getColegio()) {
			cenComponentes.setCenClienteIdinstitucion(Short.valueOf(tarjetaIntegrantesUpdateDTO.getColegio()));
		} else {
			cenComponentes.setCenClienteIdinstitucion(Short.valueOf(idInstitucion));
		}

		if (null != tarjetaIntegrantesUpdateDTO.getFechaBajaCargo()) {
			cenComponentes.setFechabaja(tarjetaIntegrantesUpdateDTO.getFechaBajaCargo());
		}

		if (null != tarjetaIntegrantesUpdateDTO.getFechaCargo()) {
			cenComponentes.setFechacargo(tarjetaIntegrantesUpdateDTO.getFechaCargo());
		}
		cenComponentes.setFechamodificacion(new Date());
		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getFlagSocio())) {
			cenComponentes.setFlagSocio(Short.valueOf(tarjetaIntegrantesUpdateDTO.getFlagSocio()));
		}
		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getIdCargo())) {
			cenComponentes.setIdcargo(Short.valueOf(tarjetaIntegrantesUpdateDTO.getIdCargo()));
		}
		cenComponentes.setIdcomponente(Short.valueOf(tarjetaIntegrantesUpdateDTO.getIdComponente()));
		cenComponentes.setIdinstitucion(idInstitucion);

		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getIdProvincia())) {
			cenComponentes.setIdprovincia(tarjetaIntegrantesUpdateDTO.getIdProvincia());
		}

		if (!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getNumColegiado())) {
			cenComponentes.setNumcolegiado(tarjetaIntegrantesUpdateDTO.getNumColegiado());
		}

		if (!tarjetaIntegrantesUpdateDTO.getIdTipoColegio().equals("")) {
			cenComponentes.setIdtipocolegio(Short.valueOf(tarjetaIntegrantesUpdateDTO.getIdTipoColegio()));
		}

		cenComponentes.setSociedad("0");
		cenComponentes.setUsumodificacion(usuario);

		return cenComponentes;
	}

	@Override
	public UpdateResponseDTO deleteMember(TarjetaIntegrantesDeleteDTO[] tarjetaIntegrantesDeleteDTO,
			HttpServletRequest request) {

		LOGGER.info("updateMember() -> Entrada al servicio para actualizar información de integrantes");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			for (int i = 0; i < tarjetaIntegrantesDeleteDTO.length; i++) {
				LOGGER.info(
						"getCargos() / cenComponentesExtendsMapper.updateMember() -> Entrada a cenComponentesExtendsMapper para actualizar datos de un integrante");
				CenComponentes recordUpdate = new CenComponentes();
				recordUpdate.setFechabaja(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIdcomponente(Short.valueOf(tarjetaIntegrantesDeleteDTO[i].getIdComponente()));
				recordUpdate.setIdinstitucion(idInstitucion);
				recordUpdate.setIdpersona(Long.valueOf(tarjetaIntegrantesDeleteDTO[i].getIdPersona()));
				response = cenComponentesExtendsMapper.updateByPrimaryKeySelective(recordUpdate);

				LOGGER.info(
						"getCargos() / cenComponentesExtendsMapper.updateMember() -> Salida de cenComponentesExtendsMapper para actualizar datos de un integrante");

				updateResponseDTO.setStatus(SigaConstants.OK);
				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("getCargos() / cenComponentesExtendsMapper.updateMember() -> "
							+ updateResponseDTO.getStatus() + ". No se pudo actualizar datos de un integrantes");

				}
			}

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("getCargos() / admUsuariosExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus()
					+ ". No existen ningún usuario en base de datos");
		}

		LOGGER.info("updateMember() -> Salida del servicio para actualizar información de integrantes");
		return updateResponseDTO;
	}

	@Override
	public StringDTO provinciaColegio(StringDTO idInstitucionIntegrante, HttpServletRequest request) {
		StringDTO idProvincia = new StringDTO();

		idProvincia = cenInstitucionExtendsMapper.selectProvinciaColegio(idInstitucionIntegrante);

		return idProvincia;
	}

	protected CenCliente rellenarInsertCenCliente(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,
			AdmUsuarios usuario) {
		CenCliente record = new CenCliente();

		record.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));
		record.setIdinstitucion(Short.valueOf(tarjetaIntegrantesCreateDTO.getColegio()));
		record.setFechaalta(new Date());
		record.setCaracter("P");
		record.setPublicidad(SigaConstants.DB_FALSE);
		record.setGuiajudicial(SigaConstants.DB_FALSE);
		record.setComisiones(SigaConstants.DB_FALSE);
		record.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setIdlenguaje(usuario.getIdlenguaje());
		record.setExportarfoto(SigaConstants.DB_FALSE);

		return record;
	}

	protected CenCliente rellenarInsertCenClienteUpdate(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO,
			AdmUsuarios usuario) {
		CenCliente record = new CenCliente();

		record.setIdpersona(Long.valueOf(tarjetaIntegrantesUpdateDTO.getIdPersonaComponente()));
		record.setIdinstitucion(Short.valueOf(tarjetaIntegrantesUpdateDTO.getColegio()));
		record.setFechaalta(new Date());
		record.setCaracter("P");
		record.setPublicidad(SigaConstants.DB_FALSE);
		record.setGuiajudicial(SigaConstants.DB_FALSE);
		record.setComisiones(SigaConstants.DB_FALSE);
		record.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setIdlenguaje(usuario.getIdlenguaje());
		record.setExportarfoto(SigaConstants.DB_FALSE);

		return record;
	}

	protected CrearPersonaDTO rellenarInsertCenPersona(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,
			AdmUsuarios usuario) {
		CrearPersonaDTO crearPersonaDTO = new CrearPersonaDTO();

		crearPersonaDTO.setNombre(tarjetaIntegrantesCreateDTO.getNombre());
		crearPersonaDTO.setApellido1(tarjetaIntegrantesCreateDTO.getApellidos1());
		crearPersonaDTO.setApellido2(tarjetaIntegrantesCreateDTO.getApellidos2());
		crearPersonaDTO.setNif(tarjetaIntegrantesCreateDTO.getNifCif());
		crearPersonaDTO.setTipoIdentificacion(tarjetaIntegrantesCreateDTO.getTipoIdentificacion());

		return crearPersonaDTO;
	}

	protected CenNocolegiado rellenarInsertCenNoColegiado(AdmUsuarios usuario, Long idPersona, Short idInstitucion) {
		CenNocolegiado cenNocolegiado = new CenNocolegiado();
		cenNocolegiado.setFechamodificacion(new Date());
		cenNocolegiado.setIdpersona(idPersona);
		cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
		cenNocolegiado.setIdinstitucion(idInstitucion);
		cenNocolegiado.setTipo("1");
		cenNocolegiado.setSociedadsj("0");
		return cenNocolegiado;
	}
}
