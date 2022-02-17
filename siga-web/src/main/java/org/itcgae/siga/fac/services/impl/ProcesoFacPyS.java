package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.AdmConsultaInformeConsultaDTO;
import org.itcgae.siga.DTO.fac.DireccionesDTO;
import org.itcgae.siga.DTO.fac.DocumentoDTO;
import org.itcgae.siga.DTO.fac.EntradaDireccionEspecificaDTO;
import org.itcgae.siga.DTO.fac.FacEstadoConfirmacionFact;
import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.DTO.fac.FacFicherosDescargaBean;
import org.itcgae.siga.DTO.fac.FacturaFacturacionProgramadaDTO;
import org.itcgae.siga.DTO.fac.ScsPersonaJGBean;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SIGALogging;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmInforme;
import org.itcgae.siga.db.entities.AdmInformeExample;
import org.itcgae.siga.db.entities.AdmTipofiltroinforme;
import org.itcgae.siga.db.entities.AdmTipofiltroinformeExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenProvincias;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.EnvCamposenvios;
import org.itcgae.siga.db.entities.EnvCamposenviosExample;
import org.itcgae.siga.db.entities.EnvCamposplantilla;
import org.itcgae.siga.db.entities.EnvCamposplantillaExample;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDestinatariosKey;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.EnvPlantillaremitentes;
import org.itcgae.siga.db.entities.EnvPlantillaremitentesExample;
import org.itcgae.siga.db.entities.EnvRemitentes;
import org.itcgae.siga.db.entities.EnvRemitentesExample;
import org.itcgae.siga.db.entities.EnvRemitentesKey;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.FacFacturacionprogramadaKey;
import org.itcgae.siga.db.entities.FacPlantillafacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.ScsJuzgado;
import org.itcgae.siga.db.entities.ScsJuzgadoKey;
import org.itcgae.siga.db.entities.ScsProcurador;
import org.itcgae.siga.db.entities.ScsProcuradorKey;
import org.itcgae.siga.db.entities.ScsTelefonospersona;
import org.itcgae.siga.db.entities.ScsTelefonospersonaExample;
import org.itcgae.siga.db.mappers.AdmInformeMapper;
import org.itcgae.siga.db.mappers.AdmTipofiltroinformeMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EnvCamposenviosMapper;
import org.itcgae.siga.db.mappers.EnvCamposplantillaMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvPlantillaremitentesMapper;
import org.itcgae.siga.db.mappers.EnvRemitentesMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmConsultainformeExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDocumentosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacPlantillafacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcuradorExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTelefonosPersonaExtendsMapper;
import org.itcgae.siga.services.IEnviosCommonsService;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class ProcesoFacPyS {

    private Logger LOGGER = Logger.getLogger(ProcesoFacPyS.class);

    protected static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF = "messages.facturacion.confirmacion.errorPdf";
    protected static final String TRASPASO_FACTURAS_WS_ACTIVO = "TRASPASO_FACTURAS_WS_ACTIVO";
    protected static final String PROC_CARGOS_PRESENTACION = "{call PKG_SIGA_CARGOS.PRESENTACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    protected static final String PROC_GENERACION_FACTURACION = "{call PKG_SIGA_FACTURACION.GENERACIONFACTURACION(?,?,?,?,?,?,?,?)}";
    protected static final String PROC_CONFIRMACION_FACTURACION = "{call PKG_SIGA_FACTURACION.CONFIRMACIONFACTURACION(?,?,?,?,?,?)}";
    protected static final String COD_OK = "0";
    protected static final String COD_FACTURACION_CONFIRMACION_ERROR_PDF = "-208";
    protected static final String COD_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO = "-205";
    protected static final String MSG_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO = "messages.facturacion.confirmar.contadorRepetido";
    protected static final String MSG_FACTURACION_CONFIRMACION_ERROR_PDF = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
    protected static final String MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR = "facturacion.confirmarFacturacion.mensaje.generacionDisquetesERROR";
    protected static final String FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION = "facturacion.directorioFisicoLogProgramacion";
    protected static final String LOG_XLS = ".log.xls";
    protected static final String LOG_FAC_CONFIRMACION_PREFIX = "LOG_FAC_CONFIRMACION_";
    protected static final String LOG_FAC_TRASPASO_PREFIX = "LOG_FAC_TRASPASO_";
    protected static final String TXT_ERR_NO_SE_HA_PODIDO_FACTURAR_NADA = "No se ha podido facturar nada. Compruebe la configuracion y el periodo indicado";
    protected static final String TIPO_ADM_INFORME_PREV = "PREV";
    protected static final Short DEFAULT_INSTITUCION = 0;
    protected static final String PROP_FACTURACION_DIRECTORIO_FISICO_PREVISIONES_JAVA = "facturacion.directorioFisicoPrevisionesJava";
    protected static final String PROP_FACTURACION_DIRECTORIO_PREVISIONES_JAVA = "facturacion.directorioPrevisionesJava";
    protected static final String LOG_FAC_SUFFIX = LOG_XLS;
    protected static final String LOG_FAC_PREFIX = "LOG_FAC";
    protected static final String PROP_SIGA_JTA_TIMEOUT_PESADA = "siga.jta.timeout.pesada";
    protected static final Integer DEFAULT_SIGA_JTA_TIMEOUT_PESADA = 2400;
    protected static final String PROP_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION = "facturacion.programacionAutomatica.maxMinutosEnEjecucion";
    protected static final Double DEFAULT_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION = 120.0 / (24.0 * 60.0);
    protected static final Integer USUARIO_AUTO = 0;
    protected static final String DEFAULT_LENGUAJE = "1";
    protected static final String[] CODIGOS_ERROR_FORMATO = {"-201", "-202", "-203", "-204"};
    protected static final String FACTURACION_DIRECTORIO_FISICO_TEMPORAL_FACTURAS_JAVA = "facturacion.directorioFisicoTemporalFacturasJava";
    protected static final String FACTURACION__DIRECTORIO_TEMPORAL_FACTURAS_JAVA = "facturacion.directorioTemporalFacturasJava";
    protected static final String FACTURACION_DIRECTORIO_FISICO_PLANTILLA_FACTURA_JAVA = "facturacion.directorioFisicoPlantillaFacturaJava";
    protected static final String FACTURACION_DIRECTORIO_PLANTILLA_FACTURA_JAVA = "facturacion.directorioPlantillaFacturaJava";
    protected static final String INFORMES_DIRECTORIO_FISICO_SALIDA_INFORMES_JAVA = "informes.directorioFisicoSalidaInformesJava";
    protected static final String FACTURACION_DIRECTORIO_BANCOS_ORACLE = "facturacion.directorioBancosOracle";
    protected static final String FACTURACION_DIRECTORIO_FISICO_FACTURAS_PDF_JAVA = "facturacion.directorioFisicoFacturaPDFJava";
    protected static final String FACTURACION_DIRECTORIO_FACTURA_PDF_JAVA = "facturacion.directorioFacturaPDFJava";
    protected static final Short TRASPASO_PROGRAMADA = Short.valueOf("23");
    protected static final Short CONFIRM_FINALIZADA = Short.valueOf("3");

    protected Map<Short, CenInstitucion> mInstituciones;

    @Autowired
    protected FacFacturacionprogramadaExtendsMapper facProgMapper;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    @Autowired
    protected GenPropertiesMapper genPropertiesMapper;

    @Autowired
    protected GenParametrosMapper genParametrosMapper;

    @Autowired
    protected CenInstitucionMapper instMapper;

    @Autowired
    protected WSCommons wsCommons;

    @Autowired
    protected AdmInformeMapper adInformeMapper;

    @Autowired
    protected AdmTipofiltroinformeMapper admTipofiltroinformeMapper;

    @Autowired
    protected AdmConsultainformeExtendsMapper admConsultainformeExtendsMapper;

    @Autowired
    protected GenRecursosMapper genRecursosMapper;

    @Autowired
    protected EcomColaMapper ecomColaMapper;

    @Autowired
    protected EcomColaParametrosMapper ecomColaParametrosMapper;

    @Autowired
    protected FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    protected FacPlantillafacturacionExtendsMapper facPlantillafacturacionExtendsMapper;

    @Autowired
    protected CenPersonaExtendsMapper cenPersonaExtendsMapper;

    @Autowired
    protected FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

    @Autowired
    protected FacturacionHelper facturacionHelper;

    @Autowired
    protected EnvEnviosExtendsMapper envEnviosExtendsMapper;

    @Autowired
    protected CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

    @Autowired
    protected EnvCamposenviosMapper envCamposenviosMapper;

    @Autowired
    protected EnvCamposplantillaMapper envCamposplantillaMapper;

    @Autowired
    protected EnvPlantillaremitentesMapper envPlantillaremitentesMapper;

    @Autowired
    protected EnvRemitentesMapper envRemitentesMapper;

    @Autowired
    protected EnvDestinatariosMapper envDestinatariosMapper;

    @Autowired
    protected ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

    @Autowired
    protected ScsTelefonosPersonaExtendsMapper scsTelefonosPersonaExtendsMapper;

    @Autowired
    protected ScsProcuradorExtendsMapper scsProcuradorExtendsMapper;

    @Autowired
    protected ScsJuzgadoExtendsMapper scsJuzgadoExtendsMapper;

    @Autowired
    protected EnvDocumentosExtendsMapper envDocumentosExtendsMapper;

    @Autowired
    protected IEnviosCommonsService enviosCommonsService;

    public void ejecutar() {

        List<CenInstitucion> listaInstituciones = obtenerInstitucionesAlta();

        for (CenInstitucion cenInstitucion : listaInstituciones) {

            execute(cenInstitucion.getIdinstitucion().toString());

        }

    }

    protected abstract void execute(String idInstitucion);

    private List<CenInstitucion> obtenerInstitucionesAlta() {

        CenInstitucionExample cenInstitucionExample = new CenInstitucionExample();
        cenInstitucionExample.setDistinct(true);
        cenInstitucionExample.createCriteria().andFechaenproduccionIsNotNull();

        return instMapper.selectByExample(cenInstitucionExample);
    }

    protected Double getMaxMinutosEnEjecucion() {
        Double minutos = getProperty(PROP_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION, Double::valueOf, DEFAULT_FACTURACION_PROGRAMACION_AUTOMATICA_MAX_MINUTOS_EN_EJECUCION);
        minutos = minutos / (24.0 * 60.0);
        return minutos;
    }

    protected int getTimeoutLargo() {
        Integer time = getProperty(PROP_SIGA_JTA_TIMEOUT_PESADA, Integer::valueOf, DEFAULT_SIGA_JTA_TIMEOUT_PESADA);
        return time;
    }

    protected void rollBack(TransactionStatus transactionStatus) {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
        }
    }

    protected void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.commit(transactionStatus);
        }
    }

    protected void finalizaTransaccion(TransactionStatus transactionStatus) {
        if (!transactionStatus.isCompleted()) {
            if (transactionStatus.isRollbackOnly()) {
                rollBack(transactionStatus);
            } else {
                commit(transactionStatus);
            }
        }
    }

    protected String getProperty(final String key) {
        return getProperty(key, null, null);
    }

    protected String getProperty(final String key, final String defaultValue) {
        return getProperty(key, null, defaultValue);
    }

    protected <T> T getProperty(final String key, Function<String, T> convert, T defValue) {
        T value = null;
        String sValue = null;
        GenPropertiesExample pEx = new GenPropertiesExample();
        pEx.createCriteria().andParametroEqualTo(key);

        List<GenProperties> lParam = genPropertiesMapper.selectByExample(pEx);
        if (lParam.size() > 0) {
            sValue = lParam.get(0).getValor();
        }

        if (convert != null) {
            value = convert.apply(sValue);
        } else {
            value = (T) sValue;
        }

        return value;
    }

    protected GenParametros getParametro(Short idInstitucion, String modulo, String parametro) {
        return getParametro(idInstitucion, modulo, parametro, null);
    }

    protected GenParametros getParametro(Short idInstitucion, String modulo, String parametro, String valorDefecto) {

        GenParametrosKey genParametrosKey = new GenParametrosKey();
        genParametrosKey.setIdinstitucion(idInstitucion);
        genParametrosKey.setModulo(modulo);
        genParametrosKey.setParametro(parametro);

        GenParametros genParametro = genParametrosMapper.selectByPrimaryKey(genParametrosKey);

        if (genParametro == null && !UtilidadesString.esCadenaVacia(valorDefecto)) {
            genParametro = new GenParametros();
            genParametro.setValor(valorDefecto);
        }

        return genParametro;
    }

    protected String getRecurso(String idRecurso) {
        return getRecurso(idRecurso, null);
    }

    protected String getRecurso(String idRecurso, String idLenguaje) {

        String respuesta = null;

        GenRecursosKey genRecursosKey = new GenRecursosKey();
        genRecursosKey.setIdrecurso(idRecurso);
        genRecursosKey.setIdlenguaje(!UtilidadesString.esCadenaVacia(idLenguaje) ? idLenguaje : DEFAULT_LENGUAJE);

        GenRecursos genRecursos = genRecursosMapper.selectByPrimaryKey(genRecursosKey);

        if (genRecursos != null && !UtilidadesString.esCadenaVacia(genRecursos.getDescripcion())) {
            respuesta = genRecursos.getDescripcion();
        }

        return respuesta;
    }

    protected void marcaEjecutandoGeneracion(FacFacturacionprogramada fac) {
        fac.setIdestadoconfirmacion(FacEstadosFacturacion.EJECUTANDO_GENERACION.getId());
        fac.setUsumodificacion(USUARIO_AUTO);
        fac.setFechamodificacion(new Date());
        facProgMapper.updateByPrimaryKeySelective(fac);
    }

    protected String getLenguajeInstitucion(Short idinstitucion) {
        String idLenguaje = DEFAULT_LENGUAJE;
        if (mInstituciones == null) {
            initInstituciones();
        }
        if (mInstituciones.containsKey(idinstitucion)) {
            idLenguaje = mInstituciones.get(idinstitucion).getIdlenguaje();
        }
        return idLenguaje;
    }

    private void initInstituciones() {
        CenInstitucionExample example = new CenInstitucionExample();
        example.createCriteria().andFechaenproduccionIsNotNull();
        List<CenInstitucion> lInst = instMapper.selectByExample(example);
        mInstituciones = lInst.stream().collect(Collectors.toMap(i -> i.getIdinstitucion(), i -> i));
    }

    protected ArrayList<File> generarInformeXLS(AdmInforme informe, List<HashMap<String, String>> filtrosInforme, String sRutaJava, String idInstitucion) throws Exception {

        // Variables
        String sentencia;
        List<LinkedHashMap<String, String>> datos;

        // obteniendo ruta de almacenamiento
        String idinstitucionInforme = informe.getIdinstitucion().toString();
        String idinstitucion = idInstitucion;

        String rutaAlm = "";

        if ((sRutaJava == null) || (sRutaJava.isEmpty())) {
            rutaAlm = getProperty(INFORMES_DIRECTORIO_FISICO_SALIDA_INFORMES_JAVA) + File.separator + informe.getDirectorio() + File.separator + (idinstitucionInforme.equals("0") ? "2000" : idinstitucionInforme) + File.separator;
        } else {
            rutaAlm = sRutaJava;
        }

        // obteniendo los tipos de filtros obligatorios
        AdmTipofiltroinformeExample admTipofiltroinformeExample = new AdmTipofiltroinformeExample();
        admTipofiltroinformeExample.createCriteria().andIdtipoinformeEqualTo(informe.getIdtipoinforme())
                .andObligatorioEqualTo(SigaConstants.DB_TRUE);
        List<AdmTipofiltroinforme> tiposFiltro = admTipofiltroinformeMapper.selectByExample(admTipofiltroinformeExample);

        // comprobando que los filtros obligatorios estan en la lista de filtros del informe
        for (HashMap<String, String> filtro : filtrosInforme) {
            filtro.put("OBLIGATORIO", SigaConstants.DB_FALSE);
        }

        String nombreCampo;
        boolean encontrado;

        for (AdmTipofiltroinforme tipoFiltro : tiposFiltro) {

            nombreCampo = tipoFiltro.getNombrecampo();
            encontrado = false;

            for (HashMap<String, String> filtro : filtrosInforme) {
                if (filtro.get("NOMBRECAMPO").equals(nombreCampo)) {
                    filtro.put("OBLIGATORIO", SigaConstants.DB_TRUE);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                throw new Exception("Problema en la configuracion del informe: No estan configurados todos los tipos de filtros obligatorios");
            }
        }

        // obteniendo las consultas del informe
        List<AdmConsultaInformeConsultaDTO> consultas = admConsultainformeExtendsMapper.getConsultasInforme(informe.getIdinstitucion().toString(), informe.getIdplantilla().toString());

        // creando la ruta de salida
        SIGAHelper.mkdirs(rutaAlm);

        ArrayList<File> listaFicheros = new ArrayList<>();

        // variables para los ficheros de salida
        /**
         * @TODO Habria que obtener un identificador unico: de momento se genera
         *       el numero de usuario, que vale si el mismo usuario no ejecuta
         *       informes a la vez (por ejemplo en dos navegadores).
         *       Ademas, estaria bien dar un numero unico pero que sea algo
         *       descriptivo?
         */
        String nombreFichero;
        File ficheroGenerado = null;
        BufferedWriter out;

        for (AdmConsultaInformeConsultaDTO consulta : consultas) {

            // sustituyendo los filtros en la consulta por los datos del informe
            if ((sentencia = sustituirFiltrosConsulta(consulta, filtrosInforme)) == null) {  //CR7 - CAMBIAR LA CONSULTA 1027 - Datos previsiones facturacion
                // falla si la consulta no tiene los filtros obligatorios
                throw new Exception("Problema en la configuracion del informe: La consulta no lleva los filtros obligatorios o lleva de mas");
            }

            try {

                /** @TODO Convendria que cambiar a selectBind */
                datos = admConsultainformeExtendsMapper.selectCamposOrdenados(sentencia);

            } catch (Exception sqle) {
                String mensaje = sqle.getMessage();
                if (mensaje.indexOf("TimedOutException") != -1
                        || mensaje.indexOf("timed out") != -1) {
                    throw new Exception("La consulta ha superado el tiempo permitido de ejecución y se ha anulado la acción para no cargar al sistema. Pruebe a ejecutarlo en otro momento de menos uso o póngase en contacto con el administrador del sistema.");
                } else if (sqle.toString().indexOf("ORA-") != -1) {
                    throw new Exception("Ha habido un problema durante la ejecucion de la consulta: " + consulta.getDescripcion() + ". Consulte al Administrador", sqle);
                } else {
                    throw new Exception("Problema indeterminado en la ejecucion de algunas consulta del informe. Consulte al administrador");
                }
            }

            // Si no hay datos no se genera informe en esta iteracion
            if (datos == null || datos.size() == 0) {
                continue;
            }

            // generando el fichero
            try {

                if (informe.getIdtipoinforme().equalsIgnoreCase("PREV")) {
                    //Si estamos generando un informe de previsiones
                    nombreFichero = informe.getNombresalida() + ".xls";
                } else {
                    nombreFichero = informe.getNombresalida() + "_" + idinstitucion + "_" + USUARIO_AUTO + "_"
                            + getFechaCompletaBD().replaceAll("/", "").replaceAll(":", "").replaceAll(" ", "") + '_'
                            + consulta.getNombre() + ".xls";
                }

                ficheroGenerado = new File(rutaAlm + File.separator + nombreFichero);

                if (ficheroGenerado.exists()) {
                    ficheroGenerado.delete();
                }

                ficheroGenerado.createNewFile();
                out = new BufferedWriter(new FileWriter(ficheroGenerado));

                // escribiendo las cabeceras
                LinkedHashMap<String, String> hashOrdenado = datos.get(0);

                for (Map.Entry<String, String> entry : hashOrdenado.entrySet()) {
                    String key = entry.getKey();
                    out.write(key + "\t");
                }

                out.newLine();

                // escribiendo los resultados
                for (Map.Entry<String, String> entry : hashOrdenado.entrySet()) {
                    String value = entry.getValue();
                    out.write(value + "\t");
                }

                out.newLine();

                // cerrando el fichero
                out.close();
                listaFicheros.add(ficheroGenerado);

            } catch (Exception sqle) {
                throw new Exception("Problema en la generacion del fichero Excel", sqle);
            }

        }

        return listaFicheros;
    }

    protected String sustituirFiltrosConsulta(AdmConsultaInformeConsultaDTO consulta, List<HashMap<String, String>> listaFiltros) {

        String sentencia = consulta.getSentencia();

        // quitando saltos de linea (para que salga mejor en LOG)
        sentencia = sentencia.replaceAll("\n", " ");

        // reemplazando filtros
        String nombreCampo, obligatorio, valor;

        for (HashMap<String, String> filtro : listaFiltros) {
            nombreCampo = "%%" + filtro.get("NOMBRECAMPO").toUpperCase() + "%%";
            obligatorio = filtro.get("OBLIGATORIO");
            valor = filtro.get("VALOR");

            if (sentencia.indexOf(nombreCampo) > -1) {
                sentencia = sentencia.replaceAll(nombreCampo, valor);
            } else if (obligatorio == SigaConstants.DB_TRUE && consulta.getGeneral() == SigaConstants.DB_FALSE) {
                return null; // no estan todos los filtros obligatorios
            }
        }

        // comprobando los campos que faltan por sustituir en la sentencia
        if (sentencia.indexOf("%%") > -1) {
            return null;
        }

        return sentencia;
    }

    protected String getFechaCompletaBD() {
        return admConsultainformeExtendsMapper.getFechaCompletaBD();
    }

    protected void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros) throws Exception {
        insertaColaConParametros(ecomCola, parametros, getUsuarioAuto());
    }

    protected void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros, AdmUsuarios usuario) throws Exception {

        insertartCola(ecomCola, usuario);

        if (parametros != null) {
            Iterator<String> it = parametros.keySet().iterator();

            while (it.hasNext()) {
                EcomColaParametros ecomColaParametros = new EcomColaParametros();
                String clave = it.next();
                String valor = parametros.get(clave);
                ecomColaParametros.setIdecomcola(ecomCola.getIdecomcola());
                ecomColaParametros.setClave(clave);
                ecomColaParametros.setValor(valor);
                if (ecomColaParametrosMapper.insert(ecomColaParametros) != 1) {
                    throw new Exception("Error al insertar los parámetros de la cola.");
                }
            }
        }
    }

    protected int insertartCola(EcomCola ecomCola, AdmUsuarios usuario) {

        try {
            ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.INICIAL.getId());
            ecomCola.setReintento(0);
            ecomCola.setFechacreacion(new Date());
            ecomCola.setFechamodificacion(new Date());
            ecomCola.setUsumodificacion(usuario.getIdusuario());

            return ecomColaMapper.insert(ecomCola);
        } catch (Exception e) {
            LOGGER.error(String.format("Se ha producido un error al insertar en la cola %s", ecomCola));
            throw e;
        }
    }

    protected AdmUsuarios getUsuarioAuto() {
        AdmUsuarios usuario = new AdmUsuarios();
        usuario.setIdusuario(USUARIO_AUTO);
        return usuario;
    }

    protected String generarPdfEnvioProgramacionFactura(FacFacturacionprogramada beanP, SIGALogging log, Long idSerieFacturacion, Long idProgramacion,
                                                        FacFacturacionprogramada hashFactura, boolean isGenerarEnvio, TransactionStatus tx) throws Exception {

        String msjAviso = null;
        String nombreFichero = "LOG_FAC_CONFIRMACION_" + beanP.getIdseriefacturacion() + "_" + beanP.getIdprogramacion() + ".log.xls";

        try {

            // Se guardan las facturas impresas.
            LOGGER.info("TIENE QUE GENERAR PDF");

            //////////// INICIO TRANSACCION ////////////////
            beanP.setIdestadopdf(FacEstadosFacturacion.PDF_PROCESANDO.getId()); // cambio de estado PDF a PROCESANDO
            facProgMapper.updateByPrimaryKeySelective(beanP);

            commit(tx);
            //////////// FIN TRANSACCION ////////////////

            //////////// ALMACENAR RAPIDA ////////////////
            //En facturaciones rapidas, en compra de PYS no hay que generar el excel con el log
            int errorAlmacenar = generaryEnviarProgramacionFactura(Integer.valueOf(beanP.getIdinstitucion()), idSerieFacturacion, idProgramacion, isGenerarEnvio, log, tx);

            ////////////INICIO TRANSACCION ////////////////
            switch (errorAlmacenar) {
                case 0: //NO HAY ERROR. SE HA GENERADO CORRECTAMENTE Y SE PROCESADO EL ENVIO
                    if (isGenerarEnvio) {
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADA.getId()); // cambio de estado PDF a FINALIZADA
                        hashFactura.setIdestadoenvio(FacEstadoConfirmacionFact.ENVIO_FINALIZADA.getId()); // cambio de estado ENVIO a FINALIZADO
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);

                    } else {
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADA.getId()); // cambio de estado PDF a FINALIZADA
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    }

                    LOGGER.info("OK TODO. CAMBIO DE ESTADOS");
                    break;

                case 1: // ERROR EN GENERAR PDF
                    LOGGER.info("ERROR AL ALMACENAR FACTURA. RETORNO=" + errorAlmacenar);
                    msjAviso = getRecurso("messages.facturacion.confirmacion.errorPdf");
                    if (isGenerarEnvio) {
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADAERRORES.getId()); // cambio de estado PDF a FINALIZADA CON ERRRORES
                        hashFactura.setIdestadoenvio(FacEstadoConfirmacionFact.ENVIO_FINALIZADAERRORES.getId()); // cambio de estado ENVIO a FINALIZADO CON ERRRORES
                        hashFactura.setLogerror(nombreFichero);
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    } else {
                        hashFactura.setLogerror(nombreFichero);
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADAERRORES.getId()); // cambio de estado PDF a FINALIZADA CON ERRRORES
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    }

                    break;

                case 2: // ERROR EN ENVIO FACTURA
                    LOGGER.info("ERROR AL ALMACENAR FACTURA. RETORNO=" + errorAlmacenar);
                    msjAviso = getRecurso("messages.facturacion.confirmacion.errorEnvio");
                    if (isGenerarEnvio) {
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADA.getId()); // cambio de estado PDF a FINALIZADA
                        hashFactura.setIdestadoenvio(FacEstadoConfirmacionFact.ENVIO_FINALIZADAERRORES.getId()); // cambio de estado ENVIO a FINALIZADO CON ERRRORES
                        hashFactura.setLogerror(nombreFichero);
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    } else {
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADA.getId()); // cambio de estado PDF a FINALIZADA
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    }

                    LOGGER.info("ERROR ENVIAR FACTURA. CAMBIO DE ESTADOS");
                    break;

                default:
                    msjAviso = getRecurso("messages.facturacion.confirmacion.errorPdf");
                    if (isGenerarEnvio) {
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADAERRORES.getId()); // cambio de estado PDF a FINALIZADA CON ERRRORES
                        hashFactura.setIdestadoenvio(FacEstadoConfirmacionFact.ENVIO_FINALIZADAERRORES.getId()); // cambio de estado ENVIO a FINALIZADO CON ERRRORES
                        hashFactura.setLogerror(nombreFichero);
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    } else {
                        hashFactura.setLogerror(nombreFichero);
                        hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADAERRORES.getId()); // cambio de estado PDF a FINALIZADA CON ERRRORES
                        facProgMapper.updateByPrimaryKeySelective(hashFactura);
                    }

                    LOGGER.info("ERROR GENERAL GENERAR/ENVIAR FACTURA. CAMBIO DE ESTADOS");
                    break;
            }

            commit(tx);
            //////////// FIN TRANSACCION ////////////////

        } catch (Exception e) {

            LOGGER.error("ERROR GENERAL EN TRY GENERAR/ENVIAR.");
            rollBack(tx);

            // ESCRIBO EN EL LOG mensaje general ??
            log.writeLogFactura("PDF/ENVIO", "N/A", "N/A", "Error general en el proceso de generacion o envio de facturas PDF: " + e.toString());

            //////////// INICIO TRANSACCION ////////////////
            if (isGenerarEnvio) {
                hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADAERRORES.getId()); // cambio de estado PDF a FINALIZADA CON ERRRORES
                hashFactura.setIdestadoenvio(FacEstadoConfirmacionFact.ENVIO_FINALIZADAERRORES.getId()); // cambio de estado ENVIO a FINALIZADO CON ERRRORES
                hashFactura.setLogerror(nombreFichero);
                facProgMapper.updateByPrimaryKeySelective(hashFactura);

            } else {
                hashFactura.setIdestadopdf(FacEstadoConfirmacionFact.PDF_FINALIZADAERRORES.getId()); // cambio de estado PDF a FINALIZADA CON ERRRORES
                hashFactura.setLogerror(nombreFichero);
                facProgMapper.updateByPrimaryKeySelective(hashFactura);
            }


            commit(tx);
            //////////// FIN TRANSACCION ////////////////

            throw e;
        }

        return msjAviso;
    }

    protected int generaryEnviarProgramacionFactura(Integer institucion, Long serieFacturacion, Long idProgramacion, boolean bGenerarEnvios,
                                                    SIGALogging log, TransactionStatus tx) throws Exception {

        int salida = 0;
        boolean existeAlgunErrorPdf = false;
        boolean existeAlgunErrorEnvio = false;

        try {
            // Obtengo las facturas a almacenar
            List<FacturaFacturacionProgramadaDTO> facturaFacturacionProgramadaDTOList = facFacturaExtendsMapper.getFacturasDeFacturacionProgramada(institucion.toString(), serieFacturacion.toString(), idProgramacion.toString());

            /** CR - Si no se ha generado ninguna factura, se lanza una excepcion ya que no se puede generar PDF **/
            if (facturaFacturacionProgramadaDTOList == null || facturaFacturacionProgramadaDTOList.size() < 1) {
                throw new Exception(getRecurso("messages.facturacion.confirmacion.errorPdf"));
            }

            LOGGER.info("ALMACENAR >> " + institucion + " " + serieFacturacion + " " + idProgramacion);

            // Obtengo la plantilla a utilizar
            List<FacPlantillafacturacion> facPlantillafacturacionList = facPlantillafacturacionExtendsMapper.getPlantillaSerieFacturacion(institucion.toString(), serieFacturacion.toString());
            String sPlantilla = facPlantillafacturacionList.get(0).getPlantillapdf();

            //TODO Esta comprobacion de rutas no me parece correcta: deberia pasarse la ruta de o a algun metodo y no hacerlo aqui como en una isla sin relacion con nada a la vista
            // Obtencion de la ruta donde se almacenan temporalmente los ficheros formato FOP
            String rutaTemporal = getProperty(FACTURACION_DIRECTORIO_FISICO_TEMPORAL_FACTURAS_JAVA) + getProperty(FACTURACION__DIRECTORIO_TEMPORAL_FACTURAS_JAVA) + File.separator + institucion;
            SIGAHelper.mkdirs(rutaTemporal);

            // Obtencion de la ruta de donde se obtiene la plantilla adecuada
            String rutaPlantilla = getProperty(FACTURACION_DIRECTORIO_FISICO_PLANTILLA_FACTURA_JAVA) + getProperty(FACTURACION_DIRECTORIO_PLANTILLA_FACTURA_JAVA) + File.separator + institucion + File.separator + sPlantilla;
            File rutaModelo = new File(rutaPlantilla);

            //Comprobamos que exista la ruta y sino la creamos
            if (!rutaModelo.exists()) {
                // ESCRIBO EN EL LOG
                throw new Exception(getRecurso("messages.facturacion.almacenar.rutaPlantillaFacturaMal"));
            }

            LOGGER.info("ALMACENAR >> TERMINA DE OBTENER PLANTILLAS Y DATOS GENERALES");

            // recorro todas las facturas para ir creando lo sinformes pertinentes

            LOGGER.info("ALMACENAR >> NUMERO DE FACTURAS: " + facturaFacturacionProgramadaDTOList.size());

            String idFactura = "";

            /** CR7 - Se saca fuera ya que siempre se usa la misma plantilla para tdas las facturas **/
            //SE SELECCIONA LA PLANTILLA MAIL
            FacFacturacionprogramadaKey facFacturacionprogramadaKey = new FacFacturacionprogramadaKey();
            facFacturacionprogramadaKey.setIdinstitucion(institucion.shortValue());
            facFacturacionprogramadaKey.setIdseriefacturacion(serieFacturacion);
            facFacturacionprogramadaKey.setIdprogramacion(idProgramacion);

            FacFacturacionprogramada facProgBean = facProgMapper.selectByPrimaryKey(facFacturacionprogramadaKey);

            Integer plantillaMail = null;
            if (facProgBean != null && facProgBean.getIdtipoplantillamail() != null) {
                plantillaMail = facProgBean.getIdtipoplantillamail();
            }

            //Aunque nos ha fallado esta factura es posible que la siguiente, no.
            //POR LO TANTO no COMPROBAMOS QUE HAYA SIDO CORRECTO EL CAMBIO ANTERIOR
            //while (correcto && listaFacturas.hasMoreElements()){
            ArrayList<FacFicherosDescargaBean> listaFicherosPDFDescarga = new ArrayList<FacFicherosDescargaBean>();
            ArrayList<File> listaFicheros = new ArrayList<>();
            FacFicherosDescargaBean facFicherosDescargaBean = null;

            for (FacturaFacturacionProgramadaDTO f : facturaFacturacionProgramadaDTOList) {

                try {

                    facFicherosDescargaBean = new FacFicherosDescargaBean();
                    idFactura = f.getIdFactura();
                    String idPersona = f.getIdPersona().toString();
                    String numFactura = f.getNumeroFactura();

                    LOGGER.info("ALMACENAR " + idFactura + " >> FACTURA: " + idFactura);

                    // TRY del proceso de generacion de la factura en PDF
                    File filePDF = null;

                    try {

                        // PROCESO DE CREAR EL PDF
                        // RGG 15/02/2007 CAMBIOS PARA INFORME MASTER REPOR
                        AdmUsuarios usuario = new AdmUsuarios();
                        usuario.setIdusuario(USUARIO_AUTO);
                        usuario.setIdlenguaje(SigaConstants.LENGUAJE_DEFECTO);
                        filePDF = facturacionHelper.generarPdfFacturaFirmada(idFactura, f.getIdInstitucion().toString(), usuario);

                        if (filePDF == null) {
                            throw new Exception(getRecurso("message.facturacion.error.fichero.nulo"));
                        } else {

                            // Obtenemos el nombre de la persona de la factura
                            String nombreColegiado = "";
                            if (idPersona != null && !"".equalsIgnoreCase(idPersona)) {
                                nombreColegiado = obtenerNombreApellidos(idPersona);
                                if (nombreColegiado != null && !"".equalsIgnoreCase(nombreColegiado)) {
                                    nombreColegiado = eliminarAcentosYCaracteresEspeciales(nombreColegiado) + "-";
                                } else {
                                    nombreColegiado = "";
                                }
                            }

                            FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacion();
                            facSeriefacturacionKey.setIdinstitucion(f.getIdInstitucion());
                            facSeriefacturacionKey.setIdseriefacturacion(serieFacturacion);

                            FacSeriefacturacion facSeriefacturacion = facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey);

                            if (facSeriefacturacion != null) {
                                facFicherosDescargaBean.setFormatoDescarga(facSeriefacturacion.getIdNombreDescargaFac().intValue());
                                facFicherosDescargaBean.setFichero(filePDF);
                                facFicherosDescargaBean.setNombreFacturaFichero(nombreColegiado);
                                listaFicherosPDFDescarga.add(facFicherosDescargaBean);
                            }

                            listaFicheros.add(filePDF);

                        }

                        LOGGER.info("ALMACENAR " + idFactura + " >> FACTURA GENERADA EN PDF");

                    } catch (Exception e) {

                        LOGGER.error("ALMACENAR " + idFactura + " >> ERROR EN PROCESO DE FOP A PDF: " + e.getMessage());

                        // ESCRIBO EN EL LOG
                        log.writeLogFactura("PDF", idPersona, numFactura, "Error en el proceso de generacion de facturas PDF: " + e.getMessage());
                        salida = 1;
                        //Aunque nos ha fallado esta factura es posible que la siguiente, no.
                        //POR LO TANTO no cazamos la excepcion
                        //throw ee;
                        existeAlgunErrorPdf = true;

                    }

                    LOGGER.error("ALMACENAR " + idFactura + " >> VAMOS A VER SI ENVIARMOS: ENVIAR:" + bGenerarEnvios + " CORRECTO:" + (filePDF != null));


                    /***************    ENVIO FACTURAS *****************/
                    if (bGenerarEnvios && filePDF != null) {
                        enviarProgramacionFactura(idPersona, institucion.toString(), idFactura, plantillaMail, numFactura, filePDF, log, salida, existeAlgunErrorEnvio, tx);
                    }

                } catch (Exception e) {
                    LOGGER.error("ALMACENAR " + idFactura + " >> CATCH GENERAL");
                    throw e;
                }

                LOGGER.info("ALMACENAR " + idFactura + " >> PROCESO DE FACTURA OK ");

            } // bucle

            // Si tiene log es que esta generando y por tanto hay que crear los documentos excel y el zip
            if (log != null && !existeAlgunErrorPdf && listaFicheros.size() > 0) {

                File ficheroPdfFirmado = listaFicheros.get(0);
                String ruta = ficheroPdfFirmado.getParentFile().getParentFile().getPath();

                String rutaFicheroInformeConfirmacion = ruta + File.separator + facProgBean.getNombrefichero();
                File ficheroInformeConfirmacion = new File(rutaFicheroInformeConfirmacion);

                if (ficheroInformeConfirmacion.exists()) {
                    FacFicherosDescargaBean facFicherosDescargaBeanXls = new FacFicherosDescargaBean();
                    facFicherosDescargaBeanXls.setFichero(ficheroInformeConfirmacion);
                    facFicherosDescargaBeanXls.setFormatoDescarga(-1);     //Ponemos -4 para indicar que el nombre de este ficho en la descarga no se debe de modificar
                    listaFicherosPDFDescarga.add(facFicherosDescargaBeanXls);

                } else {
                    /**************  CREAMOS EL INFORME DE CONFIRMACION DE FACTURA QUE SE ANADIR AL ZIP DE FACTURAS EMITIDAS    ****************/
                    AdmInformeExample admInformeExample = new AdmInformeExample();
                    admInformeExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf("0")).andIdtipoinformeEqualTo("FACT");

                    List<AdmInforme> v = adInformeMapper.selectByExample(admInformeExample);

                    LOGGER.info("### Inicio datosInforme CONFIRMACION");

                    if (v != null && v.size() > 0) {

                        ArrayList<HashMap<String, String>> filtrosInforme = new ArrayList<>();
                        HashMap<String, String> filtro;
                        filtro = new HashMap<>();
                        filtro.put("NOMBRECAMPO", "IDIOMA");
                        filtro.put("VALOR", DEFAULT_LENGUAJE);
                        filtrosInforme.add(filtro);

                        filtro = new HashMap<>();
                        filtro.put("NOMBRECAMPO", "IDSERIEFACTURACION");
                        filtro.put("VALOR", serieFacturacion.toString());
                        filtrosInforme.add(filtro);

                        filtro = new HashMap<>();
                        filtro.put("NOMBRECAMPO", "IDPROGRAMACION");
                        filtro.put("VALOR", idProgramacion.toString());
                        filtrosInforme.add(filtro);

                        filtro = new HashMap<>();
                        filtro.put("NOMBRECAMPO", "IDINSTITUCION");
                        filtro.put("VALOR", institucion.toString());
                        filtrosInforme.add(filtro);

                        for (AdmInforme informe : v) {

                            informe.setNombresalida("CONFIRMACION_" + serieFacturacion + "_" + idProgramacion);

                            LOGGER.info("### Inicio generacion fichero excel CONFIRMACION");

                            ArrayList<File> listaFicherosConfirmacion = generarInformeXLS(informe, filtrosInforme, ruta, institucion.toString());

                            LOGGER.info("### Fin generacion fichero excel CONFIRMACION");

                            // Si no se generan los informes de confirmacion
                            if (listaFicherosConfirmacion == null || listaFicherosConfirmacion.size() == 0) {
                                LOGGER.info("### Error al generar el informe de la confirmacion. Inicio creacion fichero log CONFIRMACION sin datos");
                                throw new Exception(getRecurso("message.facturacion.error.fichero.nulo"));
                            }

                            for (int i = 0; i < listaFicherosConfirmacion.size(); i++) {

                                File ficheroXls = listaFicherosConfirmacion.get(0);

                                facProgBean.setNombrefichero(ficheroXls.getName());
                                facProgMapper.updateByPrimaryKeySelective(facProgBean);
                                commit(tx);

                                FacFicherosDescargaBean facFicherosDescargaBeanXls = new FacFicherosDescargaBean();
                                facFicherosDescargaBeanXls.setFichero(ficheroXls);
                                facFicherosDescargaBeanXls.setFormatoDescarga(-1);     //Ponemos -4 para indicar que el nombre de este ficho en la descarga no se debe de modificar
                                listaFicherosPDFDescarga.add(facFicherosDescargaBeanXls);

                            }


                        }

                    }

                    /********************************************************************************************************/

                }

                // inc6666 - Si es correcto generamos el ZIP con los pdfs firmados y el documento excel de facturacion
                ruta = ficheroPdfFirmado.getParentFile().getParentFile().getParentFile().getPath() + File.separator; // "\Datos\SIGADES\ficheros\facturas_emitidas\" + idInstitucion + "\"
                doZip(ruta, serieFacturacion.toString() + "_" + idProgramacion.toString(), listaFicherosPDFDescarga);

            }

            // Eliminacion de los pdfs firmados, el documento excel de la facturacion y su carpeta
            File directorio = null;
            for (int i = 0; i < listaFicheros.size(); i++) {
                File ficheroPdfFirmado = listaFicheros.get(i);
                directorio = ficheroPdfFirmado.getParentFile();
                ficheroPdfFirmado.delete(); // Elimina los pdfs firmados y los documentos excel de la facturacion
            }

            if (directorio != null && directorio.isDirectory() && directorio.list().length == 0) {
                directorio.delete(); // borra el directorio de las firmas
            }

        } catch (Exception e) {
            LOGGER.error("ALMACENAR >> ERROR GENERAL EN LA FUNCION ALMACENAR: " + e);
            // ESCRIBO EN EL LOG un mensaje general con la descripcion de la excepcion
            log.writeLogFactura("PDF/ENVIO", "N/A", "N/A", getRecurso("message.facturacion.error.generacion.envio.factura") + e);
            existeAlgunErrorEnvio = true;
            existeAlgunErrorPdf = true;
        }

        LOGGER.info("ALMACENAR >> SALIDA=" + salida);

        if (existeAlgunErrorPdf && existeAlgunErrorEnvio) {
            salida = 3;
        } else if (!existeAlgunErrorPdf && !existeAlgunErrorEnvio) {
            salida = 0;
        } else if (existeAlgunErrorPdf) {
            salida = 1;
        } else {
            salida = 2;
        }

        return salida;
    }

    protected String obtenerNombreApellidos(String idPersona) throws Exception {

        String nombre = "";

        try {

            CenPersona cenPersona = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(idPersona));

            if (cenPersona != null) {

                nombre = cenPersona.getNombre();

                if (cenPersona.getApellidos1() != null && !cenPersona.getApellidos1().equals("#NA")) {
                    nombre += " " + cenPersona.getApellidos1();
                }

                if (cenPersona.getApellidos2() != null && !cenPersona.getApellidos2().equals("#NA")) {
                    nombre += " " + cenPersona.getApellidos2();
                }

            }

        } catch (Exception e) {
            throw new Exception("Error al obtener el nombre y apellidos", e);
        }

        return nombre;
    }

    protected String eliminarAcentosYCaracteresEspeciales(String input) {

        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ!\"#$%&'()*+-,./:;<=>?@[\\]^_`{|}~";

        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC                                ";
        String output = input;

        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }// for i

        return output;
    }

    protected void enviarProgramacionFactura(String idPersona, String idInstitucion, String idFactura, Integer plantillaMail, String numeroFactura, File ficheroPdf,
                                             SIGALogging log, int salida, boolean existeAlgunErrorEnvio, TransactionStatus tx) throws Exception {

        try {
            LOGGER.info("ALMACENAR " + idFactura + " >> PROCESO DE ENVIO");

            //Obtenemos el bean del envio:
            String descripcion = "Envio factura " + numeroFactura + " - " + obtenerNombreApellidos(idPersona);

            // Bean envio
            EnvEnvios enviosBean = new EnvEnvios();
            generateBeanEnvio(enviosBean, Short.valueOf(idInstitucion), descripcion);

            // RGG
            String preferencia = getParametro(Short.valueOf(idInstitucion), "ENV", "TIPO_ENVIO_PREFERENTE", "1").getValor();
            Integer valorPreferencia = calculaTipoEnvio(preferencia);
            enviosBean.setIdtipoenvios(valorPreferencia.shortValue());

            // Preferencia del tipo de envio si el usuario tiene uno:
            List<EntradaDireccionEspecificaDTO> direccion = getEntradaDireccionEspecifica(idPersona, idInstitucion, preferencia);

            if (direccion == null || direccion.size() == 0) {
                direccion = getEntradaDireccionEspecifica(idPersona, idInstitucion, "3");// si no hay direccion preferente mail, buscamos la de correo
                if (direccion == null || direccion.size() == 0) {
                    direccion = getEntradaDireccionEspecifica(idPersona, idInstitucion, "2");// si no hay direccion de despacho, buscamos la de despacho
                    if (direccion == null || direccion.size() == 0) {
                        direccion = getEntradaDireccionEspecifica(idPersona, idInstitucion, "");// si no hay direccion de despacho, buscamos cualquier direccion.
                        if (direccion == null || direccion.size() == 0) {
                            LOGGER.info("ALMACENAR " + idFactura + " >> NO TIENE DIRECCION PREFERENTE " + preferencia);
                            throw new Exception("No se ha encontrado direcciOn de la persona para el tipo de envio preferente: " + preferencia);
                        }
                    }
                }
            }

            if (plantillaMail != null) {
                enviosBean.setIdplantillaenvios(plantillaMail);
                // Creacion documentos
                DocumentoDTO documento = new DocumentoDTO(ficheroPdf, "Factura " + ficheroPdf.getName());
                List<DocumentoDTO> documentos = new ArrayList<>();
                documentos.add(documento);

                /*************** INICIO TRANSACCION ***************/

                // Genera el envio:
                generarEnvio(enviosBean, idPersona, SigaConstants.TIPODESTINATARIO_CENPERSONA, documentos, null, null);
                commit(tx);
                LOGGER.info("ALMACENAR " + idFactura + " >> ENVIO GENERADO OK");
                /*************** FIN TRANSACCION ***************/

            } else {
                throw new Exception(getRecurso("messages.facturacion.almacenar.plantillasEnvioMal"));
            }


        } catch (Exception e) {

            rollBack(tx);

            LOGGER.error("ALMACENAR " + idFactura + " >> ERROR EN PROCESO DE ENVIO: " + e);
            // ESCRIBO EN EL LOG
            log.writeLogFactura("ENVIO", idPersona, numeroFactura, getRecurso("message.facturacion.error.envio.factura") + e);
            salida = 2;
            //Aunque nos ha fallado esta factura es posible que la siguiente, no.
            //POR LO TANTO no cazamos la excepcion
            //throw eee;
            existeAlgunErrorEnvio = true;
        }
    }

    protected void generateBeanEnvio(EnvEnvios enviosBean, Short idInstitucion, String descripcion) {
        enviosBean.setIdinstitucion(Short.valueOf(idInstitucion));
        enviosBean.setIdenvio(envEnviosExtendsMapper.getNewIdEnvio().longValue());
        enviosBean.setDescripcion(descripcion);
        // trunco la descripcion
        if (enviosBean.getDescripcion().length() > 200) {
            enviosBean.setDescripcion(enviosBean.getDescripcion().substring(0, 99));
        }
        enviosBean.setFechaprogramada(new Date());
        enviosBean.setGenerardocumento(getParametro(Short.valueOf(idInstitucion), "ENV", "GENERAR_DOCUMENTO_ENVIO", "C").getValor());
        enviosBean.setImprimiretiquetas(getParametro(Short.valueOf(idInstitucion), "ENV", "IMPRIMIR_ETIQUETAS_ENVIO", "1").getValor());
        enviosBean.setIdestado(SigaConstants.ENV_ENVIOS_ESTADOS.ESTADO_PENDIENTE_AUTOMATICO.getId());

        //Controlamos el valor preferente:
        String preferencia = getParametro(Short.valueOf(idInstitucion), "ENV", "TIPO_ENVIO_PREFERENTE", "1").getValor();
        Integer valorPreferencia = calculaTipoEnvio(preferencia);
        enviosBean.setIdtipoenvios(valorPreferencia.shortValue());
    }

    protected Integer calculaTipoEnvio(String preferencia) {
        //Controlamos el valor preferente:
        Integer valorPreferencia;

        if (preferencia.indexOf("E") != -1) {
            valorPreferencia = new Integer(1);
        } else {
            if (preferencia.indexOf("C") != -1) {
                valorPreferencia = new Integer(2);
            } else {
                if (preferencia.indexOf("F") != -1) {
                    valorPreferencia = new Integer(3);
                } else {
                    valorPreferencia = new Integer(1);
                }
            }
        }
        return valorPreferencia;
    }

    protected void doZip(String rutaServidorDescargasZip, String nombreFichero, ArrayList<FacFicherosDescargaBean> ficherosPDF) throws Exception {
        facturacionHelper.doZipGeneracionRapida(rutaServidorDescargasZip, nombreFichero, ficherosPDF);
    }

    protected List<EntradaDireccionEspecificaDTO> getEntradaDireccionEspecifica(String idPersona, String idInstitucion, String idDireccion) {
        return cenDireccionesExtendsMapper.getEntradaDireccionEspecifica(idPersona, idInstitucion, idDireccion);
    }

    protected void generarEnvio(EnvEnvios enviosBean, String idPersona, String tipoDestinatario, List<DocumentoDTO> documentos, ArrayList<?> designasList, ArrayList<?> ejgsList) throws Exception {

        //aalg: INC_06541_SIGA. incluir el usuario de modificacion al generar el envio
        enviosBean.setUsumodificacion(USUARIO_AUTO);
        enviosBean.setComisionajg(Short.valueOf(SigaConstants.DB_FALSE));
        envEnviosExtendsMapper.insert(enviosBean);

        // Copiamos los datos la plantilla, incluidos los remitentes
        copiarCamposPlantilla(enviosBean.getIdinstitucion().intValue(), enviosBean.getIdenvio().intValue(), enviosBean.getIdtipoenvios().intValue(), enviosBean.getIdplantillaenvios().intValue(), null);

        if (idPersona != null) {
            addDocumentosDestinatario(enviosBean, idPersona, tipoDestinatario, documentos);
        }
        insertarComunicaciones(enviosBean, designasList, ejgsList);

    }

    protected void copiarCamposPlantilla(Integer idInstitucion, Integer id, Integer idTipoEnvio, Integer idPlantilla, Object bean) throws Exception {

        //Borramos los campos de un envio
        EnvCamposenviosExample envCamposenviosExample = new EnvCamposenviosExample();
        envCamposenviosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion.shortValue())
                .andIdenvioEqualTo(id.longValue());
        envCamposenviosMapper.deleteByExample(envCamposenviosExample);

        //Copiamos los campos de la plantilla al envio
        EnvCamposplantillaExample envCamposplantillaExample = new EnvCamposplantillaExample();
        envCamposplantillaExample.createCriteria().andIdtipoenviosEqualTo(idTipoEnvio.shortValue())
                .andIdplantillaenviosEqualTo(idPlantilla);
        List<EnvCamposplantilla> vCamposPlantilla = envCamposplantillaMapper.selectByExample(envCamposplantillaExample);

        if (vCamposPlantilla != null) {

            for (EnvCamposplantilla cpBean : vCamposPlantilla) {
                EnvCamposenvios ceBean = new EnvCamposenvios();

                ceBean.setIdenvio(id.longValue());
                ceBean.setIdinstitucion(idInstitucion.shortValue());
                ceBean.setIdcampo(cpBean.getIdcampo());
                ceBean.setIdformato(cpBean.getIdformato());
                ceBean.setTipocampo(cpBean.getTipocampo());

                //TODO DENTRO DE LOS IFS HAY CODIGO QUE NO SE HA MIGRADO PORQUE NO SE UTILIZA
                if (cpBean.getTipocampo().equals(SigaConstants.K_TIPOCAMPO_E)) {
                    if (cpBean.getIdcampo().toString().equals(SigaConstants.K_IDCAMPO_CUERPO)) {
                        String sCuerpo = sustituirEtiquetas(cpBean.getValor(), new Hashtable());
                        cpBean.setValor(sCuerpo);
                    } else if (cpBean.getIdcampo().toString().equals(SigaConstants.K_IDCAMPO_ASUNTO)) {
                        String sAsunto = sustituirEtiquetas(cpBean.getValor(), new Hashtable());
                        cpBean.setValor(sAsunto);
                    }
                } else if (cpBean.getTipocampo().equals(SigaConstants.K_TIPOCAMPO_S)) {
                    if (cpBean.getIdcampo().toString().equals(SigaConstants.K_IDCAMPO_SMS)) {
                        String sCuerpo = sustituirEtiquetas(cpBean.getValor(), new Hashtable());
                        cpBean.setValor(sCuerpo);
                    }
                }

                ceBean.setValor(cpBean.getValor());
                envCamposenviosMapper.insert(ceBean);
            }

        }

        // Copiamos los remitentes de la plantilla
        try {
            copiarRemitentesDesdePlantilla(idInstitucion, id, idTipoEnvio, idPlantilla);
        } catch (Exception e) {
            throw new Exception("Error al copiar remitentes en el envio", e);
        }

    }

    protected String sustituirEtiquetas(String sArchivo, Hashtable etiquetas) throws Exception {
        String retorno = sustituirEtiquetas(sArchivo, etiquetas, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO);
        retorno = sustituirEtiquetas(retorno, etiquetas, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO);
        return retorno;
    }

    private String sustituirEtiquetas(String sArchivo, Hashtable etiquetas, String marcaInicioFin) throws Exception {
        try {
            if (!etiquetas.isEmpty()) {
                String key = "";
                for (Enumeration e = etiquetas.keys(); e.hasMoreElements(); ) {
                    key = (String) e.nextElement();
                    final Pattern pattern = Pattern.compile(marcaInicioFin + key + marcaInicioFin);
                    final Matcher matcher = pattern.matcher(sArchivo);
                    sArchivo = matcher.replaceAll((String) etiquetas.get(key));
                }
            }
            return sArchivo;
        } catch (Exception e) {
            throw new Exception("Error sustituyendo etiquetas", e);
        }
    }

    protected void copiarRemitentesDesdePlantilla(Integer idInstitucion, Integer idEnvio, Integer idTipoEnvios, Integer idPlantillaEnvios) throws Exception {

        try {

            // Verificamos is la plantilla tiene remitentes
            EnvPlantillaremitentesExample envPlantillaremitentesExample = new EnvPlantillaremitentesExample();
            envPlantillaremitentesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion.shortValue())
                    .andIdtipoenviosEqualTo(idTipoEnvios.shortValue())
                    .andIdplantillaenviosEqualTo(idPlantillaEnvios.intValue());
            List<EnvPlantillaremitentes> remitentes = envPlantillaremitentesMapper.selectByExample(envPlantillaremitentesExample);

            // Borramos los actuales los remitentes
            EnvRemitentesExample envRemitentesExample = new EnvRemitentesExample();
            envRemitentesExample.createCriteria().andIdenvioEqualTo(idEnvio.longValue())
                    .andIdinstitucionEqualTo(idInstitucion.shortValue());
            envRemitentesMapper.deleteByExample(envRemitentesExample);

            // Si la plantilla no tiene remitentes, se estable uno por defecto
            if (remitentes.size() <= 0) {
                addRemitentesPorDefecto(idInstitucion, idEnvio, idTipoEnvios, idPlantillaEnvios);
            } else {
                addRemitentesDesdePlantilla(idInstitucion, idEnvio, idTipoEnvios, idPlantillaEnvios, remitentes);
            }

        } catch (Exception e) {
            throw new Exception(getRecurso("messages.general.error"), e);
        }

    }

    private void addRemitentesPorDefecto(Integer idInstitucion, Integer idEnvio, Integer idTipoEnvios, Integer idPlantillaEnvios) throws Exception {

        try {

            CenInstitucion instBean = instMapper.selectByPrimaryKey(idInstitucion.shortValue());

            Long idPersona = instBean.getIdpersona();
            List<DireccionesDTO> direcciones = cenDireccionesExtendsMapper.getDirecciones(idPersona, idInstitucion, false);

            EnvRemitentes remBean = new EnvRemitentes();
            remBean.setIdenvio(idEnvio.longValue());
            remBean.setIdinstitucion(idInstitucion.shortValue());
            remBean.setIdpersona(idPersona);
            remBean.setDescripcion("");

            if (direcciones != null && direcciones.size() > 0) {
                DireccionesDTO direccion = direcciones.get(0);
                remBean.setDomicilio(direccion.getDomicilio());
                remBean.setIdpoblacion(direccion.getIdPoblacion());
                remBean.setPoblacionextranjera(direccion.getPoblacionExtranjera());
                remBean.setIdprovincia(direccion.getIdProvincia());
                remBean.setIdpais(direccion.getIdPais());
                if (remBean.getIdpais().equals("")) {
                    remBean.setIdpais(SigaConstants.ID_PAIS_ESPANA);
                }
                remBean.setCodigopostal(direccion.getCodigoPostal());
                remBean.setCorreoelectronico(direccion.getCorreoElectronico());
                remBean.setFax1(direccion.getFax1());
                remBean.setFax2(direccion.getFax2());
            }

            //Insertamos bean si no existe en la tabla
            if (!existeRemitente("" + idEnvio, "" + idInstitucion, "" + idPersona)) {
                envRemitentesMapper.insert(remBean);
            } else {
                throw new Exception(getRecurso("messages.envios.error.existeelemento"));
            }

        } catch (Exception e) {
            throw new Exception("Error al anadir los remitentes por defecto", e);
        }

    }

    protected boolean existeRemitente(String idEnvio, String idInstitucion, String idPersona) {
        EnvRemitentesKey envRemitentesKey = new EnvRemitentesKey();
        envRemitentesKey.setIdenvio(Long.valueOf(idEnvio));
        envRemitentesKey.setIdinstitucion(Short.valueOf(idInstitucion));
        envRemitentesKey.setIdpersona(Long.valueOf(idPersona));

        EnvRemitentes envRemitentes = envRemitentesMapper.selectByPrimaryKey(envRemitentesKey);

        return (envRemitentes != null);
    }

    protected void addRemitentesDesdePlantilla(Integer idInstitucion, Integer idEnvio, Integer idTipoEnvios, Integer idPlantillaEnvios, List<EnvPlantillaremitentes> remitentes) throws Exception {

        try {

            if (remitentes.size() < 1) {
                this.addRemitentesPorDefecto(idInstitucion, idEnvio, idTipoEnvios, idPlantillaEnvios);
                return;
            }

            // Copiamos todos los remitentes de la plantilla
            for (EnvPlantillaremitentes b : remitentes) {
                EnvRemitentes remBean = new EnvRemitentes();
                remBean.setIdenvio(idEnvio.longValue());
                remBean.setIdinstitucion(b.getIdinstitucion());
                remBean.setIdpersona(b.getIdpersona());
                remBean.setDescripcion(b.getDescripcion());
                remBean.setDomicilio(b.getDomicilio());
                remBean.setIdpoblacion(b.getIdpoblacion());
                remBean.setPoblacionextranjera(b.getPoblacionextranjera());
                remBean.setIdprovincia(b.getIdprovincia());
                remBean.setIdpais(b.getIdpais());
                if (remBean.getIdpais().equals("")) {
                    remBean.setIdpais(SigaConstants.ID_PAIS_ESPANA);
                }
                remBean.setCodigopostal(b.getCodigopostal());
                remBean.setCorreoelectronico(b.getCorreoelectronico());
                remBean.setFax1(b.getFax1());
                remBean.setFax2(b.getFax2());

                //remitentesAdm.insert(remBean);
                //Insertamos bean si no existe en la tabla
                if (existeRemitente("" + idEnvio, "" + idInstitucion, "" + b.getIdpersona())) {
                    envRemitentesMapper.insert(remBean);
                } else {
                    throw new Exception(getRecurso("messages.envios.error.existeelemento"));
                }
            }

        } catch (Exception e) {
            throw new Exception("Error al copiar remitentes desde plantilla", e);
        }

    }

    protected void addDocumentosDestinatario(EnvEnvios enviosBean, String idPersona, String tipoDestinatario, List<DocumentoDTO> documentos) throws Exception {

        try {

            boolean crearDestinatario;
            EnvDestinatarios destBean;

            List<DireccionesDTO> direcciones;
            if (!existeDestinatario(String.valueOf(enviosBean.getIdenvio()), String.valueOf(enviosBean.getIdinstitucion()), idPersona)) {
                destBean = new EnvDestinatarios();
                destBean.setIdenvio(enviosBean.getIdenvio());
                destBean.setIdinstitucion(enviosBean.getIdinstitucion());
                destBean.setIdpersona(Long.valueOf(idPersona));
                destBean.setTipodestinatario(tipoDestinatario);

                if (tipoDestinatario.equals(SigaConstants.TIPODESTINATARIO_CENPERSONA)) {
                    direcciones = getDirecciones(String.valueOf(enviosBean.getIdinstitucion()), idPersona, String.valueOf(enviosBean.getIdtipoenvios()));


                    CenPersona persona = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(idPersona));

                    destBean.setApellidos1(persona.getApellidos1());
                    destBean.setApellidos2(persona.getApellidos2());
                    destBean.setNombre(persona.getNombre());

                    if (direcciones != null && direcciones.size() > 0) {
                        DireccionesDTO htDir = direcciones.get(0);
                        destBean.setDomicilio(htDir.getDomicilio());
                        destBean.setIdpoblacion(htDir.getIdPoblacion());
                        destBean.setPoblacionextranjera(htDir.getPoblacionExtranjera());
                        destBean.setIdprovincia(htDir.getIdProvincia());
                        destBean.setIdpais(htDir.getIdPais());
                        if (destBean.getIdpais().equals("")) {
                            destBean.setIdpais(SigaConstants.ID_PAIS_ESPANA);
                        }
                        destBean.setCodigopostal(htDir.getCodigoPostal());
                        destBean.setCorreoelectronico(htDir.getCorreoElectronico());
                        destBean.setFax1(htDir.getFax1());
                        destBean.setFax2(htDir.getFax2());
                        destBean.setMovil(htDir.getMovil());
                    }
                } else if (tipoDestinatario.equals(SigaConstants.TIPODESTINATARIO_SCSPERSONAJG)) {
                    ScsPersonaJGBean personaJGBean = getPersonaJG(Long.valueOf(idPersona), enviosBean.getIdinstitucion().intValue());

                    destBean.setApellidos1(personaJGBean.getApellido1());
                    destBean.setApellidos2(personaJGBean.getApellido2());
                    destBean.setNombre(personaJGBean.getNombre());
                    destBean.setNifcif(personaJGBean.getNif());

                    destBean.setDomicilio(personaJGBean.getDireccion());
                    destBean.setIdpoblacion(personaJGBean.getIdPoblacion());
                    destBean.setIdprovincia(personaJGBean.getProIdProvincia());
                    destBean.setIdpais(personaJGBean.getIdPais());
                    if (destBean.getIdpais().equals("")) {
                        destBean.setIdpais(SigaConstants.ID_PAIS_ESPANA);
                    }
                    destBean.setCodigopostal(personaJGBean.getCodigoPostal());
                    destBean.setCorreoelectronico(personaJGBean.getCorreoElectronico());
                    destBean.setFax1(personaJGBean.getFax());

                    //CR7 - Ahora esta gestion de telefonos depende del parametro ENVIAR_SMS_SOLO_PREFERENTE y ENVIAR_BUROSMS_SOLO_PREFERENTE

                    List<ScsTelefonospersona> vTelefonos = personaJGBean.getTelefonos();
                    if (vTelefonos != null && vTelefonos.size() > 0) {
                        String activoParamSMS = "0";
                        if (enviosBean.getIdtipoenvios() == SigaConstants.K_SMS) {
                            activoParamSMS = getParametro(enviosBean.getIdinstitucion(), "SCS", "ENVIAR_SMS_SOLO_PREFERENTES", "0").getValor();
                        } else if (enviosBean.getIdtipoenvios() == SigaConstants.K_BUROSMS) {
                            activoParamSMS = getParametro(enviosBean.getIdinstitucion(), "SCS", "ENVIAR_BUROSMS_SOLO_PREFERENTES", "0").getValor();
                        }

                        if (activoParamSMS.equals("1")) { //Si el parametro es 1 solo se enviaran los marcados
                            for (int i = 0; i < vTelefonos.size(); i++) {
                                ScsTelefonospersona telefono = vTelefonos.get(i);
                                if (telefono.getPreferentesms() != null && telefono.getPreferentesms().equals("1")) {
                                    if (UtilidadesString.esNumMovilECOS(telefono.getNumerotelefono())) {
                                        destBean.setMovil(telefono.getNumerotelefono());
                                        break;
                                    }
                                }
                            }
                        } else if (activoParamSMS.equals("0")) { //Si el parametro es 0 se genstionara como se hacia anteriormente
                            boolean bTienePreferenteSMS = false;
                            for (int i = 0; i < vTelefonos.size(); i++) {
                                ScsTelefonospersona telefono = vTelefonos.get(i);
                                if (telefono.getPreferentesms() != null && telefono.getPreferentesms().equals("1")) {
                                    bTienePreferenteSMS = true;
                                    if (UtilidadesString.esNumMovilECOS(telefono.getNumerotelefono())) {
                                        destBean.setMovil(telefono.getNumerotelefono());
                                        break;
                                    }
                                }
                                //BEGIN BNS INC_09399_SIGA
                                else if (!bTienePreferenteSMS) {
                                    //Buscamos si existe algun telefono que parezca movil aunque no tenga el preferenteSMS
                                    if (UtilidadesString.esNumMovilECOS(telefono.getNumerotelefono())) {
                                        destBean.setMovil(telefono.getNumerotelefono());
                                    }
                                }
                                // END BNS INC_09399_SIGA
                            }
                        }
                    }
                } else if (tipoDestinatario.equals(SigaConstants.TIPODESTINATARIO_SCSPROCURADOR)) {

                    ScsProcuradorKey scsProcuradorKey = new ScsProcuradorKey();
                    scsProcuradorKey.setIdinstitucion(enviosBean.getIdinstitucion());
                    scsProcuradorKey.setIdprocurador(Long.valueOf(idPersona));
                    ScsProcurador procurador = scsProcuradorExtendsMapper.selectByPrimaryKey(scsProcuradorKey);

                    destBean.setApellidos1(procurador.getApellidos1());
                    destBean.setApellidos2(procurador.getApellidos2());
                    destBean.setNombre(procurador.getNombre());
                    destBean.setNifcif("");

                    destBean.setDomicilio(procurador.getDomicilio());
                    destBean.setIdpoblacion(procurador.getIdpoblacion());
                    destBean.setIdprovincia(procurador.getIdprovincia());
                    destBean.setIdpais(SigaConstants.ID_PAIS_ESPANA);

                    destBean.setCodigopostal(procurador.getCodigopostal());
                    destBean.setCorreoelectronico(procurador.getEmail());
                    destBean.setFax1(procurador.getFax1());
                    destBean.setFax2(procurador.getFax1());
                    destBean.setMovil(procurador.getTelefono1());

                } else if (tipoDestinatario.equals(SigaConstants.TIPODESTINATARIO_SCSJUZGADO)) {

                    ScsJuzgadoKey scsJuzgadoKey = new ScsJuzgadoKey();
                    scsJuzgadoKey.setIdinstitucion(enviosBean.getIdinstitucion());
                    scsJuzgadoKey.setIdjuzgado(Long.valueOf(idPersona));

                    ScsJuzgado juzgado = scsJuzgadoExtendsMapper.selectByPrimaryKey(scsJuzgadoKey);

                    destBean.setNombre(juzgado.getNombre());
                    destBean.setApellidos1("");
                    destBean.setApellidos2("");
                    destBean.setNifcif("");

                    destBean.setDomicilio(juzgado.getDomicilio());
                    destBean.setIdpoblacion(juzgado.getIdpoblacion());
                    destBean.setIdprovincia(juzgado.getIdprovincia());
                    destBean.setIdpais(SigaConstants.ID_PAIS_ESPANA);

                    destBean.setCorreoelectronico(juzgado.getEmail());
                    destBean.setCodigopostal(juzgado.getCodigopostal());
                    destBean.setFax1(juzgado.getFax1());
                    destBean.setFax2(juzgado.getFax1());
                    destBean.setMovil(juzgado.getTelefono1());
                }

                crearDestinatario = true;

            } else {

                EnvDestinatariosKey envDestinatariosKey = new EnvDestinatariosKey();
                envDestinatariosKey.setIdenvio(enviosBean.getIdenvio());
                envDestinatariosKey.setIdinstitucion(enviosBean.getIdinstitucion());
                envDestinatariosKey.setIdpersona(Long.valueOf(idPersona));

                destBean = envDestinatariosMapper.selectByPrimaryKey(envDestinatariosKey);
                crearDestinatario = false;
            }
            addDestinatarioIndividualDocAdjuntos(enviosBean, destBean, documentos, crearDestinatario);
            // RGG 29-09-2005 se quita para usar en todos los lados la funcion de arriba
            // addDestinatarioIndividual(destBean,documentos,crearDestinatario);

        } catch (Exception e) {
            throw new Exception(getRecurso("messages.general.error"), e);
        }

    }

    protected boolean existeDestinatario(String idEnvio, String idInstitucion, String idPersona) {
        EnvDestinatariosKey envDestinatariosKey = new EnvDestinatariosKey();
        envDestinatariosKey.setIdenvio(Long.valueOf(idEnvio));
        envDestinatariosKey.setIdinstitucion(Short.valueOf(idInstitucion));
        envDestinatariosKey.setIdpersona(Long.valueOf(idPersona));

        EnvDestinatarios envDestinatarios = envDestinatariosMapper.selectByPrimaryKey(envDestinatariosKey);

        return (envDestinatarios != null);
    }

    protected List<DireccionesDTO> getDirecciones(String idInstitucion, String idPersona, String idTipoEnvio) throws
            Exception {

        List<DireccionesDTO> direcciones;

        try {
            direcciones = cenDireccionesExtendsMapper.getDirecciones(Long.valueOf(idPersona), Integer.valueOf(idInstitucion), false);
        } catch (Exception e) {
            throw new Exception("Error al ejecutar el 'select' en B.D.", e);
        }

        List<DireccionesDTO> direccionesTipo = new ArrayList<>();
        int tipo = 0;
        try {
            tipo = Integer.valueOf(idTipoEnvio).intValue();
        } catch (Exception e) {
            tipo = -1;
        }

        boolean foundPrimeraPreferente = false;

        switch (tipo) {
            case SigaConstants.K_CORREO_ELECTRONICO:
            case SigaConstants.K_DOCUMENTACIONLETRADO:
                for (int i = 0; i < direcciones.size(); i++) {
                    DireccionesDTO htDir = direcciones.get(i);
                    String correoElectronico = htDir.getCorreoElectronico();
                    if (correoElectronico != null && !correoElectronico.equals("")) {
                        String preferente = htDir.getPreferente();
                        if (preferente != null && !preferente.equals("")) {
                            if (preferente.contains(SigaConstants.TIPO_PREFERENTE_CORREOELECTRONICO) && !foundPrimeraPreferente) {
                                foundPrimeraPreferente = true;
                                direccionesTipo.add(0, htDir);
                            } else {
                                direccionesTipo.add(htDir);

                            }


                        } else {
                            direccionesTipo.add(htDir);
                        }
                    }
                }
                break;
            case SigaConstants.K_CORREO_ORDINARIO:
                for (int i = 0; i < direcciones.size(); i++) {
                    DireccionesDTO htDir = direcciones.get(i);
                    String direccion = htDir.getDomicilio();
                    String idPoblacion = htDir.getIdPoblacion();
                    String cp = htDir.getCodigoPostal();
                    if ((direccion != null && !direccion.equals(""))
                            && ((idPoblacion != null && !idPoblacion.equals("")) || (cp != null && !cp.equals("")))) {
                        String preferente = htDir.getPreferente();
                        if (preferente != null) {
                            if (preferente.contains(SigaConstants.TIPO_PREFERENTE_CORREO) && !foundPrimeraPreferente) {
                                foundPrimeraPreferente = true;
                                direccionesTipo.add(0, htDir);
                            } else {
                                direccionesTipo.add(htDir);

                            }

                        } else {
                            direccionesTipo.add(htDir);
                        }


                    }
                }
                break;
            case SigaConstants.K_SMS:
            case SigaConstants.K_BUROSMS:
                for (int i = 0; i < direcciones.size(); i++) {
                    DireccionesDTO htDir = direcciones.get(i);
                    String movil = htDir.getMovil();
                    if (movil != null && !movil.equals("")) {
                        String preferente = htDir.getPreferente();
                        if (preferente != null) {
                            if (preferente.contains(SigaConstants.TIPO_PREFERENTE_SMS) && !foundPrimeraPreferente) {
                                foundPrimeraPreferente = true;
                                direccionesTipo.add(0, htDir);
                            } else {
                                direccionesTipo.add(htDir);

                            }

                        } else {
                            direccionesTipo.add(htDir);
                        }
                    }
                }
                break;
            case SigaConstants.K_FAX:
                for (int i = 0; i < direcciones.size(); i++) {
                    DireccionesDTO htDir = direcciones.get(i);
                    String fax1 = htDir.getFax1();
                    String fax2 = htDir.getFax2();
                    if ((fax1 != null && !fax1.equals("")) ||
                            (fax2 != null && !fax2.equals(""))) {
                        String preferente = htDir.getPreferente();
                        if (preferente != null) {
                            if (preferente.contains(SigaConstants.TIPO_PREFERENTE_FAX) && !foundPrimeraPreferente) {
                                foundPrimeraPreferente = true;
                                direccionesTipo.add(0, htDir);
                            } else {
                                direccionesTipo.add(htDir);

                            }

                        } else {
                            direccionesTipo.add(htDir);
                        }
                    }
                }
                break;
            default:
                direccionesTipo = direcciones;
                break;
        }

        return direccionesTipo;
    }

    protected ScsPersonaJGBean getPersonaJG(Long idPersona, Integer idInstitucion) throws Exception {

        ScsPersonaJGBean personaJG;

        try {
            personaJG = scsPersonajgExtendsMapper.getPersonaJG(idPersona, idInstitucion).stream().findFirst().orElse(null);

            if (personaJG != null) {

                if (!personaJG.getIdPoblacion().equals("")) {
                    CenPoblaciones poblacion = new CenPoblaciones();
                    poblacion.setNombre(personaJG.getPoblacionStr());
                    personaJG.setPoblacion(poblacion);
                }

                if (!personaJG.getProIdProvincia().equals("")) {
                    CenProvincias provincia = new CenProvincias();
                    provincia.setNombre(personaJG.getProvinciaStr());
                    personaJG.setProvincia(provincia);
                }

                ScsTelefonospersonaExample scsTelefonospersonaExample = new ScsTelefonospersonaExample();
                scsTelefonospersonaExample.createCriteria().andIdpersonaEqualTo(idPersona)
                        .andIdinstitucionEqualTo(idInstitucion.shortValue());
                List<ScsTelefonospersona> vTelefonos = scsTelefonosPersonaExtendsMapper.selectByExample(scsTelefonospersonaExample);
                personaJG.setTelefonos(vTelefonos);
            }
        } catch (Exception e) {
            throw new Exception("Error al ejecutar consulta.", e);
        }

        return personaJG;
    }

    protected void addDestinatarioIndividualDocAdjuntos(EnvEnvios enviosBean, EnvDestinatarios destBean, List<DocumentoDTO> vDocumentos, boolean crearDestinatario) throws Exception {
        addDestinatarioIndividualDocAdjuntos(enviosBean, destBean, vDocumentos, crearDestinatario, false);
    }

    protected void addDestinatarioIndividualDocAdjuntos(EnvEnvios enviosBean, EnvDestinatarios destBean, List<DocumentoDTO> vDocumentos, boolean crearDestinatario, boolean crearCertificado) throws Exception {

        try {
            if (crearDestinatario || crearCertificado) {
                //***** Insertamos Destinatario *******
                //
                //EnvEnviosAdm enviosAdm = new EnvEnviosAdm(idUsuario);
                int tipo = enviosBean.getIdtipoenvios().intValue();

//		        if (crearCertificado || comprobarDireccion(tipo,destBean)){
                try {
                    envDestinatariosMapper.insert(destBean);
                } catch (Exception e) {
                    throw new Exception(getRecurso("messages.general.error"), e);
                }
//		        }
            }

            //******** Insertamos Documentos *********//
            if (vDocumentos != null && vDocumentos.size() > 0) {
                String idInstitucion = String.valueOf(destBean.getIdinstitucion());
                String idEnvio = String.valueOf(destBean.getIdenvio());
                String idPersona = String.valueOf(destBean.getIdpersona());

                EnvDocumentos documentoBean = null;

                for (int i = 0; i < vDocumentos.size(); i++) {
                    //Recuperamos el documento:
                    DocumentoDTO doc = vDocumentos.get(i);

                    //Insertar documentos adjuntos:
                    documentoBean = new EnvDocumentos();

                    //Calculamos el nuevo ID:
                    Integer idDoc = envDocumentosExtendsMapper.getNewIdDocumento(idInstitucion, idEnvio);

                    //Almacenamos el documento:
                    guardarDoc(idInstitucion, idEnvio, idPersona, String.valueOf(idDoc), doc);

                    documentoBean.setIdinstitucion(destBean.getIdinstitucion());
                    documentoBean.setIdenvio(destBean.getIdenvio());
                    documentoBean.setIddocumento(idDoc.shortValue());
                    documentoBean.setPathdocumento(doc.getDocumento().getName());
                    documentoBean.setDescripcion(doc.getDescripcion());
                    doc.setBeanAsociado(documentoBean);
                    envDocumentosExtendsMapper.insert(documentoBean);
                }
            }

        } catch (Exception e) {
            throw new Exception("Error en Envio.addDestinatarioIndividualDocAdjuntos", e);
        }
    }

    protected void guardarDoc(String idInstitucion, String idEnvio, String idPersona, String idDoc, DocumentoDTO doc) throws Exception {

        String sNombreFichero = "";
        File fDocumento = doc.getDocumento();

        //Recuperamos los datos del envio

        //Vector vEnvio =  envioAdm.selectByPK(htPk);
        //EnvEnviosBean envioBean =(EnvEnviosBean)vEnvio.firstElement();

        if (fDocumento.exists()) {
            FileInputStream FIStream = null;
            OutputStream bos = null;
            String sNombreDocumento = idInstitucion + "_" + idEnvio + "_" + idDoc;

            try {
                String sDirectorio = getPathEnvio(idInstitucion, idEnvio);
                SIGAHelper.mkdirs(sDirectorio);

                FIStream = new FileInputStream(fDocumento);
                sNombreFichero = sDirectorio + File.separator + sNombreDocumento;
                bos = new FileOutputStream(sNombreFichero);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];

                while ((bytesRead = FIStream.read(buffer, 0, 8192)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }

                bos.close();
            } catch (Exception e) {
                throw new Exception("Error copiando el documento", e);
//	    	    throw new SIGAException("messages.envios.error.copiarDocumento",e);
            } finally {
                try {
                    bos.close();
                    FIStream.close();
                } catch (Exception eee) {
                }
            }
        } else {
            throw new Exception(getRecurso("messages.envios.error.noExisteDocumento"));
        }

    }

    protected String getPathEnvio(String idInstitucion, String idEnvio) throws Exception {

        EnvEnviosKey envEnviosKey = new EnvEnviosKey();
        envEnviosKey.setIdinstitucion(Short.valueOf(idInstitucion));
        envEnviosKey.setIdenvio(Long.valueOf(idEnvio));
        EnvEnvios envioBean = envEnviosExtendsMapper.selectByPrimaryKey(envEnviosKey);

        Calendar cal = Calendar.getInstance();
        Date d = envioBean.getFecha();
        cal.setTime(d);

        String anio = String.valueOf(cal.get(Calendar.YEAR));
        String mes = String.valueOf(cal.get(Calendar.MONTH) + 1);

        String pathDoc;
        try {
            pathDoc = getParametro(SigaConstants.ID_INSTITUCION_0, SigaConstants.MODULO_ENV, SigaConstants.PATH_DOCUMENTOSADJUNTOS).getValor();
        } catch (Exception e) {
            throw e;
        }

        String sDirectorio = pathDoc + File.separator + idInstitucion + File.separator + anio + File.separator + mes + File.separator + idEnvio;

        return sDirectorio;
    }

    protected void insertarComunicaciones(EnvEnvios enviosBean, ArrayList<?> designasList, ArrayList<?> ejgsList) throws Exception {

        //TODO ESTE CODIGO NO SE HA TERMINADO DE MIGRAR PORQUE NO SE UTILIZA
//        if (designasList != null && designasList.size() > 0) {
//            try {
//                insertarComunicacionDesignaSalida(designasList, Long.parseLong(enviosBean.getIdenvio().toString()));
//            } catch (Exception e) {
//                throw new Exception("Error al insertar en scs_comunicaciones" + e);
//            }
//        }
//        if (ejgsList != null && ejgsList.size() > 0) {
//            try {
//                insertarComunicacionEjgSalida(ejgsList, Long.parseLong(enviosBean.getIdenvio().toString()));
//            } catch (Exception e) {
//                throw new Exception("Error al insertar en scs_comunicaciones" + e);
//            }
//
//        }

    }

    protected Path getPathLogConfirmacion(FacFacturacionprogramada fac) {
        String nombreFichero = getNombreFicheroLogConfirmacion(fac);
        String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
        return Paths.get(pathFichero).resolve(fac.getIdinstitucion().toString()).resolve(nombreFichero);
    }

    protected String getNombreFicheroLogConfirmacion(FacFacturacionprogramada fac) {
        return LOG_FAC_CONFIRMACION_PREFIX + fac.getIdseriefacturacion() + "_" + fac.getIdprogramacion() + LOG_XLS;
    }
}
