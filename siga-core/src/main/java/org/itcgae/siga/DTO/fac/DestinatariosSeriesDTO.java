package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class DestinatariosSeriesDTO {

	private List<DestinatariosSeriesItem> destinatariosSeriesItems = new ArrayList<>();
	private Error error = null;

	public List<DestinatariosSeriesItem> getDestinatariosSeriesItems() {
		return destinatariosSeriesItems;
	}

	public void setDestinatariosSeriesItems(List<DestinatariosSeriesItem> destinatariosSeriesItems) {
		this.destinatariosSeriesItems = destinatariosSeriesItems;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DestinatariosSeriesDTO that = (DestinatariosSeriesDTO) o;
		return Objects.equals(destinatariosSeriesItems, that.destinatariosSeriesItems) &&
				Objects.equals(error, that.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(destinatariosSeriesItems, error);
	}

	@Override
	public String toString() {
		return "DestinatariosSeriesDTO{" +
				"destinatariosSeriesItems=" + destinatariosSeriesItems +
				", error=" + error +
				'}';
	}

}
