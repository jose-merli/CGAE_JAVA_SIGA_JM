package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.itcgae.siga.cen.services.IFichaColegialRegTelService;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialRegTelServiceImpl implements IFichaColegialRegTelService {

	private Logger LOGGER = Logger.getLogger(FichaColegialRegTelServiceImpl.class);

	@Autowired
	private DocushareHelper docushareHelper;

	@Override
	public DocushareDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String dir = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		dir = docushareHelper.buscaCollectionCenso("prueba", idInstitucion);
		List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(dir);
		docushareDTO.setDocuShareObjectVO(docus);
		return docushareDTO;
	}

	@Override
	public DocushareDTO searchListDir(int numPagina, DocuShareObjectVO docu, HttpServletRequest request)
			throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String dir = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		dir = docushareHelper.buscaCollectionCenso("prueba/" + docu.getTitle(), idInstitucion);
		List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(dir);
		docushareDTO.setDocuShareObjectVO(docus);
		return docushareDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> downloadDoc(DocuShareObjectVO docushareObjectVO,
			HttpServletRequest request) throws Exception {
		String dir = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		dir = docushareHelper.buscaCollectionCenso("prueba/" + docushareObjectVO.getTitle(), idInstitucion);
		List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(dir);
		// Se convierte el fichero en array de bytes para enviarlo al front
		File file = new File(dir);
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
