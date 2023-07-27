package org.itcgae.siga.db.services.cajg.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CajgRemesaSqlProvider;

public class CajgRemesaSqlExtendsProvider extends CajgRemesaSqlProvider{

	public String comboRemesa(Short idInstitucion) {

		SQL sql = new SQL();	
		
		sql.SELECT("TRIM(REMESA.PREFIJO)|| REMESA.NUMERO || TRIM(REMESA.SUFIJO)||' - ' || REMESA.DESCRIPCION || ' (' || TO_CHAR((SELECT MIN(FECHAREMESA) FROM CAJG_REMESAESTADOS ESTADOS WHERE ESTADOS.IDREMESA = REMESA.IDREMESA  AND ESTADOS.IDINSTITUCION = REMESA.IDINSTITUCION AND ESTADOS.IDESTADO = 0),'DD-MM-YYYY') || ')' AS DESCRIPCION");
		sql.SELECT("REMESA.IDREMESA");
		sql.SELECT("REMESA.NUMERO");
		sql.SELECT("REMESA.IDREMESA");
		sql.FROM("CAJG_REMESA REMESA");    
		sql.WHERE("REMESA.IDINSTITUCION = "+ idInstitucion+ " AND (SELECT COUNT(1) FROM CAJG_REMESAESTADOS ESTADOS  WHERE ESTADOS.IDREMESA = REMESA.IDREMESA AND ESTADOS.IDINSTITUCION = REMESA.IDINSTITUCION)<2  AND  REMESA.IDTIPOREMESA = 0");    
		sql.ORDER_BY("REMESA.IDREMESA DESC");
		
		
		return sql.toString();
	}
}
