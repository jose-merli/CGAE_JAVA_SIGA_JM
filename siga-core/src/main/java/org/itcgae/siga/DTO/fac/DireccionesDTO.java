package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class DireccionesDTO {

    private String idInstitucion;
    private String idPersona;
    private String idDireccion;
    private String domicilio;
    private String codigoPostal;
    private String telefono1;
    private String telefono2;
    private String movil;
    private String fax1;
    private String fax2;
    private String correoElectronico;
    private String paginaWeb;
    private Date fechBaja;
    private String preferente;
    private String idPaisDirec;
    private String idProvincia;
    private String idPoblacion;
    private String poblacionExtranjera;
    private Date fechaModificacion;
    private String usuModificacion;
    private String otraProvincia;
    private String poblacion;
    private String provincia;
    private String nombrePais;
    private String idPais;
    private String descripcionTipoDireccion;
    private String idTipoDireccion;
    private String colegioOrigen;


}
