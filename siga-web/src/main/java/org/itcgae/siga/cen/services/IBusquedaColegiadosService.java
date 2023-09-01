package org.itcgae.siga.cen.services;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaColegiadosService {

	public ComboDTO getCivilStatus(HttpServletRequest request);
	
	public ComboDTO getSituacion(HttpServletRequest request);
	
	public ComboDTO getSituacionGlobalColegiado (String idPersona, HttpServletRequest request);
	
	public ComboDTO getCVCategory(HttpServletRequest request);
	
	public ColegiadoDTO searchColegiado( ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException;
	
	public ComboInstitucionDTO getLabel(HttpServletRequest request);

	public ColegiadoDTO searchColegiadoFicha(ColegiadoItem colegiadoItem, HttpServletRequest request);
	
	public ResponseFileDTO generateExcel(ColegiadoItem colegiadoItem, HttpServletRequest request);


	
}
