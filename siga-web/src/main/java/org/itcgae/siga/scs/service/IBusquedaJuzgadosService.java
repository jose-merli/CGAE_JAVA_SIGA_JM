package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JuzgadoDTO;
import org.itcgae.siga.DTO.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IBusquedaJuzgadosService {

	public JuzgadoDTO searchJudged(JuzgadoItem juzgadoItem, HttpServletRequest request);

	public UpdateResponseDTO deleteJudged(JuzgadoDTO juzgadoDTO, HttpServletRequest request);

	public UpdateResponseDTO activateJudged(JuzgadoDTO juzgadoDTO, HttpServletRequest request);
}
