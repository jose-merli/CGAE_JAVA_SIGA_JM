package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ServicioDetalleDTO {
	private int idtiposervicios; //Categoria servicio
	private int idservicio; //Tipo Servicio
	private int idserviciosinstitucion; //Servicio
	private String descripcion;
	private String iniciofinalponderado; //Aplicación de precio por cambio de situación del interesado Radio Buttons
	private String facturacionponderada; //Facturación proporcional por días de suscripción Checkbox
	private String momentocargo;
	private String permitirbaja;
	private String permitiralta;
	private String automatico; //Tipo suscripcion
	private String cuentacontable;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	private int idconsulta;
	private String criterios;
	private int idtipoiva;
	private String codigo_traspasonav;
	
	private String categoria;
	private int valoriva;
	private String tipo;
	
	private List<Integer> formasdepagointernet = new ArrayList<>();
	private List<Integer> formasdepagosecretaria = new ArrayList<>();
	private List<Integer> formasdepagointernetoriginales = new ArrayList<>();
	private List<Integer> formasdepagosecretariaoriginales = new ArrayList<>();
	private boolean editar;
	private ServicioDetalleDTO serviciooriginal;
	
	private String codigoext;
	private String nofacturable;
	
	private Error error = null;

	public int getIdtiposervicios() {
		return idtiposervicios;
	}

	public void setIdtiposervicios(int idtiposervicios) {
		this.idtiposervicios = idtiposervicios;
	}

	public int getIdservicio() {
		return idservicio;
	}

	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}

	public int getIdserviciosinstitucion() {
		return idserviciosinstitucion;
	}

	public void setIdserviciosinstitucion(int idserviciosinstitucion) {
		this.idserviciosinstitucion = idserviciosinstitucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIniciofinalponderado() {
		return iniciofinalponderado;
	}

	public void setIniciofinalponderado(String iniciofinalponderado) {
		this.iniciofinalponderado = iniciofinalponderado;
	}

	public String getMomentocargo() {
		return momentocargo;
	}

	public void setMomentocargo(String momentocargo) {
		this.momentocargo = momentocargo;
	}

	public String getPermitirbaja() {
		return permitirbaja;
	}

	public void setPermitirbaja(String permitirbaja) {
		this.permitirbaja = permitirbaja;
	}

	public String getPermitiralta() {
		return permitiralta;
	}

	public void setPermitiralta(String permitiralta) {
		this.permitiralta = permitiralta;
	}

	public String getAutomatico() {
		return automatico;
	}

	public void setAutomatico(String automatico) {
		this.automatico = automatico;
	}

	public String getCuentacontable() {
		return cuentacontable;
	}

	public void setCuentacontable(String cuentacontable) {
		this.cuentacontable = cuentacontable;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	public int getIdconsulta() {
		return idconsulta;
	}

	public void setIdconsulta(int idconsulta) {
		this.idconsulta = idconsulta;
	}

	public String getCriterios() {
		return criterios;
	}

	public void setCriterios(String criterios) {
		this.criterios = criterios;
	}

	public String getFacturacionponderada() {
		return facturacionponderada;
	}

	public void setFacturacionponderada(String facturacionponderada) {
		this.facturacionponderada = facturacionponderada;
	}

	public int getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(int idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public String getCodigo_traspasonav() {
		return codigo_traspasonav;
	}

	public void setCodigo_traspasonav(String codigo_traspasonav) {
		this.codigo_traspasonav = codigo_traspasonav;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getValoriva() {
		return valoriva;
	}

	public void setValoriva(int valoriva) {
		this.valoriva = valoriva;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Integer> getFormasdepagointernet() {
		return formasdepagointernet;
	}

	public void setFormasdepagointernet(List<Integer> formasdepagointernet) {
		this.formasdepagointernet = formasdepagointernet;
	}

	public List<Integer> getFormasdepagosecretaria() {
		return formasdepagosecretaria;
	}

	public void setFormasdepagosecretaria(List<Integer> formasdepagosecretaria) {
		this.formasdepagosecretaria = formasdepagosecretaria;
	}

	public List<Integer> getFormasdepagointernetoriginales() {
		return formasdepagointernetoriginales;
	}

	public void setFormasdepagointernetoriginales(List<Integer> formasdepagointernetoriginales) {
		this.formasdepagointernetoriginales = formasdepagointernetoriginales;
	}

	public List<Integer> getFormasdepagosecretariaoriginales() {
		return formasdepagosecretariaoriginales;
	}

	public void setFormasdepagosecretariaoriginales(List<Integer> formasdepagosecretariaoriginales) {
		this.formasdepagosecretariaoriginales = formasdepagosecretariaoriginales;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}


	public ServicioDetalleDTO getServiciooriginal() {
		return serviciooriginal;
	}

	public void setServiciooriginal(ServicioDetalleDTO serviciooriginal) {
		this.serviciooriginal = serviciooriginal;
	}

	public String getCodigoext() {
		return codigoext;
	}

	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}

	public String getNofacturable() {
		return nofacturable;
	}

	public void setNofacturable(String nofacturable) {
		this.nofacturable = nofacturable;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((automatico == null) ? 0 : automatico.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((codigo_traspasonav == null) ? 0 : codigo_traspasonav.hashCode());
		result = prime * result + ((codigoext == null) ? 0 : codigoext.hashCode());
		result = prime * result + ((criterios == null) ? 0 : criterios.hashCode());
		result = prime * result + ((cuentacontable == null) ? 0 : cuentacontable.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (editar ? 1231 : 1237);
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((facturacionponderada == null) ? 0 : facturacionponderada.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((formasdepagointernet == null) ? 0 : formasdepagointernet.hashCode());
		result = prime * result
				+ ((formasdepagointernetoriginales == null) ? 0 : formasdepagointernetoriginales.hashCode());
		result = prime * result + ((formasdepagosecretaria == null) ? 0 : formasdepagosecretaria.hashCode());
		result = prime * result
				+ ((formasdepagosecretariaoriginales == null) ? 0 : formasdepagosecretariaoriginales.hashCode());
		result = prime * result + idconsulta;
		result = prime * result + idservicio;
		result = prime * result + idserviciosinstitucion;
		result = prime * result + idtipoiva;
		result = prime * result + idtiposervicios;
		result = prime * result + ((iniciofinalponderado == null) ? 0 : iniciofinalponderado.hashCode());
		result = prime * result + ((momentocargo == null) ? 0 : momentocargo.hashCode());
		result = prime * result + ((nofacturable == null) ? 0 : nofacturable.hashCode());
		result = prime * result + ((permitiralta == null) ? 0 : permitiralta.hashCode());
		result = prime * result + ((permitirbaja == null) ? 0 : permitirbaja.hashCode());
		result = prime * result + ((serviciooriginal == null) ? 0 : serviciooriginal.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + valoriva;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicioDetalleDTO other = (ServicioDetalleDTO) obj;
		if (automatico == null) {
			if (other.automatico != null)
				return false;
		} else if (!automatico.equals(other.automatico))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (codigo_traspasonav == null) {
			if (other.codigo_traspasonav != null)
				return false;
		} else if (!codigo_traspasonav.equals(other.codigo_traspasonav))
			return false;
		if (codigoext == null) {
			if (other.codigoext != null)
				return false;
		} else if (!codigoext.equals(other.codigoext))
			return false;
		if (criterios == null) {
			if (other.criterios != null)
				return false;
		} else if (!criterios.equals(other.criterios))
			return false;
		if (cuentacontable == null) {
			if (other.cuentacontable != null)
				return false;
		} else if (!cuentacontable.equals(other.cuentacontable))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (editar != other.editar)
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (facturacionponderada == null) {
			if (other.facturacionponderada != null)
				return false;
		} else if (!facturacionponderada.equals(other.facturacionponderada))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (formasdepagointernet == null) {
			if (other.formasdepagointernet != null)
				return false;
		} else if (!formasdepagointernet.equals(other.formasdepagointernet))
			return false;
		if (formasdepagointernetoriginales == null) {
			if (other.formasdepagointernetoriginales != null)
				return false;
		} else if (!formasdepagointernetoriginales.equals(other.formasdepagointernetoriginales))
			return false;
		if (formasdepagosecretaria == null) {
			if (other.formasdepagosecretaria != null)
				return false;
		} else if (!formasdepagosecretaria.equals(other.formasdepagosecretaria))
			return false;
		if (formasdepagosecretariaoriginales == null) {
			if (other.formasdepagosecretariaoriginales != null)
				return false;
		} else if (!formasdepagosecretariaoriginales.equals(other.formasdepagosecretariaoriginales))
			return false;
		if (idconsulta != other.idconsulta)
			return false;
		if (idservicio != other.idservicio)
			return false;
		if (idserviciosinstitucion != other.idserviciosinstitucion)
			return false;
		if (idtipoiva != other.idtipoiva)
			return false;
		if (idtiposervicios != other.idtiposervicios)
			return false;
		if (iniciofinalponderado == null) {
			if (other.iniciofinalponderado != null)
				return false;
		} else if (!iniciofinalponderado.equals(other.iniciofinalponderado))
			return false;
		if (momentocargo == null) {
			if (other.momentocargo != null)
				return false;
		} else if (!momentocargo.equals(other.momentocargo))
			return false;
		if (nofacturable == null) {
			if (other.nofacturable != null)
				return false;
		} else if (!nofacturable.equals(other.nofacturable))
			return false;
		if (permitiralta == null) {
			if (other.permitiralta != null)
				return false;
		} else if (!permitiralta.equals(other.permitiralta))
			return false;
		if (permitirbaja == null) {
			if (other.permitirbaja != null)
				return false;
		} else if (!permitirbaja.equals(other.permitirbaja))
			return false;
		if (serviciooriginal == null) {
			if (other.serviciooriginal != null)
				return false;
		} else if (!serviciooriginal.equals(other.serviciooriginal))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valoriva != other.valoriva)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServicioDetalleDTO [idtiposervicios=" + idtiposervicios + ", idservicio=" + idservicio
				+ ", idserviciosinstitucion=" + idserviciosinstitucion + ", descripcion=" + descripcion
				+ ", iniciofinalponderado=" + iniciofinalponderado + ", momentocargo=" + momentocargo
				+ ", permitirbaja=" + permitirbaja + ", permitiralta=" + permitiralta + ", automatico=" + automatico
				+ ", cuentacontable=" + cuentacontable + ", fechabaja=" + fechabaja + ", idconsulta=" + idconsulta
				+ ", criterios=" + criterios + ", facturacionponderada=" + facturacionponderada + ", idtipoiva="
				+ idtipoiva + ", codigo_traspasonav=" + codigo_traspasonav + ", categoria=" + categoria + ", valoriva="
				+ valoriva + ", tipo=" + tipo + ", formasdepagointernet=" + formasdepagointernet
				+ ", formasdepagosecretaria=" + formasdepagosecretaria + ", formasdepagointernetoriginales="
				+ formasdepagointernetoriginales + ", formasdepagosecretariaoriginales="
				+ formasdepagosecretariaoriginales + ", editar=" + editar + ", serviciooriginal=" + serviciooriginal
				+ ", codigoext=" + codigoext + ", nofacturable=" + nofacturable + ", error=" + error + "]";
	}

}