package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.db.mappers.CenCargamasivaMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteMapper;
import org.itcgae.siga.db.mappers.CenHistoricoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.SIGAServicesHelperMapper;
import org.itcgae.siga.exception.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CargasMasivasGFServiceTest {

	@Mock
	ICargasMasivasGFService cargasMasivasGFService;

	@Mock
	CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;

	@Mock
	CenGruposclienteMapper cenGruposclienteMapper;

	@Mock
	CenPersonaMapper cenPersonaMapper;

	@Mock
	CenClienteExtendsMapper cenClienteExtendsMapper;

	@Mock
	CenGruposclienteClienteMapper cenGruposclienteClienteMapper;

	@Mock
	CenHistoricoMapper cenHistoricoMapper;

	@Mock
	CenCargamasivaMapper cenCargamasivaMapper;

	@Mock
	IFicherosService ficherosService;

	@Mock
	GenPropertiesMapper genPropertiesMapper;

	@Mock
	GenRecursosMapper genRecursosMapper;

	@Mock
	SIGAServicesHelperMapper sigaServicesHelperMapper;

	@Mock
	CenHistoricoExtendsMapper cenHistoricoExtendsMapper;

	@InjectMocks
	private CargasMasivasGFServiceImpl cargasMasivasGFServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void createExcelFileTest() throws Exception {
		List<String> listString = cenTestUtils.getListaSimulada();
		Vector<Hashtable<String, Object>> datosVector = cenTestUtils.getDatosVectorSimulado();

		File response = cargasMasivasGFServiceImpl.createExcelFile(listString, datosVector);
		assertThat(response).isNotNull();
	}

	@Test
	public void generateExcelEtiquetasTest() {
		ResponseEntity<InputStreamResource> response = cargasMasivasGFServiceImpl.generateExcelEtiquetas();
		assertThat(response).isNotNull();
	}

	@Test
	public void searchEtiquetasTest() throws Exception {
		Short idInstitucion = (short) 2000;
		List<CargaMasivaItem> cargaMasivaItemList = cenTestUtils.getListCargaMasivaItemSimulado();
		CargaMasivaItem cargaMasivaItem = cenTestUtils.getCargaMasivaItemSimulado();

		when(cenCargaMasivaExtendsMapper.selectEtiquetas(idInstitucion, cargaMasivaItem))
				.thenReturn(cargaMasivaItemList);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CargaMasivaDTO cargaMasivaDTOResultado = cargasMasivasGFServiceImpl.searchEtiquetas(cargaMasivaItem, mockreq);

		CargaMasivaDTO cargaMasivaDTOEsperado = cenTestUtils.getCargaMasivaDTO();

		assertThat(cargaMasivaDTOResultado.toString()).isEqualTo(cargaMasivaDTOEsperado.toString());

	}

	// El test sólo funciona si tienes la carpeta creada
	@Test
	public void downloadOriginalFileTest() throws Exception {

		CargaMasivaItem cargaMasivaIteem = cenTestUtils.getCargaMasivaItemSimuladoGF();
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		ResponseEntity<InputStreamResource> response = cargasMasivasGFServiceImpl.downloadOriginalFile(cargaMasivaIteem,
				mockreq);
		assertThat(response).isNotNull();
	}

	@Test
	public void downloadLogFileTest() throws Exception {

		CargaMasivaItem cargaMasivaIteem = cenTestUtils.getCargaMasivaItemSimuladoGF();
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		ResponseEntity<InputStreamResource> response = cargasMasivasGFServiceImpl.downloadLogFile(cargaMasivaIteem,
				mockreq);
		assertThat(response).isNotNull();
	}

}
