package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface IFacturacionPySFacturasService {

    public InsertResponseDTO compensarAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception;

    public InsertResponseDTO pagarPorCajaAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request);

    DeleteResponseDTO eliminarPagoPorCajaAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception;

    public InsertResponseDTO renegociarAbonoSJCS(EstadosAbonosItem nuevoEstado, HttpServletRequest request);

}
