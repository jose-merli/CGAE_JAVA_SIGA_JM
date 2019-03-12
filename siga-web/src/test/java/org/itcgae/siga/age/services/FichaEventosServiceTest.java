package org.itcgae.siga.age.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.AgeTestUtils;
import org.idcgae.siga.commons.testUtils.ForTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.age.service.impl.FichaEventosServiceImpl;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.AgeRepeticionevento;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeDiassemanaExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEstadoeventosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeRepeticionEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipoeventosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.CenInfluenciaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaEventosServiceTest {

	@Mock
	private ForPersonacursoExtendsMapper forPersonacursoExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private AgeEventoExtendsMapper ageEventoExtendsMapper;

	@Mock
	private AgeTipoeventosExtendsMapper ageTipoeventosExtendsMapper;

	@Mock
	private AgeEstadoeventosExtendsMapper ageEstadoeventosExtendsMapper;

	@Mock
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;

	@Mock
	private AgeDiassemanaExtendsMapper ageDiassemanaExtendsMapper;

	@Mock
	private AgeRepeticionEventoExtendsMapper ageRepeticionEventoExtendsMapper;
	
	@Mock
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;
	
	@Mock
	private CenInfluenciaExtendsMapper cenInfluenciaExtendsMapper;

	@Mock
	AgeEvento ageEventoInsert;
	
	@InjectMocks
	private FichaEventosServiceImpl fichaEventosServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private AgeTestUtils ageTestUtils = new AgeTestUtils();
	
	private ForTestUtils forTestUtils = new ForTestUtils();
	
	@Test
	public void saveEventCalendarTest() throws Exception {

		String idLenguaje = "1";
		EventoItem eventoItem = ageTestUtils.getEventoItem2005();
		List<ComboItem> eventos = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> repeticionEventoInserted = testUtils.getListComboItemsSimulados();
		List<AgeEvento> eventosList = ageTestUtils.getListAgeEventoSimulados();
		List<AgeCalendario> calendariosList = ageTestUtils.getListaAgeCalendariosSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageRepeticionEventoExtendsMapper.insert(Mockito.any(AgeRepeticionevento.class))).thenReturn(1);

		when(ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent()).thenReturn(repeticionEventoInserted);

		when(ageEventoExtendsMapper.insert(Mockito.any(AgeEvento.class))).thenReturn(1);

		when(ageEventoExtendsMapper.selectMaxEvent()).thenReturn(eventos);
		
		when(ageCalendarioExtendsMapper.selectByExample(Mockito.any(AgeCalendarioExample.class))).thenReturn(calendariosList);

		when(ageEventoExtendsMapper.selectByExample(Mockito.any(AgeEventoExample.class))).thenReturn(eventosList);

		when(ageEventoExtendsMapper.updateByPrimaryKey(Mockito.any(AgeEvento.class))).thenReturn(1);

		when(ageEventoExtendsMapper.insert(Mockito.any(AgeEvento.class))).thenReturn(1);

		
//		when(ageEventoInsert.getIdevento().toString()).then("1");
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		InsertResponseDTO insertResponseDTOResultado = fichaEventosServiceImpl.saveEventCalendar(eventoItem, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setCode(200);
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}
	
	@Test
	public void saveEventCalendarTestKO() throws Exception {

		String idLenguaje = "1";
		EventoItem eventoItem = ageTestUtils.getEventoItem();
		List<ComboItem> eventos = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> repeticionEventoInserted = testUtils.getListComboItemsSimulados();
		List<AgeEvento> eventosList = ageTestUtils.getListAgeEventoSimulados();
		List<AgeCalendario> calendariosList = ageTestUtils.getListaAgeCalendariosSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageRepeticionEventoExtendsMapper.insert(Mockito.any(AgeRepeticionevento.class))).thenReturn(0);

		when(ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent()).thenReturn(repeticionEventoInserted);

		when(ageEventoExtendsMapper.insert(Mockito.any(AgeEvento.class))).thenReturn(0);

		when(ageEventoExtendsMapper.selectMaxEvent()).thenReturn(eventos);
		
		when(ageCalendarioExtendsMapper.selectByExample(Mockito.any(AgeCalendarioExample.class))).thenReturn(calendariosList);

		when(ageEventoExtendsMapper.selectByExample(Mockito.any(AgeEventoExample.class))).thenReturn(eventosList);

		when(ageEventoExtendsMapper.updateByPrimaryKey(Mockito.any(AgeEvento.class))).thenReturn(0);

		when(ageEventoExtendsMapper.insert(Mockito.any(AgeEvento.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = fichaEventosServiceImpl.saveEventCalendar(eventoItem, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}

	@Test
	public void getTrainersLabelsTest() throws Exception {

		Short idInstitucion = 2000;
		String idCurso = "1";
		List<FormadorCursoItem> formadoresCursoItem = forTestUtils.getListFormadoresCursoItem();

		when(forPersonacursoExtendsMapper.getTrainersLabels(idInstitucion.toString(), idCurso, "1")).thenReturn(formadoresCursoItem);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		FormadorCursoDTO formadoresCursoResultado = fichaEventosServiceImpl.getTrainersLabels(idCurso, mockreq);

		FormadorCursoDTO formadoresCursoEsperado = new FormadorCursoDTO();
		formadoresCursoEsperado.setFormadoresCursoItem(formadoresCursoItem);
		
		assertThat(formadoresCursoResultado).isEqualTo(formadoresCursoEsperado);

	}
	
	@Test
	public void getTypeEventTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> tipoEventos = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageTipoeventosExtendsMapper.getTypeEvent(idLenguaje)).thenReturn(tipoEventos);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaEventosServiceImpl.getTypeEvent(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getEventStatesTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> estadoEventos = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageEstadoeventosExtendsMapper.getEventStates(idLenguaje)).thenReturn(estadoEventos);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaEventosServiceImpl.getEventStates(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getRepeatEveryTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = ageTestUtils.getListRepeatEverySimulados();
		List<GenDiccionario> repetirCada = forTestUtils.getListGenDiccionario();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genDiccionarioExtendsMapper.selectByExample(Mockito.any(GenDiccionarioExample.class))).thenReturn(repetirCada);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaEventosServiceImpl.getRepeatEvery(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getDaysWeekTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageDiassemanaExtendsMapper.getDaysWeek(idLenguaje)).thenReturn(comboItemsSimulados);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaEventosServiceImpl.getDaysWeek(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getJudicialDistrict() throws Exception {

		String idInstitucion = "2000";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();

		when(cenInfluenciaExtendsMapper.getJudicialDistrict(idInstitucion)).thenReturn(comboItemsSimulados);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaEventosServiceImpl.getJudicialDistrict(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

	@Test
	public void updateEventCalendarTest() throws Exception {

		String idLenguaje = "1";
		EventoItem eventoItem = ageTestUtils.getEventoItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeEvento> eventosList = ageTestUtils.getListAgeEventoSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageEventoExtendsMapper.selectByExample(Mockito.any(AgeEventoExample.class))).thenReturn(eventosList);

		when(ageEventoExtendsMapper.updateByPrimaryKey(Mockito.any(AgeEvento.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaEventosServiceImpl.updateEventCalendar(eventoItem, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(200);
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void updateEventCalendarKOTest() throws Exception {

		String idLenguaje = "1";
		EventoItem eventoItem = ageTestUtils.getEventoItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeEvento> eventosList = ageTestUtils.getListAgeEventoSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageEventoExtendsMapper.selectByExample(Mockito.any(AgeEventoExample.class))).thenReturn(eventosList);

		when(ageEventoExtendsMapper.updateByPrimaryKey(Mockito.any(AgeEvento.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaEventosServiceImpl.updateEventCalendar(eventoItem, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Error al modificar evento");
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void deleteEventCalendarTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeEvento> eventosList = ageTestUtils.getListAgeEventoSimulados();
		EventoDTO eventoDTO = ageTestUtils.getEventoDTO();


		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageEventoExtendsMapper.selectByExample(Mockito.any(AgeEventoExample.class))).thenReturn(eventosList);

		when(ageEventoExtendsMapper.updateByPrimaryKey(Mockito.any(AgeEvento.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaEventosServiceImpl.deleteEventCalendar(eventoDTO, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(200);
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void deleteEventCalendarTestKO() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeEvento> eventosList = ageTestUtils.getListAgeEventoSimulados();
		EventoDTO eventoDTO = ageTestUtils.getEventoDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageEventoExtendsMapper.selectByExample(Mockito.any(AgeEventoExample.class))).thenReturn(eventosList);

		when(ageEventoExtendsMapper.updateByPrimaryKey(Mockito.any(AgeEvento.class))).thenThrow(Exception.class);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaEventosServiceImpl.deleteEventCalendar(eventoDTO, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	
}