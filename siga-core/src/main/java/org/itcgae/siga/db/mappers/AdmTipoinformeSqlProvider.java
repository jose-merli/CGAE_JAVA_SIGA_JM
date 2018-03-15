package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmTipoinforme;
import org.itcgae.siga.db.entities.AdmTipoinformeExample.Criteria;
import org.itcgae.siga.db.entities.AdmTipoinformeExample.Criterion;
import org.itcgae.siga.db.entities.AdmTipoinformeExample;

public class AdmTipoinformeSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(AdmTipoinformeExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("ADM_TIPOINFORME");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(AdmTipoinformeExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("ADM_TIPOINFORME");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(AdmTipoinforme record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("ADM_TIPOINFORME");
		if (record.getIdtipoinforme() != null) {
			sql.VALUES("IDTIPOINFORME", "#{idtipoinforme,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoinformepadre() != null) {
			sql.VALUES("IDTIPOINFORMEPADRE", "#{idtipoinformepadre,jdbcType=VARCHAR}");
		}
		if (record.getTipoformato() != null) {
			sql.VALUES("TIPOFORMATO", "#{tipoformato,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getClase() != null) {
			sql.VALUES("CLASE", "#{clase,jdbcType=CHAR}");
		}
		if (record.getDirectorio() != null) {
			sql.VALUES("DIRECTORIO", "#{directorio,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(AdmTipoinformeExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDTIPOINFORME");
		} else {
			sql.SELECT("IDTIPOINFORME");
		}
		sql.SELECT("DESCRIPCION");
		sql.SELECT("IDTIPOINFORMEPADRE");
		sql.SELECT("TIPOFORMATO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("CLASE");
		sql.SELECT("DIRECTORIO");
		sql.FROM("ADM_TIPOINFORME");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		AdmTipoinforme record = (AdmTipoinforme) parameter.get("record");
		AdmTipoinformeExample example = (AdmTipoinformeExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("ADM_TIPOINFORME");
		if (record.getIdtipoinforme() != null) {
			sql.SET("IDTIPOINFORME = #{record.idtipoinforme,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoinformepadre() != null) {
			sql.SET("IDTIPOINFORMEPADRE = #{record.idtipoinformepadre,jdbcType=VARCHAR}");
		}
		if (record.getTipoformato() != null) {
			sql.SET("TIPOFORMATO = #{record.tipoformato,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getClase() != null) {
			sql.SET("CLASE = #{record.clase,jdbcType=CHAR}");
		}
		if (record.getDirectorio() != null) {
			sql.SET("DIRECTORIO = #{record.directorio,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ADM_TIPOINFORME");
		sql.SET("IDTIPOINFORME = #{record.idtipoinforme,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("IDTIPOINFORMEPADRE = #{record.idtipoinformepadre,jdbcType=VARCHAR}");
		sql.SET("TIPOFORMATO = #{record.tipoformato,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("CLASE = #{record.clase,jdbcType=CHAR}");
		sql.SET("DIRECTORIO = #{record.directorio,jdbcType=VARCHAR}");
		AdmTipoinformeExample example = (AdmTipoinformeExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(AdmTipoinforme record) {
		SQL sql = new SQL();
		sql.UPDATE("ADM_TIPOINFORME");
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoinformepadre() != null) {
			sql.SET("IDTIPOINFORMEPADRE = #{idtipoinformepadre,jdbcType=VARCHAR}");
		}
		if (record.getTipoformato() != null) {
			sql.SET("TIPOFORMATO = #{tipoformato,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getClase() != null) {
			sql.SET("CLASE = #{clase,jdbcType=CHAR}");
		}
		if (record.getDirectorio() != null) {
			sql.SET("DIRECTORIO = #{directorio,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDTIPOINFORME = #{idtipoinforme,jdbcType=VARCHAR}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, AdmTipoinformeExample example, boolean includeExamplePhrase) {
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