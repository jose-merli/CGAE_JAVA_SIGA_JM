package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.RetencionIRPFDTO;
import org.itcgae.siga.DTO.scs.RetencionIRPFItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IRetencionesIrpfService {

	public InsertResponseDTO createRetencion(RetencionIRPFItem retencionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateRetenciones(RetencionIRPFDTO retencionDTO, HttpServletRequest request);
	
	public RetencionIRPFDTO searchRetenciones(RetencionIRPFItem retencionItem, HttpServletRequest request);

	public UpdateResponseDTO deleteRetenciones(RetencionIRPFDTO retencionDTO, HttpServletRequest request);

	public UpdateResponseDTO activateRetenciones(RetencionIRPFDTO retencionDTO, HttpServletRequest request);

	public ComboDTO getSociedades(HttpServletRequest request);
	}
