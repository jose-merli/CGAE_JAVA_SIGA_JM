package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenEstadocolegialSqlProvider;

public class CenEstadocolegialSqlExtendsProvider extends CenEstadocolegialSqlProvider{

	public String distinctSituacionColegial(String idLenguaje) {
		
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("EST.IDESTADO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_ESTADOCOLEGIAL EST");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON EST.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
	
	public String getSituacionGlobalColegiado(String idPersona) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		//Subconsulta para traer la última fecha
		sql2.SELECT("MAX(FECHAESTADO)");
		sql2.FROM("CEN_DATOSCOLEGIALESESTADO");
		sql2.WHERE("IDPERSONA = '" + idPersona + "'");
		sql2.GROUP_BY("IDINSTITUCION ,IDPERSONA");
		
		sql.SELECT("IDESTADO");
		sql.SELECT("IDINSTITUCION");
		sql.FROM("CEN_DATOSCOLEGIALESESTADO");
		sql.WHERE("IDPERSONA = '" + idPersona + "'");
		sql.WHERE("FECHAESTADO IN (" + sql2 + ")");

		return sql.toString();
	}
}
