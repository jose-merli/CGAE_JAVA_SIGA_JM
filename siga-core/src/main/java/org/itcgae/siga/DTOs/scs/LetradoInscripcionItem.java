package org.itcgae.siga.DTOs.scs;

import java.util.List;

import org.itcgae.siga.db.entities.CenPersona;


public class LetradoInscripcionItem {

	// Atributos
		private Integer idInstitucion;
		private Integer idTurno;
		private Integer idGuardia;
		private Long idPersona;
		private List<String> periodoGuardias;
		
		private String saltoCompensacion; //Valores = S(Salto)/ N(No)/ C(Compensacion)
		private String idSaltoCompensacion;
		private BajasTemporalesItem bajaTemporal;
		private String idSaltoCompensacionGrupo;
		
		private CenPersonaItem persona;
		private InscripcionGuardiaItem inscripcionGuardia;
		private InscripcionTurnoItem inscripcionTurno;
		private Integer posicion;
		private Long idGrupoGuardiaColegiado;
		private Integer grupo;
		private Integer ordenGrupo;
		private String numeroGrupo;
		/**
		 * @return the idInstitucion
		 */
		public Integer getIdInstitucion() {
			return idInstitucion;
		}
		/**
		 * @param idInstitucion the idInstitucion to set
		 */
		public void setIdInstitucion(Integer idInstitucion) {
			this.idInstitucion = idInstitucion;
		}
		/**
		 * @return the idTurno
		 */
		public Integer getIdTurno() {
			return idTurno;
		}
		/**
		 * @param idTurno the idTurno to set
		 */
		public void setIdTurno(Integer idTurno) {
			this.idTurno = idTurno;
		}
		/**
		 * @return the idGuardia
		 */
		public Integer getIdGuardia() {
			return idGuardia;
		}
		/**
		 * @param idGuardia the idGuardia to set
		 */
		public void setIdGuardia(Integer idGuardia) {
			this.idGuardia = idGuardia;
		}
		/**
		 * @return the idPersona
		 */
		public Long getIdPersona() {
			return idPersona;
		}
		/**
		 * @param idPersona the idPersona to set
		 */
		public void setIdPersona(Long idPersona) {
			this.idPersona = idPersona;
		}
		/**
		 * @return the periodoGuardias
		 */
		public List<String> getPeriodoGuardias() {
			return periodoGuardias;
		}
		/**
		 * @param periodoGuardias the periodoGuardias to set
		 */
		public void setPeriodoGuardias(List<String> periodoGuardias) {
			this.periodoGuardias = periodoGuardias;
		}
		/**
		 * @return the saltoCompensacion
		 */
		public String getSaltoCompensacion() {
			return saltoCompensacion;
		}
		/**
		 * @param saltoCompensacion the saltoCompensacion to set
		 */
		public void setSaltoCompensacion(String saltoCompensacion) {
			this.saltoCompensacion = saltoCompensacion;
		}
		/**
		 * @return the idSaltoCompensacion
		 */
		public String getIdSaltoCompensacion() {
			return idSaltoCompensacion;
		}
		/**
		 * @param idSaltoCompensacion the idSaltoCompensacion to set
		 */
		public void setIdSaltoCompensacion(String idSaltoCompensacion) {
			this.idSaltoCompensacion = idSaltoCompensacion;
		}
		/**
		 * @return the bajaTemporal
		 */
		public BajasTemporalesItem getBajaTemporal() {
			return bajaTemporal;
		}
		/**
		 * @param bajaTemporal the bajaTemporal to set
		 */
		public void setBajaTemporal(BajasTemporalesItem bajaTemporal) {
			this.bajaTemporal = bajaTemporal;
		}
		/**
		 * @return the idSaltoCompensacionGrupo
		 */
		public String getIdSaltoCompensacionGrupo() {
			return idSaltoCompensacionGrupo;
		}
		/**
		 * @param idSaltoCompensacionGrupo the idSaltoCompensacionGrupo to set
		 */
		public void setIdSaltoCompensacionGrupo(String idSaltoCompensacionGrupo) {
			this.idSaltoCompensacionGrupo = idSaltoCompensacionGrupo;
		}
		/**
		 * @return the persona
		 */
		public CenPersonaItem getPersona() {
			return persona;
		}
		/**
		 * @param persona the persona to set
		 */
		public void setPersona(CenPersonaItem persona) {
			this.persona = persona;
		}
		/**
		 * @return the inscripcionGuardia
		 */
		public InscripcionGuardiaItem getInscripcionGuardia() {
			return inscripcionGuardia;
		}
		/**
		 * @param inscripcionGuardia the inscripcionGuardia to set
		 */
		public void setInscripcionGuardia(InscripcionGuardiaItem inscripcionGuardia) {
			this.inscripcionGuardia = inscripcionGuardia;
		}
		/**
		 * @return the inscripcionTurno
		 */
		public InscripcionTurnoItem getInscripcionTurno() {
			return inscripcionTurno;
		}
		/**
		 * @param inscripcionTurno the inscripcionTurno to set
		 */
		public void setInscripcionTurno(InscripcionTurnoItem inscripcionTurno) {
			this.inscripcionTurno = inscripcionTurno;
		}
		/**
		 * @return the posicion
		 */
		public Integer getPosicion() {
			return posicion;
		}
		/**
		 * @param posicion the posicion to set
		 */
		public void setPosicion(Integer posicion) {
			this.posicion = posicion;
		}
		/**
		 * @return the idGrupoGuardiaColegiado
		 */
		public Long getIdGrupoGuardiaColegiado() {
			return idGrupoGuardiaColegiado;
		}
		/**
		 * @param idGrupoGuardiaColegiado the idGrupoGuardiaColegiado to set
		 */
		public void setIdGrupoGuardiaColegiado(Long idGrupoGuardiaColegiado) {
			this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
		}
		/**
		 * @return the grupo
		 */
		public Integer getGrupo() {
			return grupo;
		}
		/**
		 * @param grupo the grupo to set
		 */
		public void setGrupo(Integer grupo) {
			this.grupo = grupo;
		}
		/**
		 * @return the ordenGrupo
		 */
		public Integer getOrdenGrupo() {
			return ordenGrupo;
		}
		/**
		 * @param ordenGrupo the ordenGrupo to set
		 */
		public void setOrdenGrupo(Integer ordenGrupo) {
			this.ordenGrupo = ordenGrupo;
		}
		/**
		 * @return the numeroGrupo
		 */
		public String getNumeroGrupo() {
			return numeroGrupo;
		}
		/**
		 * @param numeroGrupo the numeroGrupo to set
		 */
		public void setNumeroGrupo(String numeroGrupo) {
			this.numeroGrupo = numeroGrupo;
		}
		
		
		
}
