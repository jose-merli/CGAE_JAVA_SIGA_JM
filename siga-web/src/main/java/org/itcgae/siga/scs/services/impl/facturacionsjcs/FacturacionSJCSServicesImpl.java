package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ESTADO_FACTURACION;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class FacturacionSJCSServicesImpl implements IFacturacionSJCSServices {

    private Logger LOGGER = Logger.getLogger(FacturacionSJCSServicesImpl.class);

    private static Boolean alguienEjecutando = Boolean.FALSE;

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

    @Autowired
    private FcsFactEstadosfacturacionMapper fcsFactEstadosfacturacionMapper;

    @Autowired
    private FcsFactGrupofactHitoMapper fcsFactGrupofactHitoMapper;

    @Autowired
    private FcsHistoricoTipoactuacionMapper fcsHistoricoTipoactuacionMapper;

    @Autowired
    FcsHistoricoTipoasistcolegioMapper fcsHistoricoTipoasistcolegioMapper;

    @Autowired
    FcsHistoTipoactcostefijoMapper fcsHistoTipoactcostefijoMapper;

    @Autowired
    FcsHistoricoHitofactMapper fcsHistoricoHitofactMapper;

    @Autowired
    FcsFactGuardiascolegiadoMapper fcsFactGuardiascolegiadoMapper;

    @Autowired
    FcsFactApunteMapper fcsFactApunteMapper;

    @Autowired
    FcsFacturacionEstadoEnvioMapper fcsFacturacionEstadoEnvioMapper;

    @Autowired
    FcsFactActuaciondesignaMapper fcsFactActuaciondesignaMapper;

    @Autowired
    FcsFactEjgMapper fcsFactEjgMapper;

    @Autowired
    FcsFactSojMapper fcsFactSojMapper;

    @Autowired
    FcsHistoAcreditacionprocMapper fcsHistoAcreditacionprocMapper;

    @Autowired
    FcsHistoricoAcreditacionMapper fcsHistoricoAcreditacionMapper;

    @Autowired
    FcsHistoricoProcedimientosMapper fcsHistoricoProcedimientosMapper;

    @Autowired
    ScsGuardiascolegiadoMapper scsGuardiascolegiadoMapper;

    @Autowired
    ScsActuacionasistenciaMapper scsActuacionasistenciaMapper;

    @Autowired
    FcsMovimientosvariosMapper fcsMovimientosvariosMapper;

    @Autowired
    ScsAsistenciaMapper scsAsistenciaMapper;

    @Autowired
    ScsEjgMapper scsEjgMapper;

    @Autowired
    ScsSojMapper scsSojMapper;

    @Autowired
    FcsPagosjgMapper fcsPagosjgMapper;

    @Autowired
    FcsFacturacionjgMapper fcsFacturacionjgMapper;

    @Autowired
    private CenInstitucionExtendsMapper institucionMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosMapper;

    @Autowired
    private AdmConfigMapper admConfigMapper;

    @Override
    public FacturacionDTO buscarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        FacturacionDTO facturaciones = new FacturacionDTO();
        List<GenParametros> tamMax = null;
        Integer tamMaximo = null;
        Error error = new Error();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.buscarFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.buscarFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && usuarios.size() > 0) {
                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS")
                            .andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarFacturaciones() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarFacturaciones() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Entrada a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
                    List<FacturacionItem> facturacionItems = fcsFacturacionJGExtendsMapper
                            .buscarFacturaciones(facturacionItem, idInstitucion.toString(), tamMaximo);

                    if (null != facturacionItems && facturacionItems.size() > tamMaximo) {
                        facturacionItems.remove(facturacionItems.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    facturaciones.setFacturacionItem(facturacionItems);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Salida a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
                } else {
                    LOGGER.warn(
                            "FacturacionSJCSServicesImpl.buscarFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("FacturacionSJCSServicesImpl.buscarFacturaciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.buscarFacturaciones() -> Se ha producido un error al buscar las facturaciones",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSServicesImpl.buscarFacturaciones() -> Salida del servicio para obtener las facturaciones");

        return facturaciones;
    }

    @Override
    @Transactional
    public FacturacionDeleteDTO eliminarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        FacturacionDeleteDTO facturacionesDelete = new FacturacionDeleteDTO();
        Error error = new Error();
        int response = 0;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.eliminarFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.eliminarFacturaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                if (checkDeleteFacturacion(facturacionItem, idInstitucion)) {

                    int idFactura = Integer.valueOf(facturacionItem.getIdFacturacion());

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.eliminarFacturaciones() / eliminaTablasFacturacion() -> Entrada a eliminafacturacion para eliminar la facturacion de las tablas relacionadas");
                    response = eliminaTablasFacturacion(idFactura, idInstitucion.toString());

                    if (response > 0)
                        response = updateTablasFacturacion(idFactura, idInstitucion.toString());

                    if (response > 0)
                        response = eliminaFacturacionjg(idFactura, idInstitucion.toString());

                    if (response > 0) {
                        facturacionesDelete.setStatus(SigaConstants.OK);
                    } else {
                        facturacionesDelete.setStatus(SigaConstants.KO);
                    }
                } else {
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.eliminarFacturaciones() -> No se cumplen las restricciones para poder eliminar la facturación");
                    facturacionesDelete.setStatus(SigaConstants.KO);
                    error.setDescription("facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeErrorEliminar");
                }
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.eliminarFacturaciones() -> Salida a eliminafacturacion para eliminar la facturacion de las tablas relacionadas");
            } else {
                LOGGER.warn(
                        "FacturacionSJCSServicesImpl.eliminarFacturaciones() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("FacturacionSJCSServicesImpl.eliminarFacturaciones() -> idInstitucion del token nula");
        }

        facturacionesDelete.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.eliminarFacturaciones() -> Salida del servicio para eliminar las facturaciones");

        return facturacionesDelete;
    }

    private int eliminaTablasFacturacion(int idFactura, String idInstitucion) {
        try {
            // ELIMINAMOS EL REGISTRO DE TODAS LAS TABLAS RELACIONADAS CON LA FACTURACION

            FcsHistoricoTipoactuacionExample fcsHistoricoTipoactuacionExample = new FcsHistoricoTipoactuacionExample();
            fcsHistoricoTipoactuacionExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoricoTipoactuacionMapper.deleteByExample(fcsHistoricoTipoactuacionExample);

            FcsHistoricoTipoasistcolegioExample fcsHistoricoTipoasistcolegioExample = new FcsHistoricoTipoasistcolegioExample();
            fcsHistoricoTipoasistcolegioExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoricoTipoasistcolegioMapper.deleteByExample(fcsHistoricoTipoasistcolegioExample);

            FcsHistoTipoactcostefijoExample fcsHistoTipoactcostefijoExample = new FcsHistoTipoactcostefijoExample();
            fcsHistoTipoactcostefijoExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoTipoactcostefijoMapper.deleteByExample(fcsHistoTipoactcostefijoExample);

            FcsHistoricoHitofactExample fcsHistoricoHitofactExample = new FcsHistoricoHitofactExample();
            fcsHistoricoHitofactExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoricoHitofactMapper.deleteByExample(fcsHistoricoHitofactExample);

            FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
            fcsFactGrupofactHitoExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactGrupofactHitoMapper.deleteByExample(fcsFactGrupofactHitoExample);

            FcsFactGuardiascolegiadoExample fcsFactGuardiascolegiadoExample = new FcsFactGuardiascolegiadoExample();
            fcsFactGuardiascolegiadoExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactGuardiascolegiadoMapper.deleteByExample(fcsFactGuardiascolegiadoExample);

            FcsFactApunteExample fcsFactApunteExample = new FcsFactApunteExample();
            fcsFactApunteExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactApunteMapper.deleteByExample(fcsFactApunteExample);

            FcsFactEstadosfacturacionExample fcsFactEstadosfacturacionExample = new FcsFactEstadosfacturacionExample();
            fcsFactEstadosfacturacionExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactEstadosfacturacionMapper.deleteByExample(fcsFactEstadosfacturacionExample);

            FcsFacturacionEstadoEnvioExample fcsFacturacionEstadoEnvioExample = new FcsFacturacionEstadoEnvioExample();
            fcsFacturacionEstadoEnvioExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFacturacionEstadoEnvioMapper.deleteByExample(fcsFacturacionEstadoEnvioExample);

            FcsFactActuaciondesignaExample fcsFactActuaciondesignaExample = new FcsFactActuaciondesignaExample();
            fcsFactActuaciondesignaExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactActuaciondesignaMapper.deleteByExample(fcsFactActuaciondesignaExample);

            FcsFactEjgExample fcsFactEjgExample = new FcsFactEjgExample();
            fcsFactEjgExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactEjgMapper.deleteByExample(fcsFactEjgExample);

            FcsFactSojExample fcsFactSojExample = new FcsFactSojExample();
            fcsFactSojExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFactSojMapper.deleteByExample(fcsFactSojExample);

            FcsHistoAcreditacionprocExample fcsHistoAcreditacionprocExample = new FcsHistoAcreditacionprocExample();
            fcsHistoTipoactcostefijoExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoAcreditacionprocMapper.deleteByExample(fcsHistoAcreditacionprocExample);

            FcsHistoricoAcreditacionExample fcsHistoricoAcreditacionExample = new FcsHistoricoAcreditacionExample();
            fcsHistoricoAcreditacionExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoricoAcreditacionMapper.deleteByExample(fcsHistoricoAcreditacionExample);

            FcsHistoricoProcedimientosExample fcsHistoricoProcedimientosExample = new FcsHistoricoProcedimientosExample();
            fcsHistoricoProcedimientosExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsHistoricoProcedimientosMapper.deleteByExample(fcsHistoricoProcedimientosExample);

            return 1;
        } catch (Exception e) {
            LOGGER.error("Se ha producido un error al eliminar los registros relacionados con la facturación: "
                    + idFactura + " del colegio " + idInstitucion, e);
            return 0;
        }
    }

    private int updateTablasFacturacion(int idFactura, String idInstitucion) {
        try {
            // ACTUALIZO A NULL EL ID DE FACTURACIÓN EN LAS TABLAS DONDE ESTÁ REFERENCIADA
            // LA FACTURACIÓN QUE ESTAMOS ELIMINANDO
            ScsGuardiascolegiadoExample scsGuardiasColegiadoExample = new ScsGuardiascolegiadoExample();
            scsGuardiasColegiadoExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<ScsGuardiascolegiado> listaScsGuardiascolegiado = scsGuardiascolegiadoMapper
                    .selectByExample(scsGuardiasColegiadoExample);
            for (ScsGuardiascolegiado item : listaScsGuardiascolegiado) {
                item.setIdfacturacion(null);
                scsGuardiascolegiadoMapper.updateByExample(item, scsGuardiasColegiadoExample);
            }

            ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
            scsActuacionasistenciaExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<ScsActuacionasistencia> listaScsActuacionasistencia = scsActuacionasistenciaMapper
                    .selectByExample(scsActuacionasistenciaExample);
            for (ScsActuacionasistencia item : listaScsActuacionasistencia) {
                item.setIdfacturacion(null);
                scsActuacionasistenciaMapper.updateByExample(item, scsActuacionasistenciaExample);
            }

            FcsMovimientosvariosExample fcsMovimientosvariosExample = new FcsMovimientosvariosExample();
            fcsMovimientosvariosExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<FcsMovimientosvarios> listaFcsMovimientosvarios = fcsMovimientosvariosMapper
                    .selectByExample(fcsMovimientosvariosExample);
            for (FcsMovimientosvarios item : listaFcsMovimientosvarios) {
                item.setIdfacturacion(null);
                fcsMovimientosvariosMapper.updateByExample(item, fcsMovimientosvariosExample);
            }

            ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
            scsAsistenciaExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<ScsAsistencia> listaScsAsistencia = scsAsistenciaMapper.selectByExample(scsAsistenciaExample);
            for (ScsAsistencia item : listaScsAsistencia) {
                item.setIdfacturacion(null);
                scsAsistenciaMapper.updateByExample(item, scsAsistenciaExample);
            }

            ScsEjgExample scsEjgExample = new ScsEjgExample();
            scsEjgExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<ScsEjg> listaScsEjg = scsEjgMapper.selectByExample(scsEjgExample);
            for (ScsEjg item : listaScsEjg) {
                item.setIdfacturacion(null);
                scsEjgMapper.updateByExample(item, scsEjgExample);
            }

            ScsSojExample scsSojExample = new ScsSojExample();
            scsSojExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<ScsSoj> listaScsSoj = scsSojMapper.selectByExample(scsSojExample);
            for (ScsSoj item : listaScsSoj) {
                item.setIdfacturacion(null);
                scsSojMapper.updateByExample(item, scsSojExample);
            }

            // Parece que no hay que hacer update en la tabla FCS_PAGOSJG porque no debería
            // haber pagos para una facturación que se puede eliminar

            return 1;
        } catch (Exception e) {
            LOGGER.error("Se ha producido un error al actualizar a null los registros asociados a la facturación: "
                    + idFactura + " del colegio " + idInstitucion, e);
            return 0;
        }

    }

    private int eliminaFacturacionjg(int idFactura, String idInstitucion) {
        try {
            // ELIMINO LA FACTURACION
            FcsFacturacionjgExample fcsFacturacionjgExample = new FcsFacturacionjgExample();
            fcsFacturacionjgExample.createCriteria().andIdfacturacionEqualTo(idFactura)
                    .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            fcsFacturacionjgMapper.deleteByExample(fcsFacturacionjgExample);
            return 1;
        } catch (Exception e) {
            LOGGER.error("Se ha producido un error al eliminar la facturación: " + idFactura + " del colegio "
                    + idInstitucion, e);
            return 0;
        }

    }

    private boolean checkDeleteFacturacion(FacturacionItem facturacionItem, Short idInstitucion) {

        LOGGER.info("Inicio de checkDeleteFacturacion() -> Se comprueba si la facturación se puede eliminar");

        boolean response = true;

        try {
            Integer numero = fcsFacturacionJGExtendsMapper.getNumeroFacturacionesNoCerradas(facturacionItem,
                    idInstitucion);

            // Se comprueba que la facturación no se encuentra en estado "cerrada (idEstado
            // = 30)" y que no exista una facturación con fecha de ejecución posterior a la
            // fecha de ejecución de la facturación que se quiere eliminar
            if (facturacionItem.getIdEstado().equals("30") || null == numero || numero > 0) {
                response = false;
            }
        } catch (Exception e) {
            LOGGER.error("Se ha producido un error al realizar los comprobaciones de eliminación de la facturación: "
                    + facturacionItem.getIdFacturacion() + " del colegio " + idInstitucion, e);
            response = false;
        }

        LOGGER.info("Inicio de checkDeleteFacturacion() -> La facturación se puede eliminar: " + response);

        return response;
    }

    @Override
    public FacturacionDTO datosFacturacion(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        FacturacionDTO facturaciones = new FacturacionDTO();

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                LOGGER.info(
                        "datosFacturacion() / fcsFacturacionJGExtendsMapper.datosFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los datos de la facturacón");
                List<FacturacionItem> facturacionItem = fcsFacturacionJGExtendsMapper.datosFacturacion(idFacturacion,
                        idInstitucion.toString());
                facturaciones.setFacturacionItem(facturacionItem);
                LOGGER.info(
                        "datosFacturacion() / fcsFacturacionJGExtendsMapper.datosFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los datos de la facturación");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para obtener los datos de las facturaciones");

        return facturaciones;
    }

    @Override
    public FacturacionDTO historicoFacturacion(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        FacturacionDTO facturaciones = new FacturacionDTO();
        String idLenguaje = "";
        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                idLenguaje = usuario.getIdlenguaje();
                LOGGER.info(
                        "historicoFacturacion() / fcsFacturacionJGExtendsMapper.historicoFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los historicos de estados de la facturacón");
                List<FacturacionItem> facturacionItem = fcsFacturacionJGExtendsMapper
                        .historicoFacturacion(idFacturacion, idLenguaje, idInstitucion.toString());
                facturaciones.setFacturacionItem(facturacionItem);
                LOGGER.info(
                        "historicoFacturacion() / fcsFacturacionJGExtendsMapper.historicoFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los historicos de estados de la facturación");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para obtener el historico de estados de las facturaciones");

        return facturaciones;
    }

    @Override
    public StringDTO numApuntes(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        StringDTO apuntes = new StringDTO();

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                LOGGER.info(
                        "numApuntes() / fcsFacturacionJGExtendsMapper.numApuntes() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los números de apuntes");
                String numeroApuntes = fcsFacturacionJGExtendsMapper.numApuntes(idFacturacion,
                        idInstitucion.toString());
                apuntes.setValor(numeroApuntes);
                LOGGER.info(
                        "historicoFacturacion() / fcsFacturacionJGExtendsMapper.historicoFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los números de apuntes");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para obtener los números de apuntes");

        return apuntes;
    }

    @Override
    @Transactional
    public InsertResponseDTO saveFacturacion(FacturacionItem facturacionItem, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
        insertResponse.setError(error);
        int response = 0;
        int idFacturacion = 0;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                FcsFacturacionjg record = new FcsFacturacionjg();
                FcsFactEstadosfacturacion record2 = new FcsFactEstadosfacturacion();

                LOGGER.info(
                        "saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Entrada a guardar los datos de la facturacón");

                // GUARDAR DATOAS DE LA FACTURACION
                try {
                    // OBTENEMOS EL ID DE LA FACTURA
                    LOGGER.info(
                            "saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Guardar datos en fcsFacturacionjg");
                    NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdFacturacion(idInstitucion);
                    idFacturacion = Integer.parseInt(idP.getNewId()) + 1;

                    int idPartida = Integer.parseInt(facturacionItem.getIdPartidaPresupuestaria());
                    Short idEstado = 10;

                    // SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA
                    record.setIdfacturacion(idFacturacion);
                    record.setIdinstitucion(idInstitucion);
                    record.setFechadesde(facturacionItem.getFechaDesde());
                    record.setFechahasta(facturacionItem.getFechaHasta());
                    record.setNombre(facturacionItem.getNombre());
                    record.setRegularizacion(facturacionItem.getRegularizacion());
                    record.setPrevision(facturacionItem.getPrevision());
                    record.setVisible(facturacionItem.getVisible());
                    record.setFechamodificacion(new Date());
                    record.setUsumodificacion(usuario.getIdusuario());
                    record.setIdpartidapresupuestaria(idPartida);

                    response = fcsFacturacionJGExtendsMapper.insert(record);

                    LOGGER.info(
                            "saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Salida guardar datos en fcsFacturacionjg");

                    LOGGER.info(
                            "saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Entrada para guardar los estados de la facturacion en fcsFactEstadosFacturacion");

                    // GUARDAMOS EL ESTADO DE LA FACTURA
                    Short idOrdenEstado = 1;

                    record2.setIdfacturacion(record.getIdfacturacion());
                    record2.setIdinstitucion(idInstitucion);
                    record2.setIdestadofacturacion(idEstado);
                    record2.setFechaestado(new Date());
                    record2.setFechamodificacion(new Date());
                    record2.setUsumodificacion(usuario.getIdusuario());
                    record2.setIdordenestado(idOrdenEstado);

                    response = fcsFactEstadosfacturacionMapper.insert(record2);

                    LOGGER.info(
                            "saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Salida guardar los estados de la facturacion en fcsFactEstadosFacturacion");
                } catch (Exception e) {
                    LOGGER.error(
                            "ERROR: FacturacionServicesImpl.saveFacturacion() > al guardar los datos de la facturacion.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    insertResponse.setStatus(SigaConstants.KO);
                }

                LOGGER.info(
                        "saveFacturacion() / fcsFacturacionJGExtendsMapper.datosFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los datos de la facturación");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para guardar las facturaciones");

        if (response == 0 && error.getDescription() == null) {
            error.setCode(400);
            insertResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            insertResponse.setId(Integer.toString(idFacturacion));
            insertResponse.setStatus(SigaConstants.OK);
        }

        insertResponse.setError(error);

        return insertResponse;
    }

    @Override
    @Transactional
    public UpdateResponseDTO updateFacturacion(FacturacionItem facturacionItem, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
        org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
        updateResponse.setError(error);

        int response = 0;
        int idFacturacion = 0;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                FcsFacturacionjg record = new FcsFacturacionjg();

                try {
                    LOGGER.info("updateFacturacion() -> Actualizar datos en fcsFacturacionjg");
                    idFacturacion = Integer.parseInt(facturacionItem.getIdFacturacion());
                    int idPartida = Integer.parseInt(facturacionItem.getIdPartidaPresupuestaria());

                    // SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA
                    record.setIdfacturacion(idFacturacion);
                    record.setIdinstitucion(idInstitucion);
                    record.setFechadesde(facturacionItem.getFechaDesde());
                    record.setFechahasta(facturacionItem.getFechaHasta());
                    record.setNombre(facturacionItem.getNombre());
                    record.setRegularizacion(facturacionItem.getRegularizacion());
                    record.setVisible(facturacionItem.getVisible());
                    record.setFechamodificacion(new Date());
                    record.setUsumodificacion(usuario.getIdusuario());
                    record.setIdpartidapresupuestaria(idPartida);

                    response = fcsFacturacionJGExtendsMapper.updateByPrimaryKeySelective(record);

                    LOGGER.info("updateFacturacion() -> Salida actualizar datos en fcsFacturacionjg");

                } catch (Exception e) {
                    LOGGER.error(
                            "ERROR: FacturacionServicesImpl.updateFacturacion() > al actualizar los datos de la facturacion.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    updateResponse.setStatus(SigaConstants.KO);
                }
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para actualizar las facturaciones");

        if (response == 0 && error.getDescription() == null) {
            error.setCode(400);
            updateResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            updateResponse.setStatus(SigaConstants.OK);
        }

        updateResponse.setError(error);

        return updateResponse;
    }

    @Override
    @Transactional
    public InsertResponseDTO ejecutarFacturacion(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
        insertResponse.setError(error);
        int response = 0;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                FcsFacturacionjg record2 = new FcsFacturacionjg();

                LOGGER.info("ejecutarFacturacion() -> Entrada para poner la facturacion como programada");

                // GUARDAR DATOAS DE LA FACTURACION
                try {
                    // ACTUALIZAMOS LA PREVISION DE LA TABLA FCS_FACTURACIONJG
                    LOGGER.info("ejecutarFacturacion() -> Actualizar la prevision");

                    record2.setPrevision("0");
                    record2.setIdfacturacion(Integer.parseInt(idFacturacion));
                    record2.setIdinstitucion(idInstitucion);

                    response = fcsFacturacionJGExtendsMapper.updateByPrimaryKeySelective(record2);

                    LOGGER.info("ejecutarFacturacion() -> Salida actualizar la prevision");

                    // HACEMOS INSERT DEL ESTADO PROGRAMADA
                    LOGGER.info("ejecutarFacturacion() -> Guardar datos en fcsFactEstadosfacturacion");
                    response = insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_PROGRAMADA.getCodigo(),
                            idInstitucion, Integer.valueOf(idFacturacion), usuario.getIdusuario());
                    LOGGER.info("ejecutarFacturacion() -> Salida guardar datos en fcsFactEstadosfacturacion");
                } catch (Exception e) {
                    LOGGER.error(
                            "ERROR: FacturacionServicesImpl.ejecutarFacturacion() >  Al poner la facturacion como programada.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    insertResponse.setStatus(SigaConstants.KO);
                }

                LOGGER.info("ejecutarFacturacion() -> Salida poner la facturacion como programada");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para poner la facturacion como programada");

        if (response == 0 && error.getDescription() == null) {
            error.setCode(400);
            insertResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            insertResponse.setStatus(SigaConstants.OK);
        }

        insertResponse.setError(error);

        return insertResponse;
    }

    @Override
    public InsertResponseDTO reabrirFacturacion(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
        insertResponse.setError(error);
        int response = 0;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                LOGGER.info("reabrirFacturacion() -> Entrada para reabrir la facturacion");

                // GUARDAR DATOAS DE LA FACTURACION
                try {
                    // HACEMOS INSERT DEL ESTADO ABIERTA
                    LOGGER.info("reabrirFacturacion() -> Guardar datos para reabrir en fcsFactEstadosfacturacion");
                    response = insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_ABIERTA.getCodigo(), idInstitucion,
                            Integer.valueOf(idFacturacion), usuario.getIdusuario());
                    /*
                     * NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(idInstitucion,
                     * idFacturacion);
                     *
                     * Short idOrdenEstado = (short) (Integer.parseInt(idP.getNewId())+1); Short
                     * idEstado = 10;
                     *
                     * record.setIdinstitucion(idInstitucion);
                     * record.setIdestadofacturacion(idEstado);
                     * record.setIdfacturacion(Integer.parseInt(idFacturacion));
                     * record.setFechaestado(new Date()); record.setFechamodificacion(new Date());
                     * record.setUsumodificacion(usuario.getIdusuario());
                     * record.setIdordenestado(idOrdenEstado);
                     *
                     * response = fcsFactEstadosfacturacionMapper.insert(record);
                     */
                    LOGGER.info(
                            "reabrirFacturacion() -> Salida guardar datos para reabrir en fcsFactEstadosfacturacion");
                } catch (Exception e) {
                    LOGGER.error("ERROR: FacturacionServicesImpl.reabrirFacturacion() >  Al reabrir la facturacion.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    insertResponse.setStatus(SigaConstants.KO);
                }

                LOGGER.info("reabrirFacturacion() -> Salida para reabrir la facturacion");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para rabrir las facturaciones");

        if (response == 0 && error.getDescription() == null) {
            error.setCode(400);
            insertResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            insertResponse.setStatus(SigaConstants.OK);
        }

        insertResponse.setError(error);

        return insertResponse;
    }

    @Override
    @Transactional
    public InsertResponseDTO simularFacturacion(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
        insertResponse.setError(error);
        int response = 0;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                FcsFacturacionjg record2 = new FcsFacturacionjg();

                LOGGER.info("simularFacturacion() -> Entrada para simular la facturacion");

                // GUARDAR DATOAS DE LA FACTURACION
                try {
                    // PRIMERO ACTUALIZAMOS FCS_FACTURACIONJG EL CAMPO PREVISION A 1
                    // SETEAMOS LOS DATOS Y HACEMOS UPDATE
                    LOGGER.info("simularFacturacion() -> Actualizar prevision para simular facturacion");

                    record2.setPrevision("1");
                    record2.setIdfacturacion(Integer.parseInt(idFacturacion));
                    record2.setIdinstitucion(idInstitucion);

                    response = fcsFacturacionJGExtendsMapper.updateByPrimaryKeySelective(record2);

                    LOGGER.info("simularFacturacion() -> Salida actualizar prevision para simular facturacion");

                    // HACEMOS INSERT DEL ESTADO PROGRAMADA
                    LOGGER.info("simularFacturacion() -> Guardar datos para simular facturacion");
                    response = insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_PROGRAMADA.getCodigo(),
                            idInstitucion, Integer.valueOf(idFacturacion), usuario.getIdusuario());
                    /*
                     * NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(idInstitucion,
                     * idFacturacion); Short idOrdenEstado = (short)
                     * (Integer.parseInt(idP.getNewId())+1); Short idEstado =
                     * ESTADO_FACTURACION.ESTADO_FACTURACION_PROGRAMADA.getCodigo().shortValue();
                     *
                     * record.setIdinstitucion(idInstitucion);
                     * record.setIdestadofacturacion(idEstado);
                     * record.setIdfacturacion(Integer.parseInt(idFacturacion));
                     * record.setFechaestado(new Date()); record.setFechamodificacion(new Date());
                     * record.setUsumodificacion(usuario.getIdusuario());
                     * record.setIdordenestado(idOrdenEstado);
                     *
                     * response = fcsFactEstadosfacturacionMapper.insert(record);
                     */
                    LOGGER.info("simularFacturacion() -> Salida guardar datos para simular facturacion");
                } catch (Exception e) {
                    LOGGER.error("ERROR: FacturacionServicesImpl.simularFacturacion() >  Al simular la facturacion.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    insertResponse.setStatus(SigaConstants.KO);
                }
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para simular la facturacion");

        if (response == 0 && error.getDescription() == null) {
            error.setCode(400);
            insertResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            insertResponse.setStatus(SigaConstants.OK);
        }

        insertResponse.setError(error);

        return insertResponse;
    }

    @Override
    public FacturacionDTO conceptosFacturacion(String idFacturacion, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.conceptosFacturacion() -> Entrada al servicio para obtener los conceptos de facturaciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        FacturacionDTO facturaciones = new FacturacionDTO();
        String idLenguaje;

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.conceptosFacturacion() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.conceptosFacturacion() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                idLenguaje = usuario.getIdlenguaje();

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.conceptosFacturacion() -> fcsFacturacionJGExtendsMapper.conceptosFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los conceptos de facturacón");
                List<FacturacionItem> facturacionItem = fcsFacturacionJGExtendsMapper
                        .conceptosFacturacion(idFacturacion, idInstitucion.toString(), idLenguaje);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.conceptosFacturacion() -> fcsFacturacionJGExtendsMapper.conceptosFacturacion() -> Salida de fcsFacturacionJGExtendsMapper para obtener los conceptos de facturación");

                facturaciones.setFacturacionItem(facturacionItem);

            } else {
                LOGGER.warn(
                        "FacturacionSJCSServicesImpl.conceptosFacturacion() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("FacturacionSJCSServicesImpl.conceptosFacturacion() -> idInstitucion del token nula");
        }

        LOGGER.info(
                "FacturacionSJCSServicesImpl.conceptosFacturacion() -> Salida del servicio para obtener los conceptos de facturaciones");

        return facturaciones;
    }

    @Override
    @Transactional
    public InsertResponseDTO saveConceptosFac(List<FacturacionItem> listaFacturacionItem, HttpServletRequest request) {

        LOGGER.info("FacturacionSJCSServicesImpl.saveConceptosFac() -> Entrada");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        Error error = new Error();
        int response = 0;
        List<Integer> responses = new ArrayList<Integer>();

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.saveConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "FacturacionSJCSServicesImpl.saveConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && !usuarios.isEmpty()) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                for (FacturacionItem facturacionItem : listaFacturacionItem) {

                    FcsFactGrupofactHito record = new FcsFactGrupofactHito();

                    try {

                        Short idGrupoFacturacion = (short) Integer.parseInt(facturacionItem.getIdGrupo());
                        Short idHitoGeneral = (short) Integer.parseInt(facturacionItem.getIdConcepto());

                        // COMPROBAMOS QUE LOS DATOS A INSERTAR NO EXISTAN EN LA TABLA
                        FcsFactGrupofactHitoExample example = new FcsFactGrupofactHitoExample();
                        example.createCriteria()
                                .andIdfacturacionEqualTo(Integer.parseInt(facturacionItem.getIdFacturacion()))
                                .andIdinstitucionEqualTo(idInstitucion).andIdgrupofacturacionEqualTo(idGrupoFacturacion)
                                .andIdhitogeneralEqualTo(idHitoGeneral);

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.saveConceptosFac() -> fcsFacturacionJGExtendsMapper.selectByExample() -> Entrada: comprobamos que los conceptos no existan previamente.");
                        List<FcsFactGrupofactHito> conceptos = fcsFactGrupofactHitoMapper.selectByExample(example);
                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.saveConceptosFac() -> fcsFacturacionJGExtendsMapper.selectByExample() -> Salida: comprobamos que los conceptos no existan previamente.");

                        if (conceptos.isEmpty()) {

                            record.setIdinstitucion(idInstitucion);
                            record.setIdfacturacion(Integer.parseInt(facturacionItem.getIdFacturacion()));
                            record.setIdgrupofacturacion(idGrupoFacturacion);
                            record.setIdhitogeneral(idHitoGeneral);
                            record.setFechamodificacion(new Date());
                            record.setUsumodificacion(usuario.getIdusuario());

                            LOGGER.info(
                                    "FacturacionSJCSServicesImpl.saveConceptosFac() -> fcsFactGrupofactHitoMapper.insert() -> Entrada guardar conceptos en fcsFacturacionjg");

                            response = fcsFactGrupofactHitoMapper.insert(record);
                            responses.add(response);

                            LOGGER.info(
                                    "FacturacionSJCSServicesImpl.saveConceptosFac() -> fcsFactGrupofactHitoMapper.insert() -> Salida guardar datos en fcsFacturacionjg");
                        } else {
                            error.setCode(400);
                            error.setDescription("facturacionSJCS.facturacionesYPagos.mensaje.conceptosExistentes");
                            LOGGER.info("FacturacionSJCSServicesImpl.saveConceptosFac() -> Concepto ya existente");
                            insertResponse.setStatus(SigaConstants.KO);
                        }
                    } catch (Exception e) {
                        LOGGER.error(
                                "FacturacionServicesImpl.saveConceptosFac() -> ERROR al guardar los conceptos de la facturación.",
                                e);
                        error.setCode(400);
                        error.setDescription("general.mensaje.error.bbdd");
                        insertResponse.setStatus(SigaConstants.KO);
                    }
                }
            } else {
                LOGGER.warn(
                        "FacturacionSJCSServicesImpl.saveConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("FacturacionSJCSServicesImpl.saveConceptosFac() -> idInstitucion del token nula");
        }

        if (responses.contains(0) && error.getDescription() == null) {
            error.setCode(400);
            insertResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            insertResponse.setStatus(SigaConstants.OK);
        }

        insertResponse.setError(error);

        LOGGER.info("FacturacionSJCSServicesImpl.saveConceptosFac() -> Salida");

        return insertResponse;
    }

    @Override
    @Transactional
    public UpdateResponseDTO updateConceptosFac(List<FacturacionItem> listaFacturacionItem,
                                                HttpServletRequest request) {

        LOGGER.info("FacturacionSJCSServicesImpl.updateConceptosFac() -> Entrada");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponse = new UpdateResponseDTO();
        Error error = new Error();
        int response = 0;
        List<Integer> responses = new ArrayList<Integer>();

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

            LOGGER.info(
                    "FacturacionSJCSServicesImpl.updateConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.updateConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && !usuarios.isEmpty()) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                for (FacturacionItem facturacionItem : listaFacturacionItem) {

                    FcsFactGrupofactHito record = new FcsFactGrupofactHito();

                    try {

                        Short idGrupoFacturacion = (short) Integer.parseInt(facturacionItem.getIdGrupo());
                        Short idGrupoOld = (short) Integer.parseInt(facturacionItem.getIdGrupoOld());

                        Short idHitoGeneral = (short) Integer.parseInt(facturacionItem.getIdConcepto());
                        Short idHitoOld = (short) Integer.parseInt(facturacionItem.getIdConceptoOld());

                        // COMPROBAMOS QUE LOS DATOS A INSERTAR NO EXISTAN EN LA TABLA
                        FcsFactGrupofactHitoExample example = new FcsFactGrupofactHitoExample();
                        example.createCriteria()
                                .andIdfacturacionEqualTo(Integer.parseInt(facturacionItem.getIdFacturacion()))
                                .andIdinstitucionEqualTo(idInstitucion).andIdgrupofacturacionEqualTo(idGrupoFacturacion)
                                .andIdhitogeneralEqualTo(idHitoGeneral);

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.updateConceptosFac() -> fcsFacturacionJGExtendsMapper.selectByExample() -> Entrada: comprobamos que los conceptos no existan previamente.");
                        List<FcsFactGrupofactHito> conceptos = fcsFactGrupofactHitoMapper.selectByExample(example);
                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.updateConceptosFac() -> fcsFacturacionJGExtendsMapper.selectByExample() -> Salida: comprobamos que los conceptos no existan previamente.");

                        if (conceptos.isEmpty()) {
                            FcsFactGrupofactHitoExample exampleUpdate = new FcsFactGrupofactHitoExample();

                            exampleUpdate.createCriteria()
                                    .andIdfacturacionEqualTo(Integer.parseInt(facturacionItem.getIdFacturacion()))
                                    .andIdinstitucionEqualTo(idInstitucion).andIdgrupofacturacionEqualTo(idGrupoOld)
                                    .andIdhitogeneralEqualTo(idHitoOld);
                            // SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA
                            record.setIdinstitucion(idInstitucion);
                            record.setIdfacturacion(Integer.parseInt(facturacionItem.getIdFacturacion()));
                            record.setIdgrupofacturacion(idGrupoFacturacion);
                            record.setIdhitogeneral(idHitoGeneral);
                            record.setFechamodificacion(new Date());
                            record.setUsumodificacion(usuario.getIdusuario());

                            LOGGER.info(
                                    "FacturacionSJCSServicesImpl.updateConceptosFac() -> fcsFactGrupofactHitoMapper.updateByExampleSelective() -> Entrada actualizar conceptos en fcsFacturacionjg");
                            response = fcsFactGrupofactHitoMapper.updateByExampleSelective(record, exampleUpdate);
                            responses.add(response);
                            LOGGER.info(
                                    "FacturacionSJCSServicesImpl.updateConceptosFac() -> fcsFactGrupofactHitoMapper.updateByExampleSelective() -> Salida actualizar conceptos en fcsFacturacionjg");

                        } else {
                            error.setCode(400);
                            error.setDescription("facturacionSJCS.facturacionesYPagos.mensaje.conceptosExistentes");
                            LOGGER.info("FacturacionSJCSServicesImpl.updateConceptosFac() -> Concepto ya existente");
                            updateResponse.setStatus(SigaConstants.KO);
                        }
                    } catch (Exception e) {
                        LOGGER.error(
                                "FacturacionSJCSServicesImpl.updateConceptosFac() -> ERROR al actualizar los conceptos de la facturación",
                                e);
                        error.setCode(400);
                        error.setDescription("general.mensaje.error.bbdd");
                        updateResponse.setStatus(SigaConstants.KO);
                    }
                }
            } else {
                LOGGER.warn(
                        "FacturacionSJCSServicesImpl.updateConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("FacturacionSJCSServicesImpl.updateConceptosFac() -> idInstitucion del token nula");
        }

        if (responses.contains(0) && error.getDescription() == null) {
            error.setCode(400);
            updateResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            updateResponse.setStatus(SigaConstants.OK);
        }

        updateResponse.setError(error);

        LOGGER.info("FacturacionSJCSServicesImpl.updateConceptosFac() -> Salida");

        return updateResponse;
    }

    @Override
    @Transactional
    public DeleteResponseDTO deleteConceptosFac(List<FacturacionItem> facturacionDTO, HttpServletRequest request) {

        LOGGER.info("FacturacionSJCSServicesImpl.deleteConceptosFac() -> Entrada");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponse = new DeleteResponseDTO();
        Error error = new Error();
        int response = 0;
        List<Integer> responses = new ArrayList<Integer>();

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

            LOGGER.info(
                    "FacturacionSJCSServicesImpl.deleteConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "FacturacionSJCSServicesImpl.deleteConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                FcsFactGrupofactHito record = new FcsFactGrupofactHito();

                try {

                    for (FacturacionItem factItem : facturacionDTO) {
                        Short idGrupoFacturacion = (short) Integer.parseInt(factItem.getIdGrupo());
                        Short idHitoGeneral = (short) Integer.parseInt(factItem.getIdConcepto());

                        record.setIdinstitucion(idInstitucion);
                        record.setIdfacturacion(Integer.parseInt(factItem.getIdFacturacion()));
                        record.setIdgrupofacturacion(idGrupoFacturacion);
                        record.setIdhitogeneral(idHitoGeneral);

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.deleteConceptosFac() -> fcsFactGrupofactHitoMapper.deleteByPrimaryKey() -> Entrada eliminar conceptos en fcsFacturacionjg");
                        response = fcsFactGrupofactHitoMapper.deleteByPrimaryKey(record);
                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.deleteConceptosFac() -> fcsFactGrupofactHitoMapper.deleteByPrimaryKey() -> Salida eliminar conceptos en fcsFacturacionjg");
                        responses.add(response);
                    }

                } catch (Exception e) {
                    LOGGER.error(
                            "FacturacionSJCSServicesImpl.deleteConceptosFac() -> ERROR al eliminar los conceptos de la facturacion.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    deleteResponse.setStatus(SigaConstants.KO);
                }
            } else {
                LOGGER.warn(
                        "FacturacionSJCSServicesImpl.deleteConceptosFac() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("FacturacionSJCSServicesImpl.deleteConceptosFac() -> idInstitucion del token nula");
        }

        if (responses.contains(0) && error.getDescription() == null) {
            error.setCode(400);
            deleteResponse.setStatus(SigaConstants.KO);
        } else if (error.getCode() == null) {
            error.setCode(200);
            deleteResponse.setStatus(SigaConstants.OK);
        }

        deleteResponse.setError(error);

        LOGGER.info("FacturacionSJCSServicesImpl.deleteConceptosFac() -> Salida");

        return deleteResponse;
    }

    @Override
    public PagosjgDTO datosPagos(String idFacturacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagos = new PagosjgDTO();
        String idLenguaje = "";

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);
                idLenguaje = usuario.getIdlenguaje();

                LOGGER.info(
                        "datosPagos() / fcsFacturacionJGExtendsMapper.datosPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los datos de los pagos");
                List<PagosjgItem> pagosItem = fcsFacturacionJGExtendsMapper.datosPagos(idFacturacion,
                        idInstitucion.toString(), idLenguaje);
                pagosItem = pagosItem.stream()
                        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(PagosjgItem::getIdHitoGeneral))),
                                ArrayList::new));
                pagos.setPagosjgItem(pagosItem);
                LOGGER.info(
                        "datosPagos() / fcsFacturacionJGExtendsMapper.datosPagos() -> Salida fcsFacturacionJGExtendsMapper para obtener los datos de los pagos");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para obtener los datos de las facturaciones");

        return pagos;
    }

    @Override
    public void ejecutaFacturacionSJCS() {
        if (isAlguienEjecutando()) {
            LOGGER.debug(
                    "YA SE ESTA EJECUTANDO LA FACTURACIÓN SJCS EN BACKGROUND. CUANDO TERMINE SE INICIARA OTRA VEZ EL PROCESO.");
            return;
        }
        try {
            procesarFacturacionSJCS();

        } catch (Exception e) {
            throw e;
        } finally {
            setNadieEjecutando();
        }
    }

    private void procesarFacturacionSJCS() {

        CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
        exampleInstitucion.setDistinct(true);
        exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull();

        List<CenInstitucion> listaInstituciones = institucionMapper.selectByExample(exampleInstitucion);

        for (CenInstitucion institucion : listaInstituciones) {
            facturacionesBloqueadas(institucion);

            facturacionesProgramadas(institucion);
        }

    }

    private void facturacionesProgramadas(CenInstitucion institucion) {

        LOGGER.info("ENTRA -> FacturacionSJCSServicesImpl.facturacionesProgramadas()");

        List<FcsFacturacionjg> listaFacturaciones = fcsFacturacionJGExtendsMapper
                .facturacionesPorEstadoProgramadas(institucion.getIdinstitucion().toString());

        for (FcsFacturacionjg item : listaFacturaciones) {

            try {
                // Insertamos el estado En ejecucion para las facturaciones en ejecucion
                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_EN_EJECUCION.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);

                // Ejecutamos la facturación y genero los multiples ficheros pendientes

                // UtilidadesFacturacionSJCS utils = new UtilidadesFacturacionSJCS();
                if (item.getRegularizacion().equals("1")) {
                    ejecutarRegularizacionJG(item, institucion);
                } else {
                    ejecutarFacturacionJG(item, institucion);
                }

                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_EJECUTADA.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);
            } catch (Exception e) {
                LOGGER.error(e);
                actualizaObservacionesEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_EN_EJECUCION.getCodigo(),
                        item.getIdinstitucion(), item.getIdfacturacion(), e.getMessage());
                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_ABIERTA.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);
            }
        }

        LOGGER.info("SALE -> FacturacionSJCSServicesImpl.facturacionesProgramadas(): " + listaFacturaciones.toString());
    }

    private void facturacionesBloqueadas(CenInstitucion institucion) {

        LOGGER.info("ENTRA -> FacturacionSJCSServicesImpl.facturacionesBloqueadas()");

        List<FcsFacturacionjg> listaFacturaciones = fcsFacturacionJGExtendsMapper
                .facturacionesPorEstadoEjecucion(institucion.getIdinstitucion().toString());

        for (FcsFacturacionjg item : listaFacturaciones) {

            try {
                // Insertamos el estado programada para las facturaciones en ejecucion
                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_PROGRAMADA.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);

                // Insertamos el estado En ejecucion para las facturaciones en ejecucion
                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_EN_EJECUCION.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);

                // Ejecutamos la facturación y genero los multiples ficheros pendientes
                // UtilidadesFacturacionSJCS utils = new UtilidadesFacturacionSJCS();
                if (item.getRegularizacion().equals("1")) {
                    ejecutarRegularizacionJG(item, institucion);
                } else {
                    ejecutarFacturacionJG(item, institucion);
                }
                // Insertamos el estado En ejecucion para las facturaciones ejecutada
                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_EJECUTADA.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);
            } catch (Exception e) {
                LOGGER.error(e);
                actualizaObservacionesEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_EN_EJECUCION.getCodigo(),
                        item.getIdinstitucion(), item.getIdfacturacion(), e.getMessage());
                insertarEstado(ESTADO_FACTURACION.ESTADO_FACTURACION_ABIERTA.getCodigo(), item.getIdinstitucion(),
                        item.getIdfacturacion(), SigaConstants.USUMODIFICACION_0);
            }
        }

        LOGGER.info("SALE -> FacturacionSJCSServicesImpl.facturacionesBloqueadas(): " + listaFacturaciones.toString());
    }

    @Transactional
    private int insertarEstado(Integer codigoEstado, Short idInstitucion, Integer idFacturacion, Integer usuario) {
        NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(Short.valueOf(idInstitucion),
                String.valueOf(idFacturacion));
        Short idOrdenEstado = (short) (Integer.parseInt(idP.getNewId()) + 1);
        Short idEstado = codigoEstado.shortValue();

        FcsFactEstadosfacturacion record = new FcsFactEstadosfacturacion();
        record.setIdinstitucion(Short.valueOf(idInstitucion));
        record.setIdestadofacturacion(idEstado);
        record.setIdfacturacion(Integer.valueOf(idFacturacion));
        record.setFechaestado(new Date());
        record.setFechamodificacion(new Date());
        record.setUsumodificacion(usuario);
        record.setIdordenestado(idOrdenEstado);

        return fcsFactEstadosfacturacionMapper.insert(record);
    }

    @Transactional
    private void actualizaObservacionesEstado(Integer codigo, Short idInstitucion, Integer idFacturacion,
                                              String observaciones) {
        if (observaciones != null) {
            FcsFactEstadosfacturacionKey estadoFactPK = fcsFacturacionJGExtendsMapper
                    .ultimoEstadoFacturacion(String.valueOf(idFacturacion), String.valueOf(idInstitucion));

            FcsFactEstadosfacturacion estado = new FcsFactEstadosfacturacion();
            estado.setIdestadofacturacion(estadoFactPK.getIdestadofacturacion());
            estado.setIdfacturacion(estadoFactPK.getIdfacturacion());
            estado.setIdinstitucion(estadoFactPK.getIdinstitucion());
            estado.setFechaestado(estadoFactPK.getFechaestado());
            estado.setObservaciones(observaciones);
            fcsFactEstadosfacturacionMapper.updateByPrimaryKeySelective(estado);
        }
    }

    private void setNadieEjecutando() {
        synchronized (FacturacionSJCSServicesImpl.alguienEjecutando) {
            FacturacionSJCSServicesImpl.alguienEjecutando = Boolean.FALSE;
        }
    }

    public boolean isAlguienEjecutando() {
        synchronized (FacturacionSJCSServicesImpl.alguienEjecutando) {
            if (!FacturacionSJCSServicesImpl.alguienEjecutando) {
                FacturacionSJCSServicesImpl.alguienEjecutando = Boolean.TRUE;
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Funcion que devuelve el nombre de los ficheros de exportacion
     *
     * @param usuario
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Hashtable getNombreFicherosFacturacion(FcsFacturacionjg itemFact, Integer usuario) throws Exception {
        return getNombreFicherosFac(itemFact, null, null, usuario);
    }

    /**
     * Funcion que devuelve el nombre de los ficheros de exportacion
     *
     * @param idPago
     * @param idPersona
     * @param usuario
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Hashtable getNombreFicherosPago(FcsFacturacionjg itemFact, Integer idPago, Long idPersona, Integer usuario)
            throws Exception {
        return getNombreFicherosFac(itemFact, idPago, idPersona, usuario);
    }

    /**
     * Funcion que devuelve el nombre de los ficheros de exportacion
     *
     * @param idPago
     * @param idPersona
     * @param usuario
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    static private Hashtable getNombreFicherosFac(FcsFacturacionjg itemFact, Integer idPago, Long idPersona,
                                                  Integer usuario) throws Exception {
        try {
            Hashtable nombreFicheros = new Hashtable();

            boolean bPrevision = (itemFact.getPrevision().equalsIgnoreCase(SigaConstants.DB_TRUE) ? true : false);
            boolean bRegularizacion = (itemFact.getRegularizacion().equalsIgnoreCase(SigaConstants.DB_TRUE) ? true
                    : false);

            String sNombreFichero = "";
            if (bPrevision && bRegularizacion)
                return nombreFicheros;

            if (idPago != null) {
                sNombreFichero = "PAGOS_"; // Pagos
            } else {
                if (!bPrevision && !bRegularizacion) // Facturacion
                    sNombreFichero = "FACTURACION_";

                if (bPrevision) // Prevision
                    sNombreFichero = "PREVISION_";

                if (bRegularizacion) // Regularizacion
                    sNombreFichero = "REGULARIZACION_";
            }

            String extensionFichero = "_" + itemFact.getIdinstitucion() + "_" + itemFact.getIdfacturacion();
            if (idPago != null) {
                extensionFichero += "_" + idPago;
            }
            if (idPersona != null) {
                extensionFichero += "_" + idPersona;
            }
            extensionFichero += ".XLS";

            nombreFicheros.put(SigaConstants.HITO_GENERAL_TURNO, sNombreFichero + "TURNOSOFICIO" + extensionFichero);
            nombreFicheros.put(SigaConstants.HITO_GENERAL_GUARDIA, sNombreFichero + "GUARDIAS" + extensionFichero);
            nombreFicheros.put(SigaConstants.HITO_GENERAL_EJG, sNombreFichero + "EJG" + extensionFichero);
            nombreFicheros.put(SigaConstants.HITO_GENERAL_SOJ, sNombreFichero + "SOJ" + extensionFichero);

            return nombreFicheros;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void exportarDatosFacturacion(FcsFacturacionjg item, Integer usuario, CenInstitucion institucion)
            throws Exception {
        exportarDatosFac(item, null, null, usuario, institucion);
    }

    public void exportarDatosPagos(FcsFacturacionjg item, Integer idPago, Long idPersona, Integer usuario,
                                   CenInstitucion institucion) throws Exception {
        exportarDatosFac(item, idPago, idPersona, usuario, institucion);
    }

    @SuppressWarnings("rawtypes")
    private void exportarDatosFac(FcsFacturacionjg itemFac, Integer idPago, Long idPersona, Integer usuario,
                                  CenInstitucion institucion) throws Exception {
        try {

            // String tipoP=SigaConstants.FACTURACION_SJCS;

            StringDTO config = genParametrosMapper.selectParametroPorInstitucion("PATH_PREVISIONES_BD",
                    SigaConstants.IDINSTITUCION_0);// .selectByExample(example);

            String pathFicheros = config.getValor();

            Hashtable nombreFicheros = null;
            if (idPago == null) {
                nombreFicheros = getNombreFicherosFacturacion(itemFac, usuario);
            }
            // TODO Realizar cuando se haga el proceso de pagos
            /*
             * else { nombreFicheros = UtilidadesFacturacionSJCS.getNombreFicherosPago(item,
             * idPago, idPersona, usuario); tipoP=ClsConstants.PAGOS_SJCS; }
             */

            // El lenguaje de los informes es el de la institucion a la que pertenecen las
            // facturaciones, no tiene que ver con el usuario que la genera.
            // Por lo tanto accedemos al idioma de cada institucion

            // TURNOS DE OFICIO
            ejecutarPLExportar("PROC_FCS_EXPORTAR_TURNOS_OFI", itemFac.getIdinstitucion().toString(),
                    itemFac.getIdfacturacion().toString(), null, (idPersona == null ? "" : idPersona.toString()),
                    pathFicheros, (String) nombreFicheros.get(SigaConstants.HITO_GENERAL_TURNO),
                    institucion.getIdlenguaje());

            // Guardias
            ejecutarPLExportar("PROC_FCS_EXPORTAR_GUARDIAS", itemFac.getIdinstitucion().toString(),
                    itemFac.getIdfacturacion().toString(), null, (idPersona == null ? "" : idPersona.toString()),
                    pathFicheros, (String) nombreFicheros.get(SigaConstants.HITO_GENERAL_GUARDIA),
                    institucion.getIdlenguaje());

            // SOJ
            ejecutarPLExportar("PROC_FCS_EXPORTAR_SOJ", itemFac.getIdinstitucion().toString(),
                    itemFac.getIdfacturacion().toString(), null, (idPersona == null ? "" : idPersona.toString()),
                    pathFicheros, (String) nombreFicheros.get(SigaConstants.HITO_GENERAL_SOJ),
                    institucion.getIdlenguaje());

            // EJG
            ejecutarPLExportar("PROC_FCS_EXPORTAR_EJG", itemFac.getIdinstitucion().toString(),
                    itemFac.getIdfacturacion().toString(), null, (idPersona == null ? "" : idPersona.toString()),
                    pathFicheros, (String) nombreFicheros.get(SigaConstants.HITO_GENERAL_EJG),
                    institucion.getIdlenguaje());

        } catch (Exception e) {
            throw new Exception("Error al exportar datos: " + e.getMessage());
        }
    }

    public String ejecutarPLExportar(String nomPL, String idInstitucion, String idFacturacionDesde,
                                     String idFacturacionHasta, String idPersona, String pathFichero, String fichero, String idioma)
            throws Exception {
        Object[] param_in; // Parametros de entrada del PL
        String resultado[] = null; // Parametros de salida del PL

        try {
            param_in = new Object[7];
            param_in[0] = idInstitucion;
            param_in[1] = idFacturacionDesde;
            param_in[2] = (idFacturacionHasta == null ? "" : idFacturacionHasta);
            param_in[3] = (idPersona == null ? "" : idPersona);
            param_in[4] = pathFichero;
            param_in[5] = fichero;
            param_in[6] = idioma;

            // Ejecucion del PL
            resultado = callPLProcedure(
                    "{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_EXPORTAR_TURNOS_OFI (?,?,?,?,?,?,?,?,?)}", 2, param_in);
            if (!resultado[0].equalsIgnoreCase("0")) {
                LOGGER.error("Error en PL = " + (String) resultado[1]);
            }

        } catch (Exception e) {
            throw new Exception("Error al exportar datos: " + e.getMessage());
        }

        // Resultado del PL
        return resultado[0];
    }

    @Transactional
    void ejecutarRegularizacionJG(FcsFacturacionjg item, CenInstitucion institucion) throws Exception {
        try {
            // proceso de facturacion

            double importeTotal = 0;
            Double importeOficio = null, importeGuardia = null, importeSOJ = null, importeEJG = null;

            // parametros de entrada
            Object[] param_in_facturacion = new Object[4];
            param_in_facturacion[0] = item.getIdinstitucion().toString(); // IDINSTITUCION
            param_in_facturacion[1] = item.getIdfacturacion().toString(); // IDFACTURACION
            param_in_facturacion[2] = item.getUsumodificacion().toString(); // USUMODIFICACION
            param_in_facturacion[3] = institucion.getIdlenguaje();

            String resultado[] = null;

            //////////////////////////////////
            // TURNOS DE OFICIO rgg 29-03-2005
            resultado = new String[3];
            try {
                resultado = callPLProcedure(
                        "{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_TURNOS_OFI(?,?,?,?,?,?,?)}", 3,
                        param_in_facturacion);
                if (!resultado[1].equals("0")) {
                    LOGGER.error("Error en PL = " + (String) resultado[2]);
                    throw new Exception("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio: "
                            + (String) resultado[2]);
                }
            } catch (IOException | NamingException | SQLException e) {
                LOGGER.error(
                        "Error en PL al ejecutar la regularización de Turnos de Oficio = " + (String) resultado[2]);
                throw new Exception("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio", e);
            }

            importeOficio = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeOficio.doubleValue();

            //////////////////////////////////
            // GUARDIAS PRESENCIALES rgg 29-03-2005
            resultado = new String[3];
            try {
                resultado = callPLProcedure(
                        "{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_GUARDIAS(?,?,?,?,?,?,?)}", 3,
                        param_in_facturacion);
                if (!resultado[1].equals("0")) {
                    LOGGER.error("Error en PL = " + (String) resultado[2]);
                    throw new Exception(
                            "Ha ocurrido un error al ejecutar la regularización de Guardias: " + (String) resultado[2]);
                }
            } catch (IOException | NamingException | SQLException e) {
                LOGGER.error("Error en PL al ejecutar la regularización de Guardias = " + (String) resultado[2]);
                throw new Exception("Ha ocurrido un error al ejecutar la regularización de Guardias", e);
            }

            importeGuardia = new Double((String) resultado[0].replaceAll(",", "."));
            importeTotal += importeGuardia.doubleValue();

            //////////////////////////////////
            // SOJ rgg 29-03-2005
            resultado = new String[3];
            try {
                resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_SOJ(?,?,?,?,?,?,?)}",
                        3, param_in_facturacion);
                if (!resultado[1].equals("0")) {
                    LOGGER.error("Error en PL al ejecutar la regularización de SOJ= " + (String) resultado[2]);
                    throw new Exception(
                            "Ha ocurrido un error al ejecutar la regularización de SOJ: " + (String) resultado[2]);
                }
            } catch (IOException | NamingException | SQLException e) {
                LOGGER.error("Error en PL al ejecutar la regularización de SOJ = " + (String) resultado[2]);
                throw new Exception("Ha ocurrido un error al ejecutar la regularización de SOJ", e);
            }

            importeSOJ = new Double((String) resultado[0].replaceAll(",", "."));
            importeTotal += importeSOJ.doubleValue();

            //////////////////////////////////
            // EJG rgg 29-03-2005
            resultado = new String[3];
            try {
                resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_EJG(?,?,?,?,?,?,?)}",
                        3, param_in_facturacion);
                if (!resultado[1].equals("0")) {
                    LOGGER.error("Error en PL = " + (String) resultado[2]);
                    throw new Exception(
                            "Ha ocurrido un error al ejecutar la regularización de Expedientes de Justicia Gratuita: "
                                    + (String) resultado[2]);
                }
            } catch (IOException | NamingException | SQLException e) {
                LOGGER.error("Error en PL al ejecutar la regularización de EJG= " + (String) resultado[2]);
                throw new Exception("Ha ocurrido un error al ejecutar la regularización de EJG", e);
            }

            importeEJG = new Double(((String) resultado[0]).replaceAll(",", "."));
            importeTotal += importeEJG.doubleValue();

            actualizarTotales(item, importeEJG, importeGuardia, importeOficio, importeSOJ, importeTotal);

            // Exportacion de datos a EXCEL
            // UtilidadesFacturacionSJCS utils = new UtilidadesFacturacionSJCS();
            exportarDatosFacturacion(item, SigaConstants.USUMODIFICACION_0, institucion);
        } catch (Exception ex) {
            LOGGER.error("Error en la ejecución de la Facturación SJCS. idinstitucion=" + item.getIdinstitucion()
                    + " idfacturacion=" + item.getIdfacturacion());
            throw new Exception("Error en la ejecución de la Facturación SJCS. idinstitucion=" + item.getIdinstitucion()
                    + " idfacturacion=" + item.getIdfacturacion(), ex);
        }
    }

    @Transactional
    void ejecutarFacturacionJG(FcsFacturacionjg itemFac, CenInstitucion institucion) throws Exception {

        // Fichero de log
        StringDTO config = genParametrosMapper.selectParametroPorInstitucion("PATH_PREVISIONES_BD",
                SigaConstants.IDINSTITUCION_0);// .selectByExample(example);

        String pathFicheros = config.getValor();

        String sNombreFichero = pathFicheros + File.separator + "LOG_ERROR_" + itemFac.getIdinstitucion() + "_"
                + itemFac.getIdfacturacion() + ".log";
        File ficheroLog = new File(sNombreFichero);
        if (ficheroLog != null && ficheroLog.exists()) {
            ficheroLog.delete();
        }

        try {

            boolean prevision = false;

            // proceso de facturacion
            double importeTotal = 0;
            Double importeOficio = null, importeGuardia = null, importeSOJ = null, importeEJG = null;

            //////////////////////////////////
            // TURNOS DE OFICIO rgg 16-03-2005

            Object[] param_in_facturacion = new Object[3];
            param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
            param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
            param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

            String resultado[] = new String[3];
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_TURNOS_OFI(?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
            if (!resultado[2].equalsIgnoreCase("Fin correcto ")) {
                LOGGER.error("Error en PL = " + (String) resultado[2]);
                throw new Exception("Ha ocurrido un error al ejecutar la facturación de Turnos de Oficio: "
                        + (String) resultado[2]);
            }
            importeOficio = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeOficio.doubleValue();

            //////////////////////////////////
            // GUARDIAS rgg 22-03-2005

            param_in_facturacion = new Object[3];
            param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
            param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
            param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

            resultado = new String[3];
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_GUARDIAS(?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
            if (!resultado[2].equalsIgnoreCase("El proceso:PROC_FCS_FACTURAR_GUARDIAS ha finalizado correctamente")) {
                LOGGER.error("Error en PL = " + (String) resultado[2]);
                throw new Exception(
                        "Ha ocurrido un error al ejecutar la facturación de Guardias: " + (String) resultado[2]);
            }
            importeGuardia = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeGuardia.doubleValue();

            //////////////////////////////////
            // EXPEDIENTES SOJ rgg 22-03-2005

            param_in_facturacion = new Object[3];
            param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
            param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
            param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

            resultado = new String[3];
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_SOJ(?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
            if (!resultado[2].equalsIgnoreCase("Fin correcto")) {
                LOGGER.error("Error en PL = " + (String) resultado[2]);
                throw new Exception(
                        "Ha ocurrido un error al ejecutar la facturación de Expedientes de Orientación Jurídica: "
                                + (String) resultado[2]);
            }
            importeSOJ = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeSOJ.doubleValue();

            //////////////////////////////////
            // EXPEDIENTES EJG rgg 22-03-2005

            param_in_facturacion = new Object[3];
            param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
            param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
            param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

            resultado = new String[3];
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_EJG (?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
            if (!resultado[2].equalsIgnoreCase("Fin correcto")) {
                LOGGER.error("Error en PL = " + (String) resultado[2]);
                throw new Exception(
                        "Ha ocurrido un error al ejecutar la facturación de Expedientes de Justicia Gratuita: "
                                + (String) resultado[2]);
            }

            importeEJG = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeEJG.doubleValue();

            if (prevision) {
                //////////////////////////////////////
                /// CREAMOS EL INFORME
                // ArrayList filtrosInforme = getFiltrosInforme(itemFac, institucion);
                // File fichero = getFicheroGenerado(institucion,
                ////////////////////////////////////// SigaConstants.I_INFORMEFACTSJCS,null,
                ////////////////////////////////////// filtrosInforme);
                // itemFac.setNombrefisico(fichero.getPath());

                // TODO Esta funcionalidad llamará al módulo de comunicaciones cuando esté
                // desarrollado

                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

            actualizarTotales(itemFac, importeEJG, importeGuardia, importeOficio, importeSOJ, importeTotal);

            // Exportacion de datos a EXCEL: Se ha comentado este metodo por que no se
            // quiere utilizar
            // UtilidadesFacturacionSJCS.exportarDatosFacturacion(new
            // Integer(idInstitucion), new Integer(idFacturacion), this.usrbean);

        } catch (Exception e) {
            LOGGER.error("Error al ejecutar facturacion SJCS.", e);
            throw e;
        }
    }

    @Transactional
    private void actualizarTotales(FcsFacturacionjg item, Double importeEJG, Double importeGuardia,
                                   Double importeOficio, Double importeSOJ, double importeTotal) throws Exception {
        //////////////////////////////////
        // ACTUALIZO LOS TOTALES
        item.setImporteejg(new BigDecimal(importeEJG));
        item.setImporteguardia(new BigDecimal(importeGuardia));
        item.setImporteoficio(new BigDecimal(importeOficio));
        item.setImportesoj(new BigDecimal(importeSOJ));
        item.setImportetotal(new BigDecimal(importeTotal));
        try {
            fcsFacturacionjgMapper.updateByPrimaryKey(item);
        } catch (Exception ex) {
            LOGGER.error("Ha ocurrido un error al almacenar los importes resultado de la facturacion");
            throw new Exception("Ha ocurrido un error al almacenar los importes resultado de la facturacion", ex);
        }

    }

    /**
     * Recupera el datasource con los datos de conexión sacados del fichero de
     * configuracion
     *
     * @return
     * @throws IOException
     * @throws NamingException
     */
    private DataSource getOracleDataSource() throws IOException, NamingException {
        try {

            LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");

            AdmConfigExample example = new AdmConfigExample();
            example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
            List<AdmConfig> config = admConfigMapper.selectByExample(example);
            Context ctx = new InitialContext();
            return (DataSource) ctx.lookup(config.get(0).getValor());
        } catch (NamingException e) {
            throw e;
        }
    }

    /**
     * Calls a PL Funtion
     *
     * @param functionDefinition string that defines the function
     * @param inParameters       input parameters
     * @param outParameters      number of output parameters
     * @return error code, '0' if ok
     * @throws NamingException
     * @throws IOException
     * @throws SQLException    type Exception
     * @author CSD
     */
    private String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
            throws IOException, NamingException, SQLException {
        String result[] = null;

        if (outParameters > 0)
            result = new String[outParameters];
        DataSource ds = getOracleDataSource();
        Connection con = ds.getConnection();
        try {
            CallableStatement cs = con.prepareCall(functionDefinition);
            int size = inParameters.length;

            // input Parameters
            for (int i = 0; i < size; i++) {

                cs.setString(i + 1, (String) inParameters[i]);
            }
            // output Parameters
            for (int i = 0; i < outParameters; i++) {
                cs.registerOutParameter(i + size + 1, Types.VARCHAR);
            }

            for (int intento = 1; intento <= 2; intento++) {
                try {
                    cs.execute();
                    break;

                } catch (SQLTimeoutException tex) {
                    throw tex;

                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
                        // segunda vez deberia funcionar)
                        throw ex;
                    }
                }

            }

            for (int i = 0; i < outParameters; i++) {
                result[i] = cs.getString(i + size + 1);
            }
            cs.close();
            return result;

        } catch (SQLTimeoutException ex) {
            return null;
        } catch (SQLException ex) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            con.close();
            con = null;
        }
    }

}