package org.itcgae.siga.age.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.AgeTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.age.service.impl.FichaCalendarioServiceImpl;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;
import org.itcgae.siga.db.entities.AgePermisoscalendario;
import org.itcgae.siga.db.entities.AgePermisoscalendarioExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgePermisosCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipocalendarioExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaCalendarioServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private AgeTipocalendarioExtendsMapper ageTipocalendarioExtendsMapper;

	@Mock
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;

	@Mock
	private AgePermisosCalendarioExtendsMapper agePermisosCalendarioExtendsMapper;

	@InjectMocks
	private FichaCalendarioServiceImpl fichaCalendarioServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private AgeTestUtils ageTestUtils = new AgeTestUtils();

	@Test
	public void getCalendarTypeTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItems = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageTipocalendarioExtendsMapper.getCalendarType(Mockito.anyString())).thenReturn(comboItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaCalendarioServiceImpl.getCalendarType(mockreq);

		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.combooItems(comboItems);

		assertThat(comboResultado).isEqualTo(comboEsperado);
	}
	
	@Test
	public void updatePermissionsTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItems = testUtils.getListComboItemsSimulados();
		List<AgePermisoscalendario> permisos = ageTestUtils.getAgePermisosSimulados();
		UpdateResponseDTO updateResponseEsperado = new UpdateResponseDTO();
		PermisosCalendarioDTO permisosCalendarioDTO = ageTestUtils.getPermisosCalendarioSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageTipocalendarioExtendsMapper.getCalendarType(Mockito.anyString())).thenReturn(comboItems);
		when(agePermisosCalendarioExtendsMapper.selectByExample(Mockito.any(AgePermisoscalendarioExample.class))).thenReturn(permisos);
		when(agePermisosCalendarioExtendsMapper.updateByPrimaryKey(Mockito.any(AgePermisoscalendario.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseResultado = fichaCalendarioServiceImpl.updatePermissions(permisosCalendarioDTO, mockreq);
		
		assertThat(updateResponseResultado).isEqualTo(updateResponseEsperado);
	}
	
	@Test
	public void updatePermissionsSinPermisoTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItems = testUtils.getListComboItemsSimulados();
		List<AgePermisoscalendario> permisos = null;
		UpdateResponseDTO updateResponseEsperado = new UpdateResponseDTO();
		PermisosCalendarioDTO permisosCalendarioDTO = ageTestUtils.getPermisosCalendarioSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageTipocalendarioExtendsMapper.getCalendarType(Mockito.anyString())).thenReturn(comboItems);
		when(agePermisosCalendarioExtendsMapper.selectByExample(Mockito.any(AgePermisoscalendarioExample.class))).thenReturn(permisos);
		when(agePermisosCalendarioExtendsMapper.insert(Mockito.any(AgePermisoscalendario.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseResultado = fichaCalendarioServiceImpl.updatePermissions(permisosCalendarioDTO,mockreq);

		assertThat(updateResponseResultado).isEqualTo(updateResponseEsperado);
	}
	
	@Test
	public void saveCalendarTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CalendarItem calendarItem = ageTestUtils.getAgeCalendarioItem();
		List<ComboItem> calendarInserted = testUtils.getListComboItemsSimulados();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageCalendarioExtendsMapper.insert(Mockito.any(AgeCalendario.class))).thenReturn(1);
		when(ageCalendarioExtendsMapper.selectMaxCalendar()).thenReturn(calendarInserted);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseResultado = fichaCalendarioServiceImpl.saveCalendar(calendarItem,mockreq);
		
		InsertResponseDTO insertResponseEsperado = new InsertResponseDTO();
		insertResponseEsperado.setId(calendarInserted.get(0).getValue());
		Error error = new Error();
		error.setCode(200);
		insertResponseEsperado.setError(error);

		assertThat(insertResponseResultado).isEqualTo(insertResponseEsperado);
	}
	
	@Test
	public void saveCalendarErrorTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CalendarItem calendarItem = ageTestUtils.getAgeCalendarioItem();
		List<ComboItem> calendarInserted = testUtils.getListComboItemsSimulados();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageCalendarioExtendsMapper.insert(Mockito.any(AgeCalendario.class))).thenReturn(0);
		when(ageCalendarioExtendsMapper.selectMaxCalendar()).thenReturn(calendarInserted);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseResultado = fichaCalendarioServiceImpl.saveCalendar(calendarItem,mockreq);
		
		InsertResponseDTO insertResponseEsperado = new InsertResponseDTO();
		insertResponseEsperado.setId(calendarInserted.get(0).getValue());
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Error al insertar nuevo calendario");
		insertResponseEsperado.setError(error);

		assertThat(insertResponseResultado).isEqualTo(insertResponseEsperado);
	}
	
	@Test
	public void updateCalendarTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeCalendario> calendarios = ageTestUtils.getListaAgeCalendariosSimulados();
		CalendarItem calendarItem = ageTestUtils.getAgeCalendarioItem();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageCalendarioExtendsMapper.selectByExample(Mockito.any(AgeCalendarioExample.class))).thenReturn(calendarios);
		when(ageCalendarioExtendsMapper.updateByPrimaryKey(Mockito.any(AgeCalendario.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseResultado = fichaCalendarioServiceImpl.updateCalendar(calendarItem, mockreq);
		
		UpdateResponseDTO updateResponseEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(200);
		updateResponseEsperado.setError(error);
		
		assertThat(updateResponseResultado).isEqualTo(updateResponseEsperado);
	}
	
	@Test
	public void updateCalendarErrorTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeCalendario> calendarios = ageTestUtils.getListaAgeCalendariosSimulados();
		CalendarItem calendarItem = ageTestUtils.getAgeCalendarioItem();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageCalendarioExtendsMapper.selectByExample(Mockito.any(AgeCalendarioExample.class))).thenReturn(calendarios);
		when(ageCalendarioExtendsMapper.updateByPrimaryKey(Mockito.any(AgeCalendario.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseResultado = fichaCalendarioServiceImpl.updateCalendar(calendarItem, mockreq);
		
		UpdateResponseDTO updateResponseEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Error al modificar nuevo calendario");
		updateResponseEsperado.setError(error);
		
		assertThat(updateResponseResultado).isEqualTo(updateResponseEsperado);
	}
	
	@Test
	public void getCalendarTest() throws Exception {
		AgeCalendario ageCalendario = ageTestUtils.getAgeCalendario();
		CalendarItem calendarItem = ageTestUtils.getAgeCalendarioItem();
		calendarItem.setIdInstitucion(null);
		calendarItem.setIdCalendario(null);
		
		when(ageCalendarioExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(ageCalendario);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CalendarDTO calendarResultado = fichaCalendarioServiceImpl.getCalendar(String.valueOf(ageCalendario.getIdcalendario()), mockreq);
		
		CalendarDTO calendarEsperado = new CalendarDTO();
		calendarEsperado.setCalendar(calendarItem);
		
		assertThat(calendarResultado).isEqualTo(calendarEsperado);
	}
	
	@Test
	public void PermisosPerfilesCalendarDTOTest() throws Exception {
		List<PermisosPerfilesCalendarItem> profilesCalendar = ageTestUtils.getListPermisosPerfilesCalendarItemSimulado();
		
		when(agePermisosCalendarioExtendsMapper.getProfilesPermissions(Mockito.anyString(), Mockito.anyString())).thenReturn(profilesCalendar);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		PermisosPerfilesCalendarDTO permisosPerfilesCalendarResultado = fichaCalendarioServiceImpl.getProfilesPermissions("1",mockreq);
		PermisosPerfilesCalendarDTO permisosPerfilesCalendarEsperado = new PermisosPerfilesCalendarDTO();
		permisosPerfilesCalendarEsperado.setPermisosPerfilesCalendar(profilesCalendar);
		
		assertThat(permisosPerfilesCalendarResultado).isEqualTo(permisosPerfilesCalendarEsperado);
	}
}