package org.itcgae.siga.ws.client;

import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeRequestDocument;
import es.cgae.consultatramites.token.schema.AutenticarUsuarioSedeResponseDocument;
import ieci.tdw.ispac.services.ws.server.BusquedaAvanzadaDocument;
import ieci.tdw.ispac.services.ws.server.BusquedaAvanzadaResponseDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteDocument;
import ieci.tdw.ispac.services.ws.server.GetExpedienteResponseDocument;
import org.apache.log4j.Logger;
import org.itcgae.siga.ws.config.*;
import org.redabogacia.regtel.ws.eregtel.ConsultaAsientoDocument;
import org.redabogacia.regtel.ws.eregtel.ConsultaAsientoResponseDocument;
import org.redabogacia.regtel.ws.eregtel.RegistroEntradaDocument;
import org.redabogacia.regtel.ws.eregtel.RegistroEntradaResponseDocument;
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

    public BusquedaAvanzadaResponseDocument getExpedientesEXEAPersonalColegio (BusquedaAvanzadaDocument request, String uriService){

        BusquedaAvanzadaResponseDocument response;

        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfigAddInEXEA.class))
        {
            AddInEXEAClient client = context.getBean(AddInEXEAClient.class);
            response = client.getExpedientesEXEAPersonalColegio(request, uriService);

        }catch(Exception e) {
            LOGGER.error("ClientExpedientesEXEA -> Error al obtener el detalle del expediente: " + e.getMessage(), e);
            throw e;
        }

        return response;
    }

    public RegistroEntradaResponseDocument iniciarExpedienteColegiacion (RegistroEntradaDocument request, String uriService){

        RegistroEntradaResponseDocument response;

        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfigRegTel.class))
        {
            RegTelClient client = context.getBean(RegTelClient.class);
            response = client.iniciarExpedienteColegiacion(request, uriService);

        }catch(Exception e) {
            LOGGER.error("ClientExpedientesEXEA -> Error al iniciar el expediente: " + e.getMessage(), e);
            throw e;
        }

        return response;
    }

    public ConsultaAsientoResponseDocument consultaExpediente (ConsultaAsientoDocument request, String uriService){

        ConsultaAsientoResponseDocument response;

        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfigRegTel.class))
        {
            RegTelClient client = context.getBean(RegTelClient.class);
            response = client.consultarExpediente(request,uriService);

        }catch(Exception e) {
            LOGGER.error("ClientExpedientesEXEA -> Error al consultar el expediente: " + e.getMessage(), e);
            throw e;
        }

        return response;
    }
}
