package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboGuardiasFuturasDTO {
	private List<ComboGuardiasFuturasItem> comboGuardiasFuturasItems = new ArrayList<ComboGuardiasFuturasItem>();
	private Error error = null;

	@JsonProperty("comboGuardiasFuturasItems")
	public List<ComboGuardiasFuturasItem> getComboGuardiasFuturasItems() {
		return comboGuardiasFuturasItems;
	}

	public void setComboGuardiasFuturasItems(List<ComboGuardiasFuturasItem> comboGuardiasFuturasItems) {
		this.comboGuardiasFuturasItems = comboGuardiasFuturasItems;
	}

	@JsonProperty("error")
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
	
		return Objects.hash(getComboGuardiasFuturasItems(), getError());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ComboGuardiasFuturasDTO that = (ComboGuardiasFuturasDTO) obj;
        return Objects.equals(getComboGuardiasFuturasItems(), that.getComboGuardiasFuturasItems()) &&
                Objects.equals(getError(), that.getError());
	}
	@Override
	public String toString() {
		return "ComboGuardiasFuturasDTO {" +
        "comboGuardiasFuturasItems=" + comboGuardiasFuturasItems +
        ", error=" + error +
        '}';
	}

	
	
}
