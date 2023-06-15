package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import org.itcgae.siga.DTOs.gen.Error;



public class FacRegistroFichContaDTO {
	private List<FacRegistroFichConta> facRegistroFichConta =  new ArrayList<>();
	private Error error = null;
	
	public List<FacRegistroFichConta> getFacRegistroFichConta() {
		return facRegistroFichConta;
	}
	public void setFacRegistroFichConta(List<FacRegistroFichConta> facRegistroFichConta) {
		this.facRegistroFichConta = facRegistroFichConta;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}

	
}
