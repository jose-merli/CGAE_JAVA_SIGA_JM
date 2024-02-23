package org.itcgae.siga.scs.services.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.ComunicacionesDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaItem;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.GuardaJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.LetradoDesignaDTO;
import org.itcgae.siga.DTOs.scs.ListDTO;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaLetradosDesignaItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IDesignacionesService {

	/**
	 * busquedaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item,
			HttpServletRequest request);

	/**
	 * insertaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	public InsertResponseDTO insertaJustificacionExpres(ActuacionesJustificacionExpressItem item,
			HttpServletRequest request);

	/**
	 * actualizaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	public UpdateResponseDTO actualizaJustificacionExpres(GuardaJustificacionExpressItem item,
			HttpServletRequest request);

	/**
	 * eliminaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	public DeleteResponseDTO eliminaJustificacionExpres(List<ActuacionesJustificacionExpressItem> item,
			HttpServletRequest request);

	public List<DesignaItem> busquedaDesignas(DesignaItem item, HttpServletRequest request);
	
	public List<DesignaItem> busquedaNuevaDesigna(@RequestBody DesignaItem item, HttpServletRequest request, boolean isNoColegiado);

	public List<DesignaItem> busquedaProcedimientoDesignas(DesignaItem item, HttpServletRequest request);

	public List<DesignaItem> busquedaModuloDesignas(DesignaItem item, HttpServletRequest request);

	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request,
			Boolean historico);

	public UpdateResponseDTO updateDetalleDesigna(DesignaItem designaItem, HttpServletRequest request);

	public UpdateResponseDTO updateDatosAdicionales(DesignaItem designaItem, HttpServletRequest request);

	public UpdateResponseDTO deleteContrario(ScsContrariosdesigna contrario, HttpServletRequest request);

	public List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem designa,
			HttpServletRequest request);

	public DeleteResponseDTO deleteInteresado(ScsDefendidosdesigna item, HttpServletRequest request);

	public InsertResponseDTO insertInteresado(ScsDefendidosdesigna item, HttpServletRequest request);

	public InsertResponseDTO insertContrario(ScsContrariosdesigna contrario, HttpServletRequest request);

	public ActuacionDesignaDTO busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request);

	public UpdateResponseDTO anularReactivarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			boolean anular, HttpServletRequest request);

	public UpdateResponseDTO validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, boolean validar,
			HttpServletRequest request);

	public DeleteResponseDTO eliminarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			HttpServletRequest request);

	public InsertResponseDTO guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request);
	
	public UpdateResponseDTO actualizarActDesigna(ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateJustiActDesigna(ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request);

	ResponseDataDTO compruebaLetradoInscritoEnTurno(ScsDesignasletrado designaLetrado, HttpServletRequest request) throws Exception;

	public ComboDTO comboMotivosCambioActDesigna(HttpServletRequest request);

	public InsertResponseDTO createDesigna(DesignaItem designaItem, HttpServletRequest request);

	public ProcuradorDTO busquedaProcurador(List<String> procurador, HttpServletRequest request);
	
	public ProcuradorDTO compruebaProcurador(String procurador, HttpServletRequest request);
	
	public ProcuradorDTO compruebaFechaProcurador(ProcuradorItem procurador, HttpServletRequest request);

	ComboDTO comboTipoMotivo(HttpServletRequest request);

	public InsertResponseDTO guardarProcurador(ProcuradorItem procuradorItem, HttpServletRequest request);
	
	public String existeDesginaJuzgadoProcedimiento(DesignaItem designa, HttpServletRequest request);

	public List<DesignaItem> getDatosAdicionales(DesignaItem designa, HttpServletRequest request);

	public UpdateResponseDTO updateRepresentanteInteresado(ScsDefendidosdesigna item, HttpServletRequest request);

	public ComboDTO comboPrisiones(HttpServletRequest request);

	public UpdateResponseDTO updateRepresentanteContrario(ScsContrariosdesigna item, HttpServletRequest request);

	public UpdateResponseDTO updateAbogadoContrario(ScsContrariosdesigna item, HttpServletRequest request);

	public UpdateResponseDTO updateProcuradorContrario(ScsContrariosdesigna item, HttpServletRequest request);

	public ColegiadoItemDTO SearchAbogadoByIdPersona(String idPersona, HttpServletRequest request);

	public RelacionesDTO busquedaRelaciones(List<String> relaciones, HttpServletRequest request);
	
	public ComboDTO getPartidaPresupuestariaDesigna(HttpServletRequest request, DesignaItem designaItem);
	
	public UpdateResponseDTO updatePartidaPresupuestaria(DesignaItem designaItem, HttpServletRequest request);
	
	public DeleteResponseDTO deleteDesigna(List<DesignaItem> item, HttpServletRequest request);

	public DesignaItem busquedaDesignaActual(ScsDesigna designa, HttpServletRequest request);

	public List<ListaLetradosDesignaItem> busquedaLetradosDesigna(ScsDesigna item, HttpServletRequest request);
			
	public DeleteResponseDTO eliminarRelacion(List<String> datos, HttpServletRequest request);
	
	public EnviosMasivosDTO busquedaComunicaciones(List<String> comunicaciones, HttpServletRequest request);

	public ActuacionDesignaItem getHistorioAccionesActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request);
	
	public LetradoDesignaDTO getLetradoDesigna(AsuntosClaveJusticiableItem asuntoClave, HttpServletRequest request);
	
	public UpdateResponseDTO updateDesigna(DesignaItem designaItem, HttpServletRequest request);

	public UpdateResponseDTO updateLetradoDesigna(String[] item, HttpServletRequest request) throws Exception;
	
	public InsertResponseDTO subirDocumentoActDesigna(MultipartHttpServletRequest request);
	
	public DocumentoActDesignaDTO getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem,
			HttpServletRequest request);
	
	public ResponseEntity<InputStreamResource> descargarDocumentosActDesigna(
			List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request);
	
	public DeleteResponseDTO eliminarDocumentosActDesigna(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem,
			HttpServletRequest request);
	
	public DocumentoDesignaDTO getDocumentosPorDesigna(DocumentoDesignaItem documentoDesignaItem,
			HttpServletRequest request);
	
	public InsertResponseDTO subirDocumentoDesigna(MultipartHttpServletRequest request);
	
	public DeleteResponseDTO eliminarDocumentosDesigna(List<DocumentoDesignaItem> listaDocumentoDesignaItem,
			HttpServletRequest request);
	
	public ResponseEntity<InputStreamResource> descargarDocumentosDesigna(
			List<DocumentoDesignaItem> listaDocumentoDesignaItem, HttpServletRequest request);

	public InsertResponseDTO asociarEjgDesigna(List<String> item, HttpServletRequest request);
	
	public String busquedaJuzgadoDesignas(Integer idJuzgado, HttpServletRequest request);
	
	public UpdateResponseDTO actualizarPartidaPresupuestariaActDesigna(ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request);
	
	public ListDTO getDelitos(DesignaItem designaItem, HttpServletRequest request);

	InsertResponseDTO getPreDesignaEJG(ScsEjgdesigna item, HttpServletRequest request) throws Exception;

	EjgDesignaDTO getEjgDesigna(DesignaItem datos, HttpServletRequest request);

	UpdateResponseDTO guardarProcuradorEJG(ProcuradorItem procurador, HttpServletRequest request);

	public UpdateResponseDTO asociarAsistenciaDesigna(List<String> designaItem, HttpServletRequest request);
	
	UpdateResponseDTO eliminarRelacionAsistenciaDesigna(RelacionesItem datos, HttpServletRequest request);

	ScsEjg getEJG(EjgItem item, HttpServletRequest request);

	public StringDTO formatoProcedimiento(HttpServletRequest request);

	ResponseEntity<InputStreamResource> descargarDocumentosDesignaJustificacionExpres(
			JustificacionExpressItem justificacionExpressItem, HttpServletRequest request);

	ResponseEntity<InputStreamResource> descargarDocumentosActDesignaJustificacionExpres(
			DocumentoDesignaItem documentoDesignaItem, HttpServletRequest request);

	public StringDTO queryImprimirJustificacionExpress(JustificacionExpressItem item, HttpServletRequest request);

	InsertResponseDTO subirDocumentoDesignaJustificacionExpres(MultipartHttpServletRequest request);

	public InsertResponseDTO updateInteresado(String[] item, HttpServletRequest request);

	public InsertResponseDTO updateContrario(String[] item, HttpServletRequest request);

	public StringDTO busquedaTotalRegistrosDesignas(DesignaItem designaItem, HttpServletRequest request);

	public TurnosDTO busquedaColaOficioPrimerLetrado(TurnosItem turnosItem, HttpServletRequest request);

	public RelacionesDTO busquedaRelacionesConIdExpedienteExt(List<String> relaciones, HttpServletRequest request);


}
