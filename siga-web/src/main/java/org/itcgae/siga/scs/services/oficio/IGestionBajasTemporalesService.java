package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;

public interface IGestionBajasTemporalesService {

	public BajasTemporalesDTO busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request);
	
	public InsertResponseDTO nuevaBajaTemporal(ColegiadoItem colegiadoItem,HttpServletRequest request);
	
	public UpdateResponseDTO updateAnular(BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request);

	public UpdateResponseDTO updateValidar(BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateDenegar(BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request);
	
}
