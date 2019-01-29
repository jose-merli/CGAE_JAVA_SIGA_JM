package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class KeysDTO {
	
	private List<KeyItem> keysItem = new ArrayList<KeyItem>();
	private Error error = null;
	

	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<KeyItem> getKeysItem() {
		return keysItem;
	}
	public void setKeysItem(List<KeyItem> keysItem) {
		this.keysItem = keysItem;
	}
	
}
