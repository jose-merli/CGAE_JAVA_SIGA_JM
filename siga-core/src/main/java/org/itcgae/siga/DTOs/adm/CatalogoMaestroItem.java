package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CatalogoMaestroItem   {
  
  private String catalogo = null;
  private String codigoExt = null;
  private String descripcion = null;
  private String idInstitucion = null;
  private String idRegistro = null;
  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date fechaBaja = null;
  private String longitudDescripcion;
  private String longitudCodigoExt;
  
  
  
  /**
   **/
  public CatalogoMaestroItem longitudCodigoExt(String longitudCodigoExt) {
    this.longitudCodigoExt = longitudCodigoExt;
    return this;
  }
  
  @JsonProperty("longitudCodigoExt")
   public String getLongitudCodigoExt() {
	return longitudCodigoExt;
   }


	public void setLongitudCodigoExt(String longitudCodigoExt) {
		this.longitudCodigoExt = longitudCodigoExt;
	}


   /**
   **/
  public CatalogoMaestroItem catalogo(String catalogo) {
    this.catalogo = catalogo;
    return this;
  }
  
  
  @JsonProperty("catalogo")
  public String getCatalogo() {
    return catalogo;
  }
  public void setCatalogo(String catalogo) {
    this.catalogo = catalogo;
  }

  
  /**
   **/
  public CatalogoMaestroItem codigoExt(String codigoExt) {
    this.codigoExt = codigoExt;
    return this;
  }
  
  
  @JsonProperty("codigoExt")
  public String getCodigoExt() {
    return codigoExt;
  }
  public void setCodigoExt(String codigoExt) {
    this.codigoExt = codigoExt;
  }

  /**
   **/
  public CatalogoMaestroItem descripcion(String descripcion) {
    this.codigoExt = descripcion;
    return this;
  }
  
  
  @JsonProperty("descripcion")
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  
  /**
   **/
  public CatalogoMaestroItem idRegistro(String idRegistro) {
    this.idRegistro = idRegistro;
    return this;
  }
  
  
  @JsonProperty("idRegistro")
  public String getIdRegistro() {
    return idRegistro;
  }
  public void setIdRegistro(String idRegistro) {
    this.idRegistro = idRegistro;
  }


  
  /**
   **/
  public CatalogoMaestroItem idInstitucion(String idInstitucion) {
    this.idInstitucion = idInstitucion;
    return this;
  }
  
  
  @JsonProperty("idInstitucion")
  public String getIdInstitucion() {
    return idInstitucion;
  }
  public void setIdInstitucion(String idInstitucion) {
    this.idInstitucion = idInstitucion;
  }
  
	/**
	 */
	public CatalogoMaestroItem fechaBaja(Date fechaBaja){
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
	
	

	/**
	 **/
	public CatalogoMaestroItem longitudDescripcion(String longitudDescripcion) {
		this.longitudDescripcion = longitudDescripcion;
		return this;
	}

	@JsonProperty("longitudDescripcion")
	public String getLongitudDescripcion() {
		return longitudDescripcion;
	}

	public void setLongitudDescripcion(String longitudDescripcion) {
		this.longitudDescripcion = longitudDescripcion;
	}
	
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CatalogoMaestroItem catalogoMaestroItem = (CatalogoMaestroItem) o;
    return Objects.equals(this.catalogo, catalogoMaestroItem.catalogo) &&
        Objects.equals(this.codigoExt, catalogoMaestroItem.codigoExt)&&
        Objects.equals(this.descripcion, catalogoMaestroItem.descripcion)&&
        Objects.equals(this.idInstitucion, catalogoMaestroItem.idInstitucion)&&
    	Objects.equals(this.idRegistro, catalogoMaestroItem.idRegistro)&&
    	Objects.equals(this.fechaBaja, catalogoMaestroItem.fechaBaja) &&
    	Objects.equals(this.longitudCodigoExt, catalogoMaestroItem.longitudCodigoExt) &&
    	Objects.equals(this.longitudDescripcion, catalogoMaestroItem.longitudDescripcion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalogo, codigoExt,descripcion,idInstitucion,longitudDescripcion, longitudCodigoExt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CatalogoMaestroItem {\n");
    
    sb.append("    catalogo: ").append(toIndentedString(catalogo)).append("\n");
    sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
    sb.append("    longitudCodigoExt: ").append(toIndentedString(longitudCodigoExt)).append("\n");
    sb.append("    longitudDescripcion: ").append(toIndentedString(longitudDescripcion)).append("\n");
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

