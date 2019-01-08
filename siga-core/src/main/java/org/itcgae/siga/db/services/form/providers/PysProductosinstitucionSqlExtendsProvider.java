package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysProductosinstitucionSqlProvider;

public class PysProductosinstitucionSqlExtendsProvider extends PysProductosinstitucionSqlProvider {

	
	public String selectTypesCertificatesCourse(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("pys.idTipoProducto");
		sql.SELECT("pys.idProducto");
		sql.SELECT("pys.idProducto as value");
		sql.SELECT("pys.idProductoInstitucion");
		sql.SELECT("pys.descripcion");
		sql.SELECT("pys.descripcion as label");
		sql.SELECT("pys.valor");

		sql.FROM("PYS_PRODUCTOSINSTITUCION pys");
		
		sql.WHERE("pys.idInstitucion =" + idInstitucion);
		sql.WHERE("pys.TIPOCERTIFICADO = 'C'");
		sql.WHERE("pys.FECHABAJA IS NULL");
		
		return sql.toString();
	}

}
