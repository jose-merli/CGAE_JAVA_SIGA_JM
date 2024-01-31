package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EcomIntercambio;
import org.itcgae.siga.db.entities.EcomIntercambioExample.Criteria;
import org.itcgae.siga.db.entities.EcomIntercambioExample.Criterion;
import org.itcgae.siga.db.entities.EcomIntercambioExample;

public class EcomIntercambioSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String countByExample(EcomIntercambioExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("ECOM_INTERCAMBIO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String deleteByExample(EcomIntercambioExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("ECOM_INTERCAMBIO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String insertSelective(EcomIntercambio record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("ECOM_INTERCAMBIO");
		if (record.getIdecomintercambio() != null) {
			sql.VALUES("IDECOMINTERCAMBIO", "#{idecomintercambio,jdbcType=NUMERIC}");
		}
		if (record.getIdecomcola() != null) {
			sql.VALUES("IDECOMCOLA", "#{idecomcola,jdbcType=NUMERIC}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFechaenvio() != null) {
			sql.VALUES("FECHAENVIO", "#{fechaenvio,jdbcType=TIMESTAMP}");
		}
		if (record.getFecharespuesta() != null) {
			sql.VALUES("FECHARESPUESTA", "#{fecharespuesta,jdbcType=TIMESTAMP}");
		}
		if (record.getIdestadorespuesta() != null) {
			sql.VALUES("IDESTADORESPUESTA", "#{idestadorespuesta,jdbcType=NUMERIC}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=NUMERIC}");
		}
		if (record.getEjgAnio() != null) {
			sql.VALUES("EJG_ANIO", "#{ejgAnio,jdbcType=NUMERIC}");
		}
		if (record.getEjgIdtipo() != null) {
			sql.VALUES("EJG_IDTIPO", "#{ejgIdtipo,jdbcType=NUMERIC}");
		}
		if (record.getEjgNumero() != null) {
			sql.VALUES("EJG_NUMERO", "#{ejgNumero,jdbcType=NUMERIC}");
		}
		if (record.getRespuesta() != null) {
			sql.VALUES("RESPUESTA", "#{respuesta,jdbcType=CLOB}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String selectByExampleWithBLOBs(EcomIntercambioExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDECOMINTERCAMBIO");
		} else {
			sql.SELECT("IDECOMINTERCAMBIO");
		}
		sql.SELECT("IDECOMCOLA");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("FECHAENVIO");
		sql.SELECT("FECHARESPUESTA");
		sql.SELECT("IDESTADORESPUESTA");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("EJG_ANIO");
		sql.SELECT("EJG_IDTIPO");
		sql.SELECT("EJG_NUMERO");
		sql.SELECT("RESPUESTA");
		sql.FROM("ECOM_INTERCAMBIO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String selectByExample(EcomIntercambioExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDECOMINTERCAMBIO");
		} else {
			sql.SELECT("IDECOMINTERCAMBIO");
		}
		sql.SELECT("IDECOMCOLA");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("FECHAENVIO");
		sql.SELECT("FECHARESPUESTA");
		sql.SELECT("IDESTADORESPUESTA");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("EJG_ANIO");
		sql.SELECT("EJG_IDTIPO");
		sql.SELECT("EJG_NUMERO");
		sql.FROM("ECOM_INTERCAMBIO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		EcomIntercambio record = (EcomIntercambio) parameter.get("record");
		EcomIntercambioExample example = (EcomIntercambioExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("ECOM_INTERCAMBIO");
		if (record.getIdecomintercambio() != null) {
			sql.SET("IDECOMINTERCAMBIO = #{record.idecomintercambio,jdbcType=NUMERIC}");
		}
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=NUMERIC}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFechaenvio() != null) {
			sql.SET("FECHAENVIO = #{record.fechaenvio,jdbcType=TIMESTAMP}");
		}
		if (record.getFecharespuesta() != null) {
			sql.SET("FECHARESPUESTA = #{record.fecharespuesta,jdbcType=TIMESTAMP}");
		}
		if (record.getIdestadorespuesta() != null) {
			sql.SET("IDESTADORESPUESTA = #{record.idestadorespuesta,jdbcType=NUMERIC}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=NUMERIC}");
		}
		if (record.getEjgAnio() != null) {
			sql.SET("EJG_ANIO = #{record.ejgAnio,jdbcType=NUMERIC}");
		}
		if (record.getEjgIdtipo() != null) {
			sql.SET("EJG_IDTIPO = #{record.ejgIdtipo,jdbcType=NUMERIC}");
		}
		if (record.getEjgNumero() != null) {
			sql.SET("EJG_NUMERO = #{record.ejgNumero,jdbcType=NUMERIC}");
		}
		if (record.getRespuesta() != null) {
			sql.SET("RESPUESTA = #{record.respuesta,jdbcType=CLOB}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ECOM_INTERCAMBIO");
		sql.SET("IDECOMINTERCAMBIO = #{record.idecomintercambio,jdbcType=NUMERIC}");
		sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=NUMERIC}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("FECHAENVIO = #{record.fechaenvio,jdbcType=TIMESTAMP}");
		sql.SET("FECHARESPUESTA = #{record.fecharespuesta,jdbcType=TIMESTAMP}");
		sql.SET("IDESTADORESPUESTA = #{record.idestadorespuesta,jdbcType=NUMERIC}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=NUMERIC}");
		sql.SET("EJG_ANIO = #{record.ejgAnio,jdbcType=NUMERIC}");
		sql.SET("EJG_IDTIPO = #{record.ejgIdtipo,jdbcType=NUMERIC}");
		sql.SET("EJG_NUMERO = #{record.ejgNumero,jdbcType=NUMERIC}");
		sql.SET("RESPUESTA = #{record.respuesta,jdbcType=CLOB}");
		EcomIntercambioExample example = (EcomIntercambioExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ECOM_INTERCAMBIO");
		sql.SET("IDECOMINTERCAMBIO = #{record.idecomintercambio,jdbcType=NUMERIC}");
		sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=NUMERIC}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("FECHAENVIO = #{record.fechaenvio,jdbcType=TIMESTAMP}");
		sql.SET("FECHARESPUESTA = #{record.fecharespuesta,jdbcType=TIMESTAMP}");
		sql.SET("IDESTADORESPUESTA = #{record.idestadorespuesta,jdbcType=NUMERIC}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=NUMERIC}");
		sql.SET("EJG_ANIO = #{record.ejgAnio,jdbcType=NUMERIC}");
		sql.SET("EJG_IDTIPO = #{record.ejgIdtipo,jdbcType=NUMERIC}");
		sql.SET("EJG_NUMERO = #{record.ejgNumero,jdbcType=NUMERIC}");
		EcomIntercambioExample example = (EcomIntercambioExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	public String updateByPrimaryKeySelective(EcomIntercambio record) {
		SQL sql = new SQL();
		sql.UPDATE("ECOM_INTERCAMBIO");
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{idecomcola,jdbcType=NUMERIC}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFechaenvio() != null) {
			sql.SET("FECHAENVIO = #{fechaenvio,jdbcType=TIMESTAMP}");
		}
		if (record.getFecharespuesta() != null) {
			sql.SET("FECHARESPUESTA = #{fecharespuesta,jdbcType=TIMESTAMP}");
		}
		if (record.getIdestadorespuesta() != null) {
			sql.SET("IDESTADORESPUESTA = #{idestadorespuesta,jdbcType=NUMERIC}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=NUMERIC}");
		}
		if (record.getEjgAnio() != null) {
			sql.SET("EJG_ANIO = #{ejgAnio,jdbcType=NUMERIC}");
		}
		if (record.getEjgIdtipo() != null) {
			sql.SET("EJG_IDTIPO = #{ejgIdtipo,jdbcType=NUMERIC}");
		}
		if (record.getEjgNumero() != null) {
			sql.SET("EJG_NUMERO = #{ejgNumero,jdbcType=NUMERIC}");
		}
		if (record.getRespuesta() != null) {
			sql.SET("RESPUESTA = #{respuesta,jdbcType=CLOB}");
		}
		sql.WHERE("IDECOMINTERCAMBIO = #{idecomintercambio,jdbcType=NUMERIC}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ECOM_INTERCAMBIO
	 * @mbg.generated  Wed Jan 31 13:32:27 CET 2024
	 */
	protected void applyWhere(SQL sql, EcomIntercambioExample example, boolean includeExamplePhrase) {
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