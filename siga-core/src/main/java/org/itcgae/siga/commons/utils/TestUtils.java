package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioItem;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.age.PermisoCalendarioItem;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgePermisoscalendario;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {

	public MockHttpServletRequest getRequestWithGeneralAuthentication() throws TokenGenerationException {

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

	public MockHttpServletRequest getRequestWithGeneralAuthentication2005() throws TokenGenerationException {

		MockHttpServletRequest mockreq = new MockHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();

		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		userCgae.setInstitucion("2005");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);

		mockreq.addHeader("Authorization", token);

		return mockreq;
	}

	public MockHttpServletRequest getRequestWithVariableAuthentication(String idInstitucion) {
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
	
	public List<ComboItem> getListComboItemsLabelTestSimulados(){
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		comboItem_0.setLabel("");
		comboItem_0.setValue("");
		comboItems.add(comboItem_0);

		return comboItems;
	}
	
	public List<ComboItem> getListComboItemsValoresNulosSimulados() {

		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		ComboItem comboItem_1 = new ComboItem();
		
		comboItem_0.setLabel("");
		comboItem_0.setValue("");
		comboItems.add(0, comboItem_0);
		comboItem_1.setLabel("comboItem_0");
		comboItem_1.setValue("0");
		comboItems.add(comboItem_1);

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

	public List<AdmUsuarios> getListUsuariosSimulados(String idLenguaje) {
		List<AdmUsuarios> usuarios = new ArrayList<>();
		AdmUsuarios admUsuarios = new AdmUsuarios();
		admUsuarios.setIdlenguaje(idLenguaje);
		admUsuarios.setIdusuario(1);
		usuarios.add(admUsuarios);
		return usuarios;
	}


	public List<AgeCalendario> getListaAgeCalendariosSimulados() {
				List<AgeCalendario> listaAgeCalendarios = new ArrayList<AgeCalendario>();
		listaAgeCalendarios.add(getAgeCalendario());

		return listaAgeCalendarios;
	}

	
	public List<CalendarItem> getListaCalendariosSimulados(){
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

	public AgeCalendario getAgeCalendario() {
		AgeCalendario ageCalendario = new AgeCalendario();

		ageCalendario.setIdinstitucion((short) 2000);
		ageCalendario.setIdcalendario((long) 100);
		ageCalendario.setDescripcion("Descripcion");
		ageCalendario.setColor("Red");
		ageCalendario.setIdtipocalendario((long) 1);

		return ageCalendario;
	}


	public EventoItem getEventoItem() {
		EventoItem eventoItem = new EventoItem();

		eventoItem.setIdInstitucion((short) 2000);
		eventoItem.setUsuModificacion((long) 1);
		eventoItem.setIdCalendario("1");
		eventoItem.setIdEstadoEvento("1");
		eventoItem.setIdTipoEvento("1");
		eventoItem.setIdEvento("1");

		return eventoItem;
	}

	public EventoItem getEventoItem2005() {
		EventoItem eventoItem = new EventoItem();

		eventoItem.setIdInstitucion((short) 2005);
		eventoItem.setUsuModificacion((long) 1);
		eventoItem.setIdCalendario("1");
		eventoItem.setIdEstadoEvento("1");
		eventoItem.setIdTipoEvento("1");
		eventoItem.setIdEvento("1");
		eventoItem.setIdTipoCalendario("1");

		return eventoItem;
	}

	public EventoDTO getEventoDTO() {
		EventoDTO eventoDTO = new EventoDTO();
		List<EventoItem> listaEventos = new ArrayList<EventoItem>();
		listaEventos.add(getEventoItem());
		eventoDTO.setEventos(listaEventos);

		return eventoDTO;
	}

	public AgeEvento getAgeEvento() {
		AgeEvento ageEvento = new AgeEvento();

		ageEvento.setIdinstitucion((short) 2000);
		ageEvento.setIdcalendario((long) 100);
		ageEvento.setDescripcion("Descripcion");

		return ageEvento;
	}

	public List<AgeEvento> getListAgeEventoSimulados() {
		List<AgeEvento> listaAgeEventos = new ArrayList<AgeEvento>();
		listaAgeEventos.add(getAgeEvento());

		return listaAgeEventos;
	}

	public FormadorCursoItem getFormadoresCursoItem() {
		FormadorCursoItem formador = new FormadorCursoItem();

		formador.setIdInstitucion((short) 2000);
		formador.setNif("48921787M");
		formador.setIdPersona(Long.valueOf("43344334"));
		formador.setIdCurso(Long.valueOf("1"));
		formador.setIdRol("1");
		formador.setIdTipoCoste("1");
		formador.setTarifa(Double.valueOf("10"));
		return formador;
	}

	public List<FormadorCursoItem> getListFormadoresCursoItem() {
		List<FormadorCursoItem> listaFormadoresCursoItem = new ArrayList<FormadorCursoItem>();
		listaFormadoresCursoItem.add(getFormadoresCursoItem());

		return listaFormadoresCursoItem;
	}

	public GenDiccionario getGenDiccionario() {
		GenDiccionario genDiccionario = new GenDiccionario();

		genDiccionario.setIdlenguaje("1");
		genDiccionario.setDescripcion("descripcion");

		return genDiccionario;
	}

	public List<GenDiccionario> getListGenDiccionario() {
		List<GenDiccionario> genDiccionarioList = new ArrayList<GenDiccionario>();
		genDiccionarioList.add(getGenDiccionario());

		return genDiccionarioList;
	}

	public List<ComboItem> getListRepeatEverySimulados() {

		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		comboItem_0.setLabel("descripcion");
		comboItem_0.setValue("d");
		comboItems.add(comboItem_0);

		return comboItems;

	}

	public List<ForCurso> getListForCursoSimulados() {

		List<ForCurso> listCursos = new ArrayList<ForCurso>();

		ForCurso curso = new ForCurso();
		curso.setIdcurso(Long.valueOf("1"));
		curso.setIdinstitucion(Short.valueOf("2000"));
		curso.setNombrecurso("Prueba");

		listCursos.add(curso);

		return listCursos;

	}

	public FormadorCursoDTO getFormadorCursoDTOSimulado() {
		FormadorCursoDTO formadorCursoDTO = new FormadorCursoDTO();
		List<FormadorCursoItem> listaFormadores = new ArrayList<FormadorCursoItem>();
		listaFormadores.add(getFormadoresCursoItem());
		formadorCursoDTO.setFormadoresCursoItem(listaFormadores);

		return formadorCursoDTO;
	}

	public List<ForPersonaCurso> getListForPersonaCursoSimulados() {

		List<ForPersonaCurso> listFormadoresCursos = new ArrayList<ForPersonaCurso>();

		ForPersonaCurso formador = new ForPersonaCurso();
		formador.setIdcurso(Long.valueOf("1"));
		formador.setIdinstitucion(Short.valueOf("2000"));
		formador.setIdpersona(Long.valueOf("43344334"));
		formador.setIdrol(Short.valueOf("1"));
		formador.setIdtipocoste(Short.valueOf("1"));
		formador.setTarifa(Long.valueOf("10"));

		listFormadoresCursos.add(formador);

		return listFormadoresCursos;

	}

	public NotificacionEventoItem getNotificacionEventoItem() {
		NotificacionEventoItem notificacionItem = new NotificacionEventoItem();

		notificacionItem.setIdInstitucion((short) 2000);
		notificacionItem.setUsuModificacion((long) 1);
		notificacionItem.setIdCalendario("1");
		notificacionItem.setIdEvento("1");
		notificacionItem.setIdNotificacion("1");
		notificacionItem.setIdPlantilla("1");
		notificacionItem.setIdTipoCuando("1");
		notificacionItem.setIdTipoEnvio("1");
		notificacionItem.setIdTipoNotificacion("1");
		notificacionItem.setIdUnidadMedida("1");
		notificacionItem.setCuando("1");
		notificacionItem.setNombrePlantilla("Plantilla");

		return notificacionItem;
	}

	public ComboPlantillaEnvioDTO getComboPlantillaEnvioDTOSimulado() {
		ComboPlantillaEnvioDTO comboPlantillaEnvioDTO = new ComboPlantillaEnvioDTO();
		List<ComboPlantillaEnvioItem> listaComboPlantillaEnvioItem = new ArrayList<ComboPlantillaEnvioItem>();
		ComboPlantillaEnvioItem comboPlantillaEnvioItem = new ComboPlantillaEnvioItem();
		comboPlantillaEnvioItem.setLabel("Plantilla");
		comboPlantillaEnvioItem.setValue(getNotificacionEventoItem());

		listaComboPlantillaEnvioItem.add(comboPlantillaEnvioItem);
		comboPlantillaEnvioDTO.setComboAgeItems(listaComboPlantillaEnvioItem);

		return comboPlantillaEnvioDTO;
	}

	public List<ComboPlantillaEnvioItem> getListaComboPlantillaEnvioItem() {
		List<ComboPlantillaEnvioItem> listaComboPlantillaEnvioItem = new ArrayList<ComboPlantillaEnvioItem>();
		ComboPlantillaEnvioItem comboPlantillaEnvioItem = new ComboPlantillaEnvioItem();
		comboPlantillaEnvioItem.setLabel("Plantilla");
		comboPlantillaEnvioItem.setValue(getNotificacionEventoItem());

		listaComboPlantillaEnvioItem.add(comboPlantillaEnvioItem);

		return listaComboPlantillaEnvioItem;
	}

	public List<NotificacionEventoItem> getListNotificacionEventoItem() {
		List<NotificacionEventoItem> listaNotificacionEventoItem = new ArrayList<NotificacionEventoItem>();
		listaNotificacionEventoItem.add(getNotificacionEventoItem());

		return listaNotificacionEventoItem;
	}

	public AgeNotificacionesevento getAgeNotificacionesevento() {

		AgeNotificacionesevento ageNotificacionesevento = new AgeNotificacionesevento();
		ageNotificacionesevento.setIdcalendario((long) 1);
		ageNotificacionesevento.setIdevento((long) 1);
		ageNotificacionesevento.setIdnotificacionevento((long) 1);
		ageNotificacionesevento.setIdplantilla((long) 1);
		ageNotificacionesevento.setIdtipocuando((long) 1);
		ageNotificacionesevento.setIdtipoenvios((long) 1);
		ageNotificacionesevento.setIdtiponotificacionevento((long) 1);
		ageNotificacionesevento.setIdunidadmedida((long) 1);
		ageNotificacionesevento.setCuando((long) 1);

		return ageNotificacionesevento;
	}

	public List<AgeNotificacionesevento> getListAgeNotificacionesevento() {
		List<AgeNotificacionesevento> listaNotificacionEventoItem = new ArrayList<AgeNotificacionesevento>();
		listaNotificacionEventoItem.add(getAgeNotificacionesevento());

		return listaNotificacionEventoItem;
	}

	public NotificacionEventoDTO getNotificacionEventoDTOSimulado() {
		NotificacionEventoDTO notificacionEventoDTO = new NotificacionEventoDTO();
		List<NotificacionEventoItem> listaNotificacionEventoItem = new ArrayList<NotificacionEventoItem>();
		listaNotificacionEventoItem.add(getNotificacionEventoItem());
		notificacionEventoDTO.setEventNotificationItems(listaNotificacionEventoItem);

		return notificacionEventoDTO;
	}

	public PermisoCalendarioItem getPermisoCalendarioItem() {
		PermisoCalendarioItem permisoCalendarioItem = new PermisoCalendarioItem();

		permisoCalendarioItem.setIdCalendario("1");
		permisoCalendarioItem.setDerechoacceso("2");
		permisoCalendarioItem.setIdPerfil("1");

		return permisoCalendarioItem;
	}

	public PermisosCalendarioDTO getPermisosCalendarioDTOSimulado() {
		PermisosCalendarioDTO permisosCalendarioDTO = new PermisosCalendarioDTO();
		List<PermisoCalendarioItem> listaPermisoCalendarioItem = new ArrayList<PermisoCalendarioItem>();
		listaPermisoCalendarioItem.add(getPermisoCalendarioItem());
		permisosCalendarioDTO.setPermisoCalendarioItems(listaPermisoCalendarioItem);

		return permisosCalendarioDTO;
	}

	public AgePermisoscalendario getAgePermisoscalendario() {

		AgePermisoscalendario agePermisoscalendario = new AgePermisoscalendario();
		agePermisoscalendario.setIdcalendario((long) 1);
		agePermisoscalendario.setIdinstitucion((short) 2000);
		agePermisoscalendario.setIdpermisoscalendario((long) 1);
		agePermisoscalendario.setTipoacceso((long) 1);
		agePermisoscalendario.setUsumodificacion((long) 1);

		return agePermisoscalendario;
	}

	public List<AgePermisoscalendario> getListAgePermisoscalendario() {
		List<AgePermisoscalendario> listaAgePermisoscalendario = new ArrayList<AgePermisoscalendario>();
		listaAgePermisoscalendario.add(getAgePermisoscalendario());

		return listaAgePermisoscalendario;
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
