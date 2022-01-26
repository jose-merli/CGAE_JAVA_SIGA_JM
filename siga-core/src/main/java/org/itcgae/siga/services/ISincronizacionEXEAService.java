package org.itcgae.siga.services;


import com.exea.sincronizacion.redabogacia.*;
import org.itcgae.siga.commons.utils.SigaExceptions;

public interface ISincronizacionEXEAService {
    public ObtenerNumColegiacionResponseDocument getNumColegiacion(ObtenerNumColegiacionRequestDocument request, String ipCliente);
    public AltaColegiadoResponseDocument aprobarAltaColegiado (AltaColegiadoRequestDocument request, String ipCliente) throws SigaExceptions;
    public AltaSancionResponseDocument altaSancion (AltaSancionRequestDocument request, String ipCliente) throws SigaExceptions;
    public UpdateEstadoExpedienteResponseDocument updateEstadoExpediente (UpdateEstadoExpedienteRequestDocument request, String ipCliente) throws SigaExceptions;
    public ActualizacionSancionResponseDocument actualizarSancion (ActualizacionSancionRequestDocument request, String ipCliente) throws SigaExceptions;
}
