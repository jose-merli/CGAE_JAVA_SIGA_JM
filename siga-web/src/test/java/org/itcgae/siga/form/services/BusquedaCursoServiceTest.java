package org.itcgae.siga.form.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.ForTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForCursoExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadocursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForVisibilidadcursoExtendsMapper;
import org.itcgae.siga.form.services.impl.BusquedaCursosServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaCursoServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private ForEstadocursoExtendsMapper forEstadocursoExtendsMapper;

	@Mock
	private ForVisibilidadcursoExtendsMapper forVisibilidadcursoExtendsMapper;

	@Mock
	private ForTemacursoExtendsMapper forTemacursoExtendsMapper;

	@Mock
	private ForCursoExtendsMapper forCursoExtendsMapper;

	@Mock
	private AdmUsuariosMapper admUsuariosMapper;

	@InjectMocks
	private BusquedaCursosServiceImpl busquedaCursosServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private ForTestUtils forTestUtils = new ForTestUtils();

	@Test
	public void getVisibilidadCursosTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		// Cuando se encuentre este método con cualquier objeto del tipo
		// AdmUsuariosExample (Mockito.any(AdmUsuariosExample.class)) dentro del
		// servicio "busquedaCursosServiceImpl", devolverá lo que le estamos poniendo en
		// el thenReturn
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(forVisibilidadcursoExtendsMapper.distinctVisibilidadCurso(idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaCursosServiceImpl.getVisibilidadCursos(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		ComboItem comboItemVacio = testUtils.getComboItemVacio();
				
		comboItemsSimulados.add(0, comboItemVacio);
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getEstadosCursosTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		// Cuando se encuentre este método con cualquier objeto del tipo
		// AdmUsuariosExample (Mockito.any(AdmUsuariosExample.class)) dentro del
		// servicio "busquedaCursosServiceImpl", devolverá lo que le estamos poniendo en
		// el thenReturn
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(forEstadocursoExtendsMapper.distinctEstadoCurso(idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaCursosServiceImpl.getEstadosCursos(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		ComboItem comboItemVacio = testUtils.getComboItemVacio();
				
		comboItemsSimulados.add(0, comboItemVacio);
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getTemasCursosTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		// Cuando se encuentre este método con cualquier objeto del tipo
		// AdmUsuariosExample (Mockito.any(AdmUsuariosExample.class)) dentro del
		// servicio "busquedaCursosServiceImpl", devolverá lo que le estamos poniendo en
		// el thenReturn
		Short idInstitucion = new Short("2000");
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(forTemacursoExtendsMapper.distinctTemaCurso(idInstitucion,idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaCursosServiceImpl.getTemasCursos(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void searchCursoTest() throws Exception {
		
		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO,SigaConstants.CURSO_SIN_ARCHIVAR);
		CursoItem cursoItemBusqueda = forTestUtils.getCursoSimulado(SigaConstants.ESTADO_CURSO_CANCELADO,SigaConstants.CURSO_SIN_ARCHIVAR);
		
		when(forCursoExtendsMapper.selectCursos(Mockito.anyShort(), Mockito.any(CursoItem.class), Mockito.anyString())).thenReturn(cursoItemList);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CursoDTO cursoResultado = busquedaCursosServiceImpl.searchCurso(cursoItemBusqueda, mockreq);
		
		CursoDTO cursoEsperado = new CursoDTO();
		cursoEsperado.setCursoItem(cursoItemList);
		
		assertThat(cursoResultado).isEqualTo(cursoEsperado);

	}
	
	@Test
	public void searchCursoTestKO() throws Exception {
		
		CursoItem cursoItemBusqueda = forTestUtils.getCursoSimulado(SigaConstants.ESTADO_CURSO_CANCELADO,SigaConstants.CURSO_SIN_ARCHIVAR);
		
		when(forCursoExtendsMapper.selectCursos(Mockito.anyShort(), Mockito.any(CursoItem.class), Mockito.anyString())).thenReturn(null);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CursoDTO cursoResultado = busquedaCursosServiceImpl.searchCurso(cursoItemBusqueda, mockreq);
		
		CursoDTO cursoEsperado = new CursoDTO();
		List<CursoItem> listaCursoItems = null;
		cursoEsperado.setCursoItem(listaCursoItems);
		
		assertThat(cursoResultado).isEqualTo(cursoEsperado);

	}
	
	@Test
	public void archivarCursosTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO,SigaConstants.CURSO_SIN_ARCHIVAR);
		
		when(forCursoExtendsMapper.updateByExampleSelective(Mockito.any(ForCurso.class), Mockito.any(ForCursoExample.class))).thenReturn((int)1);
		when(admUsuariosMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		int archivarResultado = busquedaCursosServiceImpl.archivarCursos(cursoItemList, mockreq);
		
		int resultadoEsperado = 1;
		
		assertThat(archivarResultado).isEqualTo(resultadoEsperado);

	}
	
	@Test
	public void archivarCursosTestKOListVacia() throws Exception {

		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO,SigaConstants.CURSO_ARCHIVADO);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		int archivarResultado = busquedaCursosServiceImpl.archivarCursos(cursoItemList, mockreq);
		
		int resultadoEsperado = 0;
		
		assertThat(archivarResultado).isEqualTo(resultadoEsperado);

	}
	
	@Test
	public void archivarCursosTestKOUserNull() throws Exception {

		List<AdmUsuarios> usuarios = new ArrayList<AdmUsuarios>();
		usuarios.add(null);
		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO,SigaConstants.CURSO_SIN_ARCHIVAR);
		
		when(forCursoExtendsMapper.updateByExampleSelective(Mockito.any(ForCurso.class), Mockito.any(ForCursoExample.class))).thenReturn((int)1);
		when(admUsuariosMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		int archivarResultado = busquedaCursosServiceImpl.archivarCursos(cursoItemList, mockreq);
		
		int resultadoEsperado = 0;
		
		assertThat(archivarResultado).isEqualTo(resultadoEsperado);

	}
	
	@Test
	public void desarchivarCursos() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO, SigaConstants.CURSO_ARCHIVADO);
		
		when(forCursoExtendsMapper.updateByExampleSelective(Mockito.any(ForCurso.class), Mockito.any(ForCursoExample.class))).thenReturn((int)1);
		when(admUsuariosMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		int desarchivarResultado = busquedaCursosServiceImpl.desarchivarCursos(cursoItemList, mockreq);
		
		int resultadoEsperado = 1;
		
		assertThat(desarchivarResultado).isEqualTo(resultadoEsperado);

	}
	
	@Test
	public void desarchivarCursosKOListVacia() throws Exception {

		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO, SigaConstants.CURSO_SIN_ARCHIVAR);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		int desarchivarResultado = busquedaCursosServiceImpl.desarchivarCursos(cursoItemList, mockreq);
		
		int resultadoEsperado = 0;
		
		assertThat(desarchivarResultado).isEqualTo(resultadoEsperado);

	}
	
	@Test
	public void desarchivarCursosKOUserNull() throws Exception {
		
		List<AdmUsuarios> usuarios = new ArrayList<AdmUsuarios>();
		usuarios.add(null);
		List<CursoItem> cursoItemList = forTestUtils.getListCursosSimulados(SigaConstants.ESTADO_CURSO_CANCELADO, SigaConstants.CURSO_ARCHIVADO);
		
		when(forCursoExtendsMapper.updateByExampleSelective(Mockito.any(ForCurso.class), Mockito.any(ForCursoExample.class))).thenReturn((int)1);
		when(admUsuariosMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		int desarchivarResultado = busquedaCursosServiceImpl.desarchivarCursos(cursoItemList, mockreq);
		
		int resultadoEsperado = 0;
		
		assertThat(desarchivarResultado).isEqualTo(resultadoEsperado);

	}

}
