package org.itcgae.siga.services;


import com.exea.sincronizacion.redabogacia.AltaColegiadoRequestDocument;
import com.exea.sincronizacion.redabogacia.AltaColegiadoResponseDocument;
import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionRequestDocument;
import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionResponseDocument;
import org.itcgae.siga.commons.utils.SigaExceptions;

public interface ISincronizacionEXEAService {
    public ObtenerNumColegiacionResponseDocument getNumColegiacion(ObtenerNumColegiacionRequestDocument request, String ipCliente);
    public AltaColegiadoResponseDocument aprobarAltaColegiado (AltaColegiadoRequestDocument request, String ipCliente) throws SigaExceptions;
}
