package org.itcgae.siga.DTO.fac;
import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class CuentasBancariasDTO {
	
	private String status = new String();
	private Error error = null;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<CuentasBancariasItem> getCuentasBancariasITem() {
		return cuentasBancariasITem;
	}
	public void setCuentasBancariasITem(List<CuentasBancariasItem> cuentasBancariasITem) {
		this.cuentasBancariasITem = cuentasBancariasITem;
	}
	private List<CuentasBancariasItem> cuentasBancariasITem = new ArrayList<CuentasBancariasItem>();
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuentasBancariasITem == null) ? 0 : cuentasBancariasITem.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentasBancariasDTO other = (CuentasBancariasDTO) obj;
		if (cuentasBancariasITem == null) {
			if (other.cuentasBancariasITem != null)
				return false;
		} else if (!cuentasBancariasITem.equals(other.cuentasBancariasITem))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CuentasBancariasDTO [cuentasBancariasITem="	+ cuentasBancariasITem +"status=" + status + ", error=" + error + "]";
	}
	
	
	

}
