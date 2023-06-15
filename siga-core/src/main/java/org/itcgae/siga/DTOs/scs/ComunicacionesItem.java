package org.itcgae.siga.DTOs.scs;

public class ComunicacionesItem {

	private String claseComunicacion;
	private String destinatario;
	private String fechaCreacion;
	private String fechaProgramacion;
	private String tipoEnvio;
	private String estado;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getClaseComunicacion() {
		return claseComunicacion;
	}
	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaProgramacion() {
		return fechaProgramacion;
	}
	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claseComunicacion == null) ? 0 : claseComunicacion.hashCode());
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((fechaProgramacion == null) ? 0 : fechaProgramacion.hashCode());
		result = prime * result + ((tipoEnvio == null) ? 0 : tipoEnvio.hashCode());
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
		ComunicacionesItem other = (ComunicacionesItem) obj;
		if (claseComunicacion == null) {
			if (other.claseComunicacion != null)
				return false;
		} else if (!claseComunicacion.equals(other.claseComunicacion))
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (fechaProgramacion == null) {
			if (other.fechaProgramacion != null)
				return false;
		} else if (!fechaProgramacion.equals(other.fechaProgramacion))
			return false;
		if (tipoEnvio == null) {
			if (other.tipoEnvio != null)
				return false;
		} else if (!tipoEnvio.equals(other.tipoEnvio))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ComunicacionesItem [claseComunicacion=" + claseComunicacion + ", destinatario=" + destinatario
				+ ", fechaCreacion=" + fechaCreacion + ", fechaProgramacion=" + fechaProgramacion + ", tipoEnvio="
				+ tipoEnvio + ", estado=" + estado + "]";
	}
}
