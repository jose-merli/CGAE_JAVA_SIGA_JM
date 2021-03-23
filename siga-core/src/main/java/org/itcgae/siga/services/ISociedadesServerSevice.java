package org.itcgae.siga.services;

import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesRequestDocument;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesResponseDocument;

public interface ISociedadesServerSevice {
	public GetListaSociedadesResponseDocument peticionServicio(String endpointReference, GetListaSociedadesRequestDocument peticionEntrada);
}
