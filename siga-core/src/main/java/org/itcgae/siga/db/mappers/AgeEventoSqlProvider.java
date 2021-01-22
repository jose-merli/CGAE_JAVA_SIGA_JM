package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.AgeEventoExample.Criteria;
import org.itcgae.siga.db.entities.AgeEventoExample.Criterion;

public class AgeEventoSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String countByExample(AgeEventoExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("AGE_EVENTO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String deleteByExample(AgeEventoExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("AGE_EVENTO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String insertSelective(AgeEvento record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("AGE_EVENTO");
		sql.VALUES("IDEVENTO", "#{idevento,jdbcType=DECIMAL}");
		if (record.getIdcalendario() != null) {
			sql.VALUES("IDCALENDARIO", "#{idcalendario,jdbcType=DECIMAL}");
		}
		if (record.getTitulo() != null) {
			sql.VALUES("TITULO", "#{titulo,jdbcType=VARCHAR}");
		}
		if (record.getFechainicio() != null) {
			sql.VALUES("FECHAINICIO", "#{fechainicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafin() != null) {
			sql.VALUES("FECHAFIN", "#{fechafin,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getLugar() != null) {
			sql.VALUES("LUGAR", "#{lugar,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getRecursos() != null) {
			sql.VALUES("RECURSOS", "#{recursos,jdbcType=VARCHAR}");
		}
		if (record.getIdestadoevento() != null) {
			sql.VALUES("IDESTADOEVENTO", "#{idestadoevento,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoevento() != null) {
			sql.VALUES("IDTIPOEVENTO", "#{idtipoevento,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdrepeticionevento() != null) {
			sql.VALUES("IDREPETICIONEVENTO", "#{idrepeticionevento,jdbcType=DECIMAL}");
		}
		if (record.getIdeventooriginal() != null) {
			sql.VALUES("IDEVENTOORIGINAL", "#{ideventooriginal,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String selectByExample(AgeEventoExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDEVENTO");
		} else {
			sql.SELECT("IDEVENTO");
		}
		sql.SELECT("IDCALENDARIO");
		sql.SELECT("TITULO");
		sql.SELECT("FECHAINICIO");
		sql.SELECT("FECHAFIN");
		sql.SELECT("FECHABAJA");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("LUGAR");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("RECURSOS");
		sql.SELECT("IDESTADOEVENTO");
		sql.SELECT("IDTIPOEVENTO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("IDREPETICIONEVENTO");
		sql.SELECT("IDEVENTOORIGINAL");
		sql.FROM("AGE_EVENTO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		AgeEvento record = (AgeEvento) parameter.get("record");
		AgeEventoExample example = (AgeEventoExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("AGE_EVENTO");
		if (record.getIdevento() != null) {
			sql.SET("IDEVENTO = #{record.idevento,jdbcType=DECIMAL}");
		}
		if (record.getIdcalendario() != null) {
			sql.SET("IDCALENDARIO = #{record.idcalendario,jdbcType=DECIMAL}");
		}
		if (record.getTitulo() != null) {
			sql.SET("TITULO = #{record.titulo,jdbcType=VARCHAR}");
		}
		if (record.getFechainicio() != null) {
			sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafin() != null) {
			sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getLugar() != null) {
			sql.SET("LUGAR = #{record.lugar,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		}
		if (record.getRecursos() != null) {
			sql.SET("RECURSOS = #{record.recursos,jdbcType=VARCHAR}");
		}
		if (record.getIdestadoevento() != null) {
			sql.SET("IDESTADOEVENTO = #{record.idestadoevento,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoevento() != null) {
			sql.SET("IDTIPOEVENTO = #{record.idtipoevento,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdrepeticionevento() != null) {
			sql.SET("IDREPETICIONEVENTO = #{record.idrepeticionevento,jdbcType=DECIMAL}");
		}
		if (record.getIdeventooriginal() != null) {
			sql.SET("IDEVENTOORIGINAL = #{record.ideventooriginal,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("AGE_EVENTO");
		sql.SET("IDEVENTO = #{record.idevento,jdbcType=DECIMAL}");
		sql.SET("IDCALENDARIO = #{record.idcalendario,jdbcType=DECIMAL}");
		sql.SET("TITULO = #{record.titulo,jdbcType=VARCHAR}");
		sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
		sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("LUGAR = #{record.lugar,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("RECURSOS = #{record.recursos,jdbcType=VARCHAR}");
		sql.SET("IDESTADOEVENTO = #{record.idestadoevento,jdbcType=DECIMAL}");
		sql.SET("IDTIPOEVENTO = #{record.idtipoevento,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("IDREPETICIONEVENTO = #{record.idrepeticionevento,jdbcType=DECIMAL}");
		sql.SET("IDEVENTOORIGINAL = #{record.ideventooriginal,jdbcType=DECIMAL}");
		AgeEventoExample example = (AgeEventoExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String updateByPrimaryKeySelective(AgeEvento record) {
		SQL sql = new SQL();
		sql.UPDATE("AGE_EVENTO");
		if (record.getIdcalendario() != null) {
			sql.SET("IDCALENDARIO = #{idcalendario,jdbcType=DECIMAL}");
		}
		if (record.getTitulo() != null) {
			sql.SET("TITULO = #{titulo,jdbcType=VARCHAR}");
		}
		if (record.getFechainicio() != null) {
			sql.SET("FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafin() != null) {
			sql.SET("FECHAFIN = #{fechafin,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getLugar() != null) {
			sql.SET("LUGAR = #{lugar,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getRecursos() != null) {
			sql.SET("RECURSOS = #{recursos,jdbcType=VARCHAR}");
		}
		if (record.getIdestadoevento() != null) {
			sql.SET("IDESTADOEVENTO = #{idestadoevento,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoevento() != null) {
			sql.SET("IDTIPOEVENTO = #{idtipoevento,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdrepeticionevento() != null) {
			sql.SET("IDREPETICIONEVENTO = #{idrepeticionevento,jdbcType=DECIMAL}");
		}
		if (record.getIdeventooriginal() != null) {
			sql.SET("IDEVENTOORIGINAL = #{ideventooriginal,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDEVENTO = #{idevento,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	protected void applyWhere(SQL sql, AgeEventoExample example, boolean includeExamplePhrase) {
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