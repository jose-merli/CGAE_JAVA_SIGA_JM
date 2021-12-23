package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface ICertificacionFacSJCSService {

    InsertResponseDTO tramitarCertificacion(String idFacturacion, HttpServletRequest request);

    Resource getInformeCAM(String idFacturacion, String tipoFichero, HttpServletRequest request) throws Exception;

    UpdateResponseDTO subirFicheroCAM(String idFacturacion, MultipartFile fichero, MultipartHttpServletRequest request);

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

    InsertResponseDTO reabrirFacturacion(String idFacturacion, HttpServletRequest request);

    MovimientosVariosAsoCerDTO getMvariosAsociadosCertificacion(String idCertificacion, HttpServletRequest request);

    MovimientosVariosApliCerDTO getMvariosAplicadosEnPagosEjecutadosPorPeriodo(Date fechaDesde, Date fechaHasta, HttpServletRequest request);
}
