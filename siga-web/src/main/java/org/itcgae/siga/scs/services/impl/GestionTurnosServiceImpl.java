package org.itcgae.siga.scs.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.ModulosItem;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTO.scs.TurnosDTO;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsPartidapresupuestaria;
import org.itcgae.siga.db.entities.ScsPartidapresupuestariaExample;
import org.itcgae.siga.db.entities.ScsPretensionesproced;
import org.itcgae.siga.db.entities.ScsPretensionesprocedExample;
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsProcedimientosExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
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
	private ScsOrdenacioncolasMapper scsOrdenacioncolasMapper;

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
								scsTurno.setIdarea(Short.parseShort(turnosItem.getIdarea()));
								scsTurno.setIdmateria(Short.parseShort(turnosItem.getIdmateria()));
								scsTurno.setIdjurisdiccion(Short.parseShort(turnosItem.getIdjurisdiccion()));
								scsTurno.setIdzona(Short.parseShort(turnosItem.getIdzona()));
								scsTurno.setIdsubzona(Short.parseShort(turnosItem.getIdsubzona()));
								scsTurno.setIdtipoturno(Short.parseShort(turnosItem.getIdtipoturno()));
								scsTurno.setFechamodificacion(new Date());
								scsTurno.setDescripcion(turnosItem.getDescripcion());
								scsTurno.setRequisitos(turnosItem.getRequisitos());
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
	
	@Override
	public InsertResponseDTO createTurnos(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("createModules() ->  Entrada al servicio para insertar modulos");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		Integer idTurnoNuevo = 0;
		Integer idOrdenacionNuevo = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		

			if (null != usuarios && usuarios.size() > 0) {
				try {
						ScsTurnoExample ejemplo = new ScsTurnoExample();
						ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAbreviaturaEqualTo(turnosItem.getAbreviatura());


						
						List<ScsTurno> turnosExistentes = scsTurnosExtendsMapper.selectByExample(ejemplo);
	
						if((turnosExistentes != null && turnosExistentes.size() > 0)) {
							response = 0;
							error.setCode(400);
							error.setDescription("menu.justiciaGratuita.maestros.nombreCodigoExistente");
						}else {
							ScsTurno turno = new ScsTurno();
							ScsOrdenacioncolas ordenacion = new ScsOrdenacioncolas();
							NewIdDTO idTurno = scsTurnosExtendsMapper.getIdTurno(idInstitucion);
							NewIdDTO idOrdenacion = scsTurnosExtendsMapper.getIdOrdenacion(idInstitucion);
							
							if (idTurno == null) {
								idTurnoNuevo = 1;
								turno.setIdturno(1);
							} else {
								idOrdenacionNuevo = (Integer.parseInt(idOrdenacion.getNewId()) + 1);
								
								ordenacion.setIdordenacioncolas(Integer.parseInt(idOrdenacionNuevo.toString()));
								ordenacion.setAlfabeticoapellidos(Short.parseShort("4"));
								ordenacion.setFechamodificacion(new Date());
								ordenacion.setFechanacimiento(Short.parseShort("0"));
								ordenacion.setAntiguedadcola(Short.parseShort("0"));
								ordenacion.setNumerocolegiado(Short.parseShort("0"));
								ordenacion.setUsumodificacion(usuarios.get(0).getIdusuario());
								response = scsOrdenacioncolasMapper.insert(ordenacion);
							
								turno.setIdordenacioncolas(Integer.parseInt(idOrdenacionNuevo.toString()));
								idTurnoNuevo = (Integer.parseInt(idTurno.getNewId()) + 1);
								turno.setIdturno(Integer.parseInt(idTurnoNuevo.toString()));
							}

							turno.setIdinstitucion(idInstitucion);
							turno.setAbreviatura(turnosItem.getAbreviatura());
							turno.setNombre(turnosItem.getNombre());
							turno.setCodigoext(turnosItem.getCodigoext());
							turno.setIdpartidapresupuestaria(Integer.parseInt(turnosItem.getIdpartidapresupuestaria()));
							turno.setIdgrupofacturacion(Short.parseShort(turnosItem.getIdgrupofacturacion()));
							turno.setIdarea(Short.parseShort(turnosItem.getIdarea()));
							turno.setIdmateria(Short.parseShort(turnosItem.getIdmateria()));
							turno.setIdjurisdiccion(Short.parseShort(turnosItem.getIdjurisdiccion()));
							turno.setIdzona(Short.parseShort(turnosItem.getIdzona()));
							turno.setIdsubzona(Short.parseShort(turnosItem.getIdsubzona()));
							turno.setIdtipoturno(Short.parseShort(turnosItem.getIdtipoturno()));
							turno.setFechamodificacion(new Date());
							turno.setDescripcion(turnosItem.getDescripcion());
							turno.setRequisitos(turnosItem.getRequisitos());
							turno.setUsumodificacion(usuarios.get(0).getIdusuario());
							turno.setGuardias(Short.parseShort("0"));
							turno.setVisiblemovil(Short.parseShort("1"));
							turno.setValidarjustificaciones("S");
							turno.setActivarretriccionacredit("N");
							turno.setLetradoactuaciones("N");
							turno.setLetradoasistencias("S");
							turno.setDesignadirecta("N");
							turno.setValidarinscripciones("S");
							turno.setVisibilidad("1");
							
							
							LOGGER.info(
									"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

							response = scsTurnosExtendsMapper.insert(turno);

							LOGGER.info(
									"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

							
//							Combo multiselect de Procedimientos
//							if(response == 1)
//							if(modulosItem.getProcedimientos() != null && modulosItem.getProcedimientos().length()>0) {
//								
//								String[] lista = modulosItem.getProcedimientos().split(",");
//								
//								for(int i = 0; i < lista.length ; i++) {
//									ScsPretensionesproced pretension = new ScsPretensionesproced();
//									pretension.setIdpretension(Short.parseShort(lista[i]));
//									pretension.setFechamodificacion(new Date());
//									pretension.setUsumodificacion(usuarios.get(0).getIdusuario());
//									pretension.setIdprocedimiento(modulo.getIdprocedimiento());
//									pretension.setIdinstitucion(idInstitucion);
//									LOGGER.info(
//											"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para actualizar los procedimientos");
//									scsPretensionesprocedMapper.insert(pretension);
//									LOGGER.info(
//											"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para actualizar los procedimientos");
//								}
//							
//								
//							}else {
////								ELIMINAR TODOS
//								ScsPretensionesprocedExample pretenprocedExample = new ScsPretensionesprocedExample();
//								pretenprocedExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdprocedimientoEqualTo(modulo.getIdprocedimiento());
//								List<ScsPretensionesproced> pretensionesAnteriores = scsPretensionesprocedMapper.selectByExample(pretenprocedExample);
//								
//								for(int i = 0; i < pretensionesAnteriores.size() ; i++) {
//									scsPretensionesprocedMapper.deleteByPrimaryKey(pretensionesAnteriores.get(i));
//								}
//							}
							
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
			if(error.getDescription() == null) {
							error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(String.valueOf(idTurnoNuevo));
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("updateModules() -> Salida del servicio para insertar modulos");

		return insertResponseDTO;
	}
}