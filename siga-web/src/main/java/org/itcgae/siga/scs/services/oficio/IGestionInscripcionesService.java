package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;


import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTO.scs.GuardiasItem;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioDTO;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;

public interface IGestionInscripcionesService {
	public ComboDTO comboTurnos(HttpServletRequest request);

	public InscripcionesDTO busquedaInscripciones(InscripcionesItem inscripcionesItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateSolicitarBaja(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);
	
	public InsertResponseDTO insertSolicitarAlta(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);
	
	public InscripcionesDisponiblesDTO busquedaTarjetaInscripciones(InscripcionesItem inscripcionesItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateDenegar(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateCambiarFecha(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);

	public InscripcionesDTO TarjetaColaOficio(InscripcionesItem inscripcionesItem, HttpServletRequest request);
	
	public InscripcionesTarjetaOficioDTO busquedaTarjeta(InscripcionesItem inscripcionesItem, HttpServletRequest request);

	public Boolean checkTrabajosSJCS(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);

	public InscripcionesDTO checkSaltos(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateBorrarSaltos(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);

	public UpdateResponseDTO updateValidar(InscripcionesDTO inscripcionesDTO, HttpServletRequest request);
}
