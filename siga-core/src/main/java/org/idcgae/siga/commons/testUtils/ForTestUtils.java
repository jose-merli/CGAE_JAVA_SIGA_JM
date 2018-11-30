package org.idcgae.siga.commons.testUtils;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.springframework.stereotype.Service;

@Service
public class ForTestUtils {

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

	public FormadorCursoItem getFormadoresCursoItem() {
		FormadorCursoItem formador = new FormadorCursoItem();

		formador.setIdInstitucion((short) 2000);
		formador.setNif("48921787M");
		formador.setIdPersona(Long.valueOf("43344334"));
		formador.setIdCurso(Long.valueOf("1"));
		formador.setIdRol("1");
		formador.setIdTipoCoste("1");
		formador.setTarifa(Double.valueOf("10"));
		return formador;
	}

	public List<FormadorCursoItem> getListFormadoresCursoItem() {
		List<FormadorCursoItem> listaFormadoresCursoItem = new ArrayList<FormadorCursoItem>();
		listaFormadoresCursoItem.add(getFormadoresCursoItem());

		return listaFormadoresCursoItem;
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

	public List<ForCurso> getListForCursoSimulados() {

		List<ForCurso> listCursos = new ArrayList<ForCurso>();

		ForCurso curso = new ForCurso();
		curso.setIdcurso(Long.valueOf("1"));
		curso.setIdinstitucion(Short.valueOf("2000"));
		curso.setNombrecurso("Prueba");

		listCursos.add(curso);

		return listCursos;

	}

	public FormadorCursoDTO getFormadorCursoDTOSimulado() {
		FormadorCursoDTO formadorCursoDTO = new FormadorCursoDTO();
		List<FormadorCursoItem> listaFormadores = new ArrayList<FormadorCursoItem>();
		listaFormadores.add(getFormadoresCursoItem());
		formadorCursoDTO.setFormadoresCursoItem(listaFormadores);

		return formadorCursoDTO;
	}

	public List<ForPersonaCurso> getListForPersonaCursoSimulados() {

		List<ForPersonaCurso> listFormadoresCursos = new ArrayList<ForPersonaCurso>();

		ForPersonaCurso formador = new ForPersonaCurso();
		formador.setIdcurso(Long.valueOf("1"));
		formador.setIdinstitucion(Short.valueOf("2000"));
		formador.setIdpersona(Long.valueOf("43344334"));
		formador.setIdrol(Short.valueOf("1"));
		formador.setIdtipocoste(Short.valueOf("1"));
		formador.setTarifa(Long.valueOf("10"));

		listFormadoresCursos.add(formador);

		return listFormadoresCursos;

	}

}
