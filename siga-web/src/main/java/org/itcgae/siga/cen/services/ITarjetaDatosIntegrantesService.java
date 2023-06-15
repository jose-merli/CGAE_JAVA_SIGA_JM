package org.itcgae.siga.cen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosLiquidacionIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosLiquidacionIntegrantesSearchItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesDeleteDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ITarjetaDatosIntegrantesService {

	public DatosIntegrantesDTO searchIntegrantesData(int numPagina, DatosIntegrantesSearchDTO datosIntegrantesSearchDTO,	HttpServletRequest request);
	
	public ComboDTO getProvinces(HttpServletRequest request);
	
	public ComboDTO getColegios(HttpServletRequest request);
	
	public ComboDTO getCargos(HttpServletRequest request);
	
	public UpdateResponseDTO updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, HttpServletRequest request);
	
	public UpdateResponseDTO createMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteMember(TarjetaIntegrantesDeleteDTO[] tarjetaIntegrantesDeleteDTO,HttpServletRequest request);
	
	public StringDTO provinciaColegio(StringDTO idInstitucionIntegrante, HttpServletRequest request);

	public DatosLiquidacionIntegrantesSearchDTO listadoHistoricoLiquidacion(DatosLiquidacionIntegrantesSearchItem datosLiquidacionDTO, HttpServletRequest request);

	public DeleteResponseDTO eliminarLiquidacion(List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacionDTO, HttpServletRequest request);

	public InsertResponseDTO insertHistoricoLiquidacion(List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacionDTO,
			HttpServletRequest request);

	public Boolean buscarPagosColegiados(DatosLiquidacionIntegrantesSearchItem datosLiquidacionDTO,
			HttpServletRequest request);
	
	
	
	
}

