package org.itcgae.siga.ws.config;


import ieci.tdw.ispac.services.ws.server.GetExpedienteDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteResponseDocument;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class AddInEXEAClient extends WebServiceGatewaySupport {

    public GetExpedienteResponseDocument getDetalleExpediente (GetExpedienteDocument request, String url){

        return (GetExpedienteResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }

}
