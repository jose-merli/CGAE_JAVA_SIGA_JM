package org.itcgae.siga.ws.config;

import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeRequestDocument;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeResponseDocument;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;
import samples.servicemodel.microsoft.GetEnumsResponseDocument;

import java.net.URI;

public class TokenEXEAClient extends WebServiceGatewaySupport {

    public AutenticarUsuarioSedeResponseDocument autenticarUsuarioSede (AutenticarUsuarioSedeRequestDocument request, String url){

        return (AutenticarUsuarioSedeResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }
}
