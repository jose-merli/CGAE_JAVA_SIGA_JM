package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;


import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTO.scs.GuardiasItem;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioDTO;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;

public interface IGestionBajasTemporalesService {

	public BajasTemporalesDTO busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateAnular(BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request);

	public UpdateResponseDTO updateValidar(BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateDenegar(BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request);
	
}
