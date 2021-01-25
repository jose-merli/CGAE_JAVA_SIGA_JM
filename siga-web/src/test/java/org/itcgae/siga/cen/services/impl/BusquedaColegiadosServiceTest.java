package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocolegialExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaColegiadosServiceTest {

	@Mock
	private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;
	
	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenEstadocolegialExtendsMapper cenEstadocolegialExtendsMapper;
	
	@Mock
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;
	
	@Mock
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@InjectMocks
	private BusquedaColegiadosServiceImpl busquedaColegiadosServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void getCivilStatusTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsValoresNulosSimulados();
		List<ComboItem> civilStatus = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenEstadocivilExtendsMapper.distinctCivilStatus(idLenguaje)).thenReturn(civilStatus);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaColegiadosServiceImpl.getCivilStatus(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getSituacionTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<ComboItem> situacion = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenEstadocolegialExtendsMapper.distinctSituacionColegial(idLenguaje)).thenReturn(situacion);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaColegiadosServiceImpl.getSituacion(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getCVCategoryTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsValoresNulosSimulados();
		List<ComboItem> cvCategory = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenTiposcvExtendsMapper.selectCategoriaCV(idLenguaje)).thenReturn(cvCategory);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaColegiadosServiceImpl.getCVCategory(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void getLabelTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsLabelTestSimulados();
		List<ComboItem> cvCategory = testUtils.getListComboItemsSimulados();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

//		when(cenColegiadoExtendsMapper.getLabel(usuarios.get(0))).thenReturn(cvCategory);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaColegiadosServiceImpl.getCVCategory(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}
	
	@Test
	public void searchColegiadoTest() throws Exception {

//		Short idInstitucion = 2000;
//		List<ColegiadoItem> colegiadoItemList = cenTestUtils.getListColegiadoItemSimulados();
//		ColegiadoItem colegiadoItem = cenTestUtils.getColegiadoItem();
//
//		when(cenColegiadoExtendsMapper.selectColegiados(idInstitucion, colegiadoItem)).thenReturn(colegiadoItemList);
//
//		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
//
//		ColegiadoDTO colegiadoDTOResultado = busquedaColegiadosServiceImpl.searchColegiado(colegiadoItem, mockreq);
//
//		ColegiadoDTO colegiadoDTOEsperado = new ColegiadoDTO();
//		colegiadoDTOEsperado.setColegiadoItem(colegiadoItemList);
//		
//		
//		assertThat(colegiadoDTOResultado).isEqualTo(colegiadoDTOEsperado);

	}
	
}