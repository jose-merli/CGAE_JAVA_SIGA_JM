package org.itcgae.siga.exea.impl;

import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeRequestDocument;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeResponseDocument;
import es.cgae.consultatramites.token.schema.UsuarioType;
import ieci.tdw.ispac.services.ws.server.*;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.DocumentacionAsistenciaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpProcedimientosExea;
import org.itcgae.siga.db.entities.ExpProcedimientosExeaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpExpedientesExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpProcedimientosExeaExtendsMapper;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.itcgae.siga.scs.services.impl.guardia.AsistenciaServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientExpedientesEXEA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExpedientesEXEAServiceImpl implements ExpedientesEXEAService {

    private final Logger LOGGER = Logger.getLogger(ExpedientesEXEAServiceImpl.class);

    @Autowired
    private ExpProcedimientosExeaExtendsMapper expProcedimientosExeaExtendsMapper;

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private ExpExpedientesExtendsMapper expExpedientesExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

    @Autowired
    private ClientExpedientesEXEA _clientExpedientesEXEA;

    @Override
    public StringDTO isEXEActivoInstitucion(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        StringDTO stringDTO = new StringDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "isEXEActivoInstitucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "isEXEActivoInstitucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    ExpProcedimientosExeaExample expProcedimientosExeaExample = new ExpProcedimientosExeaExample();
                    expProcedimientosExeaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);
                    List<ExpProcedimientosExea> procedimientosExea = expProcedimientosExeaExtendsMapper.selectByExample(expProcedimientosExeaExample);

                    //Si la institucion no tiene procedimientos de EXEA o no tiene ninguno que sea de colegiacion
                    if(procedimientosExea != null
                            && !procedimientosExea.isEmpty()
                            && procedimientosExea.stream().anyMatch(procedimiento -> 1 == procedimiento.getEsColegiacion())){
                        stringDTO.setValor(SigaConstants.DB_TRUE);
                    }else{
                        stringDTO.setValor(SigaConstants.DB_FALSE);
                    }

                }
            }
        }catch(Exception e){
            LOGGER.error("isEXEActivoInstitucion() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al consultar si la institucion tiene tramites activos con EXEA");
            error.description("Error al consultar si la institucion tiene tramites activos con EXEA");
        }
        return stringDTO;
    }

    @Override
    public ExpedienteDTO getExpedientesSIGAColegiado(HttpServletRequest request, String idPersona) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "getExpedientesSIGAColegiado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getExpedientesSIGAColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    List<ExpedienteItem> expedienteItems = expExpedientesExtendsMapper.getExpedientesSigaColegiado(idInstitucion, idPersona);
                    expedienteDTO.setExpedienteItem(expedienteItems);

                }
            }
        }catch(Exception e){
            LOGGER.error("getExpedientesSIGAColegiado() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar los expedientes del colegiado");
            error.description("Error al buscar los expedientes del colegiado");
            expedienteDTO.setError(error);
        }
        return expedienteDTO;
    }

    @Override
    public StringDTO getTokenLoginEXEA(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        StringDTO stringDTO = new StringDTO();
        Error error = new Error();
        String idAplicacion, idSede, cdgoExternoInstitucion, urlService;
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "getTokenLoginEXEA() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getTokenLoginEXEA() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    urlService = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_AUTENTICACION_URL_PARAM, idInstitucion.toString()).getValor();

                    idSede = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.ID_SEDE_PARAM, idInstitucion.toString()).getValor();

                    idAplicacion = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.ID_APLICACION_PARAM, idInstitucion.toString()).getValor();

                    cdgoExternoInstitucion = cenInstitucionExtendsMapper.selectByPrimaryKey(idInstitucion).getCodigoext();

                    if(!UtilidadesString.esCadenaVacia(idSede) && !UtilidadesString.esCadenaVacia(idAplicacion)
                        && !"NULL".equals(idSede) && !"NULL".equals(idAplicacion)){

                        AutenticarUsuarioSedeRequestDocument requestDocument = AutenticarUsuarioSedeRequestDocument.Factory.newInstance();
                        AutenticarUsuarioSedeRequestDocument.AutenticarUsuarioSedeRequest requestSede = requestDocument.addNewAutenticarUsuarioSedeRequest();
                        requestSede.setCodInstitucion(cdgoExternoInstitucion);
                        requestSede.setIdAplicacion(idAplicacion);
                        requestSede.setIdSede(idSede);
                        UsuarioType usuarioType = requestSede.addNewIdUsuario();
                        usuarioType.setDNI(dni);

                        LOGGER.info("getTokenLoginEXEA() / Request a EXEA: " + requestDocument.xmlText());

                        AutenticarUsuarioSedeResponseDocument response = _clientExpedientesEXEA.autenticarUsuarioEXEA(requestDocument,urlService);

                        if(response.getAutenticarUsuarioSedeResponse().getError() != null){
                            stringDTO.setValor("Error al logar en EXEA: " + response.getAutenticarUsuarioSedeResponse().getError().getCodError() + " " + response.getAutenticarUsuarioSedeResponse().getError().getDesError());
                        }else {
                            stringDTO.setValor("Bearer " + response.getAutenticarUsuarioSedeResponse().getToken());
                        }
                    }

                }
            }
        }catch(Exception e){
            LOGGER.error("getTokenLoginEXEA() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al obtener el token de EXEA");
            error.description("Error al obtener el token de EXEA");
        }
        return stringDTO;
    }

    @Override
    public ExpedienteDTO getExpedientesEXEAPersonalColegio(HttpServletRequest request, String identificacionColegiado) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "getExpedientesEXEAPersonalColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getExpedientesEXEAPersonalColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    BusquedaAvanzadaDocument busquedaAvanzadaDocument = BusquedaAvanzadaDocument.Factory.newInstance();
                    BusquedaAvanzadaDocument.BusquedaAvanzada busquedaAvanzada = busquedaAvanzadaDocument.addNewBusquedaAvanzada();
                    busquedaAvanzada.setDominio(0);
                    String xmlBusqueda = getXMLBusquedaAvanzada(identificacionColegiado);
                    if(!UtilidadesString.esCadenaVacia(xmlBusqueda)){
                        busquedaAvanzada.setXmlBusqueda(xmlBusqueda);
                    }

                }
            }
        }catch(Exception e){
            LOGGER.error("getExpedientesEXEAPersonalColegio() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar los expedientes de EXEA");
            error.description("Error al buscar los expedientes de EXEA");
            expedienteDTO.setError(error);
        }
        return expedienteDTO;
    }

    @Override
    public ExpedienteDTO getDetalleExpedienteEXEA(HttpServletRequest request, String numExpedienteEXEA) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        Error error = new Error();
        String urlService;
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "getDetalleExpedienteEXEA() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getDetalleExpedienteEXEA() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    if(!UtilidadesString.esCadenaVacia(numExpedienteEXEA)) {
                        urlService = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_WEBSERVICES_ADDIN_PARAM, idInstitucion.toString()).getValor();
                        GetExpedienteDocument getExpedienteDocument = GetExpedienteDocument.Factory.newInstance();
                        GetExpedienteDocument.GetExpediente getExpediente = getExpedienteDocument.addNewGetExpediente();
                        getExpediente.setIdExp(numExpedienteEXEA);

                        GetExpedienteResponseDocument responseDocument = _clientExpedientesEXEA.getDetalleExpedienteEXEA(getExpedienteDocument, urlService);

                        if(responseDocument != null && responseDocument.getGetExpedienteResponse() != null){
                            ExpedienteItem expedienteItem = fillExpedienteItem(responseDocument.getGetExpedienteResponse().getGetExpedienteReturn());
                            expedienteDTO.setExpedienteItem(Arrays.asList(expedienteItem));
                        }else{
                            error.setCode(500);
                            error.setDescription("Error al buscar el detalle del expediente");
                            error.setMessage("Error al buscar el detalle del expediente");
                            expedienteDTO.setError(error);
                        }
                    }else{
                        error.setCode(500);
                        error.setDescription("Identificador del expediente no informado");
                        error.setMessage("Identificador del expediente no informado");
                        expedienteDTO.setError(error);
                    }

                }
            }
        }catch(Exception e){
            LOGGER.error("getDetalleExpedienteEXEA() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar el detalle del expediente " + numExpedienteEXEA);
            error.description("Error al buscar el detalle del expediente " + numExpedienteEXEA);
            expedienteDTO.setError(error);
        }
        return expedienteDTO;
    }

    private ExpedienteItem fillExpedienteItem(Expediente expediente){
        ExpedienteItem expedienteItem = new ExpedienteItem();
        List<DocumentacionAsistenciaItem> documentacionTotal = new ArrayList<>();
        expedienteItem.setNumExpediente(expediente.getInformacionBasica().getNumExp());
        expedienteItem.setTipoExpediente(expediente.getAsunto());
        expedienteItem.setFechaApertura(SigaConstants.DATE_FORMAT_MIN.format(expediente.getFechaInicio().getTime()));
        expedienteItem.setDescInstitucion(expediente.getNombreOrgProductor());

        if(expediente.getDocumentosFisicos() != null
                && expediente.getDocumentosFisicos().sizeOfItemArray() > 0){

            for (int i = 0; i < expediente.getDocumentosFisicos().sizeOfItemArray(); i++) {

                DocFisico docFisico = expediente.getDocumentosFisicos().getItemArray(i);

                DocumentacionAsistenciaItem documentacion = new DocumentacionAsistenciaItem();
                documentacion.setNombreFichero(docFisico.getAsunto());
                documentacion.setDescTipoDoc(docFisico.getTipoDocumento());
                documentacionTotal.add(documentacion);
            }

        }

        if(expediente.getDocumentosElectronicos() != null
                && expediente.getDocumentosElectronicos().sizeOfItemArray() > 0){

            for (int i = 0; i < expediente.getDocumentosElectronicos().sizeOfItemArray(); i++) {

                DocElectronico docElectronico = expediente.getDocumentosElectronicos().getItemArray(i);

                DocumentacionAsistenciaItem documentacion = new DocumentacionAsistenciaItem();
                documentacion.setNombreFichero(docElectronico.getAsunto());
                documentacion.setDescTipoDoc(docElectronico.getTipoDocumento());
                documentacionTotal.add(documentacion);
            }

        }

        expedienteItem.setDocumentos(documentacionTotal);

        return expedienteItem;
    }

    private String getXMLBusquedaAvanzada(String dniColegiado) throws ParserConfigurationException, TransformerException {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("search");
        doc.appendChild(rootElement);

        Element entityElement = doc.createElement("entity");
        rootElement.appendChild(entityElement);

        Element nifFieldElement = doc.createElement("field");
        entityElement.appendChild(nifFieldElement);

        Element nameNifFieldElement = doc.createElement("name");
        nameNifFieldElement.appendChild(doc.createTextNode("NIFCIFTITULAR"));
        nifFieldElement.appendChild(nameNifFieldElement);

        Element operatorNifFieldElement = doc.createElement("operator");
        operatorNifFieldElement.appendChild(doc.createTextNode("="));
        nifFieldElement.appendChild(operatorNifFieldElement);

        Element valueNifFieldElement = doc.createElement("value");
        valueNifFieldElement.appendChild(doc.createTextNode(dniColegiado));
        nifFieldElement.appendChild(valueNifFieldElement);

        StringWriter writer = new StringWriter();

        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        return writer.getBuffer().toString();
    }

}
