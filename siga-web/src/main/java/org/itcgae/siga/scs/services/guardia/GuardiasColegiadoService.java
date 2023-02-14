package org.itcgae.siga.scs.services.guardia;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasKey;
import org.springframework.transaction.annotation.Transactional;

public interface GuardiasColegiadoService {
	//obtener datos para las tarjetas de la ficha
	public GuardiasDTO getGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	public TurnosDTO getTurnoGuardiaColeg(TurnosItem turnosItem, HttpServletRequest request);
	public List<DatosCalendarioItem> getCalendarioColeg(String[] datosCalendarioItem, HttpServletRequest request);
	public ColegiadoDTO getColegiado(ColegiadoItem guardiasItem, HttpServletRequest request);
	public PermutaDTO getPemutasColeg(PermutaItem permutaItem, HttpServletRequest request);
	
	//acciones tarjeta Datos Generales de guardias de colegiado
	public UpdateResponseDTO  updateGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	public InsertResponseDTO  insertGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	
	//acciones tarjeta Sustituciones Guardias de Colegiado
	public UpdateResponseDTO  sustituirGuardiaColeg(String[] datos, HttpServletRequest request) throws Exception;
	public void validarGuardiaColegiado(ScsCabeceraguardias guardiaKey);

	// Comprobaciones para la sustitución de letrado
	public ResponseDataDTO existeFacturacionGuardiaColegiado(String[] datos, HttpServletRequest request);
	public ResponseDataDTO existeAsistenciasGuardiaColegiado(String[] datos, HttpServletRequest request);

	public String getIdConjuntoGuardia(String idGuardia, HttpServletRequest request);
	public ComboDTO getTurnoInscrito(String idPersona, HttpServletRequest request);
	public ComboGuardiasFuturasDTO getGuardiaDestinoInscrito(GuardiasItem guardiaItem, HttpServletRequest request);
	
	//acciones tarjeta Permutas Guardias de Colegiado.
	public UpdateResponseDTO  validarPermuta(List<PermutaItem> permutas, HttpServletRequest request) throws Exception;
	public InsertResponseDTO permutarGuardia(PermutaItem permutaItem, HttpServletRequest request) throws Exception;
	
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@INICIO
	public Date getFechaSolicitante(String idPersona, Short idCalendarioGuardias, Short idGuardia, HttpServletRequest request);
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@FIN 
}
