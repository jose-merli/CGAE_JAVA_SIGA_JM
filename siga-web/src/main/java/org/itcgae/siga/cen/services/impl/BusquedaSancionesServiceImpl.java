package org.itcgae.siga.cen.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.IBusquedaSancionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenSancion;
import org.itcgae.siga.db.entities.CenSancionExample;
import org.itcgae.siga.db.entities.CenSancionKey;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSancionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposancionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientCENSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegiados.info.redabogacia.ColegiadoRequestDocument;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument.ColegiadoRequest;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse.Colegiado;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse.Colegiado.Sancion;
import com.colegiados.info.redabogacia.IdentificacionType;

@Service
public class BusquedaSancionesServiceImpl implements IBusquedaSancionesService {

	private Logger LOGGER = Logger.getLogger(BusquedaSancionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenTiposancionExtendsMapper cenTiposancionExtendsMapper;

	@Autowired
	private CenSancionExtendsMapper cenSancionExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private AdmConfigMapper admConfigMapper;

	@Autowired
	private ClientCENSO clientCENSO;

	@Override
	public ComboDTO getComboTipoSancion(HttpServletRequest request) {
		LOGGER.info("getComboTipoSancion() -> Entrada al servicio para obtener los tipos de sanciones");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info(
				"getComboTipoSancion() / cenTiposancionExtendsMapper.getComboTipoSancion() -> Entrada a cenTiposancionMapper para obtener los diferentes tipos de sanciones");
		comboItems = cenTiposancionExtendsMapper.getComboTipoSancion();
		LOGGER.info(
				"getComboTipoSancion() / cenTiposancionExtendsMapper.getComboTipoSancion() -> Salida de cenTiposancionMapper para obtener los diferentes tipos de sanciones");

		if (!comboItems.equals(null) && comboItems.size() > 0) {

			// añade elemento vacio al principio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			comboItems.add(0, comboItem);

			comboDTO.setCombooItems(comboItems);
		}

		LOGGER.info("getComboTipoSancion() -> Salida del servicio para obtener los tipos de sanciones");
		return comboDTO;
	}

	@Override
	public BusquedaSancionesDTO searchBusquedaSanciones(int numPagina,
			BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request) {
		LOGGER.info("searchBusquedaSanciones() -> Entrada al servicio para la búsqueda por filtros de sanciones");

		List<BusquedaSancionesItem> busquedaSancionesItems = new ArrayList<BusquedaSancionesItem>();
		BusquedaSancionesDTO busquedaSancionesDTO = new BusquedaSancionesDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				Colegiado colegiado = null;

				try {
					AdmConfigExample example = new AdmConfigExample();
					example.createCriteria().andClaveEqualTo("url.ws.censo");
					List<AdmConfig> config = admConfigMapper.selectByExample(example);

					if (null != config && config.size() > 0) {

						// Busqueda por nif
						ColegiadoRequest colegiadoRequest = ColegiadoRequest.Factory.newInstance();
						String tipo = isNifNie(busquedaSancionesSearchDTO.getNif());
						// Rellenamos la peticion
						IdentificacionType identificacion = IdentificacionType.Factory.newInstance();
						if (tipo.equals("NIF")) {
							identificacion.setNIF(busquedaSancionesSearchDTO.getNif());
						} else if (tipo.equals("NIE")) {
							identificacion.setNIE(busquedaSancionesSearchDTO.getNif());
						}
						colegiadoRequest.setIdentificacion(identificacion);

						ColegiadoRequestDocument colegiadoRequestDocument = ColegiadoRequestDocument.Factory
								.newInstance();
						colegiadoRequestDocument.setColegiadoRequest(colegiadoRequest);
						ColegiadoResponseDocument colegiadoResponseDocument = null;

						colegiadoResponseDocument = clientCENSO
								.busquedaColegiadoConIdentificacion(colegiadoRequestDocument, config.get(0).getValor());
						ColegiadoResponse colegiadoResponse = colegiadoResponseDocument.getColegiadoResponse();
						colegiado = colegiadoResponse.getColegiado();

						if (null != colegiado) {
							if (null != colegiado.getSancionArray() && colegiado.getSancionArray().length > 0) {

								for (Sancion sancionesColegiados : colegiado.getSancionArray()) {

									BusquedaSancionesItem busquedaSancionesItem = new BusquedaSancionesItem();
									String nombreCompleto = "";

									nombreCompleto = colegiado.getDatosPersonales().getNombre() + " ";

									if (null != colegiado.getDatosPersonales().getApellido1()) {
										nombreCompleto += colegiado.getDatosPersonales().getApellido1() + " ";
									} else {
										nombreCompleto += "";
									}
									if (null != colegiado.getDatosPersonales().getApellido2()) {
										nombreCompleto += colegiado.getDatosPersonales().getApellido2();
									} else {
										nombreCompleto += "";
									}

									busquedaSancionesItem.setNombre(nombreCompleto);

									busquedaSancionesItem.setNif(busquedaSancionesSearchDTO.getNif());
									busquedaSancionesItem.setTexto(sancionesColegiados.getDescripcion());

									busquedaSancionesItem
											.setColegio(sancionesColegiados.getColegio().getDescripcionColegio());
									busquedaSancionesItem
											.setTipoSancion(sancionesColegiados.getTipoSancion().getDescripcion());
									busquedaSancionesItem
											.setRefColegio(sancionesColegiados.getColegio().getCodigoColegio());
									busquedaSancionesItem
											.setColegio(sancionesColegiados.getColegio().getDescripcionColegio());

									SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
									if (sancionesColegiados.getFechaInicio() != null) {
										String fechaDesde = format
												.format(sancionesColegiados.getFechaInicio().getTime());
										busquedaSancionesItem.setFechaDesde(fechaDesde);
									}

									if (sancionesColegiados.getFechaFin() != null) {
										String fechaHasta = format.format(sancionesColegiados.getFechaFin().getTime());
										busquedaSancionesItem.setFechaHasta(fechaHasta);
									}

									if (sancionesColegiados.getFechaRehabilitacion() != null) {
										String fechaRehabilitado = format
												.format(sancionesColegiados.getFechaRehabilitacion().getTime());
										busquedaSancionesItem.setFechaRehabilitado(fechaRehabilitado);
										
										if(sancionesColegiados.getFechaRehabilitacion().after(new Date())) {
											busquedaSancionesItem.setRehabilitado(SigaConstants.SI);
										}else {
											busquedaSancionesItem.setRehabilitado(SigaConstants.NO);
										}
									}else {
										busquedaSancionesItem.setRehabilitado(SigaConstants.NO);
									}

									if (sancionesColegiados.getFechaFirmeza() != null) {
										String fechaFirmeza = format
												.format(sancionesColegiados.getFechaFirmeza().getTime());
										busquedaSancionesItem.setFechaFirmeza(fechaFirmeza);
									}
									
									busquedaSancionesItem.setFirmeza(SigaConstants.SI);

									busquedaSancionesItems.add(busquedaSancionesItem);

								}

								Collections.sort(busquedaSancionesItems);
								
								busquedaSancionesDTO.setBusquedaSancionesItem(busquedaSancionesItems);

							}
						}
					}
				} catch (Exception e) {
					LOGGER.error("Error en la llamada a busqueda de colegiados.", e);
				}

			} else {
				LOGGER.warn(
						"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchBusquedaSanciones() -> idInstitucion del token nula");
		}

		LOGGER.info("searchLegalPersons() -> Salida del servicio para la búsqueda por filtros de sanciones");
		return busquedaSancionesDTO;
	}

	@Override
	public InsertResponseDTO insertSanction(BusquedaSancionesItem busquedaSancionesItem, HttpServletRequest request) {
		LOGGER.info("insertSanction() -> Entrada al servicio para insertar una sanción");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"insertSanction() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"insertSanction() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {

					Long idPersona = Long.valueOf(busquedaSancionesItem.getIdPersona());

					CenSancion cenSancion = fillCenSancion(usuario, idPersona, busquedaSancionesItem);

					NewIdDTO idSancion = cenSancionExtendsMapper
							.getMaxIdSancion(String.valueOf(usuario.getIdinstitucion()), String.valueOf(idPersona));

					if (idSancion == null) {
						cenSancion.setIdsancion((long) 1);
					} else {
						int idS = Integer.parseInt(idSancion.getNewId()) + 1;
						cenSancion.setIdsancion((long) idS);
					}

					LOGGER.info(
							"insertSanction() / cenSancionExtendsMapper.insertSelective() -> Entrada a cenSancionExtendsMapper para insertar la sanción");
					int response = cenSancionExtendsMapper.insertSelective(cenSancion);
					LOGGER.info(
							"insertSanction() / cenSancionExtendsMapper.insertSelective() -> Salida a cenSancionExtendsMapper para insertar la sanción");

					if (response == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn("insertSanction() / cenSancionExtendsMapper.insertSelective() -> "
								+ insertResponseDTO.getStatus() + ". No se pudo insertar la sanción");
					} else {

						if ((busquedaSancionesItem.getFechaFirmezaDate() != null
								&& busquedaSancionesItem.getFirmeza().equals("No"))
								&& !String.valueOf(cenSancion.getIdinstitucion())
										.equals(SigaConstants.InstitucionGeneral)) {

							LOGGER.info(
									"insertSanction() / cenSancionExtendsMapper.selectByPrimaryKey() -> Entrada a cenSancionExtendsMapper para ver si existe en la tabla");

							CenSancionKey cenSancionKey = new CenSancionKey();
							cenSancionKey.setIdinstitucion(cenSancion.getIdinstitucion());
							cenSancionKey.setIdpersona(Long.valueOf(cenSancion.getIdpersona()));
							cenSancionKey.setIdsancion(Long.valueOf(cenSancion.getIdsancion()));

							CenSancion cenSancionRegistry = cenSancionExtendsMapper.selectByPrimaryKey(cenSancionKey);
							LOGGER.info(
									"insertSanction() / cenSancionExtendsMapper.selectByPrimaryKey() -> Entrada a cenSancionExtendsMapper para ver si existe en la tabla");

							if (null != cenSancionRegistry) {
								CenSancion registry = new CenSancion();

								registry = cenSancion;

								registry.setIdsancionorigen(cenSancion.getIdsancion());
								registry.setIdinstitucion(Short.valueOf(SigaConstants.InstitucionGeneral));
								registry.setIdinstitucionsancion(usuario.getIdinstitucion());
								registry.setIdinstitucionorigen(usuario.getIdinstitucion());

								LOGGER.info(
										"insertSanction() / cenSancionExtendsMapper.insertSelective() -> Entrada a cenSancionExtendsMapper para insertar la sanción");
								int responseD = cenSancionExtendsMapper.insertSelective(registry);
								LOGGER.info(
										"insertSanction() / cenSancionExtendsMapper.insertSelective() -> Salida a cenSancionExtendsMapper para insertar la sanción");

								if (responseD == 0) {
									insertResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn("insertSanction() / cenSancionExtendsMapper.insertSelective() -> "
											+ insertResponseDTO.getStatus() + ". No se pudo insertar la sanción");
								} else {
									insertResponseDTO.setStatus(SigaConstants.OK);
								}
							}

						} else {
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

					}

					// } else {
					// insertResponseDTO.setStatus(SigaConstants.KO);
					// }
				} catch (Exception e) {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("insertSanction() / cenSancionExtendsMapper.insertSelective() -> "
							+ insertResponseDTO.getStatus() + ". No se pudo insertar la sanción");
				}
			}
		}

		LOGGER.info("insertSanction() -> Salida del servicio para insertar una sanción");
		return insertResponseDTO;
	}

	private CenSancion fillCenSancion(AdmUsuarios usuario, Long idPersona,
			BusquedaSancionesItem busquedaSancionesItem) {

		CenSancion cenSancion = new CenSancion();

		if (busquedaSancionesItem.getIdSancion() != null) {
			cenSancion.setIdsancion(Long.valueOf(busquedaSancionesItem.getIdSancion()));
		}

		if (busquedaSancionesItem.getTipoSancion() != null) {
			cenSancion.setIdtiposancion(Short.valueOf(busquedaSancionesItem.getTipoSancion()));
		}

		if (busquedaSancionesItem.getRefColegio() != null) {
			cenSancion.setRefcolegio(busquedaSancionesItem.getRefColegio());
		}

		if (busquedaSancionesItem.getFechaAcuerdoDate() != null) {
			cenSancion.setFechaacuerdo(busquedaSancionesItem.getFechaAcuerdoDate());
		}

		if (busquedaSancionesItem.getFechaFirmezaDate() != null) {
			cenSancion.setFechafirmeza(busquedaSancionesItem.getFechaFirmezaDate());
		}

		if (busquedaSancionesItem.getFechaDesdeDate() != null) {
			cenSancion.setFechainicio(busquedaSancionesItem.getFechaDesdeDate());
		}

		if (busquedaSancionesItem.getFechaHastaDate() != null) {
			cenSancion.setFechafin(busquedaSancionesItem.getFechaHastaDate());
		}

		if (busquedaSancionesItem.getFechaRehabilitadoDate() != null) {
			cenSancion.setFecharehabilitado(busquedaSancionesItem.getFechaRehabilitadoDate());
		}

		if (busquedaSancionesItem.getFechaArchivadaDate() != null) {
			cenSancion.setFechaarchivada(busquedaSancionesItem.getFechaArchivadaDate());
		}

		if (busquedaSancionesItem.getFirmeza() != null && busquedaSancionesItem.getFirmeza().equals("Sí")) {
			cenSancion.setChkfirmeza("1");
		} else {
			cenSancion.setChkfirmeza("0");
		}

		if (busquedaSancionesItem.getRehabilitado() != null && busquedaSancionesItem.getRehabilitado().equals("Sí")) {
			cenSancion.setChkrehabilitado("1");
		} else {
			cenSancion.setChkrehabilitado("0");
		}

		if (busquedaSancionesItem.getArchivada() != null && busquedaSancionesItem.getArchivada().equals("Sí")) {
			cenSancion.setChkarchivada("1");
		} else {
			cenSancion.setChkarchivada("0");
		}

		cenSancion.setFechamodificacion(new Date());

		if (usuario.getIdinstitucion() != null) {
			cenSancion.setIdinstitucion(usuario.getIdinstitucion());
		}

		if (busquedaSancionesItem.getIdColegio() != null) {
			cenSancion.setIdinstitucionsancion(Short.valueOf(busquedaSancionesItem.getIdColegio()));
		}

		cenSancion.setIdpersona(idPersona);

		if (busquedaSancionesItem.getTexto() != null) {
			cenSancion.setTexto(busquedaSancionesItem.getTexto());
		}

		if (busquedaSancionesItem.getObservaciones() != null) {
			cenSancion.setObservaciones(busquedaSancionesItem.getObservaciones());
		}

		if (usuario.getIdusuario() != null) {
			cenSancion.setUsumodificacion(usuario.getIdusuario());
		}

		return cenSancion;
	}

	@Override
	public UpdateResponseDTO updateSanction(BusquedaSancionesItem busquedaSancionesItem, HttpServletRequest request) {
		LOGGER.info("updateSanction() -> Entrada al servicio para insertar una sanción");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateSanction() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateSanction() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					LOGGER.info(
							"updateSanction() / cenSancionExtendsMapper.selectByPrimaryKey() -> Entrada a cenSancionExtendsMapper para ver si existe en la tabla");

					CenSancionKey cenSancionKey = new CenSancionKey();
					cenSancionKey.setIdinstitucion(usuario.getIdinstitucion());
					cenSancionKey.setIdpersona(Long.valueOf(busquedaSancionesItem.getIdPersona()));
					cenSancionKey.setIdsancion(Long.valueOf(busquedaSancionesItem.getIdSancion()));

					CenSancion cenSancion = cenSancionExtendsMapper.selectByPrimaryKey(cenSancionKey);
					LOGGER.info(
							"updateSanction() / cenSancionExtendsMapper.selectByPrimaryKey() -> Entrada a cenSancionExtendsMapper para ver si existe en la tabla");

					if (null != cenSancion) {

						if (busquedaSancionesItem.getIsRestablecer()) {
							cenSancion.setChkarchivada("0");
							cenSancion.setFechaarchivada(null);
							cenSancion.setFechamodificacion(new Date());
						} else {
							cenSancion = fillCenSancion(usuario, Long.valueOf(busquedaSancionesItem.getIdPersona()),
									busquedaSancionesItem);
						}

						LOGGER.info(
								"updateSanction() / cenSancionExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenSancionExtendsMapper para actualizar la sanción");
						int response = cenSancionExtendsMapper.updateByPrimaryKey(cenSancion);
						LOGGER.info(
								"updateSanction() / cenSancionExtendsMapper.updateByPrimaryKeySelective() -> Salida a cenSancionExtendsMapper para actualizar la sanción");

						if (response == 0) {
							updateResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn("updateSanction() / cenSancionExtendsMapper.updateByPrimaryKeySelective() -> "
									+ updateResponseDTO.getStatus() + ". No se pudo actualizar la sanción");
						} else {

							if ((busquedaSancionesItem.getFechaFirmezaDate() != null
									&& busquedaSancionesItem.getFirmeza().equals("No"))
									&& !String.valueOf(cenSancion.getIdinstitucion())
											.equals(SigaConstants.InstitucionGeneral)) {

								CenSancion registry = new CenSancion();
								LOGGER.info(
										"updateSanction() / cenSancionExtendsMapper.selectByPrimaryKey() -> Entrada a cenSancionExtendsMapper para ver si existe en la tabla");

								CenSancionExample cenSancionExample = new CenSancionExample();
								cenSancionExample.createCriteria()
										.andIdinstitucionEqualTo(Short.valueOf(SigaConstants.InstitucionGeneral))
										.andIdpersonaEqualTo(cenSancion.getIdpersona())
										.andIdsancionEqualTo(Long.valueOf(cenSancion.getIdsancion()));

								List<CenSancion> cenSancionD = cenSancionExtendsMapper
										.selectByExample(cenSancionExample);

								LOGGER.info(
										"updateSanction() / cenSancionESxtendsMapper.selectByPrimaryKey() -> Entrada a cenSancionExtendsMapper para ver si existe en la tabla");

								if (cenSancionD.isEmpty()) {

									registry = cenSancion;

									registry.setIdsancionorigen(cenSancion.getIdsancion());
									registry.setIdinstitucion(Short.valueOf(SigaConstants.InstitucionGeneral));
									registry.setIdinstitucionsancion(usuario.getIdinstitucion());
									registry.setIdinstitucionorigen(usuario.getIdinstitucion());

									LOGGER.info(
											"updateSanction() / cenSancionExtendsMapper.insertSelective() -> Entrada a cenSancionExtendsMapper para insertar la sanción");
									int responseD = cenSancionExtendsMapper.insertSelective(registry);
									LOGGER.info(
											"updateSanction() / cenSancionExtendsMapper.insertSelective() -> Salida a cenSancionExtendsMapper para insertar la sanción");

									if (responseD == 0) {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn("insertSanction() / cenSancionExtendsMapper.insertSelective() -> "
												+ updateResponseDTO.getStatus() + ". No se pudo insertar la sanción");
									} else {
										updateResponseDTO.setStatus(SigaConstants.OK);
									}
								} else {
									CenSancionExample cenSancionExample1 = new CenSancionExample();
									cenSancionExample1.createCriteria()
											.andIdinstitucionEqualTo(Short.valueOf(SigaConstants.InstitucionGeneral))
											.andIdpersonaEqualTo(cenSancion.getIdpersona())
											.andIdsancionEqualTo(Long.valueOf(cenSancion.getIdsancion()));

									cenSancion.setIdsancionorigen(cenSancion.getIdsancion());
									cenSancion.setIdinstitucion(Short.valueOf(SigaConstants.InstitucionGeneral));
									cenSancion.setIdinstitucionsancion(usuario.getIdinstitucion());
									cenSancion.setIdinstitucionorigen(usuario.getIdinstitucion());

									if (cenSancionD.get(0).getFechafin() != null)
										cenSancion.setFechafin(null);

									LOGGER.info(
											"updateSanction() / cenSancionExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenSancionExtendsMapper para actualizar la sanción");
									int responseDD = cenSancionExtendsMapper.updateByExample(cenSancion,
											cenSancionExample1);
									LOGGER.info(
											"updateSanction() / cenSancionExtendsMapper.updateByPrimaryKeySelective() -> Salida a cenSancionExtendsMapper para actualizar la sanción");

									if (responseDD == 0) {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"updateSanction() / cenSancionExtendsMapper.updateByPrimaryKeySelective() -> "
														+ updateResponseDTO.getStatus()
														+ ". No se pudo actualizar la sanción");
									} else {
										updateResponseDTO.setStatus(SigaConstants.OK);
									}
								}

							} else if (busquedaSancionesItem.getFechaFirmezaDate() == null) {
								CenSancionExample cenSancionExample = new CenSancionExample();
								cenSancionExample.createCriteria()
										.andIdinstitucionEqualTo(Short.valueOf(SigaConstants.InstitucionGeneral))
										.andIdpersonaEqualTo(cenSancion.getIdpersona())
										.andIdsancionEqualTo(Long.valueOf(cenSancion.getIdsancion()));

								List<CenSancion> cenSancionD = cenSancionExtendsMapper
										.selectByExample(cenSancionExample);

								if (!cenSancionD.isEmpty()) {
									CenSancionExample cenSancionExample1 = new CenSancionExample();
									cenSancionExample1.createCriteria()
											.andIdinstitucionEqualTo(Short.valueOf(SigaConstants.InstitucionGeneral))
											.andIdpersonaEqualTo(cenSancion.getIdpersona())
											.andIdsancionEqualTo(Long.valueOf(cenSancion.getIdsancion()));
									cenSancion.setIdinstitucion(Short.valueOf(SigaConstants.InstitucionGeneral));
									cenSancion.setFechafin(new Date());
									LOGGER.info(
											"updateSanction() / cenSancionExtendsMapper.updateByExample() -> Entrada a cenSancionExtendsMapper para actualizar la sanción");
									int responseDD = cenSancionExtendsMapper.updateByExample(cenSancion,
											cenSancionExample1);
									LOGGER.info(
											"updateSanction() / cenSancionExtendsMapper.updateByExample() -> Salida a cenSancionExtendsMapper para actualizar la sanción");

									if (responseDD == 0) {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn("updateSanction() / cenSancionExtendsMapper.updateByExample() -> "
												+ updateResponseDTO.getStatus() + ". No se pudo actualizar la sanción");
									} else {
										updateResponseDTO.setStatus(SigaConstants.OK);
									}
								}else {
									updateResponseDTO.setStatus(SigaConstants.OK);
								}
							} else {
								updateResponseDTO.setStatus(SigaConstants.OK);
							}
						}
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				} catch (Exception e) {
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}
		}

		LOGGER.info("updateSanction() -> Salida del servicio para insertar una sanción");
		return updateResponseDTO;
	}

	@Override
	public void archivarSancionesRehabilitado() {
		CenSancionExample cenSancionExample = new CenSancionExample();
		cenSancionExample.createCriteria().andChkarchivadaEqualTo("0").andFecharehabilitadoIsNotNull()
				.andFecharehabilitadoLessThan(new Date());
		List<CenSancion> cenSancionList = cenSancionExtendsMapper.selectByExample(cenSancionExample);

		if (cenSancionList != null && cenSancionList.size() > 0) {
			for (CenSancion cenSancion : cenSancionList) {

				cenSancion.setChkarchivada("1");
				cenSancion.setFechaarchivada(new Date());

				cenSancionExtendsMapper.updateByPrimaryKeySelective(cenSancion);
			}
		}

	}

	public static String isNifNie(String nif) {
		String tipo;
		if (nif.length() != 9) {
			return null;
		} else {
			// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
			if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y")
					|| nif.toUpperCase().startsWith("Z")) {
				nif = nif.substring(1);
				tipo = "NIE";
			} else {
				tipo = "NIF";
			}
		}

		Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if (m.matches()) {
			String letra = m.group(2);
			// Extraer letra del NIF
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			dni = dni % 23;
			String reference = letras.substring(dni, dni + 1);

			if (reference.equalsIgnoreCase(letra)) {
				return tipo;
			} else {
				return tipo;
			}
		} else
			return null;
	}

	@Override
	public BusquedaSancionesDTO searchBusquedaSancionesBBDD(int numPagina,
			BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchBusquedaSanciones() -> Entrada al servicio para la búsqueda por filtros de sanciones en bbdd");

		List<BusquedaSancionesItem> busquedaSancionesItems = new ArrayList<BusquedaSancionesItem>();
		BusquedaSancionesDTO busquedaSancionesDTO = new BusquedaSancionesDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				try {

					LOGGER.info(
							"searchBusquedaSanciones() / cenTiposancionExtendsMapper.searchBusquedaSanciones() -> Entrada a cenTiposancionExtendsMapper para busqueda de sanciones por filtro");

					busquedaSancionesItems = cenTiposancionExtendsMapper.searchBusquedaSanciones(
							busquedaSancionesSearchDTO, idLenguaje, String.valueOf(idInstitucion));

					busquedaSancionesDTO.setBusquedaSancionesItem(busquedaSancionesItems);

					LOGGER.info(
							"searchBusquedaSanciones() / cenTiposancionExtendsMapper.searchBusquedaSanciones() -> Salida de cenTiposancionExtendsMapper para busqueda de sanciones por filtro");

				} catch (Exception e) {
					LOGGER.error("Error en la llamada a busqueda de sanciones.", e);
				}

			} else {
				LOGGER.warn(
						"searchBusquedaSanciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchBusquedaSanciones() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"searchBusquedaSanciones() -> Salida del servicio para la búsqueda por filtros de sanciones en bbdd");
		return busquedaSancionesDTO;
	}

}
