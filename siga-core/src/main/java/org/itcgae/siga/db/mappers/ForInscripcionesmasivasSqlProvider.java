package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ForInscripcionesmasivas;
import org.itcgae.siga.db.entities.ForInscripcionesmasivasExample.Criteria;
import org.itcgae.siga.db.entities.ForInscripcionesmasivasExample.Criterion;
import org.itcgae.siga.db.entities.ForInscripcionesmasivasExample;

public class ForInscripcionesmasivasSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String countByExample(ForInscripcionesmasivasExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("FOR_INSCRIPCIONESMASIVAS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String deleteByExample(ForInscripcionesmasivasExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("FOR_INSCRIPCIONESMASIVAS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String insertSelective(ForInscripcionesmasivas record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("FOR_INSCRIPCIONESMASIVAS");
		if (record.getIdcargainscripcion() != null) {
			sql.VALUES("IDCARGAINSCRIPCION", "#{idcargainscripcion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getNombrefichero() != null) {
			sql.VALUES("NOMBREFICHERO", "#{nombrefichero,jdbcType=VARCHAR}");
		}
		if (record.getNumerolineastotales() != null) {
			sql.VALUES("NUMEROLINEASTOTALES", "#{numerolineastotales,jdbcType=DECIMAL}");
		}
		if (record.getInscripcionescorrectas() != null) {
			sql.VALUES("INSCRIPCIONESCORRECTAS", "#{inscripcionescorrectas,jdbcType=DECIMAL}");
		}
		if (record.getIdcurso() != null) {
			sql.VALUES("IDCURSO", "#{idcurso,jdbcType=DECIMAL}");
		}
		if (record.getIdfichero() != null) {
			sql.VALUES("IDFICHERO", "#{idfichero,jdbcType=DECIMAL}");
		}
		if (record.getIdficherolog() != null) {
			sql.VALUES("IDFICHEROLOG", "#{idficherolog,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String selectByExample(ForInscripcionesmasivasExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDCARGAINSCRIPCION");
		} else {
			sql.SELECT("IDCARGAINSCRIPCION");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("NOMBREFICHERO");
		sql.SELECT("NUMEROLINEASTOTALES");
		sql.SELECT("INSCRIPCIONESCORRECTAS");
		sql.SELECT("IDCURSO");
		sql.SELECT("IDFICHERO");
		sql.SELECT("IDFICHEROLOG");
		sql.FROM("FOR_INSCRIPCIONESMASIVAS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ForInscripcionesmasivas record = (ForInscripcionesmasivas) parameter.get("record");
		ForInscripcionesmasivasExample example = (ForInscripcionesmasivasExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("FOR_INSCRIPCIONESMASIVAS");
		if (record.getIdcargainscripcion() != null) {
			sql.SET("IDCARGAINSCRIPCION = #{record.idcargainscripcion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getNombrefichero() != null) {
			sql.SET("NOMBREFICHERO = #{record.nombrefichero,jdbcType=VARCHAR}");
		}
		if (record.getNumerolineastotales() != null) {
			sql.SET("NUMEROLINEASTOTALES = #{record.numerolineastotales,jdbcType=DECIMAL}");
		}
		if (record.getInscripcionescorrectas() != null) {
			sql.SET("INSCRIPCIONESCORRECTAS = #{record.inscripcionescorrectas,jdbcType=DECIMAL}");
		}
		if (record.getIdcurso() != null) {
			sql.SET("IDCURSO = #{record.idcurso,jdbcType=DECIMAL}");
		}
		if (record.getIdfichero() != null) {
			sql.SET("IDFICHERO = #{record.idfichero,jdbcType=DECIMAL}");
		}
		if (record.getIdficherolog() != null) {
			sql.SET("IDFICHEROLOG = #{record.idficherolog,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("FOR_INSCRIPCIONESMASIVAS");
		sql.SET("IDCARGAINSCRIPCION = #{record.idcargainscripcion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("NOMBREFICHERO = #{record.nombrefichero,jdbcType=VARCHAR}");
		sql.SET("NUMEROLINEASTOTALES = #{record.numerolineastotales,jdbcType=DECIMAL}");
		sql.SET("INSCRIPCIONESCORRECTAS = #{record.inscripcionescorrectas,jdbcType=DECIMAL}");
		sql.SET("IDCURSO = #{record.idcurso,jdbcType=DECIMAL}");
		sql.SET("IDFICHERO = #{record.idfichero,jdbcType=DECIMAL}");
		sql.SET("IDFICHEROLOG = #{record.idficherolog,jdbcType=DECIMAL}");
		ForInscripcionesmasivasExample example = (ForInscripcionesmasivasExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	public String updateByPrimaryKeySelective(ForInscripcionesmasivas record) {
		SQL sql = new SQL();
		sql.UPDATE("FOR_INSCRIPCIONESMASIVAS");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getNombrefichero() != null) {
			sql.SET("NOMBREFICHERO = #{nombrefichero,jdbcType=VARCHAR}");
		}
		if (record.getNumerolineastotales() != null) {
			sql.SET("NUMEROLINEASTOTALES = #{numerolineastotales,jdbcType=DECIMAL}");
		}
		if (record.getInscripcionescorrectas() != null) {
			sql.SET("INSCRIPCIONESCORRECTAS = #{inscripcionescorrectas,jdbcType=DECIMAL}");
		}
		if (record.getIdcurso() != null) {
			sql.SET("IDCURSO = #{idcurso,jdbcType=DECIMAL}");
		}
		if (record.getIdfichero() != null) {
			sql.SET("IDFICHERO = #{idfichero,jdbcType=DECIMAL}");
		}
		if (record.getIdficherolog() != null) {
			sql.SET("IDFICHEROLOG = #{idficherolog,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDCARGAINSCRIPCION = #{idcargainscripcion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_INSCRIPCIONESMASIVAS
	 * @mbg.generated  Mon Jan 21 16:53:00 CET 2019
	 */
	protected void applyWhere(SQL sql, ForInscripcionesmasivasExample example, boolean includeExamplePhrase) {
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