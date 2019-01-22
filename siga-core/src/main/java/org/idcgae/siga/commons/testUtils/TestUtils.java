package org.idcgae.siga.commons.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
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
		generaInputXLS();
		File file = new File(".\\input.xls");
	
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		mockreq.addFile(multipartFile);
		file.deleteOnExit();
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

	public FichaDatosCurricularesDTO getFichaDatosCurricularesDTO() {
		FichaDatosCurricularesDTO fichaDatosCurricularesDTO = new FichaDatosCurricularesDTO();
		List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();
		fichaDatosCurricularesItem.add(getFichaDatosCurricularesItem());
		fichaDatosCurricularesDTO.setFichaDatosCurricularesItem(fichaDatosCurricularesItem);

		return fichaDatosCurricularesDTO;
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
	
	public void generaInputXLS() {
		try {
			FileOutputStream fileOut = new FileOutputStream("input.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("input");

			// index from 0,0... cell A1 is cell(0,0)
			HSSFRow row1 = worksheet.createRow((short) 0);

			HSSFCell cellA1 = row1.createCell(0);
			cellA1.setCellValue("COLEGIADONUMERO");

			HSSFCell cellB1 = row1.createCell(1);
			cellB1.setCellValue("PERSONANIF");

			HSSFCell cellC1 = row1.createCell(2);
			cellC1.setCellValue("C_IDGRUPO");

			HSSFCell cellD1 = row1.createCell(3);
			cellD1.setCellValue("GENERAL");

			HSSFCell cellE1 = row1.createCell(4);
			cellE1.setCellValue("ACCION");

			HSSFCell cellF1 = row1.createCell(5);
			cellF1.setCellValue("C_FECHAINICIO");

			HSSFCell cellG1 = row1.createCell(6);
			cellG1.setCellValue("C_FECHAFIN");
			
			// Fila 2
			HSSFRow row2 = worksheet.createRow((short) 1);

			HSSFCell cellA2 = row2.createCell(0);
			cellA2.setCellValue("3586");

			HSSFCell cellB2 = row2.createCell(1);
			cellB2.setCellValue("70639511W");

			HSSFCell cellC2 = row2.createCell(2);
			cellC2.setCellValue("19");

			HSSFCell cellD2 = row2.createCell(3);
			cellD2.setCellValue("0");

			HSSFCell cellE2 = row2.createCell(4);
			cellE2.setCellValue("A");

			HSSFCell cellF2 = row2.createCell(5);
			cellF2.setCellValue("27/09/2018");

			HSSFCell cellG2 = row2.createCell(6);
			cellG2.setCellValue("28/09/2018");
			
			// Fila 3
			HSSFRow row3 = worksheet.createRow((short) 2);

			HSSFCell cellA3 = row3.createCell(0);
			cellA3.setCellValue("3586");

			HSSFCell cellB3 = row3.createCell(1);
			cellB3.setCellValue("70639511W");

			HSSFCell cellC3 = row3.createCell(2);
			cellC3.setCellValue("19");

			HSSFCell cellD3 = row3.createCell(3);
			cellD3.setCellValue("0");

			HSSFCell cellE3 = row3.createCell(4);
			cellE3.setCellValue("B");

			HSSFCell cellF3 = row3.createCell(5);
			cellF3.setCellValue("28/09/2018");

			HSSFCell cellG3 = row3.createCell(6);
			cellG3.setCellValue("29/09/2018");

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
