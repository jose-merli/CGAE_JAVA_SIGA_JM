package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcuradorItem {
	
		private String idProcurador;
		private String nombre;
		private String apellido1;
		private String apellido2;
		private String nombreApe;
		private String nColegiado;
		
		private String idColProcurador;
		private String nombreColProcurador;
		
		private String domicilio;
		private String idInstitucion;
		private String codigoPostal;
		private String idPoblacion;
		private String idProvincia;
		private String nombrePoblacion;
		private String nombreProvincia;
		
		private boolean historico;
		private Date fechabaja;
		
		private String fax1;
		private String telefono1;
		private String telefono2;
		private String codigoExt;
		private String email;
		
		private String numerodesignacion;
		private Date fechaDesigna;
		private String observaciones;
		private String motivosRenuncia;
		private Date fecharenunciasolicita;
		private String numeroTotalProcuradores;
		
		private Short anio;
		private String idTurno;
		private String numero;
		
		private Long usuModificacion;
		private Date fechaModificacion;
		
		public String getIdTurno() {
			return idTurno;
		}

		public void setIdTurno(String idTurno) {
			this.idTurno = idTurno;
		}

		public String getNombreApe() {
			return nombreApe;
		}

		public void setNombreApe(String nombreApe) {
			this.nombreApe = nombreApe;
		}

		public String getFax1() {
			return fax1;
		}

		public void setFax1(String fax1) {
			this.fax1 = fax1;
		}

		@JsonProperty("idProcurador")
		public String getIdProcurador() {
			return idProcurador;
		}

		public void setIdProcurador(String idProcurador) {
			this.idProcurador = idProcurador;
		}

		@JsonProperty("apellido1")
		public String getApellido1() {
			return apellido1;
		}

		public void setApellido1(String apellido1) {
			this.apellido1 = apellido1;
		}
		
		@JsonProperty("apellido2")
		public String getApellido2() {
			return apellido2;
		}

		public void setApellido2(String apellido2) {
			this.apellido2 = apellido2;
		}

		@JsonProperty("nColegiado")
		public String getnColegiado() {
			return nColegiado;
		}

		public void setnColegiado(String nColegiado) {
			this.nColegiado = nColegiado;
		}

		@JsonProperty("idColProcurador")
		public String getIdColProcurador() {
			return idColProcurador;
		}

		public void setIdColProcurador(String idColProcurador) {
			this.idColProcurador = idColProcurador;
		}



		
		
		@JsonProperty("fechabaja")
		public Date getFechaBaja() {
			return fechabaja;
		}

		public void setFechaBaja(Date fechaBaja) {
			this.fechabaja = fechaBaja;
		}

		public ProcuradorItem nombre(String nombre) {
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
		public ProcuradorItem domicilio(String domicilio) {
			this.domicilio = domicilio;
			return this;
		}

		@JsonProperty("domicilio")
		public String getDomicilio() {
			return domicilio;
		}

		public void setDomicilio(String domicilio) {
			this.domicilio = domicilio;
		}
		
		/**
		 * 
		 **/
		public ProcuradorItem idInstitucion(String idInstitucion) {
			this.idInstitucion = idInstitucion;
			return this;
		}

		@JsonProperty("idInstitucion")
		public String getIdInstitucion() {
			return idInstitucion;
		}

		public void setIdInstitucion(String idInstitucion) {
			this.idInstitucion = idInstitucion;
		}
		
		

		/**
		 * 
		 **/
		public ProcuradorItem usuModificacion(Long usuModificacion) {
			this.usuModificacion = usuModificacion;
			return this;
		}

		@JsonProperty("usuModificacion")
		public Long getUsuModificacion() {
			return usuModificacion;
		}

		public void setUsuModificacion(Long usuModificacion) {
			this.usuModificacion = usuModificacion;
		}

		/**
		 * 
		 **/
		public ProcuradorItem fechaModificacion(Date fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
			return this;
		}

		@JsonProperty("fechaModificacion")
		public Date getFechaModificacion() {
			return fechaModificacion;
		}

		public void setFechaModificacion(Date fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
		}

		/**
		 * 
		 **/
		public ProcuradorItem descripcionsubzona(String codigoPostal) {
			this.codigoPostal = codigoPostal;
			return this;
		}

		@JsonProperty("codigoPostal")
		public String getCodigoPostal() {
			return codigoPostal;
		}

		public void setCodigoPostal(String codigoPostal) {
			this.codigoPostal = codigoPostal;
		}

		/**
		 * 
		 **/
		public ProcuradorItem idPoblacion(String idPoblacion) {
			this.idPoblacion = idPoblacion;
			return this;
		}

		@JsonProperty("idPoblacion")
		public String getIdPoblacion() {
			return idPoblacion;
		}

		public void setIdPoblacion(String idPoblacion) {
			this.idPoblacion = idPoblacion;
		}
		
		/**
		 * 
		 **/
		public ProcuradorItem idProvincia(String idProvincia) {
			this.idProvincia = idProvincia;
			return this;
		}

		@JsonProperty("idProvincia")
		public String getIdProvincia() {
			return idProvincia;
		}

		public void setIdProvincia(String idProvincia) {
			this.idProvincia = idProvincia;
		}
			
		/**
		 * 
		 **/
		public ProcuradorItem historico(boolean historico) {
			this.historico = historico;
			return this;
		}

		@JsonProperty("historico")
		public boolean getHistorico() {
			return historico;
		}

		public void setHistorico(boolean historico) {
			this.historico = historico;
		}
		
		
		/**
		 **/
		public ProcuradorItem telefono1(String telefono1) {
			this.telefono1 = telefono1;
			return this;
		}

		@JsonProperty("telefono1")
		public String getTelefono1() {
			return telefono1;
		}

		public void setTelefono1(String telefono1) {
			this.telefono1 = telefono1;
		}
		
		/**
		 **/
		public ProcuradorItem telefono2(String telefono2) {
			this.telefono2 = telefono2;
			return this;
		}

		@JsonProperty("telefono2")
		public String getTelefono2() {
			return telefono2;
		}

		public void setTelefono2(String telefono2) {
			this.telefono2 = telefono2;
		}
		
		/**
		 **/
		/**
		 **/
		public ProcuradorItem codigoExt(String codigoExt) {
			this.codigoExt = codigoExt;
			return this;
		}
		
		@JsonProperty("codigoExt")
		public String getCodigoExt() {
			return codigoExt;
		}

		public void setCodigoExt(String codigoExt) {
			this.codigoExt = codigoExt;
		}

		/**
		 **/
		

		/**
		 **/
		public ProcuradorItem email(String email) {
			this.email = email;
			return this;
		}
		
		@JsonProperty("email")
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * 
		 **/
		public ProcuradorItem nombrePoblacion(String nombrePoblacion) {
			this.nombrePoblacion = nombrePoblacion;
			return this;
		}

		@JsonProperty("nombrePoblacion")	
		public String getNombrePoblacion() {
			return nombrePoblacion;
		}

		public void setNombrePoblacion(String nombrePoblacion) {
			this.nombrePoblacion = nombrePoblacion;
		}

		/**
		 * 
		 **/
		public ProcuradorItem nombreProvincia(String nombreProvincia) {
			this.nombreProvincia = nombreProvincia;
			return this;
		}

		@JsonProperty("nombreProvincia")
		public String getNombreProvincia() {
			return nombreProvincia;
		}

		public void setNombreProvincia(String nombreProvincia) {
			this.nombreProvincia = nombreProvincia;
		}

		/**
		 * @return the fechabaja
		 */
//		public Date getFechabaja() {
//			return fechabaja;
//		}

		/**
		 * @param fechabaja the fechabaja to set
		 */
//		public void setFechabaja(Date fechabaja) {
//			this.fechabaja = fechabaja;
//		}

		/**
		 * @return the numero
		 */
		public String getNumero() {
			return numero;
		}

		/**
		 * @param numero the numero to set
		 */
		public void setNumero(String numero) {
			this.numero = numero;
		}

		/**
		 * @return the numerodesignacion
		 */
		public String getNumerodesignacion() {
			return numerodesignacion;
		}

		/**
		 * @param numerodesignacion the numerodesignacion to set
		 */
		public void setNumerodesignacion(String numerodesignacion) {
			this.numerodesignacion = numerodesignacion;
		}

		/**
		 * @return the fechaDesigna
		 */
		public Date getFechaDesigna() {
			return fechaDesigna;
		}

		/**
		 * @param fechaDesigna the fechaDesigna to set
		 */
		public void setFechaDesigna(Date fechaDesigna) {
			this.fechaDesigna = fechaDesigna;
		}

		/**
		 * @return the observaciones
		 */
		public String getObservaciones() {
			return observaciones;
		}

		/**
		 * @param observaciones the observaciones to set
		 */
		public void setObservaciones(String observaciones) {
			this.observaciones = observaciones;
		}

		/**
		 * @return the motivosRenuncia
		 */
		public String getMotivosRenuncia() {
			return motivosRenuncia;
		}

		/**
		 * @param motivosRenuncia the motivosRenuncia to set
		 */
		public void setMotivosRenuncia(String motivosRenuncia) {
			this.motivosRenuncia = motivosRenuncia;
		}

		/**
		 * @return the fecharenunciasolicita
		 */
		public Date getFecharenunciasolicita() {
			return fecharenunciasolicita;
		}

		/**
		 * @param fecharenunciasolicita the fecharenunciasolicita to set
		 */
		public void setFecharenunciasolicita(Date fecharenunciasolicita) {
			this.fecharenunciasolicita = fecharenunciasolicita;
		}

		/**
		 * @return the numeroTotalProcuradores
		 */
		public String getNumeroTotalProcuradores() {
			return numeroTotalProcuradores;
		}

		/**
		 * @param numeroTotalProcuradores the numeroTotalProcuradores to set
		 */
		public void setNumeroTotalProcuradores(String numeroTotalProcuradores) {
			this.numeroTotalProcuradores = numeroTotalProcuradores;
		}

		@Override
		public boolean equals(java.lang.Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			ProcuradorItem prisionItem = (ProcuradorItem) o;
			return Objects.equals(this.idProcurador, prisionItem.idProcurador)
					&& Objects.equals(this.nombre, prisionItem.nombre)
					&& Objects.equals(this.domicilio, prisionItem.domicilio)
					&& Objects.equals(this.usuModificacion, prisionItem.usuModificacion)
					&& Objects.equals(this.fechaModificacion, prisionItem.fechaModificacion)
					&& Objects.equals(this.codigoPostal, prisionItem.codigoPostal)
					&& Objects.equals(this.idProvincia, prisionItem.idProvincia)
					&& Objects.equals(this.idInstitucion, prisionItem.idInstitucion)
					&& Objects.equals(this.historico, prisionItem.historico)
					&& Objects.equals(this.fechabaja, prisionItem.fechabaja)
					&& Objects.equals(this.telefono1, prisionItem.telefono1)
					&& Objects.equals(this.telefono2, prisionItem.telefono2)
					&& Objects.equals(this.codigoExt, prisionItem.codigoExt)
					&& Objects.equals(this.email, prisionItem.email)
					&& Objects.equals(this.nombrePoblacion, prisionItem.nombrePoblacion)
					&& Objects.equals(this.nombreProvincia, prisionItem.nombreProvincia)
					&& Objects.equals(this.numero, prisionItem.numero);

		}

		
		
		@Override
		public int hashCode() {
			return Objects.hash(idProcurador, nombre, domicilio, usuModificacion, fechaModificacion, codigoPostal,
					idPoblacion, idProvincia, idInstitucion, historico, fechabaja, telefono1, telefono2,
					codigoExt, email, nombrePoblacion, nombreProvincia, numero);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("class PrisionItem {\n");

			sb.append("    idProcurador: ").append(toIndentedString(idProcurador)).append("\n");
			sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
			sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
			sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
			sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
			sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");
			sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
			sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
			sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
			sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
			sb.append("    fechabaja: ").append(toIndentedString(fechabaja)).append("\n");
			sb.append("    telefono1: ").append(toIndentedString(telefono1)).append("\n");
			sb.append("    telefono2: ").append(toIndentedString(telefono2)).append("\n");
			sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
			sb.append("    email: ").append(toIndentedString(email)).append("\n");
			sb.append("    nombrePoblacion: ").append(toIndentedString(nombrePoblacion)).append("\n");
			sb.append("    nombreProvincia: ").append(toIndentedString(nombreProvincia)).append("\n");
			sb.append("    numero: ").append(toIndentedString(numero)).append("\n");

			sb.append("}");
			return sb.toString();
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

		public String getNombreColProcurador() {
			return nombreColProcurador;
		}

		public void setNombreColProcurador(String nombreColProcurador) {
			this.nombreColProcurador = nombreColProcurador;
		}

		public Short getAnio() {
			return anio;
		}

		public void setAnio(Short anio) {
			this.anio = anio;
		}
	}
