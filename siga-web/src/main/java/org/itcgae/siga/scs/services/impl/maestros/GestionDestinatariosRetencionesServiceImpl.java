package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DestinatariosDTO;
import org.itcgae.siga.DTOs.scs.DestinatariosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FcsDestinatariosRetenciones;
import org.itcgae.siga.db.entities.FcsDestinatariosRetencionesExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDestinatariosRetencionesExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IDestinatariosRetencionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionDestinatariosRetencionesServiceImpl implements IDestinatariosRetencionesService {

	private Logger LOGGER = Logger.getLogger(GestionDestinatariosRetencionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsDestinatariosRetencionesExtendsMapper scsDestinatariosRetencionesExtendsMapper;

	@Override
	public DestinatariosDTO searchDestinatario(DestinatariosItem destinatariosItem, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener modulos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DestinatariosDTO destinatariosDTO = new DestinatariosDTO();
		List<DestinatariosItem> destinatariosItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchModules() / scsPartidaPresupuestariaExtendsMapper.selectByExample() -> Entrada a scsPartidaPresupuestariaExtendsMapper para obtener los modulos");
				destinatariosItem.setIdinstitucion(idInstitucion.toString());
				destinatariosItems = scsDestinatariosRetencionesExtendsMapper.searchDestinatario(destinatariosItem);

				LOGGER.info(
						"searchModules() / scsPartidaPresupuestaria.selectByExample() -> Salida a scsPartidaPresupuestariaExtends para obtener los modulos");

				if (destinatariosItems != null) {
					destinatariosDTO.setDestinatariosItem(destinatariosItems);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener modulos");
		return destinatariosDTO;
	}

	@Override
	public UpdateResponseDTO deleteDestinatariosRetenc(DestinatariosDTO destinatariosDTO, HttpServletRequest request) {
		LOGGER.info("deleteModules() ->  Entrada al servicio para eliminar modulos");

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
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (DestinatariosItem destinatariosItem : destinatariosDTO.getDestinatariosItem()) {

						FcsDestinatariosRetenciones destinatariosRetenc = new FcsDestinatariosRetenciones();
						destinatariosRetenc.setIddestinatario(Integer.parseInt(destinatariosItem.getIddestinatario()));
						destinatariosRetenc.setIdinstitucion(idInstitucion);

						destinatariosRetenc = scsDestinatariosRetencionesExtendsMapper
								.selectByPrimaryKey(destinatariosRetenc);

						if (destinatariosRetenc.getFechabaja() == null) {
							destinatariosRetenc.setFechabaja(new Date());
						} else {
							destinatariosRetenc.setFechabaja(null);
						}

						destinatariosRetenc.setFechamodificacion(new Date());
						destinatariosRetenc.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

						response = scsDestinatariosRetencionesExtendsMapper.updateByPrimaryKey(destinatariosRetenc);

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteModules() -> Salida del servicio para eliminar modulos");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDestinatariosRet(DestinatariosDTO destinatariosDTO, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {

					for (DestinatariosItem destinatariosItem : destinatariosDTO.getDestinatariosItem()) {
						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

						FcsDestinatariosRetencionesExample example = new FcsDestinatariosRetencionesExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIddestinatarioEqualTo(Integer.parseInt(destinatariosItem.getIddestinatario()));

						FcsDestinatariosRetenciones fcsDestinatariosRetenciones = new FcsDestinatariosRetenciones();
						List<FcsDestinatariosRetenciones> fcsDestinatariosRetencionesLista = null;
						scsDestinatariosRetencionesExtendsMapper.selectByExample(example);

						 if (destinatariosItem.getOrden() != null &&
						 !destinatariosItem.getOrden().trim().equals("")){
						 fcsDestinatariosRetenciones.setOrden(Short.parseShort(destinatariosItem.getOrden()));
						 }else {
						 fcsDestinatariosRetenciones.setOrden(null);
						 }
						 if (destinatariosItem.getCuentacontable() != null &&
						 !destinatariosItem.getCuentacontable().trim().equals("")){
						 fcsDestinatariosRetenciones.setCuentacontable(destinatariosItem.getCuentacontable());
						 }else{
						 fcsDestinatariosRetenciones.setCuentacontable(null);
						 }
						fcsDestinatariosRetenciones.setIdinstitucion(idInstitucion);
						fcsDestinatariosRetenciones.setIddestinatario(Integer.parseInt(destinatariosItem.getIddestinatario()));
						fcsDestinatariosRetenciones.setNombre(destinatariosItem.getNombre());
						fcsDestinatariosRetenciones.setFechabaja(destinatariosItem.getFechabaja());
						fcsDestinatariosRetenciones.setFechamodificacion(new Date());
						fcsDestinatariosRetenciones.setUsumodificacion(usuario.getIdusuario());
						fcsDestinatariosRetenciones.setBloqueado("N");

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

						response = scsDestinatariosRetencionesExtendsMapper
								.updateByPrimaryKey(fcsDestinatariosRetenciones);

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

					}

				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("messages.inserted.success");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO createDestinatarioRetenc(DestinatariosItem destinatariosItem, HttpServletRequest request) {
		LOGGER.info("createPartidaPres() ->  Entrada al servicio para crear un nuevo destinatario");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Integer idDestinatario = 0;
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createPartidaPres() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createPartidaPres() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					FcsDestinatariosRetenciones destinatariosRetenc = new FcsDestinatariosRetenciones();
					destinatariosRetenc.setNombre(destinatariosItem.getNombre());
					destinatariosRetenc.setFechamodificacion(new Date());
					destinatariosRetenc.setIdinstitucion(idInstitucion);
					if (destinatariosItem.getOrden() != null && !destinatariosItem.getOrden().trim().equals(""))
						destinatariosRetenc.setOrden(Short.parseShort(destinatariosItem.getOrden()));
					if (destinatariosItem.getCuentacontable() != null
							&& !destinatariosItem.getCuentacontable().trim().equals(""))
						destinatariosRetenc.setCuentacontable(destinatariosItem.getCuentacontable());
					destinatariosRetenc.setFechabaja(null);
					destinatariosRetenc.setUsumodificacion(usuarios.get(0).getIdusuario());
					destinatariosRetenc.setBloqueado("N");
					NewIdDTO iddestinatarioretenciones = scsDestinatariosRetencionesExtendsMapper
							.getIdDestinatariosRetenc(usuario.getIdinstitucion());

					if (iddestinatarioretenciones == null) {
						destinatariosRetenc.setIddestinatario(1);
					} else {
						idDestinatario = (Integer.parseInt(iddestinatarioretenciones.getNewId()) + 1);
						destinatariosRetenc.setIddestinatario(idDestinatario);
					}

					LOGGER.info(
							"createPartidaPres() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva zona");

					response = scsDestinatariosRetencionesExtendsMapper.insert(destinatariosRetenc);

					LOGGER.info(
							"createPartidaPres() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva area");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idDestinatario));
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");

		return insertResponseDTO;
	}
}
