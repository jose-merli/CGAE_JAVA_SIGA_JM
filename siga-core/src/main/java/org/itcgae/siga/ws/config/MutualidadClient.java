package org.itcgae.siga.ws.config;

import java.net.URISyntaxException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

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

    public GetEnumsResponseDocument getMutualidad(String uriService, GetEnumsDocument request) throws URISyntaxException {
        return (GetEnumsResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    
    public ObtenerCuotaYCapObjetivoResponseDocument getCuotaYCapObjetivo(String uriService, ObtenerCuotaYCapObjetivoDocument request) throws URISyntaxException {
        return (ObtenerCuotaYCapObjetivoResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public EstadoMutualistaResponseDocument getEstadoMutualista(String uriService, EstadoMutualistaDocument request) throws URISyntaxException {
        return (EstadoMutualistaResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public EstadoSolicitudResponseDocument getEstadoSolicitud(String uriService, EstadoSolicitudDocument request) throws URISyntaxException {
        return (EstadoSolicitudResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public MGASolicitudPolizaAccuGratuitosResponseDocument mGASolicitudPolizaAccuGratuitos(String uriService, MGASolicitudPolizaAccuGratuitosDocument request) throws URISyntaxException {
        return (MGASolicitudPolizaAccuGratuitosResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public MGASolicitudPolizaProfesionalResponseDocument mGASolicitudPolizaProfesional(String uriService, MGASolicitudPolizaProfesionalDocument request) throws URISyntaxException {
        return (MGASolicitudPolizaProfesionalResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
}