package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class FacFacturacionprogramadaItem {

    private String idSerieFacturacion;
    private String compraSuscripcion;

    private Date fechaInicioServicios;
    private Date fechaInicioProductos;
    private Date fechaFinServicios;
    private Date fechaFinProductos;
    private Date fechaCompraSuscripcionDesde;
    private Date fechaCompraSuscripcionHasta;

    private String importe;
    private String importeDesde;
    private String importeHasta;

    private String idEstadoConfirmacion;
    private String estadoGeneracion;
    private String estadoConfirmacion;
    private String idEstadoPDF;
    private String estadoPDF;
    private String idEstadoEnvio;
    private String estadoEnvio;
    private String idEstadoTraspaso;
    private String estadoTraspaso;

    private Date fechaPrevistaGeneracion;
    private Date fechaPrevistaGeneracionDesde;
    private Date fechaPrevistaGeneracionHasta;

    private Date fechaPrevistaConfirm;
    private Date fechaPrevistaConfirmDesde;
    private Date fechaPrevistaConfirmHasta;

    private Date fechaRealGeneracion;
    private Date fechaRealGeneracionDesde;
    private Date fechaRealGeneracionHasta;

    private Date fechaConfirmacion;
    private Date fechaConfirmacionDesde;
    private Date fechaConfirmacionHasta;

    private String idProgramacion;
    private String descripcion;
    private String nombreAbreviado;
    private Date fechaProgramacion;
    private Boolean archivarFact;
    private String usuModificacion;
    private Date fechaModificacion;
    private String nombreFichero;
    private String logError;
    private String logTraspaso;
    private Boolean traspasoFacturas;
    private Boolean generaPDF;
    private Boolean envio;
    private String idTipoPlantillaMail;
    private String tipoPlantillaMail;
    private String traspasoPlatilla;
    private String traspasoCodAuditoriaDef;

    private Date fechaPresentacion;
    private Date fechaRecibosPrimeros;
    private Date fechaRecibosRecurrentes;
    private Date fechaRecibosCOR1;
    private Date fechaRecibosB2B;

    private String idModeloFactura;
    private String idModeloRectificativa;
    private String modeloFactura;
    private String modeloRectificativa;

    private Boolean esDatosGenerales;

    public String getTipoPlantillaMail() {
        return tipoPlantillaMail;
    }

    public void setTipoPlantillaMail(String tipoPlantillaMail) {
        this.tipoPlantillaMail = tipoPlantillaMail;
    }

    public Boolean getEsDatosGenerales() {
        return esDatosGenerales;
    }

    public void setEsDatosGenerales(Boolean esDatosGenerales) {
        this.esDatosGenerales = esDatosGenerales;
    }

    public String getIdSerieFacturacion() {
        return idSerieFacturacion;
    }

    public void setIdSerieFacturacion(String idSerieFacturacion) {
        this.idSerieFacturacion = idSerieFacturacion;
    }

    public String getCompraSuscripcion() {
        return compraSuscripcion;
    }

    public void setCompraSuscripcion(String compraSuscripcion) {
        this.compraSuscripcion = compraSuscripcion;
    }

    public Date getFechaInicioServicios() {
        return fechaInicioServicios;
    }

    public void setFechaInicioServicios(Date fechaInicioServicios) {
        this.fechaInicioServicios = fechaInicioServicios;
    }

    public Date getFechaInicioProductos() {
        return fechaInicioProductos;
    }

    public void setFechaInicioProductos(Date fechaInicioProductos) {
        this.fechaInicioProductos = fechaInicioProductos;
    }

    public Date getFechaFinServicios() {
        return fechaFinServicios;
    }

    public void setFechaFinServicios(Date fechaFinServicios) {
        this.fechaFinServicios = fechaFinServicios;
    }

    public Date getFechaFinProductos() {
        return fechaFinProductos;
    }

    public void setFechaFinProductos(Date fechaFinProductos) {
        this.fechaFinProductos = fechaFinProductos;
    }

    public Date getFechaCompraSuscripcionDesde() {
        return fechaCompraSuscripcionDesde;
    }

    public void setFechaCompraSuscripcionDesde(Date fechaCompraSuscripcionDesde) {
        this.fechaCompraSuscripcionDesde = fechaCompraSuscripcionDesde;
    }

    public Date getFechaCompraSuscripcionHasta() {
        return fechaCompraSuscripcionHasta;
    }

    public void setFechaCompraSuscripcionHasta(Date fechaCompraSuscripcionHasta) {
        this.fechaCompraSuscripcionHasta = fechaCompraSuscripcionHasta;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getImporteDesde() {
        return importeDesde;
    }

    public void setImporteDesde(String importeDesde) {
        this.importeDesde = importeDesde;
    }

    public String getImporteHasta() {
        return importeHasta;
    }

    public void setImporteHasta(String importeHasta) {
        this.importeHasta = importeHasta;
    }

    public String getIdEstadoConfirmacion() {
        return idEstadoConfirmacion;
    }

    public void setIdEstadoConfirmacion(String idEstadoConfirmacion) {
        this.idEstadoConfirmacion = idEstadoConfirmacion;
    }

    public String getEstadoGeneracion() {
        return estadoGeneracion;
    }

    public void setEstadoGeneracion(String estadoGeneracion) {
        this.estadoGeneracion = estadoGeneracion;
    }

    public String getEstadoConfirmacion() {
		return estadoConfirmacion;
	}

	public void setEstadoConfirmacion(String estadoConfirmacion) {
		this.estadoConfirmacion = estadoConfirmacion;
	}

	public String getIdEstadoPDF() {
        return idEstadoPDF;
    }

    public void setIdEstadoPDF(String idEstadoPDF) {
        this.idEstadoPDF = idEstadoPDF;
    }

    public String getEstadoPDF() {
        return estadoPDF;
    }

    public void setEstadoPDF(String estadoPDF) {
        this.estadoPDF = estadoPDF;
    }

    public String getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(String idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public String getIdEstadoTraspaso() {
        return idEstadoTraspaso;
    }

    public void setIdEstadoTraspaso(String idEstadoTraspaso) {
        this.idEstadoTraspaso = idEstadoTraspaso;
    }

    public String getEstadoTraspaso() {
        return estadoTraspaso;
    }

    public void setEstadoTraspaso(String estadoTraspaso) {
        this.estadoTraspaso = estadoTraspaso;
    }

    public Date getFechaPrevistaGeneracion() {
        return fechaPrevistaGeneracion;
    }

    public void setFechaPrevistaGeneracion(Date fechaPrevistaGeneracion) {
        this.fechaPrevistaGeneracion = fechaPrevistaGeneracion;
    }

    public Date getFechaPrevistaGeneracionDesde() {
        return fechaPrevistaGeneracionDesde;
    }

    public void setFechaPrevistaGeneracionDesde(Date fechaPrevistaGeneracionDesde) {
        this.fechaPrevistaGeneracionDesde = fechaPrevistaGeneracionDesde;
    }

    public Date getFechaPrevistaGeneracionHasta() {
        return fechaPrevistaGeneracionHasta;
    }

    public void setFechaPrevistaGeneracionHasta(Date fechaPrevistaGeneracionHasta) {
        this.fechaPrevistaGeneracionHasta = fechaPrevistaGeneracionHasta;
    }

    public Date getFechaPrevistaConfirm() {
        return fechaPrevistaConfirm;
    }

    public void setFechaPrevistaConfirm(Date fechaPrevistaConfirm) {
        this.fechaPrevistaConfirm = fechaPrevistaConfirm;
    }

    public Date getFechaPrevistaConfirmDesde() {
        return fechaPrevistaConfirmDesde;
    }

    public void setFechaPrevistaConfirmDesde(Date fechaPrevistaConfirmDesde) {
        this.fechaPrevistaConfirmDesde = fechaPrevistaConfirmDesde;
    }

    public Date getFechaPrevistaConfirmHasta() {
        return fechaPrevistaConfirmHasta;
    }

    public void setFechaPrevistaConfirmHasta(Date fechaPrevistaConfirmHasta) {
        this.fechaPrevistaConfirmHasta = fechaPrevistaConfirmHasta;
    }

    public Date getFechaRealGeneracion() {
        return fechaRealGeneracion;
    }

    public void setFechaRealGeneracion(Date fechaRealGeneracion) {
        this.fechaRealGeneracion = fechaRealGeneracion;
    }

    public Date getFechaRealGeneracionDesde() {
        return fechaRealGeneracionDesde;
    }

    public void setFechaRealGeneracionDesde(Date fechaRealGeneracionDesde) {
        this.fechaRealGeneracionDesde = fechaRealGeneracionDesde;
    }

    public Date getFechaRealGeneracionHasta() {
        return fechaRealGeneracionHasta;
    }

    public void setFechaRealGeneracionHasta(Date fechaRealGeneracionHasta) {
        this.fechaRealGeneracionHasta = fechaRealGeneracionHasta;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(Date fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public Date getFechaConfirmacionDesde() {
        return fechaConfirmacionDesde;
    }

    public void setFechaConfirmacionDesde(Date fechaConfirmacionDesde) {
        this.fechaConfirmacionDesde = fechaConfirmacionDesde;
    }

    public Date getFechaConfirmacionHasta() {
        return fechaConfirmacionHasta;
    }

    public void setFechaConfirmacionHasta(Date fechaConfirmacionHasta) {
        this.fechaConfirmacionHasta = fechaConfirmacionHasta;
    }

    public String getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(String idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreAbreviado() {
        return nombreAbreviado;
    }

    public void setNombreAbreviado(String nombreAbreviado) {
        this.nombreAbreviado = nombreAbreviado;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public String getUsuModificacion() {
        return usuModificacion;
    }

    public void setUsuModificacion(String usuModificacion) {
        this.usuModificacion = usuModificacion;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public String getLogError() {
        return logError;
    }

    public void setLogError(String logError) {
        this.logError = logError;
    }

    public String getLogTraspaso() {
        return logTraspaso;
    }

    public void setLogTraspaso(String logTraspaso) {
        this.logTraspaso = logTraspaso;
    }

    public String getTraspasoPlatilla() {
        return traspasoPlatilla;
    }

    public void setTraspasoPlatilla(String traspasoPlatilla) {
        this.traspasoPlatilla = traspasoPlatilla;
    }

    public String getTraspasoCodAuditoriaDef() {
        return traspasoCodAuditoriaDef;
    }

    public void setTraspasoCodAuditoriaDef(String traspasoCodAuditoriaDef) {
        this.traspasoCodAuditoriaDef = traspasoCodAuditoriaDef;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public Date getFechaRecibosPrimeros() {
        return fechaRecibosPrimeros;
    }

    public void setFechaRecibosPrimeros(Date fechaRecibosPrimeros) {
        this.fechaRecibosPrimeros = fechaRecibosPrimeros;
    }

    public Date getFechaRecibosRecurrentes() {
        return fechaRecibosRecurrentes;
    }

    public void setFechaRecibosRecurrentes(Date fechaRecibosRecurrentes) {
        this.fechaRecibosRecurrentes = fechaRecibosRecurrentes;
    }

    public Date getFechaRecibosCOR1() {
        return fechaRecibosCOR1;
    }

    public void setFechaRecibosCOR1(Date fechaRecibosCOR1) {
        this.fechaRecibosCOR1 = fechaRecibosCOR1;
    }

    public Date getFechaRecibosB2B() {
        return fechaRecibosB2B;
    }

    public void setFechaRecibosB2B(Date fechaRecibosB2B) {
        this.fechaRecibosB2B = fechaRecibosB2B;
    }

    public String getIdModeloFactura() {
        return idModeloFactura;
    }

    public void setIdModeloFactura(String idModeloFactura) {
        this.idModeloFactura = idModeloFactura;
    }

    public String getIdModeloRectificativa() {
        return idModeloRectificativa;
    }

    public void setIdModeloRectificativa(String idModeloRectificativa) {
        this.idModeloRectificativa = idModeloRectificativa;
    }

    public String getModeloFactura() {
        return modeloFactura;
    }

    public void setModeloFactura(String modeloFactura) {
        this.modeloFactura = modeloFactura;
    }

    public String getModeloRectificativa() {
        return modeloRectificativa;
    }

    public void setModeloRectificativa(String modeloRectificativa) {
        this.modeloRectificativa = modeloRectificativa;
    }

    public Boolean getArchivarFact() {
        return archivarFact;
    }

    public void setArchivarFact(Boolean archivarFact) {
        this.archivarFact = archivarFact;
    }

    public Boolean getTraspasoFacturas() {
        return traspasoFacturas;
    }

    public void setTraspasoFacturas(Boolean traspasoFacturas) {
        this.traspasoFacturas = traspasoFacturas;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public String getIdTipoPlantillaMail() {
        return idTipoPlantillaMail;
    }

    public void setIdTipoPlantillaMail(String idTipoPlantillaMail) {
        this.idTipoPlantillaMail = idTipoPlantillaMail;
    }

    public Boolean getGeneraPDF() {
        return generaPDF;
    }

    public void setGeneraPDF(Boolean generaPDF) {
        this.generaPDF = generaPDF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacFacturacionprogramadaItem that = (FacFacturacionprogramadaItem) o;
        return Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
                Objects.equals(compraSuscripcion, that.compraSuscripcion) &&
                Objects.equals(fechaInicioServicios, that.fechaInicioServicios) &&
                Objects.equals(fechaInicioProductos, that.fechaInicioProductos) &&
                Objects.equals(fechaFinServicios, that.fechaFinServicios) &&
                Objects.equals(fechaFinProductos, that.fechaFinProductos) &&
                Objects.equals(fechaCompraSuscripcionDesde, that.fechaCompraSuscripcionDesde) &&
                Objects.equals(fechaCompraSuscripcionHasta, that.fechaCompraSuscripcionHasta) &&
                Objects.equals(importe, that.importe) &&
                Objects.equals(importeDesde, that.importeDesde) &&
                Objects.equals(importeHasta, that.importeHasta) &&
                Objects.equals(idEstadoConfirmacion, that.idEstadoConfirmacion) &&
                Objects.equals(estadoGeneracion, that.estadoGeneracion) &&
                Objects.equals(idEstadoPDF, that.idEstadoPDF) &&
                Objects.equals(estadoPDF, that.estadoPDF) &&
                Objects.equals(idEstadoEnvio, that.idEstadoEnvio) &&
                Objects.equals(estadoEnvio, that.estadoEnvio) &&
                Objects.equals(idEstadoTraspaso, that.idEstadoTraspaso) &&
                Objects.equals(estadoTraspaso, that.estadoTraspaso) &&
                Objects.equals(fechaPrevistaGeneracion, that.fechaPrevistaGeneracion) &&
                Objects.equals(fechaPrevistaGeneracionDesde, that.fechaPrevistaGeneracionDesde) &&
                Objects.equals(fechaPrevistaGeneracionHasta, that.fechaPrevistaGeneracionHasta) &&
                Objects.equals(fechaPrevistaConfirm, that.fechaPrevistaConfirm) &&
                Objects.equals(fechaPrevistaConfirmDesde, that.fechaPrevistaConfirmDesde) &&
                Objects.equals(fechaPrevistaConfirmHasta, that.fechaPrevistaConfirmHasta) &&
                Objects.equals(fechaRealGeneracion, that.fechaRealGeneracion) &&
                Objects.equals(fechaRealGeneracionDesde, that.fechaRealGeneracionDesde) &&
                Objects.equals(fechaRealGeneracionHasta, that.fechaRealGeneracionHasta) &&
                Objects.equals(fechaConfirmacion, that.fechaConfirmacion) &&
                Objects.equals(fechaConfirmacionDesde, that.fechaConfirmacionDesde) &&
                Objects.equals(fechaConfirmacionHasta, that.fechaConfirmacionHasta) &&
                Objects.equals(idProgramacion, that.idProgramacion) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(nombreAbreviado, that.nombreAbreviado) &&
                Objects.equals(fechaProgramacion, that.fechaProgramacion) &&
                Objects.equals(archivarFact, that.archivarFact) &&
                Objects.equals(usuModificacion, that.usuModificacion) &&
                Objects.equals(fechaModificacion, that.fechaModificacion) &&
                Objects.equals(nombreFichero, that.nombreFichero) &&
                Objects.equals(logError, that.logError) &&
                Objects.equals(logTraspaso, that.logTraspaso) &&
                Objects.equals(traspasoFacturas, that.traspasoFacturas) &&
                Objects.equals(generaPDF, that.generaPDF) &&
                Objects.equals(envio, that.envio) &&
                Objects.equals(idTipoPlantillaMail, that.idTipoPlantillaMail) &&
                Objects.equals(tipoPlantillaMail, that.tipoPlantillaMail) &&
                Objects.equals(traspasoPlatilla, that.traspasoPlatilla) &&
                Objects.equals(traspasoCodAuditoriaDef, that.traspasoCodAuditoriaDef) &&
                Objects.equals(fechaPresentacion, that.fechaPresentacion) &&
                Objects.equals(fechaRecibosPrimeros, that.fechaRecibosPrimeros) &&
                Objects.equals(fechaRecibosRecurrentes, that.fechaRecibosRecurrentes) &&
                Objects.equals(fechaRecibosCOR1, that.fechaRecibosCOR1) &&
                Objects.equals(fechaRecibosB2B, that.fechaRecibosB2B) &&
                Objects.equals(idModeloFactura, that.idModeloFactura) &&
                Objects.equals(idModeloRectificativa, that.idModeloRectificativa) &&
                Objects.equals(modeloFactura, that.modeloFactura) &&
                Objects.equals(modeloRectificativa, that.modeloRectificativa) &&
                Objects.equals(esDatosGenerales, that.esDatosGenerales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSerieFacturacion, compraSuscripcion, fechaInicioServicios, fechaInicioProductos, fechaFinServicios, fechaFinProductos, fechaCompraSuscripcionDesde, fechaCompraSuscripcionHasta, importe, importeDesde, importeHasta, idEstadoConfirmacion, estadoGeneracion, idEstadoPDF, estadoPDF, idEstadoEnvio, estadoEnvio, idEstadoTraspaso, estadoTraspaso, fechaPrevistaGeneracion, fechaPrevistaGeneracionDesde, fechaPrevistaGeneracionHasta, fechaPrevistaConfirm, fechaPrevistaConfirmDesde, fechaPrevistaConfirmHasta, fechaRealGeneracion, fechaRealGeneracionDesde, fechaRealGeneracionHasta, fechaConfirmacion, fechaConfirmacionDesde, fechaConfirmacionHasta, idProgramacion, descripcion, nombreAbreviado, fechaProgramacion, archivarFact, usuModificacion, fechaModificacion, nombreFichero, logError, logTraspaso, traspasoFacturas, generaPDF, envio, idTipoPlantillaMail, tipoPlantillaMail, traspasoPlatilla, traspasoCodAuditoriaDef, fechaPresentacion, fechaRecibosPrimeros, fechaRecibosRecurrentes, fechaRecibosCOR1, fechaRecibosB2B, idModeloFactura, idModeloRectificativa, modeloFactura, modeloRectificativa, esDatosGenerales);
    }

    @Override
    public String toString() {
        return "FacFacturacionprogramadaItem{" +
                "idSerieFacturacion='" + idSerieFacturacion + '\'' +
                ", compraSuscripcion='" + compraSuscripcion + '\'' +
                ", fechaInicioServicios=" + fechaInicioServicios +
                ", fechaInicioProductos=" + fechaInicioProductos +
                ", fechaFinServicios=" + fechaFinServicios +
                ", fechaFinProductos=" + fechaFinProductos +
                ", fechaCompraSuscripcionDesde=" + fechaCompraSuscripcionDesde +
                ", fechaCompraSuscripcionHasta=" + fechaCompraSuscripcionHasta +
                ", importe='" + importe + '\'' +
                ", importeDesde='" + importeDesde + '\'' +
                ", importeHasta='" + importeHasta + '\'' +
                ", idEstadoConfirmacion='" + idEstadoConfirmacion + '\'' +
                ", estadoConfirmacion='" + estadoGeneracion + '\'' +
                ", idEstadoPDF='" + idEstadoPDF + '\'' +
                ", estadoPDF='" + estadoPDF + '\'' +
                ", idEstadoEnvio='" + idEstadoEnvio + '\'' +
                ", estadoEnvio='" + estadoEnvio + '\'' +
                ", idEstadoTraspaso='" + idEstadoTraspaso + '\'' +
                ", estadoTraspaso='" + estadoTraspaso + '\'' +
                ", fechaPrevistaGeneracion=" + fechaPrevistaGeneracion +
                ", fechaPrevistaGeneracionDesde=" + fechaPrevistaGeneracionDesde +
                ", fechaPrevistaGeneracionHasta=" + fechaPrevistaGeneracionHasta +
                ", fechaPrevistaConfirm=" + fechaPrevistaConfirm +
                ", fechaPrevistaConfirmDesde=" + fechaPrevistaConfirmDesde +
                ", fechaPrevistaConfirmHasta=" + fechaPrevistaConfirmHasta +
                ", fechaRealGeneracion=" + fechaRealGeneracion +
                ", fechaRealGeneracionDesde=" + fechaRealGeneracionDesde +
                ", fechaRealGeneracionHasta=" + fechaRealGeneracionHasta +
                ", fechaConfirmacion=" + fechaConfirmacion +
                ", fechaConfirmacionDesde=" + fechaConfirmacionDesde +
                ", fechaConfirmacionHasta=" + fechaConfirmacionHasta +
                ", idProgramacion='" + idProgramacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nombreAbreviado='" + nombreAbreviado + '\'' +
                ", fechaProgramacion=" + fechaProgramacion +
                ", archivarFact=" + archivarFact +
                ", usuModificacion='" + usuModificacion + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                ", nombreFichero='" + nombreFichero + '\'' +
                ", logError='" + logError + '\'' +
                ", logTraspaso='" + logTraspaso + '\'' +
                ", traspasoFacturas=" + traspasoFacturas +
                ", generaPDF=" + generaPDF +
                ", envio=" + envio +
                ", idTipoPlantillaMail='" + idTipoPlantillaMail + '\'' +
                ", tipoPlantillaMail='" + tipoPlantillaMail + '\'' +
                ", traspasoPlatilla='" + traspasoPlatilla + '\'' +
                ", traspasoCodAuditoriaDef='" + traspasoCodAuditoriaDef + '\'' +
                ", fechaPresentacion=" + fechaPresentacion +
                ", fechaRecibosPrimeros=" + fechaRecibosPrimeros +
                ", fechaRecibosRecurrentes=" + fechaRecibosRecurrentes +
                ", fechaRecibosCOR1=" + fechaRecibosCOR1 +
                ", fechaRecibosB2B=" + fechaRecibosB2B +
                ", idModeloFactura='" + idModeloFactura + '\'' +
                ", idModeloRectificativa='" + idModeloRectificativa + '\'' +
                ", modeloFactura='" + modeloFactura + '\'' +
                ", modeloRectificativa='" + modeloRectificativa + '\'' +
                ", esDatosGenerales=" + esDatosGenerales +
                '}';
    }
}
