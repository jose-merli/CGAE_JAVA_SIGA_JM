package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class ConfigColumnasQueryBuilderDTO {
	private List <ConfigColumnasQueryBuilderItem> configColumnasQueryBuilderItem = new ArrayList <ConfigColumnasQueryBuilderItem>();
	private Error error;
	
	public List<ConfigColumnasQueryBuilderItem> getConfigColumnasQueryBuilderItem() {
		return configColumnasQueryBuilderItem;
	}
	public void setConfigColumnasQueryBuilderItem(List<ConfigColumnasQueryBuilderItem> configColumnasQueryBuilderItem) {
		this.configColumnasQueryBuilderItem = configColumnasQueryBuilderItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(configColumnasQueryBuilderItem, error);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigColumnasQueryBuilderDTO other = (ConfigColumnasQueryBuilderDTO) obj;
		return Objects.equals(configColumnasQueryBuilderItem, other.configColumnasQueryBuilderItem)
				&& Objects.equals(error, other.error);
	}
	
	@Override
	public String toString() {
		return "ConfigColumnasQueryBuilderDTO [configColumnasQueryBuilderItem=" + configColumnasQueryBuilderItem
				+ ", error=" + error + "]";
	}

}
