package org.idcgae.siga.commons.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.utils.TokenGenerationException;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TestUtils {

	public MockHttpServletRequest getRequestWithGeneralAuthentication() throws TokenGenerationException {

		MockHttpServletRequest mockreq = new MockHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();

		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		userCgae.setInstitucion("2000");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);

		mockreq.addHeader("Authorization", token);

		return mockreq;
	}

	public MockMultipartHttpServletRequest getMultipartRequestWithGeneralAuthentication()
			throws TokenGenerationException, IOException {

		MockMultipartHttpServletRequest mockreq = new MockMultipartHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();

		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		userCgae.setInstitucion("2000");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		mockreq.addParameter("idPersona", "1");
		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);

		mockreq.addHeader("Authorization", token);

		// FileInputStream inputFile = new FileInputStream( "/path/to/the/file.txt");
		MockMultipartFile file = new MockMultipartFile("files", "filename.txt", "text/plain",
				"Este archivo es generado como prueba de subida en TestUtils".getBytes(StandardCharsets.UTF_8));
		mockreq.addFile(file);

		return mockreq;
	}

	public MockMultipartHttpServletRequest getMultipartRequestWithGeneralAuthenticationCargasMasivas()
			throws TokenGenerationException, IOException {

		MockMultipartHttpServletRequest mockreq = new MockMultipartHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();

		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		userCgae.setInstitucion("2000");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		mockreq.addParameter("idPersona", "1");
		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);

		mockreq.addHeader("Authorization", token);

		File file = new File("C:\\Users\\DTUser\\Documents\\input.xls");
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		mockreq.addFile(multipartFile);

		return mockreq;
	}

	public MockHttpServletRequest getRequestWithGeneralAuthentication2005() throws TokenGenerationException {

		MockHttpServletRequest mockreq = new MockHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();

		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		userCgae.setInstitucion("2005");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);

		mockreq.addHeader("Authorization", token);

		return mockreq;
	}

	public MockHttpServletRequest getRequestWithGeneralAuthenticationNoIdInst() throws TokenGenerationException {

		MockHttpServletRequest mockreq = new MockHttpServletRequest();
		UserCgae userCgae = new UserCgae();
		List<String> listPerfiles = new ArrayList<>();

		userCgae.setDni("44149718E");
		userCgae.setGrupo("ADG");
		// userCgae.setInstitucion("2005");
		listPerfiles.add("ADG");
		userCgae.setPerfiles(listPerfiles);

		UserTokenUtils.configure("1234", "Bearer", 1000000, "");
		String token = UserTokenUtils.generateToken(userCgae);

		mockreq.addHeader("Authorization", token);

		return mockreq;
	}

	public MockHttpServletRequest getRequestWithVariableAuthentication(String idInstitucion) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ComboItem> getListComboItemsSimulados() {

		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		comboItem_0.setLabel("comboItem_0");
		comboItem_0.setValue("0");
		comboItems.add(comboItem_0);

		return comboItems;

	}

	public List<ComboItem> getListComboItemsLabelTestSimulados() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		comboItem_0.setLabel("");
		comboItem_0.setValue("");
		comboItems.add(comboItem_0);

		return comboItems;
	}

	public List<ComboItem> getListComboItemsValoresNulosSimulados() {

		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		ComboItem comboItem_0 = new ComboItem();
		ComboItem comboItem_1 = new ComboItem();

		comboItem_0.setLabel("");
		comboItem_0.setValue("");
		comboItems.add(0, comboItem_0);
		comboItem_1.setLabel("comboItem_0");
		comboItem_1.setValue("0");
		comboItems.add(comboItem_1);

		return comboItems;

	}

	public ComboItem getComboItemVacio() {

		ComboItem comboItem = new ComboItem();
		comboItem.setLabel("");
		comboItem.setValue("");

		return comboItem;

	}

	public List<AdmUsuarios> getListUsuariosSimulados(String idLenguaje) {
		List<AdmUsuarios> usuarios = new ArrayList<>();
		AdmUsuarios admUsuarios = new AdmUsuarios();
		admUsuarios.setIdlenguaje(idLenguaje);
		admUsuarios.setIdinstitucion((short) 2000);
		admUsuarios.setIdusuario(1);
		admUsuarios.setUsumodificacion(2);
		usuarios.add(admUsuarios);
		return usuarios;
	}

	public List<FichaDatosCurricularesItem> getListaFichaDatosCurricularesItem() {
		List<FichaDatosCurricularesItem> curriculares = new ArrayList<>();
		curriculares.add(getFichaDatosCurricularesItem());
		return curriculares;
	}

	public FichaDatosCurricularesItem getFichaDatosCurricularesItem() {
		FichaDatosCurricularesItem curriculares = new FichaDatosCurricularesItem();
		curriculares.setIdPersona("123");
		curriculares.setIdCv("1234");
		curriculares.setFechaDesdeDate(new Date());
		curriculares.setFechaMovimientoDate(new Date());
		curriculares.setCertificado("!");
		curriculares.setDescripcion("a");
		curriculares.setIdInstitucion("2005");
		curriculares.setIdTipoCv("1");
		curriculares.setCredito("1");
		curriculares.setIdTipoCvSubtipo1("1");
		curriculares.setIdTipoCvSubtipo2("2");
		return curriculares;
	}

	public GenDiccionario getGenDiccionario() {
		GenDiccionario genDiccionario = new GenDiccionario();

		genDiccionario.setIdlenguaje("1");
		genDiccionario.setDescripcion("descripcion");

		return genDiccionario;
	}

	public List<GenDiccionario> getListGenDiccionario() {
		List<GenDiccionario> genDiccionarioList = new ArrayList<GenDiccionario>();
		genDiccionarioList.add(getGenDiccionario());

		return genDiccionarioList;
	}
}
