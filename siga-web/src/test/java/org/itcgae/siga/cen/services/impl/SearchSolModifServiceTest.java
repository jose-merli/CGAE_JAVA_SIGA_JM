package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.CenTestUtils;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenSolicitudesmodificacion;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudesmodificacionExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchSolModifServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenSolicitudesmodificacionExtendsMapper cenSolicitudesModificacionExtendsMapper;

	@InjectMocks
	private SearchSolModifServiceImpl searchSolModifServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void searchSolModifTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils
				.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolicitudesModificacionExtendsMapper.searchSolModif(solicitudModificacionSearchDTO, idLenguaje,
				idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifServiceImpl.searchSolModif(1,
				solicitudModificacionSearchDTO, mockreq);

		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();

		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);

	}

	@Test
	public void processSolModifTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitudesModificacionExtendsMapper
				.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifServiceImpl.processSolModif(solModificacionItem,
				mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}

	@Test
	public void processSolModifKOTest() throws Exception {
		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitudesModificacionExtendsMapper
				.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifServiceImpl.processSolModif(solModificacionItem,
				mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}

	@Test
	public void denySolModifTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitudesModificacionExtendsMapper
				.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifServiceImpl.denySolModif(solModificacionItem,
				mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitudesModificacionExtendsMapper
				.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifServiceImpl.denySolModif(solModificacionItem,
				mockreq);

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
}
