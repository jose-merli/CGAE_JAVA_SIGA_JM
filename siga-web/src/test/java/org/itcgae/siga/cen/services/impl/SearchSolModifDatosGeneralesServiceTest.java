package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
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
public class SearchSolModifDatosGeneralesServiceTest {
	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenSolicitmodifdatosbasicosExtendsMapper cenSolicitModifDatosBasicosExtendsMapper;
	
	@Mock
	private CenClienteExtendsMapper cenClienteExtendsMapper;
	
	@InjectMocks
	private SearchSolModifDatosGeneralesServiceImpl searchSolModifDatosGeneralesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchSolModifDatosGeneralesTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolicitModifDatosBasicosExtendsMapper.searchSolModifDatosGenerales(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifDatosGeneralesServiceImpl.searchSolModifDatosGenerales(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);

	}

	@Test
	public void processSolModifDatosGeneralesTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolicitmodifdatosbasicos> lista = cenTestUtils.getListCenSolicitmodifdatosbasicosSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitModifDatosBasicosExtendsMapper.selectByExample(Mockito.any(CenSolicitmodifdatosbasicosExample.class))).thenReturn(lista);

		when(cenClienteExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenCliente.class))).thenReturn(1);
		
		when(cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosGeneralesServiceImpl.processSolModifDatosGenerales(solModificacionItem, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void processSolModifDatosGeneralesKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolicitmodifdatosbasicos> lista = cenTestUtils.getListCenSolicitmodifdatosbasicosSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitModifDatosBasicosExtendsMapper.selectByExample(Mockito.any(CenSolicitmodifdatosbasicosExample.class))).thenReturn(lista);

		when(cenClienteExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenCliente.class))).thenReturn(0);
		
		when(cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(0);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosGeneralesServiceImpl.processSolModifDatosGenerales(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}

	@Test
	public void denySolModifDatosGeneralesTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosGeneralesServiceImpl.denySolModifDatosGenerales(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}

	@Test
	public void denySolModifDatosGeneralesKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitModifDatosBasicosExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosGeneralesServiceImpl.denySolModifDatosGenerales(solModificacionItem, mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
}
