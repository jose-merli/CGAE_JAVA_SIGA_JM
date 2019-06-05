package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConClaseComunicacionesExtendsSqlProvider {
	
	public String selectClaseComunicaciones (String idLenguaje){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDCLASECOMUNICACION");
		sql.SELECT("CAT.DESCRIPCION");
		
		sql.FROM("MOD_CLASECOMUNICACIONES COM");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON COM.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
	
	public String selectClaseComunicacionesByModulo(String idModulo){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDCLASECOMUNICACION");
		sql.SELECT("NOMBRE");
		
		sql.FROM("MOD_CLASECOMUNICACIONES");
		
		sql.WHERE("IDMODULO = '" + idModulo + "' OR IDMODULO IS NULL");
		
		return sql.toString();
	}
}
