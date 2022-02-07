package org.itcgae.siga.fac.services.impl;

import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.entities.FacPagoabonoefectivo;
import org.itcgae.siga.db.entities.FacPagosporcaja;
import org.itcgae.siga.db.entities.FacRenegociacion;
import org.itcgae.siga.db.entities.FacRenegociacionExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.mappers.FacRenegociacionMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class FacturaAccionesHelper {

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
    private GenDiccionarioMapper genDiccionarioMapper;

    @Autowired
    private FacturacionHelper facturacionHelper;

    public BigDecimal compensarAbono(Long idAbono, String idFactura, Double importeFactura, AdmUsuarios usuario) throws Exception {
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

        if (importeFactura > 0.0) {
            pagoAbono.setIdinstitucion(abonoKey.getIdinstitucion());
            pagoAbono.setIdabono(abonoKey.getIdabono());

            Long newIdPagoAbono = facPagoabonoefectivoExtendsMapper.getNuevoID(abonoKey.getIdinstitucion().toString(), abonoKey.getIdabono().toString());
            pagoAbono.setIdpagoabono(newIdPagoAbono);

            if (importeFactura > cantidadPendiente.doubleValue()) {
                pagoAbono.setImporte(cantidadPendiente.setScale(2, RoundingMode.DOWN));
            } else {
                pagoAbono.setImporte(BigDecimal.valueOf(importeFactura).setScale(2, RoundingMode.DOWN));
            }

            BigDecimal importeCompensado = pagoAbono.getImporte();
            pagoAbono.setFecha(new Date());
            pagoAbono.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

            int resultado = facPagoabonoefectivoExtendsMapper.insert(pagoAbono);

            if (resultado <= 0)
                throw new BusinessException("Error al insertar el abono");

            abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

            if (abono.getEstado().equals(SigaConstants.FAC_ABONO_ESTADO_PAGADO) || cantidadPendiente.compareTo(BigDecimal.ZERO) <= 0)
                throw new BusinessException("Sólo se puede compensar un abono si está abonado");

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

            if (importeFactura > cantidadPendiente.doubleValue()) {
                pagoCaja.setImporte(cantidadPendiente.setScale(2, RoundingMode.DOWN));
                cantidadPendiente = BigDecimal.ZERO;
            } else {
                pagoCaja.setImporte(BigDecimal.valueOf(importeFactura).setScale(2, RoundingMode.DOWN));
                cantidadPendiente = cantidadPendiente.subtract(BigDecimal.valueOf(importeFactura));
            }

            pagoCaja.setIdabono(idAbono);
            pagoCaja.setIdpagoabono(pagoAbono.getIdpagoabono());

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
                consultarActNuevoEstadoFactura(factura, true);

                // Insertar nuevo estado en historico de facturas
                resultado = facHistoricofacturaExtendsMapper.insertarHistoricoFacParametros(factura.getIdinstitucion().toString(), factura.getIdfactura(), 10, pagoCaja.getIdpagoporcaja().intValue(),
                        null, null, null,null, null, abono.getIdabono().intValue(), null);

                if (resultado <= 0)
                    throw new BusinessException("Error al insertar la factura el historico");

            }
        }

        return cantidadOriginal.subtract(cantidadPendiente);
    }

    public void pagarAbonoPorCaja(Long idAbono, String idFactura, BigDecimal importe, AdmUsuarios usuario) {
        int resultado;

        FacPagoabonoefectivo pagoAbonoEfectivo = new FacPagoabonoefectivo();
        FacPagosporcaja pagoPorCaja = new FacPagosporcaja();

        FacFactura factura;
        FacAbono abono;

        // Insertamos un nuevo abono efectivo
        pagoAbonoEfectivo.setIdinstitucion(usuario.getIdinstitucion());
        pagoAbonoEfectivo.setIdabono(idAbono);

        // Obtenemos el id del nuevo pago abono a insertar
        Long newIdPagoAbono = facPagoabonoefectivoExtendsMapper.getNuevoID(usuario.getIdinstitucion().toString(),
                idAbono.toString());
        pagoAbonoEfectivo.setIdpagoabono(newIdPagoAbono);

        pagoAbonoEfectivo.setImporte(importe.setScale(2, RoundingMode.DOWN));
        pagoAbonoEfectivo.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

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
                consultarActNuevoEstadoFactura(factura, true);
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

    public int renegociarAbono(String idFactura, Short idFormaPago, Short idCuenta, String observaciones, AdmUsuarios usuario) throws Exception {
        FacFacturaKey facturaKey = new FacFacturaKey();
        facturaKey.setIdinstitucion(usuario.getIdinstitucion());
        facturaKey.setIdfactura(idFactura);

        Short nuevoEstado;
        FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

        boolean actualizarFacturaEnDisco = false;

        if (factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_DEVUELTA))
                && UtilidadesString.esCadenaVacia(factura.getComisionidfactura()))
            actualizarFacturaEnDisco = true;


        if (idFormaPago.equals(Short.parseShort(SigaConstants.TIPO_FORMAPAGO_METALICO))) {
            if (factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA)))
                return 1;

            idFormaPago = Short.parseShort(SigaConstants.TIPO_FORMAPAGO_METALICO);
            nuevoEstado = Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA);
            idCuenta = null;

        } else if (idFormaPago.equals(Short.parseShort(SigaConstants.TIPO_FORMAPAGO_FACTURA))) {
            if (factura.getIdcuenta() == null)
                return 2;

            idFormaPago = Short.parseShort(SigaConstants.TIPO_FORMAPAGO_FACTURA);
            nuevoEstado = Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO);

            CenCuentasbancarias cuentaBancaria = null;
            if (cuentaBancaria != null && cuentaBancaria.getIdcuenta() != null) {
                idCuenta = cuentaBancaria.getIdcuenta();
            } else {
                return 3;
            }
        } else if (false) {

        }

        // Insertamos un nuevo registro en FAC_RENEGOCIACION
        FacRenegociacion renegociacion = new FacRenegociacion();
        renegociacion.setComentario(observaciones.trim());
        renegociacion.setIdfactura(idFactura);
        renegociacion.setIdinstitucion(usuario.getIdinstitucion());
        renegociacion.setIdpersona(factura.getIdpersona());
        renegociacion.setImporte(factura.getImptotalporpagar());
        renegociacion.setFecharenegociacion(new Date());

        if (idCuenta != null) {
            renegociacion.setIdcuenta(idCuenta);
            factura.setIdcuenta(idCuenta);
        } else {
            factura.setIdcuenta(null);
        }

        Short newIdRenegociacion = null; // TODO
        renegociacion.setIdrenegociacion(newIdRenegociacion);

        int resultado;

        // Insertamos la renegociación
        resultado = facRenegociacionMapper.insert(renegociacion);

        if (resultado <= 0)
            throw new BusinessException("Error al insertar la renegociación");

        // Actualizamos la tabla FacFacturaIncluidaEnDisquete

        // Actualizamos el estado y forma de pago de la factura
        // factura.setEstado(nuevoEstado);
        factura.setIdformapago(idFormaPago);

        consultarActNuevoEstadoFactura(factura, true);

        resultado = insertarHistoricoFacParametros(usuario.getIdinstitucion(), idFactura, (short) 7, null, null,
                null, null, null, newIdRenegociacion, null, null);

        if (resultado <= 0)
            throw new BusinessException("No se ha insertado en el histórico de la facturación");

        return 0;
    }

    private String consultarActNuevoEstadoFactura(FacFactura facturaBean, boolean actualizar) throws BusinessException {

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
                                facturaLocalBean.getIdinstitucion().toString(), Boolean.TRUE);
                    } catch (Exception e) {
                        throw new BusinessException("Excepcion en la generación del informe de factura.", e);
                    }
                }
            }

        } catch (Exception e) {
            throw new BusinessException("Excepcion en la consulta/actualizacion del estado de la factura.", e);
        }

        return nuevoEstado;
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
