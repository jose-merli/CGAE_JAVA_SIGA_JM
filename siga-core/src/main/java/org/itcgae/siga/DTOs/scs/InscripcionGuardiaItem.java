package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class InscripcionGuardiaItem implements Comparable<InscripcionGuardiaItem>{
	
	private String numeroGrupo;
	private String idGrupoGuardiaColegiado;
	private String orden;
	private String nColegiado;
	private String fechaValidacion;
	private String fechaBaja;
	private String compensaciones;
	private String saltos;
	private String apellido1;
	private String apellido2;
	private String nombre;
	private String idPersona;
	private String ordenCola;
	private String idTurno;
	private String idGuardia;
	private String idGrupoGuardia;
	private Date fechaSuscripcion;
	private Date fechamodificacion;
	private Integer usumodificacion;
	private String observacionessuscripcion;
	private String observacionesbaja;
	private Date fechasolicitudbaja;
	private Date fechavalidacion;
	private String observacionesvalidacion;
	private Date fechadenegacion;
	private String observacionesdenegacion;
	private Date fechabaja;
	private String observacionesvalbaja;
	// Atributos calculados
	private String	estado;
	private String idInstitucion;
	private Integer ultimoCola;
	
	//Atributos Auxiliares
	private String fechaIni;
	private String fechaFin;
	private int posicion;
	
	public InscripcionGuardiaItem(String numeroGrupo, String idGrupoGuardiaColegiado, String orden, String nColegiado,
			String fechaValidacion, Date fechabaja, String compensaciones, String saltos, String apellido1,
			String apellido2, String nombre, String idPersona, String ordenCola, String idTurno, String idGuardia,
			String idGrupoGuardia, Date fechaSuscripcion, Date fechamodificacion, Integer usumodificacion,
			String observacionessuscripcion, String observacionesbaja, Date fechasolicitudbaja, Date fechavalidacion2,
			String observacionesvalidacion, Date fechadenegacion, String observacionesdenegacion,
			String observacionesvalbaja) {
		super();
		this.numeroGrupo = numeroGrupo;
		this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
		this.orden = orden;
		this.nColegiado = nColegiado;
		this.fechaValidacion = fechaValidacion;
		this.fechabaja = fechabaja;
		this.compensaciones = compensaciones;
		this.saltos = saltos;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre = nombre;
		this.idPersona = idPersona;
		this.ordenCola = ordenCola;
		this.idTurno = idTurno;
		this.idGuardia = idGuardia;
		this.idGrupoGuardia = idGrupoGuardia;
		this.fechaSuscripcion = fechaSuscripcion;
		this.fechamodificacion = fechamodificacion;
		this.usumodificacion = usumodificacion;
		this.observacionessuscripcion = observacionessuscripcion;
		this.observacionesbaja = observacionesbaja;
		this.fechasolicitudbaja = fechasolicitudbaja;
		fechavalidacion = fechavalidacion2;
		this.observacionesvalidacion = observacionesvalidacion;
		this.fechadenegacion = fechadenegacion;
		this.observacionesdenegacion = observacionesdenegacion;
		this.observacionesvalbaja = observacionesvalbaja;
	}
	
	public InscripcionGuardiaItem( String idInstitucion, String idTurno, String idGuardia,String idPersona, Date fechaSuscripcion,
			String idGrupoGuardia) {
		super();
		this.idPersona = idPersona;
		this.idTurno = idTurno;
		this.idGuardia = idGuardia;
		this.idGrupoGuardia = idGrupoGuardia;
		this.fechaSuscripcion = fechaSuscripcion;
		this.idInstitucion = idInstitucion;
	
	}
	
	public InscripcionGuardiaItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdGrupoGuardia() {
		return idGrupoGuardia;
	}
	public void setIdGrupoGuardia(String idGrupoguardia) {
		this.idGrupoGuardia = idGrupoguardia;
	}
	public Date getFechaSuscripcion() {
		return fechaSuscripcion;
	}
	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}
	
	public String getIdturno() {
		return idTurno;
	}
	public void setIdturno(String idturno) {
		this.idTurno = idturno;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getOrdenCola() {
		return ordenCola;
	}
	public void setOrdenCola(String ordenCola) {
		this.ordenCola = ordenCola;
	}
	public String getNumeroGrupo() {
		return numeroGrupo;
	}
	public void setNumeroGrupo(String numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}
	public String getIdGrupoGuardiaColegiado() {
		return idGrupoGuardiaColegiado;
	}
	public void setIdGrupoGuardiaColegiado(String idGrupoGuardiaColegiado) {
		this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getnColegiado() {
		return nColegiado;
	}
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	public String getFechaValidacion() {
		return fechaValidacion;
	}
	public void setFechaValidacion(String fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}
	public String getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getCompensaciones() {
		return compensaciones;
	}
	public void setCompensaciones(String compensaciones) {
		this.compensaciones = compensaciones;
	}
	public String getSaltos() {
		return saltos;
	}
	public void setSaltos(String saltos) {
		this.saltos = saltos;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the fechamodificacion
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * @param fechamodificacion the fechamodificacion to set
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * @return the usumodificacion
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * @param usumodificacion the usumodificacion to set
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * @return the observacionessuscripcion
	 */
	public String getObservacionessuscripcion() {
		return observacionessuscripcion;
	}

	/**
	 * @param observacionessuscripcion the observacionessuscripcion to set
	 */
	public void setObservacionessuscripcion(String observacionessuscripcion) {
		this.observacionessuscripcion = observacionessuscripcion;
	}

	/**
	 * @return the observacionesbaja
	 */
	public String getObservacionesbaja() {
		return observacionesbaja;
	}

	/**
	 * @param observacionesbaja the observacionesbaja to set
	 */
	public void setObservacionesbaja(String observacionesbaja) {
		this.observacionesbaja = observacionesbaja;
	}

	/**
	 * @return the fechasolicitudbaja
	 */
	public Date getFechasolicitudbaja() {
		return fechasolicitudbaja;
	}

	/**
	 * @param fechasolicitudbaja the fechasolicitudbaja to set
	 */
	public void setFechasolicitudbaja(Date fechasolicitudbaja) {
		this.fechasolicitudbaja = fechasolicitudbaja;
	}

	/**
	 * @return the fechavalidacion
	 */
	public Date getFechavalidacion() {
		return fechavalidacion;
	}

	/**
	 * @param fechavalidacion the fechavalidacion to set
	 */
	public void setFechavalidacion(Date fechavalidacion) {
		this.fechavalidacion = fechavalidacion;
	}

	/**
	 * @return the observacionesvalidacion
	 */
	public String getObservacionesvalidacion() {
		return observacionesvalidacion;
	}

	/**
	 * @param observacionesvalidacion the observacionesvalidacion to set
	 */
	public void setObservacionesvalidacion(String observacionesvalidacion) {
		this.observacionesvalidacion = observacionesvalidacion;
	}

	/**
	 * @return the fechadenegacion
	 */
	public Date getFechadenegacion() {
		return fechadenegacion;
	}

	/**
	 * @param fechadenegacion the fechadenegacion to set
	 */
	public void setFechadenegacion(Date fechadenegacion) {
		this.fechadenegacion = fechadenegacion;
	}

	/**
	 * @return the observacionesdenegacion
	 */
	public String getObservacionesdenegacion() {
		return observacionesdenegacion;
	}

	/**
	 * @param observacionesdenegacion the observacionesdenegacion to set
	 */
	public void setObservacionesdenegacion(String observacionesdenegacion) {
		this.observacionesdenegacion = observacionesdenegacion;
	}
	

	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	/**
	 * @return the observacionesvalbaja
	 */
	public String getObservacionesvalbaja() {
		return observacionesvalbaja;
	}

	/**
	 * @param observacionesvalbaja the observacionesvalbaja to set
	 */
	public void setObservacionesvalbaja(String observacionesvalbaja) {
		this.observacionesvalbaja = observacionesvalbaja;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	public Integer getUltimoCola() {
		return ultimoCola;
	}

	public void setUltimoCola(Integer ultimoCola) {
		this.ultimoCola = ultimoCola;
	}

	public int compareTo(InscripcionGuardiaItem o) {
		return Integer.parseInt(numeroGrupo) - Integer.parseInt(o.numeroGrupo);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result + ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + ((compensaciones == null) ? 0 : compensaciones.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((fechaSuscripcion == null) ? 0 : fechaSuscripcion.hashCode());
		result = prime * result + ((fechaValidacion == null) ? 0 : fechaValidacion.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((fechadenegacion == null) ? 0 : fechadenegacion.hashCode());
		result = prime * result + ((fechamodificacion == null) ? 0 : fechamodificacion.hashCode());
		result = prime * result + ((fechasolicitudbaja == null) ? 0 : fechasolicitudbaja.hashCode());
		result = prime * result + ((fechavalidacion == null) ? 0 : fechavalidacion.hashCode());
		result = prime * result + ((idGrupoGuardia == null) ? 0 : idGrupoGuardia.hashCode());
		result = prime * result + ((idGrupoGuardiaColegiado == null) ? 0 : idGrupoGuardiaColegiado.hashCode());
		result = prime * result + ((idGuardia == null) ? 0 : idGuardia.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idTurno == null) ? 0 : idTurno.hashCode());
		result = prime * result + ((nColegiado == null) ? 0 : nColegiado.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroGrupo == null) ? 0 : numeroGrupo.hashCode());
		result = prime * result + ((observacionesbaja == null) ? 0 : observacionesbaja.hashCode());
		result = prime * result + ((observacionesdenegacion == null) ? 0 : observacionesdenegacion.hashCode());
		result = prime * result + ((observacionessuscripcion == null) ? 0 : observacionessuscripcion.hashCode());
		result = prime * result + ((observacionesvalbaja == null) ? 0 : observacionesvalbaja.hashCode());
		result = prime * result + ((observacionesvalidacion == null) ? 0 : observacionesvalidacion.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((ordenCola == null) ? 0 : ordenCola.hashCode());
		result = prime * result + ((saltos == null) ? 0 : saltos.hashCode());
		result = prime * result + ((ultimoCola == null) ? 0 : ultimoCola.hashCode());
		result = prime * result + ((usumodificacion == null) ? 0 : usumodificacion.hashCode());
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
		InscripcionGuardiaItem other = (InscripcionGuardiaItem) obj;
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		if (compensaciones == null) {
			if (other.compensaciones != null)
				return false;
		} else if (!compensaciones.equals(other.compensaciones))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (fechaSuscripcion == null) {
			if (other.fechaSuscripcion != null)
				return false;
		} else if (!fechaSuscripcion.equals(other.fechaSuscripcion))
			return false;
		if (fechaValidacion == null) {
			if (other.fechaValidacion != null)
				return false;
		} else if (!fechaValidacion.equals(other.fechaValidacion))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (fechadenegacion == null) {
			if (other.fechadenegacion != null)
				return false;
		} else if (!fechadenegacion.equals(other.fechadenegacion))
			return false;
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (fechasolicitudbaja == null) {
			if (other.fechasolicitudbaja != null)
				return false;
		} else if (!fechasolicitudbaja.equals(other.fechasolicitudbaja))
			return false;
		if (fechavalidacion == null) {
			if (other.fechavalidacion != null)
				return false;
		} else if (!fechavalidacion.equals(other.fechavalidacion))
			return false;
		if (idGrupoGuardia == null) {
			if (other.idGrupoGuardia != null)
				return false;
		} else if (!idGrupoGuardia.equals(other.idGrupoGuardia))
			return false;
		if (idGrupoGuardiaColegiado == null) {
			if (other.idGrupoGuardiaColegiado != null)
				return false;
		} else if (!idGrupoGuardiaColegiado.equals(other.idGrupoGuardiaColegiado))
			return false;
		if (idGuardia == null) {
			if (other.idGuardia != null)
				return false;
		} else if (!idGuardia.equals(other.idGuardia))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idTurno == null) {
			if (other.idTurno != null)
				return false;
		} else if (!idTurno.equals(other.idTurno))
			return false;
		if (nColegiado == null) {
			if (other.nColegiado != null)
				return false;
		} else if (!nColegiado.equals(other.nColegiado))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroGrupo == null) {
			if (other.numeroGrupo != null)
				return false;
		} else if (!numeroGrupo.equals(other.numeroGrupo))
			return false;
		if (observacionesbaja == null) {
			if (other.observacionesbaja != null)
				return false;
		} else if (!observacionesbaja.equals(other.observacionesbaja))
			return false;
		if (observacionesdenegacion == null) {
			if (other.observacionesdenegacion != null)
				return false;
		} else if (!observacionesdenegacion.equals(other.observacionesdenegacion))
			return false;
		if (observacionessuscripcion == null) {
			if (other.observacionessuscripcion != null)
				return false;
		} else if (!observacionessuscripcion.equals(other.observacionessuscripcion))
			return false;
		if (observacionesvalbaja == null) {
			if (other.observacionesvalbaja != null)
				return false;
		} else if (!observacionesvalbaja.equals(other.observacionesvalbaja))
			return false;
		if (observacionesvalidacion == null) {
			if (other.observacionesvalidacion != null)
				return false;
		} else if (!observacionesvalidacion.equals(other.observacionesvalidacion))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (ordenCola == null) {
			if (other.ordenCola != null)
				return false;
		} else if (!ordenCola.equals(other.ordenCola))
			return false;
		if (saltos == null) {
			if (other.saltos != null)
				return false;
		} else if (!saltos.equals(other.saltos))
			return false;
		if (ultimoCola == null) {
			if (other.ultimoCola != null)
				return false;
		} else if (!ultimoCola.equals(other.ultimoCola))
			return false;
		if (usumodificacion == null) {
			if (other.usumodificacion != null)
				return false;
		} else if (!usumodificacion.equals(other.usumodificacion))
			return false;
		return true;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}


}
