package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IFichaDatosColegialesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoExample;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.entities.CenDireccionTipodireccionExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscolegialesestadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionTipodireccionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposseguroExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaDatosColegialesServiceImpl implements IFichaDatosColegialesService {

	private Logger LOGGER = Logger.getLogger(FichaDatosColegialesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenTratamientoExtendsMapper cenTratamientoExtendsMapper;

	@Autowired
	private CenTiposseguroExtendsMapper cenTiposseguroExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoExtendsMapper;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Autowired
	private CenDireccionTipodireccionExtendsMapper cenDireccionTipodireccionExtendsMapper;

	@Override
	public ComboDTO getSocietyTypes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboDTO getTratamiento(HttpServletRequest request) {

		LOGGER.info("getTratamiento() -> Entrada al servicio para obtener los tipos de tratamiento disponibles");
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
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = cenTratamientoExtendsMapper.selectTratamiento("a");

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getPais() -> Salida del servicio para obtener los tipos de tratamiento disponibles");

		return comboDTO;
	}

	@Override
	public ComboDTO getLabel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboDTO getTypeInsurances(HttpServletRequest request) {
		LOGGER.info("getTypeInsurances() -> Entrada al servicio para obtener los tipos seguros disponibles");
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
					"getTypeInsurances() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeInsurances() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeInsurances() / cenTiposseguroExtendsMapper.getTypeInsurances() -> Entrada a cenTiposseguroExtendsMapper para obtener tipos de seguro");
				comboItems = cenTiposseguroExtendsMapper.getTypeInsurances(usuario.getIdlenguaje());

				if (null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);

				}

				LOGGER.info(
						"getTypeInsurances() / cenTiposseguroExtendsMapper.getTypeInsurances() -> Salida de cenTiposseguroExtendsMapper para obtener tipos de seguro");

			}

		}
		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTypeInsurances() -> Salida del servicio para obtener los tipos seguros disponibles");
		return comboDTO;

	}

	@Override
	public ColegiadoDTO datosColegialesSearch(int numPagina, ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("datosColegialesSearch() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		List<ColegiadoItem> colegiadoListItem = new ArrayList<ColegiadoItem>();
		ColegiadoDTO datosColegialesDTO = new ColegiadoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				// if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosColegialesSearch() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");
				colegiadoListItem = cenColegiadoExtendsMapper.selectColegiaciones(idInstitucion,
						usuario.getIdlenguaje(), colegiadoItem);
				LOGGER.info(
						"datosColegialesSearch() / CenColegiadoExtendsMapper.selectDirecciones() -> Salida de CenColegiadoExtendsMapper para busqueda de Colegiados");

				datosColegialesDTO.setColegiadoItem(colegiadoListItem);
			} else {
				LOGGER.warn(
						"datosColegialesSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosColegialesSearch() -> idInstitucion del token nula");
		}

		LOGGER.info("datosColegialesSearch() -> Salida del servicio para la búsqueda por filtros de Colegiados");
		return datosColegialesDTO;
	}

	@Override
	public UpdateResponseDTO datosColegialesUpdate(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("datosColegialesSearch() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		UpdateResponseDTO response = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				// if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosColegialesUpdate() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");

				CenColegiado colegiado = new CenColegiado();
				colegiado.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				colegiado.setIdinstitucion(idInstitucion);
				colegiado.setUsumodificacion(usuario.getIdusuario());
				if (colegiadoItem.getNumColegiado() != null) {
					colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
				}
				if (colegiadoItem.getIdTiposSeguro() != null) {
					colegiado.setIdtiposseguro(Short.parseShort(colegiadoItem.getIdTiposSeguro()));
				}
				if (colegiadoItem.getSituacion() != null) {
					colegiado.setSituacionresidente(colegiadoItem.getSituacionResidente());
				}
				if (colegiadoItem.getComunitario() != null) {
					colegiado.setComunitario(colegiadoItem.getComunitario());
				}
				if (colegiadoItem.getnMutualista() != null && colegiadoItem.getnMutualista() != "") {
					colegiado.setNmutualista(colegiadoItem.getnMutualista());
				} else {
					colegiado.setNmutualista(null);
				}

				colegiado.setFechaincorporacion(colegiadoItem.getIncorporacionDate());
				colegiado.setFechatitulacion(colegiadoItem.getFechaTitulacionDate());
				colegiado.setFechapresentacion(colegiadoItem.getFechapresentacionDate());
				colegiado.setFechajura(colegiadoItem.getFechaJuraDate());

				colegiado.setFechamodificacion(new Date());

				int responseUpdate = cenColegiadoExtendsMapper.updateColegiado(colegiado);
				LOGGER.info(
						"datosColegialesUpdate() / CenColegiadoExtendsMapper.selectDirecciones() -> Salida de CenColegiadoExtendsMapper para actualización de Colegiados");

				if (responseUpdate >= 1) {
					response.setStatus(SigaConstants.OK);
				} else {
					response.setStatus(SigaConstants.KO);
				}
				// 4. Actualiza la tabla CEN_DATOSCOLEGIALESESTADO

				// CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new
				// CenDatoscolegialesestadoExample();
				// cenDatoscolegialesestadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
				// cenDatoscolegialesestadoExample.setOrderByClause("FECHAESTADO DESC");
				// // Buscamos por idPersona para ver si el estado es diferente
				//
				// cenDatoscolegialesestado =
				// cenDatoscolegialesestadoMapper.selectByExample(cenDatoscolegialesestadoExample);
				//
				// if(cenDatoscolegialesestado != null && cenDatoscolegialesestado.size()>0) {
				// if
				// (!cenDatoscolegialesestado.get(0).getIdestado().equals(Short.valueOf(colegiadoItem.getSituacion())))
				// {
				// CenDatoscolegialesestado cenEstadoColegial = new CenDatoscolegialesestado();
				// cenEstadoColegial.setIdestado(Short.parseShort(colegiadoItem.getSituacion()));
				// cenEstadoColegial.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				// cenEstadoColegial.setIdinstitucion(Short.parseShort(colegiadoItem.getIdInstitucion()));
				// cenEstadoColegial.setFechamodificacion(new Date());
				// cenEstadoColegial.setUsumodificacion(usuario.getIdusuario());
				// cenEstadoColegial.setFechaestado(new Date());
				// cenDatoscolegialesestadoMapper.insertSelective(cenEstadoColegial);
				// }
				// }

			} else {
				LOGGER.warn(
						"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
				response.setStatus(SigaConstants.KO);
			}

		} else {
			LOGGER.warn("datosColegialesUpdate() -> idInstitucion del token nula");
			response.setStatus(SigaConstants.KO);
		}

		LOGGER.info("datosColegialesUpdate() -> Salida del servicio para la búsqueda por filtros de Colegiados");
		return response;
	}

	@Override
	public InsertResponseDTO datosColegialesInsertEstado(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("datosColegialesInsertEstado() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		InsertResponseDTO response = new InsertResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesInsertEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesInsertEstado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosColegialesInsertEstado() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");

				try {

					CenDatoscolegialesestado datosColegiales = new CenDatoscolegialesestado();
					List<Short> addTipoDirecciones = null;
					List<String> addTipoDireccionesPreferentes = null;

					// Comprobamos que sea cambio de ejerciente a no ejerciente
					CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
					cenDatoscolegialesestadoExample.createCriteria()
							.andIdinstitucionEqualTo(Short.valueOf(colegiadoItem.getIdInstitucion()))
							.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));

					List<CenDatoscolegialesestado> cenDatoscolegialesestadosList = cenDatoscolegialesestadoExtendsMapper
							.selectByExample(cenDatoscolegialesestadoExample);

					if (null != cenDatoscolegialesestadosList && cenDatoscolegialesestadosList.size() > 0) {
						// Obtenemos el estado colegial que tiene el colegiado guardado en bbdd
						CenDatoscolegialesestado cenDatoscolegialesestadoBBDD = cenDatoscolegialesestadosList.get(0);

						// Obtenemos los tipos de direcciones que tiene el colegiado
						CenDireccionTipodireccionExample cenDireccionTipodireccionExample = new CenDireccionTipodireccionExample();
						cenDireccionTipodireccionExample.createCriteria()
								.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						List<CenDireccionTipodireccion> cenTipoDireccionesList = cenDireccionTipodireccionExtendsMapper
								.selectByExample(cenDireccionTipodireccionExample);

						// Obtenemos las direcciones que tiene el colegiado
						CenDireccionesExample cenDireccionesExample = new CenDireccionesExample();
						cenDireccionesExample.createCriteria()
								.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						List<CenDirecciones> cenDireccionesList = cenDireccionesExtendsMapper
								.selectByExample(cenDireccionesExample);

						// Paso de ejerciente a no ejerciente
						if (cenDatoscolegialesestadoBBDD.getIdestado() == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE
								&& Integer.parseInt(
										colegiadoItem.getSituacion()) != SigaConstants.ESTADO_COLEGIAL_EJERCIENTE) {

							// Obtenemos las direcciones que tenemos que que añadir
							addTipoDirecciones = compruebaTipoDireccion(cenTipoDireccionesList, false);
							addTipoDireccionesPreferentes = compruebaTipoDireccionPreferente(cenDireccionesList);

							// Paso de no ejerciente a ejerciente
						} else if (cenDatoscolegialesestadoBBDD
								.getIdestado() != SigaConstants.ESTADO_COLEGIAL_EJERCIENTE
								&& Integer.parseInt(
										colegiadoItem.getSituacion()) == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE) {

							// Obtenemos las direcciones que tenemos que que añadir
							addTipoDirecciones = compruebaTipoDireccion(cenTipoDireccionesList, true);
							addTipoDireccionesPreferentes = compruebaTipoDireccionPreferente(cenDireccionesList);

						}

						CenDireccionTipodireccion tipoCensoWeb = null;

						// Lo añadimos automaticamente a la dirección donde se encuentre CensoWeb
						// Buscamos la dirección que tenga tipoCenso
						for (CenDireccionTipodireccion cenTipoDireccion : cenTipoDireccionesList) {
							if (cenTipoDireccion.getIdtipodireccion() == Short
									.valueOf(SigaConstants.TIPO_DIR_CENSOWEB)) {
								tipoCensoWeb = cenTipoDireccion;
							}
						}

						CenDirecciones direccionCensoWeb = null;
						for (CenDirecciones cenDireccion : cenDireccionesList) {
							if (cenDireccion.getIddireccion() == tipoCensoWeb.getIddireccion()) {
								direccionCensoWeb = cenDireccion;
							}
						}

						// Si hay que añadir algun tipo de direccion lo añadimos
						if (null != addTipoDirecciones && addTipoDirecciones.size() > 0) {

							for (Short tipo : addTipoDirecciones) {
								CenDireccionTipodireccion cenDireccionTipodireccion = new CenDireccionTipodireccion();
								cenDireccionTipodireccion.setFechamodificacion(new Date());
								cenDireccionTipodireccion.setIdinstitucion(direccionCensoWeb.getIdinstitucion());
								cenDireccionTipodireccion.setIdpersona(Long.valueOf(colegiadoItem.getIdPersona()));
								cenDireccionTipodireccion.setUsumodificacion(usuario.getIdusuario());
								cenDireccionTipodireccion.setIddireccion(direccionCensoWeb.getIddireccion());
								cenDireccionTipodireccion.setIdtipodireccion(tipo);

								cenDireccionTipodireccionExtendsMapper.insert(cenDireccionTipodireccion);

							}
						}

						// Si hay que añadir algun tipo de direccion lo añadimos
						if (null != addTipoDireccionesPreferentes && addTipoDireccionesPreferentes.size() > 0) {
							String addPref = direccionCensoWeb.getPreferente();
							for (String tipoPreferente : addTipoDireccionesPreferentes) {
								addPref += tipoPreferente;
							}

							direccionCensoWeb.setPreferente(addPref);
							direccionCensoWeb.setUsumodificacion(usuario.getIdusuario());
							direccionCensoWeb.setFechamodificacion(new Date());

							cenDireccionesExtendsMapper.updateByPrimaryKey(direccionCensoWeb);
						}
					}

					datosColegiales.setFechaestado(colegiadoItem.getFechaEstado());
					datosColegiales.setFechamodificacion(new Date());
					datosColegiales.setIdestado(Short.valueOf(colegiadoItem.getSituacion()));

					datosColegiales.setIdinstitucion(Short.valueOf(colegiadoItem.getIdInstitucion()));
					datosColegiales.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
					datosColegiales.setUsumodificacion(usuario.getIdusuario());
					datosColegiales.setObservaciones(colegiadoItem.getObservaciones());
					datosColegiales.setSituacionresidente(colegiadoItem.getSituacionResidente());
					int resultado = cenDatoscolegialesestadoExtendsMapper.insert(datosColegiales);

					if (resultado == 1) {
						response.setStatus(SigaConstants.OK);
					} else {
						response.setStatus(SigaConstants.KO);
					}
				} catch (Exception e) {
					LOGGER.warn(e.getMessage());
					response.setStatus(SigaConstants.KO);
				}
			} else {
				LOGGER.warn(
						"datosColegialesInsertEstado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
				response.setStatus(SigaConstants.KO);
			}

		} else {
			LOGGER.warn("datosColegialesInsertEstado() -> idInstitucion del token nula");
			response.setStatus(SigaConstants.KO);
		}

		LOGGER.info("datosColegialesInsertEstado() -> Salida del servicio para la búsqueda por filtros de Colegiados");
		return response;
	}

	@Override
	public UpdateResponseDTO datosColegialesUpdateEstados(List<ColegiadoItem> listColegiadoItem,
			HttpServletRequest request) {

		LOGGER.info(
				"datosColegialesUpdateEstados() -> Entrada al servicio para actualizar los estados colegiales de un colegial determinado");

		UpdateResponseDTO response = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean existeDummy = false;
		Date fechaEstadoNueva = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesUpdateEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesUpdateEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					int resultado = 0;
					Short idInstitucionColegial = Short.valueOf(listColegiadoItem.get(0).getIdInstitucion());
					Long idPersonaColegial = Long.valueOf(listColegiadoItem.get(0).getIdPersona());
					for (ColegiadoItem colegiadoItem : listColegiadoItem) {

						// Hacemos esta comprobación para obviar el primer registro en el caso de que
						// sea el dummy cuando queremos hacer update+insert
						if (colegiadoItem.getFechaEstado() != null) {
							CenDatoscolegialesestado datosColegiales = new CenDatoscolegialesestado();
							List<Short> addTipoDirecciones = null;
							List<String> addTipoDireccionesPreferentes = null;

							// Si solamente es modificar
							if (!existeDummy && colegiadoItem.getIdPersona() != null) {
								// Comprobamos que sea cambio de ejerciente a no ejerciente
								CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
								cenDatoscolegialesestadoExample.createCriteria()
										.andIdinstitucionEqualTo(idInstitucionColegial)
										.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
										.andFechaestadoGreaterThanOrEqualTo(colegiadoItem.getFechaEstado());

								List<CenDatoscolegialesestado> cenDatoscolegialesestadosList = cenDatoscolegialesestadoExtendsMapper
										.selectByExample(cenDatoscolegialesestadoExample);

								if (null != cenDatoscolegialesestadosList && cenDatoscolegialesestadosList.size() > 0) {
									// Obtenemos el estado colegial que tiene el colegiado guardado en bbdd
									CenDatoscolegialesestado cenDatoscolegialesestadoBBDD = cenDatoscolegialesestadosList
											.get(0);

									// Obtenemos los tipos de direcciones que tiene el colegiado
									CenDireccionTipodireccionExample cenDireccionTipodireccionExample = new CenDireccionTipodireccionExample();
									cenDireccionTipodireccionExample.createCriteria()
											.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
											.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

									List<CenDireccionTipodireccion> cenTipoDireccionesList = cenDireccionTipodireccionExtendsMapper
											.selectByExample(cenDireccionTipodireccionExample);

									// Obtenemos las direcciones que tiene el colegiado
									CenDireccionesExample cenDireccionesExample = new CenDireccionesExample();
									cenDireccionesExample.createCriteria()
											.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
											.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

									List<CenDirecciones> cenDireccionesList = cenDireccionesExtendsMapper
											.selectByExample(cenDireccionesExample);

									// Paso de ejerciente a no ejerciente
									if (cenDatoscolegialesestadoBBDD
											.getIdestado() == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE
											&& Integer.parseInt(colegiadoItem
													.getIdEstado()) != SigaConstants.ESTADO_COLEGIAL_EJERCIENTE) {

										// Obtenemos las direcciones que tenemos que que añadir
										addTipoDirecciones = compruebaTipoDireccion(cenTipoDireccionesList, false);
										addTipoDireccionesPreferentes = compruebaTipoDireccionPreferente(
												cenDireccionesList);

										// Paso de no ejerciente a ejerciente
									} else if (Integer.parseInt(
											colegiadoItem.getIdEstado()) == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE
											&& cenDatoscolegialesestadoBBDD
													.getIdestado() != SigaConstants.ESTADO_COLEGIAL_EJERCIENTE) {

										// Obtenemos las direcciones que tenemos que que añadir
										addTipoDirecciones = compruebaTipoDireccion(cenTipoDireccionesList, true);
										addTipoDireccionesPreferentes = compruebaTipoDireccionPreferente(
												cenDireccionesList);

									}

									CenDireccionTipodireccion tipoCensoWeb = null;

									// Lo añadimos automaticamente a la dirección donde se encuentre CensoWeb
									// Buscamos la dirección que tenga tipoCenso
									for (CenDireccionTipodireccion cenTipoDireccion : cenTipoDireccionesList) {
										if (cenTipoDireccion.getIdtipodireccion() == Short
												.valueOf(SigaConstants.TIPO_DIR_CENSOWEB)) {
											tipoCensoWeb = cenTipoDireccion;
										}
									}

									CenDirecciones direccionCensoWeb = null;
									for (CenDirecciones cenDireccion : cenDireccionesList) {
										if (cenDireccion.getIddireccion() == tipoCensoWeb.getIddireccion()) {
											direccionCensoWeb = cenDireccion;
										}
									}

									// Si hay que añadir algun tipo de direccion lo añadimos
									if (null != addTipoDirecciones && addTipoDirecciones.size() > 0) {

										for (Short tipo : addTipoDirecciones) {
											CenDireccionTipodireccion cenDireccionTipodireccion = new CenDireccionTipodireccion();
											cenDireccionTipodireccion.setFechamodificacion(new Date());
											cenDireccionTipodireccion
													.setIdinstitucion(direccionCensoWeb.getIdinstitucion());
											cenDireccionTipodireccion
													.setIdpersona(Long.valueOf(colegiadoItem.getIdPersona()));
											cenDireccionTipodireccion.setUsumodificacion(usuario.getIdusuario());
											cenDireccionTipodireccion
													.setIddireccion(direccionCensoWeb.getIddireccion());
											cenDireccionTipodireccion.setIdtipodireccion(tipo);

											cenDireccionTipodireccionExtendsMapper.insert(cenDireccionTipodireccion);

										}
									}

									// Si hay que añadir algun tipo de direccion lo añadimos
									if (null != addTipoDireccionesPreferentes
											&& addTipoDireccionesPreferentes.size() > 0) {
										String addPref = direccionCensoWeb.getPreferente();
										for (String tipoPreferente : addTipoDireccionesPreferentes) {
											addPref += tipoPreferente;
										}

										direccionCensoWeb.setPreferente(addPref);
										direccionCensoWeb.setUsumodificacion(usuario.getIdusuario());
										direccionCensoWeb.setFechamodificacion(new Date());

										cenDireccionesExtendsMapper.updateByPrimaryKey(direccionCensoWeb);
									}
								}

							}

							datosColegiales.setFechaestado(colegiadoItem.getFechaEstado());
							datosColegiales.setIdinstitucion(idInstitucionColegial);
							datosColegiales.setIdpersona(idPersonaColegial);
							datosColegiales.setObservaciones(colegiadoItem.getObservaciones());
							datosColegiales.setFechamodificacion(new Date());
							datosColegiales.setUsumodificacion(usuario.getIdusuario());

							datosColegiales.setIdestado(Short.valueOf(colegiadoItem.getIdEstado()));
							
							fechaEstadoNueva = colegiadoItem.getFechaEstadoNueva();

							if (colegiadoItem.getSituacionResidente() != null) {
								datosColegiales.setSituacionresidente(
										colegiadoItem.getSituacionResidente().equalsIgnoreCase("si") ? "1" : "0");
							}
							LOGGER.info(
									"datosColegialesUpdateEstados() / cenDatoscolegialesestadoMapper.updateByPrimaryKeySelective() -> Entrada a cenDatoscolegialesestadoMapper para actualizar el estado colegial");
							resultado += cenDatoscolegialesestadoExtendsMapper.updateEstadoColegial(datosColegiales, fechaEstadoNueva);
							LOGGER.info(
									"datosColegialesUpdateEstados() / cenDatoscolegialesestadoMapper.updateByPrimaryKeySelective() -> Entrada a cenDatoscolegialesestadoMapper para para actualizar el estado colegial");

						} else {
							existeDummy = true;
						}
					}
					if (resultado > 0) {
						response.setStatus(SigaConstants.OK);
					} else {
						response.setStatus(SigaConstants.KO);
					}
				} catch (Exception e) {
					LOGGER.warn(e.getMessage());
					response.setStatus(SigaConstants.KO);
				}
			} else {
				LOGGER.warn(
						"datosColegialesUpdateEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
				response.setStatus(SigaConstants.KO);
			}

		} else {
			LOGGER.warn("datosColegialesUpdateEstados() -> idInstitucion del token nula");
			response.setStatus(SigaConstants.KO);
		}

		LOGGER.info(
				"datosColegialesUpdateEstados() -> Salida del servicio para actualizar los estados colegiales de un colegial determinado");
		return response;
	}

	public List<Short> compruebaTipoDireccion(List<CenDireccionTipodireccion> cenTipoDireccionesList,
			boolean isEjerciente) {
		List<Short> tiposDirecciones = new ArrayList<Short>();
		List<Short> addTiposDirecciones = new ArrayList<Short>();

		for (CenDireccionTipodireccion cenDireccion : cenTipoDireccionesList) {
			tiposDirecciones.add(cenDireccion.getIdtipodireccion());
		}

		boolean idFindTipoDirGuardia = tiposDirecciones.contains(Short.parseShort(SigaConstants.TIPO_DIR_GUARDIA));
		boolean idFindTipoDirFact = tiposDirecciones.contains(Short.parseShort(SigaConstants.TIPO_DIR_FACTURACION));
		boolean idFindTipoDirDes = tiposDirecciones.contains(Short.parseShort(SigaConstants.TIPO_DIR_DESPACHO));
		boolean idFindTipoDirTras = tiposDirecciones.contains(Short.parseShort(SigaConstants.TIPO_DIR_TRASPASO));
		boolean idFindTipoDirGuia = tiposDirecciones.contains(Short.parseShort(SigaConstants.TIPO_DIR_GUIAJUDICIAL));

		if (!idFindTipoDirGuardia) {
			addTiposDirecciones.add(Short.parseShort(SigaConstants.TIPO_DIR_GUARDIA));
		}

		if (!idFindTipoDirFact) {
			addTiposDirecciones.add(Short.parseShort(SigaConstants.TIPO_DIR_FACTURACION));
		}

		if (!idFindTipoDirTras) {
			addTiposDirecciones.add(Short.parseShort(SigaConstants.TIPO_DIR_TRASPASO));
		}

		if (!idFindTipoDirGuia) {
			addTiposDirecciones.add(Short.parseShort(SigaConstants.TIPO_DIR_GUIAJUDICIAL));
		}

		if (isEjerciente) {
			if (!idFindTipoDirDes) {
				addTiposDirecciones.add(Short.parseShort(SigaConstants.TIPO_DIR_DESPACHO));
			}
		}

		return addTiposDirecciones;
	}

	public List<String> compruebaTipoDireccionPreferente(List<CenDirecciones> cenDireccionesList) {
		String tiposDirecciones = "";
		List<String> addTiposDirecciones = new ArrayList<String>();

		for (CenDirecciones cenDireccion : cenDireccionesList) {
			tiposDirecciones += cenDireccion.getPreferente();
		}

		boolean idFindTipoDirCorreo = tiposDirecciones.contains(SigaConstants.DIR_PREFERENTE_CORREO);
		boolean idFindTipoDirSMS = tiposDirecciones.contains(SigaConstants.DIR_PREFERENTE_SMS);
		boolean idFindTipoDirEmail = tiposDirecciones.contains(SigaConstants.DIR_PREFERENTE_EMAIL);

		if (!idFindTipoDirCorreo) {
			addTiposDirecciones.add(SigaConstants.DIR_PREFERENTE_CORREO);
		}

		if (!idFindTipoDirSMS) {
			addTiposDirecciones.add(SigaConstants.DIR_PREFERENTE_SMS);
		}

		if (!idFindTipoDirEmail) {
			addTiposDirecciones.add(SigaConstants.DIR_PREFERENTE_EMAIL);
		}

		return addTiposDirecciones;
	}

	@Override
	public UpdateResponseDTO datosColegialesDeleteEstado(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info(
				"datosColegialesDeleteEstado() -> Entrada al servicio para eliminar el estado colegial de un colegial determinado");

		UpdateResponseDTO response = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesDeleteEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesDeleteEstado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					int resultado = 0;

					CenDatoscolegialesestado estadoColegial = new CenDatoscolegialesestado();

					List<Short> addTipoDirecciones = null;
					List<String> addTipoDireccionesPreferentes = null;

					// Comprobamos que sea cambio de ejerciente a no ejerciente
					CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
					cenDatoscolegialesestadoExample.createCriteria()
							.andIdinstitucionEqualTo(Short.valueOf(colegiadoItem.getIdInstitucion()))
							.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));
					cenDatoscolegialesestadoExample.setOrderByClause("fechaestado desc");

					List<CenDatoscolegialesestado> cenDatoscolegialesestadosList = cenDatoscolegialesestadoExtendsMapper
							.selectByExample(cenDatoscolegialesestadoExample);

					if (null != cenDatoscolegialesestadosList && cenDatoscolegialesestadosList.size() > 0) {
						CenDatoscolegialesestado cenDatoscolegialesestadoBBDD = null;

						// Obtenemos el estado colegial que tiene el colegiado guardado en bbdd
						if (cenDatoscolegialesestadosList.size() > 1) {
							cenDatoscolegialesestadoBBDD = cenDatoscolegialesestadosList.get(1);
						}

						// Obtenemos los tipos de direcciones que tiene el colegiado
						CenDireccionTipodireccionExample cenDireccionTipodireccionExample = new CenDireccionTipodireccionExample();
						cenDireccionTipodireccionExample.createCriteria()
								.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						List<CenDireccionTipodireccion> cenTipoDireccionesList = cenDireccionTipodireccionExtendsMapper
								.selectByExample(cenDireccionTipodireccionExample);

						// Obtenemos las direcciones que tiene el colegiado
						CenDireccionesExample cenDireccionesExample = new CenDireccionesExample();
						cenDireccionesExample.createCriteria()
								.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						List<CenDirecciones> cenDireccionesList = cenDireccionesExtendsMapper
								.selectByExample(cenDireccionesExample);

						// Paso de ejerciente a no ejerciente
						if (cenDatoscolegialesestadoBBDD != null
								&& cenDatoscolegialesestadoBBDD
										.getIdestado() != SigaConstants.ESTADO_COLEGIAL_EJERCIENTE
								&& Integer.parseInt(
										colegiadoItem.getIdEstado()) == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE) {

							// Obtenemos las direcciones que tenemos que que añadir
							addTipoDirecciones = compruebaTipoDireccion(cenTipoDireccionesList, false);
							addTipoDireccionesPreferentes = compruebaTipoDireccionPreferente(cenDireccionesList);

							// Paso de no ejerciente a ejerciente
						} else if (cenDatoscolegialesestadoBBDD != null
								&& cenDatoscolegialesestadoBBDD
										.getIdestado() == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE
								&& Integer.parseInt(
										colegiadoItem.getIdEstado()) != SigaConstants.ESTADO_COLEGIAL_EJERCIENTE) {

							// Obtenemos las direcciones que tenemos que que añadir
							addTipoDirecciones = compruebaTipoDireccion(cenTipoDireccionesList, true);
							addTipoDireccionesPreferentes = compruebaTipoDireccionPreferente(cenDireccionesList);

						}

						CenDireccionTipodireccion tipoCensoWeb = null;

						// Lo añadimos automaticamente a la dirección donde se encuentre CensoWeb
						// Buscamos la dirección que tenga tipoCenso
						for (CenDireccionTipodireccion cenTipoDireccion : cenTipoDireccionesList) {
							if (cenTipoDireccion.getIdtipodireccion() == Short
									.valueOf(SigaConstants.TIPO_DIR_CENSOWEB)) {
								tipoCensoWeb = cenTipoDireccion;
							}
						}

						CenDirecciones direccionCensoWeb = null;
						for (CenDirecciones cenDireccion : cenDireccionesList) {
							if (cenDireccion.getIddireccion() == tipoCensoWeb.getIddireccion()) {
								direccionCensoWeb = cenDireccion;
							}
						}

						// Si hay que añadir algun tipo de direccion lo añadimos
						if (null != addTipoDirecciones && addTipoDirecciones.size() > 0) {

							for (Short tipo : addTipoDirecciones) {
								CenDireccionTipodireccion cenDireccionTipodireccion = new CenDireccionTipodireccion();
								cenDireccionTipodireccion.setFechamodificacion(new Date());
								cenDireccionTipodireccion.setIdinstitucion(direccionCensoWeb.getIdinstitucion());
								cenDireccionTipodireccion.setIdpersona(Long.valueOf(colegiadoItem.getIdPersona()));
								cenDireccionTipodireccion.setUsumodificacion(usuario.getIdusuario());
								cenDireccionTipodireccion.setIddireccion(direccionCensoWeb.getIddireccion());
								cenDireccionTipodireccion.setIdtipodireccion(tipo);

								cenDireccionTipodireccionExtendsMapper.insert(cenDireccionTipodireccion);

							}
						}

						// Si hay que añadir algun tipo de direccion lo añadimos
						if (null != addTipoDireccionesPreferentes && addTipoDireccionesPreferentes.size() > 0) {
							String addPref = direccionCensoWeb.getPreferente();
							for (String tipoPreferente : addTipoDireccionesPreferentes) {
								addPref += tipoPreferente;
							}

							direccionCensoWeb.setPreferente(addPref);
							direccionCensoWeb.setUsumodificacion(usuario.getIdusuario());
							direccionCensoWeb.setFechamodificacion(new Date());

							cenDireccionesExtendsMapper.updateByPrimaryKey(direccionCensoWeb);
						}
					}

					estadoColegial.setFechaestado(colegiadoItem.getFechaEstado());
					estadoColegial.setIdinstitucion(Short.parseShort(colegiadoItem.getIdInstitucion()));
					estadoColegial.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));

					LOGGER.info(
							"datosColegialesDeleteEstado() / cenDatoscolegialesestadoMapper.deleteByPrimaryKey() -> Entrada a cenDatoscolegialesestadoMapper para eliminar el estado colegial");
					resultado = cenDatoscolegialesestadoExtendsMapper.deleteEstadoColegial(estadoColegial);
					LOGGER.info(
							"datosColegialesDeleteEstado() / cenDatoscolegialesestadoMapper.deleteByPrimaryKey() -> Entrada a cenDatoscolegialesestadoMapper para eliminar el estado colegial");

					if (resultado == 1) {
						response.setStatus(SigaConstants.OK);
					} else {
						response.setStatus(SigaConstants.KO);
					}
				} catch (Exception e) {
					LOGGER.warn(e.getMessage());
					response.setStatus(SigaConstants.KO);
				}
			} else {
				LOGGER.warn(
						"datosColegialesDeleteEstado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
				response.setStatus(SigaConstants.KO);
			}

		} else {
			LOGGER.warn("datosColegialesDeleteEstado() -> idInstitucion del token nula");
			response.setStatus(SigaConstants.KO);
		}

		LOGGER.info(
				"datosColegialesUpdateEstados() -> Salida del servicio para eliminar el estado colegial de un colegial determinado");
		return response;
	}

}
