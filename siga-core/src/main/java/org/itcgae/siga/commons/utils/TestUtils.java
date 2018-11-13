package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TestUtils{
	
	public MockHttpServletRequest  getRequestWithGeneralAuthentication() throws TokenGenerationException {

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

	public MockHttpServletRequest  getRequestWithVariableAuthentication(String idInstitucion) {
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
	
	public List<CursoItem> getListCursosSimulados(String estado, Integer flagArchivado) {
			
		List<CursoItem> cursoItems = new ArrayList<CursoItem>();
		
		cursoItems.add(getCursoSimulado(estado, flagArchivado));
		
		return cursoItems;
	}	
	
	public CursoItem getCursoSimulado(String estado, Integer flagArchivado) {
		
		CursoItem cursoItem = new CursoItem();
		cursoItem.setCodigoCurso("003GREW");
		cursoItem.setIdInstitucion("2000");
		cursoItem.setIdEstado(estado);
		cursoItem.setFlagArchivado(flagArchivado);
		
		return cursoItem;
	}
	
	public ComboItem getComboItemVacio() {
		
		ComboItem comboItem = new ComboItem();
		comboItem.setLabel("");
		comboItem.setValue("");
		
		return comboItem;
		
	}
	
	public List<AdmUsuarios> getListUsuariosSimulados(String idLenguaje){
		List<AdmUsuarios> usuarios = new ArrayList<>();
		AdmUsuarios admUsuarios = new AdmUsuarios();
		admUsuarios.setIdlenguaje(idLenguaje);
		admUsuarios.setIdusuario(1);
		usuarios.add(admUsuarios);
		return usuarios;
	}
	
	public List<AgeCalendario> getListaAgeCalendariosSimulados(){
		List<AgeCalendario> listaAgeCalendarios = new ArrayList<AgeCalendario>();
		listaAgeCalendarios.add(getAgeCalendario());
		
		return listaAgeCalendarios;
	}
	
	public AgeCalendario getAgeCalendario(){
		AgeCalendario ageCalendario = new AgeCalendario();

		ageCalendario.setIdinstitucion((short)2000);
		ageCalendario.setIdcalendario((long)100);
		ageCalendario.setDescripcion("Descripcion");
		ageCalendario.setColor("Red");
		ageCalendario.setIdtipocalendario((long)1);
		  
		return ageCalendario;		
	}
}
