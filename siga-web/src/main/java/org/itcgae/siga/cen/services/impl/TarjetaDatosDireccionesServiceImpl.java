package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaDireccionesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.entities.CenDireccionTipodireccionExample;
import org.itcgae.siga.db.entities.CenDireccionTipodireccionKey;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoKey;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionTipodireccionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPaisExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPoblacionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolimodidireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipoDireccionExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaDatosDireccionesServiceImpl implements ITarjetaDatosDireccionesService {

	private Logger LOGGER = Logger.getLogger(TarjetaDatosDireccionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenSolicitmodifdatosbasicosExtendsMapper cenSolicitmodifdatosbasicosMapper;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Autowired
	private CenPaisExtendsMapper cenPaisExtendsMapper;

	@Autowired
	private CenDireccionTipodireccionExtendsMapper cenDireccionTipodireccionMapper;

	@Autowired
	private CenSolimodidireccionesExtendsMapper cenSolimodidireccionesExtendsMapper;

	@Autowired
	private CenTipoDireccionExtendsMapper cenTipoDireccionExtendsMapper;

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private IAuditoriaCenHistoricoService auditoriaCenHistoricoService;

	@Autowired
	private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Override
	public DatosDireccionesDTO datosDireccionesSearch(int numPagina,
			DatosDireccionesSearchDTO datosDireccionesSearchDTO, HttpServletRequest request) {
		LOGGER.info("searchBanksData() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de direcciones");
				datosDireccionesItem = cenDireccionesExtendsMapper.selectDirecciones(datosDireccionesSearchDTO,
						idInstitucion.toString());
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de direcciones");

				if (null != datosDireccionesItem && datosDireccionesItem.size() > 0) {
					String[] tiposDirecciones = null;
					List<String> tiposDireccionesList = null;
					for (DatosDireccionesItem datosDireccionItem : datosDireccionesItem) {
						if (!UtilidadesString.esCadenaVacia(datosDireccionItem.getIdTipoDireccionList())) {
							tiposDirecciones = datosDireccionItem.getIdTipoDireccionList().split(";");
							tiposDireccionesList = new ArrayList<String>(Arrays.asList(tiposDirecciones));
							// indicar en el combo los tipos de direcciones preferentes
							if (datosDireccionItem.getPreferente() != ""
									&& datosDireccionItem.getPreferente() != null) {
								if (datosDireccionItem.getPreferente().contains(SigaConstants.DIR_PREFERENTE_EMAIL)) {
									tiposDireccionesList.add(SigaConstants.TIPO_DIR_PREFERENTE_EMAIL);
								}
								if (datosDireccionItem.getPreferente().contains(SigaConstants.DIR_PREFERENTE_CORREO)) {
									tiposDireccionesList.add(SigaConstants.TIPO_DIR_PREFERENTE_CORREO);
								}
								if (datosDireccionItem.getPreferente().contains(SigaConstants.DIR_PREFERENTE_SMS)) {
									tiposDireccionesList.add(SigaConstants.TIPO_DIR_PREFERENTE_SMS);
								}
								if (datosDireccionItem.getPreferente().contains(SigaConstants.DIR_PREFERENTE_FAX)) {
									tiposDireccionesList.add(SigaConstants.TIPO_DIR_PREFERENTE_FAX);
								}
							}

							tiposDirecciones = new String[tiposDireccionesList.size()];
							tiposDireccionesList.toArray(tiposDirecciones);
							datosDireccionItem.setIdTipoDireccion(tiposDirecciones);

						}
					}
				}
				datosDireccionesDTO.setDatosDireccionesItem(datosDireccionesItem);
			} else {
				LOGGER.warn(
						"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosDireccionesSearch() -> idInstitucion del token nula");
		}

		LOGGER.info("datosDireccionesSearch() -> Salida del servicio para la búsqueda por filtros de direcciones");
		return datosDireccionesDTO;
	}

	@Override
	public UpdateResponseDTO deleteDireccion(TarjetaDireccionesUpdateDTO[] tarjetaDireccionesUpdateDTO,
			HttpServletRequest request) {

		LOGGER.info("updateMember() -> Entrada al servicio para actualizar información de direcciones");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		int response = 0;
		Error error = new Error();

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

			for (int i = 0; i < tarjetaDireccionesUpdateDTO.length; i++) {

				LOGGER.info(
						"getCargos() / cenDireccionesExtendsMapper.updateMember() -> Entrada a cenDireccionesExtendsMapper para actualizar datos de un direcciones");
				CenDirecciones recordUpdate = new CenDirecciones();
				recordUpdate.setFechabaja(new Date());
				recordUpdate.setFechamodificacion(new Date());
				recordUpdate.setUsumodificacion(usuario.getIdusuario());
				recordUpdate.setIddireccion(Long.valueOf(tarjetaDireccionesUpdateDTO[i].getIdDireccion()));
				recordUpdate.setIdinstitucion(idInstitucion);
				recordUpdate.setIdpersona(Long.valueOf(tarjetaDireccionesUpdateDTO[i].getIdPersona()));
				response = cenDireccionesExtendsMapper.updateByPrimaryKeySelective(recordUpdate);

				LOGGER.info(
						"getCargos() / cenDireccionesExtendsMapper.updateMember() -> Salida de cenDireccionesExtendsMapper para actualizar datos de un direcciones");

				updateResponseDTO.setStatus(SigaConstants.OK);
				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("getCargos() / cenDireccionesExtendsMapper.updateMember() -> "
							+ updateResponseDTO.getStatus() + ". No se pudo actualizar datos de un direcciones");

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
	public ComboDTO getPais(HttpServletRequest request) {

		LOGGER.info("getPais() -> Entrada al servicio para obtener los tipos de direccion disponibles");
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
					"getPais() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getPais() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = cenPaisExtendsMapper.selectPais(usuario.getIdlenguaje());
				if (null != comboItems && comboItems.size() > 0) {
					/*
					 * ComboItem element = new ComboItem(); element.setLabel("");
					 * element.setValue(""); comboItems.add(0, element);
					 */

					// busqueda binaria de España
					List<String> listaPaises = comboItems.stream()
							.map(object -> Objects.toString(object.getLabel(), null)).collect(Collectors.toList());
					int indexSpain = Collections.binarySearch(listaPaises, "ESPAÑA");
					ComboItem elementSpain = new ComboItem();
					elementSpain.setLabel(comboItems.get(indexSpain).getLabel());
					elementSpain.setValue(comboItems.get(indexSpain).getValue());
					comboItems.add(0, elementSpain);

					// eliminamos españa del indice anterior
					comboItems.remove(indexSpain + 1);

				}

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getPais() -> Salida del servicio para obtener los tipos de direccion disponibles");

		return comboDTO;
	}

	@Override
	public ComboDTO getPoblacion(HttpServletRequest request, String idProvincia, String filtro) {

		ComboDTO poblacionesReturn = new ComboDTO();
		List<CenPoblaciones> poblaciones = new ArrayList<CenPoblaciones>();

		poblaciones = cenPoblacionesExtendsMapper.selectByFilter(filtro, idProvincia);

		if (null != poblaciones && poblaciones.size() > 0) {
			List<ComboItem> combooItems = new ArrayList<ComboItem>();
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			combooItems.add(comboItem);

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
	public ComboDTO getTipoDireccion(HttpServletRequest request) {

		LOGGER.info("getTipoDireccion() -> Entrada al servicio para obtener los tipos de direccion disponibles");
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
					"getTipoDireccion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTipoDireccion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = cenTipoDireccionExtendsMapper.selectTipoDireccion(usuario.getIdlenguaje());
				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getTipoDireccion() -> Salida del servicio para obtener los tipos de direccion disponibles");

		return comboDTO;
	}

	@Override
	public UpdateResponseDTO updateDirection(DatosDireccionesItem datosDireccionesItem, HttpServletRequest request) {

		LOGGER.info("updateDirection() -> Entrada al servicio para actualizar direcciones");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		List<CenDirecciones> listCenDireccionesAnterior = new ArrayList<CenDirecciones>();
		CenDirecciones cenDireccionesAnterior = new CenDirecciones();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateDirection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateDirection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Comprobamos si la persona es colegiado o no, ya que la obligatoriedad solo
				// vale para los colegiados
				CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
				cenColegiadoExample.createCriteria()
						.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
						.andIdinstitucionEqualTo(Short.valueOf(datosDireccionesItem.getIdInstitucion()));

				List<CenColegiado> cenColegiadoList = cenColegiadoExtendsMapper.selectByExample(cenColegiadoExample);

				// Comprobamos si contiene algún tipo de dirección único
				List<String> rdo = new ArrayList<String>();
				List<String> allRdo = new ArrayList<String>();
				List<String> tipoPrefentes = new ArrayList<String>();

				// Guardamos los tipos de direcciones para separar de los preferentes y no
				// preferentes
				for (int i = 0; i < datosDireccionesItem.getIdTipoDireccion().length; i++) {
					if (datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_CENSOWEB)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_GUARDIA)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_GUIAJUDICIAL)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_TRASPASO)
							|| datosDireccionesItem.getIdTipoDireccion()[i]
									.equals(SigaConstants.TIPO_DIR_FACTURACION)) {
						rdo.add(datosDireccionesItem.getIdTipoDireccion()[i]);
						allRdo.add(datosDireccionesItem.getIdTipoDireccion()[i]);
					}

					if (datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_PREFERENTE_EMAIL)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_EMAIL);
					} else if (datosDireccionesItem.getIdTipoDireccion()[i]
							.equals(SigaConstants.TIPO_DIR_PREFERENTE_CORREO)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_CORREO);
					} else if (datosDireccionesItem.getIdTipoDireccion()[i]
							.equals(SigaConstants.TIPO_DIR_PREFERENTE_SMS)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_SMS);
					} else if (datosDireccionesItem.getIdTipoDireccion()[i]
							.equals(SigaConstants.TIPO_DIR_PREFERENTE_FAX)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_FAX);
					}

					if (datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_REVISTA)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_RESIDENCIA)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_DESPACHO)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_PUBLICA)) {
						allRdo.add(datosDireccionesItem.getIdTipoDireccion()[i]);
					}
				}

				String[] array = new String[rdo.size()];
				array = rdo.toArray(array);
				String[] allArray = new String[allRdo.size()];
				allArray = allRdo.toArray(allArray);
				datosDireccionesItem.setIdTipoDireccion(allArray);
				List<CenDireccionTipodireccion> cenDireccionTipodireccionList = new ArrayList<CenDireccionTipodireccion>();

				// Si es colegiado se realiza las restricciones
				if (null != cenColegiadoList && cenColegiadoList.size() > 0) {

					// Eliminamos de la dirección guardada el tipo de direccion de la nueva
					// dirección
					if (!rdo.isEmpty())

						cenDireccionTipodireccionList = cenDireccionTipodireccionMapper.select(
								datosDireccionesItem.getIdPersona(), String.valueOf(usuario.getIdinstitucion()), array,
								datosDireccionesItem.getIdDireccion());
					{
						for (CenDireccionTipodireccion tipoDir : cenDireccionTipodireccionList) {

							CenDireccionTipodireccionExample cenDireccionTipodireccionExample = new CenDireccionTipodireccionExample();
							cenDireccionTipodireccionExample.createCriteria()
									.andIddireccionEqualTo(tipoDir.getIddireccion())
									.andIdpersonaEqualTo(tipoDir.getIdpersona())
									.andIdinstitucionEqualTo(tipoDir.getIdinstitucion())
									.andIdtipodireccionEqualTo(tipoDir.getIdtipodireccion());

							cenDireccionTipodireccionMapper.deleteByExample(cenDireccionTipodireccionExample);
						}
					}

					// Eliminamos la direcciones prefente
					for (String p : tipoPrefentes) {

						if (p != SigaConstants.DIR_PREFERENTE_FAX) {
							CenDireccionesExample cenDireccionesPreferenteExample = new CenDireccionesExample();
							cenDireccionesPreferenteExample.createCriteria()
									.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
									.andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
									.andPreferenteLike("%" + p + "%")
									.andIddireccionNotEqualTo(Long.valueOf(datosDireccionesItem.getIdDireccion()));

							List<CenDirecciones> cenDireccionesList = cenDireccionesExtendsMapper
									.selectByExample(cenDireccionesPreferenteExample);
							String preferentes = "";

							for (CenDirecciones cenDireccion : cenDireccionesList) {
								cenDireccion.setUsumodificacion(usuario.getIdusuario());
								cenDireccion.setFechamodificacion(new Date());
								for (String s : tipoPrefentes) {
									preferentes = cenDireccion.getPreferente().replaceAll(s, "");
								}
								cenDireccion.setPreferente(preferentes);
								cenDireccionesExtendsMapper.updateByPrimaryKey(cenDireccion);
							}
						}
					}
				}

				// Consultamos la dirección a actualizar
				CenDireccionesKey key = new CenDireccionesKey();
				key.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
				key.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
				key.setIdinstitucion(Short.valueOf(idInstitucion));
				CenDirecciones direcciones = cenDireccionesExtendsMapper.selectByPrimaryKey(key);

				direcciones.setFechamodificacion(new Date());
				direcciones.setUsumodificacion(usuario.getIdusuario());
				direcciones.setCodigopostal(datosDireccionesItem.getCodigoPostal());
				direcciones.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
				direcciones.setDomicilio(datosDireccionesItem.getDomicilio());
				direcciones.setFax1(datosDireccionesItem.getFax());
				direcciones.setIdpais(datosDireccionesItem.getIdPais());
				direcciones.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
				direcciones.setIdprovincia(datosDireccionesItem.getIdProvincia());
				direcciones.setMovil(datosDireccionesItem.getMovil());
				direcciones.setOtraprovincia(Short.valueOf(datosDireccionesItem.getOtraProvincia()));
				direcciones.setPaginaweb(datosDireccionesItem.getPaginaWeb());
				direcciones.setTelefono1(datosDireccionesItem.getTelefono());
				
				if(direcciones.getFechabaja() != null) {
					direcciones.setFechabaja(null);
				}

				String preferentes = "";
				for (String s : tipoPrefentes) {
					preferentes += s;
					direcciones.setPreferente(preferentes);
				}

				if (datosDireccionesItem.getPoblacionExtranjera() != ""
						&& datosDireccionesItem.getPoblacionExtranjera() != null) {
					direcciones.setPoblacionextranjera(datosDireccionesItem.getPoblacionExtranjera());
				}

				CenDireccionTipodireccionExample tipoDireccionexample = new CenDireccionTipodireccionExample();
				tipoDireccionexample.createCriteria()
						.andIddireccionEqualTo(Long.valueOf(datosDireccionesItem.getIdDireccion()))
						.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
						.andIdinstitucionEqualTo(idInstitucion);
				// Consultamos los tipos de direccion de la direccion a actualizar
				List<CenDireccionTipodireccion> tiposDireccion = cenDireccionTipodireccionMapper
						.selectByExample(tipoDireccionexample);

				if (null != datosDireccionesItem.getIdTipoDireccion()
						&& datosDireccionesItem.getIdTipoDireccion().length > 0) {
					List<String> idTiposDireccionTotal = new ArrayList<String>();
					List<String> idTiposDireccionFront = new ArrayList<String>();
					idTiposDireccionFront.addAll(Arrays.asList(datosDireccionesItem.getIdTipoDireccion()));
					if (null != tiposDireccion && tiposDireccion.size() > 0) {
						for (CenDireccionTipodireccion cenDireccionTipodireccion : tiposDireccion) {
							idTiposDireccionTotal.add(cenDireccionTipodireccion.getIdtipodireccion().toString());
						}
					}

					for (String uso : datosDireccionesItem.getIdTipoDireccion()) {
						if (idTiposDireccionTotal.contains(uso)) {
							idTiposDireccionTotal.remove(uso);
							idTiposDireccionFront.remove(uso);
						}
					}
					// Procesamos los distintos tipos de direccion que nos viene en la tabla
					if (null != idTiposDireccionTotal && idTiposDireccionTotal.size() > 0) {
						for (String idTipoDireccionBorrar : idTiposDireccionTotal) {
							CenDireccionTipodireccionKey TipoDireccionkey = new CenDireccionTipodireccionKey();
							TipoDireccionkey.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
							TipoDireccionkey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
							TipoDireccionkey.setIdinstitucion(Short.valueOf(idInstitucion));
							TipoDireccionkey.setIdtipodireccion(Short.valueOf(idTipoDireccionBorrar));
							// Eliminamos las ya existentes y que se han eliminado en el update
							LOGGER.info(
									"updateDirection() / cenDireccionTipodireccionMapper.deleteByExample() -> Entrada a cenDireccionTipodireccionMapper para eliminar tiposdedirecciones");

							cenDireccionTipodireccionMapper.deleteByPrimaryKey(TipoDireccionkey);

							LOGGER.info(
									"updateDirection() / cenNocolegiadoExtendsMapper.deleteByExample() -> Salida de cenDireccionTipodireccionMapper para eliminar tiposdedirecciones");
						}
					}
					if (null != idTiposDireccionFront && idTiposDireccionFront.size() > 0) {
						for (String idTipoDireccionInsertar : idTiposDireccionFront) {
							CenDireccionTipodireccion TipoDireccionrecord = new CenDireccionTipodireccion();
							TipoDireccionrecord.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
							TipoDireccionrecord.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
							TipoDireccionrecord.setIdinstitucion(Short.valueOf(idInstitucion));
							TipoDireccionrecord.setIdtipodireccion(Short.valueOf(idTipoDireccionInsertar));
							TipoDireccionrecord.setFechamodificacion(new Date());
							TipoDireccionrecord.setUsumodificacion(usuario.getIdusuario());
							LOGGER.info(
									"updateDirection() / cenDireccionTipodireccionMapper.insert() -> Entrada a cenDireccionTipodireccionMapper para insertar tiposdedirecciones");
							cenDireccionTipodireccionMapper.insert(TipoDireccionrecord);
							LOGGER.info(
									"updateDirection() / cenNocolegiadoExtendsMapper.insert() -> Salida de cenDireccionTipodireccionMapper para insertar tiposdedirecciones");
						}
					}

					// datos para auditoria
					CenDireccionesExample cenDireccionesExample = new CenDireccionesExample();
					cenDireccionesExample.createCriteria()
							.andIddireccionEqualTo(Long.valueOf(datosDireccionesItem.getIdDireccion()))
							.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
							.andIdinstitucionEqualTo(idInstitucion);
					listCenDireccionesAnterior = cenDireccionesExtendsMapper.selectByExample(cenDireccionesExample);
					cenDireccionesAnterior = listCenDireccionesAnterior.get(0);

					// } else {
					//
					// }

					// Actualizamos la direccion
					LOGGER.info(
							"updateDirection() / cenDireccionesExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenDireccionesExtendsMapper para actualizar direcciones");
					response = cenDireccionesExtendsMapper.updateByPrimaryKeySelective(direcciones);
					LOGGER.info(
							"updateDirection() / cenDireccionesExtendsMapper.updateByExampleSelective() -> Salida de cenDireccionesExtendsMapper para actualizar direcciones ");

					// comprobacion actualización
					if (response >= 1) {

						if (datosDireccionesItem.isEsColegiado()) {

							// Actualizamos la tabla cen_colegiados para mandar a sociedades
							CenColegiadoKey colegiadokey = new CenColegiadoKey();
							colegiadokey.setIdinstitucion(Short.valueOf(idInstitucion));
							colegiadokey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
							CenColegiado colegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadokey);

							colegiado.setFechamodificacion(new Date());
							colegiado.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"updateDirection() / cenColegiadoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenColegiadoExtendsMapper para actualizar el Colegiado");
							cenColegiadoExtendsMapper.updateByPrimaryKey(colegiado);
							LOGGER.info(
									"updateDirection() / cenColegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenColegiadoExtendsMapper para actualizar el Colegiado");

							LOGGER.info(
									"updateDirection() -> OK. Update para actualizar direcciones realizado correctamente");
							updateResponseDTO.setStatus(SigaConstants.OK);

						} else {

							// Actualizamos la tabla cen_nocolegiados para mandar a sociedades
							CenNocolegiadoKey noColegiadokey = new CenNocolegiadoKey();
							noColegiadokey.setIdinstitucion(Short.valueOf(idInstitucion));
							noColegiadokey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
							CenNocolegiado noColegiado = cenNocolegiadoExtendsMapper.selectByPrimaryKey(noColegiadokey);

							noColegiado.setFechamodificacion(new Date());
							noColegiado.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"updateDirection() / cenNocolegiadoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar el noColegiado");
							cenNocolegiadoExtendsMapper.updateByPrimaryKey(noColegiado);
							LOGGER.info(
									"updateDirection() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar el noColegiado");

							LOGGER.info(
									"updateDirection() -> OK. Update para actualizar direcciones realizado correctamente");
							updateResponseDTO.setStatus(SigaConstants.OK);
						}

						// AUDITORIA

						CenDirecciones cenDireccionesPosterior = new CenDirecciones();
						CenDireccionesKey keyDireccionesPosterior = new CenDireccionesKey();
						keyDireccionesPosterior.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
						keyDireccionesPosterior.setIdinstitucion(idInstitucion);
						keyDireccionesPosterior.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
						cenDireccionesPosterior = cenDireccionesExtendsMapper
								.selectByPrimaryKey(keyDireccionesPosterior);

						auditoriaCenHistoricoService.manageAuditoriaDatosDirecciones(cenDireccionesAnterior,
								cenDireccionesPosterior, "UPDATE", request, datosDireccionesItem.getMotivo());
					} else {
						LOGGER.info(
								"updateDirection() -> KO. Update para actualizar direcciones  NO realizado correctamente");
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("Error al actualizar la direccion");
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					}

				} else {
					LOGGER.info(
							"updateDirection() -> KO. Update para actualizar direcciones  NO realizado correctamente");
					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage(
							"No se puede actualizar la dirección con tipo CensoWeb, Traspaso, Facturación o Guardia, dado que, son tipos dirección únicos");
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}
			} else {
				LOGGER.warn(
						"updateDirection() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}

		} else {
			LOGGER.warn("updateDirection() -> idInstitucion del token nula");
		}

		LOGGER.info("updateDirection() -> Salida del servicio para actualizar direcciones ");
		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO createDirection(DatosDireccionesItem datosDireccionesItem, HttpServletRequest request) {

		LOGGER.info("createDirection() -> Entrada al servicio para insertar cuentas bancarias");
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"createDirection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"createDirection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			Long idDireccion = new Long(1);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Comprobamos si la persona es colegiado o no, ya que la obligatoriedad solo
				// vale para los colegiados
				CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
				cenColegiadoExample.createCriteria()
						.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
						.andIdinstitucionEqualTo(idInstitucion);

				List<CenColegiado> cenColegiadoList = cenColegiadoExtendsMapper.selectByExample(cenColegiadoExample);

				List<String> rdo = new ArrayList<String>();
				List<String> allRdo = new ArrayList<String>();
				List<String> tipoPrefentes = new ArrayList<String>();

				// Comprobamos si contiene algún tipo de dirección único
				for (int i = 0; i < datosDireccionesItem.getIdTipoDireccion().length; i++) {
					if (datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_CENSOWEB)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_GUARDIA)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_GUIAJUDICIAL)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_TRASPASO)
							|| datosDireccionesItem.getIdTipoDireccion()[i]
									.equals(SigaConstants.TIPO_DIR_FACTURACION)) {
						rdo.add(datosDireccionesItem.getIdTipoDireccion()[i]);
						allRdo.add(datosDireccionesItem.getIdTipoDireccion()[i]);
					}

					if (datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_PREFERENTE_EMAIL)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_EMAIL);
					} else if (datosDireccionesItem.getIdTipoDireccion()[i]
							.equals(SigaConstants.TIPO_DIR_PREFERENTE_CORREO)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_CORREO);
					} else if (datosDireccionesItem.getIdTipoDireccion()[i]
							.equals(SigaConstants.TIPO_DIR_PREFERENTE_SMS)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_SMS);
					} else if (datosDireccionesItem.getIdTipoDireccion()[i]
							.equals(SigaConstants.TIPO_DIR_PREFERENTE_FAX)) {
						tipoPrefentes.add(SigaConstants.DIR_PREFERENTE_FAX);
					}

					if (datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_REVISTA)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_RESIDENCIA)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_DESPACHO)
							|| datosDireccionesItem.getIdTipoDireccion()[i].equals(SigaConstants.TIPO_DIR_PUBLICA)) {
						allRdo.add(datosDireccionesItem.getIdTipoDireccion()[i]);
					}
				}

				String[] array = new String[rdo.size()];
				array = rdo.toArray(array);
				String[] allArray = new String[allRdo.size()];
				allArray = allRdo.toArray(allArray);
				datosDireccionesItem.setIdTipoDireccion(allArray);
				List<CenDireccionTipodireccion> cenDireccionTipodireccionList = new ArrayList<CenDireccionTipodireccion>();

				// Si es colegiado se realiza las restricciones
				if (null != cenColegiadoList && cenColegiadoList.size() > 0) {

					// Eliminamos de la dirección guardada el tipo de direccion de la nueva
					// dirección
					if (!rdo.isEmpty())

						cenDireccionTipodireccionList = cenDireccionTipodireccionMapper.select(
								datosDireccionesItem.getIdPersona(), String.valueOf(usuario.getIdinstitucion()), array,
								datosDireccionesItem.getIdDireccion());
					{
						for (CenDireccionTipodireccion tipoDir : cenDireccionTipodireccionList) {

							CenDireccionTipodireccionExample cenDireccionTipodireccionExample = new CenDireccionTipodireccionExample();
							cenDireccionTipodireccionExample.createCriteria()
									.andIddireccionEqualTo(tipoDir.getIddireccion())
									.andIdpersonaEqualTo(tipoDir.getIdpersona())
									.andIdinstitucionEqualTo(tipoDir.getIdinstitucion())
									.andIdtipodireccionEqualTo(tipoDir.getIdtipodireccion());

							cenDireccionTipodireccionMapper.deleteByExample(cenDireccionTipodireccionExample);
						}
					}

					// Eliminamos la direcciones prefente
					for (String p : tipoPrefentes) {

						if (p != SigaConstants.DIR_PREFERENTE_FAX) {
							CenDireccionesExample cenDireccionesPreferenteExample = new CenDireccionesExample();
							cenDireccionesPreferenteExample.createCriteria()
									.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
									.andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
									.andPreferenteLike("%" + p + "%")
									.andIddireccionNotEqualTo(Long.valueOf(datosDireccionesItem.getIdDireccion()));

							List<CenDirecciones> cenDireccionesList = cenDireccionesExtendsMapper
									.selectByExample(cenDireccionesPreferenteExample);
							String preferentes = "";

							for (CenDirecciones cenDireccion : cenDireccionesList) {
								cenDireccion.setUsumodificacion(usuario.getIdusuario());
								cenDireccion.setFechamodificacion(new Date());
								for (String s : tipoPrefentes) {
									preferentes = cenDireccion.getPreferente().replaceAll(s, "");
								}
								cenDireccion.setPreferente(preferentes);
								cenDireccionesExtendsMapper.updateByPrimaryKey(cenDireccion);
							}
						}
					}
				}

				// Luego creamos la nueva direccion, obtenemos el nuevo idDireccion
				List<DatosDireccionesItem> newIdDireccion = cenDireccionesExtendsMapper
						.selectNewIdDireccion(datosDireccionesItem.getIdPersona(), idInstitucion.toString());
				if (null != newIdDireccion && newIdDireccion.size() > 0) {
					if (null != newIdDireccion.get(0)) {
						idDireccion = Long.valueOf(newIdDireccion.get(0).getIdDireccion());
					}

				}

				// Rellenamos la entidad con la informacion a insertar
				CenDirecciones direcciones = new CenDirecciones();

				direcciones.setIddireccion(idDireccion);
				direcciones.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
				direcciones.setIdinstitucion(Short.valueOf(idInstitucion));
				direcciones.setFechamodificacion(new Date());
				direcciones.setUsumodificacion(usuario.getIdusuario());
				direcciones.setCodigopostal(datosDireccionesItem.getCodigoPostal());
				direcciones.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
				direcciones.setDomicilio(datosDireccionesItem.getDomicilio());
				direcciones.setFax1(datosDireccionesItem.getFax());
				direcciones.setIdpais(datosDireccionesItem.getIdPais());
				direcciones.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
				direcciones.setIdprovincia(datosDireccionesItem.getIdProvincia());
				direcciones.setMovil(datosDireccionesItem.getMovil());
				direcciones.setOtraprovincia(Short.valueOf(datosDireccionesItem.getOtraProvincia()));
				direcciones.setPaginaweb(datosDireccionesItem.getPaginaWeb());
				direcciones.setTelefono1(datosDireccionesItem.getTelefono());

				String preferentes = "";
				for (String s : tipoPrefentes) {
					preferentes += s;
					direcciones.setPreferente(preferentes);
				}

				if (datosDireccionesItem.getPoblacionExtranjera() != ""
						&& datosDireccionesItem.getPoblacionExtranjera() != null) {
					direcciones.setPoblacionextranjera(datosDireccionesItem.getPoblacionExtranjera());
				}

				LOGGER.info(
						"createDirection() / cenDireccionesExtendsMapper.insert() -> Entrada a cenDireccionesExtendsMapper para insertar direcciones");
				response = cenDireccionesExtendsMapper.insert(direcciones);
				LOGGER.info(
						"createDirection() / cenDireccionesExtendsMapper.insert() -> Salida de cenDireccionesExtendsMapper para insertar direcciones");

				if (null != datosDireccionesItem.getIdTipoDireccion()
						&& datosDireccionesItem.getIdTipoDireccion().length > 0) {

					List<String> idTiposDireccionFront = new ArrayList<String>();
					idTiposDireccionFront.addAll(Arrays.asList(datosDireccionesItem.getIdTipoDireccion()));

					for (String idTipoDireccionInsertar : datosDireccionesItem.getIdTipoDireccion()) {
						CenDireccionTipodireccion TipoDireccionrecord = new CenDireccionTipodireccion();
						TipoDireccionrecord.setIddireccion(idDireccion);
						TipoDireccionrecord.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
						TipoDireccionrecord.setIdinstitucion(Short.valueOf(idInstitucion));
						TipoDireccionrecord.setIdtipodireccion(Short.valueOf(idTipoDireccionInsertar));
						TipoDireccionrecord.setFechamodificacion(new Date());
						TipoDireccionrecord.setUsumodificacion(usuario.getIdusuario());
						LOGGER.info(
								"createDirection() / cenDireccionTipodireccionMapper.insert() -> Entrada a cenDireccionTipodireccionMapper para insertar los tipos de direcciones");
						cenDireccionTipodireccionMapper.insert(TipoDireccionrecord);
						LOGGER.info(
								"createDirection() / cenDireccionTipodireccionMapper.insert() -> Salida de cenDireccionTipodireccionMapper para insertar los tipos de direcciones");
					}

				}

				// comprobacion actualización
				if (response >= 1) {

					if (datosDireccionesItem.isEsColegiado()) {

						// Actualizamos la tabla cen_colegiados para mandar a sociedades
						CenColegiadoKey colegiadokey = new CenColegiadoKey();
						colegiadokey.setIdinstitucion(Short.valueOf(idInstitucion));
						colegiadokey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
						CenColegiado colegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadokey);

						colegiado.setFechamodificacion(new Date());
						colegiado.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"updateDirection() / cenColegiadoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenColegiadoExtendsMapper para actualizar el Colegiado");
						cenColegiadoExtendsMapper.updateByPrimaryKey(colegiado);
						LOGGER.info(
								"updateDirection() / cenColegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenColegiadoExtendsMapper para actualizar el Colegiado");

						LOGGER.info("createDirection() -> OK. Insert para direcciones realizado correctamente");
						insertResponseDTO.setId(idDireccion.toString());
						insertResponseDTO.setStatus(SigaConstants.OK);

					} else {
						// Actualizamos la tabla cen_nocolegiados para mandar a sociedades
						CenNocolegiadoKey noColegiadokey = new CenNocolegiadoKey();
						noColegiadokey.setIdinstitucion(Short.valueOf(idInstitucion));
						noColegiadokey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
						CenNocolegiado noColegiado = cenNocolegiadoExtendsMapper.selectByPrimaryKey(noColegiadokey);

						noColegiado.setFechamodificacion(new Date());
						noColegiado.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"updateDirection() / cenNocolegiadoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar el noColegiado");
						cenNocolegiadoExtendsMapper.updateByPrimaryKey(noColegiado);
						LOGGER.info(
								"updateDirection() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar el noColegiado");

						LOGGER.info("createDirection() -> OK. Insert para direcciones realizado correctamente");
						insertResponseDTO.setId(idDireccion.toString());
						insertResponseDTO.setStatus(SigaConstants.OK);
					}

					// AUDITORIA

					CenDirecciones cenDireccionesPosterior = new CenDirecciones();

					CenDireccionesKey key = new CenDireccionesKey();
					key.setIdinstitucion(idInstitucion);
					key.setIddireccion(idDireccion);
					key.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
					cenDireccionesPosterior = cenDireccionesExtendsMapper.selectByPrimaryKey(key);

					auditoriaCenHistoricoService.manageAuditoriaDatosDirecciones(null, cenDireccionesPosterior,
							"INSERT", request, datosDireccionesItem.getMotivo());
				} else {
					LOGGER.info("createDirection() -> KO. Insert para direcciones  NO realizado correctamente");
					insertResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al insertar la dirección");
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}

			} else {
				LOGGER.warn(
						"createDirection() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}

		} else {
			LOGGER.warn("createDirection() -> idInstitucion del token nula");
		}

		LOGGER.info("createDirection() -> Salida del servicio para insertar direcciones ");
		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO solicitudUpdateDirection(DatosDireccionesItem datosDireccionesItem,
			HttpServletRequest request) {

		LOGGER.info("updateDirection() -> Entrada al servicio para actualizar direcciones");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		List<CenDirecciones> listCenDireccionesAnterior = new ArrayList<CenDirecciones>();
		CenDirecciones cenDireccionesAnterior = new CenDirecciones();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateDirection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateDirection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// datos para auditoria
				CenDireccionesExample cenDireccionesExample = new CenDireccionesExample();
				cenDireccionesExample.createCriteria()
						.andIddireccionEqualTo(Long.valueOf(datosDireccionesItem.getIdDireccion()))
						.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()))
						.andIdinstitucionEqualTo(idInstitucion);
				listCenDireccionesAnterior = cenDireccionesExtendsMapper.selectByExample(cenDireccionesExample);
				cenDireccionesAnterior = listCenDireccionesAnterior.get(0);

				// Consultamos la dirección a actualizar
				CenDireccionesKey key = new CenDireccionesKey();
				key.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
				key.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
				key.setIdinstitucion(Short.valueOf(idInstitucion));
				CenDirecciones direcciones = cenDireccionesExtendsMapper.selectByPrimaryKey(key);

				direcciones.setFechamodificacion(new Date());
				direcciones.setUsumodificacion(usuario.getIdusuario());
				direcciones.setCodigopostal(datosDireccionesItem.getCodigoPostal());
				direcciones.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
				direcciones.setDomicilio(datosDireccionesItem.getDomicilio());
				direcciones.setFax1(datosDireccionesItem.getFax());
				direcciones.setIdpais(datosDireccionesItem.getIdPais());
				direcciones.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
				direcciones.setIdprovincia(datosDireccionesItem.getIdProvincia());
				direcciones.setMovil(datosDireccionesItem.getMovil());
				direcciones.setOtraprovincia(Short.valueOf(datosDireccionesItem.getOtraProvincia()));
				direcciones.setPaginaweb(datosDireccionesItem.getPaginaWeb());
				direcciones.setTelefono1(datosDireccionesItem.getTelefono());
				if (datosDireccionesItem.getPoblacionExtranjera() != ""
						&& datosDireccionesItem.getPoblacionExtranjera() != null) {
					direcciones.setPoblacionextranjera(datosDireccionesItem.getPoblacionExtranjera());
				}

				CenDireccionTipodireccionExample tipoDireccionexample = new CenDireccionTipodireccionExample();
				tipoDireccionexample.createCriteria()
						.andIddireccionEqualTo(Long.valueOf(datosDireccionesItem.getIdDireccion()))
						.andIdpersonaEqualTo(Long.valueOf(datosDireccionesItem.getIdPersona()));
				// Consultamos los tipos de direccion de la direccion a actualizar
				List<CenDireccionTipodireccion> tiposDireccion = cenDireccionTipodireccionMapper
						.selectByExample(tipoDireccionexample);

				// Gestionamos los abonos que nos llegan
				if (null != datosDireccionesItem.getIdTipoDireccion()
						&& datosDireccionesItem.getIdTipoDireccion().length > 0) {
					List<String> idTiposDireccionTotal = new ArrayList<String>();
					List<String> idTiposDireccionFront = new ArrayList<String>();
					idTiposDireccionFront.addAll(Arrays.asList(datosDireccionesItem.getIdTipoDireccion()));
					if (null != tiposDireccion && tiposDireccion.size() > 0) {
						for (CenDireccionTipodireccion cenDireccionTipodireccion : tiposDireccion) {
							idTiposDireccionTotal.add(cenDireccionTipodireccion.getIdtipodireccion().toString());
						}
					}

					for (String uso : datosDireccionesItem.getIdTipoDireccion()) {
						if (idTiposDireccionTotal.contains(uso)) {
							idTiposDireccionTotal.remove(uso);
							idTiposDireccionFront.remove(uso);
						}
					}
					// Procesamos los distintos tipos de direccion que nos viene en la tabla
					if (null != idTiposDireccionTotal && idTiposDireccionTotal.size() > 0) {
						for (String idTipoDireccionBorrar : idTiposDireccionTotal) {
							CenDireccionTipodireccionKey TipoDireccionkey = new CenDireccionTipodireccionKey();
							TipoDireccionkey.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
							TipoDireccionkey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
							TipoDireccionkey.setIdinstitucion(Short.valueOf(idInstitucion));
							TipoDireccionkey.setIdtipodireccion(Short.valueOf(idTipoDireccionBorrar));
							// Eliminamos las ya existentes y que se han eliminado en el update
							LOGGER.info(
									"updateDirection() / cenDireccionTipodireccionMapper.deleteByExample() -> Entrada a cenDireccionTipodireccionMapper para eliminar tiposdedirecciones");
							// cenDireccionTipodireccionMapper.deleteByPrimaryKey(TipoDireccionkey);
							LOGGER.info(
									"updateDirection() / cenNocolegiadoExtendsMapper.deleteByExample() -> Salida de cenDireccionTipodireccionMapper para eliminar tiposdedirecciones");
						}
					}
					if (null != idTiposDireccionFront && idTiposDireccionFront.size() > 0) {
						for (String idTipoDireccionInsertar : idTiposDireccionFront) {
							CenDireccionTipodireccion TipoDireccionrecord = new CenDireccionTipodireccion();
							TipoDireccionrecord.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
							TipoDireccionrecord.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
							TipoDireccionrecord.setIdinstitucion(Short.valueOf(idInstitucion));
							TipoDireccionrecord.setIdtipodireccion(Short.valueOf(idTipoDireccionInsertar));
							TipoDireccionrecord.setFechamodificacion(new Date());
							TipoDireccionrecord.setUsumodificacion(usuario.getIdusuario());
							LOGGER.info(
									"updateDirection() / cenDireccionTipodireccionMapper.insert() -> Entrada a cenDireccionTipodireccionMapper para insertar tiposdedirecciones");
							cenDireccionTipodireccionMapper.insert(TipoDireccionrecord);
							LOGGER.info(
									"updateDirection() / cenNocolegiadoExtendsMapper.insert() -> Salida de cenDireccionTipodireccionMapper para insertar tiposdedirecciones");
						}
					}

				} else {

					// Eliminamos los tipos de dirección

					LOGGER.info(
							"updateDirection() / cenDireccionTipodireccionMapper.deleteByExample() -> Entrada a cenDireccionTipodireccionMapper para eliminar tiposdedirecciones");
					// cenDireccionTipodireccionMapper.deleteByExample(tipoDireccionexample);
					LOGGER.info(
							"updateDirection() / cenNocolegiadoExtendsMapper.deleteByExample() -> Salida de cenDireccionTipodireccionMapper para eliminar tiposdedirecciones");
				}

				// Actualizamos la direccion
				LOGGER.info(
						"updateDirection() / cenDireccionesExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenDireccionesExtendsMapper para actualizar direcciones");
				response = cenDireccionesExtendsMapper.updateByPrimaryKeySelective(direcciones);
				LOGGER.info(
						"updateDirection() / cenDireccionesExtendsMapper.updateByExampleSelective() -> Salida de cenDireccionesExtendsMapper para actualizar direcciones ");

				// comprobacion actualización
				if (response >= 1) {

					if (datosDireccionesItem.isEsColegiado()) {

						// Actualizamos la tabla cen_colegiados para mandar a sociedades
						CenColegiadoKey colegiadokey = new CenColegiadoKey();
						colegiadokey.setIdinstitucion(Short.valueOf(idInstitucion));
						colegiadokey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
						CenColegiado colegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadokey);

						colegiado.setFechamodificacion(new Date());
						colegiado.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"updateDirection() / cenColegiadoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenColegiadoExtendsMapper para actualizar el Colegiado");
						cenColegiadoExtendsMapper.updateByPrimaryKey(colegiado);
						LOGGER.info(
								"updateDirection() / cenColegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenColegiadoExtendsMapper para actualizar el Colegiado");

						LOGGER.info(
								"updateDirection() -> OK. Update para actualizar direcciones realizado correctamente");
						updateResponseDTO.setStatus(SigaConstants.OK);

					} else {

						// Actualizamos la tabla cen_nocolegiados para mandar a sociedades
						CenNocolegiadoKey noColegiadokey = new CenNocolegiadoKey();
						noColegiadokey.setIdinstitucion(Short.valueOf(idInstitucion));
						noColegiadokey.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
						CenNocolegiado noColegiado = cenNocolegiadoExtendsMapper.selectByPrimaryKey(noColegiadokey);

						noColegiado.setFechamodificacion(new Date());
						noColegiado.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"updateDirection() / cenNocolegiadoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar el noColegiado");
						cenNocolegiadoExtendsMapper.updateByPrimaryKey(noColegiado);
						LOGGER.info(
								"updateDirection() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar el noColegiado");

						LOGGER.info(
								"updateDirection() -> OK. Update para actualizar direcciones realizado correctamente");
						updateResponseDTO.setStatus(SigaConstants.OK);
					}

					// AUDITORIA

					CenDirecciones cenDireccionesPosterior = new CenDirecciones();
					CenDireccionesKey keyDireccionesPosterior = new CenDireccionesKey();
					keyDireccionesPosterior.setIddireccion(Long.valueOf(datosDireccionesItem.getIdDireccion()));
					keyDireccionesPosterior.setIdinstitucion(idInstitucion);
					keyDireccionesPosterior.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
					cenDireccionesPosterior = cenDireccionesExtendsMapper.selectByPrimaryKey(keyDireccionesPosterior);

					auditoriaCenHistoricoService.manageAuditoriaDatosDirecciones(cenDireccionesAnterior,
							cenDireccionesPosterior, "UPDATE", request, datosDireccionesItem.getMotivo());
				} else {
					LOGGER.info(
							"updateDirection() -> KO. Update para actualizar direcciones  NO realizado correctamente");
					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al actualizar la direccion");
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}

			} else {
				LOGGER.warn(
						"updateDirection() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}

		} else {
			LOGGER.warn("updateDirection() -> idInstitucion del token nula");
		}

		LOGGER.info("updateDirection() -> Salida del servicio para actualizar direcciones ");
		return updateResponseDTO;
	}

	public InsertResponseDTO solicitudCreateDirection(DatosDireccionesItem datosDireccionesItem,
			HttpServletRequest request) {

		LOGGER.info("createDirection() -> Entrada al servicio para insertar cuentas bancarias");
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int responseUpdate = 3;
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"createDirection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"createDirection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				CenSolimodidirecciones direcciones = new CenSolimodidirecciones();

				NewIdDTO idSolicitudBD = cenSolimodidireccionesExtendsMapper
						.getMaxIdSolicitud(String.valueOf(idInstitucion), datosDireccionesItem.getIdPersona());
				if (idSolicitudBD == null) {
					direcciones.setIdsolicitud(Long.parseLong("1"));
				} else {
					int idCv = Integer.parseInt(idSolicitudBD.getNewId()) + 1;
					direcciones.setIdsolicitud(Long.parseLong("" + idCv));
				}
				Long idDireccion = new Long(1);

				// Rellenamos la entidad con la informacion a insertar

				if (datosDireccionesItem.getIdDireccion() != null) {

					idDireccion = Long.parseLong(datosDireccionesItem.getIdDireccion());

				} else {

					List<DatosDireccionesItem> newIdDireccion = cenSolimodidireccionesExtendsMapper
							.selectNewIdDireccion(datosDireccionesItem.getIdPersona(), idInstitucion.toString());
					if (null != newIdDireccion && newIdDireccion.size() > 0) {
						if (null != newIdDireccion.get(0)) {
							idDireccion = Long.valueOf(newIdDireccion.get(0).getIdDireccion());
						}
					} // TENEMOS EN CUENTA QUE EL CAMPO IDDIRECCIÓN O SE HAGA NULLABLE O SE ELIMINE LA
						// FK PARA PERMITIR QUE SE GUARDE "newIdDireccion" EN CASO DE SER UNA SOLICITUD
						// DE CREACIÓN.
				}

				direcciones.setFechaalta(new Date());
				direcciones.setIddireccion(idDireccion);
				direcciones.setMotivo(datosDireccionesItem.getMotivo());
				direcciones.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
				direcciones.setIdinstitucion(Short.valueOf(idInstitucion));
				direcciones.setFechamodificacion(new Date());
				direcciones.setUsumodificacion(usuario.getIdusuario());
				direcciones.setCodigopostal(datosDireccionesItem.getCodigoPostal());
				direcciones.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
				direcciones.setDomicilio(datosDireccionesItem.getDomicilio());
				direcciones.setFax1(datosDireccionesItem.getFax());
				direcciones.setIdpais(datosDireccionesItem.getIdPais());
				direcciones.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
				direcciones.setIdprovincia(datosDireccionesItem.getIdProvincia());
				direcciones.setMovil(datosDireccionesItem.getMovil());
				direcciones.setOtraprovincia(Short.valueOf(datosDireccionesItem.getOtraProvincia()));
				direcciones.setPaginaweb(datosDireccionesItem.getPaginaWeb());
				direcciones.setTelefono1(datosDireccionesItem.getTelefono());
				// direcciones.setIdestadosolic(Short.parseShort("10"));

				List<ComboItem> autoAceptar = cenSolicitmodifdatosbasicosMapper
						.getAutoAceptar(String.valueOf(idInstitucion));

				if (autoAceptar.get(0).getLabel().equals("S")) {
					direcciones.setIdestadosolic(Short.parseShort("20"));
				} else {
					direcciones.setIdestadosolic(Short.parseShort("10"));
				}

				if (datosDireccionesItem.getPoblacionExtranjera() != ""
						&& datosDireccionesItem.getPoblacionExtranjera() != null) {
					direcciones.setPoblacionextranjera(datosDireccionesItem.getPoblacionExtranjera());
				}

				LOGGER.info(
						"createDirection() / cenDireccionesExtendsMapper.insert() -> Entrada a cenDireccionesExtendsMapper para insertar direcciones");
				response = cenSolimodidireccionesExtendsMapper.insert(direcciones);
				LOGGER.info(
						"createDirection() / cenDireccionesExtendsMapper.insert() -> Salida de cenDireccionesExtendsMapper para insertar direcciones");

				if (autoAceptar.get(0).getLabel().equals("S")) {
					CenDirecciones modificacion = new CenDirecciones();
					modificacion.setIdinstitucion(idInstitucion);
					modificacion.setIdpersona(Long.parseLong(datosDireccionesItem.getIdPersona()));
					modificacion.setFechamodificacion(new Date());
					modificacion.setUsumodificacion(usuario.getIdusuario());
					modificacion.setIddireccion(Long.parseLong(datosDireccionesItem.getIdDireccion()));
					// modificacion.setPreferente(solicitud.getPreferente());
					modificacion.setCodigopostal(datosDireccionesItem.getCodigoPostal());
					if (datosDireccionesItem.getTelefono() != null) {
						modificacion.setTelefono1(datosDireccionesItem.getTelefono());
					}
					// modificacion.setTelefono2(solicitud.getTelefono2());
					if (datosDireccionesItem.getDomicilio() != null) {
						modificacion.setDomicilio(datosDireccionesItem.getDomicilio());
					}
					if (datosDireccionesItem.getMovil() != null) {
						modificacion.setMovil(datosDireccionesItem.getMovil());
					}
					if (datosDireccionesItem.getFax() != null) {
						modificacion.setFax1(datosDireccionesItem.getFax());
					}
					// modificacion.setFax2(solicitud.getFax2());
					if (datosDireccionesItem.getCorreoElectronico() != null) {
						modificacion.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
					}
					if (datosDireccionesItem.getPaginaWeb() != null) {
						modificacion.setPaginaweb(datosDireccionesItem.getPaginaWeb());
					}
					modificacion.setIdpais(datosDireccionesItem.getIdPais());
					if (datosDireccionesItem.getIdProvincia() != null) {
						modificacion.setIdprovincia(datosDireccionesItem.getIdProvincia());
					}
					if (datosDireccionesItem.getIdPoblacion() != null) {
						modificacion.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
					}
					if (datosDireccionesItem.getPoblacionExtranjera() != null) {
						modificacion.setPoblacionextranjera(datosDireccionesItem.getPoblacionExtranjera());
					}
					if (datosDireccionesItem.getOtraProvincia() != null) {
						modificacion.setOtraprovincia(Short.parseShort(datosDireccionesItem.getOtraProvincia()));
					}

					responseUpdate = cenDireccionesExtendsMapper.updateByPrimaryKeySelective(modificacion);
					error.setCode(200);
					error.setDescription(
							"Su petición ha sido aceptada automáticamente. Puede ver ya los datos actualizados");
				} else {
					GenParametrosExample ejemploParam = new GenParametrosExample();
					List<GenParametros> xDias = new ArrayList<GenParametros>();
					ejemploParam.createCriteria().andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
							.andIdinstitucionEqualTo(idInstitucion);
					xDias = genParametrosMapper.selectByExample(ejemploParam);
					error.setCode(200);
					if (xDias.size() == 0) {
						GenParametrosExample ejemploParam2 = new GenParametrosExample();
						ejemploParam2.createCriteria()
								.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
								.andIdinstitucionEqualTo((short) 2000);
						xDias = genParametrosMapper.selectByExample(ejemploParam2);
					}
					error.setDescription("Su petición ha sido registrada y será revisada en los próximos "
							+ xDias.get(0).getValor()
							+ " días. Puede comprobar el estado de su petición en el menú Solicitudes de modificación");
				}

				insertResponseDTO.setError(error);

				// comprobacion actualización
				if (response >= 1) {
					LOGGER.info("createDirection() -> OK. Insert para solicitud direcciones realizado correctamente");
					insertResponseDTO.setId(idDireccion.toString());
					insertResponseDTO.setStatus(SigaConstants.OK);
					if (responseUpdate == 1) {
						LOGGER.info("createDirection() -> OK. Solicitud de direccion procesada correctamente");

					}
				} else {
					LOGGER.info(
							"createDirection() -> KO. InsertSolicitud para direcciones  NO realizado correctamente");
					insertResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al insertar la Solicitud");
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}

			} else {
				LOGGER.warn(
						"createDirection() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}

		} else {
			LOGGER.warn("createDirection() -> idInstitucion del token nula");
		}

		LOGGER.info("createDirection() -> Salida del servicio para insertar direcciones ");
		return insertResponseDTO;
	}

	@Override
	public InsertResponseDTO duplicateDirection(DatosDireccionesItem datosDireccionesItem, HttpServletRequest request) {
		LOGGER.info("duplicateDirection() -> Entrada al servicio para duplicar direcciones");
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"duplicateDirection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"duplicateDirection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			Long idDireccion = new Long(1);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Obtenemos el nuevo idDireccion
				List<DatosDireccionesItem> newIdDireccion = cenDireccionesExtendsMapper
						.selectNewIdDireccion(datosDireccionesItem.getIdPersona(), idInstitucion.toString());
				if (null != newIdDireccion && newIdDireccion.size() > 0) {
					if (null != newIdDireccion.get(0)) {
						idDireccion = Long.valueOf(newIdDireccion.get(0).getIdDireccion());
					}

				}
				// Rellenamos la entidad con la direccion a duplicar
				CenDirecciones direcciones = new CenDirecciones();

				direcciones.setIddireccion(idDireccion);
				direcciones.setIdpersona(Long.valueOf(datosDireccionesItem.getIdPersona()));
				direcciones.setIdinstitucion(Short.valueOf(idInstitucion));
				direcciones.setFechamodificacion(new Date());
				direcciones.setUsumodificacion(usuario.getIdusuario());
				direcciones.setCodigopostal(datosDireccionesItem.getCodigoPostal());
				direcciones.setCorreoelectronico(datosDireccionesItem.getCorreoElectronico());
				direcciones.setDomicilio(datosDireccionesItem.getDomicilio());
				direcciones.setFax1(datosDireccionesItem.getFax());
				direcciones.setIdpais(datosDireccionesItem.getIdPais());
				direcciones.setIdpoblacion(datosDireccionesItem.getIdPoblacion());
				direcciones.setIdprovincia(datosDireccionesItem.getIdProvincia());
				direcciones.setMovil(datosDireccionesItem.getMovil());
				direcciones.setOtraprovincia(Short.valueOf(datosDireccionesItem.getOtraProvincia()));
				direcciones.setPaginaweb(datosDireccionesItem.getPaginaWeb());
				direcciones.setTelefono1(datosDireccionesItem.getTelefono());
				direcciones.setFechabaja(new Date());

				if (datosDireccionesItem.getPoblacionExtranjera() != ""
						&& datosDireccionesItem.getPoblacionExtranjera() != null) {
					direcciones.setPoblacionextranjera(datosDireccionesItem.getPoblacionExtranjera());
				}

				LOGGER.info(
						"duplicateDirection() / cenDireccionesExtendsMapper.insert() -> Entrada a cenDireccionesExtendsMapper para duplicar direcciones");
				response = cenDireccionesExtendsMapper.insert(direcciones);
				LOGGER.info(
						"duplicateDirection() / cenDireccionesExtendsMapper.insert() -> Salida de cenDireccionesExtendsMapper para duplicar direcciones");

				if(response == 0) {
					LOGGER.info("duplicateDirection() -> KO. Duplicar direccion  NO realizado correctamente");
					insertResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al duplicar la direccion");
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}else {
					insertResponseDTO.setStatus(SigaConstants.OK);
					insertResponseDTO.setId(idDireccion.toString());
						LOGGER.info("createDirection() -> OK. Solicitud de direccion procesada correctamente");
				}
			} else {
				LOGGER.warn(
						"createDirection() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}

		} else {
			LOGGER.warn("createDirection() -> idInstitucion del token nula");
		}

		LOGGER.info("createDirection() -> Salida del servicio para insertar direcciones ");
		return insertResponseDTO;
	}
}
