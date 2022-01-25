package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenSancion;
import org.itcgae.siga.db.entities.CenSancionExample;
import org.itcgae.siga.db.entities.CenSancionExample.Criteria;
import org.itcgae.siga.db.entities.CenSancionExample.Criterion;

public class CenSancionSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String countByExample(CenSancionExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_SANCION");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String deleteByExample(CenSancionExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_SANCION");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String insertSelective(CenSancion record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_SANCION");
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdsancion() != null) {
			sql.VALUES("IDSANCION", "#{idsancion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdtiposancion() != null) {
			sql.VALUES("IDTIPOSANCION", "#{idtiposancion,jdbcType=DECIMAL}");
		}
		if (record.getRefcolegio() != null) {
			sql.VALUES("REFCOLEGIO", "#{refcolegio,jdbcType=VARCHAR}");
		}
		if (record.getRefcgae() != null) {
			sql.VALUES("REFCGAE", "#{refcgae,jdbcType=VARCHAR}");
		}
		if (record.getFecharesolucion() != null) {
			sql.VALUES("FECHARESOLUCION", "#{fecharesolucion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaacuerdo() != null) {
			sql.VALUES("FECHAACUERDO", "#{fechaacuerdo,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafirmeza() != null) {
			sql.VALUES("FECHAFIRMEZA", "#{fechafirmeza,jdbcType=TIMESTAMP}");
		}
		if (record.getFechainicio() != null) {
			sql.VALUES("FECHAINICIO", "#{fechainicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafin() != null) {
			sql.VALUES("FECHAFIN", "#{fechafin,jdbcType=TIMESTAMP}");
		}
		if (record.getFecharehabilitado() != null) {
			sql.VALUES("FECHAREHABILITADO", "#{fecharehabilitado,jdbcType=TIMESTAMP}");
		}
		if (record.getTexto() != null) {
			sql.VALUES("TEXTO", "#{texto,jdbcType=VARCHAR}");
		}
		if (record.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionsancion() != null) {
			sql.VALUES("IDINSTITUCIONSANCION", "#{idinstitucionsancion,jdbcType=DECIMAL}");
		}
		if (record.getChkrehabilitado() != null) {
			sql.VALUES("CHKREHABILITADO", "#{chkrehabilitado,jdbcType=VARCHAR}");
		}
		if (record.getChkfirmeza() != null) {
			sql.VALUES("CHKFIRMEZA", "#{chkfirmeza,jdbcType=VARCHAR}");
		}
		if (record.getFechaimposicion() != null) {
			sql.VALUES("FECHAIMPOSICION", "#{fechaimposicion,jdbcType=TIMESTAMP}");
		}
		if (record.getChkarchivada() != null) {
			sql.VALUES("CHKARCHIVADA", "#{chkarchivada,jdbcType=VARCHAR}");
		}
		if (record.getFechaarchivada() != null) {
			sql.VALUES("FECHAARCHIVADA", "#{fechaarchivada,jdbcType=TIMESTAMP}");
		}
		if (record.getFechatraspaso() != null) {
			sql.VALUES("FECHATRASPASO", "#{fechatraspaso,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaenviado() != null) {
			sql.VALUES("FECHAENVIADO", "#{fechaenviado,jdbcType=TIMESTAMP}");
		}
		if (record.getIdsancionorigen() != null) {
			sql.VALUES("IDSANCIONORIGEN", "#{idsancionorigen,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionorigen() != null) {
			sql.VALUES("IDINSTITUCIONORIGEN", "#{idinstitucionorigen,jdbcType=DECIMAL}");
		}
		if (record.getNumExpediente() != null) {
			sql.VALUES("NUM_EXPEDIENTE", "#{numExpediente,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String selectByExample(CenSancionExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPERSONA");
		} else {
			sql.SELECT("IDPERSONA");
		}
		sql.SELECT("IDSANCION");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDTIPOSANCION");
		sql.SELECT("REFCOLEGIO");
		sql.SELECT("REFCGAE");
		sql.SELECT("FECHARESOLUCION");
		sql.SELECT("FECHAACUERDO");
		sql.SELECT("FECHAFIRMEZA");
		sql.SELECT("FECHAINICIO");
		sql.SELECT("FECHAFIN");
		sql.SELECT("FECHAREHABILITADO");
		sql.SELECT("TEXTO");
		sql.SELECT("OBSERVACIONES");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDINSTITUCIONSANCION");
		sql.SELECT("CHKREHABILITADO");
		sql.SELECT("CHKFIRMEZA");
		sql.SELECT("FECHAIMPOSICION");
		sql.SELECT("CHKARCHIVADA");
		sql.SELECT("FECHAARCHIVADA");
		sql.SELECT("FECHATRASPASO");
		sql.SELECT("FECHAENVIADO");
		sql.SELECT("IDSANCIONORIGEN");
		sql.SELECT("IDINSTITUCIONORIGEN");
		sql.SELECT("NUM_EXPEDIENTE");
		sql.FROM("CEN_SANCION");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenSancion record = (CenSancion) parameter.get("record");
		CenSancionExample example = (CenSancionExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_SANCION");
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdsancion() != null) {
			sql.SET("IDSANCION = #{record.idsancion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdtiposancion() != null) {
			sql.SET("IDTIPOSANCION = #{record.idtiposancion,jdbcType=DECIMAL}");
		}
		if (record.getRefcolegio() != null) {
			sql.SET("REFCOLEGIO = #{record.refcolegio,jdbcType=VARCHAR}");
		}
		if (record.getRefcgae() != null) {
			sql.SET("REFCGAE = #{record.refcgae,jdbcType=VARCHAR}");
		}
		if (record.getFecharesolucion() != null) {
			sql.SET("FECHARESOLUCION = #{record.fecharesolucion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaacuerdo() != null) {
			sql.SET("FECHAACUERDO = #{record.fechaacuerdo,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafirmeza() != null) {
			sql.SET("FECHAFIRMEZA = #{record.fechafirmeza,jdbcType=TIMESTAMP}");
		}
		if (record.getFechainicio() != null) {
			sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafin() != null) {
			sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
		}
		if (record.getFecharehabilitado() != null) {
			sql.SET("FECHAREHABILITADO = #{record.fecharehabilitado,jdbcType=TIMESTAMP}");
		}
		if (record.getTexto() != null) {
			sql.SET("TEXTO = #{record.texto,jdbcType=VARCHAR}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionsancion() != null) {
			sql.SET("IDINSTITUCIONSANCION = #{record.idinstitucionsancion,jdbcType=DECIMAL}");
		}
		if (record.getChkrehabilitado() != null) {
			sql.SET("CHKREHABILITADO = #{record.chkrehabilitado,jdbcType=VARCHAR}");
		}
		if (record.getChkfirmeza() != null) {
			sql.SET("CHKFIRMEZA = #{record.chkfirmeza,jdbcType=VARCHAR}");
		}
		if (record.getFechaimposicion() != null) {
			sql.SET("FECHAIMPOSICION = #{record.fechaimposicion,jdbcType=TIMESTAMP}");
		}
		if (record.getChkarchivada() != null) {
			sql.SET("CHKARCHIVADA = #{record.chkarchivada,jdbcType=VARCHAR}");
		}
		if (record.getFechaarchivada() != null) {
			sql.SET("FECHAARCHIVADA = #{record.fechaarchivada,jdbcType=TIMESTAMP}");
		}
		if (record.getFechatraspaso() != null) {
			sql.SET("FECHATRASPASO = #{record.fechatraspaso,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaenviado() != null) {
			sql.SET("FECHAENVIADO = #{record.fechaenviado,jdbcType=TIMESTAMP}");
		}
		if (record.getIdsancionorigen() != null) {
			sql.SET("IDSANCIONORIGEN = #{record.idsancionorigen,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionorigen() != null) {
			sql.SET("IDINSTITUCIONORIGEN = #{record.idinstitucionorigen,jdbcType=DECIMAL}");
		}
		if (record.getNumExpediente() != null) {
			sql.SET("NUM_EXPEDIENTE = #{record.numExpediente,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SANCION");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDSANCION = #{record.idsancion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDTIPOSANCION = #{record.idtiposancion,jdbcType=DECIMAL}");
		sql.SET("REFCOLEGIO = #{record.refcolegio,jdbcType=VARCHAR}");
		sql.SET("REFCGAE = #{record.refcgae,jdbcType=VARCHAR}");
		sql.SET("FECHARESOLUCION = #{record.fecharesolucion,jdbcType=TIMESTAMP}");
		sql.SET("FECHAACUERDO = #{record.fechaacuerdo,jdbcType=TIMESTAMP}");
		sql.SET("FECHAFIRMEZA = #{record.fechafirmeza,jdbcType=TIMESTAMP}");
		sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
		sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
		sql.SET("FECHAREHABILITADO = #{record.fecharehabilitado,jdbcType=TIMESTAMP}");
		sql.SET("TEXTO = #{record.texto,jdbcType=VARCHAR}");
		sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCIONSANCION = #{record.idinstitucionsancion,jdbcType=DECIMAL}");
		sql.SET("CHKREHABILITADO = #{record.chkrehabilitado,jdbcType=VARCHAR}");
		sql.SET("CHKFIRMEZA = #{record.chkfirmeza,jdbcType=VARCHAR}");
		sql.SET("FECHAIMPOSICION = #{record.fechaimposicion,jdbcType=TIMESTAMP}");
		sql.SET("CHKARCHIVADA = #{record.chkarchivada,jdbcType=VARCHAR}");
		sql.SET("FECHAARCHIVADA = #{record.fechaarchivada,jdbcType=TIMESTAMP}");
		sql.SET("FECHATRASPASO = #{record.fechatraspaso,jdbcType=TIMESTAMP}");
		sql.SET("FECHAENVIADO = #{record.fechaenviado,jdbcType=TIMESTAMP}");
		sql.SET("IDSANCIONORIGEN = #{record.idsancionorigen,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCIONORIGEN = #{record.idinstitucionorigen,jdbcType=DECIMAL}");
		sql.SET("NUM_EXPEDIENTE = #{record.numExpediente,jdbcType=VARCHAR}");
		CenSancionExample example = (CenSancionExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	public String updateByPrimaryKeySelective(CenSancion record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SANCION");
		if (record.getIdtiposancion() != null) {
			sql.SET("IDTIPOSANCION = #{idtiposancion,jdbcType=DECIMAL}");
		}
		if (record.getRefcolegio() != null) {
			sql.SET("REFCOLEGIO = #{refcolegio,jdbcType=VARCHAR}");
		}
		if (record.getRefcgae() != null) {
			sql.SET("REFCGAE = #{refcgae,jdbcType=VARCHAR}");
		}
		if (record.getFecharesolucion() != null) {
			sql.SET("FECHARESOLUCION = #{fecharesolucion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaacuerdo() != null) {
			sql.SET("FECHAACUERDO = #{fechaacuerdo,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafirmeza() != null) {
			sql.SET("FECHAFIRMEZA = #{fechafirmeza,jdbcType=TIMESTAMP}");
		}
		if (record.getFechainicio() != null) {
			sql.SET("FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP}");
		}
		if (record.getFechafin() != null) {
			sql.SET("FECHAFIN = #{fechafin,jdbcType=TIMESTAMP}");
		}
		if (record.getFecharehabilitado() != null) {
			sql.SET("FECHAREHABILITADO = #{fecharehabilitado,jdbcType=TIMESTAMP}");
		}
		if (record.getTexto() != null) {
			sql.SET("TEXTO = #{texto,jdbcType=VARCHAR}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionsancion() != null) {
			sql.SET("IDINSTITUCIONSANCION = #{idinstitucionsancion,jdbcType=DECIMAL}");
		}
		if (record.getChkrehabilitado() != null) {
			sql.SET("CHKREHABILITADO = #{chkrehabilitado,jdbcType=VARCHAR}");
		}
		if (record.getChkfirmeza() != null) {
			sql.SET("CHKFIRMEZA = #{chkfirmeza,jdbcType=VARCHAR}");
		}
		if (record.getFechaimposicion() != null) {
			sql.SET("FECHAIMPOSICION = #{fechaimposicion,jdbcType=TIMESTAMP}");
		}
		if (record.getChkarchivada() != null) {
			sql.SET("CHKARCHIVADA = #{chkarchivada,jdbcType=VARCHAR}");
		}
		if (record.getFechaarchivada() != null) {
			sql.SET("FECHAARCHIVADA = #{fechaarchivada,jdbcType=TIMESTAMP}");
		}
		if (record.getFechatraspaso() != null) {
			sql.SET("FECHATRASPASO = #{fechatraspaso,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaenviado() != null) {
			sql.SET("FECHAENVIADO = #{fechaenviado,jdbcType=TIMESTAMP}");
		}
		if (record.getIdsancionorigen() != null) {
			sql.SET("IDSANCIONORIGEN = #{idsancionorigen,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionorigen() != null) {
			sql.SET("IDINSTITUCIONORIGEN = #{idinstitucionorigen,jdbcType=DECIMAL}");
		}
		if (record.getNumExpediente() != null) {
			sql.SET("NUM_EXPEDIENTE = #{numExpediente,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		sql.WHERE("IDSANCION = #{idsancion,jdbcType=DECIMAL}");
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_SANCION
	 * @mbg.generated  Fri Nov 19 13:05:08 CET 2021
	 */
	protected void applyWhere(SQL sql, CenSancionExample example, boolean includeExamplePhrase) {
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