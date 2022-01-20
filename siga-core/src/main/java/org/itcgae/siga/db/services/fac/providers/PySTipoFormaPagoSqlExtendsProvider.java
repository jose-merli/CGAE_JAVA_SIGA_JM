package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;

public class PySTipoFormaPagoSqlExtendsProvider {
	
	public String comboTipoFormaPago(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDFORMAPAGO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_FORMAPAGO");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}

	public String comboTipoFormaPagoInternet(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDFORMAPAGO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_FORMAPAGO");
		
		sql.WHERE("INTERNET = 'A'");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	public String comboTipoFormaPagoSecretaria(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDFORMAPAGO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_FORMAPAGO");
		
		sql.WHERE("INTERNET = 'S'");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	public String comboPreciosServicio(String idioma) {
		return "";
	}
	
	//Realiza un borrado fisico de las formas de pago de un producto
	public String borradoFisicoFormasPagoByProducto(ListaProductosItem producto,Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.DELETE_FROM(" PYS_FORMAPAGOPRODUCTO");
		
		sql.WHERE(" IDTIPOPRODUCTO = " + producto.getIdtipoproducto());
		sql.WHERE(" IDPRODUCTO = " + producto.getIdproducto());
		sql.WHERE(" IDPRODUCTOINSTITUCION = " +  producto.getIdproductoinstitucion());
		sql.WHERE(" IDINSTITUCION = " + idInstitucion);
				
		return sql.toString();
	}
	
	//Realiza un borrado fisico de las formas de pago de un servicio
	public String borradoFisicoFormasPagoByServicio(ListaServiciosItem servicio,Short idInstitucion) {
			SQL sql = new SQL();
			
			sql.DELETE_FROM(" PYS_FORMAPAGOSERVICIOS");
			
			sql.WHERE(" IDTIPOSERVICIOS = " + servicio.getIdtiposervicios());
			sql.WHERE(" IDSERVICIO = " + servicio.getIdservicio());
			sql.WHERE(" IDSERVICIOSINSTITUCION = " +  servicio.getIdserviciosinstitucion());
			sql.WHERE(" IDINSTITUCION = " + idInstitucion);
					
			return sql.toString();
	}
}