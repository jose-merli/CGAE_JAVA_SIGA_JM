package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class FundamentosCalificacionDTO   {
  
  private List<FundamentosCalificacionItem> fundamentosCalificacionesItems = new ArrayList<FundamentosCalificacionItem>();
  private Error error = null;

  
  public List<FundamentosCalificacionItem> getFundamentosCalificacionesItems() {
	return fundamentosCalificacionesItems;
}

public void setFundamentosCalificacionesItems(List<FundamentosCalificacionItem> fundamentosCalificacionesItems) {
	this.fundamentosCalificacionesItems = fundamentosCalificacionesItems;
}

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
    FundamentosCalificacionDTO juzgadoDTO = (FundamentosCalificacionDTO) o;
    return Objects.equals(this.fundamentosCalificacionesItems, juzgadoDTO.fundamentosCalificacionesItems) &&
        Objects.equals(this.error, juzgadoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fundamentosCalificacionesItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JuzgadoDTO {\n");
    
    sb.append("    juzgadoItems: ").append(toIndentedString(fundamentosCalificacionesItems)).append("\n");
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
}

