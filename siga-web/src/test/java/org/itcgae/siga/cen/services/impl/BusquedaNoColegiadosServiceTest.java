package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaNoColegiadosServiceTest {

	@Mock
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@InjectMocks
	private BusquedaNoColegiadosServiceImpl busquedaNoColegiadosServiceImpl;

	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	
	@Test
	public void searchNoColegiadoTest() throws Exception {

		Short idInstitucion = 2000;
		List<NoColegiadoItem> noColegiadoItemList = cenTestUtils.getListNoColegiadoItemSimulados();
		NoColegiadoItem noColegiadoItem = cenTestUtils.getNoColegiadoItem();

		when(cenNocolegiadoExtendsMapper.selectNoColegiados(idInstitucion, noColegiadoItem)).thenReturn(noColegiadoItemList);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoDTO noColegiadoDTOResultado = busquedaNoColegiadosServiceImpl.searchNoColegiado(noColegiadoItem, mockreq);

		NoColegiadoDTO noColegiadoDTOEsperado = new NoColegiadoDTO();
		noColegiadoDTOEsperado.setNoColegiadoItem(noColegiadoItemList);
		
		
		assertThat(noColegiadoDTOResultado).isEqualTo(noColegiadoDTOEsperado);
	}
	
	@Test
	public void searchHistoricNoColegiadoTest() throws Exception {

		Short idInstitucion = 2000;
		String idLenguaje = "1";
		List<NoColegiadoItem> noColegiadoItemList = cenTestUtils.getListNoColegiadoItemSimulados();
		NoColegiadoItem noColegiadoItem = cenTestUtils.getNoColegiadoItem();

		when(cenNocolegiadoExtendsMapper.searchHistoricNoColegiado(noColegiadoItem, idLenguaje, idInstitucion.toString())).thenReturn(noColegiadoItemList);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoDTO noColegiadoDTOResultado = busquedaNoColegiadosServiceImpl.searchHistoricNoColegiado(1, noColegiadoItem, mockreq);

		NoColegiadoDTO noColegiadoDTOEsperado = new NoColegiadoDTO();
		List<NoColegiadoItem> noColegiadoItemListNull = new ArrayList<NoColegiadoItem>();
		noColegiadoDTOEsperado.setNoColegiadoItem(noColegiadoItemListNull);
		
		
		assertThat(noColegiadoDTOResultado).isEqualTo(noColegiadoDTOEsperado);
	}
	
}