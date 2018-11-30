package org.itcgae.siga.DTOs.gen;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FestivosDTO {
 
 private List<ListOfResult> listOfResult = new ArrayList<ListOfResult>();

 private float total;


 // Getter Methods 

 @JsonProperty("total")
 public float getTotal() {
  return total;
 }


 @JsonProperty("listOfResult")
 public List<ListOfResult> getListOfResult() {
	return listOfResult;
}



public void setListOfResult(List<ListOfResult> listOfResultObject) {
	this.listOfResult = listOfResultObject;
}



public void setTotal(float total) {
  this.total = total;
 }
}
