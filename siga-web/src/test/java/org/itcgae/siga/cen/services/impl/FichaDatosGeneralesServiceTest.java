package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoExample;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscolegialesestadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodifexportarfotoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaDatosGeneralesServiceTest {

	@Mock
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Mock
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Mock
	private CenColegiadoExtendsMapper cenColegiadoMapper;

	@Mock
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	
	@Mock
	private GenPropertiesMapper genPropertiesMapper;
	
	@Mock
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;

	@Mock
	private  CenSolicitmodifdatosbasicosExtendsMapper  cenSolicitmodifdatosbasicosMapper;

	@Mock
	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoMapper;

	@Mock
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;

	@Mock
	private CenSolicmodifexportarfotoExtendsMapper cenSolicmodifexportarfoto;

	@Mock
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenClienteMapper cenClienteMapper;

	@Mock
	private CenTratamientoExtendsMapper cenFichaDatosGeneralesExtendsMapper;

	@Mock
	private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;

	@Mock
	private CenGruposclienteMapper cenGruposclienteMapper;

	@InjectMocks
	private FichaDatosGeneralesServiceImpl fichaDatosGeneralesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void updateColegiadoTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CenPersona persona = cenTestUtils.getCenPersona((long) 1223, "20092000V");
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(true, "1");
		List<ComboEtiquetasItem> gruposPersona = cenTestUtils.getListaEtiquetasSimuladas("1");
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		CenCliente cliente = cenTestUtils.getCenCliente();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = cenTestUtils.getDatosColegialesList();
		
		//		Mockeo de las llamadas a BD
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(persona); 
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(gruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.updateByExampleSelective(Mockito.any(CenPersona.class), Mockito.any(CenPersonaExample.class))).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(cenClienteMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cliente); 
		when(cenNocolegiadoMapper.updateByExampleSelective(Mockito.any(CenNocolegiado.class), Mockito.any(CenNocolegiadoExample.class))).thenReturn(1); 
		when(cenColegiadoMapper.updateByExampleSelective(Mockito.any(CenColegiado.class), Mockito.any(CenColegiadoExample.class))).thenReturn(1); 
		when(cenDatoscolegialesestadoMapper.selectByExample(Mockito.any(CenDatoscolegialesestadoExample.class))).thenReturn(cenDatoscolegialesestado); 
		when(cenDatoscolegialesestadoMapper.insertSelective(Mockito.any(CenDatoscolegialesestado.class))).thenReturn(1); 

//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);

		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void updateColegiadoFallosUpdateTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CenPersona persona = cenTestUtils.getCenPersona((long) 1223, "20092000V");
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(true, "1");
		List<ComboEtiquetasItem> gruposPersona = cenTestUtils.getListaEtiquetasSimuladas("2");
		List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		CenCliente cliente = cenTestUtils.getCenCliente();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = cenTestUtils.getDatosColegialesList();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(persona); 
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(gruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(cenPersonaExtendsMapper.updateByExampleSelective(Mockito.any(CenPersona.class), Mockito.any(CenPersonaExample.class))).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(Mockito.any(ComboEtiquetasItem.class),Mockito.anyString(),Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(cenClienteMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cliente); 
		when(cenNocolegiadoMapper.updateByExampleSelective(Mockito.any(CenNocolegiado.class), Mockito.any(CenNocolegiadoExample.class))).thenReturn(1); 
		when(cenColegiadoMapper.updateByExampleSelective(Mockito.any(CenColegiado.class), Mockito.any(CenColegiadoExample.class))).thenReturn(1); 
		when(cenDatoscolegialesestadoMapper.selectByExample(Mockito.any(CenDatoscolegialesestadoExample.class))).thenReturn(cenDatoscolegialesestado); 
		when(cenDatoscolegialesestadoMapper.insertSelective(Mockito.any(CenDatoscolegialesestado.class))).thenReturn(1); 

//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);

		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
	@Test
	public void updateColegiadoNoColegiadoYUsuarioLetradoTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CenPersona persona = cenTestUtils.getCenPersona((long) 1223, "20092000V");
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "1");
		List<ComboEtiquetasItem> gruposPersona = cenTestUtils.getListaEtiquetasSimuladas("1");
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		CenCliente cliente = cenTestUtils.getCenCliente();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = cenTestUtils.getDatosColegialesList();
		
		//		Mockeo de las llamadas a BD
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(persona); 
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(gruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.updateByExampleSelective(Mockito.any(CenPersona.class), Mockito.any(CenPersonaExample.class))).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(cenClienteMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cliente); 
		when(cenNocolegiadoMapper.updateByExampleSelective(Mockito.any(CenNocolegiado.class), Mockito.any(CenNocolegiadoExample.class))).thenReturn(1); 
		when(cenColegiadoMapper.updateByExampleSelective(Mockito.any(CenColegiado.class), Mockito.any(CenColegiadoExample.class))).thenReturn(1); 
		when(cenDatoscolegialesestadoMapper.selectByExample(Mockito.any(CenDatoscolegialesestadoExample.class))).thenReturn(cenDatoscolegialesestado); 
		when(cenDatoscolegialesestadoMapper.insertSelective(Mockito.any(CenDatoscolegialesestado.class))).thenReturn(1); 

		//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void updateColegiadoNoUsuarioTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "2");
		
		//		Mockeo de las llamadas a BD
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);

		//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);

		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void updateColegiadoEtiquetasAntiguasTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CenPersona persona = cenTestUtils.getCenPersona((long) 1223, "20092000V");
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "1");
		List<ComboEtiquetasItem> gruposPersona = cenTestUtils.getListaEtiquetasSimuladas("2");
		List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		CenCliente cliente = cenTestUtils.getCenCliente();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = cenTestUtils.getDatosColegialesList();
		
		//		Mockeo de las llamadas a BD
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(persona); 
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(gruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenPersonaExtendsMapper.updateByExampleSelective(Mockito.any(CenPersona.class), Mockito.any(CenPersonaExample.class))).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(cenClienteMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cliente); 
		when(cenNocolegiadoMapper.updateByExampleSelective(Mockito.any(CenNocolegiado.class), Mockito.any(CenNocolegiadoExample.class))).thenReturn(1); 
		when(cenColegiadoMapper.updateByExampleSelective(Mockito.any(CenColegiado.class), Mockito.any(CenColegiadoExample.class))).thenReturn(1); 
		when(cenDatoscolegialesestadoMapper.selectByExample(Mockito.any(CenDatoscolegialesestadoExample.class))).thenReturn(cenDatoscolegialesestado); 
		when(cenDatoscolegialesestadoMapper.insertSelective(Mockito.any(CenDatoscolegialesestado.class))).thenReturn(1); 

		//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void updateColegiadoUpdateFallidoTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CenPersona persona = cenTestUtils.getCenPersona((long) 1223, "20092000V");
		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "1");
		List<ComboEtiquetasItem> gruposPersona = cenTestUtils.getListaEtiquetasSimuladas("2");
		List<CenGruposclienteCliente> listarelacionGrupoPersona =  cenTestUtils.getListaGruposSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		CenCliente cliente = cenTestUtils.getCenCliente();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = cenTestUtils.getDatosColegialesList();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(persona); 
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(gruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(Mockito.any(ComboEtiquetasItem.class),Mockito.anyString(),Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(cenPersonaExtendsMapper.updateByExampleSelective(Mockito.any(CenPersona.class), Mockito.any(CenPersonaExample.class))).thenReturn(0); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(cenClienteMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cliente); 
		when(cenNocolegiadoMapper.updateByExampleSelective(Mockito.any(CenNocolegiado.class), Mockito.any(CenNocolegiadoExample.class))).thenReturn(0); 
		when(cenColegiadoMapper.updateByExampleSelective(Mockito.any(CenColegiado.class), Mockito.any(CenColegiadoExample.class))).thenReturn(0); 
		when(cenDatoscolegialesestadoMapper.selectByExample(Mockito.any(CenDatoscolegialesestadoExample.class))).thenReturn(cenDatoscolegialesestado); 
		when(cenDatoscolegialesestadoMapper.insertSelective(Mockito.any(CenDatoscolegialesestado.class))).thenReturn(0); 

		//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);
		
		UpdateResponseDTO responseEsperado = new UpdateResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void getLabelPersonTest1() throws Exception {
		//		Primero de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "2");
		List<ComboEtiquetasItem>comboEtiquetasItems = cenTestUtils.getComboEtiquetasSimulados("01/02/2018","02/02/2018","#87CEFA");
		ComboEtiquetasDTO responseEsperado = new ComboEtiquetasDTO();

		//		Mockeo de las llamadas a BD
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(comboEtiquetasItems);

		//		Ejecución del método a testear
		ComboEtiquetasDTO response = fichaDatosGeneralesServiceImpl.getLabelPerson(colegiado, mockreq);
		responseEsperado.setComboEtiquetasItems(comboEtiquetasItems);
		
		//		Se compran los items dentro de los response porque al tener id's distintos no los consideraba iguales aunque tuvieran el mismo contenido
		assertThat(response.getComboEtiquetasItems()).isEqualTo(responseEsperado.getComboEtiquetasItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void getLabelPersonTest2() throws Exception {
		//		Segundo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "2");
		List<ComboEtiquetasItem>comboEtiquetasItems = cenTestUtils.getComboEtiquetasSimulados("01/02/2018","02/02/2019","#40E0D0");
		ComboEtiquetasDTO responseEsperado = new ComboEtiquetasDTO();

		//		Mockeo de las llamadas a BD
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(comboEtiquetasItems);

		//		Ejecución del método a testear
		ComboEtiquetasDTO response = fichaDatosGeneralesServiceImpl.getLabelPerson(colegiado, mockreq);
		responseEsperado.setComboEtiquetasItems(comboEtiquetasItems);
		
		//		Se compran los items dentro de los response porque al tener id's distintos no los consideraba iguales aunque tuvieran el mismo contenido
		assertThat(response.getComboEtiquetasItems()).isEqualTo(responseEsperado.getComboEtiquetasItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void getLabelPersonTest3() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "2");
		List<ComboEtiquetasItem>comboEtiquetasItems = cenTestUtils.getComboEtiquetasSimulados("01/02/2018","02/02/2018","#F08080");
		ComboEtiquetasDTO responseEsperado = new ComboEtiquetasDTO();

		//		Mockeo de las llamadas a BD
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(comboEtiquetasItems);

		//		Ejecución del método a testear
		ComboEtiquetasDTO response = fichaDatosGeneralesServiceImpl.getLabelPerson(colegiado, mockreq);
		responseEsperado.setComboEtiquetasItems(comboEtiquetasItems);
		
		//		Se compran los items dentro de los response porque al tener id's distintos no los consideraba iguales aunque tuvieran el mismo contenido
		assertThat(response.getComboEtiquetasItems()).isEqualTo(responseEsperado.getComboEtiquetasItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void partidoJudicialSearchTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "2");
		DatosDireccionesDTO responseEsperado = new DatosDireccionesDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDireccionesExtendsMapper.selectPartidoJudicial(Mockito.anyString(), Mockito.anyString())).thenReturn(datosDireccionesItem);
		
		//		Ejecución del método a testear
		DatosDireccionesDTO response = fichaDatosGeneralesServiceImpl.partidoJudicialSearch(colegiado, mockreq);
		
		//		Se compran los items dentro de los response porque al tener id's distintos no los consideraba iguales aunque tuvieran el mismo contenido
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void partidoJudicialSearchSinUserTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoItem colegiado = cenTestUtils.getColegiadoItem(false, "2");
		DatosDireccionesDTO responseEsperado = new DatosDireccionesDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		usuarios.clear();
		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenDireccionesExtendsMapper.selectPartidoJudicial(Mockito.anyString(), Mockito.anyString())).thenReturn(datosDireccionesItem);
		
		//		Ejecución del método a testear
		DatosDireccionesDTO response = fichaDatosGeneralesServiceImpl.partidoJudicialSearch(colegiado, mockreq);
	
		//		Se compran los items dentro de los response porque al tener id's distintos no los consideraba iguales aunque tuvieran el mismo contenido
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void solicitudUploadPhotographyTest() throws Exception {
		MockMultipartHttpServletRequest mockreq = testUtils.getMultipartRequestWithGeneralAuthentication();

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		List<GenProperties> properties = cenTestUtils.getListGenPropertiesSimulados();
		List<CenPersona> cenPersonas = cenTestUtils.getListaPersonasSimuladas(Long.parseLong("1"), "1");
		NewIdDTO idSolicitudBD = new NewIdDTO();
		idSolicitudBD.setNewId("1");

		//		Mockeo de las llamadas a BD
		when(genPropertiesMapper.selectByExample(Mockito.any(GenPropertiesExample.class))).thenReturn(properties);
		
		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(cenPersonas);
		
		when(cenSolicmodifexportarfoto.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);

		when(cenSolicmodifexportarfoto.insert(Mockito.any(CenSolicmodifexportarfoto.class))).thenReturn(1);

		responseEsperado.setStatus(SigaConstants.OK);
		//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudUploadPhotography(mockreq);
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}

	@Test
	public void solicitudUploadPhotographyIdCambiadoTest() throws Exception {
		MockMultipartHttpServletRequest mockreq = testUtils.getMultipartRequestWithGeneralAuthentication();

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		List<GenProperties> properties = cenTestUtils.getListGenPropertiesSimulados();
		List<CenPersona> cenPersonas = cenTestUtils.getListaPersonasSimuladas(Long.parseLong("1"), "1");
		NewIdDTO idSolicitudBD = null;
		
		//		Mockeo de las llamadas a BD
		
		when(genPropertiesMapper.selectByExample(Mockito.any(GenPropertiesExample.class))).thenReturn(properties);
		
		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(cenPersonas);
		
		when(cenSolicmodifexportarfoto.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);
		when(cenSolicmodifexportarfoto.insert(Mockito.any(CenSolicmodifexportarfoto.class))).thenReturn(0);
		responseEsperado.setStatus(SigaConstants.KO);

		//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudUploadPhotography(mockreq);
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	

	@Test
	public void solicitudUploadPhotographyNoPersonaTest() throws Exception {
		MockMultipartHttpServletRequest mockreq = testUtils.getMultipartRequestWithGeneralAuthentication();

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		List<GenProperties> properties = cenTestUtils.getListGenPropertiesSimulados();
		NewIdDTO idSolicitudBD = null;
		
		//		Mockeo de las llamadas a BD
		
		when(genPropertiesMapper.selectByExample(Mockito.any(GenPropertiesExample.class))).thenReturn(properties);
		
		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(null);
		
		when(cenSolicmodifexportarfoto.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);
		when(cenSolicmodifexportarfoto.insert(Mockito.any(CenSolicmodifexportarfoto.class))).thenReturn(0);
		responseEsperado.setStatus(SigaConstants.KO);

		//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudUploadPhotography(mockreq);
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
	
	@Test
	public void solicitudUploadPhotographyErrorTest() throws Exception {
		MockMultipartHttpServletRequest mockreq = testUtils.getMultipartRequestWithGeneralAuthentication();

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		List<GenProperties> properties = null;
		List<CenPersona> cenPersonas = cenTestUtils.getListaPersonasSimuladas(Long.parseLong("1"), "1");
		
		//		Mockeo de las llamadas a BD
		
		when(genPropertiesMapper.selectByExample(Mockito.any(GenPropertiesExample.class))).thenReturn(properties);
		
		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(cenPersonas);
		
//		when(cenSolicmodifexportarfoto.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);
//		when(cenSolicmodifexportarfoto.insert(Mockito.any(CenSolicmodifexportarfoto.class))).thenReturn(0);
		responseEsperado.setStatus(SigaConstants.KO);

		//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudUploadPhotography(mockreq);
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
	
	@Test
	public void solicitudModificacionTest() throws Exception {
			MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

			NoColegiadoItem nocolegiado = cenTestUtils.getNoColegiadoItem("1");
			List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
			NewIdDTO idSolicitudBD = new NewIdDTO();
			idSolicitudBD.setNewId("1");
			
			//		Mockeo de las llamadas a BD
			when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

			when(cenSolicitmodifdatosbasicosMapper.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);

			when(cenSolicitmodifdatosbasicosMapper.insert(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(1); 
			//		Ejecución del método a testear
			InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudModificacion(nocolegiado, mockreq);
			
			InsertResponseDTO responseEsperado = new InsertResponseDTO();
			responseEsperado.setStatus(SigaConstants.OK);
			
			assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
		}
	
	
	@Test
	public void solicitudModificacionIdCambiadoTest() throws Exception {
			MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

			NoColegiadoItem nocolegiado = cenTestUtils.getNoColegiadoItem("1");
			List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
			NewIdDTO idSolicitudBD = null;
			
			//		Mockeo de las llamadas a BD
			when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

			when(cenSolicitmodifdatosbasicosMapper.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);

			when(cenSolicitmodifdatosbasicosMapper.insert(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(1); 
			//		Ejecución del método a testear
			InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudModificacion(nocolegiado, mockreq);
			
			InsertResponseDTO responseEsperado = new InsertResponseDTO();
			responseEsperado.setStatus(SigaConstants.OK);
			
			assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
		}
	
	
	@Test
	public void solicitudModificacionNoUserTest() throws Exception {
			MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

			NoColegiadoItem nocolegiado = cenTestUtils.getNoColegiadoItem("1");
			NewIdDTO idSolicitudBD = null;
			
			//		Mockeo de las llamadas a BD
			when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);
			when(cenSolicitmodifdatosbasicosMapper.getMaxIdSolicitud(Mockito.anyString(), Mockito.anyString())).thenReturn(idSolicitudBD);
			when(cenSolicitmodifdatosbasicosMapper.insert(Mockito.any(CenSolicitmodifdatosbasicos.class))).thenReturn(1); 
			//		Ejecución del método a testear
			InsertResponseDTO response = fichaDatosGeneralesServiceImpl.solicitudModificacion(nocolegiado, mockreq);
			
			InsertResponseDTO responseEsperado = new InsertResponseDTO();
			responseEsperado.setStatus(SigaConstants.KO);
			
			assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
		}

	
	@Test
	public void getEstadoCivilTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO responseEsperado = new ComboDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		responseEsperado.setCombooItems(testUtils.getListComboItemsSimulados());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenEstadocivilExtendsMapper.distinctCivilStatus(Mockito.anyString())).thenReturn(combo);
		
		//		Ejecución del método a testear
		ComboDTO response = fichaDatosGeneralesServiceImpl.getEstadoCivil(mockreq);
		
		assertThat(response.getCombooItems()).isEqualTo(responseEsperado.getCombooItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void getTratamientoTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO responseEsperado = new ComboDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		responseEsperado.setCombooItems(testUtils.getListComboItemsSimulados());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenFichaDatosGeneralesExtendsMapper.selectTratamiento(Mockito.anyString())).thenReturn(combo);
		
		//		Ejecución del método a testear
		ComboDTO response = fichaDatosGeneralesServiceImpl.getTratamiento(mockreq);
		
		assertThat(response.getCombooItems()).isEqualTo(responseEsperado.getCombooItems()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
	
	@Test
	public void createNoColegiadoTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();

		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
	
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
	
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 

		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 

		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 

		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 

		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 

		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 

		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 

		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 

		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(1); 


//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	

	

	@Test
	public void createNoColegiadoSinGruposTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		listarelacionGrupoPersona.clear();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();

		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(0); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void createNoColegiadoSinGruposErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		listarelacionGrupoPersona.clear();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();

		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void createNoColegiadoSinGruposBusquedaErrTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		listarelacionGrupoPersona.clear();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		listaGruposPersona.clear();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void createNoColegiadoErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(0); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void createNoColegiadoCenGruposTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		listaGruposPersona.clear();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(1); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.OK);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void createNoColegiadoCenGruposCliTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		listaGruposPersona.clear();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(1); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void createNoColegiadoErrorUsuarioNoExisteTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		listaGruposPersona.clear();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);


//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	@Test
	public void createNoColegiadoErrorNifExisteTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		listaGruposPersona.clear();
		//		Mockeo de las llamadas a BD
		when(cenPersonaExtendsMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(null);


//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	

	@Test
	public void createNoColegiadoErrorInsertPersonaTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	@Test
	public void createNoColegiadoNoClienteTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();

		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	

	
	@Test
	public void createNoColegiadoCenGruposClienteErrorTest() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		NoColegiadoItem noColegiado = cenTestUtils.getNoColegiadoItem("1");
		List<ComboItem> combo = testUtils.getListComboItemsSimulados();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = cenTestUtils.getListaGruposSimulados();
		List<CenGruposcliente> listaGruposPersona = cenTestUtils.getListaGrupCliSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		listaGruposPersona.clear();
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenPersonaExtendsMapper.insertSelectiveForPerson(Mockito.any(CenPersona.class), Mockito.any(AdmUsuarios.class))).thenReturn(1); 
		when(cenPersonaExtendsMapper.selectMaxIdPersona()).thenReturn(combo); 
		when(cenClienteMapper.insert(Mockito.any(CenCliente.class))).thenReturn(1); 
		when(cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.any(EtiquetaUpdateDTO.class))).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.selectDistinctGruposClientes(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(listaGruposPersona); 
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.any(ComboEtiquetasItem.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0); 
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
		when(genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
		when(cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(Mockito.anyString(), Mockito.any(AdmUsuarios.class))).thenReturn(0); 

//		Ejecución del método a testear
		InsertResponseDTO response = fichaDatosGeneralesServiceImpl.createNoColegiado(noColegiado, mockreq);

		InsertResponseDTO responseEsperado = new InsertResponseDTO();
		responseEsperado.setStatus(SigaConstants.KO);
		
		assertThat(response.getStatus()).isEqualTo(responseEsperado.getStatus()); //Comparar resultado esperado con resultado que trae el TEST
	}
	
	
	
}
