package org.itcgae.siga.scs.services.ejg;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface IEJGIntercambiosService {
    ResponseDataDTO esColegioZonaComun(HttpServletRequest request) throws Exception;
    EjgListaIntercambiosDTO getListadoIntercambiosAltaEJG(EjgItem item, HttpServletRequest request) throws Exception;
    EjgListaIntercambiosDTO getListadoIntercambiosDocumentacionEJG(EjgItem item, HttpServletRequest request) throws Exception;
    UpdateResponseDTO consultarEstadoPericles(EjgItem ejgItem, HttpServletRequest request) throws Exception;
    UpdateResponseDTO enviaDocumentacionAdicional(EjgDocumentacionItem documentacionItemItem, HttpServletRequest request) throws Exception;
}
