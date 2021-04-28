package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.entities.ScsSaltoscompensacionesExample;
import org.itcgae.siga.db.entities.ScsSaltoscompensacionesExample.Criteria;
import org.itcgae.siga.db.entities.ScsSaltoscompensacionesExample.Criterion;

public class ScsSaltoscompensacionesSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String countByExample(ScsSaltoscompensacionesExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_SALTOSCOMPENSACIONES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String deleteByExample(ScsSaltoscompensacionesExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_SALTOSCOMPENSACIONES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String insertSelective(ScsSaltoscompensaciones record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_SALTOSCOMPENSACIONES");

		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}

		if (record.getIdturno() != null) {
			sql.VALUES("IDTURNO", "#{idturno,jdbcType=DECIMAL}");
		}

		if (record.getIdsaltosturno() != null) {
			sql.VALUES("IDSALTOSTURNO", "#{idsaltosturno,jdbcType=DECIMAL}");
		}

		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}

		if (record.getSaltoocompensacion() != null) {
			sql.VALUES("SALTOOCOMPENSACION", "#{saltoocompensacion,jdbcType=VARCHAR}");
		}

		if (record.getFecha() != null) {
			sql.VALUES("FECHA", "#{fecha,jdbcType=TIMESTAMP}");
		}

		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}

		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}

		if (record.getIdguardia() != null) {
			sql.VALUES("IDGUARDIA", "#{idguardia,jdbcType=DECIMAL}");
		}

		if (record.getMotivos() != null) {
			sql.VALUES("MOTIVOS", "#{motivos,jdbcType=VARCHAR}");
		}

		if (record.getFechacumplimiento() != null) {
			sql.VALUES("FECHACUMPLIMIENTO", "#{fechacumplimiento,jdbcType=TIMESTAMP}");
		}

		if (record.getIdcalendarioguardias() != null) {
			sql.VALUES("IDCALENDARIOGUARDIAS", "#{idcalendarioguardias,jdbcType=DECIMAL}");
		}

		if (record.getIdcalendarioguardiascreacion() != null) {
			sql.VALUES("IDCALENDARIOGUARDIASCREACION", "#{idcalendarioguardiascreacion,jdbcType=DECIMAL}");
		}

		if (record.getTipomanual() != null) {
			sql.VALUES("TIPOMANUAL", "#{tipomanual,jdbcType=DECIMAL}");
		}

		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String selectByExample(ScsSaltoscompensacionesExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDTURNO");
		sql.SELECT("IDSALTOSTURNO");
		sql.SELECT("IDPERSONA");
		sql.SELECT("SALTOOCOMPENSACION");
		sql.SELECT("FECHA");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDGUARDIA");
		sql.SELECT("MOTIVOS");
		sql.SELECT("FECHACUMPLIMIENTO");
		sql.SELECT("IDCALENDARIOGUARDIAS");
		sql.SELECT("IDCALENDARIOGUARDIASCREACION");
		sql.SELECT("TIPOMANUAL");
		sql.FROM("SCS_SALTOSCOMPENSACIONES");
		applyWhere(sql, example, false);

		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}

		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsSaltoscompensaciones record = (ScsSaltoscompensaciones) parameter.get("record");
		ScsSaltoscompensacionesExample example = (ScsSaltoscompensacionesExample) parameter.get("example");

		SQL sql = new SQL();
		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");

		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}

		if (record.getIdturno() != null) {
			sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
		}

		if (record.getIdsaltosturno() != null) {
			sql.SET("IDSALTOSTURNO = #{record.idsaltosturno,jdbcType=DECIMAL}");
		}

		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}

		if (record.getSaltoocompensacion() != null) {
			sql.SET("SALTOOCOMPENSACION = #{record.saltoocompensacion,jdbcType=VARCHAR}");
		}

		if (record.getFecha() != null) {
			sql.SET("FECHA = #{record.fecha,jdbcType=TIMESTAMP}");
		}

		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}

		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}

		if (record.getIdguardia() != null) {
			sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
		}

		if (record.getMotivos() != null) {
			sql.SET("MOTIVOS = #{record.motivos,jdbcType=VARCHAR}");
		}

		if (record.getFechacumplimiento() != null) {
			sql.SET("FECHACUMPLIMIENTO = #{record.fechacumplimiento,jdbcType=TIMESTAMP}");
		}

		if (record.getIdcalendarioguardias() != null) {
			sql.SET("IDCALENDARIOGUARDIAS = #{record.idcalendarioguardias,jdbcType=DECIMAL}");
		}

		if (record.getIdcalendarioguardiascreacion() != null) {
			sql.SET("IDCALENDARIOGUARDIASCREACION = #{record.idcalendarioguardiascreacion,jdbcType=DECIMAL}");
		}

		if (record.getTipomanual() != null) {
			sql.SET("TIPOMANUAL = #{record.tipomanual,jdbcType=DECIMAL}");
		}

		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");

		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
		sql.SET("IDSALTOSTURNO = #{record.idsaltosturno,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("SALTOOCOMPENSACION = #{record.saltoocompensacion,jdbcType=VARCHAR}");
		sql.SET("FECHA = #{record.fecha,jdbcType=TIMESTAMP}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
		sql.SET("MOTIVOS = #{record.motivos,jdbcType=VARCHAR}");
		sql.SET("FECHACUMPLIMIENTO = #{record.fechacumplimiento,jdbcType=TIMESTAMP}");
		sql.SET("IDCALENDARIOGUARDIAS = #{record.idcalendarioguardias,jdbcType=DECIMAL}");
		sql.SET("IDCALENDARIOGUARDIASCREACION = #{record.idcalendarioguardiascreacion,jdbcType=DECIMAL}");
		sql.SET("TIPOMANUAL = #{record.tipomanual,jdbcType=DECIMAL}");

		ScsSaltoscompensacionesExample example = (ScsSaltoscompensacionesExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	public String updateByPrimaryKeySelective(ScsSaltoscompensaciones record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");

		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		}

		if (record.getSaltoocompensacion() != null) {
			sql.SET("SALTOOCOMPENSACION = #{saltoocompensacion,jdbcType=VARCHAR}");
		}

		if (record.getFecha() != null) {
			sql.SET("FECHA = #{fecha,jdbcType=TIMESTAMP}");
		}

		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}

		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}

		if (record.getIdguardia() != null) {
			sql.SET("IDGUARDIA = #{idguardia,jdbcType=DECIMAL}");
		}

		if (record.getMotivos() != null) {
			sql.SET("MOTIVOS = #{motivos,jdbcType=VARCHAR}");
		}

		if (record.getFechacumplimiento() != null) {
			sql.SET("FECHACUMPLIMIENTO = #{fechacumplimiento,jdbcType=TIMESTAMP}");
		}

		if (record.getIdcalendarioguardias() != null) {
			sql.SET("IDCALENDARIOGUARDIAS = #{idcalendarioguardias,jdbcType=DECIMAL}");
		}

		if (record.getIdcalendarioguardiascreacion() != null) {
			sql.SET("IDCALENDARIOGUARDIASCREACION = #{idcalendarioguardiascreacion,jdbcType=DECIMAL}");
		}

		if (record.getTipomanual() != null) {
			sql.SET("TIPOMANUAL = #{tipomanual,jdbcType=DECIMAL}");
		}

		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDTURNO = #{idturno,jdbcType=DECIMAL}");
		sql.WHERE("IDSALTOSTURNO = #{idsaltosturno,jdbcType=DECIMAL}");

		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	protected void applyWhere(SQL sql, ScsSaltoscompensacionesExample example, boolean includeExamplePhrase) {
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