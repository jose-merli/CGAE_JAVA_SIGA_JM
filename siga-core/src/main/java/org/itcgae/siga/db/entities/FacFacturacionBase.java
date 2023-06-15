package org.itcgae.siga.db.entities;

public class FacFacturacionBase {
	
	private Short idInstitucion;
    private Long idSerieFacturacion;
    private Long idProgramacion;
    private Integer idUsuarioModificacion;
    private String idIdioma;
    
    private String codRetorno;
    private String datosError;
    
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
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
	public Integer getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}
	public void setIdUsuarioModificacion(Integer idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}
	public String getCodRetorno() {
		return codRetorno;
	}
	public void setCodRetorno(String codRetorno) {
		this.codRetorno = codRetorno;
	}
	public String getDatosError() {
		return datosError;
	}
	public void setDatosError(String datosError) {
		this.datosError = datosError;
	}
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
    
    
	
    
    


}