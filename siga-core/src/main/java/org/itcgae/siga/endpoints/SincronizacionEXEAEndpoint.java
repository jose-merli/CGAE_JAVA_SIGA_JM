package org.itcgae.siga.endpoints;

import com.exea.sincronizacion.redabogacia.*;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.services.ISincronizacionEXEAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

@Endpoint
public class SincronizacionEXEAEndpoint {

    private static final String NAMESPACE_URI = "urn:redabogacia.sincronizacion.exea.com";

    Logger LOGGER = LoggerFactory.getLogger(SincronizacionEXEAEndpoint.class);

    private String ipCliente = "";

    @Autowired
    private ISincronizacionEXEAService service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerNumColegiacionRequest")
    @ResponsePayload
    public ObtenerNumColegiacionResponseDocument obtenerNumColegiacion(@RequestPayload ObtenerNumColegiacionRequestDocument peticion) {
        LOGGER.info("Entra en ws obtenerNumColegiacion");

        TransportContext ctx = TransportContextHolder.getTransportContext();

        try {
            HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
            ipCliente = connection.getHttpServletRequest().getRemoteAddr();
            LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

        }catch (Exception e){
            LOGGER.error("Imposible obtener la url de conexion de acceso a obtenerNumColegiacion");
        }
        return service.getNumColegiacion(peticion, ipCliente);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "altaColegiadoRequest")
    @ResponsePayload
    public AltaColegiadoResponseDocument altaColegiado(@RequestPayload AltaColegiadoRequestDocument peticion) throws SigaExceptions {
        LOGGER.info("Entra en ws aprobarAltaColegiado");

        TransportContext ctx = TransportContextHolder.getTransportContext();

        try {
            HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
            ipCliente = connection.getHttpServletRequest().getRemoteAddr();
            LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

        }catch (Exception e){
            LOGGER.error("Imposible obtener la url de conexion de acceso a aprobarAltaColegiado");
        }
        return service.aprobarAltaColegiado(peticion, ipCliente);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "altaSancionRequest")
    @ResponsePayload
    public AltaSancionResponseDocument altaSancion(@RequestPayload AltaSancionRequestDocument peticion) throws SigaExceptions {
        LOGGER.info("Entra en ws altaSancion");

        TransportContext ctx = TransportContextHolder.getTransportContext();

        try {
            HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
            ipCliente = connection.getHttpServletRequest().getRemoteAddr();
            LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

        }catch (Exception e){
            LOGGER.error("Imposible obtener la url de conexion de acceso a altaSancion");
        }
        return service.altaSancion(peticion, ipCliente);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEstadoExpedienteRequest")
    @ResponsePayload
    public UpdateEstadoExpedienteResponseDocument updateEstadoExpediente(@RequestPayload UpdateEstadoExpedienteRequestDocument peticion) throws SigaExceptions {
        LOGGER.info("Entra en ws updateEstadoExpediente");

        TransportContext ctx = TransportContextHolder.getTransportContext();

        try {
            HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
            ipCliente = connection.getHttpServletRequest().getRemoteAddr();
            LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

        }catch (Exception e){
            LOGGER.error("Imposible obtener la url de conexion de acceso a updateEstadoExpediente");
        }
        return service.updateEstadoExpediente(peticion, ipCliente);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "actualizacionSancionRequest")
    @ResponsePayload
    public ActualizacionSancionResponseDocument actualizarSancion(@RequestPayload ActualizacionSancionRequestDocument peticion) throws SigaExceptions {
        LOGGER.info("Entra en ws actualizarSancion");

        TransportContext ctx = TransportContextHolder.getTransportContext();

        try {
            HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
            ipCliente = connection.getHttpServletRequest().getRemoteAddr();
            LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

        }catch (Exception e){
            LOGGER.error("Imposible obtener la url de conexion de acceso a actualizarSancion");
        }
        return service.actualizarSancion(peticion, ipCliente);
    }


}
