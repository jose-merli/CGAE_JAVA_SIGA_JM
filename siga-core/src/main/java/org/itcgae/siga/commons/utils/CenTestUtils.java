package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.springframework.stereotype.Service;

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
		solModificacionItem.setCodigo("1");
		solModificacionItem.setIdTipoModificacion("35");
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
	
	public CenSolimodidirecciones getCenSolimodidireccionesSimulado() {
		CenSolimodidirecciones cenSolimodidirecciones = new CenSolimodidirecciones();
		
		cenSolimodidirecciones.setIdsolicitud((long) 1);
		cenSolimodidirecciones.setIdestadosolic((short) 10);
		cenSolimodidirecciones.setIdpersona((long) 2005001213);
		cenSolimodidirecciones.setFechamodificacion(new Date());
		cenSolimodidirecciones.setUsumodificacion(2);
		cenSolimodidirecciones.setIddireccion((long) 1);
		cenSolimodidirecciones.setCodigopostal("35200");
		cenSolimodidirecciones.setTelefono1("654789876");
		cenSolimodidirecciones.setCorreoelectronico("correo@gmail.com");
		
		return cenSolimodidirecciones;
	}
	
	public List<CenSolimodidirecciones> getListCenSolimodidireccionesSimulado() {
		List<CenSolimodidirecciones> listCenSolimodidirecciones = new ArrayList<CenSolimodidirecciones>();
		
		listCenSolimodidirecciones.add(getCenSolimodidireccionesSimulado());
		
		return listCenSolimodidirecciones;
	}
	
	public CenSolicitmodifdatosbasicos getCenSolicitmodifdatosbasicosSimulado() {
		CenSolicitmodifdatosbasicos cenSolicitmodifdatosbasicos = new CenSolicitmodifdatosbasicos();
		
		cenSolicitmodifdatosbasicos.setIdpersona((long) 2005001213); 
		cenSolicitmodifdatosbasicos.setIdsolicitud((short) 1);
		cenSolicitmodifdatosbasicos.setIdlenguaje("1");
		cenSolicitmodifdatosbasicos.setIdinstitucion((short) 2000); 
		cenSolicitmodifdatosbasicos.setIdestadosolic((short) 10);
		cenSolicitmodifdatosbasicos.setIdpersona((long) 2005001213);
		cenSolicitmodifdatosbasicos.setFechamodificacion(new Date());
		cenSolicitmodifdatosbasicos.setUsumodificacion(2);
		
		return cenSolicitmodifdatosbasicos;
	}
	
	public List<CenSolicitmodifdatosbasicos> getListCenSolicitmodifdatosbasicosSimulado() {
		List<CenSolicitmodifdatosbasicos> listCenSolicitmodifdatosbasicos = new ArrayList<CenSolicitmodifdatosbasicos>();
		
		listCenSolicitmodifdatosbasicos.add(getCenSolicitmodifdatosbasicosSimulado());
		
		return listCenSolicitmodifdatosbasicos;
	}
	
	public CenSolicmodifexportarfoto getCenSolicmodifexportarfotoSimulado() {
		CenSolicmodifexportarfoto cenSolicmodifexportarfoto = new CenSolicmodifexportarfoto();
		
		cenSolicmodifexportarfoto.setIdpersona((long) 2005001213);
		cenSolicmodifexportarfoto.setIdsolicitud((short) 1);
		cenSolicmodifexportarfoto.setExportarfoto("1");
		
		return cenSolicmodifexportarfoto;
	}
	
	public List<CenSolicmodifexportarfoto> getListCenSolicmodifexportarfotoSimulado() {
		List<CenSolicmodifexportarfoto> listCenSolicmodifexportarfoto = new ArrayList<CenSolicmodifexportarfoto>();
		
		listCenSolicmodifexportarfoto.add(getCenSolicmodifexportarfotoSimulado());
		
		return listCenSolicmodifexportarfoto;
	}
	
	public CenPersona getCenPersonaSimulado() {
		CenPersona cenPersona = new CenPersona();
		
		cenPersona.setIdpersona((long) 2005001213);
	
		return cenPersona;
	}
	
	public List<CenPersona> getListCenPersonaSimulado() {
		List<CenPersona> listCenPersona = new ArrayList<CenPersona>();
		
		listCenPersona.add(getCenPersonaSimulado());
		
		return listCenPersona;
	}
	
	public NewIdDTO getNewIdDTOSimulado() {
		NewIdDTO newIdDTO = new NewIdDTO();
		
		newIdDTO.setNewId("1");
	
		return newIdDTO;
	}
	
	public CenCliente getCenClienteSimulado() {
		CenCliente cenCliente = new CenCliente();
		
		cenCliente.setIdpersona((long) 2005001213);
		cenCliente.setIdlenguaje("1");
		cenCliente.setCaracter("P");
		cenCliente.setComisiones(SigaConstants.DB_FALSE);
		cenCliente.setExportarfoto(SigaConstants.DB_FALSE);
		cenCliente.setFechaalta(new Date());
		cenCliente.setFechamodificacion(new Date());
		cenCliente.setGuiajudicial(SigaConstants.DB_FALSE);
		cenCliente.setIdinstitucion((short) 2000);
		cenCliente.setIdlenguaje("1");
		cenCliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE));
		cenCliente.setLetrado("1");
		cenCliente.setPublicidad(SigaConstants.DB_FALSE);
		cenCliente.setUsumodificacion(2);
		
		return cenCliente;
	}
	
	public CenCuentasbancarias getCenCuentasbancariasSimulado() {
		CenCuentasbancarias cenCuentasbancarias = new CenCuentasbancarias();
		
		cenCuentasbancarias.setAbonocargo("T");
		cenCuentasbancarias.setIdcuenta((short) 1);
		cenCuentasbancarias.setIdpersona((long) 2005001213);
		cenCuentasbancarias.setFechamodificacion(new Date());
		cenCuentasbancarias.setIdinstitucion((short) 2000);
		cenCuentasbancarias.setUsumodificacion(2);
		
		return cenCuentasbancarias;
	}
	
	public SolModifDatosBancariosItem getSolModifDatosBancariosItemSimulado() {
		SolModifDatosBancariosItem solModifDatosBancariosItem = new SolModifDatosBancariosItem();
		
		solModifDatosBancariosItem.setIdPersona("2005001213");
		solModifDatosBancariosItem.setAbonoCargo("T");
		solModifDatosBancariosItem.setAbonoJCS("0");
		solModifDatosBancariosItem.setIdCuenta("1");
		solModifDatosBancariosItem.setCodigoSucursal("123223");
		solModifDatosBancariosItem.setDigitoControl("39");
		solModifDatosBancariosItem.setIban("ES6621000418401234567891");
		solModifDatosBancariosItem.setIdPersona("2005001213");
		
		return solModifDatosBancariosItem;
	}
}
