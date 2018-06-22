package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenTipoidentificacionSqlExtendsProvider {

	
	public String getIdentificationTypes(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TIPO.IDTIPOIDENTIFICACION");
		sql.SELECT_DISTINCT("GEN.DESCRIPCION");
		sql.FROM("CEN_TIPOIDENTIFICACION TIPO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GEN on GEN.IDRECURSO = TIPO.DESCRIPCION");
		
		sql.WHERE("GEN.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("GEN.DESCRIPCION");
		
		return sql.toString();
	}
}
