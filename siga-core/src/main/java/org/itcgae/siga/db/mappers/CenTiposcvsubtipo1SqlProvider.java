package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example.Criteria;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example.Criterion;

public class CenTiposcvsubtipo1SqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String countByExample(CenTiposcvsubtipo1Example example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_TIPOSCVSUBTIPO1");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String deleteByExample(CenTiposcvsubtipo1Example example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_TIPOSCVSUBTIPO1");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String insertSelective(CenTiposcvsubtipo1 record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_TIPOSCVSUBTIPO1");
		if (record.getIdtipocv() != null) {
			sql.VALUES("IDTIPOCV", "#{idtipocv,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocvsubtipo1() != null) {
			sql.VALUES("IDTIPOCVSUBTIPO1", "#{idtipocvsubtipo1,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
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
		if (record.getFechaBaja() != null) {
			sql.VALUES("FECHA_BAJA", "#{fechaBaja,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String selectByExample(CenTiposcvsubtipo1Example example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDTIPOCV");
		} else {
			sql.SELECT("IDTIPOCV");
		}
		sql.SELECT("IDTIPOCVSUBTIPO1");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("CODIGOEXT");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHA_BAJA");
		sql.FROM("CEN_TIPOSCVSUBTIPO1");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenTiposcvsubtipo1 record = (CenTiposcvsubtipo1) parameter.get("record");
		CenTiposcvsubtipo1Example example = (CenTiposcvsubtipo1Example) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_TIPOSCVSUBTIPO1");
		if (record.getIdtipocv() != null) {
			sql.SET("IDTIPOCV = #{record.idtipocv,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocvsubtipo1() != null) {
			sql.SET("IDTIPOCVSUBTIPO1 = #{record.idtipocvsubtipo1,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
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
		if (record.getFechaBaja() != null) {
			sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_TIPOSCVSUBTIPO1");
		sql.SET("IDTIPOCV = #{record.idtipocv,jdbcType=DECIMAL}");
		sql.SET("IDTIPOCVSUBTIPO1 = #{record.idtipocvsubtipo1,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
		CenTiposcvsubtipo1Example example = (CenTiposcvsubtipo1Example) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	public String updateByPrimaryKeySelective(CenTiposcvsubtipo1 record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_TIPOSCVSUBTIPO1");
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechaBaja() != null) {
			sql.SET("FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}");
		sql.WHERE("IDTIPOCVSUBTIPO1 = #{idtipocvsubtipo1,jdbcType=DECIMAL}");
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO1
	 * @mbg.generated  Tue Oct 23 10:50:16 CEST 2018
	 */
	protected void applyWhere(SQL sql, CenTiposcvsubtipo1Example example, boolean includeExamplePhrase) {
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