package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.CertificadoDTO;
import org.itcgae.siga.DTOs.cen.CertificadoItem;
import org.itcgae.siga.cen.services.IFichaDatosCertificadosService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatosCertificadosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaDatosCertificadosServiceImpl implements IFichaDatosCertificadosService{

	private Logger LOGGER = Logger.getLogger(FichaDatosColegialesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenDatosCertificadosExtendsMapper cenDatosCertificadosExtendsMapper;
	
	@Override
	public CertificadoDTO datosCertificadosSearch(int numPagina, String idPersona,
			HttpServletRequest request) {
		
		LOGGER.info("datosCertificadosSearch() -> Entrada al servicio para la búsqueda por filtros de direcciones");

//		cert
		List<CertificadoItem> certificadoListItem = new ArrayList<CertificadoItem>();
		CertificadoDTO certificadoDTO = new CertificadoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosCertificadosSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosCertificadosSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
//				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"datosCertificadosSearch() / CenColegiadoExtendsMapper.selectDirecciones() -> Entrada a CenColegiadoExtendsMapper para busqueda de Colegiados");
				certificadoListItem = cenDatosCertificadosExtendsMapper.datosCertificadosSearch(idPersona,idInstitucion);
				LOGGER.info(
						"datosCertificadosSearch() / CenColegiadoExtendsMapper.selectDirecciones() -> Salida de CenColegiadoExtendsMapper para busqueda de Colegiados");

//				certificadoDTO.setColegiadoItem(colegiadoListItem);
				certificadoDTO.setCertificadoItem(certificadoListItem);
			} else {
				LOGGER.warn(
						"datosCertificadosSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosCertificadosSearch() -> idInstitucion del token nula");
		}

		LOGGER.info("datosCertificadosSearch() -> Salida del servicio para la búsqueda por filtros de Colegiados");
		return certificadoDTO;
	}	
}
