package org.itcgae.siga.fac.services;

import org.itcgae.siga.db.entities.AdmUsuarios;

public interface INuevoFicheroDevolucionesAsyncService {

    public boolean isAvailable();

    public void nuevoFicheroDevoluciones(String idDisqueteDevoluciones, String nombreFichero,
                                         String rutaOracle, String rutaServidor,
                                         Boolean conComision, AdmUsuarios usuario) throws Exception;

}
