package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacDisqueteDevolucionesNuevoItem;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FicherosAbonosDTO;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesDTO;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacAbonoincluidoendisquete;
import org.itcgae.siga.db.entities.FacAbonoincluidoendisqueteExample;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionKey;
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.itcgae.siga.db.entities.FacDisqueteabonosKey;
import org.itcgae.siga.db.entities.FacDisquetecargos;
import org.itcgae.siga.db.entities.FacDisquetecargosExample;
import org.itcgae.siga.db.entities.FacDisquetecargosKey;
import org.itcgae.siga.db.entities.FacDisquetedevoluciones;
import org.itcgae.siga.db.entities.FacDisquetedevolucionesKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisquete;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisqueteExample;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisqueteKey;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.entities.FacHistoricofacturaExample;
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.entities.FacLineadevoludisqbancoExample;
import org.itcgae.siga.db.entities.FacLineafactura;
import org.itcgae.siga.db.entities.FacLineafacturaExample;
import org.itcgae.siga.db.entities.FacPropositos;
import org.itcgae.siga.db.entities.FacPropositosExample;
import org.itcgae.siga.db.entities.FacSeriefacturacionBanco;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisqueteabonosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetedevolucionesExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineafacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionBancoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoincluidoendisqueteExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPropositosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionPySExportacionesService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacturacionPySExportacionesServiceImpl implements IFacturacionPySExportacionesService {

    private Logger LOGGER = Logger.getLogger(FacturacionPySExportacionesServiceImpl.class);

    private final String FICHERO_DEVOLUCIONES_SERVER_PATH = "facturacion.directorioFisicoDevolucionesJava";
    private final String FICHERO_DEVOLUCIONES_SERVER_DIR = "facturacion.directorioDevolucionesJava";
    private final String FICHERO_DEVOLUCIONES_ORACLE_DIR = "facturacion.directorioDevolucionesOracle";
    private final String FICHERO_DEVOLUCIONES_EXTENSION = ".d19";

    private final String FICHERO_TRANSFERENCIAS_SERVER_PATH = "facturacion.directorioFisicoAbonosBancosJava";
    private final String FICHERO_TRANSFERENCIAS_SERVER_DIR = "facturacion.directorioAbonosBancosJava";
    private final String FICHERO_TRANSFERENCIAS_ORACLE_DIR = "facturacion.directorioAbonosBancosOracle";
    private final String FICHERO_TRANSFERENCIAS_PREFIJO = "facturacion.prefijo.ficherosAbonos";

    private final String FICHERO_ADEUDOS_SERVER_PATH = "facturacion.directorioFisicoPagosBancosJava";
    private final String FICHERO_ADEUDOS_SERVER_DIR = "facturacion.directorioPagosBancosJava";
    private final String FICHERO_ADEUDOS_ORACLE_DIR = "facturacion.directorioBancosOracle";

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    private WSCommons commons;

    @Autowired
    private FacturacionHelper facturacionHelper;

    @Autowired
    private FacDisquetecargosExtendsMapper facDisquetecargosExtendsMapper;

    @Autowired
    private FacDisquetedevolucionesExtendsMapper facDisquetedevolucionesExtendsMapper;

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private GenDiccionarioMapper genDiccionarioMapper;

    @Autowired
    private FacLineadevoludisqbancoMapper facLineadevoludisqbancoMapper;

    @Autowired
    private FacFacturaincluidaendisqueteMapper facFacturaincluidaendisqueteMapper;

    @Autowired
    private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

    @Autowired
    private FacAbonoExtendsMapper facAbonoExtendsMapper;

    @Autowired
    private FacDisqueteabonosExtendsMapper facDisqueteabonosExtendsMapper;

    @Autowired
    private FacAbonoincluidoendisqueteExtendsMapper facAbonoincluidoendisqueteExtendsMapper;

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private FacSeriefacturacionBancoExtendsMapper facSeriefacturacionBancoExtendsMapper;

    @Autowired
    private FacPropositosExtendsMapper facPropositosExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private CenClienteMapper cenClienteMapper;

    @Autowired
    private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

    @Autowired
    private FacLineafacturaExtendsMapper facLineafacturaExtendsMapper;

    @Autowired
    private AdmContadorMapper admContadorMapper;

    @Autowired
    private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

    @Autowired
    private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

    @Autowired
    private FacturaAccionesHelper facturaAccionesHelper;

    @Override
    public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request)
            throws Exception {
        FicherosAdeudosDTO ficherosAdeudosDTO = new FicherosAdeudosDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosAdeudos() -> Entrada al servicio para obtener los ficheros de adeudos");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info("FacturacionPySExportacionesServiceImpl.getFicherosAdeudos() -> obteniendo datos de ficheros de adeudos");

            List<FicherosAdeudosItem> items = facDisquetecargosExtendsMapper.getFicherosAdeudos(item,
                    usuario.getIdinstitucion().toString());

            ficherosAdeudosDTO.setFicherosAdeudosItems(items);
        }

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosAdeudos() -> Salida del servicio  para obtener los ficheros de adeudos");

        return ficherosAdeudosDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResponseDTO nuevoFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
            throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        insertResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("nuevoFicheroAdeudos() -> Entrada al servicio para crear un fichero de adeudos");

        if (usuario != null) {

            // Comprobar los campos obligatorios
            if ( Objects.isNull(ficheroAdeudosItem.getFechaPresentacion())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosPrimeros())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosRecurrentes())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosCOR())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosB2B())) {
                throw new BusinessException("general.message.camposObligatorios");
            }

            // En caso de que el fichero sea para facturas sueltas, se establecen los estados de las facturas
            // correspondientes al estado LISTA_PARA_FICHERO
            if (Objects.nonNull(ficheroAdeudosItem.getFacturasGeneracion()) && !ficheroAdeudosItem.getFacturasGeneracion().isEmpty()) {
                for (String idFactura: ficheroAdeudosItem.getFacturasGeneracion()) {
                    FacFacturaKey key = new FacFacturaKey();
                    key.setIdinstitucion(usuario.getIdinstitucion());
                    key.setIdfactura(idFactura);
                    FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(key);
                    if (Objects.nonNull(factura) && factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO))) {
                        factura.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_LISTA_PARA_FICHERO));
                        facFacturaExtendsMapper.updateByPrimaryKey(factura);
                    }
                }

                procesarNuevoFicheroAdeudos("", "", ficheroAdeudosItem, usuario);

                // Se restauran aquellas facturas que no han podido formar parte de ningún fichero
                for (String idFactura: ficheroAdeudosItem.getFacturasGeneracion()) {
                    FacFacturaKey key = new FacFacturaKey();
                    key.setIdinstitucion(usuario.getIdinstitucion());
                    key.setIdfactura(idFactura);
                    FacFactura factura = facFacturaExtendsMapper.selectByPrimaryKey(key);
                    if (Objects.nonNull(factura) && factura.getEstado().equals(Short.parseShort(SigaConstants.ESTADO_FACTURA_LISTA_PARA_FICHERO))) {
                        factura.setEstado(Short.parseShort(SigaConstants.ESTADO_FACTURA_BANCO));
                        facFacturaExtendsMapper.updateByPrimaryKey(factura);
                    }
                }
            } else if (Objects.nonNull(ficheroAdeudosItem.getFacturacionesGeneracion()) && !ficheroAdeudosItem.getFacturacionesGeneracion().isEmpty()) {
                for (FacFacturacionprogramadaItem facturacion: ficheroAdeudosItem.getFacturacionesGeneracion()) {
                    FacDisquetecargosExample disqueteExample = new FacDisquetecargosExample();
                    disqueteExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                            .andIdseriefacturacionEqualTo(Long.parseLong(facturacion.getIdSerieFacturacion()))
                            .andIdprogramacionEqualTo(Long.parseLong(facturacion.getIdProgramacion()));

                    if (facDisquetecargosExtendsMapper.countByExample(disqueteExample) > 0) {
                        throw new BusinessException("facturacionPyS.ficheroAdeudos.error.facturacion");
                    } else {
                        procesarNuevoFicheroAdeudos(facturacion.getIdSerieFacturacion(), facturacion.getIdProgramacion(), ficheroAdeudosItem, usuario);
                    }
                }
            } else {
                throw new BusinessException("facturacionPyS.ficheroAdeudos.error.nuevo");
            }
        }

        LOGGER.info("nuevoFicheroAdeudos() -> Salida del servicio para crear un fichero de adeudos");

        return insertResponseDTO;
    }

    private void procesarNuevoFicheroAdeudos(String idSerieFacturacion, String idProgramacion, FicherosAdeudosItem ficheroAdeudosItem, AdmUsuarios usuario) throws Exception {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Object[] param_in = new Object[11]; // Parametros de entrada del PL

        // Ruta del fichero
        String pathFichero = getProperty(FICHERO_ADEUDOS_ORACLE_DIR).toUpperCase() + usuario.getIdinstitucion();

        // Parámetros de entrada
        param_in[0] = usuario.getIdinstitucion();
        param_in[1] = idSerieFacturacion;
        param_in[2] = idProgramacion;
        param_in[3] = formatDate.format(ficheroAdeudosItem.getFechaPresentacion());
        param_in[4] = formatDate.format(ficheroAdeudosItem.getFechaRecibosPrimeros());
        param_in[5] = formatDate.format(ficheroAdeudosItem.getFechaRecibosRecurrentes());
        param_in[6] = formatDate.format(ficheroAdeudosItem.getFechaRecibosCOR());
        param_in[7] = formatDate.format(ficheroAdeudosItem.getFechaRecibosB2B());
        param_in[8] = pathFichero;
        param_in[9] = usuario.getIdusuario();
        param_in[10] = usuario.getIdlenguaje();

        String[] resultado = commons.callPLProcedureFacturacionPyS(
                "{call Pkg_Siga_Cargos.Presentacion(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", 3, param_in);

        String[] codigosErrorFormato = {"5412", "5413", "5414", "5415", "5416", "5417", "5418", "5421", "5422"};
        if (Arrays.asList(codigosErrorFormato).contains(resultado[1])) {
            throw new BusinessException(resultado[2]);
        } else {
            if (!resultado[1].equals("0")) {
                throw new BusinessException("general.mensaje.error.bbdd");
            }
        }

        // Restaurar facturas a su estado inicial
        if (resultado[1].equals("0") && resultado[0].equals("0"))
            throw new BusinessException("facturacionPyS.ficheroAdeudos.error.nuevo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateResponseDTO actualizarFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
            throws Exception {
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        updateResponseDTO.setError(error);

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("actualizarFicheroAdeudos() -> Entrada al servicio para actualizar un fichero de adeudos");

        if (usuario != null) {
            // Comprobar los campos obligatorios
            if (Objects.isNull(ficheroAdeudosItem.getIdDisqueteCargos())
                    || Objects.isNull(ficheroAdeudosItem.getFechaPresentacion())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosPrimeros())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosRecurrentes())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosCOR())
                    || Objects.isNull(ficheroAdeudosItem.getFechaRecibosB2B())) {
                throw new BusinessException("general.message.camposObligatorios");
            }

            actualizarRegistroFicheroAdeudos(ficheroAdeudosItem, usuario);

            Object[] param_in = new Object[9]; // Parametros de entrada del PL

            // Ruta del fichero
            String pathFicheroOracle = getProperty(FICHERO_ADEUDOS_ORACLE_DIR).toUpperCase() + usuario.getIdinstitucion();
            String pathFicheroServer = getProperty(FICHERO_ADEUDOS_SERVER_PATH) + getProperty(FICHERO_ADEUDOS_SERVER_DIR);

            String sBarra = "";
            if (pathFicheroServer.indexOf("/") > -1) sBarra = "/";
            if (pathFicheroServer.indexOf("\\") > -1) sBarra = "\\";
            pathFicheroServer += sBarra + usuario.getIdinstitucion().toString();

            // Se borran todos os ficheros que contenga el identificador del fichero de abonos
            File directorioFicheros = new File(pathFicheroServer);
            if (directorioFicheros.exists() && directorioFicheros.isDirectory()){
                File[] ficheros = directorioFicheros.listFiles();
                for (int x=0; x<ficheros.length; x++){
                    String nombreFichero = ficheros[x].getName();
                    if (nombreFichero.startsWith(ficheroAdeudosItem.getIdDisqueteCargos() + ".")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        nombreFichero = sdf.format(new Date()) + "_" + nombreFichero;

                        File newFile = new File(directorioFicheros, nombreFichero);
                        ficheros[x].renameTo(newFile);
                        // Se cambia el nombre del archivo para que no se interponga en la nueva generación
                    }
                }
            }


            // Parámetros de entrada
            param_in[0] = usuario.getIdinstitucion();
            param_in[1] = Integer.parseInt(ficheroAdeudosItem.getIdDisqueteCargos());
            param_in[2] = formatDate.format(ficheroAdeudosItem.getFechaPresentacion());
            param_in[3] = formatDate.format(ficheroAdeudosItem.getFechaRecibosPrimeros());
            param_in[4] = formatDate.format(ficheroAdeudosItem.getFechaRecibosRecurrentes());
            param_in[5] = formatDate.format(ficheroAdeudosItem.getFechaRecibosCOR());
            param_in[6] = formatDate.format(ficheroAdeudosItem.getFechaRecibosB2B());
            param_in[7] = pathFicheroOracle;
            param_in[8] = Integer.parseInt(usuario.getIdlenguaje());

            String[] resultado = commons.callPLProcedureFacturacionPyS(
                    "{call PKG_SIGA_CARGOS.Regenerar_Presentacion(?,?,?,?,?,?,?,?,?,?,?)}", 2, param_in);

            String[] codigosErrorFormato = {"5412", "5413", "5414", "5415", "5416", "5417", "5418", "5421", "5422"};
            if (Arrays.asList(codigosErrorFormato).contains(resultado[0])) {
                throw new BusinessException(resultado[1]);
            } else {
                if (!resultado[0].equals("0")) {
                    throw new BusinessException("general.mensaje.error.bbdd");
                }
            }
        }

        LOGGER.info("actualizarFicheroAdeudos() -> Salida del servicio para actualizar un fichero de adeudos");

        return updateResponseDTO;
    }

    private void actualizarRegistroFicheroAdeudos(FicherosAdeudosItem ficheroAdeudos, AdmUsuarios usuario) {

        FacDisquetecargosKey key = new FacDisquetecargosKey();
        key.setIdinstitucion(usuario.getIdinstitucion());
        key.setIddisquetecargos(Long.parseLong(ficheroAdeudos.getIdDisqueteCargos()));

        FacDisquetecargos facDisquete = this.facDisquetecargosExtendsMapper.selectByPrimaryKey(key);
        facDisquete.setFechapresentacion(ficheroAdeudos.getFechaPresentacion());
        facDisquete.setFecharecibosprimeros(ficheroAdeudos.getFechaRecibosPrimeros());
        facDisquete.setFecharecibosrecurrentes(ficheroAdeudos.getFechaRecibosRecurrentes());
        facDisquete.setFechareciboscor1(ficheroAdeudos.getFechaRecibosCOR());
        facDisquete.setFecharecibosb2b(ficheroAdeudos.getFechaRecibosB2B());

        this.facDisquetecargosExtendsMapper.updateByPrimaryKey(facDisquete);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteResponseDTO eliminarFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
            throws Exception {
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        deleteResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("deleteResponseDTO() -> Entrada al servicio para eliminar un fichero de adeudos");

        if (usuario != null) {

            //Buscar las facturas que no tengan estado pagado por banco
            List<FacFactura> facturasNoPagadas = new ArrayList<>();
            facturasNoPagadas = facHistoricofacturaExtendsMapper.facturasDevueltasEnDisquete(ficheroAdeudosItem.getIdDisqueteCargos(), ficheroAdeudosItem.getIdInstitucion());


            //Devolver las factruas no pagadas
            if(facturasNoPagadas.size() > 0){
                GenDiccionarioKey diccionarioKey = new GenDiccionarioKey();
                diccionarioKey.setIdrecurso("general.message.error.realiza.accion");
                diccionarioKey.setIdlenguaje(usuario.getIdlenguaje());

                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append(genDiccionarioMapper.selectByPrimaryKey(diccionarioKey).getDescripcion() + ": ");
                int cont = 0;
                for (FacFactura facturasDevueltas : facturasNoPagadas){
                    errorMessage.append(facturasDevueltas.getNumerofactura());
                    cont++;
                    if(cont == 10){
                        errorMessage.append(" ...");
                        break;
                    }
                    else {
                        errorMessage.append(", ");
                    }
                }
                if(cont != 10){
                    errorMessage.deleteCharAt(errorMessage.length()-2);
                }
                throw new BusinessException(errorMessage.toString());
            }

            else {

                //Buscar pagos del disquete
                FacFacturaincluidaendisqueteExample facFacturaincluidaendisqueteExample = new FacFacturaincluidaendisqueteExample();

                facFacturaincluidaendisqueteExample.createCriteria().
                        andIdinstitucionEqualTo(Short.valueOf(ficheroAdeudosItem.getIdInstitucion())).
                        andIddisquetecargosEqualTo(Long.valueOf(ficheroAdeudosItem.getIdDisqueteCargos()));

                List<FacFacturaincluidaendisquete> listaPagos = facFacturaincluidaendisqueteMapper.selectByExample(facFacturaincluidaendisqueteExample);


                //Eliminar los pagos del historico de factura y restaurar la factura, borrar pago por banco y finalmente el disquete
                FacFactura facturaActual;
                FacFacturaKey facturaKey = new FacFacturaKey();
                facturaKey.setIdinstitucion(Short.valueOf(ficheroAdeudosItem.getIdInstitucion()));

                for (FacFacturaincluidaendisquete pago : listaPagos){

                    facturaKey.setIdfactura(pago.getIdfactura());

                    facturaActual = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

                    FacHistoricofacturaExample historicofacturaExample = new FacHistoricofacturaExample();
                    historicofacturaExample.createCriteria().andIdfacturaEqualTo(facturaActual.getIdfactura()).
                            andIdinstitucionEqualTo(facturaActual.getIdinstitucion());

                    List<FacHistoricofactura> historicoActual = facHistoricofacturaExtendsMapper.selectByExample(historicofacturaExample);
                    FacHistoricofactura estadoAnterior = historicoActual.get(historicoActual.size() - 2);

                    facturaActual.setEstado(estadoAnterior.getEstado());
                    facturaActual.setImptotalporpagar(estadoAnterior.getImptotalporpagar());
                    facturaActual.setImptotalanticipado(estadoAnterior.getImptotalanticipado());
                    facturaActual.setImptotalpagado(estadoAnterior.getImptotalpagado());
                    facturaActual.setImptotalpagadoporbanco(estadoAnterior.getImptotalpagadoporbanco());
                    facturaActual.setComisionidfactura(estadoAnterior.getComisionidfactura());
                    facturaActual.setIdcuentadeudor(estadoAnterior.getIdcuentadeudor());
                    facturaActual.setIdpersonadeudor(estadoAnterior.getIdpersonadeudor());
                    facturaActual.setIdformapago(estadoAnterior.getIdformapago());
                    facturaActual.setUsumodificacion(estadoAnterior.getUsumodificacion());
                    facturaActual.setFechamodificacion(estadoAnterior.getFechamodificacion());
                    facturaActual.setImptotalpagadosolocaja(estadoAnterior.getImptotalpagadosolocaja());
                    facturaActual.setImptotalpagadoporcaja(estadoAnterior.getImptotalpagadoporcaja());
                    facturaActual.setImptotalpagadosolotarjeta(estadoAnterior.getImptotalpagadosolotarjeta());
                    facturaActual.setImptotalcompensado(estadoAnterior.getImptotalcompensado());

                    facHistoricofacturaExtendsMapper.deleteByPrimaryKey(historicoActual.get(historicoActual.size()-1));
                    facFacturaExtendsMapper.updateByPrimaryKey(facturaActual);
                    facFacturaincluidaendisqueteMapper.deleteByPrimaryKey(pago);
                }

                FacDisquetecargosKey disquetecargosKey = new FacDisquetecargosKey();
                disquetecargosKey.setIddisquetecargos(Long.valueOf(ficheroAdeudosItem.getIdDisqueteCargos()));
                disquetecargosKey.setIdinstitucion(Short.valueOf(ficheroAdeudosItem.getIdInstitucion()));

                facDisquetecargosExtendsMapper.deleteByPrimaryKey(disquetecargosKey);

                // A continuación, se elimina el fichero
                // Ruta del fichero
                String pathFicheroServer = getProperty(FICHERO_ADEUDOS_SERVER_PATH) + getProperty(FICHERO_ADEUDOS_SERVER_DIR);

                String sBarra = "";
                if (pathFicheroServer.indexOf("/") > -1) sBarra = "/";
                if (pathFicheroServer.indexOf("\\") > -1) sBarra = "\\";
                pathFicheroServer += sBarra + usuario.getIdinstitucion().toString();

                // Se borran todos os ficheros que contenga el identificador del fichero de adeudos
                File directorioFicheros = new File(pathFicheroServer);
                if (directorioFicheros.exists() && directorioFicheros.isDirectory()){
                    File[] ficheros = directorioFicheros.listFiles();
                    for (int x=0; x<ficheros.length; x++){
                        String nombreFichero = ficheros[x].getName();
                        if (nombreFichero.startsWith(ficheroAdeudosItem.getIdDisqueteCargos() + ".")) {
                            ficheros[x].delete();
                        }
                    }
                }
            }

            deleteResponseDTO.setStatus(HttpStatus.OK.toString());
        }

        LOGGER.info("deleteResponseDTO() -> Salida del servicio para eliminar un fichero de adeudos");

        return deleteResponseDTO;
    }

    @Override
    public ResponseEntity<InputStreamResource> descargarFicheroAdeudos(List<FicherosAdeudosItem> ficheroAdeudosItems, HttpServletRequest request) throws Exception {
        ResponseEntity<InputStreamResource> res = null;

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("descargarFicheroAdeudos() -> Entrada al servicio para descargar ficheros de adeudos");

        String pathFichero = getProperty(FICHERO_ADEUDOS_SERVER_PATH) + getProperty(FICHERO_ADEUDOS_SERVER_DIR)
                + File.separator + usuario.getIdinstitucion();

        List<File> listaFicheros = ficheroAdeudosItems.stream().flatMap(item -> {
            FacDisquetecargosKey key = new FacDisquetecargosKey();
            key.setIdinstitucion(usuario.getIdinstitucion());
            key.setIddisquetecargos(Long.parseLong(item.getIdDisqueteCargos()));
            FacDisquetecargos disquetecargos = facDisquetecargosExtendsMapper.selectByPrimaryKey(key);

            List<File> files = new ArrayList<>();
            if (Objects.nonNull(disquetecargos)) {
                String nombreFichero = disquetecargos.getNombrefichero();
                File directorioFicheros = new File(pathFichero);

                // Se buscan todos los ficheros que coincidan con el nombre del fichero
                if (directorioFicheros.exists() && Objects.nonNull(disquetecargos.getNombrefichero())) {
                    File[] ficheros = directorioFicheros.listFiles();
                    String nombreFicheroListadoSinExtension, nombreFicheroGeneradoSinExtension;
                    for (File file: ficheros){
                        nombreFicheroListadoSinExtension = (file.getName().indexOf(".") > 0)
                                ? file.getName().substring(0, file.getName().indexOf(".")) : file.getName();
                        nombreFicheroGeneradoSinExtension = (nombreFichero.indexOf(".") > 0)
                                ? nombreFichero.substring(0, nombreFichero.indexOf(".")) : nombreFichero;
                        if(nombreFicheroGeneradoSinExtension.equalsIgnoreCase(nombreFicheroListadoSinExtension)){
                            files.add(file);
                        }
                    }
                }
            }

            return files.stream();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        // Construcción de la respuesta para uno o más archivos
        res = SIGAServicesHelper.descargarFicheros(listaFicheros,
                MediaType.parseMediaType("application/octet-stream"),
                MediaType.parseMediaType("application/zip"), "LOG_FICHERO_ADEUDOS");

        LOGGER.info("descargarFicheroAdeudos() -> Salida del servicio para descargar ficheros de adeudos");

        return res;
    }

    @Override
    public FicherosDevolucionesDTO getFicherosDevoluciones(FicherosDevolucionesItem item, HttpServletRequest request)
            throws Exception {
        FicherosDevolucionesDTO ficherosDevolucionesDTO = new FicherosDevolucionesDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosDevoluciones() -> Entrada al servicio para obtener los ficheros de devoluciones");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info(
                    "FacturacionPySExportacionesServiceImpl.getFicherosDevoluciones() -> obteniendo datos de ficheros de devoluciones");

            List<FicherosDevolucionesItem> items = facDisquetedevolucionesExtendsMapper.getFicherosDevoluciones(item,
                    usuario.getIdinstitucion().toString());

            ficherosDevolucionesDTO.setFicherosDevolucionesItems(items);
        }

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosDevoluciones() -> Salida del servicio  para obtener los ficheros de devoluciones");

        return ficherosDevolucionesDTO;
    }

    @Override
    public InsertResponseDTO nuevoFicheroDevoluciones(FacDisqueteDevolucionesNuevoItem ficherosDevolucionesItem, HttpServletRequest request)
            throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        insertResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        String[] codigosErrorFormato = {"20000", "5399", "5402"};

        LOGGER.info("nuevoFicheroDevoluciones() -> Entrada al servicio para crear un fichero de devoluciones");

        if (usuario != null && ficherosDevolucionesItem != null) {
            String rutaServidor = getProperty(FICHERO_DEVOLUCIONES_SERVER_PATH) + getProperty(FICHERO_DEVOLUCIONES_SERVER_DIR);
            String rutaOracle =  getProperty(FICHERO_DEVOLUCIONES_ORACLE_DIR).toUpperCase() + usuario.getIdinstitucion();

            String idDisqueteDevoluciones = facDisquetedevolucionesExtendsMapper.getNextIdDisqueteDevoluciones(usuario.getIdinstitucion());

            // Obtenemos la ruta del servidor
            rutaServidor += File.separator + usuario.getIdinstitucion();
            String nombreFichero = idDisqueteDevoluciones + FICHERO_DEVOLUCIONES_EXTENSION;

            // Procesar y subir archivo para el fichero de devoluciones
            InputStream newFile = ficherosDevolucionesItem.getUploadFile() != null
                    ? ficherosDevolucionesItem.getUploadFile().getInputStream() : null;
            subirFicheroDisquete(newFile, rutaServidor, nombreFichero);

            boolean conComision = ficherosDevolucionesItem.getConComision() != null ? ficherosDevolucionesItem.getConComision() : false;
            procesarNuevoFicheroDevoluciones(idDisqueteDevoluciones, nombreFichero, rutaOracle, conComision, usuario);
        }

        LOGGER.info("nuevoFicheroDevoluciones() -> Salida del servicio para crear un fichero de devoluciones");

        return insertResponseDTO;
    }

    private void subirFicheroDisquete(InputStream ficheroOriginal, String rutaServidor, String nombreFichero) {
        LOGGER.info("subirFicheroDisquete() -> Entrada al servicio para subir el fichero de devoluciones");

        String rutaFichero = rutaServidor + File.separator + nombreFichero;
        InputStream stream =null;
        BufferedReader rdr = null;
        BufferedWriter out = null;

        try {
            stream = ficheroOriginal;
            File dir = new File(rutaServidor);
            dir.mkdirs();

            rdr = new BufferedReader(new InputStreamReader(stream));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaFichero),"ISO-8859-1"));

            boolean esXML = false;
            boolean controlarDocument = true;

            String nombreFicheroDevoluciones = nombreFichero;
            if (nombreFicheroDevoluciones.toUpperCase().endsWith("XML")) {
                esXML = true;
            }

            String linea = "";
            while (linea!=null && linea.trim().equals("")) {
                linea = rdr.readLine();
            }

            while (linea!=null) {

                String lineaFichero = linea;

                // Control que valida si es un fichero XML
                if (!esXML && linea.indexOf("<")>=0) {
                    esXML = true;
                }

                // Control que realiza una serie de cambios cuando es XML
                if (esXML) {

                    // Comienzo a buscar por la primera letra
                    int buscador = 0;

                    // Control de longitud de linea
                    while (buscador < linea.length()) {

                        // Busco < (principio de etiqueta)
                        buscador = linea.indexOf("<", buscador);

                        // Si no tiene etiqueta pinto la linea
                        if (buscador < 0) {
                            break;

                        }

                        // Si tiene < pasamos de letra
                        buscador++;

                        // Comprueba que tenga por lo menos alguna letra mas
                        if (linea.length() < buscador) {
                            break;
                        }

                        // Obtengo la siguiente letra al <
                        char letra = linea.charAt(buscador);

                        // Si no encuentra </ es que es una apertura de etiqueta
                        if (letra != '/') {

                            final String etiquetaDocument = "DOCUMENT";

                            // Control de si hay que validar la etiqueta DOCUMENT
                            if (controlarDocument && linea.length() > buscador + etiquetaDocument.length()) {

                                // Obtengo el nombre de la etiqueta
                                String buscaDocument = linea.substring(buscador, buscador + etiquetaDocument.length());

                                // Compruebo si la etiqueta es DOCUMENT
                                if (buscaDocument.equalsIgnoreCase(etiquetaDocument)) {

                                    // Hay que buscar el final de la etiqueta DOCUMENT
                                    int buscadorDocument = linea.indexOf(">", buscador + etiquetaDocument.length());

                                    // Encuento el final de la etiqueta DOCUMENT
                                    if (buscadorDocument > 0) {

                                        // Elimino los atributos de la etiqueta DOCUMENT
                                        linea = linea.substring(0, buscador + etiquetaDocument.length()) + linea.substring(buscadorDocument);

                                        // Indico que hay que buscar despues de la etiqueta DOCUMENT
                                        buscador += etiquetaDocument.length();

                                        // Indicamos que ya hemos controlado la etiqueta DOCUMENT
                                        controlarDocument = false;
                                    }
                                }
                            }

                            // Pasamos a la siguiente letra
                            buscador++;
                            continue;
                        }

                        // Encuentro </ y buscamos el final de la etiqueta
                        buscador = linea.indexOf(">", buscador);

                        // Encuento el final de la etiqueta </...>
                        if (buscador<0) {
                            break;
                        }

                        // Pasamos a la siguiente letra >
                        buscador++;

                        // ponemos un retorno de linea al finalizar cada etiqueta final, porque asi evitamos un xml en una linea inmensa
                        lineaFichero = linea.substring(0, buscador);

                        // Escribimos la linea
                        out.write(lineaFichero);
                        out.write("\n");

                        // Eliminamos los datos escritos
                        linea = linea.substring(buscador);

                        // Volvemos a empezar
                        buscador = 0;
                    }

                    // Guardamos la linea tal como esta ahora
                    lineaFichero = linea;
                } // FIN WHILE

                // Comprueba si queda algo por escribir de la linea
                if (!lineaFichero.trim().equals("")) {

                    // Escribimos la linea
                    out.write(lineaFichero);
                    out.write("\n");
                }

                // Obtenemos la siguiente linea
                linea = "";
                while (linea!=null && linea.trim().equals("")) {
                    linea = rdr.readLine();
                }
            }

            // close the stream
            stream.close();
            out.close();
            rdr.close();
        } catch (FileNotFoundException e) {
            throw new BusinessException("facturacion.nuevoFichero.literal.errorAcceso");
        } catch (IOException e) {
            throw new BusinessException("facturacion.nuevoFichero.literal.errorLectura");
        }

        LOGGER.info("subirFicheroDisquete() -> Saliendo del servicio para subir el fichero de devoluciones");
    }

    @Transactional(rollbackFor = Exception.class)
    private void procesarNuevoFicheroDevoluciones(String idDisqueteDevoluciones, String nombreFichero,
                                         String rutaOracle, Boolean conComision, AdmUsuarios usuario) throws Exception {

        LOGGER.info("nuevoFicheroDevoluciones() -> Iniciando servicio en background para la generación de fichero de devoluciones");

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
                        .andIddisquetedevolucionesEqualTo(Long.parseLong(idDisqueteDevoluciones));

                List<FacLineadevoludisqbanco> devoluciones = facLineadevoludisqbancoMapper.selectByExample(devolucionesExample);

                // Aplicamos la comision a cada devolucion
                for (FacLineadevoludisqbanco devolucion : devoluciones) {
                    if (conComision)
                        facturaAccionesHelper.aplicarComisionAFactura(devolucion, null, usuario);
                }
            }
        } else {
            throw new BusinessException(resultado[1]);
        }

        LOGGER.info("nuevoFicheroDevoluciones() -> Finalizando servicio en background para la generación de fichero de devoluciones");
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteResponseDTO eliminarFicheroDevoluciones(FicherosDevolucionesItem ficherosDevolucionesItem, HttpServletRequest request)
            throws Exception {
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        deleteResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("eliminarFicheroDevoluciones() -> Entrada al servicio para eliminar un fichero de devoluciones");

        if (usuario != null) {
            // ultima entrada en el historico

            FacturaItem filtros = new FacturaItem();
            filtros.setIdentificadorDevolucion(ficherosDevolucionesItem.getIdDisqueteDevoluciones());
            List<FacturaItem> facturasDisquete = facFacturaExtendsMapper.getFacturas(filtros, usuario.getIdinstitucion().toString(),usuario.getIdlenguaje());

            //Devolver las factruas no devueltas
            if(facturasDisquete.size() > 0){
                GenDiccionarioKey diccionarioKey = new GenDiccionarioKey();
                diccionarioKey.setIdrecurso("general.message.error.realiza.accion");
                diccionarioKey.setIdlenguaje(usuario.getIdlenguaje());


                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append(genDiccionarioMapper.selectByPrimaryKey(diccionarioKey).getDescripcion() + ": ");
                int cont = 0;
                for (FacturaItem facturasDevueltas : facturasDisquete) {
                    if (!facturasDevueltas.getIdEstado().equals(SigaConstants.ESTADO_FACTURA_DEVUELTA)) {
                        errorMessage.append(facturasDevueltas.getNumeroFactura());
                        cont++;
                        if(cont == 10){
                            errorMessage.append(" ...");
                            break;
                        }
                        else {
                            errorMessage.append(", ");
                        }

                    }

                }
                if(cont != 10){
                    errorMessage.deleteCharAt(errorMessage.length()-2);
                }

                if (cont > 0) {
                    throw new BusinessException(errorMessage.toString());
                }

            }

            FacLineadevoludisqbancoExample lineasDisqueteExample = new FacLineadevoludisqbancoExample();
            lineasDisqueteExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andIddisquetedevolucionesEqualTo(Long.parseLong(ficherosDevolucionesItem.getIdDisqueteDevoluciones()));

            // Se itera sobre las facturas asociadas al disquete de devoluciones y se eliminan las relaciones
            List<FacLineadevoludisqbanco> lineasDisquete = facLineadevoludisqbancoMapper.selectByExample(lineasDisqueteExample);
            for (FacLineadevoludisqbanco linea: lineasDisquete) {
                // Buscamos la línea que contiene la información de la factura
                FacFacturaincluidaendisqueteKey facturaincluidaendisqueteKey = new FacFacturaincluidaendisqueteKey();
                facturaincluidaendisqueteKey.setIdinstitucion(linea.getIdinstitucion());
                facturaincluidaendisqueteKey.setIddisquetecargos(linea.getIddisquetecargos());
                facturaincluidaendisqueteKey.setIdfacturaincluidaendisquete(linea.getIdfacturaincluidaendisquete());

                FacFacturaincluidaendisquete facturaincluidaendisquete = facFacturaincluidaendisqueteMapper.selectByPrimaryKey(facturaincluidaendisqueteKey);

                // Buscamos la factura asociada al diquete de cargos
                FacFacturaKey facturaKey = new FacFacturaKey();
                facturaKey.setIdinstitucion(facturaincluidaendisquete.getIdinstitucion());
                facturaKey.setIdfactura(facturaincluidaendisquete.getIdfactura());

                // Restauramos la factura al estado anterior
                FacFactura facturaActual = facFacturaExtendsMapper.selectByPrimaryKey(facturaKey);

                FacHistoricofacturaExample historicofacturaExample = new FacHistoricofacturaExample();
                historicofacturaExample.createCriteria().andIdfacturaEqualTo(facturaActual.getIdfactura()).
                        andIdinstitucionEqualTo(facturaActual.getIdinstitucion());

                List<FacHistoricofactura> historicoActual = facHistoricofacturaExtendsMapper.selectByExample(historicofacturaExample);
                FacHistoricofactura estadoAnterior = historicoActual.get(historicoActual.size() - 2);

                facturaActual.setEstado(estadoAnterior.getEstado());
                facturaActual.setImptotalporpagar(estadoAnterior.getImptotalporpagar());
                facturaActual.setImptotalanticipado(estadoAnterior.getImptotalanticipado());
                facturaActual.setImptotalpagado(estadoAnterior.getImptotalpagado());
                facturaActual.setImptotalpagadoporbanco(estadoAnterior.getImptotalpagadoporbanco());
                facturaActual.setComisionidfactura(estadoAnterior.getComisionidfactura());
                facturaActual.setIdcuentadeudor(estadoAnterior.getIdcuentadeudor());
                facturaActual.setIdpersonadeudor(estadoAnterior.getIdpersonadeudor());
                facturaActual.setIdformapago(estadoAnterior.getIdformapago());
                facturaActual.setUsumodificacion(estadoAnterior.getUsumodificacion());
                facturaActual.setFechamodificacion(estadoAnterior.getFechamodificacion());
                facturaActual.setImptotalpagadosolocaja(estadoAnterior.getImptotalpagadosolocaja());
                facturaActual.setImptotalpagadoporcaja(estadoAnterior.getImptotalpagadoporcaja());
                facturaActual.setImptotalpagadosolotarjeta(estadoAnterior.getImptotalpagadosolotarjeta());
                facturaActual.setImptotalcompensado(estadoAnterior.getImptotalcompensado());

                facHistoricofacturaExtendsMapper.deleteByPrimaryKey(historicoActual.get(historicoActual.size()-1));
                facFacturaExtendsMapper.updateByPrimaryKey(facturaActual);
                facLineadevoludisqbancoMapper.deleteByPrimaryKey(linea);
            }

            // Finalmente se elimina el disquete de devoluciones
            FacDisquetedevolucionesKey key = new FacDisquetedevolucionesKey();
            key.setIdinstitucion(usuario.getIdinstitucion());
            key.setIddisquetedevoluciones(Long.parseLong(ficherosDevolucionesItem.getIdDisqueteDevoluciones()));

            facDisquetedevolucionesExtendsMapper.deleteByPrimaryKey(key);

            // A continuación, se elimina el fichero
            // Ruta del fichero
            String pathFicheroServer = getProperty(FICHERO_DEVOLUCIONES_SERVER_PATH) + getProperty(FICHERO_DEVOLUCIONES_SERVER_DIR);

            String sBarra = "";
            if (pathFicheroServer.indexOf("/") > -1) sBarra = "/";
            if (pathFicheroServer.indexOf("\\") > -1) sBarra = "\\";
            pathFicheroServer += sBarra + usuario.getIdinstitucion().toString();

            // Se borran todos os ficheros que contenga el identificador del fichero de devoluciones
            File directorioFicheros = new File(pathFicheroServer);
            if (directorioFicheros.exists() && directorioFicheros.isDirectory()){
                File[] ficheros = directorioFicheros.listFiles();
                for (int x=0; x<ficheros.length; x++){
                    String nombreFichero = ficheros[x].getName();
                    if (nombreFichero.startsWith(ficherosDevolucionesItem.getIdDisqueteDevoluciones() + ".")) {
                        ficheros[x].delete();
                    }
                }
            }

            deleteResponseDTO.setStatus(HttpStatus.OK.toString());
        }

        LOGGER.info("eliminarFicheroDevoluciones() -> Salida del servicio para eliminar un fichero de devoluciones");

        return deleteResponseDTO;
    }

    @Override
    public ResponseEntity<InputStreamResource> descargarFicheroDevoluciones(List<FicherosDevolucionesItem> ficheroDevolucionesItems, HttpServletRequest request) throws Exception {
        ResponseEntity<InputStreamResource> res = null;

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("descargarFicheroDevoluciones() -> Entrada al servicio para descargar ficheros de devoluciones");

        String pathFichero = getProperty(FICHERO_DEVOLUCIONES_SERVER_PATH) + getProperty(FICHERO_DEVOLUCIONES_SERVER_DIR)
                + File.separator + usuario.getIdinstitucion();

        List<File> listaFicheros = ficheroDevolucionesItems.stream().map(item -> {
            FacDisquetedevolucionesKey key = new FacDisquetedevolucionesKey();
            key.setIdinstitucion(usuario.getIdinstitucion());
            key.setIddisquetedevoluciones(string2Long(item.getIdDisqueteDevoluciones()));
            FacDisquetedevoluciones disquetedevoluciones = facDisquetedevolucionesExtendsMapper.selectByPrimaryKey(key);

            List<File> files = new ArrayList<>();
            File file = null;
            if (Objects.nonNull(disquetedevoluciones) && Objects.nonNull(disquetedevoluciones.getNombrefichero())) {
                String nombreFichero = pathFichero + File.separator + disquetedevoluciones.getNombrefichero();
                file = new File(nombreFichero);
            }

            return file;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        // Construcción de la respuesta para uno o más archivos
        res = SIGAServicesHelper.descargarFicheros(listaFicheros,
                MediaType.parseMediaType("application/octet-stream"),
                MediaType.parseMediaType("application/zip"), "LOG_FICHERO_DEVOLUCIONES");

        LOGGER.info("descargarFicheroDevoluciones() -> Salida del servicio para descargar ficheros de devoluciones");

        return res;
    }

    @Override
    public FicherosAbonosDTO getFicherosTransferencias(FicherosAbonosItem item, HttpServletRequest request)
            throws Exception {
        FicherosAbonosDTO ficherosAbonosDTO = new FicherosAbonosDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosTransferencias() -> Entrada al servicio para obtener los ficheros de transferencias");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info(
                    "FacturacionPySExportacionesServiceImpl.getFicherosTransferencias() -> obteniendo datos de ficheros de transferencias");

            List<FicherosAbonosItem> items = facDisqueteabonosExtendsMapper.getFicherosTransferencias(item,
                    usuario.getIdinstitucion().toString(), usuario.getIdlenguaje());

            ficherosAbonosDTO.setFicherosAbonosItems(items);
        }

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosTransferencias() -> Salida del servicio  para obtener los ficheros de transferencias");

        return ficherosAbonosDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResponseDTO nuevoFicheroTransferencias(List<FacturaItem> abonoItems, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.nuevoFicheroTransferencias() -> Entrada al servicio para generar un nuevo fichero de transferencias");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            String fcs = "0";

            List<FacSeriefacturacionBanco> bancosSufijos = facSeriefacturacionBancoExtendsMapper.getBancosSufijos(usuario.getIdinstitucion());

            for (FacSeriefacturacionBanco banco: bancosSufijos) {

                String bancosCodigo = banco.getBancosCodigo();
                Short idSufijo = banco.getIdsufijo();
                String idPropositoSEPA = getParametro("FAC", "PROPOSITO_TRANSFERENCIA_SEPA", usuario.getIdinstitucion());
                String idPropositoOtros = getParametro("FAC", "PROPOSITO_OTRA_TRANSFERENCIA", usuario.getIdinstitucion());


				if (idPropositoSEPA.equals(""))
					idPropositoSEPA = getParametro("FAC", "PROPOSITO_TRANSFERENCIA_SEPA", Short.parseShort("0"));
				if (idPropositoOtros.equals(""))
					idPropositoOtros = getParametro("FAC", "PROPOSITO_OTRA_TRANSFERENCIA",  Short.parseShort("0"));


                // Propósito SEPA
                if (!UtilidadesString.esCadenaVacia(idPropositoSEPA)) {
                    FacPropositosExample propositosExample = new FacPropositosExample();
                    propositosExample.createCriteria().andCodigoEqualTo(idPropositoSEPA);
                    List<FacPropositos> propositos = facPropositosExtendsMapper.selectByExample(propositosExample);
                    if (propositos != null && !propositos.isEmpty())
                        idPropositoSEPA = propositos.get(0).getIdproposito().toString();
                }

                // Propósito Otros
                if (!UtilidadesString.esCadenaVacia(idPropositoOtros)) {
                    FacPropositosExample propositosExample = new FacPropositosExample();
                    propositosExample.createCriteria().andCodigoEqualTo(idPropositoOtros);
                    List<FacPropositos> propositos = facPropositosExtendsMapper.selectByExample(propositosExample);
                    if (propositos != null && !propositos.isEmpty())
                        idPropositoOtros = propositos.get(0).getIdproposito().toString();
                }

                if (idSufijo == null) {
                    throw new BusinessException("facturacion.ficheroBancarioTransferencias.errorSufijosSerie.mensajeCondicionesIncumplidas");
                }

                // Se agrupan los abonos de la petición por su banco y sufijo
                List<FacAbono> abonosBanco = facAbonoExtendsMapper.getAbonosBanco(usuario.getIdinstitucion(), bancosCodigo, idSufijo,
                        abonoItems.stream().map(a -> a.getIdAbono()).collect(Collectors.toList()));

                if (abonosBanco != null && !abonosBanco.isEmpty()) {
                    int resultado = this.prepararFicheroTransferencias(fcs, usuario.getIdinstitucion(), bancosCodigo, idSufijo, abonosBanco, idPropositoSEPA, idPropositoOtros, usuario);

                    if (resultado == -1) {
                        throw new BusinessException("general.mensaje.error.bbdd");
                    }
                }
            }

        }

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.nuevoFicheroTransferencias() -> Salida del servicio  para generar un nuevo fichero de transferencias");

        return insertResponseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResponseDTO nuevoFicheroTransferenciasSjcs(List<FacturaItem> abonoItems, HttpServletRequest request) throws Exception {
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuarios usuario = new AdmUsuarios();
        int resultado = -1;

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.nuevoFicheroTransferencias() -> Entrada al servicio para generar un nuevo fichero de transferencias");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            String fcs = "1";

            List<FicherosAbonosItem> bancosSufijos = facAbonoExtendsMapper.getBancosSufijosSjcs(usuario.getIdinstitucion());

            for (FicherosAbonosItem banco: bancosSufijos) {

                String bancosCodigo = banco.getBancosCodigo();
                Short idSufijo = banco.getIdSufijo() == null ? null : Short.parseShort(banco.getIdSufijo());
                String idPropositoSEPA = banco.getPropSEPA();
                String idPropositoOtros = banco.getPropOtros();

                // Se agrupan los abonos de la petición por su banco y sufijo
                List<FacAbono> abonosBanco = facAbonoExtendsMapper.getAbonosBancoSjcs(usuario.getIdinstitucion(), bancosCodigo, idSufijo,
                        abonoItems.stream().map(a -> a.getIdAbono()).collect(Collectors.toList()));

                if (abonosBanco != null && !abonosBanco.isEmpty()) {
                    resultado = this.prepararFicheroTransferencias(fcs, usuario.getIdinstitucion(), bancosCodigo, idSufijo, abonosBanco, idPropositoSEPA, idPropositoOtros, usuario);
                }

            }

            if (resultado == -1) {
                throw new BusinessException("general.mensaje.error.bbdd");
            }

        }

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.nuevoFicheroTransferencias() -> Salida del servicio  para generar un nuevo fichero de transferencias");

        return insertResponseDTO;
    }

    private int prepararFicheroTransferencias(String fcs, Short idInstitucion,
                                              String bancosCodigo,
                                              Short idSufijo,
                                              List<FacAbono> abonosBanco,
                                              String idPropositoSEPA,
                                              String idPropositoOtros,
                                              AdmUsuarios usuario) throws Exception {
        Long idDisqueteAbono = facDisqueteabonosExtendsMapper.getNextIdDisqueteAbono(idInstitucion);
        String nombreFichero = getProperty(FICHERO_TRANSFERENCIAS_PREFIJO) + idDisqueteAbono;

        // Creación del nuevo fichero de transferencias
        FacDisqueteabonos record = new FacDisqueteabonos();
        record.setIdinstitucion(idInstitucion);
        record.setIddisqueteabono(idDisqueteAbono);
        record.setFecha(new Date());
        record.setFechaejecucion(new Date());
        record.setBancosCodigo(bancosCodigo);
        record.setFcs(fcs);
        record.setNombrefichero(nombreFichero);
        record.setNumerolineas(Long.valueOf(abonosBanco.size()));
        record.setIdsufijo(idSufijo);

        record.setFechamodificacion(new Date());
        record.setUsumodificacion(usuario.getIdusuario());

        // Propositos
        record.setIdpropsepa(Short.parseShort(idPropositoSEPA));
        record.setIdpropotros(Short.parseShort(idPropositoOtros));

        if (facDisqueteabonosExtendsMapper.insert(record) == 0) {
            return -1;
        }

        int numeroAbonosIncluidosEnDisquete = 0;
        for (FacAbono abono: abonosBanco) {
            FacAbonoKey abonoKey = new FacAbonoKey();
            abonoKey.setIdinstitucion(idInstitucion);
            abonoKey.setIdabono(abono.getIdabono());

            FacAbono abonoToUpdate = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

            if (abonoToUpdate.getImppendienteporabonar() == null)
                throw new BusinessException("general.mensaje.error.bbdd");

            BigDecimal importeAbonado = abonoToUpdate.getImppendienteporabonar();

            if (importeAbonado.doubleValue() == 0.0)
                throw new BusinessException("general.mensaje.error.bbdd");

            numeroAbonosIncluidosEnDisquete++;

            // Introducimos el abono en el fichero de transferencias
            FacAbonoincluidoendisquete recordAbono = new FacAbonoincluidoendisquete();
            recordAbono.setIdinstitucion(idInstitucion);
            recordAbono.setIdabono(abono.getIdabono());
            recordAbono.setIddisqueteabono(idDisqueteAbono);
            recordAbono.setImporteabonado(importeAbonado);
            recordAbono.setContabilizado("N");

            recordAbono.setFechamodificacion(new Date());
            recordAbono.setUsumodificacion(usuario.getIdusuario());
            facAbonoincluidoendisqueteExtendsMapper.insert(recordAbono);

            // Actualización de los importes
            BigDecimal impPendientePorAbonar = abonoToUpdate.getImppendienteporabonar().subtract(importeAbonado);
            BigDecimal impTotalAbonado = abonoToUpdate.getImptotalabonado().add(importeAbonado);
            BigDecimal impTotalAbonadoPorBanco = abonoToUpdate.getImptotalabonadoporbanco().add(importeAbonado);

            abonoToUpdate.setImppendienteporabonar(impPendientePorAbonar.setScale(2, RoundingMode.DOWN));
            abonoToUpdate.setImptotalabonado(impTotalAbonado.setScale(2, RoundingMode.DOWN));
            abonoToUpdate.setImptotalabonadoporbanco(impTotalAbonadoPorBanco.setScale(2, RoundingMode.DOWN));

            //  Actualización del estado
            if (impPendientePorAbonar.doubleValue() <= 0) {
                abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
            } else if (abonoToUpdate.getIdcuenta() != null) {
                abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
            } else {
                abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
            }

            facAbonoExtendsMapper.updateByPrimaryKey(abonoToUpdate);
        }

        if (numeroAbonosIncluidosEnDisquete == 0) {
            throw new BusinessException("general.mensaje.error.bbdd");
        }

        // Obtener la ruta del fichero
        String rutaServidor = getProperty(FICHERO_TRANSFERENCIAS_ORACLE_DIR).toUpperCase() + usuario.getIdinstitucion();

        Object[] param_in = new Object[7]; // Parametros de entrada del PL

        param_in[0] = idInstitucion;
        param_in[1] = idDisqueteAbono;
        param_in[2] = record.getIdpropsepa();
        param_in[3] = record.getIdpropotros();
        param_in[4] = rutaServidor;
        param_in[5] = nombreFichero;
        param_in[6] = Integer.parseInt(usuario.getIdlenguaje());

        String[] resultado = commons.callPLProcedureFacturacionPyS(
                "{call PKG_SIGA_ABONOS.Generarficherotransferencias(?,?,?,?,?,?,?,?,?)}", 2, param_in);

        if (resultado[0] != null && resultado.length > 1 && string2Integer(resultado[0]) != 0) {
            throw new BusinessException(resultado[1]);
        }

        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateResponseDTO actualizarFicheroTranferencias(FacDisqueteabonos updateItem, HttpServletRequest request)
            throws Exception {
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        updateResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("actualizarFicheroDevoluciones() -> Entrada al servicio para actualizar un fichero devoluciones");

        if (usuario != null) {
            // Clave primaria
            FacDisqueteabonosKey key = new FacDisqueteabonosKey();
            key.setIddisqueteabono(updateItem.getIddisqueteabono());
            key.setIdinstitucion(usuario.getIdinstitucion());

            FacDisqueteabonos record = facDisqueteabonosExtendsMapper.selectByPrimaryKey(key);

            if (updateItem.getBancosCodigo() != null)
                record.setBancosCodigo(updateItem.getBancosCodigo());
            if (updateItem.getFcs() != null)
                record.setFcs(updateItem.getFcs());
            if (updateItem.getFecha() != null)
                record.setFecha(updateItem.getFecha());
            if (updateItem.getFechaejecucion() != null)
                record.setFechaejecucion(updateItem.getFechaejecucion());
            if (updateItem.getFechamodificacion() != null)
                record.setFechamodificacion(updateItem.getFechamodificacion());
            if (updateItem.getIdsufijo() != null)
                record.setIdsufijo(updateItem.getIdsufijo());
            if (updateItem.getNombrefichero() != null)
                record.setNombrefichero(updateItem.getNombrefichero());
            if (updateItem.getNumerolineas() != null)
                record.setNumerolineas(updateItem.getNumerolineas());
            if (updateItem.getUsumodificacion() != null)
                record.setUsumodificacion(updateItem.getUsumodificacion());

            facDisqueteabonosExtendsMapper.updateByPrimaryKey(record);

            updateResponseDTO.setId(record.getIddisqueteabono().toString());
        }

        LOGGER.info("actualizarProgramacionFactura() -> Salida del servicio para actualizar un fichero devoluciones");

        return updateResponseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteResponseDTO eliminarFicheroTransferencias(FicherosAbonosItem ficherosAbonosItem, HttpServletRequest request)
            throws Exception {
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        deleteResponseDTO.setError(error);

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("eliminarFicheroTransferencias() -> Entrada al servicio para eliminar un fichero de transferencias");

        FacDisqueteabonosKey keyDisquete = new FacDisqueteabonosKey();
        keyDisquete.setIdinstitucion(usuario.getIdinstitucion());
        keyDisquete.setIddisqueteabono(string2Long(ficherosAbonosItem.getIdDisqueteAbono()));

        FacDisqueteabonos disquete = facDisqueteabonosExtendsMapper.selectByPrimaryKey(keyDisquete);

        if (disquete != null) {
            FacAbonoincluidoendisqueteExample abonosInclDisquete = new FacAbonoincluidoendisqueteExample();
            abonosInclDisquete.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
                    .andIddisqueteabonoEqualTo(string2Long(ficherosAbonosItem.getIdDisqueteAbono()));

            List<FacAbonoincluidoendisquete> abonos = facAbonoincluidoendisqueteExtendsMapper.selectByExample(abonosInclDisquete);

            if (abonos != null && !abonos.isEmpty()) {
                for (FacAbonoincluidoendisquete abonoIncluido: abonos) {
                    FacAbonoKey keyAbono = new FacAbonoKey();
                    keyAbono.setIdinstitucion(abonoIncluido.getIdinstitucion());
                    keyAbono.setIdabono(abonoIncluido.getIdabono());

                    FacAbono abonoToUpdate = facAbonoExtendsMapper.selectByPrimaryKey(keyAbono);

                    if (abonoToUpdate != null) {
                        BigDecimal importeAbonado = abonoIncluido.getImporteabonado();

                        // Compruebo que el importe por abonar y el importe abonado están inicializados
                        if (abonoToUpdate.getImppendienteporabonar() == null)
                            abonoToUpdate.setImppendienteporabonar(BigDecimal.ZERO);
                        if (abonoToUpdate.getImptotalabonado() == null)
                            abonoToUpdate.setImptotalabonado(importeAbonado);
                        if (abonoToUpdate.getImptotalabonadoporbanco() == null)
                            abonoToUpdate.setImptotalabonadoporbanco(importeAbonado);

                        // Actualización de los importes
                        BigDecimal impPendientePorAbonar = abonoToUpdate.getImppendienteporabonar().add(importeAbonado);
                        BigDecimal impTotalAbonado = abonoToUpdate.getImptotalabonado().subtract(importeAbonado);
                        BigDecimal impTotalAbonadoPorBanco = abonoToUpdate.getImptotalabonadoporbanco().subtract(importeAbonado);

                        abonoToUpdate.setImppendienteporabonar(impPendientePorAbonar.setScale(2, RoundingMode.DOWN));
                        abonoToUpdate.setImptotalabonado(impTotalAbonado.setScale(2, RoundingMode.DOWN));
                        abonoToUpdate.setImptotalabonadoporbanco(impTotalAbonadoPorBanco.setScale(2, RoundingMode.DOWN));

                        //  Actualización del estado
                        if (impPendientePorAbonar.doubleValue() <= 0) {
                            abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
                        } else if (abonoToUpdate.getIdcuenta() != null) {
                            abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
                        } else {
                            abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
                        }

                        facAbonoExtendsMapper.updateByPrimaryKey(abonoToUpdate);
                    }

                    facAbonoincluidoendisqueteExtendsMapper.deleteByPrimaryKey(abonoIncluido);
                }
            }

            facDisqueteabonosExtendsMapper.deleteByPrimaryKey(keyDisquete);

            // A continuación, se eliminan los ficheros de adeudos
            String pathFichero = getProperty(FICHERO_TRANSFERENCIAS_SERVER_PATH) + getProperty(FICHERO_TRANSFERENCIAS_SERVER_DIR)
                    + File.separator + usuario.getIdinstitucion();

            String nombreFichero = disquete.getNombrefichero();
            File directorioFicheros = new File(pathFichero);

            // Se buscan todos los ficheros que coincidan con el nombre del fichero
            if (directorioFicheros.exists() && Objects.nonNull(disquete.getNombrefichero())) {
                File[] ficheros = directorioFicheros.listFiles();
                String nombreFicheroListadoSinExtension, nombreFicheroGeneradoSinExtension;
                for (File file: ficheros) {
                    nombreFicheroListadoSinExtension = (file.getName().indexOf(".") > 0)
                            ? file.getName().substring(0, file.getName().indexOf(".")) : file.getName();
                    nombreFicheroGeneradoSinExtension = (nombreFichero.indexOf(".") > 0)
                            ? nombreFichero.substring(0, nombreFichero.indexOf(".")) : nombreFichero;
                    if(nombreFicheroGeneradoSinExtension.equalsIgnoreCase(nombreFicheroListadoSinExtension) && file.exists()){
                        file.delete();
                    }
                }
            }


        }


        LOGGER.info("eliminarFicheroTransferencias() -> Salida del servicio para eliminar un fichero de transferencias");

        return deleteResponseDTO;
    }

    @Override
    public ResponseEntity<InputStreamResource> descargarFicheroTransferencias(List<FicherosAbonosItem> ficheroAbonosItems, HttpServletRequest request) throws Exception {
        ResponseEntity<InputStreamResource> res = null;

        // Conseguimos información del usuario logeado
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        LOGGER.info("descargarFicheroTransferencias() -> Entrada al servicio para descargar ficheros de transferencias");

        String pathFichero = getProperty(FICHERO_TRANSFERENCIAS_SERVER_PATH) + getProperty(FICHERO_TRANSFERENCIAS_SERVER_DIR)
                + File.separator + usuario.getIdinstitucion();

        List<File> listaFicheros = ficheroAbonosItems.stream().flatMap(item -> {
            FacDisqueteabonosKey key = new FacDisqueteabonosKey();
            key.setIdinstitucion(usuario.getIdinstitucion());
            key.setIddisqueteabono(string2Long(item.getIdDisqueteAbono()));
            FacDisqueteabonos disqueteabonos = facDisqueteabonosExtendsMapper.selectByPrimaryKey(key);

            List<File> files = new ArrayList<>();
            if (Objects.nonNull(disqueteabonos)) {
                String nombreFichero = disqueteabonos.getNombrefichero();
                File directorioFicheros = new File(pathFichero);

                // Se buscan todos los ficheros que coincidan con el nombre del fichero
                if (directorioFicheros.exists() && Objects.nonNull(disqueteabonos.getNombrefichero())) {
                    File[] ficheros = directorioFicheros.listFiles();
                    String nombreFicheroListadoSinExtension, nombreFicheroGeneradoSinExtension;
                    for (File file: ficheros){
                        nombreFicheroListadoSinExtension = (file.getName().indexOf(".") > 0)
                                ? file.getName().substring(0, file.getName().indexOf(".")) : file.getName();
                        nombreFicheroGeneradoSinExtension = (nombreFichero.indexOf(".") > 0)
                                ? nombreFichero.substring(0, nombreFichero.indexOf(".")) : nombreFichero;
                        if(nombreFicheroGeneradoSinExtension.equalsIgnoreCase(nombreFicheroListadoSinExtension)){
                            files.add(file);
                        }
                    }
                }
            }

            return files.stream();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        // Construcción de la respuesta para uno o más archivos
        res = SIGAServicesHelper.descargarFicheros(listaFicheros,
                MediaType.parseMediaType("application/octet-stream"),
                MediaType.parseMediaType("application/zip"), "LOG_FICHERO_TRANSFERENCIAS");

        LOGGER.info("descargarFicheroTransferencias() -> Salida del servicio para descargar ficheros de transferencias");

        return res;
    }

    private String getProperty(String parametro) {
        GenPropertiesKey keyProperties = new GenPropertiesKey();
        keyProperties.setFichero("SIGA");
        keyProperties.setParametro(parametro);
        GenProperties property = genPropertiesMapper.selectByPrimaryKey(keyProperties);
        return property != null ? property.getValor() : "";
    }

    private String getParametro(String modulo, String parametro, Short idInstitucion) {
        GenParametrosKey keyParametros = new GenParametrosKey();
        keyParametros.setModulo(modulo);
        keyParametros.setParametro(parametro);
        keyParametros.setIdinstitucion(idInstitucion);
        GenParametros property = genParametrosExtendsMapper.selectByPrimaryKey(keyParametros);
        return property != null ? property.getValor() : "";
    }

    private Short string2Short(String val) {
        return val != null ? Short.valueOf(val) : null;
    }

    private Long string2Long(String val) {
        return val != null ? Long.valueOf(val) : null;
    }

    private Integer string2Integer(String val) {
        return val != null ? Integer.valueOf(val) : null;
    }

    private String boolToString10(Boolean b) {
        return b ? "1" : "0";
    }

}
