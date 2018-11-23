package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteMapper;
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
import org.itcgae.siga.db.services.cen.mappers.CenTiposancionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
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
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;

	@Mock
	private  CenSolicitmodifdatosbasicosExtendsMapper  cenSolicitmodifdatosbasicosMapper;

	@Mock
	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoMapper;

	@Mock
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;

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

	@Test
	public void updateColegiado() throws Exception {
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		CenPersona persona = testUtils.getCenPersona((long) 1223, "20092000V");
		List<CenPersona> listaPersonas = testUtils.getListaPersonasSimuladas((long) 1223, "20092000V");
		ColegiadoItem colegiado = testUtils.getColegiadoItem((long) 1223, "20092000V");
		List<ComboEtiquetasItem> gruposPersona = testUtils.getListaEtiquetasSimuladas();
		List<CenGruposclienteCliente> listarelacionGrupoPersona = testUtils.getListaGruposSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		CenCliente cliente = testUtils.getCenCliente();
//		Ejecución del método a testear
		UpdateResponseDTO response = fichaDatosGeneralesServiceImpl.updateColegiado(colegiado, mockreq);

		//		Mockeo de las llamadas a BD
		
				//	if (null != idInstitucion) { --126--
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
				// 		if (null != usuarios && usuarios.size() > 0) { --135--
		when(cenPersonaExtendsMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(persona); 
		when(cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(Mockito.anyString(), Mockito.anyString())).thenReturn(gruposPersona); 
				//	if(etiqueta.getIdGrupo() != "") {	--161--
				//	if(!gruposPerJuridicaAntiguos.contains(etiqueta.getIdGrupo())) {  --164--
		when(cenGruposclienteClienteExtendsMapper.selectByExample(Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(listarelacionGrupoPersona); 
				// if (listarelacionGrupoPersona.isEmpty()) {	--176-- (ELSE)
					// USAR cuando listarelacionGrupoPersona sea vacía.		when(cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(Mockito.any(ComboEtiquetasItem.class),Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1); 
					// if (response == 0) --187-- (ELSE)
		when(cenGruposclienteClienteExtendsMapper.updateByExample(Mockito.any(CenGruposclienteCliente.class), Mockito.any(CenGruposclienteClienteExample.class))).thenReturn(1); 
				// if (response == 0) --217-- (ELSE)
				// 	if (!updateResponseDTO.getStatus().equals(SigaConstants.KO)) { --242--
		when(cenPersonaExtendsMapper.updateByExampleSelective(Mockito.any(CenPersona.class), Mockito.any(CenPersonaExample.class))).thenReturn(1); 
				//	if (eliminadoGrupo == 0) { --290--
		when(cenClienteMapper.selectByPrimaryKey(Mockito.any(CenClienteKey.class))).thenReturn(cliente); 
				//  if(colegiadoItem.getColegiado() == false || colegiadoItem.getColegiado() == null) { (ELSE) --333--
		when(cenColegiadoMapper.updateByExampleSelective(Mockito.any(CenColegiado.class), Mockito.any(CenColegiadoExample.class))); 
				
		
		 
		
		
		
		ComboDTO comboEsperado = new ComboDTO();
		
		ComboItem comboItemVacio = testUtils.getComboItemVacio();
				
		comboItemsSimulados.add(0, comboItemVacio);
		comboEsperado.setCombooItems(comboItemsSimulados);
		
//		assertThat(comboResultado).isEqualTo(comboEsperado); Comparar resultado esperado con resultado que trae el TEST
	}
}
