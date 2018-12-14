package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenSancion;
import org.itcgae.siga.db.entities.CenSancionKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSancionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposancionExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaSancionesServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenTiposancionExtendsMapper cenTiposancionExtendsMapper;

	@Mock
	private CenSancionExtendsMapper cenSancionExtendsMapper;

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@InjectMocks
	private BusquedaSancionesServiceImpl busquedaSancionesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

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

	@Test
	public void searchBusquedaSancionesTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		BusquedaSancionesSearchDTO busquedaSancionesSearchDTO = cenTestUtils.getBusquedaSancionesSearchDTOSimulado();
		List<BusquedaSancionesItem> busquedaSancionesItems = cenTestUtils.getListBusquedaSancionesItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposancionExtendsMapper.searchBusquedaSanciones(busquedaSancionesSearchDTO, idLenguaje, idInstitucion)).thenReturn(busquedaSancionesItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		BusquedaSancionesDTO busquedaSancionesDTOResultado = busquedaSancionesServiceImpl.searchBusquedaSanciones(1, busquedaSancionesSearchDTO, mockreq);
		
		BusquedaSancionesDTO busquedaSancionesDTOEsperado = new BusquedaSancionesDTO();
		busquedaSancionesDTOEsperado.setBusquedaSancionesItem(busquedaSancionesItems);
		
		assertThat(busquedaSancionesDTOResultado.toString()).isEqualTo(busquedaSancionesDTOEsperado.toString());

	}

	@Test
	public void insertSanctionTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		
		String idPersona = "2005001213";
		String idInstitucion = "2000";
				
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		BusquedaSancionesItem busquedaSancionesItem = cenTestUtils.getBusquedaSancionesItemSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSancionExtendsMapper.getMaxIdSancion(idPersona, idInstitucion)).thenReturn(newIdDTO);
				
		when(cenSancionExtendsMapper.insertSelective(Mockito.any(CenSancion.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = busquedaSancionesServiceImpl.insertSanction(busquedaSancionesItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}
	
	@Test
	public void insertSanctionErrorTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		
		String idPersona = "2005001213";
		String idInstitucion = "2000";
				
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		BusquedaSancionesItem busquedaSancionesItem = cenTestUtils.getBusquedaSancionesItemSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSancionExtendsMapper.getMaxIdSancion(idPersona, idInstitucion)).thenReturn(newIdDTO);
				
		when(cenSancionExtendsMapper.insertSelective(Mockito.any(CenSancion.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = busquedaSancionesServiceImpl.insertSanction(busquedaSancionesItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}
	
	@Test
	public void updateSanctionTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		
		CenSancion cenSancion = cenTestUtils.getCenSancionSimulado();
		
		BusquedaSancionesItem busquedaSancionesItem = cenTestUtils.getBusquedaSancionesItemSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
				
		when(cenSancionExtendsMapper.selectByPrimaryKey(Mockito.any(CenSancionKey.class))).thenReturn(cenSancion);

		when(cenSancionExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSancion.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = busquedaSancionesServiceImpl.updateSanction(busquedaSancionesItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void updateSanctionErrorTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		
		CenSancion cenSancion = cenTestUtils.getCenSancionSimulado();
		
		BusquedaSancionesItem busquedaSancionesItem = cenTestUtils.getBusquedaSancionesItemSimulado();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
				
		when(cenSancionExtendsMapper.selectByPrimaryKey(Mockito.any(CenSancionKey.class))).thenReturn(cenSancion);

		when(cenSancionExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSancion.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = busquedaSancionesServiceImpl.updateSanction(busquedaSancionesItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
}
