package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.EstadosAbonosDTO;
import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionPySFacturasService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    private GenRecursosMapper genRecursosMapper;

    @Override
    public EstadosAbonosDTO getEstadosAbonosSJCS(String idAbono, HttpServletRequest request) throws Exception {
        EstadosAbonosDTO estadosPagosDTO = new EstadosAbonosDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySServiceImpl.getEstadosAbonosSJCS() -> Entrada al servicio para obtener el historico del abono SJCS");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info("facPagoabonoefectivoExtendsMapper.getEstadosAbonosSJCS() -> obteniendo el historico del abono SJCS");

            List<EstadosAbonosItem> result = facPagoabonoefectivoExtendsMapper.getEstadosAbonosSJCS(idAbono,
                    usuario.getIdinstitucion(), usuario.getIdlenguaje());

            // Se calcula el importe pendiente para cada una de las líneas
            if (result != null && result.size() > 1) {
                BigDecimal total = new BigDecimal(result.get(0).getImportePendiente());
                for (int i = 0; i < result.size(); i++) {
                    BigDecimal movimiento = new BigDecimal(result.get(i).getMovimiento());
                    if (total != null && movimiento != null) {
                        total = total.subtract(movimiento).setScale(2, RoundingMode.DOWN);
                        result.get(i).setImportePendiente(total.toString());
                    }
                }
            }

            estadosPagosDTO.setEstadosAbonosItems(result);
        }

        LOGGER.info(
                "FacturacionPySServiceImpl.getEstadosAbonosSJCS() -> Salida del servicio  para obtener el historico del abono sjcs");

        return estadosPagosDTO;
    }

    @Override
    public InsertResponseDTO compensarAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();
        String idFactura = null;

        LOGGER.info(
                "FacturacionPySFacturasImpl.compensarAbonoSJCS() -> Entrando al servicio  para compensar una abono SJCS");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        // Obtenemos la factura a compensar si ha sido indicada
        if (!UtilidadesString.esCadenaVacia(nuevoEstado.getNumeroFactura())) {
            FacFacturaExample facturaExample = new FacFacturaExample();
            facturaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andNumerofacturaEqualTo(nuevoEstado.getNumeroFactura());

            List<FacFactura> facturas = facFacturaExtendsMapper.selectByExample(facturaExample);

            if (facturas == null || facturas.isEmpty())
                throw new BusinessException("No existe una factura con ese número de factura");

            if (facturas.get(0).getImptotalporpagar().compareTo(new BigDecimal(nuevoEstado.getMovimiento())) < 0)
                throw new BusinessException("La factura con ese número no tiene tanto importe pendiente");

            // Procedemos a compensar el abono
            facturaAccionesHelper.compensarAbono(Long.parseLong(nuevoEstado.getIdAbono()), facturas.get(0).getIdfactura(),
                    new BigDecimal(nuevoEstado.getMovimiento()), usuario);
        } else {
            FacAbonoKey abonoKey = new FacAbonoKey();
            abonoKey.setIdinstitucion(usuario.getIdinstitucion());
            abonoKey.setIdabono(Long.parseLong(nuevoEstado.getIdAbono()));

            FacAbono abono = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

            // El sistema buscará las facturas pendientes de cobro del colegiado/sociedad al que corresponde este Abono SJCS
            FacFacturaExample facturaExample = new FacFacturaExample();
            facturaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andEstadoEqualTo(Short.parseShort(SigaConstants.ESTADO_FACTURA_CAJA))
                    .andIdpersonaEqualTo(abono.getIdpersona());
            facturaExample.setOrderByClause("fechaemision");

            List<FacFactura> facturas = facFacturaExtendsMapper.selectByExample(facturaExample);

            // Se comprobará que la suma de importe pendiente es mayor o igual que en el Movimiento
            BigDecimal importePendiente = new BigDecimal(nuevoEstado.getMovimiento());
            if (importePendiente.compareTo(facturas.stream().map(FacFactura::getImptotalporpagar)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO)) > 0)
                throw new BusinessException("La factura con ese número no tiene tanto importe pendiente");

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
                "FacturacionPySFacturasImpl.compensarAbonoSJCS() -> Salida del servicio  para compensar un abono SJCS");

        return insertResponseDTO;
    }

    @Override
    public InsertResponseDTO pagarPorCajaAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.pagarPorCajaAbonoSJCS() -> Entrando al servicio  para pagar una abono SJCS");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        facturaAccionesHelper.pagarAbonoPorCaja(Long.parseLong(nuevoEstado.getIdAbono()), null,
                new BigDecimal(nuevoEstado.getMovimiento()), usuario);

        LOGGER.info(
                "FacturacionPySFacturasImpl.pagarPorCajaAbonoSJCS() -> Salida del servicio  para pagar un abono SJCS");

        return insertResponseDTO;
    }

    @Override
    public DeleteResponseDTO eliminarPagoPorCajaAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.eliminarPagoPorCajaAbonoSJCS() -> Entrando al servicio  para pagar una abono SJCS");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        facturaAccionesHelper.eliminarUltimoPagoPorCaja(Long.parseLong(nuevoEstado.getIdAbono()), usuario);

        LOGGER.info(
                "FacturacionPySFacturasImpl.eliminarPagoPorCajaAbonoSJCS() -> Salida del servicio  para pagar un abono SJCS");

        return deleteResponseDTO;
    }

    @Override
    public InsertResponseDTO renegociarAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySFacturasImpl.renegociarAbonoSJCS() -> Entrando al servicio  para pagar una abono SJCS");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        Short idCuenta = UtilidadesString.esCadenaVacia(nuevoEstado.getIdCuenta()) ? null : Short.parseShort(nuevoEstado.getIdCuenta());
        facturaAccionesHelper.renegociarAbono(Long.parseLong(nuevoEstado.getIdAbono()), idCuenta, usuario);

        LOGGER.info(
                "FacturacionPySFacturasImpl.renegociarAbonoSJCS() -> Salida del servicio  para pagar un abono SJCS");

        return insertResponseDTO;
    }

}
