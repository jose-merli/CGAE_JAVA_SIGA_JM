package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenAcPeticion;
import org.itcgae.siga.db.entities.CenAcPeticionExample;
import org.itcgae.siga.db.entities.CenAcPeticionExample.Criteria;
import org.itcgae.siga.db.entities.CenAcPeticionExample.Criterion;

public class CenAcPeticionSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenAcPeticionExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_AC_PETICION");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenAcPeticionExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_AC_PETICION");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenAcPeticion record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_AC_PETICION");
		if (record.getIdcenacpeticion() != null) {
			sql.VALUES("IDCENACPETICION", "#{idcenacpeticion,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacxmlrecibida() != null) {
			sql.VALUES("IDCENACXMLRECIBIDA", "#{idcenacxmlrecibida,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacxmlnovalido() != null) {
			sql.VALUES("IDCENACXMLNOVALIDO", "#{idcenacxmlnovalido,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacips() != null) {
			sql.VALUES("IDCENACIPS", "#{idcenacips,jdbcType=DECIMAL}");
		}
		if (record.getIpRecibida() != null) {
			sql.VALUES("IP_RECIBIDA", "#{ipRecibida,jdbcType=VARCHAR}");
		}
		if (record.getTipodocumento() != null) {
			sql.VALUES("TIPODOCUMENTO", "#{tipodocumento,jdbcType=VARCHAR}");
		}
		if (record.getNumdoc() != null) {
			sql.VALUES("NUMDOC", "#{numdoc,jdbcType=VARCHAR}");
		}
		if (record.getCodcolegio() != null) {
			sql.VALUES("CODCOLEGIO", "#{codcolegio,jdbcType=VARCHAR}");
		}
		if (record.getDesccolegio() != null) {
			sql.VALUES("DESCCOLEGIO", "#{desccolegio,jdbcType=VARCHAR}");
		}
		if (record.getNumcolegiado() != null) {
			sql.VALUES("NUMCOLEGIADO", "#{numcolegiado,jdbcType=VARCHAR}");
		}
		if (record.getFechapeticion() != null) {
			sql.VALUES("FECHAPETICION", "#{fechapeticion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenAcPeticionExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDCENACPETICION");
		} else {
			sql.SELECT("IDCENACPETICION");
		}
		sql.SELECT("IDCENACXMLRECIBIDA");
		sql.SELECT("IDCENACXMLNOVALIDO");
		sql.SELECT("IDCENACIPS");
		sql.SELECT("IP_RECIBIDA");
		sql.SELECT("TIPODOCUMENTO");
		sql.SELECT("NUMDOC");
		sql.SELECT("CODCOLEGIO");
		sql.SELECT("DESCCOLEGIO");
		sql.SELECT("NUMCOLEGIADO");
		sql.SELECT("FECHAPETICION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.FROM("CEN_AC_PETICION");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenAcPeticion record = (CenAcPeticion) parameter.get("record");
		CenAcPeticionExample example = (CenAcPeticionExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_PETICION");
		if (record.getIdcenacpeticion() != null) {
			sql.SET("IDCENACPETICION = #{record.idcenacpeticion,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacxmlrecibida() != null) {
			sql.SET("IDCENACXMLRECIBIDA = #{record.idcenacxmlrecibida,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacxmlnovalido() != null) {
			sql.SET("IDCENACXMLNOVALIDO = #{record.idcenacxmlnovalido,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacips() != null) {
			sql.SET("IDCENACIPS = #{record.idcenacips,jdbcType=DECIMAL}");
		}
		if (record.getIpRecibida() != null) {
			sql.SET("IP_RECIBIDA = #{record.ipRecibida,jdbcType=VARCHAR}");
		}
		if (record.getTipodocumento() != null) {
			sql.SET("TIPODOCUMENTO = #{record.tipodocumento,jdbcType=VARCHAR}");
		}
		if (record.getNumdoc() != null) {
			sql.SET("NUMDOC = #{record.numdoc,jdbcType=VARCHAR}");
		}
		if (record.getCodcolegio() != null) {
			sql.SET("CODCOLEGIO = #{record.codcolegio,jdbcType=VARCHAR}");
		}
		if (record.getDesccolegio() != null) {
			sql.SET("DESCCOLEGIO = #{record.desccolegio,jdbcType=VARCHAR}");
		}
		if (record.getNumcolegiado() != null) {
			sql.SET("NUMCOLEGIADO = #{record.numcolegiado,jdbcType=VARCHAR}");
		}
		if (record.getFechapeticion() != null) {
			sql.SET("FECHAPETICION = #{record.fechapeticion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_PETICION");
		sql.SET("IDCENACPETICION = #{record.idcenacpeticion,jdbcType=DECIMAL}");
		sql.SET("IDCENACXMLRECIBIDA = #{record.idcenacxmlrecibida,jdbcType=DECIMAL}");
		sql.SET("IDCENACXMLNOVALIDO = #{record.idcenacxmlnovalido,jdbcType=DECIMAL}");
		sql.SET("IDCENACIPS = #{record.idcenacips,jdbcType=DECIMAL}");
		sql.SET("IP_RECIBIDA = #{record.ipRecibida,jdbcType=VARCHAR}");
		sql.SET("TIPODOCUMENTO = #{record.tipodocumento,jdbcType=VARCHAR}");
		sql.SET("NUMDOC = #{record.numdoc,jdbcType=VARCHAR}");
		sql.SET("CODCOLEGIO = #{record.codcolegio,jdbcType=VARCHAR}");
		sql.SET("DESCCOLEGIO = #{record.desccolegio,jdbcType=VARCHAR}");
		sql.SET("NUMCOLEGIADO = #{record.numcolegiado,jdbcType=VARCHAR}");
		sql.SET("FECHAPETICION = #{record.fechapeticion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		CenAcPeticionExample example = (CenAcPeticionExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenAcPeticion record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_AC_PETICION");
		if (record.getIdcenacxmlrecibida() != null) {
			sql.SET("IDCENACXMLRECIBIDA = #{idcenacxmlrecibida,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacxmlnovalido() != null) {
			sql.SET("IDCENACXMLNOVALIDO = #{idcenacxmlnovalido,jdbcType=DECIMAL}");
		}
		if (record.getIdcenacips() != null) {
			sql.SET("IDCENACIPS = #{idcenacips,jdbcType=DECIMAL}");
		}
		if (record.getIpRecibida() != null) {
			sql.SET("IP_RECIBIDA = #{ipRecibida,jdbcType=VARCHAR}");
		}
		if (record.getTipodocumento() != null) {
			sql.SET("TIPODOCUMENTO = #{tipodocumento,jdbcType=VARCHAR}");
		}
		if (record.getNumdoc() != null) {
			sql.SET("NUMDOC = #{numdoc,jdbcType=VARCHAR}");
		}
		if (record.getCodcolegio() != null) {
			sql.SET("CODCOLEGIO = #{codcolegio,jdbcType=VARCHAR}");
		}
		if (record.getDesccolegio() != null) {
			sql.SET("DESCCOLEGIO = #{desccolegio,jdbcType=VARCHAR}");
		}
		if (record.getNumcolegiado() != null) {
			sql.SET("NUMCOLEGIADO = #{numcolegiado,jdbcType=VARCHAR}");
		}
		if (record.getFechapeticion() != null) {
			sql.SET("FECHAPETICION = #{fechapeticion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDCENACPETICION = #{idcenacpeticion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenAcPeticionExample example, boolean includeExamplePhrase) {
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