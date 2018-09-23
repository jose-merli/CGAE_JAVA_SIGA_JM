package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenColegiadoSqlProvider;

public class CenColegiadoSqlExtendsProvider extends CenColegiadoSqlProvider {

public String selectDatosColegiales(String idPersona, String idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("CEN.NCOLEGIADO");
		sql.SELECT("SEG.NOMBRE");
		sql.SELECT("CEN.NMUTUALISTA");
		sql.SELECT("CEN.FECHAINCORPORACION");
		sql.SELECT("CEN.FECHAPRESENTACION");
		sql.SELECT("CEN.FECHAJURA");
		sql.SELECT("CEN.FECHATITULACION");
		sql.SELECT("CASE WHEN CEN.SITUACIONRESIDENTE = 1 THEN 'Si' ELSE 'No' END AS SITUACIONRESIDENTE");
		sql.SELECT("CASE WHEN CEN.COMUNITARIO = 1 THEN 'Si' ELSE 'No' END AS COMUNITARIO");
		sql.SELECT("EST.DESCRIPCION");
		sql.SELECT("DAT.OBSERVACIONES");
		
		sql.FROM("CEN_COLEGIADO CEN");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSSEGURO SEG ON SEG.IDTIPOSSEGURO = CEN.IDTIPOSSEGURO");
		sql.INNER_JOIN("CEN_DATOSCOLEGIALESESTADO DAT ON (DAT.IDPERSONA = CEN.IDPERSONA AND DAT.IDINSTITUCION = CEN.IDINSTITUCION)");
		sql.INNER_JOIN("CEN_ESTADOCOLEGIAL EST ON EST.IDESTADO = DAT.IDESTADO");
	
		sql.WHERE("CEN.IDPERSONA = '" + idPersona + "'");
		sql.WHERE("CEN.IDINSTITUCION = '" + idInstitucion + "'");
		
		return sql.toString();
	}
}
