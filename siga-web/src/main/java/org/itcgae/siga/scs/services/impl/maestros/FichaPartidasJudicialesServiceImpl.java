package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInfluencia;
import org.itcgae.siga.db.entities.CenInfluenciaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.CenInfluenciaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPartidasJudicialExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IPartidasJudicialesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaPartidasJudicialesServiceImpl implements IPartidasJudicialesService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenPartidasJudicialExtendsMapper cenPartidaJudicialExtendsMapper;
	
	@Autowired
	private CenInfluenciaExtendsMapper cenInfluenciaExtendsMapper;

	@Override
	public PartidasJudicialesDTO searchPartida(PartidasJudicialesItem partidasJudicialesItem, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener modulos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PartidasJudicialesDTO partidasJudicialDTO = new PartidasJudicialesDTO();
		List<PartidasJudicialesItem> partidasJudicialesItems = null;

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
				partidasJudicialesItem.setIdinstitucion(idInstitucion.toString());
				partidasJudicialesItems = cenPartidaJudicialExtendsMapper.searchPartida(partidasJudicialesItem);

				LOGGER.info(
						"searchModules() / scsPartidaPresupuestaria.selectByExample() -> Salida a scsPartidaPresupuestariaExtends para obtener los modulos");


				if (partidasJudicialesItems != null) {
					partidasJudicialDTO.setPartidasJudicialesItem(partidasJudicialesItems);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener modulos");
		return partidasJudicialDTO;
	}

	
	
	@Override
	public UpdateResponseDTO deletePartidasJudi(PartidasJudicialesDTO partidasjudicialesDTO, HttpServletRequest request) {
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

					for (PartidasJudicialesItem partidasjudicialesItem : partidasjudicialesDTO.getPartidasJudicialesItem()) {

						
						CenInfluencia partidaj = new CenInfluencia();
						partidaj.setIdpartido(Long.parseLong(partidasjudicialesItem.getIdpartido()));
						partidaj.setIdinstitucion(idInstitucion);
						
						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

						response = cenInfluenciaExtendsMapper.deleteByPrimaryKey(partidaj);

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

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
	public ComboDTO getPartidoJudicial(HttpServletRequest request) {
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

				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				List<ComboItem> comboItems = cenPartidaJudicialExtendsMapper.getPartidosJudiciales(idInstitucion);

				LOGGER.info(
						"getPartidoJudicial() / cenPartidojudicialExtendsMapper.getPartidosJudiciales() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getPartidoJudicial() -> Salida del servicio para obtener combo partidos judiciales");
		return combo;
	}
	
	
	@Override
	public InsertResponseDTO createPartidoJudi(PartidasJudicialesItem partidasJudicialesItem, HttpServletRequest request) {
		LOGGER.info("createGroupZone() ->  Entrada al servicio para crear un nuevo grupo zona");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		short idPartidoJ = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createGroupZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					
					LOGGER.info(
							"createGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Entrada a ageEventoExtendsMapper para buscar la sesión");
					
//					PartidasJudicialesItem ejemplo = new PartidasJudicialesItem();
//					ejemplo.setIdinstitucion(idInstitucion.toString());  
//					ejemplo.setIdpartido(partidasJudicialesItem.getIdpartido());
					
					CenInfluenciaExample ejemplo2 = new CenInfluenciaExample();
					ejemplo2.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpartidoEqualTo(Long.parseLong(partidasJudicialesItem.getIdpartido()));
					List<CenInfluencia> nombreDuplicada = cenInfluenciaExtendsMapper.selectByExample(ejemplo2);
					
					
					LOGGER.info(
							"createGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					if (nombreDuplicada != null && nombreDuplicada.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.gestionPartidoJudicial.existePartidoJudicial");

					} else {

						CenInfluencia partidoJudicial = new CenInfluencia();

						partidoJudicial.setFechamodificacion(new Date());
						partidoJudicial.setUsumodificacion(usuario.getIdusuario().intValue());
						partidoJudicial.setIdinstitucion(idInstitucion);
						partidoJudicial.setIdpartido(Long.parseLong(partidasJudicialesItem.getIdpartido()));

						LOGGER.info(
								"createGroupZone() / scsZonasExtendsMapper.insert() -> Entrada a scsZonasExtendsMapper para insertar el nuevo grupo zona");

						response = cenInfluenciaExtendsMapper.insert(partidoJudicial);

						LOGGER.info(
								"createGroupZone() / scsZonasExtendsMapper.insert() -> Salida de scsZonasExtendsMapper para insertar el nuevo grupo zona");
					}
					
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idPartidoJ)));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createGroupZone() -> Salida del servicio para crear un nuevo grupo zona");

		
	}
		return insertResponseDTO;
	}
}





