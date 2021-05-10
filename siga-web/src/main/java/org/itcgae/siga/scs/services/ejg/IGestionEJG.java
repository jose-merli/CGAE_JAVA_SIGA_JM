package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;

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
	
	UpdateResponseDTO anadirExpedienteARemesa(List<EjgItem> datos, HttpServletRequest request);
	
	UpdateResponseDTO descargarExpedientesJG(List<EjgItem> datos, HttpServletRequest request);
	
	InsertResponseDTO insertaDatosGenerales(EjgItem datos, HttpServletRequest request);
	
	UpdateResponseDTO actualizaDatosGenerales(EjgItem datos, HttpServletRequest request);

	UpdateResponseDTO borrarEstado(List<EjgItem> datos, HttpServletRequest request);

	InsertResponseDTO nuevoEstado(List<EjgItem> datos, HttpServletRequest request);
	
	UpdateResponseDTO borrarFamiliar(List<EjgItem> datos, HttpServletRequest request);

	UpdateResponseDTO borrarRelacion(List<EjgItem> datos, HttpServletRequest request);

	UpdateResponseDTO guardarImpugnacion(EjgItem datos, HttpServletRequest request);

	UpdateResponseDTO guardarResolucion(EjgItem datos, HttpServletRequest request);

	UpdateResponseDTO guardarInformeCalificacion(EjgItem datos, HttpServletRequest request);

	UpdateResponseDTO borrarInformeCalificacion(EjgItem datos, HttpServletRequest request);
	
	UpdateResponseDTO descargarInformeCalificacion(EjgItem datos, HttpServletRequest request);
	
	UpdateResponseDTO descargarDocumentacion(EjgItem datos, HttpServletRequest request);

	List<ScsEjgPrestacionRechazada> searchPrestacionesRechazadas(EjgItem ejgItem, HttpServletRequest request);
}
