package org.itcgae.siga.scs.services.impl.ejg;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomIntercambio;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
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
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.ecom.mappers.EcomIntercambioExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDocumentacionEjgExtendsMapper;
import org.itcgae.siga.scs.services.impl.facturacionsjcs.FacturacionSJCSHelper;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ScsDocumentacionEjgExtendsMapper scsDocumentacionEjgExtendsMapper;

    @Autowired
    private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;

    @Autowired
    private ScsContrariosejgMapper scsContrariosejgMapper;

    @Autowired
    private DocushareHelper docushareHelper;

    @Autowired
    private ScsEejgPeticionesMapper scsEejgPeticionesMapper;

    @Autowired
    private EcomIntercambioExtendsMapper ecomIntercambioExtendsMapper;

    @Autowired
    private GenParametrosMapper genParametrosMapper;

    private static final Short[] EXCEPCIONES_ZONA_COMUN = new Short[] {Short.valueOf("2055"), Short.valueOf("2032")};


    public boolean envioPericlesDisponible(Short idInstitucion) {
        return (isColegioZonaComun(idInstitucion) || Arrays.asList(EXCEPCIONES_ZONA_COMUN).contains(idInstitucion))
                && isColegioConfiguradoEnvioPericles(idInstitucion);
    }

    public boolean isColegioZonaComun(Short idInstitucion) {
        List<CenInstitucion> zonaComun = cenInstitucionExtendsMapper.getInstitucionByGrupo(idInstitucion, SigaConstants.GRUPOINSTITUCION.COMUN_MINI.getCodigoGrupo());
        return !zonaComun.isEmpty();
    }

    private boolean isColegioConfiguradoEnvioPericles(Short idInstitucion) {
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
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), "Consulta Estado EJG");
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
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envío documentación EJG - %s", documentacionejg.getIddocumentacion()));
    }

    @Transactional
    public void insertarDocumentacionExpedienteEconomicoAdicionalEnCola(ScsEejgPeticiones eejgPeticiones) throws Exception {
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, eejgPeticiones.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, eejgPeticiones.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, eejgPeticiones.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, eejgPeticiones.getNumero().toString());

        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NIFNIE, eejgPeticiones.getNif());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO, SigaConstants.DB_TRUE);

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(eejgPeticiones.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIA_DOCUMENTO.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envío documentación económica - %s", eejgPeticiones.getNif()));
    }

    @Transactional
    public void insertarDocumentacionRegtelAdicionalEnCola(ScsEjg ejg, String identificadords) throws Exception {
        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, ejg.toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, ejg.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, ejg.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, ejg.getNumero().toString());

        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUSHARE, identificadords);

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(ejg.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIO_COMUNICACION.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envío documentación Regtel - %s", identificadords));
    }


    enum CASO {
        PERICLES_INSERT_ESTADO, PERICLES_UPDATE_ESTADO, PERICLES_REENVIA
    }

    public void insertaCambioEstadoPericles(ScsEstadoejg estado, AdmUsuarios usuario) throws Exception {
        LOGGER.info("insertaEstadoEjg() -> Entrando al servicio que envia la documentación al CAJG si es necesario");
        if (isColegioZonaComun(estado.getIdinstitucion()) && envioPericlesDisponible(estado.getIdinstitucion())) {
            ScsEjg ejg = getScsEjg(estado.getIdinstitucion(), estado.getAnio(), estado.getIdtipoejg(), estado.getNumero());
            if (UtilidadesString.esCadenaVacia(ejg.getNumeroCajg())) {
                envioPericlesExpediente(estado, usuario, EJGIntercambiosHelper.CASO.PERICLES_INSERT_ESTADO);
            } else {
                encolaEnvioDocumentacion(ejg);
            }
        } else {
            LOGGER.info("insertaEstadoEjg() <- El colegio no pertenece a la zona común");
        }
        LOGGER.info("insertaEstadoEjg() <- Saliendo del servicio que envia la documentación al CAJG si es necesario");
    }


    private void envioPericlesExpediente(ScsEstadoejg estadoEjgItem, AdmUsuarios usuario, CASO caso) throws Exception {
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
        newEstadoejgRecord.setIdestadoporejg(estadoEjgItem.getIdestadoporejg() + 1);
        newEstadoejgRecord.setIdtipoejg(estadoEjgItem.getIdtipoejg());
        newEstadoejgRecord.setAnio(estadoEjgItem.getAnio());
        newEstadoejgRecord.setNumero(estadoEjgItem.getNumero());
        newEstadoejgRecord.setIdinstitucion(estadoEjgItem.getIdinstitucion());
        newEstadoejgRecord.setFechainicio(new Date());
        newEstadoejgRecord.setAutomatico(SigaConstants.DB_TRUE);
        newEstadoejgRecord.setIdestadoejg(SigaConstants.ESTADOS_EJG.GENERADO_ENV_COMISION.getCodigo());
        newEstadoejgRecord.setObservaciones("Espere a que se procese el envio para continuar");
        newEstadoejgRecord.setFechamodificacion(new Date());
        newEstadoejgRecord.setUsumodificacion(usuario.getIdusuario());

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
        LOGGER.info("encolaEnvioDocumentacion() -> Entrando al servicio que encola el envío de todas las documentaciones");

        // --> Envío de Expedientes Económicos
        List<Map<String, String>> listasParametrosExpEcon = insercionDocumentacionExpedienteEconomico(ejg);

        // --> Envío de Documentación EJG
        List<Map<String, String>> listasParametrosEjg = insercionDocumentacionEJG(ejg);

        // --> Envío de Regtel
        List<Map<String, String>> listasParametrosRegtel = new ArrayList<>();
        try {
            listasParametrosRegtel = insercionDocumentacionRegtel(ejg);
        } catch (Exception e) {
            LOGGER.error("encolaEnvioDocumentacion() -> Error al obtener los documentos de Regtel");
        }

        List<Map<String, String>> listasParametros = new ArrayList<>();
        listasParametros.addAll(listasParametrosExpEcon);
        listasParametros.addAll(listasParametrosEjg);
        listasParametros.addAll(listasParametrosRegtel);

        insercionEnEnviosPericles(listasParametros);

        LOGGER.info("encolaEnvioDocumentacion() <- Saliendo al servicio que encola el envío de todas las documentaciones");
    }

    private List<Map<String, String>> insercionDocumentacionEJG(ScsEjg ejg) {
        LOGGER.info("insercionDocumentacionEJG() -> Entrando al servicio que envía la documentación EJG");
        List<Map<String, String>> listaParametros = new ArrayList<>();

        // Obtenemos las documentaciones
        List<ScsDocumentacionejg> documentacionejgList = scsDocumentacionEjgExtendsMapper.getDocumentacionParaEnviarPericles(ejg.getIdinstitucion(),
                ejg.getIdtipoejg(), ejg.getAnio(), ejg.getNumero(), false);


        // --> Envío de documentación
        for (int i = 0; i < documentacionejgList.size(); i++) {
            ScsDocumentacionejg documentacionejg = documentacionejgList.get(i);

            Map<String, String> parametrosCola = new HashMap<>();
            parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, documentacionejg.getIdinstitucion().toString());
            parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, documentacionejg.getAnio().toString());
            parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, documentacionejg.getIdtipoejg().toString());
            parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, documentacionejg.getNumero().toString());

            parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUMENTACION, documentacionejg.getIddocumentacion().toString());
            listaParametros.add(parametrosCola);
        }

        LOGGER.info("insercionDocumentacionEJG() <- Saliendo del servicio que envía la documentación EJG");
        return listaParametros;
    }

    private List<Map<String, String>> insercionDocumentacionExpedienteEconomico(ScsEjg ejg) {
        LOGGER.info("insercionDocumentacionExpedienteEconomico() -> Entrando al servicio que envía la documentación de expedientes económicos");
        List<Map<String, String>> listaParametros = new ArrayList<>();

        // --> Envio de informes económicos
        ScsUnidadfamiliarejgExample unidadfamiliarejgExample = new ScsUnidadfamiliarejgExample();
        unidadfamiliarejgExample.createCriteria().andIdinstitucionEqualTo(ejg.getIdinstitucion())
                .andIdtipoejgEqualTo(ejg.getIdtipoejg()).andAnioEqualTo(ejg.getAnio())
                .andNumeroEqualTo(ejg.getNumero());
        unidadfamiliarejgExample.setOrderByClause("SOLICITANTE DESC");

        List<ScsUnidadfamiliarejg> unidadfamiliarejgList = scsUnidadfamiliarejgMapper.selectByExample(unidadfamiliarejgExample);
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
                    Map<String, String> parametrosCola = new HashMap<>();
                    parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, unidadfamiliarejg.getIdinstitucion().toString());
                    parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, unidadfamiliarejg.getAnio().toString());
                    parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, unidadfamiliarejg.getIdtipoejg().toString());
                    parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, unidadfamiliarejg.getNumero().toString());

                    parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NIFNIE, persona.getNif());

                    listaParametros.add(parametrosCola);
                }
            }

        }

        LOGGER.info("insercionDocumentacionExpedienteEconomico() <- Saliendo del servicio que envía la documentación de expedientes económicos");
        return listaParametros;
    }

    private List<Map<String, String>> insercionDocumentacionRegtel(ScsEjg ejg) throws Exception {
        LOGGER.info("insercionDocumentacionRegtel() -> Entrando al servicio que envía la documentación de Regtel");
        List<Map<String, String>> listaParametros = new ArrayList<>();

        if (!UtilidadesString.esCadenaVacia(ejg.getIdentificadords())) {
            String paramRegtel = getValorParametroWithNull(ejg.getIdinstitucion(), "PATH_DOCUSHARE_EJG", SigaConstants.MODULO_GEN);
            if (paramRegtel != null && paramRegtel.equalsIgnoreCase(SigaConstants.DB_TRUE)) {
                paramRegtel = getValorParametroWithNull(ejg.getIdinstitucion(), "PCJAG_ENVIO_REGTEL_EJG", SigaConstants.MODULO_ECOM);
                if (paramRegtel != null && paramRegtel.equalsIgnoreCase(SigaConstants.DB_TRUE)) {
                    List<DocuShareObjectVO> datosDocuShare = docushareHelper.getContenidoCollection(ejg.getIdinstitucion(), ejg.getIdentificadords(), ejg.getIdentificadords());
                    for (DocuShareObjectVO docushare: datosDocuShare) {
                        Map<String, String> parametrosCola = new HashMap<>();
                        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, ejg.toString());
                        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, ejg.getAnio().toString());
                        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, ejg.getIdtipoejg().toString());
                        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, ejg.getNumero().toString());

                        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUSHARE, docushare.getId());

                        listaParametros.add(parametrosCola);
                    }
                }
            }
        }

        LOGGER.info("insercionDocumentacionRegtel() <- Saliendo del servicio que envía la documentación de Regtel");
        return listaParametros;
    }

    private void insercionEnEnviosPericles(List<Map<String, String>> listaParametros) throws Exception {
        for (int i = 0; i < listaParametros.size(); i++) {
            Map<String, String> parametros = listaParametros.get(i);

            parametros.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO, i == listaParametros.size() - 1 ? SigaConstants.DB_TRUE : SigaConstants.DB_FALSE);

            EcomCola colaEnviaPericles = new EcomCola();
            colaEnviaPericles.setIdinstitucion(Short.parseShort(parametros.get(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION)));
            colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIA_DOCUMENTO.getId());

            String documento = "", identificador = "";
            if (parametros.get(SigaConstants.PERICLES_PARAM_ECOMCOLA_NIFNIE) != null) {
                documento = "económica";
            } else if (parametros.get(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUMENTACION) != null) {
                documento = "EJG";
            } else if (parametros.get(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUSHARE) != null) {
                documento = "Regtel";
            }

            facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametros);
            insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envío documentación %s - %s", documento, identificador));
        }
    }

    private void insertaIntercambio(Long idEcomCola, Short idInstitucion, String descripcion) {
        LOGGER.info("insertaIntercambio() -> Entrando a insertar intercambio en ECOM_INTERCAMBIO");

        EcomIntercambio record = new EcomIntercambio();
        Long newId = ecomIntercambioExtendsMapper.getNewId();
        record.setIdecomintercambio(newId); // Se obtiene un nuevo id utilizando la secuencia

        record.setIdecomcola(idEcomCola);
        record.setIdinstitucion(idInstitucion);
        record.setDescripcion(descripcion);

        record.setFechaenvio(new Date());

        ecomIntercambioExtendsMapper.insert(record);

        LOGGER.info("insertaIntercambio() <- Saliendo de insertar intercambio en ECOM_INTERCAMBIO");
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
