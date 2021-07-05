package org.itcgae.siga.db.services.fac.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
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

}

