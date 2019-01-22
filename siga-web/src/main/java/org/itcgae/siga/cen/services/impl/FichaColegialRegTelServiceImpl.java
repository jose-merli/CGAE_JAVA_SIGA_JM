package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.itcgae.siga.cen.services.IFichaColegialRegTelService;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer.ConcurrencyControlConfigurer;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialRegTelServiceImpl implements IFichaColegialRegTelService {

	private Logger LOGGER = Logger.getLogger(FichaColegialRegTelServiceImpl.class);

	@Autowired
	private DocushareHelper docushareHelper;

	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	

	@Autowired
	private  CenNocolegiadoMapper cenNocolegiadoMapper;
	
	@Autowired
	private GenParametrosMapper genParametrosMapper;
	
	@Override
	public DocushareDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// numero de colegiado o numero de comunitario. 
		CenColegiadoExample example = new CenColegiadoExample();
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona)).andIdinstitucionEqualTo(idInstitucion);
		List<CenColegiado> config= cenColegiadoMapper.selectByExample(example);
		if(config.get(0).getIdentificadords() == null) {
			if(config.get(0).getComunitario() == "0" ) {
				valorColegiadoDocu = config.get(0).getNcolegiado();
			}else {
				valorColegiadoDocu = config.get(0).getNcomunitario();
			}
			identificadorDS = docushareHelper.buscaCollectionCenso(valorColegiadoDocu, idInstitucion);
		}else {
			docushareHelper.setIdInstitucion(idInstitucion);
			identificadorDS = config.get(0).getIdentificadords();
		}
		//NO COLEGIADO
//		identificadorDS = "Collection-179";
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(identificadorDS,identificadorDS);
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	@Override
	public DocushareDTO searchListDir(int numPagina, DocuShareObjectVO docu, HttpServletRequest request)
			throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		identificadorDS =  docu.getId();
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(identificadorDS, docu.getParent());
			docushareDTO.setDocuShareObjectVO(docus);
		} 
		return docushareDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> downloadDoc(DocuShareObjectVO docushareObjectVO,
			HttpServletRequest request) throws Exception {
		File file = null;
		String identificadorDS = null;
		identificadorDS = docushareObjectVO.getId();
		file= docushareHelper.getDocument(identificadorDS);
		// Se convierte el fichero en array de bytes para enviarlo al front

		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE)); 
    
			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public DocushareDTO searchListDocNoCol(int numPagina, String idPersona, HttpServletRequest request)
			throws Exception {
		 DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorNoColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// numero de colegiado o numero de comunitario. 
		CenPersonaExample persona = new CenPersonaExample();
		persona.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona));
		List<CenPersona> configPersona = cenPersonaMapper.selectByExample(persona);
		CenNocolegiadoExample example = new CenNocolegiadoExample();
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona));
		List<CenNocolegiado> config= cenNocolegiadoMapper.selectByExample(example);
		if(config.get(0).getIdentificadords() == null) {
			if(configPersona.get(0).getIdtipoidentificacion()== 10) {
				valorNoColegiadoDocu = "NIF " + configPersona.get(0).getNifcif();
			}else if(configPersona.get(0).getIdtipoidentificacion()== 20) {
				valorNoColegiadoDocu = "CIF " + configPersona.get(0).getNifcif();
			}else{
				valorNoColegiadoDocu = "NIE " +  configPersona.get(0).getNifcif();
			}
			identificadorDS = docushareHelper.buscaCollectionNoColegiado(valorNoColegiadoDocu);
		}else {
			docushareHelper.setIdInstitucion(idInstitucion);
			identificadorDS = config.get(0).getIdentificadords();
		}
		//NO COLEGIADO
//		identificadorDS = "Collection-179";
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(identificadorDS,"");
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	@Override
	public DocushareDTO searchListDirNoCol(int numPagina, DocuShareObjectVO docu, HttpServletRequest request)
			throws Exception {
		 DocushareDTO docushareDTO = new DocushareDTO();
			String identificadorDS = null;
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			String valorNoColegiadoDocu = null;
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			// numero de colegiado o numero de comunitario. 
			CenPersonaExample persona = new CenPersonaExample();
			persona.createCriteria().andIdpersonaEqualTo(Long.parseLong(docu.getIdPersona()));
			List<CenPersona> configPersona = cenPersonaMapper.selectByExample(persona);
			CenNocolegiadoExample example = new CenNocolegiadoExample();
			example.createCriteria().andIdpersonaEqualTo(Long.parseLong(docu.getIdPersona()));
			List<CenNocolegiado> config= cenNocolegiadoMapper.selectByExample(example);
			if(config.get(0).getIdentificadords() == null) {
				if(configPersona.get(0).getIdtipoidentificacion()== 10) {
					valorNoColegiadoDocu = "NIF " + configPersona.get(0).getNifcif();
				}else if(configPersona.get(0).getIdtipoidentificacion()== 20) {
					valorNoColegiadoDocu = "CIF " + configPersona.get(0).getNifcif();
				}else{
					valorNoColegiadoDocu = "NIE " +  configPersona.get(0).getNifcif();
				}
				identificadorDS = docushareHelper.buscaCollectionNoColegiado(valorNoColegiadoDocu);
			}else {
				docushareHelper.setIdInstitucion(idInstitucion);
				identificadorDS = config.get(0).getIdentificadords();
			}
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(identificadorDS, docu.getParent());
			docushareDTO.setDocuShareObjectVO(docus);
		} 
		return docushareDTO;
	}
	
	@Override
	public  String getPermisoRegTel( HttpServletRequest request) throws Exception {
		String result= null;
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andParametroEqualTo("REGTEL");
		List<GenParametros> config = genParametrosMapper.selectByExample(example);
		if(config.size() > 0) {
			result = config.get(0).getValor();
		}else {
			result = "0";
		}
		return result;
	}
}
