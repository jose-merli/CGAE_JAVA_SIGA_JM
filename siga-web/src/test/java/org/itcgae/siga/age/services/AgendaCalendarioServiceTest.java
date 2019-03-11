package org.itcgae.siga.age.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.AgeTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.age.service.impl.AgendaCalendarioServiceImpl;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class AgendaCalendarioServiceTest {

	@Mock
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;
	
	@Mock 
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@InjectMocks
	private AgendaCalendarioServiceImpl agendaCalendarioServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private AgeTestUtils ageTestUtils = new AgeTestUtils();

	@Test
	public void getCalendariosByIdInstitucionTest() throws Exception {


		//List<AgeCalendario> listAgeCalendarioSimulado = ageTestUtils.getListaAgeCalendariosSimulados();

		List<CalendarItem> listAgeCalendarioSimulado = ageTestUtils.getListaCalendariosSimulados();

		
		when(ageCalendarioExtendsMapper.getCalendariosPermisos(Mockito.anyShort(), Mockito.anyString())).thenReturn(listAgeCalendarioSimulado);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CalendarDTO calendarResultado = agendaCalendarioServiceImpl.getCalendariosByIdInstitucion(mockreq);
		
		CalendarDTO calendarEsperado = new CalendarDTO();
		calendarEsperado.setCalendarItems(listAgeCalendarioSimulado);
		
		assertThat(calendarResultado).isEqualTo(calendarEsperado);

	}
	
	@Test
	public void getCalendarsTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(ageCalendarioExtendsMapper.getCalendars(Mockito.anyString())).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = agendaCalendarioServiceImpl.getCalendars(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getEventosByIdCalendario() throws Exception {

		String idCalendario = "1";
		Short idInstitucion = 2000;
		List<EventoItem> listEventosSimulado = ageTestUtils.getListaEventosSimulados(idCalendario, idInstitucion);
		
		when(ageCalendarioExtendsMapper.getCalendarioEventos(Mockito.anyShort(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listEventosSimulado);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		EventoDTO calendarResultado = agendaCalendarioServiceImpl.getEventosByIdCalendario(mockreq, idCalendario);
		
		EventoDTO calendarEsperado = new EventoDTO();
		calendarEsperado.setEventos(listEventosSimulado);
		
		assertThat(calendarResultado).isEqualTo(calendarEsperado);

	}
	
}