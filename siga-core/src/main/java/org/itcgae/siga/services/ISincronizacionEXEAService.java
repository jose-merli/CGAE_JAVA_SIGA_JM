package org.itcgae.siga.services;


import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionRequestDocument;
import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionResponseDocument;

public interface ISincronizacionEXEAService {
    public ObtenerNumColegiacionResponseDocument getNumColegiacion(ObtenerNumColegiacionRequestDocument request, String ipCliente);
}
