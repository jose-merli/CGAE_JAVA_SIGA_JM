package org.itcgae.siga.db.services.fac.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.TiposServiciosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysServiciosSqlProvider;

public class PySTiposServiciosSqlExtendsProvider extends PysServiciosSqlProvider{

	public String searchTiposServicios(String idioma, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("ss.IDTIPOSERVICIOS");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("ss.IDSERVICIO");
		sql.SELECT("ss.DESCRIPCION");
		sql.SELECT("ss.FECHABAJA");
		
		sql.FROM("PYS_SERVICIOS ss");
		
		sql.JOIN("PYS_TIPOSERVICIOS tp ON ss.IDTIPOSERVICIOS = tp.IDTIPOSERVICIOS");
		
		sql.WHERE("ss.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("ss.FECHABAJA IS NULL");
		
		sql.ORDER_BY("ss.IDTIPOSERVICIOS");
		sql.ORDER_BY("ss.IDSERVICIO");
		sql.ORDER_BY("ss.IDINSTITUCION");

		return sql.toString();
	}

	public String searchTiposServiciosHistorico(String idioma, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("ss.IDTIPOSERVICIOS");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("ss.IDSERVICIO");
		sql.SELECT("ss.DESCRIPCION");
		sql.SELECT("ss.FECHABAJA");
		
		sql.FROM("PYS_SERVICIOS ss");
		
		sql.JOIN("PYS_TIPOSERVICIOS tp ON ss.IDTIPOSERVICIOS = tp.IDTIPOSERVICIOS");
		
		sql.WHERE("ss.IDINSTITUCION = '" + idInstitucion + "'");
		
		sql.ORDER_BY("ss.IDTIPOSERVICIOS");
		sql.ORDER_BY("ss.IDSERVICIO");
		sql.ORDER_BY("ss.IDINSTITUCION");

		return sql.toString();
	}

	public String searchListadoServiciosBuscador(String idioma, Short idInstitucion, FiltroServicioItem filtroServicioItem) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.IDINSTITUCION");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.IDSERVICIO");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.IDSERVICIOSINSTITUCION");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.DESCRIPCION");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.FECHABAJA");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.AUTOMATICO");
		sql.SELECT_DISTINCT(" PYS_SERVICIOSINSTITUCION.IDTIPOIVA");
		sql.SELECT_DISTINCT(" F_SIGA_GETRECURSO (PYS_TIPOSERVICIOS.DESCRIPCION, 1) AS CATEGORIA");
		sql.SELECT_DISTINCT(" PYS_SERVICIOS.DESCRIPCION AS TIPO");
		sql.SELECT_DISTINCT(" PYS_TIPOIVA.DESCRIPCION AS IVA");
		sql.SELECT_DISTINCT(" concat(F_siga_formatonumero(ROUND(((PYS_PRECIOSSERVICIOS.VALOR / PYS_PERIODICIDAD.PERIODOSMES)*PYS_TIPOIVA.VALOR / 100)+ (PYS_PRECIOSSERVICIOS.VALOR / PYS_PERIODICIDAD.PERIODOSMES), 2), 2), ' €') AS PRECIO_IVA_MES");
		sql.SELECT_DISTINCT(" f_siga_getrecurso (PYS_FORMAPAGO.DESCRIPCION, 1) AS FORMA_PAGO");
		
		sql.FROM(" PYS_SERVICIOSINSTITUCION");
		
		sql.INNER_JOIN(" PYS_TIPOSERVICIOS ON PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = PYS_TIPOSERVICIOS.IDTIPOSERVICIOS");
		sql.INNER_JOIN(" PYS_SERVICIOS ON PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = PYS_SERVICIOS.IDTIPOSERVICIOS\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDSERVICIO = PYS_SERVICIOS.IDSERVICIO\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDINSTITUCION = PYS_SERVICIOS.IDINSTITUCION");
		sql.INNER_JOIN(" PYS_TIPOIVA ON\r\n"
				+ " PYS_SERVICIOSINSTITUCION.IDTIPOIVA = PYS_TIPOIVA.IDTIPOIVA");
		sql.JOIN(" PYS_FORMAPAGOSERVICIOS ON PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = PYS_FORMAPAGOSERVICIOS.IDTIPOSERVICIOS\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDSERVICIO = PYS_FORMAPAGOSERVICIOS.IDSERVICIO\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDINSTITUCION = PYS_FORMAPAGOSERVICIOS.IDINSTITUCION\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDSERVICIOSINSTITUCION = PYS_FORMAPAGOSERVICIOS.IDSERVICIOSINSTITUCION");
		sql.JOIN(" PYS_PRECIOSSERVICIOS ON PYS_SERVICIOSINSTITUCION.IDSERVICIO = PYS_PRECIOSSERVICIOS.IDSERVICIO\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = PYS_PRECIOSSERVICIOS.IDTIPOSERVICIOS\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDSERVICIOSINSTITUCION = PYS_PRECIOSSERVICIOS.IDSERVICIOSINSTITUCION\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDINSTITUCION = PYS_PRECIOSSERVICIOS.IDINSTITUCION");
		sql.JOIN(" PYS_PERIODICIDAD ON PYS_PRECIOSSERVICIOS.IDPERIODICIDAD = PYS_PERIODICIDAD.IDPERIODICIDAD");
		sql.JOIN(" PYS_FORMAPAGO ON PYS_FORMAPAGO.IDFORMAPAGO = PYS_FORMAPAGOSERVICIOS.IDFORMAPAGO ");
		
		sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDINSTITUCION = '" + idInstitucion +"'");
		
		if(filtroServicioItem.getTipoSuscripcion() != null && filtroServicioItem.getTipoSuscripcion() != "")
			sql.WHERE(" PYS_SERVICIOSINSTITUCION.AUTOMATICO = = '" + filtroServicioItem.getTipoSuscripcion() + "'");
		
		if(filtroServicioItem.getCategoria() != null && filtroServicioItem.getCategoria() != "")
			sql.WHERE("  PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = '" + filtroServicioItem.getCategoria() + "'");
		
		if(filtroServicioItem.getTipo() != null && filtroServicioItem.getTipo() != "")
			sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDSERVICIO = '" + filtroServicioItem.getTipo() + "'");
		
		if(filtroServicioItem.getFormaPago() != null && filtroServicioItem.getFormaPago() != "")
			sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDFORMAPAGO = '" + filtroServicioItem.getFormaPago() + "'");
		
		if(filtroServicioItem.getCodigo() != null && filtroServicioItem.getCodigo() != "")
			sql.WHERE(" PYS_SERVICIOSINSTITUCION.CODIGO_TRASPASONAV = " + filtroServicioItem.getCodigo() + "')");
		
		if(filtroServicioItem.getIva() != null && filtroServicioItem.getIva() != "")
			sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDTIPOIVA = '" + filtroServicioItem.getIva() + "'");
		
		if(filtroServicioItem.getServicio() != null && filtroServicioItem.getServicio() != "")
			sql.WHERE(" regexp_like(PYS_SERVICIOSINSTITUCION.DESCRIPCION,'" + filtroServicioItem.getServicio() + "')");
		
		if(filtroServicioItem.getPrecioDesde() != null && filtroServicioItem.getPrecioDesde() != "")
			sql.WHERE(" F_siga_formatonumero(ROUND(((PYS_PRECIOSSERVICIOS.VALOR / PYS_PERIODICIDAD.PERIODOSMES)*PYS_TIPOIVA.VALOR / 100)+ (PYS_PRECIOSSERVICIOS.VALOR / PYS_PERIODICIDAD.PERIODOSMES), 2), 2)  >= " + Float.parseFloat(filtroServicioItem.getPrecioDesde()) + "");
	
		if(filtroServicioItem.getPrecioHasta() != null && filtroServicioItem.getPrecioHasta() != "")
			sql.WHERE(" F_siga_formatonumero(ROUND(((PYS_PRECIOSSERVICIOS.VALOR / PYS_PERIODICIDAD.PERIODOSMES)*PYS_TIPOIVA.VALOR / 100)+ (PYS_PRECIOSSERVICIOS.VALOR / PYS_PERIODICIDAD.PERIODOSMES), 2), 2)  <= " + Float.parseFloat(filtroServicioItem.getPrecioHasta()) + "");


		sql.ORDER_BY(" PYS_SERVICIOSINSTITUCION.DESCRIPCION");
		
		return sql.toString();
	}
	
	public String comboTiposServicios(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOSERVICIOS AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_TIPOSERVICIOS");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}

	public String getIndiceMaxServicio(List<TiposServiciosItem> listadoServicios, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL((MAX(IDSERVICIO) + 1),1) AS IDSERVICIO");
		
		sql.FROM("PYS_SERVICIOS");
		
		sql.WHERE("IDTIPOSERVICIOS ='" + listadoServicios.get(0).getIdtiposervicios() + "'");;
		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		
		return sql.toString();
	}
	
	public String activarDesactivarServicio(AdmUsuarios usuario, Short idInstitucion, TiposServiciosItem servicio) {
		SQL sql = new SQL();
		
		sql.UPDATE("PYS_SERVICIOS");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '"+ usuario.getIdusuario() + "'");
		
		if(servicio.getFechabaja() != null) {
			sql.SET("FECHABAJA = NULL");
		}
		else{
			sql.SET("FECHABAJA = SYSDATE");
		}
		
		sql.WHERE("IDSERVICIO = '" + servicio.getIdservicio() + "'");
		sql.WHERE("IDTIPOSERVICIOS = '" + servicio.getIdtiposervicios() + "'");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		return sql.toString();
	}

	public String comprobarUsoServicio(ListaServiciosItem servicio, Short idInstitucion) {
		SQL sql = new SQL();
	
		sql.SELECT(" IDPETICION");
		
		sql.FROM(" PYS_SERVICIOSSOLICITADOS");
		
		sql.WHERE(" IDINSTITUCION = '" + idInstitucion +"'");
		
		sql.WHERE(" IDTIPOSERVICIOS = '" + servicio.getIdtiposervicios() + "'");
		
		sql.WHERE(" IDSERVICIO = '" + servicio.getIdservicio() + "'");
		
		sql.WHERE(" IDSERVICIOSINSTITUCION = '" + servicio.getIdserviciosinstitucion() + "'");
		
		return sql.toString();
	}

	public String borradoLogicoServicios(AdmUsuarios usuario, ListaServiciosItem servicio, Short idInstitucion) {
		SQL sql = new SQL();
	
		sql.UPDATE(" PYS_SERVICIOSINSTITUCION");
		
		sql.SET(" FECHAMODIFICACION = SYSDATE");
		sql.SET(" USUMODIFICACION = '"+ usuario.getIdusuario() + "'");
		
		if(servicio.getFechabaja() != null) {
			sql.SET(" FECHABAJA = NULL");
		}
		else{
			sql.SET(" FECHABAJA = SYSDATE");
		}
		
		sql.WHERE(" IDSERVICIO = '" + servicio.getIdservicio() + "'");
		sql.WHERE(" IDTIPOSERVICIOS = '" + servicio.getIdtiposervicios() + "'");
		sql.WHERE(" IDSERVICIOSINSTITUCION = '" + servicio.getIdserviciosinstitucion() + "'");
		sql.WHERE(" IDINSTITUCION = '"+ idInstitucion +"'");

		return sql.toString();
	}

	public String borradoFisicoServiciosRegistro(ListaServiciosItem servicio, Short idInstitucion) {
		SQL sql = new SQL();
	
		sql.DELETE_FROM("PYS_SERVICIOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" IDTIPOSERVICIOS = '" + servicio.getIdtiposervicios() + "'");
		sql.WHERE(" IDSERVICIO = '" + servicio.getIdservicio() + "'");
		sql.WHERE(" IDSERVICIOSINSTITUCION = '" + servicio.getIdserviciosinstitucion() + "'");
		
		return sql.toString();
	}

}