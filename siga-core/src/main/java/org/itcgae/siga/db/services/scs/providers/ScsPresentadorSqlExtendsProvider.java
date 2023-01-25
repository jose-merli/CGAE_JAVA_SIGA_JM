package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsPresentadorSqlProvider;

public class ScsPresentadorSqlExtendsProvider extends ScsPresentadorSqlProvider{
	
	public String comboPresentadores(String idLenguaje, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("presentador.IDpresentador");
		sql.SELECT("NVL(catalogopresentador.DESCRIPCION,presentador.DESCRIPCION) AS DESCRIPCION ");

		sql.FROM("scs_presentador presentador");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogopresentador on catalogopresentador.idrecurso = presentador.DESCRIPCION and catalogopresentador.idlenguaje ="+idLenguaje);
		sql.WHERE("presentador.fechabaja is null");
		sql.WHERE("presentador.idinstitucion ='"+String.valueOf(idInstitucion)+"'");
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}

}
