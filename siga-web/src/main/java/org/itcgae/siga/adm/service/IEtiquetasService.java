package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.EtiquetaDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaSearchDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IEtiquetasService {

	public ComboDTO getLabelLenguage();
	
	public EtiquetaDTO searchLabels(int numPagina, EtiquetaSearchDTO  etiquetaSearchDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateLabel(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request);
	
	
}
