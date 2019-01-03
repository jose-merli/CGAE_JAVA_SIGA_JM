package org.itcgae.siga.ws.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;

import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.GetEnumsResponseDocument;

public class MutualidadClient extends WebServiceGatewaySupport {

    public GetEnumsResponseDocument getMutualidad(GetEnumsDocument request) throws URISyntaxException {
    	 
        //request.setId(id);

        ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums"));

        return (GetEnumsResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
}