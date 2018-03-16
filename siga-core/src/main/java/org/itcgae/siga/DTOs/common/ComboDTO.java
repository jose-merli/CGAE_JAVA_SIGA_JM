package org.itcgae.siga.DTOs.common;

import org.apache.commons.digester3.annotations.rules.ObjectCreate;


public class ComboDTO {
	
	/**
	 * Id 
	 */
	String id;
	
	
	/**
	 * value
	 */
	String value;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getValue() {
		return value;
	}
	

	public void setValue(String value) {
		this.value = value;
	}

}
