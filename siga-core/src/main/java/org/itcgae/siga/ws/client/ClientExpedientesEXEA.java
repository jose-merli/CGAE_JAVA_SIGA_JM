package org.itcgae.siga.ws.client;

import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeRequestDocument;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeResponseDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteResponseDocument;
import org.apache.log4j.Logger;
import org.itcgae.siga.ws.config.AddInEXEAClient;
import org.itcgae.siga.ws.config.TokenEXEAClient;
import org.itcgae.siga.ws.config.WebServiceClientConfigAddInEXEA;
import org.itcgae.siga.ws.config.WebServiceClientConfigTokenEXEA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


@Component
public class ClientExpedientesEXEA extends WebServiceGatewaySupport {

    private static final Logger LOGGER = Logger.getLogger(ClientExpedientesEXEA.class);


    /**
     * Bean con la configuración básica del webServiceTemplate
     */
    @Autowired
    protected WebServiceTemplate webServiceTemplate;

    public AutenticarUsuarioSedeResponseDocument autenticarUsuarioEXEA (AutenticarUsuarioSedeRequestDocument request, String uriService){

        AutenticarUsuarioSedeResponseDocument response;

        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfigTokenEXEA.class))
        {
            TokenEXEAClient client = context.getBean(TokenEXEAClient.class);
            response = client.autenticarUsuarioSede(request, uriService);


        }catch(Exception e) {
            LOGGER.error("ClientExpedientesEXEA -> Error al obtener el token de EXEA: " + e.getMessage(), e);
            throw e;
        }

        return response;
    }

    public GetExpedienteResponseDocument getDetalleExpedienteEXEA (GetExpedienteDocument request, String uriService){

        GetExpedienteResponseDocument response;

        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfigAddInEXEA.class))
        {
            AddInEXEAClient client = context.getBean(AddInEXEAClient.class);
            response = client.getDetalleExpediente(request, uriService);

        }catch(Exception e) {
            LOGGER.error("ClientExpedientesEXEA -> Error al obtener el detalle del expediente: " + e.getMessage(), e);
            throw e;
        }

        return response;
    }
}
