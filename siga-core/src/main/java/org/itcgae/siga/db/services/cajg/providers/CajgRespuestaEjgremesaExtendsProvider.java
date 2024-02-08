package org.itcgae.siga.db.services.cajg.providers;

import org.apache.ibatis.jdbc.SQL;

public class CajgRespuestaEjgremesaExtendsProvider {

	public String deleteConSelect(Long idRemesa, Short idInstitucion) {

		SQL sql = new SQL();
		SQL sql2 =new SQL();
		
		sql2.SELECT("idejgremesa");
		sql2.FROM("cajg_ejgremesa rem");
		sql2.WHERE("rem.idremesa = " + idRemesa);
		sql2.WHERE("rem.idinstitucion = " + idInstitucion);
	 
		sql.DELETE_FROM("CAJG_RESPUESTA_EJGREMESA resp");
		sql.WHERE("idejgremesa  in ("+ sql2.toString()+ ")");
	
		return sql.toString();
	}
	
	public String deleteByEJGRemesa(Long idejgremesa) {

		SQL sql = new SQL();
 
		sql.DELETE_FROM("CAJG_RESPUESTA_EJGREMESA resp");
		sql.WHERE("idejgremesa = "+ idejgremesa);
	
		return sql.toString();
	}
		
}
