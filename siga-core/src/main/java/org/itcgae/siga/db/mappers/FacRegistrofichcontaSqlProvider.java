package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FacRegistrofichconta;
import org.itcgae.siga.db.entities.FacRegistrofichcontaExample.Criteria;
import org.itcgae.siga.db.entities.FacRegistrofichcontaExample.Criterion;
import org.itcgae.siga.db.entities.FacRegistrofichcontaExample;

public class FacRegistrofichcontaSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String countByExample(FacRegistrofichcontaExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("FAC_REGISTROFICHCONTA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String deleteByExample(FacRegistrofichcontaExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("FAC_REGISTROFICHCONTA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String insertSelective(FacRegistrofichconta record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("FAC_REGISTROFICHCONTA");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdcontabilidad() != null) {
			sql.VALUES("IDCONTABILIDAD", "#{idcontabilidad,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.VALUES("FECHACREACION", "#{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechadesde() != null) {
			sql.VALUES("FECHADESDE", "#{fechadesde,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahasta() != null) {
			sql.VALUES("FECHAHASTA", "#{fechahasta,jdbcType=TIMESTAMP}");
		}
		if (record.getNumeroasientos() != null) {
			sql.VALUES("NUMEROASIENTOS", "#{numeroasientos,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getNombrefichero() != null) {
			sql.VALUES("NOMBREFICHERO", "#{nombrefichero,jdbcType=VARCHAR}");
		}
		if (record.getEstado() != null) {
			sql.VALUES("ESTADO", "#{estado,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String selectByExample(FacRegistrofichcontaExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDCONTABILIDAD");
		sql.SELECT("FECHACREACION");
		sql.SELECT("FECHADESDE");
		sql.SELECT("FECHAHASTA");
		sql.SELECT("NUMEROASIENTOS");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("NOMBREFICHERO");
		sql.SELECT("ESTADO");
		sql.SELECT("FECHABAJA");
		sql.FROM("FAC_REGISTROFICHCONTA");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		FacRegistrofichconta record = (FacRegistrofichconta) parameter.get("record");
		FacRegistrofichcontaExample example = (FacRegistrofichcontaExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("FAC_REGISTROFICHCONTA");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdcontabilidad() != null) {
			sql.SET("IDCONTABILIDAD = #{record.idcontabilidad,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechadesde() != null) {
			sql.SET("FECHADESDE = #{record.fechadesde,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahasta() != null) {
			sql.SET("FECHAHASTA = #{record.fechahasta,jdbcType=TIMESTAMP}");
		}
		if (record.getNumeroasientos() != null) {
			sql.SET("NUMEROASIENTOS = #{record.numeroasientos,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getNombrefichero() != null) {
			sql.SET("NOMBREFICHERO = #{record.nombrefichero,jdbcType=VARCHAR}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("FAC_REGISTROFICHCONTA");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDCONTABILIDAD = #{record.idcontabilidad,jdbcType=DECIMAL}");
		sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		sql.SET("FECHADESDE = #{record.fechadesde,jdbcType=TIMESTAMP}");
		sql.SET("FECHAHASTA = #{record.fechahasta,jdbcType=TIMESTAMP}");
		sql.SET("NUMEROASIENTOS = #{record.numeroasientos,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("NOMBREFICHERO = #{record.nombrefichero,jdbcType=VARCHAR}");
		sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		FacRegistrofichcontaExample example = (FacRegistrofichcontaExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	public String updateByPrimaryKeySelective(FacRegistrofichconta record) {
		SQL sql = new SQL();
		sql.UPDATE("FAC_REGISTROFICHCONTA");
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechadesde() != null) {
			sql.SET("FECHADESDE = #{fechadesde,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahasta() != null) {
			sql.SET("FECHAHASTA = #{fechahasta,jdbcType=TIMESTAMP}");
		}
		if (record.getNumeroasientos() != null) {
			sql.SET("NUMEROASIENTOS = #{numeroasientos,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getNombrefichero() != null) {
			sql.SET("NOMBREFICHERO = #{nombrefichero,jdbcType=VARCHAR}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{estado,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDCONTABILIDAD = #{idcontabilidad,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_REGISTROFICHCONTA
	 * @mbg.generated  Tue Jan 25 09:48:46 CET 2022
	 */
	protected void applyWhere(SQL sql, FacRegistrofichcontaExample example, boolean includeExamplePhrase) {
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