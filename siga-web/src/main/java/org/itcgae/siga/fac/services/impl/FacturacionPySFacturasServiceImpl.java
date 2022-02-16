package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.EstadosAbonosDTO;
import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
import org.itcgae.siga.DTO.fac.EstadosPagosDTO;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.entities.FacHistoricofacturaExample;
import org.itcgae.siga.db.entities.FacPagosporcaja;
import org.itcgae.siga.db.entities.FacPagosporcajaExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionPySFacturasService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturacionPySFacturasServiceImpl implements IFacturacionPySFacturasService {

    private Logger LOGGER = Logger.getLogger(FacturacionPySFacturasServiceImpl.class);

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    private FacturaAccionesHelper facturaAccionesHelper;

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private FacAbonoExtendsMapper facAbonoExtendsMapper;

    @Autowired
    private FacPagoabonoefectivoExtendsMapper facPagoabonoefectivoExtendsMapper;

    @Autowired
    private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

    @Autowired
    private GenRecursosMapper genRecursosMapper;

    @Override
    public EstadosAbonosDTO getEstadosAbonos(String idAbono, HttpServletRequest request) throws Exception {
        EstadosAbonosDTO estadosPagosDTO = new EstadosAbonosDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySServiceImpl.getEstadosAbonos() -> Entrada al servicio para obtener el historico del abono");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info("facPagoabonoefectivoExtendsMapper.getEstadosAbonos() -> obteniendo el historico del abono");

            List<EstadosAbonosItem> result = facPagoabonoefectivoExtendsMapper.getEstadosAbonos(idAbono,
                    usuario.getIdinstitucion(), usuario.getIdlenguaje());

            // Se calcula el importe pendiente para cada una de las líneas
            if (result != null && result.size() > 1) {
                BigDecimal total = new BigDecimal(result.get(0).getImportePendiente());
                for (int i = 0; i < result.size(); i++) {
                    EstadosAbonosItem item = result.get(i);
                    BigDecimal movimiento = new BigDecimal(result.get(i).getMovimiento());
                    if (total != null && movimiento != null) {
                        total = total.subtract(movimiento).setScale(2, RoundingMode.DOWN);
                        item.setImportePendiente(total.toString());

                        if (total.compareTo(BigDecimal.ZERO) == 0) {
                            item.setIdEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO.toString());
                            item.setEstado(getRecurso("general.literal.pagado", usuario.getIdlenguaje()));
                        }
                    }
                }
            }

            estadosPagosDTO.setEstadosAbonosItems(result);
        }

        LOGGER.info(
                "FacturacionPySServiceImpl.getEstadosAbonos() -> Salida del servicio  para obtener el historico del abono");

        return estadosPagosDTO;
    }

    @Override
    public InsertResponseDTO compensarAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();
        String idFactura = null;

        LOGGER.info(
                "FacturacionPySFacturasImpl.compensarAbono() -> Entrando al servicio  para compensar un abono");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        // Obtenemos la factura a compensar si ha sido indicada
        if (!UtilidadesString.esCadenaVacia(nuevoEstado.getNumeroFactura())) {
            FacFacturaExample facturaExample = new FacFacturaExample();
            facturaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andNumerofacturaEqualTo(nuevoEstado.getNumeroFactura());

            List<FacFactura> facturas = facFacturaExtendsMapper.selectByExample(facturaExample);

            if (facturas == null || facturas.isEmpty())
                throw new BusinessException("facturacionSJCS.abonosSJCS.error.compensacion.numeroAbono");

            FacFactura factura = facturas.get(0);

            if (!factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA))
                    || !factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO)))
                throw new BusinessException("facturacionSJCS.abonosSJCS.error.compensacion.estado");

            if (factura.getImptotalporpagar().compareTo(new BigDecimal(nuevoEstado.getMovimiento())) < 0)
                throw new BusinessException("facturacionSJCS.abonosSJCS.error.compensacion.importe");

            // Procedemos a compensar el abono
            facturaAccionesHelper.compensarAbono(Long.parseLong(nuevoEstado.getIdAbono()), factura.getIdfactura(),
                    new BigDecimal(nuevoEstado.getMovimiento()), usuario);
        } else {
            FacAbonoKey abonoKey = new FacAbonoKey();
            abonoKey.setIdinstitucion(usuario.getIdinstitucion());
            abonoKey.setIdabono(Long.parseLong(nuevoEstado.getIdAbono()));

            FacAbono abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

            // El sistema buscará las facturas pendientes de cobro del colegiado/sociedad al que corresponde este Abono SJCS
            FacFacturaExample facturaExample = new FacFacturaExample();
            facturaExample.or().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andEstadoEqualTo(Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO))
                    .andIdpersonaEqualTo(abono.getIdpersona());
            facturaExample.or().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andEstadoEqualTo(Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA))
                    .andIdpersonaEqualTo(abono.getIdpersona());
            facturaExample.setOrderByClause("fechaemision");

            List<FacFactura> facturas = facFacturaExtendsMapper.selectByExample(facturaExample);

            // Se comprobará que la suma de importe pendiente es mayor o igual que en el Movimiento
            BigDecimal importePendiente = new BigDecimal(nuevoEstado.getMovimiento());
            if (importePendiente.compareTo(facturas.stream().map(FacFactura::getImptotalporpagar)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO)) > 0)
                throw new BusinessException("facturacionSJCS.abonosSJCS.error.compensacion.importe");

            for (FacFactura factura: facturas) {
                // Procedemos a compensar el abono con cada una de las facturas
                BigDecimal importeACompensar = factura.getImptotalporpagar().compareTo(importePendiente) > 0
                        ? importePendiente : factura.getImptotalporpagar();

                BigDecimal importeCompensado = facturaAccionesHelper.compensarAbono(Long.parseLong(nuevoEstado.getIdAbono()),
                        factura.getIdfactura(), importeACompensar, usuario);

                importePendiente = importePendiente.subtract(importeCompensado);

                // y comprobará que la suma de importe pendiente es mayor o igual que en el Movimiento
                if (importePendiente.compareTo(BigDecimal.ZERO) <= 0)
                    break;
            }
        }

        LOGGER.info(
                "FacturacionPySFacturasImpl.compensarAbono() -> Salida del servicio  para compensar un abono");

        return insertResponseDTO;
    }

    @Override
    public InsertResponseDTO compensarAbonoVarios(List<EstadosAbonosItem> nuevosEstados, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.compensarAbonoVarios() -> Entrando al servicio  para compensar varios abonos");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        int abonos = 0;
        for (EstadosAbonosItem estadosAbonosItem: nuevosEstados) {
            try {
                compensarAbono(estadosAbonosItem, request);
                abonos++;
            } catch (Exception e) {
                LOGGER.warn("FacturacionPySFacturasImpl.compensarAbonoVarios() -> Error al compensar el abono con id="
                        + estadosAbonosItem.getIdAbono() + ". Error: " + e.getMessage());
            }
        }

        // Número de abonos que han pasado exitósamente por la acción de compensación
        insertResponseDTO.setId(String.valueOf(abonos));

        LOGGER.info(
                "FacturacionPySFacturasImpl.compensarAbonoSJCSVarios() -> Salida del servicio  para compensar varios abonos");

        return insertResponseDTO;
    }

    @Override
    public InsertResponseDTO pagarPorCajaAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.pagarPorCajaAbono() -> Entrando al servicio  para pagar una abono");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        facturaAccionesHelper.pagarAbonoPorCaja(Long.parseLong(nuevoEstado.getIdAbono()), null,
                new BigDecimal(nuevoEstado.getMovimiento()), nuevoEstado.getComentario(), usuario);

        LOGGER.info(
                "FacturacionPySFacturasImpl.pagarPorCajaAbono() -> Salida del servicio  para pagar un abono");

        return insertResponseDTO;
    }

    @Override
    public InsertResponseDTO pagarPorCajaAbonoVarios(List<EstadosAbonosItem> nuevosEstados, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.pagarPorCajaAbonoVarios() -> Entrando al servicio para pagar varios abonos por caja");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        int abonos = 0;
        for (EstadosAbonosItem estadosAbonosItem: nuevosEstados) {
            try {
            	pagarPorCajaAbono(estadosAbonosItem, request);
                abonos++;
            } catch (Exception e) {
                LOGGER.warn("FacturacionPySFacturasImpl.pagarPorCajaAbonoVarios() -> Error al pagar por caja el abono con id="
                        + estadosAbonosItem.getIdAbono() + ". Error: " + e.getMessage());
            }
        }

        // Número de abonos que han pasado exitósamente por la acción de nuevo abono
        insertResponseDTO.setId(String.valueOf(abonos));

        LOGGER.info(
                "FacturacionPySFacturasImpl.pagarPorCajaAbonoSJCSVarios() -> Salida del servicio  para pagar varios abonos por caja");

        return insertResponseDTO;
    }

    @Override
    public DeleteResponseDTO eliminarPagoPorCajaAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.eliminarPagoPorCajaAbono() -> Entrando al servicio  para eliminar un abono");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        facturaAccionesHelper.eliminarUltimoPagoPorCaja(Long.parseLong(nuevoEstado.getIdAbono()), usuario);

        LOGGER.info(
                "FacturacionPySFacturasImpl.eliminarPagoPorCajaAbono() -> Salida del servicio  para eliminar un abono");

        return deleteResponseDTO;
    }

    @Override
    public InsertResponseDTO renegociarAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.renegociarAbono() -> Entrando al servicio  para renegociar una abono");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        Short idCuenta = UtilidadesString.esCadenaVacia(nuevoEstado.getIdCuenta()) ? null : Short.parseShort(nuevoEstado.getIdCuenta());
        facturaAccionesHelper.renegociarAbono(Long.parseLong(nuevoEstado.getIdAbono()), idCuenta, usuario);

        LOGGER.info(
                "FacturacionPySFacturasImpl.renegociarAbono() -> Salida del servicio  para renegociar un abono");

        return insertResponseDTO;
    }

    @Override
    public InsertResponseDTO renegociarAbonoVarios(List<EstadosAbonosItem> nuevosEstados, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.renegociarAbonoVarios() -> Entrando al servicio para pagar varios abonos por caja");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        int abonos = 0;
        for (EstadosAbonosItem estadosAbonosItem: nuevosEstados) {
            try {
                renegociarAbono(estadosAbonosItem, request);
                abonos++;
            } catch (Exception e) {
                LOGGER.warn("FacturacionPySFacturasImpl.renegociarAbonoVarios() -> Error al renegociar el abono con id=" + estadosAbonosItem.getIdAbono());
            }
        }

        // Número de abonos que han pasado exitósamente por la acción de renegociación
        insertResponseDTO.setId(String.valueOf(abonos));

        LOGGER.info(
                "FacturacionPySFacturasImpl.renegociarAbonoVarios() -> Salida del servicio  para renegociar varios abonos por caja");

        return insertResponseDTO;
    }

    @Override
    public EstadosPagosDTO getEstadosPagos(String idFactura, HttpServletRequest request) throws Exception {
        EstadosPagosDTO estadosPagosDTO = new EstadosPagosDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.getEstadosPagos() -> Entrada al servicio para obtener el historico de la factura");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info("FacturacionPySFacturasImpl.getEstadosPagos() -> obteniendo el historico de la factura");

            FacFactura factura = new FacFactura();
            String idFacturaParent = idFactura;

            do {
                FacFacturaKey key = new FacFacturaKey();
                key.setIdinstitucion(usuario.getIdinstitucion());
                key.setIdfactura(idFacturaParent);
                factura = facFacturaExtendsMapper.selectByPrimaryKey(key);

                if (factura != null && factura.getComisionidfactura() != null)
                    idFacturaParent = factura.getComisionidfactura();
            } while (factura != null && factura.getComisionidfactura() != null);

            List<EstadosPagosItem> result = facHistoricofacturaExtendsMapper.getEstadosPagos(idFacturaParent,
                    usuario.getIdinstitucion().toString(), usuario.getIdlenguaje());

            estadosPagosDTO.setEstadosPagosItems(result);
        }

        LOGGER.info(
                "FacturacionPySFacturasImpl.getEstadosPagos() -> Salida del servicio  para obtener el historico de la factura");

        return estadosPagosDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResponseDTO insertarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        insertResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("insertarEstadosPagos() -> Entrada al servicio para crear una entrada al historico de factura");

        if (usuario != null) {

            // renegociar
            if (item.getIdAccion().equalsIgnoreCase("7")) {

                Short idCuenta = UtilidadesString.esCadenaVacia(item.getCuentaBanco()) ? null : Short.parseShort(item.getCuentaBanco());
                facturaAccionesHelper.renegociarFactura(item.getModo(), item.getIdFactura(), idCuenta,
                        item.getFechaModificaion(), item.getComentario(), usuario);
            }

            // nuevo cobro
            if (item.getIdAccion().equalsIgnoreCase("4")) {
                BigDecimal importe = new BigDecimal(item.getImpTotalPagado());
                facturaAccionesHelper.pagarFacturaPorCaja(item.getIdFactura(), importe, item.getComentario(), item.getFechaModificaion(), usuario);
            }

            // devolver
            if (item.getIdAccion().equalsIgnoreCase("6")) {
                // Ultima entrada del historico de facturas
                FacHistoricofacturaExample example = new FacHistoricofacturaExample();
                example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                        .andIdfacturaEqualTo(item.getIdFactura());
                example.setOrderByClause("IDHISTORICO");
                List<FacHistoricofactura> facHistoricoList = facHistoricofacturaExtendsMapper.selectByExample(example);

                FacHistoricofactura facHistoricoInsert = facHistoricoList.get(facHistoricoList.size() - 1);

                facturaAccionesHelper.devolverFactura(facHistoricoInsert.getIddisquetecargos(), facHistoricoInsert.getIdfacturaincluidaendisquete(),
                        item.getFechaModificaion(), item.getComentario(), item.getComision() != null ? item.getComision() : false, usuario);
            }


            // anular
            if (item.getIdAccion().equalsIgnoreCase("8")) {
                facturaAccionesHelper.anularFactura(item.getIdFactura(), item.getFechaModificaion(), item.getComentario(), usuario);
            }

            //insertResponseDTO.setId(facHistoricoInsert.getIdfactura());
        }

        LOGGER.info("insertarEstadosPagos() -> Salida del servicio para crear una entrada al historico de factura");

        return insertResponseDTO;
    }

    @Override
    public InsertResponseDTO insertarEstadosPagosVarios(List<EstadosPagosItem> nuevosEstados, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.insertarEstadosPagosVarios() -> Entrando al servicio para insertar un nuevo estado de factura");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        int facturas = 0;
        for (EstadosPagosItem estadosPagosItem: nuevosEstados) {
            try {
                insertarEstadosPagos(estadosPagosItem, request);
                facturas++;
            } catch (Exception e) {
                LOGGER.warn("FacturacionPySFacturasImpl.insertarEstadosPagosVarios() -> Error al insertar un nuevo estado para la factura con id=" + estadosPagosItem.getIdFactura());
            }
        }

        // Número de abonos que han pasado exitósamente por la acción de renegociación
        insertResponseDTO.setId(String.valueOf(facturas));

        LOGGER.info(
                "FacturacionPySFacturasImpl.insertarEstadosPagosVarios() -> Salida del servicio para insertar un nuevo estado de factura");

        return insertResponseDTO;
    }

    @Override
    public DeleteResponseDTO eliminarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception {
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        deleteResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("eliminarEstadosPagos() -> Entrada al servicio para eliminar una entrada del historico de factura");

        if (usuario != null && item.getIdAccion().equalsIgnoreCase("4")) {
            facturaAccionesHelper.eliminarUltimoCobroPorCaja(item.getIdFactura(), usuario);
            deleteResponseDTO.setStatus(HttpStatus.OK.toString());
        }

        LOGGER.info("eliminarEstadosPagos() -> Salida del servicio para eliminar una entrada del historico de factura");

        return deleteResponseDTO;
    }

    private String getRecurso(String idrecurso, String idioma) {
        GenRecursosKey keyParametros = new GenRecursosKey();
        keyParametros.setIdrecurso(idrecurso);
        keyParametros.setIdlenguaje(idioma);
        GenRecursos traduccion = genRecursosMapper.selectByPrimaryKey(keyParametros);
        return traduccion != null ? traduccion.getDescripcion() : "";
    }

}
