package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;

public interface IBusquedaJuzgadosService {

	public JuzgadoDTO searchCourt(JuzgadoItem juzgadoItem, HttpServletRequest request);

	public UpdateResponseDTO deleteCourt(JuzgadoDTO juzgadoDTO, HttpServletRequest request);

	public UpdateResponseDTO activateCourt(JuzgadoDTO juzgadoDTO, HttpServletRequest request);
}
