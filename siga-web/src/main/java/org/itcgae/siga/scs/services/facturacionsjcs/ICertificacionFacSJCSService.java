package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ICertificacionFacSJCSService {

    InsertResponseDTO tramitarCertificacion(String idFacturacion, HttpServletRequest request);
	Resource getInformeCAM(String idFacturacion,String tipoFichero,HttpServletRequest request) throws Exception;
	UpdateResponseDTO subirFicheroCAM(String idFacturacion, MultipartFile fichero, MultipartHttpServletRequest request);

}
