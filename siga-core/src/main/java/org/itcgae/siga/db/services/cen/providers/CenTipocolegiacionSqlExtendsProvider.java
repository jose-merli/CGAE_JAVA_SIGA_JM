package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenTipocolegiacionSqlExtendsProvider {
	
	
	public String selectTipoColegiacion(String idLenguage, String isEXEA){
		
		SQL sql = new SQL();
		sql.SELECT("E.IDTIPOCOLEGIACION AS VALUE");
		sql.SELECT("INITCAP(F_SIGA_GETRECURSO(DESCRIPCION," + idLenguage + ")) AS LABEL");
		sql.FROM("CEN_TIPOCOLEGIACION E");
		sql.WHERE("EXEA ='"+isEXEA+"'");
		sql.ORDER_BY("LABEL");
		
		return sql.toString();
	}

}