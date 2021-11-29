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
import org.itcgae.siga.db.mappers.FcsMovimientosvariosMapper;
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

                        if (fin == 1) {
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
    public InsertResponseDTO saveDatosGenMovimientosVarios(MovimientosVariosFacturacionItem movimiento, HttpServletRequest request) {

        LOGGER.debug("<MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> Entrada");

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
                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios() -> Insertar Datos Generales a un nuevo movimiento");

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

                        //record.setIdfacturacion(Integer.parseInt(movimiento.getIdFacturacion()));


                        record.setMotivo(movimiento.getMotivo());

                        if (movimiento.getTipo() != null) {
                            record.setIdtipomovimiento(Short.parseShort(movimiento.getTipo()));
                        }

                        //falta certificación
                        response = fcsMovimientosVariosMapper.insertSelective(record);


                        if (response == 1) {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios() -> Salida con los datos ya insertados");
                        } else {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveDatosGenMovimientosVarios() -> Salida sin haber insertado los datos");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.saveDatosGenMovimientosVarios() -> ERROR al insertar los datos generales en el nuevo movimiento.",
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

        if (response == 0 && error.getDescription() == null) {
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
        LOGGER.debug("<MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> Entrada");

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
                    "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                try {

                    if (movimiento != null) {

                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios() -> Modificar los datos generales de un movimiento");
                        FcsMovimientosvarios movimientoItem = new FcsMovimientosvarios();

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

                        if (response == 1) {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios() -> Salida con los datos generales ya modificados");
                        } else {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateDatosGenMovimientosVarios() -> Salida sin haber modificado los datos generales");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.updateDatosGenMovimientosVarios() -> ERROR al actualizar los datos generales.",
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

        if (response == 0 && error.getDescription() == null) {
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

        LOGGER.debug("<MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> Entrada");

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
                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios() -> Insertar datos de la tarjeta criterios de aplicación en un nuevo movimiento");

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

                        //response=fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios(movimiento, idInstitucion.toString());


                        if (response == 1) {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios() -> Salida con los datos ya insertados");
                        } else {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.saveCriteriosMovimientosVarios() -> Salida sin haber insertado los datos");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.saveCriteriosMovimientosVarios() -> ERROR al insertar los datos de la tarjeta criterios de aplicación.",
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

        if (response == 0 && error.getDescription() == null) {
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
        LOGGER.debug("<MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> Entrada");

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
                    "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.debug(
                    "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                try {

                    if (movimiento != null) {

                        LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios() -> Modificar los datos en el movimiento");
                        FcsMovimientosvarios movimientoItem = new FcsMovimientosvarios();

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


                        if (response == 1) {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios() -> Salida con los datos ya modificados");
                        } else {
                            LOGGER.debug("fcsMovimientosvariosExtendsMapper.updateCriteriosMovimientosVarios() -> Salida sin haber modificado los datos");
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "MovimientosVariosFactServiceImpl.updateCriteriosMovimientosVarios() -> ERROR al modificar los datos del movimiento.",
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

        if (response == 0 && error.getDescription() == null) {
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
