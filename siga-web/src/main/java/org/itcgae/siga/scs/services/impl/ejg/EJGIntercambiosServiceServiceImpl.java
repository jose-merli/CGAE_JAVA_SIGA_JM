package org.itcgae.siga.scs.services.impl.ejg;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosDTO;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
import org.itcgae.siga.db.entities.ScsDocumentacionejgKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.ecom.mappers.EcomIntercambioExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IEJGIntercambiosService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EJGIntercambiosServiceServiceImpl implements IEJGIntercambiosService {

    private Logger LOGGER = Logger.getLogger(EJGIntercambiosServiceServiceImpl.class);

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    private ScsEstadoejgMapper scsEstadoejgMapper;

    @Autowired
    private ScsDocumentacionejgMapper scsDocumentacionejgMapper;

    @Autowired
    private EcomIntercambioExtendsMapper ecomIntercambioExtendsMapper;

    @Autowired
    private EJGIntercambiosHelper ejgIntercambiosHelper;

    @Override
    public ResponseDataDTO esColegioZonaComun(HttpServletRequest request) throws Exception {
        LOGGER.info("esColegioZonaComun() -> Entrando al servicio que comprueba si el colegio pertenece a la zona común y está configurado para la integración con Pericles");

        ResponseDataDTO responseDataDTO = new ResponseDataDTO();

        // Conseguimos información del usuario logeado
        LOGGER.info("esColegioZonaComun() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
        LOGGER.info("esColegioZonaComun() <- Saliendo del servicio de autenticación");

        if (usuario.getIdinstitucion() == null) {
            LOGGER.warn("esColegioZonaComun() -> Error: no se ha introducido una idInstitucion válida");
            throw new Exception("No se ha introducido una idInstitucion válida");
        }

        Boolean esZonaComun = ejgIntercambiosHelper.isColegioZonaComun(usuario.getIdinstitucion())
                && ejgIntercambiosHelper.isColegioConfiguradoEnvioPericles(usuario.getIdinstitucion());
        responseDataDTO.setData(esZonaComun.toString());

        LOGGER.info("esColegioZonaComun() <- Saliendo del servicio que comprueba si el colegio pertenece a la zona común y está configurado para la integración con Pericles");

        return responseDataDTO;
    }

    @Override
    public EjgListaIntercambiosDTO getListadoIntercambiosAltaEJG(EjgItem item, HttpServletRequest request) throws Exception {
        LOGGER.info("getListadoIntercambiosAltaEJG() -> Entrando al servicio para obtener el listado de intercambio de Alta EJG");

        EjgListaIntercambiosDTO ejgListaIntercambiosDTO = new EjgListaIntercambiosDTO();

        // Conseguimos información del usuario logeado
        LOGGER.info("getListadoIntercambiosAltaEJG() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
        LOGGER.info("getListadoIntercambiosAltaEJG() <- Saliendo del servicio de autenticación");

        Short idInstitucion = Short.parseShort(item.getidInstitucion());
        if (!ejgIntercambiosHelper.isColegioZonaComun(idInstitucion)
                || !ejgIntercambiosHelper.isColegioConfiguradoEnvioPericles(idInstitucion)) {
            LOGGER.warn("getListadoIntercambiosAltaEJG() -> Error: El colegio con idInstitucion=" + idInstitucion + " no pertenece a la zona común");
            throw new Exception("El colegio no pertenece a la zona común");
        }

        if (!UtilidadesString.anyMatchCadenaVacia(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero())) {
            LOGGER.info("getListadoIntercambiosAltaEJG() -> Entrando al servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosAltaEJG()");
            List<EjgListaIntercambiosItem> intercambios = ecomIntercambioExtendsMapper.getListadoIntercambiosAltaEJG(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero());
            ejgListaIntercambiosDTO.setEjgListaIntercambiosItems(intercambios);
            LOGGER.info("getListadoIntercambiosAltaEJG() <- Saliendo del servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosAltaEJG()");
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
        LOGGER.info("getListadoIntercambiosDocumentacionEJG() <- Saliendo del servicio de autenticación");

        Short idInstitucion = Short.parseShort(item.getidInstitucion());
        if (!ejgIntercambiosHelper.isColegioZonaComun(idInstitucion) || !ejgIntercambiosHelper.isColegioConfiguradoEnvioPericles(idInstitucion)) {
            LOGGER.warn("getListadoIntercambiosDocumentacionEJG() -> El colegio con idInstitucion=" + idInstitucion + " no pertenece a la zona común");
            throw new Exception("El colegio no pertenece a la zona común");
        }

        if (!UtilidadesString.anyMatchCadenaVacia(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero())) {
            LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Entrando al servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosDocumentacionEJG()");
            List<EjgListaIntercambiosItem> intercambios = ecomIntercambioExtendsMapper.getListadoIntercambiosDocumentacionEJG(item.getidInstitucion(), item.getAnnio(), item.getTipoEJG(), item.getNumero());
            ejgListaIntercambiosDTO.setEjgListaIntercambiosItems(intercambios);
            LOGGER.info("getListadoIntercambiosDocumentacionEJG() <- Saliendo del servicio de ecomIntercambioExtendsMapper.getListadoIntercambiosDocumentacionEJG()");
        } else {
            LOGGER.warn("getListadoIntercambiosDocumentacionEJG() -> Error: Alguno de los parámetros no es correcto");
            throw new Exception("Faltan parametros para buscar la lista de intercambios");
        }

        LOGGER.info("getListadoIntercambiosDocumentacionEJG() -> Saliendo del servicio para obtener el listado de intercambio de Documentación EJG");
        return ejgListaIntercambiosDTO;
    }

    @Override
    public UpdateResponseDTO consultarEstadoPericles(EjgItem ejgItem, HttpServletRequest request) throws Exception {
        LOGGER.info("consultarEstadoPericles() -> Entrando al servicio para pedir la consulta del estado de Pericles");

        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

        // Conseguimos información del usuario logeado
        LOGGER.info("consultarEstadoPericles() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
        LOGGER.info("consultarEstadoPericles() <- Entrando al servicio de autenticación");


        if (UtilidadesString.anyMatchCadenaVacia(ejgItem.getidInstitucion(), ejgItem.getAnnio(), ejgItem.getTipoEJG(), ejgItem.getNumero())) {
            LOGGER.warn("consultarEstadoPericles() -> Error: Falta alguno de los parámetros necesarios para realizar la petición");
            throw new Exception("Falta alguno de los parámetros necesarios para realizar la petición");
        }

        ScsEjg ejg = ejgIntercambiosHelper.getScsEjg(Short.parseShort(ejgItem.getidInstitucion()),
                Short.parseShort(ejgItem.getAnnio()), Short.parseShort(ejgItem.getTipoEJG()), Long.parseLong(ejgItem.getNumero()));
        if (ejg == null) {
            LOGGER.warn("consultarEstadoPericles() -> Error: No se ha encontrado el EJG indicado");
            throw new Exception(("No se ha encontrado el EJG indicado"));
        }

        if (!ejgIntercambiosHelper.isColegioZonaComun(ejg.getIdinstitucion())
                || !ejgIntercambiosHelper.isColegioConfiguradoEnvioPericles(ejg.getIdinstitucion())) {
            LOGGER.warn("consultarEstadoPericles() -> Error: El colegio no pertenece a la zona común");
            throw new Exception("El colegio no pertenece a la zona común");
        }

        ScsEstadoejgExample estadoejgExample = new ScsEstadoejgExample();
        estadoejgExample.createCriteria().andIdinstitucionEqualTo(ejg.getIdinstitucion())
                .andAnioEqualTo(ejg.getAnio()).andIdtipoejgEqualTo(ejg.getIdtipoejg())
                .andNumeroEqualTo(ejg.getNumero());
        estadoejgExample.setOrderByClause("IDESTADOPOREJG");

        List<ScsEstadoejg> estados = scsEstadoejgMapper.selectByExample(estadoejgExample);
        if (estados == null || estados.size() == 0
                || !estados.get(estados.size() - 1).getIdestadoejg().equals(SigaConstants.ESTADOS_EJG.REMITIDO_COMISION.getCodigo())) {
            LOGGER.warn("consultarEstadoPericles() -> Error: Estado incorrecto");
            throw new Exception("El EJG tiene un estado incorrecto");
        }

        // ejgIntercambiosHelper.insertarConsultaEstadoEnCola(ejg);

        LOGGER.info("consultarEstadoPericles() <- Saliendo del servicio para pedir la consulta del estado de Pericles");
        return updateResponseDTO;
    }

    @Override
    public UpdateResponseDTO enviaDocumentacionAdicional(EjgDocumentacionItem documentacionItem, HttpServletRequest request) throws Exception {
        LOGGER.info("enviaDocumentacionAdicional() -> Entrando al servicio para enviar documentación adicional");

        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

        // Conseguimos información del usuario logeado
        LOGGER.info("enviaDocumentacionAdicional() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
        LOGGER.info("enviaDocumentacionAdicional() -> Saliendo del servicio de autenticación");

        if (UtilidadesString.anyMatchCadenaVacia(documentacionItem.getAnio(), documentacionItem.getIdTipoEjg(), documentacionItem.getNumero())
                || documentacionItem.getIdDocumentacion() == null) {
            LOGGER.warn("enviaDocumentacionAdicional() -> Error: Falta alguno de los parámetros necesarios para realizar la petición");
            throw new Exception("Falta alguno de los parámetros necesarios para realizar la petición");
        }

        ScsEjg ejg = ejgIntercambiosHelper.getScsEjg(usuario.getIdinstitucion(),
                Short.parseShort(documentacionItem.getAnio()), Short.parseShort(documentacionItem.getIdTipoEjg()),
                Long.parseLong(documentacionItem.getNumero()));
        if (ejg == null) {
            LOGGER.warn("enviaDocumentacionAdicional() -> Error: No se ha encontrado el EJG indicado");
            throw new Exception("No se ha encontrado el EJG indicado");
        }

        if (!ejgIntercambiosHelper.isColegioZonaComun(ejg.getIdinstitucion())
                || !ejgIntercambiosHelper.isColegioConfiguradoEnvioPericles(ejg.getIdinstitucion())) {
            LOGGER.warn("enviaDocumentacionAdicional() -> Error: El colegio no pertenece a la zona común");
            throw new Exception("El colegio no pertenece a la zona común");
        }

        if (ejg.getIdexpedienteext() == null) {
            LOGGER.warn("enviaDocumentacionAdicional() -> Error: El EJG no tiene el identificador de Pericles");
            throw new Exception("El EJG no tiene el identificador de Pericles");
        }

        ScsDocumentacionejgKey documentacionejgKey = new ScsDocumentacionejgKey();
        documentacionejgKey.setIdinstitucion(usuario.getIdinstitucion());
        documentacionejgKey.setIddocumentacion(documentacionItem.getIdDocumentacion().intValue());
        ScsDocumentacionejg documentacionejg = scsDocumentacionejgMapper.selectByPrimaryKey(documentacionejgKey);

        if (documentacionejg == null) {
            LOGGER.warn("enviaDocumentacionAdicional() -> Error: No existe la documentación de EJG indicada");
            throw new Exception("No existe la documentación de EJG indicada");
        }

        ejgIntercambiosHelper.insertarDocumentacionAdicionalEnCola(documentacionejg);

        LOGGER.info("enviaDocumentacionAdicional() <- Saliendo del servicio para enviar documentación adicional");
        return updateResponseDTO;
    }

}
