package org.itcgae.siga.db.services.cajg.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CajgRemesaSqlProvider;

public class CajgRemesaSqlExtendsProvider extends CajgRemesaSqlProvider{

	public String comboRemesa(Short idInstitucion) {

		SQL sql = new SQL();
	 
		sql.SELECT_DISTINCT("REMESA.DESCRIPCION");
		sql.SELECT_DISTINCT("REMESA.IDREMESA");
		sql.FROM("CAJG_REMESA REMESA");
		sql.INNER_JOIN("CAJG_REMESAESTADOS ESTADOS ON ESTADOS.IDREMESA = REMESA.IDREMESA "
				+ "AND ESTADOS.IDINSTITUCION = REMESA.IDINSTITUCION");
		sql.WHERE("REMESA.idinstitucion = "+ idInstitucion+ " AND ESTADOS.IDESTADO IN (1,2)");
		//HAY QUE TENER EN CUENTA E INVESTIGAR LAS REMESAS QUE PERMITEN LA INCLUSION
		//DE EJGS
	
		return sql.toString();
	}
}
