
package org.itcgae.siga.DTOs.scs;

import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItem;

public class SaltoCompGuardiaItem {

	private String idPersona;
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
	private List<SaltoCompGuardiaLetradoGrupoDTO> letradosGrupo;
	private String fechaAnulacion;
	private List<ComboItem> comboGuardia;
	private List<ComboItem> comboColegiados;
	private int numeroSaltosComp = 1;
	
	public int getNumeroSaltosComp() {
		return numeroSaltosComp;
	}
	
	public void setNumeroSaltosComp(int numeroSaltosComp) {
		this.numeroSaltosComp = numeroSaltosComp;
	}

	public List<ComboItem> getComboColegiados() {
		return comboColegiados;
	}

	public void setComboColegiados(List<ComboItem> comboColegiados) {
		this.comboColegiados = comboColegiados;
	}

	public List<ComboItem> getComboGuardia() {
		return comboGuardia;
	}

	public void setComboGuardia(List<ComboItem> comboGuardia) {
		this.comboGuardia = comboGuardia;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(String fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	/**
	 * @return the letradosGrupo
	 */
	public List<SaltoCompGuardiaLetradoGrupoDTO> getLetradosGrupo() {
		return letradosGrupo;
	}

	/**
	 * @param letradosGrupo the letradosGrupo to set
	 */
	public void setLetradosGrupo(List<SaltoCompGuardiaLetradoGrupoDTO> letradosGrupo) {
		this.letradosGrupo = letradosGrupo;
	}

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

	@Override
	public String toString() {
		return "SaltoCompGuardiaItem [idPersona=" + idPersona + ", idGuardia=" + idGuardia + ", idTurno=" + idTurno
				+ ", turno=" + turno + ", guardia=" + guardia + ", idSaltosTurno=" + idSaltosTurno + ", colegiadoGrupo="
				+ colegiadoGrupo + ", letrado=" + letrado + ", saltoCompensacion=" + saltoCompensacion + ", fecha="
				+ fecha + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", motivo=" + motivo
				+ ", fechaUso=" + fechaUso + ", grupo=" + grupo + ", historico=" + historico + ", letradosGrupo="
				+ letradosGrupo + ", fechaAnulacion=" + fechaAnulacion + ", comboGuardia=" + comboGuardia
				+ ", comboColegiados=" + comboColegiados + "]";
	}

}
