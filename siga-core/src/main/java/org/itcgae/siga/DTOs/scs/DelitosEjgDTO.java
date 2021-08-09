package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.ScsDelitosejg;

public class DelitosEjgDTO {

	private List<ScsDelitosejg> delitosEjgItem = new ArrayList<ScsDelitosejg>();
	private Error error = null;
	
	
	public List<ScsDelitosejg> getdelitosEjgItem() {
		return delitosEjgItem;
	}
	public void setDelitosEjgItem(List<ScsDelitosejg> delitosEjgItem) {
		this.delitosEjgItem = delitosEjgItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
