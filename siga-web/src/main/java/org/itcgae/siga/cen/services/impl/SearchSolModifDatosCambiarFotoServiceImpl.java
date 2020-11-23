package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ISearchSolModifDatosCambiarFotoService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenSolicmodifcambiarfoto;
import org.itcgae.siga.db.entities.CenSolicmodifcambiarfotoExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodifcambiarfotoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosCambiarFotoServiceImpl implements ISearchSolModifDatosCambiarFotoService {

	private Logger LOGGER = Logger.getLogger(SolicitudModificacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private CenSolicmodifcambiarfotoExtendsMapper cenSolicmodifcambiarfotoMapper;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Override
	public SolModificacionDTO searchSolModifDatosCambiarFoto(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info("searchSolModifDatosCambiarFoto() -> Entrada al servicio para recuperar los datos de la foto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosCambiarFoto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosCambiarFoto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"searchSolModifDatosCambiarFoto() / cenSolicModifExportarFotoExtendsMapper.searchSolModifDatosCambiarFoto() -> Entrada a cenSolicModifExportarFotoExtendsMapper para obtener los datos de la foto");
				// Añadir
				// List<CenSolicmodifcambiarfoto> solModificacionItems =
				// cenSolicmodifcambiarfotoMapper.selectByExample(exampleSolicitudesModif);
				List<SolModificacionItem> solModificacionItems = cenSolicmodifcambiarfotoMapper
						.searchSolModifDatoscambiarFoto(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
								String.valueOf(idInstitucion));
				solModificacionDTO.setSolModificacionItems(solModificacionItems);

			}
		}

		LOGGER.info("searchSolModifDatosCambiarFoto() -> Salida del servicio para recuperar los datos de la foto");

		return solModificacionDTO;
	}

	@Override
	public ComboItem searchSolModifDatosCambiarFotoDetail(SolModificacionItem solModificacionItem,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("searchSolModifDatosCambiarFoto() -> Entrada al servicio para recuperar los datos de la foto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionItem solModificacionDTO = new SolModificacionItem();
		ComboItem comboItem = new ComboItem();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosCambiarFoto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosCambiarFoto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<GenProperties> genProperties = new ArrayList<GenProperties>();
			String pathFinal = "";

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				// obtener el directorio para la fotografia de la persona
				LOGGER.debug("loadPhotography() -> Obtener el directorio para la fotografia de la persona jurídica");
				GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
				genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
				LOGGER.info(
						"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener el directorio para la fotografia de la persona jurídica");
				genProperties = genPropertiesMapper.selectByExample(genPropertiesExample);
				LOGGER.info(
						"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener el directorio para la fotografia de la persona jurídica");

				if (null != genProperties && genProperties.size() > 0) {
					String path = genProperties.get(0).getValor() + "/" + String.valueOf(idInstitucion) + "/";
					pathFinal = pathFinal.concat(path);

					CenSolicmodifcambiarfoto cenSolicmodifcambiarfoto = cenSolicmodifcambiarfotoMapper
							.selectByPrimaryKey(Short.valueOf(solModificacionItem.getIdSolicitud()));

					if (cenSolicmodifcambiarfoto != null) {
						comboItem.setLabel(cenSolicmodifcambiarfoto.getFotografia());

						pathFinal = pathFinal.concat(comboItem.getLabel());
						LOGGER.info("loadPhotography() -> Se obtiene fotografia de la persona jurídica del path:  "
								+ pathFinal);

						// Se coge la imagen de la persona juridica
						File file = new File(pathFinal);
						FileInputStream fis = null;
						try {
							fis = new FileInputStream(file);
							IOUtils.copy(fis, response.getOutputStream());

						} catch (FileNotFoundException e) {
							LOGGER.error("No se ha encontrado el fichero", e);
							comboItem = null;

						} catch (IOException e1) {
							LOGGER.error(
									"No se han podido escribir los datos binarios de la imagen en la respuesta HttpServletResponse",
									e1);
							comboItem = null;
						} finally {
							if (null != fis)
								try {
									fis.close();
								} catch (IOException e) {
									LOGGER.error("No se ha cerrado el archivo correctamente", e);
									comboItem = null;
								}
						}

					}

				}
			}
		}

		LOGGER.info("searchSolModifDatosCambiarFoto() -> Salida del servicio para recuperar los datos de la foto");

		return comboItem;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosCambiarFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosCambiarFoto() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");

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

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);

			List<CenSolicmodifcambiarfoto> solicitud = new ArrayList<CenSolicmodifcambiarfoto>();
			CenSolicmodifcambiarfotoExample example = new CenSolicmodifcambiarfotoExample();

			example.createCriteria().andIdpersonaEqualTo(Long.parseLong(solModificacionItem.getIdPersona()))
					.andIdinstitucionEqualTo(idInstitucion)
					.andIdsolicitudEqualTo(Short.valueOf(solModificacionItem.getIdSolicitud()));

			solicitud = cenSolicmodifcambiarfotoMapper.selectByExample(example);
			if (null != solicitud && solicitud.size() > 0) {
				CenCliente modificacion = new CenCliente();
				modificacion.setIdinstitucion(idInstitucion);
				modificacion.setIdpersona(solicitud.get(0).getIdpersona());
				modificacion.setFechamodificacion(new Date());
				modificacion.setUsumodificacion(usuario.getIdusuario());
				modificacion.setFotografia(solicitud.get(0).getFotografia());

				int responseUpdate = cenClienteExtendsMapper.updateByPrimaryKeySelective(modificacion);

				if (responseUpdate >= 1) {
					CenSolicmodifcambiarfoto record = new CenSolicmodifcambiarfoto();
					record.setIdsolicitud(Short.valueOf(solModificacionItem.getIdSolicitud()));
					record.setIdestadosolic((short) 20);
					int response = cenSolicmodifcambiarfotoMapper.updateByPrimaryKeySelective(record);

					if (response == 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn(
								"processSolModifDatosCambiarFoto() / cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelective() -> "
										+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}

				}

			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("processSolModifDatosGenerales() / No existe el usuario.");
			}

			LOGGER.info(
					"processSolModifDatosCambiarFoto() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO denySolModifDatosCambiarFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosCambiarFoto() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		CenSolicmodifcambiarfoto record = new CenSolicmodifcambiarfoto();
		record.setIdsolicitud(Short.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolicmodifcambiarfotoMapper.updateByPrimaryKeySelective(record);

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn(
					"denySolModifDatosCambiarFoto() / cenSolicModifExportarFotoExtendsMapper.updateByPrimaryKeySelective() -> "
							+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		LOGGER.info(
				"denySolModifDatosCambiarFoto() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
