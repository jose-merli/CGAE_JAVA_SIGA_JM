package org.itcgae.siga.form.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadocursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForVisibilidadcursoExtendsMapper;
import org.itcgae.siga.form.services.impl.BusquedaCursosServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusquedaCursoServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private ForEstadocursoExtendsMapper forEstadocursoExtendsMapper;

	@Mock
	private ForVisibilidadcursoExtendsMapper forVisibilidadcursoExtendsMapper;

	@Mock
	private ForTemacursoExtendsMapper forTemacursoExtendsMapper;

	@Mock
	private ForCursoExtendsMapper forCursoExtendsMapper;

	@Mock
	AdmUsuariosMapper admUsuariosMapper;

	@InjectMocks
	private BusquedaCursosServiceImpl busquedaCursosServiceImpl;

	private TestUtils testUtils = new TestUtils();

	@Test
	public void getVisibilidadCursosTest() throws Exception {

		String idLenguaje = "1";
		List<ComboItem> comboItemsSimulados = testUtils.getListComboItemsSimulados();
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);

		// Cuando se encuentre este método con cualquier objeto (anyObject()) dentro del
		// servicio "busquedaCursosServiceImpl", devolverá lo que le estamos poniendo en
		// el thenReturn
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(forVisibilidadcursoExtendsMapper.distinctVisibilidadCurso(idLenguaje)).thenReturn(comboItemsSimulados);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		ComboDTO comboResultado = busquedaCursosServiceImpl.getVisibilidadCursos(mockreq);
		
		ComboDTO comboEsperado = new ComboDTO();
		
		ComboItem comboItemVacio = testUtils.getComboItemVacio();
				
		comboItemsSimulados.add(0, comboItemVacio);
		comboEsperado.setCombooItems(comboItemsSimulados);
		
		assertThat(comboResultado).isEqualTo(comboEsperado);

	}

}
