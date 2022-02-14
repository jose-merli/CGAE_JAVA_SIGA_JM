package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTO.fac.EstadosAbonosDTO;
import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
import org.itcgae.siga.DTO.fac.EstadosPagosDTO;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface IFacturacionPySFacturasService {

    public EstadosAbonosDTO getEstadosAbonos(String idAbono, HttpServletRequest request) throws Exception;

    public InsertResponseDTO compensarAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception;

    public InsertResponseDTO compensarAbonoVarios(List<EstadosAbonosItem> nuevosEstados, HttpServletRequest request) throws Exception;

    public InsertResponseDTO pagarPorCajaAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception;

    public InsertResponseDTO pagarPorCajaAbonoVarios(List<EstadosAbonosItem> nuevosEstados, HttpServletRequest request) throws Exception;

    public DeleteResponseDTO eliminarPagoPorCajaAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception;

    public InsertResponseDTO renegociarAbono(EstadosAbonosItem nuevoEstado, HttpServletRequest request) throws Exception;

    public InsertResponseDTO renegociarAbonoVarios(List<EstadosAbonosItem> nuevosEstados, HttpServletRequest request) throws Exception;

    public EstadosPagosDTO getEstadosPagos(String idFactura, HttpServletRequest request) throws Exception;

    public InsertResponseDTO insertarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception;

    public InsertResponseDTO insertarEstadosPagosVarios(List<EstadosPagosItem> nuevosEstados, HttpServletRequest request) throws Exception;

}
