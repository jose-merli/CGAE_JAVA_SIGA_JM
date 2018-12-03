package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
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
public class FichaSociedadesServiceTest {

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Mock
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Mock
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;

	@Mock
	private CenTiposseguroExtendsMapper cenTiposseguroExtendsMapper;

	@InjectMocks
	private FichaColegialSociedadesServiceImpl fichaColegialSociedadesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();

	@Test
	public void searchSocietiesTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<BusquedaJuridicaItem> fichaDatosColegialesItems = new ArrayList<BusquedaJuridicaItem>();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");

		BusquedaJuridicaDTO responseEsperado = new BusquedaJuridicaDTO();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenComponentesExtendsMapper.searchSocieties(Mockito.anyString(), Mockito.anyString() , Mockito.anyString())).thenReturn(fichaDatosColegialesItems);
		
		//		Ejecución del método a testear
		BusquedaJuridicaDTO response = fichaColegialSociedadesServiceImpl.searchSocieties(1, "a",mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void searchSocietiesNoUserTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		BusquedaJuridicaDTO responseEsperado = new BusquedaJuridicaDTO();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);
		
		//		Ejecución del método a testear
		BusquedaJuridicaDTO response = fichaColegialSociedadesServiceImpl.searchSocieties(1, "a",mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
}
