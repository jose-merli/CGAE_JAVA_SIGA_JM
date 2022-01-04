package org.itcgae.siga.exea.impl;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeRequestDocument;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeResponseDocument;
import es.cgae.consultatramites.token.schema.UsuarioType;
import ieci.tdw.ispac.services.ws.server.*;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionAsistenciaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.CenDocumentacionsolicitudMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDocumentacionsolicitudExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpExpedientesExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpProcedimientosExeaExtendsMapper;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientExpedientesEXEA;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Autowired
    private CenDocumentacionsolicitudExtendsMapper cenDocumentacionsolicitudExtendsMapper;

    @Autowired
    private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

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
        String descInstitucion;
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                descInstitucion = cenInstitucionExtendsMapper.selectByPrimaryKey(idInstitucion).getNombre();

                LOGGER.info(
                        "getExpedientesEXEAPersonalColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getExpedientesEXEAPersonalColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    BusquedaAvanzadaDocument busquedaAvanzadaDocument = BusquedaAvanzadaDocument.Factory.newInstance();
                    BusquedaAvanzadaDocument.BusquedaAvanzada busquedaAvanzada = busquedaAvanzadaDocument.addNewBusquedaAvanzada();
                    busquedaAvanzada.setNombreGrupo("ICA Cantabria");
                    busquedaAvanzada.setNombreFrmBusqueda("BÚSQUEDA");
                    busquedaAvanzada.setDominio(1);
                    String xmlBusqueda = getXMLBusquedaAvanzada(identificacionColegiado);
                    if(!UtilidadesString.esCadenaVacia(xmlBusqueda)){
                        busquedaAvanzada.setXmlBusqueda(xmlBusqueda);
                    }
                    String urlService = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_WEBSERVICES_ADDIN_PARAM, idInstitucion.toString()).getValor();

                    if(!UtilidadesString.esCadenaVacia(urlService)) {
                        BusquedaAvanzadaResponseDocument response = _clientExpedientesEXEA.getExpedientesEXEAPersonalColegio(busquedaAvanzadaDocument, urlService);
                        List<ExpedienteItem> expedienteItems = getExpedienteItemFromXML(response.getBusquedaAvanzadaResponse());
                        expedienteItems.forEach(expedienteItem -> {expedienteItem.setDescInstitucion(descInstitucion);});
                        expedienteDTO.setExpedienteItem(expedienteItems);
                    }else{
                        error.setCode(500);
                        error.setMessage("No se encontro la url para obtener los expedientes");
                        error.setDescription("No se encontro la url para obtener los expedientes");
                        expedienteDTO.setError(error);
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

    @Override
    public StringDTO getParamsDocumentacionEXEA(HttpServletRequest request) {
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
                        "getParamsDocumentacionEXEA() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getParamsDocumentacionEXEA() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    String numProcedimiento, codExtInstitucion;
                    ExpProcedimientosExeaExample expProcedimientosExeaExample = new ExpProcedimientosExeaExample();
                    expProcedimientosExeaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);
                    List<ExpProcedimientosExea> procedimientosExea = expProcedimientosExeaExtendsMapper.selectByExample(expProcedimientosExeaExample);

                    if(procedimientosExea != null
                            && !procedimientosExea.isEmpty()
                            && procedimientosExea.stream().anyMatch(procedimiento -> 1 == procedimiento.getEsColegiacion())){
                        numProcedimiento = procedimientosExea.stream().filter(procedimiento -> 1 == procedimiento.getEsColegiacion()).collect(Collectors.toList()).get(0).getCodPcdExea();

                        CenInstitucion institucion = cenInstitucionExtendsMapper.selectByPrimaryKey(idInstitucion);

                        if(institucion != null){
                            codExtInstitucion = institucion.getCodigoext();
                            stringDTO.setValor(codExtInstitucion + "/" + numProcedimiento);
                        }else{
                            stringDTO.setValor("Error, no se ha encontrado ninguna institucion");
                        }
                    }else{
                        stringDTO.setValor("Error, no hay ningun procedimiento de EXEA de colegiacion");
                    }
                }
            }
        }catch(Exception e){
            LOGGER.error("getParamsDocumentacionEXEA() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al consultar los parametros para consultar la documentacion requerida en EXEA");
            error.description("Error al consultar los parametros para consultar la documentacion requerida en EXEA");
        }
        return stringDTO;
    }

    @Override
    public InsertResponseDTO sincronizarDocumentacionEXEA(HttpServletRequest request, List<DocumentacionIncorporacionItem> documentacionEXEA) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "sincronizarDocumentacionEXEA() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "sincronizarDocumentacionEXEA() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0
                && documentacionEXEA != null && !documentacionEXEA.isEmpty()) {

                    for (DocumentacionIncorporacionItem documento : documentacionEXEA) {

                        CenDocumentacionsolicitudExample cenDocumentacionsolicitudExample = new CenDocumentacionsolicitudExample();
                        cenDocumentacionsolicitudExample.createCriteria().andCodigoextEqualTo(documento.getCodDocEXEA());

                        List<CenDocumentacionsolicitud> documentacionSolicitud = cenDocumentacionsolicitudExtendsMapper.selectByExample(cenDocumentacionsolicitudExample);

                        if(documentacionSolicitud != null && !documentacionSolicitud.isEmpty()){
                            CenDocumentacionsolicitud documentoSIGA = documentacionSolicitud.get(0);
                            GenRecursosCatalogosKey key = new GenRecursosCatalogosKey();
                            key.setIdrecurso(documentoSIGA.getDescripcion());
                            key.setIdlenguaje("1");

                            GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosExtendsMapper.selectByPrimaryKey(key);

                            if(genRecursosCatalogos != null && !genRecursosCatalogos.getDescripcion().equals(documento.getDocumento())){
                                //Actualizamos descripcion si no coincide
                                genRecursosCatalogos.setDescripcion(documento.getDocumento());
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);

                                //Actualizamos para los demas lenguajes
                                genRecursosCatalogos.setIdlenguaje("2");
                                genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#CA");
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);

                                genRecursosCatalogos.setIdlenguaje("3");
                                genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#EU");
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);

                                genRecursosCatalogos.setIdlenguaje("4");
                                genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#GL");
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);

                            }

                            //TODO actualizar obligatorio y observaciones

                        }else{
                            //select max id gen recursos catalogos
                            NewIdDTO newIdDTO = genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion.toString(),usuarios.get(0).getIdlenguaje());
                            GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
                            if(newIdDTO == null){
                                genRecursosCatalogos.setIdrecurso("1");
                            }else {
                                long idRecurso = Long.valueOf(newIdDTO.getNewId()) + 1;
                                genRecursosCatalogos.setIdrecurso(String.valueOf(idRecurso));
                            }
                            //4 inserts gen recursos catalogos
                            genRecursosCatalogos.setNombretabla("CEN_DOCUMENTACIONSOLICITUD");
                            genRecursosCatalogos.setCampotabla("DESCRIPCION");
                            genRecursosCatalogos.setIdlenguaje("1");
                            genRecursosCatalogos.setFechamodificacion(new Date());
                            genRecursosCatalogos.setDescripcion(documento.getDocumento());
                            genRecursosCatalogos.setIdrecursoalias("cen.documentacionsolicitud."+documento.getCodDocEXEA());
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);

                            genRecursosCatalogos.setIdlenguaje("2");
                            genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#CA");
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);

                            genRecursosCatalogos.setIdlenguaje("3");
                            genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#EU");
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);

                            genRecursosCatalogos.setIdlenguaje("4");
                            genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#GL");
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);

                            //Select max id cen documentacion solicitud
                            NewIdDTO nextID = cenDocumentacionsolicitudExtendsMapper.getNextId();
                            CenDocumentacionsolicitud cenDocumentacionsolicitud = new CenDocumentacionsolicitud();
                            if(nextID == null){
                                cenDocumentacionsolicitud.setIddocumentacion((short)1);
                            }else{
                                cenDocumentacionsolicitud.setIddocumentacion(Short.valueOf(nextID.getNewId()));
                            }
                            cenDocumentacionsolicitud.setDescripcion(genRecursosCatalogos.getIdrecurso());
                            cenDocumentacionsolicitud.setFechamodificacion(new Date());
                            cenDocumentacionsolicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
                            cenDocumentacionsolicitud.setCodigoext(documento.getCodDocEXEA());
                            //TODO sets obligatorio y observaciones

                            //Insert en cen documentacion solicitud
                            cenDocumentacionsolicitudExtendsMapper.insert(cenDocumentacionsolicitud);

                            //Insert en cen document solicitud institu
                        }

                    }

                }
            }
        }catch(Exception e){
            LOGGER.error("sincronizarDocumentacionEXEA() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al sincronizar la documentacion de SIGA con la de EXEA");
            error.description("Error al sincronizar la documentacion de SIGA con la de EXEA");
            insertResponseDTO.setError(error);
            insertResponseDTO.setStatus(SigaConstants.KO);
        }
        return insertResponseDTO;
    }

    private ExpedienteItem fillExpedienteItem(Expediente expediente) throws ParserConfigurationException, IOException, SAXException {
        ExpedienteItem expedienteItem = new ExpedienteItem();
        List<DocumentacionAsistenciaItem> documentacionTotal = new ArrayList<>();
        expedienteItem.setNumExpediente(expediente.getInformacionBasica().getNumExp());
        expedienteItem.setTipoExpediente(expediente.getAsunto());
        expedienteItem.setFechaApertura(SigaConstants.DATE_FORMAT_MIN.format(expediente.getFechaInicio().getTime()));
        expedienteItem.setDescInstitucion(expediente.getNombreOrgProductor());

        if(expediente.getDocumentosFisicos() != null
                && !expediente.getDocumentosFisicos().isNil()){

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(expediente.getDocumentosFisicos().xmlText())));
            NodeList docFisicos = doc.getElementsByTagName("ser:documentosFisicos");
            for (int i = 0; i < docFisicos.getLength(); i++) {

                Element docFisico = (Element) docFisicos.item(i);
                DocumentacionAsistenciaItem documentacionAsistenciaItem = new DocumentacionAsistenciaItem();

                if(docFisico != null && docFisico.getChildNodes() != null && docFisico.getChildNodes().getLength() > 0){
                    for (int j = 0; j < docFisico.getChildNodes().getLength(); j++) {
                        switch (docFisico.getChildNodes().item(j).getNodeName()){
                            case "ser:asunto":
                                documentacionAsistenciaItem.setNombreFichero(docFisico.getChildNodes().item(j).getFirstChild().getNodeValue());
                                break;
                            case "ser:tipoDocumento":
                                documentacionAsistenciaItem.setDescTipoDoc(docFisico.getChildNodes().item(j).getFirstChild().getNodeValue());
                                break;
                        }
                    }
                }
                documentacionTotal.add(documentacionAsistenciaItem);
            }

        }

        if(expediente.getDocumentosElectronicos() != null
                && !expediente.getDocumentosElectronicos().isNil()){

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(expediente.getDocumentosFisicos().xmlText())));
            NodeList docFisicos = doc.getElementsByTagName("ser:documentosElectronicos");
            for (int i = 0; i < docFisicos.getLength(); i++) {

                Element docElectronico = (Element) docFisicos.item(i);
                DocumentacionAsistenciaItem documentacionAsistenciaItem = new DocumentacionAsistenciaItem();

                if(docElectronico != null && docElectronico.getChildNodes() != null && docElectronico.getChildNodes().getLength() > 0){
                    for (int j = 0; j < docElectronico.getChildNodes().getLength(); j++) {
                        switch (docElectronico.getChildNodes().item(j).getNodeName()){
                            case "ser:asunto":
                                documentacionAsistenciaItem.setNombreFichero(docElectronico.getChildNodes().item(j).getFirstChild().getNodeValue());
                                break;
                            case "ser:tipoDocumento":
                                documentacionAsistenciaItem.setDescTipoDoc(docElectronico.getChildNodes().item(j).getFirstChild().getNodeValue());
                                break;
                        }
                    }
                }
                documentacionTotal.add(documentacionAsistenciaItem);
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
        entityElement.setAttribute("name","SPAC_EXPEDIENTES");
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

    private List<ExpedienteItem> getExpedienteItemFromXML(BusquedaAvanzadaResponseDocument.BusquedaAvanzadaResponse busquedaAvanzadaResponse) throws JDOMException, IOException, SAXException, ParserConfigurationException {
        List<ExpedienteItem> expedienteItems = new ArrayList<>();
        Node nodoValor = busquedaAvanzadaResponse.getBusquedaAvanzada().getDomNode().getLastChild();
        if(nodoValor != null && "valor".equals(nodoValor.getNodeName())) {
            String xmlExpedientes = nodoValor.getChildNodes().item(0).getNodeValue();

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse(new InputSource(new StringReader(xmlExpedientes)));

            NodeList nodes = doc.getElementsByTagName("item");
            //Este sera el formato en el que vendra el campo valor en la respuesta SOAP:
            /*
                <?xml version='1.0' encoding='ISO-8859-1'?>
                <results>
                    <item>
                        <value name='SPAC_FASES.ID' type='12'>
                            <![CDATA[1374]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.ID' type='12'>
                            <![CDATA[1014]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.NUMEXP' type='12'>
                            <![CDATA[DIP/A39075/2021/4022]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.NOMBREPROCEDIMIENTO' type='12'>
                            <![CDATA[Denuncia e información previa]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.NREG' type='12'>
                            <![CDATA[A39075/2021/REE-990001]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.FREG' type='91'>
                            <![CDATA[03/12/2021]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.IDENTIDADTITULAR' type='12'>
                            <![CDATA[MARÍA GARCÍA GÓMEZ]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.NIFCIFTITULAR' type='12'>
                            <![CDATA[39635231K]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.FAPERTURA' type='91'>
                            <![CDATA[03/12/2021]]>
                        </value>
                        <value name='SPAC_TBL_004.SUSTITUTO' type='12'>
                            <![CDATA[PRESENTACION]]>
                        </value>
                        <value name='SPAC_EXPEDIENTES.HAYRECURSO' type='12'>
                            <![CDATA[]]>
                        </value>
                    </item>
             *
             */
            //PRIMERO RECORREMOS LAS ETIQUETAS ITEM
            for (int i = 0; i < nodes.getLength(); i++) {
                ExpedienteItem expedienteItem = new ExpedienteItem();
                Element expedienteXML = (Element) nodes.item(i);
                NodeList values = expedienteXML.getElementsByTagName("value");
                //RECORREMOS LAS ETIQUETAS VALUES
                for (int j = 0; j < values.getLength(); j++) {
                    Element propiedadExp = (Element) values.item(j);
                    String valorPropiedad = getCDataFromElement(propiedadExp);
                    //Dependiendo del atributo name setearemos una propiedad u otra
                    switch(propiedadExp.getAttribute("name")){
                        case "SPAC_EXPEDIENTES.NOMBREPROCEDIMIENTO":
                            expedienteItem.setTipoExpediente(valorPropiedad);
                            break;
                        case "SPAC_TBL_004.SUSTITUTO":
                            expedienteItem.setEstadoExpediente(valorPropiedad);
                            break;
                        case "SPAC_EXPEDIENTES.NUMEXP":
                            expedienteItem.setNumExpediente(valorPropiedad);
                            break;
                        case "SPAC_EXPEDIENTES.FAPERTURA":
                            expedienteItem.setFechaApertura(valorPropiedad);
                            break;
                        case "SPAC_EXPEDIENTES.NREG":
                            expedienteItem.setNumRegistro(valorPropiedad);
                            break;
                        case "SPAC_EXPEDIENTES.FREG":
                            expedienteItem.setFechaRegistro(valorPropiedad);
                            break;
                        case "SPAC_EXPEDIENTES.IDENTIDADTITULAR":
                            expedienteItem.setTitular(valorPropiedad);
                            break;
                        case "SPAC_FASES.ID":
                            expedienteItem.setIdFase(valorPropiedad);
                            break;
                    }
                }
                expedienteItem.setRelacion("Interesado"); //La relacion siempre es Interesado
                expedienteItems.add(expedienteItem);
            }
        }

        return expedienteItems;
    }

    /**
     * Metodo para obtener los valores incluidos dentro de los <!CDATA[]> en los XMLs
     *
     * @param e
     * @return
     */
    private String getCDataFromElement(Element e) {

        NodeList list = e.getChildNodes();
        String data;

        for(int index = 0; index < list.getLength(); index++){
            if(list.item(index) instanceof CharacterData){
                CharacterData child = (CharacterData) list.item(index);
                data = child.getData();

                if(data != null && data.trim().length() > 0)
                    return child.getData();
            }
        }
        return "";
    }

}
