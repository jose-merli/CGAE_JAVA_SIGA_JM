package org.itcgae.siga.db.entities;

import lombok.Data;

import java.util.Date;

@Data
public class FacFacturaDevolucion {
	
	private String idInstitucion;
	private String listaFacturas;
	private Date fechaDevolucion;
    private String idIdioma;
    private String usuModificacion;

    private String codretorno;
    private String datoserror;
    private String listaIdDisquetesDevolucion;
}