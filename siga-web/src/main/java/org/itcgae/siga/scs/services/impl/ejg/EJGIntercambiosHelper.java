package org.itcgae.siga.scs.services.impl.ejg;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomIntercambio;
import org.itcgae.siga.db.entities.EcomIntercambioExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
import org.itcgae.siga.db.entities.ScsDocumentacionejgExample;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgMapper;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.ecom.mappers.EcomIntercambioExtendsMapper;
import org.itcgae.siga.scs.services.impl.facturacionsjcs.FacturacionSJCSHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EJGIntercambiosHelper {

    private Logger LOGGER = Logger.getLogger(EJGIntercambiosHelper.class);

    private static String PCAJG_WS_URL = "PCAJG_WS_URL";
    private static String MODULO_SCS = "SCS";

    @Autowired
    private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

    @Autowired
    private ScsPersonajgMapper scsPersonajgMapper;

    @Autowired
    private FacturacionSJCSHelper facturacionSJCSHelper;

    @Autowired
    private ScsEjgMapper scsEjgMapper;

    @Autowired
    private ScsEstadoejgMapper scsEstadoejgMapper;

    @Autowired
    private ScsDocumentacionejgMapper scsDocumentacionejgMapper;

    @Autowired
    private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;

    @Autowired
    private ScsContrariosejgMapper scsContrariosejgMapper;

    @Autowired
    private ScsEejgPeticionesMapper scsEejgPeticionesMapper;

    @Autowired
    private EcomIntercambioExtendsMapper ecomIntercambioExtendsMapper;

    @Autowired
    private GenParametrosMapper genParametrosMapper;

    public boolean isColegioZonaComun(Short idInstitucion) {
        List<CenInstitucion> zonaComun = cenInstitucionExtendsMapper.getInstitucionByGrupo(idInstitucion, SigaConstants.GRUPOINSTITUCION.COMUN_MINI.getCodigoGrupo());
        return !zonaComun.isEmpty();
    }

    public boolean isColegioConfiguradoEnvioPericles(Short idInstitucion) {
        return getValorParametroWithNull(idInstitucion, PCAJG_WS_URL, MODULO_SCS) != null;
    }


    @Transactional
    public void insertarConsultaEstadoEnCola(ScsEjg ejg) throws Exception {
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, ejg.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, ejg.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, ejg.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, ejg.getNumero().toString());

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(ejg.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_CONSULTA_ESTADO.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
    }


    @Transactional
    public void insertarDocumentacionAdicionalEnCola(ScsDocumentacionejg documentacionejg) throws Exception {
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, documentacionejg.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, documentacionejg.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, documentacionejg.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, documentacionejg.getNumero().toString());

        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUMENTACION, documentacionejg.getIddocumentacion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO, SigaConstants.DB_TRUE);

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(documentacionejg.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIO_COMUNICACION.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
    }


    enum CASO {
        PERICLES_INSERT_ESTADO, PERICLES_UPDATE_ESTADO, PERICLES_REENVIA
    }

    public void insertaCambioEstadoPericles(ScsEstadoejg estado) throws Exception {
        LOGGER.info("insertaEstadoEjg() -> Entrando al servicio que envia la documentación al CAJG si es necesario");
        if (isColegioZonaComun(estado.getIdinstitucion())
                && isColegioConfiguradoEnvioPericles(estado.getIdinstitucion())) {

            ScsEjg ejg = getScsEjg(estado.getIdinstitucion(), estado.getAnio(), estado.getIdtipoejg(), estado.getNumero());
            if (UtilidadesString.esCadenaVacia(ejg.getNumeroCajg())) {
                envioPericlesExpediente(estado, EJGIntercambiosHelper.CASO.PERICLES_INSERT_ESTADO);
            } else {
                encolaEnvioDocumentacion(ejg);
            }
        } else {
            LOGGER.info("insertaEstadoEjg() <- El colegio no pertenece a la zona común");
        }
        LOGGER.info("insertaEstadoEjg() <- Saliendo del servicio que envia la documentación al CAJG si es necesario");
    }

    @Transactional
    public void updateEstadoEjg(ScsEstadoejg estado) throws Exception {
        LOGGER.info("insertaEstadoEjg() -> ");
        if (isColegioZonaComun(estado.getIdinstitucion())
                && isColegioConfiguradoEnvioPericles(estado.getIdinstitucion())) {
            if (estado.getIdestadoejg() != null && estado.getIdestadoejg().equals(SigaConstants.ESTADOS_EJG.LISTO_REMITIR_COMISION.getCodigo())) {
                LOGGER.info("insertaEstadoEjg() -> ");

                envioPericlesExpediente(estado, CASO.PERICLES_UPDATE_ESTADO);
            }
        } else {
            LOGGER.info("insertaEstadoEjg() <- ");
        }
        LOGGER.info("insertaEstadoEjg() <- ");
    }

    private void envioPericlesExpediente(ScsEstadoejg estadoEjgItem, CASO caso) throws Exception {
        if (caso.equals(CASO.PERICLES_UPDATE_ESTADO)) {
            LOGGER.info("envioPericlesExpediente() -> Entrando en Insertar intercabio en ECOM_COLA");

            ScsEstadoejgKey estadoEjgKey = new ScsEstadoejgKey();
            estadoEjgKey.setIdestadoporejg(estadoEjgItem.getIdestadoporejg());
            estadoEjgKey.setIdtipoejg(estadoEjgItem.getIdtipoejg());
            estadoEjgKey.setAnio(estadoEjgItem.getAnio());
            estadoEjgKey.setNumero(estadoEjgItem.getNumero());
            estadoEjgKey.setIdinstitucion(estadoEjgItem.getIdinstitucion());

            ScsEstadoejg estadoejg = scsEstadoejgMapper.selectByPrimaryKey(estadoEjgKey);
            if (estadoejg.getIdestadoejg() != null && estadoejg.getIdestadoejg().equals(SigaConstants.ESTADOS_EJG.LISTO_REMITIR_COMISION.getCodigo())) {
                LOGGER.info("envioPericlesExpediente() -> Entrando en Insertar intercabio en ECOM_COLA");
                return;
            }
        }

        if (caso.equals(CASO.PERICLES_REENVIA)) {
            LOGGER.info("envioPericlesExpediente() -> Entrando en Insertar intercabio en ECOM_COLA");

            ScsEstadoejg oldEstadoejgRecord = new ScsEstadoejg();
            oldEstadoejgRecord.setIdestadoporejg(estadoEjgItem.getIdestadoporejg());
            oldEstadoejgRecord.setIdtipoejg(estadoEjgItem.getIdtipoejg());
            oldEstadoejgRecord.setAnio(estadoEjgItem.getAnio());
            oldEstadoejgRecord.setNumero(estadoEjgItem.getNumero());
            oldEstadoejgRecord.setIdinstitucion(estadoEjgItem.getIdinstitucion());
            oldEstadoejgRecord.setFechabaja(new Date());

            scsEstadoejgMapper.updateByPrimaryKeySelective(oldEstadoejgRecord);
        }

        LOGGER.info("envioPericlesExpediente() -> Entrando en Insertar intercabio en ECOM_COLA");

        ScsEstadoejg newEstadoejgRecord = new ScsEstadoejg();
        newEstadoejgRecord.setIdestadoporejg(estadoEjgItem.getIdestadoporejg());
        newEstadoejgRecord.setIdtipoejg(estadoEjgItem.getIdtipoejg());
        newEstadoejgRecord.setAnio(estadoEjgItem.getAnio());
        newEstadoejgRecord.setNumero(estadoEjgItem.getNumero());
        newEstadoejgRecord.setIdinstitucion(estadoEjgItem.getIdinstitucion());
        newEstadoejgRecord.setFechainicio(new Date());
        newEstadoejgRecord.setAutomatico(SigaConstants.DB_TRUE);
        newEstadoejgRecord.setIdestadoejg(SigaConstants.ESTADOS_EJG.GENERADO_ENV_COMISION.getCodigo());
        newEstadoejgRecord.setObservaciones("Espere a que se procese el envio para continuar");

        scsEstadoejgMapper.insertSelective(newEstadoejgRecord);

        LOGGER.info("envioPericlesExpediente() -> Entrando en Insertar intercabio en ECOM_COLA");
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, estadoEjgItem.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, estadoEjgItem.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, estadoEjgItem.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, estadoEjgItem.getNumero().toString());

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(estadoEjgItem.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIA_EXPEDIENTE.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
        LOGGER.info("envioPericlesExpediente() <- Saliendo de Insertar intercabio en ECOM_COLA");

        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), "Intercambio EJG Pericles");
    }

    private void encolaEnvioDocumentacion(ScsEjg ejg) throws Exception {
        // Obtenemos la documentación
        ScsDocumentacionejgExample documentacionejgExample = new ScsDocumentacionejgExample();
        documentacionejgExample.createCriteria().andIdinstitucionEqualTo(ejg.getIdinstitucion())
                .andAnioEqualTo(ejg.getAnio()).andIdtipoejgEqualTo(ejg.getIdtipoejg())
                .andNumeroEqualTo(ejg.getNumero());

        // Obtenemos los miembros de la unidad familiar
        ScsUnidadfamiliarejgExample unidadfamiliarejgExample = new ScsUnidadfamiliarejgExample();
        unidadfamiliarejgExample.createCriteria().andIdinstitucionEqualTo(ejg.getIdinstitucion())
                .andIdtipoejgEqualTo(ejg.getIdtipoejg()).andAnioEqualTo(ejg.getAnio())
                .andNumeroEqualTo(ejg.getNumero());
        unidadfamiliarejgExample.setOrderByClause("SOLICITANTE DESC");

        List<ScsDocumentacionejg> documentacionejgList = scsDocumentacionejgMapper.selectByExample(documentacionejgExample);
        List<ScsUnidadfamiliarejg> unidadfamiliarejgList = scsUnidadfamiliarejgMapper.selectByExample(unidadfamiliarejgExample);

        for (int i = 0; i < documentacionejgList.size(); i++) {
            insercionIndividualDocumentacion(documentacionejgList.get(i), i == documentacionejgList.size() - 1);
        }

        for (int i = 0; i < unidadfamiliarejgList.size(); i++) {
            ScsUnidadfamiliarejg unidadfamiliarejg = unidadfamiliarejgList.get(i);

            ScsPersonajgKey personajgKey = new ScsPersonajgKey();
            personajgKey.setIdinstitucion(unidadfamiliarejg.getIdinstitucion());
            personajgKey.setIdpersona(unidadfamiliarejg.getIdpersona());
            ScsPersonajg persona = scsPersonajgMapper.selectByPrimaryKey(personajgKey);

            if (persona.getIdtipoidentificacion() == (short) 10 || persona.getIdtipoidentificacion() == (short) 40) {
                ScsEejgPeticionesExample eejgPeticionesExample = new ScsEejgPeticionesExample();
                eejgPeticionesExample.createCriteria().andIdinstitucionEqualTo(unidadfamiliarejg.getIdinstitucion())
                        .andAnioEqualTo(unidadfamiliarejg.getAnio()).andIdtipoejgEqualTo(unidadfamiliarejg.getIdtipoejg())
                        .andNumeroEqualTo(unidadfamiliarejg.getNumero()).andNifEqualTo(persona.getNif())
                        .andCsvIsNotNull().andEstadoNotEqualTo(40L);
                eejgPeticionesExample.setOrderByClause("IDPETICION DESC");

                List<ScsEejgPeticiones> eejgPeticiones = scsEejgPeticionesMapper.selectByExample(eejgPeticionesExample);

                if (eejgPeticiones != null && !eejgPeticiones.isEmpty()) {
                    boolean esUltimo = true;
                    for (int j = 0; j < documentacionejgList.size(); j++) {
                        if (documentacionejgList.get(j).getIdfichero() != null) {
                            esUltimo = false;
                            break;
                        }
                    }

                    insercionIndividualUnidadFamiliar(unidadfamiliarejgList.get(i), persona, esUltimo);
                }
            }
        }
    }

    private void insercionIndividualDocumentacion(ScsDocumentacionejg documentacionejg, boolean esUltimo) throws Exception {
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, documentacionejg.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, documentacionejg.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, documentacionejg.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, documentacionejg.getNumero().toString());

        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUMENTACION, documentacionejg.getIddocumentacion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO, esUltimo ? SigaConstants.DB_TRUE : SigaConstants.DB_FALSE);

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(documentacionejg.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIA_DOCUMENTO.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envio documentación(%s) Pericles", documentacionejg.getIddocumentacion()));
    }

    private void insercionIndividualUnidadFamiliar(ScsUnidadfamiliarejg unidadfamiliarejg, ScsPersonajg persona, boolean esUltimo) throws Exception {
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, unidadfamiliarejg.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, unidadfamiliarejg.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, unidadfamiliarejg.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, unidadfamiliarejg.getNumero().toString());

        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NIFNIE, persona.getNif());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO, esUltimo ? SigaConstants.DB_TRUE : SigaConstants.DB_FALSE);

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(unidadfamiliarejg.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIA_DOCUMENTO.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envio informe económico(%s) Pericles", persona.getIdtipoidentificacion()));
    }

    private void actualizarIntercambio(Long idEcomCola, SigaConstants.ECOM_ESTADOSCOLA estado, String respuesta, String descripcion) {
        LOGGER.info("actualizarIntercambio() -> Entrando a actualizar intercambio en ECOM_INTERCAMBIO");
        EcomIntercambio record = new EcomIntercambio();

        // Actualizamos los valores del log de intercambios
        record.setIdecomcola(idEcomCola);
        record.setIdestadorespuesta(estado.getId());
        record.setFecharespuesta(new Date());
        record.setRespuesta(respuesta);
        record.setDescripcion(descripcion);

        // Buscamos el intercambio por idEcomCola
        EcomIntercambioExample example = new EcomIntercambioExample();
        example.createCriteria().andIdecomcolaEqualTo(idEcomCola);

        ecomIntercambioExtendsMapper.updateByExampleSelective(record, example);
        LOGGER.info("actualizarIntercambio() <- Saliendo de actualizar intercambio en ECOM_INTERCAMBIO");
    }

    private void insertaIntercambio(Long idEcomCola, Short idInstitucion, String descripcion) {
        LOGGER.info("insertaIntercambio() -> Entrando a insertar intercambio en ECOM_INTERCAMBIO");

        EcomIntercambio record = new EcomIntercambio();
        Long newId = ecomIntercambioExtendsMapper.getNewId();
        record.setIdecomintercambio(newId); // Se obtiene un nuevo id utilizando la secuencia

        record.setIdecomcola(idEcomCola);
        record.setIdinstitucion(idInstitucion);
        record.setDescripcion(descripcion);

        ecomIntercambioExtendsMapper.insert(record);

        LOGGER.info("insertaIntercambio() <- Saliendo de insertar intercambio en ECOM_INTERCAMBIO");
    }

    public boolean validarCamposExpedienteParaPericles() {
        // Campos obligatorios:
        // -- Número de expediente
        // -- Fecha Solicitud
        // -- Tipo resolución colegio
        // -- Nombre de solicitante
        // -- Nombre de vía del solicitante (Opcional)
        // ---- Tipo de vía de la dirección del solicitante
        // ---- Número de vía del solicitante
        // ---- Código postal del solititante
        // ---- Provincia del solicitante
        // ---- Población del solititante
        // -- Nombre del familiar (Opcional)
        // ---- Primer apellido del familiar
        // ---- Parentesco del familiar
        // ---- Primer apellido del contrario
        return false;
    }

    public ScsEjg getScsEjg(Short idInstitucion, Short annio, Short idTipoEjg, Long numero) {
        ScsEjgKey ejgKey = new ScsEjgKey();
        ejgKey.setIdinstitucion(idInstitucion);
        ejgKey.setAnio(annio);
        ejgKey.setIdtipoejg(idTipoEjg);
        ejgKey.setNumero(numero);

        return scsEjgMapper.selectByPrimaryKey(ejgKey);
    }

    private String getValorParametroWithNull(Short idInstitucion, String parametro, String modulo) {
        GenParametrosKey parametrosKey = new GenParametrosKey();
        parametrosKey.setIdinstitucion(idInstitucion);
        parametrosKey.setParametro(parametro);
        parametrosKey.setModulo(modulo);

        GenParametros genParametros = genParametrosMapper.selectByPrimaryKey(parametrosKey);

        if (genParametros == null || UtilidadesString.esCadenaVacia(genParametros.getValor())) {
            return null;
        }

        return genParametros.getValor();
    }

}
