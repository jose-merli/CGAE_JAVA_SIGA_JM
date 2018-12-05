package org.idcgae.siga.commons.testUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.DTOs.cen.SoliModifDatosBasicosItem;
import org.itcgae.siga.DTOs.cen.SoliModifFotoItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
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
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.springframework.stereotype.Service;

@Service
public class CenTestUtils {

	public List<ColegiadoItem> getListColegiadoItemSimulados() {

		List<ColegiadoItem> colegiadoItems = new ArrayList<ColegiadoItem>();

		colegiadoItems.add(getColegiadoItem());

		return colegiadoItems;
	}

	public ColegiadoItem getColegiadoItem() {
		ColegiadoItem colegiadoItem = new ColegiadoItem();

		colegiadoItem.setIdInstitucion("2000");
		colegiadoItem.setIdPersona("23453212");

		return colegiadoItem;
	}

	public ColegiadoDTO getColegiadoDTO() {
		ColegiadoDTO colegiadoDTO = new ColegiadoDTO();
		List<ColegiadoItem> listaColegiados = new ArrayList<ColegiadoItem>();
		listaColegiados.add(getColegiadoItem());
		colegiadoDTO.setColegiadoItem(listaColegiados);

		return colegiadoDTO;
	}

	public List<NoColegiadoItem> getListNoColegiadoItemSimulados() {

		List<NoColegiadoItem> noColegiadoItem = new ArrayList<NoColegiadoItem>();

		noColegiadoItem.add(getNoColegiadoItem());

		return noColegiadoItem;
	}

	public NoColegiadoItem getNoColegiadoItem() {
		NoColegiadoItem noColegiadoItem = new NoColegiadoItem();

		noColegiadoItem.setIdInstitucion("2000");
		noColegiadoItem.setIdPersona("23453212");

		return noColegiadoItem;
	}

	public ArrayList<CenDatoscolegialesestado> getDatosColegialesList() {
		ArrayList<CenDatoscolegialesestado> listaDatosColegiales = new ArrayList<CenDatoscolegialesestado>();
		listaDatosColegiales.add(getCenDatoscolegialesestado());

		return listaDatosColegiales;
	}

	public CenDatoscolegialesestado getCenDatoscolegialesestado() {
		CenDatoscolegialesestado persona = new CenDatoscolegialesestado();
		persona.setIdpersona(Long.parseLong("1234"));
		persona.setIdestado(Short.parseShort("1234"));
		persona.setIdinstitucion(Short.parseShort("1234"));
		persona.setFechaestado(new Date());
		persona.setFechamodificacion(new Date());
		persona.setUsumodificacion(1);
		return persona;
	}

	public List<CenPersona> getListaPersonasSimuladas(Long idpersona, String nifcif) {
		List<CenPersona> listaPersonasSimuladas = new ArrayList<CenPersona>();
		listaPersonasSimuladas.add(getCenPersona(idpersona, nifcif));

		return listaPersonasSimuladas;
	}

	public CenPersona getCenPersona(Long idpersona, String nifcif) {
		CenPersona persona = new CenPersona();
		persona.setIdpersona(idpersona);
		persona.setNombre("Nombre");
		persona.setApellidos1("apellidos1");
		persona.setApellidos2("apellidos2");
		persona.setNifcif(nifcif);
		persona.setFechamodificacion(new Date());
		persona.setUsumodificacion(1);
		persona.setIdtipoidentificacion(Short.parseShort("10"));
		persona.setFechanacimiento(new Date());
		persona.setIdestadocivil(Short.parseShort("10"));
		persona.setNaturalde("naturalde");
		persona.setFallecido("Fallecido");
		persona.setSexo("M");
		return persona;
	}

	public ColegiadoItem getColegiadoItem(Boolean isColegiado, String idGrupo) {
		ColegiadoItem colegiado = new ColegiadoItem();
		colegiado.setIdPersona(String.valueOf(1223));
		colegiado.setIdInstitucion("2005");
		colegiado.setNombre("Nombre");
		colegiado.setApellidos1("apellidos1");
		colegiado.setIdTipoIdentificacion("10");
		colegiado.setApellidos2("apellidos2");
		colegiado.setColegiado(isColegiado);
		colegiado.setNaturalDe("");
		colegiado.setIncorporacion(new Date());
		colegiado.setSituacion("1");
		colegiado.setNif("20092000V");
		colegiado.setIdtratamiento("1");
		colegiado.setComisiones("0");
		colegiado.setAsientoContable("");
		colegiado.setSexo("M");
		// CREAR COMBOETIQUETASITEM Y METERLO EN EL ARRAY
		colegiado.setEtiquetas(new ComboEtiquetasItem[] { getComboEtiquetasItem(idGrupo) });
		return colegiado;
	}

	public List<ComboEtiquetasItem> getListaEtiquetasSimuladas(String idGrupo) {
		List<ComboEtiquetasItem> listaEtiquetasSimuladas = new ArrayList<ComboEtiquetasItem>();
		listaEtiquetasSimuladas.add(getComboEtiquetasItem(idGrupo));

		return listaEtiquetasSimuladas;
	}

	public List<ComboEtiquetasItem> getComboEtiquetasSimulados(String fInicio, String fBaja, String color) {
		List<ComboEtiquetasItem> listaGruposSimulados = new ArrayList<ComboEtiquetasItem>();
		listaGruposSimulados.add(getComboEtiquetasItem(fInicio, fBaja, color));

		return listaGruposSimulados;
	}

	public ComboEtiquetasItem getComboEtiquetasItem(String fInicio, String fBaja, String color) {
		ComboEtiquetasItem etiquetas = new ComboEtiquetasItem();
		etiquetas.setFechaBaja(fInicio);
		etiquetas.setFechaInicio(fBaja);
		etiquetas.setColor(color);
		return etiquetas;
	}

	public List<CenGruposclienteCliente> getListaGruposSimulados() {
		List<CenGruposclienteCliente> listaGruposSimulados = new ArrayList<CenGruposclienteCliente>();
		listaGruposSimulados.add(getGruposCliCliItem());

		return listaGruposSimulados;
	}

	public CenGruposclienteCliente getGruposCliCliItem() {
		CenGruposclienteCliente gruposCliCli = new CenGruposclienteCliente();
		gruposCliCli.setFechaInicio(new Date());
		gruposCliCli.setFechaBaja(new Date());
		gruposCliCli.setFechamodificacion(new Date());
		gruposCliCli.setIdgrupo(Short.parseShort("1"));
		gruposCliCli.setIdinstitucion((short) 2005);
		gruposCliCli.setIdpersona(Long.parseLong("213"));
		return gruposCliCli;
	}

	public ComboEtiquetasItem getComboEtiquetasItem(String idGrupo) {
		ComboEtiquetasItem comboEtiquetas = new ComboEtiquetasItem();
		comboEtiquetas.setIdGrupo(idGrupo);
		comboEtiquetas.setFechaBaja("23/05/2014");
		comboEtiquetas.setFechaInicio("23/05/2014");
		comboEtiquetas.setLabel("Label");
		return comboEtiquetas;
	}

	public int insertSelectiveForUpdateLegalPerson(ComboEtiquetasItem etiqueta, String idPersona, String idInstitucion,
			String idUsuario) {
		return 1;
	}

	public CenCliente getCenCliente() {
		CenCliente cenCliente = new CenCliente();
		cenCliente.setFechamodificacion(new Date());
		cenCliente.setIdinstitucion((short) 2000);
		cenCliente.setIdpersona(Long.parseLong("213"));
		return cenCliente;
	}

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
		// cenSolimodidirecciones.setIdpais("191");
		// cenSolimodidirecciones.setIdpoblacion("04064000605");
		// cenSolimodidirecciones.setIdprovincia("04");
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
		// cenDatoscv.setFechabaja(new Date());
		// cenDatoscv.setFechafin(new Date());
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
	
	public List<CenTiposcv> getListCenTiposcvSimulado() {
		List<CenTiposcv> listCenTiposcv = new ArrayList<CenTiposcv>();

		listCenTiposcv.add(getCenTiposcvSimulado());

		return listCenTiposcv;
	}

	public GenRecursosCatalogos getGenRecursosCatalogosSimulado(String descripcion) {
		GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();

		genRecursosCatalogos.setIdlenguaje("1");
		genRecursosCatalogos.setIdrecurso("1234");
		genRecursosCatalogos.setDescripcion(descripcion);

		return genRecursosCatalogos;
	}
	
	public List<GenRecursosCatalogos> getListGenRecursosCatalogosSimulado() {
		List<GenRecursosCatalogos> listGenRecursosCatalogos = new ArrayList<GenRecursosCatalogos>();

		listGenRecursosCatalogos.add(getGenRecursosCatalogosSimulado("STRING"));

		return listGenRecursosCatalogos;
	}

	public GenRecursos getGenRecursosSimulado(String descripcion) {
		GenRecursos genRecursos = new GenRecursos();

		genRecursos.setIdlenguaje("1");
		genRecursos.setIdrecurso("1234");
		genRecursos.setDescripcion(descripcion);

		return genRecursos;
	}
	
	public List<GenRecursos> getListGenRecursosSimulado() {
		List<GenRecursos> listGenRecursos = new ArrayList<GenRecursos>();

		listGenRecursos.add(getGenRecursosSimulado("DESCRIPCIÓN"));

		return listGenRecursos;
	}

	
	public CenTiposcvsubtipo1 getCenTiposcvsubtipo1Simulado() {
		CenTiposcvsubtipo1 cenTiposcvsubtipo1 = new CenTiposcvsubtipo1();

		cenTiposcvsubtipo1.setIdtipocv((short) 1);
		cenTiposcvsubtipo1.setIdtipocvsubtipo1((short) 1);
		cenTiposcvsubtipo1.setDescripcion("1234");
		cenTiposcvsubtipo1.setIdinstitucion((short) 2000);

		return cenTiposcvsubtipo1;
	}
	
	public List<CenTiposcvsubtipo1> getListCenTiposcvsubtipo1Simulado() {
		List<CenTiposcvsubtipo1> listCenTiposcvsubtipo1 = new ArrayList<CenTiposcvsubtipo1>();

		listCenTiposcvsubtipo1.add(getCenTiposcvsubtipo1Simulado());

		return listCenTiposcvsubtipo1;
	}

	public CenTiposcvsubtipo2 getCenTiposcvsubtipo2Simulado() {
		CenTiposcvsubtipo2 cenTiposcvsubtipo2 = new CenTiposcvsubtipo2();

		cenTiposcvsubtipo2.setIdtipocv((short) 1);
		cenTiposcvsubtipo2.setIdtipocvsubtipo2((short) 1);
		cenTiposcvsubtipo2.setDescripcion("1234");

		return cenTiposcvsubtipo2;
	}
	
	public List<CenTiposcvsubtipo2> getListCenTiposcvsubtipo2Simulado() {
		List<CenTiposcvsubtipo2> listCenTiposcvsubtipo2 = new ArrayList<CenTiposcvsubtipo2>();

		listCenTiposcvsubtipo2.add(getCenTiposcvsubtipo2Simulado());

		return listCenTiposcvsubtipo2;
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
		cenSolicitudmodificacioncv.setIdinstitucion((short) 2000);
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

	public TipoCurricularItem getTipoCurricularItemSimulado() {
		TipoCurricularItem tipoCurricularItem = new TipoCurricularItem();

		tipoCurricularItem.setDescripcion("1234");
		tipoCurricularItem.setIdTipoCV("1");
		tipoCurricularItem.setIdTipoCvSubtipo1("1");
		tipoCurricularItem.setTipoCategoriaCurricular("1");
	
		return tipoCurricularItem;
	}

	public List<TipoCurricularItem> getListTipoCurricularItemSimulado() {
		List<TipoCurricularItem> listTipoCurricularItem = new ArrayList<TipoCurricularItem>();

		listTipoCurricularItem.add(getTipoCurricularItemSimulado());

		return listTipoCurricularItem;
	}
	
	public TipoCurricularDTO getTipoCurricularDTO() {
		TipoCurricularDTO tipoCurricularDTO = new TipoCurricularDTO();
		List<TipoCurricularItem> listaTipoCurricularItem = new ArrayList<TipoCurricularItem>();
		listaTipoCurricularItem.add(getTipoCurricularItemSimulado());
		tipoCurricularDTO.setTipoCurricularItems(listaTipoCurricularItem);

		return tipoCurricularDTO;
	}
	
	public SubtipoCurricularItem getSubtipoCurricularItemSimulado() {
		SubtipoCurricularItem subtipoCurricularItem = new SubtipoCurricularItem();

		subtipoCurricularItem.setIdTipoCV("1");
		subtipoCurricularItem.setIdTipoCvSubtipo2("1");
		subtipoCurricularItem.setTipoCategoriaCurricular("1");
		subtipoCurricularItem.setDescripcion("1234");

		return subtipoCurricularItem;
	}
	
	public List<SubtipoCurricularItem> getListSubtipoCurricularItemSimulado() {
		List<SubtipoCurricularItem> listSubtipoCurricularItem = new ArrayList<SubtipoCurricularItem>();

		listSubtipoCurricularItem.add(getSubtipoCurricularItemSimulado());

		return listSubtipoCurricularItem;
	}
	
	public SubtipoCurricularDTO getSubtipoCurricularDTO() {
		SubtipoCurricularDTO subtipoCurricularDTO = new SubtipoCurricularDTO();
		List<SubtipoCurricularItem> listaSubtipoCurricularItem = new ArrayList<SubtipoCurricularItem>();
		listaSubtipoCurricularItem.add(getSubtipoCurricularItemSimulado());
		subtipoCurricularDTO.setSubtipoCurricularItems(listaSubtipoCurricularItem);

		return subtipoCurricularDTO;
	}
	
	public List<String> getListaSimulada(){
		List<String> listString = new ArrayList<String>();
		
		listString.add("S1");
		listString.add("S2");
		listString.add("S3");
		listString.add("S4");
		
		return listString;
	}
	
	public Vector<Hashtable<String, Object>> getDatosVectorSimulado(){
		Vector<Hashtable<String, Object>> datosVector =  new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
		
		datosHashtable.put("S1", "1");
		datosHashtable.put("S2", "2");
		datosHashtable.put("S3", "3");
		datosHashtable.put("S4", "4");
		
		datosVector.add(datosHashtable);
		return datosVector;
	}
	
	public CargaMasivaItem getCargaMasivaItemSimulado() {
		CargaMasivaItem cargaMasivaItem = new CargaMasivaItem();

		cargaMasivaItem.setIdFichero((long) 257721);
		cargaMasivaItem.setIdFicheroLog((long) 257722);
		cargaMasivaItem.setTipoCarga("1");
		cargaMasivaItem.setNombreFichero("NOMBRE");
		
		return cargaMasivaItem;
	}
	
	public CargaMasivaItem getCargaMasivaItemSimuladoGF() {
		CargaMasivaItem cargaMasivaItem = new CargaMasivaItem();

		cargaMasivaItem.setIdFichero((long) 257723);
		cargaMasivaItem.setIdFicheroLog((long) 257724);
		cargaMasivaItem.setTipoCarga("1");
		cargaMasivaItem.setNombreFichero("NOMBRE");
		
		return cargaMasivaItem;
	}
	
	public List<CargaMasivaItem> getListCargaMasivaItemSimulado(){
		List<CargaMasivaItem> listCargaMasivaItem = new ArrayList<CargaMasivaItem>();

		listCargaMasivaItem.add(getCargaMasivaItemSimulado());

		return listCargaMasivaItem;
	}
	
	public CargaMasivaDTO getCargaMasivaDTO() {
		CargaMasivaDTO cargaMasivaDTO = new CargaMasivaDTO();
		List<CargaMasivaItem> listaCargaMasivaItem = new ArrayList<CargaMasivaItem>();
		listaCargaMasivaItem.add(getCargaMasivaItemSimulado()); 
		cargaMasivaDTO.setCargaMasivaItem(listaCargaMasivaItem);

		return cargaMasivaDTO;
	}
	
	public List<CenDatoscv> getListCenDatoscv() {
		List<CenDatoscv> listaCenDatoscv = new ArrayList<CenDatoscv>();
		CenDatoscv cenDatoscv = new CenDatoscv();
		cenDatoscv.setFechamodificacion(new Date());
		cenDatoscv.setIdinstitucion((short) 2005);
		cenDatoscv.setIdpersona(Long.parseLong("213"));
		listaCenDatoscv.add(cenDatoscv);
		return listaCenDatoscv;
	}
	
	public List<GenProperties> getListGenPropertiesSimulados(){
		List<GenProperties> properties = new ArrayList<>();
		GenProperties property = new GenProperties();
		property.setValor("1");
		properties.add(property);
		return properties;
	}
	
//	
//	public CenGruposclienteCliente getGruposCliCliItem() {
//		CenGruposclienteCliente gruposCliCli = new CenGruposclienteCliente();
//		gruposCliCli.setFechaInicio(new Date());
//		gruposCliCli.setFechaBaja(new Date());
//		gruposCliCli.setFechamodificacion(new Date());
//		gruposCliCli.setIdgrupo(Short.parseShort("1"));
//		gruposCliCli.setIdinstitucion((short) 2005);
//		gruposCliCli.setIdpersona(Long.parseLong("213"));
//		return gruposCliCli;
//	}
	
	
	public List<ColegiadoItem> getListaColegiadosSimulada(Boolean isColegiado, String idGrupo){
		List<ColegiadoItem> listaColegiadosSimulada = new ArrayList<ColegiadoItem>();
		listaColegiadosSimulada.add(getColegiadoItem(isColegiado, idGrupo));
		
		return listaColegiadosSimulada;
	}
	
	
	public List<CenGruposcliente> getListaGrupCliSimulados(){
		List<CenGruposcliente> listaGruposSimulados = new ArrayList<CenGruposcliente>();
		listaGruposSimulados.add(getGrupCliItem());
		
		return listaGruposSimulados;
	}
	
	public CenGruposcliente getGrupCliItem() {
		CenGruposcliente gruposCliCli = new CenGruposcliente();
		gruposCliCli.setFechamodificacion(new Date());
		gruposCliCli.setIdgrupo(Short.parseShort("1"));
		gruposCliCli.setIdinstitucion((short) 2005);
		gruposCliCli.setNombre("A");
		gruposCliCli.setUsumodificacion(1234);
		return gruposCliCli;
	}
	
	public NoColegiadoItem getNoColegiadoItem(String idGrupo) {
		NoColegiadoItem noColegiado = new NoColegiadoItem();
		noColegiado.setIdPersona(String.valueOf(1223));
		noColegiado.setIdInstitucion("2005");
		noColegiado.setNombre("Nombre");
		noColegiado.setApellidos1("apellidos1");
		noColegiado.setGuiaJudicial("1");
		noColegiado.setIdLenguaje("1");
		noColegiado.setMotivo("motivo");
		noColegiado.setIdTipoIdentificacion("10");
		noColegiado.setApellidos2("apellidos2");
//		noColegiado.setColegiado(isColegiado);
		noColegiado.setNaturalDe("");
//		noColegiado.setIncorporacion(new Date());
		noColegiado.setSituacion("1");
		noColegiado.setNif("20092000V");
//		noColegiado.setIdtratamiento("1");
		noColegiado.setComisiones("0");
		noColegiado.setAsientoContable("");
		noColegiado.setSexo("M");
//		CREAR COMBOETIQUETASITEM Y METERLO EN EL ARRAY
		noColegiado.setEtiquetas(new ComboEtiquetasItem[] {getComboEtiquetasItem(idGrupo)});
		return noColegiado;
	}
	
	public BusquedaSancionesSearchDTO getBusquedaSancionesSearchDTOSimulado() {
		BusquedaSancionesSearchDTO busquedaSancionesSearchDTO = new BusquedaSancionesSearchDTO();

		busquedaSancionesSearchDTO.setTipoSancion("3");

		return busquedaSancionesSearchDTO;
	}

	public BusquedaSancionesItem getBusquedaSancionesItemSimulado() {
		BusquedaSancionesItem busquedaSancionesItem = new BusquedaSancionesItem();

		busquedaSancionesItem.setIdPersona("2005001213");
		busquedaSancionesItem.setIdSancion("1");
		busquedaSancionesItem.setTipoSancion("3");
	
		return busquedaSancionesItem;
	}

	public List<BusquedaSancionesItem> getListBusquedaSancionesItemSimulado() {
		List<BusquedaSancionesItem> listBusquedaSancionesItem = new ArrayList<BusquedaSancionesItem>();

		listBusquedaSancionesItem.add(getBusquedaSancionesItemSimulado());

		return listBusquedaSancionesItem;
	}
}
