package org.itcgae.siga.DTOs.scs;

import java.util.List;

public class TarjetaDefensaJuridicaItem {

    private String numProcedimiento;
    private String numDiligencia;
    private List<String> idDelitos;
    private String idProcedimiento;
    private String idComisaria;
    private String idJuzgado;
    private String nig;
    private String observaciones;
    private String comentariosDelitos;

    public String getNumProcedimiento() {
        return numProcedimiento;
    }

    public void setNumProcedimiento(String numProcedimiento) {
        this.numProcedimiento = numProcedimiento;
    }

    public String getIdJuzgado() {
        return idJuzgado;
    }

    public void setIdJuzgado(String idJuzgado) {
        this.idJuzgado = idJuzgado;
    }

    public String getNumDiligencia() {
        return numDiligencia;
    }

    public void setNumDiligencia(String numDiligencia) {
        this.numDiligencia = numDiligencia;
    }

    public List<String> getIdDelitos() {
        return idDelitos;
    }

    public void setIdDelitos(List<String> idDelitos) {
        this.idDelitos = idDelitos;
    }

    public String getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getIdComisaria() {
        return idComisaria;
    }

    public void setIdComisaria(String idComisaria) {
        this.idComisaria = idComisaria;
    }

    public String getNig() {
        return nig;
    }

    public void setNig(String nig) {
        this.nig = nig;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getComentariosDelitos() {
        return comentariosDelitos;
    }

    public void setComentariosDelitos(String comentariosDelitos) {
        this.comentariosDelitos = comentariosDelitos;
    }
}
