package org.itcgae.siga.commons.utils;

public interface ISIGALogging {

    void write(String s);

    void writeLimpio(String s);

    void writeLimpioError(Exception e, String idInstitucion, String idUsuario);

    void writeLogFactura(String tipo, String idPersona, String numeroFactura, String descripcion);

    void writeLogTraspasoFactura(String numFactura, String traspasada, String descripcionError);

    void writeLogGestorColaSincronizarDatos(int level, Integer idInstitucionOrigen, Long idPersona, String nombreCliente, String descripcion);
}
