package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcs;
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcsExample.Criteria;
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcsExample.Criterion;
import org.itcgae.siga.db.entities.CenHistoricoLiquidacionsjcsExample;

public class CenHistoricoLiquidacionsjcsSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String countByExample(CenHistoricoLiquidacionsjcsExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_HISTORICO_LIQUIDACIONSJCS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String deleteByExample(CenHistoricoLiquidacionsjcsExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_HISTORICO_LIQUIDACIONSJCS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String insertSelective(CenHistoricoLiquidacionsjcs record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_HISTORICO_LIQUIDACIONSJCS");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcomponente() != null) {
			sql.VALUES("IDCOMPONENTE", "#{idcomponente,jdbcType=DECIMAL}");
		}
		sql.VALUES("IDHISTORICO", "#{idhistorico,jdbcType=DECIMAL}");
		if (record.getFecha() != null) {
			sql.VALUES("FECHA", "#{fecha,jdbcType=TIMESTAMP}");
		}
		if (record.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getSociedad() != null) {
			sql.VALUES("SOCIEDAD", "#{sociedad,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String selectByExample(CenHistoricoLiquidacionsjcsExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPERSONA");
		sql.SELECT("IDCOMPONENTE");
		sql.SELECT("IDHISTORICO");
		sql.SELECT("FECHA");
		sql.SELECT("OBSERVACIONES");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("SOCIEDAD");
		sql.FROM("CEN_HISTORICO_LIQUIDACIONSJCS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenHistoricoLiquidacionsjcs record = (CenHistoricoLiquidacionsjcs) parameter.get("record");
		CenHistoricoLiquidacionsjcsExample example = (CenHistoricoLiquidacionsjcsExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_HISTORICO_LIQUIDACIONSJCS");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcomponente() != null) {
			sql.SET("IDCOMPONENTE = #{record.idcomponente,jdbcType=DECIMAL}");
		}
		if (record.getIdhistorico() != null) {
			sql.SET("IDHISTORICO = #{record.idhistorico,jdbcType=DECIMAL}");
		}
		if (record.getFecha() != null) {
			sql.SET("FECHA = #{record.fecha,jdbcType=TIMESTAMP}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getSociedad() != null) {
			sql.SET("SOCIEDAD = #{record.sociedad,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_HISTORICO_LIQUIDACIONSJCS");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDCOMPONENTE = #{record.idcomponente,jdbcType=DECIMAL}");
		sql.SET("IDHISTORICO = #{record.idhistorico,jdbcType=DECIMAL}");
		sql.SET("FECHA = #{record.fecha,jdbcType=TIMESTAMP}");
		sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("SOCIEDAD = #{record.sociedad,jdbcType=VARCHAR}");
		CenHistoricoLiquidacionsjcsExample example = (CenHistoricoLiquidacionsjcsExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	public String updateByPrimaryKeySelective(CenHistoricoLiquidacionsjcs record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_HISTORICO_LIQUIDACIONSJCS");
		if (record.getFecha() != null) {
			sql.SET("FECHA = #{fecha,jdbcType=TIMESTAMP}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getSociedad() != null) {
			sql.SET("SOCIEDAD = #{sociedad,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		sql.WHERE("IDCOMPONENTE = #{idcomponente,jdbcType=DECIMAL}");
		sql.WHERE("IDHISTORICO = #{idhistorico,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_HISTORICO_LIQUIDACIONSJCS
	 * @mbg.generated  Tue Dec 14 08:35:14 CET 2021
	 */
	protected void applyWhere(SQL sql, CenHistoricoLiquidacionsjcsExample example, boolean includeExamplePhrase) {
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