package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.db.entities.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFacturacionSJCSServices {

    public FacturacionDTO buscarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request);

    public FacturacionDeleteDTO eliminarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request);

    public FacturacionDTO datosFacturacion(String idFacturacion, HttpServletRequest request);

    public FacturacionDTO historicoFacturacion(String idFacturacion, HttpServletRequest request);

    public StringDTO numApuntes(String idFacturacion, HttpServletRequest request);

    public InsertResponseDTO saveFacturacion(FacturacionItem facturacionItem, HttpServletRequest request);

    public UpdateResponseDTO updateFacturacion(FacturacionItem facturacionItem, HttpServletRequest request);

    public InsertResponseDTO ejecutarFacturacion(String idFacturacion, HttpServletRequest request);

    public InsertResponseDTO reabrirFacturacion(String idFacturacion, HttpServletRequest request);

    public InsertResponseDTO simularFacturacion(String idFacturacion, HttpServletRequest request);

    public FacturacionDTO conceptosFacturacion(String idFacturacion, HttpServletRequest request);

    public InsertResponseDTO saveConceptosFac(List<FacturacionItem> listaFacturacionItem, HttpServletRequest request);

    public UpdateResponseDTO updateConceptosFac(List<FacturacionItem> listaFacturacionItem, HttpServletRequest request);

    public DeleteResponseDTO deleteConceptosFac(List<FacturacionItem> facturacionDTO, HttpServletRequest request);

    public PagosjgDTO datosPagos(String idFacturacion, HttpServletRequest request);

    public void ejecutaFacturacionSJCS();

    public FacturacionesAsuntoDTO getFacturacionesPorAsuntoActuacionDesigna(ScsActuaciondesigna scsActuaciondesigna, HttpServletRequest request);

    public FacturacionesAsuntoDTO getFacturacionesPorAsuntoAsistencia(ScsAsistencia scsAsistencia, HttpServletRequest request);

    public FacturacionesAsuntoDTO getFacturacionesPorAsuntoActuacionAsistencia(ScsActuacionasistencia scsActuacionasistencia, HttpServletRequest request);

    public FacturacionesAsuntoDTO getFacturacionesPorGuardia(ScsCabeceraguardias guardia, HttpServletRequest request);

    public FacturacionesAsuntoDTO getFacturacionesPorEJG(ScsEjg ejg, HttpServletRequest request);
	
    public void ejecutaFacturacionesSJCSBloqueadas();

}