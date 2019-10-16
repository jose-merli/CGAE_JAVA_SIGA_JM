package org.itcgae.siga.scs.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPartidasPresupuestariasExtendsMapper;
import org.itcgae.siga.scs.service.IPartidasPresupuestariasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FichaPartidasPresupuestariasServiceImpl implements IPartidasPresupuestariasService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasPresupuestariasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsPartidasPresupuestariasExtendsMapper scsPartidaPresupuestariaExtendsMapper;

	@Autowired
	private ScsPartidapresupuestariaMapper scsPartidapresupuestariaMapper;

	@Override
	public PartidasDTO searchPartida(PartidasItem partidasItem, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener modulos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PartidasDTO partidasDTO = new PartidasDTO();
		List<PartidasItem> partidasItems = null;

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
				partidasItem.setIdinstitucion(idInstitucion.toString());
				partidasItems = scsPartidaPresupuestariaExtendsMapper.searchPartida(partidasItem);

				LOGGER.info(
						"searchModules() / scsPartidaPresupuestaria.selectByExample() -> Salida a scsPartidaPresupuestariaExtends para obtener los modulos");


				if (partidasItems != null) {
					partidasDTO.setPartidasItem(partidasItems);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener modulos");
		return partidasDTO;
	}
	
	
	@Override
	public UpdateResponseDTO deletePartidasPres(PartidasDTO partidasDTO, HttpServletRequest request) {
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

					for (PartidasItem partidasItem : partidasDTO.getPartidasItem()) {

						
						ScsPartidapresupuestaria partida = new ScsPartidapresupuestaria();
						partida.setIdpartidapresupuestaria(Integer.parseInt(partidasItem.getIdpartidapresupuestaria()));
						partida.setIdinstitucion(idInstitucion);

						partida = scsPartidaPresupuestariaExtendsMapper.selectByPrimaryKey(partida);
						
						if(partidasItem.getFechabaja() == null) {
							partida.setFechabaja(new Date());
						}else {
							partida.setFechabaja(null);
						}
						
						partida.setFechamodificacion(new Date());
						partida.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

						response = scsPartidaPresupuestariaExtendsMapper.updateByPrimaryKey(partida);

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
	public UpdateResponseDTO updatePartidasPres(PartidasDTO partidasDTO, HttpServletRequest request) {
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

				try {
						
						for (PartidasItem partidasItems : partidasDTO.getPartidasItem()) {

							
							PartidasItem ejemplo = new PartidasItem();
							ejemplo.setNombrepartida(partidasItems.getNombrepartida());
//							ejemplo.setDescripcion(partidasItem.getDescripcion());
							ejemplo.setIdinstitucion(idInstitucion.toString());  
							ejemplo.setIdpartidapresupuestaria(partidasItems.getIdpartidapresupuestaria());
							List<PartidasItem> nombrePartidasDuplicadas = scsPartidaPresupuestariaExtendsMapper.searchPartidaPres(ejemplo);

							PartidasItem ejemplo2 = new PartidasItem();
//							ejemplo.setNombrepartida(partidasItem.getNombrepartida());
							ejemplo2.setDescripcion(partidasItems.getDescripcion());
							ejemplo2.setIdinstitucion(idInstitucion.toString());  
							ejemplo2.setIdpartidapresupuestaria(partidasItems.getIdpartidapresupuestaria());
							List<PartidasItem> descripcionPartidasDuplicadas = scsPartidaPresupuestariaExtendsMapper.searchPartidaPres(ejemplo2);
							
							if ((nombrePartidasDuplicadas != null && nombrePartidasDuplicadas.size() > 0)|| (descripcionPartidasDuplicadas != null && descripcionPartidasDuplicadas.size() > 0)) {
								
								error.setCode(400);
								error.setDescription("Este nombre o descripción ya existe");

							}else {
								LOGGER.info(
										"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

								ScsPartidapresupuestariaExample example = new ScsPartidapresupuestariaExample();
								example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpartidapresupuestariaEqualTo(Integer.parseInt(partidasItems.getIdpartidapresupuestaria()));
								ScsPartidapresupuestaria scsPartidaPresupuestaria = new ScsPartidapresupuestaria();
								List<ScsPartidapresupuestaria> scsPartidaPresupuestariaLista = scsPartidaPresupuestariaExtendsMapper.selectByExample(example);
								scsPartidaPresupuestaria = scsPartidaPresupuestariaLista.get(0);
								
								String importe2 = partidasItems.getImportepartida().toString();
								String numerofinal = importe2.replaceAll(",", ".");
								BigDecimal importe = new BigDecimal(numerofinal);
								scsPartidaPresupuestaria.setDescripcion(partidasItems.getDescripcion());
								scsPartidaPresupuestaria.setFechabaja(partidasItems.getFechabaja());
								scsPartidaPresupuestaria.setFechamodificacion(partidasItems.getFechamodificacion());
								scsPartidaPresupuestaria.setImportepartida(importe);
								scsPartidaPresupuestaria.setNombrepartida(partidasItems.getNombrepartida());
								scsPartidaPresupuestaria.setUsumodificacion(Integer.parseInt(partidasItems.getUsumodificacion()));
								LOGGER.info(
										"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");


								
									LOGGER.info(
											"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

									response = scsPartidaPresupuestariaExtendsMapper.updateByExample(scsPartidaPresupuestaria, example);

									LOGGER.info(
											"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
							}
							
						}
					
					}
											
					 catch (Exception e) {
						response = 0;
						error.setCode(400);
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
						updateResponseDTO.setStatus(SigaConstants.KO);
					}				
							

			if (response == 0 && error.getDescription() == null)
			{
				error.setCode(400);
				error.setDescription("No se ha modificado la partida presupuestaria");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
			else if(response == 1 && existentes != 0 ) {
				error.setCode(200);
				error.setDescription("Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");
				
			}else if (error.getCode() == null) {
				error.setCode(200);
				error.setDescription("Se ha modificado la partida presupuestaria correctamente");
			}

			updateResponseDTO.setError(error);

			LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}
	}
		return updateResponseDTO;
}

	
	@Override
	@Transactional
	public InsertResponseDTO createPartidaPres(PartidasItem partidasItem, HttpServletRequest request) {
		LOGGER.info("createPartidaPres() ->  Entrada al servicio para crear una nueva partida presupuestaria");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Integer idPartidaPresupuestaria = 0;
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

					ScsPartidapresupuestariaExample ejemplo = new ScsPartidapresupuestariaExample();
					ejemplo.createCriteria().andNombrepartidaEqualTo(partidasItem.getNombrepartida())
					.andIdinstitucionEqualTo(idInstitucion);  
					
					List<ScsPartidapresupuestaria> nombrePartidasDuplicadas = scsPartidapresupuestariaMapper.selectByExample(ejemplo);


					ScsPartidapresupuestariaExample ejemplo2 = new ScsPartidapresupuestariaExample();
					ejemplo2.createCriteria().andDescripcionEqualTo(partidasItem.getDescripcion())
					.andIdinstitucionEqualTo(idInstitucion);  
					
					List<ScsPartidapresupuestaria> descripcionPartidasDuplicadas = scsPartidapresupuestariaMapper.selectByExample(ejemplo2);
					
					LOGGER.info(
							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a scsPartidaPresupuestariaExtendsMapper para buscar la partida");

					if ((nombrePartidasDuplicadas != null && nombrePartidasDuplicadas.size() > 0) || (descripcionPartidasDuplicadas != null && descripcionPartidasDuplicadas.size() > 0)) {
						
						error.setCode(400);
						error.setDescription("Este nombre ya existe");

					} else {

						ScsPartidapresupuestaria partida = new ScsPartidapresupuestaria();
						partida.setDescripcion(partidasItem.getDescripcion());
						partida.setNombrepartida(partidasItem.getNombrepartida());
						partida.setFechamodificacion(new Date());
						partida.setIdinstitucion(idInstitucion);
						partida.setImportepartida(BigDecimal.valueOf(Double.valueOf(partidasItem.getImportepartida())));
						partida.setFechabaja(null);
						partida.setUsumodificacion(usuarios.get(0).getIdusuario());
						
//						ScsArea area = new ScsArea();
//						area.setContenido(areasItem.getContenido());
//						area.setFechamodificacion(new Date());
//						area.setIdinstitucion(idInstitucion);
//						area.setNombre(areasItem.getNombreArea());
//						area.setUsumodificacion(usuarios.get(0).getIdusuario());

						NewIdDTO idPartidaPres = scsPartidaPresupuestariaExtendsMapper.getIdPartidaPres(usuario.getIdinstitucion());

						if (idPartidaPres == null) {
							partida.setIdpartidapresupuestaria(1);
						} else {
							idPartidaPresupuestaria = (Integer.parseInt(idPartidaPres.getNewId()) + 1);
							partida.setIdpartidapresupuestaria(idPartidaPresupuestaria);
						}

						LOGGER.info(
								"createPartidaPres() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva zona");

						response = scsPartidaPresupuestariaExtendsMapper.insert(partida);

						LOGGER.info(
								"createPartidaPres() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva area");
					}

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
			insertResponseDTO.setId(String.valueOf(idPartidaPresupuestaria));
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");

		return insertResponseDTO;
	}
}



















//	@Override
//	public ComboDTO getPartidoJudicial(HttpServletRequest request) {
//		LOGGER.info("getPartidoJudicial() -> Entrada al servicio para obtener combo partidos judiciales");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		ComboDTO combo = new ComboDTO();
//
//		if (idInstitucion != null) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"getPartidoJudicial() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"getPartidoJudicial() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (usuarios != null && usuarios.size() > 0) {
//
//				LOGGER.info(
//						"getPartidoJudicial() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");
//	
//				List<ComboItem> comboItems = ScsJurisdiccionExtendsMapper.getJurisdicciones(idInstitucion.toString(), dni);
//
//				LOGGER.info(
//						"getPartidoJudicial() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");
//
//				combo.setCombooItems(comboItems);
//			}
//
//		}
//		LOGGER.info("getPartidoJudicial() -> Salida del servicio para obtener combo jurisdicciones");
//		return combo;
//	}
//	
//	@Override
//	public AreasDTO searchAreas(AreasItem areasItem, HttpServletRequest request) {
//		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener las zonas");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		AreasDTO areasDTO = new AreasDTO();
//		List<AreasItem> areasItems = null;
//
//		if (idInstitucion != null) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"searchSubzonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"searchSubzonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (usuarios != null && usuarios.size() > 0) {
//
//				LOGGER.info(
//						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
//				areasItem.setidInstitucion(idInstitucion);
//				areasItems = scsAreasMateriasExtendsMapper.searchAreas(areasItem);
//
//				LOGGER.info(
//						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");
//
//
//				if (areasItems != null) {
//					areasDTO.setAreasItems(areasItems);
//				}
//			}
//
//		}
//		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener las zonas");
//		return areasDTO;
//	}
//	
//	@Override
//	public UpdateResponseDTO updateArea(AreasItem areasItem, HttpServletRequest request) {
//		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar zonas de un grupo zona");
//
//		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//
//				try {
//						// Eliminamos asociaciones partidos judiciales con zona
//
//						ScsArea area = new ScsArea();
//						area.setContenido(areasItem.getContenido());
//						area.setFechamodificacion(new Date());
//						area.setIdarea(Short.parseShort(areasItem.getIdArea()));
//						area.setIdinstitucion(areasItem.getidInstitucion());
//						area.setNombre(areasItem.getNombreArea());
//						area.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//						LOGGER.info(
//								"deleteAreas() / scsAreasMateriasExtendsMapper.deleteByExample() -> Entrada a scsAreasMateriasExtendsMapper para eliminar las Areas seleccionadas");
//
//						response = scsAreasMateriasExtendsMapper.updateByPrimaryKey(area);
//
//						LOGGER.info(
//								"deleteAreas() / scsAreasMateriasExtendsMapper.deleteByExample() -> Salida de scsAreasMateriasExtendsMapper para eliminar las Areas seleccionadas");
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.eliminarError");
//			updateResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			error.setDescription("general.message.registro.actualizado");
//		}
//
//		updateResponseDTO.setError(error);
//
//		LOGGER.info("deleteZones() -> Salida del servicio para eliminar zonas de un grupo zona");
//
//		return updateResponseDTO;
//	}
//
//
//	

//
//	@Override
//	@Transactional
//	public InsertResponseDTO createArea(AreasItem areasItem, HttpServletRequest request) {
//		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");
//
//		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		Short idAreaNueva = 0;
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//				AdmUsuarios usuario = usuarios.get(0);
//
//				try {
//
//					AreasItem ejemplo = new AreasItem();
//					ejemplo.setNombreArea(areasItem.getNombreArea());
//					ejemplo.setidInstitucion(idInstitucion);
//					
//					List<AreasItem> areasDuplicadas = scsAreasMateriasExtendsMapper.searchAreas(ejemplo);
//
//					LOGGER.info(
//							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");
//
//					if (areasDuplicadas != null && areasDuplicadas.size() > 0) {
//						
//						error.setCode(400);
//						error.setDescription("messages.censo.nombreExiste");
//						
//					} else {
//						
//							ScsArea area = new ScsArea();
//							area.setContenido(areasItem.getContenido());
//							area.setFechamodificacion(new Date());
//							area.setIdinstitucion(idInstitucion);
//							area.setNombre(areasItem.getNombreArea());
//							area.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//							NewIdDTO idArea = scsAreasMateriasExtendsMapper.getIdArea(usuario.getIdinstitucion());
//
//							if (idArea == null) {
//								area.setIdarea((short) 1);
//							} else {
//								idAreaNueva = (short) (Integer.parseInt(idArea.getNewId()) + 1);
//								area.setIdarea(idAreaNueva);
//							}
//
//							LOGGER.info(
//									"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva zona");
//
//							response = scsAreasMateriasExtendsMapper.insert(area);
//
//							
//							LOGGER.info(
//									"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva area");
//						}
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					insertResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			insertResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			insertResponseDTO.setId(String.valueOf(Short.valueOf(idAreaNueva)));
//			error.setDescription("messages.inserted.success");
//		}
//
//		insertResponseDTO.setError(error);
//
//		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");
//
//		return insertResponseDTO;
//	}
//	
//	@Override
//	public MateriasDTO searchMaterias(String idArea, HttpServletRequest request) {
//		LOGGER.info("searchMaterias() -> Entrada al servicio para obtener las Materias");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		MateriasDTO materiasDTO = new MateriasDTO();
//		List<MateriasItem> materiasItems = null;
//		MateriasItem materia = new MateriasItem();
//		if (idInstitucion != null) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"searchMaterias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"searchMaterias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (usuarios != null && usuarios.size() > 0) {
//
//				LOGGER.info(
//						"searchMaterias() / scsMateriaExtendsMapper.searchMateria() -> Entrada a scsMateriaExtendsMapper para obtener las Materias");
//				materia.setIdArea(idArea);
//				materia.setidInstitucion(idInstitucion);
//				materiasItems = scsMateriaExtendsMapper.searchMateria(materia);
//
//				LOGGER.info(
//						"searchMaterias() / scsMateriaExtendsMapper.searchMateria() -> Salida a scsMateriaExtendsMapper para obtener las Materias");
//
//				for (MateriasItem materias : materiasItems) {
//
//					if (materias.getJurisdiccion() != null) {
//
//						String[] materiasEnvio = materias.getJurisdiccion().split(";");
//						materias.setJurisdicciones(materiasEnvio);
//
//					}
//				}
//				materiasDTO.setmateriasItems(materiasItems);
//			}
//
//		}
//		LOGGER.info("searchMateria() -> Salida del servicio para obtener las Materias");
//		return materiasDTO;
//	}
//	
//
//	@Override
//	public UpdateResponseDTO updateMaterias(AreasDTO areasDTO, HttpServletRequest request) {
//		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
//// PENDIENTE DE ACABAR
//		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//
//				try {
//					
//					for (AreasItem areasItem : areasDTO.getAreasItems()) {
//					
//						ScsMateria materia = new ScsMateria();
//						materia.setIdmateria(Short.parseShort(areasItem.getIdMateria()));
//						materia.setFechamodificacion(new Date());
//						materia.setIdarea(Short.parseShort(areasItem.getIdArea()));
//						materia.setIdinstitucion(areasItem.getidInstitucion());
//						materia.setNombre(areasItem.getNombreMateria());
//						materia.setContenido(areasItem.getContenido());
//						materia.setUsumodificacion(usuarios.get(0).getIdusuario());
//						response = scsMateriaExtendsMapper.updateByPrimaryKeySelective(materia);
//						
//						String[] jurisdiccionesCombo  = areasItem.getJurisdiccion().split(";");
//						
//						ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
//						ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion()).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
//						List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper.selectByExample(ejemploJurisdiccion);
//						
//							for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
//								scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
//							}
//							
//							if(jurisdiccionesCombo[0] != "")
//							for (String jurisdicciones : jurisdiccionesCombo) {
//								ScsMateriajurisdiccion materiaJurisdiccion = new ScsMateriajurisdiccion();
//								materiaJurisdiccion.setFechamodificacion(new Date());
//								materiaJurisdiccion.setIdarea(Short.parseShort(areasItem.getIdArea()));
//								materiaJurisdiccion.setIdmateria(Short.parseShort(areasItem.getIdMateria()));
//								materiaJurisdiccion.setIdinstitucion(areasItem.getidInstitucion());
//								materiaJurisdiccion.setIdjurisdiccion(Short.parseShort(jurisdicciones));
//								materiaJurisdiccion.setUsumodificacion(usuarios.get(0).getIdusuario());
//								scsMateriaJurisdiccionExtendsMapper.insert(materiaJurisdiccion);
//							}
//						}
//						
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.actualizarerror");
//			updateResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			error.setDescription("general.message.registro.actualizado");
//		}
//
//		updateResponseDTO.setError(error);
//
//		LOGGER.info("deleteZones() -> Salida del servicio para eliminar zonas de un grupo zona");
//
//		return updateResponseDTO;
//	}
//
//	@Override
//	public UpdateResponseDTO deleteMaterias(AreasDTO areasDTO, HttpServletRequest request) {
//		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
//		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//
//				try {
//					boolean existe = false;
//					
//					for (AreasItem areasItem : areasDTO.getAreasItems()) {
//						
//						ScsTurnoExample ejemploTurno = new ScsTurnoExample();
//						ejemploTurno.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdinstitucionEqualTo(idInstitucion);
//						List<ScsTurno> turnos = scsTurnoMapper.selectByExample(ejemploTurno);
//
//						ExpExpedienteExample ejemploExpediente = new ExpExpedienteExample();
//						ejemploExpediente.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdinstitucionEqualTo(idInstitucion);
//						List<ExpExpediente> expedientes = expExpedienteMapper.selectByExample(ejemploExpediente);
//
//						if(!(turnos == null || turnos.size() == 0) || !(expedientes == null || expedientes.size() == 0)) {
//							existe = true; 
//						}
//					}
//					
//					if(!existe) {
//						for (AreasItem areasItem : areasDTO.getAreasItems()) {
//							
//							ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
//							ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion()).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
//							List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper.selectByExample(ejemploJurisdiccion);
//							
//								for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
//									scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
//								}
//								
//							ScsMateriaExample example = new ScsMateriaExample();
//							example.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdinstitucionEqualTo(idInstitucion).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()));
//							response = scsMateriaExtendsMapper.deleteByExample(example);
//							
//					}
//					}else {
//						response = 2;
//					}
//					
//					
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.erroreliminadomaterias");
//			updateResponseDTO.setStatus(SigaConstants.KO);
//		
//		} else if(response == 2) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.materiaEnUso");
//			updateResponseDTO.setStatus(SigaConstants.KO);	
//	
//		}else{
//			error.setCode(200);
//			error.setDescription("general.message.registro.actualizado");
//		}
//
//		updateResponseDTO.setError(error);
//
//		LOGGER.info("deleteZones() -> Salida del servicio para eliminar materias de un area");
//
//		return updateResponseDTO;
//	}
//	
//	@Override
//	@Transactional
//	public InsertResponseDTO createMaterias(AreasItem areasItem, HttpServletRequest request) {
//		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");
//
//		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		Short idMateriaNueva = 0;
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"createMaterias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"createMaterias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//				AdmUsuarios usuario = usuarios.get(0);
//
//				try {
//
////					MateriasItem ejemplo = new MateriasItem();
////					ejemplo.setNombreMateria(areasItem.getNombreMateria());
////					ejemplo.setIdArea(areasItem.getIdArea());
////					ejemplo.setidInstitucion(idInstitucion);
////					
////					List<MateriasItem> materiasDuplicadas = scsMateriaExtendsMapper.searchMateria(ejemplo);
//
//					LOGGER.info(
//							"createMaterias() / scsAreasMateriasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");
//
////					if (materiasDuplicadas != null && materiasDuplicadas.size() > 0) {
////						
////						error.setCode(400);
////						error.setDescription("messages.censo.nombreExiste");
////						
////					} else {
//						
//							ScsMateria materia = new ScsMateria();
//							materia.setContenido(areasItem.getContenido());
//							materia.setFechamodificacion(new Date());
//							materia.setIdinstitucion(idInstitucion);
//							materia.setNombre(areasItem.getNombreMateria());
//							materia.setUsumodificacion(usuarios.get(0).getIdusuario());
//							materia.setIdarea(Short.parseShort(areasItem.getIdArea()));
//							
//							String[] jurisdiccionesCombo = null;
//							
//							NewIdDTO idMateria = scsMateriaExtendsMapper.getIdMateria(usuario.getIdinstitucion());
//							if(areasItem.getJurisdiccion() != "" && areasItem.getJurisdiccion() != null) {
//								jurisdiccionesCombo  = areasItem.getJurisdiccion().split(";");
//							}
//
//							if (idMateria == null) {
//								idMateriaNueva = 1;
//								materia.setIdmateria((short) 1);
//							} else {
//								idMateriaNueva = (short) (Integer.parseInt(idMateria.getNewId()) + 1);
//								materia.setIdmateria(idMateriaNueva);
//							}
//
//							LOGGER.info(
//									"createMaterias() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva materia");
//
//							response = scsMateriaExtendsMapper.insert(materia);
//							if(jurisdiccionesCombo != null) {
//								for (String jurisdicciones : jurisdiccionesCombo) {
//									ScsMateriajurisdiccion materiaJurisdiccion = new ScsMateriajurisdiccion();
//									materiaJurisdiccion.setFechamodificacion(new Date());
//									materiaJurisdiccion.setIdarea(Short.parseShort(areasItem.getIdArea()));
//									materiaJurisdiccion.setIdmateria(idMateriaNueva);
//									materiaJurisdiccion.setIdinstitucion(idInstitucion);
//									materiaJurisdiccion.setIdjurisdiccion(Short.parseShort(jurisdicciones));
//									materiaJurisdiccion.setUsumodificacion(usuarios.get(0).getIdusuario());
//									scsMateriaJurisdiccionExtendsMapper.insert(materiaJurisdiccion);
//								}
//							}
//							
//							LOGGER.info(
//									"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva materia");
////						}
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					insertResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			insertResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			insertResponseDTO.setId(String.valueOf(Short.valueOf(idMateriaNueva)));
//			error.setDescription("messages.inserted.success");
//		}
//
//		insertResponseDTO.setError(error);
//
//		LOGGER.info("createZone() -> Salida del servicio para crear una nueva materia");
//
//		return insertResponseDTO;
//	}
//	
