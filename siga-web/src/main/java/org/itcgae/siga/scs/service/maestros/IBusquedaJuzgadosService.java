package org.itcgae.siga.scs.service.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JuzgadoDTO;
import org.itcgae.siga.DTO.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IBusquedaJuzgadosService {

	public JuzgadoDTO searchCourt(JuzgadoItem juzgadoItem, HttpServletRequest request);

	public UpdateResponseDTO deleteCourt(JuzgadoDTO juzgadoDTO, HttpServletRequest request);

	public UpdateResponseDTO activateCourt(JuzgadoDTO juzgadoDTO, HttpServletRequest request);
}
