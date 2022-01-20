package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.db.mappers.PysProductosinstitucionSqlProvider;

public class PysProductosinstitucionSqlExtendsProvider extends PysProductosinstitucionSqlProvider {

	public String selectTypesCertificatesCourse(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("pys.idTipoProducto");
		sql.SELECT("pys.idProducto");
		sql.SELECT("CONCAT( CONCAT(pys.idTipoProducto, pys.idProducto), pys.idProductoInstitucion) as value");
		sql.SELECT("pys.idProductoInstitucion");
		sql.SELECT("pys.descripcion");
		sql.SELECT("pys.descripcion as label");
		sql.SELECT("pys.valor");
		sql.SELECT("CONCAT( CONCAT(pys.idTipoProducto, pys.idProducto), pys.idProductoInstitucion) AS CLAVE");

		sql.FROM("PYS_PRODUCTOSINSTITUCION pys");
		
		sql.WHERE("pys.idInstitucion =" + idInstitucion);
		sql.WHERE("pys.TIPOCERTIFICADO = 'C'");
		sql.WHERE("pys.FECHABAJA IS NULL");
		
		return sql.toString();
	}
	
	public String detalleProducto(int idTipoProducto, int idProducto, int idProductoInstitucion, Short idInstitucion) {

		SQL sql = new SQL();
		
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTOINSTITUCION");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.DESCRIPCION");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.VALOR");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.MOMENTOCARGO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.SOLICITARBAJA");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.SOLICITARALTA");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.CUENTACONTABLE");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDIMPRESORA");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDPLANTILLA");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.TIPOCERTIFICADO");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.FECHABAJA");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDCONTADOR");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.NOFACTURABLE");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.IDTIPOIVA");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.CODIGOEXT");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.CODIGO_TRASPASONAV");
		sql.SELECT(" PYS_PRODUCTOSINSTITUCION.ORDEN");
		
		sql.SELECT(" PYS_PRODUCTOS.DESCRIPCION AS TIPO");
		sql.SELECT(" PYS_TIPOIVA.VALOR AS VALORIVA");
		sql.SELECT(" F_SIGA_GETRECURSO (PYS_TIPOSPRODUCTOS.DESCRIPCION, 1) AS CATEGORIA");
		
		
		sql.FROM( "PYS_PRODUCTOSINSTITUCION");
		
		sql.JOIN(" PYS_PRODUCTOS ON\r\n"
				+ " PYS_PRODUCTOSINSTITUCION.IDPRODUCTO = PYS_PRODUCTOS.IDPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = PYS_PRODUCTOS.IDTIPOPRODUCTO\r\n"
				+ " AND PYS_PRODUCTOS.IDINSTITUCION = PYS_PRODUCTOSINSTITUCION.IDINSTITUCION");
		
		sql.JOIN(" PYS_TIPOSPRODUCTOS ON\r\n"
				+ " PYS_TIPOSPRODUCTOS.IDTIPOPRODUCTO = PYS_PRODUCTOS.IDTIPOPRODUCTO");
		
		sql.JOIN(" PYS_TIPOIVA ON\r\n"
				+ " PYS_PRODUCTOSINSTITUCION.IDTIPOIVA = PYS_TIPOIVA.IDTIPOIVA");
		
		sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDTIPOPRODUCTO = '" + idTipoProducto + "'");
		sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTO = '" + idProducto + "'");
		sql.WHERE(" PYS_PRODUCTOSINSTITUCION.IDPRODUCTOINSTITUCION = '" + idProductoInstitucion + "'");
		
		sql.ORDER_BY("PYS_PRODUCTOSINSTITUCION.DESCRIPCION");
		
		return sql.toString();
	}
	
	public String obtenerFormasDePagoInternetByProducto(int idTipoProducto, int idProducto, int idProductoInstitucion, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDFORMAPAGO");
				
		sql.FROM(" PYS_FORMAPAGOPRODUCTO");
				
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDTIPOPRODUCTO = '" + idTipoProducto + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDPRODUCTO = '" + idProducto + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDPRODUCTOINSTITUCION = '" + idProductoInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.INTERNET = 'A'" );
				
		return sql.toString();
	}
	
	public String obtenerFormasDePagoSecretariaByProducto(int idTipoProducto, int idProducto, int idProductoInstitucion, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDFORMAPAGO");
				
		sql.FROM(" PYS_FORMAPAGOPRODUCTO");
				
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDTIPOPRODUCTO = '" + idTipoProducto + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDPRODUCTO = '" + idProducto + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.IDPRODUCTOINSTITUCION = '" + idProductoInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOPRODUCTO.INTERNET = 'S'" );
				
		return sql.toString();
	}

	public String getIndiceMaxProducto(ProductoDetalleDTO producto, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" NVL((MAX(IDPRODUCTOINSTITUCION) + 1),1) AS IDPRODUCTOINSTITUCION");
		
		sql.FROM(" PYS_PRODUCTOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" IDTIPOPRODUCTO ='" + producto.getIdtipoproducto() + "'");
		sql.WHERE(" IDPRODUCTO ='" + producto.getIdproducto() + "'");
		
		return sql.toString();
	}
	
	public String getIdProductoInstitucion(ProductoDetalleDTO producto, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDPRODUCTOINSTITUCION");
		
		sql.FROM(" PYS_PRODUCTOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" IDTIPOPRODUCTO ='" + producto.getIdtipoproducto() + "'");
		sql.WHERE(" IDPRODUCTO ='" + producto.getIdproducto() + "'");
		sql.WHERE(" DESCRIPCION = '" + producto.getDescripcion() + "'");
		
		return sql.toString();
	}

}