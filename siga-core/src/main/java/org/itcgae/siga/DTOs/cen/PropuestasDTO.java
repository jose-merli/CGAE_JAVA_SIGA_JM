package org.itcgae.siga.DTOs.cen;

import java.util.Calendar;

public class PropuestasDTO {
	
	private int tipoIdentificador;
	private String identificador;
	private Calendar fechaNacimiento;
	private int Sexo;
	private int tipoPropuesta;
	
	
	
	public int getTipoIdentificador() {
		return tipoIdentificador;
	}

	public void setTipoIdentificador(int tipoIdentificador) {
		this.tipoIdentificador = tipoIdentificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getSexo() {
		return Sexo;
	}

	public void setSexo(int sexo) {
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
		result = prime * result + Sexo;
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + tipoIdentificador;
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
		if (Sexo != other.Sexo)
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
		if (tipoIdentificador != other.tipoIdentificador)
			return false;
		if (tipoPropuesta != other.tipoPropuesta)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "PropuestasDTO [tipoIdentificador=" + tipoIdentificador + ", identificador=" + identificador
				+ ", fechaNacimiento=" + fechaNacimiento + ", Sexo=" + Sexo + ", tipoPropuesta=" + tipoPropuesta + "]";
	}
	
	

}
