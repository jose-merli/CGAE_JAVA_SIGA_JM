package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvExample;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncvExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchSolModifDatosCurricularesServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;

	@Mock
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;
	
	@InjectMocks
	private SearchSolModifDatosCurricularesServiceImpl searchSolModifDatosCurricularesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void searchSolModifDatosCurriculares() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolicitudmodificacioncvExtendsMapper.searchSolModifDatosCurriculares(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifDatosCurricularesServiceImpl.searchSolModifDatosCurriculares(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);
	}
	
	@Test
	public void processSolModifDatosCurricularesTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String idPersona = "2005001213";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolicitudmodificacioncv> lista = cenTestUtils.getListCenSolicitudmodificacioncvSimulado();
		List<CenDatoscv> cenDatoscvList = cenTestUtils.getListCenDatoscvSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		SolModifDatosCurricularesItem solModifDatosCurricularesItem = cenTestUtils.getSolModifDatosCurricularesItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitudmodificacioncvExtendsMapper.selectByExample(Mockito.any(CenSolicitudmodificacioncvExample.class))).thenReturn(lista);

		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(cenDatoscvList);
		
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);
		
		when(cenDatoscvExtendsMapper.updateCurriculo(Mockito.any(CenDatoscv.class))).thenReturn(1);
		
		when(cenDatoscvExtendsMapper.getMaxIdCv(idInstitucion, idPersona)).thenReturn(newIdDTO);
			
		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(cenDatoscvList);
		
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);

		when(cenDatoscvExtendsMapper.insertSelective(Mockito.any(CenDatoscv.class))).thenReturn(1);
		
		when(cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudmodificacioncv.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosCurricularesServiceImpl.processSolModifDatosCurriculares(solModifDatosCurricularesItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void processSolModifDatosCurricularesKOTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String idPersona = "2005001213";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolicitudmodificacioncv> lista = cenTestUtils.getListCenSolicitudmodificacioncvSimulado();
		List<CenDatoscv> cenDatoscvList = cenTestUtils.getListCenDatoscvSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		SolModifDatosCurricularesItem solModifDatosCurricularesItem = cenTestUtils.getSolModifDatosCurricularesItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitudmodificacioncvExtendsMapper.selectByExample(Mockito.any(CenSolicitudmodificacioncvExample.class))).thenReturn(lista);

		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(cenDatoscvList);
		
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(0);
		
		when(cenDatoscvExtendsMapper.updateCurriculo(Mockito.any(CenDatoscv.class))).thenReturn(0);
		
		when(cenDatoscvExtendsMapper.getMaxIdCv(idInstitucion, idPersona)).thenReturn(newIdDTO);
			
		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(cenDatoscvList);
		
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(0);

		when(cenDatoscvExtendsMapper.insertSelective(Mockito.any(CenDatoscv.class))).thenReturn(0);
		
		when(cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudmodificacioncv.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosCurricularesServiceImpl.processSolModifDatosCurriculares(solModifDatosCurricularesItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifDatosCurricularesTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudmodificacioncv.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosCurricularesServiceImpl.denySolModifDatosCurriculares(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denySolModifDatosCurricularesKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitudmodificacioncvExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudmodificacioncv.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosCurricularesServiceImpl.denySolModifDatosCurriculares(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
}
