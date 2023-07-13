package org.itcgae.siga.DTOs.scs;

public class GuardiasTurnoItem {

	private String 	idInstitucion;
	private String 	idTurno;
	private String 	idGuardia;
	private String		nombre;
	private Integer		numeroLetradosGuardia;
	private Integer		numeroSustitutosGuardia;
	private Integer 	diasGuardia;
	private Integer 	diasPagados;
	private String		validarJustificaciones;
	private Integer 	diasSeparacionGuardia;
	private Integer		idOrdenacionColas;
	private Integer 	numeroAsistencias;
	private String		descripcion;	
	private String		descripcionFacturacion;	
	private String		descripcionPago;
	private Integer		idPartidaPresupuestaria;
	private Integer 	numeroActuaciones;
	private String		designaDirecta;
	private Long 		idPersona_Ultimo;
	private Long 		idGrupoGuardiaColegiado_Ultimo;
	private String		fechaSuscripcion_Ultimo;
	private String 		tipodiasGuardia;
	private Integer		diasPeriodo;
	private String		tipoDiasPeriodo;
	private String		festivos;
	private String		seleccionLaborables;
	private String		seleccionFestivos;
	private String		porGrupos;
	private String		rotarComponentes;
	
	private Integer		idGuardiaSustitucion;
	private Integer		idTurnoSustitucion;
	private String		seleccionTiposDia;
	private String		descripcionTipoDiasGuardia;
	
	private String  	turno;
	private Integer 	idInstitucionPrincipal;
	private Integer 	idTurnoPrincipal;
	private Integer 	idGuardiaPrincipal;
	private Integer 	idTipoGuardiaSeleccionado;
	private String		envioCentralita;
	
	
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getNumeroLetradosGuardia() {
		return numeroLetradosGuardia;
	}
	public void setNumeroLetradosGuardia(Integer numeroLetradosGuardia) {
		this.numeroLetradosGuardia = numeroLetradosGuardia;
	}
	public Integer getNumeroSustitutosGuardia() {
		return numeroSustitutosGuardia;
	}
	public void setNumeroSustitutosGuardia(Integer numeroSustitutosGuardia) {
		this.numeroSustitutosGuardia = numeroSustitutosGuardia;
	}
	public Integer getDiasGuardia() {
		return diasGuardia;
	}
	public void setDiasGuardia(Integer diasGuardia) {
		this.diasGuardia = diasGuardia;
	}
	public Integer getDiasPagados() {
		return diasPagados;
	}
	public void setDiasPagados(Integer diasPagados) {
		this.diasPagados = diasPagados;
	}
	public String getValidarJustificaciones() {
		return validarJustificaciones;
	}
	public void setValidarJustificaciones(String validarJustificaciones) {
		this.validarJustificaciones = validarJustificaciones;
	}
	public Integer getDiasSeparacionGuardia() {
		return diasSeparacionGuardia;
	}
	public void setDiasSeparacionGuardia(Integer diasSeparacionGuardia) {
		this.diasSeparacionGuardia = diasSeparacionGuardia;
	}
	public Integer getIdOrdenacionColas() {
		return idOrdenacionColas;
	}
	public void setIdOrdenacionColas(Integer idOrdenacionColas) {
		this.idOrdenacionColas = idOrdenacionColas;
	}
	public Integer getNumeroAsistencias() {
		return numeroAsistencias;
	}
	public void setNumeroAsistencias(Integer numeroAsistencias) {
		this.numeroAsistencias = numeroAsistencias;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionFacturacion() {
		return descripcionFacturacion;
	}
	public void setDescripcionFacturacion(String descripcionFacturacion) {
		this.descripcionFacturacion = descripcionFacturacion;
	}
	public String getDescripcionPago() {
		return descripcionPago;
	}
	public void setDescripcionPago(String descripcionPago) {
		this.descripcionPago = descripcionPago;
	}
	public Integer getIdPartidaPresupuestaria() {
		return idPartidaPresupuestaria;
	}
	public void setIdPartidaPresupuestaria(Integer idPartidaPresupuestaria) {
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
	}
	public Integer getNumeroActuaciones() {
		return numeroActuaciones;
	}
	public void setNumeroActuaciones(Integer numeroActuaciones) {
		this.numeroActuaciones = numeroActuaciones;
	}
	public String getDesignaDirecta() {
		return designaDirecta;
	}
	public void setDesignaDirecta(String designaDirecta) {
		this.designaDirecta = designaDirecta;
	}
	public Long getIdPersona_Ultimo() {
		return idPersona_Ultimo;
	}
	public void setIdPersona_Ultimo(Long idPersona_Ultimo) {
		this.idPersona_Ultimo = idPersona_Ultimo;
	}
	public Long getIdGrupoGuardiaColegiado_Ultimo() {
		return idGrupoGuardiaColegiado_Ultimo;
	}
	public void setIdGrupoGuardiaColegiado_Ultimo(Long idGrupoGuardiaColegiado_Ultimo) {
		this.idGrupoGuardiaColegiado_Ultimo = idGrupoGuardiaColegiado_Ultimo;
	}
	public String getFechaSuscripcion_Ultimo() {
		return fechaSuscripcion_Ultimo;
	}
	public void setFechaSuscripcion_Ultimo(String fechaSuscripcion_Ultimo) {
		this.fechaSuscripcion_Ultimo = fechaSuscripcion_Ultimo;
	}
	public String getTipodiasGuardia() {
		return tipodiasGuardia;
	}
	public void setTipodiasGuardia(String tipodiasGuardia) {
		this.tipodiasGuardia = tipodiasGuardia;
	}
	public Integer getDiasPeriodo() {
		return diasPeriodo;
	}
	public void setDiasPeriodo(Integer diasPeriodo) {
		this.diasPeriodo = diasPeriodo;
	}
	public String getTipoDiasPeriodo() {
		return tipoDiasPeriodo;
	}
	public void setTipoDiasPeriodo(String tipoDiasPeriodo) {
		this.tipoDiasPeriodo = tipoDiasPeriodo;
	}
	public String getFestivos() {
		return festivos;
	}
	public void setFestivos(String festivos) {
		this.festivos = festivos;
	}
	public String getSeleccionLaborables() {
		return seleccionLaborables;
	}
	public void setSeleccionLaborables(String seleccionLaborables) {
		this.seleccionLaborables = seleccionLaborables;
	}
	public String getSeleccionFestivos() {
		return seleccionFestivos;
	}
	public void setSeleccionFestivos(String seleccionFestivos) {
		this.seleccionFestivos = seleccionFestivos;
	}
	public String getPorGrupos() {
		return porGrupos;
	}
	public void setPorGrupos(String porGrupos) {
		this.porGrupos = porGrupos;
	}
	public String getRotarComponentes() {
		return rotarComponentes;
	}
	public void setRotarComponentes(String rotarComponentes) {
		this.rotarComponentes = rotarComponentes;
	}
	public Integer getIdGuardiaSustitucion() {
		return idGuardiaSustitucion;
	}
	public void setIdGuardiaSustitucion(Integer idGuardiaSustitucion) {
		this.idGuardiaSustitucion = idGuardiaSustitucion;
	}
	public Integer getIdTurnoSustitucion() {
		return idTurnoSustitucion;
	}
	public void setIdTurnoSustitucion(Integer idTurnoSustitucion) {
		this.idTurnoSustitucion = idTurnoSustitucion;
	}
	public String getSeleccionTiposDia() {
		return seleccionTiposDia;
	}
	public void setSeleccionTiposDia(String seleccionTiposDia) {
		this.seleccionTiposDia = seleccionTiposDia;
	}
	public String getDescripcionTipoDiasGuardia() {
		return descripcionTipoDiasGuardia;
	}
	public void setDescripcionTipoDiasGuardia(String descripcionTipoDiasGuardia) {
		this.descripcionTipoDiasGuardia = descripcionTipoDiasGuardia;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public Integer getIdInstitucionPrincipal() {
		return idInstitucionPrincipal;
	}
	public void setIdInstitucionPrincipal(Integer idInstitucionPrincipal) {
		this.idInstitucionPrincipal = idInstitucionPrincipal;
	}
	public Integer getIdTurnoPrincipal() {
		return idTurnoPrincipal;
	}
	public void setIdTurnoPrincipal(Integer idTurnoPrincipal) {
		this.idTurnoPrincipal = idTurnoPrincipal;
	}
	public Integer getIdGuardiaPrincipal() {
		return idGuardiaPrincipal;
	}
	public void setIdGuardiaPrincipal(Integer idGuardiaPrincipal) {
		this.idGuardiaPrincipal = idGuardiaPrincipal;
	}
	public Integer getIdTipoGuardiaSeleccionado() {
		return idTipoGuardiaSeleccionado;
	}
	public void setIdTipoGuardiaSeleccionado(Integer idTipoGuardiaSeleccionado) {
		this.idTipoGuardiaSeleccionado = idTipoGuardiaSeleccionado;
	}
	public String getEnvioCentralita() {
		return envioCentralita;
	}
	public void setEnvioCentralita(String envioCentralita) {
		this.envioCentralita = envioCentralita;
	}
	
	
}
