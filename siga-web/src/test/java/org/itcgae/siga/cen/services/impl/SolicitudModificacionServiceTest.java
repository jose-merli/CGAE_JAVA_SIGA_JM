package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitudesmodificacion;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadoSolicitudModifExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudesmodificacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposModificacionesExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SolicitudModificacionServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private CenTiposModificacionesExtendsMapper cenTiposModificacionesExtendsMapper;

	@Mock
	private CenEstadoSolicitudModifExtendsMapper cenEstadoSolicitudModifExtendsMapper;

	@Mock
	private CenSolicitudesmodificacionExtendsMapper  cenSolicitudesModificacionExtendsMapper;
	
	@Mock
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@InjectMocks
	private SolicitudModificacionServiceImpl solicitudModificacionServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();
	
	@Test
	public void getComboTipoModificacionTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposModificacionesExtendsMapper
				.getTipoModificacion(idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = solicitudModificacionServiceImpl.getComboTipoModificacion(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

	@Test
	public void getComboEstadoTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenEstadoSolicitudModifExtendsMapper.getEstado(idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = solicitudModificacionServiceImpl.getComboEstado(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

	@Test
	public void searchModificationRequestTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenTiposModificacionesExtendsMapper
				.searchModificationRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = solicitudModificacionServiceImpl.searchModificationRequest(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);

	}
	
	@Test
	public void processGeneralModificationRequestTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(1);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = solicitudModificacionServiceImpl.processGeneralModificationRequest(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	
	@Test
	public void processGeneralModificationRequestKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = solicitudModificacionServiceImpl.processGeneralModificationRequest(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denyGeneralModificationRequestTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(1);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = solicitudModificacionServiceImpl.denyGeneralModificationRequest(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void denyGeneralModificationRequestKOTest() throws Exception {

		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(cenSolicitudesModificacionExtendsMapper.updateByPrimaryKeySelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(0);
		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = solicitudModificacionServiceImpl.denyGeneralModificationRequest(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	@Test
	public void insertGeneralModificationRequestTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String idPersona = "2005001213";
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		CenCliente cenCliente = cenTestUtils.getCenClienteSimulado();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenPersona> lista = cenTestUtils.getListCenPersonaSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(lista);

		when(cenSolicitudesModificacionExtendsMapper.getMaxIdSolicitud(idInstitucion, idPersona)).thenReturn(newIdDTO);
		
		when(cenClienteExtendsMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cenCliente);
		
		when(cenClienteExtendsMapper.insertSelective(Mockito.any(CenCliente.class))).thenReturn(1);
		
		when(cenSolicitudesModificacionExtendsMapper.insertSelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(1);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = solicitudModificacionServiceImpl.insertGeneralModificationRequest(solModificacionItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.OK);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}
	
	@Test
	public void insertGeneralModificationRequestKOTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String idPersona = "2005001213";
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		CenCliente cenCliente = cenTestUtils.getCenClienteSimulado();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenPersona> lista = cenTestUtils.getListCenPersonaSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(lista);

		when(cenSolicitudesModificacionExtendsMapper.getMaxIdSolicitud(idInstitucion, idPersona)).thenReturn(newIdDTO);
		
		when(cenClienteExtendsMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cenCliente);
		
		when(cenClienteExtendsMapper.insertSelective(Mockito.any(CenCliente.class))).thenReturn(1);
		
		when(cenSolicitudesModificacionExtendsMapper.insertSelective(Mockito.any(CenSolicitudesmodificacion.class))).thenReturn(0);

		
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		InsertResponseDTO insertResponseDTOResultado = solicitudModificacionServiceImpl.insertGeneralModificationRequest(solModificacionItem, mockreq);
		
		InsertResponseDTO insertResponseDTOEsperado = new InsertResponseDTO();
		insertResponseDTOEsperado.setStatus(SigaConstants.KO);

		assertThat(insertResponseDTOResultado).isEqualTo(insertResponseDTOEsperado);
	}
}
