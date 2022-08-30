package org.itcgae.siga.scs.services.impl.ejg;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosDTO;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomIntercambio;
import org.itcgae.siga.db.entities.EcomIntercambioExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.ecom.mappers.EcomIntercambioExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IEJGIntercambiosService;
import org.itcgae.siga.scs.services.impl.facturacionsjcs.FacturacionSJCSHelper;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EJGIntercambiosServiceServiceImpl implements IEJGIntercambiosService {

    private Logger LOGGER = Logger.getLogger(EJGIntercambiosServiceServiceImpl.class);

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    private EcomIntercambioExtendsMapper ecomIntercambioExtendsMapper;

    @Autowired
    private FacturacionSJCSHelper facturacionSJCSHelper;

    @Autowired
    private ScsEjgMapper scsEjgMapper;

    @Autowired
    private ScsEstadoejgMapper scsEstadoejgMapper;

    @Autowired
    private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

    @Autowired
    private GenParametrosMapper genParametrosMapper;

    @Override
    public EjgListaIntercambiosDTO getListadoIntercambiosAltaEJG(EjgItem item, HttpServletRequest request) throws Exception {
        LOGGER.info("getListadoIntercambiosAltaEJG() -> Entrando al servicio para obtener el listado de intercambio de Alta EJG");

        EjgListaIntercambiosDTO ejgListaIntercambiosDTO = new EjgListaIntercambiosDTO();

        // Conseguimos información del usuario logeado
        LOGGER.info("getListadoIntercambiosAltaEJG() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        if (!UtilidadesString.anyMatchCadenaVacia(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero())) {
            LOGGER.info("getListadoIntercambiosAltaEJG() -> Entrando al servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosAltaEJG()");
            List<EjgListaIntercambiosItem> intercambios = ecomIntercambioExtendsMapper.getListadoIntercambiosAltaEJG(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero());
            ejgListaIntercambiosDTO.setEjgListaIntercambiosItems(intercambios);
            LOGGER.info("getListadoIntercambiosAltaEJG() -> Saliendo del servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosAltaEJG()");
        } else {
            LOGGER.warn("getListadoIntercambiosAltaEJG() -> Error: Alguno de los parámetros no es correcto");
            throw new Exception("Faltan parametros para buscar la lista de intercambios");
        }

        LOGGER.info("getListadoIntercambiosAltaEJG() -> Saliendo del servicio para obtener el listado de intercambio de Alta EJG");
        return ejgListaIntercambiosDTO;
    }

    @Override
    public EjgListaIntercambiosDTO getListadoIntercambiosDocumentacionEJG(EjgItem item, HttpServletRequest request) throws Exception {
        LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Entrando al servicio para obtener el listado de intercambio de Documentación EJG");

        EjgListaIntercambiosDTO ejgListaIntercambiosDTO = new EjgListaIntercambiosDTO();

        // Conseguimos información del usuario logeado
        LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

        if (!UtilidadesString.anyMatchCadenaVacia(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero())) {
            LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Entrando al servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosDocumentacionEJG()");
            List<EjgListaIntercambiosItem> intercambios = ecomIntercambioExtendsMapper.getListadoIntercambiosDocumentacionEJG(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero());
            ejgListaIntercambiosDTO.setEjgListaIntercambiosItems(intercambios);
            LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Saliendo del servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosDocumentacionEJG()");
        } else {
            LOGGER.warn("getListadoIntercambiosDocumentacionEJG() -> Error: Alguno de los parámetros no es correcto");
            throw new Exception("Faltan parametros para buscar la lista de intercambios");
        }

        LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Saliendo del servicio para obtener el listado de intercambio de Documentación EJG");
        return ejgListaIntercambiosDTO;
    }

    public void insertaEstadoEjg(ScsEstadoejg estado, String idioma) throws Exception {
        LOGGER.info("insertaEstadoEjg() -> ");
        if (isColegioZonaComun(estado.getIdinstitucion()) && isColegioConfiguradoEnvioPericles(estado.getIdinstitucion())) {
            if (estado.getIdestadoejg() != null && estado.getIdestadoejg().equals(SigaConstants.ESTADOS_EJG.LISTO_REMITIR_COMISION.getCodigo())) {
                LOGGER.info("insertaEstadoEjg() -> ");
                ScsEjgKey ejgKey = new ScsEjgKey();
                ejgKey.setIdtipoejg(estado.getIdtipoejg());
                ejgKey.setAnio(estado.getAnio());
                ejgKey.setNumero(estado.getNumero());
                ejgKey.setIdinstitucion(estado.getIdinstitucion());

                ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
                if (UtilidadesString.esCadenaVacia(ejg.getNumeroCajg())) {
                    LOGGER.info("insertaEstadoEjg() -> ");
                    envioPericlesExpediente(estado, CASO.PERICLES_INSERT_ESTADO);
                } else {
                    LOGGER.info("insertaEstadoEjg() -> ");
                    // Encola Envio Documentación (Por hacer)
                }
            }
        } else {
            LOGGER.info("insertaEstadoEjg() -> ");
        }
        LOGGER.info("insertaEstadoEjg() -> ");
    }

    enum CASO {
        PERICLES_INSERT_ESTADO, PERICLES_UPDATE_ESTADO, PERICLES_REENVIA
    }

    public void envioPericlesExpediente(ScsEstadoejg estadoEjgItem, CASO caso) throws Exception {
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

    /*
    public void envioPericlesDocumentacion() throws Exception {
        // Actualizar observaciones de documentación

        Map<String, String> parametrosCola = new HashMap<>();
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDINSTITUCION, estadoEjgItem.getIdinstitucion().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ANIO, estadoEjgItem.getAnio().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDTIPOEJG, estadoEjgItem.getIdtipoejg().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_NUMERO, estadoEjgItem.getNumero().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_IDDOCUMENTACION, estadoEjgItem.getNumero().toString());
        parametrosCola.put(SigaConstants.PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO, SigaConstants.DB_TRUE);

        EcomCola colaEnviaPericles = new EcomCola();
        colaEnviaPericles.setIdinstitucion(estadoEjgItem.getIdinstitucion());
        colaEnviaPericles.setIdoperacion(SigaConstants.OPERACION.PERICLES_ENVIA_DOCUMENTO.getId());

        facturacionSJCSHelper.insertaColaConParametros(colaEnviaPericles, parametrosCola);
        insertaIntercambio(colaEnviaPericles.getIdecomcola(), colaEnviaPericles.getIdinstitucion(), String.format("Envio documentación(%s) Pericles", ""));
    }

     */

    public void getEJGPericles(Short idInstitucion, Short anio, Short idTipoEjg, Long numero) {
        LOGGER.info("getEJGPericles() -> ");

        // Obtenemos los datos del EJG

        // Obtenemos los datos de la designación vigente hoy

        // Obtenemos los datos del abogado designado

        // Para el procurador miramos si tene el EJG, si no tiene miraremos el último de la designación

        // Obtenemos los miembros de la unidad familiar

        // Obtenemos los contrarios (del ejg y si no tiene de la designación)


        LOGGER.info("getEJGPericles() <- ");
    }


    public void actualizarIntercambio(Long idEcomCola, SigaConstants.ECOM_ESTADOSCOLA estado, String respuesta, String descripcion) {
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
    }

    public void insertaIntercambio(Long idEcomCola, Short idInstitucion, String descripcion) {
        LOGGER.info("");

        EcomIntercambio record = new EcomIntercambio();
        Long newId = ecomIntercambioExtendsMapper.getNewId();
        record.setIdecomintercambio(newId); // Se obtiene un nuevo id utilizando la secuencia

        record.setIdecomcola(idEcomCola);
        record.setIdinstitucion(idInstitucion);
        record.setDescripcion(descripcion);

        ecomIntercambioExtendsMapper.insert(record);

        LOGGER.info("");
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

    public void consultarEstadoPericles() {

    }

    public void envioDocumentacionAdicional() {

    }


    private static String PCAJG_WS_URL = "PCAJG_WS_URL";
    private static String MODULO_SCS = "SCS";

    public boolean isColegioZonaComun(Short idInstitucion) {
        List<CenInstitucion> cenInstitucion = cenInstitucionExtendsMapper.getInstitucionByGrupo(idInstitucion, SigaConstants.GRUPOINSTITUCION.COMUN_MINI.getCodigoGrupo());
        return !cenInstitucion.isEmpty();
    }

    public boolean isColegioConfiguradoEnvioPericles(Short idInstitucion) {
        return getValorParametroWithNull(idInstitucion, PCAJG_WS_URL, MODULO_SCS) != null;
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
