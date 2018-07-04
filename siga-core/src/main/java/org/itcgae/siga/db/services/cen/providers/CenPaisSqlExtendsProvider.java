package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenPaisSqlProvider;

public class CenPaisSqlExtendsProvider extends CenPaisSqlProvider{

	
	public String selectPais(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("PAIS.IDPAIS");
		sql.SELECT("REC.DESCRIPCION");
		sql.FROM("CEN_PAIS PAIS");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = PAIS.NOMBRE " + 
				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.ORDER_BY("REC.DESCRIPCION");
		return sql.toString();
	}
	
}
