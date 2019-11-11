package org.redabogacia.ecom.db.services.ecom.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.mappers.EcomColaSqlProvider;

public class EcomColaExtendsSqlProvider extends EcomColaSqlProvider {
	
	private static String OPERACION_ACTIVA = "2";
	private static String SERVICIO_ACTIVO = "1";
	
	public String getPendientes(EcomColaExample example, int horasEnEjecucion) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("C.IDECOMCOLA");
		} else {
			sql.SELECT("C.IDECOMCOLA");
		}
		sql.SELECT("C.IDINSTITUCION");
		sql.SELECT("C.IDESTADOCOLA");
		sql.SELECT("C.IDOPERACION");
		sql.SELECT("C.REINTENTO");
		sql.SELECT("C.FECHACREACION");
		sql.SELECT("C.FECHAMODIFICACION");
		sql.SELECT("C.USUMODIFICACION");
		sql.SELECT("C.FECHAEJECUCION");
		sql.FROM(" ECOM_COLA C");
		sql.JOIN(" ECOM_OPERACION O ON O.IDOPERACION = C.IDOPERACION");
		sql.JOIN(" ECOM_SERVICIO S ON S.IDSERVICIO = O.IDSERVICIO");
		sql.WHERE(" O.ACTIVO = '" + OPERACION_ACTIVA + "'");
		sql.WHERE(" S.ACTIVO = '" + SERVICIO_ACTIVO + "'");
		sql.WHERE(" (C.FECHAEJECUCION < SYSDATE OR C.FECHAEJECUCION IS NULL)");
		sql.WHERE(" (C.IDESTADOCOLA IN (" + SigaConstants.ECOM_ESTADOSCOLA.INICIAL.getId() + ", " + SigaConstants.ECOM_ESTADOSCOLA.REINTENTANDO.getId() + ")"
				+ " OR (C.IDESTADOCOLA = " + SigaConstants.ECOM_ESTADOSCOLA.EJECUTANDOSE.getId() + " AND C.FECHAMODIFICACION < SYSDATE - " + horasEnEjecucion + "/24))");
		
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		sql.ORDER_BY("C.IDESTADOCOLA DESC");
		return sql.toString();
	}
}
