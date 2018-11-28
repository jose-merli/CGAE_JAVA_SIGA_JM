package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.CenTestUtils;
import org.itcgae.siga.commons.utils.TestUtils;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolicmodicuentasExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodicuentasExtendsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchSolModifDatosBancariosServiceTest {

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Mock
	private CenSolicmodicuentasExtendsMapper cenSolicModiCuentasExtendsMapper;
	
	@Mock
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

	@InjectMocks
	private SearchSolModifDatosBancariosServiceImpl searchSolModifDatosBancariosServiceImpl;
	
	private TestUtils testUtils = new TestUtils();
	
	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void searchSolModifDatosBancariosTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		SolicitudModificacionSearchDTO solicitudModificacionSearchDTO = cenTestUtils.getSolicitudModificacionSearchDTOSimulado();
		List<SolModificacionItem> solModificacionItems = cenTestUtils.getListSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		when(cenSolicModiCuentasExtendsMapper.searchSolModifDatosBancarios(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)).thenReturn(solModificacionItems);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		SolModificacionDTO solModificacionDTOResultado = searchSolModifDatosBancariosServiceImpl.searchSolModifDatosBancarios(1, solicitudModificacionSearchDTO, mockreq);
		
		SolModificacionDTO solModificacionDTOEsperado = cenTestUtils.getSolModificacionDTOSimulado();
		
		assertThat(solModificacionDTOResultado).isEqualTo(solModificacionDTOEsperado);

	}

	@Test
	public void processSolModifDatosBancariosKOTest() throws Exception {

		String idLenguaje = "1";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenSolicmodicuentas> lista = cenTestUtils.getListCenSolicmodicuentasSimulado();
		SolModificacionItem solModificacionItem = cenTestUtils.getSolModificacionItemSimulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenSolicModiCuentasExtendsMapper.selectByExample(Mockito.any(CenSolicmodicuentasExample.class))).thenReturn(lista);

		when(cenSolicModiCuentasExtendsMapper.updateByPrimaryKey(Mockito.any(CenSolicmodicuentas.class))).thenReturn(0);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		UpdateResponseDTO updateResponseDTOResultado = searchSolModifDatosBancariosServiceImpl.processSolModifDatosBancarios(solModificacionItem, mockreq);
		

		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		Error error = new Error();
		error.setCode(400);
		error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
		updateResponseDTOEsperado.setError(error);

		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);

	}

}
