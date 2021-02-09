package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DestinatariosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IComunicacionesService {

	
	public ComboDTO claseComunicacion(HttpServletRequest request);
	public ComboDTO modelosClasesComunicacion(HttpServletRequest request, String idClaseComuncicacion);
	public EnviosMasivosDTO comunicacionesSearch(HttpServletRequest request, EnviosMasivosSearch filtros);
	public Error reenviar(HttpServletRequest request, EnviosMasivosItem[] envio);
	public DestinatariosDTO detalleDestinatarios(HttpServletRequest request,String idEnvio);
	public ResponseFileDTO descargarDocumento(HttpServletRequest request, ResponseDocumentoDTO documentoDTO);
	public String descargarCertificado(HttpServletRequest request, String idEnvio);
	
}
