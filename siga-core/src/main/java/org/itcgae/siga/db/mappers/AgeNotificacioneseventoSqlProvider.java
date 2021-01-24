package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample.Criteria;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample.Criterion;

public class AgeNotificacioneseventoSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String countByExample(AgeNotificacioneseventoExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("AGE_NOTIFICACIONESEVENTO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String deleteByExample(AgeNotificacioneseventoExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("AGE_NOTIFICACIONESEVENTO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String insertSelective(AgeNotificacionesevento record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("AGE_NOTIFICACIONESEVENTO");
		sql.VALUES("IDNOTIFICACIONEVENTO", "#{idnotificacionevento,jdbcType=DECIMAL}");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtiponotificacionevento() != null) {
			sql.VALUES("IDTIPONOTIFICACIONEVENTO", "#{idtiponotificacionevento,jdbcType=DECIMAL}");
		}
		if (record.getCuando() != null) {
			sql.VALUES("CUANDO", "#{cuando,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocuando() != null) {
			sql.VALUES("IDTIPOCUANDO", "#{idtipocuando,jdbcType=DECIMAL}");
		}
		if (record.getIdunidadmedida() != null) {
			sql.VALUES("IDUNIDADMEDIDA", "#{idunidadmedida,jdbcType=DECIMAL}");
		}
		if (record.getIdcalendario() != null) {
			sql.VALUES("IDCALENDARIO", "#{idcalendario,jdbcType=DECIMAL}");
		}
		if (record.getIdevento() != null) {
			sql.VALUES("IDEVENTO", "#{idevento,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getIdplantilla() != null) {
			sql.VALUES("IDPLANTILLA", "#{idplantilla,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoenvios() != null) {
			sql.VALUES("IDTIPOENVIOS", "#{idtipoenvios,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String selectByExample(AgeNotificacioneseventoExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDNOTIFICACIONEVENTO");
		} else {
			sql.SELECT("IDNOTIFICACIONEVENTO");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("IDTIPONOTIFICACIONEVENTO");
		sql.SELECT("CUANDO");
		sql.SELECT("IDTIPOCUANDO");
		sql.SELECT("IDUNIDADMEDIDA");
		sql.SELECT("IDCALENDARIO");
		sql.SELECT("IDEVENTO");
		sql.SELECT("FECHABAJA");
		sql.SELECT("IDPLANTILLA");
		sql.SELECT("IDTIPOENVIOS");
		sql.FROM("AGE_NOTIFICACIONESEVENTO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		AgeNotificacionesevento record = (AgeNotificacionesevento) parameter.get("record");
		AgeNotificacioneseventoExample example = (AgeNotificacioneseventoExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("AGE_NOTIFICACIONESEVENTO");
		if (record.getIdnotificacionevento() != null) {
			sql.SET("IDNOTIFICACIONEVENTO = #{record.idnotificacionevento,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtiponotificacionevento() != null) {
			sql.SET("IDTIPONOTIFICACIONEVENTO = #{record.idtiponotificacionevento,jdbcType=DECIMAL}");
		}
		if (record.getCuando() != null) {
			sql.SET("CUANDO = #{record.cuando,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocuando() != null) {
			sql.SET("IDTIPOCUANDO = #{record.idtipocuando,jdbcType=DECIMAL}");
		}
		if (record.getIdunidadmedida() != null) {
			sql.SET("IDUNIDADMEDIDA = #{record.idunidadmedida,jdbcType=DECIMAL}");
		}
		if (record.getIdcalendario() != null) {
			sql.SET("IDCALENDARIO = #{record.idcalendario,jdbcType=DECIMAL}");
		}
		if (record.getIdevento() != null) {
			sql.SET("IDEVENTO = #{record.idevento,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getIdplantilla() != null) {
			sql.SET("IDPLANTILLA = #{record.idplantilla,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoenvios() != null) {
			sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("AGE_NOTIFICACIONESEVENTO");
		sql.SET("IDNOTIFICACIONEVENTO = #{record.idnotificacionevento,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("IDTIPONOTIFICACIONEVENTO = #{record.idtiponotificacionevento,jdbcType=DECIMAL}");
		sql.SET("CUANDO = #{record.cuando,jdbcType=DECIMAL}");
		sql.SET("IDTIPOCUANDO = #{record.idtipocuando,jdbcType=DECIMAL}");
		sql.SET("IDUNIDADMEDIDA = #{record.idunidadmedida,jdbcType=DECIMAL}");
		sql.SET("IDCALENDARIO = #{record.idcalendario,jdbcType=DECIMAL}");
		sql.SET("IDEVENTO = #{record.idevento,jdbcType=DECIMAL}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		sql.SET("IDPLANTILLA = #{record.idplantilla,jdbcType=DECIMAL}");
		sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
		AgeNotificacioneseventoExample example = (AgeNotificacioneseventoExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	public String updateByPrimaryKeySelective(AgeNotificacionesevento record) {
		SQL sql = new SQL();
		sql.UPDATE("AGE_NOTIFICACIONESEVENTO");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtiponotificacionevento() != null) {
			sql.SET("IDTIPONOTIFICACIONEVENTO = #{idtiponotificacionevento,jdbcType=DECIMAL}");
		}
		if (record.getCuando() != null) {
			sql.SET("CUANDO = #{cuando,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocuando() != null) {
			sql.SET("IDTIPOCUANDO = #{idtipocuando,jdbcType=DECIMAL}");
		}
		if (record.getIdunidadmedida() != null) {
			sql.SET("IDUNIDADMEDIDA = #{idunidadmedida,jdbcType=DECIMAL}");
		}
		if (record.getIdcalendario() != null) {
			sql.SET("IDCALENDARIO = #{idcalendario,jdbcType=DECIMAL}");
		}
		if (record.getIdevento() != null) {
			sql.SET("IDEVENTO = #{idevento,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getIdplantilla() != null) {
			sql.SET("IDPLANTILLA = #{idplantilla,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoenvios() != null) {
			sql.SET("IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDNOTIFICACIONEVENTO = #{idnotificacionevento,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Fri Nov 30 09:15:27 CET 2018
	 */
	protected void applyWhere(SQL sql, AgeNotificacioneseventoExample example, boolean includeExamplePhrase) {
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