package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacturasFacturacionRapidaDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesNumeros;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionKey;
import org.itcgae.siga.db.entities.PysTipoiva;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysCompraExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionRapidaService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class FacturacionRapidaServiceImpl implements IFacturacionRapidaService {

    private final Logger LOGGER = Logger.getLogger(FacturacionRapidaServiceImpl.class);

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    @Autowired
    private PysCompraExtendsMapper pysCompraExtendsMapper;

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

    @Autowired
    private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

    @Autowired
    private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;

    private TransactionStatus getNeTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(Integer.parseInt("30"));
        def.setName("transGenFacRap");
        return transactionManager.getTransaction(def);
    }

    private void rollBack(TransactionStatus transactionStatus) {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
        }
    }

    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.commit(transactionStatus);
        }
    }

    @Override
    public ComboDTO getSeleccionSerieFacturacion(HttpServletRequest request, String idInstitucion, String idPeticion) throws Exception {

        LOGGER.info("FacturacionRapidaServiceImpl.getSeleccionSerieFacturacion() --> ENTRADA al servicio para obtener el combo de serie de facturacion rapida");

        ComboDTO comboDTO = new ComboDTO();
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {

            boolean isFacturado = false;

            if (UtilidadesString.esCadenaVacia(idInstitucion)) {
                throw new Exception("Falta el identificador de la institucion");
            }

            if (UtilidadesString.esCadenaVacia(idPeticion)) {
                throw new Exception("Falta el identificador de la peticion");
            }

            // Obtiene las compras de la peticion
            LOGGER.info("FacturacionRapidaServiceImpl.getSeleccionSerieFacturacion() --> pysCompraExtendsMapper.obtenerComprasPeticion() --> ENTRADA al servicio para obtener las compras de la peticion");
            List<PysCompra> vCompras = pysCompraExtendsMapper.obtenerComprasPeticion(idInstitucion, idPeticion);
            LOGGER.info("FacturacionRapidaServiceImpl.getSeleccionSerieFacturacion() --> pysCompraExtendsMapper.obtenerComprasPeticion() --> SALIDA del servicio para obtener las compras de la peticion");

            if (vCompras == null || vCompras.size() == 0) {
                throw new Exception("messages.facturacionRapidaCompra.noElementosFacturables");
            }

            // Obtiene las facturas de una peticion de una solicitud de compra de productos
            LOGGER.info("FacturacionRapidaServiceImpl.getSeleccionSerieFacturacion() --> facFacturaExtendsMapper.obtenerFacturasFacturacionRapida() --> ENTRADA al servicio para obtener facturas de una peticion de una solicitud de compra de productos");
            List<FacturasFacturacionRapidaDTO> vFacturas = facFacturaExtendsMapper.obtenerFacturasFacturacionRapida(idInstitucion, idPeticion, null);
            LOGGER.info("FacturacionRapidaServiceImpl.getSeleccionSerieFacturacion() --> facFacturaExtendsMapper.obtenerFacturasFacturacionRapida() --> SALIDA del servicio para obtener facturas de una peticion de una solicitud de compra de productos");

            if (vFacturas != null && vFacturas.size() > 0) {// Compruebo si ya tiene facturas asociadas a la peticion
                isFacturado = true; // JPT: Esto sirve para indicar que ya esta facturado
            } else {
                // Busca las series de facturacion del producto
                List<FacSeriefacturacion> vSeriesFacturacion = obtenerSeriesAdecuadas(vCompras);

                if (vSeriesFacturacion != null) {
                    for (FacSeriefacturacion serie : vSeriesFacturacion) {
                        ComboItem comboItem = new ComboItem();
                        comboItem.setLabel(serie.getNombreabreviado());
                        comboItem.setValue(serie.getIdseriefacturacion().toString());
                        comboDTO.getCombooItems().add(comboItem);
                    }
                }
            }

            if (isFacturado) {
                comboDTO.setCombooItems(null);
            }

        }

        LOGGER.info("FacturacionRapidaServiceImpl.getSeleccionSerieFacturacion() --> SALIDA del servicio para obtener el combo de serie de facturacion rapida");

        return comboDTO;
    }

    private List<FacSeriefacturacion> obtenerSeriesAdecuadas(List<PysCompra> compras) throws Exception {

        List<FacSeriefacturacion> facSeriefacturacionList;

        try {

            if (compras == null || compras.size() == 0) {
                throw new Exception("Error: No se han recibido compras.");
            }

            String idInstitucion = "", idPersona = "";
            StringBuilder listadoProductos = new StringBuilder();
            int numeroTipos = 0;

            // Obtiene un listado de los tipos de productos y un contador
            for (PysCompra compra : compras) {
                String sTipoProducto = "('" + compra.getIdproducto() + "','" + compra.getIdtipoproducto() + "')";

                if (listadoProductos.length() > 0) {
                    if (listadoProductos.indexOf(sTipoProducto) == -1) {
                        numeroTipos++;
                        listadoProductos.append(",");
                        listadoProductos.append(sTipoProducto);
                    }

                } else {
                    numeroTipos++;
                    listadoProductos.append(sTipoProducto);
                    idInstitucion = compra.getIdinstitucion().toString();
                    idPersona = (compra.getIdpersonadeudor() != null && !compra.getIdpersonadeudor().toString().equals("") ? compra.getIdpersonadeudor().toString() : compra.getIdpersona().toString());
                }
            }

            facSeriefacturacionList = facSeriefacturacionExtendsMapper.obtenerSeriesAdecuadas(idInstitucion, numeroTipos, listadoProductos.toString());

            if (facSeriefacturacionList == null || facSeriefacturacionList.size() == 0) {
                facSeriefacturacionList = facSeriefacturacionExtendsMapper.obtenerSeriesAdecuadas2(idInstitucion, numeroTipos, listadoProductos.toString(), idPersona);
            }

        } catch (Exception e) {
            throw new Exception("Error al buscar las series de facturacion candidatas.", e);
        }

        return facSeriefacturacionList;
    }

    @Override
    public void facturacionRapidaCompra(HttpServletRequest request, String idInstitucion, String idPeticion, String idSerieFacturacion) throws Exception {
        LOGGER.info("FacturacionRapidaServiceImpl.facturacionRapidaCompra() --> ENTRADA al servicio para la facturacion rapida de una compra");

        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {

        }

        LOGGER.info("FacturacionRapidaServiceImpl.facturacionRapidaCompra() --> SALIDA del servicio para la facturacion rapida de una compra");
    }

    private void facturacionRapidaProductosCertificados(String idInstitucion, String idPeticion, String idSerieSeleccionada, String idSolicitudCertificado) throws Exception {

        TransactionStatus tx = null;

        try {

            tx = getNeTransaction();

            // Listas necesarias para el proceso
            List<PysCompra> vCompras = new ArrayList<>();
            List<FacturasFacturacionRapidaDTO> vFacturas;

            if (idSolicitudCertificado != null) { // CERTIFICADO

                // Obtiene las facturas de una solicitud de certificado
                vFacturas = facFacturaExtendsMapper.obtenerFacturasFacturacionRapida(idInstitucion, null, idSolicitudCertificado);

                if (vFacturas == null || vFacturas.size() == 0) { // No esta facturado => vCompras => Tx

                    // BLOQUEAMOS LA TABLA DE COMPRAS EN ESTA TRANSACCION PARA CONTROLAR LAS PETICIONES SIMULTANEAS
                    pysCompraExtendsMapper.lockTable();

                    // Obtengo la peticion de compra
                    PysCompra beanCompra = pysCompraExtendsMapper.obtenerCompraCertificado(idInstitucion, idSolicitudCertificado).stream().findFirst().orElse(null);

                    PysTipoiva beanTipoIva = pySTipoIvaExtendsMapper.selectByPrimaryKey(beanCompra.getIdtipoiva());

                    // ANTES DE FACTURAR APUNTO EL IMPORTE TOTAL COMO IMPORTE ANTICIPADO
                    double importe = UtilidadesNumeros.redondea(beanCompra.getCantidad().intValue() * beanCompra.getImporteunitario().doubleValue() * (1 + (beanTipoIva.getValor().doubleValue() / 100)), 2);
                    beanCompra.setImporteanticipado(BigDecimal.valueOf(importe));

                    if (pysCompraExtendsMapper.updateByPrimaryKeySelective(beanCompra) == 0) {
                        throw new Exception("Error al actualizar el importe anticipado");
                    }

                    vCompras.add(beanCompra);

                    // Obtengo la peticion del certificado
                    idPeticion = beanCompra.getIdpeticion().toString();
                } else { // Esta facturado => vFacturas => No Tx

                    // Obtengo los datos de la factura
                    FacturasFacturacionRapidaDTO hFactura = vFacturas.get(0);

                    // Obtengo la peticion del certificado
                    idPeticion = hFactura.getIdPeticion().toString();
                }

            } else { // PRODUCTOS NO CERTIFICADOS

                // Obtiene las facturas de una peticion de una solicitud de compra de productos
                vFacturas = facFacturaExtendsMapper.obtenerFacturasFacturacionRapida(idInstitucion, idPeticion, null);

                if (vFacturas == null || vFacturas.size() == 0) { // No esta facturado => vCompras => Tx

                    // BLOQUEAMOS LA TABLA DE COMPRAS EN ESTA TRANSACCION PARA CONTROLAR LAS PETICIONES SIMULTANEAS
                    pysCompraExtendsMapper.lockTable();

                    vCompras = pysCompraExtendsMapper.obtenerComprasPeticion(idInstitucion, idPeticion);
                    if (vCompras.size() == 0) {
                        throw new Exception("messages.facturacionRapidaCompra.noElementosFacturables");
                    }

                } // else {} // Esta facturado => vFacturas => No Tx

            }

            if (vFacturas == null || vFacturas.size() == 0) { // Compruebo si no tiene facturas asociadas a la peticion => vCompras => Tx

                // Obtiene la serie candidata
                FacSeriefacturacion beanSerieCandidata = null;
                if (idSerieSeleccionada == null || idSerieSeleccionada.equals("")) {
                    List<FacSeriefacturacion> series = obtenerSeriesAdecuadas(vCompras);
                    if (series == null || series.size() != 1) {
                        // LIBERAMOS EL BLOQUEO DE LAS TABLAS Y LA TRANSACCION
                        throw new Exception("messages.facturacionRapidaCompra.noSerieAdecuada");

                    } else if (series.size() == 1) {
                        beanSerieCandidata = series.get(0);
                    }

                } else { // Se ha seleccionado una serie
                    Hashtable<String, String> hSerieFacturacion = new Hashtable<String, String>();
                    hSerieFacturacion.put("IDINSTITUCION", idInstitucion);
                    hSerieFacturacion.put("IDSERIEFACTURACION", idSerieSeleccionada);

                    FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
                    facSeriefacturacionKey.setIdinstitucion(Short.valueOf(idInstitucion));
                    facSeriefacturacionKey.setIdseriefacturacion(Long.valueOf(idSerieSeleccionada));

                    FacSeriefacturacion vSerieFacturacion = facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey);

                    if (vSerieFacturacion != null) {
                        beanSerieCandidata = vSerieFacturacion;
                    }
                }

                // Obtengo la peticion de compra
                PysPeticioncomprasuscripcionKey pysPeticioncomprasuscripcionKey = new PysPeticioncomprasuscripcionKey();
                pysPeticioncomprasuscripcionKey.setIdinstitucion(Short.valueOf(idInstitucion));
                pysPeticioncomprasuscripcionKey.setIdpeticion(Long.valueOf(idPeticion));

                PysPeticioncomprasuscripcion vPeticionCompraSuscripcion = pysPeticioncomprasuscripcionExtendsMapper.selectByPrimaryKey(pysPeticioncomprasuscripcionKey);

                PysPeticioncomprasuscripcion beanPeticionCompraSuscripcion = null;
                if (vPeticionCompraSuscripcion != null) {
                    beanPeticionCompraSuscripcion = vPeticionCompraSuscripcion;
                }

                if (idSolicitudCertificado != null) { // CERTIFICADO
                    if (beanPeticionCompraSuscripcion.getIdestadopeticion().toString().equals(SigaConstants.ESTADO_PETICION_COMPRA_PENDIENTE)) { // Esta en estado pendiente. Hay que aprobarla
                        beanPeticionCompraSuscripcion.setIdestadopeticion(Short.valueOf(SigaConstants.ESTADO_PETICION_COMPRA_PROCESADA));

                        if (pysPeticioncomprasuscripcionExtendsMapper.updateByPrimaryKeySelective(beanPeticionCompraSuscripcion) == 0) {
                            throw new Exception("Error al actualizar el estado de la peticion de compra del certificado");
                        }
                    }

                } else {
                    // LOCALIZO LAS COMPRAS (SI NO EXISTEN LAS GENERO)
                    if (beanPeticionCompraSuscripcion.getIdestadopeticion().equals(SigaConstants.ESTADO_PETICION_COMPRA_BAJA)) { // Esta en estado baja
                        throw new Exception("messages.facturacionRapidaCompra.estadoBaja");

                    } else if (beanPeticionCompraSuscripcion.getIdestadopeticion().equals(SigaConstants.ESTADO_PETICION_COMPRA_PENDIENTE)) { // Esta en estado pendiente. Hay que aprobarla
//                        beanPeticionCompraSuscripcion = aprobarCompras(vCompras);
                    }
                }

            }

        } catch (Exception e) {
            rollBack(tx);
            throw e;
        }

    }
}