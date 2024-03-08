package org.itcgae.siga.scs.services.justiciables;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.scs.JusticiableDTO;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.DTOs.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.scs.ScsUnidadfamiliarejgDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;

public interface IGestionJusticiableService {
	
	public ComboDTO getMinusvalias(HttpServletRequest request);

	public ComboDTO getProfesiones(HttpServletRequest request);
	
	public ComboDTO getPoblacion(String idPoblacion, HttpServletRequest request);
	
	public ComboDTO getTipoVias(HttpServletRequest request, String idTipoViaJusticiable);

	public JusticiableDTO searchJusticiable (JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);

	public InsertResponseDTO createJusticiable(JusticiableItem justiciableItem,  HttpServletRequest request);
	
	public UpdateResponseDTO updateJusticiable(JusticiableItem justiciableItem, boolean datosGenerales, HttpServletRequest request);
	
	public UpdateResponseDTO updateJusticiableDatosPersonales(JusticiableItem justiciableItem, boolean datosGenerales, HttpServletRequest request);
	
	public JusticiableTelefonoDTO getTelefonos(JusticiableItem justiciableItem, HttpServletRequest request);
	
	public AsuntosJusticiableDTO searchAsuntosJusticiable(String idPersona, HttpServletRequest request, String origen);
	
	public AsuntosJusticiableDTO searchAsuntosConClave(List<AsuntosClaveJusticiableItem> asuntosClaveList, boolean fromJusticiable, HttpServletRequest request);

	public JusticiableDTO getJusticiableByNif(JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);

	public JusticiableDTO getJusticiableByIdPersona(JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);

	public UpdateResponseDTO associateRepresentante(JusticiableItem justiciableItem, HttpServletRequest request);

	public UpdateResponseDTO disassociateRepresentante(JusticiableItem justiciableItem, HttpServletRequest request);

	public ComboDTO getGruposLaborales(HttpServletRequest request);

	public ComboDTO getParentesco(HttpServletRequest request);

	public ComboDTO getTiposIngresos(HttpServletRequest request);

	public UpdateResponseDTO updateUnidadFamiliar(UnidadFamiliarEJGItem unidadFamiliarEJGItem, HttpServletRequest request);

	public ScsUnidadfamiliarejgDTO getSolicitante(EjgItem datos, HttpServletRequest request);	
	
	public UpdateResponseDTO asociarDesignacion(ScsDefendidosdesigna datos, HttpServletRequest request);

	public UpdateResponseDTO asociarEJG(List<String> idpersonajg, HttpServletRequest request);

	public UpdateResponseDTO asociarAsistencia(List<String> itemAsistencia, HttpServletRequest request);
	
	public UpdateResponseDTO asociarSOJ(List<String> itemSOJ, HttpServletRequest request);
}
