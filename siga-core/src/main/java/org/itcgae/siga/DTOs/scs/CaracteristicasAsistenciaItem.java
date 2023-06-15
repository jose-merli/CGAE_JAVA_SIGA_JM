package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class CaracteristicasAsistenciaItem {
    private String idOrigenContacto;
    private boolean violenciaDomestica;
    private boolean violenciaGenero;
    private boolean contraLibertadSexual;
    private boolean menorAbuso;
    private boolean discapacidadPsiquicaAbuso;
    private boolean judicial;
    private boolean penal;
    private boolean civil;
    private boolean interposicionDenuncia;
    private boolean medidasCautelares;
    private boolean ordenProteccion;
    private boolean otras;
    private String otrasDesc;
    private String asesoramiento;
    private String derivaractuacionesjudiciales;
    private String ministerioFiscal;
    private String medicoForense;
    private String derechosjusticiagratuita;
    private String obligadadesalojodomicilio;
    private String entrevistaletradodemandante;
    private String letradodesignadocontiactujudi;
    private String civilespenales;
    
    public String getDerivaractuacionesjudiciales() {
		return derivaractuacionesjudiciales;
	}

	public void setDerivaractuacionesjudiciales(String derivaractuacionesjudiciales) {
		this.derivaractuacionesjudiciales = derivaractuacionesjudiciales;
	}

	public String getDerechosjusticiagratuita() {
		return derechosjusticiagratuita;
	}

	public void setDerechosjusticiagratuita(String derechosjusticiagratuita) {
		this.derechosjusticiagratuita = derechosjusticiagratuita;
	}

	public String getObligadadesalojodomicilio() {
		return obligadadesalojodomicilio;
	}

	public void setObligadadesalojodomicilio(String obligadadesalojodomicilio) {
		this.obligadadesalojodomicilio = obligadadesalojodomicilio;
	}

	public String getEntrevistaletradodemandante() {
		return entrevistaletradodemandante;
	}

	public void setEntrevistaletradodemandante(String entrevistaletradodemandante) {
		this.entrevistaletradodemandante = entrevistaletradodemandante;
	}

	public String getLetradodesignadocontiactujudi() {
		return letradodesignadocontiactujudi;
	}

	public void setLetradodesignadocontiactujudi(String letradodesignadocontiactujudi) {
		this.letradodesignadocontiactujudi = letradodesignadocontiactujudi;
	}

	public String getCivilespenales() {
		return civilespenales;
	}

	public void setCivilespenales(String civilespenales) {
		this.civilespenales = civilespenales;
	}

	private String numColegiado;
    private String nombreColegiado;
    private String idProcedimiento;
    private String numeroProcedimiento;
    private String nig;
    private String descOrigenContacto;
    private String idJuzgado;

    public String getIdOrigenContacto() {
        return idOrigenContacto;
    }

    public void setIdOrigenContacto(String idOrigenContacto) {
        this.idOrigenContacto = idOrigenContacto;
    }

    public boolean isViolenciaDomestica() {
        return violenciaDomestica;
    }

    public void setViolenciaDomestica(boolean violenciaDomestica) {
        this.violenciaDomestica = violenciaDomestica;
    }

    public boolean isViolenciaGenero() {
        return violenciaGenero;
    }

    public void setViolenciaGenero(boolean violenciaGenero) {
        this.violenciaGenero = violenciaGenero;
    }

    public boolean isContraLibertadSexual() {
        return contraLibertadSexual;
    }

    public void setContraLibertadSexual(boolean contraLibertadSexual) {
        this.contraLibertadSexual = contraLibertadSexual;
    }

    public boolean isMenorAbuso() {
        return menorAbuso;
    }

    public void setMenorAbuso(boolean menorAbuso) {
        this.menorAbuso = menorAbuso;
    }

    public boolean isDiscapacidadPsiquicaAbuso() {
        return discapacidadPsiquicaAbuso;
    }

    public void setDiscapacidadPsiquicaAbuso(boolean discapacidadPsiquicaAbuso) {
        this.discapacidadPsiquicaAbuso = discapacidadPsiquicaAbuso;
    }

    public boolean isJudicial() {
        return judicial;
    }

    public void setJudicial(boolean judicial) {
        this.judicial = judicial;
    }

    public boolean isPenal() {
        return penal;
    }

    public void setPenal(boolean penal) {
        this.penal = penal;
    }

    public boolean isCivil() {
        return civil;
    }

    public void setCivil(boolean civil) {
        this.civil = civil;
    }

    public boolean isInterposicionDenuncia() {
        return interposicionDenuncia;
    }

    public void setInterposicionDenuncia(boolean interposicionDenuncia) {
        this.interposicionDenuncia = interposicionDenuncia;
    }

    public boolean isMedidasCautelares() {
        return medidasCautelares;
    }

    public void setMedidasCautelares(boolean medidasCautelares) {
        this.medidasCautelares = medidasCautelares;
    }

    public boolean isOrdenProteccion() {
        return ordenProteccion;
    }

    public void setOrdenProteccion(boolean ordenProteccion) {
        this.ordenProteccion = ordenProteccion;
    }

    public boolean isOtras() {
        return otras;
    }

    public void setOtras(boolean otras) {
        this.otras = otras;
    }

    public String getOtrasDesc() {
        return otrasDesc;
    }

    public void setOtrasDesc(String otrasDesc) {
        this.otrasDesc = otrasDesc;
    }

    public String getAsesoramiento() {
        return asesoramiento;
    }

    public void setAsesoramiento(String asesoramiento) {
        this.asesoramiento = asesoramiento;
    }

    public String getMinisterioFiscal() {
        return ministerioFiscal;
    }

    public void setMinisterioFiscal(String ministerioFiscal) {
        this.ministerioFiscal = ministerioFiscal;
    }

    public String getMedicoForense() {
        return medicoForense;
    }

    public void setMedicoForense(String medicoForense) {
        this.medicoForense = medicoForense;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public String getNombreColegiado() {
        return nombreColegiado;
    }

    public void setNombreColegiado(String nombreColegiado) {
        this.nombreColegiado = nombreColegiado;
    }

    public String getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getNumeroProcedimiento() {
        return numeroProcedimiento;
    }

    public void setNumeroProcedimiento(String numeroProcedimiento) {
        this.numeroProcedimiento = numeroProcedimiento;
    }

    public String getNig() {
        return nig;
    }

    public void setNig(String nig) {
        this.nig = nig;
    }

    public String getDescOrigenContacto() {
        return descOrigenContacto;
    }

    public void setDescOrigenContacto(String descOrigenContacto) {
        this.descOrigenContacto = descOrigenContacto;
    }

    public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaracteristicasAsistenciaItem that = (CaracteristicasAsistenciaItem) o;
        return isViolenciaDomestica() == that.isViolenciaDomestica() &&
                isViolenciaGenero() == that.isViolenciaGenero() &&
                isContraLibertadSexual() == that.isContraLibertadSexual() &&
                isMenorAbuso() == that.isMenorAbuso() &&
                isDiscapacidadPsiquicaAbuso() == that.isDiscapacidadPsiquicaAbuso() &&
                isJudicial() == that.isJudicial() &&
                isPenal() == that.isPenal() &&
                isCivil() == that.isCivil() &&
                isInterposicionDenuncia() == that.isInterposicionDenuncia() &&
                isMedidasCautelares() == that.isMedidasCautelares() &&
                isOrdenProteccion() == that.isOrdenProteccion() &&
                isOtras() == that.isOtras() &&
                Objects.equals(getIdOrigenContacto(), that.getIdOrigenContacto()) &&
                Objects.equals(getOtrasDesc(), that.getOtrasDesc()) &&
                Objects.equals(getAsesoramiento(), that.getAsesoramiento()) &&
                Objects.equals(getMinisterioFiscal(), that.getMinisterioFiscal()) &&
                Objects.equals(getMedicoForense(), that.getMedicoForense()) &&
                Objects.equals(getNumColegiado(), that.getNumColegiado()) &&
                Objects.equals(getNombreColegiado(), that.getNombreColegiado()) &&
                Objects.equals(getIdProcedimiento(), that.getIdProcedimiento()) &&
                Objects.equals(getNumeroProcedimiento(), that.getNumeroProcedimiento()) &&
                Objects.equals(getNig(), that.getNig()) &&
                Objects.equals(getDescOrigenContacto(), that.getDescOrigenContacto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrigenContacto(), isViolenciaDomestica(), isViolenciaGenero(), isContraLibertadSexual(), isMenorAbuso(), isDiscapacidadPsiquicaAbuso(), isJudicial(), isPenal(), isCivil(), isInterposicionDenuncia(), isMedidasCautelares(), isOrdenProteccion(), isOtras(), getOtrasDesc(), getAsesoramiento(), getMinisterioFiscal(), getMedicoForense(), getNumColegiado(), getNombreColegiado(), getIdProcedimiento(), getNumeroProcedimiento(), getNig(), getDescOrigenContacto());
    }

    @Override
    public String toString() {
        return "CaracteristicasAsistenciaItem{" +
                "idOrigenContacto='" + idOrigenContacto + '\'' +
                ", violenciaDomestica=" + violenciaDomestica +
                ", violenciaGenero=" + violenciaGenero +
                ", contraLibertadSexual=" + contraLibertadSexual +
                ", menorAbuso=" + menorAbuso +
                ", discapacidadPsiquicaAbuso=" + discapacidadPsiquicaAbuso +
                ", judicial=" + judicial +
                ", penal=" + penal +
                ", civil=" + civil +
                ", interposicionDenuncia=" + interposicionDenuncia +
                ", medidasCautelares=" + medidasCautelares +
                ", ordenProteccion=" + ordenProteccion +
                ", otras=" + otras +
                ", otrasDesc='" + otrasDesc + '\'' +
                ", asesoramiento='" + asesoramiento + '\'' +
                ", ministerioFiscal='" + ministerioFiscal + '\'' +
                ", medicoForense='" + medicoForense + '\'' +
                ", numColegiado='" + numColegiado + '\'' +
                ", nombreColegiado='" + nombreColegiado + '\'' +
                ", idProcedimiento='" + idProcedimiento + '\'' +
                ", numProcedimiento='" + numeroProcedimiento + '\'' +
                ", nig='" + nig + '\'' +
                ", descOrigenContacto='" + descOrigenContacto + '\'' +
                '}';
    }
}
