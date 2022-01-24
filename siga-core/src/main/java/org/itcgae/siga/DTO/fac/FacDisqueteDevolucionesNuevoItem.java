package org.itcgae.siga.DTO.fac;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FacDisqueteDevolucionesNuevoItem {

    private MultipartFile uploadFile;
    private Boolean conComision;

}
