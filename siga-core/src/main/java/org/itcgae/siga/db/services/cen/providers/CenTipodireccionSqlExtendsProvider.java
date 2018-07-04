package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTipodireccionSqlProvider;

public class CenTipodireccionSqlExtendsProvider extends CenTipodireccionSqlProvider{

	
	public String selectTipoDireccion(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TIPODIRECCION.IDTIPODIRECCION");
		sql.SELECT("REC.DESCRIPCION");
		sql.FROM("CEN_TIPODIRECCION TIPODIRECCION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = TIPODIRECCION.DESCRIPCION " + 
				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.ORDER_BY("REC.DESCRIPCION");
		return sql.toString();
	}
	
}
