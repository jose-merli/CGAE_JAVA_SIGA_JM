package org.itcgae.siga.scs.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTO.scs.TurnosDTO;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.service.IGestionTurnosService;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionTurnosServiceImpl implements IGestionTurnosService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;


	@Override
	public TurnosDTO busquedaTurnos(TurnosItem turnosItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TurnosDTO turnosDTO = new TurnosDTO();
		List<TurnosItem> turnosItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				turnosItems = scsTurnosExtendsMapper.busquedaTurnos(turnosItem,idInstitucion);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (turnosItems != null) {
					turnosDTO.setTurnosItems(turnosItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return turnosDTO;
	}
	
	@Override
	public TurnosDTO busquedaFichaTurnos(TurnosItem turnosItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TurnosDTO turnosDTO = new TurnosDTO();
		List<TurnosItem> turnosItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				turnosItems = scsTurnosExtendsMapper.busquedaFichaTurnos(turnosItem,idInstitucion);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (turnosItems != null) {
					turnosDTO.setTurnosItems(turnosItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return turnosDTO;
	}
	
	@Override
	public UpdateResponseDTO eliminateTurnos(TurnosDTO turnosDTO, HttpServletRequest request) {
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

					for (TurnosItem turnosItem : turnosDTO.getTurnosItems()) {

						
						ScsTurno turno = new ScsTurno();
						turno.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
						turno.setIdinstitucion(idInstitucion);

						turno = scsTurnosExtendsMapper.selectByPrimaryKey(turno);
						
						if(turnosItem.getFechabaja() == null) {
							turno.setFechabaja(new Date());
							turno.setVisibilidad("0");
						}else {
							turno.setFechabaja(null);
							turno.setVisibilidad("1");
						}
						
						turno.setFechamodificacion(new Date());
						turno.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

						response = scsTurnosExtendsMapper.updateByPrimaryKey(turno);

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
	public UpdateResponseDTO updateDatosGenerales(TurnosItem turnosItem, HttpServletRequest request) {
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
						

								LOGGER.info(
										"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
								
								ScsTurnoExample example = new ScsTurnoExample();
								example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()));
								
								ScsTurno scsTurno = new ScsTurno();
								List<ScsTurno> scsTurnoLista = scsTurnosExtendsMapper.selectByExample(example);
								scsTurno = scsTurnoLista.get(0);
								
								scsTurno.setAbreviatura(turnosItem.getAbreviatura());
								scsTurno.setNombre(turnosItem.getNombre());
								scsTurno.setCodigoext(turnosItem.getCodigoext());
								scsTurno.setIdpartidapresupuestaria(Integer.parseInt(turnosItem.getIdpartidapresupuestaria()));
								scsTurno.setIdgrupofacturacion(Short.parseShort(turnosItem.getIdgrupofacturacion()));
								
								scsTurno.setFechamodificacion(new Date());
//								scsPartidaPresupuestaria.setDescripcion(partidasItems.getDescripcion());
//								scsPartidaPresupuestaria.setFechabaja(partidasItems.getFechabaja());
//								scsPartidaPresupuestaria.setFechamodificacion(partidasItems.getFechamodificacion());
//								scsPartidaPresupuestaria.setImportepartida(importe);
//								scsPartidaPresupuestaria.setNombrepartida(partidasItems.getNombrepartida());
								scsTurno.setUsumodificacion(usuarios.get(0).getIdusuario());
								LOGGER.info(
										"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");


								
									LOGGER.info(
											"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

									response = scsTurnosExtendsMapper.updateByExample(scsTurno, example);

									LOGGER.info(
											"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
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
}
