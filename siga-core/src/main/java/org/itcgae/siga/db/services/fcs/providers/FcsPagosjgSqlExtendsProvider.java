package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsPagosjgSqlProvider;

public class FcsPagosjgSqlExtendsProvider extends FcsPagosjgSqlProvider{

	public String comboPagosColegio(String idLenguaje, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("p.idpagosjg AS id");
		sql.SELECT("to_char(e.fechaestado, 'dd/mm/yy') || ' - ' || p.nombre || ' (' || to_char(p.fechadesde, 'dd/mm/yy') || '-' || to_char(p.fechahasta, 'dd/mm/yy') || ')' AS descripcion");
		sql.FROM("fcs_pagosjg p, fcs_pagos_estadospagos e");
		sql.WHERE("p.idinstitucion = '" + idInstitucion + "'");
		
		SQL sql1 = new SQL();
		sql1.SELECT("MAX(es.fechaestado)");
		sql1.FROM("fcs_pagos_estadospagos es");
		sql1.WHERE("es.idpagosjg = p.idpagosjg");
		sql1.WHERE("es.idinstitucion = p.idinstitucion");
		
		sql.WHERE("e.fechaestado = (" + sql1 + ")");
		
		SQL sql2 = new SQL();
		sql2.SELECT("estado.idestadopagosjg");
		sql2.FROM("fcs_pagos_estadospagos estado");
		sql2.WHERE("estado.fechaestado = e.fechaestado");
		sql2.WHERE("estado.idinstitucion = e.idinstitucion");
		sql2.WHERE("estado.idpagosjg = e.idpagosjg");

		sql.WHERE("(" + sql2 + ") >= 30");
		
		sql.WHERE("p.idinstitucion = e.idinstitucion");
		sql.WHERE("p.idpagosjg = e.idpagosjg");
		
		sql.ORDER_BY("e.fechaestado desc");
		return sql.toString();
	}
}

