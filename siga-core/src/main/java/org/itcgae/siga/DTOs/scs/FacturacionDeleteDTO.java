package org.itcgae.siga.DTOs.scs;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacturacionDeleteDTO {

	private String status = new String();
	private Error error = null;
	
	public FacturacionDeleteDTO status(String Status){
		this.status = Status;
		return this;
	}
	
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public FacturacionDeleteDTO error(Error error){
		this.error = error;
		return this;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		FacturacionDeleteDTO other = (FacturacionDeleteDTO) obj;
		
		if (error == null) {
			if (other.error != null) {
				return false;
			}
		} else if (!error.equals(other.error)) {
			return false;
		}
		
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		
		return true;
	}	
}