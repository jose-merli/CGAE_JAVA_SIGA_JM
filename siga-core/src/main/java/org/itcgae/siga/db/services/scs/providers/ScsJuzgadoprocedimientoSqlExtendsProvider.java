package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsJuzgadoprocedimientoSqlProvider;

public class ScsJuzgadoprocedimientoSqlExtendsProvider extends ScsJuzgadoprocedimientoSqlProvider{

	public String searchProcJudged(String idLenguaje, Short idInstitucion, String idJuzgado, String historico) {
		
		SQL sql = new SQL();
		
		sql.SELECT("proc.idinstitucion");
		sql.SELECT("proc.idprocedimiento");
		sql.SELECT("proc.nombre");
		sql.SELECT("proc.codigo");
		sql.SELECT("proc.precio as importe");
		sql.SELECT("juris.descripcion as jurisdiccion");
		sql.SELECT("proc.fechadesdevigor");
		sql.SELECT("proc.fechahastavigor");

		sql.FROM("SCS_JUZGADOPROCEDIMIENTO procedimiento");
		sql.INNER_JOIN("SCS_PROCEDIMIENTOS proc on proc.idprocedimiento = procedimiento.idprocedimiento and procedimiento.idinstitucion = proc.idinstitucion");
		sql.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '" + idLenguaje + "')");;
		
		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");
		
		if(historico.equals("false")) {
			sql.WHERE("(proc.fechadesdevigor <= sysdate AND (proc.FECHAHASTAVIGOR > sysdate OR proc.fechahastavigor is null))");
		}
		
		sql.WHERE("procedimiento.idjuzgado = '" + idJuzgado + "'");

		sql.ORDER_BY("proc.nombre");
	
		return sql.toString();
	}
	
	public String getIdProcedimiento(Short idInstitucion, String idJuzgado) {
		SQL sql = new SQL();

		sql.SELECT("MAX(idprocedimiento) AS idprocedimiento");
		sql.FROM("SCS_JUZGADOPROCEDIMIENTO");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("idjuzgado = '"+ idJuzgado +"'");

		return sql.toString();
	}
	
}
