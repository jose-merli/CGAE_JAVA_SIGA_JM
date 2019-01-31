package org.itcgae.siga.ws.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;

import samples.servicemodel.microsoft.EstadoMutualistaDocument;
import samples.servicemodel.microsoft.EstadoMutualistaResponseDocument;
import samples.servicemodel.microsoft.EstadoSolicitudDocument;
import samples.servicemodel.microsoft.EstadoSolicitudResponseDocument;
import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.GetEnumsResponseDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosResponseDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalResponseDocument;
import samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoDocument;
import samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoResponseDocument;

public class MutualidadClient extends WebServiceGatewaySupport {

    public GetEnumsResponseDocument getMutualidad(GetEnumsDocument request) throws URISyntaxException {
    	 
        ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums"));

        return (GetEnumsResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
    
    
    public ObtenerCuotaYCapObjetivoResponseDocument getCuotaYCapObjetivo(ObtenerCuotaYCapObjetivoDocument request) throws URISyntaxException {

    	ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/ObtenerCuotaYCapObjetivo"));
        return (ObtenerCuotaYCapObjetivoResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
    
    public EstadoMutualistaResponseDocument getEstadoMutualista(EstadoMutualistaDocument request) throws URISyntaxException {

    	ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/EstadoMutualista"));
        return (EstadoMutualistaResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
    
    public EstadoSolicitudResponseDocument getEstadoSolicitud(EstadoSolicitudDocument request) throws URISyntaxException {

    	ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/EstadoSolicitud"));
        return (EstadoSolicitudResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
    
    public MGASolicitudPolizaAccuGratuitosResponseDocument mGASolicitudPolizaAccuGratuitos(MGASolicitudPolizaAccuGratuitosDocument request) throws URISyntaxException {

    	ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/MGASolicitudPolizaAccuGratuitos"));
        return (MGASolicitudPolizaAccuGratuitosResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
    
    public MGASolicitudPolizaProfesionalResponseDocument mGASolicitudPolizaProfesional(MGASolicitudPolizaProfesionalDocument request) throws URISyntaxException {

    	ActionCallback callback = new ActionCallback(
                new URI("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/MGASolicitudPolizaProfesional"));
        return (MGASolicitudPolizaProfesionalResponseDocument) getWebServiceTemplate()
                .marshalSendAndReceive(request,callback);
    }
    
}