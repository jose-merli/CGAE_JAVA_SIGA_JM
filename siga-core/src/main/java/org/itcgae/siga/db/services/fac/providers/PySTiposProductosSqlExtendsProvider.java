package org.itcgae.siga.db.services.fac.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysProductosSqlProvider;

public class PySTiposProductosSqlExtendsProvider extends PysProductosSqlProvider{

	//Datos tabla pantalla Maestros --> Tipos Productos
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
	
	//Datos con historico (incluidos registros con fechabaja != null) tabla pantalla Maestros --> Tipos Productos
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
	
	//Obtiene los datos del combo categoria de productos (PYS_TIPOSPRODUCTOS)
	public String comboTiposProductos(Short idInstitucion,String idioma) {
		SQL sql = new SQL();
		
		/*sql.SELECT("IDTIPOPRODUCTO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		sql.FROM("PYS_TIPOSPRODUCTOS");	
		sql.ORDER_BY("DESCRIPCION");*/
		sql.SELECT_DISTINCT("pp.IDTIPOPRODUCTO AS ID");
		sql.SELECT("(select  f_siga_getrecurso (ps.DESCRIPCION,'" + idioma + "') from  PYS_TIPOSPRODUCTOS ps where ps.IDTIPOPRODUCTO = pp.IDTIPOPRODUCTO )DESCRIPCION");
		sql.FROM("PYS_PRODUCTOS pp");
		sql.WHERE("pp.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	//Realiza un borrado logico (establecer fechabaja = new Date()) o lo reactiva en caso de que esta inhabilitado.
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
	
	//Obtiene el siguiente id a establecer a la hora de crear un nuevo tipo producto (idproducto - PYS_PRODUCTOS)
		public String getIndiceMaxTipoProducto(int idtipoproducto, Short idInstitucion) {
			SQL sql = new SQL();
			
			sql.SELECT("NVL((MAX(IDPRODUCTO) + 1),1) AS IDPRODUCTO");
			
			sql.FROM("PYS_PRODUCTOS");
			
			sql.WHERE("IDTIPOPRODUCTO ='" + idtipoproducto + "'");;
			sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
			
			return sql.toString();
		}
	
	//Obtiene el siguiente id a establecer a la hora de crear un nuevo tipo producto (idproducto - PYS_PRODUCTOS) //REVISAR EN QUE SITIOS SE USA
	public String getIndiceMaxProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL((MAX(IDPRODUCTO) + 1),1) AS IDPRODUCTO");
		
		sql.FROM("PYS_PRODUCTOS");
		
		sql.WHERE("IDTIPOPRODUCTO ='" + listadoProductos.get(0).getIdtipoproducto() + "'");;
		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		
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
	
	public String searchTiposProductosByIdCategoriaMultiple(String idioma, Short idInstitucion, String idCategoria) { //En realidad busca los productos con ese idcategoria
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOPRODUCTO || '-' || IDPRODUCTO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_PRODUCTOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOPRODUCTO IN ( " + idCategoria + ")");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	//Obtiene la informacion de los productos al darle a buscar en Facturacion --> Productos para rellenar la tabla.
	public String searchListadoProductosBuscador(String idioma, Short idInstitucion, FiltroProductoItem filtroProductoItem) {
		SQL sql = new SQL();
		
		sql.SELECT(" PRIN.IDPRODUCTO");
		sql.SELECT(" PRIN.IDTIPOPRODUCTO");
		sql.SELECT(" PRIN.IDPRODUCTOINSTITUCION");
		sql.SELECT(" PRIN.DESCRIPCION");
		sql.SELECT(" PRIN.FECHABAJA");
		sql.SELECT("count(fopa1.idformapago) AS NUM_FORMAS_PAGO\r\n"
				+ ", case when count(fopa1.idformapago)<=3 \r\n"
				+ " then LISTAGG(f_siga_getrecurso(fo.descripcion,"+idioma+") , ', ') WITHIN GROUP (ORDER BY prin.descripcion)\r\n"
				+ " else to_char(count(fopa1.idformapago))\r\n"
				+ " end FORMAS_PAGO");
		sql.SELECT("LISTAGG(fo.idformapago, ',') WITHIN GROUP (ORDER BY fo.idformapago) as idformaspago");
		sql.SELECT("LISTAGG(fo.internet, ',') WITHIN GROUP (ORDER BY fo.idformapago) as formaspagoInternet");
		sql.SELECT(" concat(F_siga_formatonumero(PRIN.VALOR,2), ' €') AS VALOR");
		sql.SELECT(" F_SIGA_GETRECURSO (TPRODUCTO.DESCRIPCION,"+idioma+") AS CATEGORIA");
		sql.SELECT(" PRODUC.DESCRIPCION AS TIPO");
		sql.SELECT(" tiva.idtipoiva as idtipoiva");
		sql.SELECT(" TIVA.valor as valorIva");
		sql.SELECT(" TIVA.DESCRIPCION AS IVA");
		sql.SELECT(" TIVA.FECHABAJA AS fechaBajaIva");
		sql.SELECT(" concat(F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2), ' €') AS PRECIO_IVA");
		sql.SELECT(" PRIN.IDCONTADOR");
		sql.SELECT(" PRIN.NOFACTURABLE");
		sql.SELECT(" PRIN.SOLICITARBAJA");
		
		sql.FROM(" pys_productosinstitucion prin, pys_formapagoproducto fopa1, pys_formapago fo, pys_tipoiva tiva, pys_tiposproductos tproducto, pys_productos produc");
		if(filtroProductoItem.getFormaPago() != null && filtroProductoItem.getFormaPago() != "") {
			sql.FROM(" pys_formapagoproducto fopa2");
		}
		
		sql.WHERE(" PRIN.IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE(" fopa1.idinstitucion(+) = prin.idinstitucion");
		sql.WHERE(" fopa1.idtipoproducto(+) = prin.idtipoproducto");
		sql.WHERE(" fopa1.idproducto(+) = prin.idproducto");
		sql.WHERE(" fopa1.idproductoinstitucion(+) = prin.idproductoinstitucion");
		sql.WHERE(" fo.idformapago (+) = fopa1.idformapago");
		sql.WHERE(" tiva.idtipoiva (+) = prin.idtipoiva");
		sql.WHERE(" tproducto.idtipoproducto (+) = prin.idtipoproducto");
		sql.WHERE(" produc.idproducto (+) = prin.idproducto");
		sql.WHERE(" produc.idtipoproducto (+) = prin.idtipoproducto");
		sql.WHERE(" produc.idinstitucion (+) = prin.idinstitucion");

		if(filtroProductoItem.getCategoria() != null && filtroProductoItem.getCategoria() != "")
			sql.WHERE(" PRIN.IDTIPOPRODUCTO = '" + filtroProductoItem.getCategoria() + "'");
		
		if(filtroProductoItem.getTipo() != null && filtroProductoItem.getTipo() != "")
			sql.WHERE(" PRIN.IDPRODUCTO = '" + filtroProductoItem.getTipo() + "'");
		
		if(filtroProductoItem.getProducto() != null && filtroProductoItem.getProducto() != "")
			sql.WHERE("  upper(prin.descripcion) like upper('%" + filtroProductoItem.getProducto() + "%')");
		if(filtroProductoItem.getFormaPago() != null && filtroProductoItem.getFormaPago() != "") {
			sql.WHERE(" fopa2.idinstitucion = prin.idinstitucion");
			sql.WHERE(" fopa2.idtipoproducto = prin.idtipoproducto");
			sql.WHERE(" fopa2.idproducto = prin.idproducto");
			sql.WHERE(" fopa2.idproductoinstitucion = prin.idproductoinstitucion");
			sql.WHERE(" fopa2.IDFORMAPAGO = '" + filtroProductoItem.getFormaPago() + "'");
		}
		
		if(filtroProductoItem.getCodigo() != null && filtroProductoItem.getCodigo() != "")
			sql.WHERE(" PRIN.CODIGOEXT = '" + filtroProductoItem.getCodigo() + "'");
		
		if(filtroProductoItem.getIva() != null && filtroProductoItem.getIva() != "")
			sql.WHERE(" TIVA.IDTIPOIVA = '" + filtroProductoItem.getIva() + "'");
		
		if(filtroProductoItem.getPrecioDesde() != null && filtroProductoItem.getPrecioDesde() != "")
			sql.WHERE(" ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2)  >= " + Float.parseFloat(filtroProductoItem.getPrecioDesde()) + "");
		
		if(filtroProductoItem.getPrecioHasta() != null && filtroProductoItem.getPrecioHasta() != "")
			sql.WHERE(" ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2)  <= " + Float.parseFloat(filtroProductoItem.getPrecioHasta()) + "");
		
		sql.GROUP_BY(" prin.idproducto, prin.idtipoproducto, prin.idproductoinstitucion, prin.fechabaja, "
				+ "prin.valor, tproducto.descripcion, produc.descripcion, prin.descripcion, tiva.descripcion, "
				+ "tiva.valor, prin.idcontador, PRIN.NOFACTURABLE, tiva.idtipoiva,  TIVA.DESCRIPCION,"
				+ "tiva.fechabaja, PRIN.SOLICITARBAJA");

		sql.ORDER_BY(" PRIN.DESCRIPCION");
		
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
		
		//REVISAR: ESA COLUMNA YA NO SE USA
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
	
	public String obtenerCodigosPorColegio(Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" CODIGOEXT");
		
		sql.FROM(" PYS_PRODUCTOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		
		return sql.toString();
	}
	
	public String getImpTotalesCompra(Short idInstitucion, Long idPeticion) {
		
		SQL sql = new SQL();
		
		sql.SELECT(" ROUND(((prodSol.VALOR*TIVA.VALOR/100)+prodSol.VALOR)*prodSol.cantidad, 2) as importeTotal");
		
		sql.FROM(" PYS_COMPRA");
		
		sql.JOIN(" PYS_PETICIONCOMPRASUSCRIPCION ON\r\n"
				+ " PYS_COMPRA.IDPETICION = PYS_PETICIONCOMPRASUSCRIPCION.IDPETICION");
		
		sql.JOIN(" PYS_PRODUCTOSSOLICITADOS prodSol ON\r\n"
				+ " PYS_COMPRA.IDTIPOPRODUCTO = prodSol.IDTIPOPRODUCTO\r\n"
				+ " AND PYS_COMPRA.IDPRODUCTO = prodSol.IDPRODUCTO\r\n"
				+ " AND PYS_COMPRA.IDPRODUCTOINSTITUCION = prodSol.idProductoInstitucion \r\n"
				+ " AND PYS_PETICIONCOMPRASUSCRIPCION.IDINSTITUCION = prodSol.IDINSTITUCION \r\n"
				+ " AND PYS_PETICIONCOMPRASUSCRIPCION.IDPETICION = prodSol.IDPETICION");
		
		sql.JOIN("pys_tipoiva tiva on prodSol.idtipoiva = tiva.idtipoiva");
		
		
		
		sql.WHERE(" PYS_PETICIONCOMPRASUSCRIPCION.IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE(" PYS_PETICIONCOMPRASUSCRIPCION.IDPETICION = '" + idPeticion +"'");
		
		return sql.toString();
	}

}

