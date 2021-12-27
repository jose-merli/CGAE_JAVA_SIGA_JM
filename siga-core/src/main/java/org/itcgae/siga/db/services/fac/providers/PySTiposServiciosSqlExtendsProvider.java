package org.itcgae.siga.db.services.fac.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
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

	//Servicio que devuelve la informacion necesaria para la tabla en Facturacion --> Servicios.
	public String searchListadoServiciosBuscador(String idioma, Short idInstitucion, FiltroServicioItem filtroServicioItem) {
		SQL sql = new SQL();
		
		sql.SELECT("  DECODE(\r\n"
				+ " valor_minimo,\r\n"
				+ " NULL,\r\n"
				+ " 'Sin precio',\r\n"
				+ " (valor_minimo\r\n"
				+ "  || '/'\r\n"
				+ " || f_siga_getrecurso(\r\n"
				+ " perio_min.descripcion,\r\n"
				+ " 1\r\n"
				+ " )\r\n"
				+ " || ' - '\r\n"
				+ " || valor_maximo\r\n"
				+ " || '/'\r\n"
				+ " || f_siga_getrecurso(\r\n"
				+ " perio_max.descripcion,\r\n"
				+ " 1\r\n"
				+ " ) )\r\n"
				+ " ) precio");
		sql.SELECT(" valor_minimo valorminimo");
		sql.SELECT(" f_siga_getrecurso(perio_min.descripcion, 1) periodominimo");
		sql.SELECT(" valor_maximo valormaximo");
		sql.SELECT(" f_siga_getrecurso(perio_max.descripcion, 1) periodomaximo");
		sql.SELECT(" servin.idinstitucion");
		sql.SELECT(" servin.idservicio");
		sql.SELECT(" servin.idtiposervicios");
		sql.SELECT(" servin.idserviciosinstitucion");
		sql.SELECT(" servin.descripcion");
		sql.SELECT(" servin.fechabaja");
		sql.SELECT(" case when servin.automatico = 1 then 'Automática' when servin.automatico = 0 then 'Manual' end AUTOMATICO");
		sql.SELECT(" servin.idtipoiva");
		sql.SELECT(" f_siga_getrecurso(\r\n"
				+ " tservicio.descripcion,\r\n"
				+ " 1\r\n"
				+ " ) AS categoria");
		sql.SELECT(" servis.descripcion AS tipo");
		sql.SELECT(" tiva.descripcion AS iva");
		sql.SELECT(" CASE\r\n"
				+ " WHEN COUNT(foserv.idformapago) <= 3 THEN\r\n"
				+ " LISTAGG(\r\n"
				+ " f_siga_getrecurso(\r\n"
				+ " fo.descripcion,\r\n"
				+ " 1\r\n"
				+ " ),\r\n"
				+ " ','\r\n"
				+ " ) WITHIN GROUP(ORDER BY servin.descripcion)\r\n"
				+ " ELSE TO_CHAR(COUNT(foserv.idformapago) )\r\n"
				+ " END\r\n"
				+ " forma_pago");
		sql.SELECT("servin.noFacturable");
		sql.SELECT("LISTAGG(fo.idformapago, ',') WITHIN GROUP (ORDER BY fo.idformapago) as idformaspago");
		sql.SELECT("LISTAGG(fo.internet, ',') WITHIN GROUP (ORDER BY fo.idformapago) as formaspagoInternet");
		sql.SELECT("servin.SOLICITARBAJA");
		sql.SELECT(" TIVA.FECHABAJA AS fechaBajaIva");
		sql.SELECT(" TIVA.valor as valorIva");
		
		sql.FROM("   (\r\n"
				+ "	        SELECT\r\n"
				+ "	            MIN(precioserv_min.valor) valor_minimo,\r\n"
				+ "	            MIN(precioserv_min.idperiodicidad) id_periodicidad_minima,\r\n"
				+ "	            MAX(precioserv_max.valor) valor_maximo,\r\n"
				+ "	            MAX(precioserv_max.idperiodicidad) id_periodicidad_maxima,\r\n"
				+ "	            servin.idinstitucion,\r\n"
				+ "	            servin.idservicio,\r\n"
				+ "	            servin.idtiposervicios,\r\n"
				+ "	            servin.idserviciosinstitucion\r\n"
				+ "	        FROM\r\n"
				+ "	            pys_serviciosinstitucion servin,\r\n"
				+ "	            pys_preciosservicios precioserv_min,\r\n"
				+ "	            pys_periodicidad perio_min,\r\n"
				+ "	            pys_preciosservicios precioserv_max,\r\n"
				+ "	            pys_periodicidad perio_max\r\n"
				+ "	        WHERE\r\n"
				+ "	                servin.idinstitucion = '" + idInstitucion +"'\r\n"
				+ "	/*and servin.idservicio  = 2\r\n"
				+ "	AND servin.idtiposervicios  = 12\r\n"
				+ "	AND servin.idserviciosinstitucion  = 1*/\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_min.idservicio (+) = servin.idservicio\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_min.idtiposervicios (+) = servin.idtiposervicios\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_min.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_min.idinstitucion (+) = servin.idinstitucion\r\n"
				+ "	            AND\r\n"
				+ "	                perio_min.idperiodicidad (+) = precioserv_min.idperiodicidad\r\n"
				+ "	            AND\r\n"
				+ "	                ( precioserv_min.valor / perio_min.periodosmes ) = (\r\n"
				+ "	                    SELECT\r\n"
				+ "	                        MIN(precioserv.valor / perio.periodosmes)\r\n"
				+ "	                    FROM\r\n"
				+ "	                        pys_preciosservicios precioserv,\r\n"
				+ "	                        pys_periodicidad perio\r\n"
				+ "	                    WHERE\r\n"
				+ "	                            precioserv.idservicio (+) = servin.idservicio\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv.idtiposervicios (+) = servin.idtiposervicios\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv.idinstitucion (+) = servin.idinstitucion\r\n"
				+ "	                        AND\r\n"
				+ "	                            perio.idperiodicidad (+) = precioserv.idperiodicidad\r\n"
				+ "	                )\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_max.idservicio (+) = servin.idservicio\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_max.idtiposervicios (+) = servin.idtiposervicios\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_max.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
				+ "	            AND\r\n"
				+ "	                precioserv_max.idinstitucion (+) = servin.idinstitucion\r\n"
				+ "	            AND\r\n"
				+ "	                perio_max.idperiodicidad (+) = precioserv_max.idperiodicidad\r\n"
				+ "	            AND\r\n"
				+ "	                ( precioserv_max.valor / perio_max.periodosmes ) = (\r\n"
				+ "	                    SELECT\r\n"
				+ "	                        MAX(precioserv.valor / perio.periodosmes)\r\n"
				+ "	                    FROM\r\n"
				+ "	                        pys_preciosservicios precioserv,\r\n"
				+ "	                        pys_periodicidad perio\r\n"
				+ "	                    WHERE\r\n"
				+ "	                            precioserv.idservicio (+) = servin.idservicio\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv.idtiposervicios (+) = servin.idtiposervicios\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv.idinstitucion (+) = servin.idinstitucion\r\n"
				+ "	                        AND\r\n"
				+ "	                            perio.idperiodicidad (+) = precioserv.idperiodicidad\r\n"
				+ "	                )\r\n"
				+ "	        GROUP BY\r\n"
				+ "	            servin.idinstitucion,\r\n"
				+ "	            servin.idservicio,\r\n"
				+ "	            servin.idtiposervicios,\r\n"
				+ "	            servin.idserviciosinstitucion\r\n"
				+ "	        UNION\r\n"
				+ "	        SELECT\r\n"
				+ "	            NULL,\r\n"
				+ "	            NULL,\r\n"
				+ "	            NULL,\r\n"
				+ "	            NULL,\r\n"
				+ "	            servin.idinstitucion,\r\n"
				+ "	            servin.idservicio,\r\n"
				+ "	            servin.idtiposervicios,\r\n"
				+ "	            servin.idserviciosinstitucion\r\n"
				+ "	        FROM\r\n"
				+ "	            pys_serviciosinstitucion servin\r\n"
				+ "	        WHERE\r\n"
				+ "	                servin.idinstitucion = '" + idInstitucion + "'\r\n"
				+ "	            AND NOT\r\n"
				+ "	                EXISTS (\r\n"
				+ "	                    SELECT\r\n"
				+ "	                        1\r\n"
				+ "	                    FROM\r\n"
				+ "	                        pys_preciosservicios precioserv_min\r\n"
				+ "	                    WHERE\r\n"
				+ "	                            precioserv_min.idservicio = servin.idservicio\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv_min.idtiposervicios = servin.idtiposervicios\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv_min.idserviciosinstitucion = servin.idserviciosinstitucion\r\n"
				+ "	                        AND\r\n"
				+ "	                            precioserv_min.idinstitucion = servin.idinstitucion\r\n"
				+ "	                )\r\n"
				+ "	    ) a");
		sql.FROM(" pys_periodicidad perio_min");
		sql.FROM(" pys_periodicidad perio_max");
		sql.FROM(" pys_serviciosinstitucion servin");
		sql.FROM(" pys_tipoiva tiva");
		sql.FROM(" pys_tiposervicios tservicio");
		sql.FROM(" pys_servicios servis");
		sql.FROM(" pys_formapagoservicios foserv");
		sql.FROM(" pys_formapago fo");
		if(filtroServicioItem.getFormaPago() != null && filtroServicioItem.getFormaPago() != "")
			sql.FROM(" pys_formapagoservicios foserv2");
		
		sql.WHERE(" perio_min.idperiodicidad (+) = a.id_periodicidad_minima");
		sql.WHERE(" perio_max.idperiodicidad (+) = a.id_periodicidad_maxima");
		sql.WHERE(" servin.idinstitucion = a.idinstitucion");
		sql.WHERE(" servin.idtiposervicios = a.idtiposervicios");
		sql.WHERE(" servin.idserviciosinstitucion = a.idserviciosinstitucion");
		sql.WHERE(" servin.idservicio = a.idservicio");
		sql.WHERE(" tiva.idtipoiva (+) = servin.idtipoiva");
		sql.WHERE(" tservicio.idtiposervicios (+) = servin.idtiposervicios");
		sql.WHERE(" servis.idservicio (+) = servin.idservicio");
		sql.WHERE(" servis.idtiposervicios (+) = servin.idtiposervicios");
		sql.WHERE(" servis.idinstitucion (+) = servin.idinstitucion");
		sql.WHERE(" foserv.idinstitucion (+) = servin.idinstitucion");
		sql.WHERE(" foserv.idtiposervicios (+) = servin.idtiposervicios");
		sql.WHERE(" foserv.idservicio (+) = servin.idservicio");
		sql.WHERE(" foserv.idserviciosinstitucion (+) = servin.idserviciosinstitucion");
		sql.WHERE(" fo.idformapago (+) = foserv.idformapago");
		
		if(filtroServicioItem.getFormaPago() != null && filtroServicioItem.getFormaPago() != "") {
			sql.WHERE(" foserv2.idinstitucion = servin.idinstitucion");
			sql.WHERE(" foserv2.idtiposervicios = servin.idtiposervicios");
			sql.WHERE(" foserv2.idservicio = servin.idservicio");
			sql.WHERE(" foserv2.idserviciosinstitucion = servin.idserviciosinstitucion");
			sql.WHERE(" foserv2.IDFORMAPAGO = '" + filtroServicioItem.getFormaPago() + "'");
		}
		
		if(filtroServicioItem.getTipoSuscripcion() != null && filtroServicioItem.getTipoSuscripcion() != "")
			sql.WHERE(" servin.AUTOMATICO = '" + filtroServicioItem.getTipoSuscripcion() + "'");
		
		if(filtroServicioItem.getCategoria() != null && filtroServicioItem.getCategoria() != "")
			sql.WHERE("  servin.IDTIPOSERVICIOS = '" + filtroServicioItem.getCategoria() + "'");
		
		if(filtroServicioItem.getTipo() != null && filtroServicioItem.getTipo() != "")
			sql.WHERE(" servin.IDSERVICIO = '" + filtroServicioItem.getTipo() + "'");
		
		if(filtroServicioItem.getCodigo() != null && filtroServicioItem.getCodigo() != "")
			sql.WHERE(" servin.CODIGOEXT = '" + filtroServicioItem.getCodigo() + "'");
		
		if(filtroServicioItem.getIva() != null && filtroServicioItem.getIva() != "")
			sql.WHERE(" servin.IDTIPOIVA = '" + filtroServicioItem.getIva() + "'");
		
		if(filtroServicioItem.getServicio() != null && filtroServicioItem.getServicio() != "")
        	sql.WHERE("  upper(servin.DESCRIPCION ) like upper('%" + filtroServicioItem.getServicio() + "%')");
		
		if(filtroServicioItem.getPrecioDesde() != null && filtroServicioItem.getPrecioDesde() != "")
			sql.WHERE(" valor_minimo >= " + Float.parseFloat(filtroServicioItem.getPrecioDesde()) + "");
	
		if(filtroServicioItem.getPrecioHasta() != null && filtroServicioItem.getPrecioHasta() != "")
			sql.WHERE(" valor_minimo <= " + Float.parseFloat(filtroServicioItem.getPrecioHasta()) + "");

		sql.GROUP_BY(" valor_minimo");
		sql.GROUP_BY(" perio_min.descripcion");
		sql.GROUP_BY(" valor_maximo");
		sql.GROUP_BY(" perio_max.descripcion");
		sql.GROUP_BY(" servin.idinstitucion");
		sql.GROUP_BY(" servin.idservicio");
		sql.GROUP_BY(" servin.idtiposervicios");
		sql.GROUP_BY(" servin.idserviciosinstitucion");
		sql.GROUP_BY(" servin.fechabaja");
		sql.GROUP_BY(" servin.idtipoiva");
		sql.GROUP_BY(" tservicio.descripcion");
		sql.GROUP_BY(" servin.descripcion");
		sql.GROUP_BY(" servin.automatico");
		sql.GROUP_BY(" tiva.descripcion");
		sql.GROUP_BY(" servis.descripcion");
		sql.GROUP_BY("servin.noFacturable");
		sql.GROUP_BY("servin.SOLICITARBAJA");
		sql.GROUP_BY("TIVA.FECHABAJA");
		sql.GROUP_BY("TIVA.valor");
			
		sql.ORDER_BY(" servin.DESCRIPCION");
		
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