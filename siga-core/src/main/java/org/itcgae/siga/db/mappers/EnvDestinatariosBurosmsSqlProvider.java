package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EnvDestinatariosBurosms;
import org.itcgae.siga.db.entities.EnvDestinatariosBurosmsExample;
import org.itcgae.siga.db.entities.EnvDestinatariosBurosmsExample.Criteria;
import org.itcgae.siga.db.entities.EnvDestinatariosBurosmsExample.Criterion;

public class EnvDestinatariosBurosmsSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String countByExample(EnvDestinatariosBurosmsExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("ENV_DESTINATARIOS_BUROSMS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String deleteByExample(EnvDestinatariosBurosmsExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("ENV_DESTINATARIOS_BUROSMS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String insertSelective(EnvDestinatariosBurosms record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("ENV_DESTINATARIOS_BUROSMS");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=NUMERIC}");
		}
		if (record.getIdenvio() != null) {
			sql.VALUES("IDENVIO", "#{idenvio,jdbcType=NUMERIC}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=NUMERIC}");
		}
		if (record.getMovil() != null) {
			sql.VALUES("MOVIL", "#{movil,jdbcType=VARCHAR}");
		}
		if (record.getIdsolicitudecos() != null) {
			sql.VALUES("IDSOLICITUDECOS", "#{idsolicitudecos,jdbcType=VARCHAR}");
		}
		if (record.getCsv() != null) {
			sql.VALUES("CSV", "#{csv,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=NUMERIC}");
		}
		if (record.getIddocumento() != null) {
			sql.VALUES("IDDOCUMENTO", "#{iddocumento,jdbcType=NUMERIC}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String selectByExample(EnvDestinatariosBurosmsExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDENVIO");
		sql.SELECT("IDPERSONA");
		sql.SELECT("MOVIL");
		sql.SELECT("IDSOLICITUDECOS");
		sql.SELECT("CSV");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDDOCUMENTO");
		sql.FROM("ENV_DESTINATARIOS_BUROSMS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		EnvDestinatariosBurosms record = (EnvDestinatariosBurosms) parameter.get("record");
		EnvDestinatariosBurosmsExample example = (EnvDestinatariosBurosmsExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("ENV_DESTINATARIOS_BUROSMS");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=NUMERIC}");
		}
		if (record.getIdenvio() != null) {
			sql.SET("IDENVIO = #{record.idenvio,jdbcType=NUMERIC}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=NUMERIC}");
		}
		if (record.getMovil() != null) {
			sql.SET("MOVIL = #{record.movil,jdbcType=VARCHAR}");
		}
		if (record.getIdsolicitudecos() != null) {
			sql.SET("IDSOLICITUDECOS = #{record.idsolicitudecos,jdbcType=VARCHAR}");
		}
		if (record.getCsv() != null) {
			sql.SET("CSV = #{record.csv,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=NUMERIC}");
		}
		if (record.getIddocumento() != null) {
			sql.SET("IDDOCUMENTO = #{record.iddocumento,jdbcType=NUMERIC}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ENV_DESTINATARIOS_BUROSMS");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=NUMERIC}");
		sql.SET("IDENVIO = #{record.idenvio,jdbcType=NUMERIC}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=NUMERIC}");
		sql.SET("MOVIL = #{record.movil,jdbcType=VARCHAR}");
		sql.SET("IDSOLICITUDECOS = #{record.idsolicitudecos,jdbcType=VARCHAR}");
		sql.SET("CSV = #{record.csv,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=NUMERIC}");
		sql.SET("IDDOCUMENTO = #{record.iddocumento,jdbcType=NUMERIC}");
		EnvDestinatariosBurosmsExample example = (EnvDestinatariosBurosmsExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	public String updateByPrimaryKeySelective(EnvDestinatariosBurosms record) {
		SQL sql = new SQL();
		sql.UPDATE("ENV_DESTINATARIOS_BUROSMS");
		if (record.getMovil() != null) {
			sql.SET("MOVIL = #{movil,jdbcType=VARCHAR}");
		}
		if (record.getIdsolicitudecos() != null) {
			sql.SET("IDSOLICITUDECOS = #{idsolicitudecos,jdbcType=VARCHAR}");
		}
		if (record.getCsv() != null) {
			sql.SET("CSV = #{csv,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=NUMERIC}");
		}
		if (record.getIddocumento() != null) {
			sql.SET("IDDOCUMENTO = #{iddocumento,jdbcType=NUMERIC}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=NUMERIC}");
		sql.WHERE("IDENVIO = #{idenvio,jdbcType=NUMERIC}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=NUMERIC}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DESTINATARIOS_BUROSMS
	 * @mbg.generated  Fri Apr 12 10:30:51 CEST 2024
	 */
	protected void applyWhere(SQL sql, EnvDestinatariosBurosmsExample example, boolean includeExamplePhrase) {
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