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
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.CenSolimodidireccionesExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolimodidireccionesExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchSolModifDatosDireccionesServiceTest {
	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenSolimodidireccionesExtendsMapper cenSoliModiDireccionesExtendsMapper;
	
	@Mock
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@InjectMocks
	private SearchSolModifDatosDireccionesServiceImpl searchSolModifDatosDireccionesServiceImpl;
	
    private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void searchSolModifDatosDireccionesTest() throws Exception{
		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSoliModiDireccionesExtendsMapper.searchSolModifDatosDirecciones(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifDatosDireccionesServiceImpl.searchSolModifDatosDirecciones(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);

	}

	@Test
	public void processSolModifDatosDireccionesTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolimodidirecciones> lista = cenTestUtils.getListCenSolimodidireccionesSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSoliModiDireccionesExtendsMapper.selectByExample(Mockito.any(CenSolimodidireccionesExample.class))).thenReturn(lista);

		when(cenDireccionesExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenDirecciones.class))).thenReturn(1);
		
		when(cenSoliModiDireccionesExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolimodidirecciones.class))).thenReturn(1);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosDireccionesServiceImpl.processSolModifDatosDirecciones(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void processSolModifDatosDireccionesKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolimodidirecciones> lista = cenTestUtils.getListCenSolimodidireccionesSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSoliModiDireccionesExtendsMapper.selectByExample(Mockito.any(CenSolimodidireccionesExample.class))).thenReturn(lista);

		when(cenDireccionesExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenDirecciones.class))).thenReturn(0);
		
		when(cenSoliModiDireccionesExtendsMapper.updateByPrimaryKey(Mockito.any(CenSolimodidirecciones.class))).thenReturn(0);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosDireccionesServiceImpl.processSolModifDatosDirecciones(solModificacionItem, mockreq);
		
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}

	@Test
	public void denySolModifDatosDireccionesTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSoliModiDireccionesExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolimodidirecciones.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosDireccionesServiceImpl.denySolModifDatosDirecciones(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	
	@Test
	public void denySolModifDatosDireccionesKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSoliModiDireccionesExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolimodidirecciones.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosDireccionesServiceImpl.denySolModifDatosDirecciones(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}

}
