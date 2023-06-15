package org.itcgae.siga.DTO.fac;

import java.util.List;

public class FichaMonederoItem {

	//TARJETA DATOS GENERALES
    private String idInstitucion;
    private Long idPersona;
    private String nombre;
    private String apellidos;
    private String idtipoidentificacion;
    private String nif;
    private String nColegiado;

    //TARJETA MOVIMIENTOS
    private List<ListaMovimientosMonederoItem> movimientos;
    
    //TARJETA SERVICIOS
    private List<ListaServiciosMonederoItem> servicios;

    private Short idAnticipo; //Identificador del monedero
    private String anioAnticipo;

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getIdtipoidentificacion() {
		return idtipoidentificacion;
	}

	public void setIdtipoidentificacion(String idtipoidentificacion) {
		this.idtipoidentificacion = idtipoidentificacion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public List<ListaMovimientosMonederoItem> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<ListaMovimientosMonederoItem> movimientos) {
		this.movimientos = movimientos;
	}

	public List<ListaServiciosMonederoItem> getServicios() {
		return servicios;
	}

	public void setServicios(List<ListaServiciosMonederoItem> servicios) {
		this.servicios = servicios;
	}

	public Short getIdAnticipo() {
		return idAnticipo;
	}

	public void setIdAnticipo(Short idAnticipo) {
		this.idAnticipo = idAnticipo;
	}

	public String getAnioAnticipo() {
		return anioAnticipo;
	}

	public void setAnioAnticipo(String anioAnticipo) {
		this.anioAnticipo = anioAnticipo;
	}

	public String getnColegiado() {
		return nColegiado;
	}

	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
}
