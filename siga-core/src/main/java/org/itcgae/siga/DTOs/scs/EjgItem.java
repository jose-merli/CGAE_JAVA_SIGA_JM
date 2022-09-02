package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EjgItem {
    private String idEJG;
    private String colegio;
    private String idInstitucion;
    private String annio;
    private String numero;
    private String numEjg;
    private String tipoEJG;
    private String tipoEJGColegio;
    private String creadoDesde;
    private Date fechaAperturaDesd;
    private Date fechaAperturaHast;
    private Date fechaApertura;
    private Date fechaModificacion;
    private String idPersona;
    private String idPersonajg;
    private String estadoEJG;
    private Date fechaEstadoDesd;
    private Date fechaEstadoHast;
    private Date fechaLimiteDesd;
    private Date fechaLimiteHast;

    private String anioexpInsos;
    private String numeroexpInsos;
    private String idTipoExpInsos;


    private String dictamenSing;
    private Date fechaDictamenDesd;
    private Date fechaDictamenHast;
    private String resolucion;
    private String fundamentoJuridico;
    private Date fechaResolucionDesd;
    private Date fechaResolucionHast;
    private String fundamentoImpuganacion;
    private Date fechaImpugnacionDesd;
    private Date fechaImpugnacionHast;
    private String fundamentoCalifDes;

    //Defensa juridica
    private String juzgado;
    private String asunto;
    private String calidad;
    private String perceptivo;
    private String renuncia;
    private String numAnnioProcedimiento;
    private String procedimiento;
    private String NIG;
    private Short idsituacion;
    private String numerodiligencia;
    private Long comisaria;
    private String delitos;
    private Short idPretension;


    private String annioCAJG;
    private String numCAJG;
    private String annioActa;
    private String numActa;
    private String ponente;
    private Date fechaPonenteDesd;
    private Date fechaPonenteHast;
    private String numRegRemesa;
    private String numRegRemesa1;
    private String numRegRemesa2;
    private String numRegRemesa3;

    private String nif;
    private String apellidos;
    private String nombre;
    private String rol;

    private String turno;
    private String idTurno;

    private String guardia;
    private String idGuardia;

    private String numColegiado;
    private String apellidosYNombre;
    private String tipoLetrado;

    private String turnoDes;
    private String nombreApeSolicitante;

    private String idInstTipoExp;
    private Date fechapresentacion;
    private Date fechalimitepresentacion;
    private boolean historico;
    private String observacionesDictamen;
    private String observaciones;

    private String[] prestacion;
    private String[] prestacionesRechazadas;

    //Impugnacion
    private boolean requiereTurn;
    private boolean bis;
    private Date fechaPublicacion;
    private String nImpugnacion;
    private String observacionesImpugnacion;
    private String sentidoAuto;
    private String autoResolutorio;
    private Date fechaAuto;
    private String impugnacion;
    private String impugnacionDesc;

    private Date fechaEstadoNew;
    private String estadoNew;

    private String numDesigna;

    //Procurador asociado
    private Date fechaDesProc;
    private String idProcurador;
    private Short idInstitucionProc;
    private String numerodesignaproc;
    private String nombreApProcurador;

    //Tarjeta Dictamen
    private Short iddictamen;
    private Date fechaDictamen;
    private Short idTipoDictamen;
    private Short fundamentoCalif;
    private String dictamen;
    private String dilnigproc;

    //REGTEL
    private String identificadords;

    // Estados de solicitud de expediente económico
    private String[] estadosSolicitudExpEco;

    // Check que indica si se busca EJGs por estados concretos o que hayan pasado por ese estado en algún momento
    private boolean ultimoEstado;
    
    private boolean informacionEconomica;
    private int editableComision;

    private Long idExpedienteExt;

    /**
     *
     **/
    public EjgItem idEJG(String idEJG) {
        this.idEJG = idEJG;
        return this;
    }

    @JsonProperty("idEJG")
    public String getIdEJG() {
        return idEJG;
    }

    public void setIdEJG(String idEJG) {
        this.idEJG = idEJG;
    }

    /**
     *
     **/
    public EjgItem colegio(String colegio) {
        this.colegio = colegio;
        return this;
    }

    @JsonProperty("colegio")
    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    /**
     *
     **/
    public EjgItem annio(String annio) {
        this.annio = annio;
        return this;
    }

    @JsonProperty("annio")
    public String getAnnio() {
        return annio;
    }

    public void setAnnio(String annio) {
        this.annio = annio;
    }

    /**
     *
     **/
    public EjgItem numero(String numero) {
        this.numero = numero;
        return this;
    }

    @JsonProperty("numero")
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     *
     **/
    public EjgItem tipoEJG(String tipoEJG) {
        this.tipoEJG = tipoEJG;
        return this;
    }

    @JsonProperty("tipoEJG")
    public String getTipoEJG() {
        return tipoEJG;
    }

    public void setTipoEJG(String tipoEJG) {
        this.tipoEJG = tipoEJG;
    }

    /**
     *
     **/
    public EjgItem tipoEJGColegio(String tipoEJGColegio) {
        this.tipoEJGColegio = tipoEJGColegio;
        return this;
    }

    @JsonProperty("tipoEJGColegio")
    public String getTipoEJGColegio() {
        return tipoEJGColegio;
    }

    public void setTipoEJGColegio(String tipoEJGColegio) {
        this.tipoEJGColegio = tipoEJGColegio;
    }

    /**
     *
     **/
    public EjgItem creadoDesde(String creadoDesde) {
        this.creadoDesde = creadoDesde;
        return this;
    }

    @JsonProperty("creadoDesde")
    public String getCreadoDesde() {
        return creadoDesde;
    }

    public void setCreadoDesde(String creadoDesde) {
        this.creadoDesde = creadoDesde;
    }

    /**
     *
     **/
    public EjgItem fechaAperturaDesd(Date fechaAperturaDesd) {
        this.fechaAperturaDesd = fechaAperturaDesd;
        return this;
    }

    @JsonProperty("fechaAperturaDesd")
    public Date getFechaAperturaDesd() {
        return fechaAperturaDesd;
    }

    public void setFechaAperturaDesd(Date fechaAperturaDesd) {
        this.fechaAperturaDesd = fechaAperturaDesd;
    }

    /**
     *
     **/
    public EjgItem fechaAperturaHast(Date fechaAperturaHast) {
        this.fechaAperturaHast = fechaAperturaHast;
        return this;
    }

    @JsonProperty("fechaAperturaHast")
    public Date getFechaAperturaHast() {
        return fechaAperturaHast;
    }

    public void setFechaAperturaHast(Date fechaAperturaHast) {
        this.fechaAperturaHast = fechaAperturaHast;
    }

    /**
     *
     **/
    public EjgItem fechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
        return this;
    }

    @JsonProperty("fechaApertura")
    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /**
     *
     **/
    public EjgItem fechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    @JsonProperty("fechaModificacion")
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setfechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     *
     **/
    public EjgItem estadoEJG(String estadoEJG) {
        this.estadoEJG = estadoEJG;
        return this;
    }

    @JsonProperty("estadoEJG")
    public String getEstadoEJG() {
        return estadoEJG;
    }

    public void setEstadoEJG(String estadoEJG) {
        this.estadoEJG = estadoEJG;
    }

    /**
     *
     **/
    public EjgItem fechaEstadoDesd(Date fechaEstadoDesd) {
        this.fechaEstadoDesd = fechaEstadoDesd;
        return this;
    }

    @JsonProperty("fechaEstadoDesd")
    public Date getFechaEstadoDesd() {
        return fechaEstadoDesd;
    }

    public void setFechaEstadoDesd(Date fechaEstadoDesd) {
        this.fechaEstadoDesd = fechaEstadoDesd;
    }

    /**
     *
     **/
    public EjgItem fechaEstadoHast(Date fechaEstadoHast) {
        this.fechaEstadoHast = fechaEstadoHast;
        return this;
    }

    @JsonProperty("fechaEstadoHast")
    public Date getFechaEstadoHast() {
        return fechaEstadoHast;
    }

    public void setFechaEstadoHast(Date fechaEstadoHast) {
        this.fechaEstadoHast = fechaEstadoHast;
    }

    /**
     *
     **/
    public EjgItem fechaLimiteDesd(Date fechaLimiteDesd) {
        this.fechaLimiteDesd = fechaLimiteDesd;
        return this;
    }

    @JsonProperty("fechaLimiteDesd")
    public Date getFechaLimiteDesd() {
        return fechaLimiteDesd;
    }

    public void setFechaLimiteDesd(Date fechaLimiteDesd) {
        this.fechaLimiteDesd = fechaLimiteDesd;
    }

    /**
     *
     **/
    public EjgItem fechaLimiteHast(Date fechaLimiteHast) {
        this.fechaLimiteHast = fechaLimiteHast;
        return this;
    }

    @JsonProperty("fechaLimiteHast")
    public Date getFechaLimiteHast() {
        return fechaLimiteHast;
    }

    public void setFechaLimiteHast(Date fechaLimiteHast) {
        this.fechaLimiteHast = fechaLimiteHast;
    }

    /**
     *
     **/
    public EjgItem dictamen(String dictamen) {
        this.dictamen = dictamen;
        return this;
    }

    @JsonProperty("dictamen")
    public String getDictamen() {
        return dictamen;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    /**
     *
     **/
    public EjgItem fundamentoCalif(Short fundamentoCalif) {
        this.fundamentoCalif = fundamentoCalif;
        return this;
    }

    @JsonProperty("fundamentoCalif")
    public Short getFundamentoCalif() {
        return fundamentoCalif;
    }

    public void setFundamentoCalif(Short fundamentoCalif) {
        this.fundamentoCalif = fundamentoCalif;
    }

    /**
     *
     **/
    public EjgItem fechaDictamenDesd(Date fechaDictamenDesd) {
        this.fechaDictamenDesd = fechaDictamenDesd;
        return this;
    }

    @JsonProperty("fechaDictamenDesd")
    public Date getFechaDictamenDesd() {
        return fechaDictamenDesd;
    }

    public void setFechaDictamenDesd(Date fechaDictamenDesd) {
        this.fechaDictamenDesd = fechaDictamenDesd;
    }

    /**
     *
     **/
    public EjgItem fechaDictamenHast(Date fechaDictamenHast) {
        this.fechaDictamenHast = fechaDictamenHast;
        return this;
    }

    @JsonProperty("fechaDictamenHast")
    public Date getFechaDictamenHast() {
        return fechaDictamenHast;
    }

    public void setFechaDictamenHast(Date fechaDictamenHast) {
        this.fechaDictamenHast = fechaDictamenHast;
    }

    /**
     *
     **/
    public EjgItem resolucion(String resolucion) {
        this.resolucion = resolucion;
        return this;
    }

    @JsonProperty("resolucion")
    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    /**
     *
     **/
    public EjgItem fundamentoJuridico(String fundamentoJuridico) {
        this.fundamentoJuridico = fundamentoJuridico;
        return this;
    }

    @JsonProperty("fundamentoJuridico")
    public String getFundamentoJuridico() {
        return fundamentoJuridico;
    }

    public void setFundamentoJuridico(String fundamentoJuridico) {
        this.fundamentoJuridico = fundamentoJuridico;
    }

    /**
     *
     **/
    public EjgItem fechaResolucionDesd(Date fechaResolucionDesd) {
        this.fechaResolucionDesd = fechaResolucionDesd;
        return this;
    }

    @JsonProperty("fechaResolucionDesd")
    public Date getFechaResolucionDesd() {
        return fechaResolucionDesd;
    }

    public void setFechaResolucionDesd(Date fechaResolucionDesd) {
        this.fechaResolucionDesd = fechaResolucionDesd;
    }

    /**
     *
     **/
    public EjgItem fechaResolucionHast(Date fechaResolucionHast) {
        this.fechaResolucionHast = fechaResolucionHast;
        return this;
    }

    @JsonProperty("fechaResolucionHast")
    public Date getFechaResolucionHast() {
        return fechaResolucionHast;
    }

    public void setFechaResolucionHast(Date fechaResolucionHast) {
        this.fechaResolucionHast = fechaResolucionHast;
    }

    /**
     *
     **/
    public EjgItem impugnacion(String impugnacion) {
        this.impugnacion = impugnacion;
        return this;
    }

    @JsonProperty("impugnacion")
    public String getImpugnacion() {
        return impugnacion;
    }

    public void setImpugnacion(String impugnacion) {
        this.impugnacion = impugnacion;
    }

    /**
     *
     **/
    public EjgItem fundamentoImpuganacion(String fundamentoImpuganacion) {
        this.fundamentoImpuganacion = fundamentoImpuganacion;
        return this;
    }

    @JsonProperty("fundamentoImpuganacion")
    public String getFundamentoImpuganacion() {
        return fundamentoImpuganacion;
    }

    public void setFundamentoImpuganacion(String fundamentoImpuganacion) {
        this.fundamentoImpuganacion = fundamentoImpuganacion;
    }

    /**
     *
     **/
    public EjgItem fechaImpugnacionDesd(Date fechaImpugnacionDesd) {
        this.fechaImpugnacionDesd = fechaImpugnacionDesd;
        return this;
    }

    @JsonProperty("fechaImpugnacionDesd")
    public Date getFechaImpugnacionDesd() {
        return fechaImpugnacionDesd;
    }

    public void setFechaImpugnacionDesd(Date fechaImpugnacionDesd) {
        this.fechaImpugnacionDesd = fechaImpugnacionDesd;
    }

    /**
     *
     **/
    public EjgItem fechaImpugnacionHast(Date fechaImpugnacionHast) {
        this.fechaImpugnacionHast = fechaImpugnacionHast;
        return this;
    }

    @JsonProperty("fechaImpugnacionHast")
    public Date getFechaImpugnacionHast() {
        return fechaImpugnacionHast;
    }

    public void setFechaImpugnacionHast(Date fechaImpugnacionHast) {
        this.fechaImpugnacionHast = fechaImpugnacionHast;
    }

    /**
     *
     **/
    public EjgItem juzgado(String juzgado) {
        this.juzgado = juzgado;
        return this;
    }

    @JsonProperty("juzgado")
    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    /**
     *
     **/
    public EjgItem asunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    @JsonProperty("asunto")
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     *
     **/
    public EjgItem calidad(String calidad) {
        this.calidad = calidad;
        return this;
    }

    @JsonProperty("calidad")
    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    /**
     *
     **/
    public EjgItem perceptivo(String perceptivo) {
        this.perceptivo = perceptivo;
        return this;
    }

    @JsonProperty("perceptivo")
    public String getPerceptivo() {
        return perceptivo;
    }

    public void setPerceptivo(String perceptivo) {
        this.perceptivo = perceptivo;
    }

    /**
     *
     **/
    public EjgItem renuncia(String renuncia) {
        this.renuncia = renuncia;
        return this;
    }

    @JsonProperty("renuncia")
    public String getRenuncia() {
        return renuncia;
    }

    public void setRenuncia(String renuncia) {
        this.renuncia = renuncia;
    }

    /**
     *
     **/
    public EjgItem numAnnioProcedimiento(String numAnnioProcedimiento) {
        this.numAnnioProcedimiento = numAnnioProcedimiento;
        return this;
    }

    @JsonProperty("numAnnioProcedimiento")
    public String getNumAnnioProcedimiento() {
        return numAnnioProcedimiento;
    }

    public void setNumAnnioProcedimiento(String numAnnioProcedimiento) {
        this.numAnnioProcedimiento = numAnnioProcedimiento;
    }

    /**
     *
     **/
    public EjgItem procedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
        return this;
    }

    @JsonProperty("procedimiento")
    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     *
     **/
    public EjgItem NIG(String NIG) {
        this.NIG = NIG;
        return this;
    }

    @JsonProperty("nig")
    public String getNig() {
        return NIG;
    }

    public void setNig(String NIG) {
        this.NIG = NIG;
    }

    /**
     *
     **/
    public EjgItem annioCAJG(String annioCAJG) {
        this.annioCAJG = annioCAJG;
        return this;
    }

    @JsonProperty("annioCAJG")
    public String getAnnioCAJG() {
        return annioCAJG;
    }

    public void setAnnioCAJG(String annioCAJG) {
        this.annioCAJG = annioCAJG;
    }

    /**
     *
     **/
    public EjgItem numCAJG(String numCAJG) {
        this.numCAJG = numCAJG;
        return this;
    }

    @JsonProperty("numCAJG")
    public String getNumCAJG() {
        return numCAJG;
    }

    public void setNumCAJG(String numCAJG) {
        this.numCAJG = numCAJG;
    }

    /**
     *
     **/
    public EjgItem annioActa(String annioActa) {
        this.annioActa = annioActa;
        return this;
    }

    @JsonProperty("annioActa")
    public String getAnnioActa() {
        return annioActa;
    }

    public void setAnnioActa(String annioActa) {
        this.annioActa = annioActa;
    }

    /**
     *
     **/
    public EjgItem numActa(String numActa) {
        this.numActa = numActa;
        return this;
    }

    @JsonProperty("numActa")
    public String getNumActa() {
        return numActa;
    }

    public void setNumActa(String numActa) {
        this.numActa = numActa;
    }

    /**
     *
     **/
    public EjgItem ponente(String ponente) {
        this.ponente = ponente;
        return this;
    }

    @JsonProperty("ponente")
    public String getPonente() {
        return ponente;
    }

    public void setPonente(String ponente) {
        this.ponente = ponente;
    }

    /**
     *
     **/
    public EjgItem fechaPonenteDesd(Date fechaPonenteDesd) {
        this.fechaPonenteDesd = fechaPonenteDesd;
        return this;
    }

    @JsonProperty("fechaPonenteDesd")
    public Date getFechaPonenteDesd() {
        return fechaPonenteDesd;
    }

    public void setFechaPonenteDesd(Date fechaPonenteDesd) {
        this.fechaPonenteDesd = fechaPonenteDesd;
    }

    /**
     *
     **/
    public EjgItem fechaPonenteHast(Date fechaPonenteHast) {
        this.fechaPonenteHast = fechaPonenteHast;
        return this;
    }

    @JsonProperty("fechaPonenteHast")
    public Date getFechaPonenteHast() {
        return fechaPonenteHast;
    }

    public void setFechaPonenteHast(Date fechaPonenteHast) {
        this.fechaPonenteHast = fechaPonenteHast;
    }

    /**
     *
     **/
    public EjgItem numRegRemesa(String numRegRemesa) {
        this.numRegRemesa = numRegRemesa;
        return this;
    }

    @JsonProperty("numRegRemesa")
    public String getNumRegRemesa() {
        return numRegRemesa;
    }

    public void setNumRegRemesa(String numRegRemesa) {
        this.numRegRemesa = numRegRemesa;
    }

    /**
     *
     **/
    public EjgItem numRegRemesa1(String numRegRemesa1) {
        this.numRegRemesa1 = numRegRemesa1;
        return this;
    }

    @JsonProperty("numRegRemesa1")
    public String getNumRegRemesa1() {
        return numRegRemesa1;
    }

    public void setNumRegRemesa1(String numRegRemesa1) {
        this.numRegRemesa1 = numRegRemesa1;
    }

    /**
     *
     **/
    public EjgItem numRegRemesa2(String numRegRemesa2) {
        this.numRegRemesa2 = numRegRemesa2;
        return this;
    }

    @JsonProperty("numRegRemesa2")
    public String getNumRegRemesa2() {
        return numRegRemesa2;
    }

    public void setNumRegRemesa2(String numRegRemesa2) {
        this.numRegRemesa2 = numRegRemesa2;
    }

    /**
     *
     **/
    public EjgItem numRegRemesa3(String numRegRemesa3) {
        this.numRegRemesa3 = numRegRemesa3;
        return this;
    }

    @JsonProperty("numRegRemesa3")
    public String getNumRegRemesa3() {
        return numRegRemesa3;
    }

    public void setNumRegRemesa3(String numRegRemesa3) {
        this.numRegRemesa3 = numRegRemesa3;
    }

    /**
     *
     **/
    public EjgItem nif(String nif) {
        this.nif = nif;
        return this;
    }

    @JsonProperty("nif")
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     *
     **/
    public EjgItem apellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    @JsonProperty("apellidos")
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     *
     **/
    public EjgItem nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @JsonProperty("nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     **/
    public EjgItem rol(String rol) {
        this.rol = rol;
        return this;
    }

    @JsonProperty("rol")
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     *
     **/
    public EjgItem turno(String turno) {
        this.turno = turno;
        return this;
    }

    @JsonProperty("turno")
    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     *
     **/
    public EjgItem guardia(String guardia) {
        this.guardia = guardia;
        return this;
    }

    @JsonProperty("guardia")
    public String getGuardia() {
        return guardia;
    }

    public void setGuardia(String guardia) {
        this.guardia = guardia;
    }

    @JsonProperty("numColegiado")
    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    /**
     *
     **/
    public EjgItem apellidosYNombre(String apellidosYNombre) {
        this.apellidosYNombre = apellidosYNombre;
        return this;
    }

    @JsonProperty("apellidosYNombre")
    public String getApellidosYNombre() {
        return apellidosYNombre;
    }

    public void setApellidosYNombre(String apellidosYNombre) {
        this.apellidosYNombre = apellidosYNombre;
    }

    /**
     *
     **/
    public EjgItem tipoLetrado(String tipoLetrado) {
        this.tipoLetrado = tipoLetrado;
        return this;
    }

    @JsonProperty("tipoLetrado")
    public String getTipoLetrado() {
        return tipoLetrado;
    }

    public void setTipoLetrado(String tipoLetrado) {
        this.tipoLetrado = tipoLetrado;
    }

    /**
     *
     **/
    public EjgItem idPersona(String idPersona) {
        this.idPersona = idPersona;
        return this;
    }

    @JsonProperty("idPersona")
    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    /**
     *
     **/
    public EjgItem turnoDes(String turnoDes) {
        this.turnoDes = turnoDes;
        return this;
    }

    @JsonProperty("turnoDes")
    public String getTurnoDes() {
        return turnoDes;
    }

    public void setTurnoDes(String turnoDes) {
        this.turnoDes = turnoDes;
    }

    /**
     *
     **/
    public EjgItem nombreApeSolicitante(String nombreApeSolicitante) {
        this.nombreApeSolicitante = nombreApeSolicitante;
        return this;
    }

    @JsonProperty("nombreApeSolicitante")
    public String getNombreApeSolicitante() {
        return nombreApeSolicitante;
    }

    public void setNombreApeSolicitante(String nombreApeSolicitante) {
        this.nombreApeSolicitante = nombreApeSolicitante;
    }

    /**
     *
     **/
    public EjgItem dictamenSing(String dictamenSing) {
        this.dictamenSing = dictamenSing;
        return this;
    }

    @JsonProperty("dictamenSing")
    public String getDictamenSing() {
        return dictamenSing;
    }

    public void setDictamenSing(String dictamenSing) {
        this.dictamenSing = dictamenSing;
    }

    /**
     *
     **/
    public EjgItem fechapresentacion(Date fechapresentacion) {
        this.fechapresentacion = fechapresentacion;
        return this;
    }

    @JsonProperty("fechapresentacion")
    public Date getFechapresentacion() {
        return fechapresentacion;
    }

    public void setFechapresentacion(Date fechapresentacion) {
        this.fechapresentacion = fechapresentacion;
    }

    /**
     *
     **/
    public EjgItem fechalimitepresentacion(Date fechalimitepresentacion) {
        this.fechalimitepresentacion = fechalimitepresentacion;
        return this;
    }

    @JsonProperty("fechalimitepresentacion")
    public Date getFechalimitepresentacion() {
        return fechalimitepresentacion;
    }

    public void setFechalimitepresentacion(Date fechalimitepresentacion) {
        this.fechalimitepresentacion = fechalimitepresentacion;
    }

    /**
     *
     **/
    public EjgItem idTurno(String idTurno) {
        this.idTurno = idTurno;
        return this;
    }

    @JsonProperty("idTurno")
    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /**
     *
     **/
    public EjgItem numEjg(String numEjg) {
        this.numEjg = numEjg;
        return this;
    }

    @JsonProperty("numEjg")
    public String getNumEjg() {
        return numEjg;
    }

    public void setNumEjg(String numEjg) {
        this.numEjg = numEjg;
    }

    /**
     *
     **/
    public EjgItem idGuardia(String idGuardia) {
        this.idGuardia = idGuardia;
        return this;
    }

    @JsonProperty("idGuardia")
    public String getIdGuardia() {
        return idGuardia;
    }

    public void setIdGuardia(String idGuardia) {
        this.idGuardia = idGuardia;
    }

    /**
     *
     **/
    public EjgItem historico(boolean historico) {
        this.historico = historico;
        return this;
    }

    @JsonProperty("historico")
    public boolean isHistorico() {
        return historico;
    }

    public void setHistorico(boolean historico) {
        this.historico = historico;
    }

    /**
     *
     **/
    public EjgItem fechaDictamen(Date fechaDictamen) {
        this.fechaDictamen = fechaDictamen;
        return this;
    }

    @JsonProperty("fechaDictamen")
    public Date getFechaDictamen() {
        return fechaDictamen;
    }

    public void setFechaDictamen(Date fechaDictamen) {
        this.fechaDictamen = fechaDictamen;
    }

    /**
     *
     **/
    public EjgItem observacionesDictamen(String observacionesDictamen) {
        this.observacionesDictamen = observacionesDictamen;
        return this;
    }

    @JsonProperty("observacionesDictamen")
    public String getObservacionesDictamen() {
        return observacionesDictamen;
    }

    public void setObservacionesDictamen(String observacionesDictamen) {
        this.observacionesDictamen = observacionesDictamen;
    }

    /**
     *
     **/
    public EjgItem fundamentoCalifDes(String fundamentoCalifDes) {
        this.fundamentoCalifDes = fundamentoCalifDes;
        return this;
    }

    @JsonProperty("fundamentoCalifDes")
    public String getFundamentoCalifDes() {
        return fundamentoCalifDes;
    }

    public void setFundamentoCalifDes(String fundamentoCalifDes) {
        this.fundamentoCalifDes = fundamentoCalifDes;
    }

    /**
     *
     **/
    public EjgItem iddictamen(Short iddictamen) {
        this.iddictamen = iddictamen;
        return this;
    }

    @JsonProperty("iddictamen")
    public Short getIddictamen() {
        return iddictamen;
    }

    public void setIddictamen(Short iddictamen) {
        this.iddictamen = iddictamen;
    }

    //impugnacion

    /**
     *
     **/
    public EjgItem requiereTurn(boolean requiereTurn) {
        this.requiereTurn = requiereTurn;
        return this;
    }

    @JsonProperty("requiereTurn")
    public boolean isRequiereTurn() {
        return requiereTurn;
    }

    public void setRequiereTurn(boolean requiereTurn) {
        this.requiereTurn = requiereTurn;
    }

    /**
     *
     **/
    public EjgItem bis(boolean bis) {
        this.bis = bis;
        return this;
    }

    @JsonProperty("bis")
    public boolean getBis() {
        return bis;
    }

    public void setBis(boolean bis) {
        this.bis = bis;
    }

    /**
     *
     **/
    public EjgItem fechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
        return this;
    }

    @JsonProperty("fechaPublicacion")
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     *
     **/
    public EjgItem nImpugnacion(String nImpugnacion) {
        this.nImpugnacion = nImpugnacion;
        return this;
    }

    @JsonProperty("nImpugnacion")
    public String getnImpugnacion() {
        return nImpugnacion;
    }

    public void setnImpugnacion(String nImpugnacion) {
        this.nImpugnacion = nImpugnacion;
    }

    /**
     *
     **/
    public EjgItem observacionesImpugnacion(String observacionesImpugnacion) {
        this.observacionesImpugnacion = observacionesImpugnacion;
        return this;
    }

    @JsonProperty("observacionesImpugnacion")
    public String getObservacionesImpugnacion() {
        return observacionesImpugnacion;
    }

    public void setObservacionesImpugnacion(String observacionesImpugnacion) {
        this.observacionesImpugnacion = observacionesImpugnacion;
    }

    /**
     *
     **/
    public EjgItem sentidoAuto(String sentidoAuto) {
        this.sentidoAuto = sentidoAuto;
        return this;
    }

    @JsonProperty("sentidoAuto")
    public String getSentidoAuto() {
        return sentidoAuto;
    }

    public void setSentidoAuto(String sentidoAuto) {
        this.sentidoAuto = sentidoAuto;
    }

    /**
     *
     **/
    public EjgItem autoResolutorio(String autoResolutorio) {
        this.autoResolutorio = autoResolutorio;
        return this;
    }

    @JsonProperty("autoResolutorio")
    public String getAutoResolutorio() {
        return autoResolutorio;
    }

    public void setAutoResolutorio(String autoResolutorio) {
        this.autoResolutorio = autoResolutorio;
    }

    /**
     *
     **/
    public EjgItem fechaAuto(Date fechaAuto) {
        this.fechaAuto = fechaAuto;
        return this;
    }

    @JsonProperty("fechaAuto")
    public Date getFechaAuto() {
        return fechaAuto;
    }

    public void setFechaAuto(Date fechaAuto) {
        this.fechaAuto = fechaAuto;
    }

    /**
     *
     **/
    public EjgItem fechaEstadoNew(Date fechaEstadoNew) {
        this.fechaEstadoNew = fechaEstadoNew;
        return this;
    }

    @JsonProperty("fechaEstadoNew")
    public Date getFechaEstadoNew() {
        return fechaEstadoNew;
    }

    public void setFechaEstadoNew(Date fechaEstadoNew) {
        this.fechaEstadoNew = fechaEstadoNew;
    }

    /**
     *
     **/
    public EjgItem estadoNew(String estadoNew) {
        this.estadoNew = estadoNew;
        return this;
    }

    @JsonProperty("estadoNew")
    public String getEstadoNew() {
        return estadoNew;
    }

    public void seteEstadoNew(String estadoNew) {
        this.estadoNew = estadoNew;
    }


    /**
     *
     **/
    public EjgItem idInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
        return this;
    }

    @JsonProperty("idInstitucion")
    public String getidInstitucion() {
        return idInstitucion;
    }

    public void setidInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getIdInstTipoExp() {
        return idInstTipoExp;
    }

    public void setIdInstTipoExp(String idInstTipoExp) {
        this.idInstTipoExp = idInstTipoExp;
    }

    public String[] getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(String[] prestacion) {
        this.prestacion = prestacion;
    }

    public String[] getPrestacionesRechazadas() {
        return prestacionesRechazadas;
    }

    public void setPrestacionesRechazadas(String[] prestacionesRechazadas) {
        this.prestacionesRechazadas = prestacionesRechazadas;
    }

    public String getNumDesigna() {
        return numDesigna;
    }

    public void setNumDesigna(String numDesigna) {
        this.numDesigna = numDesigna;
    }

    public String getAnioexpInsos() {
        return anioexpInsos;
    }

    public void setAnioexpInsos(String anioexpInsos) {
        this.anioexpInsos = anioexpInsos;
    }

    public String getNumeroexpInsos() {
        return numeroexpInsos;
    }

    public void setNumeroexpInsos(String numeroexpInsos) {
        this.numeroexpInsos = numeroexpInsos;
    }

    public String getIdTipoExpInsos() {
        return idTipoExpInsos;
    }

    public void setIdTipoExpInsos(String idTipoExpInsos) {
        this.idTipoExpInsos = idTipoExpInsos;
    }

    public String getIdPersonajg() {
        return idPersonajg;
    }

    public void setIdPersonajg(String idPersonajg) {
        this.idPersonajg = idPersonajg;
    }

    public Short getIdsituacion() {
        return idsituacion;
    }

    public void setIdsituacion(Short idsituacion) {
        this.idsituacion = idsituacion;
    }

    public String getNumerodiligencia() {
        return numerodiligencia;
    }

    public void setNumerodiligencia(String numerodiligencia) {
        this.numerodiligencia = numerodiligencia;
    }

    public Long getComisaria() {
        return comisaria;
    }

    public void setComisaria(Long comisaria) {
        this.comisaria = comisaria;
    }

    public String getDelitos() {
        return delitos;
    }

    public void setDelitos(String delitos) {
        this.delitos = delitos;
    }

    public String getIdProcurador() {
        return idProcurador;
    }

    public void setIdProcurador(String idProcurador) {
        this.idProcurador = idProcurador;
    }

    public Short getIdInstitucionProc() {
        return idInstitucionProc;
    }

    public void setIdInstitucionProc(Short idInstitucionProc) {
        this.idInstitucionProc = idInstitucionProc;
    }

    public Short getIdPretension() {
        return idPretension;
    }

    public void setIdPretension(Short idPretension) {
        this.idPretension = idPretension;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaDesProc() {
        return fechaDesProc;
    }

    public void setFechaDesProc(Date fechaDesProc) {
        this.fechaDesProc = fechaDesProc;
    }

    public String getNumerodesignaproc() {
        return numerodesignaproc;
    }

    public void setNumerodesignaproc(String numerodesignaproc) {
        this.numerodesignaproc = numerodesignaproc;
    }

    public String getNombreApProcurador() {
        return nombreApProcurador;
    }

    public void setNombreApProcurador(String nombreApProcurador) {
        this.nombreApProcurador = nombreApProcurador;
    }

    public Short getIdTipoDictamen() {
        return idTipoDictamen;
    }

    public void setIdTipoDictamen(Short idTipoDictamen) {
        this.idTipoDictamen = idTipoDictamen;
    }

    public String getIdentificadords() {
        return identificadords;
    }

    public void setIdentificadords(String identificadords) {
        this.identificadords = identificadords;
    }

    public String getDilnigproc() {
        return dilnigproc;
    }

    public void setDilnigproc(String dilnigproc) {
        this.dilnigproc = dilnigproc;
    }

    public String getImpugnacionDesc() {
        return impugnacionDesc;
    }

    public void setImpugnacionDesc(String impugnacionDesc) {
        this.impugnacionDesc = impugnacionDesc;
    }

    public String[] getEstadosSolicitudExpEco() {
        return estadosSolicitudExpEco;
    }

    public void setEstadosSolicitudExpEco(String[] estadosSolicitudExpEco) {
        this.estadosSolicitudExpEco = estadosSolicitudExpEco;
    }

    public boolean isUltimoEstado() {
        return ultimoEstado;
    }

    public void setUltimoEstado(boolean ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
    }

	public boolean isInformacionEconomica() {
		return informacionEconomica;
	}

	public void setInformacionEconomica(boolean informacionEconomica) {
		this.informacionEconomica = informacionEconomica;
	}

	public int getEditableComision() {
		return editableComision;
	}

	public void setEditableComision(int editableComision) {
		this.editableComision = editableComision;
	}

    public Long getIdExpedienteExt() {
        return idExpedienteExt;
    }

    public void setIdExpedienteExt(Long idExpedienteExt) {
        this.idExpedienteExt = idExpedienteExt;
    }
}
