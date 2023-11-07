package org.itcgae.siga.scs.services.soj;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionSojDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionSojItem;
import org.itcgae.siga.DTOs.scs.FichaSojDTO;
import org.itcgae.siga.DTOs.scs.FichaSojItem;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ISojService {
    FichaSojDTO getDetallesSoj(FichaSojItem fichaSojItem, HttpServletRequest request) throws Exception;
	UpdateResponseDTO guardarDatosGenerales(FichaSojItem fichaSojItem, HttpServletRequest request) throws Exception;
	UpdateResponseDTO asociarSOJ(HttpServletRequest request, FichaSojItem sojItem) throws Exception;
	UpdateResponseDTO desasociarSOJ(HttpServletRequest request, FichaSojItem sojItem);
	UpdateResponseDTO guardarServiciosTramitacion(HttpServletRequest request, FichaSojItem sojItem);
	DocumentacionSojDTO getDocumentosSOJ(FichaSojItem fichaSojItem, HttpServletRequest request) throws Exception;
	InsertResponseDTO subirDocumentoSOJ(List<DocumentacionSojItem> documentacionesSOJ, HttpServletRequest request);
	DeleteResponseDTO eliminarDocumentoSOJ(HttpServletRequest request, List<DocumentacionSojItem> documentos);
	UpdateResponseDTO asociarEJGaSOJ(List<String> datos, HttpServletRequest request);
}
