package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonFormat;

@Service
public class CenTestUtils{
	
	
	public SolicitudModificacionSearchDTO getSolicitudModificacionSearchDTOSimulado() {
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = new SolicitudModificacionSearchDTO();
		solicitudModificacionSearchDTO.setEstado("20");
		solicitudModificacionSearchDTO.setTipoModificacion("35");
		
		return solicitudModificacionSearchDTO;
	}
	
	public SolModificacionItem getSolModificacionItemSimulado() {
		SolModificacionItem solModificacionItem = new SolModificacionItem();
		
		solModificacionItem.setIdSolicitud("1");
		solModificacionItem.setEspecifica("1");
		solModificacionItem.setEstado("10");
		solModificacionItem.setIdPersona("2005001213");
		solModificacionItem.setMotivo("Cambiar cuenta");
		solModificacionItem.setNombre("JOSÉ PÉREZ SARMIENTO");
		solModificacionItem.setNumColegiado("1234");
		
		return solModificacionItem;
	}
	
	public List<SolModificacionItem> getListSolModificacionItemSimulado() {
		List<SolModificacionItem> listSolModificacionItem = new ArrayList<SolModificacionItem>();
		
		listSolModificacionItem.add(getSolModificacionItemSimulado());
		
		return listSolModificacionItem;
	}
	
	
	public SolModificacionDTO getSolModificacionDTOSimulado() {
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();
		
		solModificacionDTO.setSolModificacionItems(getListSolModificacionItemSimulado());
		
		return solModificacionDTO;
	}
	
	public CenSolicmodicuentas getCenSolicmodicuentasSimulado() {
		CenSolicmodicuentas cenSolicmodicuentas = new CenSolicmodicuentas();
		
		cenSolicmodicuentas.setAbonocargo("T");
		cenSolicmodicuentas.setAbonosjcs("1");
		cenSolicmodicuentas.setCodigosucursal("3902");
		cenSolicmodicuentas.setDigitocontrol("99");
		cenSolicmodicuentas.setIban("ES5600013902990000353161");
		cenSolicmodicuentas.setNumerocuenta("0000353161");
		cenSolicmodicuentas.setTitular("JOSÉ PÉREZ SARMIENTO");
		
		return cenSolicmodicuentas;
	}
	
	public List<CenSolicmodicuentas> getListCenSolicmodicuentasSimulado() {
		List<CenSolicmodicuentas> listCenSolicmodicuentas = new ArrayList<CenSolicmodicuentas>();
		
		listCenSolicmodicuentas.add(getCenSolicmodicuentasSimulado());
		
		return listCenSolicmodicuentas;
	}
}
