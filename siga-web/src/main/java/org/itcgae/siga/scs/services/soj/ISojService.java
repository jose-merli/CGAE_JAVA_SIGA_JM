package org.itcgae.siga.scs.services.soj;

import org.itcgae.siga.DTOs.scs.FichaSojDTO;
import org.itcgae.siga.DTOs.scs.FichaSojItem;

import javax.servlet.http.HttpServletRequest;

public interface ISojService {
    FichaSojDTO getDetallesSoj(FichaSojItem fichaSojItem, HttpServletRequest request) throws Exception;
}
