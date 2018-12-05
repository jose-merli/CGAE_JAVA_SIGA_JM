package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FichaOtrasColegiacionesServiceTest {


	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenTratamientoExtendsMapper cenFichaDatosGeneralesExtendsMapper;

	@Mock
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@InjectMocks
	private FichaColegialOtrasColegiacionesServiceImpl fichaColegialOtrasColegiacionesServiceImpl;
	
	private TestUtils testUtils = new TestUtils();

	@Test
	public void searchOtherColleguesTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();
		List<ColegiadoItem> colegiadoItems = new ArrayList<ColegiadoItem>();

		ColegiadoDTO responseEsperado = new ColegiadoDTO();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados("1");
		responseEsperado.setColegiadoItem(new ArrayList<ColegiadoItem>());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenColegiadoExtendsMapper.searchOtherCollegues(Mockito.anyString(), Mockito.anyString())).thenReturn(colegiadoItems);
		
		//	Ejecución del método a testear
		ColegiadoDTO response = fichaColegialOtrasColegiacionesServiceImpl.searchOtherCollegues(1, "", mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
		

	@Test
	public void searchOtherColleguesNoUserTest() throws Exception {
		//		Ultimo de tres para comprobar los 3 colores distintos posibles.
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ColegiadoDTO responseEsperado = new ColegiadoDTO();
		responseEsperado.setColegiadoItem(new ArrayList<ColegiadoItem>());
		//		Mockeo de las llamadas a BD
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(null);
		
		//	Ejecución del método a testear
		ColegiadoDTO response = fichaColegialOtrasColegiacionesServiceImpl.searchOtherCollegues(1, "", mockreq);
		
		assertThat(response).isEqualTo(responseEsperado); //Comparar resultado esperado con resultado que trae el TEST
	}
	
}
