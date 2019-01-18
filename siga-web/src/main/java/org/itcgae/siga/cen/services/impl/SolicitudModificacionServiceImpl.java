package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ISolicitudModificacionService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitudesmodificacion;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadoSolicitudModifExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudesmodificacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposModificacionesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudModificacionServiceImpl implements ISolicitudModificacionService {

	private Logger LOGGER = Logger.getLogger(SolicitudModificacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper _admUsuariosExtendsMapper;

	@Autowired
	private CenTiposModificacionesExtendsMapper cenTiposModificacionesExtendsMapper;

	@Autowired
	private CenEstadoSolicitudModifExtendsMapper cenEstadoSolicitudModifExtendsMapper;

	@Autowired
	private CenSolicitudesmodificacionExtendsMapper cenSolicitudesModificacionExtendsMapper;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Override
	public ComboDTO getComboTipoModificacion(HttpServletRequest request) {
		LOGGER.info("getComboTipoModificacion() -> Entrada al servicio para cargar el tipo de modificación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getComboTipoModificacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getComboTipoModificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getComboTipoModificacion() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de modificación");

				List<ComboItem> comboItems = cenTiposModificacionesExtendsMapper
						.getTipoModificacion(usuario.getIdlenguaje());

				if (comboItems != null && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("getComboTipoModificacion() -> Salida del servicio para obtener los tipos de modificación");
		return combo;
	}

	@Override
	public ComboDTO getComboEstado(HttpServletRequest request) {
		LOGGER.info("getComboEstado() -> Entrada al servicio para cargar los estados de solicitud");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getComboEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getComboEstado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getComboEstado() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los estados de solicitud");

				List<ComboItem> comboItems = cenEstadoSolicitudModifExtendsMapper.getEstado(usuario.getIdlenguaje());

				if (comboItems != null && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("getComboEstado() -> Salida del servicio para obtener los estados de solicitud");
		return combo;
	}

	@Override
	public SolModificacionDTO searchModificationRequest(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchModificationRequest() -> Entrada al servicio para recuperar las solicitudes de modificación");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchModificationRequest() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchModificationRequest() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosSolicitudSearch() / cenEstadoSolicitudExtendsMapper.selectTipoSolicitud() -> Entrada a cenEstadoSolicitudExtendsMapper para obtener los estados de solicitud");
				List<SolModificacionItem> solModificacionItems = cenTiposModificacionesExtendsMapper
						.searchModificationRequest(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
								String.valueOf(idInstitucion));
				solModificacionDTO.setSolModificacionItems(solModificacionItems);

			}
		}

		LOGGER.info(
				"searchModificationRequest() -> Salida del servicio para recuperar las solicitudes de modificación");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processGeneralModificationRequest(ArrayList<SolModificacionItem> solModificacionDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"processGeneralModificationRequest() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int i = 0;
		while (i < solModificacionDTO.size()) {
			SolModificacionItem solModificacionItem = solModificacionDTO.get(i);

			CenSolicitudesmodificacion record = new CenSolicitudesmodificacion();
			record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
			record.setIdestadosolic((short) 20);
			int response = cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(record);

			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"processGeneralModificationRequest() / cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective() -> "
								+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");
			} else {
				updateResponseDTO.setStatus(SigaConstants.OK);
			}

			LOGGER.info(
					"processGeneralModificationRequest() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
			i++;
		}
		// for(int i = 0 ; solModificacionDTO.size() < i; i++ ) {

		// }

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO denyGeneralModificationRequest(ArrayList<SolModificacionItem> solModificacionDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"denyGeneralModificationRequest() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int i = 0;
		while (i < solModificacionDTO.size()) {
			SolModificacionItem solModificacionItem = solModificacionDTO.get(i);

			CenSolicitudesmodificacion record = new CenSolicitudesmodificacion();
			record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
			record.setIdestadosolic((short) 30);
			int response = cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(record);

			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"denyGeneralModificationRequest() / cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelective() -> "
								+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");
			} else {
				updateResponseDTO.setStatus(SigaConstants.OK);
			}

			LOGGER.info(
					"denyGeneralModificationRequest() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
			i++;
		}
		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertGeneralModificationRequest(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info("insertGeneralModificationRequest() -> Entrada al servicio para insertar una nueva solicitud");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"insertGeneralModificationRequest() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"insertGeneralModificationRequest() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				CenSolicitudesmodificacion record = new CenSolicitudesmodificacion();
				record.setDescripcion(solModificacionItem.getMotivo());
				record.setFechaalta(new Date());
				record.setFechamodificacion(new Date());
				record.setIdestadosolic((short) 10);
				record.setIdinstitucion(idInstitucion);

				CenPersonaExample cenPersonaExample = new CenPersonaExample();
				cenPersonaExample.createCriteria().andNifcifEqualTo(dni);
				List<CenPersona> cenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);

				record.setIdpersona(Long.valueOf(cenPersona.get(0).getIdpersona()));

				NewIdDTO idSolicitud = cenSolicitudesModificacionExtendsMapper.getMaxIdSolicitud(
						String.valueOf(idInstitucion), String.valueOf(cenPersona.get(0).getIdpersona()));

				if (idSolicitud == null) {
					record.setIdsolicitud(Long.parseLong("1"));
				} else {
					int idSol = Integer.parseInt(idSolicitud.getNewId()) + 1;
					record.setIdsolicitud((long) idSol);
				}

				record.setIdtipomodificacion(Short.valueOf(solModificacionItem.getIdTipoModificacion()));
				record.setUsumodificacion(usuario.getIdusuario());

				// Antes de insertar comprobamos si el usuario existe en cen_cliente
				CenClienteKey cenClienteKey = new CenClienteKey();
				cenClienteKey.setIdinstitucion(idInstitucion);
				cenClienteKey.setIdpersona(Long.valueOf(cenPersona.get(0).getIdpersona()));
				CenCliente cenCliente = cenClienteExtendsMapper.selectByPrimaryKey(cenClienteKey);

				if (cenCliente == null) {
					CenCliente cenClienteToInsert = new CenCliente();

					cenClienteToInsert.setCaracter("P");
					cenClienteToInsert.setComisiones(SigaConstants.DB_FALSE);
					cenClienteToInsert.setExportarfoto(SigaConstants.DB_FALSE);
					cenClienteToInsert.setFechaalta(new Date());
					cenClienteToInsert.setFechamodificacion(new Date());
					cenClienteToInsert.setGuiajudicial(SigaConstants.DB_FALSE);
					cenClienteToInsert.setIdinstitucion(idInstitucion);
					cenClienteToInsert.setIdlenguaje(usuario.getIdlenguaje());
					cenClienteToInsert.setIdpersona(cenPersona.get(0).getIdpersona());
					cenClienteToInsert.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE));
					cenClienteToInsert.setLetrado("1");
					cenClienteToInsert.setPublicidad(SigaConstants.DB_FALSE);
					cenClienteToInsert.setUsumodificacion(usuario.getIdusuario());

					int clientInsert = cenClienteExtendsMapper.insertSelective(cenClienteToInsert);

					if (clientInsert == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn("insertGeneralModificationRequest() / cenClienteExtendsMapper.insertSelective() -> "
								+ insertResponseDTO.getStatus() + " .no se pudo crear el registro en CEN_CLIENTE");
					} else {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
				}

				int response = cenSolicitudesModificacionExtendsMapper.insertSelective(record);

				if (response == 0) {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"insertGeneralModificationRequest() / cenSolicitudesModificacionExtendsMapper.insertSelective() -> "
									+ insertResponseDTO.getStatus() + " .no se pudo generar la solicitud");

				} else {
					insertResponseDTO.setStatus(SigaConstants.OK);
				}

			}
		}

		LOGGER.info("insertGeneralModificationRequest() -> Salida al servicio para insertar una nueva solicitud");

		return insertResponseDTO;
	}

	@Override
	public StringDTO verifyPerson(StringDTO nifCif, HttpServletRequest request) {
		LOGGER.info(
				"verifyPerson() -> Entrada al servicio para verificar si la persona logueada está en la tabla cen_colegiado");

		StringDTO stringDTO = new StringDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		
		String dni = "";
		
		if(nifCif.getValor() == "") {
			dni = UserTokenUtils.getDniFromJWTToken(token);
		}else {
			dni = nifCif.getValor();
		}
		
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andNifcifEqualTo(dni);
			List<CenPersona> cenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);

			if (!cenPersona.isEmpty()) {
				CenColegiadoKey cenColegiadoKey = new CenColegiadoKey();
				cenColegiadoKey.setIdinstitucion(idInstitucion);
				cenColegiadoKey.setIdpersona(cenPersona.get(0).getIdpersona());

				CenColegiado cenColegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(cenColegiadoKey);

				if (cenColegiado != null) {
					stringDTO.setValor("existe");
				} else {
					stringDTO.setValor("noExiste");
				}
			}
		}

		LOGGER.info(
				"verifyPerson() -> Salida al servicio para verificar si la persona logueada está en la tabla cen_colegiado");
		return stringDTO;
	}

}
