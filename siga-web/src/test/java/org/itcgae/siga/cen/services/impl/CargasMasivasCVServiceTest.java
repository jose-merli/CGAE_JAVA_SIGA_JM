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
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.mappers.CenHistoricoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.CenTiposcvMapper;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo1Mapper;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo2Mapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GenFicheroExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
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
public class CargasMasivasCVServiceTest {

	@Mock
	private CenPersonaMapper cenPersonaMapper;
	@Mock
	private CenTiposcvMapper cenTiposcvMapper;
	@Mock
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;
	@Mock
	private CenTiposcvsubtipo1Mapper cenTiposcvsubtipo1Mapper;
	@Mock
	private CenTiposcvsubtipo2Mapper cenTiposcvsubtipo2Mapper;
	@Mock
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Mock
	private CenDatoscvExtendsMapper cenDatoscvMapperExtends;
	@Mock
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;
	@Mock
	private CenHistoricoMapper cenHistoricoMapper;
	@Mock
	private FicherosServiceImpl ficherosServiceImpl;
	@Mock
	private GenPropertiesMapper genPropertiesMapper;
	@Mock
	private GenRecursosMapper genRecursosMapper;
	@Mock
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	@Mock
	private GenFicheroExtendsMapper genFicheroExtendsMapper;

	@InjectMocks
	private CargasMasivasCVServiceImpl cargasMasivasCVServiceImpl;

	private TestUtils testUtils = new TestUtils();

	private CenTestUtils cenTestUtils = new CenTestUtils();

	@Test
	public void createExcelFileTest() throws BusinessException {
		List<String> listString = cenTestUtils.getListaSimulada();
		Vector<Hashtable<String, Object>> datosVector = cenTestUtils.getDatosVectorSimulado();

		File response = cargasMasivasCVServiceImpl.createExcelFile(listString, datosVector);
		assertThat(response).isNotNull();
	}

	@Test
	public void generateExcelCVTest() {
		ResponseEntity<InputStreamResource> response = cargasMasivasCVServiceImpl.generateExcelCV();
		assertThat(response).isNotNull();
	}

	/** Para ejecutar este test habrá que crear un archivo en la siguiente ruta: C:\\Users\\DTUser\\Documents\\input.xls 
	 * Con el siguiente bloque de información: 
	 * COLEGIADONUMERO	PERSONANIF	C_FECHAINICIO	C_FECHAFIN	C_CREDITOS	FECHAVERIFICACION	C_DESCRIPCION	TIPOCVCOD	SUBTIPOCV1COD	SUBTIPOCV2COD
	 * 3586	            29002353C					                                             DESCRIPCIÓN	         1	          1	              1
	 * 3544	            29002483C					                                             CARGO	                 1		
	 **/
	@Test
	public void uploadFileTest() throws Exception {

		String idLenguaje = "1";
		String idInstitucion = "2000";
		String idPersona = "2005001213";
		List<AdmUsuarios> usuarios = testUtils.getListUsuariosSimulados(idLenguaje);
		List<CenPersona> cenPersona = cenTestUtils.getListaPersonasSimuladas(Long.valueOf(idPersona), "54678765C");
		CenPersona CenPersona = cenTestUtils.getCenPersonaSimulado();
		CenDatoscv cenDatoscv = cenTestUtils.getCenDatoscvSimulado();
		NewIdDTO newIdDTO = cenTestUtils.getNewIdDTOSimulado();
		List<CenTiposcv> tiposCV = cenTestUtils.getListCenTiposcvSimulado();
		GenRecursosCatalogos genRecursosCatalogos = cenTestUtils.getGenRecursosCatalogosSimulado("DESCRIPCIÓN");
		List<CenTiposcvsubtipo1> tiposcvsubtipo1s = cenTestUtils.getListCenTiposcvsubtipo1Simulado();
		List<CenTiposcvsubtipo2> tiposcvsubtipo2 = cenTestUtils.getListCenTiposcvsubtipo2Simulado();

		when(admUsuariosExtendsMapper.selectByExample(Mockito.any(AdmUsuariosExample.class))).thenReturn(usuarios);

		when(cenPersonaMapper.selectByExample(Mockito.any(CenPersonaExample.class))).thenReturn(cenPersona);

		when(cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersona))).thenReturn(CenPersona);

		when(cenTiposcvMapper.selectByExample(Mockito.any(CenTiposcvExample.class))).thenReturn(tiposCV);

		when(genRecursosCatalogosMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class)))
				.thenReturn(genRecursosCatalogos);

		when(cenTiposcvsubtipo1Mapper.selectByExample(Mockito.any(CenTiposcvsubtipo1Example.class)))
				.thenReturn(tiposcvsubtipo1s);

		when(cenTiposcvsubtipo1Mapper.selectByExample(Mockito.any(CenTiposcvsubtipo1Example.class)))
				.thenReturn(tiposcvsubtipo1s);

		when(genRecursosCatalogosMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class)))
				.thenReturn(genRecursosCatalogos);

		when(cenTiposcvsubtipo2Mapper.selectByExample(Mockito.any(CenTiposcvsubtipo2Example.class)))
				.thenReturn(tiposcvsubtipo2);

		when(genRecursosCatalogosMapper.selectByPrimaryKey(Mockito.any(GenRecursosCatalogosKey.class)))
				.thenReturn(genRecursosCatalogos);

		when(cenDatoscvMapperExtends.selectByPrimaryKey(Mockito.any(CenDatoscvKey.class))).thenReturn(cenDatoscv);

		when(cenDatoscvMapperExtends.getMaxIdCv(idInstitucion, idPersona)).thenReturn(newIdDTO);

		when(cenDatoscvMapperExtends.insertSelective(Mockito.any(CenDatoscv.class))).thenReturn(1);

		when(cenDatoscvMapperExtends.insertSelective(Mockito.any(CenDatoscv.class))).thenReturn(1);

		when(genFicheroExtendsMapper.insert(Mockito.any(GenFichero.class))).thenReturn(1);

		when(genFicheroExtendsMapper.insert(Mockito.any(GenFichero.class))).thenReturn(1);

		when(cenCargaMasivaExtendsMapper.insert(Mockito.any(CenCargamasiva.class))).thenReturn(1);

		MockMultipartHttpServletRequest mockreq = testUtils.getMultipartRequestWithGeneralAuthenticationCargasMasivas();

		UpdateResponseDTO updateResponseDTOResultado = cargasMasivasCVServiceImpl.uploadFile(mockreq);
		UpdateResponseDTO updateResponseDTOEsperado = new UpdateResponseDTO();
		updateResponseDTOEsperado.setStatus(SigaConstants.OK);
		Error error = new Error();
		String errores = "El registro ya existe.<br/>Errores en la línea 2 : Al existir subtipos 1 para este tipo de cv es obligatorio introducir el subtipo 1. <br/>";
		error.setDescription(errores);
		error.setMessage("Fichero cargado correctamente. Registros Correctos: 1<br/> Registros Erroneos: 1");
		updateResponseDTOEsperado.setError(error);
		
		assertThat(updateResponseDTOResultado.toString()).isEqualTo(updateResponseDTOEsperado.toString());
	}

	@Test
	public void searchCVTest() throws Exception {
		Short idInstitucion = (short) 2000;
		List<CargaMasivaItem> cargaMasivaItemList = cenTestUtils.getListCargaMasivaItemSimulado();
		CargaMasivaItem cargaMasivaItem = cenTestUtils.getCargaMasivaItemSimulado();

		when(cenCargaMasivaExtendsMapper.selectEtiquetas(idInstitucion, cargaMasivaItem))
				.thenReturn(cargaMasivaItemList);

		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication();

		CargaMasivaDTO cargaMasivaDTOResultado = cargasMasivasCVServiceImpl.searchCV(cargaMasivaItem, mockreq);

		CargaMasivaDTO cargaMasivaDTOEsperado = cenTestUtils.getCargaMasivaDTO();

		assertThat(cargaMasivaDTOResultado.toString()).isEqualTo(cargaMasivaDTOEsperado.toString());

	}

	/**
	 * El test sólo funcionará si existe la carpeta C:\Users\DTUser\Documents\CV2000\cargas
	 * y dentro se encuentra el fichero excel 2000_257721.xls
	 */
	@Test
	public void downloadOriginalFileTest() throws Exception {

		CargaMasivaItem cargaMasivaIteem = cenTestUtils.getCargaMasivaItemSimulado();
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		ResponseEntity<InputStreamResource> response = cargasMasivasCVServiceImpl.downloadOriginalFile(cargaMasivaIteem,
				mockreq);
		assertThat(response).isNotNull();
	}

	/**
	 * El test sólo funcionará si existe la carpeta C:\Users\DTUser\Documents\CV2000\cargas
	 * y dentro se encuentra el fichero excel log_2000_257722.xls
	 */
	@Test
	public void downloadLogFileTest() throws Exception {

		CargaMasivaItem cargaMasivaIteem = cenTestUtils.getCargaMasivaItemSimulado();
		MockHttpServletRequest mockreq = testUtils.getRequestWithGeneralAuthentication2005();

		ResponseEntity<InputStreamResource> response = cargasMasivasCVServiceImpl.downloadLogFile(cargaMasivaIteem,
				mockreq);
		assertThat(response).isNotNull();
	}
}
