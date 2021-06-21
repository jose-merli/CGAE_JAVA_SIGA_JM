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
		//pp.FECHABAJA IS NULL te devuelve los no eliminados (los que no tienen historico) 
		sql.WHERE("pp.FECHABAJA IS NULL");
		
		sql.ORDER_BY("pp.IDTIPOPRODUCTO");
		sql.ORDER_BY("pp.IDPRODUCTO");
		sql.ORDER_BY("pp.IDINSTITUCION");

		return sql.toString();
	}
	
	public String activarDesactivarProducto(AdmUsuarios usuario, Short idInstitucion, List<TiposProductosItem> listadoProductos) {
		SQL sql = new SQL();
		String idsProductos ="";
		
		for(int i = 0; i < listadoProductos.size(); i++){
			if(i != listadoProductos.size() - 1) {
				idsProductos = idsProductos + Integer.toString(listadoProductos.get(i).getIdproducto()) +",";
			}else if(i == listadoProductos.size() - 1) {
				idsProductos = idsProductos + Integer.toString(listadoProductos.get(i).getIdproducto());
			}
		}
		
		//idsProductos.replace("'", "");
		
		sql.UPDATE("PYS_PRODUCTOS");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '"+ usuario.getIdusuario() + "'");
		
		if(listadoProductos.get(0).getFechabaja() != null) {
			sql.SET("FECHABAJA = NULL");
		}
		else{
			sql.SET("FECHABAJA = SYSDATE");
		}
		
		
		
		sql.WHERE("IDPRODUCTO IN ("+ idsProductos +")");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		return sql.toString();
	}

}

