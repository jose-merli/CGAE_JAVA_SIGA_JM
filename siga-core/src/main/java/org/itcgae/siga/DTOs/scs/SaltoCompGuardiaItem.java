
package org.itcgae.siga.DTOs.scs;

public class SaltoCompGuardiaItem {

	private String idGuardia;
	private String idTurno;
	private String turno;
	private String guardia;
	private String idSaltosTurno;
	private String colegiadoGrupo;
	private String letrado;
	private String saltoCompensacion;
	private String fecha;
	private String fechaDesde;
	private String fechaHasta;
	private String motivo;
	private String fechaUso;
	private String grupo;
	private boolean historico;
	
	
	
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
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
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getGuardia() {
		return guardia;
	}
	public void setGuardia(String guardia) {
		this.guardia = guardia;
	}
	public String getIdSaltosTurno() {
		return idSaltosTurno;
	}
	public void setIdSaltosTurno(String idSaltosTurno) {
		this.idSaltosTurno = idSaltosTurno;
	}
	public String getColegiadoGrupo() {
		return colegiadoGrupo;
	}
	public void setColegiadoGrupo(String colegiadoGrupo) {
		this.colegiadoGrupo = colegiadoGrupo;
	}
	public String getLetrado() {
		return letrado;
	}
	public void setLetrado(String letrado) {
		this.letrado = letrado;
	}
	public String getSaltoCompensacion() {
		return saltoCompensacion;
	}
	public void setSaltoCompensacion(String saltoCompensacion) {
		this.saltoCompensacion = saltoCompensacion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getFechaUso() {
		return fechaUso;
	}
	public void setFechaUso(String fechaUso) {
		this.fechaUso = fechaUso;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	
}
