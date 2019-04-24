package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IFichaColegialRegTelService {

	public DocushareDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception;

	public DocushareDTO searchListDir(int numPagina, DocuShareObjectVO docu, HttpServletRequest request) throws Exception;
	
	public DocushareDTO searchListDocNoCol(int numPagina, String idPersona, HttpServletRequest request) throws Exception;

	public DocushareDTO searchListDirNoCol(int numPagina, DocuShareObjectVO docu, HttpServletRequest request) throws Exception;
	
	public ResponseEntity<InputStreamResource> downloadDoc(DocuShareObjectVO cargaMasivaItem, HttpServletRequest request) throws Exception;

	public String getPermisoRegTel(HttpServletRequest request) throws Exception;
	
	public String insertCollection(String idPersona, HttpServletRequest request) throws Exception;
	
	public String insertCollectionNoCol(String idPersona, HttpServletRequest request) throws Exception;

}
