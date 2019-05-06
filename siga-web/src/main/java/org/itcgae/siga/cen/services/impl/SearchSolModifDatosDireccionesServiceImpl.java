package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.CenSolimodidireccionesExample;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolimodidireccionesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosDireccionesServiceImpl implements ISearchSolModifDatosDireccionesService {

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosDireccionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper _admUsuariosExtendsMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;

	@Autowired
	private CenSolimodidireccionesExtendsMapper cenSoliModiDireccionesExtendsMapper;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Override
	public SolModificacionDTO searchSolModifDatosDirecciones(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {

		LOGGER.info(
				"searchSolModifDatosDirecciones() -> Entrada al servicio para recuperar los datos de la búsqueda específica de direcciones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosDirecciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosDirecciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				if (letrado.equalsIgnoreCase("S")) {
					CenPersonaExample example = new CenPersonaExample();
					example.createCriteria().andNifcifEqualTo(usuario.getNif());
					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(example);
					if (null != cenPersona && cenPersona.size() > 0) {

						LOGGER.info(
								"searchSolModifDatosDirecciones() / cenSoliModiDireccionesExtendsMapper.searchSolModifDatosDirecciones() -> Entrada a cenSoliModiDireccionesExtendsMapper para obtener los resultados de la búsqueda");
						List<SolModificacionItem> solModificacionItems = cenSoliModiDireccionesExtendsMapper
								.searchSolModifDatosDirecciones(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
										String.valueOf(idInstitucion), cenPersona.get(0).getIdpersona());
						solModificacionDTO.setSolModificacionItems(solModificacionItems);
					}

				} else {

					LOGGER.info(
							"searchSolModifDatosDirecciones() / cenSoliModiDireccionesExtendsMapper.searchSolModifDatosDirecciones() -> Entrada a cenSoliModiDireccionesExtendsMapper para obtener los resultados de la búsqueda");
					List<SolModificacionItem> solModificacionItems = cenSoliModiDireccionesExtendsMapper
							.searchSolModifDatosDirecciones(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
									String.valueOf(idInstitucion), null);
					solModificacionDTO.setSolModificacionItems(solModificacionItems);
				}

			}
		}

		LOGGER.info(
				"searchSolModifDatosDirecciones() -> Salida del servicio para recuperar los datos de la búsqueda específica de direcciones");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosDirecciones(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosDirecciones() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

		LOGGER.info(
				"processSolModifDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);

			CenSolimodidirecciones solicitud = new CenSolimodidirecciones();
			CenSolimodidireccionesExample example = new CenSolimodidireccionesExample();

			example.createCriteria().andIdpersonaEqualTo(Long.parseLong(solModificacionItem.getIdPersona()))
					.andIdinstitucionEqualTo(idInstitucion)
					.andIdsolicitudEqualTo(Long.valueOf(solModificacionItem.getIdSolicitud()));

			List<CenSolimodidirecciones> lista = cenSoliModiDireccionesExtendsMapper.selectByExample(example);

			solicitud = lista.get(0);

			CenDirecciones modificacion = new CenDirecciones();
			modificacion.setIdinstitucion(idInstitucion);
			modificacion.setIdpersona(solicitud.getIdpersona());
			modificacion.setFechamodificacion(new Date());
			modificacion.setUsumodificacion(usuario.getIdusuario());
			modificacion.setIddireccion(solicitud.getIddireccion());
			// modificacion.setPreferente(solicitud.getPreferente());
			modificacion.setCodigopostal(solicitud.getCodigopostal());
			modificacion.setTelefono1(solicitud.getTelefono1());
			modificacion.setDomicilio(solicitud.getDomicilio());
			modificacion.setMovil(solicitud.getMovil());
			modificacion.setFax1(solicitud.getFax1());
			modificacion.setCorreoelectronico(solicitud.getCorreoelectronico());
			modificacion.setPaginaweb(solicitud.getPaginaweb());
			modificacion.setIdpais(solicitud.getIdpais());
			modificacion.setIdprovincia(solicitud.getIdprovincia());
			modificacion.setIdpoblacion(solicitud.getIdpoblacion());
			modificacion.setPoblacionextranjera(solicitud.getPoblacionextranjera());
			modificacion.setOtraprovincia(solicitud.getOtraprovincia());

			int responseUpdate = cenDireccionesExtendsMapper.updateByPrimaryKey(modificacion);

			if (responseUpdate >= 1) {
				updateResponseDTO.setStatus(SigaConstants.OK);

				CenSolimodidirecciones record = new CenSolimodidirecciones();
				record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
				record.setIdestadosolic((short) 20);
				int response = cenSoliModiDireccionesExtendsMapper.updateByPrimaryKeySelective(record);

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"processSolModifDatosDirecciones() / cenSoliModiDireccionesExtendsMapper.updateByPrimaryKey() -> "
									+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

				} else {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
			}

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosGenerales() / No existe el usuario.");
		}

		LOGGER.info(
				"processSolModifDatosDirecciones() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		return updateResponseDTO;

	}

	@Override
	public UpdateResponseDTO denySolModifDatosDirecciones(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosDirecciones() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		CenSolimodidirecciones record = new CenSolimodidirecciones();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSoliModiDireccionesExtendsMapper.updateByPrimaryKeySelective(record);

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn(
					"denySolModifDatosDirecciones() / cenSoliModiDireccionesExtendsMapper.updateByPrimaryKeySelective() -> "
							+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		}

		updateResponseDTO.setStatus(SigaConstants.OK);
		LOGGER.info(
				"denySolModifDatosDirecciones() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
