package org.itcgae.siga.DTOs.scs;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.FcsCertificaciones;

import java.util.ArrayList;
import java.util.List;

@Data
public class CertificacionesDTO {

    private List<FcsCertificaciones> fcsCertificacionesList = new ArrayList<>();
    private Error error = null;
}
