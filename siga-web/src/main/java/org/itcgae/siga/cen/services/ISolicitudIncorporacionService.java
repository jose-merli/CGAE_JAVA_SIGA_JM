package org.itcgae.siga.cen.services;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISolicitudIncorporacionService {
	
	
	public ComboDTO getTipoSolicitud(HttpServletRequest request);
	public ComboDTO getEstadoSolicitud(HttpServletRequest request);
	public ComboDTO getTratamiento(HttpServletRequest request);
	public ComboDTO getEstadoCivil(HttpServletRequest request);
	public ComboDTO getTipoIdentificacion(HttpServletRequest request);
	public ComboDTO getTipoColegiacion(HttpServletRequest request);
	public ComboDTO getModalidadDocumentacion(HttpServletRequest request);
	public SolIncorporacionDTO datosSolicitudSearch (int numPagina, SolicitudIncorporacionSearchDTO datosSolicitudSearchDTO, HttpServletRequest request);
	public InsertResponseDTO guardarSolicitudIncorporacion (SolIncorporacionItem SolIncorporacionDTO, HttpServletRequest request);
	public InsertResponseDTO aprobarSolicitud (Long idSolicitud, HttpServletRequest request);
	public InsertResponseDTO denegarsolicitud (Long idSolicitud, HttpServletRequest request);
	 public SolIncorporacionItem numColegiadoSearch(SolIncorporacionItem solIncorporacionItem, HttpServletRequest request);
	public SolIncorporacionItem nifExistenteSearch(SolIncorporacionItem solIncorporacionItem,
			HttpServletRequest request);
	public DocumentacionIncorporacionDTO getDocRequerida (HttpServletRequest request, String tipoColegiacion, String tipoSolicitud, String modalidad, String idSolicitud);
}
