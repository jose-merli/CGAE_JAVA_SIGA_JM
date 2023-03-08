package org.itcgae.siga.scs.services.guardia;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.*;

import javax.servlet.http.HttpServletRequest;

public interface ActuacionAsistenciaService {

    public ActuacionAsistenciaDTO searchTarjetaActuacion (HttpServletRequest request, String anioNumero, String idActuacion);
    public DatosGeneralesActuacionAsistenciaDTO searchTarjetaDatosGenerales(HttpServletRequest request, String anioNumero, String idActuacion);
    public UpdateResponseDTO saveDatosGenerales(HttpServletRequest request, DatosGeneralesActuacionAsistenciaItem datosGenerales, String anioNumero);
    public TarjetaJustificacionActuacionAsistenciaDTO searchTarjetaJustificacion(HttpServletRequest request, String anioNumero, String idActuacion);
    public UpdateResponseDTO saveTarjetaJustificacion (HttpServletRequest request, String anioNumero, String idActuacion, TarjetaJustificacionActuacionAsistenciaItem tarjeta);
    public UpdateResponseDTO updateEstadoActuacion (HttpServletRequest request, String anioNumero, String idActuacion, TarjetaJustificacionActuacionAsistenciaItem tarjeta);
    public HistoricoActuacionAsistenciaDTO searchHistorico(HttpServletRequest request, String anioNumero, String idActuacion);
    public boolean searchHitoNueve(HttpServletRequest request, String anioNumero, String idInstitucion);
}
