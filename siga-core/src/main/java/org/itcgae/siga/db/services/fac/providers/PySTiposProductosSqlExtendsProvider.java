package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.db.mappers.PysProductosSqlProvider;

public class PySTiposProductosSqlExtendsProvider extends PysProductosSqlProvider{

	public String searchTiposProductos(String idioma, Short institucion) {
		SQL sql = new SQL();

		sql.SELECT("pp.IDTIPOPRODUCTO");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("pp.IDPRODUCTO");
		sql.SELECT("pp.DESCRIPCION");
		sql.SELECT("pp.FECHABAJA");
		
		sql.FROM("PYS_PRODUCTOS pp");
		
		sql.JOIN("PYS_TIPOSPRODUCTOS tp ON pp.IDTIPOPRODUCTO = tp.IDTIPOPRODUCTO");
		
		sql.WHERE("pp.IDINSTITUCION = '" + institucion + "'");
		//pp.FECHABAJA IS NULL te devuelve los no eliminados (los que no tienen historico) 
		//sql.WHERE("pp.FECHABAJA IS NULL");
		
		sql.ORDER_BY("pp.IDTIPOPRODUCTO");
		sql.ORDER_BY("pp.IDPRODUCTO");
		sql.ORDER_BY("pp.IDINSTITUCION");

		return sql.toString();
	}

}
