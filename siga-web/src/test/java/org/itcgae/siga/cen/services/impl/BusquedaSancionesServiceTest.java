package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.services.cen.mappers.CenTiposancionExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaSancionesServiceTest {

	@Mock
	private CenTiposancionExtendsMapper cenTiposancionExtendsMapper;
	
	@InjectMocks
	private BusquedaSancionesServiceImpl busquedaSancionesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();

	@Test
	public void getComboTipoSancionTest() throws Exception {
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();

		when(cenTiposancionExtendsMapper.getComboTipoSancion()).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaSancionesServiceImpl.getComboTipoSancion(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		ComboItem comboItemVacio = testUtils.getComboItemVacio();
				
		comboItemsSimulados.add(0, comboItemVacio);
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);
	}
}
