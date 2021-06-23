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
	
	public String activarDesactivarProducto(AdmUsuarios usuario, Short idInstitucion, List<TiposProductosItem> listadoProductos) {
		SQL sql = new SQL();
		String idsProductos ="";
		
		//Creo la cadena de ids de productos para usarlos en el IN del sql
		for(int i = 0; i < listadoProductos.size(); i++){
			if(i != listadoProductos.size() - 1) {
				idsProductos = idsProductos + Integer.toString(listadoProductos.get(i).getIdproducto()) +",";
			}else if(i == listadoProductos.size() - 1) {
				idsProductos = idsProductos + Integer.toString(listadoProductos.get(i).getIdproducto());
			}
		}
		
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
	
	public String getIndiceMaxProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL((MAX(IDPRODUCTO) + 1),1) AS IDPRODUCTO");
		
		sql.FROM("PYS_PRODUCTOS");
		
		sql.WHERE("IDTIPOPRODUCTO ='" + listadoProductos.get(0).getIdtipoproducto() + "'");;
		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		
		return sql.toString();
	}
	
//	public String crearProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion, AdmUsuarios usuario, int indice) {
//		SQL sql = new SQL();
//		
//		sql.INSERT_INTO("PYS_PRODUCTOS");
//		sql.INTO_COLUMNS("IDTIPOPRODUCTO, IDPRODUCTO, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, IDINSTITUCION, FECHABAJA");
//		sql.VALUES();
//		
//		//Se realizará un INSERT en la tabla PYS_PRODUCTOS, modificando el campo DESCRIPCION con el valor obtenido por pantalla
//		
//		
//		return sql.toString();
//	}
	

	
	public String modificarProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion, AdmUsuarios usuario) {
		SQL sql = new SQL();
		String idsProductos ="";
		
		
		sql.UPDATE("PYS_PRODUCTOS");	//HAY QUE HACER UN FOR PARA EJECUTAR LA SQL VARIAS VECES
//		sql.SET("DESCRIPCION = '" +  ); //MULTIPLES DESCRIPCIONES
//		
//		Se realizará un UPDATE en la tabla PYS_PRODUCTOS, modificando el campo DESCRIPCION con el valor obtenido por pantalla, FECHAMODIFICACION y USUMODIFICACION con la fecha y el usuario actual
//		Se utilizará la entidad PYS_PRODUCTOS que genera MyBatis

		
		return sql.toString();
	}

}

