package org.idcgae.siga.commons.testUtils;

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
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgePermisoscalendario;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.springframework.stereotype.Service;

@Service
public class AgeTestUtils {

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
