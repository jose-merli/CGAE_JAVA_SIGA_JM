package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenComponentes;
import org.itcgae.siga.db.entities.CenComponentesExample.Criteria;
import org.itcgae.siga.db.entities.CenComponentesExample.Criterion;
import org.itcgae.siga.db.entities.CenComponentesExample;

public class CenComponentesSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenComponentesExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_COMPONENTES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenComponentesExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_COMPONENTES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenComponentes record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_COMPONENTES");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcomponente() != null) {
			sql.VALUES("IDCOMPONENTE", "#{idcomponente,jdbcType=DECIMAL}");
		}
		if (record.getCargo() != null) {
			sql.VALUES("CARGO", "#{cargo,jdbcType=VARCHAR}");
		}
		if (record.getFechacargo() != null) {
			sql.VALUES("FECHACARGO", "#{fechacargo,jdbcType=TIMESTAMP}");
		}
		if (record.getCenClienteIdpersona() != null) {
			sql.VALUES("CEN_CLIENTE_IDPERSONA", "#{cenClienteIdpersona,jdbcType=DECIMAL}");
		}
		if (record.getCenClienteIdinstitucion() != null) {
			sql.VALUES("CEN_CLIENTE_IDINSTITUCION", "#{cenClienteIdinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getSociedad() != null) {
			sql.VALUES("SOCIEDAD", "#{sociedad,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdcuenta() != null) {
			sql.VALUES("IDCUENTA", "#{idcuenta,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocolegio() != null) {
			sql.VALUES("IDTIPOCOLEGIO", "#{idtipocolegio,jdbcType=DECIMAL}");
		}
		if (record.getIdprovincia() != null) {
			sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getNumcolegiado() != null) {
			sql.VALUES("NUMCOLEGIADO", "#{numcolegiado,jdbcType=VARCHAR}");
		}
		if (record.getCapitalsocial() != null) {
			sql.VALUES("CAPITALSOCIAL", "#{capitalsocial,jdbcType=DECIMAL}");
		}
		if (record.getIdcargo() != null) {
			sql.VALUES("IDCARGO", "#{idcargo,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenComponentesExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPERSONA");
		sql.SELECT("IDCOMPONENTE");
		sql.SELECT("CARGO");
		sql.SELECT("FECHACARGO");
		sql.SELECT("CEN_CLIENTE_IDPERSONA");
		sql.SELECT("CEN_CLIENTE_IDINSTITUCION");
		sql.SELECT("SOCIEDAD");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDCUENTA");
		sql.SELECT("IDTIPOCOLEGIO");
		sql.SELECT("IDPROVINCIA");
		sql.SELECT("NUMCOLEGIADO");
		sql.SELECT("CAPITALSOCIAL");
		sql.SELECT("IDCARGO");
		sql.SELECT("FECHABAJA");
		sql.FROM("CEN_COMPONENTES");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenComponentes record = (CenComponentes) parameter.get("record");
		CenComponentesExample example = (CenComponentesExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_COMPONENTES");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcomponente() != null) {
			sql.SET("IDCOMPONENTE = #{record.idcomponente,jdbcType=DECIMAL}");
		}
		if (record.getCargo() != null) {
			sql.SET("CARGO = #{record.cargo,jdbcType=VARCHAR}");
		}
		if (record.getFechacargo() != null) {
			sql.SET("FECHACARGO = #{record.fechacargo,jdbcType=TIMESTAMP}");
		}
		if (record.getCenClienteIdpersona() != null) {
			sql.SET("CEN_CLIENTE_IDPERSONA = #{record.cenClienteIdpersona,jdbcType=DECIMAL}");
		}
		if (record.getCenClienteIdinstitucion() != null) {
			sql.SET("CEN_CLIENTE_IDINSTITUCION = #{record.cenClienteIdinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getSociedad() != null) {
			sql.SET("SOCIEDAD = #{record.sociedad,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdcuenta() != null) {
			sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocolegio() != null) {
			sql.SET("IDTIPOCOLEGIO = #{record.idtipocolegio,jdbcType=DECIMAL}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getNumcolegiado() != null) {
			sql.SET("NUMCOLEGIADO = #{record.numcolegiado,jdbcType=VARCHAR}");
		}
		if (record.getCapitalsocial() != null) {
			sql.SET("CAPITALSOCIAL = #{record.capitalsocial,jdbcType=DECIMAL}");
		}
		if (record.getIdcargo() != null) {
			sql.SET("IDCARGO = #{record.idcargo,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_COMPONENTES");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDCOMPONENTE = #{record.idcomponente,jdbcType=DECIMAL}");
		sql.SET("CARGO = #{record.cargo,jdbcType=VARCHAR}");
		sql.SET("FECHACARGO = #{record.fechacargo,jdbcType=TIMESTAMP}");
		sql.SET("CEN_CLIENTE_IDPERSONA = #{record.cenClienteIdpersona,jdbcType=DECIMAL}");
		sql.SET("CEN_CLIENTE_IDINSTITUCION = #{record.cenClienteIdinstitucion,jdbcType=DECIMAL}");
		sql.SET("SOCIEDAD = #{record.sociedad,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
		sql.SET("IDTIPOCOLEGIO = #{record.idtipocolegio,jdbcType=DECIMAL}");
		sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		sql.SET("NUMCOLEGIADO = #{record.numcolegiado,jdbcType=VARCHAR}");
		sql.SET("CAPITALSOCIAL = #{record.capitalsocial,jdbcType=DECIMAL}");
		sql.SET("IDCARGO = #{record.idcargo,jdbcType=DECIMAL}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		CenComponentesExample example = (CenComponentesExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenComponentes record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_COMPONENTES");
		if (record.getCargo() != null) {
			sql.SET("CARGO = #{cargo,jdbcType=VARCHAR}");
		}
		if (record.getFechacargo() != null) {
			sql.SET("FECHACARGO = #{fechacargo,jdbcType=TIMESTAMP}");
		}
		if (record.getCenClienteIdpersona() != null) {
			sql.SET("CEN_CLIENTE_IDPERSONA = #{cenClienteIdpersona,jdbcType=DECIMAL}");
		}
		if (record.getCenClienteIdinstitucion() != null) {
			sql.SET("CEN_CLIENTE_IDINSTITUCION = #{cenClienteIdinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getSociedad() != null) {
			sql.SET("SOCIEDAD = #{sociedad,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdcuenta() != null) {
			sql.SET("IDCUENTA = #{idcuenta,jdbcType=DECIMAL}");
		}
		if (record.getIdtipocolegio() != null) {
			sql.SET("IDTIPOCOLEGIO = #{idtipocolegio,jdbcType=DECIMAL}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getNumcolegiado() != null) {
			sql.SET("NUMCOLEGIADO = #{numcolegiado,jdbcType=VARCHAR}");
		}
		if (record.getCapitalsocial() != null) {
			sql.SET("CAPITALSOCIAL = #{capitalsocial,jdbcType=DECIMAL}");
		}
		if (record.getIdcargo() != null) {
			sql.SET("IDCARGO = #{idcargo,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		sql.WHERE("IDCOMPONENTE = #{idcomponente,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMPONENTES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenComponentesExample example, boolean includeExamplePhrase) {
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