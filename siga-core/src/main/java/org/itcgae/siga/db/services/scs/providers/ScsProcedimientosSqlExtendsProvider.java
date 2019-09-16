package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsProcedimientosSqlProvider;

public class ScsProcedimientosSqlExtendsProvider extends ScsProcedimientosSqlProvider{

	public String searchProcess(String idLenguaje, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("proc.idinstitucion");
		sql.SELECT("proc.idprocedimiento");
		sql.SELECT("proc.nombre");
		sql.SELECT("proc.codigo");
		sql.SELECT("proc.precio as importe");
		sql.SELECT("juris.descripcion as jurisdiccion");

		sql.FROM("SCS_PROCEDIMIENTOS proc");
		sql.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '" + idLenguaje + "')");;
		
		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");
		
		sql.ORDER_BY("proc.nombre");
	
		return sql.toString();
	}

}
