package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DeleteResponseDTO {
	
	private String status = new String();
	private Error error = null;
	private String id = new String();
	
	
	/**
	 */
	public DeleteResponseDTO status(String status){
		this.status = status;
		return this;
	}
	
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	/**
	 */
	public DeleteResponseDTO error(Error error){
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
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DeleteResponseDTO deleteResponseDTO = (DeleteResponseDTO) o;
		return Objects.equals(this.status, deleteResponseDTO.status) &&
				Objects.equals(this.error, deleteResponseDTO.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DeleteResponseDTO {\n");
    
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    error: ").append(toIndentedString(error)).append("\n");
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
