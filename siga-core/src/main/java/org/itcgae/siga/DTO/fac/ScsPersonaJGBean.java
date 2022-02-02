package org.itcgae.siga.DTO.fac;

import lombok.Data;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenProvincias;
import org.itcgae.siga.db.entities.ScsTelefonospersona;

import java.util.List;

@Data
public class ScsPersonaJGBean {

    CenPoblaciones poblacion;
    CenProvincias provincia;
    List<ScsTelefonospersona> telefonos;

    private Integer idPersona;
    private String nif;
    private String tipo;
    private String tipoIdentificacion;
    private String observaciones;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private String existeDomicilio;
    private String codigoPostal;
    private String fechaNacimiento;
    private Integer idProfesion;
    private Integer idMinusvalia;
    private String regimenConyugal;
    private Integer idInstitucion;
    private String idPais;
    private String idProvincia;
    private String perIdProvicncia;
    private String proIdProvincia;
    private String idPoblacion;
    private Integer idEstadoCivil;
    private Integer idProcurador;
    private Integer idRepresentanteJG;
    private String enCalidadDe;
    private String representante;
    private String sexo;
    private String idioma;
    private String hijos;
    private String edad;
    private String fax;
    private String correoElectronico;
    private String idTipoDir;
    private String numeroDir;
    private String escaleraDir;
    private String pisoDir;
    private String puertaDir;
    private String idTipoVia;
    private String asistidoSolicitaJG;
    private String asistidoAutorizaEEJG;
    private String autorizaAvisoTelematico;
    private String notificacionTelematica;
    private String poblacionStr;
    private String provinciaStr;
}
