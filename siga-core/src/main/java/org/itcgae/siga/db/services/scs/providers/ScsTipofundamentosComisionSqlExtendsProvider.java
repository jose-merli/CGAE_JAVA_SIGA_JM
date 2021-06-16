package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.db.entities.ScsTipofundamentos;

public class ScsTipofundamentosComisionSqlExtendsProvider extends ScsTipofundamentos{
	
	public String comboFundamentoJuridComision(String idLenguaje, String idInstitucion, String resolucion) {
		SQL sql = new SQL();
		
		sql.SELECT("fundamento.idfundamento");
		sql.SELECT("catalogoFundamento.descripcion");
//		sql.SELECT("fundamento.IDTIPORESOLUCION");
		sql.FROM("SCS_TIPOFUNDAMENTOS fundamento");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoFundamento on catalogoFundamento.idrecurso = fundamento.DESCRIPCION and catalogoFundamento.idlenguaje ="+idLenguaje);
		sql.WHERE("fundamento.fecha_baja is null AND fundamento.IDINSTITUCION =" + idInstitucion + " AND fundamento.IDTIPORESOLUCION =" +resolucion);
		sql.ORDER_BY("catalogoFundamento.descripcion");
		return sql.toString();
	}
	
}
