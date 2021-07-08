package org.itcgae.siga.DTO.fac;

public class IdPeticionDTO {
	private int idpeticion = 0;

	public int getIdpeticion() {
		return idpeticion;
	}

	public void setIdpeticion(int idpeticion) {
		this.idpeticion = idpeticion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idpeticion;
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
		IdPeticionDTO other = (IdPeticionDTO) obj;
		if (idpeticion != other.idpeticion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IdPeticionDTO [idpeticion=" + idpeticion + "]";
	}
	

}
