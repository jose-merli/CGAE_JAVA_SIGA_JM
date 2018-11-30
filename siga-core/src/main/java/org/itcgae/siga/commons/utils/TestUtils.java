package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioItem;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.age.PermisoCalendarioItem;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.utils.TokenGenerationException;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgePermisoscalendario;
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

}
