package org.itcgae.siga.scs.services.ejg;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;

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
}
