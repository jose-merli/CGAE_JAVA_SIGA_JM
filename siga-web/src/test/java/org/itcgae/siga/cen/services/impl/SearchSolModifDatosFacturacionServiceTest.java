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
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolmodifacturacionservicio;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolModifFacturacionServicioExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchSolModifDatosFacturacionServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenSolModifFacturacionServicioExtendsMapper cenSolModifFacturacionServicioExtendsMapper;
	
	@InjectMocks
	private SearchSolModifDatosFacturacionServiceImpl searchSolModifDatosFacturacionServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchSolModifDatosFacturacionTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolModifFacturacionServicioExtendsMapper.searchSolModifDatosFacturacion(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifDatosFacturacionServiceImpl.searchSolModifDatosFacturacion(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);

	}

	@Test
	public void processSolModifDatosFacturacionTest() throws Exception {
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolmodifacturacionservicio.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosFacturacionServiceImpl.processSolModifDatosFacturacion(solModificacionItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void processSolModifDatosFacturacionKOTest() throws Exception {
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolmodifacturacionservicio.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosFacturacionServiceImpl.processSolModifDatosFacturacion(solModificacionItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifDatosFacturacionTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolmodifacturacionservicio.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosFacturacionServiceImpl.denySolModifDatosFacturacion(solModificacionItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifDatosFacturacionKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolModifFacturacionServicioExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolmodifacturacionservicio.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosFacturacionServiceImpl.denySolModifDatosFacturacion(solModificacionItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
}
