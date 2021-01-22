package org.itcgae.siga.DTOs.cen;

import java.util.Date;

public class PropuestasDTO {
	
	private String tipoIdentificador;
	private String identificador;
	private Date fechaNacimiento;
	private String Sexo;
	private int tipoPropuesta;
	
	
	public String getTipoIdentificador() {
		return tipoIdentificador;
	}
	public void setTipoIdentificador(String tipoIdentificador) {
		this.tipoIdentificador = tipoIdentificador;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getSexo() {
		return Sexo;
	}
	public void setSexo(String sexo) {
		Sexo = sexo;
	}
	public int getTipoPropuesta() {
		return tipoPropuesta;
	}
	public void setTipoPropuesta(int tipoPropuesta) {
		this.tipoPropuesta = tipoPropuesta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Sexo == null) ? 0 : Sexo.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + ((tipoIdentificador == null) ? 0 : tipoIdentificador.hashCode());
		result = prime * result + tipoPropuesta;
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
		PropuestasDTO other = (PropuestasDTO) obj;
		if (Sexo == null) {
			if (other.Sexo != null)
				return false;
		} else if (!Sexo.equals(other.Sexo))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		if (tipoIdentificador == null) {
			if (other.tipoIdentificador != null)
				return false;
		} else if (!tipoIdentificador.equals(other.tipoIdentificador))
			return false;
		if (tipoPropuesta != other.tipoPropuesta)
			return false;
		return true;
	}
	
	

}
