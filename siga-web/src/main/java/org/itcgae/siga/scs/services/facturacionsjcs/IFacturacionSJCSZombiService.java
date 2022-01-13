package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.db.entities.FcsTrazaErrorEjecucion;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface IFacturacionSJCSZombiService {

    public FacturacionDTO deshacerTurnosOfi(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerGuardias(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerSOJ(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerEJG(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerRegularTurnosOfi(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerRegularGuardias(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerRegularSOJ(FcsTrazaErrorEjecucion facturacionItem);

    public FacturacionDTO deshacerRegularEJG(FcsTrazaErrorEjecucion facturacionItem);
}
