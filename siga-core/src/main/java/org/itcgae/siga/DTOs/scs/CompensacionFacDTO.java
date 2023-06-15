package org.itcgae.siga.DTOs.scs;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.List;

@Data
public class CompensacionFacDTO {

    private List<CompensacionFacItem> compensaciones;
    private Error error = null;
}
