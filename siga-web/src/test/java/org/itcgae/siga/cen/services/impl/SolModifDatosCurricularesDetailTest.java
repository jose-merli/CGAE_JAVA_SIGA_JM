package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvKey;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Key;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Key;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SolModifDatosCurricularesDetailTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;

	@Mock
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;

	@Mock
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

	@Mock
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Mock
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;

	@Mock
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;
	
	@InjectMocks
	private SolModifDatosCurricularesDetailServiceImpl solModifDatosCurricularesDetailServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchDatosCurricularesDetailTest() throws Exception {

		String idLenguaje = "1";
		Short idTipoCv = (short) 1;
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CenDatoscv cenDatoscv = cenTestUtils.getCenDatoscvSimulado();
		CenTiposcv cenTiposcv = cenTestUtils.getCenTiposcvSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado(null);
		CenTiposcvsubtipo1 cenTiposcvsubtipo1 = cenTestUtils.getCenTiposcvsubtipo1Simulado();
		CenTiposcvsubtipo2 cenTiposcvsubtipo2 = cenTestUtils.getCenTiposcvsubtipo2Simulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();
				
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDatoscvExtendsMapper.selectByPrimaryKey(Mockito.any(CenDatoscvKey.class))).thenReturn(cenDatoscv);
		when(cenTiposcvExtendsMapper.selectByPrimaryKey(idTipoCv)).thenReturn(cenTiposcv);
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);
		when(cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey(Mockito.any(CenTiposcvsubtipo1Key.class))).thenReturn(cenTiposcvsubtipo1);
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);
		when(cenTiposCVSubtipo2ExtendsMapper.selectByPrimaryKey(Mockito.any(CenTiposcvsubtipo2Key.class))).thenReturn(cenTiposcvsubtipo2);
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);


		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModifDatosCurricularesItem solModifDatosBancariosItemResultado = solModifDatosCurricularesDetailServiceImpl
				.searchDatosCurricularesDetail(1, solModificacionItem, mockreq);

		SolModifDatosCurricularesItem solModifDatosCurricularesItemEsperado =  cenTestUtils.getSolModifDatosCurricularesItemSimulado();

		assertThat(solModifDatosBancariosItemResultado).isEqualTo(solModifDatosCurricularesItemEsperado);

	}
	
	
	@Test
	public void searchSolModifDatosCurricularesDetailTest() throws Exception {

		String idLenguaje = "1";
		Long idSolicitud = (long) 1;
		Short idTipoCv = (short) 1;
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CenSolicitudmodificacioncv cenSolicitudmodificacioncv = cenTestUtils.getCenSolicitudmodificacioncvSimulado();
		CenTiposcv cenTiposcv = cenTestUtils.getCenTiposcvSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado(null);
		CenTiposcvsubtipo1 cenTiposcvsubtipo1 = cenTestUtils.getCenTiposcvsubtipo1Simulado();
		CenTiposcvsubtipo2 cenTiposcvsubtipo2 = cenTestUtils.getCenTiposcvsubtipo2Simulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();
				
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolicitudmodificacioncvExtendsMapper.selectByPrimaryKey(idSolicitud)).thenReturn(cenSolicitudmodificacioncv);
		when(cenTiposcvExtendsMapper.selectByPrimaryKey(idTipoCv)).thenReturn(cenTiposcv);
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);
		when(cenTiposCVSubtipo1ExtendsMapper.selectByPrimaryKey(Mockito.any(CenTiposcvsubtipo1Key.class))).thenReturn(cenTiposcvsubtipo1);
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);
		when(cenTiposCVSubtipo2ExtendsMapper.selectByPrimaryKey(Mockito.any(CenTiposcvsubtipo2Key.class))).thenReturn(cenTiposcvsubtipo2);
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);


		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModifDatosCurricularesItem solModifDatosCurricularesItemResultado = solModifDatosCurricularesDetailServiceImpl
				.searchSolModifDatosCurricularesDetail(1, solModificacionItem, mockreq);

		SolModifDatosCurricularesItem solModifDatosCurricularesItemEsperado =  cenTestUtils.getSolModifDatosCurricularesItemSimulado();

		assertThat(solModifDatosCurricularesItemResultado).isEqualTo(solModifDatosCurricularesItemEsperado);

	}
	

}
