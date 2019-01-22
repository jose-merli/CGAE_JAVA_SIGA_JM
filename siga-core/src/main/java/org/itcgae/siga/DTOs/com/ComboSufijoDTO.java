package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ComboSufijoDTO {

	private List<SufijoItem> sufijos = new ArrayList<SufijoItem>();
	
	private Error error = null;		
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<SufijoItem> getSufijos() {
		return sufijos;
	}
	public void setSufijos(List<SufijoItem> sufijos) {
		this.sufijos = sufijos;
	}
}
