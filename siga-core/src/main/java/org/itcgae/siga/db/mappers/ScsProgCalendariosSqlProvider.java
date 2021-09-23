package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsProgCalendarios;
import org.itcgae.siga.db.entities.ScsProgCalendariosExample.Criteria;
import org.itcgae.siga.db.entities.ScsProgCalendariosExample.Criterion;
import org.itcgae.siga.db.entities.ScsProgCalendariosExample;

public class ScsProgCalendariosSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String countByExample(ScsProgCalendariosExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_PROG_CALENDARIOS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String deleteByExample(ScsProgCalendariosExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_PROG_CALENDARIOS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String insertSelective(ScsProgCalendarios record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_PROG_CALENDARIOS");
		if (record.getIdprogcalendario() != null) {
			sql.VALUES("IDPROGCALENDARIO", "#{idprogcalendario,jdbcType=DECIMAL}");
		}
		if (record.getIdconjuntoguardia() != null) {
			sql.VALUES("IDCONJUNTOGUARDIA", "#{idconjuntoguardia,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getFechaprogramacion() != null) {
			sql.VALUES("FECHAPROGRAMACION", "#{fechaprogramacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechacalinicio() != null) {
			sql.VALUES("FECHACALINICIO", "#{fechacalinicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechacalfin() != null) {
			sql.VALUES("FECHACALFIN", "#{fechacalfin,jdbcType=TIMESTAMP}");
		}
		if (record.getEstado() != null) {
			sql.VALUES("ESTADO", "#{estado,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdficherocalendario() != null) {
			sql.VALUES("IDFICHEROCALENDARIO", "#{idficherocalendario,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String selectByExample(ScsProgCalendariosExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPROGCALENDARIO");
		} else {
			sql.SELECT("IDPROGCALENDARIO");
		}
		sql.SELECT("IDCONJUNTOGUARDIA");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("FECHAPROGRAMACION");
		sql.SELECT("FECHACALINICIO");
		sql.SELECT("FECHACALFIN");
		sql.SELECT("ESTADO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDFICHEROCALENDARIO");
		sql.FROM("SCS_PROG_CALENDARIOS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsProgCalendarios record = (ScsProgCalendarios) parameter.get("record");
		ScsProgCalendariosExample example = (ScsProgCalendariosExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROG_CALENDARIOS");
		if (record.getIdprogcalendario() != null) {
			sql.SET("IDPROGCALENDARIO = #{record.idprogcalendario,jdbcType=DECIMAL}");
		}
		if (record.getIdconjuntoguardia() != null) {
			sql.SET("IDCONJUNTOGUARDIA = #{record.idconjuntoguardia,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getFechaprogramacion() != null) {
			sql.SET("FECHAPROGRAMACION = #{record.fechaprogramacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechacalinicio() != null) {
			sql.SET("FECHACALINICIO = #{record.fechacalinicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechacalfin() != null) {
			sql.SET("FECHACALFIN = #{record.fechacalfin,jdbcType=TIMESTAMP}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdficherocalendario() != null) {
			sql.SET("IDFICHEROCALENDARIO = #{record.idficherocalendario,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROG_CALENDARIOS");
		sql.SET("IDPROGCALENDARIO = #{record.idprogcalendario,jdbcType=DECIMAL}");
		sql.SET("IDCONJUNTOGUARDIA = #{record.idconjuntoguardia,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("FECHAPROGRAMACION = #{record.fechaprogramacion,jdbcType=TIMESTAMP}");
		sql.SET("FECHACALINICIO = #{record.fechacalinicio,jdbcType=TIMESTAMP}");
		sql.SET("FECHACALFIN = #{record.fechacalfin,jdbcType=TIMESTAMP}");
		sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDFICHEROCALENDARIO = #{record.idficherocalendario,jdbcType=DECIMAL}");
		ScsProgCalendariosExample example = (ScsProgCalendariosExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	public String updateByPrimaryKeySelective(ScsProgCalendarios record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROG_CALENDARIOS");
		if (record.getIdconjuntoguardia() != null) {
			sql.SET("IDCONJUNTOGUARDIA = #{idconjuntoguardia,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getFechaprogramacion() != null) {
			sql.SET("FECHAPROGRAMACION = CAST(#{fechaprogramacion,jdbcType=TIMESTAMP} as TIMESTAMP)");
		}
		if (record.getFechacalinicio() != null) {
			sql.SET("FECHACALINICIO = #{fechacalinicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechacalfin() != null) {
			sql.SET("FECHACALFIN = #{fechacalfin,jdbcType=TIMESTAMP}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{estado,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdficherocalendario() != null) {
			sql.SET("IDFICHEROCALENDARIO = #{idficherocalendario,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDPROGCALENDARIO = #{idprogcalendario,jdbcType=DECIMAL}");
		return sql.toString();
	}

	
	public String updateProgCalendario(String idconjuntoguardia, String idinstitucion, String fechaprogramacion, String fechacalinicio, String fechacalfin, String estado, String fechamodificacion, String usumodificacion, String idficherocalendario, String idprogcalendario) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROG_CALENDARIOS");
		if (idconjuntoguardia != null) {
			sql.SET("IDCONJUNTOGUARDIA = " + idconjuntoguardia);
		}
		if (idinstitucion != null) {
			sql.SET("IDINSTITUCION =  " + idinstitucion );
		}
		if (fechaprogramacion != null) {
			sql.SET("FECHAPROGRAMACION =  TO_DATE('" + fechaprogramacion + "' , 'dd/MM/yyyy HH24:mi:ss')");
		}
		if (fechacalinicio != null) {
			sql.SET("FECHACALINICIO = '" + fechacalinicio + "'");
		}
		if (fechacalfin != null) {
			sql.SET("FECHACALFIN =  '" + fechacalfin + "'");
		}
		if (estado != null) {
			sql.SET("ESTADO = " + estado);
		}
		if (idficherocalendario != null) {
			sql.SET("IDFICHEROCALENDARIO = " + idficherocalendario);
		}
		sql.WHERE("IDPROGCALENDARIO = " + idprogcalendario);
		return sql.toString();
	}
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Thu Jul 22 19:00:11 CEST 2021
	 */
	protected void applyWhere(SQL sql, ScsProgCalendariosExample example, boolean includeExamplePhrase) {
		if (example == null) {
			return;
		}
		String parmPhrase1;
		String parmPhrase1_th;
		String parmPhrase2;
		String parmPhrase2_th;
		String parmPhrase3;
		String parmPhrase3_th;
		if (includeExamplePhrase) {
			parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		} else {
			parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		}
		StringBuilder sb = new StringBuilder();
		List<Criteria> oredCriteria = example.getOredCriteria();
		boolean firstCriteria = true;
		for (int i = 0; i < oredCriteria.size(); i++) {
			Criteria criteria = oredCriteria.get(i);
			if (criteria.isValid()) {
				if (firstCriteria) {
					firstCriteria = false;
				} else {
					sb.append(" or ");
				}
				sb.append('(');
				List<Criterion> criterions = criteria.getAllCriteria();
				boolean firstCriterion = true;
				for (int j = 0; j < criterions.size(); j++) {
					Criterion criterion = criterions.get(j);
					if (firstCriterion) {
						firstCriterion = false;
					} else {
						sb.append(" and ");
					}
					if (criterion.isNoValue()) {
						sb.append(criterion.getCondition());
					} else if (criterion.isSingleValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
						} else {
							sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,
									criterion.getTypeHandler()));
						}
					} else if (criterion.isBetweenValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
						} else {
							sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j,
									criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
						}
					} else if (criterion.isListValue()) {
						sb.append(criterion.getCondition());
						sb.append(" (");
						List<?> listItems = (List<?>) criterion.getValue();
						boolean comma = false;
						for (int k = 0; k < listItems.size(); k++) {
							if (comma) {
								sb.append(", ");
							} else {
								comma = true;
							}
							if (criterion.getTypeHandler() == null) {
								sb.append(String.format(parmPhrase3, i, j, k));
							} else {
								sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
							}
						}
						sb.append(')');
					}
				}
				sb.append(')');
			}
		}
		if (sb.length() > 0) {
			sql.WHERE(sb.toString());
		}
	}
}