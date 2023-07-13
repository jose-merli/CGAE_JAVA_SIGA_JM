package org.itcgae.siga.DTOs.scs;

public class GuardiasCalendarioItem {

	 private String idinstitucion;
	 private String idturno;
	 private String idguardia;
	 private String idcalendarioguardias;
	 private String fechafin;
	 private String fechainicio;
	 private String observaciones;
	 private String fechamodificacion;
	 private String usumodificacion;
	 private String idpersona_ultimoanterior;
	 private String idgrupoguardia_ultimoanterior;
	 private String fechasusc_ultimoanterior;
	 private String idturnoprincipal;
	 private String idguardiaprincipal;
	 private String idcalendarioguardiasprincipal;
	 
	public GuardiasCalendarioItem(String idinstitucion, String idturno, String idguardia, String idcalendarioguardias,
			String fechafin, String fechainicio, String observaciones, String fechamodificacion, String usumodificacion,
			String idpersona_ultimoanterior, String idgrupoguardia_ultimoanterior, String fechasusc_ultimoanterior,
			String idturnoprincipal, String idguardiaprincipal, String idcalendarioguardiasprincipal) {
		super();
		this.idinstitucion = idinstitucion;
		this.idturno = idturno;
		this.idguardia = idguardia;
		this.idcalendarioguardias = idcalendarioguardias;
		this.fechafin = fechafin;
		this.fechainicio = fechainicio;
		this.observaciones = observaciones;
		this.fechamodificacion = fechamodificacion;
		this.usumodificacion = usumodificacion;
		this.idpersona_ultimoanterior = idpersona_ultimoanterior;
		this.idgrupoguardia_ultimoanterior = idgrupoguardia_ultimoanterior;
		this.fechasusc_ultimoanterior = fechasusc_ultimoanterior;
		this.idturnoprincipal = idturnoprincipal;
		this.idguardiaprincipal = idguardiaprincipal;
		this.idcalendarioguardiasprincipal = idcalendarioguardiasprincipal;
	}
	
	public GuardiasCalendarioItem(String idinstitucion, String idturno, String idguardia, String idcalendarioguardias,
			String fechainicio, String fechafin, String observaciones) {
		this.idinstitucion = idinstitucion;
		this.idturno = idturno;
		this.idguardia = idguardia;
		this.idcalendarioguardias = idcalendarioguardias;
		this.fechainicio = fechainicio;
		this.fechafin = fechafin;
		this.observaciones = observaciones;
	}
	
	public GuardiasCalendarioItem(String idinstitucion, String idturno, String idguardia, String idcalendarioguardias,
			String fechainicio, String fechafin, String observaciones, String fechamodificacion, String usumodificacion,
			String idturnoprincipal, String idguardiaprincipal, String idcalendarioguardiasprincipal) {
		
		this.idinstitucion = idinstitucion;
		this.idturno = idturno;
		this.idguardia = idguardia;
		this.idcalendarioguardias = idcalendarioguardias;
		this.fechafin = fechafin;
		this.fechainicio = fechainicio;
		this.observaciones = observaciones;
		this.fechamodificacion = fechamodificacion;
		this.usumodificacion = usumodificacion;
		this.idturnoprincipal = idturnoprincipal;
		this.idguardiaprincipal = idguardiaprincipal;
		this.idcalendarioguardiasprincipal = idcalendarioguardiasprincipal;
	}
	
	public GuardiasCalendarioItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public String getIdturno() {
		return idturno;
	}
	public void setIdturno(String idturno) {
		this.idturno = idturno;
	}
	public String getIdguardia() {
		return idguardia;
	}
	public void setIdguardia(String idguardia) {
		this.idguardia = idguardia;
	}
	public String getIdcalendarioguardias() {
		return idcalendarioguardias;
	}
	public void setIdcalendarioguardias(String idcalendarioguardias) {
		this.idcalendarioguardias = idcalendarioguardias;
	}
	public String getFechafin() {
		return fechafin;
	}
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}
	public String getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(String fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public String getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	public String getIdpersona_ultimoanterior() {
		return idpersona_ultimoanterior;
	}
	public void setIdpersona_ultimoanterior(String idpersona_ultimoanterior) {
		this.idpersona_ultimoanterior = idpersona_ultimoanterior;
	}
	public String getIdgrupoguardia_ultimoanterior() {
		return idgrupoguardia_ultimoanterior;
	}
	public void setIdgrupoguardia_ultimoanterior(String idgrupoguardia_ultimoanterior) {
		this.idgrupoguardia_ultimoanterior = idgrupoguardia_ultimoanterior;
	}
	public String getFechasusc_ultimoanterior() {
		return fechasusc_ultimoanterior;
	}
	public void setFechasusc_ultimoanterior(String fechasusc_ultimoanterior) {
		this.fechasusc_ultimoanterior = fechasusc_ultimoanterior;
	}
	public String getIdturnoprincipal() {
		return idturnoprincipal;
	}
	public void setIdturnoprincipal(String idturnoprincipal) {
		this.idturnoprincipal = idturnoprincipal;
	}
	public String getIdguardiaprincipal() {
		return idguardiaprincipal;
	}
	public void setIdguardiaprincipal(String idguardiaprincipal) {
		this.idguardiaprincipal = idguardiaprincipal;
	}
	public String getIdcalendarioguardiasprincipal() {
		return idcalendarioguardiasprincipal;
	}
	public void setIdcalendarioguardiasprincipal(String idcalendarioguardiasprincipal) {
		this.idcalendarioguardiasprincipal = idcalendarioguardiasprincipal;
	}

 
	
}
