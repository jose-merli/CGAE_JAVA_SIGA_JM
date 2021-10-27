package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRetencionesService {

    RetencionesDTO searchRetenciones(RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request);

    DeleteResponseDTO deleteRetenciones(List<RetencionesItem> retencionesItemList, HttpServletRequest request);

    RetencionesAplicadasDTO searchRetencionesAplicadas(RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request);

    AplicacionRetencionDTO getAplicacionesRetenciones(AplicacionRetencionRequestDTO aplicacionRetencionRequestDTO, HttpServletRequest request);

}
