package org.itcgae.siga.cen.services.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosLiquidacionIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosLiquidacionIntegrantesSearchItem;
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
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcs;
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcsExample;
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcsKey;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenComponentesMapper;
import org.itcgae.siga.db.mappers.CenHistoricoLiquidacionsjcsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegioprocuradorExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenHistoricoLiquidacionsjcsExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaDatosIntegrantesServiceImpl implements ITarjetaDatosIntegrantesService {

	private Logger LOGGER = Logger.getLogger(TarjetaDatosIntegrantesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;
	
	@Autowired
	private CenComponentesMapper cenComponentesMapper;

	@Autowired
	private CenColegioprocuradorExtendsMapper cenColegioprocuradorExtendsMapper;

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
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private CenHistoricoLiquidacionsjcsExtendsMapper cenHistoricoLiquidacionsjcsExtendsMapper;
	
	@Autowired 
	private CenHistoricoLiquidacionsjcsMapper cenHistoricoLiquidacionsjcsMapper;

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
	public ComboDTO getColegios(HttpServletRequest request) {
		LOGGER.info("getProvinces() -> Entrada al servicio para búsqueda de las provincias");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Entrada a cenProvinciasExtendsMapper para obtener listado de provincias ");
		comboItems = cenColegioprocuradorExtendsMapper.selectDistinctColegios();
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

						updateResponseDTO.setStatus(SigaConstants.OK);
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

			// 1. Ya existe un idpersona para el nuevo integrante
			if (!tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante().equals("")) {
				int responseCenCliente = 1; // se puede crear o no => se inicializa a 1
				int responseCenComponentes = 0; // se debe crear siempre
				int responseInsertNoColegiado = 1;

				// 1.1 Comprobamos que existe en tabla cen_cliente
				CenCliente cenCliente = new CenCliente();
				CenClienteKey key = new CenClienteKey();
				key.setIdinstitucion(Short.valueOf(tarjetaIntegrantesCreateDTO.getColegio()));
				key.setIdpersona(Long.valueOf(tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante()));
				cenCliente = cenClienteMapper.selectByPrimaryKey(key);

				// 1.2 Si no existe, se crea un registro
				if (null == cenCliente) {
					CenCliente record = new CenCliente();
					record = rellenarInsertCenCliente(tarjetaIntegrantesCreateDTO, usuario);
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
					}

					if (responseCenComponentes == 1) {
						updateResponseDTO.setStatus(SigaConstants.OK);
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
						}

						if (responseCenComponentes == 1) {
							updateResponseDTO.setStatus(SigaConstants.OK);
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

	@Override
	public DatosLiquidacionIntegrantesSearchDTO listadoHistoricoLiquidacion(DatosLiquidacionIntegrantesSearchItem datosLiquidacion, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.debug("listadoHistoricoLiquidacion() -> Entrada al servicio para la búsqueda de las altas y bajas que ha tenido el colegiado referentes al pago de SJCS a través de la sociedad");

		List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacionItem = new ArrayList<DatosLiquidacionIntegrantesSearchItem>();
		DatosLiquidacionIntegrantesSearchDTO datosLiquidacionDTO = new DatosLiquidacionIntegrantesSearchDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"listadoHistoricoLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.debug(
					"listadoHistoricoLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.debug(
						"listadoHistoricoLiquidacion() / cenHistoricoLiquidacionsjcsExtendsMapper.selectLiquidacion() -> Entrada a cenHistoricoLiquidacionsjcsExtendsMapper para busqueda de histórico de liquidacion");
				datosLiquidacion.setIdInstitucion(idInstitucion.toString());
				datosLiquidacionItem = cenHistoricoLiquidacionsjcsExtendsMapper.selectLiquidacion(datosLiquidacion);
				LOGGER.debug(
						"listadoHistoricoLiquidacion() / cenHistoricoLiquidacionsjcsExtendsMapper.selectLiquidacion() -> Salida de cenHistoricoLiquidacionsjcsExtendsMapper");

				datosLiquidacionDTO.setDatosLiquidacionItem(datosLiquidacionItem);
			} else {
				LOGGER.warn(
						"listadoHistoricoLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("listadoHistoricoLiquidacion() -> idInstitucion del token nula");
		}

		LOGGER.debug("listadoHistoricoLiquidacion() -> Salida del servicio para buscar las altas y bajas que ha tenido el colegiado referente al pago SJCS a través de la sociedad");
		
		return datosLiquidacionDTO;
	}
	
	
	    @Transactional
		public DeleteResponseDTO eliminarLiquidacion(List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacion, HttpServletRequest request) {
			// TODO Auto-generated method stub
			LOGGER.debug("eliminarLiquidacion() -> Entrada al servicio para eliminar el pago liquidado referente a una sociedad");

			DeleteResponseDTO dpd = new DeleteResponseDTO();
			AdmUsuarios usuario = new AdmUsuarios();
			int response = 0;
			int updateSociedad = 0;
			int responseUpdateFecha = 0;
			
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.debug(
						"eliminarLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.debug(
						"eliminarLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					LOGGER.debug(
							"eliminarLiquidacion() / cenHistoricoLiquidacionsjcsMapper.deleteByPrimaryKey() -> Entrada a cenHistoricoLiquidacionsjcsMapper para borrar la liquidacion");
					if(datosLiquidacion != null) {
						
						DatosLiquidacionIntegrantesSearchItem objetoNuevo = new DatosLiquidacionIntegrantesSearchItem();
						DatosLiquidacionIntegrantesSearchItem objetoAnterior = new DatosLiquidacionIntegrantesSearchItem();
							
						for(DatosLiquidacionIntegrantesSearchItem Liquidacion: datosLiquidacion) {
							
							if(Liquidacion.isAnterior()) {
								 objetoAnterior = new DatosLiquidacionIntegrantesSearchItem();
								 objetoAnterior.setAnterior(Liquidacion.isAnterior());
								 objetoAnterior.setFechaFin(Liquidacion.getFechaFin());
								 objetoAnterior.setFechaInicio(Liquidacion.getFechaInicio());
								 objetoAnterior.setFechaModificacion(Liquidacion.getFechaModificacion());
								 objetoAnterior.setIdComponente(Liquidacion.getIdComponente());
								 objetoAnterior.setIdHistorico(Liquidacion.getIdHistorico());
								 objetoAnterior.setIdInstitucion(Liquidacion.getIdInstitucion());
								 objetoAnterior.setIdPersona(Liquidacion.getIdPersona());
								 objetoAnterior.setObservaciones(Liquidacion.getObservaciones());
								 objetoAnterior.setSociedad(Liquidacion.getSociedad());
								 objetoAnterior.setUsuModificacion(Liquidacion.getUsuModificacion());
							}
							
							if(!Liquidacion.isAnterior()) {
								 objetoNuevo = new DatosLiquidacionIntegrantesSearchItem();
								 objetoNuevo.setAnterior(Liquidacion.isAnterior());
								 objetoNuevo.setFechaFin(Liquidacion.getFechaFin());
								 objetoNuevo.setFechaInicio(Liquidacion.getFechaInicio());
								 objetoNuevo.setFechaModificacion(Liquidacion.getFechaModificacion());
								 objetoNuevo.setIdComponente(Liquidacion.getIdComponente());
								 objetoNuevo.setIdHistorico(Liquidacion.getIdHistorico());
								 objetoNuevo.setIdInstitucion(Liquidacion.getIdInstitucion());
								 objetoNuevo.setIdPersona(Liquidacion.getIdPersona());
								 objetoNuevo.setObservaciones(Liquidacion.getObservaciones());
								 objetoNuevo.setSociedad(Liquidacion.getSociedad());
								 objetoNuevo.setUsuModificacion(Liquidacion.getUsuModificacion());
							}
						}
					
						
					CenHistoricoLiquidacionsjcsKey datos = new CenHistoricoLiquidacionsjcsKey();
					datos.setIdhistorico(Short.parseShort(objetoNuevo.getIdHistorico()));
					datos.setIdinstitucion(Short.parseShort(objetoNuevo.getIdInstitucion()));
					datos.setIdpersona(Long.parseLong(objetoNuevo.getIdPersona()));
					datos.setIdcomponente(Short.parseShort(objetoNuevo.getIdComponente()));
					response = cenHistoricoLiquidacionsjcsMapper.deleteByPrimaryKey(datos);
					
					LOGGER.debug(
							"eliminarLiquidacion() / cenComponentesExtendsMapper.selectIntegrantes() -> Salida de cenHistoricoLiquidacionsjcsMapper para borrar la liquidacion");

					if(response == 1) {
						
						//hacer el update de ese componente con esa persona en el campo sociedad a 1 a lo contrario que estaba
						CenComponentes updateRecord = new CenComponentes();
						
						updateRecord.setFechamodificacion(new Date());
						
						if(objetoAnterior.getSociedad().equals("Alta")) {
							updateRecord.setSociedad("0");
						}else {
							updateRecord.setSociedad("1");
						}
						
						updateRecord.setIdinstitucion(idInstitucion);
						updateRecord.setIdcomponente(Short.parseShort(objetoAnterior.getIdComponente()));
						updateRecord.setIdpersona(Long.parseLong(objetoAnterior.getIdPersona()));
										
						updateSociedad = cenComponentesMapper.updateByPrimaryKeySelective(updateRecord);
						
						if(updateSociedad == 1) {
							
							CenHistoricoLiquidacionsjcs updateFechaAnterior = new CenHistoricoLiquidacionsjcs();
							updateFechaAnterior.setIdhistorico(Short.parseShort(objetoAnterior.getIdHistorico()));
							updateFechaAnterior.setIdinstitucion(Short.parseShort(objetoAnterior.getIdInstitucion()));
							updateFechaAnterior.setIdpersona(Long.parseLong(objetoAnterior.getIdPersona()));
							updateFechaAnterior.setIdcomponente(Short.parseShort(objetoAnterior.getIdComponente()));
							updateFechaAnterior.setFechafin(null);
							responseUpdateFecha = cenHistoricoLiquidacionsjcsMapper.updateByPrimaryKeySelective(updateFechaAnterior);
						}
					}
				}
					
					dpd.setStatus(SigaConstants.OK);
					if (response == 0 || updateSociedad == 0 || responseUpdateFecha == 0) {
						dpd.setStatus(SigaConstants.KO);
						LOGGER.warn("eliminarLiquidacion() / cenHistoricoLiquidacionsjcsMapper.deleteByPrimaryKey() -> "
								+ dpd.getStatus() + ". No se pudo eliminar la liquidacion");

					}
				}

			} else {
				dpd.setStatus(SigaConstants.KO);
				LOGGER.warn("eliminarLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> " + dpd.getStatus()
						+ ". No existen ningún usuario en base de datos");
			}

			LOGGER.debug("eliminarLiquidacion() -> Salida del servicio para eliminar una liquidacion");
			return dpd;
		}
	    
	    @Transactional
		public InsertResponseDTO insertHistoricoLiquidacion(List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacion, HttpServletRequest request) {
			// TODO Auto-generated method stub
			LOGGER.debug("insertHistoricoLiquidacion() -> Entrada al servicio para insertar un histórico");

			InsertResponseDTO ins = new InsertResponseDTO();
			int response = 0;
			int updateFecha = 0;
			int updateSociedad = 0;
			
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.debug(
						"insertHistoricoLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.debug(
						"insertHistoricoLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					LOGGER.debug(
							"insertHistoricoLiquidacion() / cenHistoricoLiquidacionsjcsMapper.updateSociedadComponentes() -> Entrada a cenHistoricoLiquidacionsjcsMapper para actualizar el campo sociedad");
					
				
			if(datosLiquidacion != null) {
				
				DatosLiquidacionIntegrantesSearchItem objetoNuevo = new DatosLiquidacionIntegrantesSearchItem();
				DatosLiquidacionIntegrantesSearchItem objetoAnterior = new DatosLiquidacionIntegrantesSearchItem();
					
				for(DatosLiquidacionIntegrantesSearchItem Liquidacion: datosLiquidacion) {
					
					if(Liquidacion.isAnterior()) {
						 objetoAnterior = new DatosLiquidacionIntegrantesSearchItem();
						 objetoAnterior.setAnterior(Liquidacion.isAnterior());
						 objetoAnterior.setFechaFin(Liquidacion.getFechaFin());
						 objetoAnterior.setFechaInicio(Liquidacion.getFechaInicio());
						 objetoAnterior.setFechaModificacion(Liquidacion.getFechaModificacion());
						 objetoAnterior.setIdComponente(Liquidacion.getIdComponente());
						 objetoAnterior.setIdHistorico(Liquidacion.getIdHistorico());
						 objetoAnterior.setIdInstitucion(Liquidacion.getIdInstitucion());
						 objetoAnterior.setIdPersona(Liquidacion.getIdPersona());
						 objetoAnterior.setObservaciones(Liquidacion.getObservaciones());
						 objetoAnterior.setSociedad(Liquidacion.getSociedad());
						 objetoAnterior.setUsuModificacion(Liquidacion.getUsuModificacion());
					}
					
					if(!Liquidacion.isAnterior()) {
						 objetoNuevo = new DatosLiquidacionIntegrantesSearchItem();
						 objetoNuevo.setAnterior(Liquidacion.isAnterior());
						 objetoNuevo.setFechaFin(Liquidacion.getFechaFin());
						 objetoNuevo.setFechaInicio(Liquidacion.getFechaInicio());
						 objetoNuevo.setFechaModificacion(Liquidacion.getFechaModificacion());
						 objetoNuevo.setIdComponente(Liquidacion.getIdComponente());
						 objetoNuevo.setIdHistorico(Liquidacion.getIdHistorico());
						 objetoNuevo.setIdInstitucion(Liquidacion.getIdInstitucion());
						 objetoNuevo.setIdPersona(Liquidacion.getIdPersona());
						 objetoNuevo.setObservaciones(Liquidacion.getObservaciones());
						 objetoNuevo.setSociedad(Liquidacion.getSociedad());
						 objetoNuevo.setUsuModificacion(Liquidacion.getUsuModificacion());
					}
				}
				
				
					CenComponentes updateRecord = new CenComponentes();
					
					updateRecord.setFechamodificacion(new Date());
					
					if(objetoNuevo.getSociedad().equals("1")) {
						updateRecord.setSociedad("1");
					}else {
						updateRecord.setSociedad("0");
					}
					
					updateRecord.setIdinstitucion(idInstitucion);
					updateRecord.setIdcomponente(Short.parseShort(objetoNuevo.getIdComponente()));
					updateRecord.setIdpersona(Long.parseLong(objetoNuevo.getIdPersona()));
									
					updateSociedad = cenComponentesMapper.updateByPrimaryKeySelective(updateRecord);
					
					
					//ahora voy a meter en el objeto anterior, como fecha fin, la fecha de inicio del objeto nuevo
					if(objetoAnterior.getFechaInicio() != null && objetoNuevo != null && updateSociedad == 1) {
						//primero hago un selectByExample para averiguar el idHistorico y para averiguar que existe
						CenHistoricoLiquidacionsjcsExample select = new CenHistoricoLiquidacionsjcsExample();
						
						select.createCriteria().andIdhistoricoEqualTo(Short.parseShort(objetoAnterior.getIdHistorico())).andIdinstitucionEqualTo(idInstitucion).andIdcomponenteEqualTo(Short.parseShort(objetoAnterior.getIdComponente())).andIdpersonaEqualTo(Long.parseLong(objetoAnterior.getIdPersona())).andFechainicioEqualTo(objetoAnterior.getFechaInicio()).andUsumodificacionEqualTo(objetoAnterior.getUsuModificacion());
						
						List<CenHistoricoLiquidacionsjcs> objetoSelect = cenHistoricoLiquidacionsjcsMapper.selectByExample(select);
						
						if(objetoSelect != null) {
							
							CenHistoricoLiquidacionsjcs upd = new CenHistoricoLiquidacionsjcs();
							
							upd.setIdinstitucion(idInstitucion);
							upd.setFechamodificacion(new Date());
							upd.setFechafin(objetoNuevo.getFechaInicio());
							upd.setUsumodificacion(objetoSelect.get(0).getUsumodificacion());
							upd.setIdcomponente(objetoSelect.get(0).getIdcomponente());
							upd.setIdpersona(objetoSelect.get(0).getIdpersona());	
							upd.setIdhistorico(objetoSelect.get(0).getIdhistorico());
							updateFecha = cenHistoricoLiquidacionsjcsMapper.updateByPrimaryKeySelective(upd);
						}
						
					}

					if((updateFecha == 1 && updateSociedad == 1) || updateSociedad == 1) { 
						CenHistoricoLiquidacionsjcs record = new CenHistoricoLiquidacionsjcs();
						
						record.setIdinstitucion(idInstitucion);
						record.setFechamodificacion(new Date());
						record.setFechainicio(objetoNuevo.getFechaInicio());
						record.setObservaciones(objetoNuevo.getObservaciones());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setIdcomponente(Short.parseShort(objetoNuevo.getIdComponente()));
						record.setIdpersona(Long.parseLong(objetoNuevo.getIdPersona()));	
						record.setSociedad(updateRecord.getSociedad());
						
						response = cenHistoricoLiquidacionsjcsMapper.insertSelective(record);
					}
				}
			
			
					
					LOGGER.debug(
							"insertHistoricoLiquidacion() / cenComponentesExtendsMapper.updateSociedadComponentes() -> Salida de cenHistoricoLiquidacionsjcsMapper para actualizar el campo sociedad");

					ins.setStatus(SigaConstants.OK);
					if ((updateFecha == 0 && updateSociedad == 0) || updateSociedad == 0 || response == 0) {
						ins.setStatus(SigaConstants.KO);
						LOGGER.warn("insertHistoricoLiquidacion() / cenHistoricoLiquidacionsjcsMapper.deleteByPrimaryKey() -> "
								+ ins.getStatus() + ". No se pudo insertar la liquidacion");

					}
				}

			} else {
				ins.setStatus(SigaConstants.KO);
				LOGGER.warn("insertHistoricoLiquidacion() / admUsuariosExtendsMapper.selectByExample() -> " + ins.getStatus()
						+ ". No existen ningún usuario en base de datos");
			}

			LOGGER.debug("insertHistoricoLiquidacion() -> Salida del servicio para eliminar una liquidacion");
			return ins;
		}
	    
	    @Override
		public Boolean buscarPagosColegiados(DatosLiquidacionIntegrantesSearchItem datosLiquidacion, HttpServletRequest request) {
			// TODO Auto-generated method stub
			LOGGER.debug("buscarPagosColegiados() -> Entrada al servicio para la búsqueda de los pagos de un colegiado en un periodo de fecha");
			int datosLiquidacionItem = 0;
			Boolean eliminar = false;

			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.debug(
						"buscarPagosColegiados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.debug(
						"buscarPagosColegiados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					LOGGER.debug(
							"buscarPagosColegiados() / cenHistoricoLiquidacionsjcsExtendsMapper.buscarPagosColegiados() -> Entrada para la búsqueda de pagos");
					datosLiquidacion.setIdInstitucion(idInstitucion.toString());
					datosLiquidacionItem = cenHistoricoLiquidacionsjcsExtendsMapper.buscarPagosColegiados(datosLiquidacion);
					LOGGER.debug(
							"buscarPagosColegiados() / cenHistoricoLiquidacionsjcsExtendsMapper.buscarPagosColegiados() -> Salida para la búsqueda de pagos");

					if(datosLiquidacionItem > 0) {
						eliminar = false;
					}else {
						eliminar = true;
					}
				} else {
					LOGGER.warn(
							"buscarPagosColegiados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("buscarPagosColegiados() -> idInstitucion del token nula");
			}

			LOGGER.debug("buscarPagosColegiados() -> Salida al servicio para la búsqueda de los pagos de un colegiado en un periodo de fecha");
			
			return eliminar;
		}

	}