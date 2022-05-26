package org.itcgae.siga.fac.services.impl;

import io.jsonwebtoken.lang.Collections;
import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionKey;
import org.itcgae.siga.db.entities.FacDisquetedevoluciones;
import org.itcgae.siga.db.entities.FacDisquetedevolucionesKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcion;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcionAnu;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcionExample;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisquete;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisqueteExample;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisqueteKey;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.entities.FacHistoricofacturaExample;
import org.itcgae.siga.db.entities.FacLineaabono;
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.entities.FacLineadevoludisqbancoKey;
import org.itcgae.siga.db.entities.FacLineafactura;
import org.itcgae.siga.db.entities.FacLineafacturaExample;
import org.itcgae.siga.db.entities.FacPagoabonoefectivo;
import org.itcgae.siga.db.entities.FacPagoabonoefectivoExample;
import org.itcgae.siga.db.entities.FacPagosporcaja;
import org.itcgae.siga.db.entities.FacPagosporcajaExample;
import org.itcgae.siga.db.entities.FacPagosporcajaKey;
import org.itcgae.siga.db.entities.FacRenegociacion;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionAnuMapper;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.mappers.FacRenegociacionMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetedevolucionesExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineaabonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineafacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacRenegociacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FacturaAccionesHelper {

    private Logger LOGGER = Logger.getLogger(FacturaAccionesHelper.class);

    @Autowired
    private FacPagoabonoefectivoExtendsMapper facPagoabonoefectivoExtendsMapper;

    @Autowired
    private FacAbonoExtendsMapper facAbonoExtendsMapper;

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

    @Autowired
    private FacPagosporcajaExtendsMapper facPagosporcajaExtendsMapper;

    @Autowired
    private FacDisquetecargosExtendsMapper facDisquetecargosExtendsMapper;

    @Autowired
    private FacRenegociacionMapper facRenegociacionMapper;

    @Autowired
    private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

    @Autowired
    private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

    @Autowired
    private AdmContadorExtendsMapper admContadorExtendsMapper;

    @Autowired
    private FacFacturaincluidaendisqueteMapper facFacturaincluidaendisqueteMapper;

    @Autowired
    private CenClienteMapper cenClienteMapper;

    @Autowired
    private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

    @Autowired
    private FacDisquetedevolucionesExtendsMapper facDisquetedevolucionesExtendsMapper;

    @Autowired
    private FacLineadevoludisqbancoMapper facLineadevoludisqbancoMapper;

    @Autowired
    private FacLineafacturaExtendsMapper facLineafacturaExtendsMapper;

    @Autowired
    private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

    @Autowired
    private FacRenegociacionExtendsMapper facRenegociacionExtendsMapper;

    @Autowired
    private FacLineaabonoExtendsMapper facLineaabonoExtendsMapper;

    @Autowired
    private FacFacturacionsuscripcionMapper facFacturacionsuscripcionMapper;

    @Autowired
    private FacFacturacionsuscripcionAnuMapper facFacturacionsuscripcionAnuMapper;

    @Autowired
    private GenDiccionarioMapper genDiccionarioMapper;

    @Autowired
    private FacturacionHelper facturacionHelper;

    @Autowired
    private WSCommons commons;

    @Transactional(rollbackFor = Exception.class)
    public BigDecimal compensarAbono(Long idAbono, String idFactura, BigDecimal importeFactura, AdmUsuarios usuario) {
        FacAbono abono = new FacAbono();
        FacPagoabonoefectivo pagoAbono = new FacPagoabonoefectivo();
        FacPagosporcaja pagoCaja = new FacPagosporcaja();

        FacAbonoKey abonoKey = new FacAbonoKey();
        abonoKey.setIdinstitucion(usuario.getIdinstitucion());
        abonoKey.setIdabono(idAbono);

        // Obtener los datos de pago del abono
        abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);
        BigDecimal cantidadPendiente = abono.getImppendienteporabonar();
        BigDecimal cantidadOriginal = cantidadPendiente;

        if (importeFactura.compareTo(BigDecimal.ZERO) > 0) {
            pagoAbono.setIdinstitucion(abonoKey.getIdinstitucion());
            pagoAbono.setIdabono(abonoKey.getIdabono());

            Long newIdPagoAbono = facPagoabonoefectivoExtendsMapper.getNuevoID(abonoKey.getIdinstitucion().toString(), abonoKey.getIdabono().toString());
            pagoAbono.setIdpagoabono(newIdPagoAbono);

            if (importeFactura.compareTo(cantidadPendiente) > 0) {
                pagoAbono.setImporte(cantidadPendiente.setScale(2, RoundingMode.DOWN));
            } else {
                pagoAbono.setImporte(importeFactura.setScale(2, RoundingMode.DOWN));
            }

            BigDecimal importeCompensado = pagoAbono.getImporte();
            pagoAbono.setFecha(new Date());
            pagoAbono.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

            pagoAbono.setFechamodificacion(new Date());
            pagoAbono.setUsumodificacion(usuario.getIdusuario());

            int resultado = facPagoabonoefectivoExtendsMapper.insert(pagoAbono);

            if (resultado <= 0)
                throw new BusinessException("Error al insertar el abono");

            abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

            if (abono.getEstado().equals(SigaConstants.FAC_ABONO_ESTADO_PAGADO) || cantidadPendiente.compareTo(BigDecimal.ZERO) <= 0)
                throw new BusinessException("facturacionSJCS.abonosSJCS.compensacion.deshabilitado");

            BigDecimal importePendientePorAbonar = abono.getImppendienteporabonar().subtract(importeCompensado);
            BigDecimal importeTotalAbonado = abono.getImptotalabonado().add(importeCompensado);

            abono.setImppendienteporabonar(importePendientePorAbonar.setScale(2, RoundingMode.DOWN));
            abono.setImptotalabonado(importeTotalAbonado.setScale(2, RoundingMode.DOWN));
            abono.setImptotalabonadoefectivo(importeCompensado.setScale(2, RoundingMode.DOWN));

            if (abono.getImppendienteporabonar().doubleValue() <= 0.0) {
                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
            } else if (abono.getIdcuenta() != null) {
                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
            } else {
                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
            }

            resultado = facAbonoExtendsMapper.updateByPrimaryKey(abono);

            if (resultado <= 0)
                throw new BusinessException("Error al actualizar el abono");


            Short newIdPagoPorCaja = facPagosporcajaExtendsMapper.getNuevoID(abonoKey.getIdinstitucion().toString(), idFactura);

            pagoCaja.setIdinstitucion(abonoKey.getIdinstitucion());
            pagoCaja.setIdfactura(idFactura);
            pagoCaja.setIdpagoporcaja(newIdPagoPorCaja);
            pagoCaja.setFecha(new Date());
            pagoCaja.setTarjeta("N");
            pagoCaja.setTipoapunte(SigaConstants.TIPO_APUNTE_COMPENSADO);
            pagoCaja.setObservaciones(getTraduccion("messages.abonos.literal.compensa", usuario.getIdlenguaje()) + " "
                    + abono.getNumeroabono());
            pagoCaja.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

            if (importeFactura.compareTo(cantidadPendiente) > 0) {
                pagoCaja.setImporte(cantidadPendiente.setScale(2, RoundingMode.DOWN));
                cantidadPendiente = BigDecimal.ZERO;
            } else {
                pagoCaja.setImporte(importeFactura.setScale(2, RoundingMode.DOWN));
                cantidadPendiente = cantidadPendiente.subtract(importeFactura);
            }

            pagoCaja.setIdabono(idAbono);
            pagoCaja.setIdpagoabono(pagoAbono.getIdpagoabono());

            pagoCaja.setFechamodificacion(new Date());
            pagoCaja.setUsumodificacion(usuario.getIdusuario());

            resultado = facPagosporcajaExtendsMapper.insert(pagoCaja);

            if (resultado > 0) {
                FacFacturaKey facturaKey = new FacFacturaKey();
                facturaKey.setIdinstitucion(usuario.getIdinstitucion());
                facturaKey.setIdfactura(idFactura);

                FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

                // Actualizar los importes de la factura

                BigDecimal impTotalCompensadoFac = factura.getImptotalcompensado().add(pagoCaja.getImporte());
                BigDecimal impTotalPagadoFac = factura.getImptotalpagado().add(pagoCaja.getImporte());
                BigDecimal impTotalPorPagarFac = factura.getImptotalporpagar().subtract(pagoCaja.getImporte());

                factura.setImptotalcompensado(impTotalCompensadoFac.setScale(2, RoundingMode.DOWN));
                factura.setImptotalpagado(impTotalPagadoFac.setScale(2, RoundingMode.DOWN));
                factura.setImptotalporpagar(impTotalPorPagarFac.setScale(2, RoundingMode.DOWN));

                resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);

                if (resultado <= 0)
                    throw new BusinessException("Error al insertar la factura");

                // Actualizar el estado de la factura
                consultarActNuevoEstadoFactura(factura, true, usuario);

                // Insertar nuevo estado en historico de facturas
                resultado = insertarHistoricoFacParametros(factura.getIdinstitucion(), factura.getIdfactura(), (short) 10, pagoCaja.getIdpagoporcaja(),
                        null, null, null,null, null, abono.getIdabono(), null);

                if (resultado <= 0)
                    throw new BusinessException("Error al insertar la factura el historico");

            }
        }

        return cantidadOriginal.subtract(cantidadPendiente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void pagarAbonoPorCaja(Long idAbono, String idFactura, BigDecimal importe, String observaciones, AdmUsuarios usuario) {
        int resultado;

        FacPagoabonoefectivo pagoAbonoEfectivo = new FacPagoabonoefectivo();
        FacPagosporcaja pagoPorCaja = new FacPagosporcaja();

        FacFactura factura;
        FacAbono abono;

        // Insertamos un nuevo abono efectivo
        pagoAbonoEfectivo.setIdinstitucion(usuario.getIdinstitucion());
        pagoAbonoEfectivo.setIdabono(idAbono);

        // Obtenemos el id del nuevo pago abono a insertar
        Long newIdPagoAbono = facPagoabonoefectivoExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(), idAbono.toString());
        pagoAbonoEfectivo.setIdpagoabono(newIdPagoAbono);

        pagoAbonoEfectivo.setImporte(importe.setScale(2, RoundingMode.DOWN));
        pagoAbonoEfectivo.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

        // Agregamos la nota de acción del abono
        if (!UtilidadesString.esCadenaVacia(observaciones))
            pagoAbonoEfectivo.setObservaciones(observaciones.trim());

        pagoAbonoEfectivo.setFecha(new Date());
        pagoAbonoEfectivo.setFechamodificacion(new Date());
        pagoAbonoEfectivo.setUsumodificacion(usuario.getIdusuario());

        resultado = facPagoabonoefectivoExtendsMapper.insert(pagoAbonoEfectivo);

        if (resultado <= 0)
            throw new BusinessException("Error al actualizar el pago efectivo del abono");

        FacAbonoKey abonoKey = new FacAbonoKey();
        abonoKey.setIdinstitucion(usuario.getIdinstitucion());
        abonoKey.setIdabono(idAbono);

        abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

        BigDecimal impPendientePorAbonar = abono.getImppendienteporabonar().subtract(importe);
        BigDecimal impTotalAbonado = abono.getImptotalabonado().add(importe);
        BigDecimal impTotalAbonadoEfectivo = abono.getImptotalabonadoefectivo().add(importe);

        abono.setImppendienteporabonar(impPendientePorAbonar.setScale(2, RoundingMode.DOWN));
        abono.setImptotalabonado(impTotalAbonado.setScale(2, RoundingMode.DOWN));
        abono.setImptotalabonadoefectivo(impTotalAbonadoEfectivo.setScale(2, RoundingMode.DOWN));

        // Actualizamos el estado del abono
        if (abono.getImppendienteporabonar().compareTo(BigDecimal.ZERO) <= 0) {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
        } else if (abono.getIdcuenta() != null) {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
        } else {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
        }

        resultado = facAbonoExtendsMapper.updateByPrimaryKey(abono);

        if (resultado <= 0)
            throw new BusinessException("Error al actualizar estado e importes del abono");


        // Obtenemos los importes de la factura
        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(usuario.getIdinstitucion());
        facturaKey.setIdfactura(idFactura);
        FacFactura impFactura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        if (impFactura != null) {
            BigDecimal impTotalFactura = impFactura.getImptotal();
            BigDecimal impTotalPagadoFactura = impFactura.getImptotalpagado();

            BigDecimal impPteFac = impTotalFactura.subtract(impTotalPagadoFactura);
            if (impPteFac.compareTo(BigDecimal.ZERO) > 0) {
                pagoPorCaja.setIdinstitucion(usuario.getIdinstitucion());
                pagoPorCaja.setIdabono(idAbono);
                pagoPorCaja.setIdpagoabono(newIdPagoAbono);
                pagoPorCaja.setIdfactura(idFactura);

                // Nuevo id de pago por caja
                Short newIdPagoPorCaja = facPagosporcajaExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(), idFactura);
                pagoPorCaja.setIdpagoporcaja(newIdPagoPorCaja);

                pagoPorCaja.setTarjeta("N");
                pagoPorCaja.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);
                pagoPorCaja.setImporte(importe.setScale(2, RoundingMode.DOWN));

                pagoPorCaja.setFecha(new Date());
                pagoPorCaja.setFechamodificacion(pagoPorCaja.getFecha());
                pagoPorCaja.setUsumodificacion(usuario.getIdusuario());

                resultado = facPagosporcajaExtendsMapper.insert(pagoPorCaja);

                if (resultado <= 0)
                    throw new BusinessException("Error al insertar el pago por caja");

                facturaKey = new FacFacturaKey();
                facturaKey.setIdinstitucion(usuario.getIdinstitucion());
                facturaKey.setIdfactura(idFactura);

                factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

                if (factura == null)
                    throw new BusinessException("No se ha encontrado la factura buscada");

                // Actualizamos los importes de la factura
                BigDecimal impTotalCompensadoFac = factura.getImptotalcompensado().add(importe);
                BigDecimal impTotalPagadoFac = factura.getImptotalpagado().add(importe);
                BigDecimal impTotalPorPagarFac = factura.getImptotalporpagar().subtract(importe);

                factura.setImptotalcompensado(impTotalCompensadoFac.setScale(2, RoundingMode.DOWN));
                factura.setImptotalpagado(impTotalPagadoFac.setScale(2, RoundingMode.DOWN));
                factura.setImptotalporpagar(impTotalPorPagarFac.setScale(2, RoundingMode.DOWN));

                resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);

                if (resultado <= 0)
                    throw new BusinessException("Error al actualizar los importes de la factura");

                // Actualizamos el nuevo estado de la factura
                consultarActNuevoEstadoFactura(factura, true, usuario);
            } else {
                facturaKey = new FacFacturaKey();
                facturaKey.setIdinstitucion(usuario.getIdinstitucion());
                facturaKey.setIdfactura(idFactura);

                factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

                if (factura == null)
                    throw new BusinessException("No se ha encontrado la factura buscada");

                // Actualizamos el importe compensado
                BigDecimal impTotalCompensadoFac = factura.getImptotalcompensado().add(importe);
                factura.setImptotalcompensado(impTotalCompensadoFac.setScale(2, RoundingMode.DOWN));

                resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);

                if (resultado <= 0)
                    throw new BusinessException("Error al actualizar los importes de la factura");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public int eliminarUltimoPagoPorCaja(Long idAbono, AdmUsuarios usuario) {
        int resultado;
        FacAbonoKey abonoKey = new FacAbonoKey();
        abonoKey.setIdinstitucion(usuario.getIdinstitucion());
        abonoKey.setIdabono(idAbono);

        FacAbono abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

        if (abono.getIdcuenta() != null)
            throw new BusinessException("Sólo se puede eliminar si el último estado es Abono por caja");

        // Obtenemos los pagos realizados en una factura
        FacPagoabonoefectivoExample pagoabonoefectivoExample = new FacPagoabonoefectivoExample();
        pagoabonoefectivoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                .andIdabonoEqualTo(idAbono);
        pagoabonoefectivoExample.setOrderByClause("idpagoabono");

        List<FacPagoabonoefectivo> pagoabonoefectivoList = facPagoabonoefectivoExtendsMapper.selectByExample(pagoabonoefectivoExample);
        if (pagoabonoefectivoList == null || pagoabonoefectivoList.isEmpty())
            throw new BusinessException("No existen abonos anteriores");

        FacPagoabonoefectivo ultimoPagoAbonoEfectivo = pagoabonoefectivoList.get(pagoabonoefectivoList.size() - 1);

        // Comprobamos que no se trate de una compensación
        FacPagosporcajaExample pagoCajaExample = new FacPagosporcajaExample();
        pagoCajaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                .andIdabonoEqualTo(idAbono)
                .andIdpagoabonoEqualTo(ultimoPagoAbonoEfectivo.getIdpagoabono());

        List<FacPagosporcaja> pagosporcajasList = facPagosporcajaExtendsMapper.selectByExample(pagoCajaExample);

        if (pagosporcajasList != null && !pagosporcajasList.isEmpty())
            throw new BusinessException("Sólo se puede eliminar si el último estado es Abono por caja");

        // Actualizamos los importes del abono
        BigDecimal impPendientePorAbonar = abono.getImppendienteporabonar().add(ultimoPagoAbonoEfectivo.getImporte());
        BigDecimal impTotalAbonado = abono.getImptotalabonado().subtract(ultimoPagoAbonoEfectivo.getImporte());
        BigDecimal impTotalAbonadoEfectivo = abono.getImptotalabonadoefectivo().subtract(ultimoPagoAbonoEfectivo.getImporte());

        abono.setImppendienteporabonar(impPendientePorAbonar.setScale(2, RoundingMode.DOWN));
        abono.setImptotalabonado(impTotalAbonado.setScale(2, RoundingMode.DOWN));
        abono.setImptotalabonadoefectivo(impTotalAbonadoEfectivo.setScale(2, RoundingMode.DOWN));

        // Actualizamos el estado del abono
        if (abono.getImppendienteporabonar().compareTo(BigDecimal.ZERO) <= 0) {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
        } else if (abono.getIdcuenta() != null) {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
        } else {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
        }

        resultado = facAbonoExtendsMapper.updateByPrimaryKey(abono);

        if (resultado <= 0)
            throw new BusinessException("Error al actualizar los importes del abono");

        resultado = facPagoabonoefectivoExtendsMapper.deleteByPrimaryKey(ultimoPagoAbonoEfectivo);

        if (resultado <= 0)
            throw new BusinessException("Error al eliminar el abono por caja");

        return resultado;
    }

    @Transactional(rollbackFor = Exception.class)
    public int renegociarAbono(Long idAbono, Short idCuenta, AdmUsuarios usuario, String modo) {
        int resultado;
        FacAbonoKey abonoKey = new FacAbonoKey();
        abonoKey.setIdinstitucion(usuario.getIdinstitucion());
        abonoKey.setIdabono(idAbono);
        
        CenCuentasbancariasKey cuentaBancariaKey;
        CenCuentasbancarias cuentaBancaria;

        FacAbono abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

        if (abono.getEstado().equals(SigaConstants.FAC_ABONO_ESTADO_PAGADO))
            throw new BusinessException("facturacionSJCS.abonosSJCS.error.renegociar.estado");

        if (abono.getImptotalneto() == null || abono.getImptotalneto().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("facturacionSJCS.abonosSJCS.error.importeTotalCero");

        if (abono.getImppendienteporabonar() == null || abono.getImppendienteporabonar().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("facturacionSJCS.abonosSJCS.error.importePendienteCero");
        
        if( modo == null || modo.isEmpty()) {
        	if (idCuenta == null)
                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
            else
                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);

            if (abono.getEstado() == SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA) {
                abono.setIdcuenta(null);
            } else if (abono.getEstado() == SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO) {
                abono.setIdcuenta(idCuenta);

                CenCuentasbancariasKey bancoKey = new CenCuentasbancarias();
                bancoKey.setIdinstitucion(usuario.getIdinstitucion());
                bancoKey.setIdcuenta(idCuenta);
                bancoKey.setIdpersona(abono.getIdpersona());

                CenCuentasbancarias banco = cenCuentasbancariasExtendsMapper.selectByPrimaryKey(bancoKey);

                if (banco == null)
                    throw new BusinessException("facturacionSJCS.abonosSJCS.error.bancoAsociado");
            }
        }else {
            switch (modo) {
            case "caja":
            	 abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
            	 abono.setIdcuenta(null);
                break;
            case "cuenta_activa":
            	
            	cuentaBancaria = getCuentaActivaUnica(abono.getIdpersona(), usuario.getIdinstitucion());

                if (cuentaBancaria != null) {
                    idCuenta = cuentaBancaria.getIdcuenta();
                } else {
                    throw new BusinessException("facturacionSJCS.abonosSJCS.error.bancoAsociado");                   
                }
                
                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
                abono.setIdcuenta(idCuenta);
                
                break;
            case "cuentaFactura_activa_masClientes":
                cuentaBancaria = getCuentaActivaServiciosActivos(abono.getIdpersona(), usuario.getIdinstitucion());

                if (cuentaBancaria != null) {
                    idCuenta = cuentaBancaria.getIdcuenta();
                } else {
                    throw new BusinessException("facturacionSJCS.abonosSJCS.error.bancoAsociado");
                }

                abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
                abono.setIdcuenta(idCuenta);
                break;
                
            default:
                throw new BusinessException("El modo de renegociación es incorrecto");
                
            }

        }

        
        resultado = facAbonoExtendsMapper.updateByPrimaryKey(abono);

        if (resultado <= 0)
            throw new BusinessException("Error al actualizar los datos del abono");

        return resultado;
    }

    @Transactional(rollbackFor = Exception.class)
    public void renegociarFactura(String modo, String idFactura, Short idCuenta, Date fecha, String observaciones, AdmUsuarios usuario) throws Exception {
        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(usuario.getIdinstitucion());
        facturaKey.setIdfactura(idFactura);

        Short nuevoEstado = null;
        Short idFormaPago = null;
        FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        boolean actualizarFacturaEnDisco = false;

        // Comprobamos primero si el estado de la factura es correcto para renegociación
        if (!Collections.arrayToList(new String[]{
                SigaConstants.ESTADO_FACTURA_CAJA,
                SigaConstants.ESTADO_FACTURA_BANCO,
                SigaConstants.ESTADO_FACTURA_DEVUELTA
        }).contains(factura.getEstado().toString()))
            throw new BusinessException("facturacionPyS.facturas.error.estado");

        if (factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_DEVUELTA))
                && UtilidadesString.esCadenaVacia(factura.getComisionidfactura()))
            actualizarFacturaEnDisco = true;

        CenCuentasbancariasKey cuentaBancariaKey;
        CenCuentasbancarias cuentaBancaria;
        switch (modo) {
            case "caja":
                if (factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA)))
                    throw new BusinessException("facturacionPyS.facturas.error.caja");

                idFormaPago = Short.parseShort(SigaConstants.TIPO_FORMAPAGO_METALICO);
                nuevoEstado = Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA);
                idCuenta = null;
                break;

            case "cuentaFactura_activa":
                if (factura.getIdcuenta() == null)
                    throw new BusinessException("facturacionPyS.facturas.error.cuentaActiva");

                idFormaPago = Short.parseShort(SigaConstants.TIPO_FORMAPAGO_FACTURA);
                nuevoEstado = Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO);

                cuentaBancariaKey = new CenCuentasbancariasKey();
                cuentaBancariaKey.setIdinstitucion(usuario.getIdinstitucion());
                cuentaBancariaKey.setIdpersona(factura.getIdpersona());
                cuentaBancariaKey.setIdcuenta(factura.getIdcuenta());

                // Obtener la Cuenta activa única
                cuentaBancaria = cenCuentasbancariasExtendsMapper.selectByPrimaryKey(cuentaBancariaKey);

                if (cuentaBancaria != null && cuentaBancaria.getIdcuenta() != null
                        && cuentaBancaria.getFechabaja() == null && (cuentaBancaria.getAbonocargo().equals("C")
                        || cuentaBancaria.getAbonocargo().equals("T")))
                    idCuenta = cuentaBancaria.getIdcuenta();
                else
                    throw new BusinessException("facturacionSJCS.abonosSJCS.error.bancoAsociado");
                break;

            case "cuentaFactura_activa_masClientes":

                idFormaPago = Short.parseShort(SigaConstants.TIPO_FORMAPAGO_FACTURA);
                nuevoEstado = Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO);

                if (factura.getIdcuenta() != null) {
                    cuentaBancariaKey = new CenCuentasbancariasKey();
                    cuentaBancariaKey.setIdinstitucion(usuario.getIdinstitucion());
                    cuentaBancariaKey.setIdpersona(factura.getIdpersona());
                    cuentaBancariaKey.setIdcuenta(factura.getIdcuenta());

                    cuentaBancaria = cenCuentasbancariasExtendsMapper.selectByPrimaryKey(cuentaBancariaKey);

                    if (cuentaBancaria != null && cuentaBancaria.getIdcuenta() != null
                            && cuentaBancaria.getFechabaja() == null && (cuentaBancaria.getAbonocargo().equals("C")
                            || cuentaBancaria.getAbonocargo().equals("T")))
                        idCuenta = cuentaBancaria.getIdcuenta();
                    else
                        throw new BusinessException("facturacionSJCS.abonosSJCS.error.bancoAsociado");
                } else {
                    cuentaBancaria = getCuentaActivaUnica(factura.getIdpersona(), usuario.getIdinstitucion());

                    if (cuentaBancaria != null) {
                        idCuenta = cuentaBancaria.getIdcuenta();
                    } else {
                        cuentaBancaria = getCuentaActivaServiciosActivos(factura.getIdpersona(), usuario.getIdinstitucion());

                        if (cuentaBancaria != null) {
                            idCuenta = cuentaBancaria.getIdcuenta();
                        } else {
                            throw new BusinessException("facturacionSJCS.abonosSJCS.error.bancoAsociado");
                        }
                    }
                }
                break;

            case "otroBanco":
                idFormaPago = Short.parseShort(SigaConstants.TIPO_FORMAPAGO_FACTURA);
                nuevoEstado = Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO);

                if (idCuenta == null)
                    throw new BusinessException("facturacionPyS.facturas.error.otroBancoAusente");
                break;

            default:
                throw new BusinessException("El modo de renegociación es incorrecto");
        }

        // Insertamos un nuevo registro en FAC_RENEGOCIACION
        FacRenegociacion renegociacion = new FacRenegociacion();

        if (!UtilidadesString.esCadenaVacia(observaciones))
            renegociacion.setComentario(observaciones.trim());

        renegociacion.setIdfactura(idFactura);
        renegociacion.setIdinstitucion(usuario.getIdinstitucion());
        renegociacion.setIdpersona(factura.getIdpersona());
        renegociacion.setImporte(factura.getImptotalporpagar());
        renegociacion.setFechamodificacion(new Date());
        renegociacion.setUsumodificacion(usuario.getIdusuario());

        // Fecha de renegociación de la petición
        if (fecha != null) {
            renegociacion.setFecharenegociacion(fecha);
        } else {
            renegociacion.setFecharenegociacion(new Date());
        }

        // Actualizamos el idCuenta
        if (idCuenta != null) {
            renegociacion.setIdcuenta(idCuenta);
            factura.setIdcuenta(idCuenta);
        } else {
            factura.setIdcuenta(null);
        }

        Short newIdRenegociacion = facRenegociacionExtendsMapper.getNuevoID(usuario.getIdinstitucion(), idFactura);
        renegociacion.setIdrenegociacion(newIdRenegociacion);

        int resultado;

        // Insertamos la renegociación
        resultado = facRenegociacionMapper.insertSelective(renegociacion);

        if (resultado <= 0)
            throw new BusinessException("Error al insertar la renegociación");

        // Actualizamos el disquete
        if (actualizarFacturaEnDisco)
            actualizarRenegociacionEnDisquete(idFactura, newIdRenegociacion, usuario);

        // Actualizamos el estado y forma de pago de la factura
        factura.setEstado(nuevoEstado);
        factura.setIdformapago(idFormaPago);
        resultado = actualizarFacturaRenegociacion(factura, usuario);

        if (resultado <= 0)
            throw new BusinessException("Error al actualizar la factura");


        // Actualizamos el historico de la factura
        resultado = insertarHistoricoFacParametros(usuario.getIdinstitucion(), idFactura, (short) 7, null, null,
                null, null, null, newIdRenegociacion, null, null);

        if (resultado <= 0)
            throw new BusinessException("No se ha insertado en el histórico de la facturación");
    }

    private CenCuentasbancarias getCuentaActivaUnica(Long idPersona, Short idInstitucion) {
        List<CenCuentasbancarias> cuentas = getCuentasActivas(idPersona, idInstitucion);

        if (cuentas == null || cuentas.size() != 1)
            return null;
        else
            return cuentas.get(0);
    }

    private CenCuentasbancarias getCuentaActivaServiciosActivos(Long idPersona, Short idInstitucion) {
        return cenCuentasbancariasExtendsMapper.getCuentaActivaServiciosActivos(idInstitucion, idPersona);
    }

    private List<CenCuentasbancarias> getCuentasActivas(Long idPersona, Short idInstitucion) {
        CenCuentasbancariasExample cuentaExample = new CenCuentasbancariasExample();
        cuentaExample.createCriteria().andIdpersonaEqualTo(idPersona)
                .andIdinstitucionEqualTo(idInstitucion)
                .andFechabajaIsNull().andAbonocargoIn(Arrays.asList("T", "C"));

        return cenCuentasbancariasExtendsMapper.selectByExample(cuentaExample);
    }

    @Transactional(rollbackFor = Exception.class)
    public int pagarFacturaPorCaja(String idFactura, BigDecimal importe, String observaciones, Date fechaPago, AdmUsuarios usuario) {
        int resultado;

        // Insertamos un nuevo pago por caja
        FacPagosporcaja pagosCaja = new FacPagosporcaja();

        pagosCaja.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);
        pagosCaja.setTarjeta("N");
        pagosCaja.setFecha(fechaPago);
        pagosCaja.setIdfactura(idFactura);
        pagosCaja.setIdinstitucion(usuario.getIdinstitucion());

        Short newIdPagoCaja = facPagosporcajaExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(), idFactura);
        pagosCaja.setIdpagoporcaja(newIdPagoCaja);

        if (importe.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("facturacionSJCS.abonosSJCS.error.importeCero");

        pagosCaja.setImporte(importe);

        if (!UtilidadesString.esCadenaVacia(observaciones))
            pagosCaja.setObservaciones(observaciones.trim());

        // Actualizamos los importes de la factura

        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(usuario.getIdinstitucion());
        facturaKey.setIdfactura(idFactura);

        FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        if (factura == null)
            throw new BusinessException("No se ha encontrado la factura");

        BigDecimal impTotalPagadoPorCaja = factura.getImptotalpagadoporcaja().add(importe);
        BigDecimal impTotalPagadoSoloCaja = factura.getImptotalpagadosolocaja().add(importe);
        BigDecimal impTotalPagado = factura.getImptotalpagado().add(importe);
        BigDecimal impTotalPorPagar = factura.getImptotalporpagar().subtract(importe);

        factura.setImptotalpagadoporcaja(impTotalPagadoPorCaja.setScale(2, RoundingMode.DOWN));
        factura.setImptotalpagadosolocaja(impTotalPagadoSoloCaja.setScale(2, RoundingMode.DOWN));
        factura.setImptotalpagado(impTotalPagado.setScale(2, RoundingMode.DOWN));
        factura.setImptotalporpagar(impTotalPorPagar.setScale(2, RoundingMode.DOWN));

        if (factura.getImptotalporpagar().compareTo(BigDecimal.ZERO) < 0)
            throw new BusinessException("facturacionPyS.facturas.error.importeMenorAImportePendiente");

        if (factura.getFechaemision().compareTo(fechaPago) > 0)
            throw new BusinessException("facturacionPyS.facturas.error.fechaPosteriorFechaEmision");


        pagosCaja.setFechamodificacion(new Date());
        pagosCaja.setUsumodificacion(usuario.getIdusuario());
        resultado = facPagosporcajaExtendsMapper.insertSelective(pagosCaja);
        if (resultado <= 0)
            throw new BusinessException("Error al insertar el pago de la factura");

        resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);
        if (resultado <= 0)
            throw new BusinessException("Error al actualizar los importes de la factura");

        // Actualizamos el importe de la factura
        consultarActNuevoEstadoFactura(factura, true, usuario);

        resultado = insertarHistoricoFacParametros(usuario.getIdinstitucion(), idFactura, (short) 4, newIdPagoCaja,
                null, null, null, null, null, null, null);

        if (resultado <= 0)
            throw new BusinessException("Error al insertar el histórico de la facturación");

        return resultado;
    }

    @Transactional(rollbackFor = Exception.class)
    public int eliminarUltimoCobroPorCaja(String idFactura, AdmUsuarios usuario) {

        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(usuario.getIdinstitucion());
        facturaKey.setIdfactura(idFactura);

        FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        if (factura == null)
            throw new BusinessException("No se ha encontrado la factura");

        // Ultima entrada del historico de facturas
        FacHistoricofacturaExample example = new FacHistoricofacturaExample();
        example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                .andIdfacturaEqualTo(idFactura);
        example.setOrderByClause("IDHISTORICO");
        List<FacHistoricofactura> facHistoricoList = facHistoricofacturaExtendsMapper.selectByExample(example);

        if (facHistoricoList == null || facHistoricoList.size() < 2)
            throw new BusinessException("No existe información en el histórico de facturas");

        FacHistoricofactura facHistoricoAnterior = facHistoricoList.get(facHistoricoList.size() - 2);
        FacHistoricofactura facHistoricoEliminar = facHistoricoList.get(facHistoricoList.size() - 1);

        FacPagosporcajaKey pagosporcajaKey = new FacPagosporcajaKey();
        pagosporcajaKey.setIdinstitucion(usuario.getIdinstitucion());
        pagosporcajaKey.setIdfactura(idFactura);
        pagosporcajaKey.setIdpagoporcaja(facHistoricoEliminar.getIdpagoporcaja());

        // Devolvemos la factura al estado anterior
        factura.setImptotalpagadoporcaja(facHistoricoAnterior.getImptotalpagadoporcaja().setScale(2, RoundingMode.DOWN));
        factura.setImptotalpagadosolocaja(facHistoricoAnterior.getImptotalpagadosolocaja().setScale(2, RoundingMode.DOWN));
        factura.setImptotalpagado(facHistoricoAnterior.getImptotalpagado().setScale(2, RoundingMode.DOWN));
        factura.setImptotalporpagar(facHistoricoAnterior.getImptotalporpagar().setScale(2, RoundingMode.DOWN));

        factura.setFechamodificacion(new Date());
        factura.setUsumodificacion(usuario.getIdusuario());
        factura.setEstado(facHistoricoAnterior.getEstado());

        int resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);
        if (resultado <= 0)
            throw new BusinessException("Error al actualizar la factura");

        resultado = facHistoricofacturaExtendsMapper.deleteByPrimaryKey(facHistoricoEliminar);
        if (resultado <= 0)
            throw new BusinessException("Error al eliminar el último estado del histórico");

        resultado = facPagosporcajaExtendsMapper.deleteByPrimaryKey(pagosporcajaKey);
        if (resultado <= 0)
            throw new BusinessException("Error al eliminar el último cobro por caja");


        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void anularFactura(String idFactura, Date fecha, String observaciones, AdmUsuarios usuario) {
        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(usuario.getIdinstitucion());
        facturaKey.setIdfactura(idFactura);

        FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        if (factura == null)
            throw new BusinessException("Error al obtener las facturas");

       if (!factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_ANULADA))) {
            insertarAbono(factura, fecha, observaciones, usuario);
       }
    }

    private int insertarAbono(FacFactura factura, Date fecha, String observaciones, AdmUsuarios usuario) {
        int resultado;

        FacAbono abono = new FacAbono();

        abono.setObservaciones(getTraduccion("messages.informes.abono.mensajeFactura", usuario.getIdlenguaje()) + " " + factura.getNumerofactura());

        if (!UtilidadesString.esCadenaVacia(observaciones))
            abono.setMotivos(observaciones.trim());

        Long newIdAbono = facAbonoExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString());
        abono.setIdabono(newIdAbono);

        abono.setIdinstitucion(usuario.getIdinstitucion());
        abono.setIdabono(newIdAbono);

        // Escribir el motivo
        //abono.setMotivos(getTraduccion("facturacion.altaAbonos.literal.devolucion", usuario.getIdlenguaje()) + " " + "motivo");
        abono.setFecha(fecha);
        abono.setContabilizada(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);
        abono.setIdfactura(factura.getIdfactura());

        abono.setIdpersona(factura.getIdpersona());
        abono.setIdpersonadeudor(factura.getIdpersonadeudor());
        abono.setUsumodificacion(usuario.getIdusuario());
        abono.setFechamodificacion(new Date());


        // Actualizar importes de la factura
        BigDecimal importeCompensado = factura.getImptotalporpagar();
        BigDecimal importeTotal = factura.getImptotal();

        // Obtenemos el siguiente número de abono
        FacSeriefacturacionKey serieKey = new FacSeriefacturacionKey();
        serieKey.setIdinstitucion(usuario.getIdinstitucion());
        serieKey.setIdseriefacturacion(factura.getIdseriefacturacion());
        FacSeriefacturacion serie = facSeriefacturacionExtendsMapper.selectByPrimaryKey(serieKey);

        String nuevoNumeroAbono = facAbonoExtendsMapper.getNuevoNumeroAbono(usuario.getIdinstitucion().toString(), serie.getIdcontadorAbonos());
        abono.setNumeroabono(nuevoNumeroAbono);

        // Incrementamos el contador del abono
        incrementarContador(serie.getIdcontadorAbonos(), usuario.getIdinstitucion());

        // Insertamos el abono
        resultado = facAbonoExtendsMapper.insertSelective(abono);
        if (resultado <= 0)
            throw new BusinessException("Error en insertar abono Devolucion");

        // Obtengo el abono insertado
        FacAbonoKey abonoKey = new FacAbonoKey();
        abonoKey.setIdinstitucion(usuario.getIdinstitucion());
        abonoKey.setIdabono(newIdAbono);

        abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

        if (importeCompensado.compareTo(BigDecimal.ZERO) > 0) {
            // Insertamos el pago abono efectivo
            FacPagoabonoefectivo pagoAbono = new FacPagoabonoefectivo();
            pagoAbono.setIdinstitucion(usuario.getIdinstitucion());
            pagoAbono.setIdabono(newIdAbono);

            Long newIdPagoAbono = facPagoabonoefectivoExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(), newIdAbono.toString());
            pagoAbono.setIdpagoabono(newIdPagoAbono);

            pagoAbono.setImporte(importeCompensado.setScale(2, RoundingMode.DOWN));
            pagoAbono.setFecha(fecha);
            pagoAbono.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

            pagoAbono.setFechamodificacion(new Date());
            pagoAbono.setUsumodificacion(usuario.getIdusuario());
            resultado = facPagoabonoefectivoExtendsMapper.insertSelective(pagoAbono);
            if (resultado <= 0)
                throw new BusinessException("Error al insertar el pago abono efectivo");

            // Insertamos el pago abono por caja
            FacPagosporcaja pagoCaja = new FacPagosporcaja();
            pagoCaja.setIdinstitucion(usuario.getIdinstitucion());
            pagoCaja.setIdfactura(factura.getIdfactura());

            Short newIdPagoCaja = facPagosporcajaExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(), factura.getIdfactura());
            pagoCaja.setIdpagoporcaja(newIdPagoCaja);

            pagoCaja.setFecha(fecha);
            pagoCaja.setTarjeta("N");
            pagoCaja.setTipoapunte(SigaConstants.TIPO_APUNTE_COMPENSADO);
            pagoCaja.setObservaciones(getTraduccion("messages.abonos.literal.compensa", usuario.getIdlenguaje()) + " " + nuevoNumeroAbono);
            pagoCaja.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);
            pagoCaja.setImporte(importeCompensado.setScale(2, RoundingMode.DOWN));
            pagoCaja.setIdabono(newIdAbono);
            pagoCaja.setIdpagoabono(newIdPagoAbono);

            pagoCaja.setFechamodificacion(new Date());
            pagoCaja.setUsumodificacion(usuario.getIdusuario());
            resultado = facPagosporcajaExtendsMapper.insertSelective(pagoCaja);
            if (resultado <= 0)
                throw new BusinessException("Error al insertar el pago abono por caja");

            BigDecimal impTotalCompensado = factura.getImptotalcompensado().add(importeCompensado);
            BigDecimal impTotalPagado = factura.getImptotalpagado().add(importeCompensado);
            BigDecimal impTotalPorPagar = factura.getImptotalporpagar().subtract(importeCompensado);

            factura.setImptotalcompensado(impTotalCompensado.setScale(2, RoundingMode.DOWN));
            factura.setImptotalpagado(impTotalPagado.setScale(2, RoundingMode.DOWN));
            factura.setImptotalporpagar(impTotalPorPagar.setScale(2, RoundingMode.DOWN));

            factura.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_ANULADA));

            resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);
            if (resultado <= 0)
                throw new BusinessException("Error al actualizar los importes de la factura");

            // Mover las suscripciones a la tabla de suscripciones anuladas
            moverFacturacionSuscripcion(factura.getIdfactura(), usuario.getIdinstitucion());
        }



        // Líneas de factura
        FacLineafacturaExample lineafacturaExample = new FacLineafacturaExample();
        lineafacturaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                .andIdfacturaEqualTo(factura.getIdfactura());

        List<FacLineafactura> lineafacturas = facLineafacturaExtendsMapper.selectByExample(lineafacturaExample);

        for (FacLineafactura lineafactura: lineafacturas) {
            FacLineaabono lineaabono = new FacLineaabono();
            lineaabono.setIdinstitucion(lineafactura.getIdinstitucion());
            lineaabono.setIdabono(newIdAbono);
            lineaabono.setCantidad(lineafactura.getCantidad());
            lineaabono.setDescripcionlinea(lineafactura.getDescripcion());
            lineaabono.setIdfactura(factura.getIdfactura());
            lineaabono.setIva(lineafactura.getIva());
            lineaabono.setLineafactura(lineafactura.getNumerolinea());

            Long newIdLineaAbono = facLineaabonoExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(), newIdAbono.toString());
            lineaabono.setNumerolinea(newIdLineaAbono);

            lineaabono.setPreciounitario(lineafactura.getPreciounitario());

            lineaabono.setUsumodificacion(usuario.getIdusuario());
            lineaabono.setFechamodificacion(new Date());
            resultado = facLineaabonoExtendsMapper.insertSelective(lineaabono);
            if (resultado <= 0)
                throw new BusinessException("Error al insertar la línea del abono");
        }

        // Actualizamos los importes del abono
        abono.setImptotal(importeTotal);
        abono.setImppendienteporabonar(importeTotal.subtract(importeCompensado).setScale(2, RoundingMode.DOWN));
        abono.setImptotalabonado(importeCompensado.setScale(2, RoundingMode.DOWN));
        abono.setImptotalabonadoefectivo(BigDecimal.ZERO);
        abono.setImptotalabonadoporbanco(BigDecimal.ZERO);
        abono.setImptotaliva(factura.getImptotaliva());
        abono.setImptotalneto(factura.getImptotalneto());

        // Actualizamos el estado del abono
        if (importeTotal.subtract(importeCompensado).compareTo(BigDecimal.ZERO) <= 0) {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
        } else if (abono.getIdcuenta() != null) {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
        } else {
            abono.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
        }

        facAbonoExtendsMapper.updateByPrimaryKey(abono);
        if (resultado <= 0)
            throw new BusinessException("Error al actualizar el abono");

        factura.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_ANULADA));
        resultado = facFacturaExtendsMapper.updateByPrimaryKey(factura);
        if (resultado <= 0)
            throw new BusinessException("Error al actualizar el estado de la factura");

        // Añadimos al historico de la factura

        if (!UtilidadesString.esCadenaVacia(factura.getComisionidfactura())) {
            resultado = insertarHistoricoFacParametros(factura.getIdinstitucion(), factura.getIdfactura(), (short) 9,
                    null, null, null, null, null, null, null, factura.getComisionidfactura());
        } else {
            resultado = insertarHistoricoFacParametros(factura.getIdinstitucion(), factura.getIdfactura(), (short) 8,
                    null, null, null, null, null, null, newIdAbono, null);
        }

        if (resultado <= 0)
            throw new BusinessException("Error al insertar el histórico de la factura");

        try {
            //TODO: Si no se produce error regeneramos el pdf
            facturacionHelper.generarPdfFacturaFirmada(factura.getIdfactura(),
                    factura.getIdinstitucion().toString(), Boolean.TRUE, usuario);
        } catch (Exception e) {
            LOGGER.warn("Excepcion en la generación del informe de factura");
        }

        // Mover las suscripciones a la tabla de suscripciones anuladas
        moverFacturacionSuscripcion(factura.getIdfactura(), usuario.getIdinstitucion());

        return resultado;
    }

    private void moverFacturacionSuscripcion(String idFactura, Short idInstitucion) {
        FacFacturacionsuscripcionExample suscripcionExample = new FacFacturacionsuscripcionExample();
        suscripcionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                .andIdfacturaEqualTo(idFactura);

        List<FacFacturacionsuscripcion> suscripciones = facFacturacionsuscripcionMapper.selectByExample(suscripcionExample);
        for (FacFacturacionsuscripcion suscripcion: suscripciones) {
            FacFacturacionsuscripcionAnu suscripcionAnulada = new FacFacturacionsuscripcionAnu();
            BeanUtils.copyProperties(suscripcion, suscripcionAnulada);
            facFacturacionsuscripcionAnuMapper.insertSelective(suscripcionAnulada);
        }

        facFacturacionsuscripcionMapper.deleteByExample(suscripcionExample);
    }

    private void incrementarContador(String idContador, Short idInstitucion) {
        AdmContadorKey contadorKey = new AdmContadorKey();
        contadorKey.setIdinstitucion(idInstitucion);
        contadorKey.setIdcontador(idContador);

        AdmContador contador = admContadorExtendsMapper.selectByPrimaryKey(contadorKey);
        contador.setContador(contador.getContador() + 1);
        admContadorExtendsMapper.updateByPrimaryKey(contador);
    }

    private int actualizarFacturaRenegociacion(FacFactura facturaToSave, AdmUsuarios usuario) {
        facturaToSave.setFechamodificacion(new Date());
        facturaToSave.setUsumodificacion(usuario.getIdusuario());
        int resultado = facFacturaExtendsMapper.updateByPrimaryKey(facturaToSave);
        try {
            //TODO: Si no se produce error regeneramos el pdf
            facturacionHelper.generarPdfFacturaFirmada(facturaToSave.getIdfactura(),
                    facturaToSave.getIdinstitucion().toString(), Boolean.TRUE, usuario);
        } catch (Exception e) {
            LOGGER.warn("Excepcion en la generación del informe de factura");
        }

        return resultado;
    }

    private String consultarActNuevoEstadoFactura(FacFactura facturaBean, boolean actualizar, AdmUsuarios usuario) throws BusinessException {

        String nuevoEstado = "";

        try {
            double cero = 0;
            if (facturaBean.getImptotalporpagar().doubleValue() <= cero) {
                // Est� pagada
                nuevoEstado = SigaConstants.ESTADO_FACTURA_PAGADA;
            } else {
                // Pendiente de pago
                // BNS
                String ultimoFicheroBancarioDeFactura = facDisquetecargosExtendsMapper.getRenegociacionFactura(facturaBean.getIdinstitucion().toString(), facturaBean.getIdfactura());
                if (ultimoFicheroBancarioDeFactura == null) {
                    if (facturaBean.getIdcuenta() == null && facturaBean.getIdcuentadeudor() == null) {
                        // pendiente pago por caja
                        nuevoEstado = SigaConstants.ESTADO_FACTURA_CAJA;
                    } else {
                        // La factura esta pendiente de enviar a banco
                        nuevoEstado = SigaConstants.ESTADO_FACTURA_BANCO;
                    }
                } else {
                    if (ultimoFicheroBancarioDeFactura.trim().equals("")) {
                        //La factura esta devuelta y pendiente de renegociacion
                        nuevoEstado = SigaConstants.ESTADO_FACTURA_DEVUELTA;
                    } else {
                        // La factura esta renegociada y pendiente de enviar a banco
                        if (facturaBean.getIdcuenta() == null && facturaBean.getIdcuentadeudor() == null) {
                            // pendiente pago por caja
                            nuevoEstado = SigaConstants.ESTADO_FACTURA_CAJA;
                        } else {
                            // La factura esta pendiente de enviar a banco
                            nuevoEstado = SigaConstants.ESTADO_FACTURA_BANCO;
                        }
                    }
                }
            }

            if (actualizar) {

                FacFacturaKey facFacturaKey = new FacFacturaKey();
                facFacturaKey.setIdinstitucion(facturaBean.getIdinstitucion());
                facFacturaKey.setIdfactura(facturaBean.getIdfactura());

                FacFactura facturaLocalBean = facFacturaExtendsMapper.selectByPrimaryKey(facFacturaKey);

                if (null != facturaLocalBean) {
                    facturaLocalBean.setEstado(Short.valueOf(nuevoEstado));
                    if (facFacturaExtendsMapper.updateByPrimaryKeySelective(facturaLocalBean) == 0) {
                        throw new BusinessException("Error al actualizar el estado");
                    }
                    try {
                        //TODO: Si no se produce error regeneramos el pdf
                        facturacionHelper.generarPdfFacturaFirmada(facturaLocalBean.getIdfactura(),
                                facturaLocalBean.getIdinstitucion().toString(), Boolean.TRUE, usuario);
                    } catch (Exception e) {
                        LOGGER.warn("Excepcion en la generación del informe de factura");
                    }
                }
            }

        } catch (Exception e) {
            throw new BusinessException("Excepcion en la consulta/actualizacion del estado de la factura.", e);
        }

        return nuevoEstado;
    }

    private void actualizarRenegociacionEnDisquete(String idFactura, Short idRenegociacion, AdmUsuarios usuario) {
        FacFacturaincluidaendisqueteExample example = new FacFacturaincluidaendisqueteExample();
        example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                .andIdfacturaEqualTo(idFactura)
                .andIdrenegociacionIsNull();

        List<FacFacturaincluidaendisquete> facturasIncluidas = facFacturaincluidaendisqueteMapper.selectByExample(example);

        if (facturasIncluidas != null && !facturasIncluidas.isEmpty()) {
            for (FacFacturaincluidaendisquete facturaIncluida: facturasIncluidas) {
                facturaIncluida.setIdrenegociacion(idRenegociacion);
                facturaIncluida.setFechamodificacion(new Date());
                facturaIncluida.setUsumodificacion(usuario.getIdusuario());

                facFacturaincluidaendisqueteMapper.updateByPrimaryKey(facturaIncluida);
            }
        }

    }

    public void devolverFactura(Long idDisqueteCargos, Integer idFacturaIncluida, Date fecha, String observaciones, boolean comision, AdmUsuarios usuario) throws Exception {

        //Pago a devolver
        FacFacturaincluidaendisqueteKey facFacturaincluidaendisqueteKey = new FacFacturaincluidaendisqueteKey();
        facFacturaincluidaendisqueteKey.setIddisquetecargos(idDisqueteCargos);
        facFacturaincluidaendisqueteKey.setIdfacturaincluidaendisquete(idFacturaIncluida);
        facFacturaincluidaendisqueteKey.setIdinstitucion(usuario.getIdinstitucion());

        FacFacturaincluidaendisquete facFacturaincluidaendisquete = facFacturaincluidaendisqueteMapper.selectByPrimaryKey(facFacturaincluidaendisqueteKey);


        String listaFacturas = idDisqueteCargos + "||"
                + idFacturaIncluida + "||" + facFacturaincluidaendisquete.getIdfactura() + "||"
                + facFacturaincluidaendisquete.getIdrecibo() + "||" + observaciones;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        //Obtener ID Disquete Devoluciones
        String resultado[] = null;

        Object[] param_in = new Object[5]; // Parametros de entrada del PL

        param_in[0] = usuario.getIdinstitucion();
        param_in[1] = listaFacturas;
        param_in[2] = dateFormat.format(fecha);
        param_in[3] = usuario.getIdlenguaje();
        param_in[4] = usuario.getIdusuario();

        resultado = commons.callPLProcedureFacturacionPyS("{call PKG_SIGA_CARGOS.DevolucionesManuales(?,?,?,?,?,?,?,?)}", 3, param_in);

        if (resultado != null) {
        	if (!resultado[0].equals("0")) {
                throw new BusinessException("facturacionPyS.facturas.error.devolucionesManuales");
            }
        } else {
        	throw new BusinessException("facturacionPyS.facturas.error.devolucionesManuales");
        }

        String [] aListaIdDisquetesDevolucion = resultado[2].split(";");
        for (String sIdDisquetesDevolucion : aListaIdDisquetesDevolucion) {
            FacLineadevoludisqbancoKey lineadevoludisqbancoKey = new FacLineadevoludisqbancoKey();
            lineadevoludisqbancoKey.setIdinstitucion(usuario.getIdinstitucion());
            lineadevoludisqbancoKey.setIddisquetedevoluciones(Long.parseLong(sIdDisquetesDevolucion));
            lineadevoludisqbancoKey.setIdrecibo(facFacturaincluidaendisquete.getIdrecibo());

            FacLineadevoludisqbanco lineadevoludisqbanco = facLineadevoludisqbancoMapper.selectByPrimaryKey(lineadevoludisqbancoKey);

            if (comision)
                aplicarComisionAFactura(lineadevoludisqbanco, fecha, usuario);
        }
    }

    public void aplicarComisionAFactura(FacLineadevoludisqbanco lineaDevolucion, Date fechaDevolucion, AdmUsuarios usuario) {
        if (fechaDevolucion == null)
            fechaDevolucion = new Date();

        // Obtenemos la factura incluida en disquete
        FacFacturaincluidaendisqueteKey facturaincluidaendisqueteKey = new FacFacturaincluidaendisqueteKey();
        facturaincluidaendisqueteKey.setIdinstitucion(lineaDevolucion.getIdinstitucion());
        facturaincluidaendisqueteKey.setIddisquetecargos(lineaDevolucion.getIddisquetecargos());
        facturaincluidaendisqueteKey.setIdfacturaincluidaendisquete(lineaDevolucion.getIdfacturaincluidaendisquete());

        FacFacturaincluidaendisquete facturaincluidaendisquete = facFacturaincluidaendisqueteMapper.selectByPrimaryKey(facturaincluidaendisqueteKey);

        // Obtenemos los datos del cliente deudor
        CenClienteKey cenClienteKey = new CenClienteKey();
        cenClienteKey.setIdinstitucion(facturaincluidaendisquete.getIdinstitucion());
        cenClienteKey.setIdpersona(facturaincluidaendisquete.getIdpersona());

        CenCliente cenCliente = cenClienteMapper.selectByPrimaryKey(cenClienteKey);

        // Obtenemos el disquete de devoluciones
        FacDisquetedevolucionesKey facDisquetedevolucionesKey = new FacDisquetedevolucionesKey();
        facDisquetedevolucionesKey.setIdinstitucion(lineaDevolucion.getIdinstitucion());
        facDisquetedevolucionesKey.setIddisquetedevoluciones(lineaDevolucion.getIddisquetedevoluciones());

        FacDisquetedevoluciones disquetedevoluciones = facDisquetedevolucionesExtendsMapper.selectByPrimaryKey(facDisquetedevolucionesKey);

        // Obtenemos el banco del acreedor
        FacBancoinstitucionKey bancoinstitucionKey = new FacBancoinstitucionKey();
        bancoinstitucionKey.setIdinstitucion(disquetedevoluciones.getIdinstitucion());
        bancoinstitucionKey.setBancosCodigo(disquetedevoluciones.getBancosCodigo());

        FacBancoinstitucion bancoinstitucion = facBancoinstitucionExtendsMapper.selectByPrimaryKey(bancoinstitucionKey);

        // Se actualiza los campos CARGARCLIENTE y GASTOSDEVOLUCION
        if (bancoinstitucion.getComisionimporte() == null || bancoinstitucion.getComisionimporte().doubleValue() <= 0.0) {
            lineaDevolucion.setGastosdevolucion(new BigDecimal(0.0));
        } else {
            lineaDevolucion.setGastosdevolucion(bancoinstitucion.getComisionimporte());
        }

        if (cenCliente.getComisiones() != null && cenCliente.getComisiones().equalsIgnoreCase(SigaConstants.DB_TRUE)
                && bancoinstitucion.getComisionimporte() != null && bancoinstitucion.getComisionimporte().doubleValue() > 0.0) {
            // Se actualiza los campos CARGARCLIENTE y GASTOSDEVOLUCION
            lineaDevolucion.setCargarcliente("S");
            facLineadevoludisqbancoMapper.updateByPrimaryKey(lineaDevolucion);

            // Obtenemos la factura original
            FacFacturaKey facUpdateKey = new FacFacturaKey();
            facUpdateKey.setIdinstitucion(facturaincluidaendisquete.getIdinstitucion());
            facUpdateKey.setIdfactura(facturaincluidaendisquete.getIdfactura());

            FacFactura facUpdate = facFacturaExtendsMapper.selectByPrimaryKey(facUpdateKey);
            facUpdate.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_ANULADA));
            facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);

            insertarHistoricoFacParametros(facUpdate.getIdinstitucion(),
                    facUpdate.getIdfactura(), (short) 9, null, null,
                    null,null,null,null,null,
                    facUpdate.getIdfactura());

            try {
                // Si no se produce error regeneramos el pdf con la información de la factura
                facturacionHelper.generarPdfFacturaFirmada(facUpdate.getIdfactura(), facUpdate.getIdinstitucion().toString(), true, usuario);
            } catch (Exception ex) {
                LOGGER.warn("aplicarComisionAFactura() -> Error al generar el pdf con la información de la factura");
            }

            // Copia Factura
            FacFactura facturaComision = new FacFactura();
            BeanUtils.copyProperties(facUpdate, facturaComision);

            // JPT - Devoluciones 117 - Obtiene nuevo identificador de factura
            String newIdFactura = facFacturaExtendsMapper.getNewFacturaID(String.valueOf(facturaComision.getIdinstitucion())).get(0).getValue();

            // JPT - Devoluciones 117 - Asigna el nuevo identificador de factura
            facturaComision.setIdfactura(newIdFactura);

            // JPT - Devoluciones 117 - Obtenemos la serie de facturación para obtener su contador
            FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
            facSeriefacturacionKey.setIdinstitucion(facturaComision.getIdinstitucion());
            facSeriefacturacionKey.setIdseriefacturacion(facturaComision.getIdseriefacturacion());

            // JPT - Devoluciones 117 - Actualiza el contador
            AdmContadorKey admContadorKey = new AdmContadorKey();
            admContadorKey.setIdinstitucion(facturaComision.getIdinstitucion());
            admContadorKey.setIdcontador(facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey).getIdcontador());

            AdmContador admContador = admContadorExtendsMapper.selectByPrimaryKey(admContadorKey);
            admContador.setContador(admContador.getContador()+1);

            admContadorExtendsMapper.updateByPrimaryKey(admContador);

            // JPT - Devoluciones 117 - Indico la fecha de devolucion
            facturaComision.setFechaemision(fechaDevolucion);

            // JPT - Devoluciones 117 - Indico el estado de la factura calculado anteriormente
            facturaComision.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_DEVUELTA));

            // JPT - Devoluciones 117 - Pone en FAC_FACTURA.COMISIONIDFACTURA el identificador de la factura original
            facturaComision.setComisionidfactura(facUpdate.getIdfactura());

            // JPT - Devoluciones 117 - Asigna el nuevo numero de factura
            facturaComision.setNumerofactura(facFacturaExtendsMapper.getNuevoNumeroFactura(facturaComision.getIdinstitucion().toString(), facturaComision.getIdseriefacturacion().toString()).get(0).getValue());

            // JPT - Devoluciones 117 - Calcula el importe del iva
            long IVAComision = Long.parseLong(facBancoinstitucionExtendsMapper.getPorcentajeIva(String.valueOf(bancoinstitucion.getIdinstitucion()), bancoinstitucion.getBancosCodigo()).get(0).getValue());
            BigDecimal importeIVAComision = bancoinstitucion.getComisionimporte().multiply(BigDecimal.valueOf(IVAComision)).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN);

            // JPT - Devoluciones 117 - Calcula los importes de la factura final con los importes de la comision
            BigDecimal impTotalPorPagar = facturaComision.getImptotalporpagar().add(importeIVAComision.add(bancoinstitucion.getComisionimporte()));
            BigDecimal impTotal = facturaComision.getImptotal().add(importeIVAComision.add(bancoinstitucion.getComisionimporte()));
            BigDecimal impTotalIva = facturaComision.getImptotaliva().add(importeIVAComision);
            BigDecimal impTotalNeto = facturaComision.getImptotalneto().add(bancoinstitucion.getComisionimporte());

            facturaComision.setImptotalporpagar(impTotalPorPagar.setScale(2, RoundingMode.DOWN));
            facturaComision.setImptotal(impTotal.setScale(2, RoundingMode.DOWN));
            facturaComision.setImptotaliva(impTotalIva.setScale(2, RoundingMode.DOWN));
            facturaComision.setImptotalneto(impTotalNeto.setScale(2, RoundingMode.DOWN));

            // Se actualiza el estado del la última entrada que tendrá la factura en el histórico
            facturaComision.setEstado(facturaComision.getIdcuenta() == null ? Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA)
                    : Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO));

            facFacturaExtendsMapper.insertSelective(facturaComision);

            // Rellenamos los datos para insercciónn EMISIÓN Y CONFIRMACIÓN
            FacHistoricofactura facHistoricoFacturaDevuelta = rellenarHistoricoFactura(facturaComision, usuario);

            // EMISIÓN
            facHistoricoFacturaDevuelta.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_EN_REVISION));
            facHistoricoFacturaDevuelta.setIdtipoaccion(Short.parseShort("1"));
            facHistoricoFacturaDevuelta.setComisionidfactura(facUpdate.getIdfactura());
            facHistoricoFacturaDevuelta.setIdhistorico(
                    facHistoricofacturaExtendsMapper.getNextIdHstorico(facturaComision.getIdinstitucion(),
                            facturaComision.getIdfactura()));
            facHistoricofacturaExtendsMapper.insertSelective(facHistoricoFacturaDevuelta);

            // CONFIRMACIÓN
            facHistoricoFacturaDevuelta.setEstado(facturaComision.getIdcuenta() == null ? Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA)
                    : Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO));
            facHistoricoFacturaDevuelta.setIdtipoaccion(Short.parseShort("2"));
            facHistoricoFacturaDevuelta.setIdhistorico(
                    facHistoricofacturaExtendsMapper.getNextIdHstorico(facturaComision.getIdinstitucion(),
                            facturaComision.getIdfactura()));
            facHistoricofacturaExtendsMapper.insertSelective(facHistoricoFacturaDevuelta);

            // Copia las lineas de factura
            FacLineafacturaExample exampleLinea = new FacLineafacturaExample();
            exampleLinea.createCriteria().andIdfacturaEqualTo(facturaincluidaendisquete.getIdfactura())
                    .andIdinstitucionEqualTo(usuario.getIdinstitucion());
            exampleLinea.setOrderByClause("NUMEROLINEA");

            List<FacLineafactura> listLinea = facLineafacturaExtendsMapper.selectByExample(exampleLinea);

            long maximoNumeroOrden = 1;
            long maximoNumeroLinea = 1;

            for (FacLineafactura lf : listLinea) {
                lf.setIdfactura(facturaComision.getIdfactura());
                facLineafacturaExtendsMapper.insert(lf);

                if(lf.getNumeroorden() > maximoNumeroOrden){
                    maximoNumeroOrden = lf.getNumeroorden();
                }
                if(lf.getNumerolinea() > maximoNumeroLinea){
                    maximoNumeroLinea = lf.getNumerolinea();
                }
            }

            // Generamos una nueva línea de factura para la comisión
            FacLineafactura lineaFactura = new FacLineafactura();
            lineaFactura.setIdinstitucion(facUpdate.getIdinstitucion());
            lineaFactura.setIdfactura(facturaComision.getIdfactura());
            lineaFactura.setNumerolinea(maximoNumeroLinea + 1);
            lineaFactura.setNumeroorden(maximoNumeroOrden + 1);
            lineaFactura.setCantidad(1);
            lineaFactura.setImporteanticipado(new BigDecimal(0.0));
            lineaFactura.setDescripcion(bancoinstitucion.getComisiondescripcion());
            lineaFactura.setPreciounitario(bancoinstitucion.getComisionimporte());
            lineaFactura.setIdtipoiva(bancoinstitucion.getIdtipoiva());
            lineaFactura.setIva(BigDecimal.valueOf(IVAComision));
            lineaFactura.setCtaproductoservicio(bancoinstitucion.getComisioncuentacontable());
            lineaFactura.setCtaiva(pySTipoIvaExtendsMapper.getC_CTAIVA(facUpdate.getIdinstitucion().toString(), bancoinstitucion.getIdtipoiva().toString()).get(0).getValue());
            lineaFactura.setIdformapago(facUpdate.getIdformapago());

            lineaFactura.setFechamodificacion(new Date());
            lineaFactura.setUsumodificacion(usuario.getIdusuario());
            facLineafacturaExtendsMapper.insertSelective(lineaFactura);
        } else {
            // Se actualiza los campos CARGARCLIENTE y GASTOSDEVOLUCION
            lineaDevolucion.setCargarcliente("N");
            facLineadevoludisqbancoMapper.updateByPrimaryKey(lineaDevolucion);
        }
    }

    private FacHistoricofactura rellenarHistoricoFactura(FacFactura factura, AdmUsuarios usuario) {
        FacHistoricofactura historico = new FacHistoricofactura();

        historico.setIdinstitucion(factura.getIdinstitucion());
        historico.setIdfactura(factura.getIdfactura());

        historico.setFechamodificacion(new Date());
        historico.setUsumodificacion(usuario.getIdusuario());

        historico.setIdformapago(factura.getIdformapago());
        historico.setIdpersona(factura.getIdpersona());
        historico.setIdcuenta(factura.getIdcuenta());
        historico.setIdpersonadeudor(factura.getIdpersonadeudor());
        historico.setIdcuentadeudor(factura.getIdcuentadeudor());
        historico.setImptotalanticipado(factura.getImptotalanticipado());
        historico.setImptotalpagadoporcaja(factura.getImptotalpagadoporcaja());
        historico.setImptotalpagadosolocaja(factura.getImptotalpagadosolocaja());
        historico.setImptotalpagadosolotarjeta(factura.getImptotalpagadosolotarjeta());
        historico.setImptotalpagadoporbanco(factura.getImptotalpagadoporbanco());
        historico.setImptotalpagado(factura.getImptotalpagado());
        historico.setImptotalporpagar(factura.getImptotalporpagar());
        historico.setImptotalcompensado(factura.getImptotalcompensado());

        return historico;
    }

    private int insertarHistoricoFacParametros(Short idInstitucion, String idFactura, Short idTipoAccion,
                                               Short idPagoPorCaja, Long idDisqueteCargos, Integer idFacturaIncluidaEnDisquete,
                                               Long idDisqueteDevoluciones, String idRecibo, Short idRenegociacion, Long idAbono, String comisionIdFactura) {
        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(idInstitucion);
        facturaKey.setIdfactura(idFactura);
        FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        FacHistoricofactura record = new FacHistoricofactura();

        Short newIdHistorico = facHistoricofacturaExtendsMapper.getNextIdHstorico(idInstitucion, idFactura);
        record.setIdhistorico(newIdHistorico);

        record.setIdinstitucion(factura.getIdinstitucion());
        record.setIdfactura(factura.getIdfactura());
        record.setFechamodificacion(new Date());
        record.setUsumodificacion(factura.getUsumodificacion());

        record.setIdpersona(factura.getIdpersona());
        record.setIdcuenta(factura.getIdcuenta());
        record.setIdpersonadeudor(factura.getIdpersonadeudor());
        record.setIdcuentadeudor(factura.getIdcuentadeudor());

        record.setImptotalanticipado(factura.getImptotalanticipado());
        record.setImptotalpagadoporcaja(factura.getImptotalpagadoporcaja());
        record.setImptotalpagadosolocaja(factura.getImptotalpagadosolocaja());
        record.setImptotalpagadosolotarjeta(factura.getImptotalpagadosolotarjeta());
        record.setImptotalpagadoporbanco(factura.getImptotalpagadoporbanco());
        record.setImptotalpagado(factura.getImptotalpagado());
        record.setImptotalporpagar(factura.getImptotalporpagar());
        record.setImptotalcompensado(factura.getImptotalcompensado());

        record.setIdtipoaccion(idTipoAccion);
        record.setIdformapago(factura.getIdformapago());
        record.setEstado(factura.getEstado());

        record.setIdpagoporcaja(idPagoPorCaja);
        record.setIddisquetecargos(idDisqueteCargos);
        record.setIdfacturaincluidaendisquete(idFacturaIncluidaEnDisquete);
        record.setIddisquetedevoluciones(idDisqueteDevoluciones);
        record.setIdrecibo(idRecibo);
        record.setIdrenegociacion(idRenegociacion);
        record.setIdabono(idAbono);
        record.setComisionidfactura(comisionIdFactura);


        return facHistoricofacturaExtendsMapper.insertSelective(record);
    }

    private String getTraduccion(String idrecurso, String idioma) {
        GenDiccionarioKey keyParametros = new GenDiccionarioKey();
        keyParametros.setIdrecurso(idrecurso);
        keyParametros.setIdlenguaje(idioma);
        GenDiccionario traduccion = genDiccionarioMapper.selectByPrimaryKey(keyParametros);
        return traduccion != null ? traduccion.getDescripcion() : "";
    }
}
