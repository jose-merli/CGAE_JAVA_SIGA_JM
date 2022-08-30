package org.itcgae.siga.scs.services.ejg;

import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosDTO;

import javax.servlet.http.HttpServletRequest;

public interface IEJGIntercambiosService {
    EjgListaIntercambiosDTO getListadoIntercambiosAltaEJG(EjgItem item, HttpServletRequest request) throws Exception;
    EjgListaIntercambiosDTO getListadoIntercambiosDocumentacionEJG(EjgItem item, HttpServletRequest request) throws Exception;
}
