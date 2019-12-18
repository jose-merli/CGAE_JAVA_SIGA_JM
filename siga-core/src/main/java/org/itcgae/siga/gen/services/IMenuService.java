package org.itcgae.siga.gen.services;

import java.security.cert.CertificateEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.EntornoDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;

public interface IMenuService {

	
	public MenuDTO getMenu(HttpServletRequest request);
	
	public  ComboDTO getInstituciones(HttpServletRequest request);
	
	public  ComboDTO getPerfiles(String idInstitucion);

	public PermisoDTO getPermisos(PermisoRequestItem permisoRequestItem, HttpServletRequest request) throws CertificateEncodingException;

	public UpdateResponseDTO updatePermisos(PermisoUpdateItem permisoRequestItem, HttpServletRequest request);
	
	public UsuarioLogeadoDTO getUserLog( HttpServletRequest request);

	public PermisoDTO getAccessControl(ControlRequestItem permisoRequestItem, HttpServletRequest request);
	
    public void getHeaderLogo(HttpServletRequest httpRequest, HttpServletResponse response);

	public HashMap<String,String> getAccessControlWithOutPerm(String authorization);
	
	public EntornoDTO getEntorno( HttpServletRequest request);

	public UpdateResponseDTO validaInstitucion(HttpServletRequest request);
	
	public UpdateResponseDTO setIdiomaUsuario(HttpServletRequest request, String idLenguaje);

	public ComboItem getInstitucionActual(HttpServletRequest request);

	public ComboItem getLetrado(HttpServletRequest request);
	
	public ColegiadoItem isColegiado(HttpServletRequest request);


}
