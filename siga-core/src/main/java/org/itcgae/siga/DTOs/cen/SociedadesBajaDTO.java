package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class SociedadesBajaDTO {
	
	
	private String nif;

	private Date fechaBaja;

	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}




	
	
	
	
	/**
	 */
	public SociedadesBajaDTO fechaBaja(Date fechaBaja){
		this.fechaBaja = fechaBaja;
		return this;
	}
	
	
	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	

	
		
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    SociedadesBajaDTO sociedadesEditadasDTO = (SociedadesBajaDTO) o;
	    return Objects.equals(this.nif, sociedadesEditadasDTO.nif) &&
	    		Objects.equals(this.fechaBaja, sociedadesEditadasDTO.fechaBaja) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nif, fechaBaja);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SociedadesEditadasDTO {\n");
	    
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
	    sb.append("}");
	    return sb.toString();
	}

	/**
	* Convert the given object to string with each line indented by 4 spaces
	* (except the first line).
	*/
	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	}
	
	
}
