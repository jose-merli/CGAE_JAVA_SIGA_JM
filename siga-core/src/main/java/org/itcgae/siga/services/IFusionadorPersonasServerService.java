package org.itcgae.siga.services;


import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasResponseDocument;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasRequestDocument;

public interface IFusionadorPersonasServerService {
	public GetFusionadorPersonasResponseDocument peticionServicio(String endpointReference, GetFusionadorPersonasRequestDocument peticionEntrada);
}
