package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class MovimientosVariosFacturacionItem {

    private String descripcion;
    private String tipo;
    private String certificacion;
    private String idAplicadoEnPago;
    private Date fechaApDesde;
    private Date fechaApHasta;
    private String idConcepto;
    private String idPartidaPresupuestaria;
    private String ncolegiado;
    private String letrado; //nombre, apellidos
    private double cantidad;
    private double cantidadAplicada;
    private double cantidadRestante;
    private String idInstitucion;
    private String idMovimiento;
    private String idPersona;
    private String motivo;
    private Date fechaAlta;
    private Date fechaModificacion;
    private int usuModificacion;
    private String contabilizado;
    private String idFacturacion;
    private String idGrupoFacturacion;
    private boolean historico;
    private String nif;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String nombretipo;
    private String nombrefacturacion;
    private String nombrePago;

    public String getNombrePago() {
        return nombrePago;
    }

    public void setNombrePago(String nombrePago) {
        this.nombrePago = nombrePago;
    }

    public String getNombretipo() {
        return nombretipo;
    }

    public void setNombretipo(String nombretipo) {
        this.nombretipo = nombretipo;
    }

    public String getNombrefacturacion() {
        return nombrefacturacion;
    }

    public void setNombrefacturacion(String nombrefacturacion) {
        this.nombrefacturacion = nombrefacturacion;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public boolean isHistorico() {
        return historico;
    }

    public void setHistorico(boolean historico) {
        this.historico = historico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public String getIdAplicadoEnPago() {
        return idAplicadoEnPago;
    }

    public void setIdAplicadoEnPago(String idAplicadoEnPago) {
        this.idAplicadoEnPago = idAplicadoEnPago;
    }

    public Date getFechaApDesde() {
        return fechaApDesde;
    }

    public void setFechaApDesde(Date fechaApDesde) {
        this.fechaApDesde = fechaApDesde;
    }

    public Date getFechaApHasta() {
        return fechaApHasta;
    }

    public void setFechaApHasta(Date fechaApHasta) {
        this.fechaApHasta = fechaApHasta;
    }

    public String getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(String idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getIdPartidaPresupuestaria() {
        return idPartidaPresupuestaria;
    }

    public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
    }

    public String getNcolegiado() {
        return ncolegiado;
    }

    public void setNcolegiado(String ncolegiado) {
        this.ncolegiado = ncolegiado;
    }

    public String getLetrado() {
        return letrado;
    }

    public void setLetrado(String letrado) {
        this.letrado = letrado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadAplicada() {
        return cantidadAplicada;
    }

    public void setCantidadAplicada(double cantidadAplicada) {
        this.cantidadAplicada = cantidadAplicada;
    }

    public double getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(double cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getUsuModificacion() {
        return usuModificacion;
    }

    public void setUsuModificacion(int usuModificacion) {
        this.usuModificacion = usuModificacion;
    }

    public String getContabilizado() {
        return contabilizado;
    }

    public void setContabilizado(String contabilizado) {
        this.contabilizado = contabilizado;
    }

    public String getIdFacturacion() {
        return idFacturacion;
    }

    public void setIdFacturacion(String idFacturacion) {
        this.idFacturacion = idFacturacion;
    }

    public String getIdGrupoFacturacion() {
        return idGrupoFacturacion;
    }

    public void setIdGrupoFacturacion(String idGrupoFacturacion) {
        this.idGrupoFacturacion = idGrupoFacturacion;
    }
}
