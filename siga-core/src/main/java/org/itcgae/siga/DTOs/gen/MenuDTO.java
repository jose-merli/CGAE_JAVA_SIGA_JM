package org.itcgae.siga.DTOs.gen;

import org.apache.commons.digester3.annotations.rules.ObjectCreate;


public class MenuDTO {
	
	/**
	 * Id 
	 */
	Long id;
	
	
	/**
	 * value
	 */
	String value;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

}
