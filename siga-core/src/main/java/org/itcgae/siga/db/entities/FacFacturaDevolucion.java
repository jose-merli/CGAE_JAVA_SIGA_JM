package org.itcgae.siga.db.entities;

import lombok.Data;

@Data
public class FacFacturaDevolucion {
	
	private Short idInstitucion;
	private String listaFacturas;
	private String fechaDevolucion;
    private String idIdioma;
    private Integer usuModificacion;

    private String codretorno;
    private String datoserror;
    private String listaIdDisquetesDevolucion;
}