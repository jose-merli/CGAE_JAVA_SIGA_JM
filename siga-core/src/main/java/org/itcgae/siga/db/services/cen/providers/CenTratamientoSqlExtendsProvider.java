package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTipodireccionSqlProvider;
import org.itcgae.siga.db.mappers.CenTratamientoSqlProvider;

public class CenTratamientoSqlExtendsProvider extends CenTratamientoSqlProvider{

	
	public String selectTratamiento() {
		
		SQL sql = new SQL();
		
		sql.SELECT("IDTRATAMIENTO AS VALUE");
		sql.SELECT("f_siga_getrecurso(DESCRIPCION,1) AS LABEL");
		sql.FROM("CEN_TRATAMIENTO");
//		sql.INNER_JOIN("CEN_TRATAMIENTO TRA ON (REC.IDRECURSO = TIPODIRECCION.DESCRIPCION " + 
//				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.ORDER_BY("LABEL");
		return sql.toString();
	}
	
}
