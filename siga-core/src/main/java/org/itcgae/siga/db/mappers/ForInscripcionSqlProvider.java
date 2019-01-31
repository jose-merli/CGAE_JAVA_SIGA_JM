package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample.Criteria;
import org.itcgae.siga.db.entities.ForInscripcionExample.Criterion;
import org.itcgae.siga.db.entities.ForInscripcionExample;

public class ForInscripcionSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String countByExample(ForInscripcionExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("FOR_INSCRIPCION");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String deleteByExample(ForInscripcionExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("FOR_INSCRIPCION");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String insertSelective(ForInscripcion record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("FOR_INSCRIPCION");
		sql.VALUES("IDINSCRIPCION", "#{idinscripcion,jdbcType=DECIMAL}");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoinscripcion() != null) {
			sql.VALUES("IDESTADOINSCRIPCION", "#{idestadoinscripcion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.VALUES("FECHASOLICITUD", "#{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getCalificacion() != null) {
			sql.VALUES("CALIFICACION", "#{calificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcurso() != null) {
			sql.VALUES("IDCURSO", "#{idcurso,jdbcType=DECIMAL}");
		}
		if (record.getIdcalificacion() != null) {
			sql.VALUES("IDCALIFICACION", "#{idcalificacion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getPagada() != null) {
			sql.VALUES("PAGADA", "#{pagada,jdbcType=DECIMAL}");
		}
		if (record.getIdcargainscripcion() != null) {
			sql.VALUES("IDCARGAINSCRIPCION", "#{idcargainscripcion,jdbcType=DECIMAL}");
		}
		if (record.getEmitircertificado() != null) {
			sql.VALUES("EMITIRCERTIFICADO", "#{emitircertificado,jdbcType=DECIMAL}");
		}
		if (record.getCertificadoemitido() != null) {
			sql.VALUES("CERTIFICADOEMITIDO", "#{certificadoemitido,jdbcType=DECIMAL}");
		}
		if (record.getIdpeticionsuscripcion() != null) {
			sql.VALUES("IDPETICIONSUSCRIPCION", "#{idpeticionsuscripcion,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String selectByExample(ForInscripcionExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSCRIPCION");
		} else {
			sql.SELECT("IDINSCRIPCION");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDESTADOINSCRIPCION");
		sql.SELECT("FECHASOLICITUD");
		sql.SELECT("CALIFICACION");
		sql.SELECT("IDPERSONA");
		sql.SELECT("IDCURSO");
		sql.SELECT("IDCALIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("FECHABAJA");
		sql.SELECT("PAGADA");
		sql.SELECT("IDCARGAINSCRIPCION");
		sql.SELECT("EMITIRCERTIFICADO");
		sql.SELECT("CERTIFICADOEMITIDO");
		sql.SELECT("IDPETICIONSUSCRIPCION");
		sql.FROM("FOR_INSCRIPCION");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ForInscripcion record = (ForInscripcion) parameter.get("record");
		ForInscripcionExample example = (ForInscripcionExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("FOR_INSCRIPCION");
		if (record.getIdinscripcion() != null) {
			sql.SET("IDINSCRIPCION = #{record.idinscripcion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoinscripcion() != null) {
			sql.SET("IDESTADOINSCRIPCION = #{record.idestadoinscripcion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getCalificacion() != null) {
			sql.SET("CALIFICACION = #{record.calificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcurso() != null) {
			sql.SET("IDCURSO = #{record.idcurso,jdbcType=DECIMAL}");
		}
		if (record.getIdcalificacion() != null) {
			sql.SET("IDCALIFICACION = #{record.idcalificacion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getPagada() != null) {
			sql.SET("PAGADA = #{record.pagada,jdbcType=DECIMAL}");
		}
		if (record.getIdcargainscripcion() != null) {
			sql.SET("IDCARGAINSCRIPCION = #{record.idcargainscripcion,jdbcType=DECIMAL}");
		}
		if (record.getEmitircertificado() != null) {
			sql.SET("EMITIRCERTIFICADO = #{record.emitircertificado,jdbcType=DECIMAL}");
		}
		if (record.getCertificadoemitido() != null) {
			sql.SET("CERTIFICADOEMITIDO = #{record.certificadoemitido,jdbcType=DECIMAL}");
		}
		if (record.getIdpeticionsuscripcion() != null) {
			sql.SET("IDPETICIONSUSCRIPCION = #{record.idpeticionsuscripcion,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("FOR_INSCRIPCION");
		sql.SET("IDINSCRIPCION = #{record.idinscripcion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDESTADOINSCRIPCION = #{record.idestadoinscripcion,jdbcType=DECIMAL}");
		sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		sql.SET("CALIFICACION = #{record.calificacion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDCURSO = #{record.idcurso,jdbcType=DECIMAL}");
		sql.SET("IDCALIFICACION = #{record.idcalificacion,jdbcType=DECIMAL}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		sql.SET("PAGADA = #{record.pagada,jdbcType=DECIMAL}");
		sql.SET("IDCARGAINSCRIPCION = #{record.idcargainscripcion,jdbcType=DECIMAL}");
		sql.SET("EMITIRCERTIFICADO = #{record.emitircertificado,jdbcType=DECIMAL}");
		sql.SET("CERTIFICADOEMITIDO = #{record.certificadoemitido,jdbcType=DECIMAL}");
		sql.SET("IDPETICIONSUSCRIPCION = #{record.idpeticionsuscripcion,jdbcType=DECIMAL}");
		ForInscripcionExample example = (ForInscripcionExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	public String updateByPrimaryKeySelective(ForInscripcion record) {
		SQL sql = new SQL();
		sql.UPDATE("FOR_INSCRIPCION");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoinscripcion() != null) {
			sql.SET("IDESTADOINSCRIPCION = #{idestadoinscripcion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getCalificacion() != null) {
			sql.SET("CALIFICACION = #{calificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdcurso() != null) {
			sql.SET("IDCURSO = #{idcurso,jdbcType=DECIMAL}");
		}
		if (record.getIdcalificacion() != null) {
			sql.SET("IDCALIFICACION = #{idcalificacion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getPagada() != null) {
			sql.SET("PAGADA = #{pagada,jdbcType=DECIMAL}");
		}
		if (record.getIdcargainscripcion() != null) {
			sql.SET("IDCARGAINSCRIPCION = #{idcargainscripcion,jdbcType=DECIMAL}");
		}
		if (record.getEmitircertificado() != null) {
			sql.SET("EMITIRCERTIFICADO = #{emitircertificado,jdbcType=DECIMAL}");
		}
		if (record.getCertificadoemitido() != null) {
			sql.SET("CERTIFICADOEMITIDO = #{certificadoemitido,jdbcType=DECIMAL}");
		}
		if (record.getIdpeticionsuscripcion() != null) {
			sql.SET("IDPETICIONSUSCRIPCION = #{idpeticionsuscripcion,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDINSCRIPCION = #{idinscripcion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCION
	 * @mbg.generated  Thu Jan 10 14:40:28 CET 2019
	 */
	protected void applyWhere(SQL sql, ForInscripcionExample example, boolean includeExamplePhrase) {
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