package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenProvincias;
import org.itcgae.siga.db.entities.CenProvinciasExample;
import org.itcgae.siga.db.entities.CenProvinciasExample.Criteria;
import org.itcgae.siga.db.entities.CenProvinciasExample.Criterion;

public class CenProvinciasSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenProvinciasExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_PROVINCIAS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenProvinciasExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_PROVINCIAS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenProvincias record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_PROVINCIAS");
		if (record.getIdprovincia() != null) {
			sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.VALUES("IDPAIS", "#{idpais,jdbcType=VARCHAR}");
		}
		if (record.getIdcomunidadautonoma() != null) {
			sql.VALUES("IDCOMUNIDADAUTONOMA", "#{idcomunidadautonoma,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getCodigoext() != null) {
			sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenProvinciasExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPROVINCIA");
		} else {
			sql.SELECT("IDPROVINCIA");
		}
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDPAIS");
		sql.SELECT("IDCOMUNIDADAUTONOMA");
		sql.SELECT("NOMBRE");
		sql.SELECT("CODIGOEXT");
		sql.FROM("CEN_PROVINCIAS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenProvincias record = (CenProvincias) parameter.get("record");
		CenProvinciasExample example = (CenProvinciasExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_PROVINCIAS");
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
		}
		if (record.getIdcomunidadautonoma() != null) {
			sql.SET("IDCOMUNIDADAUTONOMA = #{record.idcomunidadautonoma,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_PROVINCIAS");
		sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
		sql.SET("IDCOMUNIDADAUTONOMA = #{record.idcomunidadautonoma,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		CenProvinciasExample example = (CenProvinciasExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenProvincias record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_PROVINCIAS");
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.SET("IDPAIS = #{idpais,jdbcType=VARCHAR}");
		}
		if (record.getIdcomunidadautonoma() != null) {
			sql.SET("IDCOMUNIDADAUTONOMA = #{idcomunidadautonoma,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PROVINCIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenProvinciasExample example, boolean includeExamplePhrase) {
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