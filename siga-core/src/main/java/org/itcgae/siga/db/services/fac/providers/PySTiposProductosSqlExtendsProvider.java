package org.itcgae.siga.db.services.fac.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysProductosSqlProvider;

public class PySTiposProductosSqlExtendsProvider extends PysProductosSqlProvider{

	public String searchTiposProductos(String idioma, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("pp.IDTIPOPRODUCTO");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("pp.IDPRODUCTO");
		sql.SELECT("pp.DESCRIPCION");
		sql.SELECT("pp.FECHABAJA");
		
		sql.FROM("PYS_PRODUCTOS pp");
		
		sql.JOIN("PYS_TIPOSPRODUCTOS tp ON pp.IDTIPOPRODUCTO = tp.IDTIPOPRODUCTO");
		
		sql.WHERE("pp.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("pp.FECHABAJA IS NULL");
		
		sql.ORDER_BY("pp.IDTIPOPRODUCTO");
		sql.ORDER_BY("pp.IDPRODUCTO");
		sql.ORDER_BY("pp.IDINSTITUCION");

		return sql.toString();
	}
	
	public String searchTiposProductosHistorico(String idioma, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("pp.IDTIPOPRODUCTO");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("pp.IDPRODUCTO");
		sql.SELECT("pp.DESCRIPCION");
		sql.SELECT("pp.FECHABAJA");
		
		sql.FROM("PYS_PRODUCTOS pp");
		
		sql.JOIN("PYS_TIPOSPRODUCTOS tp ON pp.IDTIPOPRODUCTO = tp.IDTIPOPRODUCTO");
		
		sql.WHERE("pp.IDINSTITUCION = '" + idInstitucion + "'");
		
		sql.ORDER_BY("pp.IDTIPOPRODUCTO");
		sql.ORDER_BY("pp.IDPRODUCTO");
		sql.ORDER_BY("pp.IDINSTITUCION");

		return sql.toString();
	}
	
	public String searchTiposProductosByIdCategoria(String idioma, Short idInstitucion, String idCategoria) {
		SQL sql = new SQL();
		
		sql.SELECT("IDPRODUCTO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_PRODUCTOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOPRODUCTO = '" + idCategoria + "'");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	

	public String searchListadoProductosBuscador(String idioma, Short idInstitucion, FiltroProductoItem filtroProductoItem) {
		SQL sql = new SQL();
		
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.DESCRIPCION");
		sql.SELECT(" concat(F_siga_formatonumero(PYS_PRODUCTOSINSTITUCION.VALOR,2), ' €') AS VALOR");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.FECHABAJA");
		sql.SELECT(" F_SIGA_GETRECURSO (PYS_TIPOSPRODUCTOS.DESCRIPCION,1) AS CATEGORIA");
		sql.SELECT(" PYS_PRODUCTOS.DESCRIPCION AS TIPO");
		sql.SELECT(" PYS_TIPOIVA.DESCRIPCION AS IVA");
		sql.SELECT(" concat(F_siga_formatonumero(ROUND((PYS_PRODUCTOSINSTITUCION.VALOR*PYS_TIPOIVA.VALOR/100)+PYS_PRODUCTOSINSTITUCION.VALOR, 2),2), ' €') AS PRECIO_IVA");
		sql.SELECT(" f_siga_getrecurso (PYS_FORMAPAGO.DESCRIPCION, 1) AS FORMA_PAGO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDCONTADOR");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTOINSTITUCION");
		
		sql.FROM(" PYS_PRODUCTOSINSTITUCION");
		
		sql.INNER_JOIN(" PYS_TIPOSPRODUCTOS ON PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = PYS_TIPOSPRODUCTOS.IDTIPOPRODUCTO");
		sql.INNER_JOIN(" PYS_PRODUCTOS ON PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = PYS_PRODUCTOS.IDTIPOPRODUCTO\r\n"
				+ "	AND PYS_PRODUCTOSINSTITUCION.IDPRODUCTO = PYS_PRODUCTOS.IDPRODUCTO\r\n"
				+ "	AND PYS_PRODUCTOSINSTITUCION.IDINSTITUCION = PYS_PRODUCTOS.IDINSTITUCION");
		sql.INNER_JOIN(" PYS_TIPOIVA ON\r\n"
				+ " PYS_PRODUCTOSINSTITUCION.IDTIPOIVA = PYS_TIPOIVA.IDTIPOIVA");
		sql.INNER_JOIN(" PYS_FORMAPAGOPRODUCTO ON\r\n"
				+ "	PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = PYS_FORMAPAGOPRODUCTO.IDTIPOPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOSINSTITUCION.IDPRODUCTO = PYS_FORMAPAGOPRODUCTO.IDPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOSINSTITUCION.IDINSTITUCION = PYS_FORMAPAGOPRODUCTO.IDINSTITUCION\r\n"
				+ " AND PYS_PRODUCTOSINSTITUCION.IDPRODUCTOINSTITUCION = PYS_FORMAPAGOPRODUCTO.IDPRODUCTOINSTITUCION");
		sql.INNER_JOIN(" PYS_FORMAPAGO ON (PYS_FORMAPAGOPRODUCTO.IDFORMAPAGO = PYS_FORMAPAGO.IDFORMAPAGO)");
		
		
		sql.WHERE("PYS_PRODUCTOSINSTITUCION.IDINSTITUCION = '" + idInstitucion +"'");
		
		if(filtroProductoItem.getCategoria() != null && filtroProductoItem.getCategoria() != "")
			sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = '" + filtroProductoItem.getCategoria() + "'");
		
		if(filtroProductoItem.getTipo() != null && filtroProductoItem.getCategoria() != "")
			sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTO = '" + filtroProductoItem.getTipo() + "'");
		
		if(filtroProductoItem.getProducto() != null && filtroProductoItem.getProducto() != "")
			sql.WHERE(" regexp_like(PYS_PRODUCTOSINSTITUCION.DESCRIPCION,'" + filtroProductoItem.getProducto() + "')");
		
		if(filtroProductoItem.getFormaPago() != null && filtroProductoItem.getFormaPago() != "")
			sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDFORMAPAGO = '" + filtroProductoItem.getFormaPago() + "'");
		
		if(filtroProductoItem.getCodigo() != null && filtroProductoItem.getCodigo() != "")
			sql.WHERE(" PYS_PRODUCTOSINSTITUCION.CODIGOEXT = '" + filtroProductoItem.getCodigo() + "'");
		
		if(filtroProductoItem.getIva() != null && filtroProductoItem.getIva() != "")
			sql.WHERE(" PYS_TIPOIVA.IDTIPOIVA = '" + filtroProductoItem.getIva() + "'");
		
		if(filtroProductoItem.getPrecioDesde() != null && filtroProductoItem.getPrecioDesde() != "")
			sql.WHERE(" ROUND((PYS_PRODUCTOSINSTITUCION.VALOR*PYS_TIPOIVA.VALOR/100)+PYS_PRODUCTOSINSTITUCION.VALOR, 2)  >= " + Float.parseFloat(filtroProductoItem.getPrecioDesde()) + "");
		
		if(filtroProductoItem.getPrecioHasta() != null && filtroProductoItem.getPrecioHasta() != "")
			sql.WHERE(" ROUND((PYS_PRODUCTOSINSTITUCION.VALOR*PYS_TIPOIVA.VALOR/100)+PYS_PRODUCTOSINSTITUCION.VALOR, 2)  <= " + Float.parseFloat(filtroProductoItem.getPrecioHasta()) + "");

		sql.ORDER_BY(" PYS_PRODUCTOSINSTITUCION.DESCRIPCION");
		
		return sql.toString();
	}
	
	
	public String comboTiposProductos(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOPRODUCTO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_TIPOSPRODUCTOS");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	
	public String activarDesactivarProducto(AdmUsuarios usuario, Short idInstitucion, TiposProductosItem producto) {
		SQL sql = new SQL();
		
		sql.UPDATE("PYS_PRODUCTOS");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '"+ usuario.getIdusuario() + "'");
		
		if(producto.getFechabaja() != null) {
			sql.SET("FECHABAJA = NULL");
		}
		else{
			sql.SET("FECHABAJA = SYSDATE");
		}
		
		sql.WHERE("IDPRODUCTO = '" + producto.getIdproducto() + "'");
		sql.WHERE("IDTIPOPRODUCTO = '" + producto.getIdtipoproducto() + "'");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		return sql.toString();
	}
	
	
	public String getIndiceMaxProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL((MAX(IDPRODUCTO) + 1),1) AS IDPRODUCTO");
		
		sql.FROM("PYS_PRODUCTOS");
		
		sql.WHERE("IDTIPOPRODUCTO ='" + listadoProductos.get(0).getIdtipoproducto() + "'");;
		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		
		return sql.toString();
	}
	
	
	public String comprobarUsoProducto(ListaProductosItem producto, Short idInstitucion) {
		SQL sql = new SQL();
	
		sql.SELECT(" PYS_COMPRA.IDPETICION");
		
		sql.FROM(" PYS_COMPRA");
		
		sql.JOIN(" PYS_PRODUCTOS ON\r\n"
				+ " PYS_PRODUCTOS.IDPRODUCTO = PYS_COMPRA.IDPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOS.IDTIPOPRODUCTO = PYS_COMPRA.IDTIPOPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOS.IDINSTITUCION = PYS_COMPRA.IDINSTITUCION");
		
		sql.JOIN(" PYS_PETICIONCOMPRASUSCRIPCION ON\r\n"
				+ " PYS_COMPRA.IDPETICION = PYS_PETICIONCOMPRASUSCRIPCION.IDPETICION");
		
		sql.JOIN(" PYS_PRODUCTOSINSTITUCION ON\r\n"
				+ " PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = PYS_PRODUCTOS.IDTIPOPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOSINSTITUCION.IDPRODUCTO = PYS_PRODUCTOS.IDPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOSINSTITUCION.IDINSTITUCION = PYS_PRODUCTOS.IDINSTITUCION");
		
		sql.WHERE(" (PYS_PETICIONCOMPRASUSCRIPCION.IDESTADOPETICION = 10\r\n"
				+ " OR PYS_PETICIONCOMPRASUSCRIPCION.IDESTADOPETICION = 20)");
		
		sql.WHERE("PYS_PRODUCTOS.IDTIPOPRODUCTO = '" + producto.getIdtipoproducto() + "'");
		
		sql.WHERE(" PYS_PRODUCTOS.IDPRODUCTO = '" + producto.getIdproducto() + "'");
		
		sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTOINSTITUCION = '" + producto.getIdproductoinstitucion() + "'");
		
		sql.WHERE(" PYS_PETICIONCOMPRASUSCRIPCION.IDINSTITUCION = '" + idInstitucion +"'");
		
		return sql.toString();
	}
	
	
	public String comprobarSolicitudProducto(ListaProductosItem producto, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" PYS_PRODUCTOSSOLICITADOS.IDPETICION");

		sql.FROM(" PYS_PRODUCTOSSOLICITADOS");
		
		sql.WHERE(" PYS_PRODUCTOSSOLICITADOS.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_PRODUCTOSSOLICITADOS.IDTIPOPRODUCTO = '" + producto.getIdtipoproducto() + "'");
		sql.WHERE(" PYS_PRODUCTOSSOLICITADOS.IDPRODUCTO = '" + producto.getIdproducto() + "'");
		sql.WHERE(" PYS_PRODUCTOSSOLICITADOS.IDPRODUCTOINSTITUCION = '" + producto.getIdproductoinstitucion() + "'");
		
		return sql.toString();
	}
	
	
	public String borradoLogicoProductos(AdmUsuarios usuario, ListaProductosItem producto, Short idInstitucion) {
		SQL sql = new SQL();
	
		sql.UPDATE("PYS_PRODUCTOSINSTITUCION");
		
		sql.SET(" FECHAMODIFICACION = SYSDATE");
		sql.SET(" USUMODIFICACION = '"+ usuario.getIdusuario() + "'");
		
		if(producto.getFechabaja() != null) {
			sql.SET("FECHABAJA = NULL");
		}
		else{
			sql.SET("FECHABAJA = SYSDATE");
		}
		
		sql.WHERE("IDPRODUCTO = '" + producto.getIdproducto() + "'");
		sql.WHERE("IDTIPOPRODUCTO = '" + producto.getIdtipoproducto() + "'");
		sql.WHERE(" IDPRODUCTOINSTITUCION = '" + producto.getIdproductoinstitucion() + "'");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");

		return sql.toString();
	}
	
	
	public String borradoFisicoProductosRegistro(ListaProductosItem producto, Short idInstitucion) {
		SQL sql = new SQL();
	
		sql.DELETE_FROM("PYS_PRODUCTOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" IDTIPOPRODUCTO = '" + producto.getIdtipoproducto() + "'");
		sql.WHERE(" IDPRODUCTO = '" + producto.getIdproducto() + "'");
		sql.WHERE(" IDPRODUCTOINSTITUCION = '" + producto.getIdproductoinstitucion() + "'");
		
		return sql.toString();
	}
	
	
	public String borradoFisicoProductosIdentificador(ListaProductosItem producto, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.DELETE_FROM("ADM_CONTADOR");
		
		sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" IDCONTADOR = '" + producto.getIdcontador()+ "'");
	
		return sql.toString();
	}

}

