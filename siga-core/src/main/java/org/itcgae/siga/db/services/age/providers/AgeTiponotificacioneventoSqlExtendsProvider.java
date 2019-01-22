package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeTiponotificacioneventoSqlProvider;

public class AgeTiponotificacioneventoSqlExtendsProvider extends  AgeTiponotificacioneventoSqlProvider{

	
	public String getTypeNotifications(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tiponoti.IDTIPONOTIFICACIONEVENTO");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("AGE_TIPONOTIFICACIONEVENTO tiponoti");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = tiponoti.DESCRIPCION " + 
				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.ORDER_BY("rec.DESCRIPCION");
		return sql.toString();
	}
	
}
