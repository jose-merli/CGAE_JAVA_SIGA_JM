package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample.Criteria;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample.Criterion;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;

public class CenGruposclienteClienteSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	public String countByExample(CenGruposclienteClienteExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_GRUPOSCLIENTE_CLIENTE");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	public String deleteByExample(CenGruposclienteClienteExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_GRUPOSCLIENTE_CLIENTE");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	public String insertSelective(CenGruposclienteCliente record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdgrupo() != null) {
			sql.VALUES("IDGRUPO", "#{idgrupo,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionGrupo() != null) {
			sql.VALUES("IDINSTITUCION_GRUPO", "#{idinstitucionGrupo,jdbcType=DECIMAL}");
		}
		if (record.getFechaBaja() != null) {
			sql.VALUES("FECHA_BAJA", "#{fechaBaja,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaInicio() != null) {
			sql.VALUES("FECHA_INICIO", "#{fechaInicio,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	public String selectByExample(CenGruposclienteClienteExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPERSONA");
		} else {
			sql.SELECT("IDPERSONA");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDGRUPO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDINSTITUCION_GRUPO");
		sql.SELECT("FECHA_BAJA");
		sql.SELECT("FECHA_INICIO");
		sql.FROM("CEN_GRUPOSCLIENTE_CLIENTE");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenGruposclienteCliente record = (CenGruposclienteCliente) parameter.get("record");
		CenGruposclienteClienteExample example = (CenGruposclienteClienteExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_GRUPOSCLIENTE_CLIENTE");
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdgrupo() != null) {
			sql.SET("IDGRUPO = #{record.idgrupo,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionGrupo() != null) {
			sql.SET("IDINSTITUCION_GRUPO = #{record.idinstitucionGrupo,jdbcType=DECIMAL}");
		}
		if (record.getFechaBaja() != null) {
			sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaInicio() != null) {
			sql.SET("FECHA_INICIO = #{record.fechaInicio,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDGRUPO = #{record.idgrupo,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION_GRUPO = #{record.idinstitucionGrupo,jdbcType=DECIMAL}");
		sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
		sql.SET("FECHA_INICIO = #{record.fechaInicio,jdbcType=TIMESTAMP}");
		CenGruposclienteClienteExample example = (CenGruposclienteClienteExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_GRUPOSCLIENTE_CLIENTE
	 * @mbg.generated  Wed Jun 06 18:03:15 CEST 2018
	 */
	protected void applyWhere(SQL sql, CenGruposclienteClienteExample example, boolean includeExamplePhrase) {
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