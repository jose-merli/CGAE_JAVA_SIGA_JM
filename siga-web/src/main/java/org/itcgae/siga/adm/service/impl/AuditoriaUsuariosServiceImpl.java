package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioItem;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IAuditoriaUsuariosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenTipocambioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuditoriaUsuariosServiceImpl implements IAuditoriaUsuariosService{

	private Logger LOGGER = Logger.getLogger(AuditoriaUsuariosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenTipocambioExtendsMapper cenTipocambioExtendsMapper;
	
	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	
	@Override
	public ComboDTO getActionType(HttpServletRequest request) {

		LOGGER.info("getActionType() -> Entrada al servicio para obtener los tipos de acciones de usuarios");
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		ComboItem comboItem = new ComboItem();
		
		// Conseguimos idLenguaje del usuario logeado
		LOGGER.debug("getActionType() -> Conseguimos idLenguaje del usuario logeado");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info("getActionType() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("getActionType() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		AdmUsuarios usuario = usuarios.get(0);
		
		if(null != usuario.getIdlenguaje()) {
			LOGGER.info("getActionType() / cenTipocambioExtendsMapper.getActionType() -> Entrada a cenTipocambioExtendsMapper para obtener acciones del usuario logeado");		
			combooItems = cenTipocambioExtendsMapper.getActionType(usuario.getIdlenguaje());
			LOGGER.info("getActionType() / cenTipocambioExtendsMapper.getActionType() -> Salida de admUsuariosExtendsMapper para obtener acciones del usuario logeado");
			comboItem.setLabel("");
			comboItem.setValue("");
			combooItems.add(0, comboItem);
			comboDTO.setCombooItems(combooItems);
		}
		else {
			LOGGER.warn("getActionType() -> idlenguaje del usuario logeado nulo");
		}
		
		LOGGER.info("getActionType() -> Salida del servicio para obtener los tipos de acciones de usuarios");
		return comboDTO;
	}

	@Override
	public HistoricoUsuarioDTO auditUsersSearch(int numPagina, HistoricoUsuarioRequestDTO historicoUsuarioRequestDTO,
			HttpServletRequest request) {

		LOGGER.info("auditUsersSearch() -> Entrada al servicio para búsqueda de auditoría de usuarios con filtros");
		List<HistoricoUsuarioItem> historicoUsuarioItem = new ArrayList<HistoricoUsuarioItem>();
		HistoricoUsuarioDTO historicoUsuarioDTO =  new HistoricoUsuarioDTO();
		
		
		// Conseguimos idLenguaje e idInstitucion del usuario logeado
		LOGGER.debug("auditUsersSearch() -> Conseguimos idLenguaje e idInstitucion del usuario logeado");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info("auditUsersSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("auditUsersSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		AdmUsuarios usuario = usuarios.get(0);
		
		if(null != idInstitucion && null != usuario.getIdlenguaje()) {
			historicoUsuarioRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
			historicoUsuarioRequestDTO.setIdLenguaje(usuario.getIdlenguaje());
			LOGGER.info("auditUsersSearch() / cenHistoricoExtendsMapper.auditUsersSearch() -> Entrada a cenHistoricoExtendsMapper para obtener lista de usuarios que cumplen el filtro");
			historicoUsuarioItem = cenHistoricoExtendsMapper.auditUsersSearch(numPagina, historicoUsuarioRequestDTO);
			LOGGER.info("auditUsersSearch() / cenHistoricoExtendsMapper.auditUsersSearch() -> Salida de cenHistoricoExtendsMapper para obtener lista de usuarios que cumplen el filtro");
			historicoUsuarioDTO.setHistoricoUsuarioItem(historicoUsuarioItem);
		}
		else {
			LOGGER.warn("auditUsersSearch() -> No existe idInstitucion del token ni existe un usuario logeado");
		}
		
		LOGGER.info("auditUsersSearch() -> Salida del servicio para búsqueda de auditoría de usuarios con filtros");
		return historicoUsuarioDTO;
	}

	@Override
	public UpdateResponseDTO auditUsersUpdate(HistoricoUsuarioUpdateDTO historicoUsuarioUpdateDTO,
			HttpServletRequest request) {
		
		LOGGER.info("auditUsersUpdate() -> Entrada al servicio para actualizar características de usuario de auditoría");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Conseguimos idLenguaje e idInstitucion del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info("auditUsersUpdate() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("auditUsersUpdate() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			
			//clave primaria(idPersona, idInstitucion, idHistorico)
			LOGGER.debug("auditUsersUpdate() -> actualización de usuario auditoría por clave primaria(idPersona, idInstitucion, idHistorico)");
			CenHistorico record =  new CenHistorico();
			record.setMotivo(historicoUsuarioUpdateDTO.getMotivo());
			record.setUsumodificacion(usuario.getIdusuario());
			record.setFechamodificacion(new Date());
			record.setIdpersona(Long.valueOf(historicoUsuarioUpdateDTO.getIdPersona()));
			record.setIdinstitucion(Short.valueOf(idInstitucion));
			record.setIdhistorico(Short.valueOf(historicoUsuarioUpdateDTO.getIdHistorico()));
			
			LOGGER.info("auditUsersUpdate() / cenHistoricoExtendsMapper.updateByPrimaryKeySelective() -> Entrada a cenHistoricoExtendsMapper para actualizar características de usuario de auditoría");
			response = cenHistoricoExtendsMapper.updateByPrimaryKeySelective(record);
			LOGGER.info("auditUsersUpdate() / cenHistoricoExtendsMapper.updateByPrimaryKeySelective() -> Salida de cenHistoricoExtendsMapper para actualizar características de usuario de auditoría");
		}
		else {
			LOGGER.info("auditUsersUpdate() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
		}
	
		// respuesta si se actualiza correctamente
		if(response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("auditUsersUpdate() / -> OK. Características de usuario de auditoría actualizado correctamente en bd");
		}
			
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("auditUsersUpdate() / -> KO. Características de usuario de auditoría NO actualizado en bd");
		}
			
		LOGGER.info("auditUsersUpdate() -> Salida del servicio para para actualizar características de usuario de auditoría");
		return updateResponseDTO;
	}

	

	

}
