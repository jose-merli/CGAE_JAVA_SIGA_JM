package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.commons.utils.CenTestUtils;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodicuentasExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SolModifDatosBancariosDetailServiceTest {

	@Mock
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

	@Mock
	private CenSolicmodicuentasExtendsMapper cenSolicmodicuentasExtendsMapper;

	@InjectMocks
	private SolModifDatosBancariosDetailServiceImpl solModifDatosBancariosDetailServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void searchDatosBancariosDetailTest() throws Exception {

		CenCuentasbancarias cenCuentasbancarias = cenTestUtils.getCenCuentasbancariasSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenCuentasbancariasExtendsMapper.selectByPrimaryKey(Mockito.any(CenCuentasbancariasKey.class)))
				.thenReturn(cenCuentasbancarias);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModifDatosBancariosItem solModifDatosBancariosItemResultado = solModifDatosBancariosDetailServiceImpl
				.searchDatosBancariosDetail(1, solModificacionItem, mockreq);

		SolModifDatosBancariosItem solModifDatosBancariosItemEsperado = cenTestUtils
				.getSolModifDatosBancariosItemSimulado();

		assertThat(solModifDatosBancariosItemResultado).isEqualTo(solModifDatosBancariosItemEsperado);

	}

}
