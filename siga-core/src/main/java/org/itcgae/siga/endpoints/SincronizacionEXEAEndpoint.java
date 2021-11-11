package org.itcgae.siga.endpoints;

import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionRequestDocument;
import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionResponseDocument;
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
    ISincronizacionEXEAService service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerNumColegiacionRequest")
    @ResponsePayload
    public ObtenerNumColegiacionResponseDocument obtenerNumColegiacion(@RequestPayload ObtenerNumColegiacionRequestDocument peticion) {
        LOGGER.info("Entra en ws getListaSociedades");

        TransportContext ctx = TransportContextHolder.getTransportContext();

        try {
            HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
            ipCliente = connection.getHttpServletRequest().getRemoteAddr();
            LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

        }catch (Exception e){
            LOGGER.info("Imposible obtener la url de conexion de acceso a obtenerNumColegiacion");
        }
        return service.getNumColegiacion(peticion, ipCliente);
    }
}
