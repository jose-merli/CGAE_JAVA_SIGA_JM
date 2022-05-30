package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.UtilidadesNumeros;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.entities.PysCompraExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcionKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysProductosinstitucionKey;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysProductossolicitadosExample;
import org.itcgae.siga.db.entities.PysProductossolicitadosKey;
import org.itcgae.siga.db.entities.PysServiciossolicitadosExample;
import org.itcgae.siga.db.entities.PysTipoiva;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysCompraExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductosinstitucionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductossolicitadosExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionRapidaService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

@Service
public class FacturacionRapidaServiceImpl implements IFacturacionRapidaService {

    private final Logger LOGGER = Logger.getLogger(FacturacionRapidaServiceImpl.class);

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    private PlatformTransactionManager transactionManager;

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

    @Autowired
    private PysProductossolicitadosExtendsMapper pysProductossolicitadosExtendsMapper;

    @Autowired
    private PysProductosinstitucionExtendsMapper pysProductosinstitucionExtendsMapper;

    @Autowired
    private PysServiciossolicitadosMapper pysServiciossolicitadosMapper;

    @Autowired
    private FacFacturacionprogramadaExtendsMapper facFacturacionprogramadaExtendsMapper;

    @Autowired
    private WSCommons wsCommons;

    @Autowired
    private FacturacionHelper facturacionHelper;

    @Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;

    @Autowired
    private ProTratarConfirmacion proTratarConfirmacion;

    private static final String PROP_SIGA_JTA_TIMEOUT_PESADA = "siga.jta.timeout.pesada";

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
                throw new Exception("No existen elementos facturables");
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

    @Override
    public Resource facturacionRapidaCompra(HttpServletRequest request, String idInstitucion, String idPeticion, String idSerieFacturacion) throws Exception {
        LOGGER.info("FacturacionRapidaServiceImpl.facturacionRapidaCompra() --> ENTRADA al servicio para la facturacion rapida de una compra");

        Resource resource = null;

        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {

            if (UtilidadesString.esCadenaVacia(idInstitucion)) {
                throw new Exception("Falta el identificador de la institucion");
            }

            if (UtilidadesString.esCadenaVacia(idPeticion)) {
                throw new Exception("Falta el identificador de la idPeticion");
            }

            if (UtilidadesString.esCadenaVacia(idSerieFacturacion)) {
                throw new Exception("Falta el identificador de la idSerieFacturacion");
            }

            resource = facturacionRapidaProductosCertificados(idInstitucion, idPeticion, idSerieFacturacion, null, usuario);
        }

        LOGGER.info("FacturacionRapidaServiceImpl.facturacionRapidaCompra() --> SALIDA del servicio para la facturacion rapida de una compra");

        return resource;
    }

    @Override
    public Resource facturacionRapidaCompraCertificados(HttpServletRequest request, String idInstitucion, String idSolicitudCertificado, String idSerieFacturacion) throws Exception {
        LOGGER.info("FacturacionRapidaServiceImpl.facturacionRapidaCompraCertificados() --> ENTRADA al servicio para la facturacion rapida de una compra");

        Resource resource = null;

        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {

            if (UtilidadesString.esCadenaVacia(idInstitucion)) {
                throw new Exception("Falta el identificador de la institucion");
            }

            if (UtilidadesString.esCadenaVacia(idSolicitudCertificado)) {
                throw new Exception("Falta el identificador de la idSolicitudCertificado");
            }

            if (UtilidadesString.esCadenaVacia(idSerieFacturacion)) {
                throw new Exception("Falta el identificador de la idSerieFacturacion");
            }

            resource = facturacionRapidaProductosCertificados(idInstitucion, null, idSerieFacturacion, idSolicitudCertificado, usuario);
        }

        LOGGER.info("FacturacionRapidaServiceImpl.facturacionRapidaCompraCertificados() --> SALIDA del servicio para la facturacion rapida de una compra");

        return resource;
    }

    private final Function<Integer, Boolean> i10ToBool = v -> v.intValue() == 1;

    private int getTimeoutLargo() {
        Integer time = Integer.valueOf(facturacionHelper.getProperty(PROP_SIGA_JTA_TIMEOUT_PESADA));
        return time;
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

    private Resource facturacionRapidaProductosCertificados(String idInstitucion, String idPeticion, String idSerieSeleccionada, String idSolicitudCertificado, AdmUsuarios usuario) throws Exception {

        TransactionStatus tx = null;
        Resource resource = null;

        try {

            tx = facturacionHelper.getNewLongTransaction(getTimeoutLargo());

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
                        throw new Exception("No existen elementos facturables");
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
                        throw new Exception("No se ha encontrado una serie de facturacion adecuada");

                    } else if (series.size() == 1) {
                        beanSerieCandidata = series.get(0);
                    }

                } else { // Se ha seleccionado una serie
                    FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
                    facSeriefacturacionKey.setIdinstitucion(Short.valueOf(idInstitucion));
                    facSeriefacturacionKey.setIdseriefacturacion(Long.valueOf(idSerieSeleccionada));

                    beanSerieCandidata = facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey);
                }

                // Obtengo la peticion de compra
                PysPeticioncomprasuscripcionKey pysPeticioncomprasuscripcionKey = new PysPeticioncomprasuscripcionKey();
                pysPeticioncomprasuscripcionKey.setIdinstitucion(Short.valueOf(idInstitucion));
                pysPeticioncomprasuscripcionKey.setIdpeticion(Long.valueOf(idPeticion));

                PysPeticioncomprasuscripcion beanPeticionCompraSuscripcion = pysPeticioncomprasuscripcionExtendsMapper.selectByPrimaryKey(pysPeticioncomprasuscripcionKey);

                if (idSolicitudCertificado != null) { // CERTIFICADO
                    if (beanPeticionCompraSuscripcion.getIdestadopeticion().toString().equals(SigaConstants.ESTADO_PETICION_COMPRA_PENDIENTE)) { // Esta en estado pendiente. Hay que aprobarla
                        beanPeticionCompraSuscripcion.setIdestadopeticion(Short.valueOf(SigaConstants.ESTADO_PETICION_COMPRA_PROCESADA));

                        if (pysPeticioncomprasuscripcionExtendsMapper.updateByPrimaryKeySelective(beanPeticionCompraSuscripcion) == 0) {
                            throw new Exception("Error al actualizar el estado de la peticion de compra del certificado");
                        }
                    }

                } else {
                    // LOCALIZO LAS COMPRAS (SI NO EXISTEN LAS GENERO)
                    if (beanPeticionCompraSuscripcion.getIdestadopeticion().toString().equals(SigaConstants.ESTADO_PETICION_COMPRA_BAJA)) { // Esta en estado baja
                        throw new Exception("La petición está en estado 'Baja'");

                    } else if (beanPeticionCompraSuscripcion.getIdestadopeticion().toString().equals(SigaConstants.ESTADO_PETICION_COMPRA_PENDIENTE)) { // Esta en estado pendiente. Hay que aprobarla
                        beanPeticionCompraSuscripcion = aprobarCompras(vCompras, usuario);
                    }
                }

                // FACTURACION RAPIDA DESDE SERIE CANDIDATA (GENERACION)
                FacFacturacionprogramada programacion = procesarFacturacionRapidaCompras(beanPeticionCompraSuscripcion, vCompras, beanSerieCandidata, usuario);

                // CONFIRMACION RAPIDA (en este caso la transaccion se gestiona dentro la transaccion)
                proTratarConfirmacion.confirmarProgramacionFactura(programacion, false, null, false, false, true, tx);

                if (idSolicitudCertificado != null) { // CERTIFICADO

                    // Obtiene las facturas de una solicitud de certificado
                    vFacturas = facFacturaExtendsMapper.obtenerFacturasFacturacionRapida(idInstitucion, null, idSolicitudCertificado);

                } else { // PRODUCTOS NO CERTIFICADOS

                    // Obtiene las facturas de una peticion de una solicitud de compra de productos
                    vFacturas = facFacturaExtendsMapper.obtenerFacturasFacturacionRapida(idInstitucion, idPeticion, null);
                }

                commit(tx);

            } else {
            } // Esta facturado => vFacturas => No Tx

            // GENERAR FICHERO: Siempre elimina el zip con los pdfs firmads o el pdf firmado

            try {

                File fichero = generarInformeFacturacionRapida(idInstitucion, idPeticion, vFacturas, usuario);

                if (fichero == null) {
                    throw new Exception("Error al generar la factura. Fichero devuelto es nulo.");
                }

                // DESCARGAR FICHERO
                String nombreColegiado = "";
                if (vFacturas != null && vFacturas.size() > 0) {
                    FacturasFacturacionRapidaDTO obj = vFacturas.get(0);
                    String idPersona = obj.getIdPersona().toString();
                    nombreColegiado = "";
                    if (idPersona != null && !"".equalsIgnoreCase(idPersona)) {
                        nombreColegiado = facturacionHelper.obtenerNombreApellidos(idPersona);
                        if (nombreColegiado != null && !"".equalsIgnoreCase(nombreColegiado)) {
                            nombreColegiado = UtilidadesString.eliminarAcentosYCaracteresEspeciales(nombreColegiado) + "-";
                        } else {
                            nombreColegiado = "";
                        }
                    }
                }

                int inicio = fichero.getName().indexOf(".zip");
                //Si se llama a este metodo desde el demonio de: acciones masivas, la request viene null

                //Si es -1 no es un fichero zip
                if (inicio == -1) {

                    FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
                    facSeriefacturacionKey.setIdseriefacturacion(vFacturas.get(0).getIdSerieFacturacion());
                    facSeriefacturacionKey.setIdinstitucion(vFacturas.get(0).getIdInstitucion());

                    FacSeriefacturacion vSeriesFacturacion = facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey);

                    String[] nombreFicherosarrays;
                    String nombreFichero;
                    if (vSeriesFacturacion != null) {
                        FacSeriefacturacion beanSerieFacturacion = vSeriesFacturacion;
                        switch (beanSerieFacturacion.getIdNombreDescargaFac()) {
                            case 1:
                                nombreFicherosarrays = fichero.getName().split("-", 2);
                                nombreFichero = nombreFicherosarrays[1];
                                break;
                            case 2:
                                //Quitamos la extension y anadimos el nombre mas la extension
                                String[] separacionExtensionDelFichero = fichero.getName().split(Pattern.quote("."));
                                String[] separacionNombreColegiado = nombreColegiado.split("-");
                                nombreFicherosarrays = separacionExtensionDelFichero[0].split("-", 2);

                                nombreFichero = nombreFicherosarrays[1] + "-" + separacionNombreColegiado[0] + "." + separacionExtensionDelFichero[1];
                                break;

                            default:
                                nombreFicherosarrays = fichero.getName().split("-", 2);
                                nombreFichero = nombreColegiado + nombreFicherosarrays[1];
                                break;
                        }
                    } else {
                        nombreFicherosarrays = fichero.getName().split("-", 2);
                        nombreFichero = nombreColegiado + nombreFicherosarrays[1];
                    }

                    if (fichero != null) {
                        resource = new ByteArrayResource(Files.readAllBytes(fichero.toPath())) {
                            public String getFilename() {
                                return nombreFichero;
                            }
                        };
                    }

                } else {
                    if (fichero != null) {
                        resource = new ByteArrayResource(Files.readAllBytes(fichero.toPath())) {
                            public String getFilename() {
                                return fichero.getName();
                            }
                        };
                    }
                }

                return resource;

            } catch (Exception e) {
                // Si el error viene de la obtención de la plantilla se devuelve un mensaje de éxito pero sin el fichero pdf
                if (!UtilidadesString.esCadenaVacia(e.getMessage()) && e.getMessage().contains("ObtenerContenidoPlantilla ERROR")) {
                    return null;
                } else {
                    throw e;
                }
            }

        } catch (Exception e) {
            rollBack(tx);
            throw e;
        }

    }

    private PysPeticioncomprasuscripcion aprobarCompras(List<PysCompra> vCompras, AdmUsuarios usuario) throws Exception {
        PysPeticioncomprasuscripcion beanPeticionCompraSuscripcion = null;

        try {

            // Recorre las compras a aprobar
            for (PysCompra beanCompra : vCompras) {

                if (beanCompra.getNofacturable() != null && beanCompra.getNofacturable().equals("0")) { //Si no es NO FACTURABLE
                    if (!confirmarProducto(beanCompra.getIdinstitucion().intValue(), beanCompra.getIdpeticion(), beanCompra.getIdtipoproducto().intValue(), beanCompra.getIdproducto(), beanCompra.getIdproductoinstitucion(), new Double(0), "0", usuario)) {
                        throw new Exception("Error al confirmar producto");
                    }
                }

            }

            if (vCompras.size() > 0) {
                PysCompra beanCompra = vCompras.get(0);

                // Obtengo la peticion de compra
                PysPeticioncomprasuscripcionKey pysPeticioncomprasuscripcionKey = new PysPeticioncomprasuscripcionKey();
                pysPeticioncomprasuscripcionKey.setIdinstitucion(beanCompra.getIdinstitucion());
                pysPeticioncomprasuscripcionKey.setIdpeticion(beanCompra.getIdpeticion());

                beanPeticionCompraSuscripcion = pysPeticioncomprasuscripcionExtendsMapper.selectByPrimaryKey(pysPeticioncomprasuscripcionKey);
            }

        } catch (Exception e) {
            throw new Exception("Error al confirmar la peticion de compra o suscripcion", e);
        }

        return beanPeticionCompraSuscripcion;
    }

    private boolean confirmarProducto(Integer idInstitucion, Long idPeticion, Integer idTipoProducto, Long idProducto, Long idProductoInstitucion, Double importeAnticipado, String fechaEfectiva, AdmUsuarios usuario) throws Exception {

        try {

            PysPeticioncomprasuscripcionKey pysPeticioncomprasuscripcionKey = new PysPeticioncomprasuscripcionKey();
            pysPeticioncomprasuscripcionKey.setIdinstitucion(idInstitucion.shortValue());
            pysPeticioncomprasuscripcionKey.setIdpeticion(idPeticion);
            PysPeticioncomprasuscripcion peticionBean = pysPeticioncomprasuscripcionExtendsMapper.selectByPrimaryKey(pysPeticioncomprasuscripcionKey);

            if (fechaEfectiva == null) {
                fechaEfectiva = "0";
            }
            // Peticion de ALTA
            if (peticionBean.getTipopeticion().equals(SigaConstants.TIPO_PETICION_COMPRA_ALTA)) {

                // 1. Marcamos el producto como ACEPTADO
                PysProductossolicitadosKey pysProductossolicitadosKey = new PysProductossolicitadosKey();
                pysProductossolicitadosKey.setIdinstitucion(idInstitucion.shortValue());
                pysProductossolicitadosKey.setIdpeticion(idPeticion);
                pysProductossolicitadosKey.setIdtipoproducto(idTipoProducto.shortValue());
                pysProductossolicitadosKey.setIdproducto(idProducto);
                pysProductossolicitadosKey.setIdproductoinstitucion(idProductoInstitucion);
                PysProductossolicitados productoBean = pysProductossolicitadosExtendsMapper.selectByPrimaryKey(pysProductossolicitadosKey);

                productoBean.setAceptado(SigaConstants.PRODUCTO_ACEPTADO);

                if (pysProductossolicitadosExtendsMapper.updateByPrimaryKeySelective(productoBean) == 0) {
                    return false;
                }

                // 2. Creamos el apunte -> Insertamos en la tabla PysCompra el registro
                PysCompra compraBean = new PysCompra();
                compraBean.setCantidad(productoBean.getCantidad());

                if (fechaEfectiva.equals("0") || fechaEfectiva == null) {
                    compraBean.setFecha(new Date());
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(SigaConstants.DATE_FORMAT_JAVA);
                    Date d = sdf.parse(fechaEfectiva);
                    compraBean.setFecha(d);
                }
                compraBean.setIdformapago(productoBean.getIdformapago());
                if (compraBean.getIdformapago() != null) {
                    compraBean.setNofacturable("0");
                } else {
                    compraBean.setNofacturable("1");
                }
                compraBean.setIdinstitucion(productoBean.getIdinstitucion());
                compraBean.setIdpeticion(productoBean.getIdpeticion());
                compraBean.setIdproducto(productoBean.getIdproducto());
                compraBean.setIdproductoinstitucion(productoBean.getIdproductoinstitucion());
                compraBean.setIdtipoproducto(productoBean.getIdtipoproducto());
                compraBean.setImporteunitario(productoBean.getValor());
                compraBean.setIdcuenta(productoBean.getIdcuenta());
                compraBean.setIdpersona(productoBean.getIdpersona());

                // RGG 29-04-2005 cambio para insertar la descripcion
                // buscamos la descripcion
                PysProductosinstitucionKey pysProductosinstitucionKey = new PysProductosinstitucionKey();
                pysProductosinstitucionKey.setIdinstitucion(productoBean.getIdinstitucion());
                pysProductosinstitucionKey.setIdtipoproducto(productoBean.getIdtipoproducto());
                pysProductosinstitucionKey.setIdproducto(productoBean.getIdproducto());
                pysProductosinstitucionKey.setIdproductoinstitucion(productoBean.getIdproductoinstitucion());
                PysProductosinstitucion vpi = pysProductosinstitucionExtendsMapper.selectByPrimaryKey(pysProductosinstitucionKey);

                String descripcion = "";
                if (vpi != null) {
                    descripcion = vpi.getDescripcion();
                }
                compraBean.setDescripcion(descripcion);
//				System.out.println("PAGO --> "+productoBean.getIdFormaPago());

                if (productoBean.getNofacturable().equals("0")) {

                    if (productoBean.getIdformapago().intValue() == Integer.parseInt(SigaConstants.TIPO_FORMAPAGO_TARJETA)) {
                        double importeAnticipadoTarjeta = UtilidadesNumeros.redondea(productoBean.getCantidad().doubleValue() * productoBean.getValor().doubleValue() * (1 + (productoBean.getIdtipoiva().floatValue() / 100)), 2);
                        compraBean.setImporteanticipado(BigDecimal.valueOf(new Double(importeAnticipadoTarjeta)));
                    } else {
                        if (productoBean.getIdformapago().intValue() == Integer.parseInt(SigaConstants.TIPO_FORMAPAGO_METALICO))
                            compraBean.setImporteanticipado(BigDecimal.valueOf(importeAnticipado));
                        else
                            compraBean.setImporteanticipado(BigDecimal.valueOf(new Double(0)));
                    }
                }

                compraBean.setIdtipoiva(productoBean.getIdtipoiva());
                compraBean.setFechamodificacion(new Date());
                compraBean.setUsumodificacion(usuario.getIdusuario());

                // Comprobamos si existe el objeto compra, si existe lo actualizamos si no realizamos la inserccion
                PysCompraExample pysCompraExample = new PysCompraExample();
                pysCompraExample.createCriteria().andIdinstitucionEqualTo(compraBean.getIdinstitucion())
                        .andIdpeticionEqualTo(compraBean.getIdpeticion())
                        .andIdproductoEqualTo(compraBean.getIdproducto())
                        .andIdtipoproductoEqualTo(compraBean.getIdtipoproducto())
                        .andIdproductoinstitucionEqualTo(compraBean.getIdproductoinstitucion());

                long numCompras = pysCompraExtendsMapper.countByExample(pysCompraExample);

                if (numCompras == 0) {
                    if (pysCompraExtendsMapper.insert(compraBean) == 0) {
                        return false;
                    }
                } else if (numCompras == 1) {
                    if (pysCompraExtendsMapper.updateByPrimaryKeySelective(compraBean) == 0) {
                        return false;
                    }
                }


                // 3. Verificamos si los articulos de la peticion estan en un estado distinto de PENDIENTE
                long productos_serviciosPendientes = getNumProductosServiciosPendientes(idInstitucion, idPeticion);
                if (productos_serviciosPendientes > 0) {
                    return true;
                }
                // 3.1 no hay articulos pendientes, cambiamos el estado a PROCESADA
                peticionBean.setIdestadopeticion(Short.valueOf(SigaConstants.ESTADO_PETICION_COMPRA_PROCESADA));
                return i10ToBool.apply(pysPeticioncomprasuscripcionExtendsMapper.updateByPrimaryKeySelective(peticionBean));

            }
            // Peticion de BAJA
            else {

                // 1. Comprobamos si esta en Compra el producto solicitado:
                PysCompraExample pysCompraExample = new PysCompraExample();
                pysCompraExample.createCriteria().andIdinstitucionEqualTo(idInstitucion.shortValue())
                        .andIdpeticionEqualTo(peticionBean.getIdpeticionalta())
                        .andIdtipoproductoEqualTo(idTipoProducto.shortValue())
                        .andIdproductoEqualTo(idProducto).andIdproductoinstitucionEqualTo(idProductoInstitucion)
                        .andIdfacturaIsNotNull();
                List<PysCompra> v = pysCompraExtendsMapper.selectByExample(pysCompraExample);

                if ((v != null) && (v.size() > 0)) {
                    throw new Exception("No se encuentra el producto solicitado");
                }

				/*
				if (!productoAdm.delete(claves)) {
					return false;
				}
				*/

                // 2. Cambiamos el estado de peticion de baja a PROCESADA
                peticionBean.setIdestadopeticion(Short.valueOf(SigaConstants.ESTADO_PETICION_COMPRA_PROCESADA));
                //peticionBean.setFecha(GstDate.getApplicationFormatDate("ES",fechaEfectiva));
                if (pysPeticioncomprasuscripcionExtendsMapper.updateByPrimaryKeySelective(peticionBean) == 0) {
                    return false;
                }

                // 3. Cambiamos el estado de producto de peticion de baja a PRODUCTO_BAJA
                PysProductossolicitadosKey pysProductossolicitadosKey = new PysProductossolicitadosKey();
                pysProductossolicitadosKey.setIdinstitucion(idInstitucion.shortValue());
                pysProductossolicitadosKey.setIdpeticion(idPeticion);
                pysProductossolicitadosKey.setIdtipoproducto(idTipoProducto.shortValue());
                pysProductossolicitadosKey.setIdproducto(idProducto);
                pysProductossolicitadosKey.setIdproductoinstitucion(idProductoInstitucion);

                PysProductossolicitados productoBean = pysProductossolicitadosExtendsMapper.selectByPrimaryKey(pysProductossolicitadosKey);

                productoBean.setAceptado(SigaConstants.PRODUCTO_BAJA);
                if (pysProductossolicitadosExtendsMapper.updateByPrimaryKeySelective(productoBean) == 0) {
                    return false;
                }

                // 4. Cambiamos el estado de producto de peticion de alta a PRODUCTO_BAJA
                pysProductossolicitadosKey = new PysProductossolicitadosKey();
                pysProductossolicitadosKey.setIdinstitucion(idInstitucion.shortValue());
                pysProductossolicitadosKey.setIdpeticion(peticionBean.getIdpeticionalta());
                pysProductossolicitadosKey.setIdtipoproducto(idTipoProducto.shortValue());
                pysProductossolicitadosKey.setIdproducto(idProducto);
                pysProductossolicitadosKey.setIdproductoinstitucion(idProductoInstitucion);

                productoBean = pysProductossolicitadosExtendsMapper.selectByPrimaryKey(pysProductossolicitadosKey);

                productoBean.setAceptado(SigaConstants.PRODUCTO_BAJA);

                if (pysProductossolicitadosExtendsMapper.updateByPrimaryKeySelective(productoBean) == 0) {
                    return false;
                }

                // 5. Verificamos si los articulos de la peticion estan en un estado distinto de PENDIENTE
                long productos_serviciosPendientes = getNumProductosServiciosPendientes(idInstitucion, peticionBean.getIdpeticionalta());
                if (productos_serviciosPendientes > 0) {
                    return true;
                }

                //mhg - Cogemos el producto que queremos dar de baja para actualizar la fecha de baja/efectiva
                pysCompraExample.clear();
                pysCompraExample.createCriteria().andIdinstitucionEqualTo(idInstitucion.shortValue())
                        .andIdpeticionEqualTo(peticionBean.getIdpeticionalta())
                        .andIdtipoproductoEqualTo(idTipoProducto.shortValue())
                        .andIdproductoEqualTo(idProducto)
                        .andIdproductoinstitucionEqualTo(idProductoInstitucion);

                List<PysCompra> vProducto = pysCompraExtendsMapper.selectByExample(pysCompraExample);

                if ((vProducto != null) && (vProducto.size() > 0)) {
                    PysCompra compraBean = vProducto.get(0);
                    if (fechaEfectiva.equals("0")) {
                        compraBean.setFecha(new Date());
                    }
                    //mhg - El siguiente caso nunca deberia pasar ya que en una anulaci�n no se deberia seleccionar la fecha de baja.
                    else {
                        SimpleDateFormat sdf = new SimpleDateFormat(SigaConstants.DATE_FORMAT_JAVA);
                        Date d = sdf.parse(fechaEfectiva);
                        compraBean.setFechabaja(d);
                    }

                    if (pysCompraExtendsMapper.updateByPrimaryKeySelective(compraBean) == 0) {
                        return false;
                    }
                }

                // 5.1 No hay articulos pendientes, cambiamos el estado a PROCESADA
                pysPeticioncomprasuscripcionKey = new PysPeticioncomprasuscripcionKey();
                pysPeticioncomprasuscripcionKey.setIdinstitucion(idInstitucion.shortValue());
                pysPeticioncomprasuscripcionKey.setIdpeticion(peticionBean.getIdpeticionalta());

                peticionBean = pysPeticioncomprasuscripcionExtendsMapper.selectByPrimaryKey(pysPeticioncomprasuscripcionKey);
                //peticionBean.setFecha(GstDate.getApplicationFormatDate("ES",fechaEfectiva));

                return i10ToBool.apply(pysPeticioncomprasuscripcionExtendsMapper.updateByPrimaryKeySelective(peticionBean));

            }

        } catch (Exception e) {
            throw new Exception("Error al confirmar la solicitud de un producto", e);
        }
    }

    private long getNumProductosServiciosPendientes(Integer idInstiducion, Long idPeticion) throws Exception {

        try {

            long num = 0;
            PysProductossolicitadosExample pysProductossolicitadosExample = new PysProductossolicitadosExample();
            pysProductossolicitadosExample.createCriteria().andIdinstitucionEqualTo(idInstiducion.shortValue())
                    .andIdpeticionEqualTo(idPeticion).andAceptadoEqualTo(SigaConstants.PRODUCTO_PENDIENTE);

            long numProductos = pysProductossolicitadosExtendsMapper.countByExample(pysProductossolicitadosExample);

            PysServiciossolicitadosExample pysServiciossolicitadosExample = new PysServiciossolicitadosExample();
            pysServiciossolicitadosExample.createCriteria().andIdinstitucionEqualTo(idInstiducion.shortValue())
                    .andIdpeticionEqualTo(idPeticion).andAceptadoEqualTo(SigaConstants.PRODUCTO_PENDIENTE);

            long numServicios = pysServiciossolicitadosMapper.countByExample(pysServiciossolicitadosExample);

            num = numProductos + numServicios;

            return num;

        } catch (Exception e) {
            throw new Exception("Error al obtener el numero de articulos pendientes.");
        }
    }

    private FacFacturacionprogramada procesarFacturacionRapidaCompras(PysPeticioncomprasuscripcion beanPeticionCompraSuscripcion, List<PysCompra> compras, FacSeriefacturacion beanSerieCandidata, AdmUsuarios usuario) throws Exception {

        FacFacturacionprogramada beanFacturacionProgramada = new FacFacturacionprogramada();

        try {

            Date fechaMin = null, fechaMax = null;

            // Recorro la lista de compras, para insertar las compras
            for (PysCompra compra : compras) {

                // Obtengo la fecha de la compra
                Date fechaAux = compra.getFecha();

                // Obtengo la fecha minima y maxima
                if (fechaMin == null || fechaAux.before(fechaMin)) {
                    fechaMin = fechaAux;
                }
                if (fechaMax == null || fechaAux.after(fechaMax)) {
                    fechaMax = fechaAux;
                }
            }

            // Creo la programacion de la facturacion
            beanFacturacionProgramada.setIdinstitucion(beanPeticionCompraSuscripcion.getIdinstitucion());
            beanFacturacionProgramada.setArchivarfact("0");
            beanFacturacionProgramada.setConfdeudor(beanSerieCandidata.getConfdeudor());
            beanFacturacionProgramada.setConfingresos(beanSerieCandidata.getConfingresos());
            beanFacturacionProgramada.setCtaclientes(beanSerieCandidata.getCtaclientes());
            beanFacturacionProgramada.setCtaingresos(beanSerieCandidata.getCtaingresos());
            beanFacturacionProgramada.setFechacargo(new Date());
            beanFacturacionProgramada.setFechainicioproductos(fechaMin);
            beanFacturacionProgramada.setFechafinproductos(fechaMax);
            beanFacturacionProgramada.setFechainicioservicios(fechaMin);
            beanFacturacionProgramada.setFechafinservicios(fechaMax);
            beanFacturacionProgramada.setFechaprogramacion(new Date());
            beanFacturacionProgramada.setFechaprevistageneracion(new Date());
            beanFacturacionProgramada.setFecharealgeneracion(new Date());
            beanFacturacionProgramada.setFechaprevistaconfirm(null);
            beanFacturacionProgramada.setFechaconfirmacion(null);
            beanFacturacionProgramada.setGenerapdf("1");
            beanFacturacionProgramada.setEnvio(beanSerieCandidata.getEnviofacturas());
            beanFacturacionProgramada.setIdestadoenvio(FacEstadoConfirmacionFact.ENVIO_NOAPLICA.getId());
            beanFacturacionProgramada.setIdestadopdf(FacEstadoConfirmacionFact.PDF_NOAPLICA.getId());
            beanFacturacionProgramada.setIdprevision(null);
            beanFacturacionProgramada.setIdseriefacturacion(beanSerieCandidata.getIdseriefacturacion());
            beanFacturacionProgramada.setIdtipoplantillamail(beanSerieCandidata.getIdtipoplantillamail());
            beanFacturacionProgramada.setIdestadoconfirmacion(FacEstadoConfirmacionFact.GENERADA.getId());

            // Obtiene un nuevo identidicador de serie de facturacion
            String idFacturacionProgramada = facFacturacionprogramadaExtendsMapper.getNextIdFacturacionProgramada(beanFacturacionProgramada.getIdinstitucion(), beanFacturacionProgramada.getIdseriefacturacion()).getNewId();
            beanFacturacionProgramada.setIdprogramacion(Long.valueOf(idFacturacionProgramada));

            // Obtiene la descripcion
            String descripcion = beanSerieCandidata.getNombreabreviado() + " [" + idFacturacionProgramada + "]";
            beanFacturacionProgramada.setDescripcion(descripcion);

            beanFacturacionProgramada.setFechamodificacion(new Date());
            beanFacturacionProgramada.setUsumodificacion(usuario.getIdusuario());

            if (facFacturacionprogramadaExtendsMapper.insert(beanFacturacionProgramada) == 0) {
                throw new Exception("Error al insertar cliente incluido en serie");
            }

            // Carga los parametros
            String resultado[] = new String[2];
            Object[] param_in = new Object[6];
            param_in[0] = beanFacturacionProgramada.getIdinstitucion().toString();
            param_in[1] = beanFacturacionProgramada.getIdseriefacturacion().toString();
            param_in[2] = beanFacturacionProgramada.getIdprogramacion().toString();
            param_in[3] = usuario.getIdlenguaje(); // Idioma
            param_in[4] = beanPeticionCompraSuscripcion.getIdpeticion().toString();
            param_in[5] = usuario.getIdusuario().toString();

            // Genera la facturacion
            resultado = wsCommons.callPLProcedureFacturacionPyS("{call PKG_SIGA_FACTURACION.GENERACIONFACTURACION(?,?,?,?,?,?,?,?)}", 2, param_in);

        	// Compruebo que ha finalizado correctamente
            String[] codigosErrorFormato = {"-201", "-202", "-203", "-204"};
            String codretorno = resultado[0];
            if (Arrays.asList(codigosErrorFormato).contains(codretorno)) {
                throw new Exception(resultado[1]);

            } else if (!codretorno.equals("0")) {
                throw new Exception("Error al generar la Facturacion rapida: " + resultado[1]);
            }

            // Desbloquea la facturacion programada
            if (facFacturacionprogramadaExtendsMapper.updateByPrimaryKeySelective(beanFacturacionProgramada) == 0) {
                throw new Exception("Error al actualizar la programacion");
            }

        } catch (Exception e) {
            throw new Exception("Error al realizar generacion de facturacion rapida.", e);
        }

        return beanFacturacionProgramada;
    }

    private File generarInformeFacturacionRapida(String idInstitucion, String idPeticion, List<FacturasFacturacionRapidaDTO> vFacturas, AdmUsuarios usuario) throws Exception {
        File fichero;

        /** Si se trata solo de una factura, se devuelve directamente el fichero PDF **/
        if (vFacturas != null && vFacturas.size() == 1) {
            fichero = facturacionHelper.generarPdfFacturaFirmada(vFacturas.get(0).getIdFactura(), vFacturas.get(0).getIdInstitucion().toString(), usuario);
        } else {
            /** Si generan mas de una factura, se genera un zip con todas las facturas (PDF) **/
            fichero = generarZipFacturacionRapida(idInstitucion, idPeticion, vFacturas, usuario);
        }

        return fichero;
    }

    private File generarZipFacturacionRapida(String idInstitucion, String idPeticion, List<FacturasFacturacionRapidaDTO> vFacturas, AdmUsuarios usuario) throws Exception {
        File ficheroZip = null;

        try {

            // Obtenemos las rutas del zip
            String rutaAlmacen = facturacionHelper.getProperty("facturacion.directorioFisicoFacturaPDFJava") + facturacionHelper.getProperty("facturacion.directorioFacturaPDFJava") + File.separator + idInstitucion;
            String rutaFicheroZip = rutaAlmacen + File.separator + idPeticion + ".zip";
            ficheroZip = new File(rutaFicheroZip);

            // Generamos el zip
            LOGGER.info("ANTES DE GENERAR EL INFORME ZIP.");

            // Comprueba que existe la ruta donde guardar el fichero zip
            File carpetaAlmacen = new File(rutaAlmacen);
            SIGAHelper.mkdirs(rutaAlmacen);
            if (!carpetaAlmacen.canWrite()) {
                throw new Exception("No se tienen permisos para acceder a la carpeta destino");
            }

            // Se crea un array con los pdf del zip
            ArrayList<File> listaFicherosPDF = new ArrayList<>();
            ArrayList<FacFicherosDescargaBean> listaFicherosPDFDescarga = new ArrayList<>();
            FacFicherosDescargaBean facFicherosDescargaBean;

            // Recorre las facturas asociadas a una peticion
            for (int i = 0; i < vFacturas.size(); i++) {

                facFicherosDescargaBean = new FacFicherosDescargaBean();
                /** Generamos cada PDF de la facturacion para ir anadiendolo al ZIP **/
                FacturasFacturacionRapidaDTO hFactura = vFacturas.get(i);
                String idPersona = hFactura.getIdPersona().toString();
                String idSerieFacturacion = hFactura.getIdSerieFacturacion().toString();
                String idInstitucionFac = hFactura.getIdInstitucion().toString();

                File ficheroPdf = facturacionHelper.generarPdfFacturaFirmada(hFactura.getIdFactura(), hFactura.getIdInstitucion().toString(), usuario);

                // Comprobamos que exista el fichero pdf de la factura
                if (ficheroPdf == null) {
                    throw new Exception("Error al generar la factura. Fichero devuelto es nulo.");
                } else if (!ficheroPdf.exists()) {
                    throw new Exception("No existe el fichero PDF de la factura");
                }

                // Obtenemos el nombre de la persona de la factura
                String nombreColegiado = "";
                nombreColegiado = "";
                if (idPersona != null && !"".equalsIgnoreCase(idPersona)) {
                    nombreColegiado = facturacionHelper.obtenerNombreApellidos(idPersona);
                    if (nombreColegiado != null && !"".equalsIgnoreCase(nombreColegiado)) {
                        nombreColegiado = UtilidadesString.eliminarAcentosYCaracteresEspeciales(nombreColegiado) + "-";
                    } else {
                        nombreColegiado = "";
                    }
                }

                FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
                facSeriefacturacionKey.setIdinstitucion(Short.valueOf(idInstitucionFac));
                facSeriefacturacionKey.setIdseriefacturacion(Long.valueOf(idSerieFacturacion));

                FacSeriefacturacion beanSerieFacturacion = facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey);

                if (beanSerieFacturacion != null) {
                    facFicherosDescargaBean.setFormatoDescarga(beanSerieFacturacion.getIdNombreDescargaFac().intValue());
                    facFicherosDescargaBean.setFichero(ficheroPdf);
                    facFicherosDescargaBean.setNombreFacturaFichero(nombreColegiado);
                    listaFicherosPDFDescarga.add(facFicherosDescargaBean);
                }


                // Si llega a este punto, o bien existia el fichero previamente, o bien lo hemos generado => Lo incluimos en el array con los pdf del zip
                listaFicherosPDF.add(ficheroPdf);
            }

            // Generamos el fichero zip con todas las facturas asociadas a la solicitud
            facturacionHelper.doZipGeneracionRapida(rutaAlmacen + File.separator, idPeticion, listaFicherosPDFDescarga);

            // Eliminacion de los pdfs firmados y su carpeta
            File directorio = null;
            for (int i = 0; i < listaFicherosPDF.size(); i++) {
                File ficheroPdfFirmado = listaFicherosPDF.get(i);
                directorio = ficheroPdfFirmado.getParentFile();
                ficheroPdfFirmado.delete(); // Elimina los pdfs firmados
            }
            if (directorio != null && directorio.isDirectory() && directorio.list().length == 0) {
                directorio.delete(); // borra el directorio de las firmas
            }

        } catch (Exception e) {
            throw new Exception("Error al generar el ZIP de una facturacion rapida: " + e.getLocalizedMessage(), e);
        }

        return ficheroZip;
    }

    public Resource descargarFacturasBySolicitud(HttpServletRequest request, List<ListaCompraProductosItem> listaPeticiones) throws Exception  {

        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken( request.getHeader("Authorization") );
        String solicitudes = "";

        
        if(listaPeticiones.size() == 1) {
        	solicitudes = (listaPeticiones.get(0).getnSolicitud());
        }else {
        	for (int i = 0; i < listaPeticiones.size(); i++) {
            	if(i == listaPeticiones.size()-1) {
            		solicitudes += (listaPeticiones.get(i).getnSolicitud());
            	}else {
            		solicitudes += (listaPeticiones.get(i).getnSolicitud() + ", ");
            	}   			
    		}
        }

        List<FacturaItem> facturas = facFacturaExtendsMapper.getFacturasByIdSolicitud(solicitudes, idInstitucion.toString());

        try {
            return this.facturacionHelper.obtenerFicheros(facturas);
        } catch (Exception e) {
            throw e;
        }

    }

}