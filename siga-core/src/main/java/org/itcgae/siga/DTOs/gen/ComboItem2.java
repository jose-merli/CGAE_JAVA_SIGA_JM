package org.itcgae.siga.DTOs.gen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ComboItem2   {
  
  private String label1 = null;
  private String label2 = null;
  private String value = null;

  
  /**
   **/
  public String label(String label1, String label2) {
    return label1 + ' ' + label2;
  }
  
  
  public ComboItem2 value(String value) {
    this.value = value;
    return this;
  }
  
  
  @JsonProperty("value")
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  



  public String getLabel1() {
	return label1;
}


public void setLabel1(String label1) {
	this.label1 = label1;
}


public String getLabel2() {
	return label2;
}


public void setLabel2(String label2) {
	this.label2 = label2;
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

