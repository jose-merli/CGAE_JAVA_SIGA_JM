package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.db.entities.ScsTipofundamentos;

public class ScsTipofundamentosSqlExtendsProvider extends ScsTipofundamentos{

	public String searchFundamentosResolucion(FundamentoResolucionItem fundamentoResolucionItem, String idLenguaje, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("fundamento.idfundamento");
		sql.SELECT("fundamento.codigo");
		sql.SELECT("NVL(catalogoFundamento.descripcion, fundamento.descripcion) as descripcion");
		sql.SELECT("fundamento.idinstitucion");
		sql.SELECT("fundamento.codigoejis");
		sql.SELECT("fundamento.textoplantilla");
		sql.SELECT("fundamento.TEXTOPLANTILLA2");
		sql.SELECT("fundamento.TEXTOPLANTILLA3");
		sql.SELECT("fundamento.TEXTOPLANTILLA4");
		sql.SELECT("NVL(catalogoResolucion.descripcion, tiporesolucion.descripcion) as descripcionresolucion");
		sql.SELECT("fundamento.fechabaja");
		sql.SELECT("fundamento.IDTIPORESOLUCION");
		sql.SELECT("catalogoFundamento.idrecurso as recursofundamento");

		sql.FROM("SCS_TIPOFUNDAMENTOS fundamento");
		
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoFundamento on catalogoFundamento.idrecurso = fundamento.DESCRIPCION and catalogoFundamento.idlenguaje = '" + idLenguaje + "'");
		sql.LEFT_OUTER_JOIN("SCS_TIPORESOLUCION tiporesolucion on fundamento.IDTIPORESOLUCION = tiporesolucion.IDTIPORESOLUCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoResolucion on catalogoResolucion.idrecurso = tiporesolucion.DESCRIPCION and catalogoResolucion.idlenguaje = '" + idLenguaje + "'");
		
		sql.WHERE("fundamento.idinstitucion = '" + idInstitucion + "'");
		
		if(fundamentoResolucionItem.getCodigoExt() != null && fundamentoResolucionItem.getCodigoExt() != "") {
			sql.WHERE("UPPER(fundamento.codigo) like UPPER('%"+ fundamentoResolucionItem.getCodigoExt() + "%')");
		}
		
		if(fundamentoResolucionItem.getDescripcionFundamento() != null && fundamentoResolucionItem.getDescripcionFundamento() != "") {
			sql.WHERE("UPPER(catalogoFundamento.descripcion) like UPPER('%"+ fundamentoResolucionItem.getDescripcionFundamento() + "%')");
		}
		
		if(!fundamentoResolucionItem.isHistorico()) {
			sql.WHERE("fundamento.fechabaja is null");
		}
		
		sql.ORDER_BY("catalogoFundamento.descripcion");
	
		return sql.toString();
	}
	
	public String getIdFundamentoResolucion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDFUNDAMENTO) AS IDFUNDAMENTO");
		sql.FROM("SCS_TIPOFUNDAMENTOS");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
	public String comboFundamentoJurid(String idLenguaje, String idInstitucion, String resolucion) {
		SQL sql = new SQL();
		
		sql.SELECT("fundamento.idfundamento");
		sql.SELECT("F_SIGA_GETRECURSO( fundamento.DESCRIPCION,1) descripcion");
//		sql.SELECT("fundamento.IDTIPORESOLUCION");
		sql.FROM("SCS_TIPOFUNDAMENTOS fundamento");
		sql.WHERE("fundamento.fecha_baja is null AND fundamento.IDINSTITUCION =" + idInstitucion);
		if(resolucion != null && !resolucion.isEmpty()) {
			sql.WHERE("fundamento.IDTIPORESOLUCION =" +resolucion);
		}
		//Añadido para que no salte un error cuando se intenta arreglar la tilde de un nulo
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}
	
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
