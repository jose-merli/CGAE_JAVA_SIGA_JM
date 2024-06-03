package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class ModulosItem {

	private String idInstitucion;
	private String codigo;
	private String importe;
	private String nombre;
	private String idProcedimiento;
	private String procedimientos;
	private String precio;
	private String idjurisdiccion;
	private Date fechabaja;
	private String complemento;
	private String vigente;
	private String orden;
	private String codigoext;
	private String permitiraniadirletrado;
	private Date fechadesdevigor;
	private Date fechahastavigor;
	private String observaciones;
	private boolean historico;
	private String jurisdiccionDes;
	private boolean verSoloAlta;
	private boolean usado;
    private String pretensiones; // Nueva propiedad
    private String juzgados; // Nueva propiedad
    private String acreditaciones;
    private int numJuzgados;
    private int numAcreditaciones;

	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getImporte() {
		return importe;
	}
	
	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdProcedimiento() {
		return idProcedimiento;
	}
	
	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
	
	public String getProcedimientos() {
		return procedimientos;
	}
	
	public void setProcedimientos(String procedimientos) {
		this.procedimientos = procedimientos;
	}
	
	public String getPrecio() {
		return precio;
	}
	
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	public String getIdjurisdiccion() {
		return idjurisdiccion;
	}
	
	public void setIdjurisdiccion(String idjurisdiccion) {
		this.idjurisdiccion = idjurisdiccion;
	}
	
	public Date getFechabaja() {
		return fechabaja;
	}
	
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getVigente() {
		return vigente;
	}
	
	public void setVigente(String vigente) {
		this.vigente = vigente;
	}
	
	public String getOrden() {
		return orden;
	}
	
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public String getCodigoext() {
		return codigoext;
	}
	
	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}
	
	public String getPermitiraniadirletrado() {
		return permitiraniadirletrado;
	}
	
	public void setPermitiraniadirletrado(String permitiraniadirletrado) {
		this.permitiraniadirletrado = permitiraniadirletrado;
	}
	
	public Date getFechadesdevigor() {
		return fechadesdevigor;
	}
	
	public void setFechadesdevigor(Date fechadesdevigor) {
		this.fechadesdevigor = fechadesdevigor;
	}
	
	public Date getFechahastavigor() {
		return fechahastavigor;
	}
	
	public void setFechahastavigor(Date fechahastavigor) {
		this.fechahastavigor = fechahastavigor;
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public boolean isHistorico() {
		return historico;
	}
	
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
	public String getJurisdiccionDes() {
		return jurisdiccionDes;
	}
	
	public void setJurisdiccionDes(String jurisdiccionDes) {
		this.jurisdiccionDes = jurisdiccionDes;
	}
	
	public boolean isVerSoloAlta() {
		return verSoloAlta;
	}
	
	public void setVerSoloAlta(boolean verSoloAlta) {
		this.verSoloAlta = verSoloAlta;
	}
	
	public boolean isUsado() {
		return usado;
	}
	
	public void setUsado(boolean usado) {
		this.usado = usado;
	}
	
    public String getPretensiones() {
        return pretensiones;
    }

    public void setPretensiones(String pretensiones) {
        this.pretensiones = pretensiones;
    }

    public String getJuzgados() {
        return juzgados;
    }

    public void setJuzgados(String juzgados) {
        this.juzgados = juzgados;
    }
    
    public String getAcreditaciones()
    {
    	return acreditaciones;
    }
    
    public void setAcreditaciones(String acreditaciones)
    {
    	this.acreditaciones = acreditaciones;
    }
    
    public int getNumJuzgados() {
		return numJuzgados;
	}

	public void setNumJuzgados(int numJuzgados) {
		this.numJuzgados = numJuzgados;
	}

	public int getNumAcreditaciones() {
		return numAcreditaciones;
	}

	public void setNumAcreditaciones(int numAcreditaciones) {
		this.numAcreditaciones = numAcreditaciones;
	}
}