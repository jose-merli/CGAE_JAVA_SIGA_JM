package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ServicioDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ITiposServiciosService {
	
	public ListadoTipoServicioDTO searchTiposServicios(HttpServletRequest request);
	public ListadoTipoServicioDTO searchTiposServiciosHistorico(HttpServletRequest request);
	public ComboDTO comboTiposServicios(HttpServletRequest request);
	public ComboDTO searchTiposServiciosByIdCategoria(HttpServletRequest request, String idCategoria);
	public DeleteResponseDTO crearEditarServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request) throws Exception;
	public ServicioDTO activarDesactivarServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request);
	ComboDTO searchTiposServiciosByIdCategoriaMultiple(HttpServletRequest request, String idCategoria);

}
