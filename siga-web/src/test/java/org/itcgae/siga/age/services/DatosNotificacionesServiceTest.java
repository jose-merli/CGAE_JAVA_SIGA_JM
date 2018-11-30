package org.itcgae.siga.age.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.AgeTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.age.service.impl.DatosNotificacionesServiceImpl;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample;
import org.itcgae.siga.db.mappers.AgeNotificacioneseventoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeNotificacioneseventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipocuandoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTiponotificacioneventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeUnidadmedidaExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.EnvPlantillasenviosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.EnvTipoenviosExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DatosNotificacionesServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private AgeTiponotificacioneventoExtendsMapper ageTiponotificacioneventoExtendsMapper;

	@Mock
	private AgeUnidadmedidaExtendsMapper ageUnidadmedidaExtendsMapper;

	@Mock
	private AgeNotificacioneseventoMapper ageNotificacioneseventoMapper;

	@Mock
	private AgeTipocuandoExtendsMapper ageTipocuandoExtendsMapper;

	@Mock
	private EnvPlantillasenviosExtendsMapper envPlantillasenviosExtendsMapper;

	@Mock
	private EnvTipoenviosExtendsMapper envTipoenviosExtendsMapper;

	@Mock
	private AgeNotificacioneseventoExtendsMapper ageNotificacioneseventoExtendsMapper;

	@InjectMocks
	private DatosNotificacionesServiceImpl datosNotificacionesServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private AgeTestUtils ageTestUtils = new AgeTestUtils();
	
	@Test
	public void getTypeNotificationsTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> tipoNotificaciones = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageTiponotificacioneventoExtendsMapper.getTypeNotifications(idLenguaje)).thenReturn(tipoNotificaciones);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = datosNotificacionesServiceImpl.getTypeNotifications(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getMeasuredUnitTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> unidadMedidas = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageUnidadmedidaExtendsMapper.getMeasuredUnit(idLenguaje)).thenReturn(unidadMedidas);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = datosNotificacionesServiceImpl.getMeasuredUnit(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getTypeWhereTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> tipoDonde = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageTipocuandoExtendsMapper.getTypeWhere(idLenguaje)).thenReturn(tipoDonde);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = datosNotificacionesServiceImpl.getTypeWhere(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void saveNotificationOKTest() throws Exception {

		String idLenguaje = "1";
		NotificacionEventoItem notificacionEventoItem = ageTestUtils.getNotificacionEventoItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageNotificacioneseventoMapper.insert(Mockito.any(AgeNotificacionesevento.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		InsertResponseDTO insertResponseDTOResultado = datosNotificacionesServiceImpl.saveNotification(notificacionEventoItem, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setCode(200);
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}
	
	@Test
	public void saveNotificationKOTest() throws Exception {

		String idLenguaje = "1";
		NotificacionEventoItem notificacionEventoItem = ageTestUtils.getNotificacionEventoItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageNotificacioneseventoMapper.insert(Mockito.any(AgeNotificacionesevento.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		InsertResponseDTO insertResponseDTOResultado = datosNotificacionesServiceImpl.saveNotification(notificacionEventoItem, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}
	
	@Test
	public void getTemplatesTest() throws Exception {

		Short idInstitucion = 2000;
		List<NotificacionEventoItem> notificaciones = ageTestUtils.getListNotificacionEventoItem();
		List<ComboPlantillaEnvioItem> listComboPlantillaEnvioEsperado = ageTestUtils.getListaComboPlantillaEnvioItem();

		when(envPlantillasenviosExtendsMapper.getTemplates(idInstitucion.toString())).thenReturn(notificaciones);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboPlantillaEnvioDTO comboPlantillaEnvioDTOResultado = datosNotificacionesServiceImpl.getTemplates(mockreq);

		ComboPlantillaEnvioDTO comboPlantillaEnvioDTOEsperado = new ComboPlantillaEnvioDTO();
		comboPlantillaEnvioDTOEsperado.setComboAgeItems(listComboPlantillaEnvioEsperado);
		
		assertThat(comboPlantillaEnvioDTOResultado).isEqualTo(comboPlantillaEnvioDTOEsperado);

	}
	
	@Test
	public void getTypeSendTest() throws Exception {

		String idLenguaje = "1";
		String idPlantillaEnvio = "1";
		String idTipoEnvio = "1";
		String idInstitucion = "2000";
		
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(envTipoenviosExtendsMapper.getTypeSend(idPlantillaEnvio, idTipoEnvio, idInstitucion, idLenguaje)).thenReturn(comboItemsSimulados);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = datosNotificacionesServiceImpl.getTypeSend(idPlantillaEnvio, idTipoEnvio, mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

	@Test
	public void updateNotificationOKTest() throws Exception {

		String idLenguaje = "1";
		NotificacionEventoItem notificacionEventoItem = ageTestUtils.getNotificacionEventoItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeNotificacionesevento> notificationList = ageTestUtils.getListAgeNotificacionesevento();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageNotificacioneseventoMapper.selectByExample(Mockito.any(AgeNotificacioneseventoExample.class))).thenReturn(notificationList);

		when(ageNotificacioneseventoMapper.updateByPrimaryKey(Mockito.any(AgeNotificacionesevento.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = datosNotificacionesServiceImpl.updateNotification(notificacionEventoItem, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(200);
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void updateNotificationKOTest() throws Exception {

		String idLenguaje = "1";
		NotificacionEventoItem notificacionEventoItem = ageTestUtils.getNotificacionEventoItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeNotificacionesevento> notificationList = ageTestUtils.getListAgeNotificacionesevento();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageNotificacioneseventoMapper.selectByExample(Mockito.any(AgeNotificacioneseventoExample.class))).thenReturn(notificationList);

		when(ageNotificacioneseventoMapper.updateByPrimaryKey(Mockito.any(AgeNotificacionesevento.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = datosNotificacionesServiceImpl.updateNotification(notificacionEventoItem, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void getEventNotificationsTest() throws Exception {

		Short idInstitucion = 2000;
		String idCalendario = "1";
		List<NotificacionEventoItem> eventNotifications = ageTestUtils.getListNotificacionEventoItem();

		when(ageNotificacioneseventoExtendsMapper.getEventNotifications(idCalendario, idInstitucion.toString())).thenReturn(eventNotifications);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NotificacionEventoDTO notificacionEventoDTOResultado = datosNotificacionesServiceImpl.getEventNotifications(idCalendario, mockreq);

		NotificacionEventoDTO notificacionEventoDTOEsperado = ageTestUtils.getNotificacionEventoDTOSimulado();
		
		assertThat(notificacionEventoDTOResultado).isEqualTo(notificacionEventoDTOEsperado);

	}

	@Test
	public void getHistoricEventNotificationsTest() throws Exception {

		Short idInstitucion = 2000;
		String idCalendario = "1";
		List<NotificacionEventoItem> eventNotifications = ageTestUtils.getListNotificacionEventoItem();

		when(ageNotificacioneseventoExtendsMapper.getHistoricEventNotifications(idCalendario, idInstitucion.toString())).thenReturn(eventNotifications);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NotificacionEventoDTO notificacionEventoDTOResultado = datosNotificacionesServiceImpl.getHistoricEventNotifications(idCalendario, mockreq);

		NotificacionEventoDTO notificacionEventoDTOEsperado = ageTestUtils.getNotificacionEventoDTOSimulado();
		
		assertThat(notificacionEventoDTOResultado).isEqualTo(notificacionEventoDTOEsperado);

	}
	

	@Test
	public void deleteNotificationOKTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeNotificacionesevento> notificacionesList = ageTestUtils.getListAgeNotificacionesevento();
		NotificacionEventoDTO notificacionEventoDTO = ageTestUtils.getNotificacionEventoDTOSimulado();


		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageNotificacioneseventoMapper.selectByExample(Mockito.any(AgeNotificacioneseventoExample.class))).thenReturn(notificacionesList);

		when(ageNotificacioneseventoMapper.updateByPrimaryKey(Mockito.any(AgeNotificacionesevento.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = datosNotificacionesServiceImpl.deleteNotification(notificacionEventoDTO, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(200);
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void deleteNotificationKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<AgeNotificacionesevento> notificacionesList = ageTestUtils.getListAgeNotificacionesevento();
		NotificacionEventoDTO notificacionEventoDTO = ageTestUtils.getNotificacionEventoDTOSimulado();


		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(ageNotificacioneseventoMapper.selectByExample(Mockito.any(AgeNotificacioneseventoExample.class))).thenReturn(notificacionesList);

		when(ageNotificacioneseventoMapper.updateByPrimaryKey(Mockito.any(AgeNotificacionesevento.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = datosNotificacionesServiceImpl.deleteNotification(notificacionEventoDTO, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
	
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
}