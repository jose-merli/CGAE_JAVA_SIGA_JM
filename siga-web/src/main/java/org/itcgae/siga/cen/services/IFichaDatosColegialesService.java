package org.itcgae.siga.cen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaDatosColegialesService {
	
	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboDTO getTratamiento(HttpServletRequest request);

	public ComboDTO getLabel( HttpServletRequest request);
	
	public ComboDTO getTypeInsurances(HttpServletRequest request);

	public ColegiadoDTO datosColegialesSearch(int numPagina, ColegiadoItem colegiadoItem,
			HttpServletRequest request);

	public UpdateResponseDTO datosColegialesUpdate(ColegiadoItem colegiadoItem,
			HttpServletRequest request);

	public InsertResponseDTO datosColegialesInsertEstado(ColegiadoItem colegiadoItem,
			HttpServletRequest request);
	
	public UpdateResponseDTO datosColegialesUpdateEstados(List<ColegiadoItem> listColegiadoItem,
			HttpServletRequest request);
	
	public UpdateResponseDTO datosColegialesDeleteEstado(ColegiadoItem colegiadoItem,
			HttpServletRequest request);
	
	public StringDTO getNumColegiado(HttpServletRequest request);

	public ColegiadoDTO datosColegialesSearchActual(int numPagina, ColegiadoItem colegiadoItem,
			HttpServletRequest request);
	
	public ColegiadoDTO datosColegialesSearchHistor(ColegiadoItem colegiadoItem,
			HttpServletRequest request);
	
	public ColegiadoDTO sendMailsOtherCentres(String[] centresToSendMails,
			HttpServletRequest request);
	
	public StringDTO getTurnosGuardias(ColegiadoItem colegiadoItem,
            HttpServletRequest request);

	public UpdateResponseDTO datosColegialesUpdateMasivo(ColegiadoItemDTO listColegiadoItem,
			HttpServletRequest request);

	public StringDTO getCuentaContableSJCS(ColegiadoItem colegiadoItem,
			HttpServletRequest request);
	
//	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
}
