package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.CenTestUtils;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpSolicitudborrado;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.ExpSolicitudBorradoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchSolModifDatosExpedientesServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private ExpSolicitudBorradoExtendsMapper expSolicitudBorradoExtendsMapper;
	
	@InjectMocks
	private SearchSolModifDatosExpedientesServiceImpl searchSolModifDatosExpedientesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchSolModifDatosExpedientesTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(expSolicitudBorradoExtendsMapper.searchSolModifDatosExpedientes(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifDatosExpedientesServiceImpl.searchSolModifDatosExpedientes(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);
		
	}

	@Test
	public void processSolModifDatosExpedientesTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(expSolicitudBorradoExtendsMapper.updateByPrimaryKeySelective(Mockito.any(ExpSolicitudborrado.class))).thenReturn(1);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosExpedientesServiceImpl.processSolModifDatosExpedientes(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void processSolModifDatosExpedientesKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(expSolicitudBorradoExtendsMapper.updateByPrimaryKeySelective(Mockito.any(ExpSolicitudborrado.class))).thenReturn(0);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosExpedientesServiceImpl.processSolModifDatosExpedientes(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifDatosExpedientesTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(expSolicitudBorradoExtendsMapper.updateByPrimaryKeySelective(Mockito.any(ExpSolicitudborrado.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosExpedientesServiceImpl.denySolModifDatosExpedientes(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifDatosExpedientesKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(expSolicitudBorradoExtendsMapper.updateByPrimaryKeySelective(Mockito.any(ExpSolicitudborrado.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosExpedientesServiceImpl.denySolModifDatosExpedientes(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
}
