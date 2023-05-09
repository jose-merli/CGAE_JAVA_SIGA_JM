package org.itcgae.siga.DTOs.scs;

import java.util.List;
import java.util.Objects;

public class TarjetaAsistenciaResponseItem implements Comparable<TarjetaAsistenciaResponseItem> {
	
	private String anio;
	private String numero;
	private String anioNumero;
	private String asistido;
	private String idDelito;
	private String observaciones;
	private String incidencias;
	private String ejgNumero;
	private String ejgAnio;
	private String ejgAnioNumero;
	private String idTipoEjg;
	private List<ActuacionAsistenciaItem> actuaciones;
	private String nombre, sexo, apellido1, apellido2, nif;
	private FiltroAsistenciaItem filtro;
	private String descripcionGuardia;
	private String idGuardia;
	private String idTurno;
	private String estado;
	private String fechaSolicitud;
	private String fechaCierre;
	private String fechaAsistencia;
	private String idLetradoGuardia;
	private String idLetradoManual;
	private String idTipoAsistenciaColegio;
	private String idSolicitudCentralita;
	private String fechaEstado;
	private String descripcionEstado;
	private String descripcionTurno;
	private String descripcionTipoAsistenciaColegio;
	private String idPersonaJg;
	private String numContrarios;
	private String numeroColegiado;
	private String nombreColegiado;
	private String fechaGuardia;
	private String validada;
	private String numeroActuaciones;
	private ListaContrarioJusticiableItem primerContrario;
	private RelacionesItem primeraRelacion;
	private String nig, juzgado, comisaria, numProcedimiento, idProcedimiento, numDiligencia;
	private List<String> delitos;
	private String numDocumentos;
	private String numValidadas;
	private String numJustificadas;
	private String numFacturadas;
	private boolean diaDespuesDisabled;
	private String guardiaRequeridaValidacion;
	private String idJuzgado;
	private String idInstitucion;
	private String isSustituto;


	public String getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(String incidencias) {
		this.incidencias = incidencias;
	}
	
	public String getIdLetradoManual() {
		return idLetradoManual;
	}

	public void setIdLetradoManual(String idLetradoManual) {
		this.idLetradoManual = idLetradoManual;
	}
	
	public String getIsSustituto() {
		return isSustituto;
	}

	public void setIsSustituto(String isSustituto) {
		this.isSustituto = isSustituto;
	}

	public String getGuardiaRequeridaValidacion() {
		return guardiaRequeridaValidacion;
	}

	public void setGuardiaRequeridaValidacion(String guardiaRequeridaValidacion) {
		this.guardiaRequeridaValidacion = guardiaRequeridaValidacion;
	}

	public String getIdTipoEjg() {
		return idTipoEjg;
	}

	public void setIdTipoEjg(String idTipoEjg) {
		this.idTipoEjg = idTipoEjg;
	}

	public boolean isDiaDespuesDisabled() {
		return diaDespuesDisabled;
	}

	public void setDiaDespuesDisabled(boolean diaDespuesDisabled) {
		this.diaDespuesDisabled = diaDespuesDisabled;
	}

	public String getNumValidadas() {
		return numValidadas;
	}

	public void setNumValidadas(String numValidadas) {
		this.numValidadas = numValidadas;
	}

	public String getNumJustificadas() {
		return numJustificadas;
	}

	public void setNumJustificadas(String numJustificadas) {
		this.numJustificadas = numJustificadas;
	}

	public String getNumFacturadas() {
		return numFacturadas;
	}

	public void setNumFacturadas(String numFacturadas) {
		this.numFacturadas = numFacturadas;
	}

	public String getNumDocumentos() {
		return numDocumentos;
	}

	public void setNumDocumentos(String numDocumentos) {
		this.numDocumentos = numDocumentos;
	}

	/**
	 * @return the numeroActuaciones
	 */
	public String getNumeroActuaciones() {
		return numeroActuaciones;
	}
	/**
	 * @param numeroActuaciones the numeroActuaciones to set
	 */
	public void setNumeroActuaciones(String numeroActuaciones) {
		this.numeroActuaciones = numeroActuaciones;
	}
	/**
	 * @return the descripcionGuardia
	 */
	public String getDescripcionGuardia() {
		return descripcionGuardia;
	}
	/**
	 * @param descripcionGuardia the descripcionGuardia to set
	 */
	public void setDescripcionGuardia(String descripcionGuardia) {
		this.descripcionGuardia = descripcionGuardia;
	}
	/**
	 * @return the numeroColegiado
	 */
	public String getNumeroColegiado() {
		return numeroColegiado;
	}
	/**
	 * @param numeroColegiado the numeroColegiado to set
	 */
	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}
	/**
	 * @return the nombreColegiado
	 */
	public String getNombreColegiado() {
		return nombreColegiado;
	}
	/**
	 * @param nombreColegiado the nombreColegiado to set
	 */
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}
	/**
	 * @return the fechaGuardia
	 */
	public String getFechaGuardia() {
		return fechaGuardia;
	}
	/**
	 * @param fechaGuardia the fechaGuardia to set
	 */
	public void setFechaGuardia(String fechaGuardia) {
		this.fechaGuardia = fechaGuardia;
	}
	
	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}
	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return the anioNumero
	 */
	public String getAnioNumero() {
		return anioNumero;
	}
	/**
	 * @param anioNumero the anioNumero to set
	 */
	public void setAnioNumero(String anioNumero) {
		this.anioNumero = anioNumero;
	}
	/**
	 * @return the asistido
	 */
	public String getAsistido() {
		return asistido;
	}
	/**
	 * @param asistido the asistido to set
	 */
	public void setAsistido(String asistido) {
		this.asistido = asistido;
	}
	/**
	 * @return the ejgNumero
	 */
	public String getEjgNumero() {
		return ejgNumero;
	}
	/**
	 * @param ejgNumero the ejgNumero to set
	 */
	public void setEjgNumero(String ejgNumero) {
		this.ejgNumero = ejgNumero;
	}
	/**
	 * @return the ejgAnio
	 */
	public String getEjgAnio() {
		return ejgAnio;
	}
	/**
	 * @param ejgAnio the ejgAnio to set
	 */
	public void setEjgAnio(String ejgAnio) {
		this.ejgAnio = ejgAnio;
	}
	/**
	 * @return the ejgAnioNumero
	 */
	public String getEjgAnioNumero() {
		return ejgAnioNumero;
	}
	/**
	 * @param ejgAnioNumero the ejgAnioNumero to set
	 */
	public void setEjgAnioNumero(String ejgAnioNumero) {
		this.ejgAnioNumero = ejgAnioNumero;
	}

	/**
	 * @return the actuaciones
	 */
	public List<ActuacionAsistenciaItem> getActuaciones() {
		return actuaciones;
	}
	/**
	 * @param actuaciones the actuaciones to set
	 */
	public void setActuaciones(List<ActuacionAsistenciaItem> actuaciones) {
		this.actuaciones = actuaciones;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}
	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	/**
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * @return the idDelito
	 */
	public String getIdDelito() {
		return idDelito;
	}
	/**
	 * @param idDelito the idDelito to set
	 */
	public void setIdDelito(String idDelito) {
		this.idDelito = idDelito;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the filtro
	 */
	public FiltroAsistenciaItem getFiltro() {
		return filtro;
	}
	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(FiltroAsistenciaItem filtro) {
		this.filtro = filtro;
	}
	/**
	 * @return the idGuardia
	 */
	public String getIdGuardia() {
		return idGuardia;
	}
	/**
	 * @param idGuardia the idGuardia to set
	 */
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	/**
	 * @return the idTurno
	 */
	public String getIdTurno() {
		return idTurno;
	}
	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaSolicitud
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	/**
	 * @return the fechaCierre
	 */
	public String getFechaCierre() {
		return fechaCierre;
	}
	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	/**
	 * @return the fechaAsistencia
	 */
	public String getFechaAsistencia() {
		return fechaAsistencia;
	}
	/**
	 * @param fechaAsistencia the fechaAsistencia to set
	 */
	public void setFechaAsistencia(String fechaAsistencia) {
		this.fechaAsistencia = fechaAsistencia;
	}
	/**
	 * @return the idLetradoGuardia
	 */
	public String getIdLetradoGuardia() {
		return idLetradoGuardia;
	}
	/**
	 * @param idLetradoGuardia the idLetradoGuardia to set
	 */
	public void setIdLetradoGuardia(String idLetradoGuardia) {
		this.idLetradoGuardia = idLetradoGuardia;
	}
	/**
	 * @return the idTipoAsistenciaColegio
	 */
	public String getIdTipoAsistenciaColegio() {
		return idTipoAsistenciaColegio;
	}
	/**
	 * @param idTipoAsistenciaColegio the idTipoAsistenciaColegio to set
	 */
	public void setIdTipoAsistenciaColegio(String idTipoAsistenciaColegio) {
		this.idTipoAsistenciaColegio = idTipoAsistenciaColegio;
	}
	/**
	 * @return the idSolicitudCentralita
	 */
	public String getIdSolicitudCentralita() {
		return idSolicitudCentralita;
	}
	/**
	 * @param idSolicitudCentralita the idSolicitudCentralita to set
	 */
	public void setIdSolicitudCentralita(String idSolicitudCentralita) {
		this.idSolicitudCentralita = idSolicitudCentralita;
	}
	public String getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	/**
	 * @return the descripcionEstado
	 */
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	/**
	 * @param descripcionEstado the descripcionEstado to set
	 */
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	/**
	 * @return the descripcionTurno
	 */
	public String getDescripcionTurno() {
		return descripcionTurno;
	}
	/**
	 * @param descripcionTurno the descripcionTurno to set
	 */
	public void setDescripcionTurno(String descripcionTurno) {
		this.descripcionTurno = descripcionTurno;
	}
	/**
	 * @return the validada
	 */
	public String getValidada() {
		return validada;
	}
	/**
	 * @param validada the validada to set
	 */
	public void setValidada(String validada) {
		this.validada = validada;
	}

	public String getIdPersonaJg() {
		return idPersonaJg;
	}

	public void setIdPersonaJg(String idPersonaJg) {
		this.idPersonaJg = idPersonaJg;
	}

	public String getNumContrarios() {
		return numContrarios;
	}

	public void setNumContrarios(String numContrarios) {
		this.numContrarios = numContrarios;
	}
	/**
	 * @return the descripcionTipoAsistenciaColegio
	 */
	public String getDescripcionTipoAsistenciaColegio() {
		return descripcionTipoAsistenciaColegio;
	}
	/**
	 * @param descripcionTipoAsistenciaColegio the descripcionTipoAsistenciaColegio to set
	 */
	public void setDescripcionTipoAsistenciaColegio(String descripcionTipoAsistenciaColegio) {
		this.descripcionTipoAsistenciaColegio = descripcionTipoAsistenciaColegio;
	}

	public ListaContrarioJusticiableItem getPrimerContrario() {
		return primerContrario;
	}

	public void setPrimerContrario(ListaContrarioJusticiableItem primerContrario) {
		this.primerContrario = primerContrario;
	}

	public RelacionesItem getPrimeraRelacion() {
		return primeraRelacion;
	}

	public void setPrimeraRelacion(RelacionesItem primeraRelacion) {
		this.primeraRelacion = primeraRelacion;
	}

	public String getNig() {
		return nig;
	}

	public void setNig(String nig) {
		this.nig = nig;
	}

	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	public String getComisaria() {
		return comisaria;
	}

	public void setComisaria(String comisaria) {
		this.comisaria = comisaria;
	}

	public String getNumProcedimiento() {
		return numProcedimiento;
	}

	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}

	public String getIdProcedimiento() {
		return idProcedimiento;
	}

	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}

	public String getNumDiligencia() {
		return numDiligencia;
	}

	public void setNumDiligencia(String numDiligencia) {
		this.numDiligencia = numDiligencia;
	}

	public List<String> getDelitos() {
		return delitos;
	}

	public void setDelitos(List<String> delitos) {
		this.delitos = delitos;
	}

	public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actuaciones, anio, anioNumero, apellido1, apellido2, asistido,
				 ejgAnio, ejgAnioNumero, ejgNumero, nif, nombre, numero, sexo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaAsistenciaResponseItem other = (TarjetaAsistenciaResponseItem) obj;
		return Objects.equals(actuaciones, other.actuaciones) && Objects.equals(anio, other.anio)
				&& Objects.equals(anioNumero, other.anioNumero) && Objects.equals(apellido1, other.apellido1)
				&& Objects.equals(apellido2, other.apellido2) && Objects.equals(asistido, other.asistido)
				&& Objects.equals(ejgAnio, other.ejgAnio) && Objects.equals(ejgAnioNumero, other.ejgAnioNumero)
				&& Objects.equals(ejgNumero, other.ejgNumero)
				&& Objects.equals(nif, other.nif) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(numero, other.numero) && Objects.equals(sexo, other.sexo);
	}
	@Override
	public String toString() {
		return "TarjetaAsistenciaResponseItem [anio=" + anio + ", numero=" + numero + ", anioNumero=" + anioNumero
				+ ", asistido=" + asistido + ", ejgNumero="
				+ ejgNumero + ", ejgAnio=" + ejgAnio + ", ejgAnioNumero=" + ejgAnioNumero + ", actuaciones=" + actuaciones + ", nombre=" + nombre + ", sexo=" + sexo
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nif=" + nif + "]";
	}


	@Override
	public int compareTo(TarjetaAsistenciaResponseItem that) {
		if (this.getAnio() == null && that.getAnio() == null) {
			// pass
		} else if (this.getAnio() == null) {
			return -1;
		} else if (that.getAnio() == null) {
			return 1;
		} else {
			int anioComparison = Integer.valueOf(this.getAnio()).compareTo(Integer.valueOf(that.getAnio()));
			if (anioComparison != 0) {
				return anioComparison < 0 ? -1 : 1;
			}
		}

		if (this.getNumero() == null && that.getNumero() == null) {
			return 0;
		} else if (this.getNumero() == null) {
			return -1;
		} else if (that.getNumero() == null) {
			return 1;
		} else {
			return Integer.valueOf(this.getNumero()).compareTo(Integer.valueOf(that.getNumero()));
		}
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
}
