package org.itcgae.siga.DTOs.scs;

public class FiltroAsistenciaItem {

	private String diaGuardia;
	private String idGuardia;
	private String idTurno;
	private String idTipoAsistenciaColegiado;
	private String idLetradoGuardia;
	private boolean isSustituto;
	private String idTipoAsistencia;
	
	public String getDiaGuardia() {
		return diaGuardia;
	}
	public void setDiaGuardia(String diaGuardia) {
		this.diaGuardia = diaGuardia;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdTipoAsistenciaColegiado() {
		return idTipoAsistenciaColegiado;
	}
	public void setIdTipoAsistenciaColegiado(String idTipoAsistenciaColegiado) {
		this.idTipoAsistenciaColegiado = idTipoAsistenciaColegiado;
	}
	public String getIdLetradoGuardia() {
		return idLetradoGuardia;
	}
	public void setIdLetradoGuardia(String idLetradoGuardia) {
		this.idLetradoGuardia = idLetradoGuardia;
	}
	public boolean isSustituto() {
		return isSustituto;
	}
	public void setSustituto(boolean isSustituto) {
		this.isSustituto = isSustituto;
	}
	public String getIdTipoAsistencia() {
		return idTipoAsistencia;
	}
	public void setIdTipoAsistencia(String idTipoAsistencia) {
		this.idTipoAsistencia = idTipoAsistencia;
	}
	@Override
	public String toString() {
		return "AsistenciaItem [diaGuardia=" + diaGuardia + ", idGuardia=" + idGuardia + ", idTurno=" + idTurno
				+ ", idTipoAsistencia=" + idTipoAsistenciaColegiado + ", idLetradoGuardia=" + idLetradoGuardia + ", isSustituto="
				+ isSustituto + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diaGuardia == null) ? 0 : diaGuardia.hashCode());
		result = prime * result + ((idGuardia == null) ? 0 : idGuardia.hashCode());
		result = prime * result + ((idLetradoGuardia == null) ? 0 : idLetradoGuardia.hashCode());
		result = prime * result + ((idTipoAsistenciaColegiado == null) ? 0 : idTipoAsistenciaColegiado.hashCode());
		result = prime * result + ((idTurno == null) ? 0 : idTurno.hashCode());
		result = prime * result + (isSustituto ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiltroAsistenciaItem other = (FiltroAsistenciaItem) obj;
		if (diaGuardia == null) {
			if (other.diaGuardia != null)
				return false;
		} else if (!diaGuardia.equals(other.diaGuardia))
			return false;
		if (idGuardia == null) {
			if (other.idGuardia != null)
				return false;
		} else if (!idGuardia.equals(other.idGuardia))
			return false;
		if (idLetradoGuardia == null) {
			if (other.idLetradoGuardia != null)
				return false;
		} else if (!idLetradoGuardia.equals(other.idLetradoGuardia))
			return false;
		if (idTipoAsistenciaColegiado == null) {
			if (other.idTipoAsistenciaColegiado != null)
				return false;
		} else if (!idTipoAsistenciaColegiado.equals(other.idTipoAsistenciaColegiado))
			return false;
		if (idTurno == null) {
			if (other.idTurno != null)
				return false;
		} else if (!idTurno.equals(other.idTurno))
			return false;
		if (isSustituto != other.isSustituto)
			return false;
		return true;
	}
	
}
