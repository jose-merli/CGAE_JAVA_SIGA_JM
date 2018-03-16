package org.itcgae.siga.services;

import java.util.List;

import org.itcgae.siga.DTOs.common.ComboDTO;
import org.itcgae.siga.DTOs.common.ComboDTORequest;



public interface IComboService {
	public  List<ComboDTO> getCombo(String combo);
	
	public List<ComboDTO> getComboParametros(String combo, ComboDTORequest institucion) ;
		
}
