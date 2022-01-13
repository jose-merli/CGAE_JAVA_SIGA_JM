package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class ConceptoPagoItem {

    private String idInstitucion;
    private String idPagosjg;
    private String idFacturacion;
    private String idConcepto;
    private String desConcepto;
    private String idGrupoFacturacion;
    private Double importeFacturado;
    private Double importePendiente;
    private Double porcentajePendiente;
    private Double porcentajeApagar;
    private Double cantidadApagar;

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getIdPagosjg() {
        return idPagosjg;
    }

    public void setIdPagosjg(String idPagosjg) {
        this.idPagosjg = idPagosjg;
    }

    public String getIdFacturacion() {
        return idFacturacion;
    }

    public void setIdFacturacion(String idFacturacion) {
        this.idFacturacion = idFacturacion;
    }

    public String getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(String idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getDesConcepto() {
        return desConcepto;
    }

    public void setDesConcepto(String desConcepto) {
        this.desConcepto = desConcepto;
    }

    public String getIdGrupoFacturacion() {
        return idGrupoFacturacion;
    }

    public void setIdGrupoFacturacion(String idGrupoFacturacion) {
        this.idGrupoFacturacion = idGrupoFacturacion;
    }

    public Double getImporteFacturado() {
        return importeFacturado;
    }

    public void setImporteFacturado(Double importeFacturado) {
        this.importeFacturado = importeFacturado;
    }

    public Double getImportePendiente() {
        return importePendiente;
    }

    public void setImportePendiente(Double importePendiente) {
        this.importePendiente = importePendiente;
    }

    public Double getPorcentajePendiente() {
        return porcentajePendiente;
    }

    public void setPorcentajePendiente(Double porcentajePendiente) {
        this.porcentajePendiente = porcentajePendiente;
    }

    public Double getPorcentajeApagar() {
        return porcentajeApagar;
    }

    public void setPorcentajeApagar(Double porcentajeApagar) {
        this.porcentajeApagar = porcentajeApagar;
    }

    public Double getCantidadApagar() {
        return cantidadApagar;
    }

    public void setCantidadApagar(Double cantidadApagar) {
        this.cantidadApagar = cantidadApagar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConceptoPagoItem that = (ConceptoPagoItem) o;
        return Objects.equals(idInstitucion, that.idInstitucion) &&
                Objects.equals(idPagosjg, that.idPagosjg) &&
                Objects.equals(idFacturacion, that.idFacturacion) &&
                Objects.equals(idConcepto, that.idConcepto) &&
                Objects.equals(desConcepto, that.desConcepto) &&
                Objects.equals(idGrupoFacturacion, that.idGrupoFacturacion) &&
                Objects.equals(importeFacturado, that.importeFacturado) &&
                Objects.equals(importePendiente, that.importePendiente) &&
                Objects.equals(porcentajePendiente, that.porcentajePendiente) &&
                Objects.equals(porcentajeApagar, that.porcentajeApagar) &&
                Objects.equals(cantidadApagar, that.cantidadApagar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInstitucion, idPagosjg, idFacturacion, idConcepto, desConcepto, idGrupoFacturacion, importeFacturado, importePendiente, porcentajePendiente, porcentajeApagar, cantidadApagar);
    }

    @Override
    public String toString() {
        return "ConceptoPagoItem{" +
                "idInstitucion='" + idInstitucion + '\'' +
                ", idPagosjg='" + idPagosjg + '\'' +
                ", idFacturacion='" + idFacturacion + '\'' +
                ", idConcepto='" + idConcepto + '\'' +
                ", desConcepto='" + desConcepto + '\'' +
                ", idGrupoFacturacion='" + idGrupoFacturacion + '\'' +
                ", importeFacturado=" + importeFacturado +
                ", importePendiente=" + importePendiente +
                ", porcentajePendiente=" + porcentajePendiente +
                ", porcentajeApagar=" + porcentajeApagar +
                ", cantidadApagar=" + cantidadApagar +
                '}';
    }
}
