package org.itcgae.siga.cen.services.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.IFichaDatosColegialesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColacambioletrado;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoExample;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoKey;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.entities.CenDireccionTipodireccionExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColacambioletradoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscolegialesestadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionTipodireccionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudincorporacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposseguroExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiascolegiadoExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional(timeout=2400)
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

	@Autowired
	private CenSolicitudincorporacionExtendsMapper _cenSolicitudincorporacionExtendsMapper;

	@Autowired
	private AdmConfigMapper admConfigMapper;

	@Autowired
	private IAuditoriaCenHistoricoService auditoriaCenHistoricoService;

	@Autowired
	private GenDiccionarioMapper genDiccionarioMapper;
    @Autowired
    private ScsGuardiascolegiadoExtendsMapper _scsGuardiascolegiadoMapper;
	
	@Autowired
	private CenColacambioletradoExtendsMapper cenColacambioletradoMapper;

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
//					ComboItem element = new ComboItem();
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);

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

				CenColegiadoKey colegiadoKey = new CenColegiadoKey();
				colegiadoKey.setIdinstitucion(idInstitucion);
				colegiadoKey.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				CenColegiado cenColegiadoAnterior = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadoKey);
				CenColegiado colegiado = new CenColegiado();
				colegiado.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				colegiado.setIdinstitucion(idInstitucion);
				colegiado.setUsumodificacion(usuario.getIdusuario());
				/*if (colegiadoItem.getNumColegiado() != null) {
					colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
				}*/
				if (colegiadoItem.getIdTiposSeguro() != null && colegiadoItem.getIdTiposSeguro() != "") {
					colegiado.setIdtiposseguro(Short.parseShort(colegiadoItem.getIdTiposSeguro()));
				} else {
					colegiado.setIdtiposseguro(null);
				}
				if (colegiadoItem.getSituacionResidente() != null) {
					colegiado.setSituacionresidente(
							colegiadoItem.getSituacionResidente().equalsIgnoreCase("si") ? "1" : "0");
				} else {
					colegiado.setSituacionresidente("0");
				}

				if (colegiadoItem.getComunitario() != null) {
					colegiado.setComunitario(colegiadoItem.getComunitario());
					if (colegiadoItem.getNumColegiado() != null) {
						if (colegiadoItem.getComunitario().equals("1")) {
							colegiado.setNcomunitario(colegiadoItem.getNumColegiado());
						}else{
							
							colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
						}
					}
				}else{
					if (colegiadoItem.getNumColegiado() != null) {
						colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
					}
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

					// Añadimos auditoria
					if (!UtilidadesString.esCadenaVacia(colegiadoItem.getMotivo())) {

						colegiadoKey.setIdinstitucion(idInstitucion);
						colegiadoKey.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
						CenColegiado cenColegiadoPosterior = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadoKey);
						// AUDITORIA => actualizamos cen_historico si todo es correcto
						auditoriaCenHistoricoService.manageAuditoriaDatosColegiales(cenColegiadoAnterior,
								cenColegiadoPosterior, "UPDATE", request, colegiadoItem.getMotivo());
					}

					response.setStatus(SigaConstants.OK);

				} else {
					response.setStatus(SigaConstants.KO);
				}

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
					CenDirecciones direccionCensoWeb = null;

					// Comprobamos que sea cambio de ejerciente a no ejerciente
					CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
					cenDatoscolegialesestadoExample.createCriteria()
							.andIdinstitucionEqualTo(Short.valueOf(colegiadoItem.getIdInstitucion()))
							.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));

					List<CenDatoscolegialesestado> cenDatoscolegialesestadosList = cenDatoscolegialesestadoExtendsMapper
							.selectByExample(cenDatoscolegialesestadoExample);

					// ACTUALIZAMOS CEN_COLEGIADO
					CenColegiadoKey colegiadoKey = new CenColegiadoKey();
					colegiadoKey.setIdinstitucion(idInstitucion);
					colegiadoKey.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
					CenColegiado colegiado = new CenColegiado();
					colegiado.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
					colegiado.setIdinstitucion(idInstitucion);
					colegiado.setUsumodificacion(usuario.getIdusuario());
					if (colegiadoItem.getNumColegiado() != null) {
						colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
					}
					if (colegiadoItem.getIdTiposSeguro() != null && colegiadoItem.getIdTiposSeguro() != "") {
						colegiado.setIdtiposseguro(Short.parseShort(colegiadoItem.getIdTiposSeguro()));
					} else {
						colegiado.setIdtiposseguro(null);
					}
					if (colegiadoItem.getSituacionResidente() != null) {
						colegiado.setSituacionresidente(colegiadoItem.getSituacionResidente());
					} else {
						colegiado.setSituacionresidente("0");
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

					if (responseUpdate >= 1) {
						// ACTUALIZAMOS CEN_DATOSCOLEGIALESESTADO

						if (null != cenDatoscolegialesestadosList && cenDatoscolegialesestadosList.size() > 0) {
							LOGGER.info("datosColegialesInsertEstado() / Recorremos los estados colegiales");
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
							if (null != tipoCensoWeb) {
							
								for (CenDirecciones cenDireccion : cenDireccionesList) {
									if (cenDireccion.getIddireccion().equals(tipoCensoWeb.getIddireccion())) {
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
										cenDireccionTipodireccion.setIddireccion(direccionCensoWeb.getIddireccion());
										cenDireccionTipodireccion.setIdtipodireccion(tipo);
										LOGGER.info(
												"datosColegialesInsertEstado() / cenDireccionTipodireccionExtendsMapper.insert -> Entrada a insertar direcciones");
										cenDireccionTipodireccionExtendsMapper.insert(cenDireccionTipodireccion);

									}
								}

								// Si hay que añadir algun tipo de direccion lo añadimos
								if (null != addTipoDireccionesPreferentes && addTipoDireccionesPreferentes.size() > 0) {
									String addPref = direccionCensoWeb.getPreferente();
									if (addPref == null) {
										addPref = "";
									}
									for (String tipoPreferente : addTipoDireccionesPreferentes) {
										addPref += tipoPreferente;
									}

									direccionCensoWeb.setPreferente(addPref);
									direccionCensoWeb.setUsumodificacion(usuario.getIdusuario());
									direccionCensoWeb.setFechamodificacion(new Date());
									LOGGER.info(
											"datosColegialesInsertEstado() / cenDireccionTipodireccionExtendsMapper.update -> Entrada a actualizar direcciones");
									int response3 = cenDireccionesExtendsMapper.updateByPrimaryKey(direccionCensoWeb);
								}
							}
						}

						datosColegiales.setFechaestado(colegiadoItem.getFechaEstado());
						datosColegiales.setFechamodificacion(new Date());
						datosColegiales.setIdestado(Short.valueOf(colegiadoItem.getSituacion()));

						datosColegiales.setIdinstitucion(Short.valueOf(colegiadoItem.getIdInstitucion()));
						datosColegiales.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
						datosColegiales.setUsumodificacion(usuario.getIdusuario());
						if(colegiadoItem.getObservaciones() != null) {
							datosColegiales.setObservaciones(colegiadoItem.getObservaciones());
						}else {
							datosColegiales.setObservaciones("");
						}
						datosColegiales.setSituacionresidente(colegiadoItem.getSituacionResidente());
						int resultado = cenDatoscolegialesestadoExtendsMapper.insert(datosColegiales);

						// Llamamos al PL para mantener los colegiados
						int res = 0;
					
						if(direccionCensoWeb != null && direccionCensoWeb.getIddireccion() != null) {
							res = insertarCambioEnCola(SigaConstants.COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION,usuario.getIdinstitucion().intValue(),
									datosColegiales.getIdpersona(), direccionCensoWeb.getIddireccion(), usuario.getIdusuario());
						}else {
							res = insertarCambioEnCola(SigaConstants.COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION,usuario.getIdinstitucion().intValue(),
									datosColegiales.getIdpersona(), null, usuario.getIdusuario());
						}
						
						
						if(res <=0) {
							LOGGER.error("Error al insertar en la cola de actualizacion de letrados. Institucion: " +
									usuario.getIdinstitucion() + ", idpersona: " +
									datosColegiales.getIdpersona() + ", usumodificacion: " +
									usuario.getIdusuario());
						}else {
							LOGGER.info(
									"updateDirection() -> OK al insertar en la cola de actualizacion de letrados.");
						}
						/*Object[] paramMandatos = new Object[5];
						paramMandatos[0] = datosColegiales.getIdpersona().toString();
						paramMandatos[1] = usuario.getIdinstitucion().toString();
						paramMandatos[2] = new Long(30).toString();
						paramMandatos[3] = null;
						paramMandatos[4] = usuario.getIdusuario().toString();
						String resultadoPl[] = new String[2];
						try {
							LOGGER.info("datosColegialesInsertEstado() / llamada PL actualizar datos letrado");
							resultadoPl = callPLProcedure("{call Pkg_Siga_Censo.Actualizardatosletrado(?,?,?,?,?,?,?)}",
									2, paramMandatos);

							LOGGER.info("datosColegialesInsertEstado() / salida PL actualizar datos letrado");
						} catch (IOException | NamingException | SQLException e) {
							// TODO Auto-generated catch block
							LOGGER.error("Error Datos Colegiales", e);

							e.printStackTrace();
						}*/

						if (resultado == 1) {
							response.setStatus(SigaConstants.OK);
							// Llamamos al PL para mantener los colegiados
							Object[] paramTurno = new Object[3];
							paramTurno[0] = usuario.getIdinstitucion().toString();
							paramTurno[1] = datosColegiales.getIdpersona().toString();
							paramTurno[2] = usuario.getIdusuario().toString();
							String resultadoPlTurno[] = new String[2];
							try {
								LOGGER.info(
										"datosColegialesInsertEstado() / llamada PL actualizar cambio estado colegial");
								resultadoPlTurno = callPLProcedure(
										"{call Pkg_Siga_Cambio_Colegiacion.Revision_Cambio_Estadocolegial(?,?,?,?,?)}",
										2, paramTurno);

								LOGGER.info(
										"datosColegialesInsertEstado() / salida PL actualizar cambio estado colegial");
							} catch (IOException | NamingException | SQLException e) {
								// TODO Auto-generated catch block
								LOGGER.error("Error Datos Colegiales", e);
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								e.printStackTrace();
							}
							if (resultadoPlTurno[0].equals("0")) {
								response.setStatus(SigaConstants.OK);
								org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
								GenDiccionarioExample example = new GenDiccionarioExample();
								example.createCriteria().andIdrecursoEqualTo(resultadoPlTurno[1])
										.andIdlenguajeEqualTo(usuario.getIdlenguaje());
								List<GenDiccionario> diccionario = genDiccionarioMapper.selectByExample(example);
								if (null != diccionario && diccionario.size() > 0) {
									error.setMessage(diccionario.get(0).getDescripcion());
								} else {
									error.setMessage(resultadoPlTurno[1]);
								}
								response.setError(error);
								
								// AUDITORIA si se insertó un estado correctamente
								
								switch (Integer.parseInt(colegiadoItem.getSituacion())) {
								case SigaConstants.ESTADO_COLEGIAL_SINEJERCER:
									auditoriaCenHistoricoService.manageAuditoriaEstados(null, datosColegiales, "INSERT", request, datosColegiales.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.BAJA_EJERCICIO, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_EJERCIENTE:
									auditoriaCenHistoricoService.manageAuditoriaEstados(null, datosColegiales, "INSERT", request, datosColegiales.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.ALTA_EJERCICIO, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL:
									auditoriaCenHistoricoService.manageAuditoriaEstados(null, datosColegiales, "INSERT", request, datosColegiales.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.BAJA_COLEGIACION, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_INHABILITACION:
									auditoriaCenHistoricoService.manageAuditoriaEstados(null, datosColegiales, "INSERT", request, datosColegiales.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.INHABILITACION, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_SUSPENSION:
									auditoriaCenHistoricoService.manageAuditoriaEstados(null, datosColegiales, "INSERT", request, datosColegiales.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.SUSPENSION_EJERCICIO, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								default:
									break;
								}
								
							} else {
								response.setStatus(SigaConstants.KO);
								resultado = 0;
								org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
								GenDiccionarioExample example = new GenDiccionarioExample();
								example.createCriteria().andIdrecursoEqualTo(resultadoPlTurno[1])
										.andIdlenguajeEqualTo(usuario.getIdlenguaje());
								List<GenDiccionario> diccionario = genDiccionarioMapper.selectByExample(example);
								if (null != diccionario && diccionario.size() > 0) {
									error.setMessage(diccionario.get(0).getDescripcion());
								} else {
									error.setMessage(resultadoPlTurno[1]);
								}
								response.setError(error);
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							}
						} else {
							response.setStatus(SigaConstants.KO);
						}
					} else {
						response.setStatus(SigaConstants.KO);
					}
				} catch (Exception e) {
					LOGGER.error("Error Datos Colegiales", e);
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
	@Transactional(timeout=2400)
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
		Date colegiadoSiguienteFechaEstado = null;
		boolean updateEstado = false;
		
		// Datos auditoria
		CenDatoscolegialesestado estadoColegial = new CenDatoscolegialesestado();
		CenDatoscolegialesestado estadoColegialUltimo = null;
		ColegiadoItem colegiadoItemAuditoria = listColegiadoItem.get(0);
		boolean actualizadoUltimoEstadoModificado = false;

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
					if (listColegiadoItem != null && listColegiadoItem.size() > 1) {
						if (listColegiadoItem.get(1).getFechaEstado() != null) {
							colegiadoSiguienteFechaEstado = listColegiadoItem.get(1).getFechaEstado();
						}
					}

					int i = 0;
					for (ColegiadoItem colegiadoItem : listColegiadoItem) {
						Boolean ejecutarPL = Boolean.FALSE;
						// Hacemos esta comprobación para obviar el primer registro en el caso de que
						// sea el dummy cuando queremos hacer update+insert
						if (colegiadoItem.getFechaEstado() != null) {
							CenDatoscolegialesestado datosColegiales = new CenDatoscolegialesestado();
							List<Short> addTipoDirecciones = null;
							List<String> addTipoDireccionesPreferentes = null;
							CenDirecciones direccionCensoWeb = null;

							// Si solamente es modificar
							if (!existeDummy && colegiadoItem.getIdPersona() != null && i == 0) {
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

									if (null != tipoCensoWeb) {

										for (CenDirecciones cenDireccion : cenDireccionesList) {
											if (cenDireccion.getIddireccion().equals(tipoCensoWeb.getIddireccion())) {
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
												LOGGER.info(
														"datosColegialesUpdateEstados() / cenDireccionTipodireccionExtendsMapper.insert -> Entrada a insertar la direccion");
												cenDireccionTipodireccionExtendsMapper
														.insert(cenDireccionTipodireccion);

											}
										}

										// Si hay que añadir algun tipo de direccion lo añadimos
										if (null != addTipoDireccionesPreferentes
												&& addTipoDireccionesPreferentes.size() > 0) {
											String addPref = direccionCensoWeb.getPreferente();
											if (addPref == null) {
												addPref = "";
											}
											for (String tipoPreferente : addTipoDireccionesPreferentes) {
												addPref += tipoPreferente;
											}

											direccionCensoWeb.setPreferente(addPref);
											direccionCensoWeb.setUsumodificacion(usuario.getIdusuario());
											direccionCensoWeb.setFechamodificacion(new Date());
											LOGGER.info(
													"datosColegialesUpdateEstados() / cenDireccionTipodireccionExtendsMapper.updateByPrimaryKey -> Entrada a actualizar la direccion");
											cenDireccionesExtendsMapper.updateByPrimaryKey(direccionCensoWeb);
										}
									}
								}
								ejecutarPL = Boolean.TRUE;

							}

							datosColegiales.setFechaestado(colegiadoItem.getFechaEstado());
							datosColegiales.setIdinstitucion(idInstitucionColegial);
							datosColegiales.setIdpersona(idPersonaColegial);
							datosColegiales.setObservaciones(colegiadoItem.getObservaciones());

							CenDatoscolegialesestadoKey cenDatoscolegialesestadoKey = new CenDatoscolegialesestadoKey();
							cenDatoscolegialesestadoKey.setIdpersona(idPersonaColegial);
							cenDatoscolegialesestadoKey.setIdinstitucion(idInstitucionColegial);
							cenDatoscolegialesestadoKey.setFechaestado(colegiadoItem.getFechaEstado());
							CenDatoscolegialesestado cenDatoscolegialesestadosBBDD = cenDatoscolegialesestadoExtendsMapper
									.selectByPrimaryKey(cenDatoscolegialesestadoKey);

							boolean updateDate = false;
							if (cenDatoscolegialesestadosBBDD != null) {
								if (colegiadoItem.getObservaciones() != null && !colegiadoItem.getObservaciones()
										.equals(cenDatoscolegialesestadosBBDD.getObservaciones())
										|| colegiadoItem.getSituacionResidente() != null && !(colegiadoItem.getSituacionResidente().equalsIgnoreCase("Si") ? "1" : "0")
												.equals(cenDatoscolegialesestadosBBDD.getSituacionresidente())
												|| !colegiadoItem.getIdEstado().equals(String.valueOf(cenDatoscolegialesestadosBBDD.getIdestado()))
												|| (colegiadoItem.getFechaEstadoNueva() != null && colegiadoItem.getFechaEstadoNueva().compareTo(cenDatoscolegialesestadosBBDD.getFechaestado()) != 0)) {
									updateDate = true;
									updateEstado = true;
									
									// Preparamos los datos para la auditoria sólo si es el último estado el que se ha modificado
									if (colegiadoItem.getFechaEstado() == colegiadoItemAuditoria.getFechaEstado()) {
										actualizadoUltimoEstadoModificado = true;
										populateEstadoColegial(colegiadoItemAuditoria, estadoColegial);
										estadoColegial.setFechaestado(colegiadoItemAuditoria.getFechaEstado());
										estadoColegialUltimo = datosColegiales;
									}
									
								}
							}

//							if (colegiadoSiguienteFechaEstado != null) {
//								if (colegiadoSiguienteFechaEstado.before(colegiadoItem.getFechaEstado())
//										&& colegiadoItem.getFechaEstado().before(new Date())) {
//									updateDate = true;
//								}
//							}

							if (updateDate) {
								datosColegiales.setFechamodificacion(new Date());
							}

							datosColegiales.setUsumodificacion(usuario.getIdusuario());

							datosColegiales.setIdestado(Short.valueOf(colegiadoItem.getIdEstado()));

							fechaEstadoNueva = colegiadoItem.getFechaEstadoNueva();

							if (colegiadoItem.getSituacionResidente() != null) {
								datosColegiales.setSituacionresidente(
										colegiadoItem.getSituacionResidente().equalsIgnoreCase("si") ? "1" : "0");
							}
							if(datosColegiales.getObservaciones() == null) {
								datosColegiales.setObservaciones("");
							}
							if(updateDate) {
								LOGGER.info(
										"datosColegialesUpdateEstados() / cenDatoscolegialesestadoMapper.updateByPrimaryKeySelective() -> Entrada a cenDatoscolegialesestadoMapper para actualizar el estado colegial");
								resultado += cenDatoscolegialesestadoExtendsMapper.updateEstadoColegial(datosColegiales,
										fechaEstadoNueva);
								LOGGER.info(
										"datosColegialesUpdateEstados() / cenDatoscolegialesestadoMapper.updateByPrimaryKeySelective() -> Entrada a cenDatoscolegialesestadoMapper para para actualizar el estado colegial");
							}
							if (ejecutarPL) {
								int res = 0;
								
								if(direccionCensoWeb != null && direccionCensoWeb.getIddireccion() != null) {
									res = insertarCambioEnCola(SigaConstants.COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION,usuario.getIdinstitucion().intValue(),
											datosColegiales.getIdpersona(), direccionCensoWeb.getIddireccion(), usuario.getIdusuario());
								}else {
									res = insertarCambioEnCola(SigaConstants.COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION,usuario.getIdinstitucion().intValue(),
											datosColegiales.getIdpersona(), null, usuario.getIdusuario());
								}
								
								if(res <=0) {
									LOGGER.error("Error al insertar en la cola de actualizacion de letrados. Institucion: " +
											usuario.getIdinstitucion() + ", idpersona: " +
											listColegiadoItem.get(0).getIdPersona() + ", usumodificacion: " +
											usuario.getIdusuario());
								}else {
									LOGGER.info(
											"updateDirection() -> OK al insertar en la cola de actualizacion de letrados.");
								}
								/*
								// Llamamos al PL para mantener los colegiados
								Object[] paramMandatos = new Object[5];
								paramMandatos[0] = listColegiadoItem.get(0).getIdPersona().toString();
								paramMandatos[1] = usuario.getIdinstitucion().toString();
								paramMandatos[2] = new Long(30).toString();
								paramMandatos[3] = null;
								paramMandatos[4] = usuario.getIdusuario().toString();
								String resultadoPl[] = new String[2];
								try {
									LOGGER.info("datosColegialesUpdateEstados() / Llamada al pl de actualizar letrado");
									resultadoPl = callPLProcedure(
											"{call Pkg_Siga_Censo.Actualizardatosletrado(?,?,?,?,?,?,?)}", 2,
											paramMandatos);
									LOGGER.info("datosColegialesUpdateEstados() / salida al pl de actualizar letrado");
								} catch (IOException | NamingException | SQLException e) {
									// TODO Auto-generated catch block
									LOGGER.error("Error Datos Colegiales", e);
									e.printStackTrace();
								}*/

							}

							CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
							cenColegiadoExample.createCriteria().andIdpersonaEqualTo(idPersonaColegial);

							List<CenColegiado> cenColegiadoList = cenColegiadoExtendsMapper
									.selectByExample(cenColegiadoExample);

							if (cenColegiadoList.size() == 0 && cenColegiadoList != null) {
								CenColegiado cenColegiado = cenColegiadoList.get(0);

								cenColegiado.setNcolegiado(colegiadoItem.getNumColegiado());
								cenColegiado.setUsumodificacion(usuario.getIdusuario());
								cenColegiado.setFechamodificacion(new Date());

								cenColegiadoExtendsMapper.updateByPrimaryKey(cenColegiado);
							}

						} else {
							existeDummy = true;
						}
						i++;
					}
					if (resultado > 0) {
						
						if (listColegiadoItem.get(0).getCambioEstado() != null
								&& listColegiadoItem.get(0).getCambioEstado()) {

							// Llamamos al PL para mantener los colegiados
							Object[] paramTurno = new Object[3];
							paramTurno[0] = usuario.getIdinstitucion().toString();
							paramTurno[1] = listColegiadoItem.get(0).getIdPersona().toString();
							paramTurno[2] = usuario.getIdusuario().toString();
							String resultadoPlTurno[] = new String[2];
							try {
								LOGGER.info(
										"datosColegialesUpdateEstados() / Llamada al pl de Revision_Cambio_Estadocolegial");
								resultadoPlTurno = callPLProcedure(
										"{call Pkg_Siga_Cambio_Colegiacion.Revision_Cambio_Estadocolegial(?,?,?,?,?)}",
										2, paramTurno);

								LOGGER.info(
										"datosColegialesUpdateEstados() / Salida al pl de Revision_Cambio_Estadocolegial");
							} catch (IOException | NamingException | SQLException e) {
								// TODO Auto-generated catch block
								LOGGER.error("Error Datos Colegiales", e);
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								e.printStackTrace();
							}
							if (resultadoPlTurno[0].equals("0")) {
								response.setStatus(SigaConstants.OK);
								org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
								GenDiccionarioExample example = new GenDiccionarioExample();
								example.createCriteria().andIdrecursoEqualTo(resultadoPlTurno[1])
										.andIdlenguajeEqualTo(usuario.getIdlenguaje());
								List<GenDiccionario> diccionario = genDiccionarioMapper.selectByExample(example);
								if (null != diccionario && diccionario.size() > 0) {
									error.setMessage(diccionario.get(0).getDescripcion());
								} else {
									error.setMessage(resultadoPlTurno[1]);
								}

								response.setError(error);
								
								if (actualizadoUltimoEstadoModificado) {
									populateEstadoColegial(colegiadoItemAuditoria, estadoColegial);
									
									// AUDITORIA si se actualizó un estado correctamente
									if (estadoColegial.getIdestado() != null) {
										switch ((int) estadoColegial.getIdestado()) {
										case SigaConstants.ESTADO_COLEGIAL_SINEJERCER:
											auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
													SigaConstants.CEN_TIPOCAMBIO.BAJA_EJERCICIO, idPersonaColegial);
											break;
										case SigaConstants.ESTADO_COLEGIAL_EJERCIENTE:
											auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
													SigaConstants.CEN_TIPOCAMBIO.ALTA_EJERCICIO, idPersonaColegial);
											break;
										case SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL:
											auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
													SigaConstants.CEN_TIPOCAMBIO.BAJA_COLEGIACION, idPersonaColegial);
											break;
										case SigaConstants.ESTADO_COLEGIAL_INHABILITACION:
											auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
													SigaConstants.CEN_TIPOCAMBIO.INHABILITACION, idPersonaColegial);
											break;
										case SigaConstants.ESTADO_COLEGIAL_SUSPENSION:
											auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
													SigaConstants.CEN_TIPOCAMBIO.SUSPENSION_EJERCICIO, idPersonaColegial);
											break;
										default:
											break;
										}
									}
								}
							} else {
								response.setStatus(SigaConstants.KO);
								resultado = 0;
								org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
								GenDiccionarioExample example = new GenDiccionarioExample();
								example.createCriteria().andIdrecursoEqualTo(resultadoPlTurno[1])
										.andIdlenguajeEqualTo(usuario.getIdlenguaje());
								List<GenDiccionario> diccionario = genDiccionarioMapper.selectByExample(example);
								if (null != diccionario && diccionario.size() > 0) {
									error.setMessage(diccionario.get(0).getDescripcion());
								} else {
									error.setMessage(resultadoPlTurno[1]);
								}

								response.setError(error);
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							}
						} else {
							response.setStatus(SigaConstants.OK);
							
							if (actualizadoUltimoEstadoModificado) {
								populateEstadoColegial(colegiadoItemAuditoria, estadoColegial);
								
								// AUDITORIA si se actualizó un estado correctamente
								if (estadoColegial.getIdestado() != null) {
									switch ((int) estadoColegial.getIdestado()) {
									case SigaConstants.ESTADO_COLEGIAL_SINEJERCER:
										auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
												SigaConstants.CEN_TIPOCAMBIO.BAJA_EJERCICIO, idPersonaColegial);
										break;
									case SigaConstants.ESTADO_COLEGIAL_EJERCIENTE:
										auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
												SigaConstants.CEN_TIPOCAMBIO.ALTA_EJERCICIO, idPersonaColegial);
										break;
									case SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL:
										auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
												SigaConstants.CEN_TIPOCAMBIO.BAJA_COLEGIACION, idPersonaColegial);
										break;
									case SigaConstants.ESTADO_COLEGIAL_INHABILITACION:
										auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
												SigaConstants.CEN_TIPOCAMBIO.INHABILITACION, idPersonaColegial);
										break;
									case SigaConstants.ESTADO_COLEGIAL_SUSPENSION:
										auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
												SigaConstants.CEN_TIPOCAMBIO.SUSPENSION_EJERCICIO, idPersonaColegial);
										break;
									default:
										break;
									}
								}
							}
						}
						// Se comprueba si se deben revisar las cuentas y se ejecutan los scripts que se
						// encargan de ello

						// Lanzamos el proceso de revision de suscripciones del letrado
						String resultadoPl[] = ejecutarPL_RevisionSuscripcionesLetrado("" + idInstitucion.toString(),
								"" + listColegiadoItem.get(0).getIdPersona(), "", "" + usuario.getIdusuario().toString());
						if ((resultadoPl == null) || (!resultadoPl[0].equals("0"))) {
							Error error = new Error();
							response.setStatus(SigaConstants.KO);
							error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO"
									+ resultadoPl[1]);
							response.setError(error);
							return response;
						}

					} else {
						if(updateEstado){
						response.setStatus(SigaConstants.KO);
						}else{
							response.setStatus(SigaConstants.OK);
						}
					}
				} catch (Exception e) {
					LOGGER.error("Error Datos Colegiales", e);
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
					CenDirecciones direccionCensoWeb = null;

					// Comprobamos que sea cambio de ejerciente a no ejerciente
					CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
					cenDatoscolegialesestadoExample.createCriteria()
							.andIdinstitucionEqualTo(Short.valueOf(colegiadoItem.getIdInstitucion()))
							.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));
					cenDatoscolegialesestadoExample.setOrderByClause("fechaestado desc");

					List<CenDatoscolegialesestado> cenDatoscolegialesestadosList = cenDatoscolegialesestadoExtendsMapper
							.selectByExample(cenDatoscolegialesestadoExample);
					
					CenDatoscolegialesestado estadoColegialUltimo = null;

					// ACTUALIZAMOS CEN_COLEGIADO PARA ELIMINAR
					CenColegiadoKey colegiadoKey = new CenColegiadoKey();
					colegiadoKey.setIdinstitucion(idInstitucion);
					colegiadoKey.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
					CenColegiado colegiado = new CenColegiado();
					colegiado.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
					colegiado.setIdinstitucion(idInstitucion);
					colegiado.setUsumodificacion(usuario.getIdusuario());
					if (colegiadoItem.getNumColegiado() != null) {
						colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
					}
					if (colegiadoItem.getIdTiposSeguro() != null && colegiadoItem.getIdTiposSeguro() != "") {
						colegiado.setIdtiposseguro(Short.parseShort(colegiadoItem.getIdTiposSeguro()));
					} else {
						colegiado.setIdtiposseguro(null);
					}
					if (colegiadoItem.getSituacionResidente() != null) {
						colegiado.setSituacionresidente(
								colegiadoItem.getSituacionResidente().equalsIgnoreCase("si") ? "1" : "0");
					} else {
						colegiado.setSituacionresidente("0");
					}

					if (colegiadoItem.getComunitario() != null) {
						colegiado.setComunitario(colegiadoItem.getComunitario());
					}
					if (colegiadoItem.getnMutualista() != null && colegiadoItem.getnMutualista() != "") {
						colegiado.setNmutualista(colegiadoItem.getnMutualista());
					} else {
						colegiado.setNmutualista(null);
					}

					// colegiado.setFechaincorporacion(colegiadoItem.getIncorporacionDate());
					colegiado.setFechatitulacion(colegiadoItem.getFechaTitulacionDate());
					// colegiado.setFechapresentacion(colegiadoItem.getFechapresentacionDate());
					colegiado.setFechajura(colegiadoItem.getFechaJuraDate());

					colegiado.setFechamodificacion(new Date());

					cenColegiadoExtendsMapper.updateColegiado(colegiado);

					if (null != cenDatoscolegialesestadosList && cenDatoscolegialesestadosList.size() > 0) {
						CenDatoscolegialesestado cenDatoscolegialesestadoBBDD = null;

						// Obtenemos el estado colegial que tiene el colegiado guardado en bbdd
						if (cenDatoscolegialesestadosList.size() > 1) {
							cenDatoscolegialesestadoBBDD = cenDatoscolegialesestadosList.get(1);
							
							// Asignamos el estado colegial anterior al eliminado
							estadoColegialUltimo = cenDatoscolegialesestadoBBDD;
							
						// Si no existe estado anterior, este será el que se está eliminando
						} else if (cenDatoscolegialesestadosList.size() == 1) {
							estadoColegialUltimo = cenDatoscolegialesestadosList.get(0);
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
						if (null != tipoCensoWeb) {
						
							for (CenDirecciones cenDireccion : cenDireccionesList) {
								if (cenDireccion.getIddireccion().equals(tipoCensoWeb.getIddireccion())) {
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
					}

					estadoColegial.setFechaestado(colegiadoItem.getFechaEstado());
					estadoColegial.setIdinstitucion(Short.parseShort(colegiadoItem.getIdInstitucion()));
					estadoColegial.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));

					LOGGER.info(
							"datosColegialesDeleteEstado() / cenDatoscolegialesestadoMapper.deleteByPrimaryKey() -> Entrada a cenDatoscolegialesestadoMapper para eliminar el estado colegial");
					resultado = cenDatoscolegialesestadoExtendsMapper.deleteEstadoColegial(estadoColegial);
					LOGGER.info(
							"datosColegialesDeleteEstado() / cenDatoscolegialesestadoMapper.deleteByPrimaryKey() -> Entrada a cenDatoscolegialesestadoMapper para eliminar el estado colegial");
					// Llamamos al PL para mantener los colegiados
					int res = 0;
					
					if(direccionCensoWeb != null && direccionCensoWeb.getIddireccion() != null) {
						res = insertarCambioEnCola(SigaConstants.COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION,usuario.getIdinstitucion().intValue(),
								estadoColegial.getIdpersona(), direccionCensoWeb.getIddireccion(), usuario.getIdusuario());
					}else {
						res = insertarCambioEnCola(SigaConstants.COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION,usuario.getIdinstitucion().intValue(),
								estadoColegial.getIdpersona(), null, usuario.getIdusuario());
					}
					
					if(res <=0) {
						LOGGER.error("Error al insertar en la cola de actualizacion de letrados. Institucion: " +
								usuario.getIdinstitucion() + ", idpersona: " +
								estadoColegial.getIdpersona() + ", usumodificacion: " +
								usuario.getIdusuario());
					}else {
						LOGGER.info(
								"updateDirection() -> OK al insertar en la cola de actualizacion de letrados.");
					}
					/*
					Object[] paramMandatos = new Object[5];
					paramMandatos[0] = estadoColegial.getIdpersona().toString();
					paramMandatos[1] = usuario.getIdinstitucion().toString();
					paramMandatos[2] = new Long(30).toString();
					paramMandatos[3] = null;
					paramMandatos[4] = usuario.getIdusuario().toString();
					String resultadoPl[] = new String[2];
					try {
						LOGGER.info("datosColegialesInsertEstado() / llamada PL actualizar datos letrado");
						resultadoPl = callPLProcedure("{call Pkg_Siga_Censo.Actualizardatosletrado(?,?,?,?,?,?,?)}", 2,
								paramMandatos);
						LOGGER.info("datosColegialesInsertEstado() / salida PL actualizar datos letrado");
					} catch (IOException | NamingException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/

					if (resultado == 1) {
						response.setStatus(SigaConstants.OK);

						/// Llamamos al Pl
						Object[] paramTurno = new Object[3];
						paramTurno[0] = usuario.getIdinstitucion().toString();
						paramTurno[1] = estadoColegial.getIdpersona().toString();
						paramTurno[2] = usuario.getIdusuario().toString();
						String resultadoPlTurno[] = new String[2];
						try {
							LOGGER.info("datosColegialesInsertEstado() / llamada PL Revision_Cambio_Estadocolegial");
							resultadoPlTurno = callPLProcedure(
									"{call Pkg_Siga_Cambio_Colegiacion.Revision_Cambio_Estadocolegial(?,?,?,?,?)}", 2,
									paramTurno);
							LOGGER.info("datosColegialesInsertEstado() / salida PL Revision_Cambio_Estadocolegial");
						} catch (IOException | NamingException | SQLException e) {
							// TODO Auto-generated catch block
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							e.printStackTrace();
						}
						if (resultadoPlTurno[0].equals("0")) {
							response.setStatus(SigaConstants.OK);
							org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();

							GenDiccionarioExample example = new GenDiccionarioExample();
							example.createCriteria().andIdrecursoEqualTo(resultadoPlTurno[1])
									.andIdlenguajeEqualTo(usuario.getIdlenguaje());
							List<GenDiccionario> diccionario = genDiccionarioMapper.selectByExample(example);
							if (null != diccionario && diccionario.size() > 0) {
								error.setMessage(diccionario.get(0).getDescripcion());
							} else {
								error.setMessage(resultadoPlTurno[1]);
							}
							response.setError(error);
							
							// AUDITORIA si se insertó un estado correctamente
							
							populateEstadoColegial(colegiadoItem, estadoColegial);
							
							if (estadoColegial.getIdestado() != null) {
								switch ((int) estadoColegial.getIdestado()) {
								case SigaConstants.ESTADO_COLEGIAL_SINEJERCER:
									auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.BAJA_EJERCICIO, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_EJERCIENTE:
									auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.ALTA_EJERCICIO, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL:
									auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.BAJA_COLEGIACION, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_INHABILITACION:
									auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.INHABILITACION, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								case SigaConstants.ESTADO_COLEGIAL_SUSPENSION:
									auditoriaCenHistoricoService.manageAuditoriaEstados(estadoColegial, estadoColegialUltimo, "UPDATE", request, estadoColegial.getObservaciones(),
											SigaConstants.CEN_TIPOCAMBIO.SUSPENSION_EJERCICIO, Long.parseLong(colegiadoItem.getIdPersona()));
									break;
								default:
									break;
								}
							}
							
						} else {
							response.setStatus(SigaConstants.KO);
							resultado = 0;
							org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
							GenDiccionarioExample example = new GenDiccionarioExample();
							example.createCriteria().andIdrecursoEqualTo(resultadoPlTurno[1])
									.andIdlenguajeEqualTo(usuario.getIdlenguaje());
							List<GenDiccionario> diccionario = genDiccionarioMapper.selectByExample(example);
							if (null != diccionario && diccionario.size() > 0) {
								error.setMessage(diccionario.get(0).getDescripcion());
							} else {
								error.setMessage(resultadoPlTurno[1]);
							}
							response.setError(error);
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						}
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

	/**
	 * Añade datos a un objeto CenDatoscolegialesestado
	 * @param colegiadoItem Objeto de donde se obtienen los datos
	 * @param estadoColegial Objeto a rellenar de datos
	 */
	private void populateEstadoColegial(ColegiadoItem colegiadoItem, CenDatoscolegialesestado estadoColegial) {
		
		// Asignamos el id
		if ("No Ejerciente".equals(colegiadoItem.getEstadoColegial())) {
			estadoColegial.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_SINEJERCER);
			
		} else if ("Ejerciente".equals(colegiadoItem.getEstadoColegial())) {
			estadoColegial.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_EJERCIENTE);
			
		} else if ("Baja Colegial".equals(colegiadoItem.getEstadoColegial())) {
			estadoColegial.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL);
			
		} else if (SigaConstants.CEN_TIPOCAMBIO.INHABILITACION.getDescripcionTipoCambio().equals(colegiadoItem.getEstadoColegial())) {
			estadoColegial.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_INHABILITACION);
			
		} else if (SigaConstants.CEN_TIPOCAMBIO.SUSPENSION_EJERCICIO.getDescripcionTipoCambio().equals(colegiadoItem.getEstadoColegial())) {
			estadoColegial.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_SUSPENSION);
		}
		
		// Asignamos las observaciones
		estadoColegial.setObservaciones(colegiadoItem.getObservaciones());	
	}

	@Override
	public StringDTO getNumColegiado(HttpServletRequest request) {

		LOGGER.info("getNumColegiado() -> Entrada al servicio para obtener los tipos de tratamiento disponibles");
		StringDTO nColegiado = new StringDTO();

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
				nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNColegiado(String.valueOf(idInstitucion));

			}

		}
		LOGGER.info("getNumColegiado() -> Salida del servicio para obtener los tipos de tratamiento disponibles");

		return nColegiado;
	}

	/**
	 * Calls a PL Funtion
	 * 
	 * @author CSD
	 * @param functionDefinition
	 *            string that defines the function
	 * @param inParameters
	 *            input parameters
	 * @param outParameters
	 *            number of output parameters
	 * @return error code, '0' if ok
	 * @throws NamingException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClsExceptions
	 *             type Exception
	 */
	private String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
			throws IOException, NamingException, SQLException {
		String result[] = null;

		if (outParameters > 0)
			result = new String[outParameters];
		DataSource ds = getOracleDataSource();
		Connection con = ds.getConnection();
		try {
			CallableStatement cs = con.prepareCall(functionDefinition);
			int size = inParameters.length;

			// input Parameters
			for (int i = 0; i < size; i++) {
				cs.setString(i + 1, (String) inParameters[i]);

			}
			// output Parameters
			for (int i = 0; i < outParameters; i++) {
				cs.registerOutParameter(i + size + 1, Types.VARCHAR);
			}

			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					break;

				} catch (SQLTimeoutException tex) {
					throw tex;

				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
																		// segunda vez deberia funcionar)
						throw ex;
					}
				}

			}

			for (int i = 0; i < outParameters; i++) {
				result[i] = cs.getString(i + size + 1);
			}
			cs.close();
			return result;

		} catch (SQLTimeoutException ex) {
			return null;
		} catch (SQLException ex) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			con.close();
			con = null;
		}
	}

	/**
	 * Recupera el datasource con los datos de conexión sacados del fichero de
	 * configuracion
	 * 
	 * @return
	 * @throws IOException
	 * @throws NamingException
	 */
	private DataSource getOracleDataSource() throws IOException, NamingException {
		try {

			LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");

			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
			List<AdmConfig> config = admConfigMapper.selectByExample(example);
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(config.get(0).getValor());
		} catch (NamingException e) {
			throw e;
		}
	}

	@Override
	public ColegiadoDTO datosColegialesSearchActual(int numPagina, ColegiadoItem colegiadoItem,
			HttpServletRequest request) {

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
				colegiadoListItem = cenColegiadoExtendsMapper.selectColegiacionActual(idInstitucion,
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
	/**
	 * PL que realiza una revision de letrado
	 * 
	 * @param idInstitucion
	 * @param idPersona
	 * @param usuario
	 * @return
	 * @throws ClsExceptions
	 */
	private String[] ejecutarPL_RevisionSuscripcionesLetrado(String idInstitucion, String idPersona, String fecha,
			String usuario) throws Exception {
		LOGGER.info("Entrada Ejecución PL Revision SuscripcionesLetrado");
		Object[] paramIn = new Object[4]; // Parametros de entrada del PL
		String resultado[] = new String[2]; // Parametros de salida del PL

		try {
			// Parametros de entrada del PL
			paramIn[0] = idInstitucion;
			paramIn[1] = idPersona;
			paramIn[2] = fecha;
			paramIn[3] = usuario;

			// Ejecucion del PL
			resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO (?,?,?,?,?,?)}", 2,
					paramIn);

		} catch (Exception e) {
			LOGGER.info("Error Ejecución PL Revision SuscripcionesLetrado: " + e.getMessage());
			resultado[0] = "1"; // P_NUMREGISTRO
			resultado[1] = "ERROR"; // ERROR P_DATOSERROR
		}
		LOGGER.info("Salida Ejecución PL Revision SuscripcionesLetrado" );
		return resultado;
	}
	
	public int insertarCambioEnCola (int idTipoCambio,
			 Integer idInstitucion,
			 Long idPersona,
			 Long idDireccion,
			 Integer usumodificacion) 
	{
		try
		{
			CenColacambioletrado bean = new CenColacambioletrado ();
			bean.setFechacambio(new Date());
			bean.setIdcambio(getNuevoId (idInstitucion, idPersona));
			if (idDireccion != null) 
				bean.setIddireccion (idDireccion);
			bean.setIdinstitucion (idInstitucion.shortValue());
			bean.setIdpersona (idPersona);
			bean.setIdtipocambio ((new Integer (idTipoCambio).shortValue()));
			bean.setUsumodificacion(usumodificacion);
			bean.setFechamodificacion(new Date());
			return cenColacambioletradoMapper.insert(bean);
		}
		catch (Exception e) {
			LOGGER.info("Error Ejecución PL Revision SuscripcionesLetrado: " + e.getMessage());
		return 0;
		}
	} //insertarCambioEnCola()

	private Long getNuevoId(Integer idInstitucion, Long idPersona) {
		
		MaxIdDto id = cenColacambioletradoMapper.selectNuevoId(idInstitucion, idPersona);
		return id.idMax;
	}

	@Override
	public UpdateResponseDTO datosColegialesUpdateMasivo(ColegiadoItemDTO listColegiadoItem,
			HttpServletRequest request) {

		LOGGER.info("datosColegialesUpdateMasivo() -> Entrada al servicio para la actualizacion de estados colegiales");
		ColegiadoItem colegiadoItem = listColegiadoItem.getColegiadoItem();
		InsertResponseDTO responseinsert = new InsertResponseDTO();
		UpdateResponseDTO response = new UpdateResponseDTO();
		response.setStatus(SigaConstants.OK);
		responseinsert.setStatus(SigaConstants.OK);
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosColegialesUpdateMasivo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosColegialesUpdateMasivo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				// if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosColegialesUpdateMasivo() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");
				//1. Actualizamos el colegiado
				CenColegiadoKey colegiadoKey = new CenColegiadoKey();
				colegiadoKey.setIdinstitucion(idInstitucion);
				colegiadoKey.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				CenColegiado cenColegiadoAnterior = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadoKey);
				CenColegiado colegiado = new CenColegiado();
				colegiado.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				colegiado.setIdinstitucion(idInstitucion);
				colegiado.setUsumodificacion(usuario.getIdusuario());
				/*if (colegiadoItem.getNumColegiado() != null) {
					colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
				}*/
				if (colegiadoItem.getIdTiposSeguro() != null && colegiadoItem.getIdTiposSeguro() != "") {
					colegiado.setIdtiposseguro(Short.parseShort(colegiadoItem.getIdTiposSeguro()));
				} else {
					colegiado.setIdtiposseguro(null);
				}
				if (colegiadoItem.getSituacionResidente() != null) {
					colegiado.setSituacionresidente(
							colegiadoItem.getSituacionResidente().equalsIgnoreCase("si") ? "1" : "0");
				} else {
					colegiado.setSituacionresidente("0");
				}

				if (colegiadoItem.getComunitario() != null) {
					colegiado.setComunitario(colegiadoItem.getComunitario());
					if (colegiadoItem.getNumColegiado() != null) {
						if (colegiadoItem.getComunitario().equals("1")) {
							colegiado.setNcomunitario(colegiadoItem.getNumColegiado());
						}else{
							
							colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
						}
					}
				}else{
					if (colegiadoItem.getNumColegiado() != null) {
						colegiado.setNcolegiado(colegiadoItem.getNumColegiado());
					}
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

				int responseUpdateColegiado = cenColegiadoExtendsMapper.updateColegiado(colegiado);
				LOGGER.info(
						"datosColegialesUpdateMasivo() / CenColegiadoExtendsMapper.selectDirecciones() -> Salida de CenColegiadoExtendsMapper para actualización de Colegiados");

				LOGGER.info(
						"datosColegialesUpdateMasivo() / Entrada Actualizamos los estados de la tabla");

				//2 actualizamos los estados
				response = this.datosColegialesUpdateEstados(listColegiadoItem.getColegiadoItemEstados(), request);
				

				LOGGER.info(
						"datosColegialesUpdateMasivo() / Salida Actualizamos los estados de la tabla");
				if (response.getStatus().equals(SigaConstants.OK)) {
					
				
					//3 Insertamos el estado si así precisa
					if (null != listColegiadoItem.getNuevocolegiadoItem() && null != listColegiadoItem.getNuevocolegiadoItem().getIdPersona()) {

						LOGGER.info(
								"datosColegialesUpdateMasivo() / Entrada a insertar primer estado de la tabla");
						responseinsert = this.datosColegialesInsertEstado(listColegiadoItem.getNuevocolegiadoItem(), request);

						LOGGER.info(
								"datosColegialesUpdateMasivo() / Salida a insertar primer estado de la tabla");
					}
					
				}
				
				
				
				
				
				//4 Añadimos auditoria
				if (responseUpdateColegiado >= 1 && response.getStatus().equals(SigaConstants.OK) && responseinsert.getStatus().equals(SigaConstants.OK)) {

					// Añadimos auditoria
					if (!UtilidadesString.esCadenaVacia(colegiadoItem.getMotivo())) {

						LOGGER.info(
								"datosColegialesUpdateMasivo() / Entrada a insertar auditoria");
						colegiadoKey.setIdinstitucion(idInstitucion);
						colegiadoKey.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
						CenColegiado cenColegiadoPosterior = cenColegiadoExtendsMapper.selectByPrimaryKey(colegiadoKey);
						// AUDITORIA => actualizamos cen_historico si todo es correcto
						auditoriaCenHistoricoService.manageAuditoriaDatosColegiales(cenColegiadoAnterior,
								cenColegiadoPosterior, "UPDATE", request, colegiadoItem.getMotivo());
						LOGGER.info(
								"datosColegialesUpdateMasivo() / Salida a insertar auditoria");
					}

					response.setStatus(SigaConstants.OK);

				} else {
					if (null != responseinsert.getStatus()) {
						if (responseinsert.getStatus().equals(SigaConstants.KO)) {
							if (null != responseinsert.getError()) {
								response.setError(responseinsert.getError());
							}
						}
						
					}
					response.setStatus(SigaConstants.KO);
				}

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
    public StringDTO getTurnosGuardias(ColegiadoItem colegiadoItem, HttpServletRequest request) {

        LOGGER.info("getTurnosGuardias() -> Entrada al servicio para obtener los turnos o guardias asociados al colegiado");
        StringDTO resultado = new StringDTO();

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
                resultado = _scsGuardiascolegiadoMapper.getTurnosGuardias(colegiadoItem.getIdPersona(), idInstitucion);
                if(resultado == null)
                	resultado = new StringDTO().valor("0");
            }

        }
        LOGGER.info("getTurnosGuardias() -> Salida del servicio para obtener los turnos o guardias asociados al colegiado");

        return resultado;
    }


}
