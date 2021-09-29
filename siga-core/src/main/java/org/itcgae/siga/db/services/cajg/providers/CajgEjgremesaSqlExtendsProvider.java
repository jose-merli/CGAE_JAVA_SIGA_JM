package org.itcgae.siga.db.services.cajg.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CajgEjgremesaSqlProvider;
import org.itcgae.siga.db.mappers.CajgRemesaSqlProvider;

public class CajgEjgremesaSqlExtendsProvider extends CajgEjgremesaSqlProvider{
	
	public String getIdEjgRemesa() {
		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(IDEJGREMESA)+1 ,1) ");
		sql.FROM("CAJG_EJGREMESA");

		return sql.toString();
	}
	
	public String getNumeroIntercambio(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(NUMEROINTERCAMBIO)+1 ,1) ");
		sql.FROM("CAJG_EJGREMESA");
		sql.WHERE("IDINSTITUCION = "+ idInstitucion);

		return sql.toString();
	}

}
