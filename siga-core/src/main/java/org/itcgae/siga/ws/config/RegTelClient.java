package org.itcgae.siga.ws.config;

import org.redabogacia.regtel.ws.eregtel.ConsultaAsientoDocument;
import org.redabogacia.regtel.ws.eregtel.ConsultaAsientoResponseDocument;
import org.redabogacia.regtel.ws.eregtel.RegistroEntradaDocument;
import org.redabogacia.regtel.ws.eregtel.RegistroEntradaResponseDocument;
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
}
