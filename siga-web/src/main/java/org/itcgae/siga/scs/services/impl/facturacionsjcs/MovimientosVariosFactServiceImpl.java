package org.itcgae.siga.scs.services.impl.facturacionsjcs;


import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FcsMovimientosvarios;
import org.itcgae.siga.db.entities.FcsMovimientosvariosKey;
import org.itcgae.siga.db.entities.FcsMvariosCertificaciones;
import org.itcgae.siga.db.entities.FcsMvariosCertificacionesKey;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosMapper;
import org.itcgae.siga.db.mappers.FcsMvariosCertificacionesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsMovimientosvariosExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IMovimientosVariosFactServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private FcsMovimientosvariosMapper fcsMovimientosVariosMapper;
    
    @Autowired 
    private FcsMvariosCertificacionesMapper fcsMvariosCertificacionesMapper;


    public MovimientosVariosFacturacionDTO buscarMovimientosVarios(MovimientosVariosFacturacionItem facturacionItem, HttpServletRequest request) {
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
                LOGGER.debug(
                        "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.debug(
                        "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && usuarios.size() > 0) {
                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);

                    LOGGER.debug(
                            "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / fcsMovimientosvariosExtendsMapper.buscarMovimientosVarios() -> Entrada a fcsMovimientosvariosExtendsMapper para obtener los movimientos varios");
                    List<MovimientosVariosFacturacionItem> movimientosItems = fcsMovimientosvariosExtendsMapper
                            .buscarMovimientosVarios(facturacionItem, idInstitucion.toString());


                    movimientos.setFacturacionItem(movimientosItems);
                    LOGGER.debug(
                            "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / fcsMovimientosvariosExtendsMapper.buscarMovimientosVarios() -> Salida a fcsMovimientosvariosExtendsMapper para obtener los movimientos varios");
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
                    "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() -> Se ha producido un error al buscar los movimientos varios",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        movimientos.setError(error);
        LOGGER.debug(
                "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() -> Salida del servicio para obtener los movimientos varios");

        return movimientos;
    }

    @Transactional
    public DeleteResponseDTO deleteMovimientosVarios(List<MovimientosVariosFacturacionItem> movimientos, HttpServletRequest request) throws Exception {

        LOGGER.debug("<MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> Entrada");

        String token = request.getHeader("Authorization");
        boolean borradoCorrecto = false;
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponse = new DeleteResponseDTO();
        Error error = new Error();
        int ko = 0;
        int fin = 0;
        int response = 0;
        List<Integer> responses = new ArrayList<Integer>();

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.deleteMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                try {

                    for (MovimientosVariosFacturacionItem mov : movimientos) {


                        // obteniendo el origen del movimiento
                        String funcion = "f_Siga_Asuntoorigen_MV";
                        String origen = fcsMovimientosvariosExtendsMapper.ejecutarFuncionMovVario(idInstitucion.toString(), mov.getIdMovimiento(), funcion);


                        if (origen != null && !"".equalsIgnoreCase(origen)) {

                            if (ORIGEN_MV_RECURSO_ACTUACIONDESIGNA.equalsIgnoreCase(origen)) {
                                String nombreTabla = "SCS_ACTUACIONDESIGNA";
                                List<String> idInst = fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());
                                try {
                                    if (idInst != null) {

                                        if (idInst.size() > 1) {
                                            throw new Exception("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
                                        } else {

                                            fin = fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());

                                            if (fin == 0) {
                                                throw new Exception("Error al ejecutar el 'quitaMovimientoVarioAsociado' en B.D. donde actualiza el idmovimiento de la actuación asociada a null para eliminar luego ese movimiento");
                                            }
                                        }

                                    }
                                } catch (Exception e) {
                                    throw new Exception(e.getMessage());
                                }

                            } else if (ORIGEN_MV_RECURSO_ACTUACIONASISTENCIA.equalsIgnoreCase(origen)) {

                                String nombreTabla = "SCS_ACTUACIONASISTENCIA";
                                List<String> idInst = fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());
                                try {
                                    if (idInst != null) {

                                        if (idInst.size() > 1) {
                                            throw new Exception("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
                                        } else {

                                            fin = fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());

                                            if (fin == 0) {
                                                throw new Exception("Error al ejecutar el 'quitaMovimientoVarioAsociado' en B.D. donde actualiza el idmovimiento de la actuación asociada a null para eliminar luego ese movimiento");
                                            }
                                        }

                                    }
                                } catch (Exception e) {
                                    throw new Exception(e.getMessage());
                                }

                            } else if (ORIGEN_MV_RECURSO_ASISTENCIA.equalsIgnoreCase(origen)) {


                                String nombreTabla = "SCS_ASISTENCIA";
                                List<String> idInst = fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());
                                try {
                                    if (idInst != null) {

                                        if (idInst.size() > 1) {
                                            throw new Exception("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
                                        } else {

                                            fin = fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());

                                            if (fin == 0) {
                                                throw new Exception("Error al ejecutar el 'quitaMovimientoVarioAsociado' en B.D. donde actualiza el idmovimiento de la actuación asociada a null para eliminar luego ese movimiento");
                                            }
                                        }

                                    }
                                } catch (Exception e) {
                                    throw new Exception(e.getMessage());
                                }

                            } else if (ORIGEN_MV_RECURSO_CABECERAGUARDIA.equalsIgnoreCase(origen)) {

                                String nombreTabla = "SCS_CABECERAGUARDIAS";
                                LOGGER.debug("fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion() -> Entrada para comprobar si exiten actuaciones para ese movimiento");
                                List<String> idInst = fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());
                                LOGGER.debug("fcsMovimientosvariosExtendsMapper.comprobarExistenciaActuacion -> Salida con los datos recogidos.");
                                try {
                                    if (idInst != null) {

                                        if (idInst.size() > 1) {
                                            throw new Exception("Error al intentar borrar un Movimiento Vario: tiene varios asuntos relacionados");
                                        } else {
                                            LOGGER.debug("MovimientosVariosFactServiceImpl.quitaMovimientoVarioAsociado() ->Entrada para actualizar el idMovimiento a null de una tabla relacionada con FCS_MOVIMIENTOSVARIOS antes de eliminarlo");
                                            fin = fcsMovimientosvariosExtendsMapper.quitaMovimientoVarioAsociado(nombreTabla, idInstitucion.toString(), mov.getIdMovimiento());
                                            LOGGER.debug("MovimientosVariosFactServiceImpl.quitaMovimientoVarioAsociado() ->Salida");

                                            if (fin == 0) {
                                                throw new Exception("Error al ejecutar el 'quitaMovimientoVarioAsociado' en B.D. donde actualiza el idmovimiento de la actuación asociada a null para eliminar luego ese movimiento");
                                            }
                                        }

                                    }
                                } catch (Exception e) {
                                    throw new Exception(e.getMessage());
                                }


                            }//EN DE LOS ELSE IF
                        }

                     int cert = 0;
                     
                        if (fin == 1) {
                            LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Entrada para eliminar un movimiento vario");
                            FcsMovimientosvariosKey movimientoMapper = new FcsMovimientosvariosKey();
                            movimientoMapper.setIdinstitucion(idInstitucion);
                            movimientoMapper.setIdmovimiento(Long.parseLong(mov.getIdMovimiento()));
                            response = fcsMovimientosVariosMapper.deleteByPrimaryKey(movimientoMapper);
                            //response=fcsMovimientosvariosExtendsMapper.delete(mov.getIdInstitucion().toString(), mov.getIdMovimiento());
                            LOGGER.debug("MovimientosVariosFactServiceImpl.delete() -> Salida del movimiento eliminado.");
                            
                           if(response == 1) {
                        	   LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Entrada para eliminar la certficación asociada al movimiento");
                            	//borrar la certificación asociada a este movimiento
                            	FcsMvariosCertificacionesKey deleteCert = new FcsMvariosCertificacionesKey();
                            	
                            	deleteCert.setIdcertificacion(Short.parseShort(mov.getCertificacion()));
                            	deleteCert.setIdinstitucion(idInstitucion);
                            	deleteCert.setIdmovimiento(Long.parseLong(mov.getIdMovimiento()));
                            	
                               cert = fcsMvariosCertificacionesMapper.deleteByPrimaryKey(deleteCert);
                               LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Salida para eliminar la certificacion asociada al movimiento vario");
                           }
                        }

                        responses.add(cert);

                    }

                    int tamañoLista = responses.size();
                    int contFallidos = 0;

                    for (Integer resp : responses) {
                        if (resp == 0) {
                            contFallidos++;
                        }
                    }
                    
                 

                    error.setDescription("Se han eliminado " + (tamañoLista - contFallidos) + " de " + tamañoLista + " movimientos varios");

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
    public InsertResponseDTO saveMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {

        LOGGER.debug("<MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> Entrada");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        Error error = new Error();
        int response = 0;
        int responseCert = 0;
        Long newid = Long.valueOf("0");

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                try {

                    NewIdDTO idMovimiento = fcsMovimientosvariosExtendsMapper
                            .selectMaxIdMovimientoByIdInstitucion(idInstitucion.toString());

                     newid = Long.parseLong(idMovimiento.getNewId());

                    if (movimiento != null) {
                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveMovimientosVarios() -> Insertar Datos Generales a un nuevo movimiento");

                        FcsMovimientosvarios record = new FcsMovimientosvarios();
                        record.setFechamodificacion(new Date());
                        record.setUsumodificacion(usuarios.get(0).getIdusuario());
                        record.setIdinstitucion(idInstitucion);

                        record.setIdmovimiento(newid + 1);
                        record.setIdpersona(Long.parseLong(movimiento.getIdPersona()));
                        record.setFechaalta(new Date());

                        record.setDescripcion(movimiento.getDescripcion());
                        record.setCantidad(new BigDecimal(movimiento.getCantidad()));
                        record.setContabilizado(" ");

                        record.setMotivo(movimiento.getMotivo());

                        if (movimiento.getTipo() != null) {
                            record.setIdtipomovimiento(Short.parseShort(movimiento.getTipo()));
                        }
                        
                        if (movimiento.getIdGrupoFacturacion() != null) {
                        	record.setIdgrupofacturacion(Short.parseShort(movimiento.getIdGrupoFacturacion()));
                        }

                        if (movimiento.getIdFacturacion() != null) {
                        	record.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));

                        }

                        if (movimiento.getIdConcepto() != null) {
                        	record.setIdhitogeneral(Short.parseShort(movimiento.getIdConcepto()));
                        }

                        if (movimiento.getIdPartidaPresupuestaria() != null) {
                        	record.setIdpartidapresupuestaria(Integer.parseInt(movimiento.getIdPartidaPresupuestaria()));
                        }

                 
                        
                        response = fcsMovimientosVariosMapper.insertSelective(record);


                        if (response == 1 && movimiento.getCertificacion() != null && movimiento.getCertificacion() != "") {                     
                            
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveMovimientosVarios() -> Salida con los datos ya insertados");
                            
                            FcsMvariosCertificaciones insertCert = new FcsMvariosCertificaciones();
                            insertCert.setIdmovimiento(newid + 1);
                            insertCert.setFechamodificacion(new Date());
                            insertCert.setIdinstitucion(idInstitucion);
                            insertCert.setUsumodificacion(usuarios.get(0).getIdusuario());
                            insertCert.setIdcertificacion(Short.parseShort(movimiento.getCertificacion()));
                            
                            responseCert = fcsMvariosCertificacionesMapper.insert(insertCert);
                            
                        } else if(response == 0 || responseCert == 0){
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveMovimientosVarios() -> Salida sin haber insertado los datos");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> ERROR al insertar los datos generales en el nuevo movimiento.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    insertResponse.setStatus(SigaConstants.KO);
                }
            } else {
                LOGGER.error(
                        "MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.error("MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> idInstitucion del token nula");
        }

        if (response == 0 && responseCert == 0 && error.getDescription() == null) {
            error.setCode(400);
            insertResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            insertResponse.setId(String.valueOf(newid + 1));
            insertResponse.setStatus(SigaConstants.OK);
        }

        insertResponse.setError(error);

        LOGGER.debug("MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> Salida");

        return insertResponse;
    }

    @Transactional
    public UpdateResponseDTO updateMovimientosVarios(MovimientosVariosFacturacionItem movimiento,
                                                             HttpServletRequest request) {
        LOGGER.debug("<MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> Entrada");

        String token = request.getHeader("Authorization");
        boolean insertadoCorrectamente = false;
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
        Error error = new Error();
        int ko = 0;
        int response = 0;
        int responseCert = 0;
        int responseCert2 = 0;


        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
	
                try {

                    if (movimiento != null) {

                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateMovimientosVarios() -> Modificar los datos de un movimiento");
                        FcsMovimientosvarios movimientoItem = new FcsMovimientosvarios();

                        movimientoItem.setFechamodificacion(new Date());

                        movimientoItem.setUsumodificacion(usuarios.get(0).getIdusuario());


                        movimientoItem.setIdinstitucion(idInstitucion);

                        movimientoItem.setIdpersona(Long.parseLong(movimiento.getIdPersona()));

                        movimientoItem.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));


                        movimientoItem.setDescripcion(movimiento.getDescripcion());
                        movimientoItem.setCantidad(new BigDecimal(movimiento.getCantidad()));
                        movimientoItem.setMotivo(movimiento.getMotivo());

                        if(movimiento.getIdFacturacion() != null) {
                        	 movimientoItem.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));
                        }
                        
                        if(movimiento.getTipo() != null) {
                        	movimientoItem.setIdtipomovimiento(Short.parseShort(movimiento.getTipo()));
                        }
                        
                        if(movimiento.getIdConcepto() != null) {
                        	 movimientoItem.setIdhitogeneral(Short.parseShort(movimiento.getIdConcepto()));
                        }
                       
                        if(movimiento.getIdGrupoFacturacion() != null) {
                        	 movimientoItem.setIdgrupofacturacion(Short.parseShort(movimiento.getIdGrupoFacturacion()));
                        }
                       
                        if(movimiento.getIdPartidaPresupuestaria() != null) {
                        	  movimientoItem.setIdpartidapresupuestaria(Integer.parseInt(movimiento.getIdPartidaPresupuestaria()));
                        }
                       
                      
                        


                        response = fcsMovimientosVariosMapper.updateByPrimaryKeySelective(movimientoItem);

                        if (response == 1  && movimiento.getCertificacion() != null && movimiento.getCertificacion() != "") {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateMovimientosVarios() -> Salida con los datos ya modificados");
                  
                            LOGGER.debug("fcsMvariosCertificacionesMapper.updateByPrimaryKeySelective() -> Entrada para actualizar el dato de certificacion");
                            FcsMvariosCertificaciones updCert = new FcsMvariosCertificaciones();
                            updCert.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));
                            updCert.setFechamodificacion(new Date());
                            updCert.setIdinstitucion(idInstitucion);
                            updCert.setUsumodificacion(usuarios.get(0).getIdusuario());
                            updCert.setIdcertificacion(Short.parseShort(movimiento.getCertificacion()));
                            
                            responseCert = fcsMvariosCertificacionesMapper.updateByPrimaryKeySelective(updCert);
                           
                           if(responseCert == 1) {
                               LOGGER.debug("fcsMvariosCertificacionesMapper.updateByPrimaryKeySelective() -> Salida con el dato de certificacion actualizado");

                           }else {
                               //insert
                               LOGGER.debug("fcsMvariosCertificacionesMapper.insert() -> Entrada para insertar el dato de certificacion");                      
                               
                               FcsMvariosCertificaciones insertCert = new FcsMvariosCertificaciones();
                               insertCert.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));
                               insertCert.setFechamodificacion(new Date());
                               insertCert.setIdinstitucion(idInstitucion);
                               insertCert.setUsumodificacion(usuarios.get(0).getIdusuario());
                               insertCert.setIdcertificacion(Short.parseShort(movimiento.getCertificacion()));
                               
                               responseCert2 = fcsMvariosCertificacionesMapper.insertSelective(insertCert);
                               LOGGER.debug("fcsMvariosCertificacionesMapper.insert() -> Salida con el dato de certificacion insertado");

                           }

                        } else if(response == 0 && responseCert == 0 && responseCert2 == 0){
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateMovimientosVarios() -> Salida sin haber modificado los datos generales");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> ERROR al actualizar los datos generales.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    updateResponse.setStatus(SigaConstants.KO);
                }
            } else {
                LOGGER.error(
                        "MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.error("MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> idInstitucion del token nula");
        }

        if ((response == 0 && responseCert == 0 && responseCert2 == 0) && (response == 1 && responseCert == 0 && responseCert2 == 0) && error.getDescription() == null) {
            error.setCode(400);
            updateResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            updateResponse.setStatus(SigaConstants.OK);
        }

        updateResponse.setError(error);

        LOGGER.debug("MovimientosVariosFactServiceImpl.updateMovimientosVarios() -> Salida");

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
                LOGGER.debug(
                        "MovimientosVariosFactServiceImpl.getListadoPagos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.debug(
                        "MovimientosVariosFactServiceImpl.getListadoPagos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && usuarios.size() > 0) {
                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);

                    LOGGER.debug(
                            "MovimientosVariosFactServiceImpl.getListadoPagos() / fcsMovimientosvariosExtendsMapper.getListadoPagos() -> Entrada a fcsMovimientosvariosExtendsMapper para obtener el listado de pagos");
                    List<MovimientosVariosFacturacionItem> pagos = fcsMovimientosvariosExtendsMapper
                            .getListadoPagos(facturacionItem, idInstitucion.toString());


                    movimientos.setFacturacionItem(pagos);
                    LOGGER.debug(
                            "MovimientosVariosFactServiceImpl.getListadoPagos() / fcsMovimientosvariosExtendsMapper.getListadoPagos() -> Salida a fcsMovimientosvariosExtendsMapper para obtener el listado de pagos");
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
                    "MovimientosVariosFactServiceImpl.getListadoPagos() -> Se ha producido un error al buscar los pagos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        movimientos.setError(error);
        LOGGER.debug(
                "MovimientosVariosFactServiceImpl.getListadoPagos() -> Salida del servicio para obtener los pagos");

        return movimientos;
    }

    @Transactional
    public InsertResponseDTO saveClienteMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {

        LOGGER.debug("<MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> Entrada");

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


                        if (response == 1) {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios() -> Salida con el movimiento ya insertado");
                        } else {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios() -> Salida sin haber insertado el movimiento");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.saveClienteMovimientosVarios() -> ERROR al insertar los datos del colegiado en un nuevo movimiento.",
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

        if (response == 0 && error.getDescription() == null) {
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
        LOGGER.debug("<MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> Entrada");

        String token = request.getHeader("Authorization");
        boolean insertadoCorrectamente = false;
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
        Error error = new Error();
        int ko = 0;
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
                        FcsMovimientosvarios movimientoItem = new FcsMovimientosvarios();

                        movimientoItem.setFechamodificacion(new Date());

                        movimientoItem.setUsumodificacion(usuarios.get(0).getIdusuario());


                        movimientoItem.setIdinstitucion(idInstitucion);

                        movimientoItem.setIdpersona(Long.parseLong(movimiento.getIdPersona()));

                        movimientoItem.setIdmovimiento(Long.parseLong(movimiento.getIdMovimiento()));

                        response = fcsMovimientosVariosMapper.updateByPrimaryKeySelective(movimientoItem);


                        if (response == 1) {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateClienteMovimientosVarios() -> Salida con el movimiento ya modificados");
                        } else {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateClienteMovimientosVarios() -> Salida sin haber modificado el movimiento");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.updateClienteMovimientosVarios() -> ERROR al modificar los datos del colegiado del movimiento.",
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

        if (response == 0 && error.getDescription() == null) {
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


    @Override
    public MovimientosVariosFacturacionDTO getMovimientoVarioPorId(String idMovimiento, HttpServletRequest request) {

        LOGGER.info("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() -> Entrada al servicio para obtener los datos del movimiento vario");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        MovimientosVariosFacturacionDTO movimientosVariosFacturacionDTO = new MovimientosVariosFacturacionDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    MovimientosVariosFacturacionItem movimiento = fcsMovimientosvariosExtendsMapper.getMovimientoVarioPorId(idMovimiento, idInstitucion);
                    movimientosVariosFacturacionDTO.getFacturacionItem().add(movimiento);

                } else {
                    LOGGER.warn("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() / admUsuariosExtendsMapper.selectByExample() -> " +
                            "No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() -> Se ha producido un error al intentar obtener el movimiento vario", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        movimientosVariosFacturacionDTO.setError(error);

        LOGGER.info("MovimientosVariosFactServiceImpl.getMovimientoVarioPorId() -> Salida del servicio para obtener los datos del movimiento vario");

        return movimientosVariosFacturacionDTO;
    }


}
