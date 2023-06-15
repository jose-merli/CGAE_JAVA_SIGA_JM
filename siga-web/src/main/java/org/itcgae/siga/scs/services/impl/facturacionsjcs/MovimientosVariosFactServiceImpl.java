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
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuacionasistenciaKey;
import org.itcgae.siga.db.entities.ScsActuaciondesigna;
import org.itcgae.siga.db.entities.ScsActuaciondesignaKey;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaKey;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasExample;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosMapper;
import org.itcgae.siga.db.mappers.FcsMvariosCertificacionesMapper;
import org.itcgae.siga.db.mappers.ScsActuaciondesignaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsMovimientosvariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActuacionasistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCabeceraguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IMovimientosVariosFactServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    @Autowired
    private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;
    
    @Autowired
    private ScsDesignacionesExtendsMapper scsDesignaExtendsMapper;
    
    @Autowired
    private ScsActuaciondesignaMapper scsActDesignaExtendsMapper;
    
    @Autowired
    private ScsActuacionasistenciaExtendsMapper scsActAsistenciaExtendsMapper;
    
    @Autowired
    private ScsCabeceraguardiasExtendsMapper scsCabeceraguardiasExtendsMapper;
    
    @Autowired
    private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;
    
    @Autowired
    private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
    
    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;
    
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
                    
                    Integer tamMaximo = getTamanoMaximo(idInstitucion);

                    LOGGER.debug(
                            "MovimientosVariosFactServiceImpl.buscarMovimientosVarios() / fcsMovimientosvariosExtendsMapper.buscarMovimientosVarios() -> Entrada a fcsMovimientosvariosExtendsMapper para obtener los movimientos varios");
                    List<MovimientosVariosFacturacionItem> movimientosItems = fcsMovimientosvariosExtendsMapper
                            .buscarMVColegiado(facturacionItem, idInstitucion.toString(), tamMaximo);


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
                     
                     responses.add(fin);
                        if (fin == 1 || origen == null) {
                        	
                            LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Entrada para eliminar un movimiento vario");
                            FcsMovimientosvariosKey movimientoMapper = new FcsMovimientosvariosKey();
                            movimientoMapper.setIdinstitucion(idInstitucion);
                            movimientoMapper.setIdmovimiento(Long.valueOf(mov.getIdMovimiento()));
                            response = fcsMovimientosVariosMapper.deleteByPrimaryKey(movimientoMapper);
                            //response=fcsMovimientosvariosExtendsMapper.delete(mov.getIdInstitucion().toString(), mov.getIdMovimiento());
                            LOGGER.debug("MovimientosVariosFactServiceImpl.delete() -> Salida del movimiento eliminado.");
                            
                           if(response == 1 && mov.getCertificacion()!=null && mov.getCertificacion() != "") {
                        	   LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Entrada para eliminar la certficación asociada al movimiento");
                            	//borrar la certificación asociada a este movimiento
                            	FcsMvariosCertificacionesKey deleteCert = new FcsMvariosCertificacionesKey();
                            	
                            	deleteCert.setIdcertificacion(Short.valueOf(mov.getCertificacion()));
                            	deleteCert.setIdinstitucion(idInstitucion);
                            	deleteCert.setIdmovimiento(Long.valueOf(mov.getIdMovimiento()));
                            	
                               cert = fcsMvariosCertificacionesMapper.deleteByPrimaryKey(deleteCert);
                               LOGGER.debug("MovimientosVariosFactServiceImpl.delete() ->Salida para eliminar la certificacion asociada al movimiento vario");
                               responses.add(cert);
                           }
                        }

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

        if (responses.contains(0) && error.getDescription() != null) {
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


    public InsertResponseDTO saveMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {

        LOGGER.debug("<MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> Entrada");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        Error error = new Error();
    	Long newid = Long.valueOf("0");
        int response = 0;


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

                    newid = Long.valueOf(idMovimiento.getNewId());
                    response = guardarMV(idInstitucion,movimiento,newid,usuarios.get(0));

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.saveMovimientosVarios() -> ERROR al insertar los datos generales en el nuevo movimiento.",
                            e);
                    error.setCode(400);
                    if(e.getMessage()!=null && e.getMessage().contains("ya tiene un movimiento vario asociado")) {
                    	error.setDescription(e.getMessage());
                    }else {
                    	error.setDescription("general.mensaje.error.bbdd");
                    }
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

        if (response == 0 && error.getDescription() != null) {
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
    private int guardarMV(Short idInstitucion, MovimientosVariosFacturacionItem movimiento, Long newid, AdmUsuarios usuario) throws Exception {
    	int response = 0;
        int responseAsunto = 0;
        int responseCert = 0;
        
    	if (movimiento != null) {
        	
            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveMovimientosVarios() -> Insertar Datos Generales a un nuevo movimiento");

            FcsMovimientosvarios record = new FcsMovimientosvarios();
            record.setFechamodificacion(new Date());
            record.setUsumodificacion(usuario.getIdusuario());
            record.setIdinstitucion(idInstitucion);

            record.setIdmovimiento(newid + 1);
            record.setIdpersona(Long.valueOf(movimiento.getIdPersona()));
            record.setFechaalta(new Date());

            record.setDescripcion(movimiento.getDescripcion());
            record.setCantidad(new BigDecimal(movimiento.getCantidad()));
            record.setContabilizado(" ");

            record.setMotivo(movimiento.getMotivo());

            if (movimiento.getTipo() != null && !movimiento.getTipo().isEmpty()) {
                record.setIdtipomovimiento(Short.valueOf(movimiento.getTipo()));
            }
            
            if (movimiento.getIdGrupoFacturacion() != null && !movimiento.getIdGrupoFacturacion().isEmpty()) {
            	record.setIdgrupofacturacion(Short.valueOf(movimiento.getIdGrupoFacturacion()));
            }

            if (movimiento.getIdFacturacion() != null && !movimiento.getIdFacturacion().isEmpty()) {
            	record.setIdfacturacion(Integer.valueOf(movimiento.getIdFacturacion()));

            }

            if (movimiento.getIdConcepto() != null && !movimiento.getIdConcepto().isEmpty()) {
            	record.setIdhitogeneral(Short.valueOf(movimiento.getIdConcepto()));
            }

            if (movimiento.getIdPartidaPresupuestaria() != null && !movimiento.getIdPartidaPresupuestaria().isEmpty()) {
            	record.setIdpartidapresupuestaria(Integer.valueOf(movimiento.getIdPartidaPresupuestaria()));
            }

     
            
            response = fcsMovimientosVariosMapper.insertSelective(record);
            
            if(response == 1) {
            	responseAsunto = actualizarAsuntoMV(movimiento, newid + 1 , idInstitucion);
            }


            if (response == 1 && movimiento.getCertificacion() != null && movimiento.getCertificacion() != "") {                     
                
                LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveMovimientosVarios() -> Salida con los datos ya insertados");
                
                FcsMvariosCertificaciones insertCert = new FcsMvariosCertificaciones();
                insertCert.setIdmovimiento(newid + 1);
                insertCert.setFechamodificacion(new Date());
                insertCert.setIdinstitucion(idInstitucion);
                insertCert.setUsumodificacion(usuario.getIdusuario());
                insertCert.setIdcertificacion(Short.valueOf(movimiento.getCertificacion()));
                
                responseCert = fcsMvariosCertificacionesMapper.insert(insertCert);
                
            }
            
            if(response == 0 || (responseCert == 0 && movimiento.getCertificacion() != null && movimiento.getCertificacion() != "")
            		|| responseAsunto == 0){
                LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveMovimientosVarios() -> Salida sin haber insertado los datos");
                response = 0;
            }
        }
        return response;
	}

	private int actualizarAsuntoMV(MovimientosVariosFacturacionItem movimiento, Long newid, Short idInstitucion) throws Exception {
		String asuntoSplit [] = null;
		String asunto = "";
		int response = 0;
		
		if(movimiento.getDescripcion()!=null && !movimiento.getDescripcion().isEmpty()) {
			asuntoSplit = movimiento.getDescripcion().split(" ");
			if(asuntoSplit != null && asuntoSplit.length > 0){
				asunto = asuntoSplit[0];
				String identificador [] = null;
				String anio = "";
				String numero = "";
				String actuacion = "";
				switch(asunto)
				{
				   case "Asistencia" :
					   identificador = asuntoSplit[1].split("/");
					   if(identificador!= null && identificador.length>0) {
							anio = identificador[0];
							numero = identificador[1];
							ScsAsistenciaKey key = new ScsAsistenciaKey();
							key.setAnio(Short.valueOf(anio));
							key.setNumero(Long.valueOf(numero));
							key.setIdinstitucion(idInstitucion);
							ScsAsistencia asistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(key);
							
							if(asistencia!= null) {
								if(asistencia.getIdmovimiento()!=null) {
									throw new Exception("La asistencia "+asistencia.getAnio()+"/"+asistencia.getNumero()
										+" ya tiene un movimiento vario asociado");
								}
								asistencia.setIdmovimiento(newid);
								response = scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(asistencia);
							}else {
								throw new Exception("No se ha encontrado la asistencia relacionada");
							}
					   }
				      break; 
				      
				   case "Designación" :
					   identificador = asuntoSplit[1].split("/");
					   if(identificador!= null && identificador.length>0) {
							anio = identificador[0];
							numero = identificador[1];
							actuacion = identificador[2];
							actuacion = actuacion.substring(0, actuacion.indexOf("-"));
							
							ScsDesignaExample example = new ScsDesignaExample();
							example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(Short.valueOf(anio))
									.andCodigoEqualTo(numero);
							List<ScsDesigna> designaList = scsDesignaExtendsMapper.selectByExample(example);
							if(designaList!= null && !designaList.isEmpty()) {
								ScsDesigna designa = designaList.get(0);
								
								ScsActuaciondesignaKey key = new ScsActuaciondesignaKey();
								key.setAnio(designa.getAnio());
								key.setIdinstitucion(idInstitucion);
								key.setIdturno(designa.getIdturno());
								key.setNumero(designa.getNumero());
								key.setNumeroasunto(Long.valueOf(actuacion));
								ScsActuaciondesigna act = scsActDesignaExtendsMapper.selectByPrimaryKey(key );
								if(act!=null) {
									if(act.getIdmovimiento()!=null) {
										throw new Exception("La actuación "+ act.getNumeroasunto() + " de la designa "+act.getAnio()+"/"
												+act.getNumero()+" ya tiene un movimiento vario asociado");
									}
									act.setIdmovimiento(newid);
									response = scsActDesignaExtendsMapper.updateByPrimaryKeySelective(act);
								}
							}else {
								throw new Exception("No se ha encontrado la designa relacionada");
							}
					   }
				      break;
				      
				   case "Actuación" :
					   identificador = asuntoSplit[3].split("/");
					   if(identificador!= null && identificador.length>0) {
							anio = identificador[0];
							numero = identificador[1];
							actuacion = identificador[2];
							ScsActuacionasistenciaKey key = new ScsActuacionasistenciaKey();
							key.setAnio(Short.valueOf(anio));
							key.setNumero(Long.valueOf(numero));
							key.setIdinstitucion(idInstitucion);
							key.setIdactuacion(Long.valueOf(actuacion));
							ScsActuacionasistencia actAsistencia = scsActAsistenciaExtendsMapper.selectByPrimaryKey(key);
							if(actAsistencia!= null) {
								if(actAsistencia.getIdmovimiento()!=null) {
									throw new Exception("La actuación "+ actAsistencia.getNumeroasunto() + " de la asistencia "+actAsistencia.getAnio()+"/"
											+actAsistencia.getNumero()+" ya tiene un movimiento vario asociado");
								}
								actAsistencia.setIdmovimiento(newid);
								response = scsActAsistenciaExtendsMapper.updateByPrimaryKeySelective(actAsistencia);
							}else {
								throw new Exception("No se ha encontrado la actuación de la asistencia relacionada");
							}
					   }
					  break;
					  
				   case "Guardia":
					   int separadorPunto = 0;
					   int separadorMayor = 0;
					   separadorPunto = movimiento.getDescripcion().indexOf(".");
					   separadorMayor = movimiento.getDescripcion().indexOf(">");
					   String nombreTurno = movimiento.getDescripcion().substring(separadorPunto+1, separadorMayor);
					   String nombreGuardia = movimiento.getDescripcion().substring(separadorMayor+1);
					   String fechaIni = movimiento.getDescripcion().substring(movimiento.getDescripcion().indexOf("/")-2,separadorPunto);
					   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					   Date fechaInicio = sdf.parse(fechaIni.trim());
					   if(nombreTurno != null && !nombreTurno.isEmpty() && nombreGuardia != null && !nombreGuardia.isEmpty()) {
						   ScsTurnoExample exampleTurno = new ScsTurnoExample();
						   exampleTurno.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						   		.andNombreEqualTo(nombreTurno);
						   List<ScsTurno> turno = scsTurnosExtendsMapper.selectByExample(exampleTurno);
						   if(turno == null) {
							   exampleTurno.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							   		.andAbreviaturaEqualTo(nombreTurno);
							   turno = scsTurnosExtendsMapper.selectByExample(exampleTurno);
						   }
						   if(turno != null) {
								ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
								example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(turno.get(0).getIdturno()).andNombreEqualTo(nombreGuardia);
								List<ScsGuardiasturno> guardiaList = scsGuardiasturnoExtendsMapper.selectByExample(example);
								if(guardiaList!= null && !guardiaList.isEmpty()) {
									ScsGuardiasturno guardia = guardiaList.get(0);
									ScsCabeceraguardiasKey key = new ScsCabeceraguardiasKey();
									key.setIdguardia(guardia.getIdguardia());
									key.setIdinstitucion(idInstitucion);
									key.setIdpersona(Long.valueOf(movimiento.getIdPersona()));
									key.setIdturno(guardia.getIdturno());
									key.setFechainicio(fechaInicio);
									ScsCabeceraguardias cabecera = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(key );
									if(cabecera!= null) {
										if(cabecera.getIdmovimiento()!=null) {
											throw new Exception("La guardia "+guardia.getDescripcion()
												+" ya tiene un movimiento vario asociado");
										}
										cabecera.setIdmovimiento(newid);
									response = scsCabeceraguardiasExtendsMapper.updateByPrimaryKeySelective(cabecera);
									}
								}
							}else {
								throw new Exception("No se ha encontrado la actuación de la asistencia relacionada");
							}
					   }
					  break;

				   default : 
				      // EJG
				}

			}
		}
		return response;
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

                        movimientoItem.setIdpersona(Long.valueOf(movimiento.getIdPersona()));

                        movimientoItem.setIdmovimiento(Long.valueOf(movimiento.getIdMovimiento()));


                        movimientoItem.setDescripcion(movimiento.getDescripcion());
                        movimientoItem.setCantidad(new BigDecimal(movimiento.getCantidad()));
                        movimientoItem.setMotivo(movimiento.getMotivo());

                        if(movimiento.getIdFacturacion() != null) {
                        	 movimientoItem.setIdfacturacion(Integer.valueOf(movimiento.getIdFacturacion()));
                        }
                        
                        if(movimiento.getTipo() != null) {
                        	movimientoItem.setIdtipomovimiento(Short.valueOf(movimiento.getTipo()));
                        }
                        
                        if(movimiento.getIdConcepto() != null) {
                        	 movimientoItem.setIdhitogeneral(Short.valueOf(movimiento.getIdConcepto()));
                        }
                       
                        if(movimiento.getIdGrupoFacturacion() != null) {
                        	 movimientoItem.setIdgrupofacturacion(Short.valueOf(movimiento.getIdGrupoFacturacion()));
                        }
                       
                        if(movimiento.getIdPartidaPresupuestaria() != null) {
                        	  movimientoItem.setIdpartidapresupuestaria(Integer.valueOf(movimiento.getIdPartidaPresupuestaria()));
                        }
                       
                      
                        


                        response = fcsMovimientosVariosMapper.updateByPrimaryKeySelective(movimientoItem);

                        if (response == 1  && movimiento.getCertificacion() != null && movimiento.getCertificacion() != "") {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateMovimientosVarios() -> Salida con los datos ya modificados");
                  
                            LOGGER.debug("fcsMvariosCertificacionesMapper.updateByPrimaryKeySelective() -> Entrada para actualizar el dato de certificacion");
                            FcsMvariosCertificaciones updCert = new FcsMvariosCertificaciones();
                            updCert.setIdmovimiento(Long.valueOf(movimiento.getIdMovimiento()));
                            updCert.setFechamodificacion(new Date());
                            updCert.setIdinstitucion(idInstitucion);
                            updCert.setUsumodificacion(usuarios.get(0).getIdusuario());
                            updCert.setIdcertificacion(Short.valueOf(movimiento.getCertificacion()));
                            
                            responseCert = fcsMvariosCertificacionesMapper.updateByPrimaryKeySelective(updCert);
                           
                           if(responseCert == 1) {
                               LOGGER.debug("fcsMvariosCertificacionesMapper.updateByPrimaryKeySelective() -> Salida con el dato de certificacion actualizado");

                           }else {
                               //insert
                               LOGGER.debug("fcsMvariosCertificacionesMapper.insert() -> Entrada para insertar el dato de certificacion");                      
                               
                               FcsMvariosCertificaciones insertCert = new FcsMvariosCertificaciones();
                               insertCert.setIdmovimiento(Long.valueOf(movimiento.getIdMovimiento()));
                               insertCert.setFechamodificacion(new Date());
                               insertCert.setIdinstitucion(idInstitucion);
                               insertCert.setUsumodificacion(usuarios.get(0).getIdusuario());
                               insertCert.setIdcertificacion(Short.valueOf(movimiento.getCertificacion()));
                               
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

                    Long newid = Long.valueOf(idMovimiento.getNewId());

                    if (movimiento != null) {
                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveClienteMovimientosVarios() -> Insertar nuevo cliente en el movimiento");

                        String idPersona = fcsMovimientosvariosExtendsMapper.selectIdPersonaByNif(movimiento.getNif());

                        FcsMovimientosvarios record = new FcsMovimientosvarios();
                        record.setFechamodificacion(new Date());
                        record.setUsumodificacion(usuarios.get(0).getIdusuario());
                        record.setIdinstitucion(idInstitucion);

                        record.setIdmovimiento(newid + 1);
                        record.setIdpersona(Long.valueOf(idPersona));
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

                        movimientoItem.setIdpersona(Long.valueOf(movimiento.getIdPersona()));

                        movimientoItem.setIdmovimiento(Long.valueOf(movimiento.getIdMovimiento()));

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

    private Integer getTamanoMaximo(Short idinstitucion) {
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
	    		.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idinstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    List<GenParametros> tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        Integer tamMaximo = null;
		if (tamMax != null) {
            tamMaximo  = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
		return tamMaximo;
	}
    
}
