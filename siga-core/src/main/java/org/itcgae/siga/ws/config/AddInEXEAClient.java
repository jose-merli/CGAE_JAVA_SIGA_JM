package org.itcgae.siga.ws.config;


import ieci.tdw.ispac.services.ws.server.BusquedaAvanzadaDocument;
import ieci.tdw.ispac.services.ws.server.BusquedaAvanzadaResponseDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteResponseDocument;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class AddInEXEAClient extends WebServiceGatewaySupport {

    public GetExpedienteResponseDocument getDetalleExpediente (GetExpedienteDocument request, String url){

        return (GetExpedienteResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }

    public BusquedaAvanzadaResponseDocument getExpedientesEXEAPersonalColegio (BusquedaAvanzadaDocument request, String url){

        return (BusquedaAvanzadaResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }

}
