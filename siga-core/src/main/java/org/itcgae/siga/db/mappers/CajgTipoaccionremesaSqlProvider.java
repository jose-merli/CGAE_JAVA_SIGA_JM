package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CajgTipoaccionremesa;
import org.itcgae.siga.db.entities.CajgTipoaccionremesaExample.Criteria;
import org.itcgae.siga.db.entities.CajgTipoaccionremesaExample.Criterion;
import org.itcgae.siga.db.entities.CajgTipoaccionremesaExample;

public class CajgTipoaccionremesaSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String countByExample(CajgTipoaccionremesaExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CAJG_TIPOACCIONREMESA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String deleteByExample(CajgTipoaccionremesaExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CAJG_TIPOACCIONREMESA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String insertSelective(CajgTipoaccionremesa record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CAJG_TIPOACCIONREMESA");
		if (record.getIdtipoaccionremesa() != null) {
			sql.VALUES("IDTIPOACCIONREMESA", "#{idtipoaccionremesa,jdbcType=DECIMAL}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String selectByExample(CajgTipoaccionremesaExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDTIPOACCIONREMESA");
		} else {
			sql.SELECT("IDTIPOACCIONREMESA");
		}
		sql.SELECT("DESCRIPCION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.FROM("CAJG_TIPOACCIONREMESA");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CajgTipoaccionremesa record = (CajgTipoaccionremesa) parameter.get("record");
		CajgTipoaccionremesaExample example = (CajgTipoaccionremesaExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CAJG_TIPOACCIONREMESA");
		if (record.getIdtipoaccionremesa() != null) {
			sql.SET("IDTIPOACCIONREMESA = #{record.idtipoaccionremesa,jdbcType=DECIMAL}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CAJG_TIPOACCIONREMESA");
		sql.SET("IDTIPOACCIONREMESA = #{record.idtipoaccionremesa,jdbcType=DECIMAL}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		CajgTipoaccionremesaExample example = (CajgTipoaccionremesaExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String updateByPrimaryKeySelective(CajgTipoaccionremesa record) {
		SQL sql = new SQL();
		sql.UPDATE("CAJG_TIPOACCIONREMESA");
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDTIPOACCIONREMESA = #{idtipoaccionremesa,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	protected void applyWhere(SQL sql, CajgTipoaccionremesaExample example, boolean includeExamplePhrase) {
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