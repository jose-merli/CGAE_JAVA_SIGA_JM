package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.entities.EcomColaExample.Criteria;
import org.itcgae.siga.db.entities.EcomColaExample.Criterion;

public class EcomColaSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String countByExample(EcomColaExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("ECOM_COLA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String deleteByExample(EcomColaExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("ECOM_COLA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String insertSelective(EcomCola record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("ECOM_COLA");
		sql.VALUES("IDECOMCOLA", "#{idecomcola,jdbcType=DECIMAL}");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadocola() != null) {
			sql.VALUES("IDESTADOCOLA", "#{idestadocola,jdbcType=DECIMAL}");
		}
		if (record.getIdoperacion() != null) {
			sql.VALUES("IDOPERACION", "#{idoperacion,jdbcType=DECIMAL}");
		}
		if (record.getReintento() != null) {
			sql.VALUES("REINTENTO", "#{reintento,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.VALUES("FECHACREACION", "#{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechaejecucion() != null) {
			sql.VALUES("FECHAEJECUCION", "#{fechaejecucion,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String selectByExample(EcomColaExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDECOMCOLA");
		} else {
			sql.SELECT("IDECOMCOLA");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDESTADOCOLA");
		sql.SELECT("IDOPERACION");
		sql.SELECT("REINTENTO");
		sql.SELECT("FECHACREACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHAEJECUCION");
		sql.FROM("ECOM_COLA");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		EcomCola record = (EcomCola) parameter.get("record");
		EcomColaExample example = (EcomColaExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("ECOM_COLA");
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadocola() != null) {
			sql.SET("IDESTADOCOLA = #{record.idestadocola,jdbcType=DECIMAL}");
		}
		if (record.getIdoperacion() != null) {
			sql.SET("IDOPERACION = #{record.idoperacion,jdbcType=DECIMAL}");
		}
		if (record.getReintento() != null) {
			sql.SET("REINTENTO = #{record.reintento,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechaejecucion() != null) {
			sql.SET("FECHAEJECUCION = #{record.fechaejecucion,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ECOM_COLA");
		sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDESTADOCOLA = #{record.idestadocola,jdbcType=DECIMAL}");
		sql.SET("IDOPERACION = #{record.idoperacion,jdbcType=DECIMAL}");
		sql.SET("REINTENTO = #{record.reintento,jdbcType=DECIMAL}");
		sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHAEJECUCION = #{record.fechaejecucion,jdbcType=TIMESTAMP}");
		EcomColaExample example = (EcomColaExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	public String updateByPrimaryKeySelective(EcomCola record) {
		SQL sql = new SQL();
		sql.UPDATE("ECOM_COLA");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadocola() != null) {
			sql.SET("IDESTADOCOLA = #{idestadocola,jdbcType=DECIMAL}");
		}
		if (record.getIdoperacion() != null) {
			sql.SET("IDOPERACION = #{idoperacion,jdbcType=DECIMAL}");
		}
		if (record.getReintento() != null) {
			sql.SET("REINTENTO = #{reintento,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechaejecucion() != null) {
			sql.SET("FECHAEJECUCION = #{fechaejecucion,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDECOMCOLA = #{idecomcola,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_COLA
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	protected void applyWhere(SQL sql, EcomColaExample example, boolean includeExamplePhrase) {
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