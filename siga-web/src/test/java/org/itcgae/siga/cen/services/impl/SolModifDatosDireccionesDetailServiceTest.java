package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPais;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenProvincias;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPaisExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPoblacionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolimodidireccionesExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SolModifDatosDireccionesDetailServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Mock
	private CenSolimodidireccionesExtendsMapper cenSoliModiDireccionesExtendsMapper;

	@Mock
	private CenPaisExtendsMapper cenPaisExtendsMapper;

	@Mock
	private CenProvinciasExtendsMapper cenProvinciasExtendsMapper;

	@Mock
	private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;

	@Mock
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	@InjectMocks
	private SolModifDatosDireccionesDetailServiceImpl solModifDatosDireccionesDetailServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchSolModifDatosDireccionesDetailTest() throws Exception {

		String idLenguaje = "1";
		Long idSolicitud = (long) 1;
		String idPais = "191";
		String idProvincia = "04";
		String idPoblacion = "04064000605";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CenSolimodidirecciones cenSolimodidirecciones = cenTestUtils.getCenSolimodidireccionesSimulado();
		CenPais cenPais = cenTestUtils.getCenPaisSimulado();
		CenProvincias cenProvincias = cenTestUtils.getCenProvinciasSimulado();
		CenPoblaciones cenPoblaciones = cenTestUtils.getCenPoblacionesSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado(null);

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenSoliModiDireccionesExtendsMapper.selectByPrimaryKey(idSolicitud))
				.thenReturn(cenSolimodidirecciones);

		when(cenPaisExtendsMapper.selectByPrimaryKey(idPais))
		.thenReturn(cenPais);
		
		when(cenProvinciasExtendsMapper.selectByPrimaryKey(idProvincia)).thenReturn(cenProvincias);
		
		when(cenPoblacionesExtendsMapper.selectByPrimaryKey(idPoblacion))
		.thenReturn(cenPoblaciones);
		
		when(genRecursosCatalogosExtendsMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class))).thenReturn(genRecursosCatalogos);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SoliModiDireccionesItem soliModiDireccionesItemResultado = solModifDatosDireccionesDetailServiceImpl
				.searchSolModifDatosDireccionesDetail(1, solModificacionItem, mockreq);

		SoliModiDireccionesItem soliModiDireccionesItemEsperado = cenTestUtils.getSoliModiDireccionesItemSimulado();

		assertThat(soliModiDireccionesItemResultado.toString()).isEqualTo(soliModiDireccionesItemEsperado.toString());
	}
	
	@Test
	public void searchDatosDireccionesTest() throws Exception {

		String idLenguaje = "1";
		String idPersona = "2005001213";
		String codigo = "1";
		String idInstitucion = "2000";
		
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<DatosDireccionesItem> datosDireccionesItem = cenTestUtils.getListDatosDireccionesItemSimulado();

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenDireccionesExtendsMapper.selectDireccionesSolEsp(idPersona, codigo, idInstitucion))
				.thenReturn(datosDireccionesItem);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SoliModiDireccionesItem soliModiDireccionesItemResultado = solModifDatosDireccionesDetailServiceImpl
				.searchDatosDirecciones(1, solModificacionItem, mockreq);

		SoliModiDireccionesItem soliModiDireccionesItemEsperado = cenTestUtils.getSoliModiDireccionesItemSimulado();

		assertThat(soliModiDireccionesItemResultado.toString()).isEqualTo(soliModiDireccionesItemEsperado.toString());
	}
}
