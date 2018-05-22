package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTiposociedadSqlProvider;

public class CenTiposociedadSqlExtendsProvider extends CenTiposociedadSqlProvider{

	public String getSocietyTypes(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT CENT.LETRACIF");
		sql.SELECT("GENR.DESCRIPCION");
		sql.FROM(" cen_tiposociedad CENT");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS GENR on CENT.DESCRIPCION = GENR.IDRECURSO");
		sql.WHERE(" GENR.IDLENGUAJE = '" + idLenguaje +"'");
		
		return sql.toString();
	}
	
	
}
