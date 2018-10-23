package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeUnidadmedidaSqlProvider;

public class AgeUnidadMedidaSqlExtendsProvider extends  AgeUnidadmedidaSqlProvider{

		
	public String getMeasuredUnit(String idLenguaje) {

	SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("IDUNIDADMEDIDA");
		sql.SELECT("DESCRIPCION");
		sql.FROM("AGE_UNIDADMEDIDA");
//		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = tipocalendario.DESCRIPCION " + 
//				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')"); Añadir cuando guardemos la traducción
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}
	
}
