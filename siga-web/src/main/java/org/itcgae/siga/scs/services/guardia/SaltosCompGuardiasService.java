package org.itcgae.siga.scs.services.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.BusquedaLetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;

public interface SaltosCompGuardiasService {

	public SaltoCompGuardiaDTO searchSaltosYCompensaciones(SaltoCompGuardiaItem saltoItem, HttpServletRequest request);

	public DeleteResponseDTO guardarSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request);

	public DeleteResponseDTO borrarSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request);

	public DeleteResponseDTO anularSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request);

	public String isGrupo(BusquedaLetradosGuardiaDTO letradoGuardiaItem);

}
