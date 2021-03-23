package org.itcgae.siga.DTOs.scs;

public class SaltoCompGuardiaLetradoGrupoDTO {

	private String numeroGrupo;
	private String colegiado;
	private String grupo;
	private String colegiadoGrupo;
	private String letrado;

	public String getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(String numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}

	public String getColegiado() {
		return colegiado;
	}

	public void setColegiado(String colegiado) {
		this.colegiado = colegiado;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
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

	@Override
	public String toString() {
		return "SaltoCompGuardiaLetrado [numeroGrupo=" + numeroGrupo + ", colegiado=" + colegiado + ", grupo=" + grupo
				+ ", colegiadoGrupo=" + colegiadoGrupo + ", letrado=" + letrado + "]";
	}

}
