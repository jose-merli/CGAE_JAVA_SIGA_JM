package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.GuardiasinputItem;

public interface FichaListaGuardiaService {

	public void detalleListaGuardia(GuardiasinputItem guardias, HttpServletRequest request);

	public DeleteResponseDTO guardarListaGuardia(GuardiasinputItem guardias, HttpServletRequest request);

	public void guardiasEnlista(GuardiasinputItem guardias, HttpServletRequest request);

	public void comboGuardiasDias(GuardiasinputItem guardias, HttpServletRequest request);

	public DeleteResponseDTO guardarGuardiasEnLista(GuardiasinputItem guardias, HttpServletRequest request);

	public DeleteResponseDTO borrarGuardiasEnLista(GuardiasinputItem guardias, HttpServletRequest request);

}
