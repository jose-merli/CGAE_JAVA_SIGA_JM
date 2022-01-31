package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTO.fac.FacDisqueteDevolucionesNuevoItem;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FicherosAbonosDTO;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesDTO;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFacturacionPySExportacionesService {

    // Ficheros de Adeudos

    public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request) throws Exception;

    public InsertResponseDTO nuevoFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
            throws Exception;

    public UpdateResponseDTO actualizarFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
            throws Exception;

    public DeleteResponseDTO eliminarFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
            throws Exception;

    public ResponseEntity<InputStreamResource> descargarFicheroAdeudos(List<FicherosAdeudosItem> ficheroAdeudosItems, HttpServletRequest request)
            throws Exception;

    // Ficheros de devoluciones

    public FicherosDevolucionesDTO getFicherosDevoluciones(FicherosDevolucionesItem item, HttpServletRequest request)
            throws Exception;

    public InsertResponseDTO nuevoFicheroDevoluciones(FacDisqueteDevolucionesNuevoItem ficherosDevolucionesItem, HttpServletRequest request)
            throws Exception;

    public DeleteResponseDTO eliminarFicheroDevoluciones(FicherosDevolucionesItem ficherosDevolucionesItem, HttpServletRequest request)
            throws Exception;

    public ResponseEntity<InputStreamResource> descargarFicheroDevoluciones(List<FicherosDevolucionesItem> ficheroDevolucionesItems, HttpServletRequest request) throws Exception;


    // Ficheros de transferencias

    public FicherosAbonosDTO getFicherosTransferencias(FicherosAbonosItem item, HttpServletRequest request)
            throws Exception;

    public InsertResponseDTO nuevoFicheroTransferencias(List<FacturaItem> abonoItems, HttpServletRequest request) throws Exception;

    public InsertResponseDTO nuevoFicheroTransferenciasSjcs(List<FacturaItem> abonoItems, HttpServletRequest request) throws Exception;

    public UpdateResponseDTO actualizarFicheroTranferencias(FacDisqueteabonos updateItem, HttpServletRequest request)
            throws Exception;

    public DeleteResponseDTO eliminarFicheroTransferencias(FicherosAbonosItem ficherosAbonosItem, HttpServletRequest request)
            throws Exception;

    public ResponseEntity<InputStreamResource> descargarFicheroTransferencias(List<FicherosAbonosItem> ficheroAbonosItems, HttpServletRequest request) throws Exception;

}
