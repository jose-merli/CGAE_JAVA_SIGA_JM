package org.itcgae.siga.scs.services.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;

public interface ISaltosCompOficioService {

	public SaltoCompGuardiaDTO searchSaltosYCompensaciones(SaltoCompGuardiaItem saltoItem, HttpServletRequest request);

	public DeleteResponseDTO guardarSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request);

	public DeleteResponseDTO anularSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request);

	public DeleteResponseDTO borrarSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request);

	public ComboDTO searchLetradosTurno(SaltoCompGuardiaItem saltoItem, HttpServletRequest request);

}
