package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PagosjgItem {

    private String idInstitucion;
    private String idPagosjg;
    private String idFacturacion;
    private String nombre;
    private String nombreFac;
    private String importeEJG;
    private String importeGuardia;
    private String importeOficio;
    private String importesOJ;
    private String importeRepartir;
    private String importePagado;
    private Date fechaEstado;
    private String desEstado;
    private String idHitoGeneral;
    private String porcentaje;
    private String abreviatura;
    private String idEstado;
    private String idPartidaPresupuestaria;
    private String desConcepto;
    private String idConcepto;
    private String idGrupo;
    private String descGrupo;
    private Date fechaDesde;
    private Date fechaHasta;
    private String cantidad;
    private String codBanco;
    private String idSufijo;
    private String nombreUsuModificacion;
    private String idPropSepa;
    private String idPropOtros;

    @JsonProperty("idInstitucion")
    public String getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    @JsonProperty("idPagosjg")
    public String getIdPagosjg() {
        return idPagosjg;
    }

    public void setIdPagosjg(String idPagosjg) {
        this.idPagosjg = idPagosjg;
    }

    @JsonProperty("idFacturacion")
    public String getIdFacturacion() {
        return idFacturacion;
    }

    public void setIdFacturacion(String idFacturacion) {
        this.idFacturacion = idFacturacion;
    }

    @JsonProperty("nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreFac() {
        return nombreFac;
    }

    @JsonProperty("nombreFac")
    public void setNombreFac(String nombreFac) {
        this.nombreFac = nombreFac;
    }

    @JsonProperty("importeEJG")
    public String getImporteEJG() {
        return importeEJG;
    }

    public void setImporteEJG(String importeEJG) {
        this.importeEJG = importeEJG;
    }

    @JsonProperty("importeGuardia")
    public String getImporteGuardia() {
        return importeGuardia;
    }

    public void setImporteGuardia(String importeGuardia) {
        this.importeGuardia = importeGuardia;
    }

    @JsonProperty("importeOficio")
    public String getImporteOficio() {
        return importeOficio;
    }

    public void setImporteOficio(String importeOficio) {
        this.importeOficio = importeOficio;
    }

    @JsonProperty("importesOJ")
    public String getImportesOJ() {
        return importesOJ;
    }

    public void setImportesOJ(String importesOJ) {
        this.importesOJ = importesOJ;
    }

    @JsonProperty("importeRepartir")
    public String getImporteRepartir() {
        return importeRepartir;
    }

    public void setImporteRepartir(String importeRepartir) {
        this.importeRepartir = importeRepartir;
    }

    @JsonProperty("importePagado")
    public String getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(String importePagado) {
        this.importePagado = importePagado;
    }

    @JsonProperty("fechaEstado")
    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    @JsonProperty("desEstado")
    public String getDesEstado() {
        return desEstado;
    }

    public void setDesEstado(String desEstado) {
        this.desEstado = desEstado;
    }

    @JsonProperty("desConcepto")
    public String getDesConcepto() {
        return desConcepto;
    }

    public void setDesConcepto(String desConcepto) {
        this.desConcepto = desConcepto;
    }

    /**
     * @return the idHitoGeneral
     */
    @JsonProperty("idHitoGeneral")
    public String getIdHitoGeneral() {
        return idHitoGeneral;
    }

    /**
     * @param idHitoGeneral the idHitoGeneral to set
     */
    public void setIdHitoGeneral(String idHitoGeneral) {
        this.idHitoGeneral = idHitoGeneral;
    }

    @JsonProperty("porcentaje")
    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    @JsonProperty("idEstado")
    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    @JsonProperty("idPartidaPresupuestaria")
    public String getIdPartidaPresupuestaria() {
        return idPartidaPresupuestaria;
    }

    public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
    }

    @JsonProperty("idConcepto")
    public String getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(String idConcepto) {
        this.idConcepto = idConcepto;
    }

    @JsonProperty("idGrupo")
    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    @JsonProperty("descGrupo")
    public String getDescGrupo() {
        return descGrupo;
    }

    public void setDescGrupo(String descGrupo) {
        this.descGrupo = descGrupo;
    }

    @JsonProperty("fechaDesde")
    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    @JsonProperty("fechaHasta")
    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @JsonProperty("abreviatura")
    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @JsonProperty("cantidad")
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @JsonProperty("codBanco")
    public String getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    @JsonProperty("idSufijo")
    public String getIdSufijo() {
        return idSufijo;
    }

    public void setIdSufijo(String idSufijo) {
        this.idSufijo = idSufijo;
    }

    @JsonProperty("nombreUsuModificacion")
    public String getNombreUsuModificacion() {
        return nombreUsuModificacion;
    }

    public void setNombreUsuModificacion(String nombreUsuModificacion) {
        this.nombreUsuModificacion = nombreUsuModificacion;
    }

    @JsonProperty("idPropSepa")
    public String getIdPropSepa() {
        return idPropSepa;
    }

    public void setIdPropSepa(String idPropSepa) {
        this.idPropSepa = idPropSepa;
    }

    @JsonProperty("idPropOtros")
    public String getIdPropOtros() {
        return idPropOtros;
    }

    public void setIdPropOtros(String idPropOtros) {
        this.idPropOtros = idPropOtros;
    }

    @Override
    public String toString() {
        return "PagosjgItem{" +
                "idInstitucion='" + idInstitucion + '\'' +
                ", idPagosjg='" + idPagosjg + '\'' +
                ", idFacturacion='" + idFacturacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nombreFac='" + nombreFac + '\'' +
                ", importeEJG='" + importeEJG + '\'' +
                ", importeGuardia='" + importeGuardia + '\'' +
                ", importeOficio='" + importeOficio + '\'' +
                ", importesOJ='" + importesOJ + '\'' +
                ", importeRepartir='" + importeRepartir + '\'' +
                ", importePagado='" + importePagado + '\'' +
                ", fechaEstado=" + fechaEstado +
                ", desEstado='" + desEstado + '\'' +
                ", idHitoGeneral='" + idHitoGeneral + '\'' +
                ", porcentaje='" + porcentaje + '\'' +
                ", abreviatura='" + abreviatura + '\'' +
                ", idEstado='" + idEstado + '\'' +
                ", idPartidaPresupuestaria='" + idPartidaPresupuestaria + '\'' +
                ", desConcepto='" + desConcepto + '\'' +
                ", idConcepto='" + idConcepto + '\'' +
                ", idGrupo='" + idGrupo + '\'' +
                ", descGrupo='" + descGrupo + '\'' +
                ", fechaDesde=" + fechaDesde +
                ", fechaHasta=" + fechaHasta +
                ", cantidad='" + cantidad + '\'' +
                ", codBanco='" + codBanco + '\'' +
                ", idSufijo='" + idSufijo + '\'' +
                ", nombreUsuModificacion='" + nombreUsuModificacion + '\'' +
                ", idPropSepa='" + idPropSepa + '\'' +
                ", idPropOtros='" + idPropOtros + '\'' +
                '}';
    }
}
