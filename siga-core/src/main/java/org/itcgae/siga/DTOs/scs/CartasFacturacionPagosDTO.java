package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartasFacturacionPagosDTO {

	private List<CartasFacturacionPagosItem> cartasFacturacionPagosItems = new ArrayList<CartasFacturacionPagosItem>();
	private Error error = null;

	/**
	 **/
	public CartasFacturacionPagosDTO cartasFacturacionPagosItem(List<CartasFacturacionPagosItem> cartasFacturacionPagosItems) {
		this.cartasFacturacionPagosItems = cartasFacturacionPagosItems;
		return this;
	}

	@JsonProperty("cartasFacturacionPagosItems")
	public List<CartasFacturacionPagosItem> getCartasFacturacionPagosItems() {
		return cartasFacturacionPagosItems;
	}

	public void setCartasFacturacionPagosItems(List<CartasFacturacionPagosItem> cartasFacturacionPagosItems) {
		this.cartasFacturacionPagosItems = cartasFacturacionPagosItems;
	}

	/**
	 **/
	public CartasFacturacionPagosDTO error(Error error) {
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CartasFacturacionPagosDTO {\n");

		sb.append("    CartasFacturacionPagosItem: ").append(toIndentedString(cartasFacturacionPagosItems)).append("\n");
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
