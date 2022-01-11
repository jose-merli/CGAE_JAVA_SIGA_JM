package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class MovimientosVariosApliCerRequestDTO {

    private Date fechaDesde;
    private Date fechaHasta;

}
