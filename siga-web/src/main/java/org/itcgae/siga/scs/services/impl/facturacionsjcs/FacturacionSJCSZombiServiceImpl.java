package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.scs.mappers.ScsAcreditacionExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSZombiService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FacturacionSJCSZombiServiceImpl implements IFacturacionSJCSZombiService {

    private Logger LOGGER = Logger.getLogger(FacturacionSJCSZombiServiceImpl.class);

    @Autowired
    FcsHistoricoAcreditacionExtendsMapper fcsHistoricoAcreditacionExtendsMapper;

    @Autowired
    ScsAcreditacionExtendsMapper scsAcreditacionExtendsMapper;

    @Autowired
    FcsHistoAcreditacionprocExtendsMapper fcsHistoAcreditacionprocExtendsMapper;

    @Autowired
    FcsHistoricoProcedimientosExtendsMapper fcsHistoricoProcedimientosExtendsMapper;

    @Autowired
    FcsFactActuaciondesignaMapper fcsFactActuaciondesignaMapper;

    @Autowired
    FcsFactApunteMapper fcsFactApunteMapper;

    @Autowired
    FcsFactGuardiascolegiadoMapper fcsFactGuardiascolegiadoMapper;

    @Autowired
    FcsHistoricoTipoasistcolegioExtendsMapper fcsHistoricoTipoasistcolegioExtendsMapper;

    @Autowired
    FcsHistoricoTipoactuacionExtendsMapper fcsHistoricoTipoactuacionExtendsMapper;

    @Autowired
    FcsHistoTipoactcostefijoExtendsMapper fcsHistoTipoactcostefijoExtendsMapper;

    @Autowired
    FcsHistoricoHitofactExtendsMapper fcsHistoricoHitofactExtendsMapper;

    @Autowired
    FcsFactAsistenciaMapper fcsFactAsistenciaMapper;

    @Autowired
    FcsFactActuacionasistenciaMapper fcsFactActuacionasistenciaMapper;

    @Autowired
    FcsHistoricoHitofactMapper fcsHistoricoHitofactMapper;

    @Autowired
    FcsFactSojMapper fcsFactSojMapper;

    @Autowired
    FcsFactEjgMapper fcsFactEjg;

    @Autowired
    ScsActuaciondesignaMapper scsActuaciondesignaMapper;

    @Autowired
    ScsCabeceraguardiasMapper scsCabeceraguardiasMapper;

    @Autowired
    ScsGuardiascolegiadoMapper scsGuardiascolegiadoMapper;

    @Autowired
    ScsAsistenciaMapper scsAsistenciaMapper;

    @Autowired
    ScsActuacionasistenciaMapper scsActuacionasistenciaMapper;

    @Autowired
    ScsSojMapper scsSojMapper;

    @Autowired
    ScsEjgMapper scsEjgMapper;

    //FACTURAS SIN REGULARIZACION
    @Override
    public FacturacionDTO deshacerTurnosOfi(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos las facturaciones con regularizacion
        try {
            //DESHACER REGULAR_TURNOS_OFI
            deshacerRegularTurnosOfi(facturacionItem);

            ScsActuaciondesigna  record = new ScsActuaciondesigna ();
            record.setFacturado(new String("0"));

            ScsActuaciondesignaExample example = new ScsActuaciondesignaExample();
            example.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
            andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsActuaciondesignaMapper.updateByExampleSelective(record, example);

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerTurnosOfi() -> Se ha producido un error al deshacer los de turnos de oficio",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerTurnosOfi() -> Salida del servicio para deshacer los turnos de oficio");

        return facturaciones;
    }

    @Override
    public FacturacionDTO deshacerGuardias(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        try {
            deshacerRegularGuardias(facturacionItem);

            ScsCabeceraguardias  record = new ScsCabeceraguardias ();
            record.setFacturado(new String("0"));

            ScsCabeceraguardiasExample example = new ScsCabeceraguardiasExample();
            example.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
                    andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsCabeceraguardiasMapper.updateByExampleSelective(record, example);

            ScsGuardiascolegiado recordGC = new ScsGuardiascolegiado ();
            record.setFacturado(new String("0"));

            ScsGuardiascolegiadoExample exampleGC = new ScsGuardiascolegiadoExample();
            exampleGC.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
                    andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsGuardiascolegiadoMapper.updateByExampleSelective(recordGC, exampleGC);

            ScsAsistencia  recordA = new ScsAsistencia ();
            record.setFacturado(new String("0"));

            ScsAsistenciaExample exampleA = new ScsAsistenciaExample();
            exampleA.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
                    andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsAsistenciaMapper.updateByExampleSelective(recordA, exampleA);

            ScsActuacionasistencia  recordAA = new ScsActuacionasistencia ();
            record.setFacturado(new String("0"));

            ScsActuacionasistenciaExample exampleAA = new ScsActuacionasistenciaExample();
            exampleAA.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
                    andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsActuacionasistenciaMapper.updateByExampleSelective(recordAA, exampleAA);


        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerGuardias() -> Se ha producido un error al deshacer las guardias",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerGuardias() -> Salida del servicio para deshacer las guardias");

        return facturaciones;
    }

    @Override
    public FacturacionDTO deshacerSOJ(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos SOJs con regularizacion
        try {
            deshacerRegularSOJ(facturacionItem);

            ScsSoj  record = new ScsSoj ();
            record.setFacturado(new String("0"));

            ScsSojExample example = new ScsSojExample();
            example.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
                    andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsSojMapper.updateByExampleSelective(record, example);

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerSOJ() -> Se ha producido un error al deshacer los SOJs",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerSOJ() -> Salida del servicio para deshacer los SOJs");

        return facturaciones;
    }

    @Override
    public FacturacionDTO deshacerEJG(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos SOJs con regularizacion
        try {
            FcsHistoricoHitofactExample fcsHistoricoHitofactExample = new FcsHistoricoHitofactExample();
            fcsHistoricoHitofactExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsHistoricoHitofactMapper.deleteByExample(fcsHistoricoHitofactExample);

            deshacerRegularEJG(facturacionItem);

            ScsEjgWithBLOBs  record = new ScsEjgWithBLOBs ();
            record.setFacturado(new String("0"));

            ScsEjgExample example = new ScsEjgExample();
            example.createCriteria().andIdinstitucionEqualTo(facturacionItem.getIdinstitucion()).
                    andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());

            scsEjgMapper.updateByExampleSelective(record, example);

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerEJG() -> Se ha producido un error al deshacer lOS EJGs",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerEJG() -> Salida del servicio para deshacer los EJGs");

        return facturaciones;
    }

    //FACTURAS CON REGULARIZACION
    @Override
    public FacturacionDTO deshacerRegularTurnosOfi(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos las facturaciones con regularizacion
        try {
            //DESHACER REGULAR_TURNOS_OFI
            List<String> idAcreditaciones = scsAcreditacionExtendsMapper.getIDAcreditaciones(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));
            fcsHistoricoAcreditacionExtendsMapper.deleteByAcreditacion(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()), idAcreditaciones);

            fcsHistoricoProcedimientosExtendsMapper.deleteByProcedimiento(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));

            fcsHistoAcreditacionprocExtendsMapper.deleteByAcreditacionProc(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));

            FcsFactActuaciondesignaExample fcsFactActuaciondesignaExample = new FcsFactActuaciondesignaExample();
            fcsFactActuaciondesignaExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(Integer.valueOf(facturacionItem.getIdfacturacion()));
            fcsFactActuaciondesignaMapper.deleteByExample(fcsFactActuaciondesignaExample);

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerRegularTurnosOfi() -> Se ha producido un error al deshacer la regulacion de turnos de oficio",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerRegularTurnosOfi() -> Salida del servicio para deshacer la regularizacion de turnos de oficio");

        return facturaciones;
    }

    @Override
    public FacturacionDTO deshacerRegularGuardias(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos las facturaciones con regularizacion
        try {
            //DESHACER REGULAR_GUARDIAS
            FcsFactApunteExample fcsFactApunteExample = new FcsFactApunteExample();
            fcsFactApunteExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsFactApunteMapper.deleteByExample(fcsFactApunteExample);

            FcsFactGuardiascolegiadoExample fcsFactGuardiascolegiadoExample = new FcsFactGuardiascolegiadoExample();
            fcsFactGuardiascolegiadoExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsFactGuardiascolegiadoMapper.deleteByExample(fcsFactGuardiascolegiadoExample);

            FcsFactAsistenciaExample fcsFactAsistenciaExample = new FcsFactAsistenciaExample();
            fcsFactAsistenciaExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsFactAsistenciaMapper.deleteByExample(fcsFactAsistenciaExample);

            FcsFactActuacionasistenciaExample fcsFactActuacionasistenciaExample = new FcsFactActuacionasistenciaExample();
            fcsFactActuacionasistenciaExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsFactActuacionasistenciaMapper.deleteByExample(fcsFactActuacionasistenciaExample);

            fcsHistoricoTipoasistcolegioExtendsMapper.delete(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));

            fcsHistoricoTipoactuacionExtendsMapper.delete(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));

            fcsHistoTipoactcostefijoExtendsMapper.delete(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));

            fcsHistoricoHitofactExtendsMapper.delete(String.valueOf(facturacionItem.getIdinstitucion()), String.valueOf(facturacionItem.getIdfacturacion()));

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerRegularGuardias() -> Se ha producido un error al deshacer la regulacion de guardias",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerRegularGuardias() -> Salida del servicio para deshacer la regularizacion de guardias");

        return facturaciones;
    }

    @Override
    public FacturacionDTO deshacerRegularSOJ(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos SOJs con regularizacion
        try {
            //DESHACER REGULAR_SOJ
            FcsHistoricoHitofactExample fcsHistoricoHitofactExample = new FcsHistoricoHitofactExample();
            fcsHistoricoHitofactExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsHistoricoHitofactMapper.deleteByExample(fcsHistoricoHitofactExample);

            FcsFactSojExample fcsFactSojExample = new FcsFactSojExample();
            fcsFactSojExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsFactSojMapper.deleteByExample(fcsFactSojExample);

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerRegularSOJ() -> Se ha producido un error al deshacer la regulacion de SOJs",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerRegularSOJ() -> Salida del servicio para deshacer la regularizacion de SOJs");

        return facturaciones;
    }

    @Override
    public FacturacionDTO deshacerRegularEJG(FcsTrazaErrorEjecucion facturacionItem) {
        FacturacionDTO facturaciones = new FacturacionDTO();
        Error error = new Error();

        //Eliminamos SOJs con regularizacion
        try {
            //DESHACER REGULAR_SOJ
            FcsFactEjgExample fcsFactEjgExample  = new FcsFactEjgExample ();
            fcsFactEjgExample.createCriteria()
                    .andIdinstitucionEqualTo(facturacionItem.getIdinstitucion())
                    .andIdfacturacionEqualTo(facturacionItem.getIdfacturacion());
            fcsFactEjg.deleteByExample(fcsFactEjgExample);

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSZombiServiceImpl.deshacerRegularEJG() -> Se ha producido un error al deshacer la regulacion de EJGs",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        facturaciones.setError(error);
        LOGGER.info(
                "FacturacionSJCSZombiServiceImpl.deshacerRegularEJG() -> Salida del servicio para deshacer la regularizacion de EJGs");

        return facturaciones;
    }
}
