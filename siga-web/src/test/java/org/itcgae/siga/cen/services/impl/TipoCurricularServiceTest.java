package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TipoCurricularServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;

	@Mock
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;
	
	@Mock
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	@InjectMocks
	private TipoCurricularServiceImpl tipoCurricularServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void getComboCategoriaCurricularTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposcvExtendsMapper
				.selectCategoriaCV(idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = tipoCurricularServiceImpl.getComboCategoriaCurricular(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

	@Test
	public void searchTipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		TipoCurricularItem tipoCurricularItem = cenTestUtils.getTipoCurricularItemSimulado();
		List<TipoCurricularItem> tipoCurricularItems = cenTestUtils.getListTipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposCVSubtipo1ExtendsMapper
				.searchTipoCurricular(tipoCurricularItem, idLenguaje, idInstitucion)).thenReturn(tipoCurricularItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		TipoCurricularDTO tipoCurricularDTOResultado = tipoCurricularServiceImpl.searchTipoCurricular(1, tipoCurricularItem, mockreq);
		
		TipoCurricularDTO tipoCurricularDTOEsperado = cenTestUtils.getTipoCurricularDTO();
		
		assertThat(tipoCurricularDTOResultado).isEqualTo(tipoCurricularDTOEsperado);

	}

	@Test
	public void createTipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String tipoCategoriaCurricular = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		TipoCurricularItem tipoCurricularItem = cenTestUtils.getTipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(1);

		when(cenTiposCVSubtipo1ExtendsMapper.getMaxIdCvSubtipo1(idInstitucion, tipoCategoriaCurricular)).thenReturn(newIdDTO);
		
		when(cenTiposCVSubtipo1ExtendsMapper.insert(Mockito.any(CenTiposcvsubtipo1.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = tipoCurricularServiceImpl.createTipoCurricular(tipoCurricularItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}
	
	@Test
	public void createTipoCurricularKOTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String tipoCategoriaCurricular = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		TipoCurricularItem tipoCurricularItem = cenTestUtils.getTipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(0);

		when(cenTiposCVSubtipo1ExtendsMapper.getMaxIdCvSubtipo1(idInstitucion, tipoCategoriaCurricular)).thenReturn(newIdDTO);
		
		when(cenTiposCVSubtipo1ExtendsMapper.insert(Mockito.any(CenTiposcvsubtipo1.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = tipoCurricularServiceImpl.createTipoCurricular(tipoCurricularItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.KO);
		Error error = new Error();
		error.setMessage("general.message.error.realiza.accion");
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado.getStatus()).isEqualTo(insertResponseDTOEsperado.getStatus());
	}

	@Test
	public void updateTipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		TipoCurricularDTO tipoCurricularDTO = cenTestUtils.getTipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(1);

		when(cenTiposCVSubtipo1ExtendsMapper.updateByPrimaryKey(Mockito.any(CenTiposcvsubtipo1.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = tipoCurricularServiceImpl.updateTipoCurricular(tipoCurricularDTO, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void updateTipoCurricularKOTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		TipoCurricularDTO tipoCurricularDTO = cenTestUtils.getTipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(0);

		when(cenTiposCVSubtipo1ExtendsMapper.updateByPrimaryKey(Mockito.any(CenTiposcvsubtipo1.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = tipoCurricularServiceImpl.updateTipoCurricular(tipoCurricularDTO, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void deleteTipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		TipoCurricularDTO tipoCurricularDTO = cenTestUtils.getTipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenTiposCVSubtipo1ExtendsMapper.updateByExampleSelective(Mockito.any(CenTiposcvsubtipo1.class), Mockito.any(CenTiposcvsubtipo1Example.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		DeleteResponseDTO deleteResponseDTOResultado = tipoCurricularServiceImpl.deleteTipoCurricular(tipoCurricularDTO, mockreq);
		
		DeleteResponseDTO deleteResponseDTOEsperado = new DeleteResponseDTO();
		deleteResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(deleteResponseDTOResultado).isEqualTo(deleteResponseDTOEsperado);
	}
	
	@Test
	public void deleteTipoCurricularKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		TipoCurricularDTO tipoCurricularDTO = cenTestUtils.getTipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenTiposCVSubtipo1ExtendsMapper.updateByExampleSelective(Mockito.any(CenTiposcvsubtipo1.class), Mockito.any(CenTiposcvsubtipo1Example.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		DeleteResponseDTO deleteResponseDTOResultado = tipoCurricularServiceImpl.deleteTipoCurricular(tipoCurricularDTO, mockreq);
		
		DeleteResponseDTO deleteResponseDTOEsperado = new DeleteResponseDTO();
		deleteResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(deleteResponseDTOResultado).isEqualTo(deleteResponseDTOEsperado);
	}

	@Test
	public void getHistoryTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		TipoCurricularItem tipoCurricularItem = cenTestUtils.getTipoCurricularItemSimulado();
		List<TipoCurricularItem> listTipoCurricularItem = cenTestUtils.getListTipoCurricularItemSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposCVSubtipo1ExtendsMapper
				.getHistory(tipoCurricularItem, idInstitucion, idLenguaje)).thenReturn(listTipoCurricularItem);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		TipoCurricularDTO tipoCurricularDTOResultado = tipoCurricularServiceImpl.getHistory(tipoCurricularItem, mockreq);
		
		TipoCurricularDTO tipoCurricularDTOEsperado = cenTestUtils.getTipoCurricularDTO();
		
		assertThat(tipoCurricularDTOResultado).isEqualTo(tipoCurricularDTOEsperado);

	}
}
