package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsDelitoSqlProvider;

public class ScsDelitoSqlExtendsProvider extends ScsDelitoSqlProvider{
	
	public String comboDelitos(String idLenguaje, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("delito.IDdelito");
		sql.SELECT("catalogodelito.DESCRIPCION");

		sql.FROM("scs_delito delito");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogodelito on catalogodelito.idrecurso = delito.DESCRIPCION and catalogodelito.idlenguaje ="+idLenguaje);
		sql.WHERE("delito.fechabaja is null");
		sql.WHERE("delito.idinstitucion ='"+String.valueOf(idInstitucion)+"'");
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}

}
