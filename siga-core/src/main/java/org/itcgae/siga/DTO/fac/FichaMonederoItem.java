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

    //TARJETA MOVIMIENTOS
    private List<ListaMovimientosMonederoItem> movimientos;

    private Short idLinea; //Identificador del monedero
    private String anioLinea;

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

	public Short getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Short idLinea) {
		this.idLinea = idLinea;
	}

	public String getAnioLinea() {
		return anioLinea;
	}

	public void setAnioLinea(String anioLinea) {
		this.anioLinea = anioLinea;
	}
}
