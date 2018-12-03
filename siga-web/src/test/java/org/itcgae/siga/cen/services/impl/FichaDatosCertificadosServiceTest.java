package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.CertificadoDTO;
import org.itcgae.siga.DTOs.cen.CertificadoItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatosCertificadosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaDatosCertificadosServiceTest {


	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenTratamientoExtendsMapper cenFichaDatosGeneralesExtendsMapper;

	@Mock
	private CenDatosCertificadosExtendsMapper cenDatosCertificadosExtendsMapper;

	@InjectMocks
	private FichaDatosCertificadosServiceImpl fichaDatosCertificadosServiceImpl;
	
	private TestUtils testUtils = new TestUtils();

	@Test
	public void datosCertificadosSearchTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<CertificadoItem> certificadoListItem = new ArrayList<CertificadoItem>();

		CertificadoDTO responseEsperado = new CertificadoDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		responseEsperado.setCertificadoItem(new ArrayList<CertificadoItem>());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenDatosCertificadosExtendsMapper.datosCertificadosSearch(Mockito.anyString())).thenReturn(certificadoListItem);
		
		//	Ejecución del método a testear
		CertificadoDTO response = fichaDatosCertificadosServiceImpl.datosCertificadosSearch(1, "", mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
		

	@Test
	public void datosCertificadosSearchNoUserTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CertificadoDTO responseEsperado = new CertificadoDTO();
		responseEsperado.setCertificadoItem(new ArrayList<CertificadoItem>());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);
		
		//	Ejecución del método a testear
		CertificadoDTO response = fichaDatosCertificadosServiceImpl.datosCertificadosSearch(1, "", mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
}
