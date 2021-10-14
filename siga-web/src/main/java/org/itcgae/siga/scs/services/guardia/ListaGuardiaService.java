package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ListaGuardiaService {

	public ListaGuardiasDTO searchListaGuardias(HttpServletRequest request, ListaGuardiasItem filtro);

	public InsertResponseDTO saveListaGuardias(HttpServletRequest httpServletRequest, ListaGuardiasItem listaGuardiasItem);

	public GuardiasDTO getGuardiasFromLista(HttpServletRequest httpServletRequest, String s);

	public UpdateResponseDTO saveGuardiasEnLista(HttpServletRequest httpServletRequest, List<GuardiasItem> list);

	public DeleteResponseDTO deleteGuardiasFromLista(HttpServletRequest httpServletRequest, List<GuardiasItem> list);
}
