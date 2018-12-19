package org.itcgae.siga.cen.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.idcgae.siga.commons.testUtils.CenTestUtils;
import org.idcgae.siga.commons.testUtils.TestUtils;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteKey;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CargasMasivasGFServiceTest {

	@Mock
	private ICargasMasivasGFService cargasMasivasGFService;

	@Mock
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;

	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Mock
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;

	@Mock
	private CenGruposclienteMapper cenGruposclienteMapper;

	@Mock
	private CenPersonaMapper cenPersonaMapper;

	@Mock
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Mock
	private CenGruposclienteClienteMapper cenGruposclienteClienteMapper;

	@Mock
	private CenHistoricoMapper cenHistoricoMapper;

	@Mock
	private CenCargamasivaMapper cenCargamasivaMapper;

	@Mock
    IFicherosService ficherosService;

	@Mock
	private GenPropertiesMapper genPropertiesMapper;

	@Mock
	private GenRecursosMapper genRecursosMapper;

	@Mock
	private SIGAServicesHelperMapper sigaServicesHelperMapper;

	@Mock
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;

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

	/** Para ejecutar este test habrá que crear un archivo en la siguiente ruta: C:\\Users\\DTUser\\Documents\\input.xls 
	 * Con el siguiente bloque de información: 
	 *COLEGIADONUMERO	PERSONANIF	C_IDGRUPO	GENERAL	ACCION	C_FECHAINICIO	C_FECHAFIN
	 *			3586	70639511W	19	 		0		A		27/09/2018		28/09/2018
	 *			3586	70639511W	19			0		B		28/09/2018		29/09/2018
	 **/
	@Test
	public void uploadFileExcelTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String idPersona = "2005001213";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		CenPersona CenPersona = cenTestUtils.getCenPersonaSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado("DESCRIPCIÓN");
		CenGruposcliente gruposCliente = cenTestUtils.getGrupCliItem();
		CenGruposclienteCliente	 cenGruposclienteCliente = cenTestUtils.getGruposCliCliItem();
		List<GenRecursos> genRecursos = cenTestUtils.getListGenRecursosSimulado();
		List<GenProperties> genPropertiesDirectorio = cenTestUtils.getListGenPropertiesSimulados();
		
		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);
		
		when(cenClienteExtendsMapper.getIdPersonaWithNif("70639511W", (short) 2000)).thenReturn(Long.valueOf(idPersona));
		
		when(cenClienteExtendsMapper.getIdPersona("3586", "70639511W", (short) 2000)).thenReturn(Long.valueOf(idPersona));
		
		when(cenPersonaMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(CenPersona);
		
		when(cenGruposclienteMapper.selectByPrimaryKey(Mockito.any(CenGruposcliente.class))).thenReturn(gruposCliente);

		when(genRecursosCatalogosMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class)))
		.thenReturn(genRecursosCatalogos);
		
		when(cenGruposclienteClienteMapper.selectByPrimaryKey(Mockito.any(CenGruposclienteClienteKey.class))).thenReturn(cenGruposclienteCliente);
		
		when(cenGruposclienteClienteMapper.selectByPrimaryKey(Mockito.any(CenGruposclienteClienteKey.class))).thenReturn(cenGruposclienteCliente);


		when(cenGruposclienteClienteMapper.insert(Mockito.any(CenGruposclienteCliente.class))).thenReturn(1);

		when(cenGruposclienteClienteMapper.updateByPrimaryKey(Mockito.any(CenGruposclienteCliente.class)))
				.thenReturn(1);

		
		when(cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(idPersona, idInstitucion))
				.thenReturn(newIdDTO);

		when(genRecursosMapper.selectByExample(Mockito.any(GenRecursosExample.class)))
				.thenReturn(genRecursos);

		when(genRecursosMapper.selectByExample(Mockito.any(GenRecursosExample.class)))
		.thenReturn(genRecursos);

		when(cenHistoricoMapper.insert(Mockito.any(CenHistorico.class))).thenReturn(1);

		when(genPropertiesMapper.selectByExample(Mockito.any(GenPropertiesExample.class))).thenReturn(genPropertiesDirectorio);

		when(genPropertiesMapper.selectByExample(Mockito.any(GenPropertiesExample.class))).thenReturn(genPropertiesDirectorio);

		when(cenCargaMasivaExtendsMapper.insert(Mockito.any(CenCargamasiva.class))).thenReturn(1);

		MockMultipartHttpServletRequest mockreq = testUtils.getMultipartRequestWithGeneralAuthenticationCargasMasivas();

		UpdateResponseDTO updateResponseDTOResultado = cargasMasivasGFServiceImpl.uploadFileExcel(mockreq);
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);
		Error error = new Error();
		String errores = "Usuario ya asignado a ese grupo fijo. Error al insertar en cargas masivas";
		error.setDescription(errores);
		error.setMessage("Fichero cargado correctamente. Registros Correctos: 1<br/> Registros Erroneos: 1");
		updateResponseDTOEsperado.setError(error);
		
		assertThat(updateResponseDTOResultado).isEqualTo(updateResponseDTOEsperado);
	}
	
	/**
	 * El test sólo funcionará si existe la carpeta C:\Users\DTUser\Documents\GF2000\gruposfijos
	 * y dentro se encuentra el fichero excel con nombre 2000_257723.xls (se puede crear vacío)
	 */
	@Test
	public void downloadOriginalFileTest() throws Exception {

		CargaMasivaItem cargaMasivaIteem = cenTestUtils.getCargaMasivaItemSimuladoGF();
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		ResponseEntity<InputStreamResource> response = cargasMasivasGFServiceImpl.downloadOriginalFile(cargaMasivaIteem,
				mockreq);
		assertThat(response).isNotNull();
	}

	/**
	 * El test sólo funcionará si existe la carpeta C:\Users\DTUser\Documents\GF2000\gruposfijos
	 * y dentro se encuentra el fichero excel con nombre log_2000_257724 (se puede crear vacío)
	 */
	@Test
	public void downloadLogFileTest() throws Exception {

		CargaMasivaItem cargaMasivaIteem = cenTestUtils.getCargaMasivaItemSimuladoGF();
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		ResponseEntity<InputStreamResource> response = cargasMasivasGFServiceImpl.downloadLogFile(cargaMasivaIteem,
				mockreq);
		assertThat(response).isNotNull();
	}

}
