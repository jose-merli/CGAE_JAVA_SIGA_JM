package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.db.mappers.ScsAcreditacionSqlProvider;

public class ScsAcreditacionSqlExtendsProvider extends ScsAcreditacionSqlProvider{

	public String searchAcreditaciones(ModulosItem modulosItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT("acreditacion.idacreditacion");
		sql.SELECT("acreditacion.descripcion");
		sql.SELECT("acre.codigoext");
		sql.SELECT("acre.nig_numprocedimiento");
		sql.SELECT("acre.porcentaje");
		sql.SELECT("acre.codsubtarifa");
		sql.SELECT("acre.idinstitucion");

		sql.FROM("SCS_ACREDITACION acreditacion");
		sql.INNER_JOIN("SCS_ACREDITACIONPROCEDIMIENTO acre on acre.idacreditacion = acreditacion.idacreditacion");
		
		sql.WHERE("idprocedimiento = '" + modulosItem.getIdProcedimiento() + "'");
		sql.WHERE("idInstitucion = '" + modulosItem.getidInstitucion() + "'");

		sql.ORDER_BY("descripcion");
	
		return sql.toString();
	}
	
	public String getAcreditaciones(String idInstitucion, String idProcedimiento) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("ACREDITACION.IDACREDITACION");
		sql.SELECT_DISTINCT("ACREDITACION.DESCRIPCION");
		if(idProcedimiento != null) {
			sql.WHERE("ACREDITACION.IDACREDITACION NOT IN (SELECT IDACREDITACION FROM SCS_ACREDITACIONPROCEDIMIENTO ACRE WHERE IDPROCEDIMIENTO = "+ idProcedimiento +" AND IDINSTITUCION = " + idInstitucion + ")");
		}
		sql.FROM("SCS_ACREDITACION ACREDITACION");
		
		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}

	public String getIDAcreditaciones(String idInstitucion, String idFacturacion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("ACREDITACION.IDACREDITACION");
		if(idFacturacion != null) {
			sql.WHERE("ACREDITACION.IDACREDITACION  WHERE IDFACTURACION = "+ idFacturacion +" AND IDINSTITUCION = " + idInstitucion);
		}
		sql.FROM("SCS_ACREDITACION ACREDITACION");

		sql.ORDER_BY("IDACREDITACION");

		return sql.toString();
	}
	
}
