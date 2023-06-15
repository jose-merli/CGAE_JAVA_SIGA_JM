package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ICertificacionFacSJCSService {

    InsertResponseDTO tramitarCertificacion(TramitarCerttificacionRequestDTO tramitarCerttificacionRequestDTO, HttpServletRequest request);

    Resource getInformeCAM(DescargaInfomreCAMItem descargaInfomreCAMItem, HttpServletRequest request) throws Exception;

    UpdateResponseDTO subirFicheroCAM(MultipartHttpServletRequest request);

    CertificacionesDTO buscarCertificaciones(BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, HttpServletRequest request);

    ComboDTO getComboEstadosCertificaciones(HttpServletRequest request);

    UpdateResponseDTO validaCatalunya(GestionEconomicaCatalunyaItem gestEcom, HttpServletRequest request);

    DeleteResponseDTO eliminarCertificaciones(List<CertificacionesItem> certificacionesItemList, HttpServletRequest request);

    EstadoCertificacionDTO getEstadosCertificacion(String idCertificacion, HttpServletRequest request);

    InsertResponseDTO createOrUpdateCertificacion(CertificacionesItem certificacionesItem, HttpServletRequest request);

    UpdateResponseDTO reabrirCertificacion(CertificacionesItem certificacionesItem, HttpServletRequest request);

    FacturacionDTO getFactCertificaciones(String idCertificacion, HttpServletRequest request);

    InsertResponseDTO saveFactCertificacion(CertificacionesItem certificacionesItem, HttpServletRequest request);

    DeleteResponseDTO delFactCertificacion(List<CertificacionesItem> certificacionesItemList, HttpServletRequest request);

    Resource descargaErrorValidacion(GestionEconomicaCatalunyaItem gestionVo, HttpServletRequest request) throws Exception;

    UpdateResponseDTO enviaRespuestaCICAC_ICA(GestionEconomicaCatalunyaItem gestEcom, HttpServletRequest request);

    Resource descargarCertificacionesXunta(DescargaCertificacionesXuntaItem descargaItem, HttpServletRequest request) throws Exception;

    public InsertResponseDTO reabrirFacturacion(List<CertificacionesItem> certificacionesItemList, HttpServletRequest request);

    MovimientosVariosAsoCerDTO getMvariosAsociadosCertificacion(String idCertificacion, HttpServletRequest request);

    MovimientosVariosApliCerDTO getMvariosAplicadosEnPagosEjecutadosPorPeriodo(MovimientosVariosApliCerRequestDTO movimientosVariosApliCerRequestDTO, HttpServletRequest request);

    UpdateResponseDTO accionXuntaEnvios(EnvioXuntaItem envioItem, HttpServletRequest request);

    void marcaVisiblesFacturacionesCerradas();

    Resource descargaGeneral(String idCertificacion, String idEstadoCertificacion, HttpServletRequest request) throws Exception;
    
    Resource descargarLogReintegrosXunta(List<String> idFactsList, HttpServletRequest request) throws Exception;

    Resource descargarInformeIncidencias(List<String> idFactsList, HttpServletRequest request) throws Exception;

    StringDTO perteneceInstitucionCAMoXunta(HttpServletRequest request);

	PcajgAlcActErrorCamDTO buscarErroresCAM(String idCertificacion,
			HttpServletRequest request);
}
