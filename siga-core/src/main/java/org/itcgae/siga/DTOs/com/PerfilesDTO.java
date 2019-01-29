package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class PerfilesDTO {
	
	private List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
	private Error error = null;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<PerfilDTO> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(List<PerfilDTO> perfiles) {
		this.perfiles = perfiles;
	}
}
