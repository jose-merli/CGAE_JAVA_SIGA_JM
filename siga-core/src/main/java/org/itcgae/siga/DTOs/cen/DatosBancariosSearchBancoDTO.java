package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosBancariosSearchBancoDTO {

	private String iban;
	

	
	
	/**
	 */
	public DatosBancariosSearchBancoDTO iban(String iban){
		this.iban = iban;
		return this;
	}
	
	@JsonProperty("iban")
	public String getiban() {
		return iban;
	}
	public void setiban(String iban) {
		this.iban = iban;
	}
	


	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosSearchBancoDTO {\n");
	    
	    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");


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
