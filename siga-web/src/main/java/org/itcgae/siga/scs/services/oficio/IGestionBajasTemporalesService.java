package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;

public interface IGestionBajasTemporalesService {
	

	public ComboDTO comboEstado(HttpServletRequest request);

	public BajasTemporalesDTO busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request);
	
	public InsertResponseDTO nuevaBajaTemporal(ColegiadoItem colegiadoItem,HttpServletRequest request);

	public UpdateResponseDTO updateEstado(BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request);
	
}
