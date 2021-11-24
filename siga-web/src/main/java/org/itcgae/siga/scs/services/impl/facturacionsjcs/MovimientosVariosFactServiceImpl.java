package org.itcgae.siga.scs.services.impl.facturacionsjcs;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FcsFactGrupofactHito;
import org.itcgae.siga.db.entities.FcsMovimientosvarios;
import org.itcgae.siga.db.entities.FcsMovimientosvariosKey;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsMovimientosvariosExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IMovimientosVariosFactServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;

@Service
public class MovimientosVariosFactServiceImpl implements IMovimientosVariosFactServices {
	
	// Asuntos de Origen de Movimientos Varios - Recursos
		public static final String ORIGEN_MV_RECURSO_ACTUACIONDESIGNA = "movimientosVarios.ActuacionDesigna.titulo";
		public static final String ORIGEN_MV_RECURSO_ACTUACIONASISTENCIA = "movimientosVarios.ActuacionAsistencias.titulo";
		public static final String ORIGEN_MV_RECURSO_ASISTENCIA = "movimientosVarios.asistencia.titulo";
		public static final String ORIGEN_MV_RECURSO_CABECERAGUARDIA = "movimientosVarios.guardia.titulo";
		
	 private Logger LOGGER = Logger.getLogger(FacturacionSJCSServicesImpl.class);

	    //private static Boolean alguienEjecutando = Boolean.FALSE;

	    @Autowired
	    private FcsMovimientosvariosExtendsMapper fcsMovimientosvariosExtendsMapper;
	    
	    @Autowired
	    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	    
	    @Autowired
	    private  FcsMovimientosvariosMapper fcsMovimientosVariosMapper;
	    
	    
	    public MovimientosVariosFacturacionDTO buscarMovimientosVarios(MovimientosVariosFacturacionItem facturacionItem, HttpServletRequest request) {
	        String token = request.getHeader("Authorization");
	        String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        MovimientosVariosFacturacionDTO movimientos = new MovimientosVariosFacturacionDTO();
	        //List<GenParametros> tamMax = null;
	       // Integer tamMaximo = null;
	        Error error = new Error();
	        

	        try {

	            if (null != idInstitucion) {
	                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	                exampleUsuarios.createCriteria().andNifEqualTo(dni)
	                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	                LOGGER.info(
	                        "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	                LOGGER.info(
	                        "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	                if (null != usuarios && usuarios.size() > 0) {
	                    AdmUsuarios usuario = usuarios.get(0);
	                    usuario.setIdinstitucion(idInstitucion);
	               
	                    LOGGER.info(
	                            "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / fcsMovimientosvariosExtendsMapper.buscarMovimientosVarios() -> Entrada a fcsMovimientosvariosExtendsMapper para obtener las facturaciones");
	                    List<MovimientosVariosFacturacionItem> movimientosItems = fcsMovimientosvariosExtendsMapper
	                            .buscarMovimientosVarios(facturacionItem, idInstitucion.toString());
               

	                    movimientos.setFacturacionItem(movimientosItems);
	                    LOGGER.info(
	                            "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / fcsMovimientosvariosExtendsMapper.buscarMovimientosVarios() -> Salida a fcsMovimientosvariosExtendsMapper para obtener las facturaciones");
	                } else {
	                    LOGGER.warn(
	                            "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                    + dni + " e idInstitucion = " + idInstitucion);
	                }
	            } else {
	                LOGGER.warn("MovimientosVariosFactServiceImpl.buscarMovimientosVarios() -> idInstitucion del token nula");
	            }

	        } catch (Exception e) {
	            LOGGER.error(
	                    "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() -> Se ha producido un error al buscar las facturaciones",
	                    e);
	            error.setCode(500);
	            error.setDescription("general.mensaje.error.bbdd");
	        }

	        movimientos.setError(error);
	        LOGGER.info(
	                "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() -> Salida del servicio para obtener las facturaciones");

	        return movimientos;
	    }
	    
	    @Transactional
	    public DeleteResponseDTO deleteMovimientosVarios(List<MovimientosVariosFacturacionItem>  movimientos, HttpServletRequest request) throws Exception {
	    	
	    	LOGGER.info("<MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	boolean borradoCorrecto = false;
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        DeleteResponseDTO deleteResponse = new DeleteResponseDTO();
	        Error error = new Error();
	        int ko=0;
	        int fin=0;
	        int response = 0;
	        List<Integer> responses = new ArrayList<Integer>();

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.info(
	                    "MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.info(
	                    "MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {

	                    for (MovimientosVariosFacturacionItem mov : movimientos) {
	                                             
		
							// obteniendo el origen del movimiento	
	                    	String funcion="f_Siga_Asuntoorigen_MV";
							String origen = fcsMovimientosvariosExtendsMapper.ejecutarFuncionMovVario(idInstitucion.toString(),mov.getIdMovimiento(),funcion);
							
							
					
							if (origen != null && !"".equalsIgnoreCase(origen)) {
			
								if (ORIGEN_MV_RECURSO_ACTUACIONDESIGNA.equalsIgnoreCase(origen)) {
									String nombreTabla = "SCS_ACTUACIONDESIGNA";
									List<String> idInst= fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
									try{
										if(idInst != null) {
											
											if(idInst.size() > 1) {
												throw new Exception ("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
											}else {
												
											 fin=fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
												
												if(fin==0) {
													throw new Exception ("Error al ejecutar el 'actualizaMovimientosVarios' en B.D.");
												}
											}
											
										}
									} catch (Exception e) {
										throw new Exception (e.getMessage());
									}
			
								} else if (ORIGEN_MV_RECURSO_ACTUACIONASISTENCIA.equalsIgnoreCase(origen)) {
			
									String nombreTabla = "SCS_ACTUACIONASISTENCIA";
									List<String> idInst= fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
									try{
										if(idInst != null) {
											
											if(idInst.size() > 1) {
												throw new Exception ("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
											}else {
												
												 fin=fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
												
												if(fin==0) {
													throw new Exception ("Error al ejecutar el 'actualizaMovimientosVarios' en B.D.");
												}
											}
											
										}
									} catch (Exception e) {
										throw new Exception (e.getMessage());
									}
			
								} else if (ORIGEN_MV_RECURSO_ASISTENCIA.equalsIgnoreCase(origen)) {
			
												
									String nombreTabla = "SCS_ASISTENCIA";
									List<String> idInst= fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
									try{
										if(idInst != null) {
											
											if(idInst.size() > 1) {
												throw new Exception ("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
											}else {
												
												  fin=fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
												
												if(fin==0) {
													throw new Exception ("Error al ejecutar el 'actualizaMovimientosVarios' en B.D.");
												}
											}
											
										}
									} catch (Exception e) {
										throw new Exception (e.getMessage());
									}
			
								} else if (ORIGEN_MV_RECURSO_CABECERAGUARDIA.equalsIgnoreCase(origen)) {
			
									String nombreTabla = "SCS_CABECERAGUARDIAS";
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion() -> Entrada para comprobar si exiten actuaciones para ese movimiento");
									List<String> idInst= fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion -> Salida con los datos recogidos.");
									try{
										if(idInst != null) {
											
											if(idInst.size() > 1) {
												throw new Exception ("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
											}else {
					                        	LOGGER.debug("MovimientosVariosFactServiceImpl.quitaMovimientoVarioAsociado() ->Entrada para actualizar el idMovimiento a null de una tabla relacionada con FCS_MOVIMIENTOSVARIOS antes de eliminarlo");
												 fin=fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla,idInstitucion.toString(),mov.getIdMovimiento());
						                        LOGGER.debug("MovimientosVariosFactServiceImpl.quitaMovimientoVarioAsociado() ->Salida con los datos actualizados");

												if(fin==0) {
													throw new Exception ("Error al ejecutar el 'actualizaMovimientosVarios' en B.D.");
												}
											}
											
										}
									} catch (Exception e) {
										throw new Exception (e.getMessage());
									}
									
									
								}//EN DE LOS ELSE IF
							}

	                        if(fin==1) {
	                        	LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Entrada para eliminar un movimiento vario");
	                        	FcsMovimientosvariosKey movimientoMapper = new FcsMovimientosvariosKey();
	                        	movimientoMapper.setIdinstitucion(idInstitucion);
	                        	movimientoMapper.setIdmovimiento(Long.parseLong(mov.getIdMovimiento()));
	                        	response = fcsMovimientosVariosMapper.deleteByPrimaryKey(movimientoMapper);
	                        	//response=fcsMovimientosvariosExtendsMapper.delete(mov.getIdInstitucion().toString(), mov.getIdMovimiento());
	                        	LOGGER.debug("MovimientosVariosFactServiceImpl.delete() -> Salida del movimiento eliminado.");
	                        }
	                        
	                        responses.add(response);
	                        
	                    }
	                    
	                    int tamañoLista=responses.size();
	                    int contFallidos=0;
	                    
	                    for(Integer resp : responses) {
	                    	if(resp == 0) {
	                    		contFallidos++;
	                    	}
	                    }
	                    
	                    error.setDescription("Se han eliminado "+(tamañoLista-contFallidos)+" de "+tamañoLista+" movimientos varios");

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    deleteResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (responses.contains(0) && error.getDescription() == null) {
	            error.setCode(400);
	            deleteResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            deleteResponse.setStatus(SigaConstants.OK);
	        }

	        deleteResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> Salida");

	        return deleteResponse;
	    	
	    }
	    
	    
	    @Transactional
	    public InsertResponseDTO saveDatosGenMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {
	    	
	    	LOGGER.info("<MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        InsertResponseDTO insertResponse = new InsertResponseDTO();
	        Error error = new Error();
	        int response = 0;
	        

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {                   
	                	
	                	NewIdDTO idMovimiento = fcsMovimientosvariosExtendsMapper
	                			.selectMaxIdMovimientoByIdInstitucion(idInstitucion.toString());

	                    Long newid = Long.parseLong(idMovimiento.getNewId());
	                    
							if (movimiento != null) {	
								 LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios() -> Insertar nuevo cliente en el movimiento");

								 FcsMovimientosvarios record = new FcsMovimientosvarios();
				                    record.setFechamodificacion(new Date());
				                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
				                    record.setIdinstitucion(idInstitucion);

				                    record.setIdmovimiento( newid + 1);
				                    record.setIdpersona(Long.parseLong(movimiento.getIdPersona()));
				                    record.setFechaalta(new Date());
				                    
				                    record.setDescripcion(movimiento.getDescripcion());
				                    record.setCantidad(new BigDecimal(movimiento.getCantidad()));
				                    record.setContabilizado(" ");
				                    
				                    //record.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));
				                    
				                    
				                    record.setMotivo(movimiento.getMotivo());
				                    
				                    if(movimiento.getTipo() != null) {
				                    	record.setIdtipomovimiento(Short.parseShort(movimiento.getTipo()));
				                    }
				                    
				                    //falta certificación 
				                    response = fcsMovimientosVariosMapper.insertSelective(record);
				                    
								//response=fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios(movimiento, idInstitucion.toString());													
										

								if(response==1) {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios() -> Salida con el movimiento ya insertado");
								}else {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios() -> Salida sin haber insertado el movimiento");
								}
							}

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    insertResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (response==0 && error.getDescription() == null) {
	            error.setCode(400);
	            insertResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            insertResponse.setStatus(SigaConstants.OK);
	        }

	        insertResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> Salida");

	        return insertResponse;
	    }

	    @Transactional
		public UpdateResponseDTO updateDatosGenMovimientosVarios(MovimientosVariosFacturacionItem movimiento,
				HttpServletRequest request) {
			LOGGER.info("<MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	boolean insertadoCorrectamente = false;
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
	        Error error = new Error();
	        int ko=0;
	        int response = 0;
	        

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {                                           
					
							if (movimiento != null) {	
		
							 LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios() -> Modificar el cliente en el movimiento");
							 FcsMovimientosvarios movimientoItem= new FcsMovimientosvarios();

			                    movimientoItem.setFechamodificacion(new Date());

			                    movimientoItem.setUsumodificacion(usuarios.get(0).getIdusuario());



			                    movimientoItem.setIdinstitucion(idInstitucion);

			                    movimientoItem.setIdpersona(Long.parseLong(movimiento.getIdPersona()));

			                    movimientoItem.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));
		                
			                    
			                    movimientoItem.setDescripcion(movimiento.getDescripcion());
			                    movimientoItem.setCantidad(new BigDecimal(movimiento.getCantidad()));
			                    movimientoItem.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));
			                    movimientoItem.setMotivo(movimiento.getMotivo());
			                    
			                    movimientoItem.setIdtipomovimiento(Short.parseShort(movimiento.getTipo()));
			                    
			                    
			                    //falta la certificación
			                    response = fcsMovimientosVariosMapper.updateByPrimaryKeySelective(movimientoItem);
								
			                    //response=fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios(movimiento, idInstitucion.toString());	
								
								if(response==1) {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios() -> Salida con el movimiento ya modificados");
								}else {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios() -> Salida sin haber modificado el movimiento");
								}
							}

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    updateResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (response==0 && error.getDescription() == null) {
	            error.setCode(400);
	            updateResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            updateResponse.setStatus(SigaConstants.OK);
	        }

	        updateResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> Salida");
	 
	        return updateResponse;
		}
	    
	    @Transactional
	    public InsertResponseDTO saveCriteriosMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {
	    	
	    	LOGGER.info("<MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        InsertResponseDTO insertResponse = new InsertResponseDTO();
	        Error error = new Error();
	        int response = 0;
	        

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {             
	                	
	                	NewIdDTO idMovimiento = fcsMovimientosvariosExtendsMapper
	                			.selectMaxIdMovimientoByIdInstitucion(idInstitucion.toString());

	                    Long newid = Long.parseLong(idMovimiento.getNewId());
					
							if (movimiento != null) {	
								 LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios() -> Insertar nuevo cliente en el movimiento");

								 FcsMovimientosvarios record = new FcsMovimientosvarios();
				                    record.setFechamodificacion(new Date());
				                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
				                    record.setIdinstitucion(idInstitucion);

				                    record.setIdmovimiento(newid + 1);
				                    record.setIdpersona(Long.parseLong(movimiento.getIdPersona()));
				                    record.setFechaalta(new Date());
				                    record.setContabilizado(" ");
				                    record.setDescripcion(" ");
				                    record.setCantidad(new BigDecimal("0.0"));
				                    
				                    
				                    if(movimiento.getIdGrupoFacturacion() != null) {
				                    	record.setIdgrupofacturacion(Short.parseShort(movimiento.getIdGrupoFacturacion()));
				                    }
				                    
				                    if(movimiento.getIdFacturacion() != null) {
					                    record.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));

				                    }
				                    
				                    if(movimiento.getIdConcepto() != null) {
				                    	record.setIdhitogeneral(Short.parseShort(movimiento.getIdConcepto()));
				                    }
				                    
				                    if(movimiento.getIdPartidaPresupuestaria() != null) {
				                    	 record.setIdpartidapresupuestaria(Integer.parseInt(movimiento.getIdPartidaPresupuestaria()));
				                    }
				                   

				                    response = fcsMovimientosVariosMapper.insertSelective(record);
				                    
								//response=fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios(movimiento, idInstitucion.toString());													
										

								if(response==1) {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios() -> Salida con el movimiento ya insertado");
								}else {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios() -> Salida sin haber insertado el movimiento");
								}
							}

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    insertResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (response==0 && error.getDescription() == null) {
	            error.setCode(400);
	            insertResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            insertResponse.setStatus(SigaConstants.OK);
	        }

	        insertResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> Salida");

	        return insertResponse;
	    }

	    @Transactional
		public UpdateResponseDTO updateCriteriosMovimientosVarios(MovimientosVariosFacturacionItem movimiento,
				HttpServletRequest request) {
			LOGGER.info("<MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	boolean insertadoCorrectamente = false;
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
	        Error error = new Error();
	        int ko=0;
	        int response = 0;
	        

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {                                           
					
							if (movimiento != null) {	
		
							 LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios() -> Modificar el cliente en el movimiento");
							 FcsMovimientosvarios movimientoItem= new FcsMovimientosvarios();

							 	movimientoItem.setFechamodificacion(new Date());
							 	movimientoItem.setUsumodificacion(usuarios.get(0).getIdusuario());
							 	movimientoItem.setIdinstitucion(idInstitucion);

							 	movimientoItem.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));
							 	movimientoItem.setIdpersona(Long.parseLong(movimiento.getIdPersona()));
			                    
							 	movimientoItem.setIdgrupofacturacion(Short.parseShort(movimiento.getIdGrupoFacturacion()));
							 	movimientoItem.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));
							 	movimientoItem.setIdhitogeneral(Short.parseShort(movimiento.getIdConcepto()));
			                    movimientoItem.setIdpartidapresupuestaria(Integer.parseInt(movimiento.getIdPartidaPresupuestaria()));
			              
		                
			                    response = fcsMovimientosVariosMapper.updateByPrimaryKeySelective(movimientoItem);
								
			                    //response=fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios(movimiento, idInstitucion.toString());	
								
								if(response==1) {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios() -> Salida con el movimiento ya modificados");
								}else {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios() -> Salida sin haber modificado el movimiento");
								}
							}

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    updateResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (response==0 && error.getDescription() == null) {
	            error.setCode(400);
	            updateResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            updateResponse.setStatus(SigaConstants.OK);
	        }

	        updateResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> Salida");
	 
	        return updateResponse;
		}
	    
		
		@Override
	    public MovimientosVariosFacturacionDTO getListadoPagos(MovimientosVariosFacturacionItem facturacionItem, HttpServletRequest request) {
	        String token = request.getHeader("Authorization");
	        String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        MovimientosVariosFacturacionDTO movimientos = new MovimientosVariosFacturacionDTO();
	        Error error = new Error();
	        

	        try {

	            if (null != idInstitucion) {
	                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	                exampleUsuarios.createCriteria().andNifEqualTo(dni)
	                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	                LOGGER.info(
	                        "MovimientosVariosFactServiceImpl.getListadoPagos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	                LOGGER.info(
	                        "MovimientosVariosFactServiceImpl.getListadoPagos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	                if (null != usuarios && usuarios.size() > 0) {
	                    AdmUsuarios usuario = usuarios.get(0);
	                    usuario.setIdinstitucion(idInstitucion);
	               
	                    LOGGER.info(
	                            "MovimientosVariosFactServiceImpl.getListadoPagos() / fcsMovimientosvariosExtendsMapper.getListadoPagos() -> Entrada a fcsMovimientosvariosExtendsMapper para obtener las facturaciones");
	                    List<MovimientosVariosFacturacionItem> pagos = fcsMovimientosvariosExtendsMapper
	                            .getListadoPagos(facturacionItem, idInstitucion.toString());
               

	                    movimientos.setFacturacionItem(pagos);
	                    LOGGER.info(
	                            "MovimientosVariosFactServiceImpl.getListadoPagos() / fcsMovimientosvariosExtendsMapper.getListadoPagos() -> Salida a fcsMovimientosvariosExtendsMapper para obtener las facturaciones");
	                } else {
	                    LOGGER.warn(
	                            "MovimientosVariosFactServiceImpl.getListadoPagos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                    + dni + " e idInstitucion = " + idInstitucion);
	                }
	            } else {
	                LOGGER.warn("MovimientosVariosFactServiceImpl.getListadoPagos() -> idInstitucion del token nula");
	            }

	        } catch (Exception e) {
	            LOGGER.error(
	                    "MovimientosVariosFactServiceImpl.getListadoPagos() -> Se ha producido un error al buscar las facturaciones",
	                    e);
	            error.setCode(500);
	            error.setDescription("general.mensaje.error.bbdd");
	        }

	        movimientos.setError(error);
	        LOGGER.info(
	                "MovimientosVariosFactServiceImpl.getListadoPagos() -> Salida del servicio para obtener las facturaciones");

	        return movimientos;
	    }
		
		@Transactional
	    public InsertResponseDTO saveClienteMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {
	    	
	    	LOGGER.info("<MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        InsertResponseDTO insertResponse = new InsertResponseDTO();
	        Error error = new Error();
	        int response = 0;
	        

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {        
	                	
	                	
	                	NewIdDTO idMovimiento = fcsMovimientosvariosExtendsMapper
	                			.selectMaxIdMovimientoByIdInstitucion(idInstitucion.toString());

	                    Long newid = Long.parseLong(idMovimiento.getNewId());
					
							if (movimiento != null) {	
								 LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios() -> Insertar nuevo cliente en el movimiento");

								 String idPersona = fcsMovimientosvariosExtendsMapper.selectIdPersonaByNif(movimiento.getNif());
								 
								 FcsMovimientosvarios record = new FcsMovimientosvarios();
				                    record.setFechamodificacion(new Date());
				                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
				                    record.setIdinstitucion(idInstitucion);

				                    record.setIdmovimiento(newid + 1);
				                    record.setIdpersona(Long.parseLong(idPersona));
				                    record.setFechaalta(new Date());
				                    
				                    record.setDescripcion(" ");
				                    record.setCantidad(new BigDecimal("0.0"));
				                    record.setContabilizado(" ");
				                    

				                    response = fcsMovimientosVariosMapper.insertSelective(record);
				                    
								//response=fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios(movimiento, idInstitucion.toString());													
										

								if(response==1) {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios() -> Salida con el movimiento ya insertado");
								}else {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios() -> Salida sin haber insertado el movimiento");
								}
							}

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    insertResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (response==0 && error.getDescription() == null) {
	            error.setCode(400);
	            insertResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            insertResponse.setStatus(SigaConstants.OK);
	        }

	        insertResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> Salida");

	        return insertResponse;
	    }

	    @Transactional
		public UpdateResponseDTO updateClienteMovimientosVarios(MovimientosVariosFacturacionItem movimiento,
				HttpServletRequest request) {
			LOGGER.info("<MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> Entrada");
	    	
	    	String token = request.getHeader("Authorization");
	    	boolean insertadoCorrectamente = false;
	    	String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
	        Error error = new Error();
	        int ko=0;
	        int response = 0;
	        

	        if (null != idInstitucion) {
	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	            LOGGER.debug(
	                    "MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (null != usuarios && usuarios.size() > 0) {
	                AdmUsuarios usuario = usuarios.get(0);
	                usuario.setIdinstitucion(idInstitucion);

	                try {                                           
					
							if (movimiento != null) {	
		
							 LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateClienteMovimientosVarios() -> Modificar el cliente en el movimiento");
							 FcsMovimientosvarios movimientoItem= new FcsMovimientosvarios();

			                    movimientoItem.setFechamodificacion(new Date());

			                    movimientoItem.setUsumodificacion(usuarios.get(0).getIdusuario());



			                    movimientoItem.setIdinstitucion(idInstitucion);

			                    movimientoItem.setIdpersona(Long.parseLong(movimiento.getIdPersona()));

			                    movimientoItem.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));
		                
			                    response = fcsMovimientosVariosMapper.updateByPrimaryKeySelective(movimientoItem);
								
			                    //response=fcsMovimientosvariosExtendsMapper.updateClienteMovimientosVarios(movimiento, idInstitucion.toString());	
								
								if(response==1) {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateClienteMovimientosVarios() -> Salida con el movimiento ya modificados");
								}else {
									LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateClienteMovimientosVarios() -> Salida sin haber modificado el movimiento");
								}
							}

	                } catch (Exception e) {
	                    LOGGER.error(
	                            "MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> ERROR al eliminar los conceptos de la facturacion.",
	                            e);
	                    error.setCode(400);
	                    error.setDescription("general.mensaje.error.bbdd");
	                    updateResponse.setStatus(SigaConstants.KO);
	                }
	            } else {
	                LOGGER.error(
	                        "MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
	                                + dni + " e idInstitucion = " + idInstitucion);
	            }
	        } else {
	            LOGGER.error("MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> idInstitucion del token nula");
	        }

	        if (response==0 && error.getDescription() == null) {
	            error.setCode(400);
	            updateResponse.setStatus(SigaConstants.KO);
	        } else if (error.getCode() == null) {
	            error.setCode(200);
	            updateResponse.setStatus(SigaConstants.OK);
	        }

	        updateResponse.setError(error);

	        LOGGER.debug("MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> Salida");
	 
	        return updateResponse;
		}

	    
}
