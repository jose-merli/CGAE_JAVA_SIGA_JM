package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModifDatosBasicosItem;
import org.itcgae.siga.commons.utils.CenTestUtils;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SolModifDatosGeneralesDetailServiceTest {
	
	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenSolicitmodifdatosbasicosExtendsMapper cenSolicitModifDatosBasicosExtendsMapper;

	@Mock
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Mock
	private AdmLenguajesMapper admLenguajesMapper;

	@Mock
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	@InjectMocks
	private SolModifDatosGeneralesDetailServiceImpl solModifDatosGeneralesDetailServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void searchDatosGeneralesDetailTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CenCliente cenCliente = cenTestUtils.getCenClienteSimulado();
		AdmLenguajes admLenguajes = cenTestUtils.getAdmLenguajesSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado("Español");

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenClienteExtendsMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cenCliente);
		when(admLenguajesMapper.selectByPrimaryKey(idLenguaje)).thenReturn(admLenguajes);
		when(genRecursosCatalogosExtendsMapper
				.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);


		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SoliModifDatosBasicosItem soliModifDatosBasicosItemResultado = solModifDatosGeneralesDetailServiceImpl
				.searchDatosGeneralesDetail(1, solModificacionItem, mockreq);

		SoliModifDatosBasicosItem soliModifDatosBasicosItemEsperado = cenTestUtils.getSoliModifDatosBasicosItemSimulado(null);

		assertThat(soliModifDatosBasicosItemResultado.toString()).isEqualTo(soliModifDatosBasicosItemEsperado.toString());
	}
	
	@Test
	public void searchSolModifDatosGeneralesDetailTest() throws Exception {
		String idLenguaje = "1";
		Short idSolicitud = (short) 1;
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CenSolicitmodifdatosbasicos cenSolicitmodifdatosbasicos = cenTestUtils.getCenSolicitmodifdatosbasicosSimulado();
		AdmLenguajes admLenguajes = cenTestUtils.getAdmLenguajesSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado("Español");

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolicitModifDatosBasicosExtendsMapper.selectByPrimaryKey(idSolicitud)).thenReturn(cenSolicitmodifdatosbasicos);
		when(admLenguajesMapper.selectByPrimaryKey(idLenguaje)).thenReturn(admLenguajes);
		when(genRecursosCatalogosExtendsMapper
				.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);


		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SoliModifDatosBasicosItem soliModifDatosBasicosItemResultado = solModifDatosGeneralesDetailServiceImpl
				.searchSolModifDatosGeneralesDetail(1, solModificacionItem, mockreq);

		SoliModifDatosBasicosItem soliModifDatosBasicosItemEsperado = cenTestUtils.getSoliModifDatosBasicosItemSimulado("2005001213");

		assertThat(soliModifDatosBasicosItemResultado.toString()).isEqualTo(soliModifDatosBasicosItemEsperado.toString());

	}
}
