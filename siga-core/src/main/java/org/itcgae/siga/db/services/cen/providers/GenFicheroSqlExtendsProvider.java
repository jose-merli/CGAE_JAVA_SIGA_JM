package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.GenFicheroSqlProvider;

public class GenFicheroSqlExtendsProvider extends GenFicheroSqlProvider{

	
	public String selectMaxIdFichero() {
		
		SQL sql = new SQL();
		
		sql.SELECT("max(IDFICHERO) as IDFICHERO1");
		sql.SELECT("max(IDFICHERO) as IDFICHERO2");
		sql.FROM("GEN_FICHERO");
		
		return sql.toString();
	}
}
