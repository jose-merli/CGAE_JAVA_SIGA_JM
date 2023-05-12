package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

public class RelacionesItem {
    private String sjcs;
    private String idinstitucion;
    private String anio;
    private String numero;
    private String idletrado;
    private String colegiado; 
    private String justiciable; 
    private String idturno;
    private String idturnodesigna;
    private String idtipo;
    private String codigo;
    private String descturno;
    private String destipo;
    private Date fechaDesignacion;
    private String datosinteres;
    private String idsjcs;

    private String impugnacion;
    private Date fechaimpugnacion;
    private String dictamen;
    private Date fechadictamen;
    private String resolucion;
    private Date fecharesolucion;
    private String centrodetencion;
    private Date fechaasunto;
    private String dilnigproc;
    private String idpersonajg;
    private String numEjg;
    private String dictamenObs;
    private String idFundamentoCalif;
    private String idGuardia;
    private String idTipoDictamenEJG;

    public String getIdTipoDictamenEJG() {
        return idTipoDictamenEJG;
    }

    public void setIdTipoDictamenEJG(String idTipoDictamenEJG) {
        this.idTipoDictamenEJG = idTipoDictamenEJG;
    }

    public String getDictamenObs() {
        return dictamenObs;
    }

    public void setDictamenObs(String dictamenObs) {
        this.dictamenObs = dictamenObs;
    }

    public String getIdFundamentoCalif() {
        return idFundamentoCalif;
    }

    public void setIdFundamentoCalif(String idFundamentoCalif) {
        this.idFundamentoCalif = idFundamentoCalif;
    }

    public String getIdGuardia() {
        return idGuardia;
    }

    public void setIdGuardia(String idGuardia) {
        this.idGuardia = idGuardia;
    }

    public String getNumEjg() {
        return numEjg;
    }

    public void setNumEjg(String numEjg) {
        this.numEjg = numEjg;
    }

    public String getSjcs() {
        return sjcs;
    }

    public void setSjcs(String sjcs) {
        this.sjcs = sjcs;
    }

    public String getIdsjcs() {
        return idsjcs;
    }

    public void setIdsjcs(String idsjcs) {
        this.idsjcs = idsjcs;
    }

    public String getIdinstitucion() {
        return idinstitucion;
    }

    public void setIdinstitucion(String idinstitucion) {
        this.idinstitucion = idinstitucion;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdletrado() {
        return idletrado;
    }

    public void setIdletrado(String idletrado) {
        this.idletrado = idletrado;
    }

    public String getColegiado() {
        return colegiado;
    }

    public void setColegiado(String colegiado) {
        this.colegiado = colegiado;
    }

    public String getIdturno() {
        return idturno;
    }

    public void setIdturno(String idturno) {
        this.idturno = idturno;
    }

    public String getIdturnodesigna() {
        return idturnodesigna;
    }

    public void setIdturnodesigna(String idturnodesigna) {
        this.idturnodesigna = idturnodesigna;
    }

    public String getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(String idtipo) {
        this.idtipo = idtipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescturno() {
        return descturno;
    }

    public void setDescturno(String descturno) {
        this.descturno = descturno;
    }

    public String getDestipo() {
        return destipo;
    }

    public void setDestipo(String destipo) {
        this.destipo = destipo;
    }

    public Date getFechaDesignacion() {
        return fechaDesignacion;
    }

    public void setFechaDesignacion(Date fechaDesignacion) {
        this.fechaDesignacion = fechaDesignacion;
    }

    public String getDatosinteres() {
        return datosinteres;
    }

    public void setDatosinteres(String datosinteres) {
        this.datosinteres = datosinteres;
    }

    public String getJusticiable() {
        return justiciable;
    }

    public void setJusticiable(String justiciable) {
        this.justiciable = justiciable;
    }

    public String getImpugnacion() {
        return impugnacion;
    }

    public void setImpugnacion(String impugnacion) {
        this.impugnacion = impugnacion;
    }

    public Date getFechaimpugnacion() {
        return fechaimpugnacion;
    }

    public void setFechaimpugnacion(Date fechaimpugnacion) {
        this.fechaimpugnacion = fechaimpugnacion;
    }

    public String getDictamen() {
        return dictamen;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public Date getFechadictamen() {
        return fechadictamen;
    }

    public void setFechadictamen(Date fechadictamen) {
        this.fechadictamen = fechadictamen;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public Date getFecharesolucion() {
        return fecharesolucion;
    }

    public void setFecharesolucion(Date fecharesolucion) {
        this.fecharesolucion = fecharesolucion;
    }

    public String getCentrodetencion() {
        return centrodetencion;
    }

    public void setCentrodetencion(String centrodetencion) {
        this.centrodetencion = centrodetencion;
    }

    public Date getFechaasunto() {
        return fechaasunto;
    }

    public void setFechaasunto(Date fechaasunto) {
        this.fechaasunto = fechaasunto;
    }

    public String getDilnigproc() {
        return dilnigproc;
    }

    public void setDilnigproc(String dilnigproc) {
        this.dilnigproc = dilnigproc;
    }

    public String getIdpersonajg() {
        return idpersonajg;
    }

    public void setIdpersonajg(String idpersonajg) {
        this.idpersonajg = idpersonajg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelacionesItem that = (RelacionesItem) o;
        return Objects.equals(sjcs, that.sjcs) &&
                Objects.equals(idinstitucion, that.idinstitucion) &&
                Objects.equals(anio, that.anio) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(idletrado, that.idletrado) &&
                Objects.equals(colegiado, that.colegiado) &&
                Objects.equals(justiciable, that.justiciable) &&
                Objects.equals(idturno, that.idturno) &&
                Objects.equals(idturnodesigna, that.idturnodesigna) &&
                Objects.equals(idtipo, that.idtipo) &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(descturno, that.descturno) &&
                Objects.equals(destipo, that.destipo) &&
                Objects.equals(fechaDesignacion, that.fechaDesignacion) &&
                Objects.equals(datosinteres, that.datosinteres) &&
                Objects.equals(idsjcs, that.idsjcs) &&
                Objects.equals(impugnacion, that.impugnacion) &&
                Objects.equals(fechaimpugnacion, that.fechaimpugnacion) &&
                Objects.equals(dictamen, that.dictamen) &&
                Objects.equals(fechadictamen, that.fechadictamen) &&
                Objects.equals(resolucion, that.resolucion) &&
                Objects.equals(fecharesolucion, that.fecharesolucion) &&
                Objects.equals(centrodetencion, that.centrodetencion) &&
                Objects.equals(fechaasunto, that.fechaasunto) &&
                Objects.equals(dilnigproc, that.dilnigproc) &&
                Objects.equals(idpersonajg, that.idpersonajg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sjcs, idinstitucion, anio, numero, idletrado, colegiado, justiciable, idturno, idturnodesigna, idtipo, codigo, descturno, destipo, fechaDesignacion, datosinteres, idsjcs, impugnacion, fechaimpugnacion, dictamen, fechadictamen, resolucion, fecharesolucion, centrodetencion, fechaasunto, dilnigproc, idpersonajg);
    }

    @Override
    public String toString() {
        return "RelacionesItem{" +
                "sjcs='" + sjcs + '\'' +
                ", idinstitucion='" + idinstitucion + '\'' +
                ", anio='" + anio + '\'' +
                ", numero='" + numero + '\'' +
                ", idletrado='" + idletrado + '\'' +
                ", colegiado='" + colegiado + '\'' +
                ", justiciable='" + justiciable + '\'' +
                ", idturno='" + idturno + '\'' +
                ", idturnodesigna='" + idturnodesigna + '\'' +
                ", idtipo='" + idtipo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descturno='" + descturno + '\'' +
                ", destipo='" + destipo + '\'' +
                ", fechaDesignacion=" + fechaDesignacion +
                ", datosinteres='" + datosinteres + '\'' +
                ", idsjcs='" + idsjcs + '\'' +
                ", impugnacion='" + impugnacion + '\'' +
                ", fechaimpugnacion=" + fechaimpugnacion +
                ", dictamen='" + dictamen + '\'' +
                ", fechadictamen=" + fechadictamen +
                ", resolucion='" + resolucion + '\'' +
                ", fecharesolucion=" + fecharesolucion +
                ", centrodetencion='" + centrodetencion + '\'' +
                ", fechaasunto=" + fechaasunto +
                ", dilnigproc='" + dilnigproc + '\'' +
                ", idpersonajg='" + idpersonajg + '\'' +
                '}';
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
