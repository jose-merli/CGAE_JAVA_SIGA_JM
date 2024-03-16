package org.itcgae.siga.scs.services.guardia;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.CalendarioProgramadosDTO;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosSalidaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesResponse;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioyProgramacionItem;
import org.itcgae.siga.DTOs.scs.DeleteCalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DeleteIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.GuardiaCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDTO;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesResponseDTO;
import org.itcgae.siga.DTOs.scs.LetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.RangoFechasItem;
import org.itcgae.siga.DTOs.scs.SaveIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;


public interface GuardiasService {

	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request);
	
	public boolean getSeparar(GuardiasItem guardiasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request);

	public UpdateResponseDTO activateGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request);

	public UpdateResponseDTO updateGuardia(GuardiasItem guardiasItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateColaGuardia(InscripcionGuardiaDTO guardiaBody, HttpServletRequest request);
	
	public GuardiasItem detalleGuardia(GuardiasItem guardiaTurno, HttpServletRequest request);

	public InsertResponseDTO createGuardia(GuardiasItem guardiasItem, HttpServletRequest request);

	public GuardiasItem resumenGuardia(GuardiasItem guardiasItem, HttpServletRequest request);

	public GuardiasDTO tarjetaIncompatibilidades(String idGuardia, String idTurno, HttpServletRequest request);

	public ComboDTO getBaremos(String idGuardia, HttpServletRequest request);

	public DatosCalendarioItem getCalendario(String idGuardia, HttpServletRequest request);

	public GuardiasItem resumenConfiguracionCola(GuardiasItem guardia, HttpServletRequest request);

	public InscripcionGuardiaDTO searchColaGuardia(GuardiasItem guardiasItem, HttpServletRequest request, String from);

	public UpdateResponseDTO updateUltimoCola(GuardiasItem guardiasItem, HttpServletRequest request);

	public GuardiasDTO resumenIncompatibilidades(GuardiasItem guardiasItem, HttpServletRequest request);

	public TurnosDTO resumenTurno(String idTurno, HttpServletRequest request);

	public UpdateResponseDTO guardarColaGuardias(List<InscripcionGuardiaItem> inscripciones,
			HttpServletRequest request);

	public LetradosGuardiaDTO letradosGuardia(String idTurno, String idGuardia, HttpServletRequest request);
	
	public IncompatibilidadesDTO getIncompatibilidades(IncompatibilidadesDatosEntradaItem incompBody,
			HttpServletRequest request);

	public DeleteResponseDTO deleteIncompatibilidades(
			List<DeleteIncompatibilidadesDatosEntradaItem> deleteIncompatibilidadesBody, HttpServletRequest request);

	public DeleteResponseDTO saveIncompatibilidades(List<SaveIncompatibilidadesDatosEntradaItem> incompatibilidadesBody,
			HttpServletRequest request);

	public ComboIncompatibilidadesResponse getCombo(ComboIncompatibilidadesDatosEntradaItem comboIncompatibilidadesDatosEntradaItem, HttpServletRequest request);

	public List<CalendariosProgDatosSalidaItem> getCalendariosProg(CalendariosProgDatosEntradaItem calendarioProgBody,
			HttpServletRequest request);

	public CalendarioProgramadosDTO getCalendarioProgramado(CalendariosProgDatosEntradaItem calendarioProgBody,
			HttpServletRequest request);
	
	public List<DatosCalendarioProgramadoItem> getCalendarioProgramadoTarjeta(CalendariosProgDatosEntradaItem calendarioProgBody,
			HttpServletRequest request);
	
	public DatosCalendarioProgramadoItem getLastCalendarioProgramado(CalendariosProgDatosEntradaItem calendarioProgBody,
			HttpServletRequest request);

	public InsertResponseDTO subirDocumentoActDesigna(MultipartHttpServletRequest request);
			
	public DocumentoActDesignaDTO getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem,
			HttpServletRequest request);
			
	public ResponseEntity<InputStreamResource> descargarDocumentosActDesigna(
			List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request);
			
	public DeleteResponseDTO eliminarDocumentosActDesigna(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem,
			HttpServletRequest request);

	public List<GuardiaCalendarioItem> getGuardiasFromCalendar(String idCalendar, String fechaDesde, String fechaHasta, HttpServletRequest request);


	public List<GuardiaCalendarioItem> guardiasFromCojunto(HttpServletRequest request, String idConjuntoGuardia);

	public List<RangoFechasItem> getFechasProgramacionGuardia(String idGuardia, HttpServletRequest request);

	public InsertResponseDTO insertGuardiaToConjunto(HttpServletRequest request, String idConjuntoGuardia, String idTurno, String idGuardia, String fechaDesde, String fechaHasta, List<GuardiaCalendarioItem> item);

	public InsertResponseDTO insertGuardiaToCalendar(Boolean update,HttpServletRequest request, String idCalendar,
			List<GuardiaCalendarioItem> itemList) throws Exception;

	public InsertResponseDTO deleteGuardiaFromCalendar(HttpServletRequest request, String idCalendar,
			List<GuardiaCalendarioItem> itemList);

	public InsertResponseDTO updateCalendarioProgramado(HttpServletRequest request,
			DatosCalendarioProgramadoItem calendarioItem);

	public InsertResponseDTO newCalendarioProgramado(HttpServletRequest request,
			DatosCalendarioProgramadoItem calendarioItem);

	public InsertResponseDTO generarCalendario(HttpServletRequest request, DatosCalendarioProgramadoItem programacionItem) throws Exception;

	public DatosDocumentoItem descargarExcelLog(HttpServletRequest request, DatosCalendarioyProgramacionItem list);
			

	public InscripcionesResponseDTO getInscripciones(InscripcionDatosEntradaDTO inscripcionesDTO,
			HttpServletRequest request);
	
	
	

	GuardiasDTO busquedaGuardiasColegiado(GuardiasItem guardiaItem, HttpServletRequest request);

	public UpdateResponseDTO validarSolicitudGuardia(GuardiasItem guardiasItem, HttpServletRequest request);

	public UpdateResponseDTO desvalidarGuardiaColegiado(GuardiasItem guardiasItem, HttpServletRequest request);

	public DeleteResponseDTO eliminarGuardiaColegiado(GuardiasItem guardiasItem, HttpServletRequest request);

	public void generarCalendarioAsync();

	public ByteArrayInputStream descargarZIPExcelLog(HttpServletRequest request, List<DatosCalendarioyProgramacionItem> programacionItem);

	UpdateResponseDTO denegarInscripciones(BusquedaInscripcionItem denegarBody, HttpServletRequest request);

	UpdateResponseDTO validarInscripciones(BusquedaInscripcionItem validarBody, HttpServletRequest request);

	public StringDTO getTipoDiaGuardia(HttpServletRequest var1, String var2, String var3);

	public boolean validarIncompatibilidadGuardia(String idInstitucion, String idTurno, String idGuardia,
			ArrayList arrayDiasGuardia, String idPersona);

	public void insertarSaltoCompensacion(ScsSaltoscompensaciones salto) throws Exception;

	public ResponseEntity<InputStreamResource> descargarLogCalendarioProgramado(DatosCalendarioProgramadoItem item,
			HttpServletRequest request) throws Exception;

	public DeleteResponseDTO deleteCalendariosProgramados(List<DeleteCalendariosProgDatosEntradaItem> listDeleteCalBody,
			HttpServletRequest request);

	public ComboDTO getComboDiasDisponiblesGC(HttpServletRequest request, GuardiasItem guardiaDia);
	
	public ComboDTO getFechasByCabeceraGuardiaColegiado(HttpServletRequest request, GuardiasItem guardiaDia);

}
