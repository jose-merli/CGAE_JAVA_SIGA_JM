package org.itcgae.siga.DTOs.scs;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.FcsRetencionesJudiciales;

@Data
public class RetencionDTO {
    private RetencionJudicialItem retencion;
    private Error error = null;
}
