package org.itcgae.siga.gen.services;

import java.security.cert.CertificateEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.EntornoDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;

public interface IProcesoService {
	
	public String getIdProceso(String url);

}
