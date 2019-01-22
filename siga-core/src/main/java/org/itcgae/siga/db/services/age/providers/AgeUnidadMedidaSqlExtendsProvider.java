package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeUnidadmedidaSqlProvider;

public class AgeUnidadMedidaSqlExtendsProvider extends  AgeUnidadmedidaSqlProvider{

		
	public String getMeasuredUnit(String idLenguaje) {

	SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("uni.IDUNIDADMEDIDA");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("AGE_UNIDADMEDIDA uni");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = uni.DESCRIPCION " + 
				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.ORDER_BY("rec.DESCRIPCION");
		return sql.toString();
	}
	
}
