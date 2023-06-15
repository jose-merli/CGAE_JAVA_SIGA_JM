package org.itcgae.siga.scs.services.impl.maestros;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
//import org.itcgae.siga.DTOs.scs.RetencionIRPFDTO;
//import org.itcgae.siga.DTOs.scs.RetencionIRPFItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.RetencionIRPFDTO;
import org.itcgae.siga.DTOs.scs.RetencionIRPFItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsMaestroretenciones;
import org.itcgae.siga.db.entities.ScsMaestroretencionesExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposociedadExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRetencionirpfExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IRetencionesIrpfService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetencionesIrpfServiceImpl implements IRetencionesIrpfService {

	private Logger LOGGER = Logger.getLogger(RetencionesIrpfServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsRetencionirpfExtendsMapper scsRetencionesirpfExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Autowired
	private CenTiposociedadExtendsMapper cenTiposociedadExtendsMapper;

	@Override
	public RetencionIRPFDTO searchRetenciones(RetencionIRPFItem retencionItem, HttpServletRequest request) {
		LOGGER.info("searchRetenciones() -> Entrada al servicio para obtener prisiones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RetencionIRPFDTO retencionDTO = new RetencionIRPFDTO();
		List<RetencionIRPFItem> retencionItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchRetenciones() / scsProcuradorExtendsMapper.searchRetenciones() -> Entrada a scsProcuradorExtendsMapper para obtener las retenciones");

				retencionItem.setIdInstitucion(idInstitucion.toString());
				retencionItemList = scsRetencionesirpfExtendsMapper.searchRetenciones(usuarios.get(0).getIdlenguaje(),
						retencionItem);

				LOGGER.info(
						"searchRetenciones() / scsProcuradorExtendsMapper.searchRetenciones() -> Salida a scsProcuradorExtendsMapper para obtener las retenciones");

				if (retencionItemList != null) {
					retencionDTO.setRetencionIrpfItems(retencionItemList);
				}
			}

		}
		LOGGER.info("searchRetenciones() -> Salida del servicio para obtener las retenciones");
		return retencionDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteRetenciones(RetencionIRPFDTO retencionDTO, HttpServletRequest request) {

		LOGGER.info("deleteRetenciones() ->  Entrada al servicio para eliminar retenciones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (RetencionIRPFItem retencionItem : retencionDTO.getRetencionIrpfItems()) {

						ScsMaestroretencionesExample scsMaestrosRetencionesExample = new ScsMaestroretencionesExample();
						scsMaestrosRetencionesExample.createCriteria()
								.andIdretencionEqualTo(Integer.parseInt(retencionItem.getIdRetencion()));

						LOGGER.info(
								"deleteRetenciones() / scsRetencionirpfExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la retencion");

						List<ScsMaestroretenciones> maestroretencionesList = scsRetencionesirpfExtendsMapper
								.selectByExample(scsMaestrosRetencionesExample);

						LOGGER.info(
								"deleteRetenciones() / scsRetencionirpfExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la retencion");

						if (null != maestroretencionesList && maestroretencionesList.size() > 0) {

							ScsMaestroretenciones maestro = maestroretencionesList.get(0);

							maestro.setFechabaja(new Date());
							maestro.setFechamodificacion(new Date());
							maestro.setUsumodificacion(usuario.getIdusuario());
							LOGGER.info(
									"deleteRetenciones() / scsRetencionirpfExtendsMapper.updateByPrimaryKey() -> Entrada a scsRetencionirpfExtendsMapper para dar de baja a una retencion");

							response = scsRetencionesirpfExtendsMapper.updateByPrimaryKey(maestro);

							LOGGER.info(
									"deleteRetenciones() / scsRetencionirpfExtendsMapper.updateByPrimaryKey() -> Salida de scsRetencionirpfExtendsMapper para dar de baja a una retencion");
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deletePretensiones() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;

	}

	@Override
	@Transactional
	public UpdateResponseDTO activateRetenciones(RetencionIRPFDTO retencionDTO, HttpServletRequest request) {

		LOGGER.info("activatePretensiones() ->  Entrada al servicio para dar de alta a prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activateRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try {
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);

					for (RetencionIRPFItem retencionItem : retencionDTO.getRetencionIrpfItems()) {

						ScsMaestroretencionesExample maestroretencionesExample = new ScsMaestroretencionesExample();
						maestroretencionesExample.createCriteria()
								.andIdretencionEqualTo(Integer.valueOf(retencionItem.getIdRetencion()));

						LOGGER.info(
								"activateRetenciones() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsMaestroretenciones> retencionesList = scsRetencionesirpfExtendsMapper
								.selectByExample(maestroretencionesExample);

						LOGGER.info(
								"activateRetenciones() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != retencionesList && retencionesList.size() > 0) {

							ScsMaestroretenciones maestros = retencionesList.get(0);

							maestros.setFechabaja(null);
							maestros.setFechamodificacion(new Date());
							maestros.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateRetenciones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

							response = scsRetencionesirpfExtendsMapper.updateByPrimaryKey(maestros);

							LOGGER.info(
									"activateRetenciones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
						}

					}
				}
			} catch (Exception e) {
				LOGGER.error(e);
				response = 0;
				error.setCode(400);
				error.setDescription("general.mensaje.error.bbdd");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateRetenciones() -> Salida del servicio para dar de alta a las prisiones");

		return updateResponseDTO;

	}

	@Override
	public UpdateResponseDTO updateRetenciones(RetencionIRPFDTO retencionDTO, HttpServletRequest request) {

		LOGGER.info("updateRetenciones() ->  Entrada al servicio para editar prision");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ScsMaestroretenciones> scsRetencionesList= null;
		List<ScsMaestroretenciones> scsRetencionesSociedadRepetidaList= null;
		
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				for (RetencionIRPFItem retencionItem : retencionDTO.getRetencionIrpfItems()) {
					scsRetencionesList= null;

					try {
						ScsMaestroretencionesExample example = new ScsMaestroretencionesExample();

						// Obtenemos el fundamento de resolucion que queremos modificar
						if (retencionItem.getTipoSociedad() != null
								&& retencionItem.getTipoSociedad() != "") {
						example.createCriteria()
								.andIdretencionNotEqualTo(Integer.valueOf(retencionItem.getIdRetencion()))
								.andLetranifsociedadEqualTo(retencionItem.getTipoSociedad())
								.andFechabajaIsNull();
						
						scsRetencionesList = scsRetencionesirpfExtendsMapper
								.selectByExample(example);
						}
						

//						Buscamos otra retención con el mismo tipo sociedad seleccionado
						ScsMaestroretencionesExample ejemploRetencion = new ScsMaestroretencionesExample();

						// Obtenemos el fundamento de resolucion que queremos modificar
						if (retencionItem.getTipoSociedad() != null
								&& retencionItem.getTipoSociedad() != "") {
							ejemploRetencion.createCriteria()
								.andIdretencionNotEqualTo(Integer.valueOf(retencionItem.getIdRetencion()))
								.andLetranifsociedadEqualTo(retencionItem.getTipoSociedad());
							LOGGER.info(
									"updateRetenciones() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

							scsRetencionesSociedadRepetidaList = scsRetencionesirpfExtendsMapper
									.selectByExample(ejemploRetencion);
							LOGGER.info(
									"updateRetenciones() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Salida de scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

						}else {
							scsRetencionesSociedadRepetidaList = new ArrayList<ScsMaestroretenciones>();
						}
						
						
						
//						Continuamos solo si no encuentra más retenciones
						
						example = new ScsMaestroretencionesExample();
						example.createCriteria()
						.andIdretencionEqualTo(Integer.valueOf(retencionItem.getIdRetencion()))
						.andFechabajaIsNull();
						List<ScsMaestroretenciones> scsRetencionesListAux = scsRetencionesirpfExtendsMapper.selectByExample(example);

						GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
						genRecursosCatalogosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdrecursoNotEqualTo(scsRetencionesListAux.get(0).getDescripcion())
								.andDescripcionEqualTo(retencionItem.getDescripcion())
								.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("scs_maestroretenciones");
						List<GenRecursosCatalogos> scsRetencionesList2 = genRecursosCatalogosExtendsMapper
								.selectByExample(genRecursosCatalogosExample);

						LOGGER.info(
								"updateRetenciones() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");
						if ((scsRetencionesList != null && scsRetencionesList.size() > 0)
								|| (scsRetencionesList2 != null && scsRetencionesList2.size() > 0)
								|| scsRetencionesSociedadRepetidaList.size()>0) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.documentacionIRPF.existeRetencionMismoNombre");
						}else {

							example = new ScsMaestroretencionesExample();
							example.createCriteria()
									.andIdretencionEqualTo(Integer.valueOf(retencionItem.getIdRetencion()))
									.andFechabajaIsNull();

							LOGGER.info(
									"updateRetenciones() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");
							scsRetencionesList = scsRetencionesirpfExtendsMapper.selectByExample(example);

							LOGGER.info(
									"updateRetenciones() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Entrada a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

							LOGGER.info(
									"updateRetenciones() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Salida a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

							ScsMaestroretenciones maestros = scsRetencionesList.get(0);

							maestros.setRetencion(BigDecimal.valueOf(Double.valueOf(retencionItem.getRetencion())));
							maestros.setFechamodificacion(new Date());
							maestros.setUsumodificacion(usuario.getIdusuario().intValue());

							maestros.setClavem190(retencionItem.getClaveModelo());
							maestros.setLetranifsociedad(retencionItem.getTipoSociedad());
							
							GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
							genRecursosCatalogos.setIdrecurso(maestros.getDescripcion());
							genRecursosCatalogos.setIdlenguaje(usuarios.get(0).getIdlenguaje());
							genRecursosCatalogos = genRecursosCatalogosExtendsMapper
									.selectByPrimaryKey(genRecursosCatalogos);
							genRecursosCatalogos.setFechamodificacion(new Date());
							genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario().intValue());
							genRecursosCatalogos.setDescripcion(retencionItem.getDescripcion());
							response = scsRetencionesirpfExtendsMapper.updateByPrimaryKey(maestros);

							genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
							updateRestoIdiomas(genRecursosCatalogos);

							updateResponseDTO.setId(maestros.getIdretencion().toString());
						}

					} catch (Exception e) {
						LOGGER.error(e);
						response = 0;
						error.setCode(400);
						error.setDescription("general.mensaje.error.bbdd");
						updateResponseDTO.setStatus(SigaConstants.KO);
					}
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateRetenciones() -> Salida del servicio para editar prision");

		return updateResponseDTO;

	}

	@Override
	public InsertResponseDTO createRetencion(RetencionIRPFItem retencionItem, HttpServletRequest request) {

		LOGGER.info("createRetencion() ->  Entrada al servicio para crear una nueva prisión");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ScsMaestroretenciones> scsRetencionesSociedadRepetidaList= null;

		long idRetencion = 0;
		List<ScsMaestroretenciones> scsRetencionesList2 = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createRetencion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createRetencion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"createRetencion() / scsFundamentoscalificacionExtendsMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo juzgado");

					GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
					genRecursosCatalogosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andDescripcionEqualTo(retencionItem.getDescripcion())
							.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("scs_maestroretenciones");
;
					List<GenRecursosCatalogos> l = genRecursosCatalogosExtendsMapper
							.selectByExample(genRecursosCatalogosExample);

					if (retencionItem.getTipoSociedad() != null
							&& retencionItem.getTipoSociedad() != "") {
						ScsMaestroretencionesExample example = new ScsMaestroretencionesExample();
						example.createCriteria()
								.andLetranifsociedadEqualTo(retencionItem.getTipoSociedad());

						LOGGER.info(
								"updateRetenciones() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

						scsRetencionesList2 = scsRetencionesirpfExtendsMapper.selectByExample(example);

						LOGGER.info(
								"createRetencion() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión");
					}
					LOGGER.info(
							"createRetencion() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión");


//					Buscamos otra retención con el mismo tipo sociedad seleccionado
					ScsMaestroretencionesExample ejemploRetencion = new ScsMaestroretencionesExample();

					// Obtenemos el fundamento de resolucion que queremos modificar
					if (retencionItem.getTipoSociedad() != null
							&& retencionItem.getTipoSociedad() != "") {
						ejemploRetencion.createCriteria()
							.andLetranifsociedadEqualTo(retencionItem.getTipoSociedad());
						
						scsRetencionesSociedadRepetidaList = scsRetencionesirpfExtendsMapper
								.selectByExample(ejemploRetencion);
					}else {
						scsRetencionesSociedadRepetidaList = new ArrayList<ScsMaestroretenciones>();
					}
					
					
//					Continuamos solo si no encuentra más retenciones
					
					if ((l != null && l.size() > 0)
							|| (scsRetencionesList2 != null && scsRetencionesList2.size() > 0)
							|| scsRetencionesSociedadRepetidaList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.documentacionIRPF.existeRetencionMismoNombre");
						insertResponseDTO.setStatus(SigaConstants.KO);
					} else {

						ScsMaestroretenciones maestros = new ScsMaestroretenciones();

						maestros.setFechabaja(null);
						maestros.setFechamodificacion(new Date());
						maestros.setUsumodificacion(usuario.getIdusuario().intValue());
						maestros.setPordefecto("0");
						if(retencionItem.getClaveModelo() != null)
							maestros.setClavem190(retencionItem.getClaveModelo());
						if(retencionItem.getTipoSociedad() != null)
							maestros.setLetranifsociedad(retencionItem.getTipoSociedad());
						maestros.setRetencion(BigDecimal.valueOf(Double.valueOf(retencionItem.getRetencion())));
						
						GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
						genRecursosCatalogos.setDescripcion(retencionItem.getDescripcion());
						genRecursosCatalogos.setFechamodificacion(new Date());
						genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
						genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());
						genRecursosCatalogos.setIdinstitucion(usuario.getIdinstitucion());

						genRecursosCatalogos.setNombretabla("SCS_MAESTRORETENCIONES");
						genRecursosCatalogos.setCampotabla("DESCRIPCION");
						NewIdDTO idRC = genRecursosCatalogosExtendsMapper
								.getMaxIdRecursoCatalogo(idInstitucion.toString(), usuario.getIdlenguaje());
						NewIdDTO idP = scsRetencionesirpfExtendsMapper.getIdRetencion();

						if (idP == null) {
							maestros.setIdretencion((int) idRetencion);
						} else {
							idRetencion = (Short.valueOf(idP.getNewId()) + 1);
							maestros.setIdretencion((int) idRetencion);
						}
						if (idRC == null) {
							genRecursosCatalogos.setIdrecurso("1");

						} else {
							genRecursosCatalogos.setIdrecurso((Long.parseLong(idRC.getNewId()) + 1) + "");
							genRecursosCatalogos.setIdrecursoalias("SCS_MAESTRORETENCIONES.descripcion." + idInstitucion
									+ "." + genRecursosCatalogos.getIdrecurso());
							maestros.setDescripcion(genRecursosCatalogos.getIdrecurso());
						}

						LOGGER.info(
								"createRetencion() / scsPrisionExtendsMapper.insert() -> Entrada a scsPrisionExtendsMapper para insertar la nueva prision");

						response = scsRetencionesirpfExtendsMapper.insert(maestros);
						insertResponseDTO.setId(maestros.getIdretencion().toString());

						genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
						insertarRestoIdiomas(genRecursosCatalogos);
						LOGGER.info(
								"createRetencion() / scsPrisionExtendsMapper.insert() -> Salida de scsPrisionExtendsMapper para insertar la nueva prision");

					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idRetencion));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createPretension() -> Salida del servicio para crear una nueva prisión");

		return insertResponseDTO;

	}

	@Override
	public ComboDTO getSociedades(HttpServletRequest request) {
		LOGGER.info("getPartidoJudicial() -> Entrada al servicio para obtener combo partidos judiciales");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getPartidoJudicial() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getPartidoJudicial() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				List<ComboItem> comboItems = cenTiposociedadExtendsMapper.getSocietyTypes(usuario.getIdlenguaje());

				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getPartidoJudicial() -> Salida del servicio para obtener combo partidos judiciales");
		return combo;
	}

	// INSERTAR GEN RECURSOS CATALOGOS
	private int insertarRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

	// MODIFICAR GEN RECURSOS CATALOGOS
	private int updateRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

}