package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;
import org.itcgae.siga.cen.services.IFichaDatosCurricularesService;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FichaDatosCurricularesServiceImpl implements IFichaDatosCurricularesService {

	private Logger LOGGER = Logger.getLogger(FichaDatosCurricularesServiceImpl.class);

	@Autowired
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public FichaDatosCurricularesDTO searchDatosCurriculares(
			FichaDatosCurricularesSearchDTO fichaDatosCurricularesSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a searchDatosCurriculares");
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = new FichaDatosCurricularesDTO();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {				
				fichaDatosCurricularesItem = cenDatoscvExtendsMapper.searchDatosCurriculares(fichaDatosCurricularesSearchDTO.getIdPersona(), String.valueOf(idInstitucion));
				fichaDatosCurricularesDTO.setFichaDatosCurricularesItem(fichaDatosCurricularesItem);
			
		}
		LOGGER.info(
				"searchDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Salida de searchDatosCurriculares");
		return fichaDatosCurricularesDTO;
	}

}
