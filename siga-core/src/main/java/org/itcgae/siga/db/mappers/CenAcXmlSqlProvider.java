package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenAcXml;
import org.itcgae.siga.db.entities.CenAcXmlExample;
import org.itcgae.siga.db.entities.CenAcXmlExample.Criteria;
import org.itcgae.siga.db.entities.CenAcXmlExample.Criterion;

public class CenAcXmlSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenAcXmlExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_AC_XML");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenAcXmlExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_AC_XML");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenAcXml record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_AC_XML");
		if (record.getIdcenacxml() != null) {
			sql.VALUES("IDCENACXML", "#{idcenacxml,jdbcType=DECIMAL}");
		}
		if (record.getXml() != null) {
			sql.VALUES("XML", "#{xml,jdbcType=CLOB}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExampleWithBLOBs(CenAcXmlExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDCENACXML");
		} else {
			sql.SELECT("IDCENACXML");
		}
		sql.SELECT("XML");
		sql.FROM("CEN_AC_XML");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenAcXmlExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDCENACXML");
		} else {
			sql.SELECT("IDCENACXML");
		}
		sql.FROM("CEN_AC_XML");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenAcXml record = (CenAcXml) parameter.get("record");
		CenAcXmlExample example = (CenAcXmlExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_XML");
		if (record.getIdcenacxml() != null) {
			sql.SET("IDCENACXML = #{record.idcenacxml,jdbcType=DECIMAL}");
		}
		if (record.getXml() != null) {
			sql.SET("XML = #{record.xml,jdbcType=CLOB}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_XML");
		sql.SET("IDCENACXML = #{record.idcenacxml,jdbcType=DECIMAL}");
		sql.SET("XML = #{record.xml,jdbcType=CLOB}");
		CenAcXmlExample example = (CenAcXmlExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_XML");
		sql.SET("IDCENACXML = #{record.idcenacxml,jdbcType=DECIMAL}");
		CenAcXmlExample example = (CenAcXmlExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenAcXml record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_XML");
		if (record.getXml() != null) {
			sql.SET("XML = #{xml,jdbcType=CLOB}");
		}
		sql.WHERE("IDCENACXML = #{idcenacxml,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_XML
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenAcXmlExample example, boolean includeExamplePhrase) {
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