package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederoDTO;

import javax.servlet.http.HttpServletRequest;


public interface ILineaanticipoService {
    ListaMonederoDTO listarMonederos(HttpServletRequest request, FiltroMonederoItem filtroMonederoItem);
}
