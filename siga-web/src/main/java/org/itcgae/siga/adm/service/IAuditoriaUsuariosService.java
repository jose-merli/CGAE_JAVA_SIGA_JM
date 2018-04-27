package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.HistoricoUsuarioDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IAuditoriaUsuariosService {

	public ComboDTO getActionType(HttpServletRequest request);
	
	public HistoricoUsuarioDTO auditUsersSearch(int numPagina,HistoricoUsuarioRequestDTO  historicoUsuarioRequestDTO , HttpServletRequest request);
	
	public UpdateResponseDTO auditUsersUpdate(HistoricoUsuarioUpdateDTO historicoUsuarioUpdateDTO , HttpServletRequest request);
	
	
}
