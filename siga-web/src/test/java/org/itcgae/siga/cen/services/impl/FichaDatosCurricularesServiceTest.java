package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvExample;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudmodificacioncvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo1ExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposCVSubtipo2ExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaDatosCurricularesServiceTest {

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Mock
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Mock
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Mock
	private CenSolicitudmodificacioncvExtendsMapper cenSolicitudmodificacioncvExtendsMapper;
	
	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;

	@Mock
	private CenDatoscvExtendsMapper cenDatoscvExtendsMapper;

	@Mock
	private CenTiposCVSubtipo1ExtendsMapper cenTiposCVSubtipo1ExtendsMapper;

	@Mock
	private CenTiposCVSubtipo2ExtendsMapper cenTiposCVSubtipo2ExtendsMapper;

	
	@InjectMocks
	private FichaDatosCurricularesServiceImpl fichaDatosCurricularesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void searchDatosCurricularesTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();
		FichaDatosCurricularesSearchDTO busqueda = new FichaDatosCurricularesSearchDTO();
		
		FichaDatosCurricularesDTO responseEsperado = new FichaDatosCurricularesDTO();
		responseEsperado.setFichaDatosCurricularesItem(fichaDatosCurricularesItem);
		//		Mockeo de las llamadas a BD
//		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString(), Mockito.anyBoolean() , Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		
		//	 	Ejecución del método a testear
		FichaDatosCurricularesDTO response = fichaDatosCurricularesServiceImpl.searchDatosCurriculares(busqueda, mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	

	@Test
	public void deleteDatosCurricularesTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = testUtils.getFichaDatosCurricularesDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");

		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		//when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString(), Mockito.anyBoolean() , Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		
		when(cenDatoscvExtendsMapper.updateCurriculo(Mockito.any(CenDatoscv.class))).thenReturn(1);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.deleteDatosCurriculares(fichaDatosCurricularesDTO, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void deleteDatosCurricularesErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = testUtils.getListaFichaDatosCurricularesItem();
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = testUtils.getFichaDatosCurricularesDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString(), Mockito.anyBoolean() , Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		
		when(cenDatoscvExtendsMapper.updateCurriculo(Mockito.any(CenDatoscv.class))).thenReturn(0);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.deleteDatosCurriculares(fichaDatosCurricularesDTO, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}

	@Test
	public void deleteDatosCurriculareNoUserTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = testUtils.getFichaDatosCurricularesDTO();

		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.deleteDatosCurriculares(fichaDatosCurricularesDTO, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void updateDatosCurricularesTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = testUtils.getListaFichaDatosCurricularesItem();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<CenDatoscv> datosCurricularesActivos = cenTestUtils.getListCenDatoscv();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString() , Mockito.anyBoolean(), Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		
		when(cenDatoscvExtendsMapper.updateCurriculo(Mockito.any(CenDatoscv.class))).thenReturn(1);

		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(datosCurricularesActivos);
		
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);


		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.updateDatosCurriculares(fichaCurricularesItem, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
	@Test
	public void updateDatosCurricularesErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = testUtils.getListaFichaDatosCurricularesItem();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<CenDatoscv> datosCurricularesActivos = cenTestUtils.getListCenDatoscv();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString(), Mockito.anyBoolean() , Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		when(cenDatoscvExtendsMapper.updateCurriculo(Mockito.any(CenDatoscv.class))).thenReturn(0);
		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(datosCurricularesActivos);
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.updateDatosCurriculares(fichaCurricularesItem, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void updateDatosCurricularesNoUserTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.updateDatosCurriculares(fichaCurricularesItem, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void insertDatosCurricularesTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = testUtils.getListaFichaDatosCurricularesItem();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<CenDatoscv> datosCurricularesActivos = cenTestUtils.getListCenDatoscv();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString() , Mockito.anyBoolean(), Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		when(cenDatoscvExtendsMapper.insertSelective(Mockito.any(CenDatoscv.class))).thenReturn(1);
		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(datosCurricularesActivos);
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);

		//	 	Ejecución del método a testear
		InsertResponseDTO response = fichaDatosCurricularesServiceImpl.insertDatosCurriculares(fichaCurricularesItem, mockreq);
		
		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void insertDatosCurricularesErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<CenTiposcvsubtipo1> cenSubtipo = cenTestUtils.getListCenTiposcvsubtipo1Simulado();
		List<CenTiposcvsubtipo2> cenSubtipo2 = cenTestUtils.getListCenTiposcvsubtipo2Simulado();
		NewIdDTO idCvBD = cenTestUtils.getNewIdDTOSimulado();
			
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposCVSubtipo1ExtendsMapper.selectByExample(Mockito.any(CenTiposcvsubtipo1Example.class))).thenReturn(cenSubtipo);
		when(cenTiposCVSubtipo1ExtendsMapper.selectByExample(Mockito.any(CenTiposcvsubtipo1Example.class))).thenReturn(cenSubtipo);
		when(cenTiposCVSubtipo2ExtendsMapper.selectByExample(Mockito.any(CenTiposcvsubtipo2Example.class))).thenReturn(cenSubtipo2);
		when(cenTiposCVSubtipo2ExtendsMapper.selectByExample(Mockito.any(CenTiposcvsubtipo2Example.class))).thenReturn(cenSubtipo2);
		when(cenDatoscvExtendsMapper.getMaxIdCv(Mockito.anyString(), Mockito.anyString())).thenReturn(idCvBD);
		when(cenDatoscvExtendsMapper.insertSelective(Mockito.any(CenDatoscv.class))).thenReturn(0);

//		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString() , Mockito.anyBoolean(), Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
//		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(datosCurricularesActivos);
//		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);

		//	 	Ejecución del método a testear
		InsertResponseDTO response = fichaDatosCurricularesServiceImpl.insertDatosCurriculares(fichaCurricularesItem, mockreq);
		
		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void insertDatosCurricularesNoUserTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);
		
		//	 	Ejecución del método a testear
		InsertResponseDTO response = fichaDatosCurricularesServiceImpl.insertDatosCurriculares(fichaCurricularesItem, mockreq);
		
		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
//	aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	@Test
	public void solicitudUpdateDatosCurricularesTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = testUtils.getListaFichaDatosCurricularesItem();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<CenDatoscv> datosCurricularesActivos = cenTestUtils.getListCenDatoscv();
		NewIdDTO idSolicitudBD = new NewIdDTO();
		idSolicitudBD.setNewId("1");
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString() , Mockito.anyBoolean(), Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		when(cenSolicitudmodificacioncvExtendsMapper.solicitudUpdateCurriculo(Mockito.any(CenSolicitudmodificacioncv.class))).thenReturn(1);
		when(cenSolicitudmodificacioncvExtendsMapper.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);
		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(datosCurricularesActivos);
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.solicitudUpdateDatosCurriculares(fichaCurricularesItem, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void solicitudUpdateDatosCurricularesErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = testUtils.getListaFichaDatosCurricularesItem();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<CenDatoscv> datosCurricularesActivos = cenTestUtils.getListCenDatoscv();
		NewIdDTO idSolicitudBD = null;
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDatoscvExtendsMapper.searchDatosCurriculares(Mockito.anyString() , Mockito.anyBoolean(), Mockito.anyString())).thenReturn(fichaDatosCurricularesItem);
		when(cenSolicitudmodificacioncvExtendsMapper.solicitudUpdateCurriculo(Mockito.any(CenSolicitudmodificacioncv.class))).thenReturn(0);
		when(cenSolicitudmodificacioncvExtendsMapper.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);
		when(cenDatoscvExtendsMapper.selectByExample(Mockito.any(CenDatoscvExample.class))).thenReturn(datosCurricularesActivos);
		when(cenDatoscvExtendsMapper.updateByPrimaryKey(Mockito.any(CenDatoscv.class))).thenReturn(1);


		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.solicitudUpdateDatosCurriculares(fichaCurricularesItem, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void solicitudUpdateDatosCurricularesNoUserTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		FichaDatosCurricularesItem fichaCurricularesItem = testUtils.getFichaDatosCurricularesItem();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);

		//	 	Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosCurricularesServiceImpl.solicitudUpdateDatosCurriculares(fichaCurricularesItem, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
}


















