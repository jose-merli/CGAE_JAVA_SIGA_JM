package org.itcgae.siga.services;

import org.itcgae.sspp.ws.registrosociedades.GetListaSociedadesRequestDocument;
import org.itcgae.sspp.ws.registrosociedades.GetListaSociedadesResponseDocument;

public interface ISociedadesServerSevice {
	public GetListaSociedadesResponseDocument peticionServicio(String endpointReference, GetListaSociedadesRequestDocument peticionEntrada);
}
