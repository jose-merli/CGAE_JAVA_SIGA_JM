package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.age.PermisoCalendarioItem;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgePermisoscalendario;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TestUtils{
	
	public MockHttpServletRequest  getRequestWithGeneralAuthentication() throws TokenGenerationException {

		MockHttpServletRequest mockreq = new MockHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();
		
		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		userCgae.setInstitucion("2000");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);
		
		mockreq.addHeader("Authorization", token); 
		
		return mockreq;
	}

	public MockHttpServletRequest  getRequestWithVariableAuthentication(String idInstitucion) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ComboItem> getListComboItemsSimulados() {
		
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		comboItem_0.setLabel("comboItem_0");
		comboItem_0.setValue("0");
		comboItems.add(comboItem_0);
		
		return comboItems;
		
	}
	
	public List<CursoItem> getListCursosSimulados(String estado, Integer flagArchivado) {
			
		List<CursoItem> cursoItems = new ArrayList<CursoItem>();
		
		cursoItems.add(getCursoSimulado(estado, flagArchivado));
		
		return cursoItems;
	}	
	
	public CursoItem getCursoSimulado(String estado, Integer flagArchivado) {
		
		CursoItem cursoItem = new CursoItem();
		cursoItem.setCodigoCurso("003GREW");
		cursoItem.setIdInstitucion("2000");
		cursoItem.setIdEstado(estado);
		cursoItem.setFlagArchivado(flagArchivado);
		
		return cursoItem;
	}
	
	public ComboItem getComboItemVacio() {
		
		ComboItem comboItem = new ComboItem();
		comboItem.setLabel("");
		comboItem.setValue("");
		
		return comboItem;
		
	}
	
	public List<AdmUsuarios> getListUsuariosSimulados(String idLenguaje){
		List<AdmUsuarios> usuarios = new ArrayList<>();
		AdmUsuarios admUsuarios = new AdmUsuarios();
		admUsuarios.setIdlenguaje(idLenguaje);
		admUsuarios.setIdusuario(1);
		usuarios.add(admUsuarios);
		return usuarios;
	}
	
	public List<CalendarItem> getListaAgeCalendariosSimulados(){
		List<CalendarItem> listaAgeCalendarios = new ArrayList<CalendarItem>();
		listaAgeCalendarios.add(getAgeCalendarioItem());
		
		return listaAgeCalendarios;
	}

	
	public CalendarItem getAgeCalendarioItem(){
		CalendarItem calendarItem = new CalendarItem();

		calendarItem.setIdInstitucion((short)2000);
		calendarItem.setIdCalendario("100");
		calendarItem.setDescripcion("Descripcion");
		calendarItem.setColor("Red");
		calendarItem.setIdTipoCalendario("1");
		  
		return calendarItem;		
	}
	
	public List<EventoItem> getListaEventosSimulados(String idCalendario, Short idInstitucion){
		List<EventoItem> listaEventos = new ArrayList<EventoItem>();
		listaEventos.add(getEvento(idCalendario, idInstitucion));
		
		return listaEventos;
	}
	
	public EventoItem getEvento(String idCalendario, Short idInstitucion){
		EventoItem eventoItem = new EventoItem();

		eventoItem.setIdInstitucion(idInstitucion);
		eventoItem.setIdCalendario(idCalendario);
		eventoItem.setDescripcion("Descripcion");
		eventoItem.setColor("Red");
		eventoItem.setIdTipoEvento("1");
		eventoItem.setIdEvento("1");
		  
		return eventoItem;		
	}
//	new ArrayList<CenDatoscolegialesestado>()


	public ArrayList<CenDatoscolegialesestado> getDatosColegialesList(){
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
	
	public List<CenPersona> getListaPersonasSimuladas(Long idpersona,String nifcif){
		List<CenPersona> listaPersonasSimuladas = new ArrayList<CenPersona>();
		listaPersonasSimuladas.add(getCenPersona(idpersona, nifcif));
		
		return listaPersonasSimuladas;
	}
	
	public CenPersona getCenPersona(Long idpersona,String nifcif) {
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
//		CREAR COMBOETIQUETASITEM Y METERLO EN EL ARRAY
		colegiado.setEtiquetas(new ComboEtiquetasItem[] {getComboEtiquetasItem(idGrupo)});
		return colegiado;
	}
	
	public List<ComboEtiquetasItem> getListaEtiquetasSimuladas(String idGrupo){
		List<ComboEtiquetasItem> listaEtiquetasSimuladas = new ArrayList<ComboEtiquetasItem>();
		listaEtiquetasSimuladas.add(getComboEtiquetasItem(idGrupo));
		
		return listaEtiquetasSimuladas;
	}
	
//	public List<ComboEtiquetasItem> getListaEtiquetasSimuladas(){
//		List<ComboEtiquetasItem> listaEtiquetasSimuladas = new ArrayList<ComboEtiquetasItem>();
//		listaEtiquetasSimuladas.add(getComboEtiquetasItem());
//		
//		return listaEtiquetasSimuladas;
//	}
	
	public List<ComboEtiquetasItem> getComboEtiquetasSimulados(String fInicio, String fBaja, String color){
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
	
	
	public List<CenGruposclienteCliente> getListaGruposSimulados(){
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
	
	public int insertSelectiveForUpdateLegalPerson(ComboEtiquetasItem etiqueta, String idPersona, String idInstitucion, String idUsuario) {
		return 1;
	}
	
	public CenCliente getCenCliente() {
		CenCliente cenCliente = new CenCliente();
		cenCliente.setFechamodificacion(new Date());
		cenCliente.setIdinstitucion((short) 2005);
		cenCliente.setIdpersona(Long.parseLong("213"));
		return cenCliente;
	}
	public List<AgePermisoscalendario> getAgePermisosSimulados(){
		List<AgePermisoscalendario> permisos = new ArrayList<AgePermisoscalendario>();
		
		permisos.add(getAgePermisoCalendarioSimulado());
		
		return permisos;
	}
	
	
	public AgePermisoscalendario getAgePermisoCalendarioSimulado(){
		AgePermisoscalendario permiso = new AgePermisoscalendario();
		
		permiso.setIdcalendario((long)1);
		permiso.setIdinstitucion((short)2000);
		permiso.setIdperfil("1");
		permiso.setIdpermisoscalendario((long)3);
		
		return permiso;
	}
	
	public PermisosCalendarioDTO getPermisosCalendarioSimulado() {
		PermisosCalendarioDTO permisosCalendarioDTO = new PermisosCalendarioDTO();
		List<PermisoCalendarioItem> permisos = new ArrayList<PermisoCalendarioItem>();
		
		permisos.add(getPermisoCalendarioItemSimulado());
		permisosCalendarioDTO.setPermisoCalendarioItems(permisos);
		
		return permisosCalendarioDTO;
	}
	
	public PermisoCalendarioItem getPermisoCalendarioItemSimulado() {
		PermisoCalendarioItem permiso = new PermisoCalendarioItem();
		
		permiso.setDerechoacceso("3");
		permiso.setIdCalendario("1");
		permiso.setIdPerfil("1");
		
		return permiso;
	}
	
	public List<AgeCalendario> getAgeCalendariosSimulados(){
		List<AgeCalendario> listaAgeCalendarios = new ArrayList<AgeCalendario>();
		listaAgeCalendarios.add(getAgeCalendario());
		
		return listaAgeCalendarios;
	}
	
	public AgeCalendario getAgeCalendario(){
		AgeCalendario calendar = new AgeCalendario();

		calendar.setIdinstitucion((short)2000);
		calendar.setIdcalendario((long)100);
		calendar.setDescripcion("Descripcion");
		calendar.setColor("Red");
		calendar.setIdtipocalendario((long)1);
		  
		return calendar;		
	}
	
	public List<PermisosPerfilesCalendarItem> getListPermisosPerfilesCalendarItemSimulado(){
		List<PermisosPerfilesCalendarItem> permisosPerfilesCalendarItem = new ArrayList<PermisosPerfilesCalendarItem>();
		permisosPerfilesCalendarItem.add(getPermisosPerfilesCalendarItemSimulado());
		
		return permisosPerfilesCalendarItem;
	}
	
	public PermisosPerfilesCalendarItem getPermisosPerfilesCalendarItemSimulado(){
		PermisosPerfilesCalendarItem calendar = new PermisosPerfilesCalendarItem();

		calendar.setDerechoacceso("1");
		calendar.setIdPerfil("1");
		calendar.setNombrePerfil("1");
		  
		return calendar;		
	}
	
}
