package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysProductossolicitadosSqlProvider;

public class PysProductossolicitadosSqlExtendsProvider extends PysProductossolicitadosSqlProvider {

	
	public String selectMaxIdPeticion(Short idInstitucion, Short idTipoProducto, Long idProducto, Long idProductoInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(idpeticion)+1,1) as IDPETICION1");
		sql.FROM("PYS_PRODUCTOSSOLICITADOS");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion + "'");
		sql.WHERE("IDTIPOPRODUCTO = '"+ idTipoProducto + "'");
		sql.WHERE("IDPRODUCTO = '"+ idProducto + "'");
		sql.WHERE("IDPRODUCTOINSTITUCION = '"+ idProductoInstitucion + "'");
		return sql.toString();
	}

}
