package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsParentescoSqlProvider;

public class ScsParentescoSqlExtendsProvider extends ScsParentescoSqlProvider {
	
	public String getParentesco(Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("PARENTESCO.IDPARENTESCO AS IDGRUPO");
		sql.SELECT("f_siga_getrecurso(PARENTESCO.descripcion,"+idLenguaje+") DESCRIPCION");
		
		sql.FROM("SCS_PARENTESCO PARENTESCO");
		
		//sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("PARENTESCO.fecha_baja is null");
		sql.WHERE("PARENTESCO.idinstitucion = "+idInstitucion);

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}

}
