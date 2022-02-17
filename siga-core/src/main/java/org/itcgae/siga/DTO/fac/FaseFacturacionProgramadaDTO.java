package org.itcgae.siga.DTO.fac;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

@Data
public class FaseFacturacionProgramadaDTO {

    private List<FaseFacturacionProgramadaItem> faseFacturacionProgramadaItemList = new ArrayList<>();
    private Error error = null;

}
