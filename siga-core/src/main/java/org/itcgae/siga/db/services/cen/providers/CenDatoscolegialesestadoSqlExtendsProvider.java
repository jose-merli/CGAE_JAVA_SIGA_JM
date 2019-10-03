package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.mappers.CenDatoscolegialesestadoSqlProvider;

public class CenDatoscolegialesestadoSqlExtendsProvider extends CenDatoscolegialesestadoSqlProvider {

	public String insertColegiado(CenDatoscolegialesestado cenDatoscolegialesestado) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_DATOSCOLEGIALESESTADO");

		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona  where idpersona like '"
				+ cenDatoscolegialesestado.getIdinstitucion() + "' || '%'  )");

		sql.VALUES("IDINSTITUCION", "'" + cenDatoscolegialesestado.getIdinstitucion() + "'");

		if (cenDatoscolegialesestado.getFechaestado() != null) {
			sql.VALUES("FECHAESTADO", "'" + cenDatoscolegialesestado.getFechaestado() + "'");
		}
		if (cenDatoscolegialesestado.getIdestado() != null) {
			sql.VALUES("IDESTADO", "'" + cenDatoscolegialesestado.getIdestado() + "'");
		}
		if (cenDatoscolegialesestado.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "'" + cenDatoscolegialesestado.getFechamodificacion() + "'");
		}
		if (cenDatoscolegialesestado.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "'" + cenDatoscolegialesestado.getUsumodificacion() + "'");
		}
		if (cenDatoscolegialesestado.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "'" + cenDatoscolegialesestado.getObservaciones().replace("'", "''") + "'");
		}
		return sql.toString();
	}

	public String updateEstadoColegial(CenDatoscolegialesestado record, Date fechaEstadoNueva) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SQL sql = new SQL();
		sql.UPDATE("CEN_DATOSCOLEGIALESESTADO");

		if (record.getIdestado() != null) {
			sql.SET("IDESTADO = " + record.getIdestado());
		}

		if (record.getSituacionresidente() != null && !record.getSituacionresidente().equals("")) {
			sql.SET("SITUACIONRESIDENTE = " + record.getSituacionresidente());
		}

		if (record.getObservaciones() != null && !record.getObservaciones().equals("")) {
			sql.SET("OBSERVACIONES = '" + record.getObservaciones() + "'");
		}else {
			sql.SET("OBSERVACIONES = " + null);

		}
		
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = TO_DATE('" + dateFormat.format(record.getFechamodificacion())
					+ "', 'dd/MM/RRRR')");
		}

		sql.SET("USUMODIFICACION = " + record.getUsumodificacion());

		if (fechaEstadoNueva != null) {
			sql.SET("FECHAESTADO = TO_DATE('" + dateFormat.format(fechaEstadoNueva) + "', 'dd/MM/RRRR')");
			sql.WHERE("TO_DATE(FECHAESTADO, 'dd/MM/RRRR') = TO_DATE('" + dateFormat.format(record.getFechaestado())
					+ "', 'dd/MM/RRRR')");
		} else {
			sql.SET("FECHAESTADO = TO_DATE('" + dateFormat.format(record.getFechaestado()) + "', 'dd/MM/RRRR')");
			sql.WHERE("TO_DATE(FECHAESTADO, 'dd/MM/RRRR') = TO_DATE('" + dateFormat.format(record.getFechaestado())
					+ "', 'dd/MM/RRRR')");
		}

		sql.WHERE("IDINSTITUCION = " + record.getIdinstitucion() + "");
		sql.WHERE("IDPERSONA = " + record.getIdpersona() + "");

		return sql.toString();
	}

	public String deleteEstadoColegial(CenDatoscolegialesestado record) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_DATOSCOLEGIALESESTADO");

		sql.WHERE("IDINSTITUCION = " + record.getIdinstitucion() + "");
		sql.WHERE("IDPERSONA = " + record.getIdpersona() + "");
		sql.WHERE("TO_DATE(FECHAESTADO, 'dd/MM/RRRR') = TO_DATE('" + dateFormat.format(record.getFechaestado())
				+ "', 'dd/MM/RRRR')");

		return sql.toString();
	}

}
