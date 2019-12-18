package org.itcgae.siga.scs.services.impl.maestros;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.PartidasDTO;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPartidasPresupuestariasExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IPartidasPresupuestariasService;
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
							ejemplo.setIdinstitucion(idInstitucion.toString());  
							ejemplo.setIdpartidapresupuestaria(partidasItems.getIdpartidapresupuestaria());
							List<PartidasItem> nombrePartidasDuplicadas = scsPartidaPresupuestariaExtendsMapper.searchPartidaPres(ejemplo);

							PartidasItem ejemplo2 = new PartidasItem();
							ejemplo2.setDescripcion(partidasItems.getDescripcion());
							ejemplo2.setIdinstitucion(idInstitucion.toString());  
							ejemplo2.setIdpartidapresupuestaria(partidasItems.getIdpartidapresupuestaria());
							List<PartidasItem> descripcionPartidasDuplicadas = scsPartidaPresupuestariaExtendsMapper.searchPartidaPres(ejemplo2);
							
							if((nombrePartidasDuplicadas != null && nombrePartidasDuplicadas.size() > 0) || (descripcionPartidasDuplicadas != null && descripcionPartidasDuplicadas.size()>0)) {
								error.setCode(400);
								error.setDescription("menu.justiciaGratuita.maestros.errorpartidapresupuestaria");
								updateResponseDTO.setStatus(SigaConstants.KO);
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
								if(partidasItems.getImportepartida() == null || partidasItems.getImportepartida() =="") {
									 importe2 = "0";
								}
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
						LOGGER.error(e);
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
					
					if((nombrePartidasDuplicadas != null && nombrePartidasDuplicadas.size() > 0) || (descripcionPartidasDuplicadas != null && descripcionPartidasDuplicadas.size()>0)) {
						error.setCode(400);
						error.setDescription("menu.justiciaGratuita.maestros.errorpartidapresupuestaria");
						insertResponseDTO.setStatus(SigaConstants.KO);
					}
					else {
						
				
					
					LOGGER.info(
							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a scsPartidaPresupuestariaExtendsMapper para buscar la partida");

				
						String importe2 = partidasItem.getImportepartida().toString();
						if(partidasItem.getImportepartida() == null || partidasItem.getImportepartida() =="") {
							 importe2 = "0";
						}
						String numerofinal = importe2.replaceAll(",", ".");
						BigDecimal importe = new BigDecimal(numerofinal);
						ScsPartidapresupuestaria partida = new ScsPartidapresupuestaria();
						partida.setDescripcion(partidasItem.getDescripcion());
						partida.setNombrepartida(partidasItem.getNombrepartida());
						partida.setFechamodificacion(new Date());
						partida.setIdinstitucion(idInstitucion);
						partida.setImportepartida(importe);
						partida.setFechabaja(null);
						partida.setUsumodificacion(usuarios.get(0).getIdusuario());
						
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
