package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancarias;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasExample.Criteria;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasExample.Criterion;

public class CenAnexosCuentasbancariasSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenAnexosCuentasbancariasExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_ANEXOS_CUENTASBANCARIAS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenAnexosCuentasbancariasExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_ANEXOS_CUENTASBANCARIAS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenAnexosCuentasbancarias record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_ANEXOS_CUENTASBANCARIAS");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcuenta() != null) {
			sql.VALUES("IDCUENTA", "#{idcuenta,jdbcType=DECIMAL}");
		}
		if (record.getIdmandato() != null) {
			sql.VALUES("IDMANDATO", "#{idmandato,jdbcType=DECIMAL}");
		}
		if (record.getIdanexo() != null) {
			sql.VALUES("IDANEXO", "#{idanexo,jdbcType=DECIMAL}");
		}
		if (record.getOrigen() != null) {
			sql.VALUES("ORIGEN", "#{origen,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFirmaFecha() != null) {
			sql.VALUES("FIRMA_FECHA", "#{firmaFecha,jdbcType=TIMESTAMP}");
		}
		if (record.getFirmaLugar() != null) {
			sql.VALUES("FIRMA_LUGAR", "#{firmaLugar,jdbcType=VARCHAR}");
		}
		if (record.getFechacreacion() != null) {
			sql.VALUES("FECHACREACION", "#{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.VALUES("USUCREACION", "#{usucreacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdficherofirma() != null) {
			sql.VALUES("IDFICHEROFIRMA", "#{idficherofirma,jdbcType=DECIMAL}");
		}
		if (record.getEsautomatico() != null) {
			sql.VALUES("ESAUTOMATICO", "#{esautomatico,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenAnexosCuentasbancariasExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPERSONA");
		sql.SELECT("IDCUENTA");
		sql.SELECT("IDMANDATO");
		sql.SELECT("IDANEXO");
		sql.SELECT("ORIGEN");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("FIRMA_FECHA");
		sql.SELECT("FIRMA_LUGAR");
		sql.SELECT("FECHACREACION");
		sql.SELECT("USUCREACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDFICHEROFIRMA");
		sql.SELECT("ESAUTOMATICO");
		sql.FROM("CEN_ANEXOS_CUENTASBANCARIAS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenAnexosCuentasbancarias record = (CenAnexosCuentasbancarias) parameter.get("record");
		CenAnexosCuentasbancariasExample example = (CenAnexosCuentasbancariasExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_ANEXOS_CUENTASBANCARIAS");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcuenta() != null) {
			sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
		}
		if (record.getIdmandato() != null) {
			sql.SET("IDMANDATO = #{record.idmandato,jdbcType=DECIMAL}");
		}
		if (record.getIdanexo() != null) {
			sql.SET("IDANEXO = #{record.idanexo,jdbcType=DECIMAL}");
		}
		if (record.getOrigen() != null) {
			sql.SET("ORIGEN = #{record.origen,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFirmaFecha() != null) {
			sql.SET("FIRMA_FECHA = #{record.firmaFecha,jdbcType=TIMESTAMP}");
		}
		if (record.getFirmaLugar() != null) {
			sql.SET("FIRMA_LUGAR = #{record.firmaLugar,jdbcType=VARCHAR}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.SET("USUCREACION = #{record.usucreacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdficherofirma() != null) {
			sql.SET("IDFICHEROFIRMA = #{record.idficherofirma,jdbcType=DECIMAL}");
		}
		if (record.getEsautomatico() != null) {
			sql.SET("ESAUTOMATICO = #{record.esautomatico,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_ANEXOS_CUENTASBANCARIAS");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
		sql.SET("IDMANDATO = #{record.idmandato,jdbcType=DECIMAL}");
		sql.SET("IDANEXO = #{record.idanexo,jdbcType=DECIMAL}");
		sql.SET("ORIGEN = #{record.origen,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("FIRMA_FECHA = #{record.firmaFecha,jdbcType=TIMESTAMP}");
		sql.SET("FIRMA_LUGAR = #{record.firmaLugar,jdbcType=VARCHAR}");
		sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		sql.SET("USUCREACION = #{record.usucreacion,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDFICHEROFIRMA = #{record.idficherofirma,jdbcType=DECIMAL}");
		sql.SET("ESAUTOMATICO = #{record.esautomatico,jdbcType=DECIMAL}");
		CenAnexosCuentasbancariasExample example = (CenAnexosCuentasbancariasExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenAnexosCuentasbancarias record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_ANEXOS_CUENTASBANCARIAS");
		if (record.getOrigen() != null) {
			sql.SET("ORIGEN = #{origen,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getFirmaFecha() != null) {
			sql.SET("FIRMA_FECHA = #{firmaFecha,jdbcType=TIMESTAMP}");
		}
		if (record.getFirmaLugar() != null) {
			sql.SET("FIRMA_LUGAR = #{firmaLugar,jdbcType=VARCHAR}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.SET("USUCREACION = #{usucreacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdficherofirma() != null) {
			sql.SET("IDFICHEROFIRMA = #{idficherofirma,jdbcType=DECIMAL}");
		}
		if (record.getEsautomatico() != null) {
			sql.SET("ESAUTOMATICO = #{esautomatico,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		sql.WHERE("IDCUENTA = #{idcuenta,jdbcType=DECIMAL}");
		sql.WHERE("IDMANDATO = #{idmandato,jdbcType=DECIMAL}");
		sql.WHERE("IDANEXO = #{idanexo,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ANEXOS_CUENTASBANCARIAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenAnexosCuentasbancariasExample example, boolean includeExamplePhrase) {
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