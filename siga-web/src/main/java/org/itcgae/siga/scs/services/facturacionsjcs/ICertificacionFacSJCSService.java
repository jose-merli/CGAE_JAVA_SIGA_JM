package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import java.io.File;

import javax.servlet.http.HttpServletRequest;

public interface ICertificacionFacSJCSService {

    InsertResponseDTO tramitarCertificacion(String idFacturacion, HttpServletRequest request);
	File getInforme(String idFacturacion, String tipoFichero, HttpServletRequest request);

}
