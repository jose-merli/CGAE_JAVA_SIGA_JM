package org.itcgae.siga.exea.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeRequestDocument;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeResponseDocument;
import es.cgae.consultatramites.token.schema.UsuarioType;
import ieci.tdw.ispac.services.ws.server.*;
import org.apache.axis2.context.OperationContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionAsistenciaItem;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.CenComunidadesautonomasMapper;
import org.itcgae.siga.db.mappers.CenDocumentacionpresentadaMapper;
import org.itcgae.siga.db.mappers.CenSolicitudincorporacionMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.*;
import org.itcgae.siga.db.services.exp.mappers.ExpExpedientesExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpProcedimientosExeaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.CenDocumentsolicitudinstituExtendsMapper;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientExpedientesEXEA;
import org.jdom.JDOMException;
import org.redabogacia.regtel.ws.eregtel.*;
import org.redabogacia.regtel.ws.eregtel.Interesado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
	private CenPersonaExtendsMapper _cenPersonaExtendsMapper;

    @Autowired
    private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

    @Autowired
    private ClientExpedientesEXEA _clientExpedientesEXEA;

    @Autowired
    private CenDocumentacionsolicitudExtendsMapper cenDocumentacionsolicitudExtendsMapper;

    @Autowired
    private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

    @Autowired
    private CenDocumentsolicitudinstituExtendsMapper cenDocumentsolicitudinstituExtendsMapper;

    @Autowired
    private FicherosServiceImpl ficherosServiceImpl;

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private CenSolicitudincorporacionExtendsMapper cenSolicitudincorporacionExtendsMapper;
    
    @Autowired
    private GenDiccionarioMapper genDiccionarioMapper;

    @Autowired
    private CenDocumentacionpresentadaMapper cenDocumentacionpresentadaMapper;

    @Autowired
    private GenFicheroMapper genFicheroMapper;

    @Autowired
    private CenPaisExtendsMapper cenPaisExtendsMapper;

    @Autowired
    private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;

    @Autowired
    private CenProvinciasExtendsMapper cenProvinciasExtendsMapper;

    @Autowired
    private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;

    @Autowired
    private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

    @Autowired
    private CenComunidadesautonomasMapper cenComunidadesautonomasMapper;

    private OperationContext operationContext;



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

                    String nombreFormBusqueda = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_NOMBRE_FORM_BUSQ, idInstitucion.toString()).getValor();
                    String nombreGrupo = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_NOMBRE_GRUPO, idInstitucion.toString()).getValor();
                    BusquedaAvanzadaDocument busquedaAvanzadaDocument = BusquedaAvanzadaDocument.Factory.newInstance();
                    BusquedaAvanzadaDocument.BusquedaAvanzada busquedaAvanzada = busquedaAvanzadaDocument.addNewBusquedaAvanzada();
                    busquedaAvanzada.setNombreGrupo(nombreGrupo);
                    busquedaAvanzada.setNombreFrmBusqueda(nombreFormBusqueda);
                    busquedaAvanzada.setDominio(1);
                    String xmlBusqueda = getXMLBusquedaAvanzada(identificacionColegiado);
                    if(!UtilidadesString.esCadenaVacia(xmlBusqueda)){
                        busquedaAvanzada.setXmlBusqueda(xmlBusqueda);
                    }
                    String urlService = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_WEBSERVICES_ADDIN_PARAM, idInstitucion.toString()).getValor();
                    if(!UtilidadesString.esCadenaVacia(urlService) && !UtilidadesString.esCadenaVacia(nombreFormBusqueda)
                        && !UtilidadesString.esCadenaVacia(nombreGrupo) && !"NULL".equals(nombreFormBusqueda) && !"NULL".equals(nombreGrupo)) {
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
    public ExpedienteDTO getDetalleExpedienteEXEA(HttpServletRequest request, String numExpedienteEXEA, String identificacionColegiado) {
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

                            if(!UtilidadesString.esCadenaVacia(identificacionColegiado)){
                                ExpedienteDTO expedientes = this.getExpedientesEXEAPersonalColegio(request, identificacionColegiado);

                                if(expedientes.getError() == null
                                    && expedientes.getExpedienteItem() != null
                                    && !expedientes.getExpedienteItem().isEmpty()){
                                    List<ExpedienteItem> expedienteItems = expedientes.getExpedienteItem()
                                            .stream().filter(expediente -> expedienteItem.getNumExpediente().equals(expediente.getNumExpediente()))
                                                    .collect(Collectors.toList());

                                    if(!expedienteItems.isEmpty()){
                                        expedienteItem.setIdFase(expedienteItems.get(0).getIdFase());
                                        expedienteItem.setFechaRegistro(expedienteItems.get(0).getFechaRegistro());
                                        expedienteItem.setDescInstitucion(expedienteItems.get(0).getDescInstitucion());
                                        expedienteItem.setNumRegistro(expedienteItems.get(0).getNumRegistro());
                                        expedienteItem.setEstadoExpediente(expedienteItems.get(0).getEstadoExpediente());
                                    }
                                }
                            }
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
    @Transactional
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
                            LOGGER.info("sincronizarDocumentacionEXEA() / Documento con cdgoExt " + documento.getCodDocEXEA() + " encontrado, actualizamos");
                            CenDocumentacionsolicitud documentoSIGA = documentacionSolicitud.get(0);
                            GenRecursosCatalogosKey key = new GenRecursosCatalogosKey();
                            key.setIdrecurso(documentoSIGA.getDescripcion());
                            key.setIdlenguaje("1");

                            GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosExtendsMapper.selectByPrimaryKey(key);

                            if(genRecursosCatalogos != null && !genRecursosCatalogos.getDescripcion().equals(documento.getDocumento())){
                                LOGGER.info("sincronizarDocumentacionEXEA() / La descripcion ha cambiado, actualizamos GEN_RECURSOS_CATALOGOS");

                                //Actualizamos descripcion si no coincide
                                genRecursosCatalogos.setFechamodificacion(new Date());
                                genRecursosCatalogos.setUsumodificacion(usuarios.get(0).getIdusuario());
                                genRecursosCatalogos.setDescripcion(documento.getDocumento());
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
                                LOGGER.info("sincronizarDocumentacionEXEA() / Actualizado espaniol");

                                //Actualizamos para los demas lenguajes
                                genRecursosCatalogos.setIdlenguaje("2");
                                genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#CA");
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
                                LOGGER.info("sincronizarDocumentacionEXEA() / Actualizado catalan");

                                genRecursosCatalogos.setIdlenguaje("3");
                                genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#EU");
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
                                LOGGER.info("sincronizarDocumentacionEXEA() / Actualizado euskera");

                                genRecursosCatalogos.setIdlenguaje("4");
                                genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#GL");
                                genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
                                LOGGER.info("sincronizarDocumentacionEXEA() / Actualizado gallego");

                            }

                            if(SigaConstants.SI.equals(documento.getObligatorio())){
                                documentoSIGA.setObligatorio(SigaConstants.DB_TRUE);
                            }else{
                                documentoSIGA.setObligatorio(SigaConstants.DB_FALSE);
                            }
                            documentoSIGA.setObservaciones(documento.getObservaciones());
                            cenDocumentacionsolicitudExtendsMapper.updateByPrimaryKeySelective(documentoSIGA);
                            LOGGER.info("sincronizarDocumentacionEXEA() / Actualizado en CEN_DOCUMENTACIONSOLICITUD");

                            CenDocumentsolicitudinstituKey cenDocumentsolicitudinstituKey = new CenDocumentsolicitudinstituKey();
                            cenDocumentsolicitudinstituKey.setIdinstitucion(idInstitucion);
                            cenDocumentsolicitudinstituKey.setIdtipocolegiacion(Short.valueOf(documento.getTipoColegiacion()));
                            cenDocumentsolicitudinstituKey.setIdtiposolicitud(Short.valueOf(documento.getTipoSolicitud()));
                            cenDocumentsolicitudinstituKey.setIdmodalidad(Short.valueOf(documento.getIdModalidad()));
                            cenDocumentsolicitudinstituKey.setIddocumsoliinstitu(documentoSIGA.getIddocumentacion());

                            CenDocumentsolicitudinstitu cenDocumentsolicitudinstitu = cenDocumentsolicitudinstituExtendsMapper.selectByPrimaryKey(cenDocumentsolicitudinstituKey);
                            //Si no existe dicho documento para los criterios marcados lo insertamos
                            if(cenDocumentsolicitudinstitu == null){
                                LOGGER.info("sincronizarDocumentacionEXEA() / No existe en CEN_DOCUMENTSOLICITUDINSTITU para los criterios establecidos, se inserta");
                                cenDocumentsolicitudinstitu = new CenDocumentsolicitudinstitu();
                                cenDocumentsolicitudinstitu.setIdinstitucion(idInstitucion);
                                cenDocumentsolicitudinstitu.setIdtipocolegiacion(Short.valueOf(documento.getTipoColegiacion()));
                                cenDocumentsolicitudinstitu.setIdtiposolicitud(Short.valueOf(documento.getTipoSolicitud()));
                                cenDocumentsolicitudinstitu.setIdmodalidad(Short.valueOf(documento.getIdModalidad()));
                                cenDocumentsolicitudinstitu.setIddocumsoliinstitu(documentoSIGA.getIddocumentacion());
                                cenDocumentsolicitudinstitu.setUsumodificacion(usuarios.get(0).getIdusuario());
                                cenDocumentsolicitudinstitu.setFechamodificacion(new Date());
                                cenDocumentsolicitudinstituExtendsMapper.insert(cenDocumentsolicitudinstitu);
                                LOGGER.info("sincronizarDocumentacionEXEA() / Actualizado en CEN_DOCUMENTSOLICITUDINSTITU");
                            }
                        }else{
                            LOGGER.info("sincronizarDocumentacionEXEA() / Documento con cdgoExt " + documento.getCodDocEXEA() + " no encontrado, insertamos");
                            //select max id gen recursos catalogos
                            NewIdDTO newIdDTO = genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion.toString(),usuarios.get(0).getIdlenguaje());
                            GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
                            if(newIdDTO == null){
                                genRecursosCatalogos.setIdrecurso("1");
                            }else {
                                long idRecurso = Long.valueOf(newIdDTO.getNewId()) + 1;
                                genRecursosCatalogos.setIdrecurso(String.valueOf(idRecurso));
                            }
                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertamos en GEN_RECURSOS_CATALOGOS, nuevo ID: " + genRecursosCatalogos.getIdrecurso());
                            //4 inserts gen recursos catalogos
                            genRecursosCatalogos.setNombretabla("CEN_DOCUMENTACIONSOLICITUD");
                            genRecursosCatalogos.setCampotabla("DESCRIPCION");
                            genRecursosCatalogos.setIdlenguaje("1");
                            genRecursosCatalogos.setFechamodificacion(new Date());
                            genRecursosCatalogos.setDescripcion(documento.getDocumento());
                            genRecursosCatalogos.setIdrecursoalias("cen.documentacionsolicitud."+documento.getCodDocEXEA());
                            genRecursosCatalogos.setUsumodificacion(usuarios.get(0).getIdusuario());
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertado espaniol");

                            genRecursosCatalogos.setIdlenguaje("2");
                            genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#CA");
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertado catalan");

                            genRecursosCatalogos.setIdlenguaje("3");
                            genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#EU");
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertado euskera");

                            genRecursosCatalogos.setIdlenguaje("4");
                            genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#GL");
                            genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertado gallego");

                            //Select max id cen documentacion solicitud

                            NewIdDTO nextID = cenDocumentacionsolicitudExtendsMapper.getNextId();
                            CenDocumentacionsolicitud cenDocumentacionsolicitud = new CenDocumentacionsolicitud();
                            if(nextID == null){
                                cenDocumentacionsolicitud.setIddocumentacion((short)1);
                            }else{
                                cenDocumentacionsolicitud.setIddocumentacion(Short.valueOf(nextID.getNewId()));
                            }
                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertamos en CEN_DOCUMENTACIONSOLICITUD, nuevo ID: " + cenDocumentacionsolicitud.getIddocumentacion());
                            cenDocumentacionsolicitud.setDescripcion(genRecursosCatalogos.getIdrecurso());
                            cenDocumentacionsolicitud.setFechamodificacion(new Date());
                            cenDocumentacionsolicitud.setUsumodificacion(usuarios.get(0).getIdusuario());
                            cenDocumentacionsolicitud.setCodigoext(documento.getCodDocEXEA());
                            if(SigaConstants.SI.equals(documento.getObligatorio())){
                                cenDocumentacionsolicitud.setObligatorio(SigaConstants.DB_TRUE);
                            }else{
                                cenDocumentacionsolicitud.setObligatorio(SigaConstants.DB_FALSE);
                            }
                            cenDocumentacionsolicitud.setObservaciones(documento.getObservaciones());
                            cenDocumentacionsolicitud.setBloqueado("N");
                            //Insert en cen documentacion solicitud
                            cenDocumentacionsolicitudExtendsMapper.insert(cenDocumentacionsolicitud);

                            LOGGER.info("sincronizarDocumentacionEXEA() / Insertamos en Cen_Documentsolicitudinstitu con los criterios establecidos");
                            CenDocumentsolicitudinstitu cenDocumentsolicitudinstitu = new CenDocumentsolicitudinstitu();
                            cenDocumentsolicitudinstitu.setIdinstitucion(idInstitucion);
                            cenDocumentsolicitudinstitu.setIdtipocolegiacion(Short.valueOf(documento.getTipoColegiacion()));
                            cenDocumentsolicitudinstitu.setIdtiposolicitud(Short.valueOf(documento.getTipoSolicitud()));
                            cenDocumentsolicitudinstitu.setIdmodalidad(Short.valueOf(documento.getIdModalidad()));
                            cenDocumentsolicitudinstitu.setIddocumsoliinstitu(cenDocumentacionsolicitud.getIddocumentacion());
                            cenDocumentsolicitudinstitu.setUsumodificacion(usuarios.get(0).getIdusuario());
                            cenDocumentsolicitudinstitu.setFechamodificacion(new Date());
                            cenDocumentsolicitudinstituExtendsMapper.insert(cenDocumentsolicitudinstitu);
                        }

                    }

                }
            }
            LOGGER.info("sincronizarDocumentacionEXEA() - FIN SINCRONIZACION");
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

    @Override
    @Transactional
    public InsertResponseDTO subirDocumentoSolIncorp(MultipartHttpServletRequest request) {
    	LOGGER.info("ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() - INICIO");
    	InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
    	String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        
        Error error = new Error();
        ObjectMapper objectMapper = new ObjectMapper();
        int affectedRows = 0;
        
        try {

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (usuarios != null && !usuarios.isEmpty()) {

                String idSolicitud = request.getParameter("idSolicitud");
                String json = "";
                String[] jsons = request.getParameter("documentosJSON").replace("[", "").replace("]", "").replace("},{", "};{").split(";");
                Collections.reverse(Arrays.asList(jsons)); //El orden del JSON es inverso al de los documentos, lo revertimos
                Iterator<String> itr = request.getFileNames();
                Short idInstitucionSol = cenSolicitudincorporacionExtendsMapper.selectByPrimaryKey(Long.valueOf(idSolicitud)).getIdinstitucion();
                int i = 0;
                
                while (itr.hasNext()) {
                	//Recuperamos el documentoItem del json
                	json = jsons[i];
                    i++;
                    LOGGER.info("ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() - El objeto JSON contiene: " + json);
                	DocumentacionIncorporacionItem documentacionIncorporacionItem = objectMapper.readValue(json, DocumentacionIncorporacionItem.class);
                	
                    MultipartFile file = request.getFile(itr.next());
                    LOGGER.info("ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() - El fileName contiene: " + file.getOriginalFilename());
                    String nombreFichero = file.getOriginalFilename();
                    String extension = FilenameUtils.getExtension(nombreFichero);

                    Long idFichero = uploadFile(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucionSol,
                            nombreFichero, extension, idSolicitud);

                    String codDocAnexo = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.COD_DOC_ANEXO_PARAM, idInstitucion.toString()).getValor();

                    if(!codDocAnexo.equals(documentacionIncorporacionItem.getCodDocEXEA())) {
                        CenDocumentacionpresentada cenDocumentacionpresentada = new CenDocumentacionpresentada();
                        cenDocumentacionpresentada.setIdsolicitud(Long.valueOf(idSolicitud));
                        cenDocumentacionpresentada.setIddocumentacion(Short.valueOf(documentacionIncorporacionItem.getIdDocumentacion()));
                        cenDocumentacionpresentada.setIdfichero(idFichero);
                        cenDocumentacionpresentada.setNombrefichero(nombreFichero);
                        cenDocumentacionpresentada.setFechamodificacion(new Date());
                        cenDocumentacionpresentada.setUsumodificacion(usuarios.get(0).getIdusuario());
                        affectedRows += cenDocumentacionpresentadaMapper.insert(cenDocumentacionpresentada);
                    }else{
                        Short idDocAnexo = createAnexo(documentacionIncorporacionItem, idSolicitud, usuarios.get(0));
                        if(idDocAnexo != null){
                            CenDocumentacionpresentada cenDocumentacionpresentada = new CenDocumentacionpresentada();
                            cenDocumentacionpresentada.setIdsolicitud(Long.valueOf(idSolicitud));
                            cenDocumentacionpresentada.setIddocumentacion(idDocAnexo);
                            cenDocumentacionpresentada.setIdfichero(idFichero);
                            cenDocumentacionpresentada.setNombrefichero(nombreFichero);
                            cenDocumentacionpresentada.setFechamodificacion(new Date());
                            cenDocumentacionpresentada.setUsumodificacion(usuarios.get(0).getIdusuario());
                            affectedRows += cenDocumentacionpresentadaMapper.insert(cenDocumentacionpresentada);
                        }
                    }

                    if(affectedRows > 0){
                        insertResponseDTO.setStatus(SigaConstants.OK);
                        insertResponseDTO.setId(idSolicitud);
                    } else{
                        LOGGER.error("ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() / Error al insertar el registro del documento de la solicitud");
                        error.setCode(500);
                        error.setDescription("Error al insertar el registro del documento de la solicitud");
                        insertResponseDTO.setError(error);
                    }
                    
                }
            }
        }catch(Exception e){
            LOGGER.error(
                    "ExpedientesEXEAServiceImpl.subirDocumentoAsistencia() -> Se ha producido un error al subir un fichero perteneciente a la solicitud de colegiacion",
                    e);
            error.setCode(500);
            error.setDescription("Error al subir el fichero perteneciente a la solicitud de colegiacion");
            error.setMessage(e.getMessage());
            insertResponseDTO.setError(error);
        }
        LOGGER.info("ExpedientesEXEAServiceImpl.subirDocumentoSolIncorp() - FIN");
        return insertResponseDTO;
    }

    @Override
    @Transactional
    public ResponseEntity<InputStreamResource> descargarDocumentosSolIncorp(List<DocumentacionIncorporacionItem> documentos, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ResponseEntity<InputStreamResource> res = null;
        InputStream fileStream = null;
        HttpHeaders headers = new HttpHeaders();

        try {

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.descargarDocumentosSolIncorp() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.descargarDocumentosSolIncorp() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (usuarios != null && !usuarios.isEmpty() && !documentos.isEmpty()) {

                if (documentos.size() == 1) {
                    String extension = "";

                    GenFicheroKey genFicheroKey = new GenFicheroKey();
                    genFicheroKey.setIdfichero(Long.valueOf(documentos.get(0).getIdFichero()));
                    genFicheroKey.setIdinstitucion(idInstitucion);
                    extension = genFicheroMapper.selectByPrimaryKey(genFicheroKey).getExtension();

                    String path = getDirectorioFicheroSolIncorp(idInstitucion);
                    path += File.separator + idInstitucion + "_" + documentos.get(0).getIdFichero()
                            + "." + extension;

                    File file = new File(path);
                    fileStream = new FileInputStream(file);

                    String tipoMime = getMimeType("."+extension);

                    headers.setContentType(MediaType.parseMediaType(tipoMime));
                    if(UtilidadesString.esCadenaVacia(documentos.get(0).getNombreDoc())){
                        documentos.get(0).setNombreDoc("default."+extension);
                    }
                    headers.set("Content-Disposition",
                            "attachment; filename=\"" + documentos.get(0).getNombreDoc() + "\"");
                    headers.setContentLength(file.length());

                } else {
                    fileStream = getZipFileDocumentosSolIncorp(documentos, idInstitucion);

                    headers.setContentType(MediaType.parseMediaType("application/zip"));
                    headers.set("Content-Disposition", "attachment; filename=\"documentos.zip\"");
                }

                res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
                        HttpStatus.OK);
            }

        } catch (Exception e) {
            LOGGER.error(
                    "ExpedientesEXEAServiceImpl.descargarDocumentosSolIncorp() -> Se ha producido un error al descargar un archivo asociado a la solicitud",
                    e);
        }

        return res;
    }

    @Override
    public ResponseEntity<InputStreamResource> getJustificante(HttpServletRequest request, String claveConsulta) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ResponseEntity<InputStreamResource> res = null;
        InputStream fileStream = null;
        HttpHeaders headers = new HttpHeaders();

        try {

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.getJustificante() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.getJustificante() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(claveConsulta)) {

                String urlWS = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_URL_WEBSERVICES_REGTEL, idInstitucion.toString()).getValor();

                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                SOAPConnection soapConnection = soapConnectionFactory.createConnection();

                MessageFactory messageFactory = MessageFactory.newInstance();
                SOAPMessage soapRequest = messageFactory.createMessage();

                SOAPPart soapPart = soapRequest.getSOAPPart();
                //Create this based on the SOAP request XML, you can use SOAP UI to get the XML as you like
                String myNamespace = "ereg";
                String myNamespaceURI = "http://www.redabogacia.org/regtel/ws/eregtel";

                // SOAP Envelope
                SOAPEnvelope envelope = soapPart.getEnvelope();
                envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

                // SOAP Body
                SOAPBody soapBody = envelope.getBody();
                SOAPElement soapBodyElem = soapBody.addChildElement("ConsultaAdjunto", myNamespace);

                SOAPElement claveConsultaElem = soapBodyElem.addChildElement("claveConsulta", myNamespace);
                claveConsultaElem.addTextNode(claveConsulta);

                SOAPElement numSecElement = soapBodyElem.addChildElement("nroSecuenciaAdjunto", myNamespace);
                numSecElement.addTextNode("-1");

                SOAPMessage soapResponse = soapConnection.call(soapRequest, urlWS);
                int numOfAttachments = soapResponse.countAttachments();
                if(numOfAttachments > 0){
                    Iterator attachments = soapResponse.getAttachments();
                    while(attachments.hasNext()){
                        AttachmentPart attachment = (AttachmentPart) attachments.next();
                        byte [] fichero = attachment.getRawContentBytes();
                        fileStream = new ByteArrayInputStream(fichero);
                        headers.setContentType(MediaType.parseMediaType(attachment.getContentType()));
                    }

                    Iterator itr = soapResponse.getSOAPBody().getChildElements();
                    javax.xml.soap.Node nodoConsultaAdjuntoResponse = (javax.xml.soap.Node) itr.next();
                    Node adjunto = nodoConsultaAdjuntoResponse.getFirstChild();
                    String nombreArchivo = adjunto.getChildNodes().item(1).getTextContent();

                    headers.set("Content-Disposition",
                            "attachment; filename=" + nombreArchivo);
                }

                soapConnection.close();

                res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
                        HttpStatus.OK);
            }

        } catch (Exception e) {
            LOGGER.error(
                    "ExpedientesEXEAServiceImpl.getJustificante() -> Se ha producido un error al descargar el justificante",
                    e);
        }

        return res;
    }

    @Override
    @Transactional
    public DeleteResponseDTO eliminarDocumentoSolIncorp(HttpServletRequest request, String idSolicitud, List<DocumentacionIncorporacionItem> documentos) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        int affectedRows = 0;
        try {

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.eliminarDocumentoSolIncorp() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ExpedientesEXEAServiceImpl.eliminarDocumentoSolIncorp() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (usuarios != null && !usuarios.isEmpty()) {

                for (DocumentacionIncorporacionItem doc : documentos) {

                    String path = getDirectorioFicheroSolIncorp(idInstitucion);
                    path += File.separator + idInstitucion + "_" + doc.getIdFichero()
                            + doc.getNombreDoc()
                            .substring(doc.getNombreDoc().lastIndexOf("."), doc.getNombreDoc().length())
                            .toLowerCase();

                    File file = new File(path);

                    if (file.exists()) {
                        file.delete();
                    }

                    String codDocAnexo = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.COD_DOC_ANEXO_PARAM, idInstitucion.toString()).getValor();

                    if(!codDocAnexo.equals(doc.getCodDocEXEA())) {
                        CenDocumentacionpresentadaKey cenDocumentacionpresentadaKey = new CenDocumentacionpresentadaKey();
                        cenDocumentacionpresentadaKey.setIddocumentacion(Short.valueOf(doc.getIdDocumentacion()));
                        cenDocumentacionpresentadaKey.setIdsolicitud(Long.valueOf(idSolicitud));

                        affectedRows += cenDocumentacionpresentadaMapper.deleteByPrimaryKey(cenDocumentacionpresentadaKey);
                    }else{
                        affectedRows += deleteAnexo(doc, idSolicitud);
                    }


                    if (affectedRows > 0) {
                        deleteResponseDTO.setStatus(SigaConstants.OK);
                    }else {
                        deleteResponseDTO.setStatus(SigaConstants.KO);
                        LOGGER.error(
                                "ExpedientesEXEAServiceImpl.eliminarDocumentoSolIncorp() -> Se ha producido un error en la eliminación de documentos asociados a la solicitud");
                        error.setCode(500);
                        error.setDescription("Se ha producido un error en la eliminación de documentos asociados a la solicitud");
                        deleteResponseDTO.setError(error);
                    }

                    GenFicheroKey genFicheroKey = new GenFicheroKey();
                    Short idInstitucionSol = cenSolicitudincorporacionExtendsMapper.selectByPrimaryKey(Long.valueOf(idSolicitud)).getIdinstitucion();

                    genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
                    genFicheroKey.setIdinstitucion(idInstitucionSol);
                    affectedRows = 0;
                    affectedRows += genFicheroMapper.deleteByPrimaryKey(genFicheroKey);

                    if (affectedRows > 0) {
                        deleteResponseDTO.setStatus(SigaConstants.OK);
                    }else{
                        LOGGER.error(
                                "AsistenciaServiceImpl.eliminarDocumentosAsistencia() -> Se ha producido un error en la eliminación de documentos asociados a la solicitud");
                        deleteResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(500);
                        error.setDescription("Se ha producido un error en la eliminación de documentos asociados a la solicitud");
                        deleteResponseDTO.setError(error);
                    }
                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "ExpedientesEXEAServiceImpl.eliminarDocumentoSolIncorp() -> Se ha producido un error en la eliminación de documentos asociados a la asistencia",
                    e);
            error.setCode(500);
            error.setDescription("Se ha producido un error en la eliminación de documentos asociados a la solicitud");
            error.setMessage(e.getMessage());
            deleteResponseDTO.setError(error);
        }

        return deleteResponseDTO;
    }

    @Override
    @Transactional
    public UpdateResponseDTO iniciarTramiteColegiacionEXEA(HttpServletRequest request, String data) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "iniciarTramiteColegiacionEXEA() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "iniciarTramiteColegiacionEXEA() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {
                	String idSolicitud = data.split("/")[0];
                    String asunto = data.split("/")[1];
                    String idTipoSolicitud;
                    
                    // Obtención del parámetro que indica si la comprobación del certificado está activa
                	GenParametrosKey key = new GenParametrosKey();
					key.setIdinstitucion(idInstitucion);
					key.setModulo(SigaConstants.MODULO_CENSO);
					key.setParametro(SigaConstants.PARAMETRO_COMPROCACION_CERTIFICADO_SOL_INC);
					GenParametros genParametro = genParametrosExtendsMapper.selectByPrimaryKey(key);
					
					String comprobarCertificado = genParametro == null || genParametro.getValor() == null ? "0" : genParametro.getValor();
					
					// Obtención del parámetro que informa la fecha de control de la existencia CNI
					key = new GenParametrosKey();
					key.setIdinstitucion(idInstitucion);
					key.setModulo(SigaConstants.MODULO_CENSO);
					key.setParametro(SigaConstants.FECHA_CONTROL_EXISTENCIA_CNI);
					genParametro = genParametrosExtendsMapper.selectByPrimaryKey(key);
					
					String fechaControlExistenciaCNI = genParametro == null || genParametro.getValor() == null ? "01/08/2007" : genParametro.getValor();
					
					// Obtenemos los datos de la solicitud
					CenSolicitudincorporacion solIncorporacion;
					solIncorporacion = cenSolicitudincorporacionExtendsMapper.selectByPrimaryKey(Long.valueOf(idSolicitud));
					
					// Preparamos los literales de para informar el error en formato multiidioma
					String literalCertificadoNoValido = getTraduccionLiteral("censo.solicitudIncorporacion.ficha.errorCertificado",
							usuarios.get(0).getIdlenguaje());
					
					// Se comprobará el certificado de solicitud de incorporación si está activo y si el tipo de solicitud es
					// ejerciente o no ejerciente
					if ("1".equals(comprobarCertificado) &&
							(solIncorporacion.getIdtipocolegiacion() != 20 || solIncorporacion.getIdtipocolegiacion() != 50)) {
						
						if (solIncorporacion.getIdtipocolegiacion() == 30 ) {
							idTipoSolicitud = "20";
						}else{
							idTipoSolicitud = "10";
						}
						
						if ("20".equals(idTipoSolicitud) && solIncorporacion.getIdtipocolegiacion() != 20) {
							// Consultamos si existe el certificado
							String existeCertificado = _cenPersonaExtendsMapper.getCertificado(solIncorporacion.getNumeroidentificador(),
									idTipoSolicitud, fechaControlExistenciaCNI);
							
							// Si no existe certificado lanzamos una excepción indicando la descripción del error y se para el proceso
							if (existeCertificado == null) {
								throw new Exception(literalCertificadoNoValido);
							}
						}
						
					}

                    CenSolicitudincorporacion solicitudincorporacion = cenSolicitudincorporacionExtendsMapper.selectByPrimaryKey(Long.valueOf(idSolicitud));

                    if(solicitudincorporacion != null){
                        String urlWS = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.EXEA_URL_WEBSERVICES_REGTEL, idInstitucion.toString()).getValor();
                        RegistroEntradaDocument registroEntradaDocument = fillRegistroEntradaDocument(solicitudincorporacion, idInstitucion, asunto);

                        LOGGER.info("iniciarTramiteColegiacionEXEA() / Tramite iniciado");
                        RegistroEntradaResponseDocument registroEntradaResponseDocument = _clientExpedientesEXEA.iniciarExpedienteColegiacion(registroEntradaDocument, urlWS);
                        LOGGER.info("iniciarTramiteColegiacionEXEA() / Respuesta tramite: " + registroEntradaResponseDocument);
                        LOGGER.info("iniciarTramiteColegiacionEXEA() / Una vez tramitado, comprobamos su estado");
                        if(registroEntradaResponseDocument != null
                            && registroEntradaResponseDocument.getRegistroEntradaResponse() != null
                            && registroEntradaResponseDocument.getRegistroEntradaResponse().getRespuesta() != null
                            && SigaConstants.OK.equals(registroEntradaResponseDocument.getRegistroEntradaResponse().getRespuesta().getCodigo())){

                            String claveConsulta = registroEntradaResponseDocument.getRegistroEntradaResponse().getDatosRespuesta().getClaveConsulta();
                            LOGGER.info("iniciarTramiteColegiacionEXEA() / Clave consulta: " + claveConsulta);
                            ConsultaAsientoDocument consultaAsientoDocument = ConsultaAsientoDocument.Factory.newInstance();
                            ConsultaAsientoDocument.ConsultaAsiento consultaAsiento = consultaAsientoDocument.addNewConsultaAsiento();
                            consultaAsiento.setClaveConsulta(claveConsulta);

                            ConsultaAsientoResponseDocument consultaAsientoResponseDocument = _clientExpedientesEXEA.consultaExpediente(consultaAsientoDocument,urlWS);
                            //Si viene informado el num expediente es que ha ido todo bien, actualizamos la solicitud de incorporacion
                            if(consultaAsientoResponseDocument != null
                                && consultaAsientoResponseDocument.getConsultaAsientoResponse() != null
                                && consultaAsientoResponseDocument.getConsultaAsientoResponse().getAsiento() != null
                                && consultaAsientoResponseDocument.getConsultaAsientoResponse().getAsiento().getDatosExpedienteAsiento() != null
                                && !UtilidadesString.esCadenaVacia(consultaAsientoResponseDocument.getConsultaAsientoResponse().getAsiento().getDatosExpedienteAsiento().getNumeroExpediente())
                                && SigaConstants.OK.equals(consultaAsientoResponseDocument.getConsultaAsientoResponse().getRespuesta().getCodigo())){

                                String numRegistro = registroEntradaResponseDocument.getRegistroEntradaResponse().getDatosRespuesta().getCodigoRegistro();
                                String numExpediente = consultaAsientoResponseDocument.getConsultaAsientoResponse().getAsiento().getDatosExpedienteAsiento().getNumeroExpediente();
                                LOGGER.info("iniciarTramiteColegiacionEXEA() / Numero registro REGTEL: " + numRegistro);
                                //Seteamos numero registro
                                solicitudincorporacion.setNumRegistro(numRegistro);
                                solicitudincorporacion.setClaveconsultaregtel(claveConsulta);
                                solicitudincorporacion.setNumExpediente(numExpediente);
                                //Pasamos de pendiente documentacion a pendiente aprobacion
                                solicitudincorporacion.setIdestado(SigaConstants.INCORPORACION_PENDIENTE_APROBACION);
                                solicitudincorporacion.setFechaestado(new Date());
                                solicitudincorporacion.setFechamodificacion(new Date());
                                solicitudincorporacion.setUsumodificacion(usuarios.get(0).getIdusuario());
                                int affectedRows = cenSolicitudincorporacionExtendsMapper.updateByPrimaryKey(solicitudincorporacion);

                                if(affectedRows == 1){
                                    LOGGER.info("iniciarTramiteColegiacionEXEA() / Actualizado en BBDD");
                                    updateResponseDTO.setStatus(SigaConstants.OK);
                                    updateResponseDTO.setId(solicitudincorporacion.getIdsolicitud().toString() + ";" + solicitudincorporacion.getNumRegistro() + ";" + solicitudincorporacion.getClaveconsultaregtel() + ";" + solicitudincorporacion.getNumExpediente());
                                }else{
                                    error.setCode(500);
                                    error.setDescription("Error al actualizar la solicitud");
                                    error.setMessage("Error al actualizar la solicitud");
                                    updateResponseDTO.setStatus(SigaConstants.KO);
                                    updateResponseDTO.setError(error);
                                    LOGGER.error("iniciarTramiteColegiacionEXEA() / Error al actualizar en BBDD");
                                }

                            }else{
                                //No viene informado el num expediente o no ha devuelto OK la respuesta, informamos error
                                error.setCode(500);
                                error.setDescription("Error al consultar el tramite : " + consultaAsientoResponseDocument.getConsultaAsientoResponse().getRespuesta().getCodigo() + " - " + consultaAsientoResponseDocument.getConsultaAsientoResponse().getRespuesta().getDescripcion());
                                error.setMessage("Error al consultar el tramite : " + consultaAsientoResponseDocument.getConsultaAsientoResponse().getRespuesta().getCodigo() + " - " + consultaAsientoResponseDocument.getConsultaAsientoResponse().getRespuesta().getDescripcion());
                                updateResponseDTO.setStatus(SigaConstants.KO);
                                updateResponseDTO.setError(error);
                                LOGGER.error("iniciarTramiteColegiacionEXEA() / Error al consultar el tramite");
                            }
                        }else{
                            //Ha ocurrido un error al iniciar el tramite, informamos error
                            error.setCode(500);
                            error.setDescription("Error al iniciar el tramite : " + registroEntradaResponseDocument.getRegistroEntradaResponse().getRespuesta().getCodigo() + " - " + registroEntradaResponseDocument.getRegistroEntradaResponse().getRespuesta().getDescripcion());
                            error.setMessage("Error al iniciar el tramite : " + registroEntradaResponseDocument.getRegistroEntradaResponse().getRespuesta().getCodigo() + " - " + registroEntradaResponseDocument.getRegistroEntradaResponse().getRespuesta().getDescripcion());
                            updateResponseDTO.setStatus(SigaConstants.KO);
                            updateResponseDTO.setError(error);
                            LOGGER.error("iniciarTramiteColegiacionEXEA() / Error al iniciar el tramite");
                        }
                    }else{
                        LOGGER.error("iniciarTramiteColegiacionEXEA() / No se encontro la solicitud a aprobar");
                        error.setCode(500);
                        error.setMessage("No se encontro la solicitud");
                        error.description("No se encontro la solicitud");
                        updateResponseDTO.setStatus(SigaConstants.KO);
                        updateResponseDTO.setError(error);
                    }
                }
            }
        }catch(Exception e){
            LOGGER.error("iniciarTramiteColegiacionEXEA() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al iniciar el tramite de colegiacion por EXEA");
            error.description("Error al iniciar el tramite de colegiacion por EXEA");
            updateResponseDTO.setStatus(SigaConstants.KO);
            updateResponseDTO.setError(error);
        }
        return updateResponseDTO;
    }
    
    private String getTraduccionLiteral(String idRecurso, String idLenguaje) {

        GenDiccionarioKey genDiccionarioKey = new GenDiccionarioKey();
        genDiccionarioKey.setIdlenguaje(idLenguaje);
        genDiccionarioKey.setIdrecurso(idRecurso);

        return genDiccionarioMapper.selectByPrimaryKey(genDiccionarioKey).getDescripcion();
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

    /**
     *
     * Metodo que crea una lista de {@link ExpedienteItem} a partir del XML recibido en la respuesta
     *
     * @param busquedaAvanzadaResponse
     * @return
     * @throws JDOMException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private List<ExpedienteItem> getExpedienteItemFromXML(BusquedaAvanzadaResponseDocument.BusquedaAvanzadaResponse busquedaAvanzadaResponse) throws IOException, SAXException, ParserConfigurationException {
        LOGGER.info("getExpedienteItemFromXML() / INICIO");
        List<ExpedienteItem> expedienteItems = new ArrayList<>();
        Node nodoValor = busquedaAvanzadaResponse.getBusquedaAvanzada().getDomNode().getLastChild();
        if(nodoValor != null && "valor".equals(nodoValor.getNodeName())) {
            String xmlExpedientes = nodoValor.getChildNodes().item(0).getNodeValue();

            LOGGER.info("getExpedienteItemFromXML() / String del XML con los expedientes: " + xmlExpedientes);

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

        LOGGER.info("getExpedienteItemFromXML() / FIN - Expedientes devueltos : " + expedienteItems);
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

    /**
     * Metodo que llama al servicio de insert en GEN_FICHERO y sube el fichero físico
     *
     * @param bytes
     * @param idUsuario
     * @param idInstitucion
     * @param nombreFichero
     * @param extension
     * @param idSolicitud
     * @return
     */
    private Long uploadFile(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
                            String extension, String idSolicitud){

        FicheroVo ficheroVo = new FicheroVo();

        String directorioFichero = getDirectorioFicheroSolIncorp(idInstitucion);
        ficheroVo.setDirectorio(directorioFichero);
        ficheroVo.setNombre(nombreFichero);
        ficheroVo.setDescripcion("Fichero asociado a la solicitud de incorporacion " + idSolicitud);

        ficheroVo.setIdinstitucion(idInstitucion);
        ficheroVo.setFichero(bytes);
        ficheroVo.setExtension(extension.toLowerCase());

        ficheroVo.setUsumodificacion(idUsuario);
        ficheroVo.setFechamodificacion(new Date());
        ficherosServiceImpl.insert(ficheroVo);
        
        LOGGER.info("uploadFile(), -> Directorio " + ficheroVo.getDirectorio() +" | Nombre: "+ ficheroVo.getNombre());

        SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());

        return ficheroVo.getIdfichero();
    }

    /**
     * Metodo que obtiene el directorio donde se almacenan los documentos de las asistencias
     *
     * @param idInstitucion
     * @return
     */
    private String getDirectorioFicheroSolIncorp(Short idInstitucion) {

        // Extraemos el path para los ficheros
        GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
        genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
        List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
        String path = genPropertiesPath.get(0).getValor();

        StringBuffer directorioFichero = new StringBuffer(path);
        directorioFichero.append(idInstitucion);
        directorioFichero.append(File.separator);

        // Extraemos el path concreto para solicitudes de incorporacion
        GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
        genPropertiesExampleD.createCriteria().andParametroEqualTo("exea.ficheros.solincorp");
        List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
        directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

        return directorioFichero.toString();
    }

    /**
     *
     * Metodo que devuelve el tipo Mime (Cabecera Content-Type) dependiendo el tipo de extension del documento
     *
     * @param extension
     * @return
     */
    private String getMimeType(String extension) {

        String mime = "";

        switch (extension.toLowerCase()) {

            case ".doc":
                mime = "application/msword";
                break;
            case ".docx":
                mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;
            case ".pdf":
                mime = "application/pdf";
                break;
            case ".jpg":
                mime = "image/jpeg";
                break;
            case ".png":
                mime = "image/png";
                break;
            case ".rtf":
                mime = "application/rtf";
                break;
            case ".txt":
                mime = "text/plain";
                break;
        }

        return mime;
    }

    /**
     * Metodo que devuelve un ZIP en {@link InputStream} para cuando se va a descargar mas de un archivo a la vez
     *
     * @param documentos
     * @param idInstitucion
     * @return
     */
    private InputStream getZipFileDocumentosSolIncorp(List<DocumentacionIncorporacionItem> documentos,
                                                Short idInstitucion) {

        ByteArrayOutputStream byteArrayOutputStream = null;

        try {

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            for (DocumentacionIncorporacionItem doc : documentos) {
                int i = 0;
                GenFicheroKey genFicheroKey = new GenFicheroKey();
                genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
                genFicheroKey.setIdinstitucion(idInstitucion);
                String extension = genFicheroMapper.selectByPrimaryKey(genFicheroKey).getExtension();
                if(UtilidadesString.esCadenaVacia(doc.getNombreDoc())){
                    doc.setNombreDoc("default("+(i++)+")."+extension);
                }
                zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreDoc()));
                String path = getDirectorioFicheroSolIncorp(idInstitucion);
                path += File.separator + idInstitucion + "_" + doc.getIdFichero() + "." + extension;
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                IOUtils.copy(fileInputStream, zipOutputStream);
                fileInputStream.close();
            }

            zipOutputStream.closeEntry();

            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.flush();
                IOUtils.closeQuietly(zipOutputStream);
            }

            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Transactional
    private Short createAnexo(DocumentacionIncorporacionItem documento, String idSolicitud, AdmUsuarios usuario){

        Short idAnexo = null;
        Short idInstitucion = cenSolicitudincorporacionExtendsMapper.selectByPrimaryKey(Long.valueOf(idSolicitud)).getIdinstitucion();

        NewIdDTO newIdDTO = genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion.toString(),usuario.getIdlenguaje());
        GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
        if(newIdDTO == null){
            genRecursosCatalogos.setIdrecurso("1");
        }else {
            long idRecurso = Long.valueOf(newIdDTO.getNewId()) + 1;
            genRecursosCatalogos.setIdrecurso(String.valueOf(idRecurso));
        }
        LOGGER.info("createAnexo() / Insertamos en GEN_RECURSOS_CATALOGOS, nuevo ID: " + genRecursosCatalogos.getIdrecurso());
        //4 inserts gen recursos catalogos
        genRecursosCatalogos.setNombretabla("CEN_DOCUMENTACIONSOLICITUD");
        genRecursosCatalogos.setCampotabla("DESCRIPCION");
        genRecursosCatalogos.setIdlenguaje("1");
        genRecursosCatalogos.setFechamodificacion(new Date());
        genRecursosCatalogos.setDescripcion(documento.getDocumento());
        genRecursosCatalogos.setIdrecursoalias("cen.documentacionsolicitud."+documento.getCodDocEXEA());
        genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
        genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
        LOGGER.info("createAnexo() / Insertado espaniol");

        genRecursosCatalogos.setIdlenguaje("2");
        genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#CA");
        genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
        LOGGER.info("createAnexo() / Insertado catalan");

        genRecursosCatalogos.setIdlenguaje("3");
        genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#EU");
        genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
        LOGGER.info("createAnexo() / Insertado euskera");

        genRecursosCatalogos.setIdlenguaje("4");
        genRecursosCatalogos.setDescripcion(documento.getDocumento()+"#GL");
        genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
        LOGGER.info("createAnexo() / Insertado gallego");

        //Select max id cen documentacion solicitud

        NewIdDTO nextID = cenDocumentacionsolicitudExtendsMapper.getNextId();
        CenDocumentacionsolicitud cenDocumentacionsolicitud = new CenDocumentacionsolicitud();
        if(nextID == null){
            cenDocumentacionsolicitud.setIddocumentacion((short)1);
        }else{
            cenDocumentacionsolicitud.setIddocumentacion(Short.valueOf(nextID.getNewId()));
        }
        idAnexo = cenDocumentacionsolicitud.getIddocumentacion();
        LOGGER.info("createAnexo() / Insertamos en CEN_DOCUMENTACIONSOLICITUD, nuevo ID: " + cenDocumentacionsolicitud.getIddocumentacion());
        cenDocumentacionsolicitud.setDescripcion(genRecursosCatalogos.getIdrecurso());
        cenDocumentacionsolicitud.setFechamodificacion(new Date());
        cenDocumentacionsolicitud.setUsumodificacion(usuario.getIdusuario());
        cenDocumentacionsolicitud.setCodigoext(documento.getCodDocEXEA());
        if(SigaConstants.SI.equals(documento.getObligatorio())){
            cenDocumentacionsolicitud.setObligatorio(SigaConstants.DB_TRUE);
        }else{
            cenDocumentacionsolicitud.setObligatorio(SigaConstants.DB_FALSE);
        }
        cenDocumentacionsolicitud.setObservaciones(documento.getObservaciones());
        cenDocumentacionsolicitud.setBloqueado("N");
        //Insert en cen documentacion solicitud
        cenDocumentacionsolicitudExtendsMapper.insert(cenDocumentacionsolicitud);

        LOGGER.info("createAnexo() / Insertamos en Cen_Documentsolicitudinstitu con los criterios establecidos");
        CenDocumentsolicitudinstitu cenDocumentsolicitudinstitu = new CenDocumentsolicitudinstitu();
        cenDocumentsolicitudinstitu.setIdinstitucion(idInstitucion);
        cenDocumentsolicitudinstitu.setIdtipocolegiacion(Short.valueOf(documento.getTipoColegiacion()));
        cenDocumentsolicitudinstitu.setIdtiposolicitud(Short.valueOf(documento.getTipoSolicitud()));
        cenDocumentsolicitudinstitu.setIdmodalidad(Short.valueOf(documento.getIdModalidad()));
        cenDocumentsolicitudinstitu.setIddocumsoliinstitu(cenDocumentacionsolicitud.getIddocumentacion());
        cenDocumentsolicitudinstitu.setUsumodificacion(usuario.getIdusuario());
        cenDocumentsolicitudinstitu.setFechamodificacion(new Date());
        cenDocumentsolicitudinstituExtendsMapper.insert(cenDocumentsolicitudinstitu);

        return idAnexo;
    }

    @Transactional
    private int deleteAnexo (DocumentacionIncorporacionItem doc, String idSolicitud){
        int affectedRows = 0;

        Short idInstitucionSol = cenSolicitudincorporacionExtendsMapper.selectByPrimaryKey(Long.valueOf(idSolicitud)).getIdinstitucion();
        CenDocumentacionpresentadaKey cenDocumentacionpresentadaKey = new CenDocumentacionpresentadaKey();
        cenDocumentacionpresentadaKey.setIddocumentacion(Short.valueOf(doc.getIdDocumentacion()));
        cenDocumentacionpresentadaKey.setIdsolicitud(Long.valueOf(idSolicitud));

        affectedRows += cenDocumentacionpresentadaMapper.deleteByPrimaryKey(cenDocumentacionpresentadaKey);

        CenDocumentsolicitudinstituKey cenDocumentsolicitudinstituKey = new CenDocumentsolicitudinstituKey();
        cenDocumentsolicitudinstituKey.setIddocumsoliinstitu(Short.valueOf(doc.getIdDocumentacion()));
        cenDocumentsolicitudinstituKey.setIdmodalidad(Short.valueOf(doc.getIdModalidad()));
        cenDocumentsolicitudinstituKey.setIdtipocolegiacion(Short.valueOf(doc.getTipoColegiacion()));
        cenDocumentsolicitudinstituKey.setIdtiposolicitud(Short.valueOf(doc.getTipoSolicitud()));
        cenDocumentsolicitudinstituKey.setIdinstitucion(idInstitucionSol);

        affectedRows += cenDocumentsolicitudinstituExtendsMapper.deleteByPrimaryKey(cenDocumentsolicitudinstituKey);

        CenDocumentacionsolicitud cenDocumentacionsolicitud = cenDocumentacionsolicitudExtendsMapper.selectByPrimaryKey(Short.valueOf(doc.getIdDocumentacion()));
        if(cenDocumentacionsolicitud != null)
        {
            GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
            genRecursosCatalogosExample.createCriteria().andDescripcionEqualTo(cenDocumentacionsolicitud.getDescripcion());
            affectedRows += genRecursosCatalogosExtendsMapper.deleteByExample(genRecursosCatalogosExample);
            affectedRows += cenDocumentacionsolicitudExtendsMapper.deleteByPrimaryKey(Short.valueOf(doc.getIdDocumentacion()));
        }

        return affectedRows;
    }

    private RegistroEntradaDocument fillRegistroEntradaDocument (CenSolicitudincorporacion cenSolicitudincorporacion, Short idInstitucion, String asunto) throws IOException, NoSuchAlgorithmException, TransformerException, ParserConfigurationException {
        RegistroEntradaDocument registroEntradaDocument = null;
        LOGGER.info("fillRegistroEntradaDocument() / INICIO");

        //Obtenemos el codigo del procedimiento de colegiacion de exea para la institucion
        ExpProcedimientosExeaExample expProcedimientosExeaExample = new ExpProcedimientosExeaExample();
        expProcedimientosExeaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andEsColegiacionEqualTo((short)1);

        List<ExpProcedimientosExea> procedimientosExea = expProcedimientosExeaExtendsMapper.selectByExample(expProcedimientosExeaExample);

        if(procedimientosExea != null && !procedimientosExea.isEmpty()){

            String codProcedimiento = procedimientosExea.get(0).getCodPcdExea();
            registroEntradaDocument = RegistroEntradaDocument.Factory.newInstance();
            RegistroEntradaDocument.RegistroEntrada registroEntrada = registroEntradaDocument.addNewRegistroEntrada();

            DatosExpediente datosExpediente = registroEntrada.addNewDatosExpedienteEntrada();
            datosExpediente.setCodigoProcedimiento(codProcedimiento);

            Interesado interesado = registroEntrada.addNewPresentador();
            Persona persona = interesado.addNewIdentidad();

            PersonaFisica personaFisica = PersonaFisica.Factory.newInstance();
            personaFisica.setNombre(cenSolicitudincorporacion.getNombre());
            personaFisica.setApellido1(cenSolicitudincorporacion.getApellido1());
            if(!UtilidadesString.esCadenaVacia(cenSolicitudincorporacion.getApellido2())){
                personaFisica.setApellido2(cenSolicitudincorporacion.getApellido2());
            }
            IdentificacionPF identificacionPF = IdentificacionPF.Factory.newInstance();
            if(Short.valueOf((short)10) == cenSolicitudincorporacion.getIdtipoidentificacion()){ //NIF-DNI
                identificacionPF.setDNI(cenSolicitudincorporacion.getNumeroidentificador());
            }else if(Short.valueOf((short)40) == cenSolicitudincorporacion.getIdtipoidentificacion()){ //NIE
                identificacionPF.setNIE(cenSolicitudincorporacion.getNumeroidentificador());
            }else{
                identificacionPF.setPasaporte(cenSolicitudincorporacion.getNumeroidentificador());
            }
            personaFisica.setIdentificacion(identificacionPF);
            persona.setPersonaFisica(personaFisica);


            interesado.setEmail(cenSolicitudincorporacion.getCorreoelectronico());
            if(!UtilidadesString.esCadenaVacia(cenSolicitudincorporacion.getMovil())){
                Interesado.Telefono telefono = interesado.addNewTelefono();
                telefono.setNumero(cenSolicitudincorporacion.getMovil());
                telefono.setAceptaSMS(true);
            }

            String cdgoExtInst = cenInstitucionExtendsMapper.selectByPrimaryKey(idInstitucion).getCodigoext();
            if(!UtilidadesString.esCadenaVacia(cenSolicitudincorporacion.getNcolegiado())){
               Interesado.IdentificacionColegiacion identificacionColegiacion =  interesado.addNewIdentificacionColegiacion();
               identificacionColegiacion.setNumeroColegiado(cenSolicitudincorporacion.getNcolegiado());
               identificacionColegiacion.setColegio(cdgoExtInst);
            }

            registroEntrada.setEntidadDestinataria(cdgoExtInst);
            registroEntrada.setAsunto(asunto);
            registroEntrada.setDatosEspecificos(createXMLDatosEspecificosFromSolicitud(cenSolicitudincorporacion));
            RegistroEntradaDocument.RegistroEntrada.Adjuntos adjuntos = registroEntrada.addNewAdjuntos();
            CenDocumentacionpresentadaExample cenDocumentacionpresentadaExample = new CenDocumentacionpresentadaExample();
            cenDocumentacionpresentadaExample.createCriteria().andIdsolicitudEqualTo(cenSolicitudincorporacion.getIdsolicitud());
            LOGGER.info("fillRegistroEntradaDocument() / Datos especificos: " + registroEntrada.getDatosEspecificos());

            List<CenDocumentacionpresentada> documentosSolicitud = cenDocumentacionpresentadaMapper.selectByExample(cenDocumentacionpresentadaExample);

            if(documentosSolicitud != null && !documentosSolicitud.isEmpty()){

                int i = 1;
                for(CenDocumentacionpresentada documento : documentosSolicitud){

                    Adjunto adjunto = adjuntos.addNewAdjunto();

                    CenDocumentacionsolicitud cenDocumentacionsolicitud = cenDocumentacionsolicitudExtendsMapper.selectByPrimaryKey(documento.getIddocumentacion());
                    GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
                    genRecursosCatalogosKey.setIdlenguaje("1");
                    genRecursosCatalogosKey.setIdrecurso(cenDocumentacionsolicitud.getDescripcion());
                    String descripcionAdjunto = genRecursosCatalogosExtendsMapper.selectByPrimaryKey(genRecursosCatalogosKey).getDescripcion();
                    GenFicheroKey genFicheroKey = new GenFicheroKey();
                    genFicheroKey.setIdfichero(Long.valueOf(documento.getIdfichero()));
                    genFicheroKey.setIdinstitucion(idInstitucion);
                    String extension = genFicheroMapper.selectByPrimaryKey(genFicheroKey).getExtension();

                    String path = getDirectorioFicheroSolIncorp(idInstitucion);
                    path += File.separator + idInstitucion + "_" + documento.getIdfichero()
                            + "." + extension;

                    Path pathDoc = Paths.get(path);

                    adjunto.setFicheroAdjunto(Files.readAllBytes(pathDoc));
                    adjunto.setNombreOriginalArchivo(documento.getNombrefichero());
                    adjunto.setDescripcion(descripcionAdjunto);
                    if(cenDocumentacionsolicitud != null){
                        adjunto.setCodigoTipoDocumento(cenDocumentacionsolicitud.getCodigoext().replaceFirst("^0+(?!$)",""));
                    }
                    adjunto.setHash(createHashFromFile(pathDoc.toFile()));
                    adjunto.setNumeroSecuencia(i);

                    i++;
                }

            }

        }

        LOGGER.info("fillRegistroEntradaDocument() / FIN");
        return registroEntradaDocument;
    }

    /**
     *
     * Crea el Hash necesario para la peticion de colegiacion a partir del archivo
     *
     * @param file
     * @return
     */
    private String createHashFromFile(File file) throws NoSuchAlgorithmException, IOException {
        String hashSHA1 = "";
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        InputStream fis = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[8192];
        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }
        hashSHA1 = new HexBinaryAdapter().marshal(digest.digest());
        return  hashSHA1;
    }

    /**
     * Metodo que rellena el nodo datosEspecificos de la peticion a partir de la solicitud de incorporacion
     *
     * @param solicitudincorporacion
     * @return
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    private String createXMLDatosEspecificosFromSolicitud(CenSolicitudincorporacion solicitudincorporacion) throws TransformerException, ParserConfigurationException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("solicitudColegiacion");
        doc.appendChild(rootElement);

        //NODO SOLICITANTE - INICIO
        Element solicitanteElement = doc.createElement("solicitante");
        rootElement.appendChild(solicitanteElement);

        Element tipoDocIdent = doc.createElement("tipoDocIdent");
        if(Short.valueOf((short)10) == solicitudincorporacion.getIdtipoidentificacion()){ //NIF-DNI
            tipoDocIdent.appendChild(doc.createTextNode("D"));
        }else if(Short.valueOf((short)40) == solicitudincorporacion.getIdtipoidentificacion()){ //NIE
            tipoDocIdent.appendChild(doc.createTextNode("X"));
        }else{ //Pasaporte
            tipoDocIdent.appendChild(doc.createTextNode("T"));
        }
        solicitanteElement.appendChild(tipoDocIdent);

        Element numDocIdenElement = doc.createElement("numDocIdent");
        numDocIdenElement.appendChild(doc.createTextNode(solicitudincorporacion.getNumeroidentificador()));
        solicitanteElement.appendChild(numDocIdenElement);

        Element sexoElement = doc.createElement("sexo");
        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getSexo())) {
            sexoElement.appendChild(doc.createTextNode(solicitudincorporacion.getSexo()));
        }
        solicitanteElement.appendChild(sexoElement);

        Element nombreElement = doc.createElement("nombre");
        nombreElement.appendChild(doc.createTextNode(solicitudincorporacion.getNombre()));
        solicitanteElement.appendChild(nombreElement);

        Element apellido1Element = doc.createElement("apellido1");
        apellido1Element.appendChild(doc.createTextNode(solicitudincorporacion.getApellido1()));
        solicitanteElement.appendChild(apellido1Element);

        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getApellido2())) {
            Element apellido2Element = doc.createElement("apellido2");
            apellido2Element.appendChild(doc.createTextNode(solicitudincorporacion.getApellido2()));
            solicitanteElement.appendChild(apellido2Element);
        }
        String cdgoExtPais = cenPaisExtendsMapper.selectByPrimaryKey(solicitudincorporacion.getIdpais()).getCodigoext();
        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getIdpais())){
            Element paisElement = doc.createElement("paisNacionalidad");
            paisElement.appendChild(doc.createTextNode(cdgoExtPais));
            solicitanteElement.appendChild(paisElement);
        }
        if (solicitudincorporacion.getFechanacimiento() != null) {
        	Element fechaNacimientoElement = doc.createElement("fechaNacimiento");
            fechaNacimientoElement.appendChild(doc.createTextNode(SigaConstants.DATE_FORMAT.format(solicitudincorporacion.getFechanacimiento())));
            solicitanteElement.appendChild(fechaNacimientoElement);
        }
        //NODO SOLICITANTE - FIN

        //NODO DETALLES - INICIO
        Element detallesElement = doc.createElement("detalles");
        rootElement.appendChild(detallesElement);

        Element modalidadElement = doc.createElement("modalidad");
        if((short)30 == solicitudincorporacion.getIdtipocolegiacion()){
            modalidadElement.appendChild(doc.createTextNode("E"));
        } else if((short)40 == solicitudincorporacion.getIdtipocolegiacion()){
            modalidadElement.appendChild(doc.createTextNode("N"));
        } else if((short)50 == solicitudincorporacion.getIdtipocolegiacion()){
            modalidadElement.appendChild(doc.createTextNode("I"));
        }
        detallesElement.appendChild(modalidadElement);

        Element cuentaPropiaElement = doc.createElement("porCuentaPropia");
        cuentaPropiaElement.appendChild(doc.createTextNode("SI"));
        detallesElement.appendChild(cuentaPropiaElement);

        Element situacionPreviaElement = doc.createElement("situacionPrevia");
        if((short)11 == solicitudincorporacion.getIdmodalidaddocumentacion()){
            situacionPreviaElement.appendChild(doc.createTextNode("1"));
        } else if((short)12 == solicitudincorporacion.getIdmodalidaddocumentacion()){
            situacionPreviaElement.appendChild(doc.createTextNode("2"));
        } else if((short)13 == solicitudincorporacion.getIdmodalidaddocumentacion()){
            situacionPreviaElement.appendChild(doc.createTextNode("3"));
        }
        detallesElement.appendChild(situacionPreviaElement);

        Element idiomaElement = doc.createElement("idioma");
        idiomaElement.appendChild(doc.createTextNode("es"));
        detallesElement.appendChild(idiomaElement);

        Element emailElement = doc.createElement("emailContacto");
        emailElement.appendChild(doc.createTextNode(solicitudincorporacion.getCorreoelectronico()));
        detallesElement.appendChild(emailElement);

        //Ya no se pasa
        /*Element coberturaElement = doc.createElement("coberturaSocial");
        coberturaElement.appendChild(doc.createTextNode("RT"));
        detallesElement.appendChild(coberturaElement);*/

        if(solicitudincorporacion.getIdestadocivil() != null){
            String cdgoEstadoCivil = cenEstadocivilExtendsMapper.selectByPrimaryKey(solicitudincorporacion.getIdestadocivil()).getCodigoejis().substring(0,1);
            Element estadoCivilElement = doc.createElement("estadoCivil");
            estadoCivilElement.appendChild(doc.createTextNode(cdgoEstadoCivil));
            detallesElement.appendChild(estadoCivilElement);
        }

        Element autorizaCesionElement = doc.createElement("autorizaCesion");
        autorizaCesionElement.appendChild(doc.createTextNode(SigaConstants.NO));
        detallesElement.appendChild(autorizaCesionElement);

        Element cniElement = doc.createElement("solicitarCNI");
        cniElement.appendChild(doc.createTextNode(SigaConstants.NO));
        detallesElement.appendChild(cniElement);

        Element reincorpElement = doc.createElement("reincorporacion");
        if((short)60 == solicitudincorporacion.getIdtiposolicitud()){
            reincorpElement.appendChild(doc.createTextNode(SigaConstants.SI));
        } else if((short)50 == solicitudincorporacion.getIdtiposolicitud()){
            reincorpElement.appendChild(doc.createTextNode(SigaConstants.NO));
        }
        detallesElement.appendChild(reincorpElement);
        //NODO DETALLES - FIN
        //NODO CUENTA BANCARIA - INICIO
        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getIban())){
            Element cuentaElement = doc.createElement("cuentaBancaria");
            rootElement.appendChild(cuentaElement);

            Element tipoCuentaElement = doc.createElement("tipoCuenta");
            tipoCuentaElement.appendChild(doc.createTextNode("ES"));
            cuentaElement.appendChild(tipoCuentaElement);

            Element ibanElement = doc.createElement("IBAN");
            ibanElement.appendChild(doc.createTextNode(solicitudincorporacion.getIban()));
            cuentaElement.appendChild(ibanElement);

            Element bicElement = doc.createElement("BIC");
            if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getBic())) {
                bicElement.appendChild(doc.createTextNode(solicitudincorporacion.getBic()));
            }
            cuentaElement.appendChild(bicElement);

            Element titularElement = doc.createElement("titularCuenta");
            if(UtilidadesString.esCadenaVacia(solicitudincorporacion.getApellido2())){
                titularElement.appendChild(doc.createTextNode(solicitudincorporacion.getNombre() + " " + solicitudincorporacion.getApellido1()));
            }else{
                titularElement.appendChild(doc.createTextNode(solicitudincorporacion.getNombre() + " " + solicitudincorporacion.getApellido1() + " " + solicitudincorporacion.getApellido2()));
            }
            cuentaElement.appendChild(titularElement);

            Element abonosElement = doc.createElement("checkAbonos");
            abonosElement.appendChild(doc.createTextNode(SigaConstants.SI));
            cuentaElement.appendChild(abonosElement);

            Element turnoElement = doc.createElement("checkTurno");
            turnoElement.appendChild(doc.createTextNode(SigaConstants.SI));
            cuentaElement.appendChild(turnoElement);
        }
        //NODO CUENTA BANCARIA - FIN

        //NODO DOMICILIO - INICIO
        Element domicilioElement = doc.createElement("domicilioProfesional");
        rootElement.appendChild(domicilioElement);

        //NODO DIRECCION POSTAL - INICIO
        Element postalElement = doc.createElement("direccionPostal");
        domicilioElement.appendChild(postalElement);

        Element codPaisElement = doc.createElement("codPais");
        codPaisElement.appendChild(doc.createTextNode(cdgoExtPais));
        postalElement.appendChild(codPaisElement);

        CenProvincias cenProvincias = cenProvinciasExtendsMapper.selectByPrimaryKey(solicitudincorporacion.getIdprovincia());
        String codExtComAut = cenComunidadesautonomasMapper.selectByPrimaryKey(cenProvincias.getIdcomunidadautonoma()).getCodigoext();
        Element codComunidadElement = doc.createElement("codComunidad");
        codComunidadElement.appendChild(doc.createTextNode(codExtComAut));
        postalElement.appendChild(codComunidadElement);

        String codExtProvincia = cenProvincias.getCodigoext();
        Element codProvinciaElement = doc.createElement("codProvincia");
        codProvinciaElement.appendChild(doc.createTextNode(codExtProvincia));
        postalElement.appendChild(codProvinciaElement);

        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getIdpoblacion())) {
            String descPoblacion = cenPoblacionesExtendsMapper.selectByPrimaryKey(solicitudincorporacion.getIdpoblacion()).getNombre();
            Element localidadElement = doc.createElement("localidad");
            localidadElement.appendChild(doc.createTextNode(descPoblacion));
            postalElement.appendChild(localidadElement);
        }

        Element codPostalElement = doc.createElement("codigoPostal");
        codPostalElement.appendChild(doc.createTextNode(solicitudincorporacion.getCodigopostal()));
        postalElement.appendChild(codPostalElement);

        /*Element tipoViaElement = doc.createElement("tipoVia");
        tipoViaElement.appendChild(doc.createTextNode("CL"));
        postalElement.appendChild(tipoViaElement);*/

        Element nombreViaElement = doc.createElement("nombreVia");
        nombreViaElement.appendChild(doc.createTextNode(solicitudincorporacion.getDomicilio())); //FIXME
        postalElement.appendChild(nombreViaElement);

        /*Element numeroViaElement = doc.createElement("numeroVia");
        numeroViaElement.appendChild(doc.createTextNode("1")); //FIXME
        postalElement.appendChild(numeroViaElement);

        Element restoDirElement = doc.createElement("restoDir");
        restoDirElement.appendChild(doc.createTextNode("1 A")); //FIXME
        postalElement.appendChild(restoDirElement);*/
        //NODO DIRECCION POSTAL - FIN

        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getTelefono1())
            || !UtilidadesString.esCadenaVacia(solicitudincorporacion.getTelefono2())) {
            //NODO TLF FIJO - INICIO

            Element tlfFijoElement = doc.createElement("telefonoFijo");
            domicilioElement.appendChild(tlfFijoElement);

            Element numeroFijoElement = doc.createElement("numeroFijo");
            if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getTelefono1())){
                numeroFijoElement.appendChild(doc.createTextNode(solicitudincorporacion.getTelefono1()));
            }else{
                numeroFijoElement.appendChild(doc.createTextNode(solicitudincorporacion.getTelefono2()));
            }

            tlfFijoElement.appendChild(numeroFijoElement);

            Element publFijoElement = doc.createElement("publicarFijo");
            publFijoElement.appendChild(doc.createTextNode(SigaConstants.SI));
            tlfFijoElement.appendChild(publFijoElement);
            //NODO TLF FIJO - FIN
        }

        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getMovil())){
            //NODO MOVIL - INICIO
            Element tlfMovilElement = doc.createElement("telefonoMovil");
            domicilioElement.appendChild(tlfMovilElement);

            Element numeroMovilElement = doc.createElement("numeroMovil");
            numeroMovilElement.appendChild(doc.createTextNode(solicitudincorporacion.getMovil()));
            tlfMovilElement.appendChild(numeroMovilElement);

            Element publMovilElement = doc.createElement("publicarMovil");
            publMovilElement.appendChild(doc.createTextNode(SigaConstants.SI));
            tlfMovilElement.appendChild(publMovilElement);
            //NODO MOVIL - FIN
        }

        if(!UtilidadesString.esCadenaVacia(solicitudincorporacion.getCorreoelectronico())){
            //NODO EMAIL - INICIO
            Element emailParentElement = doc.createElement("email");
            domicilioElement.appendChild(emailParentElement);

            Element direccionEmailElement = doc.createElement("direccionEmail");
            direccionEmailElement.appendChild(doc.createTextNode(solicitudincorporacion.getCorreoelectronico()));
            emailParentElement.appendChild(direccionEmailElement);

            Element publEmailElement = doc.createElement("publicarEmail");
            publEmailElement.appendChild(doc.createTextNode(SigaConstants.SI));
            emailParentElement.appendChild(publEmailElement);
            //NODO EMAL - FIN
        }
        //NODO DOMICILIO - FIN
        //TODO NODO tituloHabilitante?
        StringWriter writer = new StringWriter();

        transformer.transform(new DOMSource(doc), new StreamResult(writer));


        return writer.getBuffer().toString();
    }
}
