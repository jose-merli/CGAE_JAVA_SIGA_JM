package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AsuntosAsistenciaItem;
import org.itcgae.siga.DTOs.scs.AsuntosSOJItem;
import org.itcgae.siga.DTOs.scs.DelitosEjgDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoEjgItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ListaContrarioEJGJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IGestionEJG {

	EjgDTO datosEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboPrestaciones(HttpServletRequest request);

	UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request);

	ExpedienteEconomicoDTO getExpedientesEconomicos(EjgItem ejgItem, HttpServletRequest request);

	EstadoEjgDTO getEstados(EjgItem ejgItem, HttpServletRequest request);

	EjgDocumentacionDTO getDocumentos(EjgItem ejgItem, HttpServletRequest request);

	EjgItem getDictamen(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboOrigen(HttpServletRequest request);

	ComboDTO comboActaAnnio(HttpServletRequest request);

	ResolucionEJGItem getResolucion(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboTipoExpediente(HttpServletRequest request);
	
	UpdateResponseDTO cambioEstadoMasivo(List<EjgItem> datos, HttpServletRequest request);
	
	UpdateResponseDTO editarEstado(EstadoEjgItem datos, HttpServletRequest request);
	
	ResponseEntity<InputStreamResource> descargarExpedientesJG(List<EjgItem> datos, HttpServletRequest request);
	
	EjgDTO insertaDatosGenerales(EjgItem datos, HttpServletRequest request);
	
	UpdateResponseDTO actualizaDatosGenerales(EjgItem datos, HttpServletRequest request);

	UpdateResponseDTO borrarEstado(List<EstadoEjgItem> datos, HttpServletRequest request);

	InsertResponseDTO nuevoEstado(EstadoEjgItem datos, HttpServletRequest request);

	UpdateResponseDTO borrarRelacion(List<EjgItem> datos, HttpServletRequest request);

	UpdateResponseDTO guardarImpugnacion(EjgItem datos, HttpServletRequest request);

	UpdateResponseDTO guardarResolucion(EjgItem datos, HttpServletRequest request);
	
	UpdateResponseDTO descargarInformeCalificacion(EjgItem datos, HttpServletRequest request);
	
	List<ScsEjgPrestacionRechazada> searchPrestacionesRechazadas(EjgItem ejgItem, HttpServletRequest request);

	UpdateResponseDTO guardarServiciosTramitacion(EjgItem datos, HttpServletRequest request);

	EjgDesignaDTO getEjgDesigna(EjgItem datos, HttpServletRequest request); 
	
	EnviosMasivosDTO getComunicaciones(EjgItem item, HttpServletRequest request);

	InsertResponseDTO insertFamiliarEJG(List<String> item, HttpServletRequest request);

	UpdateResponseDTO borrarFamiliar(List<UnidadFamiliarEJGItem> datos, HttpServletRequest request);
	
	RelacionesDTO getRelacionesEJG(EjgItem item, HttpServletRequest request);

	ComboDTO comboSituaciones(HttpServletRequest request);

	ComboDTO comboCDetenciones(HttpServletRequest request);

	ComboDTO comboTipoencalidad(HttpServletRequest request);

	UpdateResponseDTO updateDatosJuridicos(EjgItem datos, HttpServletRequest request);

	List<ListaContrarioEJGJusticiableItem> busquedaListaContrariosEJG(EjgItem item, HttpServletRequest request,
			Boolean historico);

	InsertResponseDTO insertContrarioEJG(ScsContrariosejg item, HttpServletRequest request);

	UpdateResponseDTO deleteContrarioEJG(ScsContrariosejg item, HttpServletRequest request);

	UpdateResponseDTO updateAbogadoContrarioEJG(ScsContrariosejg item, HttpServletRequest request);

	UpdateResponseDTO updateProcuradorContrarioEJG(ScsContrariosejg item, HttpServletRequest request);

	UpdateResponseDTO updateRepresentanteContrarioEJG(ScsContrariosejg item, HttpServletRequest request);

	ProcuradorDTO busquedaProcuradorEJG(EjgItem ejg, HttpServletRequest request);

	UpdateResponseDTO guardarProcuradorEJG(EjgItem item, HttpServletRequest request);

	//UpdateResponseDTO nuevoProcuradorEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboDelitos(HttpServletRequest request);

	DelitosEjgDTO getDelitosEjg(EjgItem item, HttpServletRequest request);

	//InsertResponseDTO actualizarDelitosEJG(EjgItem item, HttpServletRequest request);

	ProcuradorDTO busquedaProcuradores(ProcuradorItem procuradorItem, HttpServletRequest request);

	ComboDTO comboPresentadores(HttpServletRequest request);

	ComboDTO comboTipoDocumentacion(HttpServletRequest request);

	ComboDTO comboDocumentos(String idTipoDocumentacion, HttpServletRequest request);

	InsertResponseDTO subirDocumentoEjg(MultipartHttpServletRequest request);

	InsertResponseDTO crearDocumentacionEjg(EjgDocumentacionItem documentacionEjgItem, HttpServletRequest request);

	UpdateResponseDTO actualizarDocumentacionEjg(EjgDocumentacionItem documentoEjgItem, HttpServletRequest request);

	DeleteResponseDTO eliminarDocumentosEjg(EjgDocumentacionItem doc, HttpServletRequest request);

	DeleteResponseDTO eliminarDocumentacionEjg(List<EjgDocumentacionItem> listadocumentoEjgItem,
			HttpServletRequest request);

	ResponseEntity<InputStreamResource> descargarDocumentosEjg(List<EjgDocumentacionItem> listadocumentoEjgItem,
			HttpServletRequest request);

	InsertResponseDTO solicitarEEJG(UnidadFamiliarEJGItem datos, HttpServletRequest request);

	UpdateResponseDTO actualizarInformeCalificacionEjg(EjgItem ejgItem, HttpServletRequest request);
	
	UpdateResponseDTO asociarDesignacion(List<String> datos, HttpServletRequest request);

	UpdateResponseDTO asociarAsistencia(List<String> datos, HttpServletRequest request);

	UpdateResponseDTO asociarSOJ(List<String> datos, HttpServletRequest request);
}
