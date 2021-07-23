package org.itcgae.siga.scs.services.impl.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.GuardiasinputItem;
import org.itcgae.siga.scs.services.guardia.FichaListaGuardiaService;
import org.springframework.stereotype.Service;

@Service
public class FichaListaGuardiaServiceImpl implements FichaListaGuardiaService{

	@Override
	public void detalleListaGuardia(GuardiasinputItem guardias, HttpServletRequest request) {
		
	}

	@Override
	public DeleteResponseDTO guardarListaGuardia(GuardiasinputItem guardias, HttpServletRequest request) {
		return null;
	}

	@Override
	public void guardiasEnlista(GuardiasinputItem guardias, HttpServletRequest request) {
		
	}

	@Override
	public void comboGuardiasDias(GuardiasinputItem guardias, HttpServletRequest request) {
		
	}

	@Override
	public DeleteResponseDTO guardarGuardiasEnLista(GuardiasinputItem guardias, HttpServletRequest request) {
		return null;
	}

	@Override
	public DeleteResponseDTO borrarGuardiasEnLista(GuardiasinputItem guardias, HttpServletRequest request) {
		return null;
	}
	
}
