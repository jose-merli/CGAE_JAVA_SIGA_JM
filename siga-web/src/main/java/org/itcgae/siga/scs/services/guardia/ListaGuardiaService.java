package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.DTOs.scs.GuardiasinputItem;
import org.itcgae.siga.DTOs.scs.ListaGuardiasDTO;
import org.itcgae.siga.DTOs.scs.ListaGuardiasItem;
import org.springframework.web.bind.annotation.RequestBody;

public interface ListaGuardiaService {

	public ListaGuardiasDTO searchListaGuardias(HttpServletRequest request, ListaGuardiasItem filtro);
}
