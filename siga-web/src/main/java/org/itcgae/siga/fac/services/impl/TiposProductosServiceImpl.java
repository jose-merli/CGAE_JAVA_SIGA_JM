package org.itcgae.siga.fac.services.impl;

import java.util.List;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.TiposProductosDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.impl.SolicitudModificacionServiceImpl;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.security.UserTokenUtils;

public class TiposProductosServiceImpl {
	
	private Logger LOGGER = Logger.getLogger(TiposProductosServiceImpl.class);

//	@Override
//	public TiposProductosDTO searchTiposProductos(String idioma, String institucion) {
//		LOGGER.info(
//				"searchModificationRequest() -> Entrada al servicio para recuperar las solicitudes de modificación");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();
//		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
//		if (idInstitucion != null) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//			LOGGER.info(
//					"searchModificationRequest() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//			LOGGER.info(
//					"searchModificationRequest() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (usuarios != null && usuarios.size() > 0) {
//
//				AdmUsuarios usuario = usuarios.get(0);
//				LOGGER.info(
//						"datosSolicitudSearch() / cenEstadoSolicitudExtendsMapper.selectTipoSolicitud() -> Entrada a cenEstadoSolicitudExtendsMapper para obtener los estados de solicitud");
//				if (letrado.equalsIgnoreCase("S")) {
//					CenPersonaExample example = new CenPersonaExample();
//					example.createCriteria().andNifcifEqualTo(usuario.getNif());
//					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(example );
//					if (null != cenPersona && cenPersona.size()>0) {
//						List<SolModificacionItem> solModificacionItems = cenTiposModificacionesExtendsMapper
//								.searchModificationRequest(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
//										String.valueOf(idInstitucion),cenPersona.get(0).getIdpersona());
//						solModificacionDTO.setSolModificacionItems(solModificacionItems);
//					}
//
//				}else{
//					List<SolModificacionItem> solModificacionItems = cenTiposModificacionesExtendsMapper
//							.searchModificationRequest(solicitudModificacionSearchDTO, usuario.getIdlenguaje(),
//									String.valueOf(idInstitucion),null);
//					solModificacionDTO.setSolModificacionItems(solModificacionItems);
//				}
//
//			}
//		}
//
//		LOGGER.info(
//				"searchModificationRequest() -> Salida del servicio para recuperar las solicitudes de modificación");
//
//		return tiposProductosDTO;
//	}
}
