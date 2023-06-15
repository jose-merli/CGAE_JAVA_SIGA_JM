package org.itcgae.siga.ws.config;

import org.apache.axis2.context.OperationContext;
import org.apache.axis2.wsdl.WSDLConstants;
import org.redabogacia.regtel.ws.eregtel.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class RegTelClient extends WebServiceGatewaySupport{

    public RegistroEntradaResponseDocument iniciarExpedienteColegiacion (RegistroEntradaDocument request, String url){

        return (RegistroEntradaResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }

    public ConsultaAsientoResponseDocument consultarExpediente (ConsultaAsientoDocument request, String url){

        return (ConsultaAsientoResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }

    public ConsultaAdjuntoResponseDocument getJustificante (ConsultaAdjuntoDocument request, String url){

        return (ConsultaAdjuntoResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(url, request);
    }
}
