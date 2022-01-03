package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EcomColaExtendsSqlProvider {
	
public String getNewId (){
		
		SQL sql = new SQL();
		
		sql.SELECT("SEQ_ECOM_COLA.NEXTVAL as idmax");
		
		sql.FROM("DUAL");
		
		return sql.toString();
	}

}
