package org.itcgae.siga.DTO.fac;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class MonederoDTO {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
    private String nifCif;
    private String nombreCompleto;
    private String descripcion;
    private float importeInicial;
    private float importeRestante;
    private float importeUsado;
    private int idPersona;
    private int idAnticipo;
}
