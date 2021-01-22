package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenPoblacionesExample;
import org.itcgae.siga.db.entities.CenPoblacionesExample.Criteria;
import org.itcgae.siga.db.entities.CenPoblacionesExample.Criterion;

public class CenPoblacionesSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String countByExample(CenPoblacionesExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_POBLACIONES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String deleteByExample(CenPoblacionesExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_POBLACIONES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String insertSelective(CenPoblaciones record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_POBLACIONES");
		if (record.getIdpoblacion() != null) {
			sql.VALUES("IDPOBLACION", "#{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpartido() != null) {
			sql.VALUES("IDPARTIDO", "#{idpartido,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getIne() != null) {
			sql.VALUES("INE", "#{ine,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacionmunicipio() != null) {
			sql.VALUES("IDPOBLACIONMUNICIPIO", "#{idpoblacionmunicipio,jdbcType=VARCHAR}");
		}
		if (record.getPrioridad() != null) {
			sql.VALUES("PRIORIDAD", "#{prioridad,jdbcType=DECIMAL}");
		}
		if (record.getSedejudicial() != null) {
			sql.VALUES("SEDEJUDICIAL", "#{sedejudicial,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String selectByExample(CenPoblacionesExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPOBLACION");
		} else {
			sql.SELECT("IDPOBLACION");
		}
		sql.SELECT("IDPROVINCIA");
		sql.SELECT("NOMBRE");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDPARTIDO");
		sql.SELECT("CODIGOEXT");
		sql.SELECT("INE");
		sql.SELECT("IDPOBLACIONMUNICIPIO");
		sql.SELECT("PRIORIDAD");
		sql.SELECT("SEDEJUDICIAL");
		sql.FROM("CEN_POBLACIONES");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenPoblaciones record = (CenPoblaciones) parameter.get("record");
		CenPoblacionesExample example = (CenPoblacionesExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_POBLACIONES");
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpartido() != null) {
			sql.SET("IDPARTIDO = #{record.idpartido,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		}
		if (record.getIne() != null) {
			sql.SET("INE = #{record.ine,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacionmunicipio() != null) {
			sql.SET("IDPOBLACIONMUNICIPIO = #{record.idpoblacionmunicipio,jdbcType=VARCHAR}");
		}
		if (record.getPrioridad() != null) {
			sql.SET("PRIORIDAD = #{record.prioridad,jdbcType=DECIMAL}");
		}
		if (record.getSedejudicial() != null) {
			sql.SET("SEDEJUDICIAL = #{record.sedejudicial,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_POBLACIONES");
		sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDPARTIDO = #{record.idpartido,jdbcType=DECIMAL}");
		sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		sql.SET("INE = #{record.ine,jdbcType=VARCHAR}");
		sql.SET("IDPOBLACIONMUNICIPIO = #{record.idpoblacionmunicipio,jdbcType=VARCHAR}");
		sql.SET("PRIORIDAD = #{record.prioridad,jdbcType=DECIMAL}");
		sql.SET("SEDEJUDICIAL = #{record.sedejudicial,jdbcType=DECIMAL}");
		CenPoblacionesExample example = (CenPoblacionesExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenPoblaciones record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_POBLACIONES");
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpartido() != null) {
			sql.SET("IDPARTIDO = #{idpartido,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getIne() != null) {
			sql.SET("INE = #{ine,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacionmunicipio() != null) {
			sql.SET("IDPOBLACIONMUNICIPIO = #{idpoblacionmunicipio,jdbcType=VARCHAR}");
		}
		if (record.getPrioridad() != null) {
			sql.SET("PRIORIDAD = #{prioridad,jdbcType=DECIMAL}");
		}
		if (record.getSedejudicial() != null) {
			sql.SET("SEDEJUDICIAL = #{sedejudicial,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_POBLACIONES
	 * @mbg.generated  Wed Nov 28 12:20:12 CET 2018
	 */
	protected void applyWhere(SQL sql, CenPoblacionesExample example, boolean includeExamplePhrase) {
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