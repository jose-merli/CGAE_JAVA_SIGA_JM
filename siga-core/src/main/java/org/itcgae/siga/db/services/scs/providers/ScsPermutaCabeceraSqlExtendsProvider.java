package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsPermutaCabeceraSqlProvider;

public class ScsPermutaCabeceraSqlExtendsProvider extends ScsPermutaCabeceraSqlProvider{
	public String maxIdPermutaCabecera(String idinstitucion){
		
		SQL sql = new SQL();
		
		sql.SELECT("max(id_permuta_cabecera) + 1");
		sql.FROM("SCS_PERMUTA_CABECERA");
		sql.WHERE("idinstitucion = " + idinstitucion);
		
		return sql.toString();
		 
	 }
}
