package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.HistoricoUsuarioDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioItem;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IAuditoriaUsuariosService;
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

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenTipocambioExtendsMapper cenTipocambioExtendsMapper;
	
	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	
	@Override
	public ComboDTO getActionType(HttpServletRequest request) {

		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		ComboItem comboItem = new ComboItem();
		
		// Conseguimos idLenguaje del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		if(null != usuario.getIdlenguaje()) {
			combooItems = cenTipocambioExtendsMapper.getActionType(usuario.getIdlenguaje());
			comboItem.setLabel("");
			comboItem.setValue("");
			combooItems.add(0, comboItem);
			comboDTO.setCombooItems(combooItems);
		}
		
		return comboDTO;
	}

	@Override
	public HistoricoUsuarioDTO auditUsersSearch(int numPagina, HistoricoUsuarioRequestDTO historicoUsuarioRequestDTO,
			HttpServletRequest request) {

		List<HistoricoUsuarioItem> historicoUsuarioItem = new ArrayList<HistoricoUsuarioItem>();
		HistoricoUsuarioDTO historicoUsuarioDTO =  new HistoricoUsuarioDTO();
		
		
		// Conseguimos idLenguaje e idInstitucion del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		if(null != idInstitucion && null != usuario.getIdlenguaje()) {
			historicoUsuarioRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
			historicoUsuarioRequestDTO.setIdLenguaje(usuario.getIdlenguaje());
			historicoUsuarioItem = cenHistoricoExtendsMapper.auditUsersSearch(numPagina, historicoUsuarioRequestDTO);
			
			historicoUsuarioDTO.setHistoricoUsuarioItem(historicoUsuarioItem);
		}
		
		return historicoUsuarioDTO;
	}

	@Override
	public UpdateResponseDTO auditUsersUpdate(HistoricoUsuarioUpdateDTO historicoUsuarioUpdateDTO,
			HttpServletRequest request) {
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Conseguimos idLenguaje e idInstitucion del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		//clave primaria(idPersona, idInstitucion, idHistorico)
		CenHistorico record =  new CenHistorico();
		record.setMotivo(historicoUsuarioUpdateDTO.getMotivo());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setFechamodificacion(new Date());
		record.setIdpersona(Long.valueOf(historicoUsuarioUpdateDTO.getIdPersona()));
		record.setIdinstitucion(Short.valueOf(idInstitucion));
		record.setIdhistorico(Short.valueOf(historicoUsuarioUpdateDTO.getIdHistorico()));
		
		cenHistoricoExtendsMapper.updateByPrimaryKeySelective(record);

		if(response == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");
		
		return null;
	}

	

	

}
