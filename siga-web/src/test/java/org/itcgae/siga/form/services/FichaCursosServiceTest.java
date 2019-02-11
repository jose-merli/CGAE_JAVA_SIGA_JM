package org.itcgae.siga.form.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.ForTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForRolesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTipocosteExtendsMapper;
import org.itcgae.siga.form.services.impl.FichaCursosServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaCursosServiceTest {

	@Mock
	private ForCursoExtendsMapper forCursoExtendsMapper;
	
	@Mock
	private ForPersonacursoExtendsMapper forPersonacursoExtendsMapper;

	@Mock
	private ForRolesExtendsMapper forRolesExtendsMapper;

	@Mock
	private ForTipocosteExtendsMapper forTipocosteExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Mock
	private CenClienteMapper cenClienteMapper;

	@InjectMocks
	private FichaCursosServiceImpl fichaCursosServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private ForTestUtils forTestUtils = new ForTestUtils();

	@Test
	public void getTrainersCourseTest() throws Exception {

		Short idInstitucion = 2000;
		String idLenguaje = "1";
		String idCurso = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<FormadorCursoItem> formadoresCursoItem = forTestUtils.getListFormadoresCursoItem();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.getTrainersCourse(idInstitucion.toString(), idCurso, idLenguaje)).thenReturn(formadoresCursoItem);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		FormadorCursoDTO formadoresCursoResultado = fichaCursosServiceImpl.getTrainersCourse(idCurso, mockreq);

		FormadorCursoDTO formadoresCursoDTOEsperado = forTestUtils.getFormadorCursoDTOSimulado();
		
		assertThat(formadoresCursoResultado).isEqualTo(formadoresCursoDTOEsperado);

	}
	
	@Test
	public void getRolesTrainersTest() throws Exception {

		Short idInstitucion = 2000;
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> rolesTrainers = testUtils.getListComboItemsSimulados();

		when(forRolesExtendsMapper.getRolesTrainers(idInstitucion.toString(),"1")).thenReturn(rolesTrainers);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaCursosServiceImpl.getRolesTrainers(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getTypeCostTrainersTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> tipoCosteFormadores = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forTipocosteExtendsMapper.getTypeCostTrainers(idLenguaje)).thenReturn(tipoCosteFormadores);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = fichaCursosServiceImpl.getTypeCostTrainers(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void updateTrainersCourseOKTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ForPersonaCurso> trainersList = forTestUtils.getListForPersonaCursoSimulados();
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainersList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaCursosServiceImpl.updateTrainersCourse(formadorCursoDTO, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(200);
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void updateTrainersCourseKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ForPersonaCurso> trainersList = forTestUtils.getListForPersonaCursoSimulados();
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainersList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaCursosServiceImpl.updateTrainersCourse(formadorCursoDTO, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Error al modificar el formador");
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	@Test
	public void saveTrainersCourseOKTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comoboItems = testUtils.getListComboItemsSimulados();
		List<ForPersonaCurso> trainersList = null;
		List<ForPersonaCurso> tutorList = null;
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainersList);

		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(comoboItems);

		when(cenPersonaExtendsMapper.insert(Mockito.any(CenPersona.class))).thenReturn(1);

		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(tutorList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenReturn(1);

		when(forPersonacursoExtendsMapper.insert(Mockito.any(ForPersonaCurso.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = fichaCursosServiceImpl.saveTrainersCourse(formadorCursoDTO, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setCode(200);
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}
	
	@Test
	public void saveTrainersCourseOKExistTutorTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comoboItems = testUtils.getListComboItemsSimulados();
		List<ForPersonaCurso> trainersList = forTestUtils.getListForPersonaCursoSimulados();
		List<ForPersonaCurso> tutorList = forTestUtils.getListForPersonaCursoSimulados();
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainersList);

		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(comoboItems);

		when(cenPersonaExtendsMapper.insert(Mockito.any(CenPersona.class))).thenReturn(1);

		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(tutorList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenReturn(1);

		when(forPersonacursoExtendsMapper.insert(Mockito.any(ForPersonaCurso.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = fichaCursosServiceImpl.saveTrainersCourse(formadorCursoDTO, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setMessage("Ya existe el formador añadido con ese rol");
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}
	
	@Test
	public void saveTrainersCourseKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comoboItems = testUtils.getListComboItemsSimulados();
		List<ForPersonaCurso> trainersList = null;
		List<ForPersonaCurso> tutorList = null;
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainersList);

		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(comoboItems);

		when(cenPersonaExtendsMapper.insert(Mockito.any(CenPersona.class))).thenThrow(Exception.class);

		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(0);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(tutorList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenReturn(0);

		when(forPersonacursoExtendsMapper.insert(Mockito.any(ForPersonaCurso.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = fichaCursosServiceImpl.saveTrainersCourse(formadorCursoDTO, mockreq);

		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);

	}
	
	@Test
	public void deleteTrainersCourseTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ForPersonaCurso> trainersList = forTestUtils.getListForPersonaCursoSimulados();
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();


		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainersList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaCursosServiceImpl.deleteTrainersCourse(formadorCursoDTO, mockreq);

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
		List<ForPersonaCurso> trainerList = forTestUtils.getListForPersonaCursoSimulados();
		FormadorCursoDTO formadorCursoDTO = forTestUtils.getFormadorCursoDTOSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(forPersonacursoExtendsMapper.selectByExample(Mockito.any(ForPersonaCursoExample.class))).thenReturn(trainerList);

		when(forPersonacursoExtendsMapper.updateByPrimaryKey(Mockito.any(ForPersonaCurso.class))).thenThrow(Exception.class);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = fichaCursosServiceImpl.deleteTrainersCourse(formadorCursoDTO, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}
	
	
}