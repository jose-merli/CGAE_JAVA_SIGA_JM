package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;


public class ListaMonederoDTO {
    private List<MonederoDTO> monederoItems = new ArrayList<>();
    private Error error = null;
}
