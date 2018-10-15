package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.mappers.CenDatoscolegialesestadoSqlProvider;

public class CenDatoscolegialesestadoSqlExtendsProvider extends CenDatoscolegialesestadoSqlProvider{
	
	public String insertColegiado(CenDatoscolegialesestado cenDatoscolegialesestado) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_DATOSCOLEGIALESESTADO");
		
		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");

		sql.VALUES("IDINSTITUCION",  "'" +cenDatoscolegialesestado.getIdinstitucion()+"'");
					
		if (cenDatoscolegialesestado.getFechaestado() != null) {
			sql.VALUES("FECHAESTADO",  "'" +cenDatoscolegialesestado.getFechaestado()+"'");
		}
		if (cenDatoscolegialesestado.getIdestado() != null) {
			sql.VALUES("IDESTADO", "'" + cenDatoscolegialesestado.getIdestado() +"'");
		}
		if (cenDatoscolegialesestado.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "'" + cenDatoscolegialesestado.getFechamodificacion() +"'");
		}
		if (cenDatoscolegialesestado.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "'" + cenDatoscolegialesestado.getUsumodificacion() +"'");
		}
		if (cenDatoscolegialesestado.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "'" +cenDatoscolegialesestado.getObservaciones() +"'");
		}
		return sql.toString();
	}
	

}
