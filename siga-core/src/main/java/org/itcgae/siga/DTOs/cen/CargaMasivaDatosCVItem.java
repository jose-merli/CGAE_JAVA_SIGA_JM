package org.itcgae.siga.DTOs.cen;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CargaMasivaDatosCVItem {
	public static final List<String> CAMPOSEJEMPLO = Arrays.asList(CargaMasivaDatosCVItem.COLEGIADONUMERO,CargaMasivaDatosCVItem.PERSONANIF,
			CargaMasivaDatosCVItem.C_FECHAINICIO,CargaMasivaDatosCVItem.C_FECHAFIN,CargaMasivaDatosCVItem.C_CREDITOS,CargaMasivaDatosCVItem.FECHAVERIFICACION,CargaMasivaDatosCVItem.C_DESCRIPCION
			,CargaMasivaDatosCVItem.TIPOCVCOD,CargaMasivaDatosCVItem.SUBTIPOCV1COD,CargaMasivaDatosCVItem.SUBTIPOCV2COD);
	public static final List<String> CAMPOSLOG = Arrays.asList(CargaMasivaDatosCVItem.COLEGIADONUMERO,CargaMasivaDatosCVItem.PERSONANIF,CargaMasivaDatosCVItem.PERSONANOMBRE,CargaMasivaDatosCVItem.C_IDPERSONA,
			CargaMasivaDatosCVItem.C_FECHAINICIO ,CargaMasivaDatosCVItem.C_FECHAFIN,CargaMasivaDatosCVItem.C_CREDITOS,CargaMasivaDatosCVItem.FECHAVERIFICACION,CargaMasivaDatosCVItem.C_DESCRIPCION
			,CargaMasivaDatosCVItem.TIPOCVCOD,CargaMasivaDatosCVItem.TIPOCVNOMBRE,CargaMasivaDatosCVItem.C_IDTIPOCV,CargaMasivaDatosCVItem.SUBTIPOCV1COD,CargaMasivaDatosCVItem.SUBTIPOCV1NOMBRE,CargaMasivaDatosCVItem.C_IDTIPOCVSUBTIPO1,CargaMasivaDatosCVItem.SUBTIPOCV2COD,CargaMasivaDatosCVItem.SUBTIPOCV2NOMBRE,CargaMasivaDatosCVItem.C_IDTIPOCVSUBTIPO2,CargaMasivaDatosCVItem.ERRORES);
	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx ="xlsx";
	public static final String nombreFicheroEjemplo ="PlantillaMasivaDatosCV";
	public static final String nombreFicheroError ="LogErrorCargaMasivaCV";
	
	
	public static final String C_IDTIPOCV = "IDTIPOCV";
    public static final String C_CREDITOS = "C_CREDITOS";
    public static final String C_IDPERSONA = "C_IDPERSONA";
    public static final String C_IDTIPOCVSUBTIPO1 = "C_IDTIPOCVSUBTIPO1";
    public static final String C_IDTIPOCVSUBTIPO2 = "C_IDTIPOCVSUBTIPO2";
	public static final String TIPOCVCOD = "TIPOCVCOD";
	public static final String SUBTIPOCV1COD = "SUBTIPOCV1COD";
	public static final String SUBTIPOCV2COD = "SUBTIPOCV2COD";
	public static final String TIPOCVNOMBRE = "TIPOCVNOMBRE";
	public static final String SUBTIPOCV1NOMBRE = "SUBTIPOCV1NOMBRE";
	public static final String SUBTIPOCV2NOMBRE = "SUBTIPOCV2NOMBRE";
	public static final String FECHAVERIFICACION = "FECHAVERIFICACION";
	
	public static final String C_FECHAINICIO = "C_FECHAINICIO";
    public static final String C_FECHAFIN = "C_FECHAFIN";
    public static final String C_DESCRIPCION = "C_DESCRIPCION";
    public static final String COLEGIADONUMERO = "COLEGIADONUMERO";
    public static final String PERSONANIF = "PERSONANIF";
    public static final String PERSONANOMBRE = "PERSONANOMBRE";
    public static final String ERRORES = "ERRORES";
    
	private String colegiadoNumero;
	private String personaNIF;
	private Date fechaInicio;
	private Date fechaFin;
	private Long creditos;
	private Date fechaVerificacion;
	private Short tipoCVCOD;
	private String subtipoCV1COD;
	private String subtipoCV2COD;
	private String personaNombre;
	private Long idPersona;
	private String tipoCVNombre;
	private Short idTipoCV;
	private String subTipoCV1Nombre;
	private Short idTipoCVSubtipo1;
	private String subtipoCV2Nombre;
	private Short idTipoCVSubtipo2;
	private String errores;
	private String descripcion;
	private Short idInstitucion;
	private Short subTipo1IdInstitucion;
	private Short subTipo2IdInstitucion;
	
	public String getColegiadoNumero() {
		return colegiadoNumero;
	}
	public void setColegiadoNumero(String colegiadoNumero) {
		this.colegiadoNumero = colegiadoNumero;
	}
	public String getPersonaNIF() {
		return personaNIF;
	}
	public void setPersonaNIF(String personaNIF) {
		this.personaNIF = personaNIF;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date c_fechaInicio) {
		this.fechaInicio = c_fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date c_fechaFin) {
		this.fechaFin = c_fechaFin;
	}
	public Long getCreditos() {
		return creditos;
	}
	public void setCreditos(Long c_creditos) {
		this.creditos = c_creditos;
	}
	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}
	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}
	public Short getTipoCVCOD() {
		return tipoCVCOD;
	}
	public void setTipoCVCOD(Short tipoCVCOD) {
		this.tipoCVCOD = tipoCVCOD;
	}
	public String getSubtipoCV1COD() {
		return subtipoCV1COD;
	}
	public void setSubtipoCV1COD(String subtipoCV1COD) {
		this.subtipoCV1COD = subtipoCV1COD;
	}
	public String getSubtipoCV2COD() {
		return subtipoCV2COD;
	}
	public void setSubtipoCV2COD(String subtipoCV2COD) {
		this.subtipoCV2COD = subtipoCV2COD;
	}
	public String getPersonaNombre() {
		return personaNombre;
	}
	public void setPersonaNombre(String personaNombre) {
		this.personaNombre = personaNombre;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long c_idPersona) {
		this.idPersona = c_idPersona;
	}
	public String getTipoCVNombre() {
		return tipoCVNombre;
	}
	public void setTipoCVNombre(String tipoCVNombre) {
		this.tipoCVNombre = tipoCVNombre;
	}
	public Short getIdTipoCV() {
		return idTipoCV;
	}
	public void setIdTipoCV(Short c_idTipoCV) {
		this.idTipoCV = c_idTipoCV;
	}
	public String getSubTipoCV1Nombre() {
		return subTipoCV1Nombre;
	}
	public void setSubTipoCV1Nombre(String subTipoCV1Nombre) {
		this.subTipoCV1Nombre = subTipoCV1Nombre;
	}
	public Short getIdTipoCVSubtipo1() {
		return idTipoCVSubtipo1;
	}
	public void setIdTipoCVSubtipo1(Short c_idTipoCVSubtipo1) {
		this.idTipoCVSubtipo1 = c_idTipoCVSubtipo1;
	}
	public String getSubtipoCV2Nombre() {
		return subtipoCV2Nombre;
	}
	public void setSubtipoCV2Nombre(String subtipoCV2Nombre) {
		this.subtipoCV2Nombre = subtipoCV2Nombre;
	}
	public Short getIdTipoCVSubtipo2() {
		return idTipoCVSubtipo2;
	}
	public void setIdTipoCVSubtipo2(Short c_idTipoCVSubtipo2) {
		this.idTipoCVSubtipo2 = c_idTipoCVSubtipo2;
	}
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String c_descripcion) {
		this.descripcion = c_descripcion;
	}
	public Short getIdinstitucionSubt1() {
		return subTipo1IdInstitucion;
	}
	public Short getIdinstitucionSubt2() {
		return subTipo2IdInstitucion;
	}
	public void setIdinstitucionSubt1(Short subTipo1IdInstitucion) {
	   this.subTipo1IdInstitucion = subTipo1IdInstitucion;
	}
	public void setIdinstitucionSubt2(Short subTipo2IdInstitucion) {
		this.subTipo2IdInstitucion = subTipo2IdInstitucion;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
}
