package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.springframework.stereotype.Repository;

public interface ITiposProductosService {
	public ListadoTipoProductoDTO searchTiposProductos(HttpServletRequest request);
}
