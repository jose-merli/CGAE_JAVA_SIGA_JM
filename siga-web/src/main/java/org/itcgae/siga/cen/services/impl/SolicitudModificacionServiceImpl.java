package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ISolicitudModificacionService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadoSolicitudExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadoSolicitudModifExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposModificacionesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudModificacionServiceImpl implements ISolicitudModificacionService{

	private Logger LOGGER = Logger.getLogger(SolicitudModificacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper _admUsuariosExtendsMapper;
	
	@Autowired
	private CenTiposModificacionesExtendsMapper cenTiposModificacionesExtendsMapper;
	
	@Autowired
	private CenEstadoSolicitudModifExtendsMapper cenEstadoSolicitudModifExtendsMapper;
	
	@Override
	public ComboDTO getComboTipoModificacion(HttpServletRequest request) {
	LOGGER.info("getComboTipoModificacion() -> Entrada al servicio para cargar el tipo de modificación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getComboTipoModificacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getComboTipoModificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getComboTipoModificacion() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de modificación");

				List<ComboItem> comboItems = cenTiposModificacionesExtendsMapper.getTipoModificacion(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getComboTipoModificacion() -> Salida del servicio para obtener los tipos de modificación");
		return combo;
	}
	
	@Override
	public ComboDTO getComboEstado(HttpServletRequest request) {
	LOGGER.info("getComboEstado() -> Entrada al servicio para cargar los estados de solicitud");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getComboEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getComboEstado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getComboEstado() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los estados de solicitud");

				List<ComboItem> comboItems = cenEstadoSolicitudModifExtendsMapper.getEstado(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getComboEstado() -> Salida del servicio para obtener los estados de solicitud");
		return combo;
	}

}
