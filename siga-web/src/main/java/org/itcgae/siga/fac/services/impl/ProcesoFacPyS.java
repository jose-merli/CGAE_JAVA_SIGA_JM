package org.itcgae.siga.fac.services.impl;

import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmInformeMapper;
import org.itcgae.siga.db.mappers.AdmTipofiltroinformeMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmConsultainformeExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public abstract class ProcesoFacPyS {

    protected static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR_ENVIO = "messages.facturacion.confirmacion.errorEnvio";
    protected static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF = "messages.facturacion.confirmacion.errorPdf";
    protected static final String TRASPASO_FACTURAS_WS_ACTIVO = "TRASPASO_FACTURAS_WS_ACTIVO";
    protected static final String MESSAGES_FACTURACION_CONFIRMACION_ERROR = "messages.facturacion.confirmacion.error";
    protected static final String PROC_PAGOS_BANCO = "{call PKG_SIGA_CARGOS.PRESENTACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    protected static final String PROC_GENERACION_FACTURACION = "{call PKG_SIGA_FACTURACION.GENERACIONFACTURACION(?,?,?,?,?,?,?,?)}";
    protected static final String PROC_CONFIRMACION_FACTURACION = "{call PKG_SIGA_FACTURACION.CONFIRMACIONFACTURACION(?,?,?,?,?,?,?,?)}";
    protected static final String COD_OK = "0";
    protected static final String COD_FACTURACION_CONFIRMACION_ERROR_PDF = "-208";
    protected static final String COD_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO = "-205";
    protected static final String MSG_FACTURACION_CONFIRMAR_CONTADOR_REPETIDO = "messages.facturacion.confirmar.contadorRepetido";
    protected static final String MSG_FACTURACION_CONFIRMACION_ERROR_PDF = MESSAGES_FACTURACION_CONFIRMACION_ERROR_PDF;
    protected static final String MSG_FACTURACION_CONFIRMAR_FACTURACION_MENSAJE_GENERACION_DISQUETES_ERROR = "facturacion.confirmarFacturacion.mensaje.generacionDisquetesERROR";
    protected static final String FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION = "facturacion.directorioFisicoLogProgramacion";
    protected static final String LOG_XLS = ".log.xls";
    protected static final String LOG_FAC_CONFIRMACION_PREFIX = "LOG_FAC_CONFIRMACION_";
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

    protected TransactionStatus getNewLongTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(getTimeoutLargo());
        def.setName("transGenFac");
        return transactionManager.getTransaction(def);
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
        }

        return value;
    }

    protected String getParametro(final String key) {
        return getParametro(key, null, null);
    }

    protected String getParametro(final String key, final String defaultValue) {
        return getParametro(key, null, defaultValue);
    }

    protected <T> T getParametro(final String key, Function<String, T> convert, T defValue) {
        T value = null;
        String sValue = null;
        GenParametrosExample pEx = new GenParametrosExample();
        pEx.createCriteria().andParametroEqualTo(key);

        List<GenParametros> lParam = genParametrosMapper.selectByExample(pEx);
        if (lParam.size() > 0) {
            sValue = lParam.get(0).getValor();
        }

        if (convert != null) {
            value = convert.apply(sValue);
        }

        return value;
    }

    protected void marcaEjecutandoGeneracion(FacFacturacionprogramada fac) {
        fac.setIdestadoconfirmacion(FacEstadosFacturacion.EJECUTANDO_GENERACION.getId());
        fac.setUsumodificacion(USUARIO_AUTO);
        fac.setFechamodificacion(new Date());
        facProgMapper.updateByPrimaryKey(fac);
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

}
