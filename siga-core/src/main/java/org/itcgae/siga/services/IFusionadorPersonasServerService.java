package org.itcgae.siga.services;


import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasRequestDocument;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasResponseDocument;

public interface IFusionadorPersonasServerService {
	public GetFusionadorPersonasResponseDocument peticionServicio(String endpointReference, GetFusionadorPersonasRequestDocument peticionEntrada);
}
