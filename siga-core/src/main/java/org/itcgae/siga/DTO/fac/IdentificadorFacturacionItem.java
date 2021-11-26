package org.itcgae.siga.DTO.fac;

public abstract class IdentificadorFacturacionItem {
    private Long idSerieFacturacion;
    private Long idProgramacion;
    
	public Long getIdSerieFacturacion() {
		return idSerieFacturacion;
	}
	public void setIdSerieFacturacion(Long idSerieFacturacion) {
		this.idSerieFacturacion = idSerieFacturacion;
	}
	public Long getIdProgramacion() {
		return idProgramacion;
	}
	public void setIdProgramacion(Long idProgramacion) {
		this.idProgramacion = idProgramacion;
	}
    
	
    
}
