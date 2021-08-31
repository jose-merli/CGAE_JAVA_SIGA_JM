package org.itcgae.siga.scs.services.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AsistenciaService {
	
	public ComboDTO getTurnosByColegiadoFecha(HttpServletRequest request, String guardiaDia, String idPersona);
	
	public ComboDTO getTiposAsistenciaColegio (HttpServletRequest request, String idTurno, String idGuardia);
	
	public ComboDTO getColegiadosGuardiaDia (HttpServletRequest request, String idTurno, String idGuardia, String guardiaDia);
	
	public TarjetaAsistenciaResponseDTO searchAsistencias (HttpServletRequest request, FiltroAsistenciaItem filtro);

	public ComboDTO getJuzgados (HttpServletRequest request, String idTurno);
	
	public ComboDTO getComisarias (HttpServletRequest request, String idTurno);
	
	public DeleteResponseDTO guardarAsistenciasExpres(HttpServletRequest request, List<TarjetaAsistenciaResponseItem> asistencias);
	
	public TarjetaAsistenciaResponseDTO searchAsistenciasByIdSolicitud(HttpServletRequest request, String idSolicitud);
	
	public StringDTO getDefaultTipoAsistenciaColegio (HttpServletRequest request);
	
	public InsertResponseDTO guardarAsistencia(HttpServletRequest request, List<TarjetaAsistenciaResponseItem> asistencias);

	public UpdateResponseDTO updateEstadoAsistencia(HttpServletRequest request, List<TarjetaAsistenciaResponseItem> asistencias);
	
	public TarjetaAsistenciaResponseDTO buscarTarjetaAsistencias(HttpServletRequest request, String anioNumero);

	public UpdateResponseDTO asociarJusticiable(HttpServletRequest request, JusticiableItem justiciable, String anioNumero, String actualizaDatos);

	public UpdateResponseDTO desasociarJusticiable(HttpServletRequest request, JusticiableItem justiciable, String anioNumero);

	public List<ListaContrarioJusticiableItem> searchListaContrarios(HttpServletRequest request, String anioNumero, boolean mostrarHistorico);

	public InsertResponseDTO asociarContrario(HttpServletRequest request, List<JusticiableItem> justiciables, String anioNumero);

	public UpdateResponseDTO desasociarContrario(HttpServletRequest request, List<ListaContrarioJusticiableItem> contrarios, String anioNumero);

	public TarjetaDefensaJuridicaDTO searchTarjetaDefensaJuridica(HttpServletRequest request, String anioNumero);

	public UpdateResponseDTO guardarTarjetaDefensaJuridica(HttpServletRequest request, TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem, String anioNumero);

	public TarjetaObservacionesDTO searchTarjetaObservaciones(HttpServletRequest request, String anioNumero);

	public UpdateResponseDTO guardarTarjetaObservaciones(HttpServletRequest request, TarjetaObservacionesItem tarjetaObservacionesItem, String anioNumero);

	public RelacionesDTO searchRelaciones(HttpServletRequest request, String anioNumero);

	public UpdateResponseDTO asociarDesigna(HttpServletRequest request, String anioNumero,  DesignaItem designaItem, String copiarDatos);

	public UpdateResponseDTO eliminarRelacion(HttpServletRequest request, String anioNumero,  List<RelacionesItem> asuntos);
}
