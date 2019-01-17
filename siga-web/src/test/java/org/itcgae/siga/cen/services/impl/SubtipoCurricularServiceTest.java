package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SubtipoCurricularServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

	@Mock
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	@InjectMocks
	private SubtipoCurricularServiceImpl subtipoCurricularServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchSubtipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		SubtipoCurricularItem subtipoCurricularItem = cenTestUtils.getSubtipoCurricularItemSimulado();
		List<SubtipoCurricularItem> subtipoCurricularItems = cenTestUtils.getListSubtipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposCVSubtipo2ExtendsMapper
				.searchSubtipoCurricular(subtipoCurricularItem, idLenguaje, idInstitucion)).thenReturn(subtipoCurricularItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SubtipoCurricularDTO subtipoCurricularDTOResultado = subtipoCurricularServiceImpl.searchSubtipoCurricular(1, subtipoCurricularItem, mockreq);
		
		SubtipoCurricularDTO subtipoCurricularDTOEsperado = cenTestUtils.getSubtipoCurricularDTO();
		
		assertThat(subtipoCurricularDTOResultado).isEqualTo(subtipoCurricularDTOEsperado);

	}

	@Test
	public void getComboSubtipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SubtipoCurricularItem subtipoCurricularItem = cenTestUtils.getSubtipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposCVSubtipo2ExtendsMapper
				.searchComboSubtipoCurricular(subtipoCurricularItem, idLenguaje, idInstitucion)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = subtipoCurricularServiceImpl.getCurricularSubtypeCombo("1", mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

	@Test
	public void createSubtipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String tipoCategoriaCurricular = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		SubtipoCurricularItem subtipoCurricularItem = cenTestUtils.getSubtipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(1);

		when(cenTiposCVSubtipo2ExtendsMapper.getMaxIdCvSubtipo2(idInstitucion, tipoCategoriaCurricular)).thenReturn(newIdDTO);
		
		when(cenTiposCVSubtipo2ExtendsMapper.insert(Mockito.any(CenTiposcvsubtipo2.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = subtipoCurricularServiceImpl.createSubtipoCurricular(subtipoCurricularItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}
	
	@Test
	public void createSubtipoCurricularKOTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String tipoCategoriaCurricular = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		SubtipoCurricularItem subtipoCurricularItem = cenTestUtils.getSubtipoCurricularItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(0);

		when(cenTiposCVSubtipo2ExtendsMapper.getMaxIdCvSubtipo2(idInstitucion, tipoCategoriaCurricular)).thenReturn(newIdDTO);
		
		when(cenTiposCVSubtipo2ExtendsMapper.insert(Mockito.any(CenTiposcvsubtipo2.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = subtipoCurricularServiceImpl.createSubtipoCurricular(subtipoCurricularItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.KO);
		Error error = new Error();
		error.setMessage("general.message.error.realiza.accion");
		insertResponseDTOEsperado.setError(error);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}

	@Test
	public void updateSubtipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		SubtipoCurricularDTO subtipoCurricularDTO = cenTestUtils.getSubtipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(1);

		when(cenTiposCVSubtipo2ExtendsMapper.updateByPrimaryKey(Mockito.any(CenTiposcvsubtipo2.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = subtipoCurricularServiceImpl.updateSubtipoCurricular(subtipoCurricularDTO, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void updateSubtipoCurricularKOTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<GenRecursosCatalogos> genRecursosCatalogos = cenTestUtils.getListGenRecursosCatalogosSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		SubtipoCurricularDTO subtipoCurricularDTO = cenTestUtils.getSubtipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(genRecursosCatalogosExtendsMapper.selectByExample(Mockito.any(GenRecursosCatalogosExample.class))).thenReturn(genRecursosCatalogos);
				
		when(genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion, idLenguaje)).thenReturn(newIdDTO);

		when(genRecursosCatalogosExtendsMapper.insert(Mockito.any(GenRecursosCatalogos.class))).thenReturn(0);

		when(cenTiposCVSubtipo2ExtendsMapper.updateByPrimaryKey(Mockito.any(CenTiposcvsubtipo2.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = subtipoCurricularServiceImpl.updateSubtipoCurricular(subtipoCurricularDTO, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void deleteSubtipoCurricularTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SubtipoCurricularDTO subtipoCurricularDTO = cenTestUtils.getSubtipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenTiposCVSubtipo2ExtendsMapper.updateByExampleSelective(Mockito.any(CenTiposcvsubtipo2.class), Mockito.any(CenTiposcvsubtipo2Example.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		DeleteResponseDTO deleteResponseDTOResultado = subtipoCurricularServiceImpl.deleteSubtipoCurricular(subtipoCurricularDTO, mockreq);
		
		DeleteResponseDTO deleteResponseDTOEsperado = new DeleteResponseDTO();
		deleteResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(deleteResponseDTOResultado).isEqualTo(deleteResponseDTOEsperado);
	}
	
	@Test
	public void deleteTipoCurricularKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SubtipoCurricularDTO subtipoCurricularDTO = cenTestUtils.getSubtipoCurricularDTO();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenTiposCVSubtipo2ExtendsMapper.updateByExampleSelective(Mockito.any(CenTiposcvsubtipo2.class), Mockito.any(CenTiposcvsubtipo2Example.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		DeleteResponseDTO deleteResponseDTOResultado = subtipoCurricularServiceImpl.deleteSubtipoCurricular(subtipoCurricularDTO, mockreq);
		
		DeleteResponseDTO deleteResponseDTOEsperado = new DeleteResponseDTO();
		deleteResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(deleteResponseDTOResultado).isEqualTo(deleteResponseDTOEsperado);
	}
	
	@Test
	public void getHistoryTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		SubtipoCurricularItem subtipoCurricularItem = cenTestUtils.getSubtipoCurricularItemSimulado();
		List<SubtipoCurricularItem> subtipoCurricularItems = cenTestUtils.getListSubtipoCurricularItemSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposCVSubtipo2ExtendsMapper
				.getHistory(subtipoCurricularItem, idInstitucion, idLenguaje)).thenReturn(subtipoCurricularItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SubtipoCurricularDTO subtipoCurricularDTOResultado = subtipoCurricularServiceImpl.getHistory(subtipoCurricularItem, mockreq);
		
		SubtipoCurricularDTO subtipoCurricularDTOEsperado = cenTestUtils.getSubtipoCurricularDTO();
		
		assertThat(subtipoCurricularDTOResultado).isEqualTo(subtipoCurricularDTOEsperado);

	}

}
