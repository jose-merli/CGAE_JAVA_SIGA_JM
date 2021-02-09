package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocolegialExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaColegiadosServiceTest {

	private Logger LOGGER = Logger.getLogger(BusquedaColegiadosServiceImpl.class);

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
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

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

		Short idInstitucion = 2000;
		List<ColegiadoItem> colegiadoItemList = cenTestUtils.getListColegiadoItemSimulados();
		ColegiadoItem colegiadoItem = cenTestUtils.getColegiadoItem();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("CEN").andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO").andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
	    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
		when(cenColegiadoExtendsMapper.selectColegiados(idInstitucion, colegiadoItem,500)).thenReturn(colegiadoItemList);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoDTO colegiadoDTOResultado = busquedaColegiadosServiceImpl.searchColegiado(colegiadoItem, mockreq);

		ColegiadoDTO colegiadoDTOEsperado = new ColegiadoDTO();
		colegiadoDTOEsperado.setColegiadoItem(colegiadoItemList);
		
		
		assertThat(colegiadoDTOResultado).isEqualTo(colegiadoDTOEsperado);

	}
	
}