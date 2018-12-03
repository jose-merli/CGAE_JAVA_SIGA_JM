package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposseguroExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaDatosColegialesServiceTest {

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Mock
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Mock
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenTratamientoExtendsMapper cenFichaDatosGeneralesExtendsMapper;

	@Mock
	private CenTiposseguroExtendsMapper cenTiposseguroExtendsMapper;

	@InjectMocks
	private FichaDatosColegialesServiceImpl fichaDatosColegialesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

	
	@Test
	public void getTratamientoTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO responseEsperado = new ComboDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		responseEsperado.setCombooItems(testUtils.getListComboItemsSimulados());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenFichaDatosGeneralesExtendsMapper.selectTratamiento(Mockito.anyString())).thenReturn(combo);
		
		//		Ejecución del método a testear
		ComboDTO response = fichaDatosColegialesServiceImpl.getTratamiento(mockreq);
		
		assertThat(response.getCombooItems()).isEqualTo(responseEsperado.getCombooItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void getTypeInsurancesTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO responseEsperado = new ComboDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		responseEsperado.setCombooItems(testUtils.getListComboItemsValoresNulosSimulados());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenTiposseguroExtendsMapper.getTypeInsurances(Mockito.anyString())).thenReturn(combo);
		
		//		Ejecución del método a testear
		ComboDTO response = fichaDatosColegialesServiceImpl.getTypeInsurances(mockreq);
		
		assertThat(response.getCombooItems()).isEqualTo(responseEsperado.getCombooItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void datosColegialesSearchTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoDTO responseEsperado = new ColegiadoDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<ColegiadoItem> colegiadoList = cenTestUtils.getListaColegiadosSimulada(true, "1");
		List<ColegiadoItem> colegiadoListItem = cenTestUtils.getListaColegiadosSimulada(true, "1");
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem();
		responseEsperado.setColegiadoItem(colegiadoList);
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenColegiadoExtendsMapper.selectColegiaciones(Mockito.anyShort(), Mockito.anyString(), Mockito.any(ColegiadoItem.class))).thenReturn(colegiadoListItem);
		
		//		Ejecución del método a testear
		ColegiadoDTO response = fichaDatosColegialesServiceImpl.datosColegialesSearch(1, colegiado, mockreq);//datosColegialesSearch
		
		assertThat(response.getColegiadoItem().toString()).isEqualTo(responseEsperado.getColegiadoItem().toString()); //Comparar resultado esperado con resultado que trae el TEST

	}
	
	
	@Test
	public void datosColegialesSearchNoUserTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoDTO responseEsperado = new ColegiadoDTO();
		List<ColegiadoItem> colegiadoList = new ArrayList<ColegiadoItem>();
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem();
		responseEsperado.setColegiadoItem(colegiadoList);
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);
		
//		when(cenColegiadoExtendsMapper.selectColegiaciones(Mockito.anyShort(), Mockito.anyString(), Mockito.any(ColegiadoItem.class))).thenReturn(colegiadoListItem);
		
		//		Ejecución del método a testear
		ColegiadoDTO response = fichaDatosColegialesServiceImpl.datosColegialesSearch(1, colegiado, mockreq);//datosColegialesSearch
		
		assertThat(response.getColegiadoItem().toString()).isEqualTo(responseEsperado.getColegiadoItem().toString()); //Comparar resultado esperado con resultado que trae el TEST

	}
		
	
}
