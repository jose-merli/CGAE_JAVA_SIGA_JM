package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsParentescoSqlProvider;

public class ScsParentescoSqlExtendsProvider extends ScsParentescoSqlProvider {
	
	public String getParentesco(Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("PARENTESCO.IDPARENTESCO AS IDGRUPO");
		sql.SELECT("catalogoPARENTESCO.descripcion");

		sql.FROM("SCS_PARENTESCO PARENTESCO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogoPARENTESCO on catalogoPARENTESCO.idrecurso = PARENTESCO.DESCRIPCION and catalogoPARENTESCO.idlenguaje = "+idLenguaje);

		//sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("PARENTESCO.fecha_baja is null");
		sql.WHERE("PARENTESCO.idinstitucion = "+idInstitucion);

		sql.ORDER_BY("catalogoPARENTESCO.descripcion");

		return sql.toString();
	}

}
