package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysTipoivaSqlProvider;

public class PySTipoIvaSqlExtendsProvider extends PysTipoivaSqlProvider{
	
	public String comboIva(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOIVA AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_TIPOIVA");
		
		sql.ORDER_BY("NLSSORT(DESCRIPCION, 'NLS_SORT=BINARY')");
		
		return sql.toString();
	}
	
	public String comboIvaNoDerogados(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOIVA AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_TIPOIVA");
		
		sql.WHERE("FECHABAJA IS NULL");
		
		sql.ORDER_BY("NLSSORT(DESCRIPCION, 'NLS_SORT=BINARY')");
		
		return sql.toString();
	}

	public String comboTiposIVA(String idioma) {
		SQL sql = new SQL();

		sql.SELECT("p.idtipoiva");
		sql.SELECT("f_siga_getrecurso (p.descripcion,'" + idioma + "') AS DESCRIPCION");

		sql.FROM("pys_tipoiva p");

		sql.WHERE("p.idtipoiva NOT IN ( SELECT p2.idtipoiva FROM pys_tipoiva p2 WHERE upper(p2.descripcion) LIKE '%DEROGADO%')");

		return sql.toString();
	}
	
}
