package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FcsFacturacionEstadoEnvio;
import org.itcgae.siga.db.entities.FcsFacturacionEstadoEnvioExample.Criteria;
import org.itcgae.siga.db.entities.FcsFacturacionEstadoEnvioExample.Criterion;
import org.itcgae.siga.db.entities.FcsFacturacionEstadoEnvioExample;

public class FcsFacturacionEstadoEnvioSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String countByExample(FcsFacturacionEstadoEnvioExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("FCS_FACTURACION_ESTADO_ENVIO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String deleteByExample(FcsFacturacionEstadoEnvioExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("FCS_FACTURACION_ESTADO_ENVIO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String insertSelective(FcsFacturacionEstadoEnvio record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("FCS_FACTURACION_ESTADO_ENVIO");
		if (record.getIdfacturacion() != null) {
			sql.VALUES("IDFACTURACION", "#{idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoenviofacturacion() != null) {
			sql.VALUES("IDESTADOENVIOFACTURACION", "#{idestadoenviofacturacion,jdbcType=DECIMAL}");
		}
		if (record.getOrden() != null) {
			sql.VALUES("ORDEN", "#{orden,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String selectByExample(FcsFacturacionEstadoEnvioExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDFACTURACION");
		} else {
			sql.SELECT("IDFACTURACION");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDESTADOENVIOFACTURACION");
		sql.SELECT("ORDEN");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.FROM("FCS_FACTURACION_ESTADO_ENVIO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		FcsFacturacionEstadoEnvio record = (FcsFacturacionEstadoEnvio) parameter.get("record");
		FcsFacturacionEstadoEnvioExample example = (FcsFacturacionEstadoEnvioExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("FCS_FACTURACION_ESTADO_ENVIO");
		if (record.getIdfacturacion() != null) {
			sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoenviofacturacion() != null) {
			sql.SET("IDESTADOENVIOFACTURACION = #{record.idestadoenviofacturacion,jdbcType=DECIMAL}");
		}
		if (record.getOrden() != null) {
			sql.SET("ORDEN = #{record.orden,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("FCS_FACTURACION_ESTADO_ENVIO");
		sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDESTADOENVIOFACTURACION = #{record.idestadoenviofacturacion,jdbcType=DECIMAL}");
		sql.SET("ORDEN = #{record.orden,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		FcsFacturacionEstadoEnvioExample example = (FcsFacturacionEstadoEnvioExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String updateByPrimaryKeySelective(FcsFacturacionEstadoEnvio record) {
		SQL sql = new SQL();
		sql.UPDATE("FCS_FACTURACION_ESTADO_ENVIO");
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}");
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDESTADOENVIOFACTURACION = #{idestadoenviofacturacion,jdbcType=DECIMAL}");
		sql.WHERE("ORDEN = #{orden,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACION_ESTADO_ENVIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	protected void applyWhere(SQL sql, FcsFacturacionEstadoEnvioExample example, boolean includeExamplePhrase) {
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