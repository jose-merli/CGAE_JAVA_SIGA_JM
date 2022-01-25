package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.mappers.GenFicheroSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;

public class GenFicheroSqlExtendsProvider extends GenFicheroSqlProvider{

	
	public String selectMaxIdFichero() {
		
		SQL sql = new SQL();
		
		sql.SELECT("max(IDFICHERO) as IDFICHERO1");
		sql.SELECT("max(IDFICHERO) as IDFICHERO2");
		sql.FROM("GEN_FICHERO");
		
		return sql.toString();
	}
	
	public String nextIdGenFichero() {
		return "select SEQ_GENFICHERO.Nextval as IDFICHERO from dual";
	}

	public String selectMaxIdFicheroByIdInstitucion(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("max(IDFICHERO) as IDFICHERO");
		sql.FROM("GEN_FICHERO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
}
