package org.itcgae.siga.db.services.cajg.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CajgRemesaSqlProvider;

public class CajgRemesaSqlExtendsProvider extends CajgRemesaSqlProvider{

	public String comboRemesa(Short idInstitucion) {

		SQL sql = new SQL();
	 		
		
		sql.SELECT_DISTINCT("CASE WHEN REMESA.PREFIJO IS NOT NULL THEN REMESA.PREFIJO || '/' ELSE '' END || REMESA.NUMERO || CASE WHEN REMESA.SUFIJO IS NOT NULL THEN '/' || REMESA.SUFIJO ELSE '' END || ' - ' || REMESA.DESCRIPCION || ' (' || TO_CHAR(ESTADOS.FECHAREMESA, 'dd-MM-yyyy') || ')' AS DESCRIPCION");
		sql.SELECT_DISTINCT("REMESA.IDREMESA");
		sql.SELECT_DISTINCT("REMESA.NUMERO");
		sql.SELECT_DISTINCT("ESTADOS.FECHAREMESA");
		sql.FROM("CAJG_REMESA REMESA");
		sql.INNER_JOIN("CAJG_REMESAESTADOS ESTADOS ON ESTADOS.IDREMESA = REMESA.IDREMESA AND ESTADOS.IDINSTITUCION = REMESA.IDINSTITUCION");
		sql.WHERE("REMESA.idinstitucion = "+ idInstitucion+ " AND ESTADOS.IDESTADO IN (0)");
		sql.ORDER_BY("ESTADOS.FECHAREMESA DESC, DESCRIPCION DESC");
		
		return sql.toString();
	}
}
