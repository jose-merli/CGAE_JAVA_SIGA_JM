package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class FacAbonoItem {
	
	private Long idAbono;
	private String motivos;
	private Date fechaEmision;
	private Date fechaEmisionDesde;
	private Date fechaEmisionHasta;
	private String contabilizada;
	private Long idPersona;
	private int idCuenta;
	private String  idFactura;
	private Long idPagoJG;
	private String numeroAbono;
	private String observaciones;
	private String forma;
	
	private int estado;
	private String estadoNombre;
	private double importeTotalNeto;
	private double importeTotalIVA;
	private double importeTotal;
	private double importeTotalDesde;
	private double importeTOtalHasta;
	private double importeTotalAbonadoEfectivo;
	private double importeTotalAbonadoBanco;
	private double importeTotalAbonado;
	private double importePendientePorAbonar;
	
	private int idPersonaDeudor;
	private int idCuentaDeudor;
	private int idPersonaOrigen;
	
	private String nombrePago;
	
	//Filtros - Agrupacion
    private String grupoFacturacionNombre;
    private Date pagoDesde;
    private Date pagoHasta;
    private String identificadorFicheroT;

    //Filtros - Colegiado
    private String colegioNombre;
    private int numColegio;
    private String numIdentificadorColegiado;
    private String apellidosColegiado;
    private String nombreColegiado;

    //Filtros - Sociedad
    private String colegioNombreSociedad;
    private String numIdentificadorSociedad;
    private String nombreSociedad;
    private String abreviaturaSociedad;
    
    //Aux
    private String ncolident;
    private String nombreCompleto;
    private String esSociedad;
    private String nombreGeneral;
    private String apellidosGeneral;
    
    
    
	public String getNombreGeneral() {
		return nombreGeneral;
	}
	public void setNombreGeneral(String nombreGeneral) {
		this.nombreGeneral = nombreGeneral;
	}
	public String getApellidosGeneral() {
		return apellidosGeneral;
	}
	public void setApellidosGeneral(String apellidosGeneral) {
		this.apellidosGeneral = apellidosGeneral;
	}
	public String getEsSociedad() {
		return esSociedad;
	}
	public void setEsSociedad(String esSociedad) {
		this.esSociedad = esSociedad;
	}
	public Long getIdAdono() {
		return idAbono;
	}
	public void setIdAdono(Long idAdono) {
		this.idAbono = idAdono;
	}
	public String getMotivos() {
		return motivos;
	}
	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmison) {
		this.fechaEmision = fechaEmison;
	}
	public Date getFechaEmisionDesde() {
		return fechaEmisionDesde;
	}
	public void setFechaEmisionDesde(Date fechaEmisionDesde) {
		this.fechaEmisionDesde = fechaEmisionDesde;
	}
	public Date getFechaEmisionHasta() {
		return fechaEmisionHasta;
	}
	public void setFechaEmisionHasta(Date fechaEmisionHasta) {
		this.fechaEmisionHasta = fechaEmisionHasta;
	}
	
	public String getContabilizada() {
		return contabilizada;
	}
	public void setContabilizada(String contabilizada) {
		this.contabilizada = contabilizada;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}
	public String getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}
	public Long getIdPagoJG() {
		return idPagoJG;
	}
	public void setIdPagoJG(Long idPagoJG) {
		this.idPagoJG = idPagoJG;
	}
	public String getNumeroAbono() {
		return numeroAbono;
	}
	public void setNumeroAbono(String numeroAbono) {
		this.numeroAbono = numeroAbono;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getEstadoNombre() {
		return estadoNombre;
	}
	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}
	public double getImporteTotalNeto() {
		return importeTotalNeto;
	}
	public void setImporteTotalNeto(double importeTotalNeto) {
		this.importeTotalNeto = importeTotalNeto;
	}
	public double getImporteTotalIVA() {
		return importeTotalIVA;
	}
	public void setImporteTotalIVA(double importeTotalIVA) {
		this.importeTotalIVA = importeTotalIVA;
	}
	public double getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	public double getImporteTotalDesde() {
		return importeTotalDesde;
	}
	public void setImporteTotalDesde(double importeTotalDesde) {
		this.importeTotalDesde = importeTotalDesde;
	}
	public double getImporteTOtalHasta() {
		return importeTOtalHasta;
	}
	public void setImporteTOtalHasta(double importeTOtalHasta) {
		this.importeTOtalHasta = importeTOtalHasta;
	}
	public double getImporteTotalAbonadoEfectivo() {
		return importeTotalAbonadoEfectivo;
	}
	public void setImporteTotalAbonadoEfectivo(double importeTotalAbonadoEfectivo) {
		this.importeTotalAbonadoEfectivo = importeTotalAbonadoEfectivo;
	}
	public double getImporteTotalAbonadoBanco() {
		return importeTotalAbonadoBanco;
	}
	public void setImporteTotalAbonadoBanco(double importeTotalAbonadoBanco) {
		this.importeTotalAbonadoBanco = importeTotalAbonadoBanco;
	}
	public double getImporteTotalAbonado() {
		return importeTotalAbonado;
	}
	public void setImporteTotalAbonado(double importeTotalAbonado) {
		this.importeTotalAbonado = importeTotalAbonado;
	}
	public double getImportePendientePorAbonar() {
		return importePendientePorAbonar;
	}
	public void setImportePendientePorAbonar(double importePendientePorAbonar) {
		this.importePendientePorAbonar = importePendientePorAbonar;
	}
	public int getIdPersonaDeudor() {
		return idPersonaDeudor;
	}
	public void setIdPersonaDeudor(int idPersonaDeudor) {
		this.idPersonaDeudor = idPersonaDeudor;
	}
	public int getIdCuentaDeudor() {
		return idCuentaDeudor;
	}
	public void setIdCuentaDeudor(int idCuentaDeudor) {
		this.idCuentaDeudor = idCuentaDeudor;
	}
	public int getIdPersonaOrigen() {
		return idPersonaOrigen;
	}
	public void setIdPersonaOrigen(int idPersonaOrigen) {
		this.idPersonaOrigen = idPersonaOrigen;
	}
	public String getGrupoFacturacionNombre() {
		return grupoFacturacionNombre;
	}
	public void setGrupoFacturacionNombre(String grupoFacturacionNombre) {
		this.grupoFacturacionNombre = grupoFacturacionNombre;
	}
	public Date getPagoDesde() {
		return pagoDesde;
	}
	public void setPagoDesde(Date pagoDesde) {
		this.pagoDesde = pagoDesde;
	}
	public Date getPagoHasta() {
		return pagoHasta;
	}
	public void setPagoHasta(Date pagoHasta) {
		this.pagoHasta = pagoHasta;
	}
	public String getIdentificadorFicheroT() {
		return identificadorFicheroT;
	}
	public void setIdentificadorFicheroT(String identificadorFicheroT) {
		this.identificadorFicheroT = identificadorFicheroT;
	}
	public String getColegioNombre() {
		return colegioNombre;
	}
	public void setColegioNombre(String colegioNombre) {
		this.colegioNombre = colegioNombre;
	}
	public int getNumColegio() {
		return numColegio;
	}
	public void setNumColegio(int numColegio) {
		this.numColegio = numColegio;
	}
	public String getNumIdentificadorColegiado() {
		return numIdentificadorColegiado;
	}
	public void setNumIdentificadorColegiado(String numIdentificadorColegiado) {
		this.numIdentificadorColegiado = numIdentificadorColegiado;
	}
	public String getApellidosColegiado() {
		return apellidosColegiado;
	}
	public void setApellidosColegiado(String apellidosColegiado) {
		this.apellidosColegiado = apellidosColegiado;
	}
	public String getNombreColegiado() {
		return nombreColegiado;
	}
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}
	public String getColegioNombreSociedad() {
		return colegioNombreSociedad;
	}
	public void setColegioNombreSociedad(String colegioNombreSociedad) {
		this.colegioNombreSociedad = colegioNombreSociedad;
	}
	public String getNumIdentificadorSociedad() {
		return numIdentificadorSociedad;
	}
	public void setNumIdentificadorSociedad(String numIdentificadorSociedad) {
		this.numIdentificadorSociedad = numIdentificadorSociedad;
	}
	public String getNombreSociedad() {
		return nombreSociedad;
	}
	public void setNombreSociedad(String nombreSociedad) {
		this.nombreSociedad = nombreSociedad;
	}
	public String getAbreviaturaSociedad() {
		return abreviaturaSociedad;
	}
	public void setAbreviaturaSociedad(String abreviaturaSociedad) {
		this.abreviaturaSociedad = abreviaturaSociedad;
	}
	public String getForma() {
		return forma;
	}
	public void setForma(String forma) {
		this.forma = forma;
	}
	public String getNcolident() {
		return ncolident;
	}
	public void setNcolident(String ncolident) {
		this.ncolident = ncolident;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getNombrePago() {
		return nombrePago;
	}
	public void setNombrePago(String nombrePago) {
		this.nombrePago = nombrePago;
	}
	
	
	
	
}
