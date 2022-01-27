package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionKey;
import org.itcgae.siga.db.entities.FacDisquetedevoluciones;
import org.itcgae.siga.db.entities.FacDisquetedevolucionesKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisquete;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisqueteKey;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.entities.FacHistoricofacturaExample;
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.entities.FacLineadevoludisqbancoExample;
import org.itcgae.siga.db.entities.FacLineafactura;
import org.itcgae.siga.db.entities.FacLineafacturaExample;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetedevolucionesExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineafacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.fac.services.INuevoFicheroDevolucionesAsyncService;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class NuevoFicheroDevolucionesAsyncServiceImpl implements INuevoFicheroDevolucionesAsyncService {

    private Logger LOGGER = Logger.getLogger(NuevoFicheroDevolucionesAsyncServiceImpl.class);

    @Autowired
    WSCommons commons;

    @Autowired
    private FacLineadevoludisqbancoMapper facLineadevoludisqbancoMapper;

    @Autowired
    private FacLineafacturaExtendsMapper facLineafacturaExtendsMapper;

    @Autowired
    private FacFacturaincluidaendisqueteMapper facFacturaincluidaendisqueteMapper;

    @Autowired
    private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

    @Autowired
    private CenClienteMapper cenClienteMapper;

    @Autowired
    private FacDisquetedevolucionesExtendsMapper facDisquetedevolucionesExtendsMapper;

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

    @Autowired
    private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

    @Autowired
    private AdmContadorMapper admContadorMapper;

    @Autowired
    private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

    private static final AtomicBoolean GENERACION_EN_PROCESO = new AtomicBoolean();

    @Override
    public boolean isAvailable() {
        return !NuevoFicheroDevolucionesAsyncServiceImpl.GENERACION_EN_PROCESO.get();
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void nuevoFicheroDevoluciones(String idDisqueteDevoluciones, String nombreFichero,
                                         String rutaOracle, String rutaServidor,
                                         Boolean conComision, AdmUsuarios usuario) throws Exception {

        if (NuevoFicheroDevolucionesAsyncServiceImpl.GENERACION_EN_PROCESO.compareAndSet(false, true)) {
            LOGGER.info("nuevoFicheroDevoluciones() -> Iniciando servicio en background para la generación de fichero de devoluciones");

            try {
                // Presentación del fichero de devoluciones
                String[] resultado = actualizacionTablasDevoluciones(usuario.getIdinstitucion(), rutaOracle,
                        nombreFichero, usuario.getIdlenguaje(), usuario.getIdusuario());

                String codretorno = resultado[0];
                String fechaDevolucion = resultado[2];

                if (codretorno.equalsIgnoreCase("0")) {
                    if (conComision) {
                        // Identificamos los disquetes devueltos asociados al fichero de devoluciones
                        FacLineadevoludisqbancoExample devolucionesExample = new FacLineadevoludisqbancoExample();
                        devolucionesExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                                .andIddisquetedevolucionesGreaterThanOrEqualTo(Long.parseLong(idDisqueteDevoluciones));

                        List<FacLineadevoludisqbanco> devoluciones = facLineadevoludisqbancoMapper.selectByExample(devolucionesExample);

                        // Aplicamos la comision a cada devolucion
                        for (FacLineadevoludisqbanco devolucion : devoluciones) {
                            if (conComision)
                                aplicarComisionAFactura(devolucion, conComision, usuario);
                        }
                    }
                }

                NuevoFicheroDevolucionesAsyncServiceImpl.GENERACION_EN_PROCESO.set(false);
                LOGGER.info("nuevoFicheroDevoluciones() -> Finalizando servicio en background para la generación de fichero de devoluciones");

            } catch (Exception e) {
                NuevoFicheroDevolucionesAsyncServiceImpl.GENERACION_EN_PROCESO.set(false);
                LOGGER.warn("nuevoFicheroDevoluciones() -> Error en la generación del fichero: Se rehabilita la flag de generación");
                throw e;
            }
        }
    }

    private String[] actualizacionTablasDevoluciones(Short institucion, String path, String fichero, String idioma, Integer usuario) throws Exception {
        LOGGER.info("actualizacionTablasDevoluciones() -> Entrada al servicio para presentar el fichero de devoluciones");

        String resultado[] = new String[3];
        String codigoError_FicNoEncontrado = "5397";	// Código de error, el fichero no se ha encontrado.
        String codretorno  = codigoError_FicNoEncontrado;
        try	{
            int i=0;
            while (i<3 && codretorno.equalsIgnoreCase(codigoError_FicNoEncontrado)){
                i++;
                Thread.sleep(1000);
                Object[] param_in = new Object[5];
                param_in[0] = institucion;
                param_in[1] = path;
                param_in[2] = fichero;
                param_in[3] = idioma;
                param_in[4] = usuario;
                resultado = commons.callPLProcedureFacturacionPyS(
                        "{call PKG_SIGA_CARGOS.DEVOLUCIONES(?,?,?,?,?,?,?,?)}", 3, param_in);
                codretorno = resultado[0];
            }

        } catch (Exception e){
            throw new Exception("actualizacionTableroDevoluciones() -> Proc:PKG_SIGA_CARGOS.DEVOLUCIONES " + resultado[1]);
        }

        LOGGER.info("actualizacionTablasDevoluciones() -> Saliendo del servicio para presentar el fichero de devoluciones");

        return resultado;
    }

    private void aplicarComisionAFactura(FacLineadevoludisqbanco lineaDevolucion, Boolean conComision, AdmUsuarios usuario) {
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

        if (conComision && cenCliente.getComisiones() != null && cenCliente.getComisiones().equalsIgnoreCase(SigaConstants.DB_TRUE)
                && bancoinstitucion.getComisionimporte() != null && bancoinstitucion.getComisionimporte().doubleValue() > 0.0) {
            // Se actualiza los campos CARGARCLIENTE y GASTOSDEVOLUCION
            lineaDevolucion.setCargarcliente("S");
            facLineadevoludisqbancoMapper.updateByPrimaryKey(lineaDevolucion);

            // Obtenemos la factura original
            FacFacturaKey facUpdateKey = new FacFacturaKey();
            facUpdateKey.setIdinstitucion(facturaincluidaendisquete.getIdinstitucion());
            facUpdateKey.setIdfactura(facturaincluidaendisquete.getIdfactura());

            FacFactura facUpdate = facFacturaExtendsMapper.selectByPrimaryKey(facUpdateKey);

            // ultima entrada en el historico
            FacHistoricofacturaExample example = new FacHistoricofacturaExample();
            example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andIdfacturaEqualTo(facturaincluidaendisquete.getIdfactura());
            example.setOrderByClause("IDHISTORICO");
            List<FacHistoricofactura> facHistoricoList = facHistoricofacturaExtendsMapper.selectByExample(example);

            FacHistoricofactura facHistoricoInsert = facHistoricoList.get(facHistoricoList.size() - 1);

            //Copia Factura
            FacFactura facturaComision = new FacFactura();
            BeanUtils.copyProperties(facUpdate, facturaComision);

            facturaComision.setIdfactura(facFacturaExtendsMapper.getNewFacturaID(String.valueOf(facturaComision.getIdinstitucion())).get(0).getValue());

            //Historico Factura Anulada
            FacHistoricofactura fachistoricoAnulada = new FacHistoricofactura();

            fachistoricoAnulada.setIdfactura(facUpdate.getIdfactura());
            fachistoricoAnulada.setIdinstitucion(facUpdate.getIdinstitucion());

            fachistoricoAnulada.setIdtipoaccion((short) 9);
            fachistoricoAnulada.setEstado((short) 8);
            fachistoricoAnulada.setIdhistorico((short) (facHistoricoInsert.getIdhistorico()+1));

            fachistoricoAnulada.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalpagado(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalporpagar(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalanticipado(BigDecimal.valueOf(0));
            fachistoricoAnulada.setImptotalcompensado(facUpdate.getImptotal());
            fachistoricoAnulada.setIdformapago((short) 20); //FORMAPAGO= domiciliacion bancaria
            fachistoricoAnulada.setIdpersona(facHistoricoInsert.getIdpersona());

            //Anular Factura Original
            facUpdate.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
            facUpdate.setImptotalpagado(BigDecimal.valueOf(0));
            facUpdate.setImptotalporpagar(BigDecimal.valueOf(0));
            facUpdate.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
            facUpdate.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
            facUpdate.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
            facUpdate.setImptotalcompensado(facUpdate.getImptotal());

            facUpdate.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_ANULADA));

            //Factura Comision
            facturaComision.setFechaemision(facUpdate.getFechamodificacion());
            facturaComision.setEstado((short) 4);

            facturaComision.setComisionidfactura(facUpdate.getIdfactura());
            facturaComision.setNumerofactura(facFacturaExtendsMapper.getNuevoNumeroFactura(facturaComision.getIdinstitucion().toString(), facturaComision.getIdseriefacturacion().toString()).get(0).getValue());

            long IVAComision = Long.parseLong(facBancoinstitucionExtendsMapper.getPorcentajeIva(String.valueOf(bancoinstitucion.getIdinstitucion()), bancoinstitucion.getBancosCodigo()).get(0).getValue());
            BigDecimal importeIVAComision = bancoinstitucion.getComisionimporte().multiply(BigDecimal.valueOf(IVAComision/100));

            facturaComision.setImptotalporpagar(facturaComision.getImptotalporpagar().add(importeIVAComision.add(bancoinstitucion.getComisionimporte())));
            facturaComision.setImptotal(facturaComision.getImptotal().add(importeIVAComision.add(bancoinstitucion.getComisionimporte())));
            facturaComision.setImptotaliva(facturaComision.getImptotaliva().add(importeIVAComision));
            facturaComision.setImptotalneto(facturaComision.getImptotalneto().add(bancoinstitucion.getComisionimporte()));

            facFacturaExtendsMapper.insert(facturaComision);

            //se actualiza el historico
            fachistoricoAnulada.setComisionidfactura(facturaComision.getIdfactura());
            fachistoricoAnulada.setFechamodificacion(new Date());
            fachistoricoAnulada.setUsumodificacion(usuario.getIdusuario());
            facHistoricofacturaExtendsMapper.insert(fachistoricoAnulada);

            //Actualizar Contador Factura
            FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
            facSeriefacturacionKey.setIdinstitucion(facturaComision.getIdinstitucion());
            facSeriefacturacionKey.setIdseriefacturacion(facturaComision.getIdseriefacturacion());

            AdmContadorKey admContadorKey = new AdmContadorKey();
            admContadorKey.setIdinstitucion(facturaComision.getIdinstitucion());
            admContadorKey.setIdcontador(facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey).getIdcontador());

            AdmContador admContador = admContadorMapper.selectByPrimaryKey(admContadorKey);
            admContador.setContador(admContador.getContador()+1);

            admContadorMapper.updateByPrimaryKey(admContador);

            //Historico Factura Comision
            FacHistoricofactura fachistoricoRevision = fachistoricoAnulada;

            fachistoricoRevision.setComisionidfactura(facUpdate.getIdfactura());

            fachistoricoRevision.setIdfactura(facturaComision.getIdfactura());
            fachistoricoRevision.setIdhistorico((short) 1);
            fachistoricoRevision.setIdtipoaccion((short) 1);
            fachistoricoRevision.setEstado((short) 7);

            fachistoricoRevision.setImptotalanticipado(BigDecimal.valueOf(0));
            fachistoricoRevision.setImptotalcompensado(facturaComision.getImptotal());
            fachistoricoRevision.setFechamodificacion(new Date());
            fachistoricoRevision.setUsumodificacion(usuario.getIdusuario());

            facHistoricofacturaExtendsMapper.insert(fachistoricoRevision);

            FacHistoricofactura fachistoricoPendiente = fachistoricoRevision;

            fachistoricoRevision.setIdhistorico((short) 2);
            fachistoricoRevision.setIdtipoaccion((short) 2);
            fachistoricoRevision.setEstado((short) 9);

            facHistoricofacturaExtendsMapper.insert(fachistoricoPendiente);

            //Copia las lineas de factura
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
            lineaFactura.setCtaproductoservicio(bancoinstitucion.getComisioncuentacontable());
            lineaFactura.setCtaiva(pySTipoIvaExtendsMapper.getC_CTAIVA(facUpdate.getIdinstitucion().toString(), bancoinstitucion.getIdtipoiva().toString()).get(0).getValue());
            lineaFactura.setIdformapago(facUpdate.getIdformapago());

            facLineafacturaExtendsMapper.insert(lineaFactura);
        } else {
            // Se actualiza los campos CARGARCLIENTE y GASTOSDEVOLUCION
            lineaDevolucion.setCargarcliente("N");
            facLineadevoludisqbancoMapper.updateByPrimaryKey(lineaDevolucion);
        }
    }
}
