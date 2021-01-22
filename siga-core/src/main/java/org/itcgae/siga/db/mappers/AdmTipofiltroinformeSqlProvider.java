package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmTipofiltroinforme;
import org.itcgae.siga.db.entities.AdmTipofiltroinformeExample;
import org.itcgae.siga.db.entities.AdmTipofiltroinformeExample.Criteria;
import org.itcgae.siga.db.entities.AdmTipofiltroinformeExample.Criterion;

public class AdmTipofiltroinformeSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(AdmTipofiltroinformeExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("ADM_TIPOFILTROINFORME");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(AdmTipofiltroinformeExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("ADM_TIPOFILTROINFORME");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(AdmTipofiltroinforme record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("ADM_TIPOFILTROINFORME");
		if (record.getIdtipoinforme() != null) {
			sql.VALUES("IDTIPOINFORME", "#{idtipoinforme,jdbcType=VARCHAR}");
		}
		if (record.getIdtipofiltroinforme() != null) {
			sql.VALUES("IDTIPOFILTROINFORME", "#{idtipofiltroinforme,jdbcType=DECIMAL}");
		}
		if (record.getNombrecampo() != null) {
			sql.VALUES("NOMBRECAMPO", "#{nombrecampo,jdbcType=VARCHAR}");
		}
		if (record.getObligatorio() != null) {
			sql.VALUES("OBLIGATORIO", "#{obligatorio,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(AdmTipofiltroinformeExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDTIPOINFORME");
		} else {
			sql.SELECT("IDTIPOINFORME");
		}
		sql.SELECT("IDTIPOFILTROINFORME");
		sql.SELECT("NOMBRECAMPO");
		sql.SELECT("OBLIGATORIO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.FROM("ADM_TIPOFILTROINFORME");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		AdmTipofiltroinforme record = (AdmTipofiltroinforme) parameter.get("record");
		AdmTipofiltroinformeExample example = (AdmTipofiltroinformeExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("ADM_TIPOFILTROINFORME");
		if (record.getIdtipoinforme() != null) {
			sql.SET("IDTIPOINFORME = #{record.idtipoinforme,jdbcType=VARCHAR}");
		}
		if (record.getIdtipofiltroinforme() != null) {
			sql.SET("IDTIPOFILTROINFORME = #{record.idtipofiltroinforme,jdbcType=DECIMAL}");
		}
		if (record.getNombrecampo() != null) {
			sql.SET("NOMBRECAMPO = #{record.nombrecampo,jdbcType=VARCHAR}");
		}
		if (record.getObligatorio() != null) {
			sql.SET("OBLIGATORIO = #{record.obligatorio,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ADM_TIPOFILTROINFORME");
		sql.SET("IDTIPOINFORME = #{record.idtipoinforme,jdbcType=VARCHAR}");
		sql.SET("IDTIPOFILTROINFORME = #{record.idtipofiltroinforme,jdbcType=DECIMAL}");
		sql.SET("NOMBRECAMPO = #{record.nombrecampo,jdbcType=VARCHAR}");
		sql.SET("OBLIGATORIO = #{record.obligatorio,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		AdmTipofiltroinformeExample example = (AdmTipofiltroinformeExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(AdmTipofiltroinforme record) {
		SQL sql = new SQL();
		sql.UPDATE("ADM_TIPOFILTROINFORME");
		if (record.getNombrecampo() != null) {
			sql.SET("NOMBRECAMPO = #{nombrecampo,jdbcType=VARCHAR}");
		}
		if (record.getObligatorio() != null) {
			sql.SET("OBLIGATORIO = #{obligatorio,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDTIPOINFORME = #{idtipoinforme,jdbcType=VARCHAR}");
		sql.WHERE("IDTIPOFILTROINFORME = #{idtipofiltroinforme,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOFILTROINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, AdmTipofiltroinformeExample example, boolean includeExamplePhrase) {
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