package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class LetradosGuardiaDTO {

	private List<LetradoGuardiaItem> letradosGuardiaItem = new ArrayList<>();
	private Error error = null;

	public List<LetradoGuardiaItem> getLetradosGuardiaItem() {
		return letradosGuardiaItem;
	}

	public void setLetradosGuardiaItem(List<LetradoGuardiaItem> letradosGuardiaItem) {
		this.letradosGuardiaItem = letradosGuardiaItem;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "LetradosGuardiaDTO [letradosGuardiaItem=" + letradosGuardiaItem + ", error=" + error + "]";
	}

}
