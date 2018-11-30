package org.idcgae.siga.commons.testUtils;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.utils.TokenGenerationException;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;

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
	
	public List<ComboItem> getListComboItemsLabelTestSimulados(){
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
		admUsuarios.setIdusuario(1);
		usuarios.add(admUsuarios);
		return usuarios;
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
