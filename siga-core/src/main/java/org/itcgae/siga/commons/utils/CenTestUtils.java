package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.DTOs.cen.SoliModifDatosBasicosItem;
import org.itcgae.siga.DTOs.cen.SoliModifFotoItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenPais;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenProvincias;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
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
		cenSolicmodicuentas.setIdcuenta((short) 1);
		cenSolicmodicuentas.setCodigosucursal("123223");
		cenSolicmodicuentas.setDigitocontrol("39");
		cenSolicmodicuentas.setIban("ES6621000418401234567891");
		cenSolicmodicuentas.setIdpersona((long) 2005001213);
		
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
//		cenSolimodidirecciones.setIdpais("191");
//		cenSolimodidirecciones.setIdpoblacion("04064000605");
//		cenSolimodidirecciones.setIdprovincia("04");
		cenSolimodidirecciones.setIdestadosolic((short) 10);
		cenSolimodidirecciones.setIdpersona((long) 2005001213);
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
		cenSolicmodifexportarfoto.setExportarfoto("No");
		
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
		cenCuentasbancarias.setAbonosjcs("1");
		cenCuentasbancarias.setIdcuenta((short) 1);
		cenCuentasbancarias.setIdpersona((long) 2005001213);
		cenCuentasbancarias.setFechamodificacion(new Date());
		cenCuentasbancarias.setIdinstitucion((short) 2000);
		cenCuentasbancarias.setUsumodificacion(2);
		cenCuentasbancarias.setCodigosucursal("123223");
		cenCuentasbancarias.setDigitocontrol("39");
		cenCuentasbancarias.setIban("ES6621000418401234567891");
	
		return cenCuentasbancarias;
	}
	
	public SolModifDatosBancariosItem getSolModifDatosBancariosItemSimulado() {
		SolModifDatosBancariosItem solModifDatosBancariosItem = new SolModifDatosBancariosItem();
		
		solModifDatosBancariosItem.setIdPersona("2005001213");
		solModifDatosBancariosItem.setAbonoCargo("T");
		solModifDatosBancariosItem.setAbonoJCS("Sí");
		solModifDatosBancariosItem.setIdCuenta("1");
		solModifDatosBancariosItem.setCodigoSucursal("123223");
		solModifDatosBancariosItem.setDigitoControl("39");
		solModifDatosBancariosItem.setIban("ES6621000418401234567891");
		
		return solModifDatosBancariosItem;
	}
	
	public CenDatoscv getCenDatoscvSimulado() {
		CenDatoscv cenDatoscv = new CenDatoscv();
		
		cenDatoscv.setIdcv((short) 1);
		cenDatoscv.setIdtipocv((short) 1);
		cenDatoscv.setIdinstitucion((short) 2000);
		cenDatoscv.setIdpersona((long) 2005001213);
//		cenDatoscv.setFechabaja(new Date());
//		cenDatoscv.setFechafin(new Date());
		cenDatoscv.setFechamodificacion(new Date());
		cenDatoscv.setUsumodificacion(2);
		
		return cenDatoscv;
	}
	
	public List<CenDatoscv> getListCenDatoscvSimulado() {
		List<CenDatoscv> listCenDatoscv = new ArrayList<CenDatoscv>();
		
		listCenDatoscv.add(getCenDatoscvSimulado());
		
		return listCenDatoscv;
	}
	
	public CenTiposcv getCenTiposcvSimulado() {
		CenTiposcv cenTiposcv = new CenTiposcv();
		
		cenTiposcv.setIdtipocv((short) 1);
		cenTiposcv.setDescripcion("1234");

		return cenTiposcv;
	}
	
	public GenRecursosCatalogos getGenRecursosCatalogosSimulado(String descripcion) {
		GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
		
		genRecursosCatalogos.setIdlenguaje("1");
		genRecursosCatalogos.setIdrecurso("1234");
		genRecursosCatalogos.setDescripcion(descripcion);

		return genRecursosCatalogos;
	}
	
	public CenTiposcvsubtipo1 getCenTiposcvsubtipo1Simulado() {
		CenTiposcvsubtipo1 cenTiposcvsubtipo1 = new CenTiposcvsubtipo1();
		
		cenTiposcvsubtipo1.setIdtipocv((short) 1);
		cenTiposcvsubtipo1.setIdtipocvsubtipo1((short) 1);
		cenTiposcvsubtipo1.setDescripcion("1234");

		return cenTiposcvsubtipo1;
	}
	
	public CenTiposcvsubtipo2 getCenTiposcvsubtipo2Simulado() {
		CenTiposcvsubtipo2 cenTiposcvsubtipo2 = new CenTiposcvsubtipo2();
		
		cenTiposcvsubtipo2.setIdtipocv((short) 1);
		cenTiposcvsubtipo2.setIdtipocvsubtipo2((short) 1);
		cenTiposcvsubtipo2.setDescripcion("1234");

		return cenTiposcvsubtipo2;
	}
	
	public SolModifDatosCurricularesItem getSolModifDatosCurricularesItemSimulado() {
		SolModifDatosCurricularesItem solModifDatosCurricularesItem = new SolModifDatosCurricularesItem();
		
		solModifDatosCurricularesItem.setIdPersona("2005001213");
		solModifDatosCurricularesItem.setIdCv("1");
		solModifDatosCurricularesItem.setIdSolicitud("1");
		solModifDatosCurricularesItem.setIdTipoCv("1");
		solModifDatosCurricularesItem.setIdTipoCvSubtipo1("1");
		solModifDatosCurricularesItem.setIdTipoCvSubtipo2("1");
		
		return solModifDatosCurricularesItem;
	}
	
	public CenSolicitudmodificacioncv getCenSolicitudmodificacioncvSimulado() {
		CenSolicitudmodificacioncv cenSolicitudmodificacioncv = new CenSolicitudmodificacioncv();
		
		cenSolicitudmodificacioncv.setIdpersona((long) 2005001213);
		cenSolicitudmodificacioncv.setIdcv((short) 1);
		cenSolicitudmodificacioncv.setIdsolicitud((long) 1);
		cenSolicitudmodificacioncv.setIdinstitucion((short)  2000);
		cenSolicitudmodificacioncv.setIdtipocv((short) 1);
		cenSolicitudmodificacioncv.setIdtipocvsubtipo1((short) 1);
		cenSolicitudmodificacioncv.setIdtipocvsubtipo2((short) 1);
		
		return cenSolicitudmodificacioncv;
	}
	
	public List<CenSolicitudmodificacioncv> getListCenSolicitudmodificacioncvSimulado() {
		List<CenSolicitudmodificacioncv> listCenSolicitudmodificacioncv = new ArrayList<CenSolicitudmodificacioncv>();
		
		listCenSolicitudmodificacioncv.add(getCenSolicitudmodificacioncvSimulado());
		
		return listCenSolicitudmodificacioncv;
	}
	
	public CenPais getCenPaisSimulado() {
		CenPais cenPais = new CenPais();
		
		cenPais.setIdpais("191");
		
		return cenPais;
	}
	
	public CenProvincias getCenProvinciasSimulado() {
		CenProvincias cenProvincias = new CenProvincias();
		
		cenProvincias.setIdprovincia("04");
		
		return cenProvincias;
	}
	
	public CenPoblaciones getCenPoblacionesSimulado() {
		CenPoblaciones cenPoblaciones = new CenPoblaciones();
		
		cenPoblaciones.setIdprovincia("04");
		cenPoblaciones.setIdpoblacion("04064000605");
		
		return cenPoblaciones;
	}
	
	public SoliModiDireccionesItem getSoliModiDireccionesItemSimulado() {
		SoliModiDireccionesItem soliModiDireccionesItem = new SoliModiDireccionesItem();
		
		soliModiDireccionesItem.setIdDireccion("1");
		soliModiDireccionesItem.setIdPersona("2005001213");
		soliModiDireccionesItem.codigoPostal("35200");
		soliModiDireccionesItem.setTelefono("654789876");
		soliModiDireccionesItem.correoElectronico("correo@gmail.com");
		
		return soliModiDireccionesItem;
	}
	
	public DatosDireccionesItem getDatosDireccionesItemSimulado() {
		DatosDireccionesItem datosDireccionesItem = new DatosDireccionesItem();
		
		datosDireccionesItem.setIdDireccion("1");
		datosDireccionesItem.setIdPersona("2005001213");
		datosDireccionesItem.setCodigoPostal("35200");
		datosDireccionesItem.setTelefono("654789876");
		datosDireccionesItem.setCorreoElectronico("correo@gmail.com");
		
		return datosDireccionesItem;
	}
	
	public List<DatosDireccionesItem> getListDatosDireccionesItemSimulado() {
		List<DatosDireccionesItem> listDatosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		
		listDatosDireccionesItem.add(getDatosDireccionesItemSimulado());
		
		return listDatosDireccionesItem;
	}
	
	public AdmLenguajes getAdmLenguajesSimulado() {
		AdmLenguajes admLenguajes = new AdmLenguajes();
		
		admLenguajes.setIdlenguaje("1");
		admLenguajes.setDescripcion("Descripción");
		
		return admLenguajes;
	}
	
	public SoliModifDatosBasicosItem getSoliModifDatosBasicosItemSimulado(String idPersona) {
		SoliModifDatosBasicosItem soliModifDatosBasicosItem = new SoliModifDatosBasicosItem();
		
		soliModifDatosBasicosItem.setIdioma("Español");
		soliModifDatosBasicosItem.setIdPersona(idPersona);
		
		return soliModifDatosBasicosItem;
	}
	
	public SoliModifFotoItem getSoliModifFotoItemSimulado(String idPersona) {
		SoliModifFotoItem soliModifFotoItem = new SoliModifFotoItem();
		
		soliModifFotoItem.setIdPersona(idPersona);
		soliModifFotoItem.setExpFoto("No");
	
		return soliModifFotoItem;
	}
}
