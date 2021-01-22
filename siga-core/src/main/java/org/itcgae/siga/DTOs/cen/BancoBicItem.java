package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BancoBicItem {
	
	private String banco;
	private String bic;
	private String bicEspanol;
	
	
	
	/**
	 */
	public BancoBicItem banco(String banco){
		this.banco = banco;
		return this;
	}
	
	
	
	@JsonProperty("banco")
	public String getBanco() {
		return banco;
	}
	
	
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	
	/**
	 */
	public BancoBicItem bic(String bic){
		this.bic = bic;
		return this;
	}
	
	@JsonProperty("bic")
	public String getBic() {
		return bic;
	}
	
	
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	/**
	 */
	public BancoBicItem bicEspanol(String bicEspanol){
		this.bicEspanol = bicEspanol;
		return this;
	}
	
	@JsonProperty("bicEspanol")
	public String getBicEspanol() {
		return bicEspanol;
	}
	
	
	public void setBicEspanol(String bicEspanol) {
		this.bicEspanol = bicEspanol;
	}
	
	
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    BancoBicItem datosRegistralesItem = (BancoBicItem) o;
	    return Objects.equals(this.banco, datosRegistralesItem.banco) &&
	    	   Objects.equals(this.bic, datosRegistralesItem.bic) &&
	    	   Objects.equals(this.bicEspanol, datosRegistralesItem.bicEspanol) 
	    	   ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(banco, bic, bicEspanol);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosRegistralesItem {\n");
	    
	    sb.append("    banco: ").append(toIndentedString(banco)).append("\n");
	    sb.append("    bic: ").append(toIndentedString(bic)).append("\n");
	    sb.append("    bicEspanol: ").append(toIndentedString(bicEspanol)).append("\n");
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
