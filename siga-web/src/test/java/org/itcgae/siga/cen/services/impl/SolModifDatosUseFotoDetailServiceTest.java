package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModifFotoItem;
import org.itcgae.siga.commons.utils.CenTestUtils;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodifexportarfotoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SolModifDatosUseFotoDetailServiceTest {

	@Mock
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Mock
	private CenSolicmodifexportarfotoExtendsMapper  cenSolicModifExportarFotoExtendsMapper;

	@InjectMocks
	private SolModifDatosUseFotoDetailServiceImpl solModifDatosUseFotoDetailServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchDatosUseFotoDetailTest() throws Exception {

		CenCliente cenCliente = cenTestUtils.getCenClienteSimulado();
		
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();
				
		when(cenClienteExtendsMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cenCliente);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SoliModifFotoItem soliModifFotoItemResultado = solModifDatosUseFotoDetailServiceImpl
				.searchDatosUseFotoDetail(1, solModificacionItem, mockreq);

		SoliModifFotoItem soliModifFotoItemEsperado =  cenTestUtils.getSoliModifFotoItemSimulado(null);

		assertThat(soliModifFotoItemResultado.toString()).isEqualTo(soliModifFotoItemEsperado.toString());

	}
	
	@Test
	public void searchSolModifDatosUseFotoDetailTest() throws Exception {

		Short idSolicitud = (short) 1;
		
		CenSolicmodifexportarfoto cenSolicmodifexportarfoto = cenTestUtils.getCenSolicmodifexportarfotoSimulado();
		
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();
				
		when(cenSolicModifExportarFotoExtendsMapper.selectByPrimaryKey(idSolicitud)).thenReturn(cenSolicmodifexportarfoto);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SoliModifFotoItem soliModifFotoItemResultado = solModifDatosUseFotoDetailServiceImpl
				.searchSolModifDatosUseFotoDetail(1, solModificacionItem, mockreq);

		SoliModifFotoItem soliModifFotoItemEsperado =  cenTestUtils.getSoliModifFotoItemSimulado("2005001213");

		assertThat(soliModifFotoItemResultado.toString()).isEqualTo(soliModifFotoItemEsperado.toString());

	}
}
